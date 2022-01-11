package scripts.QuestPackages.ElementalWorkshopI;

import dax.walker.utils.AccurateMouse;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSTile;
import org.tribot.api2007.types.RSVarBit;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.RuneMysteries.RuneMysteries;
import scripts.QuestSteps.BuyItemsStep;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.InventoryRequirement;
import scripts.Requirements.ItemReq;
import scripts.Tasks.Priority;

import java.util.ArrayList;
import java.util.Arrays;

public class ElementalWorkshop implements QuestTask {

    private static ElementalWorkshop quest;

    public static ElementalWorkshop get() {
        return quest == null ? quest = new ElementalWorkshop() : quest;
    }


    int KNIFE = 946;
    int STEEL_PICKAXE = 1269;
    int NEEDLE = 1733;
    int THREAD = 1734;
    int LEATHER = 1741;
    int HAMMER = 2347;
    int COAL = 453;
    int CAMELOT_TAB = 8010;
    int LOBSTER = 379;
    int BATTERED_BOOK = 2886;
    int SLASHED_BOOK = 9715;
    int BATTERED_KEY = 2887;
    int ELEMENTAL_ORE = 2892;
    int STONE_BOWL = 2888;
    int BOWL_OF_LAVA = 2889;
    int ELEMENTAL_BAR = 2893;
    int ADAMANT_SCIMITAR = 1331;

