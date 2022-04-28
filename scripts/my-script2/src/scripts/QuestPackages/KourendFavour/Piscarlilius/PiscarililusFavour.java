package scripts.QuestPackages.KourendFavour.Piscarlilius;

import org.tribot.api2007.Inventory;
import scripts.ItemID;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.ItemRequirement;
import scripts.Requirements.Requirement;
import scripts.Tasks.Priority;
import scripts.cQuesterV2;

import java.util.List;

public class PiscarililusFavour implements QuestTask {
    private static FixCrane quest;

    public static FixCrane get() {
        return quest == null ? quest = new FixCrane() : quest;
    }


    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return cQuesterV2.taskList.size() > 0 &&
                cQuesterV2.taskList.get(0).equals(this);
    }

    @Override
    public void execute() {
        if (UnnotePlanks.get().validate())
            UnnotePlanks.get().execute();

        if (FixCrane.get().validate())
            FixCrane.get().execute();
    }

    @Override
    public String questName() {
        return "Piscarililus Favour";
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
