package scripts.QuestPackages.KourendFavour.ArceuusLibrary.LibraryTasks;

import org.tribot.script.sdk.Waiting;

import scripts.QuestPackages.KourendFavour.ArceuusLibrary.LibraryUtils;
import scripts.QuestPackages.KourendFavour.ArceuusLibrary.Professor.Professor;
import scripts.QuestPackages.KourendFavour.ArceuusLibrary.State;
import scripts.QuestSteps.QuestTask;

import scripts.Requirements.ItemRequirement;
import scripts.Requirements.Requirement;
import scripts.Tasks.Priority;

import java.util.List;

public class GetNewAssignmentTask implements QuestTask {

  //  private static final JustLogger LOGGER = new JustLogger(GetNewAssignmentTask.class);
  private static GetNewAssignmentTask quest;

    public static GetNewAssignmentTask get() {
        return quest == null ? quest = new GetNewAssignmentTask() : quest;
    }
    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public void execute() {

        State.get().setStatus("Getting new task");
   //     LOGGER.info("Getting new task");
        Professor nextProfessor = State.get().getCurrentProfessor();

        if(LibraryUtils.helpProfessor(nextProfessor))
                Waiting.waitUntil(7000, () -> State.get().getCurrentAssignment().isPresent()
                || nextProfessor != State.get().getCurrentProfessor());
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
        return "Getting new task";
    }

    @Override
    public boolean validate() {
        return !State.get().getCurrentAssignment().isPresent();
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