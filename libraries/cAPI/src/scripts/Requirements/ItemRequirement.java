package scripts.Requirements;

import lombok.Getter;
import lombok.Setter;
import org.tribot.api.General;
import org.tribot.api2007.Equipment;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSItemDefinition;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.cache.BankCache;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.EquipmentItem;
import org.tribot.script.sdk.types.InventoryItem;
import org.tribot.script.sdk.types.definitions.ItemDefinition;
import scripts.Utils;

import java.util.*;
import java.util.stream.Collectors;

public class ItemRequirement implements Requirement {


    @Getter
    private String itemName;

    @Getter
    private int chargesNeeded;

    @Getter
    private int id;

    @Getter
    @Setter
    private List<Integer> alternateItems = new ArrayList<>();


    @Getter
    @Setter
    public boolean acceptNoted;

    @Getter
    @Setter
    private int amount;

    @Getter
    @Setter
    public int minAmount;

    @Getter
    @Setter
    private boolean acceptEquipped;

    @Getter
    @Setter
    private boolean shouldEquip;


    @Setter
    protected boolean exclusiveToOneItemType;

    @Setter
    @Getter
    private boolean displayMatchedItemName;

    @Setter
    @Getter
    private Requirement conditionToHide;


    public ItemRequirement(int ItemID) {
        this.id = ItemID;
        this.amount = 1;
        this.minAmount = 1;
    }


    public ItemRequirement(int ItemID, int amount) {
        this.id = ItemID;
        this.amount = amount;
        this.minAmount = amount;
    }


    public ItemRequirement(String name, int ItemID, int amount) {
        this.itemName = name;
        this.id = ItemID;
        this.amount = amount;
        this.minAmount = amount;
    }


    public ItemRequirement(String name, int ItemID) {
        this.itemName = name;
        this.id = ItemID;
        this.amount = 1;
        this.minAmount = 1;
    }


    public ItemRequirement(String name, List<Integer> ItemIDs) {
        this(name, ItemIDs.get(0), 1);
        this.addAlternateItemID(ItemIDs.subList(1, ItemIDs.size()));
    }

    @lombok.Builder
    public ItemRequirement(List<Integer> ItemIDs, int num) {
        this(ItemIDs.get(0), num);
        this.addAlternateItemID(ItemIDs.subList(1, ItemIDs.size()));
    }

    @lombok.Builder
    public ItemRequirement(int ItemID, int amount, boolean acceptEquipped) {
        this(ItemID, amount);
        this.acceptEquipped = acceptEquipped;
    }

    @lombok.Builder
    public ItemRequirement(int ItemID, int amount, boolean acceptEquipped, boolean shouldEquip) {
        this(ItemID, amount);
        this.acceptEquipped = acceptEquipped;
        this.shouldEquip = shouldEquip;
    }

    @lombok.Builder
    public ItemRequirement(int ItemID, int amount, int minAmount) {
        this(ItemID, amount);
        this.minAmount = minAmount;
    }

    @lombok.Builder
    public ItemRequirement(int ItemID, int amount, int minAmount, boolean acceptEquipped) {
        this(ItemID, amount, minAmount);
        this.acceptEquipped = acceptEquipped;
    }

    @lombok.Builder
    public ItemRequirement(int ItemID, int amount, int minAmount, boolean acceptEquipped, boolean shouldEquip) {
        this(ItemID, amount, minAmount, acceptEquipped);
        this.shouldEquip = shouldEquip;
    }


    public ItemRequirement(ItemRequirement.Builder builder) {
        this.itemName = builder.itemName;
        this.id = builder.id;
        this.amount = builder.amount;
        this.minAmount = builder.minAmount;
        this.acceptEquipped = builder.acceptEquipped;
        this.shouldEquip = builder.shouldEquip;
        this.chargesNeeded = builder.amountOfChargesNeeded;
        this.alternateItems = builder.alternateItemIDs;
    }


    public ItemRequirement equipped() {
        ItemRequirement newItem = this;
        newItem.setShouldEquip(true);
        return newItem;
    }

    public ItemRequirement quantity(int newQuantity) {
        ItemRequirement newItem = this;
        newItem.setAmount(newQuantity);
        return newItem;
    }

