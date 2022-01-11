package scripts.Tasks.Combat.CrabTasks;


import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.Combat;
import org.tribot.api2007.Login;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSTile;

import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.PathingUtil;
import scripts.Tasks.Combat.Data.Const;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Tasks.Combat.Data.CrabVars;
import scripts.Timer;
import scripts.Utils;
import scripts.cSkiller;


public class MoveToCrabTile implements Task {

    public void moveToCrabTile(RSTile crabTile) {
        if (!Combat.isAutoRetaliateOn()) {
            cSkiller.status = "Setting auto-retaliate";
            General.println("[Debug]: Setting auto-retaliate");
            Combat.setAutoRetaliate(true);
        }

        if (!Const.SANDCRAB_ISLAND.contains(Player.getPosition())) {
            cSkiller.status = "Going to Crabclaw Isle";
            PathingUtil.walkToArea(Const.BEACH_AREA, false);
            if (Utils.clickNPC(Const.SANDICRAHB_NPC, "Travel")) {
                NPCInteraction.waitForConversationWindow();
                Timer.waitCondition(() -> Const.SANDCRAB_ISLAND.contains(Player.getPosition()), 10000);
            }
        }

        if (!Player.getPosition().equals(crabTile)) {
            cSkiller.status = "Moving to Crab Tile";

            if (Const.SANDCRAB_ISLAND.contains(Player.getPosition())) {
                cSkiller.status = "Moving to Crab Tile - local walking";
                PathingUtil.localNavigation(crabTile);
            }
            for (int i = 0; i < 3; i++) {
                if (!crabTile.isOnScreen())
                    PathingUtil.walkToTile(crabTile, 1, true);

                else if (crabTile.isOnScreen() && Walking.clickTileMS(crabTile, "Walk here"))
                    Timer.waitCondition(() -> Player.getPosition().equals(crabTile), 4500, 7500);

                if (Player.getPosition().equals(crabTile))
                    break;
            }
        }

    }


    @Override
    public String toString() {
        return "Moving to Crab Tile";
    }

    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        if (Vars.get().currentTask != null &&
                Vars.get().currentTask.equals(SkillTasks.ATTACK) ||
                Vars.get().currentTask.equals(SkillTasks.STRENGTH) ||
                Vars.get().currentTask.equals(SkillTasks.DEFENCE) ||
                Vars.get().currentTask.equals(SkillTasks.RANGED)) {
            return Login.getLoginState().equals(Login.STATE.INGAME) &&
                    !Player.getPosition().equals(CrabVars.get().crabTile);
        }
        return false;
    }

    @Override
    public void execute() {
        moveToCrabTile(CrabVars.get().crabTile);
    }

    @Override
    public String taskName() {
        return "Combat Training";
    }

}

