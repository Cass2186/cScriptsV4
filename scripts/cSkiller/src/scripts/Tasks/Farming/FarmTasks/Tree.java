package scripts.Tasks.Farming.FarmTasks;

import dax.walker.utils.AccurateMouse;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSObject;
import scripts.Tasks.Farming.Data.FarmVars;
import scripts.Tasks.Farming.Data.FarmingUtils;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;
import scripts.Timer;
import scripts.Utils;


public class Tree implements Task {

    private void checkHealth(int patchId) {
        RSObject[] tree = Objects.findNearest(20, Filters.Objects.actionsContains("Check-health").and(Filters.Objects.idEquals(patchId)));
        if (tree.length > 0) {
            FarmVars.get().status = "Checking health";

            if (!tree[0].isClickable())
                tree[0].adjustCameraTo();

            if (AccurateMouse.click(tree[0], "Check-health")) {
                if (Timer.waitCondition(() ->
                        Objects.findNearest(20, Filters.Objects.actionsContains("Check-health").
                                and(Filters.Objects.idEquals(patchId))).length < 1, 6000, 9000)) ;
            }
        }
    }

    public void plantTree(int herbId, int patchId) {
        RSObject[] patch = Objects.findNearest(20,
                Filters.Objects.nameContains("Tree patch").and(Filters.Objects.idEquals(patchId)));
        if (patch.length > 0) {
            FarmVars.get().status = "Planting Tree";
            if (Utils.useItemOnObject(herbId, patchId)) {
                Timer.waitCondition(() -> Player.getAnimation() != -1, 4000, 8000);
                Timer.abc2WaitCondition(() -> Player.getAnimation() == -1, 4000, 8000);
            }
        }
    }

    public void plantTreeWhole(int patchId) {
        FarmingUtils.clearDeadPlants(patchId);
        checkHealth(patchId);
        // cut down
        FarmingUtils.rakeWeeds(patchId);
        FarmingUtils.useCompost(patchId);
        plantTree(FarmVars.get().treeId, patchId);

    }

    @Override
    public void execute() {

    }

    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return false;
    }
}
