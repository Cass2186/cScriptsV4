package scripts.Tasks.Agility.Tasks.Canifis;

import org.tribot.api2007.Player;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.AgilityAreas;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.PathingUtil;
import scripts.Tasks.Agility.AgilityAPI.AgilUtils;
import scripts.Timer;

public class GoToCanifis implements Task {

    @Override
    public String toString() {
        return "Going to Canifis Start";
    }


    @Override
    public Priority priority() {
        return Priority.HIGH;
    }

    @Override
    public boolean validate() {
        return Vars.get().currentTask != null &&
                Vars.get().currentTask.equals(SkillTasks.AGILITY) &&
                AgilUtils.isWithinLevelRange(40, 50) &&
                !AgilityAreas.ALL_CANIFIS_ROOFTOPS.contains(Player.getPosition()) &&
                !AgilityAreas.ALL_CANIFIS_ROOFTOPS_LEVEL_2.contains(Player.getPosition()) &&
                !AgilityAreas.CANIFIS_FINISHED_AREA.contains(Player.getPosition()) &&
                !AgilityAreas.CANIFIS_LARGE_START.contains(Player.getPosition())
                && Vars.get().donePriestInPeril;
    }

    @Override
    public void execute() {
        if (PathingUtil.localNavigation(AgilityAreas.CANIFIS_SMALL_START.getRandomTile())) {
            Timer.waitCondition(() ->
                            AgilityAreas.CANIFIS_LARGE_START.contains(Player.getPosition()),
                    7000, 9000);
        } else {
            PathingUtil.walkToArea(AgilityAreas.CANIFIS_SMALL_START, false);
        }
    }

    @Override
    public String taskName() {
        return "Agility";
    }

}
