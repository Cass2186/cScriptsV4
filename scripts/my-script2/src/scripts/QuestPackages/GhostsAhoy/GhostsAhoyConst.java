package scripts.QuestPackages.GhostsAhoy;

import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;

public class GhostsAhoyConst {


    public static int BEDSHEET = 4285;
    public static int BONE_KEY = 4272;
    public static int ROBES = 4247;
    public static int CHEST_KEY = 4273;


    public static final int CRONE_ID = 2996;
    public static final int NECROVARUS_ID = 2986;
    public static final int VELORINA_ID = 2985;
    public static int PRIEST_IN_PERIL_GAMESETTING = 302;
    public static int RESTLESS_GHOST_GAMESETTING = 107;
    public static int MAP_PIECE_1 = 4274;
    public static int MAP_PIECE_2 = 4276;
    public static int MAP_PIECE_3 = 4275;
    public static int COMPLETE_MAP = 4277;
    public static int BOOK_OF_HORACIO = 4248;
    public static int TRANSLATION_MANUAL = 4249;
    public static int SHEET = 4284;
    public static int GHOST_SHEET = 4285;
    public static int SIGNED_BOW = 4236;
    public static COLOR topHalf;
    public static COLOR bottomHalf;
    public static COLOR skull;

    public static enum COLOR {
        RED,
        PURPLE,
        BLUE,
        YELLOW,
        ORANGE,
        GREEN
    }

    public static RSArea BEFORE_BOAT_AREA = new RSArea(new RSTile(3603, 3540, 0), new RSTile(3611, 3537, 0));
    //   RSArea BOAT_AREA_PLANE_1 = new RSArea(new RSTile(3615, 3541, 1), new RSTile(3600, 3545, 1));
    public static RSArea BOAT_AREA_PLANE_2 = new RSArea(new RSTile(3623, 3541, 2), new RSTile(3616, 3545, 2));
    public static RSArea BOAT_AREA_PLANE_1 = new RSArea(new RSTile(3623, 3541, 1), new RSTile(3600, 3545, 1));
 //   public static RSArea BAR_AREA = new RSArea(new RSTile(3678, 3477, 0), new RSTile(3671, 3484, 0));
    public static   RSArea DRAGONTOOTH_ISLAND = new RSArea(new RSTile(3779, 3574, 0), new RSTile(3832, 3523, 0));
    public static RSArea BAR_AREA = new RSArea(new RSTile(3681, 3489, 0), new RSTile(3671, 3499, 0));
    public static RSTile START_TILE = new RSTile(3677, 3509, 0);
    public static RSTile CRONE_TILE = new RSTile(3462, 3558, 0);
    public static RSTile NECROVOUS_TILE = new RSTile(3659, 3515, 0);
    public static RSTile NETTLE_TILE = new RSTile(3089, 3472, 0); //edge yews

    public static RSArea SLIME_LEVEL1 = new RSArea(new RSTile(3682, 9889, 0), new RSTile(3686, 9886, 0));
    public static RSArea ECTOFUNTUS = new RSArea(new RSTile(3656, 3522, 0), new RSTile(3664, 3517, 0));
    public static RSArea BONE_CRUSHER_AREA = new RSArea(new RSTile(3656, 3526, 1), new RSTile(3663, 3523, 1));


    public static String[] START_DIALOG = {
            "Why, what is the matter?",
            "Yes, I do. It is a very sad story.",
            "Yes."
    };

    public static String[] CRONE_DIALOG = {"I'm here about Necrovarus."};
    public static String[] CRONE_2_DIALOG = {
            "I'm here about Necrovarus.",
            "You are doing so much for me - is there anything I can do for you?",
            "I am afraid I have lost the boat you gave to me."
    };
    public static String[] FINAL_NECROVARUS_DIALOG = {"Let any ghost who so wishes pass on into the next world."};

    public static  RSArea BOAT_CABIN_AREA = new RSArea(new RSTile(3616, 3545, 1), new RSTile(3622, 3541, 1));
    public static RSArea OUTSIDE_ENERGY_BARRIER = new RSArea(
            new RSTile[] {
                    new RSTile(3664, 3508, 0),
                    new RSTile(3656, 3508, 0),
                    new RSTile(3656, 3510, 0),
                    new RSTile(3664, 3510, 0)
            }
    );
    public static  RSArea PORT_PHYSMATIS = new RSArea(
            new RSTile[] {
                    new RSTile(3681, 3516, 0),
                    new RSTile(3677, 3516, 0),
                    new RSTile(3668, 3507, 0),
                    new RSTile(3661, 3507, 0),
                    new RSTile(3654, 3507, 0),
                    new RSTile(3653, 3506, 0),
                    new RSTile(3653, 3474, 0),
                    new RSTile(3650, 3471, 0),
                    new RSTile(3650, 3459, 0),
                    new RSTile(3652, 3457, 0),
                    new RSTile(3660, 3457, 0),
                    new RSTile(3662, 3455, 0),
                    new RSTile(3665, 3455, 0),
                    new RSTile(3666, 3454, 0),
                    new RSTile(3674, 3454, 0),
                    new RSTile(3676, 3456, 0),
                    new RSTile(3693, 3456, 0),
                    new RSTile(3695, 3454, 0),
                    new RSTile(3702, 3455, 0),
                    new RSTile(3710, 3458, 0),
                    new RSTile(3702, 3471, 0),
                    new RSTile(3712, 3489, 0),
                    new RSTile(3712, 3506, 0),
                    new RSTile(3693, 3507, 0),
                    new RSTile(3692, 3532, 0),
                    new RSTile(3688, 3533, 0),
                    new RSTile(3688, 3516, 0)
            }
    );

    public static   RSArea FINAL_ROCK_AREA = new RSArea(new RSTile(3608, 3561, 0), new RSTile(3601, 3566, 0));
}
