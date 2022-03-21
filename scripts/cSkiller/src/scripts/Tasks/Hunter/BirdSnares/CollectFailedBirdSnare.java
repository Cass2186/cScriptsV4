package scripts.Tasks.Hunter.BirdSnares;

import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.Tasks.Hunter.HunterData.HunterVars;
import scripts.Tasks.Hunter.HunterData.TrapTypes;

public class CollectFailedBirdSnare implements Task {
    @Override
    public Priority priority() {
        return Priority.HIGH;
    }

    @Override
    public boolean validate() {
        if (Vars.get().currentTask != null && Vars.get().currentTask.equals(SkillTasks.HUNTER)) {
            if (HunterVars.get().currentTrapType.equals(TrapTypes.BIRD_TRAP)){

            }
        }
        return false;
    }

    @Override
    public void execute() {

    }

    @Override
    public String taskName() {
        return "Hunter";
    }

    @Override
    public String toString() {
        return "Collecting failed birdsnare";
    }
}
