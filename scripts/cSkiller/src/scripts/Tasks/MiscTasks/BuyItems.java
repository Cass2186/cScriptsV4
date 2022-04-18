package scripts.Tasks.MiscTasks;

import obf.G;
import org.tribot.api.General;
import org.tribot.api2007.Inventory;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.tasks.BankTaskError;
import org.tribot.script.sdk.types.GrandExchangeOffer;
import org.tribot.script.sdk.types.definitions.ItemDefinition;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.BankManager;
import scripts.GEManager.Exchange;
import scripts.GEManager.GEItem;
import scripts.ItemID;
import scripts.QuestSteps.BuyItemsStep;
import scripts.Requirements.ItemReq;
import scripts.Requirements.ItemRequirement;
import scripts.rsitem_services.GrandExchange;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class BuyItems implements Task {

    int[] FIVE_INCREMENTS = {5, 10, 15, 20, 25, 30, 35, 40, 45, 50};

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
                } else if (GrandExchange.getPrice(i.getId()) > 8000) {
                    listToBuy.add(new GEItem(i.getId(), i.getAmount(), 15));
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

    private void handleBankTaskError(Optional<BankTaskError> err) {
        err.ifPresent(e -> Log.error(e.toString()));

    }

    /**
     *
     */
    public static void getMissingAndBuyAllQuestItems(List<ItemReq> itemReqList, ArrayList<GEItem> questBuyList) {
        if (handleMissingItems(itemReqList).size() > 0) {
            Log.info("We're missing item(s), going to buy all required quest items as a fail safe");
            BuyItemsStep buy = new BuyItemsStep(questBuyList);
            buy.buyItems();
        }
    }

    /**
     * @param itemReqList required inventory item list
     * @return a list of missing inventory items from the list passed
     */
    private static List<GEItem> handleMissingItems(List<ItemReq> itemReqList) {
        List<GEItem> exchangeList = new ArrayList<>();
        for (ItemReq itemReq : itemReqList) {
            if (!itemReq.check()) {
                Optional<ItemDefinition> def = ItemDefinition.get(itemReq.getId());

                if (def.map(ItemDefinition::isGrandExchangeTradeable).orElse(false)) {
                    Log.error(String.format("Missing GE Tradeable inventory item: %s . Min = %s",
                            def.get().getName(), itemReq.getAmount()));
                    //TODO handle this
                    int amount = itemReq.getAmount() <= 0 ? 1 : itemReq.getAmount();
                    exchangeList.add(new GEItem(itemReq.getId(), amount, 25));
                } else if (def.isPresent() && !def.get().isGrandExchangeTradeable()) {
                    Log.error(String.format("Missing GE NOT-Tradeable inventory item: %s . Min = %s",
                            def.get().getName(), itemReq.getAmount()));
                    //TODO handle this
                    int amount = itemReq.getAmount() <= 0 ? 1 : itemReq.getAmount();
                    exchangeList.add(new GEItem(itemReq.getId(), amount, 25));
                }
            }
        }
        return exchangeList;
    }

  /*  /**
     * @param itemReqList required inventory item list
     * @return a list of missing inventory items from the list passed

    private static List<GEItem> handleMissingItems(List<ItemRequirement> itemReqList) {
        List<GEItem> exchangeList = new ArrayList<>();
        for (ItemRequirement itemReq : itemReqList) {
            if (!itemReq.check()) {
                Optional<ItemDefinition> def = ItemDefinition.get(itemReq.getId());

                if (def.map(ItemDefinition::isGrandExchangeTradeable).orElse(false)) {
                    Log.error(String.format("Missing GE Tradeable inventory item: %s . Min = %s",
                            def.get().getName(), itemReq.getAmount()));
                    //TODO handle this
                    int amount = itemReq.getAmount() <= 0 ? 1 : itemReq.getAmount();
                    exchangeList.add(new GEItem(itemReq.getId(), amount, 25));
                } else if (def.isPresent() && !def.get().isGrandExchangeTradeable()) {
                    Log.error(String.format("Missing GE NOT-Tradeable inventory item: %s . Min = %s",
                            def.get().getName(), itemReq.getAmount()));
                    //TODO handle this
                    int amount = itemReq.getAmount() <= 0 ? 1 : itemReq.getAmount();
                    exchangeList.add(new GEItem(itemReq.getId(), amount, 25));
                }
            }
        }
        return exchangeList;
    }*/

    public void buyItems() {
        BuyItemsStep buy = new BuyItemsStep(this.itemList);
        BankManager.open(true);
        BankManager.depositAll(true);
        BankManager.setUnNoted();
        if (!org.tribot.script.sdk.GrandExchange.isNearby() &&
                Inventory.find(ItemID.RING_OF_WEALTH).length == 0) {
            Log.info("[BuyItemsStep]: Need RoW, not at GE");
            BankManager.withdrawArray(ItemID.RING_OF_WEALTH_REVERSED, 1);
        }

        buy.buyItems();
        for (int i = 0; i < 3; i++) {
            Exchange.collectItems();
            if (Waiting.waitUntil(1500, 150, ()->
                    !Query.grandExchangeOffers().statusNotEquals(GrandExchangeOffer.Status.EMPTY).isAny())) {
                Log.info("[BuyItemStep]: Collected successfully");
                break;
            }
            if (i == 2){
                Log.error("[BuyItemStep]: Failed to collect");
            }
            Waiting.waitUniform(400,600);
        }
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
        Exchange.collectItems();
        Log.warn("Collection passed");
        BankManager.open(true);
        BankManager.depositAll(true);
    }

    @Override
    public String taskName() {
        return "Buying Items";
    }
}
