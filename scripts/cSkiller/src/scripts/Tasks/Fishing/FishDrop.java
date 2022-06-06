package scripts.Tasks.Fishing;

import lombok.val;
import org.tribot.api.General;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSItem;
import org.tribot.script.sdk.GameState;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.antiban.AntibanProperties;
import org.tribot.script.sdk.input.Keyboard;
import org.tribot.script.sdk.interfaces.Item;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.InventoryItem;
import org.tribot.script.sdk.util.Retry;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.EntitySelector.Entities;
import scripts.EntitySelector.finders.prefabs.ItemEntity;

import java.util.List;

public class FishDrop implements Task {


    public void setShiftDroppingPref() {
        if (!Options.isShiftClickDropEnabled()) {
            General.println("[Debug]: Setting shift click drop - enabled");
            Options.setShiftClickDropEnabled(true);
            Interfaces.closeAll();
        }
        if (!Inventory.getDroppingPattern().equals(Inventory.DROPPING_PATTERN.TOP_TO_BOTTOM)) {
            Inventory.setDroppingPattern(Inventory.DROPPING_PATTERN.TOP_TO_BOTTOM);
        }
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
        Keyboard.HoldAction.KeyHoldContext holdShiftContext = null;
        boolean waitedForShiftStart = false;
        if (isShiftDropEnabled) {
            holdShiftContext = Keyboard.hold()
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



    @Override
    public String toString() {
        return "Dropping fish";
    }


    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        return Vars.get().currentTask != null && Vars.get().currentTask.equals(SkillTasks.FISHING) &&
                Inventory.isFull();
    }

    @Override
    public void execute() {
        List<InventoryItem> raw = Query.inventory().nameContains("raw").toList();
        List<InventoryItem> leap = Query.inventory().nameContains("leaping").toList();

        setShiftDroppingPref();
        int sp = Mouse.getSpeed();
        Mouse.setSpeed(300);
       // if (raw.size() > 0)
            drop(raw);
       // if (leap.size() > 0)
           drop(leap);

        Mouse.setSpeed(sp);
    }

    @Override
    public String taskName() {
        return "Fishing";
    }
}
