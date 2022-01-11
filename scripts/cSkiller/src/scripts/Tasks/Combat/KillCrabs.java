package scripts.Tasks.Combat;

import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;

public class KillCrabs implements Task {


    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return Vars.get().currentTask != null &&
                Vars.get().currentTask.equals(SkillTasks.ATTACK) ||
                Vars.get().currentTask.equals(SkillTasks.STRENGTH) ||
                Vars.get().currentTask.equals(SkillTasks.DEFENCE) ||
                Vars.get().currentTask.equals(SkillTasks.RANGED);
    }

    @Override
    public void execute() {

    }

    @Override
    public String taskName() {
        return "Combat Training";
    }
}
