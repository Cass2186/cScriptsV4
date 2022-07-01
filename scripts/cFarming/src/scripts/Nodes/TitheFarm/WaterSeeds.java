package scripts.Nodes.TitheFarm;

import org.tribot.script.sdk.MyPlayer;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.Area;
import org.tribot.script.sdk.types.GameObject;
import org.tribot.script.sdk.types.InventoryItem;
import scripts.Nodes.TitheFarm.TitheData.Const;
import scripts.Nodes.TitheFarm.TitheData.TitheVars;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;
import scripts.Utils;

import java.util.Optional;

public class WaterSeeds implements Task {

    @Override
    public String toString() {
        return "Watering Seeds";
    }
    @Override
    public Priority priority() {
        return Priority.HIGHEST;
    }

    @Override
    public boolean validate() {
        return TitheVars.get().workingArea.isPresent() &&
                Query.gameObjects()
                .inArea(TitheVars.get().workingArea.get())
                .actionContains("Water")
                .isAny();
    }

    @Override
    public void execute() {
        Utils.idlePredictableAction();
        waterSeed(TitheVars.get().workingArea);
    }
    private boolean waterSeed(Optional<Area> patchArea) {
        if (patchArea.isEmpty())
            return false;

        Optional<GameObject> patch = Query.gameObjects()
                .inArea(patchArea.get())
                .actionContains("Water")
                .findClosest();

        return patch.map(p -> p.interact("Water")).orElse(false) &&
                Waiting.waitUntil(3000, 350, ()-> !Query.gameObjects()
                        .inArea(patchArea.get())
                        .actionContains("Water").isAny());
    }


}
