package scripts.Tasks;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.*;
import org.tribot.api2007.types.*;
import org.tribot.script.sdk.Bank;
import org.tribot.script.sdk.GameState;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.cache.BankCache;
import org.tribot.script.sdk.interfaces.Item;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.InventoryItem;
import org.tribot.script.sdk.walking.GlobalWalking;
import scripts.BankManager;
import scripts.Requirements.ItemReq;
import scripts.Timer;

import java.util.*;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BankTask {

    public HashMap<String, BankCache> bankCacheHashMap = new HashMap<>();

    public LinkedHashMap<String, ItemReq> withdrawList = new LinkedHashMap<>();

    String finalItem;

    public static BankCache bankCache;

    public BankTask() {
       // super();
        bankCache = new BankCache();

    }

    public static boolean setWithdrawNoted(boolean enable) {
        return enable ? setNoted() : setUnNoted();
    }


    public static boolean areNotesOn() {
        return GameState.getSetting(115) == 1;
    }

    public static boolean setNoted() {
        RSInterface noteButton = Interfaces.findWhereAction("Note", 12);
        if (!areNotesOn() && noteButton != null &&
                noteButton.click()){
            Timer.waitCondition(BankManager::areNotesOn, 2000, 3000);
        }
        return areNotesOn();
    }

    public static boolean setUnNoted() {
        RSInterface button = Interfaces.findWhereAction("Item", 12);
        Log.log("[BankManager]: Selecting Unnoted items");
        if (areNotesOn() && button != null && button.click()){
            Timer.waitCondition(() -> !BankManager.areNotesOn(), 2000, 3000);
        }
        return !areNotesOn();
    }

    public static List<String> expandItemName(String name) {
        ArrayList<String> names = new ArrayList<>();
        Pattern pattern = Pattern.compile("^(.*?)([0-9]+)~([0-9]+)(.*?)$");
        Matcher matcher = pattern.matcher(name);
        if (matcher.find()) {
            String prepend = matcher.group(1), append = matcher.group(4);
            int start = Integer.parseInt(matcher.group(2)), finish = Integer.parseInt(matcher.group(3)),
                    dir = start > finish ? -1 : 1;
            for (int i = start; i * dir <= finish * dir; i += dir) {
                names.add(prepend + i + append);
            }
        } else {
            pattern = Pattern.compile("^(.*?)\\{(.*?)}(.*?)$");
            matcher = pattern.matcher(name);
            if (matcher.find()) {
                String prepend = matcher.group(1), append = matcher.group(3);
                String[] tings = matcher.group(2).split(";");
                for (String t : tings) {
                    names.add((prepend + t + append).trim());
                }
            } else {
                names.add(name);
            }
        }
        return names;
    }

    public void step()  {
        if (GrandExchange.getWindowState() != null) {
            GrandExchange.close();
            Timing.waitCondition(() -> GrandExchange.getWindowState() == null, 4000);
        } else if (!Banking.isBankScreenOpen()) {
            RSObject[] bank = org.tribot.api2007.Objects.find(20, "Bank booth");
            RSNPC[] banker = NPCs.find("Banker");
            RSObject[] chest =  org.tribot.api2007.Objects.find(20, "Bank chest", "Open chest");
            if (bank.length < 1 && banker.length < 1 && chest.length < 1) {
                General.println("BankEvent: Walking to closest bank");
                GlobalWalking.walkToBank();
                Timing.waitCondition(() -> Banking.isInBank() || !Player.getRSPlayer().isMoving(), 5000);
            } else if (Bank.open()) {
                General.println("BankEvent opening bank");
                Waiting.waitUntil(5000, Bank::isOpen);
                updateCache();
            }
            return;
        }

        if (!Banking.isBankLoaded()) {
            return;
        }

        /*if (!InventoryEvent.containsOnly(arryOfItemsToWithdraw())) {
            General.println("Banking unreqired items");
            Banking.depositAllExcept(arryOfItemsToWithdraw());
            Timing.waitCondition(() -> InventoryEvent.containsOnly(arryOfItemsToWithdraw()), 6000);
        }
        for (Map.Entry<String, RequisitionItem> withdrawList : withdrawList.entrySet()) {
            RequisitionItem reqItem = withdrawList.getValue();
            String itemName = reqItem.getName();
            int amount = reqItem.getQty();
            Supplier<Boolean> itemCondition = reqItem.getCondition();
            boolean noted = reqItem.getNoted();
            finalItem = "";

            if (itemName.contains("~")) {
                List<String> expandedItem = expandItemName(itemName);
                for (String item : expandedItem) {
                    if (contains(item)) {
                        finalItem = item;
                        break;
                    }
                }
            } else {
                finalItem = itemName;
            }
            if (finalItem.equals("")) {
                continue;
            }
            RSItem finalRsItem = InventoryEvent.getInventoryItem(finalItem);
            RSItemDefinition finalItemDefinition = null;
            if (finalRsItem != null) {
                finalItemDefinition = finalRsItem.getDefinition();
            }
            if (InventoryEvent.contains(finalRsItem) && finalItemDefinition != null && !finalItemDefinition.isNoted() && noted) {
                General.println("Depositing item: " + finalItem + " we need noted");
                Banking.deposit(InventoryEvent.getCount(itemName), itemName);
                Timing.waitCondition(() -> !InventoryEvent.contains(finalRsItem), 2000);
            } else if (!bankCacheHashMap.containsKey(finalItem) || !contains(finalItem)) {
                General.println("Stopping we dont have item '" + finalItem + "' in bank");
                updateCache();
                org.tribot.script.sdk.cache.BankCache.update();
                setComplete();
            } else if (!InventoryEvent.contains(finalItem)) {
                if (!itemCondition.get()) {
                    General.println("Skipping do to not needing item");
                    continue;
                }
                boolean setStatus = setWithdrawNoted(noted);
                if (!setStatus) {
                    continue;
                } else if (noted) {
                    General.println("Withdrawing noted: " + finalItem);
                }
                if (Banking.withdraw(amount, finalItem)) {
                    Timing.waitCondition(() -> InventoryEvent.contains(finalItem), 2000);
                }
            } else if (InventoryEvent.contains(finalItem) && finalRsItem != null) {
                boolean isStackable = finalItemDefinition != null && finalItemDefinition.isStackable();
                int itemCount = isStackable ? finalRsItem.getStack() : InventoryEvent.getCount(finalItem);
                boolean shouldWithdraw = itemCount < amount;
                boolean setWithdrawStatus = setWithdrawNoted(noted);
                BooleanSupplier bankWaitCondition = isStackable ? () -> InventoryEvent.getStackedCount(finalItem) == amount : () -> InventoryEvent.getCount(finalItem) == amount;
                log("Trying to withdraw: "+finalItem);
                if (shouldWithdraw && Banking.withdraw(amount - itemCount, finalItem)) {
                    log("Succesfully withdrew: "+finalItem + (amount-itemCount));
                    Timing.waitCondition(bankWaitCondition, 2000);
                } else {
                    log("We fuggin up?: ");
                    General.sleep(General.random(50, 250));
                }
            }
        }
        General.println("Trying to update cache");
        updateCache();
        org.tribot.script.sdk.cache.BankCache.update();
        Banking.close();
        setComplete();*/
    }

    public void setWithdrawList(String itemName, int amount, boolean noted, Supplier<Boolean> condition) {
        if (withdrawList.containsKey(itemName)) {
            if (withdrawList.get(itemName).getAmount() != amount) {
               // withdrawList.replace(itemName, new ItemReq(itemName, amount, noted, condition));
            }
        } else {
           // withdrawList.put(itemName, new ItemReq(itemName, amount, noted, condition));
        }
    }

    public BankTask addReq(String itemName, int amount) {
        setWithdrawList(itemName, amount, false, () -> true);
        return this;
    }

    public BankTask addReq(String itemName, int amount, Supplier<Boolean> condition) {
        setWithdrawList(itemName, amount, false, condition);
        return this;
    }

    public BankTask addReq(String itemName, int amount, boolean noted) {
        setWithdrawList(itemName, amount, noted, () -> true);
        return this;
    }

    public BankTask addReq(String itemName, int amount, boolean noted, Supplier<Boolean> condition) {
        setWithdrawList(itemName, amount, noted, condition);
        return this;
    }

    public String[] arrayOfItemsToWithdraw() {
        return withdrawList.keySet().toArray(new String[0]);
    }


    public RSItem getBankItem(String... itemNames) {
        RSItem[] items = Banking.find(itemNames);
        for (RSItem item : items) {
            if (item != null) {
                return item;
            }
        }
        return null;
    }

    public int getCount(String... itemNames) {
        int amount = 0;
        if (getBankItem(itemNames) != null) {
            amount = getBankItem(itemNames).getStack();
        }
        return amount;
    }

    public boolean contains(String itemName) {
        Optional<Item> item = Query.bank().nameEquals(itemName).findFirst();
        return item.isPresent();
    }

    public void bankEquipment() {
        if (!Bank.open()) {
            if (!Bank.isNearby()) {
                GlobalWalking.walkToBank();
            } else if (Bank.open()) {
                Timer.waitCondition(Bank::ensureOpen, 2000);
            }
        } else {
            if (Bank.depositEquipment()) {
                Timer.waitCondition(() -> Equipment.getItems().length == 0, 2000);
            }
        }
    }

    public boolean openBank() {
        if (!Banking.isBankScreenOpen()) {
            if(Bank.isNearby()) {
                if(Bank.open()) {
                    Waiting.waitUntil(5000, () ->Bank.isOpen());
                }
            } else {
                if(GlobalWalking.walkToBank()) {
                    Waiting.waitUntil(10000, () -> Bank.isNearby());
                }
            }
        } else {
            depositAll();
            Waiting.waitNormal(500,75);
            updateCache();
            BankCache.update();
            return true;
        }
        return false;
    }

    public void depositAll() {
        if (Bank.isOpen()) {
            if (Inventory.getAll().length > 0) {
                Bank.depositInventory();
            }
        }
    }

    public boolean needCache() {
        return bankCacheHashMap.size() <= 0;
    }

    public void updateItem(String itemName, int id, int amount) {
        if (bankCacheHashMap.containsKey(itemName)) {
           //bankCacheHashMap.replace(itemName, new BankCache(itemName, id, amount));
        } else {
           // bankCacheHashMap.put(itemName, new BankCache(itemName, id, amount));
        }
    }

    public void updateCache() {
        if (!Bank.isOpen() && !Bank.ensureOpen()) {
            Log.log("[BankTask]: Bank is not open cannot continue");
        } else {
            bankCacheHashMap = new HashMap<>();
            RSItem[] bankCache = Banking.getAll();
            for (RSItem rsItem : bankCache) {
                updateItem(rsItem.getDefinition().getName(), rsItem.getDefinition().getID(), rsItem.getStack());
            }
            RSItem[] inventCache = Inventory.getAll();
            for (RSItem item : inventCache) {
                int qty;
                if (item.getDefinition().isStackable()) {
                    qty = item.getStack();
                } else {
                   // qty = InventoryEvent.getItemCount(item.getDefinition().getName());
                }
              //  updateItem(item.getDefinition().getName(), item.getDefinition().getID(), qty);
            }
            RSItem[] equipment = Equipment.getItems();
            for (RSItem rsItem : equipment) {
                updateItem(rsItem.getDefinition().getName(), rsItem.getDefinition().getID(), rsItem.getStack());
            }
        }
    }
}

