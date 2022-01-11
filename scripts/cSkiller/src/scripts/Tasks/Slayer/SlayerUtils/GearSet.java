package scripts.Tasks.Slayer.SlayerUtils;

import org.tribot.api2007.Equipment;
import org.tribot.api2007.types.RSItem;

public class GearSet {
    RSItem head;
    RSItem cape;
    RSItem body;
    RSItem legs;
    RSItem boots;
    RSItem weapon;
    RSItem shield;
    RSItem ammo;

    /**
     * constructor that will make an object with all the gear the player is wearing
     * pass   Equipment.getItems() as arg to get your current gear as an array of RSItem[]
     * @param
     */
    public GearSet(RSItem[] allGear) {
        // may need all the factors before
        for (RSItem item : allGear) {

            if (item.getEquipmentSlot() == Equipment.SLOTS.HELMET) {
                head = item;

            } else if (item.getEquipmentSlot() == Equipment.SLOTS.CAPE) {
                cape = item;

            } else if (item.getEquipmentSlot() == Equipment.SLOTS.BODY) {
                body = item;

            } else if (item.getEquipmentSlot() == Equipment.SLOTS.LEGS) {
                legs = item;

            } else if (item.getEquipmentSlot() == Equipment.SLOTS.BOOTS) {
                boots = item;

            } else if (item.getEquipmentSlot() == Equipment.SLOTS.WEAPON) {
                weapon = item;

            } else if (item.getEquipmentSlot() == Equipment.SLOTS.SHIELD) {
                shield = item;

            } else if (item.getEquipmentSlot() == Equipment.SLOTS.ARROW) {
                ammo = item;
            }
        }

    }
}
