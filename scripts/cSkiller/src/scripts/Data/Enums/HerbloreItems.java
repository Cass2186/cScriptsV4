package scripts.Data.Enums;

import lombok.Getter;
import org.tribot.api.General;
import org.tribot.api2007.Skills;
import scripts.Data.SkillTasks;
import scripts.ItemId;
import scripts.Requirements.ItemReq;
import scripts.Utils;

import java.awt.*;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

public enum HerbloreItems {

    ATTACK_POTION(25, ItemId.EYE_OF_NEWT, ItemId.GUAM_POTION_UNF, 3, 12),
    STRENGTH_POTION(50, ItemId.LIMPWURT, ItemId.TARROMIN_POTION_UNF, 12, 22),
    RESTORE_POTION(62.5, ItemId.RED_SPIDERS_EGGS, ItemId.HARRALANDER_UNF, 22, 26),
    ENERGY_POTION(67.5, ItemId.CHOCOLATE_DUST, ItemId.HARRALANDER_POTION_UNF, 26, 38),
    PRAYER_POTION(87.5, ItemId.SNAPE_GRASS, ItemId.RANARR_POTION_UNF, 38, 45),
    SUPER_ATTACK_POTION(100, ItemId.EYE_OF_NEWT, ItemId.IRIT_POTION_UNF, 45, 52),
    SUPER_ENERGY_POTION(117.5, ItemId.MORT_MYRE_FUNGUS, ItemId.AVANTOE_UNF, 52, 55),
    SUPER_STRENGTH_POTION(125, ItemId.LIMPWURT, ItemId.KWUARM_UNF, 55, 63),
    SUPER_RESTORE_POTION(142.5, ItemId.RED_SPIDERS_EGGS, ItemId.SNAPDRAGON_UNF, 63, 72),
    RANGING_POTION(162.5, ItemId.WINE_OF_ZAMORAK, ItemId.DWARF_WEED_UNF, 72, 76);

    private double xpPer;
    @Getter
    private int itemId;
    @Getter
    private int unfPotionId;
    private int minLevel;
    private int maxLevel;

    HerbloreItems(double xpPer, int itemId, int unfPotionId, int minLevel, int levelMax) {
        this.xpPer = xpPer;
        this.itemId = itemId;
        this.unfPotionId = unfPotionId;
        this.minLevel = minLevel;
        this.maxLevel = levelMax;
    }

    private static Skills.SKILLS skill = Skills.SKILLS.HERBLORE;

    public int determineResourcesToNextItem() {
        if (Skills.getActualLevel(skill) >= this.maxLevel)
            return 0;
        int max = this.maxLevel;
        int xpTillMax = Skills.getXPToLevel(skill, SkillTasks.HERBLORE.getEndLevel());
        if (max < SkillTasks.HERBLORE.getEndLevel()) {
            xpTillMax = Skills.getXPToLevel(skill, this.maxLevel);
        }
        General.println("DetermineResourcesToNextItem: " + (xpTillMax / this.xpPer));
        return (int) (xpTillMax / this.xpPer) + 14;
    }

    public static Optional<HerbloreItems> getCurrentItem() {
        for (HerbloreItems i : values()) {
            if (Skills.getActualLevel(skill) < i.maxLevel) {
                return Optional.of(i);
            }
        }
        return Optional.empty();
    }

    public static List<ItemReq> getRequiredItemList() {
        List<ItemReq> i = new ArrayList<>();
        if (getCurrentItem().isPresent()) {
            getCurrentItem().ifPresent(h -> i.add(new ItemReq(h.getItemId(), h.determineResourcesToNextItem())));
            getCurrentItem().ifPresent(h -> i.add(new ItemReq(h.unfPotionId, h.determineResourcesToNextItem())));
            General.println("[HerbloreItems]: We need " + getCurrentItem().get().determineResourcesToNextItem() +
                    " items", Color.BLACK);

            General.println("[HerbloreItems]: We need " + i.size() + " sized list for herblore items", Color.BLACK);
        }
        return i;
    }
}
