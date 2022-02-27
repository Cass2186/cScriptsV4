package scripts.Tasks.Ranging;

import org.tribot.api.General;

import org.tribot.api2007.*;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.MyPlayer;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.Area;
import org.tribot.script.sdk.types.GameObject;
import org.tribot.script.sdk.types.InventoryItem;
import org.tribot.script.sdk.types.WorldTile;
import scripts.*;
import scripts.API.Priority;
import scripts.API.Task;

import java.util.Optional;

public class CannonHandler implements Task {

    public static int CANNON_FIRING_INDEX = 1;
    public static int CBALLS_LEFT_SETTING = 3;
    public static int fillCannonAt = General.randomSD(7, 4);
    private static WorldTile CANNON_TILE = new WorldTile(2530, 3237, 0);

    public static void setupCannon(WorldTile cannonTile) {
        Optional<GameObject> cannon = Query.gameObjects()
                .actionContains("Refill")
                .idEquals(ObjectID.DWARF_MULTICANNON)
                .findClosest();

        Optional<InventoryItem> cBall = Query.inventory()
                .idEquals(ItemID.CANNONBALL)
                .findClosestToMouse();
        if (cannon.isPresent() || cBall.isEmpty()) // already set up or no cannonballs
            return;

        if (Inventory.find(ItemID.CANNON_IDS).length > 2
                && Inventory.find(ItemID.CANNONBALL).length > 0) {

            if (!MyPlayer.getPosition().equals(cannonTile)) {
                Log.debug("[CannonHandler]: Moving to cannon tile");
                PathingUtil.walkToTile(cannonTile);
                if (cannonTile.isVisible() && cannonTile.interact("Walk here")) {
                    PathingUtil.movementIdle();
                }
            }
            Log.debug("[CannonHandler]: Setting Up Cannon");

            Optional<InventoryItem> cannonBase = Query.inventory()
                    .idEquals(ItemID.CANNON_BASE)
                    .findClosestToMouse();

            if (cannonBase.isEmpty()) {
                Log.error("Missing Cannon base, cannot set up");
                return;
            }

            if (cannonBase.map(c -> c.click("Set-up")).orElse(false)) {
                Timer.waitCondition(() -> Inventory.find(ItemID.CANNON_IDS).length == 0,
                        10000, 12000);
            }

            fireCannon();
        }
    }

    public static boolean fireCannon() {
        if (Inventory.find(ItemID.CANNONBALL).length == 0) {
            Log.error("[CannonHandler]: Out of Cannonballs, picking up cannon");
            pickupCannon();
            return false;
        }
        if (Game.getSetting(CANNON_FIRING_INDEX) == 0) {
            Log.debug("[Debug]: Clicking cannon");
            return clickCannon("Fire");
        }
        return false;
    }

    public static boolean clickCannon(String option) {
        Optional<GameObject> cannon = Query.gameObjects()
                .actionContains("Refill")
                .idEquals(ObjectID.DWARF_MULTICANNON)
                .inArea(Area.fromRadius(CANNON_TILE, 3))
                .findClosest();

        if (cannon.isEmpty()) {
            Log.error("[CannonHandler]: Failed to find cannon");
            return false;
        } else {
            Log.debug("[CannonHandler]: Clicking cannon with option: " + option);
            if (cannon.map(c -> c.interact(option)).orElse(false)) {
                if (Timer.waitCondition(() -> Player.isMoving(), 2000))
                    Timer.waitCondition(() -> !Player.isMoving(), 4000, 6000);
                fillCannonAt = General.randomSD(7, 4);
                return true;
            }
        }

        return false;
    }

    public static boolean eatForSpace() {
        return EatUtil.eatFood();
    }


    public static boolean pickupCannon() {
        Optional<GameObject> cannon = Query.gameObjects()
                .actionContains("Refill")
                .idEquals(ObjectID.DWARF_MULTICANNON)
                .inArea(Area.fromRadius(CANNON_TILE, 3))
                .findClosest();

        if (cannon.isPresent()) {
            Log.debug("Identified cannon");

            if (Inventory.getAll().length > 24) {

                for (int i = 0; i < 5; i++) {
                    Waiting.waitNormal(500,75);
                    EatUtil.eatFood();

                    if (Inventory.getAll().length <= 24)
                        break;

                }
            }
            Log.debug("[CannonHandler]: Going to pick up cannon");
            if (cannon.map(c->c.interact("Pick-up")).orElse(false))
                return Timer.waitCondition(() -> Inventory.find(ItemID.CANNON_IDS).length >= 4,
                        5000, 7000);
        }
        return false;
    }


    public static void repairCannon() {
        Optional<GameObject> cannon = Query.gameObjects()
                .actionContains("Repair")
                .nameContains("Broken")
                .findClosest();
        if (cannon.map(c -> c.interact("Repair")).orElse(false)) {
            Log.debug("[CannonHandler]: Repairing Cannon");
            if (Timer.waitCondition(() -> Player.getAnimation() != -1, 5000, 7000))
                Timer.waitCondition(() -> Player.getAnimation() == -1, 3500, 5000);
        }
    }


    @Override
    public Priority priority() {
        return null;
    }

    @Override
    public boolean validate() {
        return false;
    }

    @Override
    public void execute() {

    }

    @Override
    public String taskName() {
        return null;
    }
}
