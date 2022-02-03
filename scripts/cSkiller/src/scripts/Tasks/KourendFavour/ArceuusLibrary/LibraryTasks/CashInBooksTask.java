package scripts.Tasks.KourendFavour.ArceuusLibrary.LibraryTasks;


import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSItem;
import org.tribot.script.sdk.Waiting;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.InterfaceUtil;
import scripts.Tasks.KourendFavour.ArceuusLibrary.Constants;
import scripts.Tasks.KourendFavour.ArceuusLibrary.State;

import scripts.Utils;

public class CashInBooksTask implements Task {

    @Override
    public scripts.API.Priority priority() {
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
    public String taskName() {
        return "Arceuus Library";
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
                Interfaces.get(219, 1, 1) != null)) {
          //  Interfaces.get(219, 1, 1).click();
            return InterfaceUtil.clickInterfaceText(219, "Continue");
        }
        return false;
    }


}
