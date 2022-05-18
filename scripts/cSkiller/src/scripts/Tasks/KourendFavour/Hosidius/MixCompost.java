package scripts.Tasks.KourendFavour.Hosidius;

import org.tribot.api.General;
import org.tribot.api2007.types.RSItem;
import org.tribot.script.sdk.Inventory;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.cache.BankCache;
import org.tribot.script.sdk.query.Query;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.BankManager;
import scripts.Data.Vars;
import scripts.ItemID;
import scripts.Timer;
import scripts.Utils;

public class MixCompost implements Task {




    public void mixCompostAndSaltpetre() {
        Log.info("Making compost");
        if (Utils.useItemOnItem(ItemID.COMPOST, ItemID.SALTPETRE))
            Waiting.waitUntil(35000, 750, () -> !Inventory.contains(ItemID.COMPOST));
    }


    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return Vars.get().hosaFavour &&
                Inventory.contains(ItemID.COMPOST) && Inventory.contains(ItemID.SALTPETRE);
    }

    @Override
    public void execute() {
        mixCompostAndSaltpetre();
    }

    @Override
    public String toString() {
        return "Mixing";
    }

    @Override
    public String taskName() {
        return "Hosidius Favour";
    }
}
