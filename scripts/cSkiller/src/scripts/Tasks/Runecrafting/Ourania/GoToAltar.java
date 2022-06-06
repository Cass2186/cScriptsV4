package scripts.Tasks.Runecrafting.Ourania;

import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSItem;
import org.tribot.script.sdk.Inventory;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.MyPlayer;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.GameObject;
import org.tribot.script.sdk.types.InventoryItem;
import org.tribot.script.sdk.types.WorldTile;
import org.tribot.script.sdk.walking.GlobalWalking;
import org.tribot.script.sdk.walking.LocalWalking;
import scripts.*;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.Tasks.BirdHouseRuns.Nodes.Wait;
import scripts.Tasks.Runecrafting.RunecraftData.RcVars;

import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Optional;

public class GoToAltar implements Task {

    WorldTile ALTAR_TILE = new WorldTile(3058, 5579, 0);

    WorldTile[] path = {
            new WorldTile(3014, 5623, 0),
            new WorldTile(3014, 5623, 0),
            new WorldTile(3014, 5622, 0),
            new WorldTile(3014, 5621, 0),
            new WorldTile(3014, 5620, 0),
            new WorldTile(3014, 5619, 0),
            new WorldTile(3014, 5618, 0),
            new WorldTile(3014, 5617, 0),
            new WorldTile(3014, 5616, 0),
            new WorldTile(3014, 5615, 0),
            new WorldTile(3014, 5614, 0),
            new WorldTile(3014, 5613, 0),
            new WorldTile(3014, 5612, 0),
            new WorldTile(3014, 5611, 0),
            new WorldTile(3014, 5610, 0),
            new WorldTile(3014, 5609, 0),
            new WorldTile(3014, 5608, 0),
            new WorldTile(3014, 5607, 0),
            new WorldTile(3014, 5606, 0),
            new WorldTile(3014, 5605, 0),
            new WorldTile(3014, 5604, 0),
            new WorldTile(3014, 5603, 0),
            new WorldTile(3014, 5602, 0),
            new WorldTile(3014, 5601, 0),
            new WorldTile(3014, 5600, 0),
            new WorldTile(3014, 5599, 0),
            new WorldTile(3014, 5598, 0),
            new WorldTile(3014, 5597, 0),
            new WorldTile(3014, 5596, 0),
            new WorldTile(3014, 5595, 0),
            new WorldTile(3014, 5594, 0),
            new WorldTile(3014, 5593, 0),
            new WorldTile(3014, 5592, 0),
            new WorldTile(3015, 5591, 0),
            new WorldTile(3016, 5590, 0),
            new WorldTile(3016, 5589, 0),
            new WorldTile(3016, 5588, 0),
            new WorldTile(3017, 5587, 0),
            new WorldTile(3018, 5586, 0),
            new WorldTile(3018, 5585, 0),
            new WorldTile(3018, 5584, 0),
            new WorldTile(3019, 5583, 0),
            new WorldTile(3020, 5582, 0),
            new WorldTile(3020, 5581, 0),
            new WorldTile(3020, 5580, 0),
            new WorldTile(3021, 5579, 0),
            new WorldTile(3022, 5579, 0),
            new WorldTile(3023, 5579, 0),
            new WorldTile(3024, 5579, 0),
            new WorldTile(3025, 5579, 0),
            new WorldTile(3026, 5578, 0),
            new WorldTile(3027, 5578, 0),
            new WorldTile(3028, 5578, 0),
            new WorldTile(3029, 5578, 0),
            new WorldTile(3030, 5579, 0),
            new WorldTile(3031, 5580, 0),
            new WorldTile(3032, 5581, 0),
            new WorldTile(3033, 5582, 0),
            new WorldTile(3034, 5582, 0),
            new WorldTile(3035, 5582, 0),
            new WorldTile(3036, 5582, 0),
            new WorldTile(3037, 5582, 0),
            new WorldTile(3038, 5582, 0),
            new WorldTile(3039, 5582, 0),
            new WorldTile(3040, 5582, 0),
            new WorldTile(3041, 5582, 0),
            new WorldTile(3041, 5581, 0),
            new WorldTile(3042, 5580, 0),
            new WorldTile(3043, 5579, 0),
            new WorldTile(3044, 5579, 0),
            new WorldTile(3045, 5579, 0),
            new WorldTile(3046, 5579, 0),
            new WorldTile(3047, 5579, 0),
            new WorldTile(3048, 5579, 0),
            new WorldTile(3049, 5579, 0),
            new WorldTile(3050, 5579, 0),
            new WorldTile(3051, 5579, 0),
            new WorldTile(3052, 5579, 0),
            new WorldTile(3053, 5579, 0),
            new WorldTile(3054, 5579, 0),
            new WorldTile(3055, 5579, 0),
            new WorldTile(3056, 5579, 0),
            new WorldTile(3057, 5579, 0),
            new WorldTile(3058, 5579, 0)
    };

