package scripts.Data;

import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;

public class Const {

    public enum LOG_ACTIONS{
        DROP,
        FLETCH_ARROW_SHAFTS,
        FLETCH_BOW_DROP,
        LIGHT_FIRES

    }

    public static final long startTime = System.currentTimeMillis();

    public static int FISHING_ANIMATION[] = {
            621, // net fishing
            623 // rod fishing
    };

    public static int[] FISH_IDS = {
            317, // raw shrimp
            335, // raw trout
            331, // raw salmon
            333, // cooked trout
            329, // cooked salmon
            343, // burnt fish
    };

    public static RSArea CATHERBY_FISHING_AREA = new RSArea(
            new RSTile[]{
                    new RSTile(2833, 3436, 0),
                    new RSTile(2833, 3430, 0),
                    new RSTile(2846, 3429, 0),
                    new RSTile(2852, 3423, 0),
                    new RSTile(2865, 3423, 0),
                    new RSTile(2865, 3429, 0)
            }
    );

    public static int FLY_FISHING_ROD = 509;
    public static int FEATHER = 314;

    public static   RSArea LUMBRIDGE_FISHING_AREA = new RSArea(new RSTile(3241, 3153, 0), 15);
    public static RSArea BARBARIAN_OUTPOST = new RSArea(new RSTile(3100, 3435, 0), new RSTile(3110, 3422, 0));
    public static RSArea CATHERBY_BANK_AND_COOK_AREA = new RSArea(new RSTile(2820, 3437, 0), new RSTile(2804, 3446, 0));


    public static final int startAgilityLvl = Skills.getActualLevel(Skills.SKILLS.AGILITY);
    public static final int startCraftingLvl= Skills.getActualLevel(Skills.SKILLS.CRAFTING);
    public static final int startConstructionLvl= Skills.getActualLevel(Skills.SKILLS.CONSTRUCTION);
    public static final int startCoookingLvl= Skills.getActualLevel(Skills.SKILLS.COOKING);
    public static final int startFishingLvl= Skills.getActualLevel(Skills.SKILLS.FISHING);
    public static final int startHerbloreLvl= Skills.getActualLevel(Skills.SKILLS.HERBLORE);
    public static final int startFiremakingLvl= Skills.getActualLevel(Skills.SKILLS.FIREMAKING);
    public static final int startMagicLvl= Skills.getActualLevel(Skills.SKILLS.MAGIC);
    public static final int startMiningLvl= Skills.getActualLevel(Skills.SKILLS.MINING);
    public static final int startWoodcuttingLvl= Skills.getActualLevel(Skills.SKILLS.WOODCUTTING);
    public static final int startHunterLvl= Skills.getActualLevel(Skills.SKILLS.HUNTER);
    public static final int startPrayerLvl = Skills.getActualLevel(Skills.SKILLS.PRAYER);
}
