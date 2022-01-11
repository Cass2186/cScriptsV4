package scripts.QuestPackages.HeroesQuest;

import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;

public class HeroesQuestConst {

    public static RSArea taverleyDungeon = new RSArea(new RSTile(2816, 9668, 0), new RSTile(2973, 9855, 0));
    public static RSArea  deepTaverleyDungeon1 = new RSArea(new RSTile(2816, 9856, 0), new RSTile(2880, 9760, 0));
    public static  RSArea  deepTaverleyDungeon2 = new RSArea(new RSTile(2880, 9760, 0), new RSTile(2907, 9793, 0));
    public static  RSArea  deepTaverleyDungeon3 = new RSArea(new RSTile(2889, 9793, 0), new RSTile(2923, 9815, 0));
    public static  RSArea  deepTaverleyDungeon4 = new RSArea(new RSTile(2907, 9772, 0), new RSTile(2928, 9793, 0));
    public static RSArea  jailCell = new RSArea(new RSTile(2928, 9683, 0), new RSTile(2934, 9689, 0));
    public static  RSArea  phoenixEntry = new RSArea(new RSTile(3239, 9780, 0), new RSTile(3249, 9786, 0));
    public static  RSArea  phoenixBase = new RSArea(new RSTile(3232, 9761, 0), new RSTile(3254, 9785, 0));
    public static  RSArea  garden1 = new RSArea(new RSTile(2783, 3194, 0), new RSTile(2797, 3199, 1));
    public static  RSArea  garden2 = new RSArea(new RSTile(2780, 3188, 0), new RSTile(2786, 3196, 0));
    public static  RSArea  secretRoom = new RSArea(new RSTile(2780, 3197, 0), new RSTile(2782, 3198, 0));
    public static  RSArea treasureRoom = new RSArea(new RSTile(2764, 3196, 0), new RSTile(2769, 3199, 0));
    public static  RSArea iceEntrance = new RSArea(new RSTile(2840, 3512, 0), new RSTile(2851, 3522, 0));

    public static RSArea  iceRoom1P1 = new RSArea(new RSTile(2811, 9897, 0), new RSTile(2841, 9908, 0));
    public static  RSArea  iceRoom1P2 = new RSArea(new RSTile(2832, 9907, 0), new RSTile(2853, 9965, 0));
    public static  RSArea  iceRoom1P3 = new RSArea(new RSTile(2811, 9907, 0), new RSTile(2824, 9942, 0));

    public static  RSArea iceRoom2P1 = new RSArea(new RSTile(2825, 9909, 0), new RSTile(2829, 9970, 0));
    public static  RSArea iceRoom2P2 = new RSArea(new RSTile(2854, 9908, 0), new RSTile(2878, 9918, 0));
    public static  RSArea  iceRoom2P3 = new RSArea(new RSTile(2878, 9920, 0), new RSTile(2899, 9977, 0));
    public static  RSArea iceRoom2P4 = new RSArea(new RSTile(2826, 9965, 0), new RSTile(2888, 9977, 0));

    public static   RSArea iceUp1P1 = new RSArea(new RSTile(2799, 3500, 0), new RSTile(2811, 3515, 0));
    public static   RSArea iceUp1P2 = new RSArea(new RSTile(2811, 3503, 0), new RSTile(2828, 3514, 0));
    public static   RSArea  iceUp1P3 = new RSArea(new RSTile(2811, 3514, 0), new RSTile(2823, 3518, 0));
    public static   RSArea  iceUp1P4 = new RSArea(new RSTile(2823, 3514, 0), new RSTile(2825, 3516, 0));

    public static RSArea  iceUp2 = new RSArea(new RSTile(2852, 3515, 0), new RSTile(2862, 3522, 0));

    public static  RSArea  iceThrone1 = new RSArea(new RSTile(2857, 9919, 0), new RSTile(2873, 9965, 0));
    public static  RSArea iceThrone2 = new RSArea(new RSTile(2874, 9937, 0), new RSTile(2879, 9965, 0));
    public static  RSArea    iceThrone3 = new RSArea(new RSTile(2860, 9917, 0), new RSTile(2866, 9918, 0));

    public static  RSArea entrana = new RSArea(new RSTile(2798, 3327, 0), new RSTile(2878, 3394, 1));

    public static RSArea START_AREA_ROCK = new RSArea(new RSTile(2838, 3517, 0), new RSTile(2836, 3520, 0));
    public static RSArea POST_ROCK_SLIDE = new RSArea(new RSTile(2839, 3512, 0), new RSTile(2853, 3521, 0));
    public static RSArea ICE_LAIR = new RSArea(new RSTile(2901, 9898, 0), new RSTile(2808, 9978, 0));

    public static RSArea LADDER_2_AREA = new RSArea(new RSTile(2822, 9909, 0), new RSTile(2825, 9905, 0));

