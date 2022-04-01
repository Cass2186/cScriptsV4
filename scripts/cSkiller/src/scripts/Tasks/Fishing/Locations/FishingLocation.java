package scripts.Tasks.Fishing.Locations;

import org.tribot.api2007.types.RSArea;

public interface FishingLocation {

    RSArea getArea();
    int getRequiredLevel();
}
