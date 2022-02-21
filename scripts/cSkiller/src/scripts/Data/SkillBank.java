package scripts.Data;

import org.tribot.api.General;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Equipment;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSItem;
import org.tribot.script.sdk.Log;
import scripts.BankManager;
import scripts.Data.Enums.CookItems;
import scripts.Data.Enums.Crafting.CraftItems;
import scripts.Data.Enums.FiremakingItems;
import scripts.Data.Enums.HerbloreItems;
import scripts.ItemID;
import scripts.Requirements.ItemReq;
import scripts.Timer;
import scripts.Utils;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class SkillBank {

    public static List<ItemReq> withdraw(List<ItemReq> list) {
        Log.log("[SkillBank]: Withdrawing Items");
        if (list.size() == 0) {
            Log.log("[SkillBank]: List size is zero");
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

            if (i.isAcceptEquipped() && Equipment.isEquipped(i.getId()))
                continue;

            RSItem[] it = Inventory.find(i.getId());
            int minAmount = i.getAmount() > 0 ? i.getAmount() : 1;
            if (it.length > 0 && it[0].getStack() >= minAmount) {
                Log.log("[SkillBank]: We already have enough of item ID: " + i.getId());
                continue;
            }

            if (!Banking.isBankScreenOpen())
                BankManager.open(true);

            RSItem[] item = Banking.find(i.getId());
            Log.log("[SkillBank]: itemInBank[].length: " + item.length + " of item ID: " + i.getId());

            if (specialItems != null) // add tinderbox or blowingglass pipe if needed
                itemList.add(specialItems);


            if (item.length < 1 && Inventory.find(i.getId()).length < 1 && !Equipment.isEquipped(i.getId())) { // no item in bank or inv
                General.println("[SkillBank]: Missing the item in bank and inv with ID: " + i.getId(), Color.CYAN);
                List<ItemReq> listToAdd = getSkillItemList();
                if (listToAdd == null)
                    listToAdd = new ArrayList<>();

                Log.log("[SkillBank]: ListToAdd.size() " + listToAdd.size());
                if (listToAdd.size() > 0) {
                    itemList.addAll(listToAdd); // adds all skilling items required per the enums method
                    Log.log("[SkillBank]: Adding listToAdd to itemList");
                }
                if (!itemList.contains(i)) { // adds item if it's not in the list already
                    itemList.add(i);
                    Log.log("[SkillBank]: Missing itemList size: " + itemList.size());

                }
                continue;
            }

            if (item.length > 0 && item[0].getStack() < i.getAmount()) {
                if (!itemList.contains(i)) // adds item if it's not in the list already
                    itemList.add(i);
                Log.log("[SkillBank]: Missing ENOUGH of Item -> itemList size: " + itemList.size());
                continue;
            }
            Log.debug("Is Item Noted? " + i.isItemNoted());
            if (i.isItemNoted() && !BankManager.areNotesOn()) {
                BankManager.toggleNoted(true);

            }
            if (!i.isItemNoted() && BankManager.areNotesOn())
                BankManager.toggleNoted(false);

            if (i.isAcceptEquipped() && Equipment.isEquipped(i.getId()))
                continue;
            // we will try up to 5 times to withdraw the item (remember we are still in the for loop above)
            for (int b = 0; b < 4; b++) {
                General.sleep(40, 60);
                Log.log("[SkillBank]: Attempting to withdraw item: " + i.getId() +
                        " x " + i.getAmount());
                int invSize = Inventory.getAll().length;
                if (i.isItemNoted() && Banking.withdraw(i.getAmount(), i.getId())) {
                    if (Timer.waitCondition(() -> Inventory.getAll().length > invSize ||
                            Inventory.find(Utils.getNotedItemID(i.getId())).length > 0, 3600, 6400))
                        break;
                }
                if (BankManager.withdraw(i.getAmount(), true, i.getId())) {//if (Banking.withdraw(i.getAmount(), i.getId())) {
                    Log.log("[SkillBank]: Successfully withdraw item: " + i.getId());
                    if (Timer.waitCondition(() -> Inventory.getAll().length > invSize ||
                            Inventory.find(i.getId()).length > 0, 3600, 6400))
                        break;
                } else if (Banking.withdraw(i.getAmount(), i.getId())) {
                    if (Timer.waitCondition(() -> Inventory.getAll().length > invSize ||
                            Inventory.find(Utils.getNotedItemID(i.getId())).length > 0, 3600, 6400))
                        break;

                } else {
                    Log.log("[SkillBank]: Failed to withdraw item " + i.getId() + " iter #" + b);
                    General.sleep(20, 40);
                }
                if (b == 3) { // failed to get 4x, adding to list

                    Optional<ItemReq> opt = itemList.stream()
                            .filter(listItem -> listItem.getId() == i.getId()).findFirst();
                    if (opt.isPresent()) {
                        //already have this item id in the list
                        ItemReq r = new ItemReq(i.getId(), (i.getAmount() + opt.get().getAmount()));
                        itemList.add(r);
                    } else
                        itemList.add(i);

                    Log.log("[SkillBank]: Failed with withdraw ItemID: " + i.getId() + " -> Missing itemList size: " +
                            itemList.size());
                }

            }
        }
        Log.log("[SkillBank]: returning itemList of size: " + itemList.size());
        return itemList;
    }

    private static ItemReq checkSkillSpecificItems(RSItem[] bankCache) {
        if (Vars.get().currentTask != null && bankCache != null) {
            if (Vars.get().currentTask.equals(SkillTasks.CRAFTING) && Arrays.stream(bankCache)
                    .noneMatch(a -> a.getID() == ItemID.GLASSBLOWING_PIPE) &&
                    Inventory.find(ItemID.GLASSBLOWING_PIPE).length == 0) {
                Log.log("[SkillBank]: Adding Glass blowing pipe to purchase list");
                return new ItemReq(ItemID.GLASSBLOWING_PIPE, 1);

            } else if (Vars.get().currentTask.equals(SkillTasks.FIREMAKING) && Arrays.stream(bankCache)
                    .noneMatch(a -> a.getID() == ItemID.TINDERBOX) &&
                    Inventory.find(ItemID.TINDERBOX).length == 0) {
                Log.log("[SkillBank]: Adding Tinderbox to purchase list");
                return new ItemReq(ItemID.TINDERBOX, 1);
            }


        }
        return null;
    }

    public static List<ItemReq> getSkillItemList() {
        if (Vars.get().currentTask != null) {
            General.println("[SkillBank]: Getting skill item list; Current task is " + Vars.get().currentTask.toString());
            if (Vars.get().currentTask.equals(SkillTasks.COOKING)) {
                return CookItems.getRequiredRawFood();
            } else if (Vars.get().currentTask.equals(SkillTasks.CRAFTING)) {
                return CraftItems.getRequiredItemList();

            } else if (Vars.get().currentTask.equals(SkillTasks.FIREMAKING)) {
                return FiremakingItems.getRequiredLogList();

            } else if (Vars.get().currentTask.equals(SkillTasks.HERBLORE)) {
                General.println("[SkillBank]: getSkillItemList is called");
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
