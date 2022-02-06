package scripts.QuestPackages.HauntedMine;

import dax.walker_engine.WalkerEngine;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.DynamicClicking;
import org.tribot.api2007.Game;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSInterface;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.Inventory;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Quest;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.types.LocalTile;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestSteps.*;
import scripts.Requirements.*;
import scripts.Requirements.Util.ConditionalStep;
import scripts.Requirements.Util.Conditions;
import scripts.Requirements.Util.LogicType;
import scripts.Requirements.Util.Operation;
import scripts.Tasks.Priority;
import scripts.Timer;

import java.util.*;

public class HauntedMine implements QuestTask {
    private static HauntedMine quest;

    public static HauntedMine get() {
        return quest == null ? quest = new HauntedMine() : quest;
    }

    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.CHISEL, 1, 500),
                    new GEItem(ItemID.STAMINA_POTION[0], 4, 15),
                    new GEItem(ItemID.AMULET_OF_GLORY[2], 1, 15),
                    new GEItem(ItemID.RING_OF_WEALTH[0], 1, 25)
            )
    );

    InventoryRequirement initialItemReqs = new InventoryRequirement(new ArrayList<>(
            Arrays.asList(
                    new ItemReq(ItemID.STAMINA_POTION[0], 4, 1),
                    new ItemReq(ItemID.AMULET_OF_GLORY[2], 2, 0, true, true),
                    new ItemReq(ItemID.CHISEL, 1, 1),
                    new ItemReq(ItemID.RANGING_POTION[0], 1, 0),
                    new ItemReq(ItemID.RING_OF_WEALTH[0], 1, 0, true)
            )
    ));

    BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);


    ItemReq zealotsKey = new ItemReq("Zealot's key", ItemID.ZEALOTS_KEY);

    ItemReq zealotsKeyHighlighted = new ItemReq("Zealot's key", ItemID.ZEALOTS_KEY);


    ItemReq chisel = new ItemReq("Chisel", ItemID.CHISEL);
    ItemReq glowingFungus = new ItemReq("Glowing fungus", ItemID.GLOWING_FUNGUS);
    ItemReq glowingFungusHighlight = new ItemReq("Glowing fungus", ItemID.GLOWING_FUNGUS);

    ItemReq crystalMineKey = new ItemReq("Crystal-mine key", ItemID.CRYSTALMINE_KEY);

    ItemReq combatGear = new ItemReq("Combat gear", -1, -1);

    ItemReq food = new ItemReq(ItemID.SHARK, 10, 1);


    RSArea entryRoom1 = new RSArea(new RSTile(2647, 9803, 0), new RSTile(2680, 9814, 0));

    RSArea level1North = new RSArea(new RSTile(3404, 9628, 0), new RSTile(3439, 9662, 0));
    RSArea level1South = new RSArea(new RSTile(3409, 9616, 0), new RSTile(3431, 9627, 0));

    RSArea level2South = new RSArea(new RSTile(2780, 4558, 0), new RSTile(2815, 4576, 0));
    RSArea level2North = new RSArea(new RSTile(2765, 4577, 0), new RSTile(2814, 4605, 0));
    RSArea level2North2 = new RSArea(new RSTile(2770, 4575, 0), new RSTile(2775, 4576, 0));

    RSArea level3North1 = new RSArea(new RSTile(2709, 4518, 0), new RSTile(2744, 4543, 0));
    RSArea level3North2 = new RSArea(new RSTile(2693, 4496, 0), new RSTile(2724, 4517, 0));
    RSArea level3North3 = new RSArea(new RSTile(2694, 4493, 0), new RSTile(2698, 4495, 0));
    RSArea level3North4 = new RSArea(new RSTile(2721, 4492, 0), new RSTile(2724, 4496, 0));

    RSArea level3South1 = new RSArea(new RSTile(2725, 4485, 0), new RSTile(2741, 4517, 0));
    RSArea level3South2 = new RSArea(new RSTile(2718, 4484, 0), new RSTile(2729, 4490, 0));
    RSArea level3South3 = new RSArea(new RSTile(2710, 4491, 0), new RSTile(2718, 4495, 0));

    RSArea liftRoom1 = new RSArea(new RSTile(2798, 4489, 0), new RSTile(2812, 4532, 0));
    RSArea liftRoom2 = new RSArea(new RSTile(2794, 4524, 0), new RSTile(2797, 4532, 0));
    RSArea cartRoom = new RSArea(new RSTile(2757, 4483, 0), new RSTile(2795, 4545, 0));

    RSArea collectRoom = new RSArea(new RSTile(2772, 4535, 0), new RSTile(2776, 4542, 0));

    RSArea floodedRoom = new RSArea(new RSTile(2688, 4432, 0), new RSTile(2753, 4472, 0));

    RSArea daythRoom1 = new RSArea(new RSTile(2779, 4441, 0), new RSTile(2815, 4474, 0));
    RSArea daythRoom2 = new RSArea(new RSTile(2774, 4457, 0), new RSTile(2779, 4465, 0));
    RSArea daythRoomDark = new RSArea(new RSTile(2716, 4559, 0), new RSTile(2734, 4569, 0));

    RSArea crystalRoom1 = new RSArea(new RSTile(2762, 4418, 0), new RSTile(2780, 4449, 0));
    RSArea crystalRoom2 = new RSArea(new RSTile(2762, 4421, 0), new RSTile(2810, 4439, 0));
    RSArea crystalRoom3 = new RSArea(new RSTile(2797, 4440, 0), new RSTile(2808, 4450, 0));

    RSArea crystalEntrance = new RSArea(new RSTile(2758, 4450, 0), new RSTile(2776, 4456, 0));
    RSArea crystalEntranceDark = new RSArea(new RSTile(2708, 4588, 0), new RSTile(2736, 5499, 0));


    VarbitRequirement askedAboutKey = new VarbitRequirement(2397, 1);
    AreaRequirement inLevel1North = new AreaRequirement(level1North);
    AreaRequirement inLevel1South = new AreaRequirement(level1South);
    AreaRequirement inLevel2South = new AreaRequirement(level2South);
    AreaRequirement inLevel2North = new AreaRequirement(level2North, level2North2);

    AreaRequirement inLevel3South = new AreaRequirement(level3South1, level3South2, level3South3);
    AreaRequirement inLevel3North = new AreaRequirement(level3North1, level3North2, level3North3, level3North4);
    AreaRequirement inLiftRoom = new AreaRequirement(liftRoom1, liftRoom2);
    AreaRequirement inCartRoom = new AreaRequirement(cartRoom);
    AreaRequirement inCollectRoom = new AreaRequirement(collectRoom);
    AreaRequirement inFloodedRoom = new AreaRequirement(floodedRoom);
    AreaRequirement inDaythRoom = new AreaRequirement(daythRoom1, daythRoom2);
    AreaRequirement inCrystalRoom = new AreaRequirement(crystalRoom1, crystalRoom2, crystalRoom3);
    AreaRequirement inCrystalEntrance = new AreaRequirement(crystalEntrance);
    AreaRequirement inCrystalOrCrystalEntranceRoom = new AreaRequirement(crystalRoom1, crystalRoom2, crystalRoom3, crystalEntrance);

    VarbitRequirement valveOpened = new VarbitRequirement(2393, 1);
    VarbitRequirement valveOpen = new VarbitRequirement(2394, 1);

    Conditions hasKeyOrOpenedValve = new Conditions(LogicType.OR, zealotsKey, valveOpened);

    VarbitRequirement leverAWrong = new VarbitRequirement(2385, 0);
    VarbitRequirement leverBWrong = new VarbitRequirement(2386, 0);
    VarbitRequirement leverCWrong = new VarbitRequirement(2387, 1);
    VarbitRequirement leverDWrong = new VarbitRequirement(2388, 1);
    VarbitRequirement leverEWrong = new VarbitRequirement(2389, 0);
    VarbitRequirement leverFWrong = new VarbitRequirement(2390, 0);
    VarbitRequirement leverGWrong = new VarbitRequirement(2391, 1);
    VarbitRequirement leverHWrong = new VarbitRequirement(2392, 1);

    VarbitRequirement fungusInCart = new VarbitRequirement(2395, 1);
    VarbitRequirement fungusOnOtherSide = new VarbitRequirement(2396, 1);

    // NpcHintArrowRequirement  daythNearby = new NpcHintArrowRequirement(NpcID.TREUS_DAYTH, NpcID.GHOST_3617);

    VarplayerRequirement killedDayth = new VarplayerRequirement(382, 9, Operation.GREATER_EQUAL);

    AreaRequirement inDarkCrystalRoom = new AreaRequirement(crystalEntranceDark);
    AreaRequirement inDarkDaythRoom = new AreaRequirement(daythRoomDark);


    NPCStep talkToZealot = new NPCStep(NpcID.ZEALOT, new RSTile(3443, 3258, 0),
            new String[]{
                    "I follow the path of Saradomin.",
                    "I come seeking challenges and quests.",
                    "Yes.",
                    "What quest is that then?",
                    "Why was everyone in the mines slaughtered?",
                    "Is there any other way into the mines?",
                    "Where is the second entrance to the mines?",
                    "Can I borrow your key?",
                    "I didn't want that key anyway."

            });
    NPCStep pickpocketZealot = new NPCStep(NpcID.ZEALOT, new RSTile(3443, 3258, 0),
            "Pickpocket the Zealot outside the Abandoned Mine in south west Morytania.");

    ObjectStep enterMine = new ObjectStep(ObjectID.CART_TUNNEL_4915, new RSTile(3426, 3225, 0),
            "Crawl-down");

    RSTile mineEntranceTile = new RSTile(3443, 3258, 0);
    RSArea START_AREA = new RSArea(
            new RSTile[]{
                    new RSTile(3435, 3266, 0),
                    new RSTile(3436, 3257, 0),
                    new RSTile(3445, 3244, 0),
                    new RSTile(3456, 3243, 0),
                    new RSTile(3455, 3267, 0)
            }
    );
    AreaRequirement inStartArea = new AreaRequirement(START_AREA);


    public void enterMine() {
        if (Utils.getVarBitValue(5983) == 1 && hasKeyOrOpenedValve.check()) {
            if (inStartArea.check()) {
                Log.log("[Debug]: Walking to mine entrance");
                WalkerEngine.getInstance().walkPath(Arrays.asList(HauntedMineConst.pathToMineEntranceFromStart));
            }
            Log.log("[Debug]: Entering mine");
            enterMine.execute();
        }
    }

    ObjectStep goDownFromLevel1South = new ObjectStep(4965, new RSTile(3422, 9624, 0),
            "Climb-down");

    ObjectStep goDownFromLevel2South = new ObjectStep(4969, new RSTile(2798, 4564, 0),
            "Climb-down");

    ObjectStep goDownFromLevel2North = new ObjectStep(4969, new RSTile(2797, 4598, 0),
            "Climb-down");

    ObjectStep goDownFromLevel3NorthEast = new ObjectStep(4967, new RSTile(2732, 4528, 0),
            "Climb-down");

    ObjectStep goDownToFungusRoom = new ObjectStep(967, new RSTile(2725, 4487, 0), "Go down the ladder to the south, making sure to avoid the moving mine cart.");

    ObjectStep readPanel = new ObjectStep(ObjectID.POINTS_SETTINGS, new RSTile(2770, 4521, 0),
            "Check");

    public void handleStartPanel() {
        int interfaceId = 144;
        if (!Interfaces.isInterfaceSubstantiated(144)) {
            readPanel.execute();
            Timer.waitCondition(() -> Interfaces.isInterfaceSubstantiated(144), 3500, 5500);
        }
        RSInterface startButton = Interfaces.findWhereAction("Start", interfaceId);
        if (startButton != null && startButton.click()) {
            Waiting.waitNormal(6000, 200);
            Utils.cutScene();
        }
    }

    ObjectStep pullLeverA = new ObjectStep(4950, new RSTile(2785, 4516, 0),
            "Pull");
    ObjectStep pullLeverB = new ObjectStep(4951, new RSTile(2785, 4516, 0),
            "Pull", !leverBWrong.check());
    ObjectStep pullLeverC = new ObjectStep(4952, new RSTile(2785, 4516, 0),
            "Pull", !leverCWrong.check());
    ObjectStep pullLeverD = new ObjectStep(4953, new RSTile(2785, 4516, 0),
            "Pull", !leverDWrong.check());
    ObjectStep pullLeverE = new ObjectStep(4954, new RSTile(2785, 4516, 0),
            "Pull", !leverEWrong.check());

    ObjectStep pullLeverF = new ObjectStep(4955, new RSTile(2785, 4532, 0),
            "Pull",!leverFWrong.check());
    ObjectStep pullLeverG = new ObjectStep(4956, new RSTile(2769, 4532, 0),
            "Pull", !leverGWrong.check());
    ObjectStep pullLeverH = new ObjectStep(4957, new RSTile(2770, 4532, 0),
            "Pull", !leverHWrong.check());


    RSTile tileBeforeValve = new RSTile(2807, 4496, 0);
    UseItemOnObjectStep useKeyOnValve = new UseItemOnObjectStep(zealotsKeyHighlighted.getId(),
            ObjectID.WATER_VALVE, tileBeforeValve, valveOpened.check() || valveOpen.check(),
            zealotsKeyHighlighted);

    ObjectStep openValve = new ObjectStep(ObjectID.WATER_VALVE, new RSTile(2807, 4496, 0),
            "Turn",
            Player.getAnimation() != -1);

    // useKeyOnValve.addSubSteps(openValve);

    ObjectStep goDownLift = new ObjectStep(ObjectID.LIFT_4938, new RSTile(2805, 4493, 0),
            "Go-down");

    ObjectStep goDownToCollectFungus = new ObjectStep(4967, new RSTile(2710, 4539, 0),
            "Climb-down", Player.getAnimation() != -1 && Player.getAnimation() == -1);
    ObjectStep collectFungus = new ObjectStep(ObjectID.MINE_CART_4974, new RSTile(2775, 4537, 0),
            "Search", NPCInteraction.isConversationWindowUp());

    ObjectStep goUpFromCollectRoom = new ObjectStep(4968, new RSTile(2774, 4539, 0),
            "Climb-up", inLevel3North.check());

    GroundItemStep pickUpChisel = new GroundItemStep(chisel.getId(), new RSTile(2801, 4501, 0));

    ObjectStep pickFungus = new ObjectStep(ObjectID.GLOWING_FUNGUS_4933, new RSTile(2794, 4493, 0),
            "Pick", glowingFungus.check());

    ObjectStep goUpFromLiftRoom = new ObjectStep(4968, new RSTile(2797, 4530, 0),
            "Climb-up", Player.getAnimation() != -1 && Player.getAnimation() == -1);

    UseItemOnObjectStep putFungusInCart = new UseItemOnObjectStep(glowingFungusHighlight.getId(),
            ObjectID.MINE_CART_4974, new RSTile(2779, 4506, 0),
            "Put the glowing fungus into the mine cart north west of the ladder.", glowingFungusHighlight);


    ObjectStep goUpFromFungusRoom = new ObjectStep(4968, new RSTile(2789, 4487, 0),
            "Climb-up", Player.getAnimation() != -1 && Player.getAnimation() == -1);
    ObjectStep goUpFromLevel3South = new ObjectStep(4970, new RSTile(2734, 4502, 0),
            "Climb-up", Player.getAnimation() != -1 && Player.getAnimation() == -1);

    ObjectStep goUpFromLevel2South = new ObjectStep(4966, new RSTile(2783, 4569, 0),
            "Climb-up", Player.getAnimation() != -1 && Player.getAnimation() == -1);

    ObjectStep leaveLevel1South = new ObjectStep(ObjectID.CART_TUNNEL_15830, new RSTile(3410, 9623, 0),
            "Crawl-through", Player.getAnimation() != -1 && Player.getAnimation() == -1);

    // goUpFromFungusRoom.addSubSteps(goUpFromLevel3South, goUpFromLevel2South, leaveLevel1South);

    ObjectStep enterMineNorth = new ObjectStep(ObjectID.CART_TUNNEL_4914, new RSTile(3429, 3233, 0),
            "Crawl-down", Player.getAnimation() != -1 && Player.getAnimation() == -1);

    ObjectStep goDownLevel1North = new ObjectStep(4965, new RSTile(3412, 9633, 0),
            "Climb-down", new Conditions(fungusOnOtherSide, inLevel2North, hasKeyOrOpenedValve).check());
    ObjectStep goDownLevel2North = new ObjectStep(4969, new RSTile(2798, 4599, 0),
            "Climb-down", inLevel3North.check());

    ObjectStep goDownToDayth = new ObjectStep(4971, new RSTile(2750, 4437, 0),
            "Walk-down", Player.getAnimation() != -1);

    ObjectStep goDownToCrystals = new ObjectStep(4971, new RSTile(2695, 4437, 0),
            "Walk-down", Player.getAnimation() != -1);

    NPCStep tryToPickUpKey = new NPCStep(NpcID.INNOCENTLOOKING_KEY, new RSTile(2788, 4456, 0), "Attempt to pick up the innocent-looking key. Treus Dayth (level 95) will spawn. Kill him.");

    //   NPCStep   killDayth = new NPCStep(NpcID.TREUS_DAYTH, new RSTile(2788, 4450, 0), "Kill Treus Dayth.");

    //   tryToPickUpKey.addSubSteps(killDayth);

    NPCStep pickUpKey = new NPCStep(NpcID.INNOCENTLOOKING_KEY, new RSTile(2788, 4455, 0), "Pick up the innocent-looking key.");

    ObjectStep goUpFromDayth = new ObjectStep(4973, new RSTile(2813, 4454, 0),
            "Walk-up");

    ObjectStep cutCrystal = new ObjectStep(ObjectID.CRYSTAL_OUTCROP, new RSTile(2787, 4428, 0),
            "Cut", chisel);

    ObjectStep leaveCrystalRoom = new ObjectStep(4973, new RSTile(2756, 4454, 0),
            "Go back up to the flooded area.");

    ObjectStep goBackUpLift = new ObjectStep(ObjectID.LIFT_4942, new RSTile(2726, 4456, 0),
            "Go back up the lift to get a chisel.");

    ObjectStep leaveDarkCrystalRoom = new ObjectStep(4972, new RSTile(2710, 4593, 0), "You need a glowing fungus. Go back up to the flooded area.");
    ObjectStep leaveDarkDaythRoom = new ObjectStep(4972, new RSTile(2732, 4563, 0), "You need a glowing fungus. Go back up to the flooded area.");


    /**
     * STEPS
     */
    RSTile mineCartLevel3SouthHideTileReverse = new RSTile(2728, 4503, 0);

    public void solveMineCartReverse() {
        RSNPC[] mineCart = NPCs.findNearest(3621);
        if (mineCart.length > 0) {
            if (mineCartSouthTriggerArea.contains(mineCart[0].getPosition())
                    && mineCartLevel3SouthHideTileReverse.equals(Player.getPosition())) {
                Log.log("[Debug]: Cart is in trigger area");
               // goDownToFungusRoom.execute();
                Timer.slowWaitCondition(() -> Player.getAnimation() != -1, 4500, 5500);
            } else if (mineCartLevel3SouthHideTile.equals(Player.getPosition())) {
                Log.log("[Debug]: Waiting on cart to move to trigger area");
                Timer.waitCondition(() -> mineCartSouthTriggerArea.contains(mineCart[0].getPosition()), 25000, 35000);
            } else if (HauntedMineConst.inLevel3SouthBeforeMineCart.check() &&
                    mineCartSmallSouthTriggerArea.contains(mineCart[0])) {
                if (PathingUtil.localNavigation(mineCartLevel3SouthHideTile)) {
                    Timer.waitCondition(() -> mineCartLevel3SouthHideTile.isClickable(), 3500, 5000);
                    if (mineCartLevel3SouthHideTile.isClickable() &&
                            DynamicClicking.clickRSTile(mineCartLevel3SouthHideTile, "Walk here"))
                        PathingUtil.movementIdle();
                }
            } else {
                Log.log("[Debug]: Waiting on cart to move to trigger area");
                Timer.waitCondition(() -> mineCartSmallSouthTriggerArea.contains(mineCart[0]), 25000, 35000);
            }
        }
    }

    RSTile mineCartLevel3NorthHideTile = new RSTile(2728, 4503, 0);
    RSTile mineCartLevel3SouthHideTile = new RSTile(2726, 4496, 0);
    RSArea mineCartSouthTriggerArea = new RSArea(new RSTile(2727, 4497, 0), new RSTile(2727, 4510, 0));
    RSArea mineCartSmallSouthTriggerArea = new RSArea(new RSTile(2727, 4508, 0), new RSTile(2727, 4504, 0));

    public void handleLevel3SouthMineCart() {
        RSNPC[] mineCart = NPCs.findNearest(3621);
        if (mineCart.length > 0) {
            if (mineCartSouthTriggerArea.contains(mineCart[0].getPosition())
                    && mineCartLevel3SouthHideTile.equals(Player.getPosition())) {
                Log.log("[Debug]: Cart is in trigger area");
                goDownToFungusRoom.execute();
                Timer.slowWaitCondition(() -> Player.getAnimation() != -1, 4500, 5500);
            } else if (mineCartLevel3SouthHideTile.equals(Player.getPosition())) {
                Log.log("[Debug]: Waiting on cart to move to trigger area");
                Timer.waitCondition(() -> mineCartSouthTriggerArea.contains(mineCart[0].getPosition()), 25000, 35000);
            } else if (HauntedMineConst.inLevel3SouthBeforeMineCart.check() &&
                    mineCartSmallSouthTriggerArea.contains(mineCart[0])) {
                if (PathingUtil.localNavigation(mineCartLevel3SouthHideTile)) {
                    Timer.waitCondition(() -> mineCartLevel3SouthHideTile.isClickable(), 3500, 5000);
                    if (mineCartLevel3SouthHideTile.isClickable() &&
                            DynamicClicking.clickRSTile(mineCartLevel3SouthHideTile, "Walk here"))
                        PathingUtil.movementIdle();
                }
            } else {
                Log.log("[Debug]: Waiting on cart to move to trigger area");
                Timer.waitCondition(() -> mineCartSmallSouthTriggerArea.contains(mineCart[0]), 25000, 35000);
            }
        }
    }

    RSArea outsideDaythRoom = new RSArea(
            new RSTile[]{
                    new RSTile(2799, 4454, 0),
                    new RSTile(2801, 4457, 0),
                    new RSTile(2811, 4458, 0),
                    new RSTile(2812, 4456, 0),
                    new RSTile(2812, 4451, 0),
                    new RSTile(2802, 4452, 0),
                    new RSTile(2801, 4453, 0),
                    new RSTile(2799, 4453, 0)
            }
    );
    AreaRequirement inOutsideDaythRoom = new AreaRequirement(outsideDaythRoom);

    public void navigateMine() {
        if (inCrystalRoom.check()) {
            cutCrystal.execute();
        } else if (inDarkCrystalRoom.check()) {
            leaveDarkCrystalRoom.execute();
        } else if (inDarkDaythRoom.check()) {
            Log.log("[Debug]: In dark datyh room");
            leaveDarkDaythRoom.execute();
        } else if (new Conditions(glowingFungus, inDaythRoom).check()) {
            Log.log("[Debug]: Kill boss");
            Waiting.waitNormal(5000, 200);
        } else if (new Conditions(glowingFungus, inOutsideDaythRoom).check()) {
            Log.log("[Debug]: tryToPickUpKey");
            tryToPickUpKey.setInteractionString("Take");
            tryToPickUpKey.execute();
        } else if (new Conditions(glowingFungus, inFloodedRoom).check()) {
            Log.log("[Debug]: goDownToDayth");
            goDownToDayth.execute();
        } else if (new Conditions(inCrystalEntrance).check()) {
            Log.log("[Debug]: leaveCrystalRoom");
            leaveCrystalRoom.execute();
        } else if (new Conditions(glowingFungus, inLiftRoom, valveOpen, chisel).check()) {
            Log.log("[Debug]: goDownLift");
            goDownLift.execute();
        } else if (new Conditions(glowingFungus, inLiftRoom, valveOpened, chisel).check()) {
            Log.log("[Debug]:             openValve.execute();\n");
            openValve.execute();
        } else if (new Conditions(glowingFungus, inLiftRoom, zealotsKey, chisel).check()) {
            Log.log("[Debug]: useKeyOnValve");
            useKeyOnValve.execute();
        } else if (new Conditions(glowingFungus, inLiftRoom, hasKeyOrOpenedValve).check()) {
            Log.log("[Debug]: pickUpChisel");
            pickUpChisel.execute();
        } else if (new Conditions(glowingFungus, inCollectRoom, hasKeyOrOpenedValve).check()) {
            Log.log("[Debug]: goUpFromCollectRoom");
            goUpFromCollectRoom.execute();
        } else if (new Conditions(glowingFungus, inLevel3North, hasKeyOrOpenedValve).check()) {
            Log.log("[Debug]: goDownFromLevel3NorthEast");
            //TODO add click on the tile to avoid the cart
            goDownFromLevel3NorthEast.execute();
        } else if (new Conditions(glowingFungus, inLevel2North, hasKeyOrOpenedValve).check()) {
            Log.log("[Debug]: goDownLevel2North");
            goDownLevel2North.execute();
        } else if (new Conditions(glowingFungus, inLevel1North, hasKeyOrOpenedValve).check()) {
            Log.log("[Debug]: goDownLevel1North");
            goDownLevel1North.execute();
        } else if (new Conditions(fungusOnOtherSide, inCollectRoom, hasKeyOrOpenedValve).check()) {
            Log.log("[Debug]: Collecting fungus");
            if (Inventory.isFull())
                EatUtil.eatFood();
            collectFungus.execute();
            NPCInteraction.handleConversation("Take it.");
        } else if (new Conditions(fungusOnOtherSide, inLevel3North, hasKeyOrOpenedValve).check()) {
            Log.log("[Debug]:Going down to Collect fungus");
            goDownToCollectFungus.execute();
        } else if (new Conditions(fungusOnOtherSide, inLiftRoom, hasKeyOrOpenedValve).check()) {
            goUpFromLiftRoom.execute();
        } else if (new Conditions(fungusOnOtherSide, inLevel2North, hasKeyOrOpenedValve).check()) {
            Log.log("[Debug]: Going down level 2 north");
            goDownLevel2North.execute();
        } else if (new Conditions(fungusOnOtherSide, inLevel1North, hasKeyOrOpenedValve).check()) {
            Log.log("[Debug]: Going down level 1 north");
            goDownLevel1North.execute();
        } else if (new Conditions(fungusOnOtherSide, inLevel1South, hasKeyOrOpenedValve).check()) {
            Log.log("[Debug]: Going up level 1 south");
            leaveLevel1South.execute();
        } else if (new Conditions(fungusOnOtherSide, inLevel2South, hasKeyOrOpenedValve).check()) {
            Log.log("[Debug]: Going up level 2 south");
            goUpFromLevel2South.execute();
        } else if (new Conditions(fungusOnOtherSide, inLevel3South, hasKeyOrOpenedValve).check()) {
            Log.log("[Debug]: Going up level 3 south");
            goUpFromLevel3South.execute();
        } else if (new Conditions(fungusOnOtherSide, inCartRoom, hasKeyOrOpenedValve).check()) {
            Log.log("[Debug]: Going up from fungus room");
            goUpFromFungusRoom.execute();
        } else if (new Conditions(fungusOnOtherSide, hasKeyOrOpenedValve).check()) {
            Log.log("[Debug]: Enter mine north");
            enterMineNorth.execute();
        } else if (new Conditions(fungusInCart, inCartRoom).check()) {
            Log.log("[Debug]: Solve mine carts");
            if (leverAWrong.check()) pullLeverA.execute();
            else if (leverBWrong.check()) pullLeverB.execute();
            else if (leverCWrong.check()) pullLeverC.execute();
            else if (leverDWrong.check()) pullLeverD.execute();
            else if (leverEWrong.check()) pullLeverE.execute();
            else if (leverFWrong.check()) pullLeverF.execute();
            else if (leverGWrong.check()) pullLeverG.execute();
            else if (leverHWrong.check()) pullLeverH.execute();
            else handleStartPanel();

        } else if (new Conditions(glowingFungus, inCartRoom).check()) {
            Log.log("[Debug]: Put fungus in cart");
            putFungusInCart.execute();
        } else if (inLevel1South.check()) {
            Log.log("[Debug]: In level 1 south");
            goDownFromLevel1South.execute();
        } else if (inLevel2South.check()) {
            Log.log("[Debug]: In level 2 south");
            goDownFromLevel2South.execute();

        } else if (inLevel3South.check()) {
            Log.log("[Debug]: In level 3 south");
            handleLevel3SouthMineCart();
        } else if (inCartRoom.check()) {
            Log.log("[Debug]: In cart room");
            pickFungus.execute();
        } else {
            Log.log("Enter mine");
            enterMine();
        }
    }

    public void exploreMineStep() {
        ConditionalStep exploreMine = new ConditionalStep(cutCrystal);

        exploreMine.addStep(new Conditions(glowingFungus, inCrystalEntrance, crystalMineKey, chisel), cutCrystal);
        exploreMine.addStep(new Conditions(glowingFungus, inFloodedRoom, crystalMineKey, chisel), goDownToCrystals);
        exploreMine.addStep(new Conditions(inFloodedRoom, crystalMineKey), goBackUpLift);
        exploreMine.addStep(new Conditions(inDaythRoom, crystalMineKey), goUpFromDayth);
        exploreMine.addStep(new Conditions(inDaythRoom, killedDayth), pickUpKey);
        //exploreMine.addStep(new Conditions(daythNearby), killDayth);
        exploreMine.addStep(new Conditions(glowingFungus, inDaythRoom), tryToPickUpKey);
        exploreMine.addStep(new Conditions(glowingFungus, inFloodedRoom), goDownToDayth);
        exploreMine.addStep(new Conditions(inCrystalEntrance), leaveCrystalRoom);
        exploreMine.addStep(new Conditions(glowingFungus, inLiftRoom, valveOpen, chisel), goDownLift);
        exploreMine.addStep(new Conditions(glowingFungus, inLiftRoom, valveOpened, chisel), openValve);
        exploreMine.addStep(new Conditions(glowingFungus, inLiftRoom, zealotsKey, chisel), useKeyOnValve);
        exploreMine.addStep(new Conditions(glowingFungus, inLiftRoom, hasKeyOrOpenedValve), pickUpChisel);
        exploreMine.addStep(new Conditions(glowingFungus, inCollectRoom, hasKeyOrOpenedValve), goUpFromCollectRoom);
        exploreMine.addStep(new Conditions(glowingFungus, inLevel3North, hasKeyOrOpenedValve), goDownFromLevel3NorthEast);
        exploreMine.addStep(new Conditions(glowingFungus, inLevel2North, hasKeyOrOpenedValve), goDownLevel2North);
        exploreMine.addStep(new Conditions(glowingFungus, inLevel1North, hasKeyOrOpenedValve), goDownLevel1North);
        exploreMine.execute();
    }

    public Map<Integer, QuestStep> setconditions() {
        collectFungus.addDialogStep("Take it.");
        pickpocketZealot.setInteractionString("Pickpocket");
        Map<Integer, QuestStep> steps = new HashMap<>();

        steps.put(0, talkToZealot);


        if (askedAboutKey.check() && !zealotsKey.check()) {
            Log.log("[Debug]: Getting key");
            pickpocketZealot.execute();
        }

        navigateMine();

        /*steps.put(1, exploreMine);
        steps.put(2, exploreMine);
        steps.put(3, exploreMine);
        steps.put(4, exploreMine);
        steps.put(5, exploreMine);
        steps.put(6, exploreMine);
        steps.put(7, exploreMine);
        steps.put(8, exploreMine);
        steps.put(9, exploreMine);
        steps.put(10, exploreMine);*/
        return steps;
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
        int gameSetting = Game.getSetting(QuestVarPlayer.QUEST_HAUNTED_MINE.getId());
        if (gameSetting == 11) {
            cQuesterV2.taskList.remove(this);
            return;
        }
        if (gameSetting ==0)
            talkToZealot.execute();
        Log.log("[Debug]: Haunted mine gameSetting is " + gameSetting);
        Map<Integer, QuestStep> steps = setconditions();
        Optional<QuestStep> step = Optional.ofNullable(steps.get(gameSetting));
       //step.ifPresent(QuestStep::execute);
        navigateMine();
        exploreMineStep();
        Waiting.waitNormal(500, 20);

    }

    @Override
    public String questName() {
        return "Haunted Mine (" + Game.getSetting(QuestVarPlayer.QUEST_HAUNTED_MINE.getId()) + ")";
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


