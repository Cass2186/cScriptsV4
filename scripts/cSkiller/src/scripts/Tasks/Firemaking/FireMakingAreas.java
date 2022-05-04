package scripts.Tasks.Firemaking;

import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.types.Area;
import org.tribot.script.sdk.types.WorldTile;

public class FireMakingAreas {

    public static RSArea STARTING_AREA_1 = new RSArea(new RSTile(3175, 3493, 0), new RSTile(3177, 3509, 0));
    public static  RSArea STARTING_AREA_2 = new RSArea(new RSTile(3175, 3486, 0), new RSTile(3177, 3473, 0));
    public static  RSArea GE_WEST_WALL = new RSArea(new RSTile(3143, 3470, 0), new RSTile(3139, 3515, 0));
    public static RSArea GE_BOOTH_AREA = new RSArea(new RSTile(3167, 3491, 0), new RSTile(3162, 3487, 0));
    public static Area GE_START_AREA = Area.fromRectangle(new WorldTile(3175, 3504, 0),
            new WorldTile(3179, 3475, 0));

}
