package scripts.Tasks;

import dax.walker.utils.AccurateMouse;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSItemDefinition;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Waiting;
import scripts.*;
import scripts.NmzData.Const;
import scripts.NmzData.Vars;

import java.util.List;

public class DrinkPotion implements Task {


    public static void determinePotion() {
        if (Inventory.find(ItemID.PRAYER_POTION).length > 0) {
            Vars.get().usingPrayerPots = true;
            Vars.get().usingAbsorptions = false;
        } else if (Inventory.find(Const.ABSORPTION_POTION).length > 0) {
            Vars.get().usingPrayerPots = false;
            Vars.get().usingAbsorptions = true;
        }
        if (Inventory.find(ItemID.SUPER_COMBAT_POTION).length > 0) {

            Vars.get().usingSuperCombat = true;
            Vars.get().usingOverloadPots = false;
        } else if (Inventory.find(Const.OVERLOAD_POTION).length > 0) {
            Vars.get().usingOverloadPots = true;
        } else {
            Vars.get().usingOverloadPots = false;
        }
        Log.info("[Debug]: Using Super combat potions? " + Vars.get().usingSuperCombat);
        Log.info("[Debug]: Using prayer potions? " + Vars.get().usingPrayerPots);
        Log.info("[Debug]: Using absorption potions? " + Vars.get().usingAbsorptions);
        Log.info("[Debug]: Using overload potions? " + Vars.get().usingOverloadPots);

    }


    public static boolean shouldDrinkPrayerPot() {
        int drinkAtAbsolute = getAbsolutePrayerDrinkAt();
        System.out.println("[Debug]: Drinking Prayer Potion at " + Vars.get().drinkPrayAtPercentage + "% || " +
                "Absolute: " + drinkAtAbsolute);
        return Prayer.getPrayerPoints() < drinkAtAbsolute;
    }

    public static int getAbsolutePrayerDrinkAt() {
        int maxPray = Skills.getActualLevel(Skills.SKILLS.PRAYER);
        return (int) (maxPray * (Vars.get().drinkPrayAtPercentage) / 100);
    }


    public boolean drinkPotion(int[] potion) {
        RSItem[] invItem = Inventory.find(potion);
        String potionName = RSItemDefinition.get(potion[0]).getName();
        if (invItem.length > 0) {
            General.println("[Debug]: Drinking " + potionName);
            if (invItem[0].click("Drink")) {
                Waiting.waitNormal(300, 65);
                Vars.get().lastAction = System.currentTimeMillis();
                return true;
            }
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

    public void drinkPotion(int[] potionArray, Skills.SKILLS skill) {
        General.println("Drinking ranged at " + (Skills.getActualLevel(skill) + Vars.get().add));
        if (Skills.getCurrentLevel(skill) <=
                Skills.getActualLevel(skill) + Vars.get().add) {
            Log.info("[DrinkPotion]: Drinking potion");
            drinkPotion(potionArray);
            Vars.get().add = General.random(3, 10);

        }
    }

    public static long timeSinceLastAction(long lastActionTime) {
        long time = System.currentTimeMillis() - lastActionTime;
        General.println("[Debug]: Time since last action: " + Timing.msToString(time));
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
        Log.info("[Debug]: Sleeping for: " + sleep + "ms (unmodified: " + initialSleep + " ms)");
        Timer.waitCondition(() -> (Prayer.getPrayerPoints() < 5 && Vars.get().usingPrayerPots), sleep);
        // General.sleep(sleep);
    }

    public void rockCake() {
        RSItem[] rockCake = Inventory.find(Const.ROCK_CAKE);
        if (rockCake.length > 0 &&
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

                    if (rockCake[0].click("Guzzle")) {
                        Log.info("[Debug]: Guzzled");
                        General.sleep(General.randomSD(150, 700, 350, 75));
                    }
                    if (Combat.getHP() == 1)
                        break;
                }
            }

            Vars.get().eatRockCakeAt = General.randomSD(2, 6, 3, 2);
            Log.info("[Debug]: Next eating rock cake at: " + Vars.get().eatRockCakeAt);
        }
    }


    public void drinkOverload() {
        if (Vars.get().usingOverloadPots) {
            if (Vars.get().usingPrayerPots)
                Prayer.enable(Prayer.PRAYERS.PROTECT_FROM_MELEE);

            int startHp = Combat.getHP();
            if (startHp > 50) {
                Log.info("Drinking overload");
                determineSleep();

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
                rockCake();
            } else {
                Vars.get().overloadTimer = new Timer(300000);
                Log.info("[Debug]: Cannot overload due to low HP");
                rockCake();
            }
        }
    }

    public static boolean shouldDrinkAbsorption() {
        return Utils.getVarBitValue(Varbits.NMZ_ABSORPTION.getId()) <= Vars.get().drinkAbsorptionAt;
    }


    public void drinkAbsorption() {
        if (shouldDrinkAbsorption()) {

            // if we have >150 abs points left it will sleep, otherwise it drinks to avoid death
            if (Utils.getVarBitValue(Varbits.NMZ_ABSORPTION.getId()) > 150)
                determineSleep();

            Log.info("[Debug]: Drinking up to: " + Vars.get().drinkAbsorptionUpTo);
            if (Utils.getVarBitValue(Varbits.NMZ_ABSORPTION.getId()) < Vars.get().drinkAbsorptionUpTo) {
                for (int i =0; i < 100; i++) {
                    Waiting.waitNormal(120,30);
                    if (!Game.isInInstance() || !org.tribot.script.sdk.Login.isLoggedIn())
                        break;
                    if(Utils.getVarBitValue(Varbits.NMZ_ABSORPTION.getId()) < Vars.get().drinkAbsorptionUpTo)
                        drinkPotion(Const.ABSORPTION_POTION);
                    else
                        break;
                }
            }
            Vars.get().drinkAbsorptionUpTo = General.random(400, 800);
            Vars.get().drinkAbsorptionAt = General.random(50, 150);
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
                (Vars.get().usingPrayerPots && shouldDrinkPrayerPot()) ||
                (Vars.get().usingOverloadPots && !Vars.get().overloadTimer.isRunning()) ||
                (shouldDrinkAbsorption() && Vars.get().usingAbsorptions) ||
                (Vars.get().usingAbsorptions &&
                        Combat.getHP() >= Vars.get().eatRockCakeAt);
    }

    @Override
    public void execute() {
        Log.info("[Debug]: DrinkPotion validated: " +
                (Vars.get().usingOverloadPots && !Vars.get().overloadTimer.isRunning()));
        drinkPrayerPotion();

        if (Vars.get().usingAbsorptions)
            drinkAbsorption();

        drinkPotion(Const.SUPER_RANGING_POTION, Skills.SKILLS.RANGED);
        drinkPotion(ItemID.SUPER_COMBAT_POTION, Skills.SKILLS.STRENGTH);

        if (Vars.get().usingOverloadPots && !Vars.get().overloadTimer.isRunning())
            drinkOverload();
        rockCake();

       /* if (Vars.get().usingPrayerPots) {
            General.println("[Debug]: Drinking Prayer Potion");
            General.sleep(250, 2800);
            if (PrayerUtil.drinkPrayerPotion()) {
                Vars.get().drinkPrayAtPercentage = General.randomSD(6, 72, 43, 18);
                General.println("[Debug]: Drinking Next Prayer Potion at: " + getAbsolutePrayerDrinkAt());
            }
        }*/
    }
}