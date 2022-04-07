package scripts.Tasks.MiscTasks;

import org.tribot.api.General;
import org.tribot.api2007.Inventory;
import org.tribot.script.sdk.Log;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.BankManager;
import scripts.GEManager.Exchange;
import scripts.GEManager.GEItem;
import scripts.ItemID;
import scripts.QuestSteps.BuyItemsStep;
import scripts.Requirements.ItemReq;
import scripts.rsitem_services.GrandExchange;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BuyItems implements Task {

    public BuyItems(ArrayList<GEItem> list) {
        this.itemList = list;
    }

    public BuyItems() {

    }

    public static ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    //   new GEItem(ItemID.STEEL_NAILS, 60, 50),
            )
    );

    public ArrayList<GEItem> itemList = new ArrayList<GEItem>(
            Arrays.asList(
                    //   new GEItem(ItemID.STEEL_NAILS, 60, 50),
            )
    );

    // needs to be edited to use different amounts than the bank withdraw amount
    public static ArrayList<GEItem> populateBuyList(List<ItemReq> list) {
        ArrayList<GEItem> listToBuy = new ArrayList<GEItem>(Arrays.asList());

        if (list != null) {
            for (ItemReq i : list) {
                if (i.getAmount() == 0) {
                    General.println("[BuyItems]: Continuing due to amout == 0");
                    continue;
                }
                General.println("[BuyItems]: Adding buy item x" + i.getAmount(), Color.green);
                if (GrandExchange.getPrice(i.getId()) < 1000 && i.getAmount() < 10) {
                    listToBuy.add(new GEItem(i.getId(), i.getAmount(), 350));
                } else
                    listToBuy.add(new GEItem(i.getId(), i.getAmount(), 35));

                General.println("[BuyItems]: Items to buy size: " + listToBuy.size());
            }

            if (listToBuy.size() > 0) {
                General.println("[BuyItems]: Adding RoW(5) to buy list ");
                listToBuy.add(new GEItem(ItemID.RING_OF_WEALTH[0], 1, 25));

            }

        }
        if (listToBuy.size() == 0)
            listToBuy.clear();

        return listToBuy;
    }

    public void buyItems() {
        BuyItemsStep buy = new BuyItemsStep(this.itemList);
        BankManager.open(true);
        BankManager.depositAll(true);
        BankManager.setUnNoted();
        if (!org.tribot.script.sdk.GrandExchange.isNearby() &&
                Inventory.find(ItemID.RING_OF_WEALTH).length ==0) {
            Log.info("[BuyItemsStep]: Need RoW, not at GE");
            BankManager.withdrawArray(ItemID.RING_OF_WEALTH_REVERSED, 1);
        }
       // if (!org.tribot.script.sdk.GrandExchange.isNearby())
        //    BankManager.withdrawArray(ItemID.RING_OF_WEALTH, 1);
       // BankManager.withdrawArray(ItemID.AMULET_OF_GLORY, 1);
        buy.buyItems();
        Exchange.clickCollect();
        itemList.clear();
        BankManager.open(true);
        BankManager.depositAll(true);
    }

    @Override
    public String toString() {
        return "Buying items";
    }

    @Override
    public Priority priority() {
        return Priority.HIGHEST;
    }

    @Override
    public boolean validate() {
        return itemsToBuy.size() > 0;
    }

    @Override
    public void execute() {
        General.println("[BuyItems]: Buying items", Color.RED);
        BuyItemsStep buy = new BuyItemsStep(itemsToBuy);

        buy.buyItems();
        itemsToBuy.clear();
        Exchange.clickCollect();
        BankManager.open(true);
        BankManager.depositAll(true);
    }

    @Override
    public String taskName() {
        return "Buying Items";
    }
}
