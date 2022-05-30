package scripts.Data;

import org.tribot.script.sdk.types.Area;
import org.tribot.script.sdk.types.WorldTile;
import scripts.Requirements.AreaRequirement;

public class Areas {

   // public static  Area UNDEAD_DRUID_AREA = Area.fromPolygon(new WorldTile(1805, 9932, 0), new WorldTile(1795, 9953, 0));
    public static   Area LARGE_SCARAB_FIGHT_AREA = Area.fromPolygon(
            new WorldTile[]{
                    new WorldTile(3295, 9262, 2),
                    new WorldTile(3305, 9262, 2),
                    new WorldTile(3306, 9257, 2),
                    new WorldTile(3308, 9252, 2),
                    new WorldTile(3308, 9250, 2),
                    new WorldTile(3293, 9252, 2),
                    new WorldTile(3295, 9256, 2)
            }
    );
    public static  Area UNDEAD_DRUID_AREA  = Area.fromPolygon(

                    new WorldTile(1804, 9954, 0),
                    new WorldTile(1795, 9954, 0),
                    new WorldTile(1795, 9948, 0),
                    new WorldTile(1799, 9948, 0),
                    new WorldTile(1799, 9944, 0),
                    new WorldTile(1796, 9944, 0),
                    new WorldTile(1796, 9934, 0),
                    new WorldTile(1806, 9934, 0),
                    new WorldTile(1806, 9944, 0),
                    new WorldTile(1804, 9944, 0),
                    new WorldTile(1804, 9948, 0)

    );

   public static Area scarabFightAreaSdk = Area.fromPolygon(
           new WorldTile[] {
                   new WorldTile(3302, 9250, 2),
                   new WorldTile(3294, 9250, 2),
                   new WorldTile(3294, 9252, 2),
                   new WorldTile(3296, 9253, 2),
                   new WorldTile(3296, 9257, 2),
                   new WorldTile(3298, 9257, 2),
                   new WorldTile(3299, 9262, 2),
                   new WorldTile(3301, 9262, 2),
                   new WorldTile(3302, 9257, 2),
                   new WorldTile(3306, 9257, 2),
                   new WorldTile(3306, 9251, 2)
           }
   );
 public static Area UNDEAD_DRUID_AREA_SDK  = Area.fromPolygon(
         new WorldTile[] {
                 new WorldTile(1804, 9954, 0),
                 new WorldTile(1795, 9954, 0),
                 new WorldTile(1795, 9948, 0),
                 new WorldTile(1799, 9948, 0),
                 new WorldTile(1799, 9944, 0),
                 new WorldTile(1796, 9944, 0),
                 new WorldTile(1796, 9934, 0),
                 new WorldTile(1806, 9934, 0),
                 new WorldTile(1806, 9944, 0),
                 new WorldTile(1804, 9944, 0),
                 new WorldTile(1804, 9948, 0)
         }
 );
    /**
     * just before shed to zanaris
     */

    public static Area SWAMP_AREA = Area.fromPolygon(new WorldTile(3201, 3170, 0), new WorldTile(3199, 3167, 0));
    public static Area SOURHOG_ENTRANCE_AREA = Area.fromPolygon(new WorldTile(3147, 3348, 0), new WorldTile(3151, 3344, 0));
    public static Area TREE_STRONGHOLD_FAILSAFE = Area.fromRadius(new WorldTile(2461, 3444, 0), 5);
    public static Area NIEVE_AREA = Area.fromRadius(new WorldTile(2433, 3423, 0), 4);
    public static WorldTile BRONZE_DRAGON_SAFE_TILE = new WorldTile(1655, 10103, 0);
    public static Area BRONZE_DRAGON_SAFE_AREA = Area.fromPolygon(new WorldTile(1655, 10103, 0), new WorldTile(1655, 10103, 0));
    public static Area ABYSSAL_DEMON_AREA = Area.fromPolygon(
            new WorldTile[]{
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
            }
    );
    public static Area lavaTeleArea = Area.fromPolygon(
            new WorldTile[]{
                    new WorldTile(3175, 3749, 0),
                    new WorldTile(3173, 3749, 0),
                    new WorldTile(3171, 3746, 0),
                    new WorldTile(3165, 3746, 0),
                    new WorldTile(3161, 3750, 0),
                    new WorldTile(3175, 3750, 0)
            }
    );
    public static Area WHOLE_WILDERNESS = Area.fromPolygon(new WorldTile(3365, 3524, 0), new WorldTile(2963, 3967, 0));

