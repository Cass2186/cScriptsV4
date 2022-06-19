package scripts.Data;

import org.tribot.script.sdk.types.Area;
import org.tribot.script.sdk.types.WorldTile;

public class Const {

    public static final int MINION_ID = 6768;
    public static final int LIZARD_SHAMAN_ID = 6766;

    public static final WorldTile TOP_LEFT_CORNER_TILE = new WorldTile(1419, 3723, 0);
    public static final Area MAIN_SHAMAN_AREA = Area.fromRectangle(new WorldTile(1419, 3722, 0), new WorldTile(1439, 3703, 0));
    public static final Area LEFT_WALL_SAFE_STRIP = Area.fromRectangle(new WorldTile(1419, 3723, 0), new WorldTile(1419, 3705, 0));
    public static final Area TOP_WALL_SAFE_STRIP = Area.fromRectangle(new WorldTile(1419, 3722, 0), new WorldTile(1437, 3722, 0));
    public static final  Area WESTERN_TEMPLE_ROOM_AREA =  Area.fromRectangle(new WorldTile(1296, 10093, 0), new WorldTile(1288, 10100, 0));
}
