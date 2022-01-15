package scripts;

import dax.api_lib.DaxWalker;
import dax.walker.utils.AccurateMouse;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.*;
import org.tribot.api2007.types.*;
import org.tribot.script.sdk.Bank;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.tasks.BankTask;
import org.tribot.script.sdk.tasks.BankTaskError;
import org.tribot.script.sdk.tasks.InsufficientItemError;
import scripts.Requirements.ItemReq;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class BankManager {

    // public static ArrayList<BankItem> inventoryList = new ArrayList<>();

    public static final int[] GAMES_NECKLACE = {3853, 3855, 3857, 3859, 3861, 3863, 3865, 3867};
    public static final int[] SUPER_STRENGTH = {2440, 157, 159, 161};
    public static int[] CHARGED_GLORIES = {11978, 11976, 1712, 1710, 1708, 1706};
    public static final int[] PRAYER_POTION = {2434, 139, 141, 143};
    public static final int[] STAMINA_POTION = {12625, 12627, 12629, 12631};
    public static final int[] AMULET_OF_GLORY = {11978, 11976, 1712, 1710, 1708, 1706};
    public static final int[] COMBAT_BRACELET = {11118, 11120, 11122, 11124, 11972, 11974};
    public static ArrayList<Integer> neededItemIDs = null;
    public static int itemsToBuy;
    public static int stack = 0;
    public static RSItem[] myItems1;
    public static RSItem[] myItems;
    public static RSItem[] myEquipedItems;

    private static RSItem[] cachedSearch;

    public static RSArea GE_Area = new RSArea(new RSTile(3160, 3494, 0), new RSTile(3168, 3485, 0));

    public static RSItem[] inv;

    static int PARENT = 12;
    static int ITEM_WINDOW_CHILD = 12;
    static int SCROLLBAR_CHILD = 13;
    static int SEARCH_BUTTON_CHILD = 39;
    public static int BACKSPADE_CODE = 8;
    public static char BACKSPACE = KeyEvent.VK_BACK_SPACE;
    static int SEARCH_BOX_PARENT = 162;
    static int SEARCH_BOX_CHILD = 45;

    private static boolean closeBankSearch() {
        if (Interfaces.isInterfaceSubstantiated(162, 44)) {
            if (Interfaces.isInterfaceSubstantiated(SEARCH_BOX_PARENT, 44)) { // search area is  open (based on text)
                if (Interfaces.isInterfaceSubstantiated(PARENT, SEARCH_BUTTON_CHILD))
                    if (Interfaces.get(PARENT, SEARCH_BUTTON_CHILD).click())
                        return Timer.waitCondition(() -> !Interfaces.isInterfaceSubstantiated(SEARCH_BOX_PARENT, 44), 4000, 7000);

            } else
                return true;
        }
        return true;
    }


    public void performBankTask(BankTask task) {

    }

    /**
     * INCOMPLETE
     *
     * @param err return false if no error
     */
    public static Optional<ItemReq> getItemReqFromBankError(Optional<BankTaskError> err) {
        if (err.isEmpty()) return Optional.empty();

        if (err.get() instanceof InsufficientItemError) {
            int num = ((InsufficientItemError) err.get()).getRequiredAmt() -
                    ((InsufficientItemError) err.get()).getActualAmt();
            int id = ((InsufficientItemError) err.get()).getItemId();
            ItemReq req = new ItemReq(id, num);
            return Optional.of(req);
        }
        return Optional.empty();
    }


    public static void depositAllExcept(int... ids) {
        for (int i = 0; i < 3; i++) {
            if (Inventory.getAll().length > Inventory.find(ids).length) {
                Log.log("[BankManager]: Depositing all, attempt " + (i + 1));
                depositAllExcept(false, ids);
                General.sleep(General.random(500, 900));
            } else
                return;
        }
    }

    public static boolean checkInventoryItems(int... ids) { // put the items required in the args here
        int items = 0;
        inv = Inventory.getAll();
        //Log.log("[BankManager]: Checking inventory items...");
        // Log.log("[BankManager]: Item list length " + ids.length);
        for (int i = 0; i < ids.length; i++) {
            for (int d = 0; d < inv.length; d++) {
                if (inv[d].getID() == ids[i]) {
                    RSItemDefinition def = inv[d].getDefinition();
                    if (def != null) {
                        //Log.log("[BankManager]: We have item: " + def.getName());
                    }
                    //  Log.log("[BankManager]: We have item " + ids[i]);
                    items++;
                    break;
                }
            }

        }
        if (items == ids.length) {
            // Log.log("[BankManager]: We have all inventory items needed");
            return true;
        }
        Log.log("[BankManager]: Missing an item. Expected " + ids.length + "/have: " + items);
        return false;
    }

    /**
     * Two methods to check withdraw success
     * 1st is for an item ID,
     * 2nd is an int Array (item id)
     */
    private static boolean checkWithdrawSuccess(int quantity, int itemID) {
        Timer.waitCondition(() -> Inventory.find(itemID).length > 0, 800, 1500);
        if (Inventory.find(itemID).length < 1 && !Inventory.isFull()) {
            Log.log("[Debug]: Seemingly failed to get item, trying again.");
            return BankManager.withdraw(quantity, true, itemID);
        } else
            return true;
    }


    public static boolean depositEquipment() {
        open(true);
        closeHelpWindow();
        for (int i = 0; i < 3; i++) {
            if (Bank.depositEquipment())
                Timer.waitCondition(() -> Equipment.getItems().length == 0, 3000);

            if (Equipment.getItems().length == 0)
                return true;
        }
        if (Equipment.getItems().length == 0) {
            return true;
        }
        return false;
    }


    public static ArrayList<RSItem> items = new ArrayList<RSItem>();
    public static List<RSItem> list;

    /**
     * Caches and combines both banked and equipped items to a single array...
     * that is referenced when buying items (see "determinePurchaseAmounts()")
     */
    public static void getAllList() {
        open(true);
        closeHelpWindow();
        if (areItemsLoaded()) {
            items.clear(); // need this incase we need to recache later
            General.sleep(General.random(1000, 3000));
            BankManager.depositAllExcept(true, 995);
            Log.log("[Debug]: Setting list...");
            myItems1 = Banking.getAll();
            list = Arrays.asList(myItems1);
            items.addAll(list);

            myEquipedItems = Equipment.getItems();
            list = Arrays.asList(myEquipedItems);
            items.addAll(list);

            myItems = items.toArray(new RSItem[0]); // likely don't need this anymore as I changed determine purchase amounts
            Log.log("[Debug]: Cached Items: " + myItems.length);
        }
    }


    public static void determinePurchaseAmounts(int itemID, int quanitity) {
        for (int i = 0; i < items.size(); i++) {
            stack = 0;
            String itemName = RSItemDefinition.get(itemID).getName();
            if (itemID == items.get(i).getID()) {
                stack = items.get(i).getStack();
                if (stack > 0) {
                    Log.log("[Debug]: We already have " + stack + " of " + itemName);
                    itemsToBuy = quanitity - stack;
                    return;

                }/*else if (itemsToBuy < 1) {

                } */
            }
        }
        String itemName = RSItemDefinition.get(itemID).getName();
        itemsToBuy = quanitity - stack;
        Log.log("[Debug]: Need to buy " + itemsToBuy + " of " + itemName);
    }


    public static int getCount(int id) {
        cachedSearch = Banking.find(id);
        // Log.log("Amount of " + id + cachedSearch[0].getStack());
        return cachedSearch.length > 0 ? cachedSearch[0].getStack() : 0;
    }

    private static int getTotalCount(int id) {
        if (!areItemsLoaded()) {
            General.sleep(1000);
        }
        cachedSearch = Banking.find(id);
        RSItem[] inv = Inventory.find(id);
        int invCount = 0;
        if (inv.length > 0) {
            RSItemDefinition def = inv[0].getDefinition();
            if (def != null) {
                invCount = def.isStackable() || def.isNoted() ? inv[0].getStack() : inv.length;
            }
        }
        return (cachedSearch.length > 0 ? cachedSearch[0].getStack() : 0) + invCount;
    }

    public static boolean areItemsLoaded() {
        return getCurrentBankSpace() == Banking.getAll().length;
    }

    public static boolean close(boolean shouldWait) {
        return !Banking.close() || !shouldWait || Timer.waitCondition(() -> !Banking.isBankScreenOpen(), 3000);
    }

    public static boolean open(boolean shouldWait) {
        if (Bank.isOpen())
            return true;

        if (!Banking.isInBank()) {
            Log.log("[Bank]: Walking to Bank");
            for (int i = 0; i < 3; i++) { //tries 3 times to make a dax call to walk to bank
                if (DaxWalker.walkToBank()) {
                    Timer.waitCondition(Banking::isInBank, 8000, 10000);
                    break;
                } else
                    General.sleep(General.random(2500, 4000));
            }

        }
        Log.log("[Bank]: Getting item");
        // will try 3 times to open the bank
        // loop breaks if successful
        if (!Bank.isOpen()) {
            for (int i = 0; i < 3; i++) {
                if (Banking.openBank()) {
                    if (Timer.quickWaitCondition(Bank::isOpen, 4500, 6000))
                        Utils.idlePredictableAction();
                        break;
                }
            }
        }
        closeHelpWindow();
        return !Banking.openBank() || !shouldWait || Timer.waitCondition(Bank::isOpen, 5000);
    }


    private static int getCurrentBankSpace() {
        RSInterface amount = Interfaces.get(12, 5);
        if (amount != null) {
            String txt = amount.getText();
            if (txt != null) {
                try {
                    int toInt = Integer.parseInt(txt);
                    if (toInt > 0)
                        return toInt;
                } catch (NumberFormatException e) {
                    return -1;
                }
            }
        }
        return -1;
    }

    public static boolean depositAll(boolean shouldWait) {
        if (Inventory.getAll().length < 1) { // prevents it from pushing the button if the inventory is already empty
            return true;
        }
        if (!shouldWait) {
            return Banking.depositAll() <= 0; // no check if it was successful

        } else {
            if (!Bank.isOpen()) {
                BankManager.open(true);
                closeHelpWindow();
            }
            Log.log("[BankManager]: Depositing all");

            Banking.depositAll();
            if(Timer.waitCondition(() -> Inventory.getAll().length == 0, 2000, 5000)){
                Utils.idlePredictableAction();
            }

            if (Inventory.getAll().length == 0)  // this checks if it was successful and if not it will try 1 more time
                return true;
            else {
                Log.log("[BankManager]: Depositing all attempt 2");
                return Banking.depositAll() <= 0 || !shouldWait || Timer.waitCondition(() -> inventoryChange(false), 5000);
            }
        }
    }

    public static boolean depositAllExcept(boolean shouldWait, int... id) {
        closeHelpWindow();
        return Banking.depositAllExcept(id) <= 0 || !shouldWait || Timer.waitCondition(() -> inventoryChange(false), 5000);
    }


    public static boolean withdrawArray(int[] array, int num) {
        for (int i = 0; i < array.length; i++) {

            if (withdraw(num, true, array[i]))
                return true;
        }

        return false;
    }


    public static boolean withdraw(int num, boolean shouldWait, int itemId) {
        if (!Bank.isOpen())
            open(true);

        closeHelpWindow();

        RSItem[] item = Banking.find(itemId);

        if (item.length < 1) {
            String name = RSItemDefinition.get(itemId).getName();
            if (name != null)
                Log.log("[BankManager]: Missing bank item: " + name);
            else
                Log.log("[BankManager]: Missing bank item: " + itemId);
            return false;
        }

        // we will try up to 5 times to withdraw
        for (int i = 0; i < 5; i++) {
            if (!shouldWait) {
                // if we aren't waiting for inventory to change we
                if (Banking.withdraw(num, itemId)) {
                    Utils.microSleep();
                    return true;

                } else
                    Utils.microSleep();

            } else {

                if (Banking.withdraw(num, itemId)) {
                    if (Timer.waitCondition(() -> inventoryChange(true) || Inventory.find(itemId).length > 0, 4000, 6000))
                        return true;

                    else
                        Utils.microSleep();
                }
            }
        }
        return false;
    }

    private static void closeHelpWindow() {
        if (Interfaces.isInterfaceSubstantiated(664, 28))
            if (Interfaces.get(664, 28, 0).click()) {
                Timer.waitCondition(() -> !Interfaces.isInterfaceSubstantiated(664, 28), 5000);
                General.sleep(General.random(300, 1200));
            }
    }

    /**
     * @param num
     * @param shouldWait
     * @param itemId
     * @return
     */
    public static boolean withdraw(int num, boolean shouldWait, int[] itemId) {
        General.sleep(General.randomSD(50, 400, 250, 75)); // sleep here so withdraws are not back to back speedy

        if (!Banking.isBankScreenOpen())
            open(true);

        closeHelpWindow();
        closeBankSearch();

        RSItem[] item = Banking.find(itemId);
        if (item.length < 1) {
            Log.log("[Debug]: Missing Bank item: " + RSItemDefinition.get(itemId[0]).getName());
            return false;
        }

        if (Inventory.isFull() && num == 0)
            BankManager.depositAll(true);

        for (int i = 0; i < 5; i++) {
            if (!shouldWait) {

                if (Banking.withdraw(num, itemId)) {
                    Utils.microSleep();
                    return true;

                } else
                    Utils.microSleep();

            } else {

                if (Banking.withdraw(num, itemId)) {
                    if (Timer.waitCondition(() -> inventoryChange(true) || Inventory.find(itemId).length > 0, 4000, 6000))
                        return true;

                    else
                        Utils.microSleep();
                }
            }
        }
        return false;
    }

    public static boolean withdraw(int num, int itemId, boolean endIfNoItem) {
        if (!Banking.isBankScreenOpen()) {
            open(true);
            closeHelpWindow();
        }

        closeBankSearch();
        RSItem[] item = Banking.find(itemId);
        if (item.length < 1) {

            if (endIfNoItem) {
                Log.log("[BankManager]: Missing bank item: " + RSItemDefinition.get(itemId).getName());
                throw new IllegalStateException("Missing bank Item: " + RSItemDefinition.get(itemId).getName());

            } else {
                Log.log("[BankManager]: Missing bank item: " + RSItemDefinition.get(itemId).getName());
                return false;
            }

        } else if (item[0].getStack() < num) {
            Log.log("[BankManager]: Missing bank item: " + RSItemDefinition.get(itemId).getName());
            throw new IllegalStateException("[BankManager Error]: Missing bank Item in adequate amounts: " + RSItemDefinition.get(itemId).getName());
        }

        if (Inventory.isFull()) {
            Log.log("[BankManager]: Inventory is full depositing all items (Check script code to prevent this in the future)");
            BankManager.depositAll(true);
        }

        for (int i = 0; i < 5; i++) {
            if (Banking.withdraw(num, itemId)) {
                return Timer.waitCondition(() -> inventoryChange(true), 2500, 6000);

            } else
                Utils.microSleep();
        }
        return false;
    }

    public static boolean withdraw(boolean restockItem, int num, int itemId) {
        if (!Banking.isBankScreenOpen()) {
            open(true);
            if (Interfaces.isInterfaceSubstantiated(664, 28))
                if (Interfaces.get(664, 28, 0).click())
                    Timer.waitCondition(() -> !Interfaces.isInterfaceSubstantiated(664, 28), 4000);
        }
        closeBankSearch();
        RSItem[] item = Banking.find(itemId);
        if (item.length < 1) {

            if (restockItem) {
                Log.log("[BankManager]: Missing item: " + RSItemDefinition.get(itemId).getName());
                return true;

            } else {
                Log.log("[BankManager]: Missing item: " + RSItemDefinition.get(itemId).getName());
                return false;
            }

        } else if (item[0].getStack() < num) {
            Log.log("[Debug]: Missing adequate amounts of item: " + RSItemDefinition.get(itemId).getName());
            return false;
        }

        if (Inventory.isFull()) {
            Log.log("[BankManager]: Inventory is full depositing all items (Check script code to prevent this in the future)");
            BankManager.depositAll(true);
        }

        for (int i = 0; i < 5; i++) {
            if (Banking.withdraw(num, itemId))
                return true;

            else
                Utils.microSleep();
        }
        return false;
    }

    public static boolean withdraw(int num, int itemId) { // same as above but default waits
        if (!Banking.isBankScreenOpen()) {
            open(true);
            if (Interfaces.isInterfaceSubstantiated(664, 28))
                if (Interfaces.get(664, 28, 0).click())
                    Timer.waitCondition(() -> !Interfaces.isInterfaceSubstantiated(664, 28), 4000);
        }
        closeBankSearch();
        RSItem[] item = Banking.find(itemId);
        if (item.length < 1) {
            return false;
        }


        return Banking.withdrawItem(item[0], num) || Timer.waitCondition(() -> inventoryChange(true), 2500);
    }

    public static boolean withdraw2(int num, boolean shouldWait, int itemId) {
        if (!Banking.isBankScreenOpen()) {
            open(true);
            if (Interfaces.isInterfaceSubstantiated(664, 28))
                if (Interfaces.get(664, 28, 0).click())
                    Timer.waitCondition(() -> !Interfaces.isInterfaceSubstantiated(664, 28), 4000);
        }
        RSItem[] item = Banking.find(itemId);
        if (item.length < 1) {
            Log.log("[BankManager]: Missing item: " + RSItemDefinition.get(itemId).getName());
            return false;
        }
        if (Inventory.isFull() && num == 0) {
            BankManager.depositAll(true);
        }
        if (Banking.withdrawItem(item[0], num))
            Timer.waitCondition(() -> inventoryChange(true), 2500);
        return checkWithdrawSuccess(num, itemId);
    }


    public static boolean deposit(int num, int itemId) {
        if (!Bank.isOpen())
            open(true);

        closeHelpWindow();

        Banking.deposit(num, itemId);
        RSItem[] item = Inventory.find(itemId);
        return item.length <= 0 || !Banking.deposit(num, itemId) || Timer.waitCondition(() -> inventoryChange(true), 2500);
    }


    public static boolean turnNotesOn() {
        RSInterfaceChild c = Interfaces.get(12, 25);
        return c != null && c.click() && Timer.waitCondition(BankManager::areNotesOn, 2000, 3000);
    }

    public static boolean areNotesOn() {
        return Game.getSetting(115) == 1;
    }

    public static boolean setNoted() {
        RSInterfaceChild noteButton = Interfaces.get(12, 25);
        if (Game.getSetting(115) != 1 && noteButton != null &&
            noteButton.click()){
            Timer.waitCondition(BankManager::areNotesOn, 2000, 3000);
        }
        return areNotesOn();
    }

    public static boolean setUnNoted() {
        RSInterface button = Interfaces.findWhereAction("Item", 12);
        RSInterfaceChild itemButton = Interfaces.get(12, 23);
        Log.log("[BankManager]: Selecting Unnoted items");
        if (Game.getSetting(115) == 1 && button != null &&
                button.click()){
            Timer.waitCondition(() -> !BankManager.areNotesOn(), 2000, 3000);
        }
        return !areNotesOn();
    }
    public static boolean toggleNoted(boolean enable) {
        return enable ? setNoted() : setUnNoted();
    }

    public static boolean inventoryChange(boolean increase) {
        final int count = Inventory.getAll().length;
        General.sleep(100);
        return increase ? count < Inventory.getAll().length : count > Inventory.getAll().length;
        // says if true in the method call parameters, returns that inventory has increased size (length),
    }


    public static boolean checkEquippedGlory() {
        if (!Equipment.isEquipped(CHARGED_GLORIES)) {
            Log.log("[Debug]: Need to replace equipped Glory");
            BankManager.open(true);

            if (Inventory.isFull())
                BankManager.depositAll(true);

            BankManager.withdrawArray(ItemId.AMULET_OF_GLORY, 1);
            Timer.waitCondition(() -> Inventory.find(ItemId.AMULET_OF_GLORY).length > 0, 2500, 4000);
            RSItem[] invGlory = Inventory.find(ItemId.AMULET_OF_GLORY);
            if (invGlory.length > 0) {
                if (invGlory[0].click("Wear"))
                    return Timer.waitCondition(() -> Equipment.isEquipped(ItemId.AMULET_OF_GLORY), 2500, 4000);
            } else
                return false;
        }
        return true;
    }


    public static boolean checkCombatBracelet() {
        if (!Equipment.isEquipped(11118, 11120, 11122, 11124, 11972, 11974)) {
            BankManager.open(true);
            if (Banking.isBankScreenOpen()) {
                if (withdraw(1, true, ItemId.COMBAT_BRACELET)) {
                    RSItem[] invItem = Inventory.find(COMBAT_BRACELET);
                    if (invItem.length > 0) {
                        return AccurateMouse.click(invItem[0], "Wear");
                    }
                }
            }
        } else {
            Log.log("[Debug]: We already have a charged combat bracelet.");
            return true;
        }
        return false;
    }


    public static boolean getTeleItem(int itemID, int alternate1, int alternate2, int alternate3) { // need a check for having inventory items
        RSItem[] bankCount = Banking.find(itemID);
        Log.log("[BankManager] bankCount length = " + bankCount.length);
        if (bankCount.length > 0) { //checks if we have the item
            String itemName = RSItemDefinition.get(itemID).getName(); // converts ID to its actual name
            Log.log("[Debug]: Getting: " + itemName);  // prints the actual item name from the ID entered
            BankManager.withdraw(1, true, itemID);
            return Timer.waitCondition(() -> inventoryChange(true), 2500);

        } else {
            Log.log("[BankManager]: Missing item: " + itemID);
            bankCount = Banking.find(alternate1);
            if (bankCount.length > 0) {
                String itemName = RSItemDefinition.get(alternate1).getName(); // converts ID to its actual name
                Log.log("[Debug]: Getting: " + itemName);  // prints the actual item name from the ID entered
                BankManager.withdraw(1, true, alternate1);
                return Timer.waitCondition(() -> inventoryChange(true), 2500);

            } else {
                Log.log("[BankManager]: Missing item: " + alternate1);
                bankCount = Banking.find(alternate2);
                if (bankCount.length > 0) {
                    String itemName = RSItemDefinition.get(alternate2).getName(); // converts ID to its actual name
                    Log.log("[Debug]: Getting: " + itemName);  // prints the actual item name from the ID entered
                    BankManager.withdraw(1, true, alternate2);
                    return Timer.waitCondition(() -> inventoryChange(true), 2500);

                } else {
                    Log.log("[BankManager]: Missing item: " + alternate2);
                    bankCount = Banking.find(alternate3);
                    if (bankCount.length > 0) {
                        String itemName = RSItemDefinition.get(alternate3).getName(); // converts ID to its actual name
                        Log.log("[Debug]: Getting: " + itemName);  // prints the actual item name from the ID entered
                        BankManager.withdraw(1, true, alternate3);
                        return Timer.waitCondition(() -> inventoryChange(true), 2500);

                    }
                }
            }
        }
        General.sleep(General.random(500, 2000));
        Log.log("[BankManager]: None of the entered items were found or in sufficient quantities.");
        return false;
    }

    public static boolean getPotion(int[] potionArray) { // need a check for having inventory items
        RSItem[] bankCount = Banking.find(potionArray[0]);
        if (bankCount.length > 0) { //checks if we have the item
            String itemName = RSItemDefinition.get(potionArray[0]).getName(); // converts ID to its actual name
            Log.log("[BankManager]: Getting: " + itemName);  // prints the actual item name from the ID entered
            BankManager.withdraw(1, true, potionArray[0]);
            return Timer.waitCondition(() -> inventoryChange(true), 2500);

        } else {
            Log.log("[BankManager]: Missing item: " + potionArray[0]);
            bankCount = Banking.find(potionArray[1]);
            if (bankCount.length > 0) {
                String itemName = RSItemDefinition.get(potionArray[1]).getName(); // converts ID to its actual name
                Log.log("[Debug]: Getting: " + itemName);  // prints the actual item name from the ID entered
                BankManager.withdraw(1, true, potionArray[1]);
                return Timer.waitCondition(() -> inventoryChange(true), 2500);

            } else {
                Log.log("[BankManager]: Missing item: " + potionArray[1]);
                bankCount = Banking.find(potionArray[2]);
                if (bankCount.length > 0) {
                    String itemName = RSItemDefinition.get(potionArray[2]).getName(); // converts ID to its actual name
                    Log.log("[Debug]: Getting: " + itemName);  // prints the actual item name from the ID entered
                    BankManager.withdraw(1, true, potionArray[2]);
                    return Timer.waitCondition(() -> inventoryChange(true), 2500);

                } else {
                    Log.log("[BankManager]: Missing item: " + potionArray[2]);
                    bankCount = Banking.find(potionArray[3]);
                    if (bankCount.length > 0) {
                        String itemName = RSItemDefinition.get(potionArray[3]).getName(); // converts ID to its actual name
                        Log.log("[Debug]: Getting: " + itemName);  // prints the actual item name from the ID entered
                        BankManager.withdraw(1, true, potionArray[3]);
                        return Timer.waitCondition(() -> inventoryChange(true), 2500);

                    }
                }
            }
        }
        General.sleep(General.random(500, 2000));
        Log.log("[Debug]: None of the entered items were found or in sufficient quantities.");
        return false;
    }

}
