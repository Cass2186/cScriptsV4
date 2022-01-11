package scripts.Tasks.Falador;

import scripts.API.Priority;
import scripts.API.Task;
import scripts.AgilityAPI.AgilUtils;
import scripts.AgilityAPI.Obstacle;
import scripts.Data.AgilityAreas;
import scripts.Data.Vars;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class FaladorCourse implements Task {


    public Obstacle ROUGH_WALL = new Obstacle(14898, "Climb",
            AgilityAreas.FALADOR_FINISH_AREA,
            AgilityAreas.FALADOR_AREA_ONE);

    public Obstacle TIGHT_ROPE_ONE = new Obstacle(14899, "Cross",
            AgilityAreas.FALADOR_AREA_ONE,
            AgilityAreas.FALADOR_AREA_TWO);
    public Obstacle HAND_HOLDS = new Obstacle(14901, "Cross" ,
            AgilityAreas.FALADOR_AREA_TWO,
            AgilityAreas.FALADOR_AREA_THREE);

    public Obstacle GAP_ONE = new Obstacle(14903, "Jump",
            AgilityAreas.FALADOR_AREA_THREE,
            AgilityAreas.FALADOR_AREA_FOUR);

    public Obstacle GAP_TWO = new Obstacle(14904, "Jump",
            AgilityAreas.FALADOR_AREA_FOUR,
            AgilityAreas.FALADOR_AREA_FIVE);

    public Obstacle TIGHT_ROPE_TWO = new Obstacle(14905, "Cross",
            AgilityAreas.FALADOR_AREA_FIVE,
            AgilityAreas.FALADOR_AREA_SIX);

    public Obstacle TIGHT_ROPE_THREE = new Obstacle(14911, "Cross",
            AgilityAreas.FALADOR_AREA_SIX,
            AgilityAreas.FALADOR_AREA_SEVEN);

    public Obstacle GAP_THREE = new Obstacle(14919, "Jump",
            AgilityAreas.FALADOR_AREA_SEVEN,
            AgilityAreas.FALADOR_AREA_EIGHT);

    public Obstacle LEDGE_ONE = new Obstacle(14920, "Jump",
            AgilityAreas.FALADOR_AREA_EIGHT,
            AgilityAreas.FALADOR_AREA_NINE);


    public Obstacle LEDGE_TWO = new Obstacle(14921, "Jump",
            AgilityAreas.FALADOR_AREA_NINE,
            AgilityAreas.FALADOR_AREA_TEN);

    public Obstacle LEDGE_THREE = new Obstacle(14923, "Jump",
            AgilityAreas.FALADOR_AREA_TEN,
            AgilityAreas.FALADOR_AREA_ELEVEN);

    public Obstacle LEDGE_FOUR = new Obstacle(14924, "Jump",
            AgilityAreas.FALADOR_AREA_ELEVEN,
            AgilityAreas.FALADOR_AREA_TWELVE);

    public Obstacle FINAL_EDGE = new Obstacle(14925, "Jump",
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
        return message;
    }

    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        return AgilUtils.isWithinLevelRange(52,60) &&
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
        return "Falador";
    }
}
