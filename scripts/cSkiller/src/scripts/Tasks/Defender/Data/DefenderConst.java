package scripts.Tasks.Defender.Data;

import org.tribot.script.sdk.types.Area;
import org.tribot.script.sdk.types.WorldTile;

public class DefenderConst {

    public static int[] DEFENDERS = {12954, 8850, 8849, 8848, 8847, 8846, 8845, 8844};
    public static int RUNE_DEFENDER = 8850;
    public static int DRAGON_DEFENDER = 12954;
    public static int TOKENS = 8851;
    public static int MITHRIL_PLATELEGS = 1071;
    public static int MITHRIL_PLATEBODY = 1121;
    public static int MITHRIL_FULLHELM = 1159;
    public static int BLACK_FULLHELM = 1165;
    public static int BLACK_PLATEBODY = 1125;
    public static int BLACK_PLATELEGS = 1077;
    public static int MITHRIL_NPC = 2454;
    public static int LEFT_ANIMATOR_ID = 23955;
    public static int[] FOOD = {7946, 329};

    public static int CYCLOPS_ID = 2467;
    public static int BLACK_KNIFE = 869;

    public static int MIN_TOKENS = 500;
    public static int MAX_TOKENS = 1000;

    public static WorldTile LEFT_ANIMATOR_TILE = new WorldTile(2851, 3538, 0);
    public static Area LEFT_ANIMATOR_TILE_AREA = Area.fromRadius(new WorldTile(2851, 3538, 0), 3);
    // Area UPPER_CYCLOPS_AREA = new Area(new WorldTile[]{new WorldTile(2838, 3543, 2), new WorldTile(2847, 3543, 2), new WorldTile(2847, 3538, 2), new WorldTile(2875, 3538, 2), new WorldTile(2875, 3555, 2), new WorldTile(2838, 3556, 2)});
    public static Area UPPER_CYCLOPS_AREA = Area.fromPolygon(

            new WorldTile(2838, 3543, 2),
            new WorldTile(2847, 3543, 2),
            new WorldTile(2847, 3538, 2),
            new WorldTile(2848, 3538, 2),
            new WorldTile(2848, 3533, 2),
            new WorldTile(2878, 3533, 2),
            new WorldTile(2878, 3558, 2),
            new WorldTile(2836, 3558, 2)
    );
    public static  Area BEFORE_DOOR = Area.fromRectangle(new WorldTile(2846, 3537, 2), new WorldTile(2838, 3542, 2));
    public static Area DRAGON_DEFENDER_AREA = Area.fromPolygon(

            new WorldTile(2912, 9973, 0),
            new WorldTile(2912, 9966, 0),
            new WorldTile(2905, 9966, 0),
            new WorldTile(2905, 9957, 0),
            new WorldTile(2941, 9957, 0),
            new WorldTile(2941, 9974, 0),
            new WorldTile(2912, 9974, 0)
    );

    public static   WorldTile BANK_TILE = new WorldTile(2843, 3543, 0);

}
