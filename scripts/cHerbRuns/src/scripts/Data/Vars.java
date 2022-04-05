package scripts.Data;

import org.tribot.script.sdk.Skill;
import org.tribot.script.sdk.query.Query;
import scripts.ItemID;
import scripts.Timer;
import scripts.Utils;

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


    public String status = "Initializing";

    public long currentTime;

    public final long startTime = System.currentTimeMillis();

    public long lastAction = System.currentTimeMillis();

    public long getRunTime() {
        return System.currentTimeMillis() - startTime;
    }

    public int getProfitHr() {
        double timeRanMin = ((double) this.getRunTime() / 3600000);
        int hr = (int) (getProfit() / timeRanMin);
        //Log.debug("Hourly: " + hr);
        return Math.max(hr, 0);
    }

    public int getProfit() {
        return Utils.getInventoryValue() - Const.START_VALUE;
    }

    public String getProfitString() {
        return Utils.addCommaToNum(getProfit()) + " | " + Utils.addCommaToNum(getProfitHr()) + "/hr";
    }

    public List<String> houseBlackListNames = new ArrayList<>();

    /**
     * Integers
     */
    public int profit = Utils.getInventoryValue() - Const.START_VALUE;
    public int messageCount = 0;
    public int lastIterFarmXp =  Skill.FARMING.getXp();

    public int getGainedFarmXp() {
        return Skill.FARMING.getXp() - Const.START_FARM_XP;
    }


    //2.5 min timer for xp gain
    public Timer safetyTimer = new Timer(150000);

    public boolean useStamina = true;
    public boolean showDetailedPaint = false;


}
