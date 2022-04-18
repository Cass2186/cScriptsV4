package scripts.QuestPackages.WaterfallQuest;

import dax.api_lib.DaxWalker;
import dax.api_lib.models.RunescapeBank;
import dax.walker.utils.AccurateMouse;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.Quest;
import scripts.GEManager.Exchange;
import scripts.GEManager.GEItem;
import scripts.*;
import scripts.QuestPackages.WitchsHouse.WitchsHouse;
import scripts.QuestPackages.WitchsPotion.WitchsPotion;
import scripts.QuestSteps.*;
import scripts.Requirements.*;
import scripts.Tasks.Priority;

import java.awt.event.KeyEvent;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WaterfallQuest implements QuestTask {


    private static WaterfallQuest quest;

    public static WaterfallQuest get() {
        return quest == null ? quest = new WaterfallQuest() : quest;
    }


    //Items Required
    //Items Recommended
    ItemReq gamesNecklace, food;

    AreaRequirement inGnomeBasement, inGlarialTomb, inFalls, onHudonIsland, onDeadTreeIsland, onLedge, inUpstairsInHouse,
            inGolrieRoom, gotPebble, inEndRoom, inEnd2;


    /*   public Map<Integer, QuestStep> loadSteps() {
           Quests.WATERFALL_QUEST.getGameSetting();
           loadRSAreas();
           setupItemReqs();
           setupConditions();
           setupSteps();
           Map<Integer, QuestStep> steps = new HashMap<>();

           steps.put(0, talkToAlmera);

           ConditionalStep goTalkToHudon = new ConditionalStep(boardRaft);
           goTalkToHudon.addStep(onHudonIsland, talkToHudon);

           steps.put(1, goTalkToHudon);

           ConditionalStep goReadBook = new ConditionalStep(goUpstairsHadley);
           goReadBook.addStep(book, readBook);
           goReadBook.addStep(inUpstairsInHouse, searchBookcase);
           goReadBook.addStep(onLedge, getInBarrel);
           goReadBook.addStep(onDeadTreeIsland, useRopeOnTree);
           goReadBook.addStep(onHudonIsland, useRopeOnRock);

           steps.put(2, goReadBook);

           // TODO: Add lines to guide through maze
           goGetPebble = new ConditionalStep(enterGnomeDungeon);
           goGetPebble.addStep(inGolrieRoom, talkToGolrie);
           goGetPebble.addStep(new Conditions(inGnomeBasement, key), enterGnomeDoor);
           goGetPebble.addStep(inGnomeBasement, searchGnomeCrate);
           goGetPebble.setLockingCondition(gotPebble);

           getGlarialStuff = new ConditionalStep(usePebble);
           getGlarialStuff.addStep(new Conditions(glarialsAmulet.alsoCheckBank(questBank), inGlarialTomb), searchGlarialCoffin);
           getGlarialStuff.addStep(inGlarialTomb, searchGlarialChest);
           getGlarialStuff.setLockingCondition(new Conditions(new Conditions(glarialsAmulet.alsoCheckBank(questBank), glarialsUrn.alsoCheckBank(questBank))));

           ConditionalStep puttingToRest = new ConditionalStep(getFinalItems);
           puttingToRest.addStep(inEnd2, useUrnOnChalice);
           puttingToRest.addStep(inEndRoom, useRunes);
           puttingToRest.addStep(new Conditions(inFalls, baxKey), useKeyOnFallsDoor);
           puttingToRest.addStep(inFalls, searchFallsCrate);
           puttingToRest.addStep(onLedge, enterFalls);
           puttingToRest.addStep(onDeadTreeIsland, useRopeOnTreeFinal);
           puttingToRest.addStep(onHudonIsland, useRopeOnRockFinal);
           puttingToRest.addStep(new Conditions(glarialsUrn, glarialsAmulet, airRunes, earthRunes, waterRunes, rope), boardRaftFinal);

           ConditionalStep finishingSteps = new ConditionalStep(goGetPebble);
           finishingSteps.addStep(new Conditions(glarialsUrn.alsoCheckBank(questBank), glarialsAmulet.alsoCheckBank(questBank)), puttingToRest);
           finishingSteps.addStep(gotPebble, getGlarialStuff);

           steps.put(3, finishingSteps);
           steps.put(4, finishingSteps);
           steps.put(5, puttingToRest);
           steps.put(6, puttingToRest);
           steps.put(7, puttingToRest); // 7 didn't occur during testing
           steps.put(8, puttingToRest);

           return steps;
       }
   */

    ItemReq highlightRope = new ItemReq("Rope", ItemID.ROPE);
    ItemReq rope = new ItemReq("Rope", ItemID.ROPE);

    ItemReq book = new ItemReq("Book on baxtorian", ItemID.BOOK_ON_BAXTORIAN);
    ItemReq glarialsPebble = new ItemReq("Glarial's pebble", ItemID.GLARIALS_PEBBLE);
    //glarialsPebble.setTooltip("You can get another from Golrie under the Tree Gnome Village");
    ItemReq glarialsUrn = new ItemReq("Glarial's urn", ItemID.GLARIALS_URN);
    // glarialsUrn.setTooltip("You can get another from the chest in Glarial's tomb");
    ItemReq glarialsAmulet = new ItemReq(ItemID.GLARIALS_AMULET);
    // glarialsAmulet.setTooltip("You can get another from the chest in Glarial's tomb");
    ItemReq unequippedAmulet = new ItemReq("Glarial's amulet", ItemID.GLARIALS_AMULET);
    ItemReq key = new ItemReq("Key", 293);
    ItemReq baxKey = new ItemReq("Key", 298);

    ItemReq airRunes = new ItemReq("Air runes", ItemID.AIR_RUNE, 6);
    ItemReq airRune = new ItemReq("Air rune", ItemID.AIR_RUNE);
    ItemReq earthRunes = new ItemReq("Earth runes", ItemID.EARTH_RUNE, 6);
    ItemReq earthRune = new ItemReq("Earth rune", ItemID.EARTH_RUNE);
    ItemReq waterRunes = new ItemReq("Water runes", ItemID.WATER_RUNE, 6);
    ItemReq waterRune = new ItemReq("Water rune", ItemID.WATER_RUNE);

    //   gamesNecklace = new ItemReq("Games necklace", ItemCollections.getGamesNecklaces());
    //   food = new ItemReq("Food", ItemCollections.getGoodEatingFood(), -1);



        RSArea  gnomeBasement = new RSArea(new RSTile(2497, 9552, 0), new RSTile(2559, 9593, 0));
        RSArea  glarialTomb = new RSArea(new RSTile(2524, 9801, 0), new RSTile(2557, 9849, 0));
        RSArea  golrieRoom = new RSArea(new RSTile(2502, 9576, 0), new RSTile(2523, 9593, 0));
        RSArea  hudonIsland = new RSArea(new RSTile(2510, 3476, 0), new RSTile(2515, 3482, 0));
        RSArea  deadTreeIsland = new RSArea(new RSTile(2512, 3465, 0), new RSTile(2513, 3475, 0));
        RSArea  ledge = new RSArea(new RSTile(2510, 3462, 0), new RSTile(2513, 3464, 0));
        RSArea  upstairsInHouse = new RSArea(new RSTile(2516, 3424, 1), new RSTile(2520, 3431, 1));
        RSArea  falls = new RSArea(new RSTile(2556, 9861, 0), new RSTile(2595, 9920, 0));
        RSArea   endRoom = new RSArea(new RSTile(2561, 9902, 0), new RSTile(2570, 9917, 0));
        RSArea   end2 = new RSArea(new RSTile(2599, 9890, 0), new RSTile(2608, 9916, 0));


    public void setupConditions() {
        onDeadTreeIsland = new AreaRequirement(deadTreeIsland);
        onHudonIsland = new AreaRequirement(hudonIsland);
        onLedge = new AreaRequirement(ledge);
        inUpstairsInHouse = new AreaRequirement(upstairsInHouse);
        inGnomeBasement = new AreaRequirement(gnomeBasement);
        inGolrieRoom = new AreaRequirement(golrieRoom);
        inGlarialTomb = new AreaRequirement(glarialTomb);
        inFalls = new AreaRequirement(falls);
        inEndRoom = new AreaRequirement(endRoom);
        inEnd2 = new AreaRequirement(end2);
        //      gotPebble = new VarbitRequirement(9110, 1);
    }


    public void getPebble() {
        if (!gnomeBasement.contains(Player.getPosition()) && !glarialsPebble.check()) {
            cQuesterV2.status = "Getting Pebble";
            PathingUtil.walkToTile(new RSTile(2533, 3156, 0), 2, false);
            if (Utils.clickObj(5250, "Climb-down", true))
                Timer.waitCondition(() -> gnomeBasement.contains(Player.getPosition()), 12000, 15000);
        }

       /* if (undergroundArea1.contains(Player.getPosition()) && !key.check() && !glarialsPebble.check()) {
            Walking.blindWalkTo(crateTile);
            Timer.waitCondition(() -> crateArea.contains(Player.getPosition()), 12000, 15000);
            Utils.shortSleep();
            checkEat();
            if (Utils.clickObj()(1990, "Search"))
                Timer.waitCondition(() -> Inventory.find(key).length > 0, 12000, 15000);
            checkEat();
        }
        if (undergroundArea1.contains(Player.getPosition()) && (Inventory.find(key).length > 0 || Inventory.find(pebble).length < 1) && !golrieArea.contains(Player.getPosition())) {
            if (Walking.blindWalkTo(new RSTile(2535, 9554, 0))) // don't use Utils class here
                Utils.shortSleep();
            Walking.blindWalkTo(new RSTile(2515, 9575, 0));
            checkEat();
            Timer.waitCondition(() -> underGroundDoorArea.contains(Player.getPosition()), 12000, 15000);
            checkEat();
            if (Utils.clickObj()("Door", "Open"))
                Timer.abc2WaitCondition(() -> golrieArea.contains(Player.getPosition()), 12000, 15000);

            checkEat();
        }
        if (undergroundArea1.contains(Player.getPosition()) && Inventory.find(key).length > 0 && golrieArea.contains(Player.getPosition()) && Inventory.find(pebble).length < 1) {
            cQuesterV2.status  = "Talking to Golrie";
            Utils.talkToNPC(GOLRIE_ID); // don't wrap in an if function, breaks it.
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }*/
    }


    NPCStep talkToAlmera = new NPCStep("Almera", new RSTile(2521, 3495, 0),
            new String[]{"How can I help?", "Yes."});
    ObjectStep boardRaft = new ObjectStep(ObjectID.LOG_RAFT,
            new RSTile(2509, 3494, 0), "Board");

    NPCStep talkToHudon = new NPCStep(NpcID.HUDON, new RSTile(2511, 3484, 0));
    UseItemOnObjectStep useRopeOnRock = new UseItemOnObjectStep(ItemID.ROPE, ObjectID.ROCK,
            new RSTile(2512, 3476, 0),  preLedgeArea.contains(Player.getPosition()),
             highlightRope);

    UseItemOnObjectStep useRopeOnTree = new UseItemOnObjectStep(ItemID.ROPE, ObjectID.DEAD_TREE_2020, new RSTile(2513, 34680),
            "Use a rope on the dead tree.", highlightRope);

    ObjectStep getInBarrel = new ObjectStep(ObjectID.BARREL_2022, new RSTile(2512, 3463, 0), "Get in the barrel.");

    ObjectStep goUpstairsHadley = new ObjectStep(16671, new RSTile(2518, 3430, 0),
            "Climb-up", Player.getPosition().getPlane() == 1);
    ObjectStep searchBookcase = new ObjectStep(ObjectID.BOOKCASE_1989, new RSTile(2519, 3427, 1),
            "Search");
    ClickItemStep readBook = new ClickItemStep(book.getId(), "Read");

    ObjectStep enterGnomeDungeon = new ObjectStep(5250, new RSTile(2533, 3155, 0), "Go to the centre of the Tree Gnome Village and go down the ladder at the entrance.");
    ObjectStep searchGnomeCrate = new ObjectStep(ObjectID.CRATE_1990, new RSTile(2548, 9565, 0), "Search the off-coloured crate in the east room.");
    ObjectStep enterGnomeDoor = new ObjectStep(ObjectID.DOOR_1991, new RSTile(2515, 9575, 0), "Go through the gate in the west room.", key);
    NPCStep talkToGolrie = new NPCStep(NpcID.GOLRIE_4183, new RSTile(2514, 9580, 0));
    ObjectStep usePebble = new ObjectStep(ObjectID.GLARIALS_TOMBSTONE, new RSTile(2559, 3445, 0), "Bank everything besides the pebble and some food. After, go use Glarial's pebble to Glarial's Tombstone east of Baxtorian Falls.", glarialsPebble);


    ObjectStep searchGlarialChest = new ObjectStep(ObjectID.CHEST_1994, new RSTile(2530, 9844, 0), "Search the chest in the western room.");
    ObjectStep searchGlarialCoffin = new ObjectStep(ObjectID.GLARIALS_TOMB, new RSTile(2542, 9812, 0), "Search Glarial's Tomb in the south room.");
    //  ObjectStep getFinalItems = new DetailedQuestStep("Leave Glarial's Tomb and get 6 air, water and earth runes, a rope, glarial's amulet, glarial's urn, a rope, and some food.", airRunes, earthRunes, waterRunes, glarialsAmulet, glarialsUrn, rope);

    ObjectStep boardRaftFinal = new ObjectStep(ObjectID.LOG_RAFT, new RSTile(2509, 3494, 0), "Board the log raft west of Almera.");
    ObjectStep useRopeOnRockFinal = new ObjectStep(ObjectID.ROCK, new RSTile(2512, 3468, 0), "Use a rope on the " +
            "rock to the south.", highlightRope);
    ;
    ObjectStep useRopeOnTreeFinal = new ObjectStep(ObjectID.DEAD_TREE_2020, new RSTile(2513, 3468, 0), "Use a rope on the dead tree.", highlightRope);

    ObjectStep enterFalls = new ObjectStep(ObjectID.DOOR_2010, new RSTile(2511, 3464, 0), "EQUIP Glarial's amulet, then enter the falls.", glarialsAmulet);

    ObjectStep searchFallsCrate = new ObjectStep(ObjectID.CRATE_1999, new RSTile(2589, 9888, 0), "Search the crate in the east room for a key.");
    ObjectStep useKeyOnFallsDoor = new ObjectStep(ObjectID.DOOR_2002, new RSTile(2566, 9901, 0), "Go through the doors from the west room.", baxKey);

    //ObjectStep useRunes = new DetailedQuestStep("Use 1 earth, water and air rune on each of the 6 pillars in the room. Afterwards, use Glarial's amulet on the statue of Glarial.", airRune, waterRune, earthRune);

    ObjectStep useAmuletOnStatue = new ObjectStep(ObjectID.STATUE_OF_GLARIAL, new RSTile(2603, 9915, 0), "Use Glarial's amulet on the Statue of Glarial", unequippedAmulet);


    ObjectStep useUrnOnChalice = new ObjectStep(ObjectID.CHALICE, new RSTile(2604, 9911, 0), "Use Glarial's urn on the Chalice to finish the quest.", glarialsUrn);


    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.LOBSTER, 20, 50),
                    new GEItem(ItemID.AMULET_OF_GLORY[2], 2, 25),
                    new GEItem(ItemID.STAMINA_POTION[0], 3, 20),
                    new GEItem(ItemID.ROPE, 4, 200),
                    new GEItem(ItemID.WATER_RUNE, 10, 20),
                    new GEItem(ItemID.AIR_RUNE, 10, 20),
                    new GEItem(ItemID.EARTH_RUNE, 10, 20),
                    new GEItem(ItemID.AIR_RUNE, 10, 20),
                    new GEItem(ItemID.EARTH_RUNE, 4, 200),
                    new GEItem(ItemID.GAMES_NECKLACE[0], 2, 40),
                    new GEItem(ItemID.RING_OF_DUELING[0], 2, 40),
                    new GEItem(ItemID.RING_OF_WEALTH[0], 2, 40)
            )
    );

    InventoryRequirement startInventory = new InventoryRequirement(new ArrayList<>(
            Arrays.asList(
                    new ItemReq(ItemID.GAMES_NECKLACE[0], 1, 0, true),
                    new ItemReq(ItemID.STAMINA_POTION[0], 1, 0),
                    new ItemReq(ItemID.AMULET_OF_GLORY[2], 1, 0),
                    new ItemReq(ItemID.ROPE, 1, 1),
                    new ItemReq(ItemID.LOBSTER, 8, 2),
                    new ItemReq(ItemID.RING_OF_DUELING[0], 1, 0, true)
            ))
    );

    BuyItemsStep buyInitial = new BuyItemsStep(itemsToBuy);


    public void getItems() {
        if (!startInventory.check()) {
            cQuesterV2.status = "Getting initial items";
            startInventory.withdrawItems();
        }
    }

    public void buyItems() {
        if (Inventory.find(ItemID.LOBSTER).length < 7 || Inventory.find(ItemID.ROPE).length < 1) {
            buyInitial.buyItems();
            Exchange.collectItems();
            startInventory.withdrawItems();
        }
    }

    public boolean goToHudonIsland() {
        if (!onHudonIsland.check()) {
            cQuesterV2.status = "Going to raft";
            boardRaft.execute();
            return Timer.waitCondition(() -> onHudonIsland.check(), 5500, 7500);
        }
        return onHudonIsland.check();
    }

    public boolean goToDeadTreeIsland() {
        if (goToHudonIsland()) {
            cQuesterV2.status = "Using rope on Rock";
            useRopeOnRock.execute();
            return Timer.waitCondition(() -> onDeadTreeIsland.check(), 8500, 11500);
        }
        return onDeadTreeIsland.check();
    }

    public boolean goToCaveEntranceFromIsland() {
        if (goToDeadTreeIsland()) {
            cQuesterV2.status = "Using rope on tree";
            useRopeOnTree.execute();
            return Timer.slowWaitCondition(() -> onLedge.check(), 5500, 7500);
        }
        return onLedge.check();
    }


    public void talkToHudonStep() {
        if (goToDeadTreeIsland()) {
            cQuesterV2.status = "Talking to Hudon";
            talkToHudon.execute();
        }

    }

    public void getBook() {
        if (onLedge.check() && goToCaveEntranceFromIsland()) {
            cQuesterV2.status = "Getting in barrel";
            getInBarrel.execute();
            Timer.slowWaitCondition(() -> !onLedge.check(), 5000, 7000);
        }
        if (Player.getPosition().getPlane() == 0) {
            cQuesterV2.status = "Going upstairs, hadley";
            goUpstairsHadley.execute();
        }
        if (!book.check()) {
            cQuesterV2.status = "Searching bookcase";
            searchBookcase.execute();
            readBook.execute();
        } else {
            readBook.execute();
        }


    }

    public static int pebble = 294;
    public static int necklace = 295;
    public static int urn = 296;
    public static int BOOK = 292;
    public static int key2 = 298;
    public static int lobster = 379;


    public static char spaceKey = KeyEvent.VK_SPACE;


    public static RSTile endOfCrashSite = new RSTile(2512, 3477, 0);
    public static RSTile braxtonHouse = new RSTile(2519, 3431, 0);
    public static RSTile crateTile = new RSTile(2548, 9566, 0);
    public static RSTile glarialsTombstoneTile = new RSTile(2557, 3444, 0);

    public static RSArea startArea = new RSArea(new RSTile(2518, 3498, 0), new RSTile(2523, 3493, 0));
    public static RSArea raftArea = new RSArea(new RSTile(2509, 3495, 0), new RSTile(2513, 3493, 0));
    public static RSArea raftCrashArea = new RSArea(new RSTile(2510, 3481, 0), new RSTile(2513, 3476, 0));
    public static RSArea preLedgeArea = new RSArea(new RSTile(2511, 3469, 0), new RSTile(2514, 3465, 0));
    public static RSArea LEDGE = new RSArea(new RSTile(2509, 3463, 0), new RSTile(2513, 3462, 0));
    public static RSArea braxtonHouseBottomFloor = new RSArea(new RSTile(2511, 3431, 0), new RSTile(2520, 3427, 0));
    public static RSArea braxtonHouseUpperFloor = new RSArea(new RSTile(2516, 3431, 1), new RSTile(2520, 3424, 1));
    public static RSArea underGroundLadderArea = new RSArea(new RSTile(2530, 9558, 0), new RSTile(2536, 9552, 0));
    public static RSArea crateArea = new RSArea(new RSTile(2545, 9568, 0), new RSTile(2548, 9564, 0));
    public static RSArea undergroundArea1 = new RSArea(new RSTile(2502, 9586, 0), new RSTile(2554, 9546, 0));
    public static RSArea underGroundDoorArea = new RSArea(new RSTile(2515, 9575, 0), new RSTile(2515, 9573, 0));
    public static RSArea golrieArea = new RSArea(new RSTile(2522, 9576, 0), new RSTile(2508, 9584, 0));
    public static RSArea tombstoneArea = new RSArea(new RSTile(2553, 3448, 0), new RSTile(2560, 3441, 0));
    public static RSArea undergroundArea2 = new RSArea(new RSTile(2558, 9848, 0), new RSTile(2523, 9807, 0));
    public static RSArea chestArea = new RSArea(new RSTile(2528, 9845, 0), new RSTile(2533, 9842, 0));
    public static RSArea lastCaveArea = new RSArea(new RSTile(2558, 9918, 0), new RSTile(2596, 9860, 0));

    /**
     * Getting the pebble
     */
    public RSArea crateArea2 = new RSArea(new RSTile(2586, 9888, 0), new RSTile(2592, 9885, 0));
    public RSArea endArea = new RSArea(new RSTile(2561, 9904, 0), new RSTile(2570, 9917, 0));
    public RSArea preEndArea = new RSArea(new RSTile(2568, 9894, 0), new RSTile(2565, 9901, 0));
    public RSArea area = new RSArea(new RSTile(2567, 9893, 0), new RSTile(2569, 9892, 0));

    public RSArea FINAL_ROOM = new RSArea(new RSTile(2571, 9902, 0), new RSTile(2560, 9917, 0));


    public RSObject[] logRaft = Objects.findNearest(20, "Log raft");
    public RSObject[] pillars = Objects.findNearest(20, 2005);
    public RSObject[] stairs = Objects.findNearest(20, "Staircase");
    public RSObject[] crate = Objects.findNearest(20, 358);
    public RSObject[] door = Objects.findNearest(20, "Door");
    public RSObject[] chest = Objects.findNearest(20, "Chest");

    public int GOLRIE_ID = 4183;

    /*public void getItems() {
        if (Inventory.find(lobster).length < 7 || Inventory.find(rope.getId()).length < 1) {
            cQuesterV2.status = "Getting Items";
            General.println("[Debug]: Getting Items");
            BankManager.open(true);
            BankManager.checkEquippedGlory();
            BankManager.depositAll(true);
            BankManager.withdraw(1, true,
                    ItemID.GAMES_NECKLACE[0]);
            BankManager.withdraw(1, true, rope.getId());
            BankManager.withdraw(8, true, lobster);
            BankManager.withdraw(1, true,
                    ItemID.STAMINA_POTION[0]);
            BankManager.withdraw(1, true,
                    ItemID.RING_OF_DUELING[0]);

            Utils.idle(200, 1500);
            BankManager.close(true);
            Utils.longSleep();
        }
    }*/

    public void startQuest() {
        cQuesterV2.status = "Starting Quest";
        PathingUtil.walkToArea(startArea);
        if (NpcChat.talkToNPC("Almera")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("How can I help?", "Yes.");
            NPCInteraction.handleConversation();
            Utils.modSleep();
        }
    }

    public static void talkToHudon() {
        cQuesterV2.status = "=Talking to Hudon";
        if (!raftArea.contains(Player.getPosition()) && !raftCrashArea.contains(Player.getPosition())) {
            PathingUtil.walkToArea(raftArea);
        }
        if (raftArea.contains(Player.getPosition())) {
            RSObject[] logRaft = Objects.findNearest(20, "Log raft");

            if (logRaft.length > 0)
                if (AccurateMouse.click(logRaft[0], "Board"))
                    Timer.abc2WaitCondition(() -> raftCrashArea.contains(Player.getPosition()), 8000, 12000);

        }
        if (raftCrashArea.contains(Player.getPosition())) {
            if (NpcChat.talkToNPC("Hudon")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                Utils.longSleep();
            }
        }
    }

    public static RSArea BOTTOM_OF_BRAXTORIAN_HOUSE = new RSArea(new RSTile[]{new RSTile(2521, 3435, 0), new RSTile(2521, 3428, 0), new RSTile(2521, 3423, 0), new RSTile(2517, 3424, 0), new RSTile(2517, 3427, 0), new RSTile(2511, 3427, 0), new RSTile(2511, 3432, 0), new RSTile(2516, 3432, 0), new RSTile(2516, 3435, 0)});

    public static void goToBook() {
        cQuesterV2.status = "Getting Book";
        if (raftCrashArea.contains(Player.getPosition())) {
            PathingUtil.localNavigation(endOfCrashSite);
            General.sleep(General.random(1000, 3000));
            if (Utils.useItemOnObject(ItemID.ROPE, 1996))
                Timer.abc2WaitCondition(() -> preLedgeArea.contains(Player.getPosition()), 14000, 18000);
        }

        if (preLedgeArea.contains(Player.getPosition()))
            if (Utils.useItemOnObject(ItemID.ROPE, 2020))
                Timer.abc2WaitCondition(() -> LEDGE.contains(Player.getPosition()), 12000, 15000);

        if (LEDGE.contains(Player.getPosition()))
            if (Utils.clickObj("Barrel", "Get in"))
                Timer.waitCondition(() -> !LEDGE.contains(Player.getPosition()), 8000, 12000);

        if (!braxtonHouseUpperFloor.contains(Player.getPosition()) && !braxtonHouseBottomFloor.contains(Player.getPosition()))
            PathingUtil.walkToTile(braxtonHouse);

        if (BOTTOM_OF_BRAXTORIAN_HOUSE.contains(Player.getPosition()))
            if (Utils.clickObj("Staircase", "Climb-up"))
                Timer.abc2WaitCondition(() -> braxtonHouseUpperFloor.contains(Player.getPosition()), 12000, 15000);

        if (braxtonHouseUpperFloor.contains(Player.getPosition()) && Inventory.find(BOOK).length < 1) {
            if (Utils.clickObj(1989, "Search"))
                Timer.abc2WaitCondition(() -> Inventory.find(BOOK).length > 0, 12000, 15000);

            RSItem[] invBook = Inventory.find(BOOK);
            if (invBook.length > 0)
                if (AccurateMouse.click(invBook[0], "Read"))
                    General.sleep(General.random(2000, 5000));

        }
    }

    public void getPebbleOld() {
        if (!undergroundArea1.contains(Player.getPosition()) && Inventory.find(pebble).length < 1) {
            cQuesterV2.status = "Getting Pebble";
            PathingUtil.walkToTile(new RSTile(2533, 3156, 0));
            if (Utils.clickObj(5250, "Climb-down"))
                Timer.waitCondition(() -> underGroundLadderArea.contains(Player.getPosition()), 12000, 15000);
        }

        if (undergroundArea1.contains(Player.getPosition()) && Inventory.find(key.getId()).length < 1 && Inventory.find(pebble).length < 1) {
            Walking.blindWalkTo(crateTile);
            Timer.waitCondition(() -> crateArea.contains(Player.getPosition()), 12000, 15000);
            Utils.shortSleep();
            checkEat();
            if (Utils.clickObj(1990, "Search"))
                Timer.waitCondition(() -> Inventory.find(key.getId()).length > 0, 12000, 15000);
            checkEat();
        }
        if (undergroundArea1.contains(Player.getPosition()) && (Inventory.find(key.getId()).length > 0 || Inventory.find(pebble).length < 1) && !golrieArea.contains(Player.getPosition())) {
            if (Walking.blindWalkTo(new RSTile(2535, 9554, 0))) // don't use Utils class here
                Utils.shortSleep();
            Walking.blindWalkTo(new RSTile(2515, 9575, 0));
            checkEat();
            Timer.waitCondition(() -> underGroundDoorArea.contains(Player.getPosition()), 12000, 15000);
            checkEat();
            if (Utils.clickObj("Door", "Open"))
                Timer.abc2WaitCondition(() -> golrieArea.contains(Player.getPosition()), 12000, 15000);

            checkEat();
        }
        if (undergroundArea1.contains(Player.getPosition()) && Inventory.find(key.getId()).length > 0 && golrieArea.contains(Player.getPosition()) && Inventory.find(pebble).length < 1) {
            cQuesterV2.status = "Talking to Golrie";
            NpcChat.talkToNPC(GOLRIE_ID); // don't wrap in an if function, breaks it.
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }
    }


    public static void depositItems() {
        if (Inventory.find(pebble).length > 0 && (Inventory.find(295).length < 1 || Inventory.find(urn).length < 1)) {
            cQuesterV2.status = "Banking";
            DaxWalker.getInstance().walkToBank(RunescapeBank.BARBARIAN_OUTPOST);
            BankManager.open(true);
            BankManager.depositEquipment();
            BankManager.depositAllExcept(true, pebble);
            General.sleep(General.random(300, 600));
            BankManager.withdraw(12, true,
                    ItemID.LOBSTER);
            BankManager.withdraw(1, true,
                    ItemID.GAMES_NECKLACE);
            BankManager.withdraw(1, true,
                    ItemID.STAMINA_POTION[0]);
            BankManager.withdraw(1, true,
                    ItemID.STAMINA_POTION[1]);
            BankManager.close(true);
        }
        if (Inventory.find(pebble).length > 0) {
            cQuesterV2.status = "Going to tombstone";
            PathingUtil.walkToTile(glarialsTombstoneTile);
            General.sleep(General.random(400, 2000));
        }
    }

    public static void checkEat() {
        RSItem[] invLob = Inventory.find(ItemID.LOBSTER);

        if (Combat.getHPRatio() < 60 && invLob.length > 0) {
            AccurateMouse.click(invLob[0], "Eat");
            Utils.microSleep();
        }
    }

    public static void enterTomb() {
        if (Inventory.find(pebble).length > 0) {
            if (!tombstoneArea.contains(Player.getPosition())) {
                cQuesterV2.status = "Going to tombstone";
                PathingUtil.walkToTile(glarialsTombstoneTile);
            }
            if (tombstoneArea.contains(Player.getPosition())) {
                cQuesterV2.status = "Entering tombstone";
                if (Utils.useItemOnObject(pebble, 1992)) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                    Timer.waitCondition(() -> undergroundArea2.contains(Player.getPosition()), 6000, 9000);
                }
            }
            if (undergroundArea2.contains(Player.getPosition()) && Inventory.find(necklace).length < 1) {
                if (Skills.getActualLevel(Skills.SKILLS.PRAYER) > 42 && Prayer.getPrayerPoints() > 0)
                    Prayer.enable(Prayer.PRAYERS.PROTECT_FROM_MELEE);

                if (!Game.isRunOn())
                    Options.setRunEnabled(true);

                Walking.blindWalkTo(new RSTile(2530, 9844, 0));
                Timer.waitCondition(() -> chestArea.contains(Player.getPosition()), 6000, 9000);
                checkEat();

                if (Utils.clickObj("Chest", "Open")) {
                    Timer.waitCondition(() -> Inventory.find(necklace).length > 0, 4500, 6000);

                    Utils.idle(100, 400);
                }
                checkEat();
            }
            if (undergroundArea2.contains(Player.getPosition()) && Inventory.find(necklace).length > 0 && Inventory.find(urn).length < 1) {
                Walking.blindWalkTo(new RSTile(2546, 9837, 0));

                Utils.idle(1500, 2500);
                Walking.blindWalkTo(new RSTile(2542, 9811, 0));

                Utils.idle(1500, 2500);
                checkEat();

                General.sleep(General.random(500, 2000));
                if (Utils.clickObj(1993, "Search")) {
                    Timer.waitCondition(() -> Inventory.find(urn).length > 0, 6000, 9000);

                    Utils.idle(100, 400);
                }

                if (Inventory.find(urn).length < 1) { // second check
                    if (Utils.clickObj(1993, "Search")) {
                        Timer.waitCondition(() -> Inventory.find(urn).length > 0, 6000, 9000);

                        Utils.idle(100, 400);
                    }
                }
                checkEat();
            }

            if (undergroundArea2.contains(Player.getPosition()) && Inventory.find(necklace).length > 0 && Inventory.find(urn).length > 0) {
                Utils.equipItem(ItemID.GAMES_NECKLACE);
                PathingUtil.walkToTile(RunescapeBank.BARBARIAN_OUTPOST.getPosition());
                checkEat();

            }
        }
    }


    public static void getItems2() {
        if (!BankManager.checkInventoryItems(
                ItemID.LOBSTER, urn, necklace, ItemID.WATER_RUNE, ItemID.AIR_RUNE, ItemID.EARTH_RUNE, ItemID.ROPE)) {
            cQuesterV2.status = "Banking for final step";
            Prayer.disable(Prayer.PRAYERS.PROTECT_FROM_MELEE);
            BankManager.open(true);
            BankManager.checkEquippedGlory();
            BankManager.depositAll(true);
            General.sleep(General.random(300, 600));
            BankManager.withdraw(10, true, lobster);
            BankManager.withdraw(1, true, ItemID.GAMES_NECKLACE);
            BankManager.withdraw(1, true, urn);
            BankManager.withdraw(1, true, necklace);
            BankManager.withdraw(6, true, ItemID.EARTH_RUNE);
            BankManager.withdraw(6, true, ItemID.AIR_RUNE);
            BankManager.withdraw(6, true, ItemID.WATER_RUNE);
            BankManager.withdraw(1, true, ItemID.ROPE);
            BankManager.withdraw(1, true, pebble);
            BankManager.withdraw(1, true,
                    ItemID.STAMINA_POTION);
            BankManager.withdraw(1, true,
                    ItemID.RING_OF_WEALTH);
            BankManager.close(true);
        }

        if (!BankManager.checkInventoryItems(urn, necklace)) {
            General.println("[Debug]: We are missing the urn &/or necklace. Will attempt to go back and get.");
            depositItems(); // short afk after depositing
            enterTomb();
        }
    }

    public void goingToLastCave() {
        if ((Inventory.find(295).length > 0 && Inventory.find(urn).length > 0)) {
            cQuesterV2.status = "Going to last Cave";
            if (!raftArea.contains(Player.getPosition()) && !raftCrashArea.contains(Player.getPosition())
                    && !preLedgeArea.contains(Player.getPosition()) && !ledge.contains(Player.getPosition())
                    && !lastCaveArea.contains(Player.getPosition()))
                PathingUtil.walkToArea(raftArea);

            if (raftArea.contains(Player.getPosition()))
                if (Utils.clickObj("Log raft", "Board"))
                    Timer.abc2WaitCondition(() -> raftCrashArea.contains(Player.getPosition()), 8000, 12000);

            if (raftCrashArea.contains(Player.getPosition())) {
                Walking.blindWalkTo(endOfCrashSite);
                General.sleep(General.random(1000, 2000));
                if (Utils.useItemOnObject(ItemID.ROPE, 1996))
                    Timer.waitCondition(() -> preLedgeArea.contains(Player.getPosition()), 12000, 15000);

            }
            if (preLedgeArea.contains(Player.getPosition()))
                if (Utils.useItemOnObject(ItemID.ROPE, 2020))
                    Timer.waitCondition(() -> ledge.contains(Player.getPosition()), 5000, 7000);

            if (ledge.contains(Player.getPosition())) {
                if (Utils.clickObj(2010, "Open")) {
                    Timer.abc2WaitCondition(() -> lastCaveArea.contains(Player.getPosition()), 12000, 16000);
                }
            }
        }
    }


    public void navigateCave() {
        if (!endArea.contains(Player.getPosition()) && !FINAL_ROOM.contains(Player.getPosition())) {
            if (lastCaveArea.contains(Player.getPosition()) && Inventory.find(key2).length < 1) {
                cQuesterV2.status = "Getting key";
                PathingUtil.walkToTile(new RSTile(2589, 9886, 0));
                Timer.waitCondition(() -> crateArea2.contains(Player.getPosition()), 12000, 15000);
            }
            if (crateArea2.contains(Player.getPosition()) && Inventory.find(key2).length < 1) {
                if (Utils.clickObj(1999, "Search"))
                    Timer.waitCondition(() -> Inventory.find(key2).length > 0, 7000, 10000);
                if (Combat.getHPRatio() < 55)
                    AccurateMouse.click(Inventory.find(lobster)[0], "Eat");
            }

            if (lastCaveArea.contains(Player.getPosition()) && Inventory.find(key2).length > 0) {
                cQuesterV2.status = "Waterfall: Going to final room";
                PathingUtil.walkToTile(new RSTile(2568, 9892, 0));
                Timer.waitCondition(() -> !Player.isMoving(), 10000, 12000);
                if (Combat.getHPRatio() < 75)
                    AccurateMouse.click(Inventory.find(lobster)[0], "Eat");

                if (Utils.clickObj("Door", "Open")) {
                    Timer.waitCondition(() -> preEndArea.contains(Player.getPosition()), 12000, 15000);
                    Utils.shortSleep();
                }
            }
            if (preEndArea.contains(Player.getPosition())) {
                cQuesterV2.status = "Opening door 2";
                Walking.blindWalkTo((new RSTile(2566, 9901, 0)));
                General.sleep(General.random(1500, 2500));

                RSObject[] door = Objects.findNearest(20, 2002);
                if (AccurateMouse.click(door[0], "Open")) {
                    Timer.waitCondition(() -> FINAL_ROOM.contains(Player.getPosition()), 12000, 15000);
                    Utils.shortSleep();
                    Walking.blindWalkTo(new RSTile(2564, 9909, 0));
                }
            }
        }
    }

    public void finishQuest() {
        if (FINAL_ROOM.contains(Player.getPosition()) && Inventory.find(key2).length > 0) {
            cQuesterV2.status = "Using runes";
            General.println("[Debug]: " + cQuesterV2.status);
            Utils.setCameraZoomAboveDefault();
            General.sleep(General.random(500, 2000));
            Camera.setCameraAngle(General.random(85, 100));
            RSObject[] pillars = Objects.findNearest(30, 2005);
            General.println("[Debug]: Pillars detected: " + pillars.length);
            for (RSObject obj : pillars){
                useRunesOnPillars(obj);
            }
            /*RSItem[] waterRune = Inventory.find(ItemID.WATER_RUNE);
            if (waterRune.length > 0) {
                if (waterRune[0].getStack() == 6) {
                    useRunesOnPillars(0);
                }
                if (waterRune[0].getStack() == 5) {
                    useRunesOnPillars(1);
                }
                if (waterRune[0].getStack() == 4) {
                    useRunesOnPillars(4);
                }
                if (waterRune[0].getStack() == 3) {
                    useRunesOnPillars(5);
                }
                if (waterRune[0].getStack() == 2) {
                    useRunesOnPillars(3);
                }
                if (waterRune[0].getStack() == 1) {
                    useRunesOnPillars(2);
                }
            }*/
            if (Inventory.find(ItemID.WATER_RUNE).length < 1 && Inventory.find(ItemID.EARTH_RUNE).length < 1 &&
                    Inventory.find(ItemID.AIR_RUNE).length < 1) {
                if (Utils.useItemOnObject(necklace, 2006)) {
                    Utils.idle(4000, 9000);
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                    NPCChat.clickContinue(true);
                    Keyboard.typeKeys(spaceKey);

                    Utils.idle(4000, 9000);
                }
                if (Inventory.find(urn).length > 0) {
                    if (Objects.find(20, 2014).length > 0) {
                        if (Utils.useItemOnObject(urn, "Chalice")) {
                            NPCInteraction.waitForConversationWindow();
                            NPCInteraction.handleConversation();
                            NPCChat.clickContinue(true);
                            Keyboard.typeKeys(spaceKey);
                        }
                    }
                }
            }
        }
    }

    public void useRunesOnPillars(RSObject pillarNum) {
        if (PathingUtil.clickScreenWalk(pillarNum.getPosition())){
            PathingUtil.movementIdle();
        }
        if (Inventory.find(ItemID.EARTH_RUNE).length > 0) {
            int stack = Inventory.find(ItemID.EARTH_RUNE)[0].getStack();
            if (Utils.useItemOnObject(ItemID.EARTH_RUNE, pillarNum))
                Timer.slowWaitCondition(() -> Inventory.find(ItemID.EARTH_RUNE).length < 1 ||
                        Inventory.find(ItemID.EARTH_RUNE)[0].getStack() < stack, 4500, 5000);

        }
        if (Inventory.find(ItemID.AIR_RUNE).length > 0) {
            int stack = Inventory.find(ItemID.AIR_RUNE)[0].getStack();
            if (Utils.useItemOnObject(ItemID.AIR_RUNE, pillarNum))
                Timer.waitCondition(() -> Inventory.find(ItemID.AIR_RUNE).length < 1
                        || Inventory.find(ItemID.AIR_RUNE)[0].getStack() < stack, 5000);

        }
        if (Inventory.find(ItemID.WATER_RUNE).length > 0) {
            int stack = Inventory.find(ItemID.WATER_RUNE)[0].getStack();
            if (Utils.useItemOnObject(ItemID.WATER_RUNE, pillarNum))
                Timer.waitCondition(() -> Inventory.find(ItemID.WATER_RUNE).length < 1
                        || Inventory.find(ItemID.WATER_RUNE)[0].getStack() < stack, 5000);

        }
    }


    public void useRunesOnPillars(int pillarNumber) {

        if (Inventory.find(ItemID.EARTH_RUNE).length > 0) {
            int stack = Inventory.find(ItemID.EARTH_RUNE)[0].getStack();
            Utils.selectInvItem(ItemID.EARTH_RUNE);
            if (AccurateMouse.click(pillars[pillarNumber], "Use")) {
                Timer.waitCondition(() -> Inventory.find(ItemID.EARTH_RUNE).length < 1 ||
                        Inventory.find(ItemID.EARTH_RUNE)[0].getStack() < stack, 5000);
            }
        }
        if (Inventory.find(ItemID.AIR_RUNE).length > 0) {
            int stack = Inventory.find(ItemID.AIR_RUNE)[0].getStack();
            Utils.selectInvItem(ItemID.AIR_RUNE);
            if (AccurateMouse.click(pillars[pillarNumber], "Use")) {
                Timer.waitCondition(() -> Inventory.find(ItemID.AIR_RUNE).length < 1
                        || Inventory.find(ItemID.AIR_RUNE)[0].getStack() < stack, 5000);
            }
        }
        if (Inventory.find(ItemID.WATER_RUNE).length > 0) {
            int stack = Inventory.find(ItemID.WATER_RUNE)[0].getStack();
            Utils.selectInvItem(ItemID.WATER_RUNE);
            if (AccurateMouse.click(pillars[pillarNumber], "Use")) {
                Timer.waitCondition(() -> Inventory.find(ItemID.WATER_RUNE).length < 1
                        || Inventory.find(ItemID.WATER_RUNE)[0].getStack() < stack, 5000);
            }
        }
    }


    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        return cQuesterV2.taskList.get(0).equals(this);
    }

    @Override
    public void execute() {
        setupConditions();

        if (Game.getSetting(65) == 0) { // start quest
            buyItems();
            getItems();
            cQuesterV2.status = "Starting Quest";
            talkToAlmera.execute();

        } else if (Game.getSetting(65) == 1) {
            talkToHudonStep();

        } else if (Game.getSetting(65) == 2) {
            getBook();

        } else if (Game.getSetting(65) == 3) { // upon reading book
            getPebbleOld();
            depositItems();
            enterTomb();

        } else if (Game.getSetting(65) == 4) {
            getItems2();
            goingToLastCave();

        } else if (Game.getSetting(65) == 5) {
            goingToLastCave();
            navigateCave();

        } else if (Game.getSetting(65) == 6) {
            goingToLastCave();
            navigateCave();
            finishQuest();
        }
        if (Game.getSetting(65) == 10) {
            // done
            cQuesterV2.taskList.remove(this);
        }
    }

    @Override
    public String questName() {
        return "Waterfall Quest (" + Game.getSetting(65) +")";
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
        return Quest.WATERFALL_QUEST.getState().equals(Quest.State.COMPLETE);
    }
}
