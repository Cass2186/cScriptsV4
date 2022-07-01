package scripts.Nodes.TitheFarm;

import scripts.Tasks.Priority;
import scripts.Tasks.Task;

public class GetWater implements Task {
    @Override
    public Priority priority() {
        return Priority.HIGH;
    }

    @Override
    public boolean validate() {
        return false;
    }

    @Override
    public void execute() {

    }
}
