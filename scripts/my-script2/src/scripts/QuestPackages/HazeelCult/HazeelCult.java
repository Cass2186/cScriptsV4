package scripts.QuestPackages.HazeelCult;

import dax.api_lib.DaxWalker;
import dax.walker.utils.AccurateMouse;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.types.*;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.GertrudesCat.GertrudesCat;
import scripts.QuestSteps.BuyItemsStep;
import scripts.QuestSteps.QuestTask;
import scripts.Tasks.Priority;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

public class HazeelCult implements QuestTask {
    private static HazeelCult quest;

    public static HazeelCult get() {
        return quest == null ? quest = new HazeelCult() : quest;
    }


    GEItem lobster = new GEItem(ItemId.LOBSTER, 15, 25);
    GEItem staminaPots = new GEItem(ItemId.STAMINA_POTION[0], 2, 15);
    GEItem glory = new GEItem(ItemId.AMULET_OF_GLORY[2], 1, 10);
    GEItem ringOfDueling = new GEItem(ItemId.RING_OF_DUELING[0], 1, 15);
    GEItem mindRune = new GEItem(ItemId.MIND_RUNE, 300, 15);
    GEItem fireRune = new GEItem(ItemId.FIRE_RUNE, 900, 1);
    GEItem staffOfAir = new GEItem(ItemId.STAFF_OF_AIR, 1, 150);

    ArrayList toBuy = new ArrayList(Arrays.asList(
            lobster,
            staminaPots,
            glory,
            ringOfDueling,
            mindRune,
            fireRune,
            staffOfAir
    ));
    int LOBSTER = 379;
    int IRON_SCIMITAR = 1323;
    int MITHRIL_SCIMITAR = 1329;
    int ADAMANT_SCIMITAR = 1331;
    int ARMOUR = 2405;

    RSArea START_AREA = new RSArea(new RSTile(2567, 3267, 0), new RSTile(2564, 3271, 0));
    RSArea CLIVET_AREA = new RSArea(new RSTile(2571, 9680, 0), new RSTile(2565, 9685, 0));
    RSArea VALVE_1 = new RSArea(new RSTile(2562, 3251, 0), new RSTile(2565, 3248, 0));
    RSArea VALVE_2 = new RSArea(new RSTile(2571, 3264, 0), new RSTile(2575, 3262, 0));
    RSArea VALVE_3 = new RSArea(new RSTile(2583, 3244, 0), new RSTile(2587, 3241, 0));
    RSArea VALVE_4 = new RSArea(new RSTile(2597, 3259, 0), new RSTile(2594, 3262, 0));
    RSArea VALVE_5 = new RSArea(new RSTile(2609, 3240, 0), new RSTile(2613, 3236, 0));
    RSArea ENTRANCE_TO_SEWER = new RSArea(new RSTile(2589, 3233, 0), new RSTile(2585, 3236, 0));
    RSArea RAFT_LANDING_AREA = new RSArea(new RSTile(2604, 9693, 0), new RSTile(2608, 9690, 0));
    RSArea ALMONE_AREA = new RSArea(new RSTile(2604, 9675, 0), new RSTile(2611, 9666, 0));
    RSArea HOUSE_SECOND_FLOOR = new RSArea(new RSTile(2575, 3267, 1), new RSTile(2567, 3270, 1));
    RSArea LARGE_SEWER_AREA = new RSArea(new RSTile(2590, 3232, 0), new RSTile(2584, 3238, 0));

    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(toBuy);

    BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);

    public void buyItems() {
        cQuesterV2.status = "Buying Items";
        General.println("[Debug]: " + cQuesterV2.status);
        buyStep.buyItems();
    }

    public void getItems() {
        cQuesterV2.status = "Getting Items";
        General.println("[Debug]: " + cQuesterV2.status);
        BankManager.open(true);
        BankManager.checkEquippedGlory();
        BankManager.depositAll(true);
        BankManager.withdraw(12, true, ItemId.LOBSTER);
        BankManager.withdraw(2, true, ItemId.STAMINA_POTION[0]);
        BankManager.withdraw(1, true, ItemId.RING_OF_DUELING[0]);
        BankManager.withdraw(1, true, ItemId.STAFF_OF_AIR);
        Utils.equipItem(ItemId.STAFF_OF_AIR);
        BankManager.withdraw(300, true, ItemId.MIND_RUNE);
        BankManager.withdraw(800, true, ItemId.FIRE_RUNE);
        BankManager.close(true);
        Utils.equipItem(ItemId.RING_OF_DUELING[0]);
    }


    public void step1() {
        if (Inventory.find(LOBSTER).length > 10) {
            cQuesterV2.status = "Starting quest";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(START_AREA);
            RSNPC[] ceril = NPCs.findNearest("Ceril Carnillean");
            if (ceril.length > 0 && PathingUtil.localNavigation(ceril[0].getPosition())) {
                PathingUtil.movementIdle();
                if (NpcChat.talkToNPC("Ceril Carnillean")) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation("What's wrong?");
                    NPCInteraction.handleConversation("Yes, of course, I'd be happy to help.");
                    NPCInteraction.handleConversation();
                    NPCInteraction.handleConversation();
                }
            }
        }
    }

    public void step2() {
        cQuesterV2.status = "Going to Clivet";
        General.println("[Debug]: " + cQuesterV2.status);
        if (!CLIVET_AREA.contains(Player.getPosition()))
            PathingUtil.walkToArea(ENTRANCE_TO_SEWER);

        if (Utils.clickObject("Cave entrance", "Enter", false))
            Timer.abc2WaitCondition(() -> CLIVET_AREA.contains(Player.getPosition()), 8000, 14000);

        if (NpcChat.talkToNPC("Clivet")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("What do you mean?");
            NPCInteraction.handleConversation("You're crazy, I'd never help you.");
            NPCInteraction.handleConversation();
        }
    }

    public void turnValve(String direction) {
        RSObject[] valve = Objects.findNearest(30, "Sewer valve");
        if (valve.length > 0) {
            if (!valve[0].isClickable()) {
                valve[0].adjustCameraTo();
            }
            if (AccurateMouse.click(valve[0], direction)) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                Utils.idle(800, 5000);
            }
        }
    }

    public void step3() {
        cQuesterV2.status = "Going to valve 1";
        General.println("[Debug]: " + cQuesterV2.status);
        if (CLIVET_AREA.contains(Player.getPosition()))
            if (Utils.clickObject("Stairs", "Climb-up", false))
                Timer.abc2WaitCondition(() -> ENTRANCE_TO_SEWER.contains(Player.getPosition()), 8000, 12000);

        PathingUtil.walkToArea(VALVE_1);
        turnValve("Turn-right");
    }

    public void step4() {
        cQuesterV2.status = "Going to valve 2";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(VALVE_2);
        turnValve("Turn-right");
    }

    public void step5() {
        cQuesterV2.status = "Going to valve 3";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(VALVE_3);
        turnValve("Turn-left");
    }

    public void step6() {
        cQuesterV2.status = "Going to valve 4";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(VALVE_4);
        turnValve("Turn-right");
    }

    public void step7() {
        cQuesterV2.status = "Going to valve 5";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(VALVE_5);
        turnValve("Turn-right");
    }

    public void step8() {
        if (Inventory.find(ARMOUR).length < 1) {
            cQuesterV2.status = "Going to Clivet";
            General.println("[Debug]: " + cQuesterV2.status);
            if (!CLIVET_AREA.contains(Player.getPosition()) && !RAFT_LANDING_AREA.contains(Player.getPosition()))
                PathingUtil.walkToArea(ENTRANCE_TO_SEWER);

            if (Utils.clickObject("Cave entrance", "Enter", false))
                Timer.abc2WaitCondition(() -> CLIVET_AREA.contains(Player.getPosition()), 8000, 12000);


            if (CLIVET_AREA.contains(Player.getPosition())) {
                if (NpcChat.talkToNPC("Clivet")) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                }
                cQuesterV2.status = "Going to Alomone";
                General.println("[Debug]: " + cQuesterV2.status);
                if (Utils.clickObject("Raft", "Board", false)) {
                    Utils.idle(500, 2500);
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                }
            }
            if (RAFT_LANDING_AREA.contains(Player.getPosition()) || ALMONE_AREA.contains(Player.getPosition())) {
                if (PathingUtil.localNavigation(ALMONE_AREA.getRandomTile()))
                    PathingUtil.movementIdle();

                if (!Combat.isAutoRetaliateOn())
                    Combat.setAutoRetaliate(true);

                if (!Autocast.isAutocastEnabled(Autocast.FIRE_STRIKE))
                    Autocast.enableAutocast(Autocast.FIRE_STRIKE);

                if (NpcChat.talkToNPC("Alomone")) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                    Timer.waitCondition(() -> Combat.isUnderAttack(), 10000, 15000);
                }

                while (Timer.waitCondition(() -> !Combat.isUnderAttack() || Combat.getHPRatio() < 50, 40000, 65000)) { // may be
                    General.sleep(100);

                    if (Combat.getHPRatio() < 50) {
                        if (Inventory.find(LOBSTER).length > 0) {
                            AccurateMouse.click(Inventory.find(LOBSTER)[0], "Eat");
                            Utils.microSleep();
                        }
                    }
                    Timer.abc2WaitCondition(() -> GroundItems.find(ARMOUR).length > 0 || Combat.getHPRatio() < 50, 25000, 45000);
                    if (GroundItems.find(ARMOUR).length > 0)
                        break;
                }
                RSGroundItem[] armour = GroundItems.find(ARMOUR);
                if (armour.length > 0) {
                    General.println("[Debug]: Looting armour");
                    if (AccurateMouse.click(armour[0], "Take"))
                        Timer.slowWaitCondition(() -> Inventory.find(ARMOUR).length > 0, 8000, 15000);
                }
            }
        }
    }

    public void step9() {
        if (Inventory.find(ARMOUR).length > 0) {
            cQuesterV2.status = "Going to Butler";
            General.println("[Debug]: " + cQuesterV2.status);

            if (ALMONE_AREA.contains(Player.getPosition()))
                if (PathingUtil.localNavigation(RAFT_LANDING_AREA.getRandomTile()))
                    PathingUtil.movementIdle();

            if (RAFT_LANDING_AREA.contains(Player.getPosition())) {
                if (Objects.findNearest(20, "Raft").length > 0) {
                    AccurateMouse.click(Objects.findNearest(20, "Raft")[0], "Board");

                    Utils.idle(500, 2500);
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                }
            }
            if (CLIVET_AREA.contains(Player.getPosition())) {
                if (Objects.findNearest(20, "Stairs").length > 0) {
                    AccurateMouse.click(Objects.findNearest(20, "Stairs")[0], "Climb-up");
                    Timer.waitCondition(() -> ENTRANCE_TO_SEWER.contains(Player.getPosition()), 8000, 12000);
                    Utils.idle(300, 2500);
                }
            }
            if (!HOUSE_SECOND_FLOOR.contains(Player.getPosition()) && !CLIVET_AREA.contains(Player.getPosition()) && !RAFT_LANDING_AREA.contains(Player.getPosition()) && !ALMONE_AREA.contains(Player.getPosition())) {
                PathingUtil.walkToArea(HOUSE_SECOND_FLOOR);
            }
            if (HOUSE_SECOND_FLOOR.contains(Player.getPosition())) {
                DaxWalker.getInstance().walkTo(NPCs.findNearest("Butler Jones")[0].getPosition());
                NpcChat.talkToNPC("Butler Jones");
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                NPCInteraction.handleConversation();
                if (!NPCInteraction.isConversationWindowUp()) {
                    cQuesterV2.status = "Talking to Ceril";
                    General.println("[Debug]: " + cQuesterV2.status);
                    PathingUtil.walkToArea(HOUSE_SECOND_FLOOR);
                    DaxWalker.getInstance().walkTo(NPCs.findNearest("Ceril Carnillean")[0].getPosition());
                }
                if (NpcChat.talkToNPC("Ceril Carnillean")) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                    NPCInteraction.handleConversation();
                }
            }
        }
    }

    public void step10() {
        if (HOUSE_SECOND_FLOOR.contains(Player.getPosition())) {
            Utils.closeQuestCompletionWindow();
            cQuesterV2.status = "Getting poison";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(HOUSE_SECOND_FLOOR);
            RSObject[] cupboard = Objects.findNearest(20, 2850);
            if (cupboard.length > 0) {
                DaxWalker.getInstance().walkTo(cupboard[0].getPosition());
                AccurateMouse.click(cupboard[0], "Open");
                Timer.waitCondition(() -> Objects.findNearest(20, 2851).length > 0, 8000, 12000);
            }
            RSObject[] openCupboard = Objects.findNearest(20, 2851);
            if (openCupboard.length > 0) {
                if (AccurateMouse.click(openCupboard[0], "Search")) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                    //   NPCInteraction.handleConversation();
                    //   NPCInteraction.handleConversation();
                    //  NPCInteraction.handleConversation();
                    //  NPCInteraction.handleConversation();
                }
            }
        }
    }


    int GAME_SETTING = 223;

    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return   cQuesterV2.taskList.get(0).equals(HazeelCult.get());
    }

    @Override
    public void execute() {
        if (Game.getSetting(223) == 0) {
            buyItems();
            getItems();
            step1();
        }
        if (Game.getSetting(223) == 2) {
            step2();
        }
        if (Game.getSetting(223) == 4) {
            step3();
            step4();
            step5();
            step6();
            step7();
            step8();
        }
        if (Game.getSetting(223) == 6) {
            step8();
            step9();

        }
        if (Game.getSetting(223) == 7) {
            step10();
        }
        if (Game.getSetting(223) == 9) {
            Utils.closeQuestCompletionWindow();
            cQuesterV2.taskList.remove(this);
        }
    }

    @Override
    public String questName() {
        return "Hazeel Cult";
    }

    @Override
    public boolean checkRequirements() {
        return false;
    }

}
