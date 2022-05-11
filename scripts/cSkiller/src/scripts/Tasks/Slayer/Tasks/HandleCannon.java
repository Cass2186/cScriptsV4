package scripts.Tasks.Slayer.Tasks;

import org.tribot.api.General;
import org.tribot.script.sdk.*;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.GameObject;
import org.tribot.script.sdk.types.InventoryItem;
import scripts.*;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Tasks.Slayer.SlayerConst.Areas;
import scripts.Tasks.Slayer.SlayerConst.SlayerConst;
import scripts.Tasks.Slayer.SlayerUtils.SlayerVars;

import java.util.List;
import java.util.Optional;

public class HandleCannon implements Task {


    public static void setupCannon() {

        List<GameObject> cannonList = Query.gameObjects().inArea().idEquals(ObjectID.DWARF_MULTICANNON).toList();
        Optional<InventoryItem> cball = Query.inventory().idEquals(ItemID.CANNONBALL).findClosestToMouse();
        List<InventoryItem> invCannon = Query.inventory().idEquals(ItemID.CANNON_IDS).toList();

        if (cannonList.size() > 0 || cball.isEmpty())
            return;

        if (cball.map(c -> c.getStack() < 15).orElse(false))
            return;

        if (SlayerVars.get().use_cannon && invCannon.size() > 3) {
            Log.info("[CannonHandler]: Setting Up Cannon");

            if (SlayerVars.get().cannon_location != null &&
                    !MyPlayer.getTile().equals(SlayerVars.get().cannon_location) &&
                    PathingUtil.walkToTile(SlayerVars.get().cannon_location) &&
                    Waiting.waitUntil(7000, 400,
                            () -> SlayerVars.get().cannon_location.isVisible())) {
                if (SlayerVars.get().cannon_location.interact("Walk here")) {
                    Waiting.waitUntil(5000, 50,
                            () -> MyPlayer.getTile().equals(SlayerVars.get().cannon_location));
                }
            }


            Optional<InventoryItem> cannonBase = Query.inventory()
                    .idEquals(ItemID.CANNON_IDS[0]).findClosestToMouse();
            if (cannonBase.map(c -> c.click("Set-up")).orElse(false)) {
                Waiting.waitUntil(10000, 500,
                        () -> Inventory.getCount(ItemID.CANNON_IDS) == 0);
                Utils.idlePredictableAction();
            }

            fireCannon();
        }
    }

    public static boolean fireCannon() {
        if (!Inventory.contains(ItemID.CANNONBALL)) {
            Log.info("[CannonHandler]: Out of Cannonballs, picking up cannon");
            pickupCannon();
            return false;
        }
        if (GameState.getSetting(1) == 0 ||
                GameState.getSetting(SlayerConst.CBALLS_LEFT_GAMESETTING) <= SlayerVars.get().fill_cannon_at) {
            return clickCannon("Fire");
        }
        return false;
    }

    public static boolean clickCannon(String option) {
        if (SlayerVars.get().cannon_location != null) {
            Optional<GameObject> cannon = Query.gameObjects()
                    //.inArea()
                    .maxDistance(20)
                    .idEquals(ObjectID.DWARF_MULTICANNON).findClosestByPathDistance();

            if (cannon.isEmpty()) {
                Log.info("[CannonHandler]: Failed to click cannon: Optional is empty");
                return false;
            }

            Log.info("[CannonHandler]: Clicking cannon with option: " + option);
            if (cannon.map(c -> c.interact(option)).orElse(false)) {
                if (Waiting.waitUntil(2000, 50, () -> MyPlayer.isMoving()))
                    Waiting.waitUntil(6000, 400, () -> !MyPlayer.isMoving());
                //CannonMonitor.cballsLeft = 30;
                SlayerVars.get().fill_cannon_at = General.random(3, 22);
                return true;
            }
        }
        return false;
    }

    public static boolean eatForSpace() {
        return EatUtil.eatFood(false);
    }


    public static boolean pickupCannon() {
        if (!SlayerVars.get().use_cannon)
            return false;


        Optional<GameObject> obj = Query.gameObjects()
                .maxDistance(20)
                .idEquals(ObjectID.DWARF_MULTICANNON)
                .findClosestByPathDistance();


        if (obj.isPresent()) {
            Log.info("[Debug]: Identified cannon: Obj.length > 0");
            int invin_length = Inventory.getAll().size();
            List<InventoryItem> food = Query.inventory().actionContains("Eat").toList();
            if (Inventory.getAll().size() > 24 && (invin_length - food.size()) >= 24) {
                EatUtil.eatFood();
                if (!eatForSpace()) {
                    Log.info("[Debug]: Cannot eat for space, leaving cannon.");
                    return false;
                }
            }
            Log.info("[CannonHandler]: Going to pick up cannon");
            if (obj.map(o -> o.interact("Pick-up")).orElse(false))
                return Waiting.waitUntil(5000, 200,
                        () -> Inventory.getCount(ItemID.CANNON_IDS) >= 4);
        }
        return false;
    }


