package scripts.Data;

import lombok.Data;
import lombok.Getter;
import org.tribot.script.sdk.types.Area;
import org.tribot.script.sdk.types.WorldTile;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public enum TempleRooms {

    WESTERN_ROOM(List.of(
            new WorldTile(1291, 10094, 0),//south pillar, south wall
            new WorldTile(1289, 10095, 0), //south pillar, west wall
            new WorldTile(1289, 10098, 0), //North pillar, west wall
            new WorldTile(1291, 10100, 0),//North pillar, north wall
            new WorldTile(1295, 10093, 0),//South-East pillar, South wall
            new WorldTile(1296, 10095, 0) //South-East pillar, East wall
    ), Const.WESTERN_TEMPLE_ROOM_AREA);


    @Getter
    private List<WorldTile> safeTileList;
    @Getter
    private Area roomArea;

    TempleRooms(List<WorldTile> safeTileList, Area roomArea) {
        this.safeTileList = safeTileList;
        this.roomArea = roomArea;
    }


    /*
    gets current room based on area
     */
    public Optional<TempleRooms> getCurrentTempleRoom() {
        for (TempleRooms t : TempleRooms.values()) {
            if (t.getRoomArea().containsMyPlayer())
                return Optional.of(t);
        }
        return Optional.empty();
    }

}
