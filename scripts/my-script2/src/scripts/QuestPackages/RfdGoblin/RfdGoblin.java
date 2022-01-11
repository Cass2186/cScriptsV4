package scripts.QuestPackages.RfdGoblin;

import dax.walker.utils.AccurateMouse;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.types.*;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.CooksAssistant.CooksAssistant;
import scripts.QuestSteps.BuyItemsStep;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.InventoryRequirement;
import scripts.Requirements.ItemReq;
import scripts.Tasks.Priority;

import java.util.ArrayList;
import java.util.Arrays;

public class RfdGoblin implements QuestTask {


    private static RfdGoblin quest;

    public static RfdGoblin get() {
        return quest == null ? quest = new RfdGoblin() : quest;
    }

    public static int bread = 2309;
    public static int orange = 2108;
    public static int knife = 946;
    public static int blueDye = 1767;
    public static int spice = 2007;
    public static int fishingBait = 313;
    public static int bucketOfWater = 1929;
    public static int charcoal = 973;
    public static int faladorTab = 8009;
    public static int lumbridgeTab = 8008;
    public static int orangeSlices = 2112;
    public static int slop = 7511;
    public static int dyedOrangeSlices = 7514;
    public static int spicyMaggots = 7513;
    public static int soggyBread = 7512;
    int GOBLIN_COOK_ID_4850 = 4850;
    int GOBLIN_COOK_ID = 4851;
    int WARTFACE_OBJ_ID = 12334;

    RSArea lumbridge = new RSArea(new RSTile(3213, 3225, 0), new RSTile(3216, 3219, 0));
    // RSArea lumbridgeKitchen = new RSArea(new RSTile(10693, 9102, 0), 20);
    RSArea goblinVillage = new RSArea(new RSTile(2959, 3507, 0), new RSTile(2962, 3504, 0));
    RSArea goblinUnderground = new RSArea(new RSTile(6669, 10443, 0), 15);
    RSArea goblinUnderground2 = new RSArea(new RSTile(10315, 11589, 0), 15);

    RSObject[] door = Objects.findNearest(20, "Large door");
    RSObject[] wartface = Objects.findNearest(20, 12334);
    RSObject[] ladder = Objects.findNearest(20, "Ladder");
    RSNPC[] goblinCook = NPCs.findNearest(GOBLIN_COOK_ID_4850);

