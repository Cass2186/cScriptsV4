package scripts.Data;

import lombok.Data;
import net.sourceforge.jdistlib.Uniform;
import org.tribot.api.General;
import org.tribot.api2007.Game;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Skills;
import org.tribot.script.sdk.GameState;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.MyPlayer;
import org.tribot.script.sdk.Waiting;
import scripts.AntiBan;
import scripts.ItemID;
import scripts.Tasks.Fishing.Locations.FishingLocation;
import scripts.Tasks.Magic.Alch;
import scripts.Tasks.Slayer.SlayerUtils.SlayerVars;
import scripts.Tasks.Woodcutting.WoodcuttingData.WcLocations;
import scripts.Timer;
import scripts.dax.tracker.DaxTracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Vars {

    private static Vars vars;

    public static Vars get() {
        return vars == null ? vars = new Vars() : vars;
    }


    public static void reset() {
        vars = new Vars();
    }



    public SkillTasks currentTask, prevTask;


    public HashMap<Skills.SKILLS, Integer> skillStartXpMap = new HashMap<>();

    //TODO replace all that references these with the values in Const
    public int startAgilityXp = Skills.getXP(Skills.SKILLS.AGILITY);
    public int startCraftingXp = Skills.getXP(Skills.SKILLS.CRAFTING);
    public int startConstructionXp = Skills.getXP(Skills.SKILLS.CONSTRUCTION);
    public int startCoookingXp = Skills.getXP(Skills.SKILLS.COOKING);
    public int startFishingXp = Skills.getXP(Skills.SKILLS.FISHING);
    public int startHerbloreXp = Skills.getXP(Skills.SKILLS.HERBLORE);
    public int startFiremakingXp = Skills.getXP(Skills.SKILLS.FIREMAKING);
    public int startMagicXp = Skills.getXP(Skills.SKILLS.MAGIC);
    public int startMiningXp = Skills.getXP(Skills.SKILLS.MINING);
    public int startWoodcuttingXp = Skills.getXP(Skills.SKILLS.WOODCUTTING);
    public int startHunterXp = Skills.getXP(Skills.SKILLS.HUNTER);

    // Numbers
    public int abc2Chance = General.random(0, 25);
    public int mouseSpeed = General.random(120, 140);
    public long currentTime = System.currentTimeMillis();
    public long breakLength = 0;
    public int skillSwitchMin = 12000000; //45min //1600000;// ~26m
    public int skillSwitchMax = 24000000;
    public Timer skillSwitchTimer = new Timer(General.random(skillSwitchMin, skillSwitchMax)); //~26-45m //General.random(2400000, 4200000)
    public long startTime = System.currentTimeMillis();

    //booleans
    public boolean isOnBreak = false;
    public boolean switchingTasks = false;

    public boolean shouldShowGUI = true;


    //afk stuff
    public int afkDurationAvg = General.random(20000, 40000); //30-40s
    public int afkDurationSD = 20000; //20s SD
    public int afkFrequencyAvg = 1200000; //every 20 min
    public int afkFrequencySD = 180000; //3m SD
    public boolean moveMouseOffScreenAfk = true;
    public Timer afkTimer = new Timer(General.randomSD(afkFrequencyAvg, afkFrequencySD));
    public boolean doGarden = false;

    //Minibreak vars IN SECONDS
    public int miniBreakDurationAvg = 480; //8m
    public int miniBreakDurationSD = 210; //3.5m
    public int miniBreakDurationMin = 120; //2m
    public int miniBreakDurationMax = 1200;//20


    public int eatAt = General.random(30, 65);

    public int miniBreakFrequencyAvg = 2700; //45m
    public int miniBreakFrequencySD = 3600; //60m
    public Timer miniBreakTimer = new Timer(General.randomSD(miniBreakFrequencyAvg * 1000, miniBreakFrequencySD * 1000));


    public boolean afkMode = false;


    //agility
    public boolean useWildernessAgility = true; //TODO currently only goes if you start at the course and <70
    public int useWildernessAgilityUntilLevel = 70;
    public boolean useSummerPieBoost = false;
    public int useSummerPieBoostWithinXLevels = 5;
    public int marksCollected = 0;
    public boolean donePriestInPeril = GameState.getSetting(302) >= 61;
    public boolean shouldAlchAgil = false;
    public boolean overridingCourse = false;
    public boolean growKittenDuringAgility = false;

    //firemaking
    public int firemakingTargetLevel = 30;
    public boolean shouldResetFireMaking = true;


    //MINING
    public boolean useMLM = false;


    //SMITHING
    public boolean smeltCannonballs = true;


    //fishing
    public boolean getBarbarianRod = false; //TODO implement this in fishing tasks
    public FishingLocation fishingLocation;
    public boolean growKittenDuringFishing = false;

    //cooking
    public boolean doingWines = Skills.getActualLevel(Skills.SKILLS.COOKING) >= 35;

    //Thieving
    public boolean useFruitStalls = true;

    //Magic
    public int clickAllJeweleryChance = General.random(25, 45);
    public boolean preferJeweleryOverTeleports = false;

    //mining
    public int oreDeposits = 0;

    //woodcutting
    public List<WcLocations> wcLocationsList = new ArrayList<>(List.of(
            WcLocations.VARROCK_WEST_TREES,
            WcLocations.VARROCK_WEST_OAKS,
            WcLocations.PORT_SARIM_WILLOWS,
            WcLocations.SEERS_MAPLES
    ));

    public DaxTracker daxTracker;



    // PAINT
    public boolean showTimers = false;
    public boolean showSlayerInfo = false;
    public boolean showExperienceGained = true;

    public Alch.AlchItems alchItem = Alch.AlchItems.AIR_BATTLESTAFF;

    public List<CombatTask> combatTaskList = new ArrayList<>();


    /**
     * SLAYER
     */

    public SlayerVars slayVars = SlayerVars.get();
}