    public static Area ABBERANT_SPECTRE_AREA_1 = Area.fromPolygon(new WorldTile(2467, 9786, 0), new WorldTile(2474, 9773, 0));
    public static Area WHOLE_ZANARIS = Area.fromPolygon(new WorldTile(2508, 4404, 0), new WorldTile(2369, 4481, 0));
    public static Area CHAELDAR_AREA = Area.fromPolygon(new WorldTile(2442, 4432, 0), new WorldTile(2451, 4425, 0));
    public static Area ABOVE_LUMBRIDGE_SWAMP_ENTRANCE = Area.fromPolygon(new WorldTile(3172, 3170, 0), new WorldTile(3167, 3174, 0));
    public static Area ABBERANT_SPECTRES_AREA = Area.fromPolygon(new WorldTile(2467, 9786, 0), new WorldTile(2474, 9775, 0));
    public static Area ANKOU_AREA = Area.fromPolygon(new WorldTile(2486, 9795, 0), new WorldTile(2466, 9810, 0));
    public static Area BASILISK_AREA = Area.fromPolygon(new WorldTile(2752, 9998, 0), new WorldTile(2734, 10018, 0));
    public static Area BANSHEE_AREA = Area.fromPolygon(new WorldTile[]{new WorldTile(3453, 3535, 0), new WorldTile(3449, 3531, 0), new WorldTile(3445, 3531, 0), new WorldTile(3442, 3534, 0), new WorldTile(3439, 3534, 0), new WorldTile(3438, 3533, 0), new WorldTile(3434, 3533, 0), new WorldTile(3432, 3535, 0), new WorldTile(3434, 3541, 0), new WorldTile(3437, 3541, 0), new WorldTile(3437, 3547, 0), new WorldTile(3431, 3547, 0), new WorldTile(3431, 3557, 0), new WorldTile(3433, 3557, 0), new WorldTile(3433, 3562, 0), new WorldTile(3450, 3562, 0), new WorldTile(3450, 3559, 0), new WorldTile(3449, 3558, 0), new WorldTile(3449, 3553, 0), new WorldTile(3450, 3552, 0), new WorldTile(3450, 3548, 0), new WorldTile(3449, 3547, 0), new WorldTile(3449, 3543, 0), new WorldTile(3453, 3539, 0)});
    public static Area BAT_AREA = Area.fromPolygon(new WorldTile[]{new WorldTile(2899, 9845, 0), new WorldTile(2899, 9823, 0), new WorldTile(2927, 9823, 0), new WorldTile(2933, 9832, 0), new WorldTile(2931, 9836, 0), new WorldTile(2920, 9845, 0)});
    public static Area BEAR_AREA = Area.fromPolygon(new WorldTile(2714, 3329, 0), new WorldTile(2680, 3346, 0));
    // public static Area BLUE_DRAGON_AREA = Area.fromPolygon(new WorldTile(2922, 9796, 0), new WorldTile(2913, 9807, 0));
    public static Area BLUE_DRAGON_AREA = Area.fromPolygon(
            new WorldTile[]{
                    new WorldTile(2907, 9814, 0),
                    new WorldTile(2907, 9794, 0),
                    new WorldTile(2927, 9784, 0),
                    new WorldTile(2926, 9795, 0),
                    new WorldTile(2922, 9799, 0),
                    new WorldTile(2922, 9809, 0)
            }
    );
    //  public static Area BLACK_DEMON_AREA = Area.fromPolygon(new WorldTile(1716, 10091, 0), new WorldTile(1725, 10079, 0));
    public static Area BLACK_DEMON_AREA = Area.fromPolygon(new WorldTile(1419, 10100, 1), new WorldTile(1459, 10059, 1));
    public static Area BLOODVELD_AREA = Area.fromPolygon(new WorldTile(2479, 9828, 0), new WorldTile(2461, 9837, 0));
    public static Area BIRD_AREA = Area.fromPolygon(new WorldTile(3226, 3300, 0), new WorldTile(3235, 3295, 0));
    // blue dragon area is the area with baby blue drags.

