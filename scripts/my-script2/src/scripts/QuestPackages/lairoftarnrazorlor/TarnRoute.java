package scripts.QuestPackages.lairoftarnrazorlor;

import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.Log;
import scripts.ObjectID;
import scripts.QuestSteps.ObjectStep;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.AreaRequirement;
import scripts.Requirements.ItemOnTileRequirement;
import scripts.Requirements.ObjectCondition;
import scripts.Requirements.Util.Conditions;
import scripts.Requirements.Util.Operation;
import scripts.Requirements.VarbitRequirement;
import scripts.Tasks.Priority;

import java.util.ArrayList;
import java.util.Arrays;

public class TarnRoute implements QuestTask {


  /*  public void setupItemRequirements() {
        combatGear = new ItemRequirement("Combat gear", -1, -1);
        combatGear.setDisplayItemId(BankSlotIcons.getCombatGear());
        diary = new ItemRequirement("Tarn's diary", ItemID.TARNS_DIARY);
        diary.setHighlightInInventory(true);
        protectFromMagic = new PrayerRequirement("Activate Protect from Magic", Prayer.PROTECT_FROM_MAGIC);
    }*/

    RSArea hauntedMine = new RSArea(new RSTile(3390, 9600, 0), new RSTile(3452, 9668, 0));
    RSArea room1 = new RSArea(new RSTile(3136, 4544, 0), new RSTile(3199, 4570, 0));
    RSArea room1PastTrap1 = new RSArea(new RSTile(3196, 4558, 0), new RSTile(3196, 4562, 0));
    RSArea room1PastTrap2 = new RSArea(new RSTile(3193, 4563, 0), new RSTile(3196, 4570, 0));

    RSArea room2 = new RSArea(new RSTile(3172, 4574, 1), new RSTile(3197, 4589, 1));
    RSArea room3 = new RSArea(new RSTile(3166, 4575, 0), new RSTile(3170, 4579, 0));
    RSArea room4 = new RSArea(new RSTile(3166, 4586, 0), new RSTile(3170, 4592, 0));
    RSArea room5P1 = new RSArea(new RSTile(3143, 4589, 1), new RSTile(3162, 4590, 1));
    RSArea room5P2 = new RSArea(new RSTile(3154, 4590, 1), new RSTile(3157, 4598, 1));

    RSArea room6P1 = new RSArea(new RSTile(3136, 4592, 1), new RSTile(3151, 4600, 1));

    RSArea room6P2 = new RSArea(new RSTile(3141, 4601, 1), new RSTile(3163, 4607, 1));
    RSArea room6P3 = new RSArea(new RSTile(3159, 4596, 1), new RSTile(3175, 4602, 1));

    RSArea pillar1 = new RSArea(new RSTile(3148, 4595, 1), new RSTile(3148, 4595, 1));
    RSArea pillar2 = new RSArea(new RSTile(3146, 4595, 1), new RSTile(3146, 4595, 1));
    RSArea pillar3 = new RSArea(new RSTile(3144, 4595, 1), new RSTile(3144, 4595, 1));
    RSArea pillar4 = new RSArea(new RSTile(3142, 4595, 1), new RSTile(3142, 4595, 1));

    RSArea pillar5 = new RSArea(new RSTile(3144, 4597, 1), new RSTile(3144, 4597, 1));
    RSArea pillar6 = new RSArea(new RSTile(3144, 4599, 1), new RSTile(3144, 4599, 1));

    RSArea switch1 = new RSArea(new RSTile(3137, 4593, 1), new RSTile(3140, 4601, 1));

    RSArea room6PastTrap1 = new RSArea(new RSTile(3150, 4604, 1), new RSTile(3153, 4604, 1));
    RSArea room6PastTrap2P1 = new RSArea(new RSTile(3154, 4604, 1), new RSTile(3160, 4604, 1));
    RSArea room6PastTrap2P2 = new RSArea(new RSTile(3159, 4596, 1), new RSTile(3175, 4602, 1));

