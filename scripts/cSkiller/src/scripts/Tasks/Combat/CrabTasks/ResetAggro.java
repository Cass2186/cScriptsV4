package scripts.Tasks.Combat.CrabTasks;


import org.tribot.api.General;
import org.tribot.api2007.Combat;
import org.tribot.api2007.Login;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.PathingUtil;
import scripts.Tasks.Combat.Data.Const;
import scripts.Tasks.Combat.Data.CrabTile;
import scripts.Tasks.Combat.Data.CrabVars;
import scripts.cSkiller;

public class ResetAggro implements Task {


    public static void determineResetArea(RSTile crabTile) {
        if (crabTile.equals(Const.NORTH_WEST1)) {
            CrabVars.get().crabResetArea = Const.SE_RESET_AREA;

        } else if (crabTile.equals(Const.NORTH_WEST2)) {
            CrabVars.get().crabResetArea = Const.SE_RESET_AREA;

        } else if (crabTile.equals(Const.WEST)) {
            CrabVars.get().crabResetArea = Const.SE_RESET_AREA;

        } else if (crabTile.equals(Const.SOUTH_WEST)) {
            CrabVars.get().crabResetArea = Const.NE_RESET_AREA;

        } else if (crabTile.equals(Const.SOUTH)) {
            CrabVars.get().crabResetArea = Const.NW_RESET_AREA;

        } else if (crabTile.equals(Const.SOUTH_EAST1)) {
            CrabVars.get().crabResetArea = Const.NW_RESET_AREA;

        } else if (crabTile.equals(Const.SOUTH_EAST2)) {
            CrabVars.get().crabResetArea = Const.NW_RESET_AREA;

        } else if (crabTile.equals(Const.NORTH_EAST1)) {
            CrabVars.get().crabResetArea = Const.SW_RESET_AREA;

        } else if (crabTile.equals(Const.NORTH_EAST2)) {
            CrabVars.get().crabResetArea = Const.SW_RESET_AREA;

        } else if (crabTile.equals(CrabTile.FOSSIL_ISLAND_1.getTile())) {
            CrabVars.get().crabResetArea = CrabTile.FOSSIL_ISLAND_1.getResetArea();
        }
    }

    public static void moveToResetTile(RSArea resetArea) {
        General.sleep(4500,7000);
        if (Login.getLoginState() != Login.STATE.INGAME) {
            cSkiller.status = "Logging in";
            Login.login();
        }
        if (!Combat.isUnderAttack() && Player.getAnimation() == -1 && Player.getRSPlayer().getInteractingCharacter() == null) { // double checks to make sure you're actually not in combat
            cSkiller.status = "Resetting Aggression";
            General.println("[Reset Aggro]: Resetting Aggression");
            //attempt to feed a path to dax before trying to us dax to generate the path
            PathingUtil.localNavigation(resetArea.getRandomTile());
            if (!resetArea.contains(Player.getPosition()))
                PathingUtil.walkToArea(resetArea, false);

        }
        CrabVars.get().shouldResetAggro = false;
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
        if (Vars.get().currentTask != null &&
                Vars.get().currentTask.equals(SkillTasks.ATTACK) ||
                Vars.get().currentTask.equals(SkillTasks.STRENGTH) ||
                Vars.get().currentTask.equals(SkillTasks.DEFENCE) ||
                Vars.get().currentTask.equals(SkillTasks.RANGED)) {
            return Login.getLoginState().equals(Login.STATE.INGAME) &&
                    (CrabVars.get().shouldResetAggro || Fight.checkAggro());
        }
        return false;
    }

    @Override
    public void execute() {
        if (CrabVars.get().crabResetArea == null) {
            determineResetArea(CrabVars.get().crabTile);
        }
        moveToResetTile(CrabVars.get().crabResetArea);
    }

    @Override
    public String taskName() {
        return "Training Combat";
    }

}

