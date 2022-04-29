package scripts.Tasks;

import org.tribot.api.General;
import org.tribot.api.Timing;

import org.tribot.api2007.Combat;
import org.tribot.api2007.Game;
import org.tribot.api2007.Skills;
import org.tribot.script.sdk.*;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.InventoryItem;
import scripts.*;
import scripts.NmzData.Const;
import scripts.NmzData.Vars;

import java.util.Optional;

public class DrinkPotion implements Task {


    public static void determinePotion() {
        if (Inventory.contains(ItemID.PRAYER_POTION)) {
            Vars.get().usingPrayerPots = true;
            Vars.get().usingAbsorptions = false;
        } else if (Inventory.contains(Const.ABSORPTION_POTION)) {
            Vars.get().usingPrayerPots = false;
            Vars.get().usingAbsorptions = true;
        }
        if (Inventory.contains(ItemID.SUPER_COMBAT_POTION)) {

            Vars.get().usingSuperCombat = true;
            Vars.get().usingOverloadPots = false;
        } else if (Inventory.contains(Const.OVERLOAD_POTION)) {
            Vars.get().usingOverloadPots = true;
        } else {
            Vars.get().usingOverloadPots = false;
        }
        if (Inventory.contains(ItemID.RANGING_POTION) || Inventory.contains(ItemID.SUPER_RANGING_4, ItemID.SUPER_RANGING_3,
                ItemID.SUPER_RANGING_2,
                ItemID.SUPER_RANGING_1)) {
            Vars.get().usingRangingPotion = true;
        }
        if (Inventory.contains(ItemID.LOCATOR_ORB)) {
            Vars.get().usingLocatorOrb = true;
        }
        Log.info("Using Super combat potions? " + Vars.get().usingSuperCombat);
        Log.info("Using prayer potions? " + Vars.get().usingPrayerPots);
        Log.info("Using absorption potions? " + Vars.get().usingAbsorptions);
        Log.info("Using overload potions? " + Vars.get().usingOverloadPots);
        Log.info("Using Ranging potions? " + Vars.get().usingRangingPotion);
    }


    public static boolean shouldDrinkPrayerPot() {
        int drinkAtAbsolute = getAbsolutePrayerDrinkAt();
        //   System.out.println("[Debug]: Drinking Prayer Potion at " + Vars.get().drinkPrayAtPercentage + "% || " +
        //          "Absolute: " + drinkAtAbsolute);
        return Prayer.getPrayerPoints() < drinkAtAbsolute;
    }

    public static int getAbsolutePrayerDrinkAt() {
        int maxPray = Skills.getActualLevel(Skills.SKILLS.PRAYER);
        return (int) (maxPray * (Vars.get().drinkPrayAtPercentage) / 100);
    }


    public boolean drinkPotion(int[] potion) {
        Optional<InventoryItem> invItem = Query.inventory()
                .idEquals(potion)
                .findClosestToMouse();
        if (invItem.map(i -> i.click("Drink")).orElse(false)) {
            invItem.ifPresent(pot -> Log.debug("Drinking " + pot.getName()));
            Waiting.waitNormal(500, 55);
            Vars.get().lastAction = System.currentTimeMillis();
            return true;
        }

        return false;
    }

    public void drinkPrayerPotion() {
        if (Vars.get().usingPrayerPots && shouldDrinkPrayerPot()) {
            General.println("[Debug]: Drinking Prayer Potion");
            determineSleep();

            //General.println("[Debug]: ABC2 Sleeping for " + AntiBan.getReactionTime());
            //long finTime = System.currentTimeMillis() + AntiBan.getReactionTime();
            // Timer.waitCondition(() -> System.currentTimeMillis() > finTime || Prayer.getPrayerPoints() < 4,
            //      AntiBan.getReactionTime());

            for (int i = 0; i < 2; i++) {
                if (PrayerUtil.drinkPrayerPotion()) {
                    Vars.get().drinkPrayAtPercentage = General.randomSD(6, 65, 40, 12);
                    Log.info("[Debug]: Drinking Next Prayer Potion at: " + getAbsolutePrayerDrinkAt());
                }
                if (Prayer.getPrayerPoints() > (Skills.getActualLevel(Skills.SKILLS.PRAYER)
                        - General.random(12, 16)))
                    break;
            }
        }
    }


