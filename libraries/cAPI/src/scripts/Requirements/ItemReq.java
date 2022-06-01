package scripts.Requirements;

import lombok.Getter;
import lombok.Setter;
import org.tribot.api.General;
import org.tribot.api2007.Equipment;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSItemDefinition;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.types.definitions.ItemDefinition;
import scripts.EntitySelector.Entities;
import scripts.EntitySelector.finders.prefabs.ItemEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Taken from Final Caliburs Scripter applications and modified
 */

public class ItemReq implements Requirement {

    @Getter
    @Setter
    private boolean equip;

    @Getter
    @Setter
    private int amountOfChargesNeeded;

    @Getter
    private String itemName;


    @Getter
    private boolean isItemNoted;

    @Getter
    @Setter
    private int id;

    @Getter
    @Setter
    private List<Integer> alternateItemIDs = new ArrayList<>();

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

    @Getter
    private boolean isStackable;

    @Getter
    private int minimumDosesNeeded = 0;


    public ItemReq(int ItemID) {
        this.id = ItemID;
        this.amount = 1;
        this.minAmount = 1;
        this.isStackable = isItemStackable();
    }

    public ItemReq(int ItemID, int amount) {
        this.id = ItemID;
        this.amount = amount;
        this.minAmount = amount;
        this.isStackable = isItemStackable();
    }

    public ItemReq(int[] ItemID, int amount) {
        this.id = ItemID[0];

        this.amount = amount;
        this.minAmount = amount;
        for (int i : ItemID){
            alternateItemIDs.add(i);
        }
        this.isStackable = isItemStackable();
    }

    public ItemReq(String name, int ItemID, int amount) {
        this.itemName = name;
        this.id = ItemID;
        this.amount = amount;
        this.minAmount = amount;
        this.isStackable = isItemStackable();
    }

    public ItemReq(String name, int ItemID) {
        this.itemName = name;
        this.id = ItemID;
        this.amount = 1;
        this.minAmount = 1;
        this.isStackable = isItemStackable();
    }

    public ItemReq(int ItemID, int amount, boolean acceptEquipped) {
        this(ItemID, amount);
        this.acceptEquipped = acceptEquipped;
        this.isStackable = isItemStackable();
    }

    public ItemReq(int ItemID, int amount, boolean acceptEquipped, boolean shouldEquip) {
        this(ItemID, amount, acceptEquipped);
        this.shouldEquip = shouldEquip;
        this.isStackable = isItemStackable();
    }

    public ItemReq(List<Integer> itemCollection) {
        this.id = itemCollection.get(0);
        addAlternateItemID(itemCollection);
        this.amount = 1;
        this.minAmount = 1;

    }

    public ItemReq(int ItemID, int amount, int minAmount) {
        this(ItemID, amount);
        this.minAmount = minAmount;
        this.isStackable = isItemStackable();
    }

    public ItemReq(int ItemID, int amount, int minAmount, boolean acceptEquipped) {
        this(ItemID, amount, minAmount);
        this.acceptEquipped = acceptEquipped;
        this.isStackable = isItemStackable();
    }

    public ItemReq(int ItemID, int amount, int minAmount, boolean acceptEquipped, boolean shouldEquip) {
        this(ItemID, amount, minAmount, acceptEquipped);
        this.shouldEquip = shouldEquip;
        this.isStackable = isItemStackable();
    }

    private ItemReq(Builder builder) {
        setEquip(builder.equip);
        setAmountOfChargesNeeded(builder.amountOfChargesNeeded);
        itemName = builder.itemName;
        isItemNoted = builder.isItemNoted;
        setId(builder.id);
        setAlternateItemIDs(builder.alternateItemIDs);
        setAmount(builder.amount);
        setMinAmount(builder.minAmount);
        setAcceptEquipped(builder.acceptEquipped);
        setShouldEquip(builder.shouldEquip);
        isStackable = builder.isStackable;
        minimumDosesNeeded = builder.minimumDosesNeeded;
    }

