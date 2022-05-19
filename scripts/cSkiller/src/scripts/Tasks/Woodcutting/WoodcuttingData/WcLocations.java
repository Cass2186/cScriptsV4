package scripts.Tasks.Woodcutting.WoodcuttingData;

import lombok.Getter;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSArea;
import org.tribot.script.sdk.types.Area;
import scripts.Utils;
import scripts.Varbits;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;

public enum WcLocations {

    VARROCK_WEST_TREES(WoodCuttingConst.VARROCK_WESK_REGULAR,"Tree", 1, 15),
    VARROCK_WEST_OAKS(WoodCuttingConst.VARROCK_WEST_OAKS, "Oak", 15, 31),
    PORT_SARIM_WILLOWS(WoodCuttingConst.PORT_SARIM_WILLOWS, "Willow", 31, 55),
    DRAYNOR_WILLOWS(WoodCuttingConst.DRAYNOR_WILLOW_AREA, "Willow", 31, 55),
    SEERS_WILLOWS(WoodCuttingConst.SEERS_MAPLES_AREA, "Willow", 31, 55),
    CASTLE_WARS_TEAKS(WoodCuttingConst.TEAK_AREA, "Teak", 55, 61),
    SEERS_MAPLES(WoodCuttingConst.SEERS_MAPLES_AREA, "Maple",  61, 85),
    WC_GUILD_WILLOWS(WoodCuttingConst.WC_GUILD_MAPLE_AREA, "Willow",
            Utils.getVarBitValue(Varbits.KOUREND_FAVOR_HOSIDIUS.getId()) > 750,
            55, 80),
    WC_GUILD_MAPLES(WoodCuttingConst.WC_GUILD_MAPLE_AREA, "Maple",
            Utils.getVarBitValue(Varbits.KOUREND_FAVOR_HOSIDIUS.getId()) > 750,
            61, 85),
    WC_GUILD_YEWS(WoodCuttingConst.WC_GUILD_YEW_AREA, "Yew",
            Utils.getVarBitValue(Varbits.KOUREND_FAVOR_HOSIDIUS.getId()) > 750,
            75, 90),
    WC_GUILD_MAGICS(WoodCuttingConst.WC_GUILD_MAGIC_AREA, "Magic tree",
            Utils.getVarBitValue(Varbits.KOUREND_FAVOR_HOSIDIUS.getId()) > 750,
            90, 99);

    @Getter
    private Area area;


    @Getter
    private boolean requirement = true;

    @Getter
    private int minLevel;

    @Getter
    private int maxLevel;

    @Getter
    private String treeName;

    WcLocations(Area area) {
        this.area = area;
    }

    WcLocations(Area area, String treeName, int minLevel, int maxLevel) {
        this.area = area;
        this.treeName = treeName;
        this.minLevel = minLevel;
        this.maxLevel = maxLevel;
    }

    WcLocations(Area area, String treeName, boolean requirement, int minLevel, int maxLevel) {
        this.area = area;
        this.treeName = treeName;
        this.requirement = requirement;
        this.minLevel = minLevel;
        this.maxLevel = maxLevel;
    }




    public boolean isWithinLevelRange() {
        int currentLevel = Skills.getActualLevel(Skills.SKILLS.WOODCUTTING);
        return (currentLevel >= this.minLevel) && (currentLevel < this.maxLevel);
    }

    public static Optional<WcLocations> getWoodcuttingLocation() {
        return Arrays.stream(WcLocations.values())
                .filter(w-> w.requirement)
                .filter(WcLocations::isWithinLevelRange)
                .max(Comparator.comparingInt(WcLocations::getMinLevel));
    }

}
