package scripts.Tasks.Agility.Tasks.Falador;

import org.tribot.api2007.Player;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.AgilityAreas;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.PathingUtil;
import scripts.Tasks.Agility.AgilityAPI.AgilUtils;

public class GoToFaladorStart implements Task {

    @Override
    public String toString() {
        return "Going to Falador Start";
    }


    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        return Vars.get().currentTask != null &&
                Vars.get().currentTask.equals(SkillTasks.AGILITY) &&
                AgilUtils.isWithinLevelRange(50, 60) &&
                Player.getPosition().getPlane() == 0 &&
                !AgilityAreas.FALADOR_FINISH_AREA.contains(Player.getPosition());
    }

    @Override
    public void execute() {

        if (!PathingUtil.localNavigation(AgilityAreas.FALADOR_FINISH_AREA))
            PathingUtil.walkToArea(AgilityAreas.FALADOR_FINISH_AREA, false);
    }

    @Override
    public String taskName() {
        return "Agility - Falador";
    }
}
