package scripts.Tasks.Guardians;

import org.tribot.script.sdk.Inventory;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.GameObject;
import scripts.Data.Vars;
import scripts.ItemID;
import scripts.RcApi.RcUtils;
import scripts.Tasks.Guardians.GuardiansData.GuardiansVars;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;
import scripts.Timer;

import java.util.Optional;

public class EnterGuardian implements Task {


    @Override
    public String toString() {
        return "Enter Guardian";
    }

    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return Vars.get().playingGuardians
                && (Inventory.getCount(ItemID.GUARDIAN_ESSENCE) > 5 //todo check number
                || (Inventory.isFull() && Inventory.getCount(ItemID.GUARDIAN_ESSENCE) > 8));

    }

    @Override
    public void execute() {

    }


    private boolean enterGuardian(String typeName) {
        Optional<GameObject> guardian = Query.gameObjects().actionContains("Enter")
                .nameContains("Guardian of")
                .nameContains(typeName)
                .findClosestByPathDistance();
        return guardian.map(g -> g.interact("Enter")).orElse(false) &&
                Timer.waitCondition(7500, 450, RcUtils::atAltar);
    }
}
