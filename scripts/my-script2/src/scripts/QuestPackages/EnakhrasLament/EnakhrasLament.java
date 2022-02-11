package scripts.QuestPackages.EnakhrasLament;

import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.*;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.ElementalWorkshopI.ElementalWorkshop;
import scripts.QuestSteps.*;
import scripts.Requirements.*;
import scripts.Requirements.Items.ItemCollections;
import scripts.Requirements.Util.ConditionalStep;
import scripts.Requirements.Util.Conditions;
import scripts.Tasks.Priority;
import scripts.Timer;

import java.util.*;

public class EnakhrasLament implements QuestTask {

    private static EnakhrasLament quest;

    public static EnakhrasLament get() {
        return quest == null ? quest = new EnakhrasLament() : quest;
    }

    //Items Required
    ItemRequirement pickaxe, chiselHighlighted, sandstone32, sandstone20, base, body, head, granite2, granite, leftArm, rightArm, leftLeg,
            rightLeg, kSigil, rSigil, mSigil, zSigil, softClay, camelMould, camelHead, breadOrCake, fireSpellRunes, airSpellRunes,
            mapleLog, log, oakLog, willowLog, coal, candle, air2, chaos, earth2, sandstone5, tinderbox, crumbleUndeadRunes, sandstone52,
            airStaff, airRuneOrStaff, earthRuneOrStaff, earthStaff;

    // SpellbookRequirement onNormals;
    UseItemOnObjectStep placeBase, useChiselOnWall;
    UseItemOnItemStep useChiselOn32Sandstone, useChiselOn20Sandstone, useChiselOnGranite;
    Requirement hasPlacedBase, hasTalkedToLazimAfterBase, hasPlacedBody, chiseledStatue, canChooseHead, inTempleEntranceRoom,
            inTempleGroundFloor, startedTemple, gottenLimbs, openedDoor1, openedDoor2, openedDoor3, openedDoor4, mPlaced, kPlaced,
            rPlaced, zPlaced, goneUpstairs, hasGottenRightArm, hasGottenRightLeg, inCentreRoom, inPuzzleFloor,
            fedBread, meltedFountain, cleanedFurnace, litBraziers, litLog, litOak, litWillow, litMaple, litCandle, litCoal, inNorthPuzzleRoom,
            inTopRoom, inLastRoom, wallNeedsChisel, finishedWall, protectFromMelee;

    QuestStep talkToLazim, bringLazim32Sandstone, bringLazim20Sandstone,
            placeBody, talkToLazimToChooseHead, getGranite, craftHead, talkToLazimAboutBody,
            chiselStatue, giveLazimHead, talkToLazimInTemple, enterTemple, enterTempleDownLadder, cutOffLimb, takeM,
            talkToLazimForHead, enterDoor1, enterDoor2, enterDoor3, enterDoor4, enterKDoor, enterRDoor, enterMDoor, enterZDoor,
            takeZ, takeK, takeR, useStoneHeadOnPedestal, useSoftClayOnPedestal, goUpToPuzzles, useBread, castAirSpell,
            castFireSpell, useMapleLog, useOakLog, useLog, useWillowLog, useCoal, useCandle, passBarrier, goUpFromPuzzleRoom, castCrumbleUndead,
            goDownToFinalRoom, protectThenTalk, repairWall, talkToAkthankos;

    //RSArea()s
    RSArea templeEntranceRoom, templeGroundFloor, centreRoom, puzzleFloor, northPuzzleRoom, topRoom, lastRoom;


