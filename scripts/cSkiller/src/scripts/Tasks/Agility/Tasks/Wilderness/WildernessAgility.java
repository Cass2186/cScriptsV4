package scripts.Tasks.Agility.Tasks.Wilderness;

import org.tribot.api.input.Mouse;
import org.tribot.api2007.Player;
import org.tribot.api2007.ext.Filters;
import org.tribot.script.sdk.*;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.GameObject;
import org.tribot.script.sdk.types.World;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.AgilityAreas;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.Listeners.PkListener;
import scripts.Tasks.Agility.AgilityAPI.AgilUtils;
import scripts.Tasks.Agility.AgilityAPI.Obstacle;
import scripts.Tasks.Slayer.Tasks.WorldHop;
import scripts.Timer;
import scripts.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class WildernessAgility implements Task {

    Obstacle OBSTACLE_PIPE = new Obstacle(23137, "Squeeze-through",
            AgilityAreas.WILDERNESS_START_AREA,
            AgilityAreas.AFTER_OBSTACLE_PIPE_WILDERNESS);
    Obstacle ROPE_SWING = new Obstacle(23132, "Swing-on",
            AgilityAreas.AFTER_OBSTACLE_PIPE_WILDERNESS,
            AgilityAreas.AFTER_ROPE_SWING_WILDERNESS);
    Obstacle STEPPING_STONES = new Obstacle(23556, "Cross",
            AgilityAreas.AFTER_ROPE_SWING_WILDERNESS,
            AgilityAreas.AFTER_STEPPING_STONES_WILDERNESS);
    Obstacle LOG_BALANCE = new Obstacle(23542, "Walk-across",
            AgilityAreas.AFTER_STEPPING_STONES_WILDERNESS,
            AgilityAreas.AFTER_LOG_WILDERNESS);
    Obstacle ROCKS = new Obstacle(23640, "Climb",
            AgilityAreas.AFTER_LOG_WILDERNESS,
            AgilityAreas.WILDERNESS_START_AREA);

    List<Obstacle> allObstacles = new ArrayList<>(Arrays.asList(
            OBSTACLE_PIPE,
            ROPE_SWING,
            STEPPING_STONES,
            LOG_BALANCE,
            ROCKS
    ));

    String message = "";

    private void pkObserver(){
        int maxLevel = MyPlayer.getCombatLevel() + Combat.getWildernessLevel();
        int minLevel = MyPlayer.getCombatLevel() - Combat.getWildernessLevel();

        List<org.tribot.script.sdk.types.Player> nearbyPlayers = Query.players()
                //.hasSkullIcon()
                .maxDistance(25)
                .maxLevel(maxLevel)
                .minLevel(minLevel)
                .toList();

        if (nearbyPlayers.size() > 0){
            Mouse.setSpeed(250);
            Log.warn("Hopping worlds");
            WorldHopper.hop(org.tribot.api2007.WorldHopper.getRandomWorld(true, false));
        }

    }



    @Override
    public String toString() {
        Optional<Obstacle> obs = AgilUtils.getCurrentObstacle(allObstacles);
        obs.ifPresent(obstacle -> message = obstacle.getObstacleAction() + " " +
                obstacle.getObstacleName());
        return message;
    }


    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return Vars.get().currentTask != null && Vars.get().currentTask.equals(SkillTasks.AGILITY)
                && Combat.isInWilderness() &&
                AgilUtils.isWithinLevelRange(52,  Vars.get().useWildernessAgilityUntilLevel);
    }

    @Override
    public void execute() {
        if (AgilityAreas.FAIL_AREA_UNDERGROUND_WILDERNESS.contains(Player.getPosition())){
            Optional<GameObject> ladder = Query.gameObjects().actionContains("Climb-up").findBestInteractable();
            if (ladder.map(l->l.interact("Climb-up")).orElse(false)){
                Log.info("Climbing Ladder");
                Waiting.waitUntil(7000, 200, ()->
                       !AgilityAreas.FAIL_AREA_UNDERGROUND_WILDERNESS.contains(Player.getPosition()));
            } else
                Log.error("Climbing Ladder FAILED");
        }
        pkObserver();
        allObstacles.get(0).setTimeOutMin(12000); //pipe
        allObstacles.get(2).setTimeOutMin(10000); //stepping stones
        Optional<Obstacle> obs = AgilUtils.getCurrentObstacle(allObstacles);
        obs.ifPresent(obstacle -> message = obstacle.getObstacleAction() + " " +
                obstacle.getObstacleName());
        obs.ifPresent(Obstacle::navigateObstacle);
    }

    @Override
    public String taskName() {
        return "Agility - Wilderness";
    }
}
