package scripts.Tasks.MiscTasks;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.Combat;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Login;
import org.tribot.script.sdk.Waiting;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.Timer;

public class MiniBreak implements Task {

    /**
     * Difference b/w minibreak and AFK is this is longer and logs out
     */


    private static MiniBreak quest;

    public static MiniBreak get() {
        return quest == null ? quest = new MiniBreak() : quest;
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
        Login.logout();
        int length = General.randomSD(Vars.get().miniBreakDurationMin, Vars.get().miniBreakDurationMax,
                Vars.get().miniBreakDurationAvg, Vars.get().miniBreakDurationSD);
        Log.log("[Debug]: Minibreak for: " + Timing.msToString(length));
        Timer.waitCondition(()-> {
            Waiting.waitNormal(1000,5000);
            if (Mouse.isInBounds() && Vars.get().moveMouseOffScreenAfk){
                Mouse.leaveGame();
            }
            return Combat.isUnderAttack();
        },length);
        Login.login();
         Vars.get().miniBreakTimer = new Timer(General.randomSD(Vars.get().miniBreakFrequencyAvg*1000,
                 Vars.get().miniBreakFrequencySD*1000));
    }


    @Override
    public String taskName() {
        return null;
    }
}
