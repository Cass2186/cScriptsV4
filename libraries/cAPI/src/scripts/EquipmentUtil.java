package scripts;

import org.tribot.script.sdk.Equipment;

public class EquipmentUtil {

    public static boolean isAllEquipped(int... ids){
        for (int i : ids){
            if (!Equipment.contains(ids))
                return false;
        }
        return true;
    }
}
