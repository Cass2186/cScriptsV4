package scripts.Data.Enums.Crafting;

import lombok.Getter;

public enum BATTLESTAVES {

    WATER_BATTLESTAFF,
    EARTH_BATTLESTAFF,
    FIRE_BATTLESTAFF,
    AIR_BATTLESTAFF;

    @Getter
    private int uncutId;
    private double xpPer;
    private int minLevel;
    private int maxLevel;

    BATTLESTAVES(){

    }

    BATTLESTAVES(int uncutId, double xpPer, int minLevel, int levelMax) {
        this.uncutId = uncutId;
        this.xpPer = xpPer;
        this.minLevel = minLevel;
        this.maxLevel = levelMax;
    }

}
