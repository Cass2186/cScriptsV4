package scripts.Tasks.Hunter;

import dax.walker.utils.AccurateMouse;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.EntitySelector.Entities;
import scripts.EntitySelector.finders.prefabs.ObjectEntity;
import scripts.PathingUtil;
import scripts.Tasks.Hunter.HunterData.HunterConst;
import scripts.Timer;
import scripts.Utils;

import java.util.Arrays;
import java.util.List;

public class SetTrap implements Task {

    public static int determineMaxTraps() {
        if (Skills.getCurrentLevel(Skills.SKILLS.HUNTER) < 20)
            return 1;

        else if (Skills.getCurrentLevel(Skills.SKILLS.HUNTER) < 40)
            return 2;

        else if (Skills.getCurrentLevel(Skills.SKILLS.HUNTER) < 60)
            return 3;

        else if (Skills.getCurrentLevel(Skills.SKILLS.HUNTER) < 80)
            return 4;

        else
            return 5;
    }

    public static int getAllNearbyTraps(List<RSTile> tiles) {
        int i = 0;
        for (RSTile t : tiles) {

            RSObject[] traps =Entities.find(ObjectEntity::new)
                    .inArea(new RSArea(t,1))
                    .nameContains("trap")
                    .getResults();

            if (traps.length > 0)
                i++;

        }
        return i;
    }

    public static int getAllNearbySnares(List<RSTile> tiles) {
        int i = 0;
        for (RSTile t : tiles) {
            RSObject[] traps = Objects.findNearest(20, Filters.Objects.tileEquals(t)
                    .and(Filters.Objects.nameContains("snare")));

            if (traps.length > 0)
                i++;

        }
        return i;
    }

    String message;

    public void layBirdTrap(RSArea area, RSTile... tile) {
        int maxTraps = determineMaxTraps();
        if (!area.contains(Player.getPosition())) {
            PathingUtil.walkToArea(area, false);
        }


        for (int i = 0; i < tile.length; i++) {
            RSObject[] trap1 = Objects.findNearest(10, Filters.Objects.tileEquals(tile[i]).and(Filters.Objects.nameEquals("Bird snare")));

            if (Objects.findNearest(20, HunterConst.LAID_BIRD_SNARE).length < maxTraps
                    && Inventory.find(HunterConst.BIRD_SNARE).length > 0) {

                if (!Player.getPosition().equals(tile[i]) && trap1.length == 0) {
                    message = "Going to trap tile";
                    if (AccurateMouse.walkScreenTile(tile[i]))
                        PathingUtil.movementIdle();
                    General.sleep(750, 3000);
                }
                RSItem[] trap = Inventory.find(HunterConst.BIRD_SNARE);
                if (area.contains(Player.getPosition()) &&
                        Player.getPosition().equals(tile[i]) && trap1.length == 0 && trap.length > 0) {
                    message = "Laying trap";
                    if (trap[0].click("Lay"))
                        Timer.slowWaitCondition(() ->
                                Objects.findNearest(1, HunterConst.LAID_BIRD_SNARE).length > 0 &&
                                        Player.getAnimation() == -1, 5000, 8000);
                }
            }
        }

        if (Inventory.isFull() || Inventory.getAll().length >= 24)
            Inventory.drop(HunterConst.RAW_BIRD_MEAT, HunterConst.BONES);

        message = "Waiting...";
        General.println(message);
        Timer.slowWaitCondition(() -> Objects.find(10, HunterConst.DISABLED_BIRD_SNARE).length > 0 ||
                Objects.find(10, HunterConst.CAUGHT_BIRD_SNARE).length > 0 ||
                GroundItems.find(HunterConst.BIRD_SNARE).length > 0 ||
                getAllNearbySnares(Arrays.asList(HunterConst.TROPICAL_WAGTAIL_TILE_1,
                        HunterConst.TROPICAL_WAGTAIL_TILE_2)) < determineMaxTraps(), 45000, 60000);
    }


