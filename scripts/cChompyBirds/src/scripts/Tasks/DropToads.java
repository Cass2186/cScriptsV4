package scripts.Tasks;


import org.tribot.script.sdk.Inventory;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.MyPlayer;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.Area;
import org.tribot.script.sdk.types.GameObject;
import org.tribot.script.sdk.types.InventoryItem;
import org.tribot.script.sdk.types.WorldTile;
import scripts.Data.Const;
import scripts.ItemID;
import scripts.PathingUtil;
import scripts.Utils;


import java.util.List;

public class DropToads implements Task {
    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return Inventory.getCount(ItemID.BLOATED_TOAD) == 3
                && Query.npcs().idEquals(Const.CHOMPY_ID).toList().size() == 0
                && Query.npcs().idEquals(Const.NPC_BLOATED_TOAD).toList().size() < 3;

    }


    @Override
    public String toString() {
        return "Dropping Toads";
    }


    @Override
    public void execute() {
        Utils.idleNormalAction();
        dropToads();
    }

    private WorldTile randomizeTile(WorldTile origin, int tileRange) {
        return Area.fromRadius(origin, tileRange).getRandomTile();
    }

    private void dropToads() {
        List<InventoryItem> invToad = Query.inventory().idEquals(ItemID.BLOATED_TOAD).toList();

        if (invToad.size() > 0) {
            Log.info("Moving to drop tile");
            if (PathingUtil.walkToTile(randomizeTile(Const.DROP_TILE, 2))) {
                PathingUtil.movementIdle();
            }

            for (InventoryItem t : invToad) {
                WorldTile myTile = MyPlayer.getTile();
                List<GameObject> droppedToad = Query.gameObjects().maxDistance(10).idEquals(Const.NPC_BLOATED_TOAD)
                        .toList();
                if (droppedToad.stream().anyMatch(toad -> toad.getTile().equals(MyPlayer.getTile()))) {
                    Log.info("Standing on a toad, moving");
                    if (myTile.translate(-1, 0).interact("Walk here")) {
                        Waiting.waitUntil(1500, 150, MyPlayer::isMoving);
                    }
                }
                if (t.click("Drop")) {
                    Waiting.waitUntil(7500, 450, () ->
                            !MyPlayer.getTile().equals(myTile));
                }
            }
        }
    }

}
