package scripts.QuestPackages.LunarDiplomacy;

import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.MyPlayer;
import org.tribot.script.sdk.Quest;
import scripts.ItemID;
import scripts.NpcID;
import scripts.ObjectID;
import scripts.QuestSteps.*;
import scripts.Requirements.*;
import scripts.Requirements.Items.ItemCollections;
import scripts.Requirements.Util.ConditionalStep;
import scripts.Requirements.Util.Conditions;
import scripts.Requirements.Util.LogicType;
import scripts.Requirements.Util.Operation;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LunarDiplomacy implements QuestTask {


    //Items Required
    ItemRequirement sealOfPassage, bullseyeLantern, bullseyeLanternLit, emeraldLantern, emeraldLanternLit, emeraldLens,
            bullseyeLanternHighlighted, tinderboxHighlighted, emeraldLensHighlighted, emeraldLanternLitHighlighted, suqahTooth,
            groundTooth, marrentilPotion, guamPotion, guamMarrentilPotion, guamMarrentilPotionHighlighted, sleepPotion, specialVial,
            specialVialHighlighted, waterVial, guam, marrentill, pestle, airTalisman, waterTalisman, earthTalisman, fireTalisman,
            dramenStaff, dramenStaffHighlighted, lunarStaffP1, lunarStaffP1Highlighted, lunarStaffP2, lunarStaffP2Highlighted,
            lunarStaffP3, lunarStaffP3Highlighted, lunarStaff, pickaxe, hammer, needle, thread, coins400, spade, lunarOre,
            lunarBar, tiara, helm, amulet, ring, cape, torso, gloves, boots, legs, suqahHide4, kindling, helmEquipped, bodyEquipped,
            legsEquipped, bootsEquipped, glovesEquipped, cloakEquipped, amuletEquipped, ringEquipped, lunarStaffEquipped, soakedKindling,
            sleepPotionHighlighted, soakedKindlingHighlighted, sealOfPassageEquipped;

    //Items Recommended
    ItemRequirement combatRunes, combatGear;

    Requirement atBaseOfStairs, onCoveF1, onBoatF0, onBoatF1, onBoatF2, onBoatF3, revealedCannon, revealedChart, revealedChest,
            revealedPillar, revealedCrate, onBoatLunar, onLunarDock, onLunarIsle, inYagaHouse, toothNearby,
            inAirAltar, inEarthAltar, inWaterAltar, inFireAltar, inLunarMine, talkedToSelene, talkedToMeteora, talkedToRimae, tiaraNearby,
            hadClothes, hadHelm, hadCape, hadAmulet, hadTorso, hadGloves, hadLegs, hadRing, hadBoots,
            litBrazier, inCentreOfDream, inChanceDream, inNumbersDream, inTreeDream, inMemoryDream, inRaceDream,
            inMimicDream, startedNumberChallenge, finishedChance, finishedNumbers, finishedTree, finishedMemory, finishedRace,
            finishedMimic, needToTalkAtMiddle, doingTreeChallenge, startedRaceChallenge, inFightArena;

    QuestStep talkToLokar, talkToBrundt, talkToLokarAgain, travelWithLokar, climbLadder, boardShip,
            talkToBentley, climbDownSouthStairs, talkToJack, climbUpSouthStairs, talkToBentleyAfterJack,
            goDownToJackAgain, talkToJackAgain, goUpToShultz, talkToShultz, goDownToBurns1, goDownToBurns2,
            goDownToBurns3, talkToBurns, goUpToLee1, goUpToLee2, goUpToLee3, talkToLee, goDownToDavey, talkToDavey,
            goUpToCabinBoy, talkToCabinBoy, extinguishLantern, replaceLens, lightLantern, getLensAndBullseye,
            goUpToCannon1, goUpToCannon2, goUpToCannon3, useLanternOnCannon, goDownToChart, goUpToChart1, goUpToChart2,
            useLanternOnChart, goDownToChest1, goDownToChest2, goDownToChest3, useLanternOnChest, useLanternOnPillar,
            useLanternOnCrate, goUpToSail1, goUpToSail2, goDownToSail, goDownToIsle1, goDownToIsle2, enterTown, talkToOneiromancer,
            enterChickenHouse, talkToYaga, leaveChickenHouse, fillVial, addGuam, addMarrentil, addGuamToMarrentill, grindTooth, pickUpTooth,
            addToothToPotion, bringPotionToOneiromancer, enterFireAltar, enterWaterAltar, enterAirAltar, enterEarthAltar, useOnEarth, useOnFire,
            useOnAir, useOnWater, talkToOneiromancerWithStaff, enterMine, smeltBar, makeHelmet, talkToPauline, talkToMeteora, talkToSelene,
            returnTiaraToMeteora, digForRing, makeClothes, bringItemsToOneiromancer, talkToRimae, pickUpTiara, useVialOnKindling, lightBrazier,
            useKindlingOnBrazier, talkToEthereal, doMemoryChallenge,
            startTreeChallenge, doRaceChallenge, startNumber, startRace, doTreeChallenge, talkWithEtherealToFight, leaveLecturn, finishQuest;

    NPCStep talkToBentleyToSail, killSuqahForTooth, killSuqahForTiara, fightMe;

    ObjectStep mineOre;

    // DetailedOwnerStep doNumberChallenge, doMimicChallenge, doChanceChallenge;

    ConditionalStep returnToMakePotion, returnToTalkToYaga, enteringTheIsland, boardingTheBoat, setSail, returnToOneWithPotion, returnWithStaff, makingHelm,
            gettingRing, gettingCape, gettingAmulet, gettingClothes;

    ObjectStep goToNumbers = new ObjectStep(ObjectID.PLATFORM_16633, new RSTile(1768, 5080, 2));
    ObjectStep goToMimic = new ObjectStep(ObjectID.PLATFORM_16632, new RSTile(1765, 5079, 2));
    ObjectStep goToRace = new ObjectStep(ObjectID.PLATFORM_16634, new RSTile(1770, 5088, 2));
    ObjectStep goToMemory = new ObjectStep(ObjectID.PLATFORM_16636, new RSTile(1751, 5095, 2));
    ObjectStep goToTrees = new ObjectStep(ObjectID.PLATFORM_16635, new RSTile(1764, 5098, 2));
    ObjectStep goToChance = new ObjectStep(ObjectID.PLATFORM_16637, new RSTile(1751, 5080, 2));

    //RSArea()s
    RSArea baseOfStairs, coveF1, boatF0, boatF1, boatF2, boatF3, boatLunar1, boatLunar2, lunarDock, lunarIsle, yagaHouse, airAltar,
            waterAltar, earthAltar, fireAltar, lunarMine, centreOfDream, chanceDream, numbersDream, treeDream, memoryDream, raceDream,
            mimicDream, fightArena;

    public Map<Integer, QuestStep> loadSteps() {
        loadRSAreas();
        setupItemRequirements();
        setupConditions();
        setupSteps();
        setupConditionalSteps();

        Map<Integer, QuestStep> steps = new HashMap<>();

        steps.put(0, talkToLokar);
        steps.put(10, talkToBrundt);
        steps.put(20, talkToLokarAgain);

        ConditionalStep talkingToBentley = new ConditionalStep(boardingTheBoat);
        talkingToBentley.addStep(onBoatF2, talkToBentley);
        steps.put(30, talkingToBentley);

        ConditionalStep talkingToJack = new ConditionalStep(boardingTheBoat);
        talkingToJack.addStep(onBoatF1, talkToJack);
        talkingToJack.addStep(onBoatF2, climbDownSouthStairs);
        steps.put(40, talkingToJack);

        ConditionalStep talkingToBentleyAfterJack = new ConditionalStep(boardingTheBoat);
        talkingToBentleyAfterJack.addStep(onBoatF1, climbUpSouthStairs);
        talkingToBentleyAfterJack.addStep(onBoatF2, talkToBentleyAfterJack);
        steps.put(45, talkingToBentleyAfterJack);

        ConditionalStep talkingToJackAgain = new ConditionalStep(boardingTheBoat);
        talkingToJackAgain.addStep(onBoatF1, talkToJackAgain);
        talkingToJackAgain.addStep(onBoatF2, goDownToJackAgain);
        steps.put(50, talkingToJackAgain);

        ConditionalStep talkingToShultz = new ConditionalStep(boardingTheBoat);
        talkingToShultz.addStep(onBoatF2, talkToShultz);
        talkingToShultz.addStep(onBoatF1, goUpToShultz);
        steps.put(60, talkingToShultz);

        ConditionalStep talkingToBurns = new ConditionalStep(boardingTheBoat);
        talkingToBurns.addStep(onBoatF3, goDownToBurns1);
        talkingToBurns.addStep(onBoatF2, goDownToBurns2);
        talkingToBurns.addStep(onBoatF1, goDownToBurns3);
        talkingToBurns.addStep(onBoatF0, talkToBurns);
        steps.put(70, talkingToBurns);

        ConditionalStep talkingToLee = new ConditionalStep(boardingTheBoat);
        talkingToLee.addStep(onBoatF3, talkToLee);
        talkingToLee.addStep(onBoatF2, goUpToLee3);
        talkingToLee.addStep(onBoatF1, goUpToLee2);
        talkingToLee.addStep(onBoatF0, goUpToLee1);
        steps.put(80, talkingToLee);

        ConditionalStep talkingToDavey = new ConditionalStep(boardingTheBoat);
        talkingToDavey.addStep(onBoatF3, goDownToDavey);
        talkingToDavey.addStep(onBoatF2, talkToDavey);
        steps.put(90, talkingToDavey);

        ConditionalStep talkingToCabinBoyAgain = new ConditionalStep(boardingTheBoat);
        talkingToCabinBoyAgain.addStep(onBoatF3, talkToCabinBoy);
        talkingToCabinBoyAgain.addStep(onBoatF2, goUpToCabinBoy);
        steps.put(100, talkingToCabinBoyAgain);

        ConditionalStep removingSymbols = new ConditionalStep(getLensAndBullseye);
        removingSymbols.addStep(new Conditions(emeraldLanternLit, onBoatF0, revealedCannon, revealedChart, revealedChest,
                revealedPillar), useLanternOnCrate);
        removingSymbols.addStep(new Conditions(emeraldLanternLit, onBoatF0, revealedCannon, revealedChart, revealedChest), useLanternOnPillar);
        removingSymbols.addStep(new Conditions(emeraldLanternLit, onBoatF3, revealedCannon, revealedChart), goDownToChest1);
        removingSymbols.addStep(new Conditions(emeraldLanternLit, onBoatF2, revealedCannon, revealedChart), goDownToChest2);
        removingSymbols.addStep(new Conditions(emeraldLanternLit, onBoatF1, revealedCannon, revealedChart), goDownToChest3);
        removingSymbols.addStep(new Conditions(emeraldLanternLit, onBoatF0, revealedCannon, revealedChart), useLanternOnChest);

        removingSymbols.addStep(new Conditions(emeraldLanternLit, onBoatF3, revealedCannon), goDownToChart);
        removingSymbols.addStep(new Conditions(emeraldLanternLit, onBoatF2, revealedCannon), useLanternOnChart);
        removingSymbols.addStep(new Conditions(emeraldLanternLit, onBoatF1, revealedCannon), goUpToChart2);
        removingSymbols.addStep(new Conditions(emeraldLanternLit, onBoatF0, revealedCannon), goUpToChart1);

        removingSymbols.addStep(new Conditions(emeraldLanternLit, onBoatF3), useLanternOnCannon);
        removingSymbols.addStep(new Conditions(emeraldLanternLit, onBoatF2), goUpToCannon3);
        removingSymbols.addStep(new Conditions(emeraldLanternLit, onBoatF1), goUpToCannon2);
        removingSymbols.addStep(new Conditions(emeraldLanternLit, onBoatF0), goUpToCannon1);

        removingSymbols.addStep(emeraldLanternLit, boardingTheBoat);
        removingSymbols.addStep(emeraldLantern, lightLantern);
        removingSymbols.addStep(bullseyeLantern, replaceLens);
        removingSymbols.addStep(bullseyeLanternLit, extinguishLantern);
        steps.put(110, removingSymbols);
        steps.put(112, removingSymbols);
        steps.put(114, removingSymbols);
        steps.put(116, removingSymbols);
        steps.put(118, removingSymbols);

        steps.put(120, setSail);

        ConditionalStep enteringTheTown = new ConditionalStep(enteringTheIsland);
        enteringTheTown.addStep(onLunarIsle, enterTown);
        steps.put(125, enteringTheTown);

        ConditionalStep talkingToTheOneirmancer = new ConditionalStep(enteringTheIsland);
        talkingToTheOneirmancer.addStep(onLunarIsle, talkToOneiromancer);
        steps.put(130, talkingToTheOneirmancer);

        ConditionalStep talkingToYaga = new ConditionalStep(returnToTalkToYaga);
        talkingToYaga.addStep(inYagaHouse, talkToYaga);
        talkingToYaga.addStep(onLunarIsle, enterChickenHouse);
        steps.put(135, talkingToYaga);

        ConditionalStep makingThePotion = new ConditionalStep(fillVial);
        makingThePotion.addStep(new Conditions(onLunarIsle, sleepPotion), bringPotionToOneiromancer);
        makingThePotion.addStep(new Conditions(sleepPotion), returnToOneWithPotion);
        makingThePotion.addStep(new Conditions(groundTooth, guamMarrentilPotion), addToothToPotion);
        makingThePotion.addStep(new Conditions(suqahTooth, guamMarrentilPotion), grindTooth);
        makingThePotion.addStep(new Conditions(toothNearby, guamMarrentilPotion), pickUpTooth);
        makingThePotion.addStep(new Conditions(guamMarrentilPotion, onLunarIsle), killSuqahForTooth);
        makingThePotion.addStep(guamMarrentilPotion, returnToMakePotion);
        makingThePotion.addStep(marrentilPotion, addGuamToMarrentill);
        makingThePotion.addStep(guamPotion, addMarrentil);
        makingThePotion.addStep(waterVial, addGuam);
        makingThePotion.addStep(inYagaHouse, leaveChickenHouse);
        steps.put(140, makingThePotion);

        ConditionalStep makingTheLunarStaff = new ConditionalStep(enterAirAltar);
        makingTheLunarStaff.addStep(new Conditions(onLunarIsle, lunarStaff), talkToOneiromancerWithStaff);
        makingTheLunarStaff.addStep(lunarStaff, returnWithStaff);
        makingTheLunarStaff.addStep(new Conditions(lunarStaffP3, inEarthAltar), useOnEarth);
        makingTheLunarStaff.addStep(lunarStaffP3, enterEarthAltar);
        makingTheLunarStaff.addStep(new Conditions(lunarStaffP2, inWaterAltar), useOnWater);
        makingTheLunarStaff.addStep(lunarStaffP2, enterWaterAltar);
        makingTheLunarStaff.addStep(new Conditions(lunarStaffP1, inFireAltar), useOnFire);
        makingTheLunarStaff.addStep(lunarStaffP1, enterFireAltar);
        makingTheLunarStaff.addStep(inAirAltar, useOnAir);
        steps.put(145, makingTheLunarStaff);

        ConditionalStep gettingRestOfEquipment = new ConditionalStep(makingHelm);
        gettingRestOfEquipment.addStep(new Conditions(hadHelm, hadCape, hadAmulet, hadRing, hadClothes), bringItemsToOneiromancer);
        gettingRestOfEquipment.addStep(new Conditions(hadHelm, hadCape, hadAmulet, hadRing), gettingClothes);
        gettingRestOfEquipment.addStep(new Conditions(hadHelm, hadCape, hadAmulet), gettingRing);
        gettingRestOfEquipment.addStep(new Conditions(hadHelm, hadCape), gettingAmulet);
        gettingRestOfEquipment.addStep(hadHelm, gettingCape);
        steps.put(155, gettingRestOfEquipment);

        ConditionalStep enterDream = new ConditionalStep(useVialOnKindling);
        enterDream.addStep(new Conditions(soakedKindling, litBrazier), useKindlingOnBrazier);
        enterDream.addStep(soakedKindling, lightBrazier);
        steps.put(160, enterDream);

        ConditionalStep startingDream = new ConditionalStep(enterDream);
        startingDream.addStep(inCentreOfDream, talkToEthereal);
        steps.put(165, startingDream);

        //TODO add these back in
        ConditionalStep challenges = new ConditionalStep(enterDream);
        challenges.addStep(new Conditions(inCentreOfDream, needToTalkAtMiddle), talkToEthereal);
      //  challenges.addStep(new Conditions(inNumbersDream, startedNumberChallenge), doNumberChallenge);
      //  challenges.addStep(inMimicDream, doMimicChallenge);
        challenges.addStep(inNumbersDream, startNumber);
        //challenges.addStep(inChanceDream, doChanceChallenge);
        challenges.addStep(inMemoryDream, doMemoryChallenge);
        challenges.addStep(new Conditions(inTreeDream, doingTreeChallenge), doTreeChallenge);
        challenges.addStep(inTreeDream, startTreeChallenge);
        challenges.addStep(new Conditions(inRaceDream, startedRaceChallenge), doRaceChallenge);
        challenges.addStep(inRaceDream, startRace);
        challenges.addStep(new Conditions(inCentreOfDream, finishedRace, finishedNumbers, finishedMimic, finishedChance, finishedMemory, finishedTree), talkWithEtherealToFight);
        challenges.addStep(new Conditions(inCentreOfDream, finishedRace, finishedNumbers, finishedMimic, finishedChance, finishedMemory), goToTrees);
        challenges.addStep(new Conditions(inCentreOfDream, finishedRace, finishedNumbers, finishedMimic, finishedChance), goToMemory);
        challenges.addStep(new Conditions(inCentreOfDream, finishedRace, finishedNumbers, finishedMimic), goToChance);
        challenges.addStep(new Conditions(inCentreOfDream, finishedRace, finishedNumbers), goToMimic);
        challenges.addStep(new Conditions(inCentreOfDream, finishedRace), goToNumbers);
        challenges.addStep(inCentreOfDream, goToRace);
        steps.put(170, challenges);

        ConditionalStep fightingYourself = new ConditionalStep(enterDream);
        fightingYourself.addStep(inFightArena, fightMe);
        fightingYourself.addStep(inCentreOfDream, talkWithEtherealToFight);
        steps.put(175, fightingYourself);

        ConditionalStep reportingToOne = new ConditionalStep(finishQuest);
        reportingToOne.addStep(inCentreOfDream, leaveLecturn);
        steps.put(180, reportingToOne);

        return steps;
    }

    public void setupItemRequirements() {
        sealOfPassage = new ItemRequirement("Seal of passage", ItemID.SEAL_OF_PASSAGE);
        // sealOfPassage.setTooltip("You can get another from Brundt");

        sealOfPassageEquipped = new ItemRequirement(ItemID.SEAL_OF_PASSAGE, 1, true);
        //   sealOfPassageEquipped.setTooltip("You can get another from Brundt");

        bullseyeLantern = new ItemRequirement("Bullseye lantern", ItemID.BULLSEYE_LANTERN);
        bullseyeLantern.addAlternateItemIDs(ItemID.SAPPHIRE_LANTERN_4701);
        bullseyeLanternHighlighted = new ItemRequirement("Bullseye lantern", ItemID.BULLSEYE_LANTERN);
        bullseyeLanternHighlighted.addAlternateItemIDs(ItemID.SAPPHIRE_LANTERN_4701);

        bullseyeLanternLit = new ItemRequirement("Bullseye lantern", ItemID.BULLSEYE_LANTERN_4550);
        bullseyeLanternLit.addAlternateItemIDs(ItemID.SAPPHIRE_LANTERN_4702);

        emeraldLantern = new ItemRequirement("Emerald lantern", ItemID.EMERALD_LANTERN);


        emeraldLanternLit = new ItemRequirement("Emerald lantern", ItemID.EMERALD_LANTERN_9065);
        emeraldLanternLitHighlighted = new ItemRequirement("Emerald lantern", ItemID.EMERALD_LANTERN_9065);


        emeraldLens = new ItemRequirement("Emerald lens", ItemID.EMERALD_LENS);
        // emeraldLens.setTooltip("You can get another from the Cabin boy");

        emeraldLensHighlighted = new ItemRequirement("Emerald lens", ItemID.EMERALD_LENS);
        //   emeraldLensHighlighted.setTooltip("You can get another from the Cabin boy");


        tinderboxHighlighted = new ItemRequirement("Tinderbox", ItemID.TINDERBOX);


        guam = new ItemRequirement("Guam leaf", ItemID.GUAM_LEAF);

        marrentill = new ItemRequirement("Marrentill", ItemID.MARRENTILL);

        suqahTooth = new ItemRequirement("Suqah tooth", ItemID.SUQAH_TOOTH);

        groundTooth = new ItemRequirement("Ground tooth", ItemID.GROUND_TOOTH);

        specialVial = new ItemRequirement("Empty vial", ItemID.EMPTY_VIAL);
        //    specialVial.setTooltip("You can get another from Baba Yaga");

        specialVialHighlighted = new ItemRequirement("Empty vial", ItemID.EMPTY_VIAL);
        //  specialVialHighlighted.setTooltip("You can get another from Baba Yaga");

        waterVial = new ItemRequirement("Empty vial", ItemID.VIAL_OF_WATER_9086);

        //  waterVial.setTooltip("You can get another from Baba Yaga");

        guamPotion = new ItemRequirement("Guam vial", ItemID.GUAM_VIAL);

        marrentilPotion = new ItemRequirement("Marr vial", ItemID.MARR_VIAL);


        guamMarrentilPotion = new ItemRequirement("Guam-marr vial", ItemID.GUAMMARR_VIAL);
        guamMarrentilPotionHighlighted = new ItemRequirement("Guam-marr vial", ItemID.GUAMMARR_VIAL);


        sleepPotion = new ItemRequirement("Waking sleep vial", ItemID.WAKING_SLEEP_VIAL);
        sleepPotionHighlighted = new ItemRequirement("Waking sleep vial", ItemID.WAKING_SLEEP_VIAL);


        pestle = new ItemRequirement("Pestle and mortar", ItemID.PESTLE_AND_MORTAR);


        airTalisman = new ItemRequirement("Air talisman/tiara, or access via the Abyss", ItemID.AIR_TALISMAN);
        airTalisman.addAlternateItemIDs(ItemID.AIR_TIARA, ItemID.ELEMENTAL_TALISMAN);

        fireTalisman = new ItemRequirement("Fire talisman/tiara, or access via the Abyss", ItemID.FIRE_TALISMAN);
        fireTalisman.addAlternateItemIDs(ItemID.FIRE_TIARA, ItemID.ELEMENTAL_TALISMAN);

        earthTalisman = new ItemRequirement("Earth talisman/tiara, or access via the Abyss", ItemID.EARTH_TALISMAN);
        earthTalisman.addAlternateItemIDs(ItemID.EARTH_TIARA, ItemID.ELEMENTAL_TALISMAN);

        waterTalisman = new ItemRequirement("Water talisman/tiara, or access via the Abyss", ItemID.WATER_TALISMAN);
        waterTalisman.addAlternateItemIDs(ItemID.WATER_TIARA, ItemID.ELEMENTAL_TALISMAN);

        dramenStaff = new ItemRequirement("Dramen staff", ItemID.DRAMEN_STAFF);
        //dramenStaff.setTooltip("You can get another from under Entrana");
        dramenStaffHighlighted = new ItemRequirement("Dramen staff", ItemID.DRAMEN_STAFF);
        // dramenStaffHighlighted.setTooltip("You can get another from under Entrana");

        lunarStaffP1 = new ItemRequirement("Lunar staff - pt1", ItemID.LUNAR_STAFF__PT1);
        lunarStaffP1Highlighted = new ItemRequirement("Lunar staff - pt1", ItemID.LUNAR_STAFF__PT1);


        lunarStaffP2 = new ItemRequirement("Lunar staff - pt2", ItemID.LUNAR_STAFF__PT2);
        lunarStaffP2Highlighted = new ItemRequirement("Lunar staff - pt2", ItemID.LUNAR_STAFF__PT2);


        lunarStaffP3 = new ItemRequirement("Lunar staff - pt3", ItemID.LUNAR_STAFF__PT3);
        lunarStaffP3Highlighted = new ItemRequirement("Lunar staff - pt3", ItemID.LUNAR_STAFF__PT3);


        lunarStaff = new ItemRequirement("Lunar staff", ItemID.LUNAR_STAFF);

        pickaxe = new ItemRequirement("Any pickaxe", ItemCollections.getPickaxes());
        hammer = new ItemRequirement("Hammer", ItemCollections.getHammer());
        needle = new ItemRequirement("Needle", ItemID.NEEDLE);
        thread = new ItemRequirement("Thread", ItemID.THREAD);
        combatGear = new ItemRequirement("Combat gear", -1, -1);

        coins400 = new ItemRequirement(ItemID.COINS_995, 400);

        combatRunes = new ItemRequirement("Combat runes", -1, -1);

        spade = new ItemRequirement("Spade", ItemID.SPADE);

        lunarOre = new ItemRequirement("Lunar ore", ItemID.LUNAR_ORE);
        lunarBar = new ItemRequirement("Lunar bar", ItemID.LUNAR_BAR);

        tiara = new ItemRequirement("A special tiara", ItemID.A_SPECIAL_TIARA);

        helm = new ItemRequirement("Lunar helm", ItemID.LUNAR_HELM);
        amulet = new ItemRequirement("Lunar amulet", ItemID.LUNAR_AMULET);
        ring = new ItemRequirement("Lunar ring", ItemID.LUNAR_RING);
        cape = new ItemRequirement("Lunar cape", ItemID.LUNAR_CAPE);
        torso = new ItemRequirement("Lunar torso", ItemID.LUNAR_TORSO);
        gloves = new ItemRequirement("Lunar gloves", ItemID.LUNAR_GLOVES);
        boots = new ItemRequirement("Lunar boots", ItemID.LUNAR_BOOTS);
        legs = new ItemRequirement("Lunar legs", ItemID.LUNAR_LEGS);
        kindling = new ItemRequirement("Kindling", ItemID.KINDLING);
        // kindling.setTooltip("You can get any of these items from the Oneiromancer");

        soakedKindling = new ItemRequirement("Soaked kindling", ItemID.SOAKED_KINDLING);
        soakedKindlingHighlighted = new ItemRequirement("Soaked kindling", ItemID.SOAKED_KINDLING);

        helmEquipped = new ItemRequirement(ItemID.LUNAR_HELM, 1, true);
        bodyEquipped = new ItemRequirement(ItemID.LUNAR_TORSO, 1, true);
        legsEquipped = new ItemRequirement(ItemID.LUNAR_LEGS, 1, true);
        bootsEquipped = new ItemRequirement(ItemID.LUNAR_BOOTS, 1, true);
        glovesEquipped = new ItemRequirement(ItemID.LUNAR_GLOVES, 1, true);
        cloakEquipped = new ItemRequirement(ItemID.LUNAR_CAPE, 1, true);
        amuletEquipped = new ItemRequirement(ItemID.LUNAR_AMULET, 1, true);
        ringEquipped = new ItemRequirement(ItemID.LUNAR_RING, 1, true);
        lunarStaffEquipped = new ItemRequirement(ItemID.LUNAR_STAFF, 1, true);

        suqahHide4 = new ItemRequirement("Suqah hide", ItemID.SUQAH_HIDE, 4);
        suqahHide4.addAlternateItemIDs(ItemID.SUQAH_LEATHER, ItemID.LUNAR_BOOTS, ItemID.LUNAR_LEGS, ItemID.LUNAR_GLOVES, ItemID.LUNAR_TORSO);
    }

    public void loadRSAreas() {
        baseOfStairs = new RSArea(new RSTile(2212, 3794, 0), new RSTile(2214, 3795, 0));
        coveF1 = new RSArea(new RSTile(2212, 3796, 1), new RSTile(2215, 3810, 1));
        boatF0 = new RSArea(new RSTile(2216, 3784, 0), new RSTile(2235, 3820, 0));
        boatF1 = new RSArea(new RSTile(2216, 3784, 1), new RSTile(2235, 3820, 1));
        boatF2 = new RSArea(new RSTile(2214, 3784, 2), new RSTile(2235, 3820, 2));
        boatF3 = new RSArea(new RSTile(2216, 3784, 3), new RSTile(2235, 3820, 3));

        boatLunar1 = new RSArea(new RSTile(2128, 3890, 2), new RSTile(2135, 3903, 2));
        boatLunar2 = new RSArea(new RSTile(2133, 3888, 0), new RSTile(2147, 3919, 3));

        lunarDock = new RSArea(new RSTile(2116, 3889, 1), new RSTile(2131, 3902, 1));
        lunarIsle = new RSArea(new RSTile(2048, 3841, 0), new RSTile(2174, 3968, 0));

        yagaHouse = new RSArea(new RSTile(2449, 4645, 0), new RSTile(2453, 4649, 0));

        airAltar = new RSArea(new RSTile(2832, 4826, 0), new RSTile(2855, 4849, 0));
        earthAltar = new RSArea(new RSTile(2644, 4820, 0), new RSTile(2675, 4856, 0));
        fireAltar = new RSArea(new RSTile(2570, 4856, 0), new RSTile(2615, 4815, 0));
        waterAltar = new RSArea(new RSTile(2704, 4820, 0), new RSTile(2733, 4846, 0));
        lunarMine = new RSArea(new RSTile(2300, 10313, 2), new RSTile(2370, 10354, 2));
        centreOfDream = new RSArea(new RSTile(1748, 5076, 2), new RSTile(1771, 5099, 2));
        memoryDream = new RSArea(new RSTile(1730, 5078, 2), new RSTile(1741, 5113, 2));
        chanceDream = new RSArea(new RSTile(1730, 5059, 2), new RSTile(1741, 5070, 2));
        mimicDream = new RSArea(new RSTile(1765, 5056, 2), new RSTile(1774, 5071, 2));
        raceDream = new RSArea(new RSTile(1781, 5078, 2), new RSTile(1788, 5109, 2));
        treeDream = new RSArea(new RSTile(1749, 5108, 2), new RSTile(1770, 5115, 2));
        numbersDream = new RSArea(new RSTile(1780, 5060, 2), new RSTile(1788, 5068, 2));
        fightArena = new RSArea(new RSTile(1812, 5076, 2), new RSTile(1840, 5099, 2));
    }

    public void setupConditions() {
        atBaseOfStairs = new AreaRequirement(baseOfStairs);
        onCoveF1 = new AreaRequirement(coveF1);

        onBoatF0 = new AreaRequirement(boatF0);
        onBoatF1 = new AreaRequirement(boatF1);
        onBoatF2 = new AreaRequirement(boatF2);
        onBoatF3 = new AreaRequirement(boatF3);

        onBoatLunar = new AreaRequirement(boatLunar1, boatLunar2);
        onLunarDock = new AreaRequirement(lunarDock);
        onLunarIsle = new AreaRequirement(lunarIsle);
        inLunarMine = new AreaRequirement(lunarMine);
        inYagaHouse = new AreaRequirement(yagaHouse);

        inAirAltar = new AreaRequirement(airAltar);
        inEarthAltar = new AreaRequirement(earthAltar);
        inFireAltar = new AreaRequirement(fireAltar);
        inWaterAltar = new AreaRequirement(waterAltar);

        inFightArena = new AreaRequirement(fightArena);

        revealedPillar = new VarbitRequirement(2431, 2);
        revealedCannon = new VarbitRequirement(2432, 2);
        revealedCrate = new VarbitRequirement(2433, 2);
        revealedChart = new VarbitRequirement(2434, 2);
        revealedChest = new VarbitRequirement(2435, 2);

      //  toothNearby = new ItemOnTileRequirement(suqahTooth);

        talkedToSelene = new VarbitRequirement(2445, 1);
        talkedToMeteora = new VarbitRequirement(2446, 1);
        talkedToRimae = new VarbitRequirement(2447, 1);

       // tiaraNearby = new ItemOnTileRequirement(tiara);

       /* hadHelm = new Conditions(LogicType.OR, helm.alsoCheckBank(questBank), new VarbitRequirement(2436, 1));
        hadCape = new Conditions(LogicType.OR, cape.alsoCheckBank(questBank), new VarbitRequirement(2437, 1));
        hadAmulet = new Conditions(LogicType.OR, amulet.alsoCheckBank(questBank), new VarbitRequirement(2438, 1));
        hadTorso = new Conditions(LogicType.OR, torso.alsoCheckBank(questBank), new VarbitRequirement(2439, 1));
        hadGloves = new Conditions(LogicType.OR, gloves.alsoCheckBank(questBank), new VarbitRequirement(2441, 1));
        hadBoots = new Conditions(LogicType.OR, boots.alsoCheckBank(questBank), new VarbitRequirement(2440, 1));
        hadLegs = new Conditions(LogicType.OR, legs.alsoCheckBank(questBank), new VarbitRequirement(2442, 1));
        hadRing = new Conditions(LogicType.OR, ring.alsoCheckBank(questBank), new VarbitRequirement(2443, 1));
        hadClothes = new Conditions(hadBoots, hadTorso, hadGloves, hadLegs);*/

        litBrazier = new VarbitRequirement(2430, 1);

        inCentreOfDream = new AreaRequirement(centreOfDream);
        inChanceDream = new AreaRequirement(chanceDream);
        inNumbersDream = new AreaRequirement(numbersDream);
        inTreeDream = new AreaRequirement(treeDream);
        inMemoryDream = new AreaRequirement(memoryDream);
        inRaceDream = new AreaRequirement(raceDream);
        inMimicDream = new AreaRequirement(mimicDream);

        doingTreeChallenge = new VarbitRequirement(3184, 1);
        startedRaceChallenge = new VarbitRequirement(2424, 1);

        startedNumberChallenge = new VarbitRequirement(2416, 1);

        needToTalkAtMiddle = new VarbitRequirement(2429, 1);

        finishedMimic = new VarbitRequirement(2405, 5, Operation.GREATER_EQUAL);
        finishedNumbers = new VarbitRequirement(2406, 6, Operation.GREATER_EQUAL);
        finishedTree = new VarbitRequirement(2407, 1, Operation.GREATER_EQUAL);
        finishedMemory = new VarbitRequirement(2408, 1, Operation.GREATER_EQUAL);
        finishedRace = new VarbitRequirement(2409, 1, Operation.GREATER_EQUAL);
        finishedChance = new VarbitRequirement(2410, 5, Operation.GREATER_EQUAL);
    }

    public void setupSteps() {
        talkToLokar = new NPCStep(NpcID.LOKAR_SEARUNNER_6648, new RSTile(2620, 3693, 0),
                "Talk to Lokar Searunner on Rellekka's docks.");
        talkToLokar.addDialogStep("You've been away from these parts a while?", "Why did you leave?", "Why not, I've always wondered what the state of my innards are!");

        talkToBrundt = new NPCStep(NpcID.BRUNDT_THE_CHIEFTAIN_9263, new RSTile(2658, 3667, 0),
                "Talk to Brundt in Rellekka's longhall.");
        talkToBrundt.addDialogStep("Ask about a Seal of Passage.");

        talkToLokarAgain = new NPCStep(NpcID.LOKAR_SEARUNNER_6648, new RSTile(2620, 3693, 0), sealOfPassage);
        talkToLokarAgain.addDialogStep("Arrr! Yar! Let's be on our way, yar!");

        travelWithLokar = new NPCStep(NpcID.LOKAR_SEARUNNER, new RSTile(2620, 3693, 0), sealOfPassage);
        travelWithLokar.addDialogStep("Go now.");

        climbLadder = new ObjectStep(ObjectID.LADDER_16960, new RSTile(2213, 3795, 0), "Climb up to the ship.");
        boardShip = new ObjectStep(ObjectID.LADDER_16959, new RSTile(2214, 3801, 1), "Climb up to the ship.");
        //climbLadder.addSubSteps(travelWithLokar, boardShip);

        talkToBentley = new NPCStep(NpcID.CAPTAIN_BENTLEY, new RSTile(2222, 3796, 2),
                sealOfPassageEquipped);
        talkToBentley.addDialogStep("Can we sail to Lunar Isle now?");

        climbDownSouthStairs = new ObjectStep(ObjectID.STAIRS_16947, new RSTile(2222, 3792, 2), "Go down to talk to 'Birds-Eye' Jack.");
        talkToJack = new NPCStep(NpcID.BIRDSEYE_JACK_3861, new RSTile(2222, 3788, 1), "Talk to 'Birds-Eye' Jack.");
        // climbDownSouthStairs.addSubSteps(talkToJack);

        climbUpSouthStairs = new ObjectStep(ObjectID.STAIRS_16945, new RSTile(2222, 3792, 1), "Go upstairs to talk to Bentley again.");
        talkToBentleyAfterJack = new NPCStep(NpcID.CAPTAIN_BENTLEY, new RSTile(2222, 3796, 2), "Talk to Captain Bentley again.");
        talkToBentleyAfterJack.addDialogStep("Perhaps it's the Navigator's fault?");
        // climbUpSouthStairs.addSubSteps(talkToBentleyAfterJack);

        goDownToJackAgain = new ObjectStep(ObjectID.STAIRS_16947, new RSTile(2222, 3792, 2), "Go down to talk to 'Birds-Eye' Jack again.");
        talkToJackAgain = new NPCStep(NpcID.BIRDSEYE_JACK_3861, new RSTile(2222, 3788, 1), "Talk to 'Birds-Eye' Jack.");
        //  goDownToJackAgain.addSubSteps(talkToJackAgain);

        goUpToShultz = new ObjectStep(ObjectID.STAIRS_16945, new RSTile(2222, 3792, 1), "Talk to 'Eagle-eye' Shultz on the north end of the ship.");
        talkToShultz = new NPCStep(NpcID.EAGLEEYE_SHULTZ, new RSTile(2224, 3813, 2), "Talk to 'Eagle-eye' Shultz on the north end of the ship.");
        //  goUpToShultz.addSubSteps(talkToShultz);

        goDownToBurns1 = new ObjectStep(ObjectID.STAIRS_16947, new RSTile(2227, 3794, 3), "Go to the bottom deck to talk to 'Beefy' Burns.");
        goDownToBurns2 = new ObjectStep(ObjectID.STAIRS_16947, new RSTile(2222, 3792, 2), "Go to the bottom deck to talk to 'Beefy' Burns.");
        goDownToBurns3 = new ObjectStep(ObjectID.STAIRS_16948, new RSTile(2225, 3808, 1), "Go to the bottom deck to talk to 'Beefy' Burns.");
        talkToBurns = new NPCStep(NpcID.BEEFY_BURNS, new RSTile(2221, 3788, 0), "Talk to 'Beefy' Burns in the south of the ship.");
        // goDownToBurns1.addSubSteps(goDownToBurns2, goDownToBurns3, talkToBurns);

        goUpToLee1 = new ObjectStep(ObjectID.STAIRS_16946, new RSTile(2225, 3808, 0), "Go to the top deck to talk to 'Lecherous' Lee.");
        goUpToLee2 = new ObjectStep(ObjectID.STAIRS_16945, new RSTile(2222, 3792, 1), "Go to the top deck to talk to 'Lecherous' Lee.");
        goUpToLee3 = new ObjectStep(ObjectID.STAIRS_16945, new RSTile(2227, 3794, 2), "Go to the top deck to talk to 'Lecherous' Lee.");

        talkToLee = new NPCStep(NpcID.LECHEROUS_LEE, new RSTile(2224, 3788, 3), "Talk to 'Lecherous' Lee.");
        //goUpToLee1.addSubSteps(goUpToLee2, goUpToLee3, talkToLee);

        goDownToDavey = new ObjectStep(ObjectID.STAIRS_16947, new RSTile(2227, 3794, 3), "Go down to the deck to talk to First mate 'Davey-boy'.");
        talkToDavey = new NPCStep(NpcID.FIRST_MATE_DAVEYBOY, new RSTile(2223, 3791, 2), "Talk to First mate 'Davey-boy'.");
        //  goDownToDavey.addSubSteps(talkToDavey);

        goUpToCabinBoy = new ObjectStep(ObjectID.STAIRS_16945, new RSTile(2227, 3794, 2), "Go up to the Cabin Boy.");
        talkToCabinBoy = new NPCStep(NpcID.CABIN_BOY, new RSTile(2225, 3789, 3), "Talk to the Cabin Boy.");
        //goUpToCabinBoy.addSubSteps(talkToCabinBoy);

        //TODO Do these
       // getLensAndBullseye = new DetailedQuestStep("Go get a bullseye lantern to add the emerald lens to.", bullseyeLantern, emeraldLens);
       // extinguishLantern = new DetailedQuestStep("Extinguish the bullseye lantern.", bullseyeLanternLit);
      ////  replaceLens = new DetailedQuestStep("Use the emerald lens on the lantern.", emeraldLensHighlighted, bullseyeLanternHighlighted);
       // lightLantern = new DetailedQuestStep("Light the emerald lantern.", tinderboxHighlighted, emeraldLantern);

        goUpToCannon1 = new ObjectStep(ObjectID.STAIRS_16946, new RSTile(2225, 3808, 0), "Go to the top deck to remove the cannon's symbol.");
        goUpToCannon2 = new ObjectStep(ObjectID.STAIRS_16945, new RSTile(2222, 3792, 1), "Go to the top deck to remove the cannon's symbol.");
        goUpToCannon3 = new ObjectStep(ObjectID.STAIRS_16945, new RSTile(2227, 3794, 2), "Go to the top deck to remove the cannon's symbol.");
        useLanternOnCannon = new ObjectStep(17015, new RSTile(2227, 3786, 3), "Use the emerald lantern on the east cannon.", emeraldLanternLitHighlighted);
        useLanternOnCannon.addDialogStep("Rub away!");
        //goUpToCannon1.addSubSteps(goUpToCannon2, goUpToCannon3, useLanternOnCannon);

        goDownToChart = new ObjectStep(ObjectID.STAIRS_16947, new RSTile(2227, 3794, 3), "Go to the main deck to remove a wallchart's symbol.");
        goUpToChart1 = new ObjectStep(ObjectID.STAIRS_16946, new RSTile(2225, 3808, 0), "Go to the main deck to remove a wallchart's symbol.");
        goUpToChart2 = new ObjectStep(ObjectID.STAIRS_16945, new RSTile(2222, 3792, 1), "Go to the main deck to remove a wallchart's symbol.");
        useLanternOnChart = new ObjectStep(17017, new RSTile(2222, 3790, 2), "Use the emerald lantern on the wallchart in the south room.", emeraldLanternLitHighlighted);
        useLanternOnChart.addDialogStep("Rub away!");
        //     goDownToChart.addSubSteps(goUpToChart1, goUpToChart2, useLanternOnChart);

        goDownToChest1 = new ObjectStep(ObjectID.STAIRS_16947, new RSTile(2227, 3794, 3), "Go to the bottom deck to remove more symbols.");
        goDownToChest2 = new ObjectStep(ObjectID.STAIRS_16947, new RSTile(2222, 3792, 2), "Go to the bottom deck to remove more symbols.");
        goDownToChest3 = new ObjectStep(ObjectID.STAIRS_16948, new RSTile(2225, 3808, 1), "Go to the bottom deck to remove more symbols.");
        useLanternOnChest = new ObjectStep(17018, new RSTile(2223, 3811, 0), "Use the emerald lantern on the chest in the north of the bottom deck.", emeraldLanternLitHighlighted);
        useLanternOnChest.addDialogStep("Rub away!");
        //   useLanternOnChest.addSubSteps(goDownToChest1, goDownToChest2, goDownToChest3);

        useLanternOnPillar = new ObjectStep(15587, new RSTile(2224, 3793, 0), "Use the emerald lantern on the pillar next to the kitchen of the bottom deck.", emeraldLanternLitHighlighted);
        useLanternOnPillar.addDialogStep("Rub away!");
        useLanternOnCrate = new ObjectStep(17016, new RSTile(2226, 3790, 0), "Use the emerald lantern on the crate in the kitchen of the bottom deck.", emeraldLanternLitHighlighted);
        useLanternOnCrate.addDialogStep("Rub away!");

        goDownToSail = new ObjectStep(ObjectID.STAIRS_16947, new RSTile(2227, 3794, 3), "Go to the main deck and talk to Captain Bentley to set sail.");
        goUpToSail1 = new ObjectStep(ObjectID.STAIRS_16946, new RSTile(2225, 3808, 0), "Go to the main deck and talk to Captain Bentley to set sail.");
        goUpToSail2 = new ObjectStep(ObjectID.STAIRS_16945, new RSTile(2222, 3792, 1), "Go to the main deck and talk to Captain Bentley to set sail.");
        talkToBentleyToSail = new NPCStep(NpcID.CAPTAIN_BENTLEY, new RSTile(2222, 3796, 2), "Talk to Captain Bentley to set sail.");
        //TODO Fix this
        // talkToBentleyToSail.addAlternateNpcs(NpcID.CAPTAIN_BENTLEY_6650);
        //  talkToBentleyToSail.addSubSteps(goDownToSail, goUpToSail1, goUpToSail2);

        goDownToIsle1 = new ObjectStep(ObjectID.LADDER_16961, new RSTile(2127, 3893, 2), "Climb down to Lunar Isle.");
        goDownToIsle2 = new ObjectStep(ObjectID.LADDER_16962, new RSTile(2118, 3894, 1), "Climb down to Lunar Isle.");
       // enterTown = new DetailedQuestStep(new RSTile(2100, 3914, 0), "Enter the town on Lunar Isle.");
        //   enterTown.addSubSteps(goDownToIsle1, goDownToIsle2);

        talkToOneiromancer = new NPCStep(NpcID.ONEIROMANCER, new RSTile(2151, 3867, 0), sealOfPassage);

        enterChickenHouse = new NPCStep(NpcID.HOUSE, new RSTile(2085, 3931, 0), sealOfPassage);
        talkToYaga = new NPCStep(NpcID.BABA_YAGA, new RSTile(2451, 4646, 0), sealOfPassage);
        talkToYaga.addDialogStep("The Oneiromancer told me you may be able to help...");

        leaveChickenHouse = new ObjectStep(ObjectID.DOOR_16774, new RSTile(2451, 4644, 0), "Leave Baba Yaga's house.", specialVial);

        fillVial = new ObjectStep(ObjectID.SINK_16705, new RSTile(2091, 3922, 0), "Fill the vial with water.", specialVialHighlighted);
        //fillVial.addSubSteps(leaveChickenHouse);
        // TODO make these add ItemToItemSteps
       // addGuam = new DetailedQuestStep("Add guam to the vial of water.", waterVial, guam);
    //    addGuamToMarrentill = new DetailedQuestStep("Add guam to the marr vial.", marrentilPotion, guam);
        //      addGuam.addSubSteps(addGuamToMarrentill);


     //   addMarrentil = new DetailedQuestStep("Add marrentill to the guam vial.", marrentill, guamPotion);
       // grindTooth = new DetailedQuestStep("Grind the suqah tooth.", suqahTooth, pestle);

      //  addToothToPotion = new DetailedQuestStep("Add the ground tooth to the guam-marr potion.", groundTooth, guamMarrentilPotionHighlighted);

      //\  killSuqahForTooth = new NPCStep(NpcID.SUQAH, "Kill the Suqah outside the town for a tooth. You'll also need 4 hides for later, so pick them up.", true);
       //TODO add itemStep
        //pickUpTooth = new ItemStep("Pick up the suqah tooth.", suqahTooth);

        bringPotionToOneiromancer = new NPCStep(NpcID.ONEIROMANCER, new RSTile(2151, 3867, 0),
             sealOfPassage, sleepPotion);

        enterAirAltar = new ObjectStep(34813, new RSTile(2985, 3292, 0),
                "Enter the Air Altar and use a dramen staff on it.", airTalisman, dramenStaff);

        useOnAir = new ObjectStep(ObjectID.ALTAR_34760, new RSTile(2844, 4834, 0),
                "Use the staff on the altar.", dramenStaffHighlighted);


        enterFireAltar = new ObjectStep(34817, new RSTile(3313, 3255, 0),
                "Enter the Fire Altar and use a partially made lunar staff on it.", fireTalisman, lunarStaffP1);

        useOnFire = new ObjectStep(ObjectID.ALTAR_34764, new RSTile(2585, 4838, 0),
                "Use the staff on the altar.", lunarStaffP1Highlighted);


        enterWaterAltar = new ObjectStep(34815, new RSTile(3185, 3165, 0),
                "Enter the Water Altar and use the partially made lunar staff on it.", waterTalisman, lunarStaffP2);
        useOnWater = new ObjectStep(ObjectID.ALTAR_34762, new RSTile(2716, 4836, 0), "Use the staff on the altar.", lunarStaffP2Highlighted);


        enterEarthAltar = new ObjectStep(34816, new RSTile(3306, 3474, 0),
                "Enter the Earth Altar and use a partially made lunar staff on it.", earthTalisman, lunarStaffP3);

        useOnEarth = new ObjectStep(ObjectID.ALTAR_34763, new RSTile(2658, 4841, 0),
                "Use the staff on the altar.", lunarStaffP3Highlighted);


        talkToOneiromancerWithStaff = new NPCStep(NpcID.ONEIROMANCER, new RSTile(2151, 3867, 0),
                //"Bring the staff to the Oneiromancer in the south east of Lunar Isle.",
                sealOfPassage, lunarStaff);

        enterMine = new ObjectStep(ObjectID.LADDER_14996, new RSTile(2142, 3944, 0),
                "Climb-down", pickaxe);
        mineOre = new ObjectStep(ObjectID.STALAGMITE_15251, new RSTile(2335, 10345, 2), "Mine", pickaxe);
        // mineOre.addAlternateObjects(ObjectID.STALAGMITES_15250);
        //  smeltBar = new DetailedQuestStep("Smelt the ore at a furnace.", lunarOre);
        //    makeHelmet = new DetailedQuestStep("Make the lunar helmet on an anvil.", lunarBar, hammer);
        talkToPauline = new NPCStep(NpcID.PAULINE_POLARIS, new RSTile(2070, 3917, 0),
                sealOfPassage);
        talkToPauline.addDialogStep("Pauline?", "Jane Blud-Hagic-Maid");
        talkToMeteora = new NPCStep(NpcID.METEORA, new RSTile(2083, 3890, 0),
                sealOfPassage);
        talkToSelene = new NPCStep(NpcID.SELENE, new RSTile(2079, 3912, 0),
                sealOfPassage);
        talkToSelene.addDialogStep("I'm looking for a ring.");
        // killSuqahForTiara = new NPCStep(NpcID.SUQAH, true);
        //TODO add ItemStep
        //  pickUpTiara = new ItemStep("Pick up the tiara.", tiara);
        // killSuqahForTiara.addSubSteps(pickUpTiara);
        returnTiaraToMeteora = new NPCStep(NpcID.METEORA, new RSTile(2083, 3890, 0),
                sealOfPassage, tiara);
        //TODO add Dig step
        // digForRing = new DigStep( new RSTile(2078, 3863, 0), "Dig in the south west of Lunar Isle for the ring.");
        talkToRimae = new NPCStep(NpcID.RIMAE_SIRSALIS, new RSTile(2104, 3909, 0),
                sealOfPassage, suqahHide4, coins400, needle, thread);
        talkToRimae.addDialogStep("You know the ceremonial clothes?");
        makeClothes = new NPCStep(NpcID.RIMAE_SIRSALIS, new RSTile(2104, 3909, 0),
                sealOfPassage, suqahHide4, coins400, needle, thread);
        makeClothes.addDialogStep("You know the ceremonial clothes?", "That seems like a fair deal.");

        //     bringItemsToOneiromancer = new BringLunarItems(this);

        useVialOnKindling = new UseItemOnItemStep(ItemID.WAKING_SLEEP_VIAL, ItemID.KINDLING,
                Inventory.find(ItemID.KINDLING).length == 0,
                sleepPotionHighlighted, kindling);

        lightBrazier = new ObjectStep(17025, new RSTile(2073, 3912, 0),
                "Equip your lunar equipment, some combat runes, and light the Brazier in the west of Lunar Isle's town.",
                sealOfPassage, tinderboxHighlighted, soakedKindling, helmEquipped, bodyEquipped, legsEquipped, bootsEquipped, glovesEquipped,
                cloakEquipped, amuletEquipped, ringEquipped, lunarStaffEquipped);


        useKindlingOnBrazier = new ObjectStep(17025, new RSTile(2073, 3912, 0),
                "Add the soaked kindling the Brazier in the west of Lunar Isle's town.",
                sealOfPassage, soakedKindlingHighlighted, helmEquipped, bodyEquipped, legsEquipped, bootsEquipped, glovesEquipped,
                cloakEquipped, amuletEquipped, ringEquipped, lunarStaffEquipped);

        //TODO Fix this
        //  if (client.getLocalPlayer().getPlayerComposition().isFemale()) {
        talkToEthereal = new NPCStep(NpcID.ETHEREAL_LADY, new RSTile(1762, 5088, 2),
                "Talk to the Ethereal Lady.");
        talkWithEtherealToFight = new NPCStep(NpcID.ETHEREAL_LADY, new RSTile(1762, 5088, 2),
                "Talk to the Ethereal Lady. Be prepared to fight.");
        //  } else {
        talkToEthereal = new NPCStep(NpcID.ETHEREAL_MAN, new RSTile(1762, 5088, 2), "Talk to the Ethereal Man.");
        talkWithEtherealToFight = new NPCStep(NpcID.ETHEREAL_MAN, new RSTile(1762, 5088, 2), "Talk to the Ethereal Man. Be prepared to fight.");
        //   }
        talkWithEtherealToFight.addDialogStep("Of course. I'm ready.");

        goToNumbers = new ObjectStep(ObjectID.PLATFORM_16633, new RSTile(1768, 5080, 2), "Go on the platform to the number challenge.");
        goToMimic = new ObjectStep(ObjectID.PLATFORM_16632, new RSTile(1765, 5079, 2), "Go on the platform to the music challenge.");
        goToRace = new ObjectStep(ObjectID.PLATFORM_16634, new RSTile(1770, 5088, 2), "Go on the platform to the race challenge.");
        goToMemory = new ObjectStep(ObjectID.PLATFORM_16636, new RSTile(1751, 5095, 2), "Go on the platform to the memory challenge.");
        goToTrees = new ObjectStep(ObjectID.PLATFORM_16635, new RSTile(1764, 5098, 2), "Go on the platform to the trees challenge.");
        goToChance = new ObjectStep(ObjectID.PLATFORM_16637, new RSTile(1751, 5080, 2), "Go on the platform to the chance challenge.");

        //  doMemoryChallenge = new MemoryChallenge(this);
        startTreeChallenge = new NPCStep(NpcID.ETHEREAL_PERCEPTIVE, new RSTile(1765, 5112, 2), "Talk to Ethereal perspective to begin. Cut 20 logs and deposit them on the log piles faster than the NPC.");
        startTreeChallenge.addDialogStep("Ok, let's go!");
        // doRaceChallenge = new DetailedQuestStep("Race to the end of the course to win!");
        //doChanceChallenge = new ChanceChallenge(this);
        /// doNumberChallenge = new NumberChallenge(this);
        //doMimicChallenge = new MimicChallenge(this);

        startNumber = new NPCStep(NpcID.ETHEREAL_NUMERATOR, new RSTile(1786, 5066, 2),
                "Talk to the Ethereal Numerator to begin the challenge.");

        //   doTreeChallenge = new DetailedQuestStep("Chop 20 logs and deposit them in the log pile.");

        startRace = new NPCStep(NpcID.ETHEREAL_EXPERT, new RSTile(1788, 5068, 2), "Talk to the Ethereal Expert. Be prepared to race!");
        startRace.addDialogStep("Ok.");

        //  fightMe = new NPCStep(NpcID.ME, new RSTile(1823, 5087, 2), "Fight Me.");
        //  fightMe.addAlternateNpcs(NpcID.ME_786);

        leaveLecturn = new ObjectStep(ObjectID.MY_LIFE, new RSTile(1760, 5088, 2), "Read My life to return to Lunar Isle.");
        leaveLecturn.addDialogStep("Yes");
        finishQuest = new NPCStep(NpcID.ONEIROMANCER, new RSTile(2151, 3867, 0), sealOfPassage);
    }

    private void setupConditionalSteps() {
        boardingTheBoat = new ConditionalStep(travelWithLokar);
        boardingTheBoat.addStep(onCoveF1, boardShip);
        boardingTheBoat.addStep(atBaseOfStairs, climbLadder);

        setSail = new ConditionalStep(boardingTheBoat);
        setSail.addStep(onBoatF3, goDownToSail);
        setSail.addStep(onBoatF2, talkToBentleyToSail);
        setSail.addStep(onBoatF1, goUpToSail2);
        setSail.addStep(onBoatF0, goUpToSail1);

        enteringTheIsland = new ConditionalStep(setSail);
        enteringTheIsland.addStep(onLunarDock, goDownToIsle2);
        enteringTheIsland.addStep(onBoatLunar, goDownToIsle1);

        returnToTalkToYaga = new ConditionalStep(enteringTheIsland
                //   "Talk to Baba Yaga in the chicken-legged house in the north of Lunar Isle's town."
        );
        //returnToTalkToYaga.addSubSteps(talkToYaga, enterChickenHouse);

        returnToMakePotion = new ConditionalStep(enteringTheIsland
                // "Kill the Suqah outside the town on Lunar Isle for a tooth. You'll also need 4 hides for later, so pick them up."
        );
        //   returnToMakePotion.addSubSteps(killSuqahForTooth, pickUpTooth);

        returnToOneWithPotion = new ConditionalStep(enteringTheIsland,
                //"Return to the Oneiromancer with the waking sleep vial.",
                sleepPotion);
        //  returnToOneWithPotion.addSubSteps(bringPotionToOneiromancer);

        returnWithStaff = new ConditionalStep(enteringTheIsland,
                //"Return to the Oneiromancer with the Lunar Staff.",
                lunarStaff);
        // returnWithStaff.addSubSteps(talkToOneiromancerWithStaff);

        makingHelm = new ConditionalStep(enterMine);
        makingHelm.addStep(lunarBar, makeHelmet);
        makingHelm.addStep(lunarOre, smeltBar);
        makingHelm.addStep(inLunarMine, mineOre);
        //  makingHelm.setLockingCondition(hadHelm);

        gettingRing = new ConditionalStep(talkToSelene);
        gettingRing.addStep(talkedToSelene, digForRing);
        //  gettingRing.setLockingCondition(hadRing);

        gettingCape = new ConditionalStep(talkToPauline);
        //  gettingCape.setLockingCondition(hadCape);

        gettingAmulet = new ConditionalStep(talkToMeteora);
        gettingAmulet.addStep(tiara, returnTiaraToMeteora);
        gettingAmulet.addStep(tiaraNearby, pickUpTiara);
        gettingAmulet.addStep(talkedToMeteora, killSuqahForTiara);
        //   gettingAmulet.setLockingCondition(hadAmulet);

        gettingClothes = new ConditionalStep(talkToRimae);
        gettingClothes.addStep(talkedToRimae, makeClothes);
        //  gettingClothes.setLockingCondition(hadClothes);
    }


    @Override
    public Priority priority() {
        return null;
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
        return null;
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
    @Override
    public boolean isComplete() {
        return Quest.LUNAR_DIPLOMACY.getState().equals(Quest.State.COMPLETE);
    }
}
