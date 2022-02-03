package scripts.Tasks;

import dax.walker.utils.camera.DaxCamera;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.MyPlayer;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.types.LocalTile;
import scripts.Data.Phase.ZulrahPhase;
import scripts.Data.TickObserver;
import scripts.Timer;

import java.util.Optional;

public class MoveToTile implements Task {

    public Optional<LocalTile> getStandingTile() {
        if (TickObserver.instance != null) {
            ZulrahPhase currentPhase = TickObserver.instance.getPhase();
            LocalTile startTile = TickObserver.instance.getStartLocation();
            if (currentPhase != null) {
                return Optional.ofNullable(currentPhase.getStandTile(startTile));
            }
        }
        return Optional.empty();
    }

    public boolean moveToTile() {
        Optional<LocalTile> localTile = getStandingTile();
        if (localTile.isPresent()) {
            if (MyPlayer.getPosition().toLocalTile().equals(localTile.get())) {
                Log.log("Already at Tile");
                return true;
            }
            if (!localTile.get().isVisible())
                localTile.get().adjustCameraTo();

            return localTile.get().interact("Walk here");
        }
        return false;
    }

    @Override
    public String toString() {
        return "Moving to Tile";
    }

    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        Optional<LocalTile> localTile = getStandingTile();
        return localTile.isPresent() && !MyPlayer.getPosition().toLocalTile().equals(localTile.get());
    }

    @Override
    public void execute() {
        moveToTile();
        if (Waiting.waitUntil(1000, () -> MyPlayer.isMoving() || MyPlayer.getCurrentHealthPercent() < 50))
            Waiting.waitUntil(750, () -> !MyPlayer.isMoving()
                    || MyPlayer.getCurrentHealthPercent() < 50);
    }
}
