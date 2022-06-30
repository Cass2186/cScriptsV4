package scripts.Data.Enums;

import lombok.Getter;
import org.tribot.api.General;
import org.tribot.api.input.Keyboard;
import org.tribot.api2007.Skills;
import org.tribot.script.sdk.*;
import scripts.Data.SkillTasks;
import scripts.*;
import scripts.Data.Vars;
import scripts.Requirements.ItemReq;


import java.awt.*;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

public enum HerbloreItems {

    ATTACK_POTION(25, scripts.ItemID.EYE_OF_NEWT, scripts.ItemID.GUAM_POTION_UNF, 3, 12),
    STRENGTH_POTION(50, scripts.ItemID.LIMPWURT_ROOT, scripts.ItemID.TARROMIN_POTION_UNF, 12, 22),
    RESTORE_POTION(62.5, scripts.ItemID.RED_SPIDERS_EGGS, scripts.ItemID.HARRALANDER_POTION_UNF, 22, 26),
    ENERGY_POTION(67.5, scripts.ItemID.CHOCOLATE_DUST, scripts.ItemID.HARRALANDER_POTION_UNF, 26, 38),
    PRAYER_POTION(87.5, scripts.ItemID.SNAPE_GRASS, scripts.ItemID.RANARR_POTION_UNF, 38, 45),
    SUPER_ATTACK_POTION(100, scripts.ItemID.EYE_OF_NEWT, scripts.ItemID.IRIT_POTION_UNF, 45, 52),
    SUPER_ENERGY_POTION(117.5, scripts.ItemID.MORT_MYRE_FUNGUS, scripts.ItemID.AVANTOE_POTION_UNF, 52, 55),
    SUPER_STRENGTH_POTION(125, scripts.ItemID.LIMPWURT_ROOT, scripts.ItemID.KWUARM_POTION_UNF, 55, 63),
    SUPER_RESTORE_POTION(142.5, scripts.ItemID.RED_SPIDERS_EGGS, scripts.ItemID.SNAPDRAGON_POTION_UNF, 63, 72),
    RANGING_POTION(162.5, scripts.ItemID.WINE_OF_ZAMORAK, scripts.ItemID.DWARF_WEED_POTION_UNF, 72, 76);

    private double xpPer;
    @Getter
    private int ItemId;
    @Getter
    private int unfPotionId;
    private int minLevel;
    private int maxLevel;

    HerbloreItems(double xpPer, int ItemId, int unfPotionId, int minLevel, int levelMax) {
        this.xpPer = xpPer;
        this.ItemId = ItemId;
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

    public void makePotion() {
        if (Bank.isOpen())
            BankManager.close(true);

        if (Utils.useItemOnItem(this.getItemId(), this.getUnfPotionId()) &&
                Waiting.waitUntil(3250, 5, MakeScreen::isOpen))
            Utils.idlePredictableAction();

        if (MakeScreen.isOpen()) {
            Keyboard.typeString(" ");
            Timer.abc2SkillingWaitCondition(() -> ChatScreen.isOpen()
                    || !Inventory.contains(this.getItemId())
                    || !Inventory.contains(this.getUnfPotionId())
                    , 30000, 45000);

        }
        if (!SkillTasks.HERBLORE.isWithinLevelRange())
            Vars.get().currentTask = null;
    }


}