    public static Area BEFORE_BABY_BLACK_DRAG_AREA = Area.fromPolygon(new WorldTile(2879, 9828, 0), new WorldTile(2885, 9821, 0));
    public static Area BABY_BLACK_DRAGON_AREA = Area.fromPolygon(new WorldTile(2852, 9830, 1), new WorldTile(2865, 9823, 1));
    public static Area CAVE_BUG_AREA = Area.fromPolygon(new WorldTile(3144, 9580, 0), new WorldTile(3163, 9557, 0));
    public static Area CAVE_CRAWLER_AREA = Area.fromPolygon(new WorldTile[]{new WorldTile(2780, 10006, 0), new WorldTile(2772, 9999, 0), new WorldTile(2786, 9986, 0), new WorldTile(2805, 9994, 0), new WorldTile(2801, 10007, 0)});
    public static Area CAVE_SLIME_AREA = Area.fromPolygon(new WorldTile[]{new WorldTile(3143, 9582, 0), new WorldTile(3161, 9582, 0), new WorldTile(3160, 9564, 0), new WorldTile(3163, 9564, 0), new WorldTile(3163, 9551, 0), new WorldTile(3154, 9545, 0), new WorldTile(3142, 9547, 0)});
    public static Area CRAWLING_HANDS_AREA = Area.fromPolygon(new WorldTile[]{new WorldTile(3431, 3536, 0), new WorldTile(3408, 3536, 0), new WorldTile(3408, 3542, 0), new WorldTile(3409, 3543, 0), new WorldTile(3409, 3553, 0), new WorldTile(3431, 3553, 0)});
    public static Area COW_AREA = Area.fromPolygon(new WorldTile[]{new WorldTile(3243, 3298, 0), new WorldTile(3243, 3282, 0), new WorldTile(3249, 3278, 0), new WorldTile(3251, 3276, 0), new WorldTile(3253, 3272, 0), new WorldTile(3253, 3255, 0), new WorldTile(3264, 3255, 0), new WorldTile(3264, 3298, 0)});
    public static Area COCKATRICE_AREA = Area.fromPolygon(new WorldTile(2782, 10045, 0), new WorldTile(2807, 10027, 0));
    public static Area CROCODILE_AREA = Area.fromPolygon(new WorldTile[]{new WorldTile(3337, 2925, 0), new WorldTile(3341, 2928, 0), new WorldTile(3355, 2923, 0), new WorldTile(3361, 2929, 0), new WorldTile(3364, 2915, 0), new WorldTile(3335, 2914, 0)});
    public static WorldTile DAGGANOTH_HOP_TILE = new WorldTile(2515, 10004, 0);
    public static Area DOG_AREA = Area.fromPolygon(
            new WorldTile[]{new WorldTile(2642, 3308, 0), new WorldTile(2643, 3309, 0), new WorldTile(2643, 3315, 0),
                    new WorldTile(2647, 3319, 0),
                    new WorldTile(2647, 3329, 0), new WorldTile(2646, 3329, 0), new WorldTile(2643, 3332, 0), new WorldTile(2643, 3326, 0), new WorldTile(2644, 3326, 0), new WorldTile(2644, 3320, 0),
                    new WorldTile(2627, 3320, 0), new WorldTile(2627, 3326, 0), new WorldTile(2624, 3326, 0), new WorldTile(2624, 3315, 0), new WorldTile(2625, 3315, 0), new WorldTile(2626, 3314, 0), new WorldTile(2626, 3309, 0), new WorldTile(2627, 3308, 0)});
    public static Area BLACK_DRAGON_AREA = Area.fromPolygon(new WorldTile(2828, 9829, 0), new WorldTile(2843, 9821, 0));
    public static Area INFERNAL_MAGE_AREA = Area.fromPolygon(new WorldTile(3430, 3554, 1), new WorldTile(3452, 3579, 1));
    public static Area ICE_GIANT_AREA = Area.fromPolygon(new WorldTile[]{new WorldTile(3041, 9588, 0), new WorldTile(3041, 9575, 0), new WorldTile(3054, 9565, 0), new WorldTile(3064, 9569, 0), new WorldTile(3067, 9580, 0), new WorldTile(3064, 9588, 0)});
    public static Area DAGGANOTH_AREA = Area.fromPolygon(new WorldTile(2510, 10036, 0), new WorldTile(2541, 10011, 0));
    public static Area DWARF_AREA = Area.fromPolygon(new WorldTile[]{new WorldTile(3020, 3446, 0), new WorldTile(3011, 3446, 0), new WorldTile(3011, 3452, 0), new WorldTile(3015, 3452, 0), new WorldTile(3015, 3455, 0), new WorldTile(3008, 3455, 0), new WorldTile(3008, 3460, 0), new WorldTile(3015, 3463, 0), new WorldTile(3024, 3463, 0), new WorldTile(3024, 3449, 0), new WorldTile(3021, 3449, 0)});
    public static Area ELF_AREA = Area.fromPolygon(new WorldTile(2187, 3262, 0), new WorldTile(2212, 3243, 0));
    public static Area PRE_EARTH_WARRIOR_AREA = Area.fromPolygon(new WorldTile(3119, 9964, 0), new WorldTile(3122, 9961, 0));
    public static Area EARTH_WARRIOR_AREA = Area.fromPolygon(new WorldTile(3128, 9969, 0), new WorldTile(3113, 9981, 0));
    public static Area FIRE_GIANT_AREA = Area.fromPolygon(new WorldTile(2391, 9774, 0), new WorldTile(2408, 9767, 0));
    public static Area GREATER_DEMON_AREA = Area.fromPolygon(new WorldTile(1679, 10091, 0), new WorldTile(1691, 10081, 0));
    public static Area GARGOYLE_AREA = Area.fromPolygon(
            new WorldTile[]{
                    new WorldTile(3429, 9952, 3),
                    new WorldTile(3442, 9952, 3),
                    new WorldTile(3442, 9927, 3),
                    new WorldTile(3437, 9931, 3),
                    new WorldTile(3429, 9931, 3)
            }
    );
    public static Area GREEN_DRAGON_AREA = Area.fromPolygon(new WorldTile(3342, 3663, 0), new WorldTile(3318, 3697, 0));
    public static Area GHOST_AREA = Area.fromPolygon(new WorldTile(2924, 9821, 0), new WorldTile(2895, 9817, 0));
    public static Area GHOUL_AREA = Area.fromPolygon(new WorldTile[]{new WorldTile(3408, 3513, 0), new WorldTile(3409, 3506, 0), new WorldTile(3411, 3504, 0), new WorldTile(3411, 3501, 0), new WorldTile(3416, 3498, 0), new WorldTile(3416, 3495, 0), new WorldTile(3420, 3495, 0), new WorldTile(3420, 3492, 0), new WorldTile(3432, 3492, 0), new WorldTile(3432, 3516, 0), new WorldTile(3408, 3517, 0)});
    public static Area GOBLIN_AREA = Area.fromPolygon(new WorldTile[]{new WorldTile(3239, 3243, 0), new WorldTile(3258, 3244, 0), new WorldTile(3259, 3227, 0), new WorldTile(3245, 3227, 0)});
    public static Area HARPIE_BUG_SWARM_AREA = Area.fromPolygon(new WorldTile(2860, 3115, 0), new WorldTile(2868, 3107, 0));
    public static Area HELLHOUND_AREA = Area.fromPolygon(new WorldTile(2404, 9807, 0), new WorldTile(2421, 9792, 0));
    public static Area HOBGOBLIN_AREA = Area.fromPolygon(
            new WorldTile[]{
                    new WorldTile(3113, 9875, 0),
                    new WorldTile(3120, 9881, 0),
                    new WorldTile(3127, 9881, 0),
                    new WorldTile(3134, 9876, 0),
                    new WorldTile(3134, 9868, 0),
                    new WorldTile(3114, 9868, 0)
            }
    );
    public static Area BRINE_RAT_AREA = Area.fromPolygon(new WorldTile(2700, 10138, 0), new WorldTile(2713, 10128, 0));
    public static Area WHOLE_BRINE_RAT_CAVE = Area.fromPolygon(new WorldTile(2685, 10153, 0), new WorldTile(2742, 10116, 0));
    public static Area HILL_GIANT_AREA = Area.fromPolygon(new WorldTile[]{new WorldTile(3112, 9854, 0), new WorldTile(3128, 9854, 0), new WorldTile(3127, 9819, 0), new WorldTile(3092, 9824, 0), new WorldTile(3094, 9839, 0)});
    public static Area ICEFIEND_AREA = Area.fromPolygon(new WorldTile[]{new WorldTile(3025, 3466, 0), new WorldTile(2995, 3467, 0), new WorldTile(3002, 3481, 0), new WorldTile(3006, 3489, 0), new WorldTile(3012, 3489, 0), new WorldTile(3014, 3479, 0), new WorldTile(3022, 3476, 0)});
    public static Area JELLY_AREA = Area.fromPolygon(new WorldTile(2716, 10037, 0), new WorldTile(2694, 10019, 0));
    public static Area KALPHITE_AREA = Area.fromPolygon(new WorldTile[]{new WorldTile(3334, 9499, 0), new WorldTile(3326, 9490, 0), new WorldTile(3315, 9493, 0), new WorldTile(3316, 9511, 0), new WorldTile(3325, 9516, 0), new WorldTile(3334, 9509, 0)});
    // public static Area KURASK_AREA = Area.fromPolygon(new WorldTile(2687, 10009, 0), new WorldTile(2708, 9989, 0));
    public static Area KURASK_AREA = Area.fromPolygon(new WorldTile(2691, 9982, 0), new WorldTile(2723, 9952, 0));
    public static Area LIZARD_AREA = Area.fromPolygon(new WorldTile(3379, 3083, 0), new WorldTile(3414, 3043, 0));
    public static Area LESSER_DEMON_AREA = Area.fromPolygon(new WorldTile[]{new WorldTile(2924, 9813, 0), new WorldTile(2924, 9796, 0), new WorldTile(2929, 9796, 0), new WorldTile(2930, 9785, 0), new WorldTile(2943, 9785, 0), new WorldTile(2941, 9798, 0), new WorldTile(2936, 9810, 0), new WorldTile(2934, 9813, 0)});
    public static Area MUTATED_ZYGOMITE_AREA = Area.fromPolygon(new WorldTile(2426, 4462, 0), new WorldTile(2409, 4478, 0));
    public static Area MONKEY_AREA = Area.fromPolygon(new WorldTile(2892, 3148, 0), new WorldTile(2865, 3163, 0));
    public static Area MOSS_GIANT_AREA = Area.fromPolygon(new WorldTile(2548, 3411, 0), new WorldTile(2562, 3393, 0));
    public static Area MINOTAUR_AREA = Area.fromPolygon(
            new WorldTile[]{
                    new WorldTile(1869, 5228, 0),
                    new WorldTile(1869, 5224, 0),
                    new WorldTile(1875, 5224, 0),
                    new WorldTile(1876, 5245, 0),
                    new WorldTile(1868, 5245, 0),
                    new WorldTile(1866, 5237, 0)
            }
    );

