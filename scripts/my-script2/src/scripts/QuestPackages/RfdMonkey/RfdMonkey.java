package scripts.QuestPackages.RfdMonkey;

import dax.walker.utils.AccurateMouse;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.*;
import org.tribot.script.sdk.Quest;
import org.tribot.script.sdk.Waiting;
import scripts.*;
import scripts.EntitySelector.Entities;
import scripts.EntitySelector.finders.prefabs.NpcEntity;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.PriestInPeril.PriestInPeril;
import scripts.QuestSteps.BuyItemsStep;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.InventoryRequirement;
import scripts.Requirements.ItemReq;
import scripts.Requirements.ItemRequirement;
import scripts.Requirements.Requirement;
import scripts.Tasks.Priority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class RfdMonkey implements QuestTask {

    private static RfdMonkey quest;

    public static RfdMonkey get() {
        return quest == null ? quest = new RfdMonkey() : quest;
    }

    int RED_BANANA_TREE_ID = 15580;

    int GORILLA_BONES = 3181;
    int ARCHER_BONES = 3179;
    int ZOMBIE_MONKEY_BONES = 3186;
    int APE_ATOL_TAB = 19631;
    int BANANA = 1963;
    int RED_BANANA = 7572;
    int SLICED_RED_BANANA = 7574;
    int MONKEY_NUTS = 4012;
    int TCHIKI_MONKEY_NUTS = 7573;
    int TCHIKI_NUT_PASTE = 7575;
    int SNAKE_CORPSE = 7576;
    int GREEGREE = 4031;
    int M_SPEAK_AMULET = 4021;
    int MONKEY_TALISMAN = 4023;
    int LUNAR_STAFF = 9084;
    int DRAMEN_STAFF = 772;

    int GORILLA_GREEGREE = 4026;
    int NINJA_GREEGREE = 4024;
    int ZOMBIE_GREEGREE = 4030;

    // need to set this depending on the player, maybe D scim by default
    int weaponId = 4151; // abyssal whip

    RSTile ARCHER_FIGHT_TILE = new RSTile(2777, 2791, 0);
    RSArea ARCHER_FIGHT_AREA = new RSArea(ARCHER_FIGHT_TILE, 2);
    RSTile ARCHER_DE_AGRO_TILE = new RSTile(2778, 2799, 0);

    RSTile ZOMBIE_MONKEY_TILE = new RSTile(2773, 9109, 0);
    RSArea ZOMBIE_MONKEY_AREA = new RSArea(ZOMBIE_MONKEY_TILE, 2);
    RSArea ALL_UNDERGROUND_AREA = new RSArea(new RSTile(2819, 9088, 0), new RSTile(2688, 9152, 0));

    RSArea SNAKE_PIT_AREA = new RSArea(new RSTile(7429, 2755, 0), 10);
    RSTile SNAKE_TILE = new RSTile(7429, 2755, 0);

    RSArea LUMBRIDGE_AREA = new RSArea(new RSTile(3213, 3225, 0), new RSTile(3216, 3219, 0));
    RSTile MONKEY_GORILLA_SAFE_TILE = new RSTile(2788, 2778, 0); // use this tile area to kill,
    RSArea MONKEY_GORILLA_AREA = new RSArea(MONKEY_GORILLA_SAFE_TILE, 3);
    // not a true safe tile so pray melee

    RSArea MONKEY_STORE = new RSArea(new RSTile(2768, 2789, 0), new RSTile(2770, 2787, 0));
    RSArea THREE_MONKEY_AREA = new RSArea(new RSTile(2786, 2794, 0), new RSTile(2790, 2792, 0));
    RSArea RED_BANANA_TREE_AREA = new RSArea
            (new RSTile(2097, 2784, 0), 2);
    RSArea CRASH_ISLAND_HOLE_ENTRANCE = new RSArea(new RSTile(2923, 2718, 0), new RSTile(2919, 2722, 0));
    RSArea CRASH_ISLAND_BEACH = new RSArea(new RSTile(2888, 2731, 0), new RSTile(2895, 2720, 0));

    RSTile OUTSIDE_DUNGEON = new RSTile(2764, 2703, 0);
    RSArea START_OF_UNDERGROUND_PASSAGE = new RSArea(new RSTile[]{new RSTile(2768, 9105, 0), new RSTile(2768, 9099, 0), new RSTile(2780, 9097, 0), new RSTile(2781, 9103, 0)});
    RSTile ZOOKNOOK_TILE = new RSTile(2805, 9145, 0);
    RSArea ISLAND_AREA = new RSArea(new RSTile(2719, 2803, 0), new RSTile(2813, 2692, 0)); // whole Island
    RSArea GARKOR_AREA = new RSArea(new RSTile(2805, 2762, 0), new RSTile(2808, 2756, 0));
    RSArea LARGE_SHOP_AREA = new RSArea(new RSTile(2757, 2766, 0), new RSTile(2758, 2772, 0));
    RSArea SMALL_SHOP_AREA = new RSArea(new RSTile(2757, 2767, 0), new RSTile(2758, 2770, 0));
    public static final RSTile[] PATH_TO_GATE = new RSTile[]{new RSTile(2799, 2703, 0), new RSTile(2797, 2703, 0), new RSTile(2795, 2703, 0), new RSTile(2793, 2703, 0), new RSTile(2791, 2704, 0), new RSTile(2789, 2704, 0), new RSTile(2787, 2704, 0), new RSTile(2784, 2704, 0), new RSTile(2784, 2706, 0), new RSTile(2782, 2706, 0), new RSTile(2780, 2706, 0), new RSTile(2778, 2706, 0), new RSTile(2776, 2707, 0), new RSTile(2774, 2709, 0), new RSTile(2772, 2710, 0), new RSTile(2770, 2710, 0), new RSTile(2768, 2711, 0), new RSTile(2766, 2713, 0), new RSTile(2763, 2714, 0), new RSTile(2761, 2714, 0), new RSTile(2758, 2713, 0), new RSTile(2756, 2715, 0), new RSTile(2754, 2715, 0), new RSTile(2752, 2715, 0), new RSTile(2750, 2717, 0), new RSTile(2748, 2720, 0), new RSTile(2747, 2722, 0), new RSTile(2745, 2722, 0), new RSTile(2743, 2722, 0), new RSTile(2741, 2723, 0), new RSTile(2739, 2723, 0), new RSTile(2737, 2723, 0), new RSTile(2735, 2725, 0), new RSTile(2734, 2727, 0), new RSTile(2732, 2728, 0), new RSTile(2731, 2730, 0), new RSTile(2730, 2732, 0), new RSTile(2728, 2733, 0), new RSTile(2726, 2734, 0), new RSTile(2726, 2736, 0), new RSTile(2726, 2738, 0), new RSTile(2726, 2740, 0), new RSTile(2726, 2743, 0), new RSTile(2725, 2745, 0), new RSTile(2724, 2747, 0), new RSTile(2724, 2749, 0), new RSTile(2724, 2751, 0), new RSTile(2724, 2753, 0), new RSTile(2723, 2755, 0), new RSTile(2721, 2757, 0), new RSTile(2720, 2759, 0), new RSTile(2720, 2761, 0), new RSTile(2720, 2763, 0), new RSTile(2721, 2765, 0)};
    public static final RSTile[] pathToElderGuard = new RSTile[]{new RSTile(2721, 2765, 0), new RSTile(2721, 2767, 0), new RSTile(2721, 2769, 0), new RSTile(2721, 2771, 0), new RSTile(2722, 2773, 0), new RSTile(2724, 2775, 0), new RSTile(2726, 2775, 0), new RSTile(2728, 2775, 0), new RSTile(2730, 2775, 0), new RSTile(2732, 2775, 0), new RSTile(2734, 2774, 0), new RSTile(2735, 2771, 0), new RSTile(2736, 2769, 0), new RSTile(2737, 2767, 0), new RSTile(2739, 2765, 0), new RSTile(2741, 2763, 0), new RSTile(2741, 2761, 0), new RSTile(2743, 2760, 0), new RSTile(2745, 2760, 0), new RSTile(2747, 2759, 0), new RSTile(2749, 2759, 0), new RSTile(2751, 2759, 0), new RSTile(2753, 2759, 0), new RSTile(2755, 2759, 0), new RSTile(2757, 2761, 0), new RSTile(2759, 2763, 0), new RSTile(2761, 2763, 0), new RSTile(2764, 2763, 0), new RSTile(2766, 2763, 0), new RSTile(2768, 2763, 0), new RSTile(2770, 2763, 0), new RSTile(2772, 2763, 0), new RSTile(2774, 2763, 0), new RSTile(2776, 2763, 0), new RSTile(2778, 2763, 0), new RSTile(2780, 2763, 0), new RSTile(2782, 2763, 0), new RSTile(2784, 2763, 0), new RSTile(2786, 2762, 0), new RSTile(2788, 2760, 0), new RSTile(2790, 2758, 0), new RSTile(2792, 2756, 0), new RSTile(2794, 2755, 0), new RSTile(2796, 2755, 0), new RSTile(2799, 2755, 0)};
    // the whole island that isn't the city after the gates
    RSArea ISLAND_BEFORE_GATES_AREA = new RSArea(
            new RSTile[]{
                    new RSTile(2695, 2728, 0),
                    new RSTile(2700, 2728, 0),
                    new RSTile(2717, 2744, 0),
                    new RSTile(2718, 2757, 0),
                    new RSTile(2725, 2757, 0),
                    new RSTile(2728, 2742, 0),
                    new RSTile(2736, 2732, 0),
                    new RSTile(2752, 2721, 0),
                    new RSTile(2758, 2721, 0),
                    new RSTile(2776, 2717, 0),
                    new RSTile(2791, 2717, 0),
                    new RSTile(2799, 2725, 0),
                    new RSTile(2811, 2724, 0),
                    new RSTile(2812, 2690, 0),
                    new RSTile(2726, 2693, 0),
                    new RSTile(2693, 2708, 0)
            }
    );

    RSArea KING_AREA = new RSArea(
            new RSTile[]{
                    new RSTile(2805, 2759, 0),
                    new RSTile(2805, 2764, 0),
                    new RSTile(2806, 2764, 0),
                    new RSTile(2806, 2767, 0),
                    new RSTile(2800, 2767, 0),
                    new RSTile(2800, 2759, 0)
            }
    );

    RSArea LARGE_GORILLA_AREA = new RSArea(
            new RSTile[]{
                    new RSTile(2793, 2779, 0),
                    new RSTile(2789, 2775, 0),
                    new RSTile(2788, 2775, 0),
                    new RSTile(2785, 2778, 0),
                    new RSTile(2785, 2780, 0),
                    new RSTile(2788, 2783, 0),
                    new RSTile(2793, 2783, 0)
            }
    );

    RSTile[] PATH_TO_RED_TREE_FROM_THREE_MONKEYS = {
            new RSTile(2788, 2793, 0),
            new RSTile(2793, 2786, 0),
            new RSTile(2786, 2786, 0),
            new RSTile(2780, 2786, 0),
            new RSTile(2770, 2785, 0),
            new RSTile(2759, 2787, 0),
            new RSTile(2746, 2785, 0),
            new RSTile(2733, 2783, 0),
            new RSTile(2722, 2783, 0),
            new RSTile(2708, 2785, 0),
            new RSTile(2704, 2793, 0)
    };


    String[] ZOOK_STRING = {"Can you make another monkey talisman?", "Yes"};


    HashMap<Integer, Integer> invMap = new HashMap<>();

    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.ROPE, 1, 300),
                    new GEItem(ItemID.APE_ATOLL_TELEPORT, 6, 50),
                    new GEItem(ItemID.PRAYER_POTION_4, 4, 20),
                    new GEItem(ItemID.ANTIDOTE_PLUS_PLUS[0], 3, 30),
                    new GEItem(ItemID.KNIFE, 1, 300),
                    new GEItem(ItemID.LUMBRIDGE_TELEPORT, 5, 50),
                    new GEItem(ItemID.SHARK, 15, 50),
                    new GEItem(BANANA, 1, 500),
                    new GEItem(MONKEY_NUTS, 1, 500),
                    new GEItem(ItemID.SUPER_COMBAT_POTION[0], 2, 15),
                    new GEItem(ItemID.STAMINA_POTION[0], 3, 15),
                    new GEItem(ItemID.VARROCK_TELEPORT, 5, 40),
                    new GEItem(ItemID.RING_OF_WEALTH[0], 1, 25),
                    new GEItem(ItemID.DRAGON_SCIMITAR, 1, 25)
            )
    );

    BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);

    public void buyItems() {
        cQuesterV2.status = "Buying Items";
        buyStep.buyItems();
    }

    InventoryRequirement initialItemReqs = new InventoryRequirement(new ArrayList<>(
            Arrays.asList(
                    new ItemReq(ItemID.COINS, 5000, 1000),
                    new ItemReq(ItemID.DRAGON_SCIMITAR, 1, 1, true, true),
                    new ItemReq(ItemID.ROPE, 1, 1),
                    new ItemReq(APE_ATOL_TAB, 5, 1),
                    new ItemReq(ItemID.PESTLE_AND_MORTAR, 1, 1),
                    new ItemReq(M_SPEAK_AMULET, 1, 1, true, true),
                    new ItemReq(ItemID.STAMINA_POTION[0], 2, 1),
                    new ItemReq(ItemID.KNIFE, 1, 1),
                    new ItemReq(ItemID.LUMBRIDGE_TELEPORT, 5, 2),
                    new ItemReq(ItemID.SHARK, 6, 1),
                    new ItemReq(ItemID.KARAMJAN_MONKEY_GREEGREE, 1, 1, true),
                    new ItemReq(MONKEY_NUTS, 1, 1),
                    new ItemReq(BANANA, 1, 1),
                    new ItemReq(DRAMEN_STAFF, 1, 1, true),
                    new ItemReq(ItemID.SUPER_COMBAT_POTION[0], 1, 0),

                    new ItemReq(ItemID.PRAYER_POTION[0], 4, 1),
                    new ItemReq(ItemID.ANTIDOTE_PLUS_PLUS[0], 1, 0, true),
                    new ItemReq(ItemID.RING_OF_WEALTH[0], 1, 0, true)
            )
    ));

    private void getItems() {
        cQuesterV2.status = "Getting items";
        BankManager.open(true);
        BankManager.checkEquippedGlory();
        BankManager.depositAll(true);

        initialItemReqs.withdrawItems();
        // need a weapon for fight
        Utils.equipItem(M_SPEAK_AMULET);

    }

    public void goToStart() {
        cQuesterV2.status = "Going to Lumbridge";
        PathingUtil.walkToArea(LUMBRIDGE_AREA);
        if (Utils.clickObj("Large door", "Open")) {
            Timer.waitCondition(() -> NPCs.find(12341).length > 0, 8000);
            General.sleep(General.random(4000, 8000));
        }
    }

    public void startQuest() {
        goToStart();

        RSObject[] obj = Objects.findNearest(20, "Awowogei");
        if (obj.length > 0 && Utils.clickObject(obj[0], "Inspect")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }
    }

    public boolean buyTalismans(boolean buy) { // get 3050 coins for this
        RSItem[] tal = Inventory.find(MONKEY_TALISMAN);
        if (KING_AREA.contains(Player.getPosition()))
            return true;
        if (tal.length < 3 ||
                (!buy && Inventory.find(GORILLA_BONES, ARCHER_BONES, ZOMBIE_MONKEY_BONES).length < 3)) {
            RSItem[] invGreeGree = Inventory.find(GREEGREE);
            cQuesterV2.status = "Going to Buy Talismans";
            General.println("[Debug]: " + cQuesterV2.status);

            if (!ISLAND_AREA.contains(Player.getPosition())) {
                cQuesterV2.status = "Going to talisman Shop - Go to island";
                PathingUtil.walkToTile(new RSTile(2799, 2703, 0), 2, true);
            }
            if (invGreeGree.length > 0) {
                cQuesterV2.status = "Equipping Greegree";
                if (invGreeGree[0].click("Hold"))
                    Timer.waitCondition(() -> Equipment.isEquipped(GREEGREE), 2500, 3500);
                General.sleep(General.random(800, 1500));
            }
            if (!LARGE_SHOP_AREA.contains(Player.getPosition())) {
                cQuesterV2.status = "Going to talisman Shop - Gate walk";

                RSItem[] invStam = Inventory.find(ItemID.STAMINA_POTION);
                if (invStam.length > 0 && invStam[0].click("Drink"))
                    Utils.microSleep();
                //todo
                // need to check im on the right side of hte gate
                Walking.walkPath(PATH_TO_GATE);
                Timer.waitCondition(() -> !Player.isMoving(), 6000, 9000);

                RSObject[] gate = Objects.findNearest(40, 4788);
                if (gate.length > 0 && Utils.clickObject(gate[0], "Open")) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                    General.sleep(General.random(3500, 5000));
                }
            }
            if (!LARGE_SHOP_AREA.contains(Player.getPosition())) {
                cQuesterV2.status = "Going to talisman Shop - blind walk";
                PathingUtil.localNavigation(SMALL_SHOP_AREA.getRandomTile());
                Walking.blindWalkTo(SMALL_SHOP_AREA.getRandomTile());
                Timer.waitCondition(() -> !Player.isMoving(), 6000, 9000);
            }

            if (!buy)
                return SMALL_SHOP_AREA.contains(Player.getPosition());

            cQuesterV2.status = "Buying talismans";
            if (Utils.clickNPC("Tutab", "Trade"))
                Timer.waitCondition(() -> Interfaces.get(300, 16, 12) != null, 6000, 9090);

            if (Interfaces.get(300, 16, 12) != null) {
                for (int i = 0; i < 3; i++) {
                    RSItem[] talisman = Inventory.find(MONKEY_TALISMAN);
                    if (talisman.length < 3) {
                        if (AccurateMouse.click(Interfaces.get(300, 16, 12), "Buy 1"))
                            Timer.waitCondition(() -> Inventory.find(MONKEY_TALISMAN).length > talisman.length, 4000, 5000);
                    }
                }
                Interfaces.closeAll();
            }
        }
        return Inventory.find(MONKEY_TALISMAN).length > 2;
    }

    public void getZombieBones() {
        if (BankManager.checkInventoryItems(GORILLA_BONES, ARCHER_BONES)) {
            if (!BankManager.checkInventoryItems(ZOMBIE_MONKEY_BONES) && ISLAND_AREA.contains(Player.getPosition())) {
                cQuesterV2.status = "Getting Zombie monkey bones";

                if (!ZOMBIE_MONKEY_AREA.contains(Player.getPosition()))
                    PathingUtil.walkToArea(ZOMBIE_MONKEY_AREA, false);

                combat("Monkey Zombie", ZOMBIE_MONKEY_BONES, Prayer.PRAYERS.PROTECT_FROM_MELEE, ZOMBIE_MONKEY_AREA);
            }
        }
    }


    public void getGorillaBones() {
        if (!ISLAND_AREA.contains(Player.getPosition()))
            buyTalismans(false);

        if (!BankManager.checkInventoryItems(GORILLA_BONES) && ISLAND_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Getting Gorilla bones";
            General.println("[Debug]: " + cQuesterV2.status);
            if (PathingUtil.localNavigation(MONKEY_GORILLA_SAFE_TILE))
                PathingUtil.movementIdle();
            else if (buyTalismans(false) &&
                    PathingUtil.localNavigation(Player.getPosition().translate(10, 10))) // blind walks closer
                // so that local nav can work
                PathingUtil.movementIdle();

            combat("Monkey Guards", GORILLA_BONES,
                    Prayer.PRAYERS.PROTECT_FROM_MELEE, MONKEY_GORILLA_AREA);
        }
    }


    public void getNinjaBones() {
        if (BankManager.checkInventoryItems(GORILLA_BONES)) {
            if (!BankManager.checkInventoryItems(ARCHER_BONES) && ISLAND_AREA.contains(Player.getPosition())) {
                cQuesterV2.status = "Getting Archer/Ninja bones";

                if (equipGreegree()) {
                    cQuesterV2.status = "Going to Archer tile";
                    if (!ARCHER_FIGHT_AREA.contains(Player.getPosition())) {
                        PathingUtil.localNavigation(ARCHER_FIGHT_TILE);
                        PathingUtil.movementIdle();

                    }
                    if (ARCHER_FIGHT_AREA.contains(Player.getPosition()))
                        combat("Monkey Archer", ARCHER_BONES, Prayer.PRAYERS.PROTECT_FROM_MISSILES, ARCHER_FIGHT_AREA);

                }

            } else if (ARCHER_FIGHT_AREA.contains(Player.getPosition()) && BankManager.checkInventoryItems(ARCHER_BONES)) {
                cQuesterV2.status = "De-aggroing archer";
                PathingUtil.localNavigation(ARCHER_DE_AGRO_TILE);
                PathingUtil.movementIdle();
                equipGreegree();
            }
        }
    }

    public boolean equipGreegree() {
        RSItem[] invGreeGree = Inventory.find(GREEGREE);
        if (!Equipment.isEquipped(GREEGREE) && invGreeGree.length > 0) {
            if (invGreeGree[0].click("Hold"))
                return Timer.waitCondition(() -> Equipment.isEquipped(GREEGREE), 3500, 5000);
        }
        return Equipment.isEquipped(GREEGREE);
    }


    public void combat(String name, int bonesId, Prayer.PRAYERS prayType, RSArea combatArea) {
        RSItem[] scim = Inventory.find(Filters.Items.nameContains("scimitar"));

        if (combatArea.contains(Player.getPosition()) || LARGE_GORILLA_AREA.contains(Player.getPosition())) {
            General.sleep(50, 75);
            General.println("[Debug]: in combat area");
            if (Inventory.find(bonesId).length == 0) {
                cQuesterV2.status = "Killing " + name + " for bones";
                General.println("[Debug]: " + cQuesterV2.status);

                if (Prayer.getPrayerPoints() < General.random(10, 25))
                    EatUtil.drinkPotion(ItemID.PRAYER_POTION);

                RSNPC[] targ = NPCs.findNearest(name);

                if (!Prayer.isPrayerEnabled(prayType) && Prayer.getPrayerPoints() > 0)
                    Prayer.enable(prayType);

                // attempts to equip universal weapon id (abyssal whip)
                if (!Equipment.isEquipped(weaponId))
                    Utils.equipItem(weaponId);

                if (Equipment.isEquipped(GREEGREE) && scim.length > 0) {
                    scim[0].click("Wield");
                    Timer.waitCondition(() -> Combat.isUnderAttack(), 5000);
                    CombatUtil.waitUntilOutOfCombat(General.random(40, 65));
                }
                if (targ.length > 0) {
                    if (targ[0].isInteractingWithMe()) {
                        CombatUtil.waitUntilOutOfCombat(General.random(40, 65));
                    } else if (CombatUtil.clickTarget(targ[0]))
                        CombatUtil.waitUntilOutOfCombat(General.random(40, 65));
                }
                if (Inventory.isFull())
                    EatUtil.eatFood();

                if (Utils.clickGroundItem(bonesId))
                    Timer.waitCondition(() -> Inventory.find(bonesId).length > 0, 4000, 6000);

            } else {
                equipGreegree();
                if (Prayer.isPrayerEnabled(prayType))
                    Prayer.disable(prayType);

            }
        }
    }

    public void goToZooknook() {
        if (BankManager.checkInventoryItems(ARCHER_BONES, GORILLA_BONES, ZOMBIE_MONKEY_BONES)) {
            cQuesterV2.status = "Going to Zooknock";
            General.println("[Debug]: " + cQuesterV2.status);
            if (PathingUtil.walkToArea(START_OF_UNDERGROUND_PASSAGE, false)) {
                Prayer.enable(Prayer.PRAYERS.PROTECT_FROM_MELEE);
            }
            if (PathingUtil.walkToTile(ZOOKNOOK_TILE)) {
                Timer.waitCondition(() -> !Player.isMoving(), 20000, 25000);
                Prayer.disable(Prayer.PRAYERS.PROTECT_FROM_MELEE);
            }
        }
        for (int i = 0; i < 3; i++) {
            handZooknockItem(GORILLA_BONES, GORILLA_GREEGREE);
            handZooknockItem(ZOMBIE_MONKEY_BONES, ZOMBIE_GREEGREE);
            handZooknockItem(ARCHER_BONES, NINJA_GREEGREE);
        }
    }

    public void talkToAwowogei() {
        // go to and talk to him (he's an object)
        if (ISLAND_AREA.contains(Player.getPosition())) {
            if (!KING_AREA.contains(Player.getPosition())) {
                cQuesterV2.status = "Going to Awowogei";
                General.println("[Debug]: " + cQuesterV2.status);
                RSItem[] invItem1 = Inventory.find(M_SPEAK_AMULET);
                if (invItem1.length > 0) {
                    invItem1[0].click("Wear");
                }
                if (Utils.clickObj(4771, "Talk-to")) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                }
            }
            if (KING_AREA.contains(Player.getPosition())) {
                cQuesterV2.status = "Talking to Awowogei";
                General.println("[Debug]: " + cQuesterV2.status);
                if (Utils.clickObj(4771, "Talk-to")) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation("Do you have a favourite dish?");
                }
            }
        } else {
            buyTalismans(false);
        }
    }

    public void leaveAwowogeiArea() {
        if (KING_AREA.contains(Player.getPosition())) {
            General.println("[Debug]: Leaving king area");
            RSNPC guard = Entities.find(NpcEntity::new)
                    .tileEquals(new RSTile(2802, 2758, 0))
                    .nameContains("Elder Guard")
                    .getFirstResult();
            if (NpcChat.talkToNPC(guard)) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                PathingUtil.movementIdle();
            }
        }
    }


    // repeat x3 (first with talk==true)
    public void goToTempleAreaMonkeys(boolean talk) {
        leaveAwowogeiArea();
        if (!THREE_MONKEY_AREA.contains(Player.getPosition()))
            PathingUtil.localNavigation(THREE_MONKEY_AREA.getRandomTile());
        cQuesterV2.status = "Talking to 1 of 3 Monkeys";
        if (Utils.clickNPC("Kikazaru", "Talk-to")) {
            NPCInteraction.waitForConversationWindow();
            if (talk)
                NPCInteraction.handleConversation("Do you know anything about the King's favourite dish?");
            NPCInteraction.handleConversation();
        }
    }

    public void useItemOnTempleMonkey(int ItemID) {
        leaveAwowogeiArea();
        if (!THREE_MONKEY_AREA.contains(Player.getPosition()))
            PathingUtil.localNavigation(THREE_MONKEY_AREA.getRandomTile());
        cQuesterV2.status = "Using item on 1 of Monkeys";
        if (Utils.useItemOnNPC(ItemID, "Kikazaru")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }
    }


    public void getRedBanana() {
        RSItem[] sliced = Inventory.find(SLICED_RED_BANANA);
        if (sliced.length < 3) {

            if (THREE_MONKEY_AREA.contains(Player.getPosition())) {
                //check gree gree equipped?
                cQuesterV2.status = "Going to red bananas (Pathed)";
                General.println("[Debug]: " + cQuesterV2.status);
                Walking.walkPath(PATH_TO_RED_TREE_FROM_THREE_MONKEYS);
                PathingUtil.movementIdle();
            }
            cQuesterV2.status = "Getting Red Bananas";
            for (int i = 0; i < 5; i++) {
                if (!Equipment.isEquipped(GORILLA_GREEGREE)) {
                    Utils.equipItem(GORILLA_GREEGREE, "Hold");
                }
                if (!RED_BANANA_TREE_AREA.contains(Player.getPosition()) &&
                        !Player.getPosition().equals(new RSTile(2097, 2784, 0)))
                    PathingUtil.localNavigation(RED_BANANA_TREE_AREA.getRandomTile());
                if (Inventory.isFull()) {
                    Inventory.drop(BANANA);
                    EatUtil.eatFood();
                }
                if (Utils.useItemOnObject(ItemID.ROPE, RED_BANANA_TREE_ID)) { // spelling might be wrong
                    Timer.waitCondition(() -> Inventory.find(RED_BANANA).length > 0, 4000, 6000);
                }
                if (Inventory.find(ItemID.KNIFE).length == 0) {
                    // drop b/c we don't have a knife for some reason
                }
                if (Utils.useItemOnItem(ItemID.KNIFE, RED_BANANA))
                    Timer.waitCondition(() -> Inventory.find(RED_BANANA).length == 0, 4000, 6000);

                sliced = Inventory.find(SLICED_RED_BANANA);
                if (sliced.length > 2)
                    break;
            }
        }
    }

    int SNAKE_ID = 2978;


    // TODO drink p-pots and equip your attacking weapon
    public void goToSnakes() {
        RSItem[] invCorpse = Inventory.find(SNAKE_CORPSE);
        RSItem[] banana = Inventory.find(SLICED_RED_BANANA);
        RSItem[] scim = Inventory.find(Filters.Items.nameContains("scimitar"));

        if (invCorpse.length < 3 && banana.length >= 2) {

            if (!SNAKE_PIT_AREA.contains(Player.getPosition())) {
                cQuesterV2.status = "Going to Snake pit";
                PathingUtil.walkToArea(CRASH_ISLAND_HOLE_ENTRANCE, false);
                if (scim.length > 0)
                    scim[0].click("Wield");

                PrayerUtil.prayMelee();
                if (Utils.clickObj("Pit", "Enter")) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation("Yes, I'm as hard as nails.");
                    Timer.waitCondition(() -> NPCs.find(SNAKE_ID).length > 0, 4000, 5600);
                }
            }

            RSNPC[] snakes = NPCs.find(SNAKE_ID);
            if (snakes.length > 0) { // or check instance on new code
                cQuesterV2.status = "Killing Snakes";
                RSGroundItem[] groundItems = GroundItems.find(SNAKE_CORPSE);
                if (Combat.isUnderAttack() && groundItems.length == 0) {
                    Timer.waitCondition(() -> GroundItems.find(SNAKE_CORPSE).length > 0
                            || Combat.getHPRatio() < General.random(40, 65), 35000, 45000);

                    // also add prayer pot drinking support

                } else if (groundItems.length > 0) {
                    cQuesterV2.status = "Looting Snake Corpse";
                    Utils.clickGroundItem(SNAKE_CORPSE);
                }
            }
        }
        RSNPC[] snakes = NPCs.find(SNAKE_ID);
        if (snakes.length > 0 && Inventory.find(SNAKE_CORPSE).length > 2 &&
                Utils.clickObj("Rope", "Climb")) { //exits
            cQuesterV2.status = "Leaving snake pit";
            Timer.waitCondition(() -> !SNAKE_PIT_AREA.contains(Player.getPosition()), 4000, 6000);
        }
    }

    public void getNuts() {
        RSItem[] invCorpse = Inventory.find(SNAKE_CORPSE);
        RSItem[] sliced = Inventory.find(SLICED_RED_BANANA);
        if (invCorpse.length >= 3 && sliced.length >= 3) {
            cQuesterV2.status = "Getting Nuts";
            // need to be a ninja monkey to navigate the agility course


            if (Utils.clickObj("Bush", "Pick")) { //picks nut

            }
            if (Utils.useItemOnItem(TCHIKI_MONKEY_NUTS, ItemID.PESTLE_AND_MORTAR)) {

            }
            if (Utils.useItemOnItem(SNAKE_CORPSE, TCHIKI_NUT_PASTE)) {

            }
            // stuff snake and repeat 3x
        }
    }


    public void handZooknockItem(int bones, int finishedTalismanId) {
        // if in zooknock area 2805, 9144,0
        for (int i = 0; i < 4; i++) {
            General.sleep(50);

            if (Inventory.find(finishedTalismanId).length == 1)
                break;

            if (!NPCInteraction.isConversationWindowUp() &&
                    NpcChat.talkToNPC("Zooknock"))
                NPCInteraction.waitForConversationWindow();

            if (NPCInteraction.isConversationWindowUp()) {
                NPCInteraction.handleConversation(ZOOK_STRING);
                NPCInteraction.handleConversationRegex("Yes");
            }
            // hand bones
            if (!NPCInteraction.isConversationWindowUp() &&
                    Utils.useItemOnNPC(bones, "Zooknock")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
            if (!NPCInteraction.isConversationWindowUp() &&
                    Utils.useItemOnNPC(MONKEY_TALISMAN, "Zooknock")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
        }
    }

    RSTile AGILITY_START = new RSTile(2740, 2737, 0);
    RSTile TILE_BEFORE_ROPE_AGIL_COURSE = new RSTile(2751, 2731, 0);
    RSTile TILE_BEFORE_LADDER_AGIL_COURSE = new RSTile(2757, 2729, 0);
    RSArea AREA_BEFORE_ROPE_SWING = new RSArea(
            new RSTile[]{
                    new RSTile(2753, 2737, 0),
                    new RSTile(2750, 2738, 0),
                    new RSTile(2747, 2738, 0),
                    new RSTile(2743, 2742, 0),
                    new RSTile(2741, 2744, 0),
                    new RSTile(2730, 2744, 0),
                    new RSTile(2743, 2730, 0),
                    new RSTile(2751, 2724, 0),
                    new RSTile(2752, 2724, 0),
                    new RSTile(2753, 2724, 0)
            }
    );
    RSArea AREA_AFTER_ROPE_SWING = new RSArea(
            new RSTile[]{
                    new RSTile(2756, 2738, 0),
                    new RSTile(2756, 2724, 0),
                    new RSTile(2768, 2724, 0),
                    new RSTile(2761, 2736, 0)
            }
    );
    RSArea NUT_AREA = new RSArea(new RSTile(3029, 5452, 0), new RSTile(3016, 5462, 0));
    int BUSH_ID = 16059;

    public void getTchikiNuts() {
        cQuesterV2.status = "Going to get nuts";
        // check for inventory staff for fairy rings
        if (!ISLAND_AREA.contains(Player.getPosition()) && !NUT_AREA.contains(Player.getPosition()))
            PathingUtil.walkToTile(AGILITY_START, 2, false);

        Utils.equipItem(NINJA_GREEGREE, "Hold");

        if (Equipment.isEquipped(NINJA_GREEGREE)) {
            if (AREA_BEFORE_ROPE_SWING.contains(Player.getPosition())) {
                if (PathingUtil.localNavigation(TILE_BEFORE_ROPE_AGIL_COURSE))
                    PathingUtil.movementIdle();

                if (Utils.clickObj("Rope", "Swing"))
                    Timer.waitCondition(() -> AREA_AFTER_ROPE_SWING.contains(Player.getPosition()), 6000, 9000);

            }
            if (AREA_AFTER_ROPE_SWING.contains(Player.getPosition())) {
                if (Utils.clickObj("Hole", "Enter")) {
                    Timer.waitCondition(() -> NUT_AREA.contains(Player.getPosition()), 6000, 9000);
                }
            }
            if (NUT_AREA.contains(Player.getPosition())) {
                for (int i = 0; i < 3; i++) {
                    RSItem[] invNuts = Inventory.find(TCHIKI_MONKEY_NUTS);
                    RSItem[] unneededItems = Inventory.find(ItemID.ROPE, ItemID.KNIFE, MONKEY_NUTS, BANANA);
                    if (Inventory.isFull() && unneededItems.length > 0) {
                        Inventory.drop(unneededItems);
                        Utils.unselectItem();
                    }

                    if (invNuts.length > 0)
                        Inventory.drop(invNuts);

                    if (Utils.clickObj(BUSH_ID, "Pick"))
                        Timer.waitCondition(() -> Inventory.find(TCHIKI_MONKEY_NUTS).length > 0, 6000, 9000);

                    Utils.clickGroundItem(TCHIKI_MONKEY_NUTS);
                    Utils.clickGroundItem(TCHIKI_MONKEY_NUTS);
                    Utils.clickGroundItem(TCHIKI_MONKEY_NUTS);
                }
            }
        }
    }

    RSArea AFTER_CLIMBING_DOWN_TREE_AREA = new RSArea(
            new RSTile[]{
                    new RSTile(2764, 2747, 0),
                    new RSTile(2764, 2735, 0),
                    new RSTile(2771, 2735, 0),
                    new RSTile(2779, 2748, 0),
                    new RSTile(2774, 2758, 0),
                    new RSTile(2764, 2757, 0)
            }
    );
    RSTile[] PATH_TO_TRAP_DOOR = {
            new RSTile(2771, 2748, 0),
            new RSTile(2768, 2760, 0),
            new RSTile(2778, 2764, 0),
            new RSTile(2784, 2764, 0),
            new RSTile(2783, 2777, 0),
            new RSTile(2784, 2785, 0),
            new RSTile(2795, 2785, 0),
            new RSTile(2806, 2785, 0)
    };
    RSArea AREA_UNDER_TRAP_DOOR = new RSArea(new RSTile(2814, 9187, 0), new RSTile(2796, 9213, 0));

    public void leaveNutArea() {
        // check we have 3 nuts
        if (NUT_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Leaving nut area";
            if (Utils.clickObj("Exit", "Climb-up"))
                Timer.waitCondition(() -> !NUT_AREA.contains(Player.getPosition()), 6000, 9000);
        }

        if (AREA_AFTER_ROPE_SWING.contains(Player.getPosition()) &&
                Utils.clickObj("Tropical tree", "Climb-down")) {
            cQuesterV2.status = "Swinging down tree";
            Timer.waitCondition(() -> AFTER_CLIMBING_DOWN_TREE_AREA.contains(Player.getPosition()), 8000, 12000);
        }

        if (AFTER_CLIMBING_DOWN_TREE_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Going to underground";
            Walking.walkPath(PATH_TO_TRAP_DOOR);
            PathingUtil.movementIdle();
        }

        if (AREA_UNDER_TRAP_DOOR.contains(Player.getPosition()) &&
                Utils.clickObj("Exit", "Enter")) {
            PathingUtil.movementIdle();
            Timer.waitCondition(() -> FINAL_CAVE_AREA.contains(Player.getPosition()), 7000, 9000);
        }
        if (FINAL_CAVE_AREA.contains(Player.getPosition())) {
            Utils.equipItem(ZOMBIE_GREEGREE, "Hold");
            makeStuffedSnake();
        }
    }

    RSTile TILE_BEFORE_CAVE_EXIT = new RSTile(2804, 9199, 0);
    RSArea FINAL_CAVE_AREA = new RSArea(new RSTile(3049, 5492, 0), new RSTile(3068, 5477, 0));
    RSTile TILE_BEFORE_COOKER = new RSTile(3057, 5485, 0);
    int RAW_STUFFED_SNAKE = 7577;
    int COOK_ROCK = 26175;
    int COOKED_STUFFED_SNAKE = 7579;

    public void makeStuffedSnake() {
        if (Utils.useItemOnItem(ItemID.PESTLE_AND_MORTAR, TCHIKI_MONKEY_NUTS)) {
            Timer.waitCondition(() -> Inventory.find(TCHIKI_MONKEY_NUTS).length == 0, 5000, 7000);
            General.sleep(500, 1000);
        }
        RSItem[] stuffed = Inventory.find(RAW_STUFFED_SNAKE);
        RSItem[] cookedSnake = Inventory.find(COOKED_STUFFED_SNAKE);
        if (stuffed.length == 0 && Utils.useItemOnItem(TCHIKI_NUT_PASTE, SNAKE_CORPSE)) {
            Timer.waitCondition(() -> Inventory.find(TCHIKI_NUT_PASTE).length == 0, 5000, 7000);
            General.sleep(500, 1000);
        }

        stuffed = Inventory.find(RAW_STUFFED_SNAKE);
        cookedSnake = Inventory.find(COOKED_STUFFED_SNAKE);
        if (stuffed.length > 0 && cookedSnake.length == 0) {
            if (!TILE_BEFORE_COOKER.equals(Player.getPosition())) {
                cQuesterV2.status = "Moving to cook rock";
                Walking.blindWalkTo(TILE_BEFORE_COOKER);
                PathingUtil.movementIdle();
            }
            if (TILE_BEFORE_COOKER.equals(Player.getPosition())) {
                int invSize = Inventory.getAll().length;
                if (Utils.useItemOnObject(RAW_STUFFED_SNAKE, COOK_ROCK)) {
                    Timer.waitCondition(() -> Inventory.getAll().length < invSize, 5000, 7000);
                    Timer.waitCondition(() -> Inventory.getAll().length == invSize, 5000, 7000);
                }
            }
        }
    }


    public void finishQuest() {
        RSItem[] cookedSnake = Inventory.find(COOKED_STUFFED_SNAKE);
        if (cookedSnake.length > 0) {
            goToStart();

            Utils.equipItem(M_SPEAK_AMULET, "Wear");

            if (Utils.useItemOnObject(COOKED_STUFFED_SNAKE, "Awowogei")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
        }
    }


    @Override
    public void execute() {

        General.println("Varbit 1914 is " + Utils.getVarBitValue(1914));

        if (Utils.getVarBitValue(1914) == 0) {
            buyItems();
            getItems();
            startQuest();
        } else if (Utils.getVarBitValue(1914) == 5) {
            if (!BankManager.checkInventoryItems(ZOMBIE_GREEGREE, NINJA_GREEGREE, GORILLA_GREEGREE))
                buyTalismans(true);

            if (!BankManager.checkInventoryItems(GORILLA_GREEGREE))
                getGorillaBones();

            if (!BankManager.checkInventoryItems(NINJA_GREEGREE))
                getNinjaBones();

            if (!BankManager.checkInventoryItems(ZOMBIE_GREEGREE))
                getZombieBones();

            if (!BankManager.checkInventoryItems(ZOMBIE_GREEGREE, NINJA_GREEGREE, GORILLA_GREEGREE) &&
                    BankManager.checkInventoryItems(ZOMBIE_MONKEY_BONES, GORILLA_BONES, ARCHER_BONES))
                goToZooknook();


            talkToAwowogei();

            // talk to awowgei
        } else if (Utils.getVarBitValue(1914) == 10) {
            // talk to the money in the temple
            goToTempleAreaMonkeys(true);

        } else if (Utils.getVarBitValue(1914) == 20
                && Utils.getVarBitValue(1915) == 0
                && Utils.getVarBitValue(1916) == 0) {
            // talk to the money in the temple && show banana
            useItemOnTempleMonkey(BANANA);

        } else if (Utils.getVarBitValue(1914) == 20
                && Utils.getVarBitValue(1915) == 10
                && Utils.getVarBitValue(1916) == 0) {
            useItemOnTempleMonkey(MONKEY_NUTS);

        } else if (Utils.getVarBitValue(1914) == 20
                && Utils.getVarBitValue(1915) == 10
                && Utils.getVarBitValue(1916) == 10) {

            getRedBanana();

        } else if (Utils.getVarBitValue(1914) == 20
                && Utils.getVarBitValue(1915) == 20
                && Utils.getVarBitValue(1916) == 10
                && Utils.getVarBitValue(1917) == 0) {

            goToSnakes();

        } else if (Utils.getVarBitValue(1914) == 20
                && Utils.getVarBitValue(1915) == 20
                && Utils.getVarBitValue(1916) == 10
                && Utils.getVarBitValue(1917) == 10) {
            getTchikiNuts();

        } else if (Utils.getVarBitValue(1914) == 20
                && Utils.getVarBitValue(1915) == 20
                && Utils.getVarBitValue(1916) == 20
                && Utils.getVarBitValue(1917) == 10) {
            leaveNutArea();

        } else if (Utils.getVarBitValue(1914) == 30
                && Utils.getVarBitValue(1915) == 20
                && Utils.getVarBitValue(1916) == 20
                && Utils.getVarBitValue(1917) == 10) {
            leaveNutArea();

        } else if (Utils.getVarBitValue(1914) == 40
                && Utils.getVarBitValue(1915) == 20
                && Utils.getVarBitValue(1916) == 20
                && Utils.getVarBitValue(1917) == 10) {
            leaveNutArea();
            finishQuest();
            // use item on Awowogei while wearing mSpeak amulet

        } else if (Utils.getVarBitValue(1914) == 50) {
            // done
            Utils.closeQuestCompletionWindow();
            cQuesterV2.taskList.remove(this);
            Waiting.waitNormal(5000, 250);
            if (Utils.getVarBitValue(12139) == 1) {
                // cutscene
                cQuesterV2.status = "Waiting for cutscene";
                Timer.waitCondition(() -> Utils.getVarBitValue(12139) == 0, 90000, 120000);
            }
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
        return "RFD Awowogei";
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
        return Quest.LUNAR_DIPLOMACY.getState().equals(Quest.State.COMPLETE);
    }
}
