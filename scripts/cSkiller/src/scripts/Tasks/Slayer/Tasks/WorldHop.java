package scripts.Tasks.Slayer.Tasks;


import javafx.print.PageLayout;
import org.tribot.api2007.Player;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.MyPlayer;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.WorldHopper;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.Area;
import org.tribot.script.sdk.types.World;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.Tasks.Slayer.SlayerUtils.SlayerVars;

import java.util.Optional;

public class WorldHop implements Task {


    public static int getPlayerCountInArea() {
        Area slayArea = SlayerVars.get().fightArea;
        return slayArea != null ?
                Query.players().inArea(slayArea).count() : -1;
    }

    private boolean checkShouldHop(int playerThreshold) {
        // if we meet/exceed our hop threshold,
        // wait 1250ms to see if player(s) are still there
        if (getPlayerCountInArea() >= playerThreshold) {
            Log.warn("Hop player count waiting"); //TODO comment this out later
            Waiting.waitUntil(1250, 50,
                    () -> getPlayerCountInArea() < playerThreshold);
        }
        return getPlayerCountInArea() >= playerThreshold;
    }

    @Override
    public String toString() {
        return "World hopping";
    }

    @Override
    public Priority priority() {
        return Priority.HIGH;
    }

    @Override
    public boolean validate() {
        return Vars.get().currentTask != null &&
                Vars.get().currentTask.equals(SkillTasks.SLAYER) &&
                SlayerVars.get().fightArea != null &&
                SlayerVars.get().fightArea.contains(MyPlayer.getTile()) &&
                checkShouldHop( SlayerVars.get().assignment.getHopPlayerThreshold());
    }

    @Override
    public void execute() {
        Optional<World> world = Query.worlds().isLowPing().isMembers()
                .worldNumberNotEquals(WorldHopper.getCurrentWorld())
                .isMainGame().findRandom();
        world.ifPresent(w -> Log.info(String.format("Hopping to World %s with pint %s",
                w.getWorldNumber(), w.getPing())));
        if (world.map(w -> WorldHopper.hop(w.getWorldNumber())).orElse(false)) {
            SlayerVars.get().shouldWorldHop = false;
        }
    }

    @Override
    public String taskName() {
        return "Slayer";
    }
}
