package scripts.Tasks;

import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSObject;
import scripts.Data.MiningConst;
import scripts.EntitySelector.Entities;
import scripts.EntitySelector.finders.prefabs.ObjectEntity;
import scripts.PathingUtil;
import scripts.Timer;
import scripts.Utils;

public class DepositOre implements Task {


    @Override
    public String toString() {
        return "Depositing Ore";
    }

    @Override
    public Priority priority() {
        return Priority.HIGH;
    }

    @Override
    public boolean validate() {
        return Inventory.isFull()
                && Inventory.find(MiningConst.PAY_DIRT).length > 0;
    }

    @Override
    public void execute() {
        PathingUtil.walkToTile(MiningConst.MLM_DEPOSIT_BOX_TILE, 3, true);

        RSObject hopper = Entities.find(ObjectEntity::new)
                .actionsContains("Deposit")
                .nameContains("Hopper")
                //   .nameNotContains("Rockfall")
                .getFirstResult();

        if (hopper != null && Utils.clickObject(hopper, "Deposit", false))
            Timer.waitCondition(()-> !Inventory.isFull(), 6700,9000);



    }
}
