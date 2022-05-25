package scripts.Tasks;

import org.tribot.api2007.Inventory;
import org.tribot.script.sdk.EnterInputScreen;
import org.tribot.script.sdk.GameState;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.GameObject;
import scripts.ItemID;
import scripts.NmzData.Vars;
import scripts.Requirements.InventoryRequirement;
import scripts.Requirements.ItemReq;
import scripts.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class InventorySetup implements Task {

    public static InventoryRequirement getSuperCombatInv() {
        InventoryRequirement req = new InventoryRequirement(new ArrayList<>(
                List.of(new ItemReq(ItemID.SUPER_COMBAT_POTION4, 8, 6))));

        if (Vars.get().usingAbsorptions)
            req.add(new ItemReq(ItemID.ABSORPTION_4, (Vars.get().absorptionDosesToGet / 4)));
        else
            req.add(new ItemReq(ItemID.PRAYER_POTION_4, 0, 10));

        // get the HP Draining Item if needed
        if (Vars.get().usingLocatorOrb)
            req.add(new ItemReq(ItemID.LOCATOR_ORB, 1));
        else if (Vars.get().usingAbsorptions)
            req.add(new ItemReq(ItemID.ROCK_CAKE, 1));


        return req;
    }


    public static InventoryRequirement getOverloadInvReq() {
        InventoryRequirement req = new InventoryRequirement(new ArrayList<>(
                List.of(
                        new ItemReq(ItemID.ABSORPTION_4, (Vars.get().absorptionDosesToGet / 4)),
                        new ItemReq(ItemID.OVERLOAD_4, (Vars.get().overloadDosesToGet / 4))
                ))
        );
        if (Vars.get().usingLocatorOrb) {
            req.add(new ItemReq(ItemID.LOCATOR_ORB, 1));
        } else {
            req.add(new ItemReq(ItemID.ROCK_CAKE, 1));
        }
        return req;
    }

    private boolean clickBarrel(String barrel) {
        return Query.gameObjects().actionContains("Take")
                .nameContains(barrel)
                .findClosest()
                .map(c -> c.interact("Take")).orElse(false);
    }

    private boolean getAbsorptions(int amount) {
        Log.info("Getting Absorptions x" + amount);
        if (clickBarrel("Absorption potion") &&
                Waiting.waitUntil(7500, 560, () -> EnterInputScreen.isOpen())) {
            return EnterInputScreen.enter(amount);
        }
        return false;
    }

    private boolean getOverloads(int amount) {
        Log.info("Getting Overloads x" + amount);
        if (clickBarrel("Overload potion") &&
                Waiting.waitUntil(7500, 560, () -> EnterInputScreen.isOpen())) {
            return EnterInputScreen.enter(amount);
        }
        return false;
    }

    private int getAbsorptionDosestoGet() {
        int fours = Inventory.getCount(ItemID.ABSORPTION_4) * 4;
        int three = Inventory.getCount(ItemID.ABSORPTION_3) * 3;
        int twos = Inventory.getCount(ItemID.ABSORPTION_2) * 2;
        int ones = Inventory.getCount(ItemID.ABSORPTION_1);
        return Vars.get().absorptionDosesToGet - (fours + three + twos + ones);
    }

    private int getOverloadDosestoGet() {
        int fours = Inventory.getCount(ItemID.OVERLOAD_4) * 4;
        int three = Inventory.getCount(ItemID.OVERLOAD_3) * 3;
        int twos = Inventory.getCount(ItemID.OVERLOAD_2) * 2;
        int ones = Inventory.getCount(ItemID.OVERLOAD_1);
        return Vars.get().overloadDosesToGet - (fours + three + twos + ones);
    }

    @Override
    public String toString() {
        return "Setting up Inventory";
    }


    @Override
    public Priority priority() {
        return Priority.HIGH;
    }

    @Override
    public boolean validate() {
        return !GameState.isInInstance() && Vars.get().invRequirement.map(i -> !i.check()).orElse(false);
    }

    @Override
    public void execute() {
        Log.info("Setting up inventory");
        if (getAbsorptionDosestoGet() > 0) {
            if (getAbsorptions(getAbsorptionDosestoGet()))
                Utils.idleNormalAction(true);
        }
        if (getOverloadDosestoGet() > 0) {
            if (getOverloads(getOverloadDosestoGet()))
                Utils.idleNormalAction(true);
        }
        Utils.idleNormalAction(true);
        DrinkPotion.determinePotion(); // called to reset what we're using
    }
}
