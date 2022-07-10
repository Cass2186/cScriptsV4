package scripts.QuestPackages.DesertTreasure;

import org.tribot.api2007.Game;
import org.tribot.api2007.Player;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.*;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.*;
import scripts.*;
import scripts.QuestPackages.DemonSlayer.DemonSlayer;
import scripts.QuestSteps.*;
import scripts.Requirements.*;
import scripts.Requirements.Conditional.NpcCondition;
import scripts.Requirements.Items.ItemCollections;
import scripts.Requirements.Quest.QuestRequirement;
import scripts.Requirements.Util.ConditionalStep;
import scripts.Requirements.Util.Conditions;
import scripts.Requirements.Util.LogicType;
import scripts.Requirements.Util.Operation;
import scripts.Tasks.Priority;

import java.util.*;

public class DesertTreasure implements QuestTask {

    private static DesertTreasure quest;

    public static DesertTreasure get() {
        return quest == null ? quest = new DesertTreasure() : quest;
    }

    //Items Recommended
    ItemRequirement combatGear, food, prayerPotions, restorePotions, energyOrStaminas;

    //Items Required
    ItemRequirement coins650, magicLogs12, steelBars6, moltenGlass6, ashes, charcoal, bloodRune, bones, silverBar, garlicPowder, spice, cake, spikedBoots,
            climbingBoots, faceMask, tinderbox, manyLockpicks, etchings, translation, warmKey, smokeDiamond, shadowDiamond, iceDiamond, bloodDiamond, iceGloves,
            waterSpellOrMelee, cross, ringOfVisibility, antipoison, silverPot, silverPot2, potOfBlood, potWithGarlic, potWithSpice, potComplete, fireSpells,
            spikedBootsEquipped, iceDiamondHighlighted, bloodDiamondHighlighted, smokeDiamondHighlighted,
            shadowDiamondHighlighted;

    Requirement gotBloodDiamond, hadSmokeDiamond, gotIceDiamond, killedDamis, inSmokeDungeon, inFareedRoom, litTorch1, litTorch2, litTorch3, inDraynorSewer,
            litTorch4, unlockedFareedDoor, killedFareed, talkedToRasolo, unlockedCrossChest, gotRing, inShadowDungeon, damis1Nearby, damis2Nearby, talkedToMalak,
            askedAboutKillingDessous, dessousNearby, killedDessous, gaveCake, talkedToTrollChild, killedTrolls, inTrollArea, inPath, killedKamil, onIcePath,
            onIceBridge, smashedIce1, freedTrolls, placedBlood, placedIce, placedSmoke, placedShadow, inFloor1, inFloor2, inFloor3, inFloor4, inAzzRoom;

    QuestStep bringTranslationToArchaeologist,
            buyDrink, bringItemsToEblis, talkToEblisAtMirrors, enterSmokeDungeon, lightTorch1, lightTorch2, lightTorch3,
            lightTorch4, openChest, useWarmKey, enterFareedRoom, killFareed, talkToRasolo, giveCakeToTroll, talkToMalak, askAboutKillingDessous,
            talkToRuantun, blessPot, talkToMalakWithPot, addSpice, addPowder, addPowderToFinish, usePotOnGrave, killDessous, talkToMalakForDiamond,
            getCross, returnCross, enterShadowDungeon, waitForDamis, killDamis1, killDamis2, talkToChildTroll, enterIceGate, enterTrollCave,
            killKamil, climbOnToLedge, goThroughPathGate, breakIce1, breakIce2, talkToTrolls, talkToChildTrollAfterFreeing, placeBlood, placeShadow,
            placeSmoke, placeIce, enterPyramid, goDownFromFirstFloor, goDownFromSecondFloor, goDownFromThirdFloor, enterMiddleOfPyramid, talkToAzz;

    ObjectStep enterSewer;

    NPCStep killIceTrolls, talkToArchaeologist, talkToBartender, talkToEblis,
            talkToExpert, talkToExpertAgain, talkToArchaeologistAgainAfterTranslation;

    UseItemOnNpcStep useAshesonEblis, usedLogsOnEblis,
            useBloodRuneOnEblis, useMoltenGlassOnEblis, useSteelBarsOnEblis;

    ConditionalStep getSmokeDiamond, getBloodDiamond, getIceDiamond, getShadowDiamond, getDiamonds;

