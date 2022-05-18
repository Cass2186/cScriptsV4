package scripts.Tasks.Woodcutting.WoodcuttingData;

import org.tribot.api2007.Inventory;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSItem;
import org.tribot.script.sdk.types.WorldTile;
import org.tribot.script.sdk.types.Area;
import scripts.ItemID;
import scripts.Tasks.Woodcutting.WoodcuttingBank;

public class WoodCuttingConst {

    public static Area VARROCK_WEST_OAKS = Area.fromRectangle(new WorldTile(3160, 3413, 0), new WorldTile(3171, 3423, 0));
    public static Area VARROCK_WESK_REGULAR = Area.fromRectangle(new WorldTile(3158, 3411, 0), new WorldTile(3170, 3385, 0));
    public static Area PORT_SARIM_WILLOWS = Area.fromRectangle(new WorldTile(3063, 3248, 0), new WorldTile(3056, 3256, 0));
    public static Area VARROCK_WEST_BANK = Area.fromRectangle(new WorldTile(3184, 3434, 0), new WorldTile(3181, 3437, 0));
    public static Area TEAK_AREA = Area.fromRectangle(new WorldTile(2332, 3050, 0), new WorldTile(2336, 3046, 0));
    public static Area SEERS_MAPLES_AREA = Area.fromRectangle(new WorldTile(2734, 3498, 0), new WorldTile(2719, 3503, 0));
    public static Area DRAYNOR_WILLOW_AREA = Area.fromPolygon(
            new WorldTile[]{
                    new WorldTile(3081, 3237, 0),
                    new WorldTile(3087, 3225, 0),
                    new WorldTile(3094, 3226, 0),
                    new WorldTile(3092, 3237, 0),
                    new WorldTile(3085, 3239, 0)
            }
    );
    public static Area WC_GUILD_MAGIC_AREA = Area.fromPolygon(

            new WorldTile(1577, 3482, 0),
            new WorldTile(1577, 3494, 0),
            new WorldTile(1582, 3494, 0),
            new WorldTile(1582, 3482, 0)

    );
    public static Area WC_GUILD_MAPLE_AREA  =Area.fromPolygon(
                    new WorldTile(1608, 3497, 0),
                    new WorldTile(1608, 3492, 0),
                    new WorldTile(1612, 3488, 0),
                    new WorldTile(1616, 3488, 0),
                    new WorldTile(1623, 3494, 0),
                    new WorldTile(1624, 3498, 0),
                    new WorldTile(1619, 3498, 0),
            new WorldTile(1615, 3500, 0)
    );
    public static Area WC_GUILD_YEW_AREA =  Area.fromPolygon(
                    new WorldTile(1590, 3497, 0),
                    new WorldTile(1600, 3497, 0),
                    new WorldTile(1600, 3485, 0),
                    new WorldTile(1595, 3481, 0),
                    new WorldTile(1589, 3486, 0)

    );
    public static int[] WC_ANIMATIONS = {
            879, //bronze
            877, // iron
            875, // steel
            873, // black
            871, //mithril
            869, // adamant
            867, //rune
            8303, //gilded
            2846, //dragon
            2117 //infernal
    };


    public static boolean equipAxe() {
        int axe = WoodcuttingBank.getBestAxe();
        RSItem[] invAxe = Inventory.find(axe);
        int attk = Skills.getActualLevel(Skills.SKILLS.ATTACK);
        if (invAxe.length == 0)
            return false;

        if (axe == ItemID.AXE_IDS[1] && attk >= 5)
            return invAxe[0].click("Wield");

        else if (axe == ItemID.AXE_IDS[2] && attk >= 20)
            return invAxe[0].click("Wield");

        else if (axe == ItemID.AXE_IDS[3] && attk >= 30)
            return invAxe[0].click("Wield");

        else if (axe == ItemID.AXE_IDS[4] && attk >= 40)
            return invAxe[0].click("Wield");

        else if (axe == ItemID.AXE_IDS[5] && attk >= 60)
            return invAxe[0].click("Wield");

        return invAxe[0].click("Wield"); //iron axe

    }
}
