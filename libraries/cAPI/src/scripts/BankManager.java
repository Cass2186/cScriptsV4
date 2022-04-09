package scripts;

import dax.api_lib.DaxWalker;
import dax.walker.utils.AccurateMouse;
import dax.walker.utils.TribotUtil;
import org.apache.commons.lang3.StringUtils;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.*;
import org.tribot.api2007.types.*;
import org.tribot.script.sdk.Bank;
import org.tribot.script.sdk.BankSettings;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.cache.BankCache;
import org.tribot.script.sdk.interfaces.Item;
import org.tribot.script.sdk.interfaces.Stackable;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.tasks.BankTask;
import org.tribot.script.sdk.tasks.BankTaskError;
import org.tribot.script.sdk.tasks.InsufficientItemError;
import org.tribot.script.sdk.types.InventoryItem;
import scripts.Requirements.ItemReq;

import java.awt.event.KeyEvent;
import java.util.*;
import java.util.stream.Collectors;

public class BankManager {

    private static final String CHARGED_GLORY = "Amulet of glory(";
    private static final String UNCHARGED_GLORY = "Amulet of glory";
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


    public static ArrayList<RSItem> items = new ArrayList<RSItem>();
    public static List<RSItem> list;

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




    public void handleBankTaskError(BankTaskError err) {

    }

    private static void withdrawInvItems(List<ItemReq> invReqs) {
        // List<Pair<Integer, Integer>> withdrew = new ArrayList<>();
        HashMap<Integer, Integer> withdrew = new HashMap();
        invReqs.stream()
                //.map(i-> new Pair(i.getId(), (i.getAmount()- Inventory.getCount(i.getId()))))
                .filter(i -> i.getAmount() > 0)
                //  .sortedBy(it.second.endInclusive)
                .forEach(item -> {
                    int id = item.getId();
                    boolean isNoted = item.isItemNoted();
                    int amt = item.getAmount();
                    int startInvCount = org.tribot.script.sdk.Inventory.getCount(id);
                    int bankCount = BankCache.getStack(id);

                    // Check if we have the amount we need in the bank. If not, bind an error
                    if (bankCount < amt) {
                        Log.log("[Bank]: Insufficient item in bank with ID: " + id);

                        if (item.getAlternateItemIDs().
                                stream().anyMatch(jar -> BankCache.getStack(jar) >= item.getAmount())) {
                            // we have an acceptable alternate in bank
                            Optional<Integer> optional = item
                                    .getAlternateItemIDs().
                                    stream()
                                    .filter(i -> BankCache.getStack(i) >= item.getAmount()).findAny();
                            if (optional.isPresent()) {
                                id = optional.get();
                            }
                        }
                    }
                    if (bankCount < amt) {
                        // Special case: If we don't need any of this item and there is none in the bank, skip
                        //  if (bankCount == 0 && amt == 0)
                        //     return; //@forEach


                        if (BankSettings.isNoteEnabled() != isNoted) {
                            BankSettings.setNoteEnabled(isNoted);
                            Waiting.waitNormal(85, 15);
                        }

                        Bank.withdrawAll(id);
                    } else {
                        if (BankSettings.isNoteEnabled() != isNoted) {
                            BankSettings.setNoteEnabled(isNoted);
                            Waiting.waitNormal(85, 15);
                        }
                        amt = (amt - startInvCount);
                        //prevents attempting to withdraw if we have enough
                        if (amt > 0)
                            Bank.withdraw(id, amt - startInvCount);
                    }

                    withdrew.put(id, startInvCount);
                    Waiting.waitNormal(69, 16);
                });
        // Wait and confirm all inv items were withdrawn correctly
        for (Integer i : withdrew.keySet()) {
            Waiting.waitUntil(2500, () ->
                    org.tribot.script.sdk.Inventory.getCount(i) > withdrew.get(i));
        }
    }