    public Map<Integer, QuestStep> loadSteps() {
        setupItemRequirements();
        setupConditions();
        setupSteps();
        Map<Integer, QuestStep> steps = new HashMap<>();

        steps.put(0, talkToArchaeologist);
        steps.put(1, talkToExpert);
        steps.put(2, talkToExpertAgain);
        steps.put(3, talkToExpertAgain);
        steps.put(4, bringTranslationToArchaeologist);
        steps.put(5, talkToArchaeologistAgainAfterTranslation);
        steps.put(6, buyDrink);
        steps.put(7, talkToBartender);
        steps.put(8, talkToEblis);
        steps.put(9, bringItemsToEblis);
        steps.put(10, talkToEblisAtMirrors);

        getSmokeDiamond = new ConditionalStep(enterSmokeDungeon);
        getSmokeDiamond.addStep(new Conditions(inFareedRoom), killFareed);
        getSmokeDiamond.addStep(new Conditions(inSmokeDungeon, unlockedFareedDoor), enterFareedRoom);
        getSmokeDiamond.addStep(new Conditions(inSmokeDungeon, warmKey), useWarmKey);
        getSmokeDiamond.addStep(new Conditions(inSmokeDungeon, litTorch1, litTorch2, litTorch3, litTorch4), openChest);
        getSmokeDiamond.addStep(new Conditions(inSmokeDungeon, litTorch1, litTorch2, litTorch3), lightTorch4);
        getSmokeDiamond.addStep(new Conditions(inSmokeDungeon, litTorch1, litTorch2), lightTorch3);
        getSmokeDiamond.addStep(new Conditions(inSmokeDungeon, litTorch1), lightTorch2);
        getSmokeDiamond.addStep(inSmokeDungeon, lightTorch1);

        getSmokeDiamond.setLockingCondition(killedFareed);

        getShadowDiamond = new ConditionalStep(talkToRasolo);
        getShadowDiamond.addStep(damis2Nearby, killDamis2);
        getShadowDiamond.addStep(damis1Nearby, killDamis1);
        getShadowDiamond.addStep(inShadowDungeon, waitForDamis);
        getShadowDiamond.addStep(gotRing, enterShadowDungeon);
        getShadowDiamond.addStep(unlockedCrossChest, returnCross);
        getShadowDiamond.addStep(talkedToRasolo, getCross);
        getShadowDiamond.setLockingCondition(killedDamis);

        getBloodDiamond = new ConditionalStep(talkToMalak);
        getBloodDiamond.addStep(killedDessous, talkToMalakForDiamond);
        getBloodDiamond.addStep(dessousNearby, killDessous);
        getBloodDiamond.addStep(potComplete, usePotOnGrave);
        getBloodDiamond.addStep(potWithGarlic, addSpice);
        getBloodDiamond.addStep(potWithSpice, addPowderToFinish);
        getBloodDiamond.addStep(potOfBlood, addPowder);
        getBloodDiamond.addStep(silverPot2, talkToMalakWithPot);
        getBloodDiamond.addStep(silverPot, blessPot);
        getBloodDiamond.addStep(new Conditions(askedAboutKillingDessous, inDraynorSewer), talkToRuantun);
        getBloodDiamond.addStep(askedAboutKillingDessous, enterSewer);
        getBloodDiamond.addStep(talkedToMalak, askAboutKillingDessous);
        getBloodDiamond.setLockingCondition(gotBloodDiamond);

        getIceDiamond = new ConditionalStep(giveCakeToTroll);
        getIceDiamond.addStep(new Conditions(onIceBridge, freedTrolls), talkToTrolls);
        getIceDiamond.addStep(new Conditions(freedTrolls), talkToChildTrollAfterFreeing);
        getIceDiamond.addStep(new Conditions(onIceBridge, killedKamil, smashedIce1), breakIce2);
        getIceDiamond.addStep(new Conditions(onIceBridge, killedKamil), breakIce1);
        getIceDiamond.addStep(new Conditions(onIcePath, killedKamil), goThroughPathGate);
        getIceDiamond.addStep(new Conditions(inPath, killedKamil), climbOnToLedge);
        getIceDiamond.addStep(inPath, killKamil);
        getIceDiamond.addStep(new Conditions(killedTrolls, inTrollArea), enterTrollCave);
        getIceDiamond.addStep(inTrollArea, killIceTrolls);
        getIceDiamond.addStep(talkedToTrollChild, enterIceGate);
        getIceDiamond.addStep(gaveCake, talkToChildTroll);
        getIceDiamond.setLockingCondition(gotIceDiamond);

        getDiamonds = new ConditionalStep(getSmokeDiamond);
        getDiamonds.addStep(new Conditions(hadSmokeDiamond, killedDamis, gotBloodDiamond), getIceDiamond);
        getDiamonds.addStep(new Conditions(hadSmokeDiamond, killedDamis), getBloodDiamond);
        getDiamonds.addStep(killedFareed, getShadowDiamond);

        steps.put(11, getDiamonds);

        ConditionalStep placeDiamonds = new ConditionalStep(placeBlood);
        placeDiamonds.addStep(new Conditions(placedBlood, placedSmoke, placedIce), placeShadow);
        placeDiamonds.addStep(new Conditions(placedBlood, placedSmoke), placeIce);
        placeDiamonds.addStep(placedBlood, placeSmoke);

        steps.put(12, placeDiamonds);

        ConditionalStep finishQuest = new ConditionalStep(enterPyramid);
        finishQuest.addStep(inAzzRoom, talkToAzz);
        finishQuest.addStep(inFloor4, enterMiddleOfPyramid);
        finishQuest.addStep(inFloor3, goDownFromThirdFloor);
        finishQuest.addStep(inFloor2, goDownFromSecondFloor);
        finishQuest.addStep(inFloor1, goDownFromFirstFloor);

        steps.put(13, finishQuest);
        steps.put(14, finishQuest);

        return steps;
    }

