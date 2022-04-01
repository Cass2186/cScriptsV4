package scripts.Data;

import com.google.errorprone.annotations.Var;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.Equipment;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.tasks.Amount;
import org.tribot.script.sdk.tasks.BankTask;
import org.tribot.script.sdk.tasks.EquipmentReq;
import org.tribot.script.sdk.types.WorldTile;
import scripts.ItemID;
import scripts.Utils;

import java.util.function.BooleanSupplier;

public class Const {
    public static BankTask bankTask = BankTask.builder()
            .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.WEAPON).item(ItemID.STAFF_OF_AIR, Amount.of(1)))
            .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.NECK).chargedItem("Amulet of glory", 2))
            .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.RING)
                    .chargedItem("Ring of dueling", 1))
            .addInvItem(ItemID.COSMIC_RUNE, Amount.of(81))
            .addInvItem(ItemID.UNPOWERED_ORB, Amount.fill(27))
            .build();


    public static int startInvValue = Utils.getInventoryValue();

    public static boolean waitCondition(BooleanSupplier sup, int timeOut) {
        return Waiting.waitUntil(timeOut, () -> {
            Waiting.waitNormal(Vars.get().avgWaitLoopSleep, Vars.get().sdWaitLoopSleep);
            return sup.getAsBoolean();
        });
    }

    public static boolean waitCondition(BooleanSupplier sup, int timeOutLow, int timeOutHigh) {
        return Waiting.waitUntil(Utils.random(timeOutLow, timeOutHigh), () -> {
            Waiting.waitNormal(Vars.get().avgWaitLoopSleep, Vars.get().sdWaitLoopSleep);
            return sup.getAsBoolean();
        });
    }

    public static final int PARENT_INTERFACE_NPC_CONTACT = 75;
    public static final int PARENT_EQUIPMENT_INTERFACE = 387;

    public static String imbueOn = "You are charged to combine runes";
    public static String imbueOff = "charge has ended";
    public static RSTile COSMIC_PORTAL_TILE = new RSTile(2122, 4833, 0);
    public static RSArea EARTH_ALTAR_AREA = new RSArea(new RSTile(3300, 3475, 0), new RSTile(3303, 3472, 0));
    public static RSArea FIRE_ALTAR_AREA = new RSArea(new RSTile(3315, 3251, 0), new RSTile(3311, 3255, 0));
    public static RSArea FIRE_ALTAR_TILE = new RSArea(new RSTile(2583, 4840, 0), 3);
    public static RSArea CASTLE_WARS_AREA = new RSArea(new RSTile(2442, 3084, 0), 1);
    public static RSArea ZANARIS_BANK = new RSArea(
            new RSTile[]{
                    new RSTile(2386, 4463, 0),
                    new RSTile(2382, 4463, 0),
                    new RSTile(2380, 4461, 0),
                    new RSTile(2380, 4456, 0),
                    new RSTile(2382, 4454, 0),
                    new RSTile(2386, 4454, 0)
            }
    );
    public static RSArea ZANARIS_ALTAR = new RSArea(new RSTile(2407, 4381, 0), new RSTile(2414, 4376, 0));
    public static RSTile FIRE_ALTAR_TILE_BEFORE_RUINS = new RSTile(3312, 3253, 0);
    public static   RSArea CASTLE_WARS_BANK = new RSArea(new RSTile(2447, 3079, 0), new RSTile(2435, 3098, 0));
    public static   RSArea MIND_ALTAR_AREA = new RSArea(
            new RSTile[]{
                    new RSTile(2771, 4851, 0),
                    new RSTile(2772, 4830, 0),
                    new RSTile(2790, 4816, 0),
                    new RSTile(2805, 4825, 0),
                    new RSTile(2803, 4846, 0),
                    new RSTile(2789, 4855, 0)
            }
    );

    public static WorldTile FIRE_ALTAR_RING_TELE_TILE = new WorldTile(3309, 3260, 0);
}
