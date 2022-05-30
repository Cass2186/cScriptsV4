package scripts.Data;

import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import scripts.Requirements.AreaRequirement;

public class AgilityAreas {


    /**************
     * TREE GNOME *
     **************/
    public static RSArea TG_START_FINISH_AREA = new RSArea(new RSTile(2488, 3435, 0), new RSTile(2471, 3440, 0));
    public static RSArea TG_START_AREA = new RSArea(new RSTile(2477, 3436, 0), new RSTile(2471, 3438, 0));
    public static RSArea TG_OBSTACLE_2 = new RSArea(new RSTile(2470, 3429, 0), new RSTile(2478, 3425, 0));
    public static RSArea TG_OBSTACLE_3 = new RSArea(new RSTile(2470, 3425, 1), new RSTile(2476, 3421, 1));
    public static RSArea TG_OBSTACLE_4 = new RSArea(new RSTile(2472, 3421, 2), new RSTile(2477, 3418, 2));

    public static RSArea TG_OBSTACLE_5 = new RSArea(new RSTile(2488, 3418, 2), new RSTile(2483, 3421, 2));
    public static RSArea TG_OBSTACLE_6 = new RSArea(new RSTile(2488, 3418, 0), new RSTile(2483, 3425, 0));
    public static RSArea TG_OBSTACLE_7 = new RSArea(new RSTile(2488, 3426, 0), new RSTile(2483, 3431, 0));
    public static RSArea WHOLE_TG_COURSE_LEVEL_0 = new RSArea(new RSTile(2469, 3440, 0), new RSTile(2490, 3413, 0));
    public static RSArea WHOLE_TG_COURSE_LEVEL_2 = new RSArea(new RSTile(2467, 3439, 2), new RSTile(2491, 3411, 2));
    public static RSArea WHOLE_TG_COURSE_LEVEL_1 = new RSArea(new RSTile(2468, 3431, 1), new RSTile(2488, 3416, 1));

    public static AreaRequirement onTreeGnomeCourseReq = new AreaRequirement(
            WHOLE_TG_COURSE_LEVEL_0,
            WHOLE_TG_COURSE_LEVEL_1,
            WHOLE_TG_COURSE_LEVEL_2);
    /**
     * DRAYNOR
     */

    public static RSArea DRAYNOR_GROUND_LEVEL = new RSArea(new RSTile(3105, 3239, 0), new RSTile(3080, 3282, 0));
    public static RSArea WHOLE_DRAYNOR_COURSE =
            new RSArea(new RSTile(3081, 3284, 3), new RSTile(3111, 3250, 3));
    public static RSArea ALL_OF_DRAYNOR_VILLAGE = new RSArea(new RSTile(3108, 3283, 0), new RSTile(3072, 3234, 0));
    public static RSArea DRAYNOR_START_AREA = new RSArea(new RSTile(3103, 3281, 0), new RSTile(3105, 3277, 0));
    public static RSArea DRAYNOR_LARGE_START_AREA = new RSArea(new RSTile(3102, 3283, 0), new RSTile(3107, 3273, 0));
    public static RSArea DRAYNOR_OBSTACLE_AREA_1 = new RSArea(new RSTile(3102, 3277, 3), new RSTile(3096, 3281, 3));
    public static RSArea DRAYNOR_OBSTACLE_AREA_2 = new RSArea(new RSTile(3093, 3273, 3), new RSTile(3088, 3278, 3));
    public static RSArea DRAYNOR_OBSTACLE_AREA_3 = new RSArea(new RSTile(3088, 3268, 3), new RSTile(3094, 3265, 3));
    public static RSArea DRAYNOR_OBSTACLE_AREA_4 = new RSArea(new RSTile(3088, 3256, 3), new RSTile(3084, 3261, 3));
    public static RSArea DRAYNOR_OBSTACLE_AREA_5 = new RSArea(new RSTile(3095, 3255, 3), new RSTile(3085, 3255, 3));
    public static RSArea DRAYNOR_OBSTACLE_AREA_6 = new RSArea(new RSTile(3086, 3256, 3), new RSTile(3095, 3254, 3));
    public static RSArea DRAYNOR_OBSTACLE_AREA_7 = new RSArea(new RSTile(3095, 3256, 3), new RSTile(3102, 3261, 3));

    public static AreaRequirement onDraynorCourseReq = new AreaRequirement(WHOLE_DRAYNOR_COURSE);



