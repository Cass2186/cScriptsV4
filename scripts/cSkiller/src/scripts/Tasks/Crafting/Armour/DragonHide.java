package scripts.Tasks.Crafting.Armour;


import org.tribot.api.General;
import org.tribot.script.sdk.Inventory;
import org.tribot.script.sdk.Log;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.BankManager;
import scripts.Data.Enums.Crafting.CraftItems;
import scripts.Data.SkillBank;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.ItemID;
import scripts.Requirements.InventoryRequirement;
import scripts.Requirements.ItemReq;
import scripts.Requirements.ItemRequirement;
import scripts.Tasks.MiscTasks.BuyItems;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DragonHide implements Task {


    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return Vars.get().currentTask != null &&
                Vars.get().currentTask.equals(SkillTasks.CRAFTING) &&
                Vars.get().doingDragonHide;
    }


    @Override
    public void execute() {
        Optional<Armour> bestItem = Armour.getBestItem();
        if (bestItem.map(Armour::hasRequiredItems).orElse(false)) {
            // we have the items to make the armour
            Log.info("Making item");
            bestItem.ifPresent(Armour::makeItem);

        } else if (bestItem.isPresent()) {
            //TODO add buying
            Log.info("Banking for D'hide items");
            BankManager.open(true);
            List<Integer> itemIdList = new ArrayList<>();
            for (ItemReq i : bestItem.get().getItemReqList()){
                itemIdList.add(i.getId());
            }
            BankManager.depositAllExcept(ItemID.NEEDLE, ItemID.THREAD);
            List<ItemReq> newInv = SkillBank.withdraw(bestItem.get().getItemReqList());
            if (newInv != null && newInv.size() > 0) {
                Log.warn("[Crafting Dragon Hide]: Creating buy list");
                BuyItems.itemsToBuy = BuyItems.populateBuyList(bestItem.get().getRequiredItemList());
            } else
                BankManager.close(true);
            // need to bank
        } else if (bestItem.isEmpty()) {
            Log.warn("Item Optional is empty");
        } else
            Log.warn("Unknown error");
    }

    @Override
    public String toString() {
        return "Making D'hide";
    }
    @Override
    public String taskName() {
        return "Crafting";
    }
}
