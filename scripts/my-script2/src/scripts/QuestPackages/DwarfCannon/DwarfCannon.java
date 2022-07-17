package scripts.QuestPackages.DwarfCannon;

import dax.walker.utils.camera.DaxCamera;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.Combat;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.*;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.Area;
import org.tribot.script.sdk.types.Npc;
import org.tribot.script.sdk.types.Widget;
import org.tribot.script.sdk.types.WorldTile;
import org.tribot.script.sdk.util.TribotRandom;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.WitchsPotion.WitchsPotion;
import scripts.QuestSteps.BuyItemsStep;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.*;
import scripts.Tasks.Priority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DwarfCannon implements QuestTask {


    int GAME_SETTING = 0;

    private static DwarfCannon quest;

    public static DwarfCannon get() {
        return quest == null ? quest = new DwarfCannon() : quest;
    }


    /**
     * ITEM IDs
     */
    int TOOL_KIT = 1;
    int[] BROKEN_RAILING_IDS = {15595, 15594, 15593, 15592, 15591, 15590};


    /**
     * TILES AND AREAS
     */
    RSTile OUTSIDE_CAVE_TILE = new RSTile(2622, 3391, 0);
    RSTile INSIDE_CAVE_TILE = new RSTile(2571, 9851, 0);
    WorldTile INSIDE_CAVE_WORLD_TILE = new WorldTile(2571, 9851, 0);
    WorldTile UNDER_TOWER_TILE = new WorldTile(2568, 3442, 0);
    RSTile FALADOR_BASE_TILE = new RSTile(3011, 3453, 0);

    RSArea START_AREA = new RSArea(new RSTile(2569, 3458, 0), new RSTile(2565, 3462, 0));
    RSArea UNDER_TOWER_AREA = new RSArea(new RSTile(2571, 3438, 0), new RSTile(2569, 3443, 0));
    RSArea SECOND_FLOOR = new RSArea(new RSTile(2571, 3440, 1), new RSTile(2569, 3444, 1));
    RSArea THIRD_FLOOR = new RSArea(new RSTile(2571, 3442, 2), new RSTile(2567, 3444, 2));

    RSArea INSIDE_CAVE_ENTRANCE = new RSArea(new RSTile(2619, 9797, 0), 12);

    /**
     * OTHER
     */
    int CANNON_REPAIR_WIDGET = 409;

    VarbitRequirement springFixed = new VarbitRequirement(2239, 1);
    VarbitRequirement safetyFixed = new VarbitRequirement(2238, 1);
    VarbitRequirement cannonFixed = new VarbitRequirement(2235, 1);

    /**
     * QUEST STEPS
     */

    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.LOBSTER, 10, 45),
                    new GEItem(ItemID.CAMELOT_TELEPORT, 5, 40),
                    new GEItem(ItemID.STAMINA_POTION[0], 2, 15),
                    new GEItem(ItemID.SKILLS_NECKLACE[2], 2, 25),
                    new GEItem(ItemID.FALADOR_TELEPORT, 5, 25),
                    new GEItem(ItemID.RING_OF_WEALTH[0], 1, 25),
                    new GEItem(ItemID.HAMMER, 1, 250)
            )
    );

    InventoryRequirement initialItemReqs = new InventoryRequirement(new ArrayList<>(
            Arrays.asList(
                    new ItemReq(ItemID.LOBSTER, 10, 1),
                    new ItemReq(ItemID.CAMELOT_TELEPORT, 5, 1),
                    new ItemReq(ItemID.STAMINA_POTION[0], 2, 0),
                    new ItemReq(ItemID.FALADOR_TELEPORT, 5, 1),
                    new ItemReq(ItemID.HAMMER, 1, 1),
                    new ItemReq(ItemID.SKILLS_NECKLACE[2], 2, 1),
                    new ItemReq(ItemID.RING_OF_WEALTH[0], 1, 0, true)
            )
    ));

    BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);

    public void buyItems() {
        cQuesterV2.status = "Buying items";
        if (!initialItemReqs.check())
            buyStep.buyItems();
    }

    public void getQuestItems() {
        if (!initialItemReqs.check()) {
            cQuesterV2.status = "Banking";
            BankManager.open(true);
            BankManager.depositAll(true);
            BankManager.withdraw(1, true, ItemID.HAMMER);
            BankManager.withdraw(4, true, ItemID.LOBSTER);
            BankManager.withdraw(1, true, ItemID.STAMINA_POTION[0]);
            BankManager.withdraw(3, true, ItemID.CAMELOT_TELEPORT);
            BankManager.withdraw(1, true, ItemID.SKILLS_NECKLACE[2]);
            BankManager.withdraw(3, true, ItemID.FALADOR_TELEPORT);
            BankManager.close(true);
        }
    }

    public void goToStart() {
        if (initialItemReqs.check()) {
            cQuesterV2.status = "Starting";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(START_AREA, false);
            if (NpcChat.talkToNPC("Captain Lawgof")) {
                NpcChat.handle(true, "Sure, I'd be honoured to join.", "Yes.");
            }
        }
    }

    public void checkEat() {
        if (MyPlayer.getCurrentHealthPercent() < TribotRandom.uniform(35, 65)) {
            EatUtil.eatFood();
        }
    }

    public int getRailingStack() {
        return org.tribot.script.sdk.Inventory.getCount(ItemID.RAILING);
    }

    public void fixRailings() {
        cQuesterV2.status = "Fixing Railings";
        General.println("[Debug]: " + cQuesterV2.status);

        int stack = getRailingStack();
        switch (stack) {
            case (6):
                PathingUtil.walkToTile(new RSTile(2577, 3457), 1, false);
                inspectRailing(15595);
                break;
            case (5):
                PathingUtil.walkToTile(new RSTile(2573, 3457), 1, false);
                inspectRailing(15594);
                break;
            case (4):
                PathingUtil.walkToTile(new RSTile(2563, 3457), 1, false);
                inspectRailing(15593);
                break;
            case (3):
                PathingUtil.walkToTile(new RSTile(2559, 3458), 1, false);
                inspectRailing(15592);
                break;
            case (2):
                PathingUtil.walkToTile(new RSTile(2557, 3468), 1, false);
                inspectRailing(15591);
                break;
            case (1):
                PathingUtil.walkToTile(new RSTile(2555, 3479), 1, false);
                inspectRailing(15590);
                break;

        }
    }


    public void inspectRailing(int railingId) {

        //idle if we're still moving towards the railing from the walk call
        // short time out will sometimes timeout before done moving, this is fine
        if (MyPlayer.isMoving())
            Timer.waitCondition(2000, 400, () -> !MyPlayer.isMoving());

        int railingStack = getRailingStack();

        //eat if needed and cache health
        checkEat();
        int health = MyPlayer.getCurrentHealth();

        if (Query.gameObjects().idEquals(railingId)
                .findClosestByPathDistance()
                .map(o -> o.interact("Inspect")).orElse(false)
                && NpcChat.handle(true)) {
            if (Timer.waitCondition(3000, 400,
                    () -> MyPlayer.getAnimation() != -1))
                Timer.waitCondition(12000, 650,
                        () -> Inventory.find(14).length == 0
                                || getRailingStack() < railingStack
                                || MyPlayer.getAnimation() == -1
                                || MyPlayer.getCurrentHealth() < health);

        }
    }

    public void goToCaptain() {
        if (getRailingStack() == 0) {
            cQuesterV2.status = "Going to Captain";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(START_AREA, false);
            if (NpcChat.talkToNPC("Captain Lawgof")) {
                NpcChat.handle(true);
            }
        }
    }


    public void dwarfRemains() {
        if (Inventory.find(ItemID.DWARF_REMAINS).length == 0) {
            cQuesterV2.status = "Going to Remains";
            General.println("[Debug]: " + cQuesterV2.status);
            if (UNDER_TOWER_TILE.distance() > 10
                    && !SECOND_FLOOR.contains(Player.getRSPlayer())
                    && !THIRD_FLOOR.contains(Player.getRSPlayer())) {
                Log.info("Going to remains tower");
                PathingUtil.walkToTile(UNDER_TOWER_TILE);
            }

            if (MyPlayer.getTile().getPlane() == 0 && UNDER_TOWER_TILE.distance() < 10)
                if (Utils.clickObject("Ladder", "Climb-up", false))
                    Timer.waitCondition(6000, 500, () -> SECOND_FLOOR.contains(Player.getPosition()));


            if (SECOND_FLOOR.contains(Player.getPosition()))
                if (Utils.clickObject(11, "Climb-up", false))
                    Timer.waitCondition(6000, 400, () -> THIRD_FLOOR.contains(Player.getPosition()));


            if (THIRD_FLOOR.contains(Player.getPosition())) {
                RSObject[] remains = Objects.findNearest(15, "Dwarf remains");
                if (remains.length > 0 && Utils.clickObject(remains[0], "Take")) {
                    NpcChat.handle(true);
                    Timer.waitCondition(8000, 800, () -> Inventory.find(0).length > 0);
                }
            }
        }
    }

    public void descentTower() {
        if (THIRD_FLOOR.contains(Player.getPosition())
                && Utils.clickObject("Ladder", "Climb-down", false))
            Timer.waitCondition(6000, 400,
                    () -> SECOND_FLOOR.contains(Player.getPosition()));


        if (SECOND_FLOOR.contains(Player.getPosition())
                && Query.gameObjects()
                .actionContains("Climb-down")
                .findClosest().map(l -> l.interact("Climb-down")).orElse(false))
            Timer.waitCondition(8000, 600, () -> MyPlayer.getTile().getPlane() == 0);

    }

    public void returnRemains() {
        if (Inventory.find(ItemID.DWARF_REMAINS).length > 0) {
            cQuesterV2.status = "Returning Remains";
            General.println("[Debug]: " + cQuesterV2.status);
            descentTower();

            PathingUtil.walkToArea(START_AREA, false);
            if (NpcChat.talkToNPC("Captain Lawgof")) {
                NpcChat.handle(true);
            }
        }
    }

    public void theMissingSon() {
        cQuesterV2.status = "Going to cave";
        General.println("[Debug]: " + cQuesterV2.status);

        PathingUtil.walkToTile(OUTSIDE_CAVE_TILE, 3, true);

        if (Utils.clickObject("Cave entrance", "Enter", false))
            Timer.abc2WaitCondition(() -> INSIDE_CAVE_ENTRANCE.contains(Player.getPosition()), 10000, 15000);


    }

    public void theMissingSon2() {
        cQuesterV2.status = "Going to crates";
        General.println("[Debug]: " + cQuesterV2.status);
        if (PathingUtil.walkToTile(INSIDE_CAVE_TILE, 1, true))
            Timer.waitCondition(14000, 500, () -> !MyPlayer.isMoving());

        cQuesterV2.status = "Searching crates";
        if (Query.gameObjects()
                .tileEquals(new WorldTile(2571, 9850, 0))
                .actionContains("Search")
                .nameContains("Crate") //might have a specific ID, need to check, but tile works
                .findClosestByPathDistance()
                .map(c -> c.interact("Search")).orElse(false))
            NpcChat.handle(true);

    }

    public void returnAfterChild() {
        cQuesterV2.status = "Returning To Captain";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(START_AREA, false);
        if (NpcChat.talkToNPC("Captain Lawgof")) {
            NpcChat.handle(true, "Okay, I'll see what I can do.");
        }
    }


    private boolean clickWidget(int indexOne, int indexTwo) {
        return Query.widgets().inIndexPath(indexOne, indexTwo)
                .findFirst().map(Widget::click).orElse(false);
    }

    public void fixCannon() {
        cQuesterV2.status = "Fixing cannon";
        General.println("[Debug]: " + cQuesterV2.status);

        if (Utils.useItemOnObject(TOOL_KIT, "Broken multicannon"))
            Timer.abc2WaitCondition(() -> Widgets.isVisible(CANNON_REPAIR_WIDGET), 8000, 12000);

        if (Widgets.isVisible(CANNON_REPAIR_WIDGET)) {
            if (!safetyFixed.check()) {
                if (clickWidget(CANNON_REPAIR_WIDGET, 2)) //select pliers
                    Waiting.waitNormal(800, 70);
                if (clickWidget(CANNON_REPAIR_WIDGET, 7)) // select safety
                    Waiting.waitUntil(3500, 600, () -> safetyFixed.check());
            }
            if (!springFixed.check()) {
                if (clickWidget(CANNON_REPAIR_WIDGET, 3)) //select hooked tool
                    Waiting.waitNormal(800, 70);
                if (clickWidget(CANNON_REPAIR_WIDGET, 8)) // select spring
                    Waiting.waitUntil(3500, 600, () -> springFixed.check());
            }
            if (!cannonFixed.check()) {
                if (clickWidget(CANNON_REPAIR_WIDGET, 1)) //select toothed tool
                    Waiting.waitNormal(800, 70);
                if (clickWidget(CANNON_REPAIR_WIDGET, 9)) // select gear
                    Waiting.waitUntil(3500, 600, () -> cannonFixed.check());
            }
        }
    }


    public void talkToCaptain8() {
        cQuesterV2.status = "Returning To Captain";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(START_AREA, false);

        if (NpcChat.talkToNPC("Captain Lawgof")) {
            NpcChat.handle(true);

            //chat box disappears then comes back after a second or two, so we need second call
            // true is 'waitForChat' flag
            NpcChat.handle(true, "Okay then, just for you!");

        }

    }

    public boolean goToDwarfBase() {
        cQuesterV2.status = "Going to Falador Base";
        PathingUtil.walkToTile(FALADOR_BASE_TILE, 2, false);

        cQuesterV2.status = "Talking to Nulodion";
        General.println("[Debug]: " + cQuesterV2.status);
        // get first mould
        if (NpcChat.talkToNPC("Nulodion")) {
            NpcChat.handle(true);

            //chat box dissapears then comes back after a second or two, so we need second call
            // true is 'waitForChat' flag
            NpcChat.handle(true);
        }

        cQuesterV2.status = "Getting second mould";
        //drop inv mould
        RSItem[] invMould = Inventory.find(ItemID.AMMO_MOULD);
        if (org.tribot.script.sdk.Inventory.getCount(ItemID.AMMO_MOULD) == 1
                && invMould.length > 0 && invMould[0].click("Drop")) {
            Timer.waitCondition(3500, 400,
                    () -> Inventory.find(ItemID.AMMO_MOULD).length == 0);
        }

        // get second mould
        if (NpcChat.talkToNPC("Nulodion")) {
            NpcChat.handle(true);
        }
        //pickup dropped mould
        Utils.clickGroundItem(ItemID.AMMO_MOULD);
        return org.tribot.script.sdk.Inventory.getCount(ItemID.AMMO_MOULD) == 2;
    }


    public void finishQuest() {
        cQuesterV2.status = "Finishing Quest";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(START_AREA, false);
        if (NpcChat.talkToNPC("Captain Lawgof")) {
            NpcChat.handle(true);
        }
    }

    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return cQuesterV2.taskList.get(0).equals(DwarfCannon.get());
    }

    @Override
    public void execute() {
        if (Game.getSetting(GAME_SETTING) == 0) {
            buyItems();
            getQuestItems();
            goToStart();
        } else if (Game.getSetting(GAME_SETTING) == 1) {
            fixRailings();
            goToCaptain();
        } else if (Game.getSetting(GAME_SETTING) == 2) {
            dwarfRemains();
        } else if (Game.getSetting(GAME_SETTING) == 3) {
            returnRemains();
        } else if (Game.getSetting(GAME_SETTING) == 4) {
            theMissingSon();
        } else if (Game.getSetting(GAME_SETTING) == 5) {
            theMissingSon2();
        } else if (Game.getSetting(GAME_SETTING) == 6) {
            returnAfterChild();
        } else if (Game.getSetting(GAME_SETTING) == 7) {
            fixCannon();
        } else if (Game.getSetting(GAME_SETTING) == 8) {
            talkToCaptain8();
        } else if (Game.getSetting(GAME_SETTING) == 9) {
            goToDwarfBase();
        } else if (Game.getSetting(GAME_SETTING) == 10) {
            finishQuest();
        } else if (Game.getSetting(GAME_SETTING) == 11) {
            Utils.closeQuestCompletionWindow();
            cQuesterV2.taskList.remove(DwarfCannon.get());
        }
    }

    @Override
    public String questName() {
        return "Dwarf Cannon";
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

    @Override
    public boolean isComplete() {
        return Quest.DWARF_CANNON.getState().equals(Quest.State.COMPLETE);
    }
}