    /**********************
     /****** VARROCK *****
     /*******************/
    public static RSArea VARROCK_LARGE_START_AREA = new RSArea(new RSTile(3216, 3420, 0), new RSTile(3241, 3410, 0));
    //  public static    RSArea VARROCK_LARGE_START_AREA = new RSArea(new RSTile(3221, 3422, 0), new RSTile(3240, 3415, 0));
    public static RSArea VARROCK_GROUND_AREA = new RSArea(new RSTile(3190, 3436, 0), new RSTile(3240, 3388, 0));
    public static RSArea VARROCK_LEVEL_3 = new RSArea(new RSTile(3174, 3430, 3), new RSTile(3246, 3381, 3));
    public static RSArea VARROCK_LEVEL_1 = new RSArea(new RSTile(3174, 3444, 1), new RSTile(3272, 3359, 1));
    public static RSArea VARROCK_GROUND_START_AREA = new RSArea(new RSTile(3221, 3418, 0), new RSTile(3226, 3411, 0));
    public static RSArea VARROCK_END_GROUND_AREA = new RSArea(new RSTile(3229, 3419, 0), new RSTile(3243, 3409, 0));
    public static RSArea VARROCK_AREA_1 = new RSArea(new RSTile(3214, 3419, 3), new RSTile(3219, 3410, 3));
    public static RSArea VARROCK_AREA_2 = new RSArea(new RSTile(3200, 3419, 3), new RSTile(3208, 3413, 3));
    public static RSArea VARROCK_AREA_3 = new RSArea(new RSTile(3192, 3416, 1), new RSTile(3197, 3416, 1));
    public static RSArea VARROCK_AREA_4 = new RSArea(new RSTile(3192, 3406, 3), new RSTile(3198, 3402, 3));
    public static RSArea VARROCK_AREA_5 = new RSArea(
            new RSTile[]{
                    new RSTile(3202, 3404, 3),
                    new RSTile(3209, 3404, 3),
                    new RSTile(3209, 3395, 3),
                    new RSTile(3201, 3395, 3),
                    new RSTile(3190, 3382, 3),
                    new RSTile(3181, 3382, 3),
                    new RSTile(3182, 3399, 3),
                    new RSTile(3202, 3399, 3)
            }
    );

    public static RSArea VARROCK_AREA_6 = new RSArea(new RSTile(3214, 3402, 3), new RSTile(3232, 3393, 3));
    public static RSArea VARROCK_AREA_7 = new RSArea(new RSTile(3232, 3402, 3), new RSTile(3218, 3393, 3));
    public static RSArea VARROCK_AREA_9 = new RSArea(new RSTile(3241, 3410, 3), new RSTile(3234, 3416, 3));
    public static RSArea VARROCK_FINAL_AREA = new RSArea(new RSTile(3241, 3409, 3), new RSTile(3235, 3416, 3));
    public static RSArea VARROCK_SECOND_LAST_AREA = new RSArea(new RSTile(3234, 3409, 3), new RSTile(3242, 3401, 3));
    public static RSTile[] VARROCK_PATH_TO_START = {
            new RSTile(3237, 3417, 0),
            new RSTile(3222, 3415, 0)
    };

    public static AreaRequirement onVarrockCourseReq = new AreaRequirement(VARROCK_LEVEL_1, VARROCK_LEVEL_3);

