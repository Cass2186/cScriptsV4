package scripts.Data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.tribot.script.sdk.Inventory;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Skill;
import scripts.ItemID;
import scripts.Tasks.MakeTabs.MakeTabs;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;

@AllArgsConstructor
public enum Tabs {
    VARROCK(25, ItemID.FIRE_RUNE, 25, "Varrock", ItemID.VARROCK_TELEPORT),
    LUMBRIDGE(31, ItemID.EARTH_RUNE, 37, "Lumbridge", ItemID.LUMBRIDGE_TELEPORT),
    HOUSE(40, ItemID.EARTH_RUNE, 30, "House", ItemID.TELEPORT_TO_HOUSE),
    FALADOR(37, ItemID.WATER_RUNE, 48, "Falador", ItemID.FALADOR_TELEPORT),
    CAMELOT(45, -1, 55, "Camelot", ItemID.CAMELOT_TELEPORT);

    @Getter
    private int levelReq;
    @Getter
    private int otherRuneId;
    private int xpGained;
    @Getter
    private String name;
    @Getter
    private int tabID;


    public boolean canCraftTab() {
        if (this.otherRuneId == -1) {
            return (Skill.MAGIC.getActualLevel() >= this.getLevelReq());
        } else
            return (Skill.MAGIC.getActualLevel() >= this.getLevelReq()) && Inventory.contains(otherRuneId);
    }

    public static Tabs getMostProfitableTab() {
        Optional<Tabs> t = Arrays.stream(Tabs.values())
                .filter(Tabs::canCraftTab)
                .max(Comparator.comparingInt(MakeTabs::profitPerTab));
        Tabs tab = t.orElse(Tabs.VARROCK);
        Log.debug("Best tab is " + tab.getName());
        return tab;
    }


}