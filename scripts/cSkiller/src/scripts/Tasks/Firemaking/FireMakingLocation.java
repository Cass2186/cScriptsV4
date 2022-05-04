package scripts.Tasks.Firemaking;

import lombok.Getter;
import org.tribot.script.sdk.types.Area;

public enum FireMakingLocation {
    GRAND_EXCHANGE(FireMakingAreas.GE_START_AREA),
    SEERS,
    ARDOUGNE;

    @Getter
    private Area area;

    FireMakingLocation(Area area){
        this.area = area;
    }

    FireMakingLocation(){
        this.area = null;
    }
}
