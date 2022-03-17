package scripts.Data;

import org.tribot.script.sdk.Equipment;
import org.tribot.script.sdk.tasks.Amount;
import org.tribot.script.sdk.tasks.BankTask;
import org.tribot.script.sdk.tasks.EquipmentReq;
import org.tribot.script.sdk.types.WorldTile;
import scripts.ItemID;
import scripts.Utils;

public class Const {


   public static BankTask bankTask = BankTask.builder()
            .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.WEAPON).item(ItemID.STAFF_OF_AIR, Amount.of(1)))
            .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.NECK).chargedItem("Amulet of glory", 2))
            .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.RING)
                    .chargedItem("Ring of dueling", 1))
            .addInvItem(ItemID.COSMIC_RUNE, Amount.of(81))
            .addInvItem(ItemID.UNPOWERED_ORB, Amount.fill(27))
            .build();

    public static WorldTile OBILISK_TILE = new WorldTile(3088, 3570,0);
    public static int startInvValue = Utils.getInventoryValue();

    public static int HOUSE_AD_WIDGET_PARENT = 52;
}
