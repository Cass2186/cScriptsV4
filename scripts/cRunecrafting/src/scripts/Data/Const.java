package scripts.Data;

import com.google.errorprone.annotations.Var;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.Equipment;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.tasks.Amount;
import org.tribot.script.sdk.tasks.BankTask;
import org.tribot.script.sdk.tasks.EquipmentReq;
import org.tribot.script.sdk.types.Area;
import org.tribot.script.sdk.types.WorldTile;
import scripts.ItemID;
import scripts.Utils;

import java.util.Arrays;
import java.util.List;
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
    public static int BLOOD_ESSENCE_ACTIVE = 26392;

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

    public static final int BLOOD_ALTAR_ID = 27978;
    public static final WorldTile WILDERNESS_TILE = new WorldTile(3104, 3557, 0);
    public static final int PARENT_INTERFACE_NPC_CONTACT = 75;
    public static final int PARENT_EQUIPMENT_INTERFACE = 387;

    public static final  Area WHOLE_ABYSS= Area.fromRectangle(
            new WorldTile(3068, 4804, 0), new WorldTile(3010, 4861, 0));
    public static final  Area INNER_ABYSS = Area.fromPolygon(
            new WorldTile(3039, 4848, 0),
            new WorldTile(3027, 4845, 0),
            new WorldTile(3023, 4836, 0),
            new WorldTile(3025, 4823, 0),
            new WorldTile(3035, 4817, 0),
            new WorldTile(3047, 4817, 0),
            new WorldTile(3056, 4827, 0),
            new WorldTile(3052, 4844, 0)
    );
    public static String imbueOn = "You are charged to combine runes";
    public static String imbueOff = "charge has ended";
    public static RSTile COSMIC_PORTAL_TILE = new RSTile(2122, 4833, 0);
    public static Area EARTH_ALTAR_AREA = Area.fromRectangle(new WorldTile(3300, 3475, 0), new WorldTile(3303, 3472, 0));
    public static Area FIRE_ALTAR_AREA = Area.fromRectangle(new WorldTile(3315, 3251, 0), new WorldTile(3311, 3255, 0));
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
    public static RSArea CASTLE_WARS_BANK = new RSArea(new RSTile(2447, 3079, 0), new RSTile(2435, 3098, 0));
    public static RSArea MIND_ALTAR_AREA = new RSArea(
            new RSTile[]{
                    new RSTile(2771, 4851, 0),
                    new RSTile(2772, 4830, 0),
                    new RSTile(2790, 4816, 0),
                    new RSTile(2805, 4825, 0),
                    new RSTile(2803, 4846, 0),
                    new RSTile(2789, 4855, 0)
            }
    );
    public static int[] ALL_POUCHES = {
            5509, //small
            5510, // med
            5512,// large
            5514 // giant
    };
    public static final int DEGRADED_MEDIUM_POUCH = 5511;
    public static int DEGRADED_LARGE_POUCH = 5513;
    public static int DEGRADED_GIANT_POUCH = 5515;
    public static final int[] DEGRADED_POUCHES = {DEGRADED_GIANT_POUCH, DEGRADED_LARGE_POUCH, DEGRADED_MEDIUM_POUCH};
    public static final int BINDING_ANIMATION_ID = 791;
    public static final Area WHOLE_MINE_AREA = Area.fromRectangle(new WorldTile(1768, 3842, 0),
            new WorldTile(1759, 3862, 0));

    public static final Area ARCEUUS_MINE = Area.fromRectangle
            (new WorldTile(1758, 3861, 0),
                    new WorldTile(1769, 3844, 0));

    public static final RSTile NORTH_ROCK_OBSTACLE_TILE = new RSTile(1761, 3873, 0);
    public static final Area BEFORE_NORTH_OBSTACLE = Area.fromPolygon(
            new WorldTile[]{
                    new WorldTile(1764, 3876, 0),
                    new WorldTile(1764, 3881, 0),
                    new WorldTile(1761, 3881, 0),
                    new WorldTile(1760, 3880, 0),
                    new WorldTile(1757, 3880, 0),
                    new WorldTile(1755, 3878, 0),
                    new WorldTile(1753, 3878, 0),
                    new WorldTile(1750, 3875, 0),
                    new WorldTile(1750, 3869, 0),
                    new WorldTile(1753, 3872, 0),
                    new WorldTile(1754, 3872, 0),
                    new WorldTile(1755, 3873, 0),
                    new WorldTile(1756, 3873, 0),
                    new WorldTile(1758, 3873, 0)
            }
    );
    public static final List<WorldTile> PATH_TO_ESSENCE_FROM_DARK_ALTAR =
            Arrays.asList(
                    new WorldTile(1716, 3879, 0),
                    new WorldTile(1716, 3879, 0),
                    new WorldTile(1717, 3879, 0),
                    new WorldTile(1718, 3879, 0),
                    new WorldTile(1719, 3879, 0),
                    new WorldTile(1720, 3879, 0),
                    new WorldTile(1721, 3879, 0),
                    new WorldTile(1722, 3879, 0),
                    new WorldTile(1723, 3879, 0),
                    new WorldTile(1724, 3879, 0),
                    new WorldTile(1725, 3879, 0),
                    new WorldTile(1726, 3879, 0),
                    new WorldTile(1727, 3879, 0),
                    new WorldTile(1728, 3879, 0),
                    new WorldTile(1729, 3879, 0),
                    new WorldTile(1730, 3879, 0),
                    new WorldTile(1731, 3878, 0),
                    new WorldTile(1732, 3877, 0),
                    new WorldTile(1733, 3876, 0),
                    new WorldTile(1734, 3875, 0),
                    new WorldTile(1735, 3875, 0),
                    new WorldTile(1736, 3875, 0),
                    new WorldTile(1737, 3875, 0),
                    new WorldTile(1738, 3875, 0),
                    new WorldTile(1739, 3874, 0),
                    new WorldTile(1740, 3874, 0),
                    new WorldTile(1741, 3873, 0),
                    new WorldTile(1742, 3873, 0),
                    new WorldTile(1743, 3873, 0),
                    new WorldTile(1744, 3873, 0),
                    new WorldTile(1745, 3873, 0),
                    new WorldTile(1746, 3873, 0),
                    new WorldTile(1747, 3874, 0),
                    new WorldTile(1748, 3874, 0),
                    new WorldTile(1749, 3874, 0),
                    new WorldTile(1750, 3874, 0),
                    new WorldTile(1751, 3874, 0),
                    new WorldTile(1752, 3875, 0),
                    new WorldTile(1753, 3876, 0),
                    new WorldTile(1754, 3876, 0),
                    new WorldTile(1755, 3876, 0),
                    new WorldTile(1756, 3876, 0),
                    new WorldTile(1757, 3876, 0),
                    new WorldTile(1758, 3876, 0),
                    new WorldTile(1759, 3876, 0),
                    new WorldTile(1760, 3876, 0),
                    new WorldTile(1761, 3876, 0),
                    new WorldTile(1761, 3875, 0),
                    new WorldTile(1761, 3874, 0),
                    new WorldTile(1761, 3871, 0),
                    new WorldTile(1761, 3870, 0),
                    new WorldTile(1761, 3869, 0),
                    new WorldTile(1760, 3869, 0),
                    new WorldTile(1759, 3869, 0),
                    new WorldTile(1758, 3868, 0),
                    new WorldTile(1757, 3867, 0),
                    new WorldTile(1756, 3866, 0),
                    new WorldTile(1755, 3865, 0),
                    new WorldTile(1754, 3864, 0),
                    new WorldTile(1754, 3863, 0),
                    new WorldTile(1754, 3862, 0),
                    new WorldTile(1754, 3861, 0),
                    new WorldTile(1754, 3860, 0),
                    new WorldTile(1754, 3859, 0),
                    new WorldTile(1754, 3858, 0),
                    new WorldTile(1754, 3857, 0),
                    new WorldTile(1754, 3856, 0),
                    new WorldTile(1754, 3855, 0),
                    new WorldTile(1754, 3854, 0),
                    new WorldTile(1754, 3853, 0),
                    new WorldTile(1754, 3852, 0),
                    new WorldTile(1754, 3851, 0),
                    new WorldTile(1755, 3850, 0),
                    new WorldTile(1756, 3850, 0),
                    new WorldTile(1757, 3850, 0),
                    new WorldTile(1758, 3850, 0),
                    new WorldTile(1758, 3851, 0),
                    new WorldTile(1758, 3852, 0),
                    new WorldTile(1759, 3853, 0),
                    new WorldTile(1760, 3853, 0),
                    new WorldTile(1761, 3853, 0),
                    new WorldTile(1762, 3853, 0),
                    new WorldTile(1763, 3853, 0),
                    new WorldTile(1764, 3854, 0)
            );

    public static final Area DARK_ALTAR_AREA =
            Area.fromPolygon(List.of(
                    new WorldTile(1706, 3887, 0),
                    new WorldTile(1709, 3875, 0),
                    new WorldTile(1728, 3874, 0),
                    new WorldTile(1740, 3878, 0),
                    new WorldTile(1730, 3890, 0)));
    public static final RSTile TARGET_TILE_AT_DENSE_ESS = new RSTile(1763, 3847, 0);
    public static final RSArea BLOOD_ALTAR_AREA = new RSArea(new RSTile(1713, 3831, 0), new RSTile(1720, 3825, 0));
    public static final RSTile AFTER_N_OBSTACLE = new RSTile(1761, 3872, 0);
    public static final WorldTile BEFORE_N_OBSTACLE = new WorldTile(1761, 3874, 0);

    public static WorldTile FIRE_ALTAR_RING_TELE_TILE = new WorldTile(3309, 3260, 0);
}
