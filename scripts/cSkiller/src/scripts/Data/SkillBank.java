package scripts.Data;

import lombok.val;

import org.tribot.api2007.Banking;
import org.tribot.api2007.types.RSItem;
import org.tribot.script.sdk.*;
import org.tribot.script.sdk.cache.BankCache;
import org.tribot.script.sdk.interfaces.Item;
import org.tribot.script.sdk.query.Query;
import scripts.BankManager;
import scripts.Data.Enums.CookItems;
import scripts.Data.Enums.Crafting.CraftItems;
import scripts.Data.Enums.FiremakingItems;
import scripts.Data.Enums.HerbloreItems;
import scripts.ItemID;
import scripts.Requirements.ItemReq;
import scripts.Timer;
import scripts.Utils;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class SkillBank {

    public static List<ItemReq> withdraw(List<ItemReq> list) {
        Log.info("[SkillBank]: Withdrawing Items");
        if (list.size() == 0) {
            Log.info("[SkillBank]: List size is zero");
            return null;
        }
        List<ItemReq> itemList = new ArrayList<>();

        RSItem[] allItems = Banking.getAll();
        ItemReq specialItems = checkSkillSpecificItems(allItems);
        if (specialItems != null) // add tinderbox or blowingglass pipe if needed
            itemList.add(specialItems);


        for (ItemReq i : list) {

            if (i.getId() == 0)
                continue;

            if (i.isAcceptEquipped() && Equipment.contains(i.getId()))
                continue;

            int itAmount = Inventory.getCount(i.getId());
            int minAmount = i.getAmount() > 0 ? i.getAmount() : 1;

            if (itAmount >= minAmount) {
                Log.info("[SkillBank]: We already have enough of item ID: " + i.getId());
                continue;
            }

            if (!Bank.isOpen())
                BankManager.open(true);

            if (!BankCache.isInitialized())
                BankCache.update();

            Optional<Item> item = Query.bank().idEquals(i.getId()).findFirst();
            item.ifPresent(itm ->
                    Log.info("[SkillBank]: item.size() " + itm.getStack() + " of item ID: " + i.getId()));

            if (specialItems != null) // add tinderbox or blowingglass pipe if needed
                itemList.add(specialItems);

            var skip = false;
            if (item.isEmpty() && !Inventory.contains(i.getId()) && !Equipment.contains(i.getId())) { // no item in bank or inv
                Log.warn("[SkillBank]: Missing the item in bank and inv with ID: " + i.getId());
                if (i.getAlternateItemIDs() != null && i.getAlternateItemIDs().size() > 0) {

                    for (Integer alt : i.getAlternateItemIDs()) {

                        var amount = i.getAmount() <= 0 ? 1 : i.getAmount();
                        if (Inventory.getCount(alt) >= amount) {
                            Log.info("[ItemRequirement]: Accepted an alternative id of " + alt +
                                    " for ItemID of:  " + i.getId());
                            break;

                        } else if (BankCache.getStack(alt) >= amount) {
                            Log.info("[ItemReq]: Accepted an alternative id of " + alt +
                                    " for ItemID of:  " + i.getId());
                            Query.bank().idEquals(alt).findFirst();
                            skip = true;
                            break;
                        }
                    }
                }
                if (!skip) {
                    List<ItemReq> listToAdd = getSkillItemList();
                    if (listToAdd == null)
                        listToAdd = new ArrayList<>();

                    Log.info("[SkillBank]: ListToAdd.size() " + listToAdd.size());
                    if (listToAdd.size() > 0) {
                        itemList.addAll(listToAdd); // adds all skilling items required per the enums method
                        Log.info("[SkillBank]: Adding listToAdd to itemList");
                    }
                    if (!itemList.contains(i)) { // adds item if it's not in the list already
                        itemList.add(i);
                        Log.info("[SkillBank]: Missing itemList size: " + itemList.size());
                        continue;
                    }
                }

            }

            if (item.map(itm -> itm.getStack() < i.getAmount()).orElse(false)) {
                if (!itemList.contains(i)) // adds item if it's not in the list already
                    itemList.add(i);
                Log.warn("[SkillBank]: Missing ENOUGH of Item -> itemList size: " + itemList.size());
                continue;
            }
            Log.info("Is Item Noted? " + i.isItemNoted());
            if (i.isItemNoted() && !BankManager.areNotesOn()) {
                BankManager.toggleNoted(true);
            }
            if (!i.isItemNoted() && BankManager.areNotesOn())
                BankManager.toggleNoted(false);

            if (i.isAcceptEquipped() && Equipment.contains(i.getId()))
                continue;
            // we will try up to 5 times to withdraw the item (remember we are still in the for loop above)

            if (!withdrawItem(i)) { // failed to get 4x, adding to list
                Optional<ItemReq> opt = itemList.stream()
                        .filter(listItem -> listItem.getId() == i.getId()).findFirst();
                if (opt.isPresent()) {
                    //already have this item id in the list
                    ItemReq r = new ItemReq(i.getId(), (i.getAmount() + opt.get().getAmount()));
                    itemList.add(r);
                } else
                    itemList.add(i);

                Log.warn("[SkillBank]: Failed with withdraw ItemID: " + i.getId() + " -> Missing itemList size: " +
                        itemList.size());
            }

        }
        Log.info("[SkillBank]: returning itemList of size: " + itemList.size());
        return itemList;
    }

    private static boolean withdrawItem(ItemReq i) {
        var itemId = i.getId();

        for (int b = 0; b < 4; b++) {
            Waiting.waitUniform(40, 60);
            Log.info("[SkillBank]: Attempting to withdraw item: " + i.getId() +
                    " x " + i.getAmount());
            var invSize = Inventory.getAll().size();
            val itemIdFinal = itemId;

            if (i.isItemNoted() && BankManager.withdraw(i.getAmount(), false, itemId)) {
                Log.info("[SkillBank]: Successfully withdraw item: " + i.getId());
                if (Timer.waitCondition(() -> Inventory.getAll().size() > invSize ||
                        Inventory.contains(Utils.getNotedItemID(itemIdFinal)), 3600, 5400)) {
                    Log.info("Withdrew noted item successfully");
                    return true;
                } else {
                    Log.error("[SkillBank]: Failed to Successfully withdraw noted item: " + i.getId());
                }
            }
            if (BankManager.withdraw(i.getAmount(), true, itemId)) {//if (Banking.withdraw(i.getAmount(), i.getId())) {
                Log.info("[SkillBank]: Successfully withdraw item: " + i.getId());
                if (Timer.waitCondition(() -> Inventory.getAll().size() > invSize ||
                        Inventory.contains(itemIdFinal) || (i.isItemNoted() &&
                        Inventory.contains(Utils.getNotedItemID(itemIdFinal))), 3600, 5400))
                    return true;
            } else if (BankManager.withdraw(i.getAmount(), false, itemId)) {
                if (Timer.waitCondition(() -> Inventory.getAll().size() > invSize ||
                        Inventory.contains(Utils.getNotedItemID(itemIdFinal)), 3600, 5400))
                    return true;

            } else {
                Log.log("[SkillBank]: Failed to withdraw item " + itemId + " iter #" + b);
                Waiting.waitUniform(20, 40);
                if (i.getAlternateItemIDs() != null && i.getAlternateItemIDs().size() > 0) {
                    for (Integer alt : i.getAlternateItemIDs()) {
                        var amount = i.getAmount() <= 0 ? 1 : i.getAmount();

                        if (Inventory.getCount(alt) >= amount) {
                            Log.info("[ItemRequirement]: Accepted an alternative id of " + alt +
                                    " for ItemID of:  " + i.getId());
                            break;
                        } else if (BankCache.getStack(alt) >= amount) {
                            Log.info("[ItemReq]: Accepted an alternative id of " + alt +
                                    " for ItemID of:  " + i.getId());
                            itemId = alt;
                            break;
                        }

                    }
                }
            }
            if (b == 3) { // failed to get 4x, adding to list
                return false;
            }
        }
        return false;
    }

    private static ItemReq checkSkillSpecificItems(RSItem[] bankCache) {
        if (Vars.get().currentTask != null && bankCache != null) {
            if (Vars.get().currentTask.equals(SkillTasks.CRAFTING) && Arrays.stream(bankCache)
                    .noneMatch(a -> a.getID() == ItemID.GLASSBLOWING_PIPE) &&
                    !Inventory.contains(ItemID.GLASSBLOWING_PIPE)) {
                Log.info("[SkillBank]: Adding Glass blowing pipe to purchase list");
                return new ItemReq(ItemID.GLASSBLOWING_PIPE, 1);

            } else if (Vars.get().currentTask.equals(SkillTasks.FIREMAKING) && Arrays.stream(bankCache)
                    .noneMatch(a -> a.getID() == ItemID.TINDERBOX) &&
                    !Inventory.contains(ItemID.TINDERBOX)) {
                Log.info("[SkillBank]: Adding Tinderbox to purchase list");
                return new ItemReq(ItemID.TINDERBOX, 1);
            }


        }
        return null;
    }

    public static List<ItemReq> getSkillItemList() {
        if (Vars.get().currentTask != null) {
            Log.info("[SkillBank]: Getting skill item list; Current task is " + Vars.get().currentTask.toString());
            if (Vars.get().currentTask.equals(SkillTasks.COOKING)) {
                return CookItems.getRequiredRawFood();
            } else if (Vars.get().currentTask.equals(SkillTasks.CRAFTING)) {
                return CraftItems.getRequiredItemList();

            } else if (Vars.get().currentTask.equals(SkillTasks.FIREMAKING)) {
                return FiremakingItems.getRequiredLogList();

            } else if (Vars.get().currentTask.equals(SkillTasks.HERBLORE)) {
                Log.info("[SkillBank]: getSkillItemList is called");
                return HerbloreItems.getRequiredItemList();

            } else if (Vars.get().currentTask.equals(SkillTasks.WOODCUTTING)) {
                return new ArrayList<ItemReq>(Arrays.asList(
                        new ItemReq(ItemID.AXE_IDS[0], 1, 1, true),
                        new ItemReq(ItemID.AXE_IDS[1], 1, 1, true),
                        new ItemReq(ItemID.AXE_IDS[2], 1, 1, true),
                        new ItemReq(ItemID.AXE_IDS[3], 1, 1, true),
                        new ItemReq(ItemID.AXE_IDS[4], 1, 1, true)
                ));
            }


        }
        return null;
    }

}
