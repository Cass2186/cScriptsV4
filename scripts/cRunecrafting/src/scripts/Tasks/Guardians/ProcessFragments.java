package scripts.Tasks.Guardians;

import org.tribot.script.sdk.Inventory;
import org.tribot.script.sdk.query.Query;
import scripts.Data.Vars;
import scripts.ItemID;
import scripts.Tasks.Guardians.GuardiansData.GuardiansVars;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;

public class ProcessFragments implements Task {


    @Override
    public String toString() {
        return "Chipping fragments";
    }


    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return Vars.get().playingGuardians
                && Inventory.getCount(ItemID.GUARDIAN_ESSENCE) < 2 //todo check number
                && Inventory.getCount(ItemID.GUARDIAN_FRAGMENTS) >=
                GuardiansVars.get().fragmentsToMine; //todo check number
    }

    @Override
    public void execute() {

    }

    private boolean clickBench(){
        return Query.gameObjects().actionContains("Work-at")
                .nameContains("Workbench")
                .findClosestByPathDistance()
                .map(w->w.interact("Work-at")).orElse(false);
    }
}
