package scripts.Tasks.Hunter;

import dax.walker.utils.AccurateMouse;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Skill;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.Area;
import org.tribot.script.sdk.types.GameObject;
import org.tribot.script.sdk.types.WorldTile;
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
import java.util.Optional;

public class SetTrap implements Task {

    public static int determineMaxTraps() {
        if (Skill.HUNTER.getActualLevel() < 20)
            return 1;

        else if (Skill.HUNTER.getActualLevel() < 40)
            return 2;

        else if (Skill.HUNTER.getActualLevel() < 60)
            return 3;

        else if (Skill.HUNTER.getActualLevel() < 80)
            return 4;

        else
            return 5;
    }


    public static int getAllNearbyTrapsWorldTile(List<WorldTile> tiles) {
        int i = 0;
        for (WorldTile t : tiles) {
            List<GameObject> traps = Query.gameObjects().inArea(Area.fromRadius(t, 1))
                    .nameContains("trap")
                    .toList();
            if (traps.size() > 0)
                i++;

        }
        return i;
    }

    public static int getAllNearbyTraps(List<RSTile> tiles) {
        int i = 0;
        for (RSTile t : tiles) {

            RSObject[] traps = Entities.find(ObjectEntity::new)
                    .inArea(new RSArea(t, 1))
                    .nameContains("trap")
                    .getResults();

            if (traps.length > 0)
                i++;

        }
        return i;
    }

    public static int getAllNearbySnaresNew(List<WorldTile> tiles) {
        int i = 0;
        for (WorldTile t : tiles) {
            List<GameObject> snare = Query.gameObjects().tileEquals(t)
                    .nameContains("snare").toList();

            if (snare.size() > 0)
                i++;
        }
        return i;
    }


