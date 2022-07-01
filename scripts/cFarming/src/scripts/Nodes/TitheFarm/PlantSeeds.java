package scripts.Nodes.TitheFarm;

import org.tribot.script.sdk.MyPlayer;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.*;
import scripts.ItemID;
import scripts.Nodes.TitheFarm.TitheData.Const;
import scripts.Nodes.TitheFarm.TitheData.TitheVars;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;

import java.util.List;
import java.util.Optional;

public class PlantSeeds implements Task {

    @Override
    public String toString() {
        return "Planting Seeds";
    }

    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        return TitheVars.get().workingArea.isPresent() &&
                Query.gameObjects()
                        .idEquals(Const.TITHE_PATCH)
                        .inArea(TitheVars.get().workingArea.get()).isAny()
                && !Query.gameObjects()
                        .actionContains("Harvest")
                        .inArea(TitheVars.get().workingArea.get()).isAny();
    }

    @Override
    public void execute() {
        plantSeed(TitheVars.get().workingArea, ItemID.GOLOVANOVA_SEED);
    }

    private void plantSeed(Optional<Area> patchArea, int seedId) {
        if (patchArea.isEmpty())
            return;

        List<GameObject> patch = Query.gameObjects()
                .idEquals(Const.TITHE_PATCH)
                .inArea(patchArea.get())
                .sortedByDistance()
                .toList();
        for (GameObject p : patch) {
            Optional<InventoryItem> seed = Query.inventory().idEquals(seedId).findClosestToMouse();
            if (seed.map(s -> s.useOn(p)).orElse(false)) {
                Waiting.waitUntil(4000, 50,
                        () -> Query.gameObjects()
                                .inArea(patchArea.get())
                                .actionContains("Water")
                                .isAny());
                break;
            }
        }
    }

    public static Optional<Area> getWorkingAreaWestSmall() {
        Optional<GameObject> anchor = Query.gameObjects()
                .nameContains("Farm door")
                .findClosest();

        if (anchor.isPresent()) {
            LocalTile t = anchor.get().getTile().toLocalTile();
            return Optional.of(Area.fromRectangle(
                    t.translate(10, 0),
                    //  t.translate(5,11),
                    t.translate(5, -13)
                    // t.translate(10,-13)
            ));
        }

        return Optional.empty();
    }

    public static Optional<Area> getWorkingAreaWest() {
        Optional<GameObject> anchor = Query.gameObjects()
                .nameContains("Farm door")
                .findClosest();

        if (anchor.isPresent()) {
            LocalTile t = anchor.get().getTile().toLocalTile();
            return Optional.of(Area.fromRectangle(
                    t.translate(10, 11),
                    //  t.translate(5,11),
                    t.translate(5, -13)
                    // t.translate(10,-13)
            ));
        }

        return Optional.empty();
    }
}
