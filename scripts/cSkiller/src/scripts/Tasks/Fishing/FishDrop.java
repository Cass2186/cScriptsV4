package scripts.Tasks.Fishing;

import lombok.val;
import org.tribot.api.General;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSItem;
import org.tribot.script.sdk.GameState;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.antiban.AntibanProperties;
import org.tribot.script.sdk.input.Keyboard;
import org.tribot.script.sdk.interfaces.Item;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.InventoryItem;
import org.tribot.script.sdk.util.Retry;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.EntitySelector.Entities;
import scripts.EntitySelector.finders.prefabs.ItemEntity;
import scripts.ItemUtil;

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
        List<InventoryItem> leap = Query.inventory().nameContains("leaping").toList();

        setShiftDroppingPref();
        int sp = Mouse.getSpeed();
        Mouse.setSpeed(300);
       // if (raw.size() > 0)
            ItemUtil.drop(raw);
       // if (leap.size() > 0)
        ItemUtil.drop(leap);

        Mouse.setSpeed(sp);
    }

    @Override
    public String taskName() {
        return "Fishing";
    }
}