    public void setupItemRequirements() {
        coins650 = new ItemRequirement(ItemCollections.getCoins(), 650);
        magicLogs12 = new ItemRequirement("Magic logs", ItemID.MAGIC_LOGS, 12);
        magicLogs12.addAlternateItemIDs(1514);
        steelBars6 = new ItemRequirement("Steel bar", ItemID.STEEL_BAR, 6);
        steelBars6.addAlternateItemIDs(2354);
        moltenGlass6 = new ItemRequirement("Molten glass", ItemID.MOLTEN_GLASS, 6);
        moltenGlass6.addAlternateItemIDs(1776);
        ashes = new ItemRequirement("Ashes", ItemID.ASHES);
        charcoal = new ItemRequirement("Charcoal", ItemID.CHARCOAL);
        bloodRune = new ItemRequirement("Blood rune", ItemID.BLOOD_RUNE);
        bones = new ItemRequirement("Bones", ItemID.BONES);
        silverBar = new ItemRequirement("Silver bar", ItemID.SILVER_BAR);
        garlicPowder = new ItemRequirement("Garlic powder", ItemID.GARLIC_POWDER);

        //  garlicPowder.setTooltip("Use a pestle and mortar on a garlic to make powder");
        spice = new ItemRequirement("Spice", ItemID.SPICE);

        cake = new ItemRequirement("Cake", ItemID.CAKE);
        cake.addAlternateItemIDs(ItemID.CHOCOLATE_CAKE);
        cake.setDisplayMatchedItemName(true);

        spikedBoots = new ItemRequirement("Spiked boots", ItemID.SPIKED_BOOTS);
        // spikedBoots.setTooltip("Bring Dunstan in Burthorpe climbing boots and an iron bar to make these");

        spikedBootsEquipped = new ItemRequirement(ItemID.SPIKED_BOOTS, 1,
                true, true);
        //  spikedBootsEquipped.setTooltip("Bring Dunstan in Burthorpe climbing boots and an iron bar to make these");

        climbingBoots = new ItemRequirement("Climbing boots", ItemID.CLIMBING_BOOTS);
        faceMask = new ItemRequirement(ItemID.FACEMASK, 1, true);
        //  faceMask.setTooltip("Slayer mask and gas mask can also be used.");
        faceMask.addAlternateItemIDs(ItemID.FACEMASK, ItemID.SLAYER_HELMET, ItemID.SLAYER_HELMET_I, ItemID.SLAYER_HELMET_I_25177, ItemID.GAS_MASK);
        faceMask.setDisplayMatchedItemName(true);

        tinderbox = new ItemRequirement("Tinderbox", ItemID.TINDERBOX);
        manyLockpicks = new ItemRequirement("Many lockpicks", ItemID.LOCKPICK, -1);
        etchings = new ItemRequirement("Etchings", ItemID.ETCHINGS);
        //  etchings.setTooltip("You can get another from the Archaeologist in the Bedabin Camp");
        translation = new ItemRequirement("Translation", ItemID.TRANSLATION);
        // translation.setTooltip("You can get another from the Archaeological expert in the Exam Centre");

        warmKey = new ItemRequirement("Warm key", ItemID.WARM_KEY);

        smokeDiamond = new ItemRequirement("Smoke diamond", ItemID.SMOKE_DIAMOND);
        // smokeDiamond.setTooltip("You can get another from the room you killed Fareed in inside the Smoke Dungeon");
        shadowDiamond = new ItemRequirement("Shadow diamond", ItemID.SHADOW_DIAMOND);
        //  shadowDiamond.setTooltip("You can get another from the east room of the Shadow Dungeon");

        iceDiamond = new ItemRequirement("Ice diamond", ItemID.ICE_DIAMOND);
        // iceDiamond.setTooltip("You can get another from the Troll Child north of Trollheim");
        bloodDiamond = new ItemRequirement("Blood diamond", ItemID.BLOOD_DIAMOND);
        //  bloodDiamond.setTooltip("You can get another from Malak in Canifis");
//
        smokeDiamondHighlighted = new ItemRequirement("Smoke diamond", ItemID.SMOKE_DIAMOND);
        //  smokeDiamondHighlighted.setTooltip("You can get another from the room you killed Fareed in inside the Smoke Dungeon");
        shadowDiamondHighlighted = new ItemRequirement("Shadow diamond", ItemID.SHADOW_DIAMOND);
        //  shadowDiamondHighlighted.setTooltip("You can get another from the east room of the Shadow Dungeon");

        iceDiamondHighlighted = new ItemRequirement("Ice diamond", ItemID.ICE_DIAMOND);
        //  iceDiamondHighlighted.setTooltip("You can get another from the Troll Child north of Trollheim");
        bloodDiamondHighlighted = new ItemRequirement("Blood diamond", ItemID.BLOOD_DIAMOND);
        //  bloodDiamondHighlighted.setTooltip("You can get another from Malak in Canifis");

        iceGloves = new ItemRequirement(ItemID.ICE_GLOVES, 1, true);
        //  iceGloves.setTooltip("You can kill the Ice Queen under White Wolf Mountain for these");

        waterSpellOrMelee = new ItemRequirement("Water spells or melee gear", -1, -1);
        // waterSpellOrMelee.setDisplayItemId(ItemID.WATER_RUNE);

        cross = new ItemRequirement("Gilded cross", ItemID.GILDED_CROSS);
        // cross.setTooltip("You can get another from the chest in the south of the Bandit Camp");

        ringOfVisibility = new ItemRequirement(ItemID.RING_OF_VISIBILITY, 1, true);
        //  ringOfVisibility.setTooltip("You can get another from Rasolo south of Baxtorian Falls");

        antipoison = new ItemRequirement("Antipoisons", ItemCollections.getAntipoisons());

        silverPot = new ItemRequirement("Silver pot", ItemID.SILVER_POT_4658);
        silverPot2 = new ItemRequirement("Blessed pot", ItemID.BLESSED_POT);
        potOfBlood = new ItemRequirement("Blessed pot", ItemID.BLESSED_POT_4661);

        potWithGarlic = new ItemRequirement("Blessed pot", ItemID.BLESSED_POT_4663);

        potWithSpice = new ItemRequirement("Blessed pot", ItemID.BLESSED_POT_4667);


        potComplete = new ItemRequirement("Blessed pot", ItemID.BLESSED_POT_4665);


        fireSpells = new ItemRequirement("Fire spells", -1, -1);


        combatGear = new ItemRequirement("Decent combat gear", -1, -1);
        //food = new ItemRequirement("Food", ItemCollections.getGoodEatingFood(), -1);
        prayerPotions = new ItemRequirement("Prayer potions", ItemCollections.getPrayerPotions());
        restorePotions = new ItemRequirement("Restore potions", ItemCollections.getRestorePotions());
        energyOrStaminas = new ItemRequirement("Energy/Stamina potions", ItemCollections.getRunRestoreItems());
    }


    RSArea smokeDungeon = new RSArea(new RSTile(3199, 9345, 0), new RSTile(3328, 9412, 0));
    RSArea fareedRoom = new RSArea(new RSTile(3305, 9360, 0), new RSTile(3326, 9393, 0));
    RSArea shadowDungeon = new RSArea(new RSTile(2624, 5051, 0), new RSTile(2757, 5125, 0));
    RSArea draynorSewer = new RSArea(new RSTile(3078, 9641, 0), new RSTile(3129, 9699, 0));
    RSArea trollArea = new RSArea(new RSTile(2839, 3716, 0), new RSTile(2868, 3741, 0));
    RSArea path1 = new RSArea(new RSTile(2872, 3714, 0), new RSTile(2903, 3771, 0));
    RSArea path2 = new RSArea(new RSTile(2817, 3748, 0), new RSTile(2892, 3869, 0));
    RSArea icePath = new RSArea(new RSTile(2830, 3785, 1), new RSTile(2870, 3825, 1));
    RSArea iceBridge = new RSArea(new RSTile(2823, 3807, 2), new RSTile(2855, 3812, 2));
    RSArea floor1 = new RSArea(new RSTile(2893, 4944, 3), new RSTile(2931, 4973, 3));
    RSArea floor2 = new RSArea(new RSTile(2823, 4936, 2), new RSTile(2874, 4977, 2));

