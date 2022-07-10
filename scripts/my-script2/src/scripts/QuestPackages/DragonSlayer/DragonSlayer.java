package scripts.QuestPackages.DragonSlayer;

import dax.walker.utils.AccurateMouse;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.Objects;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.*;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Quest;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.cache.BankCache;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.Area;
import org.tribot.script.sdk.types.GameObject;
import org.tribot.script.sdk.types.WorldTile;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.WitchsPotion.WitchsPotion;
import scripts.QuestPackages.XMarksTheSpot.XMarksTheSpot;
import scripts.QuestSteps.BuyItemsStep;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.*;
import scripts.Requirements.Util.Conditions;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;
import scripts.Timer;

import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DragonSlayer implements QuestTask {

    private static DragonSlayer quest;

    public static DragonSlayer get() {
        return quest == null ? quest = new DragonSlayer() : quest;
    }

    int silkOnDoorVarbit = 3747;
    int lobsterPotOnDoorVarbit = 3749;
    int unfireBowlOnDoorVarbit = 3748;
    int wizardMindBombOnDoorVarbit = 3750;
    int UNFIRED_BOWL = 1791;
    int WIZARDS_MIND_BOMB = 1907;
    int LOBSTER_POT = 301;
    int SILK = 950;
    int LAW_RUNE = 563;
    int HAMMER = 2347;
    int ANTI_DRAGON_SHIELD = 1540;
    int STEEL_NAILS = 1539;
    int PLANK = 960;
    int COINS = 995;
    int MAZE_KEY = 1542;
    int RUBY_BOLTS_E = 9242;
    int ADAMANT_CROSSBOW = 9183;
    int GREEN_DHIDE_CHAPS = 1099;
    int GREEN_DHIDE_VAMBS = 1065;
    int SNAKE_SKIN_BOOTS = 6328;
    int SNAKE_SKIN_BODY = 6322;
    int SNAKE_SKIN_BANDANNA = 6326;
    int AVAS_ATTRACTOR = 10498;
    int RED_KEY = 1543;
    int ORANGE_KEY = 1544;
    int YELLOW_KEY = 1545;
    int BLUE_KEY = 1546;
    int MAGENTA_KEY = 1547;
    int GREEN_KEY = 1548;
    int MAP_PIECE_1 = 1535;
    int MAP_PIECE_2 = 1537;
    int MAP_PIECE_3 = 1536;
    int MAGIC_DOOR = 25115;
    int WHOLE_MAP = 1538;
    int ADAMANT_FULL_HELM = 1161;
    int ADAMANT_PLATEBODY = 1123;
    int ADAMANT_PLATELEGS = 1073;
    int ADAMANT_BOOTS = 4129;
    int MITH_FULL_HELM = 1159;
    int MITH_PLATELEGS = 1071;
    int MITH_PLATEBODY = 1121;
    int MITH_BOOTS = 4127;
    int RUNE_SCIMITAR = 1333;
    int[] EXTENDED_ANTIFIRE = {22209, 22212, 22215, 22218}; ///super extended anitfire

    RSTile SAFE_SPOT = new RSTile(2852, 9630, 0);

    RSArea CHAMPIONS_GUILD_BOTTOM = new RSArea(new RSTile(3194, 3355, 0), new RSTile(3188, 3362, 0));
    RSArea OZIACH_HOUSE = new RSArea(new RSTile(3071, 3515, 0), new RSTile(3067, 3519, 0));
    RSArea CHAMPIONS_GUILD_TOP = new RSArea(new RSTile(3194, 3350, 1), new RSTile(3188, 3362, 1));
    RSArea INSIDE_MAZE_DOOR = new RSArea(new RSTile(2940, 3246, 0), new RSTile(2938, 3250, 0));

    RSArea MAIN_ROOM_FLOOR_1 = new RSArea(new RSTile[]{new RSTile(2938, 3258, 0), new RSTile(2930, 3258, 0), new RSTile(2930, 3255, 0), new RSTile(2926, 3255, 0), new RSTile(2926, 3243, 0), new RSTile(2938, 3243, 0), new RSTile(2938, 3246, 0), new RSTile(2941, 3246, 0), new RSTile(2941, 3251, 0), new RSTile(2938, 3251, 0)});

    RSArea NW_ROOM_FLOOR_1 = new RSArea(new RSTile(2924, 3252, 0), new RSTile(2922, 3255, 0));
    RSArea WHOLE_FIRST_FLOOR = new RSArea(new RSTile(2942, 3236, 0), new RSTile(2921, 3257, 0));
    RSArea WHOLE_SECOND_FLOOR = new RSArea(new RSTile(2943, 3235, 1), new RSTile(2920, 3260, 1));

    RSArea GHOST_ROOM = new RSArea(new RSTile[]{new RSTile(2931, 3247, 1), new RSTile(2931, 3258, 1), new RSTile(2923, 3258, 1), new RSTile(2920, 3255, 1), new RSTile(2922, 3250, 1), new RSTile(2926, 3250, 1), new RSTile(2926, 3247, 1)});

    RSArea SECOND_FLOOR_LADDER_ROOM = new RSArea(new RSTile[]{new RSTile(2937, 3252, 1), new RSTile(2937, 3257, 1), new RSTile(2933, 3257, 1), new RSTile(2933, 3255, 1), new RSTile(2931, 3255, 1), new RSTile(2931, 3252, 1)});

    RSArea WHOLE_THIRD_FLOOR = new RSArea(new RSTile(2944, 3236, 2), new RSTile(2921, 3260, 2));
    RSArea AFTER_THIRD_FLOOR_DOOR = new RSArea(new RSTile[]{new RSTile(2923, 3250, 2), new RSTile(2923, 3237, 2), new RSTile(2941, 3237, 2), new RSTile(2941, 3243, 2), new RSTile(2936, 3243, 2), new RSTile(2936, 3242, 2), new RSTile(2926, 3242, 2), new RSTile(2926, 3250, 2)});
    RSArea THIRD_FLOOR_CORRIDOR = new RSArea(new RSTile[]{new RSTile(2923, 3250, 2), new RSTile(2923, 3240, 2), new RSTile(2937, 3240, 2), new RSTile(2937, 3236, 2), new RSTile(2941, 3236, 2), new RSTile(2941, 3244, 2), new RSTile(2936, 3244, 2), new RSTile(2936, 3242, 2), new RSTile(2926, 3242, 2), new RSTile(2926, 3250, 2)});

    RSArea ZOMBIE_ROOM = new RSArea(new RSTile(2934, 9639, 0), new RSTile(2931, 9644, 0));
    RSArea WHOLE_BASEMENT = new RSArea(new RSTile(2944, 9637, 0), new RSTile(2919, 9664, 0));
    RSArea MELZAR_AREA = new RSArea(new RSTile[]{new RSTile(2931, 9643, 0), new RSTile(2931, 9647, 0), new RSTile(2932, 9647, 0), new RSTile(2932, 9652, 0), new RSTile(2927, 9652, 0), new RSTile(2927, 9643, 0)});
    RSArea LESSER_DEMON_AREA_1 = new RSArea(new RSTile(2924, 9655, 0), new RSTile(2938, 9652, 0));
    RSArea LESSER_DEMON_AREA_2 = new RSArea(new RSTile(2938, 9647, 0), new RSTile(2934, 9655, 0));
    RSArea CHEST_AREA = new RSArea(new RSTile(2941, 9656, 0), new RSTile(2926, 9657, 0));
    RSArea BOTTOM_AFTER_DOOR_AREA = new RSArea(
            new RSTile[]{
                    new RSTile(2926, 3249, 0),
                    new RSTile(2926, 3255, 0),
                    new RSTile(2930, 3255, 0),
                    new RSTile(2930, 3258, 0),
                    new RSTile(2923, 3258, 0),
                    new RSTile(2923, 3257, 0),
                    new RSTile(2921, 3255, 0),
                    new RSTile(2921, 3253, 0),
                    new RSTile(2923, 3251, 0),
                    new RSTile(2923, 3249, 0)
            }
    );
    RSArea IN_FRONT_OF_DOOR_MINE = new RSArea(new RSTile(3050, 9838, 0), new RSTile(3049, 9842, 0));
    RSArea IN_VAULT = new RSArea(new RSTile(3060, 9836, 0), new RSTile(3051, 9845, 0));
    RSArea ORACLE_AREA = new RSArea(new RSTile(3017, 3497, 0), new RSTile(3012, 3501, 0));
    RSArea IN_FRONT_OF_CELL = new RSArea(new RSTile(3011, 3190, 0), new RSTile(3012, 3187, 0));

    RSArea DOCK_AREA = new RSArea(new RSTile(3049, 3202, 0), new RSTile(3044, 3204, 0));
    RSArea SHIP_DECK = new RSArea(new RSTile(3052, 3207, 1), new RSTile(3044, 3209, 1));
    RSArea BOTTOM_SHIP = new RSArea(new RSTile(3055, 9638, 1), new RSTile(3040, 9641, 1));
    RSArea NEDS_HOUSE = new RSArea(new RSTile(3097, 3260, 0), new RSTile(3100, 3256, 0));

    RSArea ORANGE_DOOR_AREA = new RSArea(new RSTile(2930, 3253, 1), new RSTile(2931, 3253, 1));
    RSArea YELLOW_DOOR_AREA = new RSArea(new RSTile(2924, 3249, 2), new RSTile(2924, 3250, 2));
    RSArea AFTER_YELLOW_DOOR_AREA = new RSArea(
            new RSTile[]{
                    new RSTile(2923, 3250, 2),
                    new RSTile(2926, 3250, 2),
                    new RSTile(2926, 3242, 2),
                    new RSTile(2936, 3242, 2),
                    new RSTile(2936, 3244, 2),
                    new RSTile(2941, 3244, 2),
                    new RSTile(2941, 3237, 2),
                    new RSTile(2923, 3237, 2)
            }
    );
    public static final RSTile[] PATH_AFTER_YELLOW_DOOR = new RSTile[]{new RSTile(2924, 3248, 2), new RSTile(2924, 3245, 2), new RSTile(2924, 3242, 2), new RSTile(2927, 3241, 2), new RSTile(2930, 3241, 2), new RSTile(2933, 3241, 2), new RSTile(2936, 3241, 2), new RSTile(2939, 3239, 2)};

    //Zones
    Area dwarvenMines, melzarsMaze, melzarsBasement, ratRoom1, ratRoom2, ratRoom3, postRatRoom1, postRatRoom2, ghostRoom1, ghostRoom2,
            postGhostRoom1, postGhostRoom2, skeletonRoom1, skeletonRoom2, postSkeletonRoom1, postSkeletonRoom2, postSkeletonRoom3, ladderRoom,
            roomToBasement1, roomToBasement2, zombieRoom, melzarRoom1, melzarRoom2, demonRoom1, demonRoom2, lastMelzarRoom1, lastMelzarRoom2,
            shipHull, shipDeck, crandorSurface, crandorUnderground, elvargArea, karamjaVolcano;

    private void loadAreas() {
        dwarvenMines = Area.fromRectangle(new WorldTile(2960, 9696, 0), new WorldTile(3062, 9854, 0));
        ratRoom1 = Area.fromRectangle(new WorldTile(2926, 3243, 0), new WorldTile(2937, 3254, 0));
        ratRoom2 = Area.fromRectangle(new WorldTile(2930, 3255, 0), new WorldTile(2937, 3257, 0));
        ratRoom3 = Area.fromRectangle(new WorldTile(2938, 3246, 0), new WorldTile(2940, 3250, 0));
        postRatRoom1 = Area.fromRectangle(new WorldTile(2922, 3251, 0), new WorldTile(2925, 3255, 0));
        postRatRoom2 = Area.fromRectangle(new WorldTile(2923, 3255, 0), new WorldTile(2929, 3257, 0));
        ghostRoom1 = Area.fromRectangle(new WorldTile(2926, 3247, 1), new WorldTile(2930, 3257, 1));
        ghostRoom2 = Area.fromRectangle(new WorldTile(2921, 3250, 1), new WorldTile(2925, 3257, 1));
        postGhostRoom1 = Area.fromRectangle(new WorldTile(2931, 3252, 1), new WorldTile(2936, 3254, 1));
        postGhostRoom2 = Area.fromRectangle(new WorldTile(2933, 3255, 1), new WorldTile(2936, 3256, 1));
        skeletonRoom1 = Area.fromRectangle(new WorldTile(2922, 3250, 2), new WorldTile(2932, 3252, 2));
        skeletonRoom2 = Area.fromRectangle(new WorldTile(2921, 3253, 2), new WorldTile(2935, 3257, 2));

        postSkeletonRoom1 = Area.fromRectangle(new WorldTile(2922, 3239, 2), new WorldTile(2925, 3249, 2));
        postSkeletonRoom2 = Area.fromRectangle(new WorldTile(2926, 3237, 2), new WorldTile(2940, 3241, 2));
        postSkeletonRoom3 = Area.fromRectangle(new WorldTile(2936, 3242, 2), new WorldTile(2940, 3243, 2));

        ladderRoom = Area.fromRectangle(new WorldTile(2937, 3237, 1), new WorldTile(2940, 3241, 1));
        roomToBasement1 = Area.fromRectangle(new WorldTile(2937, 3237, 0), new WorldTile(2940, 3245, 0));
        roomToBasement2 = Area.fromRectangle(new WorldTile(2932, 3240, 0), new WorldTile(2936, 3242, 0));

        zombieRoom = Area.fromRectangle(new WorldTile(2931, 9639, 0), new WorldTile(2933, 9644, 0));
        melzarRoom1 = Area.fromRectangle(new WorldTile(2927, 9643, 0), new WorldTile(2930, 9651, 0));
        melzarRoom2 = Area.fromRectangle(new WorldTile(2931, 9646, 0), new WorldTile(2931, 9651, 0));

        demonRoom1 = Area.fromRectangle(new WorldTile(2924, 9652, 0), new WorldTile(2933, 9655, 0));
        demonRoom2 = Area.fromRectangle(new WorldTile(2934, 9647, 0), new WorldTile(2933, 9658, 0));
        melzarsMaze = Area.fromRectangle(new WorldTile(2922, 3237, 0), new WorldTile(2942, 9658, 0));
        melzarsBasement = Area.fromRectangle(new WorldTile(2920, 9639, 0), new WorldTile(1, 2, 0));

        lastMelzarRoom1 = Area.fromRectangle(new WorldTile(2924, 9656, 0), new WorldTile(2942, 9656, 0));
        lastMelzarRoom2 = Area.fromRectangle(new WorldTile(2926, 9657, 0), new WorldTile(2942, 9657, 0));

        shipDeck = Area.fromRectangle(new WorldTile(3041, 3207, 1), new WorldTile(3050, 3209, 2));
        shipHull = Area.fromRectangle(new WorldTile(3041, 9639, 1), new WorldTile(3050, 9641, 1));

        crandorSurface = Area.fromRectangle(new WorldTile(2810, 3228, 0), new WorldTile(2867, 3312, 0));
        crandorUnderground = Area.fromRectangle(new WorldTile(2821, 9600, 0), new WorldTile(2872, 9663, 0));

        elvargArea = Area.fromRectangle(new WorldTile(2846, 9625, 0), new WorldTile(2867, 9651, 0));
        karamjaVolcano = Area.fromRectangle(new WorldTile(2827, 9547, 0), new WorldTile(2867, 9599, 0));
    }

    VarplayerRequirement askedAboutMelzar = new VarplayerRequirement(177, false, 11);
    VarplayerRequirement askedAboutThalzar = new VarplayerRequirement(177, false, 12);
    VarplayerRequirement askedAboutLozar = new VarplayerRequirement(177, false, 13);
    VarplayerRequirement askedAboutShip = new VarplayerRequirement(177, false, 14);
    VarplayerRequirement askedAboutShield = new VarplayerRequirement(177, false, 15);

    Conditions askedAllQuestions = new Conditions(askedAboutShip, askedAboutShield, askedAboutMelzar, askedAboutThalzar, askedAboutLozar);
    VarbitRequirement askedOracleAboutMap = new VarbitRequirement(1832, 1);
    // inDwarvenMines = new ZoneRequirement(dwarvenMines);
    VarplayerRequirement silkUsed = new VarplayerRequirement(177, true, 17);
    VarplayerRequirement unfiredBowlUsed = new VarplayerRequirement(177, true, 18);
    VarplayerRequirement lobsterPotUsed = new VarplayerRequirement(177, true, 19);
    VarplayerRequirement mindBombUsed = new VarplayerRequirement(177, true, 20);

    Conditions thalzarDoorOpened = new Conditions(silkUsed, unfiredBowlUsed, lobsterPotUsed, mindBombUsed);
    ObjectCondition thalzarChest2Nearby = new ObjectCondition(ObjectID.CHEST_2588);


    public void setupConditions() {

        /*hasBoughtBoat = new VarplayerRequirement(176, 3);

        hasRepairedHullOnce = new VarbitRequirement(1835, 1);
        hasRepairedHullTwice = new VarbitRequirement(1836, 1);
        fullyRepairedHull = new VarbitRequirement(1837, 1);

        onCrandorSurface = new ZoneRequirement(crandorSurface);
        inCrandorUnderground = new ZoneRequirement(crandorUnderground);
        inElvargArea = new ZoneRequirement(elvargArea);
        inKaramjaVolcano = new ZoneRequirement(karamjaVolcano);*/

        // unlockedShortcut = new VarplayerRequirement(177, true, 6);
    }

    public boolean RANGED = false;
    public boolean MAGIC = false;
    public boolean MELEE = true;

    // all of these change 0-> 1 upon speaking to oziach THEN
    // change 1-> after guild master has been asked about it
    public int MELZAR_VARBIT = 3742;
    public int THALZAR_VARBIT = 3743;
    public int LOZAR_VARBIT = 3744;
    public int RIGHT_SHIP_VARBIT = 3745;
    public int PROTECT_FROM_D_BREATH_VARBIT = 3746;

    /**
     * METHODS
     */
    public static void checkLevel() {
        if (Skills.getActualLevel(Skills.SKILLS.MAGIC) < 35) {
            //     || Skills.getActualLevel(Skills.SKILLS.PRAYER) < 43)

        }
    }


    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.ADAMANT_FULL_HELM, 1, 300),
                    new GEItem(ItemID.ADAMANT_PLATEBODY, 1, 100),
                    new GEItem(ItemID.ADAMANT_PLATELEGS, 1, 100),
                    new GEItem(ADAMANT_BOOTS, 1, 300),
                    new GEItem(ItemID.RUNE_SCIMITAR, 1, 30),
                    new GEItem(ItemID.EXTENDED_SUPER_ANTIFIRE_POTION[0], 1, 30),
                    new GEItem(ItemID.UNFIRED_BOWL, 1, 300),
                    new GEItem(WIZARDS_MIND_BOMB, 1, 300),
                    new GEItem(LOBSTER_POT, 1, 300),
                    new GEItem(SILK, 1, 300),
                    new GEItem(LAW_RUNE, 5, 30),
                    new GEItem(ANTI_DRAGON_SHIELD, 1, 300),
                    new GEItem(ItemID.HAMMER, 1, 300),
                    new GEItem(STEEL_NAILS, 100, 300),

                    new GEItem(ItemID.PLANK, 3, 50),
                    new GEItem(ItemID.LOBSTER, 30, 50),
                    new GEItem(ItemID.VARROCK_TELEPORT, 5, 50),
                    new GEItem(ItemID.FALADOR_TELEPORT, 5, 50),
                    new GEItem(ItemID.GAMES_NECKLACE[0], 2, 30),
                    new GEItem(ItemID.AMULET_OF_GLORY[2], 2, 30),
                    new GEItem(ItemID.COMBAT_BRACELET[0], 1, 30),
                    new GEItem(ItemID.PRAYER_POTION[0], 3, 30),
                    new GEItem(ItemID.SUPER_COMBAT_POTION[0], 1, 30),
                    new GEItem(ItemID.STAMINA_POTION[0], 5, 15),
                    new GEItem(ItemID.RING_OF_WEALTH[0], 1, 25)
            )
    );

    InventoryRequirement initialItemReqs = new InventoryRequirement(new ArrayList<>(
            Arrays.asList(
                    new ItemReq(ItemID.STAMINA_POTION[0], 1, 0),
                    new ItemReq(ItemID.RING_OF_WEALTH[0], 1, 0, true)
            )
    ));

    BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);

    public void buyItems() {
        cQuesterV2.status
                = "Buying Items";
        buyStep.buyItems();
    }

    public void getItems1() {
        cQuesterV2.status
                = "Getting Items";
        Log.info(cQuesterV2.status
        );
        BankManager.open(true);
        BankManager.depositAll(true);
        BankManager.depositEquipment();
        General.sleep(General.random(400, 800));
        BankManager.checkEquippedGlory();
        BankManager.checkCombatBracelet();
        BankManager.withdraw(5, true, ItemID.VARROCK_TELEPORT);
        BankManager.withdraw(5, true, ItemID.FALADOR_TELEPORT);
        if (RANGED) {
            BankManager.withdraw(1, true, ADAMANT_CROSSBOW);
            BankManager.withdraw(350, true, RUBY_BOLTS_E);
            BankManager.withdraw(1, true, GREEN_DHIDE_CHAPS);
            BankManager.withdraw(1, true, SNAKE_SKIN_BANDANNA);
            BankManager.withdraw(1, true, SNAKE_SKIN_BODY);
            BankManager.withdraw(1, true, SNAKE_SKIN_BOOTS);
            BankManager.withdraw(1, true, AVAS_ATTRACTOR);
            Utils.equipItem(GREEN_DHIDE_CHAPS);
            Utils.equipItem(SNAKE_SKIN_BANDANNA);
            Utils.equipItem(SNAKE_SKIN_BODY);
            Utils.equipItem(SNAKE_SKIN_BOOTS);
            Utils.equipItem(AVAS_ATTRACTOR);
            Utils.equipItem(ADAMANT_CROSSBOW);
            Utils.equipItem(RUBY_BOLTS_E);
        }
        if (MAGIC) {
            BankManager.withdraw(1, true,
                    ItemID.STAFF_OF_AIR);
            BankManager.withdraw(300, true,
                    ItemID.CHAOS_RUNE);
            BankManager.withdraw(1200, true,
                    ItemID.FIRE_RUNE);
            Utils.equipItem(
                    ItemID.STAFF_OF_AIR);
        }
        if (MELEE) {
            if (Skills.getActualLevel(Skills.SKILLS.DEFENCE) >= 30) {
                BankManager.withdraw(1, true, ADAMANT_BOOTS);
                BankManager.withdraw(1, true, ADAMANT_FULL_HELM);
                BankManager.withdraw(1, true, ADAMANT_PLATEBODY);
                BankManager.withdraw(1, true, ADAMANT_PLATELEGS);
                Utils.equipItem(ADAMANT_PLATELEGS);
                Utils.equipItem(ADAMANT_BOOTS);
                Utils.equipItem(ADAMANT_FULL_HELM);
                Utils.equipItem(ADAMANT_PLATEBODY);
            } else if (Skills.getActualLevel(Skills.SKILLS.DEFENCE) >= 20) {
                BankManager.withdraw(1, true, MITH_BOOTS);
                BankManager.withdraw(1, true, MITH_FULL_HELM);
                BankManager.withdraw(1, true, MITH_PLATEBODY);
                BankManager.withdraw(1, true, MITH_PLATELEGS);
                Utils.equipItem(MITH_BOOTS);
                Utils.equipItem(MITH_FULL_HELM);
                Utils.equipItem(MITH_PLATEBODY);
                Utils.equipItem(MITH_PLATELEGS);
            }
            BankManager.withdraw(1, true, RUNE_SCIMITAR);
            Utils.equipItem(RUNE_SCIMITAR);

        }
        BankManager.withdraw(1, true, ANTI_DRAGON_SHIELD);
        BankManager.withdraw(16, true,
                ItemID.LOBSTER);
        BankManager.withdraw(1, true,
                ItemID.SKILLS_NECKLACE[0]);
        BankManager.withdraw(2, true,
                ItemID.STAMINA_POTION[0]);
        BankManager.withdraw(1, true,
                ItemID.RING_OF_DUELING[0]);
        BankManager.close(true);
        Utils.equipItem(
                ItemID.RING_OF_DUELING[0]);
        Utils.equipItem(
                ItemID.COMBAT_BRACELET[0]);
        if (Prayer.getPrayerPoints() < 30) {
            //Utils.clanWarsReset();
        }
    }

    public void startQuest() {
        cQuesterV2.status
                = "Going to start";
        Log.info(cQuesterV2.status
        );
        PathingUtil.walkToArea(CHAMPIONS_GUILD_BOTTOM);
        if (CHAMPIONS_GUILD_BOTTOM.contains(Player.getPosition())) {
            if (NpcChat.talkToNPC("Guildmaster")) {
                NpcChat.handle(true, "Can I have a quest?", "Yes.");
                NPCInteraction.handleConversation("Can I have a quest?", "Yes.");
                Utils.equipItem(ANTI_DRAGON_SHIELD);
            }
        }
    }

    public void goToOziach() {
        cQuesterV2.status
                = "Going to Oziach";
        Log.info(cQuesterV2.status);
        PathingUtil.walkToArea(OZIACH_HOUSE);
        if (NpcChat.talkToNPC("Oziach")) {
            NpcChat.handle(true, "Can you sell me a rune platebody?",
                    "The Guildmaster of the Champions' Guild told me.",
                    "I thought you were going to give me a quest.",
                    "A dragon, that sounds like fun."
            );
        }
    }

    String[] guildMasterArray = {
            "I talked to Oziach...",
            "About my quest to kill the dragon...",
            "How can I find the route to Crandor?",
            "Where can I find the right ship?",
            "Where is Melzar's map piece?",
            "Where is Thalzar's map piece?",
            "Where is Lozar's map piece?",
            "How can I protect myself from the dragon's breath?",
            "Where can I find the right ship?",
            "What's so special about this dragon?"
    };

    // should only be called after step2() is complete otherwise they will appear as if we've talked to master
    public void talkToGuildMasterAboutPiece() {
        if (!askedAllQuestions.check()) {
            // if (Utils.getVarBitValue(MELZAR_VARBIT) == 1 ||
            //          Utils.getVarBitValue(THALZAR_VARBIT) == 1 ||
            //         Utils.getVarBitValue(LOZAR_VARBIT) == 1 ||
            //         Utils.getVarBitValue(RIGHT_SHIP_VARBIT) == 1) {
            cQuesterV2.status = "Asking Questions";
            PathingUtil.walkToArea(CHAMPIONS_GUILD_BOTTOM);
            if (CHAMPIONS_GUILD_BOTTOM.contains(Player.getPosition())) {
                NpcChat.talkToNPC("Guildmaster");
                NPCInteraction.waitForConversationWindow();
                NpcChat.handle("About my quest to kill the dragon...");
                if (!askedAboutShip.check()) {
                    Log.info("Asking about Ship");
                    NpcChat.handle("Where can I find the right ship?");
                }
                if (!askedAboutLozar.check()) {
                    Log.info("Asking about Lozar");
                    NpcChat.handle("Where is Lozar's map piece?");
                }
                if (!askedAboutThalzar.check()) {
                    Log.info("Asking about thalzar");
                    NpcChat.handle("Where is Thalzar's map piece?");
                }
                NPCInteraction.handleConversation(guildMasterArray);
            }


        }
    }

    public void step3() {
        if (Inventory.find(MAZE_KEY).length < 1 && Inventory.find(MAP_PIECE_1).length < 1) {
            // could add a bank check here for map pieces
            if (Inventory.getCount(MAP_PIECE_1) < 1 && checkBankCache(MAP_PIECE_1)) {
                Log.warn("getItems2() executing to get map piece 1");
                getItems2(false);
                return;
            } else {
                Log.info("Passed map check");
            }
            cQuesterV2.status = "Going to Champions Guild";
            Log.info(cQuesterV2.status);
            PathingUtil.walkToArea(CHAMPIONS_GUILD_BOTTOM);
            if (CHAMPIONS_GUILD_BOTTOM.contains(Player.getPosition())) {
                NpcChat.talkToNPC("Guildmaster");
                NpcChat.handle(true, "About my quest to kill the dragon...");
                if (!askedAboutShip.check()) {
                    Log.info("Asking about Ship");
                    NpcChat.handle("Where can I find the right ship?");
                }
                if (!askedAboutLozar.check()) {
                    Log.info("Asking about Lozar");
                    NpcChat.handle("Where is Lozar's map piece?");
                }
                if (!askedAboutThalzar.check()) {
                    Log.info("Asking about thalzar");
                    NpcChat.handle("Where is Thalzar's map piece?");
                }

                NPCInteraction.handleConversation(guildMasterArray);
                if (Inventory.getCount(MAP_PIECE_1) < 1 && checkBankCache(MAP_PIECE_1)) {
                    Log.warn("getItems2() executing to get map piece 1");
                    getItems2(false);
                }
            }
        }
    }

    RSArea SECOND_LADDER_AREA = new RSArea(new RSTile(2940, 3237, 1), new RSTile(2937, 3241, 1));
    RSArea LAST_LADDER_AREA = new RSArea(new RSTile(2940, 3237, 0), new RSTile(2932, 3242, 0));

    public static boolean waitUntilOutOfCombat(int keyId) {

        Timer.waitCondition(() -> {
            General.sleep(General.random(50, 200));

            AntiBan.timedActions();

            if (EatUtil.hpPercent() <= (General.random(40, 65)))
                EatUtil.eatFood();


            RSCharacter target = Combat.getTargetEntity();
            if (target != null)
                return target.getHealthPercent() == 0 || !Combat.isUnderAttack() || !EatUtil.hasFood() || Prayer.getPrayerPoints() < 5;

            return !Combat.isUnderAttack() || !EatUtil.hasFood() || Prayer.getPrayerPoints() < 5;
        }, General.random(20000, 40000));

        AntiBan.resetShouldOpenMenu();

        RSCharacter target = Combat.getTargetEntity();
        if (target != null)
            return target.getHealthPercent() == 0
                    || !Combat.isUnderAttack() || !EatUtil.hasFood()
                    || GroundItems.find(keyId).length > 0
                    || Prayer.getPrayerPoints() < General.random(4, 10);

        return !Combat.isUnderAttack();
    }

    RSArea BOTTOM_FLOOR_ROOM_AREA = new RSArea(
            new RSTile[]{
                    new RSTile(2926, 3248, 0),
                    new RSTile(2926, 3255, 0),
                    new RSTile(2930, 3255, 0),
                    new RSTile(2930, 3258, 0),
                    new RSTile(2923, 3258, 0),
                    new RSTile(2923, 3257, 0),
                    new RSTile(2921, 3255, 0),
                    new RSTile(2921, 3253, 0),
                    new RSTile(2923, 3251, 0),
                    new RSTile(2923, 3248, 0)
            }
    );

    public void navigateMaze() {
        if (Inventory.find(MAP_PIECE_1).length < 1 && Inventory.find(MAZE_KEY).length > 0) {
            Utils.equipItem(ANTI_DRAGON_SHIELD);
            if (!WHOLE_FIRST_FLOOR.contains(Player.getPosition()) &&
                    !WHOLE_BASEMENT.contains(Player.getPosition())
                    && !WHOLE_SECOND_FLOOR.contains(Player.getPosition()) &&
                    !WHOLE_THIRD_FLOOR.contains(Player.getPosition())) {
                cQuesterV2.status
                        = "Going to Melzar's maze";
                Log.info(cQuesterV2.status);
                //middle of first floor
                PathingUtil.walkToTile(new RSTile(2933, 3249, 0));
            }

            if (MAIN_ROOM_FLOOR_1.contains(Player.getPosition()) && Inventory.find(RED_KEY).length < 1) {
                if (MAGIC)
                    Autocast.enableAutocast(Autocast.FIRE_BOLT);

                cQuesterV2.status
                        = "Getting Red Key";
                Log.info(cQuesterV2.status
                );
                RSNPC[] targ = NPCs.findNearest(3969);
                if (targ.length > 0 && CombatUtil.clickTarget(targ[0])) {
                    Timer.abc2WaitCondition(() -> Combat.getHPRatio() < General.random(40, 65)
                            || GroundItems.find(RED_KEY).length > 0, 35000, 45000);

                    checkEat();
                    pickupKey(RED_KEY);
                }
            }

            if (MAIN_ROOM_FLOOR_1.contains(Player.getPosition()) && Inventory.find(RED_KEY).length > 0) {
                PathingUtil.walkToArea(NW_ROOM_FLOOR_1);
                if (Utils.clickObject(16683, "Climb-up", false)) {
                    Timer.abc2WaitCondition(() -> WHOLE_SECOND_FLOOR.contains(Player.getPosition()), 12000, 16000);
                }
            }

            if (BOTTOM_FLOOR_ROOM_AREA.contains(Player.getPosition())) {
                if (Utils.clickObject(16683, "Climb-up", false)) {
                    Timer.waitCondition(() -> WHOLE_SECOND_FLOOR.contains(Player.getPosition()), 12000, 16000);
                }
            }

            if (NW_ROOM_FLOOR_1.contains(Player.getPosition())) {
                cQuesterV2.status = "Going upstairs";
                Log.info(cQuesterV2.status);
                if (Utils.clickObject(16683, "Climb-up", false))
                    Timer.abc2WaitCondition(() -> WHOLE_SECOND_FLOOR.contains(Player.getPosition()), 12000, 16000);

            } else if (GHOST_ROOM.contains(Player.getPosition()) && Inventory.find(ORANGE_KEY).length < 1) {
                cQuesterV2.status = "Getting Orange Key";
                Log.info(cQuesterV2.status);
                RSNPC[] targ = NPCs.findNearest(3975);
                if (targ.length > 0) {

                    if (!Combat.isUnderAttack())
                        CombatUtil.clickTarget(targ[0]);

                    Timer.abc2WaitCondition(() -> Combat.getHPRatio() < General.random(40, 65) ||
                            GroundItems.find(ORANGE_KEY).length > 0, 35000, 45000);
                    checkEat();
                    pickupKey(ORANGE_KEY);
                }

            } else if (GHOST_ROOM.contains(Player.getPosition()) &&
                    Inventory.find(ORANGE_KEY).length > 0) {
                cQuesterV2.status = "Going to third floor";
                Log.info(cQuesterV2.status);
                if (PathingUtil.localNavigation(new RSTile(2930, 3253, 1)))
                    PathingUtil.movementIdle();
                WorldTile doorTile = new WorldTile(2931, 3253, 1);
                Optional<GameObject> door = Query.gameObjects().tileEquals(doorTile)
                        .nameContains("Door")
                        .findClosestByPathDistance();
                RSObject[] orangeDoor = Objects.find(10, Filters.Objects.inArea(ORANGE_DOOR_AREA));

                if (door.map(d -> d.interact("Open")).orElse(false)) {
                    Timer.abc2WaitCondition(() -> !GHOST_ROOM.contains(Player.getPosition()), 12000, 16000);

                    if (Utils.clickObject(16683, "Climb-up", false)) {
                        Timer.abc2WaitCondition(() -> !WHOLE_SECOND_FLOOR.contains(Player.getPosition()), 12000, 15000);
                    }
                }
            }

            if (WHOLE_THIRD_FLOOR.contains(Player.getPosition()) && Inventory.find(YELLOW_KEY).length < 1) {
                cQuesterV2.status = "Getting Yellow Key";
                Log.info(cQuesterV2.status);
                RSNPC[] targ = NPCs.findNearest(3972);
                if (targ.length > 0 && CombatUtil.clickTarget(targ[0])) {
                    Timer.abc2WaitCondition(() -> Combat.getHPRatio() < General.random(40, 65)
                            || GroundItems.find(YELLOW_KEY).length > 0, 55000, 65000);
                    checkEat();
                    pickupKey(YELLOW_KEY);
                }
            }

            if (WHOLE_THIRD_FLOOR.contains(Player.getPosition()) && Inventory.find(YELLOW_KEY).length > 0) {
                cQuesterV2.status
                        = "Leaving third floor";
                Log.info(cQuesterV2.status
                );

                RSObject[] yellowDoor = Objects.find(20, Filters.Objects.inArea(YELLOW_DOOR_AREA));

                if (Walking.blindWalkTo(new RSTile(2924, 3250, 2)))
                    Waiting.waitUniform(2000, 4000);

                if (yellowDoor.length > 0) {
                    if (Utils.clickObject(yellowDoor[0], "Open")) {
                        Waiting.waitUniform(1500, 3500);
                        Walking.walkPath(PATH_AFTER_YELLOW_DOOR);
                        Utils.modSleep();
                        Timer.abc2WaitCondition(() -> AFTER_THIRD_FLOOR_DOOR.contains(Player.getPosition()) && !Player.isMoving(), 12000, 18000);
                    }
                }
            }

            if (AFTER_THIRD_FLOOR_DOOR.contains(Player.getPosition())) {
                if (Player.getPosition().getPlane() == 2) {
                    RSObject[] firstLadder = Objects.findNearest(20, Filters.Objects.tileEquals(new RSTile(2940, 3240, 2)));
                    if (firstLadder.length > 0 && Utils.clickObject(firstLadder[0], "Climb-down"))
                        Timer.waitCondition(() -> Player.getPosition().getPlane() == 1, 5000, 7000);
                }
            }
            if (SECOND_LADDER_AREA.contains(Player.getPosition())) {
                if (Player.getPosition().getPlane() == 1) {
                    RSObject[] secondLadder = Objects.findNearest(20, Filters.Objects.tileEquals(new RSTile(2937, 3240, 1)));
                    if (secondLadder.length > 0)
                        if (Utils.clickObject(secondLadder[0], "Climb-down"))
                            Timer.waitCondition(() -> Player.getPosition().getPlane() == 0, 5000, 7000);
                }
            }
            if (LAST_LADDER_AREA.contains(Player.getPosition())) {
                if (Player.getPosition().getPlane() == 0) {
                    RSObject[] secondLadder = Objects.findNearest(20, Filters.Objects.tileEquals(new RSTile(2932, 3240, 0)));
                    if (secondLadder.length > 0)
                        if (Utils.clickObject(secondLadder[0], "Climb-down"))
                            Timer.waitCondition(() -> !secondLadder[0].isClickable(), 5000, 7000);
                }

            } else if (ZOMBIE_ROOM.contains(Player.getPosition()) && Inventory.find(BLUE_KEY).length < 1) {
                cQuesterV2.status = "Getting Blue Key";
                RSNPC[] targ = NPCs.findNearest("Zombie");
                if (Combat.isUnderAttack())
                    waitUntilOutOfCombat(BLUE_KEY);

                else if (targ.length > 0 && CombatUtil.clickTarget(targ[0]))
                    waitUntilOutOfCombat(BLUE_KEY);

                checkEat();
                pickupKey(BLUE_KEY);

            } else if (ZOMBIE_ROOM.contains(Player.getPosition()) && Inventory.find(BLUE_KEY).length > 0) {
                RSTile blueDoorTile = new RSTile(2931, 9644, 0);

                if (blueDoorTile.distanceTo(Player.getPosition()) > 2) {
                    Walking.blindWalkTo(blueDoorTile);
                    Timer.abc2WaitCondition(() -> !Player.isMoving(), 20000, 25000);
                    Waiting.waitUniform(1500, 4000);
                }

                if (Utils.clickObject(2599, "Open", false)) {
                    Timer.abc2WaitCondition(() -> MELZAR_AREA.contains(Player.getPosition()), 6000, 8000);
                    Waiting.waitUniform(1500, 4000);
                }

            } else if (MELZAR_AREA.contains(Player.getPosition()) && Inventory.find(MAGENTA_KEY).length < 1) {
                cQuesterV2.status
                        = "Getting Magenta Key";
                RSNPC[] targ = NPCs.findNearest(823);
                if (targ.length > 0 && CombatUtil.clickTarget(targ[0]))
                    waitUntilOutOfCombat(MAGENTA_KEY);

                if (Combat.isUnderAttack())
                    waitUntilOutOfCombat(MAGENTA_KEY);

                checkEat();
                pickupKey(MAGENTA_KEY);


            } else if (MELZAR_AREA.contains(Player.getPosition()) && Inventory.find(MAGENTA_KEY).length > 0) {
                cQuesterV2.status
                        = "Leaving Melzar room";
                ;

                if (Utils.clickObject(2600, "Open", false)) {
                    Timer.waitCondition(() -> LESSER_DEMON_AREA_1.contains(Player.getPosition()), 10000, 15000);
                }

            } else if ((LESSER_DEMON_AREA_1.contains(Player.getPosition()) || LESSER_DEMON_AREA_2.contains(Player.getPosition())) && Inventory.find(GREEN_KEY).length < 1) {
                cQuesterV2.status
                        = "Getting Green Key";

                RSNPC[] targ = NPCs.findNearest(3982);
                if (targ.length > 0 && CombatUtil.clickTarget(targ[0]))
                    waitUntilOutOfCombat(GREEN_KEY);

                if (Combat.isUnderAttack())
                    waitUntilOutOfCombat(GREEN_KEY);

                checkEat();
                pickupKey(GREEN_KEY);

            } else if ((LESSER_DEMON_AREA_1.contains(Player.getPosition()) || LESSER_DEMON_AREA_2.contains(Player.getPosition())) && Inventory.find(GREEN_KEY).length > 0) {
                cQuesterV2.status
                        = "Leaving Lesser demon room";
                if (Utils.clickObject(2601, "Open", false)) {
                    Timer.abc2WaitCondition(() -> !LESSER_DEMON_AREA_1.contains(Player.getPosition()), 12000, 15600);
                }

            } else if (CHEST_AREA.contains(Player.getPosition()) && Inventory.find(MAP_PIECE_1).length < 1) {
                if (Utils.clickObject(2603, "Open", false)) {
                    Timer.abc2WaitCondition(() -> Objects.findNearest(20, 2604).length > 0, 8000, 12000);

                    if (Utils.clickObject(2604, "Search", false)) {
                        NPCInteraction.waitForConversationWindow();
                        NPCInteraction.handleConversation();
                    }
                }

            } else if (CHEST_AREA.contains(Player.getPosition()) && Inventory.find(MAP_PIECE_1).length > 0) {
                if (Utils.clickObject(17385, "Climb-up", false))
                    Timer.abc2WaitCondition(() -> !CHEST_AREA.contains(Player.getPosition()), 12000, 15000);
            }
        }

    }

    public void checkEat() {
        if (Combat.getHPRatio() < General.random(45, 65)) {
            if (Inventory.find(
                    ItemID.LOBSTER).length > 0) {
                AccurateMouse.click(Inventory.find(
                        ItemID.LOBSTER)[0], "Eat");
            }
        }
    }

    public void pickupKey(int key) {
        RSGroundItem[] groundKey = GroundItems.find(key);
        if (groundKey.length > 0) {
            if (!GroundItems.find(key)[0].isClickable())
                GroundItems.find(key)[0].adjustCameraTo();

            if (AccurateMouse.click(groundKey[0], "Take"))
                Timer.abc2WaitCondition(() -> Inventory.find(key).length > 0, 12000, 15000);
        }
    }


    public void getItems2(boolean check) {
        if (!check || (Inventory.find(MAP_PIECE_1).length > 0 && Inventory.find(MAP_PIECE_2).length < 1)) {
            cQuesterV2.status
                    = "Getting Items for map pieces 2/3";
            Log.info(cQuesterV2.status
            );
            BankManager.open(true);
            BankManager.depositAllExcept(true, MAP_PIECE_1);
            BankManager.checkEquippedGlory();
            BankManager.checkCombatBracelet();
            BankManager.withdraw(1, true, UNFIRED_BOWL);
            BankManager.withdraw(3, true,
                    ItemID.FALADOR_TELEPORT);
            BankManager.withdraw(1, true, WIZARDS_MIND_BOMB);
            BankManager.withdraw(1, true, LOBSTER_POT);
            BankManager.withdraw(15000, true, COINS);
            BankManager.withdraw(1, true, HAMMER);
            BankManager.withdraw(1, true, SILK);
            BankManager.withdraw(90, true, STEEL_NAILS);
            BankManager.withdraw(3, true, PLANK);
            BankManager.withdraw(1, true, MAP_PIECE_1);
            BankManager.withdraw(1, true, ItemID.STAMINA_POTION[0]);
            BankManager.close(true);

        }
    }

    public void step5() {
        if (Inventory.find(MAP_PIECE_1).length > 0 && Inventory.find(MAP_PIECE_2).length < 1) {
            if (!BankManager.checkInventoryItems(WIZARDS_MIND_BOMB, UNFIRED_BOWL,
                    ItemID.FALADOR_TELEPORT, LOBSTER_POT, SILK, STEEL_NAILS, PLANK)) {
                buyItems();
                getItems2(true);
            }
            if (!askedOracleAboutMap.check()) {
                cQuesterV2.status = "Going to Oracle";
                Log.info(cQuesterV2.status);
                PathingUtil.walkToArea(ORACLE_AREA);
                if (NpcChat.talkToNPC("Oracle")) {
                    NpcChat.handle(true, "I seek a piece of the map to the island of Crandor.");
                    safteyTimer.reset();
                }
            }
        }
    }

    VarbitRequirement usedSilkOnDoor = new VarbitRequirement(silkOnDoorVarbit, 1);
    VarbitRequirement usedLobsterPotOnDoor = new VarbitRequirement(lobsterPotOnDoorVarbit, 1);
    VarbitRequirement usedBowlOnDoor = new VarbitRequirement(unfireBowlOnDoorVarbit, 1);
    VarbitRequirement usedWizardMindBombOnDoor = new VarbitRequirement(wizardMindBombOnDoorVarbit, 1);

    public void step6() {
        if (Inventory.find(MAP_PIECE_2).length < 1) {
            if (Inventory.find(MAP_PIECE_1).length > 0
                    && !IN_VAULT.contains(Player.getPosition()) && askedOracleAboutMap.check()) {
                cQuesterV2.status = "Going to Door";
                Log.info(cQuesterV2.status);
                PathingUtil.walkToArea(IN_FRONT_OF_DOOR_MINE);
                cQuesterV2.status = "Using items on Door";
                Log.info(cQuesterV2.status);
                if (!usedBowlOnDoor.check() &&
                        Utils.useItemOnObject(UNFIRED_BOWL, MAGIC_DOOR))
                    Waiting.waitUntil(3000, 800, () -> usedBowlOnDoor.check());

                if (!usedWizardMindBombOnDoor.check() &&
                        Utils.useItemOnObject(WIZARDS_MIND_BOMB, MAGIC_DOOR))
                    Waiting.waitUntil(3000, 800, () -> usedWizardMindBombOnDoor.check());


                if (!usedLobsterPotOnDoor.check() &&
                        Utils.useItemOnObject(LOBSTER_POT, MAGIC_DOOR))
                    Waiting.waitUntil(3000, 800, () -> usedLobsterPotOnDoor.check());


                if (!usedSilkOnDoor.check() &&
                        Utils.useItemOnObject(SILK, MAGIC_DOOR)) {
                    Waiting.waitUntil(3000, 800, () -> usedSilkOnDoor.check());
                }
                if (usedBowlOnDoor.check() && usedWizardMindBombOnDoor.check() && usedLobsterPotOnDoor.check() &&
                        usedSilkOnDoor.check()) {
                    if (Timer.abc2WaitCondition(() -> IN_VAULT.contains(Player.getPosition())
                            || Game.isInInstance(), 12000, 15000)) {
                        Waiting.waitNormal(1500, 200);
                    }
                }
            }
            if (!IN_VAULT.contains(Player.getPosition()) && !Game.isInInstance()) {
                //clicks door to enter
                Optional<GameObject> door = QueryUtils.getObject(25115);
                if (door.map(d -> d.click()).orElse(false) &&
                        Timer.abc2WaitCondition(() -> IN_VAULT.contains(Player.getPosition())
                                || Game.isInInstance(), 12000, 15000)) {
                    Waiting.waitNormal(1500, 150);
                }
            }
            if (IN_VAULT.contains(Player.getPosition()) || Objects.findNearest(20, 2587).length > 0) {
                cQuesterV2.status = "Searching chest";
                if (Utils.clickObject(2587, "Open", false)) {
                    NpcChat.handle(true);
                }
                if (Utils.clickObject(2588, "Search", false)) {
                    NpcChat.handle(true);
                }
            } else {

            }
        }
    }

    public void step7() {
        if (Inventory.find(MAP_PIECE_2).length > 0 &&
                Inventory.find(MAP_PIECE_3).length < 1) {
            cQuesterV2.status = "Going to get last piece";
            Log.info(cQuesterV2.status);
            PathingUtil.walkToArea(IN_FRONT_OF_CELL);
            if (Utils.clickNPC("Wormbrain", "Talk-to")) {
                NpcChat.handle(true, "I believe you've got a piece of map that I need.",
                        "I suppose I could pay you for the map piece...",
                        "Alright then, 10,000 it is.");
            }
        }
    }

    public void buyBoat() {
        if (Inventory.find(MAP_PIECE_3).length > 0) {
            cQuesterV2.status = "Going to buy boat";
            Log.info(cQuesterV2.status);
            PathingUtil.walkToArea(DOCK_AREA);
            if (NpcChat.talkToNPC("Klarense")) {
                NpcChat.handle(true, "I'd like to buy her.",
                        "Yep, sounds good.");
            }
        }
    }

    public void step9() {
        cQuesterV2.status
                = "Repairing boat";
        Log.info(cQuesterV2.status
        );
        if (!SHIP_DECK.contains(Player.getPosition()) && !BOTTOM_SHIP.contains(Player.getPosition())) {
            PathingUtil.walkToArea(DOCK_AREA);
            Utils.clickObject(2593, "Cross", false);
            Timer.abc2WaitCondition(() -> SHIP_DECK.contains(Player.getPosition()), 5000, 8000);

        }
        if (SHIP_DECK.contains(Player.getPosition())) {
            if (Utils.clickObject(2590, "Climb-down", false)) {
                Timer.abc2WaitCondition(() -> !SHIP_DECK.contains(Player.getPosition()), 5000, 8000);
            }
        }
        if (BOTTOM_SHIP.contains(Player.getPosition()) &&
                Utils.clickObject(25036, "Repair", false)) {
            NpcChat.handle(true);
            Utils.animationIdle(5000);
        }
    }

    public void getItems3() {
        cQuesterV2.status = "Getting Items for Fight";
        Log.info(cQuesterV2.status);
        BankManager.open(true);
        BankManager.depositAll(true);
        BankManager.checkEquippedGlory();
        BankManager.checkCombatBracelet();
        BankManager.withdraw(2, true, ItemID.VARROCK_TELEPORT);
        BankManager.withdraw(20, true, ItemID.LOBSTER);
        BankManager.withdraw(1, true, EXTENDED_ANTIFIRE[0]);
        BankManager.withdraw(1, true, MAP_PIECE_1);
        BankManager.withdraw(1, true, MAP_PIECE_2);
        BankManager.withdraw(1, true, MAP_PIECE_3);
        BankManager.withdraw(1, true, WHOLE_MAP);
        if (Equipment.find(ANTI_DRAGON_SHIELD).length < 1) {
            BankManager.withdraw(1, true, ANTI_DRAGON_SHIELD);
            Utils.equipItem(ANTI_DRAGON_SHIELD);
        }
        BankManager.withdraw(1, true, ItemID.STAMINA_POTION[0]);
        BankManager.withdraw(2, true, ItemID.PRAYER_POTION[0]);
        BankManager.close(true);
        Utils.useItemOnItem(MAP_PIECE_1, MAP_PIECE_2);
        NPCInteraction.handleConversation();
    }

    public void step10() {
        cQuesterV2.status = "Talking to Ned";
        Log.info(cQuesterV2.status);
        PathingUtil.walkToArea(NEDS_HOUSE);
        if (NpcChat.talkToNPC("Ned")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("You're a sailor? Could you take me to Crandor?");
            NPCInteraction.handleConversation();
        }
    }

    public void step11() {
        cQuesterV2.status = "Going to boat";
        Log.info(cQuesterV2.status);
        if (!SHIP_DECK.contains(Player.getPosition()) && !BOTTOM_SHIP.contains(Player.getPosition())) {
            PathingUtil.walkToArea(DOCK_AREA);
            if (Utils.clickObject(2593, "Cross", false)) {
                Timer.abc2WaitCondition(() -> SHIP_DECK.contains(Player.getPosition()), 5000, 8000);
            }
        }
        if (SHIP_DECK.contains(Player.getPosition())) {
            if (NpcChat.talkToNPC("Captain Ned")) {
                NpcChat.handle(true, "Yes, let's go!");
                Waiting.waitUntil(7000, 500, Utils::inCutScene);
                Utils.cutScene();
                Utils.modSleep();
            }
        }
    }

    RSArea DUNGEON_ENTRANCE = new RSArea(new RSTile(2836, 3253, 0), new RSTile(2832, 3258, 0));
    RSArea OUTSIDE_ELVARG = new RSArea(new RSTile(2845, 9634, 0), new RSTile(2843, 9638, 0));
    RSArea FIGHT_AREA = new RSArea(new RSTile(2846, 9645, 0), new RSTile(2864, 9626, 0));
    int ELVARG = 816;

    public void checkPrayer() {
        if (Prayer.getPrayerPoints() < General.random(5, 15)) {
            Utils.drinkPotion(ItemID.PRAYER_POTION);
        }
    }

    public void step12() {
        cQuesterV2.status = "Going to Elvarg";
        Log.info(cQuesterV2.status);

        if (!FIGHT_AREA.contains(Player.getPosition()))
            PathingUtil.walkToArea(OUTSIDE_ELVARG);

        if (OUTSIDE_ELVARG.contains(Player.getPosition()) && !FIGHT_AREA.contains(Player.getPosition())) {
            Prayer.enable(Prayer.PRAYERS.PROTECT_FROM_MELEE);
            Utils.drinkPotion(EXTENDED_ANTIFIRE);
            if (Utils.clickObject(25161, "Climb-over", false))
                Timer.waitCondition(() -> FIGHT_AREA.contains(Player.getPosition()), 8000, 12000);
        }
        RSNPC[] elvarg = NPCs.findNearest(ELVARG);
        if (FIGHT_AREA.contains(Player.getPosition()) && elvarg.length > 0) {
            General.sleep(500, 1200);

            if (!Combat.isUnderAttack())
                CombatUtil.clickTarget(elvarg[0]);

            if ((MAGIC || RANGED) && !SAFE_SPOT.equals(Player.getPosition())) {
                cQuesterV2.status
                        = "Going to safe tile";
                if (PathingUtil.localNavigation(SAFE_SPOT))
                    PathingUtil.movementIdle();
            }

            checkEat();
            checkPrayer();

        } else if (elvarg.length < 1 && FIGHT_AREA.contains(Player.getPosition())) {
            Timer.waitCondition(() -> Inventory.find(11279).length > 0, 15000, 22000);
            Utils.modSleep();
        }
    }

    public void step13() {
        cQuesterV2.status
                = "Going to Oziach";
        Timer.waitCondition(() -> Inventory.find(11279).length > 0, 15000, 22000);
        Prayer.disable(Prayer.PRAYERS.PROTECT_FROM_MAGIC);
        Prayer.disable(Prayer.PRAYERS.PROTECT_FROM_MELEE);
        Log.info(cQuesterV2.status);
        if (PathingUtil.walkToArea(OZIACH_HOUSE)) {
            RSNPC[] oziach = NPCs.findNearest("Oziach");
            if (oziach.length > 0) {
                PathingUtil.localNavigation(oziach[0].getPosition());
                PathingUtil.movementIdle();
            }
        }
        if (NpcChat.talkToNPC("Oziach")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }
    }

    private boolean checkBankCache(int itemId) {
        if (!BankCache.isInitialized()) {
            Log.warn("Bank Cache is not intialized, going to bank");
            BankManager.open(true);
            BankCache.update();
        }
        return BankCache.getStack(itemId) > 0;
    }

    private Timer safteyTimer = new Timer(General.random(480000, 600000)); // 8- 10min

    int GAME_SETTING = 176;

    @Override
    public void execute() {
        // checkLevel();

        General.println("RSVarBit.get(3749).getValue() = " + RSVarBit.get(3749).getValue());

        General.println("[Debug]: Game Setting 176 is " + Game.getSetting(176));
        Log.info("Asked about Lozar? " + askedAboutLozar.check());
        Log.info("Asked about Thalzar? " + askedAboutThalzar.check());
        Log.info("Asked about Melzar? " + askedAboutMelzar.check());
        Log.info("Asked about Ship? " + askedAboutShip.check());

        if (Game.getSetting(176) == 0) {
            General.sleep(100);
            buyItems();
            getItems1();
            startQuest();
        } else if (Game.getSetting(176) == 1) {
            goToOziach();
        } else if (Game.getSetting(176) == 2) {
            step3();
            talkToGuildMasterAboutPiece();
            navigateMaze();
            getItems2(true);
            talkToGuildMasterAboutPiece();
            step5();
            step6();

            step7();
            buyBoat();
        } else if (Game.getSetting(176) == 3) {
            step9();
        } else if (Game.getSetting(176) == 6) {
            getItems3();
            step10();

        } else if (Game.getSetting(176) == 7) {
            step11();
        } else if (Game.getSetting(176) == 8) {
            step12();
        } else if (Game.getSetting(176) == 9) {
            step13();
        } else if (Game.getSetting(176) == 10) {
            Utils.closeQuestCompletionWindow();
            cQuesterV2.taskList.remove(this);
        }
        Waiting.waitNormal(100, 20);
    }


    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return Game.getSetting(176) <= 10 &&
                java.util.Objects.equals(cQuesterV2.taskList.get(0), DragonSlayer.get());
    }


    @Override
    public String questName() {
        return "Dragon Slayer (" + Game.getSetting(176) + ")";
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
        return Quest.DRAGON_SLAYER_I.getState().equals(Quest.State.COMPLETE);
    }
}
