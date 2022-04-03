package scripts.Tasks.Woodcutting.WoodcuttingData;

import lombok.Getter;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSArea;

public enum WcLocations {

    VARROCK_WEST_TREES(WoodCuttingConst.VARROCK_WESK_REGULAR,"Tree", 1, 15),
    VARROCK_WEST_OAKS(WoodCuttingConst.VARROCK_WEST_OAKS, "Oak", 15, 31),
    PORT_SARIM_WILLOWS(WoodCuttingConst.PORT_SARIM_WILLOWS, "Willow", 31, 55),
    DRAYNOR_WILLOWS(WoodCuttingConst.DRAYNOR_WILLOW_AREA, "Willow", 31, 55),
    SEERS_WILLOWS(WoodCuttingConst.SEERS_MAPLES_AREA, "Willow", 31, 55),
    CASTLE_WARS_TEAKS(WoodCuttingConst.TEAK_AREA, "Teak", 55, 61),
    SEERS_MAPLES(WoodCuttingConst.SEERS_MAPLES_AREA, "Maple",  61, 99);


    @Getter
    private RSArea area;
    @Getter
    private int minLevel;
    @Getter
    private int maxLevel;
    @Getter
    private String treeName;

    WcLocations(RSArea area) {
        this.area = area;
    }

    WcLocations(RSArea area, String treeName, int minLevel, int maxLevel) {
        this.area = area;
        this.treeName = treeName;
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
