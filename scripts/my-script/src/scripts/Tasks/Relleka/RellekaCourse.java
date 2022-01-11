package scripts.Tasks.Relleka;

import scripts.API.Priority;
import scripts.AgilityAPI.AgilUtils;
import scripts.AgilityAPI.COURSES;
import scripts.API.Task;
import scripts.AgilityAPI.Obstacle;
import scripts.Data.AgilityAreas;
import scripts.Data.Vars;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class RellekaCourse implements Task {


    String message = "";
    public Obstacle WALL_CLIMB = new Obstacle(14946, "Climb",
            AgilityAreas.RELLEKA_LARGE_START,
            AgilityAreas.RELLEKA_OBS_1_AREA);
    public Obstacle GAP_ONE = new Obstacle(14947, "Leap",
            AgilityAreas.RELLEKA_OBS_1_AREA,
            AgilityAreas.RELLEKA_OBS_2_AREA);
    public Obstacle TIGHTROPE_ONE = new Obstacle(14987, "Cross",
            AgilityAreas.RELLEKA_OBS_2_AREA,
            AgilityAreas.RELLEKA_OBS_3_AREA);
    public Obstacle GAP_TWO = new Obstacle(14990, "Leap",
            AgilityAreas.RELLEKA_OBS_3_AREA,
            AgilityAreas.RELLEKA_OBS_4_AREA);
    public Obstacle GAP_THREE = new Obstacle(14991, "Hurdle",
            AgilityAreas.RELLEKA_OBS_4_AREA,
            AgilityAreas.RELLEKA_OBS_5_AREA);
    public Obstacle TIGHTROPE_TWO = new Obstacle(14992, "Cross",
            AgilityAreas.RELLEKA_OBS_5_AREA,
            AgilityAreas.RELLEKA_OBS_6_AREA);
    public Obstacle PILE_OF_FISH = new Obstacle(14994, "Jump-in",
            AgilityAreas.RELLEKA_OBS_6_AREA,
            AgilityAreas.RELLEKA_FINISH_AREA);


    List<Obstacle> allObstacles = new ArrayList<>(Arrays.asList(
            WALL_CLIMB,
            GAP_ONE,
            TIGHTROPE_ONE,
            GAP_TWO,
            GAP_THREE,
            TIGHTROPE_TWO,
            PILE_OF_FISH
    ));


    @Override
    public Priority priority() {
        return Priority.HIGH;
    }

    @Override
    public boolean validate() {
        return AgilUtils.isWithinLevelRange(80,99) ||
        (Vars.get().useSummerPie && AgilUtils.isWithinLevelRange(76,99)) ||
                (Vars.get().overridingCourse && Vars.get().course != null && Vars.get().course == COURSES.RELLEKA);
    }

    @Override
    public void execute() {
        AgilUtils.eatSummerPie(80,0);
        Optional<Obstacle> obs = AgilUtils.getCurrentObstacle(allObstacles);
        obs.ifPresent(obstacle -> message = obstacle.getObstacleAction() + " " +
                obstacle.getObstacleName());
        obs.ifPresent(Obstacle::navigateObstacle);
    }

    @Override
    public String course() {
        return COURSES.RELLEKA.courseName;
    }
}
