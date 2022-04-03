package scripts.Tasks.Slayer.SlayerUtils;

import org.tribot.script.sdk.Equipment;
import org.tribot.script.sdk.types.EquipmentItem;

public class GearSet {

    EquipmentItem head;
    EquipmentItem cape;
    EquipmentItem body;
    EquipmentItem legs;
    EquipmentItem boots;
    EquipmentItem weapon;
    EquipmentItem shield;
    EquipmentItem ammo;

    /**
     * constructor that will make an object with all the gear the player is wearing
     * pass   Equipment.getItems() as arg to get your current gear as an array of EquipmentItem[]
     * @param
     */
    public GearSet(EquipmentItem[] allGear) {
        // may need all the factors before
        for (EquipmentItem item : allGear) {

            if (item.getSlot()== Equipment.Slot.HEAD) {
                head = item;

            } else if (item.getSlot() == Equipment.Slot.CAPE) {
                cape = item;

            } else if (item.getSlot() == Equipment.Slot.BODY) {
                body = item;

            } else if (item.getSlot() == Equipment.Slot.LEGS) {
                legs = item;

            } else if (item.getSlot() == Equipment.Slot.FEET) {
                boots = item;

            } else if (item.getSlot() == Equipment.Slot.WEAPON) {
                weapon = item;

            } else if (item.getSlot() == Equipment.Slot.SHIELD) {
                shield = item;

            } else if (item.getSlot() == Equipment.Slot.AMMO) {
                ammo = item;
            }
        }

    }
}
