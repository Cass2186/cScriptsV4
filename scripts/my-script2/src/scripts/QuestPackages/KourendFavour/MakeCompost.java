package scripts.QuestPackages.KourendFavour;

import org.tribot.api.General;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSItem;
import scripts.*;
import scripts.QuestPackages.DeathsOffice.DeathsOffice;
import scripts.QuestSteps.QuestTask;
import scripts.Tasks.Priority;

public class MakeCompost implements QuestTask {



    private static MakeCompost quest;

    public static MakeCompost get() {
        return quest == null ? quest = new MakeCompost() : quest;
    }

    int COMPOST = 6032;
    int SALTPETRE = 13421;

    public void mixCompostAndSaltpierre() {
        cQuesterV2.status = "Making compost";
        bank();
        RSItem[] salt = Inventory.find(SALTPETRE);
        RSItem[] comp = Inventory.find(COMPOST);
        if (Utils.useItemOnItem(COMPOST, SALTPETRE))
            Timer.slowWaitCondition(() -> Inventory.find(COMPOST).length < 1, 28000, 36000);
    }

    public void bank() {
        if (Inventory.find(COMPOST).length < 1 || Inventory.find(SALTPETRE).length < 1) {
            BankManager.open(true);
            BankManager.depositAll(false);
            if (Banking.find(SALTPETRE).length < 1 || Banking.find(COMPOST).length < 1) {
                General.println("[Banking]: Missing Saltpetre or compost, ending script");
                cQuesterV2.taskList.remove(this);
                return;
            }
            BankManager.withdraw(14, true, SALTPETRE);
            BankManager.withdraw(14, true, COMPOST);
            BankManager.close(true);
        }
    }


    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return cQuesterV2.taskList.get(0).equals(this);
    }

    @Override
    public void execute() {
        mixCompostAndSaltpierre();
    }

    @Override
    public String questName() {
        return "Hosidius Favour";
    }

    @Override
    public boolean checkRequirements() {
        return true;
    }
}
