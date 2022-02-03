package scripts.Tasks.Combat.CrabTasks;

import org.tribot.api2007.Skills;
import org.tribot.script.sdk.Equipment;
import org.tribot.script.sdk.tasks.Amount;
import org.tribot.script.sdk.tasks.BankTask;
import org.tribot.script.sdk.tasks.EquipmentReq;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.ItemID;

public class CrabBank implements Task {


    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return Vars.get().currentTask != null &&
                Vars.get().currentTask.equals(SkillTasks.ATTACK) ||
                Vars.get().currentTask.equals(SkillTasks.STRENGTH) ||
                Vars.get().currentTask.equals(SkillTasks.DEFENCE) ||
                Vars.get().currentTask.equals(SkillTasks.RANGED);
    }

    @Override
    public void execute() {

    }

    @Override
    public String taskName() {
        return "Combat Training";
    }

    @Override
    public String toString() {
        return "Crab Banking";
    }


    public BankTask getRangedGearTask() {
        if (Skills.SKILLS.RANGED.getActualLevel() >= 70) {

        } else if (Skills.SKILLS.RANGED.getActualLevel() >= 60) {
            return BankTask.builder()
                    .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.HEAD).item(ItemID.SNAKESKIN_BANDANA, Amount.of(1)))
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

        }
        else if (Skills.SKILLS.RANGED.getActualLevel() >= 40) {
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

        }
        else if (Skills.SKILLS.RANGED.getActualLevel() >= 30) {
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


}
