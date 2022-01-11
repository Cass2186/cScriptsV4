package scripts.Tasks.Farming.FarmTasks;

import org.tribot.api2007.types.RSArea;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Tasks.Farming.Data.Enums.FARM_TASKS;
import scripts.Tasks.Farming.Data.Enums.HERBS;
import scripts.Tasks.Farming.Data.FarmTask;

public class HerbTask implements Task, FarmTask {



    @Override
    public Priority priority() {
        return null;
    }

    @Override
    public boolean validate() {
        return false;
    }

    @Override
    public void execute() {

    }

    @Override
    public String toString() {
        return "Herbs";
    }

    @Override
    public String taskName() {
        return "Farming";
    }

    @Override
    public int getTeleItem() {
        return 0;
    }

    @Override
    public int getSeedId() {
        return 0;
    }

    @Override
    public FARM_TASKS getFarmTask() {
        return FARM_TASKS.HERBS_AND_FLOWER;
    }

    @Override
    public RSArea getArea() {
        return null;
    }
}
