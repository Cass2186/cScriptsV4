package scripts.QuestPackages.FamilyCrest;

import dax.walker.utils.AccurateMouse;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.Objects;
import org.tribot.api2007.types.*;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Quest;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.GameObject;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.CooksAssistant.CooksAssistant;
import scripts.QuestSteps.*;
import scripts.Requirements.*;
import scripts.Requirements.Util.ConditionalStep;
import scripts.Requirements.Util.Conditions;
import scripts.Tasks.Priority;
import scripts.Timer;

import java.nio.file.Path;
import java.util.*;

public class FamilyCrest implements QuestTask {
    private static FamilyCrest quest;

    public static FamilyCrest get() {
        return quest == null ? quest = new FamilyCrest() : quest;
    }

    int JOHNATHON = 4988;
    int SHRIMPS = 315;
    int SALMON = 329;
    int TUNA = 361;
    int BASS = 365;
    int SWORDFISH = 373;
    int DEATH_RUNE = 560;
    int RUNE_PICKAXE = 1275;
    int RUBY = 1603;
    int RING_MOULD = 1592;
    int NECKLACE_MOULD = 1597;
    int ANTIPOISON = 2446;
    int LAVA_RUNE = 4699;
    int LOBSTER = 379;

    int PERFECT_RING = 773;
    int PERFECT_NECKLACE = 774;
    int MONK_TOP = 544;
    int MONK_BOTTOM = 542;
    int PIECE_1 = 779;
    int PIECE_2 = 780;
    int PIECE_3 = 781;
    int CREST = 782;

    RSTile N_ROOM_TILE = new RSTile(2723, 9710);
    RSTile S_BEFORE_DOOR = new RSTile(2722, 9672);

    RSArea START_AREA = new RSArea(new RSTile(3283, 3401, 0), new RSTile(3278, 3406, 0));
    RSArea CALEB_HOUSE = new RSArea(new RSTile(2820, 3449, 0), new RSTile(2812, 3455, 0));
    RSArea GEM_TRADER_AREA = new RSArea(new RSTile(3284, 3214, 0), new RSTile(3291, 3209, 0));
    RSArea AVAN_AREA = new RSArea(new RSTile(3303, 3276, 0), new RSTile(3293, 3288, 0));
    RSArea BOOT_AREA = new RSArea(new RSTile(2978, 9810, 0), new RSTile(2988, 9803, 0));
    RSArea MINE_ENTRANCE = new RSArea(new RSTile(2698, 3280, 0), new RSTile(2694, 3285, 0));
    RSArea GOLD_ORE_AREA = new RSArea(
            new RSTile[]{
                    new RSTile(2728, 9695, 0),
                    new RSTile(2728, 9684, 0),
                    new RSTile(2738, 9674, 0),
                    new RSTile(2749, 9678, 0),
                    new RSTile(2748, 9699, 0),
                    new RSTile(2741, 9701, 0),
                    new RSTile(2736, 9703, 0)
            }
    );
    RSArea DUNGEON_AREA = new RSArea(new RSTile(2729, 9719, 0), new RSTile(2692, 9665, 0));
    RSArea N_DOOR_AREA = new RSArea(new RSTile(2722, 9710, 0), new RSTile(2724, 9709, 0));
    RSArea S_BEFORE_DOOR_AREA = new RSArea(new RSTile(2724, 9672, 0), new RSTile(2716, 9673, 0));
    RSArea GOLD_DOOR_BEFORE = new RSArea(new RSTile(2727, 9688, 0), new RSTile(2726, 9691, 0));
    RSArea EDGEVILLE_FURNACE = new RSArea(new RSTile(3109, 3496, 0), new RSTile(3107, 3500, 0));
    RSArea BAR = new RSArea(new RSTile(3285, 3493, 0), new RSTile(3282, 3496, 0));
    RSArea LARGE_BAR_AREA = new RSArea(new RSTile(3286, 3493, 0), new RSTile(3275, 3506, 0));
    RSArea BAR_UPSTAIRS = new RSArea(new RSTile(3286, 3486, 1), new RSTile(3275, 3506, 1));
    RSArea BOSS_AREA = new RSArea(new RSTile(3091, 9930, 0), new RSTile(3082, 9943, 0));
    RSArea WHOLE_DUNGEON = new RSArea(new RSTile(2748, 9664, 0), new RSTile(2691, 9719, 0));
    RSArea SAFE_SPOT_AREA = new RSArea(new RSTile(3090, 9929, 0), new RSTile(3082, 9931, 0));
    //Items Required
    ItemReq shrimp, salmon, tuna, bass, swordfish, pickaxe, ruby, ruby2, ringMould, necklaceMould, antipoison, runesForBlasts, gold2, gold,
            perfectRing, perfectNecklace, goldBar, goldBar2, crestPiece1, crestPiece2, crestPiece3, crest;

    // Items Recommended
    ItemReq varrockTele, faladorTele, ardyTele, alkharidTele, catherbyTele;

    Requirement inDwarvenMines, inHobgoblinDungeon, northWallUp, southRoomUp, northRoomUp, northWallDown, southRoomDown, northRoomDown,
            inJollyBoar, inEdgevilleDungeon, crest3Nearby;

    QuestStep talkToCaleb, talkToCalebWithFish, talkToCalebOnceMore, talkToGemTrader, talkToMan, enterDwarvenMine, talkToBoot,
            enterWitchavenDungeon, pullNorthLever, pullSouthRoomLever, pullNorthLeverAgain, pullNorthRoomLever, pullNorthLever3, pullSouthRoomLever2,
            smeltGold, makeRing, makeNecklace, returnToMan, goUpToJohnathon, talkToJohnathon,
            killChronizon, pickUpCrest3, repairCrest, returnCrest;

