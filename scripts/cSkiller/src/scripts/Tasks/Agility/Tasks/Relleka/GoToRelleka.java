package scripts.Tasks.Agility.Tasks.Relleka;

import org.tribot.api.General;
import org.tribot.api2007.Player;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.MyPlayer;
import org.tribot.script.sdk.Waiting;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.AgilityAreas;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.PathingUtil;
import scripts.Tasks.Agility.AgilityAPI.AgilUtils;
import scripts.Tasks.BirdHouseRuns.Nodes.Wait;
import scripts.Timer;
import scripts.Utils;

public class GoToRelleka implements Task {


    @Override
    public Priority priority() {
        return Priority.HIGHEST;
    }

    @Override
    public boolean validate() {
        return Vars.get().currentTask != null &&
                Vars.get().currentTask.equals(SkillTasks.AGILITY) &&
                !AgilityAreas.RELLEKA_LARGE_START.contains(Player.getPosition()) &&
                Player.getPosition().getPlane() == 0 &&
                AgilUtils.isWithinLevelRange(80, 85);
    }

    @Override
    public void execute() {
        Utils.idleNormalAction();
        Log.info("Going to Relleka start");
        if (MyPlayer.getAnimation() != -1 || MyPlayer.isMoving()) {
            Log.info("Waiting movement");
            Waiting.waitUntil(2500, 500, () -> MyPlayer.getAnimation() == -1 && !MyPlayer.isMoving());
        }
        if (!AgilityAreas.RELLEKA_FINISH_AREA.contains(Player.getPosition())) {
            Log.info("Waiting 2500ms");
            Waiting.waitUntil(2100, 500, () ->
                    AgilityAreas.RELLEKA_FINISH_AREA.contains(Player.getPosition()));
        }
        for (int i = 0; i < 3; i++) {
            if (PathingUtil.localNav(Utils.getLocalTileFromRSTile(AgilityAreas.RELLEKA_START_TILE))) {
                Timer.waitCondition(() ->
                        AgilityAreas.RELLEKA_LARGE_START.contains(Player.getPosition()), 8000, 10000);
                return;
            } else
                Utils.idleNormalAction();
        }
        PathingUtil.walkToTile(AgilityAreas.RELLEKA_START_TILE, 3, false);


    }

    @Override
    public String toString() {
        return "Going to Relleka Start";
    }

    @Override
    public String taskName() {
        return "Agility - Relleka";
    }

}
