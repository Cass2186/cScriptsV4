package scripts.Tasks;

import org.tribot.script.sdk.GameTab;
import org.tribot.script.sdk.Inventory;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;

import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Skill;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.GameObject;
import org.tribot.script.sdk.types.InventoryItem;
import scripts.Data.Const;
import scripts.Data.Vars;
import scripts.*;


import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Optional;

public class CraftRunes implements Task {

    String message = "Crafting Runes";

    public static List<InventoryItem> getPouches() {
        return Query.inventory().nameContains(" pouch").
                nameNotContains("Rune pouch").toList();
    }

    public boolean craftRunesAltar() {
        // General.sleep(500);
        Optional<GameObject> altar = Query.gameObjects().nameEquals("Altar").findClosest();

        if (!Inventory.isFull())
            emptyPouches();

        if (altar.isPresent()) {

            if (!altar.get().isVisible()) {
                Utils.setCamera(90, 40, 330, 40);
                message = "Walking to altar";
                if (PathingUtil.localNav(altar.get().getTile().translate(-1, 1)))
                    Timer.waitCondition(() -> altar.get().isVisible(), 7000, 12000);
            }


            if (altar.map(a -> a.interact("Craft-rune")).orElse(false)) {
                Timer.slowWaitCondition(() -> Player.getAnimation() != -1, 9000, 15000);
                return Timer.waitCondition(() -> Player.getAnimation() == -1, 3000, 5000);
            }
        }
        return false;
    }

    public void craftRunesAltar(RSArea altarTile) {
        General.sleep(500);
        RSObject[] altar = Objects.findNearest(30, "Altar");
        if (altar.length > 0) {
            Walking.blindWalkTo(altarTile.getRandomTile());
            Timer.waitCondition(() -> altar[0].isClickable(), 7000, 12000);
            if (Utils.clickObject("Altar", "Craft-rune", false)) {
                Timer.abc2WaitCondition(() -> !Inventory.contains(ItemID.PURE_ESSENCE), 9000, 15000);
            }
        }
    }


    public boolean emptyPouches() {
        List<InventoryItem> p = getPouches();
        if (p.size() > 0) {
            Log.debug("Opening pouches");
            Keyboard.sendPress((char) KeyEvent.VK_SHIFT, 16);
            for (InventoryItem item : p) {
                if (item.click("Empty"))
                    Waiting.waitNormal(90, 20);
            }
            Keyboard.sendRelease((char) KeyEvent.VK_SHIFT, 16);
            Waiting.waitNormal(300, 75);
            return true;
        }
        return false;
    }

    private Optional<GameObject> getAltar() {
       return  Query.gameObjects()
                .nameContains("Altar")
                .actionNotContains("Pray")
                .actionContains("Craft-rune")
                .findClosestByPathDistance();
    }

    public boolean craftCombinationRune(int runeId) {
        Optional<GameObject> altar = getAltar();

        if (altar.isPresent()) {

            if (!altar.get().isVisible()) {
                Utils.setCamera(90, 10, 330, 30);
                message = "Walking to Altar";
                Log.debug("Walking to altar");
                if (PathingUtil.localNav(altar.get().getTile().translate(-1, 1))) {
                    if (!Vars.get().usingLunarImbue)
                        GameTab.INVENTORY.open();
                    Timer.waitCondition(() -> altar.get().isVisible(), 7000, 12000);
                }
            }
            if (Vars.get().usingLunarImbue && !Vars.get().isImbueActive && !Vars.get().abyssCrafting) {
                message = "Casting Imbue";
                castImbue();
            }

            message = "Crafting Runes";

            if (Inventory.contains(ItemID.PURE_ESSENCE)
                    && Utils.useItemOnObject(runeId, "Altar")) {

                if (!Vars.get().usingLunarImbue)
                    RunecraftBank.rodHover();

                expTimer.reset();
                if (Vars.get().abyssCrafting) {
                    Timer.waitCondition(() -> Player.getAnimation() != -1, 9000, 15000);
                    return Timer.waitCondition(() -> Player.getAnimation() == -1, 9000, 15000);
                }
                return Timer.waitCondition(() -> !Inventory.contains(ItemID.PURE_ESSENCE), 9000, 15000);
            }
        }
        return false;
    }

