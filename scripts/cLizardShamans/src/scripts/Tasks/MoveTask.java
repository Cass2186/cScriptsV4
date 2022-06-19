package scripts.Tasks;

import org.tribot.api2007.Player;
import org.tribot.script.sdk.Camera;
import org.tribot.script.sdk.MyPlayer;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.Area;
import org.tribot.script.sdk.types.LocalTile;
import org.tribot.script.sdk.types.WorldTile;
import scripts.Data.Const;

import java.util.Optional;
import java.util.function.BooleanSupplier;

public class MoveTask implements Task {


    @Override
    public String toString() {
        return "Moving";
    }

    @Override
    public Priority priority() {
        return Priority.HIGH;
    }

    @Override
    public boolean validate() {
        return false;
    }

    @Override
    public void execute() {

    }

    //use to move to the closest safe tile typically
    Optional<LocalTile> getClosestTileInArea(Area area) {
        return Query.tiles().inArea(area)
                .isReachable().sortedByPathDistance().findClosest();
    }

    boolean shouldMoveFromMinions(int dist) {
        return Query.npcs().idEquals(Const.MINION_ID)
                .maxDistance(dist)
                .isAny();
    }

    boolean adjustCameraAngle() {
        int angle = Camera.getAngle();
        if (angle < 80) {
            Camera.setRotationMethod(Camera.RotationMethod.MOUSE); //faster
            Camera.setAngle(100);
        }
        return angle >= 80;
    }

    boolean screenWalkToTile(Optional<LocalTile> localTile, boolean interuptCond) {
        if (localTile.isEmpty())
            return false;

        LocalTile tile = localTile.get();
        if (!tile.isVisible()) {
            tile.adjustCameraTo();
            adjustCameraAngle(); //adjust camera downward facing
        }
        if (tile.interact("Walk here")) {
            return Waiting.waitUntil(5000, 25,
                    () -> MyPlayer.getTile().toLocalTile().equals(tile) ||
                            interuptCond);
        }
        return MyPlayer.getTile().toLocalTile().equals(tile);
    }

}
