package scripts.Tasks.Relleka;

import org.tribot.api.General;
import org.tribot.api2007.Player;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.AgilityAPI.AgilUtils;
import scripts.AgilityAPI.COURSES;
import scripts.Data.AgilityAreas;
import scripts.Data.Vars;
import scripts.PathingUtil;
import scripts.Timer;

public class GoToRelleka implements Task {



    @Override
    public Priority priority() {
        return Priority.HIGHEST;
    }

    @Override
    public boolean validate() {
        return !AgilityAreas.RELLEKA_LARGE_START.contains(Player.getPosition()) &&
                Player.getPosition().getPlane() == 0 &&
                ((AgilUtils.isWithinLevelRange(80,99) ||
                (Vars.get().useSummerPie && AgilUtils.isWithinLevelRange(76,99))) ||
                (Vars.get().overridingCourse && Vars.get().course != null && Vars.get().course == COURSES.RELLEKA));
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
    public String course() {
        return COURSES.RELLEKA.courseName;
    }
}
