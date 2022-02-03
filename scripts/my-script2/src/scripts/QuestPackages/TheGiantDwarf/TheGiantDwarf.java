package scripts.QuestPackages.TheGiantDwarf;

import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import scripts.ItemID;
import scripts.NpcID;
import scripts.ObjectID;
import scripts.QuestSteps.NPCStep;
import scripts.QuestSteps.ObjectStep;
import scripts.QuestSteps.QuestStep;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.*;
import scripts.Requirements.Items.ItemCollections;
import scripts.Requirements.Util.Conditions;
import scripts.Requirements.Util.LogicType;
import scripts.Requirements.Util.Operation;
import scripts.Tasks.Priority;

public class TheGiantDwarf implements QuestTask {

    //Items Required
    ItemRequirement coins2500, logs, tinderbox, coal, ironBar, lawRune, airRune, sapphires3, oresBars, redberryPie, redberryPieNoInfo,
            weightBelow30, inventorySpace, coins200, bookOnCostumes, exquisiteClothes, exquisiteBoots, dwarvenBattleaxe, leftBoot,
            dwarvenBattleaxeBroken, dwarvenBattleaxeSapphires;

    Requirement weightBelow30Check, inventorySpaceCheck;

    //Items Recommended
    ItemRequirement rellekkaTeleport, fairyRings, staminaPotions, varrockTeleport, houseTeleport, clay10,
            copperOre10, tinOre10, ironOre10, coal10, silverOre10, goldOre10, mithrilOre10,
            bronzeBar10, ironbar10, silverBar10, goldBar10, steelBar10, mithrilBar10;

    Requirement inTrollRoom, inKeldagrim, inDwarfEntrance, askedToStartMachine, talkedToVermundi,
            talkedToLibrarian, hasBookOnCostumes, talkedToVermundiWithBook, usedCoalOnMachine, startedMachine,
            hasExquisiteClothes, talkedToSaro, talkedToDromund, hasLeftBoot, hasExquisiteBoots, givenThurgoPie,
            talkedToLibrarianAboutReldo, talkedToSantiri, usedSapphires, hasDwarvenBattleaxe, givenExquisiteClothes,
            givenExquisiteBoots, givenDwarvenBattleaxe, inConsortium, completedSecretaryTasks, completedDirectorTasks,
            joinedCompany, previouslyGivenPieToThurgo, talkedToReldo;

    QuestStep enterDwarfCave, enterDwarfCave2, talkToBoatman, talkToVeldaban, talkToBlasidar, talkToVermundi,
            talkToLibrarian, climbBookcase, talkToVermundiWithBook, talkToVermundiAfterBook, useCoalOnMachine, startMachine,
            talkToVermundiWithMachine,
            talkToSaro, talkToDromund, takeLeftBoot, takeRightBoot,
            talkToSantiri, useSapphires, talkToLibrarianAboutImcando, talkToReldo, talkToThurgo, talkToThurgoAfterPie,
            giveItemsToRiki, talkToBlasidarAfterItems,
            enterConsortium, talkToSecretary, talkToDirector, joinCompany, talkToDirectorAfterJoining, leaveConsortium, talkToVeldabanAfterJoining;

    //RSAreas
    RSArea keldagrim, keldagrim2, trollRoom, dwarfEntrance, consortium;


