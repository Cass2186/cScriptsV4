package scripts.Tasks.Farming.FarmTasks;

import dax.walker.utils.AccurateMouse;
import dax.walker.utils.camera.DaxCamera;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.*;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSGroundItem;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;
import scripts.Tasks.Farming.Data.FarmConst;
import scripts.Tasks.Farming.Data.FarmVars;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;
import scripts.Timer;
import scripts.Utils;


public class Harvest implements Task {

    private void harvestHerbs(int patchId) {
        RSObject[] herbs = Objects.findNearest(20, Filters.Objects.actionsContains("Pick").and(Filters.Objects.idEquals(patchId)));
        if (herbs.length > 0) {

            if (Inventory.isFull()) {
                Inventory.drop(6055, 1925);
                Utils.unselectItem();
                noteHerbs();
                noteItem(FarmConst.LIMPWURT_ROOT);
                getGroundItems();
            }
            FarmVars.get().status = "Picking Herbs";

            Utils.unselectItem();

            if (!herbs[0].isClickable())
                DaxCamera.focus(herbs[0]);

            unselectItem(herbs[0]);

            if (AccurateMouse.click(herbs[0], "Pick")) {
                if (Timer.waitCondition(() -> Player.getAnimation() != -1, 6000, 9000))
                    Timer.abc2WaitCondition(() -> Player.getAnimation() == -1 || Inventory.isFull(), 20000, 40000);
            }
        }
    }

    private void harvestAllotment(int patchId) {
        RSObject[] herbs = Objects.findNearest(20, Filters.Objects.actionsContains("Harvest").
                and(Filters.Objects.idEquals(patchId)));
        if (herbs.length > 0) {

            if (Inventory.isFull()) {
                Utils.dropItem(6055);
                Utils.dropItem(1925);
                noteItem(1942); //potato
                noteItem(FarmConst.LIMPWURT_ROOT);
                noteItem(FarmConst.SNAPEGRASS);
                noteItem(FarmConst.STRAWBERRIES);
                noteItem(FarmConst.WATERMELLON);
                noteItem(FarmConst.TOMATO);
                noteItem(FarmConst.CABBAGE);
                noteItem(FarmConst.SWEETCORN);
            }

            FarmVars.get().status = "Harvesting";
            Utils.unselectItem();
            if (!herbs[0].isClickable())
                DaxCamera.focus(herbs[0]);

            unselectItem(herbs[0]);

            if (AccurateMouse.click(herbs[0], "Harvest")) {
                if (Timer.waitCondition(() -> Player.getAnimation() != -1, 6000, 9000))
                    Timer.abc2WaitCondition(() -> Objects.findNearest(3, Filters.Objects.actionsContains("Harvest").
                            and(Filters.Objects.idEquals(patchId))).length == 0
                            || Inventory.isFull(), 20000, 40000);
            }
        }
    }

    private boolean harvestAllotmentMorytania(int patchId) {
        RSObject[] ptch = Objects.findNearest(20, Filters.Objects.idEquals(patchId));
        RSObject[] herbs = Objects.findNearest(20, Filters.Objects.actionsContains("Harvest").
                and(Filters.Objects.idEquals(patchId)));
        if (herbs.length > 0) {

            if (Inventory.isFull()) {
                Utils.dropItem(6055);
                Utils.dropItem(1925);
                noteItem(FarmConst.LIMPWURT_ROOT);
                noteItem(FarmConst.SNAPEGRASS);
                noteItem(FarmConst.STRAWBERRIES);
                noteItem(FarmConst.WATERMELLON);
                noteItem(FarmConst.TOMATO);
                noteItem(FarmConst.CABBAGE);
                noteItem(FarmConst.SWEETCORN);
            }

            FarmVars.get().status = "Harvesting";
            Utils.unselectItem();
            if (!herbs[0].isClickable())
                DaxCamera.focus(herbs[0]);

            unselectItem(herbs[0]);

            if (AccurateMouse.click(herbs[0], "Harvest")) {
                if (Timer.waitCondition(() -> Player.getAnimation() != -1, 6000, 9000))
                    Timer.abc2WaitCondition(() -> Objects.findNearest(3, Filters.Objects.actionsContains("Harvest").
                            and(Filters.Objects.idEquals(patchId))).length == 0
                            || Inventory.isFull(), 20000, 40000);
            }
        }
        if (ptch.length >0){
            return true;
        }
        return false;
    }

    private void harvestTree() {
        RSObject[] trees = Objects.findNearest(20, Filters.Objects.actionsContains("Check-health"));
        if (trees.length > 0) {
            FarmVars.get().status = "Harvesting Tree";

            if (!trees[0].isClickable())
                DaxCamera.focus(trees[0]);

            Utils.unselectItem();
            unselectItem(trees[0]);

            if (AccurateMouse.click(trees[0], "Check-health")) {
                int currentXp = Skills.SKILLS.FARMING.getXP();
                Timer.abc2WaitCondition(() -> Skills.SKILLS.FARMING.getXP() > currentXp, 8000, 15000);
            }
        }
    }

    private void unselectItem(RSObject item) {
        if (Game.getItemSelectionState() == 1) {
            Mouse.click(0);
        }
    }

    private void unselectItem() {
        if (Game.getItemSelectionState() == 1) {
            Mouse.click(0);
        }
    }

