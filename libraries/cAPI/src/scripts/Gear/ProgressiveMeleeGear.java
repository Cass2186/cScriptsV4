package scripts.Gear;

import lombok.Getter;
import org.tribot.api2007.Skills;
import scripts.ItemID;


import java.util.HashMap;

public enum ProgressiveMeleeGear {

    WEAPON(Skills.SKILLS.ATTACK, new HashMap<>() {
        {
            put(ItemID.DRAGON_SWORD, 60);
            put(ItemID.RUNE_SCIMITAR, 40);
            put(ItemID.ADAMANT_SCIMITAR, 30);
            put(ItemID.MITHRIL_SCIMITAR, 20);
       //     put(ItemID.STEEL_SCIMITAR, 5);
            put(ItemID.IRON_SCIMITAR, 1);
        }
    }),

    HELM(new HashMap<>() {
        {
            //  put(ItemID.OBSIDIAN_HELM, 60);
            put(ItemID.RUNE_FULL_HELM, 40);
            put(ItemID.ADAMANT_FULL_HELM, 30);
            put(ItemID.MITHRIL_FULL_HELM, 20);
            //  put(ItemID.STEEL_FULL_HELM, 5);
            put(ItemID.IRON_FULL_HELM, 1);
        }
    }),

    BODY(new HashMap<>() {
        {
            // put(ItemID.OBSIDIAN_PLATEBODY, 60);
            put(ItemID.RUNE_PLATEBODY, 40);
            put(ItemID.ADAMANT_PLATEBODY, 30);
            put(ItemID.MITHRIL_PLATEBODY, 20);
            put(ItemID.STEEL_PLATEBODY, 5);
            put(ItemID.IRON_PLATEBODY, 1);
        }
    }),

    LEGS(new HashMap<>() {
        {
            // put(ItemID.OBSIDIAN_PLATELEGS, 60);
            put(ItemID.DRAGON_PLATELEGS, 60);
            put(ItemID.RUNE_PLATELEGS, 40);
            put(ItemID.ADAMANT_PLATELEGS, 30);
            put(ItemID.MITHRIL_PLATELEGS, 20);
            put(ItemID.STEEL_PLATELEGS, 5);
            put(ItemID.IRON_PLATELEGS, 1);

        }
    }),

    SHIELD(new HashMap<>() {
        {
            put(ItemID.RUNE_KITESHIELD, 40);
            put(ItemID.ADAMANT_KITESHIELD, 30);
            put(ItemID.MITHRIL_KITESHIELD, 20);
            put(ItemID.STEEL_KITESHIELD, 5);
            put(ItemID.IRON_KITESHIELD, 1);
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


