package scripts.Gear;

import lombok.Getter;
import org.tribot.api2007.Skills;
import scripts.ItemId;

import java.util.HashMap;

public enum ProgressiveMeleeGear {

    WEAPON(Skills.SKILLS.ATTACK, new HashMap<>() {
        {
            put(ItemId.DRAGON_SWORD, 60);
            put(ItemId.RUNE_SCIMITAR, 40);
            put(ItemId.ADAMANT_SCIMITAR, 30);
            put(ItemId.MITHRIL_SCIMITAR, 20);
       //     put(ItemId.STEEL_SCIMITAR, 5);
            put(ItemId.IRON_SCIMITAR, 1);
        }
    }),

    HELM(new HashMap<>() {
        {
            //  put(ItemId.OBSIDIAN_HELM, 60);
            put(ItemId.RUNE_FULL_HELM, 40);
            put(ItemId.ADAMANT_FULL_HELM, 30);
            put(ItemId.MITHRIL_FULL_HELM, 20);
            //  put(ItemId.STEEL_FULL_HELM, 5);
            put(ItemId.IRON_FULL_HELM, 1);
        }
    }),

    BODY(new HashMap<>() {
        {
            // put(ItemId.OBSIDIAN_PLATEBODY, 60);
            put(ItemId.RUNE_PLATEBODY, 40);
            put(ItemId.ADAMANT_PLATEBODY, 30);
            put(ItemId.MITHRIL_PLATEBODY, 20);
            put(ItemId.STEEL_PLATEBODY, 5);
            put(ItemId.IRON_PLATEBODY, 1);
        }
    }),

    LEGS(new HashMap<>() {
        {
            // put(ItemId.OBSIDIAN_PLATELEGS, 60);
            put(ItemId.DRAGON_PLATELEGS, 60);
            put(ItemId.RUNE_PLATELEGS, 40);
            put(ItemId.ADAMANT_PLATELEGS, 30);
            put(ItemId.MITHRIL_PLATELEGS, 20);
            put(ItemId.STEEL_PLATELEGS, 5);
            put(ItemId.IRON_PLATELEGS, 1);

        }
    }),

    SHIELD(new HashMap<>() {
        {
            put(ItemId.RUNE_KITESHIELD, 40);
            put(ItemId.ADAMANT_KITESHIELD, 30);
            put(ItemId.MITHRIL_KITESHIELD, 20);
            put(ItemId.STEEL_KITESHIELD, 5);
            put(ItemId.IRON_KITESHIELD, 1);
        }
    }),
    BOOTS,

    WRIST();

    @Getter
    private Skills.SKILLS skill;

    private HashMap<Integer, Integer> map;

    ProgressiveMeleeGear() {

    }

    ProgressiveMeleeGear(Skills.SKILLS skill,
                         HashMap<Integer, Integer> weaponMap) {
        this.skill = skill;
        this.map = weaponMap;
    }

    ProgressiveMeleeGear(HashMap<Integer, Integer> weaponMap) {
        this.skill = Skills.SKILLS.DEFENCE;
        this.map = weaponMap;
    }


    public static final int STEEL_SCIMITAR = 1325;

    public int getBestItemFromMap() {
        for (Integer i : this.map.keySet()) {
            if (this.skill.getActualLevel() >= this.map.get(i)) {
                return i;
            }
        }
        return -1;
    }

}


