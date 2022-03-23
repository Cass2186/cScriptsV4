package scripts.Tasks;


import org.tribot.api.General;
import org.tribot.api2007.Skills;
import org.tribot.script.sdk.Equipment;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.tasks.Amount;
import org.tribot.script.sdk.tasks.BankTask;
import org.tribot.script.sdk.tasks.EquipmentReq;
import scripts.BankManager;
import scripts.Data.Const;
import scripts.Data.Vars;

import scripts.Gear.ProgressiveMeleeGear;
import scripts.ItemID;
import scripts.PathingUtil;
import scripts.Requirements.InventoryRequirement;
import scripts.Requirements.ItemReq;
import scripts.Requirements.ItemRequirement;
import scripts.Utils;


import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Bank implements Task {

    public void bank() {
        General.println("[Debug]: Banking");
        PathingUtil.walkToArea(Const.BANK_AREA);
        if (getRangedGearTask() != null)
            getRangedGearTask().execute();
        //  BankManager.open(true);
        //  BankManager.depositAll(true);
        BankManager.withdraw(11000, true, ItemID.COINS);
        BankManager.withdraw(12, true, Const.get().FOOD_IDS[1]);
        if (Vars.get().ranging) {
            BankManager.withdraw(10, true, ItemID.RANGING_POTION[0]);
        }
        BankManager.withdraw(1, true, ItemID.STAMINA_POTION[0]);
        BankManager.close(true);
    }

    public BankTask getRangedGearTask() {
        if (Skills.SKILLS.RANGED.getActualLevel() >= 70) {

        } else if (Skills.SKILLS.RANGED.getActualLevel() >= 60) {
            return BankTask.builder()
                    .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.HEAD).item(ItemID.ARCHER_HELM, Amount.of(1)))
                    .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.BODY).item(ItemID.RED_DHIDE_BODY, Amount.of(1)))
                    .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.HANDS).item(ItemID.RED_DHIDE_VAMBRACES, Amount.of(1)))
                    .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.LEGS).item(ItemID.RED_DHIDE_CHAPS, Amount.of(1)))
                    .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.FEET).item(ItemID.SNAKESKIN_BOOTS, Amount.of(1)))
                    .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.WEAPON).item(ItemID.MAGIC_SHORTBOW, Amount.of(1)))
                    .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.NECK).chargedItem("Amulet of glory", 1))
                    .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.RING).chargedItem("Ring of wealth", 1))
                    .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.AMMO).item(ItemID.RUNE_ARROW, Amount.fill(750)))
                    .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.CAPE).item(ItemID.AVAS_ACCUMULATOR, Amount.of(1)))
                    .build();

        } else if (Skills.SKILLS.RANGED.getActualLevel() >= 50) {
            return BankTask.builder()
                    .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.HEAD).item(ItemID.SNAKESKIN_BANDANA, Amount.of(1)))
                    .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.BODY).item(ItemID.BLUE_DHIDE_BODY, Amount.of(1)))
                    .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.HANDS).item(ItemID.BLUE_DHIDE_VAMBRACES, Amount.of(1)))
                    .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.LEGS).item(ItemID.BLUE_DHIDE_CHAPS, Amount.of(1)))
                    .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.FEET).item(ItemID.SNAKESKIN_BOOTS, Amount.of(1)))
                    .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.WEAPON).item(ItemID.MAGIC_SHORTBOW, Amount.of(1)))
                    .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.NECK).chargedItem("Amulet of glory", 1))
                    .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.RING).chargedItem("Ring of wealth", 1))
                    .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.AMMO).item(ItemID.RUNE_ARROW, Amount.fill(750)))
                    .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.CAPE).item(ItemID.AVAS_ACCUMULATOR, Amount.of(1)))
                    .build();

        } else if (Skills.SKILLS.RANGED.getActualLevel() >= 40) {
            return BankTask.builder()
                    .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.HEAD).item(ItemID.SNAKESKIN_BANDANA, Amount.of(1)))
                    .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.BODY).item(ItemID.GREEN_DHIDE_BODY, Amount.of(1)))
                    .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.HANDS).item(ItemID.GREEN_DHIDE_VAMBRACES, Amount.of(1)))
                    .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.LEGS).item(ItemID.GREEN_DHIDE_CHAPS, Amount.of(1)))
                    .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.FEET).item(ItemID.SNAKESKIN_BOOTS, Amount.of(1)))
                    .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.WEAPON).item(ItemID.YEW_SHORTBOW, Amount.of(1)))
                    .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.NECK).chargedItem("Amulet of glory", 1))
                    .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.RING).chargedItem("Ring of wealth", 1))
                    .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.AMMO).item(ItemID.RUNE_ARROW, Amount.fill(750)))
                    .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.CAPE).item(ItemID.AVAS_ATTRACTOR, Amount.of(1)))
                    .build();

        } else if (Skills.SKILLS.RANGED.getActualLevel() >= 30) {
            return BankTask.builder()
                    .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.HEAD).item(ItemID.SNAKESKIN_BANDANA, Amount.of(1)))
                    .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.BODY).item(ItemID.BLUE_DHIDE_BODY, Amount.of(1)))
                    .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.HANDS).item(ItemID.BLUE_DHIDE_VAMBRACES, Amount.of(1)))
                    .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.LEGS).item(ItemID.BLUE_DHIDE_CHAPS, Amount.of(1)))
                    .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.FEET).item(ItemID.SNAKESKIN_BOOTS, Amount.of(1)))
                    .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.WEAPON).item(ItemID.MAGIC_SHORTBOW, Amount.of(1)))
                    .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.NECK).chargedItem("Amulet of glory", 1))
                    .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.RING).chargedItem("Ring of wealth", 1))
                    .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.AMMO).item(ItemID.RUNE_ARROW, Amount.fill(750)))
                    .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.CAPE).item(ItemID.AVAS_ACCUMULATOR, Amount.of(1)))
                    .build();

        }
        return null;
    }

    private boolean isBestGearEquipped() {
        List<Integer> bestList = ProgressiveMeleeGear.getBestUsableGearList();
        boolean b = bestList.stream().allMatch(Equipment::contains);
        Log.debug("Is Best Gear Equipped: " + b);
        return b;
    }


    @Override
    public String toString() {
        return "Banking";
    }

    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        return Vars.get().shouldBank || (Vars.get().ranging && !getRangedGearTask().isSatisfied()) ;
    }

    @Override
    public void execute() {
        bank();
    }

}

