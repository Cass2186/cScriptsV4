package scripts.Tasks.Woodcutting;

import dax.walker.utils.camera.DaxCamera;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSObject;
import org.tribot.script.sdk.Skill;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.Area;
import org.tribot.script.sdk.types.GameObject;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.*;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.Tasks.Woodcutting.WoodcuttingData.WcLocations;
import scripts.Tasks.Woodcutting.WoodcuttingData.WoodCuttingConst;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;

public class CutLogs implements Task {


    private void chopTree(Area area, String treeName) {
        Utils.unselectItem();

        PathingUtil.walkToArea(area, false);

        if (area.containsMyPlayer()) {
            if (Utils.getPlayerCountInArea(area) > 1) {
                General.println("[Debug]: World hopping due to players in area");
                Utils.hopWorlds();
            }

            RSObject[] tree = Objects.findNearest(20, treeName);
            Optional<GameObject> treeOpt = Query.gameObjects().nameContains(treeName).maxDistance(15).findBestInteractable();
            if (!Inventory.isFull()) {

                if (Player.getAnimation() == -1) {
                    if (treeOpt.map(t -> t.interact("Chop down")).orElse(false)) {
                        //if (Utils.clickObject(tree[0], "Chop down", false)) {
                        Timer.waitCondition(() -> Player.getAnimation() != -1, 5000, 7000);
                    }
                }
                if (Player.getAnimation() != -1) {
                    General.println("[Debug]: Waiting for idle");
                    if (treeName.contains("Maple")) {
                        if (tree.length > 1) {
                            Timer.abc2SkillingWaitCondition(() -> Inventory.isFull() ||
                                    Player.getAnimation() == -1, tree[1], 70000, 95000);
                        } else {
                            Timer.abc2SkillingWaitCondition(() -> Inventory.isFull() ||
                                    Player.getAnimation() == -1, 70000, 95000);
                        }
                    } else {
                        Timer.abc2SkillingWaitCondition(() -> Inventory.isFull() ||
                                Player.getAnimation() == -1, 30000, 55000);
                    }
                }

            }
        } else if (PathingUtil.localNav(area.getCenter()))
            PathingUtil.movementIdle();

        else if (PathingUtil.walkToTile(area.getCenter())) {
            PathingUtil.movementIdle();
        }
    }

    public void progressive() {
        WoodCuttingConst.equipAxe();
        checkAxe();

        Optional<WcLocations> wcOptional = WcLocations.getWoodcuttingLocation();
        wcOptional.ifPresent(w -> chopTree(w.getArea(), w.getTreeName()));

    }

    private void withdrawAndEquip(int id, int levelReq) {
        BankManager.open(true);
        BankManager.withdraw(1, true, id);
        BankManager.close(true);
        if (Skill.ATTACK.getActualLevel() >= levelReq) {
            Utils.equipItem(id, "Wield");
        }
    }

    public void checkAxe() {
        int currentLevel = Skills.getCurrentLevel(Skills.SKILLS.WOODCUTTING);
        if (currentLevel >= SkillTasks.WOODCUTTING.getEndLevel()) {
            Vars.get().currentTask = null;
            return;
        }
        if (currentLevel < 6) {
            if (Inventory.find(ItemID.AXE_IDS[0]).length < 1 && !Equipment.isEquipped(ItemID.AXE_IDS[0])) {
                withdrawAndEquip(ItemID.AXE_IDS[0], 1);
            }
        } else if (currentLevel < 21) { // steel axe
            if (Inventory.find(ItemID.AXE_IDS[1]).length < 1 && !Equipment.isEquipped(ItemID.AXE_IDS[1])) {
                withdrawAndEquip(ItemID.STEEL_AXE, 5);
            }
        } else if (currentLevel < 31) { // mithril axe
            if (Inventory.find(ItemID.AXE_IDS[2]).length < 1 && !Equipment.isEquipped(ItemID.AXE_IDS[2])) {
                withdrawAndEquip(ItemID.MITHRIL_AXE, 20);
            }
        } else if (currentLevel < 41) { // adamant axe
            if (Inventory.find(ItemID.AXE_IDS[3]).length < 1 && !Equipment.isEquipped(ItemID.AXE_IDS[3])) {
                withdrawAndEquip(ItemID.ADAMANT_AXE, 30);
            }
        } else if (currentLevel < 61) { // rune axe
            if (Inventory.find(ItemID.AXE_IDS[4]).length < 1 && !Equipment.isEquipped(ItemID.AXE_IDS[4])) {
                withdrawAndEquip(ItemID.ADAMANT_AXE, 40);
            }
        } else if (Inventory.find(ItemID.AXE_IDS[5]).length < 1 && !Equipment.isEquipped(ItemID.AXE_IDS[5])) {
            withdrawAndEquip(ItemID.ADAMANT_AXE, 60);
        }


    }

    @Override
    public String toString() {
        return "Chopping logs";
    }


    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        boolean hasInvAxe = Query.inventory()
                .nameContains("axe")
                .nameNotContains("pickaxe")
                .isAny();

        boolean hasEquippedAxe = Query.equipment()
                .nameContains("axe")
                .nameNotContains("pickaxe")
                .isAny();

        // General.println("[CutLogs]: Has Axe: " +(invAxe != null || equippedAxe) );
        return Vars.get().currentTask != null &&
                Vars.get().currentTask.equals(SkillTasks.WOODCUTTING)
                && (hasInvAxe || hasEquippedAxe);
    }


    @Override
    public void execute() {
        Utils.FACTOR = .55;
        progressive();
    }


    @Override
    public String taskName() {
        return "Woodcutting";
    }
}
