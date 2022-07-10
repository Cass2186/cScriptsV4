package scripts.Tasks;

import org.tribot.script.sdk.*;
import org.tribot.api.General;

import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;

import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.GameObject;
import org.tribot.script.sdk.types.InventoryItem;
import org.tribot.script.sdk.util.TribotRandom;
import scripts.Data.Vars;
import scripts.*;
import scripts.RcApi.RcUtils;


import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Optional;

public class CraftRunes implements Task {

    String message = "Crafting Runes";

    public static List<InventoryItem> getPouches() {
        return Query.inventory().nameContains(" pouch").
                nameNotContains("Rune pouch").toList();
    }

    public static List<InventoryItem> getGiantAndMediumPouch() {
        return Query.inventory().idEquals(ItemID.GIANT_POUCH, ItemID.SMALL_POUCH).toList();
    }

    public static List<InventoryItem> getLargeAndSmallPouch() {
        return Query.inventory().idEquals(ItemID.LARGE_POUCH, ItemID.MEDIUM_POUCH).toList();
    }

    public boolean craftRunesAltar() {
        // General.sleep(500);
        Optional<GameObject> altar = Query.gameObjects().nameEquals("Altar").findClosest();

  //      if (!Inventory.isFull())
      //      emptyPouches();

        if (altar.isPresent()) {

            if (!altar.get().isVisible()) {
                Utils.setCamera(90, 40, 330, 40);
                message = "Walking to altar";
                if (PathingUtil.localNav(altar.get().getTile().translate(-1, 1)))
                    Timer.waitCondition(() -> altar.get().isVisible(), 7000, 12000);
            }


            if (altar.map(a -> a.interact("Craft-rune")).orElse(false)) {
               return Timer.slowWaitCondition(() -> MyPlayer.getAnimation() != -1, 9000, 15000);
              //  return Timer.waitCondition(() -> MyPlayer.getAnimation() == -1, 3000, 5000);
            }
        }
        return false;
    }

    public void craftRunesAltar(RSArea altarTile) {
        General.sleep(300, 500);
        Optional<GameObject> altar = RcUtils.getAltar();
        if (altar.map(a -> a.interact("Craft-rune")).orElse(false)) {
            Timer.abc2WaitCondition(() -> !Inventory.contains(ItemID.PURE_ESSENCE), 9000, 15000);
        }
    }


    public boolean emptyPouches() {
        List<InventoryItem> p = CraftRunes.getGiantAndMediumPouch();
        Keyboard.sendPress((char) KeyEvent.VK_SHIFT, 16);
        for (InventoryItem pouch : p) {
            if (pouch.click("Empty"))
                Waiting.waitNormal(60, 15);
        }
        if (Waiting.waitUntil(TribotRandom.uniform(600, 900), 50,
                () -> Inventory.getAll().size() >=18)) {
            craftRunesAltar();
        }
        p = CraftRunes.getLargeAndSmallPouch();
        for (InventoryItem pouch : p) {
            if (pouch.click("Empty"))
                Waiting.waitNormal(60, 15);
        }
        Keyboard.sendRelease((char) KeyEvent.VK_SHIFT, 16);
        Waiting.waitUniform(600, 1200);

        return true;
    }


    public boolean craftCombinationRune(int runeId) {
        Optional<GameObject> altar = RcUtils.getAltar();

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
                    Timer.waitCondition(() -> MyPlayer.getAnimation() != -1, 9000, 15000);
                    return Timer.waitCondition(() -> MyPlayer.getAnimation() == -1, 9000, 15000);
                }
                return Timer.waitCondition(() -> !Inventory.contains(ItemID.PURE_ESSENCE), 9000, 15000);
            }
        }
        return false;
    }

    public void craftRunes() {
        Optional<GameObject> altar = RcUtils.getAltar();

        if (altar.isPresent()) {
            for (int i = 0; i < 2; i++) {
                message = "Crafting Runes";
                Log.debug(message);

                if (altar.map(a -> a.interact("Craft-rune")).orElse(false)) {
                    expTimer.reset();
                    if (Vars.get().abyssCrafting || Vars.get().zanarisCrafting) {
                        if (Timer.waitCondition(() -> MyPlayer.getAnimation() != -1, 9000, 13000))
                            Timer.waitCondition(() -> MyPlayer.getAnimation() == -1, 4000, 6000);
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
        List<GameObject> altarList = Query.gameObjects().nameContains("Altar")
                .actionNotContains("Pray").toList();
        ;
        return altarList.size() > 0;
    }

    Timer expTimer = new Timer(General.random(420000, 480000));

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
        // Lava and NOT imbue
        if (Vars.get().lava && !Vars.get().usingLunarImbue) {
            return atAltar() && Inventory.contains(ItemID.PURE_ESSENCE, ItemID.EARTH_TALISMAN)
                    && !Vars.get().collectPouches;

        }
        // lava and Imbue
        if (Vars.get().lava && Vars.get().usingLunarImbue) {
            return atAltar() && Inventory.contains(ItemID.PURE_ESSENCE)
                    && !Vars.get().collectPouches;

        } else if (Vars.get().abyssCrafting) {
            return RcUtils.atAltar() &&
                    Inventory.contains(ItemID.PURE_ESSENCE);
        }

        //doing steam NO imbue
        if (!Vars.get().lava && !Vars.get().usingLunarImbue
                && !Vars.get().abyssCrafting && !Vars.get().zanarisCrafting &&
                Skill.RUNECRAFT.getActualLevel() >= 19) {
            Log.info("HERE");
            return RcUtils.atAltar() &&
                    Inventory.contains(ItemID.PURE_ESSENCE, ItemID.WATER_TALISMAN)
                    && !Vars.get().collectPouches;
        }
        //doing steam WITH Imbue
        if (!Vars.get().lava
                && !Vars.get().abyssCrafting && !Vars.get().zanarisCrafting && Skill.RUNECRAFT.getActualLevel() >= 19) {

            return RcUtils.getAltar().isPresent() && Inventory.contains(ItemID.PURE_ESSENCE)
                    && !Vars.get().collectPouches;
        }

        // mostly redundant with the previous one
        return !Bank.isNearby() && atAltar() && Inventory.contains(ItemID.PURE_ESSENCE) && !Vars.get().collectPouches;
    }

    @Override
    public void execute() {
        if (Skill.RUNECRAFT.getActualLevel() < 14) {
            craftRunesAltar();

        } else if (Skill.RUNECRAFT.getActualLevel() < 19) {
            craftRunesAltar(FIRE_ALTAR_TILE);

        } else if (Vars.get().abyssCrafting) {
            craftRunesAltar();
            emptyPouches();
            craftRunesAltar();
            Log.info("Done crafting");

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
