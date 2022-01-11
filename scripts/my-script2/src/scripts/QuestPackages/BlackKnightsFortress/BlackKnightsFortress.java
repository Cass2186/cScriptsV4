package scripts.QuestPackages.BlackKnightsFortress;

import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import scripts.ItemId;
import scripts.ObjectID;
import scripts.QuestSteps.NPCStep;
import scripts.QuestSteps.ObjectStep;
import scripts.QuestSteps.QuestTask;
import scripts.QuestSteps.UseItemOnObjectStep;
import scripts.Requirements.AreaRequirement;
import scripts.Requirements.ItemReq;
import scripts.Tasks.Priority;
import scripts.cQuesterV2;

public class BlackKnightsFortress implements QuestTask {

    private static BlackKnightsFortress quest;

    public static BlackKnightsFortress get() {
        return quest == null ? quest = new BlackKnightsFortress() : quest;
    }

    RSArea whiteKnightsCastleF1 = new RSArea(new RSTile(2954, 3353, 1), new RSTile(2998, 3327, 1));
    RSArea whiteKnightsCastleF2 = new RSArea(new RSTile(2954, 3353, 2), new RSTile(2998, 3327, 2));

    RSArea secretRoomFloor0 = new RSArea(new RSTile(3015, 3517, 0), new RSTile(3016, 3519, 0));
    RSArea secretRoomFloor1 = new RSArea(new RSTile(3015, 3517, 1), new RSTile(3016, 3519, 1));
    RSArea secretRoomFloor2 = new RSArea(new RSTile(3007, 3513, 2), new RSTile(3018, 3519, 2));
    RSArea secretRoomFloor3 = new RSArea(new RSTile(3009, 3514, 3), new RSTile(3012, 3517, 3));

    RSArea secretBasement = new RSArea(new RSTile(1862, 4264, 0), new RSTile(1873, 4229, 0));
    RSArea mainEntrance1 = new RSArea(new RSTile(3008, 3513, 0), new RSTile(3012, 3518, 0));
    RSArea mainEntrance2 = new RSArea(new RSTile(3012, 3514, 0), new RSTile(3014, 3516, 0));
    RSArea mainEntrance3 = new RSArea(new RSTile(3015, 3515, 0), new RSTile(3019, 3516, 0));
    RSArea mainEntrance4 = new RSArea(new RSTile(3019, 3513, 0), new RSTile(3019, 3517, 0));
    RSArea eastRoom1Floor0 = new RSArea(new RSTile(3020, 3513, 0), new RSTile(3030, 3518, 0));
    RSArea eastRoom2Floor0 = new RSArea(new RSTile(3021, 3511, 0), new RSTile(3030, 3512, 0));

    RSArea eastRoom1Floor1 = new RSArea(new RSTile(3021, 3506, 1), new RSTile(3025, 3512, 1));
    RSArea eastRoom2Floor1 = new RSArea(new RSTile(3021, 3513, 1), new RSTile(3021, 3513, 1));
    RSArea eastRoom3Floor1 = new RSArea(new RSTile(3026, 3510, 1), new RSTile(3029, 3515, 1));
    RSArea eastRoom4Floor1 = new RSArea(new RSTile(3025, 3513, 1), new RSTile(3025, 3516, 1));

    RSArea eastRoomFloor2 = new RSArea(new RSTile(3021, 3504, 2), new RSTile(3031, 3516, 2));

    RSArea listeningRoom1 = new RSArea(new RSTile(3021, 3510, 0), new RSTile(3026, 3510, 0));
    RSArea listeningRoom2 = new RSArea(new RSTile(3025, 3506, 0), new RSTile(3026, 3509, 0));

    RSArea westFloor1 = new RSArea(new RSTile(3007, 3513, 1), new RSTile(3014, 3519, 1));

