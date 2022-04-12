package scripts.Tasks.Agility.Tasks.SeersCourse;


import org.tribot.api.General;
import org.tribot.api2007.Player;
import org.tribot.script.sdk.Combat;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.types.WorldTile;
import org.tribot.script.sdk.walking.LocalWalking;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.AgilityAreas;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.PathingUtil;
import scripts.Tasks.Agility.AgilityAPI.AgilUtils;
import scripts.Timer;

public class GoToSeersStart implements Task {

    WorldTile SEERS_START = new WorldTile(2729,  3487 ,0 );

    @Override
    public String toString() {
        return "Going to Seers start";
    }
    @Override
    public Priority priority() {
        return Priority.HIGHEST;
    }

    @Override
    public boolean validate() {
        return Vars.get().currentTask != null &&
                        Vars.get().currentTask.equals(SkillTasks.AGILITY) &&
                        (AgilUtils.isWithinLevelRange(60, 70)) &&
                        (Player.getPosition().getPlane() == 0 &&
                                !AgilityAreas.SEERS_LARGE_WALL_AREA.contains(Player.getPosition()))
                && !Combat.isInWilderness();
    }

    @Override
    public void execute() {
        General.println("[Debug]: Going to Seers start");
        if (AgilityAreas.SEERS_END_AREA.contains(Player.getPosition())) {
            Log.info("[Debug]: Going to Seers start - SDK");
            if (LocalWalking.walkTo(SEERS_START))
                Timer.waitCondition(() ->
                        AgilityAreas.SEERS_LARGE_WALL_AREA.contains(Player.getPosition()), 7000, 9000);
        } else {
            PathingUtil.walkToArea(AgilityAreas.SEERS_WALL_AREA, false);
        }
    }

    @Override
    public String taskName() {
        return "Agility - Seers";
    }

}
