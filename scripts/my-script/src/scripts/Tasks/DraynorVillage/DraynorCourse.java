package scripts.Tasks.DraynorVillage;


import scripts.API.Priority;
import scripts.API.Task;
import scripts.AgilityAPI.AgilUtils;
import scripts.AgilityAPI.COURSES;
import scripts.AgilityAPI.Obstacle;
import scripts.Data.AgilityAreas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class DraynorCourse implements Task {

    String message = "";

    public Obstacle WALL_CLIMB = new Obstacle(11404, "Climb",
            AgilityAreas.DRAYNOR_LARGE_START_AREA,
            AgilityAreas.DRAYNOR_OBSTACLE_AREA_1);
    public Obstacle TIGHT_ROPE_1 = new Obstacle(11405, "Cross",
            AgilityAreas.DRAYNOR_OBSTACLE_AREA_1,
            AgilityAreas.DRAYNOR_OBSTACLE_AREA_2);
    public Obstacle TIGHT_ROPE_2 = new Obstacle(11406, "Cross",
            AgilityAreas.DRAYNOR_OBSTACLE_AREA_2,
            AgilityAreas.DRAYNOR_OBSTACLE_AREA_3);
    public Obstacle LEDGE = new Obstacle(11430, "Balance",
            AgilityAreas.DRAYNOR_OBSTACLE_AREA_3,
            AgilityAreas.DRAYNOR_OBSTACLE_AREA_4);
    public Obstacle BUILDING_OBS = new Obstacle(11630, "Jump-up",
            AgilityAreas.DRAYNOR_OBSTACLE_AREA_4,
            AgilityAreas.DRAYNOR_OBSTACLE_AREA_5);
    public Obstacle LEDGE_ONE = new Obstacle(11631, "Jump",
            AgilityAreas.DRAYNOR_OBSTACLE_AREA_6,
            AgilityAreas.DRAYNOR_OBSTACLE_AREA_7);
    public Obstacle FINAL_LEDGE = new Obstacle(11632, "Climb-down",
            AgilityAreas.DRAYNOR_OBSTACLE_AREA_7,
            AgilityAreas.DRAYNOR_GROUND_LEVEL);

    List<Obstacle> allObstacles = new ArrayList<>(
            Arrays.asList(
                    WALL_CLIMB,
                    TIGHT_ROPE_1,
                    TIGHT_ROPE_2,
                    LEDGE,
                    BUILDING_OBS,
                    LEDGE_ONE,
                    FINAL_LEDGE
            )
    );


    @Override
    public String toString(){
        return message;
    }


    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        return AgilUtils.isWithinLevelRange(10,30) ;
    }

    @Override
    public void execute() {
        Optional<Obstacle> obs = AgilUtils.getCurrentObstacle(allObstacles);
        obs.ifPresent(obstacle -> message = obstacle.getObstacleAction() + " " +
                obstacle.getObstacleName());
        obs.ifPresent(Obstacle::navigateObstacle);
    }

    @Override
    public String course() {
        return COURSES.DRAYNOR_VILLAGE.courseName;
    }
}