    RSArea START_AREA = new RSArea(new RSTile(2716, 3476, 0), new RSTile(2709, 3482, 0));
    RSArea WALL_AREA = new RSArea(new RSTile(2708, 3495, 0), new RSTile(2711, 3494, 0));
    RSArea BEHIND_WALL = new RSArea(new RSTile(2711, 3496, 0), new RSTile(2709, 3498, 0));
    RSArea CENTRE_ROOM = new RSArea(new RSTile(2729, 9883, 0), new RSTile(2710, 9898, 0));
    RSArea NORTH_ROOM = new RSArea(new RSTile(2729, 9901, 0), new RSTile(2711, 9911, 0));
    RSArea WEST_WATER_WHEEL = new RSArea(new RSTile(2713, 9909, 0), new RSTile(2712, 9906, 0));
    RSArea EAST_WATER_WHEEL = new RSArea(new RSTile(2726, 9909, 0), new RSTile(2727, 9907, 0));
    RSArea BELLOW_AREA = new RSArea(new RSTile(2732, 9884, 0), new RSTile(2735, 9881, 0));
    RSArea ROCK_AREA = new RSArea(new RSTile(2703, 9893, 0), new RSTile(2706, 9895, 0));
    RSArea LAVA_AREA = new RSArea(new RSTile(2718, 9872, 0), new RSTile(2713, 9873, 0));


    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemId.KNIFE, 1, 600),
                    new GEItem(ItemId.STAFF_OF_AIR, 1, 200),
                    new GEItem(ItemId.MIND_RUNE, 300, 20),
                    new GEItem(ItemId.FIRE_RUNE, 900, 20),
                    new GEItem(ItemId.STEEL_PICKAXE, 1, 200),
                    new GEItem(ItemId.NEEDLE, 1, 600),
                    new GEItem(ItemId.THREAD, 5, 600),
                    new GEItem(ItemId.LEATHER, 1, 600),
                    new GEItem(ItemId.HAMMER, 1, 600),
                    new GEItem(COAL, 4, 60),
                    new GEItem(ItemId.CAMELOT_TELEPORT, 5, 60),
                    new GEItem(ItemId.LOBSTER, 12, 60),
                    new GEItem(ItemId.STAMINA_POTION[0], 2, 15),
                    new GEItem(ItemId.RING_OF_WEALTH[0], 1, 25)
            )
    );

    InventoryRequirement initialItemReqs = new InventoryRequirement(new ArrayList<>(
            Arrays.asList(
                    new ItemReq(ItemId.KNIFE, 1, 1),
                    new ItemReq(ItemId.NEEDLE, 1, 1),
                    new ItemReq(ItemId.STEEL_PICKAXE, 1, 1),
                    new ItemReq(ItemId.THREAD, 5, 5),
                    new ItemReq(ItemId.LEATHER, 1, 1),
                    new ItemReq(ItemId.HAMMER, 1, 1),
                    new ItemReq(COAL, 4),
                    new ItemReq(ItemId.CAMELOT_TELEPORT, 4, 1),
                    new ItemReq(ItemId.LOBSTER, 9, 1),
                    new ItemReq(ItemId.MIND_RUNE, 300, 30),
                    new ItemReq(ItemId.FIRE_RUNE, 900, 90),
                    new ItemReq(ItemId.STAFF_OF_AIR, 1, 1, true, true),
                    new ItemReq(ItemId.STAMINA_POTION[0], 2, 0),
                    new ItemReq(ItemId.RING_OF_WEALTH[0], 1, 0, true)
            )
    ));

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
        BankManager.checkEquippedGlory();
        BankManager.depositAll(true);
        initialItemReqs.withdrawItems();
        Autocast.enableAutocast(Autocast.FIRE_STRIKE);
        if (!BankManager.checkInventoryItems(KNIFE, STEEL_PICKAXE, NEEDLE, THREAD, LEATHER, HAMMER, COAL, CAMELOT_TAB, LOBSTER)) {
            buyItems();
            getItems();
        }

    }

    public void startQuest() {
        cQuesterV2.status = "Starting Quest";
        PathingUtil.walkToArea(START_AREA);
        if (Utils.clickObj(26113, "Search"))
            Timer.abc2WaitCondition(() -> Inventory.find(BATTERED_BOOK).length > 0, 8000, 12000);

        RSItem[] book = Inventory.find(BATTERED_BOOK);
        if (book.length > 0) {

            if (AccurateMouse.click(book[0], "Read"))
                Timer.waitCondition(() -> Interfaces.get(49, 112) != null, 5000, 8700);

            General.sleep(General.random(500, 2500));
            if (Interfaces.get(49, 112) != null) {
                Interfaces.get(49, 112).click();
                General.sleep(General.random(500, 1500));
            }
        }
    }

    public void getKey() {
        if (Inventory.find(BATTERED_BOOK).length > 0) {
            cQuesterV2.status = "Getting key";
            General.println("[Debug]: " + cQuesterV2.status);
            if (Utils.useItemOnItem(KNIFE, BATTERED_BOOK))
                Timer.abc2WaitCondition(() -> Inventory.find(BATTERED_KEY).length > 0, 8000, 9000);

        }
    }

    public void enterUnderground() {
        cQuesterV2.status = "Going underground";
        PathingUtil.walkToArea(WALL_AREA);
        if (!BEHIND_WALL.contains(Player.getPosition()))
            if (Utils.clickObj(26114, "Open"))
                Timer.waitCondition(() -> BEHIND_WALL.contains(Player.getPosition()), 8000);
    }

    public void goDownStairs() {
        cQuesterV2.status = "Going down stairs";
        General.println("[Debug]: " + cQuesterV2.status);
        if (Utils.clickObj(3415, "Climb-down"))
            Timer.waitCondition(() -> !BEHIND_WALL.contains(Player.getPosition()), 8000);
    }

    public void fixWaterWheels() {
        fixWestWheel();

        if (!EAST_WATER_WHEEL.contains(Player.getPosition())) {
            cQuesterV2.status = "Fixing East Wheel";
            General.println("[Debug]: " + cQuesterV2.status);

            if (PathingUtil.localNavigation(new RSTile(2726, 9907, 0)))
                PathingUtil.movementIdle();
        }


        if (Utils.clickObj(3403, "Turn")) {
            Timer.waitCondition(() -> Player.getAnimation() != -1, 6000, 9000);
            General.sleep(1500, 3500);

        }
    }

    public void fixWestWheel() {
        if (!WEST_WATER_WHEEL.contains(Player.getPosition())) {
            cQuesterV2.status = "Fixing West Wheel";
            General.println("[Debug]: " + cQuesterV2.status);

            if (PathingUtil.localNavigation(new RSTile(2713, 9907, 0)))
                PathingUtil.movementIdle();
        }

        if (Utils.clickObj(3404, "Turn")) {
            Timer.waitCondition(() -> Player.getAnimation() != -1, 6000, 9000);
            General.sleep(1500, 3500);

        }
    }

    public void fixWaterWheels2() {
        fixWestWheel();

        if (!EAST_WATER_WHEEL.contains(Player.getPosition())) {
            cQuesterV2.status = "Fixing East Wheel";
            General.println("[Debug]: " + cQuesterV2.status);

            if (PathingUtil.localNavigation(new RSTile(2726, 9907, 0)))
                Timer.waitCondition(() -> EAST_WATER_WHEEL.contains(Player.getPosition()), 12000);
        }

        if (Utils.clickObj(3403, "Turn")) {
            Timer.waitCondition(() -> Player.getAnimation() != -1, 6000, 9000);
            General.sleep(General.random(1500, 3500));
        }


        fixWestWheel();
    }


    public void pullLever() {
        if (RSVarBit.get(2058).getValue() == 1 && RSVarBit.get(2059).getValue() == 1
                && RSVarBit.get(2060).getValue() == 0) { //varbit 2059 = 1 here
            if (Utils.clickObj(3406, "Pull")) {
                Timer.waitCondition(() -> Player.getAnimation() != -1, 4000, 6000);
                General.sleep(General.random(500, 3000));
            }
        }
    }

    public void fixBellows() {
        cQuesterV2.status = "Fixing Bellow";
        PathingUtil.localNavigation(BELLOW_AREA);
        if (BELLOW_AREA.contains(Player.getPosition()))
            if (Utils.clickObj(3407, "Fix"))
                General.sleep(General.random(4500, 6500));
    }

    public void pullBellowLever() {
        if (Utils.clickObj(3409, "Pull")) {
            Timer.waitCondition(() -> Player.getAnimation() != -1, 6000, 9000);
            General.sleep(General.random(1500, 3500));
        }
    }

    public void mineRock() {
        if (Inventory.find(ELEMENTAL_ORE).length < 1 && Inventory.find(STONE_BOWL).length < 1 && Inventory.find(BOWL_OF_LAVA).length < 1) {
            if (!ROCK_AREA.contains(Player.getPosition())) {
                cQuesterV2.status = "Going to Rocks";
                General.println("[Debug]: " + cQuesterV2.status);
                PathingUtil.localNavigation(ROCK_AREA.getRandomTile());
                Timer.waitCondition(() -> ROCK_AREA.contains(Player.getPosition()), 5000, 7000);
                //   Utils
                //  .blindWalkToArea(ROCK_AREA);
            }
            if (ROCK_AREA.contains(Player.getPosition())) {

                if (!Autocast.isAutocastEnabled(Autocast.FIRE_STRIKE))
                    Autocast.enableAutocast(Autocast.FIRE_STRIKE);

                cQuesterV2.status = "Mining Rock";
                General.println("[Debug]: " + cQuesterV2.status);

                if (!Combat.isUnderAttack() && GroundItems.find(ELEMENTAL_ORE).length == 0 &&
                        Utils.clickNPC("Elemental rock", "Mine")) {
                    Timer.waitCondition(Combat::isUnderAttack, 7000, 9000);

                }
                if (Combat.isUnderAttack()) {
                    Timer.slowWaitCondition(() -> !Combat.isUnderAttack()
                            || Combat.getHPRatio() < General.random(40, 65), 40000, 70000);

                    if (Combat.getHPRatio() < General.random(50, 65))
                        EatUtil.eatFood();

                }
            }
            if (GroundItems.find(ELEMENTAL_ORE).length == 0)
                Timer.waitCondition(() -> GroundItems.find(ELEMENTAL_ORE).length > 0, 2000);

            if (GroundItems.find(ELEMENTAL_ORE).length > 0) {
                if (Inventory.isFull())
                    EatUtil.eatFood();
            }
            if (Utils.clickGroundItem(ELEMENTAL_ORE))
                Timer.waitCondition(() -> Inventory.find(ELEMENTAL_ORE).length > 0, 8000);

        }
    }

    public void getStoneBowl() {
        cQuesterV2.status = "Getting stone bowl";
        General.println("[Debug]: " + cQuesterV2.status);

        if (Inventory.find(ELEMENTAL_ORE).length > 0 && Inventory.find(STONE_BOWL).length < 1 && Inventory.find(BOWL_OF_LAVA).length < 1) {
            if (PathingUtil.localNavigation(new RSTile(2723, 9893, 0)))
                Timer.waitCondition(() -> !Player.isMoving(), 12000);

            if (Inventory.isFull())
                EatUtil.eatFood();

            if (Utils.clickObj(3397, "Search"))
                Timer.waitCondition(() -> Inventory.find(STONE_BOWL).length > 0, 5000);
        }
    }

    public void fillBowl() {
        if (Inventory.find(ELEMENTAL_ORE).length > 0 && Inventory.find(STONE_BOWL).length > 0 && Inventory.find(BOWL_OF_LAVA).length < 1) {
            cQuesterV2.status = "Filling bowl with lava";
            General.println("[Debug]: " + cQuesterV2.status);
            if (PathingUtil.localNavigation(LAVA_AREA.getRandomTile()))
                Timer.slowWaitCondition(() -> !Player.isMoving(), 12000, 15000);

            if (Utils.useItemOnObject(STONE_BOWL, 18522)) {
                Timer.waitCondition(() -> Inventory.find(STONE_BOWL).length < 1, 5000);
                General.sleep(General.random(2000, 4000));
            }
        }
    }

    int FURNACE_ID = 3410;

    public void activateFurnace() {
        cQuesterV2.status = "Fixing furnace";
        General.println("[Debug]: " + cQuesterV2.status);
        if (Inventory.find(ELEMENTAL_ORE).length > 0 && Inventory.find(STONE_BOWL).length < 1 && Inventory.find(BOWL_OF_LAVA).length > 0) {
            PathingUtil.localNavigation(new RSTile(2724, 9875));
            PathingUtil.movementIdle();

            if (Utils.useItemOnObject(BOWL_OF_LAVA, 3410)) {

                Timer.slowWaitCondition(() -> RSVarBit.get(2062).getValue() == 1, 7000, 9000);
            }
        }
    }

    public void smelt() {
        if (Inventory.find(ELEMENTAL_BAR).length < 1) {
            cQuesterV2.status = "Smelting";
            General.println("[Debug]: " + cQuesterV2.status);
            if (PathingUtil.localNavigation(new RSTile(2724, 9875)))
                PathingUtil.movementIdle();

            if (Utils.useItemOnObject(ELEMENTAL_ORE, FURNACE_ID)) {
                Timer.waitCondition(() -> Inventory.find(ELEMENTAL_BAR).length > 0, 7000);
                General.sleep(General.random(500, 3000));
            }
        }
    }

    public void makeShield() {
        if (Inventory.find(ELEMENTAL_BAR).length > 0) {
            cQuesterV2.status = "Making shield";
            General.println("[Debug]: " + cQuesterV2.status);
            if (PathingUtil.localNavigation(new RSTile(2718, 9888)))
                PathingUtil.movementIdle();

            if (Utils.useItemOnObject(ELEMENTAL_BAR, 3402)) {
                Timer.waitCondition(() -> Inventory.find(2890).length > 0, 8000);
                General.sleep(500, 3000);
            }
        }
    }


    int GAME_SETTING = 299;

    @Override
    public void execute() {
        if (!checkRequirements()) {
            cQuesterV2.taskList.remove(this);
            return;
        }
        if (Game.getSetting(299) == 0) {
            buyItems();
            getItems();
            startQuest();
        }
        if (RSVarBit.get(2057).getValue() == 0) {// Varbit 2057 = 0
            getKey();

        }
        if (RSVarBit.get(2065).getValue() == 0 && RSVarBit.get(2057).getValue() == 1) { // Varbit 2057 = 1
            enterUnderground();
            goDownStairs();
        }
        if (RSVarBit.get(2065).getValue() == 1 && (RSVarBit.get(2058).getValue() == 0 || RSVarBit.get(2059).getValue() == 0)) { // varbit 2065 is 1 and 4065 is 0 if upstairs, 1 if downstairs
            // changes once you've been downstairs, doesn't go baack if you go up
            fixWaterWheels();
            pullLever();
        }
        if (RSVarBit.get(4605).getValue() == 0 && (RSVarBit.get(2058).getValue() == 0 || RSVarBit.get(2059).getValue() == 0)) {
            fixWaterWheels2();
            pullLever();
        }
        if (RSVarBit.get(2058).getValue() == 1 && RSVarBit.get(2059).getValue() == 1 && RSVarBit.get(2060).getValue() == 0) { //varbit 2059 = 1 here
            pullLever();

        }
        if (RSVarBit.get(2060).getValue() == 1 && RSVarBit.get(2061).getValue() == 0) {
            fixBellows();
        }
        if (RSVarBit.get(2061).getValue() == 1 && RSVarBit.get(2063).getValue() == 0) {
            pullBellowLever();

        }
        if (RSVarBit.get(2063).getValue() == 1 && RSVarBit.get(2062).getValue() == 0) {
            mineRock();
            getStoneBowl();
            fillBowl();
            activateFurnace();

        }
        if (RSVarBit.get(2062).getValue() == 1 && RSVarBit.get(2067).getValue() == 0) {
            smelt();
            makeShield();
        }
        if (RSVarBit.get(2067).getValue() == 1) {
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
        return "Elemental Workshop I";
    }

    @Override
    public boolean checkRequirements() {
        if (Skills.getActualLevel(Skills.SKILLS.CRAFTING) < 20) {
            return false;
        }
        if (Skills.getActualLevel(Skills.SKILLS.SMITHING) < 20) {
            return false;
        }
        if (Skills.getActualLevel(Skills.SKILLS.MINING) < 20) {
            return false;
        }
        return true;
    }
}
