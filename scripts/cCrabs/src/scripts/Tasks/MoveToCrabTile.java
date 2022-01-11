package scripts.Tasks;


import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.Combat;
import org.tribot.api2007.Login;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSTile;
import scripts.Data.Const;
import scripts.Data.Vars;
import scripts.PathingUtil;
import scripts.Timer;
import scripts.Utils;

public class MoveToCrabTile implements Task {

    public void moveToCrabTile(RSTile crabTile) {
        if (!Combat.isAutoRetaliateOn()) {
            Vars.get().task = "Setting auto-retaliate";
            General.println("[Debug]: Setting auto-retaliate");
            Combat.setAutoRetaliate(true);
        }

        if (!Const.SANDCRAB_ISLAND.contains(Player.getPosition())) {
            Vars.get().task = "Going to Crabclaw Isle";
            PathingUtil.walkToArea(Const.BEACH_AREA);
            if (Utils.clickNPC(Const.SANDICRAHB_NPC, "Travel")) {
                NPCInteraction.waitForConversationWindow();
                Timer.waitCondition(() -> Const.SANDCRAB_ISLAND.contains(Player.getPosition()), 10000);
            }
        }

        if (!Player.getPosition().equals(crabTile)) {
            Vars.get().task = "Moving to Crab Tile";

            if (Const.SANDCRAB_ISLAND.contains(Player.getPosition())) {
                Vars.get().task = "Moving to Crab Tile - local walking";
                if(PathingUtil.localNavigation(Vars.get().crabTile))
                PathingUtil.movementIdle();
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
        return  Login.getLoginState().equals(Login.STATE.INGAME) &&
                !Player.getPosition().equals(Vars.get().crabTile);
    }

    @Override
    public void execute() {
        moveToCrabTile(Vars.get().crabTile);
    }

}