    public Map<Integer, QuestStep> loadSteps() {
        loadRSAreas();
        setupItemRequirements();
        setupConditions();
        setupSteps();
        Map<Integer, QuestStep> steps = new HashMap<>();

        steps.put(0, talkToLazim);

        ConditionalStep makeAndPlaceBase = new ConditionalStep(bringLazim32Sandstone);
        makeAndPlaceBase.addStep(new Conditions(head, granite), giveLazimHead);
        makeAndPlaceBase.addStep(new Conditions(granite2, canChooseHead), craftHeadStep());
        makeAndPlaceBase.addStep(canChooseHead, getGranite);
        makeAndPlaceBase.addStep(chiseledStatue, talkToLazimToChooseHead);
        makeAndPlaceBase.addStep(hasPlacedBody, chiselStatue);
        makeAndPlaceBase.addStep(body, placeBody);
        makeAndPlaceBase.addStep(sandstone20, useChiselOn20Sandstone);
        makeAndPlaceBase.addStep(hasTalkedToLazimAfterBase, bringLazim20Sandstone);
        makeAndPlaceBase.addStep(hasPlacedBase, talkToLazimAboutBody);
        makeAndPlaceBase.addStep(base, placeBase);
        makeAndPlaceBase.addStep(sandstone32, useChiselOn32Sandstone);

        steps.put(10, makeAndPlaceBase);

        ConditionalStep exploreBottomLayer = new ConditionalStep(enterTemple);
        exploreBottomLayer.addStep(new Conditions(camelHead, inPuzzleFloor), useStoneHeadOnPedestal);
        exploreBottomLayer.addStep(camelMould, useChiselOnGranite);
        exploreBottomLayer.addStep(inPuzzleFloor, useSoftClayOnPedestal);
        exploreBottomLayer.addStep(new Conditions(gottenLimbs, inTempleGroundFloor, openedDoor1, openedDoor2, openedDoor3, openedDoor4), goUpToPuzzles);
        exploreBottomLayer.addStep(new Conditions(gottenLimbs, inTempleGroundFloor, openedDoor1, openedDoor2, openedDoor3, rSigil), enterDoor4);
        exploreBottomLayer.addStep(new Conditions(gottenLimbs, inTempleGroundFloor, openedDoor1, openedDoor2, openedDoor3), takeR);
        exploreBottomLayer.addStep(new Conditions(gottenLimbs, inTempleGroundFloor, openedDoor1, openedDoor2, kSigil), enterDoor3);
        exploreBottomLayer.addStep(new Conditions(gottenLimbs, inTempleGroundFloor, openedDoor1, openedDoor2), takeK);
        // It's possible to skip the rest of  but it skips some of the quest story and leaves doors locked after you finish, so this encourages players to explore
        exploreBottomLayer.addStep(new Conditions(gottenLimbs, inTempleGroundFloor, openedDoor1, zSigil), enterDoor2);
        exploreBottomLayer.addStep(new Conditions(gottenLimbs, inTempleGroundFloor, openedDoor1), takeZ);
        exploreBottomLayer.addStep(new Conditions(gottenLimbs, inTempleGroundFloor, mSigil), enterDoor1);
        exploreBottomLayer.addStep(new Conditions(gottenLimbs, inTempleGroundFloor), takeM);
        exploreBottomLayer.addStep(new Conditions(startedTemple, inTempleGroundFloor), cutOffLimb);
        exploreBottomLayer.addStep(inTempleGroundFloor, talkToLazimInTemple);
        exploreBottomLayer.addStep(inTempleEntranceRoom, enterTempleDownLadder);

        steps.put(20, exploreBottomLayer);

        ConditionalStep puzzles = new ConditionalStep(enterTemple);
        puzzles.addStep(new Conditions(fedBread, inPuzzleFloor, meltedFountain, cleanedFurnace, litLog, litOak, litWillow, litMaple, litCandle), useCoal);
        puzzles.addStep(new Conditions(fedBread, inPuzzleFloor, meltedFountain, cleanedFurnace, litLog, litOak, litWillow, litMaple), useCandle);
        puzzles.addStep(new Conditions(fedBread, inPuzzleFloor, meltedFountain, cleanedFurnace, litLog, litOak, litWillow), useMapleLog);
        puzzles.addStep(new Conditions(fedBread, inPuzzleFloor, meltedFountain, cleanedFurnace, litLog, litOak), useWillowLog);
        puzzles.addStep(new Conditions(fedBread, inPuzzleFloor, meltedFountain, cleanedFurnace, litLog), useOakLog);
        puzzles.addStep(new Conditions(fedBread, inPuzzleFloor, meltedFountain, cleanedFurnace), useLog);
        puzzles.addStep(new Conditions(fedBread, inPuzzleFloor, meltedFountain), castAirSpell);
        puzzles.addStep(new Conditions(fedBread, inPuzzleFloor), castFireSpell);
        puzzles.addStep(inPuzzleFloor, useBread);
        puzzles.addStep(inTempleGroundFloor, goUpToPuzzles);
        puzzles.addStep(inTempleEntranceRoom, enterTempleDownLadder);

        steps.put(30, puzzles);

        ConditionalStep topFloorPuzzle = new ConditionalStep(enterTemple);
        topFloorPuzzle.addStep(inTopRoom, castCrumbleUndead);
        topFloorPuzzle.addStep(inNorthPuzzleRoom, goUpFromPuzzleRoom);
        topFloorPuzzle.addStep(inPuzzleFloor, passBarrier);
        topFloorPuzzle.addStep(inTempleGroundFloor, goUpToPuzzles);
        topFloorPuzzle.addStep(inTempleEntranceRoom, enterTempleDownLadder);

        steps.put(40, topFloorPuzzle);

        ConditionalStep protectMeleePuzzle = new ConditionalStep(enterTemple);
        protectMeleePuzzle.addStep(inLastRoom, protectThenTalk);
        protectMeleePuzzle.addStep(inTopRoom, goDownToFinalRoom);
        protectMeleePuzzle.addStep(inNorthPuzzleRoom, goUpFromPuzzleRoom);
        protectMeleePuzzle.addStep(inPuzzleFloor, passBarrier);
        protectMeleePuzzle.addStep(inTempleGroundFloor, goUpToPuzzles);
        protectMeleePuzzle.addStep(inTempleEntranceRoom, enterTempleDownLadder);

        steps.put(50, protectMeleePuzzle);

        ConditionalStep repairWallForAkthankos = new ConditionalStep(enterTemple);
        repairWallForAkthankos.addStep(new Conditions(inLastRoom, wallNeedsChisel), useChiselOnWall);
        repairWallForAkthankos.addStep(new Conditions(inLastRoom, finishedWall), talkToAkthankos);
        repairWallForAkthankos.addStep(inLastRoom, repairWall);
        repairWallForAkthankos.addStep(inTopRoom, goDownToFinalRoom);
        repairWallForAkthankos.addStep(inNorthPuzzleRoom, goUpFromPuzzleRoom);
        repairWallForAkthankos.addStep(inPuzzleFloor, passBarrier);
        repairWallForAkthankos.addStep(inTempleGroundFloor, goUpToPuzzles);
        repairWallForAkthankos.addStep(inTempleEntranceRoom, enterTempleDownLadder);

        steps.put(60, repairWallForAkthankos);

        return steps;
    }

