package scripts.QuestPackages.MerlinsCrystal;

import dax.walker.utils.AccurateMouse;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.ext.Doors;
import org.tribot.api2007.types.*;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.types.World;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.TearsOfGuthix.TearsOfGuthix;
import scripts.QuestSteps.*;
import scripts.Requirements.*;
import scripts.Requirements.ItemReq;
import scripts.Tasks.Priority;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MerlinsCrystal implements QuestTask {

    private static MerlinsCrystal quest;

    public static MerlinsCrystal get() {
        return quest == null ? quest = new MerlinsCrystal() : quest;
    }


    public final int INSECT_REPELLENT = 28;
    public final int BUCKET_OF_WAX = 30;
    public final int LIT_BLACK_CANDLE = 32;
    public final int LIT_CANDLE = 33;
    public final int EXCALIBUR = 35;
    public final int CANDLE = 36;
    public final int BLACK_CANDLE = 38;
    public final int BAT_BONES = 530;


    //Items Recommended
    ItemReq varrockTeleport, camelotTeleport, twoFaladorTeleports;

    QuestStep startQuest, talkToGawain, goUpstairsInCamelot, talkToLancelot, goBackDownStairsCamelot, hideInArheinCrate, goToFirstFloor, goToSecondFloor,
            attackMordred, talkToMorgan, goToCatherbyAfterFortress, optionalGetRepellent, optionalGetBucket, optionalUseRepellent, talkToCandleMaker, talkToLadyOfLake,
            enterSarimShopAndTalk, talkToBeggar, goReadMagicWords, returnToCamelot, returnToCamelotLit, goStandInStar, lightCandle, dropBatBones, sayWords,
            goUpLadder1Camelot, goUpLadder2Camelot, smashCrystal, goDownLadder1Camelot, goDownLadder2Camelot, finishQuest;

    //  ConditionalStep getBlackCandle, getExcalibur;

 /*   @Override
    public Map<Integer, QuestStep> loadSteps()
    {
        loadRSAreas();
        setupItemReqs();
        setupConditions();
        setupSteps();
        Map<Integer, QuestStep> steps = new HashMap<>();

        steps.put(0, startQuest);
        steps.put(1, talkToGawain);

        ConditionalStep findLancelot = new ConditionalStep( goUpstairsInCamelot);
        findLancelot.addStep(inCamelot1, talkToLancelot);

        steps.put(2, findLancelot);

        ConditionalStep discoverHowToFreeMerlin = new ConditionalStep( hideInArheinCrate);
        discoverHowToFreeMerlin.addStep(new Conditions(inFaye2, morganNearby), talkToMorgan);
        discoverHowToFreeMerlin.addStep(inFaye2, attackMordred);
        discoverHowToFreeMerlin.addStep(inFaye1, goToSecondFloor);
        discoverHowToFreeMerlin.addStep(inFayeGround, goToFirstFloor);
        discoverHowToFreeMerlin.addStep(inCamelot1, goBackDownStairsCamelot);

        steps.put(3, discoverHowToFreeMerlin);

        getBlackCandle = new ConditionalStep( optionalGetRepellent);
        getBlackCandle.addStep(inFaye, goToCatherbyAfterFortress);
        getBlackCandle.addStep(bucketOfWax, talkToCandleMaker);
        getBlackCandle.addStep(new Conditions(repellent, bucket), optionalUseRepellent);
        getBlackCandle.addStep(repellent, optionalGetBucket);
        getBlackCandle.setLockingCondition(hasAnyBlackCandle);

        getExcalibur = new ConditionalStep( talkToLadyOfLake);
        getExcalibur.addStep(beggarNearby, talkToBeggar);
        getExcalibur.addStep(talkedToLady, enterSarimShopAndTalk);
        getExcalibur.setLockingCondition(excalibur);

        ConditionalStep performSpell = new ConditionalStep( returnToCamelot);
        performSpell.addStep(thrantaxNearby, sayWords);
        performSpell.addStep(new Conditions(inStar, litBlackCandle), dropBatBones);
        performSpell.addStep(inStar, lightCandle);
        performSpell.addStep(inCamelot, goStandInStar);
        performSpell.addStep(litBlackCandle, returnToCamelotLit);

        ConditionalStep completeAllTasks = new ConditionalStep( getBlackCandle);
        completeAllTasks.addStep(new Conditions(hasAnyBlackCandle, excalibur, hasReadSpell), performSpell);
        completeAllTasks.addStep(new Conditions(hasAnyBlackCandle, excalibur), goReadMagicWords);
        completeAllTasks.addStep(hasAnyBlackCandle, getExcalibur);

        steps.put(4, completeAllTasks);

        ConditionalStep goFreeMerlin = new ConditionalStep( goUpLadder1Camelot);
        goFreeMerlin.addStep(inCamelotTower2, smashCrystal);
        goFreeMerlin.addStep(inCamelotTower1, goUpLadder2Camelot);

        steps.put(5, goFreeMerlin);

        ConditionalStep goTellArthur = new ConditionalStep( finishQuest);
        goTellArthur.addStep(inCamelotTower1, goDownLadder1Camelot);
        goTellArthur.addStep(inCamelotTower2, goDownLadder2Camelot);

        steps.put(6, goTellArthur);

        return steps;
    }*/

    ItemReq bread = new ItemReq("Bread", ItemID.BREAD);
    ItemReq tinderbox = new ItemReq("Tinderbox", ItemID.TINDERBOX);
    ItemReq bucketOfWaxOptional = new ItemReq("Bucket of wax (obtainable during quest)", ItemID.BUCKET_OF_WAX);
    ItemReq bucketOfWax = new ItemReq("Bucket of wax", ItemID.BUCKET_OF_WAX);
    ItemReq batBones = new ItemReq("Bat bones", ItemID.BAT_BONES);
    ItemReq batBonesOptional = new ItemReq("Bat bones (obtainable during quest)", ItemID.BAT_BONES);
    //  ItemReq   varrockTeleport = new ItemReq(ItemID.VARROCK_TELEPORT);
    //ItemReq   camelotTeleport = new ItemReq(ItemID.CAMELOT_TELEPORT);
    //  ItemReq   twoFaladorTeleports = new ItemReq(ItemID.FALADOR_TELEPORT, 2);
    //  combatGear = new ItemReq("Combat gear + food for Sir Mordred (level 39)", -1, -1);
    //  combatGear.setDisplayItemID(BankSlotIcons.getCombatGear());
    ItemReq blackCandle = new ItemReq("Black candle", ItemID.BLACK_CANDLE);
    ItemReq litBlackCandle = new ItemReq("Lit black candle", ItemID.LIT_BLACK_CANDLE);
    ItemReq excalibur = new ItemReq("Excalibur", ItemID.EXCALIBUR);
    ItemReq equippedExcalibur = new ItemReq(ItemID.EXCALIBUR, 1, true, true);
    RSArea fayeDock = new RSArea(new RSTile(2776, 3402, 0), new RSTile(2781, 3399, 0));
    AreaRequirement inFayeDock = new AreaRequirement(fayeDock);

    RSArea fayeGround = new RSArea(new RSTile(2764, 3395, 0), new RSTile(2781, 3410, 0));
    RSArea faye1 = new RSArea(new RSTile(2764, 3395, 1), new RSTile(2781, 3410, 1));
    RSArea faye2 = new RSArea(new RSTile(2764, 3395, 2), new RSTile(2781, 3410, 2));
    RSArea camelot1 = new RSArea(new RSTile(2744, 3483, 1), new RSTile(2769, 3517, 1));
    RSArea camelot2 = new RSArea(new RSTile(2744, 3483, 2), new RSTile(2769, 3517, 2));
    RSArea camelotGround1 = new RSArea(new RSTile(2744, 3483, 0), new RSTile(2774, 3517, 0));
    RSArea camelotGround2 = new RSArea(new RSTile(2775, 3511, 0), new RSTile(2783, 3517, 0));
    RSArea camelotGround3 = new RSArea(new RSTile(2774, 3505, 0), new RSTile(2776, 3511, 0));
    RSArea star = new RSArea(new RSTile(2780, 3515, 0), new RSTile(2780, 3515, 0));
    RSArea camelotTower1 = new RSArea(new RSTile(2765, 3490, 1), new RSTile(2770, 3495, 1));
    RSArea camelotTower2 = new RSArea(new RSTile(2765, 3490, 2), new RSTile(2770, 3494, 2));


    AreaRequirement inFaye = new AreaRequirement(faye1, fayeGround, faye2);
    AreaRequirement inFayeGround = new AreaRequirement(fayeGround);
    AreaRequirement inFaye1 = new AreaRequirement(faye1);
    AreaRequirement inFaye2 = new AreaRequirement(faye2);
    AreaRequirement inCamelot = new AreaRequirement(camelotGround1, camelotGround2, camelotGround3);
    AreaRequirement inCamelot1 = new AreaRequirement(camelot1);
    AreaRequirement inCamelot2 = new AreaRequirement(camelot2);
    //  morganNearby = new NpcCondition(NpcID.MORGAN_LE_FAYE);
    // clearedHive = new ObjectCondition(ObjectID.BEEHIVE_305);
    // hasAnyBlackCandle = new Conditions(LogicType.OR, blackCandle, litBlackCandle);
    // beggarNearby = new NpcCondition(NpcID.BEGGAR);
    // talkedToLady = new WidgetTextRequirement(217, 5, "Ok. That seems easy enough.");
    // hasReadSpell = new Conditions(true, LogicType.AND, new WidgetTextRequirement(229, 1, "You find a small inscription"));
    AreaRequirement inStar = new AreaRequirement(star);
    // thrantaxNearby = new NpcCondition(NpcID.THRANTAX_THE_MIGHTY);
    AreaRequirement inCamelotTower1 = new AreaRequirement(camelotTower1);
    AreaRequirement inCamelotTower2 = new AreaRequirement(camelotTower2);


    public void setupSteps() {
        startQuest = new NPCStep(NpcID.KING_ARTHUR, new RSTile(2763, 3513, 0), "Talk to King Arthur in Camelot Castle to start.");
        startQuest.addDialogStep("I want to become a knight of the round table!", "Yes.");
         /*   talkToGawain = new NPCStep(NpcID.SIR_GAWAIN, new RSTile(2758, 3504, 0), "Talk to Sir Gawain about how Merlin got trapped.");
        talkToGawain.addDialogStep("Do you know how Merlin got trapped?");

        goUpstairsInCamelot = new ObjectStep(ObjectID.STAIRCASE_26106, new RSTile(2751, 3511, 0), "Go upstairs to talk to Lancelot.");

        talkToLancelot = new NPCStep(NpcID.SIR_LANCELOT, new RSTile(2760, 3511, 1), "Talk to Sir Lancelot about getting into Morgan Le Faye's stronghold.");
        talkToLancelot.addDialogStep("Any ideas on how to get into Morgan Le Faye's stronghold?");
        talkToLancelot.addDialogStep("Thank you for the information.");

        goBackDownStairsCamelot = new ObjectStep(ObjectID.STAIRCASE_25604, new RSTile(2751, 3512, 1), "Hide in Arhein's crate behind the Candle Maker's shop in Catherby.");

        hideInArheinCrate = new ObjectStep(ObjectID.CRATE_63, new RSTile(2801, 3442, 0), "Hide in Arhein's crate behind the Candle Maker's shop in Catherby.");
        hideInArheinCrate.addSubSteps(goBackDownStairsCamelot);
        hideInArheinCrate.addDialogStep("Yes.");

        goToFirstFloor = new ObjectStep(ObjectID.STAIRCASE_15645, new RSTile(2770, 3405, 0), "Go up the stairs in the fortress.");
        goToSecondFloor = new ObjectStep(ObjectID.STAIRCASE_15645, new RSTile(2770, 3399, 1), "Go up another floor.");
        attackMordred = new NPCStep(NpcID.SIR_MORDRED, new RSTile(2770, 3403, 2), "Attack Sir Mordred down to 0hp to cause Morgan Le Faye to spawn.");
        attackMordred.addDialogStep("Tell me how to untrap Merlin and I might.");
        attackMordred.addDialogStep("Ok I will do all that.");
        talkToMorgan = new DetailedQuestStep("Go through Morgan le Faye's dialog. IF YOU EXIT FROM THIS DIALOG YOU WILL HAVE TO FIGHT MORDRED AGAIN.");
        talkToMorgan.addDialogStep("Tell me how to untrap Merlin and I might.");
        talkToMorgan.addDialogStep("Ok I will do all that.");

    goToCatherbyAfterFortress = new DetailedQuestStep("Return to Catherby. If you still need bat bones, you can kill one of the bats just outside the fortress.");
        optionalGetRepellent = new DetailedQuestStep(new RSTile(2807, 3450, 0), "If you still need wax, go grab the insect repellent in a house in north Catherby. Otherwise, get your wax out.", repellent);
        optionalGetBucket = new DetailedQuestStep(new RSTile(2766, 3441, 0), "Go grab the bucket in the bee field west of Catherby.", bucket);

        optionalUseRepellent = new ObjectStep(ObjectID.BEEHIVE, new RSTile(2762, 3443, 0), "Use the insect repellent on a bee hive, then try to take some wax.", bucket, repellent);
        talkToCandleMaker = new NPCStep(NpcID.CANDLE_MAKER, new RSTile(2797, 3440, 0), "Talk to the Candle Maker in Catherby twice until he gives you a black candle.", bucketOfWax);
        talkToCandleMaker.addDialogStep("Have you got any black candles?");

        talkToLadyOfLake = new NPCStep(NpcID.THE_LADY_OF_THE_LAKE, new RSTile(2924, 3404, 0), "Talk to the Lady of the Lake in Taverley.");
        talkToLadyOfLake.addDialogStep("I seek the sword Excalibur.");
        talkToLadyOfLake.setLockingCondition(talkedToLady);

        enterSarimShopAndTalk = new ObjectStep(ObjectID.DOOR_59, new RSTile(3016, 3246, 0), "Attempt to enter the jewelery store in Port Sarim.", bread);
        enterSarimShopAndTalk.addDialogStep("Yes certainly.");
        talkToBeggar = new ObjectStep(ObjectID.DOOR_59, new RSTile(3016, 3246, 0), "Talk to the beggar who appears and give him some bread.", bread);
        talkToBeggar.addDialogStep("Yes certainly.");

        goReadMagicWords = new ObjectStep(ObjectID.CHAOS_ALTAR, new RSTile(3260, 3381, 0), "Check the altar in the Zamorak Temple in south east Varrock. If you've already learnt the spell, just mark this step complete in the Quest Helper sidebar.");
        goReadMagicWords.setLockingCondition(hasReadSpell);

        returnToCamelot = new ObjectStep(ObjectID.GATE_26082, new RSTile(2758, 3482, 0), "Return to Camelot", excalibur, blackCandle, batBones, tinderbox);
        returnToCamelotLit = new ObjectStep(ObjectID.GATE_26082, new RSTile(2758, 3482, 0), "Return to Camelot", excalibur, litBlackCandle, batBones);

        goStandInStar = new DetailedQuestStep(new RSTile(2780, 3515, 0), "Go stand in the star symbol north east of Camelot Castle.");
        goStandInStar.addDialogStep("Snarthon Candtrick Termanto");

        lightCandle = new DetailedQuestStep("Light the Black candle with your tinderbox.", blackCandle, tinderbox);
        dropBatBones = new DetailedQuestStep("Drop the bat bones in the star.", batBones, excalibur, litBlackCandle);
        dropBatBones.addDialogStep("Snarthon Candtrick Termanto");
        sayWords = new DetailedQuestStep("Say the spell 'Snarthon Candtrick Termanto'. Be careful not to click the wrong option or you'll have to get another Black Candle.", excalibur);
        sayWords.addDialogStep("Snarthon Candtrick Termanto");
        */
        //smashCrystal = new ObjectStep(ObjectID.GIANT_CRYSTAL, new RSTile(2768, 3494, 2), "Smash the Giant Crystal with Excalibur equipped.", equippedExcalibur);

        goDownLadder1Camelot = new ObjectStep(25606, new RSTile(2769, 3493, 1),
                "Climb-down");
        goDownLadder2Camelot = new ObjectStep(25606, new RSTile(2767, 3491, 2),
                "Climb-down");

        finishQuest = new NPCStep(NpcID.KING_ARTHUR, new RSTile(2763, 3513, 0), "Tell King Arthur you've freed Merlin.");
        //finishQuest.addSubSteps(goDownLadder1Camelot, goDownLadder2Camelot);
    }

    int BREAD = 2309;
    int insectRepellant = 28;
    int varrockTab = 8007;
    int camelotTab = 8010;
    int faladorTab = 8009;
    int ardougneTab = 8011;
    int antidoteplusplus = 5952;
    int glory4 = 1712;
    int lobster = 379;
    int games8 = 3853;
    int combatBracelet4 = 11118;
    int runeScimitar = 1333;
    int adamantScimitar = 1331;
    int MITHRIL_SCIMITAR = 1329;

    public RSItem[] invItem1;

    public RSTile kingArthurTile = new RSTile(2761, 3514, 0);
    public RSTile lancelotTile = new RSTile(2760, 3511, 1);
    public RSTile catherbyTile = new RSTile(2804, 3429, 0);
    public RSTile crateTile = new RSTile(2801, 3443, 0);
    public RSTile candleShop = new RSTile(2800, 3439, 0);
    public RSTile ladyOfTheLakeTile = new RSTile(2923, 3406, 0);
    public RSTile jewleryShop = new RSTile(3013, 3246, 0);
    public RSTile chaosAltarTile = new RSTile(3258, 3385, 0);
    public RSTile summoningTile = new RSTile(2780, 3515, 0);
    public RSTile merlinTile = new RSTile(2767, 3492, 2);
    public RSTile secondFloor = new RSTile(22770, 3397, 1);

    RSArea cratedropoff = new RSArea(new RSTile(2776, 3401, 0), 10);
    RSArea KEEP_LE_FE_SECOND_FLOOR = new RSArea(new RSTile(2776, 3395, 1), new RSTile(2761, 3409, 1));
    RSArea SECOND_FLOOR_STAIRS_UP = new RSArea(new RSTile(2768, 3397, 1), new RSTile(2771, 3395, 1));
    RSArea KEEP_LE_FE_THIRD_FLOOR = new RSArea(new RSTile(2776, 3394, 2), new RSTile(2763, 3410, 2));


    RSNPC[] ladyOfTheLake = NPCs.find("The Lady of the Lake");

    RSObject[] giantCrystal = Objects.findNearest(5, "Giant crystal");
    RSObject[] door = Objects.findNearest(15, "Door");
    RSObject[] stairs = Objects.findNearest(15, "Staircase");


    /*****
     * METHODS
     */

    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(BREAD, 1, 500),
                    new GEItem(ItemID.BUCKET_OF_WAX, 1, 400),
                    new GEItem(ItemID.BAT_BONES, 1, 300),
                    new GEItem(ItemID.VARROCK_TELEPORT, 6, 50),
                    new GEItem(ItemID.FALADOR_TELEPORT, 7, 50),
                    new GEItem(ItemID.CAMELOT_TELEPORT, 7, 50),
                    new GEItem(ItemID.ANTIDOTE_PLUS_PLUS[0], 3, 50),
                    new GEItem(ItemID.SKILLS_NECKLACE[2], 1, 30),
                    new GEItem(ItemID.LOBSTER, 15, 50),
                    new GEItem(ItemID.TINDERBOX, 1, 50),
                    new GEItem(ItemID.GAMES_NECKLACE[0], 1, 50),
                    new GEItem(ItemID.MIND_RUNE, 300, 20),
                    new GEItem(ItemID.FIRE_RUNE, 900, 20),
                    new GEItem(ItemID.STAFF_OF_AIR, 1, 50),

                    new GEItem(ItemID.AMULET_OF_GLORY[2], 2, 20),
                    new GEItem(ItemID.COMBAT_BRACELET[0], 1, 20),
                    new GEItem(ItemID.STAMINA_POTION[0], 3, 15),
                    new GEItem(ItemID.RING_OF_WEALTH[0], 1, 25)
            )
    );


    BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);

    public void buyItems() {
        cQuesterV2.status = "Buying Items";
        buyStep.buyItems();
    }

    InventoryRequirement fightInv = new InventoryRequirement(new ArrayList<>(
            Arrays.asList(
                    new ItemReq(ItemID.CHAOS_RUNE, 400, 50),
                    new ItemReq(ItemID.LAVA_RUNE, 1200, 300),
                    new ItemReq(ItemID.WATER_RUNE, 1200, 300),
                    new ItemReq(ItemID.STAFF_OF_AIR, 1, 0),
                    new ItemReq(ItemID.LOBSTER, 15, 5),
                    new ItemReq(ItemID.GAMES_NECKLACE[0], 1, 0),
                    new ItemReq(ItemID.STAMINA_POTION[0], 1, 0),
                    new ItemReq(ItemID.PRAYER_POTION[0], 3, 0)
            ))
    );

    InventoryRequirement initialInv = new InventoryRequirement(new ArrayList<>(
            Arrays.asList(
                    new ItemReq(ItemID.CAMELOT_TELEPORT, 7, 1),
                    new ItemReq(ItemID.TINDERBOX, 1, 1),
                    new ItemReq(ItemID.FALADOR_TELEPORT, 5, 1),
                    new ItemReq(ItemID.BUCKET_OF_WAX, 1, 1),
                    new ItemReq(ItemID.BAT_BONES, 1),
                    new ItemReq(ItemID.BREAD, 1),
                    new ItemReq(ItemID.LOBSTER, 13, 1),
                    new ItemReq(ItemID.MIND_RUNE, 300, 50),
                    new ItemReq(ItemID.FIRE_RUNE, 800, 100),
                    new ItemReq(ItemID.GAMES_NECKLACE[0], 1, 0),
                    new ItemReq(ItemID.STAMINA_POTION[0], 2, 0),
                    new ItemReq(ItemID.AMULET_OF_GLORY[2], 1, 0, true, true)
            ))
    );


    public void getItems() {
        if (!initialInv.check()) {
            cQuesterV2.status = "Getting Items";
            General.println("[Debug]: " + cQuesterV2.status);
            BankManager.open(true);
            BankManager.depositAll(true);
            BankManager.withdraw(7, true, ItemID.CAMELOT_TELEPORT);
            BankManager.withdraw(1, true, ItemID.TINDERBOX);
            BankManager.withdraw(5, true, ItemID.FALADOR_TELEPORT);
            BankManager.withdraw(1, true, ItemID.AMULET_OF_GLORY[2]);
            BankManager.withdraw(1, true, ItemID.GAMES_NECKLACE[0]);
            BankManager.withdraw(1, true, ItemID.BUCKET_OF_WAX);
            BankManager.withdraw(2, true, ItemID.STAMINA_POTION[0]);
            BankManager.withdraw(1, true, ItemID.BAT_BONES);
            BankManager.withdraw(1, true, ItemID.BREAD);

            BankManager.withdraw(2, true, ItemID.BLACK_CANDLE);
            BankManager.withdraw(1, true, ItemID.STAFF_OF_AIR);
            Utils.equipItem(ItemID.STAFF_OF_AIR);
            BankManager.withdraw(300, true, ItemID.MIND_RUNE);
            BankManager.withdraw(800, true, ItemID.FIRE_RUNE);
            BankManager.withdraw(13, true, ItemID.LOBSTER);
            BankManager.withdraw(2, true, ItemID.VARROCK_TELEPORT);
            BankManager.withdraw(1, true, ItemID.EXCALIBUR);
            BankManager.close(true);
        }
    }

    public void goToKingArthur() {
        cQuesterV2.status = "Going to king arthur";
        PathingUtil.walkToTile(kingArthurTile);
    }

    public void startQuest() {
        if (initialInv.check()) {
            goToKingArthur();
            if (NpcChat.talkToNPC("King Arthur")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("I want to become a knight of the round table!");
                NPCInteraction.handleConversation("Yes.");
            }
        }
    }

    public void talkToGawain() {
        cQuesterV2.status = "Going to Sir Gawain";
        if (NpcChat.talkToNPC("Sir Gawain")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Any ideas on how to ger Merlin out of that crystal?");
            NPCInteraction.handleConversation("Any ideas on how to ger Merlin out of that crystal?", "Do you know how Merlin got trapped?");
            NPCInteraction.handleConversation();
        }
    }

    public void talkToSirLancelot() {
        cQuesterV2.status = "Going to Sir Lancelot";
        PathingUtil.walkToTile(lancelotTile);
        if (NpcChat.talkToNPC("Sir Lancelot")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Any ideas on how to get into Morgan Le Faye's stronghold?");
            NPCInteraction.handleConversation();
        }
    }

    public void goToArhein() {
        if (!cratedropoff.contains(Player.getPosition())
                && !KEEP_LE_FE_SECOND_FLOOR.contains(Player.getPosition()) && !KEEP_LE_FE_THIRD_FLOOR.contains(Player.getPosition())) {
            cQuesterV2.status = "Going to Arhein";
            PathingUtil.walkToTile(catherbyTile);
            if (NpcChat.talkToNPC("Arhein")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Is that your ship?");
                NPCInteraction.handleConversation("Where do you deliver to?");
                NPCInteraction.handleConversation();
            }
        }
    }

    public void hideInCrates() {
        if (!cratedropoff.contains(Player.getPosition()) &&
                !KEEP_LE_FE_SECOND_FLOOR.contains(Player.getPosition()) && !KEEP_LE_FE_THIRD_FLOOR.contains(Player.getPosition())) {
            cQuesterV2.status = "Hiding in crates";
            PathingUtil.walkToTile(crateTile);
            if (Utils.clickObj(63, "Hide-in")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Yes.");
                NPCInteraction.handleConversation();
                Utils.idle(4000, 6000);
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Yes.");
                NPCInteraction.handleConversation();
            }
        }
    }


    public void navigateFirstFloor() {
        if (inFayeDock.check()) {
            cQuesterV2.status = "Going to Mordred";
            door = Objects.findNearest(5, "Large door");
            if (door.length > 0) {
                if (Doors.handleDoor(door[0], true))
                    General.sleep(General.random(2000, 3000));

                Utils.blindWalkToTile(new RSTile(2770, 3404, 0));
                PathingUtil.movementIdle();

                checkEat();
            }
        }
        if (inFayeGround.check()) {
            if (Utils.clickObj("Staircase", "Climb-up"))
                Timer.slowWaitCondition(() -> KEEP_LE_FE_SECOND_FLOOR.contains(Player.getPosition()), 1000, 13000);

            checkEat();
        }

    }

    public boolean checkEat() {
        RSItem[] invFood = Inventory.find(lobster);
        if (Combat.getHPRatio() < General.random(40, 60) && invFood.length > 0) {
            return (AccurateMouse.click(invFood[0], "Eat"));
        }
        return false;
    }

    public void navigateSecondFloor() {
        if (inFaye1.check()) {
            Log.debug("In second floor");
            Utils.blindWalkToTile( new RSTile(2769, 3396, 1));
            Waiting.waitNormal(750,90);
            checkEat();
            if (Utils.clickObj(15645, "Climb-up")) {
                Timer.waitCondition(() -> !KEEP_LE_FE_SECOND_FLOOR.contains(Player.getPosition()), 7000, 15000);
                checkEat();
                if (Combat.isUnderAttack() && !KEEP_LE_FE_SECOND_FLOOR.contains(Player.getPosition()))
                    Timer.abc2WaitCondition(() -> !Combat.isUnderAttack(), 6000, 9000);
            }

        }
    }

    public void navigateThirdFloor() {
        if (KEEP_LE_FE_THIRD_FLOOR.contains(Player.getPosition())) {
            RSNPC[] mordred = NPCs.find(3527);
            if (mordred.length > 0) {

                if (mordred[0].isInCombat() && !mordred[0].isInteractingWithMe()) {
                    General.println("[Debug]: Someone is already fighting mordred, need to hop");

                    int world = WorldHopper.getRandomWorld(true);
                    if (WorldHopper.changeWorld(world))
                        Timer.waitCondition(() -> WorldHopper.getWorld() ==
                                world, 12000, 18000);
                    return;


                } else if (!Combat.isUnderAttack()) {
                    if (AccurateMouse.click(mordred[0], "Talk-to")) {
                        NPCInteraction.waitForConversationWindow();
                        NPCInteraction.handleConversation();
                        Timer.waitCondition(Combat::isUnderAttack, 5000, 7000);
                    }
                }
            }
            mordred = NPCs.find(3527);
            while (mordred.length > 0) {
                cQuesterV2.status = "Killing Mordred";
                General.sleep(100);
                Utils.equipItem(ItemID.STAFF_OF_AIR);
                if (!Autocast.isAutocastEnabled(Autocast.FIRE_STRIKE))
                    Autocast.enableAutocast(Autocast.FIRE_STRIKE);

                checkEat();
                if (!Combat.isUnderAttack()) {
                    if (!NPCInteraction.isConversationWindowUp() || mordred[0].getHealthPercent() > 20) {
                        if (AccurateMouse.click(mordred[0], "Talk-to")) {
                            NPCInteraction.waitForConversationWindow();
                            NPCInteraction.handleConversation();
                            Timer.waitCondition(Combat::isUnderAttack, 5000, 7000);
                        }
                    } else if (!Combat.isUnderAttack() && mordred[0].getHealthPercent() < 20) {
                        Utils.modSleep();
                        NPCInteraction.handleConversation("Tell me how to untrap Merlin and I might.");
                        NPCInteraction.handleConversation();
                    }
                }
                mordred = NPCs.find(3527);
                if (Game.getSetting(14) == 4 || mordred.length < 1) {
                    break;
                }
            }
        }
    }

    public void getBlackCandle() {
        if (Inventory.find(ItemID.BLACK_CANDLE).length < 1 && Inventory.find(LIT_BLACK_CANDLE).length < 1) {
            if (!BankManager.checkInventoryItems(ItemID.BUCKET_OF_WAX)) {
                buyItems();
                getItems();
            }

            cQuesterV2.status = "Getting black candle";
            PathingUtil.walkToTile(candleShop);

            if (NpcChat.talkToNPC("Candle maker")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Have you got any black candles?");
                NPCInteraction.handleConversation();
                if (Inventory.find(ItemID.BLACK_CANDLE).length < 1) {
                    if (NpcChat.talkToNPC("Candle maker")) {
                        NPCInteraction.waitForConversationWindow();
                        NPCInteraction.handleConversation();
                        NPCInteraction.handleConversation();
                    }
                }
            }
        }
    }

    public void ladyofthelake() {
        if (Inventory.find(ItemID.EXCALIBUR).length < 1) {
            cQuesterV2.status = "Going to lady of the lake";
            PathingUtil.walkToTile(ladyOfTheLakeTile);
            if (NpcChat.talkToNPC("The Lady of the Lake")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("I seek the sword Excalibur.");
                NPCInteraction.handleConversation();
            }
        }
    }

    public void goToPortSarim() {
        if (Inventory.find(ItemID.EXCALIBUR).length < 1) {
            cQuesterV2.status = "Going to Port Sarim";

            if (PathingUtil.walkToTile(new RSTile(3016, 3247, 0)))
                Timer.abc2WaitCondition(() -> !Player.isMoving(), 7000, 9000);

            if (Utils.clickObj("Door", "Open")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Yes certainly.");
                NPCInteraction.handleConversation();
                Utils.modSleep();
            }
        }
    }

    public void goToAltar() {
        invItem1 = Inventory.find(ItemID.EXCALIBUR);
        if (invItem1.length > 0) {
            cQuesterV2.status = "Going to Chaos Altar";
            PathingUtil.walkToTile(chaosAltarTile);
            if (Utils.clickObj("Chaos Altar", "Check")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Yes certainly.");
                NPCInteraction.handleConversation();
            }
        }
    }

    public void summon() {
        RSItem[] invItem1 = Inventory.find(ItemID.EXCALIBUR);
        RSItem[] invBatBones = Inventory.find(batBones.getId());
        checkItems(batBones.getId());
        if (Inventory.find(excalibur.getId()).length > 0 && Inventory.find(batBones.getId()).length > 0 &&
                (Inventory.find(blackCandle.getId()).length > 0) || Inventory.find(LIT_BLACK_CANDLE).length > 0) {
            cQuesterV2.status = "Going to Summoning circle";

            if (PathingUtil.walkToTile(summoningTile))
                Timer.abc2WaitCondition(() -> !Player.isMoving(), 8000, 10000); // important they be in the right spot for next steps

            if (Utils.useItemOnItem(tinderbox.getId(), blackCandle.getId()))
                General.sleep(General.random(400, 1200));

            if (invBatBones.length > 0) {
                if (AccurateMouse.click(invBatBones[0], "Drop")) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation("Snarthon Candtrick Termanto");
                    NPCInteraction.handleConversation();
                }
            }
        }
    }

    public void freeMerlin() {
        cQuesterV2.status = "Freeing Merlin";
        PathingUtil.walkToTile(merlinTile);

        if (Utils.useItemOnObject(ItemID.EXCALIBUR, "Giant crystal")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Yes certainly.");
            NPCInteraction.handleConversation();
        }
    }

    public void finishQuest() {
        goToKingArthur();
        if (NpcChat.talkToNPC("King Arthur")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("I want to become a knight of the round table!");
            NPCInteraction.handleConversation();
        }
    }

    public void checkItems(int... ItemID) {
        if (!BankManager.checkInventoryItems(ItemID)) {
            General.println("[Debug]: Missing an item, going to check bank and buy if needed.");
            buyItems();
            getItems();
        }
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
        if (Game.getSetting(14) == 0) {
            buyItems();
            getItems();
            startQuest();
        } else if (Inventory.getAll().length == 0) {
            buyItems();
            getItems();
            return;
        }
        if (Game.getSetting(14) == 1) {
            talkToGawain();
        }
        if (Game.getSetting(14) == 2) {
            talkToSirLancelot();
        }
        if (Game.getSetting(14) == 3) {
            goToArhein();
            hideInCrates();
            navigateFirstFloor();
            navigateSecondFloor();
            navigateThirdFloor();
        }
        if (Game.getSetting(14) == 4) {
            getBlackCandle();
            ladyofthelake();
            goToPortSarim();
            goToAltar();
            summon();
        }
        if (Game.getSetting(14) == 5) {
            freeMerlin();
        }
        if (Game.getSetting(14) == 6) {
            goToKingArthur();
            finishQuest();
        }
        if (Game.getSetting(14) == 7) {
            Utils.closeQuestCompletionWindow();
            cQuesterV2.taskList.remove(this);
        }

    }

    @Override
    public String questName() {
        return "Merlin's Crystal";
    }

    @Override
    public boolean checkRequirements() {
        return false;
    }

    @Override
    public List<Requirement> getGeneralRequirements() {
        return null;
    }

    @Override
    public List<ItemRequirement> getBuyList() {
        return null;
    }
}
