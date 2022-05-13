package scripts.Tasks.Agility.Data;

import java.util.List;

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
    public static final int[] SUMMER_PIE = {7218, 7220};
    public static int MARK_OF_GRACE_ID = 11849;
    public static int EMPTY_PIE_DISH = 2313;

    public static List<Integer> SeersRT2 = List.of(4405, 4633, 1947, 4964, 682, 5011, 6017, 4654, 3731, 1214, 1322, 4283, 3872, 627, 2270, 4572, 2805, 4560, 626, 5182, 733, 5423, 1988, 628, 733, 647, 870, 776, 1622, 97, 1729, 931, 1046, 544, 1228, 2072, 1230, 1486, 1130, 931);
    public static List<Integer> SeersRT0 = List.of(742, 850, 117, 554, 512, 1924, 442, 457, 631, 439, 605, 714, 1074, 484, 744, 696, 625, 164, 438, 1811, 813, 938, 420, 810, 944, 167, 797, 798, 430, 1707, 415, 717, 162, 709, 447, 1079, 5729, 227, 1286, 714, 719, 364, 122, 676, 496, 765, 14, 945, 737);

}
