package scripts.Data;

import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import scripts.Data.Enums.Patches;

public class Const {

    public static int RANARR_SEED_ID = 5295;

    public static Patches faladorPatchObj = Patches.FALADOR_HERB_PATCH;
    public static Patches catherbyPatchObj = Patches.CATHERBY_HERB_PATCH;
    public static Patches ardougnePatchObj = Patches.ARDOUGNE_HERB_PATCH;
    public static Patches hosidiusPatchObj = Patches.HOSIDIUS_HERB_PATCH;
    public static Patches morytaniaPatchObj = Patches.MORYTANIA_HERB_PATCH;
    public static Patches guildPatchObj = Patches.FARMING_GUILD_HERB_PATCH;


    /**
     * OTHER
     */
    public int FARMING_GUILD_COMPOST_BIN = 34631;//nothing in it and whilte filling with watermellon and closed
    public int FAMRING_GUILD_COMPOST_FILL_VARBIT = 7912; // is 175 when full, and 97 when closed and fermenting

    public int FALADOR_COMPOST_BIN = 7836;//nothing in it and whilte filling with watermellon and closed
    public int FALADOR_COMPOST_FILL_VARBIT = 4961; // is 15 when full and fermenting


    public static final int[] STAMINA_POTION = {12625, 12627, 12629, 12631};
    public static final int[] SKILLS_NECKLACE = {11968, 11970, 11105, 11107, 11109};//does NOT include (1)

    public static int ARDOUNGE_TAB = 8011;
    public static int FENKENSTRAINS_TAB = 19621;
    public static int CAMELOT_TAB = 8010;
    public static int FALADOR_TAB = 8009;
    public static int VARROCK_TAB = 8007;
    public static int LUMBRIDGE_TAB = 8008;
    public static int HOUSE_TAB = 8013;
    public static int SAW = 8794;
    public static int SEED_DIBBER = 5343;
    public static int RAKE = 5341;
    public static int SUPERCOMPOST = 6034;
    public static int NOTED_CABAGES_10 = 5479;
    public static int SPADE = 952;
    public static int VOLCANIC_ASH = 21622;
    public static int BUCKET = 1925;
    public static int MAGIC_SECATEURS = 7409;
    public static int BOTTOMLESS_COMPOST = 22997;

    public static int LIMPWURT_SEEDS = 5100;
    /**
     * Allotments
     */
    public static int GUAM_SEEDS = 5291;
    public static int POTATO_SEEDS = 5318;
    public static int ONION_SEEDS = 5319;
    public static int CABBAGE_SEEDS = 5324;
    public static int TOMATO_SEEDS = 5322;
    public static int SWEETCORN_SEEDS = 5320;
    public static int STRAWBERRY_SEEDS = 5323;
    public static int WATERMELON_SEEDS = 5321;
    public static int SNAPEGRASS_SEEDS = 22879; //?


    public static int ULTRACOMPOST = 21483;
    public static int MARENTILL_SEEDS = 5292;
    public static int WEEDS = 6055;
    public static int LIMPWURT_ROOT = 225;

    public static int FARMING_GUILD_TREE_PATCH_ID = 33732;
    public static int FARMING_GUILD_HERB_PATCH_ID = 33979;
    public static int FARMING_GUILD_FLOWER_PATCH_ID = 33649;
    public static int FARMING_GUILD_BERRY_PATCH_ID = 34006;
    public static int FARMING_GUILD_CACTUS_PATCH_ID = 33761;
    public static int FARMING_GUILD_S_ALLOTMENT_PATCH_ID = 33693;
    public static int FARMING_GUILD_N_ALLOTMENT_PATCH_ID = 33694;

    public static int FALADOR_SE_ALLOTMENT_ID = 8551;
    public static int FALADOR_NW_ALLOTMENT_ID = 8550;
    public static int FALADOR_HERB_PATCH_ID = 8150;
    public static int FALADOR_FLOWER_PATCH_ID = 7847;

    public static int CATHERBY_S_ALLOTMENT_ID = 8553;
    public static int CATHERBY_N_ALLOTMENT_ID = 8552;
    public static int CATHERBY_HERB_PATCH_ID = 8151;
    public static int CATHERBY_FLOWER_PATCH_ID = 7848;

