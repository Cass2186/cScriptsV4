package scripts.Requirements;

import lombok.Getter;
import lombok.Setter;
import org.tribot.api.General;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSItem;
import org.tribot.script.sdk.*;
import org.tribot.script.sdk.cache.BankCache;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.EquipmentItem;
import org.tribot.script.sdk.types.definitions.ItemDefinition;
import scripts.BankManager;
import scripts.Utils;

import java.awt.*;
import java.util.*;
import java.util.List;

public class InventoryRequirement implements Requirement {

    @Getter
    @Setter
    private ArrayList<ItemReq> invList = new ArrayList<>();


    @Getter
    @Setter
    private boolean depositEquipment = false;

    @Getter
    @Setter
    private ArrayList<ItemRequirement> itemRequirementList = new ArrayList<>();

    public InventoryRequirement(ArrayList<ItemReq> invList) {
        this.invList = invList;
    }

    public InventoryRequirement(ArrayList<ItemRequirement> itemRequirementList, int blank) {
        this.itemRequirementList = itemRequirementList;
    }

    /**
     * @param itemReqList
     * @return a list of itemrequirements of missing items from bank, can be used to buy items
     */
    public static List<ItemRequirement> checkBankCacheForItemList(List<ItemRequirement> itemReqList) {
        if (!BankCache.isInitialized()) {
            Log.error("BankCache needs to be updated, opening bank");
            BankManager.open(true);
            BankCache.update();
        }

        int i = 0;
        List<ItemRequirement> missingItemReqs = new ArrayList<>();

        for (ItemRequirement itemReq : itemReqList) {
            if (BankCache.getStack(itemReq.getId()) >= itemReq.getMinAmount())
                i++;
            else {
                missingItemReqs.add(itemReq);
            }
        }
        return missingItemReqs;
    }

    //TODO Use this going forward
    public ArrayList<ItemRequirement> getMissingInventoryItemsListRequirement() {
        ArrayList<ItemRequirement> missing = new ArrayList<>();
        for (ItemRequirement item : this.itemRequirementList) {

            if (item.getMinAmount() == 0) { //used to check the bank was open
                item.minAmount = 1; //this prevents it from skipping items with a min of 0 if the bank is open
                if (!item.hasItem()) {
                    missing.add(item);
                    Log.info(String.format("[InventoryReq] Missing item: %s || size of list: %s",
                            item.getId(), missing.size()));
                }
                item.minAmount = 0;
            } else if (!item.hasItem()) {
                missing.add(item);
                Log.info("[InventoryRequirement] Missing item: " + item.getId() + " ||  missing.size(): " + missing.size());
            }
        }
        return missing;
    }

    public ArrayList<ItemReq> getMissingInventoryItemsList() {
        ArrayList<ItemReq> missing = new ArrayList<>();
        for (ItemReq item : this.invList) {

            if (item.getMinAmount() == 0) { //used to check the bank was open
                item.minAmount = 1; //this prevents it from skipping items with a min of 0 if the bank is open
                if (!item.hasItem()) {
                    missing.add(item);
                    Log.info("[InventoryReq] Missing item: " + item.getId() + " || missing.size(): " + missing.size());
                }
                item.minAmount = 0;
            } else if (!item.hasItem()) {
                missing.add(item);
                Log.info("[InventoryReq] Missing item: " + item.getId() + " ||  missing.size(): " + missing.size());
            }
        }
        return missing;
    }

    //TODO Use this going forward
    public ArrayList<ItemRequirement> getMissingBankItemsListItemReq() {
        ArrayList<ItemRequirement> missing = new ArrayList<>();
        for (ItemRequirement item : this.itemRequirementList) {

            if (!Bank.isOpen())
                BankManager.open(true);

            if (Bank.isOpen() && item.getMinAmount() == 0) { //used to check the bank was open
                item.minAmount = 1; //this prevents it from skipping items with a min of 0 if the bank is open
                if (!item.hasItem()) {
                    missing.add(item);
                    Log.info("[InventoryReq] Missing item: " + item.getId() + "|| size: " + missing.size());
                }
                item.minAmount = 0;
            } else if (!item.hasItem()) {
                missing.add(item);
                Log.info("[InventoryReq] Missing item: " + item.getId() + "|| size: " + missing.size());
            }
        }
        return missing;
    }

    public ArrayList<ItemReq> getMissingBankItemsList() {
        ArrayList<ItemReq> missing = new ArrayList<>();
        for (ItemReq item : this.invList) {

            if (!Bank.isOpen())
                BankManager.open(true);

            if (Bank.isOpen() && item.getMinAmount() == 0) { //used to check the bank was open
                item.minAmount = 1; //this prevents it from skipping items with a min of 0 if the bank is open
                if (!item.hasItem()) {
                    missing.add(item);
                    Log.info("[InventoryReq] Missing item: " + item.getId() + "|| size: " + missing.size());
                }
                item.minAmount = 0;
            } else if (!item.hasItem()) {
                missing.add(item);
                Log.info("[InventoryReq] Missing item: " + item.getId() + "|| size: " + missing.size());
            }
        }
        return missing;
    }


