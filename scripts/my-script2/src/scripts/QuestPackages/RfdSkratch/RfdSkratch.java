package scripts.QuestPackages.RfdSkratch;

import dax.walker.utils.AccurateMouse;
import dax.walker.utils.camera.DaxCamera;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.*;
import org.tribot.script.sdk.Quest;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.GameObject;
import org.tribot.script.sdk.types.WorldTile;
import scripts.*;
import scripts.GEManager.GEItem;
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
    int bellows = 2871;
    int[] inflatedBellows = {2872, 2873, 2874};
    int bloatedToad = 2875;
    int ROCKS = 1480;
    int BALOON_TOAD = 7564;
    int JUBBLY_ID = 4863;
    int RAW_JUBBLY = 7566;
    int COOKED_JUBBLY = 7568;
    int HAMMER = 2347;
    int LUMBRIDGE_TAB = 8008;
    int SKRATCH_OBJ_ID = 12343;

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
        General.println("[Debug]: " + cQuesterV2.status);
        BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);
        buyStep.buyItems();
    }


    public void getItems() {
        cQuesterV2.status = "Getting Items";
        General.println("[Debug]: " + cQuesterV2.status);
        BankManager.open(true);
        BankManager.checkEquippedGlory();
        BankManager.depositAll(true);
        BankManager.withdraw(1, true,
                ItemID.AMULET_OF_GLORY[0]);
        BankManager.withdraw(1, true, BankManager.STAMINA_POTION);
        BankManager.withdraw(1, true, IRON_AXE);
        BankManager.withdraw(1, true, IRON_BAR);
        BankManager.withdraw(1, true, LOG);
        BankManager.withdraw(1, true, TINDERBOX);
        BankManager.withdraw(1, true, 2347);
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
        if (Inventory.find(IRON_SPIT).length == 0) {
            cQuesterV2.status = "Making iron spit";
            PathingUtil.walkToTile(new RSTile(3188, 3425, 0));

            if (Utils.clickObj("Anvil", "Smith")) {
                Timer.waitCondition(() -> Interfaces.get(312, 32) != null, 6000, 9090);
                if (Interfaces.get(312, 32) != null) {
                    Interfaces.get(312, 32).click();
                    Timer.waitCondition(() -> Inventory.find(IRON_SPIT).length > 0, 7000, 12000);
                }
            }
        }
    }

    public void goToStart() {
        cQuesterV2.status = "Going to Start";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(LUMBRIDGE);

        if (Utils.clickObj("Large door", "Open"))
            Timer.waitCondition(() -> Objects.findNearest(20, 12343).length > 0, 8000, 12000);

        General.sleep(General.random(2500, 5000));

    }


    public void inspectOgre() {
        cQuesterV2.status = "Inspecting Ogre";
        General.println("[Debug]: " + cQuesterV2.status);
        if (Utils.clickObj(SKRATCH_OBJ_ID, "Inspect")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Yes, I'm sure I can get some Jubbly Chompy.");
            NPCInteraction.handleConversation("Oh Ok then, I guess I'll talk to Rantz.");
            NPCInteraction.handleConversation();
        }
    }

    public void goToRantz() {
        cQuesterV2.status = "Going to Rantz";
        General.println("[Debug]: " + cQuesterV2.status);
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
        General.println("[Debug]: " + cQuesterV2.status);
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
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(LOG_AREA);
        if (NpcChat.talkToNPC("Rantz")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Ok, the boat's ready, now tell me how to get a Jubbly?");
            NPCInteraction.handleConversation();
        }
    }

    public void goToTree() {
        if (Inventory.find(IRON_SPIT).length < 1 && Inventory.find(SKEWERED_CHOMPY).length < 1) {
            buyItems();
            getItems();
            makeSpit();
        }
        cQuesterV2.status = "Going to the tree";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(TREE_AREA);

        cQuesterV2.status = "Lighting fire";
        General.println("[Debug]: " + cQuesterV2.status);
        if (PathingUtil.walkToTile(new RSTile(2761, 3082, 0)))
            General.sleep(General.random(1500, 3500));

        if (Utils.useItemOnItem(LOG, TINDERBOX))
            Timer.abc2WaitCondition(() -> Objects.find(10, "Fire").length > 0, 25000, 35000);

        if (Objects.find(10, "Fire").length > 0) {
            cQuesterV2.status = "Cooking chompy";
            General.println("[Debug]: " + cQuesterV2.status);
            if (Utils.useItemOnItem(RAW_CHOMPY, IRON_SPIT))
                Timer.waitCondition(() -> Inventory.find(SKEWERED_CHOMPY).length > 0, 5000, 8000);

            if (Utils.useItemOnObject(SKEWERED_CHOMPY, "Fire")) {
                Timer.waitCondition(() -> Interfaces.isInterfaceSubstantiated(270, 14), 6000, 9000);
                if (Interfaces.isInterfaceSubstantiated(270, 14))
                    if (Interfaces.get(270, 14).click())
                        Timer.waitCondition(() -> Inventory.find(COOKED_CHOMPY).length > 0, 8000, 12000);
                Utils.modSleep();
            }

        }
    }

    public void goWithKids() {
        NPCInteraction.handleConversation();
        if (!LOG_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Going back to Rantz";
            General.println("[Debug]: " + cQuesterV2.status);
            RSNPC[] kids = NPCs.find(6261);
            if (kids.length > 0) {
                if (!kids[0].isClickable())
                    kids[0].adjustCameraTo();

                if (AccurateMouse.click(kids[0], "Board")) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation("Yes please, I'll get a lift back with you.");
                    Timer.abc2WaitCondition(() -> LOG_AREA.contains(Player.getPosition()), 20000, 35000);
                }
            }
        }
        cQuesterV2.status = "Talking to Rantz";
        if (NpcChat.talkToNPC("Rantz")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Ok, now tell me how to get Jubbly!");
            NPCInteraction.handleConversation();
        }
    }

    public void inflateToads() {
        if (Inventory.find(COOKED_JUBBLY).length > 0)
            return;
        if (!TOAD_AREA.contains(Player.getPosition()) && Inventory.find(bloatedToad).length < 3) {
            cQuesterV2.status = "Going to Toads";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(TOAD_AREA);
        }
        if (TOAD_AREA.contains(Player.getPosition()) && Inventory.find(bloatedToad).length < 3) {
            if (Inventory.find(inflatedBellows).length < 1) {
                cQuesterV2.status = "Inflating Bellow";
                General.println("[Debug]: " + cQuesterV2.status);
                if (Utils.clickObj("Swamp bubbles", "Suck"))
                    Timer.waitCondition(() -> Inventory.find(inflatedBellows).length > 0, 8000, 12000);
            }
            while (Inventory.find(inflatedBellows).length > 0 && Inventory.find(bloatedToad).length < 3) {
                General.sleep(100);
                cQuesterV2.status = "Catching Toads (x3)";
                General.println("[Debug]: " + cQuesterV2.status);

                RSNPC[] toad = NPCs.findNearest(1473);
                if (toad.length > 0) {
                    if (!toad[0].isClickable())
                        toad[0].adjustCameraTo();

                    RSItem[] invToad = Inventory.find(bloatedToad);

                    if (AccurateMouse.click(toad[0], "Inflate"))
                        Timer.waitCondition(() -> Inventory.find(bloatedToad).length > invToad.length, 8900, 12000);

                }
                if (Inventory.find(bloatedToad).length > 2)
                    break;

            }
        }
    }

    RSTile SWAMP_BUBBLE_TILE = new RSTile(2595, 2967, 0);

    public void makeBaloon() {

        RSNPC[] jubbly = NPCs.find(JUBBLY_ID);
        if (jubbly.length < 1 && Inventory.find(BALOON_TOAD).length == 0  &&
                Inventory.find(COOKED_JUBBLY).length ==0) {
            PathingUtil.walkToArea(TOAD_AREA);

            if (TOAD_AREA.contains(Player.getPosition()) && Inventory.find(BELLOW).length > 0) {
                if (Inventory.find(inflatedBellows).length < 1) {
                    cQuesterV2.status = "Inflating Bellow";
                    PathingUtil.walkToTile(SWAMP_BUBBLE_TILE);
                    General.println("[Debug]: " + cQuesterV2.status);

                    Optional<GameObject> bubbles = Query.gameObjects()
                            .tileEquals(new WorldTile(2595, 2966, 0))
                            .nameContains("Swamp bubbles")
                            .findBestInteractable();

                    if (Utils.clickObj(bubbles, "Suck"))
                        Timer.waitCondition(() -> Inventory.find(inflatedBellows).length > 0, 8000, 12000);
                }
            }

            if (Inventory.find(inflatedBellows).length > 0 && Inventory.find(bloatedToad).length > 0) {
                cQuesterV2.status = "Making baloon";
                General.println("[Debug]: " + cQuesterV2.status);

                if (Inventory.find(inflatedBellows).length > 0 && Inventory.find(bloatedToad).length > 0) {
                    AccurateMouse.click(Inventory.find(inflatedBellows)[0], "Use");
                    General.sleep(General.random(100, 300));
                    RSItem[] invToad = Inventory.find(bloatedToad);

                    if (AccurateMouse.click(Inventory.find(bloatedToad)[0], "Use"))
                        Timer.waitCondition(() -> Inventory.find(bloatedToad).length < invToad.length, 8900, 12000);

                }
            }
        }
    }

    public void mineRocks() {
        RSNPC[] jubbly = NPCs.find(JUBBLY_ID);
        if (jubbly.length < 1 && Inventory.find(BALOON_TOAD).length == 0  &&
                Inventory.find(COOKED_JUBBLY).length ==0) {
            cQuesterV2.status = "Going to mine";
            General.println("[Debug]: " + cQuesterV2.status);
            if (!MINING_AREA.contains(Player.getPosition()) && Inventory.find(bloatedToad).length > 1
                    && Inventory.find(ROCKS).length < 1)
                PathingUtil.walkToArea(MINING_AREA);

            RSObject[] rocks = Objects.findNearest(20, 12564);
            if (Inventory.find(ROCKS).length < 3 && rocks.length > 0) {


                for (int i = 0; i < 3; i++) {

                    if (AccurateMouse.click(Objects.findNearest(20, 12564)[0], "Mine")) {
                        RSItem[] invRocks = Inventory.find(ROCKS);
                        Timer.abc2WaitCondition(() -> Inventory.find(ROCKS).length > invRocks.length, 10000, 15000);
                    }
                }
            }
        }
    }

    int JUBBLY_ID_4864 = 4864;

    public void baitBird() {
        Utils.equipItem(OGRE_BOW);
        Utils.equipItem(OGRE_ARROW);
        if (Inventory.find(BALOON_TOAD).length > 0  &&
        Inventory.find(COOKED_JUBBLY).length ==0 ) {

            PathingUtil.walkToArea(RANTZ_AREA);

            if (PathingUtil.makeLargerArea(RANTZ_AREA).contains(Player.getPosition())) {
                cQuesterV2.status = "Dropping balloon";
                General.println("[Debug]: " + cQuesterV2.status);
                Utils.blindWalkToTile(new RSTile(2633, 2970, 0));
                PathingUtil.movementIdle();

                RSItem[] baloon = Inventory.find(BALOON_TOAD);
                if (baloon.length > 0) {
                    if (AccurateMouse.click(baloon[0], "Drop")) {
                        cQuesterV2.status = "Waiting for Jubbly...";
                        General.println("[Debug]: " + cQuesterV2.status);
                        Timer.waitCondition(() -> NPCs.find(JUBBLY_ID).length > 0, 65000, 85000);
                    }
                }
            }

            RSNPC[] jubbly = NPCs.find(JUBBLY_ID);
            if (jubbly.length > 0) {
                cQuesterV2.status = "Jubbly appeared";
                General.println("[Debug]: " + cQuesterV2.status);
                if (!jubbly[0].isClickable())
                    DaxCamera.focus(jubbly[0]);

                if (AccurateMouse.click(jubbly[0], "Attack")) {
                    cQuesterV2.status = "Attacking Jubbly";
                    General.println("[Debug]: " + cQuesterV2.status);
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
                    Timer.waitCondition(() -> Inventory.find(RAW_JUBBLY).length > 0, 6000, 9000);
            }
        } else {
            inflateToads();
            mineRocks();
            makeBaloon();

        }
    }

    public void cookJubbly() {
        if (Inventory.find(RAW_JUBBLY).length > 0 &&
                Inventory.find(COOKED_JUBBLY).length==0) {
            cQuesterV2.status = "Cooking Jubbly";
            if (PathingUtil.walkToTile(new RSTile(2631, 2989, 0)))
                Timer.waitCondition(() -> Objects.find(30, 6895).length > 0, 8000, 12000);

            if (Objects.find(30, 6895).length > 0) {
                Timer.waitCondition(() -> Objects.find(30, 6895)[0]
                        .getPosition().distanceTo(Player.getPosition()) < 2, 12000);
                if (Utils.useItemOnObject(RAW_JUBBLY, 6895))
                    Timer.waitCondition(() -> Inventory.find(COOKED_JUBBLY).length > 0, 20000);
            }
        }
    }

    public void finishQuest() {
        if (Objects.findNearest(20, 12343).length < 1) {
            cQuesterV2.status = "Going to Lumbridge";
            General.println("[Debug]: " + cQuesterV2.status);
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
        if (Game.getSetting(173) == 0 || RSVarBit.get(1904).getValue() == 0) {
            buyItems();
            getItems();
            makeSpit();
            goToStart();
            inspectOgre();
        }
        else  if (Game.getSetting(173) == 1 && RSVarBit.get(1904).getValue() == 10) {
            goToRantz();
        }
        else  if (RSVarBit.get(1904).getValue() == 20) {
            goToLog();
        }
        else if (RSVarBit.get(1904).getValue() == 30) {
            goToLog();
        }
        else  if (RSVarBit.get(6719).getValue() == 30) { // alternatively 1904 is 40 during cutscene
            General.sleep(General.random(8000, 12000)); // cut scene
        } else if (RSVarBit.get(1904).getValue() == 50) {
            cutLog();
        } else if (RSVarBit.get(1904).getValue() == 60) {
            cutLog();
        } else if (RSVarBit.get(1904).getValue() == 70) {
            talkToRantz3();
        } else if (RSVarBit.get(1904).getValue() == 80) {
            goToTree();
        } else if (RSVarBit.get(1904).getValue() == 90) {
            General.sleep(General.random(12000, 22000));
        } else if (RSVarBit.get(1904).getValue() == 100) {
            goWithKids();
        } else if (RSVarBit.get(1904).getValue() == 110) {
            mineRocks();
            inflateToads();
            makeBaloon();
        } else if (RSVarBit.get(1904).getValue() == 120 ||
                RSVarBit.get(1904).getValue() == 130 || RSVarBit.get(1904).getValue() == 140) {
            makeBaloon();
            baitBird();
        } else if (RSVarBit.get(1904).getValue() == 150) {
            makeBaloon();
            baitBird();
            cookJubbly();
        } else if (RSVarBit.get(1904).getValue() == 160) {
            finishQuest();
        }

        if (RSVarBit.get(1904).getValue() == 170) {
            Utils.closeQuestCompletionWindow();
            Utils.continuingChat();
            cQuesterV2.taskList.remove(this);

        }

    }

    @Override
    public String questName() {
        return "RFD Skratch";
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
