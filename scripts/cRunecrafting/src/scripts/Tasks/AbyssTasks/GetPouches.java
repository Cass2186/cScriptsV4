package scripts.Tasks.AbyssTasks;

import dax.walker.utils.AccurateMouse;
import dax.walker.utils.camera.DaxCamera;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSGroundItem;
import org.tribot.api2007.types.RSNPC;
import scripts.*;
import scripts.Data.Const;
import scripts.Data.Vars;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;

import java.awt.*;

public class GetPouches implements Task {

    @Override
    public String toString() {
        return "Getting Pouches";
    }

    @Override
    public Priority priority() {
        return Priority.HIGHEST;
    }

    @Override
    public boolean validate() {
        return Vars.get().collectPouches;
    }


    ;

    @Override
    public void execute() {
        //GoToAbyss.teleportMage();
        killLeeches();
        lootPouches();
        General.sleep(100);
      //  Main.timeoutTimer.reset();
    }


    int drinkPPotAt = General.random(5, 15);

    public void killLeeches() {
        RSNPC[] targs = NPCs.findNearest("Abyssal leech");
        if (targs.length == 0) {
            General.println("failed to check targ: null");
            return;
        }
        if (Prayer.getPrayerPoints() < drinkPPotAt) {
            EatUtil.drinkPotion(ItemID.PRAYER_POTION);
            Utils.microSleep();
            drinkPPotAt = General.random(5, 15);
            General.println("[Debug]: Next drinking prayer potion at: " + drinkPPotAt);
            if (Inventory.find(ItemID.PRAYER_POTION).length == 0) {
                General.println("Need to bank");
                BankManager.open(true);
            }
        }

        if (!Prayer.isPrayerEnabled(Prayer.PRAYERS.PROTECT_FROM_MELEE) &&
                Prayer.getPrayerPoints() > 0)
            org.tribot.script.sdk.Prayer.enableAll(org.tribot.script.sdk.Prayer.PROTECT_FROM_MELEE);

        if (!targs[0].isClickable() && !targs[0].isInteractingWithMe())
            DaxCamera.focus(targs[0]);

        if (!targs[0].isInteractingWithMe() && AccurateMouse.click(targs[0], "Attack"))
            Timer.waitCondition(() -> targs[0].getHealthPercent() == 0 || targs[0].isInteractingWithMe(), 5000, 9000);

        lootPouches();

        if (Player.getRSPlayer().getInteractingCharacter() !=null && !Player.getRSPlayer().getInteractingCharacter().equals(targs[0])) {
            General.println("[Debug]: Attacking leech");
            if (AccurateMouse.click(targs[0], "Attack"))
                Timer.waitCondition(() -> targs[0].getHealthPercent() == 0 || targs[0].isInteractingWithMe(), 5000, 9000);
        }

        if (targs[0].isInteractingWithMe()) {
            if (CombatUtil.waitUntilOutOfCombat(General.random(40, 70)))
                Timer.waitCondition(() -> targs[0].getHealthPercent() == 0, 2000, 4000);
            General.println("[Debug]: Sleeping");
            General.sleep(General.random(500, 2500));
        }
    }

    public void lootPouches() {

        RSGroundItem[] g = GroundItems.find(Const.ALL_POUCHES);
        if (g.length > 0) {
            General.println("[Looting]: Found ground pouch", Color.RED);
            Utils.clickGroundItem(g[0].getID());
        }
    }

}
