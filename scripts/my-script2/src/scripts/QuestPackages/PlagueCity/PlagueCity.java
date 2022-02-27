package scripts.QuestPackages.PlagueCity;

import dax.api_lib.DaxWalker;
import dax.walker.utils.AccurateMouse;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.types.*;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.types.GroundItem;
import org.tribot.script.sdk.types.LocalTile;
import org.tribot.script.sdk.types.WorldTile;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.RomeoAndJuliet.RomeoAndJuliet;
import scripts.QuestSteps.BuyItemsStep;
import scripts.QuestSteps.GroundItemStep;
import scripts.QuestSteps.NPCStep;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.ItemRequirement;
import scripts.Requirements.Requirement;
import scripts.Tasks.Priority;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.stream.Collectors;

public class PlagueCity implements QuestTask {

    private static PlagueCity quest;

    public static PlagueCity get() {
        return quest == null ? quest = new PlagueCity() : quest;
    }

    NPCStep talkToAlrena = new NPCStep("Alrena", new RSTile(2573, 3333, 0));

    RSTile startTile = new RSTile(2570, 3331, 0);

    RSArea edmondHouse = new RSArea(startTile, 10);
    RSArea ALL_WEST_ARDOUNGE = new RSArea(new RSTile(2556, 3266, 0), new RSTile(2507, 3334, 0));
    RSArea WEST_ARDOUGNE = new RSArea(new RSTile(2522, 3308, 0), new RSTile(2537, 3299, 0));
    RSArea IN_FRONT_OF_HOUSE = new RSArea(new RSTile(2530, 3328, 0), new RSTile(2533, 3325, 0));
    RSArea TEDS_HOUSE = new RSArea(new RSTile(2533, 3329, 0), new RSTile(2527, 3333, 0));
    RSArea TEDS_HOUSE_UPSTAIRS = new RSArea(new RSTile(2527, 3333, 1), new RSTile(2533, 3329, 1));
    RSArea PLAGUE_HOUSE_DOOR = new RSArea(new RSTile(2531, 3272, 0), new RSTile(2534, 3273, 0));
    RSArea CLERK_AREA = new RSArea(new RSTile(2529, 3312, 0), new RSTile(2522, 3319, 0));
    RSArea CLERK_STANDING_AREA = new RSArea(new RSTile(2529, 3315, 0), new RSTile(2522, 3319, 0));
    RSArea BRAVEK_ROOM = new RSArea(new RSTile(2539, 3312, 0), new RSTile(2530, 3316, 0));
    RSArea SEWER_AREA = new RSArea(new RSTile(2519, 9737, 0), new RSTile(2509, 9764, 0));
    RSArea PLAGUE_HOUSE_UPSTAIRS = new RSArea(new RSTile(2541, 3268, 0), new RSTile(2532, 3271, 0));


    RSNPC[] edmond = NPCs.findNearest("Edmond");

    RSItem[] invWater = Inventory.find(ItemID.BUCKET_OF_WATER);


    RSTile mudpatchTile = new RSTile(2566, 3332, 0);
    RSTile jethickTile = new RSTile(2537, 3304, 0);

    RSObject[] grill = Objects.findNearest(20, "Grill");
    RSObject[] pipe = Objects.findNearest(20, "Pipe");
    RSObject[] door = Objects.findNearest(20, "Door");
    RSObject[] stairs = Objects.findNearest(20, "Stairs");

