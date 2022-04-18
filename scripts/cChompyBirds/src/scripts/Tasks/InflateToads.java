package scripts.Tasks;


import org.tribot.script.sdk.Inventory;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.Npc;
import scripts.Data.Const;
import scripts.ItemID;
import scripts.Timer;
import scripts.Utils;

import java.util.Optional;

public class InflateToads implements Task {

    @Override
    public String toString() {
        return "Inflating toads";
    }

    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return Inventory.getCount(ItemID.BLOATED_TOAD) < 3
                && Query.npcs().idEquals(Const.CHOMPY_ID).toList().size() == 0;
    }

    @Override
    public void execute() {
        inflateToad();
    }

    public void inflateBellows() {
        if (Inventory.getCount(Const.INFLATED_BELLOWS) < 1) {
            Log.info("Inflating bellows");
            if (Utils.clickObj("Swamp bubbles", "Suck") &&
                    Waiting.waitUntil(7500, 350,
                    () -> Inventory.getCount(Const.INFLATED_BELLOWS) > 0))
                Utils.idlePredictableAction();
        }
    }

    public void inflateToad() {
        inflateBellows();

        if (Inventory.getCount(Const.INFLATED_BELLOWS) > 0 && Inventory.getCount(Const.BLOATED_TOAD) < 3) {
           // Log.info("Inflating Toads");

            Optional<Npc> toad = Query.npcs().idEquals(Const.TOAD_ID).findBestInteractable();
            int invToad = Inventory.getCount(Const.BLOATED_TOAD);
            if (toad.map(t -> t.interact("Inflate")).orElse(false)) {
                if (Waiting.waitUntil(8000, 150,
                        () -> Inventory.getCount(Const.BLOATED_TOAD) > invToad)) {
                    Utils.idlePredictableAction();
                }
            }

        }
    }

}