    public void getGroundItems() {
        RSGroundItem[] groundItem = GroundItems.find(FarmConst.LIMPWURT_ROOT);
        for (RSGroundItem item : groundItem) {
            FarmVars.get().status = "Getting ground items";

            if (Inventory.isFull()) {
                Inventory.drop(6055, 1925);
                unselectItem();//weeds and empty buckets
                noteHerbs();
                noteItem(FarmConst.LIMPWURT_ROOT);
            }

            if (!item.isClickable())
                item.adjustCameraTo();

            int length = Inventory.getAll().length;

            if (DynamicClicking.clickRSGroundItem(item, "Take"))
                Timer.waitCondition(() -> Inventory.getAll().length > length, 5000, 7000);

        }
    }


    private void noteItem(int id) {
        RSItem[] item = Inventory.find(id);

        if (item.length > 0) {

            if (item[0].getDefinition().getID() != item[0].getDefinition().getNotedItemID()) {
                FarmVars.get().status = "Noting herbs";

                if (Utils.useItemOnNPC(id, "Tool Leprechaun")) {
                    NPCInteraction.waitForConversationWindow();
                    Utils.shortSleep();
                }
            }
        }

    }

    private static void noteHerbs() {
        if (Inventory.isFull()) {
            Inventory.drop(6055, 6925); //weeds and empty buckets
            General.println("[Debug]: Inventory is full");
            RSItem[] grimyHerbs = Inventory.find(Filters.Items.nameContains("Grimy"));
            if (grimyHerbs.length > 0) {

                for (RSItem inv : grimyHerbs) {
                    if (inv.getDefinition().getID() != inv.getDefinition().getNotedItemID()) {
                        FarmVars.get().status = "Noting herbs";

                        if (Utils.useItemOnNPC(inv.getDefinition().getID(), "Tool Leprechaun")) {
                            NPCInteraction.waitForConversationWindow();
                            Utils.shortSleep();
                        }
                    }
                }
            }
        }
    }

    public void harvestAllAllotments(int... allotments) {
        for (int i : allotments) {
            harvestAllotment(i);
        }
    }

    public void harvestHerbsCatherby() {
        harvestHerbs(FarmConst.CATHERBY_HERB_PATCH_ID);
        harvestHerbs(FarmConst.CATHERBY_FLOWER_PATCH_ID);
    }

    public void harvestHerbsFalador() {
        harvestHerbs(FarmConst.FALADOR_HERB_PATCH_ID);
        harvestHerbs(FarmConst.FALADOR_FLOWER_PATCH_ID);
    }

    public void harvestHerbsArdougne() {
        harvestHerbs(FarmConst.ARDOUGNE_HERB_PATCH_ID);
        harvestHerbs(FarmConst.ARDOUGNE_FLOWER_PATCH_ID);
    }

    public void harvestHerbsHosidius() {
        harvestHerbs(FarmConst.HOISIDIUS_HERB_PATCH_ID);
        harvestHerbs(FarmConst.HOISIDIUS_FLOWER_PATCH_ID);
    }

    public void harvestHerbsMorytania() {
        harvestHerbs(FarmConst.MORYTANIA_HERB_PATCH_ID);
        harvestHerbs(FarmConst.MORYTANIA_FLOWER_PATCH_ID);
    }




    @Override
    public void execute() {
        FarmVars.get().status = "Harvesting.";
        if (FarmVars.get().doingTrees || FarmVars.get().doingFruitTrees) {
            harvestTree();
        } else if (FarmVars.get().doingAllotments) {
            harvestAllAllotments(
                    FarmConst.ARDOUGNE_N_ALLOTMENT_ID,
                    FarmConst.ARDOUGNE_S_ALLOTMENT_ID,
                    FarmConst.FALADOR_NW_ALLOTMENT_ID,
                    FarmConst.FALADOR_SE_ALLOTMENT_ID,
                    FarmConst.CATHERBY_N_ALLOTMENT_ID,
                    FarmConst.CATHERBY_S_ALLOTMENT_ID,
                    FarmConst.HOISIDIUS_NE_ALLOTMENT_ID,
                    FarmConst.HOISIDIUS_SW_ALLOTMENT_ID,
                    FarmConst.MORYTANIA_N_ALLOTMENT_ID,
                    FarmConst.MORYTANIA_S_ALLOTMENT_ID);
            if (harvestAllotmentMorytania(FarmConst.MORYTANIA_N_ALLOTMENT_ID)) {
                General.println("[Harvest]: Set allotments to false");
             //   FarmVars.get().doingAllotments = false;
                if (FarmVars.get().doingHerbs) {
                    FarmVars.get().shouldBank = true;
                    Move.populatePatchesToVisit();
                }
            }
        } else {
            noteHerbs();
            harvestHerbsCatherby();
            harvestHerbsArdougne();
            harvestHerbsFalador();
            harvestHerbsHosidius();
            harvestHerbsMorytania();
            harvestHerbs(FarmConst.FARMING_GUILD_HERB_PATCH_ID);
            harvestHerbs(FarmConst.FARMING_GUILD_FLOWER_PATCH_ID);
            noteHerbs();
            getGroundItems();
        }
    }


    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        if (Login.getLoginState() == Login.STATE.INGAME) {
            if (!FarmVars.get().shouldRestock && !FarmVars.get().shouldBank && !FarmVars.get().shouldBreak) {
                if (FarmVars.get().doingTrees || FarmVars.get().doingFruitTrees) {

                    RSObject[] herbs = Objects.findNearest(20, Filters.Objects.actionsContains("Check-health"));
                    return herbs.length > 0;

                } else if (FarmVars.get().doingAllotments) {
                    RSObject[] allotment = Objects.findNearest(20, Filters.Objects.actionsContains("Harvest"));
                    return allotment.length > 0;

                } else {
                    RSObject[] herbs = Objects.findNearest(20, Filters.Objects.actionsContains("Pick"));
                    return herbs.length > 0;
                }
            }
        }
        return false;
    }
}