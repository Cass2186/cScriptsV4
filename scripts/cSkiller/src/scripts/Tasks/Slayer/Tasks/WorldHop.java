package scripts.Tasks.Slayer.Tasks;


import scripts.API.Priority;
import scripts.API.Task;

public class WorldHop implements Task {



    @Override
    public String toString() {
        return "World hopping";
    }

    @Override
    public Priority priority() {
        return Priority.HIGH;
    }

    @Override
    public boolean validate() {
        return false;
    } //

    @Override
    public void execute() {

    }

    @Override
    public String taskName() {
        return null;
    }
}
