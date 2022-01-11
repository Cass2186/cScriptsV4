package scripts.Tasks.Combat.Data;

import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;

public enum CrabTile {

    FOSSIL_ISLAND_1(CrabType.AMMONITE_CRABS, Const.FOSSIL_ISLAND_1,
            new RSArea(Const.FOSSIL_ISLAND_1.translate(-25,-2), 3), "Fossil Island 1"),
    NORTH_WEST_1_CRAB_CLAW_ISLE(CrabType.SAND_CRABS, Const.NORTH_WEST1, Const.SE_RESET_AREA,
            "Crabclaw Isle - N-West 1"),
    NORTH_WEST_2_CRAB_CLAW_ISLE(CrabType.SAND_CRABS, Const.NORTH_WEST2, Const.SE_RESET_AREA,
            "Crabclaw Isle - N-West 2"),
    WEST_CLAW_ISLE(CrabType.SAND_CRABS, Const.WEST, Const.SE_RESET_AREA,
            "Crabclaw Isle - West"),
    SOUTH_WEST_CLAW_ISLE(CrabType.SAND_CRABS, Const.SOUTH_WEST, Const.NE_RESET_AREA,
            "Crabclaw Isle - S-West"),
    SOUTH_CRAB_CLAW_ISLE(CrabType.SAND_CRABS, Const.SOUTH, Const.NW_RESET_AREA,
            "Crabclaw Isle - South");


    public static RSTile NORTH_WEST1 = new RSTile(1764, 3445, 0);
    public static RSTile NORTH_WEST2 = new RSTile(1758, 3439, 0);
    public static RSTile WEST = new RSTile(1751, 3425, 0);
    public static RSTile SOUTH_WEST = new RSTile(1749, 3412, 0);
    public static RSTile SOUTH_EAST1 = new RSTile(1780, 3407, 0);
    public static RSTile SOUTH_EAST2 = new RSTile(1786, 3404, 0);
    public static RSTile NORTH_EAST1 = new RSTile(1777, 3429, 0);
    public static RSTile NORTH_EAST2 = new RSTile(1780, 3438, 0);

    private CrabType crabType;
    private RSTile tile;
    private RSArea resetArea;
    private String name;

    CrabTile(CrabType crabType, RSTile tile, RSArea resetArea, String name){
        this.crabType = crabType;
        this.tile = tile;
        this.resetArea = resetArea;
        this.name = name;
    }


    public CrabType getCrabType() {
        return crabType;
    }

    public RSTile getTile() {
        return tile;
    }

    public RSArea getResetArea() {
        return resetArea;
    }
}
