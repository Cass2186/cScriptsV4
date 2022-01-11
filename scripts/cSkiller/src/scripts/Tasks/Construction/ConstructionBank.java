package scripts.Tasks.Construction;

import org.tribot.api.General;
import org.tribot.api2007.Equipment;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSItem;
import org.tribot.script.sdk.Bank;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.tasks.Amount;
import org.tribot.script.sdk.tasks.BankTask;
import org.tribot.script.sdk.tasks.EquipmentReq;
import org.tribot.script.sdk.types.InventoryItem;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.BankManager;
import scripts.Data.Enums.HerbloreItems;
import scripts.Data.SkillBank;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.ItemId;
import scripts.Requirements.ItemReq;
import scripts.Requirements.ItemRequirement;
import scripts.Tasks.MiscTasks.BuyItems;
import scripts.Timer;
import scripts.Utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ConstructionBank implements Task {

    public static boolean checkEquippedWealth() {
        if (!Equipment.isEquipped(ItemId.RING_OF_WEALTH)) {
            General.println("[Construction Training]: Getting Ring of Wealth");
            BankManager.withdrawArray(ItemId.RING_OF_WEALTH, 1);
            Utils.equipItem(ItemId.RING_OF_WEALTH);
            Timer.waitCondition(() -> Equipment.isEquipped(ItemId.RING_OF_WEALTH), 2500, 3500);
        }
        return Equipment.isEquipped(ItemId.RING_OF_WEALTH);
    }

    BankTask invTask = BankTask.builder().addInvItem(ItemId.COINS, Amount.of(100000))
            .addEquipmentItem(EquipmentReq.slot(org.tribot.script.sdk.Equipment.Slot.RING)
                    .chargedItem("Ring of wealth", 1))
            .addNotedInvItem(ItemId.PLANK+1, Amount.of(50))
            .build();


    public void getItems() {
        BankManager.open(true);
        BankManager.depositAll(true);
        checkEquippedWealth();


        List<ItemReq>   newInv = SkillBank.withdraw(FURNITURE.getRequiredItemList());
        if (newInv != null && newInv.size() > 0) {
            General.println("[Construction Training]: Creating buy list");
            BuyItems.itemsToBuy =     BuyItems.populateBuyList(FURNITURE.getRequiredItemList());
            return;
        }
        /*
        Optional<FURNITURE> furn = FURNITURE.getCurrentItem();
        if (furn.isPresent() && furn.get().getMaxLevel() < 23) {
            furn.ifPresent(h -> BankManager.withdraw(h.determineResourcesToNextItem() * 8,
                    true,ItemId.STEEL_NAILS));

        }
        Optional<FURNITURE> plankOptional = FURNITURE.getCurrentItem();
        plankOptional.ifPresent(furniture -> BankManager.withdraw(22, true, furniture.getPlankId()));



        BankManager.withdraw(1, true, ItemId.SAW);
        BankManager.withdraw(1, true, ItemId.HAMMER);


        if (BankManager.turnNotesOn())
            plankOptional.ifPresent(furniture -> BankManager.withdraw(0, true, furniture.getPlankId()));

        int planks = Query.inventory().nameContains("plank").count();
        int hammer = Query.inventory().nameContains("Hammer").count();
        int saw = Query.inventory().nameContains("Saw").count();
        if (planks < 5 ||hammer <1 ||saw <1  ) {
            General.println("[Construction Training]: Creating buy list");
            BuyItems.itemsToBuy = BuyItems.populateBuyList(FURNITURE.getRequiredItemList());
            BankManager.depositAll(true);
            return;
        }*/
        BankManager.withdraw(70000, true, ItemId.COINS);

        BankManager.close(true);
        RSItem[] tele = Inventory.find(ItemId.TELEPORT_TO_HOUSE);
        if (tele.length > 0 && tele[0].click("Outside")) {
            Waiting.waitNormal(4000, 500);
        }

    }

    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        int item = Query.inventory().nameContains("plank").count();
        int noted = Query.inventory().nameContains("plank").isNoted().count();
        return Vars.get().currentTask != null && Vars.get().currentTask.equals(SkillTasks.CONSTRUCTION) &&
                item < 5 && noted == 0;
    }

    @Override
    public void execute() {
        checkEquippedWealth();
        getItems();
    }

    @Override
    public String toString() {
        return "Banking";
    }

    @Override
    public String taskName() {
        return "Construction";
    }
}
