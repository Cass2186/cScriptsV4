package scripts.Data;

import lombok.Data;
import net.sourceforge.jdistlib.Uniform;
import org.tribot.api.General;
import org.tribot.api2007.Game;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Skills;
import org.tribot.script.sdk.*;
import scripts.AntiBan;
import scripts.ItemID;
import scripts.ScriptUtils.ScriptTimer;
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


    public boolean onBreak = false;
    public int breakNumber = 0;
    public long totalBreakLength = 0;
    public long currentBreakLength = 0;

    // gets updated on break start
    // only displayed as a static number when we are breaking
    public long runningTimeWithBreaks = -1;
    public Timer breakTimer = new Timer(0);
    public Timer scriptTimer = new Timer(Long.MAX_VALUE);

    public ScriptTimer scriptTimerNew = new ScriptTimer();

    // returns runtime with breaks factored in
    // if we haven't breaked before, it will just return the difference between current and start time
    // if we have had a break (and therefore cached a time), it will return that;
    public long getRunningTime() {
        if (onBreak) {
            // Log.info("On break MS: "  +(System.currentTimeMillis() - (startTime + totalBreakLength)));
            //   Log.info("Run timewith breaks" + runningTimeWithBreaks);
            return runningTimeWithBreaks != -1 ? runningTimeWithBreaks :
                    (System.currentTimeMillis() - (startTime + (breakTimer.getElapsed())));
        }
        return
                (System.currentTimeMillis() - startTime - totalBreakLength);

    }

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
    public int pestControlPoints = 0;
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
    public boolean useSummerPieBoost = Skill.AGILITY.getActualLevel() >= 75;
    public int useSummerPieBoostWithinXLevels = 5;
    public int marksCollected = 0;
    public boolean donePriestInPeril = GameState.getSetting(302) >= 61;
    public boolean shouldAlchAgil = false;
    public boolean overridingCourse = false;
    public boolean growKittenDuringAgility = false;
    public boolean spamClickAgility = true;

    //crafting
    public boolean doingDragonHide = Skill.CRAFTING.getActualLevel() > 62;

    //firemaking
    public int firemakingTargetLevel = 30;
    public boolean shouldResetFireMaking = true;

    //KOUREND FAVOUR
    public boolean hosaFavour = false;
    public boolean shouldCashInCompost = false;

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
    public boolean makeOrbs = Skill.MAGIC.getActualLevel() > 66;

    //mining
    public int oreDeposits = 0;

    //mining
    public int prayerBonesId = 0;

    //woodcutting
    public List<WcLocations> wcLocationsList = new ArrayList<>(List.of(
            WcLocations.VARROCK_WEST_TREES,
            WcLocations.VARROCK_WEST_OAKS,
            WcLocations.PORT_SARIM_WILLOWS,
            WcLocations.SEERS_MAPLES
    ));

    // Prayer
    public boolean useWildernessAltar = true;

    // other
    public boolean getDefenders = false;

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

  //  public SlayerVars slayVars = SlayerVars.get();
}