    public void layNetTrap(List<RSTile> tiles) {
        for (int i = 0; i < tiles.size(); i++) {
            RSObject[] obj = Entities.find(ObjectEntity::new)
                    .tileEquals(tiles.get(i))
                    .getResults();


            int t = getAllNearbyTraps(tiles);
            General.println("There are: " + t + " traps set up");
            General.println("Is t < maxTraps() " + (t<determineMaxTraps()));
            if (t < determineMaxTraps() && Inventory.find(HunterConst.ROPE).length > 0
                    && Inventory.find(HunterConst.SMALL_FISHING_NET).length > 0) {
                // we can lay a trap

                if (obj.length > 0 && obj[0].getID() == HunterConst.YOUNG_TREE_ID &&
                        Utils.clickObject(obj[0].getID(), "Set-trap", false) &&
                        Timer.slowWaitCondition(() -> Player.getAnimation() != -1, 6000, 8000)) {
                    Timer.slowWaitCondition(() -> Player.getAnimation() == -1, 6000, 8000);
                    General.sleep(General.randomSD(800, 300));
                }
            } else {
                General.println("[Debug]: Waiting");
                Utils.FACTOR = 0.4;
                Timer.abc2SkillingWaitCondition(() ->
                        Objects.findNearest(20, HunterConst.NET_TRAP_SUCCESSFUL).length > 0 ||
                                GroundItems.find(HunterConst.ROPE).length > 0 ||
                                GroundItems.find(HunterConst.SMALL_FISHING_NET).length > 0, 45000, 60000);

            }
        }
    }


    @Override
    public String toString() {
        return message;
    }

    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        if(Vars.get().currentTask != null &&
                Vars.get().currentTask.equals(SkillTasks.HUNTER)) {
            if (Skills.getCurrentLevel(Skills.SKILLS.HUNTER) < 20) {

            }
            if (Skills.getCurrentLevel(Skills.SKILLS.HUNTER) < 29) {
                return Vars.get().currentTask != null &&
                        Vars.get().currentTask.equals(SkillTasks.HUNTER) && getAllNearbySnares(Arrays.asList(HunterConst.TROPICAL_WAGTAIL_TILE_1,
                        HunterConst.TROPICAL_WAGTAIL_TILE_2)) <= determineMaxTraps();
            } else if (Skills.getCurrentLevel(Skills.SKILLS.HUNTER) < 43) { // should be 43 if falconry is being done
                return Vars.get().currentTask != null &&
                        Vars.get().currentTask.equals(SkillTasks.HUNTER) &&
                        getAllNearbySnares(HunterConst.CANFIS_TRAP_TILES) <= determineMaxTraps();
            } else if (Skills.getCurrentLevel(Skills.SKILLS.HUNTER) < 80) {
                return Vars.get().currentTask != null &&
                        Vars.get().currentTask.equals(SkillTasks.HUNTER);
                //  layNetTrap(DESERT_AREA, DESERT_TRAP_1, DESERT_TRAP_2, DESERT_TRAP_3);
            } else {

            }
        }
        return false;
    }

    @Override
    public void execute() {
        //General.println("loop set Trap");
        if (Skills.getCurrentLevel(Skills.SKILLS.HUNTER) < 20) {
            layBirdTrap(HunterConst.COPPER_LONGTAIL_AREA, HunterConst.COPPER_LONGTAIL_TILE);
        } else if (Skills.getCurrentLevel(Skills.SKILLS.HUNTER) < 29) {
            layBirdTrap(HunterConst.TROPICAL_WAGTAIL_AREA,
                    HunterConst.TROPICAL_WAGTAIL_TILE_1, HunterConst.TROPICAL_WAGTAIL_TILE_2);

        } else if (Skills.getCurrentLevel(Skills.SKILLS.HUNTER) < 49) { // should be 43
            layNetTrap(HunterConst.CANFIS_TRAP_TILES);
        } else if (Skills.getCurrentLevel(Skills.SKILLS.HUNTER) < 80) {

            //  layNetTrap(DESERT_AREA, DESERT_TRAP_1, DESERT_TRAP_2, DESERT_TRAP_3);
        } else {

        }


    }

    @Override
    public String taskName() {
        return "Hunter: set trap";
    }
}
