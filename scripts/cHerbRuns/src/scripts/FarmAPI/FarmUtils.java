package scripts.FarmAPI;

import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.script.sdk.Inventory;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.GameObject;
import org.tribot.script.sdk.types.GroundItem;
import org.tribot.script.sdk.types.InventoryItem;
import scripts.Data.Const;
import scripts.Data.Enums.Patches;
import scripts.Data.Vars;
import scripts.ItemID;
import scripts.Timer;
import scripts.Utils;

import java.util.List;
import java.util.Optional;

public class FarmUtils {

     public static Optional<GameObject> getNearbyReadyHerbs() {
        return  Query.gameObjects()
                .maxDistance(15)
                .actionContains("Pick")
                .actionContains("Inspect")
                .findBestInteractable();

    }

    public static Optional<GameObject> getNearbyReadyHerbs(int patchId) {
        return  Query.gameObjects()
                .maxDistance(15)
                .actionContains("Pick")
                .actionContains("Inspect")
                .idEquals(patchId)
                .findBestInteractable();

    }


    public static Optional<GameObject> getNearbyReadyAllotments() {
        return  Query.gameObjects()
                .maxDistance(15)
                .actionContains("Harvest")
                .findBestInteractable();
    }

   public static Optional<GameObject>  getNearbyReadyTrees() {
        return  Query.gameObjects()
                .maxDistance(15)
                .actionContains("Check-health")
                .findBestInteractable();

    }
    public static Optional<GameObject>  getTreesToBeRemoved() {
        return  Query.gameObjects()
                .maxDistance(15)
                .actionContains("Chop down")
                .actionContains("Guide")
                .findBestInteractable();
    }

   public static Optional<GameObject> getFruitTreesToBeRemoved() {
        return Query.gameObjects()
                .maxDistance(15)
                .actionContains("Pick-")
                .actionContains("Guide")
                .findBestInteractable();

    }
    public static void getGroundLimpwurt() {
        List<GroundItem> groundItem = Query.groundItems()
                .idEquals(ItemID.LIMPWURT_ROOT).toList();
        for (GroundItem item : groundItem) {
            Vars.get().status = "Getting ground items";
            dropItemsIfNeeded();
            int size = Inventory.getAll().size();

            if (item.interact("Take"))
                Timer.waitCondition(() -> Inventory.getAll().size() > size, 5000, 7000);
        }
    }

    private static void noteHerbs() {
        if (Inventory.isFull()) {
            Inventory.drop(ItemID.WEEDS, 6925); //weeds and empty buckets
            Utils.unselectItem();

            General.println("[Debug]: Inventory is full");

            List<InventoryItem> grimyHerbs = Query.inventory().nameContains("Grimy").toList();
            for (InventoryItem i : grimyHerbs) {
                if (i.getDefinition().isNoted())
                    continue;

                Vars.get().status = "Noting herbs";
                if (Utils.useItemOnNPC(i.getDefinition().getId(), "Tool Leprechaun")) {
                    NPCInteraction.waitForConversationWindow();
                    Utils.idleNormalAction();
                }
            }
        }
    }

    private static void noteItem(int id) {
        Optional<InventoryItem> item = Query.inventory().idEquals(id).findClosestToMouse();
        if (item.map(i -> !i.getDefinition().isNoted()).orElse(false)) {
            Vars.get().status = "Noting herbs";

            if (Utils.useItemOnNPC(id, "Tool Leprechaun")) {
                NPCInteraction.waitForConversationWindow();
                Utils.shortSleep();
            }
        }
    }
    public static void dropItemsIfNeeded(){
        if (Inventory.isFull()) {
            Inventory.drop(ItemID.WEEDS,6055, 1925);
            noteHerbs();
            noteItem(ItemID.LIMPWURT_ROOT);
            getGroundLimpwurt();
        }
    }

}
