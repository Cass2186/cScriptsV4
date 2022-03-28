package scripts.Tasks.Fishing.Locations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.tribot.api2007.types.RSArea;
import scripts.Data.Const;
@AllArgsConstructor
public enum ShrimpLocations {

    LUMBRIDGE_SWAMP(Const.LUMBRIDGE_FISHING_AREA);

    @Getter
    private RSArea area;
}
