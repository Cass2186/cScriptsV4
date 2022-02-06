package scripts.QuestPackages.RomeoAndJuliet;

import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.Game;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.GoblinDiplomacy.GoblinDiplomacy;
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

public class RomeoAndJuliet implements QuestTask {

    private static RomeoAndJuliet quest;

    public static RomeoAndJuliet get() {
        return quest == null ? quest = new RomeoAndJuliet() : quest;
    }


    int CADAVA_BERRIES = 753;

    RSArea START_AREA = new RSArea(new RSTile(3206, 3427, 0), new RSTile(3220, 3421, 0));
    RSArea JULIET_AREA = new RSArea(new RSTile(3161, 3425, 1), new RSTile(3155, 3426, 1));
    RSArea CHURCH = new RSArea(new RSTile(3259, 3479, 0), new RSTile(3252, 3484, 0));
    RSArea SHOP = new RSArea(new RSTile(3198, 3402, 0), new RSTile(3192, 3406, 0));

    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.CADAVA_BERRIES, 1, 500),
                    new GEItem(ItemID.VARROCK_TELEPORT, 7, 40),
                    new GEItem(ItemID.STAMINA_POTION[0], 2, 15),
                    new GEItem(ItemID.RING_OF_WEALTH[0], 1, 25)
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
        BankManager.depositEquipment();
        BankManager.depositAll(true);
        BankManager.withdraw(1, true, CADAVA_BERRIES);
        BankManager.withdraw(2, true, ItemID.STAMINA_POTION[0]);
        BankManager.withdraw(7, true, ItemID.VARROCK_TELEPORT);
        BankManager.close(true);
    }

    public void startQuest() {
        cQuesterV2.status = "Starting Quest";
        if (!BankManager.checkInventoryItems(CADAVA_BERRIES)) {
            buyItems();
            getItems();
        }
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(START_AREA);
        if (NpcChat.talkToNPC("Romeo")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Perhaps I could help to find her for you?");
            NPCInteraction.handleConversation("Yes.");
            NPCInteraction.handleConversation();
        }
    }

    public void step2() {
        cQuesterV2.status = "Going to Juliet";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(JULIET_AREA);
        if (NpcChat.talkToNPC("Juliet")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }
    }

    public void step3() {
        cQuesterV2.status = "Going to Romeo";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(START_AREA);
        if (NpcChat.talkToNPC("Romeo")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            NPCInteraction.handleConversation();
            NPCInteraction.handleConversation();
        }
    }

    public void step4() {
        cQuesterV2.status = "Going to Church";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(CHURCH);
        if (NpcChat.talkToNPC("Father Lawrence")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            NPCInteraction.handleConversation();
            Utils.cutScene();
            Utils.shortSleep();
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }
    }

    public void step5() {
        if (!BankManager.checkInventoryItems(CADAVA_BERRIES)) {
            buyItems();
            getItems();
        } else {
            cQuesterV2.status = "Going to Get Potion";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(SHOP);
            if (NpcChat.talkToNPC("Apothecary")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Talk about something else.");
                NPCInteraction.handleConversation("Talk about Romeo & Juliet.");
                Utils.cutScene();
                NPCInteraction.handleConversation();
            }
        }
    }

    public void step6() {
        cQuesterV2.status = "Going to Juliet";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(JULIET_AREA);
        if (NpcChat.talkToNPC("Juliet")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            General.sleep(General.random(1000, 3000));
            Utils.cutScene();
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }
    }

    public void step7() {
        cQuesterV2.status = "Going to Romeo";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(START_AREA);
        if (NpcChat.talkToNPC("Romeo")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            General.sleep(2000, 3000);
            Utils.cutScene();
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }
    }

    @Override
    public void execute() {

        General.println("[Debug]: Game setting 144: " + Game.getSetting(144));

        if (Game.getSetting(144) == 0) {
            General.sleep(100);
            buyItems();
            getItems();
            startQuest();
        }
        if (Game.getSetting(144) == 10) {
            step2();
        }
        if (Game.getSetting(144) == 20) {
            step3();
        }
        if (Game.getSetting(144) == 30) {
            step4();
        }
        if (Game.getSetting(144) == 40) {
            step5();
        }
        if (Game.getSetting(144) == 50) {
            step6();
        }
        if (Game.getSetting(144) == 60) {
            step7();
        }
        if (Game.getSetting(144) == 100) {
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
        return "Romeo And Juliet";
    }

    @Override
    public boolean checkRequirements() {
        return true;
    }

    @Override
    public List<Requirement> getGeneralRequirements() {
        return null;
    }

    @Override
    public List<ItemRequirement> getBuyList() {
        return null;
    }
}
