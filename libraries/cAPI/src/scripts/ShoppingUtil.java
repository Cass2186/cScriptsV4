package scripts;

import org.tribot.script.sdk.*;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.Widget;
import org.tribot.script.sdk.types.WorldTile;
import scripts.ItemID;
import scripts.PathingUtil;
import scripts.Utils;

import java.util.Optional;

public class ShoppingUtil {

    public static boolean openShop(WorldTile shopTile, String npcName) {
        if (shopTile.distance() > 5 && PathingUtil.walkToTile(shopTile))
            PathingUtil.humanMovementIdle(shopTile);
        if (!Shop.isOpen() && Utils.clickNPC(npcName, "Trade")) {
           return Waiting.waitUntil(6000, 1200, () -> Shop.isOpen());
        }
        return Shop.isOpen();
    }
}