    public void withdrawItems() {
        ArrayList<ItemReq> missing = getMissingInventoryItemsList();
        if (missing.size() > 0) {
            Log.info("[InventoryReq]: Missing size is " + missing.size());
            BankManager.open(true);
            BankManager.depositAll(true);
            if (this.depositEquipment){
                BankManager.depositEquipment();
            }
            missing = getMissingBankItemsList(); // adds everything that might have been in the inv to begin with
        }
        for (ItemReq i : missing) {
            BankCache.update();
            // Log.info("[Debug]: Withdrawing item #: " + i);
            BankManager.open(true);
            if (BankCache.getStack(i.getId()) >= i.getAmount()) {
                BankManager.withdraw(i.getAmount(), true, i.getId());
            } else if (i.getAlternateItemIDs().size() > 0) {
                Log.info("[InventoryRequirement]: Getting alternate Item");
                for (int it : i.getAlternateItemIDs()) {
                    if (BankCache.getStack(it) >= i.getAmount()) {
                        BankManager.withdraw(i.getAmount(), true, it);
                    }
                }
            }
            if (i.isShouldEquip()) {
                RSItem[] item = Inventory.find(i.getId());
                if (item.length > 0)
                    Utils.equipItem(i.getId());
            }

        }
        BankManager.close(true);

    }

    public void withdrawItemsNew() {
        ArrayList<ItemRequirement> missing = getMissingInventoryItemsListRequirement();
        if (missing.size() > 0) {
            Log.info("[InventoryReq]: Missing size is " + missing.size());
            BankManager.open(true);
            BankManager.depositAll(true);
            missing = getMissingBankItemsListItemReq(); // adds everything that might have been in the inv to begin with
        }
        for (ItemRequirement i : missing) {
            BankCache.update();
            // Log.info("[Debug]: Withdrawing item #: " + i);
            BankManager.open(true);
            if (BankCache.getStack(i.getId()) >= i.getAmount()) {
                BankManager.withdraw(i.getAmount(), true, i.getId());
            } else if (i.getAlternateItems().size() > 0) {
                Log.info("[InventoryRequirement]: Getting alternate Item");
                for (int it : i.getAlternateItems()) {
                    if (BankCache.getStack(it) >= i.getAmount()) {
                        BankManager.withdraw(i.getAmount(), true, it);
                    }
                }
            }
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


    public void add(ItemRequirement req) {
        itemRequirementList.add(req);
    }


    public void addEquipmentItem(Equipment.Slot slot, int id) {
        Optional<EquipmentItem> item = slot.getItem();
        if (item.isPresent()) {
            //correct item
            if (item.get().getId() == id) {
                return;
            }
        } else {
            invList.add(new ItemReq(id, 1, true, true));
        }
    }

    public void addEquipmentItem(Equipment.Slot slot, int id, int minAmount) {
        Optional<EquipmentItem> item = slot.getItem();
        if (item.isPresent()) {
            //correct item with more than enough
            if (item.get().getId() == id && item.get().getStack() >= minAmount) {
                return;
            } else if (item.get().getId() == id && item.get().getStack() < minAmount) { //correct item, not enough
                invList.add(new ItemReq(id, minAmount - item.get().getStack(), true, true));
            } else if (item.get().getId() != id) {

            }
        } else {
            invList.add(new ItemReq(id, minAmount, true, true));
        }
    }


    @Override
    public boolean check() {
        for (ItemReq item : this.invList) {
            if (!item.hasItem()) {
                Optional<ItemDefinition> def = Query.itemDefinitions().idEquals(item.getId()).findFirst();
                String s = String.format("[ItemRequirement]: Missing an ItemReq: %s ",
                        item.getId());
                s = def.map(b -> String.format("[ItemRequirement]: Missing an ItemReq: %s ",
                        b.getName())).orElse(s);
                Log.warn(s);
                return false;
            }
        }
        for (ItemRequirement item : this.itemRequirementList) {
            if (!item.hasItem()) {
                Optional<ItemDefinition> def = Query.itemDefinitions().idEquals(item.getId()).findFirst();

               if (def.isEmpty())
                   Log.info("Def is empty");
                   String s = String.format("[ItemRequirement]: Missing an ItemReq: %s ",
                        item.getId());
                s = def.map(b -> String.format("[ItemRequirement]: Missing an ItemReq: %s ",
                        b.getName())).orElse(s);
                Log.warn(s);
                return false;
            }
        }
        // Log.info("[InventoryRequirement]: check() returning true");
        return true;
    }
}
