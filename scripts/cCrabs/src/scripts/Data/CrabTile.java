package scripts.Data;

import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum CrabTile {

    FOSSIL_ISLAND_1(CrabType.AMMONITE_CRABS, Const.FOSSIL_ISLAND_1,
            new RSArea(Const.FOSSIL_ISLAND_1.translate(-25, -2), 3), "Fossil Island 1"),
    NORTH_WEST_1_CRAB_CLAW_ISLE(CrabType.SAND_CRABS, Const.NORTH_WEST1, Const.SE_RESET_AREA,
            "Crabclaw Isle - N-West 1"),
    NORTH_WEST_2_CRAB_CLAW_ISLE(CrabType.SAND_CRABS, Const.NORTH_WEST2, Const.SE_RESET_AREA,
            "Crabclaw Isle - N-West 2"),
    WEST_CLAW_ISLE(CrabType.SAND_CRABS, Const.WEST, Const.SE_RESET_AREA,
            "Crabclaw Isle - West"),
    SOUTH_WEST_CLAW_ISLE(CrabType.SAND_CRABS, Const.SOUTH_WEST, Const.NE_RESET_AREA,
            "Crabclaw Isle - S-West"),
    SOUTH_CRAB_CLAW_ISLE(CrabType.SAND_CRABS, Const.SOUTH, Const.NW_RESET_AREA,
            "Crabclaw Isle - South"),

    // ZEAH EAST SHORE (W->E)
    ZEAH_EAST_SHORE_1(CrabType.SAND_CRABS, Const.ZEAH_EAST_SHORE_1, Const.ZEAH_EAST_RESET_WEST,
            "Zeah East Short - 1"),
    ZEAH_EAST_SHORE_2(CrabType.SAND_CRABS, Const.ZEAH_EAST_SHORE_2, Const.ZEAH_EAST_RESET_WEST,
            "Zeah East Short - 2"),
    ZEAH_EAST_SHORE_3(CrabType.SAND_CRABS, Const.ZEAH_EAST_SHORE_3, Const.ZEAH_EAST_RESET_WEST,
            "Zeah East Short - 3"),
    ZEAH_EAST_SHORE_4(CrabType.SAND_CRABS, Const.ZEAH_EAST_SHORE_4, Const.ZEAH_EAST_RESET_EAST,
            "Zeah East Short - 4"),
    ZEAH_EAST_SHORE_5(CrabType.SAND_CRABS, Const.ZEAH_EAST_SHORE_5, Const.ZEAH_EAST_RESET_EAST,
            "Zeah East Short - 5"),
    ZEAH_EAST_SHORE_6(CrabType.SAND_CRABS, Const.ZEAH_EAST_SHORE_6, Const.ZEAH_EAST_RESET_EAST,
            "Zeah East Short - 6");


    private CrabType crabType;
    private RSTile tile;
    private RSArea resetArea;
    private String name;

    CrabTile(CrabType crabType, RSTile tile, RSArea resetArea, String name) {
        this.crabType = crabType;
        this.tile = tile;
        this.resetArea = resetArea;
        this.name = name;
    }

    CrabTile(CrabType crabType, RSTile tile, RSTile resetTile, String name) {
        this.crabType = crabType;
        this.tile = tile;
        this.resetArea = new RSArea(resetTile, 3);
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

    public static RSTile determineClosestTile() {
        List<CrabTile> tileList = new ArrayList<>();
        tileList.addAll(Arrays.asList(CrabTile.values()));
        CrabTile best = SOUTH_CRAB_CLAW_ISLE;
        if (Const.SANDCRAB_ISLAND.contains(Player.getPosition())) {
            for (CrabTile t : CrabTile.values()) {
                if (Player.getPosition().distanceTo(t.getTile()) <
                        Player.getPosition().distanceTo(best.getTile())) {
                    best = t;
                }
            }
        }
        return best.getTile();
    }
}
