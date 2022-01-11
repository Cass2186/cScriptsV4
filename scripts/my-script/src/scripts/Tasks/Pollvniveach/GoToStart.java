package scripts.Tasks.Pollvniveach;


import dax.walker.utils.AccurateMouse;
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

public class GoToStart implements Task {

    int chanceOfClickingStartTile = General.random(40, 68);


    @Override
    public String toString() {
        return "Going to Polv start";
    }


    @Override
    public Priority priority() {
        return Priority.HIGHEST;
    }

    @Override
    public boolean validate() {
        return AgilUtils.isWithinLevelRange(70, 80) &&
                Player.getPosition().getPlane() == 0 &&
                !AgilityAreas.POLV_LARGE_START_AREA.contains(Player.getPosition())
                && !Vars.get().overridingCourse;
    }


    @Override
    public void execute() {
        General.println("[Debug]: Going to Polv start");
        if ( PathingUtil.walkToTile(AgilityAreas.POLV_START_TILE, 2, false)){
            Timer.waitCondition(()-> AgilityAreas.POLV_START_TILE.distanceTo(
                    Player.getPosition())<General.random(2,4),7000,9000);
        } else {
            PathingUtil.walkToTile(AgilityAreas.POLV_START_TILE, 2, false);
        }
        int c = General.random(0, 100);
        if (c > chanceOfClickingStartTile) {
            General.println("[Debug]: Screen walking to start");
            if (AgilityAreas.POLV_START_TILE.isClickable())
                AccurateMouse.walkScreenTile(AgilityAreas.POLV_START_TILE);
        }

    }

    @Override
    public String course() {
        return COURSES.POLLNIVEACH.courseName;
    }
}
