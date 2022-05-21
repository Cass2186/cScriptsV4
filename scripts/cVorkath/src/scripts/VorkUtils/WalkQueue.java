package scripts.VorkUtils;

import org.tribot.api2007.Game;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSPlayer;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.MyPlayer;
import org.tribot.script.sdk.types.WorldTile;

public class WalkQueue {

    private static RSTile getTruePosition(RSPlayer player) {
        RSTile tile = getWalkingTowards(player);
        return tile != null ? tile : player.getPosition();
    }

    public static RSTile getTruePosition() {
        RSPlayer myPlayer = Player.getRSPlayer();
        if (myPlayer != null) {
            return getTruePosition(myPlayer);
        }
        return Player.getPosition();
    }

    public static WorldTile getTruePositionWorldTile() {
        RSPlayer myPlayer = Player.getRSPlayer();
        if (myPlayer != null) {
            return new WorldTile(getTruePosition(myPlayer).getX(), getTruePosition(myPlayer).getY(), getTruePosition(myPlayer).getPlane());
        }
        return MyPlayer.getTile();
    }

    public static int getTruePositionY() {
        if (getTruePosition() != null) {
            return getTruePosition(Player.getRSPlayer()).getY();
        }
        return 0;
    }

    private static RSTile getWalkingTowards(RSPlayer rsCharacter) {
        int[] xIndex = rsCharacter.getWalkingQueueX(), yIndex = rsCharacter.getWalkingQueueY();
        if (xIndex == null || yIndex == null) return null;
        if (xIndex.length <= 0 || yIndex.length <= 0) return null;


        return new RSTile(Game.getBaseX() + xIndex[0]
                , Game.getBaseY() + yIndex[0], Game.getPlane());
    }

}