    public boolean hasItem() {
        RSItem[] i = Inventory.find(this.getId());
        RSItemDefinition def = RSItemDefinition.get(this.getId());
        if (Equipment.isEquipped(this.getId())) {
            Log.debug("Has item equipped w/ ID of " + this.getId());
            return true;
        }

        //TODO subtract any unnoted items from required amount to check noted with
        //check if we have enough noted item
        if (isAcceptNoted()) {
            RSItem[] notedItem = Inventory.find(Utils.getNotedItemID(this.getId()));
            if (notedItem.length > 0 && notedItem[0].getStack() >= this.getAmount())
                return true;
        }

        if (this.alternateItems != null && this.alternateItems.size() > 0) {
            for (Integer b : alternateItems) {
                if (Inventory.find(b).length > this.minAmount - 1) {
                    Log.debug("[ItemRequirement]: Accepted an alternative id of " + b +
                            " for ItemID of:  " + this.id);
                    return true;
                }
            }
        }

        if (i.length > 0) {
            if (def == null)
                Log.error("[ItemRequirement]: Definition is null");


            if (def != null && !def.isStackable()) {
                System.out.println("[ItemRequirement]: We have x" + i.length + " of unstackable item " +
                        this.getId() + " (min = " + this.minAmount + ")");
                return i.length >= this.minAmount;
            } else if (def != null && def.isStackable()) {
                System.out.println("[ItemRequirement]: We have x" + i.length + " of stackable item " +
                        this.getId() + " (min = " + this.minAmount + ")");
                return i[0].getStack() >= this.minAmount;
            }


        }


        boolean b = (i.length >= this.minAmount);
        if (def != null)
            System.out.println("[ItemRequirement]: We are missing inv item " + def.getName() + " (min = " + this.minAmount + ") " +
                    b);
        else
            General.println("[ItemRequirement]: We are missing inventory req item " + " (min = " + this.minAmount + ") " +
                    b);
        // General.println("[ItemRequirement]: boolean (i.e. do we have item) for this item(#" + this.id + " ): " + b);
        return b;
    }


    public void addAlternateItemID(List<Integer> ids) {
        this.alternateItems.addAll(ids);
    }

    public void addAlternateItemID(Integer... ids) {
        this.alternateItems.addAll(Arrays.asList(ids));
    }

    public void addAlternateItemIDs(Integer... ids) {
        this.alternateItems.addAll(Arrays.asList(ids));
    }

    /**
     * Checks if we have adequate charges for our task
     *
     * @return
     */
    public boolean hasEnoughCharges() {
        int charges = 0;
        if (this.chargesNeeded > 0) {
            Optional<String> name = getTrimmedName(ItemDefinition.get(this.id));
            if (name.isPresent()) {
                List<InventoryItem> invItemList =
                        Query.inventory()
                                .nameContains(name.get())
                                .toList();

                for (InventoryItem i : invItemList) {
                    charges = charges + getCharges(i);
                }
            }
        }
        Log.debug("Charges on player " + charges);
        boolean b = charges >= this.chargesNeeded;
        Log.debug("[ItemRequirement]: Do we have at least " + this.chargesNeeded + " charges of item: " +
                this.id + ": " + b);
        return b;
    }

    private Optional<String> getTrimmedName(Optional<ItemDefinition> def) {
        if (def.isEmpty())
            return Optional.empty();

        String[] nameSplit = def.get().getName().split(" \\(");

        if (nameSplit.length > 1) {
            Log.debug("Name is " + nameSplit[0]);
            return Optional.of(nameSplit[0]);
        }
        return Optional.empty();
    }

    private int getCharges(InventoryItem invItem) {
        String[] nameSplit = invItem.getDefinition().getName().split("\\(");
        if (nameSplit.length > 1) {
            String[] chargesUnformatted = nameSplit[1].split("\\)");
            if (chargesUnformatted.length > 0) {
                Log.debug("[ItemRequirement]: Detected item in inventory with " + chargesUnformatted[0] + " charges");
                return Integer.parseInt(chargesUnformatted[0]);
            }
        }
        return 0;
    }

    public List<Integer> getAllIds() {
        List<Integer> items = new ArrayList<>(Collections.singletonList(id));
        items.addAll(alternateItems);

        return items.stream().distinct().collect(Collectors.toList());
    }


    public boolean isActualItem() {
        return id != -1 && amount != -1;
    }

    // all lower case
    String[] TELEPORT_ITEM_LIST =
            new String[]{
                    "games necklace",
                    "amulet of glory",
                    "ring of wealth",
                    "ring of dueling",
                    "skills necklace",
            };

    public boolean checkBank() {
        if (BankCache.isInitialized()) {
            return BankCache.getStack(this.getId()) >= this.getAmount();
        }
        //TODO finish this somehow
        Log.error("[ItemRequirement]: Bank Cache isn't initialized, need to open the bank (method NOT FINISHED)");
        return false;
    }


