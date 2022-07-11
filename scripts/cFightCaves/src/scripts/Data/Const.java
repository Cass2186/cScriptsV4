package scripts.Data;

import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.Skill;
import org.tribot.script.sdk.types.Area;
import org.tribot.script.sdk.types.WorldTile;

public class Const {
    public static Area START_AREA = Area.fromRectangle(new WorldTile(2437, 5171, 0), new WorldTile(2440, 5168, 0));
    public static int CAVE_ENTRANCE_ID = 11834;
    public static final int START_RANGED_XP = Skill.RANGED.getXp();

    // NPC animations
    public static final int TZTOK_JAD_MAGIC_ATTACK = 2656;
    public static final int TZTOK_JAD_RANGE_ATTACK = 2652;

}
