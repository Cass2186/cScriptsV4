package scripts.Tasks.Agility.Tasks.Ardougne;

import javafx.print.PageLayout;
import org.tribot.api2007.Player;
import org.tribot.script.sdk.MyPlayer;
import org.tribot.script.sdk.types.WorldTile;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.AgilityAreas;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.PathingUtil;
import scripts.Tasks.Agility.AgilityAPI.AgilUtils;
import scripts.Tasks.Agility.AgilityAPI.Obstacle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class GoToArdougneAgility implements Task {

    String message = "";
    WorldTile ARDY_START_TILE = new WorldTile(2673, 3297, 0);

    @Override
    public Priority priority() {
        return Priority.HIGH;
    }

    @Override
    public boolean validate() {
        return Vars.get().currentTask != null &&
                Vars.get().currentTask.equals(SkillTasks.AGILITY) &&
                MyPlayer.getTile().getPlane() == 0 &&
                !AgilityAreas.ARDOUGNE_START_AREA.contains(Player.getPosition()) &&
                AgilUtils.isWithinLevelRange(85, 99);
    }

    @Override
    public void execute() {
        if(!PathingUtil.localNav(ARDY_START_TILE)){
            PathingUtil.walkToTile(ARDY_START_TILE);
        }
    }

    @Override
    public String toString() {
        return "Going to Ardougne Agility";
    }

    @Override
    public String taskName() {
        return "Agility";
    }

}
