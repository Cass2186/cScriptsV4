package scripts.QuestPackages.RfdEvilDave;

import dax.walker.utils.AccurateMouse;
import dax.walker.utils.camera.DaxCamera;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.*;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.*;
import org.tribot.script.sdk.*;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.InventoryItem;
import org.tribot.script.sdk.types.Npc;
import org.tribot.script.sdk.util.TribotRandom;
import scripts.*;
import scripts.EntitySelector.Entities;
import scripts.EntitySelector.finders.prefabs.NpcEntity;
import scripts.QuestPackages.GhostsAhoy.GhostsAhoy;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.ItemRequirement;
import scripts.Requirements.Requirement;
import scripts.Tasks.Priority;

import java.awt.*;
import java.util.Arrays;
import java.util.Optional;

public class RfdEvilDave implements QuestTask {


    int BOWL = 1923;
    int min = 900000; // 15 min
    int max = 1050000; //  just under 20 min
    final long startTime = Timing.currentTimeMillis();
    long WHEN_TO_FEED = (startTime + (General.random(min, max)));
    long currentTime = Timing.currentTimeMillis();


    String[] CAT_NAMES = {"Pet cat", "Pet kitten", "Hell-cat", "Hell-kitten", "Hellcat", "Hell-kitten", "Lazy cat", "Wily cat"};

    int EMPTY_SHAKER = 7496;

    // RSArea BROWN_AREA = new RSArea(new RSTile(3079, 9887, 0), new RSTile(3086, 9878, 0));
    RSArea YELLOW_AREA = new RSArea(new RSTile(3073, 9887, 0), new RSTile(3079, 9878, 0));
    RSArea RED_AREA = new RSArea(new RSTile(3079, 9898, 0), new RSTile(3084, 9892, 0));
    RSArea ORANGE_AREA = new RSArea(new RSTile(3079, 9893, 0), new RSTile(3073, 9898, 0));

    // top part
    RSArea BROWN_AREA = new RSArea(new RSTile(3084, 9894, 0), new RSTile(3075, 9898, 0));

    public enum State {
        LOOTING_SPICE, INTERACTING_WITH_CAT, WAITING_FOR_CAT, COMBINE_SPICES, WALKING_TO_MOMS_HOUSE,
        ENTERING_THE_BASEMENT_OF_DOOM, INSPECTING_GROUND_ITEMS, DROPPING_EMPTY_SHAKERS, MOVING_TO_RANDOM_TILE
    }

    int[] RED_SPICES = {7480, 7481, 7482, 7483};
    int[] YELLOW_SPICES = {7492, 7493, 7494, 7495};
    int[] ORANGE_SPICES = {7484, 7485, 7486, 7487};
    int[] BROWN_SPICES = {7488, 7489, 7490, 7491};

    int[] PET_KITTEN_IDS = {1555, 1556, 1557, 1558, 1559, 1560};
    int[] REGULAR_CAT = {1561, 1562, 1563, 1564, 1565, 1566};
    int[] OVERGROWN_CAT = {1567, 1568, 1569, 1570, 1571, 1572};

    int SALMON = 329;
    int STEW = 2003;
    int FALADOR_TAB = 8009;
    int LUMBRIDGE_TAB = 8008;

    public static int RED_SPICE_NUMBER;
    public static int YELLOW_SPICE_NUMBER;
    public static int BROWN_SPICE_NUMBER;
    public static int ORANGE_SPICE_NUMBER;

    boolean RED_CHECK = false;
    boolean YELLOW_CHECK = false;
    boolean ORANGE_CHECK = false;
    boolean BROWN_CHECK = false;
    boolean HAS_FINAL_STEW = false;

    String spice = "R(" + RED_SPICE_NUMBER + ") || Y(" + YELLOW_SPICE_NUMBER + ") || B(" + BROWN_SPICE_NUMBER +
            ") || O(" + ORANGE_SPICE_NUMBER;

    public boolean feedCat() {
        Optional<Npc> myKitten = getFollowerRSNPC();
        int lng = Query.inventory().idEquals(ItemID.SALMON).toList().size();
        Optional<InventoryItem> salmon = Query.inventory().idEquals(ItemID.SALMON).findClosestToMouse();
        if (salmon.isEmpty()) {
            pickupCat();
            Log.info("Should be feeding cat, but we are out of food or don't have a pet.");
            return false;
        }
        cQuesterV2.status = "Feeding Cat";
        Log.info("Feeding Cat");
        for (int i = 0; i < 3; i++) {
            if (salmon.map(s -> myKitten.map(s::useOn).orElse(false)).orElse(false)) {
                if (Timer.waitCondition(() -> Query.inventory().idEquals(ItemID.SALMON).toList().size() < lng, 3500, 5500)) {
                    feedTimer.reset();
                    return true;
                }
                Log.info("Seemingly failed to feed cat, looping (i = " + i + ")");
            }
            General.sleep(400, 900);
        }
        return false;
    }

