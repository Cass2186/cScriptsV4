package scripts.Data;


import org.tribot.script.sdk.Inventory;
import org.tribot.script.sdk.query.Query;
import scripts.Data.Enums.Patches;
import scripts.Timer;

import java.util.ArrayList;

public class Vars {


    private static Vars vars;

    public static Vars get() {
        return vars == null ? vars = new Vars() : vars;
    }

    public boolean doingTithe = Query.inventory().nameContains("Watering can").isAny();

    public static void reset() {
        vars = new Vars();
    }

    public ArrayList<Patches> patchesLeftToVisit = new ArrayList<>();

    public int startInvValue;

    public boolean scriptStatus = true;

    public boolean shouldBank = true;

    public boolean shouldBreak = false;

    public boolean shouldRestock = false;

    public boolean starting = true;

    public boolean usingBottomless = false;

    public boolean usingMagicSecateurs = false;

    public boolean doingTrees = false;

    public boolean doingHerbs = false;

    public boolean doingFruitTrees = false;

    public boolean doingAllotments = false;// change to un

    public int currentHerbId, treeId, fruitTreeId;
    public int currentAllotmentId;


    public String currentHerbName;

    /**
     * Strings
     */
    public String target = null;

    public String status = "Initializing...";

    public long currentTime;

    public long startTime;

    public Timer herbTimer, treeTimer, allotmentTimer, flowerTimer;

}
