package scripts.QuestPackages.DwarfCannon;

import dax.walker.utils.camera.DaxCamera;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.WitchsPotion.WitchsPotion;
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

public class DwarfCannon implements QuestTask {
    /**
     * ITEM IDs
     */
    int DWARF_REMAINS_ID = 0;
    int MOULD_ID = 4;
    int RAILING_ID = 14;
    int TOOL_KIT = 1;

    /**
     * TILES AND AREAS
     */
    RSTile OUTSIDE_CAVE_TILE = new RSTile(2622, 3391, 0);
    RSTile INSIDE_CAVE_TILE = new RSTile(2571, 9851, 0);
    RSTile FALADOR_BASE_TILE = new RSTile(3011, 3453, 0);

    RSArea START_AREA = new RSArea(new RSTile(2569, 3458, 0), new RSTile(2565, 3462, 0));
    RSArea UNDER_REMAINS_TOWER = new RSArea(new RSTile(2570, 3442, 0), 6);

    RSArea UNDER_TOWER_AREA = new RSArea(new RSTile(2571, 3438, 0), new RSTile(2569, 3443, 0));
    RSArea SECOND_FLOOR = new RSArea(new RSTile(2571, 3440, 1), new RSTile(2569, 3444, 1));
    RSArea THIRD_FLOOR = new RSArea(new RSTile(2571, 3442, 2), new RSTile(2567, 3444, 2));

    RSArea INSIDE_CAVE_ENTRANCE = new RSArea(new RSTile(2619, 9797, 0), 12);

    /**
     * OBJECTS
     */
    RSObject[] remains = Objects.findNearest(15, "Dwarf remains");
    RSObject[] railings = Objects.findNearest(15, "Railing");


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
                    new ItemReq(ItemID.LOBSTER, 10, 2),
                    new ItemReq(ItemID.CAMELOT_TELEPORT, 5, 1),
                    new ItemReq(ItemID.STAMINA_POTION[0], 2, 1),
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
        cQuesterV2.status = "Banking";
        if (Inventory.find(
                ItemID.HAMMER).length < 1 || Inventory.find(
                ItemID.CAMELOT_TELEPORT).length < 1 || Inventory.find(
                ItemID.FALADOR_TELEPORT).length < 1) {
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
        cQuesterV2.status = "Starting";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(START_AREA, false);
        if (NpcChat.talkToNPC("Captain Lawgof")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Sure, I'd be honoured to join.", "Yes.");
            NPCInteraction.handleConversation();
        }
    }

    public void checkEat() {
        if (Combat.getHPRatio() < General.random(35, 65)) {
            RSItem[] food = Inventory.find(Filters.Items.actionsContains("Eat"));
            if (food.length > 0 && food[0].click("Eat")) {
                Timer.waitCondition(() -> Player.getAnimation() != -1, 1500, 3000);
            } else if (food.length == 0) {
                General.println("[Debug]: No food, leaving.");
                buyItems();
                getQuestItems();
            }
        }
    }

    public void fixRailings() {
        cQuesterV2.status = "Fixing Railings";
        General.println("[Debug]: " + cQuesterV2.status);
        RSItem[] i = Inventory.find(14);
        if (i.length > 0) {
            if (i[0].getStack() == 6) {
                PathingUtil.walkToTile(new RSTile(2577, 3457), 1, false);
                inspectRailing(15595);
            } else if (i[0].getStack() == 5) {
                PathingUtil.walkToTile(new RSTile(2573, 3457), 1, false);
                inspectRailing(15594);

            } else if (i[0].getStack() == 4) {
                PathingUtil.walkToTile(new RSTile(2563, 3457), 1, false);
                inspectRailing(15593);
            } else if (i[0].getStack() == 3) {
                PathingUtil.walkToTile(new RSTile(2559, 3458), 1, false);
                inspectRailing(15592);
            } else if (i[0].getStack() == 2) {
                PathingUtil.walkToTile(new RSTile(2557, 3468), 1, false);
                inspectRailing(15591);
            } else if (i[0].getStack() == 1) {
                PathingUtil.walkToTile(new RSTile(2555, 3479), 1, false);
                inspectRailing(15590);
            }
        }
    }


    public void inspectRailing(int railingId) {
        railings = Objects.findNearest(15, railingId);
        RSItem[] invRailings = Inventory.find(RAILING_ID);

        checkEat();

        if (railings.length > 0 && invRailings.length > 0) {
            if (!railings[0].isClickable())
                DaxCamera.focus(railings[0]);

            if (railings[0].click("Inspect")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                Timer.waitCondition(() -> Player.getAnimation() != -1, 5000);
                Timer.waitCondition(() -> Inventory.find(14).length == 0 || Inventory.find(14)[0].getStack() < invRailings[0].getStack() || Player.getAnimation() == -1, 8000, 12000);
                Utils.shortSleep();

            }
        }
    }

