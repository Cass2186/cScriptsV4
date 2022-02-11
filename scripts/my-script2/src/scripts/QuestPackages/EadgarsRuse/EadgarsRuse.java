package scripts.QuestPackages.EadgarsRuse;

import org.tribot.api2007.Player;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.Inventory;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.Npc;
import scripts.*;
import scripts.QuestSteps.*;
import scripts.Requirements.*;
import scripts.Requirements.Items.ItemCollections;
import scripts.Requirements.Util.ConditionalStep;
import scripts.Requirements.Util.Conditions;
import scripts.Tasks.Priority;
import scripts.Timer;

import java.util.*;

public class EadgarsRuse implements QuestTask {
    //Items Required
    ItemRequirement climbingBoots, climbingBootsOr12Coins, vodka, vodkaHighlight, pineappleChunks, pineappleChunksHighlight, logs2, grain10, rawChicken5, tinderbox, pestleAndMortar, ranarrPotionUnf,
            coins12, cellKey2, alcoChunks, parrot, parrotHighlighted, robe, logs1, thistle, logHighlight, tinderboxHighlight, driedThistle, pestleAndMortarHighlight, groundThistle, ranarrUnfHighlight, trollPotion, trainedParrot,
            fakeMan, storeroomKey, goutweed, climbingBootsEquipped;

    //Items Recommended
    ItemRequirement ardougneTeleport;

    Requirement inSanfewRoom, inTenzingHut, hasClimbingBoots, hasCoins, onMountainPath, inTrollArea1, inPrison, freedEadgar, hasCellKey2, inStrongholdFloor1, inStrongholdFloor2,
            inEadgarsCave, inTrollheimArea, askedAboutAlcohol, askedAboutPineapple, fireNearby, foundOutAboutKey, inStoreroom;

    QuestStep goUpToSanfew, talkToSanfew, buyClimbingBoots, travelToTenzing, getCoinsOrBoots, climbOverStile, climbOverRocks, enterSecretEntrance, freeEadgar, goUpStairsPrison,
            getBerryKey, goUpToTopFloorStronghold, exitStronghold, enterEadgarsCave, talkToEadgar, leaveEadgarsCave, enterStronghold, goDownSouthStairs, talkToCook, goUpToTopFloorStrongholdFromCook,
            exitStrongholdFromCook, enterEadgarsCaveFromCook, talkToEadgarFromCook, talkToPete, talkToPeteAboutAlcohol, talkToPeteAboutPineapple, useVodkaOnChunks, useChunksOnParrot, enterEadgarsCaveWithParrot,
            talkToEadgarWithParrot, enterPrisonWithParrot, leaveEadgarsCaveWithParrot, enterStrongholdWithParrot, goDownNorthStairsWithParrot, goDownToPrisonWithParrot, parrotOnRack, talkToTegid, enterEadgarsCaveWithItems,
            talkToEadgarWithItems, leaveEadgarsCaveForThistle, pickThistle, lightFire, useThistleOnFire, useThistleOnTrollFire, grindThistle, useGroundThistleOnRanarr, enterEadgarsCaveWithTrollPotion, giveTrollPotionToEadgar,
            enterPrisonForParrot, enterStrongholdForParrot, goDownNorthStairsForParrot, goDownToPrisonForParrot, getParrotFromRack, leaveEadgarsCaveForParrot, leavePrisonWithParrot, goUpToTopFloorWithParrot, leaveStrongholdWithParrot,
            enterEadgarCaveWithTrainedParrot, talkToEadgarWithTrainedParrot, leaveEadgarsCaveWithScarecrow, enterStrongholdWithScarecrow, goDownSouthStairsWithScarecrow, talkToCookWithScarecrow, talkToBurntmeat, goDownToStoreroom,
            enterStoreroomDoor, getGoutweed, returnUpToSanfew, returnToSanfew;

    ObjectStep searchDrawers;


    //RSArea()s
    RSArea sanfewRoom, tenzingHut, mountainPath1, mountainPath2, mountainPath3, mountainPath4, mountainPath5, trollArea1, prison, strongholdFloor1, strongholdFloor2, eadgarsCave,
            trollheimArea, storeroom;

