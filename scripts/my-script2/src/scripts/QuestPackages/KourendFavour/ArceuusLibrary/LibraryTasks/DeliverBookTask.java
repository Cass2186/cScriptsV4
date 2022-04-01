package scripts.QuestPackages.KourendFavour.ArceuusLibrary.LibraryTasks;


import scripts.QuestPackages.KourendFavour.ArceuusLibrary.LibraryUtils;
import scripts.QuestPackages.KourendFavour.ArceuusLibrary.State;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.ItemRequirement;
import scripts.Requirements.Requirement;
import scripts.Tasks.Priority;

import java.util.List;


public class DeliverBookTask implements QuestTask {
    private static DeliverBookTask quest;

    public static DeliverBookTask get() {
        return quest == null ? quest = new DeliverBookTask() : quest;
    }

    @Override
    public boolean isComplete() {
        return false;
    }
    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        return State.get().getCurrentAssignment().isPresent()
                && State.get().getCurrentBooks() != null
                && State.get().getCurrentBooks().contains(State.get().getCurrentAssignment().get());
    }

    @Override
    public void execute() {
        if (LibraryUtils.helpProfessor(State.get().getCurrentProfessor()))
            LibraryUtils.waitAfterWalking(() ->
                    !State.get().getCurrentAssignment().isPresent(), 1000);
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
    public String toString() {
        return "Delivering book";
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