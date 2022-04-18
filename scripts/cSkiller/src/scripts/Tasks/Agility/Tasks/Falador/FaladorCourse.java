package scripts.Tasks.Agility.Tasks.Falador;

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

public class FaladorCourse implements Task {


     Obstacle ROUGH_WALL = new Obstacle(14898, "Climb",
            AgilityAreas.FALADOR_FINISH_AREA,
            AgilityAreas.FALADOR_AREA_ONE);

     Obstacle TIGHT_ROPE_ONE = new Obstacle(14899, "Cross",
            AgilityAreas.FALADOR_AREA_ONE,
            AgilityAreas.FALADOR_AREA_TWO);
     Obstacle HAND_HOLDS = new Obstacle(14901, "Cross" ,
            AgilityAreas.FALADOR_AREA_TWO,
            AgilityAreas.FALADOR_AREA_THREE);

     Obstacle GAP_ONE = new Obstacle(14903, "Jump",
            AgilityAreas.FALADOR_AREA_THREE,
            AgilityAreas.FALADOR_AREA_FOUR);

     Obstacle GAP_TWO = new Obstacle(14904, "Jump",
            AgilityAreas.FALADOR_AREA_FOUR,
            AgilityAreas.FALADOR_AREA_FIVE);

     Obstacle TIGHT_ROPE_TWO = new Obstacle(14905, "Cross",
            AgilityAreas.FALADOR_AREA_FIVE,
            AgilityAreas.FALADOR_AREA_SIX);

     Obstacle TIGHT_ROPE_THREE = new Obstacle(14911, "Cross",
            AgilityAreas.FALADOR_AREA_SIX,
            AgilityAreas.FALADOR_AREA_SEVEN);

     Obstacle GAP_THREE = new Obstacle(14919, "Jump",
            AgilityAreas.FALADOR_AREA_SEVEN,
            AgilityAreas.FALADOR_AREA_EIGHT);

     Obstacle LEDGE_ONE = new Obstacle(14920, "Jump",
            AgilityAreas.FALADOR_AREA_EIGHT,
            AgilityAreas.FALADOR_AREA_NINE);


     Obstacle LEDGE_TWO = new Obstacle(14921, "Jump",
            AgilityAreas.FALADOR_AREA_NINE,
            AgilityAreas.FALADOR_AREA_TEN);

     Obstacle LEDGE_THREE = new Obstacle(14923, "Jump",
            AgilityAreas.FALADOR_AREA_TEN,
            AgilityAreas.FALADOR_AREA_ELEVEN);

     Obstacle LEDGE_FOUR = new Obstacle(14924, "Jump",
            AgilityAreas.FALADOR_AREA_ELEVEN,
            AgilityAreas.FALADOR_AREA_TWELVE);

     Obstacle FINAL_EDGE = new Obstacle(14925, "Jump",
            AgilityAreas.FALADOR_AREA_TWELVE,
            AgilityAreas.FALADOR_FINISH_AREA);


    List<Obstacle> allObstacles = new ArrayList<>(Arrays.asList(
            ROUGH_WALL,
           TIGHT_ROPE_ONE,
            HAND_HOLDS,
           GAP_ONE,
            GAP_TWO,
           TIGHT_ROPE_TWO,
           TIGHT_ROPE_THREE,
            GAP_THREE,
            LEDGE_ONE,
            LEDGE_TWO,
            LEDGE_THREE,
            LEDGE_FOUR,
            FINAL_EDGE

    ));

    String message = "";

    @Override
    public String toString(){
        Optional<Obstacle> obs = AgilUtils.getCurrentObstacle(allObstacles);
        obs.ifPresent(obstacle -> message = obstacle.getObstacleAction() + " " +
                obstacle.getObstacleName());
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
                AgilUtils.isWithinLevelRange(50,60);
    }

    @Override
    public void execute() {
        Optional<Obstacle> obs = AgilUtils.getCurrentObstacle(allObstacles);

        obs.ifPresent(Obstacle::navigateObstacle);
    }

    @Override
    public String taskName() {
        return "Agility - Falador";
    }

}
