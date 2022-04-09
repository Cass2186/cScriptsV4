package scripts.Tasks.Agility.Tasks.SeersCourse;


import org.tribot.api2007.Player;
import org.tribot.api2007.ext.Filters;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.AgilityAreas;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.Tasks.Agility.AgilityAPI.AgilUtils;
import scripts.Tasks.Agility.AgilityAPI.Obstacle;
import scripts.Timer;
import scripts.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Seers implements Task {

     Obstacle WALL = new Obstacle(14927, "Climb-up",
            AgilityAreas.SEERS_LARGE_WALL_AREA,
            AgilityAreas.SEERS_GAP_ONE_AREA);
     Obstacle GAP_ONE = new Obstacle(14928, "Jump",
            AgilityAreas.SEERS_GAP_ONE_AREA,
            AgilityAreas.SEERS_TIGHT_ROPE_AREA);
     Obstacle TIGHT_ROPE = new Obstacle(14932, "Cross",
            AgilityAreas.SEERS_TIGHT_ROPE_AREA,
            AgilityAreas.SEERS_GAP_TWO_AREA);
     Obstacle GAP_TWO = new Obstacle(14929, "Jump",
            AgilityAreas.SEERS_GAP_TWO_AREA,
            AgilityAreas.SEERS_GAP_THREE_AREA);
     Obstacle GAP_THREE = new Obstacle(14930, "Jump",
            AgilityAreas.SEERS_GAP_THREE_AREA,
            AgilityAreas.SEERS_EDGE_AREA);
     Obstacle EDGE = new Obstacle(14931, "Jump",
            AgilityAreas.SEERS_EDGE_AREA,
            AgilityAreas.SEERS_END_AREA);

    List<Obstacle> allObstacles = new ArrayList<>(Arrays.asList(
            WALL,
            GAP_ONE,
            TIGHT_ROPE,
            GAP_TWO,
            GAP_THREE,
            EDGE
    ));

    String message = "";

    @Override
    public String toString() {

        Optional<Obstacle> obs = AgilUtils.getCurrentObstacle(allObstacles);
        obs.ifPresent(obstacle -> message = obstacle.getObstacleAction() + " " +
                obstacle.getObstacleName());
        return message;
    }

    @Override
    public Priority priority() {
        return Priority.HIGH;
    }

    @Override
    public boolean validate() {
        return Vars.get().currentTask != null &&
                Vars.get().currentTask.equals(SkillTasks.AGILITY) &&
                AgilUtils.isWithinLevelRange(60, 70);
    }

    @Override
    public void execute() {
        AgilUtils.eatSummerPie(60,0);
        WALL.setTimeOutMin(10000);
        allObstacles.get(0).setTimeOutMin(10000);


        if (AgilityAreas.UPSTAIRS_SEERS_BANK.contains(Player.getPosition())) {
            if (Utils.clickObject(Filters.Objects.actionsContains("Climb-down"), "Climb-down"))
                Timer.waitCondition(() -> Player.getPosition().getPlane() == 0, 5000, 7000);
        }
        Optional<Obstacle> obs = AgilUtils.getCurrentObstacle(allObstacles);
        obs.ifPresent(obstacle -> message = obstacle.getObstacleAction() + " " +
                obstacle.getObstacleName());
        obs.ifPresent(Obstacle::navigateObstacle);
    }

    @Override
    public String taskName() {
        return "Agility - Seers";
    }

}