    /**********************
     /****** CANIFIS *****
     /*******************/
    public static RSArea CANIFIS_SMALL_START = new RSArea(
            new RSTile[]{
                    new RSTile(3509, 3488, 0),
                    new RSTile(3504, 3488, 0),
                    new RSTile(3504, 3485, 0),
                    new RSTile(3511, 3486, 0)
            }
    );
    public static RSArea CANIFIS_LARGE_START = new RSArea(new RSTile(3510, 3487, 0), new RSTile(3502, 3490, 0));
    // public static RSArea CANIFIS_SMALL_START = new RSArea(new RSTile(3508, 3488, 0), new RSTile(3504, 3490, 0));
    public static RSArea ALL_CANIFIS = new RSArea(new RSTile(3469, 3509, 0), new RSTile(3526, 3462, 0));
    public static RSArea CANIFIS_OBSTACLE_1 = new RSArea(new RSTile(3511, 3489, 2), new RSTile(3503, 3499, 2));
    public static RSArea CANIFIS_OBSTACLE_2 = new RSArea(new RSTile(3504, 3507, 2), new RSTile(3495, 3503, 2));
    public static RSArea CANIFIS_OBSTACLE_3 = new RSArea(new RSTile(3493, 3505, 2), new RSTile(3483, 3498, 2));
    public static RSArea CANIFIS_OBSTACLE_4 = new RSArea(new RSTile(3473, 3500, 3), new RSTile(3480, 3491, 3));
    public static RSArea CANIFIS_OBSTACLE_5 = new RSArea(new RSTile(3476, 3487, 2), new RSTile(3484, 3481, 2));
    public static RSArea CANIFIS_OBSTACLE_6 = new RSArea(new RSTile(3486, 3479, 3), new RSTile(3504, 3468, 3));
    public static RSArea CANIFIS_OBSTACLE_7 = new RSArea(new RSTile(3508, 3474, 2), new RSTile(3516, 3483, 2));
    public static RSArea CANIFIS_FINISHED_AREA = new RSArea(new RSTile(3512, 3484, 0), new RSTile(3503, 3486, 0));
    public static RSArea ALL_CANIFIS_ROOFTOPS = new RSArea(new RSTile(3518, 3462, 3), new RSTile(3469, 3513, 3));
    public static RSArea ALL_CANIFIS_ROOFTOPS_LEVEL_2 = new RSArea(new RSTile(3518, 3465, 2), new RSTile(3471, 3508, 2));

    public static AreaRequirement onCanifisCourseReq = new AreaRequirement(ALL_CANIFIS_ROOFTOPS,
            ALL_CANIFIS_ROOFTOPS_LEVEL_2);


    /***************
     **** SEERS ****
     **************/
    public static final RSArea SEERS_WALL_AREA = new RSArea(new RSTile(2730, 3487, 0), new RSTile(2727, 3489, 0));
    public static final RSArea SEERS_LARGE_WALL_AREA = new RSArea(new RSTile(2732, 3485, 0), new RSTile(2725, 3491, 0));

    public static final RSTile[] SEERS_PATH_TO_WALL = {
            new RSTile(2718, 3468, 0),
            new RSTile(2726, 3476, 0),
            new RSTile(2728, 3483, 0),
            new RSTile(2729, 3488, 0)
    };
    public static final RSArea SEERS_EDGE_AREA = new RSArea(new RSTile(2698, 3465, 2), new RSTile(2702, 3460, 2));
    public static final RSArea SEERS_GAP_ONE_AREA = new RSArea(new RSTile(2721, 3497, 3), new RSTile(2730, 3490, 3));
    //public static final RSArea SEERS_GAP_TWO_AREA = new RSArea(new RSTile(2716, 3482, 2), new RSTile(2709, 3476, 2));
    public static final RSArea SEERS_GAP_TWO_AREA = new RSArea(new RSTile(2709, 3483, 2), new RSTile(2716, 3476, 2));
    public static final RSArea SEERS_GAP_THREE_AREA = new RSArea(new RSTile[]{
            new RSTile(2716, 3470, 3), new RSTile(2716, 3473, 3),
            new RSTile(2705, 3473, 3), new RSTile(2705, 3476, 3),
            new RSTile(2699, 3476, 3), new RSTile(2699, 3469, 3),
            new RSTile(2704, 3469, 3), new RSTile(2705, 3471, 3)
    });
    public static final RSArea SEERS_TIGHT_ROPE_AREA = new RSArea(new RSTile[]{
            new RSTile(2714, 3495, 2), new RSTile(2711, 3495, 2),
            new RSTile(2710, 3496, 2), new RSTile(2704, 3496, 2),
            new RSTile(2704, 3492, 2), new RSTile(2706, 3492, 2),
            new RSTile(2706, 3488, 2), new RSTile(2711, 3488, 2),
            new RSTile(2711, 3492, 2), new RSTile(2714, 3493, 2)});

    public static RSArea SEERS_END_AREA = new RSArea(new RSTile(2704, 3466, 0), new RSTile(2714, 3458, 0));
    public static RSArea UPSTAIRS_SEERS_BANK = new RSArea(new RSTile(2731, 3490, 1), new RSTile(2721, 3497, 1));

