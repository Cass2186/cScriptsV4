package scripts.Tasks.Woodcutting;

import org.tribot.api2007.Inventory;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.BankManager;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.ItemID;
import scripts.Utils;

public class DropLogs implements Task {

    @Override
    public String toString(){
        return "Dropping logs";
    }

    @Override
    public Priority priority() {
        return Priority.HIGH;
    }

    @Override
    public boolean validate() {
        return Vars.get().currentTask != null &&
                Vars.get().currentTask.equals(SkillTasks.WOODCUTTING) &&
                Inventory.isFull();
    }

    @Override
    public void execute() {
        for (int i =0 ; i<3; i++) {
            Inventory.drop(ItemID.LOG_IDS);
            Utils.unselectItem();
            if (Inventory.getAll().length < 20)
                break;
        }
        if (Inventory.getAll().length > 20){
            // inventory is still relatively full, might have had stuff in it following task change
            BankManager.open(true);
            BankManager.depositAll(true);
        }
    }

    @Override
    public String taskName() {
        return "Woodcutting";
    }

}
