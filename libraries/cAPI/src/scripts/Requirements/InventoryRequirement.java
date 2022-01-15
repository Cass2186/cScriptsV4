package scripts.Requirements;

import lombok.Getter;
import lombok.Setter;
import org.tribot.api.General;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSItem;
import org.tribot.script.sdk.Equipment;
import org.tribot.script.sdk.types.EquipmentItem;
import scripts.BankManager;
import scripts.Utils;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class InventoryRequirement implements Requirement {

    @Getter
    @Setter
    private ArrayList<ItemReq> invList;


    public InventoryRequirement(ArrayList<ItemReq> invList) {
        this.invList = invList;
    }



    public ArrayList<ItemReq> getMissingInventoryItemsList() {
        ArrayList<ItemReq> missing = new ArrayList<>();
        for (ItemReq item : this.invList) {

            if (item.getMinAmount() == 0) { //used to check the bank was open
                item.minAmount = 1; //this prevents it from skipping items with a min of 0 if the bank is open
                if (!item.hasItem()) {
                    missing.add(item);
                    General.println("[InventoryReq] Missing item: " + item.getId() + " || missing.size(): " + missing.size());
                }
                item.minAmount = 0;
            } else if (!item.hasItem()) {
                missing.add(item);
                General.println("[InventoryReq] Missing item: " + item.getId() + " ||  missing.size(): " + missing.size());
            }
        }
        return missing;
    }

    public ArrayList<ItemReq> getMissingBankItemsList() {
        ArrayList<ItemReq> missing = new ArrayList<>();
        for (ItemReq item : this.invList) {

            if (!Banking.isBankLoaded())
                BankManager.open(true);

            if (Banking.isBankLoaded() && item.getMinAmount() == 0) { //used to check the bank was open
                item.minAmount = 1; //this prevents it from skipping items with a min of 0 if the bank is open
                if (!item.hasItem()) {
                    missing.add(item);
                    General.println("[InventoryReq] Missing item: " + item.getId() + "|| size: " + missing.size());
                }
                item.minAmount = 0;
            } else if (!item.hasItem()) {
                missing.add(item);
                General.println("[InventoryReq] Missing item: " + item.getId() + "|| size: " + missing.size());
            }
        }
        return missing;
    }


    public void withdrawItems() {
        ArrayList<ItemReq> missing = getMissingInventoryItemsList();
        if (missing.size() > 0) {
            General.println("[InventoryReq]: Missing size is " + missing.size());
            BankManager.open(true);
            BankManager.depositAll(true);
            missing = getMissingBankItemsList(); // adds everything that might have been in the inv to begin with
        }
        for (ItemReq i : missing) {
            // General.println("[Debug]: Withdrawing item #: " + i);
            BankManager.open(true);
            BankManager.withdraw(i.getAmount(), true, i.getId());
            if (i.isShouldEquip()) {
                RSItem[] item = Inventory.find(i.getId());
                if (item.length > 0)
                    Utils.equipItem(i.getId());
            }

        }
        BankManager.close(true);

    }
    public void withdrawItemsNew() {
        ArrayList<ItemReq> missing = getMissingInventoryItemsList();
        if (missing.size() > 0) {
            General.println("[InventoryReq]: Missing size is " + missing.size());
            BankManager.open(true);
            BankManager.depositAll(true);
            missing = getMissingBankItemsList(); // adds everything that might have been in the inv to begin with
        }
        for (ItemReq i : missing) {
            // General.println("[Debug]: Withdrawing item #: " + i);
            BankManager.open(true);
            BankManager.withdraw(i.getAmount(), true, i.getId());
            if (i.isShouldEquip()) {
                RSItem[] item = Inventory.find(i.getId());
                if (item.length > 0)
                    Utils.equipItem(i.getId());
            }

        }
        BankManager.close(true);

    }


    public void remove(int index) {
        invList.remove(index);
    }

    public void add(ItemReq req) {
        invList.add(req);
    }



    public void addEquipmentItem(Equipment.Slot slot, int id) {
        Optional<EquipmentItem> item = slot.getItem();
        if (item.isPresent()) {
            //correct item
            if (item.get().getId() == id) {
                return;
            }
        } else {
            invList.add(new ItemReq(id, 1 ,true, true));
        }
    }

    public void addEquipmentItem(Equipment.Slot slot, int id, int minAmount) {
        Optional<EquipmentItem> item = slot.getItem();
        if (item.isPresent()) {
            //correct item with more than enough
            if (item.get().getId() == id && item.get().getStack() >= minAmount) {
                return;
            } else if (item.get().getId() == id && item.get().getStack() < minAmount) { //correct item, not enough
                invList.add(new ItemReq(id, minAmount -item.get().getStack(),true, true));
            } else if (item.get().getId() != id) {

            }
        } else {
            invList.add(new ItemReq(id, minAmount,true, true));
        }
    }

    @Override
    public boolean check() {
        for (ItemReq item : this.invList) {
            if (!item.hasItem()) {
                General.println("[ItemRequirement]: Missing an item: " + item.getId(), Color.RED);
                return false;
            }
        }

        General.println("[InventoryRequirement]: check() returning true");
        return true;
    }
}
