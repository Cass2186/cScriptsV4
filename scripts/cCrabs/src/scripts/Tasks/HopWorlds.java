package scripts.Tasks;


import org.tribot.api.General;
import org.tribot.api2007.Login;
import org.tribot.api2007.Player;
import org.tribot.api2007.Players;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSPlayer;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.MyPlayer;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.WorldHopper;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.Area;
import org.tribot.script.sdk.types.World;
import scripts.Data.Vars;
import scripts.PathingUtil;
import scripts.Timer;
import scripts.Utils;

import java.util.List;
import java.util.Optional;

public class HopWorlds implements Task {

    private int getPlayerCount(RSTile crabTile) {
        return Query.players().inArea(Area.fromRadius(Utils.getWorldTileFromRSTile(crabTile), 1))
                .nameNotContains(MyPlayer.getUsername())
                .toList().size();
    }

    public boolean checkPlayers(RSTile crabTile) {
        if (getPlayerCount(crabTile) > 0) {
            if (!Waiting.waitUntil(12500, 500, () -> getPlayerCount(crabTile) == 0)) {
                Log.info("Should hop");
                return getPlayerCount(crabTile) > 0;
            }
        }
        return false;
    }


    public void hopWorlds() {
        if (PathingUtil.walkToTile(new RSTile(1766, 3420, 0), 2, true))
            Timer.waitCondition(() -> !MyPlayer.isMoving() &&
                    !MyPlayer.isHealthBarVisible(), 11000, 15000);

        Optional<World> world = Query.worlds().isNotCurrentWorld().isMembers()
                .isNotAllTypes(World.Type.PVP, World.Type.SKILL_TOTAL)
                .isType(World.Type.MEMBERS)
                .findRandom();
        General.println("[Debug]: Hopping worlds due to crash.");
        Vars.get().hops++;
        if (world.map(w -> WorldHopper.hop(w.getWorldNumber())).orElse(false))
            Vars.get().shouldHopPlayer = false;

    }


    @Override
    public String toString() {
        return "Hopping worlds";
    }

    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        if (Login.getLoginState().equals(Login.STATE.INGAME) && Vars.get().crabTile != null)
            return checkPlayers(Vars.get().crabTile);

        return false;
    }

    @Override
    public void execute() {
        if (checkPlayers(Vars.get().crabTile)) {
            hopWorlds();
        }
    }

}

