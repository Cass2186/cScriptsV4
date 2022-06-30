package scripts.Tasks.Magic.ChargeOrbs;

import org.tribot.script.sdk.Inventory;
import org.tribot.script.sdk.types.WorldTile;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.ItemID;
import scripts.PathingUtil;

public class GoToAirObilisk implements Task {

    public static WorldTile standTile = new WorldTile(3088,3570,0);

    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        return Vars.get().currentTask != null && Vars.get().currentTask.equals(SkillTasks.MAGIC) &&
                Vars.get().makeOrbs &&
                GoToAirObilisk.standTile.distance()  > 2
                && Inventory.contains(ItemID.COSMIC_RUNE);
    }

    @Override
    public void execute() {
        PathingUtil.walkToTile(standTile);
    }

    @Override
    public String toString() {
        return "Going to make Orbs";
    }

    @Override
    public String taskName() {
        return "Magic - Orbs";
    }
}
