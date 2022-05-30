package scripts.QuestPackages.ShiloVillage;

import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.Equipment;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Prayer;
import org.tribot.api2007.WorldHopper;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.*;
import org.tribot.script.sdk.*;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.LocalTile;
import org.tribot.script.sdk.types.Widget;
import org.tribot.script.sdk.types.WorldTile;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.SheepShearer.SheepShearer;
import scripts.QuestSteps.BuyItemsStep;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.*;
import scripts.Requirements.Items.ItemRequirements;
import scripts.Requirements.Quest.QuestRequirement;
import scripts.Tasks.Priority;
import scripts.Timer;

import java.util.*;

public class ShilloVillage implements QuestTask {

    private static ShilloVillage quest;

    public static ShilloVillage get() {
        return quest == null ? quest = new ShilloVillage() : quest;
    }

    int SPADE = 952;
    int LIT_CANDLE = 33;
    int UNLIT_CANDLE = 32;
    int TINDERBOX = 590;
    int ROPE = 954;
    int HAMMER = 2347;
    int CHISEL = 1755;
    int BRONZE_WIRE = 1794;
    int BONES = 526;
    int COINS = 995;
    int RASHILIYIA_CORPSE = 609;

    int WAMPUM_BELT = 625;
    int TATTERED_SCROLL = 607;
    int CRUMPLED_SCROLL = 608;
    int ZADIMUS_CORPSE = 610;
    int BONE_SHARD = 604;
    int LOCATING_CRYSTAL = 611;
    int BERVIRIOUS_NOTES = 624;
    int SWORD_POMMEL = 623;
    int BEADS = 618;
    int BEADS_OF_THE_DEAD = 616;
    int PALM_TREE = 2237;
    int BONE_KEY = 605;

    HashMap<Integer, Integer> invMap = new HashMap<>();
    HashMap<Integer, Integer> step3, step4, step5, corpseStep, step9;


    RSArea START_AREA = new RSArea(new RSTile(2878, 2954, 0), new RSTile(2886, 2947, 0));
    RSArea MOUND_OF_EARTH_AREA = new RSArea(new RSTile(2916, 3003, 0), new RSTile(2927, 2994, 0));
    RSArea TRUFITUS_HOUSE = new RSArea(new RSTile(2807, 3087, 0), new RSTile(2811, 3085, 0));
    RSTile DIRT_TILE = new RSTile(2922, 2998, 0);
    RSArea AHZAHROON_FIRST_AREA = new RSArea(new RSTile(2883, 9408, 0), new RSTile(2948, 9339, 0));
    RSArea AHZAHROON_SECOND_AREA = new RSArea(new RSTile(2882, 9329, 0), new RSTile(2945, 9278, 0));
    RSArea BOSS_ROOM_AREA = new RSArea(new RSTile(2898, 9492, 0), new RSTile(2888, 9481, 0));
    RSTile SAFE_TILE = new RSTile(2891, 9487, 0);


    RSTile TILE4 = new RSTile(2903, 9375, 0);
    LocalTile CAVE_IN_TILE = new LocalTile(2887, 9375, 0);
    LocalTile LOOSE_ROCKS_TILE = new LocalTile(2885, 9317, 0);
    RSTile OLD_SACK_TILE = new RSTile(2938, 9286, 0);
    RSTile GALLOWS_TILE = new RSTile(2934, 9327, 0);
    RSTile STATUE_TILE = new RSTile(2795, 3089, 0);
    RSArea INITIAL_DUNGEON_AREA = new RSArea(
            new RSTile[]{
                    new RSTile(2925, 9528, 0),
                    new RSTile(2933, 9528, 0),
                    new RSTile(2933, 9520, 0),
                    new RSTile(2930, 9517, 0),
                    new RSTile(2928, 9517, 0),
                    new RSTile(2925, 9520, 0)
            }
    );
    RSArea WHOLE_BOSS_DUNGEON_AREA = new RSArea(new RSTile(2939, 9470, 0), new RSTile(2887, 9537, 0));
    RSArea AFTER_GATE_TOP_OF_SLOPE = new RSArea(new RSTile(2926, 9516, 0), new RSTile(2930, 9514, 0));
    RSArea BEFORE_ROCK_WALL_AREA = new RSArea(new RSTile(2792, 2980, 0), new RSTile(2796, 2977, 0));
    RSArea AFTER_ROCK_WALL = new RSArea(new RSTile(2791, 2980, 0), new RSTile(2786, 2977, 0));
    RSArea CAIRN_ISLAND_AREA = new RSArea(new RSTile(2762, 2987, 0), new RSTile(2764, 2985, 0));
    RSArea DKP_FAIRY_RING_AREA = new RSArea(new RSTile(2918, 3091, 0), new RSTile(2923, 3084, 0));


