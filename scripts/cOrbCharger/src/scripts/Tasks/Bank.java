package scripts.Tasks;

import org.tribot.script.sdk.Equipment;
import org.tribot.script.sdk.tasks.Amount;
import org.tribot.script.sdk.tasks.BankTask;
import org.tribot.script.sdk.tasks.EquipmentReq;
import scripts.BankManager;
import scripts.Data.Const;
import scripts.ItemID;

public class Bank implements Task{


    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return false;
    }

    @Override
    public void execute() {
        BankManager.open(true);
        // drink stam
        Const.bankTask.execute();
        BankManager.close(true);
    }

    @Override
    public String toString() {
        return "Banking";
    }
}
