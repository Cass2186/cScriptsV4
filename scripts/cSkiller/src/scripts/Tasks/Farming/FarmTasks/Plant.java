package scripts.Tasks.Farming.FarmTasks;

import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSItemDefinition;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSObject;
import scripts.Tasks.Farming.Data.Enums.Trees;
import scripts.Tasks.Farming.Data.FarmConst;
import scripts.Tasks.Farming.Data.FarmVars;

import scripts.Tasks.Farming.Data.FarmingUtils;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;
import scripts.Timer;
import scripts.Utils;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Plant implements Task {


    public boolean plantHerb(int herbId, int patchId) {
        FarmingUtils.rakeWeeds(patchId);
        RSObject[] patch = Objects.findNearest(20, Filters.Objects.nameContains("Herb patch")
                .and(Filters.Objects.idEquals(patchId)));
        if (patch.length > 0) {
            FarmVars.get().status = "Planting herbs";
            if (Utils.useItemOnObject(herbId, patchId)) {
                Timer.waitCondition(() -> Player.getAnimation() != -1, 4000, 8000);
                Timer.abc2WaitCondition(() -> Player.getAnimation() == -1, 4000, 8000);
            }
            return true;
        }
        return false;
    }

    public boolean plantTreePatch(int herbId, int patchId) {
        FarmingUtils.rakeWeeds(patchId);
        RSObject[] patch = Objects.findNearest(20, Filters.Objects.nameContains("Tree patch").and(Filters.Objects.idEquals(patchId)));
        if (patch.length > 0) {
            FarmVars.get().status = "Planting herbs";
            if (Utils.useItemOnObject(herbId, patchId)) {
                Timer.waitCondition(() -> Player.getAnimation() != -1, 4000, 8000);
                Timer.abc2WaitCondition(() -> Player.getAnimation() == -1, 4000, 8000);
                return true;
            }
        }
        return true;
    }

    public boolean plantAllotmentPatch(int seedId, int patchId) {
        FarmingUtils.rakeWeeds(patchId);
        RSObject[] patch = Objects.findNearest(20, Filters.Objects.nameContains("allotment").
                and(Filters.Objects.idEquals(patchId)));
        if (patch.length > 0) {
            FarmVars.get().status = "Planting Allotment";
            if (Utils.useItemOnObject(seedId, patchId)) {
                Timer.waitCondition(() -> Player.getAnimation() != -1, 4000, 8000);
                return Timer.abc2WaitCondition(() -> Player.getAnimation() == -1, 4000, 8000);
            }
        }
        return false;
    }

    public static void useCompostFlower(int patchId) {
        RSObject[] patch = Objects.findNearest(20, Filters.Objects.nameContains("Flower patch").and(Filters.Objects.idEquals(patchId)));
        if (patch.length > 0) {
            FarmVars.get().status = "Using Ultracompost";
            if (FarmVars.get().usingBottomless) {
                if (Utils.useItemOnObject(FarmConst.BOTTOMLESS_COMPOST, patchId)) {
                    Timer.waitCondition(() -> Player.getAnimation() != -1, 4000, 8000);
                    Utils.shortSleep();
                }
            } else if (Utils.useItemOnObject(FarmConst.ULTRACOMPOST, patchId)) {
                Timer.waitCondition(() -> Player.getAnimation() != -1, 4000, 8000);
                Utils.shortSleep();
            }
        }
    }

    public boolean plantFlowerSeed(int herbId, int patchId) {
        useCompostFlower(patchId);
        FarmingUtils.rakeWeeds(patchId);
        RSObject[] patch = Objects.findNearest(20, Filters.Objects.nameContains("Flower patch")
                .and(Filters.Objects.idEquals(patchId)));
        if (patch.length > 0) {
            FarmVars.get().status = "Planting Flower";
            if (Utils.useItemOnObject(herbId, patchId)) {
                Timer.waitCondition(() -> Player.getAnimation() != -1, 4000, 8000);
                return Timer.abc2WaitCondition(() -> Player.getAnimation() == -1, 4000, 8000);
            }
        }
        return false;
    }

    public void clearTree() {
        RSObject[] trees = Objects.findNearest(20, Filters.Objects.actionsContains("Chop down").
                or(Filters.Objects.actionsContains("Pick-")).and(Filters.Objects.actionsContains("Guide"))); //guide filters out random trees
        if (trees.length > 0) {
            FarmVars.get().status = "Clearing Tree";

            RSNPC[] npc = NPCs.findNearest(Filters.NPCs.actionsContains("Pay (tree patch)"));
            RSNPC[] npc2 = NPCs.findNearest(Filters.NPCs.actionsContains("Pay"));
            if (npc.length == 0 && npc2.length > 0 && Utils.clickNPC(npc2[0], "Pay", false)) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Yes.");
                Utils.shortSleep();
                return;
            }
            if (npc.length > 0 && Utils.clickNPC(npc[0], "Pay (tree patch)", false)) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Yes.");
                Utils.shortSleep();
            }
        }
    }

    public boolean plantHerbs(int patchId) {
        FarmingUtils.clearDeadPlants(patchId);
        FarmingUtils.rakeWeeds(patchId);
        FarmingUtils.useCompost(patchId);
        return plantHerb(FarmVars.get().currentHerbId, patchId);
    }

    public boolean plantTree(int patchId) {
        FarmingUtils.clearDeadPlants(patchId);
        clearTree();
        FarmingUtils.rakeWeeds(patchId);
        FarmingUtils.useCompostTree(patchId);
        return plantTreePatch(FarmVars.get().treeId, patchId);
    }

    public boolean plantFruitTree(int patchId) {
        FarmingUtils.clearDeadPlants(patchId);
        clearTree();
        FarmingUtils.rakeWeeds(patchId);
        FarmingUtils.useCompostTree(patchId);
        return plantTreePatch(FarmVars.get().fruitTreeId, patchId);
    }

    public boolean plantFlower(int patchId) {
        FarmingUtils.clearDeadPlants(patchId);
        FarmingUtils.rakeWeeds(patchId);
        FarmingUtils.useCompost(patchId);
        return plantFlowerSeed(FarmConst.LIMPWURT_SEEDS, patchId);
    }

    public void plantAllotments(int... allotmentIds) {
        for (int i : allotmentIds) {
            FarmingUtils.clearDeadPlants(i);
            FarmingUtils.rakeWeeds(i);
            FarmingUtils.useCompostAllotment(i);
            plantAllotmentPatch(FarmVars.get().currentAllotmentId, i);
            if (i == FarmConst.MORYTANIA_S_ALLOTMENT_ID) {
                General.println("[Debug]: Starting allotment timer");
                //TODO update based on allotment type
                FarmVars.get().allotmentTimer = new Timer(4900000); // 70min
            }
        }
    }

    public void plantAllHerbs(ArrayList<Integer> herbPatchIds) {
        for (Integer i : herbPatchIds) {
            plantHerbs(i);
            plantFlower(i);
            if (i == FarmConst.MORYTANIA_HERB_PATCH_ID) {
                General.println("[Debug]: Starting Herb Timer");
                FarmVars.get().herbTimer = new Timer(4800000); // 80min
            }
        }
    }

    @Override
    public void execute() {
        if (FarmVars.get().doingTrees) {
            FarmVars.get().status = "Planting Trees";
            General.println("[Plant] " + FarmVars.get().status, Color.RED);
            plantTree(FarmConst.FALADOR_TREE_PATCH_ID);
            plantTree(FarmConst.VAROCK_TREE_PATCH_ID);
            plantTree(FarmConst.TAVERLY_TREE_PATCH_ID);
            plantTree(FarmConst.LUMBRIDGE_TREE_PATCH_ID);
            if (plantTree(FarmConst.GNOME_STRONGHOLD_TREE_PATCH_ID)) {
                General.println("[Debug]: Starting tree timer");
                // FarmVars.get().treeTimer = new Timer(28800 * 1000); // 480min
            }
        } else if (FarmVars.get().doingAllotments) {
            plantAllotments(
                    FarmConst.ARDOUGNE_N_ALLOTMENT_ID,
                    FarmConst.ARDOUGNE_S_ALLOTMENT_ID,
                    FarmConst.FALADOR_NW_ALLOTMENT_ID,
                    FarmConst.FALADOR_SE_ALLOTMENT_ID,
                    FarmConst.CATHERBY_N_ALLOTMENT_ID,
                    FarmConst.CATHERBY_S_ALLOTMENT_ID,
                    FarmConst.HOISIDIUS_SW_ALLOTMENT_ID,
                    FarmConst.HOISIDIUS_NE_ALLOTMENT_ID,
                    FarmConst.MORYTANIA_N_ALLOTMENT_ID,
                    FarmConst.MORYTANIA_S_ALLOTMENT_ID
            );
        } else if (FarmVars.get().doingFruitTrees) {
            FarmVars.get().status = "Planting Fruit Trees";
            General.println("[Plant] " + FarmVars.get().status, Color.RED);
            plantFruitTree(FarmConst.STRONGHOLD_FRUIT_TREE_ID);
            plantFruitTree(FarmConst.TGV_FRUIT_TREE_ID);
            plantFruitTree(FarmConst.CATHERBY_FRUIT_TREE_ID);
            plantFruitTree(FarmConst.BRIMHAVEN_FRUIT_TREE_ID);
        } else {
            FarmVars.get().status = "Planting herbs.";
            plantAllHerbs( new ArrayList<>(Arrays.asList(
                    FarmConst.FALADOR_HERB_PATCH_ID,
                    FarmConst.FALADOR_FLOWER_PATCH_ID,
                    FarmConst.CATHERBY_HERB_PATCH_ID,
                    FarmConst.CATHERBY_FLOWER_PATCH_ID,
                    FarmConst.ARDOUGNE_HERB_PATCH_ID,
                    FarmConst.ARDOUGNE_FLOWER_PATCH_ID,
                    FarmConst.HOISIDIUS_HERB_PATCH_ID,
                    FarmConst.HOISIDIUS_FLOWER_PATCH_ID,
                    FarmConst.MORYTANIA_FLOWER_PATCH_ID,
                    FarmConst.MORYTANIA_HERB_PATCH_ID
            )));
            plantHerbs(FarmConst.FARMING_GUILD_HERB_PATCH_ID);
        }
    }

    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        General.println("[Debug]: Should restock =" + FarmVars.get().shouldRestock);
        General.println("[Debug]: Should bank =" + FarmVars.get().shouldBank);
        General.println("[Debug]: Should break =" + FarmVars.get().shouldBreak);

        return Login.getLoginState() == Login.STATE.INGAME
                && !FarmVars.get().shouldRestock
                && !FarmVars.get().shouldBank
                && !FarmVars.get().shouldBreak
                && (Move.nearTreeSpot() || Move.nearAllotmentSpot());
    }
}
