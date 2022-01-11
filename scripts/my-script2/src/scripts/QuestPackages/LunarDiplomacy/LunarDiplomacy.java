package scripts.QuestPackages.LunarDiplomacy;

import org.tribot.api2007.types.RSTile;
import scripts.ObjectID;
import scripts.QuestSteps.ObjectStep;
import scripts.QuestSteps.QuestTask;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;

public class LunarDiplomacy implements QuestTask {


    ObjectStep goToNumbers = new ObjectStep(ObjectID.PLATFORM_16633, new RSTile(1768, 5080, 2));
    ObjectStep  goToMimic = new ObjectStep( ObjectID.PLATFORM_16632, new RSTile(1765, 5079, 2));
    ObjectStep goToRace = new ObjectStep( ObjectID.PLATFORM_16634, new RSTile(1770, 5088, 2));
    ObjectStep goToMemory = new ObjectStep( ObjectID.PLATFORM_16636, new RSTile(1751, 5095, 2));
    ObjectStep goToTrees = new ObjectStep( ObjectID.PLATFORM_16635, new RSTile(1764, 5098, 2));
    ObjectStep goToChance = new ObjectStep( ObjectID.PLATFORM_16637, new RSTile(1751, 5080, 2));

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
