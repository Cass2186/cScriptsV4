package scripts.Tasks.Farming.Data;

import org.tribot.api.General;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.Skills;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSObject;
import scripts.EntitySelector.Entities;
import scripts.EntitySelector.finders.prefabs.ObjectEntity;
import scripts.Tasks.Farming.Data.Enums.Patches;
import scripts.Timer;
import scripts.Utils;
import scripts.Varbits;

public class FarmingUtils {

    public static boolean canMoveToNextSpot(){
        if (FarmVars.get().doingHerbs)
            return getNearbyReadyHerbs().length ==0;

        else if (FarmVars.get().doingAllotments)
                return getNearbyReadyAllotments().length == 0;


        else if ( FarmVars.get().doingTrees)
            return getNearbyReadyTrees().length == 0 && getTreesToBeRemoved().length == 0;

        else if ( FarmVars.get().doingFruitTrees)
            return getNearbyReadyTrees().length == 0 && getTreesToBeRemoved().length == 0
                    && getFruitTreesToBeRemoved().length == 0;


        return true;
    }

    public static RSObject[] getNearbyReadyHerbs() {
       return Entities.find(ObjectEntity::new)
               .setDistance(15)
                .actionsContains("Pick")
                .actionsContains("Inspect")
                .getResults();

    }

    public static RSObject[] getNearbyReadyAllotments() {
        return Entities.find(ObjectEntity::new)
                .setDistance(15)
                .actionsContains("Harvest")
                .getResults();
    }

    // works for both fruit and regular trees
    public static RSObject[] getNearbyReadyTrees() {
        return Entities.find(ObjectEntity::new)
                .setDistance(15)
                .actionsContains("Check-health")
                .getResults();
    }

    public static RSObject[] getTreesToBeRemoved(){
        return Entities.find(ObjectEntity::new)
                .setDistance(15)
                .actionsContains("Chop down")
                .actionsContains("Guide")
                .getResults();
    }

    public static RSObject[] getFruitTreesToBeRemoved(){
        return Entities.find(ObjectEntity::new)
                .setDistance(15)
                .actionsContains("Pick-")
                .actionsContains("Guide")
                .getResults();
    }

    public static void populateFruitTreePatches() {
        FarmVars.get().patchesLeftToVisit.add(Patches.STRONGHOLD_FRUIT_TREE_PATCH);
        FarmVars.get().patchesLeftToVisit.add(Patches.TGV_FRUIT_TREE_PATCH);
        FarmVars.get().patchesLeftToVisit.add(Patches.BRIMHAVEN_FRUIT_TREE_PATCH);
        FarmVars.get().patchesLeftToVisit.add(Patches.CATHERBY_FRUIT_TREE_PATCH);

        if (Skills.getActualLevel(Skills.SKILLS.FARMING) > 85
                && Utils.getVarBitValue(Varbits.KOUREND_FAVOR_HOSIDIUS.value) > 600)
            FarmVars.get().patchesLeftToVisit.add(Patches.FARMING_GUILD_FRUIT_TREE_PATCH);
    }


    public static void populateAllotmentPatches() {
        FarmVars.get().patchesLeftToVisit.add(Patches.FALADOR_ALLOTMENT_PATCH);
        FarmVars.get().patchesLeftToVisit.add(Patches.CATHERBY_ALLOTMENT_PATCH);
        FarmVars.get().patchesLeftToVisit.add(Patches.ARDOUGNE_ALLOTMENT_PATCH);
        FarmVars.get().patchesLeftToVisit.add(Patches.HOSIDIUS_ALLOTMENT_PATCH);
        FarmVars.get().patchesLeftToVisit.add(Patches.MORYTANIA_ALLOTMENT_PATCH);
    }

