package scripts.QuestPackages.DragonSlayer;

import dax.shared.helpers.questing.Quest;
import dax.walker.utils.AccurateMouse;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.*;
import org.tribot.script.sdk.Waiting;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.WitchsPotion.WitchsPotion;
import scripts.QuestPackages.XMarksTheSpot.XMarksTheSpot;
import scripts.QuestSteps.BuyItemsStep;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.InventoryRequirement;
import scripts.Requirements.ItemReq;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DragonSlayer implements QuestTask {

    private static DragonSlayer quest;

    public static DragonSlayer get() {
        return quest == null ? quest = new DragonSlayer() : quest;
    }


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
                    new GEItem(ItemId.ADAMANT_FULL_HELM, 1, 300),
                    new GEItem(ItemId.ADAMANT_PLATEBODY, 1, 100),
                    new GEItem(ItemId.ADAMANT_PLATELEGS, 1, 100),
                    new GEItem(ADAMANT_BOOTS, 1, 300),
                    new GEItem(ItemId.RUNE_SCIMITAR, 1, 30),
                    new GEItem(ItemId.EXTENDED_SUPER_ANTIFIRE_POTION[0], 1, 30),
                    new GEItem(ItemId.UNFIRED_BOWL, 1, 300),
                    new GEItem(WIZARDS_MIND_BOMB, 1, 300),
                    new GEItem(LOBSTER_POT, 1, 300),
                    new GEItem(SILK, 1, 300),
                    new GEItem(LAW_RUNE, 5, 30),
                    new GEItem(ANTI_DRAGON_SHIELD, 1, 300),
                    new GEItem(ItemId.HAMMER, 1, 300),
                    new GEItem(STEEL_NAILS, 100, 300),

                    new GEItem(ItemId.PLANK, 3, 50),
                    new GEItem(ItemId.LOBSTER, 30, 50),
                    new GEItem(ItemId.VARROCK_TELEPORT, 5, 50),
                    new GEItem(ItemId.FALADOR_TELEPORT, 5, 50),
                    new GEItem(ItemId.GAMES_NECKLACE[0], 2, 30),
                    new GEItem(ItemId.AMULET_OF_GLORY[2], 2, 30),
                    new GEItem(ItemId.COMBAT_BRACELET[0], 1, 30),
                    new GEItem(ItemId.PRAYER_POTION[0], 3, 30),
                    new GEItem(ItemId.SUPER_COMBAT_POTION[0], 1, 30),
                    new GEItem(ItemId.STAMINA_POTION[0], 5, 15),
                    new GEItem(ItemId.RING_OF_WEALTH[0], 1, 25)
            )
    );

    InventoryRequirement initialItemReqs = new InventoryRequirement(new ArrayList<>(
            Arrays.asList(
                    new ItemReq(ItemId.STAMINA_POTION[0], 1, 0),
                    new ItemReq(ItemId.RING_OF_WEALTH[0], 1, 0, true)
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
        General.println("[Debug]: " + cQuesterV2.status
        );
        BankManager.open(true);
        BankManager.depositAll(true);
        BankManager.depositEquipment();
        General.sleep(General.random(400, 800));
        BankManager.checkEquippedGlory();
        BankManager.checkCombatBracelet();
        BankManager.withdraw(5, true, ItemId.VARROCK_TELEPORT);
        BankManager.withdraw(5, true, ItemId.FALADOR_TELEPORT);
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
                    ItemId.STAFF_OF_AIR);
            BankManager.withdraw(300, true,
                    ItemId.CHAOS_RUNE);
            BankManager.withdraw(1200, true,
                    ItemId.FIRE_RUNE);
            Utils.equipItem(
                    ItemId.STAFF_OF_AIR);
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
                ItemId.LOBSTER);
        BankManager.withdraw(1, true,
                ItemId.SKILLS_NECKLACE[0]);
        BankManager.withdraw(2, true,
                ItemId.STAMINA_POTION[0]);
        BankManager.withdraw(1, true,
                ItemId.RING_OF_DUELING[0]);
        BankManager.close(true);
        Utils.equipItem(
                ItemId.RING_OF_DUELING[0]);
        Utils.equipItem(
                ItemId.COMBAT_BRACELET[0]);
        if (Prayer.getPrayerPoints() < 30) {
            //Utils.clanWarsReset();
        }
    }

    public void startQuest() {
        cQuesterV2.status
                = "Going to start";
        General.println("[Debug]: " + cQuesterV2.status
        );
        PathingUtil.walkToArea(CHAMPIONS_GUILD_BOTTOM);
        if (CHAMPIONS_GUILD_BOTTOM.contains(Player.getPosition())) {
            if (NpcChat.talkToNPC("Guildmaster")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Can I have a quest?");
                NPCInteraction.handleConversation("Yes.");
                NPCInteraction.handleConversation();
                Utils.equipItem(ANTI_DRAGON_SHIELD);
            }
        }
    }

    public void step2() {
        cQuesterV2.status
                = "Going to Oziach";
        General.println("[Debug]: " + cQuesterV2.status
        );
        PathingUtil.walkToArea(OZIACH_HOUSE);
        if (NpcChat.talkToNPC("Oziach")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Can you sell me a rune platebody?");
            NPCInteraction.handleConversation("The Guildmaster of the Champions' Guild told me.");
            NPCInteraction.handleConversation("I thought you were going to give me a quest.");
            NPCInteraction.handleConversation("A dragon, that sounds like fun.");
            NPCInteraction.handleConversation();
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
        if (Utils.getVarBitValue(MELZAR_VARBIT) == 1 ||
                Utils.getVarBitValue(THALZAR_VARBIT) == 1 ||
                Utils.getVarBitValue(LOZAR_VARBIT) == 1 ||
                Utils.getVarBitValue(RIGHT_SHIP_VARBIT) == 1) {

            PathingUtil.walkToArea(CHAMPIONS_GUILD_BOTTOM);
            if (CHAMPIONS_GUILD_BOTTOM.contains(Player.getPosition())) {
                NpcChat.talkToNPC("Guildmaster");
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation(guildMasterArray);
            }
        }
    }

    public void step3() {
        if (Inventory.find(MAZE_KEY).length < 1 && Inventory.find(MAP_PIECE_1).length < 1) {
            // could add a bank check here for map pieces
            cQuesterV2.status
                    = "Going to Champions Guild";
            General.println("[Debug]: " + cQuesterV2.status
            );
            PathingUtil.walkToArea(CHAMPIONS_GUILD_BOTTOM);
            if (CHAMPIONS_GUILD_BOTTOM.contains(Player.getPosition())) {
                if (NpcChat.talkToNPC("Guildmaster")) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation(guildMasterArray);
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


    public void navigateMaze() {
        if (Inventory.find(MAP_PIECE_1).length < 1 && Inventory.find(MAZE_KEY).length > 0) {
            Utils.equipItem(ANTI_DRAGON_SHIELD);
            if (!WHOLE_FIRST_FLOOR.contains(Player.getPosition()) && !WHOLE_BASEMENT.contains(Player.getPosition())
                    && !WHOLE_SECOND_FLOOR.contains(Player.getPosition()) && !WHOLE_THIRD_FLOOR.contains(Player.getPosition())) {
                cQuesterV2.status
                        = "Going to Melzar's maze";
                General.println("[Debug]: " + cQuesterV2.status
                );
                PathingUtil.walkToArea(INSIDE_MAZE_DOOR);
            }

            if (MAIN_ROOM_FLOOR_1.contains(Player.getPosition()) && Inventory.find(RED_KEY).length < 1) {
                if (MAGIC)
                    Autocast.enableAutocast(Autocast.FIRE_BOLT);

                cQuesterV2.status
                        = "Getting Red Key";
                General.println("[Debug]: " + cQuesterV2.status
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

            if (BOTTOM_AFTER_DOOR_AREA.contains(Player.getPosition())) {
                if (Utils.clickObject(16683, "Climb-up", false)) {
                    Timer.abc2WaitCondition(() -> WHOLE_SECOND_FLOOR.contains(Player.getPosition()), 12000, 16000);
                }
            }

            if (NW_ROOM_FLOOR_1.contains(Player.getPosition())) {
                cQuesterV2.status
                        = "Going upstairs";
                General.println("[Debug]: " + cQuesterV2.status
                );
                if (Utils.clickObject(16683, "Climb-up", false))
                    Timer.abc2WaitCondition(() -> WHOLE_SECOND_FLOOR.contains(Player.getPosition()), 12000, 16000);

            } else if (GHOST_ROOM.contains(Player.getPosition()) && Inventory.find(ORANGE_KEY).length < 1) {
                cQuesterV2.status
                        = "Getting Orange Key";
                General.println("[Debug]: " + cQuesterV2.status
                );
                RSNPC[] targ = NPCs.findNearest(3975);
                if (targ.length > 0) {

                    if (!Combat.isUnderAttack())
                        CombatUtil.clickTarget(targ[0]);

                    Timer.abc2WaitCondition(() -> Combat.getHPRatio() < General.random(40, 65) ||
                            GroundItems.find(ORANGE_KEY).length > 0, 35000, 45000);
                    checkEat();
                    pickupKey(ORANGE_KEY);
                }

            } else if (GHOST_ROOM.contains(Player.getPosition()) && Inventory.find(ORANGE_KEY).length > 0) {
                cQuesterV2.status
                        = "Going to third floor";
                General.println("[Debug]: " + cQuesterV2.status
                );
                if (PathingUtil.localNavigation(new RSTile(2930, 3253, 1)))
                    PathingUtil.movementIdle();

                RSObject[] orangeDoor = Objects.find(10, Filters.Objects.inArea(ORANGE_DOOR_AREA));

                if (orangeDoor.length > 0) {
                    if (Utils.clickObject(orangeDoor[0].getID(), "Open", false)) {
                        Timer.abc2WaitCondition(() -> !GHOST_ROOM.contains(Player.getPosition()), 12000, 16000);

                        if (Utils.clickObject(16683, "Climb-up", false)) {
                            Timer.abc2WaitCondition(() -> !WHOLE_SECOND_FLOOR.contains(Player.getPosition()), 12000, 15000);
                        }
                    }
                }
            }

            if (WHOLE_THIRD_FLOOR.contains(Player.getPosition()) && Inventory.find(YELLOW_KEY).length < 1) {
                cQuesterV2.status
                        = "Getting Yellow Key";
                General.println("[Debug]: " + cQuesterV2.status
                );
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
                General.println("[Debug]: " + cQuesterV2.status
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
                cQuesterV2.status
                        = "Getting Blue Key";
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
                    ItemId.LOBSTER).length > 0) {
                AccurateMouse.click(Inventory.find(
                        ItemId.LOBSTER)[0], "Eat");
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


    public void getItems2() {
        if (Inventory.find(MAP_PIECE_1).length > 0 && Inventory.find(MAP_PIECE_2).length < 1) {
            cQuesterV2.status
                    = "Getting Items for map pieces 2/3";
            General.println("[Debug]: " + cQuesterV2.status
            );
            BankManager.open(true);
            BankManager.depositAllExcept(true, MAP_PIECE_1);
            BankManager.checkEquippedGlory();
            BankManager.checkCombatBracelet();
            BankManager.withdraw(1, true, UNFIRED_BOWL);
            BankManager.withdraw(3, true,
                    ItemId.FALADOR_TELEPORT);
            BankManager.withdraw(1, true, WIZARDS_MIND_BOMB);
            BankManager.withdraw(1, true, LOBSTER_POT);
            BankManager.withdraw(15000, true, COINS);
            BankManager.withdraw(1, true, HAMMER);
            BankManager.withdraw(1, true, SILK);
            BankManager.withdraw(90, true, STEEL_NAILS);
            BankManager.withdraw(3, true, PLANK);
            BankManager.withdraw(1, true, MAP_PIECE_1);
            BankManager.withdraw(1, true, ItemId.STAMINA_POTION[0]);
            BankManager.close(true);

        }
    }

    boolean oracle = false;

    public void step5() {
        if (Inventory.find(MAP_PIECE_1).length > 0 && Inventory.find(MAP_PIECE_2).length < 1) {
            if (!BankManager.checkInventoryItems(WIZARDS_MIND_BOMB, UNFIRED_BOWL,
                    ItemId.FALADOR_TELEPORT, LOBSTER_POT, SILK, STEEL_NAILS, PLANK)) {
                buyItems();
                getItems2();
            }
            cQuesterV2.status
                    = "Going to Oracle";
            General.println("[Debug]: " + cQuesterV2.status
            );
            PathingUtil.walkToArea(ORACLE_AREA);
            if (NpcChat.talkToNPC("Oracle")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("I seek a piece of the map to the island of Crandor.");
                oracle = true;
                NPCInteraction.handleConversation();
                safteyTimer.reset();
            }
        }

    }

    public void step6() {
        if (Inventory.find(MAP_PIECE_2).length < 1) {
            if (Inventory.find(MAP_PIECE_1).length > 0 && !IN_VAULT.contains(Player.getPosition()) && oracle) {
                cQuesterV2.status
                        = "Going to Door";
                General.println("[Debug]: " + cQuesterV2.status
                );
                PathingUtil.walkToArea(IN_FRONT_OF_DOOR_MINE);
                cQuesterV2.status
                        = "Using items on Door";
                General.println("[Debug]: " + cQuesterV2.status
                );
                if (Utils.useItemOnObject(UNFIRED_BOWL, MAGIC_DOOR))
                    Waiting.waitUniform(500, 2000);

                if (Utils.useItemOnObject(WIZARDS_MIND_BOMB, MAGIC_DOOR))
                    Waiting.waitUniform(500, 2000);

                if (Utils.useItemOnObject(LOBSTER_POT, MAGIC_DOOR))
                    Waiting.waitUniform(500, 2000);

                if (Utils.useItemOnObject(SILK, MAGIC_DOOR))
                    Timer.abc2WaitCondition(() -> IN_VAULT.contains(Player.getPosition()), 12000, 15000);
            }

            if (IN_VAULT.contains(Player.getPosition()) || Objects.findNearest(20, 2587).length > 0) {
                if (Utils.clickObject(2587, "Open", false)) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                }
                if (Utils.clickObject(2588, "Search", false)) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();

                }
            }
        }
    }

    public void step7() {
        if (Inventory.find(MAP_PIECE_2).length > 0 && Inventory.find(MAP_PIECE_3).length < 1) {
            cQuesterV2.status
                    = "Going to get last piece";
            General.println("[Debug]: " + cQuesterV2.status
            );
            PathingUtil.walkToArea(IN_FRONT_OF_CELL);
            if (Utils.clickNPC("Wormbrain", "Talk-to")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("I believe you've got a piece of map that I need.");
                NPCInteraction.handleConversation("I suppose I could pay you for the map piece...");
                NPCInteraction.handleConversation("Alright then, 10,000 it is.");
                NPCInteraction.handleConversation();
            }
        }
    }

    public void step8() {
        if (Inventory.find(MAP_PIECE_3).length > 0) {
            cQuesterV2.status
                    = "Going to buy boat";
            General.println("[Debug]: " + cQuesterV2.status
            );
            PathingUtil.walkToArea(DOCK_AREA);
            if (NpcChat.talkToNPC("Klarense")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("I'd like to buy her.");
                NPCInteraction.handleConversation("Yep, sounds good.");
                NPCInteraction.handleConversation();
            }
        }
    }

    public void step9() {
        cQuesterV2.status
                = "Repairing boat";
        General.println("[Debug]: " + cQuesterV2.status
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
        if (BOTTOM_SHIP.contains(Player.getPosition())) {
            Utils.clickObject(25036, "Repair", false);
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            Utils.shortSleep();
        }
    }

    public void getItems3() {
        cQuesterV2.status
                = "Getting Items for Fight";
        General.println("[Debug]: " + cQuesterV2.status
        );
        BankManager.open(true);
        BankManager.depositAll(true);
        BankManager.checkEquippedGlory();
        BankManager.checkCombatBracelet();
        BankManager.withdraw(2, true, ItemId.VARROCK_TELEPORT);
        BankManager.withdraw(20, true, ItemId.LOBSTER);
        BankManager.withdraw(1, true, EXTENDED_ANTIFIRE[0]);
        BankManager.withdraw(1, true, MAP_PIECE_1);
        BankManager.withdraw(1, true, MAP_PIECE_2);
        BankManager.withdraw(1, true, MAP_PIECE_3);
        BankManager.withdraw(1, true, WHOLE_MAP);
        if (Equipment.find(ANTI_DRAGON_SHIELD).length < 1) {
            BankManager.withdraw(1, true, ANTI_DRAGON_SHIELD);
            Utils.equipItem(ANTI_DRAGON_SHIELD);
        }
        BankManager.withdraw(1, true, ItemId.STAMINA_POTION[0]);
        BankManager.withdraw(2, true, ItemId.PRAYER_POTION[0]);
        BankManager.close(true);
        Utils.useItemOnItem(MAP_PIECE_1, MAP_PIECE_2);
        NPCInteraction.handleConversation();
    }

    public void step10() {
        cQuesterV2.status
                = "Talking to Ned";
        General.println("[Debug]: " + cQuesterV2.status
        );
        PathingUtil.walkToArea(NEDS_HOUSE);
        if (NpcChat.talkToNPC("Ned")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("You're a sailor? Could you take me to Crandor?");
            NPCInteraction.handleConversation();
        }
    }

    public void step11() {
        cQuesterV2.status
                = "Going to boat";
        General.println("[Debug]: " + cQuesterV2.status
        );
        if (!SHIP_DECK.contains(Player.getPosition()) && !BOTTOM_SHIP.contains(Player.getPosition())) {
            PathingUtil.walkToArea(DOCK_AREA);
            if (Utils.clickObject(2593, "Cross", false)) {
                Timer.abc2WaitCondition(() -> SHIP_DECK.contains(Player.getPosition()), 5000, 8000);
            }
        }
        if (SHIP_DECK.contains(Player.getPosition())) {
            if (NpcChat.talkToNPC("Captain Ned") &&
                    NPCInteraction.waitForConversationWindow()) {
                NPCInteraction.handleConversation("Yes, let's go!");
                NPCInteraction.handleConversation();
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
            Utils.drinkPotion(ItemId.PRAYER_POTION);
        }
    }

    public void step12() {
        cQuesterV2.status
                = "Going to Elvarg";
        General.println("[Debug]: " + cQuesterV2.status);

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
            General.sleep(50);

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
        Prayer.disable(Prayer.PRAYERS.PROTECT_FROM_MAGIC);
        Prayer.disable(Prayer.PRAYERS.PROTECT_FROM_MELEE);
        General.println("[Debug]: " + cQuesterV2.status
        );
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


    private Timer safteyTimer = new Timer(General.random(480000, 600000)); // 8- 10min

    int GAME_SETTING = 176;

    @Override
    public void execute() {
        // checkLevel();

        General.println("RSVarBit.get(3749).getValue() = " + RSVarBit.get(3749).getValue());
        General.sleep(100);
        General.println("[Debug]: Game Setting 176 is " + Game.getSetting(176));


        if (Game.getSetting(176) == 0) {
            General.sleep(100);
            buyItems();
            getItems1();
            startQuest();
        }
        if (Game.getSetting(176) == 1) {
            step2();
        }
        if (Game.getSetting(176) == 2) {
            step3();
            talkToGuildMasterAboutPiece();
            navigateMaze();
            getItems2();
            talkToGuildMasterAboutPiece();
            step5();
            step6();

            step7();
            step8();
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
        }
        if (Game.getSetting(176) == 10) {
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
        return Game.getSetting(176) <= 10 &&
                java.util.Objects.equals(cQuesterV2.taskList.get(0), DragonSlayer.get());
    }


    @Override
    public String questName() {
        return "Dragon Slayer";
    }

    @Override
    public boolean checkRequirements() {
        return false;
    }
}
