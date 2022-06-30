package scripts;

import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Skill;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.input.Mouse;
import org.tribot.script.sdk.script.ScriptConfig;
import org.tribot.script.sdk.script.TribotScript;
import org.tribot.script.sdk.script.TribotScriptManifest;
import scripts.Data.Paint;
import scripts.Data.Vars;
import scripts.ScriptUtils.CassScript;
import scripts.Tasks.Task;
import scripts.Tasks.TaskSet;
@TribotScriptManifest(author = "Cass2186", name = "cHerbRuns", category = "Testing")
public class cHerbRuns extends CassScript implements TribotScript {

    @Override
    public void configure(ScriptConfig config) {
        TribotScript.super.configure(config);
    }

    @Override
    public void execute(String args) {
        AntiBan.create();
        super.initializeDax();
        Mouse.setClickMethod(Mouse.ClickMethod.TRIBOT_DYNAMIC);

        Paint.addMainPaint();
        //Tasks
        TaskSet tasks = new TaskSet(

        );

        while (super.isRunning.get()) {
            Waiting.waitNormal(75,25);

            //reset safety timer if we've gained xp since last itteration
            if (Skill.FARMING.getXp() > Vars.get().lastIterFarmXp) {
                Vars.get().safetyTimer.reset();
                Vars.get().lastIterFarmXp = Skill.FARMING.getXp();
            }

            // if we have not gained exp and the timer has expired, end script
            if (!Vars.get().safetyTimer.isRunning()) {
                Log.error("XP Safety timer timed out, ending");
                break;
            }

            Task task = tasks.getValidTask();
            if (task != null) {
                Vars.get().status = task.toString();
                task.execute();
            }
        }
    }


}
