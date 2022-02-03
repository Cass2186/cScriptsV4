package scripts.Tasks;

import org.tribot.api2007.Equipment;
import scripts.ItemID;
import scripts.Utils;

public class ChangeGear implements Task{



    @Override
    public String toString() {
        return "Changing Gear";
    }

    @Override
    public Priority priority() {
        return Priority.HIGH;
    }

    @Override
    public boolean validate() {
        return !Equipment.isEquipped(ItemID.RING_OF_RECOIL);
    }

    @Override
    public void execute() {
        Utils.equipItem(ItemID.RING_OF_RECOIL);
    }
}