   /* public void setupItemRequirements()
    {
        // Required
        coins2500 = new ItemRequirement("coins", ItemID.COINS_995, 10000);
      //  coins2500.setTooltip("Bring more to be safe.");
        coins200 = new ItemRequirement("Coins", ItemID.COINS_995, 200);
        logs = new ItemRequirement("Logs", ItemID.LOGS);
      //  logs.setTooltip("Most logs will work, however, arctic pine logs do not work.");
        logs.addAlternates(ItemID.OAK_LOGS, ItemID.WILLOW_LOGS, ItemID.TEAK_LOGS, ItemID.MAPLE_LOGS,
                ItemID.MAHOGANY_LOGS, ItemID.YEW_LOGS, ItemID.MAGIC_LOGS, ItemID.REDWOOD_LOGS);
        tinderbox = new ItemRequirement("Tinderbox", ItemID.TINDERBOX);

        coal = new ItemRequirement("Coal", ItemID.COAL);
        coal.setTooltip("There are rocks in the city, but you need 30 Mining.");

        ironBar = new ItemRequirement("Iron bar", ItemID.IRON_BAR);
        //ironBar.setTooltip("Purchasable during the quest.");
        lawRune = new ItemRequirement("Law rune", ItemID.LAW_RUNE);

        airRune = new ItemRequirement("Air rune", ItemID.AIR_RUNE);

        sapphires3 = new ItemRequirement("Cut sapphires", ItemID.SAPPHIRE, 3);
       // sapphires3.setTooltip("Purchasable during the quest.");

        oresBars = new ItemRequirement("Various ores and bars", -1, -1);
        //oresBars.setTooltip("Obtainable during the quest.");
        redberryPie = new ItemRequirement("Redberry pie", ItemID.REDBERRY_PIE);
      //  redberryPie.setTooltip("Unless you have previously given Thurgo an extra pie with nothing in return.");
        redberryPieNoInfo = new ItemRequirement("Redberry pie", ItemID.REDBERRY_PIE);

        // Recommended
        houseTeleport = new ItemRequirement("A house teleport if your house is in Rimmington and if you have no faster way to Mudskipper Point", -1, -1);
        rellekkaTeleport = new ItemRequirement("A Camelot/Rellekka teleport (for starting the quest)", ItemID.ENCHANTED_LYRE5);
        rellekkaTeleport.addAlternates(ItemID.ENCHANTED_LYRE4, ItemID.ENCHANTED_LYRE3, ItemID.ENCHANTED_LYRE2,
                ItemID.ENCHANTED_LYRE1, ItemID.RELLEKKA_TELEPORT, ItemID.CAMELOT_TELEPORT);
        rellekkaTeleport.addAlternates(ItemCollections.getSlayerRings());
        fairyRings = new ItemRequirement("Access to fairy rings", -1, -1);
        staminaPotions = new ItemRequirement("Some stamina potions (when collecting the ores)", ItemCollections.getStaminaPotions());
        varrockTeleport = new ItemRequirement("A ring of wealth/amulet of glory/Varrock teleport", ItemID.VARROCK_TELEPORT);
     //   varrockTeleport.addAlternates(ItemCollections.getRingOfWealths());
      //  varrockTeleport.addAlternates(ItemCollections.getAmuletOfGlories());
        clay10 = new ItemRequirement("Clay", ItemID.CLAY, 10);
        copperOre10 = new ItemRequirement("Copper ore", ItemID.COPPER_ORE, 10);
        tinOre10 = new ItemRequirement("Tin ore", ItemID.TIN_ORE, 10);
        ironOre10 = new ItemRequirement("Iron ore", ItemID.IRON_ORE, 10);
        coal10 = new ItemRequirement("Coal", ItemID.COAL, 10);
        silverOre10 = new ItemRequirement("Silver ore", ItemID.SILVER_ORE, 10);
        goldOre10 = new ItemRequirement("Gold ore", ItemID.GOLD_ORE, 10);
        mithrilOre10 = new ItemRequirement("Mithril ore", ItemID.MITHRIL_ORE, 10);
        bronzeBar10 = new ItemRequirement("Bronze bar", ItemID.BRONZE_BAR, 10);
        ironbar10 = new ItemRequirement("Iron bar", ItemID.IRON_BAR, 10);
        silverBar10 = new ItemRequirement("Silver bar", ItemID.SILVER_BAR, 10);
        goldBar10 = new ItemRequirement("Gold bar", ItemID.GOLD_BAR, 10);
        steelBar10 = new ItemRequirement("Steel bar", ItemID.STEEL_BAR, 10);
        mithrilBar10 = new ItemRequirement("Mithril bar", ItemID.MITHRIL_BAR, 10);

        // Extra
        weightBelow30 = new ItemRequirement("Weight below 30 kg", -1, -1);
        inventorySpace = new ItemRequirement("Free inventory slot", -1);

        weightBelow30Check = new WeightRequirement("Weight below 30 kg", 30, Operation.LESS_EQUAL);
        inventorySpaceCheck = new FreeInventorySlotRequirement(InventoryID.INVENTORY, 1);

        // Quest
        bookOnCostumes = new ItemRequirement("Book on costumes", ItemID.BOOK_ON_COSTUMES);
        exquisiteClothes = new ItemRequirement("Exquisite clothes", ItemID.EXQUISITE_CLOTHES);
        exquisiteBoots = new ItemRequirement("Exquisite boots", ItemID.EXQUISITE_BOOTS);
        dwarvenBattleaxe = new ItemRequirement("Dwarven battleaxe", ItemID.DWARVEN_BATTLEAXE_5059);

        leftBoot = new ItemRequirement("Left boot", ItemID.LEFT_BOOT);

        dwarvenBattleaxeBroken = new ItemRequirement("Dwarven battleaxe", ItemID.DWARVEN_BATTLEAXE);
        dwarvenBattleaxeBroken.addAlternates(ItemID.DWARVEN_BATTLEAXE_5057);


        dwarvenBattleaxeSapphires = new ItemRequirement("Dwarven battleaxe", ItemID.DWARVEN_BATTLEAXE_5058);
    }

    public void setupRSAreas() {
        trollRoom = new RSArea(new RSTile(2762, 10123, 0), new RSTile(2804, 10164, 0));
        dwarfEntrance = new RSArea(new RSTile(2814, 10121, 0), new RSTile(2884, 10139, 0));
        keldagrim = new RSArea(new RSTile(2816, 10177, 0), new RSTile(2943, 10239, 0));
        keldagrim2 = new RSArea(new RSTile(2901, 10150, 0), new RSTile(2943, 10177, 0));
        consortium = new RSArea(new RSTile(2861, 10186, 1), new RSTile(2897, 10212, 1));
    }

    public void setupConditions() {
        inTrollRoom = new AreaRequirement(trollRoom);
        inDwarfEntrance = new AreaRequirement(dwarfEntrance);
        inKeldagrim = new AreaRequirement(keldagrim, keldagrim2);

        // On boat
        // 575 0->1
        // 579 0->1

        // Arrived on boat:
        // 577 0->9
        // 578 0->9

        // Blasidar said about first topic:
        // 573 0->1

        talkedToVermundi = new Conditions(true, LogicType.OR,
                new WidgetTextRequirement(WidgetInfo.DIALOG_NPC_TEXT, "Great, thanks a lot, I'll check out the library!"),
                new WidgetTextRequirement(119, 3, true, "<col=000080>I should speak to the " +
                        "<col=800000>librarian<col=000080> in Keldagrim-West. He"),
                new WidgetTextRequirement(219, 1, 2, "Yes, about those special clothes again..."));

        talkedToLibrarian = new Conditions(true, LogicType.OR,
                new WidgetTextRequirement(WidgetInfo.DIALOG_NPC_TEXT,
                        "Let me think... I believe it is on the top shelf of one of<br>the bookcases in the library, because it is such an old<br>book.",
                        "Well, thanks, I'll have a look."
                ),
                new WidgetTextRequirement(119, 3, true, "<col=000080>library of Keldagrim-West. I should ")
        );

        hasBookOnCostumes = new Conditions(true, LogicType.OR,
                bookOnCostumes,
                new WidgetTextRequirement(119, 3, true, "<col=000080>with the <col=800000>book on dwarven costumes<col=000080> that I got from the")
        );

        talkedToVermundiWithBook = new VarbitRequirement(584, 1);

        askedToStartMachine = new Conditions(true, LogicType.OR,
                new WidgetTextRequirement(217, 4,
                        "Don't worry, I'll get them for you. Let's see... some<br>coal and some logs. Shouldn't be too hard."),
                new WidgetTextRequirement(WidgetInfo.DIALOG_NPC_TEXT, "Well, like I said, I can't do anything really " +
                        "without my<br>spinning machine."),
                new WidgetTextRequirement(119, 3, true, "<col=000080>I must get <col=800000>coal<col=000080> and <col=800000>logs<col=000080>.")
        );

        usedCoalOnMachine = new Conditions(true, LogicType.OR,
                new WidgetTextRequirement(WidgetInfo.DIALOG_NPC_TEXT, "it needs to be powered up."),
                new ChatMessageRequirement("You load the spinning machine with coal and logs."),
                new WidgetTextRequirement(119, 3, true, "<col=000080>I have to start up the dwarven <col=800000>spinning machine<col=000080> in the"));

        startedMachine = new Conditions(true, LogicType.OR,
                new ChatMessageRequirement("...and successfully start the engine!"),
                new WidgetTextRequirement(119, 3, true, "<col=000080>I should ask <col=800000>Vermundi<col=000080>, the owner of the <col=800000>clothes stall<col=000080> in the"));

        givenExquisiteClothes = new Conditions(true, new VarbitRequirement(576, true, 0));
        hasExquisiteClothes = new Conditions(true, LogicType.OR,
                givenExquisiteClothes,
                exquisiteClothes,
                new WidgetTextRequirement(119, 3, true, "<col=000080>I have the <col=800000>exquisite clothes<col=000080> that the <col=800000>sculptor<col=000080> needs. Now I"));

        talkedToSaro = new Conditions(true, LogicType.OR,
                // TODO: You need to click 'click to continue' here for the step to actually progress
                new WidgetTextRequirement(217, 4, "Thanks!"),
                new WidgetTextRequirement(119, 3, true, "<col=000080>I should seek out the <col=800000>eccentric old dwarf<col=000080> in <col=800000>Keldagrim-"),
                new WidgetTextRequirement(WidgetInfo.DIALOG_NPC_TEXT, "I thought I already told you where to get them?")
        );

        talkedToDromund = new Conditions(true, LogicType.OR,
                // TODO: You need to click 'click to continue' here for the step to actually progress
                new WidgetTextRequirement(WidgetInfo.DIALOG_NPC_TEXT, "Get out you pesky human! The boots are mine and"),
                new WidgetTextRequirement(119, 3, true, "<col=000080>I must find some way to get the <col=800000>pair of boots<col=000080> from the"),
                new WidgetTextRequirement(217, 4, "Are you sure you don't want to give me those boots?"));

        hasLeftBoot = new Conditions(true, LogicType.OR,
                leftBoot,
                new WidgetTextRequirement(119, 3, true,
                        "<str>I have sneakily stolen one boots from the old dwarf.")
        );

        givenExquisiteBoots = new VarbitRequirement(576, true, 1);
        hasExquisiteBoots = new Conditions(true, LogicType.OR,
                givenExquisiteBoots,
                exquisiteBoots,
                new WidgetTextRequirement(119, 3, true, "<col=000080>I have the <col=800000>exquisite pair of boots<col=000080> that the <col=800000>sculptor<col=000080> needs.")
        );

        talkedToSantiri = new Conditions(true, dwarvenBattleaxeBroken);

        usedSapphires = new Conditions(true, LogicType.OR,
                new ChatMessageRequirement("Great, all it needs now is a little sharpening!"),
                dwarvenBattleaxeSapphires);

        talkedToLibrarianAboutReldo = new Conditions(true, LogicType.OR,
                new WidgetTextRequirement(WidgetInfo.DIALOG_NPC_TEXT, "I suppose you could try Reldo"),
                new WidgetTextRequirement(217, 4, "Do you think he can help me?"),
                new WidgetTextRequirement(WidgetInfo.DIALOG_NPC_TEXT, "He lives quite a good deal closer"));
        previouslyGivenPieToThurgo = new VarplayerRequirement(QuestVarPlayer.QUEST_THE_KNIGHTS_SWORD.getId(), 3, Operation.GREATER_EQUAL);
        talkedToReldo = new Conditions(true, LogicType.OR,
                new WidgetTextRequirement(WidgetInfo.DIALOG_NPC_TEXT, "you could try taking them some redberry pie."));

        givenThurgoPie = new VarbitRequirement(580, 1);
        // Thurgo makes axe, 2781 = 1
        givenDwarvenBattleaxe = new VarbitRequirement(576, true, 2);
        hasDwarvenBattleaxe = new Conditions(true, LogicType.OR,
                givenDwarvenBattleaxe,
                dwarvenBattleaxe,
                new WidgetTextRequirement(119, 3, true, "<col=000080>I must give the <col=800000>restored battleaxe<col=000080> to <col=800000>Riki<col=000080>, the <col=800000>sculptor's"));

        inConsortium = new AreaRequirement(consortium);

        completedSecretaryTasks = new Conditions(true, new WidgetTextRequirement(WidgetInfo.DIALOG_NPC_TEXT, "I'm afraid I have no more work to offer you", "You should speak directly to the director."));
        completedDirectorTasks = new Conditions(true, new WidgetTextRequirement(WidgetInfo.DIALOG_NPC_TEXT, "Have you ever considered joining"));
        joinedCompany = new Conditions(true, LogicType.OR,
                new VarbitRequirement(578, 1), // Purple Pewter
                new VarbitRequirement(578, 2), // Yellow Fortune
                new VarbitRequirement(578, 3), // Blue Opal
                new VarbitRequirement(578, 4), // Green Gem
                new VarbitRequirement(578, 5), // White Chisel
                new VarbitRequirement(578, 6), // Silver Cog
                new VarbitRequirement(578, 7), // Brown Engine
                new VarbitRequirement(578, 8), // Would be Red Axe?
                new WidgetTextRequirement(WidgetInfo.DIALOG_NPC_TEXT, "I will not disappoint you."),
                new WidgetTextRequirement(WidgetInfo.DIALOG_NPC_TEXT, "Come in, come in my friend!"));
    }

    public void setupSteps()
    {
        // Starting off
        enterDwarfCave = new ObjectStep( ObjectID.TUNNEL_5008, new RSTile(2732, 3713, 0),
                "Speak to the Dwarven Boatman.");
        enterDwarfCave2 = new ObjectStep( ObjectID.CAVE_ENTRANCE_5973, new RSTile(2781, 10161, 0),
                "Speak to the Dwarven Boatman.");
        talkToBoatman = new NPCStep( NpcID.DWARVEN_BOATMAN_7725, new RSTile(2829, 10129, 0), "Speak to the Dwarven Boatman.");
        talkToBoatman.addDialogStep("That's a deal!");
        talkToBoatman.addDialogStep("Yes, I'm ready and don't mind it taking a few minutes.");
        //talkToBoatman.addSubSteps(enterDwarfCave, enterDwarfCave2);

        talkToVeldaban = new NPCStep( NpcID.COMMANDER_VELDABAN_6045, new RSTile(2827, 10214, 0),
                "Finish speaking to Commander Veldaban.");
        talkToVeldaban.addDialogStep("Yes, I will do this.");

        talkToBlasidar = new NPCStep( NpcID.BLASIDAR_THE_SCULPTOR, new RSTile(2907, 10205, 0),
                "Talk to Blasidar the sculptor in the east of Keldagrim.");
        talkToBlasidar.addDialogStep("Yes, I will do this.");

        // Clothes fit for a king
        talkToVermundi = new NPCStep( NpcID.VERMUNDI, new RSTile(2887, 10188, 0), "Talk to Vermundi west of " +
                "Blasidar. If you already have, open the Quest Journal to re-sync.");
        talkToVermundi.addDialogStep("Yes, I'm looking for some special clothes.");

        talkToLibrarian = new NPCStep( NpcID.LIBRARIAN, new RSTile(2861, 10226, 0), "Talk to the Librarian.");
        talkToLibrarian.addDialogStep("Do you know anything about King Alvis' clothes?");

        climbBookcase = new ObjectStep( ObjectID.BOOKCASE_6092, new RSTile(2859, 10228, 0),
                "Climb any bookcase to find a book on costumes.", weightBelow30Check);

        talkToVermundiWithBook = new NPCStep( NpcID.VERMUNDI, new RSTile(2887, 10188, 0),
                "Talk to Vermundi with the book on costumes.", bookOnCostumes);
        talkToVermundiWithBook.addDialogStep("Yes, about those special clothes again...");

        talkToVermundiAfterBook = new NPCStep( NpcID.VERMUNDI, new RSTile(2887, 10188, 0),
                "Talk to Vermundi again.");
        talkToVermundiAfterBook.addDialogStep("Yes, about those special clothes again...");
       // talkToVermundiWithBook.addSubSteps(talkToVermundiAfterBook);

        useCoalOnMachine = new ObjectStep( ObjectID.SPINNING_MACHINE, new RSTile(2885, 10189, 0),
                "Use your coal on the spinning machine light it with a tinder box.", coal, logs);


        startMachine = new ObjectStep( ObjectID.SPINNING_MACHINE, new RSTile(2885, 10189, 0),
                "Start the spinning machine with a tinder box.", tinderbox);


        talkToVermundiWithMachine = new NPCStep( NpcID.VERMUNDI, new RSTile(2887, 10188, 0),
                "Talk to Vermundi again and pay 200gp.", coins200);
        talkToVermundiWithMachine.addDialogStep("Yes, about those special clothes again...");
        talkToVermundiWithMachine.addDialogStep("I'll pay.");

        // Boots fit for a king
        talkToSaro = new NPCStep( NpcID.SARO, new RSTile(2827, 10198, 0), "Talk to Saro in West Keldagrim.");
        talkToSaro.addDialogStep("Yes, I'm looking for a pair of special boots.");

        talkToDromund = new NPCStep( NpcID.DROMUND, new RSTile(2838, 10224, 0), "Talk to Dromund.");

        takeLeftBoot = new DetailedQuestStep( new RSTile(2838, 10220, 0),
                "Take the Left boot when Dromund isn't looking.");

        takeRightBoot = new DetailedQuestStep( new RSTile(2836, 10226, 0),
                "Take the Right boot from outside" +
                " his window when he isn't looking using Telekinetic Grab.", lawRune, airRune, inventorySpaceCheck);

        // An axe fit for a king
        talkToSantiri = new NPCStep( NpcID.SANTIRI, new RSTile(2828, 10231, 0), "Talk to Santiri.");
        talkToSantiri.addDialogStep("Yes, I'm looking for a particular battleaxe.");
        talkToSantiri.addDialogStep("Blasidar the sculptor needs it for his statue.");
        talkToSantiri.addDialogStep("Perhaps I can repair the axe?");

        useSapphires = new DetailedQuestStep( "Use the 3 sapphires on the axe.", dwarvenBattleaxeBroken, sapphires3);

        talkToLibrarianAboutImcando = new NPCStep( NpcID.LIBRARIAN, new RSTile(2861, 10226, 0), "Talk to the" +
                " Librarian about Imcando Dwarves. If you already have and no option appears for  go talk to Reldo " +
                "in Varrock Castle.");
        talkToLibrarianAboutImcando.addDialogStep("Can you help me find an Imcando dwarf?");
        talkToLibrarianAboutImcando.conditionToHideInSidebar(previouslyGivenPieToThurgo);

        talkToReldo = new NPCStep( NpcID.RELDO_4243, new RSTile(3211, 3494, 0),
                "Talk to Reldo in Varrock Castle's library.");
        talkToReldo.addDialogStep("Ask about Imcando dwarves.");
        talkToReldo.conditionToHideInSidebar(previouslyGivenPieToThurgo);

        talkToThurgo = new NPCStep( NpcID.THURGO, new RSTile(3001, 3144, 0),
                "Talk to Thurgo at Mudskipper Point.", redberryPieNoInfo, ironBar, dwarvenBattleaxeSapphires);
        talkToThurgo.addDialogStep("Something else.");
        talkToThurgo.addDialogStep( "Would you like a redberry pie?");
        talkToThurgo.addDialogStep( "Would you like a redberry pie?");
        talkToThurgo.addDialogStep("Return to Keldagrim immediately.");

        talkToThurgoAfterPie = new NPCStep( NpcID.THURGO, new RSTile(3001, 3144, 0),
                "Talk to Thurgo at Mudskipper Point.", ironBar, dwarvenBattleaxeSapphires);
        talkToThurgoAfterPie.addDialogStep("Something else.");
        talkToThurgoAfterPie.addDialogStep("Can you help me with this ancient axe?");
        talkToThurgoAfterPie.addDialogStep("Can you repair that axe now?");
        talkToThurgoAfterPie.addDialogStep("Return to Keldagrim immediately.");

        // Halfway there
        giveItemsToRiki = new NPCStep( NpcID.RIKI_THE_SCULPTORS_MODEL, new RSTile(2887, 10188, 0),
                "Talk to Ricky the sculptor's model to give him the clothes, axe and boots.",
                exquisiteClothes.hideConditioned(givenExquisiteClothes), exquisiteBoots.hideConditioned(givenExquisiteBoots),
                dwarvenBattleaxe.hideConditioned(givenDwarvenBattleaxe));
        giveItemsToRiki.addDialogStep("Return to Keldagrim immediately.");
        ((NPCStep) giveItemsToRiki).addAlternateNpcs(NpcID.RIKI_THE_SCULPTORS_MODEL_2349, NpcID.RIKI_THE_SCULPTORS_MODEL_2350,
                NpcID.RIKI_THE_SCULPTORS_MODEL_2351, NpcID.RIKI_THE_SCULPTORS_MODEL_2352, NpcID.RIKI_THE_SCULPTORS_MODEL_2353,
                NpcID.RIKI_THE_SCULPTORS_MODEL_2354, NpcID.RIKI_THE_SCULPTORS_MODEL_2355);
        talkToBlasidarAfterItems = new NPCStep( NpcID.BLASIDAR_THE_SCULPTOR, new RSTile(2907, 10205, 0),
                "Talk to Blasidar the sculptor.");

        // Joining the consortium
        enterConsortium = new ObjectStep( ObjectID.STAIRS_6087, new RSTile(2895, 10210, 0),
                "Go to the upper floor of the market.");

        talkToSecretary = new NPCStep( NpcID.BLUE_OPAL_SECRETARY, new RSTile(2869, 10205, 1),
                "Keep talking to the same secretary and complete the tasks given. If you don't want to do one of the task, " +
                        "just talk to them again for a different one.");
        //TODO: Add a way to check which company is chosen
        //((NPCStep) talkToSecretary).addAlternateNpcs(NpcID.PURPLE_PEWTER_SECRETARY, NpcID.GREEN_GEMSTONE_SECRETARY,
        // NpcID.SILVER_COG_SECRETARY, NpcID.WHITE_CHISEL_SECRETARY);
        talkToSecretary.addDialogStep("Is there anything I can help you with?");
        talkToSecretary.addDialogStep("Do you have another task for me?");

        talkToDirector = new NPCStep( NpcID.BLUE_OPAL_DIRECTOR_5999, new RSTile(2879, 10199, 1),
                "Keep talking to the director of the same secretary and complete the tasks given. If you don't want to do one of the task, " +
                        "just talk to them again for a different one.");
        ((NPCStep) talkToDirector).addAlternateNpcs(NpcID.BLUE_OPAL_DIRECTOR);
        talkToDirector.addDialogStep("Do you have any more tasks for me?");

        joinCompany = new NPCStep( NpcID.BLUE_OPAL_DIRECTOR_5999, new RSTile(2879, 10199, 1),
                "Talk to the director to join the company.");
        ((NPCStep) joinCompany).addAlternateNpcs(NpcID.BLUE_OPAL_DIRECTOR);
        joinCompany.addDialogStep("I'd like to officially join your company.");

        talkToDirectorAfterJoining = new NPCStep( NpcID.BLUE_OPAL_DIRECTOR_5999, new RSTile(2879, 10199, 1),
                "Talk to the director after joining the company.");
        ((NPCStep) talkToDirectorAfterJoining).addAlternateNpcs(NpcID.BLUE_OPAL_DIRECTOR);
        talkToDirectorAfterJoining.addDialogStep("Blasidar the sculptor has sent me.");
        talkToDirectorAfterJoining.addDialogStep("I would support you.");
        //TODO: Make this conditional for the company chosen
        talkToDirectorAfterJoining.addDialogStep("Yes! Long live the Blue Opal!");

        leaveConsortium = new ObjectStep( ObjectID.STAIRS_6088, new RSTile(2863, 10210, 1),
                "Talk to Commander Veldaban in west Keldagrim.");

        talkToVeldabanAfterJoining = new NPCStep( NpcID.COMMANDER_VELDABAN_6045, new RSTile(2827, 10214, 0),
                "Talk to Commander Veldaban.");
        talkToVeldabanAfterJoining.addDialogStep("I'm ready.");
    }*/

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
        return "The Giant Dwarf";
    }

    @Override
    public boolean checkRequirements() {
        return false;
    }
}
