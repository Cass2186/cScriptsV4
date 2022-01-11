package scripts.Tasks;


import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSPlayer;
import org.tribot.api2007.types.RSTile;
import scripts.Data.Vars;
import scripts.PathingUtil;
import scripts.Timer;

public class HopWorlds implements Task {

    public boolean checkPlayers(RSTile crabTile) {
        RSPlayer[] nearbyPlayers = Players.find(Filters.Players.inArea(new RSArea(crabTile, 1)));
        if (nearbyPlayers.length > 1 && crabTile.equals(Player.getPosition())) { // 1 is me, so >1 = others
            General.sleep(General.random(20000, 30000));
            nearbyPlayers = Players.find(Filters.Players.inArea(new RSArea(crabTile, 1)));
            if (nearbyPlayers.length > 1 && !nearbyPlayers[0].equals(Player.getRSPlayer())) {
                General.println("[Debug]: Hopping = true");
                return true;
            } else {
                General.println("[Debug]: Hopping = false");
                return false;
            }
        } else if (nearbyPlayers.length > 0 && !crabTile.equals(Player.getPosition())) {
            General.println("[Debug]: Hopping = true");
            return true;
        }
       // General.println("[Debug]: Hopping = false");
        return false;
    }


    public void hopWorlds() {
        if (PathingUtil.walkToTile(new RSTile(1766, 3420, 0), 2, true))
            Timer.waitCondition(() -> !Player.isMoving() && !Combat.isUnderAttack(), 9000, 12000);

        int world = WorldHopper.getRandomWorld(true, false);
        General.println("[Debug]: Hopping worlds due to crash.");
        Vars.get().hops++;
        WorldHopper.changeWorld(world);
        Timer.waitCondition(() -> Login.getLoginState() == Login.STATE.INGAME, 15000, 25000);
        General.sleep(General.random(5000, 8000));
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

