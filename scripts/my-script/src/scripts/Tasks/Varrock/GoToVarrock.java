package scripts.Tasks.Varrock;

import scripts.API.Priority;
import scripts.API.Task;
import scripts.AgilityAPI.AgilUtils;
import scripts.AgilityAPI.COURSES;
import scripts.Data.AgilityAreas;
import org.tribot.api.General;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;
import scripts.PathingUtil;
import scripts.Timer;


public class GoToVarrock implements Task {




    @Override
    public String toString(){
        return "Going to Varrock course";
    }


    @Override
    public Priority priority() {
        return Priority.HIGH;
    }

    @Override
    public boolean validate() {
        return AgilUtils.isWithinLevelRange(30, 40) &&
                Player.getPosition().getPlane() ==0 &&
                (!AgilityAreas.VARROCK_GROUND_START_AREA.contains(Player.getPosition()) &&
                !AgilityAreas.VARROCK_LEVEL_1.contains(Player.getPosition()) &&
                !AgilityAreas.VARROCK_LEVEL_3.contains(Player.getPosition()));

    }

    @Override
    public void execute() {
        if (AgilityAreas.VARROCK_END_GROUND_AREA.contains(Player.getPosition())){
            General.println("[Debug]; Walking path to start");
            Walking.walkPath(AgilityAreas.VARROCK_PATH_TO_START);
            Timer.waitCondition(()-> AgilityAreas.VARROCK_PATH_TO_START[1]
                    .distanceTo(Player.getPosition()) <General.random(2,5), 6000,9000);
        }
        PathingUtil.walkToTile(AgilityAreas.VARROCK_PATH_TO_START[1], 2, false);
    }

    @Override
    public String course() {
        return COURSES.VARROCK.courseName;
    }
}
