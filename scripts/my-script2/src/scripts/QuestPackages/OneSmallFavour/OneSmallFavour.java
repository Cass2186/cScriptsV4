package scripts.QuestPackages.OneSmallFavour;

import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import scripts.*;
import scripts.QuestSteps.*;
import scripts.Requirements.*;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;

public class OneSmallFavour implements QuestTask {

    Requirement lamp1Empty, lamp1Full, lamp2Empty, lamp2Full,
            lamp3Empty, lamp3Full, lamp4Empty, lamp4Full, lamp5Empty, lamp5Full, lamp6Empty, lamp6Full, lamp7Empty, lamp7Full, lamp8Empty, lamp8Full, allEmpty, allFull,
            slagilithNearby, petraNearby, addedOrnaments, addedDirectionals, addedWeathervanePillar, hasOrUsedOrnament, hasOrUsedDirectionals,
            hasOrUsedWeathervanePillar;


    RSArea sanfewRoom = new RSArea(new RSTile(2893, 3423, 1), new RSTile(2903, 3433, 1));
    RSArea hamBase = new RSArea(new RSTile(3140, 9600, 0), new RSTile(3190, 9655, 0));
    RSArea dwarvenMine = new RSArea(new RSTile(2960, 9696, 0), new RSTile(3062, 9854, 0));
    RSArea goblinCave = new RSArea(new RSTile(2560, 9792, 0), new RSTile(2623, 9855, 0));
    RSArea scrollSpot = new RSArea(new RSTile(2616, 9835, 0), new RSTile(2619, 9835, 0));
    RSArea seersVillageUpstairs = new RSArea(new RSTile(2698, 3468, 1), new RSTile(2717, 3476, 1));
    RSArea roof = new RSArea(new RSTile(2695, 3469, 3), new RSTile(2716, 3476, 3));

    AreaRequirement inSanfewRoom = new AreaRequirement(sanfewRoom);
    AreaRequirement inHamBase = new AreaRequirement(hamBase);
    AreaRequirement inDwarvenMine = new AreaRequirement(dwarvenMine);
    AreaRequirement inGoblinCave = new AreaRequirement(goblinCave);
    AreaRequirement inScrollSpot = new AreaRequirement(scrollSpot);
    AreaRequirement inSeersVillageUpstairs = new AreaRequirement(seersVillageUpstairs);
    AreaRequirement onRoof = new AreaRequirement(roof);

    /**
     * ITEM REQUIREMENTS
     */
    ItemRequirement steelBars4 = new ItemRequirement(ItemId.STEEL_BAR, 4);
    ItemRequirement steelBars3 = new ItemRequirement(ItemId.STEEL_BAR, 3);
    ItemRequirement steelBar = new ItemRequirement(ItemId.STEEL_BAR);
    ItemRequirement softClay = new ItemRequirement(ItemId.SOFT_CLAY);
    ItemRequirement chisel = new ItemRequirement(ItemId.CHISEL);
    ItemRequirement bronzeBar = new ItemRequirement(ItemId.BRONZE_BAR);
    ItemRequirement ironBar = new ItemRequirement(ItemId.IRON_BAR);
    ItemRequirement guam2 = new ItemRequirement(ItemId.GUAM_LEAF, 2);
    ItemRequirement guam = new ItemRequirement(ItemId.GUAM_LEAF);
    ItemRequirement marrentill = new ItemRequirement(ItemId.MARRENTILL);
    ItemRequirement harralander = new ItemRequirement(ItemId.HARRALANDER);
    ItemRequirement hammer = new ItemRequirement(ItemId.HAMMER);

    ItemRequirement hammerHighlight = new ItemRequirement(ItemId.HAMMER);

    ItemRequirement emptyCup = new ItemRequirement(ItemId.EMPTY_CUP);
    ItemRequirement pigeonCages5 = new ItemRequirement(ItemId.PIGEON_CAGE, 5);

    ItemRequirement pot = new ItemRequirement(ItemId.POT);

    ItemRequirement hotWaterBowl = new ItemRequirement(ItemId.BOWL_OF_HOT_WATER);
    ItemRequirement varrockTeleports = new ItemRequirement(ItemId.VARROCK_TELEPORT, 2);
    ItemRequirement faladorTeleports = new ItemRequirement(ItemId.FALADOR_TELEPORT, 2);
    ItemRequirement ardougneTeleports = new ItemRequirement(ItemId.ARDOUGNE_TELEPORT, 2);
    ItemRequirement camelotTeleports = new ItemRequirement(ItemId.CAMELOT_TELEPORT, 2);
    ItemRequirement lumbridgeTeleports = new ItemRequirement(ItemId.LUMBRIDGE_TELEPORT, 2);

    ItemRequirement bluntAxe = new ItemRequirement(ItemId.BLUNT_AXE);

    ItemRequirement herbalTincture = new ItemRequirement(ItemId.HERBAL_TINCTURE);
    ItemRequirement potWithLid = new ItemRequirement(ItemId.AIRTIGHT_POT);
    ItemRequirement cupOfWater = new ItemRequirement(ItemId.CUP_OF_HOT_WATER);
    ItemRequirement harrTea = new ItemRequirement(ItemId.HERB_TEA_MIX);
    ItemRequirement guamTea = new ItemRequirement(ItemId.HERB_TEA_MIX_4466);
    ItemRequirement marrTea = new ItemRequirement(ItemId.HERB_TEA_MIX_4468);
    ItemRequirement harrMarrTea = new ItemRequirement(ItemId.HERB_TEA_MIX_4470);
    ItemRequirement guamHarrTea = new ItemRequirement(ItemId.HERB_TEA_MIX_4472);
    ItemRequirement guam2Tea = new ItemRequirement(ItemId.HERB_TEA_MIX_4474);
    ItemRequirement guamMarrTea = new ItemRequirement(ItemId.HERB_TEA_MIX_4476);
    ItemRequirement guamHarrMarrTea = new ItemRequirement(ItemId.HERB_TEA_MIX_4478);
    ItemRequirement guam2MarrTea = new ItemRequirement(ItemId.HERB_TEA_MIX_4480);
    ItemRequirement guam2HarrTea = new ItemRequirement(ItemId.HERB_TEA_MIX_4482);
    ItemRequirement herbTeaMix = new ItemRequirement(ItemId.HERB_TEA_MIX);
    //	herbTeaMix.addAlternates(ItemId.HERB_TEA_MIX_4466,ItemId.HERB_TEA_MIX_4468,ItemId.HERB_TEA_MIX_4470,
    //  ItemId.HERB_TEA_MIX_4472,ItemId.HERB_TEA_MIX_4474,ItemId.HERB_TEA_MIX_4476,
    //  ItemId.HERB_TEA_MIX_4478,ItemId.HERB_TEA_MIX_4480,ItemId.HERB_TEA_MIX_4482);

