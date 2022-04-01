package scripts.QuestPackages.MurderMystery;

import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.Objects;
import org.tribot.api2007.ext.Doors;
import org.tribot.api2007.types.*;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.MyPlayer;
import org.tribot.script.sdk.Quest;
import org.tribot.script.sdk.Waiting;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.KnightsSword.KnightsSword;
import scripts.QuestSteps.*;
import scripts.Requirements.ItemReq;
import scripts.Requirements.ItemRequirement;
import scripts.Requirements.Items.ItemRequirements;
import scripts.Requirements.Requirement;
import scripts.Requirements.Util.ConditionalStep;
import scripts.Requirements.Util.Conditions;
import scripts.Requirements.Util.LogicType;
import scripts.Tasks.Priority;
import scripts.Timer;

import java.util.*;

public class MurderMystery implements QuestTask {


    private static MurderMystery quest;

    public static MurderMystery get() {
        return quest == null ? quest = new MurderMystery() : quest;
    }


    int dagger = 1813;
    int PUNGENT_POT = 1812;
    int[] CRIMINALS_THREAD = {1808, 1809, 1810}; // red/green/blue
    int SILVER_NECKLACE = 1796;
    int SILVER_BOTTLE = 1800;
    int SILVER_BOOK = 1802;
    int SILVER_NEEDLE = 1804;
    int SILVER_POT = 1806;
    int SILVER_CUP = 1798;
    int FLY_PAPER = 1811;
    int POT_OF_FLOUR = 1933;
    int FLOURED_DAGGER = 1814;
    int FLOURED_CUP = 1799;
    int UNKNOWN_PRINT_NECKLACE = 1816;
    int UNKNOWN_PRINT_POT = 1821;
    int UNKNOWN_PRINT_BOOK = 1819;
    int UNKNOWN_PRINT_BOTTLE = 1818;
    int UNKNOWN_PRINT_NEEDLE = 1820;
    int UNKNOWN_PRINT_DAGGER = 1822;
    int UNKNOWN_PRINT_CUP = 1817;
    int[] UNKNOWN_PRINT = {
            UNKNOWN_PRINT_NECKLACE,
            UNKNOWN_PRINT_POT,
            UNKNOWN_PRINT_BOOK,
            UNKNOWN_PRINT_BOTTLE,
            UNKNOWN_PRINT_NEEDLE,
            UNKNOWN_PRINT_DAGGER
    };

    int FLOURED_BOOK = 1803;
    int FLOURED_BOTTLE = 1801;
    int FLOURED_NEEDLE = 1805;
    int FLOURED_NECKLACE = 1797;
    int FLOURED_POT = 1807;
    int CAROLS_PRINT = 1818;
    int ELIZABETHS_PRINT = 1820;
    int DAVIDS_PRINT = 1819;
    int FRANKS_PRINTS = 1821;
    int ANNAS_PRINTS = 1816;
    int BOBS_PRINTS = 1817;

    boolean ANNA = false;
    boolean BOB = false;
    boolean FRANK = false;
    boolean DAVID = false;
    boolean CAROL = false;
    boolean ELIZABETH = false;

    RSArea QUEST_START = new RSArea(new RSTile(2741, 3565, 0), new RSTile(2743, 3556, 0));
    RSArea SEERS_BAR = new RSArea(new RSTile(2700, 3488, 0), new RSTile(2689, 3498, 0));
    //RSArea MURDER_ROOM = new RSArea(new RSTile(2747, 3577, 0), new RSTile(2745, 3579, 0));
    RSArea MURDER_ROOM = new RSArea(new RSTile(2747, 3577, 0), new RSTile(2745, 3578, 0));
    RSArea FIRST_FLOOR_BEDROOM_AREA = new RSArea(new RSTile(2735, 3574, 0), new RSTile(2733, 3576, 0));
    RSArea SECOND_BEDROOM_AREA = new RSArea(new RSTile(2735, 3580, 1), new RSTile(2733, 3582, 1)); // NW, 2nd floor
    RSArea THIRD_BEDROOM_AREA = new RSArea(new RSTile(2735, 3577, 1), new RSTile(2733, 3579, 1));
    RSArea FOURTH_BEDROOM_AREA = new RSArea(new RSTile(2745, 3582, 1), new RSTile(2747, 3580, 1));
    RSArea FIFTH_BEDROOM_AREA = new RSArea(new RSTile(2747, 3577, 1), new RSTile(2745, 3579, 1));
    RSArea GUARD_DOG_AREA = new RSArea(new RSTile(2748, 3577, 0), new RSTile(2751, 3576, 0));
    RSArea GARDNERS_HOUSE = new RSArea(new RSTile(2730, 3582, 0), new RSTile(2732, 3580, 0));
    RSArea KITCHEN_AREA = new RSArea(new RSTile(2735, 3580, 0), new RSTile(2733, 3582, 0));
    RSArea GOSSIP_AREA = new RSArea(new RSTile(2739, 3555, 0), new RSTile(2744, 3552, 0));
    RSArea FOUNTAIN_AREA = new RSArea(new RSTile(2745, 3565, 0), new RSTile(2750, 3560, 0));
    RSArea BOB_AREA = new RSArea(new RSTile(2745, 3562, 0), new RSTile(2750, 3557, 0));
    RSArea DRAIN_AREA = new RSArea(new RSTile(2738, 3573, 0), new RSTile(2732, 3571, 0));
    RSArea DINING_AREA = new RSArea(new RSTile(2738, 3582, 0), new RSTile(2744, 3576, 0));

