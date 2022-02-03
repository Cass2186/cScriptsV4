package scripts.Tasks.Farming.Data;


import scripts.Tasks.Farming.Data.Enums.FARM_TASKS;
import scripts.Tasks.Farming.Data.Enums.Patches;
import scripts.Timer;

import java.util.ArrayList;
import java.util.List;

public class FarmVars {


    private static FarmVars vars;

    public static FarmVars get() {
        return vars == null ? vars = new FarmVars() : vars;
    }



    public static void reset() {
        vars = new FarmVars();
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

    public boolean doingHerbs= false;

    public boolean doingFruitTrees= false;

    public boolean doingAllotments= false;// change to un

    public int currentHerbId, treeId, fruitTreeId;
    public int currentAllotmentId;

    public List<FARM_TASKS> farmTaskList = new ArrayList<>();

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