    public void getStep2() {
        if (Inventory.find(14).length == 0) {
            cQuesterV2.status = "Going to Captain";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(START_AREA, false);
            if (NpcChat.talkToNPC("Captain Lawgof")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
        }
    }


    public void dwarfRemains() {
        if (Inventory.find(DWARF_REMAINS_ID).length == 0) {
            cQuesterV2.status = "Going to Remains";
            General.println("[Debug]: " + cQuesterV2.status);
            if (!UNDER_TOWER_AREA.contains(Player.getRSPlayer())
                    && !SECOND_FLOOR.contains(Player.getRSPlayer())
                    && !THIRD_FLOOR.contains(Player.getRSPlayer())) {
                PathingUtil.walkToArea(UNDER_TOWER_AREA, false);
            }

            if (UNDER_REMAINS_TOWER.contains(Player.getPosition()))
                if (Utils.clickObject("Ladder", "Climb-up", false))
                    Timer.waitCondition(() -> SECOND_FLOOR.contains(Player.getPosition()), 4000, 6000);


            if (SECOND_FLOOR.contains(Player.getPosition()))
                if (Utils.clickObject(11, "Climb-up", false))
                    Timer.waitCondition(() -> THIRD_FLOOR.contains(Player.getPosition()), 4000, 6000);


            if (THIRD_FLOOR.contains(Player.getPosition())) {
                remains = Objects.findNearest(15, "Dwarf remains");
                if (remains.length > 0 && Utils.clickObject(remains[0], "Take")) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                    Timer.slowWaitCondition(() -> Inventory.find(0).length > 0, 8000, 12000);
                }
            }
        } else {
            if (THIRD_FLOOR.contains(Player.getPosition())
                    && Utils.clickObject("Ladder", "Climb-down", false))
                Timer.waitCondition(() -> SECOND_FLOOR.contains(Player.getPosition()), 4000, 6000);

            if (SECOND_FLOOR.contains(Player.getPosition())
                    && Utils.clickObject(Filters.Objects.actionsContains("Climb-down"), "Climb-down"))
                Timer.waitCondition(() -> UNDER_TOWER_AREA.contains(Player.getPosition()), 8000, 12000);

        }
    }

    public void returnRemains() {
        if (Inventory.find(DWARF_REMAINS_ID).length > 0) {
            cQuesterV2.status = "Returning Remains";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(START_AREA, false);
            if (NpcChat.talkToNPC("Captain Lawgof")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
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
            Timer.waitCondition(() -> !Player.isMoving(), 10000, 14000);

        cQuesterV2.status = "Searching crates";
        if (Utils.clickObject("Crate", "Search", false)) { //should filter by tile
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }

    }

    public void returnAfterChild() {
        cQuesterV2.status = "Returning To Captain";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(START_AREA, false);
        if (NpcChat.talkToNPC("Captain Lawgof")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Okay, I'll see what I can do.");
            NPCInteraction.handleConversation();
        }
    }


    public void fixCannon() {
        cQuesterV2.status = "Fixing cannon";
        General.println("[Debug]: " + cQuesterV2.status);

        if (Utils.useItemOnObject(TOOL_KIT, "Broken multicannon"))
            Timer.abc2WaitCondition(() -> Interfaces.isInterfaceSubstantiated(Interfaces.get(409)), 8000, 12000);

        if (Interfaces.isInterfaceSubstantiated(Interfaces.get(409))) {
            if (Interfaces.get(409, 2).click())
                General.sleep(General.random(500, 1200));
            if (Interfaces.get(409, 7).click())
                General.sleep(General.random(1200, 4000));
            if (Interfaces.get(409, 3).click())
                General.sleep(General.random(500, 1200));
            if (Interfaces.get(409, 8).click())
                General.sleep(General.random(1200, 4000));
            if (Interfaces.get(409, 1).click())
                General.sleep(General.random(500, 1200));
            if (Interfaces.get(409, 9).click())
                Utils.modSleep();
        }
    }


    public void talkToCaptain8() {
        cQuesterV2.status = "Returning To Captain";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(START_AREA, false);

        if (NpcChat.talkToNPC("Captain Lawgof")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();

            General.sleep(General.random(3000, 4000));

            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Okay then, just for you!");
            NPCInteraction.handleConversation();
        }

    }

    public void goToDwarfBase() {
        cQuesterV2.status = "Going to Falador Base";
        PathingUtil.walkToTile(FALADOR_BASE_TILE, 2, false);

        cQuesterV2.status = "Talking to Nulodion";
        General.println("[Debug]: " + cQuesterV2.status);
        // get first mould
        if (NpcChat.talkToNPC("Nulodion")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            General.sleep(General.random(2000, 4000));
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }

        cQuesterV2.status = "Getting second mould";
        RSItem[] invMould = Inventory.find(MOULD_ID);
        if (invMould.length > 0 && invMould[0].click("Drop")) {
            Timer.waitCondition(() -> Inventory.find(MOULD_ID).length == 0, 2500, 3000);
        }

        // get second mould
        if (NpcChat.talkToNPC("Nulodion")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }
        //pickup dropped mould
        Utils.clickGroundItem(MOULD_ID);
    }


    public void talkToCaptain9() {
        cQuesterV2.status = "Finishing Quest";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(START_AREA, false);
        if (NpcChat.talkToNPC("Captain Lawgof")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }
    }

    int GAME_SETTING = 0;


    private static DwarfCannon quest;

    public static DwarfCannon get() {
        return quest == null ? quest = new DwarfCannon() : quest;
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
            getStep2();
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
            talkToCaptain9();
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
}
