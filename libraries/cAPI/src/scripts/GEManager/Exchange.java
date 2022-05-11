package scripts.GEManager;


import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api.input.Keyboard;
import org.tribot.api2007.*;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.*;
import org.tribot.script.sdk.*;
import org.tribot.script.sdk.GrandExchange;
import org.tribot.script.sdk.interfaces.Item;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.GameObject;
import org.tribot.script.sdk.types.GrandExchangeOffer;
import org.tribot.script.sdk.types.Widget;
import org.tribot.script.sdk.types.definitions.ItemDefinition;
import scripts.*;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class Exchange {

    public static int GE_PARENT = 465;
    public static int CONFIRM_OFFER_ID = 54;
    public static int SEARCH_ITEM_NAME_INTERFACE_ID = 42;
    public static int GE_BOOTH_ID = 10061;

    private static final int ROOT_WIDGET = 465;
    private static final int OFFER_WINDOW_ENTER_MENU_MASTER = 162;
    private static final int ITEM_SELECTION_MASTER = 162;


    public static String GE_CLERK_STRING = "Grand Exchange Clerk";

    public static RSInterface[] interfaceChildren;
    public static int componentItem;

    public static RSInterface BUY_1 = Interfaces.get(465, 7, 3);
    public static RSInterface ADD_5_PERCENT_BUTTON = Interfaces.get(465, 25, 13);


    public static ArrayList<Integer> price = new ArrayList<Integer>();


    public static RSArea GE_Area = new RSArea(new RSTile(3160, 3494, 0), new RSTile(3168, 3485, 0));


    /***********
     * METHODS *
     ***********/


    public static boolean goToSelectionWindow() {
        return GrandExchange.isOpen() &&
                org.tribot.api2007.GrandExchange.getWindowState() == org.tribot.api2007.GrandExchange.WINDOW_STATE.SELECTION_WINDOW ||
                org.tribot.api2007.GrandExchange.goToSelectionWindow(true);
    }

    public static RSGEOffer getEmptyOffer() {
        if (goToSelectionWindow()) {
            RSGEOffer[] offers = org.tribot.api2007.GrandExchange.getOffers();
            for (RSGEOffer ofr : offers) {
                if (ofr.getStatus() == RSGEOffer.STATUS.EMPTY)
                    return ofr;
            }
        }
        return null;
    }


    /**
     * this won't get coins or travel to GE or relist
     *
     * @param item object to buy
     * @return successfully placed or not
     */
    public static boolean placeBuyOffer(GEItem item) {
        return GrandExchange.open() && GrandExchange.placeOffer(getBuyConfigFromItem(item));
    }

    private static GrandExchange.CreateOfferConfig getBuyConfigFromItem(GEItem item) {
        Optional<ItemDefinition> itemDef = Query.itemDefinitions().idEquals(item.getItemID())
                .isGrandExchangeTradeable().stream().findFirst();
        if (itemDef.isPresent()) {
            Optional<Integer> priceOptional = ((Item) itemDef.get()).lookupPrice();
            int price = priceOptional
                    .map(p-> p *item.getPercentIncrease()).orElse(1.0).intValue();
            int clicks = Utils.roundToNearest(item.getPercentIncrease(), 5) / 5;
            if (clicks > Utils.random(5, 8)) {
                Log.warn("Price adjustment is > 5-8 clicks, using " + price);
                return GrandExchange.CreateOfferConfig.builder()
                        .type(GrandExchangeOffer.Type.BUY)
                        .itemId(item.getItemID())
                        .price(price)
                        .searchText(getItemName(item))
                        .quantity(item.getItemQuantity()).build();
            }
        }
        return GrandExchange.CreateOfferConfig.builder()
                .type(GrandExchangeOffer.Type.BUY)
                .itemId(item.getItemID())
                .priceAdjustment(Utils.roundToNearest(item.getPercentIncrease(), 5) / 5)
                .searchText(getItemName(item))
                .quantity(item.getItemQuantity()).build();
    }

    /**
     * this won't get the item or travel to GE or relist
     *
     * @param item object to buy
     * @return successfully placed or not
     */
    public static boolean placeSellOffer(GEItem item) {
        return GrandExchange.open() && GrandExchange.placeOffer(getSellConfigFromItem(item));
    }

    private static GrandExchange.CreateOfferConfig getSellConfigFromItem(GEItem item) {
        int id = isStackable(item) ? item.getItemID() : item.getItemID() + 1;
        Optional<ItemDefinition> itemDef = Query.itemDefinitions().idEquals(item.getItemID()).isGrandExchangeTradeable().stream().findFirst();
        int price = itemDef.map(i -> i.getBaseValue()).orElse(1000);
        Log.warn("Base price is " + price);
        double db = item.percentIncrease / 100;
        General.println("[Debug]: Percent to add " + item.percentIncrease);
        General.println("[Debug]: Percent of current price " + (item.percentIncrease / 100 + 1));
        double offerPrice = Math.round(price * (item.percentIncrease / 100 + 1));
        int realOfferPrice = (int) (5 * (Math.round(offerPrice / 5)) + 5);

        if (realOfferPrice > 2000)
            realOfferPrice = (int) (100 * (Math.round(offerPrice / 100)));
        return GrandExchange.CreateOfferConfig.builder()
                .type(GrandExchangeOffer.Type.SELL)
                .itemId(id)
                .priceAdjustment(-Utils.roundToNearest(item.getPercentIncrease(), 5) / 5)
                .quantity(item.getItemQuantity()).build();
    }

    private static boolean isStackable(GEItem item) {
        Optional<ItemDefinition> def = Query
                .itemDefinitions().idEquals(item.getItemID()).findFirst();
        return def.map(d -> !d.isStackable()).orElse(false);
    }


    public static boolean checkIfItemIsInHashMap(int item, HashMap<Integer, Integer> map) {
        for (Integer i : map.keySet()) {
            if (i == item)
                return true;
        }
        return false;
    }

    public static int getListPrice(RSGEOffer offer) {
        int index = offer.getIndex() + 7;
        int price = offer.getPrice();
        General.println("[Exchanger]: (getRelistPrice()) price of offer item is " + price);
        if (Interfaces.isInterfaceSubstantiated(465, index, 25)) {
            try {
                String offerPrice = Interfaces.get(465, index, 25).getText().replace(" coins", "");
                offerPrice = offerPrice.replace(",", "");
                price = Integer.parseInt(offerPrice);
                General.println("[Exchanger]: Price for the current offer is " + price + " coins");
                return price;
            } catch (Exception e) {
                General.println("[Exchanger]: Failed to get relist price");
                e.printStackTrace();
            }

        }
        return price;
    }


    public static boolean isGrandExchangeOpen() {
        return org.tribot.script.sdk.GrandExchange.isOpen();
    }

    /**
     * will move to GE, get coins, close bank and attempt to open GE
     *
     * @return if GE was opened successfully
     */
    public static boolean openGE() {
        if (!GE_Area.contains(Player.getPosition()))
            PathingUtil.walkToArea(GE_Area, false);

        if (Inventory.find(995).length < 1) {
            Log.info("Missing coins, banking.");
            BankManager.withdraw(0, true, ItemID.COINS_995);
        }

        if (Bank.isOpen())
            BankManager.close(true);

        Optional<GameObject> geBooth = Query.gameObjects()
                .actionContains("Exchange")
                .maxDistance(20).findBestInteractable();
        if (!org.tribot.script.sdk.GrandExchange.isOpen() &&
                geBooth.map(b -> b.interact("Exchange ")).orElse(false) &&
                Timer.waitCondition(() -> org.tribot.script.sdk.GrandExchange.open() ||
                        NPCInteraction.isConversationWindowUp(), 5000, 7000)) {

            if (NPCInteraction.isConversationWindowUp())
                NPCInteraction.handleConversation("I'd like to set up trade offers please.");
        }
        return Waiting.waitUntil(5000, 75, org.tribot.script.sdk.GrandExchange::isOpen);
    }


    /**
     * will open a buy offer window
     *
     * @return if successful or not
     */
    public static boolean startBuyOffer() {
        if (!isGrandExchangeOpen())
            openGE();
        //  checkIfWeNeedToCollect();
        // this allows it to loop if it has to collect first
        for (int i = 0; i < 2; i++) {
            RSGEOffer ofr = getEmptyOffer();
            if (ofr != null) {
                int index = ofr.getIndex();
                Log.info("[GEManager]: Creating buy offer at window: " + index);
                Optional<Widget> first = Query.widgets()
                        .inIndexPath(GE_PARENT, index + 7, 3).findFirst();

                if (first.map(Widget::click).orElse(false))
                    return Timer.waitCondition(() ->
                            Interfaces.isInterfaceSubstantiated(162, SEARCH_ITEM_NAME_INTERFACE_ID), 7000, 9000);

            } else {
                clickCollect();
            }
        }
        return false;
    }

    private static String determineShortName(String fullName) {
        int stringLength = fullName.length();
        try {
            if (stringLength > General.random(10, 12)) {
                int firstInt = General.random(7, 10);
                int secondInt = firstInt + 1;
                char firstChar = fullName.charAt(firstInt);
                String splitHere = firstChar + Character.toString(fullName.charAt(secondInt));
                return fullName.split(splitHere)[0];

            }
        } catch (Exception e) {
            return fullName;
        }
        return fullName;
    }

    private static String getItemName(GEItem item) {
        RSItemDefinition itemDef = RSItemDefinition.get(item.getItemID());
        String itemString = itemDef.getName();

        if (itemString != null && itemString.length() > 10) {
            itemString = determineShortName(itemString);
            General.println("[GEManager]: Truncated item string: " + itemString);
        }

        if (itemString != null && itemString.contains("("))
            itemString = itemString.split("\\(")[0];

        return itemString;
    }

    private static int ITEM_SELECTION_BOX_PARENT = 162;
    private static int SMALL_ITEM_SELECTION_BOX_COMP = 50;

    public static boolean selectBuyItem(GEItem item) {
        // should  check there's a valid item to buy before all this

        startBuyOffer();
        String itemString = getItemName(item);

        // type item String
        RSInterface typeBoxInterface = Interfaces.get(ITEM_SELECTION_BOX_PARENT, SMALL_ITEM_SELECTION_BOX_COMP);
        if (typeBoxInterface != null) {
            Keyboard.typeString(itemString.toLowerCase());
            // this allows it to load items, otherwise it'll skip it
            Waiting.waitUniform(1000, 1800);
            Timer.slowWaitCondition(() -> typeBoxInterface.getChildren() != null, 5000, 6000);
        }

        Optional<Widget> widgetParent = Widgets.get(ITEM_SELECTION_BOX_PARENT, SMALL_ITEM_SELECTION_BOX_COMP);
        // select item
        RSInterface parent = Interfaces.get(ITEM_SELECTION_BOX_PARENT, SMALL_ITEM_SELECTION_BOX_COMP);
        if (widgetParent.isPresent()) {
            List<Widget> childrenList = widgetParent.get().getChildren();

            if (childrenList.isEmpty())
                return false;

            interfaceChildren = parent.getChildren();
            if (interfaceChildren == null)
                return false;

            //used to be interfaceChildren.length
            for (int i = 0; i < childrenList.size(); i++) {
                RSInterface compItemInter = Interfaces.get(ITEM_SELECTION_BOX_PARENT,
                        SMALL_ITEM_SELECTION_BOX_COMP, i);

                Optional<Widget> compItemWidget = Query.widgets().inIndexPath(ITEM_SELECTION_BOX_PARENT,
                        SMALL_ITEM_SELECTION_BOX_COMP, i).findFirst();

                Optional<Widget> compItemInterWidget = Query.widgets()
                        .inIndexPath(ITEM_SELECTION_BOX_PARENT, SMALL_ITEM_SELECTION_BOX_COMP, i)
                        .findFirst();
                // this gives us 3 attempts to select the right item, sometimes it fails to click it with the scroll methods
                for (int iter = 0; iter < 3; iter++) {
                    if (compItemWidget.map(widg -> widg.toWidgetItem().map(it -> it.getId() ==
                            item.getItemID()).orElse(false)).orElse(false) &&
                            scrollToAndSelect(compItemInterWidget, item.getItemID())) {
                        Log.info("Used new itemSelection method");
                        if (Waiting.waitUntil(3500, 125, () ->
                                isItemSelected(item.getItemID())))
                            return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean isItemSelected(int itemId) {
        return Query.widgets().inIndexPath(465, 25, 21)
                .toItemQuery().idEquals(itemId).isAny();

    }

    private static boolean scrollToAndSelect(Optional<Widget> widget, int itemId) {
        for (int i = 0; i < 5; i++) {
            if (widget.map(w -> w.scrollTo() && w.click()).orElse(false) &&
                    Waiting.waitUntil(2500, 225, () ->
                            isItemSelected(itemId))) {
                Log.info("[Exchange]: Successfully scrolled");
                Utils.idlePredictableAction();
                return true;
            } else if (widget.map(w -> w.isVisible() && w.click()).orElse(false) &&
                    Waiting.waitUntil(2500, 225, () ->
                            isItemSelected(itemId))) {
                Log.info("[Exchange]: Successfully clicked");
                Utils.idlePredictableAction();
                return true;
            }
            Waiting.waitUniform(30, 60);
        }
        Log.warn("[Exchange]: Failed scroll");

        return false;
    }


    private static boolean scrollToAndSelect(Optional<Widget> widget) {
        for (int i = 0; i < 5; i++) {
            if (widget.map(w -> w.scrollTo() && w.click()).orElse(false)) {
                Log.debug("[Exchange]: Successfully scrolled");
                Waiting.waitNormal(400, 60);
                return true;
            } else if (widget.map(w -> w.isVisible() && w.click()).orElse(false)) {
                Log.debug("[Exchange]: Successfully clicked");
                Waiting.waitNormal(400, 60);
                return true;
            }
            Waiting.waitNormal(60, 20);
        }
        Log.debug("[Exchange]: Failed scroll");

        return false;
    }


    private static boolean pressFivePercentIncrease(double percentIncrease) {
        if (percentIncrease <= 50) { // we are better off typing if we need to press more than 4 times.
            Optional<Widget> button = Widgets.findWhereAction("+5%", GE_PARENT);
            for (int i = 0; i < (percentIncrease / 5); i++) {
                if (button.map(Widget::click).orElse(false))
                    Waiting.waitNormal(175, 45);
            }
            return true;
        }
        return false;
    }

    private static boolean pressFivePercentDecrease() {
        if (Interfaces.isInterfaceSubstantiated(465, 25, 10))
            return Interfaces.get(465, 25, 10).click();

        return false;
    }

    public static int getGrandExchangePrice() {
        RSInterface price = Interfaces.get(GE_PARENT, 25, 39);
        if (price != null) {
            String str = price.getText();
            if (str != null) {
                String[] split = str.split(" coin");
                String p = split[0].replaceAll(",", "");
                return Integer.parseInt(p);
            }
        }
        return 1;
    }

    public static void buyItem(GEItem geItem) {

        if (geItem.getItemQuantity() == 0)
            return;

        if (Inventory.find(995).length < 1) {
            BankManager.open(true);
            BankManager.withdraw(0, true, 995);
            if (!org.tribot.script.sdk.GrandExchange.isNearby())
                BankManager.withdrawArray(ItemID.RING_OF_WEALTH, 1);
        }

        if (Bank.isOpen())
            Bank.close();


        String itemString = Utils.getItemName(geItem.getItemID()); //needs null check or optional

        for (int i = 0; i < 3; i++) {
            openGE();
            Waiting.waitUntil(4500, 500, () -> GrandExchange.isOpen());
            if (GrandExchange.isOpen())
                break;
        }
        if (getEmptyOffer() == null) {
            collectItems();
        }

        Log.info("[GEManager]: Buying: " + itemString + " x " + geItem.quantity + " for " + geItem.percentIncrease + "% more");

        if (placeBuyOffer(geItem)) {
            Log.info("[Exchange]: Placed buy offer (new)");
            return;
        }

        if (selectBuyItem(geItem))
            General.sleep(General.random(800, 1500)); // lets load

        if (Interfaces.isInterfaceSubstantiated(GE_PARENT)
                && Interfaces.get(GE_PARENT, 25, 21).getComponentItem() == geItem.ItemID) {

            if (geItem.quantity != 1 && Interfaces.get(GE_PARENT, 25, 7) != null) { // selects item amount (custom)
                if (Interfaces.get(GE_PARENT, 25, 7).click()) {
                    Timer.waitCondition(() -> !Interfaces.get(162, 41).isHidden(), 4000, 6000);

                    if (!Interfaces.get(162, 41).isHidden() && itemString != null) {
                        Keyboard.typeString(String.valueOf(geItem.quantity));
                        Keyboard.pressEnter();
                        Timer.waitCondition(() -> Interfaces.isInterfaceSubstantiated(Interfaces.get(465, 25, 39)), 9000, 12000);
                        General.sleep(General.random(600, 1400)); //was before waitCondition
                    }
                }
            }
            if (Interfaces.isInterfaceSubstantiated(GE_PARENT, 25, 39)) { // selects item price (custom)
                int grandExchangePrice = getGrandExchangePrice();
                double db = geItem.percentIncrease / 100;
                General.println("[Debug]: Percent to add " + geItem.percentIncrease);
                General.println("[Debug]: Percent of current price " + (geItem.percentIncrease / 100 + 1));
                double offerPrice = Math.round(grandExchangePrice * (geItem.percentIncrease / 100 + 1));
                int realOfferPrice = (int) (5 * (Math.round(offerPrice / 5)) + 5);

                if (realOfferPrice > 2000)
                    realOfferPrice = (int) (100 * (Math.round(offerPrice / 100)));

                General.println("[GEManager]: GE List Price: " + grandExchangePrice);
                General.println("[GEManager]: Offer Price: " + realOfferPrice);

                //types price
                if (!pressFivePercentIncrease(geItem.percentIncrease)) {
                    if (Interfaces.get(465, 25, 12) != null) {
                        if (Interfaces.get(465, 25, 12).click())
                            Timer.waitCondition(() ->
                                            Interfaces.isInterfaceSubstantiated(162, 41),
                                    5000, 8000); // waits for price typing chat box

                        General.sleep(300, 600);// time for moving your hand from the mouse to keyboard

                        if (Interfaces.get(162, 41).isHidden() && //failsafe
                                Interfaces.get(465, 25, 12).click()) // click custom price
                            Timer.waitCondition(() ->
                                    !Interfaces.isInterfaceSubstantiated(162, 41), 5000, 8000); // waits for price typing chat box

                        if (Interfaces.isInterfaceSubstantiated(162, 41)) {
                            Keyboard.typeString(String.valueOf(realOfferPrice));

                            if (!Interfaces.get(162, 41).isHidden()) { // confirms we've typed it in the right area, so we don't click enter on chatbox
                                Keyboard.pressEnter();
                                General.sleep(200, 500);
                            }
                        }
                    }
                }
            }
            if (Interfaces.get(GE_PARENT, 25, CONFIRM_OFFER_ID).click()) { // clicks confirm offer
                General.sleep(General.randomSD(800, 5000, 2000, 500)); //need this sleep after clicking confirm

            } else { // if the ItemID in the image is not the right one (i.e. we selected the wrong item)
                if (Interfaces.isInterfaceSubstantiated(GE_PARENT, 4)) {
                    General.println("[GEManager]: Misclicked item... going back to offer screen.");
                    org.tribot.api2007.GrandExchange.goToSelectionWindow(true);
                    General.sleep(800, 2000);
                }
            }
        }
    }

    public static boolean shouldCollect;
    public static int INCOMPLETED_OFFER;
    public static int COMPLETED_OFFER;
    public static boolean SHOULD_WAIT = true;


    public static void collectItems() {
        // waitForOfferToCompleteAndRelist();
        boolean shouldWait = true;
        SHOULD_WAIT = true;
        long currentTime = System.currentTimeMillis();
        long timeoutTime = System.currentTimeMillis() + 450000; //7.5min
        while (SHOULD_WAIT || (System.currentTimeMillis() > timeoutTime)) {
            Waiting.waitUniform(125, 250);

            if (org.tribot.api2007.GrandExchange.getWindowState() !=
                    org.tribot.api2007.GrandExchange.WINDOW_STATE.SELECTION_WINDOW) {
                if (!GrandExchange.isOpen())
                    break;
                Log.info("Not at selection window, waiting briefly");
                if (Waiting.waitUntil(5000, 150, () ->
                        org.tribot.api2007.GrandExchange.getWindowState() ==
                                org.tribot.api2007.GrandExchange.WINDOW_STATE.SELECTION_WINDOW))
                    Log.info("After selection window");
                continue;
            }
            INCOMPLETED_OFFER = 0;
            COMPLETED_OFFER = 0;
            RSGEOffer[] offers = org.tribot.api2007.GrandExchange.getOffers();
            for (RSGEOffer of : offers) {
                if (of.getStatus().equals(RSGEOffer.STATUS.EMPTY))
                    continue;
                else if (of.getStatus().equals(RSGEOffer.STATUS.IN_PROGRESS)) {
                    Log.log("[Exchange]: at least one offer is in progess, waiting");
                    Waiting.waitUniform(1500, 5000);
                    checkRelist();
                }
            }


            Log.info("IN waiting loop for collection");
            List<GrandExchangeOffer> completedOfferList = Query
                    .grandExchangeOffers()
                    .statusEquals(GrandExchangeOffer.Status.COMPLETED)
                    .toList();

            List<GrandExchangeOffer> inProgressOfferList = Query
                    .grandExchangeOffers()
                    .statusEquals(GrandExchangeOffer.Status.IN_PROGRESS)
                    .toList();
            if (inProgressOfferList.size() > 0) {
                Log.info("at least 1 in progess");
                if (Waiting.waitUntil(30000, 400, () ->
                        Query.grandExchangeOffers()
                                .statusEquals(GrandExchangeOffer.Status.IN_PROGRESS)
                                .toList().size() == 0))
                    Log.info("No more offers in progress");
                continue;
            }
            if (completedOfferList.size() > 0 && inProgressOfferList.size() > 0) {
                int b = General.randomSD(4500, 280);
                Log.info(String.format("We have %s completed, and %s offers in progress",
                        completedOfferList.size(), inProgressOfferList.size()));

                clickCollect();
                SHOULD_WAIT = true; // will cause loop to continue
                Waiting.waitUntil(30000, 400, () ->
                        Query.grandExchangeOffers()
                                .statusEquals(GrandExchangeOffer.Status.IN_PROGRESS)
                                .toList().size() == 0);
                Log.info("Sleeping for  " + b + "ms");
                Waiting.wait(b);
                //General.sleep(800, 1500); // short sleep to wait for offers (decrease CPU Use)
            } else if (completedOfferList.size() == 0 && inProgressOfferList.size() > 0) {
                Log.info(String.format("We have 0 completed, and %s offers in progress",
                        inProgressOfferList.size()));
                SHOULD_WAIT = true; // will cause loop to continue
                checkRelist();
                Waiting.waitUntil(30000, 400, () ->
                        Query.grandExchangeOffers()
                                .statusEquals(GrandExchangeOffer.Status.IN_PROGRESS)
                                .toList().size() == 0);
                Waiting.waitNormal(600, 65);
            } else if (completedOfferList.size() > 0) {
                Log.info(String.format("We have %s completed, and 0 offers in progress",
                        completedOfferList.size()));
                Utils.idleNormalAction();
                clickCollect();

                SHOULD_WAIT = false;
                break;
            } else { // no offers placed, will break the loop
                General.println("[Debug]: No offers placed.");
                SHOULD_WAIT = false;
                break;
            }
            if (System.currentTimeMillis() > timeoutTime) {
                Log.warn("Breaking loop based on time out");
                break;
            }
        }
        Log.info("[Exchange] finished while loop");
        Optional<Widget> collectButton = Query.widgets().inIndexPath(465)
                .textContains("Collect").isVisible().findFirst();
        if (collectButton.map(Widget::click).orElse(false)) {
            General.println("[GEManager]: Collecting Failsafe");
            Timer.waitCondition(() -> BankManager.inventoryChange(true), 3000, 5000);
        }
    }

    public static RSGEOffer IN_PROGRESS_OFFER;

    /**
     * used to check if we have any placed offers that HAVE NOT completed
     *
     * @returns as true if there's >=1 in progress offer
     */
    private static Optional<RSGEOffer> checkForOffersInProgress() {
        if (org.tribot.api2007.GrandExchange.getWindowState() != org.tribot.api2007.GrandExchange.WINDOW_STATE.SELECTION_WINDOW)
            openGE();

        if (goToSelectionWindow()) {
            RSGEOffer[] offers = org.tribot.api2007.GrandExchange.getOffers();
            for (RSGEOffer ofr : offers) {
                if (ofr.getStatus() == RSGEOffer.STATUS.IN_PROGRESS &&
                        ofr.getType() == RSGEOffer.TYPE.BUY) {
                    return Optional.of(ofr);

                }
            }
        }
        return Optional.empty();
    }

    public static void checkRelist() {
        if (org.tribot.api2007.GrandExchange.getWindowState() == org.tribot.api2007.GrandExchange.WINDOW_STATE.SELECTION_WINDOW) {
            clickCollect(); // collects items first, otherwise an issue if all windows are in use
            General.sleep(General.random(1500, 3000)); // allows for the last offer placed to insta-complete if it's going to.

            Optional<RSGEOffer> offer = checkForOffersInProgress();
            if (offer.isPresent()) {
                int index = offer.get().getIndex() + 7; // 7 is to make index be the right child
                int amount = 1;

                if (Interfaces.isInterfaceSubstantiated(465, index, 18))
                    amount = Interfaces.get(465, index, 18).getComponentStack();

                int id = offer.get().getItemID();
                int price = offer.get().getPrice();

                price = getListPrice(offer.get());

                General.println("[GEManager]: Relisting item: " + RSItemDefinition.get(id).getName() + " at " + (price * 1.2) + " coins");

                if (offer.get().click("Abort offer")) {
                    General.println("[GEManager]: Aborting offer");
                    Timer.waitCondition(() -> offer.get().getStatus() == RSGEOffer.STATUS.CANCELLED, 6000, 8000);
                }
                int newPrice = (int) (price * 1.2) + 10;
                // will attempt to re-buy at 20% more, does not collect canceled gold before.
                buyItemFlat(id, newPrice, amount);

            }

        }
    }

    public static void buyItemFlat(int ItemID, int flatPrice, int quantity) {
        BankManager.determinePurchaseAmounts(ItemID, quantity);
        RSItemDefinition itemDef = RSItemDefinition.get(ItemID);
        String itemString = itemDef.getName();
        if (BankManager.itemsToBuy < 0) {
            // DO NOT try and close the GE Here, it will close and open it for each item if you do this
            return;
        }
        if (BankManager.itemsToBuy > 0) {
            GEItem geItem = new GEItem(ItemID, quantity);

            General.println("[GEManager]: Buying: " + itemString + " x " + BankManager.itemsToBuy);
            if (selectBuyItem(geItem))
                General.sleep(800, 2000);


            if (Interfaces.isInterfaceSubstantiated(GE_PARENT)
                    && Interfaces.get(GE_PARENT, 25, 21).getComponentItem() == geItem.ItemID) {

                if (geItem.quantity != 1 && Interfaces.get(GE_PARENT, 25, 7) != null) { // selects item amount (custom)
                    if (Interfaces.get(GE_PARENT, 25, 7).click()) {
                        Timer.waitCondition(() -> !Interfaces.get(162, 41).isHidden(), 4000, 6000);

                        if (!Interfaces.get(162, 41).isHidden() && itemString != null) {
                            Keyboard.typeString(String.valueOf(geItem.quantity));
                            Keyboard.pressEnter();
                            Timer.waitCondition(() ->
                                    Interfaces.isInterfaceSubstantiated(Interfaces.get(465, 25, 39)), 9000, 12000);
                            General.sleep(General.random(600, 1400)); //was before waitCondition
                        }
                    }
                }
                if (Interfaces.get(465, 25, 39) != null) { // selects item price (custom)

                    int GE_PRICE = getGrandExchangePrice();
                    General.println("[GEManager]: GE Price: " + GE_PRICE);
                    General.println("[GEManager]: Using Flat Price: " + flatPrice);
                    RSInterface listPriceButton = Interfaces.get(465, 25, 12);
                    if (listPriceButton != null &&
                            listPriceButton.click()) {
                        Timer.waitCondition(() -> Interfaces.isInterfaceSubstantiated(162, 41),
                                General.random(6000, 8000));
                        Keyboard.typeString(String.valueOf(flatPrice));
                        General.sleep(1800, 3000); // this would be the time from typing to grabbing your mouse
                        RSInterface typeAreaInter = Interfaces.get(162, 41);
                        if (typeAreaInter != null && !typeAreaInter.isHidden()) { // confirms we've typed it in the right area, so we don't click enter on chatbox
                            Keyboard.pressEnter();
                            General.sleep(200, 500);
                        }

                        if (Interfaces.get(465, 25, 54).click())
                            Waiting.waitNormal(1500, 300);
                    }


                } else { // if the ItemID in the image is not the right one (i.e. we selected the wrong item)
                    if (Interfaces.get(465, 4) != null) {
                        General.println("[GEManager]: Misclicked item... going back to offer screen.");
                        Interfaces.get(465, 4).click();
                        General.sleep(General.random(800, 2000));
                    }
                }
            }
        } else {
            return;

        }
    }

    private static boolean clickCollect() {
        if (GrandExchange.collectAll()) {
            Log.info("[Exchange]: SDK Collection");
            Waiting.waitUntil(30000, 400, () ->
                    Query.grandExchangeOffers()
                            .statusNotEquals(GrandExchangeOffer.Status.EMPTY)
                            .toList().size() == 0);
            return true;
        }
        Log.error("[Exchange]: SDK Collection FAILED");
        Optional<Widget> collectButton = Query.widgets().inIndexPath(465)
                .textContains("Collect").findFirst();
        for (int i = 0; i < 3; i++) {
            if (collectButton.map(Widget::click).orElse(false)) {
                General.println("[GEManager]: Collecting");
                if (Timer.waitCondition(() -> BankManager.inventoryChange(true), 3000, 5000))
                    break;
            } else {
                Log.info("[GEManager]: Collecting loop ");
                Waiting.waitUniform(500, 900);
            }
        }
        return false;
    }


}
