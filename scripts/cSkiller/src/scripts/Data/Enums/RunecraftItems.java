package scripts.Data.Enums;

import dax.api_lib.models.RunescapeBank;
import lombok.Getter;
import org.tribot.api.General;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSArea;
import org.tribot.script.sdk.Log;
import scripts.Data.SkillTasks;
import scripts.ItemID;
import scripts.Requirements.ItemReq;
import scripts.Tasks.Runecrafting.RunecraftData.RcConst;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public enum RunecraftItems {

    AIR_RUNE(1, 9, 5),
    EARTH_RUNE(9, 14, 6.5, RcConst.EARTH_ALTAR_AREA, ItemID.EARTH_TIARA, ItemID.VARROCK_TELEPORT,
            RunescapeBank.VARROCK_EAST),
    FIRE_RUNE(14, 19, 7, RcConst.FIRE_ALTAR_AREA,
            ItemID.FIRE_TIARA, "Ring of dueling", RunescapeBank.CASTLE_WARS),
    STEAM_RUNE(19, 99, 9.3, RcConst.FIRE_ALTAR_AREA,
            ItemID.FIRE_TIARA, "Ring of dueling",
            ItemID.WATER_TALISMAN, ItemID.WATER_RUNE, true, RunescapeBank.CASTLE_WARS);
    // LAVA_RUNE;

    @Getter
    private int minLevel;

    @Getter
    private int maxLevel;

    @Getter
    private double xpPer;

    @Getter
    private List<Integer> equipmentIds = new ArrayList<>();

    @Getter
    private int teleportId;

    @Getter
    private RunescapeBank bank;

    @Getter
    private int tiaraId;

    @Getter
    private RSArea altarArea;

    @Getter
    private String chargedTeleItemBaseName;

    @Getter
    private int additionalTalisman = -1;

    @Getter
    private int combiningRuneId;

    @Getter
    private boolean usingBindingNecklace;

    private static Skills.SKILLS skill = Skills.SKILLS.RUNECRAFTING;

    RunecraftItems(int min, int max, double xp) {
        this.maxLevel = min;
        this.maxLevel = max;
        this.xpPer = xp;

    }

    RunecraftItems(int min, int max, double xp, RSArea area, int tiaraId, String chargedItem, RunescapeBank bank) {
        this.maxLevel = min;
        this.maxLevel = max;
        this.xpPer = xp;
        this.altarArea = area;
        this.tiaraId = tiaraId;
        this.chargedTeleItemBaseName = chargedItem;
        this.bank = bank;
    }

    RunecraftItems(int min, int max, double xp, RSArea area, int tiaraId, int teleportId, RunescapeBank bank) {
        this.maxLevel = min;
        this.maxLevel = max;
        this.xpPer = xp;
        this.altarArea = area;
        this.tiaraId = tiaraId;
        this.teleportId = teleportId;
        this.bank = bank;
    }

    RunecraftItems(int min, int max, double xp, RSArea area, int tiaraId,
                   String chargedItem, int additionalTalisman, int combiningRuneId, boolean usingBindingNecklace,
                   RunescapeBank bank) {
        this.maxLevel = min;
        this.maxLevel = max;
        this.xpPer = xp;
        this.altarArea = area;
        this.tiaraId = tiaraId;
        this.chargedTeleItemBaseName = chargedItem;
        this.additionalTalisman = additionalTalisman;
        this.combiningRuneId = combiningRuneId;
        this.usingBindingNecklace = usingBindingNecklace;
        this.bank = bank;

    }

    public int determineResourcesToNextItem() {
        if (Skills.getActualLevel(skill) >= this.maxLevel)
            return 0;
        int max = this.maxLevel;
        int xpTillMax = Skills.getXPToLevel(skill, SkillTasks.RUNECRAFTING.getEndLevel());
        if (max < SkillTasks.RUNECRAFTING.getEndLevel()) {
            xpTillMax = Skills.getXPToLevel(skill, this.maxLevel);
        }
        General.println("[RunecraftItems]: DetermineResourcesToNextItem: " + (xpTillMax / this.xpPer));
        return (int) (xpTillMax / this.xpPer) + 1;
    }

    public static Optional<RunecraftItems> getCurrentItem() {
        for (RunecraftItems i : values()) {
            if (Skills.getActualLevel(skill) < i.maxLevel && Skills.getActualLevel(skill) >= i.minLevel) {
                return Optional.of(i);
            }
        }
        Log.log("returning no item");
        return Optional.empty();
    }

    //TODO check this division numbers
    public static ArrayList<ItemReq> getRequiredItemList() {
        ArrayList<ItemReq> i = new ArrayList<>();
        Optional<RunecraftItems> currentItem = getCurrentItem();
        if (currentItem.isPresent()) {

            // and binding if neeeded
            if (currentItem.get().usingBindingNecklace) {
                currentItem.ifPresent(item -> i.add(new ItemReq(ItemID.BINDING_NECKLACE,
                        item.determineResourcesToNextItem() / 220,
                        true, true)));
            }

            //add tiara
            currentItem.ifPresent(item -> i.add(new ItemReq(item.getTiaraId(), 1, true, true)));

            if (currentItem.get().getAdditionalTalisman() > 0)
                currentItem.ifPresent(item -> i.add(new ItemReq(item.getAdditionalTalisman(),
                        item.determineResourcesToNextItem() / 22)));

            if (currentItem.get().getCombiningRuneId() > 0)
                currentItem.ifPresent(item -> i.add(new ItemReq(item.combiningRuneId,
                        item.determineResourcesToNextItem())));

            currentItem.ifPresent(item -> i.add(new ItemReq(ItemID.PURE_ESSENCE,
                    item.determineResourcesToNextItem())));
            //add ROD or tele tab
            if (currentItem.get().equals(RunecraftItems.FIRE_RUNE) ||
                    currentItem.get().equals(RunecraftItems.STEAM_RUNE)) {
                currentItem.ifPresent(item -> i.add(new ItemReq(ItemID.RING_OF_DUELING[0],
                        item.determineResourcesToNextItem() / 50)));
            } else {
                currentItem.ifPresent(item -> i.add(new ItemReq(item.getTeleportId(),
                        item.determineResourcesToNextItem() / 20)));
            }

            General.println("[RunecraftItems]: We need " + getCurrentItem().get().determineResourcesToNextItem() +
                    " items", Color.BLACK);

            General.println("[RunecraftItems]: We need " + i.size() + " sized list for runecraft items", Color.BLACK);
        }
        return i;
    }


}
