package scripts.Tasks;

import dax.walker.utils.AccurateMouse;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSItemDefinition;
import org.tribot.script.sdk.Log;
import scripts.*;
import scripts.NmzData.Const;
import scripts.NmzData.Vars;

public class DrinkPotion implements Task {


    public static void determinePotion() {
        if (Inventory.find(ItemId.PRAYER_POTION).length > 0) {
            Vars.get().usingPrayerPots = true;
            Vars.get().usingAbsorptions = false;
        } else if (Inventory.find(Const.ABSORPTION_POTION).length > 0) {
            Vars.get().usingPrayerPots = false;
            Vars.get().usingAbsorptions = true;
        }
        if (Inventory.find(ItemId.SUPER_COMBAT_POTION).length > 0) {
            General.println("[Debug]: Using Super combat potions? " + Vars.get().usingSuperCombat);
            Vars.get().usingSuperCombat = true;
            Vars.get().usingOverloadPots = false;
        } else if (Inventory.find(Const.OVERLOAD_POTION).length > 0) {
            Vars.get().usingOverloadPots = true;
        } else {
            Vars.get().usingOverloadPots = false;
        }
        General.println("[Debug]: Using prayer potions? " + Vars.get().usingPrayerPots);
        General.println("[Debug]: Using absorption potions? " + Vars.get().usingAbsorptions);
        General.println("[Debug]: Using overload potions? " + Vars.get().usingOverloadPots);

    }


    public boolean shouldDrinkPrayerPot() {
        int drinkAtAbsolute = getAbsolutePrayerDrinkAt();
        // General.println("[Debug]: Drinking Prayer Potion at " + Vars.get().drinkPrayAtPercentage + "% || "
        //+ "Absolute: " + drinkAtAbsolute);
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
                General.sleep(General.randomSD(300, 60));
                Vars.get().lastAction = System.currentTimeMillis();
                return true;
            }
        }
        return false;
    }

    public void drinkPrayerPotion() {
        if (Vars.get().usingPrayerPots && shouldDrinkPrayerPot()) {
            General.println("[Debug]: Drinking Prayer Potion");
            // General.sleep(250, 2800);

            General.println("[Debug]: ABC2 Sleeping for " + AntiBan.getReactionTime());
            long finTime = System.currentTimeMillis() + AntiBan.getReactionTime();
            Timer.waitCondition(() -> System.currentTimeMillis() > finTime || Prayer.getPrayerPoints() < 4,
                    AntiBan.getReactionTime());

            for (int i = 0; i < 2; i++) {
                if (PrayerUtil.drinkPrayerPotion()) {
                    Vars.get().drinkPrayAtPercentage = General.randomSD(6, 72, 43, 18);
                    General.println("[Debug]: Drinking Next Prayer Potion at: " + getAbsolutePrayerDrinkAt());
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
            Log.log("[DrinkPotion]: Drinking potion");
            drinkPotion(potionArray);
            Vars.get().add = General.random(3, 10);

        }
    }

    public long timeSinceLastAction(long lastActionTime) {
        long time = System.currentTimeMillis() - lastActionTime;
        General.println("[Debug]: Time since last action: " + Timing.msToString(time));
        return time;
    }

    public void determineSleep() {
        int divider = General.random(5, 10);
        long timeSince = timeSinceLastAction(Vars.get().lastAction);

        int sleep = (int) (timeSince / divider);
        if (shouldDrinkAbsorption()) {
            sleep = General.randomSD(2300, 450);
        } else if (sleep > 35000) {
            sleep = General.random(7000, 20000);
        } else if (sleep < 100) {
            sleep = General.random(500, 2500);
        }
        General.println("[Debug]: Sleeping for: " + sleep + "ms");
        General.sleep(sleep);
    }

    public void rockCake() {
        RSItem[] rockCake = Inventory.find(Const.ROCK_CAKE);
        if (Combat.getHP() != 1 //&& Vars.get().overloadTimer.isRunning()
                && Combat.getHP() >= Vars.get().eatRockCakeAt) {
            determineSleep();
            drinkOverload();
            while (Combat.getHP() > 1 &&
                    ((Vars.get().usingOverloadPots && Vars.get().overloadTimer.isRunning()) ||
                            Vars.get().usingAbsorptions)) {
                Log.log("Eating rock cake");
                General.sleep(50, 80);
                if (rockCake.length > 0 && AccurateMouse.click(rockCake[0], "Guzzle")) {
                    Log.log("[Debug]: Guzzled");
                    General.sleep(General.randomSD(150, 700, 350, 75));
                }
                if (Combat.getHP() == 1)
                    break;
            }

            Vars.get().eatRockCakeAt = General.randomSD(1, 10, 4, 3);
            Log.log("[Debug]: Next eating rock cake at: " + Vars.get().eatRockCakeAt);
        }
    }


    public void drinkOverload() {

        int startHp = Combat.getHP();
        if (startHp > 50) {
            General.println("Drinking overload");
            determineSleep();

            if (drinkPotion(Const.OVERLOAD_POTION)) {
                Vars.get().overloadTimer = new Timer(300000);
                General.println("Waiting for overload to finish");
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
            General.println("[Debug]: Cannot overload due to low HP");
            rockCake();
        }
    }

    public static boolean shouldDrinkAbsorption() {
        return Utils.getVarBitValue(Varbits.NMZ_ABSORPTION.value) <= Vars.get().drinkAbsorptionAt;
    }


    public void drinkAbsorption() {
        if (shouldDrinkAbsorption()) {

            // if we have >100 abs points left it will sleep, otherwise it drinks to avoid death
            if (Utils.getVarBitValue(Varbits.NMZ_ABSORPTION.value) > 150)
                determineSleep();

            General.println("[Debug]: Drinking up to: " + Vars.get().drinkAbsorptionUpTo);
            while (Utils.getVarBitValue(Varbits.NMZ_ABSORPTION.value) < Vars.get().drinkAbsorptionUpTo) {
                General.sleep(30, 60);
                drinkPotion(Const.ABSORPTION_POTION);
            }
            Vars.get().drinkAbsorptionUpTo = General.random(400, 800);
            Vars.get().drinkAbsorptionAt = General.random(50, 150);
            General.println("[Debug]: Next drinking Absorption at " + Vars.get().drinkAbsorptionAt
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
                Combat.getHP() >= Vars.get().eatRockCakeAt;
    }

    @Override
    public void execute() {
        Log.log("[Debug]: DrinkPotion validated");
        drinkPrayerPotion();

        if (Vars.get().usingAbsorptions)
            drinkAbsorption();

        drinkPotion(Const.SUPER_RANGING_POTION, Skills.SKILLS.RANGED);
        drinkPotion(ItemId.SUPER_COMBAT_POTION, Skills.SKILLS.STRENGTH);

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