    public Map<Integer, QuestStep> loadSteps() {
        loadRSAreas();
        setupItemRequirements();
        setupConditions();
        setupSteps();
        if (freedEadgar.check()) {
            /*travelToEadgarPanel = new PanelDetails("Travel to Eadgar",
                    Arrays.asList(travelToTenzing, climbOverStile, climbOverRocks, enterSecretEntrance, goUpStairsPrison,
                            goUpToTopFloorStronghold, enterEadgarsCave, talkToEadgar), climbingBoots);*/
        } else {
           /* travelToEadgarPanel = new PanelDetails("Travel to Eadgar", Arrays.asList(travelToTenzing, climbOverStile,
                    climbOverRocks, enterSecretEntrance, getBerryKey, freeEadgar, goUpStairsPrison, goUpToTopFloorStronghold,
                    enterEadgarsCave, talkToEadgar), climbingBoots);*/
        }

        Map<Integer, QuestStep> steps = new HashMap<>();

        ConditionalStep startQuest = new ConditionalStep(goUpToSanfew);
        startQuest.addStep(inSanfewRoom, talkToSanfew);

        steps.put(0, startQuest);

        ConditionalStep enterTheStronghold = new ConditionalStep(getCoinsOrBoots);
        enterTheStronghold.addStep(new Conditions(inEadgarsCave, freedEadgar), talkToEadgar);
        enterTheStronghold.addStep(new Conditions(inTrollheimArea, freedEadgar), enterEadgarsCave);
        enterTheStronghold.addStep(new Conditions(inStrongholdFloor2, freedEadgar), exitStronghold);
        enterTheStronghold.addStep(new Conditions(inStrongholdFloor1, freedEadgar), goUpToTopFloorStronghold);
        enterTheStronghold.addStep(new Conditions(inPrison, freedEadgar), goUpStairsPrison);
        enterTheStronghold.addStep(new Conditions(inPrison, hasCellKey2), freeEadgar);
        enterTheStronghold.addStep(inPrison, freeEadgar);
        enterTheStronghold.addStep(inTrollArea1, enterSecretEntrance);
        enterTheStronghold.addStep(new Conditions(hasClimbingBoots, onMountainPath), climbOverRocks);
        enterTheStronghold.addStep(new Conditions(hasClimbingBoots, inTenzingHut), climbOverStile);
        enterTheStronghold.addStep(hasClimbingBoots, travelToTenzing);
        enterTheStronghold.addStep(hasCoins, buyClimbingBoots);

        steps.put(10, enterTheStronghold);

        ConditionalStep talkToCooksAboutGoutweed = new ConditionalStep(getCoinsOrBoots);
        talkToCooksAboutGoutweed.addStep(inEadgarsCave, leaveEadgarsCave);
        talkToCooksAboutGoutweed.addStep(inTrollheimArea, enterStronghold);
        talkToCooksAboutGoutweed.addStep(inStrongholdFloor2, goDownSouthStairs);
        talkToCooksAboutGoutweed.addStep(inStrongholdFloor1, talkToCook);
        talkToCooksAboutGoutweed.addStep(inPrison, goUpStairsPrison);
        talkToCooksAboutGoutweed.addStep(inTrollArea1, enterSecretEntrance);
        talkToCooksAboutGoutweed.addStep(new Conditions(hasClimbingBoots, onMountainPath), climbOverRocks);
        talkToCooksAboutGoutweed.addStep(new Conditions(hasClimbingBoots, inTenzingHut), climbOverStile);
        talkToCooksAboutGoutweed.addStep(hasClimbingBoots, travelToTenzing);
        talkToCooksAboutGoutweed.addStep(hasCoins, buyClimbingBoots);

        steps.put(15, talkToCooksAboutGoutweed);

        ConditionalStep returnToEadgar = new ConditionalStep(getCoinsOrBoots);
        returnToEadgar.addStep(inEadgarsCave, talkToEadgarFromCook);
        returnToEadgar.addStep(inTrollheimArea, enterEadgarsCaveFromCook);
        returnToEadgar.addStep(inStrongholdFloor2, exitStrongholdFromCook);
        returnToEadgar.addStep(inStrongholdFloor1, goUpToTopFloorStrongholdFromCook);
        returnToEadgar.addStep(inPrison, goUpStairsPrison);
        returnToEadgar.addStep(inTrollArea1, enterSecretEntrance);
        returnToEadgar.addStep(new Conditions(hasClimbingBoots, onMountainPath), climbOverRocks);
        returnToEadgar.addStep(new Conditions(hasClimbingBoots, inTenzingHut), climbOverStile);
        returnToEadgar.addStep(hasClimbingBoots, travelToTenzing);


        steps.put(25, returnToEadgar);

        ConditionalStep poisonTheParrot = new ConditionalStep(talkToPete);
        poisonTheParrot.addStep(new Conditions(parrot, inEadgarsCave), talkToEadgarWithParrot);
        poisonTheParrot.addStep(parrot, enterEadgarsCaveWithParrot);
        poisonTheParrot.addStep(alcoChunks, useChunksOnParrot);
        poisonTheParrot.addStep(new Conditions(askedAboutAlcohol, askedAboutPineapple), useVodkaOnChunks);
        poisonTheParrot.addStep(askedAboutPineapple, talkToPeteAboutAlcohol);
        poisonTheParrot.addStep(askedAboutAlcohol, talkToPeteAboutPineapple);

        steps.put(30, poisonTheParrot);

        ConditionalStep useParrotOnRack = new ConditionalStep(enterPrisonWithParrot);
        useParrotOnRack.addStep(inTrollheimArea, enterStrongholdWithParrot);
        useParrotOnRack.addStep(inStrongholdFloor2, goDownNorthStairsWithParrot);
        useParrotOnRack.addStep(inStrongholdFloor1, goDownToPrisonWithParrot);
        useParrotOnRack.addStep(inPrison, parrotOnRack);
        useParrotOnRack.addStep(inEadgarsCave, leaveEadgarsCaveWithParrot);

        steps.put(50, useParrotOnRack);

        ConditionalStep bringItemsToEadgar = new ConditionalStep(talkToTegid);
        bringItemsToEadgar.addStep(new Conditions(robe, inEadgarsCave), talkToEadgarWithItems);
        bringItemsToEadgar.addStep(robe, enterEadgarsCaveWithItems);

        steps.put(60, bringItemsToEadgar);
        steps.put(70, bringItemsToEadgar);

        ConditionalStep makePotion = new ConditionalStep(pickThistle);
        makePotion.addStep(new Conditions(trollPotion, inEadgarsCave), giveTrollPotionToEadgar);
        makePotion.addStep(trollPotion, enterEadgarsCaveWithTrollPotion);
        makePotion.addStep(groundThistle, useGroundThistleOnRanarr);
        makePotion.addStep(driedThistle, grindThistle);
        makePotion.addStep(new Conditions(thistle, fireNearby), useThistleOnFire);
        makePotion.addStep(new Conditions(logs1, tinderbox, thistle), lightFire);
        makePotion.addStep(thistle, useThistleOnTrollFire);
        makePotion.addStep(inEadgarsCave, leaveEadgarsCaveForThistle);

        steps.put(80, makePotion);

        ConditionalStep fetchParrot = new ConditionalStep(enterPrisonForParrot);
        fetchParrot.addStep(inTrollheimArea, enterStrongholdForParrot);
        fetchParrot.addStep(inStrongholdFloor2, goDownNorthStairsForParrot);
        fetchParrot.addStep(inStrongholdFloor1, goDownToPrisonForParrot);
        fetchParrot.addStep(inPrison, getParrotFromRack);
        fetchParrot.addStep(inEadgarsCave, leaveEadgarsCaveForParrot);
        steps.put(85, fetchParrot);

        ConditionalStep returnParrotToEadgar = new ConditionalStep(enterEadgarCaveWithTrainedParrot);
        returnParrotToEadgar.addStep(inEadgarsCave, talkToEadgarWithTrainedParrot);
        returnParrotToEadgar.addStep(inTrollheimArea, enterEadgarCaveWithTrainedParrot);
        returnParrotToEadgar.addStep(inStrongholdFloor2, leaveStrongholdWithParrot);
        returnParrotToEadgar.addStep(inStrongholdFloor1, goUpToTopFloorWithParrot);
        returnParrotToEadgar.addStep(inPrison, leavePrisonWithParrot);

        steps.put(86, returnParrotToEadgar);

        ConditionalStep bringManToBurntmeat = new ConditionalStep(enterStrongholdWithScarecrow);
        bringManToBurntmeat.addStep(inEadgarsCave, leaveEadgarsCaveWithScarecrow);
        bringManToBurntmeat.addStep(inStrongholdFloor2, goDownSouthStairsWithScarecrow);
        bringManToBurntmeat.addStep(inStrongholdFloor1, talkToCookWithScarecrow);
        bringManToBurntmeat.addStep(inPrison, goUpStairsPrison);

        steps.put(87, bringManToBurntmeat);

        ConditionalStep getTheGoutweed = new ConditionalStep(talkToBurntmeat);
        getTheGoutweed.addStep(new Conditions(inSanfewRoom, goutweed), returnToSanfew);
        getTheGoutweed.addStep(goutweed, returnUpToSanfew);
        getTheGoutweed.addStep(new Conditions(inStoreroom, storeroomKey), getGoutweed);
        getTheGoutweed.addStep(storeroomKey, goDownToStoreroom);
        getTheGoutweed.addStep(foundOutAboutKey, searchDrawers);

        steps.put(90, getTheGoutweed);

        ConditionalStep returnGoutWeed = new ConditionalStep(goDownToStoreroom);
        returnGoutWeed.addStep(new Conditions(inSanfewRoom, goutweed), returnToSanfew);
        returnGoutWeed.addStep(goutweed, returnUpToSanfew);
        returnGoutWeed.addStep(inStoreroom, getGoutweed);

        steps.put(100, returnGoutWeed);

        return steps;
    }