    private static void withdrawAndEquipItems(List<ItemReq> equipReqs) {
        //filter our list to items that are supposed to be equipped but fail the check (i.e. aren't equipped or enough)
        List<ItemReq> equipmentToWithdrawAndEquip = equipReqs.stream().filter(req ->
                req.isShouldEquip() && !req.check()).collect(Collectors.toList());

        var currentInvItems = org.tribot.script.sdk.Inventory.getAll();

        if (currentInvItems.size() == 28 && equipmentToWithdrawAndEquip.size() > 0) {
            // Oh no, our inv is full of stuff we need but we need to equip things
            Bank.deposit(currentInvItems.get(currentInvItems.size() - 1).getId(), 1); // Make room
            Waiting.waitUntil(2000, () -> org.tribot.script.sdk.Inventory.getAll().size() < 28);
            Waiting.waitNormal(345, 67);
            currentInvItems = org.tribot.script.sdk.Inventory.getAll(); // reset inv to reflect current state
        }

        // We will equip items using the amount of free inv space we have.
        // So if we have 1 space, we will withdraw 1 item, equip, repeat.
        // If we have more spaces than equipment to wear, then we withdraw everything up front
        int invFreeSpaces = 28 - currentInvItems.size();
        int i = 0;
        while (i < equipmentToWithdrawAndEquip.size()) {
            Waiting.wait(25);
            List<Integer> skippedIndices = new ArrayList<>();

            int i2 = i;
            while (i2 < equipmentToWithdrawAndEquip.size() && (i2 - i - skippedIndices.size()) < invFreeSpaces) {
                Waiting.wait(25);
                int id = equipmentToWithdrawAndEquip.get(i2).getId();
                int amt = equipmentToWithdrawAndEquip.get(i2).getAmount();
                // val slot = equipReqs[i2].slot
                Optional<Item> bankOptionalItem = Bank.getAll()
                        .stream().filter(item -> item.getId() == id)
                        .findFirst();
                int bankCount = bankOptionalItem.map(Stackable::getStack).orElse(0);

                // Check if we have the amount we need in the bank. If not, bind an error
                if (bankCount < amt) {
                    Log.log("[Banking]: We have insufficient item: " + id + " | Need: " + amt);
                    //InsufficientEquipmentError(slot, id, amt.start, bankCount).toEither < Unit > ().bind()
                }

                // If we have nothing in the bank, it's okay to just continue at this point because
                // what we have equipped is sufficient
                if (bankCount != 0) {

                    if (BankSettings.isNoteEnabled()) {
                        BankSettings.setNoteEnabled(false);
                        Waiting.waitNormal(85, 15);
                    }

                    if (bankCount <= amt) {
                        Bank.withdrawAll(id);
                    } else {
                        Bank.withdraw(id, amt);
                    }

                    Waiting.waitNormal(67, 13);
                } else {
                    skippedIndices.add(i2);
                }

                i2++;
            }

            // Wait for everything to actually appear in the inventory and then equip
            for (int x = 0; x < i2; x++) {
                // If we skipped this item, we don't need to equip it
                if (skippedIndices.contains(x)) {
                    continue;
                }

                int id = equipmentToWithdrawAndEquip.get(x).getId();
                Waiting.waitUntil(2500, () -> org.tribot.script.sdk.Inventory.getCount(id) > 0);

                InventoryItem item = Query.inventory().idEquals(id).findFirst().orElse(null);
                if (item != null) {
                    if (org.tribot.script.sdk.Equipment.equip(item))
                        Waiting.waitNormal(121, 30);
                } else {
                    //UnknownError.toEither<Unit> ().bind()
                }
            }

            // Confirm everything is equipped
            for (int x = 0; x < i2; x++) {
                // If we skipped this item, we don't need to wait for it to be equipped
                if (skippedIndices.contains(x)) {
                    continue;
                }
                int y = x;
                Waiting.waitUntil(2500, () ->
                        org.tribot.script.sdk.Equipment.contains(equipmentToWithdrawAndEquip.get(y).getId()));
            }
            i = i2;
        }
    }