    public static class Builder {

        private int amountOfChargesNeeded;
        private String itemName;
        private int id;
        private List<Integer> alternateItemIDs = new ArrayList<>();
        public boolean acceptNoted = false;
        private int amount;
        public int minAmount = 0;
        private boolean acceptEquipped = false;
        private boolean shouldEquip = false;


        public ItemRequirement.Builder itemName(String itemName) {
            this.itemName = itemName;
            return this;
        }

        public ItemRequirement.Builder id(int id) {
            this.id = id;
            return this;
        }

        public ItemRequirement.Builder acceptNoted(boolean acceptNoted) {
            this.acceptNoted = acceptNoted;
            return this;
        }

        public ItemRequirement.Builder amount(int amount) {
            this.amount = amount;
            return this;
        }

        public ItemRequirement.Builder minAmount(int minAmount) {
            this.minAmount = minAmount;
            return this;
        }

        public ItemRequirement.Builder chargesNeeded(int chargesNeeded) {
            this.amountOfChargesNeeded = chargesNeeded;
            return this;
        }

        public ItemRequirement.Builder acceptEquipped(boolean acceptEquipped) {
            this.acceptEquipped = acceptEquipped;
            return this;
        }

        public ItemRequirement.Builder shouldEquip(boolean shouldEquip) {
            this.shouldEquip = shouldEquip;
            return this;
        }

        //Return the finally consrcuted User object
        public ItemRequirement build() {
            ItemRequirement req = new ItemRequirement(this);
            return req;
        }

        private void validateUserObject(ItemRequirement user) {
            //Do some basic validations to check
            //if user object does not break any assumption of system
        }

    }


    public int getInventoryNumMatches(List<InventoryItem> items, int ItemID) {
        return items.stream()
                .filter(Objects::nonNull)
                .filter(i -> i.getId() == ItemID)
                .mapToInt(InventoryItem::getStack)
                .sum();
    }

    public int getEquipmentNumMatches(List<EquipmentItem> items, int ItemID) {
        return items.stream()
                .filter(Objects::nonNull)
                .filter(i -> i.getId() == ItemID)
                .mapToInt(EquipmentItem::getStack)
                .sum();
    }


    /**
     * Get the difference between the required quantity for this requirement and the amount the client has.
     * Any value <= 0 indicates they have the required amount
     */
    public int getRequiredItemDifference(int ItemID, boolean checkConsideringSlotRestrictions,
                                         List<RSItem> items) {
        List<EquipmentItem> equipment = Query.equipment().toList();
        //ItemContainer equipped = client.getItemContainer(InventoryID.EQUIPMENT);
        int tempQuantity = amount;

        if (equipment.size() > 0) {
            tempQuantity -= getEquipmentNumMatches(equipment, ItemID);
        }

        if (!checkConsideringSlotRestrictions || !acceptEquipped) {
            List<InventoryItem> inventory = Query.inventory().toList();
            if (inventory.size() > 0) {
                tempQuantity -= getInventoryNumMatches(inventory, ItemID);
            }
        }

        if (items != null) {
            // tempQuantity -= getNumMatches(items, ItemID);
        }

        return tempQuantity;
    }

    public ItemRequirement copy() {
        ItemRequirement newItem = new ItemRequirement(id, amount, shouldEquip);
        newItem.addAlternateItemID(alternateItems);
        newItem.setExclusiveToOneItemType(exclusiveToOneItemType);
        newItem.setDisplayMatchedItemName(displayMatchedItemName);
        newItem.setConditionToHide(conditionToHide);

        return newItem;
    }

    public int getBuyQuantity() {
        if (BankCache.isInitialized()) {
            return BankCache.getStack(this.id) - this.amount;
        }
        return 0;
    }

    @Override
    public boolean check() {
        return hasItem();
    }


    public boolean check(boolean checkConsideringSlotRestrictions) {
        return check(checkConsideringSlotRestrictions, new ArrayList<>());
    }

    public boolean check(boolean checkConsideringSlotRestrictions, List<RSItem> items) {
        List<RSItem> allItems = new ArrayList<>(items);
        if (BankCache.isInitialized()) {
            //   allItems.addAll(BankCache.get());
        }

        int remainder = amount;

        List<Integer> ids = getAllIds();
        for (int alternate : ids) {
            if (exclusiveToOneItemType) {
                remainder = amount;
            }
            remainder -= (amount - getRequiredItemDifference(alternate, checkConsideringSlotRestrictions,
                    allItems));
            if (remainder <= 0) {
                return true;
            }
        }
        return false;
    }


}
