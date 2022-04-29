package scripts.Tasks.BloodCrafting;

import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSItem;
import org.tribot.script.sdk.*;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.GameObject;
import org.tribot.script.sdk.types.InventoryItem;
import org.tribot.script.sdk.types.WorldTile;
import scripts.Data.Const;
import scripts.Data.Vars;
import scripts.ItemID;
import scripts.PathingUtil;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;
import scripts.Timer;
import scripts.Utils;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class GoToDarkAltar implements Task {

    public void clickAltar() {
       /* if (Const.WHOLE_MINE_AREA.contains(MyPlayer.getTile())) {
            Log.info();("[Debug]: Leaving mining area");
            if (PathingUtil.localNavigation(Const.AFTER_N_OBSTACLE))
                Timer.waitCondition(() -> !MyPlayer.isMoving(), 10000, 15000);
            if (Utils.clickObject(Filters.Objects.tileEquals(Const.NORTH_ROCK_OBSTACLE_TILE), "Climb"))
                Timer.waitCondition(() -> MyPlayer.getTile().equals(Const.BEFORE_N_OBSTACLE), 7000, 9000);

        }
        if (MyPlayer.getTile().equals(Const.AFTER_N_OBSTACLE)) {
            if (Utils.clickObject(Filters.Objects.tileEquals(Const.NORTH_ROCK_OBSTACLE_TILE), "Climb"))
                Timer.waitCondition(() -> MyPlayer.getTile().equals(Const.BEFORE_N_OBSTACLE), 7000, 9000);

        }*/
        if (Const.WHOLE_MINE_AREA.contains(MyPlayer.getTile()) ||
                MyPlayer.getTile().equals(Const.BEFORE_N_OBSTACLE)) {
            Log.info("Going to dark altar");
            PathingUtil.walkToTile(new WorldTile(1716, 3880, 0));
        }

        Optional<GameObject> darkAltar = Query.gameObjects()
                .idEquals(27979)
                .actionContains("Venerate")
                .maxPathDistance(12)
                .findClosest();
        if (darkAltar.map(d -> d.interact("Venerate")).orElse(false)) {
            Log.info("Making Dark Essence");
            Waiting.waitUntil(5500, 260,
                    () -> Inventory.getCount(ItemID.DARK_ESSENCE_BLOCK) > 0);
        }
        if (Utils.useItemOnItem(ItemID.CHISEL, ItemID.DARK_ESSENCE_BLOCK)) { //tries to chip essence
            // if MyPlayer
            //animation doesn't change to chipping it will terminate, otherwise waits for no more dark
            int num = Inventory.getCount(ItemID.DARK_ESSENCE_BLOCK);
            if (Timer.waitCondition(() -> Inventory.getCount(ItemID.DARK_ESSENCE_BLOCK)
                    == (num - 3), 5280, 6235)) {
                chipDarkEss();
            } else {
                Log.info("Going to blood altar");
                Vars.get().goToBloodAltar = true;
            }
        }

    }

    private static boolean moveChiselInInventory() {
        Optional<InventoryItem> chisel = Query.inventory()
                .idEquals(ItemID.CHISEL).findClosestToMouse();
        if (chisel.map(c -> c.getIndex() != 27 && c.dragToSlot(27)).orElse(false)) {
            Utils.idlePredictableAction();
            return true;
        }
        return false;
    }

    public static void chipDarkEss() {
        Log.info("Chipping essence");
        List<InventoryItem> ess = Query.inventory()
                .idEquals(ItemID.DARK_ESSENCE_BLOCK)
                .sorted(Comparator.comparingInt(InventoryItem::getIndex))
                .toList();

        moveChiselInInventory();

        Optional<InventoryItem> chisel = Query.inventory()
                .idEquals(ItemID.CHISEL).findClosestToMouse();

        for (int i = 0; i < ess.size()+10; i++) {
            if (!Inventory.contains(ItemID.DARK_ESSENCE_BLOCK) || ChatScreen.isOpen())
                break;

            int last = ess.size() -1;
            if (chisel.map(c -> c.useOn(ess.get(last))).orElse(false)) {
                Waiting.waitNormal(55, 15);
            }
        }
        Waiting.waitUniform(300, 600);
    }


    @Override
    public String toString() {
        return "Going to dark altar";
    }

    @Override
    public Priority priority() {
        return Priority.HIGH;
    }

    @Override
    public boolean validate() {

        return Inventory.isFull() &&
                Inventory.contains(ItemID.DENSE_ESSENCE_BLOCK) &&
                Inventory.getCount(ItemID.DARK_ESSENCE_BLOCK) == 0;
    }

    @Override
    public void execute() {

        clickAltar();
    }
}

