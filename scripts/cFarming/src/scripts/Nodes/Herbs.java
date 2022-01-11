package scripts.Nodes;

import scripts.Tasks.Priority;
import scripts.Tasks.Task;


public class Herbs implements Task {
    @Override
    public void execute() {

    }

    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return false;
    }
}
