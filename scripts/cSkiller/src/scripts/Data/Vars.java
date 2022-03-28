package scripts.Data;

import net.sourceforge.jdistlib.Uniform;
import org.tribot.api.General;
import org.tribot.api2007.Game;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Skills;
import scripts.AntiBan;
import scripts.ItemID;
import scripts.Tasks.Magic.Alch;
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
    public int skillSwitchMin = 12000000; //45min //1600000;// ~26m
    public int skillSwitchMax = 24000000;
    public Timer skillSwitchTimer = new Timer(General.random(skillSwitchMin, skillSwitchMax)); //~26-45m //General.random(2400000, 4200000)

    public HashMap<Skills.SKILLS, Integer> skillStartXpMap = new HashMap<>();

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

    public int abc2Chance = General.random(0, 25);
    public int mouseSpeed = General.random(120, 140);
    public long currentTime = System.currentTimeMillis();
    public boolean isOnBreak = false;
    public long breakLength = 0;

    //firemaking
    public int firemakingTargetLevel = 30;
    public boolean shouldResetFireMaking = true;

    public boolean useMLM = false;
    public boolean smeltCannonballs = true;

    public boolean switchingTasks = false;

    //fishing
    public boolean getBarbarianRod = false; //TODO implement this in fishing tasks

    //cooking
    public boolean doingWines = Skills.getActualLevel(Skills.SKILLS.COOKING) >= 35;

    //Thieving
    public boolean useFruitStalls = true;

    //Magic
    public int clickAllJeweleryChance = General.random(25,45);
    public boolean preferJeweleryOverTeleports = false;

    //mining
    public int oreDeposits = 0;

    //afk stuff
    public int afkDurationAvg = General.random(20000,40000); //30-40s
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

    public int marksCollected = 0;
    public int eatAt = General.random(30,65);

    public int miniBreakFrequencyAvg = 2700; //45m
    public int miniBreakFrequencySD = 3600; //60m
    public Timer miniBreakTimer = new Timer(General.randomSD(miniBreakFrequencyAvg*1000, miniBreakFrequencySD*1000));

    public DaxTracker daxTracker;


    public boolean shouldShowGUI = true;

    public boolean donePriestInPeril = Game.getSetting(302) >= 61;
    public boolean afkMode = false;
    public boolean shouldAlchAgil = false;
    public boolean overridingCourse = false;

    public Alch.AlchItems alchItem = Alch.AlchItems.AIR_BATTLESTAFF;

    public List<CombatTask> combatTaskList = new ArrayList<>();
}
