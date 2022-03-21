package scripts.Tasks.Woodcutting.WoodcuttingData;

import lombok.Getter;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSArea;

public enum WcLocations {

    VARROCK_WEST_TREES(WoodCuttingConst.VARROCK_WESK_REGULAR, 1, 15),
    VARROCK_WEST_OAKS(WoodCuttingConst.VARROCK_WEST_OAKS, 15, 31),
    PORT_SARIM_WILLOWS(WoodCuttingConst.PORT_SARIM_WILLOWS, 31, 41),
    DRAYNOR_WILLOWS(WoodCuttingConst.DRAYNOR_WILLOW_AREA, 31, 41),
    SEERS_WILLOWS(WoodCuttingConst.SEERS_MAPLES_AREA, 31, 41),
    CASTLE_WARS_TEAKS(WoodCuttingConst.TEAK_AREA, 41, 61),
    SEERS_MAPLES(WoodCuttingConst.SEERS_MAPLES_AREA, 61, 99);


    @Getter
    private RSArea area;
    private int minLevel;
    private int maxLevel;

    WcLocations(RSArea area) {
        this.area = area;
    }

    WcLocations(RSArea area, int minLevel, int maxLevel) {
        this.area = area;
        this.minLevel = minLevel;
        this.maxLevel = maxLevel;
    }

    public boolean isWithinLevelRange() {
        int currentLevel = Skills.getActualLevel(Skills.SKILLS.WOODCUTTING);
        return (currentLevel >= this.minLevel) && (currentLevel < this.maxLevel);
    }

    public static RSArea getWoodcuttingArea() {
        for (WcLocations loc : values()){
            if (loc.isWithinLevelRange())
                return loc.getArea();
        }
        return VARROCK_WEST_TREES.getArea();
    }

}