    public static AreaRequirement onSeersCourseReq = new AreaRequirement(
            SEERS_GAP_ONE_AREA,
            SEERS_GAP_TWO_AREA,
            SEERS_GAP_THREE_AREA ,
            SEERS_TIGHT_ROPE_AREA,
            SEERS_END_AREA
            );

    /**
     * FALADOR AREAS
     */

    public static RSArea FALADOR_AREA_ONE = new RSArea(
            new RSTile[]{
                    new RSTile(3041, 3342, 3),
                    new RSTile(3041, 3348, 3),
                    new RSTile(3034, 3348, 3),
                    new RSTile(3034, 3344, 3),
                    new RSTile(3036, 3344, 3),
                    new RSTile(3036, 3342, 3)
            }
    );
    public static RSArea FALADOR_AREA_TWO = new RSArea(
            new RSTile[] {
                    new RSTile(3044, 3343, 3),
                    new RSTile(3046, 3343, 3),
                    new RSTile(3046, 3341, 3),
                    new RSTile(3050, 3341, 3),
                    new RSTile(3052, 3347, 3),
                    new RSTile(3052, 3350, 3),
                    new RSTile(3047, 3350, 3),
                    new RSTile(3044, 3346, 3)
            }
    );
    public static RSArea FALADOR_AREA_TWO_b = new RSArea(
            new RSTile[]{
                    new RSTile(3045, 3343, 3),
                    new RSTile(3045, 3348, 3),
                    new RSTile(3048, 3351, 3),
                    new RSTile(3052, 3351, 3),
                    new RSTile(3052, 3347, 3),
                    new RSTile(3050, 3347, 3),
                    new RSTile(3050, 3341, 3),
                    new RSTile(3045, 3341, 3)
            }
    );
    public static RSArea FALADOR_AREA_THREE = new RSArea(new RSTile(3048, 3359, 3), new RSTile(3050, 3356, 3));
    public static RSArea FALADOR_AREA_FOUR = new RSArea(new RSTile(3045, 3367, 3), new RSTile(3048, 3361, 3));
    public static RSArea FALADOR_AREA_FIVE = new RSArea(new RSTile(3041, 3361, 3), new RSTile(3033, 3364, 3));
    public static RSArea FALADOR_AREA_SIX = new RSArea(new RSTile(3026, 3355, 3), new RSTile(3029, 3352, 3));
    public static RSArea FALADOR_AREA_SEVEN = new RSArea(new RSTile(3021, 3353, 3), new RSTile(3008, 3358, 3));
    // public static RSArea FALADOR_FINISH_AREA = new RSArea(new RSTile(3028, 3336, 0), new RSTile(3035, 3331, 0));

       public static RSArea FALADOR_FINISH_AREA = new RSArea(
            new RSTile[]{
                    new RSTile(3040, 3342, 0),
                    new RSTile(3029, 3342, 0),
                    new RSTile(3029, 3338, 0),
                    new RSTile(3028, 3338, 0),
                    new RSTile(3028, 3331, 0),
                    new RSTile(3040, 3331, 0)
            }
    );

    public static RSArea FALADOR_AREA_EIGHT = new RSArea(
            new RSTile[]{
                    new RSTile(3016, 3350, 3),
                    new RSTile(3016, 3343, 3),
                    new RSTile(3023, 3343, 3),
                    new RSTile(3023, 3350, 3)
            }
    );

    public static RSArea FALADOR_AREA_NINE = new RSArea(new RSTile(3014, 3349, 3), new RSTile(3009, 3343, 3));

    public static RSArea FALADOR_AREA_TEN = new RSArea(new RSTile(3013, 3342, 3), new RSTile(3008, 3335, 3));


    public static RSArea FALADOR_AREA_ELEVEN = new RSArea(
            new RSTile[] {
                    new RSTile(3012, 3335, 3),
                    new RSTile(3018, 3335, 3),
                    new RSTile(3018, 3331, 3),
                    new RSTile(3012, 3331, 3)
            }
    );

    public static   RSArea FALADOR_AREA_TWELVE = new RSArea(new RSTile(3019, 3335, 3), new RSTile(3027, 3332, 3));
    public static AreaRequirement onFaladorCourseReq = new AreaRequirement(
            FALADOR_AREA_ONE,
            FALADOR_AREA_TWO,
            FALADOR_AREA_THREE,
            FALADOR_AREA_FOUR,
            FALADOR_AREA_FIVE,
            FALADOR_AREA_SIX,
            FALADOR_AREA_SEVEN,
            FALADOR_AREA_EIGHT,
            FALADOR_AREA_NINE,
            FALADOR_AREA_TEN,
            FALADOR_AREA_ELEVEN,
            FALADOR_AREA_TWELVE
    );

