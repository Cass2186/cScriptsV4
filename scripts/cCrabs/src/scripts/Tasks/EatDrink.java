package scripts.Tasks;


import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSItem;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.InventoryItem;
import scripts.AntiBan;
import scripts.Data.Const;
import scripts.Data.Vars;
import scripts.Timer;


import scripts.Utils;

import java.util.Optional;

public class EatDrink implements Task {

    public static int drinkPPotAt = General.random(5, 18);

    /**
     * Methods - Drink/eat
     */

    public void drinkCombatPotion() {
        if (Skills.getCurrentLevel(Skills.SKILLS.STRENGTH) <= Skills.getActualLevel(Skills.SKILLS.STRENGTH) + Vars.get().add) {
            RSItem[] combatPot = Inventory.find(Const.COMBAT_POTION);
            if (combatPot.length > 0 && combatPot[0].click("Drink")) {
                Vars.get().add = General.random(1, 4);
                int nextDrinkLevel = Skills.getActualLevel(Skills.SKILLS.STRENGTH) + Vars.get().add;
                General.println("[Debug]: Next Drinking Fight Potion at " + nextDrinkLevel + " Strength");
            }
        }
    }

    public void setPotionAdd(int[] potionId, Skills.SKILLS skill) {
        if (Utils.getItemName(potionId[0]).toLowerCase().contains("super")) {
            Vars.get().add = (int) (Utils.getSuperLevelBoost(skill) / 4) + General.random(0, 3);
            //  General.println("[Debug]: Super add is " + Vars.get().add);
        } else {
            Vars.get().add = (int) (Utils.getLevelBoost(skill) / 4) + General.random(0, 3);
            // General.println("[Debug]: Regular add is " + Vars.get().add);
        }
    }

    public boolean drinkPotion(int[] potionId, Skills.SKILLS skill) {
        setPotionAdd(potionId, skill);
        if (Skills.getCurrentLevel(skill) <= Skills.getActualLevel(skill) + Vars.get().add) {
            RSItem[] potion = Inventory.find(potionId);
            if (potion.length > 0) {

                if (potion[0].click("Drink")) {
                    setPotionAdd(potionId, skill);
                    Waiting.waitNormal(200, 45);
                    return Timer.slowWaitCondition(() -> Player.getAnimation() == 829, 2500, 3500);
                }
            }
        }
        return false;
    }

    public void drinkPrayerPotion() {
        if (checkPrayer()) {
            RSItem[] potion = Inventory.find(Const.PRAYER_POTION);
            if (potion.length > 0 && potion[0].click("Drink")) {
                drinkPPotAt = General.randomSD(4, 22, 14, 4);
                General.println("[Debug]: Next Drinking Potion at " + drinkPPotAt);
                Timer.waitCondition(() -> Player.getAnimation() == 829, 2500, 3500);
            }
        }
    }

    public boolean drinkDivinePotion() {
        if (Skills.SKILLS.RANGED.getActualLevel() == Skills.SKILLS.RANGED.getCurrentLevel()) {
            Optional<InventoryItem> pot = Query.inventory().nameContains("Divine")
                    .nameContains("rang")
                    .findClosestToMouse();
            return pot.map(p -> p.click("Drink")).orElse(false);

        }
        return false;
    }

    public boolean eat() {
        if (Combat.getHPRatio() < Vars.get().eatAt) {
            General.println("[Debug]: Eating");
            RSItem[] food = Inventory.find(Filters.Items.actionsEquals("Eat"));

            if (food.length > 0 && food[0].click()) {
                Vars.get().eatAt = General.random(35, 60);
                General.println("[ABC2]: Next eating at: " + Vars.get().eatAt + "%");
                General.sleep(General.random(200, 800));
                return true;
            } else {
                General.println("[Debug]: Out of food and need to eat, leaving");
                 Vars.get().shouldBank = true;
            }
        }
        return false;
    }


    public boolean checkSkill(Skills.SKILLS skill) {
        return Skills.getCurrentLevel(skill) <= Skills.getActualLevel(skill) + Vars.get().add;
    }


    public static boolean checkPrayer() {
        RSItem[] invPot = Inventory.find(Const.PRAYER_POTION);
        return invPot.length > 0 && Prayer.getPrayerPoints() < drinkPPotAt && Vars.get().usingPrayer;
    }




    @Override
    public String toString() {
        return "Eating/Drinking Potion";
    }

    @Override
    public Priority priority() {
        return Priority.HIGH;
    }

    @Override
    public boolean validate() {

        if (Login.getLoginState().equals(Login.STATE.INGAME)) {
            Optional<InventoryItem> pot = Query.inventory().nameContains("Divine")
                    .nameContains("rang")
                    .findClosestToMouse();
            if (pot.isPresent()) {
                return (Skills.SKILLS.RANGED.getActualLevel() == Skills.SKILLS.RANGED.getCurrentLevel())
                        || Vars.get().shouldEat || checkPrayer()
                        || Vars.get().shouldDrink || Combat.getHPRatio() < Vars.get().eatAt;
            }
            if (Inventory.find(Const.RANGING_POTION).length > 0) {
                return checkSkill(Skills.SKILLS.RANGED) || Vars.get().shouldEat || checkPrayer()
                        || Vars.get().shouldDrink || Combat.getHPRatio() < Vars.get().eatAt;
            }
            if (Inventory.find(Const.SUPER_STRENGTH_POTION).length > 0) {
                return checkSkill(Skills.SKILLS.STRENGTH)
                        || Vars.get().shouldEat || Vars.get().shouldDrink
                        || Combat.getHPRatio() < Vars.get().eatAt;
            }
            if (Inventory.find(Const.SUPER_ATTACK_POTION).length > 0) {
                return checkSkill(Skills.SKILLS.ATTACK) || Vars.get().shouldEat
                        || Vars.get().shouldDrink || Combat.getHPRatio() < Vars.get().eatAt;
            }
            if (Inventory.find(Const.SUPER_DEFENCE_POTION).length > 0) {
                return checkSkill(Skills.SKILLS.DEFENCE) || Vars.get().shouldEat
                        || Vars.get().shouldDrink || Combat.getHPRatio() < Vars.get().eatAt;
            }
            return Vars.get().shouldEat || Vars.get().shouldDrink
                    || Combat.getHPRatio() < Vars.get().eatAt;
        }
        return false;
    }

    @Override
    public void execute() {
        eat();
        if (!drinkDivinePotion())
            drinkPotion(Const.RANGING_POTION, Skills.SKILLS.RANGED);
        else
            Waiting.waitNormal(250,75);
        drinkPotion(Const.SUPER_STRENGTH_POTION, Skills.SKILLS.STRENGTH);
        drinkPotion(Const.SUPER_ATTACK_POTION, Skills.SKILLS.ATTACK);
        drinkPotion(Const.SUPER_DEFENCE_POTION, Skills.SKILLS.DEFENCE);
        drinkPotion(Const.SUPER_DEFENCE_POTION, Skills.SKILLS.DEFENCE);
        drinkCombatPotion();
        drinkPrayerPotion();
        Vars.get().shouldDrink = false;
        Vars.get().shouldEat = false;
    }

}

