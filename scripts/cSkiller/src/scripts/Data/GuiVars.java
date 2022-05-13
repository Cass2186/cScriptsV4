package scripts.Data;

import org.tribot.api.General;
import scripts.Timer;

public class GuiVars {

    /**
     * Meant to just hold the GUI Settings
     * will contain most of what Vars contains that is MODIFIABLE
     * Then on load, fetch the items from here, which will then be saved to the Vars class on start
     */

    private static GuiVars vars;

    public static GuiVars get() {
        return vars == null ? vars = new GuiVars() : vars;
    }


    public static void reset() {
        vars = new GuiVars();
    }

    // Numbers
    public int abc2Chance;
    public int mouseSpeed;
    public long currentTime;
    public long breakLength;
    public int skillSwitchMin; //45min //1600000;// ~26m
    public int skillSwitchMax;


    //afk stuff
    public int afkDurationAvg;
    public int afkDurationSD;
    public int afkFrequencyAvg;
    public int afkFrequencySD;
    public boolean moveMouseOffScreenAfk;
    public boolean doGarden;

    //Minibreak vars IN SECONDS
    public int miniBreakDurationAvg ; //8m
    public int miniBreakDurationSD; //3.5m
    public int miniBreakDurationMin; //2m
    public int miniBreakDurationMax;//20


}
