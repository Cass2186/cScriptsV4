package scripts.Tasks.Runecrafting;

import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.MyPlayer;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.GameObject;
import org.tribot.script.sdk.types.InventoryItem;
import scripts.*;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.Tasks.BirdHouseRuns.Nodes.Wait;
import scripts.Tasks.Runecrafting.RunecraftData.RcVars;

import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Optional;

public class CraftRunes implements Task {

    String message = "Crafting Runes";

    public static boolean usingPouches() {
        return Inventory.find(Filters.Items.nameContains("pouch")).length > 0;
    }

    private void focusOnObject(Optional<GameObject> obj) {
        if (obj.map(a -> !a.isVisible()).orElse(false)) {
            Utils.setCamera(90, 40, 330, 40);
        }
    }


    public boolean craftRunesAltar() {
        Waiting.waitNormal(500,35);
        Optional<GameObject> altar = QueryUtils.getObject("Altar");

        if (!Inventory.isFull())
            emptyPouches();

        focusOnObject(altar);
        if (altar.map(a -> !a.interact("Craft-rune")).orElse(false) &&
                Timer.slowWaitCondition(() -> MyPlayer.getAnimation() != -1, 9000, 15000)) {
            return Timer.waitCondition(() -> MyPlayer.getAnimation() == -1, 3000, 5000);

        }
        return false;
    }

    public void craftRunesAltar(RSArea altarTile) {
        Waiting.waitNormal(500,35);
        RSObject[] altar = Objects.findNearest(30, "Altar");
        if (altar.length > 0) {
            Walking.blindWalkTo(altarTile.getRandomTile());
            Timer.waitCondition(() -> altar[0].isClickable(), 7000, 12000);
            if (Utils.clickObject("Altar", "Craft-rune", false)) {
                Timer.abc2WaitCondition(() -> Inventory.find(ItemID.PURE_ESSENCE).length < 1, 9000, 15000);
            }
        }
    }

    public boolean emptyPouches() {
        List<InventoryItem> pouch = Query.inventory().nameContains("pouch").toList();
        RSItem[] p = Inventory.find(Filters.Items.nameContains("pouch"));
        if (pouch.size() > 0) {
            Log.info("Opening pouches");
            Keyboard.sendPress((char) KeyEvent.VK_SHIFT, 16);
            for (InventoryItem item : pouch) {
                if (item.click("Empty"))
                    Waiting.waitNormal(120, 30);
            }
            Keyboard.sendRelease((char) KeyEvent.VK_SHIFT, 16);
            Utils.idlePredictableAction();
            return true;
        }
        return false;
    }


    public boolean craftCombinationRune(int runeId) {

        RSObject[] altar = Objects.findNearest(30, Filters.Objects.nameContains("Altar")
                .and(Filters.Objects.actionsNotContains("Pray").and(Filters.Objects.actionsContains("Craft-rune"))));
        if (altar.length > 0) {

            if (!altar[0].isClickable()) {
                Utils.setCamera(90, 10, 330, 30);
                message = "Walking to Altar";
                Log.info("Walking to altar");
                if (PathingUtil.localNavigation(altar[0].getPosition().translate(-1, 1))) {
                    if (!RcVars.get().usingLunarImbue)
                        GameTab.open(GameTab.TABS.INVENTORY);
                    Timer.waitCondition(() -> altar[0].isClickable(), 7000, 12000);
                }
            }
            if (RcVars.get().usingLunarImbue && !RcVars.get().isImbueActive && !RcVars.get().abyssCrafting) {
                message = "Casting Imbue";
                castImbue();
            }
            message = "Crafting Runes";

            Log.info("here - line 104");
            if (Inventory.find(ItemID.PURE_ESSENCE).length > 0
                    && Utils.useItemOnObject(runeId, "Altar")) {

                if (!RcVars.get().usingLunarImbue)
                    RunecraftBank.rodHover();

                expTimer.reset();
                if (RcVars.get().abyssCrafting) {
                    Timer.waitCondition(() -> Player.getAnimation() != -1, 9000, 15000);
                    return Timer.waitCondition(() -> Player.getAnimation() == -1, 9000, 15000);
                }
                return Timer.waitCondition(() -> Inventory.find(ItemID.PURE_ESSENCE).length == 0, 9000, 15000);
            }
        }
        return false;
    }

    public void craftRunes() {
        RSObject[] altar = Objects.findNearest(30, Filters.Objects.nameContains("Altar").
                and(Filters.Objects.actionsNotContains("Pray")));

        if (altar.length > 0) {

            if (!RcVars.get().abyssCrafting && (!altar[0].isClickable() ||
                    altar[0].getPosition().distanceTo(Player.getPosition()) > 8)) {
                message = "Walking to Altar";
                Log.info("[Debug]: " + message);
                if (Walking.blindWalkTo(altar[0].getPosition().translate(-1, 1)))
                    Timer.waitCondition(() -> altar[0].isClickable(), 7000, 12000);

            }

            message = "Crafting Runes";
            Log.info("[Debug]: " + message);
            if (Utils.clickObject("Altar", "Craft-rune", false)) {
                expTimer.reset();
                if (RcVars.get().abyssCrafting || RcVars.get().zanarisCrafting) {
                    Timer.waitCondition(() -> Player.getAnimation() != -1, 9000, 15000);
                    Timer.waitCondition(() -> Player.getAnimation() == -1, 9000, 15000);
                } else
                    Timer.waitCondition(() -> Inventory.find(ItemID.PURE_ESSENCE).length == 0, 9000, 15000);
            }
            if (!Inventory.isFull())
                emptyPouches();

            if (Utils.clickObject("Altar", "Craft-rune", false)) {
                expTimer.reset();
                if (RcVars.get().abyssCrafting || RcVars.get().zanarisCrafting) {
                    Timer.waitCondition(() -> Player.getAnimation() != -1, 9000, 15000);
                    Timer.waitCondition(() -> Player.getAnimation() == -1, 9000, 15000);
                } else
                    Timer.waitCondition(() -> Inventory.find(ItemID.PURE_ESSENCE).length == 0, 9000, 15000);
            }
            if (RcVars.get().abyssCrafting)
                RunecraftBank.gloryTele("Edgeville");
        }
    }

