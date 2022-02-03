package scripts.Tasks.Farming.Data;

import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSArea;
import scripts.PathingUtil;
import scripts.Tasks.Farming.Data.Enums.FARM_TASKS;

public class FarmTask {

    public int getTeleItem() {
          return 0;
     }

     public int getSeedId() {
          return 0;
     }

     public  FARM_TASKS getFarmTask() {
          return null;
     }

     public  RSArea getArea() {
          return null;
     }

     public void moveToArea(RSArea area){
         if (area.contains(Player.getPosition())) {
             PathingUtil.walkToArea(area);
         }
     }


}