    ItemRequirement guthixRest = new ItemRequirement(ItemId.GUTHIX_REST3);
    ItemRequirement sapphire = new ItemRequirement(ItemId.SAPPHIRE);
    ItemRequirement opal = new ItemRequirement(ItemId.OPAL);
    ItemRequirement jade = new ItemRequirement(ItemId.JADE);
    ItemRequirement redTopaz = new ItemRequirement(ItemId.RED_TOPAZ);
    ItemRequirement uncutSapphire = new ItemRequirement(ItemId.UNCUT_SAPPHIRE);
    ItemRequirement uncutOpal = new ItemRequirement(ItemId.UNCUT_OPAL);
    ItemRequirement uncutJade = new ItemRequirement(ItemId.UNCUT_JADE);
    ItemRequirement uncutRedTopaz = new ItemRequirement(ItemId.UNCUT_RED_TOPAZ);
    ItemRequirement stodgyMattress = new ItemRequirement(ItemId.STODGY_MATTRESS);
    //stodgyMattress.setTooltip("You can buy another from Tindel Marchant in Port Khazard for 100 gp");
    ItemRequirement mattress = new ItemRequirement(ItemId.COMFY_MATTRESS);
    ItemRequirement animateRockScroll = new ItemRequirement(ItemId.ANIMATE_ROCK_SCROLL);
    ItemRequirement ItemReqanimateRockScrollHighlight = new ItemRequirement(ItemId.ANIMATE_ROCK_SCROLL);
    //	animateRockScrollHighlight.setTooltip("You can get another from Wizard Cromperty for 100 gp");
    ItemRequirement ironOxide = new ItemRequirement(ItemId.IRON_OXIDE);
    ItemRequirement brokenVane1 = new ItemRequirement(ItemId.BROKEN_VANE_PART);
    ItemRequirement directionals = new ItemRequirement(ItemId.DIRECTIONALS);
    ItemRequirement brokenVane2 = new ItemRequirement(4431);
    ItemRequirement ornament = new ItemRequirement(ItemId.ORNAMENT);
    ItemRequirement brokenVane3 = new ItemRequirement(ItemId.BROKEN_VANE_PART_4433);
    ItemRequirement weathervanePillar = new ItemRequirement(ItemId.WEATHERVANE_PILLAR);
    ItemRequirement weatherReport = new ItemRequirement(ItemId.WEATHER_REPORT);
    ItemRequirement potLid = new ItemRequirement(ItemId.POT_LID);
    ItemRequirement unfiredPotLid = new ItemRequirement(ItemId.UNFIRED_POT_LID);
    ItemRequirement breathingSalts = new ItemRequirement(ItemId.BREATHING_SALTS);
    ItemRequirement chickenCages5 = new ItemRequirement(ItemId.CHICKEN_CAGE, 5);
    ItemRequirement sharpenedAxe = new ItemRequirement(ItemId.SHARPENED_AXE);
    ItemRequirement redMahog = new ItemRequirement(ItemId.RED_MAHOGANY_LOG);


    NPCStep returnToArhein = new NPCStep(NpcID.ARHEIN, new RSTile(2804, 3432, 0),
            new String[]{"I have the weather report for you."}, weatherReport);

    NPCStep returnToBleemadge = new NPCStep("Captain Bleemadge", new RSTile(2847, 3498, 0),
            new String[]{"Hey there, did you get your T.R.A.S.H?"});

    ObjectStep returnUpToSanfew = new ObjectStep(16671, new RSTile(2899, 3429, 0), "Climb-up", Player.getPosition().getPlane() == 1);

    NPCStep returnToSanfew = new NPCStep("Sanfew", new RSTile(2899, 3429, 1),
            new String[]{"Hi there, the Gnome Pilot has agreed to take you to see the ogres!"});

    ObjectStep goDownToHammerspikeAgain = new ObjectStep(11867, new RSTile(3019, 3450, 0));
    NPCStep returnToHammerspike = new NPCStep(NpcID.HAMMERSPIKE_STOUTBEARD, new RSTile(2968, 9811, 0));

    NPCStep killGangMembers = new NPCStep(NpcID.DWARF_GANG_MEMBER,
            new RSTile(2968, 9811, 0));
    NPCStep talkToHammerspikeFinal = new NPCStep(NpcID.HAMMERSPIKE_STOUTBEARD, new RSTile(2968, 9811, 0));
    NPCStep returnToTassie = new NPCStep(NpcID.TASSIE_SLIPCAST, new RSTile(3085, 3409, 0));
    ObjectStep spinPotLid = new ObjectStep(14887, new RSTile(3087, 3409, 0), "Use",
            Interfaces.isInterfaceSubstantiated(270), softClay);

    GroundItemStep pickUpPot = new GroundItemStep(pot.getId());

    ObjectStep firePotLid = new ObjectStep(11601, "Fire", new RSTile(3085, 3407, 0),
            Interfaces.isInterfaceSubstantiated(270));

