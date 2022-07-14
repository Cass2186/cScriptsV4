package scripts.Tasks.MiscTasks;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.Combat;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Waiting;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.Timer;

public class Afk implements Task {

    @Override
    public String toString() {
        return "AFKing";
    }

    @Override
    public Priority priority() {
        return Priority.AFK_HIGHEST;
    }

    @Override
    public boolean validate() {
        return Vars.get().currentTask != null &&
                !Vars.get().currentTask.equals(SkillTasks.PEST_CONTROL) &&
                !Vars.get().afkTimer.isRunning();
    }

    @Override
    public void execute() {
        int length = General.randomSD(Vars.get().afkDurationAvg, Vars.get().afkDurationSD);
        Log.info("AFKing for: " + Timing.msToString(length));
        Waiting.waitUntil(length,600, ()-> {
            Waiting.waitNormal(500,150);
            if (Mouse.isInBounds() && Vars.get().moveMouseOffScreenAfk){
                Mouse.leaveGame();
            }
            return Combat.isUnderAttack() || Combat.isInWilderness();
        });
        Vars.get().afkTimer = new Timer(General.randomSD(Vars.get().afkFrequencyAvg, Vars.get().afkFrequencySD));
    }

    @Override
    public String taskName() {
        return "AFK";
    }
}