    public static Area PYREFIEND_AREA = Area.fromPolygon(new WorldTile[]{new WorldTile(2752, 10015, 0), new WorldTile(2769, 10015, 0), new WorldTile(2767, 10008, 0), new WorldTile(2770, 10003, 0), new WorldTile(2770, 9992, 0), new WorldTile(2752, 9993, 0)});

    public static Area RAT_AREA = Area.fromPolygon(new WorldTile(3227, 3170, 0), new WorldTile(3208, 3191, 0));
    public static Area ROCKSLUG_AREA = Area.fromPolygon(new WorldTile(2811, 10024, 0), new WorldTile(2789, 10008, 0));

    public static Area SOURHOG_AREA = Area.fromPolygon(
            new WorldTile[]{
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
            }
    );
    public static Area SUQAH_AREA = Area.fromPolygon(new WorldTile(2110, 3947, 0), new WorldTile(2121, 3939, 0));
    public static Area SCORPION_AREA = Area.fromPolygon(new WorldTile[]{new WorldTile(3306, 3278, 0), new WorldTile(3306, 3283, 0), new WorldTile(3302, 3287, 0), new WorldTile(3302, 3288, 0), new WorldTile(3306, 3292, 0), new WorldTile(3306, 3294, 0), new WorldTile(3303, 3298, 0), new WorldTile(3292, 3297, 0), new WorldTile(3293, 3285, 0), new WorldTile(3290, 3283, 0), new WorldTile(3292, 3281, 0), new WorldTile(3293, 3279, 0)});
    public static Area SKELETON_AREA = Area.fromPolygon(new WorldTile[]{new WorldTile(3134, 9881, 0), new WorldTile(3144, 9881, 0), new WorldTile(3144, 9875, 0), new WorldTile(3146, 9873, 0), new WorldTile(3152, 9873, 0), new WorldTile(3152, 9870, 0), new WorldTile(3150, 9868, 0), new WorldTile(3136, 9868, 0), new WorldTile(3134, 9870, 0)});
    public static Area SPIDER_AREA = Area.fromPolygon(new WorldTile[]{new WorldTile(2134, 5261, 0), new WorldTile(2137, 5266, 0), new WorldTile(2136, 5277, 0), new WorldTile(2115, 5277, 0), new WorldTile(2120, 5265, 0)});
    public static Area SHADES_AREA = Area.fromPolygon(new WorldTile(3479, 3293, 0), new WorldTile(3497, 3279, 0));
    public static Area TUROTH_AREA = Area.fromPolygon(new WorldTile[]{new WorldTile(2713, 10014, 0), new WorldTile(2721, 10017, 0), new WorldTile(2730, 10013, 0), new WorldTile(2734, 10004, 0), new WorldTile(2734, 9991, 0), new WorldTile(2714, 9993, 0)});
    public static Area TROLL_AREA = Area.fromPolygon(
            new WorldTile[]{
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
            }
    );

