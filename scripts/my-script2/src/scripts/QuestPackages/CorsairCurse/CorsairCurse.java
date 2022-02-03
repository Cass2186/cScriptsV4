package scripts.QuestPackages.CorsairCurse;

import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import scripts.ItemID;
import scripts.NpcID;
import scripts.ObjectID;
import scripts.QuestPackages.RfdCook.RfdCook;
import scripts.QuestSteps.ClickItemStep;
import scripts.QuestSteps.NPCStep;
import scripts.QuestSteps.ObjectStep;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.AreaRequirement;
import scripts.Requirements.ItemRequirement;
import scripts.Requirements.Util.Operation;
import scripts.Requirements.VarbitRequirement;
import scripts.Tasks.Priority;
import scripts.cQuesterV2;

public class CorsairCurse implements QuestTask {

    private static CorsairCurse quest;

    public static CorsairCurse get() {
        return quest == null ? quest = new CorsairCurse() : quest;
    }


    ItemRequirement combatGear = new ItemRequirement("Combat gear + food to defeat Ithoi (level 34), who uses magic", -1, -1);

    ItemRequirement spade = new ItemRequirement("Spade", ItemID.SPADE);
    ItemRequirement tinderbox = new ItemRequirement("Tinderbox", ItemID.TINDERBOX);
    ItemRequirement ogreArtfact = new ItemRequirement("Ogre artefact", 21837);


    RSArea cove = new RSArea(new RSTile(2308, 2806, 0), new RSTile(2705, 3136, 2));
    RSArea ithoiHut = new RSArea(new RSTile(2527, 2835, 1), new RSTile(2532, 2841, 1));
    RSArea gnocciHut = new RSArea(new RSTile(2543, 2860, 1), new RSTile(2547, 2864, 1));
    RSArea arsenHut = new RSArea(new RSTile(2553, 2853, 1), new RSTile(2559, 2859, 1));
    RSArea ship = new RSArea(new RSTile(2573, 2835, 1), new RSTile(2583, 2837, 1));
    RSArea cavern = new RSArea(new RSTile(1876, 8960, 1), new RSTile(2073, 9093, 1));


    AreaRequirement inCove = new AreaRequirement(cove);
    AreaRequirement inIthoiHut = new AreaRequirement(ithoiHut);
    AreaRequirement inGnocciHut = new AreaRequirement(gnocciHut);
    AreaRequirement inArsenHut = new AreaRequirement(arsenHut);
    AreaRequirement inShip = new AreaRequirement(ship);
    AreaRequirement inCavern = new AreaRequirement(cavern);
    VarbitRequirement talkedToIthoi = new VarbitRequirement(6075, 1);

    VarbitRequirement talkedToArsen = new VarbitRequirement(6074, 2, Operation.GREATER_EQUAL);
    VarbitRequirement returnedToothPick = new VarbitRequirement(6074, 4);
    VarbitRequirement finishedArsen = new VarbitRequirement(6074, 5, Operation.GREATER_EQUAL);

    VarbitRequirement talkedToColin = new VarbitRequirement(6072, 1, Operation.GREATER_EQUAL);
    VarbitRequirement lookedThroughTelescope = new VarbitRequirement(6072, 2);
    VarbitRequirement finishedColin = new VarbitRequirement(6072, 3);

    VarbitRequirement talkedToGnocci = new VarbitRequirement(6073, 1);
    VarbitRequirement foundDoll = new VarbitRequirement(6073, 2);
    VarbitRequirement finishedGnocci = new VarbitRequirement(6073, 3);


