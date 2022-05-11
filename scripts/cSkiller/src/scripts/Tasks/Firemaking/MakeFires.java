package scripts.Tasks.Firemaking;

import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.*;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.*;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.*;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MakeFires implements Task {

    /********
     * level 1-15 = 61 logs (40xp/ea)
     * level 15-30 = 183 oak logs (60xp/ea)
     * level 30-45 = 533 willow logs (90xp/ea)
     * level 45-50 = 295 maple logs (135xp/ea)
     */

    public String message;
    Area GE_START_AREA = Area.fromRectangle(new WorldTile(3175, 3504, 0),
            new WorldTile(3179, 3475, 0));

    private Optional<WorldTile> getStartTile() {
        // sort by distance to player
        List<WorldTile> allTiles = GE_START_AREA.getAllTiles()
                .stream().sorted(Comparator.comparingInt(WorldTile::distance))
                .collect(Collectors.toList());
        List<WorldTile> allTilesRendered = GE_START_AREA.getAllTiles()
                .stream().sorted(Comparator.comparingInt(WorldTile::distance))
                .filter(t->t.isRendered())
                .collect(Collectors.toList());
        if (allTilesRendered.size() == 0 && allTiles.size() > 0){
            Log.info("Going to start tile");
            PathingUtil.walkToTile(allTiles.get(0));
        }
        return allTiles.stream().filter(t -> isPathGood(t, Utils.random(6, 10))).findFirst();
    }

    private boolean isPathGood(WorldTile startTile, int minLength) {
        for (int i = 0; i < minLength; i++) {
            if (!startTile.toLocalTile().translate(-i, 0).hasCollision(LocalTile.Collision.OPEN)) {
                Log.warn("Colision data is not open");
                return false;
            } else if (!startTile.toLocalTile().translate(-i, 0).isRendered()) {
                Log.warn("Tile is not walkable");
                return false;
            }
        }

        Log.info("Tile is a good for path of min tiles: " + minLength);
        return true;
    }


    public boolean shouldReset() {
        if (MyPlayer.isMoving()) {
            Log.info("Still moving, waiting briefly");
            Waiting.waitUntil(4500, 600, () -> !MyPlayer.isMoving());
        }
        RSObject[] fires = fires = Objects.findNearest(1, 26185);
        for (int i = 0; i < fires.length; i++) {
            if (fires[i].getPosition().equals(Player.getPosition())) {
                Log.info("Moving to an new start location");
                return true;
            }
        }
        if (FireMakingAreas.STARTING_AREA_1.contains(Player.getPosition()) ||
                FireMakingAreas.STARTING_AREA_2.contains(Player.getPosition()))
            return false;

        if (FireMakingAreas.GE_WEST_WALL.contains(Player.getPosition()) ||
                FireMakingAreas.GE_BOOTH_AREA.contains(Player.getPosition())) {
            return true;
        }
        if (Vars.get().shouldResetFireMaking) {
            Vars.get().shouldResetFireMaking = false;
            return true;
        }
        return false;
    }

    public boolean isItemSelected() {
        return GameState.isAnyItemSelected();
    }

    public static int getCurrentLogID() {
        if (Skills.getActualLevel(Skills.SKILLS.FIREMAKING) < 15) {
            return ItemID.LOG_IDS[0];
        } else if (Skills.getActualLevel(Skills.SKILLS.FIREMAKING) < 30) {
            return ItemID.OAK_LOGS;
        } else if (Skills.getActualLevel(Skills.SKILLS.FIREMAKING) < 45) {
            return ItemID.WILLOW_LOGS;
        } else if (Skills.getActualLevel(Skills.SKILLS.FIREMAKING) < 60) {
            return ItemID.MAPLE_LOGS;
        } else if (Skills.getActualLevel(Skills.SKILLS.FIREMAKING) < 99) {
            return ItemID.YEW_LOGS;
        }
        return ItemID.LOG_IDS[0];
    }

    public void lightFire(int logIndex) {
        RSItem[] t = Inventory.find(ItemID.TINDERBOX);
        Optional<InventoryItem> tinderbox = Query.inventory()
                .idEquals(ItemID.TINDERBOX).findClosestToMouse();

        Optional<InventoryItem> logs = Query.inventory()
                .nameContains("Logs", "logs").findClosestToMouse();

        RSItem[] invLogs = Inventory.find(ItemID.LOG_IDS[logIndex]);
        int invSize = Inventory.getAll().length;

        if (invLogs.length > 0 && t.length > 0) {
            if (shouldReset()) {
                if (getStartTile().map(PathingUtil::walkToTile)
                        .orElse(false)) {
                    Log.info("Walking to Start Tile");
                  //  PathingUtil.walkToTile(FireMakingAreas.STARTING_AREA_1.getRandomTile());
                    Waiting.waitUntil(1500, 300, () -> MyPlayer.isMoving());
                    PathingUtil.movementIdle();
                } /*else if (PathingUtil.walkToTile(FireMakingAreas.STARTING_AREA_2.getRandomTile())) {

                    Waiting.waitUntil(1500, 300, () -> MyPlayer.isMoving());
                    PathingUtil.movementIdle();
                }*/
            }
            if (MyPlayer.isMoving()) {
                Log.info("Still moving, waiting briefly");
                Waiting.waitUntil(4500, 600, () -> !MyPlayer.isMoving());
            }

            RSTile startPosition = Player.getPosition();
            if (isItemSelected() && logs.map(l -> l.click("Use ")).orElse(false)) {
                message = "Waiting...";
                Timer.waitCondition(() -> Inventory.getAll().length < invSize, 2500, 3500);
                if (Timer.slowWaitCondition(() -> !Player.getPosition().equals(startPosition) &&
                        Player.getAnimation() != 733, 12000, 19000)) {
                    Vars.get().daxTracker.trackData("Fires", 1);
                }


            } else {
                message = "Lighting fire";
                Log.info("Lighting Fire");
                if (logs.map(l -> tinderbox.map(tind -> tind.useOn(l)).orElse(false)).orElse(false))
                    Timer.waitCondition(() -> Inventory.getAll().length < invSize, 2500, 3500);

                // hover next log
                invLogs = Inventory.find(ItemID.LOG_IDS[logIndex]);
                if (invLogs.length > 0 && t[0].click("Use")) {
                    AntiBan.waitItemInteractionDelay();
                    invLogs[0].hover();
                }
                if (Timer.slowWaitCondition(() -> !Player.getPosition().equals(startPosition) &&
                        Player.getAnimation() != 733, 12000, 19000)) {
                    Vars.get().daxTracker.trackData("Fires", 1);
                }
            }
            if (ChatScreen.isOpen())
                ChatScreen.handle();
        }
    }

    @Override
    public String toString() {
        return message;
    }

    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        return Vars.get().currentTask != null &&
                Vars.get().currentTask.equals(SkillTasks.FIREMAKING);
    }

    @Override
    public void execute() {
        if (Skills.getActualLevel(Skills.SKILLS.FIREMAKING) < 15) {
            lightFire(getCurrentLogID());
        } else if (Skills.getActualLevel(Skills.SKILLS.FIREMAKING) < 30) {
            lightFire(1);
        } else if (Skills.getActualLevel(Skills.SKILLS.FIREMAKING) < 45) {
            lightFire(2);
        } else if (Skills.getActualLevel(Skills.SKILLS.FIREMAKING) < 60) {
            lightFire(3);
        } else if (Skills.getActualLevel(Skills.SKILLS.FIREMAKING) < 99) {
            lightFire(4);
        } else if (Skills.getActualLevel(Skills.SKILLS.FIREMAKING) >= Vars.get().firemakingTargetLevel) {
            cSkiller.isRunning.set(false); //ends script
            General.println("[Debug]: Done firemaking");
        }
    }

    @Override
    public String taskName() {
        return "FireMaking";
    }
}
