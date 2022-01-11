package scripts.QuestPackages.DeathPlateau;

import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.Game;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.GertrudesCat.GertrudesCat;
import scripts.QuestSteps.BuyItemsStep;
import scripts.QuestSteps.NPCStep;
import scripts.QuestSteps.QuestStep;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.InventoryRequirement;
import scripts.Requirements.ItemReq;
import scripts.Requirements.Requirement;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;

import java.util.ArrayList;
import java.util.Arrays;

public class DeathPlateau implements QuestTask {

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

    ItemReq gamesNecklace, premadeBlurb, coins500;

    //Items Required
    ItemReq asgarnianAle, premadeBlurbOrCoins, coins, bread, trout, ironBar, iou, iouHighlight, redStone, blueStone,
            yellowStone, pinkStone, greenStone, certificate, climbingBoots, spikedBoots, secretMap, combination;

    Requirement inCastleDownstairs, inCastleUpstairs, inBarDownstairs, inBarUpstairs, inHaroldsRoom,
            givenHaroldBlurberry, isRedStoneDone, isBlueStoneDone, isYellowStoneDone, isPinkStoneDone,
            isGreenStoneDone, inSabaCave, isFarEnough, talkedToSaba, talkedToDunstan;

    NPCStep talkToDenulth1, goToEohric1, talkToEohric1, goToHaroldStairs1, goToHaroldDoor1, talkToHarold1,
            goToEohric2, talkToEohric2, takeAsgarnianAle, goToHaroldStairs2, goToHaroldDoor2, talkToHarold2,
            giveHaroldBlurberry, gambleWithHarold, readIou, placeRedStone, placeBlueStone, placeYellowStone,
            placePinkStone, placeGreenStone, placeStones, enterSabaCave, talkToSaba, leaveSabaCave,
            talkToTenzing1, talkToDunstan1, talkToDenulthForDunstan, talkToDunstan2, talkToTenzing2,
            goNorth, talkToDenulth3, goToHaroldStairs3, goToHaroldDoor3, talkToHarold3;

    //Zones
    //Zone castleDownstairs, castleUpstairs, barDownstairs, barUpstairs, haroldsRoom1, haroldsRoom2, sabaCave;

    String bet = "100";
    String combinationString = " Red is North of Blue.\n" +
            "Yellow is South of Purple.\n" +
            "Green is North of Purple.\n" +
            "Blue is West of Yellow.\n" +
            "Purple is East of Red.";

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

    InventoryRequirement startInventory = new InventoryRequirement(new ArrayList<>(
            Arrays.asList(
                    new ItemReq(ItemId.COINS, 2000, 100),
                    new ItemReq(ItemId.BREAD, 10),
                    new ItemReq(ItemId.COOKED_TROUT, 10),
                    new ItemReq(ItemId.IRON_BAR, 1),
                    new ItemReq(ItemId.BLURBERRY_SPECIAL, 1),
                    new ItemReq(ItemId.ASGARNIAN_ALE, 1),
                    new ItemReq(ItemId.GAMES_NECKLACE[0], 1, 0),
                    new ItemReq(ItemId.AMULET_OF_GLORY[2], 1, 0),
                    new ItemReq(ItemId.STAMINA_POTION[0], 1, 0)
            ))
    );

