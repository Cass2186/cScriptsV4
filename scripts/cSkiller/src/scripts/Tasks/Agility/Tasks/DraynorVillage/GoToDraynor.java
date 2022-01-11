package scripts.Tasks.Agility.Tasks.DraynorVillage;

import org.tribot.api2007.Player;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.AgilityAreas;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.PathingUtil;
import scripts.Tasks.Agility.AgilityAPI.AgilUtils;


public class GoToDraynor implements Task {

    @Override
    public String toString(){
        return "Going to Draynor course";
    }


    @Override
    public Priority priority() {
        return Priority.HIGH;
    }

    @Override
    public boolean validate() {
        return Vars.get().currentTask != null &&
                Vars.get().currentTask.equals(SkillTasks.AGILITY) &&
                AgilUtils.isWithinLevelRange(10, 30) &&
                Player.getPosition().getPlane() == 0
                && !AgilityAreas.DRAYNOR_LARGE_START_AREA.contains(Player.getPosition());
    }

    @Override
    public void execute() {
        PathingUtil.walkToArea(AgilityAreas.DRAYNOR_START_AREA, false);
    }

    @Override
    public String taskName() {
        return "Agility - Draynor";
    }
}