    public static Area WALL_BEAST_AREA = Area.fromPolygon(new WorldTile(3160, 9574, 0), new WorldTile(3165, 9572, 0));

    public static Area WEREWOLF_AREA = Area.fromPolygon(new WorldTile[]{new WorldTile(3507, 3480, 0), new WorldTile(3507, 3490, 0), new WorldTile(3504, 3491, 0), new WorldTile(3504, 3494, 0), new WorldTile(3503, 3495, 0), new WorldTile(3503, 3499, 0), new WorldTile(3493, 3499, 0), new WorldTile(3482, 3494, 0), new WorldTile(3483, 3480, 0)});
    public static Area WOLF_AREA = Area.fromPolygon(new WorldTile(1875, 5225, 0), new WorldTile(1868, 5242, 0));
    public static Area WYRM_AREA = Area.fromPolygon(new WorldTile(1254, 10203, 0), new WorldTile(1285, 10175, 0));

    public static Area ZOMBIE_AREA = Area.fromPolygon(new WorldTile(3114, 9872, 0), new WorldTile(3132, 9851, 0));
    public static Area TZHARR_AREA = Area.fromPolygon(
            new WorldTile[]{
                    new WorldTile(2453, 5174, 0),
                    new WorldTile(2444, 5165, 0),
                    new WorldTile(2453, 5158, 0),
                    new WorldTile(2442, 5148, 0),
                    new WorldTile(2453, 5138, 0),
                    new WorldTile(2470, 5164, 0)
            }
    );
    public static Area TURAEL_AREA = Area.fromPolygon(new WorldTile(2930, 3537, 0), new WorldTile(2932, 3535, 0));
    public static Area VANNAKA_AREA = Area.fromPolygon(new WorldTile(3147, 9912, 0), new WorldTile(3144, 9915, 0));


