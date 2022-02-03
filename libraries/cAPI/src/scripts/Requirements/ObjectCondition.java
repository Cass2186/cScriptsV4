package scripts.Requirements;

import org.tribot.api2007.Objects;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import scripts.EntitySelector.Entities;
import scripts.EntitySelector.finders.prefabs.ObjectEntity;

public class ObjectCondition implements Requirement {

    private int objectId;
    private RSTile objectTile;

    public ObjectCondition(int id) {
        this.objectId = id;
    }

    public ObjectCondition(int id, RSTile objectTile) {
        this.objectTile = objectTile;
        this.objectId = id;
    }

    @Override
    public boolean check() {
        if (objectTile != null){
            RSObject obj = Entities.find(ObjectEntity::new)
                    .tileEquals(this.objectTile)
                    .idEquals(this.objectId)
                    .getFirstResult();
            return obj != null;
        }

        return Objects.findNearest(40, this.objectId).length > 0;
    }
}
