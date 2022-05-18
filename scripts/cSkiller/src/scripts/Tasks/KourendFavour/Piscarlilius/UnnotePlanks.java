package scripts.Tasks.KourendFavour.Piscarlilius;

import dax.walker.utils.AccurateMouse;
import org.tribot.api.General;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.WorldHopper;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.Shop;
import scripts.*;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;

public class UnnotePlanks implements Task {

    int LEENZ = 6986;
    RSArea GENERAL_STORE_AREA = new RSArea(new RSTile(1808, 3722, 0), new RSTile(1804, 3724, 0));

    public void getItems() {
        BankManager.open(true);
        BankManager.checkEquippedGlory();
        BankManager.depositAll(true);
        BankManager.withdraw(0, true, ItemID.STEEL_NAILS);
        BankManager.withdraw(1, true, ItemID.HAMMER);
        BankManager.turnNotesOn();
        BankManager.withdraw(0, true, ItemID.PLANK);
        BankManager.withdraw(0, true, ItemID.COINS_995);

        BankManager.close(true);
    }


    public void unnotePlanks() {
        if (Inventory.find(ItemID.getNotedId(ItemID.PLANK)).length > 0 && Inventory.find(ItemID.PLANK).length < 3) {
            PathingUtil.walkToArea(GENERAL_STORE_AREA);
            if (Utils.clickNPC(LEENZ, "Trade"))
                Timer.waitCondition(Shop::isOpen, 7000);

            if (Interfaces.get(300, 16) != null && Inventory.find(ItemID.getNotedId(ItemID.PLANK)).length > 0) {
                if (AccurateMouse.click(Inventory.find(ItemID.getNotedId(ItemID.PLANK))[0], "Sell 10"))
                    General.sleep(General.random(50, 350));
                if (AccurateMouse.click(Inventory.find(ItemID.getNotedId(ItemID.PLANK))[0], "Sell 10"))
                    General.sleep(General.random(400, 800));
                for (int i = 11; i < 40; i++) {
                    if (Interfaces.get(300, 16, i).getComponentItem() == 960) {
                        if (AccurateMouse.click(Interfaces.get(300, 16, i), "Buy 50"))
                            Timer.waitCondition(() -> Inventory.find(ItemID.PLANK).length > 5, 5000, 8000);
                    }
                }
            }
        }
    }


    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return Vars.get().currentTask != null && Vars.get().currentTask.equals(SkillTasks.KOUREND_FAVOUR) &&
                (Inventory.find(ItemID.getNotedId(ItemID.PLANK)).length > 0
                        && Inventory.find(ItemID.PLANK).length < 3) ;
    }

    @Override
    public void execute() {
        unnotePlanks();
    }

    @Override
    public String toString() {
        return "Unnoting planks";
    }


    @Override
    public String taskName() {
        return "Port Piscarililius Favour";
    }
}