    public void setupItemRequirements() {
        pickaxe = new ItemRequirement("Any pickaxe", ItemCollections.getPickaxes());
        chiselHighlighted = new ItemRequirement("Chisel", ItemID.CHISEL);

        sandstone52 = new ItemRequirement("52 kg of sandstone", -1, -1);
        sandstone32 = new ItemRequirement("Sandstone 32kg", ItemID.SANDSTONE_32KG);
        sandstone20 = new ItemRequirement("Sandstone 20kg", ItemID.SANDSTONE_20KG);

        base = new ItemRequirement("Sandstone base", ItemID.SANDSTONE_BASE);
        body = new ItemRequirement("Sandstone body", ItemID.SANDSTONE_BODY);


        granite2 = new ItemRequirement("Granite (5kg)", ItemID.GRANITE_5KG, 2);
        granite = new ItemRequirement("Granite (5kg)", ItemID.GRANITE_5KG);


        head = new ItemRequirement("Stone head", ItemID.STONE_HEAD);
        head.addAlternateItemID(ItemID.STONE_HEAD_6990, ItemID.STONE_HEAD_6991, ItemID.STONE_HEAD_6992);

        mSigil = new ItemRequirement("M sigil", ItemID.M_SIGIL);
        zSigil = new ItemRequirement("Z sigil", ItemID.Z_SIGIL);
        kSigil = new ItemRequirement("K sigil", ItemID.K_SIGIL);
        rSigil = new ItemRequirement("R sigil", ItemID.R_SIGIL);

        leftLeg = new ItemRequirement("Stone left leg", ItemID.STONE_LEFT_LEG);
        // leftLeg.setTooltip("You can get another from Lazim");
        leftArm = new ItemRequirement("Stone left arm", ItemID.STONE_LEFT_ARM);
        //leftArm.setTooltip("You can get another from Lazim");
        rightLeg = new ItemRequirement("Stone right leg", ItemID.STONE_RIGHT_LEG);
        // rightLeg.setTooltip("You can get another from Lazim");
        rightArm = new ItemRequirement("Stone right arm", ItemID.STONE_RIGHT_ARM);
        // rightArm.setTooltip("You can get another from Lazim");

        softClay = new ItemRequirement("Soft clay", ItemID.SOFT_CLAY);

        camelMould = new ItemRequirement("Camel mould (p)", ItemID.CAMEL_MOULD_P);
        camelHead = new ItemRequirement("Stone head", ItemID.STONE_HEAD_7002);

        breadOrCake = new ItemRequirement("Bread or cake", ItemID.BREAD);
        breadOrCake.addAlternateItemID(ItemID.CAKE);
        breadOrCake.setDisplayMatchedItemName(true);

        airSpellRunes = new ItemRequirement("Runes to cast Wind Bolt or stronger", -1, -1);

        fireSpellRunes = new ItemRequirement("Runes to cast Fire Bolt or stronger", -1, -1);
        crumbleUndeadRunes = new ItemRequirement("Runes for crumble undead spell", -1, -1);


        log = new ItemRequirement("Logs", ItemID.LOGS);

        mapleLog = new ItemRequirement("Maple logs", ItemID.MAPLE_LOGS);

        willowLog = new ItemRequirement("Willow logs", ItemID.WILLOW_LOGS);

        oakLog = new ItemRequirement("Oak logs", ItemID.OAK_LOGS);

        coal = new ItemRequirement("Coal", ItemID.COAL);

        candle = new ItemRequirement("Candle", ItemID.CANDLE);


        air2 = new ItemRequirement(ItemCollections.getAirRune(), 2);
        airStaff = new ItemRequirement(ItemID.STAFF_OF_AIR, 1, 1, true, true);
        earth2 = new ItemRequirement(ItemCollections.getEarthRune(), 2);
        chaos = new ItemRequirement("Chaos rune", ItemID.CHAOS_RUNE);

        sandstone5 = new ItemRequirement("Sandstone (5kg)", ItemID.SANDSTONE_5KG);


        tinderbox = new ItemRequirement("Tinderbox", ItemID.TINDERBOX);

        //onNormals = new SpellbookRequirement(Spellbook.NORMAL);
    }

