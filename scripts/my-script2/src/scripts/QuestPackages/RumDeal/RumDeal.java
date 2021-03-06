package scripts.QuestPackages.RumDeal;

import org.tribot.api2007.Player;
import org.tribot.api2007.Skills;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.*;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.Area;
import org.tribot.script.sdk.types.Widget;
import org.tribot.script.sdk.types.WorldTile;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.RuneMysteries.RuneMysteries;
import scripts.QuestSteps.*;
import scripts.Requirements.*;
import scripts.Requirements.Conditional.NpcCondition;
import scripts.Requirements.Quest.QuestRequirement;
import scripts.Requirements.Util.ConditionalStep;
import scripts.Requirements.Util.Conditions;
import scripts.Tasks.Priority;

import java.nio.file.Path;
import java.util.*;

public class RumDeal implements QuestTask {


    private static RumDeal quest;

    public static RumDeal get() {
        return quest == null ? quest = new RumDeal() : quest;
    }

    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.RAKE, 1, 500),
                    new GEItem(ItemID.SEED_DIBBER, 1, 500),
                    new GEItem(ItemID.SHARK, 25, 20),
                    new GEItem(ItemID.BUCKET, 1, 500),
                    new GEItem(ItemID.FENKENSTRAINS_CASTLE_TELEPORT, 5, 30),
                    new GEItem(ItemID.STAMINA_POTION[0], 3, 15),
                    new GEItem(ItemID.AMULET_OF_GLORY[2], 1, 15),
                    new GEItem(ItemID.RING_OF_WEALTH[0], 1, 25)
            )
    );
    InventoryRequirement initialItemReqs = new InventoryRequirement(new ArrayList<>(
            Arrays.asList(
                    new ItemReq(ItemID.RAKE, 1),
                    new ItemReq(ItemID.SEED_DIBBER, 1),
                    new ItemReq(ItemID.SHARK, 15, 1),
                    new ItemReq(ItemID.BUCKET, 1),
                    new ItemReq(ItemID.FENKENSTRAINS_CASTLE_TELEPORT, 5, 1),
                    new ItemReq(ItemID.AMULET_OF_GLORY[2], 1, 0, true, true),
                    new ItemReq(ItemID.STAMINA_POTION[0], 3, 0),
                    new ItemReq(ItemID.RING_OF_WEALTH[0], 1, 0, true)
            )
    ));

    BuyItemsStep buyitems = new BuyItemsStep(itemsToBuy);

    ItemRequirement combatGear, dibber, rake, slayerGloves, blindweedSeed, rakeHighlight, blindweedSeedHighlight, blindweed, blindweedHighlight, bucket, bucketHighlight,
            stagnantWater, stagnantWaterHighlight, netBowl, sluglings5, holyWrench, wrench, spiderCarcass, spiderCarcassHighlight, swill;

    Requirement prayerPoints47;

    Requirement onIsland, onIslandF1, onIslandF2, onIslandF0, rakedPatch, plantedPatch, grownPatch, onNorthIsland, added5Sluglings,
            inSpiderRoom, evilSpiritNearby, carcassNearby;

    QuestStep talkToPete, talkToBraindeath, goDownstairs, rakePatch, plantSeed, waitForGrowth, pickPlant, goUpStairsWithPlant, talkToBraindeathWithPlant, talkToPeteWithPlant,
            climbUpToDropPlant, dropPlant, goDownFromDropPlant, talkToBraindeathAfterPlant, goDownForWater, openGate, useBucketOnWater, goUpWithWater, goUpToDropWater, dropWater,
            goDownFromTopAfterDropWater, talkToBraindeathAfterWater, goDownFromTop, goUpFromBottom, goDownAfterSlugs, talkToBraindeathAfterSlugs, talkToDavey, useWrenchOnControl,
            goUpFromSpiders, talkToBraindeathAfterSpirit, goDownToSpiders, goUpFromSpidersWithCorpse, goUpToDropSpider, dropSpider, goDownAfterSpider,
            talkToBraindeathAfterSpider, useBucketOnTap, goDownToDonnie, talkToDonnie, goUpToBraindeathToFinish, talkToBraindeathToFinish, pickUpCarcass;

    NPCStep killSpider, killSpirit;
    SlugSteps getSlugs;

    //RSArea()s
    RSArea island, islandF0, islandF1, islandF2, northIsland, spiderRoom;


    public Map<Integer, QuestStep> loadSteps() {
        loadRSAreas();
        setupItemRequirements();
        setupConditions();
        setupSteps();
        Map<Integer, QuestStep> steps = new HashMap<>();

        steps.put(0, talkToPete);
        steps.put(1, talkToPete);

        ConditionalStep startOff = new ConditionalStep(talkToPete);
        startOff.addStep(onIsland, talkToBraindeath);

        steps.put(2, startOff);

        ConditionalStep growBlindweed = new ConditionalStep(talkToPete);
        growBlindweed.addStep(grownPatch, pickPlant);
        growBlindweed.addStep(plantedPatch, waitForGrowth);
        growBlindweed.addStep(inSpiderRoom, goUpFromSpiders);
        growBlindweed.addStep(new Conditions(onIslandF0, rakedPatch), plantSeed);
        growBlindweed.addStep(onIslandF2, goDownFromTop);
        growBlindweed.addStep(onIslandF0, rakePatch);
        growBlindweed.addStep(onIslandF1, goDownstairs);

        steps.put(3, growBlindweed);
        steps.put(4, growBlindweed);

        ConditionalStep bringPlant = new ConditionalStep(talkToPeteWithPlant);
        //bringPlant.addStep(inSpiderRoom, goUpFromSpiders);
        bringPlant.addStep(new Conditions(onIslandF1), talkToBraindeathWithPlant);
        // bringPlant.addStep(new Conditions(onIslandF0), goUpStairsWithPlant);
        // bringPlant.addStep(onIslandF2, goDownFromTop);

        steps.put(5, bringPlant);

        ConditionalStep addPlant = new ConditionalStep(talkToPeteWithPlant);
        addPlant.addStep(inSpiderRoom, goUpFromSpiders);
        addPlant.addStep(onIslandF1, climbUpToDropPlant);
        addPlant.addStep(onIslandF0, goUpFromBottom);
        addPlant.addStep(onIslandF2, dropPlant);

        steps.put(6, addPlant);

        ConditionalStep talkAfterPlant = new ConditionalStep(talkToPete);
        talkAfterPlant.addStep(inSpiderRoom, goUpFromSpiders);
        talkAfterPlant.addStep(onIslandF1, talkToBraindeathAfterPlant);
        talkAfterPlant.addStep(onIslandF0, goUpFromBottom);
        talkAfterPlant.addStep(onIslandF2, goDownFromDropPlant);

        steps.put(7, talkAfterPlant);

        ConditionalStep getWater = new ConditionalStep(talkToPete);
        getWater.addStep(inSpiderRoom, goUpFromSpiders);
        getWater.addStep(new Conditions(onIslandF2, stagnantWater), dropWater);
        getWater.addStep(new Conditions(onIslandF1, stagnantWater), goUpToDropWater);
        getWater.addStep(new Conditions(onIslandF0, stagnantWater), goUpWithWater);
        getWater.addStep(onNorthIsland, useBucketOnWater);
        getWater.addStep(onIslandF0, openGate);
        getWater.addStep(onIslandF1, goDownForWater);
        getWater.addStep(onIslandF2, goDownFromTop);

        steps.put(8, getWater);

        ConditionalStep putWater = new ConditionalStep(talkToPete);
        putWater.addStep(inSpiderRoom, goUpFromSpiders);
        putWater.addStep(onIslandF2, dropWater);
        putWater.addStep(onIslandF1, goUpToDropWater);
        putWater.addStep(onIslandF0, goUpWithWater);

        steps.put(9, putWater);

        ConditionalStep startSlug = new ConditionalStep(talkToPete);
        startSlug.addStep(inSpiderRoom, goUpFromSpiders);
        startSlug.addStep(onIslandF2, goDownFromTopAfterDropWater);
        startSlug.addStep(onIslandF1, talkToBraindeathAfterWater);
        startSlug.addStep(onIslandF0, goUpFromBottom);

        steps.put(10, startSlug);

        ConditionalStep getSlugsSteps = new ConditionalStep(getSlugs);
        getSlugsSteps.addStep(inSpiderRoom, goUpFromSpiders);

        steps.put(11, getSlugsSteps);

        ConditionalStep startSpirit = new ConditionalStep(talkToPete);
        startSpirit.addStep(inSpiderRoom, goUpFromSpiders);
        startSpirit.addStep(onIslandF1, talkToBraindeathAfterSlugs);
        startSpirit.addStep(onIslandF2, goDownAfterSlugs);
        startSpirit.addStep(onIslandF0, goUpFromBottom);

        // 1355 0->1
        steps.put(12, startSpirit);

        ConditionalStep killSpiritSteps = new ConditionalStep(talkToPete);
        killSpiritSteps.addStep(inSpiderRoom, goUpFromSpiders);
        killSpiritSteps.addStep(new Conditions(onIslandF1, holyWrench, evilSpiritNearby), killSpirit);
        killSpiritSteps.addStep(new Conditions(onIslandF1, holyWrench), useWrenchOnControl);
        killSpiritSteps.addStep(onIslandF1, talkToDavey);
        killSpiritSteps.addStep(onIslandF2, goDownFromTop);
        killSpiritSteps.addStep(onIslandF0, goUpFromBottom);

        steps.put(13, killSpiritSteps);

        ConditionalStep spiderStepsStart = new ConditionalStep(talkToPete);
        spiderStepsStart.addStep(inSpiderRoom, goUpFromSpiders);
        spiderStepsStart.addStep(onIslandF1, talkToBraindeathAfterSpirit);
        spiderStepsStart.addStep(onIslandF2, goDownFromTop);
        spiderStepsStart.addStep(onIslandF0, goUpFromBottom);

        steps.put(14, spiderStepsStart);

        ConditionalStep spiderSteps = new ConditionalStep(talkToPete);
        spiderSteps.addStep(new Conditions(onIslandF2, spiderCarcass), dropSpider);
        spiderSteps.addStep(new Conditions(onIslandF1, spiderCarcass), goUpToDropSpider);
        spiderSteps.addStep(new Conditions(inSpiderRoom, spiderCarcass), goUpFromSpidersWithCorpse);
        spiderSteps.addStep(carcassNearby, pickUpCarcass);
        spiderSteps.addStep(inSpiderRoom, killSpider);
        spiderSteps.addStep(onIslandF1, goDownToSpiders);
        spiderSteps.addStep(onIslandF2, goDownFromTop);
        spiderSteps.addStep(onIslandF0, goUpFromBottom);

        steps.put(15, spiderSteps);

        ConditionalStep makeBrewForDonnieStart = new ConditionalStep(talkToPete);
        makeBrewForDonnieStart.addStep(inSpiderRoom, goUpFromSpiders);
        makeBrewForDonnieStart.addStep(onIslandF1, talkToBraindeathAfterSpider);
        makeBrewForDonnieStart.addStep(onIslandF2, goDownFromTop);
        makeBrewForDonnieStart.addStep(onIslandF0, goUpFromBottom);

        steps.put(16, makeBrewForDonnieStart);

        ConditionalStep giveBrewToDonnie = new ConditionalStep(talkToPete);
        giveBrewToDonnie.addStep(new Conditions(onIslandF0, swill), talkToDonnie);
        giveBrewToDonnie.addStep(new Conditions(onIslandF1, swill), goDownToDonnie);
        giveBrewToDonnie.addStep(inSpiderRoom, goUpFromSpiders);
        giveBrewToDonnie.addStep(onIslandF1, useBucketOnTap);
        giveBrewToDonnie.addStep(onIslandF2, goDownAfterSpider);
        giveBrewToDonnie.addStep(onIslandF0, goUpFromBottom);

        steps.put(17, giveBrewToDonnie);

        ConditionalStep finishQuest = new ConditionalStep(talkToPete);
        finishQuest.addStep(inSpiderRoom, goUpFromSpiders);
        finishQuest.addStep(onIslandF1, talkToBraindeathToFinish);
        finishQuest.addStep(onIslandF2, goDownFromTop);
        finishQuest.addStep(onIslandF0, goUpToBraindeathToFinish);

        steps.put(18, finishQuest);

        return steps;
    }

    public void setupItemRequirements() {
        combatGear = new ItemRequirement("Combat gear", -1, -1);

        slayerGloves = new ItemRequirement("Slayer gloves", ItemID.SLAYER_GLOVES);
        slayerGloves.addAlternateItemID(ItemID.SLAYER_GLOVES_6720);
        blindweedSeed = new ItemRequirement("Blindweed seed", ItemID.BLINDWEED_SEED);
        blindweedSeedHighlight = new ItemRequirement("Blindweed seed", ItemID.BLINDWEED_SEED);

        rake = new ItemRequirement("Rake", ItemID.RAKE);
        rakeHighlight = new ItemRequirement("Rake", ItemID.RAKE);

        dibber = new ItemRequirement("Seed dibber", ItemID.SEED_DIBBER);
        blindweed = new ItemRequirement("Blindweed", ItemID.BLINDWEED);
        // blindweed.setTooltip("You can get another from Captain Braindeath");

        blindweedHighlight = new ItemRequirement("Blindweed", ItemID.BLINDWEED);
        // blindweedHighlight.setTooltip("You can get another from Captain Braindeath");

        bucket = new ItemRequirement("Bucket", ItemID.BUCKET);

        bucketHighlight = new ItemRequirement("Bucket", ItemID.BUCKET);

        stagnantWater = new ItemRequirement("Bucket of water", ItemID.BUCKET_OF_WATER_6712);
        // stagnantWater.setTooltip("You can get more from Captain Braindeath");

        stagnantWaterHighlight = new ItemRequirement("Bucket of water", ItemID.BUCKET_OF_WATER_6712);
        // stagnantWaterHighlight.setTooltip("You can get more from Captain Braindeath");

        netBowl = new ItemRequirement("Fishbowl and net", ItemID.FISHBOWL_AND_NET);
        //netBowl.setTooltip("You can get another from Captain Braindeath, or make it with a fishbowl and large net");

        sluglings5 = new ItemRequirement("Sluglings", ItemID.SLUGLINGS, 5);

        holyWrench = new ItemRequirement("Holy wrench", ItemID.HOLY_WRENCH);


        wrench = new ItemRequirement("Wrench", ItemID.WRENCH);
        // wrench.setTooltip("You can get another from Captain Braindeath");

        spiderCarcass = new ItemRequirement("Fever spider body", ItemID.FEVER_SPIDER_BODY);

        spiderCarcassHighlight = new ItemRequirement("Fever spider body", ItemID.FEVER_SPIDER_BODY);
        // spiderCarcassHighlight.setHighlightInInventory(true);

        swill = new ItemRequirement("Unsanitary swill", ItemID.UNSANITARY_SWILL);

        prayerPoints47 = new ItemRequirement("47 prayer points", -1, -1);
    }

    public void loadRSAreas() {
        island = new RSArea(new RSTile(2110, 5054, 0), new RSTile(2178, 5185, 2));
        islandF0 = new RSArea(new RSTile(2110, 5054, 0), new RSTile(2178, 5185, 0));
        islandF1 = new RSArea(new RSTile(2110, 5054, 1), new RSTile(2178, 5185, 1));
        islandF2 = new RSArea(new RSTile(2110, 5054, 2), new RSTile(2178, 5185, 2));
        northIsland = new RSArea(new RSTile(2110, 5099, 2), new RSTile(2178, 5185, 0));
        spiderRoom = new RSArea(new RSTile(2138, 5091, 0), new RSTile(2164, 5106, 0));
    }

    public void setupConditions() {
        onIsland = new AreaRequirement(island);
        onIslandF0 = new AreaRequirement(islandF0);
        onIslandF1 = new AreaRequirement(islandF1);
        onIslandF2 = new AreaRequirement(islandF2);
        onNorthIsland = new AreaRequirement(northIsland);
        inSpiderRoom = new AreaRequirement(spiderRoom);

        rakedPatch = new VarbitRequirement(1366, 3);
        plantedPatch = new VarbitRequirement(1366, 4);
        grownPatch = new VarbitRequirement(1366, 5);

        added5Sluglings = new VarbitRequirement(1354, 5);

        evilSpiritNearby = new NpcCondition(NpcID.EVIL_SPIRIT);

        carcassNearby = new ItemOnTileRequirement(ItemID.FEVER_SPIDER_BODY);
        // 1359-64 0->1 given swill
    }

    public void setupSteps() {
        talkToPete = new NPCStep(NpcID.PIRATE_PETE, new RSTile(3680, 3537, 0), "Talk to Pirate Pete north east of the Ectofuntus.");
        talkToPete.addDialogStep("Yes.", "Of course, I fear no demon!",
                "Nonsense! Keep the money!", "I've decided to help you for free.", "Yes.");
        talkToBraindeath = new NPCStep(NpcID.CAPTAIN_BRAINDEATH, new RSTile(2145, 5108, 1), "Talk to Captain Braindeath.");

        goDownFromTop = new ObjectStep(ObjectID.LADDER_10168, new RSTile(2163, 5092, 2), "Go down the ladder.");
        goUpFromBottom = new ObjectStep(ObjectID.WOODEN_STAIR, new RSTile(2150, 5088, 0), "Go up to the first floor.");

        goDownstairs = new ObjectStep(ObjectID.WOODEN_STAIR_10137,
                new RSTile(2150, 5090, 1), "Go down to the island's farming patch to plant the blindweed seed.", blindweedSeed, rake, dibber);
        rakePatch = new UseItemOnObjectStep(ItemID.RAKE, 10096, new RSTile(2163, 5071, 0),
                "Raking Patch", rakeHighlight);


        plantSeed = new UseItemOnObjectStep(ItemID.BLINDWEED_SEED, 10096, new RSTile(2163, 5070, 0), "Plant the seed in the blindweed patch.", blindweedSeedHighlight, dibber);

        //TODO replace with method
        waitForGrowth = new DetailedQuestStep("Wait 5 minutes for the blindweed to grow.");

        pickPlant = new ObjectStep(10096, new RSTile(2163, 5070, 0), "Pick the blindweed on Braindeath Island.");

        goUpStairsWithPlant = new ObjectStep(ObjectID.WOODEN_STAIR, new RSTile(2150, 5088, 0), "Take the blindweed back to Captain Braindeath.", blindweed);

        talkToBraindeathWithPlant = new NPCStep("Captain Braindeath",
                new RSTile(2145, 5108, 1), blindweed);

        talkToPeteWithPlant = new NPCStep(NpcID.PIRATE_PETE,
                new RSTile(3680, 3537, 0), blindweed);
        // goUpStairsWithPlant.addSubSteps(talkToPeteWithPlant, talkToBraindeathWithPlant);

        climbUpToDropPlant = new ObjectStep(ObjectID.LADDER_10167, new RSTile(2163, 5093, 1),
                "Go to the top floor and put the blindweed into the hopper.", blindweed);

        dropPlant = new UseItemOnObjectStep(ItemID.BLINDWEED, ObjectID.HOPPER_10170, new RSTile(2142, 5102, 2),
                "Go to the top floor and put the blindweed into the hopper.", blindweedHighlight);
        dropPlant.addSubSteps(climbUpToDropPlant);

        goDownFromDropPlant = new ObjectStep(ObjectID.LADDER_10168, new RSTile(2163, 5093, 2), "Return to Captain Braindeath.");

        talkToBraindeathAfterPlant = new NPCStep(NpcID.CAPTAIN_BRAINDEATH, new RSTile(2145, 5108, 1), "Talk to Captain Braindeath.");
        //talkToBraindeathAfterPlant.addSubSteps(goDownFromDropPlant);

        goDownForWater = new ObjectStep(ObjectID.WOODEN_STAIR_10137, new RSTile(2138, 5090, 1), "Go to the north part of the island and get some stagnant water.", bucket);
        openGate = new ObjectStep(ObjectID.GATE_10172, new RSTile(2120, 5098, 0),
                "Go to the north part of the island and get some stagnant water.", bucket);
        openGate.addDialogStep(" ");

        useBucketOnWater = new UseItemOnObjectStep(ItemID.BUCKET, ObjectID.STAGNANT_LAKE, new RSTile(2135, 5161, 0), "Go to the north part of the island and get some stagnant water.", bucketHighlight);
        // useBucketOnWater.addSubSteps(goDownForWater, openGate);

        goUpWithWater = new
                ObjectStep(ObjectID.WOODEN_STAIR, new RSTile(2150, 5090, 0),
                "Take the water back to the hopper on the top floor.",
                MyPlayer.getTile().getPlane() != 1,
                stagnantWater);

        goUpToDropWater = new ObjectStep(ObjectID.LADDER_10167, new RSTile(2163, 5092, 1),
                "Climb-up", MyPlayer.getTile().getPlane() ==2,
                stagnantWater);
        goUpToDropWater.addDialogStep("What exactly do you want me to do?");

        dropWater = new UseItemOnObjectStep(ItemID.BUCKET_OF_WATER_6712,
                ObjectID.HOPPER_10170, new RSTile(2142, 5102, 2),
                !Inventory.contains(ItemID.BUCKET_OF_WATER_6712),
                stagnantWaterHighlight);
       // dropWater.addSubSteps(goUpWithWater, goUpToDropWater);

        goDownFromTopAfterDropWater = new ObjectStep(ObjectID.LADDER_10168, new RSTile(2163, 5092, 2), "Return to Captain Braindeath.");

        talkToBraindeathAfterWater = new NPCStep(NpcID.CAPTAIN_BRAINDEATH, new RSTile(2145, 5108, 1), "Talk to Captain Braindeath.");
        //talkToBraindeathAfterWater.addSubSteps(goDownFromTopAfterDropWater);

        getSlugs = new SlugSteps();

        goDownAfterSlugs = new ObjectStep(ObjectID.LADDER_10168,
                new RSTile(2163, 5092, 2), "Climb-down",
                MyPlayer.getTile().getPlane() != 2);
        talkToBraindeathAfterSlugs = new NPCStep(NpcID.CAPTAIN_BRAINDEATH, new RSTile(2145, 5108, 1), "Talk to Captain Braindeath.");
        // talkToBraindeathAfterSlugs.addSubSteps(goDownAfterSlugs);
        talkToDavey = new NPCStep(NpcID.DAVEY, new RSTile(2132, 5100, 1), wrench, prayerPoints47);
        useWrenchOnControl = new UseItemOnObjectStep(ItemID.HOLY_WRENCH,
                10104, new RSTile(2144, 5101, 1),
                MyPlayer.isHealthBarVisible(), holyWrench);

        //TODO fix this guess on tile
        killSpirit = new NPCStep(NpcID.EVIL_SPIRIT, new RSTile(2144, 5101, 1), prayerPoints47);
        killSpirit.setAsKillNpcStep();

        goUpFromSpiders = new ObjectStep(ObjectID.LADDER_10167, new RSTile(2139, 5105, 0), "" +
                "Go up the ladder.");

        talkToBraindeathAfterSpirit = new NPCStep(NpcID.CAPTAIN_BRAINDEATH, new RSTile(2145, 5108, 1), "Talk to Captain Braindeath.");
        goDownToSpiders = new ObjectStep(ObjectID.LADDER_10168, new RSTile(2139, 5105, 1),
                "Go into the brewery's basement and kill a fever spider. If you're not wearing slayer gloves they'll afflict you with disease.");

        killSpider = new NPCStep(NpcID.FEVER_SPIDER, new RSTile(2139, 5105, 1)); //TODO fix this guess on tile
                //    "Go into the brewery's basement and kill a fever spider.  slayer gloves they'll afflict you with disease.");
        killSpider.setAsKillNpcStep();

        pickUpCarcass = new GroundItemStep(ItemID.FEVER_SPIDER_BODY);
        goUpFromSpidersWithCorpse = new ObjectStep(ObjectID.LADDER_10167, new RSTile(2139, 5105, 0),
                "Add the spider body to the hopper on the top floor.", spiderCarcass);
        goUpToDropSpider = new ObjectStep(ObjectID.LADDER_10167, new RSTile(2163, 5092, 1), "Add the spider body to the hopper on the top floor.", spiderCarcass);
        dropSpider = new UseItemOnObjectStep(ItemID.FEVER_SPIDER_BODY, ObjectID.HOPPER_10170, new RSTile(2142, 5103, 2), "Add the spider body to the hopper on the top floor.", spiderCarcassHighlight);
        //dropSpider.addSubSteps(goUpFromSpidersWithCorpse, goUpToDropSpider);

        goDownAfterSpider = new ObjectStep(ObjectID.LADDER_10168, new RSTile(2163, 5092, 2), "Return to Captain Braindeath.");
        talkToBraindeathAfterSpider = new NPCStep(NpcID.CAPTAIN_BRAINDEATH, new RSTile(2145, 5108, 1), "Talk to Captain Braindeath.");
     //   talkToBraindeathAfterSpider.addSubSteps(goDownAfterSpider);

        useBucketOnTap = new UseItemOnObjectStep(ItemID.BUCKET, ObjectID.OUTPUT_TAP, new RSTile(2142, 5093, 1), "Fill a bucket from the output tap in the south west of the brewery.", bucket);

        goDownToDonnie = new ObjectStep(ObjectID.WOODEN_STAIR_10137, new RSTile(2150, 5088, 1), "Bring the unsanitary swill to Captain Donnie south of the Brewery.", swill);
        talkToDonnie = new NPCStep(NpcID.CAPTAIN_DONNIE, new RSTile(2153, 5078, 0), swill);
        //talkToDonnie.addSubSteps(goDownToDonnie);

        goUpToBraindeathToFinish = new ObjectStep(ObjectID.WOODEN_STAIR, new RSTile(2150, 5088, 0), "Return to Captain Braindeath to finish.");
        talkToBraindeathToFinish = new NPCStep(NpcID.CAPTAIN_BRAINDEATH, new RSTile(2145, 5108, 1), "Talk to Captain Braindeath to finish.");
       // talkToBraindeathToFinish.addSubSteps(goUpToBraindeathToFinish);

    }

    /*
      @Override
      public List<ItemRequirement> getItemRequirements()
      {
          return Arrays.asList(combatGear, dibber, rake, slayerGloves);
      }


      @Override
      public List<String> getCombatRequirements()
      {
          return Arrays.asList("Evil spirit (level 150)", "Fever spider (level 49)");
      }

      @Override
      public QuestPointReward getQuestPointReward()
      {
          return new QuestPointReward(2);
      }


      public List<ExperienceReward> getExperienceRewards()
      {
          return Arrays.asList(
                  new ExperienceReward(Skill.FISHING, 7000),
                  new ExperienceReward(Skill.PRAYER, 7000),
                  new ExperienceReward(Skill.FARMING, 7000));
      }*/
    //everything past the gate
    Area STAGNANT_WATER_ISLAND_AREA = Area.fromPolygon(
            new WorldTile(2117, 5099, 0),
            new WorldTile(2127, 5099, 0),
            new WorldTile(2127, 5128, 0),
            new WorldTile(2174, 5131, 0),
            new WorldTile(2174, 5182, 0),
            new WorldTile(2114, 5181, 0)
    );

    private void getStagnantWater() {
        if (!STAGNANT_WATER_ISLAND_AREA.containsMyPlayer()) {
            openGate = new ObjectStep(ObjectID.GATE_10172, new RSTile(2120, 5098, 0),
                    "Go to the north part of the island and get some stagnant water.", bucket);
            openGate.addDialogStep(" ");
            openGate.execute();
            Waiting.waitUntil(5000, 500, () -> STAGNANT_WATER_ISLAND_AREA.containsMyPlayer());
        }
        if (STAGNANT_WATER_ISLAND_AREA.containsMyPlayer()) {
            RSTile waterTile = new RSTile(2135, 5161, 0);
            cQuesterV2.status = "Blind walking to stagnant water";
            Log.info(cQuesterV2.status);
            Walking.blindWalkTo(waterTile);
            for (int i = 0; i < 3; i++) //long walk so loop this
                if (MyPlayer.isMoving())
                    PathingUtil.movementIdle();

            if (waterTile.distanceTo(Player.getPosition()) < 4 &&
                    Utils.useItemOnObject(ItemID.BUCKET, ObjectID.STAGNANT_LAKE)) {

            }
        }
        useBucketOnWater = new UseItemOnObjectStep(ItemID.BUCKET, ObjectID.STAGNANT_LAKE, new RSTile(2135, 5161, 0), "Go to the north part of the island and get some stagnant water.", bucketHighlight);
        // useBucketOnWater.addSubSteps(goDownForWater, openGate);

    }

    int[] shopWidgetPath = {300, 16};

    private boolean getSlayerGloves() {
        if (Equipment.contains(ItemID.SLAYER_GLOVES_6720))
            return true;
        //TODO, ensure you have GP (200)
        if (Inventory.getCount(ItemID.COINS_995) < 200) {
            Log.info("Getting Coins for Slayer Gloves");
            BankManager.open(true);
            BankManager.withdraw(200, true, ItemID.COINS_995);
            BankManager.close(true);
        }


        Log.info("Getting Slayer Gloves");
        WorldTile shopTile = new WorldTile(3109, 3514, 0);
        if (ShoppingUtil.openShop(shopTile, "Krystilia")) {
            Log.info("Buying gloves");
            for (int i = 0; i < 5; i++) {
                if (Shop.buy(Shop.Quantity.ONE, ItemID.SLAYER_GLOVES_6720) &&
                        Waiting.waitUntil(2000, 500, () -> Inventory.contains(ItemID.SLAYER_GLOVES_6720))) {
                    Log.info("Bought");
                    break;
                } else if (Inventory.contains(ItemID.SLAYER_GLOVES_6720))
                    break;
                else Waiting.waitNormal(500, 50);
            }
            Shop.close();
        }
        if (Utils.equipItem(ItemID.SLAYER_GLOVES_6720))
            return Waiting.waitUntil(500, 50, () ->
                    Equipment.contains(ItemID.SLAYER_GLOVES_6720));

        return Equipment.contains(ItemID.SLAYER_GLOVES_6720);
    }


    @Override
    public List<Requirement> getGeneralRequirements() {
        ArrayList<Requirement> req = new ArrayList<>();
        // req.add(new QuestRequirement(Quest.ZOGRE_FLESH_EATERS, Quest.State.COMPLETE));
        // req.add(new QuestRequirement(Quest.PRIEST_IN_PERIL, Quest.State.COMPLETE));
        req.add(new SkillRequirement(Skills.SKILLS.CRAFTING, 42));
        req.add(new SkillRequirement(Skills.SKILLS.FISHING, 50));
        req.add(new SkillRequirement(Skills.SKILLS.FARMING, 40));
        req.add(new SkillRequirement(Skills.SKILLS.PRAYER, 47));
        req.add(new SkillRequirement(Skills.SKILLS.SLAYER, 42));
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
        if (!checkRequirements()) {
            Log.warn("Missing a rum deal quest requirement, check the wiki");
            cQuesterV2.taskList.remove(this);
            return;
        }
        int gameSetting = GameState.getSetting(QuestVarPlayer.QUEST_RUM_DEAL.getId());
        Log.info("[Debug]: Rum deal gameSetting is " + gameSetting);

        if (isComplete()) {
            cQuesterV2.taskList.remove(this);
            return;
        }
        if (gameSetting == 0 && !initialItemReqs.check()) {
            buyitems.buyItems();
            initialItemReqs.withdrawItems();
        }
        if (!getSlayerGloves()) {
            Log.info("Failed to get slayer gloves");
            Waiting.waitNormal(950, 50);
            return;
        }
        if (gameSetting == 11) {
            Log.info("Getting slugs");
            getSlugs = new SlugSteps();
            getSlugs.execute();
            gameSetting = GameState.getSetting(QuestVarPlayer.QUEST_RUM_DEAL.getId());
        } else
            //idle while killing npc
            if (Query.npcs().nameContains("Evil spirit").isAny()) {
                CombatUtil.waitUntilOutOfCombat("Evil spirit", 45);

                return;
            }
        Map<Integer, QuestStep> steps = loadSteps();
        Optional<QuestStep> step = Optional.ofNullable(steps.get(gameSetting));
        step.ifPresent(s -> cQuesterV2.status = s.getClass().toGenericString());
        step.ifPresent(QuestStep::execute);
        if (plantedPatch.check()) {
            cQuesterV2.status = "Waiting 5 min";
            //Waiting.waitNormal(5000, 500);
        }

        if (Query.npcs().nameContains("Fever spider").isMyPlayerInteractingWith().isAny()) {
            CombatUtil.waitUntilOutOfCombat("Fever spider", 45);
            if (Query.groundItems().idEquals(ItemID.FEVER_SPIDER_BODY).findClosest().map(
                    f -> f.interact("Take")).orElse(false))
                Waiting.waitUntil(() -> Inventory.contains(ItemID.FEVER_SPIDER_BODY));
            return;
        }
        if (gameSetting == 13 && !Inventory.contains(ItemID.HOLY_WRENCH))
            Waiting.waitNormal(3000, 50);
        else
            Waiting.waitNormal(2000, 50); //dont shorten
        Utils.cutScene();
        if (ChatScreen.isOpen())
            NpcChat.handle();
    }

    @Override
    public String questName() {
        return "Rum Deal (" + GameState.getSetting(QuestVarPlayer.QUEST_RUM_DEAL.getId()) + ")";
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
        return GameState.getSetting(QuestVarPlayer.QUEST_RUM_DEAL.getId()) == 19;
    }
}