    RSArea floor3 = new RSArea(new RSTile(2758, 4935, 1), new RSTile(2811, 4980, 1));
    RSArea floor4 = new RSArea(new RSTile(3186, 9269, 0), new RSTile(3266, 9339, 0));
    RSArea azzRoom = new RSArea(new RSTile(3227, 9310, 0), new RSTile(3239, 9323, 0));


    public void setupConditions() {
        // Given all items, 392 = 1;
        killedDamis = new VarbitRequirement(383, 5);
        hadSmokeDiamond = new Conditions(true, smokeDiamond);
        gotIceDiamond = new Conditions(true, iceDiamond);
        gotBloodDiamond = new VarbitRequirement(373, 4);
        inSmokeDungeon = new AreaRequirement(smokeDungeon);
        inFareedRoom = new AreaRequirement(fareedRoom);
        litTorch1 = new VarbitRequirement(360, 1);
        litTorch2 = new VarbitRequirement(361, 1);
        litTorch3 = new VarbitRequirement(363, 1);
        litTorch4 = new VarbitRequirement(362, 1);
        unlockedFareedDoor = new VarbitRequirement(386, 1);
        killedFareed = new VarbitRequirement(376, 1);
        talkedToRasolo = new VarbitRequirement(383, 2);
        gotRing = new VarbitRequirement(383, 3, Operation.GREATER_EQUAL);

        unlockedCrossChest = new VarbitRequirement(384, 1);

        inShadowDungeon = new AreaRequirement(shadowDungeon);

        damis1Nearby = new NpcCondition(NpcID.DAMIS);
        damis2Nearby = new NpcCondition(NpcID.DAMIS_683);

        talkedToMalak = new VarbitRequirement(373, 1);
        askedAboutKillingDessous = new VarbitRequirement(373, 2);

        inDraynorSewer = new AreaRequirement(draynorSewer);

        dessousNearby = new NpcCondition(NpcID.DESSOUS);

        killedDessous = new VarbitRequirement(373, 3);

        gaveCake = new VarbitRequirement(382, 1);
        talkedToTrollChild = new VarbitRequirement(382, 2, Operation.GREATER_EQUAL);
        // Killed kamil also results in 377 0->1
        killedKamil = new VarbitRequirement(382, 3, Operation.GREATER_EQUAL);
        freedTrolls = new VarbitRequirement(382, 4);
        gotIceDiamond = new VarbitRequirement(382, 5);

        killedTrolls = new VarbitRequirement(378, 5);

        inTrollArea = new AreaRequirement(trollArea);
        inPath = new AreaRequirement(path1, path2);
        onIcePath = new AreaRequirement(icePath);
        onIceBridge = new AreaRequirement(iceBridge);

        smashedIce1 = new VarbitRequirement(380, 1);

        placedSmoke = new VarbitRequirement(387, 1);
        placedShadow = new VarbitRequirement(388, 1);
        placedIce = new VarbitRequirement(389, 1);
        placedBlood = new VarbitRequirement(390, 1);

        inFloor1 = new AreaRequirement(floor1);
        inFloor2 = new AreaRequirement(floor2);
        inFloor3 = new AreaRequirement(floor3);
        inFloor4 = new AreaRequirement(floor4);
        inAzzRoom = new AreaRequirement(azzRoom);
    }

