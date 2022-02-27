package scripts.Tasks.Ranging;

import org.tribot.script.sdk.GameState;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Timer;

public class CannonBattle implements Task {

    public void fight(){


        Timer.waitCondition(()-> GameState.getSetting(CannonHandler.CBALLS_LEFT_SETTING)
                <= CannonHandler.fillCannonAt, 50000,75000);
    }
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
    public String taskName() {
        return "Ranged";
    }
}
