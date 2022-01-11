package scripts.Tasks.Fletching;

import org.tribot.api.General;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSItem;
import org.tribot.script.sdk.Waiting;
import scripts.*;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;

public class Fletching implements Task {

    String message;

    public void makeItemAll(int item1, int item2, String itemName) {
        message = "Fletching " + itemName;
        if (Banking.isBankScreenOpen())
            BankManager.close(true);

        if (Utils.useItemOnItem(item1, item2))
            Timer.waitCondition(() -> Interfaces.get(270) != null, 7000, 10000);

        if (Interfaces.get(270) != null) {
            General.sleep(100,300);
            InterfaceUtil.getInterfaceKeyAndPress(270, itemName);
            Timer.abc2SkillingWaitCondition(() -> (Interfaces.get(233, 2) != null
                    || Inventory.find(item2).length < 1 || Inventory.find(item1).length < 1), 60000, 75000);
        }

    }

    public void makeItemAllStackable(int item1, int item2, String itemName) {
        message = "Fletching " + itemName;
        if (Banking.isBankScreenOpen())
            BankManager.close(true);

        if (Utils.useItemOnItem(item1, item2))
            Timer.waitCondition(() -> Interfaces.get(270) != null, 7000, 10000);
        RSItem[] item = Inventory.find(item1);
        if (Interfaces.get(270) != null && item.length > 0) {
            Waiting.waitNormal(120,55);
            int stack =item[0].getStack();
            InterfaceUtil.getInterfaceKeyAndPress(270, itemName);
            Timer.abc2SkillingWaitCondition(() -> (Interfaces.get(233, 2) != null
                    || Inventory.find(item2).length < 1 || Inventory.find(item1).length < 1 ||
                    Inventory.find(item1)[0].getStack() == stack-150), 60000, 75000);
        }

    }

    public void bank(int item) {
        if (Inventory.find(item).length < 1) {
            message = "Banking...";
            General.println("[Debug]: " + message);
            BankManager.open(true);
            BankManager.depositAllExcept(true, ItemId.KNIFE, ItemId.FEATHERS);
            BankManager.withdraw(0, true, item);
            BankManager.close(true);
        }
    }


    public void fletchingMethod() {
        if (Skills.getActualLevel(Skills.SKILLS.FLETCHING) < 20) {
            makeItemAllStackable(ItemId.FEATHERS, ItemId.ARROW_SHAFT, "Headless arrow");
            bank(ItemId.ARROW_SHAFT);
        } else if (Skills.getActualLevel(Skills.SKILLS.FLETCHING) < 25) {
            makeItemAll(ItemId.KNIFE, ItemId.LOG_IDS[1], "Oak shortbow");
            bank(ItemId.LOG_IDS[1]);
        } else if (Skills.getActualLevel(Skills.SKILLS.FLETCHING) < 35) {
            makeItemAll(ItemId.KNIFE, ItemId.LOG_IDS[1], "Oak longbow");
            bank(ItemId.LOG_IDS[1]);
        } else if (Skills.getActualLevel(Skills.SKILLS.FLETCHING) < 40) { //willow short
            makeItemAll(ItemId.KNIFE, ItemId.LOG_IDS[2], "Willow shortbow");
            bank(ItemId.LOG_IDS[2]);
        } else if (Skills.getActualLevel(Skills.SKILLS.FLETCHING) < 50) { //willow long
            makeItemAll(ItemId.KNIFE, ItemId.LOG_IDS[2], "Willow longbow");
            bank(ItemId.LOG_IDS[2]);
        } else if (Skills.getActualLevel(Skills.SKILLS.FLETCHING) < 55) { //maple shortbow
            makeItemAll(ItemId.KNIFE,ItemId. LOG_IDS[3], "Maple shortbow");
            bank(ItemId.LOG_IDS[3]);
        } else if (Skills.getActualLevel(Skills.SKILLS.FLETCHING) < 65) { //maple longbow
            makeItemAll(ItemId.KNIFE, ItemId.LOG_IDS[3], "Maple longbow");
            bank(ItemId.LOG_IDS[3]);
        } else if (Skills.getActualLevel(Skills.SKILLS.FLETCHING) < 70) {
            makeItemAll(ItemId.KNIFE, ItemId.LOG_IDS[4], "Yew shortbow");// yew short
            bank(ItemId.LOG_IDS[4]);
        } else if (Skills.getActualLevel(Skills.SKILLS.FLETCHING) < 75) {
            makeItemAll(ItemId.KNIFE, ItemId.LOG_IDS[4], "Yew longbow");// yew long
            bank(ItemId.LOG_IDS[4]);
        }
    }

    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return Vars.get().currentTask != null  && Vars.get().currentTask.equals(SkillTasks.FLETCHING)
                && SkillTasks.FLETCHING.isWithinLevelRange();
    }

    @Override
    public void execute() {
        fletchingMethod();
    }

    @Override
    public String taskName() {
        return "Fletching";
    }

    @Override
    public String toString(){
        return message;
    }

}
