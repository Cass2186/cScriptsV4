package scripts.QuestPackages.kingsRansom;

import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.*;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.*;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.MonkeyMadnessI.MonkeyMadnessI;
import scripts.QuestSteps.*;
import scripts.Requirements.*;
import scripts.Requirements.Items.ItemCollections;
import scripts.Requirements.Items.ItemRequirements;
import scripts.Requirements.Quest.QuestRequirement;
import scripts.Requirements.Util.ConditionalStep;
import scripts.Requirements.Util.Conditions;
import scripts.Requirements.Util.LogicType;
import scripts.Tasks.Priority;

import java.nio.file.Path;
import java.util.*;

public class KingsRansom implements QuestTask {
    private static KingsRansom quest;

    public static KingsRansom get() {
        return quest == null ? quest = new KingsRansom() : quest;
    }

    int[] TUMBLER_ANSWERS = new int[]{3894, 3895, 3896, 3897};
    int[] TUMBLER_WIDGETS = new int[]{20, 21, 22, 23};
    int[] TUMBLER_CURRENT = new int[]{3901, 3902, 3903, 3904};
    int CURRENT_TUMBLER = 3905;
    int UP_WIDGET = 12;
    int DOWN_WIDGET = 13;
    int TRY_LOCK = 14;

    int highlightChildID;
    //Items Required
    ItemRequirement scrapPaper, addressForm, blackHelm, criminalsThread, hairclip, lawRune, airRune, bronzeMed, ironChain, bronzeMedWorn, ironChainWorn,
            blackKnightLeg, blackKnightLegWorn, blackKnightBody, blackKnightBodyWorn, blackKnightHelm, blackKnightHelmWorn, animateRock, lockpick, grabOrLockpick,
            hairclipOrLockpick, holyGrail, granite, telegrab;

    //Items Recommended
    ItemRequirement ardougneTeleport, camelotTeleport, edgevilleTeleport;

    Requirement hasBlackHelm, hasScrapPaper, hasForm, inUpstairsManor, inDownstairsManor, inTrialRoom, handlerInRoom, butlerInRoom,
            maidInRoom, askedAboutThread, askedAboutDagger, askedAboutNight, askedAboutPoison, inPrison, inBasement, inPuzzle, hasTelegrabItems,
            inBoxWidget, inKeepF0, inKeepF1, inKeepF2, inFortressEntrance, inSecretRoom;

    QuestStep talkToGossip, talkToGuard, breakWindow, grabPaper, goUpstairsManor, takeForm, searchBookcase, goDownstairsManor, goDownstairsForPaper,
            leaveWindow, returnToGuard, talkToGossipAgain, talkToAnna, goIntoTrial, callHandlerAboutPoison, callButlerAboutDagger, callMaidAboutNight,
            talkToHandlerAboutPoison, talkToButlerAboutDagger, talkToMaidAboutNight, waitForVerdict, leaveCourt, talkToAnnaAfterTrial, enterStatue,
            talkToMerlin, reachForVent, useGrabOnGuard, useHairClipOnOnDoor, solvePuzzle, climbF0ToF1, climbF1ToF2, searchTable, selectPurpleBox,
            goDownToArthur, getLockpickOrRunes, openMetalDoor, enterStatueForGrail, enterFortress, enterWallInFortress, freeArthur, talkToArthur,
            talkToArthurInCamelot, enterFortressAfterFreeing, enterWallInFortressAfterFreeing, enterBasementAfterFreeing;

    NPCStep callAboutThread, talkToCromperty;

