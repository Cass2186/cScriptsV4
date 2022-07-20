package scripts.Tasks.Mining.Tasks;

import org.tribot.api.General;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.Skills;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.*;
import org.tribot.script.sdk.Log;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.*;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.EntitySelector.Entities;
import scripts.EntitySelector.finders.prefabs.ItemEntity;
import scripts.EntitySelector.finders.prefabs.ObjectEntity;
import scripts.Tasks.Mining.Utils.MLMUtils;

public class MineOreMLM implements Task {


    public static void goToMLM() {

        if (!MLMUtils.WHOLE_MLM_AREA.contains(Player.getPosition())) {
            Log.info("Going to MLM");
            PathingUtil.walkToArea(MLMUtils.NORTH_MINING_AREA, false);
        }
        if (!MLMUtils.NORTH_MINING_AREA.contains(Player.getPosition())) {
            Log.info("Going to North area");
            PathingUtil.walkToTile(new RSTile(3735, 5689, 0), 2, false);
        }
    }


    private boolean checkDepletion(RSObject obj) {
        if (obj != null) {
            RSObjectDefinition def = obj.getDefinition();
            if (def != null && def.getName().equals("Depleted vein")) {
                Log.info("Identified vein is depleted");
                return true;
            }
        }

        return false;
    }


    public boolean isVeinDepleted() {
        RSTile myTile = Player.getPosition();
        PlayerOrientation facing = MLMUtils.getDirection();

        RSObject vein;

        if (facing == PlayerOrientation.NORTH) {
            vein = Entities.find(ObjectEntity::new)
                    .tileEquals(myTile.translate(0, 1))
                    .getFirstResult();
            // Log.info("Facing north. Is Vein null: " + (vein == null));
            return checkDepletion(vein);

        } else if (facing == PlayerOrientation.SOUTH) {
            vein = Entities.find(ObjectEntity::new)
                    .tileEquals(myTile.translate(0, 1))
                    .getFirstResult();
            //   Log.info("Facing south. Is Vein null: " + (vein == null));
            return checkDepletion(vein);

        } else if (facing == PlayerOrientation.EAST) {
            vein = Entities.find(ObjectEntity::new)
                    .tileEquals(myTile.translate(1, 0))
                    .getFirstResult();
            //  Log.info("Facing East. Is Vein null: " + (vein == null));
            return checkDepletion(vein);
        }
        //   Log.info("Failed to identify vein based on direction");

        getOreVein("Depleted vein");
        getOreVein("Ore vein");
        return false;
    }

    public RSObject getOreVein(String veinName) {
        RSTile myTile = Player.getPosition();

        RSObject[] ore = Objects.findNearest(2, Filters.Objects.
                tileEquals(myTile.translate(0, 1)).and(
                Filters.Objects.nameContains(veinName)));
        if (ore.length > 0) {
            //  Log.info("Vein is north of us");
            return ore[0];
        }
        ore = Objects.findNearest(2, Filters.Objects.
                tileEquals(myTile.translate(0, -1)).and(Filters.Objects.nameContains(veinName)));

        if (ore.length > 0) {
            // Log.info("Vein is south of us");
            return ore[0];
        }
        ore = Objects.findNearest(2, Filters.Objects.
                tileEquals(myTile.translate(-1, 0)).and(Filters.Objects.nameContains(veinName)));
        if (ore.length > 0) {
            // Log.info("Vein is west of us");
            return ore[0];
        }
        ore = Objects.findNearest(2, Filters.Objects.
                tileEquals(myTile.translate(1, 0)).and(Filters.Objects.nameContains(veinName)));
        if (ore.length > 0) {
            //  Log.info("Vein is east of us");
            return ore[0];
        }
        //    General.println("[Debug] no veins anywhere");

        return null;
    }


    public static RSObject getVein() {
        RSObject vein = Entities.find(ObjectEntity::new)
                //  .inArea(MLMUtils.NORTH_MINING_AREA)
                .actionsContains("Mine")
                .nameContains("Ore vein")
                .sortByDistance(Player.getPosition(), true)
                .inArea(new RSArea(Player.getPosition(), 15))
                .notInArea(MLMUtils.UPPER_AREA_EXCLUDE)
                .getFirstResult();

        return vein;
    }

    public boolean mineRock() {
        RSObject vein = getVein();
        RSItem[] gem = Entities.find(ItemEntity::new)
                .nameContains("Uncut")
                .getResults();
        if (gem.length > 0 && Player.getAnimation() == -1)
            Inventory.drop(gem);

        if (Player.getAnimation() == -1)
            Timer.waitCondition(() -> Player.getAnimation() != -1, 1000, 2200);

        if (Player.getAnimation() != -1) {
            AntiBan.timedActions();
          //  Log.info("mineRock() returning true");
            return true;
        }

        if (vein != null) {
            Log.info("Vein identified at " + vein.getPosition().toString());

            if (!MLMUtils.isReachable(vein.getPosition())) {
                Log.info("Navigating to vein");
                if(PathingUtil.walkToTile(vein.getPosition(), 1, false))
              //  MLMUtils.localNavigation(vein.getPosition());
                Timer.waitCondition(() -> vein.getPosition().distanceTo(Player.getPosition()) <
                        General.random(3, 5), 7000, 9000);

            }
            if (Utils.clickObject(vein, "Mine", true)) {
                Vars.get().currentTime = System.currentTimeMillis();
                Log.info("Vein is reachable, clicking");
                return Timer.waitCondition(() -> Player.getAnimation() != -1, 4500, 6000);
            }

        }
        return false;
    }

    public void hopWorlds(){
        if (shouldHop()){
            Log.info("Hopping worlds");
            Utils.hopWorlds();
        }
    }

    private boolean shouldHop(){
        return Utils.getPlayerCountInArea(MLMUtils.NORTH_MINING_AREA) > 2;
    }

    private void waitToFinishMining() {
        if (Player.getAnimation() != -1) {
            if (Timer.skillingWaitCondition(() -> {
                AntiBan.timedActions();
                return isVeinDepleted() || Inventory.isFull() || Player.getAnimation() == -1;
            }, 15000, 20000))
                sleep();
        }
    }



    String message = "";

    private void sleep() {
        if (Vars.get().abc2Chance < 30) {
            message = "ABC2 Sleeping...";
            Utils.abc2ReactionSleep(Vars.get().currentTime);
            Vars.get().abc2Chance = General.random(0, 100);

        } else {
            message = "Sleeping...";
            int sleep = General.randomSD(1250, 450);
            Log.info("Sleeping for " + sleep + " ms");
            General.sleep(sleep);
            Vars.get().abc2Chance = General.random(0, 100);
        }

    }

    @Override
    public String toString() {
        return "Mining paydirt";
    }

    @Override
    public Priority priority() {
        return Priority.NONE;
    }

    @Override
    public boolean validate() {
        return Vars.get().currentTask != null &&
                Vars.get().currentTask.equals(SkillTasks.MINING)
                && Vars.get().useMLM
                && Skills.getActualLevel(Skills.SKILLS.MINING) >= 30 &&
                !Inventory.isFull()
                && Utils.getVarBitValue(Varbits.SACK_NUMBER.getId()) <= 56;
    }

    @Override
    public void execute() {
        // General.println("Mining ore");
        goToMLM();
        if (mineRock())
            waitToFinishMining();
    }

    @Override
    public String taskName() {
        return "MLM Training";
    }
}
