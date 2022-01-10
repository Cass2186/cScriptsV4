package scripts.Gear;

import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.Equipment;
import scripts.PrayerType;

import java.util.ArrayList;
import java.util.List;

public class Gear {

    List<GearItem> gearList;

    private Gear(Builder builder) {
        this.gearList = builder.gearList;
    }


    public static class Builder {
        private List<GearItem> gearList;

        public Builder addEquipment(GearItem gearItem){
            if (this.gearList == null)
                this.gearList = new ArrayList<>();
            this.gearList.add(gearItem);
            return this;
        }

        public Builder build(){
            //validate the items
            return this;
        }
    }




    public void getMissingGearList(){

    }



}
