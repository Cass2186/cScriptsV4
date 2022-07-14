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
import org.tribot.script.sdk.types.World;
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

    public static int nextWorld =
            getNextWorld();
    public static int PARENT = 69;
    public static int WHOLE_BOX = 5;


    public static int getNextWorld() {
        Optional<World> first = Query.worlds().isNotCurrentWorld()
                .isMembers().
                isNotAllTypes(World.Type.PVP)
                .isLowPing()
                .isMainGame()
                .isRequirementsMet()
                .findRandom();
        Optional<World> second = Query.worlds().isNotCurrentWorld()
                .isMembers()
                .isRequirementsMet()
                .isMainGame()
                .isNotAllTypes(World.Type.PVP)
                .findRandom();
        return first.map(World::getWorldNumber).orElse(second.map(World::getWorldNumber).orElse(-1));
    }

    public static int getScore(Player player) {
        int score = 0;
        int maxLevel = MyPlayer.getCombatLevel() + Combat.getWildernessLevel();
        int minLevel = MyPlayer.getCombatLevel() - Combat.getWildernessLevel();

        if (player.getCombatLevel() < minLevel || player.getCombatLevel() > maxLevel)
            return 0;

        if (player.getSkullIcon().isPresent())
            return score + 201; //return to hop immediatley

        if (player.getEquipment().stream().anyMatch(
                eq-> eq.getName().contains("Mystic")))
            score = score + 50;

        if (player.getEquipment().stream().anyMatch(eq-> eq.getName().contains("xerician")))
            score = score + 50;

        if (player.getEquippedItem(Equipment.Slot.WEAPON).stream().anyMatch(
                eq-> eq.getName().contains("staff") || eq.getName().contains("crossbow")))
            score = score + 75;


        return score;
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
                if (box.map(b -> !b.getBounds().contains(Mouse.getPos())).orElse(false)) {
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


}
