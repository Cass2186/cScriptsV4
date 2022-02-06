package scripts.Tasks.Farming.FarmTasks;


import org.tribot.api.General;
import org.tribot.api2007.Login;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Skills;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSObject;
import org.tribot.script.sdk.Log;

import scripts.Data.Const;
import scripts.Data.Vars;
import scripts.PathingUtil;
import scripts.Tasks.Farming.Data.Areas;
import scripts.Tasks.Farming.Data.Enums.Patches;
import scripts.Tasks.Farming.Data.FarmConst;
import scripts.Tasks.Farming.Data.FarmVars;
import scripts.Tasks.Farming.Data.FarmingUtils;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;
import scripts.Utils;
import scripts.Varbits;

import java.awt.*;

public class Move implements Task {

    private static boolean checkForPatchId(int patchId, int flowerPatchId) {
        RSObject[] obj = Objects.findNearest(20, Filters.Objects.idEquals(patchId));
        RSObject[] obj2 = Objects.findNearest(20, Filters.Objects.idEquals(flowerPatchId));
        return (obj.length > 0 || obj2.length > 0);
    }

    private static boolean checkForTreePatchId(int patchId) {
        RSObject[] obj = Objects.findNearest(20, Filters.Objects.idEquals(patchId));
        Log.log("[Move]: Are we near a tree patch with ID " + patchId + "  " +(obj.length > 0));
        return (obj.length > 0);
    }

    private static boolean checkForObj(String action, int patchId) {
        RSObject[] obj = Objects.findNearest(10, Filters.Objects.actionsContains(action).
                and(Filters.Objects.idEquals(patchId)).and(Filters.Objects.actionsNotContains("Pick")));
        //General.println("[Move]: Check for obj length = " + obj.length);
        return (obj.length > 0);
    }

    private static boolean checkForObj(String action, int patchId, String name) {
        RSObject[] obj = Objects.findNearest(10, Filters.Objects.actionsContains(action).
                and(Filters.Objects.idEquals(patchId)).and(Filters.Objects.actionsNotContains("Pick"))
                .and(Filters.Objects.nameContains(name)));
        //General.println("[Move]: Check for obj length = " + obj.length);
        return (obj.length > 0);
    }

    private static boolean shouldMoveFromPatch(int patchId) {
        return checkForObj("Inspect", patchId);
    }

    private static boolean shouldMoveFromTree(int patchId) {
        return (checkForObj("Inspect", patchId)
                && !checkForObj("Check-health", patchId)
                && !checkForObj("Rake", patchId));
    }

    private static boolean shouldMoveFromAllotment(int patchId) {
        return (checkForObj("Inspect", patchId) &&
                !checkForObj("Inspect", patchId, "Allotment")
                && !checkForObj("Harvest", patchId));
    }


    /**
     * call on start and after breaks
     */
    public static void populatePatchesToVisit() {
        FarmVars.get().patchesLeftToVisit.add(FarmConst.faladorPatchObj);
        FarmVars.get().patchesLeftToVisit.add(FarmConst.catherbyPatchObj);
        FarmVars.get().patchesLeftToVisit.add(FarmConst.ardougnePatchObj);
        FarmVars.get().patchesLeftToVisit.add(FarmConst.hosidiusPatchObj);
        FarmVars.get().patchesLeftToVisit.add(FarmConst.morytaniaPatchObj);

        if (Skills.getActualLevel(Skills.SKILLS.FARMING) > 65
                && Utils.getVarBitValue(Varbits.KOUREND_FAVOR_HOSIDIUS.getId()) > 600)
            FarmVars.get().patchesLeftToVisit.add(FarmConst.guildPatchObj);
    }

    public static void populateTreePatches() {

        FarmVars.get().patchesLeftToVisit.add(Patches.LUMBRIDGE_TREE_PATCH);
        FarmVars.get().patchesLeftToVisit.add(Patches.FALADOR_TREE_PATCH);
        FarmVars.get().patchesLeftToVisit.add(Patches.TAVERLY_TREE_PATCH);
        FarmVars.get().patchesLeftToVisit.add(Patches.VARROCK_TREE_PATCH);
        FarmVars.get().patchesLeftToVisit.add(Patches.STRONGHOLD_TREE_PATCH);
        if (Skills.getActualLevel(Skills.SKILLS.FARMING) > 65
                && Utils.getVarBitValue(Varbits.KOUREND_FAVOR_HOSIDIUS.getId()) > 600)
            FarmVars.get().patchesLeftToVisit.add(Patches.FARMING_GUILD_TREE_PATCH);

        General.println("[Move]: Populating tree patches - Number: " + FarmVars.get().patchesLeftToVisit.size());
    }

