package scripts.Tasks.Agility.Tasks.Canifis;


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

public class CanifisCourse implements Task {

    String message = "";

    Obstacle TREE_CLIMB = new Obstacle(14843, "Climb",
            AgilityAreas.CANIFIS_LARGE_START,
            AgilityAreas.CANIFIS_OBSTACLE_1);
    Obstacle TREE_CLIMB_2 = new Obstacle(14843, "Climb",
            AgilityAreas.CANIFIS_FINISHED_AREA,
            AgilityAreas.CANIFIS_OBSTACLE_1);
    Obstacle GAP_ONE = new Obstacle(14844, "Jump",
            AgilityAreas.CANIFIS_OBSTACLE_1,
            AgilityAreas.CANIFIS_OBSTACLE_2);
    Obstacle GAP_TWO = new Obstacle(14845, "Jump",
            AgilityAreas.CANIFIS_OBSTACLE_2,
            AgilityAreas.CANIFIS_OBSTACLE_3);
    Obstacle GAP_THREE = new Obstacle(14848, "Jump",
            AgilityAreas.CANIFIS_OBSTACLE_3,
            AgilityAreas.CANIFIS_OBSTACLE_4);
    Obstacle GAP_FOUR = new Obstacle(14846, "Jump",
            AgilityAreas.CANIFIS_OBSTACLE_4,
            AgilityAreas.CANIFIS_OBSTACLE_5);
    Obstacle GAP_FIVE = new Obstacle(14894, "Vault",
            AgilityAreas.CANIFIS_OBSTACLE_5,
            AgilityAreas.CANIFIS_OBSTACLE_6);
    Obstacle GAP_SIX = new Obstacle(14847, "Jump",
            AgilityAreas.CANIFIS_OBSTACLE_6,
            AgilityAreas.CANIFIS_OBSTACLE_7);
    Obstacle FINAL_LEDGE = new Obstacle(14897, "Jump",
            AgilityAreas.CANIFIS_OBSTACLE_7,
            AgilityAreas.CANIFIS_FINISHED_AREA);

    List<Obstacle> allObstacles = new ArrayList<>(Arrays.asList(
            TREE_CLIMB,
            TREE_CLIMB_2,
            GAP_ONE,
            GAP_TWO,
            GAP_THREE,
            GAP_FOUR,
            GAP_FIVE,
            GAP_SIX,
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
        return Vars.get().currentTask != null &&
                Vars.get().currentTask.equals(SkillTasks.AGILITY) &&
                AgilUtils.isWithinLevelRange(40, 50)
                && Vars.get().donePriestInPeril;
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
        return "Agility - Canifis";
    }

}
