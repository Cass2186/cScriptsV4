package scripts.QuestPackages.BigChompyBirdHunting;

import dax.walker.utils.AccurateMouse;
import dax.walker.utils.camera.DaxCamera;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.*;
import org.tribot.script.sdk.ChatScreen;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.MakeScreen;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.GameObject;
import org.tribot.script.sdk.types.WorldTile;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.RfdSkratch.RfdSkratch;
import scripts.QuestSteps.BuyItemsStep;
import scripts.QuestSteps.QuestTask;
import scripts.Tasks.Priority;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class BigChompyBirdHunting implements QuestTask {

    private static BigChompyBirdHunting quest;

    public static BigChompyBirdHunting get() {
        return quest == null ? quest = new BigChompyBirdHunting() : quest;
    }

    int mithrilAxe = 1355;
    int adamantAxe = 1357;
    int feather = 314;
    int knife = 946;
    int chisel = 1755;
    int onion = 1957;
    int equaLeaves = 2128;
    int wolfBones = 2859;
    int cabbage = 1965;
    int tomato = 1982;
    int potato = 1942;
    int doogleLeaves = 1573;
    int lobster = 379;
    int wolfBoneArrowTips = 2861;
    int acheyLogs = 2862;
    int acheyArrowShaft = 2864;
    int flightedOgreArrow = 2865;
    int ogreArrow = 2866;
    int bellows = 2871;
    int[] inflatedBellows = {2872, 2873, 2874};
    int bloatedToad = 2875;
    int orgreBow = 2883;
    int rawChompy = 2876;
    int seasonedChompy = 2882;

    boolean addOnion = false;
    boolean addDoogleLeaves = false;
    boolean addTomato = false;
    boolean addCabbage = false;
    boolean addPotato = false;
    boolean addEquaLeaves = false;

    RSArea START_AREA = new RSArea(new RSTile(2626, 2986, 0), new RSTile(2636, 2975, 0));
    RSArea CAVE_ENTRANCE_AREA = new RSArea(new RSTile(2633, 2991, 0), new RSTile(2628, 2996, 0));
    RSArea INSIDE_CAVE = new RSArea(new RSTile(2632, 9399, 0), new RSTile(2659, 9378, 0));
    RSArea BUGS_AREA = new RSArea(new RSTile(2636, 9395, 0), new RSTile(2644, 9388, 0));
    RSArea CAVE_EXIT = new RSArea(new RSTile(2645, 9381, 0), new RSTile(2648, 9378, 0));
    RSArea TOAD_AREA = new RSArea(new RSTile(2592, 2972, 0), new RSTile(2605, 2961, 0));
    //RSArea TOAD_AREA = new RSArea(new RSTile(2592, 2972, 0), new RSTile(2605, 2967, 0));
    RSArea HUNT_AREA = new RSArea(new RSTile(2634, 2967, 0), new RSTile(2638, 2963, 0));
    RSArea HIDE_AREA = new RSArea(new RSTile(2630, 2978, 0), new RSTile(2633, 2973, 0));

    public boolean checkLevel() {
        if (Skills.getCurrentLevel(Skills.SKILLS.RANGED) < 30 ||
                Skills.getCurrentLevel(Skills.SKILLS.COOKING) < 30 ||
                Skills.getCurrentLevel(Skills.SKILLS.FLETCHING) < 5) {
            General.println("[Debug]: Missing Cooking or Ranged Level (30) or 5 fletching");
            return false;
        }
        return true;
    }

    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.FEATHERS, 100, 35),
                    new GEItem(ItemID.KNIFE, 1, 500),
                    new GEItem(ItemID.CHISEL, 1, 500),
                    new GEItem(ItemID.ONION, 1, 300),
                    new GEItem(ItemID.EQUA_LEAVES, 1, 500),
                    new GEItem(ItemID.WOLF_BONES, 7, 100),
                    new GEItem(ItemID.CABBAGE, 1, 200),
                    new GEItem(ItemID.TOMATO, 1, 300),
                    new GEItem(ItemID.POTATO, 1, 300),
                    new GEItem(ItemID.DOOGLE_LEAVES, 1, 300),

                    new GEItem(ItemID.IRON_AXE, 1, 500),
                    new GEItem(ItemID.FELDIP_HILLS_TELEPORT, 5, 100),
                    new GEItem(ItemID.LOBSTER, 15, 50),

                    new GEItem(ItemID.RING_OF_DUELING[0], 2, 30),
                    new GEItem(ItemID.STAMINA_POTION[0], 3, 15),
                    new GEItem(ItemID.RING_OF_WEALTH[0], 1, 25)
            )
    );


    public void buyItems() {
        cQuesterV2.status = "Buying Items";
        General.println("[Debug]: Buying Items");
        BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);
        buyStep.buyItems();
    }

    public void getItems() {
        cQuesterV2.status = "Getting Items";
        General.println("[Debug]: " + cQuesterV2.status);
        BankManager.open(true);
        BankManager.checkEquippedGlory();
        BankManager.depositAll(true);
        BankManager.withdraw(1, true, ItemID.IRON_AXE);
        BankManager.withdraw(100, true, feather);
        BankManager.withdraw(1, true, knife);
        BankManager.withdraw(1, true, chisel);
        BankManager.withdraw(1, true, onion);
        BankManager.withdraw(1, true, equaLeaves);
        BankManager.withdraw(5, true, wolfBones);
        BankManager.withdraw(1, true, cabbage);
        BankManager.withdraw(1, true, tomato);
        BankManager.withdraw(1, true, potato);
        BankManager.withdraw(1, true, doogleLeaves);
        BankManager.withdraw(1, true, ItemID.RING_OF_DUELING[0]);
        BankManager.withdraw(2, true, BankManager.STAMINA_POTION[0]);
        BankManager.withdraw(4, true, lobster);
        BankManager.close(true);
    }

    public void startQuest() {
        cQuesterV2.status = "Starting Quest";
        PathingUtil.walkToArea(START_AREA);
        if (NpcChat.talkToNPC("Rantz")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Ok, I'll make you some 'stabbers'.");
            NPCInteraction.handleConversation();
        }
    }

    public void makeArrowTips() {
        cQuesterV2.status = "Making ArrowTips";
        if (Inventory.find(wolfBones).length > 0) {
            if (Utils.useItemOnItem(chisel, wolfBones))
                Timer.waitCondition(() -> Interfaces.get(270, 14) != null, 5000, 8000);

            if (Interfaces.get(270, 14) != null)
                if (Interfaces.get(270, 14).click())
                    Timer.abc2WaitCondition(() -> Inventory.find(wolfBones).length < 1, 8000, 12000);
        }
    }

    public void makeArrowShaft() {
        if (Inventory.find(ogreArrow).length < 1) {
            cQuesterV2.status = "Cutting logs";
            if (Inventory.find(acheyLogs).length < 1 && Inventory.find(acheyArrowShaft).length < 1) {

                for (int i = 0; i < 15; i++) {
                    if (Inventory.find(acheyLogs).length < 5 && Inventory.find(flightedOgreArrow).length < 1) {
                        General.sleep(100);
                        RSItem[] invItem1 = Inventory.find(acheyLogs);
                        if (Utils.clickObj(2023, "Chop")) {
                            Timer.waitCondition(() -> Inventory.find(acheyLogs).length > invItem1.length, 7000, 9000);
                            Waiting.waitNormal(350, 45);
                        }
                    } else
                        break;
                }
            }
        }

        if (Inventory.find(acheyLogs).length > 1) {
            cQuesterV2.status = "Making arrow shafts";
            if (Utils.useItemOnItem(knife, acheyLogs))
                Timer.waitCondition(MakeScreen::isOpen, 5000, 7000);

            if (MakeScreen.isOpen() && MakeScreen.makeAll(ItemID.OGRE_ARROW_SHAFT))
                Timer.waitCondition(() -> Inventory.find(acheyLogs).length < 1, 8000, 12000);
        }
    }

    public void makeArrows() {
        cQuesterV2.status = "Adding feathers";
        if (Inventory.find(flightedOgreArrow).length < 1 && Inventory.find(feather).length > 0) {
            if (Utils.useItemOnItem(feather, acheyArrowShaft))
                Timer.waitCondition(() -> Interfaces.get(270, 14) != null, 5000, 7000);

            if (Interfaces.get(270, 14) != null)
                if (Interfaces.get(270, 14).click())
                    Timer.waitCondition(() -> Inventory.find(acheyArrowShaft).length < 1, 10000, 15000);
        }

        if (Inventory.find(flightedOgreArrow).length > 0 && Inventory.find(ogreArrow).length < 1) {
            cQuesterV2.status = "Adding arrowheads";
            if (Utils.useItemOnItem(wolfBoneArrowTips, flightedOgreArrow))
                Timer.waitCondition(() -> Interfaces.get(270, 14) != null, 5000, 7000);

            if (Interfaces.get(270, 14) != null)
                if (Interfaces.get(270, 14).click())
                    Timer.waitCondition(() -> Inventory.find(flightedOgreArrow).length < 1, 10000, 15000);
        }
    }

    public void returnToRantz() {
        if (Inventory.find(ogreArrow).length > 0) {
            cQuesterV2.status = "Going to Rantz";
            PathingUtil.walkToArea(START_AREA);
            if (NpcChat.talkToNPC("Rantz")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("How do we make the chompys come?");
                NPCInteraction.handleConversation("What are 'fatsy toadies'?");
                NPCInteraction.handleConversation("Where do we put the fatsy toadies?");
                NPCInteraction.handleConversation("What do you mean 'sneaky..sneaky, stick da chompy?'");
                NPCInteraction.handleConversation("Ok, thanks.");
            }
        }
    }

    int UNLOCKED_CHEST_ID = 3378;

    public void goToChest() {
        if (!CAVE_ENTRANCE_AREA.contains(Player.getPosition()) && !INSIDE_CAVE.contains(Player.getPosition())) {
            cQuesterV2.status = "Walking to cave entrance";
            PathingUtil.walkToArea(CAVE_ENTRANCE_AREA);
        }

        if (Utils.clickObj("Cave entrance", "Enter"))
            Timer.waitCondition(() -> INSIDE_CAVE.contains(Player.getPosition()), 7000, 10000);

        if (!BUGS_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Going to Bugs";
            PathingUtil.walkToArea(BUGS_AREA);
        }

        if (INSIDE_CAVE.contains(Player.getPosition())) { //was bugs area check before
            if (Utils.clickNPC("Bugs", "Talk")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
            for (int i = 0; i < 3; i++) {
                RSObject[] chest = Objects.findNearest(20, "Locked Ogre chest");
                if (chest.length > 0 && Inventory.find(bellows).length < 1) {
                    cQuesterV2.status = "Unlocking chest";
                    if (Utils.clickObj("Locked Ogre chest", "Unlock")) {
                        Timer.waitCondition(() -> Objects.find(20, UNLOCKED_CHEST_ID).length > 0, 7000, 9000);
                    }
                    RSObject[] unlockedChest = Objects.find(20, UNLOCKED_CHEST_ID);
                    if (unlockedChest.length > 0) {
                        break;
                    }
                }
            }
            RSObject[] unlockedChest = Objects.find(20, UNLOCKED_CHEST_ID);
            if (unlockedChest.length > 0) {
                cQuesterV2.status = "Searching chest";
                if (Utils.clickObj(UNLOCKED_CHEST_ID, "Search")) {
                    NPCInteraction.waitForConversationWindow();
                    Keyboard.typeString(" ");
                    Timer.waitCondition(() -> Inventory.find(bellows).length > 0, 3000, 5000);
                }
            }
        }
    }

    RSTile BUBBLE_TILE = new RSTile(2595, 2966, 0);

    public void inflateToads() {
        if (Inventory.find(bellows).length > 0) {
            if (INSIDE_CAVE.contains(Player.getPosition())) {
                cQuesterV2.status = "Leaving cave";
                PathingUtil.walkToArea(CAVE_EXIT);

                if (Utils.clickObj("Cave exit", "Walk through"))
                    Timer.waitCondition((() -> CAVE_ENTRANCE_AREA.contains(Player.getPosition())), 8900, 12000);
            }

        } else if (Inventory.find(inflatedBellows).length == 0){
            Log.log("[Debug]: Missing bellows");
            return;
        }

        if (!TOAD_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Going to Toads";
            PathingUtil.walkToTile(new RSTile(2595, 2967, 0));
            Timer.waitCondition(() -> TOAD_AREA.contains(Player.getPosition()), 8900, 12000);
        }
        while (TOAD_AREA.contains(Player.getPosition()) && Inventory.find(bloatedToad).length < 3) {
            General.sleep(150);
            if (Inventory.find(inflatedBellows).length < 1) {
                cQuesterV2.status = "Inflating bellow";
                Optional<GameObject> bubbles = Query.gameObjects()
                        .tileEquals(new WorldTile(2595, 2966, 0))
                        .nameContains("Swamp bubbles")
                        .findBestInteractable();

                if (Utils.clickObj(bubbles, "Suck"))
                    Timer.waitCondition(() -> Inventory.find(inflatedBellows).length > 0, 7000, 9000);

            }

            if (Inventory.find(inflatedBellows).length > 0 && Inventory.find(bloatedToad).length < 3) {
                cQuesterV2.status = "Getting Toads - line 282";
                RSNPC[] toad = NPCs.findNearest(1473);
                RSItem[] invToad = Inventory.find(bloatedToad);
                if (toad.length > 0) {
                    if (!toad[0].isClickable())
                        toad[0].adjustCameraTo();

                    if (AccurateMouse.click(toad[0], "Inflate"))
                        Timer.waitCondition(() -> Inventory.find(bloatedToad).length > invToad.length, 8000, 12000);
                    Utils.shortSleep();
                }
            }
        }
    }

    public void returnToRantz2() {
        if (Inventory.find(bloatedToad).length > 2) {
            cQuesterV2.status = "Going to Rantz";
            PathingUtil.walkToArea(START_AREA);
            if (START_AREA.contains(Player.getPosition())) {
                NpcChat.talkToNPC("Rantz");
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();

            }
        }
    }

    public void hunt() {
        if (Inventory.find(bloatedToad).length < 1)
            inflateToads();

        if (!HUNT_AREA.contains(Player.getPosition()) && Inventory.find(bloatedToad).length > 0) {
            cQuesterV2.status = "Going to hunt area";
            PathingUtil.walkToTile(new RSTile(2635, 2965, 0));
        }
        RSItem[] invToad = Inventory.find(bloatedToad);
        if (HUNT_AREA.contains(Player.getPosition()) && invToad.length > 0) {
            cQuesterV2.status = "Dropping toad";

            if (AccurateMouse.click(invToad[0], "Drop")) {
                Timer.waitCondition(() -> NPCs.findNearest(1474).length > 0, 8000, 12000); // 1474 = toad ID
                General.sleep(General.random(500, 2000));
                PathingUtil.walkToArea(HIDE_AREA);
                cQuesterV2.status = "Waiting...";
                Timer.waitCondition(() -> HIDE_AREA.contains(Player.getPosition()), 8000, 12000);
                Timer.waitCondition(() -> NPCs.findNearest(1475).length > 0 || NPCs.findNearest(1474).length < 1, 60000, 80000);
                Utils.shortSleep();
            }
        }
    }

    public void rantzHunt() {
        if (Inventory.find(bloatedToad).length < 1)
            inflateToads();

        if (!HUNT_AREA.contains(Player.getPosition()) && Inventory.find(bloatedToad).length > 0) {
            cQuesterV2.status = "Going to hunt area";
            PathingUtil.walkToTile(new RSTile(2635, 2965, 0), 2, false);
        }
        RSItem[] bloatedTd = Inventory.find(bloatedToad);
        if (HUNT_AREA.contains(Player.getPosition()) && bloatedTd.length > 0) {
            if (AccurateMouse.click(bloatedTd[0], "Drop")) {
                Timer.waitCondition(() -> NPCs.findNearest(1474).length > 0, 8000, 12000); // 1474 = toad ID
                General.sleep(General.random(500, 2000));
                PathingUtil.walkToArea(HIDE_AREA);
                cQuesterV2.status = "Waiting...";
                Timer.waitCondition(() -> HIDE_AREA.contains(Player.getPosition()), 8000, 12000);
                Timer.waitCondition(() -> Game.getSetting(293) == 40 || NPCs.findNearest(1474).length < 1, 60000, 80000);
                Utils.shortSleep();
            }
        }
    }

    public void returnToRantz3() {
        cQuesterV2.status = "Going to Rantz";
        PathingUtil.walkToArea(START_AREA);
        if (NpcChat.talkToNPC("Rantz")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Come on, let me have a go...");
            NPCInteraction.handleConversation("I'm actually quite strong...please let me try.");
            NPCInteraction.handleConversation();
        }
    }

    public void equipBow() {
        Utils.equipItem(orgreBow);
        Utils.equipItem(ogreArrow);
    }

    public void kill() {
        cQuesterV2.status = "Kill Bird";
        General.println("[Debug]: " + cQuesterV2.status);
        RSNPC[] bird = NPCs.find(1475);
        RSNPC[] chompy = NPCs.find("Chompy Bird");
        if (bird.length > 0) {
            if (!bird[0].isClickable())
                DaxCamera.focus(bird[0]);


            if (chompy.length > 0 && AccurateMouse.click(bird[0], "Attack"))
                Timer.waitCondition(() -> chompy[0].getHealthPercent() < 3, 25000, 35000);
        }

        if (chompy.length > 0 && chompy[0].getHealthPercent() < 5)
            Timer.waitCondition(() -> NPCs.find(1476).length > 0, 15000, 20000);

        RSNPC[] pluckableBird = NPCs.find(1476);

        if (pluckableBird.length > 0 && Utils.clickNPC(1476, "Pluck")) {
            General.println("[Debug]: Plucking bird");
            Timer.waitCondition(() -> GroundItems.find(rawChompy).length > 0, 7000, 9000);

        }
        General.println("[Debug]: Looting raw bird");
        if (Utils.clickGroundItem(rawChompy)) {
            Timer.abc2WaitCondition(() -> Inventory.find(rawChompy).length > 0, 6000, 9000);
        }
    }

    public void goToKids() {
        cQuesterV2.status = "Getting Kids' ingredients";
        if (!CAVE_ENTRANCE_AREA.contains(Player.getPosition()) && !INSIDE_CAVE.contains(Player.getPosition()))
            PathingUtil.walkToArea(CAVE_ENTRANCE_AREA);

        if (CAVE_ENTRANCE_AREA.contains(Player.getPosition()) || !INSIDE_CAVE.contains(Player.getPosition())) {
            RSObject[] caveEntrance = Objects.findNearest(20, "Cave entrance");
            if (caveEntrance.length > 0) {
                if (AccurateMouse.click(caveEntrance[0], "Enter"))
                    Timer.abc2WaitCondition(() -> INSIDE_CAVE.contains(Player.getPosition()), 8000, 12000);
            }

        }
        if (!BUGS_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Going to Bugs";
            PathingUtil.walkToArea(BUGS_AREA);
        }

        RSNPC[] bugs = NPCs.findNearest("Bugs");
        if (bugs.length > 0) {
            if (!bugs[0].isClickable())
                DaxCamera.focus(bugs[0]);

            if (AccurateMouse.click(bugs[0], "Talk")) {
                NPCInteraction.waitForConversationWindow();
                determineIngredients();
                NPCInteraction.handleConversation();
                General.sleep(General.random(1500, 4000));
            }

            RSNPC[] fycie = NPCs.findNearest("Fycie");
            if (fycie.length > 0) {
                if (!fycie[0].isClickable())
                    DaxCamera.focus(fycie[0]);

                if (AccurateMouse.click(fycie[0], "Talk")) {
                    NPCInteraction.waitForConversationWindow();
                    determineIngredients();
                    General.sleep(General.random(1500, 4000));
                }
            }
        }
    }

    public void getRantzIngredient() {
        if (INSIDE_CAVE.contains(Player.getPosition())) {
            cQuesterV2.status = "Leaving cave";
            PathingUtil.walkToArea(CAVE_EXIT);
            if (Utils.clickObj("Cave exit", "Walk through"))
                Timer.waitCondition((() -> CAVE_ENTRANCE_AREA.contains(Player.getPosition())), 8900, 12000);
        }
        cQuesterV2.status = "Going to Rantz";
        PathingUtil.walkToArea(START_AREA);


        if (NpcChat.talkToNPC("Rantz")) {
            NPCInteraction.waitForConversationWindow();
            for (int i = 0; i < 5; i++) {
                RSInterface myChatCont = Interfaces.get(217, 4);
                RSInterface theirChatCont = Interfaces.get(231, 4);
                if (theirChatCont != null){
                    ChatScreen.clickContinue();
                    Waiting.waitNormal(750,50);
                    determineIngredients();
                }

                General.sleep(General.random(1500, 4000));
            }
        }

    }

    public void determineIngredients() {
        RSInterface chat = Interfaces.get(231, 5);
        if (chat != null) {
            if (chat.getText().toLowerCase().contains("equa leaves")) {
                addEquaLeaves = true;
                General.println("[Debug]: Adding equa leaves");
            } else if (chat.getText().toLowerCase().contains("tomato")) {
                addTomato = true;
                General.println("[Debug]: Adding tomato");
            } else if (chat.getText().toLowerCase().contains("cabbage")) {
                addCabbage = true;
                General.println("[Debug]: Adding cabbage");
            } else if (chat.getText().toLowerCase().contains("potato")) {
                addPotato = true;
                General.println("[Debug]: Adding potato");
            } else if (chat.getText().toLowerCase().contains("doogle")) {
                addDoogleLeaves = true;
                General.println("[Debug]: Adding doogle leaves");
            } else if (chat.getText().toLowerCase().contains("onion")) {
                addOnion = true;
                General.println("[Debug]: Adding onion");
            } else {

                General.println("[Debug]: Failed to get ingredient from this chat");
            }
        }
    }

    public void cook() {
        RSObject[] spit = Objects.findNearest(30, "Ogre spit-roast");
        if (spit.length > 0) {
            PathingUtil.walkToTile(spit[0].getPosition());
            if (!addOnion) {
                if (Inventory.find(onion).length > 0) {
                    AccurateMouse.click(Inventory.find(onion)[0], "Drop");
                    General.sleep(General.random(300, 800));
                }
            }
            if (!addDoogleLeaves) {
                RSItem[] invLeaves = Inventory.find(doogleLeaves);
                if (invLeaves.length > 0 && invLeaves[0].click("Drop"))
                    General.sleep(300, 800);
            }
            if (!addPotato) {
                RSItem[] invLeaves = Inventory.find(potato);
                if (invLeaves.length > 0 && invLeaves[0].click("Drop"))
                    General.sleep(300, 800);
            }
            if (!addTomato) {
                RSItem[] invLeaves = Inventory.find(tomato);
                if (invLeaves.length > 0 && invLeaves[0].click("Drop"))
                    General.sleep(300, 800);
            }
            if (!addCabbage) {
                RSItem[] invLeaves = Inventory.find(cabbage);
                if (invLeaves.length > 0 && invLeaves[0].click("Drop"))
                    General.sleep(300, 800);

            }
            if (!addEquaLeaves) {
                RSItem[] invLeaves = Inventory.find(equaLeaves);
                if (invLeaves.length > 0 && invLeaves[0].click("Drop"))
                    General.sleep(300, 800);
            }
            if (Utils.useItemOnObject(rawChompy, "Ogre spit-roast"))
                Timer.abc2WaitCondition(() -> Inventory.find(seasonedChompy).length > 0, 10000, 15000);
        }
    }

    public void finishQuest() {
        cQuesterV2.status = "Going to Rantz";

        PathingUtil.walkToArea(START_AREA);

        if (NpcChat.talkToNPC("Rantz")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }
    }

    int GAME_SETTING = 293;

    @Override
    public void execute() {

        General.println("Game setting 293: " + Game.getSetting(293));

        if (Game.getSetting(293) == 0) {
            buyItems();
            getItems();
            startQuest();
        }
        if (Game.getSetting(293) == 5) {
            makeArrowTips();
            makeArrowShaft();
            makeArrows();
            returnToRantz();
        }
        if (Game.getSetting(293) == 10) {
            returnToRantz();
        }
        if (Game.getSetting(293) == 15) {
            goToChest();
        }
        if (Game.getSetting(293) == 20) {
            goToChest();
            inflateToads();
            returnToRantz2();
        }
        if (Game.getSetting(293) == 25) {
            rantzHunt();
            returnToRantz2();
        }
        if (Game.getSetting(293) == 30) {
            rantzHunt();
            returnToRantz2();
        }
        if (Game.getSetting(293) == 40) {
            returnToRantz3();
        }
        if (Game.getSetting(293) == 45) {
            equipBow();
            hunt();
            kill();
        }
        if (Game.getSetting(293) == 50) {
            kill();
            returnToRantz3();
        }
        if (Game.getSetting(293) == 55) {
           goToKids();
            getRantzIngredient();
            cook();
        }
        if (Game.getSetting(293) == 60) {
            finishQuest();
        }
        if (Game.getSetting(293) == 65) {
            Utils.closeQuestCompletionWindow();
            cQuesterV2.taskList.remove(this);
        }
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
    public String questName() {
        return "Big Chompy Bird Hunting";
    }

    @Override
    public boolean checkRequirements() {
        return checkLevel();
    }
}
