package scripts;

import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api2007.Combat;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSCharacter;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSItemDefinition;

import java.util.function.Predicate;

/**
 * Much of this was taken from @Elon
 */
public class EatUtil {

    public static final int[] FOOD_IDS = {
            7946,   //monkfish
            379,   //lobster
            385,   //Shark
            329,  //salmon
            361,   //Tuna
    };

    public static int hpPercent() {
        int current = Combat.getHP();
        int max = Combat.getMaxHP();
        int Percent = current * 100 / max;
        return Percent;
    }

    public static boolean eatFood() {
        RSCharacter t = Combat.getTargetEntity();
        Predicate<RSItem> food = Filters.Items.actionsEquals("Eat");

        RSItem[] item = Inventory.find(food);
        if (item != null && item.length > 0) {
            if (item[0].click("Eat")) {
                if (t == null)
                    return Timer.waitCondition(() -> Player.getAnimation() == 829, 800, 1400);
                else
                    Utils.waitCondtion(() -> Player.getAnimation() == 829);
            }
            int chance = General.random(0, 100);
            if (t != null && chance > 65) {
                General.println("[EatUtil]: Reengaging target");
                return DynamicClicking.clickRSNPC(t, "Attack");

            } else if (t != null) {
                return true;
            }
        }

        return false;
    }

    public static boolean eatFood(boolean reengageTarget) {
        RSCharacter t = Combat.getTargetEntity();
        Predicate<RSItem> food = Filters.Items.actionsEquals("Eat");

        RSItem[] item = Inventory.find(food);
        if (item != null && item.length > 0) {
            if (item[0].click("Eat")) {
                if ( reengageTarget && t == null)
                    return Timer.waitCondition(() -> Player.getAnimation() == 829, 800, 1400);
                else
                    Timer.waitCondition(() -> Player.getAnimation() == 829, 800, 1400);
            }
            int chance = General.random(0, 100);
            if (reengageTarget && t != null && chance > 65) {
                General.println("[EatUtil]: Reengaging target");
                return DynamicClicking.clickRSNPC(t, "Attack");

            } else if (t != null) {
                return true;
            }
        }

        return false;
    }

    public static boolean hasFood() {
        Predicate<RSItem> food = Filters.Items.actionsEquals("Eat");
        if (food != null) {
            RSItem[] item = Inventory.find(food);
            return item != null && item.length > 0;
        }
        return false;
    }


    public static boolean drinkPotion(int[] potion) {
        RSItem[] invPotion = Inventory.find(potion);

        if (invPotion.length > 0) {
            String name = RSItemDefinition.get(potion[0]).getName();
            General.println("[Debug]: Drinking " + name);
            if (invPotion[0].click("Drink"))
                return Timer.waitCondition(() -> Player.getAnimation() == 829, 1500, 2000);

        } else
            return false;

        return false;
    }

}
