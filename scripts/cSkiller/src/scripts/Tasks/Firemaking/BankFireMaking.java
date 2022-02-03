package scripts.Tasks.Firemaking;

import org.tribot.api.General;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSItem;
import org.tribot.script.sdk.tasks.BankTaskError;
import org.tribot.script.sdk.tasks.InsufficientItemError;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.BankManager;
import scripts.Data.SkillBank;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.EntitySelector.Entities;
import scripts.EntitySelector.finders.prefabs.ItemEntity;
import scripts.ItemId;
import scripts.Requirements.ItemReq;
import scripts.Tasks.MiscTasks.BuyItems;
import scripts.Timer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
        if (BankManager.depositAllExcept(false, ItemId.TINDERBOX))
            Timer.waitCondition(() -> Inventory.getAll().length == 1, 2500, 3500);
        List<ItemReq> inv = new ArrayList<>(
                Arrays.asList(
                        new ItemReq(ItemId.TINDERBOX, 1),
                        new ItemReq(logId, 0)

                )
        );
        General.println("[BankFireMaking]: getLog() inv.size() is: " + inv.size());

        inv = SkillBank.withdraw(inv);
        if (inv != null && inv.size() > 0) {
            General.println("[Firemaking Training]: Creating buy list");
            BuyItems.itemsToBuy = BuyItems.populateBuyList(inv);
        }
    }


    public void bank() {
        BankManager.open(true);

        //   BankManager.depositAllExcept(true, ItemId.TINDERBOX);

        if (Skills.getCurrentLevel(Skills.SKILLS.FIREMAKING) < 15) {
            getLog(ItemId.LOG_IDS[0]);
        } else if (Skills.getCurrentLevel(Skills.SKILLS.FIREMAKING) < 30) {
            getLog(ItemId.LOG_IDS[1]);
        } else if (Skills.getCurrentLevel(Skills.SKILLS.FIREMAKING) < 45) {
            getLog(ItemId.LOG_IDS[2]);
        } else if (Skills.getCurrentLevel(Skills.SKILLS.FIREMAKING) < 60) {
            getLog(ItemId.LOG_IDS[3]);
        } else if (Skills.getCurrentLevel(Skills.SKILLS.FIREMAKING) < 99) {
            getLog(ItemId.LOG_IDS[4]);
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