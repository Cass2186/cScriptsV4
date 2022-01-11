package scripts.Tasks.Agility.Tasks.SeersCourse;


import org.tribot.api.General;
import org.tribot.api2007.Player;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.AgilityAreas;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.PathingUtil;
import scripts.Tasks.Agility.AgilityAPI.AgilUtils;
import scripts.Timer;

public class GoToSeersStart implements Task {


    @Override
    public Priority priority() {
        return Priority.HIGHEST;
    }

    @Override
    public boolean validate() {
        return Vars.get().currentTask != null &&
                        Vars.get().currentTask.equals(SkillTasks.AGILITY) &&
                        (AgilUtils.isWithinLevelRange(60, 70)) &&
                        (Player.getPosition().getPlane() == 0 &&
                                !AgilityAreas.SEERS_LARGE_WALL_AREA.contains(Player.getPosition()));
    }

    @Override
    public void execute() {
        General.println("[Debug]: Going to Seers start");
        if (AgilityAreas.SEERS_END_AREA.contains(Player.getPosition())) {
            if (PathingUtil.localNavigation(AgilityAreas.SEERS_WALL_AREA.getRandomTile()))
                Timer.waitCondition(() ->
                        AgilityAreas.SEERS_LARGE_WALL_AREA.contains(Player.getPosition()), 7000, 9000);
        } else {
            PathingUtil.walkToArea(AgilityAreas.SEERS_WALL_AREA, false);
        }
    }

    @Override
    public String taskName() {
        return "Agility - Seers";
    }

}