    public void setupItemRequirements() {
        climbingBoots = new ItemRequirement("Climbing boots", ItemID.CLIMBING_BOOTS);
        climbingBootsEquipped = new ItemRequirement(ItemID.CLIMBING_BOOTS, 1, true);
        climbingBootsOr12Coins = new ItemRequirement("Climbing boots or 12 coins", ItemID.CLIMBING_BOOTS);
        vodka = new ItemRequirement("Vodka", ItemID.VODKA);
        pineappleChunks = new ItemRequirement("Pineapple chunks", ItemID.PINEAPPLE_CHUNKS);
        // pineappleChunks.setTooltip("You can make these by using a knife on a pineapple");
        logs2 = new ItemRequirement("Logs", ItemID.LOGS, 2);
        logs1 = new ItemRequirement("Logs", ItemCollections.getLogsForFire());
        grain10 = new ItemRequirement("Grain", ItemID.GRAIN, 10);
        rawChicken5 = new ItemRequirement("Raw chicken", ItemID.RAW_CHICKEN, 5);
        tinderbox = new ItemRequirement("Tinderbox", ItemID.TINDERBOX);
        pestleAndMortar = new ItemRequirement("Pestle and Mortar", ItemID.PESTLE_AND_MORTAR);
        ranarrPotionUnf = new ItemRequirement("Ranarr potion (unf)", ItemID.RANARR_POTION_UNF);
        ardougneTeleport = new ItemRequirement("Ardougne teleport", ItemID.ARDOUGNE_TELEPORT);
        coins12 = new ItemRequirement(ItemID.COINS_995, 1500);
        cellKey2 = new ItemRequirement("Cell key 2", ItemID.CELL_KEY_2);
        vodkaHighlight = new ItemRequirement("Vodka", ItemID.VODKA);
        // vodkaHighlight.setTooltip("You can buy some from the Gnome Stronghold drinks shop");


        pineappleChunksHighlight = new ItemRequirement("Pineapple chunks", ItemID.PINEAPPLE_CHUNKS);
        // pineappleChunksHighlight.setTooltip("You can cut a pineapple into chunks with a knife");

        alcoChunks = new ItemRequirement("Alco-chunks", ItemID.ALCOCHUNKS);

        parrot = new ItemRequirement("Drunk parrot", ItemID.DRUNK_PARROT);
        //   parrot.setTooltip("You can get another by using alco-chunks on the aviary hatch of the parrot cage in Ardougne Zoo");

        parrotHighlighted = new ItemRequirement("Parrot", ItemID.DRUNK_PARROT);
        //  parrotHighlighted.setTooltip("You can get another by using alco-chunks on the aviary hatch of the parrot cage in Ardougne Zoo");

        robe = new ItemRequirement("Robe", ItemID.DIRTY_ROBE);

        thistle = new ItemRequirement("Troll thistle", ItemID.TROLL_THISTLE);

        logHighlight = new ItemRequirement("Logs", ItemCollections.getLogsForFire());

        tinderboxHighlight = new ItemRequirement("Tinderbox", ItemID.TINDERBOX);

        driedThistle = new ItemRequirement("Dried thistle", ItemID.DRIED_THISTLE);


        pestleAndMortarHighlight = new ItemRequirement("Pestle and mortar", ItemID.PESTLE_AND_MORTAR);


        groundThistle = new ItemRequirement("Ground thistle", ItemID.GROUND_THISTLE);

        ranarrUnfHighlight = new ItemRequirement("Ranarr potion (unf)", ItemID.RANARR_POTION_UNF);

        trollPotion = new ItemRequirement("Troll potion", ItemID.TROLL_POTION);

        trainedParrot = new ItemRequirement("Drunk parrot", ItemID.DRUNK_PARROT);
        //   trainedParrot.setTooltip("If you lost the parrot Eadgar will have it");

        fakeMan = new ItemRequirement("Fake man", ItemID.FAKE_MAN);
        // fakeMan.setTooltip("You can get another from Eadgar if you lose it");

        storeroomKey = new ItemRequirement("Storeroom key", ItemID.STOREROOM_KEY);

        goutweed = new ItemRequirement("Goutweed", ItemID.GOUTWEED);
    }

