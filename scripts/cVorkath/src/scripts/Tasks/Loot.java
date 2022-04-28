package scripts.Tasks;

import org.tribot.api2007.Game;
import org.tribot.api2007.types.RSGroundItem;
import org.tribot.script.sdk.Inventory;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.pricing.Pricing;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.GroundItem;
import org.tribot.script.sdk.types.InventoryItem;
import scripts.EntitySelector.Entities;
import scripts.EntitySelector.finders.prefabs.GroundItemEntity;
import scripts.ItemID;
import scripts.dax.tracker.DaxTracker;

import java.util.Optional;

public class Loot implements Task {

    public void getItems() {
        RSGroundItem[] items = Entities.find(GroundItemEntity::new)
                .nameNotContains("Manta ray")
                .getResults();
    }

    DaxTracker tracker;
    public static int looted;


    public boolean canLoot() {
        Optional<GroundItem> loot = Query.groundItems()
                .idNotEquals(ItemID.MANTA_RAY)
                .stream()
                .filter(i -> Pricing.lookupPrice(i.getDefinition().getUnnotedItemId())
                        .orElse(1) >= 1).findAny();
        return loot.isPresent();
    }


    public void loot() {
        Optional<GroundItem> loot = Query.groundItems()
                .idNotEquals(ItemID.MANTA_RAY)
                .stream()
                .filter(i -> Pricing.lookupPrice(i.getDefinition().getUnnotedItemId())
                        .orElse(1) >= 1).findAny();
        if (loot.isEmpty()) {
            return;
        } else {
            if (Inventory.isFull()) {
                Optional<InventoryItem> food = Query.inventory().actionContains("Eat").findClosestToMouse();
                if (food.isPresent() && food.get().click("Eat")) {
                    Waiting.waitUntil(5000, () -> !Inventory.isFull());
                }
            }
            if (loot.get().interact("Take")) {
                Waiting.waitUntil(3000, () -> Query.groundItems().nameContains(loot.get().getName()).findBestInteractable().isEmpty());
              //  tracker.trackData("Looted", (long) Pricing.lookupPrice(loot.get().getId()).orElse(5000) * loot.get().getStack());
                looted = Pricing.lookupPrice(loot.get().getId()).orElse(1) * loot.get().getStack() + looted;
            }
        }
    }

    @Override
    public String toString() {
        return "Looting";
    }

    @Override
    public Priority priority() {
        return Priority.HIGH;
    }

    @Override
    public boolean validate() {
        return canLoot() && Game.isInInstance();
    }

    @Override
    public void execute() {
        loot();
    }
}
