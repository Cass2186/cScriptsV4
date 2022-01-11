package scripts.Tasks.Slayer.Tasks;

import org.tribot.api.General;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Equipment;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSItemDefinition;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.BankManager;
import scripts.Tasks.Slayer.SlayerUtils.GearSet;
import scripts.Tasks.Slayer.SlayerUtils.SlayerVars;
import scripts.Timer;
import scripts.Utils;

import java.awt.*;
import java.util.ArrayList;

public class SetSlayerGear implements Task {



    public static ArrayList<RSItem> gearList = new ArrayList<>();

    public static GearSet startingGear = new GearSet(Equipment.getItems());
    public static GearSet prayerGear;
    public static GearSet dragonGear;

    public static void cacheStartingGear() {
        gearList.clear();

        General.println("[Gear Manager]: Saving initial gear...", Color.YELLOW);
        SlayerVars.get().HEAD_GEAR = Equipment.getItem(Equipment.SLOTS.HELMET);
        SlayerVars.get().CAPE_GEAR = Equipment.getItem(Equipment.SLOTS.CAPE);
        SlayerVars.get().AMMO_GEAR = Equipment.getItem(Equipment.SLOTS.ARROW);
        SlayerVars.get().WEAPON_GEAR = Equipment.getItem(Equipment.SLOTS.WEAPON);
        SlayerVars.get().BODY_GEAR = Equipment.getItem(Equipment.SLOTS.BODY);
        SlayerVars.get().SHIELD_GEAR = Equipment.getItem(Equipment.SLOTS.SHIELD);
        SlayerVars.get().LEG_GEAR = Equipment.getItem(Equipment.SLOTS.LEGS);
        SlayerVars.get().BOOT_GEAR = Equipment.getItem(Equipment.SLOTS.BOOTS);

        gearList.add(SlayerVars.get().HEAD_GEAR);
        gearList.add(SlayerVars.get().CAPE_GEAR);
        gearList.add(SlayerVars.get().AMMO_GEAR);
        gearList.add(SlayerVars.get().WEAPON_GEAR);
        gearList.add(SlayerVars.get().BODY_GEAR);
        gearList.add(SlayerVars.get().SHIELD_GEAR);
        gearList.add(SlayerVars.get().LEG_GEAR);
        gearList.add(SlayerVars.get().BOOT_GEAR);
        General.println("[Gear]: Gearlist.size() is " + gearList.size());
    }


    public static ArrayList<Integer> checkGear() {
        if (!SlayerVars.get().usingSpecialItem && !SlayerVars.get().magicMeleeGear && !SlayerVars.get().usingAntiFire) {
            ArrayList<Integer> gearToGet = new ArrayList<Integer>();
            General.println("[SetGear]: Checking gear");
            for (RSItem gear : gearList) {
                if (gear != null && !Equipment.isEquipped(gear.getID())) {
                    General.println("[Gear Manager]: Missing gear: " + gear.getDefinition().getName());
                    gearToGet.add(gear.getID());
                }
            }
            return gearToGet;
        } else {
            General.sleep(500);
            General.println("[SetGear]: SlayerVars.usingSpecialItem: " + SlayerVars.get().usingSpecialItem
                    + "  ||  SlayerVars.magicMeleeGear: " + SlayerVars.get().magicMeleeGear +
                    "  ||  SlayerVars.usingAntiFire: " + SlayerVars.get().usingAntiFire);
            return null;
        }
    }

    public static String getItemName(int itemId) {

        RSItemDefinition itemDef = RSItemDefinition.get(itemId);
        if (itemDef != null) {
            String itemName = itemDef.getName();

            return itemName != null ? itemName : "Failed";
        }
        return "Failed";
    }

    public static void getGear() {
        ArrayList<Integer> gearToGet;
        gearToGet = checkGear();

        if (gearToGet != null && gearToGet.size() > 0) {
            SlayerVars.get().status = "Getting Missing Gear";
            General.println("[SetGear]: " + SlayerVars.get().status);
            BankManager.open(true);
            BankManager.depositAll(true);
            for (int item : gearToGet) {
                General.println("[Gear Manager]: Getting " + getItemName(item));
                BankManager.withdraw(1, true, item);
                Timer.waitCondition(() -> Inventory.find(item).length > 0, 3000, 6000);
                equipItem(item);
            }
            Utils.shortSleep();
        }

    }

    public static void getSpecialTopAndLegs(int topId, int bottomId) {
        BankManager.open(true);
        BankManager.depositAll(true);
        BankManager.withdraw(1, true, topId);
        equipItem(topId);
        BankManager.withdraw(1, true, bottomId);
        equipItem(bottomId);

    }


    private static boolean equipItem(int itemId) {
        RSItem[] invItemEquip = Inventory.find(Filters.Items.actionsEquals("Equip").and(Filters.Items.idEquals(itemId)));
        RSItem[] invItemWield = Inventory.find(Filters.Items.actionsEquals("Wield").and(Filters.Items.idEquals(itemId)));
        RSItem[] invItemWear = Inventory.find(Filters.Items.actionsEquals("Wear").and(Filters.Items.idEquals(itemId)));

        if (invItemEquip.length > 0) {
            if (invItemEquip[0].click("Equip"))
                Timer.waitCondition(() -> Equipment.find(itemId).length > 0, 3000, 5000);
            Utils.microSleep();
            return true;
        }
        if (invItemWield.length > 0) {
            if (invItemWield[0].click("Wield"))
                Timer.waitCondition(() -> Equipment.find(itemId).length > 0, 3000, 5000);
            Utils.microSleep();
            return true;
        }
        if (invItemWear.length > 0) {
            if (invItemWear[0].click("Wear"))
                Timer.waitCondition(() -> Equipment.find(itemId).length > 0, 3000, 5000);
            Utils.microSleep();
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Setting gear";
    }

    @Override
    public Priority priority() {
        return Priority.LOW;
    }


    @Override
    public void execute() {
        General.println("[SetGear]: SetGear Task activated" , Color.RED);
        getGear();
        //SlayerVars.get().needsToEquipGear = false;
    }

    @Override
    public boolean validate() {
        return (SlayerVars.get().shouldBank || Inventory.find(Filters.Items.actionsContains("Eat")).length < 3) // same as bank validiation
                || (checkGear() != null && Banking.isInBank()) && !SlayerVars.get().slayerShopRestock;
    }

    @Override
    public String taskName() {
        return "Slayer";
    }
}
