package scripts.Tasks.KourendFavour.ArceuusLibrary.LibraryTasks;

import org.tribot.script.sdk.Waiting;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Tasks.KourendFavour.ArceuusLibrary.LibraryUtils;
import scripts.Tasks.KourendFavour.ArceuusLibrary.Professor.Professor;
import scripts.Tasks.KourendFavour.ArceuusLibrary.State;

public class GetNewAssignmentTask implements Task {

  //  private static final JustLogger LOGGER = new JustLogger(GetNewAssignmentTask.class);

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
    public String taskName() {
        return "Arceuus Library";
    }

    @Override
    public String toString() {
        return "Getting new task";
    }

    @Override
    public boolean validate() {
        return !State.get().getCurrentAssignment().isPresent();
    }

}