package scripts.QuestPackages.RfdGoblin;

import dax.walker.utils.AccurateMouse;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.types.*;
import org.tribot.script.sdk.*;
import org.tribot.script.sdk.Inventory;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.InventoryItem;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.CooksAssistant.CooksAssistant;
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
import java.util.Optional;

public class RfdGoblin implements QuestTask {


    private static RfdGoblin quest;

    public static RfdGoblin get() {
        return quest == null ? quest = new RfdGoblin() : quest;
    }


    public static int dyedOrangeSlices = 7514;
    int GOBLIN_COOK_ID_4850 = 4850;
    int GOBLIN_COOK_ID = 4851;
    int WARTFACE_OBJ_ID = 12334;

    RSArea lumbridge = new RSArea(new RSTile(3213, 3225, 0), new RSTile(3216, 3219, 0));
    // RSArea lumbridgeKitchen = new RSArea(new RSTile(10693, 9102, 0), 20);
    RSArea goblinVillage = new RSArea(new RSTile(2959, 3507, 0), new RSTile(2962, 3504, 0));


    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.FALADOR_TELEPORT, 5, 50),
                    new GEItem(ItemID.BLUE_DYE, 1, 500),
                    new GEItem(ItemID.BREAD, 1, 300),
                    new GEItem(ItemID.KNIFE, 1, 300),
                    new GEItem(ItemID.ORANGE, 1, 500),
                    new GEItem(ItemID.SPICE, 1, 500),
                    new GEItem(ItemID.FISHING_BAIT, 1, 300),
                    new GEItem(ItemID.BUCKET_OF_WATER, 1, 300),
                    new GEItem(ItemID.CHARCOAL, 1, 300),
                    new GEItem(ItemID.LUMBRIDGE_TELEPORT, 3, 300),
                    new GEItem(ItemID.STAMINA_POTION[0], 1, 15),
                    new GEItem(ItemID.AMULET_OF_GLORY[2], 1, 15),
                    new GEItem(ItemID.RING_OF_WEALTH[0], 1, 25)
            )
    );

    InventoryRequirement initialItemReqs = new InventoryRequirement(new ArrayList<>(
            Arrays.asList(
                    new ItemReq(ItemID.STAMINA_POTION[0], 3, 0),
                    new ItemReq(ItemID.AMULET_OF_GLORY[2], 2, 0),
                    new ItemReq(ItemID.BLUE_DYE, 1),
                    new ItemReq(ItemID.BREAD, 1),
                    new ItemReq(ItemID.KNIFE, 1),
                    new ItemReq(ItemID.ORANGE, 1),
                    new ItemReq(ItemID.SPICE, 1),
                    new ItemReq(ItemID.FISHING_BAIT, 1),
                    new ItemReq(ItemID.BUCKET_OF_WATER, 1),
                    new ItemReq(ItemID.CHARCOAL, 1),
                    new ItemReq(ItemID.LUMBRIDGE_TELEPORT, 3, 1),
                    new ItemReq(ItemID.FALADOR_TELEPORT, 5, 1),
                    new ItemReq(ItemID.RING_OF_WEALTH[0], 1, 0, true)
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
        if (!initialItemReqs.check()) {
            cQuesterV2.status = "Getting Items";
            General.println("[Debug]: Getting Items");
            BankManager.open(true);
            BankManager.checkEquippedGlory();
            BankManager.depositAll(true);
            initialItemReqs.withdrawItems();
        }
    }

    public boolean enterRfdInstance() {
        if (!Game.isInInstance() || NPCs.findNearest("Gypsy").length == 0) {
            PathingUtil.walkToArea(lumbridge);
            if (Utils.clickObj("Large door", "Open")) {
                Waiting.waitUntil(9500, 500,
                        () -> NPCs.findNearest("Gypsy").length > 0);
                General.sleep(4000, 5000);//sleep once we enter instance as we cant' click right away

            }
        }
        return NPCs.findNearest("Gypsy").length > 0 && Game.isInInstance();
    }

    public void goToStart() {
        if (!BankManager.checkInventoryItems(ItemID.BLUE_DYE,
                ItemID.BREAD, ItemID.FISHING_BAIT, ItemID.ORANGE, ItemID.SPICE, ItemID.KNIFE, ItemID.CHARCOAL)) {
            buyItems();
            getItems();
        }
        cQuesterV2.status = "Going to Start";
        if (enterRfdInstance() &&
                Utils.clickObj(WARTFACE_OBJ_ID, "Inspect") && NpcChat.waitForChatScreen()) {
            ChatScreen.handle("Yes.");
            Utils.modSleep();
        }
    }


    // area is instanced so we check if the goblin cook is there
    public boolean goToGoblinCook() {
        boolean b = Query.npcs().idEquals(GOBLIN_COOK_ID_4850, GOBLIN_COOK_ID).isAny();
        if (!b) {
            cQuesterV2.status = " Going to cook";
            // go to the hut
            if (PathingUtil.walkToTile(new RSTile(2960, 3506, 0)))
               Waiting.waitUntil(9000, 500,
                       () -> goblinVillage.contains(Player.getPosition()));

            //click ladder
            if (Utils.clickObj(12389, "Climb-down"))
                Waiting.waitUntil(6000, 1200, () ->
                        Query.npcs().idEquals(GOBLIN_COOK_ID_4850, GOBLIN_COOK_ID).isAny());

        }
        return Query.npcs().idEquals(GOBLIN_COOK_ID_4850, GOBLIN_COOK_ID).isAny();
    }

    public void goToGoblinVillage() {
        cQuesterV2.status = "Talking to cook";
        if (goToGoblinCook() && NpcChat.talkToNPC(GOBLIN_COOK_ID_4850) && NpcChat.waitForChatScreen()) {
            ChatScreen.handle("I need your help...");
            ChatScreen.handle("What do you need? Maybe I can get it for you.");
            ChatScreen.handle();
            Utils.modSleep();
        }
    }

    public void talkToCook2() {
        cQuesterV2.status = "Talking to cook (#2)";
        if (goToGoblinCook() && NpcChat.talkToNPC(GOBLIN_COOK_ID_4850) && NpcChat.waitForChatScreen()) {
            ChatScreen.handle("I've got the charcoal you were after.");
            ChatScreen.handle();
            // General.sleep(General.random(20000, 30000));
        }
        if (Waiting.waitUntil(4000, 500, Utils::inCutScene))
            Utils.cutScene();

    }

    public void talkToChef3() {
        cQuesterV2.status = "Talking to cook (#3)";
        General.println("[Debug: " + cQuesterV2.status);
        if (goToGoblinCook() && NpcChat.talkToNPC(GOBLIN_COOK_ID) && NpcChat.waitForChatScreen()) {
            ChatScreen.handle("I need your help...");
            ChatScreen.handle();
        }
    }

    public void foodPrep() {
        cQuesterV2.status = "Making food";

        // slice orange
        if (!Inventory.contains(ItemID.ORANGE_SLICES) && !Inventory.contains(dyedOrangeSlices)) {
            if (Utils.useItemOnItem(ItemID.ORANGE, ItemID.KNIFE))
                Waiting.waitUntil(4000, 400, MakeScreen::isOpen);

            if (MakeScreen.isOpen() && MakeScreen.make(ItemID.ORANGE_SLICES))
                Timer.abc2WaitCondition(() -> Inventory.contains(ItemID.ORANGE_SLICES), 5000, 9000);
        }

        // make dyed slices
        if (!Inventory.contains(dyedOrangeSlices) &&
                Utils.useItemOnItem(ItemID.BLUE_DYE, ItemID.ORANGE_SLICES)) {
            Waiting.waitUntil(4000, 400, () -> Inventory.contains(dyedOrangeSlices));
        }
        // make spicy maggots
        if (!Inventory.contains(ItemID.SPICY_MAGGOTS) &&
                Utils.useItemOnItem(ItemID.SPICE, ItemID.FISHING_BAIT)) {
            Waiting.waitUntil(4000, 400,
                    () -> Inventory.contains(ItemID.SPICY_MAGGOTS));

        }
        // make soggy bread
        if (!Inventory.contains(ItemID.SOGGY_BREAD) &&
                Utils.useItemOnItem(ItemID.BUCKET_OF_WATER, ItemID.BREAD))
            Timer.waitCondition(() -> Inventory.contains(ItemID.SOGGY_BREAD), 5000, 8000);

    }


    public void talkToChef4() {
        if (goToGoblinCook() && NpcChat.talkToNPC(GOBLIN_COOK_ID) && NpcChat.waitForChatScreen()) {
            ChatScreen.handle("I've got the ingredients we need...");
            Timer.waitCondition(() -> Inventory.contains(ItemID.SLOP_OF_COMPROMISE), 5000, 10000);
            Utils.shortSleep();
        }
    }

    public void finishQuest() {
        if (enterRfdInstance()) {
            RSObject[] wartface = Objects.findNearest(20, WARTFACE_OBJ_ID);
            if (wartface.length > 0) {
                cQuesterV2.status = "Giving food to general";
                General.println("[Debug]: " + cQuesterV2.status);

                if (!wartface[0].isClickable()) {
                    Walking.blindWalkTo(wartface[0].getPosition());
                    Utils.shortSleep();
                    wartface[0].adjustCameraTo();
                }
                if (Utils.useItemOnObject(ItemID.SLOP_OF_COMPROMISE, WARTFACE_OBJ_ID)) {
                    NPCInteraction.waitForConversationWindow();
                    ChatScreen.handle();
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
        } else if (Game.getSetting(679) == 3070) {
            goToGoblinVillage();
        } else if (Game.getSetting(679) == 5630) {
            talkToCook2();
        } else if (Game.getSetting(679) == 8190) {
            cQuesterV2.status = "RFD Goblin: Cut scene";
            Timer.waitCondition(() -> Game.getSetting(679) != 8190, 40000);
            Utils.cutScene();
        } else if (Game.getSetting(679) == 73726) {
            talkToChef3();
        } else if (Game.getSetting(679) == 81406) {
            foodPrep();
            talkToChef4();
        } else if (Game.getSetting(679) == 83966) {
            finishQuest();
        } else if (Game.getSetting(679) == 86524) {
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

    @Override
    public List<Requirement> getGeneralRequirements() {
        return null;
    }

    @Override
    public List<ItemRequirement> getBuyList() {
        return null;
    }

    @Override
    public boolean isComplete() {
        return Quest.LUNAR_DIPLOMACY.getState().equals(Quest.State.COMPLETE);
    }
}