package scripts.Tasks.Slayer.SlayerConst;

import org.tribot.script.sdk.interfaces.Tile;
import org.tribot.script.sdk.types.Area;
import org.tribot.script.sdk.types.WorldTile;
import org.tribot.script.sdk.types.Area;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Areas {

    /**
     * just before shed to zanaris
     */
    public static Area SWAMP_AREA = Area.fromRectangle(new WorldTile(3201, 3170, 0), new WorldTile(3199, 3167, 0));
    public static Area SOURHOG_ENTRANCE_AREA = Area.fromRectangle(new WorldTile(3147, 3348, 0), new WorldTile(3151, 3344, 0));
    public static Area TREE_STRONGHOLD_FAILSAFE = Area.fromRadius(new WorldTile(2461, 3444, 0), 5);
    public static Area NIEVE_AREA = Area.fromRadius(new WorldTile(2433, 3423, 0), 4);
    public static WorldTile BRONZE_DRAGON_SAFE_TILE = new WorldTile(1655, 10103, 0);
    public static Area BRONZE_DRAGON_SAFE_AREA = Area.fromRectangle(new WorldTile(1655, 10103, 0), new WorldTile(1655, 10103, 0));
    public static Area ABYSSAL_DEMON_AREA = Area.fromPolygon(
            new ArrayList<>(Arrays.asList(
                    new WorldTile(1666, 10054, 0),
                    new WorldTile(1669, 10050, 0),
                    new WorldTile(1674, 10056, 0),
                    new WorldTile(1675, 10056, 0),
                    new WorldTile(1676, 10055, 0),
                    new WorldTile(1678, 10055, 0),
                    new WorldTile(1679, 10054, 0),
                    new WorldTile(1679, 10053, 0),
                    new WorldTile(1681, 10055, 0),
                    new WorldTile(1681, 10064, 0),
                    new WorldTile(1679, 10062, 0),
                    new WorldTile(1675, 10063, 0),
                    new WorldTile(1672, 10063, 0),
                    new WorldTile(1672, 10061, 0),
                    new WorldTile(1673, 10060, 0),
                    new WorldTile(1673, 10059, 0)
            )
            ));

    public static Area BABY_GREEN_DRAGON_AREA = Area.fromPolygon(
            new WorldTile(2659, 9587, 2),
            new WorldTile(2658, 9582, 2),
            new WorldTile(2660, 9569, 2),
            new WorldTile(2668, 9576, 2),
            new WorldTile(2676, 9567, 2),
            new WorldTile(2684, 9569, 2),
            new WorldTile(2681, 9576, 2),
            new WorldTile(2676, 9578, 2),
            new WorldTile(2671, 9585, 2),
            new WorldTile(2664, 9585, 2)
    );
    public static Area VAMPYRE_AREA = Area.fromRadius(new WorldTile(3578, 3480, 0), 15);
    public static WorldTile WALL_BEAST_TILE = new WorldTile(3162, 9573, 0);
    public static Area GOBLIN_AREA = Area.fromPolygon(
            new WorldTile(3239, 3250, 0),
            new WorldTile(3259, 3250, 0),
            new WorldTile(3261, 3223, 0),
            new WorldTile(3249, 3223, 0),
            new WorldTile(3242, 3235, 0));
    public static Area OTHERWORLDLY_BEING_AREA = Area.fromRectangle(new WorldTile(2376, 4434, 0), new WorldTile(2393, 4416, 0));
    public static Area OGRE_AREA = Area.fromRectangle(new WorldTile(2572, 2971, 0), new WorldTile(2602, 2958, 0));
    public static Area ABBERANT_SPECTRE_AREA_1 = Area.fromRectangle(new WorldTile(2467, 9786, 0), new WorldTile(2474, 9773, 0));
    public static Area WHOLE_ZANARIS = Area.fromRectangle(new WorldTile(2508, 4404, 0), new WorldTile(2369, 4481, 0));
    public static Area CHAELDAR_AREA = Area.fromRectangle(new WorldTile(2442, 4432, 0), new WorldTile(2451, 4425, 0));
    public static Area ABOVE_LUMBRIDGE_SWAMP_ENTRANCE = Area.fromRectangle(new WorldTile(3172, 3170, 0), new WorldTile(3167, 3174, 0));
    public static Area ABBERANT_SPECTRES_AREA = Area.fromRectangle(new WorldTile(2467, 9786, 0), new WorldTile(2474, 9775, 0));
    public static Area ANKOU_AREA = Area.fromRectangle(new WorldTile(2486, 9795, 0), new WorldTile(2466, 9810, 0));
    public static Area BASILISK_AREA = Area.fromRectangle(new WorldTile(2752, 9998, 0), new WorldTile(2734, 10018, 0));
    public static Area BANSHEE_AREA = Area.fromPolygon(
            new WorldTile(3449, 3568, 0),
            new WorldTile(3433, 3568, 0),
            new WorldTile(3433, 3557, 0),
            new WorldTile(3430, 3557, 0),
            new WorldTile(3430, 3547, 0),
            new WorldTile(3437, 3547, 0),
            new WorldTile(3437, 3541, 0),
            new WorldTile(3432, 3541, 0),
            new WorldTile(3432, 3535, 0),
            new WorldTile(3434, 3533, 0),
            new WorldTile(3438, 3533, 0),
            new WorldTile(3439, 3534, 0),
            new WorldTile(3442, 3534, 0),
            new WorldTile(3445, 3531, 0),
            new WorldTile(3449, 3531, 0),
            new WorldTile(3453, 3535, 0),
            new WorldTile(3453, 3539, 0),
            new WorldTile(3449, 3543, 0),
            new WorldTile(3449, 3547, 0),
            new WorldTile(3450, 3548, 0),
            new WorldTile(3450, 3552, 0),
            new WorldTile(3449, 3553, 0)
    );


    public static Area BAT_AREA = Area.fromPolygon(
            new WorldTile(2899, 9845, 0), new WorldTile(2899, 9823, 0), new WorldTile(2927, 9823, 0), new WorldTile(2933, 9832, 0), new WorldTile(2931, 9836, 0), new WorldTile(2920, 9845, 0)
    );
    public static Area BEAR_AREA = Area.fromPolygon(
            new WorldTile(2717, 3327, 0),
            new WorldTile(2689, 3327, 0),
            new WorldTile(2689, 3347, 0),
            new WorldTile(2711, 3342, 0),
            new WorldTile(2718, 3337, 0)

    );
    //public static Area BEAR_AREA = Area.fromRectangle(new WorldTile(2714, 3329, 0), new WorldTile(2680, 3346, 0));
    // public static Area BLUE_DRAGON_AREA = Area(new WorldTile(2922, 9796, 0), new WorldTile(2913, 9807, 0));
    public static Area BLUE_DRAGON_AREA = Area.fromPolygon(
            new ArrayList<>(Arrays.asList(
                    new WorldTile(2907, 9814, 0),
                    new WorldTile(2907, 9794, 0),
                    new WorldTile(2927, 9784, 0),
                    new WorldTile(2926, 9795, 0),
                    new WorldTile(2922, 9799, 0),
                    new WorldTile(2922, 9809, 0)
            )));
    public static Area JACKAL_AREA = Area.fromPolygon(
            new ArrayList<>(Arrays.asList(
                    new WorldTile(3321, 2938, 0),
                    new WorldTile(3324, 2921, 0),
                    new WorldTile(3335, 2925, 0),
                    new WorldTile(3335, 2929, 0),
                    new WorldTile(3349, 2930, 0),
                    new WorldTile(3349, 2940, 0),
                    new WorldTile(3334, 2942, 0)
            )));
    //  public static Area BLACK_DEMON_AREA = Area(new WorldTile(1716, 10091, 0), new WorldTile(1725, 10079, 0));
    public static Area BLACK_DEMON_AREA = Area.fromRectangle(new WorldTile(1419, 10100, 1), new WorldTile(1459, 10059, 1));
    public static Area BLOODVELD_AREA = Area.fromRectangle(new WorldTile(2479, 9828, 0), new WorldTile(2461, 9837, 0));
    public static Area BIRD_AREA = Area.fromRectangle(new WorldTile(3226, 3300, 0), new WorldTile(3235, 3295, 0));
    // blue dragon area is the area with baby blue drags.

    public static Area BEFORE_BABY_BLACK_DRAG_AREA = Area.fromRectangle(new WorldTile(2879, 9828, 0), new WorldTile(2885, 9821, 0));
    public static Area BABY_BLACK_DRAGON_AREA = Area.fromRectangle(new WorldTile(2852, 9830, 1), new WorldTile(2865, 9823, 1));
    public static Area CAVE_BUG_AREA = Area.fromRectangle(new WorldTile(3144, 9580, 0), new WorldTile(3163, 9557, 0));
    public static Area CAVE_CRAWLER_AREA = Area.fromPolygon(
            new ArrayList<>(Arrays.asList(
                    new WorldTile(2780, 10006, 0), new WorldTile(2772, 9999, 0), new WorldTile(2786, 9986, 0), new WorldTile(2805, 9994, 0), new WorldTile(2801, 10007, 0)
            )));
    public static Area CAVE_SLIME_AREA = Area.fromPolygon(
            new ArrayList<>(Arrays.asList(
                    new WorldTile(3143, 9582, 0), new WorldTile(3161, 9582, 0), new WorldTile(3160, 9564, 0), new WorldTile(3163, 9564, 0), new WorldTile(3163, 9551, 0), new WorldTile(3154, 9545, 0), new WorldTile(3142, 9547, 0)
            )));
    public static Area CRAWLING_HANDS_AREA = Area.fromPolygon(
            new ArrayList<>(Arrays.asList(
                    new WorldTile(3431, 3536, 0), new WorldTile(3408, 3536, 0), new WorldTile(3408, 3542, 0), new WorldTile(3409, 3543, 0), new WorldTile(3409, 3553, 0), new WorldTile(3431, 3553, 0)
            )));
    public static Area COW_AREA = Area.fromPolygon(
            new ArrayList<>(Arrays.asList(
                    new WorldTile(3243, 3298, 0), new WorldTile(3243, 3282, 0), new WorldTile(3249, 3278, 0), new WorldTile(3251, 3276, 0), new WorldTile(3253, 3272, 0), new WorldTile(3253, 3255, 0), new WorldTile(3264, 3255, 0), new WorldTile(3264, 3298, 0)
            )));
    public static Area COCKATRICE_AREA = Area.fromRectangle(new WorldTile(2782, 10045, 0), new WorldTile(2807, 10027, 0));
    public static Area CROCODILE_AREA = Area.fromPolygon(
            new ArrayList<>(Arrays.asList(
                    new WorldTile(3337, 2925, 0), new WorldTile(3341, 2928, 0), new WorldTile(3355, 2923, 0), new WorldTile(3361, 2929, 0), new WorldTile(3364, 2915, 0), new WorldTile(3335, 2914, 0)
            )));
    public static WorldTile DAGGANOTH_HOP_TILE = new WorldTile(2515, 10004, 0);
    public static Area DOG_AREA = Area.fromPolygon(
            new ArrayList<>(Arrays.asList(
                    new WorldTile(2642, 3308, 0), new WorldTile(2643, 3309, 0), new WorldTile(2643, 3315, 0),
                    new WorldTile(2647, 3319, 0),
                    new WorldTile(2647, 3329, 0), new WorldTile(2646, 3329, 0), new WorldTile(2643, 3332, 0), new WorldTile(2643, 3326, 0), new WorldTile(2644, 3326, 0), new WorldTile(2644, 3320, 0),
                    new WorldTile(2627, 3320, 0), new WorldTile(2627, 3326, 0), new WorldTile(2624, 3326, 0), new WorldTile(2624, 3315, 0), new WorldTile(2625, 3315, 0), new WorldTile(2626, 3314, 0), new WorldTile(2626, 3309, 0), new WorldTile(2627, 3308, 0)
            )));
    public static Area BLACK_DRAGON_AREA = Area.fromRectangle(new WorldTile(2828, 9829, 0), new WorldTile(2843, 9821, 0));
    public static Area INFERNAL_MAGE_AREA = Area.fromRectangle(new WorldTile(3430, 3554, 1), new WorldTile(3452, 3579, 1));
    public static Area ICE_GIANT_AREA = Area.fromPolygon(
            new ArrayList<>(Arrays.asList(
                    new WorldTile(3041, 9588, 0), new WorldTile(3041, 9575, 0), new WorldTile(3054, 9565, 0), new WorldTile(3064, 9569, 0), new WorldTile(3067, 9580, 0), new WorldTile(3064, 9588, 0)
            )));
    public static Area ICE_WARRIOR_AREA = Area.fromPolygon(
            new ArrayList<>(Arrays.asList(
                    new WorldTile(3038, 9586, 0),
                    new WorldTile(3043, 9593, 0),
                    new WorldTile(3050, 9593, 0),
                    new WorldTile(3050, 9572, 0),
                    new WorldTile(3044, 9572, 0),
                    new WorldTile(3039, 9575, 0)
            )));
    public static Area DAGGANOTH_AREA = Area.fromRectangle(new WorldTile(2510, 10036, 0), new WorldTile(2541, 10011, 0));
    public static Area DWARF_AREA = Area.fromPolygon(
            new ArrayList<>(Arrays.asList(
                    new WorldTile(3020, 3446, 0), new WorldTile(3011, 3446, 0), new WorldTile(3011, 3452, 0), new WorldTile(3015, 3452, 0), new WorldTile(3015, 3455, 0), new WorldTile(3008, 3455, 0), new WorldTile(3008, 3460, 0), new WorldTile(3015, 3463, 0), new WorldTile(3024, 3463, 0), new WorldTile(3024, 3449, 0), new WorldTile(3021, 3449, 0)
            )));
    public static Area ELF_AREA = Area.fromRadius(new WorldTile(2203, 3254, 0), 12);
    public static Area PRE_EARTH_WARRIOR_AREA = Area.fromRectangle(new WorldTile(3119, 9964, 0), new WorldTile(3122, 9961, 0));
    public static Area EARTH_WARRIOR_AREA = Area.fromRectangle(new WorldTile(3128, 9969, 0), new WorldTile(3113, 9981, 0));
    public static Area FIRE_GIANT_AREA = Area.fromRectangle(new WorldTile(2391, 9774, 0), new WorldTile(2408, 9767, 0));
    public static Area GREATER_DEMON_AREA = Area.fromRectangle(new WorldTile(1679, 10091, 0), new WorldTile(1691, 10081, 0));
    public static Area GARGOYLE_AREA = Area.fromPolygon(
            new ArrayList<>(Arrays.asList(
                    new WorldTile(3429, 9952, 3),
                    new WorldTile(3442, 9952, 3),
                    new WorldTile(3442, 9927, 3),
                    new WorldTile(3437, 9931, 3),
                    new WorldTile(3429, 9931, 3)
            ))
    );
    public static Area GREEN_DRAGON_AREA = Area.fromRectangle(new WorldTile(3342, 3663, 0), new WorldTile(3318, 3697, 0));
    public static Area GHOST_AREA = Area.fromRectangle(new WorldTile(2924, 9821, 0), new WorldTile(2895, 9817, 0));
    public static Area GHOUL_AREA = Area.fromPolygon(
            new ArrayList<>(Arrays.asList(
                    new WorldTile(3408, 3513, 0), new WorldTile(3409, 3506, 0), new WorldTile(3411, 3504, 0), new WorldTile(3411, 3501, 0), new WorldTile(3416, 3498, 0), new WorldTile(3416, 3495, 0), new WorldTile(3420, 3495, 0), new WorldTile(3420, 3492, 0), new WorldTile(3432, 3492, 0), new WorldTile(3432, 3516, 0), new WorldTile(3408, 3517, 0)
            )));
    // public static Area GOBLIN_AREA = Area(new WorldTile[]{new WorldTile(3239, 3243, 0), new WorldTile(3258, 3244, 0), new WorldTile(3259, 3227, 0), new WorldTile(3245, 3227, 0)});
    public static Area HARPIE_BUG_SWARM_AREA = Area.fromRectangle(new WorldTile(2860, 3115, 0), new WorldTile(2868, 3107, 0));
    public static Area HELLHOUND_AREA = Area.fromRectangle(new WorldTile(2404, 9807, 0), new WorldTile(2421, 9792, 0));
    public static Area HOBGOBLIN_AREA = Area.fromPolygon(
            new ArrayList<>(Arrays.asList(
                    new WorldTile(3113, 9875, 0),
                    new WorldTile(3120, 9881, 0),
                    new WorldTile(3127, 9881, 0),
                    new WorldTile(3134, 9876, 0),
                    new WorldTile(3134, 9868, 0),
                    new WorldTile(3114, 9868, 0)
            )));
    public static Area BRINE_RAT_AREA = Area.fromRectangle(new WorldTile(2700, 10138, 0), new WorldTile(2713, 10128, 0));
    public static Area WHOLE_BRINE_RAT_CAVE = Area.fromRectangle(new WorldTile(2685, 10153, 0), new WorldTile(2742, 10116, 0));
    public static Area HILL_GIANT_AREA = Area.fromPolygon(
            new ArrayList<>(Arrays.asList(
                    new WorldTile(3112, 9854, 0), new WorldTile(3128, 9854, 0), new WorldTile(3127, 9819, 0), new WorldTile(3092, 9824, 0), new WorldTile(3094, 9839, 0)
            )));
    public static Area ICEFIEND_AREA = Area.fromPolygon(
            new ArrayList<>(Arrays.asList(
                    new WorldTile(3025, 3466, 0), new WorldTile(2995, 3467, 0), new WorldTile(3002, 3481, 0), new WorldTile(3006, 3489, 0), new WorldTile(3012, 3489, 0), new WorldTile(3014, 3479, 0), new WorldTile(3022, 3476, 0)
            )));
    public static Area JELLY_AREA = Area.fromRectangle(new WorldTile(2716, 10037, 0), new WorldTile(2694, 10019, 0));
    public static Area KALPHITE_AREA = Area.fromPolygon(
            new ArrayList<>(Arrays.asList(
                    new WorldTile(3334, 9499, 0), new WorldTile(3326, 9490, 0), new WorldTile(3315, 9493, 0), new WorldTile(3316, 9511, 0), new WorldTile(3325, 9516, 0), new WorldTile(3334, 9509, 0)
            )));
    // public static Area KURASK_AREA = Area(new WorldTile(2687, 10009, 0), new WorldTile(2708, 9989, 0));
    public static Area KURASK_AREA = Area.fromRectangle(new WorldTile(2691, 9982, 0), new WorldTile(2723, 9952, 0));
    public static Area LIZARD_AREA = Area.fromRectangle(new WorldTile(3379, 3083, 0), new WorldTile(3414, 3043, 0));
    public static Area LESSER_DEMON_AREA = Area.fromPolygon(
            new ArrayList<>(Arrays.asList(
                    new WorldTile(2924, 9813, 0), new WorldTile(2924, 9796, 0), new WorldTile(2929, 9796, 0), new WorldTile(2930, 9785, 0), new WorldTile(2943, 9785, 0), new WorldTile(2941, 9798, 0), new WorldTile(2936, 9810, 0), new WorldTile(2934, 9813, 0)
            )));
    public static Area MUTATED_ZYGOMITE_AREA = Area.fromRectangle(new WorldTile(2426, 4462, 0), new WorldTile(2409, 4478, 0));
    public static Area MONKEY_AREA = Area.fromRectangle(new WorldTile(2892, 3148, 0), new WorldTile(2865, 3163, 0));
    // public static Area MOSS_GIANT_AREA = Area(new WorldTile(2548, 3411, 0), new WorldTile(2562, 3393, 0));
    //this is in catacombs
    public static Area MOSS_GIANT_AREA = Area.fromPolygon(
            new ArrayList<>(Arrays.asList(
                    new WorldTile(1668, 10020, 0),
                    new WorldTile(1677, 10020, 0),
                    new WorldTile(1674, 10015, 0),
                    new WorldTile(1675, 10008, 0),
                    new WorldTile(1672, 10006, 0),
                    new WorldTile(1670, 10006, 0),
                    new WorldTile(1666, 10003, 0),
                    new WorldTile(1664, 10003, 0),
                    new WorldTile(1662, 10005, 0),
                    new WorldTile(1657, 10002, 0),
                    new WorldTile(1650, 10002, 0),
                    new WorldTile(1650, 10008, 0),
                    new WorldTile(1654, 10011, 0),
                    new WorldTile(1654, 10015, 0),
                    new WorldTile(1660, 10016, 0),
                    new WorldTile(1664, 10016, 0))));
    public static Area MOSS_GIANT_FISHING_GUILD_AREA = Area.fromRadius(
            new WorldTile(2555, 3406, 0), 5);

    public static Area MINOTAUR_AREA = Area.fromPolygon(
            new ArrayList<>(Arrays.asList(
                    new WorldTile(1869, 5228, 0),
                    new WorldTile(1869, 5224, 0),
                    new WorldTile(1875, 5224, 0),
                    new WorldTile(1876, 5245, 0),
                    new WorldTile(1868, 5245, 0),
                    new WorldTile(1866, 5237, 0)
            )));

    public static Area PYREFIEND_AREA = Area.fromPolygon(
            new ArrayList<>(Arrays.asList(
                    new WorldTile(2752, 10015, 0), new WorldTile(2769, 10015, 0), new WorldTile(2767, 10008, 0), new WorldTile(2770, 10003, 0), new WorldTile(2770, 9992, 0), new WorldTile(2752, 9993, 0)
            )));

    public static Area RAT_AREA = Area.fromRectangle(new WorldTile(3227, 3170, 0), new WorldTile(3208, 3191, 0));
    public static Area ROCKSLUG_AREA = Area.fromRectangle(new WorldTile(2811, 10024, 0), new WorldTile(2789, 10008, 0));

    public static Area SOURHOG_AREA = Area.fromPolygon(
            new ArrayList<>(Arrays.asList(
                    new WorldTile(3156, 9704, 0),
                    new WorldTile(3158, 9704, 0),
                    new WorldTile(3167, 9706, 0),
                    new WorldTile(3167, 9706, 0),
                    new WorldTile(3170, 9706, 0),
                    new WorldTile(3174, 9704, 0),
                    new WorldTile(3178, 9693, 0),
                    new WorldTile(3185, 9687, 0),
                    new WorldTile(3178, 9673, 0),
                    new WorldTile(3169, 9667, 0),
                    new WorldTile(3159, 9674, 0),
                    new WorldTile(3151, 9703, 0)
            )));
    public static Area SUQAH_AREA = Area.fromRectangle(new WorldTile(2110, 3947, 0), new WorldTile(2121, 3939, 0));
    public static Area SCORPION_AREA = Area.fromPolygon(
            new ArrayList<>(Arrays.asList(
                    new WorldTile(3306, 3278, 0), new WorldTile(3306, 3283, 0), new WorldTile(3302, 3287, 0), new WorldTile(3302, 3288, 0), new WorldTile(3306, 3292, 0), new WorldTile(3306, 3294, 0), new WorldTile(3303, 3298, 0), new WorldTile(3292, 3297, 0), new WorldTile(3293, 3285, 0), new WorldTile(3290, 3283, 0), new WorldTile(3292, 3281, 0), new WorldTile(3293, 3279, 0)
            )));
    public static Area SKELETON_AREA = Area.fromPolygon(
            new ArrayList<>(Arrays.asList(
                    new WorldTile(3134, 9881, 0), new WorldTile(3144, 9881, 0), new WorldTile(3144, 9875, 0), new WorldTile(3146, 9873, 0), new WorldTile(3152, 9873, 0), new WorldTile(3152, 9870, 0), new WorldTile(3150, 9868, 0), new WorldTile(3136, 9868, 0), new WorldTile(3134, 9870, 0)
            )));
    public static Area SPIDER_AREA = Area.fromPolygon(
            new ArrayList<>(Arrays.asList(
                    new WorldTile(2134, 5261, 0), new WorldTile(2137, 5266, 0), new WorldTile(2136, 5277, 0), new WorldTile(2115, 5277, 0), new WorldTile(2120, 5265, 0)
            )));
    public static Area SHADES_AREA = Area.fromRectangle(new WorldTile(3479, 3293, 0), new WorldTile(3497, 3279, 0));
    public static Area TUROTH_AREA = Area.fromPolygon(
            new ArrayList<>(Arrays.asList(
                    new WorldTile(2713, 10014, 0), new WorldTile(2721, 10017, 0), new WorldTile(2730, 10013, 0), new WorldTile(2734, 10004, 0), new WorldTile(2734, 9991, 0), new WorldTile(2714, 9993, 0)
            )));
    public static Area TROLL_AREA = Area.fromPolygon(
            new ArrayList<>(Arrays.asList(
                    new WorldTile(2770, 10166, 0),
                    new WorldTile(2782, 10166, 0),
                    new WorldTile(2782, 10158, 0),
                    new WorldTile(2772, 10146, 0),
                    new WorldTile(2787, 10151, 0),
                    new WorldTile(2789, 10134, 0),
                    new WorldTile(2801, 10137, 0),
                    new WorldTile(2806, 10127, 0),
                    new WorldTile(2784, 10126, 0),
                    new WorldTile(2780, 10140, 0),
                    new WorldTile(2765, 10138, 0)
            )));

    public static Area WALL_BEAST_AREA = Area.fromRectangle(new WorldTile(3160, 9574, 0), new WorldTile(3165, 9572, 0));

    public static Area WEREWOLF_AREA = Area.fromPolygon(
            new ArrayList<>(Arrays.asList(
                    new WorldTile(3507, 3480, 0), new WorldTile(3507, 3490, 0), new WorldTile(3504, 3491, 0), new WorldTile(3504, 3494, 0), new WorldTile(3503, 3495, 0), new WorldTile(3503, 3499, 0), new WorldTile(3493, 3499, 0), new WorldTile(3482, 3494, 0), new WorldTile(3483, 3480, 0)
            )));
    public static Area WOLF_AREA = Area.fromRectangle(new WorldTile(1875, 5225, 0), new WorldTile(1868, 5242, 0));
    public static Area WYRM_AREA = Area.fromRectangle(new WorldTile(1254, 10203, 0), new WorldTile(1285, 10175, 0));

    public static Area ZOMBIE_AREA = Area.fromRectangle(new WorldTile(3114, 9872, 0), new WorldTile(3132, 9851, 0));
    public static Area TZHARR_AREA = Area.fromPolygon(
            new ArrayList<>(Arrays.asList(
                    new WorldTile(2453, 5174, 0),
                    new WorldTile(2444, 5165, 0),
                    new WorldTile(2453, 5158, 0),
                    new WorldTile(2442, 5148, 0),
                    new WorldTile(2453, 5138, 0),
                    new WorldTile(2470, 5164, 0)
            )));
    public static Area TURAEL_AREA = Area.fromRectangle(new WorldTile(2930, 3537, 0), new WorldTile(2932, 3535, 0));
    public static Area VANNAKA_AREA = Area.fromRectangle(new WorldTile(3147, 9912, 0), new WorldTile(3144, 9915, 0));


    public static Area JAILER_AREA = Area.fromRectangle(new WorldTile(2933, 9690, 0), new WorldTile(2929, 9694, 0));
    public static Area JAIL_CELL = Area.fromRectangle(new WorldTile(2928, 9689, 0), new WorldTile(2934, 9683, 0));
    public static Area WHOLE_SOURHOG_CAVE = Area.fromRectangle(new WorldTile(3150, 9720, 0), new WorldTile(3182, 9670, 0));

    /**
     * HOP TILES
     */
    public static WorldTile BLACK_DEMON_HOP_TILE = new WorldTile(1724, 10094, 0);
    public static WorldTile HELLHOUND_HOP_TILE = new WorldTile(2425, 9809, 0);
    public static WorldTile GARGOYLE_HOP_TILE = new WorldTile(3424, 9936, 3);
    public static WorldTile ANKOU_HOP_TILE = new WorldTile(2471, 9816, 0);

    public static WorldTile WYRM_HOP_TILE = new WorldTile(1288, 10203, 0);
    /**
     * CANNON TILES
     */
    public static WorldTile BLOODVELD_CANNON_TILE = new WorldTile(2470, 9831, 0);
    public static WorldTile KALPHITE_CANNON_TILE = new WorldTile(3323, 9502, 0);
    public static WorldTile HELLHOUND_CANNON_TILE = new WorldTile(2414, 9799, 0);
    public static WorldTile FIRE_GIANT_CANNON_TILE = new WorldTile(2396, 9770, 0);
    public static WorldTile ABERRANT_SPECTRE_CANNON_TILE = new WorldTile(2470, 9779, 0);
    public static WorldTile ANKOU_CANNON_TILE = new WorldTile(2479, 9800, 0);
    public static WorldTile ELF_CANNON_TILE = new WorldTile(2201, 3259, 0);
    public static WorldTile BLUE_DRAGON_CANNON_TILE = new WorldTile(2918, 9802, 0);
    public static WorldTile DAGGANOTH_CANNON_TILE = new WorldTile(2523, 10025, 0);
    public static WorldTile SUQAH_CANNON_TILE = new WorldTile(2113, 3945, 0);
}
