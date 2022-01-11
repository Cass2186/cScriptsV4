package scripts.QuestPackages.MonkeyMadnessI;

import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;

public class MMConst {


    public static RSTile kingTile = new RSTile(2465, 3495, 0);
    public static RSTile compoundGate = new RSTile(2944, 3042, 0);
    public static RSTile GLOTile = new RSTile(2955, 3025, 0);
    public static RSTile daeroTile = new RSTile(2484, 3488, 1);
    public static RSTile gliderBaseCentre = new RSTile(2393, 9892, 0);
    public static RSTile outsideDungeon = new RSTile(2764, 2703, 0);
    public static RSTile zooknocktile = new RSTile(2805, 9145, 0);
    public static RSTile garkorTile = new RSTile(2806, 2760, 0);
    public static RSTile monkeyChildHidingTile = new RSTile(2746, 2802, 0);
    public static RSTile karamTile = new RSTile(2780, 2801, 0);
    public static RSTile preCaptureTile = new RSTile(2726, 2738, 0);
    public static RSTile inFrontOfFlames = new RSTile(2807, 9206, 0);
    public static RSTile apeAtollBeachTile = new RSTile(2799, 2703, 0);
    public static RSTile OUTSIDE_JAIL_TILE = new RSTile(2762, 2804, 0);
    public static RSTile INSIDE_CELL_SAFE_TILE = new RSTile(2772, 2794, 0);


    public static RSArea GLIDER_BASE = new RSArea(gliderBaseCentre, 10);
    public static RSArea outsidePrison = new RSArea(new RSTile(2761, 2805, 0), 4);
    public static RSArea outsideMonkeyChildArea = new RSArea(new RSTile(2743, 2787, 0), 4);
    public static RSArea hidingArea = new RSArea(monkeyChildHidingTile, 1);
    public static RSArea monkeyCage = new RSArea(new RSTile(2599, 3282, 0), new RSTile(2606, 3276, 0));
    public static RSArea beachArea = new RSArea(apeAtollBeachTile, 10);
    public static RSArea aowowgeiAreaOutside = new RSArea(new RSTile(2801, 2755, 0), 4);
    public static RSArea CAPTURE_AREA = new RSArea(new RSTile(2719, 2758, 0), new RSTile(2723, 2754, 0));
    public static RSArea KARAM_AREA = new RSArea(new RSTile(2784, 2796, 0), new RSTile(2777, 2808, 0));
    public static RSArea gloughUnder = new RSArea(new RSTile(2474, 3464, 0), new RSTile(2479, 3461, 0));
    public static RSArea gloughArea = new RSArea(new RSTile(2475, 3465, 1), new RSTile(2484, 3462, 1));
    public static RSArea HANGAR = new RSArea(new RSTile(2383, 9903, 0), new RSTile(2401, 9874, 0));
    public static RSArea ISLAND_AREA = new RSArea(new RSTile(2719, 2803, 0), new RSTile(2813, 2692, 0)); // whole Island
    public static RSArea GARKOR_AREA = new RSArea(new RSTile(2805, 2762, 0), new RSTile(2808, 2756, 0));
    public static RSArea LARGE_SHOP_AREA = new RSArea(new RSTile(2757, 2766, 0), new RSTile(2758, 2772, 0));
    public static RSArea SMALL_SHOP_AREA = new RSArea(new RSTile(2757, 2767, 0), new RSTile(2758, 2770, 0));
    public static RSArea outsideJailArea2 = new RSArea(new RSTile(2776, 2803, 0), new RSTile(2759, 2807, 0));
    public static RSArea outsideJailArea1 = new RSArea(new RSTile(2763, 2799, 0), new RSTile(2759, 2802, 0));
    public static RSArea outsideJailDoor = new RSArea(new RSTile(2772, 2796, 0), new RSTile(2770, 2797, 0));
    public static RSArea TRIGGER_AREA = new RSArea(new RSTile(2771, 2798, 0), new RSTile(2764, 2804, 0));
    public static RSArea JAIL_CELL_SAFE_AREA = new RSArea(new RSTile(2776, 2793, 0), new RSTile(2770, 2794, 0));
    public static RSArea JAIL_CELL_WAITING_AREA = new RSArea(new RSTile(2770, 2794, 0), new RSTile(2773, 2793, 0));
    public static RSArea JAIL_CELL = new RSArea(new RSTile(2770, 2795, 0), new RSTile(2776, 2793, 0));
    public static RSArea OUTSIDE_CELL_SAFE_AREA = new RSArea(new RSTile(2766, 2795, 0), new RSTile(2769, 2793, 0));
    public static RSArea wholeJailArea = new RSArea(new RSTile(2776, 2793, 0), new RSTile(2764, 2802, 0));
    // this is the whole Island EXCEPT the beach.
    public static RSArea WHOLE_ISLAND = new RSArea(
            new RSTile[] {
                    new RSTile(2794, 2690, 0),
                    new RSTile(2794, 2726, 0),
                    new RSTile(2813, 2726, 0),
                    new RSTile(2817, 2811, 0),
                    new RSTile(2703, 2809, 0),
                    new RSTile(2690, 2693, 0)
            }
    );
    //****** for the melee monkeys******///
    public static RSArea MONKEY_TEMPLE = new RSArea(new RSTile(2810, 2775, 0), new RSTile(2784, 2797, 0));
    public static RSArea BLOCKING_AREA = new RSArea(new RSTile(2806, 2788, 0), new RSTile(2808, 2786, 0));
    public static RSArea MELEE_SAFE_AREA = new RSArea(new RSTile(2804, 2794, 0), new RSTile(2809, 2789, 0));
    public static RSArea AREA_TO_START_MELEE_PRAY = new RSArea(new RSTile(2779, 2798, 0), new RSTile(2782, 2796, 0));
    public static RSArea undergroundFlameArea = new RSArea(inFrontOfFlames, 25);


