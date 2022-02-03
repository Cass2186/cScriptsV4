package scripts.API;

import org.tribot.api.General;

import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.cSkiller;

import java.awt.*;

public class SkillSwitchThread extends Thread {


    @Override
    public void run() {
        while (cSkiller.isRunning.get()) {
            General.sleep(20, 40);
            if (Vars.get().isOnBreak) {
                Long remaining = Vars.get().skillSwitchTimer.getRemaining();
                Vars.get().skillSwitchTimer.setEndIn(Vars.get().breakLength + remaining);
                General.sleep(5000, 10000);
            } else if (!Vars.get().skillSwitchTimer.isRunning()) {
                General.println("[Debug]: Skill timer has ended, resetting current task");
                Vars.get().skillSwitchTimer.reset();
                Vars.get().prevTask = Vars.get().currentTask;
                Vars.get().currentTask = SkillTasks.getSkillTask();
                General.println("[Debug]: SwitchTask boolean turned to true", Color.RED);
                Vars.get().switchingTasks = true;
                General.sleep(5000);
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
