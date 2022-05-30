package scripts.Tasks.Agility.Tasks.Ardougne;

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

public class ArdougneCourse implements Task {
    String message = "";
    Obstacle WALL_CLIMB = new Obstacle(15608, "Climb-up",
            AgilityAreas.ARDOUGNE_START_AREA,
            AgilityAreas.ARDOUGNE_AREA_ONE);
    Obstacle GAP_ONE = new Obstacle(15609, "Jump",
            AgilityAreas.ARDOUGNE_AREA_ONE,
            AgilityAreas.ARDOUGNE_AREA_TWO);
    Obstacle TIGHTROPE_ONE = new Obstacle(26635, "Walk-on",
            AgilityAreas.ARDOUGNE_AREA_TWO,
            AgilityAreas.ARDOUGNE_AREA_THREE);
    Obstacle GAP_TWO = new Obstacle(15610, "Jump",
            AgilityAreas.ARDOUGNE_AREA_THREE,
            AgilityAreas.ARDOUGNE_AREA_FOUR);
    Obstacle GAP_THREE = new Obstacle(15611, "Jump",
            AgilityAreas.ARDOUGNE_AREA_FOUR,
            AgilityAreas.ARDOUGNE_AREA_FIVE);
    Obstacle TIGHTROPE_TWO = new Obstacle(28912, "Balance-across",
            AgilityAreas.ARDOUGNE_AREA_FIVE,
            AgilityAreas.ARDOUGNE_AREA_SIX);
    Obstacle PILE_OF_FISH = new Obstacle(15612, "Jump",
            AgilityAreas.ARDOUGNE_AREA_SEVEN,
            AgilityAreas.ARDOUGNE_START_AREA);


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
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        return Vars.get().currentTask != null &&
                Vars.get().currentTask.equals(SkillTasks.AGILITY) &&
                AgilUtils.isWithinLevelRange(85, 99);
    }

    @Override
    public void execute() {
        AgilUtils.eatSummerPie(90, 0);
        Optional<Obstacle> obs = AgilUtils.getCurrentObstacle(allObstacles);
        obs.ifPresent(Obstacle::navigateObstacle);
    }

    @Override
    public String toString() {
        Optional<Obstacle> obs = AgilUtils.getCurrentObstacle(allObstacles);
        obs.ifPresent(obstacle -> message = obstacle.getObstacleAction() + " " +
                obstacle.getObstacleName());
        return message;
    }

    @Override
    public String taskName() {
        return "Agility";
    }
}