    public void loadRSAreas() {
        sanfewRoom = new RSArea(new RSTile(2893, 3423, 1), new RSTile(2903, 3433, 1));
        tenzingHut = new RSArea(new RSTile(2814, 3553, 0), new RSTile(2822, 3562, 0));
        mountainPath1 = new RSArea(new RSTile(2814, 3563, 0), new RSTile(2823, 3593, 0));
        mountainPath2 = new RSArea(new RSTile(2824, 3589, 0), new RSTile(2831, 3599, 0));
        mountainPath3 = new RSArea(new RSTile(2832, 3595, 0), new RSTile(2836, 3603, 0));
        mountainPath4 = new RSArea(new RSTile(2837, 3601, 0), new RSTile(2843, 3607, 0));
        mountainPath5 = new RSArea(new RSTile(2844, 3607, 0), new RSTile(2876, 3611, 0));
        trollArea1 = new RSArea(new RSTile(2822, 3613, 0), new RSTile(2896, 3646, 0));
        prison = new RSArea(new RSTile(2822, 10049, 0), new RSTile(2859, 10110, 0));
        strongholdFloor1 = new RSArea(new RSTile(2820, 10048, 1), new RSTile(2862, 10110, 1));
        strongholdFloor2 = new RSArea(new RSTile(2820, 10048, 2), new RSTile(2862, 10110, 2));
        eadgarsCave = new RSArea(new RSTile(2884, 10074, 2), new RSTile(2901, 10091, 2));
        trollheimArea = new RSArea(new RSTile(2836, 3651, 0), new RSTile(2934, 3773, 0));
        storeroom = new RSArea(new RSTile(2850, 10063, 0), new RSTile(2870, 10093, 0));
    }

    public void setupConditions() {
        inSanfewRoom = new AreaRequirement(sanfewRoom);
        hasClimbingBoots = climbingBoots;
        hasCoins = coins12;
        inTenzingHut = new AreaRequirement(tenzingHut);
        onMountainPath = new AreaRequirement(mountainPath1, mountainPath2, mountainPath3, mountainPath4, mountainPath5);
        inTrollArea1 = new AreaRequirement(trollArea1);
        inPrison = new AreaRequirement(prison);
        freedEadgar = new VarbitRequirement(0, 1);
        hasCellKey2 = cellKey2;
        inStrongholdFloor1 = new AreaRequirement(strongholdFloor1);
        inStrongholdFloor2 = new AreaRequirement(strongholdFloor2);
        inEadgarsCave = new AreaRequirement(eadgarsCave);
        inTrollheimArea = new AreaRequirement(trollheimArea);

        askedAboutAlcohol = new Conditions(true, new WidgetTextRequirement(WidgetInfo.DIALOG_NPC_TEXT, "Just recently."));
        askedAboutPineapple = new Conditions(true, new WidgetTextRequirement(WidgetInfo.DIALOG_NPC_TEXT, "fruit and grain mostly"));

        fireNearby = new ObjectCondition(ObjectID.FIRE_26185);

        foundOutAboutKey = new Conditions(true, new WidgetTextRequirement(WidgetInfo.DIALOG_PLAYER_TEXT, "That's some well-guarded secret alright"));
        inStoreroom = new AreaRequirement(storeroom);
    }

