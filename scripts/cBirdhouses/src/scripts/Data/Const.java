package scripts.Data;

public class Const {

    private static Const consts;

    public static Const get() {
        return consts == null ? consts = new Const() : consts;
    }

    public int MONKFISH = 7946;
    public int[] FOOD_IDS = {
            329, //salmon
            361, // tuna
            379, //lobster
            7946, // monkfish
            385, //shark
    };

    public static void reset() {
        consts = new Const();
    }

    public final int[] STAMINA_POTION = {12625, 12627, 12629, 12631};
    public final int[] SKILLS_NECKLACE = {11968, 11970, 11105, 11107, 11109, 11111};
    public final int[] RING_OF_DUELING = {2552, 2554, 2556, 2558, 2560, 2562, 2564, 2566};
    public final int[] RING_OF_WEALTH = {11980, 11982, 11984, 11986, 11988};
    public final int[] AMULET_OF_GLORY = {11978, 11976, 1712, 1710, 1708, 1706};
    public final int[] GAMES_NECKLACE = {3853, 3855, 3857, 3859, 3861, 3863, 3865, 3867};
    public final int[] NECKLACE_OF_PASSAGE = {21146, 21149, 21151, 21153, 21155};
    public final int[] PRAYER_POTION = {2434, 139, 141, 143};
    public final int[] ANTIDOTE_PLUS_PLUS = {5952, 5954, 5956, 5956};

    public int COSMIC_RUNE = 564;
    public int SPACE_ONE_ID = 30566;
    public int SPACE_TWO_ID = 30565;
    public int SPACE_THREE_ID = 30568;
    public int SPACE_FOUR_ID = 30567;

    public int BARLEY_SEED_ID = 5305;

    public int[] ALL_BIRDHOUSE_IDS = {
            21512,
            21515,
            21518,
            21521,
            22192,
            22195,
            22198,
            22201,
            22204
    };


    public int[] DIGSITE_PENDANT = {
            11190, //1
            11191, //2
            11192, //3
            11193, //4
            11194, //5
    };

    public int MUSH_INTERFACE_MASTER = 608;
    public int MUSH_INTERFACE_CHILD_2 = 8;

    public int ENCHANT_RUBY_TABLET = 8018;
    public int RUBY_NECKLACE = 1660;

    public int BIRDHOUSE_ID = 21512;
    public int OAK_BIRDHOUSE_ID = 21515;
    public int WILLOW_BIRDHOUSE_ID = 21518;
    public int TEAK_BIRDHOUSE_ID = 21521;
    public int MAPLE_BIRDHOUSE_ID = 22192;
    public int MAHOGANY_BIRDHOUSE_ID = 22195;
    public int YEW_BIRDHOUSE_ID = 22198;
    public int MAGIC_BIRDHOUSE_ID = 22201;
    public int REDWOOD_BIRDHOUSE_ID = 22204;

    public int CLOCKWORK = 8792;
    public int CHISEL = 1755;
    public int LOGS = 1511;
    public int OAK_LOGS = 1521;
    public int WILLOW_LOGS = 1519;
    public int TEAK_LOGS = 6333;
    public int MAPLE_LOGS = 1517;
    public int MAHOGANY_LOGS = 6332;
    public int YEW_LOGS = 1515;
    public int MAGIC_LOGS = 1513;
    public int REDWOOD_LOGS = 19669;
    public int HAMMER = 2347;

    public int VARROCK_TELEPORT = 8007;
    public int FALADOR_TELEPORT = 8009;
    public int CAMELOT_TELEPORT = 8010;
    public int LUMBRIDGE_TELEPORT = 8008;
    public int ARDOUGNE_TELEPORT = 8011;
}
