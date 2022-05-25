package scripts.Data;

import org.tribot.api.General;
import org.tribot.script.sdk.GameState;
import org.tribot.script.sdk.Skill;
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

    //agility
    public boolean useWildernessAgility = true; //TODO currently only goes if you start at the course and <70
   // public int useWildernessAgilityUntilLevel = 70;
    public boolean useSummerPieBoost = Skill.AGILITY.getActualLevel() >= 75;
    public int useSummerPieBoostWithinXLevels = 5;
    public boolean donePriestInPeril = GameState.getSetting(302) >= 61;
    public boolean shouldAlchAgil = false;
    public boolean overridingCourse = false;
    public boolean growKittenDuringAgility = false;
    public boolean spamClickAgility = true;

    //crafting
    public boolean doingDragonHide = Skill.CRAFTING.getActualLevel() > 62;


    //KOUREND FAVOUR
    public boolean hosaFavour = false;
    public boolean shouldCashInCompost = false;

    //MINING
    public boolean useMLM = false;

    //SMITHING
    public boolean smeltCannonballs = true;


}