    UseItemOnItemStep usePotLidOnPot = new UseItemOnItemStep(pot.getId(), potLid.getId(),
            potWithLid.check());

    NPCStep returnToApothecary = new NPCStep(NpcID.APOTHECARY,
            new RSTile(3196, 3404, 0),
            new String[]{"Talk about One Small Favour."},
            potWithLid);

    NPCStep returnToHorvik = new NPCStep(NpcID.HORVIK, new RSTile(3229, 3437, 0),
            new String[]{"I have the tincture and the breathing salts."}, pigeonCages5,
            breathingSalts, herbalTincture);

    NPCStep talkToHorvikFinal = new NPCStep(NpcID.HORVIK, new RSTile(3229, 3437, 0),
            new String[]{"I have the five pigeon cages you asked for!"}, pigeonCages5);

    NPCStep returnToSeth = new NPCStep(NpcID.SETH_GROATS, new RSTile(3228, 3291, 0), chickenCages5);

    ObjectStep returnDownToJohnahus = new ObjectStep(5492, new RSTile(3166, 3252, 0));

    NPCStep returnToJohnahus = new NPCStep(NpcID.JOHANHUS_ULSBRECHT, new RSTile(3171, 9619, 0),
            new String[]{"You're in luck, I've managed to swing that chicken deal for you."});
    NPCStep returnToAggie = new NPCStep(NpcID.AGGIE, new RSTile(3086, 3258, 0),
            new String[]{"Good news! Jimmy has been released!"});
    NPCStep returnToBrian = new NPCStep(NpcID.BRIAN, new RSTile(3027, 3249, 0),
            new String[]{"I've returned with good news."});

    NPCStep returnToForester = new NPCStep(NpcID.JUNGLE_FORESTER, new RSTile(2861, 2942, 0),
            new String[]{"Good news, I have your sharpened axe!"}, sharpenedAxe);

    NPCStep returnToYanni = new NPCStep(NpcID.YANNI_SALIKA, new RSTile(2836, 2983, 0),
            new String[]{"Here's the red mahogany you asked for."}, redMahog);

    public void makePotLid() {
        if (softClay.check()) {
            cQuesterV2.status = "Making unfired Pot Lid";
            General.println("[Debug]: Making unfired pot lid");
            spinPotLid.execute();
            if (InterfaceUtil.getInterfaceKeyAndPress(270, "Pot lid")) {
                Timer.waitCondition(() -> Inventory.find(ItemId.POT_LID).length == 1, 3000, 4000);
            }
        }
        if (unfiredPotLid.check()) {
            cQuesterV2.status = "Fire Pot Lid";
            General.println("[Debug]: Making pot lid");
            firePotLid.execute();
            if (InterfaceUtil.getInterfaceKeyAndPress(270, "Pot lid")) {
                Timer.waitCondition(() -> Inventory.find(ItemId.POT_LID).length == 1, 3000, 4000);
            }
        }
        if (potLid.check()) {
            cQuesterV2.status = "Making airtight Pot";
            General.println("[Debug]: Making airtight Pot");
            usePotLidOnPot.execute();
        }
        if (potWithLid.check()) {
            cQuesterV2.status = "Going to Apothecary";
            General.println("[Debug]: Going to Apothecary");
            returnToApothecary.execute();
        }
    }

    public void setupConditions() {
        lamp1Empty = new VarbitRequirement(6225, 1);
        lamp2Empty = new VarbitRequirement(6226, 1);
        lamp3Empty = new VarbitRequirement(6227, 1);
        lamp4Empty = new VarbitRequirement(6228, 1);
        lamp5Empty = new VarbitRequirement(6229, 1);
        lamp6Empty = new VarbitRequirement(6230, 1);
        lamp7Empty = new VarbitRequirement(6231, 1);
        lamp8Empty = new VarbitRequirement(6232, 1);

        allEmpty = new VarbitRequirement(244, 255);

        lamp1Full = new VarbitRequirement(6233, 1);
        lamp2Full = new VarbitRequirement(6234, 1);
        lamp3Full = new VarbitRequirement(6235, 1);
        lamp4Full = new VarbitRequirement(6236, 1);
        lamp5Full = new VarbitRequirement(6237, 1);
        lamp6Full = new VarbitRequirement(6238, 1);
        lamp7Full = new VarbitRequirement(6239, 1);
        lamp8Full = new VarbitRequirement(6240, 1);

        allFull = new VarbitRequirement(6241, 255);

        //    slagilithNearby = new NpcCondition(NpcID.SLAGILITH);

        // petraNearby = new NpcCondition(NpcID.PETRA_FIYED);

        addedOrnaments = new VarbitRequirement(255, 1);
        addedDirectionals = new VarbitRequirement(254, 1);
        addedWeathervanePillar = new VarbitRequirement(253, 1);

        //   hasOrUsedDirectionals = new Conditions(LogicType.OR, addedDirectionals, directionals.alsoCheckBank(questBank));
        // hasOrUsedOrnament = new Conditions(LogicType.OR, addedOrnaments, ornament.alsoCheckBank(questBank));
        //    hasOrUsedWeathervanePillar = new Conditions(LogicType.OR, addedWeathervanePillar, weathervanePillar.alsoCheckBank(questBank));
    }

