package scripts.Tasks;

import dax.walker_engine.WalkerEngine;
import org.tribot.api.General;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import org.tribot.api2007.util.DPathNavigator;
import org.tribot.script.sdk.GameState;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.MyPlayer;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.interfaces.Positionable;
import org.tribot.script.sdk.types.Area;
import org.tribot.script.sdk.types.LocalTile;
import org.tribot.script.sdk.types.WorldTile;
import scripts.Data.Const;
import scripts.Data.Vars;
import scripts.QueryUtils;
import scripts.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class GenerateMap implements Task {

    //  PrayerEvent prayerEvent;



    DPathNavigator fightWalker = new DPathNavigator();
    List<RSTile> path = null;

    public static LocalTile getTileInDirection(int angle, int radius) {
        WorldTile currentTile = MyPlayer.getTile();
        double x = radius * Math.round(Math.sin(Math.PI * 2 * angle / 360));
        double y = radius * Math.cos(Math.PI * 2 * angle / 360);
        return currentTile.translate((int) x, (int) y).toLocalTile();
    }


    Area createMappedArea(Optional<LocalTile> anchor, int x1, int y1, int x2, int y2) {
        if (anchor.isEmpty()) {
            throw new NullPointerException("Anchor is empty for creating mapped area");
        }
        return Area.fromRectangle(anchor.get().translate(x1, y1), anchor.get().translate(x2, y2));
    }

    List<LocalTile> createMappedPath(LocalTile anchor, LocalTile... tiles) {
        return new ArrayList<>(Arrays.asList(tiles));
    }

    WorldTile createMappedTile(WorldTile anchor, int x, int y) {
        return anchor.translate(x, y);
    }

    public static WorldTile getMappedTile(WorldTile anchor,
                                          Positionable positionable, int margin) {
        margin = margin / 2;
        int xModifier = General.random(-margin, margin);
        int yModifier = General.random(-margin, margin);
        int x = xModifier + (positionable.getTile().getX() - anchor.getX());
        int y = yModifier + (positionable.getTile().getY() - anchor.getY());
        int z = positionable.getTile().getPlane();
        Log.info("Distance to tile: " + new WorldTile(x, y, z).toString());
        return new WorldTile(x, y, z);
    }


    @Override
    public Priority priority() {
        return Priority.HIGH;
    }

    @Override
    public boolean validate() {
        return GameState.isInInstance();
    }

    @Override
    public void execute() {
        Log.info("Generating Cave Map VALIDATED");
     /*   if (prayerEvent.isPendingCompletion()) {
            prayerEvent.execute();
            prayerEvent.reset();
        }*/
        Vars.get().caveObject = QueryUtils.getObject(Const.CAVE_ENTRANCE_ID);
        if (Vars.get().caveObject.isPresent()) {
            path = Arrays.asList(fightWalker.findPath(
                    Utils.getRSTileFromLocalTile(getTileInDirection(General.random(0, 50),
                    General.random(5, 10)))));
            if (WalkerEngine.getInstance().walkPath(path)) {
                Log.info("Generating Cave Map");
                Waiting.waitUntil(() -> QueryUtils.getObject(Const.CAVE_ENTRANCE_ID).isPresent() ||
                     (path.get(path.size() - 1).distanceTo(Player.getPosition())) < General.random(0, 5));
            }
            if (QueryUtils.getObject(Const.CAVE_ENTRANCE_ID).isPresent() && !MyPlayer.isMoving()) {
                fightWalker.traverse(General.random(1, 30));
            }
            if (Vars.get().caveObject.isPresent()) {
                Vars.get().caveObjectTile = Vars.get().caveObject.map(c -> c.getTile().toLocalTile());
                Log.info("Cached Cave Entrance");
                Log.info(Vars.get().caveObjectTile.toString());
//                Vars.get().italyBootTile = caveObjectTile.translate(3, -18);
//                Vars.get().italyBootSouthTile = caveObjectTile.translate(1, -28);
//                Vars.get().italyBootSouthTile2 = caveObjectTile.translate(4, -30);
//                Vars.get().toeRockTile = caveObjectTile.translate(2, -43);
//                Vars.get().longRockTile = caveObjectTile.translate(-27, -35);
//                Vars.get().clawRockWestTile = caveObjectTile.translate(-21, -12);
//                Vars.get().clawRockEastTile = caveObjectTile.translate(-8, -11);

                Vars.get().ITALY_ROCK_EAST =  Vars.get().caveObject.map(c -> c.getTile().toLocalTile().translate(1, -24));
                Vars.get().ITALY_ROCK_WEST =  Vars.get().caveObject.map(c -> c.getTile().toLocalTile().translate(5, -29));
                Vars.get().endPhase = createMappedArea(Vars.get().caveObjectTile, 3, -4, 9, -17);
                Vars.get().NW = createMappedArea(Vars.get().caveObjectTile, -36, -7, 23, -18);
                Vars.get().C = createMappedArea(Vars.get().caveObjectTile, -18, -25, 6, -32);
                Vars.get().SW = createMappedArea(Vars.get().caveObjectTile, -35, -42, 26, -50);
                Vars.get().S = createMappedArea(Vars.get().caveObjectTile, -14, -43, 7, -50);
                Vars.get().SE = createMappedArea(Vars.get().caveObjectTile, 1, -33, 10, -40);

                Vars.get().nePath = createMappedPath(Vars.get().caveObjectTile.get(),
                        Vars.get().caveObjectTile.get().translate(5, -18), Vars.get().caveObjectTile.get().translate(2, -17), Vars.get().caveObjectTile.get().translate(3, -29), Vars.get().caveObjectTile.get().translate(7, -30));

                Vars.get().nwPath = createMappedPath(Vars.get().caveObjectTile.get(), Vars.get().caveObjectTile.get().translate(-21, -3), Vars.get().caveObjectTile.get().translate(-22, -8), Vars.get().caveObjectTile.get().translate(-21, -11), Vars.get().caveObjectTile.get().translate(-21, -18), Vars.get().caveObjectTile.get().translate(-14, -14), Vars.get().caveObjectTile.get().translate(-8, -11), Vars.get().caveObjectTile.get().translate(-8, -5));
                Vars.get().swPath = createMappedPath(Vars.get().caveObjectTile.get(), Vars.get().caveObjectTile.get().translate(-27, -26), Vars.get().caveObjectTile.get().translate(-27, -35), Vars.get().caveObjectTile.get().translate(-29, -38));
                Vars.get().sePath = createMappedPath(Vars.get().caveObjectTile.get(), Vars.get().caveObjectTile.get().translate(6, -43), Vars.get().caveObjectTile.get().translate(2, -42), Vars.get().caveObjectTile.get().translate(0, -46), Vars.get().caveObjectTile.get().translate(0, -49));
                //setComplete();
            }
        }
    }
}
