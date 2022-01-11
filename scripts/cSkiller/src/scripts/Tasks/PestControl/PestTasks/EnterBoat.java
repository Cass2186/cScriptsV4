package scripts.Tasks.PestControl.PestTasks;

import org.tribot.api2007.Game;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Waiting;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.PathingUtil;
import scripts.Tasks.PestControl.PestUtils.PestUtils;
import scripts.Timer;
import scripts.Utils;

public class EnterBoat implements Task {

    public void enterBoat() {
        if (!PestUtils.INTERMEDIATE_BOAT_AREA.contains(Player.getPosition())) {
            PathingUtil.walkToTile(new RSTile(2644, 2644, 0), 1, false);
            if (Utils.clickObject("Gangplank", "Cross", false)) {
                Timer.waitCondition(() -> PestUtils.INTERMEDIATE_BOAT_AREA.contains(Player.getPosition()),
                        4500, 6000);
            }
        } else {
            PestUtils.getPestPoints();
            Log.log("[Debug]: Waiting for game");
            Timer.waitCondition(Game::isInInstance, 40000, 50000);
            if (Game.isInInstance()) {
                Waiting.waitNormal(2200, 220);
                RSArea area = PestUtils.getCenterArea();
                if (area != null &&
                        PathingUtil.localNavigation(area.getRandomTile()))
                    Waiting.waitNormal(1550, 220);
            }

        }

    }

    @Override
    public String toString() {
        return "Entering Boat";
    }

    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        return Vars.get().currentTask != null &&
                Vars.get().currentTask.equals(SkillTasks.PEST_CONTROL) &&
                !Game.isInInstance();
    }

    @Override
    public void execute() {
        enterBoat();
    }

    @Override
    public String taskName() {
        return "Pest Control";
    }
}
