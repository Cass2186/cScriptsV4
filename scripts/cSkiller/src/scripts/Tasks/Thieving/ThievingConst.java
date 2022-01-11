package scripts.Tasks.Thieving;

import org.tribot.api.General;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;

public class ThievingConst {
    public static final  int PICKPOCKET_ANIMATION = 827;
    public static final   int KNOCKED_OUT_NPC = 838;

    public static final   int COIN_POUCH = 22531;
    public static final      int[] FOOD_IDS = {379};
    public static final   int FRUIT_IDS[] = {1955, 1963, 247, 1951, 2102, 2114, 5504, 2120, 19653, 464, 5972};
    public static final    RSTile cakeStallTile = new RSTile(2669, 3310, 0);
    public static final   int FRUIT_STALL_ID = 28823;
    public static final   int EMPTY_FRUIT_STALL = 27537;
    public static final   int ABC2CHANCE = General.random(10,20); //out of 100
    public static final   int EMPTY_JUG = 1935;
    public static final   int DODGY_NECKLACE = 21143;

    public static final   int BAKERS_STALL_ID = 11730;

    public static final     int SILK_STALL_ID = 11729;
    public static final   int SILK = 950;
    public static final   int GUARD_ID = 5418;
    public static final   int KNIGHT_ID = 3297;

    public static final   RSArea BEHIND_SILK_STALL_AREA = new RSArea(new RSTile(2669, 3316, 0), new RSTile(2653, 3319, 0));
    public static final    RSArea RESET_AGRO_AREA = new RSArea(new RSTile(2660, 3331, 0), new RSTile(2656, 3334, 0));
   public static final    RSArea CAKE_STALL_AGRO_AREA = new RSArea(new RSTile(2672, 3308, 0), new RSTile(2669, 3314, 0));

    public static final   RSTile FRUIT_STALL_TILE = new RSTile(1800, 3607, 0);

    public static final   RSTile BAKERS_STALL_TILE = new RSTile(2669, 3310, 0);
}
