package scripts.Data;

import net.sourceforge.jdistlib.T;
import org.tribot.script.sdk.Skill;
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
    public Timer safetyTimer = new Timer(450000);
    public int lastIterRangedXp = 0;
    public int gainedRangedXp = 0;

    public int getGainedRangedXp() {
        return Skill.RANGED.getXp() - Const.START_RANGED_XP;
    }

    public boolean onBreak = false;
    public int breakNumber = 0;
    public long totalBreakLength = 0;

    // gets updated on break start
    // only displayed as a static number when we are breaking
    public long runningTimeWithBreaks = -1;


    // returns runtime with breaks factored in
    // if we haven't breaked before, it will just return the difference between current and start time
    // if we have had a break (and therefore cached a time), it will return that;
    public long getRunningTime() {
        if (breakNumber > 1) {
            return onBreak ? runningTimeWithBreaks :
                    (System.currentTimeMillis() - (startTime + totalBreakLength));
        }
        return onBreak ? runningTimeWithBreaks : (System.currentTimeMillis() - startTime);
    }

    public int getKillsHr() {
        return (int) (kills
                / ((double) (getRunningTime() / 3600000)));
    }


    /**
     * Strings
     */
    public String target = null;

    public String status = "Initializing";

    public long currentTime;

    public long startTime;

    public boolean useStamina = true;

    public int kills = 0;


}
