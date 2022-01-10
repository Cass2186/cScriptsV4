package scripts;

import org.tribot.api.General;
import org.tribot.api2007.Player;

import java.util.Arrays;

public enum PlayerOrientation {
    NORTH(4),
    NORTH_EAST(5),
    EAST(6),
    SOUTH_EAST(7),
    SOUTH(0),
    SOUTH_WEST(1),
    WEST(2),
    NORTH_WEST(3),
    NO_ORIENTATION_FOUND(-1);

    int orientationValue;

    PlayerOrientation(int orientationValue) {
        this.orientationValue = orientationValue;
    }

    public int getOrientationValue() {
        return orientationValue;
    }

    public static PlayerOrientation getOrientation() {
        int orientation = Player.getRSPlayer().getOrientation();
        if (orientation == -1) {
            General.println("[Debug] Failed to get player orientation.");
            return NO_ORIENTATION_FOUND;
        }

        return Arrays.stream(PlayerOrientation.values())
                .filter(item -> item.getOrientationValue() == (orientation / 256))
                .findFirst()
                .orElse(NO_ORIENTATION_FOUND);
    }


}