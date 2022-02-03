package scripts.Tasks.MiscTasks;

import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSItem;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.EquipmentItem;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.BankManager;
import scripts.Data.Vars;
import scripts.EntitySelector.Entities;
import scripts.EntitySelector.finders.prefabs.ItemEntity;
import scripts.ItemID;
import scripts.Tasks.Mining.Tasks.DepositPayDirt;
import scripts.Utils;

import java.util.Optional;

public class SwitchTask implements Task {

    public void depositPayDirt() {
        RSItem[] payDirt = Inventory.find("Pay-dirt");
        if (payDirt.length > 0) {
            DepositPayDirt dep = new DepositPayDirt();
            dep.depositOre();
        }
    }

    public static  boolean isWealthEquipped() {
        Optional<EquipmentItem> item = Query.equipment().nameContains("Ring of wealth (").stream().findAny();
        return item.isPresent();
    }

    public static boolean getAndEquipWealth() {
        if (!isWealthEquipped()) {
            RSItem[] invWealth = Entities.find(ItemEntity::new)
                    .nameContains("Ring of wealth (")
                    .getResults();
            if (invWealth.length > 0) {
                return Utils.equipItem(ItemID.RING_OF_WEALTH);

            } else {
                BankManager.withdrawArray(ItemID.RING_OF_WEALTH, 1);
                BankManager.close(true);
                return Utils.equipItem(ItemID.RING_OF_WEALTH);
            }
        }
        return isWealthEquipped();
    }

    @Override
    public Priority priority() {
        return Priority.HIGHEST;
    }

    @Override
    public boolean validate() {
        return Vars.get().switchingTasks;
    }

    @Override
    public void execute() {
        depositPayDirt();
        BankManager.open(true);
        BankManager.depositAll(true);
        BankManager.depositEquipment();
        getAndEquipWealth();
        Vars.get().switchingTasks = false;
    }

    @Override
    public String taskName() {
        return "Skill switching task - Depositing items";
    }
}