  /*  private void depositInventory( List<ItemReq> invReqs, List<EquipmentReq>  equipReqs) {
        // First get all the maximum withdraws. The amount will be negative if we need to deposit
        List<ItemReq> withdrawsToMake = invReqs.stream().map(it ->
                new ItemReq(it.getId(), (it.getAmount() - Inventory.getCount(it.getId()))))
                .collect(Collectors.toList());

        val equipmentWithdrawsToMake = equipReqs
                .filter { !it.isSatisfied() }
            .mapNotNull { it.item }
            .map {
            Pair(it.first, it.second.getWithdrawAmount(Inventory.getCount(it.first)).endInclusive)
        }

        // Let's calculate all the completely unrelated items that we need to deposit all of
        val unrelatedItemDepositsToMake = Inventory.getAll()
                .distinctBy { it.id }
            .filter { invItem -> withdrawsToMake.none { it.first == invItem.id } }
            .map { Pair(it.id, Inventory.getCount(it.id)) }

        // And combine them with the "withdraws" that are negative (aka deposits)
        val combined = withdrawsToMake.filter { it.second < 0 } +
                equipmentWithdrawsToMake.filter { it.second < 0 } +
                unrelatedItemDepositsToMake;

        if (withdrawsToMake.any { it.second <= 0 }) {
            // We must have something in our inv that we need if we have a negative or 0 withdraw
            val currentInvState = combined.map { Inventory.getCount(it.first) }

            // Iterate and execute on the deposits
            for (entry in combined) {
                Bank.deposit(entry.first, abs(entry.second)).bind()
                Waiting.waitNormal(192, 29)
            }

            // Wait for the deposits to register
            combined.forEachIndexed { i, entry ->
                    val expectedNewAmt = currentInvState[i] - abs(entry.second)
                Waiting.waitUntil(2500) { Inventory.getCount(entry.first) == expectedNewAmt }.bind()
            }
        }
        else if (Inventory.getAll().isNotEmpty()) {
            // We have nothing we need, so deposit all if inv contains anything
            Bank.depositInventory().bind()
            Waiting.waitUntil(2500) { Inventory.getAll().size == 0 }.bind()
            Waiting.waitNormal(254, 38)
        }
    }*/


