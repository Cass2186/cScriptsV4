package scripts.QuestPackages.AscentOfArceuus;

import dax.walker.utils.AccurateMouse;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.types.*;
import org.tribot.script.sdk.Chatbox;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.ClientOfKourend.ClientOfKourend;
import scripts.QuestSteps.BuyItemsStep;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.InventoryRequirement;
import scripts.Requirements.ItemReq;
import scripts.Tasks.Priority;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

public class AscentOfArceuus implements QuestTask {


    private static AscentOfArceuus quest;

    public static AscentOfArceuus get() {
        return quest == null ? quest = new AscentOfArceuus() : quest;
    }

    int GAME_SETTING = 2071; // change!

    int BATTLEFRONT_TELEPORT = 22949;
    int ADAMANT_SCIMITAR = 1331;
    int RUNE_SCIMITAR = 1333;


    RSArea START_AREA = new RSArea(new RSTile(1697, 3745, 0), new RSTile(1706, 3737, 0));
    RSArea CASTLE_SECOND_FLOOR = new RSArea(new RSTile(1612, 3674, 1), new RSTile(1621, 3670, 1));
    RSArea OUTSIDE_TOWER_OF_MAGIC = new RSArea(new RSTile(1597, 3821, 0), new RSTile(1600, 3818, 0));
    RSArea LARGE_OUTSIDE_TOWER_OF_MAGIC = new RSArea(new RSTile(1596, 3826, 0), new RSTile(1603, 3813, 0));

