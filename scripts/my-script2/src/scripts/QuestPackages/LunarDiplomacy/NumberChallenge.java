package scripts.QuestPackages.LunarDiplomacy;

import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api2007.Game;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.*;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.LocalTile;
import scripts.NpcID;
import scripts.ObjectID;
import scripts.QuestSteps.NPCStep;
import scripts.QuestSteps.ObjectStep;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.ItemRequirement;
import scripts.Requirements.Requirement;
import scripts.Requirements.Util.Operation;
import scripts.Requirements.VarbitRequirement;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;
import scripts.Utils;

import java.util.List;

public class NumberChallenge implements QuestTask {

   NPCStep startNumber = new NPCStep( NpcID.ETHEREAL_NUMERATOR);


    private static int[] xyzTranslateCoordinates = {9, -7, 0};
    // these are the .translate() coordinates for the launch pad from the centre "My life" piece

    public static LocalTile getNumbersLaunchPadTile(LocalTile centreTile){
        return centreTile.translate(xyzTranslateCoordinates[0],
                xyzTranslateCoordinates[1], xyzTranslateCoordinates[2]);
    }

    public static LocalTile  getMyLifeTile(){
       return  Query.gameObjects().idEquals(ObjectID.MY_LIFE).findClosest()
               .map(o->o.getTile().toLocalTile()).orElse(MyPlayer.getTile().toLocalTile());
    }

    public static LocalTile numberTile(LocalTile centreTile){
        return centreTile.translate(19,
              -13, 0);
    }
    VarbitRequirement finishedNumbers = new VarbitRequirement(2406, 6,Operation.GREATER_EQUAL);
    //DetailedQuestStep press0, press1, press2, press3, press4, press5, press6, press7, press8, press9, catchStep;

    //TODO remove the tiles and add actions
    ObjectStep   press0 = new ObjectStep( ObjectID.ZERO, "Press");
    ObjectStep    press1 = new ObjectStep( ObjectID.ONE, "Press");
    ObjectStep  press2 = new ObjectStep( ObjectID.TWO,"Press");
    ObjectStep press3 = new ObjectStep( ObjectID.THREE, "Press");
    ObjectStep   press4 = new ObjectStep( ObjectID.FOUR, "Press");
    ObjectStep  press5 = new ObjectStep( ObjectID.FIVE,"Press");
    ObjectStep  press6 = new ObjectStep( ObjectID.SIX, "Press");
    ObjectStep   press7 = new ObjectStep( ObjectID.SEVEN, "Press");
    ObjectStep  press8 = new ObjectStep( ObjectID.EIGHT, "Press");
    ObjectStep  press9 = new ObjectStep( ObjectID.NINE, "Press");

    ObjectStep catchStep;

    private void getStepFromState(ObjectStep choice1, ObjectStep choice2) {
        if (Utils.getVarBitValue(2421) == 0) {
            Log.info("Doing choice 1");
            choice1.execute();
        }
        else {
            Log.info("Doing choice 2");
             choice2.execute();
        }
    }

    public void updateSteps(){
        switch (Utils.getVarBitValue(2417)) {
            case 0:
                Log.info("Case 0");
                getStepFromState(press7, press9);
                break;
            case 1:
                Log.info("Case 1");
                getStepFromState(press3, press7);
                break;
            case 2:
                Log.info("Case2");
                getStepFromState(press6, press7);
                break;
            case 3:
                Log.info("Case 3");
                getStepFromState(press1, press5);
                break;
            case 4:
                Log.info("Case 4");
                getStepFromState(press2, press0);
                break;
            case 5:
                Log.info("Case 5");
                getStepFromState(press4, press5);
                break;
            case 6:
                Log.info("Case 6");
                getStepFromState(press1, press6);
                break;
            case 7:
            case 8:
                Log.info("Case 8");
                getStepFromState(press3, press4);
                break;
            case 9:
                Log.info("Case 9");
                getStepFromState(press3, press6);
                break;
            case 10:
                Log.info("Case 10");
                getStepFromState(press3, press1);
                break;
            case 11:
                getStepFromState(press8, press9);
                break;
            case 12:
                getStepFromState(press4, press8);
                break;
            case 13:
            case 15:
                getStepFromState(press5, press1);
                break;
            case 14:
                getStepFromState(press5, press4);
                break;
           // default:
              //  getStepFromState(catchStep);
              //  break;
        }
    }

    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        return Game.isInInstance() && !finishedNumbers.check() &&
                Query.npcs().idEquals(NpcID.ETHEREAL_NUMERATOR).isReachable().isAny();

    }

    @Override
    public void execute() {
        Log.info("Number challenge");
        updateSteps();
        if (ChatScreen.isOpen()) {
            NPCInteraction.handleConversation();
            Waiting.waitNormal(750,125);
        }
    }

    @Override
    public String questName() {
        return "Number challenge";
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

    @Override
    public boolean isComplete() {
        return Quest.LUNAR_DIPLOMACY.getState().equals(Quest.State.COMPLETE);
    }
}
