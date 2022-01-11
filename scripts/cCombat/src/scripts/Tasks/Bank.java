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
import scripts.ItemId;
import scripts.PathingUtil;
import scripts.Utils;

public class Bank implements Task {

    public void bank() {
        BankManager.open(true);
        BankManager.depositAllExcept(ItemId.OPEN_LOOTING_BAG);
        RSItem[] lootBag = org.tribot.api2007.Inventory.find(ItemId.OPEN_LOOTING_BAG);
        if (lootBag.length > 0 && lootBag[0].click("View")) {
            Utils.shortSleep();
            RSInterface dep = Interfaces.findWhereAction("Deposit loot");
            if (dep != null && dep.click()){
                Utils.microSleep();
                RSInterface dis = Interfaces.findWhereAction("Dismiss");
                if (dis != null && dis.click()){
                    Utils.microSleep();
                }
            }
        }
        BankManager.withdraw(5, true, ItemId.LOBSTER);
        BankManager.withdrawArray(ItemId.BURNING_AMULET, 1);
        BankManager.withdraw(1, true, ItemId.STAMINA_POTION[2]);
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
        Log.log("Banking");
        if (Areas.WHOLE_WILDERNESS.contains(Player.getPosition())) {
            PathingUtil.walkToArea(Areas.lavaTeleArea);
        }
        PathingUtil.walkToTile(RunescapeBank.EDGEVILLE.getPosition());
        bank();
    }
}
