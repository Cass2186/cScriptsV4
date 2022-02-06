package scripts.QuestPackages.MonksFriend;

import dax.walker.utils.AccurateMouse;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.WitchsPotion.WitchsPotion;
import scripts.QuestSteps.BuyItemsStep;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.InventoryRequirement;
import scripts.Requirements.ItemReq;
import scripts.Requirements.ItemRequirement;
import scripts.Requirements.Requirement;
import scripts.Tasks.Priority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MonksFriend implements QuestTask {


    private static MonksFriend quest;

    public static MonksFriend get() {
        return quest == null ? quest = new MonksFriend() : quest;
    }

    int BLANKET = 90;
    int GAME_SETTING = 30;

    RSArea START_AREA = new RSArea(new RSTile(2610, 3207, 0), new RSTile(2602, 3211, 0));
    RSArea STONE_CIRCLE = new RSArea(new RSTile(2563, 3221, 0), new RSTile(2562, 3222, 0));
    RSArea DUNGEON_AREA = new RSArea(new RSTile(2559, 9622, 0), new RSTile(2573, 9599, 0));
    RSArea BLANKET_AREA = new RSArea(new RSTile(2572, 9602, 0), new RSTile(2568, 9607, 0));
    RSArea CEDRIC_AREA = new RSArea(new RSTile(2616, 3257, 0), new RSTile(2611, 3262, 0));

    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.LOGS, 1, 300),
                    new GEItem(ItemID.LOBSTER, 15, 30),
                    new GEItem(ItemID.JUG_OF_WATER, 1, 50),
                    new GEItem(ItemID.RING_OF_DUELING[0], 1, 50),
                    new GEItem(ItemID.STAMINA_POTION[0], 1, 15),
                    new GEItem(ItemID.RING_OF_WEALTH[0], 1, 25)
            )
    );

    InventoryRequirement initialItemReqs = new InventoryRequirement(new ArrayList<>(
            Arrays.asList(
                    new ItemReq(ItemID.LOGS, 1, 1),
                    new ItemReq(ItemID.LOBSTER, 12, 1),
                    new ItemReq(ItemID.JUG_OF_WATER, 1, 1),
                    new ItemReq(ItemID.RING_OF_DUELING[0], 1, 0, true, true),
                    new ItemReq(ItemID.STAMINA_POTION[0], 1, 0),
                    new ItemReq(ItemID.RING_OF_WEALTH[0], 1, 0, true)
            )
    ));

    BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);

    public void buyItems() {
        if (!initialItemReqs.check()) {
            cQuesterV2.status = "Buying Items";
            General.println("[Debug]: " + cQuesterV2.status);
            buyStep.buyItems();
        }
    }

    public void getItems() {
        cQuesterV2.status = "Getting Items";
        General.println("[Debug]: " + cQuesterV2.status);
        BankManager.open(true);
        BankManager.depositAll(true);
        BankManager.checkEquippedGlory();
        initialItemReqs.withdrawItems();
    }


    public void startQuest() {
        if (!BankManager.checkInventoryItems(ItemID.JUG_OF_WATER, ItemID.LOGS)) {
            buyItems();
            getItems();
        } else {
            cQuesterV2.status = "Starting quest";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(START_AREA);
            if (NpcChat.talkToNPC("Brother Omad")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Why can't you sleep, what's wrong?");
                NPCInteraction.handleConversation("Can I help at all?");
                NPCInteraction.handleConversation("Yes.");
                NPCInteraction.handleConversation();
            }
        }
    }

    public void navigateDungeon() {
        cQuesterV2.status = "Going to Dungeon";
        General.println("[Debug]: " + cQuesterV2.status);
        if (!DUNGEON_AREA.contains(Player.getPosition()) && Inventory.find(BLANKET).length < 1) {
            PathingUtil.walkToArea(STONE_CIRCLE);
        }
        if (STONE_CIRCLE.contains(Player.getPosition())) {
            if (Utils.clickObject("Ladder", "Climb-down", false))
                Timer.waitCondition(() -> DUNGEON_AREA.contains(Player.getPosition()), 6000, 9000);

        }
        if (DUNGEON_AREA.contains(Player.getPosition()) && !BLANKET_AREA.contains(Player.getPosition()) && Inventory.find(90).length < 1) {
            if (PathingUtil.localNavigation(BLANKET_AREA.getRandomTile()))
                PathingUtil.movementIdle();
        }
        if (BLANKET_AREA.contains(Player.getPosition())) {
            Utils.clickGroundItem(BLANKET); //clicks and waits to collect item

            if (Inventory.find(BLANKET).length > 0) {
                if (PathingUtil.localNavigation(new RSTile(2561, 9621)))
                    PathingUtil.movementIdle();
            }
        }
        cQuesterV2.status = "Leaving Dungeon";
        General.println("[Debug]: " + cQuesterV2.status);
        if (DUNGEON_AREA.contains(Player.getPosition()) && !BLANKET_AREA.contains(Player.getPosition())
                && Inventory.find(BLANKET).length > 0) {

            RSObject[] ladder = Objects.findNearest(20, "Ladder");
            if (ladder.length > 0)
                if (AccurateMouse.click(ladder[0], "Climb-up"))
                    Timer.abc2WaitCondition(() -> STONE_CIRCLE.contains(Player.getPosition()), 7000, 12000);
        }
    }

    public void returnBlanket() {
        if (Inventory.find(BLANKET).length > 0) {
            cQuesterV2.status = "Going to return blanket";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(START_AREA);
            if (NpcChat.talkToNPC("Brother Omad")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
        }
    }

    public void talkToBrotherOmadAgain() {
        cQuesterV2.status = "Talking to Omad again";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(START_AREA);
        if (NpcChat.talkToNPC("Brother Omad")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Who's Brother Cedric?");
            NPCInteraction.handleConversation("Where should I look?");
            NPCInteraction.handleConversation();
        }
    }

    public void goToCedric() {
        if (!BankManager.checkInventoryItems(ItemID.JUG_OF_WATER, ItemID.LOGS)) {
            buyItems();
            getItems();
        } else {
            cQuesterV2.status = "Going to Cedric";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(CEDRIC_AREA);
            if (NpcChat.talkToNPC("Brother Cedric")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
        }
    }

    public void step6() {
        if (!BankManager.checkInventoryItems(ItemID.JUG_OF_WATER, ItemID.LOGS)) {
            buyItems();
            getItems();
        } else {
            cQuesterV2.status = "Going to Cedric";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(CEDRIC_AREA);
            if (NpcChat.talkToNPC("Brother Cedric")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Yes, I'd be happy to!");
                NPCInteraction.handleConversation();
            }
        }
    }

    public void step7() {
        cQuesterV2.status = "Talking to Cedric";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(CEDRIC_AREA);
        if (NpcChat.talkToNPC("Brother Cedric")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }
    }



    @Override
    public void execute() {
        if (Game.getSetting(30) == 0) {
            buyItems();
            getItems();
            startQuest();
        }
        if (Game.getSetting(30) == 10) {
            navigateDungeon();
            returnBlanket();
        }
        if (Game.getSetting(30) == 20) {
            talkToBrotherOmadAgain();
        }
        if (Game.getSetting(30) == 30) {
            goToCedric();
        }
        if (Game.getSetting(30) == 40 || Game.getSetting(30) == 50) {
            step6();
        }
        if (Game.getSetting(30) == 60) {
            step7();
        }
        if (Game.getSetting(30) == 70) {
            talkToBrotherOmadAgain();
            Timer.waitCondition(() -> Game.getSetting(30) == 80, 30000, 40000);
        }
        if (Game.getSetting(30) == 80) {
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
        return "Monk's Friend";
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
    public boolean checkRequirements() {
        return true;
    }
}
