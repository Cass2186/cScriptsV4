package scripts.Tasks;

import dax.api_lib.models.RunescapeBank;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSInterface;
import org.tribot.api2007.types.RSItem;
import org.tribot.script.sdk.Inventory;
import org.tribot.script.sdk.Log;
import scripts.BankManager;
import scripts.Data.Areas;
import scripts.Data.Vars;
import scripts.ItemID;
import scripts.PathingUtil;
import scripts.Utils;

public class Bank implements Task {

    public void bank() {
        BankManager.open(true);
        BankManager.depositAllExcept(ItemID.LOOTING_BAG_22586);
        RSItem[] lootBag = org.tribot.api2007.Inventory.find(ItemID.LOOTING_BAG_22586);
        if (lootBag.length > 0 && lootBag[0].click("View")) {
            Utils.shortSleep();
            RSInterface dep = Interfaces.findWhereAction("Deposit loot");
            if (dep != null && dep.click()) {
                Utils.microSleep();
                RSInterface dis = Interfaces.findWhereAction("Dismiss");
                if (dis != null && dis.click()) {
                    Utils.microSleep();
                }
            }
        }
        BankManager.withdraw(5, true, ItemID.LOBSTER);
        BankManager.withdrawArray(ItemID.BURNING_AMULET, 1);
        BankManager.withdraw(1, true, ItemID.STAMINA_POTION[2]);
        BankManager.close(true);
    }


    public void undeadDruidBank() {
        BankManager.open(true);
        BankManager.depositAll(true);
        BankManager.withdrawArray(ItemID.SKILLS_NECKLACE, 1);
        BankManager.withdrawArray(ItemID.STAMINA_POTION, 1);
        BankManager.withdrawArray(ItemID.RANGING_POTION, 3);
        BankManager.withdraw(6, true, ItemID.SHARK);
        BankManager.withdraw(1, true, ItemID.KNIFE);
        BankManager.close(true);
    }

    @Override
    public Priority priority() {
        return Priority.HIGHEST;
    }

    @Override
    public boolean validate() {
        return Inventory.isFull();
    }

    @Override
    public void execute() {
        Log.log("[Bank]: Banking");
        if (Areas.WHOLE_WILDERNESS.contains(Player.getPosition())) {
            PathingUtil.walkToArea(Areas.lavaTeleArea);
            PathingUtil.walkToTile(RunescapeBank.EDGEVILLE.getPosition());
        }
        if (Vars.get().killingUndeadDruids) {
            undeadDruidBank();
        } else {
            bank();
        }
    }
}