    public boolean isItemStackable(){
        Optional<ItemDefinition> def = ItemDefinition.get(this.id);
        return def.map(ItemDefinition::isStackable).orElse(false);
    }

   /*  public ItemReq(Builder builder) {
        this.itemName = builder.itemName;
        this.id = builder.id;
        this.amount = builder.amount;
        this.minAmount = builder.minAmount;
        this.acceptEquipped = builder.acceptEquipped;
        this.shouldEquip = builder.shouldEquip;
        this.amountOfChargesNeeded = builder.amountOfChargesNeeded;
        this.alternateItemIDs = builder.alternateItemIDs;
        this.isItemNoted = builder.isItemNoted;
        this.isStackable = isItemStackable();
    }

   public static class Builder {

        private int amountOfChargesNeeded;
        private String itemName;
        private int id;
        private List<Integer> alternateItemIDs = new ArrayList<>();
        private boolean isItemNoted = false;
        private int amount;
        private int minAmount = 0;
        private boolean acceptEquipped = false;
        private boolean shouldEquip = false;
        private int minimumDosesNeeded = 0;


        public Builder itemName(String itemName) {
            this.itemName = itemName;
            return this;
        }

        public Builder idEquals(int id) {
            this.id = id;
            return this;
        }

        public Builder isItemNoted(boolean isItemNoted) {
            this.isItemNoted = isItemNoted;
            return this;
        }

        public Builder amount(int amount) {
            this.amount = amount;
            return this;
        }

        public Builder minAmount(int minAmount) {
            this.minAmount = minAmount;
            return this;
        }

        public Builder chargesNeeded(int chargesNeeded) {
            this.amountOfChargesNeeded = chargesNeeded;
            return this;
        }

        public Builder acceptEquipped(boolean acceptEquipped) {
            this.acceptEquipped = acceptEquipped;
            return this;
        }

        public Builder addAlternateIds(int... ids) {
            for (int i : ids)
            this.alternateItemIDs.add(i);
            return this;
        }

        public Builder shouldEquip(boolean shouldEquip) {
            this.acceptEquipped = true;
            this.shouldEquip = shouldEquip;
            return this;
        }

        //Return the finally constructed User object
        public ItemReq build() {
            ItemReq req = new ItemReq(this);
            return req;
        }

        private void validateUserObject(ItemReq user) {
            //Do some basic validations to check
            //if user object does not break any assumption of system
        }
    }*/

    public boolean hasItem() {
        RSItem[] i = Inventory.find(this.id);
        RSItemDefinition def = RSItemDefinition.get(this.id);
        if (Equipment.isEquipped(this.id)) {
            Log.log("Has item equipped w/ ID of " + this.id);
            return true;
        }

        if (this.alternateItemIDs != null){
            if (this.alternateItemIDs.stream().anyMatch(jar -> Inventory.find(jar).length > 0)) {
                General.println("[ItemReq]: We have an alternate item");
                return true;
            }
        }
        if (i.length > 0) {
            if (def == null)
                General.println("[ItemReq]: Definition is null");

            if (def != null && !def.isStackable()) {
                General.println("[ItemReq]: We have x" + i.length + " of unstackable item " +
                        this.id + " (min = " + this.minAmount + ")");
                return i.length >= this.minAmount;
            } else if (def != null && def.isStackable()) {
                General.println("[ItemReq]: We have x" + i.length + " of stackable item " +
                        this.id + " (min = " + this.minAmount + ")");
                return i[0].getStack() >= this.minAmount;
            }

            if (this.alternateItemIDs != null && this.alternateItemIDs.size() > 0) {
                for (Integer b : alternateItemIDs) {
                    if (i[0].getID() == b) {
                        General.println("[ItemReq]: Accepted an alternative id of " + b +
                                " for ItemID of " + i[0].getID());
                        return true;
                    }
                }
            }
        }

        boolean b = (i.length >= this.minAmount);
        if (def != null)
            General.println("[ItemReq]: We are missing inv item " + def.getName() + " (min = " + this.minAmount + ") " +
                    b);
        else
        General.println("[ItemReq]: We are missing inv item " + " (min = " + this.minAmount + ") " +
                b);
        // General.println("[ItemReq]: boolean (i.e. do we have item) for this item(#" + this.id + " ): " + b);
        return b;
    }