    public static void withdrawList(List<ItemReq> itemReqList) {
        for (int i = 0; i < 5; i++) {
            if (itemReqList.stream().anyMatch(it -> !it.check())) {
                if (!Bank.isOpen()) {
                    Bank.open();
                    Waiting.waitNormal(375, 45);
                }
                //on first or last attempt
                if (i == 0 || i == 4)
                    Bank.depositInventory();

                // Deposit everything that needs deposited
                //depositInventory(invReqs, equipReqs);
                //  depositEquipment(equipReqs);

                withdrawAndEquipItems(itemReqList);

                withdrawInvItems(itemReqList);
                if (itemReqList.stream().noneMatch(it -> !it.check()))
                    Bank.close();
            } else
                break;
        }
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
    private static boolean checkWithdrawSuccess(int quantity, int ItemID) {
        Timer.waitCondition(() -> Inventory.find(ItemID).length > 0, 800, 1500);
        if (Inventory.find(ItemID).length < 1 && !Inventory.isFull()) {
            Log.log("[Debug]: Seemingly failed to get item, trying again.");
            return BankManager.withdraw(quantity, true, ItemID);
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
            Log.log("[BankManager]: Cached Items: " + myItems.length);
        }
    }


    public static void determinePurchaseAmounts(int ItemID, int quanitity) {
        for (int i = 0; i < items.size(); i++) {
            stack = 0;
            String itemName = RSItemDefinition.get(ItemID).getName();
            if (ItemID == items.get(i).getID()) {
                stack = items.get(i).getStack();
                if (stack > 0) {
                    Log.log("[Debug]: We already have " + stack + " of " + itemName);
                    itemsToBuy = quanitity - stack;
                    return;

                }/*else if (itemsToBuy < 1) {

                } */
            }
        }
        String itemName = RSItemDefinition.get(ItemID).getName();
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
            if (Timer.waitCondition(() -> Inventory.getAll().length == 0, 2000, 5000)) {
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


    public static boolean withdraw(int num, boolean shouldWait, int ItemID) {
        if (!Bank.isOpen())
            open(true);

        closeHelpWindow();

        RSItem[] item = Banking.find(ItemID);

        if (item.length < 1) {
            String name = RSItemDefinition.get(ItemID).getName();
            if (name != null)
                Log.log("[BankManager]: Missing bank item: " + name);
            else
                Log.log("[BankManager]: Missing bank item: " + ItemID);
            return false;
        }

        // we will try up to 5 times to withdraw
        for (int i = 0; i < 5; i++) {
            if (!shouldWait) {
                // if we aren't waiting for inventory to change we
                if (Banking.withdraw(num, ItemID)) {
                    Utils.microSleep();
                    return true;

                } else
                    Utils.microSleep();

            } else {

                if (Banking.withdraw(num, ItemID)) {
                    if (Timer.waitCondition(() -> inventoryChange(true) || Inventory.find(ItemID).length > 0, 4000, 6000))
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
     * @param ItemID
     * @return
     */
    public static boolean withdraw(int num, boolean shouldWait, int[] ItemID) {
        General.sleep(General.randomSD(50, 400, 250, 75)); // sleep here so withdraws are not back to back speedy

        if (!Bank.isOpen())
            open(true);

        closeHelpWindow();
        closeBankSearch();

        RSItem[] item = Banking.find(ItemID);
        if (item.length < 1) {
            Log.log("[Debug]: Missing Bank item: " + RSItemDefinition.get(ItemID[0]).getName());
            return false;
        }

        if (Inventory.isFull() && num == 0)
            BankManager.depositAll(true);

        for (int i = 0; i < 5; i++) {
            if (!shouldWait) {

                if (Banking.withdraw(num, ItemID)) {
                    Utils.microSleep();
                    return true;

                } else
                    Utils.microSleep();

            } else {

                if (Banking.withdraw(num, ItemID)) {
                    if (Timer.waitCondition(() -> inventoryChange(true) || Inventory.find(ItemID).length > 0, 4000, 6000))
                        return true;

                    else
                        Utils.microSleep();
                }
            }
        }
        return false;
    }

    public static boolean withdraw(int num, int ItemID, boolean endIfNoItem) {
        if (!Banking.isBankScreenOpen()) {
            open(true);
            closeHelpWindow();
        }

        closeBankSearch();
        RSItem[] item = Banking.find(ItemID);
        if (item.length < 1) {

            if (endIfNoItem) {
                Log.log("[BankManager]: Missing bank item: " + RSItemDefinition.get(ItemID).getName());
                throw new IllegalStateException("Missing bank Item: " + RSItemDefinition.get(ItemID).getName());

            } else {
                Log.log("[BankManager]: Missing bank item: " + RSItemDefinition.get(ItemID).getName());
                return false;
            }

        } else if (item[0].getStack() < num) {
            Log.log("[BankManager]: Missing bank item: " + RSItemDefinition.get(ItemID).getName());
            throw new IllegalStateException("[BankManager Error]: Missing bank Item in adequate amounts: " + RSItemDefinition.get(ItemID).getName());
        }

        if (Inventory.isFull()) {
            Log.log("[BankManager]: Inventory is full depositing all items (Check script code to prevent this in the future)");
            BankManager.depositAll(true);
        }

        for (int i = 0; i < 5; i++) {
            if (Banking.withdraw(num, ItemID)) {
                return Timer.waitCondition(() -> inventoryChange(true), 2500, 6000);

            } else
                Utils.microSleep();
        }
        return false;
    }

    public static boolean withdraw(boolean restockItem, int num, int ItemID) {
        if (!Banking.isBankScreenOpen()) {
            open(true);
            if (Interfaces.isInterfaceSubstantiated(664, 28))
                if (Interfaces.get(664, 28, 0).click())
                    Timer.waitCondition(() -> !Interfaces.isInterfaceSubstantiated(664, 28), 4000);
        }
        closeBankSearch();
        RSItem[] item = Banking.find(ItemID);
        if (item.length < 1) {

            if (restockItem) {
                Log.log("[BankManager]: Missing item: " + RSItemDefinition.get(ItemID).getName());
                return true;

            } else {
                Log.log("[BankManager]: Missing item: " + RSItemDefinition.get(ItemID).getName());
                return false;
            }

        } else if (item[0].getStack() < num) {
            Log.log("[Debug]: Missing adequate amounts of item: " + RSItemDefinition.get(ItemID).getName());
            return false;
        }

        if (Inventory.isFull()) {
            Log.log("[BankManager]: Inventory is full depositing all items (Check script code to prevent this in the future)");
            BankManager.depositAll(true);
        }

        for (int i = 0; i < 5; i++) {
            if (Banking.withdraw(num, ItemID))
                return true;

            else
                Utils.microSleep();
        }
        return false;
    }

    public static boolean withdraw(int num, int ItemID) { // same as above but default waits
        if (!Banking.isBankScreenOpen()) {
            open(true);
            if (Interfaces.isInterfaceSubstantiated(664, 28))
                if (Interfaces.get(664, 28, 0).click())
                    Timer.waitCondition(() -> !Interfaces.isInterfaceSubstantiated(664, 28), 4000);
        }
        closeBankSearch();
        RSItem[] item = Banking.find(ItemID);
        if (item.length < 1) {
            return false;
        }


        return Banking.withdrawItem(item[0], num) || Timer.waitCondition(() -> inventoryChange(true), 2500);
    }

    public static boolean withdraw2(int num, boolean shouldWait, int ItemID) {
        if (!Banking.isBankScreenOpen()) {
            open(true);
            if (Interfaces.isInterfaceSubstantiated(664, 28))
                if (Interfaces.get(664, 28, 0).click())
                    Timer.waitCondition(() -> !Interfaces.isInterfaceSubstantiated(664, 28), 4000);
        }
        RSItem[] item = Banking.find(ItemID);
        if (item.length < 1) {
            Log.log("[BankManager]: Missing item: " + RSItemDefinition.get(ItemID).getName());
            return false;
        }
        if (Inventory.isFull() && num == 0) {
            BankManager.depositAll(true);
        }
        if (Banking.withdrawItem(item[0], num))
            Timer.waitCondition(() -> inventoryChange(true), 2500);
        return checkWithdrawSuccess(num, ItemID);
    }


    public static boolean deposit(int num, int ItemID) {
        if (!Bank.isOpen())
            open(true);

        closeHelpWindow();

        Banking.deposit(num, ItemID);
        RSItem[] item = Inventory.find(ItemID);
        return item.length <= 0 || !Banking.deposit(num, ItemID) || Timer.waitCondition(() -> inventoryChange(true), 2500);
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
                noteButton.click()) {
            Timer.waitCondition(BankManager::areNotesOn, 2000, 3000);
        }
        return areNotesOn();
    }

    public static boolean setUnNoted() {
        RSInterface button = Interfaces.findWhereAction("Item", 12);
        RSInterfaceChild itemButton = Interfaces.get(12, 23);
        Log.log("[BankManager]: Selecting Unnoted items");
        if (Game.getSetting(115) == 1 && button != null &&
                button.click()) {
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

    private static Set<String> getItemsNotWorn(Set<String> items) {
        return items.stream()
                .filter(item -> !Equipment.isEquipped(item))
                .collect(Collectors.toSet());
    }

    public static boolean withdrawAndEquip(Set<String> itemsToEquip) {

        Set<String> itemsNotWorn = getItemsNotWorn(itemsToEquip);

        boolean withdrawn = itemsNotWorn.stream()
                .filter(item -> Inventory.getCount(item) == 0)
                .allMatch(item -> Banking.withdraw(1, item));

        if (withdrawn
                && Timer.waitCondition(() -> Inventory.getAllList().stream()
                .map(TribotUtil::getName)
                .collect(Collectors.toSet()).containsAll(itemsNotWorn), General.random(2000, 3000))) {

            boolean worn = Inventory.findList(rsItem -> itemsNotWorn.contains(TribotUtil.getName(rsItem))).stream()
                    .allMatch(rsItem -> AccurateMouse.click(rsItem, "Wear", "Equip"));

            if (worn && Timer.waitCondition(() -> getItemsNotWorn(itemsToEquip).size() == 0, General.random(1000, 2000))) {
                Waiting.waitNormal(100,25);
                return true;
            }
        }

        return false;

    }




    public static boolean withdrawGloryIfRequired() {
        if (Optional.ofNullable(Equipment.getItem(Equipment.SLOTS.AMULET))
                .map(TribotUtil::getName)
                .filter(itemName -> itemName.contains(CHARGED_GLORY))
                .isPresent()) {
            //We have a glory already
            return true;
        }

        return Arrays.stream(Banking.find(rsItem -> TribotUtil.getName(rsItem).contains(CHARGED_GLORY)))
                .map(TribotUtil::getName)
                .filter(StringUtils::isNotBlank)
                .sorted()
                .findFirst()
                .map(Collections::singleton)
                .map(BankManager::withdrawAndEquip)
                .orElse(false)
                && Banking.deposit(0, UNCHARGED_GLORY);
    }


    public static boolean checkEquippedGlory() {
        if (!Equipment.isEquipped(CHARGED_GLORIES)) {
            Log.log("[Debug]: Need to replace equipped Glory");
            BankManager.open(true);

            if (Inventory.isFull())
                BankManager.depositAll(true);

            BankManager.withdrawArray(
                    ItemID.AMULET_OF_GLORY
                  , 1);

            Timer.waitCondition(() -> Inventory.find(ItemID.AMULET_OF_GLORY).length > 0, 2500, 4000);
            RSItem[] invGlory = Inventory.find(ItemID.AMULET_OF_GLORY);
            if (invGlory.length > 0) {
                if (invGlory[0].click("Wear"))
                    return Timer.waitCondition(() -> Equipment.isEquipped(ItemID.AMULET_OF_GLORY), 2500, 4000);
            } else
                return false;
        }
        return true;
    }


    public static boolean checkCombatBracelet() {
        if (!Equipment.isEquipped(11118, 11120, 11122, 11124, 11972, 11974)) {
            BankManager.open(true);
            if (Banking.isBankScreenOpen()) {
                if (withdraw(1, true, ItemID.COMBAT_BRACELET)) {
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


    public static boolean getTeleItem(int ItemID, int alternate1, int alternate2, int alternate3) { // need a check for having inventory items
        RSItem[] bankCount = Banking.find(ItemID);
        Log.log("[BankManager] bankCount length = " + bankCount.length);
        if (bankCount.length > 0) { //checks if we have the item
            String itemName = RSItemDefinition.get(ItemID).getName(); // converts ID to its actual name
            Log.log("[Debug]: Getting: " + itemName);  // prints the actual item name from the ID entered
            BankManager.withdraw(1, true, ItemID);
            return Timer.waitCondition(() -> inventoryChange(true), 2500);

        } else {
            Log.log("[BankManager]: Missing item: " + ItemID);
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