    public void setupSteps() {
        NPCStep talkToTockFarm = new NPCStep(NpcID.CAPTAIN_TOCK, new RSTile(3030, 3273, 0), "Talk to Captain Tock north of Port Sarim.");
        talkToTockFarm.addDialogStep("What kind of help do you need?");
        talkToTockFarm.addDialogStep("Sure, I'll try to help with your curse.");

        NPCStep talkToTockRimmington = new NPCStep(NpcID.CAPTAIN_TOCK, new RSTile(2910, 3226, 0), "Talk to Captain Tock west of Rimmington.");
        talkToTockRimmington.addDialogStep("Okay, I'm ready go to Corsair Cove.");

        NPCStep returnToCove = new NPCStep(NpcID.CAPTAIN_TOCK_7958, new RSTile(2910, 3226, 0), "Return to Corsair Cove.");
        returnToCove.addDialogStep("Let's go.");

        ObjectStep goUpToIthoi = new ObjectStep(ObjectID.STAIRS_31735, new RSTile(2531, 2833, 0), "Talk to Ithoi in the south western hut.");
        NPCStep talkToIthoi = new NPCStep(NpcID.ITHOI_THE_NAVIGATOR, new RSTile(2529, 2840, 1), "Talk to Ithoi in the south western hut.");
        talkToIthoi.addDialogStep("I hear you've been cursed.");
        // talkToIthoi.addSubSteps(goUpToIthoi);

        ObjectStep goDownFromIthoi = new ObjectStep(ObjectID.STAIRS_31735, new RSTile(2529, 2834, 1), "Talk to Arsen in the central hut.");
        ObjectStep goUpToGnocci = new ObjectStep(ObjectID.STAIRS_31733, new RSTile(2549, 2862, 0), "Talk to Gnocci in the north west hut.");
        NPCStep talkToGnocci = new NPCStep(NpcID.GNOCCI_THE_COOK, new RSTile(2545, 2863, 1), "Talk to Gnocci in the north west hut.");
        talkToGnocci.addDialogStep("I hear you've been cursed.");
        // talkToGnocci.addSubSteps(goUpToGnocci, goDownFromIthoi);

        ObjectStep goDownFromGnocci = new ObjectStep(ObjectID.STAIRS_31734, new RSTile(2548, 2862, 1), "Talk to Chief Tess down the hole west of the Cove.");

        ObjectStep goUpToArsen = new ObjectStep(ObjectID.STAIRS_31733, new RSTile(2555, 2856, 0), "Talk to Arsen in the central hut.");
        NPCStep talkToArsen = new NPCStep(NpcID.ARSEN_THE_THIEF, new RSTile(2554, 2859, 1), "Talk to Arsen in the central hut.");
        talkToArsen.addDialogStep("I hear you've been cursed.");
        // talkToArsen.addSubSteps(goDownFromIthoi, goUpToArsen);

        ObjectStep goUpToColin = new ObjectStep(ObjectID.STAIRS_31733, new RSTile(2555, 2856, 0), "Talk to Colin in the central hut.");
        NPCStep talkToColin = new NPCStep(NpcID.CABIN_BOY_COLIN, new RSTile(2558, 2858, 1), "Talk to Colin in the central hut.");
        talkToColin.addDialogStep("I hear you've been cursed.");
        // talkToColin.addSubSteps(goUpToColin);

        ObjectStep goDownFromArsen = new ObjectStep(ObjectID.STAIRS_31734, new RSTile(2555, 2855, 1), "Talk to Gnocci in the north west hut.");
        ObjectStep grabTinderbox = new ObjectStep(ObjectID.TINDERBOX, new RSTile(2543, 2862, 1), "Pick up the tinderbox next to Gnocci.", tinderbox);
        ObjectStep pickUpSpade = new ObjectStep(ObjectID.SPADE_31585, new RSTile(2552, 2846, 0), "Take the spade in the south of the Corsair Cove.");

        NPCStep talkToTockShip = new NPCStep(NpcID.CAPTAIN_TOCK_7958, new RSTile(2574, 2835, 1), "Talk to Captain Tock on the ship.");
        talkToTockShip.addDialogStep("Arsen says he gave you a sacred ogre relic.");
        talkToTockShip.addDialogStep("About that sacred ogre relic...");
        ObjectStep goOntoShip = new ObjectStep(ObjectID.GANGPLANK_31756, new RSTile(2578, 2839, 0),
                "Talk to Captain Tock on the ship.");

        ObjectStep leaveShip = new ObjectStep(ObjectID.GANGPLANK_31756, new RSTile(2578, 2838, 1), "Enter the hole west of the Corsair Cove and talk to Chief Tess.");
        ObjectStep goDownToTess = new ObjectStep(ObjectID.HOLE_31791, new RSTile(2523, 2861, 0), "Enter the hole west of the Corsair Cove and talk to Chief Tess.");
        NPCStep talkToTess = new NPCStep(NpcID.CHIEF_TESS, new RSTile(2012, 9006, 1), "Talk to Chief Tess.");
        // talkToTess.addSubSteps(goDownToTess, leaveShip);
        talkToTess.addDialogStep("I've come to return what Arsen stole.");

        ObjectStep goUpFromTess = new ObjectStep(ObjectID.VINE_LADDER_31790, new RSTile(2012, 9005, 1), "Leave the cavern.");
        ClickItemStep digSand = new ClickItemStep(ItemID.SPADE, "Dig" ,new RSTile(2504, 2840, 0));
        digSand.addDialogStep("Search for the possessed doll and face the consequences.");
        //    digSand.addSubSteps(goUpFromTess);

        ObjectStep goUpToIthoi2 = new ObjectStep(ObjectID.STAIRS_31735, new RSTile(2531, 2833, 0), "Go look through Ithoi's telescope.");
        ObjectStep lookThroughTelescope = new ObjectStep(ObjectID.TELESCOPE_31632, new RSTile(2528, 2835, 1), "Go look through Ithoi's telescope.");
      //  lookThroughTelescope.addSubSteps(goUpToIthoi2);

        ObjectStep goDownFromIthoi2 = new ObjectStep(ObjectID.STAIRS_31735, new RSTile(2529, 2834, 1), "Leave Ithoi's hut.");
        ObjectStep goUpToGnocci2 = new ObjectStep(ObjectID.STAIRS_31733, new RSTile(2549, 2862, 0), "Talk to Gnocci in the north west hut.");
        NPCStep talkToGnocci2 = new NPCStep(NpcID.GNOCCI_THE_COOK, new RSTile(2545, 2863, 1), "Talk to Gnocci in the north west hut.");
       // talkToGnocci2.addSubSteps(goUpToGnocci2, goDownFromIthoi2);

        ObjectStep goDownFromGnocci2 = new ObjectStep(ObjectID.STAIRS_31734, new RSTile(2548, 2862, 1), "Talk to Arsen.");
        ObjectStep goUpToArsen2 = new ObjectStep(ObjectID.STAIRS_31733, new RSTile(2555, 2856, 0), "Talk to Arsen in the central hut.");
        NPCStep talkToArsen2 = new NPCStep(NpcID.ARSEN_THE_THIEF, new RSTile(2554, 2859, 1), "Talk to Arsen in the central hut.");
      //  talkToArsen2.addSubSteps(goUpToArsen2, goDownFromGnocci2);

        ObjectStep goUpToColin2 = new ObjectStep(ObjectID.STAIRS_31733, new RSTile(2555, 2856, 0), "Talk to Colin in the central hut.");
        NPCStep talkToColin2 = new NPCStep(NpcID.CABIN_BOY_COLIN, new RSTile(2558, 2858, 1), "Talk to Colin in the central hut.");
       // talkToColin2.addSubSteps(goUpToColin);

        ObjectStep goOntoShip2 = new ObjectStep(ObjectID.GANGPLANK_31756, new RSTile(2578, 2839, 0), "Talk to Captain Tock on the ship.");
        NPCStep talkToTockShip2 = new NPCStep(NpcID.CAPTAIN_TOCK_7958, new RSTile(2574, 2835, 1), "Talk to Captain Tock on the ship.");
        talkToTockShip2.addDialogStep("I've ruled out all the Corsairs' theories...");
        talkToTockShip2.addDialogStep("So what do I do now?");
       // talkToTockShip2.addSubSteps(goOntoShip2);

        ObjectStep leaveShip2 = new ObjectStep(ObjectID.GANGPLANK_31756, new RSTile(2578, 2838, 1), "Talk to Gnocci in the north west hut.");
        ObjectStep goUpToGnocci3 = new ObjectStep(ObjectID.STAIRS_31733, new RSTile(2549, 2862, 0), "Talk to Gnocci in the north west hut.");
        NPCStep talkToGnocci3 = new NPCStep(NpcID.GNOCCI_THE_COOK, new RSTile(2545, 2863, 1), "Talk to Gnocci in the north west hut.");
        talkToGnocci3.addDialogStep("I hear it happened straight after dinner.");
       // talkToGnocci3.addSubSteps(goUpToGnocci3, leaveShip2);

        ObjectStep goDownFromGnocci3 = new ObjectStep(ObjectID.STAIRS_31734, new RSTile(2548, 2862, 1), "Talk to Arsen.");
        ObjectStep goUpToArsen3 = new ObjectStep(ObjectID.STAIRS_31733, new RSTile(2555, 2856, 0), "Talk to Arsen in the central hut.");
        NPCStep talkToArsen3 = new NPCStep(NpcID.ARSEN_THE_THIEF, new RSTile(2554, 2859, 1), "Talk to Arsen in the central hut.");
        talkToArsen3.addDialogStep("I hear Ithoi cooked the meal you ate that night.");
      //  talkToArsen3.addSubSteps(goUpToArsen3, goDownFromGnocci3);

        ObjectStep goDownFromArsen3 = new ObjectStep(ObjectID.STAIRS_31734, new RSTile(2555, 2855, 1), "Talk to Ithoi in the south western hut.");
        ObjectStep goUpToIthoi3 = new ObjectStep(ObjectID.STAIRS_31735, new RSTile(2531, 2833, 0), "Talk to Ithoi in the south western hut.");
        NPCStep talkToIthoi2 = new NPCStep(NpcID.ITHOI_THE_NAVIGATOR, new RSTile(2529, 2840, 1), "Talk to Ithoi.");
       // talkToIthoi2.addSubSteps(goUpToIthoi3, goDownFromArsen3);
        talkToIthoi2.addDialogStep("I bet I can prove you're well enough to get up.");
        talkToIthoi2.addDialogStep("I know you've faked the curse.");
        talkToIthoi2.addDialogStep("I hear you cooked the meal they ate before getting sick.");
        talkToIthoi2.addDialogStep("Maybe because the Captain's thinking of firing you.");

        ObjectStep goDownFromIthoi3 = new ObjectStep(ObjectID.STAIRS_31735, new RSTile(2529, 2834, 1), "Use your tinderbox on the driftwood under Ithoi's hut.");
        ObjectStep useTinderboxOnWood = new ObjectStep(31724, new RSTile(2531, 2838, 0), "Use your tinderbox on the driftwood under Ithoi's hut.", tinderbox);
       // useTinderboxOnWood.addIcon(ItemID.TINDERBOX);
     //   useTinderboxOnWood.addSubSteps(goDownFromIthoi3);

        ObjectStep goOntoShip3 = new ObjectStep(ObjectID.GANGPLANK_31756, new RSTile(2578, 2839, 0), "Talk to Captain Tock on the ship.");
        NPCStep talkToTockShip3 = new NPCStep(NpcID.CAPTAIN_TOCK_7958, new RSTile(2574, 2835, 1), "Talk to Captain Tock on the ship.");
        talkToTockShip3.addDialogStep("I've seen Ithoi running around. He's not sick at all.");
       // talkToTockShip3.addSubSteps(goOntoShip3);

        ObjectStep goUpToIthoiToKill = new ObjectStep(ObjectID.STAIRS_31735, new RSTile(2531, 2833, 0), "Go kill Ithoi (level 35) in his hut.");
        goUpToIthoiToKill.addDialogStep("I'll be back.");
        NPCStep killIthoi = new NPCStep(NpcID.ITHOI_THE_NAVIGATOR_7964, new RSTile(2529, 2840, 1), "Kill Ithoi (level 35).");
       // killIthoi.addSubSteps(goUpToIthoiToKill);

        ObjectStep goOntoShip4 = new ObjectStep(ObjectID.GANGPLANK_31756, new RSTile(2578, 2839, 0), "Talk to Captain Tock on the ship to finish the quest.");
        NPCStep talkToTockShip4 = new NPCStep(NpcID.CAPTAIN_TOCK_7958, new RSTile(2574, 2835, 1), "Talk to Captain Tock on the ship to finish the quest.");
        talkToTockShip4.addDialogStep("I've killed Ithoi for poisoning your crew.");
      //  talkToTockShip4.addSubSteps(goOntoShip4);
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
        return "The Corsair Curse";
    }

    @Override
    public boolean checkRequirements() {
        return false;
    }
}