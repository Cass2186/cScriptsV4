package scripts.Tasks.Fishing.Locations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.tribot.api2007.types.RSArea;
import scripts.Data.Const;
@AllArgsConstructor
public enum ShrimpLocations  implements FishingLocation{

    LUMBRIDGE_SWAMP(Const.LUMBRIDGE_FISHING_AREA, 1),
    CATHERBY(Const.CATHERBY_FISHING_AREA, 1);

    @Getter
    private RSArea area;

    @Getter
    private int requiredLevel;
}
