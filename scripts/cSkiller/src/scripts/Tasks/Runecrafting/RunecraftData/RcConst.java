package scripts.Tasks.Runecrafting.RunecraftData;

import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;

public class RcConst {
    public static RSArea EARTH_ALTAR_AREA = new RSArea(new RSTile(3300, 3475, 0), new RSTile(3303, 3472, 0));
    public static RSArea FIRE_ALTAR_AREA = new RSArea(new RSTile(3315, 3251, 0), new RSTile(3311, 3255, 0));
    public static RSArea FIRE_ALTAR_TILE = new RSArea(new RSTile(2583, 4840, 0), 3);
    public static RSArea CASTLE_WARS_AREA = new RSArea(new RSTile(2442, 3084, 0), 1);
    public static RSArea ZANARIS_BANK = new RSArea(
            new RSTile[]{
                    new RSTile(2386, 4463, 0),
                    new RSTile(2382, 4463, 0),
                    new RSTile(2380, 4461, 0),
                    new RSTile(2380, 4456, 0),
                    new RSTile(2382, 4454, 0),
                    new RSTile(2386, 4454, 0)
            }
    );
    public static RSArea ZANARIS_ALTAR = new RSArea(new RSTile(2407, 4381, 0), new RSTile(2414, 4376, 0));
    public static RSTile FIRE_ALTAR_TILE_BEFORE_RUINS = new RSTile(3312, 3253, 0);


}
