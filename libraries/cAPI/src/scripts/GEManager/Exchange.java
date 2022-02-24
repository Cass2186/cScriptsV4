package scripts.GEManager;


import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Keyboard;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.*;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.*;
import org.tribot.script.sdk.Bank;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.Widgets;
import org.tribot.script.sdk.types.Widget;
import scripts.*;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class Exchange {

    public static int GE_PARENT = 465;
    public static int CONFIRM_OFFER_ID = 54;
    public static int SEARCH_ITEM_NAME_INTERFACE_ID = 42;
    public static int GE_BOOTH_ID = 10061;


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
        return GrandExchange.getWindowState() == GrandExchange.WINDOW_STATE.SELECTION_WINDOW ||
                GrandExchange.goToSelectionWindow(true);
    }

    public static RSGEOffer getEmptyOffer() {
        if (goToSelectionWindow()) {
            RSGEOffer[] offers = GrandExchange.getOffers();
            for (RSGEOffer ofr : offers) {
                if (ofr.getStatus() == RSGEOffer.STATUS.EMPTY)
                    return ofr;
            }
        }
        return null;
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

    public static boolean closeGE() {
        return GrandExchange.close(true);
    }

    public static boolean isGrandExchangeOpen() {
        return (GrandExchange.getWindowState() == GrandExchange.WINDOW_STATE.SELECTION_WINDOW ||
                GrandExchange.getWindowState() == GrandExchange.WINDOW_STATE.OFFER_WINDOW ||
                GrandExchange.getWindowState() == GrandExchange.WINDOW_STATE.NEW_OFFER_WINDOW);

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
            //get Coins
        }

        if (Banking.isBankScreenOpen())
            Banking.close(); //replace with custom method later

        RSObject[] booth = Objects.findNearest(20, Filters.Objects.actionsContains("Exchange"));

        if (!isGrandExchangeOpen() && booth.length > 0) {
            if (Utils.clickObject(booth[0], "Exchange", true))
                Timer.waitCondition(() -> GrandExchange.getWindowState() ==
                        GrandExchange.WINDOW_STATE.SELECTION_WINDOW ||
                        NPCInteraction.isConversationWindowUp(), 5000, 7000);

            if (NPCInteraction.isConversationWindowUp()) {
                NPCInteraction.handleConversation("I'd like to set up trade offers please.");
                return Timer.waitCondition(() -> (GrandExchange.getWindowState() == GrandExchange.WINDOW_STATE.SELECTION_WINDOW), 5000, 7000);
            }
        }

        return GrandExchange.getWindowState() ==
                GrandExchange.WINDOW_STATE.SELECTION_WINDOW;
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
                General.println("[GEManager]: Creating buy offer: " + index);
                String s = "Create <col=ff9040>Buy</col> offer";
                if (InterfaceUtil.click(465, index + 7, 3))
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
            if (stringLength > 11) {
                int firstInt = General.random(6, 10);
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
            Waiting.waitUniform(1000, 2000);
            Timer.slowWaitCondition(() -> typeBoxInterface.getChildren() != null, 5000, 6000);
        }

        // select item
        RSInterface parent = Interfaces.get(ITEM_SELECTION_BOX_PARENT, SMALL_ITEM_SELECTION_BOX_COMP);
        if (parent != null) {
            interfaceChildren = parent.getChildren();
            for (int i = 0; i < interfaceChildren.length; i++) {

                RSInterface compItemInter = Interfaces.get(ITEM_SELECTION_BOX_PARENT,
                        SMALL_ITEM_SELECTION_BOX_COMP, i);

                if (compItemInter != null) {
                    componentItem = compItemInter.getComponentItem();
                    // General.println("Line 385 - ID: " + componentItem + " || Item We are looking for " + item.getItemID());
                    RSInterface scrollBarInter = Interfaces.get(ITEM_SELECTION_BOX_PARENT, 51, 3);
                    // identified the item in the options
                    if (componentItem == item.getItemID() && scrollBarInter != null) {
                        if (i >= 25) {// item should be offs  screen if this is true (check item index for this)
                            //  if (y > 65) { // is off screen if this is true (checking y point)
                            long startTime = System.currentTimeMillis();
                            long add = General.random(10000, 15000);
                            for (int b = 0; b < 50; b++) {
                                if (compItemInter.getAbsolutePosition().getY() >
                                        (Interfaces.get(162, 51, 3).getAbsolutePosition().getY())) {
                                    //  Log.log("Scroll interface 3 is  " + compItemInter.getAbsolutePosition().getY());
                                    //  Log.log("Y is " + compItemInter.getY());
                                    if (!parent.getAbsoluteBounds().contains(Mouse.getPos())) //move mouse to box if needed
                                        Mouse.moveBox(parent.getAbsoluteBounds());

                                    Mouse.scroll(false); //scrolls down
                                    General.sleep(General.random(50, 400));

                                    if (System.currentTimeMillis() > startTime + add) { // failed to scroll in 7-15s
                                        Log.log("Timed out");
                                        break;
                                    }
                                } else
                                    break;
                            }
                            Waiting.waitNormal(200, 55);      // sleep once we scroll to right point
                            //}
                        }

                        // select item and wait for it to be selected in GE
                        if (compItemInter.click())
                            return Timer.waitCondition(() -> Interfaces.get(465, 25, 21) != null &&
                                    Interfaces.get(465, 25, 21).getComponentItem() == item.getItemID(), 4000, 6000);
                    }
                }
            }
        }
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
            if (Interfaces.isInterfaceSubstantiated(GE_PARENT))
                break;
        }
        if (getEmptyOffer() == null) {
            collectItems();
        }

        General.println("[GEManager]: Buying: " + itemString + " x " + geItem.quantity + " for " + geItem.percentIncrease + "% more");

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
                    GrandExchange.goToSelectionWindow(true);
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
        long currentTime = System.currentTimeMillis();

        while (shouldWait) {
            General.sleep(50);
            INCOMPLETED_OFFER = 0;
            COMPLETED_OFFER = 0;
            SHOULD_WAIT = false; // while the bottom 4 conditions should cover everything, this ensures it doesn't loop indefinitely should another situation occur
            RSGEOffer[] offers = GrandExchange.getOffers();
            for (RSGEOffer of : offers) {
                if (of.getStatus().equals(RSGEOffer.STATUS.EMPTY))
                    continue;
                else if (of.getStatus().equals(RSGEOffer.STATUS.IN_PROGRESS)) {
                    Log.log("[Exchange]: at least one offer is in progess, waiting");
                    Waiting.waitUniform(1500, 5000);
                    checkRelist();
                }
            }
            RSGEOffer[] off = GrandExchange.getOffers();
            for (RSGEOffer o : off) {
                if (o.getStatus().equals(RSGEOffer.STATUS.IN_PROGRESS))
                    INCOMPLETED_OFFER++;
                else if (o.getStatus().equals(RSGEOffer.STATUS.COMPLETED))
                    COMPLETED_OFFER++;
            }

            if (COMPLETED_OFFER > 0 && INCOMPLETED_OFFER > 0) {
                General.println("[Debug]: >= 1 completed offer and incompleted offer, collecting and waiting");
                int b = General.randomSD(3500, 400);
                Log.log("[Debug]: Sleeping for  " + b + "ms");
                Waiting.wait(b);
                clickCollect();
                SHOULD_WAIT = true; // will cause loop to continue
                General.sleep(800, 1500); // short sleep to wait for offers (decrease CPU Use)
            } else if (COMPLETED_OFFER == 0 && INCOMPLETED_OFFER > 0) {
                General.println("[Debug]: < 1 completed offer and >0 incompleted offers, waiting");
                SHOULD_WAIT = true; // will cause loop to continue
                checkRelist();
                General.sleep(800, 1500); // short sleep to wait for offers (decrease CPU Use)
            } else if (COMPLETED_OFFER > 0 && INCOMPLETED_OFFER == 0) {
                General.println("[Debug]: >= 1 completed offer and NO incompleted offers, collecting");
                Utils.abc2ReactionSleep(currentTime);
                clickCollect();
                SHOULD_WAIT = false; // by declaring SHOULD_WAIT false here, it ensures we break the loop, otherwise it would continue
                break;
            } else if (COMPLETED_OFFER == 0 && INCOMPLETED_OFFER == 0) { // no offers placed, will break the loop
                General.println("[Debug]: No offers placed.");
                SHOULD_WAIT = false;
                break;
            }

        }
    }

    public static RSGEOffer IN_PROGRESS_OFFER;

    /**
     * used to check if we have any placed offers that HAVE NOT completed
     *
     * @returns as true if there's >=1 in progress offer
     */
    private static Optional<RSGEOffer> checkForOffersInProgress() {
        if (GrandExchange.getWindowState() != GrandExchange.WINDOW_STATE.SELECTION_WINDOW)
            openGE();

        if (goToSelectionWindow()) {
            RSGEOffer[] offers = GrandExchange.getOffers();
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
        if (GrandExchange.getWindowState() == GrandExchange.WINDOW_STATE.SELECTION_WINDOW) {
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

    public static boolean clickCollect() {
        if (Interfaces.isInterfaceSubstantiated(465, 6, 1)) {
            General.println("[GEManager]: Collecting");
            if (!Interfaces.get(465, 6, 1).isHidden()) { // this is the collect button, it's never null, so we check if it's hidden
                Interfaces.get(465, 6, 1).click();
                General.sleep(General.random(300, 1000));
                Timing.waitCondition(() -> BankManager.inventoryChange(true), General.random(3000, 5000));
                if (Interfaces.isInterfaceSubstantiated(465, 6, 1)) {
                    General.println("[Debug]: Still have a valid collect bar, trying to collect again.");
                    return Interfaces.get(465, 6, 1).click();

                } else {
                    return true;
                }
            }
        }
        return false;
    }


}
