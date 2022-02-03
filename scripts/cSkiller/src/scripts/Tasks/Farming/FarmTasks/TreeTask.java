package scripts.Tasks.Farming.FarmTasks;

import org.tribot.api2007.types.RSArea;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Tasks.Farming.Data.Enums.FARM_TASKS;
import scripts.Tasks.Farming.Data.FarmTask;
import scripts.Tasks.Farming.Data.FarmingUtils;

public class TreeTask extends FarmTask implements Task {


    private int seedId = -1;
    private int patchId = -1;

    private RSArea area;

    public TreeTask(int seedId, int patchId , RSArea area) {
        this.seedId = seedId;
        this.patchId = patchId;
        this.area = area;
    }

    private boolean plantTree() {
        FarmingUtils.clearDeadPlants(this.patchId);
        FarmingUtils.clearTree();
        FarmingUtils.rakeWeeds(this.patchId);
        FarmingUtils.useCompostTree(this.patchId);
        return FarmingUtils.plantTreePatch(this.seedId, this.patchId);
    }

    @Override
    public int getTeleItem() {
        return 0;
    }

    @Override
    public int getSeedId() {
        return this.seedId;
    }

    @Override
    public FARM_TASKS getFarmTask() {
        return FARM_TASKS.TREE;
    }

    @Override
    public RSArea getArea() {
        return this.area;
    }

    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return false;
    }

    @Override
    public void execute() {
        super.moveToArea(this.area);
        plantTree();
    }

    @Override
    public String taskName() {
        return "Farming";
    }
}
