package scripts.QuestPackages.EnterTheAbyss;

import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.Game;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.RestlessGhost.RestlessGhost;
import scripts.QuestSteps.BuyItemsStep;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.InventoryRequirement;
import scripts.Requirements.ItemReq;
import scripts.Tasks.Priority;

import java.util.ArrayList;
import java.util.Arrays;

public class EnterTheAbyss implements QuestTask {

    private static EnterTheAbyss quest;

    public static EnterTheAbyss get() {
        return quest == null ? quest = new EnterTheAbyss() : quest;
    }


    int SCRYING_ORB = 5518;

    RSArea START_AREA = new RSArea(new RSTile(3109, 3552, 0), new RSTile(3101, 3559, 0));
    RSArea CHAOS_TEMPLE = new RSArea(new RSTile(3262, 3381, 0), new RSTile(3256, 3389, 0));
    RSArea AUBURY_AREA = new RSArea(new RSTile(3251, 3402, 0), new RSTile(3254, 3399, 0));
    RSArea WIZARDS_TOWER = new RSArea(new RSTile(3106, 9567, 0), new RSTile(3102, 9573, 0));
    RSArea ARDOUGNE_AREA = new RSArea(new RSTile(2685, 3320, 0), new RSTile(2682, 3326, 0));

    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.VARROCK_TELEPORT, 5, 50),
                    new GEItem(ItemID.NECKLACE_OF_PASSAGE[0], 1, 100),
                    new GEItem(ItemID.AMULET_OF_GLORY[2], 1, 50),
                    new GEItem(ItemID.STAMINA_POTION[0], 2, 15),
                    new GEItem(ItemID.RING_OF_WEALTH[0], 1, 25)
            )
    );

    InventoryRequirement initialItemReqs = new InventoryRequirement(new ArrayList<>(
            Arrays.asList(
                    new ItemReq(ItemID.LOBSTER, 15, 2),
                    new ItemReq(ItemID.ADAMANT_SCIMITAR, 1, 1, true, true),
                    new ItemReq(ItemID.VARROCK_TELEPORT, 5, 1),
                    new ItemReq(ItemID.EMPTY_BUCKET, 1, 1),
                    new ItemReq(ItemID.STAMINA_POTION[0], 1, 0),
                    new ItemReq(ItemID.NECKLACE_OF_PASSAGE[0], 1, 0, true),
                    new ItemReq(ItemID.RING_OF_WEALTH[0], 1, 0, true)
            )
    ));

    BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);

    public void buyItems() {
        if (!BankManager.checkInventoryItems(ItemID.VARROCK_TELEPORT)) {
            cQuesterV2.status = "Buying Items";
            General.println("[Debug]: " + cQuesterV2.status);
                buyStep.buyItems();
        }
    }

    public void getItems() {
        cQuesterV2.status = "Getting Items";
        General.println("[Debug]: " + cQuesterV2.status);
        BankManager.open(true);
        BankManager.checkEquippedGlory();
        BankManager.depositAll(true);
        BankManager.withdraw(2, true, ItemID.STAMINA_POTION[0]);
        BankManager.withdraw(1, true, ItemID.AMULET_OF_GLORY[2]);
        BankManager.withdraw(1, true, ItemID.NECKLACE_OF_PASSAGE[0]);
        BankManager.withdraw(3, true, ItemID.VARROCK_TELEPORT);
        BankManager.close(true);

    }


    public void startQuest() {
        if (!BankManager.checkInventoryItems(995)) {
            cQuesterV2.status = "Starting Quest";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(START_AREA);
            if (NpcChat.talkToNPC("Mage of Zamorak")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
        }
    }

    public void step2() {
        cQuesterV2.status = "Going to chaos temple";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(CHAOS_TEMPLE);
        if (NpcChat.talkToNPC("Mage of Zamorak")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Where do you get your runes from?");
            NPCInteraction.handleConversation(); // need this
            NPCInteraction.handleConversation("Yes");
            NPCInteraction.handleConversation();
        }
    }

    public void step3() {
        cQuesterV2.status = "Going To Aubury";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(AUBURY_AREA);
        Utils.shortSleep();
        if (NpcChat.talkToNPC("Aubury")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Can you teleport me to the Rune Essence?");
            NPCInteraction.handleConversation();
            Utils.idle(2500, 6000);
        }
    }

    public void step4() {
        cQuesterV2.status = "Going To Wizards Tower";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(WIZARDS_TOWER);
        if (NpcChat.talkToNPC("Sedridor")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Can you teleport me to the Rune Essence?");
            NPCInteraction.handleConversation();
            Utils.idle(2500, 6000);
        }
    }

    public void step5() {
        cQuesterV2.status = "Going To Wizard Cromperty";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(ARDOUGNE_AREA);
        Utils.shortSleep();
        if (NpcChat.talkToNPC("Wizard Cromperty")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Can you teleport me to the Rune Essence?");
            NPCInteraction.handleConversation();
            Utils.idle(2500, 6000);
        }
    }


    public void step6() {
        cQuesterV2.status = "Going to chaos temple";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(CHAOS_TEMPLE);
        if (CHAOS_TEMPLE.contains(Player.getPosition())) {
            if (NpcChat.talkToNPC("Mage of Zamorak")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
            if (NpcChat.talkToNPC("Mage of Zamorak")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
        }
    }


    public void step8() {
        cQuesterV2.status = "Finishing Quest";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(START_AREA);

        if (NpcChat.talkToNPC("Mage of Zamorak")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();

        }
    }

    public void step7() {
        cQuesterV2.status = "Finishing Quest";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(CHAOS_TEMPLE);
        if (CHAOS_TEMPLE.contains(Player.getPosition())) {
            if (NpcChat.talkToNPC("Mage of Zamorak")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("So what is this 'abyss' stuff?");
                NPCInteraction.handleConversation();
                NPCInteraction.handleConversation();
            }
        }
    }


    @Override
    public void execute() {

        if (Game.getSetting(492) == 0) {
            buyItems();
            getItems();
            startQuest();
        }
        if (Game.getSetting(492) == 1) {
            step2();
        }
        if (Game.getSetting(492) == 2) {
            step3();
            step4();
            step5();
            step6();
        }
        if (Game.getSetting(492) == 3) {
            step7();
        }
        if (Game.getSetting(492) == 4) {
            step7();
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
        return "Enter the Abyss";
    }

    @Override
    public boolean checkRequirements() {
        if (Game.getSetting(63) != 6) {
            return false;
        }
        return true;
    }
}