    RSTile BUSH_TILE_1 = new RSTile(1335, 3744, 0);
    RSTile BUSH_TILE_2 = new RSTile(1318, 3750, 0);
    RSTile BUSH_TILE_3 = new RSTile(1306, 3750, 0);
    RSTile BUSH_TILE_4 = new RSTile(1288, 3752, 0);
    RSTile BUSH_TILE_5 = new RSTile(1287, 3740, 0);
    RSTile BUSH_TILE_6 = new RSTile(1282, 3727, 0);


    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.STAFF_OF_AIR, 1, 200),
                    new GEItem(ItemID.MIND_RUNE, 400, 20),
                    new GEItem(ItemID.FIRE_RUNE, 1200, 20),
                    new GEItem(ItemID.LOBSTER, 12, 60),
                    new GEItem(BATTLEFRONT_TELEPORT, 4, 100),
                    new GEItem(ItemID.COMBAT_BRACELET[2], 1, 20),
                    new GEItem(ItemID.SKILLS_NECKLACE[0], 1, 20),
                    new GEItem(ItemID.STAMINA_POTION[0], 3, 15),
                    new GEItem(ItemID.RING_OF_WEALTH[0], 1, 25)
            )
    );

    InventoryRequirement initialItemReqs = new InventoryRequirement(new ArrayList<>(
            Arrays.asList(
                    new ItemReq(BATTLEFRONT_TELEPORT, 4),
                    new ItemReq(ItemID.COMBAT_BRACELET[2], 1, 0, true, true),
                    new ItemReq(ItemID.LOBSTER, 10, 1),
                    new ItemReq(ItemID.MIND_RUNE, 400, 30),
                    new ItemReq(ItemID.FIRE_RUNE, 1200, 90),
                    new ItemReq(ItemID.STAFF_OF_AIR, 1, 1, true, true),
                    new ItemReq(ItemID.STAMINA_POTION[0], 3, 0),
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
        if (!initialItemReqs.check()) {
            cQuesterV2.status = "Getting Items";
            General.println("[Debug]: " + cQuesterV2.status);
            BankManager.open(true);
            BankManager.depositAll(true);
            BankManager.checkEquippedGlory();
            initialItemReqs.withdrawItems();
            BankManager.close(true);
        }
    }

    private void startQuest() {
        cQuesterV2.status = "Going to start";
        PathingUtil.walkToArea(START_AREA);

        if (NpcChat.talkToNPC("Mori")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("What can I do to help?");
            NPCInteraction.handleConversation("We should let someone know about this.");
            NPCInteraction.handleConversation("Yes.");
            NPCInteraction.handleConversation();
        }
    }

    private void step2() {
        cQuesterV2.status = "Going to Castle";
        PathingUtil.walkToArea(CASTLE_SECOND_FLOOR);

        if (NpcChat.talkToNPC("Councillor Andrews")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("There's been a death in Arceuus.");
            NPCInteraction.handleConversation();
        }
    }

    private void step3() {
        cQuesterV2.status = "Going to Mori";
        PathingUtil.walkToArea(START_AREA);

        if (NpcChat.talkToNPC("Mori")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("What should we do now?");
            NPCInteraction.handleConversation();
        }
    }

    private void step4() {
        cQuesterV2.status = "Going to Tower of Magic";
        PathingUtil.walkToArea(OUTSIDE_TOWER_OF_MAGIC);
        if (NpcChat.talkToNPC("Tower Mage")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }

    }

    private void step5() {
        cQuesterV2.status = "Entering Tower of Magic";

        // Utils.walkToArea(OUTSIDE_TOWER_OF_MAGIC); //need to check that we aren't in the tower (instanced) or it will tele out

        if (!Autocast.isAutocastEnabled(Autocast.FIRE_STRIKE))
            Autocast.enableAutocast(Autocast.FIRE_STRIKE);

        if (LARGE_OUTSIDE_TOWER_OF_MAGIC.contains(Player.getPosition())) {
            if (Utils.clickObject("Door", "Open", false)) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Yes.");
                Utils.modSleep();
            }
        }

        int eatAt = General.random(40, 65);
        RSNPC[] tormentedSoul = NPCs.findNearest("Tormented soul");
        if (tormentedSoul.length > 0 && CombatUtil.clickTarget(tormentedSoul[0])) {
            Timer.waitCondition(() -> Combat.isUnderAttack(), 5000, 7000);
            CombatUtil.waitUntilOutOfCombat("Tormented soul", eatAt);
        }
        if (Combat.getHPRatio() < eatAt && Inventory.find(ItemID.LOBSTER).length > 0) {
            Inventory.find(ItemID.LOBSTER)[0].click("Eat");
            Utils.microSleep();
        }

    }


    private void step6() {
        cQuesterV2.status = "Going to upper floor";

        if (Utils.clickObject("Stairs", "Climb", false)) {
            int plane = Player.getPosition().getPlane();
            Timer.waitCondition(() -> Player.getPosition().getPlane() != plane, 10000, 15000);
            Utils.shortSleep();
        }
        if (NpcChat.talkToNPC("Asteros Arceuus")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }

    }

    RSArea TOWER_OF_MAGIC_FLOOR_TWO = new RSArea(new RSTile(1563, 3838, 1), new RSTile(1596, 3802, 1));
    RSArea ELEVATOR = new RSArea(new RSTile(1312, 3806, 0), new RSTile(1310, 3808, 0));
    RSArea NEAR_BIG_DUDES = new RSArea(new RSTile(1315, 10202, 0), new RSTile(1309, 10209, 0));

    private void step7() {
        cQuesterV2.status = "Going to Mount Karuulm";
        PathingUtil.walkToArea(NEAR_BIG_DUDES);
        if (NpcChat.talkToNPC("Kaal-Xil-Dar")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }
    }


    RSArea GRAVE_AREA = new RSArea(new RSTile(1346, 3738, 0), new RSTile(1351, 3734, 0));

    private void step8() {
        cQuesterV2.status = "Going to Grave";
        if (NEAR_BIG_DUDES.contains(Player.getPosition())){
            Utils.clickInventoryItem(ItemID.BATTLEFRONT_TELEPORT);
        }
        PathingUtil.walkToArea(GRAVE_AREA);
        if (Utils.clickObject("Ancient Grave", "Inspect", false)) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }
    }


    private void step9() {
        cQuesterV2.status = "Going to Bush 1";
        PathingUtil.walkToTile(BUSH_TILE_1, 1, false);
        PathingUtil.movementIdle();
        if (Utils.clickObject("Bush", "Inspect", false)) {
            Utils.modSleep();
        }

    }


    private void step10() {
        cQuesterV2.status = "Going to Plant";
        PathingUtil.walkToTile(BUSH_TILE_2, 1, false);
        PathingUtil.movementIdle();
        if (Utils.clickObject("Plant", "Inspect", false)) {
            Utils.modSleep();
        }
    }

    private void step11() {
        cQuesterV2.status = "Going to Plant 2";
        PathingUtil.walkToTile(BUSH_TILE_3, 1, false);
        PathingUtil.movementIdle();

        if (Utils.clickObject("Plant", "Inspect", false)) {
            Utils.modSleep();
        }
    }

    private void step12() {
        cQuesterV2.status = "Going to Tree stump";
        PathingUtil.walkToTile(BUSH_TILE_4, 1, false);
        PathingUtil.movementIdle();
        if (Utils.clickObject("Tree stump", "Inspect", false)) {
            Utils.modSleep();
        }
    }

    private void step13() {
        cQuesterV2.status = "Going to Plant 5";
        PathingUtil.walkToTile(BUSH_TILE_5, 1, false);
        PathingUtil.movementIdle();
        if (Utils.clickObject(34624, "Inspect", false)) {
            Utils.modSleep();
        }
    }

    private void step14() {
        cQuesterV2.status = "Going to Plant 6";
        PathingUtil.walkToTile(BUSH_TILE_6, 1, false);
        PathingUtil.movementIdle();
        if (!Autocast.isAutocastEnabled(Autocast.FIRE_STRIKE))
            Autocast.enableAutocast(Autocast.FIRE_STRIKE);

        RSNPC[] trappedSoul = NPCs.findNearest("Trapped Soul");

        if (trappedSoul.length > 0) {
            CombatUtil.clickTarget(trappedSoul[0]);

            CombatUtil.waitUntilOutOfCombat("Trapped Soul", 45);

            if (Combat.getHPRatio() < 45 && Inventory.find(ItemID.LOBSTER).length > 0) {
                Inventory.find(ItemID.LOBSTER)[0].click("Eat");
                Utils.microSleep();
            }

        } else if (Utils.clickObject("Plant", "Inspect", false)) {
            Utils.modSleep();
        }


    }

    private void step15() {
        cQuesterV2.status = "Going to Mount Karuulm";
        PathingUtil.walkToArea(NEAR_BIG_DUDES);
        if (NpcChat.talkToNPC("Kaal-Xil-Dar")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }
    }

    RSArea DARK_ALTAR_AREA = new RSArea(new RSTile(1721, 3876, 0), new RSTile(1710, 3889, 0));

    private void step16() {
        cQuesterV2.status = "Going to Dark altar";
        PathingUtil.walkToArea(DARK_ALTAR_AREA);
        RSObject[] rocks = Objects.findNearest(30, "Rocks");
        for (RSObject obj : rocks) {
            if (Utils.clickObject(obj, "Inspect")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                if (Game.getSetting(GAME_SETTING) == 16397)
                    break;
            }
        }
    }

    RSArea TOWER_OF_MAGIC_FLOOR_ONE = new RSArea(
            new RSTile[]{
                    new RSTile(1596, 3816, 0),
                    new RSTile(1596, 3823, 0),
                    new RSTile(1590, 3824, 0),
                    new RSTile(1590, 3830, 0),
                    new RSTile(1583, 3830, 0),
                    new RSTile(1583, 3837, 0),
                    new RSTile(1576, 3837, 0),
                    new RSTile(1576, 3802, 0),
                    new RSTile(1584, 3803, 0),
                    new RSTile(1584, 3810, 0),
                    new RSTile(1590, 3810, 0),
                    new RSTile(1590, 3816, 0)
            }
    );

    private void step17() {
        cQuesterV2.status = "Going to Tower of Magic";
        if (!TOWER_OF_MAGIC_FLOOR_ONE.contains(Player.getPosition()) && !TOWER_OF_MAGIC_FLOOR_TWO.contains(Player.getPosition()))
            PathingUtil.walkToArea(OUTSIDE_TOWER_OF_MAGIC);

        if (LARGE_OUTSIDE_TOWER_OF_MAGIC.contains(Player.getPosition())) {
            if (Utils.clickObject("Door", "Open", false)) {
                Utils.modSleep();
            }
        }
        if (TOWER_OF_MAGIC_FLOOR_ONE.contains(Player.getPosition())) {
            cQuesterV2.status = "Going to upper floor";
            if (Utils.clickObject("Stairs", "Climb", false)) {
                int plane = Player.getPosition().getPlane();
                Timer.waitCondition(() -> Player.getPosition().getPlane() != plane, 10000, 15000);
                Utils.shortSleep();
            }
        }
        if (TOWER_OF_MAGIC_FLOOR_TWO.contains(Player.getPosition())) {
            cQuesterV2.status = "Talking to Lord Trobin";
            if (NpcChat.talkToNPC("Lord Trobin Arceuus")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }

        }
    }

    int FAVOUR_CERTIFICATE = 22777;

    public void claimFavour() {
        if (Inventory.find(FAVOUR_CERTIFICATE).length > 0) {
            AccurateMouse.click(Inventory.find(FAVOUR_CERTIFICATE)[0], "Read");
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Yes.");
            NPCInteraction.handleConversation();
        }
    }

    @Override
    public void execute() {
        if (!checkRequirements()) {
            cQuesterV2.taskList.remove(this);
            return;
        }
        General.println(Game.getSetting(GAME_SETTING));
        if (Game.getSetting(GAME_SETTING) == 0) {
            buyItems();
            getItems();
            startQuest();

        } else if (Game.getSetting(GAME_SETTING) == 1) {
            step2();

        } else if (Game.getSetting(GAME_SETTING) == 2) {
            step3();

        } else if (Game.getSetting(GAME_SETTING) == 3) {
            step4();

        } else if (Game.getSetting(GAME_SETTING) == 4) {
            step5();

        } else if (Game.getSetting(GAME_SETTING) == 5) {
            step6();

        } else if (Game.getSetting(GAME_SETTING) == 7) {
            step7();

        } else if (Game.getSetting(GAME_SETTING) == 8) {
            step8();

        } else if (Game.getSetting(GAME_SETTING) == 265) {
            step9();

        } else if (Game.getSetting(GAME_SETTING) == 777) {
            step10();

        } else if (Game.getSetting(GAME_SETTING) == 1801) {
            step11();

        } else if (Game.getSetting(GAME_SETTING) == 3849) {
            step12();

        } else if (Game.getSetting(GAME_SETTING) == 7945) {
            step13();

        } else if (Game.getSetting(GAME_SETTING) == 16137) {
            step14();

        } else if (Game.getSetting(GAME_SETTING) == 16138) {
            step14();// this is once you start getting attacked

        } else if (Game.getSetting(GAME_SETTING) == 16139) {
            step15();

        } else if (Game.getSetting(GAME_SETTING) == 16396 || Game.getSetting(GAME_SETTING) == 49164) {
            step16();

        } else if (Game.getSetting(GAME_SETTING) == 32780 || Game.getSetting(GAME_SETTING) == 12) {
            step16();

        } else if (Game.getSetting(GAME_SETTING) == 16397 || Game.getSetting(GAME_SETTING) == 49165
                || Game.getSetting(GAME_SETTING) == 32781 || Game.getSetting(GAME_SETTING) == 13 ||
                Game.getSetting(GAME_SETTING) == 81933 || Game.getSetting(GAME_SETTING) == 65549 ||
                Utils.getVarBitValue(7856) == 13) {
            step17();

        } else if (Game.getSetting(GAME_SETTING) == 16462 || Game.getSetting(GAME_SETTING) == 32846
                || Game.getSetting(GAME_SETTING) == 49230 || Game.getSetting(GAME_SETTING) == 81998
                || Game.getSetting(GAME_SETTING) == 78 || Utils.getVarBitValue(7856) == 14) {
            Utils.closeQuestCompletionWindow();
            claimFavour();
            cQuesterV2.taskList.remove(this);

        }

    }


    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return cQuesterV2.taskList.get(0).equals(AscentOfArceuus.get());
    }


    @Override
    public String questName() {
        return "Ascent of Arceuus";
    }

    @Override
    public boolean checkRequirements() {
        return Skills.getCurrentLevel(Skills.SKILLS.HUNTER) >= 12 && RSVarBit.get(4896).getValue() >= 200;
    }
}