    RSTile[] PATH_TO_SKELETON_DOOR = {
            new RSTile(2928, 9510, 0),
            new RSTile(2921, 9506, 0),
            new RSTile(2915, 9502, 0),
            new RSTile(2917, 9495, 0),
            new RSTile(2916, 9491, 0),
            new RSTile(2910, 9487, 0),
            new RSTile(2903, 9480, 0),
            new RSTile(2894, 9477, 0),
            new RSTile(2893, 9479, 0)
    };
    RSArea BOTTOM_OF_SLOPE_NOT_AT_SKELETON_DOOR = new RSArea(
            new RSTile[]{
                    new RSTile(2933, 9514, 0),
                    new RSTile(2915, 9513, 0),
                    new RSTile(2894, 9481, 0),
                    new RSTile(2891, 9481, 0),
                    new RSTile(2889, 9473, 0),
                    new RSTile(2903, 9473, 0),
                    new RSTile(2931, 9499, 0)
            }
    );
    RSTile BEFORE_SKELETON_DOOR = new RSTile(2893, 9480, 0);


    String[] START_DIALOGUE = {
            "Why do I need to run?",
            "Rashiliyia? Who is she?",
            "I'll go to see the Shaman.",
            "What can we do?",
            "Yes, I'm sure and I'll take the Wampum belt to Trufitus."
    };
    String[] TRUFITUS_DIALOGUE = {
            "Mosol Rei said something about a legend?", //1
            "I am going to search for Ah Za Rhoon!",//3
            "Yes.", //5
            "Yes, I will seriously look for Ah Za Rhoon and I'd appreciate your help.",//4
            "Do you know anything more about the temple?", //second one, but need this order for chat.handle
            "Tell me more.", //3
    };
    String[] BONESHARD_DIALOGUE = {
            "It appeared when I buried Zadimus' corpse.",
            "He said something after he gave it to me.",
            "The spirit said something about keys and kin?"
    };


    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.SPADE, 1, 500),
                    new GEItem(ItemID.CANDLE, 1, 300),
                    new GEItem(ItemID.TINDERBOX, 1, 500),
                    new GEItem(ItemID.ROPE, 1, 200),
                    new GEItem(ItemID.HAMMER, 1, 500),
                    new GEItem(ItemID.CHISEL, 1, 500),
                    new GEItem(ItemID.BRONZE_WIRE, 1, 300),
                    new GEItem(ItemID.BONES, 3, 300),
                    new GEItem(ItemID.STAFF_OF_FIRE, 1, 200),
                    new GEItem(ItemID.MIND_RUNE, 300, 20),
                    new GEItem(ItemID.AIR_RUNE, 600, 20),
                    new GEItem(ItemID.ARDOUGNE_TELEPORT, 5, 50),
                    new GEItem(ItemID.MONKFISH, 10, 40),
                    new GEItem(ItemID.PRAYER_POTION4, 4, 20),
                    new GEItem(ItemID.RING_OF_DUELING[0], 1, 30),
                    new GEItem(ItemID.ANTIDOTE_PLUS_PLUS[0], 2, 30),
                    new GEItem(ItemID.STAMINA_POTION[0], 4, 15),
                    new GEItem(ItemID.AMULET_OF_GLORY[2], 1, 15),
                    new GEItem(ItemID.RING_OF_WEALTH[0], 1, 25)
            )
    );

    public void buyItems() {
        cQuesterV2.status = "Buying Items";
        BuyItemsStep buy = new BuyItemsStep(itemsToBuy);
        buy.buyItems();
    }

    InventoryRequirement initialItemReqs = new InventoryRequirement(new ArrayList<>(
            Arrays.asList(
                    new ItemReq(ItemID.STAMINA_POTION[0], 3, 0),
                    new ItemReq(ItemID.AMULET_OF_GLORY[2], 1, 0, true, true),
                    new ItemReq(ItemID.SPADE, 1),
                    new ItemReq(ItemID.TINDERBOX, 1),
                    new ItemReq(ItemID.ROPE, 1),
                    new ItemReq(ItemID.HAMMER, 1),
                    new ItemReq(ItemID.CHISEL, 1),
                    new ItemReq(ItemID.BRONZE_WIRE, 1),
                    new ItemReq(ItemID.BONES, 3),
                    new ItemReq(WAMPUM_BELT, 1, 0),
                    new ItemReq(ItemID.RING_OF_DUELING[0], 1, 0,
                            true, true),

                    new ItemReq(ItemID.ANTIDOTE_PLUS_PLUS[0], 1),

                    new ItemReq(ItemID.CANDLE, 1, 0),
                    new ItemReq(ItemID.STAFF_OF_FIRE, 1, true, true),
                    new ItemReq(ItemID.MIND_RUNE, 300, 50),
                    new ItemReq(ItemID.AIR_RUNE, 600, 100),
                    new ItemReq(ItemID.ARDOUGNE_TELEPORT, 3, 0),
                    new ItemReq(ItemID.MONKFISH, 6, 2),
                    new ItemReq(ItemID.COINS_995, 2000, 500),
                    new ItemReq(ItemID.PRAYER_POTION_4, 2, 0)
            )
    ));

    public boolean lightCandle() {
        if (Utils.useItemOnItem(ItemID.CANDLE, ItemID.TINDERBOX)) {
            return Waiting.waitUntil(1500, 125,
                    () -> Inventory.find(ItemID.LIT_CANDLE).length > 0);
        }
        return Inventory.find(ItemID.LIT_CANDLE).length > 0;
    }

    public void getItems() {
        cQuesterV2.status = "Getting Items Initial";
        if (!initialItemReqs.check()) {
            BankManager.open(true);
            BankManager.depositAll(true);
            BankManager.depositEquipment();
            BankManager.checkEquippedGlory();
            initialItemReqs.withdrawItems();
            if (!BankManager.withdraw(1, false, LIT_CANDLE)) {
                BankManager.withdraw(1, true, ItemID.CANDLE);
            }
            BankManager.close(true);
            lightCandle();
            Utils.equipItem(ItemID.STAFF_OF_FIRE);
            Utils.equipItem(ItemID.RING_OF_DUELING[0]);
        }
    }

    public void startQuest() {
        if (Inventory.find(WAMPUM_BELT).length == 0) {
            cQuesterV2.status = "Going to start";
            PathingUtil.walkToArea(START_AREA, false);

            if (org.tribot.script.sdk.Inventory.isFull())
                EatUtil.eatFood();

            if (NpcChat.talkToNPC("Mosol Rei")) {
                NPCInteraction.waitForConversationWindow();
                NpcChat.handle(START_DIALOGUE);
            }
        }
    }

    public void step2UseBeltOnTrufitus() {
        if (Inventory.find(WAMPUM_BELT).length > 0) {
            cQuesterV2.status = "Going to Trufitus";
            PathingUtil.walkToArea(TRUFITUS_HOUSE, true);
            if (Utils.useItemOnNPC(WAMPUM_BELT, "Trufitus")) {
                NPCInteraction.waitForConversationWindow();
                NpcChat.handle(TRUFITUS_DIALOGUE);
            }
        }
    }

    public void step3GoToAhZaRhoon() {
        InventoryRequirement itemReqs = new InventoryRequirement(new ArrayList<>(
                Arrays.asList(
                        new ItemReq(ItemID.SPADE, 1),
                        new ItemReq(ItemID.LIT_CANDLE, 1),
                        new ItemReq(ItemID.ROPE, 1),
                        new ItemReq(ItemID.TINDERBOX, 1)
                )));


        if (itemReqs.check()) {
            cQuesterV2.status = "Going to Ah Za Rhoon";
            PathingUtil.walkToTile(DIRT_TILE, 2, true);
            LocalTile t = new LocalTile(2922, 2998, 0);
            if (!MyPlayer.getTile().equals(t) && t.interact("Walk here"))
                PathingUtil.movementIdle();

            RSObject[] f = Objects.findNearest(20, Filters.Objects.nameContains("Fissure"));
            cQuesterV2.status = "Clearing dirt";
            if (f.length == 0 && Utils.clickInventoryItem(SPADE)) {
                Timer.waitCondition(() ->
                        Objects.findNearest(20, Filters.Objects.nameContains("Fissure")).length > 0, 4000, 6000);

            }
            if (f.length > 0) {
                cQuesterV2.status = "Using Candle on Fissure";
                if (Utils.useItemOnObject(LIT_CANDLE, "Fissure")) {
                    NpcChat.handle(true, "Yes");
                    NpcChat.handle(true, "Yes");
                }

            }
        }
        if (Utils.useItemOnObject(ROPE, "Fissure")) {
            NPCInteraction.waitForConversationWindow();
            NpcChat.handle("Yes");
        }
    }

    public void searchFissure() {
        PathingUtil.walkToTile(DIRT_TILE, 2, true);
        RSObject[] f = Objects.findNearest(20, Filters.Objects.nameContains("Fissure"));
        cQuesterV2.status = "Clearing dirt";
        if (f.length == 0 && Utils.clickInventoryItem(SPADE)) {
            Timer.waitCondition(() ->
                    Objects.findNearest(20, Filters.Objects.nameContains("Fissure")).length > 0, 4000, 6000);

        }

        if (f.length > 0) {
            cQuesterV2.status = "Searching Fissure";
            if (f[0].click("Search")) {
                Waiting.waitUntil(2000, 25, () -> ChatScreen.isOpen());
                NpcChat.handle("Yes, I'll give it a go!");
                Timer.waitCondition(() -> AHZAHROON_FIRST_AREA.contains(Player.getPosition()), 8000, 12000);
            }
        }
    }


    public void step5GoToCaveIn() {
        if (AHZAHROON_FIRST_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "5: Going to Cave in";
            PathingUtil.localNav(CAVE_IN_TILE);

            RSObject[] obj = Objects.findNearest(20, Filters.Objects.nameContains("Cave in"));
            cQuesterV2.status = "5: Searching Cave in";
            WorldTile t = new WorldTile(2888, 9372, 0);
            if (!MyPlayer.getTile().equals(t) && t.interact("Walk here"))
                PathingUtil.movementIdle();
            if (obj.length > 0 && Utils.clickObject(obj[0], "Search")) {
                NpcChat.handle(true, "Yes, I'll wriggle through.");
                Timer.waitCondition(() -> AHZAHROON_SECOND_AREA.contains(Player.getPosition()), 8000, 12000);
            }
        }
        if (AHZAHROON_SECOND_AREA.contains(Player.getPosition()) && Inventory.find(TATTERED_SCROLL).length == 0) {
            if (PathingUtil.localNav(LOOSE_ROCKS_TILE))
                Timer.waitCondition(() -> LOOSE_ROCKS_TILE.distance() < 4, 5000, 8000);
            RSObject[] obj = Objects.findNearest(20, Filters.Objects.nameContains("Loose rocks"));
            cQuesterV2.status = "5: Searching Rocks";
            if (obj.length > 0 && Utils.clickObject(obj[0], "Search")) {
                NpcChat.handle(true, "Yes, I'll carefully move the rocks to see what's behind them.");
                NpcChat.handle();
            }
        }
        RSItem[] s = Inventory.find(TATTERED_SCROLL);
        if (s.length > 0 && Inventory.find(CRUMPLED_SCROLL).length == 0) {
            cQuesterV2.status = "5: Reading scroll";
            if (s[0].click("Read")) {
                NpcChat.handle(true, "Yes please.");
                Timer.waitCondition(() -> Interfaces.isInterfaceSubstantiated(220, 16), 3000, 5000);
            }
            if (Interfaces.isInterfaceSubstantiated(220, 16)) {
                Interfaces.get(220, 16).click();
            }
        }
    }

    public void goToOldSacks() {
        if (AHZAHROON_SECOND_AREA.contains(Player.getPosition()) && Inventory.find(TATTERED_SCROLL).length > 0
                && Inventory.find(CRUMPLED_SCROLL).length == 0 && Inventory.find(ZADIMUS_CORPSE).length == 0) {
            cQuesterV2.status = "5: Searching Old Sacks";
            if (Walking.blindWalkTo(OLD_SACK_TILE))
                Timer.waitCondition(() -> OLD_SACK_TILE.distanceTo(Player.getPosition()) < 4, 5000, 8000);
            RSObject[] obj = Objects.findNearest(20, Filters.Objects.nameContains("Old sacks"));
            if (obj.length > 0 && Utils.clickObject(obj[0], "Search")) {
                Waiting.waitUntil(2000, 25, () -> ChatScreen.isOpen());
                NpcChat.handle();
            }
        }
        RSItem[] s = Inventory.find(CRUMPLED_SCROLL);
        if (s.length > 0) {
            cQuesterV2.status = "5: Reading scroll";
            WorldTile t = new WorldTile(2940, 9285, 0);
            if (!MyPlayer.getTile().equals(t) && t.interact("Walk here"))
                PathingUtil.movementIdle();

            if (s[0].click("Read")) {
                Waiting.waitUntil(2000, 25, () -> ChatScreen.isOpen());
                NpcChat.handle("Yes please.");
                Timer.waitCondition(() -> Interfaces.isInterfaceSubstantiated(220, 16), 3000, 5000);
            }
            Optional<Widget> first = Query.widgets().inIndexPath(220).actionContains("Close").findFirst();
            if (first.map(f -> f.click()).orElse(false)) {
                Waiting.waitUntil(2500, 500, () -> !Widgets.isVisible(220, 16));
            }
        }
    }

    public static boolean clickScreenWalk(WorldTile t) {
        if (!MyPlayer.getTile().equals(t) && t.interact("Walk here") &&
                Waiting.waitUntil(1200, 25, MyPlayer::isMoving))
            return Waiting.waitUntil(4500, 75, () -> MyPlayer.getTile().equals(t));
        return MyPlayer.getTile().equals(t);
    }

    public void goToGallows() {
        if (AHZAHROON_SECOND_AREA.contains(Player.getPosition()) && Inventory.find(TATTERED_SCROLL).length > 0
                && Inventory.find(CRUMPLED_SCROLL).length > 0 && Inventory.find(ZADIMUS_CORPSE).length == 0) {
            cQuesterV2.status = "5: Going to Ancient Gallows";
            if (Walking.blindWalkTo(GALLOWS_TILE))
                Timer.waitCondition(() -> GALLOWS_TILE.distanceTo(Player.getPosition()) < 4, 5000, 8000);

            RSObject[] obj = Objects.findNearest(20, Filters.Objects.nameContains("Ancient gallows"));
            cQuesterV2.status = "5: Searching Ancient Gallows";
            if (obj.length > 0 && Utils.clickObject(obj[0], "Search")) {
                NPCInteraction.waitForConversationWindow();
                NpcChat.handle("Yes, I may find something else on the corpse.");
                NpcChat.handle();
            }
        }
    }

    public void goBackToDude() {
        if (AHZAHROON_SECOND_AREA.contains(Player.getPosition()) && Inventory.find(TATTERED_SCROLL).length > 0
                && Inventory.find(CRUMPLED_SCROLL).length > 0 && Inventory.find(ZADIMUS_CORPSE).length > 0) {
            cQuesterV2.status = "Going to Trufitus";
            PathingUtil.walkToArea(TRUFITUS_HOUSE, true);
            if (Utils.useItemOnNPC(CRUMPLED_SCROLL, "Trufitus")) {
                NPCInteraction.waitForConversationWindow();
                NpcChat.handle("Anything that can help?");
                NpcChat.handle();
            }
            if (Utils.useItemOnNPC(TATTERED_SCROLL, "Trufitus")) {
                NPCInteraction.waitForConversationWindow();
                NpcChat.handle();
            }
            if (Utils.useItemOnNPC(ZADIMUS_CORPSE, "Trufitus")) {
                NPCInteraction.waitForConversationWindow();
                NpcChat.handle("Is there any sacred ground around here?");
                NpcChat.handle();
            }
        }
    }


    public void buryCorpse() {
        if (Inventory.find(BONE_SHARD, BONE_KEY).length == 0) {
            InventoryRequirement invReq = new InventoryRequirement(new ArrayList<>(
                    Arrays.asList(
                            new ItemReq(ZADIMUS_CORPSE, 1)
                    ))
            );


            if (invReq.check()) {
                cQuesterV2.status = "Burying corpse";
                PathingUtil.walkToTile(STATUE_TILE, 1, true);
                PathingUtil.movementIdle();
                if (STATUE_TILE.distanceTo(Player.getPosition()) < 3) {
                    RSItem[] itm = Inventory.find(ZADIMUS_CORPSE);
                    if (itm.length > 0 && itm[0].click("Bury")) {
                        NpcChat.handle(true);
                        NpcChat.handle(true);
                    }
                }
            }
        }
    }

    RSArea CAIRN_ISLAND_DUNGEON_AREA = new RSArea(new RSTile(2772, 9359, 0), new RSTile(2754, 9392, 0));
    RSArea DOLMAN_AREA = new RSArea(new RSTile(2768, 9363, 0), new RSTile(2764, 9366, 0));

    public void goToIsland() {
        if (!CAIRN_ISLAND_DUNGEON_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Going to Island";
            if (!AFTER_ROCK_WALL.contains(Player.getPosition())
                    && !CAIRN_ISLAND_AREA.contains(Player.getPosition()) &&
                    !CAIRN_ISLAND_DUNGEON_AREA.contains(Player.getPosition())) {
                PathingUtil.walkToArea(BEFORE_ROCK_WALL_AREA, true);
                clickScreenWalk(new WorldTile(2785, 2979, 0));
            }
            if (Utils.clickObj(2231, "Climb")) {
                Timer.waitCondition(() -> AFTER_ROCK_WALL.contains(Player.getPosition()), 5000, 7000);
            }

            if (BEFORE_ROCK_WALL_AREA.contains(Player.getPosition())) {
                cQuesterV2.status = "Climbing Rock wall";
                if (Utils.clickObj("Rocks", "Climb")) {
                    Timer.waitCondition(() -> AFTER_ROCK_WALL.contains(Player.getPosition()), 5000, 7000);
                }
            }

            if (AFTER_ROCK_WALL.contains(Player.getPosition())) {
                if (Walking.blindWalkTo(CAIRN_ISLAND_AREA.getRandomTile())) {
                    Timer.waitCondition(() -> CAIRN_ISLAND_AREA.contains(Player.getPosition()), 9000, 12000);
                }
            }
            if (CAIRN_ISLAND_AREA.contains(Player.getPosition())) {
                if (Utils.clickObj("Well stacked rocks", "Search")) {
                    NPCInteraction.waitForConversationWindow();
                    NpcChat.handle("Yes please, I can think of nothing nicer!");
                    NpcChat.handle();
                    Waiting.waitUntil(4500, 500, () -> CAIRN_ISLAND_DUNGEON_AREA.contains(Player.getPosition()));
                }
            }
        }

    }

    public void getBeadsAndNecklace() {
        if ((!Equipment.isEquipped(BEADS_OF_THE_DEAD) &&
                Inventory.find(BEADS_OF_THE_DEAD).length == 0)) {
            goToIsland();
            if (CAIRN_ISLAND_DUNGEON_AREA.contains(Player.getPosition())) {
                cQuesterV2.status = "Navigating Dungeon";
                for (int i = 0; i < 15; i++) {
                    if (Inventory.getAll().length <= 25) {
                        break;
                    }
                    EatUtil.eatFood();
                    Waiting.waitNormal(250, 25);
                }

                if (!DOLMAN_AREA.contains(Player.getPosition()))
                    PathingUtil.localNavigation(DOLMAN_AREA.getRandomTile());

                if (Utils.clickObj("Tomb dolmen", "Search")) {
                    NPCInteraction.waitForConversationWindow();
                    NpcChat.handle();
                }
                if (Utils.useItemOnItem(CHISEL, SWORD_POMMEL)) {
                    NPCInteraction.waitForConversationWindow();
                    NpcChat.handle();
                    Timer.waitCondition(() -> Inventory.find(BEADS).length > 0, 3000, 5000);
                }
                if (Utils.useItemOnItem(BEADS, BRONZE_WIRE)) {
                    NPCInteraction.waitForConversationWindow();
                    Timer.waitCondition(() -> Inventory.find(BEADS_OF_THE_DEAD).length > 0, 3000, 5000);
                }
                Utils.equipItem(BEADS_OF_THE_DEAD);
            }
        }
    }


    public void goToBossFight() {
        buryCorpse();
        if ((Equipment.isEquipped(BEADS_OF_THE_DEAD) ||
                Inventory.find(BEADS_OF_THE_DEAD).length > 0) &&
                Inventory.find(BONE_KEY, BONE_SHARD).length > 0) {
            cQuesterV2.status = "Going to boss fight";
            PathingUtil.walkToArea(DKP_FAIRY_RING_AREA, true);
            if (!Autocast.isAutocastEnabled(Autocast.FIRE_STRIKE)) {
                Autocast.enableAutocast(Autocast.FIRE_STRIKE);
            }
            if (Utils.clickObj(PALM_TREE, "Search")) {
                NPCInteraction.waitForConversationWindow();
                NpcChat.handle();
            }
            if (Utils.clickObj("Carved doors", "Search")) {
                NPCInteraction.waitForConversationWindow();
                NpcChat.handle();
            }

            if (Inventory.find(BONE_KEY).length == 0 && Utils.useItemOnItem(CHISEL, BONE_SHARD))
                Timer.waitCondition(() -> Inventory.find(BONE_KEY).length > 0, 3300);

            if (Utils.useItemOnObject(BONE_KEY, "Carved Doors")) {
                NPCInteraction.waitForConversationWindow();
                NpcChat.handle();
            }
        }
    }


    public void goToBossFightPt2() {
        if (!WHOLE_BOSS_DUNGEON_AREA.contains(Player.getPosition())) {
            goToBossFight();
        }

        if (Utils.clickObj("Hillside entrance", "Enter")) {
            Timer.waitCondition(() -> INITIAL_DUNGEON_AREA.contains(Player.getPosition()), 7000);
        }

        if (INITIAL_DUNGEON_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Opening gate";

            if (!Equipment.isEquipped(BEADS_OF_THE_DEAD))
                Utils.equipItem(BEADS_OF_THE_DEAD, "Wear");

            if (Utils.clickObj("Ancient metal gate", "Open"))
                Timer.waitCondition(() -> !INITIAL_DUNGEON_AREA.contains(Player.getPosition()), 7000);

        }
        if (AFTER_GATE_TOP_OF_SLOPE.contains(Player.getPosition())) {
            cQuesterV2.status = "Climbing down rocks";
            if (Utils.clickObj("Rocks", "Climb")) {
                Timer.abc2WaitCondition(() -> !AFTER_GATE_TOP_OF_SLOPE.contains(Player.getPosition()), 4500, 5000);
            }
        }
        if (BOTTOM_OF_SLOPE_NOT_AT_SKELETON_DOOR.contains(Player.getPosition())) {
            cQuesterV2.status = "Going to skeleton Door";
            PathingUtil.localNavigation(BEFORE_SKELETON_DOOR);
        }

        if (!BOSS_ROOM_AREA.contains(Player.getPosition())) {
            for (int i = 0; i < 3; i++) {
                if (Utils.useItemOnObject(BONES, "Tomb doors")) {
                    NPCInteraction.waitForConversationWindow();
                    NpcChat.handle();
                }
            }
            if (Utils.clickObj("Tomb doors", "Open")) {
                Waiting.waitUntil(4500, 500, () -> BOSS_ROOM_AREA.contains(Player.getPosition()));
            }
        }
    }


    int NAZASTEROOL_FIRST = 5353;
    int NAZASTEROOL_SECOND = 5354;
    int NAZASTEROOL_THIRD = 5355;

    String BOSS_NAME = "Nazastarool";

    public boolean moveToSafeTile() {
        if (!SAFE_TILE.equals(Player.getPosition())) {
            cQuesterV2.status = "Going to safe tile";

            if (PathingUtil.clickScreenWalk(SAFE_TILE))
                return Timer.waitCondition(() -> SAFE_TILE.equals(Player.getPosition()), 5000);

        }
        return SAFE_TILE.equals(Player.getPosition());
    }

    public void killBoss() {
        if (!Autocast.isAutocastEnabled(Autocast.FIRE_STRIKE))
            Autocast.enableAutocast(Autocast.FIRE_STRIKE);

        //should pray on 3rd form, can't seem to safespot it
        if (BOSS_ROOM_AREA.contains(Player.getPosition())) {

            if (!Prayer.isPrayerEnabled(Prayer.PRAYERS.PROTECT_FROM_MELEE) && Prayer.getPrayerPoints() > 0)
                Prayer.enable(Prayer.PRAYERS.PROTECT_FROM_MELEE);

            RSNPC[] boss = NPCs.findNearest(BOSS_NAME);
            if (!SAFE_TILE.equals(Player.getPosition())) {
                cQuesterV2.status = "Going to safe tile";

                if (PathingUtil.clickScreenWalk(SAFE_TILE))
                    Timer.waitCondition(() -> SAFE_TILE.equals(Player.getPosition()), 4000);

            } else if (!MyPlayer.isHealthBarVisible() && boss.length == 0) {
                cQuesterV2.status = "Looking at Tomb dolmen";
                if (Utils.clickObj("Tomb dolmen", "Look-at")) {
                    NPCInteraction.waitForConversationWindow();
                    NpcChat.handle();
                    Timer.waitCondition(() -> MyPlayer.isHealthBarVisible(), 3500);
                    boss = NPCs.findNearest(BOSS_NAME);
                }
            }

            if (boss.length > 0) {
                cQuesterV2.status = "Attacking boss";
                moveToSafeTile();
                if (!boss[0].isInCombat() && Utils.clickNPC(boss[0], "Attack", false)) {
                    RSNPC[] finalBoss = boss;
                    Timer.waitCondition(() -> finalBoss[0].isInCombat(), 4500);
                    moveToSafeTile();
                } else if (boss[0].isInCombat()) {
                    int eatAt = AntiBan.getEatAt();
                    CombatUtil.waitUntilOutOfCombat(eatAt);
                    Waiting.waitNormal(500, 20);

                }
                if (boss[0].getHealthPercent() == 0) {
                    Waiting.waitUntil(3500, 50, ChatScreen::isOpen);
                    if (ChatScreen.isOpen())
                        ChatScreen.handle();
                }

            }
        }
    }


    public boolean lootCorpse() {
        if (CombatUtil.isPraying())
            Prayer.disable(Prayer.PRAYERS.PROTECT_FROM_MELEE);

        if (Inventory.find(RASHILIYIA_CORPSE).length > 0)
            return true;

        return Utils.clickGroundItem(RASHILIYIA_CORPSE);

    }


    public void returnToIsland() {
        org.tribot.script.sdk.Prayer.disableAll();
        if (CAIRN_ISLAND_DUNGEON_AREA.contains(Player.getPosition())) {
            if (!DOLMAN_AREA.contains(Player.getPosition()))
                PathingUtil.localNavigation(DOLMAN_AREA.getRandomTile());

            if (Utils.useItemOnObject(RASHILIYIA_CORPSE, "Tomb dolmen")) {
                NPCInteraction.waitForConversationWindow();
                NpcChat.handle();
            }
        }

    }

    int GAME_SETTING = 116;


    @Override
    public void execute() {


        General.sleep(50);
        if (GameState.getSetting(GAME_SETTING) == 0) {
            if (!initialItemReqs.check()) {
                buyItems();
                getItems();
            }
            startQuest();
            step2UseBeltOnTrufitus();

        } else if (GameState.getSetting(GAME_SETTING) == 1
                || GameState.getSetting(GAME_SETTING) == 4
                || GameState.getSetting(GAME_SETTING) == 5) {
            step3GoToAhZaRhoon();

        } else if (GameState.getSetting(GAME_SETTING) == 6) {
            searchFissure();
        } else if (GameState.getSetting(GAME_SETTING) == 7) {
            step5GoToCaveIn();
            goToOldSacks();
            goToGallows();
            goBackToDude();
        } else if (GameState.getSetting(GAME_SETTING) == 8) {
            buryCorpse();
            goToIsland();
            getBeadsAndNecklace();
        } else if (GameState.getSetting(GAME_SETTING) == 9) {
            getBeadsAndNecklace();
            goToBossFight();
        } else if (GameState.getSetting(GAME_SETTING) == 10) {
            goToBossFightPt2();
        } else if (GameState.getSetting(GAME_SETTING) == 11) { // changes from 10->11 after going through ancient metal gates in dungeon
            goToBossFightPt2();
        } else if (GameState.getSetting(GAME_SETTING) == 12) {
            goToBossFightPt2();
            killBoss();
        } else if (GameState.getSetting(GAME_SETTING) == 13) {
            lootCorpse();
        } else if (GameState.getSetting(GAME_SETTING) == 14) {
            goToIsland();
            returnToIsland();
        } else if (GameState.getSetting(GAME_SETTING) == 15) {
            cQuesterV2.taskList.remove(this);
            Utils.closeQuestCompletionWindow();
        }

        // Utils.modSleep();

    }


    //Items Required
    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return cQuesterV2.taskList.size() > 0 && cQuesterV2.taskList.get(0).equals(this);
    }


    @Override
    public String questName() {
        return "Shilo Village (" + GameState.getSetting(GAME_SETTING) + ")";
    }

    @Override
    public boolean checkRequirements() {
        return getGeneralRequirements().stream().allMatch(Requirement::check);
    }

    @Override
    public List<Requirement> getGeneralRequirements() {
        ArrayList<Requirement> req = new ArrayList<>();
        req.add(new QuestRequirement(Quest.JUNGLE_POTION, Quest.State.COMPLETE));
        req.add(new SkillRequirement(Skills.SKILLS.AGILITY, 32));
        req.add(new SkillRequirement(Skills.SKILLS.CRAFTING, 20));
        return req;
    }

    @Override
    public List<ItemRequirement> getBuyList() {
        return null;
    }

    @Override
    public boolean isComplete() {
        return Quest.SHILO_VILLAGE.getState().equals(Quest.State.COMPLETE);
    }
}
