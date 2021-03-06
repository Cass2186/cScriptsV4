package scripts.Data;

import org.tribot.script.sdk.Skill;
import org.tribot.script.sdk.query.Query;
import scripts.ItemID;
import scripts.ScriptUtils.ScriptTimer;
import scripts.Timer;
import scripts.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

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
        double timeRanMin = ((double) ScriptTimer.getRuntime() / 3600000);
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

    //TODO add GUI and allow manual ending level (stored here)
    public int endRcLevel = 77;

    public int startRcLevel = 1;
    public int startRcXp = 1;
    public int lastIterRcXp = 1;

    //TODO add GUI and allow selection (stored here)
    public Optional<RunecraftItems> currentRune = Optional.empty();

    public int avgWaitLoopSleep = 200;
    public int sdWaitLoopSleep = 40;

    public boolean useStamina = true;

    public boolean lava = false;


    public boolean shouldAfk = false;


    public boolean usingLunarImbue = false;


    public boolean playingGuardians = false;

    public boolean isImbueActive = false;

    public boolean managedToPassAbyss = false;

    public boolean failedObstacle = false;

    public boolean abyssCrafting = Skill.RUNECRAFT.getActualLevel() >=90;

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