    public static Area JAILER_AREA = Area.fromPolygon(new WorldTile(2933, 9690, 0), new WorldTile(2929, 9694, 0));
    public static Area JAIL_CELL = Area.fromPolygon(new WorldTile(2928, 9689, 0), new WorldTile(2934, 9683, 0));
    public static Area WHOLE_SOURHOG_CAVE = Area.fromPolygon(new WorldTile(3150, 9720, 0), new WorldTile(3182, 9670, 0));

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


    /**
     * DAGGANOTH KINGS
     */
    public static Area WB_DUNGEON_1_PT_2 = Area.fromPolygon(
            new WorldTile[]{
                    new WorldTile(2492, 10165, 0),
                    new WorldTile(2496, 10168, 0),
                    new WorldTile(2503, 10170, 0),
                    new WorldTile(2551, 10170, 0),
                    new WorldTile(2551, 10165, 0),
                    new WorldTile(2548, 10160, 0),
                    new WorldTile(2552, 10154, 0),
                    new WorldTile(2552, 10150, 0),
                    new WorldTile(2550, 10141, 0),
                    new WorldTile(2542, 10142, 0),
                    new WorldTile(2542, 10156, 0),
                    new WorldTile(2532, 10157, 0),
                    new WorldTile(2530, 10160, 0),
                    new WorldTile(2524, 10156, 0),
                    new WorldTile(2513, 10158, 0),
                    new WorldTile(2502, 10155, 0),
                    new WorldTile(2492, 10162, 0)
            }
    );
    public static AreaRequirement wbDungeon1Pt2 = new AreaRequirement(WB_DUNGEON_1_PT_2);

    public static Area WB_DUNGEON_1_PT_2_LADDER_AREA = Area.fromPolygon(
            new WorldTile[]{
                    new WorldTile(2545, 10147, 0),
                    new WorldTile(2546, 10147, 0),
                    new WorldTile(2546, 10146, 0),
                    new WorldTile(2548, 10144, 0),
                    new WorldTile(2548, 10142, 0),
                    new WorldTile(2546, 10140, 0),
                    new WorldTile(2545, 10140, 0),
                    new WorldTile(2542, 10143, 0),
                    new WorldTile(2542, 10144, 0)
            }
    );
    public static AreaRequirement wbDungeon1LadderArea = new AreaRequirement(WB_DUNGEON_1_PT_2_LADDER_AREA);

