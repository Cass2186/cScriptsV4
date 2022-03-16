package scripts.Tasks.BirdHouseRuns.Data;

import scripts.Timer;

public class BirdHouseVars {

    private static BirdHouseVars vars;

    public static BirdHouseVars get() {
        return vars == null ? vars = new BirdHouseVars() : vars;
    }


    public static void reset() {
        vars = new BirdHouseVars();
    }

    public boolean scriptStatus = true;

    public boolean shouldBank = true;

    public boolean shouldRestock = false;
    public boolean craftBirdhouses = true;

    public int currentBirdHouseId;

    public long currentTime, nextCollectionTime;

    public long startTime;

    public Timer nextRunTimer = new Timer(3*10^6);
}
