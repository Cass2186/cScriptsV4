package scripts.QuestPackages.GrandTree;

import dax.walker.utils.AccurateMouse;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.types.*;
import org.tribot.script.sdk.Log;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.TheFeud.TheFeud;
import scripts.QuestSteps.BuyItemsStep;
import scripts.QuestSteps.QuestTask;
import scripts.Tasks.Priority;

import java.util.ArrayList;
import java.util.Arrays;

public class GrandTree implements QuestTask {

    private static GrandTree quest;

    public static GrandTree get() {
        return quest == null ? quest = new GrandTree() : quest;
    }

    int mindRune = 558;
    int fireRune = 554;
    int staffOfAir = 1381;
    int lobster = 379;
    int hazelmeresScroll = 786;
    int lumberOrder = 787;
    int gloughsKey = 788;

    RSTile gateTile = new RSTile(2944, 3041, 0);

    RSArea kingArea = new RSArea(new RSTile(2462, 3498, 0), new RSTile(2469, 3492, 0));
    RSArea jailArea = new RSArea(new RSTile(2465, 3497, 3), new RSTile(2468, 3494, 3));
    RSArea gloughArea = new RSArea(new RSTile(2475, 3465, 1), new RSTile(2484, 3462, 1));
    RSArea hazelmeerBottom = new RSArea(new RSTile(2675, 3088, 0), new RSTile(2679, 3086, 0));
    RSArea gloughUnder = new RSArea(new RSTile(2474, 3464, 0), new RSTile(2479, 3461, 0));
    RSArea dockArea = new RSArea(new RSTile(2998, 3044, 0), new RSTile(3002, 3042, 0));
    RSArea strongholdEntrance = new RSArea(new RSTile(2455, 3383, 0), new RSTile(2458, 3381, 0));
    RSArea femiLeavesYouHere = new RSArea(new RSTile(2455, 3436, 0), new RSTile(2466, 3391, 0));
    RSArea anitaLadder = new RSArea(new RSTile(2388, 3512, 0), new RSTile(2391, 3510, 0));
    RSArea anitaHouse = new RSArea(new RSTile(2387, 3516, 1), new RSTile(2391, 3513, 1));
    RSArea glough2ndFloor = new RSArea(new RSTile(2488, 3463, 2), new RSTile(2485, 3467, 2));
    RSArea BEFORE_GATE_AREA = new RSArea(new RSTile(2944, 3038, 0), new RSTile(2911, 3062, 0));
    RSArea AFTER_GATE_AREA = new RSArea(new RSTile(2945, 3044, 0), new RSTile(2955, 3036, 0));
    final RSTile[] PATH_TO_FOREMAN = new RSTile[]{new RSTile(2946, 3042, 0), new RSTile(2949, 3041, 0), new RSTile(2951, 3038, 0), new RSTile(2954, 3036, 0), new RSTile(2957, 3033, 0), new RSTile(2960, 3032, 0), new RSTile(2963, 3032, 0), new RSTile(2966, 3033, 0), new RSTile(2969, 3036, 0), new RSTile(2970, 3039, 0), new RSTile(2973, 3042, 0), new RSTile(2976, 3043, 0), new RSTile(2979, 3043, 0), new RSTile(2981, 3047, 0), new RSTile(2984, 3048, 0), new RSTile(2987, 3049, 0), new RSTile(2990, 3049, 0), new RSTile(2993, 3048, 0), new RSTile(2996, 3049, 0), new RSTile(2999, 3049, 0), new RSTile(3001, 3046, 0)};
    RSArea DOCKS = new RSArea(new RSTile(3002, 3037, 0), new RSTile(2987, 3049, 0));
    RSArea HUT_AREA = new RSArea(new RSTile(2960, 3020, 0), new RSTile(2951, 3034, 0));
    RSArea WHOLE_STRONGHOLD = new RSArea(new RSTile(2435, 3514, 0), new RSTile(2486, 3392, 0));