    //Methods
    public void goToWestArdougne() {
        if (!SEWER_AREA.contains(Player.getPosition())) {
            PathingUtil.walkToTile(startTile);
            RSObject[] hole = Objects.findNearest(20, "Dug hole");
            if (hole.length > 0) {
                AccurateMouse.click(hole[0], "Climb-down");
                Timer.abc2WaitCondition(() -> SEWER_AREA.contains(Player.getPosition()), 10000, 12000);
            }
        }
        if (SEWER_AREA.contains(Player.getPosition())) {
            RSObject[] pipe = Objects.findNearest(40, "Pipe");

            if (pipe.length > 0) {
                if (Walking.blindWalkTo(pipe[0].getPosition()))
                    Timer.abc2WaitCondition(() -> pipe[0].getPosition().distanceTo(Player.getPosition()) < 2, 10000, 12000);

                if (AccurateMouse.click(pipe[0], "Climb-up"))
                    Timer.abc2WaitCondition(() -> WEST_ARDOUGNE.contains(Player.getPosition()), 10000, 12000);
            }
        }
    }

    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.DWELLBERRIES, 1, 500),
                    new GEItem(ItemID.ROPE, 1, 500),
                    new GEItem(ItemID.SPADE, 1, 500),
                    new GEItem(ItemID.BUCKET_OF_WATER, 4, 500),
                    new GEItem(ItemID.CHOCOLATE_DUST, 1, 500),
                    new GEItem(ItemID.BUCKET_OF_MILK, 1, 500),
                    new GEItem(ItemID.SNAPE_GRASS, 1, 50),
                    new GEItem(ItemID.COMBAT_BRACELET4, 1, 20),
                    new GEItem(ItemID.CAMELOT_TELEPORT, 2, 40),
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

    public void goToStart() {
        if (Inventory.find(ItemID.DWELLBERRIES).length > 0) {
            cQuesterV2.status = "Going to start";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToTile(startTile);
        }
    }

    public void getItems() {
        cQuesterV2.status = "Getting items";
        General.println("[Debug]: Withdrawing Quest items.");
        BankManager.open(true);
        Banking.depositAll();
        if (Inventory.getAll().length > 0) {
            Banking.depositAll();
        }
        BankManager.checkEquippedGlory();
        BankManager.withdraw(1, true, ItemID.DWELLBERRIES);
        BankManager.withdraw(1, true, ItemID.SPADE);
        BankManager.withdraw(1, true, ItemID.BUCKET_OF_MILK);
        BankManager.withdraw(4, true, ItemID.BUCKET_OF_WATER);
        BankManager.withdraw(4, true, ItemID.CAMELOT_TELEPORT);
        BankManager.withdraw(1, true, ItemID.SNAPE_GRASS);
        BankManager.withdraw(1, true, ItemID.ROPE);
        BankManager.withdraw(1, true, ItemID.CHOCOLATE_DUST);
        BankManager.withdraw(1, true, ItemID.STAMINA_POTION[0]);
        Banking.close();
    }

    public void startQuest() {
        cQuesterV2.status = "Talking to Edmond";
        General.println("[Debug]: " + cQuesterV2.status);
        NpcChat.talkToNPC("Edmond");
        NPCInteraction.waitForConversationWindow();
        NPCInteraction.handleConversation("What's happened to her?");
        NPCInteraction.handleConversation("Yes.");
        NPCInteraction.handleConversation();
    }

    public void talkToAlrenaStep() {
        if (Inventory.find(ItemID.DWELLBERRIES).length > 0) {
            cQuesterV2.status = "Talking to Alrena";
            General.println("[Debug]: " + cQuesterV2.status);
            talkToAlrena.execute();
        }
    }


    public void getPicture() {
        GroundItemStep grabPictureOfElena = new GroundItemStep(ItemID.PICTURE,
                new RSTile(2576, 3334, 0));
        if (Inventory.find(ItemID.PICTURE).length < 1) {
            cQuesterV2.status = "Getting picture";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToTile(new RSTile(2576, 3334, 0));
            grabPictureOfElena.execute();
        }
    }

    public void step4() {
        if (Inventory.find(ItemID.PICTURE).length > 0) {
            cQuesterV2.status = "Talking to Edmond";
            General.println("[Debug]: " + cQuesterV2.status);

            if (NpcChat.talkToNPC("Edmond")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
        }
    }

    public void step5() {
        cQuesterV2.status = "Wetting mud patch";
        if (PathingUtil.walkToTile(mudpatchTile))
            PathingUtil.movementIdle();
        invWater = Inventory.find(ItemID.BUCKET_OF_WATER);
        RSObject[] mudPatch = Objects.findNearest(10, "Mud patch");
        if (mudPatch.length > 0) {
            for (RSItem i : invWater) {
                if (Utils.useItemOnObject(ItemID.BUCKET_OF_WATER, mudPatch[0].getID())) ;
                     Waiting.waitNormal(900, 100);
            }
        }
        if (NPCInteraction.isConversationWindowUp())
            NPCInteraction.handleConversation();
    }

    public void step6() {
        cQuesterV2.status = "Digging";
        RSItem[] invItem1 = Inventory.find(ItemID.SPADE);
        if (invItem1.length > 0 && invItem1[0].click("Dig")) {
            General.sleep(2000, 4000);
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }
    }

    public void step7() {
        RSItem[] invItem1 = Inventory.find(ItemID.ROPE);
        if (invItem1.length > 0) {
            cQuesterV2.status = "Talking to Edmond";
            if (NpcChat.talkToNPC("Edmond")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }

            Walking.blindWalkTo(new RSTile(2514, 9740, 0));
            General.sleep(6000, 9000);
            if (Utils.clickObj("Grill", "Open")) {
                General.sleep(General.random(3000, 5000));
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
            if (Utils.useItemOnObject(ItemID.ROPE, "Grill")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
        }
        if (Inventory.find(ItemID.ROPE).length < 1) {
            Walking.blindWalkTo(new RSTile(2515, 9755, 0));
            General.sleep(General.random(3000, 5000));
            edmond = NPCs.findNearest("Edmond");
            if (edmond.length > 0) {
                if (!edmond[0].isClickable())
                    edmond[0].adjustCameraTo();

                General.println("[Debug]: Talking to edmond");
                AccurateMouse.click(edmond[0], "Talk-to");
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                General.sleep(General.random(3000, 5000));
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                General.sleep(General.random(8000, 15000));
            }
        }
    }

    public void step8() {
        if (!WEST_ARDOUGNE.contains(Player.getPosition())) {
            cQuesterV2.status = "Exiting sewer";
            RSItem[] invItem1 = Inventory.find(ItemID.GAS_MASK);
            if (invItem1.length > 0 && invItem1[0].click("Wear")) {
                General.sleep(750, 2000);
            }
            RSObject[] pipe = Objects.findNearest(20, "Pipe");
            if (pipe.length > 0) {
                if (!pipe[0].isClickable())
                    pipe[0].adjustCameraTo();

                AccurateMouse.click(pipe[0], "Climb-up");
                Timer.abc2WaitCondition(() -> WEST_ARDOUGNE.contains(Player.getPosition()), 8000, 12000);
            }
        }
        cQuesterV2.status = "Talking to Jethick";
        PathingUtil.walkToTile(jethickTile);
        General.sleep(General.random(2000, 5000));
        if (NpcChat.talkToNPC("Jethick")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Yes, I'll return it for you.");
            NPCInteraction.handleConversation();
            General.sleep(General.random(1000, 3000));
        }
    }

    public void step9() {
        cQuesterV2.status = "Going to north house";
        PathingUtil.walkToArea(IN_FRONT_OF_HOUSE);
        RSObject[] door = Objects.findNearest(20, "Door");
        if (door.length > 0) {
            AccurateMouse.click(door[0], "Open");
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            NPCInteraction.handleConversation();
        }
    }

    public void step10() {
        if (TEDS_HOUSE.contains(Player.getPosition())) {
            cQuesterV2.status = "Talking to Ted";
            NpcChat.talkToNPC("Ted Rehnison");
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            NPCInteraction.handleConversation();
        }
    }

    public void step11() {
        cQuesterV2.status = "Talking to daughter";
        if (Utils.clickObj("Stairs", "Walk-up"))
            Timer.abc2WaitCondition(() -> TEDS_HOUSE_UPSTAIRS.contains(Player.getPosition()), 8000, 12000);

        if (TEDS_HOUSE_UPSTAIRS.contains(Player.getPosition())) {
            if (NpcChat.talkToNPC("Milli Rehnison")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                NPCInteraction.handleConversation();
                General.sleep(3000, 5000);
            }

        }
    }


    public void step12() {
        cQuesterV2.status = "Going to plague house";
        if (PathingUtil.walkToTile(new RSTile(2533, 3273, 0)))
            Timer.waitCondition(() -> PLAGUE_HOUSE_DOOR.contains(Player.getPosition()), 9000, 15000);

        if (Utils.clickObj(37321, "Open")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("But I think a kidnap victim is in here.");
            NPCInteraction.handleConversation("I want to check anyway.");
            NPCInteraction.handleConversation();
        }
    }


    int CLERK_4255 = 4255;

    public void step13() {
        cQuesterV2.status = "Talking to Clerk";
        PathingUtil.walkToArea(CLERK_STANDING_AREA);
        if (NpcChat.talkToNPC(CLERK_4255)) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Who is through that door?" );
            NPCInteraction.handleConversation("This is urgent though! Someone's been kidnapped!");
            NPCInteraction.handleConversation();
        }

        cQuesterV2.status = "Talking to Bravek";
        if (PathingUtil.walkToTile(new RSTile(2529, 3314, 0)))
            PathingUtil.movementIdle();

        if (Utils.clickObj(2528, "Open")) {
            Timer.waitCondition(() -> BRAVEK_ROOM.contains(Player.getPosition()), 7000, 9000);
        }
    }

    public void step14() {
        if (!BRAVEK_ROOM.contains(Player.getPosition())) {
            if (!ALL_WEST_ARDOUNGE.contains(Player.getPosition())) {
                goToWestArdougne();
            }
            PathingUtil.walkToArea(CLERK_STANDING_AREA);
            if (Utils.clickObj(2528, "Open")) {
                Timer.waitCondition(() -> BRAVEK_ROOM.contains(Player.getPosition()), 7000, 9000);
            }
        }
        if (NpcChat.talkToNPC("Bravek")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("This is really important though!");
            NPCInteraction.handleConversation("Do you know what's in the cure?");
            NPCInteraction.handleConversation();
        }
    }

    public void step15() {
        if (!ALL_WEST_ARDOUNGE.contains(Player.getPosition()))
            goToWestArdougne();

        cQuesterV2.status = "Making cure";
        if (Inventory.find(ItemID.HANGOVER_CURE).length < 1) {

            if (Utils.useItemOnItem(ItemID.CHOCOLATE_DUST, ItemID.BUCKET_OF_MILK)) {
                Timer.waitCondition(() -> Inventory.find(1977).length > 0, 5000, 7000);
                Utils.shortSleep();
            }

            if (Utils.useItemOnItem(ItemID.SNAPE_GRASS, 1977)) {
                Timer.waitCondition(() -> Inventory.find(ItemID.HANGOVER_CURE).length > 0, 5000, 7000);
            }
        }

        cQuesterV2.status = "Talking to Bravek";
        if (Utils.useItemOnNPC(ItemID.HANGOVER_CURE, "Bravek")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("They won't listen to me!");
            NPCInteraction.handleConversation();
        }

    }


    public void step16() {
        if (!ALL_WEST_ARDOUNGE.contains(Player.getPosition())) {
            goToWestArdougne();
        }
        cQuesterV2.status = "Step 16 - Going to plague house";
        if (BRAVEK_ROOM.contains(Player.getPosition())) {
            door = Objects.findNearest(20, "Door");
            if (door.length > 0) {
                if (!door[0].isClickable())
                    door[0].adjustCameraTo();

                if (AccurateMouse.click(door[0], "Open"))
                    Timer.waitCondition(() -> !BRAVEK_ROOM.contains(Player.getPosition()), 10000, 12000);
                General.sleep(General.random(800, 3000));
            }
        }
        PathingUtil.walkToTile(new RSTile(2532, 3272, 0));
        General.sleep(General.random(5000, 8000));
        door = Objects.findNearest(20, 37321);
        if (door.length > 0) {
            if (!door[0].isClickable())
                door[0].adjustCameraTo();
            if (AccurateMouse.click(door[0], "Open")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                Utils.idle(3000, 5000);
                NPCInteraction.handleConversation();
                Timer.abc2WaitCondition(() -> PLAGUE_HOUSE_UPSTAIRS.contains(Player.getPosition()), 7000, 10000);
            }
        }
    }

    public void step17() {
        if (!ALL_WEST_ARDOUNGE.contains(Player.getPosition())) {
            goToWestArdougne();
        }
        cQuesterV2.status = "Step 17 - Getting Key";
        if (Inventory.find(1507).length < 1) {
            Walking.blindWalkTo(new RSTile(2535, 3268, 0));
            General.sleep(General.random(3000, 5000));
            RSObject[] barrel = Objects.findNearest(5, "Barrel");
            if (barrel.length > 0 && barrel[0].click("Search")) {
                Timer.waitCondition(() -> Inventory.find(1507).length > 0, 5000, 9000);
            }
        }

        cQuesterV2.status = "Step 18 - Freeing Elena";
        RSObject[] stairs = Objects.findNearest(10, "Spooky stairs");
        if (stairs.length > 0) {
            AccurateMouse.click(stairs[0], "Walk-down");
            General.sleep(General.random(3000, 5000));
            Timer.waitCondition(() -> Objects.findNearest(20, 2526).length > 0, 5000, 9000);
        }

        if (Utils.clickObj(2526, "Open")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            NPCInteraction.handleConversation();
        }

        if (NpcChat.talkToNPC("Elena")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            General.sleep(3000, 5000);
        }
    }

    RSArea INSIDE_HOUSE = new RSArea(new RSTile(2541, 3268, 0), new RSTile(2532, 3271, 0));

    public void step18() {
        door = Objects.findNearest(20, "Door");
        if (door.length > 0) {
            AccurateMouse.click(door[0], "Open");
            General.sleep(3000, 5000);
        }
        RSObject[] stairs = Objects.findNearest(10, "Spooky stairs");
        if (stairs.length > 0) {
            AccurateMouse.click(stairs[0], "Walk-up");
            Timer.waitCondition(() -> PLAGUE_HOUSE_UPSTAIRS.contains(Player.getPosition()), 9000, 12000);
        }

        if (PLAGUE_HOUSE_UPSTAIRS.contains(Player.getPosition())) {
            door = Objects.findNearest(20, 37321);
            if (door.length > 0) {

                if (!door[0].isClickable())
                    door[0].adjustCameraTo();

                if (AccurateMouse.click(door[0], "Open"))
                    General.sleep(General.random(3000, 5000));
            }
        }
    }

    public void step19() {
        PathingUtil.walkToTile(startTile);
        edmond = NPCs.findNearest("Edmond");
        if (edmond.length > 0) {

            if (!edmond[0].isClickable())
                edmond[0].adjustCameraTo();

            General.println("[Debug]: Talking to edmond");
            if (NpcChat.talkToNPC("Edmond")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
        }
    }

    @Override
    public void execute() {
        Log.log(Game.getSetting(165));
        if (Game.getSetting(165) == 0) {
            buyItems();
            getItems();
            goToStart();
            startQuest();
        }
        if (Game.getSetting(165) == 1) {
            talkToAlrenaStep();
        }
        if (Game.getSetting(165) == 2) {
            getPicture();
            step4();
        }
        if (Game.getSetting(165) == 3 ||
                Game.getSetting(165) == 4 || Game.getSetting(165) == 5
                || Game.getSetting(165) == 6) {
            step5();
        }
        if (Game.getSetting(165) == 7) {
            step6();
        }
        if (Game.getSetting(165) == 8 || Game.getSetting(165) == 9) {
            step7();
        }
        if (Game.getSetting(165) == 10) {
            step8();
        }
        if (Game.getSetting(165) == 20) {
            step9();
        }
        if (Game.getSetting(165) == 21) {
            step10();
        }
        if (Game.getSetting(165) == 22) {
            step11();
        }
        if (Game.getSetting(165) == 23) {
            step12();
        }
        if (Game.getSetting(165) == 24) {
            step13();
        }
        if (Game.getSetting(165) == 25) {
            step14();
        }
        if (Game.getSetting(165) == 26) {
            step15();
        }
        if (Game.getSetting(165) == 27) {
            step16();
            step17();
        }
        if (Game.getSetting(165) == 28) {
            step18();
            step19();

        }
        if (Game.getSetting(165) >= 29) {
            if (Utils.closeQuestCompletionWindow()) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
            RSItem[] invItem1 = Inventory.find(1505);
            if (invItem1.length > 0 && invItem1[0].click("Read")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
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
        return "Plague City ( "+ Game.getSetting(165) +")";
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
