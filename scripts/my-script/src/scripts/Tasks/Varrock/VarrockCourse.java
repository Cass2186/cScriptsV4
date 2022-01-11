package scripts.Tasks.Varrock;


import org.tribot.api2007.Camera;
import org.tribot.api2007.Game;
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

public class VarrockCourse implements Task {

    String message = "";
    public Obstacle WALL_CLIMB = new Obstacle(14412, "Climb",
            AgilityAreas.VARROCK_LARGE_START_AREA,
            AgilityAreas.VARROCK_AREA_1);
    public Obstacle TREE_CLIMB_2 = new Obstacle(14413, "Cross",
            AgilityAreas.VARROCK_AREA_1,
            AgilityAreas.VARROCK_AREA_2);
    public Obstacle GAP_ONE = new Obstacle(14414, "Leap",
            AgilityAreas.VARROCK_AREA_2,
            AgilityAreas.VARROCK_AREA_3);
    public Obstacle GAP_TWO = new Obstacle(14832, "Balance",
            AgilityAreas.VARROCK_AREA_3,
            AgilityAreas.VARROCK_AREA_4);
    public Obstacle GAP_THREE = new Obstacle(14833, "Leap",
            AgilityAreas.VARROCK_AREA_4,
            AgilityAreas.VARROCK_AREA_5);
    public Obstacle GAP_FOUR = new Obstacle(14834, "Leap",
            AgilityAreas.VARROCK_AREA_5,
            AgilityAreas.VARROCK_AREA_6);
    public Obstacle GAP_FIVE = new Obstacle(14835, "Leap",
            AgilityAreas.VARROCK_AREA_6,
            AgilityAreas.VARROCK_AREA_7);
    public Obstacle GAP_SIX = new Obstacle(14385, "Leap",
            AgilityAreas.VARROCK_AREA_7,
            AgilityAreas.VARROCK_SECOND_LAST_AREA);
    public Obstacle HURDLE = new Obstacle(14836, "Hurdle",
            AgilityAreas.VARROCK_SECOND_LAST_AREA,
            AgilityAreas.VARROCK_FINAL_AREA);
    public Obstacle FINAL_LEDGE = new Obstacle(14841, "Jump-off",
            AgilityAreas.VARROCK_FINAL_AREA,
            AgilityAreas.VARROCK_END_GROUND_AREA);

    List<Obstacle> allObstacles = new ArrayList<>(Arrays.asList(
            WALL_CLIMB,
            TREE_CLIMB_2,
            GAP_ONE,
            GAP_TWO,
            GAP_THREE,
            GAP_FOUR,
            GAP_FIVE,
            GAP_SIX,
            HURDLE,
            FINAL_LEDGE
    ));



    @Override
    public String toString() {
        return message;
    }


    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        return  AgilUtils.isWithinLevelRange(30, 40);
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
        return COURSES.VARROCK.courseName;
    }
}