    public static RSArea AMULET_MOULD_UNDERGROUND = new RSArea(new RSTile(2781, 9175, 0), new RSTile(2806, 9161, 0));
    public static RSArea SOUTH_MELEE_SAFE_AREA = new RSArea(new RSTile(2803, 2781, 0), new RSTile(2809, 2775, 0));


    /**
     * PATHS
     */
    public static final RSTile[] GARKOR_PATH = new RSTile[]{new RSTile(2780, 2798, 0), new RSTile(2781, 2798, 0), new RSTile(2782, 2798, 0), new RSTile(2783, 2796, 0), new RSTile(2783, 2795, 0), new RSTile(2783, 2794, 0), new RSTile(2783, 2793, 0), new RSTile(2783, 2792, 0), new RSTile(2783, 2791, 0), new RSTile(2783, 2790, 0), new RSTile(2783, 2789, 0), new RSTile(2783, 2788, 0), new RSTile(2783, 2787, 0), new RSTile(2783, 2786, 0), new RSTile(2783, 2785, 0), new RSTile(2783, 2783, 0), new RSTile(2783, 2782, 0), new RSTile(2783, 2780, 0), new RSTile(2783, 2779, 0), new RSTile(2783, 2778, 0), new RSTile(2784, 2777, 0), new RSTile(2785, 2775, 0), new RSTile(2786, 2774, 0), new RSTile(2787, 2774, 0), new RSTile(2788, 2773, 0), new RSTile(2789, 2772, 0), new RSTile(2790, 2771, 0), new RSTile(2791, 2770, 0), new RSTile(2792, 2770, 0), new RSTile(2793, 2770, 0), new RSTile(2794, 2770, 0), new RSTile(2795, 2770, 0), new RSTile(2796, 2770, 0), new RSTile(2797, 2770, 0), new RSTile(2798, 2770, 0), new RSTile(2799, 2770, 0), new RSTile(2800, 2770, 0), new RSTile(2801, 2770, 0), new RSTile(2803, 2770, 0), new RSTile(2804, 2770, 0), new RSTile(2804, 2769, 0), new RSTile(2805, 2768, 0), new RSTile(2806, 2767, 0), new RSTile(2806, 2766, 0), new RSTile(2806, 2764, 0), new RSTile(2806, 2763, 0), new RSTile(2806, 2762, 0), new RSTile(2807, 2762, 0)};
    public static final RSTile[] pathToCrates = new RSTile[]{new RSTile(2803, 2756, 0), new RSTile(2805, 2762, 0), new RSTile(2806, 2762, 0), new RSTile(2806, 2763, 0), new RSTile(2806, 2764, 0), new RSTile(2806, 2765, 0), new RSTile(2806, 2766, 0), new RSTile(2806, 2767, 0), new RSTile(2805, 2767, 0), new RSTile(2804, 2767, 0), new RSTile(2803, 2767, 0), new RSTile(2802, 2768, 0), new RSTile(2802, 2769, 0), new RSTile(2801, 2769, 0), new RSTile(2800, 2769, 0), new RSTile(2799, 2769, 0), new RSTile(2799, 2768, 0), new RSTile(2798, 2767, 0), new RSTile(2797, 2767, 0), new RSTile(2796, 2767, 0), new RSTile(2795, 2766, 0), new RSTile(2795, 2765, 0), new RSTile(2794, 2764, 0), new RSTile(2793, 2763, 0), new RSTile(2791, 2762, 0), new RSTile(2790, 2761, 0), new RSTile(2789, 2761, 0), new RSTile(2788, 2761, 0), new RSTile(2787, 2761, 0), new RSTile(2786, 2761, 0), new RSTile(2785, 2761, 0), new RSTile(2784, 2761, 0), new RSTile(2783, 2761, 0), new RSTile(2782, 2762, 0), new RSTile(2781, 2762, 0), new RSTile(2780, 2762, 0), new RSTile(2779, 2762, 0), new RSTile(2778, 2763, 0), new RSTile(2777, 2764, 0), new RSTile(2776, 2764, 0), new RSTile(2774, 2764, 0), new RSTile(2773, 2764, 0), new RSTile(2772, 2764, 0), new RSTile(2770, 2763, 0), new RSTile(2769, 2763, 0), new RSTile(2768, 2763, 0), new RSTile(2767, 2763, 0), new RSTile(2766, 2763, 0), new RSTile(2765, 2763, 0)};
    public static final RSTile[] PATH_TO_GET_CAPTURED = new RSTile[]{new RSTile(2726, 2732, 0), new RSTile(2726, 2733, 0), new RSTile(2726, 2734, 0), new RSTile(2726, 2735, 0), new RSTile(2726, 2736, 0), new RSTile(2726, 2738, 0), new RSTile(2726, 2739, 0), new RSTile(2726, 2740, 0), new RSTile(2726, 2741, 0), new RSTile(2726, 2743, 0), new RSTile(2725, 2745, 0), new RSTile(2724, 2747, 0), new RSTile(2723, 2749, 0), new RSTile(2723, 2750, 0)};
    public static final RSTile[] ESCAPE_PATH_PT1 = {new RSTile(2806, 2784, 0), new RSTile(2808, 2781, 0), new RSTile(2808, 2778, 0)};
    public static final RSTile[] ESCAPE_PATH_PT2 = {new RSTile(2808, 2778, 0), new RSTile(2805, 2776, 0), new RSTile(2803, 2777, 0)};
    public static final RSTile[] ESCAPE_PATH_PT2_TO_CHILD = {new RSTile(2803, 2777, 0), new RSTile(2797, 2779, 0), new RSTile(2792, 2783, 0), new RSTile(2789, 2785, 0), new RSTile(2785, 2786, 0), new RSTile(2784, 2792, 0), new RSTile(2782, 2796, 0), new RSTile(2780, 2800, 0), new RSTile(2778, 2803, 0), new RSTile(2777, 2804, 0), new RSTile(2773, 2804, 0), new RSTile(2770, 2805, 0), new RSTile(2768, 2806, 0), new RSTile(2765, 2806, 0), new RSTile(2759, 2804, 0), new RSTile(2753, 2803, 0), new RSTile(2746, 2802, 0)};
    public static final RSTile[] pathToGate = new RSTile[]{new RSTile(2799, 2703, 0), new RSTile(2797, 2703, 0), new RSTile(2795, 2703, 0), new RSTile(2793, 2703, 0), new RSTile(2791, 2704, 0), new RSTile(2789, 2704, 0), new RSTile(2787, 2704, 0), new RSTile(2784, 2704, 0), new RSTile(2784, 2706, 0), new RSTile(2782, 2706, 0), new RSTile(2780, 2706, 0), new RSTile(2778, 2706, 0), new RSTile(2776, 2707, 0), new RSTile(2774, 2709, 0), new RSTile(2772, 2710, 0), new RSTile(2770, 2710, 0), new RSTile(2768, 2711, 0), new RSTile(2766, 2713, 0), new RSTile(2763, 2714, 0), new RSTile(2761, 2714, 0), new RSTile(2758, 2713, 0), new RSTile(2756, 2715, 0), new RSTile(2754, 2715, 0), new RSTile(2752, 2715, 0), new RSTile(2750, 2717, 0), new RSTile(2748, 2720, 0), new RSTile(2747, 2722, 0), new RSTile(2745, 2722, 0), new RSTile(2743, 2722, 0), new RSTile(2741, 2723, 0), new RSTile(2739, 2723, 0), new RSTile(2737, 2723, 0), new RSTile(2735, 2725, 0), new RSTile(2734, 2727, 0), new RSTile(2732, 2728, 0), new RSTile(2731, 2730, 0), new RSTile(2730, 2732, 0), new RSTile(2728, 2733, 0), new RSTile(2726, 2734, 0), new RSTile(2726, 2736, 0), new RSTile(2726, 2738, 0), new RSTile(2726, 2740, 0), new RSTile(2726, 2743, 0), new RSTile(2725, 2745, 0), new RSTile(2724, 2747, 0), new RSTile(2724, 2749, 0), new RSTile(2724, 2751, 0), new RSTile(2724, 2753, 0), new RSTile(2723, 2755, 0), new RSTile(2721, 2757, 0), new RSTile(2720, 2759, 0), new RSTile(2720, 2761, 0), new RSTile(2720, 2763, 0), new RSTile(2721, 2765, 0)};
    public static final RSTile[] pathToElderGuard = new RSTile[]{new RSTile(2721, 2765, 0), new RSTile(2721, 2767, 0), new RSTile(2721, 2769, 0), new RSTile(2721, 2771, 0), new RSTile(2722, 2773, 0), new RSTile(2724, 2775, 0), new RSTile(2726, 2775, 0), new RSTile(2728, 2775, 0), new RSTile(2730, 2775, 0), new RSTile(2732, 2775, 0), new RSTile(2734, 2774, 0), new RSTile(2735, 2771, 0), new RSTile(2736, 2769, 0), new RSTile(2737, 2767, 0), new RSTile(2739, 2765, 0), new RSTile(2741, 2763, 0), new RSTile(2741, 2761, 0), new RSTile(2743, 2760, 0), new RSTile(2745, 2760, 0), new RSTile(2747, 2759, 0), new RSTile(2749, 2759, 0), new RSTile(2751, 2759, 0), new RSTile(2753, 2759, 0), new RSTile(2755, 2759, 0), new RSTile(2757, 2761, 0), new RSTile(2759, 2763, 0), new RSTile(2761, 2763, 0), new RSTile(2764, 2763, 0), new RSTile(2766, 2763, 0), new RSTile(2768, 2763, 0), new RSTile(2770, 2763, 0), new RSTile(2772, 2763, 0), new RSTile(2774, 2763, 0), new RSTile(2776, 2763, 0), new RSTile(2778, 2763, 0), new RSTile(2780, 2763, 0), new RSTile(2782, 2763, 0), new RSTile(2784, 2763, 0), new RSTile(2786, 2762, 0), new RSTile(2788, 2760, 0), new RSTile(2790, 2758, 0), new RSTile(2792, 2756, 0), new RSTile(2794, 2755, 0), new RSTile(2796, 2755, 0), new RSTile(2799, 2755, 0)};
    public static final RSTile[] pathToKrukPt1 = new RSTile[]{new RSTile(2803, 2757, 0), new RSTile(2801, 2756, 0), new RSTile(2799, 2756, 0), new RSTile(2797, 2756, 0), new RSTile(2795, 2756, 0), new RSTile(2793, 2756, 0), new RSTile(2791, 2756, 0), new RSTile(2789, 2756, 0), new RSTile(2787, 2757, 0), new RSTile(2785, 2759, 0), new RSTile(2783, 2761, 0), new RSTile(2781, 2762, 0), new RSTile(2778, 2762, 0), new RSTile(2776, 2762, 0), new RSTile(2774, 2762, 0), new RSTile(2772, 2762, 0), new RSTile(2770, 2762, 0), new RSTile(2768, 2762, 0), new RSTile(2766, 2762, 0), new RSTile(2764, 2762, 0), new RSTile(2762, 2763, 0), new RSTile(2759, 2763, 0), new RSTile(2757, 2763, 0), new RSTile(2755, 2763, 0), new RSTile(2753, 2761, 0), new RSTile(2751, 2760, 0), new RSTile(2749, 2759, 0), new RSTile(2747, 2759, 0), new RSTile(2745, 2759, 0), new RSTile(2743, 2759, 0), new RSTile(2741, 2761, 0), new RSTile(2741, 2763, 0), new RSTile(2741, 2765, 0), new RSTile(2739, 2767, 0), new RSTile(2737, 2769, 0), new RSTile(2735, 2771, 0), new RSTile(2733, 2773, 0), new RSTile(2732, 2775, 0), new RSTile(2730, 2775, 0), new RSTile(2728, 2775, 0), new RSTile(2726, 2775, 0), new RSTile(2724, 2775, 0), new RSTile(2722, 2774, 0), new RSTile(2720, 2774, 0), new RSTile(2717, 2774, 0), new RSTile(2715, 2774, 0), new RSTile(2713, 2775, 0), new RSTile(2711, 2775, 0), new RSTile(2710, 2773, 0), new RSTile(2708, 2771, 0), new RSTile(2706, 2769, 0), new RSTile(2704, 2767, 0), new RSTile(2702, 2764, 0), new RSTile(2700, 2763, 0), new RSTile(2698, 2761, 0), new RSTile(2698, 2759, 0), new RSTile(2697, 2757, 0), new RSTile(2697, 2755, 0), new RSTile(2695, 2753, 0), new RSTile(2695, 2750, 0), new RSTile(2695, 2748, 0), new RSTile(2697, 2748, 0), new RSTile(2699, 2748, 0), new RSTile(2701, 2748, 0), new RSTile(2703, 2746, 0), new RSTile(2705, 2744, 0), new RSTile(2707, 2743, 0), new RSTile(2709, 2743, 0), new RSTile(2711, 2745, 0), new RSTile(2711, 2747, 0), new RSTile(2712, 2749, 0), new RSTile(2712, 2751, 0), new RSTile(2712, 2753, 0), new RSTile(2712, 2755, 0), new RSTile(2712, 2757, 0), new RSTile(2712, 2759, 0), new RSTile(2712, 2761, 0), new RSTile(2711, 2763, 0), new RSTile(2711, 2765, 0)};
    public static final RSTile[] pathToKrukPt2 = new RSTile[]{new RSTile(2712, 2766, 2), new RSTile(2714, 2765, 2), new RSTile(2716, 2766, 2), new RSTile(2718, 2766, 2), new RSTile(2720, 2766, 2), new RSTile(2722, 2766, 2), new RSTile(2724, 2766, 2), new RSTile(2726, 2766, 2), new RSTile(2728, 2766, 2)};
    public static final RSTile[] pathToTreeGnomeGate = {new RSTile(2599, 3272, 0), new RSTile(2601, 3273, 0), new RSTile(2604, 3274, 0), new RSTile(2606, 3275, 0), new RSTile(2608, 3278, 0), new RSTile(2608, 3281, 0), new RSTile(2607, 3283, 0), new RSTile(2605, 3285, 0), new RSTile(2603, 3286, 0), new RSTile(2604, 3288, 0), new RSTile(2606, 3292, 0), new RSTile(2607, 3299, 0), new RSTile(2607, 3308, 0), new RSTile(2607, 3314, 0), new RSTile(2607, 3323, 0), new RSTile(2607, 3330, 0), new RSTile(2608, 3335, 0), new RSTile(2611, 3339, 0), new RSTile(2613, 3343, 0), new RSTile(2613, 3356, 0), new RSTile(2610, 3364, 0), new RSTile(2607, 3368, 0), new RSTile(2597, 3368, 0), new RSTile(2583, 3370, 0), new RSTile(2582, 3368, 0), new RSTile(2582, 3353, 0), new RSTile(2571, 3357, 0), new RSTile(2567, 3363, 0), new RSTile(2562, 3363, 0), new RSTile(2555, 3365, 0), new RSTile(2548, 3372, 0), new RSTile(2542, 3376, 0), new RSTile(2538, 3381, 0), new RSTile(2534, 3384, 0), new RSTile(2530, 3386, 0), new RSTile(2525, 3388, 0), new RSTile(2518, 3388, 0), new RSTile(2510, 3388, 0), new RSTile(2497, 3388, 0), new RSTile(2481, 3387, 0), new RSTile(2472, 3388, 0), new RSTile(2466, 3383, 0), new RSTile(2461, 3380, 0)};

}