    public boolean drinkPotion(int[] potionArray, Skill skill, int add) {
        if (skill.getCurrentLevel() <=
                (skill.getActualLevel() + add) && Inventory.contains(potionArray)) {
            Log.debug("Drinking " + skill.toString().toLowerCase() + " potion at "
                    + (skill.getActualLevel() + add));
            drinkPotion(potionArray);
            return true;
        }
        return false;
    }

    public void drinkPotion(int[] potionArray, Skill skill) {
        if (skill.getCurrentLevel() <=
                (skill.getActualLevel() + Vars.get().add) && Inventory.contains(potionArray)) {
            Log.debug("Drinking " + skill.toString().toLowerCase() + " potion at "
                    + (skill.getActualLevel() + Vars.get().add));
            drinkPotion(potionArray);
            //TODO make player specific preference
            Vars.get().add = General.random(3, 8);
        }
    }

    public static long timeSinceLastAction(long lastActionTime) {
        long time = System.currentTimeMillis() - lastActionTime;
        Log.debug("Time since last action: " + Timing.msToString(time));
        return time;
    }


    public static void determineSleep() {
        int divider = General.random(5, 10);
        int initialSleep = (int) ReactionGenerator.get().nextReactionTime(5000, 2000, 5, 0.1,
                300, 30000);
        //  long timeSince = timeSinceLastAction(Vars.get().lastAction);
        int sleep = initialSleep;
        // int sleep = (int) (timeSince / divider);
        if (shouldDrinkAbsorption() && Vars.get().usingAbsorptions) {
            sleep = General.randomSD(2800, 450);
        } /*else if (sleep > 55000) {
            sleep = General.random(7000, 20000);
        } else if (sleep < 100) {
            sleep = General.random(500, 2500);
        }*/
        Log.info("Sleeping for: " + sleep + "ms (unmodified: " + initialSleep + " ms)");
        Timer.waitCondition(() -> (Prayer.getPrayerPoints() < 5 && Vars.get().usingPrayerPots), sleep);
        // General.sleep(sleep);
    }

    public void rockCake() {
        Optional<InventoryItem> rockCake = Query.inventory()
                .idEquals(Const.ROCK_CAKE)
                .findClosestToMouse();
        Optional<InventoryItem> locatorOrb = Query.inventory()
                .idEquals(ItemID.LOCATOR_ORB)
                .findClosestToMouse();
        if ((rockCake.isPresent() || locatorOrb.isPresent()) &&
                Combat.getHP() != 1 //&& Vars.get().overloadTimer.isRunning()
                && Combat.getHP() >= Vars.get().eatRockCakeAt) {
            determineSleep();
            drinkOverload();
            if (Combat.getHP() > 1 &&
                    ((Vars.get().usingOverloadPots && Vars.get().overloadTimer.isRunning()) ||
                            Vars.get().usingAbsorptions)) {

                for (int i = 0; i < 50; i++) {
                    Log.info("Eating rock cake");
                    General.sleep(50, 80);
                    if (!Game.isInInstance())
                        break;
                    if (locatorOrb.map(l -> l.click("Feel")).orElse(false)) {
                        Waiting.waitNormal(325, 60);
                    } else if (rockCake.map(r -> r.click("Guzzle")).orElse(false)) {
                        Log.info("Guzzled");
                        General.sleep(General.randomSD(150, 700, 350, 75));
                    }
                    if (Combat.getHP() == 1)
                        break;
                }
            } else if (Vars.get().usingOverloadPots && !Vars.get().overloadTimer.isRunning()) {
                Log.debug("Overload timer isn't running");

            }

            Vars.get().eatRockCakeAt = Vars.get().getEatRockCakeAt();
            Log.info("Next eating rock cake at: " + Vars.get().eatRockCakeAt);
        } else if (Vars.get().usingAbsorptions) {
            Log.error("Error with rock caking: do we have one? " + rockCake.isPresent());
            Log.error("Is HP > eatRockCake at: " + (Combat.getHP() >= Vars.get().eatRockCakeAt));
        }
    }


