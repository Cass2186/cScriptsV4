package scripts.Data;

import org.tribot.api.General;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import org.tribot.api2007.util.DPathNavigator;
import org.tribot.script.Script;
import org.tribot.script.sdk.Skill;
import org.tribot.script.sdk.types.Area;
import org.tribot.script.sdk.types.GameObject;
import org.tribot.script.sdk.types.LocalTile;
import scripts.Timer;

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
    public long startTime = System.currentTimeMillis();
    public int lastIterXp = Skill.RANGED.getXp();
    public Optional<GameObject> caveObject = null;
    public Optional<LocalTile> caveObjectTile = null;

    public int getGainedRangedXp() {
        return Skill.RANGED.getXp() - Const.START_RANGED_XP;
    }


    //AREAS
    public Area endPhase = null;
    public  Area NW = null;
    public  Area C = null;
    public  Area SW = null;
    public  Area S = null;
    public  Area SE = null;

    public Optional<LocalTile> ITALY_ROCK_WEST = Optional.empty();
    public Optional<LocalTile> ITALY_ROCK_EAST = Optional.empty();

    //SAFESPOT STUFF
    public  List<LocalTile> nePath = null;
    public  List<LocalTile> nwPath = null;
    public  List<LocalTile> swPath = null;
    public  List<LocalTile> sePath = null;
    public  List<List<LocalTile>> allPaths = null;


    static DPathNavigator fightWalker = new DPathNavigator();

}
