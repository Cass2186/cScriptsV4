package scripts.Tasks.Combat.Data;

import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.Equipment;
import org.tribot.script.sdk.tasks.Amount;
import org.tribot.script.sdk.tasks.BankTask;
import org.tribot.script.sdk.tasks.EquipmentReq;
import scripts.ItemID;

public class Const {

    private static Const consts;

    public static Const get() {
        return consts == null ? consts = new Const() : consts;
    }

    public int MONKFISH = 7946;

    public int[] FOOD_IDS = {
            329, //salmon
            379, //lobster
            MONKFISH,
            385, //shark
    };

    public static void reset() {
        consts = new Const();
    }

    public static final int[] STAMINA_POTION = {12625, 12627, 12629, 12631};
    public static final int[] SKILLS_NECKLACE = {11968, 11970, 11105, 11107, 11109, 11111};
    public static final int[] RING_OF_DUELING = {2552, 2554, 2556, 2558, 2560, 2562, 2564, 2566};
    public static final int[] RING_OF_WEALTH = {11980, 11982, 11984, 11986, 11988};
    public static final int[] AMULET_OF_GLORY = {11978, 11976, 1712, 1710, 1708, 1706};
    public static final int[] GAMES_NECKLACE = {3853, 3855, 3857, 3859, 3861, 3863, 3865, 3867};
    public static final int[] NECKLACE_OF_PASSAGE = {21146, 21149, 21151, 21153, 21155};
    public static final int[] PRAYER_POTION = {2434, 139, 141, 143};
    public static final int[] ANTIDOTE_PLUS_PLUS = {5952, 5954, 5956, 5956};
    public static final int[] COMBAT_POTION = {9739, 9741, 9743, 9745};
    public static final int[] RANGING_POTION = {2444, 169, 171, 173};
    public static final int[] SUPER_STRENGTH_POTION = {2440, 157, 159, 161};
    public static final int[] SUPER_ATTACK_POTION = {2436, 145, 147, 149};
    public static final int[] SUPER_DEFENCE_POTION = {2442, 163, 165, 167};


    public static int SANDICRAHB_NPC = 7483;

    public static final int CHEST_ID = 10562;

    public static RSTile NORTH_WEST1 = new RSTile(1764, 3445, 0);
    public static RSTile NORTH_WEST2 = new RSTile(1758, 3439, 0);
    public static RSTile WEST = new RSTile(1751, 3425, 0);
    public static RSTile SOUTH_WEST = new RSTile(1749, 3412, 0);
    public static RSTile SOUTH = new RSTile(1768, 3409, 0);
    public static RSTile SOUTH_EAST1 = new RSTile(1780, 3407, 0);
    public static RSTile SOUTH_EAST2 = new RSTile(1786, 3404, 0);
    public static RSTile NORTH_EAST1 = new RSTile(1777, 3429, 0);
    public static RSTile NORTH_EAST2 = new RSTile(1780, 3438, 0);
    public static RSTile FOSSIL_ISLAND_1 = new RSTile(3718, 3846, 0); //has 3 crabs, south centre
    public static RSTile FOSSIL_ISLAND_2 = new RSTile(3717, 3869, 0);
    public static RSTile FOSSIL_ISLAND_3 = new RSTile(3716, 3889, 0);
    public static RSTile FOSSIL_ISLAND_4 = new RSTile(3733, 3846, 0);
    public static RSTile CHEST_TILE = new RSTile(1719, 3465);

    public static RSArea SW_RESET_AREA = new RSArea(new RSTile(1745, 3415, 0), new RSTile(1755, 3406, 0));
    public static RSArea WEST_RESET_AREA = new RSArea(new RSTile(1748, 3427, 0), new RSTile(1756, 3417, 0));
    public static RSArea NW_RESET_AREA = new RSArea(new RSTile(1756, 3446, 0), new RSTile(1764, 3436, 0));
    public static RSArea SE_RESET_AREA = new RSArea(new RSTile(1793, 3410, 0), new RSTile(1783, 3403, 0));
    public static RSArea NE_RESET_AREA = new RSArea(new RSTile(1776, 3434, 0), new RSTile(1782, 3426, 0));
    public static RSArea BANK_AREA = new RSArea(new RSTile(1717, 3466, 0), new RSTile(1722, 3461, 0));
    public static RSArea BEACH_AREA = new RSArea(new RSTile(1786, 3457, 0), new RSTile(1780, 3461, 0));
    public static RSArea SANDCRAB_ISLAND = new RSArea(new RSTile[]{new RSTile(1764, 3451, 0), new RSTile(1742, 3427, 0), new RSTile(1744, 3404, 0), new RSTile(1803, 3399, 0), new RSTile(1799, 3418, 0), new RSTile(1783, 3444, 0), new RSTile(1778, 3450, 0)});


    public static int[] SAND_CRAB_IDS = {7206, 5935, 7799};
    public static int[] SAND_ROCKS_IDS = {7207, 5936, 7800};
    public static int AMMONITE_ROCK_ID = 7800;
    public static int AMMONITE_CRAB_ID = 7799;

    public static int getBestScimitar() {
        int level = Skills.getActualLevel(Skills.SKILLS.ATTACK);
        if (level < 5) {
            return ItemID.IRON_SCIMITAR;
        } else if (level < 20) {
          //  return ItemID.STEEL_SCIMITAR;
        }
        else if (level < 30) {
            return ItemID.MITHRIL_SCIMITAR;
        }
        else if (level < 40) {
            return ItemID.ADAMANT_SCIMITAR;
        }
        else if (level < 60) {
            return ItemID.RUNE_SCIMITAR;
        }
        else {
            return ItemID.DRAGON_SWORD;
        }
        return -1;
    }

    public enum ARMOR{
        IRON,
        STEEL,
        MITHRIL,
        ADAMANT,
        RUNE,
        DRAGON
    }

    public static ARMOR getBestArmorType() {
        int level = Skills.getActualLevel(Skills.SKILLS.DEFENCE);
        if (level < 5) {
            return ARMOR.IRON;
        } else if (level < 20) {
            return ARMOR.STEEL;
        }
        else if (level < 30) {
            return ARMOR.MITHRIL;
        }
        else if (level < 40) {
            return ARMOR.ADAMANT;
        }
        else if (level < 60) {
            return ARMOR.RUNE;
        }
        else {
            return ARMOR.DRAGON;
        }
    }

    public static BankTask getArmorBankTask(){
       switch(getBestArmorType()){
           case IRON:
               return BankTask.builder()
                       .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.HEAD).item(ItemID.IRON_FULL_HELM, Amount.of(1)))
                       .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.BODY).item(ItemID.IRON_PLATEBODY, Amount.of(1)))
                       .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.LEGS).item(ItemID.IRON_PLATELEGS, Amount.of(1)))
                       .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.SHIELD).item(ItemID.IRON_KITESHIELD, Amount.of(1)))
                       .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.HANDS).chargedItem("Combat bracelet", 1))
                       .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.HANDS).chargedItem("Amulet of glory", 1))
                       .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.HANDS).chargedItem("Ring of wealth", 1))
                       // .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.FEET).item(ItemID.IRON_BOOTS, Amount.of(1)))
                       .build();
           case STEEL:

       }
       return null;
    }





}