    public void craftRunes() {
        Optional<GameObject> altar = getAltar();

        if (altar.isPresent()) {
            for (int i = 0; i < 2; i++) {
                message = "Crafting Runes";
                Log.debug(message);

                if (altar.map(a -> a.interact("Craft-rune")).orElse(false)) {
                    expTimer.reset();
                    if (Vars.get().abyssCrafting || Vars.get().zanarisCrafting) {
                        if (Timer.waitCondition(() -> Player.getAnimation() != -1, 9000, 15000))
                            Timer.waitCondition(() -> Player.getAnimation() == -1, 9000, 15000);
                    } else
                        Timer.waitCondition(() -> !Inventory.contains(ItemID.PURE_ESSENCE), 9000, 15000);
                }
                if (!Inventory.isFull())
                    emptyPouches();

                if (!Inventory.contains(ItemID.PURE_ESSENCE))
                    break;
            }
            if (Vars.get().abyssCrafting)
                RunecraftBank.gloryTele("Edgeville");
        }
    }

    public static RSArea FIRE_ALTAR_TILE = new RSArea(new RSTile(2583, 4840, 0), 3);




    public static boolean castImbue() {
        return Vars.get().isImbueActive || Magic.selectSpell("Magic Imbue");
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
        // Lava and NOT imbue
        if (Vars.get().lava && !Vars.get().usingLunarImbue) {
            return atAltar() && Inventory.contains(ItemID.PURE_ESSENCE, ItemID.EARTH_TALISMAN)
                    && !Vars.get().collectPouches;

        }
        // lava and Imbue
        if (Vars.get().lava && Vars.get().usingLunarImbue) {
            return atAltar() && Inventory.contains(ItemID.PURE_ESSENCE)
                    && !Vars.get().collectPouches;

        }
        //doing steam NO imbue
        if (!Vars.get().lava && !Vars.get().usingLunarImbue
                && !Vars.get().abyssCrafting && !Vars.get().zanarisCrafting &&
                Skill.RUNECRAFT.getActualLevel() >= 19) {

            return atAltar() && Inventory.contains(ItemID.PURE_ESSENCE, ItemID.WATER_TALISMAN)
                    && !Vars.get().collectPouches;

        }
        //doing steam WITH Imbue
        if (!Vars.get().lava
                && !Vars.get().abyssCrafting && !Vars.get().zanarisCrafting && Skill.RUNECRAFT.getActualLevel() >= 19) {

            return atAltar() && Inventory.contains(ItemID.PURE_ESSENCE)
                    && !Vars.get().collectPouches;
        }

        // mostly redundant with the previous one
        return atAltar() && Inventory.contains(ItemID.PURE_ESSENCE) && !Vars.get().collectPouches;
    }

    @Override
    public void execute() {
        if (Skill.RUNECRAFT.getActualLevel() < 14) {
            craftRunesAltar();

        } else if (Skill.RUNECRAFT.getActualLevel() < 19) {
            craftRunesAltar(FIRE_ALTAR_TILE);

        } else if (Vars.get().lava) {
            craftCombinationRune(ItemID.EARTH_RUNE);
            if (!Inventory.isFull()) {
                emptyPouches();
                craftCombinationRune(ItemID.EARTH_RUNE);
            }
        } else if (!Vars.get().abyssCrafting && !Vars.get().zanarisCrafting) {
            craftCombinationRune(ItemID.WATER_RUNE);
            if (!Inventory.isFull()) {
                emptyPouches();
                craftCombinationRune(ItemID.WATER_RUNE);
            }
        } else {
            craftRunes();
        }
    }

}
