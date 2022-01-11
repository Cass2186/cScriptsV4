package scripts.Tasks.Slayer.Tasks;

import scripts.API.Priority;
import scripts.API.Task;

public class CancelSlayerTask implements Task {

    final static int SLAYER_POINTS_VARBIT = 4068;


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
        return null;
    }
}
