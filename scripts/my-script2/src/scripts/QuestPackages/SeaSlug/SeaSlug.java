package scripts.QuestPackages.SeaSlug;

import dax.walker.utils.AccurateMouse;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSGroundItem;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.Waiting;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.WaterfallQuest.WaterfallQuest;
import scripts.QuestSteps.BuyItemsStep;
import scripts.QuestSteps.QuestTask;
import scripts.Tasks.Priority;

import java.util.ArrayList;
import java.util.Arrays;

public class SeaSlug implements QuestTask {


    private static SeaSlug quest;

    public static SeaSlug get() {
        return quest == null ? quest = new SeaSlug() : quest;
    }

    int SWAMP_PASTE = 1941;
    int COINS = 995; // need 3000
    int BROKEN_GLASS = 1469;
    int TWIGS = 1467;
    int DRY_STICKS = 1468;
    int UNLIT_TORCH = 596;
    int LIT_TORCH = 594;
    int CAROLINE_ID = 5067;
    int HOLGART_1 = 5071; // use this instead of name
    int HOLGART_2 = 6241;
    int KENNITH = 5065;
    int KENT = 5074;
    int BAILEY = 5066;

    String[] startDialogue = {"Yes.", "I suppose so, how do I get there?"};

    RSArea START_AREA = new RSArea(new RSTile(2714, 3299, 0), new RSTile(2724, 3306, 0));
    RSArea WHOLE_FISHING_PLATFORM = new RSArea(new RSTile(2795, 3270, 0), new RSTile(2760, 3293, 0));
    RSArea WHOLE_FISHING_UPPER_LEVEL = new RSArea(new RSTile(2795, 3270, 1), new RSTile(2760, 3293, 1));
    RSArea SHACK = new RSArea(new RSTile(2767, 3273, 0), new RSTile(2762, 3278, 0));
    RSArea ISLAND = new RSArea(new RSTile(2788, 3314, 0), new RSTile(2801, 3325, 0));

    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(SWAMP_PASTE, 1, 3000),
                    new GEItem(ItemId.AMULET_OF_GLORY[2], 1, 30),
                    new GEItem(ItemId.STAMINA_POTION[0], 1, 15),
                    new GEItem(ItemId.RING_OF_WEALTH[0], 1, 25)
            )
    );
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
        BankManager.withdraw(1, true, ItemId.AMULET_OF_GLORY[0]);
        BankManager.withdraw(1, true, BankManager.STAMINA_POTION[0]);
        BankManager.withdraw(3000, true, COINS);
        BankManager.withdraw(1, true, SWAMP_PASTE);
        BankManager.close(true);
    }

    public void start() {
        if (!BankManager.checkInventoryItems(SWAMP_PASTE)) {
            buyItems();
            getItems();
        } else {
            cQuesterV2.status = "Going to Start";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(START_AREA);

            NpcChat.talkToNPC(CAROLINE_ID); // don't put in an if statement
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation(startDialogue);
            NPCInteraction.handleConversation();

        }
    }

    public void step2() {
        if (!BankManager.checkInventoryItems(SWAMP_PASTE)) {
            buyItems();
            getItems();
        } else {
            cQuesterV2.status = "Going to Holgart";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(START_AREA);

            cQuesterV2.status = "Talking Holgart.";
            General.println("[Debug]: " + cQuesterV2.status);
            if (NpcChat.talkToNPC(HOLGART_1)) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                NPCInteraction.handleConversation();
                General.sleep(General.random(4000, 8000)); // cut scene
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
        }
    }

    public void step3() {
        if (!START_AREA.contains(Player.getPosition()) && !WHOLE_FISHING_PLATFORM.contains(Player.getPosition())) {
            cQuesterV2.status = "Going to Start";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(START_AREA);
        }
        if (START_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Talking Holgart.";
            General.println("[Debug]: " + cQuesterV2.status);

            RSNPC[] holgart = NPCs.findNearest(HOLGART_1);
            if (holgart.length > 0) { // use this b/c he doesn't work otherwise
                if (DynamicClicking.clickRSNPC(holgart[0], "Talk-to")) { // use dynamic clicking, accurate mouse fails
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation("Will you take me there?");
                    NPCInteraction.handleConversation();
                }
            }
        }
    }

    public void step4() {
        if (WHOLE_FISHING_PLATFORM.contains(Player.getPosition()) && !SHACK.contains(Player.getPosition())) {
            cQuesterV2.status = "Sea Slug: Going to Shack";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(SHACK);
        }
        if (WHOLE_FISHING_PLATFORM.contains(Player.getPosition()) && SHACK.contains(Player.getPosition())) {
            RSGroundItem[] brokenGlass = GroundItems.find(BROKEN_GLASS);
            if (brokenGlass.length > 0 && Inventory.find(BROKEN_GLASS).length < 1) {
                cQuesterV2.status = "Sea Slug: Getting Glass.";
                General.println("[Debug]: " + cQuesterV2.status);
                AccurateMouse.click(brokenGlass[0], "Take");
                Timer.abc2WaitCondition(() -> Inventory.find(BROKEN_GLASS).length > 0, 8000, 12000);
            }

            cQuesterV2.status = "Sea Slug: Talking to Bailey.";
            General.println("[Debug]: " + cQuesterV2.status);
            if (NpcChat.talkToNPC(BAILEY)) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                NPCInteraction.handleConversation();
            }
        }
    }

    public void step5() {
        cQuesterV2.status = "Going to Kennith.";
        General.println("[Debug]: " + cQuesterV2.status);
        if (WHOLE_FISHING_PLATFORM.contains(Player.getPosition()) && !WHOLE_FISHING_UPPER_LEVEL.contains(Player.getPosition())) {
            PathingUtil.walkToTile(new RSTile(2784, 3287, 0), 2, false);

            RSGroundItem[] twigs = (GroundItems.find(TWIGS));
            if (twigs.length > 0 && Inventory.find(TWIGS).length < 1)
                if (AccurateMouse.click(twigs[0], "Take"))
                    Timer.abc2WaitCondition(() -> Inventory.find(TWIGS).length > 0, 8000, 12000);

        }

        if (Utils.clickObject(18324, "Climb-up", false))
            Timer.abc2WaitCondition(() -> WHOLE_FISHING_UPPER_LEVEL.contains(Player.getPosition()), 12000, 15000);

        if (WHOLE_FISHING_UPPER_LEVEL.contains(Player.getPosition())) {
            General.println("[Debug]: Going to kennith");
            PathingUtil.localNavigation(new RSTile(2767, 3285, 1));
            Waiting.waitUniform(6000, 9000);

            if (Utils.clickObject(18168, "Open", false))
                Timer.waitCondition(() -> Objects.findNearest(15, 11617).length > 0, 1000, 15000);

            Walking.blindWalkTo(new RSTile(2766, 3286, 1));
            if (NpcChat.talkToNPC(KENNITH)) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
        }
    }

    public void step6() {
        cQuesterV2.status = "Going back to Holgart.";
        General.println("[Debug]: " + cQuesterV2.status);
        if (WHOLE_FISHING_UPPER_LEVEL.contains(Player.getPosition())) {
            PathingUtil.localNavigation(new RSTile(2784, 3287, 1));
            PathingUtil.movementIdle();
        }

        if (Utils.clickObject(18325, "Climb-down", false))
            Timer.abc2WaitCondition(() -> Objects.findNearest(15, 18325).length < 1, 12000, 16000);

        if (WHOLE_FISHING_PLATFORM.contains(Player.getPosition())) {
            PathingUtil.walkToTile(new RSTile(2782, 3275, 0), 2, false);
            if (NPCs.find(HOLGART_2).length > 0) {
                Timer.waitCondition(() -> NPCs.find(HOLGART_2)[0].getPosition().distanceTo(Player.getPosition()) < 4, 10000, 12000);
                if (NpcChat.talkToNPC(HOLGART_2)) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                }
                Waiting.waitUniform(9000, 15000);
            }
        }
    }

    public void getDampSticks() {
        if (Inventory.find(TWIGS).length < 1) {
            cQuesterV2.status = "Getting Sticks";
            PathingUtil.walkToTile(new RSTile(2784, 3287, 0), 2, false);

            RSGroundItem[] twigs = (GroundItems.find(TWIGS));
            if (twigs.length > 0 && Inventory.find(TWIGS).length < 1)
                if (AccurateMouse.click(twigs[0], "Take"))
                    Timer.abc2WaitCondition(() -> Inventory.find(TWIGS).length > 0, 8000, 12000);
        }
    }

    public void step7() {
        if (!ISLAND.contains(Player.getPosition())) {
            cQuesterV2.status = "Going to Island";
            PathingUtil.walkToArea(START_AREA);

            RSNPC[] holgart = NPCs.findNearest(5071);
            if (holgart.length > 0) {
                if (!holgart[0].isClickable())
                    holgart[0].adjustCameraTo();

                if (DynamicClicking.clickRSNPC(holgart[0], "Travel")) { // have to use dynamic mouse
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                    Timer.waitCondition(() -> ISLAND.contains(Player.getPosition()), 10000, 15000);
                }
            }
        }
        if (ISLAND.contains(Player.getPosition())) {
            cQuesterV2.status = "Talking to Kent.";
            General.println("[Debug]: " + cQuesterV2.status);
            NpcChat.talkToNPC(KENT);
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();

            Waiting.waitUniform(3000, 5000);
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }
    }

    public void step8() {
        if (ISLAND.contains(Player.getPosition())) {
            if (NpcChat.talkToNPC(5069)) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                Timer.waitCondition(() -> WHOLE_FISHING_PLATFORM.contains(Player.getPosition()), 6000, 8000);
            }
        }


        if (WHOLE_FISHING_PLATFORM.contains(Player.getPosition()) && !SHACK.contains(Player.getPosition()) &&
                PathingUtil.walkToTile(SHACK.getRandomTile()))
            PathingUtil.movementIdle();

        if (WHOLE_FISHING_PLATFORM.contains(Player.getPosition()) && SHACK.contains(Player.getPosition()) && Inventory.find(UNLIT_TORCH).length < 1 && Inventory.find(LIT_TORCH).length < 1) {
            if (NpcChat.talkToNPC(BAILEY)) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
        }
        if (Inventory.find(UNLIT_TORCH).length > 0) {
            RSGroundItem[] brokenGlass = GroundItems.find(BROKEN_GLASS);
            if (brokenGlass.length > 0 && Inventory.find(BROKEN_GLASS).length < 1) {
                AccurateMouse.click(brokenGlass[0], "Take");
                Timer.abc2WaitCondition(() -> Inventory.find(BROKEN_GLASS).length > 0, 8000, 12000);
            }

            getDampSticks();

            if (Utils.useItemOnItem(BROKEN_GLASS, TWIGS))
                Timer.abc2WaitCondition(() -> Inventory.find(DRY_STICKS).length > 0, 3500, 6000);

            if (Inventory.find(DRY_STICKS).length > 0)
                if (AccurateMouse.click(Inventory.find(DRY_STICKS)[0], "Rub-together"))
                    Timer.abc2WaitCondition(() -> Inventory.find(LIT_TORCH).length > 0, 8000, 12000);
        }
    }

    public void step9() {
        cQuesterV2.status = "Kicking wall";
        General.println("[Debug]: " + cQuesterV2.status);
        step5(); //goes to kennith

        cQuesterV2.status = "Sea Slug: Kicking wall";
        General.println("[Debug]: " + cQuesterV2.status);
        if (PathingUtil.localNavigation(new RSTile(2768, 3288, 1))) {
            PathingUtil.movementIdle();
        }

        if (Utils.clickObject(18251, "Kick", false))
            Waiting.waitUniform(4000, 8000);
    }

    public void step10() {
        if (PathingUtil.localNavigation(new RSTile(2768, 3288, 1)))
            PathingUtil.movementIdle();
        Waiting.waitUniform(4000, 7000);

        if (Utils.clickObject(18251, "Kick", false))
            Waiting.waitUniform(4000, 8000);
    }

    public void step11() {
        cQuesterV2.status = "Using torch on Fisherman";
        if (Utils.useItemOnNPC(LIT_TORCH, "Fisherman")) {
            NPCInteraction.waitForConversationWindow();
        }

        cQuesterV2.status = "Sea Slug: Going back to Kennith";
        General.println("[Debug]: " + cQuesterV2.status);
        if (PathingUtil.localNavigation(new RSTile(2767, 3285, 1)))
            PathingUtil.movementIdle();

        if (Utils.clickObject(18168, "Open", false))
            Timer.waitCondition(() -> Objects.findNearest(15, 11617).length > 0, 12000);

        Walking.blindWalkTo(new RSTile(2766, 3286, 1));
        NpcChat.talkToNPC(KENNITH); // don't wrap in an if statement
        NPCInteraction.waitForConversationWindow();
        NPCInteraction.handleConversation();

    }

    public void step12() {
        cQuesterV2.status = "Operating Crane";
        General.println("[Debug]: " + cQuesterV2.status);
        if (Objects.findNearest(15, 18168).length > 0) {
            if (AccurateMouse.click(Objects.findNearest(15, 18168)[0], "Open"))
                Timer.abc2WaitCondition(() -> Objects.findNearest(15, 11617).length > 0, 10000, 15000);
        }
        PathingUtil.localNavigation(new RSTile(2772, 3291, 1));
        PathingUtil.movementIdle();

        if (Utils.clickObject("Crane", "Rotate", false))
            Waiting.waitUniform(15000, 20000);
    }

    public void step13() {
        step6();
        cQuesterV2.status = "Finishing Quest";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(START_AREA);
        NpcChat.talkToNPC(CAROLINE_ID); // don't put in an if statement
        NPCInteraction.waitForConversationWindow();
        NPCInteraction.handleConversation();

    }

    private Timer safteyTimer = new Timer(General.random(480000, 600000)); // 8- 10min
    int GAME_SETTING = 159;

    @Override
    public void execute() {
        General.println(Game.getSetting(159));

        if (Game.getSetting(159) == 0) {
            buyItems();
            getItems();
            start();

        }
        if (Game.getSetting(159) == 1 || Game.getSetting(159) == 2) {
            step2(); // talks to hogart
        }
        if (Game.getSetting(159) == 3) {
            step3(); // talks to hogart to go to platform
            step4();
            step5();

        }
        if (Game.getSetting(159) == 4) {
            step6();
        }
        if (Game.getSetting(159) == 5) {
            step7();
        }
        if (Game.getSetting(159) == 6) {
            step8();
        }
        if (Game.getSetting(159) == 7) {
            step9();

        }
        if (Game.getSetting(159) == 8) {
            step10();
        }
        if (Game.getSetting(159) == 9) {
            step11();
        }
        if (Game.getSetting(159) == 10) {
            step12();
        }
        if (Game.getSetting(159) == 11) {
            step13();
        }
        if (Game.getSetting(159) == 12) {
            Utils.closeQuestCompletionWindow();
            cQuesterV2.taskList.remove(SeaSlug.get());

        }

    }


    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return Game.getSetting(159) <= 12 &&
                java.util.Objects.equals(cQuesterV2.taskList.get(0), SeaSlug.get());
    }


    @Override
    public String questName() {
        return "Sea Slug";
    }

    @Override
    public boolean checkRequirements() {
        return true;
    }
}
