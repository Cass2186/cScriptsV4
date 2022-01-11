package scripts.Tasks.Farming.Data;

import org.tribot.api2007.types.RSArea;
import scripts.Tasks.Farming.Data.Enums.FARM_TASKS;

public interface FarmTask {

     int getTeleItem();
     int getSeedId();
     FARM_TASKS getFarmTask();
     RSArea getArea();


}
