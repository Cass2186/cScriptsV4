package scripts.Data;

import org.tribot.api.General;
import org.tribot.api2007.Skills;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Skill;
import org.tribot.script.sdk.types.Widget;
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

    public boolean scriptStatus = true;
    /**
     * Strings
     */
    public String target = null;

    public String status = "Initializing";

    public long currentTime;

    public final long startTime = System.currentTimeMillis();

    public long lastAction = System.currentTimeMillis();

    public long getRunTime() {
        return System.currentTimeMillis() - startTime;
    }

    public int getProfitHr() {
        double timeRanMin = ((double) this.getRunTime() / 3600000);
        int hr = (int) (getProfit() /timeRanMin);
        //Log.debug("Hourly: " + hr);
        return Math.max(hr, 0);
    }

    public int getProfit(){
        return Utils.getInventoryValue() - Const.startInvValue;
    }

    public String getProfitString(){
        return Utils.addCommaToNum(getProfit()) + " | " + Utils.addCommaToNum(getProfitHr()) + "/hr";
    }

    public List<String> houseBlackListNames = new ArrayList<>();

    /**
     * Integers
     */
    public int profit = Utils.getInventoryValue() - Const.startInvValue;
    public int messageCount = 0;

    public boolean shouldMakeMostProfitableTab = false;

    public Tabs selectedTab = Tabs.VARROCK;

    //2.5 min timer for xp gain
    public Timer safetyTimer = new Timer(150000);

    public String mostRecentHouse = "";

    public HashMap<Skill, Integer> skillStartXpMap = new HashMap<>();

    public int startMagicLevel = 1;
    public int startMagicXp = 1;
    public int lastIterMagicXp = 1;


}
