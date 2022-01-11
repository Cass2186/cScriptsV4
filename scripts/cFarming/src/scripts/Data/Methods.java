package scripts.Data;

import org.tribot.api.Clicking;
import org.tribot.api2007.Game;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSItemDefinition;
import org.tribot.api2007.types.RSObject;
import scripts.Timer;
import scripts.Utils;

import java.util.HashMap;

public class Methods {

    private static void rakeWeeds(int patchId) {
        RSObject[] weeds = Objects.findNearest(20, Filters.Objects.actionsContains("Rake").and(Filters.Objects.idEquals(patchId)));
        if (weeds.length > 0) {
            if (Game.getItemSelectionState() == 1){
                Clicking.click(weeds[0]);
            }

            if (Utils.clickObject(patchId, "Rake", false))
                Timer.waitCondition(() -> Objects.findNearest(4, Filters.Objects.actionsContains("Rake").and(Filters.Objects.idEquals(patchId))).length <
                        weeds.length, 20000, 30000);
        }
    }

    public static boolean hasItem(int itemId, int quantity) {
        RSItem[] item = Inventory.find(itemId);
        if (item.length > 0) {
            RSItemDefinition itemDef = item[0].getDefinition();
            if (itemDef != null) {
                if (itemDef.isStackable()) {
                    return item[0].getStack() >= quantity;
                } else {
                    return item[0].getStack() >= quantity;
                }
            }
        }
        return false;
    }

    public static boolean checkInventory(HashMap<Integer, Integer> invHashMap) {
        for (Integer i : invHashMap.keySet()) {
            if (hasItem(i, invHashMap.get(i))) {
                return true;
            }
        }
        return false;
    }


}