    public void determineCannonUse(String target) {
        if (target != null) {
            String s = target.toLowerCase();
            if (s.contains("bloodveld")) {
                SlayerVars.get().cannon_location = Areas.BLOODVELD_CANNON_TILE;
                SlayerVars.get().use_cannon = true;

            } else if (s.contains("aberrant spectre")) {
                SlayerVars.get().use_cannon = true;
                SlayerVars.get().cannon_location = Areas.ABERRANT_SPECTRE_CANNON_TILE;

            } else if (s.contains("ankou")) {
                SlayerVars.get().cannon_location = Areas.ANKOU_CANNON_TILE;
                SlayerVars.get().use_cannon = true;

            } else if (s.contains("blue dragons")) {
                SlayerVars.get().cannon_location = Areas.BLUE_DRAGON_CANNON_TILE;
                SlayerVars.get().use_cannon = true;


            } else if (s.contains("dagannoth")) {
                SlayerVars.get().cannon_location = Areas.DAGGANOTH_CANNON_TILE;
                SlayerVars.get().use_cannon = true;


            } else if (s.contains("elves")) {
                SlayerVars.get().cannon_location = Areas.ELF_CANNON_TILE;
                SlayerVars.get().use_cannon = true;


            } else if (s.contains("fire giant")) {
                SlayerVars.get().cannon_location = Areas.FIRE_GIANT_CANNON_TILE;
                SlayerVars.get().use_cannon = true;

            } else if (s.contains("hellhounds")) {
                SlayerVars.get().cannon_location = Areas.HELLHOUND_CANNON_TILE;
                SlayerVars.get().use_cannon = true;

            } else if (s.contains("kalphite")) {
                SlayerVars.get().cannon_location = Areas.KALPHITE_CANNON_TILE;
                SlayerVars.get().use_cannon = true;

            } else if (s.contains("suqah")) {
                SlayerVars.get().cannon_location = Areas.SUQAH_CANNON_TILE;
                SlayerVars.get().use_cannon = true;

            } else {
                SlayerVars.get().use_cannon = false;
                SlayerVars.get().cannon_location = null;
            }
        } else {
            SlayerVars.get().use_cannon = false;
            SlayerVars.get().cannon_location = null;
        }
    }


    public static void repairCannon() {
        Optional<GameObject> obj = Query.gameObjects()
                .maxDistance(20)
                .nameContains("Broken")
                .findClosestByPathDistance();
        if (obj.map(o -> o.interact("Repair")).orElse(false)) {
            Log.info("[CannonHandler]: Repairing Cannon");
            if (Waiting.waitUntil(7000, 400, () -> MyPlayer.getAnimation() != -1))
                Waiting.waitUntil(5000, 300, () -> MyPlayer.getAnimation() == -1);
        }
    }

    @Override
    public Priority priority() {
        return Priority.HIGH;
    }

    @Override
    public boolean validate() {
        if (SlayerVars.get().targets == null)
            return false;

        determineCannonUse(SlayerVars.get().targets[0]);
        return SlayerVars.get().cannon_location != null
                && SlayerVars.get().fightArea != null
                && SlayerVars.get().fightArea.containsMyPlayer()
                && SlayerVars.get().use_cannon
                && (GameState.getSetting(SlayerConst.CBALLS_LEFT_GAMESETTING) <= SlayerVars.get().fill_cannon_at ||
                Inventory.contains(ItemID.CANNON_IDS));
    }

    @Override
    public void execute() {
        setupCannon();
        if (GameState.getSetting(SlayerConst.CBALLS_LEFT_GAMESETTING) <= SlayerVars.get().fill_cannon_at) {
            fireCannon();
            SlayerVars.get().fill_cannon_at = General.random(0, 22);
            Log.info("[CannonHandler]: Refilling cannon at: " + SlayerVars.get().fill_cannon_at);
        }
        if (SlayerVars.get().getTask && SlayerVars.get().use_cannon) {
            Log.warn("[Cannonhandler]: picking up cannon due to null target or needing a task");
            if (!pickupCannon())
                SlayerVars.get().use_cannon = false;
        }
    }

    @Override
    public String taskName() {
        return "Slayer";
    }

    @Override
    public String toString() {
        return "Handling Cannon";
    }
}
