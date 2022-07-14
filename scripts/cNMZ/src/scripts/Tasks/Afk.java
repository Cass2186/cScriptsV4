package scripts.Tasks;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.Combat;
import org.tribot.api2007.Game;
import org.tribot.api2007.GroundItems;
import org.tribot.api2007.Prayer;
import org.tribot.api2007.types.RSGroundItem;
import org.tribot.script.sdk.Inventory;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.query.Query;
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
        return Timing.waitCondition(() -> {

            if (Vars.get().usingPrayerPots)
                Prayer.enable(Prayer.PRAYERS.PROTECT_FROM_MELEE);

            Waiting.waitNormal(2000, 400);

            if (abc2Actions)
                AntiBan.timedActions();

            if (mouseOffScreen && Mouse.isInBounds())
                Mouse.leaveGame();


            return (Vars.get().usingPrayerPots && DrinkPotion.shouldDrinkPrayerPot()) ||
                    (Vars.get().usingOverloadPots && !Vars.get().overloadTimer.isRunning()) ||
                    (DrinkPotion.shouldDrinkAbsorption() && Vars.get().usingAbsorptions) ||
                    (Combat.getHP() >= Vars.get().eatRockCakeAt && Query.inventory()
                            .nameContains("Absorption").isAny());

        }, General.random(300000, 460000)); //5-7.6 min
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
        if (Vars.get().usingPrayerPots && !Prayer.isPrayerEnabled(Prayer.PRAYERS.PROTECT_FROM_MELEE)) {
            Prayer.enable(Prayer.PRAYERS.PROTECT_FROM_MELEE);
        }
        lootLongBone();
        Vars.get().currentTime = System.currentTimeMillis();

        if (chance < 30) {
            Log.info("[AFK]: AFK [w/ ABC2 Actions]");
            waitCond(true, true);

        } else {
            AntiBan.timedActions();
            Log.info("[AFK]: AFKing");
            waitCond(false, true);
            Log.info("[Debug]: After Wait condition");
            if (Combat.isUnderAttack() && Combat.getHPRatio() > Vars.get().eatAt) {
                Log.info("Returning");
                return; // still in combat so we skip abc2 sleep
            }
        }
        DrinkPotion.determineSleep();
       // Utils.abc2ReactionSleep(Vars.get().currentTime, false);
       // Mouse.pickupMouse();
       //
    }
}
