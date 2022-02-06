package scripts.Data;

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

    public boolean shouldBank = true;

    public boolean shouldRestock = false;

    public int clickDelay = 400;

    public String status = "Initializing...";

    public long currentTime, nextCollectionTime;

    public long startTime;

    public Timer nextRunTimer = new Timer(3*10^6);
}
