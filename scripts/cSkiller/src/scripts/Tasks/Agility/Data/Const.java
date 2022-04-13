package scripts.Tasks.Agility.Data;

public class Const {

    private static scripts.Data.Const consts;

    public static scripts.Data.Const get() {
        return consts == null ? consts = new scripts.Data.Const() : consts;
    }

    public int[] FOOD_IDS = {
            329, //salmon
            379, //lobster
            7946, // monkfish
            385, //shark
    };

    public static void reset() {
        consts = new scripts.Data.Const();
    }

    public final int[] STAMINA_POTION = {12625, 12627, 12629, 12631};
    public final int[] SKILLS_NECKLACE = {11968, 11970, 11105, 11107, 11109, 11111};
    public static final int[] SUMMER_PIE = {7218,7220};
    public static int MARK_OF_GRACE_ID = 11849;
    public static int EMPTY_PIE_DISH = 2313;


}
