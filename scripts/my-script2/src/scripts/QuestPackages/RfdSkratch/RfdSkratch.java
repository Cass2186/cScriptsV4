package scripts.QuestPackages.RfdSkratch;

import dax.walker.utils.AccurateMouse;
import dax.walker.utils.camera.DaxCamera;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.*;
import org.tribot.script.sdk.*;
import org.tribot.script.sdk.Inventory;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.GameObject;
import org.tribot.script.sdk.types.InventoryItem;
import org.tribot.script.sdk.types.WorldTile;
import org.tribot.script.sdk.util.TribotRandom;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.BigChompyBirdHunting.BigChompyBirdHunting;
import scripts.QuestSteps.BuyItemsStep;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.ItemRequirement;
import scripts.Requirements.Requirement;
import scripts.Tasks.Priority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class RfdSkratch implements QuestTask {

    private static RfdSkratch quest;

    public static RfdSkratch get() {
        return quest == null ? quest = new RfdSkratch() : quest;
    }

    int[] AXE_IDS = {
            1349, // iron
            1353,//stee
            1355, //mithril
            1357,// adamant
            1359, // rune
            6739, // dragon
    };
    int IRON_AXE = 1349;
    int STEEL_AXE = 1353;
    int RAW_CHOMPY = 2876;
    int IRON_BAR = 2351;
    int LOG = 1511;
    int TINDERBOX = 590;
    int IRON_PICKAXE = 1267;
    int BALL_OF_WOOL = 1759;
    int OGRE_BOW = 2883;
    int OGRE_ARROW = 2866;
    int FELDIP_HILLS_TELE = 12404;
    int BELLOW = 2871;
    int IRON_SPIT = 7225;
    int SKEWERED_CHOMPY = 7230;
    int COOKED_CHOMPY = 2878;
    int[] inflatedBellows = {2872, 2873, 2874};
    int bloatedToad = 2875;
    int ROCKS = 1480;
    int BALOON_TOAD = 7564;
    int JUBBLY_ID = 4863;
    int RAW_JUBBLY = 7566;
    int COOKED_JUBBLY = 7568;
    int LUMBRIDGE_TAB = 8008;
    int SKRATCH_OBJ_ID = 12343;
    int JUBBLY_ID_4864 = 4864;

    RSArea LUMBRIDGE = new RSArea(new RSTile(3213, 3225, 0), new RSTile(3216, 3219, 0));
    RSArea RANTZ_AREA = new RSArea(new RSTile(2626, 2986, 0), new RSTile(2636, 2975, 0));
    RSArea LOG_AREA = new RSArea(new RSTile(2654, 2961, 0), new RSTile(2647, 2968, 0));
    RSArea TREE_AREA = new RSArea(new RSTile(2762, 3079, 0), new RSTile(2759, 3082, 0));
    RSArea TOAD_AREA = new RSArea(new RSTile(2592, 2972, 0), new RSTile(2605, 2967, 0));
    RSArea MINING_AREA = new RSArea(new RSTile(2566, 2961, 0), new RSTile(2570, 2956, 0));
    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.IRON_AXE, 1, 500),
                    new GEItem(ItemID.IRON_BAR, 1, 500),
                    new GEItem(ItemID.LOGS, 1, 500),
                    new GEItem(ItemID.TINDERBOX, 1, 300),
                    new GEItem(ItemID.HAMMER, 1, 500),
                    new GEItem(ItemID.LUMBRIDGE_TELEPORT, 5, 500),
                    new GEItem(ItemID.IRON_PICKAXE, 1, 1000),
                    new GEItem(ItemID.BALL_OF_WOOL, 1, 1000),
                    new GEItem(ItemID.OGRE_ARROW, 20, 100),
                    new GEItem(ItemID.FELDIP_HILLS_TELEPORT, 5, 100),
                    new GEItem(ItemID.RAW_CHOMPY, 2, 100),

                    new GEItem(ItemID.AMULET_OF_GLORY[2], 1, 30),
                    new GEItem(ItemID.STAMINA_POTION[0], 3, 15),
                    new GEItem(ItemID.RING_OF_WEALTH[0], 1, 25)
            )
    );

    public void buyItems() {
        cQuesterV2.status = "Buying Items";
        Log.info(cQuesterV2.status);
        BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);
        buyStep.buyItems();
    }


    public void getItems() {
        cQuesterV2.status = "Getting Items";
        Log.info(cQuesterV2.status);
        BankManager.open(true);
        BankManager.checkEquippedGlory();
        BankManager.depositAll(true);
        BankManager.withdraw(1, true,
                ItemID.AMULET_OF_GLORY[0]);
        BankManager.withdraw(2, true, ItemID.STAMINA_POTION4);
        BankManager.withdraw(1, true, IRON_AXE);
        BankManager.withdraw(1, true, IRON_BAR);
        BankManager.withdraw(1, true, LOG);
        BankManager.withdraw(1, true, TINDERBOX);
        BankManager.withdraw(1, true, ItemID.HAMMER);
        BankManager.withdraw(1, true, IRON_PICKAXE);
        BankManager.withdraw(2, true, LUMBRIDGE_TAB);
        BankManager.withdraw(3, true, BALL_OF_WOOL);
        BankManager.withdraw(100, true, OGRE_ARROW);
        BankManager.withdraw(1, true, OGRE_BOW);
        BankManager.withdraw(3, true, FELDIP_HILLS_TELE);
        BankManager.withdraw(2, true, RAW_CHOMPY);
        BankManager.withdraw(1, true, BELLOW);
        BankManager.withdraw(1, true, SKEWERED_CHOMPY);
        BankManager.close(true);
    }

    public void makeSpit() {
        if (!Inventory.contains(IRON_SPIT)) {
            cQuesterV2.status = "Making iron spit";
            PathingUtil.walkToTile(new RSTile(3188, 3425, 0));

            if (Utils.clickObj("Anvil", "Smith")) {
                Waiting.waitUntil(8000, TribotRandom.uniform(400, 800),
                        () -> Widgets.isVisible(312));
                if (Query.widgets()
                        .inIndexPath(312).nameContains("Spit")
                        .findFirst().map(w -> w.click()).orElse(false)) {
                    //   if (Interfaces.get(312, 32) != null) {
                    //   Interfaces.get(312, 32).click();
                    Timer.waitCondition(() -> Inventory.contains(IRON_SPIT), 7000, 12000);
                }
            }
        }
    }

    public void goToStart() {
        cQuesterV2.status = "Going to Start";
        Log.info(cQuesterV2.status);
        PathingUtil.walkToArea(LUMBRIDGE);

        if (Utils.clickObj("Large door", "Open"))
            Timer.waitCondition(() ->
                    Objects.findNearest(20, 12343).length > 0, 9000, 12000);

        General.sleep(3500, 5000);

    }


    public void inspectOgre() {
        cQuesterV2.status = "Inspecting Ogre";
        Log.info(cQuesterV2.status);
        if (Utils.clickObj(SKRATCH_OBJ_ID, "Inspect")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Yes, I'm sure I can get some Jubbly Chompy.");
            NPCInteraction.handleConversation("Oh Ok then, I guess I'll talk to Rantz.");
            NPCInteraction.handleConversation();
        }
    }

    public void goToRantz() {
        cQuesterV2.status = "Going to Rantz";
        Log.info(cQuesterV2.status);
        PathingUtil.walkToArea(RANTZ_AREA);
        if (RANTZ_AREA.contains(Player.getPosition())) {
            if (NpcChat.talkToNPC("Rantz")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("I'm trying to free Skrach, can you help?");
                NPCInteraction.handleConversation("Ok, I'll do it.");
                NPCInteraction.handleConversation();
            }
        }
    }

    public void goToLog() {
        cQuesterV2.status = "Going to Rantz";
        Log.info(cQuesterV2.status);
        PathingUtil.walkToArea(LOG_AREA);
        if (NpcChat.talkToNPC("Rantz")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Ok, here I am...I guess this is the watery place? What now?");
            NPCInteraction.handleConversation();
        }
    }

    public void cutLog() {
        if (Utils.useItemOnObject(IRON_AXE, 12549)) {
            NPCInteraction.waitForConversationWindow(); // RSVarBit 1906 changes from 2->3
            NPCInteraction.handleConversation();
        }
    }

    public void talkToRantz3() {
        cQuesterV2.status = "Going to Rantz";
        Log.info(cQuesterV2.status);
        PathingUtil.walkToArea(LOG_AREA);
        if (NpcChat.talkToNPC("Rantz")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Ok, the boat's ready, now tell me how to get a Jubbly?");
            NPCInteraction.handleConversation();
        }
    }

    public void goToTree() {
        if (!Inventory.contains(IRON_SPIT, SKEWERED_CHOMPY)) {
            buyItems();
            getItems();
            makeSpit();
        }
        cQuesterV2.status = "Going to the tree";
        Log.info(cQuesterV2.status);
        PathingUtil.walkToArea(TREE_AREA);

        cQuesterV2.status = "Lighting fire";
        Log.info(cQuesterV2.status);
        if (PathingUtil.walkToTile(new RSTile(2761, 3082, 0)))
            General.sleep(General.random(1500, 3500));

        if (Utils.useItemOnItem(LOG, TINDERBOX))
            Timer.abc2WaitCondition(() -> Objects.find(10, "Fire").length > 0, 25000, 35000);

        if (Objects.find(10, "Fire").length > 0) {
            cQuesterV2.status = "Cooking chompy";
            Log.info(cQuesterV2.status);
            if (Utils.useItemOnItem(RAW_CHOMPY, IRON_SPIT))
                Timer.waitCondition(() -> Inventory.contains(SKEWERED_CHOMPY), 5000, 8000);

            if (Utils.useItemOnObject(SKEWERED_CHOMPY, "Fire")) {
                Timer.waitCondition(() -> Interfaces.isInterfaceSubstantiated(270, 14), 6000, 9000);
                if (Interfaces.isInterfaceSubstantiated(270, 14))
                    if (Interfaces.get(270, 14).click())
                        Timer.waitCondition(() -> Inventory.contains(COOKED_CHOMPY), 8000, 12000);
                Utils.modSleep();
                Utils.cutScene();
            }

        }
    }

    public void goWithKids() {
        NpcChat.handle(true);

        if (!LOG_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Going back to Rantz";
            Log.info(cQuesterV2.status);
            if (QueryUtils.getNpc(6261).map(n ->
                    n.interact("Board")).orElse(false)) {
                NpcChat.handle(true, "Yes please, I'll get a lift back with you.");
                Timer.abc2WaitCondition(() -> LOG_AREA.contains(Player.getPosition()), 28000, 35000);
            }
        }

        cQuesterV2.status = "Talking to Rantz";
        if (NpcChat.talkToNPC("Rantz")) {
            NpcChat.handle(true, "Ok, now tell me how to get Jubbly!");
        }
    }


    public void inflateToads() {
        if (Inventory.contains(COOKED_JUBBLY))
            return;

        if (!Query.inventory().nameContains("bellow").isAny()) {
            cQuesterV2.status = "Getting Bellows";
            BigChompyBirdHunting.goToChest();
            return;
        }

        if (!TOAD_AREA.contains(Player.getPosition()) && Inventory.getCount(bloatedToad) < 3) {
            cQuesterV2.status = "Going to Toads";
            Log.info(cQuesterV2.status);
            PathingUtil.walkToArea(TOAD_AREA);
        }
        if (TOAD_AREA.contains(Player.getPosition()) && Inventory.getCount(bloatedToad) < 3) {
            if (Inventory.getCount(inflatedBellows) < 1) {
                cQuesterV2.status = "Inflating Bellow";
                Log.info(cQuesterV2.status);
                if (Utils.clickObj("Swamp bubbles", "Suck"))
                    Timer.waitCondition(() -> Inventory.getCount(inflatedBellows) > 0, 8000, 12000);
            }
            while (Inventory.getCount(inflatedBellows) > 0 && Inventory.getCount(bloatedToad) < 3) {
                General.sleep(100);
                cQuesterV2.status = "Catching Toads (x3)";
                Log.info(cQuesterV2.status);

                RSNPC[] toad = NPCs.findNearest(1473);
                if (toad.length > 0) {
                    if (!toad[0].isClickable())
                        toad[0].adjustCameraTo();

                    List<InventoryItem> invToad = Query.inventory().idEquals(bloatedToad).toList();

                    if (AccurateMouse.click(toad[0], "Inflate"))
                        Timer.waitCondition(() -> Inventory.getCount(bloatedToad) > invToad.size(), 8900, 12000);

                }
                if (Inventory.getCount(bloatedToad) > 2)
                    break;

            }
        }
    }

    RSTile SWAMP_BUBBLE_TILE = new RSTile(2595, 2967, 0);

    public void makeBaloon() {
        RSNPC[] jubbly = NPCs.find(JUBBLY_ID);
        if (jubbly.length < 1 && Inventory.getCount(BALOON_TOAD) == 0 &&
                Inventory.getCount(ItemID.RAW_JUBBLY) == 0 &&
                Inventory.getCount(COOKED_JUBBLY) == 0) {
            PathingUtil.walkToArea(TOAD_AREA);

            if (TOAD_AREA.contains(Player.getPosition()) && Inventory.getCount(BELLOW) > 0) {
                if (Inventory.getCount(inflatedBellows) == 0) {
                    cQuesterV2.status = "Inflating Bellow";
                    PathingUtil.walkToTile(SWAMP_BUBBLE_TILE);
                    Log.info(cQuesterV2.status);

                    Optional<GameObject> bubbles = Query.gameObjects()
                            .tileEquals(new WorldTile(2595, 2966, 0))
                            .nameContains("Swamp bubbles")
                            .findBestInteractable();

                    if (Utils.clickObj(bubbles, "Suck"))
                        Timer.waitCondition(() -> Inventory.getCount(inflatedBellows) > 0, 8000, 12000);
                }
            }

            if (Inventory.getCount(inflatedBellows) > 0 && Inventory.getCount(bloatedToad) > 0) {
                cQuesterV2.status = "Making baloon";
                Log.info(cQuesterV2.status);
                Optional<InventoryItem> bellows = QueryUtils.getItem(inflatedBellows);
                Optional<InventoryItem> inflatedToad = QueryUtils.getItem(bloatedToad);
                if (bellows.map(b -> inflatedToad.map(b::useOn).orElse(false)).orElse(false)) {
                    Timer.waitCondition(8500, 500,
                            () -> Inventory.getCount(BALOON_TOAD) > 0);
                }
            }
        }
    }

    public void mineRocks() {
        RSNPC[] jubbly = NPCs.find(JUBBLY_ID);
        if (jubbly.length < 1 && Inventory.getCount(BALOON_TOAD) == 0 &&
                Inventory.getCount(ItemID.RAW_JUBBLY) == 0 &&
                Inventory.getCount(COOKED_JUBBLY) == 0) {
            cQuesterV2.status = "Going to mine";
            Log.info(cQuesterV2.status);
            if (!MINING_AREA.contains(Player.getPosition()) && Inventory.getCount(bloatedToad) > 1
                    && Inventory.getCount(ROCKS) < 1)
                PathingUtil.walkToArea(MINING_AREA);

            if (Inventory.getCount(ROCKS) < 3) {
                for (int i = 0; i < 5; i++) {
                    List<InventoryItem> invRocks = Query.inventory().idEquals(ROCKS).toList();
                    if (Utils.clickObj(12564, "Mine")) {
                        Timer.abc2WaitCondition(() -> Inventory.getCount(ROCKS) > invRocks.size(), 10000, 15000);
                    }
                    if (Inventory.getCount(ROCKS) >= 3)
                        break;
                }
            }
        }
    }



    public void baitBird() {
        Utils.equipItem(OGRE_BOW);
        Utils.equipItem(OGRE_ARROW);
        if (Inventory.getCount(BALOON_TOAD) > 0 &&
                Inventory.getCount(ItemID.RAW_JUBBLY) == 0 &&
                Inventory.getCount(COOKED_JUBBLY) == 0) {

            PathingUtil.walkToArea(RANTZ_AREA);

            if (PathingUtil.makeLargerArea(RANTZ_AREA).contains(Player.getPosition())) {
                cQuesterV2.status = "Dropping balloon";
                Log.info(cQuesterV2.status);
                Utils.blindWalkToTile(new RSTile(2633, 2970, 0));
                PathingUtil.movementIdle();

                Optional<InventoryItem> baloon = Query.inventory().idEquals(BALOON_TOAD).findClosestToMouse();
                if (baloon.map(b -> b.click("Drop")).orElse(false)) {
                    cQuesterV2.status = "Waiting for Jubbly...";
                    Log.info(cQuesterV2.status);
                    Timer.waitCondition(() -> NPCs.find(JUBBLY_ID).length > 0, 65000, 85000);

                }
            }

            RSNPC[] jubbly = NPCs.find(JUBBLY_ID);
            if (jubbly.length > 0) {
                cQuesterV2.status = "Jubbly appeared";
                Log.info(cQuesterV2.status);
                if (!jubbly[0].isClickable())
                    DaxCamera.focus(jubbly[0]);

                if (AccurateMouse.click(jubbly[0], "Attack")) {
                    cQuesterV2.status = "Attacking Jubbly";
                    Log.info(cQuesterV2.status);
                    Timer.waitCondition(() -> NPCs.find(JUBBLY_ID)[0].getHealthPercent() < 5, 65000, 75000);
                    Timer.waitCondition(() -> NPCs.find(Filters.NPCs.actionsContains("Pluck")).length > 0, 20000, 25000);
                    General.sleep(General.random(500, 2500));
                }
            }

            cQuesterV2.status = "Plucking Jubbly";
            if (Utils.clickNPC(JUBBLY_ID_4864, "Pluck"))
                Timer.abc2WaitCondition(() -> GroundItems.find(RAW_JUBBLY).length > 0, 25000, 35000);


            RSGroundItem[] rawJubbly = GroundItems.find(RAW_JUBBLY);
            if (rawJubbly.length > 0) {

                if (!rawJubbly[0].isClickable())
                    DaxCamera.focus(rawJubbly[0]);

                if (AccurateMouse.click(rawJubbly[0], "Take"))
                    Timer.waitCondition(() -> Inventory.getCount(RAW_JUBBLY) > 0, 6000, 9000);
            }
        } else if (Inventory.getCount(ItemID.RAW_JUBBLY) == 0) {
            inflateToads();
            mineRocks();
            makeBaloon();

        }
    }

    public void cookJubbly() {
        if (Inventory.getCount(RAW_JUBBLY) > 0 &&
                Inventory.getCount(COOKED_JUBBLY) == 0) {
            cQuesterV2.status = "Cooking Jubbly";
            if (PathingUtil.walkToTile(new RSTile(2631, 2989, 0)))
                Timer.waitCondition(() -> Objects.find(30, 6895).length > 0, 8000, 12000);

            if (Objects.find(30, 6895).length > 0) {
                Timer.waitCondition(() -> Objects.find(30, 6895)[0]
                        .getPosition().distanceTo(Player.getPosition()) < 2, 12000);
                if (Utils.useItemOnObject(RAW_JUBBLY, 6895))
                    Timer.waitCondition(() -> Inventory.getCount(COOKED_JUBBLY) > 0, 20000);
            }
        }
    }

    public void finishQuest() {
        if (Objects.findNearest(20, 12343).length < 1) {
            cQuesterV2.status = "Going to Lumbridge";
            Log.info(cQuesterV2.status);
            PathingUtil.walkToArea(LUMBRIDGE);
            if (Utils.clickObj("Large door", "Open")) {
                Timer.waitCondition(() -> Objects.findNearest(20, 12343).length > 0, 8000, 12000);
                General.sleep(General.random(2500, 5000));
            }
        }
        if (Objects.findNearest(20, 12343).length > 0) {
            Utils.useItemOnObject(COOKED_JUBBLY, 12343);
            General.sleep(3500, 5000);
        }
    }

    public boolean checkLevel() {
        if (Skills.getActualLevel(Skills.SKILLS.FIREMAKING) < 25) {
            General.println("[Debug]: Firemaking level is below 25, ending.");
            return false;
        }
        if (Skills.getActualLevel(Skills.SKILLS.COOKING) < 41) {
            General.println("[Debug]: Cooking level is below 41, ending.");
            return false;
        }
        return true;
    }

    private void releaseAllFrogs() {
        if (Query.inventory().actionContains("Release-all")
                .findClosestToMouse().map(f -> f.click("Release-all")).orElse(false))
            Utils.idleNormalAction(true);
    }

    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return cQuesterV2.taskList.get(0).equals(this);
    }

    @Override
    public void execute() {
        if (!checkRequirements()){
            cQuesterV2.taskList.remove(this);
            return;
        }
        if (GameState.getVarbit(1904) == 0) {
            buyItems();
            getItems();
            makeSpit();
            goToStart();
            inspectOgre();
        } else if (GameState.getVarbit(1904) == 10) {
            goToRantz();
        } else if (GameState.getVarbit(1904) == 20) {
            goToLog();
        } else if (GameState.getVarbit(1904) == 30) {
            goToLog();
        } else if (RSVarBit.get(6719).getValue() == 30 ||
                GameState.getVarbit(1904) == 40) {
            cQuesterV2.status = "Waiting for cut scene";
            Waiting.waitUntil(20000, 1500,
                    () -> GameState.getVarbit(1904) == 50); // cut scene
        } else if (GameState.getVarbit(1904) == 50) {
            cutLog();
        } else if (GameState.getVarbit(1904) == 60) {
            cutLog();
        } else if (GameState.getVarbit(1904) == 70) {
            talkToRantz3();
        } else if (GameState.getVarbit(1904) == 80) {
            goToTree();
        } else if (GameState.getVarbit(1904) == 90) {
            cQuesterV2.status = "Waiting for cut scene";
            Waiting.waitUntil(20000, 1500,
                    () -> GameState.getVarbit(1904) == 100); // cut scene
        } else if (GameState.getVarbit(1904) == 100) {
            goWithKids();
        } else if (GameState.getVarbit(1904) == 110) {
            mineRocks();
            inflateToads();
            makeBaloon();
        } else if (GameState.getVarbit(1904) >= 120 &&
                GameState.getVarbit(1904) <= 140) {
            makeBaloon();
            baitBird();
        } else if (GameState.getVarbit(1904) == 150) {
            makeBaloon();
            baitBird();
            cookJubbly();
        } else if (GameState.getVarbit(1904) == 160) {
            finishQuest();
        }

        if (GameState.getVarbit(1904) == 170) {
            Utils.closeQuestCompletionWindow();
            NpcChat.handle();
            cQuesterV2.taskList.remove(this);
            releaseAllFrogs();
        }

    }

    @Override
    public String questName() {
        return "RFD Skratch (" + GameState.getVarbit(1904) + ")";
    }

    @Override
    public boolean checkRequirements() {
        return checkLevel();
    }

    @Override
    public List<Requirement> getGeneralRequirements() {
        return null;
    }

    @Override
    public List<ItemRequirement> getBuyList() {
        return null;
    }

    @Override
    public boolean isComplete() {
        return Utils.getVarBitValue(1904) == 170;
    }
}
