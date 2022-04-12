package scripts.Data;

import org.tribot.api2007.Skills;
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
            623 // rod fishing
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

    public static int startAgilityXP = Skill.AGILITY.getXp();
    public static int startAttXp = Skills.getXP(Skills.SKILLS.ATTACK);
    public static int startConstructionXP = Skill.CONSTRUCTION.getXp();
    public static int startCoookingXP = Skill.COOKING.getXp();
    public static int startCraftingXP = Skill.CRAFTING.getXp();
    public static int startDefXp = Skills.getXP(Skills.SKILLS.DEFENCE);
    public static int startFiremakingXP = Skill.FIREMAKING.getXp();
    public static int startFishingXP = Skill.FISHING.getXp();
    public static int startHPXP = Skills.getXP(Skills.SKILLS.HITPOINTS);
    public static int startHerbloreXP = Skill.HERBLORE.getXp();
    public static int startHunterXP = Skill.HUNTER.getXp();
    public static int startMagicXp = Skills.getXP(Skills.SKILLS.MAGIC);
    public static int startMiningXP = Skill.MINING.getXp();
    public static int startPrayerXP = Skill.PRAYER.getXp();
    public static int startRangeXp = Skills.getXP(Skills.SKILLS.RANGED);
    public static int startStrXp = Skills.getXP(Skills.SKILLS.STRENGTH);
    public static int startWoodcuttingXP = Skill.PRAYER.getXp();
    public static int startSlayerXP = Skills.getXP(Skills.SKILLS.SLAYER);


    public final static int startAttLvl = Skill.ATTACK.getActualLevel();
    public final static int startDefLvl = Skill.DEFENCE.getActualLevel();
    public final static int startHPLvl = Skill.HITPOINTS.getActualLevel();
    public final static int startMageLvl = Skill.MAGIC.getActualLevel();
    public final static int startRangeLvl = Skill.RANGED.getActualLevel();
    public final static int startSlayerLvl = Skill.SLAYER.getActualLevel();
    public final static int startStrLvl = Skill.STRENGTH.getActualLevel();
    public static final int startAgilityLvl = Skill.AGILITY.getActualLevel();
    public static final int startConstructionLvl = Skills.getActualLevel(Skills.SKILLS.CONSTRUCTION);
    public static final int startCoookingLvl = Skills.getActualLevel(Skills.SKILLS.COOKING);
    public static final int startCraftingLvl = Skills.getActualLevel(Skills.SKILLS.CRAFTING);
    public static final int startFiremakingLvl = Skills.getActualLevel(Skills.SKILLS.FIREMAKING);
    public static final int startFishingLvl = Skills.getActualLevel(Skills.SKILLS.FISHING);
    public static final int startHerbloreLvl = Skills.getActualLevel(Skills.SKILLS.HERBLORE);
    public static final int startHunterLvl = Skills.getActualLevel(Skills.SKILLS.HUNTER);
    public static final int startMagicLvl = Skills.getActualLevel(Skills.SKILLS.MAGIC);
    public static final int startMiningLvl = Skills.getActualLevel(Skills.SKILLS.MINING);
    public static final int startPrayerLvl = Skills.getActualLevel(Skills.SKILLS.PRAYER);
    public static final int startWoodcuttingLvl = Skills.getActualLevel(Skills.SKILLS.WOODCUTTING);
}
