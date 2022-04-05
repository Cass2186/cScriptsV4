package scripts.Data;


import org.tribot.script.sdk.types.Area;
import org.tribot.script.sdk.types.WorldTile;

public class Areas {

    public static Area FALADOR_AREA =
            Area.fromRectangle(new WorldTile(3059, 3312, 0), new WorldTile(3055, 3309, 0));
    public static Area CATHERBY_AREA = Area.fromRectangle(new WorldTile(2816, 3461, 0), new WorldTile(2811, 3466, 0));
    public static Area ARDOUGNE_AREA = Area.fromRectangle(new WorldTile(2673, 3372, 0), new WorldTile(2668, 3377, 0));
    public static Area HOSIDIUS_AREA = Area.fromRectangle(new WorldTile(1741, 3548, 0), new WorldTile(1736, 3553, 0));
    public static Area MORYTANIA_AREA = Area.fromRectangle(new WorldTile(3607, 3527, 0), new WorldTile(3602, 3532, 0));


    /**
     * /**
     * AREAS (Herb patches)
     */
    public static Area TAVERLY_TREE_AREA = Area.fromRectangle(new WorldTile(2933, 3438, 0), new WorldTile(2939, 3442, 0));
    public static Area FALADOR_TREE_AREA = Area.fromRectangle(new WorldTile(3004, 3372, 0), new WorldTile(3000, 3376, 0));
    public static Area LUMBRIDGE_TREE_AREA = Area.fromRectangle(new WorldTile(3192, 3232, 0), new WorldTile(3197, 3227, 0));
    public static Area VARROCK_TREE_AREA = Area.fromRectangle(new WorldTile(3226, 3460, 0), new WorldTile(3230, 3454, 0));
    public static Area TREE_GNOME_TREE_AREA = Area.fromRectangle(new WorldTile(2433, 3419, 0), new WorldTile(2439, 3414, 0));

    public static Area FARMING_GUILD_TREE_AREA = Area.fromRectangle(new WorldTile(1230, 3735, 0), new WorldTile(1241, 3726, 0));
    public static Area FARMING_GUILD_ALLOTMENT_AREA = Area.fromRectangle(new WorldTile(1258, 3724, 0), new WorldTile(1270, 3736, 0));

    /**
     * FRUIT TREE AREAS
     */
    public static Area STRONGHOLD_FRUIT_TREE_AREA = Area.fromRectangle(new WorldTile(2472, 3448, 0), new WorldTile(2478, 3443, 0));
    public static Area TGV_FRUIT_TREE_AREA = Area.fromRectangle(new WorldTile(2491, 3178, 0), new WorldTile(2488, 3183, 0));
    public static Area BRIMHAVEN_FRUIT_TREE_AREA = Area.fromRectangle(new WorldTile(2767, 3212, 0), new WorldTile(2763, 3217, 0));
    public static Area CATHERBY_FRUIT_TREE_AREA = Area.fromRectangle(new WorldTile(2861, 3433, 0), new WorldTile(2857, 3431, 0));


}
