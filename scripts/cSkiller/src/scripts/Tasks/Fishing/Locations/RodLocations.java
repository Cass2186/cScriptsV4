package scripts.Tasks.Fishing.Locations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.tribot.api2007.types.RSArea;
import scripts.Data.Const;

@AllArgsConstructor
public enum RodLocations {
    BARBARIAN_VILLAGE(Const.BARBARIAN_VILLAGE_FISHING_AREA),
   // AL_KHARID,
    BARBARIAN_OUTPOST (Const.BARBARIAN_VILLAGE_FISHING_AREA);

    @Getter
    private RSArea area;
}
