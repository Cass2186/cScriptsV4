package scripts.VorkUtils;

import org.tribot.api.General;
import org.tribot.api2007.Combat;
import org.tribot.script.sdk.Equipment;
import org.tribot.script.sdk.tasks.Amount;
import org.tribot.script.sdk.tasks.BankTask;
import org.tribot.script.sdk.tasks.EquipmentReq;
import org.tribot.script.sdk.types.EquipmentItem;
import scripts.ItemID;
import scripts.Timer;

import java.util.*;

public class Vars {


    private static Vars vars;

    public static Vars get() {
        return vars == null ? vars = new Vars() : vars;
    }


    public static void reset() {
        vars = new Vars();
    }

    public int eatAtHP = Combat.getMaxHP() - General.randomSD(35, 4);

    public Timer antiFireTimer = new Timer(0); //5-5.9 min

    public int drinkPrayerAt = General.randomSD(5, 40, 18, 7);

    public int foodId = 391;

    public int minimumFoodAmount = 3;

    public boolean collectDeath = false;

    public HashMap<Equipment.Slot, Integer> gearMap = new HashMap<>();

    /**
     * Call on start
     */
    public  void populateEquipmentMap() {
        List<EquipmentItem> myEquip = Equipment.getAll();
        for (EquipmentItem item : myEquip) {
                gearMap.put(item.getSlot(), item.getId());
        }
    }

    public BankTask bankTask = BankTask.builder()
            .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.HEAD).item(ItemID.VOID_RANGER_HELM, Amount.of(1)))
            .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.NECK).item(ItemID.SALVE_AMULETEI, Amount.of(1)))
            .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.BODY).item(ItemID.VOID_KNIGHT_TOP, Amount.of(1)))
            .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.WEAPON).item(ItemID.DRAGON_HUNTER_CROSSBOW, Amount.of(1)))
            .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.LEGS).item(ItemID.VOID_KNIGHT_ROBE, Amount.of(1)))
            .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.SHIELD).item(ItemID.ANTIDRAGON_SHIELD, Amount.of(1)))
            .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.HANDS).item(ItemID.VOID_KNIGHT_GLOVES, Amount.of(1)))
            .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.AMMO).item(ItemID.RUBY_DRAGON_BOLTS_E, Amount.fill(90)))
            .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.FEET).item(ItemID.GUTHIX_DHIDE_BOOTS, Amount.of(1)))
            .addInvItem(ItemID.DIAMOND_DRAGON_BOLTS_E, Amount.fill(50))
            .addInvItem(ItemID.PRAYER_POTION4, Amount.of(3))
            .addInvItem(ItemID.ANTI_VENOM_PLUS[0], Amount.of(1))
            .addInvItem(ItemID.EXTENDED_SUPER_ANTIFIRE_POTION[0], Amount.of(1))
            .addInvItem(ItemID.DIVINE_BASTION_POTION4, Amount.of(1))
            .addInvItem(ItemID.RUNE_POUCH, Amount.of(1))
            .addInvItem(ItemID.MANTA_RAY, Amount.fill(12))
            .build();

}
