package scripts.Data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.tribot.script.sdk.Inventory;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Skill;
import scripts.ItemID;
import scripts.Requirements.ItemReq;
import scripts.Requirements.ItemRequirement;
import scripts.Tasks.MakeTabs.MakeTabs;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public enum Tabs {


    // BONES_TO_BANANAS(),
    //BONES_TO_PEACHES(),
    //ENCHANT_ONYX(),
    //
    ENCHANT_SAPPHIRE(7, List.of(
            new ItemRequirement(ItemID.WATER_RUNE, 1),
            new ItemRequirement(ItemID.COSMIC_RUNE, 1)),
            17/*.5*/, "Enchant Sapphire", ItemID.ENCHANT_SAPPHIRE_OR_OPAL),
    ENCHANT_EMERALD(27, List.of(
            new ItemRequirement(ItemID.AIR_RUNE, 3),
            new ItemRequirement(ItemID.COSMIC_RUNE, 1)),
            37, "Enchant Emerald", ItemID.ENCHANT_EMERALD_OR_JADE),


    ENCHANT_RUBY(49, List.of(
            new ItemRequirement(ItemID.STAFF_OF_FIRE, 1, true, true),
           // new ItemReq(ItemID.FIRE_RUNE, 5),
            new ItemRequirement(ItemID.COSMIC_RUNE, 1)),
            51, "Enchant Ruby", ItemID.ENCHANT_RUBY_OR_TOPAZ),
    ENCHANT_DIAMOND(57,
            List.of(
                    new ItemRequirement(ItemID.STAFF_OF_EARTH, 1, true, true),
                    //new ItemReq(ItemID.EARTH_RUNE, 5),
                    new ItemRequirement(ItemID.COSMIC_RUNE, 1)),
            67, "Enchant Diamond", ItemID.ENCHANT_DIAMOND),
    //ENCHANT_DRAGONSTONE(),
    // WATCHTOWER(),
    VARROCK(25, List.of(
            new ItemRequirement(ItemID.FIRE_RUNE, 1),
            new ItemRequirement(ItemID.LAW_RUNE, 1)),
            25, "Varrock Teleport", ItemID.VARROCK_TELEPORT),
    LUMBRIDGE(31, List.of(
            new ItemRequirement(ItemID.EARTH_RUNE, 1),
            new ItemRequirement(ItemID.LAW_RUNE, 1)),
            37, "Lumbridge Teleport", ItemID.LUMBRIDGE_TELEPORT),
    FALADOR(37, List.of(
            new ItemRequirement(ItemID.WATER_RUNE, 1),
            new ItemRequirement(ItemID.LAW_RUNE, 1)),
            48, "Falador Teleport", ItemID.FALADOR_TELEPORT),
    HOUSE(40, List.of(
            new ItemRequirement(ItemID.EARTH_RUNE, 1),
            new ItemRequirement(ItemID.LAW_RUNE, 1)),
            30, "House Teleport", ItemID.TELEPORT_TO_HOUSE),
    CAMELOT(45, List.of(new ItemRequirement(ItemID.LAW_RUNE, 1)),
            55, "Camelot Teleport", ItemID.CAMELOT_TELEPORT),
    ARDOUGNE(51, List.of(
            new ItemRequirement(ItemID.WATER_RUNE, 2),
            new ItemRequirement(ItemID.LAW_RUNE, 2)),
            61, "Ardougne Teleport", ItemID.ARDOUGNE_TELEPORT);

    @Getter
    private int levelReq;

    @Getter
    private List<ItemRequirement> runeReqs;
    private int xpGained;
    @Getter
    private String name;
    @Getter
    private int tabID;



    public boolean canCraftTab() {
        return runeReqs.stream().allMatch(ItemRequirement::check) &&
                Skill.MAGIC.getActualLevel() >= this.getLevelReq();
    }

    public static Tabs getMostProfitableTab() {
        Optional<Tabs> t = Arrays.stream(Tabs.values())
                .filter(Tabs::canCraftTab)
                .max(Comparator.comparingInt(MakeTabs::profitPerTab));
        Tabs tab = t.orElse(Tabs.VARROCK);
        Log.debug("Most Profitable tab is " + tab.getName());
        return tab;
    }


}