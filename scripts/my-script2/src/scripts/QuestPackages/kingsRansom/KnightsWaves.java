package scripts.QuestPackages.kingsRansom;

import scripts.QuestSteps.QuestTask;
import scripts.Requirements.ItemRequirement;
import scripts.Requirements.Requirement;
import scripts.Tasks.Priority;
import scripts.cQuesterV2;

import java.util.List;

public class KnightsWaves implements QuestTask {


    private static KnightsWaves quest;

    public static KnightsWaves get() {
        return quest == null ? quest = new KnightsWaves() : quest;
    }


    @Override
    public Priority priority() {
        return null;
    }

    @Override
    public boolean validate() {
        return cQuesterV2.taskList.size() > 0 && cQuesterV2.taskList.get(0).equals(this);
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
        return false;
    }
}
