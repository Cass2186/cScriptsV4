package scripts;

import org.jetbrains.annotations.NotNull;
import org.tribot.script.ScriptManifest;
import org.tribot.script.sdk.*;
import org.tribot.script.sdk.input.Mouse;
import org.tribot.script.sdk.script.TribotScript;
import scripts.Data.Paint;
import scripts.Data.Vars;
import scripts.ScriptUtils.CassScript;
import scripts.Tasks.*;


@ScriptManifest(name = "cChompyKiller", authors = {"Cass2186"}, category = "Mini games", version = 0.1)
public class cChompyKiller extends CassScript implements TribotScript {


    @Override
    public void execute(@NotNull String args) {
        AntiBan.create();
        super.initializeDax();
        Mouse.setClickMethod(Mouse.ClickMethod.ACCURATE_MOUSE);
        Utils.setCameraZoomAboveDefault();

        int m = Utils.random(110, 150);
        Log.info("Set mouse speed to " + m);
        Mouse.setSpeed(m);

        Paint.addMainPaint();

        //Tasks
        TaskSet tasks = new TaskSet(
                new DropToads(),
                new InflateToads(),
                new KillBirds()
        );

        MessageListening.addServerMessageListener(message -> {
            if (message.toLowerCase().contains("you scratch a notch")) {
                Vars.get().kills++;
            }
        });
        ScriptListening.addEndingListener(() -> {
            Log.info(String.format("Kills %s | %s /hr", Vars.get().kills, Vars.get().getKillsHr()));
        });
        while (super.isRunning.get()) {
            Waiting.waitNormal(75, 10);
            if (!super.checkBasicShouldRunChecks())
                break;
            //reset safety timer if we've gained xp since last itteration
            if (Skill.RANGED.getXp() > Vars.get().lastIterRangedXp) {
                Vars.get().safetyTimer.reset();
                Vars.get().lastIterRangedXp = Skill.RANGED.getXp();
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