    public void setupSteps() {
        talkToArchaeologist = new NPCStep(NpcID.ARCHAEOLOGIST, new RSTile(3177, 3043, 0), "Talk to the " +
                "Archaeologist in the Bedabin Camp. You can use the flying carpet service from the Shanty Pass to get here.");
        talkToArchaeologist.addDialogStep("Do you have any quests?");
        talkToArchaeologist.addDialogStep("Yes, I'll help you.");

        talkToExpert = new NPCStep(NpcID.ARCHAEOLOGICAL_EXPERT, new RSTile(3359, 3334, 0),
                etchings);
        talkToExpert.addDialogStep("Ask about the Desert Treasure quest.");

        talkToExpertAgain = new NPCStep(NpcID.ARCHAEOLOGICAL_EXPERT, new RSTile(3359, 3334, 0), "Talk to the Archaeological Expert again.");
        talkToExpertAgain.addDialogStep("Ask about the Desert Treasure quest.");

        bringTranslationToArchaeologist = new NPCStep(NpcID.ARCHAEOLOGIST, new RSTile(3177, 3043, 0),
                translation);
        talkToArchaeologistAgainAfterTranslation =
                new NPCStep(NpcID.ARCHAEOLOGIST, new RSTile(3177, 3043, 0), "Talk to the Archaeologist again.");
        talkToArchaeologistAgainAfterTranslation.addDialogStep("Help him");
        buyDrink = new NPCStep(NpcID.BARTENDER, new RSTile(3159, 2978, 0), coins650);
        buyDrink.addDialogStep("Buy a drink");
        buyDrink.addDialogStep("Buy a beer");
        talkToBartender = new NPCStep(NpcID.BARTENDER, new RSTile(3159, 2978, 0), "Talk to the bartender in the Bandit Camp again.");
        talkToBartender.addDialogStep("I heard about four diamonds...");

        talkToEblis = new NPCStep(NpcID.EBLIS, new RSTile(3184, 2989, 0), "Talk to Eblis in the east of the Bandit Camp.");
        talkToEblis.addDialogStep("Tell me of The four diamonds of Azzanadra");
        talkToEblis.addDialogStep("Yes");

        useAshesonEblis = new UseItemOnNpcStep(ItemID.getNotedId(ItemID.CHARCOAL),
                NpcID.EBLIS, new RSTile(3184, 2989, 0));
        usedLogsOnEblis = new UseItemOnNpcStep(ItemID.getNotedId(ItemID.MAGIC_LOGS),
                NpcID.EBLIS, new RSTile(3184, 2989, 0));
        useBloodRuneOnEblis = new UseItemOnNpcStep(ItemID.BLOOD_RUNE,
                NpcID.EBLIS, new RSTile(3184, 2989, 0));
        useMoltenGlassOnEblis = new UseItemOnNpcStep(ItemID.getNotedId(ItemID.MOLTEN_GLASS),
                NpcID.EBLIS, new RSTile(3184, 2989, 0));
        useSteelBarsOnEblis = new UseItemOnNpcStep(ItemID.getNotedId(ItemID.STEEL_BAR),
                NpcID.EBLIS, new RSTile(3184, 2989, 0));
        bringItemsToEblis = new NPCStep(NpcID.EBLIS, new RSTile(3184, 2989, 0),
                //"Use the items on Eblis " +
                //          "in the east of the Bandit Camp. Items can be noted.", ashes, bloodRune, bones,
                charcoal, moltenGlass6, magicLogs12, steelBars6);

        talkToEblisAtMirrors = new NPCStep(NpcID.EBLIS_689, new RSTile(3214, 2954, 0), "Talk to Eblis at the mirrors south east of the Bandit Camp.");

        enterSmokeDungeon = new ObjectStep(ObjectID.SMOKEY_WELL, new RSTile(3310, 2962, 0),
                "Enter the smokey well west of Pollnivneach. You'll need to run a lot, so bring energy/stamina potions if you can.", tinderbox, faceMask, iceGloves, waterSpellOrMelee);
        lightTorch1 = new ObjectStep(NullObjectID.NULL_6405, new RSTile(3323, 9398, 0),
                "Light all the torches in the corners of the dungeon. This is timed, so try to do it as fast as possible. Start with the north east torch, and work your way to the south west.", tinderbox);

        lightTorch2 = new ObjectStep(NullObjectID.NULL_6407, new RSTile(3321, 9355, 0), "Light all the torches in the corners of the dungeon.", tinderbox);


        lightTorch3 = new ObjectStep(NullObjectID.NULL_6411, new RSTile(3207, 9395, 0), "Light all the torches in the corners of the dungeon.", tinderbox);


        lightTorch4 = new ObjectStep(NullObjectID.NULL_6409, new RSTile(3204, 9350, 0), "Light all the torches in the corners of the dungeon.", tinderbox);


        lightTorch1.addSubSteps(lightTorch2, lightTorch3, lightTorch4);

        openChest = new ObjectStep(ObjectID.BURNT_CHEST, new RSTile(3248, 9364, 0), "Open the chest in the middle of the dungeon.");

        useWarmKey = new ObjectStep(ObjectID.GATE_6452, new RSTile(3305, 9376, 0),
                "Use the warm key on the gate in the east of the dungeon. Be prepared to fight Fareed. If you aren't wearing ice gloves he'll unequip your weapon.", warmKey, iceGloves, waterSpellOrMelee);

        enterFareedRoom = new ObjectStep(ObjectID.GATE_6452, new RSTile(3305, 9376, 0),
                "Enter the gate in the east of the dungeon. Be prepared to fight Fareed. If you aren't wearing ice gloves he'll unequip your weapon.", iceGloves, waterSpellOrMelee);
        useWarmKey.addSubSteps(enterFareedRoom);
        killFareed = new NPCStep(NpcID.FAREED, new RSTile(3315, 9375, 0),
                //   "Kill Fareed. Either use melee with ice gloves, or water spells.",
                iceGloves, waterSpellOrMelee);

        talkToRasolo = new NPCStep(NpcID.RASOLO, new RSTile(2531, 3420, 0), "Talk to Rasolo south of Baxtorian Falls.");
        talkToRasolo.addDialogStep("Ask about the Diamonds of Azzanadra");
        // talkToRasolo.addDialogStepWithExclusion("Yes.", "Ask about the Diamonds of Azzanadra");

        getCross = new ObjectStep(ObjectID.SECURE_CHEST, new RSTile(3169, 2967, 0), "Bring antipoison, food, and as many picklocks as you can to the Bandit Camp, and try opening the chest in the south of the Bandit Camp. Keep trying until you succeed.", manyLockpicks, antipoison);

        talkToMalak = new NPCStep(NpcID.MALAK, new RSTile(3496, 3479, 0), "Talk to Malak in the pub in Canifis.");
        talkToMalak.addDialogStep("I am looking for a special Diamond...");
        talkToMalak.addDialogStep("Yes");

        askAboutKillingDessous = new NPCStep(NpcID.MALAK, new RSTile(3496, 3479, 0), "Ask Malek in the pub in Canifis how to kill Dessous.");
        askAboutKillingDessous.addDialogStep("How can I kill Dessous?");

        returnCross = new NPCStep(NpcID.RASOLO, new RSTile(2531, 3420, 0), cross);

        enterShadowDungeon = new ObjectStep(6560, new RSTile(2547, 3421, 0),
                "Equip the Ring of Visibility, then go down the ladder in the area east of Rasolo. It's recommended you bring combat gear to safe spot Damis.", ringOfVisibility);

        //  waitForDamis = new DetailedQuestStep( new RSTile(2745, 5115, 0), "Go to the far eastern room of the dungeon, and wait for Damis to spawn.");

        killDamis1 = new NPCStep(NpcID.DAMIS, new RSTile(2745, 5115, 0), "Kill both phases of Damis. You can safespot him by attacking a bat and keeping the bat between the two of you.");
        killDamis2 = new NPCStep(NpcID.DAMIS_683, new RSTile(2745, 5115, 0), "Kill both phases of Damis. You can safespot him by attacking a bat and keeping the bat between the two of you.");
        killDamis1.addSubSteps(killDamis2);

        enterSewer = new ObjectStep(ObjectID.TRAPDOOR_6434, new RSTile(3084, 3272, 0), "Bring a silver bar to Ruantun in Draynor Sewer.", silverBar);
        // enterSewer.addAlternateObjects(ObjectID.TRAPDOOR_6435);
        enterSewer.addDialogStep("Actually, I don't need to know anything.");
        talkToRuantun = new NPCStep(NpcID.RUANTUN, new RSTile(3112, 9690, 0), silverBar);
        talkToRuantun.addSubSteps(enterSewer);

        blessPot = new NPCStep(NpcID.HIGH_PRIEST, new RSTile(2851, 3350, 0),
                silverPot);
        talkToMalakWithPot = new NPCStep(NpcID.MALAK, new RSTile(3496, 3479, 0),
                silverPot2);

        addSpice = new UseItemOnItemStep(ItemID.SPICE, ItemID.BLESSED_POT, !Inventory.contains(ItemID.SPICE),
                potWithGarlic, spice);
        addPowder = new UseItemOnItemStep(ItemID.GARLIC_POWDER, ItemID.BLESSED_POT_4661,
                !Inventory.contains(ItemID.SPICE), potOfBlood, garlicPowder);
        // addPowderToFinish = new UseItemOnItemStep( "Add the garlic powder to the blessed pot.",
        //        potWithSpice, garlicPowder);
        addPowder.addSubSteps(addPowderToFinish);

        usePotOnGrave = new ObjectStep(ObjectID.VAMPYRE_TOMB, new RSTile(3570, 3402, 0),
                "Use the blessed pot on the vampyre tomb in the graveyard south east of Canifis. Be prepared to fight Dessous.", potComplete);

        killDessous = new NPCStep(NpcID.DESSOUS, new RSTile(3570, 3403, 0), "Kill Dessous.");

        talkToMalakForDiamond = new NPCStep(NpcID.MALAK, new RSTile(3496, 3479, 0), "Return to Malak in Canifis to get the Blood Diamond.");

        giveCakeToTroll = new NPCStep(NpcID.TROLL_CHILD_697, new RSTile(2835, 3740, 0),
                //  "Use a cake on the Troll Child north of Trollheim.",
                cake, spikedBoots);
        talkToChildTroll = new NPCStep(NpcID.TROLL_CHILD, new RSTile(2835, 3740, 0), "Talk to the Troll Child north of Trollheim.");
        talkToChildTroll.addDialogStep("Yes");

        enterIceGate = new ObjectStep(ObjectID.ICE_GATE, new RSTile(2838, 3740, 0), "Enter",
                //"Enter the ice gate east of the troll child. Make sure you're prepared for combat, " +
                //        "and your stats to be continually drained.",
                fireSpells, spikedBoots);
        killIceTrolls = new NPCStep(NpcID.ICE_TROLL_699, new RSTile(2854, 3733, 0));
        // killIceTrolls.addAlternateNpcs(NpcID.ICE_TROLL_700, NpcID.ICE_TROLL_701, NpcID.ICE_TROLL_702, NpcID.ICE_TROLL_703, NpcID.ICE_TROLL_704, NpcID.ICE_TROLL_705);
        enterTrollCave = new ObjectStep(6440, new RSTile(2869, 3719, 0), "Continue along the path through the cave to the east.");

        killKamil = new NPCStep(NpcID.KAMIL, new RSTile(2863, 3757, 0),
                //   "walk path until you find Kamil. Kill him with fire spells. protect from melee.",
                fireSpells);
        climbOnToLedge = new ObjectStep(ObjectID.ICE_LEDGE, new RSTile(2837, 3804, 0),
                "Equip the spiked boots, then continue along the path until you reach an ice ledge. Climb up it.", spikedBootsEquipped);
        goThroughPathGate = new ObjectStep(ObjectID.ICE_GATE_6462, new RSTile(2854, 3811, 1),
                "Follow the Ice Path up to the top and enter the gate there.");
        breakIce1 = new NPCStep(NpcID.ICE_BLOCK, new RSTile(2826, 3808, 2),
                //    "Break the ice surrounding the trolls at the end of the path. Fire spells are effective for this.",
                fireSpells);
        breakIce2 = new NPCStep(NpcID.ICE_BLOCK_707, new RSTile(2826, 3812, 2),
                //  "Break the ice surrounding the trolls at the end of the path. Fire spells are effective for this.",
                fireSpells);
        talkToTrolls = new NPCStep(NpcID.TROLL_MOTHER, new RSTile(2826, 3812, 2),
                "Talk to the troll parents at the end of the Ice Path.");
        talkToChildTrollAfterFreeing = new NPCStep(NpcID.TROLL_CHILD, new RSTile(2835, 3740, 0),
                "Talk to the Troll Child north of Trollheim to get the ice diamond.");

        placeBlood = new ObjectStep(NullObjectID.NULL_6482, new RSTile(3221, 2910, 0),
                "Place all the diamonds in the obelisks around the pyramid south east of the Bandit Camp. Note a " +
                        "mysterious stranger can appear and attack you whilst you're holding the diamonds.",
                bloodDiamondHighlighted, smokeDiamond,
                iceDiamond, shadowDiamond);


        placeSmoke = new ObjectStep(NullObjectID.NULL_6485, new RSTile(3245, 2910, 0),
                "Place all the diamonds in the obelisks around the pyramid south east of the Bandit Camp. Note a " +
                        "mysterious stranger can appear and attack you whilst you're holding the diamonds.",
                smokeDiamondHighlighted, iceDiamond, shadowDiamond);


        placeIce = new ObjectStep(NullObjectID.NULL_6488, new RSTile(3245, 2886, 0),
                "Place all the diamonds in the obelisks around the pyramid south east of the Bandit Camp. Note a " +
                        "mysterious stranger can appear and attack you whilst you're holding the diamonds.", iceDiamondHighlighted, shadowDiamond);


        placeShadow = new ObjectStep(NullObjectID.NULL_6491, new RSTile(3221, 2886, 0),
                "Place all the diamonds in the obelisks around the pyramid south east of the Bandit Camp. Note a " +
                        "mysterious stranger can appear and attack you whilst you're holding the diamonds.",
                shadowDiamondHighlighted);


        placeBlood.addSubSteps(placeSmoke, placeShadow, placeIce);

        enterPyramid = new ObjectStep(ObjectID.LADDER_6497, new RSTile(3233, 2897, 0),
                "Bring any energy/stamina potions you have, some food, and enter the pyramid south east of the Bandit Camp.", energyOrStaminas, food, antipoison);

        goDownFromFirstFloor = new ObjectStep(ObjectID.LADDER_6498, new RSTile(2909, 4964, 3), "Go down to the bottom of the pyramid. " +
                "You may randomly fall out of the pyramid as you traverse it and need to start again.");
        goDownFromSecondFloor = new ObjectStep(ObjectID.LADDER_6499, new RSTile(2846, 4973, 2), "Go down to the bottom of the pyramid.");
        goDownFromThirdFloor = new ObjectStep(ObjectID.LADDER_6500, new RSTile(2784, 4941, 1), "Go down to the bottom of the pyramid.");

        goDownFromFirstFloor.addSubSteps(goDownFromSecondFloor, goDownFromThirdFloor);

        enterMiddleOfPyramid = new ObjectStep(ObjectID.DOORWAY_6553, new RSTile(3234, 9324, 0), "Enter the central room of the bottom floor.");

        talkToAzz = new NPCStep(NpcID.AZZANADRA, new RSTile(3232, 9317, 0), "Talk to Azzanadra to finish the quest!");
    }


