package scripts.Tasks.Crafting;

import org.tribot.api.General;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.BankManager;
import scripts.Data.Enums.Crafting.CraftItems;
import scripts.Data.SkillBank;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.ItemID;
import scripts.Requirements.ItemReq;
import scripts.Tasks.MiscTasks.BuyItems;
import scripts.Timer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CraftBank implements Task {

    public void withdrawGlass() {
      /*  if (Inventory.find(ItemID.GLASSBLOWING_PIPE).length < 1) {
            BankManager.open(true);
            if (Banking.find(ItemID.GLASSBLOWING_PIPE).length < 1) {
                General.println("[Debug]: Missing glassblowing pipe.");
                cSkiller.changeRunningBool(false); //ends script
            }
            BankManager.depositAll(true);
            BankManager.withdraw(1, ItemID.GLASSBLOWING_PIPE, true);
        }*/

        if (Inventory.find(ItemID.MOLTEN_GLASS).length == 0 ||
                Inventory.find(ItemID.GLASSBLOWING_PIPE).length == 0) {
            BankManager.open(true);
            if (BankManager.depositAllExceptNew(false, ItemID.GLASSBLOWING_PIPE))
                Timer.waitCondition(() -> Inventory.getAll().length == 1, 2500, 3500); // this is faster than making deposite true
            List<ItemReq> inv;
            if (Inventory.find(ItemID.GLASSBLOWING_PIPE).length < 1) {
                inv = new ArrayList<>(
                        Arrays.asList(
                                new ItemReq(ItemID.GLASSBLOWING_PIPE, 1),
                                new ItemReq(ItemID.MOLTEN_GLASS, 0)
                        )
                );
            } else {
                inv = new ArrayList<>(List.of(new ItemReq(ItemID.MOLTEN_GLASS, 0)));
            }


            List<ItemReq> newInv = SkillBank.withdraw(inv);
            if (newInv != null && newInv.size() > 0) {
                General.println("[Crafting Training]: Creating buy list");
                BuyItems.itemsToBuy = BuyItems.populateBuyList(CraftItems.getRequiredItemList());
                return;
            }
            BankManager.close(true);
        }
    }


    @Override
    public String toString() {
        return "Banking - Crafting";
    }

    @Override
    public Priority priority() {
        return Priority.HIGH;
    }

    @Override
    public boolean validate() {
        return Vars.get().currentTask != null && Vars.get().currentTask.equals(SkillTasks.CRAFTING)
                && !Vars.get().doingDragonHide &&
                (Inventory.find(ItemID.MOLTEN_GLASS).length == 0 ||
                        Inventory.find(ItemID.GLASSBLOWING_PIPE).length == 0);
    }

    @Override
    public void execute() {
        withdrawGlass();
    }

    @Override
    public String taskName() {
        return "Crafting";
    }

}