    public void addAlternateItemID(List<Integer> ids) {
        this.alternateItemIDs.addAll(ids);
    }

    public void addAlternateItemID(Integer... ids) {
        this.alternateItemIDs.addAll(Arrays.asList(ids));
    }

    /**
     * Checks if we have adequate charges for our task
     *
     * @param itemName without the "(X)"
     * @return
     */
    public boolean hasEnoughCharges(String itemName) {
        int charges = 0;
        if (this.amountOfChargesNeeded > 0) {
            RSItem[] invItems = Entities.find(ItemEntity::new)
                    .nameContains(itemName)
                    .getResults();

            for (RSItem i : invItems) {
                charges = charges + getCharges(i);
            }
        }
        boolean b = charges >= this.amountOfChargesNeeded;
        Log.log("[ItemReq]: Do we have at least " + this.amountOfChargesNeeded + " charges of item: " +
                itemName + ": " + b);
        return b;
    }

    private String getName(RSItem rsItem) {
        Optional<RSItemDefinition> defOptional = Optional.ofNullable(rsItem.getDefinition());
        if (defOptional.isPresent()) {
            Optional<String> name = Optional.ofNullable(defOptional.get().getName());
            if (name.isPresent())
                return name.get();
        }
        return "";
    }

    private int getCharges(RSItem rsItem) {
        String[] nameSplit = getName(rsItem).split("\\(");
        if (nameSplit.length > 1) {
            String[] chargesUnformatted = nameSplit[1].split("\\)");
            if (chargesUnformatted.length > 0) {
                Log.log("[ItemReq]: Detected item in inventory with " + chargesUnformatted[0] + " charges");
                return Integer.parseInt(chargesUnformatted[0]);
            }
        }
        return 0;
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

    @Override
    public boolean check() {
        return hasItem();
    }

    public static final class Builder {
        private boolean equip;
        private int amountOfChargesNeeded;
        private String itemName;
        private boolean isItemNoted;
        private int id;
        private List<Integer> alternateItemIDs;
        private int amount;
        private int minAmount;
        private boolean acceptEquipped;
        private boolean shouldEquip;
        private boolean isStackable;
        private int minimumDosesNeeded = 4;

        public Builder() {

        }

        public Builder equip(boolean val) {
            equip = val;
            return this;
        }

        public Builder amountOfChargesNeeded(int val) {
            amountOfChargesNeeded = val;
            return this;
        }

        public Builder itemName(String val) {
            itemName = val;
            return this;
        }

        public Builder isItemNoted(boolean val) {
            isItemNoted = val;
            return this;
        }

        public Builder id(int val) {
            id = val;
            return this;
        }

        public Builder alternateItemIDs(List<Integer> val) {
            alternateItemIDs = val;
            return this;
        }

        public Builder amount(int val) {
            amount = val;
            return this;
        }

        public Builder minAmount(int val) {
            minAmount = val;
            return this;
        }

        public Builder acceptEquipped(boolean val) {
            acceptEquipped = val;
            return this;
        }

        public Builder shouldEquip(boolean val) {
            shouldEquip = val;
            return this;
        }

        public Builder isStackable(boolean val) {
            isStackable = val;
            return this;
        }

        public Builder minimumDosesNeeded(int val) {
            minimumDosesNeeded = val;
            return this;
        }

        public ItemReq build() {
            return new ItemReq(this);
        }
    }
}
