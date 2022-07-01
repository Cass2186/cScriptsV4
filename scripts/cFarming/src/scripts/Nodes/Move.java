package scripts.Nodes;


import org.tribot.api.General;
import org.tribot.api2007.Login;
import org.tribot.api2007.Objects;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSObject;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.query.Query;
import scripts.Data.*;

import scripts.Data.Enums.Patches;
import scripts.PathingUtil;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;

import scripts.Utils;


import java.awt.*;

public class Move implements Task {

    private static boolean checkForPatchId(int patchId, int flowerPatchId) {
        return Query.gameObjects().idEquals(patchId).isAny() ||
                Query.gameObjects().idEquals(flowerPatchId).isAny();
    }

    private static boolean checkForTreePatchId(int patchId) {
        Log.info("[Move]: Are we near a tree patch with ID " + patchId + "  " + ( Query.gameObjects()
                .idEquals(patchId).isAny()));
        return ( Query.gameObjects().idEquals(patchId).isAny());
    }

    private static boolean checkForObj(String action, int patchId) {
        return Query.gameObjects()
                .idEquals(patchId)
                .actionNotContains("Pick")
                .maxDistance(10)
                .actionContains(action).isAny();
    }

    private static boolean checkForObj(String name, int patchId, String... actions) {
        return Query.gameObjects()
                .idEquals(patchId)
                .nameContains(name)
                .actionNotContains("Pick")
                .maxDistance(10)
                .actionContains(actions)
                .isAny();
    }



    private static boolean shouldMoveFromPatch(int patchId) {
        return checkForObj("Inspect", patchId);
    }

    private static boolean shouldMoveFromTree(int patchId) {
        return (checkForObj("Inspect", patchId)
                && !checkForObj("Clear", patchId)
                && !checkForObj("Check-health", patchId)
                && !checkForObj("Rake", patchId));
    }

    private static boolean shouldMoveFromAllotment(int patchId) {
        return (checkForObj("Inspect", patchId) &&
                !checkForObj("Inspect", patchId, "Allotment")
                && !checkForObj("Harvest", patchId));
    }

    public static boolean shouldMoveFromHerb(int patchId) {
        return (checkForObj("Inspect", patchId) &&
                !checkForObj("Inspect", patchId, "Allotment")
                && !checkForObj("Harvest", patchId));
    }


    public static boolean nearTreeSpot() {
        if (Vars.get().doingTrees) {
            return checkForTreePatchId(Const.FALADOR_TREE_PATCH_ID) ||
                    checkForTreePatchId(Const.TAVERLY_TREE_PATCH_ID) ||
                    checkForTreePatchId(Const.LUMBRIDGE_TREE_PATCH_ID) ||
                    checkForTreePatchId(Const.VAROCK_TREE_PATCH_ID) ||
                    checkForTreePatchId(Const.GNOME_STRONGHOLD_TREE_PATCH_ID) ||
                    checkForTreePatchId(Const.FARMING_GUILD_TREE_PATCH_ID);
        }
        ;
        return false;
    }

    public static boolean nearAllotmentSpot() {
        if (Vars.get().doingAllotments || Vars.get().doingHerbs) {
            return checkForPatchId(Const.FALADOR_HERB_PATCH_ID, Const.FALADOR_FLOWER_PATCH_ID) ||
                    checkForPatchId(Const.CATHERBY_HERB_PATCH_ID, Const.CATHERBY_FLOWER_PATCH_ID) ||
                    checkForPatchId(Const.MORYTANIA_HERB_PATCH_ID, Const.MORYTANIA_FLOWER_PATCH_ID)
                    || checkForPatchId(Const.ARDOUGNE_HERB_PATCH_ID, Const.ARDOUGNE_FLOWER_PATCH_ID)
                    || checkForPatchId(Const.HOISIDIUS_HERB_PATCH_ID, Const.HOISIDIUS_FLOWER_PATCH_ID)
                    || checkForPatchId(Const.FARMING_GUILD_HERB_PATCH_ID, Const.FARMING_GUILD_FLOWER_PATCH_ID);
        }
        return false;
    }

