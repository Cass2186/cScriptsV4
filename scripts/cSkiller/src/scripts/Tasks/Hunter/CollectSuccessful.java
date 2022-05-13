package scripts.Tasks.Hunter;

import org.tribot.api.General;
import org.tribot.api.input.Keyboard;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.Area;
import org.tribot.script.sdk.types.GameObject;
import org.tribot.script.sdk.types.InventoryItem;
import org.tribot.script.sdk.types.WorldTile;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.AntiBan;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.EntitySelector.Entities;
import scripts.EntitySelector.finders.prefabs.ObjectEntity;
import scripts.ItemID;
import scripts.Tasks.BirdHouseRuns.Nodes.Wait;
import scripts.Tasks.Hunter.HunterData.HunterConst;
import scripts.Timer;
import scripts.Utils;

import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Optional;

public class CollectSuccessful implements Task {


    public void collectBirdSnare() {
        RSObject trap = Entities.find(ObjectEntity::new)
                .idEquals(HunterConst.CAUGHT_BIRD_SNARE)
                .getFirstResult();

        General.println("[Debug]: collecting successful");
        RSItem[] invTraps = Inventory.find(HunterConst.RAW_BIRD_MEAT);
        if (trap != null && Utils.clickObject(trap, "Check",
                false)) {
            Timer.slowWaitCondition(() -> Inventory.find(HunterConst.RAW_BIRD_MEAT).length >
                    invTraps.length, 7000, 9000); //was 10-12s
        }

    }

    public void dropSalamanders() {
        List<InventoryItem> list = Query.inventory().idEquals(HunterConst.ALL_SALAMANDERS).sortedByDistanceToMouse().toList();
        if (list.size() > 0) {
            Keyboard.sendPress((char) KeyEvent.VK_SHIFT, 16);
            list.forEach(s -> s.click("Release"));
            Keyboard.sendRelease((char) KeyEvent.VK_SHIFT, 16);
            Waiting.waitUntil(1500, 20, () -> !org.tribot.script.sdk.Inventory.contains(HunterConst.ALL_SALAMANDERS));
            Waiting.waitNormal(400, 120); //prevents trying to reclick
        }
        Inventory.drop(ItemID.WATERSKIN0);
    }

    public boolean collectNetTrapNew(List<WorldTile> tileList) {
        Optional<GameObject> check = Query.gameObjects().actionContains("Check")
                .tileEquals(tileList.toArray(WorldTile[]::new))
                .maxDistance(20).findBestInteractable();
        List<InventoryItem> sals = Query.inventory().nameContains("salamander")
                .toList();
        if (check.map(c -> c.interact("Check")).orElse(false)) {
            return Waiting.waitUntil(8000, Utils.random(340, 550),
                    () -> Query.inventory()
                            .nameContains("salamander")
                            .toList().size() > sals.size());
        }
        return false;
       /* for (WorldTile t : tileList) {
            if (Inventory.getAll().length > Utils.random(16, 19)) {
                dropSalamanders();
            }
            List<InventoryItem> sals = Query.inventory().nameContains("salamander")
                    .toList();
            check = Query.gameObjects().actionContains("Check")
                    .inArea(Area.fromRadius(t, 1))
                    .maxDistance(20).findBestInteractable();
            if (check.map(c->c.interact("Check")).orElse(false)){
                Timer.slowWaitCondition(() -> Query.inventory().nameContains("salamander")
                        .toList().size() >
                        sals.size(), 7000, 9000); //was 10-12s
            }
        }*/
    }

    public void collectNetTrap(List<RSTile> tileList) {
        for (RSTile t : tileList) {
            RSObject trap = Entities.find(ObjectEntity::new)
                    .inArea(new RSArea(t, 1))
                    .idEquals(HunterConst.NET_TRAP_SUCCESSFUL)
                    .getFirstResult();
            if (Inventory.getAll().length > Utils.random(16, 19)) {
                dropSalamanders();
            }
            List<InventoryItem> sals = Query.inventory().nameContains("salamander")
                    .toList();
            Optional<GameObject> check = Query.gameObjects().actionContains("Check")
                    .inArea(Area.fromRadius(Utils.getWorldTileFromRSTile(t), 1))
                    .maxDistance(20).findBestInteractable();
            if (check.map(c -> c.interact("Check")).orElse(false)) {
                Timer.slowWaitCondition(() -> Query.inventory().nameContains("salamander")
                        .toList().size() >
                        sals.size(), 7000, 9000); //was 10-12s
            }

            RSItem[] invTraps = Inventory.find(HunterConst.SWAMP_LIZARD);
            if (trap != null && Utils.clickObject(trap, "Check",
                    false)) {
                Timer.slowWaitCondition(() -> Inventory.find(HunterConst.SWAMP_LIZARD).length >
                        invTraps.length, 7000, 9000); //was 10-12s
            }
        }
    }

    public boolean collectBoxTrap(WorldTile... tiles) {
        Optional<GameObject> box = Query.gameObjects()
                .actionContains("Check")
                .idEquals(HunterConst.SHAKING_BOX_TRAP)
                .tileEquals(tiles)
                .maxDistance(20).findBestInteractable();

        List<InventoryItem> chins = Query.inventory().nameContains("chinchompa")
                .toList();
        if (box.map(b -> b.interact("Check")).orElse(false)) {
            return Waiting.waitUntil(8000, Utils.random(340, 550),
                    () -> Query.inventory()
                            .nameContains("chinchompa")
                            .toList().size() > chins.size());
        }
        return false;
    }


    @Override
    public String toString() {
        return "Collecting catches";
    }


    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        if (Skills.getCurrentLevel(Skills.SKILLS.HUNTER) < 29) {
            return Vars.get().currentTask != null &&
                    Vars.get().currentTask.equals(SkillTasks.HUNTER) &&
                    Objects.findNearest(20, HunterConst.CAUGHT_BIRD_SNARE)
                            .length > 0;

        } else if (Skills.getCurrentLevel(Skills.SKILLS.HUNTER) < 47) { // should be 43
            return Vars.get().currentTask != null &&
                    Vars.get().currentTask.equals(SkillTasks.HUNTER) &&
                    Query.gameObjects().idEquals(HunterConst.NET_TRAP_SUCCESSFUL)
                            .maxDistance(20).isAny();
        } else if (Skills.getCurrentLevel(Skills.SKILLS.HUNTER) < 67) {
            return Vars.get().currentTask != null &&
                    Vars.get().currentTask.equals(SkillTasks.HUNTER) &&
                    Query.gameObjects().actionContains("Check")
                            .maxDistance(20).isAny();

        } else {

        }
        return false;
    }

    @Override
    public void execute() {
      if (Skills.getCurrentLevel(Skills.SKILLS.HUNTER) < 29) {
            collectBirdSnare();

        } else if (Skills.getCurrentLevel(Skills.SKILLS.HUNTER) < 43) { // should be 43
            collectNetTrap(HunterConst.CANFIS_TRAP_TILES);
        } else if (Skills.getCurrentLevel(Skills.SKILLS.HUNTER) < 47) {
            //doing falcons, not needed
        } else if (Skills.getCurrentLevel(Skills.SKILLS.HUNTER) < 67) {
            collectNetTrapNew(HunterConst.YELLOW_SALAMANDER_TREES);
        }
    }

    @Override
    public String taskName() {
        return "Hunter";
    }
}