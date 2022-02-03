package scripts.AgilityAPI;

import dax.walker.utils.AccurateMouse;
import dax.walker.utils.camera.DaxCamera;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.GroundItems;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSGroundItem;
import org.tribot.api2007.types.RSItem;
import scripts.Data.Const;
import scripts.Data.Vars;
import scripts.Timer;
import scripts.Utils;
import scripts.cAgility;

import java.awt.*;
import java.util.List;
import java.util.Optional;

public class AgilUtils {


    public static Optional<scripts.AgilityAPI.Obstacle> getCurrentObstacle(List<scripts.AgilityAPI.Obstacle> allObstacles) {
        for (int i = 0; i < allObstacles.size(); i++) {
            if (allObstacles.get(i).isValidObstacle()) {

                return Optional.ofNullable(allObstacles.get(i));
            }
        }
        return Optional.empty();
    }


    public static boolean isWithinLevelRange(int lower, int upper) {
        return Skills.getCurrentLevel(Skills.SKILLS.AGILITY) < upper &&
                Skills.getCurrentLevel(Skills.SKILLS.AGILITY) >= lower;
    }

    public static void getMark(RSArea area) {

        RSGroundItem[] mark = GroundItems.findNearest(Const.MARK_OF_GRACE_ID);
        if (mark.length > 0 && area.contains(mark[0].getPosition()) && area.contains(Player.getPosition())) {
            if (Player.isMoving())
                Timer.waitCondition(() -> !Player.isMoving(), 5000, 7000);

            if (!mark[0].isClickable())
                DaxCamera.focus(mark[0]);

            General.println("[Debug]: Getting Mark of Grace", Color.RED);
            if (clickGroundItem(Const.MARK_OF_GRACE_ID))
                Vars.get().marksCollected++;
        }
    }

    public static boolean clickGroundItem(int ItemID) {
        RSGroundItem[] gItem = GroundItems.find(ItemID);
        if (gItem.length > 0) {


            if (!gItem[0].isClickable())
                DaxCamera.focus(gItem[0]);

            RSItem[] invItems = Inventory.find(ItemID);

            if (gItem[0].getDefinition().isStackable() && invItems.length > 0) {
                int stack = invItems[0].getStack();
                if (AccurateMouse.click(gItem[0], "Take")) {
                    return Timing.waitCondition(() -> Inventory.find(ItemID)[0].getStack() > stack, General.random(5000, 7000));
                }
            }

            if (AccurateMouse.click(gItem[0], "Take")) {
                return Timing.waitCondition(() -> Inventory.find(ItemID).length > invItems.length, General.random(5000, 7000));
            }
        }
        return false;
    }


    public static void eatSummerPie(int minlevel, int allowance) {
        int min = minlevel + (General.random(0, allowance));
        RSItem[] pie = Inventory.find(Const.SUMMER_PIE);
        Inventory.drop(Const.EMPTY_PIE_DISH);
        Utils.unselectItem();
        if (Skills.getCurrentLevel(Skills.SKILLS.AGILITY) <= min) {
            if (pie.length == 0) {
                General.println("[Debug]: Out of summer pies");
                cAgility.isRunning = false;
                return;
            } else {
                General.println("[Debug]: Eating summer pie");
                if (pie[0].click("Eat"))
                    Timer.waitCondition(() -> Player.getAnimation() != -1, 1500, 2000);
            }
        }
    }

}
