package scripts.Nodes;

import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSItemDefinition;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSObject;
import org.tribot.script.sdk.MyPlayer;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.Area;
import org.tribot.script.sdk.types.GameObject;
import scripts.Data.Const;
import scripts.Data.Enums.Trees;
import scripts.Data.Vars;

import scripts.Tasks.Priority;
import scripts.Tasks.Task;
import scripts.Timer;

import scripts.Utils;


import java.awt.*;
import java.util.Optional;

public class Plant implements Task {

    public static void rakeWeeds(int patchId) {
        RSObject[] weeds = Objects.findNearest(20,
                Filters.Objects.actionsContains("Rake").and(Filters.Objects.idEquals(patchId)));
        if (weeds.length > 0) {
            Vars.get().status = "Raking weeds";
            if (Utils.clickObject(patchId, "Rake", false)) {
                Timer.waitCondition(() -> Objects.findNearest(20,
                        Filters.Objects.actionsContains("Rake").and(Filters.Objects.idEquals(patchId))).length <
                        weeds.length, 25000, 35000);
            }
        }
    }

    public static void clearDeadPlants(int patchId) {
        Optional<GameObject> deadPlant = Query.gameObjects()
                .nameContains("Dead")
                .actionContains("Clear")
                .actionContains("Inspect")
                //.idEquals(patchId)
                .maxDistance(10)
                .findClosestByPathDistance();
        if (deadPlant.map(d -> d.interact("Clear")).orElse(false) &&
                Timer.waitCondition(() -> Player.getAnimation() != -1, 5000, 7000)) {
            Vars.get().status = "Clearing dead plants";
            Timer.waitCondition(() -> Query.gameObjects()
                    .nameContains("Dead")
                    .actionContains("Clear")
                    .actionContains("Inspect")
                    .maxDistance(5)
                    //   .idEquals(patchId)
                    .findClosestByPathDistance().isEmpty(), 20000, 30000);
        }
    }

