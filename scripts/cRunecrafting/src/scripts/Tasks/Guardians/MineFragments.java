package scripts.Tasks.Guardians;

import org.tribot.script.sdk.Inventory;
import scripts.Data.Vars;
import scripts.ItemID;
import scripts.Tasks.Guardians.GuardiansData.GuardiansVars;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;

public class MineFragments implements Task {

    @Override
    public String toString() {
        return "Mining";
    }

    @Override
    public Priority priority() {
        return null;
    }

    @Override
    public boolean validate() {
        return Vars.get().playingGuardians
                && Inventory.getCount(ItemID.GUARDIAN_ESSENCE) < 2 //todo check number
                && Inventory.getCount(ItemID.GUARDIAN_FRAGMENTS) <
                GuardiansVars.get().fragmentsToMine; //todo check number
    }

    @Override
    public void execute() {

    }
}
