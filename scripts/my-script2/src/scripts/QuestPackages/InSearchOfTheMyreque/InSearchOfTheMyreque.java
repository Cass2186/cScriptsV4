package scripts.QuestPackages.InSearchOfTheMyreque;

import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import scripts.ItemID;
import scripts.NpcID;
import scripts.ObjectID;
import scripts.QuestPackages.TheGolem.TheGolem;
import scripts.QuestSteps.*;
import scripts.Requirements.*;
import scripts.Requirements.Items.ItemCollections;
import scripts.Requirements.Items.ItemRequirements;
import scripts.Requirements.Util.ConditionalStep;
import scripts.Requirements.Util.Conditions;
import scripts.Requirements.Util.LogicType;
import scripts.Tasks.Priority;
import scripts.cQuesterV2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InSearchOfTheMyreque implements QuestTask {

    private static InSearchOfTheMyreque quest;

    public static InSearchOfTheMyreque get() {
        return quest == null ? quest = new InSearchOfTheMyreque() : quest;
    }

    //Items Required
    ItemRequirement combatGear, steelLong, steelSword2, steelMace, steelWarhammer, steeldagger, steelNails225, druidPouch5, hammer, plank6,
            coins10OrCharos, plank3, plank2, plank1, steelNails75, steelNails150;

    //Items Recommended
    ItemRequirement morttonTeleport;

    Requirement hasEnoughPouch, repairedBridge1, repairedBridge2, repairedBridge3, onBridge, onEntranceIsland, onQuestion1, onQuestion2,
            onQuestion3, onQuestion4, onQuestion5, onQuestion6, inCaves, inMyrequeCave, talkedToHarold, talkedToRadigad, talkedToSani, talkedToPolmafi,
            talkedToIvan;

    QuestStep talkToVanstrom, fillDruidPouch, talkToCyreg, boardBoat, climbTree, climbTree2, climbTree3, climbTree4, repairBridge1, repairBridge2, repairBridge3,
            talkToCurpile, talkToCurpile1, talkToCurpile2, talkToCurpile3, talkToCurpile4, talkToCurpile5, talkToCurpile6, enterDoors, enterCave, talkToMembers,
            talkToVeliaf, talkToHarold, talkToRadigad, talkToSani, talkToPolmafi, talkToIvan, talkToVeliafAgain, talkToVeliafForCutscene, killHellhound, talkToVeliafToLeave, leaveCave, goUpToCanifis,
            talkToStranger, climbTreeHellhound, enterCaveHellhound, enterDoorsHellhound, climbTreeLeave, enterCaveLeave, enterDoorsLeave;

    //RSArea()s
    RSArea entranceIsland, bridge, caves, myrequeCave;


    public Map<Integer, QuestStep> loadSteps() {
        loadRSArea();
        setupItemRequirements();
        setupConditions();
        setupSteps();
        Map<Integer, QuestStep> steps = new HashMap<>();

        steps.put(0, talkToVanstrom);

        ConditionalStep goTalkToCyreg = new ConditionalStep( fillDruidPouch);
        goTalkToCyreg.addStep(hasEnoughPouch, talkToCyreg);
        steps.put(5, goTalkToCyreg);
        steps.put(10, goTalkToCyreg);
        steps.put(15, goTalkToCyreg);

        steps.put(20, boardBoat);

        ConditionalStep goTalkToCurpile = new ConditionalStep( climbTree);
        goTalkToCurpile.addStep(new Conditions(onEntranceIsland, onQuestion6), talkToCurpile6);
        goTalkToCurpile.addStep(new Conditions(onEntranceIsland, onQuestion5), talkToCurpile5);
        goTalkToCurpile.addStep(new Conditions(onEntranceIsland, onQuestion4), talkToCurpile4);
        goTalkToCurpile.addStep(new Conditions(onEntranceIsland, onQuestion3), talkToCurpile3);
        goTalkToCurpile.addStep(new Conditions(onEntranceIsland, onQuestion2), talkToCurpile2);
        goTalkToCurpile.addStep(new Conditions(onEntranceIsland, onQuestion1), talkToCurpile1);
        goTalkToCurpile.addStep(new Conditions(onEntranceIsland), talkToCurpile);
        goTalkToCurpile.addStep(new Conditions(onBridge, repairedBridge2), repairBridge3);
        goTalkToCurpile.addStep(new Conditions(onBridge, repairedBridge1), repairBridge2);
        goTalkToCurpile.addStep(onBridge, repairBridge1);
        goTalkToCurpile.addStep(repairedBridge3, climbTree4);
        goTalkToCurpile.addStep(repairedBridge2, climbTree3);
        goTalkToCurpile.addStep(repairedBridge1, climbTree2);
        steps.put(25, goTalkToCurpile);
        steps.put(52, goTalkToCurpile);

        ConditionalStep goTalkToVeliaf = new ConditionalStep( climbTree4);
        goTalkToVeliaf.addStep(inMyrequeCave, talkToVeliaf);
        goTalkToVeliaf.addStep(inCaves, enterCave);
        goTalkToVeliaf.addStep(onEntranceIsland, enterDoors);
        steps.put(55, goTalkToVeliaf);
        steps.put(60, goTalkToVeliaf);

        ConditionalStep talkToCrew = new ConditionalStep( climbTree4);
        talkToCrew.addStep(new Conditions(inMyrequeCave, talkedToHarold, talkedToRadigad, talkedToSani, talkedToPolmafi, talkedToIvan), talkToVeliafAgain);
        talkToCrew.addStep(new Conditions(inMyrequeCave, talkedToHarold, talkedToRadigad, talkedToSani, talkedToPolmafi), talkToIvan);
        talkToCrew.addStep(new Conditions(inMyrequeCave, talkedToHarold, talkedToRadigad, talkedToSani), talkToPolmafi);
        talkToCrew.addStep(new Conditions(inMyrequeCave, talkedToHarold, talkedToRadigad), talkToSani);
        talkToCrew.addStep(new Conditions(inMyrequeCave, talkedToHarold), talkToRadigad);
        talkToCrew.addStep(inMyrequeCave, talkToHarold);
        talkToCrew.addStep(inCaves, enterCave);
        talkToCrew.addStep(onEntranceIsland, enterDoors);
        steps.put(65, talkToCrew);

        ConditionalStep goTalkToVeliafAgain = new ConditionalStep( climbTree4);
        goTalkToVeliafAgain.addStep(inMyrequeCave, talkToVeliafForCutscene);
        goTalkToVeliafAgain.addStep(inCaves, enterCave);
        goTalkToVeliafAgain.addStep(onEntranceIsland, enterDoors);
        steps.put(67, goTalkToVeliafAgain);
        steps.put(70, goTalkToVeliafAgain);

        ConditionalStep goKillSkeletonHellhound = new ConditionalStep( climbTreeHellhound);
        goKillSkeletonHellhound.addStep(inMyrequeCave, killHellhound);
        goKillSkeletonHellhound.addStep(inCaves, enterCaveHellhound);
        goKillSkeletonHellhound.addStep(onEntranceIsland, enterDoorsHellhound);
        steps.put(71, goKillSkeletonHellhound);
        steps.put(72, goKillSkeletonHellhound);
        steps.put(73, goKillSkeletonHellhound);
        steps.put(74, goKillSkeletonHellhound);
        steps.put(75, goKillSkeletonHellhound);
        steps.put(76, goKillSkeletonHellhound);
        steps.put(77, goKillSkeletonHellhound);
        steps.put(78, goKillSkeletonHellhound);
        steps.put(79, goKillSkeletonHellhound);
        steps.put(80, goKillSkeletonHellhound);

        ConditionalStep goLearnExit = new ConditionalStep( climbTreeLeave);
        goLearnExit.addStep(inMyrequeCave, talkToVeliafToLeave);
        goLearnExit.addStep(inCaves, enterCaveLeave);
        goLearnExit.addStep(onEntranceIsland, enterDoorsLeave);
        steps.put(85, goLearnExit);

        ConditionalStep tryLeavingExit = new ConditionalStep( climbTreeLeave);
        tryLeavingExit.addStep(inMyrequeCave, leaveCave);
        tryLeavingExit.addStep(inCaves, goUpToCanifis);
        tryLeavingExit.addStep(onEntranceIsland, enterDoorsLeave);
        steps.put(90, tryLeavingExit);
        steps.put(95, tryLeavingExit);

        ConditionalStep finishingOff = new ConditionalStep( talkToStranger);
        finishingOff.addStep(inMyrequeCave, leaveCave);
        finishingOff.addStep(inCaves, goUpToCanifis);
        steps.put(97, finishingOff);
        steps.put(100, finishingOff);
        steps.put(105, finishingOff);
        return steps;
    }

    public void setupItemRequirements() {
        steelLong = new ItemRequirement("Steel longsword", ItemID.STEEL_LONGSWORD);
        steelSword2 = new ItemRequirement("Steel sword", ItemID.STEEL_SWORD, 2);
        steelMace = new ItemRequirement("Steel mace", ItemID.STEEL_MACE);
        steelWarhammer = new ItemRequirement("Steel warhammer", ItemID.STEEL_WARHAMMER);
        steeldagger = new ItemRequirement("Steel dagger", ItemID.STEEL_DAGGER);
        steelNails225 = new ItemRequirement("Steel nails", ItemID.STEEL_NAILS, 225);
        druidPouch5 = new ItemRequirement("Charges in a druid pouch", ItemID.DRUID_POUCH_2958, 5);
        hammer = new ItemRequirement("Hammer", ItemID.HAMMER);
        plank6 = new ItemRequirement("Plank", ItemID.PLANK, 6);
        plank3 = new ItemRequirement("Plank", ItemID.PLANK, 3);
        plank2 = new ItemRequirement("Plank", ItemID.PLANK, 2);
        plank1 = new ItemRequirement("Plank", ItemID.PLANK);
        steelNails150 = new ItemRequirement("Steel nails", ItemID.STEEL_NAILS, 150);
        steelNails75 = new ItemRequirement("Steel nails", ItemID.STEEL_NAILS, 75);
        coins10OrCharos = new ItemRequirements(LogicType.OR, "10 coins or a Ring of Charos (a)",
                new ItemRequirement("Ring of Charos (a)", ItemID.RING_OF_CHAROSA),
                new ItemRequirement("Coins", ItemID.COINS_995, 10));
        combatGear = new ItemRequirement("Combat gear", -1, -1);

        morttonTeleport = new ItemRequirement("Teleport to Mort'ton, such as minigame teleport or Barrows Teleport", ItemID.MORTTON_TELEPORT);

    }

    public void loadRSArea() {
        bridge = new RSArea(new RSTile(3503, 3426, 0), new RSTile(3503, 3429, 0));
        entranceIsland = new RSArea(new RSTile(3480, 3430, 0), new RSTile(3513, 3464, 0));
        caves = new RSArea(new RSTile(3450, 9792, 0), new RSTile(3504, 9847, 2));
        myrequeCave = new RSArea(new RSTile(3502, 9829, 0), new RSTile(3515, 9846, 0));
    }

    public void setupConditions()
    {
        hasEnoughPouch = druidPouch5;
        repairedBridge1 = new VarbitRequirement(176, 1);
        repairedBridge2 = new VarbitRequirement(177, 1);
        repairedBridge3 = new VarbitRequirement(178, 1);

        onBridge = new AreaRequirement(bridge);
        onEntranceIsland = new AreaRequirement(entranceIsland);
        inCaves = new AreaRequirement(caves);
        inMyrequeCave = new AreaRequirement(myrequeCave);

        onQuestion1 = new WidgetTextRequirement(219, 1, 0, "female");
        onQuestion2 = new WidgetTextRequirement(219, 1, 0, "youngest");
        onQuestion3 = new WidgetTextRequirement(219, 1, 0, "leader");
        onQuestion4 = new WidgetTextRequirement(219, 1, 0, "boatman");
        onQuestion5 = new WidgetTextRequirement(219, 1, 0, "vampyre");
        onQuestion6 = new WidgetTextRequirement(219, 1, 0, "scholar");

      /*  talkedToSani = new VarbitRequirement(2496, true, 0);
        talkedToHarold = new VarbitRequirement(2496, true, 1);
        talkedToRadigad = new VarbitRequirement(2496, true, 2);
        talkedToPolmafi = new VarbitRequirement(2496, true, 3);
        talkedToIvan = new VarbitRequirement(2496, true, 4);*/
    }

    public void setupSteps() {
       /* talkToVanstrom = new NPCStep( NpcID.VANSTROM_KLAUSE_5056, new RSTile(3503, 3477, 0),
                "Talk to Vanstrom Klause in the Canifis pub.");
        talkToVanstrom.addDialogStep("Yes.");
        fillDruidPouch = new DetailedQuestStep("Fill a druid pouch with at least 5 mort myre items. Try to have more in case a ghast hits you.",
                druidPouch5);
        fillDruidPouch.addDialogStep("I'd better be off.");
        talkToCyreg = new NPCStep( NpcID.CYREG_PADDLEHORN, new RSTile(3522, 3284, 0),
                druidPouch5, steelLong, steelSword2, steeldagger, steelMace, steelNails225, steelWarhammer, plank6, hammer, coins10OrCharos);
        talkToCyreg.addDialogStep("I'd better be off.",
                "Well, I guess they'll just die without weapons.", "Resourceful enough to get their own steel weapons?",
                "If you don't tell me, their deaths are on your head!", "What kind of a man are you to say that you don't care?",
                "Here are some planks for you.");
        boardBoat = new ObjectStep(6969, new RSTile(3524, 3285, 0), "Board the swamp boaty in Mort'ton.",
                steelLong, steelSword2, steeldagger, steelMace, steelNails225, steelWarhammer, plank3, hammer, coins10OrCharos);
        boardBoat.addDialogStep("Yes. I'll pay the ten gold.");
        climbTree = new ObjectStep(5003, new RSTile(3502, 3426, 0),
                "Climb the tree to the north of the boat.", steelLong, steelSword2, steeldagger, steelMace, steelNails225, steelWarhammer, plank3, hammer);
        climbTree2 = new ObjectStep(5003, new RSTile(3502, 3426, 0),
                "Climb the tree to the north of the boat.", steelLong, steelSword2, steeldagger, steelMace, steelNails150, steelWarhammer, plank2, hammer);
        climbTree3 = new ObjectStep(5003, new RSTile(3502, 3426, 0),
                "Climb the tree to the north of the boat.", steelLong, steelSword2, steeldagger, steelMace, steelNails75, steelWarhammer, plank1, hammer);
        climbTree4 = new ObjectStep(5003, new RSTile(3502, 3426, 0),
                "Climb the tree to the north of the boat.", steelLong, steelSword2, steeldagger, steelMace, steelWarhammer);
       // climbTree.addSubSteps(climbTree2, climbTree3, climbTree4);

        repairBridge1 = new ObjectStep(26245, new RSTile(3502, 3428, 0), "Repair the bridge.", steelNails225, plank3, hammer);
        repairBridge2 = new ObjectStep(26246, new RSTile(3502, 3429, 0), "Repair the bridge.", steelNails150, plank2, hammer);
        repairBridge3 = new ObjectStep(26247, new RSTile(3502, 3430, 0), "Repair the bridge.", steelNails75, plank1, hammer);
      //  repairBridge1.addSubSteps(repairBridge2, repairBridge3);
        talkToCurpile = new NPCStep( NpcID.CURPILE_FYOD, new RSTile(3508, 3440, 0), "Talk to Curpile Fyod.");
        talkToCurpile.addDialogStep("I've come to help the Myreque. I've brought weapons.");
        talkToCurpile1 = new NPCStep( NpcID.CURPILE_FYOD, new RSTile(3508, 3440, 0), "Talk to Curpile Fyod.");
        talkToCurpile1.addDialogStep("Sani Piliu.");
        talkToCurpile2 = new NPCStep( NpcID.CURPILE_FYOD, new RSTile(3508, 3440, 0), "Talk to Curpile Fyod.");
        talkToCurpile2.addDialogStep("Ivan Strom.");
        talkToCurpile3 = new NPCStep( NpcID.CURPILE_FYOD, new RSTile(3508, 3440, 0), "Talk to Curpile Fyod.");
        talkToCurpile3.addDialogStep("Veliaf Hurtz.");
        talkToCurpile4 = new NPCStep( NpcID.CURPILE_FYOD, new RSTile(3508, 3440, 0), "Talk to Curpile Fyod.");
        talkToCurpile4.addDialogStep("Cyreg Paddlehorn.");
        talkToCurpile5 = new NPCStep( NpcID.CURPILE_FYOD, new RSTile(3508, 3440, 0), "Talk to Curpile Fyod.");
        talkToCurpile5.addDialogStep("Drakan.");
        talkToCurpile6 = new NPCStep( NpcID.CURPILE_FYOD, new RSTile(3508, 3440, 0), "Talk to Curpile Fyod.");
        talkToCurpile6.addDialogStep("Polmafi Ferdygris.");
    //    talkToCurpile.addSubSteps(talkToCurpile1, talkToCurpile3, talkToCurpile4, talkToCurpile5, talkToCurpile6);
        enterDoors = new ObjectStep(5061, new RSTile(3509, 3448, 0), "Enter the wooden doors north of Curpile.",
                steelLong, steelSword2, steeldagger, steelMace, steelWarhammer);
        enterCave = new ObjectStep(5046, new RSTile(3492, 9823, 0), "Enter the cave to the north on the east side.");
        talkToHarold = new NPCStep( NpcID.HAROLD_EVANS, new RSTile(3504, 9833, 0), "Talk to Harold Evans.");
        talkToRadigad = new NPCStep( NpcID.RADIGAD_PONFIT, new RSTile(3510, 9833, 0), "Talk to Radigad Ponfit.");
        talkToSani = new NPCStep( NpcID.SANI_PILIU, new RSTile(3511, 9838, 0), "Talk to Sani Piliu.");
        talkToPolmafi = new NPCStep( NpcID.POLMAFI_FERDYGRIS, new RSTile(3512, 9839, 0), "Talk to Polmafi Ferdygris.");
        talkToIvan = new NPCStep( NpcID.IVAN_STROM_5053, new RSTile(3512, 9843, 0), "Talk to Ivan Strom.");
     //   talkToMembers = new DetailedQuestStep( "Talk to each of the members of the Myreque.");
      //  talkToMembers.addSubSteps(talkToHarold, talkToRadigad, talkToSani, talkToPolmafi, talkToIvan);

        talkToVeliaf = new NPCStep( NpcID.VELIAF_HURTZ_5048, new RSTile(3506, 9837, 0),
                "Talk to Veliaf Hurtz.");
        talkToVeliafForCutscene = new NPCStep( NpcID.VELIAF_HURTZ_5048, new RSTile(3506, 9837, 0),
                "Talk to Veliaf Hurtz.");
        talkToVeliafAgain = new NPCStep( NpcID.VELIAF_HURTZ_5048, new RSTile(3506, 9837, 0), steelLong, steelSword2, steelWarhammer, steelMace, steeldagger);
        talkToVeliafAgain.addDialogStep("Let's talk about the weapons.");
       // talkToVeliafAgain.addSubSteps(talkToVeliafForCutscene);

        climbTreeHellhound = new ObjectStep(5003, new RSTile(3502, 3426, 0),
                "Climb the tree to the north of the boat.", combatGear);
        enterDoorsHellhound = new ObjectStep(5061, new RSTile(3509, 3448, 0), "Enter the wooden doors north of Curpile.", combatGear);
        enterCaveHellhound = new ObjectStep(5046, new RSTile(3492, 9823, 0), "Enter the cave to the north on the east side.");
        killHellhound = new NPCStep( NpcID.SKELETON_HELLHOUND, new RSTile(3506, 9837, 0), "Kill the Skeleton Hellhound.");
       // killHellhound.addSubSteps(climbTreeHellhound, enterCaveHellhound, enterDoorsHellhound);

        climbTreeLeave = new ObjectStep(5003, new RSTile(3502, 3426, 0),
                "Climb the tree to the north of the boat.", steelLong, steelSword2, steeldagger, steelMace, steelWarhammer);
        enterDoorsLeave = new ObjectStep(5061, new RSTile(3509, 3448, 0), "Enter the wooden doors north of Curpile.");
        enterCaveLeave = new ObjectStep(5046, new RSTile(3492, 9823, 0), "Enter the cave to the north on the east side.");
        talkToVeliafToLeave = new NPCStep( NpcID.VELIAF_HURTZ_5048, new RSTile(3506, 9837, 0), "Talk to Veliaf Hurtz to learn the way out.");
        //talkToVeliafToLeave.addSubSteps(climbTreeLeave, enterDoorsLeave, enterCaveLeave);

        leaveCave = new ObjectStep(5046, new RSTile(3505, 9831, 0), "Leave the cave.");
        leaveCave.addDialogStep("Okay, thanks.");
        goUpToCanifis = new ObjectStep(5054, new RSTile(3477, 9846, 0), "Leave up the ladder in the north of the cave.");
        talkToStranger = new NPCStep(5055, new RSTile(3503, 3477, 0),
                "Talk to the Stranger in the Canifis pub to finish the quest!");*/
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

    }

    @Override
    public String questName() {
        return "In Search Of The Myreque";
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
