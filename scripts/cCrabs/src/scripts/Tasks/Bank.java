package scripts.Tasks;


import org.tribot.api.General;
import org.tribot.api2007.Skills;
import org.tribot.script.sdk.Equipment;
import org.tribot.script.sdk.tasks.Amount;
import org.tribot.script.sdk.tasks.BankTask;
import org.tribot.script.sdk.tasks.EquipmentReq;
import org.tribot.script.sdk.tasks.ItemReq;
import scripts.BankManager;
import scripts.Data.Const;
import scripts.Data.Vars;

import scripts.ItemId;
import scripts.PathingUtil;
import scripts.Requirements.ItemRequirement;


import java.nio.file.Path;

public class Bank implements Task {

    public void bank() {
        General.println("[Debug]: Banking");
        PathingUtil.walkToArea(Const.BANK_AREA);
        getRangedGearTask().execute();
      //  BankManager.open(true);
      //  BankManager.depositAll(true);
        BankManager.withdraw(11000, true, ItemId.COINS);
        BankManager.withdraw(12, true, Const.get().FOOD_IDS[1]);
        if (Vars.get().ranging){
            BankManager.withdraw(10, true, ItemId.RANGING_POTION[0]);
        }
        BankManager.withdraw(1, true, ItemId.STAMINA_POTION[0]);
        BankManager.close(true);
    }

    public BankTask getRangedGearTask() {
        if (Skills.SKILLS.RANGED.getActualLevel() >= 70) {

        } else if (Skills.SKILLS.RANGED.getActualLevel() >= 60) {

        } else if (Skills.SKILLS.RANGED.getActualLevel() >= 50) {
            return BankTask.builder()
                    .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.HEAD).item(ItemId.SNAKESKIN_BANDANA, Amount.of(1)))
                    .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.BODY).item(ItemId.BLUE_DHIDE_BODY, Amount.of(1)))
                    .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.HANDS).item(ItemId.BLUE_DHIDE_VAMBRACES, Amount.of(1)))
                    .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.LEGS).item(ItemId.BLUE_DHIDE_CHAPS, Amount.of(1)))
                    .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.FEET).item(ItemId.SNAKESKIN_BOOTS, Amount.of(1)))
                    .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.WEAPON).item(ItemId.MAGIC_SHORTBOW, Amount.of(1)))
                    .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.NECK).chargedItem("Amulet of glory", 1))
                    .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.RING).chargedItem("Ring of wealth", 1))
                    .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.AMMO).item(ItemId.RUNE_ARROW, Amount.fill(750)))
                    .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.CAPE).item(ItemId.AVAS_ACCUMULATOR, Amount.of(1)))
                    .build();

        }
        else if (Skills.SKILLS.RANGED.getActualLevel() >= 40) {
            return BankTask.builder()
                    .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.HEAD).item(ItemId.SNAKESKIN_BANDANA, Amount.of(1)))
                    .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.BODY).item(ItemId.GREEN_DHIDE_BODY, Amount.of(1)))
                    .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.HANDS).item(ItemId.GREEN_DHIDE_VAMBRACES, Amount.of(1)))
                    .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.LEGS).item(ItemId.GREEN_DHIDE_CHAPS, Amount.of(1)))
                    .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.FEET).item(ItemId.SNAKESKIN_BOOTS, Amount.of(1)))
                    .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.WEAPON).item(ItemId.YEW_SHORTBOW, Amount.of(1)))
                    .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.NECK).chargedItem("Amulet of glory", 1))
                    .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.RING).chargedItem("Ring of wealth", 1))
                    .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.AMMO).item(ItemId.RUNE_ARROW, Amount.fill(750)))
                    .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.CAPE).item(ItemId.AVAS_ATTRACTOR, Amount.of(1)))
                    .build();

        }
        else if (Skills.SKILLS.RANGED.getActualLevel() >= 30) {
            return BankTask.builder()
                    .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.HEAD).item(ItemId.SNAKESKIN_BANDANA, Amount.of(1)))
                    .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.BODY).item(ItemId.BLUE_DHIDE_BODY, Amount.of(1)))
                    .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.HANDS).item(ItemId.BLUE_DHIDE_VAMBRACES, Amount.of(1)))
                    .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.LEGS).item(ItemId.BLUE_DHIDE_CHAPS, Amount.of(1)))
                    .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.FEET).item(ItemId.SNAKESKIN_BOOTS, Amount.of(1)))
                    .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.WEAPON).item(ItemId.MAGIC_SHORTBOW, Amount.of(1)))
                    .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.NECK).chargedItem("Amulet of glory", 1))
                    .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.RING).chargedItem("Ring of wealth", 1))
                    .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.AMMO).item(ItemId.RUNE_ARROW, Amount.fill(750)))
                    .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.CAPE).item(ItemId.AVAS_ACCUMULATOR, Amount.of(1)))
                    .build();

        }
        return null;
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