    public List<ItemRequirement> getItemRequirements() {
        return Arrays.asList(coins650, magicLogs12, steelBars6, moltenGlass6, ashes, charcoal,
                bloodRune, bones, silverBar, garlicPowder, spice, cake, spikedBoots, climbingBoots, faceMask, tinderbox, manyLockpicks);
    }


    public List<ItemRequirement> getItemRecommended() {
        return Arrays.asList(combatGear, food, prayerPotions, energyOrStaminas, restorePotions);
    }


    public List<String> getCombatRequirements() {
        ArrayList<String> reqs = new ArrayList<>();
        reqs.add("Dessous (level 139)");
        reqs.add("Kamil (level 154)");
        reqs.add("Fareed (level 167)");
        reqs.add("Damis (level 103, then level 174 in second phase)");
        reqs.add("5 ice trolls (level 120-124)");
        return reqs;
    }


    InventoryRequirement shadowDiamondItemsPart1 = new InventoryRequirement(new ArrayList<>(
            Arrays.asList(
                    new ItemReq(ItemID.STAMINA_POTION[0], 2, 1),
                    new ItemReq(ItemID.AMULET_OF_GLORY[2], 2, 0, true, true),
                    new ItemReq(ItemID.BLACK_DHIDE_BODY, 1, true, true),
                    new ItemReq(ItemID.BLACK_DHIDE_CHAPS, 1, true, true),
                    new ItemReq(ItemID.BLACK_DHIDE_VAMBRACES, 1, true, true),
                    new ItemReq(ItemID.RUNE_CROSSBOW, 1, true, true),
                    new ItemReq(ItemID.RUNITE_BOLTS, 400, 50, true, true),
                    new ItemReq(ItemID.SNAKESKIN_BOOTS, 1, true, true),
                    new ItemReq(ItemID.ANTIDOTE_PLUS_PLUS[0], 1),
                    new ItemReq(ItemID.RING_OF_WEALTH[0], 1, 0, true, true),
                    new ItemReq.Builder().id(ItemID.LOCKPICK).isItemNoted(true)
                            .amount(200)
                            .build(),
                    new ItemReq.Builder().id(ItemID.SHARK)
                            .isItemNoted(true)
                            .amount(20)
                            .build(),
                    new ItemReq(ItemID.RING_OF_VISIBILITY, 1, true),
                    new ItemReq(ItemID.ARDOUGNE_TELEPORT, 5, 1),
                    new ItemReq(ItemID.COINS_995, 100000, 2000),
                    new ItemReq(ItemID.SHARK, 5, 0)
            )
    ));

