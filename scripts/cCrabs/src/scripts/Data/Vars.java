package scripts.Data;

import org.tribot.api.General;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import scripts.AntiBan;

import java.util.HashMap;

public class Vars {

    private static Vars vars;
    public RSArea crabResetArea;

    public static Vars get() {
        return vars == null ? vars = new Vars() : vars;
    }


    public static void reset() {
        vars = new Vars();
    }

    public boolean scriptStatus = true;
    public HashMap<Skills.SKILLS, Integer> skillStartXpMap = new HashMap<>();
    /**
     * Strings
     */
    public String target = null;

    public String task = "Initializing";

    public long currentTime;

    public long startTime;

    public int hops = 0;
    public int eatAt = General.random(35,65);
    public int add = General.random(2, 5);
    public boolean shouldHopPlayer = false;

    public boolean shouldBank = false;

    public boolean shouldResetAggro = false;
    public boolean shouldEat = false;
    public boolean shouldDrink = false;
    public boolean shouldMoveToCrabTile = false;
    public boolean usingFossilIsland = false;
    public boolean usingPrayer = false;
    public boolean ranging = false;

    public RSTile crabTile = CrabTile.determineClosestTile();

    public int startSlayerXP = Skills.getXP(Skills.SKILLS.SLAYER);
    public int startAttXp = Skills.getXP(Skills.SKILLS.ATTACK);
    public int startStrXp = Skills.getXP(Skills.SKILLS.STRENGTH);
    public int startDefXp = Skills.getXP(Skills.SKILLS.DEFENCE);
    public int startRangeXp = Skills.getXP(Skills.SKILLS.RANGED);
    public int startMageXp = Skills.getXP(Skills.SKILLS.MAGIC);
    public int startHPXP = Skills.getXP(Skills.SKILLS.HITPOINTS);


}
