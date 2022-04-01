package scripts.Tasks.Fishing.Locations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.tribot.api2007.types.RSArea;
import scripts.Data.Const;
import scripts.Tasks.Fishing.Fish;

@AllArgsConstructor
public enum RodLocations implements FishingLocation {

    BARBARIAN_VILLAGE(Const.BARBARIAN_VILLAGE_FISHING_AREA, 15),
    AL_KHARID(Const.AL_KHARID_FISHING_AREA, 15),
    OTTOS_GROTTO(Const.OTTOS_GROTTO_FISHING_AREA, 58);


    @Getter
    private RSArea area;

    @Getter
    private int requiredLevel;
}
