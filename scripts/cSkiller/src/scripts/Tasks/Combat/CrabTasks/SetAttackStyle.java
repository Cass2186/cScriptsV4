package scripts.Tasks.Combat.CrabTasks;

import org.tribot.api2007.Skills;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.CombatTask;
import scripts.Data.Vars;

public class SetAttackStyle implements Task {

    public int getAttackIndexFromCombatTask(CombatTask task) {
        CombatTask combatTask = Vars.get().combatTaskList.get(0);
        if (combatTask.getSkill() == Skills.SKILLS.ATTACK) {

        } else if (combatTask.getSkill() == Skills.SKILLS.STRENGTH) {

        }
        else if (combatTask.getSkill() == Skills.SKILLS.DEFENCE) {

        }else if (combatTask.getSkill() == Skills.SKILLS.RANGED) {

        }

        return 1;
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
    public String taskName() {
        return null;
    }
}
