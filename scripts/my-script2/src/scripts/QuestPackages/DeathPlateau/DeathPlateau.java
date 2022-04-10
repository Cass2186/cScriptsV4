package scripts.QuestPackages.DeathPlateau;

import dax.walker.utils.AccurateMouse;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.types.*;
import org.tribot.script.sdk.ChatScreen;
import org.tribot.script.sdk.Quest;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.Listeners.ChatObserver;
import scripts.Listeners.InterfaceObserver;
import scripts.Listeners.Model.ChatListener;
import scripts.QuestPackages.GertrudesCat.GertrudesCat;
import scripts.QuestSteps.BuyItemsStep;
import scripts.QuestSteps.NPCStep;
import scripts.QuestSteps.QuestStep;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.*;
import scripts.Requirements.Util.Conditions;
import scripts.Requirements.Util.LogicType;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DeathPlateau implements QuestTask, ChatListener {

    private static DeathPlateau quest;

    public static DeathPlateau get() {
        return quest == null ? quest = new DeathPlateau() : quest;
    }

    int IOU = 3103;
    int COMBINATION = 3102;
    int YELLOW_BALL = 3111;
    int GREEN_BALL = 3113;
    int PURPLE_BALL = 3112;
    int BLUE_BALL = 3110;
    int RED_BALL = 3109;
    int BREAD = 2309;
    int COOKED_TROUT = 333;
    int IRON_BAR = 2351;
    int ASGARNIAN_ALE = 1905;
    int BLURBERRY_SPECIAL = 2064;
    int CLIMBING_BOOTS = 3105;
    int CERTIFICATE = 3114;
    int SPIKED_BOOTS = 3107;
    int MAP = 3104;
    String bet = "100";
    RSArea START_AREA = new RSArea(new RSTile(2899, 3527, 0), new RSTile(2893, 3529, 0));
    RSArea EOHRIC_AREA = new RSArea(new RSTile(2904, 3558, 1), new RSTile(2893, 3569, 1));
    RSArea BAR_UPPER_FLOOR = new RSArea(new RSTile(2915, 3536, 1), new RSTile(2905, 3544, 1));
    RSArea BAR_STAIRS_LOWER_FLOOR = new RSArea(new RSTile(2912, 3538, 0), new RSTile(2915, 3536, 0));
    RSArea PUZZLE_AREA = new RSArea(new RSTile(2893, 3561, 0), new RSTile(2897, 3566, 0));
    RSArea EQUIPMENT_ROOM = new RSArea(new RSTile(2895, 3567, 0), new RSTile(2891, 3571, 0));
    RSArea ARCHER_AREA = new RSArea(new RSTile(2895, 3567, 1), new RSTile(2891, 3571, 1));
    RSArea CAVE_OUTSIDE = new RSArea(new RSTile(2856, 3578, 0), new RSTile(2859, 3574, 0));
    RSArea INSIDE_CAVE = new RSArea(new RSTile(2264, 4763, 0), new RSTile(2274, 4751, 0));
    RSArea HOUSE = new RSArea(new RSTile(2822, 3553, 0), new RSTile(2818, 3557, 0));
    RSArea DUSTAIN_HOUSE = new RSArea(new RSTile(2923, 3572, 0), new RSTile(2917, 3577, 0));
    RSArea DUSTAIN_ROOM = new RSArea(new RSTile(2921, 3575, 0), new RSTile(2922, 3576, 0));
    RSArea END_OF_PATH = new RSArea(new RSTile(2875, 3606, 0), new RSTile(2870, 3608, 0));


    RSTile YELLOW_BALL_TILE = new RSTile(2896, 3562, 0);
    RSTile GREEN_BALL_TILE = new RSTile(2896, 3564, 0);
    RSTile PURPLE_BALL_TILE = new RSTile(2896, 3563, 0);
    RSTile BLUE_BALL_TILE = new RSTile(2894, 3561, 0);
    RSTile RED_BALL_TILE = new RSTile(2893, 3563, 0);

    NPCStep talkToDenulth1, goToEohric1, talkToEohric1, goToHaroldStairs1, goToHaroldDoor1, talkToHarold1,
            goToEohric2, talkToEohric2, takeAsgarnianAle, goToHaroldStairs2, goToHaroldDoor2, talkToHarold2,
            giveHaroldBlurberry, gambleWithHarold, readIou, placeRedStone, placeBlueStone, placeYellowStone,
            placePinkStone, placeGreenStone, placeStones, enterSabaCave, talkToSaba, leaveSabaCave,
            talkToTenzing1, talkToDunstan1, talkToDenulthForDunstan, talkToDunstan2, talkToTenzing2,
            goNorth, talkToDenulth3, goToHaroldStairs3, goToHaroldDoor3, talkToHarold3;

    //RSArea
    //s
    //RSArea
    //castleDownstairs, castleUpstairs, barDownstairs, barUpstairs, haroldsRoom1, haroldsRoom2, sabaCave;

    ItemReq asgarnianAle = new ItemReq("Asgarnian ale", ItemID.ASGARNIAN_ALE);
    ItemReq premadeBlurb = new ItemReq("Premade blurb' sp.", ItemID.BLURBERRY_SPECIAL);

    ItemReq coins = new ItemReq("Coins", ItemID.COINS_995, 60);
    ItemReq bread = new ItemReq("Bread (UNNOTED)", ItemID.BREAD, 10);
    ItemReq trout = new ItemReq("Trout (UNNOTED)", ItemID.TROUT, 10);
    ItemReq ironBar = new ItemReq("Iron bar", ItemID.IRON_BAR);
    ItemReq iou = new ItemReq("IOU", IOU);
    ItemReq iouHighlight = new ItemReq("IOU", IOU);
    ItemReq redStone = new ItemReq("Stone ball", RED_BALL);
    ItemReq blueStone = new ItemReq("Stone ball", BLUE_BALL);
    ItemReq yellowStone = new ItemReq("Stone ball", YELLOW_BALL);
    ItemReq pinkStone = new ItemReq("Stone ball", PURPLE_BALL);
    ItemReq greenStone = new ItemReq("Stone ball", GREEN_BALL);

    ItemReq certificate = new ItemReq("Certificate", 3114);
    ItemReq climbingBoots = new ItemReq("Climbing boots", ItemID.CLIMBING_BOOTS);
    ItemReq spikedBoots = new ItemReq("Spiked boots", ItemID.SPIKED_BOOTS);
    ItemReq secretMap = new ItemReq("Secret way map", 3104);
    ItemReq combination = new ItemReq("Combination", COMBINATION);
    ItemReq gamesNecklace = new ItemReq(ItemID.GAMES_NECKLACE[0]);

    String combinationString = " Red is North of Blue.\n" +
            "Yellow is South of Purple.\n" +
            "Green is North of Purple.\n" +
            "Blue is West of Yellow.\n" +
            "Purple is East of Red.";

    InventoryRequirement startInventory = new InventoryRequirement(new ArrayList<>(
            Arrays.asList(
                    new ItemReq(ItemID.COINS, 2000, 100),
                    new ItemReq(ItemID.BREAD, 10),
                    new ItemReq(ItemID.TROUT, 10),
                    new ItemReq(ItemID.IRON_BAR, 1),
                    new ItemReq(ItemID.BLURBERRY_SPECIAL, 1),
                    new ItemReq(ItemID.ASGARNIAN_ALE, 1),
                    new ItemReq(ItemID.GAMES_NECKLACE[0], 1, 0),
                    new ItemReq(ItemID.AMULET_OF_GLORY[2], 1, 0),
                    new ItemReq(ItemID.STAMINA_POTION[0], 1, 0)
            ))
    );

    public ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.BREAD, 11, 300),
                    new GEItem(ItemID.IRON_BAR, 1, 100),
                    new GEItem(ItemID.BLURBERRY_SPECIAL, 1, 100),
                    new GEItem(ItemID.ASGARNIAN_ALE, 2, 200),
                    new GEItem(ItemID.GAMES_NECKLACE[0], 2, 25),
                    new GEItem(ItemID.RING_OF_DUELING[0], 2, 25),
                    new GEItem(ItemID.STAMINA_POTION[0], 3, 10),
                    new GEItem(ItemID.AMULET_OF_GLORY[2], 1, 10),
                    new GEItem(ItemID.COMBAT_BRACELET[0], 1, 15),
                    new GEItem(ItemID.TROUT, 11, 200)
            )
    );

    BuyItemsStep buyInitial = new BuyItemsStep(itemsToBuy);


    public void getItems() {
        if (!startInventory.check()) {
            cQuesterV2.status = "Getting Items";
            General.println("[Debug]: " + cQuesterV2.status);
            BankManager.open(true);
            BankManager.checkEquippedGlory();
            BankManager.checkCombatBracelet();
            startInventory.withdrawItems();
            BankManager.close(true);
        }
    }


    RSArea castleDownstairs = new RSArea(new RSTile(2893, 3558, 0), new RSTile(2904, 3569, 0));
    RSArea castleUpstairs = new RSArea(new RSTile(2891, 3556, 1), new RSTile(2906, 3571, 1));
    RSArea barDownstairs = new RSArea(new RSTile(2905, 3536, 0), new RSTile(2915, 3543, 0));
    RSArea barUpstairs = new RSArea(new RSTile(2905, 3536, 1), new RSTile(2915, 3544, 1));
    RSArea haroldsRoom1 = new RSArea(new RSTile(2905, 3536, 1), new RSTile(2906, 3542, 1));
    RSArea haroldsRoom2 = new RSArea(new RSTile(2907, 3542, 1), new RSTile(2907, 3542, 1));
    RSArea sabaCave = new RSArea(new RSTile(2266, 4752, 0), new RSTile(2273, 4762, 0));


    AreaRequirement inCastleDownstairs = new AreaRequirement(castleDownstairs);
    AreaRequirement inCastleUpstairs = new AreaRequirement(castleUpstairs);
    AreaRequirement inBarDownstairs = new AreaRequirement(barDownstairs);
    AreaRequirement inBarUpstairs = new AreaRequirement(barUpstairs);
    AreaRequirement inHaroldsRoom = new AreaRequirement(haroldsRoom1, haroldsRoom2);
    //  givenHaroldBlurberry =new ChatMessageRequirement("You give Harold a Blurberry Special.");

    ItemOnTileRequirement isRedStoneDone = new ItemOnTileRequirement(redStone, new RSTile(2894, 3563, 0));
    ItemOnTileRequirement isBlueStoneDone = new ItemOnTileRequirement(blueStone, new RSTile(2894, 3562, 0));
    ItemOnTileRequirement isYellowStoneDone = new ItemOnTileRequirement(yellowStone, new RSTile(2895, 3562, 0));
    ItemOnTileRequirement isPinkStoneDone = new ItemOnTileRequirement(pinkStone, new RSTile(2895, 3563, 0));
    ItemOnTileRequirement isGreenStoneDone = new ItemOnTileRequirement(greenStone, new RSTile(2895, 3564, 0));
    AreaRequirement inSabaCave = new AreaRequirement(sabaCave);
    //ChatMessageRequirement isFarEnough = new ChatMessageRequirement("You should go and speak to Denulth.");
    Conditions talkedToSaba = new Conditions(true, LogicType.OR,
            new WidgetTextRequirement(WidgetInfo.DIALOG_NPC_TEXT, "Before the trolls came there used to be a nettlesome"),
            new WidgetTextRequirement(WidgetInfo.DIALOG_NPC_TEXT, "Have you got rid of those pesky trolls yet?")
    );
    Conditions talkedToDunstan = new Conditions(true, LogicType.OR,
            new WidgetTextRequirement(WidgetInfo.DIALOG_NPC_TEXT, "My son has just turned 16"),
            new WidgetTextRequirement(WidgetInfo.DIALOG_NPC_TEXT, "Have you managed to get my son")
    );


    public void initialiseListeners() {
        ChatObserver chatListener = new ChatObserver(() -> true);
        chatListener.addListener(this);
        chatListener.addString("I have duplicated a key, I need to get it from");
        chatListener.addString("the key from Leela.");
        chatListener.addString("pickup the key");
        chatListener.addString("I got a duplicated cell door key");
        chatListener.start();
    }

    public void buyItems() {
        cQuesterV2.status = "Buying Items";
        General.println("[Debug]: " + cQuesterV2.status);
        buyInitial.buyItems();
    }

    public void startQuest() {
        if (startInventory.check()) {
            talkToDenulth1 = new NPCStep("Denulth", new RSTile(2896, 3529, 0),
                    new String[]{"Do you have any quests for me?", "No but perhaps I could try and find one?",
                            "Yes."});
            talkToDenulth1.execute();
        }
    }

    public void goToHarold() {
        if (!BAR_UPPER_FLOOR.contains(Player.getPosition())) {
            cQuesterV2.status = "Going to Harold";
            PathingUtil.walkToArea(BAR_STAIRS_LOWER_FLOOR, false);

            if (Utils.clickObj("Staircase", "Climb-up", false)) {
                Timer.waitCondition(() -> BAR_UPPER_FLOOR.contains(Player.getPosition()), 8000, 12000);
            }
        }
        if (BAR_UPPER_FLOOR.contains(Player.getPosition()) && !inHaroldsRoom.check()) {
            if (PathingUtil.localNavigation(new RSTile(2906, 3543, 1)))
                PathingUtil.movementIdle();

            if (Utils.clickObj(3747, "Open", false)) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                Timer.waitCondition(() -> inHaroldsRoom.check(), 2500, 3000);
            }

        }
    }

    public void talkToEohric() {
        cQuesterV2.status = "Step 2 - Going to Eohric";
        talkToEohric1 = new NPCStep("Eohric", new RSTile(2900, 3566, 1),
                new String[]{"I'm looking for the guard that was on last night."});
        talkToEohric1.execute();
    }

    public void talkToHarold() {
        cQuesterV2.status = "Step 3 - Going to Harold";
        goToHarold();
        if (BAR_UPPER_FLOOR.contains(Player.getPosition())) {
            if (NpcChat.talkToNPC("Harold")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("You're the guard that was on duty last night?");
                NPCInteraction.handleConversation();
            }
            if (Utils.clickObj(3747, "Open", false)) {
                General.sleep(4000, 6000);
            }

            if (Utils.clickObj("Staircase", "Climb-down", false)) {
                Timer.abc2WaitCondition(() -> !BAR_UPPER_FLOOR.contains(Player.getPosition()), 8000, 12000);
            }
        }
    }

    public void gamble() {
        cQuesterV2.status = "Step 5 - Gambling";
        General.println("[Debug]: " + cQuesterV2.status);
        while (Inventory.find(IOU).length < 1) {
            General.sleep(100);
            if (BAR_UPPER_FLOOR.contains(Player.getPosition())) {
                if (NpcChat.talkToNPC("Harold")) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation("Would you like to gamble?");
                    NPCInteraction.handleConversation();
                    Timer.waitCondition(() -> Interfaces.isInterfaceSubstantiated(162, 41), 3500, 5000);
                }
                RSInterface rsInterface = Interfaces.get(162, 41);
                if (rsInterface != null && !rsInterface.isHidden()) {

                    Utils.idle(2000, 4000);
                    Keyboard.typeString(bet);

                    Utils.idle(100, 300);
                    Keyboard.pressEnter();
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();

                    Utils.idle(5000, 8000);
                    Timer.waitCondition(() -> Interfaces.get(99, 28) != null
                            && Interfaces.get(99, 28).getText().contains("Roll Dice"), 25000, 35000);
                }
                RSInterface diceInterface = Interfaces.get(99, 28);
                if (diceInterface != null && diceInterface.getText().contains("Roll Dice")) {
                    if (diceInterface.click()) {
                        Utils.idle(5000, 8000);
                        Timer.waitCondition(() -> Interfaces.get(99, 28) != null, 25000, 35000);
                    }
                }
                diceInterface = Interfaces.get(99, 28);
                if (diceInterface != null && diceInterface.click()) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                }
            }

        }
    }

    public void readIOU() {
        RSItem[] iouInv = Inventory.find(IOU);
        if (iouInv.length > 0 && iouInv[0].click("Read")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }
    }

    public void leaveUpStairs() {
        if (BAR_UPPER_FLOOR.contains(Player.getPosition())) {
            RSObject[] door = Objects.findNearest(10, 3747);
            if (inHaroldsRoom.check() &&
                    door.length > 0 && door[0].click("Open")) {
                Timer.waitCondition(() -> !inHaroldsRoom.check(), 3500, 5000);
            }
            RSObject[] stairs = Objects.findNearest(30, "Staircase");
            if (stairs.length > 0 && Utils.clickObj("Staircase", "Climb-down"))
                Timer.abc2WaitCondition(() -> !BAR_UPPER_FLOOR.contains(Player.getPosition()), 8000, 10000);
        }

    }

    public void getAndPlaceBall(int ballID, int placeID, RSTile tile) {
        RSGroundItem[] groundBall = GroundItems.find(ballID);
        if (!Inventory.isFull() && Utils.clickGroundItem(ballID))
            Timer.slowWaitCondition(() -> Inventory.find(ballID).length > 0, 8000, 12000);

        RSItem[] invBall = Inventory.find(ballID);
        if (invBall.length > 0) {
            if (!tile.isClickable())
                tile.adjustCameraTo();

            if (DynamicClicking.clickRSTile(tile, "Walk here"))
                PathingUtil.movementIdle();

            if (Utils.useItemOnObject(ballID, placeID)) {
                Timer.slowWaitCondition(() -> Inventory.find(ballID).length == 0, 5000, 7000);
            }
        }
    }

    public void giveHaroldDrink() {
        cQuesterV2.status = "Step 4 - Going to Harold";
        General.println("[Debug]: " + cQuesterV2.status);
        goToHarold();
        if (BAR_UPPER_FLOOR.contains(Player.getPosition()) && inHaroldsRoom.check()) {
            NpcChat.talkToNPC("Harold");
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();

            Utils.idle(1500, 3000);
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Can I buy you a drink?");
            NPCInteraction.handleConversation();

            Utils.idle(2500, 4000);
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();

        }
    }

    public void goToPuzzle() {
        PathingUtil.walkToArea(PUZZLE_AREA);
        if (PUZZLE_AREA.contains(Player.getPosition())) {
            if (!isYellowStoneDone.check())
                getAndPlaceBall(YELLOW_BALL, 3676, YELLOW_BALL_TILE);
            if (!isGreenStoneDone.check())
                getAndPlaceBall(GREEN_BALL, 3676, GREEN_BALL_TILE);
            if (!isPinkStoneDone.check())
                getAndPlaceBall(PURPLE_BALL, 3677, PURPLE_BALL_TILE);
            if (!isRedStoneDone.check())
                getAndPlaceBall(RED_BALL, 3677, RED_BALL_TILE);
            if (!isBlueStoneDone.check())
                getAndPlaceBall(BLUE_BALL, 3676, BLUE_BALL_TILE);
        }
    }

    public void goIntoEquipmentRoom() {
        cQuesterV2.status = "Talking to Archer";
        if (!EQUIPMENT_ROOM.contains(Player.getPosition()) && !ARCHER_AREA.contains(Player.getPosition())) {
            Walking.blindWalkTo(new RSTile(2894, 3566, 0));
            Utils.idle(3000, 5000);

            if (Utils.clickObj(3743, "Open")) {
                Timer.waitCondition(() -> EQUIPMENT_ROOM.contains(Player.getPosition()), 8000, 10000);
                Utils.idle(500, 1500);
            }
        }

        if (EQUIPMENT_ROOM.contains(Player.getPosition()))
            if (Utils.clickObj("Ladder", "Climb-up"))
                Timer.abc2WaitCondition(() -> !EQUIPMENT_ROOM.contains(Player.getPosition()), 8000, 12000);

        if (ARCHER_AREA.contains(Player.getPosition())) {
            if (NpcChat.talkToNPC("Archer")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
            if (Utils.clickObj("Ladder", "Climb-down"))
                Timer.abc2WaitCondition(() -> EQUIPMENT_ROOM.contains(Player.getPosition()), 8000, 12000);

            RSObject[] door = Objects.findNearest(20, 3743);
            if (door.length > 0) {
                if (AccurateMouse.click(door[0], "Open"))
                    Timer.waitCondition(() -> !EQUIPMENT_ROOM.contains(Player.getPosition()), 8000, 10000);
            }
        }
    }

    public void goToCave() {
        if (Inventory.find(CLIMBING_BOOTS).length < 1) {

            if (!CAVE_OUTSIDE.contains(Player.getPosition()) && !INSIDE_CAVE.contains(Player.getPosition()))
                PathingUtil.walkToArea(CAVE_OUTSIDE);

            if (!INSIDE_CAVE.contains(Player.getPosition())) {
                RSObject[] cave = Objects.findNearest(20, 3735);
                if (cave.length > 0 && AccurateMouse.click(cave[0], "Enter")) {
                    Timer.slowWaitCondition(() -> !CAVE_OUTSIDE.contains(Player.getPosition()), 8000, 12000);
                }
            }

            if (INSIDE_CAVE.contains(Player.getPosition())) {
                NpcChat.talkToNPC("Saba");
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Do you know of another way up Death Plateau?");
                NPCInteraction.handleConversation();
                NPCInteraction.handleConversation();

                if (Utils.clickObj(3736, "Exit"))
                    Timer.abc2WaitCondition(() -> CAVE_OUTSIDE.contains(Player.getPosition()), 8000, 12000);

            }
        }
    }

    RSTile TENZING_HOUSE_TILE = new RSTile(2821, 3555, 0);

    public void goToHouse() {
        if (Inventory.find(MAP).length < 1) {
            Inventory.drop(BLUE_BALL, PURPLE_BALL, YELLOW_BALL, RED_BALL, GREEN_BALL);
            cQuesterV2.status = "Going to Tenzing";
            General.println("[Debug]: " + cQuesterV2.status);
            if (Inventory.find(CLIMBING_BOOTS).length < 1) {
                PathingUtil.walkToTile(TENZING_HOUSE_TILE);
                if (NpcChat.talkToNPC("Tenzing")) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation("OK, I'll get those for you.");
                    NPCInteraction.handleConversation();
                }
            }
        }
    }

    public void getSpikedBoots() {
        cQuesterV2.status = "Going to Dunstan";
        General.println("[Debug]: " + cQuesterV2.status);
        if (Inventory.find(CLIMBING_BOOTS).length > 0) {
            PathingUtil.walkToArea(DUSTAIN_HOUSE);

            if (DUSTAIN_HOUSE.contains(Player.getPosition())) {
                if (NpcChat.talkToNPC("Dunstan")) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation("OK, I'll get those for you.");
                    NPCInteraction.handleConversation();
                }
            }
        }
    }

    public void goToDenulth() {
        cQuesterV2.status = "Going to Denulth";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(START_AREA);
        if (NpcChat.talkToNPC("Denulth")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Do you have any quests for me?");
            NPCInteraction.handleConversation("No but perhaps I could try and find one?");
            NPCInteraction.handleConversation();
        }
    }

    public void goToHouse2() {
        cQuesterV2.status = "Going to Tenzing";
        General.println("[Debug]: " + cQuesterV2.status);
        for (int i = 0; i < 3; i++) {
            if (Inventory.find(CLIMBING_BOOTS).length < 1) {
                PathingUtil.walkToTile(TENZING_HOUSE_TILE);
                if (NpcChat.talkToNPC("Tenzing")) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                    ChatScreen.handle();
                    Utils.continuingChat();
                }
                if (Inventory.find(MAP).length > 0) {
                    break;
                }
            }
        }
    }

    public void goToEndOfPath() {
        cQuesterV2.status = "Going to explore path";
        PathingUtil.walkToArea(END_OF_PATH);

    }

    public void goToFinish() {
        cQuesterV2.status = "Going to Denulth";
        General.println("[Debug]: " + cQuesterV2.status);
        if (!START_AREA.contains(Player.getPosition())) {
            PathingUtil.walkToArea(HOUSE);
            PathingUtil.walkToArea(START_AREA);
        }
        if (NpcChat.talkToNPC("Denulth")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Do you have any quests for me?");
            NPCInteraction.handleConversation("No but perhaps I could try and find one?");
            NPCInteraction.handleConversation();
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
    public void execute() {
        initialiseListeners();
        //setupItemReqs();
        cQuesterV2.gameSettingInt = 314;
        if (Game.getSetting(314) == 0) {
            buyItems();
            getItems();
            startQuest();
        }
        if (Game.getSetting(314) == 10)
            talkToEohric();

        else if (Game.getSetting(314) == 20) {
            talkToHarold();

        } else if (Game.getSetting(314) == 30) {
            talkToEohric();

        } else if (Game.getSetting(314) == 40) {
            giveHaroldDrink();

        } else if (Game.getSetting(314) == 50) {
            gamble();

        } else if (Game.getSetting(314) == 55) {
            readIOU();

        } else if (Game.getSetting(314) == 60) {
            leaveUpStairs();
            goToPuzzle();
        }
        if (Game.getSetting(314) == 70) {
            if (Inventory.find(ItemID.CERTIFICATE_3114).length < 1 &&
                    Inventory.find(ItemID.CLIMBING_BOOTS).length < 1 &&
                    Inventory.find(ItemID.SPIKED_BOOTS).length < 1 &&
                    Inventory.find(3104).length < 1) {
                goIntoEquipmentRoom();
                goToCave();
                goToHouse();
            }
            if (Inventory.find(ItemID.CERTIFICATE_3114).length < 1 &&
                    Inventory.find(ItemID.SPIKED_BOOTS).length < 1 &&
                    Inventory.find(3104).length < 1) {
                getSpikedBoots();
                goToDenulth();
            }
            if (Inventory.find(ItemID.CERTIFICATE_3114).length > 0 &&
                    Inventory.find(ItemID.SPIKED_BOOTS).length < 1
                    && Inventory.find(3104).length < 1) {
                getSpikedBoots();
            }
            if (Inventory.find(ItemID.SPIKED_BOOTS).length > 0) {
                goToHouse2();
            }
            if (Inventory.find(3104).length > 0) {
                goToEndOfPath();
                goToFinish();
            }
        }
        if (Game.getSetting(314) == 80) {
            Utils.closeQuestCompletionWindow();
            cQuesterV2.taskList.remove(this);
        }

    }


    @Override
    public String questName() {
        return "Death Plateau";
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

    @Override
    public boolean isComplete() {
        return Quest.DEATH_PLATEAU.getState().equals(Quest.State.COMPLETE);
    }

    @Override
    public void onAppear() {

    }
}
