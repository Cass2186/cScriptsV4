package scripts.Data.Enums.Crafting;

import lombok.Getter;
import org.tribot.api.General;
import org.tribot.api2007.Skills;
import scripts.Data.SkillTasks;
import scripts.ItemID;
import scripts.Requirements.ItemReq;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public enum GEMS {

    SAPPHIRE(1623, 50, 20,27),
    EMERALD(1621, 67.5, 27,34),
    RUBY(1619, 85, 34,99),
    DIAMOND(1617, 107.5, 43,99);


    @Getter
    private int uncutId;
    private double xpPer;
    private int minLevel;
    private int maxLevel;

    GEMS(int uncutId, double xpPer, int minLevel, int levelMax) {
        this.uncutId = uncutId;
        this.xpPer = xpPer;
        this.minLevel = minLevel;
        this.maxLevel = levelMax;
    }


    private static Skills.SKILLS skill = Skills.SKILLS.CRAFTING;

    public static Optional<GEMS> getCurrentItem() {
        for (GEMS i : values()) {
            if (Skills.getActualLevel(skill) < i.maxLevel) {
                return Optional.of(i);
            }
        }
        return Optional.empty();
    }

    public int determineResourcesToNextItem() {
        if (Skills.getActualLevel(skill) >= this.maxLevel)
            return 0;

        int max = this.maxLevel;
        if (SkillTasks.CRAFTING.getEndLevel() < this.maxLevel)
            max = SkillTasks.CRAFTING.getEndLevel();

        int xpTillMax = Skills.getXPToLevel(skill, max);

        General.println("[Gems]: DetermineResourcesToNextItem: " + (xpTillMax / this.xpPer));
        return (int) (xpTillMax / this.xpPer) + 5;
    }


    public static List<ItemReq> getRequiredItemList() {
        List<ItemReq> i = new ArrayList<>();
        if (getCurrentItem().isPresent()) {
            getCurrentItem().ifPresent(h -> i.add(new ItemReq(h.getUncutId(), h.determineResourcesToNextItem())));
            General.println("[Gems]: We need " + getCurrentItem().get().determineResourcesToNextItem() +
                    " items", Color.BLACK);
            General.println("[Gems]: We need " + i.size() + " sized list for Smith items", Color.BLACK);
        }
        i.add(new ItemReq(ItemID.CHISEL, 1));
        return i;
    }

}
