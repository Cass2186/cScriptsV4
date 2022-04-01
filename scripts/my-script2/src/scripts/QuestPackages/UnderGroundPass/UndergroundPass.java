package scripts.QuestPackages.UnderGroundPass;

import dax.walker.utils.AccurateMouse;
import dax.walker.utils.camera.DaxCamera;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.types.*;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Quest;
import org.tribot.script.sdk.types.Area;
import org.tribot.script.sdk.types.WorldTile;
import org.tribot.script.sdk.walking.GlobalWalking;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.WitchsPotion.WitchsPotion;
import scripts.QuestSteps.BuyItemsStep;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.*;
import scripts.Requirements.Util.Conditions;
import scripts.Requirements.Util.LogicType;
import scripts.Tasks.Priority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UndergroundPass implements QuestTask {
    private static UndergroundPass quest;

    public static UndergroundPass get() {
        return quest == null ? quest = new UndergroundPass() : quest;
    }

    int ORB_OF_LIGHT_1_INV = 1484;
    int ORB_OF_LIGHT_2_INV = 1483;
    int ORB_OF_LIGHT_3_INV = 1482;
    int ORB_OF_LIGHT_4_INV = 1481;

    int PIE_DISH = 2313;
    int ROPE = 954;
    int MAGIC_SHORTBOW = 861;
    int IRON_ARROW = 884;
    int YEW_SHORTBOW = 857;
    int RUNE_ARROW = 892;
    int SPADE = 952;
    int PLANK = 960;
    int BUCKET = 1925;
    int TINDERBOX = 590;
    int GREEN_DHIDE_BODY = 1135;
    int GREEN_DHIDE_VAMB = 1065;
    int GREEN_DHIDE_CHAPS = 1099;
    int BLUE_DHIDE_BODY = 2499;
    int BLUE_DHIDE_VAMB = 2487;
    int BLUE_DHIDE_CHAPS = 2493;
    int WILLOW_SHORTBOW = 849;
    int ADAMANT_ARROW = 890;
    int SHARK = 385;
    int[] SUMMER_PIE_ARRAY = {7218, 7220};
    int OILY_CLOTH = 1485;
    int IRON_FIRE_ARROW_UNLIT = 2532;
    int IRON_FIRE_ARROW_LIT = 2533;
    int ORB_OF_LIGHT_1 = 37326;

    int CAT_INV_ID = 1491;
    int IBANS_DOLL = 1492;
    int HISTORY_OF_IBAN = 1494;
    int AVAS_ACCUMULATOR = 10499;
    int KLANKS_GAUNTLETS = 1495;
    int CLOSED_DOOR = 1535;
    int PIZZA = 2293;
    int HALF_SUMMER_PIE = 7220;
    int FULL_SUMMER_PIE = 7218;


    RSArea JUMP_ONE_AREA = new RSArea(new RSTile(2159, 4582, 1), new RSTile(2163, 4582, 1));
    RSArea INSIDE_WITCHS_HOUSE = new RSArea(new RSTile(2157, 4564, 1), new RSTile(2154, 4567, 1));
    RSArea WITCHS_PLATFORM = new RSArea(
            new RSTile[] {
                    new RSTile(2148, 4575, 1),
                    new RSTile(2152, 4575, 1),
                    new RSTile(2158, 4571, 1),
                    new RSTile(2162, 4568, 1),
                    new RSTile(2162, 4564, 1),
                    new RSTile(2158, 4560, 1),
                    new RSTile(2154, 4560, 1),
                    new RSTile(2150, 4562, 1),
                    new RSTile(2147, 4571, 1)
            }
    );
    RSArea START_AREA = new RSArea(new RSTile(2580, 3292, 1), new RSTile(2576, 3294, 1));
    RSArea ENTRANCE_TO_UNDERGOUND_PASS = new RSArea(new RSTile(2433, 3316, 0), new RSTile(2442, 3312, 0));
    RSArea PRE_BRIDGE = new RSArea(new RSTile(2445, 9718, 0), new RSTile(2448, 9713, 0));
    RSArea UNDERGROUND_PASS_INSIDE_ENTRANCE = new RSArea(new RSTile[]{new RSTile(2498, 9711, 0), new RSTile(2477, 9711, 0), new RSTile(2477, 9719, 0), new RSTile(2480, 9722, 0), new RSTile(2499, 9721, 0)});
    RSArea PLANK_AREA = new RSArea(new RSTile[]{new RSTile(2440, 9723, 0), new RSTile(2438, 9727, 0), new RSTile(2437, 9728, 0), new RSTile(2430, 9724, 0), new RSTile(2430, 9720, 0), new RSTile(2439, 9720, 0)});

    RSArea ALL_UGP_AREA_2 = new RSArea(new RSTile(2499, 9663, 0), new RSTile(2365, 9733, 0));

    RSArea PRE_ROCKSLIDE_AREA = new RSArea(new RSTile(2481, 9719, 0), new RSTile(2477, 9721, 0));
    RSArea POST_ROCKSLIDE_1 = new RSArea(new RSTile[]{new RSTile(2468, 9725, 0), new RSTile(2467, 9721, 0), new RSTile(2472, 9720, 0), new RSTile(2477, 9720, 0), new RSTile(2480, 9723, 0), new RSTile(2478, 9726, 0)});
    RSArea POST_ROCKSLIDE_2 = new RSArea(new RSTile[]{new RSTile(2468, 9725, 0), new RSTile(2467, 9721, 0), new RSTile(2457, 9720, 0), new RSTile(2463, 9729, 0)});
    RSArea POST_ROCKSLIDE_3 = new RSArea(new RSTile[]{new RSTile(2458, 9721, 0), new RSTile(2462, 9719, 0), new RSTile(2462, 9712, 0), new RSTile(2445, 9712, 0), new RSTile(2445, 9718, 0)});

    RSArea OUTLOOK_AREA = new RSArea(new RSTile(2444, 9725, 0), new RSTile(2447, 9721, 0));
    // RSArea AFTER_BRIDGE = new RSArea(new RSTile(2443, 9719, 0), new RSTile(2439, 9714, 0));
    RSArea AFTER_BRIDGE = new RSArea(new RSTile(2431, 9718, 0), new RSTile(2444, 9695, 0));
    RSArea OIL_RAG_AREA = new RSArea(new RSTile(2445, 9718, 0), new RSTile(2451, 9714, 0));

    RSTile WEST_TRAP_TILE_1 = new RSTile(2409, 9674, 0);
    RSTile WEST_TRAP_TILE_2 = new RSTile(2405, 9675, 0);
    RSTile WEST_TRAP_TILE_3 = new RSTile(2402, 9675, 0);
    RSTile WEST_TRAP_TILE_4 = new RSTile(2397, 9677, 0);
    RSTile WEST_TRAP_TILE_5 = new RSTile(2394, 9676, 0);

    RSTile WEST_TRAP_TILE_1_RETURN = new RSTile(2407, 9674, 0);
    RSTile WEST_TRAP_TILE_2_RETURN = new RSTile(2403, 9675, 0);
    RSTile WEST_TRAP_TILE_3_RETURN = new RSTile(2400, 9675, 0);
    RSTile WEST_TRAP_TILE_4_RETURN = new RSTile(2395, 9677, 0);
    RSTile WEST_TRAP_TILE_5_RETURN = new RSTile(2392, 9676, 0);

    RSArea AFTER_WEST_TRAP_1 = new RSArea(new RSTile(2407, 9677, 0), new RSTile(2404, 9673, 0));
    RSArea AFTER_WEST_TRAP_2 = new RSArea(new RSTile(2403, 9676, 0), new RSTile(2401, 9673, 0));
    RSArea AFTER_WEST_TRAP_3 = new RSArea(new RSTile(2400, 9673, 0), new RSTile(2396, 9679, 0));
    RSArea AFTER_WEST_TRAP_4 = new RSArea(new RSTile(2393, 9678, 0), new RSTile(2395, 9674, 0));
    RSArea ALL_UGP_AREA_1_AND_3 = new RSArea(new RSTile(2499, 9663, 0), new RSTile(2365, 9733, 0));

    ArrayList<RSTile> potentialTiles = new ArrayList<RSTile>();
    ArrayList<RSTile> confirmedTiles = new ArrayList<RSTile>();
    ArrayList<RSTile> failedTiles = new ArrayList<RSTile>();
    ArrayList<RSTile> alreadyWalkedOnTiles = new ArrayList<RSTile>();

    VarbitRequirement givenWitchCat = new VarbitRequirement(9123, 1);

    int GAME_SETTING = 162;

    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.ROPE, 6, 300),
                    new GEItem(IRON_ARROW, 20, 300),
                    new GEItem(ItemID.PLANK, 1, 300),
                    new GEItem(ItemID.SPADE, 1, 300),
                    new GEItem(ItemID.EMPTY_BUCKET, 1, 300),
                    new GEItem(ItemID.TINDERBOX, 1, 300),
                    new GEItem(SUMMER_PIE_ARRAY[0], 20, 30),
                    new GEItem(ItemID.SHARK, 20, 30),

                    new GEItem(ItemID.WEST_ARDOUGNE_TELEPORT, 5, 30),

                    new GEItem(ItemID.AMULET_OF_GLORY[2], 1, 15),
                    new GEItem(ItemID.RING_OF_DUELING[0], 1, 15),
                    new GEItem(ItemID.STAMINA_POTION[0], 6, 15),
                    new GEItem(ItemID.RING_OF_WEALTH[0], 1, 25)
            )
    );

    InventoryRequirement initialItemReqs = new InventoryRequirement(new ArrayList<>(
            Arrays.asList(
                    new ItemReq(ItemID.STAMINA_POTION[0], 1, 0),
                    new ItemReq(ItemID.RING_OF_WEALTH[0], 1, 0, true)
            )
    ));


    public void buyItems() {
        cQuesterV2.status = "Buying Items";
        General.println("[Debug]: " + cQuesterV2.status);

        if (Skills.getCurrentLevel(Skills.SKILLS.RANGED) >= 50) {
            itemsToBuy.add(new GEItem(BLUE_DHIDE_BODY, 1, 25));
            itemsToBuy.add(new GEItem(BLUE_DHIDE_VAMB, 1, 25));
            itemsToBuy.add(new GEItem(BLUE_DHIDE_CHAPS, 1, 25));
            itemsToBuy.add(new GEItem(MAGIC_SHORTBOW, 1, 65));
            itemsToBuy.add(new GEItem(RUNE_ARROW, 500, 25));

        } else if (Skills.getCurrentLevel(Skills.SKILLS.RANGED) >= 40) {
            itemsToBuy.add(new GEItem(GREEN_DHIDE_BODY, 1, 25));
            itemsToBuy.add(new GEItem(GREEN_DHIDE_VAMB, 1, 25));
            itemsToBuy.add(new GEItem(GREEN_DHIDE_CHAPS, 1, 25));
            itemsToBuy.add(new GEItem(YEW_SHORTBOW, 1, 65));
            itemsToBuy.add(new GEItem(RUNE_ARROW, 500, 25));
        }
        BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);

        buyStep.buyItems();
    }

    public void getItems() {
        cQuesterV2.status = "Getting Items";
        General.println("[Debug]: Getting Items");
        BankManager.open(true);
        BankManager.checkEquippedGlory();
        BankManager.depositAll(true);
        BankManager.withdraw(2, true, ROPE);
        BankManager.withdraw(10, true, IRON_ARROW);
        BankManager.withdraw(1, true, SPADE);
        BankManager.withdraw(1, true, PLANK);
        BankManager.withdraw(1, true, BUCKET);
        BankManager.withdraw(1, true, TINDERBOX);
        BankManager.withdraw(2000, true, 995);
        BankManager.withdraw(5, true, ItemID.WEST_ARDOUGNE_TELEPORT);

        if (Skills.getCurrentLevel(Skills.SKILLS.RANGED) >= 50) {
            BankManager.withdraw(1, true, BLUE_DHIDE_BODY);
            BankManager.withdraw(1, true, BLUE_DHIDE_CHAPS);
            BankManager.withdraw(1, true, BLUE_DHIDE_VAMB);
            BankManager.withdraw(1, true, MAGIC_SHORTBOW);
            BankManager.withdraw(500, true, RUNE_ARROW);
            BankManager.withdraw(1, true, AVAS_ACCUMULATOR);
            BankManager.close(true);
            Utils.equipItem(BLUE_DHIDE_BODY);
            Utils.equipItem(BLUE_DHIDE_CHAPS);
            Utils.equipItem(BLUE_DHIDE_VAMB);
            Utils.equipItem(MAGIC_SHORTBOW);
            Utils.equipItem(RUNE_ARROW);
            Utils.equipItem(AVAS_ACCUMULATOR);

        } else if (Skills.getCurrentLevel(Skills.SKILLS.RANGED) >= 40) {
            BankManager.withdraw(1, true, GREEN_DHIDE_BODY);
            BankManager.withdraw(1, true, GREEN_DHIDE_CHAPS);
            BankManager.withdraw(1, true, GREEN_DHIDE_VAMB);
            BankManager.withdraw(1, true, YEW_SHORTBOW);
            BankManager.withdraw(700, true, RUNE_ARROW);
            BankManager.close(true);
            Utils.equipItem(GREEN_DHIDE_BODY);
            Utils.equipItem(GREEN_DHIDE_CHAPS);
            Utils.equipItem(GREEN_DHIDE_VAMB);
            Utils.equipItem(YEW_SHORTBOW);
            Utils.equipItem(RUNE_ARROW);
        }
        BankManager.open(true);
        BankManager.withdraw(12, true, SUMMER_PIE_ARRAY[0]);
        BankManager.withdraw(5, true, ItemID.STAMINA_POTION[0]);
        BankManager.close(true);
    }

    private void startQuest() {
        Log.log("Going to start");
        PathingUtil.walkToArea(START_AREA);

        if (NpcChat.talkToNPC("King Lathas")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("I am. What's our plan?");
            NPCInteraction.handleConversation("I'm ready.");
            NPCInteraction.handleConversation("Yes.");
            NPCInteraction.handleConversation();
        }
    }

    public void talkToKoftik() {
        if (!WHOLE_IBANS_LAIR_LOWER_LEVEL.contains(Player.getPosition())
                && !WHOLE_LEVEL_1_MAZE.contains(Player.getPosition())
                && !ALL_UGP_AREA_1_AND_3.contains(Player.getPosition())
                && !ALL_UGP_AREA_2.contains(Player.getPosition())) {

            Log.log("[Debug]: Going to Koftik (outside)");
            PathingUtil.walkToArea(ENTRANCE_TO_UNDERGOUND_PASS);
            if (NpcChat.talkToNPC("Koftik")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                NPCInteraction.handleConversation("I'll take my chances.");
            }
        }
    }

    public void talkToKoftikInside() {
        if (ENTRANCE_TO_UNDERGOUND_PASS.contains(Player.getPosition()))
            if (Utils.clickObj("Cave entrance", "Enter"))
                Timer.abc2WaitCondition(() -> UNDERGROUND_PASS_INSIDE_ENTRANCE.contains(Player.getPosition()), 8000, 12000);

        if (UNDERGROUND_PASS_INSIDE_ENTRANCE.contains(Player.getPosition())) {
            PathingUtil.walkToArea(POST_ROCKSLIDE_3);

            Log.log("[Debug]: Going to Koftik");
            PathingUtil.localNavigation(PRE_ROCKSLIDE_AREA);
        }
           /* if (Utils.clickObj()(3309, "Climb-over"))
                Timer.waitCondition(() -> POST_ROCKSLIDE_1.contains(Player.getPosition()), 6000, 9000);
        }
        if (POST_ROCKSLIDE_1.contains(Player.getPosition())) {
            cQuesterV2.status = "Navigating Rockslide 2";
            PathingUtil.localNavigation()(new RSTile(2468, 9723, 0));
            if (Utils.clickObj()(3309, "Climb-over"))
                Timer.waitCondition(() -> POST_ROCKSLIDE_2.contains(Player.getPosition()), 6000, 8000);

        }
        if (POST_ROCKSLIDE_2.contains(Player.getPosition())) {
            cQuesterV2.status = "Navigating Rockslide 3";
            PathingUtil.localNavigation()(new RSTile(2460, 9721, 0));
            if (Utils.clickObj()(3309, "Climb-over"))
                Timer.waitCondition(() -> POST_ROCKSLIDE_3.contains(Player.getPosition()), 6000, 8000);

        }*/
        if (POST_ROCKSLIDE_3.contains(Player.getPosition()) && !AFTER_BRIDGE.contains(Player.getPosition())) {
            cQuesterV2.status = "Talking to Koftik";
            if (NpcChat.talkToNPC("Koftik")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("What does it say?");
                NPCInteraction.handleConversation();
            }
        }
    }

    public void crossbridge() {
        if (Inventory.find(IRON_FIRE_ARROW_LIT).length < 1 && Equipment.find(IRON_FIRE_ARROW_LIT).length < 1) {
            if (Inventory.find(IRON_FIRE_ARROW_UNLIT).length < 1) {
                Utils.useItemOnItem(OILY_CLOTH, IRON_ARROW);
                Utils.shortSleep();
            }
            if (Inventory.find(IRON_FIRE_ARROW_UNLIT).length > 0) {
                Utils.useItemOnObject(IRON_FIRE_ARROW_UNLIT, "Fire");
                Utils.modSleep();
            }
        }
        if (Inventory.find(IRON_FIRE_ARROW_LIT).length > 0 || Equipment.find(IRON_FIRE_ARROW_LIT).length > 0) {
            PathingUtil.walkToArea(OUTLOOK_AREA);
            if (Equipment.find(IRON_FIRE_ARROW_LIT).length < 1) {
                Utils.equipItem(IRON_FIRE_ARROW_LIT);
                Utils.shortSleep();

            }
            if (Utils.clickObj(3340, "Fire-at")) {
                Timer.waitCondition(() -> AFTER_BRIDGE.contains(Player.getPosition()), 25000, 40000);
                Utils.modSleep();
            }
        }
    }

    public void getPlank() {
        if (Inventory.find(PLANK).length < 1) {
            cQuesterV2.status = "Getting plank";
            PathingUtil.localNavigation(PLANK_AREA);
            RSGroundItem[] plank = GroundItems.find(PLANK);

            if (plank.length > 0) {
                if (!plank[0].isClickable())
                    plank[0].adjustCameraTo();

                if (AccurateMouse.click(plank[0], "Take"))
                    Timer.waitCondition(() -> Inventory.find(PLANK).length > 0, 8000, 12000);
            }
        }
    }

    RSArea BEFORE_OBSTACLE_5 = new RSArea(new RSTile[]{new RSTile(2463, 9698, 0), new RSTile(2463, 9702, 0), new RSTile(2452, 9702, 0), new RSTile(2457, 9694, 0)});
    public static final RSTile[] PATH_TO_OBSTACLE_5 = new RSTile[]{new RSTile(2442, 9716, 0), new RSTile(2440, 9716, 0), new RSTile(2438, 9715, 0), new RSTile(2436, 9714, 0), new RSTile(2435, 9712, 0), new RSTile(2435, 9710, 0), new RSTile(2435, 9708, 0), new RSTile(2435, 9706, 0), new RSTile(2435, 9704, 0), new RSTile(2436, 9702, 0), new RSTile(2438, 9701, 0), new RSTile(2440, 9700, 0), new RSTile(2442, 9699, 0), new RSTile(2444, 9699, 0), new RSTile(2446, 9697, 0), new RSTile(2448, 9697, 0), new RSTile(2450, 9696, 0), new RSTile(2452, 9695, 0), new RSTile(2454, 9695, 0), new RSTile(2456, 9695, 0), new RSTile(2458, 9697, 0), new RSTile(2460, 9699, 0), new RSTile(2462, 9699, 0)};
    int ROCK = 23124;
    int PILE_OF_ROCKS = 3265; // "Climb"

    public static final RSTile[] ESCAPE_FROM_FALL_PT1 = new RSTile[]{new RSTile(2485, 9648, 0), new RSTile(2485, 9645, 0), new RSTile(2483, 9642, 0), new RSTile(2483, 9639, 0), new RSTile(2483, 9636, 0), new RSTile(2482, 9633, 0), new RSTile(2482, 9630, 0), new RSTile(2479, 9629, 0)};
    public static final RSTile[] ESCAPE_FROM_FALL_PT2 = new RSTile[]{new RSTile(2478, 9629, 0), new RSTile(2477, 9632, 0), new RSTile(2476, 9635, 0), new RSTile(2473, 9637, 0), new RSTile(2470, 9635, 0), new RSTile(2467, 9636, 0), new RSTile(2467, 9639, 0), new RSTile(2470, 9641, 0), new RSTile(2471, 9645, 0), new RSTile(2468, 9646, 0)};
    public static final RSTile[] ESCAPE_FROM_FALL_PT3 = new RSTile[]{new RSTile(2466, 9646, 0), new RSTile(2463, 9644, 0), new RSTile(2461, 9641, 0), new RSTile(2460, 9638, 0), new RSTile(2460, 9635, 0), new RSTile(2457, 9633, 0)};
    public static final RSTile[] ESCAPE_FROM_FALL_PT4 = new RSTile[]{new RSTile(2455, 9633, 0), new RSTile(2453, 9636, 0), new RSTile(2452, 9640, 0), new RSTile(2455, 9641, 0), new RSTile(2457, 9644, 0), new RSTile(2459, 9647, 0), new RSTile(2456, 9647, 0)};
    public static final RSTile[] ESCAPE_FROM_FALL_PT5 = new RSTile[]{new RSTile(2454, 9647, 0), new RSTile(2451, 9649, 0), new RSTile(2448, 9650, 0)};


    public void handleFailedObstacle5() {
        if (FAILED_OBSTACLE_5_AREA.contains(Player.getPosition())) {
            Walking.walkPath(ESCAPE_FROM_FALL_PT1);
            Utils.clickObj("Rockslide", "Climb-over");
            Utils.modSleep();

            Walking.walkPath(ESCAPE_FROM_FALL_PT2);
            Utils.clickObj("Rockslide", "Climb-over");
            Utils.modSleep();

            Walking.walkPath(ESCAPE_FROM_FALL_PT3);
            Utils.clickObj("Rockslide", "Climb-over");
            Utils.modSleep();

            Walking.walkPath(ESCAPE_FROM_FALL_PT4);
            Utils.clickObj("Rockslide", "Climb-over");
            Utils.modSleep();

            Walking.walkPath(ESCAPE_FROM_FALL_PT5);
            Utils.clickObj("Rockslide", "Climb-over");
            Utils.modSleep();

            Utils.clickObj(PILE_OF_ROCKS, "Climb");
            Utils.longSleep();
        }
    }

    public void handleObstacle5() {
        if (AFTER_BRIDGE.contains(Player.getPosition())) {
            cQuesterV2.status = "Going to obstacle 5";
            Walking.walkPath(PATH_TO_OBSTACLE_5);
            Utils.modSleep();
        }
        if (BEFORE_OBSTACLE_5.contains(Player.getPosition())) {
            Utils.useItemOnObject(ROPE, ROCK);
            Utils.modSleep();
        }
    }

    RSArea AFTER_OBSTACLE_5a = new RSArea(new RSTile[]{new RSTile(2465, 9701, 0), new RSTile(2465, 9689, 0), new RSTile(2493, 9692, 0), new RSTile(2495, 9709, 0)});
    RSArea FAILED_OBSTACLE_5_AREA = new RSArea(new RSTile(2439, 9660, 0), new RSTile(2491, 9621, 0));
    final RSTile[] PATH_AFTER_OBSTACLE_5 = new RSTile[]{new RSTile(2466, 9698, 0), new RSTile(2469, 9697, 0), new RSTile(2472, 9697, 0), new RSTile(2475, 9697, 0), new RSTile(2478, 9699, 0), new RSTile(2481, 9700, 0), new RSTile(2484, 9702, 0), new RSTile(2487, 9702, 0), new RSTile(2489, 9699, 0), new RSTile(2490, 9696, 0), new RSTile(2491, 9693, 0)};

    RSArea AFTER_OBSTACLE_5b = new RSArea(new RSTile[]{new RSTile(2483, 9680, 0), new RSTile(2482, 9675, 0), new RSTile(2493, 9675, 0), new RSTile(2493, 9675, 0), new RSTile(2495, 9691, 0), new RSTile(2489, 9690, 0)});

    /**
     * columns are from RIGHT to left, top to bottom
     */
    RSArea[] TILE_COLUMN_1 = {
            new RSArea(new RSTile(2475, 9681, 0), new RSTile(2476, 9682, 0)),
            new RSArea(new RSTile(2475, 9680, 0), new RSTile(2476, 9679, 0)),
            new RSArea(new RSTile(2475, 9678, 0), new RSTile(2476, 9677, 0)),
            new RSArea(new RSTile(2475, 9676, 0), new RSTile(2476, 9675, 0)),
            new RSArea(new RSTile(2475, 9674, 0), new RSTile(2476, 9673, 0))
    };
    RSArea[] TILE_COLUMN_2 = {
            new RSArea(new RSTile(2474, 9682, 0), new RSTile(2473, 9681, 0)),
            new RSArea(new RSTile(2473, 9680, 0), new RSTile(2474, 9679, 0)),
            new RSArea(new RSTile(2474, 9678, 0), new RSTile(2473, 9677, 0)),
            new RSArea(new RSTile(2473, 9676, 0), new RSTile(2474, 9675, 0)),
            new RSArea(new RSTile(2473, 9674, 0), new RSTile(2474, 9673, 0))
    };
    RSArea[] TILE_COLUMN_3 = {
            new RSArea(new RSTile(2472, 9681, 0), new RSTile(2471, 9682, 0)),
            new RSArea(new RSTile(2471, 9680, 0), new RSTile(2472, 9679, 0)),
            new RSArea(new RSTile(2471, 9678, 0), new RSTile(2472, 9677, 0)),
            new RSArea(new RSTile(2471, 9676, 0), new RSTile(2472, 9675, 0)),
            new RSArea(new RSTile(2471, 9674, 0), new RSTile(2472, 9673, 0))
    };
    RSArea[] TILE_COLUMN_4 = {
            new RSArea(new RSTile(2470, 9681, 0), new RSTile(2469, 9682, 0)),
            new RSArea(new RSTile(2469, 9680, 0), new RSTile(2470, 9679, 0)),
            new RSArea(new RSTile(2469, 9678, 0), new RSTile(2470, 9677, 0)),
            new RSArea(new RSTile(2469, 9676, 0), new RSTile(2470, 9675, 0)),
            new RSArea(new RSTile(2469, 9674, 0), new RSTile(2470, 9673, 0))
    };
    RSArea[] TILE_COLUMN_5 = {
            new RSArea(new RSTile(2468, 9681, 0), new RSTile(2467, 9682, 0)),
            new RSArea(new RSTile(2467, 9680, 0), new RSTile(2468, 9679, 0)),
            new RSArea(new RSTile(2467, 9678, 0), new RSTile(2468, 9677, 0)),
            new RSArea(new RSTile(2467, 9675, 0), new RSTile(2468, 9676, 0)),
            new RSArea(new RSTile(2467, 9674, 0), new RSTile(2468, 9673, 0))
    };

    RSArea correctColumn1;
    RSArea column1Option1; // first option to try when in column 1 and need to move
    RSArea column1Option2;

    RSArea correctColumn2;
    RSArea column2Option1; // first option to try when in column 2 and need to move
    RSArea column2Option2;

    RSArea correctColumn3;
    RSArea correctColumn4;
    RSArea correctColumn5;


    private RSArea tryTiles(RSArea[] columnArray, RSArea rowVar) {
        for (RSArea column : columnArray) {
            if (PathingUtil.walkToTile(Utils.getCornerTile(column))) {
                Timer.waitCondition(() -> Player.isMoving(), 3000, 5000);
                Timer.waitCondition(() -> !Player.isMoving(), 5000, 7000);
                Utils.shortSleep();
                if (column.contains(Player.getPosition())) {
                    General.println("[Debug]: Correct tile selected");
                    return rowVar = column;
                }
            }
        }
        General.println("[Debug]: Could not Identify correct tile");
        return null;
    }

    private void handleGridColumn1() {
        tryTiles(TILE_COLUMN_1, correctColumn1);
        if (correctColumn1 == TILE_COLUMN_1[0]) {
            column1Option1 = TILE_COLUMN_1[1];
            column1Option2 = TILE_COLUMN_2[0];

        } else if (correctColumn1 == TILE_COLUMN_1[1]) {
            column1Option1 = TILE_COLUMN_1[2];
            column1Option2 = TILE_COLUMN_2[1];

        } else if (correctColumn1 == TILE_COLUMN_1[2]) {
            column1Option1 = TILE_COLUMN_1[3];
            column1Option2 = TILE_COLUMN_2[2];

        } else if (correctColumn1 == TILE_COLUMN_1[3]) {
            column1Option1 = TILE_COLUMN_1[4];
            column1Option2 = TILE_COLUMN_2[3];

        } else if (correctColumn1 == TILE_COLUMN_1[4]) {
            column1Option1 = TILE_COLUMN_1[3];
            column1Option2 = TILE_COLUMN_2[4];
        }
    }

    private void handleGridColumn2() {
        tryTiles(TILE_COLUMN_2, correctColumn2);
        if (correctColumn2 == TILE_COLUMN_2[0]) {
            column1Option1 = TILE_COLUMN_2[1];
            column1Option2 = TILE_COLUMN_3[0];

        } else if (correctColumn2 == TILE_COLUMN_2[1]) {
            column1Option1 = TILE_COLUMN_2[2];
            column1Option2 = TILE_COLUMN_3[1];

        } else if (correctColumn2 == TILE_COLUMN_2[2]) {
            column1Option1 = TILE_COLUMN_2[3];
            column1Option2 = TILE_COLUMN_3[2];

        } else if (correctColumn2 == TILE_COLUMN_2[3]) {
            column1Option1 = TILE_COLUMN_2[4];
            column1Option2 = TILE_COLUMN_3[3];

        } else if (correctColumn2 == TILE_COLUMN_2[4]) {
            column1Option1 = TILE_COLUMN_2[3];
            column1Option2 = TILE_COLUMN_3[4];
        }
    }

    private void getPotentialTilesFromColumn(RSArea[] areaArray) {
        for (RSArea a : areaArray) {
            Utils.getCornerTile(a);
            if (Utils.getCornerTile(a).distanceTo(Player.getPosition()) <= 2) {
                potentialTiles.add(Utils.getCornerTile(a));
                General.println("[Debug]: Added tile " + a + " to potential tiles");
            }
        }
    }

    private void getAllPotentialTiles() {
        potentialTiles.clear();
        getPotentialTilesFromColumn(TILE_COLUMN_1);
        getPotentialTilesFromColumn(TILE_COLUMN_2);
        getPotentialTilesFromColumn(TILE_COLUMN_3);
        getPotentialTilesFromColumn(TILE_COLUMN_4);
        getPotentialTilesFromColumn(TILE_COLUMN_5);
    }


    public void goToObstacle6() {
        if (AFTER_OBSTACLE_5a.contains(Player.getPosition())) {
            cQuesterV2.status = "Going to Grid Obstacle";
            Walking.walkPath(PATH_AFTER_OBSTACLE_5);
            Utils.modSleep();
            if (Utils.clickObj("Rockslide", "Climb-over"))
                Timer.waitCondition(() -> AFTER_OBSTACLE_5b.contains(Player.getPosition()), 5000, 8000);

        }
        if (AFTER_OBSTACLE_5b.contains(Player.getPosition())) {
            PathingUtil.localNavigation(new RSTile(2483, 9679, 0));
            PathingUtil.movementIdle();
            if (Utils.clickObj("Rockslide", "Climb-over"))
                Utils.modSleep();
        }
    }

    public void handleGridObstacle() {
        cQuesterV2.status = "handling Grid Obstacle";
        handleGridColumn1();
        Utils.longSleep();

    }

    RSArea GRID_FAIL_AREA = new RSArea(new RSTile(2397, 9557, 0), new RSTile(2394, 9561, 0));
    RSArea AFTER_GRID_GATE_SMALL = new RSArea(new RSTile(2465, 9683, 0), new RSTile(2459, 9668, 0));
    RSArea ALTAR_AREA = new RSArea(new RSTile(2409, 9681, 0), new RSTile(2424, 9664, 0));
    RSArea AFTER_NORTH_TRAP_1 = new RSArea(new RSTile(2420, 9682, 0), new RSTile(2417, 9684, 0));
    RSArea AFTER_NORTH_TRAP_2 = new RSArea(new RSTile(2420, 9686, 0), new RSTile(2414, 9688, 0));
    RSArea AFTER_NORTH_TRAP_3 = new RSArea(new RSTile(2420, 9690, 0), new RSTile(2413, 9698, 0));

    RSArea ORB_2_AREA = new RSArea(new RSTile(2393, 9683, 0), new RSTile(2384, 9686, 0));
    RSArea ORB_3_AREA = new RSArea(new RSTile[]{new RSTile(2393, 9674, 0), new RSTile(2391, 9672, 0), new RSTile(2384, 9674, 0), new RSTile(2384, 9678, 0), new RSTile(2387, 9681, 0), new RSTile(2392, 9680, 0), new RSTile(2393, 9679, 0)});

    public void getOrbOfLight1() {
        if (AFTER_GRID_GATE_SMALL.contains(Player.getPosition())) {
            cQuesterV2.status = "Going to Altar area";
            checkHealth();
            PathingUtil.localNavigation(ALTAR_AREA);
            checkHealth();
        } else if (ALTAR_AREA.contains(Player.getPosition()) && Inventory.find(ORB_OF_LIGHT_1_INV).length < 1) {
            cQuesterV2.status = "Going to first trap";
            if (PathingUtil.clickScreenWalk(new RSTile(2418, 9680, 0))) {
                PathingUtil.movementIdle();
                Utils.shortSleep();
            }
            cQuesterV2.status = "Handling first trap";
            if (Utils.useItemOnObject(PLANK, "Flat rock")) {
                Timer.waitCondition(() -> AFTER_NORTH_TRAP_1.contains(Player.getPosition()), 10000, 12000);
                PathingUtil.movementIdle();
                Utils.shortSleep();
            }
        }
        if (AFTER_NORTH_TRAP_1.contains(Player.getPosition()) && Inventory.find(ORB_OF_LIGHT_1_INV).length < 1) {
            cQuesterV2.status = "Handling second trap";
            if (PathingUtil.clickScreenWalk(new RSTile(2418, 9684, 0))) {
                PathingUtil.movementIdle();
            }

            if (Utils.useItemOnObject(PLANK, "Flat rock")) {
                Timer.waitCondition(() -> AFTER_NORTH_TRAP_2.contains(Player.getPosition()), 10000, 12000);
                Utils.shortSleep();
            }
        }
        if (AFTER_NORTH_TRAP_2.contains(Player.getPosition()) && Inventory.find(ORB_OF_LIGHT_1_INV).length < 1) {
            cQuesterV2.status = "Handling third trap";
            if (PathingUtil.clickScreenWalk(new RSTile(2416, 9688, 0))) {
                PathingUtil.movementIdle();
            }
            if (Utils.useItemOnObject(PLANK, "Flat rock")) {
                Timer.waitCondition(() -> AFTER_NORTH_TRAP_3.contains(Player.getPosition()), 10000, 12000);
                PathingUtil.movementIdle();
                Utils.shortSleep();
            }
        }
        if (AFTER_NORTH_TRAP_3.contains(Player.getPosition()) && Inventory.find(ORB_OF_LIGHT_1_INV).length < 1) {
            cQuesterV2.status = "Looting Orb of Light";
            RSObject[] orb = Objects.findNearest(20, ORB_OF_LIGHT_1);

            if (orb.length > 0) {
                if (!orb[0].isClickable())
                    DaxCamera.focus(orb[0]);

                if (orb[0].click()) {
                    Timer.waitCondition(() -> Inventory.find(ORB_OF_LIGHT_1_INV).length > 0, 6000, 9000);
                    Utils.shortSleep();
                }
            }
        }
        if (AFTER_NORTH_TRAP_3.contains(Player.getPosition()) && Inventory.find(ORB_OF_LIGHT_1_INV).length > 0) {
            cQuesterV2.status = "Handling third trap";
            RSTile target = new RSTile(2416, 9690, 0);
            if (PathingUtil.clickScreenWalk(target)) {
                PathingUtil.movementIdle();
            }

            if (Utils.useItemOnObject(PLANK, "Flat rock")) {
                Timer.waitCondition(() -> AFTER_NORTH_TRAP_3.contains(Player.getPosition()), 10000, 12000);
                PathingUtil.movementIdle();
                Utils.shortSleep();
            }
        }
        if (AFTER_NORTH_TRAP_2.contains(Player.getPosition()) && Inventory.find(ORB_OF_LIGHT_1_INV).length > 0) {
            cQuesterV2.status = "Handling second trap";
            if (PathingUtil.clickScreenWalk(new RSTile(2418, 9686, 0))) {
                PathingUtil.movementIdle();
            }

            if (Utils.useItemOnObject(PLANK, "Flat rock")) {
                Timer.waitCondition(() -> AFTER_NORTH_TRAP_1.contains(Player.getPosition()), 10000, 12000);
                PathingUtil.movementIdle();
                Utils.shortSleep();
            }
        }
        if (AFTER_NORTH_TRAP_1.contains(Player.getPosition()) && Inventory.find(ORB_OF_LIGHT_1_INV).length > 0) {
            cQuesterV2.status = "Handling first trap";
            if (PathingUtil.clickScreenWalk(new RSTile(2418, 9682, 0))) {
                PathingUtil.movementIdle();
            }
            if (Utils.useItemOnObject(PLANK, "Flat rock")) {
                Timer.waitCondition(() -> ALTAR_AREA.contains(Player.getPosition()), 10000, 12000);
                PathingUtil.movementIdle();
                Utils.shortSleep();
            }
        }

    }


    public void checkHealth() {
        RSItem[] food = Inventory.find(FULL_SUMMER_PIE, MEAT_PIE, PIZZA, HALF_SUMMER_PIE);

        if (food.length > 0 && Combat.getHPRatio() < General.random(40, 60)) {
            cQuesterV2.status = "Eating";
            if (food[0].click())
                Utils.microSleep();
        }
        if (food.length > 0 && Combat.getHPRatio() < General.random(50, 65)) {
            cQuesterV2.status = "Eating";
            if (food[0].click())
                Utils.microSleep();
        }
    }

    public void getOrbOfLight2() {
        if (ALTAR_AREA.contains(Player.getPosition()) && Inventory.find(ORB_OF_LIGHT_1_INV).length > 0 &&
                Inventory.find(ORB_OF_LIGHT_2_INV).length < 1) {
            checkHealth();
            cQuesterV2.status = "Getting Second Orb";
            PathingUtil.localNavigation(ORB_2_AREA);
        }

        if (ORB_2_AREA.contains(Player.getPosition()) && Inventory.find(ORB_OF_LIGHT_2_INV).length < 1) {
            RSObject[] orb = Objects.findNearest(20, 37325);

            if (orb.length > 0) {
                if (!orb[0].isClickable())
                    orb[0].adjustCameraTo();

                if (orb[0].click())
                    Timer.waitCondition(() -> Inventory.find(ORB_OF_LIGHT_2_INV).length > 0, 6000, 9000);
            }
        }
        if (!ALTAR_AREA.contains(Player.getPosition()) && Inventory.find(ORB_OF_LIGHT_2_INV).length > 0) {
            checkHealth();
            PathingUtil.localNavigation(ALTAR_AREA);
        }

    }


    public void getOrbOfLight3() {
        if (ALTAR_AREA.contains(Player.getPosition()) && Inventory.find(ORB_OF_LIGHT_2_INV).length > 0 &&
                Inventory.find(ORB_OF_LIGHT_3_INV).length < 1) {
            cQuesterV2.status = "Getting third Orb";
            checkHealth();
            if (PathingUtil.clickScreenWalk(WEST_TRAP_TILE_1)) {
                PathingUtil.movementIdle();
            }

            cQuesterV2.status = "Handling first trap";
            if (Utils.useItemOnObject(PLANK, "Flat rock")) {
                Timer.waitCondition(() -> AFTER_WEST_TRAP_1.contains(Player.getPosition()), 10000, 12000);
                PathingUtil.movementIdle();
                Utils.shortSleep();
            }
        }
        if (AFTER_WEST_TRAP_1.contains(Player.getPosition()) && Inventory.find(ORB_OF_LIGHT_3_INV).length < 1) {
            cQuesterV2.status = "Handling second trap";
            if (PathingUtil.clickScreenWalk(WEST_TRAP_TILE_2)) {
                PathingUtil.movementIdle();
            }

            if (Utils.useItemOnObject(PLANK, "Flat rock")) {
                Timer.slowWaitCondition(() -> AFTER_WEST_TRAP_2.contains(Player.getPosition()), 10000, 12000);
                PathingUtil.movementIdle();
                Utils.shortSleep();
            }
        }
        if (AFTER_WEST_TRAP_2.contains(Player.getPosition()) && Inventory.find(ORB_OF_LIGHT_3_INV).length < 1) {
            cQuesterV2.status = "Handling third trap";
            if (PathingUtil.clickScreenWalk(WEST_TRAP_TILE_3)) {
                PathingUtil.movementIdle();
            }
            if (Utils.useItemOnObject(PLANK, "Flat rock")) {
                Timer.waitCondition(() -> AFTER_WEST_TRAP_3.contains(Player.getPosition()), 10000, 12000);
                PathingUtil.movementIdle();
                Utils.shortSleep();
            }
        }
        if (AFTER_WEST_TRAP_3.contains(Player.getPosition()) && Inventory.find(ORB_OF_LIGHT_3_INV).length < 1) {
            cQuesterV2.status = "Handling fourth trap";
            if (PathingUtil.clickScreenWalk(WEST_TRAP_TILE_4)) {
                PathingUtil.movementIdle();
            }
            if (Utils.useItemOnObject(PLANK, "Flat rock")) {
                Timer.waitCondition(() -> AFTER_WEST_TRAP_4.contains(Player.getPosition()), 10000, 12000);
                PathingUtil.movementIdle();
                Utils.shortSleep();
            }
        }
        if (AFTER_WEST_TRAP_4.contains(Player.getPosition()) && Inventory.find(ORB_OF_LIGHT_3_INV).length < 1) {
            cQuesterV2.status = "Handling last trap";
            checkHealth();
            if (PathingUtil.clickScreenWalk(WEST_TRAP_TILE_5)) {
                PathingUtil.movementIdle();
            }
            if (Utils.useItemOnObject(PLANK, "Flat rock")) {
                Timer.waitCondition(() -> ORB_3_AREA.contains(Player.getPosition()), 10000, 12000);
                PathingUtil.movementIdle();
                Utils.shortSleep();
            }
        }
        if (ORB_3_AREA.contains(Player.getPosition()) && Inventory.find(ORB_OF_LIGHT_3_INV).length < 1) {
            cQuesterV2.status = "Getting third Orb";
            RSObject[] orb = Objects.findNearest(20, 37324);

            if (orb.length > 0) {
                if (!orb[0].isClickable())
                    orb[0].adjustCameraTo();

                if (orb[0].click()) {
                    Timer.waitCondition(() -> Inventory.find(ORB_OF_LIGHT_3_INV).length > 0, 6000, 9000);
                    Utils.shortSleep();
                }
            }
        }

        /**
         * returning back to main room
         */
        if (ORB_3_AREA.contains(Player.getPosition()) && Inventory.find(ORB_OF_LIGHT_3_INV).length > 0) {
            cQuesterV2.status = "Handling last trap";
            if (PathingUtil.clickScreenWalk(WEST_TRAP_TILE_5_RETURN)) {
                PathingUtil.movementIdle();
            }

            if (Utils.useItemOnObject(PLANK, "Flat rock"))
                Timer.slowWaitCondition(() -> AFTER_WEST_TRAP_4.contains(Player.getPosition()), 10000, 12000);
        }
        if (AFTER_WEST_TRAP_4.contains(Player.getPosition()) && Inventory.find(ORB_OF_LIGHT_3_INV).length > 0) {
            cQuesterV2.status = "Handling second trap";
            if (PathingUtil.clickScreenWalk(WEST_TRAP_TILE_4_RETURN)) {
                PathingUtil.movementIdle();

            }
            if (Utils.useItemOnObject(PLANK, "Flat rock")) {
                Timer.slowWaitCondition(() -> AFTER_WEST_TRAP_3.contains(Player.getPosition()), 10000, 12000);
                PathingUtil.movementIdle();
            }
        }

        if (AFTER_WEST_TRAP_3.contains(Player.getPosition()) && Inventory.find(ORB_OF_LIGHT_3_INV).length > 0) {
            cQuesterV2.status = "Handling first trap";
            if (PathingUtil.clickScreenWalk(WEST_TRAP_TILE_3_RETURN)) {
                PathingUtil.movementIdle();

            }
            if (Utils.useItemOnObject(PLANK, "Flat rock")) {
                Timer.slowWaitCondition(() -> AFTER_WEST_TRAP_2.contains(Player.getPosition()), 10000, 12000);
                PathingUtil.movementIdle();
            }
        }
        if (AFTER_WEST_TRAP_2.contains(Player.getPosition()) && Inventory.find(ORB_OF_LIGHT_3_INV).length > 0) {
            cQuesterV2.status = "Handling first trap";
            if (PathingUtil.clickScreenWalk(WEST_TRAP_TILE_2_RETURN)) {
                PathingUtil.movementIdle();
            }
            if (Utils.useItemOnObject(PLANK, "Flat rock")) {
                Timer.slowWaitCondition(() -> AFTER_WEST_TRAP_1.contains(Player.getPosition()), 10000, 12000);
                PathingUtil.movementIdle();
            }
        }
        if (AFTER_WEST_TRAP_1.contains(Player.getPosition()) && Inventory.find(ORB_OF_LIGHT_3_INV).length > 0) {
            cQuesterV2.status = "Handling first trap";
            if (PathingUtil.clickScreenWalk(WEST_TRAP_TILE_1_RETURN)) {
                PathingUtil.movementIdle();
            }
            if (Utils.useItemOnObject(PLANK, "Flat rock"))
                Timer.slowWaitCondition(() -> ALTAR_AREA.contains(Player.getPosition()), 10000, 12000);
        }
    }

    RSArea ORB_4_AREA = new RSArea(new RSTile(2380, 9667, 0), new RSTile(2389, 9670, 0));

    public void getOrbOfLight4() {
        if (ALTAR_AREA.contains(Player.getPosition()) && Inventory.find(ORB_OF_LIGHT_4_INV).length < 1) {
            cQuesterV2.status = "Getting orb 4";
            PathingUtil.localNavigation(new RSTile(2384, 9668, 0));

        }
        if (ORB_4_AREA.contains(Player.getPosition()) && Inventory.find(ORB_OF_LIGHT_4_INV).length < 1) {
            RSObject[] orb = Objects.findNearest(20, 37323);
            cQuesterV2.status = "Getting orb 4";
            if (orb.length > 0) {
                if (!orb[0].isClickable())
                    orb[0].adjustCameraTo();

                if (orb[0].click("Search")) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation("Yes, I'll give it a go.");
                    Timer.waitCondition(() -> Inventory.find(ORB_OF_LIGHT_4_INV).length > 0, 8000, 12000);
                }
            }

        }
        if (!ALTAR_AREA.contains(Player.getPosition()) && Inventory.find(ORB_OF_LIGHT_4_INV).length > 0) {
            checkHealth();
            PathingUtil.localNavigation(ALTAR_AREA);
        }
    }

    RSArea FURNACE_AREA = new RSArea(new RSTile[]{new RSTile(2457, 9676, 0), new RSTile(2450, 9676, 0), new RSTile(2450, 9681, 0), new RSTile(2454, 9685, 0), new RSTile(2459, 9684, 0)});

    public void destroyOrb1() {
        checkHealth();
        if (BankManager.checkInventoryItems(ORB_OF_LIGHT_3_INV, ORB_OF_LIGHT_1_INV, ORB_OF_LIGHT_2_INV, ORB_OF_LIGHT_4_INV)) {
            cQuesterV2.status = "Destroying orbs";
            PathingUtil.walkToArea(FURNACE_AREA);
            checkHealth();
            if (FURNACE_AREA.contains(Player.getPosition())) {
                if (Utils.useItemOnObject(ORB_OF_LIGHT_1_INV, "Furnace"))
                    Timer.waitCondition(() -> Inventory.find(ORB_OF_LIGHT_1_INV).length < 1, 8000, 12000);
            }
        }
    }

    public void destroyOrb2() {
        if (BankManager.checkInventoryItems(ORB_OF_LIGHT_3_INV, ORB_OF_LIGHT_2_INV, ORB_OF_LIGHT_4_INV)) {
            cQuesterV2.status = "Destroying orb 2";
            PathingUtil.walkToArea(FURNACE_AREA);
            if (FURNACE_AREA.contains(Player.getPosition())) {
                if (Utils.useItemOnObject(ORB_OF_LIGHT_2_INV, "Furnace"))
                    Timer.waitCondition(() -> Inventory.find(ORB_OF_LIGHT_2_INV).length < 1, 8000, 12000);
            }
        }
    }

    public void destroyOrb3() {
        if (BankManager.checkInventoryItems(ORB_OF_LIGHT_3_INV, ORB_OF_LIGHT_4_INV)) {
            cQuesterV2.status = "Destroying orb 3";
            PathingUtil.walkToArea(FURNACE_AREA);
            if (FURNACE_AREA.contains(Player.getPosition())) {
                if (Utils.useItemOnObject(ORB_OF_LIGHT_3_INV, "Furnace"))
                    Timer.waitCondition(() -> Inventory.find(ORB_OF_LIGHT_3_INV).length < 1, 8000, 12000);
            }
        }
    }

    public void destroyOrb4() {
        if (BankManager.checkInventoryItems(ORB_OF_LIGHT_4_INV)) {
            cQuesterV2.status = "Destroying orb 4";
            PathingUtil.walkToArea(FURNACE_AREA);
            if (FURNACE_AREA.contains(Player.getPosition())) {
                if (Utils.useItemOnObject(ORB_OF_LIGHT_4_INV, "Furnace"))
                    Timer.waitCondition(() -> Inventory.find(ORB_OF_LIGHT_4_INV).length < 1, 8000, 12000);
            }
        }
    }

    RSArea START_OF_UGP_AREA_2 = new RSArea(new RSTile(2417, 9661, 0), new RSTile(2430, 9648, 0));

    public void goDownWell() {
        checkHealth();
        if (FURNACE_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Going to well";
            PathingUtil.walkToArea(ALTAR_AREA);
        }
        if (ALTAR_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Going down well";
            Utils.clickObj("Well", "Climb-down");
            Timer.waitCondition(() -> START_OF_UGP_AREA_2.contains(Player.getPosition()), 10000, 12000);
        }
    }

    RSArea CELL_AREA = new RSArea(new RSTile(2376, 9662, 0), new RSTile(2398, 9649, 0));
    RSArea INSIDE_CELL_1 = new RSArea(new RSTile(2392, 9654, 0), new RSTile(2394, 9650, 0));

    // contains most of the corridor to the ledge
    RSArea AFTER_MUD_PATCH = new RSArea(new RSTile(2395, 9647, 0), new RSTile(2379, 9643, 0));

    RSArea BEFORE_NARROW_LEDGE = new RSArea(new RSTile(2374, 9644, 0), new RSTile(2379, 9643, 0));
    RSArea FALLEN_OFF_LEDGE_AREA = new RSArea(new RSTile(2373, 9639, 0), new RSTile(2369, 9645, 0));
    RSArea AFTER_NARROW_LEDGE = new RSArea(new RSTile(2370, 9638, 0), new RSTile(2379, 9631, 0));
    RSArea AFTER_STONE_BRIDGE_1 = new RSArea(new RSTile(2386, 9631, 0), new RSTile(2381, 9634, 0));
    RSArea AFTER_STONE_BRIDGE_2 = new RSArea(new RSTile(2388, 9631, 0), new RSTile(2391, 9627, 0));
    RSArea AFTER_STONE_BRIDGE_3 = new RSArea(new RSTile(2393, 9627, 0), new RSTile(2398, 9632, 0));
    RSArea AFTER_STONE_BRIDGE_4 = new RSArea(new RSTile[]{new RSTile(2400, 9632, 0), new RSTile(2404, 9632, 0), new RSTile(2404, 9637, 0), new RSTile(2405, 9637, 0), new RSTile(2405, 9638, 0), new RSTile(2403, 9638, 0), new RSTile(2403, 9633, 0), new RSTile(2400, 9633, 0)});
    RSArea AFTER_STONE_BRIDGE_5 = new RSArea(new RSTile(2407, 9637, 0), new RSTile(2415, 9637, 0));
    RSArea AFTER_AGILITY_OBSTACLE = new RSArea(new RSTile(2419, 9637, 0), new RSTile(2430, 9628, 0));


    public void goToCells() {
        if (START_OF_UGP_AREA_2.contains(Player.getPosition()) || FALLEN_OFF_LEDGE_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Going to cells";
            PathingUtil.localNavigation(new RSTile(2393, 9655, 0));
        }
        if (CELL_AREA.contains(Player.getPosition()) && !INSIDE_CELL_1.contains(Player.getPosition())) {
            cQuesterV2.status = "Picking lock";
            Utils.clickObj("Gate", "Picklock");
            Timer.waitCondition(() -> INSIDE_CELL_1.contains(Player.getPosition()), 3000, 5000);
        }
        if (INSIDE_CELL_1.contains(Player.getPosition())) {
            cQuesterV2.status = "Using Spade on mud";
            Utils.useItemOnObject(SPADE, "Loose mud");
            Timer.waitCondition(() -> AFTER_MUD_PATCH.contains(Player.getPosition()), 3000, 5000);
        }
        if (AFTER_MUD_PATCH.contains(Player.getPosition())) {
            cQuesterV2.status = "Going to Ledge";
            PathingUtil.localNavigation(BEFORE_NARROW_LEDGE);
        }
        if (BEFORE_NARROW_LEDGE.contains(Player.getPosition())) {
            cQuesterV2.status = "Crossing Ledge";
            Utils.clickObj("Ledge", "Cross");
            Utils.longSleep();
        }
        if (AFTER_NARROW_LEDGE.contains(Player.getPosition())) {
            cQuesterV2.status = "Navigating Agility Obstacle One";
            if (PathingUtil.clickScreenWalk(new RSTile(2379, 9634, 0))) {
                Timer.waitCondition(() -> Player.isMoving(), 2000, 4000);
                Timer.waitCondition(() -> !Player.isMoving(), 5000, 9000);
            }
            Utils.clickObj("Stone bridge", "Cross");
            Timer.waitCondition(() -> AFTER_STONE_BRIDGE_1.contains(Player.getPosition()), 6000, 9000);
        }
        if (AFTER_STONE_BRIDGE_1.contains(Player.getPosition())) {
            cQuesterV2.status = "Navigating Agility Obstacle two";
            if (PathingUtil.clickScreenWalk(new RSTile(2386, 9631, 0))) {
                Timer.waitCondition(() -> Player.isMoving(), 2000, 4000);
                Timer.waitCondition(() -> !Player.isMoving(), 5000, 9000);
            }
            if (Utils.clickObj("Stone bridge", "Cross"))
                Timer.waitCondition(() -> AFTER_STONE_BRIDGE_2.contains(Player.getPosition()), 6000, 9000);
        }
        if (AFTER_STONE_BRIDGE_2.contains(Player.getPosition())) {
            cQuesterV2.status = "Navigating Agility Obstacle three";
            if (PathingUtil.clickScreenWalk(new RSTile(2391, 9627, 0))) {
                Timer.waitCondition(() -> Player.isMoving(), 2000, 4000);
                Timer.waitCondition(() -> !Player.isMoving(), 5000, 9000);
            }
            if (Utils.clickObj("Stone bridge", "Cross"))
                Timer.waitCondition(() -> AFTER_STONE_BRIDGE_3.contains(Player.getPosition()), 6000, 9000);
        }
        if (AFTER_STONE_BRIDGE_3.contains(Player.getPosition())) {
            cQuesterV2.status = "Navigating Agility Obstacle four";
            if (PathingUtil.clickScreenWalk(new RSTile(2398, 9632, 0))) {
                Timer.waitCondition(() -> Player.isMoving(), 2000, 4000);
                Timer.waitCondition(() -> !Player.isMoving(), 5000, 9000);
            }
            if (Utils.clickObj("Stone bridge", "Cross"))
                Timer.waitCondition(() -> AFTER_STONE_BRIDGE_4.contains(Player.getPosition()), 6000, 9000);
        }
        if (AFTER_STONE_BRIDGE_4.contains(Player.getPosition())) {
            cQuesterV2.status = "Navigating Agility Obstacle five";
            if (PathingUtil.clickScreenWalk(new RSTile(2405, 9637, 0))) {
                Timer.waitCondition(() -> Player.isMoving(), 2000, 4000);
                Timer.waitCondition(() -> !Player.isMoving(), 5000, 9000);
            }
            if (Utils.clickObj("Stone bridge", "Cross"))
                Timer.waitCondition(() -> AFTER_STONE_BRIDGE_5.contains(Player.getPosition()), 6000, 9000);
        }
        if (AFTER_STONE_BRIDGE_5.contains(Player.getPosition())) {
            cQuesterV2.status = "Finishing Agility obstacle";
            PathingUtil.localNavigation(AFTER_AGILITY_OBSTACLE);
        }
    }

    // first pipe after agility obstacle
    RSArea PIPE_1_AREA = new RSArea(new RSTile(2432, 9600, 0), new RSTile(2418, 9609, 0));
    RSArea WHOLE_UNICORN_AREA = new RSArea(new RSTile(2413, 9612, 0), new RSTile(2393, 9591, 0));
    RSArea IN_FRONT_OF_CAGE = new RSArea(new RSTile(2398, 9605, 0), new RSTile(2396, 9606, 0));
    int PIECE_OF_RAILING = 1486;

    RSArea BOULDER_AREA = new RSArea(new RSTile[]{new RSTile(2396, 9598, 0), new RSTile(2394, 9596, 0), new RSTile(2394, 9594, 0), new RSTile(2400, 9594, 0), new RSTile(2400, 9598, 0)});

    public void goToUnicorn() {
        if (AFTER_AGILITY_OBSTACLE.contains(Player.getPosition())) {
            cQuesterV2.status = "Going to pipe";
            PathingUtil.localNavigation(PIPE_1_AREA);
        }
        if (PIPE_1_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Handling pipe";
            if (Utils.clickObj("Pipe", "Squeeze-through"))
                Timer.waitCondition(() -> WHOLE_UNICORN_AREA.contains(Player.getPosition()), 10000, 12000);
        }
        if (WHOLE_UNICORN_AREA.contains(Player.getPosition())) {
            if (Inventory.find(PIECE_OF_RAILING).length < 1) {
                cQuesterV2.status = "Getting Railing";
                PathingUtil.localNavigation(IN_FRONT_OF_CAGE);
                if (Utils.clickObj("Cage", "Search"))
                    Timer.waitCondition(() -> Inventory.find(PIECE_OF_RAILING).length > 0, 8000, 12000);
            }
            if (Inventory.find(PIECE_OF_RAILING).length > 0) {
                cQuesterV2.status = "Going to boulder";
                PathingUtil.localNavigation(BOULDER_AREA);
                cQuesterV2.status = "Pushing boulder";
                if (Utils.useItemOnNPC(PIECE_OF_RAILING, "Boulder"))
                    Timer.waitCondition(() -> Game.getSetting(162) == -1308618271, 10000, 12000);
            }
        }
    }

    int UNICORN_HORN = 1487;

    RSArea WHOLE_UGP_AREA_3 = new RSArea(new RSTile[]{new RSTile(2367, 9663, 0), new RSTile(2376, 9663, 0), new RSTile(2384, 9693, 0), new RSTile(2411, 9698, 0), new RSTile(2428, 9707, 0), new RSTile(2430, 9728, 0), new RSTile(2367, 9728, 0)});

    public void getHorn() {
        if (!WHOLE_UGP_AREA_3.contains(Player.getPosition())) {
            cQuesterV2.status = "Getting Unicorn Horn";
            PathingUtil.localNavigation(IN_FRONT_OF_CAGE);
            if (Inventory.find(UNICORN_HORN).length < 1) {
                if (Utils.clickObj("Search", "Smashed cage")) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                    Utils.shortSleep();
                }
            }
            if (Utils.clickObj("Tunnel", "Pass-through"))
                Timer.waitCondition(() -> WHOLE_UGP_AREA_3.contains(Player.getPosition()), 8000, 12000);
        }
    }

    RSArea PALADIN_AREA = new RSArea(new RSTile(2428, 9725, 0), new RSTile(2420, 9716, 0));
    Area palAreaNew = Area.fromRectangle(new WorldTile(2428, 9725, 0),
            new WorldTile(2420, 9716, 0));
    RSArea UGP_AREA_3_PART_1 = new RSArea(
            new RSTile[]{
                    new RSTile(2375, 9664, 0),
                    new RSTile(2367, 9664, 0),
                    new RSTile(2368, 9693, 0),
                    new RSTile(2380, 9707, 0),
                    new RSTile(2394, 9713, 0),
                    new RSTile(2393, 9694, 0),
                    new RSTile(2381, 9693, 0),
                    new RSTile(2379, 9681, 0)
            }
    );
    RSTile SAFE_TILE_PALADINS = new RSTile(2413, 9722, 0);// new RSTile(2423, 9709, 0);
    RSArea SAFE_AREA_PALADINS = new RSArea(new RSTile(2423, 9713, 0), new RSTile(2422, 9709, 0));
    int JERRO_BADGE = 1488;
    int HARRY_BADGE = 1490;
    int CARL_BADGE = 1489;
    int MEAT_PIE = 2327;

    public void handlePalidins() {
        cQuesterV2.status = "Going to Paladins";
        if (!BankManager.checkInventoryItems(JERRO_BADGE, HARRY_BADGE, CARL_BADGE)
                && WHOLE_UGP_AREA_3.contains(Player.getPosition())) {

            drinkStamina();
            if (UGP_AREA_3_PART_1.contains(Player.getPosition()))
                PathingUtil.localNavigation(new RSTile(2391, 9707, 0));

            PathingUtil.localNavigation(PALADIN_AREA);

            Utils.equipItem(RUNE_ARROW);
            Inventory.drop(PIE_DISH); //clears up space for items from Jerro

            cQuesterV2.status = "Talking to Paladins";
            if (NpcChat.talkToNPC("Sir Jerro")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }

            cQuesterV2.status = "Killing Paladins";
            killPaladin("Sir Jerro", JERRO_BADGE);
            killPaladin("Sir Harry", HARRY_BADGE);
            killPaladin("Sir Carl", CARL_BADGE);

        }
    }

    private void killPaladin(String name, int badgeId) {
        RSNPC[] paladin = NPCs.findNearest(name);

        while (paladin.length > 0 && Inventory.find(badgeId).length < 1) {
            General.sleep(50);
            if (!paladin[0].isClickable())
                paladin[0].adjustCameraTo();

            if (!paladin[0].isInCombat() && !Combat.isUnderAttack()) {
                if (AccurateMouse.click(paladin[0], "Attack"))
                    Timer.waitCondition(() -> Combat.isUnderAttack(), 4000, 7000);
            }
            if (paladin[0].isInCombat() && !SAFE_AREA_PALADINS.contains(Player.getPosition())) {
                PathingUtil.localNavigation(SAFE_TILE_PALADINS);
                Utils.shortSleep();
            }
            RSGroundItem[] badge = GroundItems.find(badgeId);
            if (badge.length > 0) {
                if (Inventory.isFull()) {
                    Inventory.drop(PIE_DISH);
                    Inventory.drop(MEAT_PIE);
                }
                if (AccurateMouse.click(badge[0], "Take"))
                    Timer.waitCondition(() -> Inventory.find(badgeId).length > 0, 8000, 12000);
            }

        }
    }

    RSArea WELL_AREA = new RSArea(new RSTile(2383, 9710, 0), new RSTile(2368, 9726, 0));

    public void goToWell() {
        if (BankManager.checkInventoryItems(JERRO_BADGE, HARRY_BADGE, CARL_BADGE) || WELL_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Going to well";
            PathingUtil.walkToArea(WELL_AREA);
            if (Utils.useItemOnObject(UNICORN_HORN, "Well"))
                Timer.waitCondition(() -> Inventory.find(UNICORN_HORN).length < 1, 6000, 9000);

            if (Utils.useItemOnObject(JERRO_BADGE, "Well"))
                Timer.waitCondition(() -> Inventory.find(JERRO_BADGE).length < 1, 6000, 9000);

            if (Utils.useItemOnObject(HARRY_BADGE, "Well"))
                Timer.waitCondition(() -> Inventory.find(HARRY_BADGE).length < 1, 6000, 9000);

            if (Utils.useItemOnObject(CARL_BADGE, "Well"))
                Timer.waitCondition(() -> Inventory.find(CARL_BADGE).length < 1, 6000, 9000);
        }

    }


    public void leaveWellArea() {
        if (WHOLE_UGP_AREA_3.contains(Player.getPosition())) {
            cQuesterV2.status = "Leaving Well area";
            if (Utils.clickObj("Door", "Open"))
                Timer.waitCondition(() -> !WHOLE_LEVEL_1_MAZE.contains(Player.getPosition()), 9000, 12000);
        }
        Utils.modSleep();
        NPCInteraction.handleConversation();
    }

    /**
     * ALL the below areas relate to the very large maze-type area
     */
    RSArea WHOLE_LEVEL_1_MAZE = new RSArea(new RSTile(2177, 4541, 1), new RSTile(2111, 4736, 1));
    RSArea AREA_BEFORE_DOWNWARD_STAIRS = new RSArea(new RSTile(2153, 4544, 1), new RSTile(2147, 4548, 1));
    RSArea BOTTOM_RIGHT_CORNER = new RSArea(new RSTile(2174, 4545, 1), new RSTile(2165, 4555, 1));

    RSArea WHOLE_IBANS_LAIR_LOWER_LEVEL = new RSArea(new RSTile(2371, 9790, 0), new RSTile(2303, 9920, 0));
    RSArea DWARF_AREA = new RSArea(new RSTile(2309, 9808, 0), new RSTile(2323, 9802, 0));
    RSArea AREA_BEFORE_UPWARD_STAIRS = new RSArea(new RSTile(2338, 9796, 0), new RSTile(2334, 9798, 0));
    RSArea INFRONT_OF_KLANKS_HOUSE = new RSArea(new RSTile(2327, 9801, 0), new RSTile(2324, 9802, 0));

    private void drinkStamina() {
        RSItem[] stamina = Inventory.find(ItemID.STAMINA_POTION);

        if (stamina.length > 0 && Game.getSetting(1575) == 0) {
            stamina[0].click();
            Utils.microSleep();
        }
    }

    public void handleDwarves() {
        if (WHOLE_LEVEL_1_MAZE.contains(Player.getPosition())) {
            cQuesterV2.status = "Going to stairs";
            if (!AREA_BEFORE_DOWNWARD_STAIRS.contains(Player.getPosition())) {
                drinkStamina();
                Walking.blindWalkTo(BOTTOM_RIGHT_CORNER.getRandomTile());
                PathingUtil.localNavigation(BOTTOM_RIGHT_CORNER);
            }
            PathingUtil.localNavigation(AREA_BEFORE_DOWNWARD_STAIRS);
            if (AREA_BEFORE_DOWNWARD_STAIRS.contains(Player.getPosition())) {
                cQuesterV2.status = "Going down stairs";
                if (Utils.clickObj("Cave", "Descend")) {
                    Timer.waitCondition(() -> WHOLE_IBANS_LAIR_LOWER_LEVEL.contains(Player.getPosition()), 8000, 12000);
                    Utils.shortSleep();
                }
            }
        }

        if (WHOLE_IBANS_LAIR_LOWER_LEVEL.contains(Player.getPosition())) {
            cQuesterV2.status = "Going to dwarves";
            PathingUtil.localNavigation(DWARF_AREA);
            if (NpcChat.talkToNPC("Niloof")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
        }
    }


    public void getFood() {
        if (Inventory.getAll().length < 20) {
            cQuesterV2.status = "Getting Food";
            PathingUtil.localNavigation(INFRONT_OF_KLANKS_HOUSE);
            if (Utils.clickObj(CLOSED_DOOR, "Open"))
                Timer.waitCondition(() -> Objects.findNearest(10, 1536).length > 0, 8000, 12000);

            if (NpcChat.talkToNPC("Kamen")) { // this guy sells food
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("No thanks.");
                NPCInteraction.handleConversation("Okay then."); // food option for 75gp
            }
        }
    }

    public void talkToKlank() {
        if (WHOLE_IBANS_LAIR_LOWER_LEVEL.contains(Player.getPosition())) {
            if (Inventory.find(KLANKS_GAUNTLETS).length < 1) {

                if (Inventory.isFull())
                    Inventory.drop(2293); // drops the pizza

                cQuesterV2.status = "Talking to Klank";
                PathingUtil.localNavigation(DWARF_AREA);
                if (NpcChat.talkToNPC("Klank")) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation("What happened to them?");
                    NPCInteraction.handleConversation();
                }
            }

        }
    }

    RSArea LANDING_AFTER_JUMP_ONE = new RSArea(new RSTile(2155, 4581, 1), new RSTile(2149, 4584, 1));
    RSArea WHOLE_WITCHS_PLATFORM = new RSArea(new RSTile[]{new RSTile(2149, 4585, 1), new RSTile(2145, 4564, 1), new RSTile(2152, 4551, 1), new RSTile(2171, 4563, 1), new RSTile(2171, 4567, 1), new RSTile(2155, 4585, 1)});
    RSArea WITCHS_WINDOW_AREA = new RSArea(new RSTile(2158, 4565, 1), new RSTile(2159, 4564, 1));
    RSArea LANDING_AREA_2 = new RSArea(new RSTile(2138, 4585, 1), new RSTile(2146, 4582, 1));
    RSArea CAT_PLATFORM = new RSArea(new RSTile(2130, 4603, 1), new RSTile(2134, 4600, 1));

    public void handleFailure() {
        if (Inventory.find(KLANKS_GAUNTLETS).length > 0) {
            Utils.shortSleep();
            if (WHOLE_IBANS_LAIR_LOWER_LEVEL.contains(Player.getPosition())) {
                cQuesterV2.status = "Handling failed jump";
                RSItem[] food = Inventory.find(SUMMER_PIE_ARRAY);

                if (food.length > 0 && Combat.getHPRatio() < General.random(40, 60))
                    if (food[0].click())
                        Utils.microSleep();

                drinkStamina();

                Inventory.drop(PIE_DISH);

                PathingUtil.localNavigation(AREA_BEFORE_UPWARD_STAIRS);

                if (Utils.clickObj("Cave", "Ascend"))
                    Timer.waitCondition(() -> WHOLE_LEVEL_1_MAZE.contains(Player.getPosition()), 8000, 12000);

                PathingUtil.localNavigation(BOTTOM_RIGHT_CORNER);
                PathingUtil.localNavigation(JUMP_ONE_AREA);
                if (JUMP_ONE_AREA.contains(Player.getPosition())) {
                    cQuesterV2.status = "Handling bridge";
                    if (Utils.clickObj("Bridge", "Cross"))
                        Timer.waitCondition(() -> LANDING_AFTER_JUMP_ONE.contains(Player.getPosition()), 7000, 12000);
                }
            }
        }
    }

    public void goToWitchsHouse() {
        if (Inventory.find(KLANKS_GAUNTLETS).length > 0) {
            if (WHOLE_IBANS_LAIR_LOWER_LEVEL.contains(Player.getPosition())) {
                cQuesterV2.status = "Going back upstairs";
                drinkStamina();
                PathingUtil.localNavigation(AREA_BEFORE_UPWARD_STAIRS);

                if (Utils.clickObj("Cave", "Ascend")) {
                    Timer.waitCondition(() -> WHOLE_LEVEL_1_MAZE.contains(Player.getPosition()), 8000, 12000);
                    Utils.idle(3500, 7000);
                }
            }
            if (WHOLE_LEVEL_1_MAZE.contains(Player.getPosition())
                    && !WHOLE_WITCHS_PLATFORM.contains(Player.getPosition()) && !LANDING_AREA_2.contains(Player.getPosition())) {
                cQuesterV2.status = "Going to Witch's house";
                PathingUtil.localNavigation(BOTTOM_RIGHT_CORNER);
                PathingUtil.localNavigation(JUMP_ONE_AREA);
                if (JUMP_ONE_AREA.contains(Player.getPosition())) {
                    cQuesterV2.status = "Handling bridge";
                    if (Utils.clickObj("Bridge", "Cross"))
                        Timer.waitCondition(() -> LANDING_AFTER_JUMP_ONE.contains(Player.getPosition()), 7000, 12000);
                }
            }
            if (WHOLE_WITCHS_PLATFORM.contains(Player.getPosition()) &&!givenWitchCat.check() &&   Inventory.find(CAT_INV_ID).length < 1) {
                cQuesterV2.status = "Going to Witch's house";
                PathingUtil.localNavigation(WITCHS_WINDOW_AREA);
                PathingUtil.movementIdle();
                if (Utils.clickObj("Window", "Search")) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                    Utils.shortSleep();
                }
                PathingUtil.localNavigation(new RSTile(2150, 4583, 1));
                PathingUtil.movementIdle();
                if (Utils.clickObj("Bridge", "Cross"))
                    Timer.waitCondition(() -> LANDING_AREA_2.contains(Player.getPosition()), 8000, 12000);
            }
            if (!givenWitchCat.check() &&  LANDING_AREA_2.contains(Player.getPosition()) && Inventory.find(CAT_INV_ID).length < 1) {
                cQuesterV2.status = "Going to Cat";
                PathingUtil.localNavigation(CAT_PLATFORM);
                PathingUtil.movementIdle();
            }
            if (!givenWitchCat.check() &&  CAT_PLATFORM.contains(Player.getPosition()) && Inventory.find(CAT_INV_ID).length < 1) {
                // need to check inventory space here
                if (Utils.clickNPC("Witch's cat", "Pick-up"))
                    Timer.waitCondition(() -> Inventory.find(CAT_INV_ID).length > 0, 8000, 12000);
            }

            if (!givenWitchCat.check() &&  CAT_PLATFORM.contains(Player.getPosition()) &&
                    Inventory.find(CAT_INV_ID).length > 0) {
                cQuesterV2.status = "Returning to Witch";
                PathingUtil.localNavigation(new RSTile(2146, 4583, 1));
                PathingUtil.movementIdle();
            }

            if (LANDING_AREA_2.contains(Player.getPosition()) && Inventory.find(CAT_INV_ID).length > 0) {
                cQuesterV2.status = "Returning to Witch";
                if (Utils.clickObj("Bridge", "Cross"))
                    Timer.waitCondition(() -> LANDING_AFTER_JUMP_ONE.contains(Player.getPosition()), 8000, 12000);

            }
            if (LANDING_AFTER_JUMP_ONE.contains(Player.getPosition()) && Inventory.find(CAT_INV_ID).length > 0) {
                PathingUtil.localNavigation(WITCHS_WINDOW_AREA);
                PathingUtil.movementIdle();
            }
            if (WITCHS_WINDOW_AREA.contains(Player.getPosition()) && Inventory.find(CAT_INV_ID).length > 0) {
                cQuesterV2.status = "Using cat on door";
                Utils.useItemOnObject(CAT_INV_ID, "Door");
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
        }
    }

    public void searchChest() {
        if (Inventory.find(IBANS_DOLL).length < 1) {
            if (!WITCHS_PLATFORM.contains(Player.getPosition())){
                goToWitchsHouse();
            }
            cQuesterV2.status = "Searching chest";
            if (givenWitchCat.check() &&
                    !INSIDE_WITCHS_HOUSE.contains(Player.getPosition()) &&
                    Utils.clickObj("Door", "Open"))
                Timer.waitCondition(() -> INSIDE_WITCHS_HOUSE.contains(Player.getPosition()), 8000, 12000);

            if (Inventory.getAll().length > 23) {
                cQuesterV2.status = "Making room for items";
                General.println("Need to drop items");
                Inventory.drop(2327, 2309); // meat pies and bread
            }

            cQuesterV2.status = "Searching chest";
            if (Utils.clickObj("Chest", "Open")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
        }
    }

    public void leaveHouse() {
        if (Inventory.find(IBANS_DOLL).length > 0 && INSIDE_WITCHS_HOUSE.contains(Player.getPosition())) {
            cQuesterV2.status = "Leaving house";
            if (Utils.clickObj("Door", "Open"))
                Timer.waitCondition(() -> !INSIDE_WITCHS_HOUSE.contains(Player.getPosition()), 8000, 12000);
        }
    }

    RSArea BEFORE_JUMP_3 = new RSArea(new RSTile(2142, 4565, 1), new RSTile(2142, 4569, 1));
    RSArea LANDING_AREA_3 = new RSArea(new RSTile[]{new RSTile(2143, 4561, 1), new RSTile(2143, 4556, 1), new RSTile(2138, 4556, 1), new RSTile(2138, 4557, 1), new RSTile(2141, 4557, 1), new RSTile(2141, 4562, 1)});
    RSArea LANDING_AREA_4 = new RSArea(new RSTile(2125, 4566, 1), new RSTile(2124, 4566, 1));

    public void killDemons() {
        cQuesterV2.status = "Going to Kill Demons";
        if (AREA_BEFORE_DOWNWARD_STAIRS.contains(Player.getPosition())) {
            PathingUtil.localNavigation(BOTTOM_RIGHT_CORNER);
            PathingUtil.localNavigation(JUMP_ONE_AREA);
            if (JUMP_ONE_AREA.contains(Player.getPosition())) {
                cQuesterV2.status = "Handling bridge";
                if (Utils.clickObj("Bridge", "Cross"))
                    Timer.waitCondition(() -> LANDING_AFTER_JUMP_ONE.contains(Player.getPosition()), 7000, 12000);
            }
        }
        if (WHOLE_WITCHS_PLATFORM.contains(Player.getPosition())) {
            cQuesterV2.status = "Going to Kill Demons: jump 1";
            PathingUtil.localNavigation(new RSTile(2150, 4583, 1));
            Utils.shortSleep();
            if (Utils.clickObj("Bridge", "Cross"))
                Timer.waitCondition(() -> LANDING_AREA_2.contains(Player.getPosition()), 8000, 12000);

        }
        if (LANDING_AREA_2.contains(Player.getPosition())) {
            cQuesterV2.status = "Going to Kill Demons: jump 2";
            PathingUtil.localNavigation(BEFORE_JUMP_3);
            if (Utils.clickObj("Bridge", "Cross"))
                Timer.waitCondition(() -> LANDING_AREA_3.contains(Player.getPosition()), 8000, 12000);

        }
        if (WHOLE_DEMON_AREA.contains(Player.getPosition()) || LANDING_AREA_3.contains(Player.getPosition())) {
            if (Inventory.find(HOLTHION_AMULET).length < 1) {
                cQuesterV2.status = "Going to Kill Demons: Holthion";
                if (PathingUtil.localNavigation(new RSTile(2138, 4556, 1)))
                    Utils.shortSleep();

                killDemon("Holthion", HOLTHION_AMULET);
            }
            if (Inventory.find(HOLTHION_AMULET).length > 0 && Inventory.find(DOOMION_AMULET).length < 1) {
                cQuesterV2.status = "Going to Kill Demons: Doomion";
                if (PathingUtil.localNavigation(new RSTile(2135, 4561, 1)))
                    Utils.shortSleep();

                killDemon("Doomion", DOOMION_AMULET);
            }
            if (Inventory.find(HOLTHION_AMULET).length == 1
                    && Inventory.find(DOOMION_AMULET).length == 1 && Inventory.find(OTHANIAN_AMULET).length == 0) {
                cQuesterV2.status = "Going to Kill Demons: Othainian";
                if (!OTHANIAN_SAFE_SPOT.contains(Player.getPosition())) {
                    if (PathingUtil.localNavigation(new RSTile(2129, 4566, 1)))
                        if (Utils.clickObj("Bridge", "Cross"))
                            Timer.waitCondition(() -> LANDING_AREA_4.contains(Player.getPosition()), 8000, 12000);
                }
                PathingUtil.localNavigation(OTHANIAN_SAFE_SPOT);

                killDemon("Othainian", OTHANIAN_AMULET);
            }
        }
    }

    int OTHANIAN_AMULET = 1497;
    int DOOMION_AMULET = 1498;
    int HOLTHION_AMULET = 1499;
    RSArea WHOLE_DEMON_AREA = new RSArea(new RSTile(2140, 4551, 1), new RSTile(2120, 4568, 1));
    RSArea OTHANIAN_SAFE_SPOT = new RSArea(new RSTile(2122, 4569, 1), new RSTile(2122, 4568, 1));

    public void killDemon(String name, int dropId) {
        if (Inventory.find(dropId).length < 1) {
            RSNPC[] demon = NPCs.findNearest(name);

            if (demon.length > 0) {
                if (!demon[0].isClickable())
                    demon[0].adjustCameraTo();

                if (!demon[0].isInCombat()) {
                    cQuesterV2.status = "Attacking " + name;
                    if (AccurateMouse.click(demon[0], "Attack"))
                        Timer.waitCondition(() -> demon[0].isInCombat(), 5000, 8000);
                }
                if (demon[0].isInCombat()) {
                    cQuesterV2.status = "Combat Idle";
                    Timer.waitCondition(() -> demon.length < 1 || !demon[0].isInCombat() ||
                            GroundItems.find(dropId).length > 0 || Combat.getHPRatio() < 50, 20000, 40000);
                }
            }

            if (Combat.getHPRatio() < 50)
                checkHealth();

            RSGroundItem[] amulet = GroundItems.find(dropId);
            if (amulet.length > 0) {
                cQuesterV2.status = "Looting amulet";

                if (Inventory.isFull()) {
                    Inventory.drop(PIE_DISH, PIECE_OF_RAILING, MEAT_PIE, 2293, 145, 123);
                    // 2293 = pizza; 145 = super attack(2); 123 = attack potion (2)
                }

                if (AccurateMouse.click(amulet[0], "Take"))
                    Timer.waitCondition(() -> Inventory.find(dropId).length > 0, 8000, 12000);
            }
        }
    }

    RSArea CHEST_AREA = new RSArea(new RSTile(2138, 4576, 1), new RSTile(2134, 4579, 1));
    RSArea LANDING_AREA_DOOMION_SIDE = new RSArea(new RSTile(2128, 4566, 1), new RSTile(2131, 4566, 1));
    RSArea OTHANIAN_AREA = new RSArea(new RSTile(2119, 4568, 1), new RSTile(2126, 4558, 1));

    public void goToChest() {
        if (Inventory.find(HOLTHION_AMULET).length == 1
                && Inventory.find(DOOMION_AMULET).length == 1 && Inventory.find(OTHANIAN_AMULET).length == 1) {
            cQuesterV2.status = "Going to Chest";
            checkHealth();
            if (OTHANIAN_AREA.contains(Player.getPosition())) {
                PathingUtil.localNavigation(new RSTile(2125, 4566, 1));
                if (Utils.clickObj("Bridge", "Cross"))
                    Timer.waitCondition(() -> LANDING_AREA_DOOMION_SIDE.contains(Player.getPosition()), 7000, 10000);

            }
            PathingUtil.localNavigation(CHEST_AREA);
            if (CHEST_AREA.contains(Player.getPosition())) {
                if (Utils.clickObj("Chest", "Open")) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                }
            }
        }

    }

    int DWARF_BREW = 1501;
    RSArea BEFORE_DWARF_DOOR = new RSArea(new RSTile(2312, 9803, 0), new RSTile(2314, 9804, 0));
    RSArea INSIDE_DWARF_HOUSE = new RSArea(new RSTile(2314, 9798, 0), new RSTile(2309, 9801, 0));
    RSArea INSIDE_KLANKS_HOUSE = new RSArea(new RSTile(2326, 9798, 0), new RSTile(2324, 9800, 0));
    RSArea TOMB_AREA = new RSArea(new RSTile(2355, 9805, 0), new RSTile(2359, 9800, 0));

    public void goDownStairs() {
        cQuesterV2.status = "Going downstairs";
        if (CHEST_AREA.contains(Player.getPosition())) {
            PathingUtil.localNavigation(LANDING_AREA_3);
            if (Utils.clickObj("Bridge", "Cross"))
                Timer.waitCondition(() -> BEFORE_JUMP_3.contains(Player.getPosition()), 7000, 10000);

        }
        if (BEFORE_JUMP_3.contains(Player.getPosition())) {
            PathingUtil.localNavigation(new RSTile(2146, 4583, 1));
            if (Utils.clickObj("Bridge", "Cross"))
                Timer.waitCondition(() -> LANDING_AFTER_JUMP_ONE.contains(Player.getPosition()), 7000, 10000);

        }
        if (LANDING_AFTER_JUMP_ONE.contains(Player.getPosition())) {
            PathingUtil.localNavigation(new RSTile(2155, 4582, 1));
            if (Utils.clickObj("Bridge", "Cross"))
                Timer.waitCondition(() -> JUMP_ONE_AREA.contains(Player.getPosition()), 7000, 10000);

        }
        if (JUMP_ONE_AREA.contains(Player.getPosition())) {
            PathingUtil.localNavigation(BOTTOM_RIGHT_CORNER);
            PathingUtil.localNavigation(AREA_BEFORE_DOWNWARD_STAIRS);
        }
        if (AREA_BEFORE_DOWNWARD_STAIRS.contains(Player.getPosition())) {
            cQuesterV2.status = "Going down stairs";
            if (Utils.clickObj("Cave", "Descend"))
                Timer.waitCondition(() -> WHOLE_IBANS_LAIR_LOWER_LEVEL.contains(Player.getPosition()), 8000, 12000);
        }
        if (WHOLE_IBANS_LAIR_LOWER_LEVEL.contains(Player.getPosition())) {

            if (Inventory.find(DWARF_BREW).length == 0) {
                cQuesterV2.status = "Getting Dwarf Brew";
                PathingUtil.localNavigation(INFRONT_OF_KLANKS_HOUSE);

                if (Utils.clickObj(CLOSED_DOOR, "Open"))
                    Timer.waitCondition(() -> Objects.findNearest(10, 1536).length > 0, 8000, 12000);

                PathingUtil.localNavigation(INSIDE_KLANKS_HOUSE);

                if (INSIDE_KLANKS_HOUSE.contains(Player.getPosition())) {
                    if (Utils.useItemOnObject(BUCKET, "Brew Barrel"))
                        Timer.waitCondition(() -> Inventory.find(DWARF_BREW).length > 0, 6000, 9000);
                }
            }

            if (Inventory.find(DWARF_BREW).length > 0) {
                cQuesterV2.status = "Lighting Tomb on Fire";
                if (Utils.clickObj(CLOSED_DOOR, "Open"))
                    Timer.waitCondition(() -> Objects.findNearest(10, 1536).length > 0, 8000, 12000);

                PathingUtil.localNavigation(TOMB_AREA);
                if (Utils.useItemOnObject(DWARF_BREW, "Tomb"))
                    Utils.shortSleep();


            }
        }
    }

    public void lightTomb() {
        cQuesterV2.status = "Lighting Tomb on Fire";
        if (Utils.useItemOnObject(TINDERBOX, "Tomb"))
            Utils.modSleep();
    }

    RSArea SPIDER_AREA = new RSArea(new RSTile(2350, 9919, 0), new RSTile(2366, 9906, 0));
    RSArea PRE_SPIDER_AREA = new RSArea(new RSTile(2344, 9880, 0), new RSTile(2347, 9877, 0));
 RSTile SPIDER_SAFE_TILE = new RSTile(2364, 9908,0);
    public void goToSpiders() {
        cQuesterV2.status = "Going to Spiders";

        if (!SPIDER_AREA.contains(Player.getPosition()))
            Walking.blindWalkTo(PRE_SPIDER_AREA.getRandomTile());

        if (Prayer.getPrayerPoints() > 0 && Skills.getActualLevel(Skills.SKILLS.PRAYER) >= 43)
            Prayer.enable(Prayer.PRAYERS.PROTECT_FROM_MELEE);

        checkHealth();

        PathingUtil.localNavigation(SPIDER_AREA);

        if (SPIDER_AREA.contains(Player.getPosition())) {

            RSNPC[] spider = NPCs.findNearest("Kalrag");

            if (spider.length > 0) {
                if (!spider[0].isClickable())
                    spider[0].adjustCameraTo();

                if (!spider[0].isInCombat()) {
                    if (AccurateMouse.click(spider[0], "Attack"))
                        Timer.waitCondition(() -> spider[0].isInCombat(), 5000, 8000);
                } else {
                    Timer.waitCondition(() -> Combat.getHPRatio() < 45 || spider.length < 0
                            || !spider[0].isInCombat() || Game.getSetting(162) == -1281240069, 25000, 40000);
                }
                if (Combat.getHPRatio() < 45) {
                    RSItem[] food = Inventory.find(SUMMER_PIE_ARRAY);

                    if (food.length > 0) {
                        food[0].click();
                        Utils.microSleep();
                    }
                }
            }
        }
    }

    RSArea BEFORE_NW_PASSAGE_ENTRANCE = new RSArea(new RSTile(2305, 9916, 0), new RSTile(2309, 9913, 0));
    RSArea BEFORE_SOULESS_BRIDGE = new RSArea(new RSTile(2120, 4686, 1), new RSTile(2115, 4686, 1));
    RSArea AFTER_SOULESS_BRIDGE = new RSArea(new RSTile(2123, 4686, 1), new RSTile(2127, 4686, 1));
    RSArea SOULESS_AREA = new RSArea(new RSTile(2124, 4684, 1), new RSTile(2148, 4711, 1));
    RSArea BEFORE_SOULESS_CAGE = new RSArea(new RSTile(2133, 4701, 1), new RSTile(2136, 4701, 1));

    public void handleIbansDove() {
        Prayer.disable(Prayer.PRAYERS.PROTECT_FROM_MELEE);

        Utils.equipItem(KLANKS_GAUNTLETS);

        if (WHOLE_IBANS_LAIR_LOWER_LEVEL.contains(Player.getPosition())) {
            cQuesterV2.status = "Going to NW passages area";
            PathingUtil.localNavigation(BEFORE_NW_PASSAGE_ENTRANCE);
            if (Utils.clickObj("Cave", "Ascend"))
                Timer.waitCondition(() -> WHOLE_LEVEL_1_MAZE.contains(Player.getPosition()), 8000, 12000);
        }
        if (WHOLE_LEVEL_1_MAZE.contains(Player.getPosition())) {
            if (!SOULESS_AREA.contains(Player.getPosition())) {
                cQuesterV2.status = "Going to first jump";
                PathingUtil.localNavigation(BEFORE_SOULESS_BRIDGE);
                if (Utils.clickObj("Bridge", "Cross"))
                    Timer.waitCondition(() -> AFTER_SOULESS_BRIDGE.contains(Player.getPosition()), 7000, 10000);
            }
            if (SOULESS_AREA.contains(Player.getPosition())) {
                cQuesterV2.status = "Going to cage";
                PathingUtil.localNavigation(BEFORE_SOULESS_CAGE);
                Utils.clickObj("Cage", "Search");
                Utils.modSleep();
            }
        }
    }

    RSArea BEFORE_JUMP_1_IBAN = new RSArea(new RSTile(2162, 4666, 1), new RSTile(2164, 4667, 1));
    RSArea AFTER_JUMP_1_IBAN = new RSArea(new RSTile(2163, 4657, 1), new RSTile(2159, 4662, 1));
    RSArea AFTER_JUMP_2_IBAN = new RSArea(new RSTile(2163, 4640, 1), new RSTile(2158, 4653, 1));

    public void goToIban() {
        if (SOULESS_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Going to Iban";
            PathingUtil.localNavigation(BEFORE_JUMP_1_IBAN);
            if (Utils.clickObj("Bridge", "Cross")) {
                Timer.waitCondition(() -> AFTER_JUMP_1_IBAN.contains(Player.getPosition()), 7000, 10000);
                Utils.longSleep(); // does a little cut scene here
            }
        }
    }

    int ROBE_TOP = 1035;
    int ROBE_BOTTOM = 1033;
    RSArea INSIDE_IBAN_AREA = new RSArea(
            new RSTile[]{
                    new RSTile(2144, 4649, 1),
                    new RSTile(2144, 4647, 1),
                    new RSTile(2143, 4646, 1),
                    new RSTile(2143, 4644, 1),
                    new RSTile(2140, 4641, 1),
                    new RSTile(2131, 4641, 1),
                    new RSTile(2131, 4654, 1),
                    new RSTile(2140, 4655, 1),
                    new RSTile(2143, 4652, 1),
                    new RSTile(2143, 4650, 1),
                    new RSTile(2144, 4649, 1)
            }
    );

    public boolean attackNPC(String npcName) {
        if (!Combat.isUnderAttack()) {
            RSNPC[] target = NPCs.findNearest(npcName);
            if (target.length > 0) {
                if (!target[0].isInCombat() && !target[0].isInteractingWithMe()) {
                    if (!target[0].isOnScreen()) {
                        target[0].adjustCameraTo();
                    }
                    if (AccurateMouse.click(target[0], "Attack"))
                        return Timer.waitCondition(Combat::isUnderAttack, 8000, 12000);

                }
            }
        }
        return Combat.isUnderAttack();
    }

    public void goToIban2() {
        if (AFTER_JUMP_1_IBAN.contains(Player.getPosition())) {
            cQuesterV2.status = "Going to Iban";
            PathingUtil.localNavigation(new RSTile(2161, 4657, 1));
            PathingUtil.movementIdle();
            Utils.shortSleep();
            if (Utils.clickObj("Bridge", "Cross")) {
                Timer.waitCondition(() -> AFTER_JUMP_2_IBAN.contains(Player.getPosition()), 7000, 10000);
                Utils.shortSleep();
            }
        }
        if (AFTER_JUMP_2_IBAN.contains(Player.getPosition())) {
            if (BankManager.checkInventoryItems(ROBE_BOTTOM, ROBE_TOP) || Equipment.find(ROBE_BOTTOM, ROBE_TOP).length >= 1) {
            } else {
                cQuesterV2.status = "Getting Robes";
                if (attackNPC("Disciple of Iban"))
                    Timer.waitCondition(() -> GroundItems.find(ROBE_TOP).length > 0, 10000, 15000);

                RSGroundItem[] robeTop = GroundItems.find(ROBE_TOP);
                if (robeTop.length > 0) {
                    if (AccurateMouse.click(robeTop[0], "Take"))
                        Timer.waitCondition(() -> Inventory.find(ROBE_TOP).length > 0, 8000, 12000);
                }

                RSGroundItem[] robeBottom = GroundItems.find(ROBE_BOTTOM);
                if (robeBottom.length > 0) {
                    if (AccurateMouse.click(robeBottom[0], "Take"))
                        Timer.waitCondition(() -> Inventory.find(ROBE_BOTTOM).length > 0, 8000, 12000);
                }
            }
        }
        if (BankManager.checkInventoryItems(ROBE_BOTTOM, ROBE_TOP) && Equipment.find(ROBE_BOTTOM, ROBE_TOP).length < 1) {
            cQuesterV2.status = "Equipping Robes";
            if (Utils.equipItem(ROBE_TOP))
                Utils.shortSleep();
            if (Utils.equipItem(ROBE_BOTTOM))
                Utils.shortSleep();
        }

        Inventory.drop(BUCKET, TINDERBOX, 145, 127);
        // need to check for space

        if (Equipment.find(ROBE_BOTTOM, ROBE_TOP).length == 2 && !INSIDE_IBAN_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Unequipping items";
            removeGear();
        }
        if (Equipment.getItems().length <= 4 && Equipment.find(ROBE_BOTTOM, ROBE_TOP).length == 2 && !INSIDE_IBAN_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Going into temple";
            // should check for full health here
            PathingUtil.localNavigation(new RSTile(2146, 4647, 1));
            if (Utils.clickObj("Door", "Open"))
                Timer.waitCondition(() -> INSIDE_IBAN_AREA.contains(Player.getPosition()), 6000, 9000);
        }
    }

    public void useDollOnWell() {
        if (INSIDE_IBAN_AREA.contains(Player.getPosition())) {
            for (int i = 0; i <40; i++) {
                cQuesterV2.status = "Using Doll on Well";
                if (Utils.useItemOnObject(IBANS_DOLL, "Well"))
                    Utils.microSleep();
                Utils.microSleep();
            }
        }
    }

    public void removeGear() {
        Equipment.remove(Equipment.SLOTS.AMULET);
        Utils.microSleep();
        Equipment.remove(Equipment.SLOTS.WEAPON);
        Utils.microSleep();
        Equipment.remove(Equipment.SLOTS.CAPE);
        Utils.microSleep();
        Equipment.remove(Equipment.SLOTS.HELMET);
        Utils.microSleep();
        Equipment.remove(Equipment.SLOTS.SHIELD);
        Utils.microSleep();
        Equipment.remove(Equipment.SLOTS.BOOTS);
        Utils.shortSleep();
        Equipment.remove(Equipment.SLOTS.GLOVES);
        Utils.shortSleep();

    }

    public void talkToKotfikAgain() {
        PathingUtil.localNavigation(new RSTile(2444, 9608, 0));
        if (NpcChat.talkToNPC("Koftik")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            Utils.modSleep();
        }
    }

    public void finishQuest() {
        cQuesterV2.status = "Finishing Quest";
        PathingUtil.walkToArea(START_AREA);
        if (NpcChat.talkToNPC("King Lathas")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }

    }

    public void getFireArrowAgain() {
        if (OIL_RAG_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Getting oil rags";
            if (Utils.clickObj("Abandoned Equipment", "Search"))
                Timer.waitCondition(() -> Interfaces.isInterfaceSubstantiated(162, 44), 6000, 9000);

            if (Interfaces.isInterfaceSubstantiated(162, 44)) {
                Utils.shortSleep();
                Keyboard.typeString("1");
                Keyboard.pressEnter();
                Utils.shortSleep();
            }
            if (Utils.useItemOnItem(IRON_ARROW, OILY_CLOTH))
                Utils.modSleep();

            if (Utils.useItemOnObject(IRON_FIRE_ARROW_UNLIT, "Fire"))
                Utils.modSleep();
        }
    }

    ItemReq unicornHorn = new ItemReq(1487);
    ItemReq orb1 = new ItemReq(ORB_OF_LIGHT_1_INV);
    ItemReq orb2 = new ItemReq(ORB_OF_LIGHT_2_INV);
    ItemReq orb3 = new ItemReq(ORB_OF_LIGHT_3_INV);
    ItemReq orb4 = new ItemReq(ORB_OF_LIGHT_4_INV);
    ItemReq badgeJerro = new ItemReq(JERRO_BADGE);
    ItemReq badgeCarl = new ItemReq(CARL_BADGE);
    ItemReq badgeHarry = new ItemReq(HARRY_BADGE);
    ItemReq klanksGauntlets = new ItemReq(KLANKS_GAUNTLETS);

    private void setupConditions() {
        VarbitRequirement clothInBag = new VarbitRequirement(9138, 1);

       /* AreaRequirement inCastleFloor2 = new AreaRequirement(castleFloor2);
        AreaRequirement inWestArdougne = new AreaRequirement(westArdougne);
        AreaRequirement isBeforeRockslide1 = new AreaRequirement(beforeRockslide1);
        AreaRequirement isBeforeRockslide2 = new AreaRequirement(beforeRockslide2);
        AreaRequirement isBeforeRockslide3 = new AreaRequirement(beforeRockslide3);
        AreaRequirement isInFallArea = new AreaRequirement(inFallArea);
        AreaRequirement isBeforeBridge = new AreaRequirement(beforeBridge);
        AreaRequirement isNorthEastOfBridge = new AreaRequirement(northEastOfBridge);
        AreaRequirement isBeforeThePit = new AreaRequirement(westOfBridge, beforeThePit);
        AreaRequirement isAfterThePit = new AreaRequirement(afterThePit);
        AreaRequirement isBeforeTheGrid = new AreaRequirement(beforeTheGrid);
        AreaRequirement isAtTheGrid = new AreaRequirement(atTheGrid);
        AreaRequirement isAfterTheGrid = new AreaRequirement(afterTheGrid);
        AreaRequirement isBeforeTrap1 = new AreaRequirement(beforeTrap1);
        AreaRequirement isBeforeTrap2 = new AreaRequirement(beforeTrap2);
        AreaRequirement isBeforeTrap3 = new AreaRequirement(beforeTrap3);
        AreaRequirement isBeforeTrap4 = new AreaRequirement(beforeTrap4);
        AreaRequirement isBeforeTrap5 = new AreaRequirement(beforeTrap5);*/
        VarbitRequirement usedOrb1 = new VarbitRequirement(9122, 1);
        VarbitRequirement usedOrb2 = new VarbitRequirement(9121, 1);
        VarbitRequirement usedOrb3 = new VarbitRequirement(9120, 1);
        VarbitRequirement usedOrb4 = new VarbitRequirement(9119, 1);
        // Conditions destroyedAllOrbs = new Conditions(usedOrb1, usedOrb2, usedOrb3, usedOrb4);

        Conditions haveOrb1 = new Conditions(LogicType.OR, usedOrb1, orb1);
        Conditions haveOrb2 = new Conditions(LogicType.OR, usedOrb2, orb2);
        Conditions haveOrb3 = new Conditions(LogicType.OR, usedOrb3, orb3);
        Conditions haveOrb4 = new Conditions(LogicType.OR, usedOrb4, orb4);
      /*  AreaRequirement isInWellArea = new AreaRequirement(wellArea);
        AreaRequirement isInUndergroundSection2 = new AreaRequirement(inUndergroundSection2P1, inUndergroundSection2P2, inUndergroundSection2P3, inUndergroundSection2P4);
        AreaRequirement isInUndergroundSection3 = new AreaRequirement(inUndergroundSection3);
        AreaRequirement isBeforePlank2 = new AreaRequirement(beforePlank2);
        AreaRequirement isBeforePlank3 = new AreaRequirement(beforePlank3);
        AreaRequirement isAtOrb1 = new AreaRequirement(atOrb1);
        AreaRequirement isInsideCell = new AreaRequirement(insideCell);
        AreaRequirement isBeforeLedge = new AreaRequirement(beforeLedge);
        AreaRequirement isInMaze = new AreaRequirement(inMaze1, inMaze2);
        AreaRequirement isAfterMaze = new AreaRequirement(afterMaze, afterMazeShortcut);
        AreaRequirement isInUnicornArea = new AreaRequirement(inUnicornArea);
        AreaRequirement isInUnicornArea2 = new AreaRequirement(inUnicornArea2);*/
        VarbitRequirement usedHorn = new VarbitRequirement(9136, 1);
        Conditions haveUnicornHorn = new Conditions(LogicType.OR, unicornHorn, usedHorn);
        //  AreaRequirement isInKnightsArea = new AreaRequirement(inKnightsArea1, inKnightsArea2, inKnightsArea3);

        VarbitRequirement usedBadgeJerro = new VarbitRequirement(9128, 1);
        VarbitRequirement usedBadgeCarl = new VarbitRequirement(9129, 1);
        VarbitRequirement usedBadgeHarry = new VarbitRequirement(9130, 1);

        Conditions haveBadgeCarl = new Conditions(LogicType.OR, badgeCarl, usedBadgeCarl);
        Conditions haveBadgeHarry = new Conditions(LogicType.OR, badgeHarry, usedBadgeHarry);
        Conditions haveBadgeJerro = new Conditions(LogicType.OR, badgeJerro, usedBadgeJerro);
        //AreaRequirement isBeforeIbansDoor = new AreaRequirement(inKnightsArea3, beforeIbansDoor);
        //AreaRequirement isInFinalArea = new AreaRequirement(inFinalArea);
       // AreaRequirement isInDwarfCavern = new AreaRequirement(inDwarfCavern);
        //   haveKlanksGauntlets = klanksGauntlets;

        VarbitRequirement dollImbued = new VarbitRequirement(9118, 1);
        VarbitRequirement pouredBrew = new VarbitRequirement(9134, 1);
        VarbitRequirement dollAshed = new VarbitRequirement(9117, 1);
        VarbitRequirement kalragKilled = new VarbitRequirement(9115, 1);
        VarbitRequirement doveSmeared = new VarbitRequirement(9116, 1);
        //AreaRequirement isInTemple = new AreaRequirement(inTemple);
       // AreaRequirement isInPostIbanArea = new AreaRequirement(inPostIbanArea);
    }


    @Override
    public void execute() {


        if (Game.getSetting(162) == 0) {
            buyItems();
            getItems();
            startQuest();

        } else if (Game.getSetting(162) == -134217728 || (
                RSVarBit.get(9139).getValue() == 1 && RSVarBit.get(9140).getValue() == 1 &&
                        RSVarBit.get(9141).getValue() == 1 && RSVarBit.get(9142).getValue() == 1
                        && RSVarBit.get(9143).getValue() == 1)) {
            talkToKoftik();

        } else if (Game.getSetting(162) == -268435456 || (RSVarBit.get(9114).getValue() == 0 &&
                RSVarBit.get(9138).getValue() == 0 && RSVarBit.get(9139).getValue() == 0 &&
                RSVarBit.get(9140).getValue() == 1 && RSVarBit.get(9141).getValue() == 1 &&
                RSVarBit.get(9142).getValue() == 1 && RSVarBit.get(9143).getValue() == 1)) {

            talkToKoftikInside();

        } else if (Game.getSetting(162) == -234881023 || (RSVarBit.get(9114).getValue() == 1 &&
                RSVarBit.get(9138).getValue() == 1 && RSVarBit.get(9139).getValue() == 0 &&
                RSVarBit.get(9140).getValue() == 1 && RSVarBit.get(9141).getValue() == 1 &&
                RSVarBit.get(9142).getValue() == 1 && RSVarBit.get(9143).getValue() == 1)) {

            crossbridge();

        } else if (Game.getSetting(162) == -503316479 || (RSVarBit.get(9114).getValue() == 1 &&
                RSVarBit.get(9138).getValue() == 1 && RSVarBit.get(9139).getValue() == 0 &&
                RSVarBit.get(9140).getValue() == 0 && RSVarBit.get(9141).getValue() == 1 &&
                RSVarBit.get(9142).getValue() == 1 && RSVarBit.get(9143).getValue() == 1)) {

            talkToKoftik();
            getFireArrowAgain();
            talkToKoftikInside();
            crossbridge();
            getPlank();
            handleObstacle5();
            handleFailedObstacle5();
            goToObstacle6();
            // handleGridObstacle();
            getOrbOfLight1();
            getOrbOfLight2();
            getOrbOfLight3();
            getOrbOfLight4();
            destroyOrb1();

        } else if (Game.getSetting(162) == -503316223) {

            destroyOrb2();

        } else if (Game.getSetting(162) == -503316095) {

            destroyOrb3();

        } else if (Game.getSetting(162) == -503316031) {

            destroyOrb4();

        }
        if (Game.getSetting(162) == -503315999 || (RSVarBit.get(9119).getValue() == 1 &&
                RSVarBit.get(9120).getValue() == 1 && RSVarBit.get(9121).getValue() == 1 &&
                RSVarBit.get(9122).getValue() == 1 && RSVarBit.get(9138).getValue() == 1 &&
                RSVarBit.get(9139).getValue() == 0 && RSVarBit.get(9140).getValue() == 0 &&
                RSVarBit.get(9141).getValue() == 1 && RSVarBit.get(9142).getValue() == 1
                && RSVarBit.get(9143).getValue() == 1)) {

            goDownWell();


        } else if (Game.getSetting(162) == -771751455 || Game.getSetting(162) == -771747359) {
            goToCells();
            goToUnicorn();

        } else if (Game.getSetting(162) == -1308618271 || Game.getSetting(162) == -1308622367 ||
                Game.getSetting(162) == -1308614175 || (RSVarBit.get(9119).getValue() == 1 &&
                RSVarBit.get(9120).getValue() == 1 && RSVarBit.get(9121).getValue() == 1 &&
                RSVarBit.get(9122).getValue() == 1 && RSVarBit.get(9138).getValue() == 1 &&
                RSVarBit.get(9128).getValue() == 0 && RSVarBit.get(9129).getValue() == 0 &&
                RSVarBit.get(9130).getValue() == 0 && RSVarBit.get(9136).getValue() == 0 &&
                RSVarBit.get(9139).getValue() == 0 && RSVarBit.get(9140).getValue() == 1 &&
                RSVarBit.get(9141).getValue() == 1 && RSVarBit.get(9142).getValue() == 0
                && RSVarBit.get(9143).getValue() == 1)) {
            getHorn();
            handlePalidins();
            goToWell();

        } else if (Game.getSetting(162) == -1300229663 || Game.getSetting(162) == -1300213279
                || Game.getSetting(162) == -1300147743) {
            goToWell(); // these are the settings once you've started adding items to the well

        } else if (Game.getSetting(162) == -1300114975
                || (RSVarBit.get(9119).getValue() == 1 &&
                RSVarBit.get(9120).getValue() == 1 && RSVarBit.get(9121).getValue() == 1 &&
                RSVarBit.get(9122).getValue() == 1 && RSVarBit.get(9138).getValue() == 1 &&
                RSVarBit.get(9128).getValue() == 1 && RSVarBit.get(9129).getValue() == 1 &&
                RSVarBit.get(9130).getValue() == 1 && RSVarBit.get(9136).getValue() == 1 &&
                RSVarBit.get(9137).getValue() == 0 &&
                RSVarBit.get(9139).getValue() == 0 && RSVarBit.get(9140).getValue() == 1 &&
                RSVarBit.get(9141).getValue() == 1 && RSVarBit.get(9142).getValue() == 0
                && RSVarBit.get(9143).getValue() == 1 && Game.getSetting(161) == 6)) {
            leaveWellArea();
            handleDwarves(); // Setting 161 changes from 6-> 7
            talkToKlank();

        } else if (Game.getSetting(162) == -1283337759 || Game.getSetting(162) == -1279143455
                || (RSVarBit.get(9119).getValue() == 1 &&
                RSVarBit.get(9120).getValue() == 1 && RSVarBit.get(9121).getValue() == 1 &&
                RSVarBit.get(9122).getValue() == 1 && RSVarBit.get(9123).getValue() == 0 &&
                RSVarBit.get(9138).getValue() == 1 &&
                RSVarBit.get(9128).getValue() == 1 && RSVarBit.get(9129).getValue() == 1 &&
                RSVarBit.get(9130).getValue() == 1 && RSVarBit.get(9136).getValue() == 1 &&
                RSVarBit.get(9137).getValue() == 1 &&
                RSVarBit.get(9139).getValue() == 0 && RSVarBit.get(9140).getValue() == 1 &&
                RSVarBit.get(9141).getValue() == 1 && RSVarBit.get(9142).getValue() == 0 &&
                RSVarBit.get(9143).getValue() == 1 && Game.getSetting(161) == 7)) {

            talkToKlank();
            goToWitchsHouse();
            handleFailure();

        } else if ((Game.getSetting(162) == -1283337247 && Game.getSetting(161) == 7) || (
                RSVarBit.get(9118).getValue() == 0 && RSVarBit.get(9119).getValue() == 1 &&
                        RSVarBit.get(9120).getValue() == 1 && RSVarBit.get(9121).getValue() == 1 &&
                        RSVarBit.get(9122).getValue() == 1 && RSVarBit.get(9123).getValue() == 1 &&
                        RSVarBit.get(9138).getValue() == 1 &&
                        RSVarBit.get(9128).getValue() == 1 && RSVarBit.get(9129).getValue() == 1 &&
                        RSVarBit.get(9130).getValue() == 1 && RSVarBit.get(9136).getValue() == 1 &&
                        RSVarBit.get(9137).getValue() == 1 &&
                        RSVarBit.get(9139).getValue() == 0 && RSVarBit.get(9140).getValue() == 1 &&
                        RSVarBit.get(9141).getValue() == 1 && RSVarBit.get(9142).getValue() == 0
                        && RSVarBit.get(9143).getValue() == 1)) {
            searchChest(); // 161 changes 7 -> 8
            leaveHouse();

        }
        if (Game.getSetting(162) == -1283337247 || Game.getSetting(162) == -1279142943
                || (Game.getSetting(161) == 8 && RSVarBit.get(9118).getValue() == 0)) {
            leaveHouse();
            killDemons();
            handleFailure();
            goToChest(); // after this Varbit 9118 changes from 0-> 1

        }
        if ((Game.getSetting(162) == -1283337231 || Game.getSetting(162) == -1281240079 ||
                Game.getSetting(162) == -1279142927) ||
                (Game.getSetting(161) == 8 && RSVarBit.get(9118).getValue() == 1
                        && RSVarBit.get(9134).getValue() == 0)) {
            goDownStairs();
            lightTomb(); // Varbit 9134 changes from 0 -> 1 after using dwarf brew

        }
        if (Game.getSetting(162) == -1281240079 ||
                (Game.getSetting(161) == 8 && RSVarBit.get(9134).getValue() == 1 &&
                        RSVarBit.get(9117).getValue() == 0)) {
            lightTomb();// Varbit 9117 changes from 0 -> 1 after using tinderbox

        }
        if (Game.getSetting(162) == -1281240071
                || Game.getSetting(162) == -1277045767 ||
                (Game.getSetting(161) == 8 && RSVarBit.get(9115).getValue() == 0 &&
                        RSVarBit.get(9117).getValue() == 1)) {
            goToSpiders(); // after this varbit 9115 changes from 0->1

        } else if (Game.getSetting(162) == -1281240069
                || Game.getSetting(162) == -1277045765 ||
                (Game.getSetting(161) == 8 && RSVarBit.get(9115).getValue() == 1 &&
                        RSVarBit.get(9116).getValue() == 0)) {
            handleIbansDove(); // after this varbit 9116 changes from 0->1

        } else if (Game.getSetting(162) == -1281240065
                || Game.getSetting(162) == -1277045761 ||
                (Game.getSetting(161) == 8 && RSVarBit.get(9116).getValue() == 1 &&
                        RSVarBit.get(9124).getValue() == 0)) {
            goToIban(); // varbit 9124 changes from 0->1

        } else if (Game.getSetting(162) == -1281239041
                || Game.getSetting(162) == -1277044737 ||
                (Game.getSetting(161) == 8 && RSVarBit.get(9124).getValue() == 1 &&
                        RSVarBit.get(9142).getValue() == 0)) {
            goToIban2();

        } else if (Game.getSetting(162) == -1281239041
                || Game.getSetting(162) == -1277044737 ||
                (Game.getSetting(161) == 9 && RSVarBit.get(9124).getValue() == 1 &&
                        RSVarBit.get(9142).getValue() == 0)) {
            useDollOnWell();  // varbit 9142: 0->1 after this

        } else if (Game.getSetting(162) == 1906431999 ||
                Game.getSetting(162) == 1910626303 ||
                (Game.getSetting(161) == 10 && RSVarBit.get(9142).getValue() == 1 &&
                        RSVarBit.get(9143).getValue() == 0)) {
            talkToKotfikAgain();//9143: 0-> 1 after this

        } else if (Game.getSetting(162) == -241051649
                || Game.getSetting(162) == -236857345 ||
                (Game.getSetting(161) == 10 && RSVarBit.get(9143).getValue() == 1)) {
            finishQuest();

        } else if (Game.getSetting(161) == 11) {
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
        return "Underground Pass";
    }

    @Override
    public boolean checkRequirements() {
        if (Game.getSetting(68) < 16) { //biohazard
            General.println("[Debug]: Need to do biohazard");
            return false;
        }
        if (Skills.getActualLevel(Skills.SKILLS.RANGED) < 25) {
            General.println("[Debug]: Need atleast 25 Ranged");
            return false;

        } else if (Skills.getActualLevel(Skills.SKILLS.RANGED) < 40
                && Skills.getActualLevel(Skills.SKILLS.MAGIC) < 75) {
            General.println("[Debug]: Need 40 Ranged OR 75 magic");
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
        return Quest.UNDERGROUND_PASS.getState().equals(Quest.State.COMPLETE);
    }
}
