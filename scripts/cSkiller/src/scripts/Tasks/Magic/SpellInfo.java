package scripts.Tasks.Magic;

import lombok.Getter;
import org.tribot.api.General;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Skills;
import org.tribot.script.sdk.Equipment;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.tasks.Amount;
import org.tribot.script.sdk.tasks.BankTask;
import org.tribot.script.sdk.tasks.EquipmentReq;
import scripts.ItemID;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;

import scripts.Requirements.ItemReq;
import scripts.Utils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public enum SpellInfo {

    SAPPHIRE_ENCHANT("Lvl-1 Enchant", SPELL_TYPE.ENCHANT, 17, ItemID.COSMIC_RUNE,
            ItemID.SAPPHIRE_RING, ItemID.STAFF_OF_WATER, 7, 27),

    EMERALD_ENCHANT("Lvl-2 Enchant", SPELL_TYPE.ENCHANT, 27, ItemID.COSMIC_RUNE,
            ItemID.EMERALD_RING, ItemID.STAFF_OF_AIR, 27, 37),

    FALADOR_TELEPORT("Falador Teleport", SPELL_TYPE.TELEPORT, 48,
            List.of(ItemID.LAW_RUNE, ItemID.WATER_RUNE),
            ItemID.STAFF_OF_AIR, 37, 45),

    CAMELOT_TELEPORT("Camelot Teleport", SPELL_TYPE.TELEPORT, 55, List.of(ItemID.LAW_RUNE),
            ItemID.STAFF_OF_AIR, 45, 55),

    HIGH_ALCHEMY("High Level Alchemy", SPELL_TYPE.ALCH, 65, List.of(ItemID.NATURE_RUNE,
            Utils.getNotedItemID(Vars.get().alchItem.getId())),
            ItemID.STAFF_OF_FIRE, 55,99),

    DIAMOND_ENCHANT("Lvl-4 Enchant", SPELL_TYPE.ENCHANT, 67, ItemID.COSMIC_RUNE,
            ItemID.DIAMOND_BRACELET, ItemID.MUD_BATTLESTAFF, 57, SkillTasks.MAGIC.getEndLevel());


    SpellInfo(String spellString, SPELL_TYPE spellType, double expGained, int rune, int ItemId,
              int staffId, int minLevel, int maxLevel) {
        this.spellString = spellString;
        this.xpPer = expGained;
        this.runes = List.of(rune);
        this.spellType = spellType;
        this.staffId = staffId;
        this.ItemId = ItemId;
        this.maxLevel = maxLevel;
        this.minLevel = minLevel;

    }

    SpellInfo(String spellString, SPELL_TYPE spellType, double expGained, List<Integer> runeIds,
              int staffId, int minLevel, int maxLevel) {
        this.spellString = spellString;
        this.xpPer = expGained;
        this.runes = runeIds;
        this.spellType = spellType;
        this.staffId = staffId;
        this.maxLevel = maxLevel;
        this.minLevel = minLevel;

    }


    /**
     * use for teleports where there is no ItemID
     *
     * @param spellString
     * @param expGained
     * @param rune        the additional rune that is not a Law rune or covered by staff
     * @param staffId
     */
    SpellInfo(String spellString, SPELL_TYPE spellType, double expGained, int rune,
              int staffId, int minLevel, int maxLevel) {
        this.spellString = spellString;
        this.xpPer = expGained;
        this.runes = List.of(rune);
        this.spellType = SPELL_TYPE.TELEPORT;
        this.staffId = staffId;
    }

    @Getter
    private String spellString;

    private double xpPer;

    @Getter
    private List<Integer> runes;

    @Getter
    private int ItemId;

    @Getter
    private int staffId;

    private SPELL_TYPE spellType;
    private int minLevel;
    private int maxLevel;

    enum SPELL_TYPE {
        ENCHANT,
        TELEPORT,
        ALCH
    }

    private static Skills.SKILLS skill = Skills.SKILLS.MAGIC;

    public int determineResourcesToNextItem() {
        if (Skills.getActualLevel(skill) >= this.maxLevel)
            return 0;
        int max = this.maxLevel;
        int xpTillMax = Skills.getXPToLevel(skill, SkillTasks.MAGIC.getEndLevel());
        if (max < SkillTasks.MAGIC.getEndLevel()) {
            xpTillMax = Skills.getXPToLevel(skill, this.maxLevel);
        }
        General.println("[SpellItems]: DetermineResourcesToNextItem: " + (xpTillMax / this.xpPer));
        return (int) (xpTillMax / this.xpPer) + 5;
    }


    public static Optional<SpellInfo> getCurrentSpell() {
        for (SpellInfo i : values()) {
            boolean b = Skills.getActualLevel(skill) < i.maxLevel;
            // Log.log("b = " + b + " for " + i.toString() + " max: " + i.maxLevel);
            if (Skills.getActualLevel(skill) >= i.minLevel &&
                    Skills.getActualLevel(skill) < i.maxLevel) {
                // Log.log("[Debug]: Returning spell " + i.toString());
                return Optional.of(i);
            }
        }
        // Log.log("[SpellInfo]: returning an empty optional");
        return Optional.empty();
    }

    public boolean hasRequiredItems() {
        for (Integer r : runes) {
            if (Inventory.find(r).length == 0) {
                Log.log("[SpellInfo]: We are missing a rune");
                return false;
            }
        }
        return org.tribot.api2007.Equipment.isEquipped(this.staffId);
    }

    public static List<ItemReq> getRequiredItemList() {
        List<ItemReq> i = new ArrayList<>();
        if (getCurrentSpell().isPresent()) {
            Log.log(getCurrentSpell().get().toString());
            if (getCurrentSpell().get().getItemId() != 0)
                getCurrentSpell().ifPresent(h -> i.add(new ItemReq(h.getItemId(), h.determineResourcesToNextItem())));
            getCurrentSpell().ifPresent(h -> i.add(new ItemReq(h.getStaffId(), 1, true)));
            List<Integer> runes = getCurrentSpell().get().getRunes();
            for (Integer r : runes) {
                getCurrentSpell().ifPresent(h -> i.add(new ItemReq(r, h.determineResourcesToNextItem())));
            }

            General.println("[SpellItems]: We need " + getCurrentSpell().get().determineResourcesToNextItem() +
                    " items", Color.BLACK);

            General.println("[SpellItems]: We need " + i.size() + " sized list for Magic items", Color.BLACK);
        } else
            Log.log("[SpellInfo]: No Required itemList");
        return i;
    }

    public BankTask getBankTask() {
        BankTask bnk;
        if (this.spellType.equals(SPELL_TYPE.ENCHANT)) {
            return BankTask.builder()
                    .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.WEAPON).item(this.staffId, Amount.of(1)))
                    .addInvItem(ItemID.COSMIC_RUNE, Amount.fill(1))
                    .addInvItem(this.ItemId, Amount.fill(1))
                    .build();
        } else if (this.spellType == SPELL_TYPE.TELEPORT) {
            if (this.runes.size() == 1) {
                bnk = BankTask.builder()
                        .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.WEAPON).item(this.staffId, Amount.of(1)))
                        .addInvItem(ItemID.LAW_RUNE, Amount.fill(1))
                        .addInvItem(this.runes.get(0), Amount.fill(1))
                        .build();

            } else if (this.runes.size() == 2) {
                bnk = BankTask.builder()
                        .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.WEAPON).item(this.staffId, Amount.of(1)))
                        .addInvItem(ItemID.LAW_RUNE, Amount.fill(1))
                        .addInvItem(this.runes.get(0), Amount.fill(1))
                        .addInvItem(this.runes.get(1), Amount.fill(1))
                        .build();

            } else {
                bnk = BankTask.builder()
                        .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.WEAPON).item(this.staffId, Amount.of(1)))
                        .addInvItem(ItemID.LAW_RUNE, Amount.fill(1))
                        .build();

            }
        } else {
            bnk = BankTask.builder()
                    .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.WEAPON).item(this.staffId, Amount.of(1)))
                    .addInvItem(ItemID.NATURE_RUNE, Amount.fill(1))
                    .addInvItem(this.ItemId+1, Amount.fill(1))
                    .build();
        }

        return bnk;

    }

}
