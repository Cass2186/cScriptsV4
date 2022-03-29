package scripts.Tasks.Fishing.Locations;


import lombok.Getter;
import org.tribot.api2007.types.RSArea;
import scripts.Data.Const;

public enum ShrimpLocations implements FishingLocation{

    CATHERBY(Const.CATHERBY_FISHING_AREA),
    LUMBRIDGE_SWAMP(Const.LUMBRIDGE_FISHING_AREA);

    @Getter
    private RSArea area;

    @Getter
    private int requiredLevel = 1;

    ShrimpLocations(RSArea area){
        this.area = area;
    }
}
