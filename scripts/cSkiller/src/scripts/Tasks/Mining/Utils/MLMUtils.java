package scripts.Tasks.Mining.Utils;

import com.sun.tools.javac.Main;
import dax.walker_engine.WalkerEngine;
import dax.walker_engine.local_pathfinding.Reachable;
import org.tribot.api.General;
import org.tribot.api2007.Objects;
import org.tribot.api2007.PathFinding;
import org.tribot.api2007.Player;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSObjectDefinition;
import org.tribot.api2007.types.RSTile;
import org.tribot.api2007.util.DPathNavigator;
import scripts.EntitySelector.Entities;
import scripts.EntitySelector.finders.prefabs.ObjectEntity;
import scripts.PathingUtil;
import scripts.PlayerOrientation;
import scripts.Timer;

import java.util.Arrays;

public class MLMUtils {


    public static   DPathNavigator dpath = new DPathNavigator();
    public static Reachable daxReacher = new Reachable();
    public static int MINING_ANIMATION = 6752;
    public static  String ORE_VEIN = "Ore vein";
    public static  String DEPLETED_VEIN = "Depleted vein";

    public static  RSArea area = new RSArea(
            new RSTile[]{
                    new RSTile(3733, 5688, 0),
                    new RSTile(3737, 5687, 0),
                    new RSTile(3748, 5687, 0),
                    new RSTile(3749, 5686, 0),
                    new RSTile(3750, 5686, 0),
                    new RSTile(3751, 5687, 0),
                    new RSTile(3757, 5687, 0),
                    new RSTile(3757, 5690, 0),
                    new RSTile(3751, 5690, 0),
                    new RSTile(3750, 5692, 0),
                    new RSTile(3746, 5692, 0),
                    new RSTile(3745, 5692, 0),
                    new RSTile(3735, 5692, 0),
                    new RSTile(3734, 5692, 0),
                    new RSTile(3732, 5692, 0)
            }
    );
    public static RSArea UPPER_AREA_EXCLUDE = new RSArea(
            new RSTile[] {
                    new RSTile(3733, 5687, 0),
                    new RSTile(3735, 5687, 0),
                    new RSTile(3735, 5685, 0),
                    new RSTile(3736, 5685, 0),
                    new RSTile(3737, 5684, 0),
                    new RSTile(3737, 5683, 0),
                    new RSTile(3739, 5683, 0),
                    new RSTile(3741, 5684, 0),
                    new RSTile(3743, 5684, 0),
                    new RSTile(3743, 5685, 0),
                    new RSTile(3746, 5686, 0),
                    new RSTile(3749, 5685, 0),
                    new RSTile(3751, 5680, 0),
                    new RSTile(3739, 5681, 0),
                    new RSTile(3735, 5681, 0),
                    new RSTile(3733, 5684, 0)
            }
    );
    public static  RSArea NORTH_MINING_AREA     = new RSArea(
            new RSTile[] {
                    new RSTile(3730, 5693, 0),
                    new RSTile(3753, 5693, 0),
                    new RSTile(3753, 5685, 0),
                    new RSTile(3733, 5686, 0)
            }
    );
    public static RSArea WHOLE_MLM_AREA = new RSArea(new RSTile(3709, 5692, 0), new RSTile(3775, 5631, 0));

    public static RSTile MLM_DEPOSIT_BOX_TILE = new RSTile(3749, 5673, 0); //just before it

    public static RSArea N_AREA_S_WALL_CONTAINING_AREA = new RSArea(
            new RSTile[] {
                    new RSTile(3733, 5688, 0),
                    new RSTile(3733, 5687, 0),
                    new RSTile(3736, 5687, 0),
                    new RSTile(3737, 5686, 0),
                    new RSTile(3740, 5686, 0),
                    new RSTile(3741, 5687, 0),
                    new RSTile(3748, 5687, 0),
                    new RSTile(3747, 5688, 0),
                    new RSTile(3745, 5688, 0),
                    new RSTile(3744, 5689, 0),
                    new RSTile(3743, 5688, 0),
                    new RSTile(3741, 5688, 0),
                    new RSTile(3740, 5687, 0),
                    new RSTile(3737, 5687, 0),
                    new RSTile(3736, 5688, 0)
            }
    );
    public static  RSArea N_AREA_N_WALL_CONTAINING_AREA = new RSArea(
            new RSTile[] {
                    new RSTile(3732, 5691, 0),
                    new RSTile(3734, 5691, 0),
                    new RSTile(3735, 5692, 0),
                    new RSTile(3737, 5692, 0),
                    new RSTile(3738, 5691, 0),
                    new RSTile(3739, 5692, 0),
                    new RSTile(3745, 5692, 0),
                    new RSTile(3747, 5690, 0),
                    new RSTile(3748, 5691, 0),
                    new RSTile(3750, 5691, 0),
                    new RSTile(3751, 5690, 0),
                    new RSTile(3751, 5693, 0),
                    new RSTile(3732, 5693, 0)
            }
    );
    public static PlayerOrientation getDirection() {
        PlayerOrientation i = PlayerOrientation.getOrientation();
    //    General.println("[Debug]: We are facing: " + i.toString());
        return i;
    }


    public static boolean isReachable(RSTile tile) {
        RSObject[] rockfall = Objects.find(20, "Rockfall");
        if (rockfall.length > 0)
            dpath.overrideDoorCache(true,rockfall);

        dpath.setAcceptAdjacent(true); // this isn't likely doing anything
        // dont use dax's class
        boolean b = PathFinding.canReach(tile, true);
        boolean c = PathFinding.canReach(tile, false);
        General.println("[Debug]: isReachable (obj): " + b);
        General.println("[Debug]: isReachable (notObj): " + c);
        return b ;
    }


    public static boolean localNavigation(RSTile destination) {

        RSObject[] rockfall = Objects.find(20, "Rockfall");
        if (rockfall.length > 0)
            dpath.overrideDoorCache(true,rockfall);
        dpath.setAcceptAdjacent(true);
        RSTile[] path = dpath.findPath(destination);
        if (path.length == 0) {
            General.println("[PathingUtil]: DPathNavigator failed to generate a path");
            return false;
        } else {
            General.println("[PathingUtil]: DPathNavigator generated a path, feeding to dax");
            WalkerEngine.getInstance().walkPath(Arrays.asList(path));
            return true;
            //movementIdle();

        }
    }



}
