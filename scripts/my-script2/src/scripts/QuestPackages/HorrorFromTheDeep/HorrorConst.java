package scripts.QuestPackages.HorrorFromTheDeep;

import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.types.WorldTile;

public class HorrorConst {


    public static int HORROR_JOURNAL = 3845;
    public static int HORROR_DIARY = 3846;
    public static int HORROR_MANUAL = 3847;

    // DAGANOTH MOTHER FORMS
    public static int WHITE_FORM = 983;
    public static int BLUE_FORM = 984;
    public static int BROWN_FORM = 986;
    public static int RED_FORM = 985;
    public static int ORANGE_FORM = 988;
    public static int GREEN_FORM = 987;
    public static int BASEMENT_DOOR_ID = 4543;

    public static  int GAME_SETTING = 351;
    public static  int LIGHTHOUSE_KEY = 3848;
    public static RSTile BRIDGE_TILE = new RSTile(2600, 3608, 0);
    public static RSTile BRIDGE_TILE_2 = new RSTile(2592, 3607, 0);
    public static RSTile MOTHER_SAFE_TILE = new RSTile(2510, 4655, 0);
    public static RSTile BASEMENT_DOOR_TILE = new RSTile(2515, 4626, 0);
    public static WorldTile START_TILE =  new WorldTile(2508, 3633, 0);
    public static RSArea BASEMENT = new RSArea(new RSTile(2505, 4626, 0), new RSTile(2527, 4611, 0));
    public static RSArea BOTTOM_FLOOR = new RSArea(new RSTile(2442, 4602, 0), 10);
    public static RSArea START_AREA = new RSArea(new RSTile(2502, 3635, 0), new RSTile(2514, 3631, 0));
    public static RSArea INSIDE_LIGHTHOUSE_DOOR = new RSArea(new RSTile(2507, 3636, 0), new RSTile(2510, 3645, 0));
    public static RSArea AFTER_PIPE_AGILITY = new RSArea(new RSTile(2550, 3559, 0), new RSTile(2553, 3555, 0));
    public static RSArea WHOLE_BRIDGE_AREA = new RSArea(new RSTile(2602, 3606, 0), new RSTile(2592, 3609, 0));
    public static RSArea OUTSIDE_LIGHTHOUSE = new RSArea(new RSTile(2502, 3635, 0), new RSTile(2521, 3619, 0));
    public static RSArea SECOND_FLOOR = new RSArea(new RSTile(2449, 4596, 1), new RSTile(2440, 4605, 1));
    public static RSArea SAFE_AREA = new RSArea(new RSTile(2513, 4654, 0), new RSTile(2511, 4655, 0));
    public static RSArea FIXED_THIRD_FLOOR = new RSArea(new RSTile(2503, 3645, 2), new RSTile(2513, 3636, 2));
    public static RSArea FIXED_SECOND_FLOOR = new RSArea(new RSTile(2512, 3636, 1), new RSTile(2505, 3644, 1));
    public static RSArea LEDGE = new RSArea(new RSTile(2516, 4627, 0), new RSTile(2513, 4630, 0));
    public static RSArea DAGGANOTH_AREA = new RSArea(new RSTile(2501, 4631, 0), new RSTile(2536, 4660, 0));
    public static RSArea ROCKS_AND_BEACH = new RSArea(new RSTile(2529, 3591, 0), new RSTile(2508, 3619, 0));
    public static RSArea ALL_ROCKS = new RSArea(new RSTile(2528, 3596, 0), new RSTile(2509, 3619, 0));
    public static RSArea ROCKS_1 = new RSArea(new RSTile(2524, 3597, 0), new RSTile(2519, 3598, 0));
    public static RSArea ROCKS_2 = new RSArea(new RSTile(2524, 3601, 0), new RSTile(2518, 3612, 0));
    public static RSArea BEFORE_ROCKS = new RSArea(new RSTile(2526, 3590, 0), new RSTile(2517, 3596, 0));
    public static RSArea ROCKS_3 = new RSArea(new RSTile(2512, 3613, 0), new RSTile(2517, 3610, 0));
    public static RSArea ROCKS_4 = new RSArea(new RSTile(2515, 3615, 0), new RSTile(2513, 3617, 0));
    public static RSArea END_BEACH = new RSArea(new RSTile(2519, 3618, 0), new RSTile(2506, 3625, 0));
    public static RSArea BOTTOM_OF_PIT = new RSArea(new RSTile(2514, 4631, 0), new RSTile(2519, 4635, 0));
    public static RSArea FIXED_GROUND_FLOOR = new RSArea(
            new RSTile[] {
                    new RSTile(2447, 4606, 0),
                    new RSTile(2443, 4606, 0),
                    new RSTile(2440, 4603, 0),
                    new RSTile(2440, 4598, 0),
                    new RSTile(2443, 4596, 0),
                    new RSTile(2447, 4596, 0),
                    new RSTile(2450, 4599, 0),
                    new RSTile(2450, 4603, 0)
            }
    );

}
