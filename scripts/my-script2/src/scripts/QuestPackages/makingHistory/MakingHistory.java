package scripts.QuestPackages.makingHistory;

import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.Inventory;
import org.tribot.script.sdk.Quest;
import scripts.GEManager.GEItem;
import scripts.ItemID;
import scripts.NpcID;
import scripts.ObjectID;
import scripts.QuestSteps.*;
import scripts.Requirements.*;
import scripts.Requirements.Items.ItemCollections;
import scripts.Requirements.Quest.QuestRequirement;
import scripts.Requirements.Util.ConditionalStep;
import scripts.Requirements.Util.Conditions;
import scripts.Requirements.Util.LogicType;
import scripts.Requirements.Util.Operation;
import scripts.Tasks.Priority;
import scripts.cQuesterV2;

import java.util.*;

public class MakingHistory implements QuestTask {

    private static MakingHistory quest;

    public static MakingHistory get() {
        return quest == null ? quest = new MakingHistory() : quest;
    }


    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.STEEL_PICKAXE, 1, 550),
                    new GEItem(ItemID.STAMINA_POTION[0], 2, 15),
                    new GEItem(ItemID.LOCKPICK, 1, 500),
                    new GEItem(ItemID.TINDERBOX, 1, 500),
                    new GEItem(ItemID.VARROCK_TELEPORT, 5, 40),
                    new GEItem(ItemID.FALADOR_TELEPORT, 5, 40),
                    new GEItem(ItemID.LUMBRIDGE_TELEPORT, 10, 40),
                    new GEItem(ItemID.RING_OF_WEALTH[0], 1, 25),
                    new GEItem(ItemID.AMULET_OF_GLORY[2], 1, 25)
            )
    );

    InventoryRequirement initialItemReqs = new InventoryRequirement(new ArrayList<>(
            Arrays.asList(
                    new ItemReq(ItemID.STEEL_PICKAXE, 1, 1)

            )
    ));

    BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);

    //Items Required
    ItemRequirement spade, saphAmulet, ghostSpeakAmulet, enchantedKey, chest, journal, scroll, letter, enchantedKeyHighlighted, ectoTokens;


    //Items Recommended
    ItemRequirement ardougneTeleport, ectophial, ringOfDueling, passage, rellekaTeleport, runRestoreItems;

    Requirement talkedtoBlanin, talkedToDron, talkedToMelina, talkedToDroalak, inCastle, gotKey, gotChest,
            gotScroll, handedInJournal, handedInScroll, finishedFrem, finishedKey, finishedGhost, handedInEverything;

    QuestStep talkToJorral, talkToSilverMerchant, dig, talkToBlanin, talkToDron, talkToDroalak,
            talkToMelina, returnToDroalak, returnToJorral, continueTalkingToJorral, goUpToLathas, talkToLathas, finishQuest;
    UseItemOnItemStep openChest;
    ConditionalStep dronSteps, ghostSteps, keySteps;

    //Zones
    RSArea castle;


    public Map<Integer, QuestStep> loadSteps() {
        loadRSArea();
        setupItemRequirements();
        setupConditions();
        setupSteps();
        Map<Integer, QuestStep> steps = new HashMap<>();

        steps.put(0, talkToJorral);

        keySteps = new ConditionalStep(talkToSilverMerchant);
        keySteps.addStep(chest, openChest);
        keySteps.addStep(gotKey, dig);
        keySteps.setLockingCondition(finishedKey);

        dronSteps = new ConditionalStep(talkToBlanin);
        dronSteps.addStep(talkedtoBlanin, talkToDron);
        dronSteps.setLockingCondition(finishedFrem);

        ghostSteps = new ConditionalStep(talkToDroalak);
        ghostSteps.addStep(talkedToMelina, returnToDroalak);
        ghostSteps.addStep(talkedToDroalak, talkToMelina);
        ghostSteps.setLockingCondition(finishedGhost);

        ConditionalStep getItems = new ConditionalStep(keySteps);
        getItems.addStep(handedInEverything, continueTalkingToJorral);
        getItems.addStep(new Conditions(finishedKey, finishedFrem, finishedGhost), returnToJorral);
        getItems.addStep(new Conditions(finishedKey, finishedFrem), ghostSteps);
        getItems.addStep(finishedKey, dronSteps);

        steps.put(1, getItems);

        ConditionalStep goTalkToLathas = new ConditionalStep(goUpToLathas);
        goTalkToLathas.addStep(inCastle, talkToLathas);

        steps.put(2, goTalkToLathas);

        steps.put(3, finishQuest);

        return steps;
    }

    public void setupItemRequirements() {
        spade = new ItemRequirement("Spade", ItemID.SPADE);
        saphAmulet = new ItemRequirement("Sapphire amulet", ItemID.SAPPHIRE_AMULET);
        ghostSpeakAmulet = new ItemRequirement(ItemID.GHOSTSPEAK_AMULET, 1, true, true);
        // ghostSpeakAmulet.setTooltip("You can also wear the Morytania Legs 2 or higher.");
        ardougneTeleport = new ItemRequirement("Teleports to Ardougne", ItemID.ARDOUGNE_TELEPORT, 3);

        ectophial = new ItemRequirement("Ectophial, or method of getting to Port Phasmatys", ItemID.ECTOPHIAL);
        ectophial.addAlternateItemID(ItemID.FENKENSTRAINS_CASTLE_TELEPORT, ItemID.KHARYRLL_TELEPORT);
        ectophial.addAlternateItemID(ItemCollections.getSlayerRings()); // Slayer Tower
        ////    ectophial.addAlternates(ItemID.MORYTANIA_LEGS_2, ItemID.MORYTANIA_LEGS_3, ItemID.MORYTANIA_LEGS_4);
        //  ectophial.setTooltip("You will need 2 ecto-tokens if you have not completed Ghosts Ahoy.");
        //   ectophial.appendToTooltip("If you do not have 2 ecto-tokens bring 1 bone, 1 pot and 1 bucket to earn 5 ecto-tokens at the Ectofunctus.\n");
        //  ectophial.appendToTooltip("You can use the Fairy Ring 'ALQ' or the Morytania Legs 2 or higher to get to Port Phasmatys.");
        //  ectophial.appendToTooltip("Slayer rings and the Kharyrll teleport can also be used.");

        ectoTokens = new ItemRequirement("Ecto-tokens", ItemID.ECTOTOKEN, 2);
     //   ectoTokens.setTooltip("You do not need two ecto-tokens if you have completed Ghosts Ahoy.");
        // ectoTokens.appendToTooltip("If you do not have 2 ecto-tokens bring 1 bone, 1 pot and 1 bucket to earn 5 ecto-tokens at the Ectofunctus.");
        //   ectoTokens.appendToTooltip("Additionally, you can also enter Port Phasmatys via Charter ship, but that costs up to 4,100 coins.");

       /* portPhasmatysEntry = ComplexRequirementBuilder.or("2 x Ecto-tokens, or 4100 coins to travel there via Charter" +
                        " Ship")
                .with(ectoTokens)
                .with(new QuestRequirement(Quest.GHOSTS_AHOY, Quest.State.COMPLETE))
                .with(new ItemRequirement("Coins", ItemCollections.getCoins(), 4100))
                .build();*/
        // portPhasmatysEntry.setTooltip(ectoTokens.getTooltip());

        ringOfDueling = new ItemRequirement("Ring of Dueling", ItemCollections.getRingOfDuelings());
        enchantedKey = new ItemRequirement("Enchanted key", ItemID.ENCHANTED_KEY);
        // enchantedKey.setTooltip("You can get another from the silver merchant in East Ardougne's market");

        enchantedKeyHighlighted = new ItemRequirement("Enchanted key", ItemID.ENCHANTED_KEY);
        // enchantedKeyHighlighted.setTooltip("You can get another from the silver merchant in East Ardougne's market");

        journal = new ItemRequirement("Journal", ItemID.JOURNAL_6755);
        chest = new ItemRequirement("Chest", ItemID.CHEST);
        /// chest.setTooltip("You can dig up another from north of Castle Wars");

        scroll = new ItemRequirement("Scroll", ItemID.SCROLL);
        //   scroll.setTooltip("You can get another from Droalak in Port Phasmatys");
        letter = new ItemRequirement("Letter", ItemID.LETTER_6757);
        //  letter.setTooltip("You can get another from King Lathas in East Ardougne castle");
        passage = new ItemRequirement("Necklace of passage", ItemCollections.getNecklaceOfPassages());
        rellekaTeleport = new ItemRequirement("Relleka Teleport", ItemID.RELLEKKA_TELEPORT);
        rellekaTeleport.addAlternateItemID(ItemCollections.getEnchantedLyre());
        rellekaTeleport.addAlternateItemID(ItemID.FREMENNIK_SEA_BOOTS_2, ItemID.FREMENNIK_SEA_BOOTS_3, ItemID.FREMENNIK_SEA_BOOTS_4);
        //     rellekaTeleport.setTooltip("You can also use Fairy Rings (DKS or AJR) if you have those unlocked.");
        //    rellekaTeleport.appendToTooltip("You can also teleport to Camelot and run North.");
        runRestoreItems = new ItemRequirement("Potions/Items to restore run energy", ItemCollections.getRunRestoreItems());

    }

    public void loadRSArea() {
        castle = new RSArea(new RSTile(2570, 3283, 1), new RSTile(2590, 3310, 1));
    }

    public void setupConditions() {
        talkedtoBlanin = new Conditions(LogicType.OR, new VarbitRequirement(1385, 1), new VarbitRequirement(1385, 2));
        talkedToDron = new VarbitRequirement(1385, 3, Operation.GREATER_EQUAL);

        talkedToDroalak = new Conditions(LogicType.OR, new VarbitRequirement(1386, 2), new VarbitRequirement(1386, 1));
        talkedToMelina = new Conditions(LogicType.OR, new VarbitRequirement(1386, 4), new VarbitRequirement(1386, 3));
        gotScroll = new VarbitRequirement(1386, 5);
        handedInScroll = new VarbitRequirement(1386, 6);

        inCastle = new AreaRequirement(castle);
        gotKey = new VarbitRequirement(1384, 1, Operation.GREATER_EQUAL);
        gotChest = new VarbitRequirement(1384, 2, Operation.GREATER_EQUAL);
        handedInJournal = new VarbitRequirement(1384, 4);
        handedInEverything = new Conditions(handedInJournal, handedInScroll, talkedToDron);
        finishedFrem = talkedToDron;
        finishedGhost = new Conditions(LogicType.OR, handedInScroll, gotScroll);
        finishedKey = new Conditions(LogicType.OR, handedInJournal, journal);
    }

    public void setupSteps() {
        talkToJorral = new NPCStep(NpcID.JORRAL, new RSTile(2436, 3346, 0),
                "Talk to Jorral at the outpost south of the Tree Gnome Stronghold. You can teleport there with a Necklace" +
                        " of Passage.");
        talkToJorral.addDialogStep("The Outpost");
        talkToJorral.addDialogStep("Tell me more.");
        talkToJorral.addDialogStep("Ok, I'll make a stand for history!");
        talkToSilverMerchant = new NPCStep(NpcID.SILVER_MERCHANT_8722, new RSTile(2658, 3316, 0), "Talk to the Silver Merchant in the East Ardougne Market.");
        talkToSilverMerchant.addDialogStep("Ask about the outpost.");
        dig = new ClickItemStep(ItemID.SPADE, "Dig", new RSTile(2442, 3140, 0), enchantedKey);
        openChest = new UseItemOnItemStep(ItemID.ENCHANTED_KEY, ItemID.CHEST, !Inventory.contains(ItemID.CHEST), enchantedKeyHighlighted, chest);
        talkToBlanin = new NPCStep(NpcID.BLANIN, new RSTile(2673, 3670, 0), "Talk to Blanin in south east Rellekka.");
        talkToDron = new NPCStep(NpcID.DRON, new RSTile(2661, 3698, 0), "Talk to Dron in north Rellekka.");
        talkToDron.addDialogStep("I'm after important answers.");
        talkToDron.addDialogStep("Why, you're the famous warrior Dron!");
        talkToDron.addDialogStep("An iron mace");
        talkToDron.addDialogStep(/*1,*/ "Breakfast");
        talkToDron.addDialogStep(/*2,*/ "Lunch");
        talkToDron.addDialogStep("Bunnies");
        talkToDron.addDialogStep("Red");
        talkToDron.addDialogStep("36");
        talkToDron.addDialogStep("8");
        talkToDron.addDialogStep("Fifth and Fourth");
        talkToDron.addDialogStep("North East side of town");
        talkToDron.addDialogStep("Blanin");
        talkToDron.addDialogStep("Fluffy");
        talkToDron.addDialogStep("12, but what does that have to do with anything?");


        talkToDroalak = new NPCStep(NpcID.DROALAK_3494, new RSTile(3659, 3468, 0), ghostSpeakAmulet);
        talkToMelina = new NPCStep(NpcID.MELINA_3492, new RSTile(3674, 3479, 0), saphAmulet, ghostSpeakAmulet);
        returnToDroalak = new NPCStep(NpcID.DROALAK_3494, new RSTile(3659, 3468, 0), "Return to Droalak outside the general store.");
        returnToJorral = new NPCStep(NpcID.JORRAL, new RSTile(2436, 3346, 0), scroll, journal);
        continueTalkingToJorral = new NPCStep(NpcID.JORRAL, new RSTile(2436, 3346, 0), "Return to Jorral north of West Ardougne.");
        returnToJorral.addSubSteps(continueTalkingToJorral);
        talkToLathas = new NPCStep(NpcID.KING_LATHAS_9005, new RSTile(2578, 3293, 1), "Talk to King Lathas in East Ardougne castle.");
        talkToLathas.addDialogStep("Talk about the outpost.");
        finishQuest = new NPCStep(NpcID.JORRAL, new RSTile(2436, 3346, 0), letter);
    }


    public List<ItemRequirement> getItemRequirements() {
        ArrayList<ItemRequirement> reqs = new ArrayList<>();
        reqs.add(spade);
        reqs.add(saphAmulet);
        reqs.add(ghostSpeakAmulet);
        return reqs;
    }


    public List<ItemRequirement> getItemRecommended() {
        ArrayList<ItemRequirement> reqs = new ArrayList<>();
        reqs.add(ardougneTeleport);
        reqs.add(ectophial);
        reqs.add(ringOfDueling);
        reqs.add(passage);
        reqs.add(rellekaTeleport);
        reqs.add(runRestoreItems);
        return reqs;
    }


    public int getQuestPointReward() {
        return 3;
    }


  /*  public List<ExperienceReward> getExperienceRewards()
    {
        return Arrays.asList(
                new ExperienceReward(Skill.CRAFTING, 1000),
                new ExperienceReward(Skill.PRAYER, 1000));
    }

    public List<ItemReward> getItemRewards()
    {
        return Arrays.asList(
                new ItemReward("750 Coins", ItemID.COINS_995, 750),
                new ItemReward("An Enchanted Key", ItemID.ENCHANTED_KEY, 1));
    }*/

    @Override
    public List<Requirement> getGeneralRequirements() {
        return Arrays.asList(new QuestRequirement(Quest.PRIEST_IN_PERIL, Quest.State.COMPLETE),
                new QuestRequirement(Quest.THE_RESTLESS_GHOST, Quest.State.IN_PROGRESS));
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

    }

    @Override
    public String questName() {
        return "Making History";
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
        return Quest.MAKING_HISTORY.getState().equals(Quest.State.COMPLETE);
    }
}
