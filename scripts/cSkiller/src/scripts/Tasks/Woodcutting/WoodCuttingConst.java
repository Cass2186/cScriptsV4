package scripts.Tasks.Woodcutting;

import org.tribot.api2007.Inventory;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSTile;
import scripts.ItemId;

public class WoodCuttingConst {

    public static RSArea VARROCK_WEST_OAKS = new RSArea(new RSTile(3160, 3413, 0), new RSTile(3171, 3423, 0));
    public static RSArea VARROCK_WESK_REGULAR = new RSArea(new RSTile(3158, 3411, 0), new RSTile(3170, 3385, 0));
    public static RSArea PORT_SARIM_WILLOWS = new RSArea(new RSTile(3063, 3248, 0), new RSTile(3056, 3256, 0));
    public static RSArea VARROCK_WEST_BANK = new RSArea(new RSTile(3184, 3434, 0), new RSTile(3181, 3437, 0));
    public static RSArea TEAK_AREA = new RSArea(new RSTile(2332, 3050, 0), new RSTile(2336, 3046, 0));
    public static RSArea SEERS_MAPLES_AREA = new RSArea(new RSTile(2734, 3498, 0), new RSTile(2719, 3503, 0));
    public static RSArea DRAYNOR_WILLOW_AREA = new RSArea(
            new RSTile[] {
                    new RSTile(3081, 3237, 0),
                    new RSTile(3087, 3225, 0),
                    new RSTile(3094, 3226, 0),
                    new RSTile(3092, 3237, 0),
                    new RSTile(3085, 3239, 0)
            }
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

        if (axe == ItemId.AXE_IDS[1] && attk >= 5)
            return invAxe[0].click("Wield");

        else if (axe == ItemId.AXE_IDS[2] && attk >= 20)
            return invAxe[0].click("Wield");

        else if (axe == ItemId.AXE_IDS[3] && attk >= 30)
            return invAxe[0].click("Wield");

        else if (axe == ItemId.AXE_IDS[4] && attk >= 40)
            return invAxe[0].click("Wield");

        else if (axe == ItemId.AXE_IDS[5] && attk >= 60)
            return invAxe[0].click("Wield");

        return invAxe[0].click("Wield"); //iron axe

    }
}
