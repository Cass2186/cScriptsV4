package scripts.Tasks.Combat.CrabTasks;


import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSItem;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.InventoryItem;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.ItemID;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.Tasks.Combat.Data.CrabVars;


import scripts.Timer;
import scripts.Utils;

import java.util.Optional;

public class EatDrink implements Task {

    int drinkPPotAt = General.random(5, 18);

    /**
     * Methods - Drink/eat
     */

    public void drinkCombatPotion() {
        if (Skills.getCurrentLevel(Skills.SKILLS.STRENGTH) <= Skills.getActualLevel(Skills.SKILLS.STRENGTH) + scripts.Tasks.Combat.Data.CrabVars.get().add) {
            RSItem[] combatPot = Inventory.find(ItemID.COMBAT_POTION);
            if (combatPot.length > 0 && combatPot[0].click("Drink")) {
                CrabVars.get().add = General.random(1, 4);
                int nextDrinkLevel = Skills.getActualLevel(Skills.SKILLS.STRENGTH) + scripts.Tasks.Combat.Data.CrabVars.get().add;
                General.println("[Debug]: Next Drinking Fight Potion at " + nextDrinkLevel + " Strength");
            }
        }
    }

    public void setPotionAdd(int[] potionId, Skills.SKILLS skill) {
        if (Utils.getItemName(potionId[0]).toLowerCase().contains("super")) {
            CrabVars.get().add = (int) (Utils.getSuperLevelBoost(skill) / 4) + General.random(0, 3);
            //  General.println("[Debug]: Super add is " + Vars.get().add);
        } else {
            CrabVars.get().add = (int) (Utils.getLevelBoost(skill) / 4) + General.random(0, 3);
            // General.println("[Debug]: Regular add is " + Vars.get().add);
        }
    }

    public boolean drinkPotion(int[] potionId, Skills.SKILLS skill) {
        setPotionAdd(potionId, skill);
        if (Skills.getCurrentLevel(skill) <= Skills.getActualLevel(skill) + CrabVars.get().add) {
            RSItem[] potion = Inventory.find(potionId);
            if (potion.length > 0 && potion[0].click("Drink")) {
                setPotionAdd(potionId, skill);
                Waiting.waitNormal(200, 45);
                return Timer.slowWaitCondition(() -> Player.getAnimation() == 829, 2500, 3500);

            }
        }
        return false;
    }

    public void drinkPrayerPotion() {
        if (checkPrayer()) {
            RSItem[] potion = Inventory.find(ItemID.PRAYER_POTION);
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

    public Optional<Skills.SKILLS> getSkillFromPotion(Optional<InventoryItem> pot) {
        if (pot.isEmpty())
            return Optional.empty();

        String name = pot.get().getDefinition().getName().toLowerCase();
        if (name.contains("ranging")) {
            return Optional.of(Skills.SKILLS.RANGED);
        } else if (name.contains("strength")) {
            return Optional.of(Skills.SKILLS.STRENGTH);
        } else if (name.contains("attack")) {
            return Optional.of(Skills.SKILLS.ATTACK);
        } else if (name.contains("defence")) {
            return Optional.of(Skills.SKILLS.DEFENCE);
        } else if (name.contains("combat")) {
            return Optional.of(Skills.SKILLS.STRENGTH);
        }
        return Optional.empty();
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
                CrabVars.get().shouldBank = true;
            }
        }
        return false;
    }


    public boolean checkSkill(Skills.SKILLS skill) {
        return Skills.getCurrentLevel(skill) <= Skills.getActualLevel(skill) + scripts.Tasks.Combat.Data.CrabVars.get().add;
    }


    public boolean checkPrayer() {
        RSItem[] invPot = Inventory.find(ItemID.PRAYER_POTION);
        return invPot.length > 0 && Prayer.getPrayerPoints() < drinkPPotAt && CrabVars.get().usingPrayer;
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
        if (Vars.get().currentTask != null &&
                Vars.get().currentTask.equals(SkillTasks.ATTACK) ||
                Vars.get().currentTask.equals(SkillTasks.STRENGTH) ||
                Vars.get().currentTask.equals(SkillTasks.DEFENCE) ||
                Vars.get().currentTask.equals(SkillTasks.RANGED)) {
            if (Login.getLoginState().equals(Login.STATE.INGAME)) {
                Optional<InventoryItem> pot = Query.inventory().nameContains("Divine")
                        .nameContains("rang")
                        .findClosestToMouse();
                if (pot.isPresent()) {
                    return (Skills.SKILLS.RANGED.getActualLevel() == Skills.SKILLS.RANGED.getCurrentLevel()) ||
                            checkSkill(Skills.SKILLS.RANGED) || CrabVars.get().shouldEat || checkPrayer()
                            || CrabVars.get().shouldDrink || Combat.getHPRatio() < Vars.get().eatAt;
                }
                if (Inventory.find(ItemID.RANGING_POTION).length > 0) {
                    return checkSkill(Skills.SKILLS.RANGED) || CrabVars.get().shouldEat || checkPrayer()
                            || CrabVars.get().shouldDrink || Combat.getHPRatio() < Vars.get().eatAt;
                }
                if (Inventory.find(ItemID.SUPER_STRENGTH_POTION).length > 0) {
                    return checkSkill(Skills.SKILLS.STRENGTH)
                            || CrabVars.get().shouldEat || CrabVars.get().shouldDrink
                            || Combat.getHPRatio() < Vars.get().eatAt;
                }
                if (Inventory.find(ItemID.SUPER_ATTACK_POTION).length > 0) {
                    return checkSkill(Skills.SKILLS.ATTACK) || CrabVars.get().shouldEat
                            || CrabVars.get().shouldDrink || Combat.getHPRatio() < Vars.get().eatAt;
                }
                if (Inventory.find(ItemID.SUPER_DEFENCE_POTION).length > 0) {
                    return checkSkill(Skills.SKILLS.DEFENCE) || CrabVars.get().shouldEat
                            || CrabVars.get().shouldDrink || Combat.getHPRatio() < Vars.get().eatAt;
                }
                return CrabVars.get().shouldEat || CrabVars.get().shouldDrink
                        || Combat.getHPRatio() < Vars.get().eatAt;
            }
        }
        return false;
    }

    @Override
    public void execute() {
        eat();
        if (!drinkDivinePotion())
            drinkPotion(ItemID.RANGING_POTION, Skills.SKILLS.RANGED);
        drinkPotion(ItemID.SUPER_STRENGTH_POTION, Skills.SKILLS.STRENGTH);
        drinkPotion(ItemID.SUPER_ATTACK_POTION, Skills.SKILLS.ATTACK);
        drinkPotion(ItemID.SUPER_DEFENCE_POTION, Skills.SKILLS.DEFENCE);
        drinkPotion(ItemID.SUPER_DEFENCE_POTION, Skills.SKILLS.DEFENCE);
        drinkCombatPotion();
        drinkPrayerPotion();
    }

    @Override
    public String taskName() {
        return "Combat Training";
    }

}

