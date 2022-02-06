package scripts.QuestSteps;

import scripts.Tasks.Priority;

public interface QuestTask {

    Priority priority();

    boolean validate();

    void execute();

    String questName();

    boolean checkRequirements();
   // List<Requirement> getGeneralRequirements()
}