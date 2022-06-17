package scripts.Tasks;

import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.util.DPathNavigator;
import scripts.Timer;

public class MoveToPosition implements Task{

    Timer movementTimer = new Timer(0);

    @Override
    public String toString() {
        return "Moving areas";
    }

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
}
