package scripts.Tasks.Magic.ChargeOrbs;

import org.tribot.script.sdk.Inventory;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.ItemID;

public class OrbBank implements Task {
    @Override
    public Priority priority() {
        return Priority.HIGH;
    }

    @Override
    public boolean validate() {
        return Vars.get().currentTask != null
                && Vars.get().currentTask.equals(SkillTasks.MAGIC)
                && Vars.get().makeOrbs
                && !Inventory.contains(ItemID.COSMIC_RUNE, ItemID.UNPOWERED_ORB);
    }

    @Override
    public void execute() {

    }

    @Override
    public String toString() {
        return "Banking for Orbs";
    }

    @Override
    public String taskName() {
        return "Magic - Orbs";
    }
}
