package scripts.Data.Enums;

import org.tribot.api.General;
import org.tribot.api2007.Skills;
import org.tribot.script.sdk.Log;
import scripts.Data.SkillTasks;
import scripts.ItemId;
import scripts.Requirements.ItemReq;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public enum CraftItems {

    BEER_GLASS(17.5, 1, 4),
    LANTERN(19, 4, 12),
    OIL_LAMP(25, 12, 33),
    VIAL(25, 33, 42),
    FISH_BOWL(42.5, 42, 46),
    UNPOWERED_ORB(52.5, 46, SkillTasks.CRAFTING.getEndLevel()); //should end at 61


    private double xpPer;
    private int minLevel;
    private int maxLevel;

    CraftItems(double xpPer, int minLevel, int levelMax) {
        this.xpPer = xpPer;
        this.minLevel = minLevel;
        this.maxLevel = levelMax;
    }


    private static Skills.SKILLS skill = Skills.SKILLS.CRAFTING;

    public int determineResourcesToNextItem() {
        if (Skills.getActualLevel(skill) > this.maxLevel)
            return 0;
        int max = this.maxLevel;
        int xpTillMax = Skills.getXPToLevel(skill, SkillTasks.CRAFTING.getEndLevel());
        if (max <=SkillTasks.CRAFTING.getEndLevel()) {
            xpTillMax = Skills.getXPToLevel(skill, this.maxLevel);
        }
        General.println("DetermineResourcesToNextItem: " + (xpTillMax / this.xpPer));
        return (int) (xpTillMax / this.xpPer) + 5;
    }

    private static Optional<CraftItems> getCurrentItem() {
        for (CraftItems i : values()) {
            if (Skills.getActualLevel(skill) < i.maxLevel) {
                return Optional.of(i);
            }
        }
        return Optional.empty();
    }

    public static List<ItemReq> getRequiredItemList() {
        List<ItemReq> i = new ArrayList<>();
        if ( getCurrentItem().isPresent()){
            Log.log("Current Item: " +getCurrentItem().get().toString());
        }
        getCurrentItem().ifPresent(h -> i.add(new ItemReq(ItemId.MOLTEN_GLASS, h.determineResourcesToNextItem())));

        General.println("[CraftItems]: We need " + i.size() + " sized list for craft items", Color.BLACK);
        General.println("[CraftItems]: We need " + " sized list for herblore items: " + i.get(0).getAmount(), Color.BLACK);


        i.add(new ItemReq(ItemId.GLASSBLOWING_PIPE, 1));
        return i;
    }
}