    public static int HOISIDIUS_SW_ALLOTMENT_ID = 27114;
    public static int HOISIDIUS_NE_ALLOTMENT_ID = 27113;
    public static int HOISIDIUS_HERB_PATCH_ID = 27115;
    public static int HOISIDIUS_FLOWER_PATCH_ID = 27111;

    public static int SNAPEGRASS = 231;
    public static int STRAWBERRIES = 5504;
    public static int WATERMELLON = 5982;
    public static int TOMATO = 1982;
    public static int CABBAGE = 1965; //if this doesnt work do 1967
    public static int SWEETCORN = 5986;

    public static int MORYTANIA_HERB_PATCH_ID = 8153;
    public static int MORYTANIA_FLOWER_PATCH_ID = 7850;
    public static int MORYTANIA_N_ALLOTMENT_ID = 8556;
    public static int MORYTANIA_S_ALLOTMENT_ID = 8557;

    public static int ARDOUGNE_N_ALLOTMENT_ID = 8554;
    public static int ARDOUGNE_S_ALLOTMENT_ID = 8555;
    public static int ARDOUGNE_HERB_PATCH_ID = 8152;
    public static int ARDOUGNE_FLOWER_PATCH_ID = 7849;

    public static int GUILD_FLOWER_PATCH_ID = 33649;
    public static int GUILD_HERB_PATCH_ID = 33979;
    public static int GUILD_TREE_PATCH_ID = 33732;

    public static int GNOME_STRONGHOLD_TREE_PATCH_ID = 19147;
    public static int LUMBRIDGE_TREE_PATCH_ID = 8391;
    public static int TAVERLY_TREE_PATCH_ID = 8388;
    public static int FALADOR_TREE_PATCH_ID = 8389;
    public static int VAROCK_TREE_PATCH_ID = 8390;

    public static int STRONGHOLD_FRUIT_TREE_ID = 7962;
    public static int TGV_FRUIT_TREE_ID = 7963;
    public static int BRIMHAVEN_FRUIT_TREE_ID = 7964;
    public static int CATHERBY_FRUIT_TREE_ID = 7965;

    public static int[] ALL_HERB_PATCH_IDS = {
            FALADOR_HERB_PATCH_ID,
            CATHERBY_HERB_PATCH_ID,
            ARDOUGNE_HERB_PATCH_ID,
            HOISIDIUS_HERB_PATCH_ID,
            MORYTANIA_HERB_PATCH_ID,
            FARMING_GUILD_HERB_PATCH_ID
    };

    public int[] WATERING_CAN = {5340, 5339, 5338, 5337, 5336, 5335, 5334, 5333}; //only need 3 in an inventory..
    public int EMPTY_WATERING_CAN = 5331;
    public int BAGGED_PLANT_1 = 8431; //31 xp each, use 17 to get level 6
    public int BAGGED_PLANT_2 = 8433; //70 xp each, 16 for level 6-12
    public int BAGGED_PLANT_3 = 8435; //100 xp each, 9 for level 12-15
    public int BAGGED_OAK_TREE = 8421; // 30 to level 20, or  70 to level 25

    public int TELEPORT_TO_HOUSE_TAB = 8013;
    public int ESTATE_AGENT_ID = 3097;
    public int SMALL_PLANT_SPACE_1 = 15366;
    public int SMALL_PLANT_SPACE_2 = 15367;
    public int PLANT = 5134;
    public int THISTLE = 5138;
    public int REEDS = 5139;
    public int OAK_TREE = 4533;
    public int BIG_TREE_SPACE_ID = 15362;

    public String TAVERLY_FARMER = "Alain";
    public String FALADOR_FARMER = "Heskel";
    public String LUMBRIDGE_FARMER = "Fayeth";
    public String VARROCK_FARMER = "Treznor";
    public String GNOME_FARMER = "Prissy Scilla";

    public RSArea VARROCK_AGENT_AREA = new RSArea(new RSTile(3242, 3473, 0), new RSTile(3238, 3477, 0));
    public RSArea OUTSIDE_HOUSE = new RSArea(new RSTile(2951, 3226, 0), new RSTile(2956, 3213, 0));
    public RSArea WATER_AREA = new RSArea(new RSTile(2968, 3209, 0), new RSTile(2965, 3211, 0));

}