    public static RSArea EARTH_ALTAR_AREA = new RSArea(new RSTile(3300, 3475, 0), new RSTile(3303, 3472, 0));
    public static RSArea FIRE_ALTAR_AREA = new RSArea(new RSTile(3315, 3251, 0), new RSTile(3311, 3255, 0));
    public static RSArea FIRE_ALTAR_TILE = new RSArea(new RSTile(2583, 4840, 0), 3);
    public static RSArea CASTLE_WARS_AREA = new RSArea(new RSTile(2442, 3084, 0), 1);
    public static RSArea ZANARIS_BANK = new RSArea(
            new RSTile[]{
                    new RSTile(2386, 4463, 0),
                    new RSTile(2382, 4463, 0),
                    new RSTile(2380, 4461, 0),
                    new RSTile(2380, 4456, 0),
                    new RSTile(2382, 4454, 0),
                    new RSTile(2386, 4454, 0)
            }
    );
    public static RSArea ZANARIS_ALTAR = new RSArea(new RSTile(2407, 4381, 0), new RSTile(2414, 4376, 0));
    public static RSTile FIRE_ALTAR_TILE_BEFORE_RUINS = new RSTile(3312, 3253, 0);
    public static String imbueOn = "You are charged to combine runes";
    public static String imbueOff = "charge has ended";


    public static void handleImbueMessages(String message) {
        if (message.contains(imbueOff)) {
            RcVars.get().isImbueActive = false;
        } else if (message.contains(imbueOn))
            RcVars.get().isImbueActive = true;
    }

    public static boolean castImbue() {
        return RcVars.get().isImbueActive || Magic.selectSpell("Magic Imbue");
    }

    public static boolean atAltar() {
        RSObject[] altar = Objects.findNearest(30, "Altar");
        return altar.length > 0;
    }

    Timer expTimer = new Timer(General.random(420000, 480000));

    @Override
    public String toString() {
        return message;
    }

    @Override
    public Priority priority() {
        return Priority.HIGH;
    }

    @Override
    public boolean validate() {
        if (Vars.get().currentTask != null && Vars.get().currentTask.equals(SkillTasks.RUNECRAFTING) &&
        !RcVars.get().usingOuraniaAlter) {
            // Lava and NOT imbue
            if (RcVars.get().lava && !RcVars.get().usingLunarImbue) {
                return atAltar() && Inventory.find(ItemID.PURE_ESSENCE).length > 0
                        && Inventory.find(ItemID.EARTH_TALISMAN).length > 0
                        && !RcVars.get().collectPouches;

            }
            // lava and Imbue
            if (RcVars.get().lava && RcVars.get().usingLunarImbue) {
                return atAltar() && Inventory.find(ItemID.PURE_ESSENCE).length > 0
                        && !RcVars.get().collectPouches;

            }
            //doing steam NO imbue
            if (!RcVars.get().lava && !RcVars.get().usingLunarImbue
                    && !RcVars.get().abyssCrafting && !RcVars.get().zanarisCrafting &&
                    RunecraftBank.getLevel() >= 19) {

                return atAltar() && Inventory.find(ItemID.PURE_ESSENCE).length > 0
                        && Inventory.find(ItemID.WATER_TALISMAN).length > 0
                        && !RcVars.get().collectPouches;

            }
            //doing steam WITH Imbue
            if (!RcVars.get().lava
                    && !RcVars.get().abyssCrafting && !RcVars.get().zanarisCrafting && RunecraftBank.getLevel() >= 19) {

                return atAltar() && Inventory.find(ItemID.PURE_ESSENCE).length > 0
                        && !RcVars.get().collectPouches;
            }

            // mostly redundant with the previous one
            return atAltar() && Inventory.find(ItemID.PURE_ESSENCE).length > 0 && !RcVars.get().collectPouches;
        }
        return false;
    }


    ;

    @Override
    public void execute() {
        if (RunecraftBank.getLevel() < 14) {
            craftRunesAltar();

        } else if (RunecraftBank.getLevel() < 19) {
            craftRunesAltar(FIRE_ALTAR_TILE);

        } else if (RcVars.get().lava) {
            craftCombinationRune(ItemID.EARTH_RUNE);
            if (!Inventory.isFull()) {
                emptyPouches();
                craftCombinationRune(ItemID.EARTH_RUNE);
            }
        } else if (!RcVars.get().abyssCrafting && !RcVars.get().zanarisCrafting) {
            craftCombinationRune(ItemID.WATER_RUNE);
            if (!Inventory.isFull()) {
                emptyPouches();
                craftCombinationRune(ItemID.WATER_RUNE);
            }
        } else {
            craftRunes();
        }
    }


    @Override
    public String taskName() {
        return "Runecrafting runes";
    }
}
