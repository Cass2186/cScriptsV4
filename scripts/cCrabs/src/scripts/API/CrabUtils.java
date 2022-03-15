package scripts.API;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.MyPlayer;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.Area;
import org.tribot.script.sdk.types.Npc;
import org.tribot.script.sdk.types.WorldTile;
import scripts.Data.Const;
import scripts.Data.Vars;

import java.util.LinkedList;
import java.util.List;

public class CrabUtils {

    public static List<Npc> getAllSandCrabs() {
        return Query.npcs().idEquals(Const.SAND_CRAB_IDS).toList();
    }

    public static List<Npc> getAllSandyRocks() {
        return Query.npcs().idEquals(Const.SAND_ROCKS_IDS).toList();
    }

    /**
     * @return A box, use 1 for sandcrabs and 3 for ammonite
     */
    public static Area getAggroArea(int translateDistance) {
        return  Area.fromRectangle(MyPlayer.getPosition().translate(translateDistance, translateDistance),
                MyPlayer.getPosition().translate(-translateDistance, -translateDistance));
    }

    /**
     * @return If there are sandy rocks next to the player.
     */
    public static boolean sandyRocksNearby() {
        Area aggroArea = getAggroArea(1);
        return getAllSandyRocks().stream().anyMatch(aggroArea::contains);
    }

    /**
     * Checks to see if there are any sand crabs next to the player. Checks one tile away in each direction.
     *
     * @return Whether or not there are sand crabs next to the player.
     */
    public static boolean sandCrabsNearby() {
        Area aggroArea = getAggroArea(1);
        return getAllSandCrabs().stream().anyMatch(aggroArea::contains);
    }


    /**
     * Finds all the tiles of all sandy rocks nearby.
     *
     * @return All of the tiles of sandy rocks in a 3x3 box around the player.
     */
    public static WorldTile[] getSandyRockTilesNearby() {
        LinkedList<WorldTile> tiles = new LinkedList<>();
        Area aggroArea = getAggroArea(1);
        for (Npc npc : getAllSandyRocks())
            if (aggroArea.contains(npc))
                tiles.add(npc.getTile());
        return tiles.toArray(WorldTile[]::new);
    }

    public static boolean lostAgro(WorldTile crabTile) {
        Area area = Area.fromRadius(crabTile, 2);
        if (sandyRocksNearby() && area.contains(MyPlayer.getPosition())) {
            // wait to see if the rocks agro us
             if(!Waiting.waitUntil(General.random(1600,2000), CrabUtils::sandCrabsNearby)){
                 Log.debug("LOST AGRO");
                 return true;
             }

        }
        //General.println("[Debug]: Check aggro returning false");
        return false;

    }


}
