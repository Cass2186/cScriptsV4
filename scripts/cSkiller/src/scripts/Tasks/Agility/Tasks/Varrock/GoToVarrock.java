package scripts.Tasks.Agility.Tasks.Varrock;

import org.tribot.api.General;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.AgilityAreas;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.PathingUtil;
import scripts.Tasks.Agility.AgilityAPI.AgilUtils;
import scripts.Timer;


public class GoToVarrock implements Task {


    @Override
    public String toString(){
        return "Going to Varrock course";
    }


    @Override
    public Priority priority() {
        return Priority.HIGH;
    }

    @Override
    public boolean validate() {
        return Vars.get().currentTask != null &&
                Vars.get().currentTask.equals(SkillTasks.AGILITY) &&
                AgilUtils.isWithinLevelRange(30, 40) &&
                Player.getPosition().getPlane() ==0 &&
                (!AgilityAreas.VARROCK_GROUND_START_AREA.contains(Player.getPosition()) &&
                !AgilityAreas.VARROCK_LEVEL_1.contains(Player.getPosition()) &&
                !AgilityAreas.VARROCK_LEVEL_3.contains(Player.getPosition()));

    }

    @Override
    public void execute() {
        if (AgilityAreas.VARROCK_END_GROUND_AREA.contains(Player.getPosition())){
            General.println("[Debug]; Walking path to start");
            Walking.walkPath(AgilityAreas.VARROCK_PATH_TO_START);
            Timer.waitCondition(()-> AgilityAreas.VARROCK_PATH_TO_START[1]
                    .distanceTo(Player.getPosition()) <General.random(2,5), 6000,9000);
        }
        PathingUtil.walkToTile(AgilityAreas.VARROCK_PATH_TO_START[1], 2, false);
    }

    @Override
    public String taskName() {
        return "Agility - Varrock";
    }

}
