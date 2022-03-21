package scripts.Data;

import org.tribot.api.General;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSObject;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.GameObject;
import scripts.Data.Enums.Patches;
import scripts.EntitySelector.Entities;
import scripts.EntitySelector.finders.prefabs.ObjectEntity;
import scripts.Utils;
import scripts.Varbits;

import java.util.Optional;

public class FarmingUtils {

    public static boolean canMoveToNextSpot() {
        if (Vars.get().doingHerbs)
            return getNearbyReadyHerbs().length == 0;

        else if (Vars.get().doingAllotments)
            return getNearbyReadyAllotments().length == 0;


        else if (Vars.get().doingTrees)
            return getNearbyReadyTrees().length == 0 && getTreesToBeRemoved().length == 0;

        else if (Vars.get().doingFruitTrees)
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

    public static RSObject[] getTreesToBeRemoved() {
        return Entities.find(ObjectEntity::new)
                .setDistance(15)
                .actionsContains("Chop down")
                .actionsContains("Guide")
                .getResults();
    }

    public static RSObject[] getFruitTreesToBeRemoved() {
        return Entities.find(ObjectEntity::new)
                .setDistance(15)
                .actionsContains("Pick-")
                .actionsContains("Guide")
                .getResults();
    }

    /* public static Optional<GameObject> getNearbyReadyHerbs() {
        return  Query.gameObjects()
                .maxDistance(15)
                .actionContains("Pick")
                .actionContains("Inspect")
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

    }*/

    public static void populateFruitTreePatches() {
        Vars.get().patchesLeftToVisit.add(Patches.STRONGHOLD_FRUIT_TREE_PATCH);
        Vars.get().patchesLeftToVisit.add(Patches.TGV_FRUIT_TREE_PATCH);
        Vars.get().patchesLeftToVisit.add(Patches.BRIMHAVEN_FRUIT_TREE_PATCH);
        Vars.get().patchesLeftToVisit.add(Patches.CATHERBY_FRUIT_TREE_PATCH);

        if (Skills.getActualLevel(Skills.SKILLS.FARMING) > 85
                && Utils.getVarBitValue(Varbits.KOUREND_FAVOR_HOSIDIUS.getId()) > 600)
            Vars.get().patchesLeftToVisit.add(Patches.FARMING_GUILD_FRUIT_TREE_PATCH);
    }


    public static void populateAllotmentPatches() {
        Vars.get().patchesLeftToVisit.add(Patches.FALADOR_ALLOTMENT_PATCH);
        Vars.get().patchesLeftToVisit.add(Patches.CATHERBY_ALLOTMENT_PATCH);
        Vars.get().patchesLeftToVisit.add(Patches.ARDOUGNE_ALLOTMENT_PATCH);
        Vars.get().patchesLeftToVisit.add(Patches.HOSIDIUS_ALLOTMENT_PATCH);
        Vars.get().patchesLeftToVisit.add(Patches.MORYTANIA_ALLOTMENT_PATCH);
    }

    /**
     * call on start and after breaks
     */
    //TODO Change these to Patches Enum reference
    public static void populatePatchesToVisit() {
        Vars.get().patchesLeftToVisit.add(Patches.FALADOR_HERB_PATCH);
        Vars.get().patchesLeftToVisit.add(Patches.CATHERBY_HERB_PATCH);
        Vars.get().patchesLeftToVisit.add(Patches.ARDOUGNE_HERB_PATCH);
        Vars.get().patchesLeftToVisit.add(Patches.HOSIDIUS_HERB_PATCH);
        Vars.get().patchesLeftToVisit.add(Patches.MORYTANIA_HERB_PATCH);

        if (Skills.getActualLevel(Skills.SKILLS.FARMING) > 65
                && Utils.getVarBitValue(Varbits.KOUREND_FAVOR_HOSIDIUS.getId()) > 600)
            Vars.get().patchesLeftToVisit.add(Patches.FARMING_GUILD_HERB_PATCH);
    }

    public static void populateTreePatches() {
        Vars.get().patchesLeftToVisit.add(Patches.LUMBRIDGE_TREE_PATCH);
        Vars.get().patchesLeftToVisit.add(Patches.FALADOR_TREE_PATCH);
        Vars.get().patchesLeftToVisit.add(Patches.TAVERLY_TREE_PATCH);
        Vars.get().patchesLeftToVisit.add(Patches.VARROCK_TREE_PATCH);
        Vars.get().patchesLeftToVisit.add(Patches.STRONGHOLD_TREE_PATCH);
        if (Skills.getActualLevel(Skills.SKILLS.FARMING) > 65
                && Utils.getVarBitValue(Varbits.KOUREND_FAVOR_HOSIDIUS.getId()) > 600)
            Vars.get().patchesLeftToVisit.add(Patches.FARMING_GUILD_TREE_PATCH);

        General.println("[Move]: Populating tree patches - Number: " + Vars.get().patchesLeftToVisit.size());
    }


}
