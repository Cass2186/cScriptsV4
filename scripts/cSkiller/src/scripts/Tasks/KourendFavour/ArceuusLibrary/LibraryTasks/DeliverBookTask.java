package scripts.Tasks.KourendFavour.ArceuusLibrary.LibraryTasks;

import scripts.API.Priority;
import scripts.API.Task;
import scripts.Tasks.KourendFavour.ArceuusLibrary.LibraryUtils;
import scripts.Tasks.KourendFavour.ArceuusLibrary.State;

public class DeliverBookTask implements Task {

    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        return State.get().getCurrentAssignment().isPresent()
                && State.get().getCurrentBooks().contains(State.get().getCurrentAssignment().get());
    }

    @Override
    public void execute() {
        if (LibraryUtils.helpProfessor(State.get().getCurrentProfessor()))
            LibraryUtils.waitAfterWalking(() ->
                    State.get().getCurrentAssignment().isEmpty(), 1000);
    }

    @Override
    public String taskName() {
        return "Arceuus Library";
    }

    @Override
    public String toString() {
        return "Delivering book";
    }


}