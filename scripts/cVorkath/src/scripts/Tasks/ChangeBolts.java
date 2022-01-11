package scripts.Tasks;

import org.tribot.api2007.Equipment;
import org.tribot.api2007.Game;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSNPC;
import scripts.ItemId;
import scripts.VorkUtils.VorkthUtil;

import javax.swing.*;

public class ChangeBolts implements Task {


    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        RSItem[] bolts = Inventory.find(ItemId.DIAMOND_DRAGON_BOLTS_E);
        RSNPC[] vork = NPCs.findNearest(VorkthUtil.ATTACKING_VORK);
        if (bolts.length>0 && vork.length > 0 && vork[0].getHealthPercent() <= 1.2)
            return NPCs.findNearest(VorkthUtil.ATTACKING_VORK).length > 0 &&
                    Game.isInInstance() && !Equipment.isEquipped(ItemId.DIAMOND_DRAGON_BOLTS_E);
        return false;
    }

    @Override
    public void execute() {
        RSItem[] bolts = Inventory.find(ItemId.DIAMOND_DRAGON_BOLTS_E);
        if(bolts.length>0)
            bolts[0].click();
    }
}
