package scripts.QuestPackages.RestlessGhost;

import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSTile;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.NatureSpirit.NatureSpirit;
import scripts.QuestSteps.BuyItemsStep;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.InventoryRequirement;
import scripts.Requirements.ItemReq;
import scripts.Tasks.Priority;

import java.util.ArrayList;
import java.util.Arrays;

public class RestlessGhost implements QuestTask {

    private static RestlessGhost quest;

    public static RestlessGhost get() {
        return quest == null ? quest = new RestlessGhost() : quest;
    }

    int GHOST_SPEAK = 552;

    RSArea LUMBRIDGE_CHURCH = new RSArea(new RSTile(3246, 3204, 0), new RSTile(3240, 3213, 0));
    RSArea SWAMP_HOUSE = new RSArea(new RSTile(3144, 3177, 0), new RSTile(3151, 3173, 0));
    RSArea GRAVEYARD_HOUSE = new RSArea(new RSTile(3247, 3195, 0), new RSTile(3252, 3190, 0));
    RSArea WIZARD_TOWER = new RSArea(new RSTile(3107, 3159, 0), new RSTile(3103, 3162, 0));
    RSArea ALTAR_AREA = new RSArea(new RSTile(3121, 9564, 0), new RSTile(3111, 9569, 0));


    RSTile ALTAR_TILE = new RSTile(3119, 9567, 0);

    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemId.LUMBRIDGE_TELEPORT, 7, 50),
                    new GEItem(ItemId.NECKLACE_OF_PASSAGE[0], 1, 100),
                    new GEItem(ItemId.LOBSTER, 12, 50),
                    new GEItem(ItemId.STAMINA_POTION[0], 2, 15),
                    new GEItem(ItemId.RING_OF_WEALTH[0], 1, 25)
            )
    );

    InventoryRequirement initialItemReqs = new InventoryRequirement(new ArrayList<>(
            Arrays.asList(
                    new ItemReq(ItemId.LOBSTER, 15, 2),
                    new ItemReq(ItemId.ADAMANT_SCIMITAR, 1, 1, true, true),
                    new ItemReq(ItemId.VARROCK_TELEPORT, 5, 1),
                    new ItemReq(ItemId.EMPTY_BUCKET, 1, 1),
                    new ItemReq(ItemId.STAMINA_POTION[0], 1, 0),
                    new ItemReq(ItemId.NECKLACE_OF_PASSAGE[0], 1, 0, true),
                    new ItemReq(ItemId.RING_OF_WEALTH[0], 1, 0, true)
            )
    ));

    BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);

    public void buyItems() {
        cQuesterV2.status = "Buying Items";
        General.println("[Debug]: Buying Items");
        buyStep.buyItems();
    }

    public void getItems1() {
        cQuesterV2.status = "Getting Items";
        General.println("[Debug]: Getting Items");
        BankManager.open(true);
        BankManager.checkEquippedGlory();
        BankManager.depositAll(true);
        BankManager.withdraw(6, true, ItemId.LOBSTER);
        BankManager.withdraw(1, true, ItemId.STAMINA_POTION[0]);
        BankManager.withdraw(4, true, ItemId.LUMBRIDGE_TELEPORT);
        BankManager.withdraw(1, true, ItemId.NECKLACE_OF_PASSAGE[0]);
        BankManager.withdraw(1, true, ItemId.RING_OF_WEALTH[0]);
        BankManager.close(true);
    }


    public void startQuest() {
        cQuesterV2.status = "Going to Father Aereck";
        PathingUtil.walkToArea(LUMBRIDGE_CHURCH);
        if (NpcChat.talkToNPC("Father Aereck")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("I'm looking for a quest!");
            NPCInteraction.handleConversation("Ok, let me help then.", "Yes.");
            NPCInteraction.handleConversation();
        }
    }

    public void step2() {
        cQuesterV2.status = "Going to Father Urhney";
        PathingUtil.walkToArea(SWAMP_HOUSE);

        if (NpcChat.talkToNPC("Father Urhney")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Father Aereck sent me to talk to you.");
            NPCInteraction.handleConversation("He's got a ghost haunting his graveyard.");
            NPCInteraction.handleConversation();
        }
    }


    public void talkToGhost() {
        PathingUtil.walkToArea(GRAVEYARD_HOUSE);

        if (GRAVEYARD_HOUSE.contains(Player.getPosition())) {
            Utils.equipItem(GHOST_SPEAK);
            General.sleep(General.random(250, 500));
            if (Utils.clickObj(2145, "Open"))
                Timer.abc2WaitCondition(() -> NPCs.find(922).length > 0, 7000, 9000);

            if (NpcChat.talkToNPC(922)) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Yep, now tell me what the problem is.");
                NPCInteraction.handleConversation();
            }
        }
    }


    public void step4() {
        PathingUtil.walkToArea(ALTAR_AREA);
        if (Utils.clickObj("Altar", "Search"))
            Timer.abc2WaitCondition(() -> Inventory.find(553).length > 0, 5000, 8000);
    }

    public void step5() {
        PathingUtil.walkToArea(GRAVEYARD_HOUSE);

        cQuesterV2.status = "Giving Skull back";
        Utils.equipItem(GHOST_SPEAK);
        General.sleep(General.random(200, 600));
        if (Utils.clickObj(2145, "Open"))
            Timer.abc2WaitCondition(() -> NPCs.find(922).length > 0, 7000, 9000);

        if (NpcChat.talkToNPC(922)) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }

        if (Utils.useItemOnObject(553, 15061)) {
            Timer.abc2WaitCondition(() -> Game.getSetting(107) == 5, 25000, 35000);
            Utils.longSleep();
        }
        Utils.cutScene();

    }


    private void failSafe() {
        if (GRAVEYARD_HOUSE.contains(Player.getPosition())) {
            cQuesterV2.status = "Failsafe";
            RSItem[] item = Inventory.find(ItemId.RING_OF_WEALTH);
            if (item.length > 0) {
                if (item[0].click("Rub"))
                    Timer.waitCondition(() -> Interfaces.isInterfaceSubstantiated(219, 1, 2), 5000, 8000);
            }

            item = Equipment.find(ItemId.RING_OF_WEALTH);
            if (item.length > 0) {
                if (item[0].click("Rub"))
                    Timer.waitCondition(() -> Interfaces.isInterfaceSubstantiated(219, 1, 2), 5000, 8000);
            }

            RSTile position = Player.getPosition();

            if (Interfaces.isInterfaceSubstantiated(219, 1, 2)) {
                Interfaces.get(219, 1, 2).click();
                Timer.waitCondition(() -> !Player.getPosition().equals(position), 8000, 12000);
            }

        }
    }

    int GAME_SETTING = 107;

    @Override
    public void execute() {
        if (Game.getSetting(107) == 0) {
            buyItems();
            getItems1();
            startQuest();
        }
        if (Game.getSetting(107) == 1) {
            step2();
        }
        if (Game.getSetting(107) == 2) {
            talkToGhost();
        }
        if (Game.getSetting(107) == 3) {
            step4();
        }
        if (Game.getSetting(107) == 4) {
            step5();
        }
        if (Game.getSetting(107) == 5) {
            Utils.closeQuestCompletionWindow();
            failSafe();
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
        return "Restless Ghost";
    }

    @Override
    public boolean checkRequirements() {
        return true;
    }
}
