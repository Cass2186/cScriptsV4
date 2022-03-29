package scripts.Tasks.Fishing.Locations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.tribot.api2007.types.RSArea;
import scripts.Data.Const;


public enum RodLocations implements FishingLocation {
    BARBARIAN_VILLAGE(Const.BARBARIAN_VILLAGE_FISHING_AREA),
    AL_KHARID(Const.AL_KHARID_FISHING_AREA),
    OTTOS_GROTTO(Const.OTTOS_GROTTO_FISHING_AREA, 58);

    @Getter
    private RSArea area;
    @Getter
    private int requiredLevel = 15;

    RodLocations(RSArea area) {
        this.area = area;
    }

    RodLocations(RSArea area, int requiredLevel) {
        this.area = area;
        this.requiredLevel = requiredLevel;
    }
}