    RSTile FIRST_BEDROOM_BEFORE = new RSTile(734, 3575, 0);
    RSTile FIRST_BEDROOM = new RSTile(2734, 3575, 0);
    RSTile SECOND_BEDROOM = new RSTile(2734, 3581, 1);
    RSTile THIRD_BEDROOM = new RSTile(2734, 3578, 1);
    RSTile FOURTH_BEDROOM = new RSTile(2746, 3581, 1);
    RSTile FIFTH_BEDROOM = new RSTile(2746, 3578, 1);
    RSTile KITCHEN = new RSTile(2734, 3581, 0);
    RSTile KITCHEN_OUTSIDE = new RSTile(2737, 3580, 0);
    RSTile GARDNER_TILE = new RSTile(2731, 3581, 0);

    RSObject[] barrel;


    ItemRequirement pot = new ItemRequirement("Pot", ItemID.POT);
    ItemRequirement pot3 = new ItemRequirement("Pot", ItemID.POT, 3);
    ItemRequirement pungentPot = new ItemRequirement("Pungent pot", ItemID.PUNGENT_POT);

    ItemRequirement criminalsDaggerAny = new ItemRequirement(ItemID.CRIMINALS_DAGGER);


    ItemRequirement criminalsDagger = new ItemRequirement(ItemID.CRIMINALS_DAGGER);
    ItemRequirement criminalsDaggerHighlighted = new ItemRequirement(ItemID.CRIMINALS_DAGGER);

    ItemRequirement criminalsDaggerFlour = new ItemRequirement(ItemID.CRIMINALS_DAGGER_1814);
    ItemRequirement criminalsDaggerFlourHighlighted = new ItemRequirement(ItemID.CRIMINALS_DAGGER_1814);


    ItemRequirement criminalsThread = new ItemRequirement( ItemID.CRIMINALS_THREAD);
    ItemRequirement criminalsThread1 = new ItemRequirement( ItemID.CRIMINALS_THREAD);
    ItemRequirement criminalsThread2 = new ItemRequirement( ItemID.CRIMINALS_THREAD_1809);
    ItemRequirement criminalsThread3 = new ItemRequirement( ItemID.CRIMINALS_THREAD_1810);
    ItemRequirement threeFlypaper = new ItemRequirement("Flypaper", ItemID.FLYPAPER, 3);
    ItemRequirement flypaper = new ItemRequirement("Flypaper", ItemID.FLYPAPER);

    ItemRequirement potOfFlourHighlighted = new ItemRequirement("Pot of flour", ItemID.POT_OF_FLOUR);

    ItemRequirement unknownPrint = new ItemRequirement("Unknown print", ItemID.UNKNOWN_PRINT);

    /* Thread 1 items */
    ItemRequirement bobPrint = new ItemRequirement("Bob's print", ItemID.BOBS_PRINT);
    ItemRequirement carolPrint = new ItemRequirement("Carol's print", ItemID.CAROLS_PRINT);
    ItemRequirement silverCup = new ItemRequirement("Silver cup", ItemID.SILVER_CUP);
    ItemRequirement silverCupFlour = new ItemRequirement("Silver cup", ItemID.SILVER_CUP_1799);
    ItemRequirement silverBottle = new ItemRequirement("Silver bottle", ItemID.SILVER_BOTTLE);
    ItemRequirement silverBottleFlour = new ItemRequirement("Silver bottle", ItemID.SILVER_BOTTLE_1801);

    /* Thread 2 items */
    ItemRequirement annasPrint = new ItemRequirement("Anna's print", ItemID.ANNAS_PRINT);
    ItemRequirement davidsPrint = new ItemRequirement("David's print", ItemID.DAVIDS_PRINT);
    ItemRequirement silverNecklace = new ItemRequirement("Silver necklace", ItemID.SILVER_NECKLACE);
    ItemRequirement silverNecklaceFlour = new ItemRequirement("Silver necklace", ItemID.SILVER_NECKLACE_1797);
    ItemRequirement silverBook = new ItemRequirement("Silver book", ItemID.SILVER_BOOK);
    ItemRequirement silverBookFlour = new ItemRequirement("Silver book", ItemID.SILVER_BOOK_1803);

    /* Thread 3 items */
    ItemRequirement elizabethPrint = new ItemRequirement("Elizabeth's print", ItemID.ELIZABETHS_PRINT);
    ItemRequirement frankPrint = new ItemRequirement("Frank's print", ItemID.FRANKS_PRINT);
    ItemRequirement silverNeedle = new ItemRequirement("Silver needle", ItemID.SILVER_NEEDLE);
    ItemRequirement silverNeedleFlour = new ItemRequirement("Silver needle", ItemID.SILVER_NEEDLE_1805);
    ItemRequirement silverPot = new ItemRequirement("Silver needle", ItemID.SILVER_POT);
    ItemRequirement silverPotFlour = new ItemRequirement("Silver needle", ItemID.SILVER_POT_1807);

    ItemRequirement killersPrint = new ItemRequirement("Killer's print", ItemID.KILLERS_PRINT);


