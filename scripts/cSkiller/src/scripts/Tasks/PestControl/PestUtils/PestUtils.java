package scripts.Tasks.PestControl.PestUtils;

import dax.walker.utils.camera.DaxCamera;
import dax.walker_engine.local_pathfinding.Reachable;
import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api2007.Combat;
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
import org.tribot.script.sdk.types.Npc;
import scripts.AntiBan;
import scripts.EntitySelector.Entities;
import scripts.EntitySelector.finders.prefabs.NpcEntity;
import scripts.PathingUtil;
import scripts.Timer;
import scripts.Utils;

import java.util.Optional;

public class PestUtils {

    public static int PEST_POINTS = 0;
    public static final int STARTING_PEST_POINTS = 0;
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

    public static int getPestPoints() {
        RSInterface bar = Interfaces.get(407, 5);
        if (bar != null) {
            String fullString = bar.getText();
            String[] splitString = fullString.split("Pest Points: ");
            if (splitString.length > 1) {
                //   Log.log("[Debug]: Points is: " + splitString[1]);
                PEST_POINTS = Integer.parseInt(splitString[1]);
                return Integer.parseInt(splitString[1]);
            }
        }
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

    public static Optional<RSNPC> getTarget() {
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

    }

    public static Optional<RSNPC> getNearestPortal() {
        RSNPC portal = Entities.find(NpcEntity::new)
                .nameEquals("Portal")
                .sortByDistance()
                .getFirstResult();
        return Optional.ofNullable(portal);
    }

    public static boolean killTargets() {
        Optional<RSNPC> targ = PestUtils.getTarget();
        Optional<Character> interactingCharacter = MyPlayer.get().get().getInteractingCharacter();
        Optional<Npc> sdkTarg = Query.npcs().isInteractingWith(MyPlayer.get().get())
                        .isHealthBarVisible().findBestInteractable();
        if (Combat.isUnderAttack() || interactingCharacter.isPresent()) {
            Log.log("[Debug]: Waiting to finish combat");
            return Timer.waitCondition(() -> {
                AntiBan.timedActions();
                Waiting.waitNormal(700, 150);
                return  MyPlayer.get().get().getInteractingCharacter().isEmpty();
            }, 3000, 45000);
        } else if (targ.isPresent()) {
            Log.log("[Debug]: Attacking Target");
            if (targ.get().getHealthPercent() == 0)
                Waiting.waitNormal(300, 50);


            if (targ.get().getPosition().distanceTo(Player.getPosition()) > General.random(6, 8)) {
                PathingUtil.localNavigation(targ.get().getPosition().translate(-1, -1));
                Timer.waitCondition(() -> targ.get().isClickable(), 5000, 7000);
            }
            if (!targ.get().isClickable())
                DaxCamera.focus(targ.get());

            if (DynamicClicking.clickRSNPC(targ.get(), "Attack")) {
                return Timer.waitCondition(() -> Player.getAnimation() != -1, 3500, 4500);
            }
            Waiting.waitNormal(300, 50);

        }
        return false;
    }

    public static void waitForTarget() {
        if (!Game.isInInstance())
            return;

        if (Player.isMoving())
            Timer.waitCondition(() -> !Player.isMoving(), 5000);

        Log.log("[Debug]: Waiting for target");
        Timer.waitCondition(() -> {
            AntiBan.timedActions();
            return PestUtils.getTarget().isPresent() || !Game.isInInstance();
        }, 10000, 15000);

    }

    public static RSArea getCenterArea() {
        RSNPC[] knight = Entities.find(NpcEntity::new)
                .nameContains("Void Knight")
                .idEquals(PestUtils.KNIGHT_ID)
                .getResults();

        if (knight.length > 0) {
            return new RSArea(knight[0].getPosition().translate(3, -2),
                    knight[0].getPosition().translate(-2, 3));
        }
        return null;
    }

    public static RSNPC getLeaveKnight() {
        RSNPC[] knight = Entities.find(NpcEntity::new)
                .nameContains("Squire")
                .actionsContains("Leave")
                .getResults();

        if (knight.length > 0)
            return knight[0];
        return null;
    }
}
