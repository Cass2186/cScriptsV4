package scripts.Data;

import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.Skill;
import org.tribot.script.sdk.types.WorldTile;

public class Const {

    private static Const consts;

    public static Const get() {
        return consts == null ? consts = new Const() : consts;
    }

    public int[] FOOD_IDS = {
            329, //salmon
            379, //lobster
            7946, // monkfish
            385, //shark
    };

    public static int CHOMPY_ID = 1475;
    public static int TOAD_ID = 1473;
    public static void reset() {
        consts = new Const();
    }

    public final int[] STAMINA_POTION = {12625, 12627, 12629, 12631};
    public final int[] SKILLS_NECKLACE = {11968, 11970, 11105, 11107, 11109, 11111};
    public final int[] RING_OF_WEALTH = {11980, 11982, 11984, 11986, 11988};
    public final int[] AMULET_OF_GLORY = {11978, 11976, 1712, 1710, 1708, 1706};
    public final int[] GAMES_NECKLACE = {3853, 3855, 3857, 3859, 3861, 3863, 3865, 3867};
    public final int[] NECKLACE_OF_PASSAGE = {21146, 21149, 21151, 21153, 21155};
    public final int[] PRAYER_POTION = {2434, 139, 141, 143};
    public final int[] ANTIDOTE_PLUS_PLUS = {5952, 5954, 5956, 5956};

    public static final int JUG_OF_WINE = 1993;
    public static final int EMPTY_JUG = 1935;
    public static  int BELLOWS = 2871;
    public static  int[] INFLATED_BELLOWS = {2872, 2873, 2874};
    public static int BLOATED_TOAD = 2875;
    public static int NPC_BLOATED_TOAD = 1474;
    public static   RSArea TEAK_AREA = new RSArea(new RSTile(2324, 3063, 0), new RSTile(2344, 3050, 0));
    public static  RSArea TOAD_AREA = new RSArea(new RSTile(2592, 2972, 0), new RSTile(2605, 2961, 0));

    public static WorldTile DROP_TILE = new WorldTile(2330, 3057,0);

    public static int START_RANGED_XP = Skill.RANGED.getXp();


}
