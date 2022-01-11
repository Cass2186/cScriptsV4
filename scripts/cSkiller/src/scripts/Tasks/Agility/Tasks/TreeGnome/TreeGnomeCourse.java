package scripts.Tasks.Agility.Tasks.TreeGnome;


import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.AgilityAreas;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.Tasks.Agility.AgilityAPI.AgilUtils;
import scripts.Tasks.Agility.AgilityAPI.Obstacle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class TreeGnomeCourse implements Task {

     Obstacle LOG_OBSTACLE = new Obstacle(23145, "Walk-across",
            AgilityAreas.TG_START_FINISH_AREA,
            AgilityAreas.TG_OBSTACLE_2);
     Obstacle WALL_ONE = new Obstacle(23134, "Climb-over",
            AgilityAreas.TG_OBSTACLE_2,
            AgilityAreas.TG_OBSTACLE_3);
     Obstacle FIRST_TREE = new Obstacle(23559, "Climb",
            AgilityAreas.TG_OBSTACLE_3,
            AgilityAreas.TG_OBSTACLE_4);
     Obstacle TIGHT_ROPE = new Obstacle(23557, "Walk-on",
            AgilityAreas.TG_OBSTACLE_4,
            AgilityAreas.TG_OBSTACLE_5);
     Obstacle TREE_DOWN = new Obstacle(23560, "Climb-down",
            AgilityAreas.TG_OBSTACLE_5,
            AgilityAreas.TG_OBSTACLE_6);
     Obstacle WALL_TWO = new Obstacle(23135, "Climb-over",
            AgilityAreas.TG_OBSTACLE_6,
            AgilityAreas.TG_OBSTACLE_7);
     Obstacle PIPE = new Obstacle(23139, "Squeeze-through",
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
        return Vars.get().currentTask != null &&
                Vars.get().currentTask.equals(SkillTasks.AGILITY) &&
                AgilUtils.isWithinLevelRange(0,10) ;
    }

    @Override
    public void execute() {
        Optional<Obstacle> obs = AgilUtils.getCurrentObstacle(allObstacles);
        obs.ifPresent(obstacle -> message = obstacle.getObstacleAction() + " " +
                obstacle.getObstacleName());
        obs.ifPresent(Obstacle::navigateObstacle);
    }

    @Override
    public String taskName() {
        return "Agility - Tree gnome";
    }

}