    ItemRequirements hasAnyThread1Item = new ItemRequirements(LogicType.OR,
            silverCupFlour, silverCup, silverBottleFlour, silverBottle, bobPrint, carolPrint);
    ItemRequirements hasAnyThread2Item = new ItemRequirements(LogicType.OR, silverBookFlour, silverBook,
            silverNecklaceFlour, silverNecklace, annasPrint, davidsPrint);
    ItemRequirements hasAnyThread3Item = new ItemRequirements(LogicType.OR, silverNeedleFlour, silverNeedle,
            silverPotFlour, silverPot, elizabethPrint, frankPrint);

    //    heardAboutPoisonSalesman = new Conditions(true, new WidgetTextRequirement(WidgetInfo.DIALOG_NPC_TEXT, "Especially as I heard that the poison salesman in the<br>Seers' village made a big sale to one of the family the<br>other day."));
    //     talkedToPoisonSalesman = new Conditions(true, new WidgetTextRequirement(WidgetInfo.DIALOG_PLAYER_TEXT, "Uh... no, it's ok."));

    // Option 1 (red thread)
    ObjectStep searchBobsBarrel = new ObjectStep(ObjectID.BOBS_BARREL, new RSTile(2735, 3579, 0),
            "Search");
    ObjectStep searchCarolsBarrel = new ObjectStep(ObjectID.CAROLS_BARREL, new RSTile(2733, 3580, 1),
            "Search");

    // Option 2 (green thread)
    ObjectStep searchAnnasBarrel = new ObjectStep(ObjectID.ANNAS_BARREL, new RSTile(2733, 3575, 0),
            "Search");
    ObjectStep searchDavidsBarrel = new ObjectStep(ObjectID.DAVIDS_BARREL, new RSTile(2733, 3577, 1),
            "Search");

    // Option 3 (blue thread)
    ObjectStep searchFranksBarrel = new ObjectStep(ObjectID.FRANKS_BARREL, new RSTile(2747, 3577, 1),
            "Search");
    ObjectStep searchElizabethsBarrel = new ObjectStep(ObjectID.ELIZABETHS_BARREL, new RSTile(2746, 3581, 1),
            "Search");

    public Map<Integer, QuestStep> loadSteps() {

        Map<Integer, QuestStep> steps = new HashMap<>();

        //  steps.put(0, talkToGuard);

		/*
		  Starting quest, 195 0->1
		  173 0->1, may be unrelated. Had it at 1 for flypaper on dagger
		*/

        // Thread2, get Anna or David's items

        //  ConditionalStep investigating = new ConditionalStep(collectThreeFlypaper);

        // investigating.addStep(new Conditions(killersPrint), new SolvingTheCrimeStep(remainingSteps));

        /* compare prints */
        // investigating.addStep(new Conditions(pungentPot, criminalsThread2, unknownPrint, hasAnyThread2Item), getAndComparePrintsOfNecklaceOrBook);
        // investigating.addStep(new Conditions(pungentPot, criminalsThread3, unknownPrint, hasAnyThread3Item), getAndComparePrintsOfNeeedleOrPot);


        /*investigating.addStep(new Conditions(pungentPot, criminalsDagger, criminalsThread1, silverCup, silverBottle),
                fillPotWithFlour);
        investigating.addStep(new Conditions(pungentPot, criminalsDagger, criminalsThread2, silverNecklace, silverBook),
                fillPotWithFlour);
        investigating.addStep(new Conditions(pungentPot, criminalsDagger, criminalsThread3, silverNeedle, silverPot),
                fillPotWithFlour);*/


        // steps.put(1, investigating);

        // 195 0->4
        // Green thread
        // David responsible

        // 195 0->5
        // Blue thread
        // Elizabeth is responsible

        return steps;
    }