    /*public static void populateFruitTreePatches() {
        FarmVars.get().patchesLeftToVisit.add(FarmConst.strongholdFruitTreeObj);
        FarmVars.get().patchesLeftToVisit.add(FarmConst.tgvFruitTreePatchObj);
        FarmVars.get().patchesLeftToVisit.add(FarmConst.brimHavenTreePatchObj);
        FarmVars.get().patchesLeftToVisit.add(FarmConst.catherbyFruitTreePatchObj);

        if (Skills.getActualLevel(Skills.SKILLS.FARMING) > 85
                && Utils.getVarBitValue(Varbits.KOUREND_FAVOR_HOSIDIUS.value) > 600)
            FarmVars.get().patchesLeftToVisit.add(FarmConst.guildTreePatchObj);
    }
*/
    public static boolean nearTreeSpot() {
        if (FarmVars.get().doingTrees) {
            return checkForTreePatchId(FarmConst.FALADOR_TREE_PATCH_ID) ||
                    checkForTreePatchId(FarmConst.TAVERLY_TREE_PATCH_ID) ||
                    checkForTreePatchId(FarmConst.LUMBRIDGE_TREE_PATCH_ID) ||
                    checkForTreePatchId(FarmConst.VAROCK_TREE_PATCH_ID) ||
                    checkForTreePatchId(FarmConst.GNOME_STRONGHOLD_TREE_PATCH_ID) ||
                    checkForTreePatchId(FarmConst.FARMING_GUILD_TREE_PATCH_ID);
        };
        return false;
    }

    public static boolean nearAllotmentSpot() {
        if (FarmVars.get().doingAllotments) {
            return checkForPatchId(FarmConst.FALADOR_HERB_PATCH_ID, FarmConst.FALADOR_FLOWER_PATCH_ID) ||
                    checkForPatchId(FarmConst.CATHERBY_HERB_PATCH_ID, FarmConst.CATHERBY_FLOWER_PATCH_ID) ||
                    checkForPatchId(FarmConst.MORYTANIA_HERB_PATCH_ID, FarmConst.MORYTANIA_FLOWER_PATCH_ID)
                    || checkForPatchId(FarmConst.ARDOUGNE_HERB_PATCH_ID, FarmConst.ARDOUGNE_FLOWER_PATCH_ID)
                    || checkForPatchId(FarmConst.HOISIDIUS_HERB_PATCH_ID, FarmConst.HOISIDIUS_FLOWER_PATCH_ID)
                    || checkForPatchId(FarmConst.FARMING_GUILD_HERB_PATCH_ID, FarmConst.FARMING_GUILD_FLOWER_PATCH_ID);
        }
        return false;
    }

