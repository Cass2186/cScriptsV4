package scripts.QuestPackages.FishingContest;

import dax.walker.utils.AccurateMouse;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.Waiting;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.WitchsHouse.WitchsHouse;
import scripts.QuestSteps.BuyItemsStep;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.ItemRequirement;
import scripts.Requirements.Requirement;
import scripts.Tasks.Priority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FishingContest implements QuestTask {

    private static FishingContest quest;

    public static FishingContest get() {
        return quest == null ? quest = new FishingContest() : quest;
    }

    int garlic = 1550;
    int fishingRod = 307;
    int skills6 = 11968;
    int camelotTab = 8010;
    int spade = 952;
    int combatBracelet6 = 11972;
    int fishingPass = 27;
    int redVineWorm = 25;


    RSArea startArea = new RSArea(new RSTile(2818, 3487, 0), new RSTile(2823, 3482, 0));
    RSArea entrance = new RSArea(new RSTile(2643, 3444, 0), new RSTile(2648, 3436, 0));
    RSArea fishingArea = new RSArea(new RSTile(2642, 3434, 0), new RSTile(2631, 3446, 0));
    RSArea woods = new RSArea(new RSTile(2626, 3491, 0), new RSTile(2633, 3498, 0));

    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(garlic, 1, 500),
                    new GEItem(ItemID.FISHING_ROD, 1, 500),
                    new GEItem(ItemID.SKILLS_NECKLACE[0], 1, 50),
                    new GEItem(ItemID.CAMELOT_TELEPORT, 10, 50),
                    new GEItem(ItemID.SPADE, 1, 500),
                    new GEItem(ItemID.COMBAT_BRACELET[0], 1, 20),
                    new GEItem(ItemID.STAMINA_POTION[0], 2, 15),
                    new GEItem(ItemID.RING_OF_WEALTH[0], 1, 25)
            )
    );


    BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);


    public void buyItems() {
        cQuesterV2.status = "Buying Items";
        General.println("[Debug]: Buying Items");
        buyStep.buyItems();
    }

    public void getItems1() {
        cQuesterV2.status = "Getting Items";
        General.println("[Debug]: " + cQuesterV2.status);
        BankManager.open(true);
        BankManager.depositAll(true);
        BankManager.checkEquippedGlory();
        BankManager.withdraw(1, true, garlic);
        BankManager.withdraw(1, true, fishingRod);
        BankManager.withdraw(1, true, skills6);
        BankManager.withdraw(1, true, combatBracelet6);
        BankManager.withdraw(5, true, camelotTab);
        BankManager.withdraw(1, true, spade);
        BankManager.withdraw(500, true, 995);
        BankManager.withdraw(1, true, BankManager.STAMINA_POTION[0]);
        BankManager.withdraw(3, true, redVineWorm);
        BankManager.withdraw(1, true, fishingPass);
        BankManager.close(true);

    }

    public void startQuest() {
        if (!BankManager.checkInventoryItems(garlic, fishingRod, camelotTab, spade)) {
            buyItems();
            getItems1();
        } else {
            cQuesterV2.status = "Starting Quest";
            General.println("[Debug]: " + cQuesterV2.status);

            PathingUtil.walkToArea(startArea);

            if (Utils.clickObject("Stairs", "Climb-down", false)) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Why not?");
                NPCInteraction.handleConversation("If you were my friend I wouldn't mind it.");
                NPCInteraction.handleConversation("Well, let's be friends!");
                NPCInteraction.handleConversation("And how am I meant to do that?");
                NPCInteraction.handleConversation();
            }
        }
    }

    public void collectWorms() {
        if (!woods.contains(Player.getPosition())) {
            cQuesterV2.status = "Going to woods";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToTile(new RSTile(2630, 3494, 0), 1, false);
        }
        if (woods.contains(Player.getPosition())) {
            RSObject[] redVine = Objects.findNearest(20, "Vine");
            if (redVine.length > 0) {
                if (AccurateMouse.click(Objects.findNearest(20, "Vine")[0], "Check")) {
                    Timer.waitCondition(() -> Inventory.find(redVineWorm).length > 0, 5000, 7000);
                    if (Inventory.find(redVineWorm).length > 0) {
                        if (AccurateMouse.click(redVine[0], "Check"))
                            Timer.waitCondition(() -> Inventory.find(redVineWorm)[0].getStack() == 2, 5000, 7000);
                        if (AccurateMouse.click(redVine[0], "Check"))
                            Timer.abc2WaitCondition(() -> Inventory.find(redVineWorm)[0].getStack() == 3, 5000, 7000);
                    }
                }
            }
        }
    }

    public void step2() {
        if (Inventory.find(redVineWorm).length > 0) {
            if (Inventory.find(redVineWorm)[0].getStack() < 3)
                collectWorms();

        } else if (Inventory.find(redVineWorm).length < 1)
            collectWorms();

    }

    public void step3() {
        if (Inventory.find(fishingRod).length < 1) {
            getItems1();

        } else if (!fishingArea.contains(Player.getPosition()) && Inventory.find(redVineWorm).length > 0) {
            if (Inventory.find(redVineWorm)[0].getStack() >= 3) {
                cQuesterV2.status = "Starting Contest";
                General.println("[Debug]: " + cQuesterV2.status);
                PathingUtil.walkToArea(entrance);
            }
            if (NpcChat.talkToNPC("Morris")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
            PathingUtil.localNavigation(fishingArea.getRandomTile());
        }
    }

    public void step5() {
        if (Inventory.find(garlic).length > 0 && Inventory.find(redVineWorm).length > 0) {
            cQuesterV2.status = "Hiding Garlic";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(fishingArea);
            if (fishingArea.contains(Player.getPosition())) {
                if (PathingUtil.localNavigation(new RSTile(2638, 3445)))
                    PathingUtil.movementIdle();

                if (Utils.useItemOnObject(garlic, "Wall Pipe")) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                    Utils.modSleep();
                }
            }
        }

        if (Inventory.find(garlic).length < 1 && Inventory.find(redVineWorm).length > 0) {
            cQuesterV2.status = "Fishing";
            General.println("[Debug]: " + cQuesterV2.status);
            RSNPC[] fishingSpot = NPCs.find(4080);

            if (fishingSpot.length > 0) {
                if (Utils.clickNPC(fishingSpot[0], "Bait", false)) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                    PathingUtil.movementIdle();
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation("I'll enter the competition please.");
                    NPCInteraction.handleConversation();
                    General.sleep(6000, 10000);
                }
            }
        }
    }

    public void step6() {
        if (Inventory.find(26).length < 1) {
            RSNPC[] fishingSpot = NPCs.find(4080);
            if (fishingSpot.length > 0) {
                if (Utils.clickNPC(fishingSpot[0], "Bait", false)) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                    General.sleep(10000, 14000); // waits for finishing catching
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                }
            }
        }
    }

    public void step7() {
        cQuesterV2.status = "Finishing Quest";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(startArea);

        if (NpcChat.talkToNPC("Vestri")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }
    }

    int GAME_SETTING = 11;

    @Override
    public void execute() {
        if (Game.getSetting(11) == 0) {
            buyItems();
            getItems1();
            startQuest();
            Utils.longSleep();
        }
        if (Game.getSetting(11) == 1) {
            step2();
            step3();
            step5();
            Utils.longSleep();
        }
        if (Game.getSetting(11) == 3) {
            step6();
        }
        if (Game.getSetting(11) == 4) {
            step7();
        }
        if (Game.getSetting(11) == 5) {
            Utils.closeQuestCompletionWindow();
            cQuesterV2.taskList.remove(FishingContest.get());
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
        return "Fishing Contest";
    }

    @Override
    public boolean checkRequirements() {
        return false;
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