    RSItem[] invItem1;
    RSItem[] invItem2;


    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemId.FALADOR_TELEPORT, 5, 50),
                    new GEItem(blueDye, 1, 500),
                    new GEItem(ItemId.BREAD, 1, 300),
                    new GEItem(ItemId.KNIFE, 1, 300),
                    new GEItem(orange, 1, 500),
                    new GEItem(spice, 1, 500),
                    new GEItem(ItemId.FISHING_BAIT, 1, 300),
                    new GEItem(bucketOfWater, 1, 300),
                    new GEItem(charcoal, 1, 300),
                    new GEItem(ItemId.LUMBRIDGE_TELEPORT, 3, 300),
                    new GEItem(ItemId.STAMINA_POTION[0], 1, 15),
                    new GEItem(ItemId.AMULET_OF_GLORY[2], 1, 15),
                    new GEItem(ItemId.RING_OF_WEALTH[0], 1, 25)
            )
    );

    InventoryRequirement initialItemReqs = new InventoryRequirement(new ArrayList<>(
            Arrays.asList(
                    new ItemReq(ItemId.STAMINA_POTION[0], 3, 0),
                    new ItemReq(ItemId.AMULET_OF_GLORY[2], 2, 0),
                    new ItemReq(blueDye, 1),
                    new ItemReq(ItemId.BREAD, 1),
                    new ItemReq(ItemId.KNIFE, 1),
                    new ItemReq(orange, 1),
                    new ItemReq(spice, 1),
                    new ItemReq(ItemId.FISHING_BAIT, 1),
                    new ItemReq(bucketOfWater, 1),
                    new ItemReq(charcoal, 1),
                    new ItemReq(ItemId.LUMBRIDGE_TELEPORT, 3, 1),
                    new ItemReq(ItemId.FALADOR_TELEPORT, 5, 1),
                    new ItemReq(ItemId.RING_OF_WEALTH[0], 1, 0, true)
            )
    ));

    BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);

    public void buyItems() {
        if (!initialItemReqs.check()) {
            cQuesterV2.status = "Buying Items";
            General.println("[Debug]: Buying Items");
            buyStep.buyItems();
        }
    }

    public void getItems() {
        if (Inventory.find(blueDye).length < 1 || Inventory.find(faladorTab).length < 1 || Inventory.find(orange).length < 1) {
            cQuesterV2.status = "Getting Items";
            General.println("[Debug]: Getting Items");
            BankManager.open(true);
            BankManager.checkEquippedGlory();
            BankManager.depositAll(true);
            BankManager.withdraw(1, true, orange);
            BankManager.withdraw(1, true, blueDye);
            BankManager.withdraw(3, true, faladorTab);
            BankManager.withdraw(1, true, fishingBait);
            BankManager.withdraw(1, true, bread);
            BankManager.withdraw(1, true, knife);
            BankManager.withdraw(1, true, spice);
            BankManager.withdraw(1, true, bucketOfWater);
            BankManager.withdraw(1, true, charcoal);
            BankManager.withdraw(3, true, lumbridgeTab);
            BankManager.withdraw(1, true, BankManager.STAMINA_POTION[0]);
            BankManager.close(true);
        }
    }

    public boolean enterInstance() {
        if (!Game.isInInstance() || NPCs.findNearest("Gypsy").length == 0) {
            PathingUtil.walkToArea(lumbridge);
            if (Utils.clickObj("Large door", "Open")) {
                Timer.waitCondition(() -> NPCs.findNearest("Gypsy").length > 0, 8000, 12000);
                General.sleep(4000, 5000);

            }
        }
        return NPCs.findNearest("Gypsy").length > 0 && Game.isInInstance();
    }

    public void goToStart() {
        if (!BankManager.checkInventoryItems(blueDye, bread, fishingBait, orange, spice, knife, charcoal)) {
            buyItems();
            getItems();
        }
        cQuesterV2.status = "Going to Start";
        if (enterInstance() &&
                Utils.clickObj(WARTFACE_OBJ_ID, "Inspect")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            Utils.modSleep();

        }
    }


    public boolean goToGoblinCook() {
        RSNPC[] goblinCook = NPCs.findNearest(GOBLIN_COOK_ID_4850, GOBLIN_COOK_ID);
        if (goblinCook.length < 1) {
            cQuesterV2.status = " Going to cook";
            if (PathingUtil.walkToTile(new RSTile(2960, 3506, 0)))
                Timer.waitCondition(() -> goblinVillage.contains(Player.getPosition()), 6000, 9000);

            ladder = Objects.findNearest(20, 12389);
            if (Utils.clickObj(12389, "Climb-down"))
                Timer.slowWaitCondition(() -> goblinUnderground.contains(Player.getPosition()), 6000, 9000);

        }
        return NPCs.findNearest(GOBLIN_COOK_ID_4850, GOBLIN_COOK_ID).length > 0 ||
                goblinUnderground.contains(Player.getPosition());
    }

    public void goToGoblinVillage() {
        cQuesterV2.status = "Talking to cook";
        if (goToGoblinCook() && NpcChat.talkToNPC(GOBLIN_COOK_ID_4850)) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("I need your help...");
            NPCInteraction.handleConversation("What do you need? Maybe I can get it for you.");
            NPCInteraction.handleConversation();
            Utils.modSleep();
        }
    }

    public void talkToCook2() {
        cQuesterV2.status = "Talking to cook (#2)";
        if (goToGoblinCook() && NpcChat.talkToNPC(GOBLIN_COOK_ID_4850)) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("I've got the charcoal you were after.");
            NPCInteraction.handleConversation();
            Utils.cutScene();
            General.sleep(General.random(20000, 30000));
        }

    }

    public void talkToChef3() {
        cQuesterV2.status = "Talking to cook (#3)";
        General.println("[Debug: " + cQuesterV2.status);
        if (goToGoblinCook() && NpcChat.talkToNPC(GOBLIN_COOK_ID)) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("I need your help...");
            NPCInteraction.handleConversation();
        }
    }

    public void foodPrep() {
        cQuesterV2.status = "RFD Goblin: Making food";
        invItem1 = Inventory.find(orange);
        invItem2 = Inventory.find(knife);
        if (invItem1.length > 0 && invItem2.length > 0 && Inventory.find(orangeSlices).length < 1 && Inventory.find(dyedOrangeSlices).length < 1) {
            if (Utils.useItemOnItem(orange, knife)) {
                Timer.slowWaitCondition(() -> Interfaces.get(270, 14) != null, 5000, 8000);

                if (Interfaces.get(270, 14) != null)
                    if (Interfaces.get(270, 14).click())
                        Timer.abc2WaitCondition(() -> Inventory.find(orangeSlices).length > 0, 5000, 9000);
            }
            General.sleep(General.random(300, 2000));
        }
        if (Inventory.find(orangeSlices).length > 0 && Inventory.find(blueDye).length > 0) {
            if (Utils.useItemOnItem(blueDye, orangeSlices)) {
                Timer.waitCondition(() -> Inventory.find(7514).length > 0, 5000, 8000);
                General.sleep(General.random(300, 2000));
            }
        }

        invItem1 = Inventory.find(spice);
        invItem2 = Inventory.find(fishingBait);
        if (invItem1.length > 0 && invItem2.length > 0 && Inventory.find(spicyMaggots).length < 1) {
            if (Utils.useItemOnItem(spice, fishingBait)) {
                Timer.waitCondition(() -> Inventory.find(fishingBait).length < 1, 5000, 8000);
                General.sleep(General.random(300, 2000));
            }
        }


        invItem1 = Inventory.find(bread);
        invItem2 = Inventory.find(bucketOfWater);
        if (invItem1.length > 0 && invItem2.length > 0 && Inventory.find(soggyBread).length < 1)
            if (Utils.useItemOnItem(bucketOfWater, bread))
                Timer.waitCondition(() -> Inventory.find(bucketOfWater).length < 1, 5000, 8000);

    }


    public void talkToChef4() {
        if (goToGoblinCook() && NpcChat.talkToNPC(GOBLIN_COOK_ID)) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("I've got the ingredients we need...");
            NPCInteraction.handleConversation();
            Timer.abc2WaitCondition(() -> Inventory.find(7511).length > 0, 5000, 10000);
            Utils.shortSleep();
        }
    }

    public void finishQuest() {
        if (enterInstance()) {
            RSObject[] wartface = Objects.findNearest(20, 12334);
            if (wartface.length > 0) {
                cQuesterV2.status = "Giving food to general";
                General.println("[Debug]: " + cQuesterV2.status);

                if (!wartface[0].isClickable()) {
                    Walking.blindWalkTo(wartface[0].getPosition());
                    Utils.shortSleep();
                    wartface[0].adjustCameraTo();

                }
                if (Utils.useItemOnObject(slop, 12334)) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                    Utils.modSleep();
                }
            }
        }
    }


    @Override
    public void execute() {
        if (Game.getSetting(679) == 510) {
            buyItems();
            getItems();
            goToStart();
        }
        if (Game.getSetting(679) == 3070) {
            goToGoblinVillage();
        }
        if (Game.getSetting(679) == 5630) {
            talkToCook2();
        }
        if (Game.getSetting(679) == 8190) {
            cQuesterV2.status = "RFD Goblin: Cut scene";
            Timer.waitCondition(() -> Game.getSetting(679) != 8190, 40000);
            Utils.cutScene();
        }
        if (Game.getSetting(679) == 73726) {
            talkToChef3();
        }
        if (Game.getSetting(679) == 81406) {
            foodPrep();
            talkToChef4();
        }
        if (Game.getSetting(679) == 83966) {
            finishQuest();
        }
        if (Game.getSetting(679) == 86524) {
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
        return "RFD: Goblin Generals";
    }

    @Override
    public boolean checkRequirements() {
        return Game.getSetting(678) == 3; //rfd cook done
    }
}