package scripts.Tasks;

import dax.walker_engine.WalkerEngine;
import org.tribot.api.General;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSObjectDefinition;
import org.tribot.api2007.types.RSTile;
import org.tribot.api2007.util.DPathNavigator;
import scripts.Data.MiningConst;
import scripts.Data.MiningUtils;
import scripts.EntitySelector.Entities;
import scripts.EntitySelector.finders.prefabs.ObjectEntity;
import scripts.PathingUtil;
import scripts.PlayerOrientation;
import scripts.Timer;
import scripts.Utils;

import java.nio.file.Path;
import java.util.Arrays;

public class MineOre implements Task {

    private boolean checkDepletion(RSObject obj) {
        if (obj != null) {
            RSObjectDefinition def = obj.getDefinition();
            if (def != null && def.getName().equals("Depleted vein")) {
                General.println("[Debug]: Identified vein is depleted");
                return true;
            }
        }

        return false;
    }

    DPathNavigator dpath = new DPathNavigator();

    public boolean moveToTile(RSTile tile){
        dpath.setAcceptAdjacent(true);
        RSTile[] tiles = dpath.findPath(tile);
        if (tiles.length > 0){
            General.println("[Debug]: Generated local path");
            WalkerEngine.getInstance().walkPath(Arrays.asList(tiles));
            General.sleep(1000,5000);
            return true;
        }
        General.println("[Debug]: DID NOT Generate local path");
        PathingUtil.walkToTile(tile, 3, true);
        return false;
    }


    public boolean mineRock() {
        if (Player.getAnimation() == MiningConst.MINING_ANIMATION)
            return true;

        RSObject vein = Entities.find(ObjectEntity::new)
                .inArea(MiningConst.NORTH_MINING_AREA)

                .actionsContains("Mine")
                .nameContains("Ore vein")
                .sortByDistance(Player.getPosition(), true)
             //   .nameNotContains("Rockfall")
                .getFirstResult();

        if (!MiningConst.NORTH_MINING_AREA.contains(Player.getPosition())) {
            General.println("[Debug]: Going to Mining area");
            moveToTile(new RSTile(3736, 5690));

        }

        if (vein != null) {
            General.println("[Debug]: Vein identified at " + vein.getPosition().toString());

            if (!MiningUtils.isReachable(vein.getPosition())) {
                General.println("[Debug]: Navigating to vein");
                if (PathingUtil.localNavigation(vein.getPosition())) {
                    Timer.waitCondition(() -> vein.getPosition().distanceTo(Player.getPosition()) <
                            General.random(3, 5), 7000, 9000);

                    if (Utils.clickObject(vein, "Mine", false))
                        return Timer.waitCondition(() -> Player.getAnimation() != -1, 3500, 6000);
                }
            } else {
                General.println("[Debug]: Vein is reachable, clicking");
                if (Utils.clickObject(vein, "Mine", false))
                    return Timer.waitCondition(() -> Player.getAnimation() != -1, 3500, 6000);
            }
        }
        return false;
    }


    public boolean isVeinDepleted() {
        RSTile myTile = Player.getPosition();
        PlayerOrientation facing = MiningUtils.getPlayerOrientation();
        RSObject vein;
        if (facing == PlayerOrientation.NORTH) {

            vein = Entities.find(ObjectEntity::new)
                    .tileEquals(myTile.translate(0, 1))
                    .getFirstResult();
           // General.println("[Debug]: Facing north. Is Vein null: " + (vein == null));
            return checkDepletion(vein);
        } else if (facing == PlayerOrientation.SOUTH) {
            vein = Entities.find(ObjectEntity::new)
                    .tileEquals(myTile.translate(0, -1))
                    .getFirstResult();
            //General.println("[Debug]: Facing south. Is Vein null: " + (vein == null));
            return checkDepletion(vein);
        } else if (facing == PlayerOrientation.EAST) {
            vein = Entities.find(ObjectEntity::new)
                    .tileEquals(myTile.translate(1, 0))
                    .getFirstResult();
            //General.println("[Debug]: Facing East. Is Vein null: " + (vein == null));
            return checkDepletion(vein);
        }

        // getOreVein("Depleted vein"); //for debuging
        //getOreVein("Ore vein");//for debuging
        return false;
    }


    public void waitToFinishMining() {
        if (Player.getAnimation() != -1) {
            General.println("[Debug]: Waiting for depletion");
            Timer.abc2SkillingWaitCondition(()-> isVeinDepleted() || Inventory.isFull(), 22000, 35000);
        }
    }


    @Override
    public String toString() {
        return "Mining";
    }


    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        return !Inventory.isFull();
    }

    @Override
    public void execute() {
        mineRock();
            waitToFinishMining();
    }
}
