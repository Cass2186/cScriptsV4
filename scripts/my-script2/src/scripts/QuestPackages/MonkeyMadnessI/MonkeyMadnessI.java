package scripts.QuestPackages.MonkeyMadnessI;

import dax.api_lib.DaxWalker;
import dax.teleports.Teleport;
import dax.walker.utils.AccurateMouse;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.*;
import org.tribot.api2007.types.*;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Waiting;
import scripts.*;
import scripts.EntitySelector.Entities;
import scripts.EntitySelector.finders.prefabs.ItemEntity;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.DeathsOffice.DeathsOffice;
import scripts.QuestPackages.DruidicRitual.DruidicRitual;
import scripts.QuestSteps.BuyItemsStep;
import scripts.QuestSteps.QuestTask;
import scripts.Tasks.Priority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MonkeyMadnessI implements QuestTask {
    private static MonkeyMadnessI quest;

    public static MonkeyMadnessI get() {
        return quest == null ? quest = new MonkeyMadnessI() : quest;
    }


    public String strengthAndHp = "Focus on increasing strength and stamina...";
    public String attackAndDef = "Focus on improving attack and defence techniques...";

    int ZOOKNOCK_ID = 7193;
    int MONKEY_CORPSE = 3166;
    int narnodesOrders = 4005;
    int LOCKPICK = 1523;
    int stamina4 = 12625;
    int MONKFISH = 7946;
    int amuletmould = 4020;
    int monkeyDentures = 4006;
    int goldBar = 2357;
    int enchantedBar = 4007;
    int ballOfWool = 1759;
    int unfinishedAmulet = 4022;
    int mSpeakAmulet = 4021;
    int monkeyTalisman = 4023;
    int greegree = 4031;
    int MONKEY_BONES = 3183;
    int varrockTab = 8007;
    int glory4 = 1712;
    int lobster = 379;
    int[] ANTIDOTE_ID = {5952, 5954, 5956, 5958};
    int[] ANTIDOTE_PLUS_PLUS = {5952, 5954, 5956, 5956};
    int FOOD_IDS[] = {
            379, // lobster
            7946 // monkfish
    };
    int TENTH_SQUAD_SIGIL = 4035;


    final int[] STAMINA_POTION = {12625, 12627, 12629, 12631};
    final int[] PRAYER_POTION = {2434, 139, 141, 143};

    RSTile kingTile = new RSTile(2465, 3495, 0);
    RSTile compoundGate = new RSTile(2944, 3042, 0);
    RSTile GLOTile = new RSTile(2955, 3025, 0);
    RSTile daeroTile = new RSTile(2484, 3488, 1);
    RSTile gliderBaseCentre = new RSTile(2393, 9892, 0);
    RSTile outsideDungeon = new RSTile(2764, 2703, 0);
    RSTile zooknocktile = new RSTile(2805, 9145, 0);
    RSTile garkorTile = new RSTile(2806, 2760, 0);
    RSTile monkeyChildHidingTile = new RSTile(2746, 2802, 0);
    RSTile karamTile = new RSTile(2780, 2801, 0);
    RSTile preCaptureTile = new RSTile(2726, 2738, 0);
    RSTile inFrontOfFlames = new RSTile(2807, 9206, 0);
    RSTile apeAtollBeachTile = new RSTile(2799, 2703, 0);

    RSTile[] GARKOR_PATH = new RSTile[]{new RSTile(2780, 2798, 0), new RSTile(2781, 2798, 0), new RSTile(2782, 2798, 0), new RSTile(2783, 2796, 0), new RSTile(2783, 2795, 0), new RSTile(2783, 2794, 0), new RSTile(2783, 2793, 0), new RSTile(2783, 2792, 0), new RSTile(2783, 2791, 0), new RSTile(2783, 2790, 0), new RSTile(2783, 2789, 0), new RSTile(2783, 2788, 0), new RSTile(2783, 2787, 0), new RSTile(2783, 2786, 0), new RSTile(2783, 2785, 0), new RSTile(2783, 2783, 0), new RSTile(2783, 2782, 0), new RSTile(2783, 2780, 0), new RSTile(2783, 2779, 0), new RSTile(2783, 2778, 0), new RSTile(2784, 2777, 0), new RSTile(2785, 2775, 0), new RSTile(2786, 2774, 0), new RSTile(2787, 2774, 0), new RSTile(2788, 2773, 0), new RSTile(2789, 2772, 0), new RSTile(2790, 2771, 0), new RSTile(2791, 2770, 0), new RSTile(2792, 2770, 0), new RSTile(2793, 2770, 0), new RSTile(2794, 2770, 0), new RSTile(2795, 2770, 0), new RSTile(2796, 2770, 0), new RSTile(2797, 2770, 0), new RSTile(2798, 2770, 0), new RSTile(2799, 2770, 0), new RSTile(2800, 2770, 0), new RSTile(2801, 2770, 0), new RSTile(2803, 2770, 0), new RSTile(2804, 2770, 0), new RSTile(2804, 2769, 0), new RSTile(2805, 2768, 0), new RSTile(2806, 2767, 0), new RSTile(2806, 2766, 0), new RSTile(2806, 2764, 0), new RSTile(2806, 2763, 0), new RSTile(2806, 2762, 0), new RSTile(2807, 2762, 0)};
    RSTile[] pathToCrates = new RSTile[]{new RSTile(2803, 2756, 0), new RSTile(2805, 2762, 0), new RSTile(2806, 2762, 0), new RSTile(2806, 2763, 0), new RSTile(2806, 2764, 0), new RSTile(2806, 2765, 0), new RSTile(2806, 2766, 0), new RSTile(2806, 2767, 0), new RSTile(2805, 2767, 0), new RSTile(2804, 2767, 0), new RSTile(2803, 2767, 0), new RSTile(2802, 2768, 0), new RSTile(2802, 2769, 0), new RSTile(2801, 2769, 0), new RSTile(2800, 2769, 0), new RSTile(2799, 2769, 0), new RSTile(2799, 2768, 0), new RSTile(2798, 2767, 0), new RSTile(2797, 2767, 0), new RSTile(2796, 2767, 0), new RSTile(2795, 2766, 0), new RSTile(2795, 2765, 0), new RSTile(2794, 2764, 0), new RSTile(2793, 2763, 0), new RSTile(2791, 2762, 0), new RSTile(2790, 2761, 0), new RSTile(2789, 2761, 0), new RSTile(2788, 2761, 0), new RSTile(2787, 2761, 0), new RSTile(2786, 2761, 0), new RSTile(2785, 2761, 0), new RSTile(2784, 2761, 0), new RSTile(2783, 2761, 0), new RSTile(2782, 2762, 0), new RSTile(2781, 2762, 0), new RSTile(2780, 2762, 0), new RSTile(2779, 2762, 0), new RSTile(2778, 2763, 0), new RSTile(2777, 2764, 0), new RSTile(2776, 2764, 0), new RSTile(2774, 2764, 0), new RSTile(2773, 2764, 0), new RSTile(2772, 2764, 0), new RSTile(2770, 2763, 0), new RSTile(2769, 2763, 0), new RSTile(2768, 2763, 0), new RSTile(2767, 2763, 0), new RSTile(2766, 2763, 0), new RSTile(2765, 2763, 0)};


    RSArea GLIDER_BASE = new RSArea(gliderBaseCentre, 10);
    RSArea outsidePrison = new RSArea(new RSTile(2761, 2805, 0), 4);
    RSArea outsideMonkeyChildArea = new RSArea(new RSTile(2743, 2787, 0), 4);
    RSArea hidingArea = new RSArea(monkeyChildHidingTile, 1);
    RSArea monkeyCage = new RSArea(new RSTile(2599, 3282, 0), new RSTile(2606, 3276, 0));
    RSArea beachArea = new RSArea(apeAtollBeachTile, 10);
    RSArea aowowgeiAreaOutside = new RSArea(new RSTile(2801, 2755, 0), 4);
    RSArea CAPTURE_AREA = new RSArea(new RSTile(2719, 2758, 0), new RSTile(2723, 2754, 0));
    RSArea KARAM_AREA = new RSArea(new RSTile(2784, 2796, 0), new RSTile(2777, 2808, 0));
    RSArea gloughUnder = new RSArea(new RSTile(2474, 3464, 0), new RSTile(2479, 3461, 0));
    RSArea gloughArea = new RSArea(new RSTile(2475, 3465, 1), new RSTile(2484, 3462, 1));
    RSArea HANGAR = new RSArea(new RSTile(2383, 9903, 0), new RSTile(2401, 9874, 0));
    RSArea ISLAND_AREA = new RSArea(new RSTile(2719, 2803, 0), new RSTile(2813, 2692, 0)); // whole Island
    RSArea GARKOR_AREA = new RSArea(new RSTile(2805, 2762, 0), new RSTile(2808, 2756, 0));
    RSArea LARGE_SHOP_AREA = new RSArea(new RSTile(2757, 2766, 0), new RSTile(2758, 2772, 0));
    RSArea SMALL_SHOP_AREA = new RSArea(new RSTile(2757, 2767, 0), new RSTile(2758, 2770, 0));
    RSArea DENTURES_BUILDING_AREA = new RSArea(new RSTile(2770, 2764, 0), new RSTile(2759, 2772, 0));


    RSNPC[] daero = NPCs.findNearest("Daero");
    RSNPC[] zooknock = NPCs.findNearest("Zooknock");
    RSNPC[] aberab = NPCs.findNearest("Aberab");
    RSNPC[] trefaji = NPCs.findNearest("Trefaji");
    RSNPC[] theMonkeysAunt = NPCs.findNearest("The Monkey's Aunt");
    RSNPC[] monkeyChild = NPCs.findNearest("Monkey Child");
    RSNPC[] elderGuard = NPCs.findNearest("Elder Guard");

    RSObject[] gate = Objects.findNearest(20, "Gate");
    RSObject[] crate = Objects.findNearest(10, "Crate");
    RSObject[] jailDoor = Objects.findNearest(10, "Jail door");
    RSItem[] invAmuletMould = Inventory.find(amuletmould);


    boolean PAID_GLOUGH = false;


    String[] DAERO_CHAT = {
            "Talk about the journey...",
            "Talk about the 10th squad...",
            "Talk about Caranock...",
            "Leave...",
            "Who is it?",
            "<col=0000ff>Return to previous menu.", // may not have a period
    };

    // I have no idea why i did it like this, but it works
    public ArrayList<String>
            DAERO_CHAT_1 = new ArrayList<String>(Arrays.asList("Talk about the journey...",
            "What lies to the south of Karamja?")),
            DAERO_CHAT_2 = new ArrayList<String>(Arrays.asList("How will I travel?")),
            DAERO_CHAT_3 = new ArrayList<String>(Arrays.asList("Are you coming with me?")),
            DAERO_CHAT_4 = new ArrayList<String>(Arrays.asList("Talk about the 10th squad...",
                    "Why did the king send a squad of the Royal Guard?")),
            DAERO_CHAT_5 = new ArrayList<String>(List.of("Who is Garkor?")),
            DAERO_CHAT_6 = new ArrayList<String>(List.of("Why are the 10th squad so famous?")),
            DAERO_CHAT_7 = new ArrayList<String>(Arrays.asList("Talk about Caranock...")),
            DAERO_CHAT_11 = new ArrayList<String>(Arrays.asList("Leave...")),
            DAERO_CHAT_12 = new ArrayList<String>(Arrays.asList("Who is it?")),
            DAERO_CHAT_13 = new ArrayList<String>(Arrays.asList("<col=0000ff>Return to previous menu"));


    RSTile[] PATH_TO_GET_CAPTURED = new RSTile[]{new RSTile(2726, 2732, 0), new RSTile(2726, 2733, 0), new RSTile(2726, 2734, 0), new RSTile(2726, 2735, 0), new RSTile(2726, 2736, 0), new RSTile(2726, 2738, 0), new RSTile(2726, 2739, 0), new RSTile(2726, 2740, 0), new RSTile(2726, 2741, 0), new RSTile(2726, 2743, 0), new RSTile(2725, 2745, 0), new RSTile(2724, 2747, 0), new RSTile(2723, 2749, 0), new RSTile(2723, 2750, 0)};
    ArrayList<RSTile> PATH_TO_GET_CAPTURED_LIST = new ArrayList<RSTile>(Arrays.asList(PATH_TO_GET_CAPTURED));

    RSTile[] ESCAPE_PATH_PT1 = {new RSTile(2806, 2784, 0), new RSTile(2808, 2781, 0), new RSTile(2808, 2778, 0)};
    RSTile[] ESCAPE_PATH_PT2 = {new RSTile(2808, 2778, 0), new RSTile(2805, 2776, 0), new RSTile(2803, 2777, 0)};
    RSTile[] ESCAPE_PATH_PT2_TO_CHILD = {new RSTile(2803, 2777, 0), new RSTile(2797, 2779, 0), new RSTile(2792, 2783, 0), new RSTile(2789, 2785, 0), new RSTile(2785, 2786, 0), new RSTile(2784, 2792, 0), new RSTile(2782, 2796, 0), new RSTile(2780, 2800, 0), new RSTile(2778, 2803, 0), new RSTile(2777, 2804, 0), new RSTile(2773, 2804, 0), new RSTile(2770, 2805, 0), new RSTile(2768, 2806, 0), new RSTile(2765, 2806, 0), new RSTile(2759, 2804, 0), new RSTile(2753, 2803, 0), new RSTile(2746, 2802, 0)};
    RSArea SOUTH_MELEE_SAFE_AREA = new RSArea(new RSTile(2803, 2781, 0), new RSTile(2809, 2775, 0));
    RSTile[] pathToGate = new RSTile[]{new RSTile(2799, 2703, 0), new RSTile(2797, 2703, 0), new RSTile(2795, 2703, 0), new RSTile(2793, 2703, 0), new RSTile(2791, 2704, 0), new RSTile(2789, 2704, 0), new RSTile(2787, 2704, 0), new RSTile(2784, 2704, 0), new RSTile(2784, 2706, 0), new RSTile(2782, 2706, 0), new RSTile(2780, 2706, 0), new RSTile(2778, 2706, 0), new RSTile(2776, 2707, 0), new RSTile(2774, 2709, 0), new RSTile(2772, 2710, 0), new RSTile(2770, 2710, 0), new RSTile(2768, 2711, 0), new RSTile(2766, 2713, 0), new RSTile(2763, 2714, 0), new RSTile(2761, 2714, 0), new RSTile(2758, 2713, 0), new RSTile(2756, 2715, 0), new RSTile(2754, 2715, 0), new RSTile(2752, 2715, 0), new RSTile(2750, 2717, 0), new RSTile(2748, 2720, 0), new RSTile(2747, 2722, 0), new RSTile(2745, 2722, 0), new RSTile(2743, 2722, 0), new RSTile(2741, 2723, 0), new RSTile(2739, 2723, 0), new RSTile(2737, 2723, 0), new RSTile(2735, 2725, 0), new RSTile(2734, 2727, 0), new RSTile(2732, 2728, 0), new RSTile(2731, 2730, 0), new RSTile(2730, 2732, 0), new RSTile(2728, 2733, 0), new RSTile(2726, 2734, 0), new RSTile(2726, 2736, 0), new RSTile(2726, 2738, 0), new RSTile(2726, 2740, 0), new RSTile(2726, 2743, 0), new RSTile(2725, 2745, 0), new RSTile(2724, 2747, 0), new RSTile(2724, 2749, 0), new RSTile(2724, 2751, 0), new RSTile(2724, 2753, 0), new RSTile(2723, 2755, 0), new RSTile(2721, 2757, 0), new RSTile(2720, 2759, 0), new RSTile(2720, 2761, 0), new RSTile(2720, 2763, 0), new RSTile(2721, 2765, 0)};
    RSTile[] pathToElderGuard = new RSTile[]{new RSTile(2721, 2765, 0), new RSTile(2721, 2767, 0), new RSTile(2721, 2769, 0), new RSTile(2721, 2771, 0), new RSTile(2722, 2773, 0), new RSTile(2724, 2775, 0), new RSTile(2726, 2775, 0), new RSTile(2728, 2775, 0), new RSTile(2730, 2775, 0), new RSTile(2732, 2775, 0), new RSTile(2734, 2774, 0), new RSTile(2735, 2771, 0), new RSTile(2736, 2769, 0), new RSTile(2737, 2767, 0), new RSTile(2739, 2765, 0), new RSTile(2741, 2763, 0), new RSTile(2741, 2761, 0), new RSTile(2743, 2760, 0), new RSTile(2745, 2760, 0), new RSTile(2747, 2759, 0), new RSTile(2749, 2759, 0), new RSTile(2751, 2759, 0), new RSTile(2753, 2759, 0), new RSTile(2755, 2759, 0), new RSTile(2757, 2761, 0), new RSTile(2759, 2763, 0), new RSTile(2761, 2763, 0), new RSTile(2764, 2763, 0), new RSTile(2766, 2763, 0), new RSTile(2768, 2763, 0), new RSTile(2770, 2763, 0), new RSTile(2772, 2763, 0), new RSTile(2774, 2763, 0), new RSTile(2776, 2763, 0), new RSTile(2778, 2763, 0), new RSTile(2780, 2763, 0), new RSTile(2782, 2763, 0), new RSTile(2784, 2763, 0), new RSTile(2786, 2762, 0), new RSTile(2788, 2760, 0), new RSTile(2790, 2758, 0), new RSTile(2792, 2756, 0), new RSTile(2794, 2755, 0), new RSTile(2796, 2755, 0), new RSTile(2799, 2755, 0)};
    RSTile[] pathToKrukPt1 = new RSTile[]{new RSTile(2803, 2757, 0), new RSTile(2801, 2756, 0), new RSTile(2799, 2756, 0), new RSTile(2797, 2756, 0), new RSTile(2795, 2756, 0), new RSTile(2793, 2756, 0), new RSTile(2791, 2756, 0), new RSTile(2789, 2756, 0), new RSTile(2787, 2757, 0), new RSTile(2785, 2759, 0), new RSTile(2783, 2761, 0), new RSTile(2781, 2762, 0), new RSTile(2778, 2762, 0), new RSTile(2776, 2762, 0), new RSTile(2774, 2762, 0), new RSTile(2772, 2762, 0), new RSTile(2770, 2762, 0), new RSTile(2768, 2762, 0), new RSTile(2766, 2762, 0), new RSTile(2764, 2762, 0), new RSTile(2762, 2763, 0), new RSTile(2759, 2763, 0), new RSTile(2757, 2763, 0), new RSTile(2755, 2763, 0), new RSTile(2753, 2761, 0), new RSTile(2751, 2760, 0), new RSTile(2749, 2759, 0), new RSTile(2747, 2759, 0), new RSTile(2745, 2759, 0), new RSTile(2743, 2759, 0), new RSTile(2741, 2761, 0), new RSTile(2741, 2763, 0), new RSTile(2741, 2765, 0), new RSTile(2739, 2767, 0), new RSTile(2737, 2769, 0), new RSTile(2735, 2771, 0), new RSTile(2733, 2773, 0), new RSTile(2732, 2775, 0), new RSTile(2730, 2775, 0), new RSTile(2728, 2775, 0), new RSTile(2726, 2775, 0), new RSTile(2724, 2775, 0), new RSTile(2722, 2774, 0), new RSTile(2720, 2774, 0), new RSTile(2717, 2774, 0), new RSTile(2715, 2774, 0), new RSTile(2713, 2775, 0), new RSTile(2711, 2775, 0), new RSTile(2710, 2773, 0), new RSTile(2708, 2771, 0), new RSTile(2706, 2769, 0), new RSTile(2704, 2767, 0), new RSTile(2702, 2764, 0), new RSTile(2700, 2763, 0), new RSTile(2698, 2761, 0), new RSTile(2698, 2759, 0), new RSTile(2697, 2757, 0), new RSTile(2697, 2755, 0), new RSTile(2695, 2753, 0), new RSTile(2695, 2750, 0), new RSTile(2695, 2748, 0), new RSTile(2697, 2748, 0), new RSTile(2699, 2748, 0), new RSTile(2701, 2748, 0), new RSTile(2703, 2746, 0), new RSTile(2705, 2744, 0), new RSTile(2707, 2743, 0), new RSTile(2709, 2743, 0), new RSTile(2711, 2745, 0), new RSTile(2711, 2747, 0), new RSTile(2712, 2749, 0), new RSTile(2712, 2751, 0), new RSTile(2712, 2753, 0), new RSTile(2712, 2755, 0), new RSTile(2712, 2757, 0), new RSTile(2712, 2759, 0), new RSTile(2712, 2761, 0), new RSTile(2711, 2763, 0), new RSTile(2711, 2765, 0)};
    RSTile[] pathToKrukPt2 = new RSTile[]{new RSTile(2712, 2766, 2), new RSTile(2714, 2765, 2), new RSTile(2716, 2766, 2), new RSTile(2718, 2766, 2), new RSTile(2720, 2766, 2), new RSTile(2722, 2766, 2), new RSTile(2724, 2766, 2), new RSTile(2726, 2766, 2), new RSTile(2728, 2766, 2)};
    RSTile[] pathToTreeGnomeGate = {
            new RSTile(2599, 3272, 0),
            new RSTile(2601, 3273, 0),
            new RSTile(2604, 3274, 0),
            new RSTile(2606, 3275, 0),
            new RSTile(2608, 3278, 0),
            new RSTile(2608, 3281, 0),
            new RSTile(2607, 3283, 0),
            new RSTile(2605, 3285, 0),
            new RSTile(2603, 3286, 0),
            new RSTile(2604, 3288, 0),
            new RSTile(2606, 3292, 0), new RSTile(2607, 3299, 0), new RSTile(2607, 3308, 0), new RSTile(2607, 3314, 0), new RSTile(2607, 3323, 0), new RSTile(2607, 3330, 0), new RSTile(2608, 3335, 0), new RSTile(2611, 3339, 0), new RSTile(2613, 3343, 0), new RSTile(2613, 3356, 0), new RSTile(2610, 3364, 0), new RSTile(2607, 3368, 0), new RSTile(2597, 3368, 0), new RSTile(2583, 3370, 0), new RSTile(2582, 3368, 0), new RSTile(2582, 3353, 0), new RSTile(2571, 3357, 0), new RSTile(2567, 3363, 0), new RSTile(2562, 3363, 0), new RSTile(2555, 3365, 0), new RSTile(2548, 3372, 0), new RSTile(2542, 3376, 0), new RSTile(2538, 3381, 0), new RSTile(2534, 3384, 0), new RSTile(2530, 3386, 0), new RSTile(2525, 3388, 0), new RSTile(2518, 3388, 0), new RSTile(2510, 3388, 0), new RSTile(2497, 3388, 0), new RSTile(2481, 3387, 0), new RSTile(2472, 3388, 0), new RSTile(2466, 3383, 0), new RSTile(2461, 3380, 0)
    };

    /**
     * AREAS FOR NAGIVATING TO GET M'SPEAK AMULET
     */
    RSArea ABOVE_LADDER_AREA = new RSArea(new RSTile(2807, 2783, 0), new RSTile(2806, 2787, 0));
    RSArea LOWER_LADDER_ABOVE_AREA = new RSArea(new RSTile(2807, 2784, 0), new RSTile(2805, 2782, 0));
    RSTile[] LOWER_ESCAPE_PATH = {
            new RSTile(2806, 2783, 0),
            new RSTile(2808, 2781, 0),
            new RSTile(2808, 2778, 0),
            new RSTile(2806, 2776, 0),
            new RSTile(2804, 2776, 0),
            new RSTile(2801, 2778, 0),
            new RSTile(2795, 2782, 0),
            new RSTile(2789, 2785, 0),
            new RSTile(2785, 2789, 0),
            new RSTile(2781, 2798, 0)
    };

    //large area outside the monkey temple. Do not walk to a random tile here or you might die
    RSArea AFTER_PATH_1_AREA = new RSArea(
            new RSTile[]{
                    new RSTile(2789, 2783, 0),
                    new RSTile(2783, 2783, 0),
                    new RSTile(2777, 2789, 0),
                    new RSTile(2777, 2803, 0),
                    new RSTile(2770, 2803, 0),
                    new RSTile(2770, 2805, 0),
                    new RSTile(2763, 2805, 0),
                    new RSTile(2763, 2810, 0),
                    new RSTile(2789, 2810, 0)
            }
    );

    //entirety of the upstairs area
    RSArea UPSTAIRS_OF_TEMPLE_AREA = new RSArea(new RSTile(2810, 2770, 1), new RSTile(2785, 2800, 1));
    RSTile LADDER_DOWN_TILE = new RSTile(2806, 2785, 1); //climb down from here
    RSTile INITIAL_PLANE_1_TILE = new RSTile(2792, 2773, 1); // after you climb up southern ladder
    RSTile LANDING_TILE = new RSTile(2806, 2785, 0);

    //****** for the melee monkeys cont'd (ORIGINAL) ****///
    RSArea MONKEY_TEMPLE = new RSArea(new RSTile(2810, 2775, 0), new RSTile(2784, 2797, 0));
    // RSArea BLOCKING_AREA = new RSArea(new RSTile(2806, 2788, 0), new RSTile(2808, 2786, 0));
    RSArea BLOCKING_AREA = new RSArea(new RSTile[]{new RSTile(2810, 2789, 0), new RSTile(2807, 2789, 0), new RSTile(2807, 2786, 0), new RSTile(2806, 2786, 0), new RSTile(2806, 2784, 0), new RSTile(2808, 2784, 0), new RSTile(2808, 2787, 0)});
    RSArea MELEE_SAFE_AREA = new RSArea(new RSTile(2804, 2794, 0), new RSTile(2809, 2789, 0));
    RSArea AREA_TO_START_MELEE_PRAY = new RSArea(new RSTile(2779, 2798, 0), new RSTile(2782, 2796, 0));
    RSArea UNDERGROUND_FLAME_AREA = new RSArea(inFrontOfFlames, 25);


    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.BALL_OF_WOOL, 1, 500),
                    new GEItem(ItemID.MONKS_ROBE_BOTTOM, 1, 500),
                    new GEItem(ItemID.MONKS_ROBE_TOP, 1, 500),
                    new GEItem(ItemID.VARROCK_TELEPORT, 10, 50),
                    new GEItem(ItemID.CAMELOT_TELEPORT, 10, 50),
                    new GEItem(ItemID.FALADOR_TELEPORT, 10, 50),
                    new GEItem(ItemID.LOCKPICK, 1, 300),
                    new GEItem(ItemID.SKILLS_NECKLACE[0], 1, 50),
                    new GEItem(ItemID.LOBSTER, 35, 50),
                    new GEItem(ItemID.MONKFISH, 15, 50),
                    new GEItem(ItemID.GAMES_NECKLACE[0], 1, 50),
                    new GEItem(ItemID.GOLD_BAR, 1, 50),
                    new GEItem(ItemID.RING_OF_DUELING[0], 3, 50),
                    new GEItem(ItemID.PRAYER_POTION[0], 6, 20),
                    new GEItem(ItemID.ANTIDOTE_PLUS_PLUS[0], 5, 40),
                    new GEItem(ItemID.STAMINA_POTION[0], 6, 20),
                    new GEItem(ItemID.RING_OF_WEALTH[0], 1, 20),
                    new GEItem(ItemID.ADAMANT_KITESHIELD, 1, 50),
                    new GEItem(ItemID.ADAMANT_FULL_HELM, 1, 50)
            )
    );

    BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);


    public void buyItems() {
        //TODO Check we don't already have inventroy with items (i.e. so if it loops)
        cQuesterV2.status = "Buying Items";
        General.println("[Debug]: " + cQuesterV2.status);
        buyStep.buyItems();
    }

    public void talkToKing() {
        cQuesterV2.status = "Going to king";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToTile(kingTile);

        cQuesterV2.status = "Talking to king";
        General.println("[Debug]: " + cQuesterV2.status);
        if (NpcChat.talkToNPC("King Narnode Shareen")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            NPCInteraction.handleConversation("Yes");
            NPCInteraction.handleConversation();
            General.sleep(General.random(1000, 2000));
            //closes unique interface that opens for each chapter of the quest
            RSInterface chpInter = Interfaces.get(225, 14);
            if (chpInter != null)
                chpInter.click();
        }
    }

    public void getMonkeyBones() {
        if (Inventory.find("Monkey bones").length < 1) {
            cQuesterV2.status = "Getting Monkey bones";
            General.println("[Debug]: " + cQuesterV2.status);
            RSNPC[] monkey = NPCs.find("Monkey");
            if (monkey.length > 0 && CombatUtil.clickTarget(monkey[0])) {
                if (Timer.waitCondition(Combat::isUnderAttack, 7000, 10000))
                    Timer.waitCondition(() -> GroundItems.find(MONKEY_CORPSE, MONKEY_BONES).length > 0, 25000, 40000);
            }
            RSGroundItem[] bones = GroundItems.find(MONKEY_CORPSE, MONKEY_BONES);
            if (bones.length > 0 && AccurateMouse.click(bones[0], "Take"))
                Timer.waitCondition(() -> Inventory.find(MONKEY_CORPSE, MONKEY_BONES).length > 0, 9000, 15000);

        }
    }

    public void getStartItems() {
        cQuesterV2.status = "Getting initial items";
        General.println("[Debug]: " + cQuesterV2.status);
        BankManager.open(true);
        BankManager.checkEquippedGlory();
        BankManager.depositAll(true);
        BankManager.withdraw(1, true, ANTIDOTE_PLUS_PLUS[0]);
        BankManager.withdraw(1, true, ItemID.STAMINA_POTION[0]);
        BankManager.withdraw(2, true, ItemID.RING_OF_DUELING[0]);
        BankManager.withdraw(4, true, MONKFISH);
        BankManager.close(true);
        Utils.equipItem(ItemID.RING_OF_DUELING[0]);
        if (Prayer.getPrayerPoints() < 30) {
            Utils.clanWarsReset();
        }
    }

    RSArea INSIDE_SHIPYARD_GATE = new RSArea(
            new RSTile[]{
                    new RSTile(2947, 3049, 0),
                    new RSTile(2945, 3047, 0),
                    new RSTile(2945, 3034, 0),
                    new RSTile(2946, 3033, 0),
                    new RSTile(2946, 3030, 0),
                    new RSTile(2948, 3028, 0),
                    new RSTile(2948, 3022, 0),
                    new RSTile(2953, 3020, 0),
                    new RSTile(2960, 3020, 0),
                    new RSTile(2965, 3027, 0),
                    new RSTile(2953, 3049, 0)
            }
    );

    public void goToGloCarancock() {
        if (Inventory.find(MONKEY_CORPSE, MONKEY_BONES).length == 0) {
            PathingUtil.walkToTile(new RSTile(2955, 3002, 0));
            getMonkeyBones();
        }

        cQuesterV2.status = "Step 2 - Going to GLO Caranock.";
        General.println("[Debug]: Going to GLO Caranock.");
        if (Inventory.find(MONKEY_CORPSE, MONKEY_BONES).length > 0) {
            if (!INSIDE_SHIPYARD_GATE.contains(Player.getPosition())) {
                PathingUtil.walkToTile(compoundGate);
                if (Utils.clickObj("Gate", "Open")) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                }

                if (!INSIDE_SHIPYARD_GATE.contains(Player.getPosition()) &&
                        Utils.clickObj("Gate", "Open")) {
                    if (NPCInteraction.waitForConversationWindow())
                        NPCInteraction.handleConversation();
                    Timer.waitCondition(() -> INSIDE_SHIPYARD_GATE.contains(Player.getPosition()), 4500, 6000);
                }


            }
            if (INSIDE_SHIPYARD_GATE.contains(Player.getPosition()))
                PathingUtil.localNavigation(GLOTile);
        }
    }

    public void talkingToCaranock() {
        General.println("[Debug]: Step 2- Talking to GLO Caranock.");
        if (NpcChat.talkToNPC("G.L.O. Caranock")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            NPCInteraction.handleConversation();
        }
    }

    public void step4Part2Banking() {
        cQuesterV2.status = "Banking";
        General.println("[Debug]: Banking.");
        BankManager.open(true);
        BankManager.checkEquippedGlory();
        BankManager.depositAll(true);
        BankManager.withdraw(2, true, ItemID.ANTIDOTE_PLUS_PLUS);
        BankManager.withdraw(1, true, LOCKPICK);
        BankManager.withdraw(2, true, stamina4);
        BankManager.withdraw(12, true, lobster);
        BankManager.withdraw(1, true, narnodesOrders);
        BankManager.withdraw(2, true, varrockTab);
        BankManager.withdraw(200000, true, 995);
        BankManager.close(true);
    }

    public void step5Part2TalkToDaero() {
        cQuesterV2.status = "Step 5 - Going to Daero";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToTile(daeroTile);
        if (NpcChat.talkToNPC("Daero")) {
            NPCInteraction.waitForConversationWindow();
            chat();
        }
    }

    public void chat() {
        if (NPCInteraction.isConversationWindowUp()) {
            Waiting.waitUniform(2000, 3500);
            NPCInteraction.handleConversation(DAERO_CHAT[0]);
            Timer.waitCondition((() -> Interfaces.isInterfaceSubstantiated(219, 1, 4)), 5000, 7000); // talk about the journey
            if (Interfaces.get(219, 1, 4) != null) {
                General.println("[Debug]: Return to previous menu");
                if (Interfaces.get(219, 1, 4).click())
                    Timer.waitCondition((() -> !Interfaces.isInterfaceSubstantiated(219, 1, 4)), 5000, 7000); // talk about the journey
            }

            NPCInteraction.handleConversation(DAERO_CHAT[1]);
            Timer.waitCondition((() -> Interfaces.isInterfaceSubstantiated(219, 1, 4)), 5000, 7000); // talk about the journey
            if (Interfaces.get(219, 1, 4) != null) {
                General.println("[Debug]: Return to previous menu");
                if (Interfaces.get(219, 1, 4).click())
                    Timer.waitCondition((() -> !Interfaces.isInterfaceSubstantiated(219, 1, 4)), 5000, 7000); // talk about the journey
            }
            NPCInteraction.handleConversation(DAERO_CHAT[2]);
            Timer.waitCondition((() -> Interfaces.isInterfaceSubstantiated(219, 1, 4)), 5000, 7000); // talk about the journey
            if (Interfaces.get(219, 1, 4) != null) {
                General.println("[Debug]: Return to previous menu");
                if (Interfaces.get(219, 1, 4).click())
                    Timer.waitCondition((() -> !Interfaces.isInterfaceSubstantiated(219, 1, 4)), 5000, 7000); // talk about the journey
            }
            if (Interfaces.get(219, 1, 4) != null) {
                if (Interfaces.get(219, 1, 4).getText().contains("Leave")) {
                    Interfaces.get(219, 1, 4).click();
                    General.sleep(General.random(900, 2000));
                    NPCInteraction.handleConversation(DAERO_CHAT_12.toArray(new String[DAERO_CHAT_12.size()]));
                    NPCInteraction.handleConversation();
                    Timer.waitCondition(() -> GLIDER_BASE.contains(Player.getPosition()), 8000, 12000);
                    return;
                }
            }
            NPCInteraction.handleConversation();
            if (Interfaces.get(219, 1, 4) != null) {
                if (Interfaces.get(219, 1, 4).getText().contains("Leave")) {
                    Interfaces.get(219, 1, 4).click();
                    General.sleep(General.random(800, 2000));
                    NPCInteraction.handleConversation(DAERO_CHAT[4]);
                    NPCInteraction.handleConversation();
                    Timer.waitCondition(() -> GLIDER_BASE.contains(Player.getPosition()), 8000, 12000);
                    return;
                }
            }
            if (Interfaces.get(219, 1, 4) != null) {
                if (Interfaces.get(219, 1, 4).getText().contains("Leave")) {
                    Interfaces.get(219, 1, 4).click();
                    General.sleep(General.random(900, 2000));
                    NPCInteraction.handleConversation(DAERO_CHAT_12.toArray(new String[DAERO_CHAT_12.size()]));
                    NPCInteraction.handleConversation();
                    Timer.waitCondition(() -> GLIDER_BASE.contains(Player.getPosition()), 8000, 12000);
                    General.sleep(General.random(1000, 3000));
                }
            }
        }
    }

    public void step6Part2() {
        cQuesterV2.status = "Step 6a: Talking to Daero";
        General.println("[Debug]: " + cQuesterV2.status);
        General.sleep(General.random(1000, 3000));
        if (NpcChat.talkToNPC("Daero")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }

        cQuesterV2.status = "Step 6b: Talking to Waydar";
        if (NpcChat.talkToNPC("Waydar")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            // NPCInteraction.handleConversation();
        }
        cQuesterV2.status = "Step 6c: Talking to Daero";
        if (NpcChat.talkToNPC("Daero")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            //  NPCInteraction.handleConversation();
            Utils.continuingChat();
        }
    }


    /**
     * if Hangar is still instanced, use this
     */

    RSArea ISLAND_ARAE = new RSArea(new RSTile(2895, 2726, 0), 10);

    public void goToHangarNoDax() {
        cQuesterV2.status = "Going to Hagar";
        General.println("[Debug]: " + cQuesterV2.status);

        daero = NPCs.findNearest("Daero");
        RSNPC[] waydar = NPCs.findNearest("Waydar");
        if (daero.length == 0 && waydar.length < 1 && !ISLAND_ARAE.contains(Player.getPosition()))
            PathingUtil.walkToTile(daeroTile);

        else
            return;

        daero = NPCs.findNearest("Daero");
        if (daero.length > 0) {
            if (AccurateMouse.click(daero[0], "Travel")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                Timer.waitCondition(() -> HANGAR.contains(Player.getPosition()), 18000, 24000);
                General.sleep(General.random(1000, 5000));
            }

        }
    }

    public void solvePuzzle() {

        RSObject[] puzzle = Objects.findNearest(30, 4871);
        if (puzzle.length < 1) {
            goToHangarNoDax();
        }
        PathingUtil.walkToTile(new RSTile(2393, 9882));
        if (puzzle.length > 0) {

            if (!puzzle[0].isClickable())
                puzzle[0].adjustCameraTo();
        }
        if (Utils.clickObj(4871, "Operate")) {
            Timer.waitCondition(() -> Interfaces.get(306, 4, 24) != null, 8000, 12000);
            General.sleep(General.random(1000, 5000));
        }

        if (Interfaces.get(306, 4, 24) != null) {
            if (Interfaces.get(306, 4, 24).click()) {
                Utils.longSleep();
                Utils.cutScene();
                Timer.waitCondition(() -> Game.getSetting(1021) == 0, 6000, 9000);
                General.sleep(General.random(1000, 5000));
            }
        }


    }

    public void talkToDaeroAndWaydar() {
        RSNPC[] daero = NPCs.find("Daero");
        RSNPC[] waydar = NPCs.find("Waydar");

        if (daero.length < 1 && waydar.length == 0)
            goToHangarNoDax();

        Log.log("[Deubug]: Step 7- Talking to Daero");
        if (NpcChat.talkToNPC("Daero")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }

        Log.log("[Deubug]: Step 7- Talking to Waydar");
        if (NpcChat.talkToNPC("Waydar")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Yes");
            NPCInteraction.handleConversation();
            if (Timer.waitCondition(() -> NPCs.find(2022).length > 0, 10000, 15000))
                Waiting.waitUniform(2000, 3500);
        }
    }

    public void talkToLumbdo() {
        RSNPC[] lumbdo = NPCs.find(2022);
        if (lumbdo.length < 1) {
            General.println("[Debug]: Executing beach failsafe, going to hangar.");
            goToHangarNoDax();
            talkToDaeroAndWaydar();
        }
        cQuesterV2.status = "Step 8 - Talking to Lumbdo";
        General.println("[Debug]: " + cQuesterV2.status);
        if (NpcChat.talkToNPC("Lumdo")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }
    }


    public void goToGlough() {
        if (!PAID_GLOUGH) {
            cQuesterV2.status = "Step 8 - Going to Glough";
            Log.log("[Debug]: Step 7- Going to Glough");

            if (Inventory.find(995).length < 1) {
                step4Part2Banking();
            }

            if (!gloughArea.contains(Player.getPosition())) {
                PathingUtil.walkToArea(gloughUnder);
                if (Utils.clickObj("Ladder", "Climb-up")) {
                    Timer.waitCondition(() -> gloughArea.contains(Player.getPosition()), 7000, 9000);
                    Utils.modSleep();
                }
            }
            if (gloughArea.contains(Player.getPosition())) {
                if (NpcChat.talkToNPC("Glough")) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation("Yes, I suppose I do.");
                    NPCInteraction.handleConversation("Ok then. You win again, Glough.");
                    if (NPCInteraction.isConversationWindowUp())
                        PAID_GLOUGH = true; // to prevent errors in script causing a loop and paying >1x
                    NPCInteraction.handleConversation();
                }
                if (Utils.clickObj("Ladder", "Climb-down"))
                    Timer.abc2WaitCondition(() -> !gloughArea.contains(Player.getPosition()), 9000, 12000);
            }
        }
    }

    public void step9Part2TalkToWaydar() {
        cQuesterV2.status = "Step 9 - Talking to Waydar";
        General.println("[Debug]: " + cQuesterV2.status);
        if (NpcChat.talkToNPC("Waydar")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("I cannot convince Lumdo to take us to the island...");
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }
        if (NpcChat.talkToNPC("Waydar")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("I cannot convince Lumdo to take us to the island...");
            NPCInteraction.handleConversation();
            Waiting.waitUniform(4000, 6000);
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            NPCInteraction.handleConversation();
            Waiting.waitUniform(4000, 6000);
        }
    }

    RSArea ISLAND_OUTSIDE_OF_JAIL = new RSArea(new RSTile(2811, 2749, 0), new RSTile(2728, 2809, 0));

    public void getCaptured() {
        if (!wholeJailArea.contains(Player.getPosition()) &&
                !ISLAND_OUTSIDE_OF_JAIL.contains(Player.getPosition()) &&
                !MMConst.WHOLE_ISLAND.contains(Player.getPosition())) {
            cQuesterV2.status = "Getting Captured";
            General.println("[Debug]: " + cQuesterV2.status);

            // walks to tile before they start shooting arrows at us
            PathingUtil.walkToTile(preCaptureTile);
            Prayer.enable(Prayer.PRAYERS.PROTECT_FROM_MISSILES);

            //then walks closer to gate and gets shot with arrows
            Walking.walkPath(PATH_TO_GET_CAPTURED); // dax version didn't work
            //wait until we are captures in jail
            if (Timer.waitCondition(() -> wholeJailArea.contains(Player.getPosition()), 25000, 35000)) {
                Waiting.waitNormal(1500, 250);
                Prayer.disable(Prayer.PRAYERS.PROTECT_FROM_MISSILES);
            }
        }
    }


    public void checkHpAndPrayer() {
        RSItem[] food = Inventory.find(FOOD_IDS);
        if (Combat.getHPRatio() < General.random(45, 65) && food.length > 0) {
            if (food[0].click("Eat"))
                Utils.microSleep();
        }

        RSItem[] pPot = Inventory.find(PRAYER_POTION);
        if (Prayer.getPrayerPoints() < General.random(10, 20) && pPot.length > 0) {
            if (pPot[0].click())
                Utils.microSleep();
        }

        RSItem[] antidote = Inventory.find(ANTIDOTE_PLUS_PLUS);
        if (Game.getSetting(102) > 0 && antidote.length > 0) {
            if (antidote[0].click())
                Utils.microSleep();
        }
    }

    public void goToKaram() {
        if (Inventory.find(enchantedBar).length < 1) {
            cQuesterV2.status = "Going to Karam";
            General.println("[Debug]: " + cQuesterV2.status);
            Walking.blindWalkTo(new RSTile(2774, 2804, 0));

            if (Walking.blindWalkTo(karamTile))
                PathingUtil.movementIdle();

            checkHpAndPrayer();

            if (NpcChat.talkToNPC(7196)) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
        }
    }

    public void step4Ch2() {
        if (KARAM_AREA.contains(Player.getPosition()) && Inventory.find(monkeyDentures).length < 1) {
            cQuesterV2.status = "Going to Garkor";
            General.println("[Debug]: " + cQuesterV2.status);
            Prayer.enable(Prayer.PRAYERS.PROTECT_FROM_MISSILES);
            if (Walking.walkPath(GARKOR_PATH)) {
                PathingUtil.movementIdle();
                checkHpAndPrayer();

                Prayer.disable(Prayer.PRAYERS.PROTECT_FROM_MISSILES);

                if (NpcChat.talkToNPC("Garkor")) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                    //    NPCInteraction.handleConversation();
                    //    NPCInteraction.handleConversation();
                    //    NPCInteraction.handleConversation();
                }
            }
        }
    }


    public void stopMovingWait(int shortWait, int longWait) {
        Timer.waitCondition(() -> Player.isMoving(), 4000, 6000);
        Timer.waitCondition(() -> !Player.isMoving(), 8000, 12000);
        General.sleep(General.random(shortWait, longWait));
    }

    public void step5Ch2() {
        if (Inventory.find(monkeyDentures).length < 1 && Inventory.find(enchantedBar).length < 1) {
            cQuesterV2.status = "Getting Dentures";
            if (!DENTURES_BUILDING_AREA.contains(Player.getPosition())) {
                Utils.drinkPotion(ItemID.STAMINA_POTION);

                if (Walking.blindWalkTo(new RSTile(2801, 2768, 0)))
                    General.sleep(General.random(2500, 5000));

                Prayer.enable(Prayer.PRAYERS.PROTECT_FROM_MISSILES);

                if (Walking.walkPath(pathToCrates)) {
                    Timer.waitCondition(() -> !Player.isMoving(), 16000, 20000);
                    checkHpAndPrayer();
                    Prayer.disable(Prayer.PRAYERS.PROTECT_FROM_MISSILES);
                }

                if (Utils.clickObj("Doorway", "Open"))
                    General.sleep(General.random(2500, 5000));

            }
            if (DENTURES_BUILDING_AREA.contains(Player.getPosition()) && Inventory.find(monkeyDentures).length < 1) {
                Walking.blindWalkTo(new RSTile(2770, 2764, 0));
                stopMovingWait(300, 900);

                Walking.blindWalkTo(new RSTile(2770, 2768, 0));
                stopMovingWait(300, 900);

                Walking.clickTileMS(new RSTile(2768, 2769, 0), "Walk here");
                stopMovingWait(300, 900);

                while (Inventory.find(monkeyDentures).length < 1) {
                    General.sleep(50);
                    if (Utils.clickObj(4715, "Search")) {
                        NPCInteraction.waitForConversationWindow();
                        NPCInteraction.handleConversation("Yes");
                        NPCInteraction.handleConversation();
                        Timer.waitCondition(() -> Inventory.find(monkeyDentures).length > 0, 1500, 3000);
                    }
                }
            }
        }
    }

    public void goToAmuletMould() {
        if (Inventory.find(monkeyDentures).length > 0 && Inventory.find(amuletmould).length < 1) {
            if (!MMConst.AMULET_MOULD_UNDERGROUND.contains(Player.getPosition())) {
                cQuesterV2.status = "Getting amulet mould";
                General.println("[Debug]: " + cQuesterV2.status);
                if (Walking.blindWalkTo(new RSTile(2769, 2764, 0)))
                    PathingUtil.movementIdle();

                checkHpAndPrayer();

                while (!MMConst.AMULET_MOULD_UNDERGROUND.contains(Player.getPosition())) {
                    // in a loop b/c attacking spiders can prevent the text from appearing
                    General.sleep(50, 100);
                    if (Utils.clickObj(4714, "Search")) {
                        NPCInteraction.waitForConversationWindow();
                        NPCInteraction.handleConversation("Yes, I'm sure.");
                        NPCInteraction.handleConversation();
                        Timer.waitCondition(() -> MMConst.AMULET_MOULD_UNDERGROUND.contains(Player.getPosition()), 9000, 12000);
                    }
                }
            }
        }
    }

    public void getAmuletMould() {
        if (Inventory.find(monkeyDentures).length > 0 && Inventory.find(amuletmould).length < 1 && MMConst.AMULET_MOULD_UNDERGROUND.contains(Player.getPosition())) {
            cQuesterV2.status = "Getting amulet mould";
            General.println("[Debug]: " + cQuesterV2.status);
            Walking.blindWalkTo(new RSTile(2782, 9173, 0));
            PathingUtil.movementIdle();
            invAmuletMould = Inventory.find(amuletmould);
            while (Inventory.find(amuletmould).length < 1) {
                General.sleep(50, 200);
                if (Utils.clickObj(4724, "Search")) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation("Yes");
                    NPCInteraction.handleConversation();
                    Timer.waitCondition(() -> Inventory.find(amuletmould).length > 0, 2500, 4000);
                }
            }
        }
    }

    public void bankAfterMouldAndDentures() {
        if (Inventory.find(amuletmould).length > 0 && Inventory.find(monkeyDentures).length > 0) {
            if (Inventory.find(mSpeakAmulet).length < 1 && !Equipment.isEquipped(mSpeakAmulet)) {
                cQuesterV2.status = "Banking";
                General.println("[Debug]: " + cQuesterV2.status);
                BankManager.open(true);
                BankManager.depositEquipment();
                BankManager.checkEquippedGlory();
                BankManager.depositAll(true);
                BankManager.withdraw(2, true, ItemID.ANTIDOTE_PLUS_PLUS);
                BankManager.withdraw(1, true, monkeyDentures);
                BankManager.withdraw(1, true, amuletmould);
                BankManager.withdraw(3, true, PRAYER_POTION[0]);
                BankManager.withdraw(1, true, goldBar);
                BankManager.withdraw(2, true, STAMINA_POTION[0]);
                BankManager.withdraw(8, true, lobster);
                BankManager.withdraw(2, true, varrockTab);
                BankManager.withdraw(1, true, 542);
                BankManager.withdraw(1, true, 544);

                BankManager.getTeleItem(ItemID.RING_OF_DUELING[0], ItemID.RING_OF_DUELING[1],
                        ItemID.RING_OF_DUELING[2], ItemID.RING_OF_DUELING[3]);
                BankManager.close(true);
                Utils.equipItem(542);
                Utils.equipItem(544);
                if (Prayer.getPrayerPoints() < 35) {
                    Utils.clanWarsReset();
                }
            }
        }
    }

    public void goToZookNock() {
        if (Inventory.find(monkeyDentures).length > 0 && Inventory.find(amuletmould).length > 0 && Inventory.find(goldBar).length > 0) {
            cQuesterV2.status = "Going to Zooknock";
            General.println("[Debug]: " + cQuesterV2.status);
            if (PathingUtil.walkToTile(outsideDungeon)) {
                PathingUtil.movementIdle();
                Prayer.enable(Prayer.PRAYERS.PROTECT_FROM_MELEE);
            }
            if (PathingUtil.walkToTile(zooknocktile)) {
                Timer.waitCondition(() -> !Player.isMoving(), 20000, 25000);
                Prayer.disable(Prayer.PRAYERS.PROTECT_FROM_MELEE);
            }
        }
    }

    public void goToZookNockNoCheck() {
        cQuesterV2.status = "Going to Zooknock";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToTile(outsideDungeon);
        PathingUtil.movementIdle();
        Prayer.enable(Prayer.PRAYERS.PROTECT_FROM_MELEE);
        PathingUtil.walkToTile(zooknocktile);
        Timer.waitCondition(() -> !Player.isMoving(), 20000, 25000);
        Prayer.disable(Prayer.PRAYERS.PROTECT_FROM_MELEE);
    }

    public void getEnchantedBar() {
        if (Inventory.find(enchantedBar).length < 1 && Inventory.find(mSpeakAmulet).length < 1
                && Equipment.find(mSpeakAmulet).length < 1) {
            cQuesterV2.status = "Getting enchanted bar";
            General.println("[Debug}: " + cQuesterV2.status);
            zooknock = NPCs.findNearest("Zooknock");
            if (zooknock.length > 0) {
                AccurateMouse.click(zooknock[0], "Talk-to");
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                while (Interfaces.get(231) != null || Interfaces.get(217) != null) {
                    General.sleep(50);
                    NPCInteraction.handleConversation();
                    //     NPCInteraction.handleConversation();
                }
            }
            RSItem[] invItem1 = Inventory.find(amuletmould);
            RSItem[] invItem2 = Inventory.find(monkeyDentures);
            RSItem[] invItem3 = Inventory.find(goldBar);
            zooknock = NPCs.findNearest(7193);
            if (invItem1.length > 0 && zooknock.length > 0) {
                if (Utils.useItemOnNPC(amuletmould, 7193)) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                }
            }
            if (Inventory.find(monkeyDentures).length > 0) {
                Utils.useItemOnNPC(monkeyDentures, 7193);
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
            if (Inventory.find(goldBar).length > 0) {
                Utils.useItemOnNPC(goldBar, 7193);
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                Utils.modSleep();
            }
            Timer.waitCondition(() -> Inventory.find(enchantedBar).length > 0, 9000, 12000);
            Utils.modSleep();
        }
    }

    public void getItems3() {
        if (!wholeJailArea.contains(Player.getPosition()) && !ISLAND_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Banking";
            General.println("[Debug] " + cQuesterV2.status);
            BankManager.open(true);
            BankManager.checkEquippedGlory();
            BankManager.depositAll(true);

            BankManager.withdraw(1, true, ItemID.ADAMANT_KITESHIELD);
            Utils.equipItem(ItemID.ADAMANT_KITESHIELD);
            BankManager.withdraw(1, true, ItemID.ADAMANT_FULL_HELM);
            Utils.equipItem(ItemID.ADAMANT_FULL_HELM);

            BankManager.withdraw(1, true, enchantedBar);
            BankManager.withdraw(2, true, ItemID.ANTIDOTE_PLUS_PLUS);
            BankManager.withdraw(1, true, amuletmould);
            BankManager.withdraw(1, true, LOCKPICK);
            BankManager.withdraw(2, true, PRAYER_POTION[0]);
            BankManager.withdraw(12, true, lobster);
            BankManager.withdraw(1, true, STAMINA_POTION[0]);
            BankManager.withdraw(1, true, ballOfWool);
            BankManager.withdraw(2, true, varrockTab);
            BankManager.withdraw(1, true, glory4);
            BankManager.withdraw(1, true, mSpeakAmulet); // just in case we already have
            BankManager.close(true);

            if (Prayer.getPrayerPoints() < 35)
                Utils.clanWarsReset();

            getCaptured();

        }
    }

    RSTile[] PATH_TO_OUTSIDE_MELEE_MONKIES = {new RSTile(2765, 2806, 0), new RSTile(2769, 2806, 0), new RSTile(2771, 2804, 0), new RSTile(2774, 2804, 0), new RSTile(2776, 2804, 0), new RSTile(2779, 2804, 0), new RSTile(2779, 2801, 0), new RSTile(2779, 2798, 0), new RSTile(2782, 2797, 0)};
    RSTile[] PATH_TO_LADDER = {new RSTile(2782, 2797, 0), new RSTile(2783, 2794, 0), new RSTile(2784, 2789, 0), new RSTile(2784, 2786, 0), new RSTile(2788, 2786, 0), new RSTile(2793, 2788, 0), new RSTile(2797, 2789, 0), new RSTile(2801, 2788, 0), new RSTile(2803, 2786, 0), new RSTile(2806, 2785, 0)};
    RSTile[] PATH_TO_TRAPDOOR_PT1 = {new RSTile(2783, 2796, 0), new RSTile(2784, 2793, 0), new RSTile(2783, 2790, 0), new RSTile(2784, 2787, 0), new RSTile(2786, 2786, 0), new RSTile(2791, 2787, 0), new RSTile(2795, 2789, 0), new RSTile(2805, 2793, 0)};

    RSTile TILE_BEFORE_BAMBOO_LADDERS = new RSTile(2793, 2773, 0);

    public void worldHop() { // may be better to wait it out, they evetually stop attacking after 5-10m
        int world = WorldHopper.getRandomWorld(true);
        WorldHopper.changeWorld(world);
    }

    public void navigatingMeleeMonkeys() {
        if (!wholeJailArea.contains(Player.getPosition()) && (Inventory.find(mSpeakAmulet).length < 1
                && Inventory.find(unfinishedAmulet).length < 1) && !Equipment.isEquipped(mSpeakAmulet)) {
            cQuesterV2.status = "Going to Make Amulet";

         /*   if (!UNDERGROUND_FLAME_AREA.contains(Player.getPosition()) &&
                    !AREA_TO_START_MELEE_PRAY.contains(Player.getPosition()) &&
                    !MELEE_SAFE_AREA.contains(Player.getPosition())) {
                General.println("Step12ch2 first section");
                Prayer.enable(Prayer.PRAYERS.PROTECT_FROM_MELEE);
                if (Prayer.isPrayerEnabled(Prayer.PRAYERS.PROTECT_FROM_MELEE)) {
                    Walking.walkPath(PATH_TO_OUTSIDE_MELEE_MONKIES);
                    Timer.waitCondition(() -> AREA_TO_START_MELEE_PRAY.contains(Player.getPosition()), 12000, 15000);
                }
            }
            if (!MELEE_SAFE_AREA.contains(Player.getPosition()) && !UNDERGROUND_FLAME_AREA.contains(Player.getPosition())) {
                if (Prayer.isPrayerEnabled(Prayer.PRAYERS.PROTECT_FROM_MELEE)) {
                    Prayer.enable(Prayer.PRAYERS.PROTECT_FROM_MELEE);
                    Walking.walkPath(PATH_TO_TRAPDOOR_PT1);
                    Timer.waitCondition(() -> MELEE_SAFE_AREA.contains(Player.getPosition()), 12000, 15000);
                }
            }
            if (MELEE_SAFE_AREA.contains(Player.getPosition())) {
                Utils.clickScreenWalk(new RSTile(2808, 2791, 0)); // this is the tile to wait at
                Prayer.disable(Prayer.PRAYERS.PROTECT_FROM_MELEE);
                General.sleep(General.random(6000, 12000));
            }
            while (MONKEY_TEMPLE.contains(Player.getPosition())) {
                General.sleep(100);
                int i = 0;
                General.println("[Debug]: i = " + i);
                if (MELEE_SAFE_AREA.contains(Player.getPosition())) {
                    General.println("[Debug]: Waiting for monkey to move out of the way");
                    i++;
                    General.println("[Debug]: Attempts: " + i);
                    Timer.waitCondition(() -> !BLOCKING_AREA.contains(NPCs.findNearest(5276)[0]), 45000, 60000);

                    Prayer.enable(Prayer.PRAYERS.PROTECT_FROM_MELEE);
                    Utils.clickScreenWalk(new RSTile(2806, 2785, 0)); // in front of trap door
                    General.sleep(General.random(500, 900));

                    if (Utils.clickObj()(4879, "Open"))
                        Timer.waitCondition(() -> Objects.find(15, 4880).length > 0, 5000, 7000);

                    if (Utils.clickObj()(4880, "Climb-down")) {
                        Timer.waitCondition(() -> UNDERGROUND_FLAME_AREA.contains(Player.getPosition()), 5000, 8000);
                        if (UNDERGROUND_FLAME_AREA.contains(Player.getPosition())) {
                            Waiting.waitUniform(2000,3500); // need this otherwise using the item on the fire won't work
                            break;
                        }
                    }
                    if (!UNDERGROUND_FLAME_AREA.contains(Player.getPosition())) {
                        Walking.clickTileMS(new RSTile(2808, 2791, 0), "Walk here");
                        Timer.waitCondition(() -> MELEE_SAFE_AREA.contains(Player.getPosition()), 12000, 15000);
                    }
                    if (MELEE_SAFE_AREA.contains(Player.getPosition())) {
                        General.println("[Debug]: Waiting to de-agro");
                        Prayer.disable(Prayer.PRAYERS.PROTECT_FROM_MELEE);
                        General.sleep(General.random(12000, 18000)); // sleep to deagro
                    }
                }
            }*/

            if (!UNDERGROUND_FLAME_AREA.contains(Player.getPosition())) {
                Prayer.enable(Prayer.PRAYERS.PROTECT_FROM_MELEE);
                if (Prayer.isPrayerEnabled(Prayer.PRAYERS.PROTECT_FROM_MELEE)) {
                    Walking.walkPath(PATH_TO_OUTSIDE_MELEE_MONKIES);
                    Timer.waitCondition(() -> AREA_TO_START_MELEE_PRAY.contains(Player.getPosition()), 12000, 15000);
                }
                // go upstairs
                checkPrayer();
                if (AREA_TO_START_MELEE_PRAY.contains(Player.getPosition()) &&
                        PathingUtil.localNavigation(TILE_BEFORE_BAMBOO_LADDERS)) {
                    PathingUtil.movementIdle();
                    if (Utils.clickObj("Bamboo Ladder", "Climb-up"))
                        Timer.waitCondition(() -> Player.getPosition().getPlane() == 1, 5000, 6500);
                }
                // navigate upstairs
                if (UPSTAIRS_OF_TEMPLE_AREA.contains(Player.getPosition())) {
                    checkPrayer();
                    if (PathingUtil.localNavigation(LADDER_DOWN_TILE))
                        PathingUtil.movementIdle();

                    // activate prayer before going down
                    checkPrayer();
                    if (Utils.clickObj("Bamboo Ladder", "Climb-down"))
                        Timer.waitCondition(() -> Player.getPosition().getPlane() == 0, 5000, 6500);

                }
                //enter basement
                if (Player.getPosition().equals(LANDING_TILE)) {
                    if (Utils.clickObj(4879, "Open"))
                        Timer.waitCondition(() -> Objects.find(15, 4880).length > 0, 5000, 7000);

                    if (Utils.clickObj(4880, "Climb-down")) {
                        Timer.waitCondition(() -> UNDERGROUND_FLAME_AREA.contains(Player.getPosition()), 5000, 8000);
                        if (UNDERGROUND_FLAME_AREA.contains(Player.getPosition())) {
                            Waiting.waitUniform(2000, 3500); // need this otherwise using the item on the fire won't work
                        }
                    }
                }
            }

            if (UNDERGROUND_FLAME_AREA.contains(Player.getPosition()) && Inventory.find(unfinishedAmulet).length < 1) {
                goToMakeAmulet();
            }

            // breakout of prison then
            // go to the ladder near all the melee monkeys
            // use melee prayer
        }

    }

    RSArea TRIGGER_AREA = new RSArea(new RSTile(2771, 2798, 0), new RSTile(2764, 2804, 0));

    public RSArea JAIL_CELL = new RSArea(new RSTile(2770, 2795, 0), new RSTile(2776, 2793, 0));
    public RSArea OUTSIDE_CELL_SAFE_AREA = new RSArea(new RSTile(2766, 2795, 0), new RSTile(2769, 2793, 0));
    // this is

    public RSTile OUTSIDE_JAIL_TILE = new RSTile(2762, 2804, 0);
    public RSTile INSIDE_CELL_SAFE_TILE = new RSTile(2772, 2794, 0);
    public RSArea wholeJailArea = new RSArea(new RSTile(2776, 2793, 0), new RSTile(2764, 2802, 0));


    public void escapePrison() { // prison break
        if (MMConst.wholeJailArea.contains(Player.getPosition())) {
            General.sleep(General.random(2500, 5000));
            aberab = NPCs.findNearest("Aberab");
            trefaji = NPCs.findNearest("Trefaji");
            cQuesterV2.status = "Monkey Madness: Escaping Prison";
            General.println("[Debug]: " + cQuesterV2.status);
            while (!MMConst.outsideJailArea1.contains(Player.getPosition()) && !MMConst.outsideJailArea2.contains(Player.getPosition())) {
                General.sleep(200);
                cQuesterV2.status = "Waiting";
                if (Combat.getHPRatio() < General.random(40, 60) && Inventory.find(FOOD_IDS).length > 0) {
                    AccurateMouse.click(Inventory.find(FOOD_IDS)[0], "Eat");
                    General.sleep(General.random(200, 800));
                }
                if (Game.getSetting(102) > 0 && Inventory.find(ANTIDOTE_ID).length > 0) {
                    AccurateMouse.click(Inventory.find(ANTIDOTE_ID)[0], "Drink");
                    General.sleep(General.random(200, 800));
                }
                if ((!MMConst.JAIL_CELL_WAITING_AREA.contains(Player.getPosition()) &&
                        MMConst.JAIL_CELL.contains(Player.getPosition()))
                        && (!TRIGGER_AREA.contains(aberab[0].getPosition()) || !TRIGGER_AREA.contains(trefaji[0].getPosition()))) {
                    cQuesterV2.status = "Going to safe tile within Jail";
                    General.println("[Debug]: " + cQuesterV2.status);
                    Walking.clickTileMS(MMConst.INSIDE_CELL_SAFE_TILE, "Walk here");
                    Timer.waitCondition(() -> (MMConst.TRIGGER_AREA.contains(aberab[0].getPosition())
                            && MMConst.TRIGGER_AREA.contains(trefaji[0].getPosition())), 18000, 22000);
                }
                aberab = NPCs.findNearest("Aberab");
                trefaji = NPCs.findNearest("Trefaji");
                if (MMConst.TRIGGER_AREA.contains(aberab[0].getPosition()) && MMConst.TRIGGER_AREA.contains(trefaji[0].getPosition())) {
                    General.println("[Debug]: Monkeys are in safe position");
                    if (OUTSIDE_CELL_SAFE_AREA.contains(Player.getPosition()) && MMConst.TRIGGER_AREA.contains(aberab[0].getPosition()) && MMConst.TRIGGER_AREA.contains(trefaji[0].getPosition())) {
                        cQuesterV2.status = "Running out from the safe area.";
                        General.println("[Debug]: " + cQuesterV2.status);
                        Walking.blindWalkTo(MMConst.OUTSIDE_JAIL_TILE);
                        General.sleep(General.random(4000, 6000));
                        Timer.waitCondition(() -> !Player.isMoving(), 7000, 9000);
                    } else if (MMConst.JAIL_CELL.contains(Player.getPosition())) {
                        jailDoor = Objects.findNearest(10, "Jail door");
                        cQuesterV2.status = "Picking lock.";
                        General.println("[Debug]: " + cQuesterV2.status);
                        AccurateMouse.click(jailDoor[0], "Pick-lock");
                        Timer.waitCondition(() -> MMConst.outsideJailDoor.contains(Player.getPosition()), 4500, 6000);
                        if (MMConst.outsideJailDoor.contains(Player.getPosition())) {
                            General.println("[Debug]: if outside jail door contains player");
                            Walking.clickTileMS(MMConst.OUTSIDE_CELL_SAFE_AREA.getRandomTile(), "Walk here");
                            General.sleep(General.random(4500, 7000));
                            Timer.waitCondition(() -> !TRIGGER_AREA.contains(aberab[0].getPosition())
                                    || !TRIGGER_AREA.contains(trefaji[0].getPosition()), 13000, 17000);
                        }
                    }
                }
            }
        }
        // go to the ladder near all the melee monkeys
        // use melee prayer
    }

    public void checkPrayer() {
        if (Prayer.getPrayerPoints() < General.random(12, 20) &&
                Utils.drinkPotion(ItemID.PRAYER_POTION))
            General.sleep(General.random(150, 500));
        if (!Prayer.isPrayerEnabled(Prayer.PRAYERS.PROTECT_FROM_MELEE) && Prayer.getPrayerPoints() > 0) {
            Prayer.enable(Prayer.PRAYERS.PROTECT_FROM_MELEE);
        }
    }


    public void goToMakeAmulet() {
        Mouse.setSpeed(General.random(130, 165));
        if (Inventory.find(monkeyTalisman).length < 1) {
            if (!wholeJailArea.contains(Player.getPosition())) {

                checkPrayer();
                for (int i = 0; i < 5; i++) {
                    if (Utils.useItemOnObject(enchantedBar, 4766)) {
                        Timer.waitCondition(() -> Inventory.find(unfinishedAmulet).length > 0, 10000, 15000);
                        Waiting.waitUniform(2000, 3500);
                    }
                    if (Inventory.find(unfinishedAmulet).length > 0)
                        break;
                }

                for (int i = 0; i < 5; i++) {
                    if (Utils.useItemOnItem(ballOfWool, unfinishedAmulet))
                        General.sleep(General.random(500, 1500));
                    if (Inventory.find(mSpeakAmulet).length > 0)
                        break;
                }
                checkPrayer();
                RSObject[] climbingRope = Objects.findNearest(20, "Climbing rope");
                if (climbingRope.length > 0 && (Inventory.find(unfinishedAmulet).length > 0 ||
                        Inventory.find(mSpeakAmulet).length > 0)) {
                    if (!Prayer.isPrayerEnabled(Prayer.PRAYERS.PROTECT_FROM_MELEE) && Prayer.getPrayerPoints() > 0)
                        Prayer.enable(Prayer.PRAYERS.PROTECT_FROM_MELEE);

                    if (Utils.clickObj("Climbing rope", "Climb")) {
                        Timer.waitCondition(() -> ABOVE_LADDER_AREA.contains(Player.getPosition()), 4000, 7000);
                        checkPrayer();
                    }
                    if (ABOVE_LADDER_AREA.contains(Player.getPosition())) {
                        // go upstairs
                        checkPrayer();

                        if (Utils.clickObj("Bamboo Ladder", "Climb-up"))
                            Timer.waitCondition(() -> Player.getPosition().getPlane() == 1, 5000, 6500);

                        // navigate upstairs
                        if (UPSTAIRS_OF_TEMPLE_AREA.contains(Player.getPosition())) {
                            checkPrayer();
                            if (PathingUtil.localNavigation(INITIAL_PLANE_1_TILE))
                                PathingUtil.movementIdle();

                            // activate prayer before going down
                            checkPrayer();
                            if (Utils.clickObj("Bamboo Ladder", "Climb-down"))
                                Timer.waitCondition(() -> Player.getPosition().getPlane() == 0, 5000, 6500);

                        }
                        Walking.walkPath(ESCAPE_AFTER_AMULET);
                        PathingUtil.movementIdle();
                    }

                    if (LOWER_LADDER_ABOVE_AREA.contains(Player.getPosition())) {
                        Walking.walkPath(LOWER_ESCAPE_PATH);
                        PathingUtil.movementIdle();
                    }
                }

                if (AFTER_PATH_1_AREA.contains(Player.getPosition())) {
                    checkPrayer();
                    Walking.walkPath(ESCAPE_PATH_PT2_TO_CHILD);
                    PathingUtil.movementIdle();
                    Prayer.disable(Prayer.PRAYERS.PROTECT_FROM_MELEE);
                }
               /* if (SOUTH_MELEE_SAFE_AREA.contains(Player.getPosition())) {
                    PathingUtil.localNavigation()(karamTile); // check this
                    checkPrayer();
                    Walking.walkPath(ESCAPE_PATH_PT2_TO_CHILD);
                    PathingUtil.movementIdle();
                    Prayer.disable(Prayer.PRAYERS.PROTECT_FROM_MELEE);
                }*/
                checkPrayer();
            }
        }
    }

    public void equipMSpeakAmulet() {
        Utils.equipItem(mSpeakAmulet);
    }

    RSArea MONKEY_CHILD_AREA = new RSArea(new RSTile(2741, 2796, 0), new RSTile(2745, 2793, 0));

    public void talkToMonkeyChild() {
        Mouse.setSpeed(General.random(130, 165));
        if (Inventory.find(monkeyTalisman).length < 1) {
            if (!wholeJailArea.contains(Player.getPosition()) && (Inventory.find(mSpeakAmulet).length > 0 || Equipment.find(mSpeakAmulet).length > 0)) {
                int speed = General.random(140, 160);

                if (Prayer.isPrayerEnabled(Prayer.PRAYERS.PROTECT_FROM_MELEE))
                    Prayer.disable(Prayer.PRAYERS.PROTECT_FROM_MELEE);

                Mouse.setSpeed(speed);
                General.println("[Debug]: increasing Mouse speed to: " + speed);
                while (Inventory.find(monkeyTalisman).length < 1) {
                    General.sleep(50);

                    cQuesterV2.status = "Waiting";

                    if (Inventory.isFull())
                        eatAnyFoodButBanana();

                    theMonkeysAunt = NPCs.findNearest("The Monkey's Aunt");
                    monkeyChild = NPCs.findNearest("Monkey Child");

                    if (theMonkeysAunt.length > 0 && outsideMonkeyChildArea.contains(theMonkeysAunt[0].getPosition())) {
                        cQuesterV2.status = "Talking to monkey child";
                        General.println("[Debug]: " + cQuesterV2.status);
                        if (!MONKEY_CHILD_AREA.contains(Player.getPosition())) {
                            Walking.blindWalkTo(new RSTile(2743, 2795, 0));
                            Waiting.waitUniform(2000, 3500);
                        }
                        if (NpcChat.talkToNPC(5268)) {
                            NPCInteraction.waitForConversationWindow();
                            NPCInteraction.handleConversation("Well I'll be a monkey's uncle!", "How many bananas did Aunty want?");
                            NPCInteraction.handleConversation("How many bananas did Aunty want?");
                            NPCInteraction.handleConversation();
                        }
                    } else if (!hidingArea.contains(Player.getPosition())) {
                        cQuesterV2.status = "Hiding";
                        Walking.blindWalkTo(monkeyChildHidingTile);
                        General.sleep(General.random(1000, 3000));
                        if (Inventory.find(monkeyTalisman).length > 0) {
                            break;
                        }
                    }
                    General.sleep(General.random(500, 2000));
                }
            }
        }
    }

    public void eatAnyFoodButBanana() {
        RSItem food = Entities.find(ItemEntity::new)
                .actionsContains("Eat")
                .nameNotContains("Banana")
                .getFirstResult();
        if (food != null && food.click("Eat"))
            Timer.waitCondition(() -> Player.getAnimation() != -1, 1500, 2200);

    }


    public void getBananas() {
        if (Inventory.find(monkeyTalisman).length < 1) {
            if (wholeJailArea.contains(Player.getPosition()))
                escapePrison();

            else {
                RSItem[] invItem1 = Inventory.find(monkeyTalisman);
                // we need 5 bananas
                while (Inventory.find("Banana").length < 5) {
                    General.sleep(100, 200); // doesn't need to be a super fast loop
                    if (AFTER_PATH_1_AREA.contains(Player.getPosition())) {
                        checkPrayer();
                        Walking.walkPath(ESCAPE_PATH_PT2_TO_CHILD);
                        PathingUtil.movementIdle();
                        Prayer.disable(Prayer.PRAYERS.PROTECT_FROM_MELEE);
                        if (PathingUtil.localNavigation(monkeyChildHidingTile))
                            PathingUtil.movementIdle();
                    }
                    if (Inventory.isFull())
                        eatAnyFoodButBanana();

                    // if we get captured due to script error, break loop
                    if (wholeJailArea.contains(Player.getPosition()))
                        break;


                    cQuesterV2.status = "Waiting to pick bananas";
                    theMonkeysAunt = NPCs.findNearest("The Monkey's Aunt");
                    if (theMonkeysAunt.length > 0 &&
                            outsideMonkeyChildArea.contains(theMonkeysAunt[0].getPosition())) {

                        General.println("[Debug]: Getting bananas.");
                        //spam click the tree
                        if (Utils.clickObj("Banana Tree", "Search", true))
                            General.sleep(120, 400);
                        if (Inventory.find("Banana").length > 4) {
                            General.println("[Debug]: We have 5 bananas, hiding");
                            if (PathingUtil.localNavigation(monkeyChildHidingTile))
                                PathingUtil.movementIdle();
                            break;
                        }
                    }

                }
            }
        }
    }

    public void getItems4() {
        if (Inventory.find(monkeyTalisman).length > 0) {
            cQuesterV2.status = "Banking";
            General.println("[Debug]: " + cQuesterV2.status);
            BankManager.open(true);
            BankManager.checkEquippedGlory();
            BankManager.depositAll(true);
            if (Equipment.find(ItemID.RING_OF_DUELING).length < 1) {
                BankManager.withdrawArray(ItemID.RING_OF_DUELING, 1);
                Utils.equipItem(ItemID.RING_OF_DUELING[0]);
                BankManager.depositAll(true);
            }
            BankManager.withdraw(1, true, ItemID.ANTIDOTE_PLUS_PLUS);
            BankManager.withdraw(1, true, amuletmould);
            BankManager.withdraw(1, true, LOCKPICK);
            BankManager.withdraw(2, true, PRAYER_POTION[0]);
            BankManager.withdraw(13, true, MONKFISH);
            BankManager.withdraw(2, true, STAMINA_POTION[0]);
            BankManager.withdraw(1, true, ballOfWool);
            BankManager.withdraw(1, true, glory4);
            BankManager.withdraw(1, true, mSpeakAmulet);
            BankManager.withdraw(1, true, ItemID.SKILLS_NECKLACE);
            BankManager.withdraw(1, true, ItemID.RING_OF_DUELING);
            BankManager.withdraw(3050, true, 995);
            Utils.equipItem(mSpeakAmulet);
            BankManager.withdraw(1, true, MONKEY_CORPSE);
            BankManager.withdraw(1, true, MONKEY_BONES);
            BankManager.withdraw(1, true, monkeyTalisman);
            BankManager.close(true);
            if (Prayer.getPrayerPoints() < 30)
                Utils.clanWarsReset();
        }
    }


    public void returnToZooknock() {
        if (Inventory.find(greegree).length < 1 && Inventory.find(monkeyTalisman).length > 0) {
            goToZookNockNoCheck();
            Prayer.disable(Prayer.PRAYERS.PROTECT_FROM_MELEE);
            if (Utils.useItemOnNPC(monkeyTalisman, ZOOKNOCK_ID)) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
            if (Inventory.find(MONKEY_BONES).length > 0 &&
                    Utils.useItemOnNPC(MONKEY_BONES, ZOOKNOCK_ID)) {
                NPCInteraction.waitForConversationWindow();
            } else if (Inventory.find(MONKEY_CORPSE).length > 0 && //use corpse if tai bwo wannai trio is done
                    Utils.useItemOnNPC(MONKEY_CORPSE, ZOOKNOCK_ID)) {
                NPCInteraction.waitForConversationWindow();
            }

            if (NPCInteraction.isConversationWindowUp()) {
                NPCInteraction.handleConversation();
                General.sleep(General.random(3000, 5000));
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                NPCInteraction.handleConversation();
                General.sleep(General.random(5000, 9000));
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();

            }
        }
    }

    public void buyTalismans() { // get 3050 coins for this
        cQuesterV2.status = "Going to Buy Talismans";
        General.println("[Debug]: " + cQuesterV2.status);
        if (!ISLAND_AREA.contains(Player.getPosition())) {
            PathingUtil.walkToTile(new RSTile(2799, 2703, 0), 3, true);
        }
        if (Utils.equipItem(greegree))
            Timer.waitCondition(() -> Equipment.isEquipped(greegree), 1500, 2000);

        if (!LARGE_SHOP_AREA.contains(Player.getPosition())) {
            if (Inventory.find(ItemID.STAMINA_POTION[0]).length > 0) {
                AccurateMouse.click(Inventory.find(ItemID.STAMINA_POTION[0])[0], "Drink");
            }
            Walking.walkPath(pathToGate);
            Timing.waitCondition(() -> !Player.isMoving(), 6000);
            gate = Objects.findNearest(40, 4788);
            if (gate.length > 0) {
                AccurateMouse.click(gate[0], "Open");
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                General.sleep(General.random(2500, 5000));
            }
        }
        if (!LARGE_SHOP_AREA.contains(Player.getPosition())) {
            Walking.blindWalkTo(SMALL_SHOP_AREA.getRandomTile());
            Timer.waitCondition(() -> !Player.isMoving(), 6000, 9000);
            if (AccurateMouse.click(NPCs.find("Tutab")[0], "Trade"))
                Timer.waitCondition(() -> Interfaces.get(300, 16, 12) != null, 6000, 9090);
            if (Interfaces.get(300, 16, 12) != null) {
                while (Inventory.find(monkeyTalisman).length < 3) {
                    General.sleep(General.random(400, 1200));
                    AccurateMouse.click(Interfaces.get(300, 16, 12), "Buy 1");
                    if (Inventory.isFull()) {
                        Interfaces.get(300, 1, 11).click(); // closes
                        eatAnyFoodButBanana();
                        if (AccurateMouse.click(NPCs.find("Tutab")[0], "Trade"))
                            Timer.waitCondition(() -> Interfaces.get(300, 16, 12) != null,
                                    6000, 9000);
                    }
                }
            }
        }
    } // needs coins

    public void stepX11() { // leaves zooknock and then goes back to the island (faster than leaving tunnel)
        Waiting.waitUniform(4000, 6000);
        cQuesterV2.status = "Going to Garkor";
        General.println("[Debug]: " + cQuesterV2.status);
        if (!ISLAND_AREA.contains(Player.getPosition())) {
            PathingUtil.walkToTile(new RSTile(2799, 2703, 0), 2, false);
        }
        if (ISLAND_AREA.contains(Player.getPosition())) {
            if (Utils.equipItem(greegree))
                Timer.waitCondition(() -> Equipment.isEquipped(greegree), 1500, 2000);

            if (!GARKOR_AREA.contains(Player.getPosition())) {
                Walking.walkPath(pathToGate);
                PathingUtil.movementIdle();

                if (Utils.clickObj("Bamboo Gate", "Open")) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                    General.sleep(General.random(2500, 5000));
                }
            }
            Walking.walkPath(pathToElderGuard);
            PathingUtil.movementIdle();
        }
        General.sleep(General.random(2500, 5000));
        Walking.blindWalkTo(new RSTile(2805, 2760, 0));
        PathingUtil.movementIdle();

        if (NpcChat.talkToNPC("Garkor")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }
    }


    RSArea LADDER_AREA1 = new RSArea(new RSTile(2709, 2768, 0), new RSTile(2716, 2762, 0));
    RSArea KING_AREA = new RSArea(new RSTile(2800, 2765, 0), new RSTile(2804, 2759, 0));


    public void stepX12() {
        if (ISLAND_AREA.contains(Player.getPosition())) {
            if (!ZOO_AREA.contains(Player.getPosition())) {
                if (!KING_AREA.contains(Player.getPosition())) {
                    cQuesterV2.status = "Going to Awowogei";
                    General.println("[Debug]: " + cQuesterV2.status);
                    elderGuard = NPCs.findNearest("Elder Guard");
                    RSItem[] invItem1 = Inventory.find(mSpeakAmulet);
                    if (Inventory.find(mSpeakAmulet).length > 0) {
                        Inventory.find(mSpeakAmulet)[0].click("Wear");
                    }
                    if (NpcChat.talkToNPC("Elder Guard")) {
                        NPCInteraction.waitForConversationWindow();
                        NPCInteraction.handleConversation();
                    }
                    cQuesterV2.status = "Going to Kruk";
                    General.println("[Debug]: " + cQuesterV2.status);
                    Walking.blindWalkTo(new RSTile(2803, 2756, 0)); // avoids the elder guard which otherwise blocks movement
                    Utils.drinkPotion(ItemID.STAMINA_POTION);
                    Walking.walkPath(pathToKrukPt1);
                    PathingUtil.movementIdle();

                    if (LADDER_AREA1.contains(Player.getPosition())) {
                        if (Utils.clickObj(4775, "Climb-up")) {
                            General.sleep(General.random(6000, 8000));
                            Walking.walkPath(pathToKrukPt2);
                            PathingUtil.movementIdle();
                            General.sleep(General.random(3000, 5000));
                        }
                    }
                    if (Utils.clickObj(4776, "Climb-down")) {
                        General.sleep(General.random(5000, 8000));
                    }
                    if (NpcChat.talkToNPC("Kruk")) {
                        NPCInteraction.waitForConversationWindow();
                        NPCInteraction.handleConversation();
                        General.sleep(General.random(9000, 12000));
                    }
                }
            }
        }
    }


    public void stepX13() {
        if (ISLAND_AREA.contains(Player.getPosition())) {
            if (KING_AREA.contains(Player.getPosition())) {
                cQuesterV2.status = "Talking to Awowogei";
                General.println("[Debug]: " + cQuesterV2.status);
                if (Utils.clickObj(4771, "Talk-to")) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                    NPCInteraction.handleConversation();
                    NPCInteraction.handleConversation();
                }
            }
        }
    }

    RSArea ZOO_AREA = new RSArea(new RSTile(2639, 3261, 0), new RSTile(2588, 3289, 0));
    RSArea MONKEY_ENCOLSURE = new RSArea(
            new RSTile[]{
                    new RSTile(2601, 3283, 0),
                    new RSTile(2605, 3283, 0),
                    new RSTile(2607, 3281, 0),
                    new RSTile(2607, 3277, 0),
                    new RSTile(2605, 3276, 0),
                    new RSTile(2602, 3276, 0),
                    new RSTile(2600, 3274, 0),
                    new RSTile(2598, 3274, 0),
                    new RSTile(2598, 3278, 0),
                    new RSTile(2600, 3280, 0),
                    new RSTile(2600, 3283, 0)
            }
    );

    int MONKEY_ITEM_ID = 4033;

    public void stepX14() {
        if (Inventory.find(MONKEY_ITEM_ID).length == 0) {
            if (!ZOO_AREA.contains(Player.getPosition())) {

                if (!MONKEY_ENCOLSURE.contains(Player.getPosition())) {
                    cQuesterV2.status = "Going to Zoo";
                    General.println("[Debug]: " + cQuesterV2.status);
                    PathingUtil.walkToTile(new RSTile(2608, 3281, 0));
                }
            }
            if (!ISLAND_AREA.contains(Player.getPosition())) {
                if (ZOO_AREA.contains(Player.getPosition())) {
                    if (Utils.equipItem(greegree))
                        Timer.waitCondition(() -> Equipment.isEquipped(greegree), 1500, 2000);

                    if (!MONKEY_ENCOLSURE.contains(Player.getPosition())) {
                        cQuesterV2.status = "Getting Caged";
                        General.println("[Debug]: " + cQuesterV2.status);
                        if (NpcChat.talkToNPC("Monkey minder")) {
                            NPCInteraction.waitForConversationWindow();
                            NPCInteraction.handleConversation();
                            Timer.slowWaitCondition(() -> MONKEY_ENCOLSURE.contains(Player.getPosition()), 6000, 8000);
                        }
                    }
                }
                if (MONKEY_ENCOLSURE.contains(Player.getPosition())) {
                    cQuesterV2.status = "Talking to Monkey";
                    General.println("[Debug]: " + cQuesterV2.status);
                    if (NpcChat.talkToNPC(5279)) {
                        NPCInteraction.waitForConversationWindow();
                        NPCInteraction.handleConversation();
                        NPCInteraction.handleConversation();
                        NPCInteraction.handleConversation();
                        Timer.waitCondition(() -> Inventory.find(4033).length > 0, 6000, 8000); // 4033 is the monkey itself
                        General.sleep(General.random(500, 2000));
                    }
                }

            }
        }
    }

    public void stepX15() {
        if (!ISLAND_AREA.contains(Player.getPosition())) {
            if (Inventory.find(4033).length > 0) { // checks if monkey is in inventory
                if (MONKEY_ENCOLSURE.contains(Player.getPosition())) {
                    cQuesterV2.status = "Leaving cage";
                    if (Equipment.find(greegree).length > 0) {
                        Equipment.find(greegree)[0].click("Remove");
                        Timer.waitCondition(() -> Inventory.find(greegree).length > 0, 6000, 8000);
                    }
                    if (NpcChat.talkToNPC("Monkey minder")) {
                        NPCInteraction.waitForConversationWindow();
                        NPCInteraction.handleConversation();
                        General.sleep(General.random(3500, 5000));
                        NPCInteraction.waitForConversationWindow();
                        NPCInteraction.handleConversation();
                    }
                }
            }
        }
    }

    RSArea INSIDE_GATE = new RSArea(new RSTile(2459, 3384, 0), new RSTile(2463, 3389, 0));

    public void moveToBeach() {
        if (!ISLAND_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Returning to Awowogei";
            General.println("[Debug]: " + cQuesterV2.status);
            if (Inventory.find(4033).length > 0 && !MONKEY_ENCOLSURE.contains(Player.getPosition())) {
                Utils.drinkPotion(ItemID.STAMINA_POTION);

                Walking.walkPath(pathToTreeGnomeGate);
                Waiting.waitUniform(4000, 6000);
                RSObject[] gate = Objects.findNearest(20, "Gate");

                //open gate and wait until we are inside the tree gnome village gate
                if (gate.length > 0 && AccurateMouse.click(gate[0], "Open"))
                    Timer.abc2WaitCondition(() -> INSIDE_GATE.contains(Player.getPosition()), 10000, 15000);

                // walk a bit further north before making a global walking call to avoid teleports;
                PathingUtil.localNavigation(new RSTile(2468, 3486, 0));
                General.sleep(General.random(2000, 5000));

                // code above avoided teleporting by using local navigation and hardcoded paths
                // Im trying this where we just blacklist teleports and then walk. UNTESTED
                //TODO Confirm this works
                Teleport.blacklistTeleports(Teleport.values());
                PathingUtil.walkToTile(new RSTile(2799, 2703, 0));// switches to DaxWalker to handle all the NPCs
            }
        }
    }

    RSArea GATE_AREA = new RSArea(new RSTile(2719, 2765, 0), new RSTile(2722, 2756, 0));

    public void stepX17() {
        if (ISLAND_AREA.contains(Player.getPosition())) {
            Teleport.clearTeleportBlacklist(); //clears blacklist from previous step
            cQuesterV2.status = "Returning to Awowogei";
            if (!GATE_AREA.contains(Player.getPosition())) {
                if (beachArea.contains(Player.getPosition())) {

                    if (!Equipment.isEquipped(greegree)) {
                        RSItem[] invItem1 = Inventory.find(greegree);
                        if (Utils.equipItem(greegree))
                            Timer.waitCondition(() -> Equipment.isEquipped(greegree), 1500, 2000);
                    }

                    Walking.walkPath(pathToGate);
                    Timer.waitCondition(() -> !Player.isMoving(), 15000, 20000);
                }
            }

            if (GATE_AREA.contains(Player.getPosition())) {
                gate = Objects.findNearest(20, "Bamboo Gate");
                if (gate.length > 0 && AccurateMouse.click(gate[0], "Open")) {
                    NPCInteraction.handleConversation();
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                    General.sleep(3500, 5000); //sleep as we walk through the gate
                }

                if (!aowowgeiAreaOutside.contains(Player.getPosition())) {
                    Walking.walkPath(pathToElderGuard);
                    Timer.waitCondition(() -> !Player.isMoving(), 15000, 20000);
                }
            }
        }
    }

    public void stepX18() {
        if (aowowgeiAreaOutside.contains(Player.getPosition())) {
            //TODO get the specific tile this npc is on and use that
            if (NpcChat.talkToNPC("Elder Guard")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                General.sleep(3500, 6000);
            }

            if (KING_AREA.contains(Player.getPosition())) {
                cQuesterV2.status = "Talking to Awowogei";
                if (Utils.clickObj(4771, "Talk-to")) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                    NPCInteraction.handleConversation();
                    NPCInteraction.handleConversation();
                }
            }
        }
    }

    public void stepX19() {
        if (KING_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Leaving Awowogei area";
            PathingUtil.localNavigation(new RSTile(2802, 2759, 0));
            // asks the gorilla to leave the area
            if (NpcChat.talkToNPC(5278)) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                General.sleep(4500, 6000);
            }
        }
    }

    public void talkToGarkor() {
        if (Inventory.find(TENTH_SQUAD_SIGIL).length < 1 && ISLAND_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Talking to Garkor";
            Walking.blindWalkTo(garkorTile);
            PathingUtil.movementIdle();
            //handle little cut scene bs (Idk if the varbit for cutscenes works here)
            if (NpcChat.talkToNPC("Garkor")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                General.sleep(General.random(3000, 6000));
                Utils.cutScene();

                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                NPCInteraction.handleConversation();
                NPCInteraction.handleConversation();
                NPCInteraction.handleConversation();
                General.sleep(General.random(3000, 6000));
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                NPCInteraction.handleConversation();
                NPCInteraction.handleConversation();
                General.sleep(General.random(3000, 6000));
                Utils.closeQuestCompletionWindow();
                if (Interfaces.get(225, 13) != null) {
                    Interfaces.get(225, 13).click();
                    Utils.idle(300, 800);
                }
            }

            // talk to again to get the sigil
            if (NpcChat.talkToNPC("Garkor")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
        }
    }


    public void getItemsFinalFight() {
        if (Inventory.find(TENTH_SQUAD_SIGIL).length > 0 &&
                !BOSS_AREA.contains(Player.getPosition())) {
            BankManager.open(true);
            BankManager.depositAll(true);
            BankManager.withdraw(500, true, ItemID.CANNONBALL);
            BankManager.withdraw(4, true, PRAYER_POTION[0]);
            BankManager.withdraw(8, true, lobster);
            BankManager.withdraw(1, true, ItemID.RUNE_SCIMITAR);
            BankManager.withdraw(1, true, 8);
            BankManager.withdraw(1, true, 6);
            BankManager.withdraw(1, true, 10);
            BankManager.withdraw(1, true, 12);
            BankManager.withdraw(1, true, ItemID.ANTIDOTE_PLUS_PLUS);
            BankManager.withdrawArray(ItemID.AMULET_OF_GLORY, 1);
            Utils.equipItem(ItemID.AMULET_OF_GLORY);
            Utils.equipItem(ItemID.RUNE_SCIMITAR);
            BankManager.withdraw(1, true, TENTH_SQUAD_SIGIL);
            BankManager.close(true);
        }
    }

    RSArea BOSS_AREA = new RSArea(new RSTile(2696, 9206, 1), new RSTile(2736, 9166, 1));
    // RSArea BOSS_AREA = new RSArea(new RSTile(2711, 9179, 1), 25);
    int SIGIL = TENTH_SQUAD_SIGIL;
    RSArea SOUTH_WEST_CORNER = new RSArea(new RSTile(2696, 9174, 1), new RSTile(2703, 9167, 1));

    public void fightDemon() {
        RSItem[] cannonBase = Inventory.find(6);
        if (cannonBase.length == 0) {
            Log.log("Missing cannon, ending");
            cQuesterV2.taskList.remove(this);
            return;
        }

        if (!BOSS_AREA.contains(Player.getPosition())) {
            RSItem[] invItem1 = Inventory.find(SIGIL);
            Combat.setAutoRetaliate(false);
            if (Prayer.getPrayerPoints() <= 15 && Utils.drinkPotion(ItemID.PRAYER_POTION))
                General.sleep(General.randomSD(400, 900, 600, 100));

            if (invItem1.length > 0 && invItem1[0].click("Wear")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Yes");
                General.sleep(9000, 12000);
            }
            Prayer.enable(Prayer.PRAYERS.PROTECT_FROM_MAGIC);
            Timer.waitCondition(NPCInteraction::isConversationWindowUp, 19000, 22000);
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            Timer.waitCondition(() -> NPCs.findNearest("Jungle demon").length > 0, 6000, 9000);
        }
        if (BOSS_AREA.contains(Player.getPosition())) {

            RSNPC[] jungleDemon = NPCs.findNearest("Jungle demon");
            while (jungleDemon.length > 0) {
                General.sleep(50, 100);
                int prayOn = General.random(10, 25);
                // just in case we had it on for some reason
                Combat.setAutoRetaliate(false);


                if (Prayer.getPrayerPoints() <= prayOn) {
                    prayOn = General.randomSD(5, 20, 12, 4);
                    if (Utils.drinkPotion(ItemID.PRAYER_POTION))
                        General.sleep(General.randomSD(400, 900, 600, 100));
                }

                // too close to demon, can die to melee
                if (jungleDemon[0].getPosition().distanceTo(Player.getPosition()) < 5) {
                    Log.log("[Debug]: Moving away from demon");
                    if (!SOUTH_WEST_CORNER.contains(jungleDemon[0])) {
                        PathingUtil.localNavigation(new RSTile(2697, 9169, 1));
                        PathingUtil.movementIdle();
                    } else {
                        PathingUtil.localNavigation(new RSTile(2715, 9169, 1));
                        PathingUtil.movementIdle();
                    }
                }

                // set up cannon if we have one in our inventory
                cannonBase = Inventory.find(6);
                if (cannonBase.length > 0 && cannonBase[0].click("Set-up")) {
                    Timer.waitCondition(() -> Objects.findNearest(20, 6).length > 0, 9000, 12000);
                    Waiting.waitUniform(2000, 3500);
                }


                //fire cannon
                RSObject[] cannon = Objects.findNearest(20, 6);
                if (cannon.length > 0 && cannon[0].click("Fire")) {
                    //using a different var here so it's effectively final for lambda
                    int finalPrayOn = prayOn;
                    Timer.waitCondition(() -> NPCs.findNearest("Jungle demon").length < 1
                            || Prayer.getPrayerPoints() <= finalPrayOn, 25000, 60000);
                    // variable timeout as we don't want to click the cannon too often (it only fires once a rotation)
                }
                // update variable for loop
                jungleDemon = NPCs.findNearest("Jungle demon");
            }


            jungleDemon = NPCs.findNearest("Jungle demon");
            if (jungleDemon.length < 1) {
                // killed demon, now get cannon before leaving
                RSObject[] setUpCannon = Objects.findNearest(20, 6);
                if (setUpCannon.length > 0 && setUpCannon[0].click("Pick-up")) {
                    cQuesterV2.status = "Picking up Cannon";
                    Timer.waitCondition(() -> Inventory.find(ItemID.CANNON_IDS[0]).length > 0, 5000, 8000);
                    Prayer.disable(Prayer.PRAYERS.PROTECT_FROM_MAGIC);
                }

                // I don't think this conversation is needed. You can just go right to the king
             /*   if (Inventory.find(ItemID.CANNON_IDS[0]).length > 0) {
                    if (NpcChat.talkToNPC("Garkor")) {
                        NPCInteraction.waitForConversationWindow();
                        NPCInteraction.handleConversation();
                    }
                    if (NpcChat.talkToNPC("Zooknock")) {
                        NPCInteraction.waitForConversationWindow();
                        NPCInteraction.handleConversation();
                        General.sleep(General.random(3000, 5000));
                        DaxWalker.walkToBank();
                    }*/
            }
        }
    }

    public void finishQuest() {
        if (Objects.findNearest(20, 6).length < 1) {
            cQuesterV2.status = "Finishing Quest";
            General.println("[Debug]: Going to the King");
            PathingUtil.walkToTile(kingTile);
            General.sleep(General.random(2000, 4000));
            if (NpcChat.talkToNPC("King Narnode Shareen")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Yes");
                NPCInteraction.handleConversation();
                Timer.waitCondition(() -> Interfaces.get(225, 14) != null, 3500, 5000);

                // closes quest window (its a unique interface)
                RSInterface iface = Interfaces.get(225, 14);
                if (iface != null) {
                    iface.click();
                }
            }
        }
    }

    public void getXPReward(String reward) {
        cQuesterV2.status = "Going to Daero";
        General.println("[Debug]: Going to Daero.");
        PathingUtil.walkToTile(daeroTile);

        if (NpcChat.talkToNPC("Daero")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            NPCInteraction.handleConversation(reward);
            NPCInteraction.handleConversation();
        }
    }

    RSTile[] ESCAPE_AFTER_AMULET = {
            new RSTile(2806, 2785, 0),
            new RSTile(2806, 2786, 0),
            new RSTile(2805, 2787, 0),
            new RSTile(2804, 2788, 0),
            new RSTile(2803, 2788, 0),
            new RSTile(2802, 2788, 0),
            new RSTile(2801, 2788, 0),
            new RSTile(2801, 2789, 0),
            new RSTile(2801, 2790, 0),
            new RSTile(2800, 2790, 0),
            new RSTile(2799, 2790, 0),
            new RSTile(2798, 2790, 0),
            new RSTile(2797, 2790, 0),
            new RSTile(2796, 2790, 0),
            new RSTile(2795, 2790, 0),
            new RSTile(2794, 2790, 0),
            new RSTile(2793, 2789, 0),
            new RSTile(2792, 2788, 0),
            new RSTile(2791, 2788, 0),
            new RSTile(2790, 2788, 0),
            new RSTile(2789, 2788, 0),
            new RSTile(2788, 2788, 0),
            new RSTile(2787, 2787, 0),
            new RSTile(2786, 2786, 0),
            new RSTile(2785, 2786, 0),
            new RSTile(2784, 2786, 0)
    };

    @Override
    public boolean checkRequirements() {
        if (Game.getSetting(0) < 11) {
            General.println("[Debug]: Dwarf cannon is incomplete.");
            return false;
        }
        if (Skills.getActualLevel(Skills.SKILLS.PRAYER) < 43) {
            General.println("[Debug]: Prayer level <43.");
            return false;
        }
        return true;
    }

    @Override
    public void execute() {

        if (DeathsOffice.shouldHandleDeath()) {
            cQuesterV2.taskList.remove(this);
            return;
        }
        if (Game.getSetting(365) == 0) {
            buyItems();
            getStartItems();
            talkToKing();
        }

        if (Game.getSetting(365) == 1) {
            getMonkeyBones();
            goToGloCarancock(); //goes to Caranock
            talkingToCaranock();
        } else if (Utils.getVarBitValue(122) < 3) { // this varbit changes from 0->1->2->3 upon talking
            goToGloCarancock(); //goes to Caranock
            talkingToCaranock();
        } else if (Game.getSetting(365) == 2) {
            General.println("Varbut 123  = " + RSVarBit.get(123).getValue());
            if (RSVarBit.get(123).getValue() <= 4) {
                talkToKing();
                step4Part2Banking(); // banking
                step5Part2TalkToDaero(); // going to Daero
                chat();
            }
            if (RSVarBit.get(123).getValue() == 4) {
                step6Part2(); // talking to Daero and waydar
            }
            if (RSVarBit.get(123).getValue() == 5) {
                goToGlough();
                solvePuzzle();
            }
            if (RSVarBit.get(123).getValue() == 6) {
                talkToDaeroAndWaydar(); // talking to Daero and waydar
            }
            if (RSVarBit.get(123).getValue() == 7 && RSVarBit.get(124).getValue() == 0) {
                talkToDaeroAndWaydar();
            }
            if (RSVarBit.get(123).getValue() == 7 && RSVarBit.get(124).getValue() == 1) {
                talkToLumbdo();
            }
            if (RSVarBit.get(125).getValue() == 2 && RSVarBit.get(123).getValue() == 7 && RSVarBit.get(125).getValue() == 2) { // changes to 3 after this.
                step9Part2TalkToWaydar(); // talking to waydar on beach
            }
            if (Game.getSetting(1021) == 16576) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                Utils.modSleep();
            }
        }
        if (Game.getSetting(365) == 3) {
            if (RSVarBit.get(123).getValue() == 7 && RSVarBit.get(124).getValue() == 1
                    && RSVarBit.get(125).getValue() == 3 && RSVarBit.get(126).getValue() == 0) {
                getCaptured(); // gets captured
                escapePrison();
                goToKaram();
            }

            if (RSVarBit.get(123).getValue() == 7 && RSVarBit.get(124).getValue() == 1
                    && RSVarBit.get(125).getValue() == 3 && RSVarBit.get(126).getValue() < 2
                    && RSVarBit.get(127).getValue() == 0 && RSVarBit.get(128).getValue() == 1) {
                step4Ch2(); // RSVARBIT 126 is 0 at start, and changes to 1  part way through convo , and 2 near end of conversation with Garkor
            }

            if (RSVarBit.get(123).getValue() == 7 && RSVarBit.get(124).getValue() == 1
                    && RSVarBit.get(125).getValue() == 3 && RSVarBit.get(126).getValue() == 2
                    && RSVarBit.get(127).getValue() < 5 && RSVarBit.get(128).getValue() == 1) {
                escapePrison(); // just in case you get captured
                step5Ch2(); // getting dentures (126:2 and 128:1 as trigger for this step) and those after this one.
                goToAmuletMould(); // dropping down and getting mould
                getAmuletMould();
                bankAfterMouldAndDentures(); // will only bank if you have dentures adn mould
                goToZookNock();
                getEnchantedBar();
            }

            if (RSVarBit.get(123).getValue() == 7 && RSVarBit.get(124).getValue() == 1
                    && RSVarBit.get(125).getValue() == 3 && RSVarBit.get(126).getValue() == 2
                    && RSVarBit.get(127).getValue() == 5 && RSVarBit.get(128).getValue() == 1) {
                if (Inventory.find(monkeyTalisman).length < 1) {
                    getEnchantedBar();   //need a check in case it loops after getting talisman
                    getItems3();
                    getCaptured();
                    escapePrison();
                    navigatingMeleeMonkeys();
                    equipMSpeakAmulet();
                    getBananas();
                    talkToMonkeyChild();
                }
                getItems4();
                returnToZooknock(); // will have mspeak and talisman at this stage
            }
            if (Game.getSetting(1021) == 16608) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
        }
        if (Game.getSetting(365) == 4) {
            if (RSVarBit.get(123).getValue() == 7 && RSVarBit.get(124).getValue() == 1
                    && RSVarBit.get(125).getValue() == 3 && RSVarBit.get(126).getValue() == 2
                    && RSVarBit.get(127).getValue() == 6 && RSVarBit.get(128).getValue() == 1) {
                //buyGreegrees(); // not completed
                stepX11(); //RSVarbit 126 changes from 2-4 after talking to elder guard
            }
            if (RSVarBit.get(123).getValue() == 7 && RSVarBit.get(124).getValue() == 1
                    && RSVarBit.get(125).getValue() == 3 && RSVarBit.get(126).getValue() == 4
                    && RSVarBit.get(127).getValue() == 6 && RSVarBit.get(128).getValue() == 1) {
                stepX12(); // goes to awowogei and kruk
                stepX13(); // nothing changes after talking to awowogei - talks to awowogei
                stepX14(); // goes to zoo and gets monkey
                stepX15(); // leaves monkey cage
                moveToBeach();
                stepX17(); // 16 & 17 are returning to awowogei with the monkey
                stepX18();
                stepX13();
                stepX19();
                talkToGarkor();
            }
        }
        if (Game.getSetting(365) == 5) {
            getItemsFinalFight(); // banks
            fightDemon();
        }
        if (Game.getSetting(365) == 6) {
            finishQuest();
        }
        if (Game.getSetting(365) == 8) {
            finishQuest();
            NPCInteraction.handleConversation();
        }
        if (Game.getSetting(365) == 9) {
            Utils.closeQuestCompletionWindow();
            Utils.continuingChat();
            getXPReward(attackAndDef);

        }
        if (Game.getSetting(365) == 10) {
            Utils.continuingChat();
            cQuesterV2.taskList.remove(this);
        }
    }


    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return cQuesterV2.taskList.get(0).equals(this);
    }

    @Override
    public String questName() {
        return "Monkey Madness I (" + Game.getSetting(365) + ")";
    }
}
