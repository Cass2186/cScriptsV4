package scripts.QuestPackages.FightArena;

import dax.walker.utils.AccurateMouse;
import dax.walker.utils.camera.DaxCamera;
import dax.walker_engine.WalkerEngine;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.ext.Doors;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import scripts.*;
import scripts.EntitySelector.Entities;
import scripts.EntitySelector.finders.prefabs.NpcEntity;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.WitchsPotion.WitchsPotion;
import scripts.QuestSteps.BuyItemsStep;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.ItemRequirement;
import scripts.Requirements.Requirement;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FightArena implements QuestTask {

    private static FightArena quest;

    public static FightArena get() {
        return quest == null ? quest = new FightArena() : quest;
    }

    public int COINS = 995;
    public int lobster = 379;
    public int staffOfAir = 1381;
    public int KEYS = 76;
    public int khaliBrew = 77;
    public int camelotTab = 8010;
    int HELMET = 74;
    int CHEST_ARMOUR = 75;


    RSArea startArea = new RSArea(new RSTile(2561, 3202, 0), new RSTile(2571, 3192, 0));
    RSArea ARMOUR_AREA = new RSArea(new RSTile(2612, 3189, 0), new RSTile(2614, 3192, 0));
    RSArea ARMOUR_AREA_DOOR = new RSArea(new RSTile(2611, 3190, 0), new RSTile(2609, 3189, 0));
    RSArea DOOR_ENTRANCE = new RSArea(new RSTile(2618, 3172, 0), new RSTile(2616, 3173, 0));
    RSArea INSIDE_DOOR = new RSArea(new RSTile(2619, 3171, 0), new RSTile(2617, 3168, 0));
    RSArea NORTH_JAIL_AREA = new RSArea(new RSTile(2614, 3171, 0), new RSTile(2619, 3154, 0));
    RSArea BAR_AREA = new RSArea(new RSTile(2572, 3139, 0), new RSTile(2563, 3150, 0));
    RSArea COMBAT_AREA = new RSArea(new RSTile(2605, 3152, 0), new RSTile(2585, 3171, 0));
    RSArea THIRSY_GUARD_AREA = new RSArea(new RSTile(2619, 3139, 0), new RSTile(2612, 3146, 0));
    RSArea JAIL_CELL = new RSArea(new RSTile(2601, 3142, 0), new RSTile(2597, 3144, 0));
    RSArea BEFORE_EXIT_TO_AREA = new RSArea(new RSTile(2612, 3146, 0), new RSTile(2606, 3152, 0));

    RSArea SAFE_AREA1 = new RSArea(new RSTile(2597, 3161, 0), new RSTile(2599, 3160, 0));
    RSArea SAFE_AREA2 = new RSArea(new RSTile(2589, 3163, 0), new RSTile(2599, 3163, 0));

    RSTile safeTile = new RSTile(2599, 3161, 0);
    RSTile safeTile2 = new RSTile(2599, 3163, 0); // for scorpion
    RSTile END_SAFE_TILE = new RSTile(2599, 3163, 0);

    RSTile OUTSIDE_WEST_DOOR = new RSTile(2584, 3141, 0);

    RSArea FIGHT_AREA = new RSArea(new RSTile[]{new RSTile(2607, 3168, 0), new RSTile(2607, 3155, 0), new RSTile(2603, 3151, 0), new RSTile(2585, 3151, 0), new RSTile(2581, 3156, 0), new RSTile(2581, 3168, 0), new RSTile(2586, 3173, 0), new RSTile(2603, 3173, 0)});

    public final RSTile[] PATH_TO_BAR = new RSTile[]{new RSTile(2584, 3141, 0), new RSTile(2583, 3141, 0), new RSTile(2582, 3142, 0), new RSTile(2581, 3143, 0), new RSTile(2580, 3143, 0), new RSTile(2579, 3144, 0), new RSTile(2578, 3145, 0), new RSTile(2577, 3146, 0), new RSTile(2577, 3146, 0), new RSTile(2576, 3147, 0), new RSTile(2576, 3147, 0), new RSTile(2575, 3148, 0), new RSTile(2574, 3149, 0), new RSTile(2574, 3149, 0), new RSTile(2573, 3150, 0), new RSTile(2573, 3150, 0), new RSTile(2572, 3151, 0), new RSTile(2571, 3151, 0), new RSTile(2570, 3152, 0), new RSTile(2570, 3152, 0), new RSTile(2569, 3152, 0), new RSTile(2569, 3152, 0), new RSTile(2569, 3151, 0), new RSTile(2569, 3151, 0), new RSTile(2569, 3150, 0), new RSTile(2569, 3150, 0), new RSTile(2569, 3150, 0), new RSTile(2570, 3150, 0), new RSTile(2570, 3150, 0), new RSTile(2570, 3150, 0), new RSTile(2569, 3150, 0), new RSTile(2568, 3149, 0), new RSTile(2568, 3149, 0), new RSTile(2568, 3149, 0), new RSTile(2567, 3148, 0), new RSTile(2567, 3147, 0), new RSTile(2567, 3147, 0), new RSTile(2567, 3146, 0), new RSTile(2567, 3145, 0), new RSTile(2567, 3145, 0), new RSTile(2567, 3144, 0), new RSTile(2567, 3144, 0), new RSTile(2567, 3143, 0), new RSTile(2567, 3142, 0)};

    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.STAFF_OF_AIR, 1, 100),
                    new GEItem(ItemID.MIND_RUNE, 400, 25),
                    new GEItem(ItemID.CAMELOT_TELEPORT, 5, 50),
                    new GEItem(ItemID.FIRE_RUNE, 1200, 20),
                    new GEItem(ItemID.LOBSTER, 15, 35),
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

    public void getItems() {
        cQuesterV2.status = "Getting items";
        General.println("[Debug]: Withdrawing Quest items.");
        BankManager.open(true);
        BankManager.checkEquippedGlory();
        BankManager.depositAll(true);
        BankManager.withdraw2(5, true,
                ItemID.CAMELOT_TELEPORT);
        BankManager.withdraw2(1, true,
                ItemID.STAFF_OF_AIR);
        BankManager.withdraw2(25, true, COINS);
        BankManager.withdraw2(500, true,
                ItemID.MIND_RUNE);
        BankManager.withdraw2(1500, true,
                ItemID.FIRE_RUNE);
        BankManager.withdraw2(12, true,
                ItemID.LOBSTER);
        BankManager.withdraw2(1, true,
                ItemID.STAMINA_POTION[0]);
        BankManager.withdraw2(1, true,
                ItemID.RING_OF_WEALTH[0]);
        BankManager.close(true);
        Utils.equipItem(staffOfAir);
    }

    public void startQuest() {
        if (!BankManager.checkInventoryItems(ItemID.FIRE_RUNE, ItemID.MIND_RUNE, ItemID.LOBSTER)) {
            buyItems();
            getItems();
        }
        cQuesterV2.status = "Starting Quest";
        PathingUtil.walkToArea(startArea);
        if (NpcChat.talkToNPC("Lady Servil")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Can I help you?", "Yes.");
            NPCInteraction.handleConversation();
        }
    }

    public void step2() {
        cQuesterV2.status = "Getting Armour";
        if (!ARMOUR_AREA.contains(Player.getPosition()) && !ARMOUR_AREA_DOOR.contains(Player.getPosition())) {
            PathingUtil.walkToArea(ARMOUR_AREA_DOOR);
            if (Utils.clickObject(1535, "Open", false)) {
                Timer.waitCondition(() -> Objects.findNearest(20, 1536).length > 0, 6000);
                General.sleep(General.randomSD(250, 2500, 1200, 400));
            }
        }
        if (!ARMOUR_AREA.contains(Player.getPosition()) && ARMOUR_AREA_DOOR.contains(Player.getPosition())) {
            PathingUtil.clickScreenWalk(ARMOUR_AREA.getRandomTile());
            Timer.waitCondition(() -> ARMOUR_AREA.contains(Player.getPosition()), 8000, 12000);
        }
        if (ARMOUR_AREA.contains(Player.getPosition())) {
            if (Utils.clickObject(75, "Open", false))
                Timer.waitCondition(() -> Objects.findNearest(20, 76).length > 0, 6000, 8000);

            if (Utils.clickObject(76, "Search", false)) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                General.sleep(General.randomSD(250, 2500, 1200, 400));
            }
        }
    }

    public void step3() {
        if (Utils.equipItem(HELMET))
            General.sleep(General.randomSD(100, 1000, 400, 200));

        if (Utils.equipItem(CHEST_ARMOUR))
            General.sleep(General.randomSD(250, 2500, 1200, 400));

        if (ARMOUR_AREA.contains(Player.getPosition())) {
            if (Utils.clickObject(1535, "Open", false))
                Timer.waitCondition(() -> Objects.findNearest(20, 1536).length > 0, 6000);

            if (PathingUtil.localNavigation(DOOR_ENTRANCE.getRandomTile()))
                PathingUtil.movementIdle();

        }
        if (!DOOR_ENTRANCE.contains(Player.getPosition()) && !NORTH_JAIL_AREA.contains(Player.getPosition()) && !THIRSY_GUARD_AREA.contains(Player.getPosition()) && !BEFORE_THIRSTY_GUARD_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Going to entrance";
            General.println("[Debug]: " + cQuesterV2.status);
            if (PathingUtil.walkToTile(new RSTile(2617, 3172), 1, false))
                Timer.waitCondition(() -> DOOR_ENTRANCE.contains(Player.getPosition()), 8000);
        }
        if (DOOR_ENTRANCE.contains(Player.getPosition())) {
            if (Utils.clickObject("Door", "Open", false)) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                Timer.waitCondition(() -> INSIDE_DOOR.contains(Player.getPosition()), 6000);
                General.sleep(General.randomSD(250, 2500, 1200, 400));
            }
        }
        if (BEFORE_EXIT_TO_AREA.contains(Player.getPosition())) {
            if (PathingUtil.localNavigation(new RSTile(2612, 3150, 0)))
                PathingUtil.movementIdle();

        }
        if (NORTH_JAIL_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Talking to Sammy";
            General.println("[Debug]: " + cQuesterV2.status);
            if (Utils.clickNPC("Sammy Servil", "Talk-to")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
        }

        cQuesterV2.status = "Going to Guard";
        General.println("[Debug]: " + cQuesterV2.status);
        if (PathingUtil.localNavigation(new RSTile(2615, 3142, 0)))
            PathingUtil.movementIdle();
        //  Timer.waitCondition(() -> BEFORE_THIRSTY_GUARD_AREA.contains(Player.getPosition()) && !Player.isMoving(), 7000);

        cQuesterV2.status = "Talking to Guard";
        General.println("[Debug]: " + cQuesterV2.status);
        if (THIRSY_GUARD_AREA.contains(Player.getPosition())) {
            NpcChat.talkToNPC(1209); // don't wrap this one in an if statement, it messes it up
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            NPCInteraction.handleConversation();
        }
    }

    RSArea OUTSIDE_DOOR_1 = new RSArea(new RSTile(2608, 3141, 0), new RSTile(2607, 3145, 0));
    RSArea BEFORE_DOOR_1 = new RSArea(new RSTile(2609, 3145, 0), new RSTile(2610, 3141, 0));
    RSArea WEST_EXIT_DOOR = new RSArea(new RSTile(2585, 3143, 0), new RSTile(2586, 3139, 0));
    RSArea OUTSIDE_WEST_EXIT = new RSArea(new RSTile(2584, 3142, 0), new RSTile(2582, 3140, 0));
    RSArea OUTSIDE_BAR_DOOR = new RSArea(new RSTile(2568, 3151, 0), new RSTile(2570, 3152, 0));
    RSArea INSIDE_BAR_DOOR = new RSArea(new RSTile(2567, 3150, 0), new RSTile(2570, 3149, 0));
    RSObject[] door = Objects.findNearest(10, 1535);
    RSArea BEFORE_THIRSTY_GUARD_AREA = new RSArea(new RSTile(2618, 3147, 0), new RSTile(2614, 3148, 0));
    ArrayList<RSTile> tilePathToBar = new ArrayList<RSTile>(Arrays.asList(PATH_TO_BAR));
    RSArea LARGER_OUTSIDE_WEST_EXIT = new RSArea(new RSTile(2584, 3138, 0), new RSTile(2582, 3144, 0));


    public void step4() {
        cQuesterV2.status = "Going to Bar";
        General.println("[Debug]: " + cQuesterV2.status);
        if (!BAR_AREA.contains(Player.getPosition()) && Inventory.find(khaliBrew).length < 1) {
            if (THIRSY_GUARD_AREA.contains(Player.getPosition())) {
                Walking.blindWalkTo(new RSTile(2610, 3143, 0));
                Timer.waitCondition(() -> BEFORE_DOOR_1.contains(Player.getPosition()), 9000);
                door = Objects.findNearest(10, 1535);
                if (door.length > 0) {
                    AccurateMouse.click(door[0], "Open");
                    Timer.waitCondition(() -> Objects.findNearest(5, 1535).length < 1, 7000);
                    Walking.blindWalkTo(OUTSIDE_DOOR_1.getRandomTile());
                    Timer.waitCondition(() -> OUTSIDE_DOOR_1.contains(Player.getPosition()), 9000);
                    General.sleep(General.randomSD(250, 1000, 600, 200));
                }
            }
            if (OUTSIDE_DOOR_1.contains(Player.getPosition()) || WEST_EXIT_DOOR.contains(Player.getPosition())) {
                Walking.blindWalkTo(new RSTile(2585, 3141, 0));
                Timer.waitCondition(() -> WEST_EXIT_DOOR.contains(Player.getPosition()), 9000);
                door = Objects.findNearest(10, 81);
                if (door.length > 0) {
                    AccurateMouse.click(door[0], "Open");
                    Timer.waitCondition(() -> OUTSIDE_WEST_EXIT.contains(Player.getPosition()), 7000);
                    General.sleep(General.randomSD(250, 1000, 600, 200));
                }
            }
            if (OUTSIDE_WEST_EXIT.contains(Player.getPosition())) {
                WalkerEngine.getInstance().walkPath(tilePathToBar);
                General.sleep(500, 4000);
            }
        }
        if ((INSIDE_BAR_DOOR.contains(Player.getPosition()) || BAR_AREA.contains(Player.getPosition())) && Inventory.find(khaliBrew).length < 1) {
            cQuesterV2.status = "Talking to Barman";
            General.println("[Debug]: " + cQuesterV2.status);
            if (NPCs.find("Khazard barman").length > 0) {
                Walking.blindWalkTo(NPCs.find("Khazard barman")[0].getPosition());
                Timer.waitCondition(() -> NPCs.find("Khazard barman")[0].getPosition().distanceTo(Player.getPosition()) < 5, 9000);
                General.sleep(General.randomSD(250, 1000, 600, 200));
            }
            if (NpcChat.talkToNPC("Khazard barman")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("I'd like a Khali brew please.");
                NPCInteraction.handleConversation();
                General.sleep(General.randomSD(250, 1000, 600, 200));
            }
        }
        if (INSIDE_BAR_DOOR.contains(Player.getPosition()) || BAR_AREA.contains(Player.getPosition()) && Inventory.find(khaliBrew).length > 0) {
            cQuesterV2.status = "Returning to guard";
            General.println("[Debug]: " + cQuesterV2.status);
            Walking.blindWalkTo(INSIDE_BAR_DOOR.getRandomTile());
            Timer.waitCondition(() -> INSIDE_BAR_DOOR.contains(Player.getPosition()), 9000);
            General.sleep(General.random(500, 2000));
            door = Objects.findNearest(10, 1535);
            if (door.length > 0) {
                if (AccurateMouse.click(door[0], "Open"))
                    Timer.waitCondition(() -> Objects.findNearest(10, 1536).length > 0, 7000);
                General.sleep(General.randomSD(250, 1000, 600, 200));
            }
            Walking.blindWalkTo(OUTSIDE_WEST_DOOR);
            Timer.waitCondition(() -> OUTSIDE_WEST_EXIT.contains(Player.getPosition()), 7000);
            General.sleep(General.randomSD(250, 1000, 600, 200));
        }
        if ((OUTSIDE_WEST_EXIT.contains(Player.getPosition()) || LARGER_OUTSIDE_WEST_EXIT.contains(Player.getPosition())) && Inventory.find(khaliBrew).length > 0) {
            cQuesterV2.status = "Returning to guard";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToTile(new RSTile(2583, 3141), 1, false);

            door = Objects.findNearest(5, 81);
            if (door.length > 0) {
                AccurateMouse.click(door[0], "Open");
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                Timer.waitCondition(() -> WEST_EXIT_DOOR.contains(Player.getPosition()), 7000);
                General.sleep(General.randomSD(250, 1000, 600, 200));

            }
        }
        if (WEST_EXIT_DOOR.contains(Player.getPosition()) && Inventory.find(khaliBrew).length > 0) {
            cQuesterV2.status = "Returning to guard";
            General.println("[Debug]: " + cQuesterV2.status);
            if (PathingUtil.localNavigation(OUTSIDE_DOOR_1.getRandomTile()))
                Timer.waitCondition(() -> OUTSIDE_DOOR_1.contains(Player.getPosition()), 7000);

            door = Objects.findNearest(5, 1535);
            if (door.length > 0) {
                AccurateMouse.click(door[0], "Open");
                Timer.waitCondition(() -> WEST_EXIT_DOOR.contains(Player.getPosition()), 7000);
                General.sleep(General.randomSD(250, 1000, 600, 200));
            }
        }
        if (OUTSIDE_DOOR_1.contains(Player.getPosition()) && Inventory.find(khaliBrew).length > 0) {
            cQuesterV2.status = "Returning to guard";
            General.println("[Debug]: " + cQuesterV2.status);
            if (PathingUtil.localNavigation(new RSTile(2615, 3143)))
                Timer.waitCondition(() -> THIRSY_GUARD_AREA.contains(Player.getPosition()), 7000);
        }

        cQuesterV2.status = "Talking to Guard";
        General.println("[Debug]: " + cQuesterV2.status);
        if (THIRSY_GUARD_AREA.contains(Player.getPosition())) {
            if (NpcChat.talkToNPC(1209)) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                NPCInteraction.handleConversation();
            }
        }
    }

    RSArea WHOLE_THIRSTY_GUARD_AREA = new RSArea(new RSTile(2608, 3146, 0), new RSTile(2619, 3139, 0));

    public void step5() {
        if (WHOLE_THIRSTY_GUARD_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Going back to Sammy";
            General.println("[Debug]: " + cQuesterV2.status);
          /*    Walking.blindWalkTo(new RSTile(2616, 3146, 0));
            General.sleep(General.random(2000, 4000));
  */
            if (PathingUtil.localNavigation(new RSTile(2618, 3168, 0)))
                Timer.slowWaitCondition(() -> NORTH_JAIL_AREA.contains(Player.getPosition()), 6000, 7000);
        }

        if (NORTH_JAIL_AREA.contains(Player.getPosition())) {
            Utils.equipItem(staffOfAir);
            Autocast.enableAutocast(Autocast.FIRE_STRIKE);
            if (Autocast.isAutocastEnabled(Autocast.FIRE_STRIKE)) {
                cQuesterV2.status = "Talking to Sammy";
                if (Utils.clickNPC("Sammy Servil", "Talk-to")) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();

                    General.sleep(3000, 6000);
                    Timer.waitCondition(() -> COMBAT_AREA.contains(Player.getPosition()), 25000);
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                    NPCInteraction.handleConversation();

                    General.sleep(3000, 6000);
                }
            }
        } else {
            if (PathingUtil.localNavigation(new RSTile(2618, 3168, 0)))
                PathingUtil.movementIdle();
        }

    }

    public void killNPC(RSNPC[] npc) {
        if (!SAFE_AREA1.contains(Player.getPosition()) && !SAFE_AREA2.contains(Player.getPosition()) && !safeTile.equals(Player.getPosition())) {
            Walking.blindWalkTo(safeTile);
            Timer.waitCondition(() -> SAFE_AREA1.contains(Player.getPosition()), 8000);
        }
        if (SAFE_AREA1.contains(Player.getPosition()) || SAFE_AREA2.contains(Player.getPosition())) {


            while (npc.length > 0) {
                int id = npc[0].getID();
                General.sleep(40, 75);
                Autocast.enableAutocast(Autocast.FIRE_STRIKE);

                if (Combat.getHPRatio() < General.random(40, 65))
                    EatUtil.eatFood();

                if (!npc[0].isInCombat()) {
                    General.println("[Debug]: Attacking Npc");
                    Utils.shortSleep();

                    if (!npc[0].isClickable())
                        DaxCamera.focus(npc[0]);

                    RSNPC[] finalNpc = npc;
                    if (AccurateMouse.click(npc[0], "Attack"))
                        Timer.waitCondition(() -> finalNpc[0].isInCombat(), 5000, 7500);

                    General.sleep(400, 2000);
                }

                if ((npc[0].getPosition().distanceTo(Player.getPosition()) <= 2) &&
                        safeTile.equals(Player.getPosition())) {
                    General.println("[Debug]: Safe Tile 1 seems to have failed, moving.");
                    Walking.clickTileMS(safeTile2, "Walk here");
                    General.sleep(1400, 2000);
                }
                if ((npc[0].getPosition().distanceTo(Player.getPosition()) <= 2) &&
                        safeTile2.equals(Player.getPosition())) {
                    General.println("[Debug]: Safe Tile 2 seems to have failed, moving.");
                    Walking.clickTileMS(safeTile, "Walk here");
                    General.sleep(1400, 2000);
                }
                if (npc[0].getHealthPercent() == 0) {
                    break;
                }
                npc = Entities.find(NpcEntity::new)
                        .idEquals(id)
                        .inArea(FIGHT_AREA)
                        .getResults();
            }
        }
    }


    public void killNPC(int NPCName) {
        if (!Game.isRunOn())
            Options.setRunEnabled(true);

        if (!SAFE_AREA1.contains(Player.getPosition()) && !SAFE_AREA2.contains(Player.getPosition()) && !safeTile.equals(Player.getPosition())) {
            Walking.blindWalkTo(safeTile);
            Timer.waitCondition(() -> SAFE_AREA1.contains(Player.getPosition()), 8000);
        }
        if (SAFE_AREA1.contains(Player.getPosition()) || SAFE_AREA2.contains(Player.getPosition())) {
            RSNPC[] npc = Entities.find(NpcEntity::new)
                    .idEquals(NPCName)
                    .inArea(FIGHT_AREA)
                    .getResults();
            killNPC(npc);
        }
    }

    public void killNPC(String NPCName) {
        if (!Game.isRunOn())
            Options.setRunEnabled(true);

        if (!SAFE_AREA1.contains(Player.getPosition()) && !SAFE_AREA2.contains(Player.getPosition())
                && !safeTile.equals(Player.getPosition())) {
            Walking.blindWalkTo(safeTile);
            Timer.waitCondition(() -> SAFE_AREA1.contains(Player.getPosition()), 6000,7000);
        }

        if (SAFE_AREA1.contains(Player.getPosition()) || SAFE_AREA2.contains(Player.getPosition())) {
            General.println("[Debug]: In safe area");
            RSNPC[] npc = Entities.find(NpcEntity::new)
                    .nameContains(NPCName)
                    .inArea(FIGHT_AREA)
                    .getResults();

            if (npc.length == 0){
                // there's no npc in the fight area, talk to the kid to initiate the cut scene
                // usually only happens on bouncer
                if (Utils.clickNPC("Sammy Servil", "Talk-to")) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                }
                npc = Entities.find(NpcEntity::new)
                        .nameContains(NPCName)
                        .inArea(FIGHT_AREA)
                        .getResults();
            }

            killNPC(npc);
        }
    }


    public void killScoprion(int NPCName) {
        if (!SAFE_AREA1.contains(Player.getPosition()) && !SAFE_AREA2.contains(Player.getPosition())) {
            Walking.blindWalkTo(safeTile);
            Timer.waitCondition(() -> SAFE_AREA1.contains(Player.getPosition()), 8000);
        }
        if (SAFE_AREA1.contains(Player.getPosition()) || SAFE_AREA2.contains(Player.getPosition())) {
            General.println("[Debug]: in safe area");

            RSNPC[] NPC = NPCs.findNearest(NPCName);

            while (NPC.length > 0 && FIGHT_AREA.contains(NPC[0].getPosition())) {
                General.sleep(50);

                if (!NPC[0].isInCombat()) {
                    General.println("Attacking");
                    Utils.shortSleep();
                    if (!NPC[0].isClickable())
                        NPC[0].adjustCameraTo();

                    if (AccurateMouse.click(NPC[0], "Attack"))
                        Timer.waitCondition(() -> NPC[0].isInCombat(), 8000, 12000);

                } else if (Combat.getHPRatio() < General.random(40, 65))
                    EatUtil.eatFood();

                else if ((NPC[0].getPosition().distanceTo(Player.getPosition()) <= 2) && safeTile.equals(Player.getPosition())) {
                    General.println("[Debug]: Safe Tile 1 seems to have failed, moving.");
                    Walking.clickTileMS(safeTile2, "Walk here");
                    General.sleep(General.random(1000, 2500));
                }
                if ((NPC[0].getPosition().distanceTo(Player.getPosition()) <= 2) && safeTile2.equals(Player.getPosition())) {
                    General.println("[Debug]: Safe Tile 2 seems to have failed, moving.");
                    Walking.clickTileMS(safeTile, "Walk here");
                    General.sleep(General.random(1000, 2000));
                }
                if (NPC[0].getHealthPercent() == 0) {
                    break;
                }
            }
        }
    }

    public void step6() {
        if (COMBAT_AREA.contains(Player.getPosition())) {
            NPCInteraction.handleConversation();
            Walking.blindWalkTo(safeTile);
            Timer.waitCondition(() -> SAFE_AREA1.contains(Player.getPosition()), 8000);
            General.sleep(General.randomSD(250, 1000, 600, 200));
        }
        cQuesterV2.status = "Attacking Ogre";
        General.println("[Debug]: " + cQuesterV2.status);
        killNPC(1225);
    }

    public void step7() {
        if (!JAIL_CELL.contains(Player.getPosition()) && !COMBAT_AREA.contains(Player.getPosition())) {
            General.println("Waiting");
            Timer.waitCondition(() -> JAIL_CELL.contains(Player.getPosition()), 25000);
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }
        if (JAIL_CELL.contains(Player.getPosition())) {
            cQuesterV2.status = "Talking to Hengrad";
            General.println("[Debug]: " + cQuesterV2.status);
            if (NpcChat.talkToNPC("Hengrad")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                NPCInteraction.handleConversation();
                Timer.waitCondition(() -> COMBAT_AREA.contains(Player.getPosition()), 32000);
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
        }
    }

    public void killScoprion() {
        if (!COMBAT_AREA.contains(Player.getPosition())) {
            returnToArena();
        }
        if (COMBAT_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Killing Scorpion";
            General.println("[Debug]: " + cQuesterV2.status);
            NPCInteraction.handleConversation();
            General.sleep(1500, 3000);
            Walking.blindWalkTo(safeTile2);
            Timer.waitCondition(() -> Player.getPosition().equals(safeTile2), 8000);
        }
        killScoprion(1226);
    }

    public void returnToArena() {
        if (!DOOR_ENTRANCE.contains(Player.getPosition()) && !NORTH_JAIL_AREA.contains(Player.getPosition()) && !THIRSY_GUARD_AREA.contains(Player.getPosition()) && !BEFORE_THIRSTY_GUARD_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Returning to Fight Arena";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToTile(new RSTile(2617, 3172), 1, false);
            Timer.waitCondition(() -> DOOR_ENTRANCE.contains(Player.getPosition()), 8000, 12000);
        }
        if (Utils.clickObject("Door", "Open", false)) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            Timer.waitCondition(() -> COMBAT_AREA.contains(Player.getPosition()), 8000, 12000);

        }
    }

    public void killBouncer() {
        if (!COMBAT_AREA.contains(Player.getPosition())) {
            returnToArena();
        }

        if (COMBAT_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Killing Bouncer";
            General.println("[Debug]: " + cQuesterV2.status);
            if (NPCInteraction.isConversationWindowUp())
                NPCInteraction.handleConversation();
            //General.sleep(2500, 4000);
            if (!Player.getPosition().equals(safeTile))
                Walking.blindWalkTo(safeTile);
            Timer.waitCondition(() -> SAFE_AREA1.contains(Player.getPosition()), 8000);
        }
        killNPC("Bouncer");
    }

    public void finishQuest() {
        cQuesterV2.status = "Finishing Quest";
        General.println("[Debug]: " + cQuesterV2.status);
        if (PathingUtil.clickScreenWalk(END_SAFE_TILE))
            Utils.shortSleep();

        Utils.equipItem(ItemID.RING_OF_WEALTH[0]);

        if (COMBAT_AREA.contains(Player.getPosition()) && NPCInteraction.isConversationWindowUp())
            NPCInteraction.handleConversation();

        PathingUtil.walkToArea(startArea);

        if (startArea.contains(Player.getPosition())) {
            NpcChat.talkToNPC("Lady Servil");
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }
    }

    int GAME_SETTING = 17;

    @Override
    public void execute() {

        if (Game.getSetting(17) == 0) {
            buyItems();
            getItems();
            startQuest();
        }
        if (Game.getSetting(17) == 1) {
            step2();
        }
        if (Game.getSetting(17) == 2) {
            step3();
        }
        if (Game.getSetting(17) == 3) {
            step4();
            Utils.longSleep();
        }
        if (Game.getSetting(17) == 4) {

        }
        if (Game.getSetting(17) == 5) {
            step5();
        }
        if (Game.getSetting(17) == 6) {
            step6();
        }
        if (Game.getSetting(17) == 7) {
            step6();
        }
        if (Game.getSetting(17) == 8) {
            if (NpcChat.talkToNPC("Justin Servil")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
        }
        if (Game.getSetting(17) == 9) {
            step7();
            killScoprion();
        }
        if (Game.getSetting(17) == 10) {
            killBouncer();
        }
        if (Game.getSetting(17) == 11) {
            finishQuest();
        }
        if (Game.getSetting(17) == 12) {
            finishQuest();
        }
        if (Game.getSetting(17) == 14) {
            Utils.closeQuestCompletionWindow();
            Utils.continuingChat();
            cQuesterV2.taskList.remove(FightArena.get());
        }

    }

    @Override
    public String questName() {
        return "Fight arena";
    }

    @Override
    public boolean checkRequirements() {
        return false;
    }


    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return
                cQuesterV2.taskList.get(0).equals(FightArena.get());
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
