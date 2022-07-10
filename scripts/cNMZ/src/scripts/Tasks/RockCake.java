package scripts.Tasks;

import org.tribot.api.General;
import org.tribot.api2007.Game;
import org.tribot.script.sdk.GameState;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.MyPlayer;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.InventoryItem;
import scripts.ItemID;
import scripts.NmzData.Const;
import scripts.NmzData.Vars;

import java.util.Optional;

public class RockCake implements Task {


    public static void rockCake() {
        Optional<InventoryItem> rockCake = Query.inventory()
                .idEquals(Const.ROCK_CAKE)
                .findClosestToMouse();
        Optional<InventoryItem> locatorOrb = Query.inventory()
                .idEquals(ItemID.LOCATOR_ORB)
                .findClosestToMouse();
        if ((rockCake.isPresent() || locatorOrb.isPresent()) &&
                MyPlayer.getCurrentHealth() > 1 //&& Vars.get().overloadTimer.isRunning()
                && MyPlayer.getCurrentHealth() >= Vars.get().eatRockCakeAt) {

           // if (MyPlayer.getMaxHealth() != MyPlayer.getCurrentHealth()) //will skip the first it does absorptions
              //  determineSleep();

            //drinkOverload();
            if (MyPlayer.getCurrentHealth() > 1 ){//&&
                  //  ((Vars.get().usingOverloadPots && Vars.get().overloadTimer.isRunning()) ||
                  //          Vars.get().usingAbsorptions)) {

                for (int i = 0; i < 50; i++) {
                    Log.info("Eating rock cake");
                    General.sleep(50, 80);

                    if (!GameState.isInInstance() || MyPlayer.getCurrentHealth() == 1)
                        break;

                    if (locatorOrb.map(l -> l.click("Feel")).orElse(false)) {
                        Waiting.waitNormal(325, 60);
                    } else if (rockCake.map(r -> r.click("Guzzle")).orElse(false)) {
                        Waiting.waitNormal(325, 60);
                    }
                }
            } else if (Vars.get().usingOverloadPots && !Vars.get().overloadTimer.isRunning()) {
                Log.warn("Overload timer isn't running");
            }

            Vars.get().eatRockCakeAt = Vars.get().getEatRockCakeAt();
            Log.info("Next eating rock cake at: " + Vars.get().eatRockCakeAt);
        } else if (Vars.get().usingAbsorptions) {
            Log.warn("Error with rock caking: do we have one? " + rockCake.isPresent());
            Log.warn("Is HP > eatRockCake at: " + (MyPlayer.getCurrentHealth() >= Vars.get().eatRockCakeAt));
        }
    }


    @Override
    public String toString() {
        return "Eating Rockcake";
    }

    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        return Game.isInInstance() && (Vars.get().usingLocatorOrb &&
                MyPlayer.getCurrentHealth() >= Vars.get().eatRockCakeAt);
    }

    @Override
    public void execute() {
        rockCake();
    }
}