    private boolean determineLocation() {
        if (Vars.get().doingTrees) {
            if (checkForTreePatchId(Const.FALADOR_TREE_PATCH_ID)) {
                General.println("[Debug]: We are at Falador Tree Patch");
                if (shouldMoveFromTree(Const.FALADOR_TREE_PATCH_ID)) {
                    General.println("[Debug]: Removed Falador Tree patch from list");
                    // remove it from list of patches to visit
                    Vars.get().patchesLeftToVisit.remove(Patches.FALADOR_TREE_PATCH);
                    // start a timer here
                    return true;
                }
            }
            if (checkForTreePatchId(Const.TAVERLY_TREE_PATCH_ID)) {
                General.println("[Debug]: We are at Taverly Tree Patch");
                if (shouldMoveFromTree(Const.TAVERLY_TREE_PATCH_ID)) {
                    General.println("[Debug]: Removed Taverly Tree patch from list");
                    Vars.get().patchesLeftToVisit.remove(Patches.TAVERLY_TREE_PATCH);
                    return true;
                }
            }
            if (checkForTreePatchId(Const.LUMBRIDGE_TREE_PATCH_ID)) {
                General.println("[Debug]: We are at Lumbridge Tree Patch");
                if (shouldMoveFromTree(Const.LUMBRIDGE_TREE_PATCH_ID)) {
                    General.println("[Debug]: Removed Lumbridge Tree patch from list");
                    Vars.get().patchesLeftToVisit.remove(Patches.LUMBRIDGE_TREE_PATCH);
                    return true;
                }
            }
            if (checkForTreePatchId(Const.VAROCK_TREE_PATCH_ID)) {
                General.println("[Debug]: We are at Varrock Tree Patch");
                if (shouldMoveFromTree(Const.VAROCK_TREE_PATCH_ID)) {
                    General.println("[Debug]: Removed Varrock Tree patch from list");
                    Vars.get().patchesLeftToVisit.remove(Patches.VARROCK_TREE_PATCH);
                    return true;
                }
            }
            if (checkForTreePatchId(Const.GNOME_STRONGHOLD_TREE_PATCH_ID)) {
                General.println("[Debug]: We are at Gnome Tree Patch");
                if (shouldMoveFromTree(Const.GNOME_STRONGHOLD_TREE_PATCH_ID)) {
                    General.println("[Debug]: Removed Gnome Tree patch from list");
                    Vars.get().patchesLeftToVisit.remove(Patches.STRONGHOLD_TREE_PATCH);
                    return true;
                }
            }
            if (checkForTreePatchId(Const.FARMING_GUILD_TREE_PATCH_ID)) {
                General.println("[Debug]: We are at Farming guild Tree Patch");
                if (shouldMoveFromTree(Const.FARMING_GUILD_TREE_PATCH_ID)) {
                    General.println("[Debug]: Removed Farming guild patch from list");
                    Vars.get().patchesLeftToVisit.remove(Patches.FARMING_GUILD_TREE_PATCH);
                    return true;
                }
            }
            /**
             * FRUIT TREES
             */
        } else if (Vars.get().doingFruitTrees) {
            if (checkForTreePatchId(Const.STRONGHOLD_FRUIT_TREE_ID)) {
                General.println("[Debug]: We are at Stronghold Fruit Tree Patch");
                if (shouldMoveFromTree(Const.STRONGHOLD_FRUIT_TREE_ID)) {
                    General.println("[Debug]: Removed Stronghold Fruit Tree patch from list");
                    Vars.get().patchesLeftToVisit.remove(Patches.STRONGHOLD_FRUIT_TREE_PATCH);
                    return true;
                }
            } else if (checkForTreePatchId(Const.TGV_FRUIT_TREE_ID)) {
                General.println("[Debug]: We are at TGV Fruit Tree Patch");
                if (shouldMoveFromTree(Const.TGV_FRUIT_TREE_ID)) {
                    General.println("[Debug]: Removed TGV Fruit Tree patch from list");
                    Vars.get().patchesLeftToVisit.remove(Patches.TGV_FRUIT_TREE_PATCH);
                    return true;
                }
            } else if (checkForTreePatchId(Const.BRIMHAVEN_FRUIT_TREE_ID)) {
                General.println("[Debug]: We are at Brimhaven Fruit Tree Patch");
                if (shouldMoveFromTree(Const.BRIMHAVEN_FRUIT_TREE_ID)) {
                    General.println("[Debug]: Removed Brimhaven Fruit Tree patch from list");
                    Vars.get().patchesLeftToVisit.remove(Patches.BRIMHAVEN_FRUIT_TREE_PATCH);
                    return true;
                }
            } else if (checkForTreePatchId(Const.CATHERBY_FRUIT_TREE_ID)) {
                General.println("[Debug]: We are at Catherby Fruit Tree Patch");
                if (shouldMoveFromTree(Const.CATHERBY_FRUIT_TREE_ID)) {
                    General.println("[Debug]: Removed Catherby Fruit Tree patch from list");
                    Vars.get().patchesLeftToVisit.remove(Patches.CATHERBY_FRUIT_TREE_PATCH);
                    return true;
                }
            }
        }


        /**
         *  HERBS
         */
        if (Vars.get().doingHerbs) {
            if (checkForPatchId(Const.FALADOR_HERB_PATCH_ID, Const.FALADOR_FLOWER_PATCH_ID)) {
                General.println("[Debug]: We are at Falador patch");
                if (shouldMoveFromPatch(Const.FALADOR_HERB_PATCH_ID)) {
                    General.println("[Debug]: Removed Falador patch from list");
                    // remove it from list of patches to visit
                    Vars.get().patchesLeftToVisit.remove(Patches.FALADOR_HERB_PATCH);
                    // start a timer here
                    return true;
                }

            } else if (checkForPatchId(Const.CATHERBY_HERB_PATCH_ID, Const.CATHERBY_FLOWER_PATCH_ID)) {
                // we are at falador patches
                if (shouldMoveFromPatch(Const.CATHERBY_HERB_PATCH_ID)) {
                    General.println("[Debug]: Removed Catherby patch from list");
                    // remove it from list of patches to visit
                    Vars.get().patchesLeftToVisit.remove(Patches.CATHERBY_HERB_PATCH);
                    // start a timer here
                    return true;
                }

            } else if (checkForPatchId(Const.MORYTANIA_HERB_PATCH_ID,
                    Const.MORYTANIA_FLOWER_PATCH_ID)) {
                // we are at falador patches
                if (shouldMoveFromPatch(Const.MORYTANIA_HERB_PATCH_ID)) {
                    General.println("[Debug]: Removed Morytania patch from list");
                    // remove it from list of patches to visit
                    Vars.get().patchesLeftToVisit.remove(Patches.MORYTANIA_HERB_PATCH);
                    // start a timer here
                    return true;
                }

            } else if (checkForPatchId(Const.ARDOUGNE_HERB_PATCH_ID, Const.ARDOUGNE_FLOWER_PATCH_ID)) {
                // we are at falador patches
                if (shouldMoveFromPatch(Const.ARDOUGNE_HERB_PATCH_ID)) {
                    // remove it from list of patches to visit
                    Vars.get().patchesLeftToVisit.remove(Patches.ARDOUGNE_HERB_PATCH);
                    // start a timer here
                    return true;
                }

            } else if (checkForPatchId(Const.HOISIDIUS_HERB_PATCH_ID, Const.HOISIDIUS_FLOWER_PATCH_ID)) {
                // we are at Hosa patches
                if (shouldMoveFromPatch(Const.HOISIDIUS_HERB_PATCH_ID)) {
                    // remove it from list of patches to visit
                    Vars.get().patchesLeftToVisit.remove(Patches.HOSIDIUS_HERB_PATCH);
                    // start a timer here
                    return true;
                }
            } else if (checkForPatchId(Const.FARMING_GUILD_HERB_PATCH_ID, Const.FARMING_GUILD_FLOWER_PATCH_ID)) {
                // we are at farming guild patches
                if (shouldMoveFromPatch(Const.FARMING_GUILD_HERB_PATCH_ID)) {
                    // remove it from list of patches to visit
                    Vars.get().patchesLeftToVisit.remove(Const.guildPatchObj);
                    // start a timer here
                    return true;
                }
            } else if (Vars.get().starting) {
                General.println("[Debug]: DetermineLocation() returned true");
                Vars.get().starting = false;
                return true;
            }
        } else {
            if (checkForPatchId(Const.FALADOR_NW_ALLOTMENT_ID, Const.FALADOR_SE_ALLOTMENT_ID)) {
                General.println("[Debug]: We are at Falador patch");
                if (shouldMoveFromAllotment(Const.FALADOR_NW_ALLOTMENT_ID)
                        && shouldMoveFromAllotment(Const.FALADOR_SE_ALLOTMENT_ID)) {
                    General.println("[Debug]: Removed Falador patch from list");
                    // remove it from list of patches to visit
                    Vars.get().patchesLeftToVisit.remove(Patches.FALADOR_ALLOTMENT_PATCH);
                    // start a timer here
                    return true;
                }

            } else if (checkForPatchId(Const.CATHERBY_N_ALLOTMENT_ID, Const.CATHERBY_S_ALLOTMENT_ID)) {
                if (shouldMoveFromAllotment(Const.CATHERBY_N_ALLOTMENT_ID)
                        && shouldMoveFromAllotment(Const.CATHERBY_S_ALLOTMENT_ID)) {
                    // remove it from list of patches to visit
                    Vars.get().patchesLeftToVisit.remove(Patches.CATHERBY_ALLOTMENT_PATCH);
                    // start a timer here
                    return true;
                }

            } else if (checkForPatchId(Const.MORYTANIA_N_ALLOTMENT_ID,
                    Const.MORYTANIA_S_ALLOTMENT_ID)) {
                if (shouldMoveFromAllotment(Const.MORYTANIA_N_ALLOTMENT_ID)
                        && shouldMoveFromAllotment(Const.MORYTANIA_S_ALLOTMENT_ID)) {
                    Log.warn("Removing morytania allotment patch");
                    Vars.get().patchesLeftToVisit.remove(Patches.MORYTANIA_ALLOTMENT_PATCH);
                    return true;
                }

            } else if (checkForPatchId(Const.ARDOUGNE_N_ALLOTMENT_ID, Const.ARDOUGNE_S_ALLOTMENT_ID)) {
                // we are at falador patches
                if (shouldMoveFromAllotment(Const.ARDOUGNE_N_ALLOTMENT_ID)
                        && shouldMoveFromAllotment(Const.ARDOUGNE_S_ALLOTMENT_ID)) {
                    // remove it from list of patches to visit
                    Vars.get().patchesLeftToVisit.remove(Patches.ARDOUGNE_ALLOTMENT_PATCH);
                    // start a timer here
                    return true;
                }

            } else if (checkForPatchId(Const.HOISIDIUS_SW_ALLOTMENT_ID, Const.HOISIDIUS_NE_ALLOTMENT_ID)) {
                // we are at Hosa patches
                if (shouldMoveFromAllotment(Const.HOISIDIUS_SW_ALLOTMENT_ID)
                        && shouldMoveFromAllotment(Const.HOISIDIUS_NE_ALLOTMENT_ID)) {
                    // remove it from list of patches to visit
                    Vars.get().patchesLeftToVisit.remove(Const.hosidiusPatchObj);
                    General.println("[Move]: Starting herb timer", Color.RED);
                    //Vars.get().herbTimer = new Timer(4960000); // 80min
                    return true;
                }
            } else if (Vars.get().starting) {
                // General.println("[Debug]: DetermineLocation() returned true");
                Vars.get().starting = false;
                return true;
            }
        }
        // General.println("[Debug]: DetermineLocation() returned false");
        return false;
    }

