package scripts.Tasks.Cooking;

import org.tribot.api.General;
import org.tribot.api.input.Keyboard;
import org.tribot.api2007.*;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.MakeScreen;
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
            Timer.waitCondition(MakeScreen::isOpen, 2500, 4500);

        if (MakeScreen.isOpen())
            Keyboard.typeString(" ");

        Timer.abc2SkillingWaitCondition(() -> Inventory.find(Filters.Items.nameContains("Grapes")).length < 1
                || Inventory.find("Jug of water").length < 1, 65000, 75000);

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
            makeWine();
        } else
            cook();
    }

    @Override
    public String taskName() {
        return "Cooking";
    }
}