    public static boolean determineLocation() {
        if (FarmVars.get().doingTrees) {
            if (checkForTreePatchId(FarmConst.FALADOR_TREE_PATCH_ID)) {
                General.println("[Debug]: We are at Falador Tree Patch");
                if (shouldMoveFromTree(FarmConst.FALADOR_TREE_PATCH_ID)) {
                    General.println("[Debug]: Removed Falador Tree patch from list");
                    // remove it from list of patches to visit
                    FarmVars.get().patchesLeftToVisit.remove(Patches.FALADOR_TREE_PATCH);
                    // start a timer here
                    return true;
                }
            }
            if (checkForTreePatchId(FarmConst.TAVERLY_TREE_PATCH_ID)) {
                General.println("[Debug]: We are at Taverly Tree Patch");
                if (shouldMoveFromTree(FarmConst.TAVERLY_TREE_PATCH_ID)) {
                    General.println("[Debug]: Removed Taverly Tree patch from list");
                    FarmVars.get().patchesLeftToVisit.remove(Patches.TAVERLY_TREE_PATCH);
                    return true;
                }
            }
            if (checkForTreePatchId(FarmConst.LUMBRIDGE_TREE_PATCH_ID)) {
                General.println("[Debug]: We are at Lumbridge Tree Patch");
                if (shouldMoveFromTree(FarmConst.LUMBRIDGE_TREE_PATCH_ID)) {
                    General.println("[Debug]: Removed Lumbridge Tree patch from list");
                    FarmVars.get().patchesLeftToVisit.remove(Patches.LUMBRIDGE_TREE_PATCH);
                    return true;
                }
            }
            if (checkForTreePatchId(FarmConst.VAROCK_TREE_PATCH_ID)) {
                General.println("[Debug]: We are at Varrock Tree Patch");
                if (shouldMoveFromTree(FarmConst.VAROCK_TREE_PATCH_ID)) {
                    General.println("[Debug]: Removed Varrock Tree patch from list");
                    FarmVars.get().patchesLeftToVisit.remove(Patches.VARROCK_TREE_PATCH);
                    return true;
                }
            }
            if (checkForTreePatchId(FarmConst.GNOME_STRONGHOLD_TREE_PATCH_ID)) {
                General.println("[Debug]: We are at Gnome Tree Patch");
                if (shouldMoveFromTree(FarmConst.GNOME_STRONGHOLD_TREE_PATCH_ID)) {
                    General.println("[Debug]: Removed Gnome Tree patch from list");
                    FarmVars.get().patchesLeftToVisit.remove(Patches.STRONGHOLD_TREE_PATCH);
                    return true;
                }
            }
            if (checkForTreePatchId(FarmConst.FARMING_GUILD_TREE_PATCH_ID)) {
                General.println("[Debug]: We are at Farming guild Tree Patch");
                if (shouldMoveFromTree(FarmConst.FARMING_GUILD_TREE_PATCH_ID)) {
                    General.println("[Debug]: Removed Farming guild patch from list");
                    FarmVars.get().patchesLeftToVisit.remove(Patches.FARMING_GUILD_TREE_PATCH);
                    return true;
                }
            }
            /**
             * FRUIT TREES
             */
        } else if (FarmVars.get().doingFruitTrees) {
            if (checkForTreePatchId(FarmConst.STRONGHOLD_FRUIT_TREE_ID)) {
                General.println("[Debug]: We are at Stronghold Fruit Tree Patch");
                if (shouldMoveFromTree(FarmConst.STRONGHOLD_FRUIT_TREE_ID)) {
                    General.println("[Debug]: Removed Stronghold Fruit Tree patch from list");
                //    FarmVars.get().patchesLeftToVisit.remove(FarmConst.strongholdFruitTreeObj);
                    return true;
                }
            } else if (checkForTreePatchId(FarmConst.TGV_FRUIT_TREE_ID)) {
                General.println("[Debug]: We are at TGV Fruit Tree Patch");
                if (shouldMoveFromTree(FarmConst.TGV_FRUIT_TREE_ID)) {
                    General.println("[Debug]: Removed TGV Fruit Tree patch from list");
                 //   FarmVars.get().patchesLeftToVisit.remove(FarmConst.tgvFruitTreePatchObj);
                    return true;
                }
            } else if (checkForTreePatchId(FarmConst.BRIMHAVEN_FRUIT_TREE_ID)) {
                General.println("[Debug]: We are at Brimhaven Fruit Tree Patch");
                if (shouldMoveFromTree(FarmConst.BRIMHAVEN_FRUIT_TREE_ID)) {
                    General.println("[Debug]: Removed Brimhaven Fruit Tree patch from list");
                   // FarmVars.get().patchesLeftToVisit.remove(FarmConst.brimHavenTreePatchObj);
                    return true;
                }
            } else if (checkForTreePatchId(FarmConst.CATHERBY_FRUIT_TREE_ID)) {
                General.println("[Debug]: We are at Catherby Fruit Tree Patch");
                if (shouldMoveFromTree(FarmConst.CATHERBY_FRUIT_TREE_ID)) {
                    General.println("[Debug]: Removed Catherby Fruit Tree patch from list");
                  //  FarmVars.get().patchesLeftToVisit.remove(FarmConst.catherbyFruitTreePatchObj);
                    return true;
                }
            }
        }


        /**
         *  HERBS
         */

        if (!FarmVars.get().doingAllotments) {
            if (checkForPatchId(FarmConst.FALADOR_HERB_PATCH_ID, FarmConst.FALADOR_FLOWER_PATCH_ID)) {
                General.println("[Debug]: We are at Falador patch");
                if (shouldMoveFromPatch(FarmConst.FALADOR_HERB_PATCH_ID)) {
                    General.println("[Debug]: Removed Falador patch from list");
                    // remove it from list of patches to visit
                    FarmVars.get().patchesLeftToVisit.remove(FarmConst.faladorPatchObj);
                    // start a timer here
                    return true;
                }

            } else if (checkForPatchId(FarmConst.CATHERBY_HERB_PATCH_ID, FarmConst.CATHERBY_FLOWER_PATCH_ID)) {
                // we are at falador patches
                if (shouldMoveFromPatch(FarmConst.CATHERBY_HERB_PATCH_ID)) {
                    // remove it from list of patches to visit
                    FarmVars.get().patchesLeftToVisit.remove(FarmConst.catherbyPatchObj);
                    // start a timer here
                    return true;
                }

            } else if (checkForPatchId(FarmConst.MORYTANIA_HERB_PATCH_ID, FarmConst.MORYTANIA_FLOWER_PATCH_ID)) {
                // we are at falador patches
                if (shouldMoveFromPatch(FarmConst.MORYTANIA_HERB_PATCH_ID)) {
                    // remove it from list of patches to visit
                    FarmVars.get().patchesLeftToVisit.remove(FarmConst.morytaniaPatchObj);
                    // start a timer here
                    return true;
                }

            } else if (checkForPatchId(FarmConst.ARDOUGNE_HERB_PATCH_ID, FarmConst.ARDOUGNE_FLOWER_PATCH_ID)) {
                // we are at falador patches
                if (shouldMoveFromPatch(FarmConst.ARDOUGNE_HERB_PATCH_ID)) {
                    // remove it from list of patches to visit
                    FarmVars.get().patchesLeftToVisit.remove(FarmConst.ardougnePatchObj);
                    // start a timer here
                    return true;
                }

            } else if (checkForPatchId(FarmConst.HOISIDIUS_HERB_PATCH_ID, FarmConst.HOISIDIUS_FLOWER_PATCH_ID)) {
                // we are at Hosa patches
                if (shouldMoveFromPatch(FarmConst.HOISIDIUS_HERB_PATCH_ID)) {
                    // remove it from list of patches to visit
                    FarmVars.get().patchesLeftToVisit.remove(FarmConst.hosidiusPatchObj);
                    // start a timer here
                    return true;
                }
            } else if (checkForPatchId(FarmConst.FARMING_GUILD_HERB_PATCH_ID, FarmConst.FARMING_GUILD_FLOWER_PATCH_ID)) {
                // we are at farming guild patches
                if (shouldMoveFromPatch(FarmConst.FARMING_GUILD_HERB_PATCH_ID)) {
                    // remove it from list of patches to visit
                    FarmVars.get().patchesLeftToVisit.remove(FarmConst.guildPatchObj);
                    // start a timer here
                    return true;
                }
            } else if (FarmVars.get().starting) {
                General.println("[Debug]: DetermineLocation() returned true");
                FarmVars.get().starting = false;
                return true;
            }
        } else {
            if (checkForPatchId(FarmConst.FALADOR_HERB_PATCH_ID, FarmConst.FALADOR_FLOWER_PATCH_ID)) {
                General.println("[Debug]: We are at Falador patch");
                if (shouldMoveFromAllotment(FarmConst.FALADOR_NW_ALLOTMENT_ID)
                        && shouldMoveFromAllotment(FarmConst.FALADOR_SE_ALLOTMENT_ID)) {
                    General.println("[Debug]: Removed Falador patch from list");
                    // remove it from list of patches to visit
                    FarmVars.get().patchesLeftToVisit.remove(FarmConst.faladorPatchObj);
                    // start a timer here
                    return true;
                }

            } else if (checkForPatchId(FarmConst.CATHERBY_HERB_PATCH_ID, FarmConst.CATHERBY_FLOWER_PATCH_ID)) {
                // we are at falador patches
                if (shouldMoveFromAllotment(FarmConst.CATHERBY_N_ALLOTMENT_ID)
                        && shouldMoveFromAllotment(FarmConst.CATHERBY_S_ALLOTMENT_ID)) {
                    // remove it from list of patches to visit
                    FarmVars.get().patchesLeftToVisit.remove(FarmConst.catherbyPatchObj);
                    // start a timer here
                    return true;
                }

            } else if (checkForPatchId(FarmConst.MORYTANIA_HERB_PATCH_ID,
                    FarmConst.MORYTANIA_FLOWER_PATCH_ID)) {
                // we are at falador patches
                if (shouldMoveFromPatch(FarmConst.MORYTANIA_HERB_PATCH_ID)) {
                    // remove it from list of patches to visit
                    FarmVars.get().patchesLeftToVisit.remove(FarmConst.morytaniaPatchObj);

                    return true;
                }

            } else if (checkForPatchId(FarmConst.ARDOUGNE_HERB_PATCH_ID, FarmConst.ARDOUGNE_FLOWER_PATCH_ID)) {
                // we are at falador patches
                if (shouldMoveFromAllotment(FarmConst.ARDOUGNE_N_ALLOTMENT_ID)
                        && shouldMoveFromAllotment(FarmConst.ARDOUGNE_S_ALLOTMENT_ID)) {
                    // remove it from list of patches to visit
                    FarmVars.get().patchesLeftToVisit.remove(FarmConst.ardougnePatchObj);
                    // start a timer here
                    return true;
                }

            } else if (checkForPatchId(FarmConst.HOISIDIUS_HERB_PATCH_ID, FarmConst.HOISIDIUS_FLOWER_PATCH_ID)) {
                // we are at Hosa patches
                if (shouldMoveFromAllotment(FarmConst.HOISIDIUS_SW_ALLOTMENT_ID)
                        && shouldMoveFromAllotment(FarmConst.HOISIDIUS_NE_ALLOTMENT_ID)) {
                    // remove it from list of patches to visit
                    FarmVars.get().patchesLeftToVisit.remove(FarmConst.hosidiusPatchObj);
                    General.println("[Move]: Starting herb timer", Color.RED);
                    // FarmVars.get().herbTimer = new Timer(4960000); // 80min
                    return true;
                }
            } else if (FarmVars.get().starting) {
                // General.println("[Debug]: DetermineLocation() returned true");
                FarmVars.get().starting = false;
                return true;
            }
        }
        General.println("[Debug]: DetermineLocation() returned false");
        return false;
    }

