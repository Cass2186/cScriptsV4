package scripts;

import lombok.val;
import org.tribot.api.General;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.ChooseOption;
import org.tribot.api2007.Game;
import org.tribot.script.sdk.Equipment;
import org.tribot.script.sdk.GameState;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.antiban.AntibanProperties;
import org.tribot.script.sdk.cache.BankCache;
import org.tribot.script.sdk.input.Keyboard;
import org.tribot.script.sdk.interfaces.Item;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.InventoryItem;
import org.tribot.script.sdk.types.definitions.ItemDefinition;
import org.tribot.script.sdk.util.Retry;

import java.util.List;
import java.util.Optional;

public class ItemUtil {


    public static boolean clickInventoryItem(int itemId) {
        return QueryUtils.getItem(itemId).map(InventoryItem::click).orElse(false);
    }

    public static boolean clickInventoryItem(int itemId, String string) {
        return QueryUtils.getItem(itemId).map(i -> i.click(string)).orElse(false);
    }

    public static boolean equipItem(Optional<InventoryItem> itemOptional) {
        if (itemOptional.isEmpty())
            return false;

        if (itemOptional.map(i -> i.getDefinition().isStackable() &&
                Equipment.contains(i.getId())).orElse(false))
            return true;//already equipped and not stackable (e.g. arrows)

        ItemDefinition def = itemOptional.get().getDefinition();
        if (def.getActions().stream().anyMatch(s -> s.contains("Wield")) &&
                itemOptional.map(i -> i.click("Wield")).orElse(false)) {
            return Timer.waitCondition(() -> Equipment.contains(def.getId()), 1200, 2000);
        } else if (def.getActions().stream().anyMatch(s -> s.contains("Wear")) &&
                itemOptional.map(i -> i.click("Wear")).orElse(false)) {
            return Timer.waitCondition(() -> Equipment.contains(def.getId()), 1200, 2000);
        } else if (itemOptional.map(InventoryItem::click).orElse(false)) {
            return Timer.waitCondition(() -> Equipment.contains(def.getId()), 1200, 2000);

        }
        return false;
    }

    private static int getDoses(String basename, String doseString) {
        String full = String.format("%s(%s)", basename, doseString);
        Log.info("Looking for potion: " + full);
        int count = Query.inventory().nameContains(basename)
                .filter(it->it.getName().contains(doseString)).count();
        switch (doseString) {
            case ("1"):
                return count;
            case ("2"):
                return count * 2;
            case ("3"):
                return count * 3;
        }
        return 0;
    }

    public static int getDosesOfPotion(String basename) {
        int oneDose = getDoses(basename, "1");
        int twoDose = getDoses(basename, "2");
        int threeDose =getDoses(basename, "3");
        int fourDose = getDoses(basename, "4");
        int doses = (oneDose + twoDose + threeDose + fourDose);
        Log.info("We have x" + doses + " doses of " + basename);
        return doses;
    }

    /**
     * Returns an array of 28 Items based on the indexes of the given items. Array indexes without a corresponding Item
     * will have null values.
     */
    private static Item[] getAsInventory(List<? extends Item> items) {
        Item[] out = new Item[28];
        for (Item item : items) {
            int i = item.getIndex();
            if (i >= 0 && i <= 27)
                out[i] = item;
        }
        return out;
    }

    public static int drop(List<? extends Item> items) {
        if (items.isEmpty()) {
            return 0;
        }

        val antibanProps = AntibanProperties.getPropsForCurrentChar();
        val dropPattern = antibanProps.determineDropPattern();
        val isShiftDropEnabled = org.tribot.script.sdk.Options.isShiftClickDropEnabled();
        val invItems = getAsInventory(items);

        // If we should be shift dropping, let's first hold shift
        org.tribot.script.sdk.input.Keyboard.HoldAction.KeyHoldContext holdShiftContext = null;
        boolean waitedForShiftStart = false;
        if (isShiftDropEnabled) {
            holdShiftContext = org.tribot.script.sdk.input.Keyboard.hold()
                    .key(Keyboard.HoldAction.Key.SHIFT)
                    .timeout(2 * 60 * 1000)
                    .start();
            Waiting.waitNormal(110, 15);
        }

        var dropped = 0;

        for (int index : dropPattern.getDropList()) {
            val itemToDrop = invItems[index];

            if (itemToDrop != null) {

                // If an item is selected, we need to deselect it by opening the chooseoption menu and cancelling it
                if (GameState.isAnyItemSelected()) {
                    if (!ChooseOption.isOpen()) {
                        val openedChooseOption = Retry.retry(General.random(2, 4), () -> {
                            Mouse.click(3);
                            Waiting.waitNormal(180, 20);
                            return ChooseOption.isOpen();
                        });

                        // If we can't open the chooseoption menu, return early (failure)
                        if (!openedChooseOption) {
                            return dropped;
                        }
                    }

                    ChooseOption.select("Cancel");
                    Waiting.waitNormal(290, 35);
                }

                // If we just started, lets try to hover the item while waiting for the key to be held.
                // Otherwise if it hasn't started, wait a little longer and hope it starts soon.
                if (!waitedForShiftStart && holdShiftContext != null && !holdShiftContext.isStarted()) {
                    itemToDrop.hover();
                    val finalCtx = holdShiftContext;
                    Waiting.waitUntil(() -> finalCtx.isStarted() || Game.getUptext().startsWith("Drop"));
                    waitedForShiftStart = true;
                }

                if (itemToDrop.click("Drop")) {
                    dropped += itemToDrop.getStack();
                    Waiting.waitNormal(15, 5);
                }
            }
        }

        if (holdShiftContext != null) {
            holdShiftContext.stop();
        }

        return dropped;
    }
}