    public static Area WB_DUNGEON_2 = Area.fromPolygon(
            new WorldTile[]{
                    new WorldTile(1884, 4413, 0),
                    new WorldTile(1906, 4413, 0),
                    new WorldTile(1911, 4409, 0),
                    new WorldTile(1911, 4400, 0),
                    new WorldTile(1901, 4397, 0),
                    new WorldTile(1895, 4394, 0),
                    new WorldTile(1890, 4382, 0),
                    new WorldTile(1905, 4378, 0),
                    new WorldTile(1914, 4373, 0),
                    new WorldTile(1916, 4369, 0),
                    new WorldTile(1929, 4369, 0),
                    new WorldTile(1940, 4362, 0),
                    new WorldTile(1952, 4366, 0),
                    new WorldTile(1952, 4372, 0),
                    new WorldTile(1965, 4372, 0),
                    new WorldTile(1965, 4361, 0),
                    new WorldTile(1947, 4353, 0),
                    new WorldTile(1931, 4357, 0),
                    new WorldTile(1921, 4361, 0),
                    new WorldTile(1915, 4361, 0),
                    new WorldTile(1901, 4357, 0),
                    new WorldTile(1884, 4356, 0),
                    new WorldTile(1878, 4360, 0),
                    new WorldTile(1877, 4379, 0),
                    new WorldTile(1881, 4391, 0),
                    new WorldTile(1888, 4400, 0),
                    new WorldTile(1883, 4405, 0)
            }
    );
    public static AreaRequirement wbDungeon2 = new AreaRequirement(WB_DUNGEON_2);

    public static Area WB_DUNGEON_3_PT_1 = Area.fromPolygon(
            new WorldTile[]{
                    new WorldTile(1795, 4398, 1),
                    new WorldTile(1796, 4388, 1),
                    new WorldTile(1809, 4388, 1),
                    new WorldTile(1815, 4393, 1),
                    new WorldTile(1815, 4395, 1),
                    new WorldTile(1811, 4397, 1)
            }
    );
    public static AreaRequirement wbDungeon3Pt1 = new AreaRequirement(WB_DUNGEON_3_PT_1);

    public static Area WB_DUNGEON_3_PT_2 = Area.fromPolygon(
            new WorldTile[]{
                    new WorldTile(1801, 4385, 1),
                    new WorldTile(1794, 4385, 1),
                    new WorldTile(1794, 4371, 1),
                    new WorldTile(1803, 4365, 1),
                    new WorldTile(1808, 4369, 1),
                    new WorldTile(1807, 4374, 1),
                    new WorldTile(1802, 4379, 1)
            }
    );
    public static AreaRequirement wbDungeon3Pt2 = new AreaRequirement(WB_DUNGEON_3_PT_2);

    public static Area WB_DUNGEON_3_PT_3 = Area.fromPolygon(
            new WorldTile[]{
                    new WorldTile(1827, 4364, 1),
                    new WorldTile(1827, 4359, 1),
                    new WorldTile(1834, 4359, 1),
                    new WorldTile(1836, 4357, 1),
                    new WorldTile(1867, 4357, 1),
                    new WorldTile(1867, 4363, 1),
                    new WorldTile(1869, 4365, 1),
                    new WorldTile(1869, 4372, 1),
                    new WorldTile(1860, 4372, 1),
                    new WorldTile(1860, 4365, 1),
                    new WorldTile(1858, 4363, 1),
                    new WorldTile(1832, 4363, 1),
                    new WorldTile(1830, 4364, 1)
            }
    );
    public static AreaRequirement wbDungeon3Pt3 = new AreaRequirement(WB_DUNGEON_3_PT_3);

    public static Area WB_DUNGEON_3_PT_4 = Area.fromPolygon(
            new WorldTile[]{
                    new WorldTile(1862, 4388, 1),
                    new WorldTile(1867, 4388, 1),
                    new WorldTile(1867, 4400, 1),
                    new WorldTile(1868, 4407, 1),
                    new WorldTile(1888, 4407, 1),
                    new WorldTile(1889, 4405, 1),
                    new WorldTile(1894, 4405, 1),
                    new WorldTile(1894, 4411, 1),
                    new WorldTile(1891, 4413, 1),
                    new WorldTile(1862, 4413, 1),
                    new WorldTile(1860, 4404, 1),
                    new WorldTile(1860, 4398, 1)
            }
    );
    public static AreaRequirement wbDungeon3Pt4 = new AreaRequirement(WB_DUNGEON_3_PT_4);

