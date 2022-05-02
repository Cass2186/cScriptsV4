package scripts.Tasks.Fishing;

import org.tribot.api.General;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Options;
import org.tribot.api2007.types.RSItem;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.InventoryItem;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.EntitySelector.Entities;
import scripts.EntitySelector.finders.prefabs.ItemEntity;

import java.util.List;

public class FishDrop implements Task {


    public void setShiftDroppingPref() {
        if (!Options.isShiftClickDropEnabled()) {
            General.println("[Debug]: Setting shift click drop - enabled");
            Options.setShiftClickDropEnabled(true);
            Interfaces.closeAll();
        }
        if (!Inventory.getDroppingPattern().equals(Inventory.DROPPING_PATTERN.TOP_TO_BOTTOM)) {
            Inventory.setDroppingPattern(Inventory.DROPPING_PATTERN.TOP_TO_BOTTOM);
        }
    }

    @Override
    public String toString() {
        return "Dropping fish";
    }


    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        return Vars.get().currentTask != null && Vars.get().currentTask.equals(SkillTasks.FISHING) &&
                Inventory.isFull();
    }

    @Override
    public void execute() {
        List<InventoryItem> raw = Query.inventory().nameContains("raw").toList();
        setShiftDroppingPref();
        if (raw.size() > 0)
            org.tribot.script.sdk.Inventory.drop(raw);
    }

    @Override
    public String taskName() {
        return "Fishing";
    }
}
