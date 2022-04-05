package scripts.Tasks;

import org.tribot.script.sdk.Inventory;
import org.tribot.script.sdk.MyPlayer;
import org.tribot.script.sdk.types.GameObject;
import scripts.Data.Const;
import scripts.Data.Vars;
import scripts.FarmAPI.FarmUtils;
import scripts.Timer;
import scripts.Utils;

import java.util.Optional;

public class HarvestPatch implements Task {

    private void harvestHerbs(int patchId) {
        Optional<GameObject> patch = FarmUtils.getNearbyReadyHerbs(patchId);
        if (patch.isPresent()) {
            FarmUtils.dropItemsIfNeeded();

            if (patch.map(p -> p.interact("Take")).orElse(false)) {
                if (Timer.waitCondition(() -> MyPlayer.getAnimation() != -1, 6000, 9000))
                    Timer.waitCondition(() -> MyPlayer.getAnimation() == -1 ||
                            Inventory.isFull(), 20000, 40000);
            }
        }
    }

    @Override
    public Priority priority() {
        return null;
    }

    @Override
    public boolean validate() {
        return false;
    }

    @Override
    public void execute() {

    }

    @Override
    public String toString() {
        return "Picking Herbs";
    }
}
