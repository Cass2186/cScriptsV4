package scripts.Tasks.Combat;

import org.tribot.script.sdk.Combat;
import org.tribot.script.sdk.Skill;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.Tasks.BirdHouseRuns.Data.Paint;

public class KillCrabs implements Task {

    private boolean setAttackStyle() {
        if (Vars.get().currentTask == null)
            return false;

        else if (Vars.get().currentTask.equals(SkillTasks.ATTACK)) {
            return Combat.setAttackStyle(Combat.AttackStyle.ACCURATE);

        } else if (Vars.get().currentTask.equals(SkillTasks.STRENGTH)) {
            return Combat.setAttackStyle(Combat.AttackStyle.AGGRESSIVE);

        } else if (Vars.get().currentTask.equals(SkillTasks.DEFENCE)) {
            return Combat.setAttackStyle(Combat.AttackStyle.DEFENSIVE);

        } else if (Vars.get().currentTask.equals(SkillTasks.RANGED)) {
            return Combat.setAttackStyle(Combat.AttackStyle.RAPID);
        } else {
            return Combat.setAttackStyle(Combat.AttackStyle.AGGRESSIVE);
        }
    }

    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return Vars.get().currentTask != null &&
                (Vars.get().currentTask.equals(SkillTasks.ATTACK) ||
                        Vars.get().currentTask.equals(SkillTasks.STRENGTH) ||
                        Vars.get().currentTask.equals(SkillTasks.DEFENCE) ||
                        Vars.get().currentTask.equals(SkillTasks.RANGED));
    }

    @Override
    public void execute() {
        setAttackStyle();


    }

    @Override
    public String taskName() {
        return "Combat Training";
    }
}
