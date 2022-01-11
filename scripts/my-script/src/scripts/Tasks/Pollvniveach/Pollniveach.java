package scripts.Tasks.Pollvniveach;


import scripts.API.Priority;
import scripts.API.Task;
import scripts.AgilityAPI.AgilUtils;
import scripts.AgilityAPI.COURSES;
import scripts.AgilityAPI.Obstacle;
import scripts.Data.AgilityAreas;
import scripts.Data.Vars;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Pollniveach implements Task {


    public Obstacle POLV_START_OBS = new Obstacle(14935, "Climb-on",
            AgilityAreas.POLV_LARGE_START_AREA,
            AgilityAreas.POLV_OBS_1_AREA);
    public Obstacle POLV_1 = new Obstacle(14936, "Jump-on",
            AgilityAreas.POLV_OBS_1_AREA,
            AgilityAreas.POLV_OBS_2_AREA);
    public Obstacle POLV_2 = new Obstacle(14937, "Grab",
            AgilityAreas.POLV_OBS_2_AREA,
            AgilityAreas.POLV_OBS_3_AREA);
    public Obstacle POLV_3 = new Obstacle(14938, "Leap",
            AgilityAreas.POLV_OBS_3_AREA,
            AgilityAreas.POLV_OBS_4_AREA);
    public Obstacle POLV_4 = new Obstacle(14939, "Jump-to",
            AgilityAreas.POLV_OBS_4_AREA,
            AgilityAreas.POLV_OBS_5_AREA);
    public Obstacle POLV_5 = new Obstacle(14940, "Climb",
            AgilityAreas.POLV_OBS_5_AREA,
            AgilityAreas.POLV_OBS_6_AREA);
    public Obstacle POLV_6 = new Obstacle(14941,"Cross",
            AgilityAreas.POLV_OBS_6_AREA,
            AgilityAreas.POLV_OBS_7_AREA);
    public Obstacle POLV_7 = new Obstacle(14944, "Jump-on",
            AgilityAreas.POLV_OBS_7_AREA,
            AgilityAreas.POLV_OBS_8_AREA);
    public Obstacle POLV_8 = new Obstacle(14945, "Jump-to",
            AgilityAreas.POLV_OBS_8_AREA);


    List<Obstacle> allObstacles = new ArrayList<>(Arrays.asList(
            POLV_START_OBS,
            POLV_1,
            POLV_2,
            POLV_3,
            POLV_4,
            POLV_5,
            POLV_6,
            POLV_7,
            POLV_8
    ));



    String message = "";

    @Override
    public String toString(){
        return message;
    }

    @Override
    public Priority priority() {
        return Priority.HIGH;
    }

    @Override
    public boolean validate() {
        return AgilUtils.isWithinLevelRange(70,80) &&
                !Vars.get().overridingCourse;
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
        return COURSES.POLLNIVEACH.courseName;
    }
}