    private Patches determineNextPatch() {
        determineLocation();
        if (FarmVars.get().patchesLeftToVisit.size() == 0) {
            General.println("[Move]: Should break - no more patches");
            FarmVars.get().shouldBreak = true;
            return null;
        }
        General.println("Size = " + FarmVars.get().patchesLeftToVisit.size());
        Patches ptch = FarmVars.get().patchesLeftToVisit.get(0);
        return FarmVars.get().patchesLeftToVisit.get(0);
    }

    public void move() {
        /**
         * Trees
         */
        Patches ptch = determineNextPatch();
        if (ptch != null) {
            if (FarmVars.get().doingTrees) {
                General.println("[Move]: Line 372");
                PathingUtil.walkToArea(ptch.getPatchArea(), false);
                /**
                 * ALLOTMENTS
                 */
            } else if (FarmVars.get().doingFruitTrees) {
                if (ptch.getPatchIds().get(0) == FarmConst.STRONGHOLD_FRUIT_TREE_ID) {
                    FarmVars.get().status = "Going to Stronghold fruit Tree Patch";
                    PathingUtil.walkToArea(Areas.STRONGHOLD_FRUIT_TREE_AREA);
                } else if (ptch.getPatchIds().get(0) == FarmConst.TGV_FRUIT_TREE_ID) {
                    FarmVars.get().status = "Going to TGV Fruit Tree Patch";
                    PathingUtil.walkToArea(Areas.TGV_FRUIT_TREE_AREA);
                } else if (ptch.getPatchIds().get(0) == FarmConst.BRIMHAVEN_FRUIT_TREE_ID) {
                    FarmVars.get().status = "Going to Brimhaven fruit Tree Patch";
                    PathingUtil.walkToArea(Areas.BRIMHAVEN_FRUIT_TREE_AREA, false);
                } else if (ptch.getPatchIds().get(0) == FarmConst.CATHERBY_FRUIT_TREE_ID) {
                    FarmVars.get().status = "Going to Catherby fruit Tree Patch";
                    PathingUtil.walkToArea(Areas.CATHERBY_FRUIT_TREE_AREA, false);
                } else {
                    General.println("[Move]: Error determining next tree patch");
                }

                /**
                 * ALLOTMENTS
                 */
            } else
                PathingUtil.walkToArea(ptch.getPatchArea(), false);


        } else {
            // logout and break
            FarmVars.get().shouldRestock = false;
            FarmVars.get().shouldBank = false;
            General.println("[Move Node]: Breaking");
            Utils.longSleep();
            FarmVars.get().shouldBreak = true;
            //  Main.scriptRef.setLoginBotState(false);

        }

    }


    @Override
    public void execute() {
        General.println(!FarmVars.get().shouldRestock
                && !FarmVars.get().shouldBank
                && !FarmVars.get().shouldBreak);
        move();
    }

    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        RSObject[] trees = Objects.findNearest(20, Filters.Objects.actionsContains("Chop down").
                or(Filters.Objects.actionsContains("Pick-")).
                and(Filters.Objects.actionsContains("Guide")//guide filters out random trees
                        .and(Filters.Objects.actionsNotContains("Rake"))));

        if (Login.getLoginState() == Login.STATE.INGAME)
            return !FarmVars.get().shouldRestock
                    && !FarmVars.get().shouldBank
                    && !FarmVars.get().shouldBreak
                    && determineLocation()
                    && trees.length == 0;

        return false;
    }
}
