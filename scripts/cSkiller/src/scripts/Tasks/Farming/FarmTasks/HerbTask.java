package scripts.Tasks.Farming.FarmTasks;

import org.tribot.api2007.types.RSArea;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Tasks.Farming.Data.Enums.FARM_TASKS;

public class HerbTask implements Task {



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


    public int getTeleItem() {
        return 0;
    }


    public int getSeedId() {
        return 0;
    }


    public FARM_TASKS getFarmTask() {
        return FARM_TASKS.HERBS_AND_FLOWER;
    }


    public RSArea getArea() {
        return null;
    }
}
