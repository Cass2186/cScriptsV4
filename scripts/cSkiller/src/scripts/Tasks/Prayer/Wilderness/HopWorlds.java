package scripts.Tasks.Prayer.Wilderness;

import obf.F;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.Combat;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.WorldHopper;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.InventoryItem;
import org.tribot.script.sdk.types.World;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public class HopWorlds implements Task {



    @Override
    public Priority priority() {
        return Priority.HIGH;
    }

    @Override
    public boolean validate() {
        Optional<InventoryItem> item = Query.inventory().nameContains("bones").isNotNoted().findFirst();
        return Vars.get().currentTask != null &&
                Vars.get().useWildernessAltar &&
                Vars.get().currentTask.equals(SkillTasks.PRAYER) && PkObserver.shouldHop() &&
                Combat.isInWilderness() && item.isPresent();
    }

    @Override
    public void execute() {
        Mouse.setSpeed(250);
        Log.warn("Hopping worlds");
        if (WorldHopper.hop(PkObserver.nextWorld)) {
            PkObserver.nextWorld = PkObserver.getNextWorld();
        }

    }

    @Override
    public String taskName() {
        return "Hoping";
    }
}
