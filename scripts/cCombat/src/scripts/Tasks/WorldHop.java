package scripts.Tasks;

import scripts.Listeners.PkListener;

public class WorldHop implements Task, PkListener {



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
    }

    @Override
    public void execute() {

    }

    @Override
    public void onPkerNearby() {
        // valiate and hop?
    }
}