    RSArea extraRoom1 = new RSArea(new RSTile(3160, 4597, 0), new RSTile(3173, 4599, 0));
    RSArea extraRoom2 = new RSArea(new RSTile(3140, 4594, 0), new RSTile(3150, 4601, 0));

    RSArea room7 = new RSArea(new RSTile(3179, 4593, 1), new RSTile(3195, 4602, 1));

    RSArea room8 = new RSArea(new RSTile(3181, 4595, 0), new RSTile(3189, 4601, 0));
    RSArea bossRoom = new RSArea(new RSTile(3176, 4611, 0), new RSTile(3196, 4626, 0));
    RSArea finalRoom = new RSArea(new RSTile(3181, 4632, 0), new RSTile(3191, 4637, 0));


    AreaRequirement inHauntedMine = new AreaRequirement(hauntedMine);
    AreaRequirement inRoom1 = new AreaRequirement(room1);
    AreaRequirement inRoom1PastTrap1 = new AreaRequirement(room1PastTrap1);
    AreaRequirement inRoom1PastTrap2 = new AreaRequirement(room1PastTrap2);
    AreaRequirement inRoom2 = new AreaRequirement(room2);
    AreaRequirement inRoom3 = new AreaRequirement(room3);
    AreaRequirement inRoom4 = new AreaRequirement(room4);
    AreaRequirement inRoom5 = new AreaRequirement(room5P1, room5P2);
    AreaRequirement inRoom6P1 = new AreaRequirement(room6P1);
    AreaRequirement inRoom6P2 = new AreaRequirement(room6P2, room6P3);
    AreaRequirement inRoom7 = new AreaRequirement(room7);
    AreaRequirement onPillar1 = new AreaRequirement(pillar1);
    AreaRequirement onPillar2 = new AreaRequirement(pillar2);
    AreaRequirement onPillar3 = new AreaRequirement(pillar3);
    AreaRequirement onPillar4 = new AreaRequirement(pillar4);
    AreaRequirement onPillar5 = new AreaRequirement(pillar5);
    AreaRequirement onPillar6 = new AreaRequirement(pillar6);
    AreaRequirement atSwitch1 = new AreaRequirement(switch1);
    ItemOnTileRequirement switchPressed = new ItemOnTileRequirement(20635, new RSTile(3138, 4595, 1));
    AreaRequirement inRoom6PastTrap1 = new AreaRequirement(room6PastTrap1);
    AreaRequirement inRoom6PastTrap2 = new AreaRequirement(room6PastTrap2P1, room6PastTrap2P2);
    AreaRequirement inExtraRoom1 = new AreaRequirement(extraRoom1);
    AreaRequirement inExtraRoom2 = new AreaRequirement(extraRoom2);
    AreaRequirement inRoom8 = new AreaRequirement(room8);
    AreaRequirement inBossRoom = new AreaRequirement(bossRoom);
    AreaRequirement inFinalRoom = new AreaRequirement(finalRoom);

    //  tarnInSecondForm = new NpcCondition(NpcID.TARN);
    VarbitRequirement killedTarn = new VarbitRequirement(3290, 2, Operation.GREATER_EQUAL);



    ObjectStep enterLair = new ObjectStep(15833, new RSTile(3424, 9661, 0), "Enter the entrance to the north.");

    ObjectStep searchWallRoom1 = new ObjectStep(20588, new RSTile(3196, 4557, 0), "Follow the path west then north, and go through the door you reach.");

    ArrayList<RSTile> searchWallRoom1Tiles = new ArrayList<>(Arrays.asList(
            new RSTile(3166, 4547, 0),
            new RSTile(3166, 4550, 0),
            new RSTile(3175, 4550, 0),
            new RSTile(3175, 4549, 0),
            new RSTile(3184, 4549, 0),
            new RSTile(3184, 4548, 0),
            new RSTile(3189, 4548, 0),
            new RSTile(3190, 4547, 0),
            new RSTile(3191, 4548, 0),
            new RSTile(3190, 4550, 0),
            new RSTile(3190, 4555, 0),
            new RSTile(3196, 4555, 0),
            new RSTile(3196, 4556, 0)
    ));