    public void setupSteps() {
        goUpToSanfew = new ObjectStep(ObjectID.STAIRCASE_16671, new RSTile(2899, 3429, 0), "Talk to Sanfew upstairs in the Taverley herblore store.");
        talkToSanfew = new NPCStep(NpcID.SANFEW, new RSTile(2899, 3429, 1), "Talk to Sanfew upstairs in the Taverley herblore store.");
        talkToSanfew.addDialogStep("Ask general questions.");
        talkToSanfew.addDialogStep("Have you any more work for me, to help reclaim the circle?");
        talkToSanfew.addDialogStep("I'll do it.", "Yes.");
        //   talkToSanfew.addSubSteps(goUpToSanfew);

        //TODO Fix
       // getCoinsOrBoots = new DetailedQuestStep("Get some climbing boots or 12 coins.", climbingBootsOr12Coins);

        // travelToTenzing = new DetailedQuestStep( new RSTile(2820, 3555, 0));
        buyClimbingBoots = new NPCStep(NpcID.TENZING, new RSTile(2820, 3555, 0), coins12);
        buyClimbingBoots.addDialogStep("Can I buy some Climbing boots?");
        buyClimbingBoots.addDialogStep("OK, sounds good.");
        // travelToTenzing.addSubSteps(getCoinsOrBoots, buyClimbingBoots);

        climbOverStile = new ObjectStep(ObjectID.STILE_3730, new RSTile(2817, 3563, 0), "Climb over the stile north of Tenzing.");
        climbOverRocks = new ObjectStep(ObjectID.ROCKS_3748, new RSTile(2856, 3612, 0), "Follow the path until you reach some rocks. Climb over them.", climbingBoots);

        enterSecretEntrance = new ObjectStep(ObjectID.SECRET_DOOR, new RSTile(2828, 3647, 0), "Enter the Secret Door to the Troll Stronghold.");

        if (Skills.SKILLS.THIEVING.getActualLevel() >= 30) {
            getBerryKey = new NPCStep(NpcID.BERRY_4134, new RSTile(2833, 10083, 0), "Pickpocket or kill Berry for a cell key.");
        } else {
            getBerryKey = new NPCStep(NpcID.BERRY_4134, new RSTile(2833, 10083, 0), "Kill Berry for a cell key.");
        }

        freeEadgar = new ObjectStep(ObjectID.CELL_DOOR_3765, new RSTile(2832, 10082, 0), "Unlock Eadgar's cell.");

        goUpStairsPrison = new ObjectStep(ObjectID.STONE_STAIRCASE, new RSTile(2853, 10107, 0), "Go up the stairs from the prison.");
        // goUpStairsPrison.setWorldMapPoint(new RSTile(2853, 10106, 1));

        goUpToTopFloorStronghold = new ObjectStep(ObjectID.STONE_STAIRCASE, new RSTile(2843, 10109, 1), "Go up to the next floor of the Stronghold.");
        //  goUpToTopFloorStronghold.setWorldMapPoint(new RSTile(2906, 10140, 1));

        exitStronghold = new ObjectStep(ObjectID.EXIT_3772, new RSTile(2838, 10091, 2), "Leave the Stronghold.");
        //  exitStronghold.setWorldMapPoint(new RSTile(2965, 10155, 1));

        enterEadgarsCave = new ObjectStep(ObjectID.CAVE_ENTRANCE_3759, new RSTile(2893, 3673, 0), "Climb to the top of Trollheim and enter Eadgar's cave.");

        talkToEadgar = new NPCStep(NpcID.EADGAR, new RSTile(2891, 10086, 2), "Talk to Eadgar.");
        talkToEadgar.addDialogStep("I need to find some goutweed.");

        leaveEadgarsCave = new ObjectStep(ObjectID.CAVE_EXIT_3760, new RSTile(2893, 10073, 2), "Leave Eadgar's cave.");
        enterStronghold = new ObjectStep(ObjectID.STRONGHOLD, new RSTile(2839, 3690, 0), "Enter the Troll Stronghold.");
        goDownSouthStairs = new ObjectStep(ObjectID.STONE_STAIRCASE_3789, new RSTile(2844, 10052, 2), "Go down the south staircase.");
        // goDownSouthStairs.setWorldMapPoint(new RSTile(2971, 10115, 1));
        talkToCook = new NPCStep(NpcID.BURNTMEAT, new RSTile(2845, 10057, 1), "Talk to Burntmeat.");
        //   talkToCook.setWorldMapPoint(new RSTile(2911, 10087, 1));

        goUpToTopFloorStrongholdFromCook = new ObjectStep(ObjectID.STONE_STAIRCASE, new RSTile(2843, 10052, 1), "Go up to the next floor of the Stronghold.");
        //  goUpToTopFloorStrongholdFromCook.setWorldMapPoint(new RSTile(2907, 10083, 1));

        exitStrongholdFromCook = new ObjectStep(ObjectID.EXIT_3772, new RSTile(2838, 10091, 2), "Leave the Stronghold.");
        //  exitStrongholdFromCook.setWorldMapPoint(new RSTile(2965, 10155, 1));

        enterEadgarsCaveFromCook = new ObjectStep(ObjectID.CAVE_ENTRANCE_3759, new RSTile(2893, 3673, 0), "Climb to the top of Trollheim and enter Eadgar's cave.");

        talkToEadgarFromCook = new NPCStep(NpcID.EADGAR, new RSTile(2891, 10086, 2), "Talk to Eadgar in his cave on top of Trollheim.");
        // talkToEadgarFromCook.addSubSteps(goUpToTopFloorStrongholdFromCook, exitStrongholdFromCook, enterEadgarsCaveFromCook);

        talkToPete = new NPCStep(NpcID.PARROTY_PETE, new RSTile(2612, 3285, 0), pineappleChunks, vodka);
        talkToPete.addDialogStep("No thanks, Eadgar.");
        talkToPete.addDialogStep("What do you feed them?");
        talkToPete.addDialogStep("When did you add it?");

        talkToPeteAboutAlcohol = new NPCStep(NpcID.PARROTY_PETE, new RSTile(2612, 3285, 0), pineappleChunks, vodka);
        talkToPeteAboutAlcohol.addDialogStep("When did you add it?");

        talkToPeteAboutPineapple = new NPCStep(NpcID.PARROTY_PETE, new RSTile(2612, 3285, 0), pineappleChunks, vodka);
        talkToPeteAboutPineapple.addDialogStep("What do you feed them?");

        useVodkaOnChunks = new UseItemOnItemStep(ItemID.VODKA, ItemID.PINEAPPLE_CHUNKS,
                !Inventory.contains(ItemID.VODKA), pineappleChunksHighlight, vodkaHighlight);

        //talkToPete.addSubSteps(talkToPeteAboutAlcohol, talkToPeteAboutPineapple, useVodkaOnChunks);

        useChunksOnParrot = new ObjectStep(ObjectID.AVIARY_HATCH, new RSTile(2611, 3287, 0), "Use the alco-chunks on the aviary hatch of the parrot cage.", alcoChunks);


        enterEadgarsCaveWithParrot = new ObjectStep(ObjectID.CAVE_ENTRANCE_3759, new RSTile(2893, 3673, 0), "Return the parrot to Eadgar on top of Trollheim.", parrot, climbingBoots);

        talkToEadgarWithParrot = new NPCStep(NpcID.EADGAR, new RSTile(2891, 10086, 2), "Return the parrot to Eadgar on top of Trollheim.");
        // talkToEadgarWithParrot.addSubSteps(enterEadgarsCaveWithParrot);

        leaveEadgarsCaveWithParrot = new ObjectStep(ObjectID.CAVE_EXIT_3760, new RSTile(2893, 10073, 2), "Take the parrot to the Troll Stronghold prison.", parrot);
        leaveEadgarsCaveWithParrot.addDialogStep("No thanks, Eadgar.");

        enterStrongholdWithParrot = new ObjectStep(ObjectID.STRONGHOLD, new RSTile(2839, 3690, 0), "Take the parrot to the Troll Stronghold prison, and hide it in the rack there.", parrot);
        goDownNorthStairsWithParrot = new ObjectStep(ObjectID.STONE_STAIRCASE_3789, new RSTile(2844, 10109, 2), "Take the parrot to the Troll Stronghold prison, and hide it in the rack there.", parrot);
        // goDownNorthStairsWithParrot.setWorldMapPoint(new RSTile(2971, 10172, 1));
        goDownToPrisonWithParrot = new ObjectStep(ObjectID.STONE_STAIRCASE_3789, new RSTile(2853, 10108, 1), "Take the parrot to the Troll Stronghold prison, and hide it in the rack there.", parrot);
        // goDownToPrisonWithParrot.setWorldMapPoint(new RSTile(2917, 10140, 1));

        enterPrisonWithParrot = new ObjectStep(ObjectID.SECRET_DOOR, new RSTile(2828, 3647, 0), "Take the parrot to the Troll Stronghold prison, and hide it in the rack there.", parrot, climbingBoots);

        parrotOnRack = new ObjectStep(ObjectID.RACK_3821, new RSTile(2829, 10097, 0), "Use the parrot on a rack in the prison.", parrotHighlighted);
        // parrotOnRack.setWorldMapPoint(new RSTile(2830, 10098, 1));


        //   enterStrongholdWithParrot.addSubSteps(leaveEadgarsCaveWithParrot, goDownNorthStairsWithParrot, goDownToPrisonWithParrot, parrotOnRack, enterPrisonWithParrot);

        talkToTegid = new NPCStep(NpcID.TEGID, new RSTile(2910, 3417, 0), robe, grain10, rawChicken5, logs2, climbingBoots, ranarrPotionUnf, tinderbox, pestleAndMortar);
        talkToTegid.addDialogStep("Sanfew won't be happy...");
        enterEadgarsCaveWithItems = new ObjectStep(ObjectID.CAVE_ENTRANCE_3759, new RSTile(2893, 3673, 0),
                "Enter", robe, grain10, rawChicken5, logs2, climbingBoots, ranarrPotionUnf, tinderbox, pestleAndMortar);
        talkToEadgarWithItems = new NPCStep(NpcID.EADGAR, new RSTile(2891, 10086, 2), robe, grain10, rawChicken5, logs2, climbingBoots, ranarrPotionUnf, tinderbox, pestleAndMortar);

        //  talkToEadgarWithItems.addSubSteps(enterEadgarsCaveWithItems);

        leaveEadgarsCaveForThistle = new ObjectStep(ObjectID.CAVE_EXIT_3760, new RSTile(2893, 10073, 2), "Go pick a troll thistle from outside Eadgar's cave.", ranarrPotionUnf);
        leaveEadgarsCaveForThistle.addDialogStep("No thanks, Eadgar.");

        //TODO use method defined at the bottom for this
        //   pickThistle = new NPCStep(NpcID.THISTLE, new RSTile(2887, 3675, 0), "Pick", ranarrPotionUnf, logs1, tinderbox);
        //  pickThistle.addSubSteps(leaveEadgarsCaveForThistle);

        //   lightFire = new DetailedQuestStep("Light a fire then use the troll thistle on it.", logHighlight, tinderboxHighlight);

        useThistleOnFire = new UseItemOnObjectStep(ItemID.THISTLE, ObjectID.FIRE_26185, new RSTile(2887, 3675, 0),
                !Inventory.contains(ItemID.THISTLE), thistle);

        useThistleOnTrollFire = new ObjectStep(ObjectID.FIRE, new RSTile(2849, 3678, 0),
                "Use the troll thistle on one of the fire outside the Troll Stronghold.", thistle);


        // useThistleOnFire.addSubSteps(useThistleOnTrollFire);

        grindThistle = new UseItemOnItemStep(ItemID.PESTLE_AND_MORTAR, ItemID.DRIED_THISTLE,
                !Inventory.contains(ItemID.DRIED_THISTLE),
                pestleAndMortar, driedThistle);

        useGroundThistleOnRanarr = new UseItemOnItemStep(ItemID.PESTLE_AND_MORTAR, ItemID.GROUND_THISTLE,
                !Inventory.contains(ItemID.GROUND_THISTLE),
                groundThistle, ranarrUnfHighlight);

        enterEadgarsCaveWithTrollPotion = new ObjectStep(ObjectID.CAVE_ENTRANCE_3759, new RSTile(2893, 3673, 0), "Bring Eadgar the troll potion.", trollPotion);
        giveTrollPotionToEadgar = new NPCStep(NpcID.EADGAR, new RSTile(2891, 10086, 2), trollPotion);
        //    giveTrollPotionToEadgar.addSubSteps(enterEadgarsCaveWithTrollPotion);

        leaveEadgarsCaveForParrot = new ObjectStep(ObjectID.CAVE_EXIT_3760, new RSTile(2893, 10073, 2), "Get the parrot from the Troll Stronghold prison.");
        enterStrongholdForParrot = new ObjectStep(ObjectID.STRONGHOLD, new RSTile(2839, 3690, 0), "Get the parrot from the Troll Stronghold prison.");

        goDownNorthStairsForParrot = new ObjectStep(ObjectID.STONE_STAIRCASE_3789, new RSTile(2844, 10109, 2), "Get the parrot from the Troll Stronghold prison.");
        // goDownNorthStairsForParrot.setWorldMapPoint(new RSTile(2971, 10172, 1));

        goDownToPrisonForParrot = new ObjectStep(ObjectID.STONE_STAIRCASE_3789, new RSTile(2853, 10108, 1), "Get the parrot from the Troll Stronghold prison.");
        // goDownToPrisonForParrot.setWorldMapPoint(new RSTile(2917, 10140, 1));

        enterPrisonForParrot = new ObjectStep(ObjectID.SECRET_DOOR, new RSTile(2828, 3647, 0), "Get the parrot from the Troll Stronghold prison.", climbingBoots);

        getParrotFromRack = new ObjectStep(ObjectID.RACK_3821, new RSTile(2829, 10097, 0), "Get the parrot from the rack in the prison.");
        //  getParrotFromRack.setWorldMapPoint(new RSTile(2830, 10098, 1));

        //     enterStrongholdForParrot.addSubSteps(leaveEadgarsCaveForParrot, goDownNorthStairsForParrot, goDownToPrisonForParrot, enterPrisonForParrot, getParrotFromRack);

        leavePrisonWithParrot = new ObjectStep(ObjectID.STONE_STAIRCASE, new RSTile(2853, 10107, 0), "Return to Eadgar with the parrot.", trainedParrot);
        // leavePrisonWithParrot.setWorldMapPoint(new RSTile(2853, 10106, 1));

        goUpToTopFloorWithParrot = new ObjectStep(ObjectID.STONE_STAIRCASE, new RSTile(2843, 10109, 1), "Return to Eadgar with the parrot.", trainedParrot);
        //   goUpToTopFloorWithParrot.setWorldMapPoint(new RSTile(2906, 10140, 1));

        leaveStrongholdWithParrot = new ObjectStep(ObjectID.EXIT_3772, new RSTile(2838, 10091, 2), "Return to Eadgar with the parrot.", trainedParrot);
        //   leaveStrongholdWithParrot.setWorldMapPoint(new RSTile(2965, 10155, 1));

        enterEadgarCaveWithTrainedParrot = new ObjectStep(ObjectID.CAVE_ENTRANCE_3759, new RSTile(2893, 3673, 0), "Return to Eadgar with the parrot.", trainedParrot);

        talkToEadgarWithTrainedParrot = new NPCStep(NpcID.EADGAR, new RSTile(2891, 10086, 2), trainedParrot);
        //   leaveStrongholdWithParrot.addSubSteps(leavePrisonWithParrot, goUpToTopFloorWithParrot, leaveStrongholdWithParrot, enterEadgarCaveWithTrainedParrot, talkToEadgarWithTrainedParrot);

        leaveEadgarsCaveWithScarecrow = new ObjectStep(ObjectID.CAVE_EXIT_3760, new RSTile(2893, 10073, 2), "Take the fake man to Burntmeat.", fakeMan);
        leaveEadgarsCaveWithScarecrow.addDialogStep("No thanks, Eadgar.");
        enterStrongholdWithScarecrow = new ObjectStep(ObjectID.STRONGHOLD, new RSTile(2839, 3690, 0), "Take the fake man to Burntmeat.", fakeMan);
        goDownSouthStairsWithScarecrow = new ObjectStep(ObjectID.STONE_STAIRCASE_3789, new RSTile(2844, 10052, 2), "Take the fake man to Burntmeat.", fakeMan);
        //  goDownSouthStairsWithScarecrow.setWorldMapPoint(new RSTile(2971, 10115, 1));
        talkToCookWithScarecrow = new NPCStep(NpcID.BURNTMEAT, new RSTile(2845, 10057, 1), fakeMan);
        //  talkToCookWithScarecrow.setWorldMapPoint(new RSTile(2911, 10087, 1));

        talkToBurntmeat = new NPCStep(NpcID.BURNTMEAT, new RSTile(2845, 10057, 1), "Talk to Burntmeat in the Troll Stronghold kitchen.");
        //  talkToBurntmeat.setWorldMapPoint(new RSTile(2911, 10087, 1));
        talkToBurntmeat.addDialogStep("So, where can I get some goutweed?");

        //   enterStrongholdWithScarecrow.addSubSteps(leaveEadgarsCaveWithScarecrow, goDownSouthStairsWithScarecrow, talkToCookWithScarecrow, talkToBurntmeat);

        searchDrawers = new ObjectStep(ObjectID.KITCHEN_DRAWERS, new RSTile(2853, 10050, 1), "Search the kitchen drawers south east of Burntmeat.");
        // searchDrawers.addAlternateObjects(ObjectID.KITCHEN_DRAWERS_3817);

        goDownToStoreroom = new ObjectStep(ObjectID.STONE_STAIRCASE_3789, new RSTile(2853, 10061, 1), "Go down to the storeroom from the Troll Stronghold kitchen.");

        enterStoreroomDoor = new ObjectStep(ObjectID.STOREROOM_DOOR, new RSTile(2869, 10085, 0), "Enter the storeroom.", storeroomKey);

        getGoutweed = new ObjectStep(ObjectID.GOUTWEED_CRATE, new RSTile(2857, 10074, 0), "Search the goutweed crates for goutweed. You'll need to avoid the troll guards or you'll be kicked out and take damage.");

        returnUpToSanfew = new ObjectStep(ObjectID.STAIRCASE_16671, new RSTile(2899, 3429, 0), "If you wish to do Dream Mentor or Dragon Slayer II, grab two more goutweed. Afterwards, return to Sanfew upstairs in the Taverley herblore store.", goutweed);
        returnToSanfew = new NPCStep(NpcID.SANFEW, new RSTile(2899, 3429, 1), goutweed);
        returnToSanfew.addDialogStep("Ask general questions.");
        //  returnToSanfew.addSubSteps(returnUpToSanfew);
    }