    public void loadRSAreas() {
        templeEntranceRoom = new RSArea(new RSTile(3124, 9328, 1), new RSTile(3128, 9330, 1));
        templeGroundFloor = new RSArea(new RSTile(3074, 9282, 0), new RSTile(3133, 9341, 0));
        centreRoom = new RSArea(new RSTile(3098, 9306, 0), new RSTile(3110, 9318, 0));
        puzzleFloor = new RSArea(new RSTile(3086, 9305, 1), new RSTile(3121, 9326, 1));
        northPuzzleRoom = new RSArea(new RSTile(2095, 9319, 1), new RSTile(3112, 9335, 1));
        topRoom = new RSArea(new RSTile(3097, 9299, 2), new RSTile(3113, 9334, 2));
        lastRoom = new RSArea(new RSTile(3096, 9291, 1), new RSTile(3112, 9302, 1));
    }

    public void setupConditions() {
        hasPlacedBase = new VarbitRequirement(1593, 1);
        hasPlacedBody = new VarbitRequirement(1593, 2);
        chiseledStatue = new VarbitRequirement(1593, 3);
        canChooseHead = new VarbitRequirement(1563, 1);
        hasTalkedToLazimAfterBase = new VarbitRequirement(1562, 1);

        hasGottenRightArm = new VarbitRequirement(1590, 1);
        hasGottenRightLeg = new VarbitRequirement(1592, 1);

        inTempleEntranceRoom = new AreaRequirement(templeEntranceRoom);
        inTempleGroundFloor = new AreaRequirement(templeGroundFloor);
        inCentreRoom = new AreaRequirement(centreRoom);
        inPuzzleFloor = new AreaRequirement(puzzleFloor);
        inNorthPuzzleRoom = new AreaRequirement(northPuzzleRoom);
        inTopRoom = new AreaRequirement(topRoom);
        inLastRoom = new AreaRequirement(lastRoom);

        startedTemple = new VarbitRequirement(1566, 1);

        gottenLimbs = new VarbitRequirement(1587, 63);

        openedDoor1 = new VarbitRequirement(1608, 1);
        openedDoor2 = new VarbitRequirement(1609, 1);
        openedDoor3 = new VarbitRequirement(1610, 1);
        openedDoor4 = new VarbitRequirement(1611, 1);

        zPlaced = new VarbitRequirement(1611, 1);
        mPlaced = new VarbitRequirement(1612, 1);
        rPlaced = new VarbitRequirement(1613, 1);
        kPlaced = new VarbitRequirement(1614, 1);

        goneUpstairs = new VarbitRequirement(1618, 1);

        fedBread = new VarbitRequirement(1576, 1);
        meltedFountain = new VarbitRequirement(1577, 1);
        cleanedFurnace = new VarbitRequirement(1578, 1);
        litBraziers = new VarbitRequirement(1579, 1);

        litLog = new VarbitRequirement(1581, 1);
        litOak = new VarbitRequirement(1582, 1);
        litWillow = new VarbitRequirement(1583, 1);
        litMaple = new VarbitRequirement(1584, 1);
        litCandle = new VarbitRequirement(1585, 1);
        litCoal = new VarbitRequirement(1586, 1);

        wallNeedsChisel = new VarbitRequirement(1620, 1);
        finishedWall = new VarbitRequirement(1602, 3);

        // protectFromMelee = new PrayerRequirement("Protect from Melee", Prayer.PROTECT_FROM_MELEE);
    }

