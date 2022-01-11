package scripts.Data;

import org.tribot.api.General;
import org.tribot.api2007.Game;
import org.tribot.api2007.Inventory;
import scripts.AgilityAPI.COURSES;
import scripts.AntiBan;
import scripts.ItemId;
import scripts.Timer;

public class Vars {

    private static Vars vars;

    public static Vars get() {
        return vars == null ? vars = new Vars() : vars;
    }


    public static void reset() {
        vars = new Vars();
    }

    public boolean scriptStatus = true;
    /**
     * Strings
     */
    public String target = null;

    public String status = "Initializing";

    public long currentTime;

    public long startTime;

    public int xpHr = 0;

    public boolean useStamina = true;

    public int mouseSpeed = General.random(105,115);

    public int eatAt = AntiBan.getEatAt();

    public boolean donePriestInPeril = Game.getSetting(302) >= 61;

    // are we using a course specified in args/GUI?
    public boolean overridingCourse = false;

    public COURSES course;

    public boolean useSummerPie = Inventory.find(Const.SUMMER_PIE).length > 0;

    public int marksCollected = 0;

    //for future arg support
    public int foodId = 0;
    public long timeToLevel = 0;
    public int marksHr = 0;

    public COURSES currentCourse;
    public int abc2Chance = General.random(15,35); // how often it will abc2 sleep after an obstacle

    /**
     * AFK INFO
     */

    public int afkFrequencyMin = 0;
    public int afkFrequencyMax = 0;
    public int afkDurationMin = 0;
    public int afkDurationMax = 0;


    public int afkMin =0;
    public int afkMax = 0;
    public int afkIntervalMin; //4min
    public int afkIntervalMax;//9min

    public boolean kittenMode = false;
    public boolean shouldAlch = false;// Inventory.find(ItemId.NATURE_RUNE).length > 0;

    public boolean afkMode = false;
    public Timer afkTimer;

}