    Timer feedTimer = new Timer(General.random(900000, 1000000));
    Timer interactTimer = new Timer(General.random(420000, 720000)); //7-12 min

    public void checkFeeding() {
        if (!feedTimer.isRunning()) {
            feedCat();
        }
    }

    public void interactWithCat() {
        Optional<Npc> myKitten = getFollowerRSNPC();
        if (myKitten.isPresent()) {
            for (int i = 0; i < 2; i++) {
                if (myKitten.map(n -> n.interact("Interact")).orElse(false)) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation("Stroke");
                    NPCInteraction.waitForConversationWindow();
                    interactTimer.reset();
                }
            }
        }
    }

    public void checkInteraction() {
        if (!interactTimer.isRunning()) {
            cQuesterV2.status = "Interacting with Cat";
            Log.info("Stroking Cat");
            interactWithCat();
        }
    }

    RSArea LUMBRIDGE_AREA = new RSArea(new RSTile(3213, 3225, 0), new RSTile(3216, 3219, 0));
    RSArea EVIL_DAVES_UPPER_FLOOR = new RSArea(new RSTile(3077, 3496, 0), new RSTile(3081, 3489, 0));
    RSArea EVIL_DAVES_UNDERGROUND = new RSArea(new RSTile(3086, 9873, 0), new RSTile(3066, 9903, 0));


    int[] SPICE_IDS = {7480, 7481, 7482, 7483, 7484, 7485, 7486, 7487, 7488, 7489, 7490, 7491, 7492, 7493, 7494, 7495};

    RSGroundItem[] groundSpices = GroundItems.find(SPICE_IDS);

    RSItem[] invItem1, one, two, three, four;
    RSItem[] emptyShakers;

    /**
     * Strings
     */
    String[] DORIS_CHAT_1 = {
            "Is Dave in?",
            "Yes, I'm evil!"
    };

    String[] EVIL_DAVE_CHAT_1 = {
            "What did you eat at the secret council meeting?",
            "You've got to tell me because the magic requires it!"
    };


    /**
     * **********Methods***********
     */
    private void dropEmptyShakers() {
        emptyShakers = Inventory.find(EMPTY_SHAKER);
        if (emptyShakers.length > 0) {
            Log.info("Dropping empty spice shakers");
            Inventory.drop(EMPTY_SHAKER, BOWL);

            Waiting.waitUniform(500, 1700);
            Utils.unselectItem();
        }
    }


    //**********QUEST*************//
    public void buyItems() {
        cQuesterV2.status = "Buying Items";
        Log.info("" + cQuesterV2.status);
      /*  GEManager.getCoins();
        GEManager.openGE();
        GEManager.buyItem(FALADOR_TAB, 30, 5);
        GEManager.buyItem(STEW, 150, 20);
        GEManager.buyItem(SALMON, 50, 10);
        GEManager.buyItem(LUMBRIDGE_TAB, 30, 3);
        GEManager.buyItem(Constants.AMULET_OF_GLORY[0], 15, 1);
        GEManager.buyItem(Constants.STAMINA_POTION[0], 15, 1);
        GEManager.collectItems();
        GEManager.closeGE();*/
    }

    public void getItems() {
        cQuesterV2.status = "Getting Items";
        Log.info("Getting Items");
        BankManager.open(true);
        BankManager.depositAll(true);
        BankManager.withdraw(20, true, STEW);
        BankManager.withdraw(4, true, SALMON);
        BankManager.withdraw(3, true, LUMBRIDGE_TAB);
        BankManager.withdraw(1, true, ItemID.AMULET_OF_GLORY[0]);
        BankManager.withdraw(1, true, ItemID.STAMINA_POTION[0]);
        BankManager.withdraw(1, true, PET_KITTEN_IDS);
        BankManager.withdraw(1, true, REGULAR_CAT);
        BankManager.checkEquippedGlory();
        BankManager.close(true);
    }

    public void dropKitten(int[] catIds) {
        RSItem[] invKitten = Inventory.find(catIds);
        if (invKitten.length == 1) {
            cQuesterV2.status = "Dropping kitten";
            Log.info("Dropping kitten");
            if (invKitten[0].click("Drop")) {
                Timer.waitCondition(() -> Inventory.find(catIds).length == 0, 1500, 2500);
            }
            // we interact and feed upon dropping b/c we don't know the last time it happened
            feedCat();
            interactWithCat();

        }
    }

    public void dropKitten(int catIds) {
        RSItem[] invKitten = Inventory.find(catIds);
        if (invKitten.length == 1) {
            cQuesterV2.status = "Dropping kitten";
            Log.info("Dropping kitten");
            if (invKitten[0].click("Drop")) {
                Timer.waitCondition(() -> Inventory.find(catIds).length == 0, 1500, 2500);
            }
            // we interact and feed upon dropping b/c we don't know the last time it happened
            feedCat();
            interactWithCat();

        }
    }

    public void goToStart() {
        cQuesterV2.status = "Going to Start Quest";
        PathingUtil.walkToArea(LUMBRIDGE_AREA);
        if (Utils.clickObject("Large door", "Open", false)) {
            Timer.waitCondition(() -> NPCs.find(12341).length > 0, 8000);
            General.sleep(General.random(4000, 8000));
        }
    }

    public void inspectDave() {
        cQuesterV2.status = "Inspecting Evil Dave";
        if (Utils.clickObject(12341, "Inspect", false)) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }
    }


    public void step2() {
        if (!EVIL_DAVES_UPPER_FLOOR.contains(Player.getPosition()) && !EVIL_DAVES_UNDERGROUND.contains(Player.getPosition())) {
            cQuesterV2.status = "Going To Evil Dave's House";
            PathingUtil.walkToArea(EVIL_DAVES_UPPER_FLOOR);
        }
        if (NpcChat.talkToNPC("Doris")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation(DORIS_CHAT_1);
        }
    }


    public void step2b() {
        goDownTrapDoor();
        if (EVIL_DAVES_UNDERGROUND.contains(Player.getPosition())) {
            cQuesterV2.status = "Talking To Evil Dave";
            if (NpcChat.talkToNPC("Evil Dave")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation(EVIL_DAVE_CHAT_1);
            }
        }
    }

    public boolean goDownTrapDoor() {
        if (!EVIL_DAVES_UPPER_FLOOR.contains(Player.getPosition())) {
            PathingUtil.walkToArea(EVIL_DAVES_UPPER_FLOOR, false);
        }
        if (EVIL_DAVES_UPPER_FLOOR.contains(Player.getPosition())) {
            cQuesterV2.status = "Going down trap door";
            if (Utils.clickObject("Trapdoor", "Open", false))
                Timer.waitCondition(() -> Objects.findNearest(20, "Open trapdoor").length > 0, 6000);

            if (Utils.clickObject("Open trapdoor", "Go-down", false))
                return Timer.waitCondition(() -> EVIL_DAVES_UNDERGROUND.contains(Player.getPosition()), 6000);
        }
        return EVIL_DAVES_UNDERGROUND.contains(Player.getPosition());
    }

    public static void talkToNPC(String npcName, String... responses) {
        if (NpcChat.talkToNPC(npcName)) {
            NPCInteraction.waitForConversationWindow();
            for (String text : responses) {
                NPCInteraction.handleConversation(text);
            }
            NPCInteraction.handleConversation();
        }
    }

    public void TalkToDoris2() {
        if (EVIL_DAVES_UNDERGROUND.contains(Player.getPosition())) {
            cQuesterV2.status = "Going to Doris";
            if (Utils.clickObject(12265, "Climb", false))
                Timer.waitCondition(() -> EVIL_DAVES_UPPER_FLOOR.contains(Player.getPosition()), 7000, 9000);
        }

        if (EVIL_DAVES_UPPER_FLOOR.contains(Player.getPosition())) {
            cQuesterV2.status = "Talking to Doris";
            if (NpcChat.talkToNPC("Doris")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
            goDownTrapDoor();
        }
    }

    /**
     * ********** HELLRAT CATCHING - MINE ***************
     **/
    public void chaseRats() {
        if (Inventory.find(PET_KITTEN_IDS).length > 0)
            dropKitten(PET_KITTEN_IDS);

        else if (Inventory.find(REGULAR_CAT).length > 0)
            dropKitten(REGULAR_CAT);

        RSItem[] hellCat = Inventory.find(Filters.Items.nameContains("Hell"));
        if (hellCat.length > 0) {
            dropKitten(hellCat[0].getID());
        }


        if (!CENTER_AREA.contains(Player.getPosition())) {
            if (EVIL_DAVES_UNDERGROUND.contains(Player.getPosition())) {
                if (!moveArea()) {
                    General.println("Failed to move to brown area");
                    if (!CENTRE_TILE.isClickable()) {
                        PathingUtil.localNavigation(CENTRE_TILE);
                    } else {
                        PathingUtil.clickScreenWalk(CENTRE_TILE);
                        PathingUtil.movementIdle();
                    }
                }
            } else {
                cQuesterV2.status = "Going to basement";
                Log.info("Going to house");
                goDownTrapDoor();
                // need to go back to basement
            }
        }

        Optional<Npc> npcCat = getFollowerRSNPC();

        cQuesterV2.status = "Making cat chase mice";
        if (npcCat.map(n -> n.interact("Chase")).orElse(false)) {
            General.sleep(5000, 8000);
            Waiting.waitUntil(TribotRandom.uniform(6500, 9000), TribotRandom.uniform(400, 750),
                    () -> npcCat.get().getTile().distance() < 2);
        }

    }

    String fail = "The rate manages to get away!";

    public void getSpices() {
        cQuesterV2.status = "Chasing Rats";
        chaseRats();
     /*   if (checkChat()) {
            Log.info("Cat missed rat");
            Timer.waitCondition(() -> npcCat[0].getPosition().distanceTo(Player.getPosition()) < 2, 6000); // waits unitl cat has returned to you
          ;
        } else
            */
        if (GroundItems.find(SPICE_IDS).length > 0) { // we did not get the fail message, so we look for ground items
            groundSpices = GroundItems.find(SPICE_IDS);
            cQuesterV2.status = "Looting spice";
            Log.info("" + cQuesterV2.status);
            if (Utils.clickGroundItem(SPICE_IDS)) {
                Log.info("Successfully looted spice");
            }
            if (shouldCombineSpices()) {
                combineSpices();
                dropEmptyShakers();
            }
            // moveArea();
        }
    }

    private boolean shouldCombineSpices() {
        return (shouldCombineSpice("ORANGE")) ||
                (shouldCombineSpice("BROWN")) ||
                (shouldCombineSpice("RED")) ||
                (shouldCombineSpice("YELLOW"));
    }

    private boolean getSpiceNumber() {
        return (getSpiceNumber("ORANGE")) ||
                (getSpiceNumber("BROWN")) ||
                (getSpiceNumber("RED")) ||
                (getSpiceNumber("YELLOW"));
    }

    public boolean getSpiceNumber(String colour) {
        RSItem[] colourFiltered = Inventory.find(Filters.Items.nameContains(colour));
        if (colourFiltered.length > 1) { // greater than 1 length means we have at least 2
            Log.info("Identified inventory spices based on passed string of color: " + colour);
            one = Inventory.find(Filters.Items.nameContains("spice (1)").and(Filters.Items.nameContains(colour)));
            two = Inventory.find(Filters.Items.nameContains("spice (2)").and(Filters.Items.nameContains(colour)));
            three = Inventory.find(Filters.Items.nameContains("spice (3)").and(Filters.Items.nameContains(colour)));
            four = Inventory.find(Filters.Items.nameContains("spice (4)").and(Filters.Items.nameContains(colour)));

            int sum = (one.length + two.length + three.length + four.length);
            Log.info("We have " + sum + " doses of the spice: " + colour);
            return two.length > 1 ||
                    (three.length > 0 && one.length > 0) ||
                    (two.length > 0 && one.length > 0) ||
                    (two.length > 0 && three.length > 0) ||
                    (one.length > 1) || (three.length > 1); // returns true if any of these conditions/combos are met
        }
        return false;
    }


    public boolean shouldCombineSpice(String colour) {
        RSItem[] colourFiltered = Inventory.find(Filters.Items.nameContains(colour));
        if (colourFiltered.length > 1) { // greater than 1 length means we have at least 2
            one = Inventory.find(Filters.Items.nameContains("spice (1)").and(Filters.Items.nameContains(colour)));
            two = Inventory.find(Filters.Items.nameContains("spice (2)").and(Filters.Items.nameContains(colour)));
            three = Inventory.find(Filters.Items.nameContains("spice (3)").and(Filters.Items.nameContains(colour)));
            return two.length > 1 ||
                    (three.length > 0 && one.length > 0) ||
                    (two.length > 0 && one.length > 0) ||
                    (two.length > 0 && three.length > 0) ||
                    (one.length > 1) || (three.length > 1); // returns true if any of these conditions/combos are met
        }
        return false;
    }

    private void combineSpices() {
        cQuesterV2.status = "Combining spices";
        Log.info("" + cQuesterV2.status);
        if (two.length > 1 && two[0].click()) {
            General.sleep(100, 200);
            if (two[1].click()) {
                General.sleep(600, 900);
            }

        } else if (three.length > 0 && one.length > 0) {
            if (three[0].click()) {
                General.sleep(100, 200);
                if (one[0].click()) {
                    General.sleep(600, 900);
                }
            }
        } else if (two.length > 0 && one.length > 0) {
            if (two[0].click()) {
                General.sleep(100, 200);
                if (one[0].click()) {
                    General.sleep(600, 900);
                }
            }
        } else if (two.length > 0 && three.length > 0) {
            if (two[0].click()) {
                General.sleep(100, 200);
                if (three[0].click()) {
                    General.sleep(600, 900);
                }
            }
        } else if (one.length > 1) {
            if (one[0].click()) {
                General.sleep(100, 200);
                if (one[1].click()) {
                    General.sleep(600, 900);
                }
            }
        } else if (three.length > 1) {
            if (three[0].click()) {
                General.sleep(100, 200);
                if (three[1].click()) {
                    General.sleep(600, 900);
                }
            }
        }
    }


    public boolean checkSpiceNumber(int[] spice) {
        if (Inventory.find(spice[0]).length > 1) { // we have red spice (4) x2
            return true;
        } else if (Inventory.find(spice[0]).length > 0 && Inventory.find(spice[1]).length > 0) { // we have red spice (4) and red spice(3)
            return true;
        } else if (Inventory.find(spice[0]).length > 0 && Inventory.find(spice[2]).length > 0) { // we have red spice (4) and red spice(2)
            return true;
        } else if (Inventory.find(spice[1]).length > 1) { // we have red spice (3) x2
            return true;
        }
        return false;
    }

    public boolean moveArea() {
        if (checkSpiceNumber(ORANGE_SPICES) && checkSpiceNumber(YELLOW_SPICES) &&
                checkSpiceNumber(RED_SPICES) &&
                !checkSpiceNumber(BROWN_SPICES) &&
                !BROWN_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Have all spices EXCEPT BROWN, going to brown";
            General.println("[Deubug]: " + cQuesterV2.status);

            Walking.blindWalkTo(BROWN_AREA.getRandomTile());
            return Timer.waitCondition(() -> BROWN_AREA.contains(Player.getPosition()), 7000);

        }
        return BROWN_AREA.contains(Player.getPosition());
    }

    RSTile CENTRE_TILE = new RSTile(3079, 9886, 0);
    RSArea CENTER_AREA = new RSArea(CENTRE_TILE, 3);


    public void pickupCat() {
        Optional<Npc> npcCat = getFollowerRSNPC();
        if (Inventory.find(CAT_NAMES).length < 1) {
            cQuesterV2.status = "Picking up cat";
            if (Inventory.isFull()) {
                RSItem[] salmon = Inventory.find(SALMON);
                if (salmon.length > 0 && salmon[0].click("Eat")) {
                    Timer.waitCondition(() -> !Inventory.isFull(), 2500, 3500);
                }
            }
            if (npcCat.map(n -> n.interact("Pick-up")).orElse(false))
                Timer.waitCondition(() -> Inventory.find(CAT_NAMES).length > 0, 3500, 45000);
        }
    }

    int SPICED_STEW_ID = 7479;

    public boolean useSpiceOnStew(int[] spiceId) {
        RSItem[] spicedStew = Inventory.find(SPICED_STEW_ID);
        Optional<InventoryItem> spice = Query.inventory()
                .idEquals(spiceId).findClosestToMouse();
        Optional<InventoryItem> stew = Query.inventory()
                .idEquals(ItemID.STEW).findClosestToMouse();
        Log.info("Using spice on stew");
        if (spicedStew.length == 0 &&
                stew.map(st -> spice.map(sp -> sp.useOn(st))
                        .orElse(false)).orElse(false)) {
           return  Timer.waitCondition(() -> Inventory.find(SPICED_STEW_ID).length > 0, 3000);

        }
        return false;
    }

    public void useSpiceOnSpicedStew(int[] spiceId) {
        Optional<InventoryItem> spice = Query.inventory()
                .idEquals(spiceId).findClosestToMouse();
        Optional<InventoryItem> stew = Query.inventory()
                .idEquals(ItemID.SPICY_STEW).findClosestToMouse();
        if (stew.map(st -> spice.map(sp -> sp.useOn(st))
                .orElse(false)).orElse(false)) {
            General.sleep(2000, 3000);
        }
    }

    public boolean useStewOnDave() {
        RSNPC[] dave = NPCs.findNearest("Evil Dave");
        RSItem[] spicedStew = Inventory.find(SPICED_STEW_ID);
        if (dave.length > 0 && spicedStew.length > 0) {
            if (dave[0].getPosition().distanceTo(Player.getPosition()) > 4) {
                PathingUtil.localNavigation(dave[0].getPosition());
                PathingUtil.movementIdle();
            }

            for (int i = 0; i < 3; i++) {
                if (!dave[0].isClickable())
                    DaxCamera.focus(dave[0]);

                if (Utils.useItemOnNPC(SPICED_STEW_ID, "Evil Dave"))
                    if (Timer.waitCondition(ChatScreen::isOpen, 7000, 9000))
                        return true;
            }
        }
        return false;
    }

    public void checkRedSpice() {
        cQuesterV2.status = "Checking Red spice";

        int num = checkSpice(RED_SPICES, RED_CHECK, "red");

        if (num != -1) {
            RED_SPICE_NUMBER = num;
            RED_CHECK = true;
        }
        /*if (Inventory.find(STEW).length > 0 && !RED_CHECK) {
            useSpiceOnStew(RED_SPICES); // used 1 spice on stew
            useStewOnDave();
            NPCInteraction.waitForConversationWindow();
            RSInterface meTalking = Interfaces.get(217, 4);
            if (meTalking != null) {
                meTalking.click();
                Timer.waitCondition(() -> Interfaces.get(231, 4) != null, 3000);
            }
            if (Interfaces.get(231, 4).getText().contains("All the spices are<br>wrong.")) {
                Log.info("" + Interfaces.get(231, 4).getText());
                Log.info("Red spice number was wrong");
                if (Inventory.find(STEW).length > 0) {
                    useSpiceOnStew(RED_SPICES);
                    useSpiceOnSpicedStew(RED_SPICES); // add 2 red spices to stew
                    useStewOnDave();
                    NPCInteraction.waitForConversationWindow();
                    Interfaces.get(217, 3).click();
                    Timer.waitCondition(() -> Interfaces.get(231, 4) != null, 3000);
                    if (Interfaces.get(231, 4) != null) {
                        if (Interfaces.get(231, 4).getText().contains("All the spices are<br>wrong.")) {
                            RED_SPICE_NUMBER = 3;
                            Log.info("Red spice number: " + RED_SPICE_NUMBER);
                            RED_CHECK = true;
                        } else {
                            RED_SPICE_NUMBER = 2;
                            Log.info("Red spice number: " + RED_SPICE_NUMBER);
                            RED_CHECK = true;
                        }
                    }
                } else if (Interfaces.get(231, 4).getText().contains("I think you've got")) {
                    RED_SPICE_NUMBER = 1;
                    Log.info("Red spice number: " + RED_SPICE_NUMBER);
                    RED_CHECK = true;
                }
            }
        }*/
    }


    public Optional<RSInterface> getClickToContinueInterfaceMe() {
        RSInterface parent = Interfaces.get(217);
        if (parent != null) {
            RSInterface[] children = parent.getChildren();
            return Arrays.stream(children).filter(c ->
                            General.stripFormatting(c.getText()).contains("click here to continue"))
                    .findFirst();
        }
        return Optional.empty();

    }

    public Optional<RSInterface> getClickToContinueInterfaceNpc() {
        RSInterface npcTalkingParent = Interfaces.get(231);
        if (npcTalkingParent != null) {
            RSInterface[] children = npcTalkingParent.getChildren();
            return Arrays.stream(children).filter(c ->
                            General.stripFormatting(c.getText()).contains("click here to continue"))
                    .findFirst();
        }
        return Optional.empty();

    }

    /**
     * clicks "Click to continue" in chat when I am talking
     *
     * @return
     */
    public boolean clickContinueChatMe() {
        Optional<RSInterface> button = getClickToContinueInterfaceMe();
        if (button.isPresent() && button.get().click()) {
            Log.info("clicked optional");
            Timer.waitCondition(() -> !getClickToContinueInterfaceMe().isPresent(), 3500, 5000);
        }


        RSInterface parent = Interfaces.get(217);
        if (parent != null) {
            RSInterface[] children = parent.getChildren();
            for (RSInterface c : children) {
                String str = General.stripFormatting(c.getText());
                if (str.toLowerCase().contains("click here to continue")) {

                }
            }
        }
        if (ChatScreen.clickContinue())
            return (Timer.waitCondition(() -> Interfaces.get(231) != null, 5000, 7000));


        return false;
    }

    public boolean checkSpiceChat(int spiceNum) {
        if (NPCInteraction.isConversationWindowUp()) {
            if (clickContinueChatMe()) {
                Timer.waitCondition(() -> Interfaces.get(231) != null, 3000);
                Log.info("Clicked continue chat me");
                Optional<String> message = ChatScreen.getMessage();
                if (message.isPresent() &&
                        General.stripFormatting(message.get()).contains("All the spices are wrong.")) {
                    Log.info("" + message.get());
                    Log.info("Spice number was wrong");
                    return false;
                }
            }
            Optional<String> message = ChatScreen.getMessage();
            if (message.isPresent() &&
                    General.stripFormatting(message.get()).contains("I think you've got")) {
                Log.info("Spice number: " + spiceNum);
                return true;
            }
        }
        return false;
    }


    public void checkOrangeSpice() {
        cQuesterV2.status = "Checking Orange spice";
        int num = checkSpice(ORANGE_SPICES, ORANGE_CHECK, "orange");

        if (num != -1) {
            ORANGE_SPICE_NUMBER = num;
            ORANGE_CHECK = true;
        }
    }

    public int checkSpice(int[] spiceArray, boolean spiceCheck, String spiceColor) {
        if (Inventory.find(STEW).length > 0 && !spiceCheck) {
            Log.info("Using spice on stew x1");
            useSpiceOnStew(spiceArray);
            if (!useStewOnDave())
                return -1;

            if (checkSpiceChat(1)) {
                Log.info("Spice number is 1 for " + spiceColor + " spice");
                return 1;
            } else {
                useSpiceOnStew(spiceArray);
                useSpiceOnSpicedStew(spiceArray);
                if (useStewOnDave()) {
                    if (checkSpiceChat(2)) {
                        Log.info("Spice number is 2");
                        return 2;
                    } else {
                        Log.info("Spice number is 3");
                        return 3;
                    }

                }
            }
        }
        return -1;
    }


    public void checkYellowSpice() {
        if (!YELLOW_CHECK) {
            cQuesterV2.status = "Checking Yellow spice";
            int num = checkSpice(YELLOW_SPICES, YELLOW_CHECK, "yellow");

            if (num != -1) {
                Log.info("Yellow spice number is " + num);
                YELLOW_SPICE_NUMBER = num;
                YELLOW_CHECK = true;
            }
        }
    }

    public void checkBrownSpice() {
        cQuesterV2.status = "Checking brown spice";
        int num = checkSpice(BROWN_SPICES, BROWN_CHECK, "brown");

        if (num != -1) {
            Log.info("Brown spice number is " + num);
            BROWN_SPICE_NUMBER = num;
            BROWN_CHECK = true;
        }

    }

    public void makeFinalStew() {
        for (int b =0; b< 3; b++) {
            for (int i = 0; i < getRedNeeded(); i++) {
                if(!useSpiceOnStew(RED_SPICES)){
                    useSpiceOnSpicedStew(RED_SPICES);
                }
                General.sleep(800, 1200);
            }
            for (int i = 0; i < getYellowNeeded(); i++) {
                useSpiceOnSpicedStew(YELLOW_SPICES);
                General.sleep(800, 1200);
            }
            for (int i = 0; i < getOrangeNeeded(); i++) {
                useSpiceOnSpicedStew(ORANGE_SPICES);
                General.sleep(800, 1200);
            }
            for (int i = 0; i < getBrownNeeded(); i++) {
                useSpiceOnSpicedStew(BROWN_SPICES);
                General.sleep(800, 1200);
            }
        }
        useStewOnDave();
        if (NpcChat.waitForChatScreen() &&
                ChatScreen.getName().map(n->!n.contains("Evil Dave")).orElse(false) &&
                //Interfaces.get(231) == null &&
                ChatScreen.clickContinue()) {
            Timer.waitCondition(() -> Interfaces.get(231) != null, 3000);
        }
        if (ChatScreen.getMessage().map(m -> m.contains("EVIL")).orElse(false)) {
            HAS_FINAL_STEW = true;
            Log.warn("Has final stew");
            NPCInteraction.handleConversation();
        }
    }


    int EVIL_DAVE_OBJECT_ID = 12341;

    public void finishQuest() {
        if (Objects.find(10, 12341).length == 0)
            goToStart();
        cQuesterV2.status = "Saving Evil Dave (finishing)";
        if (Utils.useItemOnObject(7479, 12341)) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }
    }

    public Optional<Npc> getFollowerRSNPC() {
        return Query.npcs().actionContains("Chase")
                .actionContains("Interact")
                .sortedByDistance()
                .isInteractingWithMe()
                .findClosest();

       /* if (kitten != null && kitten.length > 0) {
            return kitten[0];
        }

        return null;*/
    }

    public int getRedNeeded() {
        int redNeeded = GameState.getVarbit(1883);
        int redInStew = GameState.getVarbit(Varbits.SPICY_STEW_RED_SPICES.getId());
        int numRedStillNeeded = redNeeded - redInStew;
        Log.info("Red Needed: " + numRedStillNeeded);
        return numRedStillNeeded;
    }

    public int getYellowNeeded() {
        int yellowNeeded = GameState.getVarbit(1884);
        int yellowInStew = GameState.getVarbit(Varbits.SPICY_STEW_YELLOW_SPICES.getId());
        int numYellowStillNeeded = yellowNeeded - yellowInStew;
        Log.info("Yellow Needed: " + numYellowStillNeeded);
        return numYellowStillNeeded;
    }

    public int getBrownNeeded() {
        int brownNeeded = GameState.getVarbit(1885);
        int brownInStew = GameState.getVarbit(Varbits.SPICY_STEW_BROWN_SPICES.getId());
        int numBrownStillNeeded = brownNeeded - brownInStew;
        Log.info("Brown Needed: " + numBrownStillNeeded);
        return numBrownStillNeeded;
    }

    public int getOrangeNeeded() {
        int orangeInStew = GameState.getVarbit(Varbits.SPICY_STEW_ORANGE_SPICES.getId());
        int orangeNeeded = GameState.getVarbit(1886);
        int numOrangeStillNeeded = orangeNeeded - orangeInStew;
        Log.info("Orange Needed: " + numOrangeStillNeeded);
        return numOrangeStillNeeded;
    }

    private boolean hasEnoughRedSpice() {
        return Query.inventory().nameContains("Red spice").count() >=
                getRedNeeded();
    }

    private boolean hasEnoughYellowSpice() {
        return Query.inventory().nameContains("Yellow spice").count() >=
                getYellowNeeded();
    }

    private boolean hasEnoughBrownSpice() {
        return Query.inventory().nameContains("Brown spice").count() >=
                getBrownNeeded();
    }

    private boolean hasEnoughOrangeSpice() {
        return Query.inventory().nameContains("Orange spice").count() >=
                getOrangeNeeded();
    }

    private static RfdEvilDave quest;

    public static RfdEvilDave get() {
        return quest == null ? quest = new RfdEvilDave() : quest;
    }

    @Override
    public void execute() {

        RSInterface inter = Interfaces.findWhereAction("Continue", 217);
        if (inter != null) {
            General.println("NOT NULL");
            inter.click();
        }


        if (Utils.getVarBitValue(1878) == 0) {
            buyItems();
            getItems();
            goToStart();
            inspectDave();
        }
        if (RSVarBit.get(1878).getValue() == 1 && RSVarBit.get(1888).getValue() == 0) {// +3,952,640 dif b/w the two here
            step2(); // VarBit 1888 changes from 0->1 after talking to doris, and then 1878 changes from 1-> after talking to dave
            step2b();
        }
        if (Utils.getVarBitValue(1878) == 1 && Utils.getVarBitValue(1888) == 1) {

        }
        if (RSVarBit.get(1878).getValue() == 2) {
            TalkToDoris2(); // varbit 1878 changes from 2->3 here
        }
        if (RSVarBit.get(1878).getValue() == 3) {

            if (EVIL_DAVES_UNDERGROUND.contains(Player.getPosition())) { // -1,605,928,957
                if (hasEnoughRedSpice() && hasEnoughYellowSpice() &&
                        hasEnoughOrangeSpice() && hasEnoughBrownSpice() &&
                        !HAS_FINAL_STEW) {
                    Log.info("Making final stew");
                    makeFinalStew();
                    NPCInteraction.handleConversation();
                    pickupCat();
                } else {
                    getSpices();
                    moveArea();
                    checkFeeding();
                    checkInteraction();
                    dropEmptyShakers();
                }
              /* if (checkSpiceNumber(RED_SPICES) && !HAS_FINAL_STEW && !RED_CHECK) {
                    checkRedSpice();
                }
                if (checkSpiceNumber(YELLOW_SPICES) && !HAS_FINAL_STEW && !YELLOW_CHECK) {
                    checkYellowSpice();
                }
                if (checkSpiceNumber(ORANGE_SPICES) && !HAS_FINAL_STEW && !ORANGE_CHECK) {
                    Log.info("Checking orange spice");
                    checkOrangeSpice();
                }
                if (checkSpiceNumber(BROWN_SPICES) && !HAS_FINAL_STEW && !BROWN_CHECK) {
                    checkBrownSpice();
                }*/

            } else {
                goDownTrapDoor(); //goes to basement
            }
        }
        if (RSVarBit.get(1878).getValue() == 4) {
            finishQuest();

        }
        if (RSVarBit.get(1878).getValue() == 5) {
            Utils.closeQuestCompletionWindow();
            cQuesterV2.taskList.remove(RfdEvilDave.get());

        }
    }


    @Override
    public String toString() {
        String spice = "R(" + RED_SPICE_NUMBER + ") || Y(" + YELLOW_SPICE_NUMBER + ") || B(" + BROWN_SPICE_NUMBER +
                ") || O(" + ORANGE_SPICE_NUMBER + ")";
        return spice;
    }


    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return cQuesterV2.taskList.get(0).equals(RfdEvilDave.get());
    }

    @Override
    public String questName() {
        return "RFD Evil Dave: R(" + GameState.getVarbit(1883) + ") || Y(" + GameState.getVarbit(1884) +
                ") || B(" + GameState.getVarbit(1885)+
                ") || O(" +GameState.getVarbit(1886) + ")";
    }

    @Override
    public boolean checkRequirements() {
        return false;
    }

    @Override
    public java.util.List<Requirement> getGeneralRequirements() {
        return null;
    }

    @Override
    public java.util.List<ItemRequirement> getBuyList() {
        return null;
    }

    @Override
    public boolean isComplete() {
        return Quest.LUNAR_DIPLOMACY.getState().equals(Quest.State.COMPLETE);
    }
}
