package scripts.QuestSteps;

import scripts.Requirements.InventoryRequirement;
import scripts.Requirements.ItemRequirement;
import scripts.Requirements.Requirement;
import scripts.Tasks.Priority;

import java.util.List;

public interface QuestTask {

    Priority priority();

    boolean validate();

    void execute();

    String questName();

    boolean checkRequirements();

    List<Requirement> getGeneralRequirements();

    List<ItemRequirement> getBuyList();


}