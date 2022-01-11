package scripts.Tasks.Combat.Data;

import org.tribot.api.General;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import scripts.AntiBan;
import scripts.Data.Vars;

public class CrabVars {

    private static CrabVars CrabVars;
    public RSArea crabResetArea;

    public static CrabVars get() {
        return CrabVars == null ? CrabVars=  new CrabVars() : CrabVars;
    }


    public static void reset() {
        CrabVars = new CrabVars();
    }

    public long currentTime = System.currentTimeMillis();
    public int hops = 0;
    public int eatAt = AntiBan.getEatAt();
    public int add = General.random(2, 5);
    public boolean shouldHopPlayer = false;
    public boolean shouldBank = false;
    public boolean shouldResetAggro = false;
    public boolean shouldEat = false;
    public boolean shouldDrink = false;
    public boolean shouldMoveToCrabTile = false;
    public boolean usingFossilIsland = false;
    public boolean usingPrayer = false;

    public RSTile crabTile = CrabTile.SOUTH_CRAB_CLAW_ISLE.getTile();

}
