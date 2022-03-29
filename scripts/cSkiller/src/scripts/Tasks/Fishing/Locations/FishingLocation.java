package scripts.Tasks.Fishing.Locations;

import org.tribot.api2007.types.RSArea;

public interface FishingLocation {

    public RSArea getArea();

    int getRequiredLevel();

}