    private Patches determineNextPatch() {
        determineLocation();
        if (Vars.get().patchesLeftToVisit.size() == 0) {
            General.println("[Move]: Should break - no more patches");
            Vars.get().shouldBreak = true;
            return null;
        }
        General.println("Patch Size = " + Vars.get().patchesLeftToVisit.size());
        return Vars.get().patchesLeftToVisit.get(0);
    }

    public void move() {
        /**
         * Trees
         */
        Patches ptch = determineNextPatch();
        if (ptch != null) {
            if (Vars.get().doingTrees) {
                General.println("[Move]: Line 372");
                PathingUtil.walkToArea(ptch.getPatchArea(), false);
                /**
                 * ALLOTMENTS
                 */
            } else if (Vars.get().doingFruitTrees) {
                if (ptch.getPatchIds().get(0) == Const.STRONGHOLD_FRUIT_TREE_ID) {
                    Vars.get().status = "Going to Stronghold fruit Tree Patch";
                    PathingUtil.walkToArea(Areas.STRONGHOLD_FRUIT_TREE_AREA);
                } else if (ptch.getPatchIds().get(0) == Const.TGV_FRUIT_TREE_ID) {
                    Vars.get().status = "Going to TGV Fruit Tree Patch";
                    PathingUtil.walkToArea(Areas.TGV_FRUIT_TREE_AREA);
                } else if (ptch.getPatchIds().get(0) == Const.BRIMHAVEN_FRUIT_TREE_ID) {
                    Vars.get().status = "Going to Brimhaven fruit Tree Patch";
                    PathingUtil.walkToArea(Areas.BRIMHAVEN_FRUIT_TREE_AREA, false);
                } else if (ptch.getPatchIds().get(0) == Const.CATHERBY_FRUIT_TREE_ID) {
                    Vars.get().status = "Going to Catherby fruit Tree Patch";
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
            Vars.get().shouldRestock = false;
            Vars.get().shouldBank = false;
            General.println("[Move Node]: Breaking");
            Utils.longSleep();
            Vars.get().shouldBreak = true;
            //  Main.scriptRef.setLoginBotState(false);

        }

    }

    public static boolean nearHerbPatch() {
        RSObject[] herbs = Objects.findNearest(15, Filters.Objects.nameContains("Patch")
                .and(Filters.Objects.actionsContains("Inspect"))
                .and(Filters.Objects.idEquals(Const.ALL_HERB_PATCH_IDS)));
        return herbs.length > 0 && Vars.get().doingHerbs;
    }


    @Override
    public void execute() {
        General.println(!Vars.get().shouldRestock
                && !Vars.get().shouldBank
                && !Vars.get().shouldBreak);
        move();
    }

    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        if (Login.getLoginState() == Login.STATE.INGAME)
            return !Vars.get().shouldRestock
                    && !Vars.get().shouldBank
                    && !Vars.get().shouldBreak
                    && determineLocation()
                    && FarmingUtils.canMoveToNextSpot()
                   && !nearHerbPatch();

        return false;
    }
}
