package scripts.Tasks.Firemaking;

import org.tribot.api.General;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSItem;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.BankManager;
import scripts.Data.SkillBank;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.EntitySelector.Entities;
import scripts.EntitySelector.finders.prefabs.ItemEntity;
import scripts.ItemID;
import scripts.Requirements.ItemReq;
import scripts.Tasks.MiscTasks.BuyItems;
import scripts.Timer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BankFireMaking implements Task {

    public boolean hasItems() {
        RSItem tinderbox = Entities.find(ItemEntity::new)
                .nameContains("Tinderbox")
                .getFirstResult();

        RSItem logs = Entities.find(ItemEntity::new)
                .idEquals(MakeFires.getCurrentLogID())
                .getFirstResult();

        return tinderbox != null && logs != null;
    }



    public void getLog(int logId) {
        BankManager.open(true);

        if (Banking.isBankScreenOpen() && Banking.find(logId).length < 1) {
            General.println("[Debug]: Missing Log.");
            //  cSkiller.changeRunningBool(false); //ends script
        }
        if (BankManager.depositAllExcept(false, ItemID.TINDERBOX))
            Timer.waitCondition(() -> Inventory.getAll().length == 1, 2500, 3500);
        List<ItemReq> inv = new ArrayList<>(
                Arrays.asList(
                        new ItemReq(ItemID.TINDERBOX, 1),
                        new ItemReq(logId, 0)

                )
        );
        General.println("[BankFireMaking]: getLog() inv.size() is: " + inv.size());

        List<ItemReq> newInv = SkillBank.withdraw(inv);
        if (newInv != null && newInv.size() > 0) {
            General.println("[Firemaking Training]: Creating buy list");
            BuyItems.itemsToBuy = BuyItems.populateBuyList(newInv);
        }
    }


    public void bank() {
        BankManager.open(true);

        //   BankManager.depositAllExcept(true, ItemID.TINDERBOX);

        if (Skills.getCurrentLevel(Skills.SKILLS.FIREMAKING) < 15) {
            getLog(ItemID.LOG_IDS[0]);
        } else if (Skills.getCurrentLevel(Skills.SKILLS.FIREMAKING) < 30) {
            getLog(ItemID.LOG_IDS[1]);
        } else if (Skills.getCurrentLevel(Skills.SKILLS.FIREMAKING) < 45) {
            getLog(ItemID.LOG_IDS[2]);
        } else if (Skills.getCurrentLevel(Skills.SKILLS.FIREMAKING) < 60) {
            getLog(ItemID.LOG_IDS[3]);
        } else if (Skills.getCurrentLevel(Skills.SKILLS.FIREMAKING) < 99) {
            getLog(ItemID.LOG_IDS[4]);
        }
        BankManager.close(true);
    }

    @Override
    public String toString() {
        return "Banking - FM";
    }

    @Override
    public Priority priority() {
        return Priority.HIGH;
    }

    @Override
    public boolean validate() {
        return Vars.get().currentTask != null &&
                Vars.get().currentTask.equals(SkillTasks.FIREMAKING)
                && !hasItems();
    }

    @Override
    public void execute() {
        bank();
    }

    @Override
    public String taskName() {
        return "FireMaking";
    }
}
