package scripts.Tasks;


import org.tribot.api.General;
import org.tribot.api2007.Combat;
import org.tribot.api2007.Login;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import org.tribot.api2007.util.DPathNavigator;
import scripts.Data.Const;
import scripts.Data.CrabTile;
import scripts.Data.Vars;
import scripts.PathingUtil;
import scripts.Utils;


public class ResetAggro implements Task {


    public static DPathNavigator nav = new DPathNavigator();


    public static void determineResetArea(RSTile crabTile) {
        if (crabTile.equals(Const.NORTH_WEST1)) {
            Vars.get().crabResetArea = Const.SE_RESET_AREA;

        } else if (crabTile.equals(Const.NORTH_WEST2)) {
            Vars.get().crabResetArea = Const.SE_RESET_AREA;

        } else if (crabTile.equals(Const.WEST)) {
            Vars.get().crabResetArea = Const.SE_RESET_AREA;

        } else if (crabTile.equals(Const.SOUTH_WEST)) {
            Vars.get().crabResetArea = Const.NE_RESET_AREA;

        } else if (crabTile.equals(Const.SOUTH)) {
            Vars.get().crabResetArea = Const.NW_RESET_AREA;

        } else if (crabTile.equals(Const.SOUTH_EAST1)) {
            Vars.get().crabResetArea = Const.NW_RESET_AREA;

        } else if (crabTile.equals(Const.SOUTH_EAST2)) {
            Vars.get().crabResetArea = Const.NW_RESET_AREA;

        } else if (crabTile.equals(Const.NORTH_EAST1)) {
            Vars.get().crabResetArea = Const.SW_RESET_AREA;

        } else if (crabTile.equals(Const.NORTH_EAST2)) {
            Vars.get().crabResetArea = Const.SW_RESET_AREA;

        } else if (crabTile.equals(CrabTile.FOSSIL_ISLAND_1.getTile())) {
            Vars.get().crabResetArea = CrabTile.FOSSIL_ISLAND_1.getResetArea();
        }
    }

    public static void moveToResetTile(RSArea resetArea) {
        if (Login.getLoginState() != Login.STATE.INGAME) {
            Vars.get().task = "Logging in";
            Login.login();
        }

        nav.setAcceptAdjacent(true);

        if (!Combat.isUnderAttack() && Player.getAnimation() == -1 &&
                Player.getRSPlayer().getInteractingCharacter() == null) { // double checks to make sure you're actually not in combat
            Vars.get().task = "Resetting Aggression";
            General.println("[Reset Aggro]: Resetting Aggression");
            //attempt to feed a path to dax before trying to us dax to generate the path
            PathingUtil.walkToTile(resetArea.getRandomTile());
            if (!resetArea.contains(Player.getPosition()))
                PathingUtil.walkToArea(resetArea, false);

        }
        Vars.get().shouldResetAggro = false;

    }


    @Override
    public String toString() {
        return "Resetting aggression";
    }

    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        if (!org.tribot.script.sdk.Login.isLoggedIn()){
            Login.login();
            Utils.shortSleep();
        }
        return //Login.getLoginState().equals(Login.STATE.INGAME) &&
                (Vars.get().shouldResetAggro || Fight.checkAggro());
    }

    @Override
    public void execute() {
        if (!org.tribot.script.sdk.Login.isLoggedIn()){
            Login.login();
            Utils.shortSleep();
        }
        if (Vars.get().crabResetArea == null) {
            determineResetArea(Vars.get().crabTile);
        }
        moveToResetTile(Vars.get().crabResetArea);
    }

}

