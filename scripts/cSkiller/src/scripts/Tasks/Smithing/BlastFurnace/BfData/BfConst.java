package scripts.Tasks.Smithing.BlastFurnace.BfData;

import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;

public class BfConst {

    public static final int[] US_BF_WORLDS = {
            355,
            356,
            357,
            386,
            494,
            495,
            496
    };


    public static final int[] STAMINA_POTION = {12625, 12627, 12629, 12631};
    public static final int[] SKILLS_NECKLACE = {11968, 11970, 11105, 11107, 11109, 11111};
    public static final int[] RING_OF_DUELING = {2552, 2554, 2556, 2558, 2560, 2562, 2564, 2566};
    public static final int[] RING_OF_WEALTH = {11980, 11982, 11984, 11986, 11988};
    public static final int[] AMULET_OF_GLORY = {11978, 11976, 1712, 1710, 1708, 1706};
    public static final int[] GAMES_NECKLACE = {3853, 3855, 3857, 3859, 3861, 3863, 3865, 3867};
    public static final int[] NECKLACE_OF_PASSAGE = {21146, 21149, 21151, 21153, 21155};
    public static final int[] PRAYER_POTION = {2434, 139, 141, 143};
    public static final int[] ANTIDOTE_PLUS_PLUS = {5952, 5954, 5956, 5956};

    public static final int OPEN_COAL_BAG = 24480;
    public static final int COAL_BAG = 12019;

    public static final int COAL = 453;
    public static final int MITHRIL_ORE = 449;
    public static final int GOLD_ORE = 444;
    public static final int ADAMANTITE_ORE = 449;
    public static final int RUNTITE_ORE = 451;
    public static final int IRON_ORE = 440;

    public static final int IRON_BAR = 2351;
    public static final int STEEL_BAR  = 2353;
    public static final int MITHRIL_BAR = 2359;
    public static final int GOLD_BAR = 2357;
    public static final int ADAMANTITE_BAR = 2361;
    public static final int RUNTITE_BAR = 2363;

    public static final int[] ALL_COAL_BAG = {24480,12019};
    public static final int ICE_GLOVES = 1580;
    public static final int GOLDSMITH_GAUNTLETS = 776;
    public static final int BUCKET = 1925;
    public static final int BUCKET_OF_WATER = 1929;

    public static  final  RSTile ORE_DEPOSIT_TILE = new RSTile(1942, 4967,0);
    public static  final RSTile BF_WALK_TO_TILE = new RSTile(1940, 4960,0);
    public static  final RSArea WHOLE_BF_AREA = new RSArea(new RSTile(1960, 4954, 0), new RSTile(1933, 4975, 0));
    public static  final RSTile BAR_COLLECTION_TILE = new RSTile(1940, 4962, 0);
    public static  final RSTile BAR_COLLECTION_TILE_LEFT_SIDE = new RSTile(1939, 4963, 0);
    public static  final RSTile BANK_TILE = new RSTile(1948, 4957, 0);
    public static  final RSArea QUEST_START_AREA = new RSArea(new RSTile(2835, 10128, 0), new RSTile(2840, 10124, 0));
}
