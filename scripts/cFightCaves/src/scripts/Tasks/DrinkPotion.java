package scripts.Tasks;

import org.tribot.api.General;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.NPCChat;
import org.tribot.api2007.Prayer;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSItem;
import org.tribot.script.sdk.*;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.InventoryItem;
import scripts.NpcChat;
import scripts.Utils;

import java.io.IOException;
import java.util.Optional;

public class DrinkPotion implements Task {

    int boostedCombatStats = 0;
    int actualCombatStats = 0;
    private static String prayer = "Prayer";
    private static String boost = "Saradomin";
    private static String ranged = "Bastion";
    private static String restore = "Super restore";


    public String toString() {
        return "Drinking Potion";
    }

    boolean drinkBoostPotion(String name) {
        if (shouldDrinkPotion(name)) {
            if (needsBoostPotion(name)) {
                Log.info("We need to boost: " + name);
                Optional<InventoryItem> potion = getPotion(name);
                return potion.map(p -> p.click("Drink")).orElse(false);
            }
        }
        return false;
    }

    boolean drinkRangePotion() {
        if (needsRangePotion()) {
            Log.info("We need to boost our range!");
            Optional<InventoryItem> potion = getPotion(ranged);
            return potion.map(p -> p.click("Drink")).orElse(false);
        }
        return false;
    }

    boolean drinkRestorePotion() {
        if (shouldDrinkPotion(restore)) {
            Log.info("We need to drink super restore");
            Optional<InventoryItem> potion = getPotion(restore);
            return potion.map(p -> p.click("Drink")).orElse(false);
        }
        return true;
    }

    boolean drinkPrayerPotion(String name) {
        if (needsPrayerPotion(name)) {
            Optional<InventoryItem> potion = getPotion(prayer);
            return potion.map(p -> p.click("Drink")).orElse(false);
        }
        return false;
    }

    public boolean isPendingCompletion() {
        return GameState.isInInstance() &&
                (needsBoostPotion(ranged) || shouldDrinkPotion(restore) ||
                        needsPrayerPotion(prayer) || needsRangePotion());
    }


    boolean shouldDrinkPotion(String name) {
        if (!hasPotion(name)) {
            return false;
        }
        return (
                (Skills.getActualLevel(Skills.SKILLS.ATTACK) > Skills.getCurrentLevel(Skills.SKILLS.ATTACK))
                        || (Skills.getActualLevel(Skills.SKILLS.DEFENCE) > Skills.getCurrentLevel(Skills.SKILLS.DEFENCE))
                        || (Skills.getActualLevel(Skills.SKILLS.STRENGTH) > Skills.getCurrentLevel(Skills.SKILLS.STRENGTH))
                        || (Skills.getActualLevel(Skills.SKILLS.MAGIC) > Skills.getCurrentLevel(Skills.SKILLS.MAGIC))
                        || (Skills.getActualLevel(Skills.SKILLS.RANGED) > Skills.getCurrentLevel(Skills.SKILLS.RANGED)) || Prayer.getPrayerPoints() <= General.random(5, 10));
    }

    boolean needsRangePotion() {
        if (!hasPotion(ranged)) {
            return false;
        }
        return Skills.getCurrentLevel(Skills.SKILLS.RANGED) < (Skills.getActualLevel(Skills.SKILLS.RANGED) + General.random(5, 10));
    }


    boolean needsBoostPotion(String name) {
        if (!hasPotion(name)) {
            return false;
        }
        boostedCombatStats = (Skills.getCurrentLevel(Skills.SKILLS.ATTACK) + Skills.getCurrentLevel(Skills.SKILLS.DEFENCE) + Skills.getCurrentLevel(Skills.SKILLS.STRENGTH) + Skills.getCurrentLevel(Skills.SKILLS.MAGIC) + Skills.getCurrentLevel(Skills.SKILLS.RANGED));
        actualCombatStats = (Skills.getActualLevel(Skills.SKILLS.ATTACK) + Skills.getActualLevel(Skills.SKILLS.DEFENCE) + Skills.getActualLevel(Skills.SKILLS.STRENGTH) + Skills.getActualLevel(Skills.SKILLS.MAGIC) + Skills.getActualLevel(Skills.SKILLS.RANGED));
        return (boostedCombatStats - actualCombatStats < General.random(5, 10)) ||
               MyPlayer.getCurrentHealthPercent() < 0.5;
    }

    boolean needsPrayerPotion(String name) {
        if (!hasPotion(name)) {
            return false;
        }
        return Prayer.getPrayerPoints() <= General.random(5, 10);
    }

    Optional<InventoryItem> getPotion(String name) {
        return Query.inventory().nameContains(name).findClosestToMouse();
    }

    boolean hasPotion(String name) {
        return Query.inventory().nameContains(name).isAny();
    }

    @Override
    public Priority priority() {
        return Priority.HIGHEST;
    }

    @Override
    public boolean validate() {
        return isPendingCompletion();
    }

    @Override
    public void execute() {
        if (!isPendingCompletion()) {
            return;
        }
        Log.info("Should drink pray pot " + needsPrayerPotion(prayer));
        Log.info("Should drink boost pot " + needsBoostPotion(ranged));
        Log.info("Should drink restore pot" + shouldDrinkPotion(restore));
        if (ChatScreen.isOpen()) {
            NpcChat.handle();
        }
        if (drinkPrayerPotion(prayer)) {
            if (Waiting.waitUntil(300, 5, () -> !needsPrayerPotion(prayer))) {
                Utils.idlePredictableAction();
                return;
            }
        }

        if (drinkRestorePotion()) {
            if (Waiting.waitUntil(300, 5, () -> !shouldDrinkPotion(restore))) {
                Utils.idlePredictableAction();
                return;
            }
        }

        if (!shouldDrinkPotion(restore) && drinkBoostPotion(ranged)) {
            Log.info("drinking bastion");
            if (Waiting.waitUntil(300, 5, () -> !needsBoostPotion(boost))) {
                Utils.idlePredictableAction();
                return;
            }
        }

        if(!shouldDrinkPotion(restore) && !needsBoostPotion(boost)){
            if (drinkRangePotion()) {
                if (Waiting.waitUntil(300, 5, () -> !needsRangePotion())) {
                    Utils.idlePredictableAction();
                }
            }
        }
    }
}
