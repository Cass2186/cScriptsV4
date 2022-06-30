package scripts.Data;

import lombok.Getter;
import lombok.Setter;
import org.tribot.script.sdk.interfaces.Positionable;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.Area;
import org.tribot.script.sdk.types.LocalTile;
import org.tribot.script.sdk.walking.LocalWalking;
import org.tribot.script.sdk.walking.WalkState;

import java.util.List;
import java.util.Optional;

public class NavHelper {


    @Getter
    @Setter
    private boolean acceptAdjacentTiles = false;

    @Getter
    @Setter
    private Positionable[] excludedTiles = null;

    public boolean walkToTile(LocalTile tile, WalkState state) {
        Optional<LocalTile> walkable = Optional.of(tile);
        if (this.acceptAdjacentTiles) {
            if (this.excludedTiles != null) {
                walkable = Query.tiles()
                        .inArea(Area.fromRadius(tile, 1))
                        .tileNotEquals(excludedTiles)
                        .isReachable()
                        .findBestInteractable();
            } else {
                walkable = Query.tiles()
                        .inArea(Area.fromRadius(tile, 1))
                        .isReachable()
                        .findBestInteractable();
            }
        }
        return walkable.map(w->LocalWalking.walkTo(w, ()-> state)).orElse(false);
    }

}
