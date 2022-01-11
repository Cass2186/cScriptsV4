package scripts.Tasks;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.Combat;
import org.tribot.api2007.Game;
import org.tribot.api2007.GroundItems;
import org.tribot.api2007.Prayer;
import org.tribot.api2007.types.RSGroundItem;
import org.tribot.script.sdk.Log;
import scripts.AntiBan;
import scripts.NmzData.Vars;
import scripts.Utils;

public class Afk implements Task {


    public void lootLongBone() {
        RSGroundItem[] longBoi = GroundItems.find("Long bone");
        if (longBoi.length > 0) {
            General.println("[Debug]: Looting long bone");
            Utils.clickGroundItem(10976);
        }
    }

    public boolean waitCond(Boolean abc2Actions, boolean mouseOffScreen) {
        boolean condition;
        Timing.waitCondition(() -> {
            General.sleep(1200, 2800);

            if (abc2Actions)
                AntiBan.timedActions();

            if (mouseOffScreen && Mouse.isInBounds())
                Mouse.leaveGame(true);


            return ((Vars.get().usingPrayerPots && Prayer.getPrayerPoints() < DrinkPotion.getAbsolutePrayerDrinkAt()) ||
                    (Vars.get().usingAbsorptions && ((!Vars.get().overloadTimer.isRunning()
                            || Combat.getHP() > 50 ) && Vars.get().usingOverloadPots) ||

                    (Vars.get().usingAbsorptions &&
                            (Combat.getHP() >= Vars.get().eatRockCakeAt ||
            DrinkPotion.shouldDrinkAbsorption()))));

        }, General.random(300000, 460000)); //5-7.6 min
        General.println("loop2");
        General.sleep(5000);
        if (DrinkPotion.shouldDrinkAbsorption() && !Vars.get().usingPrayerPots)
            General.println("[Debug]: Breaking due to needing to drinkAbsorption");

        return false; /*((Vars.get().usingPrayerPots && Prayer.getPrayerPoints() < DrinkPotion.getAbsolutePrayerDrinkAt()) ||
                (Vars.get().usingAbsorptions && (!Vars.get().overloadTimer.isRunning()
                        || Combat.getHP() > 50)) ||

                (Vars.get().usingAbsorptions &&
                        (Combat.getHP() >= Vars.get().eatRockCakeAt || DrinkPotion.shouldDrinkAbsorption())));*/
    }

    @Override
    public String toString() {
        return "Afk";
    }


    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return Game.isInInstance();
    }

    @Override
    public void execute() {
        int chance = General.random(0, 100);
        if (Vars.get().usingPrayerPots && !Prayer.isPrayerEnabled(Prayer.PRAYERS.PROTECT_FROM_MELEE)){
            Prayer.enable(Prayer.PRAYERS.PROTECT_FROM_MELEE);
        }

        Vars.get().currentTime = System.currentTimeMillis();

        if (chance < 30) {
            Log.log("[AFK]: AFK [w/ ABC2 Actions]");
            waitCond(true, true);

        } else {
            AntiBan.timedActions();
            Log.log("[AFK]: AFKing");
            waitCond(false, true);
            Log.log("[Debug]: After Wait condition");
            if (Combat.isUnderAttack() && Combat.getHPRatio() > Vars.get().eatAt) {
                Log.log("[Debug]: Returning");
                return; // still in combat so we skip abc2 sleep
            }
        }
        Utils.abc2ReactionSleep(Vars.get().currentTime, false);
        Mouse.pickupMouse();
       lootLongBone();
    }
}