    public List<ItemRequirement> getItemRequirements() {
        ArrayList<ItemRequirement> reqs = new ArrayList<>();
        reqs.add(climbingBootsOr12Coins);
        reqs.add(vodka);
        reqs.add(pineappleChunks);
        reqs.add(logs2);
        reqs.add(grain10);
        reqs.add(rawChicken5);
        reqs.add(tinderbox);
        reqs.add(pestleAndMortar);
        reqs.add(ranarrPotionUnf);
        return reqs;
    }

    public List<ItemRequirement> getItemRecommended() {
        ArrayList<ItemRequirement> reqs = new ArrayList<>();
        reqs.add(ardougneTeleport);
        return reqs;
    }

 /*   @Override
    public QuestPointReward getQuestPointReward()
    {
        return new QuestPointReward(1);
    }

    @Override
    public List<ExperienceReward> getExperienceRewards()
    {
        return Collections.singletonList(new ExperienceReward(Skill.HERBLORE, 11000));
    }
*/

    public void pickThistleStep() {
        setupConditions();
        if (inEadgarsCave.check()) {
            leaveEadgarsCaveForThistle.execute();
        } else {
            Optional<Npc> bestInteractable = Query.npcs().idEquals(NpcID.THISTLE).findBestInteractable();
            if (bestInteractable.map(t -> t.interact("Pick")).orElse(false)) {
                Timer.waitCondition(() -> Inventory.contains(ItemID.THISTLE), 3500, 5000);
            }
        }
        //light fire
        if (Utils.useItemOnItem(ItemID.TINDERBOX, ItemID.LOGS)) {
            RSTile playerTile = Player.getPosition();
            Timer.waitCondition(() -> !Player.getPosition().equals(playerTile), 7000, 9000);
        }
    }


    public List<Requirement> getGeneralRequirements() {
        ArrayList<Requirement> req = new ArrayList<>();
        // req.add(new QuestRequirement(QuestHelperQuest.DRUIDIC_RITUAL, QuestState.FINISHED));
        //  req.add(new QuestRequirement(QuestHelperQuest.TROLL_STRONGHOLD, QuestState.FINISHED));
        req.add(new SkillRequirement(Skills.SKILLS.HERBLORE, 31));
        return req;
    }

    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return false;
    }

    @Override
    public void execute() {

    }

    @Override
    public String questName() {
        return "Eadgar's Ruse";
    }

    @Override
    public boolean checkRequirements() {
        return false;
    }


    @Override
    public List<ItemRequirement> getBuyList() {
        return null;
    }
}
