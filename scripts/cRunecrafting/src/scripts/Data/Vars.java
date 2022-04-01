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


    //2.5 min timer for xp gain
    public Timer safetyTimer = new Timer(150000);

    public HashMap<Skill, Integer> skillStartXpMap = new HashMap<>();
    public int endRcLevel = 77;
    public int startRcLevel = 1;
    public int startRcXp = 1;
    public int lastIterRcXp = 1;

    public int avgWaitLoopSleep = 200;
    public int sdWaitLoopSleep = 40;

    public boolean useStamina = true;

    public boolean lava = false;

    public boolean usingPouches = false;

    public boolean shouldAfk = false;

    public boolean usingLunarImbue = false;

    public boolean isImbueActive = false;

    public boolean managedToPassAbyss = false;

    public boolean failedObstacle = false;

    public boolean abyssCrafting = false;

    public boolean needToRepairPouches = false;

    public boolean collectPouches = false;

    public boolean zanarisCrafting = false;

    public boolean mudRuneCrafting = false;

    public boolean useRingOfElements = Query.inventory()
            .idEquals(ItemID.CHARGED_RING_OF_ELEMENTS)
            .findClosestToMouse().isPresent();

    public boolean mindTiaraCrafting = false;

    public boolean goToBloodAltar = false;

    public boolean bloodRuneCrafting = false;

}
