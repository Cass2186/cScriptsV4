package scripts.VorkUtils;

import org.tribot.api.General;
import org.tribot.api2007.Combat;
import org.tribot.script.sdk.Equipment;
import org.tribot.script.sdk.tasks.Amount;
import org.tribot.script.sdk.tasks.BankTask;
import org.tribot.script.sdk.tasks.EquipmentReq;
import org.tribot.script.sdk.types.EquipmentItem;
import scripts.ItemId;
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

    public int minimumFoodAmount = 5;

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
            .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.HEAD).item(ItemId.VOID_RANGER_HELM, Amount.of(1)))
            .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.NECK).item(ItemId.SALVE_AMULETEI, Amount.of(1)))
            .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.BODY).item(ItemId.VOID_KNIGHT_TOP, Amount.of(1)))
            .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.WEAPON).item(ItemId.DRAGON_HUNTER_CROSSBOW, Amount.of(1)))
            .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.LEGS).item(ItemId.VOID_KNIGHT_ROBE, Amount.of(1)))
            .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.SHIELD).item(ItemId.ANTI_DRAGON_SHIELD, Amount.of(1)))
            .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.HANDS).item(ItemId.VOID_KNIGHT_GLOVES, Amount.of(1)))
            .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.AMMO).item(ItemId.RUBY_DRAGON_BOLTS_E, Amount.fill(150)))
            .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.FEET).item(ItemId.GUTHIX_DHIDE_BOOTS, Amount.of(1)))
            .addInvItem(ItemId.DIAMOND_DRAGON_BOLTS_E, Amount.fill(200))
            .addInvItem(ItemId.PRAYER_POTION_4, Amount.of(3))
            .addInvItem(ItemId.ANTI_VENOM_PLUS[0], Amount.of(1))
            .addInvItem(ItemId.EXTENDED_SUPER_ANTIFIRE_POTION[0], Amount.of(1))
            .addInvItem(ItemId.DIVINE_BASTION_POTION4, Amount.of(1))
            .addInvItem(ItemId.RUNE_POUCH, Amount.of(1))
            .addInvItem(ItemId.MANTA_RAY, Amount.fill(12))
            .build();

}
