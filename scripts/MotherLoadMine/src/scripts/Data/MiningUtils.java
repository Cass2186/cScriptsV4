package scripts.Data;

import com.sun.tools.javac.Main;
import org.tribot.api.General;
import org.tribot.api2007.Objects;
import org.tribot.api2007.PathFinding;
import org.tribot.api2007.Player;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSObjectDefinition;
import org.tribot.api2007.types.RSTile;
import scripts.EntitySelector.Entities;
import scripts.EntitySelector.finders.prefabs.ObjectEntity;
import scripts.PathingUtil;
import scripts.PlayerOrientation;
import scripts.Timer;
import scripts.Utils;

public class MiningUtils {

    public static PlayerOrientation getPlayerOrientation() {
        PlayerOrientation i = PlayerOrientation.getOrientation();
   //     General.println("[Debug]: We are facing: " + i.toString());
        return i;
    }

    public static boolean isReachable(RSTile tile) {
        boolean b = PathFinding.canReach(tile, true);
        boolean c = PathFinding.canReach(tile, false);
        General.println("[Debug]: isReachable (obj): " + b);
        General.println("[Debug]: isReachable (notObj): " + c);
        return b;
    }




}
