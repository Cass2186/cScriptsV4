package scripts.Tasks.Smithing.BlastFurnace.BfData;

import lombok.Getter;

public enum Bars {

    IRON(12.5, 1, 0),
    STEEL(17.5, 30, 1),
    MITHRIL(30, 50, 2),
    ADAMANTITE(37.5, 70, 3),
    RUNE(50,85, 4),
    GOLD(56.2,40, -1); //56.2 is with gauntlets

    @Getter
    private double xpPer;

    @Getter
    private int minLevel;

    @Getter
    private int coalNeeded;

    Bars(double xp, int minLevel, int coalNeeded){
        this.xpPer = xp;
        this.minLevel = minLevel;
        this.coalNeeded = coalNeeded;
    }
}
