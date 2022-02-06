package scripts.QuestPackages.KourendFavour.ArceuusLibrary.LibraryTasks;


import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSItem;
import org.tribot.script.sdk.Waiting;

import scripts.InterfaceUtil;
import scripts.QuestPackages.KourendFavour.ArceuusLibrary.ArceuusLibrary;
import scripts.QuestPackages.KourendFavour.ArceuusLibrary.Constants;
import scripts.QuestPackages.KourendFavour.ArceuusLibrary.State;
import scripts.QuestSteps.QuestTask;

import scripts.Requirements.ItemRequirement;
import scripts.Requirements.Requirement;
import scripts.Tasks.Priority;
import scripts.Utils;

import java.util.List;

public class CashInBooksTask implements QuestTask {
    private static CashInBooksTask quest;

    public static CashInBooksTask get() {
        return quest == null ? quest = new CashInBooksTask() : quest;
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
        Inventory.findList(Constants.Items.ARCANE_KNOWLEDGE).stream().allMatch(this::cashInBook);
    }

    @Override
    public String questName() {
        return null;
    }

    @Override
    public boolean checkRequirements() {
        return false;
    }


    @Override
    public String toString() {
        return "Cashing in books for xp";
    }


    public boolean cashInBook(RSItem book) {
        State.get().setStatus("Cashing in books");
        int currentXp = Skills.getXP(Skills.SKILLS.RUNECRAFTING);
        if (Utils.clickInventoryItem(book.getID())
                && Waiting.waitUntil(2000, () ->
                Interfaces.get(219, 1, 2) != null)) {
            return InterfaceUtil.clickInterfaceText(219, "Continue");
        }
        return false;
    }
    @Override
    public List<Requirement> getGeneralRequirements() {
        return null;
    }

    @Override
    public List<ItemRequirement> getBuyList() {
        return null;
    }

}
