package scripts.Data;

import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.types.Area;
import org.tribot.script.sdk.types.WorldTile;
import scripts.Requirements.AreaRequirement;

public class Areas {

   // public static  RSArea UNDEAD_DRUID_AREA = new RSArea(new RSTile(1805, 9932, 0), new RSTile(1795, 9953, 0));
    public static   RSArea LARGE_SCARAB_FIGHT_AREA = new RSArea(
            new RSTile[]{
                    new RSTile(3295, 9262, 2),
                    new RSTile(3305, 9262, 2),
                    new RSTile(3306, 9257, 2),
                    new RSTile(3308, 9252, 2),
                    new RSTile(3308, 9250, 2),
                    new RSTile(3293, 9252, 2),
                    new RSTile(3295, 9256, 2)
            }
    );
    public static  RSArea UNDEAD_DRUID_AREA  = new RSArea(
            new RSTile[] {
                    new RSTile(1804, 9954, 0),
                    new RSTile(1795, 9954, 0),
                    new RSTile(1795, 9948, 0),
                    new RSTile(1799, 9948, 0),
                    new RSTile(1799, 9944, 0),
                    new RSTile(1796, 9944, 0),
                    new RSTile(1796, 9934, 0),
                    new RSTile(1806, 9934, 0),
                    new RSTile(1806, 9944, 0),
                    new RSTile(1804, 9944, 0),
                    new RSTile(1804, 9948, 0)
            }
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

    public static RSArea SWAMP_AREA = new RSArea(new RSTile(3201, 3170, 0), new RSTile(3199, 3167, 0));
    public static RSArea SOURHOG_ENTRANCE_AREA = new RSArea(new RSTile(3147, 3348, 0), new RSTile(3151, 3344, 0));
    public static RSArea TREE_STRONGHOLD_FAILSAFE = new RSArea(new RSTile(2461, 3444, 0), 5);
    public static RSArea NIEVE_AREA = new RSArea(new RSTile(2433, 3423, 0), 4);
    public static RSTile BRONZE_DRAGON_SAFE_TILE = new RSTile(1655, 10103, 0);
    public static RSArea BRONZE_DRAGON_SAFE_AREA = new RSArea(new RSTile(1655, 10103, 0), new RSTile(1655, 10103, 0));
    public static RSArea ABYSSAL_DEMON_AREA = new RSArea(
            new RSTile[]{
                    new RSTile(1666, 10054, 0),
                    new RSTile(1669, 10050, 0),
                    new RSTile(1674, 10056, 0),
                    new RSTile(1675, 10056, 0),
                    new RSTile(1676, 10055, 0),
                    new RSTile(1678, 10055, 0),
                    new RSTile(1679, 10054, 0),
                    new RSTile(1679, 10053, 0),
                    new RSTile(1681, 10055, 0),
                    new RSTile(1681, 10064, 0),
                    new RSTile(1679, 10062, 0),
                    new RSTile(1675, 10063, 0),
                    new RSTile(1672, 10063, 0),
                    new RSTile(1672, 10061, 0),
                    new RSTile(1673, 10060, 0),
                    new RSTile(1673, 10059, 0)
            }
    );
    public static RSArea lavaTeleArea = new RSArea(
            new RSTile[]{
                    new RSTile(3175, 3749, 0),
                    new RSTile(3173, 3749, 0),
                    new RSTile(3171, 3746, 0),
                    new RSTile(3165, 3746, 0),
                    new RSTile(3161, 3750, 0),
                    new RSTile(3175, 3750, 0)
            }
    );
    public static RSArea WHOLE_WILDERNESS = new RSArea(new RSTile(3365, 3524, 0), new RSTile(2963, 3967, 0));

    public static RSArea ABBERANT_SPECTRE_AREA_1 = new RSArea(new RSTile(2467, 9786, 0), new RSTile(2474, 9773, 0));
    public static RSArea WHOLE_ZANARIS = new RSArea(new RSTile(2508, 4404, 0), new RSTile(2369, 4481, 0));
    public static RSArea CHAELDAR_AREA = new RSArea(new RSTile(2442, 4432, 0), new RSTile(2451, 4425, 0));
    public static RSArea ABOVE_LUMBRIDGE_SWAMP_ENTRANCE = new RSArea(new RSTile(3172, 3170, 0), new RSTile(3167, 3174, 0));
    public static RSArea ABBERANT_SPECTRES_AREA = new RSArea(new RSTile(2467, 9786, 0), new RSTile(2474, 9775, 0));
    public static RSArea ANKOU_AREA = new RSArea(new RSTile(2486, 9795, 0), new RSTile(2466, 9810, 0));
    public static RSArea BASILISK_AREA = new RSArea(new RSTile(2752, 9998, 0), new RSTile(2734, 10018, 0));
    public static RSArea BANSHEE_AREA = new RSArea(new RSTile[]{new RSTile(3453, 3535, 0), new RSTile(3449, 3531, 0), new RSTile(3445, 3531, 0), new RSTile(3442, 3534, 0), new RSTile(3439, 3534, 0), new RSTile(3438, 3533, 0), new RSTile(3434, 3533, 0), new RSTile(3432, 3535, 0), new RSTile(3434, 3541, 0), new RSTile(3437, 3541, 0), new RSTile(3437, 3547, 0), new RSTile(3431, 3547, 0), new RSTile(3431, 3557, 0), new RSTile(3433, 3557, 0), new RSTile(3433, 3562, 0), new RSTile(3450, 3562, 0), new RSTile(3450, 3559, 0), new RSTile(3449, 3558, 0), new RSTile(3449, 3553, 0), new RSTile(3450, 3552, 0), new RSTile(3450, 3548, 0), new RSTile(3449, 3547, 0), new RSTile(3449, 3543, 0), new RSTile(3453, 3539, 0)});
    public static RSArea BAT_AREA = new RSArea(new RSTile[]{new RSTile(2899, 9845, 0), new RSTile(2899, 9823, 0), new RSTile(2927, 9823, 0), new RSTile(2933, 9832, 0), new RSTile(2931, 9836, 0), new RSTile(2920, 9845, 0)});
    public static RSArea BEAR_AREA = new RSArea(new RSTile(2714, 3329, 0), new RSTile(2680, 3346, 0));
    // public static RSArea BLUE_DRAGON_AREA = new RSArea(new RSTile(2922, 9796, 0), new RSTile(2913, 9807, 0));
    public static RSArea BLUE_DRAGON_AREA = new RSArea(
            new RSTile[]{
                    new RSTile(2907, 9814, 0),
                    new RSTile(2907, 9794, 0),
                    new RSTile(2927, 9784, 0),
                    new RSTile(2926, 9795, 0),
                    new RSTile(2922, 9799, 0),
                    new RSTile(2922, 9809, 0)
            }
    );
    //  public static RSArea BLACK_DEMON_AREA = new RSArea(new RSTile(1716, 10091, 0), new RSTile(1725, 10079, 0));
    public static RSArea BLACK_DEMON_AREA = new RSArea(new RSTile(1419, 10100, 1), new RSTile(1459, 10059, 1));
    public static RSArea BLOODVELD_AREA = new RSArea(new RSTile(2479, 9828, 0), new RSTile(2461, 9837, 0));
    public static RSArea BIRD_AREA = new RSArea(new RSTile(3226, 3300, 0), new RSTile(3235, 3295, 0));
    // blue dragon area is the area with baby blue drags.

    public static RSArea BEFORE_BABY_BLACK_DRAG_AREA = new RSArea(new RSTile(2879, 9828, 0), new RSTile(2885, 9821, 0));
    public static RSArea BABY_BLACK_DRAGON_AREA = new RSArea(new RSTile(2852, 9830, 1), new RSTile(2865, 9823, 1));
    public static RSArea CAVE_BUG_AREA = new RSArea(new RSTile(3144, 9580, 0), new RSTile(3163, 9557, 0));
    public static RSArea CAVE_CRAWLER_AREA = new RSArea(new RSTile[]{new RSTile(2780, 10006, 0), new RSTile(2772, 9999, 0), new RSTile(2786, 9986, 0), new RSTile(2805, 9994, 0), new RSTile(2801, 10007, 0)});
    public static RSArea CAVE_SLIME_AREA = new RSArea(new RSTile[]{new RSTile(3143, 9582, 0), new RSTile(3161, 9582, 0), new RSTile(3160, 9564, 0), new RSTile(3163, 9564, 0), new RSTile(3163, 9551, 0), new RSTile(3154, 9545, 0), new RSTile(3142, 9547, 0)});
    public static RSArea CRAWLING_HANDS_AREA = new RSArea(new RSTile[]{new RSTile(3431, 3536, 0), new RSTile(3408, 3536, 0), new RSTile(3408, 3542, 0), new RSTile(3409, 3543, 0), new RSTile(3409, 3553, 0), new RSTile(3431, 3553, 0)});
    public static RSArea COW_AREA = new RSArea(new RSTile[]{new RSTile(3243, 3298, 0), new RSTile(3243, 3282, 0), new RSTile(3249, 3278, 0), new RSTile(3251, 3276, 0), new RSTile(3253, 3272, 0), new RSTile(3253, 3255, 0), new RSTile(3264, 3255, 0), new RSTile(3264, 3298, 0)});
    public static RSArea COCKATRICE_AREA = new RSArea(new RSTile(2782, 10045, 0), new RSTile(2807, 10027, 0));
    public static RSArea CROCODILE_AREA = new RSArea(new RSTile[]{new RSTile(3337, 2925, 0), new RSTile(3341, 2928, 0), new RSTile(3355, 2923, 0), new RSTile(3361, 2929, 0), new RSTile(3364, 2915, 0), new RSTile(3335, 2914, 0)});
    public static RSTile DAGGANOTH_HOP_TILE = new RSTile(2515, 10004, 0);
    public static RSArea DOG_AREA = new RSArea(
            new RSTile[]{new RSTile(2642, 3308, 0), new RSTile(2643, 3309, 0), new RSTile(2643, 3315, 0),
                    new RSTile(2647, 3319, 0),
                    new RSTile(2647, 3329, 0), new RSTile(2646, 3329, 0), new RSTile(2643, 3332, 0), new RSTile(2643, 3326, 0), new RSTile(2644, 3326, 0), new RSTile(2644, 3320, 0),
                    new RSTile(2627, 3320, 0), new RSTile(2627, 3326, 0), new RSTile(2624, 3326, 0), new RSTile(2624, 3315, 0), new RSTile(2625, 3315, 0), new RSTile(2626, 3314, 0), new RSTile(2626, 3309, 0), new RSTile(2627, 3308, 0)});
    public static RSArea BLACK_DRAGON_AREA = new RSArea(new RSTile(2828, 9829, 0), new RSTile(2843, 9821, 0));
    public static RSArea INFERNAL_MAGE_AREA = new RSArea(new RSTile(3430, 3554, 1), new RSTile(3452, 3579, 1));
    public static RSArea ICE_GIANT_AREA = new RSArea(new RSTile[]{new RSTile(3041, 9588, 0), new RSTile(3041, 9575, 0), new RSTile(3054, 9565, 0), new RSTile(3064, 9569, 0), new RSTile(3067, 9580, 0), new RSTile(3064, 9588, 0)});
    public static RSArea DAGGANOTH_AREA = new RSArea(new RSTile(2510, 10036, 0), new RSTile(2541, 10011, 0));
    public static RSArea DWARF_AREA = new RSArea(new RSTile[]{new RSTile(3020, 3446, 0), new RSTile(3011, 3446, 0), new RSTile(3011, 3452, 0), new RSTile(3015, 3452, 0), new RSTile(3015, 3455, 0), new RSTile(3008, 3455, 0), new RSTile(3008, 3460, 0), new RSTile(3015, 3463, 0), new RSTile(3024, 3463, 0), new RSTile(3024, 3449, 0), new RSTile(3021, 3449, 0)});
    public static RSArea ELF_AREA = new RSArea(new RSTile(2187, 3262, 0), new RSTile(2212, 3243, 0));
    public static RSArea PRE_EARTH_WARRIOR_AREA = new RSArea(new RSTile(3119, 9964, 0), new RSTile(3122, 9961, 0));
    public static RSArea EARTH_WARRIOR_AREA = new RSArea(new RSTile(3128, 9969, 0), new RSTile(3113, 9981, 0));
    public static RSArea FIRE_GIANT_AREA = new RSArea(new RSTile(2391, 9774, 0), new RSTile(2408, 9767, 0));
    public static RSArea GREATER_DEMON_AREA = new RSArea(new RSTile(1679, 10091, 0), new RSTile(1691, 10081, 0));
    public static RSArea GARGOYLE_AREA = new RSArea(
            new RSTile[]{
                    new RSTile(3429, 9952, 3),
                    new RSTile(3442, 9952, 3),
                    new RSTile(3442, 9927, 3),
                    new RSTile(3437, 9931, 3),
                    new RSTile(3429, 9931, 3)
            }
    );
    public static RSArea GREEN_DRAGON_AREA = new RSArea(new RSTile(3342, 3663, 0), new RSTile(3318, 3697, 0));
    public static RSArea GHOST_AREA = new RSArea(new RSTile(2924, 9821, 0), new RSTile(2895, 9817, 0));
    public static RSArea GHOUL_AREA = new RSArea(new RSTile[]{new RSTile(3408, 3513, 0), new RSTile(3409, 3506, 0), new RSTile(3411, 3504, 0), new RSTile(3411, 3501, 0), new RSTile(3416, 3498, 0), new RSTile(3416, 3495, 0), new RSTile(3420, 3495, 0), new RSTile(3420, 3492, 0), new RSTile(3432, 3492, 0), new RSTile(3432, 3516, 0), new RSTile(3408, 3517, 0)});
    public static RSArea GOBLIN_AREA = new RSArea(new RSTile[]{new RSTile(3239, 3243, 0), new RSTile(3258, 3244, 0), new RSTile(3259, 3227, 0), new RSTile(3245, 3227, 0)});
    public static RSArea HARPIE_BUG_SWARM_AREA = new RSArea(new RSTile(2860, 3115, 0), new RSTile(2868, 3107, 0));
    public static RSArea HELLHOUND_AREA = new RSArea(new RSTile(2404, 9807, 0), new RSTile(2421, 9792, 0));
    public static RSArea HOBGOBLIN_AREA = new RSArea(
            new RSTile[]{
                    new RSTile(3113, 9875, 0),
                    new RSTile(3120, 9881, 0),
                    new RSTile(3127, 9881, 0),
                    new RSTile(3134, 9876, 0),
                    new RSTile(3134, 9868, 0),
                    new RSTile(3114, 9868, 0)
            }
    );
    public static RSArea BRINE_RAT_AREA = new RSArea(new RSTile(2700, 10138, 0), new RSTile(2713, 10128, 0));
    public static RSArea WHOLE_BRINE_RAT_CAVE = new RSArea(new RSTile(2685, 10153, 0), new RSTile(2742, 10116, 0));
    public static RSArea HILL_GIANT_AREA = new RSArea(new RSTile[]{new RSTile(3112, 9854, 0), new RSTile(3128, 9854, 0), new RSTile(3127, 9819, 0), new RSTile(3092, 9824, 0), new RSTile(3094, 9839, 0)});
    public static RSArea ICEFIEND_AREA = new RSArea(new RSTile[]{new RSTile(3025, 3466, 0), new RSTile(2995, 3467, 0), new RSTile(3002, 3481, 0), new RSTile(3006, 3489, 0), new RSTile(3012, 3489, 0), new RSTile(3014, 3479, 0), new RSTile(3022, 3476, 0)});
    public static RSArea JELLY_AREA = new RSArea(new RSTile(2716, 10037, 0), new RSTile(2694, 10019, 0));
    public static RSArea KALPHITE_AREA = new RSArea(new RSTile[]{new RSTile(3334, 9499, 0), new RSTile(3326, 9490, 0), new RSTile(3315, 9493, 0), new RSTile(3316, 9511, 0), new RSTile(3325, 9516, 0), new RSTile(3334, 9509, 0)});
    // public static RSArea KURASK_AREA = new RSArea(new RSTile(2687, 10009, 0), new RSTile(2708, 9989, 0));
    public static RSArea KURASK_AREA = new RSArea(new RSTile(2691, 9982, 0), new RSTile(2723, 9952, 0));
    public static RSArea LIZARD_AREA = new RSArea(new RSTile(3379, 3083, 0), new RSTile(3414, 3043, 0));
    public static RSArea LESSER_DEMON_AREA = new RSArea(new RSTile[]{new RSTile(2924, 9813, 0), new RSTile(2924, 9796, 0), new RSTile(2929, 9796, 0), new RSTile(2930, 9785, 0), new RSTile(2943, 9785, 0), new RSTile(2941, 9798, 0), new RSTile(2936, 9810, 0), new RSTile(2934, 9813, 0)});
    public static RSArea MUTATED_ZYGOMITE_AREA = new RSArea(new RSTile(2426, 4462, 0), new RSTile(2409, 4478, 0));
    public static RSArea MONKEY_AREA = new RSArea(new RSTile(2892, 3148, 0), new RSTile(2865, 3163, 0));
    public static RSArea MOSS_GIANT_AREA = new RSArea(new RSTile(2548, 3411, 0), new RSTile(2562, 3393, 0));
    public static RSArea MINOTAUR_AREA = new RSArea(
            new RSTile[]{
                    new RSTile(1869, 5228, 0),
                    new RSTile(1869, 5224, 0),
                    new RSTile(1875, 5224, 0),
                    new RSTile(1876, 5245, 0),
                    new RSTile(1868, 5245, 0),
                    new RSTile(1866, 5237, 0)
            }
    );

    public static RSArea PYREFIEND_AREA = new RSArea(new RSTile[]{new RSTile(2752, 10015, 0), new RSTile(2769, 10015, 0), new RSTile(2767, 10008, 0), new RSTile(2770, 10003, 0), new RSTile(2770, 9992, 0), new RSTile(2752, 9993, 0)});

    public static RSArea RAT_AREA = new RSArea(new RSTile(3227, 3170, 0), new RSTile(3208, 3191, 0));
    public static RSArea ROCKSLUG_AREA = new RSArea(new RSTile(2811, 10024, 0), new RSTile(2789, 10008, 0));

    public static RSArea SOURHOG_AREA = new RSArea(
            new RSTile[]{
                    new RSTile(3156, 9704, 0),
                    new RSTile(3158, 9704, 0),
                    new RSTile(3167, 9706, 0),
                    new RSTile(3167, 9706, 0),
                    new RSTile(3170, 9706, 0),
                    new RSTile(3174, 9704, 0),
                    new RSTile(3178, 9693, 0),
                    new RSTile(3185, 9687, 0),
                    new RSTile(3178, 9673, 0),
                    new RSTile(3169, 9667, 0),
                    new RSTile(3159, 9674, 0),
                    new RSTile(3151, 9703, 0)
            }
    );
    public static RSArea SUQAH_AREA = new RSArea(new RSTile(2110, 3947, 0), new RSTile(2121, 3939, 0));
    public static RSArea SCORPION_AREA = new RSArea(new RSTile[]{new RSTile(3306, 3278, 0), new RSTile(3306, 3283, 0), new RSTile(3302, 3287, 0), new RSTile(3302, 3288, 0), new RSTile(3306, 3292, 0), new RSTile(3306, 3294, 0), new RSTile(3303, 3298, 0), new RSTile(3292, 3297, 0), new RSTile(3293, 3285, 0), new RSTile(3290, 3283, 0), new RSTile(3292, 3281, 0), new RSTile(3293, 3279, 0)});
    public static RSArea SKELETON_AREA = new RSArea(new RSTile[]{new RSTile(3134, 9881, 0), new RSTile(3144, 9881, 0), new RSTile(3144, 9875, 0), new RSTile(3146, 9873, 0), new RSTile(3152, 9873, 0), new RSTile(3152, 9870, 0), new RSTile(3150, 9868, 0), new RSTile(3136, 9868, 0), new RSTile(3134, 9870, 0)});
    public static RSArea SPIDER_AREA = new RSArea(new RSTile[]{new RSTile(2134, 5261, 0), new RSTile(2137, 5266, 0), new RSTile(2136, 5277, 0), new RSTile(2115, 5277, 0), new RSTile(2120, 5265, 0)});
    public static RSArea SHADES_AREA = new RSArea(new RSTile(3479, 3293, 0), new RSTile(3497, 3279, 0));
    public static RSArea TUROTH_AREA = new RSArea(new RSTile[]{new RSTile(2713, 10014, 0), new RSTile(2721, 10017, 0), new RSTile(2730, 10013, 0), new RSTile(2734, 10004, 0), new RSTile(2734, 9991, 0), new RSTile(2714, 9993, 0)});
    public static RSArea TROLL_AREA = new RSArea(
            new RSTile[]{
                    new RSTile(2770, 10166, 0),
                    new RSTile(2782, 10166, 0),
                    new RSTile(2782, 10158, 0),
                    new RSTile(2772, 10146, 0),
                    new RSTile(2787, 10151, 0),
                    new RSTile(2789, 10134, 0),
                    new RSTile(2801, 10137, 0),
                    new RSTile(2806, 10127, 0),
                    new RSTile(2784, 10126, 0),
                    new RSTile(2780, 10140, 0),
                    new RSTile(2765, 10138, 0)
            }
    );

    public static RSArea WALL_BEAST_AREA = new RSArea(new RSTile(3160, 9574, 0), new RSTile(3165, 9572, 0));

    public static RSArea WEREWOLF_AREA = new RSArea(new RSTile[]{new RSTile(3507, 3480, 0), new RSTile(3507, 3490, 0), new RSTile(3504, 3491, 0), new RSTile(3504, 3494, 0), new RSTile(3503, 3495, 0), new RSTile(3503, 3499, 0), new RSTile(3493, 3499, 0), new RSTile(3482, 3494, 0), new RSTile(3483, 3480, 0)});
    public static RSArea WOLF_AREA = new RSArea(new RSTile(1875, 5225, 0), new RSTile(1868, 5242, 0));
    public static RSArea WYRM_AREA = new RSArea(new RSTile(1254, 10203, 0), new RSTile(1285, 10175, 0));

    public static RSArea ZOMBIE_AREA = new RSArea(new RSTile(3114, 9872, 0), new RSTile(3132, 9851, 0));
    public static RSArea TZHARR_AREA = new RSArea(
            new RSTile[]{
                    new RSTile(2453, 5174, 0),
                    new RSTile(2444, 5165, 0),
                    new RSTile(2453, 5158, 0),
                    new RSTile(2442, 5148, 0),
                    new RSTile(2453, 5138, 0),
                    new RSTile(2470, 5164, 0)
            }
    );
    public static RSArea TURAEL_AREA = new RSArea(new RSTile(2930, 3537, 0), new RSTile(2932, 3535, 0));
    public static RSArea VANNAKA_AREA = new RSArea(new RSTile(3147, 9912, 0), new RSTile(3144, 9915, 0));


    public static RSArea JAILER_AREA = new RSArea(new RSTile(2933, 9690, 0), new RSTile(2929, 9694, 0));
    public static RSArea JAIL_CELL = new RSArea(new RSTile(2928, 9689, 0), new RSTile(2934, 9683, 0));
    public static RSArea WHOLE_SOURHOG_CAVE = new RSArea(new RSTile(3150, 9720, 0), new RSTile(3182, 9670, 0));

    /**
     * HOP TILES
     */
    public static RSTile BLACK_DEMON_HOP_TILE = new RSTile(1724, 10094, 0);
    public static RSTile HELLHOUND_HOP_TILE = new RSTile(2425, 9809, 0);
    public static RSTile GARGOYLE_HOP_TILE = new RSTile(3424, 9936, 3);
    public static RSTile ANKOU_HOP_TILE = new RSTile(2471, 9816, 0);

    public static RSTile WYRM_HOP_TILE = new RSTile(1288, 10203, 0);
    /**
     * CANNON TILES
     */
    public static RSTile BLOODVELD_CANNON_TILE = new RSTile(2470, 9831, 0);
    public static RSTile KALPHITE_CANNON_TILE = new RSTile(3323, 9502, 0);
    public static RSTile HELLHOUND_CANNON_TILE = new RSTile(2414, 9799, 0);
    public static RSTile FIRE_GIANT_CANNON_TILE = new RSTile(2396, 9770, 0);
    public static RSTile ABERRANT_SPECTRE_CANNON_TILE = new RSTile(2470, 9779, 0);
    public static RSTile ANKOU_CANNON_TILE = new RSTile(2479, 9800, 0);
    public static RSTile ELF_CANNON_TILE = new RSTile(2201, 3259, 0);
    public static RSTile BLUE_DRAGON_CANNON_TILE = new RSTile(2918, 9802, 0);
    public static RSTile DAGGANOTH_CANNON_TILE = new RSTile(2523, 10025, 0);
    public static RSTile SUQAH_CANNON_TILE = new RSTile(2113, 3945, 0);


    /**
     * DAGGANOTH KINGS
     */
    public static RSArea WB_DUNGEON_1_PT_2 = new RSArea(
            new RSTile[]{
                    new RSTile(2492, 10165, 0),
                    new RSTile(2496, 10168, 0),
                    new RSTile(2503, 10170, 0),
                    new RSTile(2551, 10170, 0),
                    new RSTile(2551, 10165, 0),
                    new RSTile(2548, 10160, 0),
                    new RSTile(2552, 10154, 0),
                    new RSTile(2552, 10150, 0),
                    new RSTile(2550, 10141, 0),
                    new RSTile(2542, 10142, 0),
                    new RSTile(2542, 10156, 0),
                    new RSTile(2532, 10157, 0),
                    new RSTile(2530, 10160, 0),
                    new RSTile(2524, 10156, 0),
                    new RSTile(2513, 10158, 0),
                    new RSTile(2502, 10155, 0),
                    new RSTile(2492, 10162, 0)
            }
    );
    public static AreaRequirement wbDungeon1Pt2 = new AreaRequirement(WB_DUNGEON_1_PT_2);

    public static RSArea WB_DUNGEON_1_PT_2_LADDER_AREA = new RSArea(
            new RSTile[]{
                    new RSTile(2545, 10147, 0),
                    new RSTile(2546, 10147, 0),
                    new RSTile(2546, 10146, 0),
                    new RSTile(2548, 10144, 0),
                    new RSTile(2548, 10142, 0),
                    new RSTile(2546, 10140, 0),
                    new RSTile(2545, 10140, 0),
                    new RSTile(2542, 10143, 0),
                    new RSTile(2542, 10144, 0)
            }
    );
    public static AreaRequirement wbDungeon1LadderArea = new AreaRequirement(WB_DUNGEON_1_PT_2_LADDER_AREA);

    public static RSArea WB_DUNGEON_2 = new RSArea(
            new RSTile[]{
                    new RSTile(1884, 4413, 0),
                    new RSTile(1906, 4413, 0),
                    new RSTile(1911, 4409, 0),
                    new RSTile(1911, 4400, 0),
                    new RSTile(1901, 4397, 0),
                    new RSTile(1895, 4394, 0),
                    new RSTile(1890, 4382, 0),
                    new RSTile(1905, 4378, 0),
                    new RSTile(1914, 4373, 0),
                    new RSTile(1916, 4369, 0),
                    new RSTile(1929, 4369, 0),
                    new RSTile(1940, 4362, 0),
                    new RSTile(1952, 4366, 0),
                    new RSTile(1952, 4372, 0),
                    new RSTile(1965, 4372, 0),
                    new RSTile(1965, 4361, 0),
                    new RSTile(1947, 4353, 0),
                    new RSTile(1931, 4357, 0),
                    new RSTile(1921, 4361, 0),
                    new RSTile(1915, 4361, 0),
                    new RSTile(1901, 4357, 0),
                    new RSTile(1884, 4356, 0),
                    new RSTile(1878, 4360, 0),
                    new RSTile(1877, 4379, 0),
                    new RSTile(1881, 4391, 0),
                    new RSTile(1888, 4400, 0),
                    new RSTile(1883, 4405, 0)
            }
    );
    public static AreaRequirement wbDungeon2 = new AreaRequirement(WB_DUNGEON_2);

    public static RSArea WB_DUNGEON_3_PT_1 = new RSArea(
            new RSTile[]{
                    new RSTile(1795, 4398, 1),
                    new RSTile(1796, 4388, 1),
                    new RSTile(1809, 4388, 1),
                    new RSTile(1815, 4393, 1),
                    new RSTile(1815, 4395, 1),
                    new RSTile(1811, 4397, 1)
            }
    );
    public static AreaRequirement wbDungeon3Pt1 = new AreaRequirement(WB_DUNGEON_3_PT_1);

    public static RSArea WB_DUNGEON_3_PT_2 = new RSArea(
            new RSTile[]{
                    new RSTile(1801, 4385, 1),
                    new RSTile(1794, 4385, 1),
                    new RSTile(1794, 4371, 1),
                    new RSTile(1803, 4365, 1),
                    new RSTile(1808, 4369, 1),
                    new RSTile(1807, 4374, 1),
                    new RSTile(1802, 4379, 1)
            }
    );
    public static AreaRequirement wbDungeon3Pt2 = new AreaRequirement(WB_DUNGEON_3_PT_2);

    public static RSArea WB_DUNGEON_3_PT_3 = new RSArea(
            new RSTile[]{
                    new RSTile(1827, 4364, 1),
                    new RSTile(1827, 4359, 1),
                    new RSTile(1834, 4359, 1),
                    new RSTile(1836, 4357, 1),
                    new RSTile(1867, 4357, 1),
                    new RSTile(1867, 4363, 1),
                    new RSTile(1869, 4365, 1),
                    new RSTile(1869, 4372, 1),
                    new RSTile(1860, 4372, 1),
                    new RSTile(1860, 4365, 1),
                    new RSTile(1858, 4363, 1),
                    new RSTile(1832, 4363, 1),
                    new RSTile(1830, 4364, 1)
            }
    );
    public static AreaRequirement wbDungeon3Pt3 = new AreaRequirement(WB_DUNGEON_3_PT_3);

    public static RSArea WB_DUNGEON_3_PT_4 = new RSArea(
            new RSTile[]{
                    new RSTile(1862, 4388, 1),
                    new RSTile(1867, 4388, 1),
                    new RSTile(1867, 4400, 1),
                    new RSTile(1868, 4407, 1),
                    new RSTile(1888, 4407, 1),
                    new RSTile(1889, 4405, 1),
                    new RSTile(1894, 4405, 1),
                    new RSTile(1894, 4411, 1),
                    new RSTile(1891, 4413, 1),
                    new RSTile(1862, 4413, 1),
                    new RSTile(1860, 4404, 1),
                    new RSTile(1860, 4398, 1)
            }
    );
    public static AreaRequirement wbDungeon3Pt4 = new AreaRequirement(WB_DUNGEON_3_PT_4);

    public static RSArea WB_DUNGEON_4_PT_1 = new RSArea(
            new RSTile[]{
                    new RSTile(1796, 4389, 2),
                    new RSTile(1805, 4387, 2),
                    new RSTile(1805, 4382, 2),
                    new RSTile(1800, 4378, 2),
                    new RSTile(1794, 4380, 2),
                    new RSTile(1794, 4385, 2)
            }
    );
    public static AreaRequirement wbDungeon4Pt1 = new AreaRequirement(WB_DUNGEON_4_PT_1);

    public static RSArea WB_DUNGEON_4_PT_2 = new RSArea(
            new RSTile[]{
                    new RSTile(1797, 4374, 2),
                    new RSTile(1797, 4363, 2),
                    new RSTile(1821, 4356, 2),
                    new RSTile(1829, 4362, 2),
                    new RSTile(1818, 4374, 2)
            }
    );
    public static AreaRequirement wbDungeon4Pt2 = new AreaRequirement(WB_DUNGEON_4_PT_2);

    public static RSArea WB_DUNGEON_4_PT_3 = new RSArea(
            new RSTile[]{
                    new RSTile(1807, 4397, 2),
                    new RSTile(1807, 4388, 2),
                    new RSTile(1818, 4388, 2),
                    new RSTile(1823, 4379, 2),
                    new RSTile(1830, 4384, 2),
                    new RSTile(1837, 4385, 2),
                    new RSTile(1837, 4390, 2),
                    new RSTile(1825, 4397, 2)
            }
    );
    public static AreaRequirement wbDungeon4Pt3 = new AreaRequirement(WB_DUNGEON_4_PT_3);

    public static RSArea WB_DUNGEON_4_PT_4 = new RSArea(
            new RSTile[] {
                    new RSTile(1858, 4390, 2),
                    new RSTile(1861, 4372, 2),
                    new RSTile(1868, 4372, 2),
                    new RSTile(1871, 4390, 2)
            }
    );
    public static AreaRequirement wbDungeon4Pt4 = new AreaRequirement(WB_DUNGEON_4_PT_3);


    public static RSArea WB_DUNGEON_5_PT_1 = new RSArea(
            new RSTile[]{
                    new RSTile(1794, 4412, 3),
                    new RSTile(1808, 4412, 3),
                    new RSTile(1808, 4401, 3),
                    new RSTile(1801, 4399, 3),
                    new RSTile(1794, 4402, 3)
            }
    );
    public static AreaRequirement wbDungeon5Pt1 = new AreaRequirement(WB_DUNGEON_5_PT_1);

    public static RSArea WB_DUNGEON_5_PT_2 = new RSArea(
            new RSTile[]{
                    new RSTile(1824, 4411, 3),
                    new RSTile(1824, 4400, 3),
                    new RSTile(1834, 4401, 3),
                    new RSTile(1830, 4395, 3),
                    new RSTile(1830, 4392, 3),
                    new RSTile(1834, 4388, 3),
                    new RSTile(1852, 4401, 3),
                    new RSTile(1854, 4408, 3),
                    new RSTile(1849, 4412, 3),
                    new RSTile(1828, 4411, 3)
            }
    );
    public static AreaRequirement wbDungeon5Pt2 = new AreaRequirement(WB_DUNGEON_5_PT_2);

   public static RSArea WB_DUNGEON_4_PT_5  = new RSArea(
            new RSTile[] {
                    new RSTile(1808, 4411, 2),
                    new RSTile(1809, 4401, 2),
                    new RSTile(1824, 4401, 2),
                    new RSTile(1824, 4406, 2),
                    new RSTile(1818, 4410, 2)
            }
    );
    public static AreaRequirement wbDungeon4Pt5 = new AreaRequirement(WB_DUNGEON_4_PT_5);
    public static   RSArea DK_AREA = new RSArea(new RSTile(2938, 4426, 0), new RSTile(2892, 4471, 0));
    public static AreaRequirement dkArea = new AreaRequirement(DK_AREA);
}