    /**
     * POLV area
     */
    public static RSArea POLV_LARGE_START_AREA = new RSArea(new RSTile(3349, 2964, 0), new RSTile(3356, 2959, 0));
    public static RSArea POLV_START_AREA = new RSArea(new RSTile(3349, 2962, 0), new RSTile(3352, 2959, 0));
    public static RSArea POLV_OBS_1_AREA = new RSArea(new RSTile(3345, 2968, 1), new RSTile(3351, 2962, 1));
    public static RSArea POLV_OBS_2_AREA = new RSArea(new RSTile(3351, 2972, 1), new RSTile(3356, 2977, 1));
    public static RSArea POLV_OBS_3_AREA = new RSArea(new RSTile(3362, 2976, 1), new RSTile(3359, 2979, 1));
    public static RSArea POLV_OBS_4_AREA = new RSArea(new RSTile(3365, 2976, 1), new RSTile(3371, 2973, 1));
    public static RSArea POLV_OBS_5_AREA = new RSArea(new RSTile(3369, 2981, 1), new RSTile(3364, 2986, 1));
    public static RSArea POLV_OBS_6_AREA = new RSArea(new RSTile(3365, 2979, 2), new RSTile(3354, 2985, 2));
    public static RSArea POLV_OBS_7_AREA = new RSArea(new RSTile[]{new RSTile(3371, 2990, 2), new RSTile(3363, 2990, 2), new RSTile(3363, 2991, 2), new RSTile(3357, 2991, 2), new RSTile(3357, 2996, 2), new RSTile(3371, 2996, 2)});
    public static RSArea POLV_OBS_8_AREA = new RSArea(new RSTile(3355, 3004, 2), new RSTile(3362, 3000, 2));
    public static RSArea ALL_POLV_LEVEL_0 = new RSArea(new RSTile(3340, 3005, 0), new RSTile(3377, 2944, 0));
    public static RSTile POLV_START_TILE = new RSTile(3352, 2962, 0);
    //RSTile POLV_START_TILE = new RSTile(3351, 2961,0);

    /**
     * RELLEKA
     */
    public static RSArea RELLEKA_LARGE_START = new RSArea(new RSTile(2621, 3679, 0), new RSTile(2627, 3677, 0));
    // just in front of the jump up area
    public static RSTile RELLEKA_START_TILE = new RSTile(2625, 3678, 0);
    public static RSArea RELLEKA_OBS_1_AREA = new RSArea(new RSTile(2626, 3672, 3), new RSTile(2621, 3676, 3));
    public static RSArea RELLEKA_OBS_2_AREA = new RSArea(new RSTile(2614, 3668, 3), new RSTile(2622, 3658, 3));
    // 3 could be reduced by increasing x by 1
    public static RSArea RELLEKA_OBS_3_AREA = new RSArea(new RSTile(2625, 3655, 3), new RSTile(2630, 3651, 3));
    public static RSArea RELLEKA_OBS_4_AREA = new RSArea(new RSTile(2638, 3653, 3), new RSTile(2644, 3649, 3));
    public static RSArea RELLEKA_OBS_5_AREA = new RSArea(new RSTile(2642, 3662, 3), new RSTile(2650, 3657, 3));
    public static RSArea RELLEKA_OBS_6_AREA = new RSArea(new RSTile(2662, 3665, 3), new RSTile(2655, 3685, 3));
    //where you land after finishing
   public static RSArea RELLEKA_FINISH_AREA = new RSArea(new RSTile(2654, 3671, 0), new RSTile(2647, 3681, 0));

    /**
     * WILDERNESS
     */