    ObjectStep searchWall2Room1 = new ObjectStep(20588, new RSTile(3197, 4562, 0), "Follow the path west then north, and go through the door you reach.");

    ObjectStep goThroughRoom1 = new ObjectStep(20517, new RSTile(3195, 4571, 0), "Follow the path west then north, and go through the door you reach.");
        //goThroughRoom1.addSubSteps(searchWallRoom1,searchWall2Room1);

    ObjectStep goThroughRoom2 = new ObjectStep(20513, new RSTile(3174, 4577, 1), "Continue out the west of room.");
    ArrayList<RSTile> room2Tiles = new ArrayList<>(Arrays.asList(
            new RSTile(3195, 4575, 1),
            new RSTile(3195, 4579, 1),
            new RSTile(3196, 4580, 1),
            new RSTile(3195, 4581, 1),
            new RSTile(3195, 4585, 1),
            new RSTile(3182, 4585, 1),
            new RSTile(3181, 4586, 1),
            new RSTile(3176, 4586, 1),
            new RSTile(3176, 4583, 1),
            new RSTile(3175, 4582, 1),
            new RSTile(3175, 4577, 1)
    ));


    ObjectStep goThroughRoom3 = new ObjectStep(20523, new RSTile(3168, 4580, 0), "Go through the north door.");
    ObjectStep goThroughRoom4 = new ObjectStep(20525, new RSTile(3165, 4589, 0), "Go through the west door.");

    ObjectStep leaveExtraRoom1 = new ObjectStep(20531, new RSTile(3168, 4596, 0), "Go into the south door.");
    ObjectStep leaveExtraRoom2 = new ObjectStep(20529, new RSTile(3150, 4598, 0), "Go into the east passageway.");

    ObjectStep goThroughRoom5 = new ObjectStep(20533, new RSTile(3154, 4597, 1), "Go through the north door.");
     //   goThroughRoom5.addSubSteps(leaveExtraRoom1,leaveExtraRoom2);

    ObjectStep jumpToPillar1 = new ObjectStep(20543, new RSTile(3148, 4595, 1), "Jump across the pillars to the west ledge.");
    ObjectStep jumpToPillar2 = new ObjectStep(20544, new RSTile(3146, 4595, 1), "Jump across the pillars.");
    ObjectStep jumpToPillar3 = new ObjectStep(20545, new RSTile(3144, 4595, 1), "Jump across the pillars.");
    ObjectStep jumpToPillar4 = new ObjectStep(20546, new RSTile(3142, 4595, 1), "Jump across the pillars.");

    ObjectStep jumpToSwitch = new ObjectStep(20562, new RSTile(3140, 4595, 1), "Jump to the ledge.");
     //  jumpToPillar1.addSubSteps(jumpToPillar2,jumpToPillar3,jumpToPillar4,jumpToSwitch);

    ObjectStep pressSwitch = new ObjectStep(20634, new RSTile(3138, 4595, 1), "Search the floor.");

    ObjectStep jumpBackToPillar4 = new ObjectStep(20546, new RSTile(3142, 4595, 1), "Jump across the pillars to the north side.");
    ObjectStep jumpBackToPillar3 = new ObjectStep(20545, new RSTile(3144, 4595, 1), "Jump across the pillars to the north side.");
    ObjectStep jumpToPillar5 = new ObjectStep(20547, new RSTile(3144, 4597, 1), "Jump across the pillars to the north side.");
    ObjectStep jumpToPillar6 = new ObjectStep(20548, new RSTile(3144, 4599, 1), "Jump across the pillars to the north side.");

    ObjectStep jumpToNorthLedge = new ObjectStep(20563, new RSTile(3144, 4601, 1), "Jump across the pillars to the north side.");
   //     jumpToNorthLedge.addSubSteps(jumpBackToPillar3,jumpBackToPillar4,jumpToPillar5,jumpToPillar6);

    ObjectStep searchWallRoom6 = new ObjectStep(20590, new RSTile(3149, 4604, 1), "Follow the path to the east.");
    ObjectStep searchWall2Room6 = new ObjectStep(20590, new RSTile(3154, 4605, 1), "Follow the path to the east.");