    public ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemId.BREAD, 11, 300),
                    new GEItem(ItemId.IRON_BAR, 1, 100),
                    new GEItem(ItemId.BLURBERRY_SPECIAL, 1, 100),
                    new GEItem(ItemId.ASGARNIAN_ALE, 2, 200),
                    new GEItem(ItemId.GAMES_NECKLACE[0], 2, 25),
                    new GEItem(ItemId.RING_OF_DUELING[0], 2, 25),
                    new GEItem(ItemId.STAMINA_POTION[0], 3, 10),
                    new GEItem(ItemId.AMULET_OF_GLORY[2], 1, 10),
                    new GEItem(ItemId.COMBAT_BRACELET[0], 1, 15),
                    new GEItem(ItemId.COOKED_TROUT, 11, 200)
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


    public void buyItems() {
        cQuesterV2.status = "Buying Items";
        General.println("[Debug]: " + cQuesterV2.status);
        buyInitial.buyItems();
    }

    public void setupItemReqs() {
        asgarnianAle = new ItemReq("Asgarnian ale", ItemId.ASGARNIAN_ALE);
        premadeBlurb = new ItemReq("Premade blurb' sp.", ItemId.BLURBERRY_SPECIAL);

        coins = new ItemReq("Coins", ItemId.COINS_995, 60);
        bread = new ItemReq("Bread (UNNOTED)", ItemId.BREAD, 10);
        trout = new ItemReq("Trout (UNNOTED)", ItemId.COOKED_TROUT, 10);
        ironBar = new ItemReq("Iron bar", ItemId.IRON_BAR);
        iou = new ItemReq("IOU", IOU);
        iouHighlight = new ItemReq("IOU", IOU);
        redStone = new ItemReq("Stone ball", RED_BALL);
        blueStone = new ItemReq("Stone ball", BLUE_BALL);
        yellowStone = new ItemReq("Stone ball", YELLOW_BALL);
        pinkStone = new ItemReq("Stone ball", PURPLE_BALL);
        greenStone = new ItemReq("Stone ball", GREEN_BALL);

        certificate = new ItemReq("Certificate", 3114);
        climbingBoots = new ItemReq("Climbing boots", ItemId.CLIMBING_BOOTS);
        spikedBoots = new ItemReq("Spiked boots", ItemId.SPIKED_BOOTS);
        secretMap = new ItemReq("Secret way map", ItemId.MAP_3104);
        combination = new ItemReq("Combination", COMBINATION);
        gamesNecklace = new ItemReq(ItemId.GAMES_NECKLACE[0]);
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

            if (Utils.clickObject("Staircase", "Climb-up", false)) {
                Timer.waitCondition(() -> BAR_UPPER_FLOOR.contains(Player.getPosition()), 8000, 12000);
            }
        }
        if (BAR_UPPER_FLOOR.contains(Player.getPosition())) {
            if (PathingUtil.localNavigation(new RSTile(2906, 3543, 1)))
                PathingUtil.movementIdle();

            if (Utils.clickObject(3747, "Open", false)) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
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
            if (Utils.clickObject(3747, "Open", false)) {
                General.sleep(4000, 6000);
            }

            if (Utils.clickObject("Staircase", "Climb-down", false)) {
                Timer.abc2WaitCondition(() -> !BAR_UPPER_FLOOR.contains(Player.getPosition()), 8000, 12000);
            }
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
        setupItemReqs();

        General.sleep(20, 40);
        cQuesterV2.currentQuest = "Death Plateau";
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
           // giveHaroldDrink();

        } else if (Game.getSetting(314) == 50) {
          //  gamble();

        } else if (Game.getSetting(314) == 55) {
          //  readIOU();

        } else if (Game.getSetting(314) == 60) {
           // leaveUpStairs();
           // goToPuzzle();
        }
        if (Game.getSetting(314) == 70) {
            if (Inventory.find(ItemId.CERTIFICATE_3114).length < 1 &&
                    Inventory.find(ItemId.CLIMBING_BOOTS).length < 1 &&
                    Inventory.find(ItemId.SPIKED_BOOTS).length < 1 &&
                    Inventory.find(ItemId.MAP_3104).length < 1) {
              //  goIntoEquipmentRoom();
               // goToCave();
               // goToHouse();
            }
            if (Inventory.find(ItemId.CERTIFICATE_3114).length < 1 &&
                    Inventory.find(ItemId.SPIKED_BOOTS).length < 1 &&
                    Inventory.find(ItemId.MAP_3104).length < 1) {
              //  getSpikedBoots();
               // goToDenulth();
            }
            if (Inventory.find(ItemId.CERTIFICATE_3114).length > 0 &&
                    Inventory.find(ItemId.SPIKED_BOOTS).length < 1
                    && Inventory.find(ItemId.MAP_3104).length < 1) {
                //getSpikedBoots();
            }
            if (Inventory.find(ItemId.SPIKED_BOOTS).length > 0) {
               // goToHouse2();
            }
            if (Inventory.find(ItemId.MAP_3104).length > 0) {
               // goToEndOfPath();
              //  goToFinish();
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

}
