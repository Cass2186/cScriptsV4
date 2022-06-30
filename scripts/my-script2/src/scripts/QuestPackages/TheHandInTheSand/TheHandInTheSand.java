package scripts.QuestPackages.TheHandInTheSand;

import org.tribot.api2007.Game;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.*;
import org.tribot.script.sdk.types.WorldTile;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.TrollRomance.TrollRomance;
import scripts.QuestSteps.*;
import scripts.Requirements.*;
import scripts.Requirements.Util.ConditionalStep;
import scripts.Requirements.Util.Conditions;
import scripts.Tasks.Priority;

import java.util.*;

public class TheHandInTheSand implements QuestTask {

    private static TheHandInTheSand quest;

    public static TheHandInTheSand get() {
        return quest == null ? quest = new TheHandInTheSand() : quest;
    }

    //Items Required
    ItemRequirement truthSerum, beer, redberries, whiteberries, pinkDye, roseLens, lanternLens, magicalOrb, redberryJuice, bottledWater, hand, beerHand, bertsRota, sandysRota, magicScroll, vial, vial2, sand, wizardsHead,
            beerOr2Coins, earthRunes5, coins, bucketOfSand, truthSerumHighlight, activatedOrb;

    //Items Recommended
    ItemRequirement teleportsToYanille, teleportsToBrimhaven;

    Requirement notTeleportedToSarim, inYanille, inLightSpot, receivedBottledWater, vialPlaced, madeTruthSerum;

    QuestStep talkToBert, talkToBertAboutRota, talkToBertAboutScroll, talkToBetty, talkToBettyOnceMore, talkToBettyAgain, talkToRarveAgain, talkToSandyWithPotion, giveCaptainABeer, useLensOnCounter,
            useDyeOnLanternLens, useSerumOnCoffee, searchSandysDesk, standInDoorway, ringBell, ringBellAgain, addWhiteberries, addRedberries, activateMagicalOrb, interrogateSandy, ringBellAfterInterrogation,
            ringBellWithItems, talkToMazion, ringBellEnd;

