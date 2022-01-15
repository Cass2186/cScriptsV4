package scripts.QuestSteps;

import org.tribot.api.General;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Equipment;
import org.tribot.api2007.GrandExchange;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSItemDefinition;
import scripts.BankManager;
import scripts.GEManager.Exchange;
import scripts.GEManager.GEItem;
import scripts.ItemId;
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
        Exchange.collectItems();
        GrandExchange.close(true);
    }

    private void getItemCache() {
        if (this.bankCache == null) {
            General.println("[Debug]: Caching bank");
            BankManager.open(true);
            BankManager.depositAll(true);
            BankManager.withdraw(0, true, ItemId.COINS);
            BankManager.setUnNoted();
            BankManager.withdrawArray(ItemId.RING_OF_WEALTH, 1);
            this.bankCache = Banking.getAllList();
        }
        if (this.gearCache == null) {
            General.println("[Debug]: Caching gear");
            this.gearCache = Arrays.asList(Equipment.getItems());
        }
    }


    private ArrayList<GEItem> updatePurchaseQuantities(ArrayList<GEItem> itemList) {
        ArrayList<GEItem> updatedList = new ArrayList<>();
        for (GEItem i : itemList) {
            int quantity = determinePurchaseAmount(i);
            if (quantity <= 0){
                continue;
            }
            GEItem item = new GEItem(i.getItemId(), quantity, i.getPercentIncrease());
            updatedList.add(item);
            if (updatedList.contains(i)) {
                General.println("[BuyItemsSteps]: updatePurchaseQuanties determines we need " + quantity + " of item " + i.getItemId(), Color.CYAN);
            }

        }
        ArrayList<GEItem> updated =  updatedList.stream().distinct().collect(Collectors.toCollection(ArrayList::new));
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
        Optional<RSItemDefinition> itemDef = Optional.ofNullable(RSItemDefinition.get(item.getItemId()));

        for (int i = 0; i < myItems.size(); i++) { //itterates through cached bank/gear items
            String itemName = RSItemDefinition.get(item.getItemId()).getName();
            if (item.getItemId() == myItems.get(i).getID()) {
                stack = myItems.get(i).getStack();
                if (stack > 0) {
                    General.println("[GEItem]: We already have " + stack + " of " + itemName);
                    numOfItem = item.getItemQuantity() - stack;
                    return numOfItem;

                } else {
                    General.println("[GEItem]: We already have " + stack + " of " + itemName);
                    General.println("[GEItem]: itemQuantity " + item.getItemQuantity());
                    return item.getItemQuantity();
                }
            }
        }
        String itemName = "";
        if (itemDef.isPresent()) {
            itemName = itemDef.get().getName();
        }

        numOfItem = item.getItemQuantity() - stack;


        General.println("[Debug]: Need to buy " + numOfItem + " of " + itemName);
        return numOfItem;
    }

}