    InventoryRequirement shadowDiamondItemsPart2 = new InventoryRequirement(new ArrayList<>(
            Arrays.asList(
                    new ItemReq(ItemID.STAMINA_POTION[0], 2, 1),
                    new ItemReq(ItemID.AMULET_OF_GLORY[2], 2, 0, true, true),
                    new ItemReq(ItemID.BLACK_DHIDE_BODY, 1, true, true),
                    new ItemReq(ItemID.BLACK_DHIDE_CHAPS, 1, true, true),
                    new ItemReq(ItemID.BLACK_DHIDE_VAMBRACES, 1, true, true),
                    new ItemReq(ItemID.RUNE_CROSSBOW, 1, true, true),
                    new ItemReq(ItemID.RUNITE_BOLTS, 400, 50, true, true),
                    new ItemReq(ItemID.SNAKESKIN_BOOTS, 1, true, true),
                    new ItemReq(ItemID.RING_OF_WEALTH[0], 1, 0, true, true),
                    new ItemReq(ItemID.RING_OF_VISIBILITY, 1, 0, true),
                    new ItemReq(ItemID.SUPER_RESTORE4, 10, 1),
                    new ItemReq(ItemID.RANGING_POTION[0], 1, 1),
                    new ItemReq(ItemID.ARDOUGNE_TELEPORT, 5, 1),
                    new ItemReq(ItemID.SHARK, 10, 1)
            )
    ));

    private void chargePrayerArdougne() {
        if (Prayer.getPrayerPoints() < 30) {
            Log.info("Recharging prayer at ardougne church");
            if (PathingUtil.walkToTile(new WorldTile(2617, 3308, 0))) {
                Optional<GameObject> altar = Query.gameObjects().nameContains("Altar")
                        .actionContains("Pray-at").findClosestByPathDistance();
                if (altar.map(a -> a.interact("Pray-at")).orElse(false)) {
                    Waiting.waitUntilAnimating(7000);
                }
            }
        }
    }