    RSArea centralArea1Floor1 = new RSArea(new RSTile(3015, 3514, 1), new RSTile(3024, 3516, 1));
    RSArea centralArea2Floor1 = new RSArea(new RSTile(3019, 3517, 1), new RSTile(3021, 3518, 1));
    RSArea centralArea3Floor1 = new RSArea(new RSTile(3022, 3513, 1), new RSTile(3024, 3513, 1));

    RSArea northPathToCabbageHole1 = new RSArea(new RSTile(3022, 3517, 1), new RSTile(3030, 3518, 1));
    RSArea northPathToCabbageHole2 = new RSArea(new RSTile(3030, 3510, 1), new RSTile(3030, 3516, 1));
    RSArea northPathToCabbageHole3 = new RSArea(new RSTile(3029, 3516, 1), new RSTile(3029, 3516, 1));

    RSArea cabbageHoleRoom = new RSArea(new RSTile(3026, 3504, 1), new RSTile(3032, 3509, 1));

    RSArea eastTurret = new RSArea(new RSTile(3027, 3505, 3), new RSTile(3031, 3508, 3));


    ItemReq ironChainbody = new ItemReq(ItemId.IRON_CHAINBODY, 1, true, true);
    ItemReq cabbage = new ItemReq(ItemId.CABBAGE, 1);
    ItemReq bronzeMed = new ItemReq(ItemId.BRONZE_MED_HELM, 1, true, true);

    ItemReq teleportFalador = new ItemReq(ItemId.FALADOR_TELEPORT, 5, 1);
    ItemReq food = new ItemReq(ItemId.LOBSTER, 6, 1);

    AreaRequirement inFaladorF1 = new AreaRequirement(whiteKnightsCastleF1);
    AreaRequirement inFaladorF2 = new AreaRequirement(whiteKnightsCastleF2);
    AreaRequirement onTopOfFortress = new AreaRequirement(secretRoomFloor3);
    AreaRequirement inBasement = new AreaRequirement(secretBasement);
    AreaRequirement inEastTurret = new AreaRequirement(eastTurret);
    AreaRequirement inSecretRoomGroundFloor = new AreaRequirement(secretRoomFloor0);
    AreaRequirement inSecretRoomFirstFloor = new AreaRequirement(secretRoomFloor1);
    AreaRequirement inSecretRoomSecondFloor = new AreaRequirement(secretRoomFloor2);
    AreaRequirement inMainEntrance = new AreaRequirement(mainEntrance1, mainEntrance2, mainEntrance3, mainEntrance4);
    AreaRequirement inCentralAreaFloor1 = new AreaRequirement(centralArea1Floor1, centralArea2Floor1, centralArea3Floor1);
    AreaRequirement inWestRoomFloor1 = new AreaRequirement(westFloor1);
    AreaRequirement inEastRoomFloor0 = new AreaRequirement(eastRoom1Floor0, eastRoom2Floor0);
    AreaRequirement inEastRoomFloor1 = new AreaRequirement(eastRoom1Floor1, eastRoom2Floor1, eastRoom3Floor1, eastRoom4Floor1);
    AreaRequirement inEastRoomFloor2 = new AreaRequirement(eastRoomFloor2);
    AreaRequirement inListeningRoom = new AreaRequirement(listeningRoom1, listeningRoom2);
    AreaRequirement inPathToCabbageRoom = new AreaRequirement(northPathToCabbageHole1, northPathToCabbageHole2, northPathToCabbageHole3);
    AreaRequirement inCabbageHoleRoom = new AreaRequirement(cabbageHoleRoom);