    public void setupSteps() {
        NPCStep talkToGuard = new NPCStep(4218, new RSTile(2741, 3561, 0),
                "Talk to the Guard in the Sinclair Manor north of Camelot.");
        talkToGuard.addDialogStep("Yes.", "Sure, I'll help.", "Yes.");

        GroundItemStep pickUpPungentPot = new GroundItemStep(pungentPot.getId(), new RSTile(2747, 3579, 0));
        GroundItemStep pickUpDagger = new GroundItemStep(criminalsDaggerAny.getId(), new RSTile(2746, 3578, 0));
        // searchWindowForThread = new ObjectStep(NullObjectID.NULL_26123, new RSTile(2748, 3577, 0), "Search the window for a clothing thread. The colour of the thread will match the killer's trousers.", criminalsThread);
        //  fillPotWithFlour = new ObjectStep(ObjectID.BARREL_OF_FLOUR_26122, new RSTile(2733, 3582, 0), "Fill your pot with flour from the barrel in the mansion's kitchen.", pot);
        //   useFlourOnDagger = new DetailedQuestStep("Use the pot of flour on the Criminal's dagger.", potOfFlourHighlighted, criminalsDaggerHighlighted);

        ObjectStep collectThreeFlypaper = new ObjectStep(2663, new RSTile(2731, 3582, 0),
                "Investigate");
        collectThreeFlypaper.addDialogStep("Yes, it might be useful.");




      /*  getSilverItems = new DetailedQuestStep("Search the barrel of both the potential suspects for their respective silver items.");
        getSilverItems.addSubSteps(searchAnnasBarrel, searchDavidsBarrel, searchElizabethsBarrel, searchFranksBarrel);

        useFlypaperOnDagger = new DetailedQuestStep("Use the flypaper on the dagger.", flypaper, criminalsDaggerFlourHighlighted);




        compareSilverToMurdererPrint = new DetailedQuestStep("Use flour on both the silver items, then use flypaper on them. Use the prints you get on the Murderer's print to identify the murderer.");
        compareSilverToMurdererPrint.addSubSteps(getAndComparePrintsOfNecklaceOrBook);


        talkToGossip = new NPCStep(NpcID.GOSSIP, new RSTile(2741, 3557, 0), "Talk to Gossip, just south of the Sinclair Mansion.");
        talkToGossip.addDialogStep(2, "Who do you think was responsible?");

        talkToPoisonSalesman = new NPCStep(NpcID.POISON_SALESMAN, new RSTile(2694, 3493, 0), "Talk to the " +
                "Poison Salesman in the Seers' Village pub.");
        talkToPoisonSalesman.addDialogStep("Who did you sell Poison to at the house?");
        talkToPoisonSalesman.addDialogStep("Talk about the Murder Mystery Quest");
        talkToTheSuspect = new DetailedQuestStep("Talk to the person who you matched prints to and ask what they did with the poison.");

        disproveSuspectStory = new DetailedQuestStep("Search the item they say they used the poison on.");
        disproveSuspectStory.addDialogStep("Why'd you buy poison the other day?");

        finishQuest = new DetailedQuestStep("Return to the guard outside the Sinclair Mansion and tell him your findings.");

        remainingSteps = new DetailedQuestStep("Follow the steps in the Quest Helper sidebar for the rest of the quest.");
        remainingSteps.addDialogStep(2, "Who do you think was responsible?");
        remainingSteps.addDialogStep("Why'd you buy poison the other day?");
        remainingSteps.addDialogStep("Who did you sell Poison to at the house?");
        remainingSteps.addDialogStep("I know who did it!");
        remainingSteps.setShowInSidebar(false);*/
    }

    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.CAMELOT_TELEPORT, 5, 50),
                    new GEItem(ItemID.POT, 1, 500),
                    new GEItem(ItemID.AMULET_OF_GLORY[2], 1, 20),
                    new GEItem(ItemID.STAMINA_POTION[0], 2, 15),
                    new GEItem(ItemID.RING_OF_WEALTH[0], 1, 25)
            )
    );


    BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);

    public void buyItems() {
        cQuesterV2.status = "Buying Items";
        General.println("[Debug]: Buying Items");
        buyStep.buyItems();
    }

    public void getItems() {
        cQuesterV2.status = " Getting Items";
        General.println("[Debug]: Getting Items");
        BankManager.open(true);
        BankManager.depositEquipment();
        BankManager.checkEquippedGlory();
        BankManager.depositAll(true);
        BankManager.withdraw(5, true, ItemID.CAMELOT_TELEPORT);
        BankManager.withdraw(1, true, ItemID.POT);
        BankManager.withdraw(2, true, ItemID.STAMINA_POTION[0]);
        BankManager.close(true);

    }


    public void startQuest() {
        cQuesterV2.status = "Starting Quest";
        PathingUtil.walkToArea(QUEST_START);
        if (NpcChat.talkToNPC("Guard")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Sure, I'll help.");
            NPCInteraction.handleConversation();
        }
    }

    public void getDagger() {
        cQuesterV2.status = "Taking dagger";
        PathingUtil.walkToArea(MURDER_ROOM);

        if (Inventory.find(dagger).length == 0 && Utils.clickGroundItem(dagger)) {

            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            Timer.waitCondition(() -> Inventory.find(dagger).length > 0, 1500);
        }
    }

    public void getPungentPot() {
        cQuesterV2.status = "Taking Pot";
        PathingUtil.walkToArea(MURDER_ROOM);
        if (Inventory.find(PUNGENT_POT).length == 0 && Utils.clickGroundItem(PUNGENT_POT)) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            Timer.waitCondition(() -> Inventory.find(PUNGENT_POT).length > 0, 1500);
        }
    }

    public void searchSmashedWindow() {
        PathingUtil.walkToArea(MURDER_ROOM);
        RSObject[] smashedWindow = Objects.findNearest(20, 26123);
        if (smashedWindow.length > 0 && Inventory.find(CRIMINALS_THREAD).length < 1) {
            cQuesterV2.status = "Investigating Window";
            if (Utils.clickObj(26123, "Investigate")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                Timer.waitCondition(() -> Inventory.find(CRIMINALS_THREAD).length > 0, 1500);
            }
        }
    }

    public void step1() {
        cQuesterV2.status = "Going to murder room";
        getDagger();
        getPungentPot();
        searchSmashedWindow();
    }

    public void bedroom1() {
        cQuesterV2.status = "Investigating first bedroom";
        if (Inventory.find(SILVER_NECKLACE).length < 1) {
            if (PathingUtil.walkToTile(new RSTile(2734, 3575, 0)))
                PathingUtil.movementIdle();
            if (Utils.clickObj(2656, "Search")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
        }
    }

    public void bedroom2() {
        cQuesterV2.status = "Investigating Second bedroom";
        if (Inventory.find(SILVER_BOTTLE).length < 1) {
            PathingUtil.walkToTile(new RSTile(2734, 3578, 0));

            if (Utils.clickObj(2657, "Search")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }

        }
    }

    public void bedroom3() {
        if (Inventory.find(SILVER_BOOK).length < 1) {
            cQuesterV2.status = "Investigating Third bedroom";
            PathingUtil.walkToArea(THIRD_BEDROOM_AREA);
            if (Utils.clickObj(2659, "Search")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();

            }
        }
    }

    public void bedroom4() {
        if (Inventory.find(SILVER_NEEDLE).length < 1) {
            cQuesterV2.status = "Investigating Fourth bedroom";
            PathingUtil.walkToArea(FOURTH_BEDROOM_AREA);
            if (Utils.clickObj(2660, "Search")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();

            }
        }
    }

    public void bedroom5() {
        if (Inventory.find(SILVER_POT).length < 1) {
            cQuesterV2.status = "Investigating Fifth bedroom";
            if (PathingUtil.walkToTile(new RSTile(2746, 3578, 0)))
                PathingUtil.movementIdle();
            if (Utils.clickObj(2661, "Search")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
        }
    }

    public void step2() {
        if (Inventory.find(UNKNOWN_PRINT).length < 5) {
            cQuesterV2.status = "Investigating Dog gate";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(GUARD_DOG_AREA);
            RSObject[] gate = Objects.findNearest(20, 2664);
            if (Utils.clickObj(2664, "Investigate")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
        }
    }

    public boolean hasAllBedroomItems() {
        return Inventory.find(dagger, PUNGENT_POT, CRIMINALS_THREAD[0], CRIMINALS_THREAD[1], CRIMINALS_THREAD[2],
                SILVER_NECKLACE, SILVER_BOOK, SILVER_BOTTLE, SILVER_NEEDLE, SILVER_POT).length >= 7;
    }

    public void getFlyPaper() {
        if (Inventory.find(FLY_PAPER).length < 3) {
            cQuesterV2.status = "Investigating Gardner's House";
            General.println("[Debug]: " + cQuesterV2.status);
            if (!GARDNERS_HOUSE.contains(Player.getPosition())) {
                PathingUtil.walkToTile(new RSTile(2731, 3578));
                if (PathingUtil.localNavigation(GARDNER_TILE)) {
                    Timer.waitCondition(() -> GARDNERS_HOUSE.contains(Player.getPosition()), 10000);
                } else if (PathingUtil.walkToTile(GARDNER_TILE))
                    Timer.waitCondition(() -> GARDNERS_HOUSE.contains(Player.getPosition()), 10000);


                RSObject[] door = Objects.find(20, 25716);
                if (door.length > 0) {
                    if (!door[0].isClickable())
                        door[0].adjustCameraTo();

                    if (Doors.handleDoor(door[0], true))
                        General.sleep(1500, 3000);
                }
            }
            if (GARDNERS_HOUSE.contains(Player.getPosition())) {
                cQuesterV2.status = "Getting Fly paper (x7)";
                General.println("[Debug]: " + cQuesterV2.status);

                for (int i = 0; i < 14; i++) {
                    if (Inventory.find(FLY_PAPER).length < 7) {
                        General.sleep(150, 600);
                        if (Utils.clickObj(2663, "Investigate")) { //sacks
                            NPCInteraction.waitForConversationWindow();
                            NPCInteraction.handleConversation("Yes, it might be useful.");
                        }
                    } else {
                        break;
                    }
                }
                if(PathingUtil.localNav(MyPlayer.getPosition().translate(0, -4)))
                    PathingUtil.movementIdle();

            }
        }
    }

    public void getFlour() {
        if (Inventory.find(POT_OF_FLOUR).length < 1 && Inventory.find(UNKNOWN_PRINT).length < 6) {
            cQuesterV2.status = "Getting flour";
            General.println("[Debug]: " + cQuesterV2.status);
            if (!KITCHEN_AREA.contains(Player.getPosition())) {
                if (PathingUtil.localNavigation(new RSTile(2734, 3581, 0)))
                    Timer.waitCondition(() -> KITCHEN_AREA.contains(Player.getPosition()), 10000);
                else if (PathingUtil.walkToTile(KITCHEN_OUTSIDE))
                    Timer.waitCondition(() -> KITCHEN_AREA.contains(Player.getPosition()), 10000);


                // if (Utils.handleDoor(new RSTile(2735, 3580,0), true))
                //    Timer.waitCondition(() -> Objects.find(10, 25719).length > 0, 5000);

            }
            if (KITCHEN_AREA.contains(Player.getPosition())) {
                General.sleep(100, 300);

                if (Utils.clickObj(26122, "Take-from"))
                    Timer.waitCondition(() -> Inventory.find(POT_OF_FLOUR).length > 0, 3500, 7000);
            }
        }
    }

    public void getDaggerPrint() {
        if (Inventory.find(UNKNOWN_PRINT_DAGGER).length < 1) {

            cQuesterV2.status = "Using flour on dagger";
            General.println("[Debug]: " + cQuesterV2.status);
            if (Utils.useItemOnItem(POT_OF_FLOUR, dagger))
                General.sleep(200, 600);

            cQuesterV2.status = "Using floured dagger on fly paper";
            General.println("[Debug]: " + cQuesterV2.status);
            if (Utils.useItemOnItem(FLOURED_DAGGER, FLY_PAPER))
                General.sleep(200, 600);

        }
    }

    public void useFlourOnItem(int item, int flouredItem, int printPaper) {
        if (!flypaper.check()) {
            getFlyPaper();
        }
        RSItem[] invPaper = Inventory.find(printPaper);
        if (invPaper.length == 0) {
            if (Inventory.find(POT_OF_FLOUR).length < 1) {
                getFlour();
            }
            if (Inventory.find(flouredItem).length < 1) {
                cQuesterV2.status = "Using Flour on Item";
                General.println("[Debug]: " + cQuesterV2.status);
                if (Inventory.find(POT_OF_FLOUR).length > 0 && Inventory.find(item).length > 0
                        && Inventory.find(FLY_PAPER).length > 0) {
                    if (Utils.useItemOnItem(POT_OF_FLOUR, item))
                        Timer.waitCondition(() -> Inventory.find(flouredItem).length > 0, 2500, 3500);

                }
            }
            if (Inventory.find(flouredItem).length > 0) {
                cQuesterV2.status = "Using Floured item on Paper";
                General.println("[Debug]: " + cQuesterV2.status);
                if (Utils.useItemOnItem(flouredItem, FLY_PAPER))
                    Timer.waitCondition(() -> Inventory.find(flouredItem).length == 0, 2000, 4000);
            }
        }
    }

    public void usePaperOnCupOrBottle() {
        useFlourOnItem(SILVER_BOTTLE, FLOURED_BOTTLE, UNKNOWN_PRINT_BOTTLE);
        useFlourOnItem(SILVER_CUP, FLOURED_CUP, UNKNOWN_PRINT_CUP);
    }

    public void usePaperOnNecklaceOrBook() {
        useFlourOnItem(SILVER_NECKLACE, FLOURED_NECKLACE, UNKNOWN_PRINT_NECKLACE);
        useFlourOnItem(SILVER_BOOK, FLOURED_BOOK, UNKNOWN_PRINT_BOOK);
    }

    public void usePaperOnNeedleOrPot() {
        useFlourOnItem(SILVER_NEEDLE, FLOURED_NEEDLE, UNKNOWN_PRINT_NEEDLE);
        useFlourOnItem(SILVER_POT, FLOURED_POT, UNKNOWN_PRINT_POT);
    }


    public void step6() {
        useFlourOnItem(SILVER_BOTTLE, FLOURED_BOTTLE, UNKNOWN_PRINT_BOTTLE);
        useFlourOnItem(SILVER_NEEDLE, FLOURED_NEEDLE, UNKNOWN_PRINT_NEEDLE);
        useFlourOnItem(SILVER_BOOK, FLOURED_BOOK, UNKNOWN_PRINT_BOOK);
        useFlourOnItem(SILVER_NECKLACE, FLOURED_NECKLACE, UNKNOWN_PRINT_NECKLACE);
        useFlourOnItem(SILVER_POT, FLOURED_POT, UNKNOWN_PRINT_POT);
        useFlourOnItem(SILVER_CUP, FLOURED_CUP, UNKNOWN_PRINT_CUP);
    }

    public void talkToGossip() {
        cQuesterV2.status = "Going to Gossip (guard).";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(GOSSIP_AREA);

        if (NpcChat.talkToNPC("Gossip")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Who do you think was responsible?");
            NPCInteraction.handleConversation();
        }

    }

    public void poisonSalesman() {
        cQuesterV2.status = "Going to Poison Salesman";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(SEERS_BAR);

        cQuesterV2.status = "Talking to Poison Salesman";
        General.println("[Debug]: " + cQuesterV2.status);
        if (NpcChat.talkToNPC("Poison salesman")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Talk about the Murder Mystery Quest");
            NPCInteraction.handleConversation("Who did you sell Poison to at the house?");
            NPCInteraction.handleConversation();
        }
    }

    int PARENT_INTERFACE = 162;
    int CHILD_INTERFACE = 56;

    public void compareAllPrints() {
        cQuesterV2.status = "Comparing prints";
        General.println("[Debug]: " + cQuesterV2.status);
        if (Inventory.find(UNKNOWN_PRINT).length > 0) {
            Utils.useItemOnItem(CAROLS_PRINT, UNKNOWN_PRINT_DAGGER);
            General.sleep(400, 800);
            if (NPCInteraction.isConversationWindowUp()) {
                NPCInteraction.handleConversation();
            }
            String one = Interfaces.get(PARENT_INTERFACE, CHILD_INTERFACE, 0).getText();
            if (one.contains("are an exact match")) {
                CAROL = true;
                General.println("[Debug]: Carol's print's match.");
                return;
            } else {
                CAROL = false;

            }
            if (Utils.useItemOnItem(FRANKS_PRINTS, UNKNOWN_PRINT_DAGGER))
                General.sleep(400, 1200);
            if (NPCInteraction.isConversationWindowUp()) {
                NPCInteraction.handleConversation();
            }
            one = Interfaces.get(PARENT_INTERFACE, CHILD_INTERFACE, 0).getText();
            if (one.contains("are an exact match")) {
                FRANK = true;
                General.println("[Debug]: Frank's print's match.");
                return;
            } else {
                FRANK = false;
            }
            Utils.useItemOnItem(ELIZABETHS_PRINT, UNKNOWN_PRINT_DAGGER);
            General.sleep(General.random(400, 1200));
            if (NPCInteraction.isConversationWindowUp()) {
                NPCInteraction.handleConversation();
            }
            one = Interfaces.get(PARENT_INTERFACE, CHILD_INTERFACE, 0).getText();
            if (one.contains("are an exact match")) {
                ELIZABETH = true;
                General.println("[Debug]: Elizabeths's print's match.");
                return;
            } else {
                ELIZABETH = false;
            }
            Utils.useItemOnItem(BOBS_PRINTS, UNKNOWN_PRINT_DAGGER);
            General.sleep(General.random(400, 1200));
            if (NPCInteraction.isConversationWindowUp()) {
                NPCInteraction.handleConversation();
            }
            one = Interfaces.get(PARENT_INTERFACE, CHILD_INTERFACE, 0).getText();
            if (one.contains("are an exact match")) {
                BOB = true;
                General.println("[Debug]: Bob's print's match.");
                return;
            } else {
                BOB = false;
            }
            Utils.useItemOnItem(ANNAS_PRINTS, UNKNOWN_PRINT_DAGGER);
            General.sleep(600, 1200);
            if (NPCInteraction.isConversationWindowUp()) {
                NPCInteraction.handleConversation();
            }
            one = Interfaces.get(PARENT_INTERFACE, CHILD_INTERFACE, 0).getText();
            if (one.contains("are an exact match")) {
                ANNA = true;
                General.println("[Debug]: Anna's print's match.");
                return;
            } else {
                ANNA = false;
            }
            Utils.useItemOnItem(DAVIDS_PRINT, UNKNOWN_PRINT_DAGGER);
            General.sleep(General.random(400, 1200));
            if (NPCInteraction.isConversationWindowUp()) {
                NPCInteraction.handleConversation();
            }
            one = Interfaces.get(PARENT_INTERFACE, CHILD_INTERFACE, 0).getText();
            if (one.contains("are an exact match")) {
                DAVID = true;
                General.println("[Debug]: David's print's match.");
                return;
            } else {
                DAVID = false;
            }
        }
    }


    public void finishUpInvestigation() {
        if (ANNA) {
            cQuesterV2.status = "Going to question Anna";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToTile(FIRST_BEDROOM);
            Timer.waitCondition(() -> FIRST_FLOOR_BEDROOM_AREA.contains(Player.getPosition()), 8000);
            if (NpcChat.talkToNPC("Anna")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Why'd you buy poison the other day?");
                NPCInteraction.handleConversation();
            }
        }
        if (BOB) {
            cQuesterV2.status = "Going to question Bob";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(BOB_AREA);
            Timer.waitCondition(() -> BOB_AREA.contains(Player.getPosition()), 8000);
            if (NpcChat.talkToNPC("Bob")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Why'd you buy poison the other day?");
                NPCInteraction.handleConversation();
            }
        }
        if (DAVID) {
            cQuesterV2.status = "Going to question David";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(DINING_AREA);

            if (NpcChat.talkToNPC("David")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Why'd you buy poison the other day?");
                NPCInteraction.handleConversation();
            }
        }
        if (CAROL) {
            cQuesterV2.status = "Going to question Carol";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToTile(SECOND_BEDROOM);
            Timer.waitCondition(() -> SECOND_BEDROOM_AREA.contains(Player.getPosition()), 8000);
            if (NpcChat.talkToNPC("Carol")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Why'd you buy poison the other day?");
                NPCInteraction.handleConversation();
            }
        }
        if (FRANK) {
            cQuesterV2.status = "Going to question Frank";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(DINING_AREA);
            if (NpcChat.talkToNPC("Frank")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Why'd you buy poison the other day?");
                NPCInteraction.handleConversation();
            }
        }
        if (ELIZABETH) {
            cQuesterV2.status = "Going to question Elizabeth";
            General.println("[Debug]: " + cQuesterV2.status);

            PathingUtil.walkToTile(FOURTH_BEDROOM);
            Timer.waitCondition(() -> FOURTH_BEDROOM_AREA.contains(Player.getPosition()), 8000);
            if (NpcChat.talkToNPC("Elizabeth")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Why'd you buy poison the other day?");
                NPCInteraction.handleConversation();
            }
        }
    }

    public void step11() {
        if (ANNA) {
            cQuesterV2.status = "Investigating Compost Pile";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToTile(new RSTile(2732, 3572, 0));
            Utils.clickObj(26120, "Investigate");
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }
        if (BOB) {
            cQuesterV2.status = "Investigating Cow pen - Bob";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToTile(new RSTile(2731, 3559, 1));
            Utils.modSleep();
            if (Utils.clickObj("Sinclair family beehive", "Investigate")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }

        }
        if (DAVID) {
            cQuesterV2.status = "Investigating Spiders Nest - David";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToTile(new RSTile(2740, 3575, 1));
            Utils.modSleep();
            if (Utils.clickObj("Spiders' nest", "Investigate")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
        }
        if (CAROL) {
            cQuesterV2.status = "Investigating Drain";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(DRAIN_AREA);
            if (Utils.clickObj("Sinclair mansion drain", "Investigate")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
        }
        if (FRANK) {
            cQuesterV2.status = "Investigating Family Crest";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(FOUNTAIN_AREA);
            if (Utils.clickObj("Sinclair family fountain", "Investigate")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
        }
        if (ELIZABETH) {
            cQuesterV2.status = "Investigating Fountain";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(FOUNTAIN_AREA);
            if (Utils.clickObj("Sinclair family fountain", "Investigate")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
        }
    }

    public void finishQuest() {
        cQuesterV2.status = "Finishing Quest";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(QUEST_START);

        if (NpcChat.talkToNPC("Guard")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("I know who did it!");
            NPCInteraction.handleConversation();
        }
        cQuesterV2.status = "Closing Quest Window";
        General.println("[Debug]: " + cQuesterV2.status);
        Utils.closeQuestCompletionWindow();

    }

    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return cQuesterV2.taskList.get(0).equals(this);
    }

    @Override
    public void execute() {
        criminalsDaggerAny.addAlternateItemID(ItemID.CRIMINALS_DAGGER_1814);
        criminalsThread.addAlternateItemID(ItemID.CRIMINALS_THREAD_1809, ItemID.CRIMINALS_THREAD_1810);

        Log.debug("check? " + new Conditions(pungentPot, criminalsDaggerAny, criminalsThread2).check());
        if (Game.getSetting(192) == 0) {
            buyItems();
            getItems();
            startQuest();
        }
        if (Game.getSetting(194) == 4) {
            DAVID = true;
        } else if (Game.getSetting(194) == 5) {
            ELIZABETH = true;
        } else if (Game.getSetting(192) == 1) {
            if (killersPrint.check()) {
                talkToGossip();
                poisonSalesman();
                finishUpInvestigation();
                step11();
            } else if (new Conditions(pungentPot, criminalsThread1, unknownPrint, hasAnyThread1Item).check()) {
                usePaperOnCupOrBottle();
                compareAllPrints();
                //getAndComparePrintsOfCupOrBottle
            } else if (new Conditions(pungentPot, criminalsThread2, unknownPrint, hasAnyThread2Item).check()) {
                usePaperOnNecklaceOrBook();
                compareAllPrints();
                //getAndComparePrintsOfNecklaceOrBook
            } else if (new Conditions(pungentPot, criminalsThread3, unknownPrint, hasAnyThread3Item).check()) {
                usePaperOnNeedleOrPot();
                compareAllPrints();
                //getAndComparePrintsOfNeeedleOrPot
            } else if (new Conditions(pungentPot, criminalsDaggerFlour, criminalsThread).check() ||
                    new Conditions(pungentPot, criminalsDagger, criminalsThread).check()) {
                Log.log("Getting dagger print");
                getFlyPaper();
                getDaggerPrint();

            } else if (new Conditions(pungentPot, criminalsDagger, criminalsThread3, silverNeedle, silverPot).check() ||
                    new Conditions(pungentPot, criminalsDagger, criminalsThread2, silverNecklace, silverBook).check() ||
                    new Conditions(pungentPot, criminalsDagger, criminalsThread1, silverCup, silverBottle).check()) {
                getFlyPaper();
                getFlour();
            } else if (new Conditions(pungentPot, criminalsDaggerAny, criminalsThread1, silverBottle).check()) {
                cQuesterV2.status = "Searching bob's barrel";
                searchBobsBarrel.execute();
            } else if (new Conditions(pungentPot, criminalsDaggerAny, criminalsThread1).check()) {
                cQuesterV2.status = "Searching carols's barrel";
                searchCarolsBarrel.execute();
            } else if (new Conditions(pungentPot, criminalsDaggerAny, criminalsThread3, silverNeedle).check()) {
                cQuesterV2.status = "Searching frank's barrel";
                searchFranksBarrel.execute();
                searchFranksBarrel.execute();
            } else if (new Conditions(pungentPot, criminalsDaggerAny, criminalsThread3).check()) {
                cQuesterV2.status = "Searching elizabeth's barrel";
                searchElizabethsBarrel.execute();
            } else if (new Conditions(pungentPot, criminalsDaggerAny, criminalsThread2, silverNecklace).check()) {
                cQuesterV2.status = "Searching david's barrel";
                searchDavidsBarrel.execute();
            } else if (new Conditions(pungentPot, criminalsDaggerAny, criminalsThread2).check()) {
                cQuesterV2.status = "Searching annas's barrel";
                Log.debug("Searching annas's barrel");
                searchAnnasBarrel.execute();
            } else if (new Conditions(pungentPot, criminalsDaggerAny).check()) {
                searchSmashedWindow();
            } else if (criminalsDaggerAny.check()) {
                getPungentPot();

            } else if (new Conditions(LogicType.OR, threeFlypaper, criminalsThread, pungentPot).check()) {
                getDagger();
            } else {
                getFlour();
            }
            /*step1();
            bedroom1();
            bedroom2();
            bedroom3();
            bedroom4();
            bedroom5();
            step2();
            getFlyPaper();
              getFlour();
              getDaggerPrint();
            step6();
            talkToGossip();
            poisonSalesman();
            compareAllPrints();
            finishUpInvestigation();
            step11();
            finishQuest();*/
        } else if (Game.getSetting(192) == 2) {
            cQuesterV2.taskList.remove(this);
        }
        Waiting.waitNormal(100, 10);

    }

    @Override
    public String questName() {
        return "Murder Mystery";
    }

    @Override
    public boolean checkRequirements() {
        return true;
    }
    @Override
    public List<Requirement> getGeneralRequirements() {
        return null;
    }

    @Override
    public List<ItemRequirement> getBuyList() {
        return null;
    }

    @Override
    public boolean isComplete() {
        return Quest.MURDER_MYSTERY.getState().equals(Quest.State.COMPLETE);
    }
}
