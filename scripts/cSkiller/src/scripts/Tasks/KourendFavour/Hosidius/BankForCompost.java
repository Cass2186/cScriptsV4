package scripts.Tasks.KourendFavour.Hosidius;

import org.tribot.api.General;
import org.tribot.script.sdk.Inventory;
import org.tribot.script.sdk.cache.BankCache;
import org.tribot.script.sdk.tasks.Amount;
import org.tribot.script.sdk.tasks.BankTask;
import org.tribot.script.sdk.tasks.BankTaskError;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.BankManager;
import scripts.Data.Enums.FletchingItem;
import scripts.Data.SkillBank;
import scripts.Data.Vars;
import scripts.ItemID;
import scripts.Requirements.ItemReq;
import scripts.Tasks.MiscTasks.BuyItems;
import scripts.Utils;
import scripts.Varbits;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class BankForCompost implements Task {

    public void bank() {
        List<ItemReq> inv = new ArrayList<>(
                Arrays.asList(
                        new ItemReq(ItemID.SALTPETRE, 14),
                        new ItemReq(ItemID.COMPOST, 14)
                )
        );
        BankManager.open(true);
        BankManager.depositAll(true);
        BankCache.update();
        if (shouldCashInFavour()) {
            Vars.get().shouldCashInCompost = true;
            return;
        }
        List<ItemReq> newInv = SkillBank.withdraw(inv);
        if (newInv != null && newInv.size() > 0) {
            General.println("[Hosidius Favour]: Creating buy list");
            int amount =1000- Utils.getVarBitValue(Varbits.KOUREND_FAVOR_HOSIDIUS.getId());
            BuyItems.itemsToBuy = BuyItems.populateBuyList( new ArrayList<>(
                    Arrays.asList(
                            new ItemReq(ItemID.SALTPETRE, amount),
                            new ItemReq(ItemID.COMPOST, amount)))
            );
            return;
        }
        BankManager.close(true);
    }


    private boolean shouldCashInFavour(){
        int amount =1000- Utils.getVarBitValue(Varbits.KOUREND_FAVOR_HOSIDIUS.getId());
        return BankCache.getStack(ItemID.SULPHUROUS_FERTILISER) >= amount;
    }

    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        return Vars.get().hosaFavour &&
                (!Inventory.contains(ItemID.COMPOST) || !Inventory.contains(ItemID.SALTPETRE));
    }

    @Override
    public void execute() {
        bank();
    }


    @Override
    public String toString() {
        return "Banking";
    }

    @Override
    public String taskName() {
        return "Hosidius Favour";
    }
}
