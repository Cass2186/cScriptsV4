package scripts.Tasks.TreeGnome;

import scripts.API.Priority;
import scripts.API.Task;
import scripts.AgilityAPI.AgilUtils;
import scripts.AgilityAPI.COURSES;
import scripts.Data.AgilityAreas;
import org.tribot.api2007.Player;
import scripts.PathingUtil;


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
        return AgilUtils.isWithinLevelRange(0, 10) &&
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
    public String course() {
        return COURSES.TREE_GNOME_STRONGHOLD.courseName;
    }
}
