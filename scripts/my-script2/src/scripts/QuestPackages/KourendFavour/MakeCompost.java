package scripts.QuestPackages.KourendFavour;

import org.tribot.api.General;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSItem;
import org.tribot.script.sdk.ChatScreen;
import org.tribot.script.sdk.Quest;
import scripts.*;
import scripts.QuestPackages.DeathsOffice.DeathsOffice;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.ItemRequirement;
import scripts.Requirements.Requirement;
import scripts.Tasks.Priority;

import java.util.List;

public class MakeCompost implements QuestTask {



    private static MakeCompost quest;

    public static MakeCompost get() {
        return quest == null ? quest = new MakeCompost() : quest;
    }

    int COMPOST = 6032;
    int SALTPETRE = 13421;

    public void mixCompostAndSaltpierre() {
        bank();
        cQuesterV2.status = "Making compost";
        if (Utils.useItemOnItem(COMPOST, SALTPETRE))
            Timer.slowWaitCondition(() -> Inventory.find(COMPOST).length < 1
                    || ChatScreen.isOpen(), 32000, 36000);
    }

    public void bank() {
        if (Inventory.find(COMPOST).length < 1 || Inventory.find(SALTPETRE).length < 1) {
            cQuesterV2.status = "Banking for compost";
            BankManager.open(true);
            BankManager.depositAll(false);
            if (Banking.find(SALTPETRE).length < 1 || Banking.find(COMPOST).length < 1) {
                General.println("[Banking]: Missing Saltpetre or compost, ending script");
                cQuesterV2.taskList.remove(this);
                return;
            }
            BankManager.withdraw(14, true, COMPOST);
            BankManager.withdraw(14, true, SALTPETRE);
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

    @Override
    public List<Requirement> getGeneralRequirements() {
        return null;
    }

    @Override
    public List<ItemRequirement> getBuyList() {
        return null;
    }

    @Override
    public boolean isComplete() {
        return false;
    }
}
