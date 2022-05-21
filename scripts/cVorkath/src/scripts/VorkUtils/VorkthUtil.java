package scripts.VorkUtils;

import dax.walker.utils.AccurateMouse;
import dax.walker.utils.camera.DaxCamera;
import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api.interfaces.Clickable;
import org.tribot.api2007.*;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.*;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Prayer;
import org.tribot.script.sdk.Waiting;
import scripts.EntitySelector.Entities;
import scripts.EntitySelector.finders.prefabs.ObjectEntity;
import scripts.Timer;
import scripts.Utils;

import java.awt.*;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.function.Predicate;

public class VorkthUtil {
    /**
     * PROJECTILES
     */
    public static final int TOXIC_FLAME = 1470;
    public static final int WHITE_SPAWN_FLAME = 395;
    public static final int ACID_SPRAY = 1483;
    public static final int SPAWN_PROJECTILE = 1484; // becomes spawn
    public static final int PRAYER_DISABLING_FLAME = 1471;
    public static final int BALL_FLAME = 1481; // this is the lobbed ball that can 1KO you

    /**
     * NPC IDs
     */
    public static final int SLEEPING_VORK = 8059;
    public static final int AWAKENING_VORK = 8058;
    public static final int ATTACKING_VORK = 8061;
    public static final int ZOMBIFIED_SPAWN = 8063;

    /**
     * Object IDs
     */
    public static final int ICE_CHUNKS_INSTANCE_ID = 31822;
    public static final int ICE_CHUNKS_ENTER_ID = 31990;


    /**
     * AREAS
     */
    public static final RSArea LUNAR_ISLE_AREA = new RSArea(new RSTile(2100, 3915, 0), 15);
    public static final RSArea DOCK_AREA = new RSArea(new RSTile(2640, 3696, 0), 5);
    public static final RSArea VORK_ISLAND_AREA = new RSArea(new RSTile(2272, 4045, 0), 15);

    public static Optional<RSTile> getWesternAcidTile() {
        Optional<RSTile> exitRockTile = getExitRockObject();
        return exitRockTile.map(t -> t.translate(-3, 1));
    }

    public static Optional<RSTile> getEasternAcidTile() {
        Optional<RSTile> exitRockTile = getExitRockObject();
        return exitRockTile.map(t -> t.translate(4, 1));
    }

    public static Optional<RSTile> getFightCenterTile() {
        Optional<RSTile> exitRockTile = getExitRockObject();
        return exitRockTile.map(t -> t.translate(0, 3));
    }

    public static Optional<RSTile> getExitRockObject() {
        RSObject exitRocks = Entities.find(ObjectEntity::new)
                .actionsContains("Escape")
                .idEquals(ICE_CHUNKS_INSTANCE_ID)
                .getFirstResult();
        if (exitRocks != null)
            return Optional.ofNullable(exitRocks.getPosition());

        return Optional.empty();
    }


    public static boolean eatFood() {
        Predicate<RSItem> food = Filters.Items.actionsEquals("Eat");

        RSItem[] item = Inventory.find(food);
        if (item != null && item.length > 0) {
            return item[0].click("Eat");
        }
        return false;
    }


    public static boolean clickVorkath(String clickString) {
        RSNPC[] vork = NPCs.findNearest("Vorkath");
        if (vork.length > 0) {
            if (!vork[0].isClickable())
                DaxCamera.focus(vork[0]);
            Log.info("Clicking Vorkath with " + clickString);
            return DynamicClicking.clickRSNPC(vork[0], clickString);
        }
        Log.warn("Failed to click Vorkath");
        return false;
    }


    public static boolean walkToTile(RSTile tile, boolean preferMinimapClick) {
        if (!tile.isClickable() || preferMinimapClick) {
            return AccurateMouse.clickMinimap(tile);
        }
        return DynamicClicking.clickRSTile(tile, "Walk here");
        //  return  AccurateMouse.walkScreenTile(tile);
    }

    public static boolean waitCond(BooleanSupplier waitUntilCondition, Clickable hoverPoint,
                                   int timeout) {
        int sd = timeout / 10;
        return Timing.waitCondition(() -> {
            General.sleep(20, 60);
            hoverPoint.hover();
            return waitUntilCondition.getAsBoolean();
        }, General.randomSD(timeout, sd));
    }

    public static boolean waitCond(BooleanSupplier waitUntilCondition, RSTile hoverPoint,
                                   int timeout) {
        Point hover = hoverPoint.getHumanHoverPoint();
        int sd = timeout / 10;
        return Waiting.waitUntil(General.randomSD(timeout, sd), 5, () -> {
            if (Mouse.getPos() != hover)
                Mouse.move(hover);
            return waitUntilCondition.getAsBoolean();
        });
    }

    public static boolean waitCond(BooleanSupplier waitUntilCondition, int timeout) {
        int sd = timeout / 10;
        return Waiting.waitUntil(General.randomSD(timeout, sd),
                20, waitUntilCondition);
    }

}