    ObjectStep goDownToChronizon;
    UseItemOnNpcStep giveJohnathonAntipoison;

    RSArea dwarvenMines = new RSArea(new RSTile(2960, 9696, 0), new RSTile(3062, 9854, 0));
    RSArea hobgoblinDungeon = new RSArea(new RSTile(2691, 9665, 0), new RSTile(2749, 9720, 0));
    RSArea jollyBoar = new RSArea(new RSTile(3271, 3485, 1), new RSTile(3288, 3511, 1));
    RSArea edgevilleDungeon = new RSArea(new RSTile(3073, 9820, 0), new RSTile(3287, 10000, 0));


    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.MONKS_ROBE_TOP, 1, 800),
                    new GEItem(ItemID.MONKS_ROBE_BOTTOM, 1, 500),
                    new GEItem(ItemID.SHRIMPS, 1, 500),
                    new GEItem(ItemID.LUMBRIDGE_TELEPORT, 2, 50),
                    new GEItem(ItemID.STAMINA_POTION[0], 6, 15),
                    new GEItem(ItemID.AMULET_OF_GLORY[2], 1, 15),
                    new GEItem(ItemID.RING_OF_DUELING[0], 2, 35),
                    new GEItem(ItemID.RING_OF_WEALTH[0], 1, 25),
                    new GEItem(ItemID.SALMON, 1, 500),
                    new GEItem(ItemID.TUNA, 1, 500),
                    new GEItem(ItemID.BASS, 1, 500),
                    new GEItem(ItemID.SWORDFISH, 1, 500),
                    new GEItem(ItemID.RUNE_PICKAXE, 1, 30),
                    new GEItem(ItemID.RUBY, 2, 30),
                    new GEItem(ItemID.LOBSTER, 25, 30),
                    new GEItem(ItemID.RING_MOULD, 1, 300),
                    new GEItem(ItemID.NECKLACE_MOULD, 1, 300),
                    new GEItem(ItemID.ANTIPOISON4, 2, 100),
                    new GEItem(ItemID.DEATH_RUNE, 350, 30),
                    new GEItem(ItemID.WATER_RUNE, 500, 30),
                    new GEItem(ItemID.LAVA_RUNE, 900, 30),
                    new GEItem(ItemID.VARROCK_TELEPORT, 5, 30),
                    new GEItem(ItemID.FALADOR_TELEPORT, 5, 30),
                    new GEItem(ItemID.VARROCK_TELEPORT, 5, 30),
                    new GEItem(ItemID.ARDOUGNE_TELEPORT, 5, 30),
                    new GEItem(ItemID.CAMELOT_TELEPORT, 5, 30),
                    new GEItem(ItemID.STAFF_OF_AIR, 1, 100),
                    new GEItem(ItemID.SKILLS_NECKLACE[2], 1, 20),
                    new GEItem(ItemID.STAFF_OF_AIR, 1, 100)
            )
    );


    BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);

    public void buyItems() {
        cQuesterV2.status = "Buying Items";
        buyStep.buyItems();
    }

    public void getInitialItems() {
        cQuesterV2.status = "Getting Items";
        General.println("[Debug]: " + cQuesterV2.status);
        BankManager.open(true);
        BankManager.depositAll(true);
        BankManager.checkEquippedGlory();
        BankManager.withdraw(3, true, ItemID.VARROCK_TELEPORT);
        BankManager.withdraw(3, true, ItemID.CAMELOT_TELEPORT);
        BankManager.withdraw(1, true, SHRIMPS);
        BankManager.withdraw(1, true, SALMON);
        BankManager.withdraw(1, true, TUNA);
        BankManager.withdraw(1, true, BASS);
        BankManager.withdraw(1, true, SWORDFISH);
        BankManager.withdraw(1, true, ItemID.SKILLS_NECKLACE[0]);
        BankManager.withdraw(2, true, ItemID.STAMINA_POTION[0]);
        BankManager.withdraw(1, true, ItemID.RING_OF_DUELING[0]);
        BankManager.close(true);
        Utils.equipItem(ItemID.RING_OF_DUELING[0]);
    }

    public void getItems2() {
        cQuesterV2.status = "Getting Items";
        General.println("[Debug]: " + cQuesterV2.status);
        BankManager.open(true);
        BankManager.depositAll(true);
        BankManager.depositEquipment();
        BankManager.checkEquippedGlory();
        BankManager.withdraw(3, true,
                ItemID.VARROCK_TELEPORT);
        BankManager.withdraw(3, true,
                ItemID.CAMELOT_TELEPORT);
        BankManager.withdraw(1, true, RING_MOULD);
        BankManager.withdraw(1, true, NECKLACE_MOULD);
        BankManager.withdraw(1, true, MONK_BOTTOM);
        BankManager.withdraw(1, true, MONK_TOP);
        BankManager.withdraw(2, true, ItemID.PERFECT_GOLD_BAR);
        BankManager.withdraw(1, true, MONK_TOP);
        Utils.equipItem(MONK_TOP);
        Utils.equipItem(MONK_BOTTOM);
        BankManager.withdraw(1, true, RUNE_PICKAXE);
        BankManager.withdraw(2, true, RUBY);
        BankManager.withdraw(16, true, LOBSTER);
        BankManager.withdraw(2, true,
                ItemID.ARDOUGNE_TELEPORT);
        BankManager.withdraw(2, true,
                ItemID.FALADOR_TELEPORT);
        BankManager.withdraw(1, true,
                ItemID.STAMINA_POTION[0]);
        BankManager.close(true);
    }


    public Map<Integer, QuestStep> loadSteps() {

        setupItemReqs();
        setupConditions();
        setupSteps();
        Map<Integer, QuestStep> steps = new HashMap<>();

        steps.put(0, talkToDimintheis);
        steps.put(1, talkToCaleb);
        steps.put(2, talkToCalebWithFish);
        steps.put(3, talkToCalebOnceMore);
        steps.put(4, talkToGemTrader);
        steps.put(5, talkToMan);

        ConditionalStep goTalkToBoot = new ConditionalStep(talkToBoot);

        steps.put(6, goTalkToBoot);


        ConditionalStep goTalkToJohnathon = new ConditionalStep(goUpToJohnathon);
        goTalkToJohnathon.addStep(inJollyBoar, talkToJohnathon);

        steps.put(8, goTalkToJohnathon);

        ConditionalStep goGiveAntipoisonToJohnathon = new ConditionalStep(goUpToJohnathon);
        goGiveAntipoisonToJohnathon.addStep(inJollyBoar, giveJohnathonAntipoison);

        steps.put(9, goGiveAntipoisonToJohnathon);


        return steps;
    }

    public void setupItemReqs() {

        // Recommended
        varrockTele = new ItemReq("Varrock Teleports", ItemID.VARROCK_TELEPORT, 2);
        faladorTele = new ItemReq("Falador Teleport", ItemID.FALADOR_TELEPORT);
        ardyTele = new ItemReq("Ardounge Teleport", ItemID.ARDOUGNE_TELEPORT);
        //alkharidTele = new ItemReq("Al-Kharid Teleport", ItemCollections.getRingOfDuelings());
        catherbyTele = new ItemReq(ItemID.CAMELOT_TELEPORT);


        // Required
        shrimp = new ItemReq("Shrimps", ItemID.SHRIMPS);
        salmon = new ItemReq("Salmon", ItemID.SALMON);
        tuna = new ItemReq("Tuna", ItemID.TUNA);
        bass = new ItemReq("Bass", ItemID.BASS);
        swordfish = new ItemReq("Swordfish", ItemID.SWORDFISH);

        pickaxe = new ItemReq(ItemID.RUNE_PICKAXE);
        ruby = new ItemReq("Ruby", ItemID.RUBY);
        ruby2 = new ItemReq("Ruby", ItemID.RUBY, 2);
        ringMould = new ItemReq("Ring mould", ItemID.RING_MOULD);
        necklaceMould = new ItemReq("Necklace mould", ItemID.NECKLACE_MOULD);

        //    antipoison = new ItemReq("At least one dose of antipoison or superantipoison", ItemCollections.getAntipoisons());

        runesForBlasts = new ItemReq("Runes for casting each of the 4 blast spells", -1, -1);

        gold = new ItemReq("'perfect' gold ore", ItemID.PERFECT_GOLD_ORE);
        gold2 = new ItemReq("'perfect' gold ore", ItemID.PERFECT_GOLD_ORE, 2);
        goldBar = new ItemReq("'perfect' gold bar", ItemID.PERFECT_GOLD_BAR);
        goldBar2 = new ItemReq("'perfect' gold bar", ItemID.PERFECT_GOLD_BAR, 2);

        perfectRing = new ItemReq("'perfect' ring", PERFECT_RING);
        perfectNecklace = new ItemReq("'perfect' necklace", PERFECT_NECKLACE);

        crest = new ItemReq("Family crest", CREST);
        crestPiece1 = new ItemReq("Crest part", 799);
        //crestPiece1.setTooltip("You can get another from Caleb in Catherby");
        crestPiece2 = new ItemReq("Crest part", 780);
        // crestPiece2.setTooltip("You can get another from Avan north of Al Kharid");
        crestPiece3 = new ItemReq("Crest part", 781);
    }

    private enum LeverState {
        UP,
        DOWN,
        UNKNOWN

    }

    public LeverState updateState(int upID, int downID, LeverState thisLever) {
        RSObject[] northWallUp = Objects.findNearest(20, upID);
        RSObject[] northWallDown = Objects.findNearest(20, downID);
        if (northWallUp.length > 0) {
            Log.log("[Debug]: LeverState is UP");
            return LeverState.UP;
        } else if (northWallDown.length > 0) {
            Log.log("[Debug]: LeverState is down");
            return LeverState.DOWN;
        } else {
            Log.log("[Debug]: LeverState is unknown");
            return thisLever;
        }
    }

    public static LeverState northWallLever = LeverState.UNKNOWN;
    public static LeverState northRoomLever = LeverState.UNKNOWN;
    public static LeverState southRoomLever = LeverState.UNKNOWN;

    public void checkLeverStates() {
        if (northWallLever == LeverState.UNKNOWN || northRoomLever == LeverState.UNKNOWN || southRoomLever == LeverState.UNKNOWN) {
            Log.log("[Debug]: One or more lever state is unknown, going to check them");
            if (northWallLever == LeverState.UNKNOWN) {
                Log.log("[Debug]: North Wall lever is unknown - going to check");
                PathingUtil.localNavigation(new RSTile(2722, 9709, 0));
                northWallLever = updateState(2422, 2421, northWallLever);
            }
            if (northRoomLever == LeverState.UNKNOWN) {
                Log.log("[Debug]: North Wall lever is unknown - going to check");
                PathingUtil.walkToTile(new RSTile(2722, 9709, 0), 3, false);
                northRoomLever = updateState(2426, 2425, northRoomLever);
            }
            if (southRoomLever == LeverState.UNKNOWN) {
                PathingUtil.walkToTile(new RSTile(2721, 9673, 0), 3, false);
                Log.log("[Debug]: North Wall lever is unknown - going to check");
                southRoomLever = updateState(2424, 2423, southRoomLever);
            }
        }
    }

    private void updateLeverStatesWithoutMovement() {
        northWallLever = updateState(2422, 2421, northWallLever);
        northRoomLever = updateState(2426, 2545, northRoomLever);
        southRoomLever = updateState(2424, 2423, southRoomLever);
    }

    public void setupConditions() {
        inDwarvenMines = new AreaRequirement(dwarvenMines);
        inHobgoblinDungeon = new AreaRequirement(hobgoblinDungeon);
        northWallUp = new ObjectCondition(2422, new RSTile(2722, 9710, 0));
        southRoomUp = new ObjectCondition(2424, new RSTile(2724, 9669, 0));
        northRoomUp = new ObjectCondition(2426, new RSTile(2722, 9718, 0));

        northWallDown = new ObjectCondition(2421, new RSTile(2722, 9710, 0));
        southRoomDown = new ObjectCondition(2423, new RSTile(2724, 9669, 0));
        northRoomDown = new ObjectCondition(2425, new RSTile(2722, 9718, 0));

        inJollyBoar = new AreaRequirement(jollyBoar);

        inEdgevilleDungeon = new AreaRequirement(edgevilleDungeon);

        crest3Nearby = new ItemOnTileRequirement(crestPiece3);
    }

    NPCStep talkToDimintheis = new NPCStep("Dimintheis", new RSTile(3280, 3402, 0));

    public void setupSteps() {
        Log.debug("Setting up ");
        talkToDimintheis.addDialogStep("Why would a nobleman live in a dump like this?");
        talkToDimintheis.addDialogStep("So where is this crest?");
        talkToDimintheis.addDialogStep("Ok, I will help you.", "Yes.");

        talkToCaleb = new NPCStep("Caleb", new RSTile(2819, 3452, 0));
        talkToCaleb.addDialogStep("Are you Caleb Fitzharmon?");
        talkToCaleb.addDialogStep("So can I have your bit?");
        talkToCaleb.addDialogStep("Ok, I will get those.");
        talkToCalebWithFish = new NPCStep("Caleb", new RSTile(2819, 3452, 0), shrimp, salmon, tuna, bass, swordfish);

        talkToCalebOnceMore = new NPCStep("Caleb", new RSTile(2819, 3452, 0));
        talkToCalebOnceMore.addDialogStep("Uh.. what happened to the rest of the crest?");

        talkToGemTrader = new NPCStep("Gem trader", new RSTile(3286, 3211, 0));
        talkToGemTrader.addDialogStep("I'm in search of a man named Avan Fitzharmon.");
        talkToMan = new NPCStep("Man", new RSTile(3295, 3275, 0));
        talkToMan.addDialogStep("I'm looking for a man named Avan Fitzharmon.");
        // enterDwarvenMine = new ObjectStep(11867, new RSTile(3019, 3450, 0),
        //         "Talk to Boot in the south western Dwarven Mines.");
        talkToBoot = new NPCStep("Boot", new RSTile(2984, 9810, 0));
        talkToBoot.addDialogStep("Hello. I'm in search of very high quality gold.");
        //  talkToBoot.addSubSteps(enterDwarvenMine);

        enterWitchavenDungeon = new ObjectStep(18270, new RSTile(2699, 3283, 0),
                "Climb-down", hobgoblinDungeon.contains(Player.getPosition()));

        pullNorthLever = new ObjectStep(2421, new RSTile(2722, 9710, 0),
                "Pull");
        pullSouthRoomLever = new ObjectStep(2423, new RSTile(2724, 9669, 0),
                "Pull");

        pullNorthLeverAgain = new ObjectStep(2422, new RSTile(2722, 9710, 0),
                "Pull");

        pullNorthRoomLever = new ObjectStep(2425, new RSTile(2722, 9718, 0),
                "Pull");

        pullNorthLever3 = new ObjectStep(2421, new RSTile(2722, 9710, 0),
                "Pull");

        pullSouthRoomLever2 = new ObjectStep(2424, new RSTile(2724, 9669, 0),
                "Pull");

        returnToMan = new NPCStep("Avan", new RSTile(3295, 3275, 0), perfectRing, perfectNecklace);

        // goUpToJohnathon = new ObjectStep(ObjectID.STAIRCASE_11797, new RSTile(3286, 3494, 0),
        //    "Go upstairs in the Jolly Boar Inn north east of Varrock and talk to Johnathon.", antipoison);

        // talkToJohnathon = new NPCStep("Johnathon", new RSTile(3277, 3504, 1), antipoison);
        giveJohnathonAntipoison = new UseItemOnNpcStep(ItemID.ANTIPOISON4, 4988,
                new RSTile(3277, 3504, 1));

        // goUpToJohnathon.addSubSteps(talkToJohnathon);


        returnCrest = new NPCStep("Dimintheis", new RSTile(3280, 3402, 0), crest);
    }

    public void mineGold() {
        if (GOLD_DOOR_BEFORE.contains(Player.getPosition())) {
            if (Objects.findNearest(20, 2430).length > 0) { // enter gold area
                if (Utils.clickObj(2430, "Open"))
                    Timer.waitCondition(() -> !GOLD_DOOR_BEFORE.contains(Player.getPosition()), 10000, 12000);

                Utils.idle(800, 1600);
            }

        }
        if (PathingUtil.localNavigation(new RSTile(2732, 9681, 0))) {
            if (!Prayer.isPrayerEnabled(Prayer.PRAYERS.PROTECT_FROM_MELEE))
                Prayer.enable(Prayer.PRAYERS.PROTECT_FROM_MELEE);
            Timer.waitCondition(() -> GOLD_ORE_AREA.contains(Player.getPosition()), 10000, 12000);
        }

        if (GOLD_ORE_AREA.contains(Player.getPosition())) {
            Log.info("In gold ore area");
            if (!Prayer.isPrayerEnabled(Prayer.PRAYERS.PROTECT_FROM_MELEE))
                Prayer.enable(Prayer.PRAYERS.PROTECT_FROM_MELEE);

            for (int i = 0; i < 15; i++) {
                Log.info("In gold ore area, I = " + i);
                int num = Inventory.find(ItemID.PERFECT_GOLD_ORE).length;
                checkEat();

                if (num == 2)
                    break;

                Optional<GameObject> mine = Query.gameObjects().idEquals(11371)
                        .actionContains("Mine")
                        .sortedByPathDistance()
                        .findClosest();
                if (mine.map(ore->ore.interact("Mine")).orElse(false)){
                    if (Timer.waitCondition(() -> Player.getAnimation() != -1, 1800, 3300))
                        Timer.waitCondition(() -> Inventory.find(ItemID.PERFECT_GOLD_ORE).length > num,
                                2200, 3800);

                    checkEat();
                }
            }
        }
    }

    public void smeltGold() {
        if (Inventory.find(PERFECT_RING).length < 1 && Inventory.find(ItemID.PERFECT_GOLD_ORE).length == 2) {
            cQuesterV2.status = "Going to Furnace";
            General.println("[Debug]: " + cQuesterV2.status);

            if (Prayer.isPrayerEnabled(Prayer.PRAYERS.PROTECT_FROM_MELEE))
                Prayer.disable(Prayer.PRAYERS.PROTECT_FROM_MELEE);

            PathingUtil.walkToArea(EDGEVILLE_FURNACE);
            if (EDGEVILLE_FURNACE.contains(Player.getPosition()) && Inventory.find(ItemID.PERFECT_GOLD_ORE).length > 0) {

                if (Utils.useItemOnObject(ItemID.PERFECT_GOLD_ORE, 16469))
                    Timer.abc2SkillingWaitCondition(() -> Inventory.find(ItemID.PERFECT_GOLD_ORE).length < 2, 20000, 25000);

                if (Utils.useItemOnObject(ItemID.PERFECT_GOLD_ORE, 16469))
                    Timer.abc2SkillingWaitCondition(() -> Inventory.find(ItemID.PERFECT_GOLD_ORE).length < 1, 20000, 25000);
            }

        }
        if (!BankManager.checkInventoryItems(ItemID.RING_MOULD, ItemID.NECKLACE_MOULD)) {
            buyItems();
            getItems2();
            PathingUtil.walkToArea(EDGEVILLE_FURNACE);
        }
        if (Utils.useItemOnObject(ItemID.PERFECT_GOLD_BAR, 16469))
            Timer.waitCondition(() -> Interfaces.get(446, 10) != null, 6000, 9000);
        if (Interfaces.get(446, 10) != null) {
            if (Interfaces.get(446, 10).click())
                Timer.waitCondition(() -> Inventory.find(ItemID.PERFECT_GOLD_BAR).length < 2, 20000, 25000);
        }


        if (Utils.useItemOnObject(ItemID.PERFECT_GOLD_BAR, 16469))
            Timer.waitCondition(() -> Interfaces.get(446, 24) != null, 7000, 9000);
        if (Interfaces.get(446, 24) != null) {
            if (Interfaces.get(446, 24).click())
                Timer.abc2SkillingWaitCondition(() -> Inventory.find(ItemID.PERFECT_GOLD_BAR).length < 1, 20000, 25000);
        }

    }

    public void getItems3() {
        cQuesterV2.status = "Getting Items for last brother.";
        General.println("[Debug]: " + cQuesterV2.status);
        BankManager.open(true);
        BankManager.depositAll(true);
        BankManager.depositEquipment();
        BankManager.checkEquippedGlory();
        BankManager.checkCombatBracelet();
        BankManager.withdraw(2, true, ANTIPOISON);
        BankManager.withdraw(1, true, PIECE_1);
        BankManager.withdraw(1, true, PIECE_2);
        BankManager.withdraw(350, true, DEATH_RUNE);
        BankManager.withdraw(500, true, ItemID.WATER_RUNE);
        BankManager.withdraw(900, true, LAVA_RUNE);
        BankManager.withdraw(1, true, ItemID.STAFF_OF_AIR);
        Utils.equipItem(ItemID.STAFF_OF_AIR);
        BankManager.withdraw(16, true, LOBSTER);
        BankManager.withdraw(3, true, ItemID.VARROCK_TELEPORT);
        BankManager.withdraw(1, true, ItemID.STAMINA_POTION[0]);
        BankManager.close(true);
    }

    public void step10() {
        cQuesterV2.status = "Going to Last Brother";
        General.println("[Debug]: " + cQuesterV2.status);
        if (!BAR_UPSTAIRS.contains(Player.getPosition()) && !LARGE_BAR_AREA.contains(Player.getPosition())) {
            if (Inventory.find(ItemID.VARROCK_TELEPORT).length > 0 && !BAR.contains(Player.getPosition())) {
                if (AccurateMouse.click(Inventory.find(ItemID.VARROCK_TELEPORT)[0], "Break"))
                    Utils.idle(3500, 6000);
            }
            PathingUtil.walkToArea(BAR);
        }
        if (LARGE_BAR_AREA.contains(Player.getPosition())) {
            if (Utils.clickObj("Staircase", "Climb-up"))
                Timer.waitCondition(() -> Player.getPosition().getPlane() == 1, 9000);

            if (NpcChat.talkToNPC("Avan")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                NPCInteraction.handleConversation();
            }
        }
        if (BAR_UPSTAIRS.contains(Player.getPosition())) {
            Walking.blindWalkTo(new RSTile(3276, 3504, 1));

            Utils.idle(4500, 7000);
            NpcChat.talkToNPC("Johnathon");
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }
    }

    public void step12() {
        if (Inventory.find(782).length < 1) {
            Autocast.enableAutocast(Autocast.WIND_BLAST);
            if (Autocast.isAutocastEnabled(Autocast.WIND_BLAST) && (Inventory.find(PIECE_3).length < 1 || Inventory.find(CREST).length < 1)) {
                cQuesterV2.status = "Going to final boss";
                General.println("[Debug]: " + cQuesterV2.status);
                PathingUtil.walkToArea(BOSS_AREA);
            }
            if (BOSS_AREA.contains(Player.getPosition()) && Inventory.find(781).length < 1) {

                if (!SAFE_SPOT_AREA.contains(Player.getPosition())) {
                    Walking.blindWalkTo(new RSTile(3085, 9931));
                    Timer.waitCondition(() -> SAFE_SPOT_AREA.contains(Player.getPosition()), 6000, 8000);
                }
                if (SAFE_SPOT_AREA.contains(Player.getPosition())) {
                    RSNPC[] target = NPCs.findNearest("Chronozon");
                    if (target.length > 0 && Inventory.find(781).length < 1) {
                        while (Combat.isUnderAttack() && !target[0].isInteractingWithMe()) {
                            General.sleep(100);
                            General.println("[Debug]: Under attack by a spider, will kill it before attacking boss.");
                            Timer.waitCondition(() -> !Combat.isUnderAttack() || Combat.getHPRatio() < 60, 5000, 8000);
                            checkEat();
                        }

                        if (AccurateMouse.click(target[0], "Attack"))
                            Utils.idle(3000, 6000);

                        if (target[0].isInCombat()) {
                            checkEat();
                            Autocast.enableAutocast(Autocast.WATER_BLAST);

                            if (AccurateMouse.click(target[0], "Attack"))
                                Timer.waitCondition(() -> target[0].isInCombat(), 5000, 7000);

                            Utils.idle(1500, 3500);

                            if (GroundItems.find(781).length > 0)
                                return;
                        }
                        if (target[0].isInCombat()) {
                            checkEat();
                            Autocast.enableAutocast(Autocast.EARTH_BLAST);

                            if (AccurateMouse.click(target[0], "Attack"))
                                Timer.waitCondition(() -> target[0].isInCombat(), 5000, 7000);

                            Utils.idle(1500, 3500);

                            if (GroundItems.find(781).length > 0)
                                return;
                        }
                        if (target[0].isInCombat()) {
                            checkEat();
                            Autocast.enableAutocast(Autocast.FIRE_BLAST);

                            if (AccurateMouse.click(target[0], "Attack"))
                                Timer.waitCondition(() -> target[0].isInCombat(), 5000, 7000);

                            Utils.idle(1500, 3500);

                            if (GroundItems.find(781).length > 0)
                                return;
                        }
                    }
                }
            }
        }
    }

    int eatAt = General.random(40, 60);

    public void checkEat() {
        if (Combat.getHPRatio() < eatAt) {
            if (Inventory.find(LOBSTER).length > 0) {
                EatUtil.eatFood();
                eatAt = General.random(40, 70);
                General.println("[Debug]: Next eating at " + eatAt);
            }
        }
        RSItem[] antiPoison = Inventory.find(ANTIPOISON);
        if (Game.getSetting(102) > 0) {

            if (antiPoison.length > 0)
                antiPoison[0].click("Drink");
        }
    }

    public void lootCrest() {
        RSGroundItem[] crest = GroundItems.find(781);
        if (crest.length > 0) {
            if (AccurateMouse.click(crest[0], "Take"))
                Timer.waitCondition(() -> Inventory.find(781).length > 0, 6000, 9000);
        }
    }

    public void step13() {
        if (Inventory.find(PIECE_3).length > 0 || Inventory.find(CREST).length > 0) {
            cQuesterV2.status = "Finishing Quest";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(START_AREA);
            if (Inventory.find(PIECE_3).length > 0) {
                cQuesterV2.status = "Combining pieces";
                General.println("[Debug]: " + cQuesterV2.status);
                Utils.useItemOnItem(PIECE_2, PIECE_3);
            }
            if (START_AREA.contains(Player.getPosition()) && Inventory.find(CREST).length > 0) {
                if (NpcChat.talkToNPC("Dimintheis")) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                }
            }
        }
    }

    RSArea SOUTH_ROOM = new RSArea(new RSTile(2724, 9666, 0), new RSTile(2715, 9671, 0));
    RSArea NORTH_ROOM = new RSArea(new RSTile(2724, 9711, 0), new RSTile(2716, 9718, 0));

    public void handleLevelPuzzle() {
        if (Inventory.find(ItemID.PERFECT_GOLD_ORE).length < 1 &&
                Inventory.find(ItemID.PERFECT_GOLD_BAR).length < 2 &&
                Inventory.find(ItemID.PERFECT_RING).length < 1) {

            if (!WHOLE_DUNGEON.contains(Player.getPosition())) {
                cQuesterV2.status = "Doing puzzle";
                General.println("[Debug]: " + cQuesterV2.status);
                PathingUtil.walkToArea(MINE_ENTRANCE);
                if (Utils.clickObj(18270, "Climb-down"))
                    Timer.waitCondition(() -> WHOLE_DUNGEON.contains(Player.getPosition()), 7000, 9000);

                Utils.idle(400, 2000);
            }


            if (DUNGEON_AREA.contains(Player.getPosition())) {
                cQuesterV2.status = "Puzzle step 1: N-Wall up";
                PathingUtil.blindWalkToTile(N_ROOM_TILE);
                //game setting is 0
                pullLever(2421, 2422);

                cQuesterV2.status = "Puzzle step 2: S-Room up";
                PathingUtil.blindWalkToArea(S_BEFORE_DOOR_AREA);

                if (Utils.clickObj(2429, "Open"))// enters south room
                    Timer.waitCondition(() -> SOUTH_ROOM.contains(Player.getPosition()), 10000, 12000);

                Utils.idle(500, 1500);

                if (!SOUTH_ROOM.contains(Player.getPosition())) {
                    if (Utils.clickObj(2429, "Open"))// enters south room
                        Timer.waitCondition(() -> SOUTH_ROOM.contains(Player.getPosition()), 10000, 12000);

                    Utils.idle(500, 1500);
                }
                if (SOUTH_ROOM.contains(Player.getPosition())) {
                    Utils.idle(500, 1500);
                    pullLever(2423, 2424); // step 2 - pull south down
                }

                cQuesterV2.status = "Puzzle step 3: N-Wall down";
                if (Utils.clickObj(2427, "Open")) // leaves south room
                    Timer.waitCondition(() -> S_BEFORE_DOOR_AREA.contains(Player.getPosition()), 10000, 12000);

                Utils.idle(500, 1500);

                Utils.blindWalkToTile(N_ROOM_TILE);// step 3 - going to pull N-wall down
                pullLever(2422, 2421);

                cQuesterV2.status = "Puzzle step 4: N-Room up";
                if (Utils.clickObj(2431, "Open")) {//  entering north room
                    Timer.waitCondition(() -> NORTH_ROOM.contains(Player.getPosition()), 10000, 12000);

                    Utils.idle(800, 1600);
                }
                if (NORTH_ROOM.contains(Player.getPosition())) {
                    pullLever(2425, 2426); // step 4 - pulling N-room up

                    cQuesterV2.status = "Puzzle step 5: N-Wall up";
                    if (Utils.clickObj(2431, "Open"))// - leaving N Room
                        Timer.waitCondition(() -> !NORTH_ROOM.contains(Player.getPosition()), 10000, 12000);
                    Utils.idle(500, 1400);
                }

                pullLever(2421, 2422); // step 5 - pulling N-wall-ip

                cQuesterV2.status = "Puzzle step 6: S-Room down";
                PathingUtil.blindWalkToArea(S_BEFORE_DOOR_AREA);
                if (Utils.clickObj(2427, "Open"))
                    Timer.waitCondition(() -> SOUTH_ROOM.contains(Player.getPosition()), 10000, 12000);

                Utils.idle(500, 1000);
                checkEat();
                pullLever(2424, 2423);

                if (Utils.clickObj(2429, "Open")) // leaves south room
                    Timer.waitCondition(() -> S_BEFORE_DOOR_AREA.contains(Player.getPosition()), 10000, 12000);

                Utils.idle(500, 1000);
                checkEat();
            }

            if (Walking.blindWalkTo(GOLD_DOOR_BEFORE.getRandomTile()))
                Timer.waitCondition(() -> GOLD_DOOR_BEFORE.contains(Player.getPosition()), 10000, 12000);

            if (GOLD_DOOR_BEFORE.contains(Player.getPosition())) {
                if (Objects.findNearest(20, 2430).length > 0) { // enter gold area
                    if (AccurateMouse.click(Objects.findNearest(20, 2430)[0], "Open"))
                        Timer.waitCondition(() -> !GOLD_DOOR_BEFORE.contains(Player.getPosition()), 10000, 12000);

                    Utils.idle(800, 1600);
                }
            }

            if (PathingUtil.localNavigation(new RSTile(2732, 9681, 0))) {
                if (!Prayer.isPrayerEnabled(Prayer.PRAYERS.PROTECT_FROM_MELEE))
                    Prayer.enable(Prayer.PRAYERS.PROTECT_FROM_MELEE);
                Timer.waitCondition(() -> GOLD_ORE_AREA.contains(Player.getPosition()), 10000, 12000);
            }

            if (GOLD_ORE_AREA.contains(Player.getPosition())) {
                if (!Prayer.isPrayerEnabled(Prayer.PRAYERS.PROTECT_FROM_MELEE))
                    Prayer.enable(Prayer.PRAYERS.PROTECT_FROM_MELEE);

                for (int i = 0; i < 25; i++) {
                    int num = Inventory.find(ItemID.PERFECT_GOLD_ORE).length;
                    checkEat();

                    if (num == 2)
                        break;

                    if (Utils.clickObj(11371, "Mine")) { //gold rocks
                        if (Timer.waitCondition(() -> Player.getAnimation() != -1, 1800, 3300))
                            Timer.waitCondition(() -> Inventory.find(ItemID.PERFECT_GOLD_ORE).length > num,
                                    2200, 3200);

                        checkEat();
                    }
                }
            }
        }
    }

    public boolean leaveNorthRoom() {
        if (NORTH_ROOM.contains(Player.getPosition())) {
            if (Utils.clickObj(2431, "Open")) {//  entering north room
                Timer.waitCondition(() -> !NORTH_ROOM.contains(Player.getPosition()), 10000, 12000);
            }
        }
        return !NORTH_ROOM.contains(Player.getPosition());
    }

    public boolean enterNorthRoom() {
        if (!NORTH_ROOM.contains(Player.getPosition())) {
            Utils.blindWalkToTile(N_ROOM_TILE);
            if (Utils.clickObj(2431, "Open")) {//  entering north room
                Timer.waitCondition(() -> NORTH_ROOM.contains(Player.getPosition()), 10000, 12000);
            }
        }
        return NORTH_ROOM.contains(Player.getPosition());
    }


    public boolean pullLever(int idBefore, int idAfter) {
        if (Objects.findNearest(20, idBefore).length > 0) {
            if (Utils.clickObj(idBefore, "Pull"))
                return Timer.waitCondition(() -> Objects.findNearest(20, idAfter).length > 0, 10000, 12000);
        }

        return false;
    }

    public boolean leaveSouthRoom() {
        if (SOUTH_ROOM.contains(Player.getPosition())) {
            RSObject[] doors = Objects.findNearest(7, "Door");
            for (RSObject d : doors) {
                if (Utils.clickObject(d, "Open"))
                    Timer.waitCondition(() -> !SOUTH_ROOM.contains(Player.getPosition()), 4000, 6000);

                if (!SOUTH_ROOM.contains(Player.getPosition()))
                    return true;
            }
        }
        return !SOUTH_ROOM.contains(Player.getPosition());
    }

    public boolean enterSouthRoom() {
        if (!SOUTH_ROOM.contains(Player.getPosition())) {
            PathingUtil.blindWalkToArea(S_BEFORE_DOOR_AREA);
            RSObject[] doors = Objects.findNearest(7, "Door");
            for (RSObject d : doors) {
                if (Utils.clickObject(d, "Open"))
                    Timer.waitCondition(() -> SOUTH_ROOM.contains(Player.getPosition()), 4000, 6000);

                if (SOUTH_ROOM.contains(Player.getPosition()))
                    return true;
            }
        }
        return SOUTH_ROOM.contains(Player.getPosition());
    }

    public void pullSouthRoomLeverUp() {
        cQuesterV2.status = "Puzzle step 2: S-Room up";
        if (enterSouthRoom()) {
            Utils.idle(500, 1500);
            pullLever(2423, 2424); // step 2 - pull south down
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
        Waiting.waitUniform(50,100);
        if (!checkRequirements()) {
            cQuesterV2.taskList.remove(this);
            return;
        }
        setupSteps();

        if (Game.getSetting(148) == 0) {
            buyItems();
            getInitialItems();
        }
        int gameSetting = Game.getSetting(QuestVarPlayer.QUEST_FAMILY_CREST.getId());
        Log.debug("Family Crest gameSetting is " + gameSetting);
        Map<Integer, QuestStep> steps = loadSteps();
        Optional<QuestStep> step = Optional.ofNullable(steps.get(gameSetting));
        step.ifPresent(QuestStep::execute);
        if (Game.getSetting(148) == 4) {
            getItems2();
            //step4();
        }
        if (Game.getSetting(148) == 7) {

            // ConditionalStep getGold = new ConditionalStep(enterWitchavenDungeon);
            if (new Conditions(perfectNecklace, perfectRing).check()) {
                Log.log("[Debug]: Return to man");
                returnToMan.execute();
            } else if (gold2.check() || goldBar2.check()) {
                smeltGold();
            } else if (northRoomLever == LeverState.UP && southRoomLever == LeverState.DOWN) {
                mineGold();
            } else if (northRoomLever == LeverState.UP && northWallLever == LeverState.UP) {
                Log.log("[Debug]: Pull South Room lever 2");
                enterSouthRoom();
                pullSouthRoomLever2.execute();
                leaveSouthRoom();

                updateLeverStatesWithoutMovement();
            } else if (northRoomLever == LeverState.UP && northWallLever == LeverState.DOWN) {
                Log.log("[Debug]: Pull North Lever 3");
                leaveNorthRoom();
                pullNorthLever3.execute();

                updateLeverStatesWithoutMovement();
            } else if (southRoomLever == LeverState.UP && northWallLever == LeverState.DOWN) {
                Log.log("[Debug]:pullNorthRoomLever");
                enterNorthRoom();
                pullNorthRoomLever.execute();

                updateLeverStatesWithoutMovement();
            } else if (southRoomLever == LeverState.UP) {
                Log.log("[Debug]:pullNorthLeverAgain");
                leaveSouthRoom();
                pullNorthLeverAgain.execute();


                updateLeverStatesWithoutMovement();
            } else if (northWallLever == LeverState.UP) {
                Log.log("[Debug]:pullSouthRoomLever");
                pullSouthRoomLeverUp();
                updateLeverStatesWithoutMovement();
            } else if (northWallLever == LeverState.DOWN) {
                cQuesterV2.status = "Puzzle step 1: N-Wall up";
                Log.log("[Debug]:pullNorthLever");
                if (PathingUtil.localNavigation(new RSTile(2722, 9709, 0)))
                    PathingUtil.movementIdle();

                pullLever(2421, 2422);
                updateLeverStatesWithoutMovement();

            } else if (inHobgoblinDungeon.check()) {
                Log.log("[Debug]:followPathAroundEast");
                PathingUtil.localNavigation(new RSTile(2721, 9700, 0));
                checkLeverStates();
            } else {
                Waiting.waitNormal(30, 60);
                enterWitchavenDungeon.execute();
            }

        }
        if (Game.getSetting(148) == 8) {
            getItems3();
            step10();
        }
        if (Game.getSetting(148) == 9) {
            //  giveJohnathonAntipoison.execute();
        }
        if (Game.getSetting(148) == 10) {
            step12();
            lootCrest();
            step13();
        }
        if (Game.getSetting(148) == 11) {
            Utils.closeQuestCompletionWindow();
            cQuesterV2.taskList.remove(this);
        }
    }

    @Override
    public String questName() {
        return "Family Crest";
    }

    @Override
    public boolean checkRequirements() {
        if (Skills.getActualLevel(Skills.SKILLS.MINING) < 41 || Skills.getActualLevel(Skills.SKILLS.SMITHING) < 40
                || Skills.getActualLevel(Skills.SKILLS.CRAFTING) < 40 || Skills.getActualLevel(Skills.SKILLS.MAGIC) < 59
                || Skills.getActualLevel(Skills.SKILLS.DEFENCE) < 40) {
            Log.log("[Debug]: Missing skill req for family crest");

            return false;
        }
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
        return Quest.FAMILY_CREST.getState().equals(Quest.State.COMPLETE);
    }
}