    private boolean goDownLadderToSmokeArea() {
        cQuesterV2.status = "Going to Smoke dungeon";
        if (!smokeDungeon.contains(Player.getPosition()) &&
                (Utils.equipItem(ItemID.RING_OF_VISIBILITY) ||
                        Equipment.contains(ItemID.RING_OF_VISIBILITY)) &&
                PathingUtil.walkToTile(new WorldTile(2547, 3442, 0))) {
            Optional<GameObject> ladder = Query.gameObjects().nameContains("Ladder")
                    .actionContains("Climb-down")
                    .findClosestByPathDistance();
            if (ladder.map(l -> l.interact("Climb-down")).orElse(false)) {
                return Waiting.waitUntil(7000, 250, () -> smokeDungeon.contains(Player.getPosition()));
            }
        }
        return smokeDungeon.contains(Player.getPosition());
    }

    Area firstHalfOfSmokeDungeon = Area.fromRectangle(new WorldTile(2624, 5076, 0),
            new WorldTile(2686, 5052, 0));
    Area centrePartOfSmokeDungeon = Area.fromRectangle(new WorldTile(2670, 5119, 0),
            new WorldTile(2702, 5065, 0));
    Area lastSmokeDungeonArea = Area.fromRectangle(new WorldTile(2748, 5056, 0), new WorldTile(2687, 5077, 0));
    Area DAMIS_AREA = Area.fromPolygon(
            new WorldTile(2728, 5087, 0),
            new WorldTile(2731, 5071, 0),
            new WorldTile(2739, 5065, 0),
            new WorldTile(2752, 5071, 0),
            new WorldTile(2752, 5089, 0));

    private void navigateSmokeDungeon() {
        if (firstHalfOfSmokeDungeon.containsMyPlayer()) {
            Log.info("Navigating first half");
            PathingUtil.localNav(new LocalTile(2678, 5060, 0));
            PathingUtil.movementIdle();
        }
        if (centrePartOfSmokeDungeon.containsMyPlayer()) {
            Log.info("Navigating centre part");
            PathingUtil.localNav(new LocalTile(2692, 5064, 0));
            PathingUtil.movementIdle();
        }
        if (lastSmokeDungeonArea.containsMyPlayer()) {
            Log.info("Navigating to end of dungeon");
            PathingUtil.localNav(new LocalTile(2735, 5074, 0));
            PathingUtil.movementIdle();
        }
    }

    private boolean drinkSuperRestore(boolean wait) {
        Optional<InventoryItem> potion = Query.inventory().nameContains("Super restore").findClosestToMouse();
        int pPoints = Prayer.getPrayerPoints();
        if (wait) {
            for (int i = 0; i < 4; i++) {
                if (potion.map(p -> p.click("Drink")).orElse(false) &&
                        Waiting.waitUntil(800, 50, () ->
                                Prayer.getPrayerPoints() > pPoints))
                    return true;
            }
        }
        return potion.map(p -> p.click("Drink")).orElse(false);
    }


    private void killDamis() {
        if (DAMIS_AREA.containsMyPlayer()) {
            Log.info("Killing Damis");
            if (Prayer.getPrayerPoints() < 12) {
                drinkSuperRestore(true);
            }
            if (!CombatUtil.isInteractingWith("Damis")) {
                Optional<Npc> damis = Query.npcs().nameContains("Damis").findClosestByPathDistance();
                if (damis.map(d -> d.interact("Attack")).orElse(false)) {
                    Waiting.waitUntil(4500, 200, () -> CombatUtil.isInteractingWith("Damis") ||
                            Prayer.getPrayerPoints() < 12);
                }
            } else {
                //already fighting
                if (Waiting.waitUntil(10000, 200, () ->
                        !CombatUtil.isInteractingWith("Damis") || Prayer.getPrayerPoints() < 12))
                    Log.warn("No longer interacting");
            }
        }
    }

    @Override
    public List<Requirement> getGeneralRequirements() {
        ArrayList<Requirement> req = new ArrayList<>();
        req.add(new QuestRequirement(Quest.THE_DIG_SITE, Quest.State.COMPLETE));
        req.add(new QuestRequirement(Quest.TEMPLE_OF_IKOV, Quest.State.COMPLETE));
        req.add(new QuestRequirement(Quest.THE_TOURIST_TRAP, Quest.State.COMPLETE));
        req.add(new QuestRequirement(Quest.TROLL_STRONGHOLD, Quest.State.COMPLETE));
        req.add(new QuestRequirement(Quest.PRIEST_IN_PERIL, Quest.State.COMPLETE));
        req.add(new QuestRequirement(Quest.WATERFALL_QUEST, Quest.State.COMPLETE));
        req.add(new SkillRequirement(Skills.SKILLS.THIEVING, 53));
        req.add(new SkillRequirement(Skills.SKILLS.MAGIC, 50));
        req.add(new SkillRequirement(Skills.SKILLS.FIREMAKING, 50));
        new QuestRequirement(Quest.PLAGUE_CITY, Quest.State.COMPLETE);
        return req;
    }

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
        Log.warn("Varbit ID is " + QuestVarbits.QUEST_DESERT_TREASURE.getId());
        int varbit = Utils.getVarBitValue(QuestVarbits.QUEST_DESERT_TREASURE.getId());
        if (isComplete()) {
            cQuesterV2.taskList.remove(this);
            return;
        }
        if (varbit == 0) {
            Log.info("Staring quest: Desert treasure");
            //TODO buy items etc
        }
        if (varbit == 11) {
            goDownLadderToSmokeArea();
            navigateSmokeDungeon();
        }
        Log.info("[Debug]: Desert treasure varbit value is " + varbit);
        Map<Integer, QuestStep> steps = loadSteps();
        Optional<QuestStep> step = Optional.ofNullable(steps.get(varbit));
        step.ifPresent(s -> cQuesterV2.status = s.getClass().toGenericString());
        // step.ifPresent(QuestStep::execute);
        Waiting.waitNormal(200, 20);
    }

    @Override
    public String questName() {
        return "Desert Treasure";
    }

    @Override
    public boolean checkRequirements() {
        return false;
    }


    @Override
    public List<ItemRequirement> getBuyList() {
        return null;
    }

    @Override
    public boolean isComplete() {
        return Quest.DESERT_TREASURE.getState().equals(Quest.State.COMPLETE);
    }
}
