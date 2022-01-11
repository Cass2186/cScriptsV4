package scripts.Data;

import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;

public class MiningConst {

    public static int MINING_ANIMATION = 6752;
    public static int PAY_DIRT = 12011;

    public static String ORE_VEIN = "Ore vein";
    public static String DEPLETED_VEIN = "Depleted vein";



    public static   RSArea NORTH_MINING_AREA = new RSArea(
            new RSTile[] {
                    new RSTile(3732, 5692, 0),
                    new RSTile(3733, 5687, 0),
                    new RSTile(3748, 5687, 0),
                    new RSTile(3749, 5686, 0),
                    new RSTile(3751, 5686, 0),
                    new RSTile(3752, 5694, 0)
            }
    );
    public static  RSTile MLM_DEPOSIT_BOX_TILE = new RSTile(3749, 5673, 0); //just before it

}
