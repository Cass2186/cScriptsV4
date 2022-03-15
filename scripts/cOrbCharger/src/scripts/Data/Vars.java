package scripts.Data;

import org.tribot.api.General;
import org.tribot.api2007.Skills;
import org.tribot.script.sdk.Skill;
import scripts.Timer;

import java.util.HashMap;

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

    public final long startTime = System.currentTimeMillis();

    public long lastAction = System.currentTimeMillis();

    /**
     * Integers
     */
    public int xpHr = 0;
    public int add = General.random(5, 10);
    public int drinkAt = General.random(7, 25);
    public int eatAt = General.random(3, 10);
    public double drinkPrayAtPercentage =General.randomSD(6, 65, 40, 12);
    public int eatRockCakeAt =   General.randomSD(2, 6, 3, 2);

    public int drinkAbsorptionAt = General.random(100, 250);
    public int drinkAbsorptionUpTo = General.random(400, 800);
    public HashMap<Skill, Integer> skillStartXpMap = new HashMap<>();


    /**
     * Booleans
     */
    public boolean usingAbsorptions = false;
    public  boolean usingPrayerPots = false;
    public  boolean usingSuperCombat = false;
    public boolean usingOverloadPots = false;
    public boolean isOverloadActive = false;

    public Timer overloadTimer = new Timer(0);  //5 min
}