    ObjectStep goThroughRoom6 = new ObjectStep(20535, new RSTile(3176, 4598, 1), "Follow the path to the east.");
       // goThroughRoom6.addSubSteps(searchWallRoom6,searchWall2Room6);

    ObjectStep goThroughRoom7 = new ObjectStep(17098, new RSTile(3193, 4598, 1),
            "Activate Protect from Magic and jump across the pillars. Go down the stairs.");
           // protectFromMagic);

    ObjectStep enterBossRoom = new ObjectStep(20539, new RSTile(3185, 4602, 0), "Enter the north passageway, and be prepared to fight.");




    public void doSteps() {
        if (inRoom8.check()) {
            Log.log("[Debug]: ");
            enterBossRoom.execute();
        } else if (inRoom7.check()) {
            Log.log("[Debug]: ");
            goThroughRoom7.execute();
        } else if (inRoom6PastTrap2.check()) {
            Log.log("[Debug]: ");
            goThroughRoom6.execute();
        } else if (inRoom6PastTrap1.check()) {
            Log.log("[Debug]: searchWall2Room6.execute()");
            searchWall2Room6.execute();
        } else if (inRoom6P2.check()) {
            Log.log("[Debug]: searchWallRoom6.execute()");
            searchWallRoom6.execute();
        } else if (onPillar6.check()) {
            Log.log("[Debug]: jumpToNorthLedge");
            jumpToNorthLedge.execute();
        } else if (onPillar5.check()) {
            Log.log("[Debug]: jumpToPillar6");
            jumpToPillar6.execute();
        } else if (new Conditions(onPillar3, switchPressed).check()) {
            Log.log("[Debug]: jumpToPillar5");
            jumpToPillar5.execute();
        } else if (new Conditions(onPillar4, switchPressed).check()) {
            Log.log("[Debug]: jumpBackToPillar3");
            jumpBackToPillar3.execute();
        } else if (new Conditions(atSwitch1, switchPressed).check()) {
            Log.log("[Debug]: jumpBackToPillar4");
            jumpBackToPillar4.execute();
        } else if (atSwitch1.check()) {
            Log.log("[Debug]: pressSwitch");
            pressSwitch.execute();
        } else if (onPillar4.check()) {
            Log.log("[Debug]: jumpToSwitch");
            jumpToSwitch.execute();
        } else if (onPillar3.check()) {
            Log.log("[Debug]: jumpToPillar4");
            jumpToPillar4.execute();
        } else if (onPillar2.check()) {
            Log.log("[Debug]: jumpToPillar3");
            jumpToPillar3.execute();
        } else if (onPillar1.check()) {
            Log.log("[Debug]: jumpToPillar2");
            jumpToPillar2.execute();
        } else if (inRoom6P1.check()) {
            Log.log("[Debug]: jumpToPillar1");
            jumpToPillar1.execute();
        } else if (inRoom5.check()) {
            Log.log("[Debug]: goThroughRoom5");
            goThroughRoom5.execute();
        } else if (inRoom4.check()) {
            Log.log("[Debug]: goThroughRoom4");
            goThroughRoom4.execute();
        } else if (inRoom3.check()) {
            Log.log("[Debug]: goThroughRoom3");
            goThroughRoom3.execute();
        } else if (inRoom2.check()) {
            Log.log("[Debug]: goThroughRoom2");
            goThroughRoom2.execute();
        } else if (inRoom1PastTrap2.check()) {
            Log.log("[Debug]: goThroughRoom1");
            goThroughRoom1.execute();
        } else if (inRoom1PastTrap1.check()) {
            Log.log("[Debug]: searchWall2Room1");
            searchWall2Room1.execute();
        } else if (inRoom1.check()) {
            Log.log("[Debug]: searchWallRoom1");
            searchWallRoom1.execute();
        } else if (inHauntedMine.check()) {
            Log.log("[Debug]: enterLair");
            enterLair.execute();
        }
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
}
