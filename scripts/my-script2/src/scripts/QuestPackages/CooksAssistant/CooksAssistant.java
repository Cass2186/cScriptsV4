package scripts.QuestPackages.CooksAssistant;

import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.Game;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSTile;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.FishingContest.FishingContest;
import scripts.QuestSteps.BuyItemsStep;
import scripts.QuestSteps.QuestTask;
import scripts.Tasks.Priority;

import java.util.ArrayList;
import java.util.Arrays;

public class CooksAssistant implements QuestTask {

    private static CooksAssistant quest;

    public static CooksAssistant get() {
        return quest == null ? quest = new CooksAssistant() : quest;
    }
    public  int bucketOfMilk = 1927;
    public  int egg = 1944;
    public  int potOfFlour = 1933;

    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(bucketOfMilk, 1, 800),
                    new GEItem(egg, 1, 500),
                    new GEItem(potOfFlour, 1, 500),
                    new GEItem(ItemId.LUMBRIDGE_TELEPORT, 2, 50),
                    new GEItem(ItemId.STAMINA_POTION[0], 2, 15),
                    new GEItem(ItemId.RING_OF_WEALTH[0], 1, 25)
            )
    );


    BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);


    public  RSArea KITCHEN_AREA = new RSArea(new RSTile(3211, 3212, 0), new RSTile(3205, 3217, 0));

    public  void buyItems() {
        if (!BankManager.checkInventoryItems(potOfFlour, egg, bucketOfMilk,
                ItemId.LUMBRIDGE_TELEPORT)) {
            cQuesterV2.status= "Buying Items";
            General.println("[Debug]: Buying Items");
            buyStep.buyItems();
        }
    }

    public void getItems() {
        if (!BankManager.checkInventoryItems(potOfFlour, egg, bucketOfMilk,
                ItemId.LUMBRIDGE_TELEPORT)) {
            cQuesterV2.status = "Getting items";
            General.println("[Debug]: Withdrawing Quest items.");
            BankManager.open(true);
            BankManager.checkEquippedGlory();
            BankManager.checkEquippedGlory();
            BankManager.withdraw(1, true, potOfFlour);
            BankManager.withdraw(1, true, egg);
            BankManager.withdraw(1, true, bucketOfMilk);
            BankManager.withdraw(1, true, ItemId.RING_OF_WEALTH[0]);
            BankManager.withdraw(2, true, ItemId.LUMBRIDGE_TELEPORT);
            BankManager.close(true);
        }
    }

    public void startQuest() {
        if (!BankManager.checkInventoryItems(potOfFlour, egg, bucketOfMilk,
                ItemId.LUMBRIDGE_TELEPORT)) {
            General.println("[Debug]: Missing items... buying again.");
            buyItems();
            getItems();
        } else {
            cQuesterV2.status = "Starting Quest";

            PathingUtil.walkToArea(KITCHEN_AREA);

            if (NpcChat.talkToNPC("Cook")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("What's wrong?");
                NPCInteraction.handleConversation("I'm always happy to help a cook in distress.");
                NPCInteraction.handleConversation("Actually, I know where to find this stuff.", "Yes.");
                NPCInteraction.handleConversation();
                Utils.continuingChat();
                if (Interfaces.get(11, 4) != null) {
                    Interfaces.get(11, 4).click();
                    NPCInteraction.handleConversation();
                }
            }

        }
    }

    public void finishQuest() {
        if (NpcChat.talkToNPC("Cook")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }
    }


    int GAME_SETTING = 29;

    @Override
    public void execute() {
        if (Game.getSetting(29) == 0) {
            buyItems();
            getItems();
            startQuest();
        }
        if (Game.getSetting(29) == 1) {
            finishQuest();
        }
        if (Game.getSetting(29) == 2) {
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
        return "Cooks assistant";
    }

    @Override
    public boolean checkRequirements() {
        return true;
    }
}
