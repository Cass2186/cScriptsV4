package scripts.Tasks.Prayer.Wilderness;

import org.tribot.api.General;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSInterface;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.*;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.Player;
import org.tribot.script.sdk.types.Widget;
import scripts.Timer;

import java.awt.*;
import java.util.List;
import java.util.Optional;

public class PkObserver {

    public static boolean shouldHop() {
        int maxLevel = MyPlayer.getCombatLevel() + Combat.getWildernessLevel();
        int minLevel = MyPlayer.getCombatLevel() - Combat.getWildernessLevel();

        return Query.players()
                //.hasSkullIcon()
                .maxDistance(25)
                .maxLevel(maxLevel)
                .minLevel(minLevel)
                .isAny();
    }

    public static RSArea WHOLE_WILDERNESS = new RSArea(new RSTile(3365, 3524, 0), new RSTile(2963, 3967, 0));
    public static int ourCombatLevel = org.tribot.api2007.Player.getRSPlayer().getCombatLevel();
    public static int combatMin = ourCombatLevel - org.tribot.api2007.Combat.getWildernessLevel();
    public static int combatMax = ourCombatLevel + org.tribot.api2007.Combat.getWildernessLevel();
    public static int pointThreshold = 200;
    public static int points = 0;
    public static int nextWorld = org.tribot.api2007.WorldHopper.getRandomWorld(true, false);
    public static int PARENT = 69;
    public static int WHOLE_BOX = 5;

    public static boolean isWorldVisible(int world) {
        RSInterface worldInter = Interfaces.get(PARENT, 16, world);
        RSInterface boxInter = Interfaces.get(PARENT, WHOLE_BOX);
        if (worldInter != null && boxInter != null) {
            Rectangle boxRectangle = boxInter.getAbsoluteBounds().getBounds();
            if (boxRectangle.contains(worldInter.getAbsolutePosition())) {
                return true;
            }
        }
        return false;
    }

    public static boolean scrollToWorldNoClick(int world) {
        org.tribot.api2007.WorldHopper.openWorldSelect();
        RSInterface worldInter = Interfaces.get(PARENT, 16, world);
        RSInterface scrollInter = Interfaces.get(PARENT, 18, 1);
        RSInterface boxInter = Interfaces.get(PARENT, 15);
        Optional<Widget> box = Query.widgets().inIndexPath(PARENT, 15).findFirst();
        Optional<Widget> first = Query.widgets().inIndexPath(PARENT, 16, world).findFirst();
        if (worldInter != null && scrollInter != null) {
            Mouse.setSpeed(250);
            //General.println("[AntiPkThread]: Scrolling to world to max visible:  " + nextWorld);
            for (int i = 0; i < 100; i++) {
                if (box.map(b->!b.getBounds().contains(Mouse.getPos())).orElse(false)){
                    Mouse.moveBox(box.get().getBounds());
                    Waiting.waitNormal(20, 5);
                }
                if (first.map(f -> f.scrollTo()).orElse(false))
                    return true;
                /*
                if (boxInter.getAbsoluteBounds().contains(worldInter.getAbsolutePosition())) {
                    return true;
                } else if (worldInter.getAbsolutePosition().getY() > scrollInter.getAbsolutePosition().getY()) {
                    Mouse.scroll(false);
                    Waiting.waitNormal(30, 10);
                } else if (worldInter.getAbsolutePosition().getY() < scrollInter.getAbsolutePosition().getY()) {
                    Mouse.scroll(true);
                    Waiting.waitNormal(30, 10);
                }*/
            }
        }
        return false;
    }

    public static boolean scrollToWorld(int world) {

        RSInterface worldInter = Interfaces.get(PARENT, 16, world);
        RSInterface scrollInter = Interfaces.get(PARENT, 18, 1);
        RSInterface boxInter = Interfaces.get(PARENT, 15);
        if (worldInter != null && scrollInter != null) {

            General.println("[AntiPkThread]: Hopping due to dangerous player to " + nextWorld);
            for (int i = 0; i < 100; i++) {
                if (!boxInter.getAbsoluteBounds().contains(Mouse.getPos())) {
                    Mouse.moveBox(boxInter.getAbsoluteBounds());
                    Waiting.waitNormal(100, 20);
                }
                if (boxInter.getAbsoluteBounds().contains(worldInter.getAbsolutePosition())) {
                    if (worldInter.click())
                        return Timer.waitCondition(() -> org.tribot.api2007.WorldHopper.getWorld() == nextWorld, 10000, 15000);
                } else if (worldInter.getAbsolutePosition().getY() > scrollInter.getAbsolutePosition().getY()) {
                    Mouse.scroll(false);
                    Waiting.waitNormal(30, 10);
                } else if (worldInter.getAbsolutePosition().getY() < scrollInter.getAbsolutePosition().getY()) {
                    Mouse.scroll(true);
                    Waiting.waitNormal(30, 10);

                }
            }
            if (worldInter.click())
                return Timer.waitCondition(() -> org.tribot.api2007.WorldHopper.getWorld() == nextWorld, 10000, 15000);
        }
        return false;
    }


}
