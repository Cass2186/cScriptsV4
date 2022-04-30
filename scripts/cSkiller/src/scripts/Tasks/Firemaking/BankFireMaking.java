package scripts.Tasks.Firemaking;

import org.tribot.api.General;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSItem;
import org.tribot.script.sdk.Bank;
import org.tribot.script.sdk.GrandExchange;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.Npc;
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

    private boolean openGEBank(){
        if (Bank.isOpen())
            return true;

        Optional<Npc> bank = Query.npcs().actionContains("Bank").findBestInteractable();
        return bank.map(b->b.interact("Bank")).orElse(false) &&
                Waiting.waitUntil(4500, Bank::isOpen);
    }

    public void getLog(int logId) {
        openGEBank();

        List<ItemReq> inv = new ArrayList<>(
                Arrays.asList(
                        new ItemReq(ItemID.TINDERBOX, 1),
                        new ItemReq(logId, 0)

                )
        );
        if (!GrandExchange.isNearby()){
            Log.info("Not at GE, getting RoW");
            inv = new ArrayList<>(
                    Arrays.asList(
                            new ItemReq(ItemID.TINDERBOX, 1),
                            new ItemReq(ItemID.RING_OF_WEALTH, 1),
                            new ItemReq(logId, 0)
                    )
            );
        }
        if (BankManager.depositAllExceptNew(false, ItemID.TINDERBOX))
            Timer.waitCondition(() -> Inventory.getAll().length == 1, 2500, 3500);

        General.println("[BankFireMaking]: getLog() inv.size() is: " + inv.size());

        List<ItemReq> newInv = SkillBank.withdraw(inv);
        if (newInv != null && newInv.size() > 0) {
            General.println("[Firemaking Training]: Creating buy list");
            BuyItems.itemsToBuy = BuyItems.populateBuyList(newInv);
        }
    }


    public void bank() {
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
