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

    public long getRunningTime(){
        return System.currentTimeMillis() - startTime;
    }

    public int getKillsHr(){
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