    private void setupSteps() {

        NPCStep speakToAmik = new NPCStep(4771, new RSTile(2959, 3339, 2));
        speakToAmik.addDialogStep("I seek a quest!");
        speakToAmik.addDialogStep("I laugh in the face of danger!");
        speakToAmik.addDialogStep("Ok, I'll do my best.");

        /* Path to grill */
        ObjectStep enterFortress = new ObjectStep(ObjectID.STURDY_DOOR, new RSTile(3016, 3514, 0), "Enter the Black Knights' Fortress. Be prepared for multiple level 33 Black Knights to attack you.",
                ironChainbody, bronzeMed, cabbage);
        ObjectStep pushWall = new ObjectStep(ObjectID.WALL_2341, new RSTile(3016, 3517, 0), "Push the wall to enter a secret room.");
        ObjectStep climbUpLadder1 = new ObjectStep(ObjectID.LADDER_17148, new RSTile(3015, 3519, 0),
                "Climb up the ladder.");
        ObjectStep climbUpLadder2 = new ObjectStep(ObjectID.LADDER_17148, new RSTile(3016, 3519, 1),
                "Climb up the next ladder.");
        ObjectStep climbDownLadder3 = new ObjectStep(ObjectID.LADDER_17149, new RSTile(3017, 3516, 2),
                "Climb down the ladder south of you.");
        ObjectStep climbUpLadder4 = new ObjectStep(ObjectID.LADDER_17148, new RSTile(3023, 3513, 1),
                "Climb up the ladder east of you.");
        ObjectStep climbDownLadder5 = new ObjectStep(ObjectID.LADDER_17149, new RSTile(3025, 3513, 2),
                "Climb down the next ladder east of you.");
        ObjectStep climbDownLadder6 = new ObjectStep(ObjectID.LADDER_17149, new RSTile(3021, 3510, 1),
                "Climb down the ladder west of you.");
        ObjectStep listenAtGrill = new ObjectStep(ObjectID.GRILL, new RSTile(3026, 3507, 0),
                "Listen at the grill.");

        /* Path to cabbage hole */
        ObjectStep climbUpLadder6 = new ObjectStep(ObjectID.LADDER_17148, new RSTile(3021, 3510, 0),
                "Climb back up the ladder from the listening room.", cabbage);
        ObjectStep climbUpLadder5 = new ObjectStep(ObjectID.LADDER_17148, new RSTile(3025, 3513, 1),
                "Climb up the ladder in the chapel room east of you.", cabbage);
        ObjectStep climbDownLadder4 = new ObjectStep(ObjectID.LADDER_17149, new RSTile(3023, 3513, 2),
                "Climb down the ladder west of you.", cabbage);
        ObjectStep climbUpLadder3 = new ObjectStep(ObjectID.LADDER_17148, new RSTile(3017, 3516, 1),
                "Climb up the ladder west of you.", cabbage);
        ObjectStep climbDownLadder2 = new ObjectStep(ObjectID.LADDER_17149, new RSTile(3016, 3519, 2),
                "Climb down the ladder north of you.", cabbage);
        ObjectStep climbDownLadder1 = new ObjectStep(ObjectID.LADDER_17149, new RSTile(3015, 3519, 1),
                "Climb down the next ladder.", cabbage);
        ObjectStep goUpLadderToCabbageZone = new ObjectStep(ObjectID.LADDER_17159, new RSTile(3022, 3518, 0),
                "Go into the east room and climb the ladder there. When trying to go through the door to the room, you'll have to go through some dialog. Select option 2.", cabbage);
        goUpLadderToCabbageZone.addDialogStep("I don't care. I'm going in anyway.");
        ObjectStep  pushWall2 = new ObjectStep(ObjectID.WALL_2341, new RSTile(3030, 3510, 1),
                "Push the wall to enter the storage room", cabbage);
        UseItemOnObjectStep useCabbageOnHole = new UseItemOnObjectStep(cabbage.getId(), 2336, new RSTile(3031, 3507, 1),
                "USE the cabbage on the hole here. Be careful not to eat it.", cabbage);


        NPCStep returnToAmik = new NPCStep(4771, new RSTile(2959, 3339, 2),
                "Return to Sir Amik Varze in Falador Castle to complete the quest.");

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
        return "Black Knight's Fortress";
    }

    @Override
    public boolean checkRequirements() {
        return false;
    }
}
