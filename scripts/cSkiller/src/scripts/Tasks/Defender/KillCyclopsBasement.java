package scripts.Tasks.Defender;

import scripts.API.Priority;
import scripts.API.Task;

public class KillCyclopsBasement implements Task {

    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return false;
    }

    @Override
    public void execute() {

    }
    @Override
    public String toString() {
        return "Killing Basement Cyclops";
    }
    @Override
    public String taskName() {
        return "Defender";
    }
}
