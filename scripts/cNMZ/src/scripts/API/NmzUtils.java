package scripts.API;

import org.tribot.api.General;
import org.tribot.api2007.Inventory;
import org.tribot.script.sdk.Equipment;
import org.tribot.script.sdk.Prayer;
import org.tribot.script.sdk.Skill;
import org.tribot.script.sdk.query.Query;
import scripts.ItemID;
import scripts.NmzData.NmzConst;
import scripts.NmzData.Vars;
import scripts.Requirements.ItemReq;

import java.util.List;

public class NmzUtils {


    public static void determineBoostPrayers() {
        if (Query.equipment().nameContains("Magic shortbow")
                .isAny()) {
            if (Skill.PRAYER.getActualLevel() >= Prayer.EAGLE_EYE.getRequiredLevel())
                Vars.get().boostPrayerList.add(Prayer.EAGLE_EYE);
        }
    }

    public static void getBankItems() {
        if (Query.equipment().nameContains("Magic shortbow")
                .isAny()) {
            List<ItemReq> items = List.of(
                    new ItemReq(ItemID.PRAYER_POTION4, 20, 15),
                    new ItemReq(ItemID.RANGING_POTION4, 6, 4)
            );
        }
    }
}
