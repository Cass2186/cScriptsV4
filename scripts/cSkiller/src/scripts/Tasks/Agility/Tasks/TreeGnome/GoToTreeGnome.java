package scripts.Tasks.Agility.Tasks.TreeGnome;

import org.tribot.api2007.Player;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.AgilityAreas;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.PathingUtil;
import scripts.Tasks.Agility.AgilityAPI.AgilUtils;


public class GoToTreeGnome implements Task {


    @Override
    public String toString(){
        return "Going to Tree gnome course";
    }

    @Override
    public Priority priority() {
        return Priority.HIGH;
    }

    @Override
    public boolean validate() {
        // will need another check for this course
        return Vars.get().currentTask != null &&
                Vars.get().currentTask.equals(SkillTasks.AGILITY) &&
                AgilUtils.isWithinLevelRange(0, 10) &&
                Player.getPosition().getPlane() == 0 &&
                !AgilityAreas.WHOLE_TG_COURSE_LEVEL_0.contains(Player.getPosition()) &&
                !AgilityAreas.WHOLE_TG_COURSE_LEVEL_2.contains(Player.getPosition()) &&
                !AgilityAreas.WHOLE_TG_COURSE_LEVEL_1.contains(Player.getPosition());
    }

    @Override
    public void execute() {
        PathingUtil.walkToArea(AgilityAreas.TG_START_AREA, false);
    }

    @Override
    public String taskName() {
        return "Agility - Tree gnome";
    }

}
