package scripts.Data.Enums;

import lombok.Getter;
import org.tribot.api.General;
import org.tribot.api2007.Skills;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.types.InventoryItem;
import scripts.Data.SkillTasks;
import scripts.ItemID;
import scripts.Requirements.ItemReq;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public enum SMITH_ITEMS {



   BRONZE_DAGGER("Dagger", 1, ItemID.BRONZE_BAR,
           genericSmithItems.DAGGER.getInterfaceChild(),1,5),

    BRONZE_BOLTS("Bolts", 1, ItemID.BRONZE_BAR,
            genericSmithItems.BOLTS.getInterfaceChild(),1,5),

    BRONZE_SCIMITAR("Scimitar", 2, ItemID.BRONZE_BAR,
            genericSmithItems.SCIMITAR.getInterfaceChild(),5, 9),

    BRONZE_WARHAMMER("Warhammer", 3, ItemID.BRONZE_BAR,
            genericSmithItems.WARHAMMER.getInterfaceChild(),9,18),

    BRONZE_PLATEBODY("Plate body", 5, ItemID.BRONZE_BAR,
            genericSmithItems.PLATEBODY.getInterfaceChild(), 20, 24);

    @Getter
    private String itemName;
    @Getter
    private int barId;
    @Getter
    private int numBars;
    @Getter
    private int interfaceChild;
    @Getter
    private int minLevel;
    @Getter
    private int maxLevel;

   public enum genericSmithItems {
        DAGGER("Dagger", 1, 9),
        BOLTS("Bolts", 1, 34),
        SCIMITAR("Scimitar", 2, 11),
        WARHAMMER("Warhammer", 3, 16),
        KITESHIELD("Kite shield", 3, 27),
        PLATEBODY("Plate body", 5, 22);

        @Getter
        private String itemName;
        @Getter
        private int barId;
        @Getter
        private int numBars;
        @Getter
        private int interfaceChild;

        genericSmithItems(String itemName, int numBars, int interfaceChild) {
            this.itemName = itemName;
            this.numBars = numBars;
            this.interfaceChild = interfaceChild;
        }
    }


    SMITH_ITEMS(String itemName, int numBars, int barId, int interfaceChild , int minLevel, int maxLevel) {
        this.itemName = itemName;
        this.numBars = numBars;
        this.barId = barId;
        this.interfaceChild = interfaceChild;
        this.minLevel = minLevel;
        this.maxLevel = maxLevel;
    }

    private static Skills.SKILLS skill = Skills.SKILLS.SMITHING;

    public double getXpFromBarId() {
        if (this.barId == ItemID.BRONZE_BAR) {
            return 12.5;
        } else if (this.barId == ItemID.IRON_BAR) {
            return 25;
        } else if (this.barId == ItemID.STEEL_BAR) {
            return 37.5;
        } else if (this.barId == ItemID.MITHRIL_BAR) {
            return 50;
        } else if (this.barId == ItemID.ADAMANTITE_BAR) {
            return 62.5;
        }
        return -1;
    }

    public int determineResourcesToNextItem() {
        if (Skills.getActualLevel(skill) >= this.maxLevel)
            return 0;
        int max = this.maxLevel;
        int xpTillMax = Skills.getXPToLevel(skill, SkillTasks.SMITHING.getEndLevel());
        if (max < SkillTasks.SMITHING.getEndLevel()) {
            xpTillMax = Skills.getXPToLevel(skill, this.maxLevel);
        }
        General.println("DetermineResourcesToNextItem: " + (xpTillMax / this.getXpFromBarId()));
        return (int) (xpTillMax / this.getXpFromBarId()) + 14;
    }

    public static Optional<SMITH_ITEMS> getCurrentItem() {
        for (SMITH_ITEMS i : values()) {
            if (Skills.getActualLevel(skill) < i.maxLevel) {
                return Optional.of(i);
            }
        }
        return Optional.empty();
    }

    public static List<ItemReq> getRequiredItemList() {
        List<ItemReq> i = new ArrayList<>();
        if (getCurrentItem().isPresent()) {
            getCurrentItem().ifPresent(h -> i.add(new ItemReq(h.getBarId(), h.determineResourcesToNextItem())));
            General.println("[SmithItems]: We need " + getCurrentItem().get().determineResourcesToNextItem() +
                    " items", Color.BLACK);
            General.println("[SmithItems]: We need " + i.size() + " sized list for Smith items", Color.BLACK);
        }
        i.add(new ItemReq(ItemID.HAMMER, 1));
        return i;
    }
}
