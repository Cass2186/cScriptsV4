package scripts.Gear;

import lombok.Getter;
import org.tribot.api2007.Skills;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.types.definitions.ItemDefinition;
import scripts.ItemID;


import java.util.*;
import java.util.stream.Collectors;

public enum ProgressiveMeleeGear {

    WEAPON(Skills.SKILLS.ATTACK, new HashMap<>() {
        {
            put(ItemID.DRAGON_SWORD, 60);
            put(ItemID.RUNE_SCIMITAR, 40);
            put(ItemID.ADAMANT_SCIMITAR, 30);
            put(ItemID.MITHRIL_SCIMITAR, 20);
            put(ItemID.STEEL_SCIMITAR, 5);
            put(ItemID.IRON_SCIMITAR, 1);
        }
    }),

    HELM(new HashMap<>() {
        {
            //  put(ItemID.OBSIDIAN_HELM, 60);
            put(ItemID.RUNE_FULL_HELM, 40);
            put(ItemID.ADAMANT_FULL_HELM, 30);
            put(ItemID.MITHRIL_FULL_HELM, 20);
            put(ItemID.STEEL_FULL_HELM, 5);
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
    BOOTS(new HashMap<>() {
        {
            put(ItemID.RUNE_BOOTS, 40);
            put(ItemID.ADAMANT_BOOTS, 30);
            put(ItemID.MITHRIL_BOOTS, 20);
            put(ItemID.STEEL_BOOTS, 5);
            put(ItemID.IRON_BOOTS, 1);
        }
    }),

    WRIST(new HashMap<>() {
        {
            put(ItemID.COMBAT_BRACELET0, 1);


        }
    }),

    NECKLACe(new HashMap<>() {{
            put(ItemID.AMULET_OF_GLORY0, 1);

        }
    });


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


    public static List<Integer> getBestUsableGearList() {
        List<Integer> gearList = new ArrayList<>();
        for (ProgressiveMeleeGear gear : values()) {
            for (Integer i : gear.map.keySet().stream().sorted(Comparator
                    .comparingInt(max -> gear.map.get(max))
                    .reversed()).collect(Collectors.toList())) {

                if (gear.skill.getActualLevel() >= gear.map.get(i)) {
                    Optional<ItemDefinition> itemDefinition = ItemDefinition.get(i);
                    itemDefinition.ifPresent(def ->
                            Log.debug("Adding " + def.getName() + " to item List"));
                    //  Log.debug("Adding " + i + " to item List");
                    gearList.add(i);
                    break;
                }
            }
        }
        return gearList;
    }

}


