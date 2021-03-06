package scripts.Tasks.Hunter.HunterData;

import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.types.Area;
import org.tribot.script.sdk.types.WorldTile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HunterConst {
    public static int BIRD_SNARE = 10006;
    public static int DISABLED_BIRD_SNARE = 9344;
    public static int LAID_BIRD_SNARE = 9345;
    public static int CAUGHT_BIRD_SNARE = 9348;
    public static int FELDIP_HILLS_TELEPORT = 12404;
    public static int PISCARTORIS_TELEPROT = 12408;
    public static int RAW_BIRD_MEAT = 9978;
    public static int BONES = 526;
    public static int ROPE = 954;
    public static int SMALL_FISHING_NET = 303;
    public static int SWAMP_LIZARD = 10149;
    public static int BOX_TRAP_ITEM_ID = 10008;
    public static int SET_UP_BOX_TRAP = 9380;
    public static int SHAKING_BOX_TRAP = 9383;
    public static int FAILED_BOX_TRAP = 9385;
    public static int RED_CHINS_NPC_ID = 2911;
    public static int DESERT_LIZARD = 10146;
    public static int[] ALL_SALAMANDERS = {
            DESERT_LIZARD,
            SWAMP_LIZARD};

    public static Area FALCONRY_AREA = Area.fromPolygon(
            new WorldTile(2367, 3613, 0),
            new WorldTile(2380, 3613, 0),
            new WorldTile(2385, 3609, 0),
            new WorldTile(2385, 3605, 0),
            new WorldTile(2390, 3594, 0),
            new WorldTile(2391, 3589, 0),
            new WorldTile(2385, 3588, 0),
            new WorldTile(2384, 3583, 0),
            new WorldTile(2380, 3579, 0),
            new WorldTile(2378, 3575, 0),
            new WorldTile(2365, 3575, 0),
            new WorldTile(2365, 3584, 0),
            new WorldTile(2368, 3586, 0),
            new WorldTile(2368, 3594, 0),
            new WorldTile(2366, 3595, 0),
            new WorldTile(2366, 3600, 0),
            new WorldTile(2368, 3602, 0)
    );

    /**
     * NET TRAPPING ANIMATIONS
     */

    public static int YOUNG_TREE_ID = 9341;
    public static int NET_TRAP_RESTING = 9343;
    public static int NET_TRAP_ENGAGED = 9003; // very brief
    public static int NET_TRAP_SUCCESSFUL = 9004;

    public static RSArea COPPER_LONGTAIL_AREA = new RSArea(new RSTile(2335, 3603, 0), new RSTile(2346, 3595, 0));
    public static RSTile COPPER_LONGTAIL_TILE = new RSTile(2343, 3600, 0);

    public static RSArea TROPICAL_WAGTAIL_AREA = new RSArea(new RSTile(2505, 2916, 0), new RSTile(2514, 2912, 0));
    public static RSTile TROPICAL_WAGTAIL_TILE_1 = new RSTile(2508, 2914, 0);
    public static RSTile TROPICAL_WAGTAIL_TILE_2 = new RSTile(2511, 2912, 0);

    public static RSArea CANIFIS_SWAMP_AREA = new RSArea(new RSTile[]{new RSTile(3532, 3452, 0), new RSTile(3529, 3446, 0), new RSTile(3537, 3443, 0), new RSTile(3546, 3450, 0), new RSTile(3541, 3454, 0)});
    public static RSTile CANFIS_TRAP_1 = new RSTile(3532, 3447, 0);
    public static RSTile CANFIS_TRAP_2 = new RSTile(3538, 3445, 0);
    public static RSTile CANFIS_TRAP_3 = new RSTile(3536, 3451, 0);

    public static List<RSTile> CANFIS_TRAP_TILES = new ArrayList<>(Arrays.asList(CANFIS_TRAP_1, CANFIS_TRAP_2, CANFIS_TRAP_3));
    public static Area CANIFIS_HUNTER_AREA = Area.fromPolygon(
            new WorldTile[]{
                    new WorldTile(3531, 3448, 0),
                    new WorldTile(3535, 3443, 0),
                    new WorldTile(3541, 3445, 0),
                    new WorldTile(3545, 3451, 0),
                    new WorldTile(3536, 3454, 0)
            }
    );

    // YELLOW SALS

    public static WorldTile N_YELLOW_SAL_TREE = new WorldTile(3407, 3093, 0);
    public static WorldTile E_YELLOW_SAL_TREE = new WorldTile(3404, 3092, 0);
    public static WorldTile S_YELLOW_SAL_TREE = new WorldTile(3407, 3088, 0);
    public static Area YELLOW_SAL_AREA = Area.fromPolygon(
            new WorldTile[]{
                    new WorldTile(3408, 3095, 0),
                    new WorldTile(3403, 3093, 0),
                    new WorldTile(3406, 3086, 0),
                    new WorldTile(3413, 3086, 0),
                    new WorldTile(3414, 3094, 0)
            }
    );
    public static List<WorldTile> YELLOW_SALAMANDER_TREES = new ArrayList<>(Arrays.asList(N_YELLOW_SAL_TREE,
            E_YELLOW_SAL_TREE, S_YELLOW_SAL_TREE));
    /**
     * RED SALAMANDERS
     */
    public static int RED_SALAMANDER_ITEM = 10147;

    public static RSTile N_RED_SAL_TREE = new RSTile(2449, 3228, 0);
    public static RSTile E_RED_SAL_TREE = new RSTile(2451, 3225, 0);
    public static RSTile W_RED_SAL_TREE = new RSTile(2447, 3225, 0);
    public static RSTile S_RED_SAL_TREE = new RSTile(2453, 3219, 0);
    public static List<RSTile> RED_SALAMANDER_TREES = new ArrayList<>(Arrays.asList(
            N_RED_SAL_TREE, E_RED_SAL_TREE,
            W_RED_SAL_TREE, S_RED_SAL_TREE));

}