    public static int getAllNearbySnares(List<RSTile> tiles) {
        int i = 0;
        for (RSTile t : tiles) {
            List<GameObject> snare = Query.gameObjects().tileEquals(Utils.getWorldTileFromRSTile(t))
                    .nameContains("snare").toList();
            if (snare.size() > 0)
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


    public void layNetTrapNew(List<WorldTile> tiles) {
        for (int i = 0; i < tiles.size(); i++) {
            Optional<GameObject> young_tree = Query.gameObjects()
                    .tileEquals(tiles.get(i))
                    .nameContains("Young tree")
                    .actionContains("Set-trap")
                    .findBestInteractable();

           /* RSObject[] obj = Entities.find(ObjectEntity::new)
                    .tileEquals(tiles.get(i))
                    .nameContains("Young Tree")
                    .getResults();
*/
            int t = getAllNearbyTrapsWorldTile(tiles);
            Log.info("There are " + t + " traps set up");
            //  Log.info("Is t < maxTraps() " + (t<determineMaxTraps()));
            if (t < determineMaxTraps() && Inventory.find(HunterConst.ROPE).length > 0
                    && Inventory.find(HunterConst.SMALL_FISHING_NET).length > 0) {
                // we can lay a trap

                if (young_tree.map(y -> y.interact("Set-trap")).orElse(false) &&
                        Timer.slowWaitCondition(() -> Player.getAnimation() != -1, 6000, 8000)) {
                    Timer.slowWaitCondition(() -> Player.getAnimation() == -1, 6000, 8000);
                    Utils.idleNormalAction();
                    return;
                }
            }
        }
        if (!Query.gameObjects().actionContains("Check")
                .maxDistance(20).isAny() && !isTrapsSetLessThanAllowed() &&
                GroundItems.find(HunterConst.ROPE).length  ==0
                && GroundItems.find(HunterConst.SMALL_FISHING_NET).length == 0) {
            Log.info("Waiting");
            Utils.FACTOR = 0.4;
            Waiting.waitUntil(10000, 125, () ->
                    // Objects.findNearest(20, HunterConst.NET_TRAP_SUCCESSFUL).length > 0 ||
                    GroundItems.find(HunterConst.ROPE).length > 0 ||
                            Query.gameObjects().actionContains("Check")
                                    .maxDistance(20).isAny() || isTrapsSetLessThanAllowed() ||
                            GroundItems.find(HunterConst.SMALL_FISHING_NET).length > 0);
            Utils.idleNormalAction();
        }
    }

    public void layNetTrap(List<RSTile> tiles) {
        for (int i = 0; i < tiles.size(); i++) {
            Optional<GameObject> young_tree = Query.gameObjects()
                    .tileEquals(Utils.getWorldTileFromRSTile(tiles.get(i)))
                    .nameContains("Young tree")
                    .actionContains("Set-trap")
                    .findBestInteractable();

            int t = getAllNearbyTraps(tiles);
            Log.info("There are " + t + " traps set up");
            //  Log.info("Is t < maxTraps() " + (t<determineMaxTraps()));
            if (t < determineMaxTraps() && Inventory.find(HunterConst.ROPE).length > 0
                    && Inventory.find(HunterConst.SMALL_FISHING_NET).length > 0) {
                // we can lay a trap

                if (young_tree.map(y -> y.interact("Set-trap")).orElse(false) &&
                        Timer.slowWaitCondition(() -> Player.getAnimation() != -1, 6000, 8000)) {
                    Timer.slowWaitCondition(() -> Player.getAnimation() == -1, 6000, 8000);
                    Utils.idleNormalAction();
                }
            } else {
                Log.info("Waiting");
                Utils.FACTOR = 0.4;
                Waiting.waitUntil(10000, 125, () ->
                        // Objects.findNearest(20, HunterConst.NET_TRAP_SUCCESSFUL).length > 0 ||
                        GroundItems.find(HunterConst.ROPE).length > 0 ||
                                Query.gameObjects().actionContains("Check")
                                        .maxDistance(20).isAny() || isTrapsSetLessThanAllowed() ||
                                GroundItems.find(HunterConst.SMALL_FISHING_NET).length > 0);
                Utils.idleNormalAction();
            }
        }
    }

    private boolean isTrapsSetLessThanAllowed() {
        if (Skill.HUNTER.getActualLevel() < 29) {
            return getAllNearbySnares(Arrays.asList(HunterConst.TROPICAL_WAGTAIL_TILE_1,
                    HunterConst.TROPICAL_WAGTAIL_TILE_2)) < determineMaxTraps();
        } else if (Skill.HUNTER.getActualLevel() < 43) { // should be 43 if falconry is being done
            return
                    getAllNearbySnares(HunterConst.CANFIS_TRAP_TILES) < determineMaxTraps();
        } else if (Skill.HUNTER.getActualLevel() < 80) {
            return getAllNearbySnaresNew(HunterConst.YELLOW_SALAMANDER_TREES) < determineMaxTraps();
            //  layNetTrap(DESERT_AREA, DESERT_TRAP_1, DESERT_TRAP_2, DESERT_TRAP_3);
        }
        return false;
    }

    @Override
    public String toString() {
        return "Setting Trap";
    }

    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        if (Vars.get().currentTask != null &&
                Vars.get().currentTask.equals(SkillTasks.HUNTER)) {
            if (Skill.HUNTER.getActualLevel() < 20) {

            }
            if (Skill.HUNTER.getActualLevel() < 29) {
                return Vars.get().currentTask != null &&
                        Vars.get().currentTask.equals(SkillTasks.HUNTER) && getAllNearbySnares(Arrays.asList(HunterConst.TROPICAL_WAGTAIL_TILE_1,
                        HunterConst.TROPICAL_WAGTAIL_TILE_2)) <= determineMaxTraps();
            } else if (Skill.HUNTER.getActualLevel() < 43) { // should be 43 if falconry is being done
                return Vars.get().currentTask != null &&
                        Vars.get().currentTask.equals(SkillTasks.HUNTER) &&
                        getAllNearbySnares(HunterConst.CANFIS_TRAP_TILES) <= determineMaxTraps();
            } else if (Skill.HUNTER.getActualLevel() < 80) {
                return Vars.get().currentTask != null &&
                        Vars.get().currentTask.equals(SkillTasks.HUNTER) &&
                        getAllNearbySnaresNew(HunterConst.YELLOW_SALAMANDER_TREES) <= determineMaxTraps();
                //  layNetTrap(DESERT_AREA, DESERT_TRAP_1, DESERT_TRAP_2, DESERT_TRAP_3);
            } else {

            }
        }
        return false;
    }

    @Override
    public void execute() {
        //General.println("loop set Trap");
        if (Skill.HUNTER.getActualLevel() < 20) {
            layBirdTrap(HunterConst.COPPER_LONGTAIL_AREA, HunterConst.COPPER_LONGTAIL_TILE);
        } else if (Skill.HUNTER.getActualLevel() < 29) {
            layBirdTrap(HunterConst.TROPICAL_WAGTAIL_AREA,
                    HunterConst.TROPICAL_WAGTAIL_TILE_1, HunterConst.TROPICAL_WAGTAIL_TILE_2);

        } else if (Skill.HUNTER.getActualLevel() < 47) { // should be 43
            layNetTrap(HunterConst.CANFIS_TRAP_TILES);
        } else if (Skill.HUNTER.getActualLevel() < 80) {
            layNetTrapNew(HunterConst.YELLOW_SALAMANDER_TREES);
            ;
        } else {

        }


    }

    @Override
    public String taskName() {
        return "Hunter";
    }
}
