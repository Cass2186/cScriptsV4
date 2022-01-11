package scripts.Tasks.Smithing.BlastFurnace.BfData;

import org.tribot.api2007.Equipment;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Skills;

public class BfVars {

    private static BfVars vars;

    public static BfVars get() {
        return vars == null ? vars = new BfVars() : vars;
    }


    public static void reset() {
        vars = new BfVars();
    }

    public boolean scriptStatus = true;

    /**
     * Strings
     */
    public String target = null;

    public String status = "Initializing";

    public long currentTime;

    public long startTime;

    public boolean startPayment = Skills.getActualLevel(Skills.SKILLS.SMITHING) < 60;

    public int currentBarId;

    public boolean usingIceGloves;
    public int cofferFillValue = 100000;
    public boolean abc2Sleep = false;

    public boolean coalBagFull = false;

    public boolean useGoldSmith = Equipment.isEquipped(BfConst.GOLDSMITH_GAUNTLETS) ||
            Inventory.find(BfConst.GOLDSMITH_GAUNTLETS).length > 0;
}