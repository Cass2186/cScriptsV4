package scripts.Tasks.Slayer.Tasks;

import org.tribot.api.General;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSItemDefinition;
import org.tribot.script.sdk.Bank;
import org.tribot.script.sdk.Equipment;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.query.EquipmentQuery;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.EquipmentItem;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.BankManager;
import scripts.Tasks.Slayer.SlayerUtils.GearSet;
import scripts.Tasks.Slayer.SlayerUtils.SlayerVars;
import scripts.Timer;
import scripts.Utils;

import java.awt.*;
import java.util.ArrayList;
import java.util.Optional;

public class SetSlayerGear implements Task {



    public static ArrayList<Optional<EquipmentItem>> gearList = new ArrayList<>();

    public static GearSet startingGear = new GearSet(Equipment.getAll().toArray(new EquipmentItem[0]));
    public static GearSet prayerGear;
    public static GearSet dragonGear;

    public static void cacheStartingGear() {
        gearList.clear();

        Log.info("[Gear Manager]: Saving initial gear...");
        SlayerVars.get().HEAD_GEAR = Query.equipment().slotEquals(Equipment.Slot.HEAD).findFirst();
        SlayerVars.get().CAPE_GEAR = Query.equipment().slotEquals(Equipment.Slot.CAPE).findFirst();
        SlayerVars.get().AMMO_GEAR = Query.equipment().slotEquals(Equipment.Slot.AMMO).findFirst();
        SlayerVars.get().WEAPON_GEAR = Query.equipment().slotEquals(Equipment.Slot.WEAPON).findFirst();
        SlayerVars.get().BODY_GEAR = Query.equipment().slotEquals(Equipment.Slot.BODY).findFirst();
        SlayerVars.get().SHIELD_GEAR = Query.equipment().slotEquals(Equipment.Slot.SHIELD).findFirst();
        SlayerVars.get().LEG_GEAR = Query.equipment().slotEquals(Equipment.Slot.LEGS).findFirst();
        SlayerVars.get().BOOT_GEAR = Query.equipment().slotEquals(Equipment.Slot.FEET).findFirst();

        gearList.add(SlayerVars.get().HEAD_GEAR);
        gearList.add(SlayerVars.get().CAPE_GEAR);
        gearList.add(SlayerVars.get().AMMO_GEAR);
        gearList.add(SlayerVars.get().WEAPON_GEAR);
        gearList.add(SlayerVars.get().BODY_GEAR);
        gearList.add(SlayerVars.get().SHIELD_GEAR);
        gearList.add(SlayerVars.get().LEG_GEAR);
        gearList.add(SlayerVars.get().BOOT_GEAR);
        Log.info("[Gear]: Gearlist.size() is " + gearList.size());
    }


    public static ArrayList<Integer> checkGear() {
        if (!SlayerVars.get().usingSpecialItem && !SlayerVars.get().magicMeleeGear && !SlayerVars.get().usingAntiFire) {
            ArrayList<Integer> gearToGet = new ArrayList<Integer>();
            Log.info("[SetGear]: Checking gear");
            for (Optional<EquipmentItem> gear : gearList) {
                if (gear.isEmpty())
                    continue;
                if (!Equipment.contains(gear.get().getId())) {
                    Log.warn("[Gear Manager]: Missing gear: " + gear.get().getDefinition().getName());
                    gearToGet.add(gear.get().getId());
                }
            }
            return gearToGet;
        } else {
            Waiting.waitNormal(350, 550);
            Log.info("[SetGear]: SlayerVars.usingSpecialItem: " + SlayerVars.get().usingSpecialItem
                    + "  ||  SlayerVars.magicMeleeGear: " + SlayerVars.get().magicMeleeGear +
                    "  ||  SlayerVars.usingAntiFire: " + SlayerVars.get().usingAntiFire);
            return null;
        }
    }

    public static String getItemName(int ItemID) {

        RSItemDefinition itemDef = RSItemDefinition.get(ItemID);
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
            Log.info("[SetGear]: " + SlayerVars.get().status);
            BankManager.open(true);
            BankManager.depositAll(true);
            for (int item : gearToGet) {
                Log.info("[Gear Manager]: Getting " + getItemName(item));
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


    private static boolean equipItem(int ItemID) {
        RSItem[] invItemEquip = Inventory.find(Filters.Items.actionsEquals("Equip").and(Filters.Items.idEquals(ItemID)));
        RSItem[] invItemWield = Inventory.find(Filters.Items.actionsEquals("Wield").and(Filters.Items.idEquals(ItemID)));
        RSItem[] invItemWear = Inventory.find(Filters.Items.actionsEquals("Wear").and(Filters.Items.idEquals(ItemID)));

        if (invItemEquip.length > 0) {
            if (invItemEquip[0].click("Equip"))
                Timer.waitCondition(() -> Equipment.contains(ItemID), 1500, 2250);
            Utils.microSleep();
            return true;
        }
        if (invItemWield.length > 0) {
            if (invItemWield[0].click("Wield"))
                Timer.waitCondition(() -> Equipment.contains(ItemID), 1500, 2250);
            Utils.microSleep();
            return true;
        }
        if (invItemWear.length > 0) {
            if (invItemWear[0].click("Wear"))
                Timer.waitCondition(() -> Equipment.contains(ItemID), 1500, 2250);
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
        Log.info("[SetGear]: SetGear Task activated");
        getGear();
        //SlayerVars.get().needsToEquipGear = false;
    }

    @Override
    public boolean validate() {
        return (SlayerVars.get().shouldBank || Inventory.find(Filters.Items.actionsContains("Eat")).length < 3) // same as bank validiation
                || (checkGear() != null && Bank.isNearby()) && !SlayerVars.get().slayerShopRestock;
    }

    @Override
    public String taskName() {
        return "Slayer";
    }
}
