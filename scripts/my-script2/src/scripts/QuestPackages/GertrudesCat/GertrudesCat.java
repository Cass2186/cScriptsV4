package scripts.QuestPackages.GertrudesCat;

import dax.walker.utils.AccurateMouse;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.Game;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.Npc;
import scripts.*;
import scripts.EntitySelector.Entities;
import scripts.EntitySelector.finders.prefabs.NpcEntity;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.XMarksTheSpot.XMarksTheSpot;
import scripts.QuestSteps.BuyItemsStep;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.ItemRequirement;
import scripts.Requirements.Requirement;
import scripts.Tasks.Priority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class GertrudesCat implements QuestTask {

    private static GertrudesCat quest;

    public static GertrudesCat get() {
        return quest == null ? quest = new GertrudesCat() : quest;
    }

    public int bucketOfMilk = 1927;
    public int coins = 995;
    public int seasonedSardines = 1552;
    public int varrockTab = 8007;
    public int fluffsKitten = 1554;

    RSArea DoogleLeavesArea = new RSArea(new RSTile(3150, 3402, 0), new RSTile(3156, 3397, 0));
    RSArea inFrontOfHouse = new RSArea(new RSTile(3154, 3411, 0), new RSTile(3148, 3415, 0));
    RSArea kidArea = new RSArea(new RSTile(3212, 3437, 0), new RSTile(3223, 3428, 0));
    RSArea catAreaLevel2 = new RSArea(new RSTile(3306, 3513, 1), new RSTile(3312, 3507, 1));
    RSArea lumberYardArea = new RSArea(new RSTile(3313, 3493, 0), new RSTile(3297, 3516, 0));
    RSArea ladderArea = new RSArea(new RSTile(3308, 3510, 0), new RSTile(3311, 3508, 0));

    RSArea crate1Area = new RSArea(new RSTile(3306, 3507, 0), new RSTile(3306, 3507, 0));
    RSArea crate2Area = new RSArea(new RSTile(3303, 3507, 0), new RSTile(3303, 3507, 0));
    RSArea crate3Area = new RSArea(new RSTile(3298, 3513, 0), new RSTile(3298, 3513, 0));
    RSArea crate4Area = new RSArea(new RSTile(3305, 3499, 0), new RSTile(3305, 3499, 0));
    RSArea crate5Area = new RSArea(new RSTile(3311, 3499, 0), new RSTile(3311, 3499, 0));
    RSArea crate6Area = new RSArea(new RSTile(3315, 3514, 0), new RSTile(3314, 3515, 0));

    RSTile CRATE1_TILE = new RSTile(3306, 3507, 0);
    RSTile crate2 = new RSTile(3303, 3507, 0);
    RSTile crate3 = new RSTile(3298, 3513, 0);
    RSTile crate4 = new RSTile(3305, 3499, 0);
    RSTile crate5 = new RSTile(3311, 3499, 0);
    RSTile crate6 = new RSTile(3315, 3514, 0);

    RSNPC[] gertrudesCat;

    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.VARROCK_TELEPORT, 5, 50),
                    new GEItem(seasonedSardines, 1, 500),
                    new GEItem(ItemID.BUCKET_OF_MILK, 1, 300),
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
        BankManager.depositAll(true);
        BankManager.withdraw(3, true, varrockTab);
        BankManager.withdraw(1, true, seasonedSardines);
        BankManager.withdraw(1, true, bucketOfMilk);
        BankManager.withdraw(500, true, coins);
        BankManager.withdraw(1, true, ItemID.STAMINA_POTION[0]);
        BankManager.close(true);
        Utils.shortSleep();
    }

    public void startQuest() {
        if (!BankManager.checkInventoryItems(varrockTab, seasonedSardines, bucketOfMilk, coins)) {
            buyItems();
            getItems();
        } else {
            cQuesterV2.status = "Starting Quest";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(inFrontOfHouse);
            if (NpcChat.talkToNPC("Gertrude")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Yes.");
                NPCInteraction.handleConversation("Yes");
                Utils.modSleep();
            }
        }
    }

    public void step2() {
        if (!BankManager.checkInventoryItems(varrockTab, seasonedSardines, bucketOfMilk, coins)) {
            buyItems();
            getItems();
        } else {
            cQuesterV2.status = "Talking to Kids";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(kidArea);
            if (NpcChat.talkToNPC("Shilop")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("What will make you tell me?");
                NPCInteraction.handleConversation("Okay then, I'll pay.");
                NPCInteraction.handleConversation();
                Utils.longSleep();
            }
        }
    }

    public void step3() {
        if (!BankManager.checkInventoryItems(varrockTab, seasonedSardines, bucketOfMilk, coins)) {
            buyItems();
            getItems();
        }
        cQuesterV2.status = "Going to cat";
        General.println("[Debug]: " + cQuesterV2.status);
        if (!ladderArea.contains(Player.getPosition()) && !catAreaLevel2.contains(Player.getPosition())) {
            PathingUtil.walkToArea(ladderArea);
        }
        if (ladderArea.contains(Player.getPosition())) {
            if (Utils.clickObject("Ladder", "Climb-up", false)) {
                Timer.slowWaitCondition(() -> catAreaLevel2.contains(Player.getPosition()), 7000, 10000);
            }
        }
        if (catAreaLevel2.contains(Player.getPosition()) && Inventory.find(bucketOfMilk).length > 0) {
            gertrudesCat = NPCs.findNearest("Gertrude's cat");
            if (gertrudesCat.length > 0) {
                if (AccurateMouse.click(gertrudesCat[0], "Pick-up")) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                    Utils.modSleep();
                }
            }

            if (Utils.useItemOnNPC(bucketOfMilk, "Gertrude's cat")) {
                Timer.slowWaitCondition(() -> Inventory.find(bucketOfMilk).length < 1, 7000, 10000);
                Utils.shortSleep();
            }
        }
    }

    public void step4() {
        if (!ladderArea.contains(Player.getPosition()) && !catAreaLevel2.contains(Player.getPosition())) {
            PathingUtil.walkToArea(ladderArea);
            if (Utils.clickObject("Ladder", "Climb-up", false))
                Timer.slowWaitCondition(() -> catAreaLevel2.contains(Player.getPosition()), 7000, 9000);
        }
        gertrudesCat = NPCs.findNearest("Gertrude's cat");
        if (gertrudesCat.length > 0) {
            if (AccurateMouse.click(gertrudesCat[0], "Pick-up")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                Utils.modSleep();
            }
        }
        if (Utils.useItemOnNPC(seasonedSardines, "Gertrude's cat")) {
            Timer.abc2WaitCondition(() -> Inventory.find(seasonedSardines).length < 1, 8000, 10000);
            Utils.modSleep();
        }
        if (Utils.clickObject("Ladder", "Climb-down", false)) {
            Timer.abc2WaitCondition(() -> ladderArea.contains(Player.getPosition()), 8000, 10000);
            Utils.shortSleep();
        }
    }

    public void step5() {
        if (!ladderArea.contains(Player.getPosition()) || !catAreaLevel2.contains(Player.getPosition()))
            PathingUtil.walkToArea(ladderArea);

        if (Inventory.find(bucketOfMilk).length < 1 && catAreaLevel2.contains(Player.getPosition())) {
            gertrudesCat = NPCs.findNearest("Gertrude's cat");
            if (gertrudesCat.length > 0) {
                if (AccurateMouse.click(gertrudesCat[0], "Pick-up")) {
                    // Constants.idle(2000, 3000);
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                    Utils.modSleep();
                }
            }
            if (Utils.clickObject("Ladder", "Climb-down", false)) {
                Timer.abc2WaitCondition(() -> ladderArea.contains(Player.getPosition()), 8000, 10000);
                Utils.shortSleep();
            }
        }
    }

    RSTile crate2Tile = new RSTile(3307, 3507, 0);
    RSTile crate3Tile = new RSTile(3303, 3506, 0);
    RSTile crate5Tile = new RSTile(3298, 3514, 0);
    // RSTile crate5Tile = new RSTile(3307, 3507, 0);
    RSTile crate6Tile = new RSTile(3315, 3515, 0);

    public void step6() {
        cQuesterV2.status = "Getting kitten";
        General.println("[Debug]: " + cQuesterV2.status);
        if (!lumberYardArea.contains(Player.getPosition()) || !catAreaLevel2.contains(Player.getPosition())) {
            PathingUtil.walkToArea(ladderArea);
        }
        searchCrate(CRATE1_TILE, crate1Area);
        searchCrate(crate2Tile);
        searchCrate(crate3Tile);
        searchCrate(crate5Tile);
        searchCrate(crate4, crate4Area);
        searchCrate(crate5, crate5Area);
        searchCrate(crate6Tile);
    }


    public void searchCrate(RSTile crateTile) {
        if (Inventory.find(fluffsKitten).length < 1) {
            PathingUtil.walkToTile(crateTile, 1, false);
            PathingUtil.movementIdle();

            RSNPC[] crate = Entities.find(NpcEntity::new)
                    .idEquals(3499)
                    .tileEquals(crateTile)
                    .getResults();
            if (crate.length > 0) {
                if (!crate[0].isClickable())
                    crate[0].adjustCameraTo();

                if (AccurateMouse.click(crate[0], "Search")) {
                    NPCInteraction.waitForConversationWindow();
                    if (NPCInteraction.isConversationWindowUp())
                        NPCInteraction.handleConversation();

                    Utils.shortSleep();
                }
            }
        }
    }

    public void searchCrate(RSTile tile, RSArea area) {
        if (Inventory.find(fluffsKitten).length < 1) {
            if (PathingUtil.localNavigation(tile))
                PathingUtil.movementIdle();

            RSNPC[] crate = NPCs.findNearest(3499);
            if (crate.length > 0) {
                if (!crate[0].isClickable())
                    crate[0].adjustCameraTo();

                if (AccurateMouse.click(crate[0], "Search")) {
                    NPCInteraction.waitForConversationWindow();
                    if (NPCInteraction.isConversationWindowUp())
                        NPCInteraction.handleConversation();

                    Utils.shortSleep();
                }
            }
        }
    }

    public void step7() {
        cQuesterV2.status = "Returning kitten";
        General.println("[Debug]: " + cQuesterV2.status);
        RSItem[] invKitten = Inventory.find(fluffsKitten);
        if (invKitten.length > 0) {
            if (!lumberYardArea.contains(Player.getPosition()) || !catAreaLevel2.contains(Player.getPosition()))
                PathingUtil.walkToArea(ladderArea);

            if (ladderArea.contains(Player.getPosition()))
                if (Utils.clickObject("Ladder", "Climb-up", false))
                    Timer.abc2WaitCondition(() -> catAreaLevel2.contains(Player.getPosition()), 8000, 10000);

            if (catAreaLevel2.contains(Player.getPosition())) {
                if (Utils.useItemOnNPC(fluffsKitten, "Gertrude's cat")) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                    Utils.modSleep();
                }
            }
        }
    }

    public void finishQuest() {
        cQuesterV2.status = "Finishing Quest";
        General.println("[Debug]: " + cQuesterV2.status);
        if (!inFrontOfHouse.contains(Player.getPosition())) {
            PathingUtil.walkToArea(inFrontOfHouse);
            PathingUtil.walkToArea(inFrontOfHouse);
        }
        if (NpcChat.talkToNPC("Gertrude")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            Utils.modSleep();
        }
    }

    public void pickupKitten() {
        cQuesterV2.status = "Picking up Cat";
        General.println("[Debug]: " + cQuesterV2.status);
        Optional<Npc> kitten = Query.npcs().nameContains("Kitten")
                .isInteractingWithMe()
                .sortedByDistance().findBestInteractable();
        if (kitten.map(k -> k.interact("Pick-up")).orElse(false)) {
            Timer.slowWaitCondition(() -> Inventory.find("Pet kitten").length > 0, 5000, 7000);
        }
    }

    int GAME_SETTING = QuestVarPlayer.QUEST_GERTRUDES_CAT.getId(); //180

    @Override
    public void execute() {
        if (Game.getSetting(GAME_SETTING) == 0) {
            buyItems();
            getItems();
            startQuest();
            Utils.longSleep();
        }
        if (Game.getSetting(GAME_SETTING) == 1) {
            step2();
        }
        if (Game.getSetting(GAME_SETTING) == 2) {
            step3();
        }
        if (Game.getSetting(GAME_SETTING) == 3) {
            step4();
            Utils.longSleep();
        }
        if (Game.getSetting(GAME_SETTING) == 4) {
            step5();
            step6();
            step7();
        }
        if (Game.getSetting(GAME_SETTING) == 5) {
            finishQuest();
        }
        if (Game.getSetting(GAME_SETTING) == 6) {
            if (Utils.closeQuestCompletionWindow()) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                Utils.modSleep();
            }
            pickupKitten();
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
        return "Gertrude's Cat";
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