    public static   RSArea WILDERNESS_START_AREA  = new RSArea(
            new RSTile[] {
                    new RSTile(3003, 3939, 0),
                    new RSTile(2999, 3934, 0),
                    new RSTile(2988, 3934, 0),
                    new RSTile(2991, 3931, 0),
                    new RSTile(3004, 3931, 0),
                    new RSTile(3007, 3934, 0),
                    new RSTile(3007, 3939, 0)
            }
    );
    public static   RSArea AFTER_OBSTACLE_PIPE_WILDERNESS = new RSArea(
            new RSTile[] {
                    new RSTile(3003, 3950, 0),
                    new RSTile(3006, 3950, 0),
                    new RSTile(3007, 3947, 0),
                    new RSTile(3009, 3950, 0),
                    new RSTile(3009, 3952, 0),
                    new RSTile(3010, 3953, 0),
                    new RSTile(3010, 3954, 0),
                    new RSTile(3007, 3953, 0),
                    new RSTile(3003, 3953, 0)
            }
    );

    public static   RSArea AFTER_ROPE_SWING_WILDERNESS = new RSArea(
            new RSTile[] {
                    new RSTile(3003, 3957, 0),
                    new RSTile(3001, 3959, 0),
                    new RSTile(3002, 3961, 0),
                    new RSTile(3002, 3963, 0),
                    new RSTile(3000, 3964, 0),
                    new RSTile(2998, 3964, 0),
                    new RSTile(2998, 3967, 0),
                    new RSTile(3005, 3967, 0),
                    new RSTile(3008, 3964, 0),
                    new RSTile(3008, 3961, 0),
                    new RSTile(3010, 3959, 0),
                    new RSTile(3010, 3958, 0),
                    new RSTile(3003, 3958, 0)
            }
    );

    public static   RSArea AFTER_STEPPING_STONES_WILDERNESS  = new RSArea(
            new RSTile[] {
                    new RSTile(2998, 3967, 0),
                    new RSTile(2998, 3964, 0),
                    new RSTile(2997, 3962, 0),
                    new RSTile(2997, 3961, 0),
                    new RSTile(2997, 3959, 0),
                    new RSTile(2998, 3958, 0),
                    new RSTile(3002, 3958, 0),
                    new RSTile(3003, 3957, 0),
                    new RSTile(3003, 3944, 0),
                    new RSTile(3002, 3943, 0),
                    new RSTile(3001, 3943, 0),
                    new RSTile(3001, 3949, 0),
                    new RSTile(2994, 3949, 0),
                    new RSTile(2990, 3953, 0),
                    new RSTile(2990, 3964, 0)
            }
    );
    public static   RSArea FAIL_AREA_UNDERGROUND_WILDERNESS  = new RSArea(new RSTile(3008, 10365, 0), new RSTile(2992, 10340, 0));
    public static   RSArea AFTER_LOG_WILDERNESS  = new RSArea(
            new RSTile[] {
                    new RSTile(2996, 3949, 0),
                    new RSTile(2996, 3943, 0),
                    new RSTile(3002, 3943, 0),
                    new RSTile(3003, 3936, 0),
                    new RSTile(2988, 3936, 0),
                    new RSTile(2988, 3946, 0),
                    new RSTile(2991, 3946, 0),
                    new RSTile(2993, 3949, 0)
            }
    );

    public static    RSArea ARDOUGNE_START_AREA  = new RSArea(
            new RSTile[] {
                    new RSTile(2666, 3298, 0),
                    new RSTile(2666, 3294, 0),
                    new RSTile(2676, 3294, 0),
                    new RSTile(2676, 3299, 0),
                    new RSTile(2669, 3299, 0)
            }
    );
    public static  RSArea ARDOUGNE_AREA_ONE = new RSArea(new RSTile(2671, 3310, 3), new RSTile(2671, 3299, 3));
    public static   RSArea ARDOUGNE_AREA_TWO = new RSArea(new RSTile(2665, 3318, 3), new RSTile(2660, 3318, 3));

    public static    RSArea ARDOUGNE_AREA_THREE = new RSArea(new RSTile(2657, 3318, 3), new RSTile(2653, 3318, 3));
    public static RSArea ARDOUGNE_AREA_FOUR = new RSArea(new RSTile(2653, 3310, 3), new RSTile(2650, 3314, 3));
    public static RSArea ARDOUGNE_AREA_FIVE = new RSArea(new RSTile(2651, 3309, 3), new RSTile(2653, 3304, 3));
    public static RSArea ARDOUGNE_AREA_SIX = new RSArea(new RSTile(2652, 3303, 3), new RSTile(2654, 3300, 3));
    public static  RSArea ARDOUGNE_AREA_SEVEN = new RSArea(new RSTile(2655, 3297, 3), new RSTile(2657, 3291, 3));
}