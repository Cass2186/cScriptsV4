package scripts.QuestPackages.BlackKnightsFortress;

import dax.walker_engine.WalkerEngine;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.Game;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.GameState;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Waiting;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestSteps.*;
import scripts.Requirements.AreaRequirement;
import scripts.Requirements.ItemReq;
import scripts.Requirements.ItemRequirement;
import scripts.Requirements.Requirement;
import scripts.Requirements.Util.ConditionalStep;
import scripts.Requirements.Util.Conditions;
import scripts.Requirements.Util.LogicType;
import scripts.Tasks.Priority;
import scripts.Timer;

import java.util.*;

public class BlackKnight  implements QuestTask {

    private static BlackKnight quest;

    public static BlackKnight get() {
        return quest == null ? quest = new BlackKnight() : quest;
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
    RSTile[] path = {
            new RSTile(3023, 3514, 1),
            new RSTile(3022, 3514, 1),
            new RSTile(3021, 3514, 1),
            new RSTile(3020, 3515, 1),
            new RSTile(3019, 3515, 1),
            new RSTile(3018, 3515, 1),
            new RSTile(3017, 3515, 1)
    };
    RSArea northPathToCabbageHole1 = new RSArea(new RSTile(3022, 3517, 1), new RSTile(3030, 3518, 1));
    RSArea northPathToCabbageHole2 = new RSArea(new RSTile(3030, 3510, 1), new RSTile(3030, 3516, 1));
    RSArea northPathToCabbageHole3 = new RSArea(new RSTile(3029, 3516, 1), new RSTile(3029, 3516, 1));

    RSArea cabbageHoleRoom = new RSArea(new RSTile(3026, 3504, 1), new RSTile(3032, 3509, 1));

    RSArea eastTurret = new RSArea(new RSTile(3027, 3505, 3), new RSTile(3031, 3508, 3));


    ItemReq ironChainbody = new ItemReq(ItemID.IRON_CHAINBODY, 1, true, true);
    ItemReq cabbage = new ItemReq(ItemID.CABBAGE, 1);
    ItemReq bronzeMed = new ItemReq(ItemID.BRONZE_MED_HELM, 1, true, true);

    ItemReq teleportFalador = new ItemReq(ItemID.FALADOR_TELEPORT, 5, 1);
    ItemReq food = new ItemReq(ItemID.LOBSTER, 6, 1);

    AreaRequirement inFaladorF1 = new AreaRequirement(whiteKnightsCastleF1);
    AreaRequirement inFaladorF2 = new AreaRequirement(whiteKnightsCastleF2);
    AreaRequirement onTopOfFortress = new AreaRequirement(secretRoomFloor3);
    AreaRequirement inBasement = new AreaRequirement(secretBasement);
    AreaRequirement inEastTurret = new AreaRequirement(eastTurret);
    AreaRequirement inSecretRoomGroundFloor = new AreaRequirement(secretRoomFloor0);
    AreaRequirement inSecretRoomFirstFloor = new AreaRequirement(secretRoomFloor1);
    AreaRequirement inSecretRoomSecondFloor = new AreaRequirement(secretRoomFloor2);
    AreaRequirement inCentralArea2Floor1 = new AreaRequirement(centralArea2Floor1);
    AreaRequirement inMainEntrance = new AreaRequirement(mainEntrance1, mainEntrance2, mainEntrance3, mainEntrance4);
    AreaRequirement inCentralAreaFloor1 = new AreaRequirement(centralArea1Floor1, centralArea2Floor1, centralArea3Floor1);
    AreaRequirement inWestRoomFloor1 = new AreaRequirement(westFloor1);
    AreaRequirement inEastRoomFloor0 = new AreaRequirement(eastRoom1Floor0, eastRoom2Floor0);
    AreaRequirement inEastRoomFloor1 = new AreaRequirement(eastRoom1Floor1, eastRoom2Floor1, eastRoom3Floor1, eastRoom4Floor1);
    AreaRequirement inEastRoomFloor2 = new AreaRequirement(eastRoomFloor2);
    AreaRequirement inListeningRoom = new AreaRequirement(listeningRoom1, listeningRoom2);
    AreaRequirement inPathToCabbageRoom = new AreaRequirement(northPathToCabbageHole1, northPathToCabbageHole2,
            northPathToCabbageHole3);
    AreaRequirement inCabbageHoleRoom = new AreaRequirement(cabbageHoleRoom);


    NPCStep speakToAmik = new NPCStep(4771, new RSTile(2959, 3339, 2));


    /* Path to grill */
   ObjectStep enterFortress = new ObjectStep(ObjectID.STURDY_DOOR, new RSTile(3016, 3514, 0),
            "Open",
            ironChainbody, bronzeMed, cabbage);
    ObjectStep pushWall = new ObjectStep(ObjectID.WALL_2341, new RSTile(3016, 3516, 0),
            "Push");
    ObjectStep climbUpLadder1 = new ObjectStep(ObjectID.LADDER_17148, new RSTile(3015, 3519, 0),
            "Climb-up");
    ObjectStep climbUpLadder2 = new ObjectStep(ObjectID.LADDER_17148, new RSTile(3016, 3519, 1),
            "Climb-up");
    ObjectStep climbDownLadder3 = new ObjectStep(ObjectID.LADDER_17149, new RSTile(3017, 3516, 2),
            "Climb-down");
    ObjectStep climbUpLadder4 = new ObjectStep(ObjectID.LADDER_17148, new RSTile(3023, 3513, 1),
            "Climb-up");
    ObjectStep climbDownLadder5 = new ObjectStep(ObjectID.LADDER_17149, new RSTile(3025, 3513, 2),
            "Climb-down");
    ObjectStep climbDownLadder6 = new ObjectStep(ObjectID.LADDER_17149, new RSTile(3021, 3510, 1),
            "Climb-down");
    ObjectStep listenAtGrill = new ObjectStep(ObjectID.GRILL, new RSTile(3026, 3507, 0),
            "Listen-at");

    ObjectStep openDoor = new ObjectStep(ObjectID.DOOR_14749, new RSTile(3026, 3507, 0),
            "Open", Objects.findNearest(3,ObjectID.DOOR_14749 ).length == 0);

    /* Path to cabbage hole */
  ObjectStep climbUpLadder6 = new ObjectStep(ObjectID.LADDER_17148, new RSTile(3021, 3510, 0),
            "Climb-up", cabbage);
    ObjectStep climbUpLadder5 = new ObjectStep(ObjectID.LADDER_17148, new RSTile(3025, 3513, 1),
            "Climb-up", cabbage);
    ObjectStep climbDownLadder4 = new ObjectStep(ObjectID.LADDER_17149, new RSTile(3023, 3513, 2),
            "Climb-down", cabbage);
    ObjectStep climbUpLadder3 = new ObjectStep(ObjectID.LADDER_17148, new RSTile(3017, 3516, 1),
            "Climb-up", cabbage);
    ObjectStep climbDownLadder2 = new ObjectStep(ObjectID.LADDER_17149, new RSTile(3016, 3519, 2),
            "Climb-down", cabbage);
    ObjectStep climbDownLadder1 = new ObjectStep(ObjectID.LADDER_17149, new RSTile(3015, 3519, 1),
            "Climb-down", cabbage);
    ObjectStep openDoorToRoom = new ObjectStep(2338, new RSTile(3020, 3515, 0), "Open");
    ObjectStep goUpLadderToCabbageZone = new ObjectStep(ObjectID.LADDER_17159,
            new RSTile(3022, 3517, 0),
            "Climb-up", cabbage);

    ObjectStep pushWall2 = new ObjectStep(ObjectID.WALL_2341, new RSTile(3030, 3510, 1),
            "Push", cabbage);
    UseItemOnObjectStep useCabbageOnHole = new UseItemOnObjectStep(ItemID.CABBAGE, 2336,
            new RSTile(3031, 3507, 1));
    ObjectStep exitEastTurret = new ObjectStep(17155, new RSTile(3029, 3507, 3),
            "Climb-down");
    ObjectStep exitBasement = new ObjectStep(25844, new RSTile(1867, 4244, 0),
            "Leave the basement to continue.");
    ObjectStep exitTopOfFortress = new ObjectStep(17155, new RSTile(3010, 3516, 3),
            "Leave the basement to continue.");
    ObjectStep exitWestRoomFirstFloor = new ObjectStep(17155, new RSTile(3011, 3515, 1),
            "Go back downstairs to continue");

    ObjectStep goBackDownFromCabbageZone = new ObjectStep(ObjectID.LADDER_17160, new RSTile(3022, 3518, 1),
            "Climb-down");
    NPCStep returnToAmik = new NPCStep(4771, new RSTile(2959, 3339, 2),
            "Return to Sir Amik Varze in Falador Castle to complete the quest.");

    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.CABBAGE, 1, 300),
                    new GEItem(ItemID.IRON_CHAINBODY, 1, 300),
                    new GEItem(ItemID.BRONZE_MED_HELM, 1, 300),
                    new GEItem(ItemID.FALADOR_TELEPORT, 7, 30),
                    new GEItem(ItemID.LOBSTER, 10, 50),
                    new GEItem(ItemID.AMULET_OF_GLORY[2], 1, 30),
                    new GEItem(ItemID.COMBAT_BRACELET6, 1, 30),
                    new GEItem(ItemID.STAMINA_POTION[0], 2, 15),
                    new GEItem(ItemID.RING_OF_WEALTH[0], 1, 25)
            )
    );



    public void buyItems() {
        cQuesterV2.status = "Buying Items";
        General.println("[Debug]: " + cQuesterV2.status);
        if (Skills.getCurrentLevel(Skills.SKILLS.ATTACK) > 30) {
            itemsToBuy.add(new GEItem(ItemID.ADAMANT_SCIMITAR, 1, 300));
        } else if (Skills.getCurrentLevel(Skills.SKILLS.ATTACK) > 20) {
            itemsToBuy.add(new GEItem(ItemID.MITHRIL_SCIMITAR, 1, 300));
        } else {
            itemsToBuy.add(new GEItem(ItemID.IRON_SCIMITAR, 1, 300));
        }
        BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);
        buyStep.buyItems();

    }

    public void getItems() {
        cQuesterV2.status = "Getting Items";
        General.println("[Debug]: " + cQuesterV2.status);
        BankManager.open(true);
        BankManager.depositAll(true);
        BankManager.depositEquipment();
        BankManager.checkEquippedGlory();
        BankManager.checkCombatBracelet();
        BankManager.withdraw(4, true, ItemID.FALADOR_TELEPORT);
        BankManager.withdraw(1, true, ItemID.IRON_CHAINBODY);
        BankManager.withdraw(2, true, ItemID.COMBAT_BRACELET6);
        BankManager.withdraw(1, true, ItemID.BRONZE_MED_HELM);
        BankManager.withdraw(1, true, ItemID.CABBAGE);
        BankManager.withdraw(1, true, BankManager.STAMINA_POTION[0]);
        BankManager.withdraw(15, true, ItemID.LOBSTER);
        if (Skills.getCurrentLevel(Skills.SKILLS.ATTACK) > 30) {
            BankManager.withdraw(1, true, ItemID.ADAMANT_SCIMITAR);
            Utils.equipItem(ItemID.ADAMANT_SCIMITAR);
        } else if (Skills.getCurrentLevel(Skills.SKILLS.ATTACK) > 20) {
            BankManager.withdraw(1, true, ItemID.MITHRIL_SCIMITAR);
            Utils.equipItem(ItemID.MITHRIL_SCIMITAR);
        } else {
            BankManager.withdraw(1, true, ItemID.IRON_SCIMITAR);
            Utils.equipItem(ItemID.IRON_SCIMITAR);
        }
        BankManager.close(true);
        Utils.equipItem(ItemID.BRONZE_MED_HELM);
        Utils.equipItem(ItemID.IRON_CHAINBODY);
    }
    public Map<Integer, QuestStep> loadSteps() {
        speakToAmik.addDialogStep("I seek a quest!");
        speakToAmik.addDialogStep("I laugh in the face of danger!");
        speakToAmik.addDialogStep("Ok, I'll do my best.", "Yes.");
       openDoorToRoom.setHandleChat(true);
        openDoorToRoom.setChat(List.of("I don't care. I'm going in anyway."));
       listenAtGrill.setHandleChat(true);
        Map<Integer, QuestStep> steps = new HashMap<>();


        ConditionalStep goStartQuest = new ConditionalStep(speakToAmik);
        steps.put(0, goStartQuest);

        ConditionalStep infiltrateTheFortress = new ConditionalStep(enterFortress);
        infiltrateTheFortress.addStep(inListeningRoom, listenAtGrill);
        infiltrateTheFortress.addStep(inEastRoomFloor1, climbDownLadder6);
        infiltrateTheFortress.addStep(inEastRoomFloor2, climbDownLadder5);
        infiltrateTheFortress.addStep(inCentralAreaFloor1, climbUpLadder4);
        infiltrateTheFortress.addStep(inSecretRoomSecondFloor, climbDownLadder3);
        infiltrateTheFortress.addStep(inSecretRoomFirstFloor, climbUpLadder2);
        infiltrateTheFortress.addStep(inSecretRoomGroundFloor, climbUpLadder1);
        infiltrateTheFortress.addStep(new Conditions(false, LogicType.OR, inMainEntrance,
                inEastRoomFloor0), pushWall);
       infiltrateTheFortress.addStep(inWestRoomFloor1, exitWestRoomFirstFloor);
        infiltrateTheFortress.addStep(inBasement, exitBasement);
        infiltrateTheFortress.addStep(onTopOfFortress, exitTopOfFortress);
        infiltrateTheFortress.addStep(inEastTurret, exitEastTurret);
        if (inMainEntrance.check() && !inCabbageHoleRoom.check())
            openDoorToRoom.execute();
        infiltrateTheFortress.addStep(new Conditions(false,
                        LogicType.OR, inCabbageHoleRoom, inPathToCabbageRoom),
                goBackDownFromCabbageZone);

        steps.put(1, infiltrateTheFortress);

        ConditionalStep sabotageThePotion = new ConditionalStep(enterFortress);
        sabotageThePotion.addStep(new Conditions(false, LogicType.OR,
                inSecretRoomGroundFloor, inMainEntrance, inEastRoomFloor0), goUpLadderToCabbageZone);
        sabotageThePotion.addStep(inCabbageHoleRoom, useCabbageOnHole);
        sabotageThePotion.addStep(inPathToCabbageRoom, pushWall2);
        sabotageThePotion.addStep(inSecretRoomFirstFloor, climbDownLadder1);
        sabotageThePotion.addStep(inSecretRoomSecondFloor, climbDownLadder2);
      //  sabotageThePotion.addStep(inCentralArea2Floor1, openDoor );
        sabotageThePotion.addStep(inCentralAreaFloor1,  climbUpLadder3);
        sabotageThePotion.addStep(inEastRoomFloor2, climbDownLadder4);
        sabotageThePotion.addStep(inEastRoomFloor1, climbUpLadder5);
        sabotageThePotion.addStep(inListeningRoom, climbUpLadder6);
        sabotageThePotion.addStep(inWestRoomFloor1, exitWestRoomFirstFloor);
        sabotageThePotion.addStep(inBasement, exitBasement);
        sabotageThePotion.addStep(onTopOfFortress, exitTopOfFortress);
        sabotageThePotion.addStep(inEastTurret, exitEastTurret);

        steps.put(2, sabotageThePotion);

        ConditionalStep goFinishQuest = new ConditionalStep(returnToAmik);

        steps.put(3, goFinishQuest);

        return steps;
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
        Log.debug("BKF");
        Waiting.waitNormal(500,10);
        int gameSetting = GameState.getSetting(QuestVarPlayer.QUEST_BLACK_KNIGHTS_FORTRESS.getId());
        if (gameSetting == 4) {
            Utils.closeQuestCompletionWindow();
            cQuesterV2.taskList.remove(this);
            return;
        }
        if (gameSetting == 0) {
            buyItems();
            getItems();
        }
        if (centralArea1Floor1.contains(Player.getPosition()) ||
                centralArea3Floor1.contains(Player.getPosition()) || centralArea2Floor1.contains(Player.getPosition())){
            Log.debug("Walking path");
            WalkerEngine.getInstance().walkPath(Arrays.asList(path));
        }
        if (inCabbageHoleRoom.check()) {
            Log.debug("In cabbage room");
            useCabbageOnHole.execute();
        }
        Map<Integer, QuestStep> steps = loadSteps();
        Optional<QuestStep> step = Optional.ofNullable(steps.get(gameSetting));
        step.ifPresent(QuestStep::execute);
        if (NPCInteraction.isConversationWindowUp())
            NPCInteraction.handleConversation("I don't care. I'm going in anyway.");
        else
            Waiting.waitNormal(150,25);
    }

    @Override
    public String questName() {
        int gameSetting = GameState.getSetting(QuestVarPlayer.QUEST_BLACK_KNIGHTS_FORTRESS.getId());
        return "BKF (" + gameSetting + ")";
    }

    @Override
    public boolean checkRequirements() {
        return true;
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
