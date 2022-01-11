package scripts.API;

import org.tribot.api.General;
import org.tribot.api2007.Inventory;
import scripts.ItemId;
import scripts.NmzData.NmzConst;
import scripts.NmzData.Vars;

public class NmzUtils {

    public static void determinePotions() {
        if (Inventory.find(ItemId.PRAYER_POTION).length > 0) {
            General.println("[Debug]: Using prayer potions");
            Vars.get().usingPrayerPots = true;
            Vars.get().usingAbsorptions = false;
        } else if (Inventory.find(NmzConst.ABSORPTION_POTION).length > 0) {
            General.println("[Debug]: Using absorption potions");
            Vars.get().usingPrayerPots = false;
            Vars.get().usingAbsorptions = true;
        }
        if (Inventory.find(NmzConst.OVERLOAD_POTION).length > 0) {
            General.println("[Debug]: Using overload potions");
            Vars.get().usingOverloadPots = true;
        } else {
            Vars.get().usingOverloadPots = false;
        }
    }
}
