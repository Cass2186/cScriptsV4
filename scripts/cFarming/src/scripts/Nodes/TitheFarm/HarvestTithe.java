package scripts.Nodes.TitheFarm;

import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.Area;
import org.tribot.script.sdk.types.GameObject;
import scripts.Nodes.TitheFarm.TitheData.TitheVars;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;

import java.util.Optional;

public class HarvestTithe implements Task {

    @Override
    public String toString() {
        return "Harvesting Seeds";
    }

    @Override
    public Priority priority() {
        return Priority.HIGH;
    }


    @Override
    public boolean validate() {
        return TitheVars.get().workingArea.isPresent() &&
                Query.gameObjects()
                        .inArea(TitheVars.get().workingArea.get())
                        .actionContains("Harvest")
                        .isAny();
    }

    @Override
    public void execute() {
        harvest(TitheVars.get().workingArea);
    }
    private boolean harvest(Optional<Area> patchArea) {
        if (patchArea.isEmpty())
            return false;

        Optional<GameObject> patch = Query.gameObjects()
                .inArea(patchArea.get())
                .actionContains("Harvest")
                .findClosest();

        return patch.map(p -> p.interact("Harvest")).orElse(false) &&
                Waiting.waitUntil(3000, 150, ()-> !Query.gameObjects()
                        .inArea(patchArea.get())
                        .actionContains("Harvest").isAny());
    }
}
