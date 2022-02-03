package scripts.Items;

import org.tribot.api2007.Banking;
import org.tribot.api2007.Equipment;
import org.tribot.api2007.Inventory;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Waiting;
import scripts.BankManager;
import scripts.ItemID;
import scripts.Requirements.ItemRequirement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ItemList {

    public static boolean has(List<ItemRequirement> list) {
        return list.stream().allMatch(item -> {
            if (item.getAmount() == -1) {
                return   item.getAmount() > 0;//item.getTotalCount() > 0;
            }
            return item.getAmount() <=  item.getAmount();//item.getTotalCount();
        });
    }

    public static int getAmountsOfListOwned(List<ItemRequirement> list) {
        return list.stream()
                .mapToInt(item -> item.getAmount())//item.getTotalCount() / item.getAmount())
                .min().orElse(0);
    }

    public static List<String> getMissingItems(List<ItemRequirement> list) {
        return list.stream()
                .filter(item -> {
                    if (item.getAmount() == -1) {
                        return item.getAmount() == 0;
                    }
                    return item.getAmount() > item.getAmount(); //item.getTotalCount();
                })
                .map(ItemRequirement::getItemName)
                .collect(Collectors.toList());
    }

   // public static boolean hasBought(List<ItemRequirement> list) {
//       return list.stream().allMatch(item -> item.getBuyQuantity() <= item.getTotalCount());
  //  }

    public static boolean hasBoughtAndSold(List<ItemRequirement> list) {
        return list.stream().allMatch(item -> {
            if (item.getId() == ItemID.COINS_995) return true;
            return item.getBuyQuantity() == item.getAmount();//item.getTotalCount();
        });
    }

    public static boolean hasAndEquipped(List<ItemRequirement> list) {
        return list.stream().allMatch(item -> item.check(true));
    }

    public static boolean hasInInventory(List<ItemRequirement> list) {
        return list.stream().allMatch(item -> item.check(false));
    }

    public static boolean hasExactInInventory(List<ItemRequirement> list) {
        List<Integer> allowedIds = getIdsList(list);
        return org.tribot.script.sdk.Inventory.getAll().stream().allMatch(item -> allowedIds.contains(item.getId())) && hasInInventory(list);
    }

    public static boolean hasAllInInventory(List<ItemRequirement> list) {
        return list.stream().allMatch(item -> BankManager.getCount(item.getId()) == 0);
    }

    public static boolean noneInInventoryExcept(List<ItemRequirement> list) {
        List<Integer> allowedIds = getIdsList(list);
        return Inventory.getAllList().stream().allMatch(item -> allowedIds.contains(item.getID()));
    }

    public static List<ItemRequirement> getItemsToEquip(List<ItemRequirement> list) {
        return list.stream()
                .filter(item -> item.isShouldEquip())
                .filter(item -> Equipment.getCount(item.getId()) < item.getAmount() ||
                        (Equipment.getCount(item.getId()) == 0 && item.getAmount() == -1))
                .collect(Collectors.toList());
    }

    public static boolean equipList(List<ItemRequirement> items) {
        for (ItemRequirement item : items) {
          //  item.equipNow(false);
            Waiting.waitNormal(125,30);
        }
        return Waiting.waitUntil(750, () -> allEquipped(items));
    }

    public static boolean allEquipped(List<ItemRequirement> items) {
        return items.stream().allMatch(item -> Equipment.isEquipped(item.getId()));
    }

    public static int[] getIds(List<ItemRequirement> list) {
        return list.stream()
                .map(ItemRequirement::getId)
                .flatMapToInt(IntStream::of)
                .toArray();
    }

    public static List<ItemRequirement> getListFromIds(List<Integer> ids) {
        return ids.stream()
                .map(ItemRequirement::new)
                .collect(Collectors.toList());
    }

    public static List<Integer> getIdsList(List<ItemRequirement> list) {
        return list.stream()
                .map(ItemRequirement::getAllIds)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    public static List<ItemRequirement> multiply(List<ItemRequirement> list, int amount, boolean buyOnly) {
        List<ItemRequirement> cloneList = copy(list);
        cloneList.forEach(item -> {
            if (!buyOnly) item.setAmount(item.getAmount() != -1 ? item.getAmount() * amount : -1);
      //      item.setBuyQuantity(item.getBuyQuantity() != -1 ? item.getBuyQuantity() * amount : -1);
        });
        return cloneList;
    }

    public static List<ItemRequirement> combine(List<ItemRequirement>... lists) {
        return new ArrayList<ItemRequirement>(Arrays.stream(lists)
                .flatMap(List::stream)
                .filter(itemRequirement -> itemRequirement != null)
                .collect(Collectors.toMap(i -> i.getId(), Function.identity(), (x, y) -> {
                 //   x.addBuyQuantity(y.getBuyQuantity());
                    return x;
                }))
                .values());
    }

    public static List<ItemRequirement> removeDuplicates(List<ItemRequirement>... lists) {
        return new ArrayList<ItemRequirement>(Arrays.stream(lists)
                .flatMap(List::stream)
                .collect(Collectors.toMap(f -> f.getId(), Function.identity(), (x, y) -> x))
                .values());
    }

    private static List<ItemRequirement> copy(List<ItemRequirement> original) {
        return original.stream().map(req -> req.copy()).collect(Collectors.toList());
    }

    public static void setEquip(List<ItemRequirement> itemList) {
        itemList.forEach(item -> item.setShouldEquip(true));
    }

    public static void setNoted(List<ItemRequirement> itemList) {
        itemList.forEach(item -> item.setAcceptNoted(true));
    }


    public static boolean containsId(int id, List<ItemRequirement> items) {
        return items.stream().anyMatch(item -> item.getAllIds().contains(id));
    }

    public static List<ItemRequirement> setQuantity(List<ItemRequirement> list, int quantity) {
        List<ItemRequirement> cloneList = copy(list);
        cloneList.forEach(item -> {
       //     item.setQuantity(quantity);
        //    item.setBuyQuantity(quantity);
        });
        return cloneList;
    }

    public static int getBuyQuantity(List<ItemRequirement> items, int id) {
        return items.stream()
                .filter(item -> item.getAllIds().contains(id))
                .findFirst()
                .map(ItemRequirement::getBuyQuantity)
                .orElse(0);
    }

    public static List<ItemRequirement> splitItems(List<ItemRequirement> list) {
        return getIdsList(list).stream()
                .map(id -> new ItemRequirement(id, 0))
                .collect(Collectors.toList());
    }

    public static void print(List<ItemRequirement> list) {
        Log.log("Items in list");
        list.forEach(item -> Log.log("Amount: " + item.getAmount() + " | Buy: " + item.getBuyQuantity() + " "
                + item.getItemName()));
    }

    public static List<ItemRequirement> sortyByMissingQuantity(List<ItemRequirement> list) {
        return list.stream()
             //   .sorted(Comparator.comparingInt(item -> item.getBuyQuantity() - item.getTotalCount()))
                .collect(Collectors.toList());
    }

    public static boolean isRestockingDisabled(List<ItemRequirement> list) {
        return list.stream()
             //   .filter(item -> item.getTotalCount() < item.getAmount())
                .anyMatch(item -> item.getBuyQuantity() < item.getAmount());
    }

}
