package scripts.Tasks.Cooking.CookTasks;

import org.tribot.api.General;
import org.tribot.api.input.Keyboard;
import org.tribot.api2007.*;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;
import org.tribot.script.sdk.ChatScreen;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.MakeScreen;
import org.tribot.script.sdk.Waiting;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.BankManager;
import scripts.Data.Enums.CookItems;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.ItemID;
import scripts.Timer;
import scripts.Utils;

public class CookFood implements Task {

    public static boolean hasRawFood() {
        RSItem[] food = Inventory.find(CookItems.getCookingRawFoodId());
        RSItem[] grapes = Inventory.find(Filters.Items.nameContains("Grapes"));
        RSItem[] jug = Inventory.find(Filters.Items.nameContains("Jug of water"));
        return Skills.getActualLevel(Skills.SKILLS.COOKING) >= 35 ?
                (grapes.length > 0 && jug.length > 0) : food.length > 0;
    }

    public RSObject findGrill() {
        RSObject[] obj = Objects.findNearest(20, Filters.Objects.actionsContains("Cook"));
        return obj.length > 0 ? obj[0] : null;
    }

    public void cook() {
        if (Banking.isBankScreenOpen())
            BankManager.close(true);

        RSItem[] food = Inventory.find(CookItems.getCookingRawFoodId());
        RSObject grill = findGrill();
        if (food.length > 0 && grill != null) {
            if (Utils.clickObject(grill, "Cook", false)) {
                Timer.waitCondition(MakeScreen::isOpen, 6500, 8000);
            }
            if (MakeScreen.isOpen()) {
                General.sleep(General.randomSD(25, 500, 175, 75));
                if (!MakeScreen.makeAll(food[0].getID())) {
                    Log.log("[Debug]: MakeAll() failed");
                    if (Interfaces.get(270, 14) != null) //acts as a failsafe
                        Keyboard.typeKeys(scripts.Keyboard.SPACEBAR);
                }
                Timer.abc2SkillingWaitCondition(() -> Inventory.find(Filters.Items.nameContains("Raw"))
                        .length < 1, 65000, 75000);
            }
        }
    }

    public void makeWine() {
        if (Utils.useItemOnItem(ItemID.GRAPES, ItemID.JUG_OF_WATER))
            Waiting.waitUntil(4000, Utils.random(200, 800), MakeScreen::isOpen);

        if (MakeScreen.isOpen() && MakeScreen.makeAll(ItemID.UNFERMENTED_WINE) &&
                Waiting.waitUntil(40000, Utils.random(400, 800), () ->
                        Inventory.find(ItemID.GRAPES).length < 1 || ChatScreen.isOpen()
                                || Inventory.find("Jug of water").length < 1)) {
            if (Utils.random(0, 100) < 65) //65% chance for normal idle
                Utils.idleNormalAction(true);
            else
                Utils.idleAfkAction();
        }
    }


    @Override
    public String toString() {
        return "Cooking food";
    }

    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        return Vars.get().currentTask != null && Vars.get().currentTask.equals(SkillTasks.COOKING) && hasRawFood();
    }

    @Override
    public void execute() {
        if (Skills.getActualLevel(Skills.SKILLS.COOKING) >= 35) {
            //can modify abc2 delay here
            Utils.FACTOR = 0.25;
            makeWine();
        } else
            cook();
    }

    @Override
    public String taskName() {
        return "Cooking";
    }
}
