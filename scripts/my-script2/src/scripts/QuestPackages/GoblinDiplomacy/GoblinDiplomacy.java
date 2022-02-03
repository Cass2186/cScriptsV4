package scripts.QuestPackages.GoblinDiplomacy;

import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.Game;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSTile;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.MerlinsCrystal.MerlinsCrystal;
import scripts.QuestSteps.BuyItemsStep;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.InventoryRequirement;
import scripts.Requirements.ItemReq;
import scripts.Tasks.Priority;

import java.util.ArrayList;
import java.util.Arrays;

public class GoblinDiplomacy implements QuestTask {


    private static GoblinDiplomacy quest;

    public static GoblinDiplomacy get() {
        return quest == null ? quest = new GoblinDiplomacy() : quest;
    }

    public int faladorTab = 8009;
    public int goblinMail = 288;
    public int orangeGoblinMail = 286;
    public int blueGoblinMail = 287;
    public int blueDye = 1767;
    public int orangeDye = 1769;


    public RSTile doricsHouse = new RSTile(2951, 3450, 0);
    public RSTile goblinVillage = new RSTile(2957, 3512, 0);


    public RSArea goblinArea = new RSArea(goblinVillage, 2);

    RSNPC[] wartface = NPCs.findNearest("General Wartface");


    public void idle(int low, int high) {
        int sleep = General.random(low, high);
        General.println("[Debug]: Sleeping for: " + sleep);
        General.sleep(sleep);
    }
    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.FALADOR_TELEPORT, 3, 50),
                    new GEItem(ItemID.BLUE_DYE, 1, 400),
                    new GEItem(ItemID.ORANGE_DYE, 1, 400),
                    new GEItem(ItemID.GOBLIN_MAIL, 3, 400),
                    new GEItem(ItemID.AMULET_OF_GLORY[2], 2, 20),
                    new GEItem(ItemID.STAMINA_POTION[0], 2, 15),
                    new GEItem(ItemID.RING_OF_WEALTH[0], 1, 25)
            )
    );
    InventoryRequirement startInv = new InventoryRequirement(new ArrayList<>(
            Arrays.asList(
                    new ItemReq(ItemID.FALADOR_TELEPORT, 1),
                    new ItemReq(ItemID.BLUE_DYE, 1),
                    new ItemReq(ItemID.ORANGE_DYE, 1),
                    new ItemReq(ItemID.GOBLIN_MAIL, 3),
                    new ItemReq(ItemID.RING_OF_WEALTH[0], 1, true, true),
                    new ItemReq(ItemID.STAMINA_POTION[0], 1, 0)
            ))
    );

    BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);

    public void buyItems() {
        cQuesterV2.status = "Buying Items";
        buyStep.buyItems();
    }

    public void getItems() {
        if (!startInv.check()) {
            cQuesterV2.status = "Getting Items";
            General.println("[Debug]: " + cQuesterV2.status);
            startInv.withdrawItems();
        }
    }


    public void goToGoblins() {
        cQuesterV2.status = "Going to Goblins";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToTile(goblinVillage);
    }

    public void startGoblin() {
        if (!BankManager.checkInventoryItems(goblinMail, blueDye, orangeDye)) {
            buyItems();
            getItems();
        } else {
            cQuesterV2.status = "Starting quest";
            General.println("[Debug]: " + cQuesterV2.status);
            wartface = NPCs.findNearest("General Wartface");
            if (NpcChat.talkToNPC("General Wartface")) {
                generalChat();
            }
        }
    }

    public void giveOrangeArmour() {
        cQuesterV2.status = "Giving orange armour";
        General.println("[Debug]: " + cQuesterV2.status);
        goToGoblins();
        if (Utils.useItemOnNPC(orangeGoblinMail, "General Wartface")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("No, he doesn't look fat");
            NPCInteraction.handleConversation("I have some orange armour here.");
            NPCInteraction.handleConversation();
            Timer.waitCondition(this::inCutScene, 3500,5000);
            cutScene();
            Timer.waitCondition(()->!inCutScene(), 3500,5000);
        }
    }


    public void makeBlueArmour() {
        cQuesterV2.status = "Making blue armour";
        General.println("[Debug]: " + cQuesterV2.status);
        if (Utils.useItemOnItem(goblinMail, blueDye))
            Timer.waitCondition(() -> Inventory.find(blueGoblinMail).length > 0, 5000, 8000);
    }

    public void makeOrangeArmour() {
        cQuesterV2.status = "Making Orange armour";
        General.println("[Debug]: " + cQuesterV2.status);
        if (Utils.useItemOnItem(goblinMail, orangeDye))
            Timer.waitCondition(() -> Inventory.find(orangeGoblinMail).length > 0, 5000, 8000);
    }


    public void giveBlueArmour() {
        cQuesterV2.status = "Giving blue armour";
        General.println("[Debug]: " + cQuesterV2.status);
        if (Utils.useItemOnNPC(blueGoblinMail, "General Wartface")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("I have some blue armour here.");
            NPCInteraction.handleConversation();
            Timer.waitCondition(this::inCutScene, 3500,5000);
            cutScene();
            Timer.waitCondition(()->!inCutScene(), 3500,5000);
        }
        cutScene();
    }

    public void giveBrownArmour() {
        cQuesterV2.status = "Giving brown armour";
        General.println("[Debug]: " + cQuesterV2.status);
        if (Utils.useItemOnNPC(goblinMail, "General Wartface")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            Timer.waitCondition(this::inCutScene, 3500,5000);
            cutScene();
            Timer.waitCondition(()->!inCutScene(), 3500,5000);
        }
        cutScene();
    }

    public void generalChat() {
        NPCInteraction.waitForConversationWindow();
        if (NPCInteraction.isConversationWindowUp())
            NPCInteraction.handleConversation("Do you want me to pick an armour colour for you?",
                    "No, he doesn't look fat", "What about a different colour?");

        if (NPCInteraction.isConversationWindowUp())
            NPCInteraction.handleConversation("What about a different colour?");

        NPCInteraction.waitForConversationWindow();
        if (NPCInteraction.isConversationWindowUp())
            NPCInteraction.handleConversation();
    }

    public boolean inCutScene() {
        return Game.getSetting(1021) == 116576;
    }

    public void cutScene() {
        while (inCutScene()) {
            General.println("[Debug]: Cutscene Idle");
            General.sleep(150, 300);
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }
    }


    @Override
    public void execute() {
        if (Game.getSetting(62) == 0) {
            buyItems();
            getItems();
            goToGoblins();
            startGoblin();
        }
        if (Game.getSetting(62) == 3) {
            makeOrangeArmour();
            giveOrangeArmour();

        }
        if (Game.getSetting(62) == 4) {
            makeBlueArmour();
            giveBlueArmour();
        }
        if (Game.getSetting(62) == 5) {
            giveBrownArmour();
            Utils.closeQuestCompletionWindow();
        }
        if (Game.getSetting(62) == 6) {
            Utils.closeQuestCompletionWindow();
            General.sleep(General.random(300, 800));
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
        return "Goblin Diplomacy";
    }

    @Override
    public boolean checkRequirements() {
        return true;
    }
}
