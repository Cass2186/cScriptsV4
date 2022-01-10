package scripts.Requirements;

import org.tribot.api2007.Objects;

public class ObjectCondition implements Requirement {

    private int objectId;


    public ObjectCondition(int id) {
        this.objectId = id;
    }


    @Override
    public boolean check() {
        return Objects.findNearest(20, this.objectId).length > 0;
    }
}
