package scripts.Tasks.TreeGnome;


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

public class TreeGnomeCourse implements Task {

    public Obstacle LOG_OBSTACLE = new Obstacle(23145, "Walk-across",
            AgilityAreas.TG_START_FINISH_AREA,
            AgilityAreas.TG_OBSTACLE_2);
    public Obstacle WALL_ONE = new Obstacle(23134, "Climb-over",
            AgilityAreas.TG_OBSTACLE_2,
            AgilityAreas.TG_OBSTACLE_3);
    public Obstacle FIRST_TREE = new Obstacle(23559, "Climb",
            AgilityAreas.TG_OBSTACLE_3,
            AgilityAreas.TG_OBSTACLE_4);
    public Obstacle TIGHT_ROPE = new Obstacle(23557, "Walk-on",
            AgilityAreas.TG_OBSTACLE_4,
            AgilityAreas.TG_OBSTACLE_5);
    public Obstacle TREE_DOWN = new Obstacle(23560, "Climb-down",
            AgilityAreas.TG_OBSTACLE_5,
            AgilityAreas.TG_OBSTACLE_6);
    public Obstacle WALL_TWO = new Obstacle(23135, "Climb-over",
            AgilityAreas.TG_OBSTACLE_6,
            AgilityAreas.TG_OBSTACLE_7);
    public Obstacle PIPE = new Obstacle(23139, "Squeeze-through",
            AgilityAreas.TG_OBSTACLE_7,
            AgilityAreas.TG_START_FINISH_AREA);

    List<Obstacle> allObstacles = new ArrayList<>(Arrays.asList(
            LOG_OBSTACLE,
            WALL_ONE,
            FIRST_TREE,
            TIGHT_ROPE,
            TREE_DOWN,
            WALL_TWO,
            PIPE

    ));

    String message = "";

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
        return AgilUtils.isWithinLevelRange(0,10) ;
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
        return COURSES.TREE_GNOME_STRONGHOLD.courseName;
    }
}
