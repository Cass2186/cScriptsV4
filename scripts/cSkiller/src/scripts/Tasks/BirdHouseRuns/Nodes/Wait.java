package scripts.Tasks.BirdHouseRuns.Nodes;

import org.tribot.api.General;
import org.tribot.api2007.Login;
import org.tribot.script.sdk.Log;
import scripts.Data.Vars;
import scripts.Tasks.BirdHouseRuns.Data.BirdHouseVars;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;

public class Wait implements Task {


    @Override
    public void execute() {
        Log.debug("Waiting for birdhouses");
        if (Login.getLoginState().equals(Login.STATE.INGAME)) {
            int i = General.random(45000, 125000);
            General.println("[Waiting]: Sleeping for " + i + "ms");
            General.sleep(i);
            Login.logout();
        } else {
            while (BirdHouseVars.get().nextRunTimer.isRunning())
                General.sleep(General.random(45000, 125000));
        }
        Vars.get().currentTime = System.currentTimeMillis();
    }

    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return true;
    }
}