    //RSAreas
    RSArea upstairsManor, downstairsManor, downstairsManor2, trialRoom, prison, basement, keepF0, keepF1, keepF2, secretRoomFloor0, mainEntrance1, mainEntrance2,
            mainEntrance3, mainEntrance4, secretBasement;
    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.LAW_RUNE, 1, 50),
                    new GEItem(ItemID.AIR_RUNE, 1, 200),
                    new GEItem(ItemID.GRANITE_2KG, 1, 200),
                    new GEItem(ItemID.BLACK_FULL_HELM, 1, 200),
                    new GEItem(ItemID.BLACK_PLATEBODY, 1, 200),
                    new GEItem(ItemID.BLACK_PLATELEGS, 1, 200),
                    new GEItem(ItemID.IRON_CHAINBODY, 1, 500),
                    new GEItem(ItemID.BRONZE_BAR, 1, 500),
                    new GEItem(ItemID.BRONZE_MED_HELM, 1, 500),
                    new GEItem(ItemID.CAMELOT_TELEPORT, 5, 50),
                    new GEItem(ItemID.AMULET_OF_GLORY4, 1, 30),
                    new GEItem(ItemID.STAMINA_POTION[0], 3, 15),
                    new GEItem(ItemID.RING_OF_WEALTH[0], 1, 25)
            )
    );

    InventoryRequirement initialItemReqs = new InventoryRequirement(new ArrayList<>(
            Arrays.asList(
                    new ItemReq(ItemID.LAW_RUNE, 1),
                    new ItemReq(ItemID.AIR_RUNE, 1),
                    new ItemReq(ItemID.GRANITE_2KG, 1),
                    new ItemReq(ItemID.BLACK_PLATELEGS, 1, true),
                    new ItemReq(ItemID.BLACK_PLATEBODY, 1, true),
                    new ItemReq(ItemID.BLACK_FULL_HELM, 1, true),
                    new ItemReq(ItemID.IRON_CHAINBODY, 1),
                    new ItemReq(ItemID.BRONZE_MED_HELM, 1),
                    new ItemReq(ItemID.CAMELOT_TELEPORT, 5),
                    new ItemReq(ItemID.ANIMATE_ROCK_SCROLL, 1, 0),
                    new ItemReq(ItemID.AMULET_OF_GLORY4, 1, 0, true, true),
                    new ItemReq(ItemID.STAMINA_POTION[0], 3, 0),
                    new ItemReq(ItemID.RING_OF_WEALTH[0], 1, 0, true)

            )
    ));

    BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);

    public Map<Integer, QuestStep> loadSteps() {
        loadAreas();
        setupItemRequirements();
        setupConditions();
        setupSteps();
        Map<Integer, QuestStep> steps = new HashMap<>();

        steps.put(0, talkToGossip);
        steps.put(5, talkToGuard);

        ConditionalStep collectItems = new ConditionalStep(breakWindow);
        collectItems.addStep(new Conditions(inDownstairsManor, hasScrapPaper, hasForm, hasBlackHelm), leaveWindow);
        collectItems.addStep(new Conditions(inUpstairsManor, hasScrapPaper, hasForm, hasBlackHelm), goDownstairsManor);
        collectItems.addStep(new Conditions(hasScrapPaper, hasForm, hasBlackHelm), returnToGuard);
        collectItems.addStep(new Conditions(inUpstairsManor, hasScrapPaper, hasForm), searchBookcase);
        collectItems.addStep(new Conditions(inUpstairsManor, hasScrapPaper), takeForm);
        collectItems.addStep(inUpstairsManor, goDownstairsForPaper);
        collectItems.addStep(new Conditions(inDownstairsManor, hasScrapPaper), goUpstairsManor);
        collectItems.addStep(inDownstairsManor, grabPaper);

        steps.put(10, collectItems);

        steps.put(15, talkToGossipAgain);
        steps.put(20, talkToGossipAgain);

        steps.put(25, talkToAnna);

        ConditionalStep trialsSteps = new ConditionalStep(talkToAnna);
        trialsSteps.addStep(new Conditions(askedAboutPoison, askedAboutDagger, askedAboutNight, askedAboutThread), waitForVerdict);
        trialsSteps.addStep(new Conditions(criminalsThread, askedAboutPoison, askedAboutDagger, askedAboutNight), callAboutThread);
        trialsSteps.addStep(new Conditions(criminalsThread, askedAboutPoison, askedAboutDagger, maidInRoom), talkToMaidAboutNight);
        trialsSteps.addStep(new Conditions(criminalsThread, askedAboutPoison, askedAboutDagger), callMaidAboutNight);
        trialsSteps.addStep(new Conditions(criminalsThread, askedAboutPoison, butlerInRoom), talkToButlerAboutDagger);
        trialsSteps.addStep(new Conditions(criminalsThread, askedAboutPoison), callButlerAboutDagger);
        trialsSteps.addStep(new Conditions(criminalsThread, handlerInRoom), talkToHandlerAboutPoison);
        trialsSteps.addStep(new Conditions(criminalsThread, inTrialRoom), callHandlerAboutPoison);
        trialsSteps.addStep(criminalsThread, goIntoTrial);

        steps.put(30, trialsSteps);

        ConditionalStep talkToAnnaAfterTrialSteps = new ConditionalStep(talkToAnnaAfterTrial);
        talkToAnnaAfterTrialSteps.addStep(inTrialRoom, leaveCourt);

        steps.put(35, talkToAnnaAfterTrialSteps);

        steps.put(40, enterStatue);

        ConditionalStep goTalkToMerlin = new ConditionalStep(enterStatue);
        goTalkToMerlin.addStep(inPrison, talkToMerlin);

        steps.put(45, goTalkToMerlin);

        ConditionalStep findMerlinEscape = new ConditionalStep(enterStatue);
        findMerlinEscape.addStep(inPrison, reachForVent);

        steps.put(50, findMerlinEscape);

        ConditionalStep freeKnights = new ConditionalStep(enterStatue);
        freeKnights.addStep(inPuzzle, solvePuzzle);
        freeKnights.addStep(new Conditions(inPrison, hairclipOrLockpick), useHairClipOnOnDoor);
        freeKnights.addStep(new Conditions(inPrison, hasTelegrabItems), useGrabOnGuard);
        freeKnights.addStep(inPrison, getLockpickOrRunes);

        steps.put(55, freeKnights);
        steps.put(60, freeKnights);

        ConditionalStep getGrail = new ConditionalStep(enterStatueForGrail);
        getGrail.addStep(inBoxWidget, selectPurpleBox);
        getGrail.addStep(inKeepF2, searchTable);
        getGrail.addStep(inKeepF1, climbF1ToF2);
        getGrail.addStep(inKeepF0, climbF0ToF1);
        getGrail.addStep(inPrison, openMetalDoor);

        steps.put(65, getGrail);

        steps.put(70, talkToCromperty);

        ConditionalStep goFreeArthur = new ConditionalStep(enterFortress);
        goFreeArthur.addStep(inBasement, freeArthur);
        goFreeArthur.addStep(inSecretRoom, goDownToArthur);
        goFreeArthur.addStep(inFortressEntrance, enterWallInFortress);

        steps.put(75, goFreeArthur);

        ConditionalStep talkToArthurInBasement = new ConditionalStep(enterFortressAfterFreeing);
        talkToArthurInBasement.addStep(inBasement, talkToArthur);
        talkToArthurInBasement.addStep(inSecretRoom, enterBasementAfterFreeing);
        talkToArthurInBasement.addStep(inFortressEntrance, enterWallInFortressAfterFreeing);

        steps.put(80, talkToArthurInBasement);

        steps.put(85, talkToArthurInCamelot);

        return steps;
    }

    public void setupItemRequirements() {
        scrapPaper = new ItemRequirement("Scrap paper", ItemID.SCRAP_PAPER);
        addressForm = new ItemRequirement("Address form", ItemID.ADDRESS_FORM);
        blackHelm = new ItemRequirement("Black knight helm", ItemID.BLACK_KNIGHT_HELM);
        criminalsThread = new ItemRequirement("Criminal's thread", ItemID.CRIMINALS_THREAD_1809);
        lawRune = new ItemRequirement("Law rune", ItemID.LAW_RUNE);
        airRune = new ItemRequirement("Air rune", ItemID.AIR_RUNE);
        hairclip = new ItemRequirement("Hair clip", ItemID.HAIR_CLIP);


        ironChain = new ItemRequirement("Iron chainbody", ItemID.IRON_CHAINBODY);
        ironChainWorn = new ItemRequirement(ItemID.IRON_CHAINBODY, 1, true);

        bronzeMed = new ItemRequirement("Bronze med helm", ItemID.BRONZE_MED_HELM);
        bronzeMedWorn = new ItemRequirement(ItemID.BRONZE_MED_HELM, 1, true);

        blackKnightBody = new ItemRequirement("Black platebody", ItemID.BLACK_PLATEBODY);
        blackKnightBodyWorn = new ItemRequirement(ItemID.BLACK_PLATEBODY, 1, true);

        blackKnightLeg = new ItemRequirement("Black platelegs", ItemID.BLACK_PLATELEGS);
        blackKnightLegWorn = new ItemRequirement(ItemID.BLACK_PLATELEGS, 1, true);

        blackKnightHelm = new ItemRequirement("Black full helm", ItemID.BLACK_FULL_HELM);
        blackKnightHelmWorn = new ItemRequirement(ItemID.BLACK_FULL_HELM, 1, true, true);

        animateRock = new ItemRequirement("Animate rock scroll", ItemID.ANIMATE_ROCK_SCROLL);
        //     animateRock.setTooltip("If you don't have one, you can get another from Wizard Cromperty in Ardougne during " + "the quest");

        lockpick = new ItemRequirement("Lockpick", ItemID.LOCKPICK);

        telegrab = new ItemRequirements("Telegrab runes", new ItemRequirement("Law rune", ItemID.LAW_RUNE),
                new ItemRequirements(LogicType.OR, "Air runes or staff", new ItemRequirement("Air runes", ItemCollections.getAirRune()), new ItemRequirement("Air staff", ItemCollections.getAirStaff())));

        grabOrLockpick = new ItemRequirements(LogicType.OR, "Runes for telekinetic grab or a lockpick", new ItemRequirement("Lockpick", ItemID.LOCKPICK), telegrab);

        hairclipOrLockpick = new ItemRequirement("Hair clip or Lockpick", ItemID.LOCKPICK);
        hairclipOrLockpick.addAlternateItemIDs(ItemID.HAIR_CLIP);

        holyGrail = new ItemRequirement("Holy grail", ItemID.HOLY_GRAIL);
        // holyGrail.setTooltip("You can get another from the purple box on the table in Morgan la Faye's Keep");

        granite = new ItemRequirement("Any granite", ItemID.GRANITE_2KG);
        granite.setDisplayMatchedItemName(true);
        granite.addAlternateItemIDs(ItemID.GRANITE_5KG, ItemID.GRANITE_500G);

        ardougneTeleport = new ItemRequirement("Ardougne teleport", ItemID.ARDOUGNE_TELEPORT);
        camelotTeleport = new ItemRequirement("Camelot teleport", ItemID.CAMELOT_TELEPORT);
        edgevilleTeleport = new ItemRequirement("Edgeville teleport", ItemID.AMULET_OF_GLORY6);
    }

    public void loadAreas() {
        upstairsManor = new RSArea(new RSTile(2729, 3572, 1), new RSTile(2749, 3584, 1));
        downstairsManor = new RSArea(new RSTile(2733, 3574, 0), new RSTile(2747, 3582, 0));
        downstairsManor2 = new RSArea(new RSTile(2739, 3573, 0), new RSTile(2742, 3573, 0));
        trialRoom = new RSArea(new RSTile(1815, 4260, 0), new RSTile(1825, 4276, 0));
        prison = new RSArea(new RSTile(1690, 4250, 0), new RSTile(1909, 4283, 0));
        keepF0 = new RSArea(new RSTile(1689, 4250, 0), new RSTile(1701, 4264, 0));
        keepF1 = new RSArea(new RSTile(1689, 4250, 1), new RSTile(1701, 4264, 1));
        keepF2 = new RSArea(new RSTile(1689, 4250, 2), new RSTile(1701, 4264, 2));
        basement = new RSArea(new RSTile(1862, 4231, 0), new RSTile(1871, 4246, 0));
        secretRoomFloor0 = new RSArea(new RSTile(3015, 3517, 0), new RSTile(3016, 3519, 0));
        secretBasement = new RSArea(new RSTile(1862, 4264, 0), new RSTile(1873, 4229, 0));
        mainEntrance1 = new RSArea(new RSTile(3008, 3513, 0), new RSTile(3012, 3518, 0));
        mainEntrance2 = new RSArea(new RSTile(3012, 3514, 0), new RSTile(3014, 3516, 0));
        mainEntrance3 = new RSArea(new RSTile(3015, 3515, 0), new RSTile(3019, 3516, 0));
        mainEntrance4 = new RSArea(new RSTile(3019, 3513, 0), new RSTile(3019, 3517, 0));
    }

    public void setupConditions() {
        hasForm = new Conditions(LogicType.OR, addressForm, new VarbitRequirement(3890, 1));
        hasScrapPaper = new Conditions(LogicType.OR, scrapPaper, new VarbitRequirement(3891, 1));
        hasBlackHelm = new Conditions(LogicType.OR, blackHelm, new VarbitRequirement(3892, 1));
        inUpstairsManor = new AreaRequirement(upstairsManor);
        inDownstairsManor = new AreaRequirement(downstairsManor, downstairsManor2);
        inTrialRoom = new AreaRequirement(trialRoom);
        inPrison = new AreaRequirement(prison);
        inKeepF0 = new AreaRequirement(keepF0);
        inKeepF1 = new AreaRequirement(keepF1);
        inKeepF2 = new AreaRequirement(keepF2);
        inBasement = new AreaRequirement(basement);
        inSecretRoom = new AreaRequirement(secretRoomFloor0);
        inFortressEntrance = new AreaRequirement(mainEntrance1, mainEntrance2, mainEntrance3, mainEntrance4);

        handlerInRoom = new VarbitRequirement(3907, 2);
        butlerInRoom = new VarbitRequirement(3907, 3);
        maidInRoom = new VarbitRequirement(3907, 5);

        askedAboutThread = new VarbitRequirement(3900, 1);
        askedAboutPoison = new VarbitRequirement(3912, 1);
        askedAboutDagger = new VarbitRequirement(3913, 1);
        askedAboutNight = new VarbitRequirement(3915, 1);

        inPuzzle = new WidgetModelRequirement(588, 1, 27214);

        hasTelegrabItems = new Conditions(airRune, lawRune);

        inBoxWidget = new WidgetModelRequirement(390, 0, 27488);
    }

    public void setupSteps() {
        talkToGossip = new NPCStep(NpcID.GOSSIP, new RSTile(2741, 3557, 0), "Talk to Gossip, just south of the Sinclair Mansion.");
        talkToGossip.addDialogStep("How curious. Maybe I should investigate it.");
        talkToGuard = new NPCStep(4218, new RSTile(2741, 3561, 0), "Talk to the Guard in the Sinclair Manor.");

        breakWindow = new ObjectStep(26123, new RSTile(2749, 3577, 0), "Break");
        goUpstairsManor = new ObjectStep(ObjectID.STAIRCASE_25682, new RSTile(2737, 3582, 0), "Climb-up");

        goDownstairsForPaper = new ObjectStep(ObjectID.STAIRCASE_25683, new RSTile(2736, 3581, 1), "Take");
        grabPaper = new GroundItemStep(ItemID.SCRAP_PAPER, new RSTile(2746, 3580, 0));
        grabPaper.addSubSteps(goDownstairsForPaper);

        takeForm = new GroundItemStep(ItemID.ADDRESS_FORM, new RSTile(2739, 3581, 1));
        searchBookcase = new ObjectStep(ObjectID.BOOKCASE_26053, new RSTile(2738, 3580, 1), "Search");
        goDownstairsManor = new ObjectStep(ObjectID.STAIRCASE_25683, new RSTile(2736, 3581, 1), "Climb-down");
        leaveWindow = new ObjectStep(26123, new RSTile(2748, 3577, 0), "Step out of the window.");
        returnToGuard = new NPCStep(4218, new RSTile(2741, 3561, 0), "Return to the guard with the 3 items.");
        returnToGuard.addDialogStep("I have proof that the Sinclairs have left.", "I have proof that links the Sinclairs to Camelot.", "I have proof of foul play.");

        talkToGossipAgain = new NPCStep(NpcID.GOSSIP, new RSTile(2741, 3557, 0), "Ask Gossip all 3 chat options.");
        talkToGossip.addDialogStep("tell me about the family.");
        talkToGossip.addDialogStep("tell me about the mansion.");
        talkToGossip.addDialogStep("tell me about the Anna Sinclair.");

        talkToAnna = new NPCStep(NpcID.ANNA, new RSTile(2737, 3468, 0), "Talk to Anna in the Seers' Village Court House.");
        talkToAnna.addDialogStep("Okay, I guess I don't have much of a choice.");
        talkToAnna.setAllowInCutscene(true);

        goIntoTrial = new ObjectStep(ObjectID.STAIRS_26017, new RSTile(2738, 3470, 0), "Go down the stairs to the court room.");
        goIntoTrial.addDialogStep("Yes, I'm ready.");
        goIntoTrial.setAllowInCutscene(true);

        callHandlerAboutPoison = new ObjectStep(ObjectID.COURT_JUDGE, new RSTile(1820, 4276, 0), "Talk to the judge to call the Dog Handler and ask them about the poison.");
        callHandlerAboutPoison.addDialogStep("Dog handler", "Previous page");
        callHandlerAboutPoison.setAllowInCutscene(true);

        talkToHandlerAboutPoison = new NPCStep(NpcID.PIERRE);
        talkToHandlerAboutPoison.addDialogStep("Ask about the poison");
        talkToHandlerAboutPoison.setAllowInCutscene(true);
        callHandlerAboutPoison.addSubSteps(talkToHandlerAboutPoison);

        callButlerAboutDagger = new ObjectStep(ObjectID.COURT_JUDGE, new RSTile(1820, 4276, 0), "Talk to the judge to call the Butler and ask them about the dagger.");
        callButlerAboutDagger.addDialogStep("Butler", "Previous page");
        callButlerAboutDagger.setAllowInCutscene(true);

        talkToButlerAboutDagger = new NPCStep(NpcID.HOBBES);
        talkToButlerAboutDagger.addDialogStep("Ask about the dagger");
        talkToButlerAboutDagger.setAllowInCutscene(true);
        callButlerAboutDagger.addSubSteps(talkToButlerAboutDagger);

        callMaidAboutNight = new ObjectStep(ObjectID.COURT_JUDGE, new RSTile(1820, 4276, 0), "Talk to the judge to call the Maid and ask them about the night of the murder.");
        callMaidAboutNight.addDialogStep("Maid", "Next page");
        callMaidAboutNight.setAllowInCutscene(true);

        talkToMaidAboutNight = new NPCStep(NpcID.MARY);
        talkToMaidAboutNight.addDialogStep("Ask about the night of the murder");
        talkToMaidAboutNight.setAllowInCutscene(true);
        callMaidAboutNight.addSubSteps(talkToMaidAboutNight);

        callAboutThread = new NPCStep(NpcID.MARY);
        callAboutThread.addDialogStep("Ask about the thread");
        callAboutThread.addAlternateNpcs(NpcID.HOBBES, NpcID.PIERRE, NpcID.DONOVAN_THE_FAMILY_HANDYMAN, NpcID.LOUISA, NpcID.STANFORD);
        callAboutThread.setAllowInCutscene(true);

        waitForVerdict = new DetailedQuestStep("Wait for the jury to reach their verdict.");
        callAboutThread.addSubSteps(waitForVerdict);

        leaveCourt = new ObjectStep(ObjectID.GATE_26042, new RSTile(1820, 4268, 0), "Leave the court room.");

        talkToAnnaAfterTrial = new NPCStep(NpcID.ANNA, new RSTile(2737, 3466, 0), "Talk to Anna in the Seers' Village Court House.");

        enterStatue = new ObjectStep(ObjectID.STATUE_26073, new RSTile(2780, 3508, 0),
                "Search", Utils.inCutScene(), grabOrLockpick);

        talkToMerlin = new NPCStep(4341, new RSTile(1907, 4281, 0), "Talk to Merlin.");
        talkToMerlin.addDialogStep("What do we do now?");
        reachForVent = new ObjectStep(ObjectID.VENT_25880, new RSTile(1904, 4283, 0),
                "Reach");
        reachForVent.addDialogStep("");
        useGrabOnGuard = new NPCStep(4332, new RSTile(1906, 4270, 0),
                //  "Use telekinetic grab on the guard grooming their hair.",
                lawRune, airRune);
        useHairClipOnOnDoor = new ObjectStep(ObjectID.METAL_DOOR, new RSTile(1904, 4273, 0),
                "Open", hairclipOrLockpick);

        // getLockpickOrRunes = new QuestStep( "Get a lockpick or runes for telegrab. Talking to the knights in the room can give you a lockpick.");
        useGrabOnGuard.addSubSteps(getLockpickOrRunes);

        goDownToArthur = new ObjectStep(ObjectID.LADDER_25843, new RSTile(3016, 3519, 0), "Enter the Black Knight Fortress basement.");

        //solvePuzzle = new LockpickPuzzle(this);

        enterStatueForGrail = new ObjectStep(ObjectID.STATUE_26073,
                new RSTile(2780, 3508, 0), "Search the statue east of Camelot.");

        openMetalDoor = new ObjectStep(ObjectID.METAL_DOOR, new RSTile(1904, 4273, 0),
                "Open");

        climbF0ToF1 = new ObjectStep(ObjectID.STAIRCASE_25786, new RSTile(1695, 4258, 0),
                "Climb-up", MyPlayer.getTile().getPlane() == 1);
        climbF1ToF2 = new ObjectStep(ObjectID.STAIRCASE_25786, new RSTile(1695, 4252, 1),
                "Climb-up", MyPlayer.getTile().getPlane() == 2);
        climbF0ToF1.addSubSteps(climbF1ToF2);
        searchTable = new ObjectStep(ObjectID.TABLE_2650, new RSTile(1695, 4259, 2),
                "Search");
        //   selectPurpleBox = new WidgetStep( "Take the purple round box.", 390, 16);
        searchTable.addSubSteps(selectPurpleBox);

        talkToCromperty = new NPCStep(NpcID.WIZARD_CROMPERTY, new RSTile(2684, 3323, 0), "Talk to Wizard Cromperty in East Ardougne.");
        talkToCromperty.addAlternateNpcs(NpcID.WIZARD_CROMPERTY_8481);
        talkToCromperty.addDialogStep("Chat.");

        enterFortress = new ObjectStep(ObjectID.STURDY_DOOR, new RSTile(3016, 3514, 0),
                "Open",
                bronzeMedWorn, ironChainWorn, blackKnightHelm, blackKnightBody, blackKnightLeg, animateRock, holyGrail, granite);
        enterWallInFortress = new ObjectStep(ObjectID.WALL_2341, new RSTile(3016, 3517, 0),
                "Push",
                blackKnightHelmWorn, blackKnightBodyWorn, blackKnightLegWorn, animateRock, holyGrail, granite);

        enterFortressAfterFreeing = new ObjectStep(ObjectID.STURDY_DOOR, new RSTile(3016, 3514, 0), "Enter the Black Knight Fortress.",
                bronzeMedWorn, ironChainWorn, blackKnightHelm, blackKnightBody, blackKnightLeg);
        enterWallInFortressAfterFreeing = new ObjectStep(ObjectID.WALL_2341, new RSTile(3016, 3517, 0), "Enter the secret room.",
                blackKnightHelmWorn, blackKnightBodyWorn, blackKnightLegWorn, bronzeMed, ironChain);

        enterBasementAfterFreeing = new ObjectStep(ObjectID.LADDER_25843, new RSTile(3016, 3519, 0), "Enter the Black Knight Fortress basement.");
        talkToArthur = new NPCStep(NpcID.KING_ARTHUR, new RSTile(1867, 4235, 0), bronzeMed, ironChain);
        talkToArthur.addSubSteps(enterBasementAfterFreeing, enterFortressAfterFreeing, enterWallInFortressAfterFreeing);

        talkToArthurInCamelot = new NPCStep(NpcID.KING_ARTHUR, new RSTile(2763, 3512, 0), "Talk to King Arthur in Camelot to finish the quest.");
    }

    private void freeArthur() {
        if (inSecretRoom.check()) {
            freeArthur = new ObjectStep(25943, new RSTile(1867, 4233, 0),
                    "Free King Arthur " +
                            "by using the animate rock scroll.", Utils.inCutScene(), animateRock, holyGrail, granite);
            Optional<GameObject> arthur = Query.gameObjects().idEquals(25943).findBestInteractable();
            Optional<InventoryItem> scroll = Query.inventory().idNotEquals(ItemID.ANIMATE_ROCK_SCROLL).findClosestToMouse();
            if (Utils.useItemOnObject(scroll, arthur))
                Waiting.waitUntil(6500, 500, Utils::inCutScene);

            if (Utils.inCutScene())
                Utils.cutScene();

            talkToArthur = new NPCStep(NpcID.KING_ARTHUR, new RSTile(1867, 4235, 0), bronzeMed, ironChain);
            talkToArthur.execute();
        }
    }


    public List<ItemRequirement> getItemRequirements() {
        return Arrays.asList(grabOrLockpick, granite, blackKnightHelm, blackKnightBody, blackKnightLeg, bronzeMed, ironChain);
    }


    public List<ItemRequirement> getItemRecommended() {
        return Arrays.asList(ardougneTeleport, camelotTeleport, edgevilleTeleport);
    }


    Area FIRST_TWO_ROOMS_NEAR_WINDOW = Area.fromPolygon(
            new WorldTile(2748, 3574, 0),
            new WorldTile(2748, 3580, 0),
            new WorldTile(2745, 3580, 0),
            new WorldTile(2745, 3574, 0)
    );

    private boolean breakWindow() {
        if (!inDownstairsManor.check() && !inUpstairsManor.check()) {
            cQuesterV2.status = "Going to break window";
            WorldTile tile = new WorldTile(2748, 3575, 0);
            if (PathingUtil.walkToTile(tile)) {
                Utils.idleNormalAction(true);
            }

            Optional<GameObject> window = Query.gameObjects()
                    .actionContains("Break")
                    .nameContains("window")
                    .findBestInteractable();
            cQuesterV2.status = "Breaking window";
            if (window.map(w -> w.interact("Break")).orElse(false)) {
                Waiting.waitUntil(7000, 250, () -> ChatScreen.isOpen() ||
                        FIRST_TWO_ROOMS_NEAR_WINDOW.containsMyPlayer());
            }
        }
        return inDownstairsManor.check() || inUpstairsManor.check();
    }

    private void pickUpPaper() {
        cQuesterV2.status = "Getting paper";
        Log.info(cQuesterV2.status);
        LocalTile paperTile = new LocalTile(2746, 3580, 0);
        PathingUtil.localNav(paperTile);
        grabPaper.execute();
    }


    private boolean solveTumbler() {
        updateSolvedPositionState();
        Optional<Widget> first = Query.widgets().inIndexPath(588, highlightChildID).findFirst();
        return first.map(f -> f.click()).orElse(false);
    }


    private void updateSolvedPositionState() {
        int current0 = Utils.getVarBitValue(TUMBLER_CURRENT[0]);
        int answer0 = Utils.getVarBitValue(TUMBLER_ANSWERS[0]);
        if (current0 != answer0) {
            updateWidget(0, current0, answer0);
            return;
        }
        int current1 = Utils.getVarBitValue(TUMBLER_CURRENT[1]);
        int answer1 = Utils.getVarBitValue(TUMBLER_ANSWERS[1]);
        if (current1 != answer1) {
            updateWidget(1, current1, answer1);
            return;
        }

        int current2 = Utils.getVarBitValue(TUMBLER_CURRENT[2]);
        int answer2 = Utils.getVarBitValue(TUMBLER_ANSWERS[2]);
        if (current2 != answer2) {
            updateWidget(2, current2, answer2);
            return;
        }
        int current3 = Utils.getVarBitValue(TUMBLER_CURRENT[3]);
        int answer3 = Utils.getVarBitValue(TUMBLER_ANSWERS[3]);
        if (current3 != answer3) {
            updateWidget(3, current3, answer3);
            return;
        }

        highlightChildID = TRY_LOCK;
    }

    private void updateWidget(int widgetID, int currentVal, int answer) {
        int currentTumbler = Utils.getVarBitValue(CURRENT_TUMBLER);
        if (currentTumbler != widgetID + 1) {
            highlightChildID = TUMBLER_WIDGETS[widgetID];
        } else if (currentVal > answer) {
            highlightChildID = DOWN_WIDGET;
        } else {
            highlightChildID = UP_WIDGET;
        }
    }

    private void handleBoxWidget() {
        if (MyPlayer.getTile().getPlane() == 2) {
            Optional<GameObject> table = Query.gameObjects().idEquals(2650).findBestInteractable();
            if (table.map(t -> t.interact("Search")).orElse(false)) {
                Log.info("solving widget");
                Waiting.waitUntil(2500, 200, () -> Widgets.isVisible(390));
            }
            Optional<Widget> first = Query.widgets().inIndexPath(390, 16).findFirst();
            if (first.map(Widget::click).orElse(false)) {
                Waiting.waitUntil(2500, 200, () -> !Widgets.isVisible(390));
            }
        }
    }

    private boolean handleFortress() {
        enterFortress = new ObjectStep(ObjectID.STURDY_DOOR, new RSTile(3016, 3514, 0),
                "Open",
                bronzeMedWorn, ironChainWorn, blackKnightHelm, blackKnightBody,
                blackKnightLeg, animateRock, holyGrail, granite);
        enterWallInFortress = new ObjectStep(ObjectID.WALL_2341, new RSTile(3016, 3517, 0),
                "Push",
                blackKnightHelmWorn, blackKnightBodyWorn, blackKnightLegWorn, animateRock, holyGrail, granite);

        enterFortressAfterFreeing = new ObjectStep(ObjectID.STURDY_DOOR, new RSTile(3016, 3514, 0), "Enter the Black Knight Fortress.",
                bronzeMedWorn, ironChainWorn, blackKnightHelm, blackKnightBody, blackKnightLeg);
        enterWallInFortressAfterFreeing = new ObjectStep(ObjectID.WALL_2341, new RSTile(3016, 3517, 0), "Enter the secret room.",
                blackKnightHelmWorn, blackKnightBodyWorn, blackKnightLegWorn, bronzeMed, ironChain);

        enterBasementAfterFreeing = new ObjectStep(ObjectID.LADDER_25843, new RSTile(3016, 3519, 0), "Enter the Black Knight Fortress basement.");

        if (inBasement.check())
            return true;
        else if (inSecretRoom.check()) {
            goDownToArthur.execute();
        } else if (inFortressEntrance.check() && blackKnightBody.equipItem() &&
                blackKnightHelmWorn.equipItem() && blackKnightLegWorn.equipItem()) {
            enterWallInFortress.execute();
        } else if (bronzeMedWorn.equipItem() && ironChainWorn.equipItem()) {
            enterFortress.execute();
        }
        return false;
    }
 /*   @Override
    public QuestPointReward getQuestPointReward()
    {
        return new QuestPointReward(1);
    }

    @Override
    public List<ExperienceReward> getExperienceRewards()
    {
        return Arrays.asList(
                new ExperienceReward(Skill.DEFENCE, 33000),
                new ExperienceReward(Skill.MAGIC, 5000));
    }

    @Override
    public List<ItemReward> getItemRewards()
    {
        return Collections.singletonList(new ItemReward("5,000 Experience Lamp (any skill over 50).", ItemID.ANTIQUE_LAMP, 1)); //4447 is Placeholder
    }*/

    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return cQuesterV2.taskList.size() > 0 && cQuesterV2.taskList.get(0).equals(this);
    }

    @Override
    public void execute() {
        if (isComplete() || !checkRequirements()) {
            cQuesterV2.taskList.remove(this);
            return;
        }

        int varbit = QuestVarbits.QUEST_KINGS_RANSOM.getId();
        Log.debug("King's ransom varbit is " + Utils.getVarBitValue(varbit));
        loadSteps(); //needed to intializae steps

        // buy initial items on quest start
        if (Utils.getVarBitValue(varbit) == 0 && !initialItemReqs.check()) {
            buyStep.buyItems();
            initialItemReqs.withdrawItems();
        } else if (Utils.getVarBitValue(varbit) == 10) {
            breakWindow();
            pickUpPaper();
        } else if (Widgets.isVisible(588)) {
            for (int i = 0; i < 50; i++) {
                Log.info("solving widget");
                if (solveTumbler())
                    Waiting.waitNormal(1200,  120);
                if (!Widgets.isVisible(588))
                    break;
            }
        } else {
            handleBoxWidget();
        }
        for (int i = 0; i <5; i++){
            Log.info("Handling fortress");
            if (handleFortress())
                break;
            Waiting.waitNormal(600, 75);
        }
        freeArthur();


        //load questSteps into a map
        Map<Integer, QuestStep> steps = loadSteps();
        //get the step based on the game setting key in the map
        Optional<QuestStep> step = Optional.ofNullable(steps.get(Utils.getVarBitValue(varbit)));

        // set status
        cQuesterV2.status = step.map(Object::toString).orElse("Unknown Step Name");

        //do the actual step
        step.ifPresent(QuestStep::execute);

        // handle any chats that are failed to be handled by the QuestStep (failsafe)
        if (ChatScreen.isOpen())
            ChatScreen.handle();

        //slow down looping if it gets stuck
        Waiting.waitNormal(100, 10);

    }

    @Override
    public String questName() {
        return "King's Ransom (" + Utils.getVarBitValue(QuestVarbits.QUEST_KINGS_RANSOM.getId()) + ")";
    }

    @Override
    public boolean checkRequirements() {
        return getGeneralRequirements().stream().allMatch(Requirement::check);
    }

    @Override
    public List<ItemRequirement> getBuyList() {
        return null;
    }

    @Override
    public boolean isComplete() {
        return Quest.KINGS_RANSOM.getState().equals(Quest.State.COMPLETE);
    }

    @Override
    public List<Requirement> getGeneralRequirements() {
        ArrayList<Requirement> req = new ArrayList<>();
        req.add(new QuestRequirement(Quest.BLACK_KNIGHTS_FORTRESS, Quest.State.COMPLETE));
        req.add(new QuestRequirement(Quest.HOLY_GRAIL, Quest.State.COMPLETE));
        req.add(new QuestRequirement(Quest.MURDER_MYSTERY, Quest.State.COMPLETE));
        req.add(new QuestRequirement(Quest.ONE_SMALL_FAVOUR, Quest.State.COMPLETE));
        req.add(new SkillRequirement(Skills.SKILLS.MAGIC, 45));
        req.add(new SkillRequirement(Skills.SKILLS.DEFENCE, 65));
        return req;
    }

}