    public QuestStep craftHeadStep() {
        craftHead = new MakeItemStep(ItemID.STONE_HEAD);
        if (Utils.useItemOnItem(ItemID.CHISEL, ItemID.GRANITE_5KG)) {
            Timer.waitCondition(MakeScreen::isOpen, 2500, 3500);
        }
        if (MakeScreen.isOpen()) {
            craftHead.execute();
        }
        return craftHead;
    }

    public void setupSteps() {
        talkToLazim = new NPCStep(NpcID.LAZIM, new RSTile(3190, 2925, 0), pickaxe);
        talkToLazim.addDialogStep("Of course!");
        bringLazim32Sandstone = new NPCStep(NpcID.LAZIM, new RSTile(3190, 2925, 0), "Get 32kg of sandstone and give it to Lazim. This can be done in batches, and you can mine some nearby.");
        bringLazim32Sandstone.addDialogStep("Okay, I'll get on with it.");
        bringLazim32Sandstone.addDialogStep("Yes, I have more stone.");
        bringLazim32Sandstone.addDialogStep("Here's a large 10 kg block.");
        bringLazim32Sandstone.addDialogStep("Here's a medium 5 kg block.");
        bringLazim32Sandstone.addDialogStep("Here's a small 2 kg block.");
        bringLazim32Sandstone.addDialogStep("Here's a tiny 1 kg block.");

        useChiselOn32Sandstone = new UseItemOnItemStep(ItemID.CHISEL, ItemID.SANDSTONE_32KG,
                !Inventory.contains(ItemID.SANDSTONE_32KG),
                chiselHighlighted, sandstone32);
        placeBase = new UseItemOnObjectStep(ItemID.SANDSTONE_BASE, NullObjectID.NULL_10952,
                new RSTile(3190, 2926, 0),
                base);
        talkToLazimAboutBody = new NPCStep(NpcID.LAZIM, new RSTile(3190, 2925, 0), "Talk to Lazim again.");
        talkToLazimAboutBody.addDialogStep("I'll do it right away!");

        bringLazim20Sandstone = new NPCStep(NpcID.LAZIM, new RSTile(3190, 2925, 0), "Get 20kg of sandstone and give it to Lazim. This can be done in batches, and you can mine some nearby.");
        bringLazim20Sandstone.addDialogStep("I'll do it right away!");
        bringLazim20Sandstone.addDialogStep("Yes, I have more stone.");
        bringLazim20Sandstone.addDialogStep("Here's a large 10 kg block.");
        bringLazim20Sandstone.addDialogStep("Here's a medium 5 kg block.");
        bringLazim20Sandstone.addDialogStep("Here's a small 2 kg block.");
        bringLazim20Sandstone.addDialogStep("Here's a tiny 1 kg block.");

        useChiselOn20Sandstone = new UseItemOnItemStep(ItemID.CHISEL, ItemID.SANDSTONE_20KG,
                !Inventory.contains(ItemID.SANDSTONE_20KG),
                chiselHighlighted, sandstone20);
        placeBody = new ObjectStep(NullObjectID.NULL_10952, new RSTile(3190, 2926, 0), "Place the body on the sandstone base.", body);
        talkToLazimToChooseHead = new NPCStep(NpcID.LAZIM, new RSTile(3190, 2925, 0), "Talk to Lazim and choose the head you'd like the statue to have.");
        getGranite = new NPCStep(NpcID.LAZIM,
                new RSTile(3190, 2925, 0), granite2);

        // TODO: Change head highlight text based on choice


        chiselStatue = new ObjectStep(NullObjectID.NULL_10952, new RSTile(3190, 2926, 0), "Use a chisel on the headless statue.", chiselHighlighted);

        giveLazimHead = new NPCStep(NpcID.LAZIM,
                new RSTile(3190, 2925, 0),  head);

        enterTemple = new ObjectStep(NullObjectID.NULL_11046, new RSTile(3194, 2925, 0), "Enter the temple south of the Bandit's Camp.");
        enterTempleDownLadder = new ObjectStep(ObjectID.LADDER_11042, new RSTile(3127, 9329, 1), "Enter the temple south of the Bandit's Camp.");
        talkToLazimInTemple = new NPCStep(NpcID.LAZIM, new RSTile(3127, 9324, 0), "Talk to Lazim in the temple.");

        cutOffLimb = new ObjectStep(NullObjectID.NULL_10970, new RSTile(3130, 9326, 0), "Use a chisel on the fallen statue to get all its limbs.", chiselHighlighted);
        cutOffLimb.addDialogStep("Remove the statue's left arm", "Remove the statue's right arm", "Remove the statue's left leg", "Remove the statue's right leg");

        takeM = new ObjectStep(ObjectID.PEDESTAL_11061, new RSTile(3128, 9319, 0), "Take the M sigil from the pedestal in the room.");
        takeZ = new ObjectStep(ObjectID.PEDESTAL_11060, new RSTile(3097, 9336, 0), "Take the Z sigil from the pedestal in the north room.");
        takeK = new ObjectStep(ObjectID.PEDESTAL_11063, new RSTile(3080, 9305, 0), "Take the K sigil from the pedestal in the west room.");
        takeR = new ObjectStep(ObjectID.PEDESTAL_11062, new RSTile(3111, 9288, 0), "Take the R sigil from the pedestal in the south room.");
        talkToLazimForHead = new NPCStep(NpcID.LAZIM, new RSTile(3127, 9324, 0), "Talk to Lazim in the temple for the stone head.");
        talkToLazimForHead.addDialogStep("Do you know where the statue's head is?");

        enterDoor1 = new ObjectStep(ObjectID.DOOR_11066, new RSTile(3126, 9337, 0), "Enter the right arm door.", rightArm);
        enterDoor2 = new ObjectStep(ObjectID.DOOR_11068, new RSTile(3079, 9334, 0), "Enter the left leg door.", leftLeg);
        enterDoor3 = new ObjectStep(ObjectID.DOOR_11064, new RSTile(3082, 9287, 0), "Enter the left arm door.", leftArm);
        enterDoor4 = new ObjectStep(ObjectID.DOOR_11070, new RSTile(3129, 9290, 0), "Enter the right leg door.", rightLeg);

        enterKDoor = new ObjectStep(ObjectID.DOOR_11057, new RSTile(3111, 9312, 0), "Enter the door with a K.", kSigil);
        enterRDoor = new ObjectStep(ObjectID.DOOR_11055, new RSTile(3104, 9319, 0), "Enter the door with an R.", rSigil);
        enterMDoor = new ObjectStep(ObjectID.DOOR_11053, new RSTile(3097, 9312, 0), "Enter the door with an M.", mSigil);
        enterZDoor = new ObjectStep(ObjectID.DOOR_11051, new RSTile(3104, 9305, 0), "Enter the door with a Z.", zSigil);

        goUpToPuzzles = new ObjectStep(ObjectID.LADDER_11041, new RSTile(3104, 9309, 0), "Open the central room's doors using the metal letters. Go up the ladder in the central room.");

        useSoftClayOnPedestal = new ObjectStep(NullObjectID.NULL_10987, new RSTile(3104, 9312, 1),
                "Use soft clay on the pedestal.", softClay);
        useChiselOnGranite = new UseItemOnItemStep(ItemID.CHISEL, ItemID.GRANITE_5KG,
                !Inventory.contains(ItemID.GRANITE_5KG),
                granite, chiselHighlighted);
        useStoneHeadOnPedestal = new ObjectStep(NullObjectID.NULL_10987, new RSTile(3104, 9312, 1), "Use the camel stone head on the pedestal.", camelHead);

        useBread = new NPCStep(NpcID.PENTYN, new RSTile(3091, 9324, 1),
             ///   "Use bread or cake on Pentyn.",
                breadOrCake);
        castFireSpell = new NPCStep(NpcID.CRUST_OF_ICE
                , new RSTile(3092, 9308, 1),
              //  "Cast a fire spell on the frozen fountain.",
                fireSpellRunes);
        castAirSpell = new NPCStep(NpcID.FURNACE_GRATE,
                new RSTile(3116, 9323, 1),
             //   "Cast an air spell on the furnace.",
                airSpellRunes);
        useMapleLog = new ObjectStep(NullObjectID.NULL_11014, new RSTile(3114, 9309, 1), "Use a maple log on the north west brazier.", mapleLog);
        useOakLog = new ObjectStep(NullObjectID.NULL_11012, new RSTile(3116, 9306, 1), "Use an oak log on the south brazier.", oakLog);
        useLog = new ObjectStep(NullObjectID.NULL_11011, new RSTile(3114, 9306, 1), "Use a normal log on the south east brazier.", log);
        useWillowLog = new ObjectStep(NullObjectID.NULL_11013, new RSTile(3118, 9306, 1), "Use a willow log on the south west brazier.", willowLog);
        useCoal = new ObjectStep(NullObjectID.NULL_11016, new RSTile(3118, 9309, 1), "Use coal on the north east brazier.", coal);
        useCandle = new ObjectStep(NullObjectID.NULL_11015, new RSTile(3116, 9309, 1), "Use a candle on the north brazier.", candle);

        passBarrier = new ObjectStep(ObjectID.MAGIC_BARRIER, new RSTile(3104, 9319, 1), "Pass through the magic barrier and go up the ladder.");
        goUpFromPuzzleRoom = new ObjectStep(ObjectID.LADDER_11041, new RSTile(3104, 9332, 1), "Go up the ladder.");
        passBarrier.addSubSteps(goUpFromPuzzleRoom);

        castCrumbleUndead = new NPCStep(NpcID.BONEGUARD,
                new RSTile(3104, 9307, 2),
            //    "Cast crumble undead on the Boneguard.",
                earth2, airRuneOrStaff, chaos);

        goDownToFinalRoom = new ObjectStep(ObjectID.STONE_LADDER_11044, new RSTile(3105, 9300, 2), "Climb down the stone ladder past the Boneguard.");

        protectThenTalk = new NPCStep(NpcID.BONEGUARD_3577,
                new RSTile(3105, 9297, 1),
                //"Put on Protect from Melee, then talk to the Boneguard.",
                protectFromMelee);
        repairWall = new ObjectStep(NullObjectID.NULL_11027, new RSTile(3107, 9291, 1), "Take sandstone from the nearby rubble, and use it to repair the south wall. For each piece added, use a chisel on the wall.", sandstone5);
        repairWall.addDialogStep("Of course I'll help you out.");

        useChiselOnWall = new UseItemOnObjectStep(ItemID.CHISEL, NullObjectID.NULL_11027,
                new RSTile(3107, 9291, 1), "Use a chisel on the wall.", chiselHighlighted);
        useChiselOnWall.addDialogStep("Of course I'll help you out.");

        talkToAkthankos = new NPCStep(NpcID.BONEGUARD_3577, new RSTile(3105, 9297, 1), "Talk to the Boneguard to finish the quest.");
    }

    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.CHISEL, 1, 500),
                    new GEItem(ItemID.BREAD, 1, 500),
                    new GEItem(ItemID.TINDERBOX, 1, 500),
                    new GEItem(ItemID.CANDLE, 1, 500),
                    new GEItem(ItemID.LOGS, 1, 500),
                    new GEItem(ItemID.OAK_LOGS, 1, 500),
                    new GEItem(ItemID.WILLOW_LOGS, 1, 500),
                    new GEItem(ItemID.MAPLE_LOGS, 1, 500),
                    new GEItem(ItemID.ADAMANT_PICKAXE, 1, 500),
                    new GEItem(ItemID.SOFT_CLAY, 1, 50),
                    new GEItem(ItemID.COAL, 1, 50),
                   // new GEItem(ItemID.SANDSTONE, 1, 50), //TODO Check tradeable?
                    new GEItem(ItemID.LAW_RUNE, 10, 50),
                    new GEItem(ItemID.FIRE_RUNE, 100, 25),
                    new GEItem(ItemID.CHAOS_RUNE, 100, 25),
                    new GEItem(ItemID.STAFF_OF_AIR, 1, 250),
                    new GEItem(ItemID.GRANITE_5KG, 2, 500),
                    new GEItem(ItemID.WATERSKIN4, 5, 100),
                    new GEItem(ItemID.DESERT_ROBE, 1, 500),
                    new GEItem(ItemID.DESERT_ROBES, 1, 500),
                    new GEItem(ItemID.SHANTAY_PASS, 10, 500),
                    new GEItem(ItemID.AMULET_OF_GLORY4, 2, 30),
                    new GEItem(ItemID.STAMINA_POTION[0], 2, 15),
                    new GEItem(ItemID.RING_OF_WEALTH[0], 1, 25)
            )
    );

    InventoryRequirement initialItemReqs = new InventoryRequirement(new ArrayList<>(
            Arrays.asList(
                   // new ItemReq(ItemID.LUMBRIDGE_TELEPORT, 4, 1),
                 //   new ItemReq(ItemID.VARROCK_TELEPORT, 4, 1),
                 //   new ItemReq(ItemID.NECKLACE_OF_PASSAGE[0], 1, 0, true),
                    new ItemReq(ItemID.STAMINA_POTION[0], 1, 0),
                    new ItemReq(ItemID.RING_OF_WEALTH[0], 1, 0, true)
            )
    ));

    BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);

    public List<ItemRequirement> getItemRequirements() {
        ArrayList<ItemRequirement> reqs = new ArrayList<>();
        reqs.add(pickaxe);
        reqs.add(chiselHighlighted);
        reqs.add(softClay);
        reqs.add(breadOrCake);
        reqs.add(tinderbox);
        reqs.add(log);
        reqs.add(oakLog);
        reqs.add(willowLog);
        reqs.add(mapleLog);
        reqs.add(candle);
        reqs.add(coal);
        reqs.add(fireSpellRunes);
        reqs.add(airSpellRunes);
        reqs.add(crumbleUndeadRunes);
        int miningLevel = Skills.SKILLS.MINING.getActualLevel();
        if (miningLevel < 45) {
            reqs.add(granite2);
        }
        if (miningLevel < 35) {
            reqs.add(sandstone52);
        }
        return reqs;
    }


    public int getQuestPointReward() {
        return 2;
    }


   /* public List<ExperienceReward> getExperienceRewards()
    {
        return Arrays.asList(
                new ExperienceReward(Skill.CRAFTING, 7000),
                new ExperienceReward(Skill.MINING, 7000),
                new ExperienceReward(Skill.FIREMAKING, 7000),
                new ExperienceReward(Skill.MAGIC, 7000));
    }


    public List<ItemReward> getItemRewards()
    {
        return Collections.singletonList(new ItemReward("Akthanakos's Camulet", ItemID.CAMULET, 1));
    }*/


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


        int varbit = QuestVarbits.QUEST_ENAKHRAS_LAMENT.getId();
        Log.debug("Enakhra's Lament Varbit is " + varbit);

        // buy initial items on quest start
        if (Utils.getVarBitValue(varbit) == 0) {
            cQuesterV2.status = "Buying items";
            buyStep.buyItems();
        }

        // done quest //TODO check number here
        if (Utils.getVarBitValue(varbit) == 100) {
            cQuesterV2.taskList.remove(this);
            return;
        }

        //load questSteps into a map
        Map<Integer, QuestStep> steps = loadSteps();
        //get the step based on the game setting key in the map
        Optional<QuestStep> step = Optional.ofNullable(steps.get(Utils.getVarBitValue(varbit)));

        // set status
        cQuesterV2.status = step.map(Object::toString).orElse("Unknown Step Name");

        //do the actual step
        step.ifPresent(QuestStep::execute);

        // handle any chats that are failed to be handled by the QuestStep (failsafe)
        if (NPCInteraction.isConversationWindowUp()) {
            Keyboard.typeString(" "); //for aggie step
            NPCInteraction.handleConversation();
        }
        //slow down looping if it gets stuck
        Waiting.waitNormal(200, 20);

    }

    @Override
    public String questName() {
        return "Enakhra's Lament (" +
                Utils.getVarBitValue(QuestVarbits.QUEST_ENAKHRAS_LAMENT.getId()) + ")";
    }

    @Override
    public boolean checkRequirements() {
        return false;
    }

    @Override
    public List<Requirement> getGeneralRequirements() {
        ArrayList<Requirement> req = new ArrayList<>();
        req.add(new SkillRequirement(Skills.SKILLS.CRAFTING, 50));
        req.add(new SkillRequirement(Skills.SKILLS.FIREMAKING, 45));
        req.add(new SkillRequirement(Skills.SKILLS.PRAYER, 43));
        req.add(new SkillRequirement(Skills.SKILLS.MAGIC, 39));
        return req;
    }

    @Override
    public List<ItemRequirement> getBuyList() {
        return null;
    }
}
