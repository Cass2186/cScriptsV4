package scripts.Data;

import scripts.Timer;

public class SkillerTimers {

    private static SkillerTimers vars;

    public static SkillerTimers get() {
        return vars == null ? vars = new SkillerTimers() : vars;
    }

    private int getMsFromSeconds(int seconds){
        return seconds*1000;
    }

    private int getMsFromMin(int min){
        return getMsFromSeconds(min*60);
    }

    public Timer birdHouseTimer = new Timer(getMsFromMin(50));
    public Timer herbTimer = new Timer(getMsFromMin(80));
    public Timer allotmentTimer = new Timer(getMsFromMin(60)); //this actually depends on the crop
}
