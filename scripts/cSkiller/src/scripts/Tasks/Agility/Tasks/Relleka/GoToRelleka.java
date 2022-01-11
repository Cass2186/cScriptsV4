package scripts.Tasks.Agility.Tasks.Relleka;

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

public class GoToRelleka implements Task {



    @Override
    public Priority priority() {
        return Priority.HIGHEST;
    }

    @Override
    public boolean validate() {
        return Vars.get().currentTask != null &&
                Vars.get().currentTask.equals(SkillTasks.AGILITY) &&
                !AgilityAreas.RELLEKA_LARGE_START.contains(Player.getPosition()) &&
                Player.getPosition().getPlane() == 0 &&
                AgilUtils.isWithinLevelRange(80,99);
    }

    @Override
    public void execute() {
        General.println("[Debug]: Going to Relleka start");
        if (AgilityAreas.RELLEKA_FINISH_AREA.contains(Player.getPosition())) {
            if (PathingUtil.localNavigation(AgilityAreas.RELLEKA_START_TILE))
                Timer.waitCondition(() ->
                        AgilityAreas.RELLEKA_LARGE_START.contains(Player.getPosition()), 8000, 10000);
        } else {
            PathingUtil.walkToTile(AgilityAreas.RELLEKA_START_TILE, 2, false);
        }
    }

    @Override
    public String taskName() {
        return "Agility - Relleka";
    }

}
