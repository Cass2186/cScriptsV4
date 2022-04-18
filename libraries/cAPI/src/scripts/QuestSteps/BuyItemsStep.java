package scripts.QuestSteps;

import org.tribot.api.General;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Equipment;
import org.tribot.api2007.GrandExchange;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSItemDefinition;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.GrandExchangeOffer;
import scripts.BankManager;
import scripts.GEManager.Exchange;
import scripts.GEManager.GEItem;
import scripts.ItemID;
import scripts.Utils;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BuyItemsStep {

    ArrayList<GEItem> invList;
    private List<RSItem> bankCache;
    private List<RSItem> gearCache;

    public BuyItemsStep(ArrayList<GEItem> invList) {
        this.invList = invList;
    }

    public void buyItems() {
        General.println("[BuyItemStep]: Buying Items");
        getItemCache();
        ArrayList<GEItem> list = updatePurchaseQuantities(invList);
        // if (list.size() > 0)
        for (GEItem i : list) {
            Exchange.buyItem(i);
            // buy each items
        }
        Utils.shortSleep();
        for (int i = 0; i < 6; i++) {
            Exchange.collectItems();
            if (Waiting.waitUntil(1500, 150, ()->
                    !Query.grandExchangeOffers()
                            .statusNotEquals(GrandExchangeOffer.Status.EMPTY).isAny())) {
                Log.info("[BuyItemStep]: Collected successfully");
                break;
            }
            if (i == 5)
                Log.error("[BuyItemStep]: Failed to collect");
            Waiting.waitUniform(900,1200);
        }
        Exchange.collectItems();
        GrandExchange.close(true);
    }

    private void getItemCache() {
        if (this.bankCache == null) {
            General.println("[BuyItemsStep]: Caching bank");
            BankManager.open(true);
            BankManager.depositAll(true);
            BankManager.withdraw(0, true, ItemID.COINS);
            BankManager.setUnNoted();
            if (!org.tribot.script.sdk.GrandExchange.isNearby() && Inventory.find(ItemID.RING_OF_WEALTH).length ==0) {
                Log.log("[BuyItemsStep]: need RoW, not at GE");
                BankManager.withdrawArray(ItemID.RING_OF_WEALTH, 1);
            }
            this.bankCache = Banking.getAllList();
        }
        if (this.gearCache == null) {
            General.println("[BuyItemsStep]: Caching gear");
            this.gearCache = Arrays.asList(Equipment.getItems());
            this.bankCache.addAll(this.gearCache);
        }
    }


    private ArrayList<GEItem> updatePurchaseQuantities(ArrayList<GEItem> itemList) {
        ArrayList<GEItem> updatedList = new ArrayList<>();
        for (GEItem i : itemList) {
            int quantity = determinePurchaseAmount(i);
            if (quantity <= 0) {
                continue;
            }
            GEItem item = new GEItem(i.getItemID(), quantity, i.getPercentIncrease());
            updatedList.add(item);
            if (updatedList.contains(i)) {
                General.println("[BuyItemsSteps]: updatePurchaseQuanties determines we need " + quantity + " of item " + i.getItemID(), Color.CYAN);
            }

        }
        ArrayList<GEItem> updated = updatedList.stream().distinct().collect(Collectors.toCollection(ArrayList::new));
        General.println("[BuyItemsSteps]: updatedList Size = " + updatedList.size());
        General.println("[BuyItemsSteps]: updated Size = " + updated.size());
        return updatedList.stream().distinct().collect(Collectors.toCollection(ArrayList::new));

    }

    //determines purchase amount for a single item
    private int determinePurchaseAmount(GEItem item) {
        List<RSItem> myItems = this.bankCache;
        myItems.addAll(this.gearCache);
        int numOfItem;
        int stack = 0;
        Optional<RSItemDefinition> itemDef = Optional.ofNullable(RSItemDefinition.get(item.getItemID()));

        for (int i = 0; i < myItems.size(); i++) { //itterates through cached bank/gear items
            String itemName = RSItemDefinition.get(item.getItemID()).getName();
            if (item.getItemID() == myItems.get(i).getID()) {
                stack = myItems.get(i).getStack();
                if (stack > 0) {
                    General.println("[BuyItemsStep]: We already have " + stack + " of " + itemName);
                    numOfItem = item.getItemQuantity() - stack;
                    return numOfItem;

                } else {
                    General.println("[BuyItemsStep]: We already have " + stack + " of " + itemName);
                    General.println("[BuyItemsStep]: itemQuantity " + item.getItemQuantity());
                    return item.getItemQuantity();
                }
            }
        }
        String itemName = "";
        if (itemDef.isPresent()) {
            itemName = itemDef.get().getName();
        }

        numOfItem = item.getItemQuantity() - stack;


        General.println("[BuyItemsStep]: Need to buy " + numOfItem + " of " + itemName);
        return numOfItem;
    }

}