    String KING_CHAT1 = "You seem worried, what's up?";
    String KING_CHAT2 = "I'd be happy to help!";

    String CAPTAIN_ERRDO = "Take me to Karamja please!";

    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(   ItemID.SUMMER_PIE, 1, 50),
                    new GEItem(ItemID.RING_OF_DUELING[0], 2, 100),

                    new GEItem( ItemID.NECKLACE_OF_PASSAGE[0], 2, 100),
                    new GEItem(ItemID.MIND_RUNE, 300, 20),
                    new GEItem(ItemID.FIRE_RUNE, 1200, 20),
                    new GEItem(ItemID.STAFF_OF_AIR, 1, 50),
                    new GEItem(ItemID.LOBSTER, 20, 50),
                    new GEItem(ItemID.AMULET_OF_GLORY[2], 2, 30),
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
        BankManager.withdraw(1, true, ItemID.NECKLACE_OF_PASSAGE[0]);
        BankManager.withdraw(1, true, ItemID.RING_OF_DUELING[0]);
        BankManager.withdraw(400, true, ItemID.MIND_RUNE);
        BankManager.withdraw(1200, true, ItemID.FIRE_RUNE);
        BankManager.withdraw(1, true, ItemID.STAFF_OF_AIR);
        BankManager.withdraw(2, true, ItemID.STAMINA_POTION[0]);
        BankManager.withdraw(12, true, ItemID.LOBSTER);
        BankManager.withdraw(1000, true, 995);
        if (SUMMER_PIE) {
            BankManager.withdraw(1, true, ItemID.SUMMER_PIE);
        }
        BankManager.close(true);
    }

    public void getItemsFight() {
        cQuesterV2.status = "Getting Items";
        General.println("[Debug]: " + cQuesterV2.status);
        BankManager.open(true);
        BankManager.checkEquippedGlory();
        BankManager.depositAll(true);
        BankManager.withdraw(1, true, ItemID.NECKLACE_OF_PASSAGE[0]);
        BankManager.withdraw(400, true, ItemID.MIND_RUNE);
        BankManager.withdraw(1200, true, ItemID.FIRE_RUNE);
        BankManager.withdraw(1, true, ItemID.STAFF_OF_AIR);
        BankManager.withdraw(1, true, ItemID.STAMINA_POTION[0]);
        BankManager.withdraw(12, true, ItemID.LOBSTER);
        BankManager.withdraw(1, true, 789);
        BankManager.withdraw(1, true, 790);
        BankManager.withdraw(1, true, 791);
        BankManager.withdraw(1, true, 792);
        if (SUMMER_PIE) {
            BankManager.withdraw(1, true, ItemID.SUMMER_PIE);
        }
        BankManager.close(true);
    }

    public void goToKing() {
        cQuesterV2.status = "Going to King";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(kingArea);
    }

    public void startQuest() {
        cQuesterV2.status = "Starting Quest";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(kingArea);

        if (kingArea.contains(Player.getPosition())) {
            NpcChat.talkToNPC("King Narnode Shareen");
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation(KING_CHAT1);
            NPCInteraction.handleConversation();
            General.sleep(2000, 3000);
            NPCInteraction.waitForConversationWindow();
          //  NPCInteraction.handleConversation();
            NPCInteraction.handleConversation(KING_CHAT2);
            NPCInteraction.handleConversation();
         //   NPCInteraction.handleConversation();
            General.sleep(2000, 3000);
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            General.sleep(4000, 6000);
        }
    }

    public void step2() {
        cQuesterV2.status = "Going to hazelmere";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(hazelmeerBottom, false);
        Timer.waitCondition(() -> hazelmeerBottom.contains(Player.getPosition()), 5000, 7000);
        if (Utils.clickObj("Ladder", "Climb-up"))
           Timer.waitCondition(()-> Player.getPosition().getPlane() ==1 , 8000,12000);

        if (NpcChat.talkToNPC("Hazelmere")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();

        }
    }


    String[] KING_CHAT3 = { // doesn't work
            "None of the above.",
            "A man came to me with the King's seal.",
            "I gave the man Daconia rocks.",
            "And Daconia rocks will kill the tree!"
    };

    public void step3() {
        cQuesterV2.status = "Returning to King";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(kingArea);
        if (kingArea.contains(Player.getPosition())) {
            if (NpcChat.talkToNPC("King Narnode Shareen")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("I think so!");
                NPCInteraction.handleConversation("None of the above.");
                NPCInteraction.handleConversation("None of the above.");
                NPCInteraction.handleConversation("A man came to me with the King's seal.");
                NPCInteraction.handleConversation("I gave the man Daconia rocks.");
                NPCInteraction.handleConversation("And Daconia rocks will kill the tree!");
                NPCInteraction.handleConversation();
                General.sleep(General.randomSD(1000, 7000, 3500, 1200));
            }
        }
    }

    public void goToGlough() {
        cQuesterV2.status = "Going to Glough";
        General.println("[Debug]: " + cQuesterV2.status);
        if (!gloughArea.contains(Player.getPosition()))
            PathingUtil.walkToArea(gloughUnder);

        if (gloughUnder.contains(Player.getPosition()))
            if (Utils.clickObj("Ladder", "Climb-up"))
                Timer.abc2WaitCondition(() -> gloughArea.contains(Player.getPosition()), 7000, 9000);
    }

    public void step4() {
        cQuesterV2.status = "Going to Glough";
        General.println("[Debug]: " + cQuesterV2.status);

        goToGlough();

        if (gloughArea.contains(Player.getPosition())) {
            if (NpcChat.talkToNPC("Glough")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
            if (Utils.clickObj("Ladder", "Climb-down"))
                Timer.abc2WaitCondition(() -> gloughUnder.contains(Player.getPosition()), 7000, 9000);
        }
    }

    public void step5() {
        cQuesterV2.status = "Going to King";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(kingArea);

        if (kingArea.contains(Player.getPosition())) {
            cQuesterV2.status = "Talking to King";
            General.println("[Debug]: " + cQuesterV2.status);
            if (NpcChat.talkToNPC("King Narnode Shareen")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
              //  NPCInteraction.handleConversation();
                General.sleep(General.randomSD(1000, 7000, 3500, 1200));
            }
        }
    }

    public void talkToCharlie() {
        cQuesterV2.status = "Going to Charlie";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(jailArea);

        if (jailArea.contains(Player.getPosition())) {
            cQuesterV2.status = "Talking to Charlie";
            if (NpcChat.talkToNPC("Charlie")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
             //   NPCInteraction.handleConversation();
            //    NPCInteraction.handleConversation();
                General.sleep(General.randomSD(1000, 7000, 3500, 1200));
            }
        }
    }

    public void step7() {
        goToGlough();

        if (gloughArea.contains(Player.getPosition())) {
            cQuesterV2.status = "Searching for book";
            General.println("[Debug]: " + cQuesterV2.status);

            if (Utils.clickObj(2434, "Open"))
                Timer.abc2WaitCondition(() -> Objects.findNearest(20, 2435).length > 0, 6000, 9000);

            if (Utils.clickObj(2435, "Search")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();

            }
        }
    }

    public void step8() {
        cQuesterV2.status = "Accusing Glough";
        General.println("[Debug]: " + cQuesterV2.status);
        if (NpcChat.talkToNPC("Glough")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            General.sleep(General.random(9000, 15000));
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            General.sleep(General.random(12000, 15000));
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            Utils.modSleep(); //need
        }

        if (NPCInteraction.isConversationWindowUp())
            NPCInteraction.handleConversation();
    }


    public void step9() {
        if (!BEFORE_GATE_AREA.contains(Player.getPosition()) && !AFTER_GATE_AREA.contains(Player.getPosition()) && !DOCKS.contains(Player.getPosition())) {
            cQuesterV2.status = "Talking to Captain Errdo";
            General.println("[Debug]: " + cQuesterV2.status);
            if (NpcChat.talkToNPC("Captain Errdo")) {
                NPCInteraction.waitForConversationWindow();
               // NPCInteraction.handleConversation();
                NPCInteraction.handleConversation(CAPTAIN_ERRDO);
                NPCInteraction.handleConversation();

            }
        }
        if (BEFORE_GATE_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Going to docks";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToTile(gateTile);

            RSObject[] gate = Objects.findNearest(20, "Gate");
            if (gate.length > 1) {
                if (AccurateMouse.click(gate[0], "Open")) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation("Glough sent me.");
                    NPCInteraction.handleConversation("Ka.");
                    NPCInteraction.handleConversation("Lu.");
                    NPCInteraction.handleConversation("Min.");
                    NPCInteraction.handleConversation();
                    Timer.waitCondition(()-> AFTER_GATE_AREA.contains(Player.getPosition()), 10000);
                }
            }
        }
        if (AFTER_GATE_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Going to Foreman";
            General.println("[Debug]: " + cQuesterV2.status);
            Walking.walkPath(PATH_TO_FOREMAN);
            Timer.abc2WaitCondition(() -> dockArea.contains(Player.getPosition()), 7000, 10000);
        }
        if (DOCKS.contains(Player.getPosition())) {
            cQuesterV2.status = "Talking to Foreman";
            General.println("[Debug]: " + cQuesterV2.status);
            if (NpcChat.talkToNPC("Foreman")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                Timer.abc2WaitCondition(() -> HUT_AREA.contains(Player.getPosition()), 7000, 10000);
            }
        }
    }

    public void step10() {
        NPCInteraction.waitForConversationWindow();
        NPCInteraction.handleConversation("Sadly his wife is no longer with us!");
        NPCInteraction.handleConversation("He loves worm holes.");
        NPCInteraction.handleConversation("Anita.");
        NPCInteraction.handleConversation();
      //  NPCInteraction.handleConversation();
        Utils.modSleep();
    }

    public void getCoins() {
        BankManager.open(true);
        BankManager.withdraw(1000, true, 995);
        BankManager.close(true);
    }

    public void step11() {
        RSItem[] invCoins = Inventory.find(995);

        if (invCoins.length > 0) {
            if (invCoins[0].getStack() >= 1000 && !WHOLE_STRONGHOLD.contains(Player.getPosition())) {
                cQuesterV2.status = "Going to Femi";
                General.println("[Debug]: " + cQuesterV2.status);

                if (!strongholdEntrance.contains(Player.getPosition()))
                    PathingUtil.walkToTile(new RSTile(2460, 3382));

                if (NpcChat.talkToNPC("Femi")) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation("Okay then, I'll pay.");
                    NPCInteraction.handleConversation();

                    Utils.idle(4000, 8000);
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                    Utils.modSleep();
                }
            } else {
                getCoins();
            }
        } else { // we don't have coins
            getCoins();
            return;
        }
        cQuesterV2.status = "Going to King";
        General.println("[Debug]: " + cQuesterV2.status);

        PathingUtil.walkToArea(kingArea);
        if (kingArea.contains(Player.getPosition())) {
            if (NpcChat.talkToNPC("King Narnode Shareen")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                talkToCharlie();
            }
        }
    }

    public void step12() {
        if (Inventory.find(gloughsKey).length < 1) {
            if (!anitaHouse.contains(Player.getPosition()) && !anitaLadder.contains(Player.getPosition())) {
                cQuesterV2.status = "Going to Anita";
                General.println("[Debug]: " + cQuesterV2.status);
                PathingUtil.walkToArea(anitaLadder);
            }
            if (anitaLadder.contains(Player.getPosition())) {
                RSObject[] stairs = Objects.findNearest(20, "Staircase");
                if (stairs.length > 0)
                    if (AccurateMouse.click(stairs[0], "Climb-up"))
                        Timer.abc2WaitCondition(() -> anitaHouse.contains(Player.getPosition()), 8000, 12000);
            }

            if (anitaHouse.contains(Player.getPosition())) {
                cQuesterV2.status = "Talking to Anita";
                General.println("[Debug]: " + cQuesterV2.status);
                if (NpcChat.talkToNPC("Anita")) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation("I suppose so.");
                    NPCInteraction.handleConversation();
                }

                if (Inventory.find(gloughsKey).length > 0)
                    if (Utils.clickObj("Staircase", "Climb-down"))
                        Timer.abc2WaitCondition(() -> anitaLadder.contains(Player.getPosition()), 8000, 12000);
            }
        }
    }

    public void step13() {
        cQuesterV2.status = "Going to check Glough's chest";
        General.println("[Debug]: " + cQuesterV2.status);
        if (Inventory.find(gloughsKey).length > 0) {
            goToGlough();
            RSObject[] chest = Objects.find(20, 2436);
            if (chest.length > 0) {
                // need this because otherwise it might try to walk to the chest using Dax and fail.
                if (!chest[0].isClickable())
                    chest[0].adjustCameraTo();
            }

            if (Utils.useItemOnObject(gloughsKey, 2436)) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                if (Utils.clickObj("Ladder", "Climb-down"))
                    Timer.abc2WaitCondition(() -> gloughUnder.contains(Player.getPosition()), 7000, 10000);
            }
        }
    }

    public void step14() {
        goToKing();
        while (Inventory.getAll().length > 24) {
            General.println("[Debug]: Eating for Space");
            General.sleep(General.random(300, 1200));
            AccurateMouse.click(Inventory.find(lobster)[0], "Eat");
        }
        if (kingArea.contains(Player.getPosition())) {
            cQuesterV2.status = "Talkng to King";
            General.println("[Debug]: " + cQuesterV2.status);
            if (NpcChat.talkToNPC("King Narnode Shareen")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                NPCInteraction.handleConversation();
                NPCInteraction.handleConversation();
                Utils.modSleep();
            }
        }
    }


    public void step15() {
        Log.log("[Debug]: Step 15: Going to Glough's");
        if (Inventory.find(staffOfAir).length < 1 && Equipment.find(staffOfAir).length < 1 || Inventory.find(mindRune).length < 1) {
            Log.log("[Debug]: Step 15: Getting items");
            getItemsFight();
        }

        if (!glough2ndFloor.contains(Player.getPosition()) && !gloughArea.contains(Player.getPosition()))
            goToGlough();

        if (gloughArea.contains(Player.getPosition())) {

            RSObject[] tree = Objects.findNearest(20, "Tree");

            if (tree.length > 0) {
                Walking.blindWalkTo(tree[0].getPosition());
                Timer.abc2WaitCondition(() -> tree[0].isClickable(), 7000, 9000);

                if (SUMMER_PIE && Inventory.find(
                        ItemID.SUMMER_PIE).length > 0)
                    if (AccurateMouse.click(Inventory.find(
                            ItemID.SUMMER_PIE)[0], "Eat"))
                        General.sleep(General.random(500, 1200));

                if (Utils.clickObj("Tree", "Climb-up"))
                    Timer.abc2WaitCondition(() -> glough2ndFloor.contains(Player.getPosition()), 7000, 10000);
            }
        }

        if (glough2ndFloor.contains(Player.getPosition())) {
            cQuesterV2.status = "Placing Sticks";
            if (Utils.useItemOnObject(789, 2440))
                Timer.abc2WaitCondition(() -> Inventory.find(789).length < 1, 5000, 7000);

            if (Utils.useItemOnObject(790, 2441))
                Timer.abc2WaitCondition(() -> Inventory.find(790).length < 1, 5000, 7000);

            if (Utils.useItemOnObject(791, 2442))
                Timer.abc2WaitCondition(() -> Inventory.find(791).length < 1, 5000, 7000);

            if (Utils.useItemOnObject(792, 2443)) {
                Timer.abc2WaitCondition(() -> Inventory.find(792).length < 1, 5000, 7000);
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
            Utils.modSleep();
        }
    }

    public void step16() {
        if (Utils.equipItem(staffOfAir))
            General.sleep(General.random(400, 1200));

        if (!Autocast.isAutocastEnabled(Autocast.FIRE_STRIKE)) {
            Autocast.enableAutocast(Autocast.FIRE_STRIKE);
            Utils.modSleep();
        }
        if (glough2ndFloor.contains(Player.getPosition())) {
            if (Autocast.isAutocastEnabled(Autocast.FIRE_STRIKE)) {
                if (Utils.clickObj("Trapdoor", "Climb-down")) {
                    //Utils.Cutscene does not work here

                    Utils.idle(8000, 12000);
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                    NPCInteraction.handleConversation();
                }
            }
        }
    }

    RSArea SAFE_AREA2 = new RSArea(new RSTile(2492, 9865, 0), new RSTile(2491, 9865, 0));
    RSTile SAFE_SPOT2 = new RSTile(2492, 9865, 0);

    public void step17() { // Fight
        if (!glough2ndFloor.contains(Player.getPosition())) {

            RSNPC[] blackDemon = NPCs.findNearest("Black demon");
            if (blackDemon.length > 0) {
                if (!Autocast.isAutocastEnabled(Autocast.FIRE_STRIKE)) {
                    Autocast.enableAutocast(Autocast.FIRE_STRIKE);
                }
                if (!SAFE_AREA2.contains(Player.getPosition())) {
                    cQuesterV2.status = "Going to SafeTile.";
                    General.println("[Debug]: " + cQuesterV2.status);
                    Walking.clickTileMS(SAFE_SPOT2, "Walk here");
                    Timer.abc2WaitCondition(() -> SAFE_AREA2.contains(Player.getPosition()), 4000, 6000);
                }
                if (SAFE_AREA2.contains(Player.getPosition()) && !blackDemon[0].isInCombat()) {
                    cQuesterV2.status = "Attacking.";
                    General.println("[Debug]: " + cQuesterV2.status);
                    AccurateMouse.click(blackDemon[0], "Attack");
                    General.sleep(General.random(1000, 3000));
                }
                if (Combat.getHPRatio() < General.random(41, 66)) {
                    cQuesterV2.status = "Eating.";
                    AccurateMouse.click(Inventory.find(lobster)[0], "Eat");
                }
            }
        }
    }

    public void step18() {
        Utils.modSleep();
        cQuesterV2.status = "Going to King";
        Walking.blindWalkTo(new RSTile(2465, 9895, 0));
        RSNPC[] king = NPCs.findNearest("King Narnode Shareen");
        if (king.length < 1) {
            General.sleep(General.random(15000, 25000));
            king = NPCs.findNearest("King Narnode Shareen");
        }
        if (king.length > 0) {
            Timer.waitCondition(() -> NPCs.findNearest("King Narnode Shareen")[0].getPosition().distanceTo(Player.getPosition()) < 4, 7000);
            if (NpcChat.talkToNPC("King Narnode Shareen")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                NPCInteraction.handleConversation();

                Utils.idle(3500, 5000);
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                NPCInteraction.handleConversation();
                Utils.idle(3500, 5000);
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                NPCInteraction.handleConversation();
            }
        }
    }

    public void searchRoot(int ID) {
        if (Objects.findNearest(20, ID).length > 0) {
            if (!Objects.findNearest(20, ID)[0].isOnScreen()) {
                Walking.blindWalkTo(Objects.findNearest(20, ID)[0].getPosition());
                Timer.abc2WaitCondition(() -> Objects.findNearest(20, ID)[0].isOnScreen(), 6000, 8000);
            }
            AccurateMouse.click(Objects.findNearest(20, ID)[0], "Search");
            General.sleep(General.random(2500, 5000));

            if (NPCInteraction.isConversationWindowUp()) {
                NPCInteraction.handleConversation();
            }
        }
    }

    public void step19() {
        RSTile anchor = new RSTile(2442, 9884, 0);
        while (Inventory.find(793).length < 1) {
            General.sleep(50);
            cQuesterV2.status = "Searching roots";
            RSObject[] roots = Objects.findNearest(60, "Root");
            roots = Objects.sortByDistance(anchor, roots);
            for (int i = 0; i < roots.length; i++) {
                if (!roots[i].isOnScreen()) {
                    Walking.blindWalkTo(roots[i]);
                    General.sleep(General.random(4000, 8000));
                }

                General.println("i = " + i);
                if (AccurateMouse.click(roots[i], "Search")) {
                    Timer.waitCondition(() -> Player.getAnimation() != -1, 5000, 10000);
                    Utils.shortSleep(); // need this for window to pop up if it's  going to
                    if (NPCInteraction.isConversationWindowUp())
                        NPCInteraction.handleConversation();
                    General.sleep(General.randomSD(250, 1500, 600, 300));
                }
                if (Inventory.find(793).length > 0) {
                    break;
                }
            }
        }
    }

    public void step20() {
        cQuesterV2.status = "Finishing";
        if (Inventory.find(793).length > 0) {
            Walking.blindWalkTo(new RSTile(2464, 9896, 0));
            if (NPCs.findNearest("King Narnode Shareen").length < 1) {
                General.sleep(General.random(10000, 15000));
            }
            Timer.abc2WaitCondition(() -> NPCs.findNearest("King Narnode Shareen")[0].getPosition().distanceTo(Player.getPosition()) < 4, 7000, 10000);
            if (NpcChat.talkToNPC("King Narnode Shareen")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                NPCInteraction.handleConversation();
                NPCInteraction.handleConversation();
                NPCInteraction.handleConversation();
            }
        }
    }

    boolean SUMMER_PIE = Skills.getActualLevel(Skills.SKILLS.AGILITY) < 25 && Skills.getActualLevel(Skills.SKILLS.AGILITY) >= 20;

    public void checkLevel() {

    }

    int GAME_SETTING = 150;

    @Override
    public void execute() {
        checkLevel();


        if (Game.getSetting(GAME_SETTING) == 0) {
            buyItems();
            getItems();
            startQuest();
        }
        if (Game.getSetting(GAME_SETTING) == 10) {
            step2();
        }
        if (Game.getSetting(GAME_SETTING) == 20) {
            step3();
        }
        if (Game.getSetting(GAME_SETTING) == 30) {
            step4();
        }
        if (Game.getSetting(GAME_SETTING) == 40) {
            step5();
        }
        if (Game.getSetting(150) == 50) {
            talkToCharlie();
        }
        if (Game.getSetting(150) == 60) {
            step7();
        }
        if (Game.getSetting(150) == 70) {
            step8();
        }
        if (Game.getSetting(150) == 80) {
            step9();
            step10();
        }
        if (Game.getSetting(150) == 90) {
            step11();
        }
        if (Game.getSetting(150) == 100) {
            step12();
            step13();
        }
        if (Game.getSetting(150) == 110) {
            step14();
        }
        if (Game.getSetting(150) == 120) {
            step15();
        }
        if (Game.getSetting(150) == 130) {
            step16();
            step17();
        }
        if (Game.getSetting(150) == 140) {
            step18();
        }
        if (Game.getSetting(150) == 150) {
            step19();
            step20();
        }
        if (Game.getSetting(150) == 160) {
            Utils.closeQuestCompletionWindow();
            NPCInteraction.handleConversation();
            Utils.continuingChat();
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
        return "The Grand Tree";
    }

    @Override
    public boolean checkRequirements() {
        if (Skills.getActualLevel(Skills.SKILLS.AGILITY) < 25 && Skills.getActualLevel(Skills.SKILLS.AGILITY) >= 20) {
            SUMMER_PIE = true;
            General.println("[Debug]: Using summer pie");
        } else if (Skills.getActualLevel(Skills.SKILLS.AGILITY) < 20) {
            General.println("[Debug]: We do not meet the requirement for Grand tree (20+ Agility)");
            return false;
        }
        return true;
    }
}