    NPCStep talkToYanni = new NPCStep(NpcID.YANNI_SALIKA, new RSTile(2836, 2983, 0), "Talk to Yanni Salika in Shilo Village.");
    NPCStep talkToJungleForester = new NPCStep(NpcID.JUNGLE_FORESTER, new RSTile(2861, 2942, 0), bluntAxe);
    NPCStep talkToBrian = new NPCStep(NpcID.BRIAN, new RSTile(3027, 3249, 0), "Talk to Brian in the Port Sarim axe shop.");
    NPCStep talkToAggie = new NPCStep(NpcID.AGGIE, new RSTile(3086, 3258, 0), "Talk to Aggie in Draynor Village.");
    NPCStep talkToJohanhus = new NPCStep(NpcID.JOHANHUS_ULSBRECHT, new RSTile(3171, 9619, 0), "Talk to Johanhus Ulsbrecht in the south of the H.A.M hideout.");
  /*  NPCStep talkToFred = new NPCStep(NpcID.FRED_THE_FARMER, new RSTile(3190, 3273, 0), "Talk to Fred the Farmer north of Lumbridge.");
    NPCStep talkToSeth = new NPCStep(NpcID.SETH_GROATS, new RSTile(3228, 3291, 0), "Talk to Seth Groats in the farm north east of Lumbridge, across the river.");
    NPCStep talkToHorvik = new NPCStep(NpcID.HORVIK, new RSTile(3229, 3437, 0), steelBars3);
    NPCStep talkToApoth = new NPCStep(NpcID.APOTHECARY, new RSTile(3196, 3404, 0), "Talk to the Apothecary in west Varrock.");
    NPCStep talkToTassie = new NPCStep(NpcID.TASSIE_SLIPCAST, new RSTile(3085, 3409, 0), "Talk to Tassie Slipcast in the Barbarian Village pottery building.");
    NPCStep talkToHammerspike = new NPCStep(NpcID.HAMMERSPIKE_STOUTBEARD, new RSTile(2968, 9811, 0), "Talk to Hammerspike Stoutbeard in the west cavern of the Dwarven Mine.");
    NPCStep talkToSanfew = new NPCStep(NpcID.SANFEW, new RSTile(2899, 3429, 1), "Talk to Sanfew upstairs in the Taverley herblore store.");


    public void setupSteps() {
        talkToYanni.addDialogStep("Is there anything else interesting to do around here?", "Ok, see you in a tick!");
        talkToJungleForester.addDialogStep("I'll get going then!", "I need to talk to you about red mahogany.");
        talkToJungleForester.addDialogStep("Okay, I'll take your axe to get it sharpened.");
        talkToBrian.addDialogStep("Do you sharpen axes?");
        talkToBrian.addDialogStepWithExclusion("Look, can you sharpen this cursed axe or what?", "Ok, ok, I'll do it! I'll go and see Aggie.");
        talkToBrian.addDialogStep("Ok, ok, I'll do it! I'll go and see Aggie.");

        talkToAggie.addDialogStep("Could I ask you about being a character witness?");
        talkToAggie.addDialogStep("Let me guess, you're going to ask me to do you a favour?");
        talkToAggie.addDialogStep("Oh, Ok, I'll see if I can find Jimmy.");

        //  goDownToJohanhus = new ObjectStep(NullObjectID.NULL_5492, new RSTile(3166, 3252, 0), "Enter the H.A.M hideout west of Lumbridge and talk to Johanhus Ulsbrecht in there.");
        talkToJohanhus.addDialogStep("I'm looking for Jimmy the Chisel.");
        talkToJohanhus.addDialogStep("And I suppose you need me to do you a favour?");
        talkToJohanhus.addDialogStep("Ok, Jimmy has to be worth more than a few scrawny chickens!");


        talkToFred.addDialogStep("I need to talk to you about Jimmy.");

        talkToSeth.addDialogStep("Oh, ok! I guess it's not that much further to Varrock!");
        talkToHorvik.addDialogStep("Hi, I need to talk to you about chicken cages!");
        talkToHorvik.addDialogStep("Ok, I guess one good turn deserves another.");
        talkToApoth.addDialogStep("Talk about One Small Favour.");
        talkToApoth.addDialogStep("Oh, ok, I guess it's not that far to the Barbarian Village.");
        talkToApoth.addDialogStep("I guess I can go to the Barbarian Village.");

        talkToTassie.addDialogStep("Ok, I'll deal with Hammerspike!");

        // goDownToHammerspike = new ObjectStep(ObjectID.TRAPDOOR_11867, new RSTile(3019, 3450, 0), "Go into the Dwarven Mine and talk to Hammerspike Stoutbeard in the west side.");
         talkToHammerspike.addDialogStep("Have you always been a gangster?");
        talkToHammerspike.addDialogStep("Ok, another favour...I think I can manage that.");
        talkToHammerspike.addSubSteps(goDownToHammerspike);

        //  goUpToSanfew = new ObjectStep(ObjectID.STAIRCASE_16671, new RSTile(2899, 3429, 0), "Talk to Sanfew upstairs in the Taverley herblore store.");
        talkToSanfew.addDialogStep("Are you taking any new initiates?");
        talkToSanfew.addDialogStepWithExclusion("Do you accept dwarves?", "A dwarf I know wants to become an initiate.");
        talkToSanfew.addDialogSteps("A dwarf I know wants to become an initiate.", "Yep, it's a deal.");

        useBowlOnCup = new DetailedQuestStep("Use a bowl of hot water on an empty cup.",
                hotWaterBowl.highlighted(), emptyCup.highlighted());
        useHerbsOnCup = new DetailedQuestStep("Use 2 guams, a marrentill and a harralander on the cup.",
                guam2.hideConditioned(new Conditions(LogicType.OR, guamTea, guam2Tea, guamMarrTea, guamHarrTea,
                        guamHarrMarrTea)).highlighted(),
                guam.hideConditioned(new Conditions(LogicType.OR, cupOfWater, marrTea, harrTea, guam2Tea, guam2MarrTea,
                        guam2HarrTea)).highlighted(),
                marrentill.hideConditioned(new Conditions(LogicType.OR, marrTea, harrMarrTea, guamMarrTea, guam2MarrTea,
                        guamHarrMarrTea)).highlighted(),
                harralander.hideConditioned(new Conditions(LogicType.OR, harrTea, harrMarrTea, guamHarrTea, guam2HarrTea,
                        guamHarrMarrTea)).highlighted(),
                cupOfWater.hideConditioned(new Conditions(LogicType.OR, guamTea, harrTea, marrTea, harrMarrTea, guamHarrTea,
                        guam2Tea, guam2MarrTea, guamMarrTea, guam2HarrTea, guamHarrMarrTea)).highlighted(),
                herbTeaMix.hideConditioned(new Conditions(LogicType.NOR, guamTea, harrTea, marrTea, harrMarrTea,
                        guamHarrTea, guam2Tea, guam2MarrTea, guamMarrTea, guam2HarrTea, guamHarrMarrTea)).highlighted());
        makeGuthixRest = new DetailedQuestStep("Make Guthix Rest by using a bowl of hot water on an empty tea cup, then using 2 guams, a marrentill and a harralander on it.", emptyCup, hotWaterBowl, guam2, marrentill, harralander);
        makeGuthixRest.addSubSteps(useBowlOnCup, useHerbsOnCup);
        NPCStep   talkToBleemadge = new NPCStep(NpcID.CAPTAIN_BLEEMADGE, new RSTile(2847, 3498, 0), "Talk to Captain Bleemadge on White Wolf Mountain.", guthixRest);
        ((NPCStep) talkToBleemadge).addAlternateNpcs(NpcID.CAPTAIN_BLEEMADGE_10461, NpcID.CAPTAIN_BLEEMADGE_10462,
                NpcID.CAPTAIN_BLEEMADGE_10463, NpcID.CAPTAIN_BLEEMADGE_10464, NpcID.CAPTAIN_BLEEMADGE_10465,
                NpcID.CAPTAIN_BLEEMADGE_10466);
        talkToBleemadge.addDialogStep("I have a special tea here for you from Sanfew!");

        NPCStep  talkToBleemadgeNoTea = new NPCStep(NpcID.CAPTAIN_BLEEMADGE, new RSTile(2847, 3498, 0), "Talk to Captain Bleemadge on White Wolf Mountain.");
        ((NPCStep) talkToBleemadgeNoTea).addAlternateNpcs(NpcID.CAPTAIN_BLEEMADGE_10461, NpcID.CAPTAIN_BLEEMADGE_10462,
                NpcID.CAPTAIN_BLEEMADGE_10463, NpcID.CAPTAIN_BLEEMADGE_10464, NpcID.CAPTAIN_BLEEMADGE_10465,
                NpcID.CAPTAIN_BLEEMADGE_10466);
        talkToBleemadgeNoTea.addDialogStep("How was that tea?");
        talkToBleemadgeNoTea.addDialogStep("Ok, I'll go and get you some T.R.A.S.H.");

        talkToBleemadge.addSubSteps(talkToBleemadgeNoTea);

        NPCStep  talkToArhein = new NPCStep(NpcID.ARHEIN, new RSTile(2804, 3432, 0), "Talk to Arhein in Catherby.");
        talkToArhein.addDialogStep("I need to talk T.R.A.S.H to you.");
        talkToArhein.addDialogStep("Yes, Ok, I'll do it!");

        NPCStep  talkToPhantuwti = new NPCStep(NpcID.PHANTUWTI_FANSTUWI_FARSIGHT, new RSTile(2702, 3473, 0), "Talk to Phantuwti in the south west house of Seers' Village.");
        talkToPhantuwti.addDialogStep("Hi, can you give me a weather forecast?");
        talkToPhantuwti.addDialogStep("What can I do to help?");
        talkToPhantuwti.addDialogStep("Yes, Ok, I'll do it.");

        enterGoblinCave = new ObjectStep(ObjectID.CAVE_ENTRANCE, new RSTile(2624, 3393, 0), "Enter the cave south east of the Fishing Guild.");
        searchWall = new ObjectStep(ObjectID.SCULPTURE, new RSTile(2621, 9835, 0), "Right-click search the sculpture in the wall in the north east corner of the cave.");

        talkToCromperty = new NPCStep(NpcID.WIZARD_CROMPERTY, new RSTile(2684, 3323, 0), "Talk to Wizard Cromperty in north east Ardougne.");
        talkToCromperty.addDialogStep("I need to talk to you about a girl stuck in some rock!");
        talkToCromperty.addDialogStep("Oh! Ok, one more 'small favour' isn't going to kill me...I hope!");

        talkToTindel = new NPCStep(NpcID.TINDEL_MARCHANT, new RSTile(2678, 3153, 0), "Talk to Tindel Marchant in Port Khazard.");
        talkToTindel.addDialogSteps("Wizard Cromperty sent me to get some iron oxide.", "Ask about iron oxide.", "Okay, I'll do it!");

        talkToRantz = new NPCStep(NpcID.RANTZ, new RSTile(2631, 2969, 0), "Talk to Rantz in Feldip Hills.");
        talkToRantz.addDialogStep("I need to talk to you about a mattress.");
        talkToRantz.addDialogStep("Ok, I'll see what I can do.");

        talkToGnormadium = new NPCStep(NpcID.GNORMADIUM_AVLAFRIM, new RSTile(2542, 2968, 0), "Talk to Gnormadium Avlafrim west of Rantz.");
        talkToGnormadium.addDialogStep("Rantz said I should help you finish this project.");
        talkToGnormadium.addDialogStep("Yes, I'll take a look at them.");

        take1 = new ObjectStep(NullObjectID.NULL_5820, new RSTile(2554, 2974, 0), "Take a jade from the north east landing light.");
        take2 = new ObjectStep(NullObjectID.NULL_5822, new RSTile(2551, 2974, 0), "Take a red topaz from the landing light.");
        take3 = new ObjectStep(NullObjectID.NULL_5821, new RSTile(2548, 2974, 0), "Take an opal from the landing light.");
        take4 = new ObjectStep(NullObjectID.NULL_5823, new RSTile(2545, 2974, 0), "Take a sapphire from the landing light.");

        take5 = new ObjectStep(NullObjectID.NULL_5820, new RSTile(2554, 2969, 0), "Take a jade from the landing light.");
        take6 = new ObjectStep(NullObjectID.NULL_5822, new RSTile(2551, 2969, 0), "Take a red topaz from the landing light.");
        take7 = new ObjectStep(NullObjectID.NULL_5821, new RSTile(2548, 2969, 0), "Take an opal from the landing light.");
        take8 = new ObjectStep(NullObjectID.NULL_5823, new RSTile(2545, 2969, 0), "Take a sapphire from the landing light.");

        put1 = new ObjectStep(NullObjectID.NULL_5820, new RSTile(2554, 2974, 0), "Put a jade from the north east landing light.", jade);
        put1.addIcon(ItemID.JADE);
        put2 = new ObjectStep(NullObjectID.NULL_5822, new RSTile(2551, 2974, 0), "Put a red topaz from the landing light.", redTopaz);
        put2.addIcon(ItemID.RED_TOPAZ);
        put3 = new ObjectStep(NullObjectID.NULL_5821, new RSTile(2548, 2974, 0), "Put an opal from the landing light.", opal);
        put3.addIcon(ItemID.OPAL);
        put4 = new ObjectStep(NullObjectID.NULL_5823, new RSTile(2545, 2974, 0), "Put a sapphire from the landing light.", sapphire);
        put4.addIcon(ItemID.SAPPHIRE);

        put5 = new ObjectStep(NullObjectID.NULL_5820, new RSTile(2554, 2969, 0), "Put a jade from the landing light.", jade);
        put5.addIcon(ItemID.JADE);
        put6 = new ObjectStep(NullObjectID.NULL_5822, new RSTile(2551, 2969, 0), "Put a red topaz from the landing light.", redTopaz);
        put6.addIcon(ItemID.RED_TOPAZ);
        put7 = new ObjectStep(NullObjectID.NULL_5821, new RSTile(2548, 2969, 0), "Put an opal from the landing light.", opal);
        put7.addIcon(ItemID.OPAL);
        put8 = new ObjectStep(NullObjectID.NULL_5823, new RSTile(2545, 2969, 0), "Put a sapphire from the landing light.", sapphire);
        put8.addIcon(ItemID.SAPPHIRE);

        cutJade = new DetailedQuestStep("Cut the uncut jade.", chisel, uncutJade);
        cutSaph = new DetailedQuestStep("Cut the uncut sapphire.", chisel, uncutSapphire);
        cutOpal = new DetailedQuestStep("Cut the uncut opal.", chisel, uncutOpal);
        cutTopaz = new DetailedQuestStep("Cut the uncut red topaz.", chisel, uncutRedTopaz);

        fixAllLamps = new DetailedQuestStep("Take the gems out of each of the gnome lamps, cut them, then put back in the cut gem.");
        fixAllLamps.addSubSteps(cutJade, cutOpal, cutSaph, cutTopaz, put1, put2, put3, put4, put5, put6, put7, put8, take1, take2, take3, take4, take5, take6, take7, take8);

        talkToGnormadiumAgain = new NPCStep(NpcID.GNORMADIUM_AVLAFRIM, new RSTile(2542, 2968, 0), "Talk to Gnormadium Avlafrim again.");
        talkToGnormadiumAgain.addDialogStep("I've fixed all the lights!");

        returnToRantz = new NPCStep(NpcID.RANTZ, new RSTile(2631, 2975, 0), "Return to Rantz in Feldip Hills.", stodgyMattress);
        returnToRantz.addDialogStep("Ok, I've helped that Gnome, he shouldn't bother you anymore.");
        returnToTindel = new NPCStep(NpcID.TINDEL_MARCHANT, new RSTile(2678, 3153, 0), "Return to Tindel Marchant in Port Khazard.", mattress);
        returnToTindel.addDialogStep("I have the mattress.");

        returnToCromperty = new NPCStep(NpcID.WIZARD_CROMPERTY, new RSTile(2684, 3323, 0), "Return to Wizard Cromperty in north east Ardougne.", ironOxide);
        returnToCromperty.addDialogStep("I have that iron oxide you asked for!");

        getPigeonCages = new DetailedQuestStep(new RSTile(2618, 3325, 0), "Get 5 pigeon cages from behind Jerico's house in central East Ardougne.", pigeonCages5);

        enterGoblinCaveAgain = new ObjectStep(ObjectID.CAVE_ENTRANCE, new RSTile(2624, 3393, 0), "Enter the cave south east of the Fishing Guild. Be prepared to fight the Slagilith (level 92).", pigeonCages5, animateRockScroll);
        standNextToSculpture = new DetailedQuestStep(new RSTile(2616, 9835, 0), "Use the animate rock scroll next to the sculpture in the north east cavern.", animateRockScroll);
        readScroll = new DetailedQuestStep("Read the animate rock scroll.", animateRockScrollHighlight);

        standNextToSculpture.addSubSteps(readScroll);

        killSlagilith = new NPCStep(NpcID.SLAGILITH, new RSTile(2617, 9837, 0), "Kill the Slagilith.");
        readScrollAgain = new DetailedQuestStep("Read the animate rock scroll", animateRockScrollHighlight);
        talkToPetra = new NPCStep(NpcID.PETRA_FIYED, new RSTile(2617, 9837, 0), "Talk to Petra Fiyed.");

        returnToPhantuwti = new NPCStep(NpcID.PHANTUWTI_FANSTUWI_FARSIGHT, new RSTile(2702, 3473, 0), "Return to Phantuwti in the south west house of Seers' Village.");
        returnToPhantuwti.addDialogStep("I've released Petra, she should have returned.");
        returnToPhantuwti.addDialogStep("I'll run you through if you don't give me that weather report.");
        returnToPhantuwti.addDialogStepWithExclusion("Why can't you get a clear picture?", "I'll run you through if you don't give me that weather report.");

        returnToPhantuwti2 = new NPCStep(NpcID.PHANTUWTI_FANSTUWI_FARSIGHT, new RSTile(2702, 3473, 0), "Return to Phantuwti in the south west house of Seers' Village.");
        returnToPhantuwti2.addDialogStep("I'll run you through if you don't give me that weather report.");
        returnToPhantuwti2.addDialogStepWithExclusion("Why can't you get a clear picture?", "I'll run you through if you don't give me that weather report.");
        returnToPhantuwti2.addDialogStep("Which special Seers tools do you mean?");
        returnToPhantuwti2.addDialogStep("What do you mean, 'special combination of items'?");

        returnToPhantuwti.addSubSteps(returnToPhantuwti2);

        goUpLadder = new ObjectStep(ObjectID.LADDER_25941, new RSTile(2699, 3476, 0), "Go up the ladder nearby.");
        goUpToRoof = new ObjectStep(ObjectID.LADDER_26118, new RSTile(2715, 3472, 1), "Go up to the roof.");
        searchVane = new ObjectStep(NullObjectID.NULL_5811, new RSTile(2702, 3476, 3), "Right-click search the weathervane on top of the Seers' building.");
        searchVane.addSubSteps(goUpLadder, goUpToRoof);
        useHammerOnVane = new ObjectStep(NullObjectID.NULL_5811, new RSTile(2702, 3476, 3), "Use a hammer on the weathervane.", hammerHighlight);
        useHammerOnVane.addIcon(ItemID.HAMMER);
        searchVaneAgain = new ObjectStep(NullObjectID.NULL_5811, new RSTile(2702, 3476, 3), "Right-click search the weathervane on top of the Seers' building again.");

        goDownFromRoof = new ObjectStep(ObjectID.TRAPDOOR_26119, new RSTile(2715, 3472, 3), "Climb down from the roof.");
        goDownLadderToSeers = new ObjectStep(ObjectID.LADDER_25939, new RSTile(2715, 3470, 1), "Repair the vane parts on the anvil in north Seers' Village.");
        useVane123OnAnvil = new ObjectStep(ObjectID.ANVIL_2097, new RSTile(2712, 3495, 0), "Repair the vane parts on an anvil. You can find one in the north of Seers' Village.", brokenVane1, brokenVane2, brokenVane3, hammer, steelBar, ironBar, bronzeBar);
        useVane12OnAnvil = new ObjectStep(ObjectID.ANVIL_2097, new RSTile(2712, 3495, 0), "Repair the vane parts on an anvil. You can find one in the north of Seers' Village.", brokenVane1, brokenVane2, hammer, steelBar, bronzeBar);
        useVane13OnAnvil = new ObjectStep(ObjectID.ANVIL_2097, new RSTile(2712, 3495, 0), "Repair the vane parts on an anvil. You can find one in the north of Seers' Village.", brokenVane1, brokenVane3, hammer, ironBar, bronzeBar);
        useVane23OnAnvil = new ObjectStep(ObjectID.ANVIL_2097, new RSTile(2712, 3495, 0), "Repair the vane parts on an anvil. You can find one in the north of Seers' Village.", brokenVane2, brokenVane3, hammer, ironBar, bronzeBar);
        useVane1OnAnvil = new ObjectStep(ObjectID.ANVIL_2097, new RSTile(2712, 3495, 0), "Repair the vane parts on an anvil. You can find one in the north of Seers' Village.", brokenVane1, hammer, steelBar);
        useVane2OnAnvil = new ObjectStep(ObjectID.ANVIL_2097, new RSTile(2712, 3495, 0), "Repair the vane parts on an anvil. You can find one in the north of Seers' Village.", brokenVane2, hammer, bronzeBar);
        useVane3OnAnvil = new ObjectStep(ObjectID.ANVIL_2097, new RSTile(2712, 3495, 0), "Repair the vane parts on an anvil. You can find one in the north of Seers' Village.", brokenVane3, hammer, ironBar);
        useVane123OnAnvil.addSubSteps(goDownFromRoof, goDownLadderToSeers, useVane1OnAnvil, useVane2OnAnvil, useVane3OnAnvil, useVane12OnAnvil, useVane13OnAnvil, useVane23OnAnvil);

        goBackUpLadder = new ObjectStep(ObjectID.LADDER_25941, new RSTile(2699, 3476, 0), "Go back up to the Seers' roof and fix the vane.");
        goBackUpToRoof = new ObjectStep(ObjectID.LADDER_26118, new RSTile(2715, 3472, 1), "Go back up to the Seers' roof and fix the vane.");
        useVane1 = new ObjectStep(NullObjectID.NULL_5811, new RSTile(2702, 3476, 3), "Use the ornament on the weathervane.", ornament);
        useVane2 = new ObjectStep(NullObjectID.NULL_5811, new RSTile(2702, 3476, 3), "Use the directionals on the weathervane.", directionals);
        useVane3 = new ObjectStep(NullObjectID.NULL_5811, new RSTile(2702, 3476, 3), "Use the weathervane pillar on the weathervane.", weathervanePillar);
        goBackUpLadder.addSubSteps(goBackUpToRoof, useVane1, useVane2, useVane3);

        goFromRoofToPhantuwti = new ObjectStep(ObjectID.TRAPDOOR_26119, new RSTile(2715, 3472, 3), "Return to Phantuwti.");
        goDownLadderToPhantuwti = new ObjectStep(ObjectID.LADDER_25940, new RSTile(2699, 3476, 1), "Return to Phantuwti.");
        finishWithPhantuwti = new NPCStep(NpcID.PHANTUWTI_FANSTUWI_FARSIGHT, new RSTile(2702, 3473, 0), "Return to Phantuwti in the south west house of Seers' Village.");
        finishWithPhantuwti.addSubSteps(goFromRoofToPhantuwti, goDownLadderToPhantuwti);
        finishWithPhantuwti.addDialogStep("I've fixed the weather vane!");

        returnToArhein = new NPCStep(NpcID.ARHEIN, new RSTile(2804, 3432, 0), "Talk to Arhein in Catherby.", weatherReport);
        returnToArhein.addDialogStep("I have the weather report for you.");

        returnToBleemadge = new NPCStep(NpcID.CAPTAIN_BLEEMADGE, new RSTile(2847, 3498, 0), "Talk to Captain Bleemadge on White Wolf Mountain.");
        returnToBleemadge.addDialogStep("Hey there, did you get your T.R.A.S.H?");
        ((NPCStep) returnToBleemadge).addAlternateNpcs(NpcID.CAPTAIN_BLEEMADGE_10461, NpcID.CAPTAIN_BLEEMADGE_10462,
                NpcID.CAPTAIN_BLEEMADGE_10463, NpcID.CAPTAIN_BLEEMADGE_10464, NpcID.CAPTAIN_BLEEMADGE_10465,
                NpcID.CAPTAIN_BLEEMADGE_10466);

        returnToSanfew = new NPCStep("Sanfew", new RSTile(2899, 3429, 1));
        returnToSanfew.addDialogStep("Hi there, the Gnome Pilot has agreed to take you to see the ogres!");

        goDownToHammerspikeAgain = new ObjectStep(11867, new RSTile(3019, 3450, 0),
                "Open");//trapdoor
        returnToHammerspike = new NPCStep(NpcID.HAMMERSPIKE_STOUTBEARD, new RSTile(2968, 9811, 0), "Return to Hammerspike Stoutbeard in the west cavern of the Dwarven Mine.");
        // returnToHammerspike.addSubSteps(goDownToHammerspikeAgain);

        killGangMembers = new NPCStep(NpcID.DWARF_GANG_MEMBER, new RSTile(2968, 9811, 0), "Kill dwarf gang members until Hammerspike gives in.");
        talkToHammerspikeFinal = new NPCStep(NpcID.HAMMERSPIKE_STOUTBEARD, new RSTile(2968, 9811, 0), "Return to Hammerspike Stoutbeard in the west cavern of the Dwarven Mine.");
        returnToTassie = new NPCStep(NpcID.TASSIE_SLIPCAST, new RSTile(3085, 3409, 0), "Return to Tassie Slipcast in the Barbarian Village pottery building.");


        returnToApothecary = new NPCStep(NpcID.APOTHECARY, new RSTile(3196, 3404, 0), potWithLid);
        returnToApothecary.addDialogStep("Talk about One Small Favour.");

        returnToHorvik = new NPCStep(NpcID.HORVIK, new RSTile(3229, 3437, 0), pigeonCages5, breathingSalts, herbalTincture);
        returnToHorvik.addDialogStep("I have the tincture and the breathing salts.");
        talkToHorvikFinal = new NPCStep(NpcID.HORVIK, new RSTile(3229, 3437, 0), pigeonCages5);
        talkToHorvikFinal.addDialogStep("I have the five pigeon cages you asked for!");
        returnToSeth = new NPCStep(NpcID.SETH_GROATS, new RSTile(3228, 3291, 0), chickenCages5);
        //returnDownToJohnahus = new ObjectStep(5492, new RSTile(3166, 3252, 0), "Enter the H.A.M hideout west of Lumbridge and talk to Johanhus Ulsbrecht in there.");
        returnToJohnahus = new NPCStep(NpcID.JOHANHUS_ULSBRECHT, new RSTile(3171, 9619, 0), "Return to Johanhus Ulsbrecht in the south of the H.A.M hideout.");
        returnToJohnahus.addDialogStep("You're in luck, I've managed to swing that chicken deal for you.");
        returnToAggie = new NPCStep(NpcID.AGGIE, new RSTile(3086, 3258, 0), "Return to Aggie in Draynor Village.");
        returnToAggie.addDialogStep("Good news! Jimmy has been released!");
        returnToBrian = new NPCStep(NpcID.BRIAN, new RSTile(3027, 3249, 0),
                "Return to Brian in the Port Sarim axe shop.");
        returnToBrian.addDialogStep("I've returned with good news.");

        returnToForester.addDialogStep("Good news, I have your sharpened axe!");
        returnToYanni.addDialogStep("Here's the red mahogany you asked for.");
    }
*/
    public void finishQuest() {
        returnToYanni.execute();
        for (int i = 0; i < 4; i++) {
            NPCInteraction.waitForConversationWindow();

            if (NPCInteraction.isConversationWindowUp())
                NPCInteraction.handleConversation();

        }
    }

    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return Utils.getVarBitValue(235) == 0;
    }

    @Override
    public void execute() {
        setupConditions();

       /* cQuesterV2.status = "Returning to arhein";
        returnToArhein.execute();
        cQuesterV2.status = "Returning to Bleemadge";
             returnToBleemadge.execute();
        cQuesterV2.status = "Returning to Sanfew";
         returnToSanfew.execute();
        cQuesterV2.status = "Returning to hammerspike";
        returnToHammerspike.execute();
        cQuesterV2.status = "Killing gang members";
         killGangMembers.setInteractionString("Attack");
            killGangMembers.execute();*/
        cQuesterV2.status = "Returning to Hammerspike final";
        talkToHammerspikeFinal.execute();
        cQuesterV2.status = "Returning to Tassie";
        returnToTassie.execute();
        makePotLid();
        cQuesterV2.status = "Returning to Horvik";
        returnToHorvik.execute();
        talkToHorvikFinal.execute();
        returnToSeth.execute();
        returnToJohnahus.execute();
        returnToAggie.execute();
        returnToBrian.execute();
        returnToForester.execute();

        finishQuest();
        //
        //
    }

    @Override
    public String questName() {
        return "One small Favour";
    }

    @Override
    public boolean checkRequirements() {
        return false;
    }

}
