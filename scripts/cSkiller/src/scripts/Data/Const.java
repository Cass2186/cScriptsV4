package scripts.Data;

import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.Skill;

import java.util.List;

public class Const {

    public enum LOG_ACTIONS {
        DROP,
        FLETCH_ARROW_SHAFTS,
        FLETCH_BOW_DROP,
        LIGHT_FIRES
    }

    public static final long startTime = System.currentTimeMillis();

    public static List<Integer> FISHING_ANIMATION = List.of(
            621, // net fishing
            623, // rod fishing
            9349,
            9350
    );

    public static RSArea OTTOS_GROTTO_FISHING_AREA = new RSArea(
            new RSTile[]{
                    new RSTile(2506, 3493, 0),
                    new RSTile(2504, 3497, 0),
                    new RSTile(2504, 3500, 0),
                    new RSTile(2500, 3505, 0),
                    new RSTile(2500, 3513, 0),
                    new RSTile(2504, 3517, 0),
                    new RSTile(2510, 3517, 0),
                    new RSTile(2510, 3520, 0),
                    new RSTile(2499, 3520, 0),
                    new RSTile(2498, 3517, 0),
                    new RSTile(2498, 3514, 0),
                    new RSTile(2496, 3511, 0),
                    new RSTile(2496, 3505, 0),
                    new RSTile(2499, 3499, 0),
                    new RSTile(2498, 3494, 0),
                    new RSTile(2498, 3491, 0)
            }
    );
    public static RSArea AL_KHARID_FISHING_AREA = new RSArea(
            new RSTile[]{
                    new RSTile(3265, 3149, 0),
                    new RSTile(3277, 3137, 0),
                    new RSTile(3282, 3141, 0),
                    new RSTile(3270, 3152, 0),
                    new RSTile(3266, 3151, 0)
            }
    );

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

    public static RSArea LUMBRIDGE_FISHING_AREA = new RSArea(new RSTile(3241, 3153, 0), 15);
    public static RSArea BARBARIAN_VILLAGE_FISHING_AREA = new RSArea(new RSTile(3100, 3435, 0), new RSTile(3110, 3422, 0));
    public static RSArea CATHERBY_BANK_AND_COOK_AREA = new RSArea(new RSTile(2820, 3437, 0), new RSTile(2804, 3446, 0));

    public static final int startAgilityXP = Skill.AGILITY.getXp();
    public static final int startAttXp = Skill.ATTACK.getXp();
    public static final int startConstructionXP = Skill.CONSTRUCTION.getXp();
    public static final int startCoookingXP = Skill.COOKING.getXp();
    public static final int startCraftingXP = Skill.CRAFTING.getXp();
    public static final int startDefXp = Skill.DEFENCE.getXp();
    public static final int startFiremakingXP = Skill.FIREMAKING.getXp();
    public static final int startFishingXP = Skill.FISHING.getXp();
    public static final int startHPXP = Skill.HITPOINTS.getXp();
    public static final int startHerbloreXP = Skill.HERBLORE.getXp();
    public static final int startHunterXP = Skill.HUNTER.getXp();
    public static final int startMagicXp = Skill.MAGIC.getXp();
    public static final int startMiningXP = Skill.MINING.getXp();
    public static final int startPrayerXP = Skill.PRAYER.getXp();
    public static final int startRangeXp = Skill.RANGED.getXp();
    public static final int startSlayerXP = Skill.SLAYER.getXp();
    public static final int startStrXp = Skill.STRENGTH.getXp();
    public static final int startWoodcuttingXP = Skill.WOODCUTTING.getXp();
    public static final int startFletchXp = Skill.FLETCHING.getXp();
    public static final int startSmithingXp = Skill.SMITHING.getXp();
    public static final int startThievingXp = Skill.THIEVING.getXp();

    public static final int startSmithingLvl = Skill.SMITHING.getActualLevel();
    public static final int startFletchLvl = Skill.FLETCHING.getActualLevel();
    public static final int startAgilityLvl = Skill.AGILITY.getActualLevel();
    public static final int startAttLvl = Skill.ATTACK.getActualLevel();
    public static final int startConstructionLvl = Skill.CONSTRUCTION.getActualLevel();
    public static final int startCoookingLvl = Skill.COOKING.getActualLevel();
    public static final int startCraftingLvl = Skill.CRAFTING.getActualLevel();
    public static final int startDefLvl = Skill.DEFENCE.getActualLevel();
    public static final int startFiremakingLvl = Skill.FIREMAKING.getActualLevel();
    public static final int startFishingLvl = Skill.FISHING.getActualLevel();
    public static final int startHPLvl = Skill.HITPOINTS.getActualLevel();
    public static final int startHerbloreLvl = Skill.HERBLORE.getActualLevel();
    public static final int startHunterLvl = Skill.HUNTER.getActualLevel();
    public static final int startMageLvl = Skill.MAGIC.getActualLevel();
    public static final int startMagicLvl = Skill.MAGIC.getActualLevel();
    public static final int startMiningLvl = Skill.MINING.getActualLevel();
    public static final int startPrayerLvl = Skill.PRAYER.getActualLevel();
    public static final int startRangeLvl = Skill.RANGED.getActualLevel();
    public static final int startSlayerLvl = Skill.SLAYER.getActualLevel();
    public static final int startStrLvl = Skill.STRENGTH.getActualLevel();
    public static final int startWoodcuttingLvl = Skill.WOODCUTTING.getActualLevel();
    public static final int startThievingLvl = Skill.THIEVING.getActualLevel();
}
