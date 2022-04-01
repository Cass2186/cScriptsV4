package scripts.QuestPackages.LunarDiplomacy;

import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.Quest;
import scripts.ObjectID;
import scripts.QuestSteps.ObjectStep;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.ItemRequirement;
import scripts.Requirements.Requirement;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;
import scripts.Utils;

import java.util.List;

public class NumberChallenge implements QuestTask {


    //DetailedQuestStep press0, press1, press2, press3, press4, press5, press6, press7, press8, press9, catchStep;

    ObjectStep   press0 = new ObjectStep( ObjectID.ZERO, new RSTile(1783, 5062, 2));
    ObjectStep    press1 = new ObjectStep( ObjectID.ONE, new RSTile(1786, 5065, 2));
    ObjectStep  press2 = new ObjectStep( ObjectID.TWO, new RSTile(1787, 5063, 2));
    ObjectStep press3 = new ObjectStep( ObjectID.THREE, new RSTile(1786, 5061, 2));
    ObjectStep   press4 = new ObjectStep( ObjectID.FOUR, new RSTile(1784, 5060, 2));
    ObjectStep  press5 = new ObjectStep( ObjectID.FIVE, new RSTile(1781, 5061, 2));
    ObjectStep  press6 = new ObjectStep( ObjectID.SIX, new RSTile(1780, 5063, 2));
    ObjectStep   press7 = new ObjectStep( ObjectID.SEVEN, new RSTile(1781, 5065, 2));
    ObjectStep  press8 = new ObjectStep( ObjectID.EIGHT, new RSTile(1782, 5066, 2));
    ObjectStep  press9 = new ObjectStep( ObjectID.NINE, new RSTile(1784, 5067, 2));

    ObjectStep catchStep;

    private ObjectStep getStepFromState(ObjectStep choice1, ObjectStep choice2) {
        if (Utils.getVarBitValue(2421) == 0) {
            return choice1;
        }
        else {
            return choice2;
        }
    }

    public void updateSteps(){
        switch (Utils.getVarBitValue(2417)) {
            case 0:
                getStepFromState(press7, press9);
                break;
            case 1:
                getStepFromState(press3, press7);
                break;
            case 2:
                getStepFromState(press6, press7);
                break;
            case 3:
                getStepFromState(press1, press5);
                break;
            case 4:
                getStepFromState(press2, press0);
                break;
            case 5:
                getStepFromState(press4, press5);
                break;
            case 6:
                getStepFromState(press1, press6);
                break;
            case 7:
            case 8:
                getStepFromState(press3, press4);
                break;
            case 9:
                getStepFromState(press3, press6);
                break;
            case 10:
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
