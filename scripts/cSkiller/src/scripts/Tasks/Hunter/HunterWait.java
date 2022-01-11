package scripts.Tasks.Hunter;

import scripts.API.Priority;
import scripts.API.Task;

public class HunterWait implements Task {
    @Override
    public Priority priority() {
        return null;
    }

    @Override
    public boolean validate() {
        return false;
    }

    @Override
    public void execute() {

    }

    @Override
    public String taskName() {
        return "Hunter: waiting";
    }
}
