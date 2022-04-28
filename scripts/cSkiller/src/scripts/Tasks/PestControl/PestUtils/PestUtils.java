package scripts.Tasks.PestControl.PestUtils;

import dax.walker_engine.local_pathfinding.Reachable;
import org.tribot.api.General;
import org.tribot.api2007.Game;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSInterface;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.MyPlayer;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.interfaces.Character;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.Area;
import org.tribot.script.sdk.types.Npc;
import org.tribot.script.sdk.types.Widget;
import scripts.AntiBan;
import scripts.Data.Vars;
import scripts.EntitySelector.Entities;
import scripts.EntitySelector.finders.prefabs.NpcEntity;
import scripts.PathingUtil;
import scripts.Timer;
import scripts.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PestUtils {

    public static int PEST_POINTS = 0;
    public static int STARTING_PEST_POINTS = -1;
    public static boolean ATTACK_PORTALS = false;// General.randomBoolean();
    public static RSArea INTERMEDIATE_BOAT_AREA = new RSArea(new RSTile(2641, 2642, 0), new RSTile(2638, 2647, 0));
    public static int[] KNIGHT_ID = {2950, 2951, 2952, 2953};
    public static int WEST_PORTAL_ID = 1743;
    public static int EAST_PORTAL_ID = 1740;
    public static int SOUTH_WEST_PORTAL_ID = 1742;
    public static int SOUTH_EAST_PORTAL_ID = 1745;
    public static int[] PORTAL_ARRAY = {
            WEST_PORTAL_ID,
            EAST_PORTAL_ID,
            SOUTH_WEST_PORTAL_ID,
            SOUTH_EAST_PORTAL_ID
    };

    public static int getGainedPestPoints() {
        return Vars.get().pestControlPoints - STARTING_PEST_POINTS;
    }

    public static int getPestPoints() {
        Optional<Widget> first = Query.widgets().inIndexPath(407)
                .textContains("Pest Point").findFirst();

        if (first.isPresent()) {
            Optional<String> stringOptional = first.get().getText();
            String fullString = stringOptional.orElse(" ");
            String[] splitString = fullString.split("Pest Points: ");
            if (splitString.length > 1) {
                Vars.get().pestControlPoints = Integer.parseInt(splitString[1]);
                Log.info("[Debug]: Points is: " + Vars.get().pestControlPoints);
                if (STARTING_PEST_POINTS == -1)
                    STARTING_PEST_POINTS = Integer.parseInt(splitString[1]);
                return Integer.parseInt(splitString[1]);
            }
        }
        Log.warn("failed to parse pest points");
        return 0;
    }

    public static int getActivityPercent() {
        RSInterface bar = Interfaces.get(408, 12);
        RSInterface activityBar = Interfaces.get(408, 12, 0);
        if (bar != null && activityBar != null) {
            int lengthOverall = bar.getWidth();
            int activityWidth = activityBar.getWidth();
            int percent = lengthOverall / activityWidth;
            //   Log.log("[Debug]: Pecent activity is " + percent);
            return percent;
        }

        return -1;
    }

    public static Reachable reach = new Reachable();

    public static Optional<Npc> getTarget() {
        Optional<Npc> interactingWith = Query.npcs().actionContains("Attack")
                .inArea(Area.fromRadius(MyPlayer.getTile(), 5))
                .sortedByPathDistance()
                .isMyPlayerInteractingWith()
                .isReachable()
                .findBestInteractable();

        if (interactingWith.isPresent())
            return interactingWith;

        List<Npc> target = new ArrayList<>();
        if (ATTACK_PORTALS) {
            target = Query.npcs().actionContains("Attack")
                    .inArea(Area.fromRadius(MyPlayer.getTile(), 8))
                    .sortedByPathDistance()
                    .isReachable()
                    .toList();
        } else {
            target = Query.npcs().actionContains("Attack")
                    .inArea(Area.fromRadius(MyPlayer.getTile(), 5))
                    .sortedByPathDistance()
                    .isReachable()
                    .toList();
        }

        for (Npc npc : target) {
            if (npc.getHealthBarPercent() == 0)
                continue;

            return Optional.of(npc);
        }
        return Optional.empty();
    }

    /*public static Optional<RSNPC> getTarget() {
        RSNPC[] target;
        if (ATTACK_PORTALS) {
            target = Entities.find(NpcEntity::new)
                    .actionsContains("Attack")
                    .sortByDistance()
                    .inArea(new RSArea(Player.getPosition(), 10))
                    .getResults();
        } else {
            target = Entities.find(NpcEntity::new)
                    .actionsContains("Attack")
                    .inArea(new RSArea(Player.getPosition(), 5))
                    .sortByDistance()
                    .getResults();
        }

        for (RSNPC npc : target) {
            if (npc.getHealthPercent() == 0)
                continue;
            //   if (!reach.canReach(npc.getPosition()))
            //     continue;

            return Optional.of(npc);
        }
        return Optional.empty();

    }*/

    public static Optional<RSNPC> getNearestPortal() {
        RSNPC portal = Entities.find(NpcEntity::new)
                .nameEquals("Portal")
                .sortByDistance()
                .getFirstResult();
        return Optional.ofNullable(portal);
    }

    public static boolean killTargets() {
        Optional<Npc> targ = PestUtils.getTarget();
        Optional<Npc> interactingCharacter = Query.npcs().isMyPlayerInteractingWith().findFirst();
        Optional<Npc> sdkTarg = Query.npcs().isMyPlayerInteractingWith()
                .isHealthBarVisible().findBestInteractable();

        if (MyPlayer.isHealthBarVisible() || interactingCharacter.isPresent()) {
            //Log.info("[Debug]: Waiting to finish combat");
            return Timer.waitCondition(() -> {
                AntiBan.timedActions();
                Waiting.waitNormal(700, 150);
                return MyPlayer.get().get().getInteractingCharacter().isEmpty();
            }, 3000, 45000);
        } else if (targ.isPresent()) {
            Log.info("[Debug]: Attacking Target");
            if (targ.get().getHealthBarPercent() == 0)
                Waiting.waitNormal(300, 50);

            if (targ.map(npc -> npc.getTile().distanceTo(MyPlayer.getTile()) > General.random(6, 8)).orElse(false) &&
                    targ.map(npc -> PathingUtil.localNav(npc.getTile())).orElse(false)) {
                Timer.waitCondition(() -> targ.get().isVisible(), 5000, 7000);
            }

            if (targ.map(t -> t.interact("Attack")).orElse(false)) {
                return Waiting.waitUntil(Utils.random(4000, 5000), 150,
                        () -> Player.getAnimation() != -1 || targ.get().isInteractingWithMe() ||
                                !targ.get().isValid());
            }
            Waiting.waitNormal(300, 50);

        }
        return false;
    }

    public static boolean waitForTarget() {
        if (!Game.isInInstance())
            return false;

        if (MyPlayer.isMoving())
            Timer.waitCondition(() -> !MyPlayer.isMoving(), 5000);

        Log.info("[Debug]: Waiting for target");
        return Timer.waitCondition(() -> {
            AntiBan.timedActions();
            return PestUtils.getTarget().isPresent() || !Game.isInInstance();
        }, 10000, 15000);

    }

    public static Area getCenterArea() {
        Optional<Npc> knight = Query.npcs()
                .nameContains("Void Knight")
                .idEquals(PestUtils.KNIGHT_ID)
                .maxDistance(40)
                .findBestInteractable();

        return knight.map(npc -> Area.fromRectangle(npc.getTile().translate(3, -2),
                npc.getTile().translate(-2, 3))).orElse(null);
    }

    public static Npc getLeaveKnight() {
        Optional<Npc> knight = Query.npcs()
                .nameContains("Squire")
                .actionContains("Leave")
                .findBestInteractable();

        return knight.orElse(null);
    }
}