    NPCStep pickpocketSandy;
    //Zones
    RSArea yanille, lightSpot;

    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.BEER, 1, 500),
                    new GEItem(ItemID.VIAL, 2, 300),
                    new GEItem(ItemID.REDBERRIES, 1, 500),
                    new GEItem(ItemID.WHITE_BERRIES, 1, 500),
                    new GEItem(ItemID.LANTERN_LENS, 1, 500),
                    new GEItem(ItemID.EARTH_RUNE, 5, 25),
                    new GEItem(ItemID.BUCKET_OF_SAND, 1, 500),
                    new GEItem(ItemID.STAMINA_POTION[0], 3, 15),
                    new GEItem(ItemID.AMULET_OF_GLORY[2], 2, 15),
                    new GEItem(ItemID.RING_OF_WEALTH[0], 1, 25),
                    new GEItem(ItemID.RING_OF_DUELING[0], 1, 35)
            )
    );

    BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);

    InventoryRequirement startInv = new InventoryRequirement(new ArrayList<>(
            Arrays.asList(
                    new ItemReq(ItemID.BEER, 1),
                    new ItemReq(ItemID.VIAL, 2),
                    new ItemReq(ItemID.REDBERRIES, 1),
                    new ItemReq(ItemID.WHITE_BERRIES, 1),
                    new ItemReq(ItemID.LANTERN_LENS, 1),
                    new ItemReq(ItemID.EARTH_RUNE, 5),
                    new ItemReq(ItemID.BUCKET_OF_SAND, 1),
                    new ItemReq(ItemID.COINS, 10000),
                    new ItemReq(ItemID.RING_OF_DUELING[0], 1, 0),
                    new ItemReq(ItemID.AMULET_OF_GLORY[2], 2, 0, true, true),
                    new ItemReq(ItemID.RING_OF_WEALTH[0], 1, true, true),
                    new ItemReq(ItemID.STAMINA_POTION[0], 3, 0)
            ))
    );

    public Map<Integer, QuestStep> loadSteps() {
        loadZones();
        setupItemRequirements();
        setupConditions();
        setupSteps();
        Map<Integer, QuestStep> steps = new HashMap<>();

        steps.put(0, talkToBert);
        // 1528, 1529, 1530 0->1
        steps.put(10, giveCaptainABeer);
        steps.put(20, ringBell);
        steps.put(30, talkToBertAboutRota);
        steps.put(40, searchSandysDesk);

        ConditionalStep goGetScroll = new ConditionalStep(pickpocketSandy);
        goGetScroll.addStep(sand, talkToBertAboutScroll);
        steps.put(50, goGetScroll);
        steps.put(60, ringBellAgain);

        ConditionalStep goToBetty = new ConditionalStep(talkToBetty);
        goToBetty.addStep(madeTruthSerum, talkToBettyOnceMore);
        //don't need as i handle in the execute() method
        //goToBetty.addStep(new Conditions(roseLens, vialPlaced, inLightSpot), useLensOnCounter);
        //  goToBetty.addStep(new Conditions(roseLens, vialPlaced), standInDoorway);
        goToBetty.addStep(roseLens, talkToBettyAgain);
        goToBetty.addStep(pinkDye, useDyeOnLanternLens);
        goToBetty.addStep(redberryJuice, addWhiteberries);
        goToBetty.addStep(receivedBottledWater, addRedberries);
        goToBetty.addStep(new Conditions(inYanille, notTeleportedToSarim), talkToRarveAgain);

        steps.put(70, goToBetty);
        steps.put(80, talkToSandyWithPotion);
        steps.put(90, useSerumOnCoffee);
        steps.put(100, activateMagicalOrb);
        steps.put(110, interrogateSandy);
        steps.put(120, ringBellAfterInterrogation);
        steps.put(130, ringBellWithItems);
        steps.put(140, talkToMazion);
        steps.put(150, ringBellEnd);

        return steps;
    }

    public void setupItemRequirements() {
        beer = new ItemRequirement("Beer", ItemID.BEER);
        bottledWater = new ItemRequirement("Bottled water", ItemID.BOTTLED_WATER);
        // bottledWater.setTooltip("You can get another from Betty");

        redberries = new ItemRequirement("Redberries", ItemID.REDBERRIES);
        whiteberries = new ItemRequirement("White berries", ItemID.WHITE_BERRIES);
        redberryJuice = new ItemRequirement("Red dye", ItemID.REDBERRY_JUICE);


        pinkDye = new ItemRequirement("Pink dye", ItemID.PINK_DYE);
        truthSerum = new ItemRequirement("Truth serum", ItemID.TRUTH_SERUM);
        // truthSerum.setTooltip("You can get another from Betty in Port Sarim");

        truthSerumHighlight = new ItemRequirement("Truth serum", ItemID.TRUTH_SERUM);
        // truthSerumHighlight.setTooltip("You can get another from Betty in Port Sarim");


        lanternLens = new ItemRequirement("Lantern lens", ItemID.LANTERN_LENS);
        roseLens = new ItemRequirement("Rose-tinted lens", ItemID.ROSE_TINTED_LENS);
        hand = new ItemRequirement("Sandy hand", ItemID.SANDY_HAND);
        //  hand.setTooltip("You can get another from Bert");
        beerHand = new ItemRequirement("Beer soaked hand", ItemID.BEER_SOAKED_HAND);
        // beerHand.setTooltip("You can get another from Bert");
        bertsRota = new ItemRequirement("Bert's rota", ItemID.BERTS_ROTA);
        /// bertsRota.setTooltip("You can get another from Bert");
        sandysRota = new ItemRequirement("Sandy's rota", ItemID.SANDYS_ROTA);
        //  sandysRota.setTooltip("You can get another by searching Sandy's desk");

        magicalOrb = new ItemRequirement("Magical orb", ItemID.MAGICAL_ORB);
        //   magicalOrb.setTooltip("You can get another from Zavistic Rarve in Yanille");

        activatedOrb = new ItemRequirement("Magical orb", ItemID.MAGICAL_ORB_A);
        // activatedOrb.setTooltip("You can get another from Zavistic Rarve in Yanille");

        vial = new ItemRequirement("Vial", ItemID.VIAL);
        vial2 = new ItemRequirement("Vial", ItemID.VIAL, 2);

        magicScroll = new ItemRequirement("A magic scroll", ItemID.A_MAGIC_SCROLL);
        //  magicScroll.setTooltip("You can get another from Bert");

        sand = new ItemRequirement("Sand", ItemID.SAND);
        //sand.setTooltip("You can get more by pickpocketing Sandy in Brimhaven");

        beerOr2Coins = new ItemRequirement("Beer or 2 gp", ItemID.BEER);
        earthRunes5 = new ItemRequirement("Earth runes", ItemID.EARTH_RUNE, 5);
        coins = new ItemRequirement(ItemID.COINS_995, 10000);

        bucketOfSand = new ItemRequirement("Bucket of sand", ItemID.BUCKET_OF_SAND);

        wizardsHead = new ItemRequirement("Wizard's head", ItemID.WIZARDS_HEAD);
        //wizardsHead.setTooltip("You can get another from Mazion on Entrana");

        teleportsToBrimhaven = new ItemRequirement("Teleports to Brimhaven, or to near a boat to Brimhaven",
                ItemID.BRIMHAVEN_TELEPORT, 2);
        teleportsToYanille = new ItemRequirement("Teleports to Yanille, such as dueling ring or minigame teleport",
                ItemID.YANILLE_TELEPORT, 3);
    }

    public void loadZones() {
        yanille = new RSArea(new RSTile(2528, 3063, 0), new RSTile(2628, 3122, 0));
        lightSpot = new RSArea(new RSTile(3016, 3259, 0), new RSTile(3016, 3259, 0));
    }

    public void setupConditions() {
        notTeleportedToSarim = new VarbitRequirement(1531, 0);
        inYanille = new AreaRequirement(yanille);
        inLightSpot = new AreaRequirement(lightSpot);
        receivedBottledWater = new VarbitRequirement(1532, 1);
        vialPlaced = new VarbitRequirement(1537, 1);
        madeTruthSerum = new VarbitRequirement(1532, 5);
    }

    public void setupSteps() {
        talkToBert = new NPCStep(NpcID.BERT, new RSTile(2551, 3099, 0), "Talk to Bert in west Yanille.");
        talkToBert.addDialogStep("Eww a hand, in the sand! Why haven't you told the authorities?");
        talkToBert.addDialogStep("Yes.");
        giveCaptainABeer = new NPCStep(NpcID.GUARD_CAPTAIN, new RSTile(2552, 3080, 0), beer);
        ringBell = new ObjectStep(ObjectID.BELL_6847, new RSTile(2598, 3085, 0),
                "Ring", beerHand);
        ringBell.addDialogStep("I have a rather sandy problem that I'd like to palm off on you.");

        talkToBertAboutRota = new NPCStep(NpcID.BERT, new RSTile(2551, 3099, 0),
                "Return to Bert in west Yanille.");

        searchSandysDesk = new ObjectStep(ObjectID.SANDYS_DESK, new RSTile(2789, 3174, 0),
                "Search");

        pickpocketSandy = new NPCStep(NpcID.SANDY, new RSTile(2790, 3175, 0),
                "Pickpocket Sandy for some sand.");
        pickpocketSandy.setInteractionString("Pickpocket");

        talkToBertAboutScroll = new NPCStep(NpcID.BERT, new RSTile(2551, 3099, 0), bertsRota, sandysRota);

        ringBellAgain = new ObjectStep(ObjectID.BELL_6847, new RSTile(2598, 3085, 0), "Ring", magicScroll);
        talkToRarveAgain = new ObjectStep(ObjectID.BELL_6847, new RSTile(2598, 3085, 0), "Ring", vial);
        talkToRarveAgain.addDialogStep("I have a rather sandy problem that I'd like to palm off on you.");
        talkToRarveAgain.addDialogStep("Can you help me more?");
        talkToRarveAgain.addDialogStep("Yes, that would be great!");

        talkToBetty = new NPCStep(NpcID.BETTY_5905, new RSTile(3014, 3258, 0), vial);
        talkToBetty.addDialogStep("Talk to Betty about the Hand in the Sand.");
        addRedberries = new UseItemOnItemStep(ItemID.REDBERRIES, ItemID.BOTTLED_WATER, !Inventory.contains(ItemID.REDBERRIES),
                bottledWater, redberries);
        addWhiteberries = new UseItemOnItemStep(ItemID.WHITE_BERRIES, ItemID.REDBERRY_JUICE,
                !Inventory.contains(ItemID.WHITE_BERRIES),
                redberryJuice, whiteberries);
        useDyeOnLanternLens = new UseItemOnItemStep(ItemID.PINK_DYE, ItemID.LANTERN_LENS,
                !Inventory.contains(ItemID.PINK_DYE), pinkDye, lanternLens);

        talkToBettyAgain = new NPCStep(NpcID.BETTY_5905, new RSTile(3014, 3258, 0), "Talk to Betty with the pink lens.");
        talkToBettyAgain.addDialogStep("Talk to Betty about the Hand in the Sand.");

        //   standInDoorway = new DetailedQuestStep( new RSTile(3016, 3259, 0));
        useLensOnCounter = new UseItemOnObjectStep(ItemID.ROSE_TINTED_LENS, ObjectID.COUNTER, new RSTile(3016, 3259, 0),
                "\"Stand in the Betty's doorway and use the rose-tinted lens on the counter.", roseLens);
        // useLensOnCounter.addSubSteps(standInDoorway);
        talkToBettyOnceMore = new NPCStep(NpcID.BETTY_5905, new RSTile(3014, 3258, 0), truthSerum, sand);
        talkToBettyOnceMore.addDialogStep("Talk to Betty about the Hand in the Sand.");
        talkToSandyWithPotion = new NPCStep(NpcID.SANDY, new RSTile(2790, 3175, 0),
                //    "Talk to Sandy in Brimhaven again with the truth serum. Select distractions until one works.",
                truthSerum);
        talkToSandyWithPotion.addDialogStep("There's a herd of huge mutant herring about to drop from the sky!");
        talkToSandyWithPotion.addDialogStep("But the pygmy shrews have eaten all the sand!");
        talkToSandyWithPotion.addDialogStep("A small parrot with a pink banana is sitting outside your window!");
        useSerumOnCoffee = new UseItemOnObjectStep(ItemID.TRUTH_SERUM, 10806, new RSTile(2789, 3176, 0),
                "Use the truth serum on Sandy's coffee mug.", truthSerum);

        activateMagicalOrb = new ClickItemStep(ItemID.MAGICAL_ORB, "Activate", new RSTile(2789, 3175, 0), magicalOrb);

        interrogateSandy = new NPCStep(NpcID.SANDY, new RSTile(2790, 3175, 0), activatedOrb);
        interrogateSandy.addDialogStep("Why is Bert's rota different from the original?",
                "Why doesn't Bert remember the change in his hours?", "What happened to the wizard?");

        ringBellAfterInterrogation = new ObjectStep(ObjectID.BELL_6847, new RSTile(2598, 3085, 0), "Ring", activatedOrb, earthRunes5, bucketOfSand);
        ringBellWithItems = new ObjectStep(ObjectID.BELL_6847, new RSTile(2598, 3085, 0), "Ring", earthRunes5, bucketOfSand);
        talkToMazion = new NPCStep(NpcID.MAZION, new RSTile(2815, 3340, 0), "Travel to Entrana (bank all combat gear), and talk to Mazion at the sand pit.");
        ringBellEnd = new ObjectStep(ObjectID.BELL_6847, new RSTile(2598, 3085, 0), "Ring", wizardsHead);
    }


    public List<ItemRequirement> getItemRequirements() {
        ArrayList<ItemRequirement> reqs = new ArrayList<>();
        reqs.add(beerOr2Coins);
        reqs.add(coins);
        reqs.add(vial2);
        reqs.add(redberries);
        reqs.add(whiteberries);
        reqs.add(lanternLens);
        reqs.add(earthRunes5);
        reqs.add(bucketOfSand);
        return reqs;
    }


    public List<ItemRequirement> getItemRecommended() {
        ArrayList<ItemRequirement> reqs = new ArrayList<>();
        reqs.add(teleportsToYanille);
        reqs.add(teleportsToBrimhaven);

        return reqs;
    }


    public List<Requirement> getGeneralRequirements() {
        return Arrays.asList(new SkillRequirement(Skills.SKILLS.THIEVING, 17),
                new SkillRequirement(Skills.SKILLS.CRAFTING, 49));
    }


    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return cQuesterV2.taskList.size() > 0 &&
                cQuesterV2.taskList.get(0).equals(this);
    }

    @Override
    public void execute() {
        int varbit = Utils.getVarBitValue(QuestVarbits.QUEST_THE_HAND_IN_THE_SAND.getId());
        if (isComplete() || Quest.THE_HAND_IN_THE_SAND.getStep() == 160) {
            cQuesterV2.taskList.remove(this);
            return;
        }

        if (!checkRequirements()) {
            Log.info("Missing requirement(s) for the hand in the sand (17 thieving and 49 crafting)");
            cQuesterV2.taskList.remove(this);
            return;
        }
        if (Quest.THE_HAND_IN_THE_SAND.getStep() == 0 && !startInv.check()) {
            buyStep.buyItems();
            if (Quest.PLAGUE_CITY.getState().equals(Quest.State.COMPLETE)) {
                startInv.add(new ItemReq(ItemID.ARDOUGNE_TELEPORT, 2, 0));
            }
            startInv.setDepositEquipment(true);
            startInv.withdrawItems();
        }
        Log.info("[Debug]: The Hand in the Sand Varbit is " + varbit);
        Map<Integer, QuestStep> steps = loadSteps();
        Optional<QuestStep> step = Optional.ofNullable(steps.get(varbit));
        if (Quest.THE_HAND_IN_THE_SAND.getStep() == 50 && sandysRota.check()) {
            Log.info("Going to Bert");
            talkToBertAboutScroll.execute();
            return;
        }
        if (Quest.THE_HAND_IN_THE_SAND.getStep() == 130 && GameState.isInInstance()) {
            NpcChat.handle(true);
            Waiting.waitUntil(8000, 500, () -> !GameState.isInInstance() || ChatScreen.isOpen());
            return;
        }

        if (Quest.THE_HAND_IN_THE_SAND.getStep() == 70 &&
                !Inventory.contains(ItemID.TRUTH_SERUM)) {
            ;
            WorldTile t = new WorldTile(3016, 3259, 0);
            if (t.distance() < 20 && !MyPlayer.getTile().equals(t) &&
                    t.interact("Walk here")) {
                PathingUtil.movementIdle();
            }
            Log.info("Using lens on counter");
            if (Utils.useItemOnObject(ItemID.ROSE_TINTED_LENS, "Counter")) {
                NpcChat.handle(true);
            }
        }

        step.ifPresent(QuestStep::execute);
        if (!Inventory.contains(ItemID.SAND) && Quest.THE_HAND_IN_THE_SAND.getStep() >=30
        && Quest.THE_HAND_IN_THE_SAND.getStep() <= 70) {
            Log.info("going to pickpocket sandy");
            pickpocketSandy.execute();
        }
        if (ChatScreen.isOpen())
            NpcChat.handle("I have a rather sandy problem that I'd like to palm off on you.");

        Waiting.waitNormal(200, 20);
    }

    @Override
    public String questName() {
        return "The Hand in the Sand (" +
                Utils.getVarBitValue(QuestVarbits.QUEST_THE_HAND_IN_THE_SAND.getId()) + "|" + Quest.THE_HAND_IN_THE_SAND.getStep() + ")";
    }

    @Override
    public boolean checkRequirements() {
        return getGeneralRequirements().stream().allMatch(Requirement::check);

    }

    @Override
    public boolean isComplete() {
        return Quest.THE_HAND_IN_THE_SAND.getState().equals(Quest.State.COMPLETE);
    }

    @Override
    public List<ItemRequirement> getBuyList() {
        return null;
    }
}
