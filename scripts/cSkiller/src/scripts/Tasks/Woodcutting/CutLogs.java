package scripts.Tasks.Woodcutting;

import dax.walker.utils.camera.DaxCamera;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.*;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.EntitySelector.Entities;
import scripts.EntitySelector.finders.prefabs.ItemEntity;

public class CutLogs implements Task {


    public void chopTree(RSArea area, String treeName) {
        Utils.unselectItem();

        PathingUtil.walkToArea(area, false);

        if (area.contains(Player.getPosition())) {
            if (Utils.getPlayerCountInArea(area) > 1) {
                General.println("[Debug]: World hopping due to players in area");
                Utils.hopWorlds();
            }

            RSObject[] tree = Objects.findNearest(20, treeName);
            if (tree.length > 0 && !Inventory.isFull()) {

                if (!tree[0].isClickable())
                    DaxCamera.focus(tree[0]);

                if (Player.getAnimation() == -1) {
                    if (Utils.clickObject(tree[0], "Chop down", false)) {
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
        } else if (PathingUtil.localNavigation(area.getRandomTile()))
            PathingUtil.movementIdle();
    }

    public void progressive() {
        WoodCuttingConst.equipAxe();
        int currentLevel = Skills.getActualLevel(Skills.SKILLS.WOODCUTTING);
        if (currentLevel < 15) {
            chopTree(WoodCuttingConst.VARROCK_WESK_REGULAR, "Tree");

        } else if (currentLevel < 30) {
            checkAxe();
            chopTree(WoodCuttingConst.VARROCK_WEST_OAKS, "Oak");
        } else if (currentLevel < 55) {
            checkAxe();
            chopTree(WoodCuttingConst.PORT_SARIM_WILLOWS, "Willow");
        } else {
            checkAxe();
        }
    }


    public void checkAxe() {
        int currentLevel = Skills.getCurrentLevel(Skills.SKILLS.WOODCUTTING);
        if (currentLevel >= SkillTasks.WOODCUTTING.getEndLevel()) {
            Vars.get().currentTask = null;
            return;
        }
        if (currentLevel < 6) {
            if (Inventory.find(ItemId.AXE_IDS[0]).length < 1 && !Equipment.isEquipped(ItemId.AXE_IDS[0])) {
                BankManager.open(true);
                BankManager.withdraw(1, true, ItemId.AXE_IDS[0]);
                BankManager.close(true);
                Utils.equipItem(ItemId.AXE_IDS[0], "Wield");

            }
        } else if (currentLevel < 21) { // steel axe
            if (Inventory.find(ItemId.AXE_IDS[1]).length < 1 && !Equipment.isEquipped(ItemId.AXE_IDS[1])) {
                BankManager.open(true);
                BankManager.withdraw(1, true, ItemId.AXE_IDS[1]);
                BankManager.close(true);
                if (Skills.getCurrentLevel(Skills.SKILLS.ATTACK) >= 5) {
                    Utils.equipItem(ItemId.AXE_IDS[1], "Wield");
                }
            }
        } else if (currentLevel < 31) { // mithril axe
            if (Inventory.find(ItemId.AXE_IDS[2]).length < 1 && !Equipment.isEquipped(ItemId.AXE_IDS[2])) {
                BankManager.open(true);
                BankManager.withdraw(1, true, ItemId.AXE_IDS[2]);
                BankManager.close(true);
                if (Skills.getCurrentLevel(Skills.SKILLS.ATTACK) >= 20) {
                    Utils.equipItem(ItemId.AXE_IDS[2], "Wield");
                }
            }
        } else if (currentLevel < 41) { // adamant axe
            if (Inventory.find(ItemId.AXE_IDS[3]).length < 1 && !Equipment.isEquipped(ItemId.AXE_IDS[3])) {
                BankManager.open(true);
                BankManager.withdraw(1, true, ItemId.AXE_IDS[3]);
                BankManager.close(true);
                if (Skills.getCurrentLevel(Skills.SKILLS.ATTACK) >= 30) {
                    Utils.equipItem(ItemId.AXE_IDS[3], "Wield");
                }
            }
        } else if (currentLevel < 61) { // rune axe
            if (Inventory.find(ItemId.AXE_IDS[4]).length < 1 && !Equipment.isEquipped(ItemId.AXE_IDS[4])) {
                BankManager.open(true);
                BankManager.withdraw(1, true, ItemId.AXE_IDS[4]);
                BankManager.close(true);
                if (Skills.getCurrentLevel(Skills.SKILLS.ATTACK) >= 40) {
                    Utils.equipItem(ItemId.AXE_IDS[4], "Wield");
                }
            }
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
        RSItem invAxe = Entities.find(ItemEntity::new) //check if noted
                .nameContains("axe")
                .getFirstResult();

        boolean equippedAxe = Equipment.isEquipped(Filters.Items.nameContains(  " axe"));

        // General.println("[CutLogs]: Has Axe: " +(invAxe != null || equippedAxe) );
        return Vars.get().currentTask != null &&
                Vars.get().currentTask.equals(SkillTasks.WOODCUTTING)
                && (invAxe != null || equippedAxe);
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
