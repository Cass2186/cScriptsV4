package scripts.QuestPackages.HeroesQuest;

import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Waiting;
import scripts.*;
import scripts.EntitySelector.Entities;
import scripts.EntitySelector.finders.prefabs.ObjectEntity;
import scripts.GEManager.GEItem;
import scripts.QuestSteps.*;
import scripts.Requirements.*;
import scripts.Requirements.Util.Operation;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class HeroesQuestBlackArmsGang implements QuestTask {
    private static HeroesQuestBlackArmsGang quest;

    public static HeroesQuestBlackArmsGang get() {
        return quest == null ? quest = new HeroesQuestBlackArmsGang() : quest;
    }

    // NPCs
    int KATRINE = 5210;
    int GRUBOR = 4916;
    int ENTRANA_FIREBIRD = 4927;
    public static final int TROBERT = 4917;
    public static final int SETH = 4918;
    public static final int GRIP = 4919;
    public static final int ALFONSE_THE_WAITER = 4920;
    public static final int CHARLIE_THE_COOK = 4921;
    public static final int ICE_QUEEN = 4922;
    public static final int ACHIETTIES = 4923;
    public static final int HELEMOS = 4924;
    public static final int STRAVEN = 5212;
    public static final int JONNY_THE_BEARD = 5213;
    public static final int CURATOR_HAIG_HALEN = 5214;
    public static final int KING_ROALD_5215 = 5215;
    RSTile CATHERBY_RANGE_TILE = new RSTile(2816, 3442, 0);


    int ROCK_SLIDE = 2634;


    ItemReq iceGloves = new ItemReq(ItemID.ICE_GLOVES, 1, true);
    ItemReq equippedIceGloves = new ItemReq(ItemID.ICE_GLOVES, 1, true);
    ItemReq fishingBait = new ItemReq(ItemID.FISHING_BAIT);
    ItemReq jailKey = new ItemReq(ItemID.JAIL_KEY);
    ItemReq dustyKey = new ItemReq(ItemID.DUSTY_KEY);
    ItemReq harralanderUnf = new ItemReq(ItemID.HARRALANDER_POTION_UNF);
    ItemReq pickaxe = new ItemReq(ItemID.MITHRIL_PICKAXE); // can be any
    ItemReq blamishSlime = new ItemReq(ItemID.BLAMISH_SNAIL_SLIME);
    ItemReq blamishOil = new ItemReq(ItemID.BLAMISH_OIL);
    ItemReq oilRod = new ItemReq(ItemID.OILY_FISHING_ROD);
    ItemReq lavaEel = new ItemReq(ItemID.LAVA_EEL);
    ItemReq rawLavaEel = new ItemReq(ItemID.RAW_LAVA_EEL);

    ItemReq thievesArmband = new ItemReq(ItemID.THIEVES_ARMBAND);

    //rangedMage = new ItemReq("A ranged or magic attack method", -1, -1);

    ItemReq miscKey = new ItemReq(ItemID.MISCELLANEOUS_KEY);
    ItemReq blackFullHelm = new ItemReq(ItemID.BLACK_FULL_HELM, 1, true);
    ItemReq blackPlatebody = new ItemReq(ItemID.BLACK_PLATEBODY, 1, true);
    ItemReq blackPlatelegs = new ItemReq(ItemID.BLACK_PLATELEGS, 1, true);
    ItemReq idPapers = new ItemReq(ItemID.ID_PAPERS);
    ItemReq candlestick = new ItemReq(ItemID.PETES_CANDLESTICK);
    ItemReq gripsKey = new ItemReq(ItemID.GRIPS_KEYRING);

    ItemReq fireFeather = new ItemReq(ItemID.FIRE_FEATHER);


    NPCStep talkToAchietties = new NPCStep("Achietties", new RSTile(2904, 3511, 0),
            new String[]{
                    "I'm a hero, may I apply to join?",
                    "I'll start looking for all those things then.","Yes."
            });
    NPCStep talkToGerrant = new NPCStep(2891, new RSTile(3013, 3224, 0),
            new String[]{"I want to find out how to catch a lava eel."});

    UseItemOnItemStep makeBlamishOil = new UseItemOnItemStep(ItemID.HARRALANDER_POTION_UNF,
            ItemID.BLAMISH_SNAIL_SLIME,
            Inventory.find(ItemID.BLAMISH_OIL).length > 0);

    UseItemOnItemStep useOilOnRod = new UseItemOnItemStep(ItemID.BLAMISH_OIL, ItemID.FISHING_ROD,
            Inventory.find(ItemID.OILY_FISHING_ROD).length > 0);

    NPCStep killJailerForKey = new NPCStep("Jailer", new RSTile(2930, 9692, 0));
    NPCStep getDustyFromAdventurer = new NPCStep(4925, new RSTile(2933, 9687, 0),
            new String[]{"So... do you know anywhere good to explore?", "Yes please!"}, jailKey);
    //enterDeeperTaverley = new ObjectStep( ObjectID.GATE_2623, new RSTile(2924, 9803, 0), "Enter the gate to the deeper Taverley dungeon.", dustyKey);
    NPCStep fishLavaEel = new NPCStep(4928, new RSTile(2887, 9767, 0),
            oilRod, fishingBait);
    UseItemOnObjectStep cookLavaEel = new UseItemOnObjectStep(rawLavaEel.getId(), "Range",
            CATHERBY_RANGE_TILE, Inventory.find(ItemID.LAVA_EEL).length > 0);

    /*************************
     *  Black Arm Gang steps
     ************************ */
    NPCStep talkToKatrine = new NPCStep(KATRINE, new RSTile(3185, 3385, 0),
            new String[]{"Is there any way I can get the rank of master thief?"});

    ObjectStep tryToEnterTrobertHouse = new ObjectStep(2626, new RSTile(2810, 3170, 0),
            "Open", NPCInteraction.isConversationWindowUp());

    NPCStep talkToTrobert = new NPCStep(TROBERT, new RSTile(2807, 3174, 0),
            new String[]{
                    "So can you help me get Scarface Pete's candlesticks?",
                    "I volunteer to undertake that mission!"
            });

    ObjectStep enterMansion = new ObjectStep(2627,
            new RSTile(2774, 3187, 0), "Open", NPCInteraction.isConversationWindowUp(),
            idPapers, blackFullHelm, blackPlatebody, blackPlatelegs);

    NPCStep talkToGrip = new NPCStep(GRIP, new RSTile(2775, 3192, 0), idPapers);
    NPCStep talkToGrip2 = new NPCStep(GRIP, new RSTile(2775, 3192, 0));
    ObjectStep getKeyFromGrip = new ObjectStep(2635, new RSTile(2776, 3196, 0));


    GroundItemStep pickupKey = new GroundItemStep(ItemID.GRIPS_KEYRING);

    UseItemOnObjectStep enterTreasureRoom = new UseItemOnObjectStep(ItemID.GRIPS_KEYRING, 2621,
            new RSTile(2764, 3197, 0), NPCInteraction.isConversationWindowUp());

    ObjectStep openChest = new ObjectStep(2632, new RSTile(2766, 3199, 0),
            "Open", Entities.find(ObjectEntity::new)
            .tileEquals(new RSTile(2766, 3199, 0))
            .actionsContains("Search").getFirstResult() != null);

    ObjectStep searchChest = new ObjectStep(2633, new RSTile(2766, 3199, 0),
            "Search", NPCInteraction.isConversationWindowUp());

    NPCStep returnToKatrine = new NPCStep(KATRINE, new RSTile(3185, 3385, 0),
            new String[]{"I have a candlestick now."}, candlestick, fireFeather);

    /**
     * Phoenix Gang steps
     */

    Predicate<RSObject> predicate = Filters.Objects
            .tileEquals(new RSTile(3244, 3383, 0));

    ObjectStep enterPhoenixBase = new ObjectStep(predicate,
            new RSTile(3243, 3383, 0),
            Player.getAnimation() != -1,
            "Climb-down", false);
    NPCStep talkToStraven = new NPCStep(STRAVEN, new RSTile(3247, 9781, 0));
    NPCStep talkToAlfonse = new NPCStep(ALFONSE_THE_WAITER, new RSTile(2792, 3186, 0),
            new String[]{"Do you sell Gherkins?"});
    // getKeyFromPartner = new DetailedQuestStep( "You'll need your partner to give you a miscellaneous key.");
    NPCStep talkToCharlie = new NPCStep(CHARLIE_THE_COOK, new RSTile(2790, 3191, 0),
            new String[]{"I'm looking for a gherkin...",
                    "I want to steal Scarface Pete's candlesticks."});
    RSArea TREASURE_ROOM = new RSArea(new RSTile(2764, 3196, 0), new RSTile(2769, 3199, 0));

    RSArea KITCHEN_AREA = new RSArea(new RSTile(2800, 3190, 0), new RSTile(2787, 3193, 0));
    RSArea GARDEN_AREA = new RSArea(
            new RSTile[]{
                    new RSTile(2787, 3188, 0),
                    new RSTile(2787, 3194, 0),
                    new RSTile(2789, 3194, 0),
                    new RSTile(2789, 3199, 0),
                    new RSTile(2783, 3199, 0),
                    new RSTile(2783, 3188, 0)
            }
    );
    ObjectStep pushWall = new ObjectStep(2629, new RSTile(2787, 3190, 0),
            "Push", GARDEN_AREA.contains(Player.getPosition()));

    RSArea ROOM_BEFORE_CLOSET = new RSArea(new RSTile(2780, 3196, 0), new RSTile(2782, 3193, 0));

    UseItemOnObjectStep useKeyOnDoor = new UseItemOnObjectStep(miscKey.getId(), 2622,
            new RSTile(2781, 3197, 0),
            !ROOM_BEFORE_CLOSET.contains(Player.getPosition()));

    NPCStep killGrip = new NPCStep(GRIP, new RSTile(2775, 3192, 0));
    //  "Wait for your partner to lure Grip into the room next to yours, and kill him with magic/ranged. Afterwards, trade your partner for a candlestick.");
    //  getCandlestick = new DetailedQuestStep( "Get your candlestick from your partner.");
    //	killGrip.addSubSteps(getCandlestick);
    //enterPhoenixBaseAgain = new ObjectStep( ObjectID.LADDER_11803, new RSTile(3244, 3383, 0), "Bring the candlestick back to Straven.");
    NPCStep bringCandlestickToStraven = new NPCStep(STRAVEN, new RSTile(3247, 9781, 0));
    //bringCandlestickToStraven.addSubSteps(enterPhoenixBaseAgain);

    ObjectStep mineEntranceRocks = new ObjectStep(ROCK_SLIDE, new RSTile(2839, 3518, 0),
            "Mine", Player.getAnimation() != -1);
    ObjectStep takeLadder1Down = new ObjectStep(16680, new RSTile(2848, 3513, 0),
            "Climb-down", Objects.findNearest(5, 16680).length == 0);

    ObjectStep takeLadder2Up = new ObjectStep(17385, new RSTile(2824, 9907, 0),
            "Climb-up", Objects.findNearest(5, 17385).length == 0);

    ObjectStep takeLadder3Down = new ObjectStep(16680, new RSTile(2827, 3510, 0),
            "Climb-down", Objects.findNearest(3, 16680).length == 0);

    ObjectStep takeLadder4Up = new ObjectStep(17385, new RSTile(2857, 9917, 0),
            "Climb-up", Objects.findNearest(3, 17385).length == 0);

    ObjectStep takeLadder5Down = new ObjectStep(16680, new RSTile(2859, 3519, 0),
            "Climb-down", Objects.findNearest(3, 16680).length == 0);

    NPCStep killIceQueen = new NPCStep(ICE_QUEEN, new RSTile(2865, 9948, 0));
    // pickupIceGloves = new DetailedQuestStep( "Pick up the ice gloves.", iceGloves);
    //	killIceQueen.addSubSteps(pickupIceGloves);

    NPCStep goToEntrana = new NPCStep(1167, new RSTile(3047, 3236, 0), new String[]{"Yes, okay, I'm ready to go."}, equippedIceGloves);
    NPCStep killFireBird = new NPCStep(ENTRANA_FIREBIRD, new RSTile(2840, 3374, 0), equippedIceGloves);
    GroundItemStep pickupFireFeather = new GroundItemStep(ItemID.FIRE_FEATHER);

    NPCStep finishQuest = new NPCStep(ACHIETTIES, new RSTile(2904, 3511, 0),
            fireFeather, thievesArmband, lavaEel);


    VarplayerRequirement talkedToKatrine = new VarplayerRequirement(188, 7, Operation.GREATER_EQUAL);
    VarplayerRequirement blackArmGangDoorUnlocked = new VarplayerRequirement(188, 8, Operation.GREATER_EQUAL);
    VarplayerRequirement gottenPapers = new VarplayerRequirement(188, 9, Operation.GREATER_EQUAL);
    VarplayerRequirement enteredMansion = new VarplayerRequirement(188, 10, Operation.GREATER_EQUAL);
    VarplayerRequirement talkedToGrip = new VarplayerRequirement(188, 11, Operation.GREATER_EQUAL);
    VarplayerRequirement unlockedCandlestickBlackArm = new VarplayerRequirement(188, 12);
    VarplayerRequirement finishedBlackArm = new VarplayerRequirement(188, 13, Operation.GREATER_EQUAL);
    VarplayerRequirement talkedToStraven = new VarplayerRequirement(188, 2, Operation.GREATER_EQUAL);
    VarplayerRequirement talkedToAlfonse = new VarplayerRequirement(188, 3, Operation.GREATER_EQUAL);
    VarplayerRequirement talkedToCharlie = new VarplayerRequirement(188, 4, Operation.GREATER_EQUAL);
    VarplayerRequirement unlockedCandlestickPhoenix = new VarplayerRequirement(188, 5, Operation.GREATER_EQUAL);
    VarplayerRequirement finishedPhoenix = new VarplayerRequirement(188, 6, Operation.GREATER_EQUAL);
    /*   inSecretRoom = new ZoneRequirement(secretRoom);
        inGarden = new ZoneRequirement(garden1, garden2);
      gripsKeyOnFloor = new ItemOnTileRequirement(gripsKey);

       chestOpen = new ObjectCondition(ObjectID.CHEST_2633); */
    // AreaRequirement inTreasureRoom = new AreaRequirement(treasureRoom);


    RSArea MANSION_AREA = new RSArea(new RSTile(2782, 3188, 0), new RSTile(2759, 3199, 0));
    VarplayerRequirement inBlackArmGang = new VarplayerRequirement(146, 4, Operation.GREATER_EQUAL);


    public void populateSteps() {
        tryToEnterTrobertHouse.setHandleChat(true);
        getKeyFromGrip.setHandleChat(true);
        getKeyFromGrip.addDialogStep("So what do my duties involve?");
        getKeyFromGrip.addDialogStep("Anything I can do now?");
        getKeyFromGrip.addDialogStep("He won't notice me having a quick look.");

    }

    public void setupItemReqs() {


    }

    public void setupConditions() {
        AreaRequirement inTaverleyDungeon = new AreaRequirement(HeroesQuestConst.taverleyDungeon);
        AreaRequirement inDeepTaverleyDungeon = new AreaRequirement(HeroesQuestConst.deepTaverleyDungeon1,
                HeroesQuestConst.deepTaverleyDungeon2,
                HeroesQuestConst.deepTaverleyDungeon3,
                HeroesQuestConst.deepTaverleyDungeon4);

        AreaRequirement inJailCell = new AreaRequirement(HeroesQuestConst.jailCell);
        SkillRequirement has70Agility = new SkillRequirement(Skills.SKILLS.AGILITY, 70);

        AreaRequirement inSecretRoom = new AreaRequirement(HeroesQuestConst.secretRoom);
        AreaRequirement inGarden = new AreaRequirement(HeroesQuestConst.garden1, HeroesQuestConst.garden2);
        // gripsKeyOnFloor = new ItemOnTileRequirement(gripsKey);
        AreaRequirement inTreasureRoom = new AreaRequirement(HeroesQuestConst.treasureRoom);
        ObjectCondition chestOpen = new ObjectCondition(2633);

        AreaRequirement inPhoenixBase = new AreaRequirement(HeroesQuestConst.phoenixBase, HeroesQuestConst.phoenixEntry);
        fishLavaEel.setInteractionString("Bait");

        AreaRequirement inThroneRoom = new AreaRequirement(HeroesQuestConst.iceThrone1, HeroesQuestConst.iceThrone2, HeroesQuestConst.iceThrone3);


        AreaRequirement onEntrana = new AreaRequirement(HeroesQuestConst.entrana);
    }

    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.FALADOR_TELEPORT, 5, 40),
                    new GEItem(ItemID.ARDOUGNE_TELEPORT, 5, 40),
                    new GEItem(ItemID.CAMELOT_TELEPORT, 5, 40),
                    new GEItem(ItemID.BLACK_FULL_HELM, 1, 40),
                    new GEItem(ItemID.BLACK_PLATEBODY, 1, 40),
                    new GEItem(ItemID.BLACK_PLATELEGS, 1, 40),

                    new GEItem(ItemID.GAMES_NECKLACE[0], 1, 35),
                    new GEItem(ItemID.LOBSTER, 25, 35),
                    new GEItem(ItemID.ANTIDRAGON_SHIELD, 1, 200),
                    new GEItem(ItemID.HARRALANDER_POTION_UNF, 1, 50),
                    new GEItem(ItemID.FISHING_ROD, 1, 250),
                    new GEItem(ItemID.FISHING_BAIT, 1, 500),
                    new GEItem(ItemID.STAFF_OF_FIRE, 1, 35),
                    new GEItem(ItemID.CHAOS_RUNE, 350, 35),
                    new GEItem(ItemID.AIR_RUNE, 700, 35),
                    new GEItem(ItemID.PRAYER_POTION[0], 2, 35),
                    new GEItem(ItemID.STAMINA_POTION[0], 4, 35),
                    new GEItem(ItemID.VARROCK_TELEPORT, 10, 35)
            )
    );

    BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);

    InventoryRequirement startInventory = new InventoryRequirement(new ArrayList<>(
            Arrays.asList(
                    new ItemReq(ItemID.FALADOR_TELEPORT, 5, 0),
                    new ItemReq(ItemID.CAMELOT_TELEPORT, 5, 0),
                    new ItemReq(ItemID.ARDOUGNE_TELEPORT, 5, 0),
                    new ItemReq(ItemID.COINS, 2000, 50),
                    new ItemReq(ItemID.AIR_RUNE, 600, 100),
                    new ItemReq(ItemID.CHAOS_RUNE, 200, 25),
                    new ItemReq(ItemID.GAMES_NECKLACE[0], 1, 0, true),
                    new ItemReq(ItemID.LOBSTER, 7, 1),
                    new ItemReq(ItemID.RUNE_PICKAXE, 7, 1),
                    new ItemReq(ItemID.ANTIDRAGON_SHIELD, 1, 0, true, true),
                    new ItemReq(ItemID.HARRALANDER_POTION_UNF, 1, 0),
                    new ItemReq(ItemID.FISHING_BAIT, 1, 0),
                    new ItemReq(ItemID.FISHING_ROD, 1, 0),
                    new ItemReq(ItemID.STAFF_OF_FIRE, 1, 0, true, true),
                    new ItemReq(ItemID.VARROCK_TELEPORT, 10, 0),
                    new ItemReq(ItemID.STAMINA_POTION[0], 2, 0),
                    new ItemReq(ItemID.PRAYER_POTION[0],1,0),
                    new ItemReq(ItemID.ICE_GLOVES, 1, 0, true, true)
            )
    ));


    public void getOilyRod() {
        if (!oilRod.hasItem() && !blamishSlime.hasItem() && !blamishOil.hasItem()) {
            cQuesterV2.status = "Going to get Oily Rod";
            General.println("[Debug]: " + cQuesterV2.status);
            talkToGerrant.execute();
        }
        if (blamishSlime.hasItem()) {
            cQuesterV2.status = "Making oil";
            General.println("[Debug]: " + cQuesterV2.status);
            makeBlamishOil.execute();
        }
        if (blamishOil.hasItem()) {
            cQuesterV2.status = "Using oil on rod";
            General.println("[Debug]: " + cQuesterV2.status);
            useOilOnRod.execute();
        }
    }

    public RSArea JAIL_CELL = new RSArea(new RSTile(2928, 9689, 0), new RSTile(2934, 9683, 0));

    UseItemOnObjectStep keyOnJailCell = new UseItemOnObjectStep(ItemID.JAIL_KEY, 2631,
            new RSTile(2930, 9692, 0),
            JAIL_CELL.contains(Player.getPosition()));

    public boolean getDustyKey() {
        if (!dustyKey.check()) {
            Autocast.enableAutocast(Autocast.FIRE_BOLT);
            cQuesterV2.status = "Getting Dusty Key";
            General.println("[Debug]: Getting a dusty key");
            if (!JAIL_CELL.contains(Player.getPosition())) {
                PathingUtil.walkToTile(new RSTile(2930, 9692, 0), 2, false);
                RSNPC[] jailer = NPCs.find("Jailer");
                if (!jailKey.check() && jailer.length > 0 && CombatUtil.clickTarget(jailer[0])) {
                    CombatUtil.waitUntilOutOfCombat(35);
                    Waiting.waitNormal(200, 20);
                }


                Utils.clickGroundItem(ItemID.JAIL_KEY);

                if (jailKey.check() && !JAIL_CELL.contains(Player.getPosition())) {
                    Walking.blindWalkTo(new RSTile(2930, 9690, 0));
                    PathingUtil.movementIdle();
                    keyOnJailCell.useItemOnObject();
                }
            }
            if (JAIL_CELL.contains(Player.getPosition())) {
                if (Inventory.isFull())
                    EatUtil.eatFood();
                getDustyFromAdventurer.setRadius(4);
                getDustyFromAdventurer.setUseLocalNav(true);
                getDustyFromAdventurer.execute();


            }
            if (dustyKey.check() && JAIL_CELL.contains(Player.getPosition())) {
                if (Utils.clickObject(2631, "Open", false))
                    Timer.waitCondition(() -> !JAIL_CELL.contains(Player.getPosition()), 8000, 12000);
            }
        }
        return dustyKey.check() && !JAIL_CELL.contains(Player.getPosition());

    }


    public void getLavaEel() {
        if (!lavaEel.check() && !rawLavaEel.check()) {
            if (!dustyKey.check()) {
                getDustyKey();
            } else {
                cQuesterV2.status = "Getting raw lava eel";
                General.println("[Debug]: " + cQuesterV2.status);
                fishLavaEel.execute();
            }
        } else if (!lavaEel.check() && rawLavaEel.check()) {
            cQuesterV2.status = "Cooking raw lava eel";
            General.println("[Debug]: " + cQuesterV2.status);
            cookLavaEel.useItemOnObject();
        }
    }


    public void enterHouse() {
        cQuesterV2.status = "Going to Trobert House";
        tryToEnterTrobertHouse.addDialogStep("Four leaved clover.");
        tryToEnterTrobertHouse.execute();
    }

    public void enterMansion() {
        cQuesterV2.status = "Entering Mansion";
        if (!Equipment.isEquipped(ItemID.BLACK_PLATEBODY))
            Utils.equipItem(ItemID.BLACK_PLATEBODY);

        if (!Equipment.isEquipped(ItemID.BLACK_FULL_HELM))
            Utils.equipItem(ItemID.BLACK_FULL_HELM);

        if (!Equipment.isEquipped(ItemID.BLACK_PLATELEGS))
            Utils.equipItem(ItemID.BLACK_PLATELEGS);

        enterMansion.execute();

    }

    public void getGripsKey() {
        if (!MANSION_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Entering Mansion";
            enterMansion.setWaitCond(MANSION_AREA.contains(Player.getPosition()));
            enterMansion.execute();
        } else if (!miscKey.check() && !gripsKey.check()) {
            cQuesterV2.status = "Getting key from Grip";
            talkToGrip.setUseLocalNav(true);
            talkToGrip.addDialogStep("So what do my duties involve?");
            talkToGrip.addDialogStep("Anything I can do now?");
            talkToGrip.addDialogStep("He won't notice me having a quick look.");
            if (Game.getSetting(Quests.HEROES_QUEST.getGameSetting()) == 10)
                talkToGrip.execute();
            if (!miscKey.check()) {
                talkToGrip2.addDialogStep("So what do my duties involve?");
                talkToGrip2.addDialogStep("Anything I can do now?");
                talkToGrip2.addDialogStep("He won't notice me having a quick look.");
                talkToGrip2.execute();
                //  getKeyFromGrip.execute();
            }
        }
    }


    public void searchChest() {
        if (gripsKey.check() && !TREASURE_ROOM.contains(Player.getPosition())) {
            cQuesterV2.status = "Entering treasure room";
            enterTreasureRoom.useItemOnObject();

        } else {
            cQuesterV2.status = "Searching chest";
            openChest.execute();
            PathingUtil.movementIdle();
            searchChest.execute();
        }
    }

    public void goToEntrana() {
        if (!fireFeather.check()) {
            cQuesterV2.status = "Kill Firebird";
            if (Equipment.getItems().length > 2) {
                BankManager.open(true);
                BankManager.depositEquipment();
                BankManager.deposit(ItemID.RUNE_PICKAXE, 1);
                BankManager.checkEquippedGlory();
                BankManager.withdraw(1, true, ItemID.ICE_GLOVES);
                BankManager.close(true);
            }
            Utils.equipItem(ItemID.ICE_GLOVES);
            if (!pickupFireFeather.isItemOnGround()) {
                killFireBird.setInteractionString("Attack");
                killFireBird.setRadius(6);
                killFireBird.execute();
                if (Timer.waitCondition(() -> Combat.isUnderAttack(), 6000, 8000))
                    Timer.waitCondition(() -> GroundItems.find(ItemID.FIRE_FEATHER).length > 0, 10000, 15000);
            }
            pickupFireFeather.execute();
        }
    }

    public boolean checkSkillReqs() {
        SkillRequirement agil = new SkillRequirement(Skills.SKILLS.FISHING, 53);
        SkillRequirement cook = new SkillRequirement(Skills.SKILLS.COOKING, 53);
        SkillRequirement herblore = new SkillRequirement(Skills.SKILLS.HERBLORE, 25);
        SkillRequirement mining = new SkillRequirement(Skills.SKILLS.MINING, 50);
        return agil.meetsSkillRequirement() && cook.meetsSkillRequirement() &&
                herblore.meetsSkillRequirement() && mining.meetsSkillRequirement();
    }

    public void getStartItems() {
        if (!startInventory.check()) {
            cQuesterV2.status = "Getting and buying items";
            Log.log("[Debug]: Getting and buying items");
            buyStep.buyItems();
            BankManager.open(true);
            BankManager.depositEquipment();
            if (inBlackArmGang.check()) {
                startInventory.add(new ItemReq(ItemID.BLACK_PLATELEGS, 1, 0));
                startInventory.add(new ItemReq(ItemID.BLACK_PLATEBODY, 1, 0));
                startInventory.add(new ItemReq(ItemID.BLACK_FULL_HELM, 1, 0));
            }
            startInventory.withdrawItems();
        }
    }

    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        return checkSkillReqs() && Game.getSetting(188) < 13;
    }

    @Override
    public void execute() {
        cQuesterV2.gameSettingInt = 188;
        setupItemReqs();
        setupConditions();
        populateSteps();
        General.println("[Debug]: GAmeSetting 188 is " + Game.getSetting(188));

        if (Game.getSetting(188) == 0) {
            getStartItems();
            iceGloves.setAcceptEquipped(true);
            if (!iceGloves.check()) {
                GetIceGloves.get().execute();
                getStartItems();
            } else {
                cQuesterV2.status = "Starting quest";
                Log.log("[Debug]: starting quest");
                talkToAchietties.execute();
            }
        } else if (Game.getSetting(188) == 1) {
            iceGloves.setAcceptEquipped(true);
            if (!iceGloves.check()) {
                GetIceGloves.get().execute();
                getStartItems();
            } else if (!oilRod.hasItem())
                getOilyRod();
            else if (!lavaEel.check())
                getLavaEel();

                // add ice gloves

            else if (inBlackArmGang.check() && iceGloves.check() && oilRod.check() && lavaEel.check()) {
                cQuesterV2.status = "Going to Katerine";
                talkToKatrine.execute();
            }
        }
        if (inBlackArmGang.check() && iceGloves.check() && oilRod.check() && lavaEel.check()) {
            if (Game.getSetting(188) == 7) {
                enterHouse();

            } else if (Game.getSetting(188) == 8) {
                cQuesterV2.status = "Entering Trobert House";
                talkToTrobert.setUseLocalNav(true);
                talkToTrobert.execute();

            } else if (Game.getSetting(188) == 9) {
                enterMansion();

            } else if (Game.getSetting(188) == 10) {
                getGripsKey();

            } else if (Game.getSetting(188) == 11) {
                getGripsKey();
                searchChest();
                finishQuest.execute();
            } else if (Game.getSetting(188) == 12) {
                goToEntrana();
                cQuesterV2.status = "Getting thieves armband";
                returnToKatrine.execute();
                finishQuest.execute();

            } else if (Game.getSetting(188) == 13) {
                cQuesterV2.status = "Finishing quest";
                finishQuest.execute();
                cQuesterV2.taskList.remove(this);

            }
        } else if (iceGloves.check() && oilRod.check() && lavaEel.check()) {
            cQuesterV2.status = "Pheonix gang steps";
            if (Game.getSetting(188) == 1) {
                enterPhoenixBase.setTileRadius(2);
                enterPhoenixBase.execute();
                cQuesterV2.status = "Pheonix gang - Talking to Straven";
                talkToStraven.execute();
            } else if (Game.getSetting(188) == 2) {
                cQuesterV2.status = "Pheonix gang - Talking to Alfonse";
                talkToAlfonse.execute();
                ;
            } else if (Game.getSetting(188) == 3) {
                cQuesterV2.status = "Pheonix gang - Talking to Charlie";
                talkToCharlie.execute();
            } else if (Game.getSetting(188) == 4) {
                if (KITCHEN_AREA.contains(Player.getPosition())) {
                    cQuesterV2.status = "Pheonix gang - PushWall";
                    pushWall.execute();
                }
                if (GARDEN_AREA.contains(Player.getPosition())) {
                    cQuesterV2.status = "Pheonix gang - PushWall";
                    useKeyOnDoor.setTileRadius(1);
                    useKeyOnDoor.useItemOnObject();
                }
            } else if (Game.getSetting(188) == 5) {
                cQuesterV2.status = "Pheonix gang - Bring Candle stick to Straven";
                goToEntrana();
                enterPhoenixBase.setTileRadius(2);
                enterPhoenixBase.execute();
                bringCandlestickToStraven.execute();
            } else if (Game.getSetting(188) == 6) {
                cQuesterV2.status = "Pheonix gang - Going to entrana";
                if (!fireFeather.check())
                    goToEntrana();
                cQuesterV2.status = "Finishing quest";
                finishQuest.execute();
                cQuesterV2.taskList.remove(this);
            }


        }
    }

    @Override
    public String questName() {
        return "Heroes Quest";
    }

    @Override
    public boolean checkRequirements() {
        return Skills.SKILLS.FISHING.getActualLevel() >= 53;
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




