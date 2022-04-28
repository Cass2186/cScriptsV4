package scripts.Tasks.BloodCrafting;

import org.tribot.script.sdk.Inventory;
import org.tribot.script.sdk.Log;
import scripts.ItemID;
import scripts.Timer;
import scripts.Utils;

public class ChipEssence {
    private void chip(){
        if (Utils.useItemOnItem(ItemID.CHISEL, ItemID.DARK_ESSENCE_BLOCK)){
            Log.info("[Debug]: Chipping essence");
            Timer.waitCondition(()-> !Inventory.contains(ItemID.DARK_ESSENCE_BLOCK), 6500,8800);
        }
    }
}