    public static RSTile[] PATH_FROM_LADDER1_TO_LADDER2 = {
            new RSTile(2847, 9912, 0),
            new RSTile(2842, 9913, 0),
            new RSTile(2837, 9907, 0),
            new RSTile(2832, 9901, 0),
            new RSTile(2825, 9899, 0),
            new RSTile(2818, 9902, 0),
            new RSTile(2820, 9907, 0),
            new RSTile(2823, 9907, 0),
            new RSTile(2824, 9906, 0)
    };

    public static RSArea FROM_LADDER1_TO_LADDER2 = new RSArea(
            new RSTile[] {
                    new RSTile(2852, 9915, 0),
                    new RSTile(2832, 9915, 0),
                    new RSTile(2827, 9908, 0),
                    new RSTile(2825, 9910, 0),
                    new RSTile(2817, 9910, 0),
                    new RSTile(2818, 9895, 0),
                    new RSTile(2849, 9898, 0)
            }
    );

    public static RSArea ABOVE_GROUND_LADDER_CLUSTER_3 = new RSArea(new RSTile(2861, 3515, 0), new RSTile(2855, 3521, 0));

    public static RSArea ABOVE_GROUND_LADDER_CLUSTER_2 = new RSArea(new RSTile(2829, 3505, 0), new RSTile(2820, 3513, 0));
    public static RSArea LAIR_AREA_BEFORE_PATH1 = new RSArea(
            new RSTile[] {
                    new RSTile(2830, 9910, 0),
                    new RSTile(2827, 9908, 0),
                    new RSTile(2825, 9910, 0),
                    new RSTile(2825, 9920, 0),
                    new RSTile(2830, 9920, 0)
            }
    );

    public static RSArea FINAL_LANDING_AREA= new RSArea(
            new RSTile[] {
                    new RSTile(2863, 9915, 0),
                    new RSTile(2856, 9918, 0),
                    new RSTile(2856, 9923, 0),
                    new RSTile(2872, 9923, 0)
            }
    );


    public static final RSTile[] PATH_1 = new RSTile [] { new RSTile(2826, 9910, 0),new RSTile(2825, 9913, 0),new RSTile(2826, 9916, 0),new RSTile(2827, 9919, 0),new RSTile(2827, 9922, 0),new RSTile(2828, 9926, 0),new RSTile(2827, 9929, 0),new RSTile(2827, 9932, 0),new RSTile(2826, 9935, 0),new RSTile(2826, 9938, 0),new RSTile(2826, 9941, 0),new RSTile(2826, 9944, 0),new RSTile(2826, 9947, 0),new RSTile(2826, 9950, 0),new RSTile(2826, 9953, 0),new RSTile(2827, 9956, 0),new RSTile(2827, 9959, 0),new RSTile(2829, 9962, 0),new RSTile(2829, 9965, 0),new RSTile(2830, 9968, 0),new RSTile(2833, 9970, 0),new RSTile(2836, 9970, 0),new RSTile(2839, 9970, 0),new RSTile(2842, 9970, 0),new RSTile(2845, 9969, 0),new RSTile(2848, 9969, 0),new RSTile(2851, 9969, 0),new RSTile(2854, 9969, 0),new RSTile(2857, 9970, 0),new RSTile(2860, 9972, 0),new RSTile(2863, 9972, 0),new RSTile(2866, 9972, 0),new RSTile(2869, 9972, 0),new RSTile(2872, 9972, 0),new RSTile(2875, 9971, 0),new RSTile(2878, 9969, 0),new RSTile(2881, 9966, 0),new RSTile(2884, 9963, 0),new RSTile(2884, 9960, 0),new RSTile(2885, 9957, 0),new RSTile(2887, 9954, 0),new RSTile(2889, 9951, 0),new RSTile(2891, 9948, 0),new RSTile(2891, 9945, 0),new RSTile(2891, 9942, 0),new RSTile(2890, 9939, 0),new RSTile(2887, 9936, 0),new RSTile(2886, 9933, 0),new RSTile(2883, 9930, 0),new RSTile(2880, 9927, 0),new RSTile(2878, 9924, 0),new RSTile(2876, 9921, 0),new RSTile(2875, 9918, 0),new RSTile(2873, 9915, 0),new RSTile(2869, 9912, 0),new RSTile(2866, 9911, 0),new RSTile(2863, 9911, 0),new RSTile(2860, 9911, 0),new RSTile(2858, 9914, 0),new RSTile(2858, 9917, 0) };
    public static  RSTile SAFE_TILE = new RSTile(2876, 9954,0);
    public static RSArea QUEEN_AREA = new RSArea(new RSTile(2854, 9965, 0), new RSTile(2878, 9948, 0));
    public static  RSArea WHOLE_QUEEN_AREA = new RSArea(
            new RSTile[] {
                    new RSTile(2862, 9915, 0),
                    new RSTile(2857, 9918, 0),
                    new RSTile(2854, 9960, 0),
                    new RSTile(2865, 9966, 0),
                    new RSTile(2876, 9962, 0),
                    new RSTile(2881, 9952, 0),
                    new RSTile(2883, 9947, 0)
            }
    );


}
