package scripts.Requirements;

import org.tribot.api2007.types.RSGroundItem;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.GroundItem;
import org.tribot.script.sdk.types.WorldTile;
import scripts.EntitySelector.Entities;
import scripts.EntitySelector.finders.prefabs.GroundItemEntity;
import scripts.Requirements.ItemReq;
import scripts.Requirements.Requirement;

import java.util.Optional;

public class ItemOnTileRequirement implements Requirement {

    private int itemId;
    private RSTile tile;
    private ItemReq itemReq;

    public ItemOnTileRequirement(ItemReq itemReq, RSTile tile) {
        this.itemReq = itemReq;
        this.tile = tile;
        this.itemId = itemReq.getId();
    }

    public ItemOnTileRequirement(int id, RSTile tile) {
        this.tile = tile;
        this.itemId = id;
    }


    public ItemOnTileRequirement(ItemReq itemReq) {
        this.itemReq = itemReq;
        this.itemId = itemReq.getId();
    }


    @Override
    public boolean check() {
        Optional<GroundItem> any;
        if (this.tile != null) {
            WorldTile worldTile = new WorldTile(this.tile.getX(), this.tile.getY(), this.tile.getPlane());
            any = Query.groundItems().idEquals(this.itemId)
                    .tileEquals(worldTile)
                    .stream().findAny();
        } else
            any = Query.groundItems()
                    .idEquals(this.itemId)
                    .stream().findAny();

        return any.isPresent();
    }
}
