package scripts.Data;

import org.tribot.api2007.types.RSTile;

public class Areas {

    private static Areas areas;

    public static Areas get() {
        return areas == null ? areas = new Areas() : areas;
    }

    public static void reset() {
        areas = new Areas();
    }


    public RSTile SPACE_ONE_TILE = new RSTile(3680, 3815, 0);

    public RSTile SPACE_TWO_TILE = new RSTile(3677, 3881, 0);

    public RSTile SPACE_THREE_TILE = new RSTile(3762, 3755, 0);

    public RSTile SPACE_FOUR_TILE = new RSTile(3768, 3760, 0);

    public RSTile WEST_MUSHROOM_TILE = new RSTile(3677, 3871, 0);

}
