package scripts.Tasks.Mining.Tasks;

import org.tribot.api.General;
import org.tribot.script.sdk.*;
import org.tribot.script.sdk.input.Mouse;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.*;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.*;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class IronOre implements Task {


    WorldTile ARDOUGNE_MINING_TILE = new WorldTile(2692, 3329, 0);
    int UNCUT_EMERALD = 1621;

    List<Integer> DEPLETED_ORE_IDS = new ArrayList<>(Arrays.asList(11391, 11390));
    List<Integer> IRON_ROCK_IDS = new ArrayList<>(Arrays.asList(11365, 11364));
    int[] IRON_ROCK_ARRAY = {11365, 11364};
    // int[] IRON_ROCK_ARRAY = {11365, 11364};
    int[] DEPLETED_ORE_ARRAY = {11391, 11390};
    WorldTile[] ARDOUGNE_IRON_ROCK_TILES = {
            new WorldTile(2691, 3329, 0),
            new WorldTile(2692, 3328, 0),
            new WorldTile(2693, 3329, 0)
    };

    public List<GameObject> getRock() {
        List<GameObject> rocks = Query.gameObjects()
                .idNotEquals(DEPLETED_ORE_ARRAY[0])
                .idNotEquals(DEPLETED_ORE_ARRAY[1])
                .actionContains("Mine")
                .toList();

        List<GameObject> interactive = Query.gameObjects()
                .isInteractive()
                .idNotEquals(DEPLETED_ORE_ARRAY)
                .actionContains("Mine")
                .toList();

        return interactive;

        // return rocks;
    }


    public void genericMineRock(WorldTile miningTile) {
        if (!miningTile.equals(MyPlayer.getTile())) {
            // go to mining tile
            Log.log("[Debug]: Going to custom mining tile");
            if (PathingUtil.walkToTile(miningTile))
                PathingUtil.movementIdle();

            if (miningTile.isVisible()) {
                miningTile.click("Walk here");
                Waiting.waitNormal(200, 45);
            }

        }
        Utils.unselectItem();
        cSkiller.status = "Mining Rocks";

        List<GameObject> rock = getRock();


        if (rock.isEmpty())
            return;

        Area area = Area.fromRadius(miningTile, 1);
        int chance = General.random(0, 100);
        for (int i = 0; i < rock.size(); i++) {
            if (Inventory.isFull()) break;

            if (area.contains(rock.get(i).getTile()) && MyPlayer.getAnimation() == -1 &&
                    rock.get(i).click("Mine")) {
                Waiting.waitNormal(100, 20);
                if (i != rock.size() - 1 && area.contains(rock.get(i + 1).getTile()))
                    rock.get(i + 1).hover();

                if (Timer.waitCondition(() -> {
                    AntiBan.timedActions();
                    return MyPlayer.getAnimation() != -1;
                }, 1500, 2000)) {
                    if (chance < General.random(25, 35) && Skill.MINING.getActualLevel() > 41)
                        Timer.abc2SkillingWaitCondition(() -> MyPlayer.getAnimation() == -1, 5500, 6500);
                    else {
                        Timer.waitCondition(() -> MyPlayer.getAnimation() == -1, 5500, 6500);
                    }
                }
            }
        }
        if (Inventory.isFull()) {
            int b = Mouse.getSpeed();
            Mouse.setSpeed(General.random(200, 220));
            cSkiller.status = "Dropping Rocks";
            List<InventoryItem> ore = Query.inventory().nameContains("ore")
                    .toList();
            Inventory.drop(ore);
            Mouse.setSpeed(b);
            Utils.unselectItem();
        }
    }


    public void mineOre() {
        if (!ARDOUGNE_MINING_TILE.equals(MyPlayer.getTile())) {
            // go to mining tile
            Log.log("[Debug]: Going to Ardougne mining tile");
            if (PathingUtil.walkToTile(ARDOUGNE_MINING_TILE))
                PathingUtil.movementIdle();

            if (ARDOUGNE_MINING_TILE.isVisible() && ARDOUGNE_MINING_TILE.click("Walk here"))
               Waiting.waitUntil(1500, 200, ()-> ARDOUGNE_MINING_TILE.equals(MyPlayer.getTile()));


        } else {
            Utils.unselectItem();
            cSkiller.status = "Mining Rocks";
            List<GameObject> rock = getRock();
            Area area = Area.fromRadius(ARDOUGNE_MINING_TILE, 1);
            if (Utils.getPlayerCountInArea(area) > 0) {
                Log.log("[Debug]: Need to worldhop due to player on tile");
                Optional<World> randomMembers = Worlds.getRandomMembers();
                randomMembers.ifPresent(w -> WorldHopper.hop(w.getWorldNumber()));
                return;
            }
            int chance = General.random(0, 100);
            for (int i = 0; i < rock.size(); i++) {
                //   for (RSObject r : rock) {
                if (Inventory.isFull()) break;

                if (area.contains(rock.get(i).getTile()) && rock.get(i).click("Mine")) {
                    Waiting.waitNormal(100, 20);
                    if (i != rock.size() - 1 && area.contains(rock.get(i + 1).getTile()))
                        rock.get(i + 1).hover();

                    if (Timer.waitCondition(() -> {
                        AntiBan.timedActions();
                        return MyPlayer.getAnimation() != -1;
                    }, 1500, 2000)) {
                        if (chance < General.random(25, 35) && Skill.MINING.getActualLevel() > 41)
                            Timer.abc2SkillingWaitCondition(() -> MyPlayer.getAnimation() == -1, 5500, 6500);
                        else {
                            Timer.waitCondition(() -> MyPlayer.getAnimation() == -1, 5500, 6500);
                            Utils.idleNormalAction();
                        }
                    }
                }
            }
        }
        if (Inventory.isFull()) {
            int b = Mouse.getSpeed();
            Mouse.setSpeed(General.random(200, 220));
            cSkiller.status = "Dropping Rocks";
            Inventory.drop(ItemID.IRON_ORE, UNCUT_EMERALD, ItemID.UNCUT_SAPPHIRE, ItemID.UNCUT_RUBY);
            Mouse.setSpeed(b);
            Utils.unselectItem();
        }
    }


    @Override
    public Priority priority() {
        return Priority.HIGH;
    }

    @Override
    public boolean validate() {
        return Vars.get().currentTask != null &&
                Vars.get().currentTask.equals(SkillTasks.MINING)
                && !Vars.get().useMLM
                && MiningBank.hasBestPickAxe();
    }

    @Override
    public void execute() {
        if (Skill.MINING.getActualLevel() >= 15)
            mineOre();
        else {
            genericMineRock(new WorldTile(3223, 3147, 0)); //lumbridge
        }
    }

    @Override
    public String taskName() {
        return "Power Mining";
    }
}