    public void drinkOverload() {
        if (Vars.get().usingOverloadPots) {
            if (Vars.get().usingPrayerPots)
                Prayer.enableAll(Prayer.PROTECT_FROM_MELEE);

            int startHp = Combat.getHP();
            if (startHp > 50) {
                Log.info("Drinking overload");
                // determineSleep(); //redundant with the one called in rockcake() prior to this

                if (drinkPotion(Const.OVERLOAD_POTION)) {
                    Vars.get().overloadTimer = new Timer(300000);
                    Log.info("Waiting for overload to finish");
                    if (!Timer.waitCondition(() -> Combat.getHP() <= (startHp - 41), 15000, 20000)) {
                        // will try twice to drink OL
                        if (drinkPotion(Const.OVERLOAD_POTION)) {
                            Vars.get().overloadTimer = new Timer(300000);
                            General.println("Waiting for overload to finish");
                            Timer.waitCondition(() -> Combat.getHP() <= (startHp - 41), 15000, 20000);
                        }
                    }
                }
            } else {
                Vars.get().overloadTimer = new Timer(300000);
                Log.info("Cannot overload due to low HP");
            }
        }
    }

    public static boolean shouldDrinkAbsorption() {
        return Utils.getVarBitValue(Varbits.NMZ_ABSORPTION.getId()) <= Vars.get().drinkAbsorptionAt;
    }


    public void drinkAbsorption() {
        if (shouldDrinkAbsorption()) {
            // if we have >150 abs points left it will sleep, otherwise it drinks to avoid death
            Log.info("Drinking up to: " + Vars.get().drinkAbsorptionUpTo);
            if (Utils.getVarBitValue(Varbits.NMZ_ABSORPTION.getId()) < Vars.get().drinkAbsorptionUpTo) {
                if (Utils.getVarBitValue(Varbits.NMZ_ABSORPTION.getId()) > 150)
                    determineSleep();

                for (int i = 0; i < 100; i++) {
                    Waiting.waitNormal(165, 35);
                    Optional<InventoryItem> abs = Query.inventory()
                            .idEquals(Const.ABSORPTION_POTION)
                            .findClosestToMouse();

                    if (!Game.isInInstance() || !org.tribot.script.sdk.Login.isLoggedIn())
                        break;
                    if (Utils.getVarBitValue(Varbits.NMZ_ABSORPTION.getId()) < Vars.get().drinkAbsorptionUpTo &&
                            abs.map(a -> a.click("Drink")).orElse(false)) {
                        Waiting.waitNormal(200, 35);
                    } else
                        break;
                }
            }
            Vars.get().drinkAbsorptionUpTo = Utils.random(400, 800);
            Vars.get().drinkAbsorptionAt = Utils.random(50, 150);
            Log.info("[Debug]: Next drinking Absorption at " + Vars.get().drinkAbsorptionAt
                    + " up to: " + Vars.get().drinkAbsorptionUpTo);
            rockCake();
        }
    }


    @Override
    public String toString() {
        return "Drinking Potion";
    }

    @Override
    public Priority priority() {
        return Priority.HIGH;
    }

    @Override
    public boolean validate() {
        return Game.isInInstance() &&
                ((Vars.get().usingPrayerPots && shouldDrinkPrayerPot()) ||
                (Vars.get().usingOverloadPots && !Vars.get().overloadTimer.isRunning()) ||
                (shouldDrinkAbsorption() && Vars.get().usingAbsorptions) ||
                (Vars.get().usingAbsorptions &&
                        Combat.getHP() >= Vars.get().eatRockCakeAt));
    }

    @Override
    public void execute() {
        Log.info("DrinkPotion validated: " +
                (Vars.get().usingOverloadPots && !Vars.get().overloadTimer.isRunning()));

        drinkPrayerPotion();

        if (Vars.get().usingAbsorptions)
            drinkAbsorption();

        drinkPotion(Const.SUPER_RANGING_POTION, Skill.RANGED);
        drinkPotion(ItemID.RANGING_POTION, Skill.RANGED);
        if (drinkPotion(ItemID.SUPER_COMBAT_POTION, Skill.STRENGTH, Vars.get().superCombatAdd)) {
            Vars.get().superCombatAdd = General.randomSD(Vars.get().superCombatPotionAddMean,
                    Vars.get().superCombatPotionAddSd);
        }

        if (Vars.get().usingOverloadPots && !Vars.get().overloadTimer.isRunning())
            drinkOverload();

        rockCake();

    }
}