    public static Area WB_DUNGEON_4_PT_1 = Area.fromPolygon(
            new WorldTile[]{
                    new WorldTile(1796, 4389, 2),
                    new WorldTile(1805, 4387, 2),
                    new WorldTile(1805, 4382, 2),
                    new WorldTile(1800, 4378, 2),
                    new WorldTile(1794, 4380, 2),
                    new WorldTile(1794, 4385, 2)
            }
    );
    public static AreaRequirement wbDungeon4Pt1 = new AreaRequirement(WB_DUNGEON_4_PT_1);

    public static Area WB_DUNGEON_4_PT_2 = Area.fromPolygon(
            new WorldTile[]{
                    new WorldTile(1797, 4374, 2),
                    new WorldTile(1797, 4363, 2),
                    new WorldTile(1821, 4356, 2),
                    new WorldTile(1829, 4362, 2),
                    new WorldTile(1818, 4374, 2)
            }
    );
    public static AreaRequirement wbDungeon4Pt2 = new AreaRequirement(WB_DUNGEON_4_PT_2);

    public static Area WB_DUNGEON_4_PT_3 = Area.fromPolygon(
            new WorldTile[]{
                    new WorldTile(1807, 4397, 2),
                    new WorldTile(1807, 4388, 2),
                    new WorldTile(1818, 4388, 2),
                    new WorldTile(1823, 4379, 2),
                    new WorldTile(1830, 4384, 2),
                    new WorldTile(1837, 4385, 2),
                    new WorldTile(1837, 4390, 2),
                    new WorldTile(1825, 4397, 2)
            }
    );
    public static AreaRequirement wbDungeon4Pt3 = new AreaRequirement(WB_DUNGEON_4_PT_3);

    public static Area WB_DUNGEON_4_PT_4 = Area.fromPolygon(
            new WorldTile[] {
                    new WorldTile(1858, 4390, 2),
                    new WorldTile(1861, 4372, 2),
                    new WorldTile(1868, 4372, 2),
                    new WorldTile(1871, 4390, 2)
            }
    );
    public static AreaRequirement wbDungeon4Pt4 = new AreaRequirement(WB_DUNGEON_4_PT_3);


    public static Area WB_DUNGEON_5_PT_1 = Area.fromPolygon(
            new WorldTile[]{
                    new WorldTile(1794, 4412, 3),
                    new WorldTile(1808, 4412, 3),
                    new WorldTile(1808, 4401, 3),
                    new WorldTile(1801, 4399, 3),
                    new WorldTile(1794, 4402, 3)
            }
    );
    public static AreaRequirement wbDungeon5Pt1 = new AreaRequirement(WB_DUNGEON_5_PT_1);

    public static Area WB_DUNGEON_5_PT_2 = Area.fromPolygon(
            new WorldTile[]{
                    new WorldTile(1824, 4411, 3),
                    new WorldTile(1824, 4400, 3),
                    new WorldTile(1834, 4401, 3),
                    new WorldTile(1830, 4395, 3),
                    new WorldTile(1830, 4392, 3),
                    new WorldTile(1834, 4388, 3),
                    new WorldTile(1852, 4401, 3),
                    new WorldTile(1854, 4408, 3),
                    new WorldTile(1849, 4412, 3),
                    new WorldTile(1828, 4411, 3)
            }
    );
    public static AreaRequirement wbDungeon5Pt2 = new AreaRequirement(WB_DUNGEON_5_PT_2);

   public static Area WB_DUNGEON_4_PT_5  = Area.fromPolygon(
            new WorldTile[] {
                    new WorldTile(1808, 4411, 2),
                    new WorldTile(1809, 4401, 2),
                    new WorldTile(1824, 4401, 2),
                    new WorldTile(1824, 4406, 2),
                    new WorldTile(1818, 4410, 2)
            }
    );
    public static AreaRequirement wbDungeon4Pt5 = new AreaRequirement(WB_DUNGEON_4_PT_5);
    public static   Area DK_AREA = Area.fromPolygon(new WorldTile(2938, 4426, 0), new WorldTile(2892, 4471, 0));
   // public static AreaRequirement dkArea = new AreaRequirement(DK_AREA);
}
