package scripts.Data;

import org.tribot.script.sdk.Skill;
import scripts.Utils;

public class Const {

    public static final int START_VALUE = Utils.getInventoryValue();
    public static final  int START_FARM_XP = Skill.FARMING.getXp();

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


}