    public boolean emptyPouches() {

        List<InventoryItem> smallMedPouches = Query.inventory()
                .idEquals(ItemID.SMALL_POUCH, ItemID.MEDIUM_POUCH)
                .toList();
        List<InventoryItem> largePouch = Query.inventory()
                .idEquals(ItemID.LARGE_POUCH)
                .idNotEquals(ItemID.RUNE_POUCH)
                .toList();
        if (largePouch.size() > 0 && smallMedPouches.size() > 0) {
            Log.info("Opening pouches");
            Keyboard.sendPress((char) KeyEvent.VK_SHIFT, 16);
            for (InventoryItem item : smallMedPouches) {
                if (item.click("Empty"))
                    Waiting.waitNormal(120, 30);
            }
            Waiting.waitUntil(2000, 75, () -> Inventory.contains(ItemID.PURE_ESSENCE));

            Keyboard.sendRelease((char) KeyEvent.VK_SHIFT, 16);
            if (craftRunesAltar())
                Waiting.waitUntil(4000, 75, () -> !Inventory.contains(ItemID.PURE_ESSENCE));


            Keyboard.sendPress((char) KeyEvent.VK_SHIFT, 16);
            for (InventoryItem item : largePouch) {
                if (item.click("Empty"))
                    Waiting.waitNormal(250, 30);
            }
            Waiting.waitUntil(2000, 125, () -> Inventory.contains(ItemID.PURE_ESSENCE));

            Keyboard.sendRelease((char) KeyEvent.VK_SHIFT, 16);
            Utils.idlePredictableAction();
            if (craftRunesAltar())
                Waiting.waitUntil(4000, 25, () -> !Inventory.contains(ItemID.PURE_ESSENCE));

            return true;
        }
        return false;
    }

    public boolean craftRunesAltar() {
        Optional<GameObject> altar = QueryUtils.getObject("Altar");
        if (altar.map(a -> !a.interact("Craft-rune")).orElse(false) &&
                Waiting.waitUntil(12500, 200, () -> MyPlayer.getAnimation() != -1)) {
             return Waiting.waitUntil(4500, 75,  () -> MyPlayer.getAnimation() == -1);

        }
        return true;
    }

    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        return Vars.get().currentTask != null &&
                Vars.get().currentTask.equals(SkillTasks.RUNECRAFTING) &&
                Inventory.contains(ItemID.PURE_ESSENCE) &&
                RcVars.get().usingOuraniaAlter;
    }

    @Override
    public void execute() {
        if (ALTAR_TILE.distance() > 10)
            LocalWalking.walkPath(List.of(path), PathingUtil::getWalkState);
        //PathingUtil.walkToTile(ALTAR_TILE);
        if (ALTAR_TILE.distance() < 5) {
            if (Inventory.isFull() &&
                    craftRunesAltar())
                Waiting.waitUntil(4000, 75, () ->
                        !Inventory.contains(ItemID.PURE_ESSENCE));

            emptyPouches();
        }
    }


    @Override
    public String toString() {
        return "Going to Ouraina Alter";
    }

    @Override
    public String taskName() {
        return "Runecrafting";
    }
}
