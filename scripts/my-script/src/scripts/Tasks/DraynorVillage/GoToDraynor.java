package scripts.Tasks.DraynorVillage;

import scripts.API.Priority;
import scripts.API.Task;
import scripts.AgilityAPI.AgilUtils;
import scripts.AgilityAPI.COURSES;
import scripts.Data.AgilityAreas;
import org.tribot.api2007.Player;
import scripts.PathingUtil;


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
        return AgilUtils.isWithinLevelRange(10, 30) &&
                Player.getPosition().getPlane() == 0
                && !AgilityAreas.DRAYNOR_LARGE_START_AREA.contains(Player.getPosition());
    }

    @Override
    public void execute() {
        PathingUtil.walkToArea(AgilityAreas.DRAYNOR_START_AREA, false);
    }

    @Override
    public String course() {
        return COURSES.DRAYNOR_VILLAGE.courseName;
    }
}