    public static void useCompost(int patchId) {
        RSObject[] patch = Objects.findNearest(20, Filters.Objects.nameContains("Herb patch").and(Filters.Objects.idEquals(patchId)));
        if (patch.length > 0) {
            Vars.get().status = "Using Ultracompost";
            if (Vars.get().usingBottomless || Inventory.find(Const.BOTTOMLESS_COMPOST).length > 0) {
                if (Utils.useItemOnObject(Const.BOTTOMLESS_COMPOST, patchId)) {
                    Timer.waitCondition(() -> Player.getAnimation() != -1, 4000, 8000);
                    Utils.shortSleep();
                }
            } else if (Utils.useItemOnObject(Const.ULTRACOMPOST, patchId)) {
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
            Vars.get().status = "Using Ultracompost";
            if (Vars.get().usingBottomless || Inventory.find(Const.BOTTOMLESS_COMPOST).length > 0) {
                if (Utils.useItemOnObject(Const.BOTTOMLESS_COMPOST, patchId)) {
                    Timer.waitCondition(() -> Player.getAnimation() != -1, 4000, 8000);
                    Utils.shortSleep();
                }
            } else if (Utils.useItemOnObject(Const.ULTRACOMPOST, patchId)) {
                Timer.waitCondition(() -> Player.getAnimation() != -1, 4000, 8000);
                Utils.shortSleep();
            }
        }
    }


    public static void useCompostTree(int patchId) {
        RSObject[] patch = Objects.findNearest(20, Filters.Objects.nameContains("Tree patch").and(Filters.Objects.idEquals(patchId)));
        if (patch.length > 0) {
            Vars.get().status = "Using Ultracompost";
            if (Inventory.find(Const.BOTTOMLESS_COMPOST).length > 0) {
                if (Utils.useItemOnObject(Const.BOTTOMLESS_COMPOST, patchId)) {
                    Timer.waitCondition(() -> Player.getAnimation() != -1, 4000, 8000);
                    Utils.shortSleep();
                }
            } else if (Utils.useItemOnObject(Const.ULTRACOMPOST, patchId)) {
                Timer.waitCondition(() -> Player.getAnimation() != -1, 4000, 8000);
                Utils.shortSleep();
            }
        }
    }


    public boolean plantHerb(int herbId, int patchId) {
        rakeWeeds(patchId);
        RSObject[] patch = Objects.findNearest(20, Filters.Objects.nameContains("Herb patch")
                .and(Filters.Objects.idEquals(patchId)));
        if (patch.length > 0) {
            Vars.get().status = "Planting herbs";
            if (Utils.useItemOnObject(herbId, patchId)) {
                Timer.waitCondition(() -> Player.getAnimation() != -1, 4000, 8000);
                Timer.abc2WaitCondition(() -> Player.getAnimation() == -1, 4000, 8000);
            }
            return true;
        }
        return false;
    }

    public boolean plantTreePatch(int herbId, int patchId) {
        rakeWeeds(patchId);
        Optional<GameObject> patch = Query.gameObjects().nameContains("Tree patch")
                .idEquals(patchId)
                .maxDistance(10)
                .findClosestByPathDistance();
        Vars.get().status = "Planting tree";
        if (patch.isPresent() && Utils.useItemOnObject(herbId, patchId) &&
                Timer.waitCondition(() -> Player.getAnimation() != -1, 4000, 8000)) {
            Timer.waitCondition(() -> Player.getAnimation() == -1, 5000, 8000);
            return true;
        }
        return false;
    }

    public boolean plantAllotmentPatch(int seedId, int patchId) {
        rakeWeeds(patchId);
        RSObject[] patch = Objects.findNearest(20, Filters.Objects.nameContains("allotment").
                and(Filters.Objects.idEquals(patchId)));
        if (patch.length > 0) {
            Vars.get().status = "Planting Allotment";
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
            Vars.get().status = "Using Ultracompost";
            if (Vars.get().usingBottomless) {
                if (Utils.useItemOnObject(Const.BOTTOMLESS_COMPOST, patchId)) {
                    Timer.waitCondition(() -> Player.getAnimation() != -1, 4000, 8000);
                    Utils.shortSleep();
                }
            } else if (Utils.useItemOnObject(Const.ULTRACOMPOST, patchId)) {
                Timer.waitCondition(() -> Player.getAnimation() != -1, 4000, 8000);
                Utils.shortSleep();
            }
        }
    }

    public boolean plantFlowerSeed(int herbId, int patchId) {
        useCompostFlower(patchId);
        rakeWeeds(patchId);
        RSObject[] patch = Objects.findNearest(20, Filters.Objects.nameContains("Flower patch")
                .and(Filters.Objects.idEquals(patchId)));
        if (patch.length > 0) {
            Vars.get().status = "Planting Flower";
            if (Utils.useItemOnObject(herbId, patchId)) {
                Timer.waitCondition(() -> Player.getAnimation() != -1, 4000, 8000);
                return Timer.abc2WaitCondition(() -> Player.getAnimation() == -1, 4000, 8000);
            }
        }
        return false;
    }

    public static void determineAllotmentId() {
        if (Skills.getCurrentLevel(Skills.SKILLS.FARMING) < 5) {
            Vars.get().currentAllotmentId = Const.POTATO_SEEDS;

        } else if (Skills.getCurrentLevel(Skills.SKILLS.FARMING) < 7) {
            Vars.get().currentAllotmentId = Const.ONION_SEEDS;

        } else if (Skills.getCurrentLevel(Skills.SKILLS.FARMING) < 12) {
            Vars.get().currentAllotmentId = Const.CABBAGE_SEEDS;

        } else if (Skills.getCurrentLevel(Skills.SKILLS.FARMING) < 20) {
            Vars.get().currentAllotmentId = Const.TOMATO_SEEDS;

        } else if (Skills.getCurrentLevel(Skills.SKILLS.FARMING) < 31) {
            Vars.get().currentAllotmentId = Const.SWEETCORN_SEEDS;

        } else if (Skills.getCurrentLevel(Skills.SKILLS.FARMING) < 47) {
            Vars.get().currentAllotmentId = Const.STRAWBERRY_SEEDS;

        } else if (Skills.getCurrentLevel(Skills.SKILLS.FARMING) < 61) {
            Vars.get().currentAllotmentId = Const.WATERMELON_SEEDS;

        } else if (Skills.getCurrentLevel(Skills.SKILLS.FARMING) >= 61) {
            Vars.get().currentAllotmentId = Const.SNAPEGRASS_SEEDS;
        }
        General.println("[Debug]: Current allotment seed is: " + Vars.get().currentAllotmentId);
    }

    public static void determineHerbId() {
        if (Skills.getCurrentLevel(Skills.SKILLS.FARMING) < 9) {

        } else if (Skills.getCurrentLevel(Skills.SKILLS.FARMING) < 14) {
            Vars.get().currentHerbId = 5291; //guam

        } else if (Skills.getCurrentLevel(Skills.SKILLS.FARMING) < 19) {
            Vars.get().currentHerbId = 5292; // marrentill

        } else if (Skills.getCurrentLevel(Skills.SKILLS.FARMING) < 26) {
            Vars.get().currentHerbId = 5293; //tarromin

        } else if (Skills.getCurrentLevel(Skills.SKILLS.FARMING) < 32) {
            Vars.get().currentHerbId = 5294; //harralandar

        } else if (Skills.getCurrentLevel(Skills.SKILLS.FARMING) < 82) { // should be 38
            Vars.get().currentHerbId = 5295; //ranarr

        } else if (Skills.getCurrentLevel(Skills.SKILLS.FARMING) < 44) {
            Vars.get().currentHerbId = 5296; // toadflax

        } else if (Skills.getCurrentLevel(Skills.SKILLS.FARMING) < 50) {
            Vars.get().currentHerbId = 5297; //irit

        } else if (Skills.getCurrentLevel(Skills.SKILLS.FARMING) < 56) {
            Vars.get().currentHerbId = 5299; // kwuarm

        } else if (Skills.getCurrentLevel(Skills.SKILLS.FARMING) >= 62) {
            Vars.get().currentHerbId = 5300; // snapdragon
        }

        General.println("[Debug]: Current herb seed is: " + Utils.getItemNameFromID(Vars.get().currentHerbId) + " unless overrode by arguments");
    }

    public static void determineTreeId() {
        if (Skills.getCurrentLevel(Skills.SKILLS.FARMING) < 15) {
            General.println("[Plant]: Too low of a level to do trees.");
            return;

        } else if (Skills.getCurrentLevel(Skills.SKILLS.FARMING) < 30) {
            Vars.get().treeId = Trees.OAK_SAPPLING.id;

        } else if (Skills.getCurrentLevel(Skills.SKILLS.FARMING) < 45) {
            Vars.get().treeId = Trees.WILLOW_SAPPLING.id;

        } else if (Skills.getCurrentLevel(Skills.SKILLS.FARMING) < 60) {
            Vars.get().treeId = Trees.MAPLE_SAPPLING.id;

        } else if (Skills.getCurrentLevel(Skills.SKILLS.FARMING) < 75) {
            Vars.get().treeId = Trees.YEW_SAPPLING.id;

        } else {
            Vars.get().treeId = Trees.MAGIC_SAPPLING.id;

        }
        General.println("[Debug]: Current Tree is: " + Vars.get().treeId);
    }

    public static void determineFruitTreeId() {
        if (Skills.getCurrentLevel(Skills.SKILLS.FARMING) < 27) {
            General.println("[Plant]: Too low of a level to do Fruit trees.");
            return;

        } else if (Skills.getCurrentLevel(Skills.SKILLS.FARMING) < 33) {
            Vars.get().fruitTreeId = Trees.APPLE_SAPPLING.id;

        } else if (Skills.getCurrentLevel(Skills.SKILLS.FARMING) < 39) {
            Vars.get().fruitTreeId = Trees.BANANA_SAPPLING.id;

        } else if (Skills.getCurrentLevel(Skills.SKILLS.FARMING) < 42) {
            Vars.get().fruitTreeId = Trees.ORANGE_SAPPLING.id;

        } else if (Skills.getCurrentLevel(Skills.SKILLS.FARMING) < 51) {
            Vars.get().fruitTreeId = Trees.CURRY_SAPPLING.id;

        } else if (Skills.getCurrentLevel(Skills.SKILLS.FARMING) < 57) {
            Vars.get().fruitTreeId = Trees.PINEAPPLE_SAPPLING.id;

        } else if (Skills.getCurrentLevel(Skills.SKILLS.FARMING) < 68) {
            Vars.get().fruitTreeId = Trees.PAPAYA_SAPPLING.id;

        } else if (Skills.getCurrentLevel(Skills.SKILLS.FARMING) < 99) {
            Vars.get().fruitTreeId = Trees.PALM_SAPPLING.id;

        } else {
            Vars.get().fruitTreeId = Trees.DRAGONFRUIT_SAPPLING.id;

        }
        General.println("[Debug]: Current Fruit Tree is: " + RSItemDefinition.get(Vars.get().fruitTreeId).getName());
    }

    public void clearTree() {
        RSObject[] trees = Objects.findNearest(20, Filters.Objects.actionsContains("Chop down").
                or(Filters.Objects.actionsContains("Pick-")).and(Filters.Objects.actionsContains("Guide"))); //guide filters out random trees
        if (trees.length > 0) {
            Vars.get().status = "Clearing Tree";

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
        clearDeadPlants(patchId);
        rakeWeeds(patchId);
        useCompost(patchId);
        return plantHerb(Vars.get().currentHerbId, patchId);
    }

    public boolean plantTree(int patchId) {
        clearDeadPlants(patchId);
        clearTree();
        rakeWeeds(patchId);
        useCompostTree(patchId);
        return plantTreePatch(Vars.get().treeId, patchId);
    }

    public boolean plantFruitTree(int patchId) {
        clearDeadPlants(patchId);
        clearTree();
        rakeWeeds(patchId);
        useCompostTree(patchId);
        return plantTreePatch(Vars.get().fruitTreeId, patchId);
    }

    public void plantFlower(int patchId) {
        clearDeadPlants(patchId);
        rakeWeeds(patchId);
        useCompost(patchId);
        plantFlowerSeed(Const.LIMPWURT_SEEDS, patchId);
    }

    public void plantAllotments(int... allotmentIds) {
        for (int i : allotmentIds) {
            clearDeadPlants(i);
            rakeWeeds(i);
            useCompostAllotment(i);
            plantAllotmentPatch(Vars.get().currentAllotmentId, i);
            if (i == Const.MORYTANIA_S_ALLOTMENT_ID) {
                General.println("[Debug]: Starting allotment timer");
                //  Vars.get().allotmentTimer = new Timer(4900000); // 70min
            }
        }
    }


    public void plantHerbsCatherby() {
        plantHerbs(Const.CATHERBY_HERB_PATCH_ID);
        plantFlower(Const.CATHERBY_FLOWER_PATCH_ID);
    }

    public void plantHerbsFalador() {
        plantHerbs(Const.FALADOR_HERB_PATCH_ID);
        plantFlower(Const.FALADOR_FLOWER_PATCH_ID);
    }

    public void plantHerbsArdougne() {
        plantHerbs(Const.ARDOUGNE_HERB_PATCH_ID);
        plantFlower(Const.ARDOUGNE_FLOWER_PATCH_ID);
    }

    public void plantHerbsHosidius() {
        plantHerbs(Const.HOISIDIUS_HERB_PATCH_ID);
        plantFlower(Const.HOISIDIUS_FLOWER_PATCH_ID);
    }

    public void plantHerbsMorytania() {
        plantFlower(Const.MORYTANIA_FLOWER_PATCH_ID);
        if (plantHerbs(Const.MORYTANIA_HERB_PATCH_ID)) {
            General.println("[Plant] Starting herb timer", Color.YELLOW);
            // Vars.get().herbTimer = new Timer(General.random(48 * 10 ^ 5, 54 * 10 ^ 5)); // 80-90 min
        }
    }


    @Override
    public void execute() {
        if (Vars.get().doingTrees) {
            Vars.get().status = "Planting Trees";
            General.println("[Plant] " + Vars.get().status, Color.RED);
            plantTree(Const.FALADOR_TREE_PATCH_ID);
            plantTree(Const.VAROCK_TREE_PATCH_ID);
            plantTree(Const.TAVERLY_TREE_PATCH_ID);
            plantTree(Const.LUMBRIDGE_TREE_PATCH_ID);
            if (plantTree(Const.GNOME_STRONGHOLD_TREE_PATCH_ID)) {
                General.println("[Debug]: Starting tree timer");
                // Vars.get().treeTimer = new Timer(28800 * 1000); // 480min
            }
        } else if (Vars.get().doingAllotments) {
            plantAllotments(
                    Const.ARDOUGNE_N_ALLOTMENT_ID,
                    Const.ARDOUGNE_S_ALLOTMENT_ID,
                    Const.FALADOR_NW_ALLOTMENT_ID,
                    Const.FALADOR_SE_ALLOTMENT_ID,
                    Const.CATHERBY_N_ALLOTMENT_ID,
                    Const.CATHERBY_S_ALLOTMENT_ID,
                    Const.HOISIDIUS_SW_ALLOTMENT_ID,
                    Const.HOISIDIUS_NE_ALLOTMENT_ID,
                    Const.MORYTANIA_N_ALLOTMENT_ID,
                    Const.MORYTANIA_S_ALLOTMENT_ID
            );
        } else if (Vars.get().doingFruitTrees) {
            Vars.get().status = "Planting Fruit Trees";
            General.println("[Plant] " + Vars.get().status, Color.RED);
            plantFruitTree(Const.STRONGHOLD_FRUIT_TREE_ID);
            plantFruitTree(Const.TGV_FRUIT_TREE_ID);
            plantFruitTree(Const.CATHERBY_FRUIT_TREE_ID);
            plantFruitTree(Const.BRIMHAVEN_FRUIT_TREE_ID);
        } else {
            Vars.get().status = "Planting herbs.";

            plantHerbsCatherby();
            plantHerbsFalador();
            plantHerbsArdougne();
            plantHerbsHosidius();
            plantHerbsMorytania();
            plantHerbs(Const.FARMING_GUILD_HERB_PATCH_ID);
        }
    }

    @Override
    public Priority priority() {
        return Priority.LOWEST;
    }

    @Override
    public boolean validate() {
        General.println("[Debug]: Should restock =" + Vars.get().shouldRestock);
        General.println("[Debug]: Should bank =" + Vars.get().shouldBank);
        General.println("[Debug]: Should break =" + Vars.get().shouldBreak);
        return Login.getLoginState() == Login.STATE.INGAME
                && !Vars.get().shouldRestock
                && !Vars.get().shouldBank
                && !Vars.get().shouldBreak;
    }
}