    /**
     * call on start and after breaks
     */
    //TODO Change these to Patches Enum reference
    public static void populatePatchesToVisit() {
        FarmVars.get().patchesLeftToVisit.add(Patches.FALADOR_HERB_PATCH);
        FarmVars.get().patchesLeftToVisit.add(Patches.CATHERBY_HERB_PATCH);
        FarmVars.get().patchesLeftToVisit.add(Patches.ARDOUGNE_HERB_PATCH);
        FarmVars.get().patchesLeftToVisit.add(Patches.HOSIDIUS_HERB_PATCH);
        FarmVars.get().patchesLeftToVisit.add(Patches.MORYTANIA_HERB_PATCH);

        if (Skills.getActualLevel(Skills.SKILLS.FARMING) > 65
                && Utils.getVarBitValue(Varbits.KOUREND_FAVOR_HOSIDIUS.value) > 600)
            FarmVars.get().patchesLeftToVisit.add(Patches.FARMING_GUILD_HERB_PATCH);
    }

    public static void populateTreePatches() {
        FarmVars.get().patchesLeftToVisit.add(Patches.LUMBRIDGE_TREE_PATCH);
        FarmVars.get().patchesLeftToVisit.add(Patches.FALADOR_TREE_PATCH);
        FarmVars.get().patchesLeftToVisit.add(Patches.TAVERLY_TREE_PATCH);
        FarmVars.get().patchesLeftToVisit.add(Patches.VARROCK_TREE_PATCH);
        FarmVars.get().patchesLeftToVisit.add(Patches.STRONGHOLD_TREE_PATCH);
        if (Skills.getActualLevel(Skills.SKILLS.FARMING) > 65
                && Utils.getVarBitValue(Varbits.KOUREND_FAVOR_HOSIDIUS.value) > 600)
            FarmVars.get().patchesLeftToVisit.add(Patches.FARMING_GUILD_TREE_PATCH);

        General.println("[Move]: Populating tree patches - Number: " + FarmVars.get().patchesLeftToVisit.size());
    }
    public static void rakeWeeds(int patchId) {
        RSObject[] weeds = Objects.findNearest(20,
                Filters.Objects.actionsContains("Rake").and(Filters.Objects.idEquals(patchId)));
        if (weeds.length > 0) {
            FarmVars.get().status = "Raking weeds";
            if (Utils.clickObject(patchId, "Rake", false)) {
                Timer.waitCondition(() -> Objects.findNearest(20,
                        Filters.Objects.actionsContains("Rake").and(Filters.Objects.idEquals(patchId))).length <
                        weeds.length, 20000, 30000);
            }
        }
    }

    public static void clearDeadPlants(int patchId) {
        RSObject[] weeds = Objects.findNearest(20, Filters.Objects.actionsContains("Clear").and(Filters.Objects.idEquals(patchId)));
        if (weeds.length > 0) {
            FarmVars.get().status = "Clearing dead plants";
            if (Utils.clickObject(patchId, "Clear", false)) {
                Timer.waitCondition(() -> Player.getAnimation() != -1, 5000, 7000);
                Timer.waitCondition(() -> Objects.findNearest(20, Filters.Objects.actionsContains("Clear").and(Filters.Objects.idEquals(patchId))).length <
                        weeds.length, 20000, 30000);
            }
        }
    }

    public static void useCompost(int patchId) {
        RSObject[] patch = Objects.findNearest(20, Filters.Objects.nameContains("Herb patch").and(Filters.Objects.idEquals(patchId)));
        if (patch.length > 0) {
            FarmVars.get().status = "Using Ultracompost";
            if (FarmVars.get().usingBottomless || Inventory.find(FarmConst.BOTTOMLESS_COMPOST).length > 0) {
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

    public static void useCompostAllotment(int patchId) {
        RSObject[] patch = Objects.findNearest(20, Filters.Objects.nameContains("Allotment").
                and(Filters.Objects.idEquals(patchId)));
        if (patch.length > 0) {
            General.println("[Planting]: Adding compost");
            FarmVars.get().status = "Using Ultracompost";
            if (FarmVars.get().usingBottomless || Inventory.find(FarmConst.BOTTOMLESS_COMPOST).length > 0) {
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


    public static void useCompostTree(int patchId) {
        RSObject[] patch = Objects.findNearest(20, Filters.Objects.nameContains("Tree patch").and(Filters.Objects.idEquals(patchId)));
        if (patch.length > 0) {
            FarmVars.get().status = "Using Ultracompost";
            if (Inventory.find(FarmConst.BOTTOMLESS_COMPOST).length > 0) {
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

}
