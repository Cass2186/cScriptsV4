package scripts.QuestPackages.MountainDaughter;

import dax.walker.utils.AccurateMouse;
import dax.walker.utils.camera.DaxCamera;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.*;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Waiting;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.TheGolem.TheGolem;
import scripts.QuestSteps.BuyItemsStep;
import scripts.QuestSteps.QuestTask;
import scripts.Tasks.Priority;

import java.util.ArrayList;
import java.util.Arrays;

public class MountainDaughter implements QuestTask {

    private static MountainDaughter quest;

    public static MountainDaughter get() {
        return quest == null ? quest = new MountainDaughter() : quest;
    }

    int SPADE = 952;
    int ROPE = 954;
    int IRON_PICKAXE = 1267;
    int IRON_AXE = 1349;
    int PLANK = 960;
    int POLE = 4494;
    int LEATHER_GLOVES = 1059;
    int HAMAL_ID = 1373;
    int MUD_ID = 5883;
    int INV_MUD_ID = 4490;
    int TALL_TREE = 5848;
    int SHINING_POOLING = 5897;
    int TREE_TO_CHOP = 5904;
    int TENT_DOOR = 5891;
    int ANCIENT_ROCK = 5895;
    int HALF_A_ROCK = 4487;
    int WHITE_PEARL_FRUIT = 4485;
    int WHITE_PEARL_SEED = 4486;
    int MUDDY_ROCK = 4492;
    int CORPSE = 4488;

    boolean PURCHASE = true;

    RSTile MUDDY_ROCK_TILE = new RSTile(2809, 3679, 0);
    RSTile TREE_TO_CHOP_TILE = new RSTile(2801, 3703, 0);
    RSTile TREE_TO_CHOP_TILE2 = new RSTile(2806, 3703, 0);

    RSArea START_AREA = new RSArea(new RSTile(2811, 3670, 0), new RSTile(2805, 3675, 0));
    RSArea PRE_ROCK_AREA = new RSArea(new RSTile(2759, 3657, 0), new RSTile(2761, 3654, 0));
    RSArea PUSH_ROCK_AREA = new RSArea(new RSTile(2765, 3666, 0), new RSTile(2762, 3666, 0));
    RSArea LANDING_AREA = new RSArea(new RSTile(2765, 3663, 0), new RSTile(2767, 3661, 0));
    RSArea POST_ROCK = new RSArea(new RSTile(2768, 3656, 0), new RSTile(2817, 3683, 0));
    RSArea BEFORE_TENT = new RSArea(new RSTile(2804, 3671, 0), new RSTile(2803, 3674, 0));
    RSArea HAMAL_TENT = new RSArea(new RSTile(2812, 3670, 0), new RSTile(2805, 3675, 0));
    RSArea JOKUL_HOUSE = new RSArea(new RSTile(2813, 3679, 0), new RSTile(2810, 3682, 0));
    RSArea MUD_POND = new RSArea(new RSTile(2811, 3659, 0), new RSTile(2804, 3663, 0));
    RSArea ISLAND_ONE = new RSArea(new RSTile(2774, 3683, 0), new RSTile(2770, 3687, 0));
    RSArea ISLAND_TWO = new RSArea(new RSTile(2770, 3693, 0), new RSTile(2775, 3690, 0));
    RSArea ISLAND_THREE = new RSArea(new RSTile(2777, 3688, 0), new RSTile(2786, 3697, 0));
    RSArea SVIDI_AREA = new RSArea(new RSTile(2723, 3650, 0), new RSTile(2695, 3691, 0));
    RSArea BRUDNT_AREA = new RSArea(new RSTile(2661, 3666, 0), new RSTile(2656, 3673, 0));
    RSArea OUTSIDE_ROCK_TENT = new RSArea(new RSTile(2801, 3666, 0), new RSTile(2798, 3667, 0));
    RSArea ROCK_TENT_AREA = new RSArea(new RSTile(2797, 3665, 0), new RSTile(2802, 3658, 0));
    RSArea WHITE_WOLF_MOUNTAIN = new RSArea(new RSTile(2849, 3505, 0), new RSTile(2854, 3501, 0));
    RSArea NORTH_SHORE = new RSArea(new RSTile(2807, 3699, 0), new RSTile(2759, 3712, 0));
    RSArea PAST_FIRST_TREE = new RSArea(new RSTile(2802, 3703, 0), new RSTile(2808, 3702, 0));
    RSArea PAST_SECOND_TREE = new RSArea(new RSTile(2808, 3704, 0), new RSTile(2810, 3702, 0));
    RSArea WHOLE_CAVE = new RSArea(new RSTile(2816, 10109, 0), new RSTile(2756, 10049, 0));
    RSArea KENDAL_AREA_SMALL = new RSArea(new RSTile(2782, 10078, 0), new RSTile(2787, 10075, 0));
    RSArea KENDAL_SAFE_SPOT_AREA = new RSArea(new RSTile(2779, 10077, 0), new RSTile(2780, 10077, 0));


    public static final RSTile[] PATH_FROM_MUD_TO_TREE = new RSTile[]{new RSTile(2807, 3663, 0), new RSTile(2805, 3666, 0), new RSTile(2804, 3669, 0), new RSTile(2803, 3673, 0), new RSTile(2804, 3676, 0), new RSTile(2804, 3679, 0), new RSTile(2805, 3683, 0), new RSTile(2806, 3687, 0), new RSTile(2807, 3690, 0), new RSTile(2807, 3693, 0), new RSTile(2804, 3694, 0), new RSTile(2801, 3691, 0), new RSTile(2797, 3687, 0), new RSTile(2793, 3684, 0), new RSTile(2790, 3684, 0), new RSTile(2787, 3684, 0), new RSTile(2784, 3684, 0), new RSTile(2782, 3681, 0), new RSTile(2779, 3678, 0), new RSTile(2776, 3677, 0), new RSTile(2773, 3677, 0), new RSTile(2770, 3679, 0)};

    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.STAFF_OF_AIR, 1, 200),
                    new GEItem(ItemID.MIND_RUNE, 400, 20),
                    new GEItem(ItemID.FIRE_RUNE, 1200, 20),
                    new GEItem(ItemID.LOBSTER, 12, 60),
                    new GEItem(ItemID.SPADE, 1, 500),
                    new GEItem(ItemID.ROPE, 1, 500),
                    new GEItem(ItemID.IRON_AXE, 1, 500),
                    new GEItem(ItemID.IRON_PICKAXE, 1, 500),
                    new GEItem(ItemID.PLANK, 1, 50),
                    new GEItem(ItemID.LEATHER_GLOVES, 1, 50),
                    new GEItem(ItemID.CAMELOT_TELEPORT, 10, 50),
                    new GEItem(ItemID.PLANK, 1, 50),
                    new GEItem(ItemID.COMBAT_BRACELET[2], 1, 20),
                    new GEItem(ItemID.SKILLS_NECKLACE[0], 1, 20),
                    new GEItem(ItemID.STAMINA_POTION[0], 3, 15),
                    new GEItem(ItemID.RING_OF_WEALTH[0], 1, 25)
            )
    );
    BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);

    public void buyItems() {
        cQuesterV2.status = "Buying Items";
        General.println("[Debug]: " + cQuesterV2.status);
        buyStep.buyItems();

    }


    public void getItems() {
        cQuesterV2.status = "Getting Items";
        General.println("[Debug]: " + cQuesterV2.status);
        BankManager.open(true);
        BankManager.checkEquippedGlory();
        BankManager.checkCombatBracelet();
        BankManager.depositAll(true);
        BankManager.withdraw(1, true, LEATHER_GLOVES);
        Utils.equipItem(LEATHER_GLOVES);
        BankManager.withdraw(1, true, PLANK);
        BankManager.withdraw(1, true, IRON_AXE);
        BankManager.withdraw(1, true, IRON_PICKAXE);
        BankManager.withdraw(1, true, ROPE);
        BankManager.withdraw(1, true, SPADE);
        BankManager.withdraw(8, true, ItemID.LOBSTER);
        BankManager.withdraw(15, true, ItemID.CAMELOT_TELEPORT);
        BankManager.withdraw(3, true, ItemID.STAMINA_POTION[0]);
        BankManager.withdraw(1, true, ItemID.STAFF_OF_AIR);
        Utils.equipItem(ItemID.STAFF_OF_AIR);
        BankManager.withdraw(300, true, ItemID.MIND_RUNE);
        BankManager.withdraw(1, true, ItemID.RING_OF_DUELING[0]);
        BankManager.withdraw(900, true, ItemID.FIRE_RUNE);
        BankManager.close(true);
    }

    public void checkLevel() {
        if (Skills.getActualLevel(Skills.SKILLS.AGILITY) < 20 ||
                Skills.getActualLevel(Skills.SKILLS.MAGIC) < 13) {
            General.println("[Debug]: Missing a requirement for MTD (20 agility or 13 magic)");
        }
    }

    RSTile MAIN_TENT_DOOR_TILE = new RSTile(2805, 3672, 0);
    RSArea WHOLE_CAMP = new RSArea(
            new RSTile[]{
                    new RSTile(2760, 3659, 0),
                    new RSTile(2768, 3659, 0),
                    new RSTile(2771, 3657, 0),
                    new RSTile(2812, 3655, 0),
                    new RSTile(2817, 3693, 0),
                    new RSTile(2809, 3710, 0),
                    new RSTile(2752, 3704, 0),
                    new RSTile(2757, 3675, 0),
                    new RSTile(2772, 3670, 0)
            }
    );

    public void goToCamp() {
        cQuesterV2.status = "Going to Camp";
        if (!POST_ROCK.contains(Player.getPosition()) && !WHOLE_CAMP.contains(Player.getPosition())) {
            PathingUtil.walkToArea(PRE_ROCK_AREA);
            RSObject[] rock = Objects.findNearest(15, 5847);

            if (rock.length > 0)
                if (rock[0].getPosition().distanceTo(Player.getPosition()) > 3) {
                    Utils.blindWalkToTile(rock[0].getPosition());
                    Utils.shortSleep();
                }


            if (Utils.clickObj(5847, "Climb-over")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                if (Timer.waitCondition(() -> WHOLE_CAMP.contains(Player.getPosition()), 7000))
                    return;

            }
            if (!WHOLE_CAMP.contains(Player.getPosition())) {
                PathingUtil.walkToArea(PUSH_ROCK_AREA);
                if (Utils.useItemOnObject(ROPE, 5842)) {
                    Timer.waitCondition(() -> LANDING_AREA.contains(Player.getPosition()), 12000, 15000);
                    Utils.shortSleep();
                }
            }
        }
        if (!HAMAL_TENT.contains(Player.getPosition())) {
            PathingUtil.walkToArea(BEFORE_TENT);

            //  if (Utils.clickObject(Filters.Objects.tileEquals(MAIN_TENT_DOOR_TILE), "Open"))
            //     Utils.idle(2500, 5000);

            PathingUtil.localNavigation(new RSTile(2810, 3671, 0));
            Waiting.waitUntil(3500, () -> START_AREA.contains(Player.getPosition()));
        }
    }

    public void startQuest() {
        cQuesterV2.status = "Starting Quest";
        goToCamp();
        if (HAMAL_TENT.contains(Player.getPosition())) {
            if (NpcChat.talkToNPC(HAMAL_ID)) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Why is everyone so hostile?");
                NPCInteraction.handleConversation("So what are you doing up here?");
                NPCInteraction.handleConversation("I will search for her!");
                NPCInteraction.handleConversation();
                Utils.modSleep();
            }
        }
    }

    public void getPole() {
        cQuesterV2.status = "Getting Pole";
        if (Inventory.find(POLE).length < 1) {
            if (HAMAL_TENT.contains(Player.getPosition()))
                if (Utils.clickObject(Filters.Objects.tileEquals(MAIN_TENT_DOOR_TILE), "Open"))
                    Utils.idle(2500, 5000);

            if (PathingUtil.localNavigation(JOKUL_HOUSE))
                PathingUtil.movementIdle();

            RSGroundItem[] pole = GroundItems.find(POLE);
            if (pole.length > 0) {
                if (!pole[0].isClickable())
                    DaxCamera.focus(pole[0]);

                if (AccurateMouse.click(pole[0], "Take"))
                    Timer.abc2WaitCondition(() -> Inventory.find(POLE).length > 0, 7000, 10000);
            }
        }
    }

    public void getMud() {
        if (Inventory.find(INV_MUD_ID).length < 1) {
            if (HAMAL_TENT.contains(Player.getPosition()))
                if (Utils.clickObj(5887, "Open"))
                    Utils.idle(2500, 5000);

            cQuesterV2.status = "Getting Mud";
            if (PathingUtil.localNavigation(MUD_POND))
                Utils.shortSleep();

            if (Utils.clickObj(MUD_ID, "Dig-up"))
                Timer.abc2WaitCondition(() -> Inventory.find(INV_MUD_ID).length > 0, 5000, 8000);

        }
    }

    public void goToIsland() {
        cQuesterV2.status = "Navigating to Island";
        if (!ISLAND_ONE.contains(Player.getPosition()) && !ISLAND_TWO.contains(Player.getPosition())
                && !ISLAND_THREE.contains(Player.getPosition())) {
            if (Walking.walkPath(PATH_FROM_MUD_TO_TREE)) {
                Timer.waitCondition(() -> !Player.isMoving(), 12000, 15000);
                Utils.shortSleep();
            }

            if (Utils.useItemOnObject(INV_MUD_ID, TALL_TREE))
                Utils.modSleep();

            if (Utils.clickObj(TALL_TREE, "Climb")) {
                Timer.abc2WaitCondition(() -> ISLAND_ONE.contains(Player.getPosition()), 15000, 20000);
                Utils.shortSleep();

            }
        }

        if (ISLAND_ONE.contains(Player.getPosition()))
            if (Utils.useItemOnObject(POLE, 5849)) {
                Timer.abc2WaitCondition(() -> ISLAND_TWO.contains(Player.getPosition()), 13000, 18000);
                Utils.shortSleep();
            }

        if (ISLAND_TWO.contains(Player.getPosition()))
            if (Utils.useItemOnObject(PLANK, 5850)) {
                Timer.abc2WaitCondition(() -> ISLAND_THREE.contains(Player.getPosition()), 22000, 28000);
                Utils.shortSleep();
            }

        if (ISLAND_THREE.contains(Player.getPosition())) {
            if (Utils.clickObj(SHINING_POOLING, "Listen-to")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Hello! Who are you?");
                NPCInteraction.handleConversation("So what exactly do you want from me?");
                NPCInteraction.handleConversation("That sounds like something I can do.");
                NPCInteraction.handleConversation("I'll get right on it.");
                NPCInteraction.handleConversation();
                Utils.modSleep();
            }
        }
    }

    public void leaveIsland() {
        cQuesterV2.status = "Leaving island";
        if (ISLAND_THREE.contains(Player.getPosition())) {
            Utils.useItemOnObject(PLANK, 5851);
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Yes.");
            Utils.modSleep();
        }
    }

    RSTile TENT_DOOR_2 = new RSTile(2799, 3665, 0);

    public void goToHamal() {
        cQuesterV2.status = "Going to Hamal";
        if (!HAMAL_TENT.contains(Player.getPosition())) {
            PathingUtil.walkToArea(BEFORE_TENT);

            PathingUtil.localNavigation(new RSTile(2810, 3671, 0));
            Waiting.waitUntil(3500, () -> START_AREA.contains(Player.getPosition()));
        }
    }

    public void talkToHamal() {
        if (HAMAL_TENT.contains(Player.getPosition())) {
            cQuesterV2.status = "Talking-to Hamal";
            if (NpcChat.talkToNPC(HAMAL_ID)) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("About the people of Rellekka...");
                NPCInteraction.handleConversation();
            }
            if (NpcChat.talkToNPC(HAMAL_ID)) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("About your food supplies...");
                NPCInteraction.handleConversation();
            }
        }
    }

    public void locateSvidi() {
        cQuesterV2.status = "Going to Svidi";
        PathingUtil.walkToArea(SVIDI_AREA);
        if (Combat.getHPRatio() < 40) {
            EatUtil.eatFood();
        }
        RSNPC[] svidi = NPCs.findNearest("Svidi");

        if (svidi.length > 0)
            PathingUtil.walkToTile(svidi[0].getPosition(), 1, false);
        else {
            Utils.microSleep();
        }
        if (Combat.isUnderAttack()) {
            Autocast.enableAutocast(Autocast.FIRE_STRIKE);
            Utils.microSleep();
        }
        if (Combat.getHPRatio() < 40) {
            EatUtil.eatFood();
        }
        if (NpcChat.talkToNPC("Svidi")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Can't I persuade you to go in there somehow?");
            NPCInteraction.handleConversation();
        }
        if (Combat.getHPRatio() < 40) {
            EatUtil.eatFood();
        }
    }

    public void goToBrundt() {
        cQuesterV2.status = "Going to Brundt the Chieftain";
        PathingUtil.walkToArea(BRUDNT_AREA);

        if (NpcChat.talkToNPC("Brundt the Chieftain")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Ask about the mountain camp.");
            NPCInteraction.handleConversation("Did it contain vast magical powers?");
            NPCInteraction.handleConversation();
            Utils.shortSleep();
        }
    }

    public void getHalfARock() {
        cQuesterV2.status = "Going to Camp";
        if (!POST_ROCK.contains(Player.getPosition()) && !ROCK_TENT_AREA.contains(Player.getPosition())) {
            PathingUtil.walkToArea(PRE_ROCK_AREA);
            RSObject[] rock = Objects.findNearest(15, 5847);

            if (rock.length > 0)
                if (rock[0].getPosition().distanceTo(Player.getPosition()) > 3) {
                    Utils.blindWalkToTile(rock[0].getPosition());
                    Utils.shortSleep();
                }

            if (Utils.clickObj(5847, "Climb-over")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
        }

        if (!ROCK_TENT_AREA.contains(Player.getPosition())) {
            PathingUtil.walkToArea(OUTSIDE_ROCK_TENT);
            if (Utils.clickObject(Filters.Objects.tileEquals(TENT_DOOR_2), "Go-through"))
                Timer.abc2WaitCondition(() -> ROCK_TENT_AREA.contains(Player.getPosition()), 5000, 9000);
        }
        if (ROCK_TENT_AREA.contains(Player.getPosition())) {
            if (Utils.useItemOnObject(IRON_PICKAXE, ANCIENT_ROCK)) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                Timer.abc2WaitCondition(() -> Inventory.find(HALF_A_ROCK).length > 0, 7000, 12000);
                Utils.shortSleep();
            }
        }
    }

    public void bringRockToBrundt() {
        cQuesterV2.status = "Going to Brundt the Chieftain";
        PathingUtil.walkToArea(BRUDNT_AREA);

        if (NpcChat.talkToNPC("Brundt the Chieftain")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Ask about the mountain camp.");
            NPCInteraction.handleConversation();
            Utils.shortSleep();
        }
    }

    public void talkToSvidiAgain() {
        cQuesterV2.status = "Going to Svidi";
        PathingUtil.walkToArea(SVIDI_AREA);

        RSNPC[] svidi = NPCs.findNearest("Svidi");

        if (svidi.length > 0) {
            PathingUtil.walkToTile(svidi[0].getPosition());
            Utils.shortSleep();
        } else {
            General.println("[Debug]: Did not locate Svidi, trying again");
            PathingUtil.walkToTile(SVIDI_AREA.getRandomTile());
            Utils.idle(1000, 4000);
        }

        if (NpcChat.talkToNPC("Svidi")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }
    }

    public void goToHamalTwo() {
        cQuesterV2.status = "Going to Hamal";
        if (!POST_ROCK.contains(Player.getPosition())) {

            PathingUtil.walkToArea(PRE_ROCK_AREA);

            if (Utils.clickObj(5847, "Climb-over")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                Utils.shortSleep();
            }
        }

        goToHamal();

        if (HAMAL_TENT.contains(Player.getPosition())) {
            if (NpcChat.talkToNPC(HAMAL_ID)) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("About the people of Rellekka...");
                NPCInteraction.handleConversation();
            }
        }
    }

    public void goToJokul() {
        cQuesterV2.status = "Getting Pole";
        if (HAMAL_TENT.contains(Player.getPosition()))
            if (Utils.clickObj(5887, "Open"))
                Timer.waitCondition(() -> Objects.findNearest(10, 5887).length == 0, 5000, 8000);

        PathingUtil.localNavigation(JOKUL_HOUSE);

        if (NpcChat.talkToNPC("Jokul")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            Utils.modSleep();
        }
    }

    public void getBerries() {
        if (Inventory.find(WHITE_PEARL_SEED).length < 1) {
            cQuesterV2.status = "Going to White Wolf Mountain";
            PathingUtil.walkToArea(WHITE_WOLF_MOUNTAIN);
            Utils.equipItem(LEATHER_GLOVES);
            if (Utils.clickObj("Thorny bushes", "Pick-from")) {
                Timer.abc2WaitCondition(() -> Inventory.find(WHITE_PEARL_FRUIT).length > 0, 5000, 8000);
                Utils.microSleep();
            }
            if (Inventory.find(WHITE_PEARL_FRUIT).length > 0) {
                if (AccurateMouse.click(Inventory.find(WHITE_PEARL_FRUIT)[0], "Eat")) {
                    Timer.abc2WaitCondition(() -> Inventory.find(WHITE_PEARL_SEED).length > 0, 5000, 8000);
                    Utils.microSleep();
                }
            }
        }
    }

    public void goToHamalThree() {
        if (Inventory.find(WHITE_PEARL_SEED).length > 0) {
            cQuesterV2.status = "Step 13: Going to Hamal";
            if (!POST_ROCK.contains(Player.getPosition())) {

                PathingUtil.walkToArea(PRE_ROCK_AREA);

                if (Utils.clickObj(5847, "Climb-over")) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                    Utils.modSleep();
                }
            }
            if (!HAMAL_TENT.contains(Player.getPosition()))
                goToHamal();

            if (HAMAL_TENT.contains(Player.getPosition())) {
                if (NpcChat.talkToNPC(HAMAL_ID)) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation("About your food supplies...");
                    NPCInteraction.handleConversation();
                    Utils.shortSleep();
                }
            }
        }
    }

    public void goToIslandTwo() {
        cQuesterV2.status = "Step 14: Navigating to Island";
        if (!ISLAND_ONE.contains(Player.getPosition()) && !ISLAND_TWO.contains(Player.getPosition())
                && !ISLAND_THREE.contains(Player.getPosition())) {

            if (!POST_ROCK.contains(Player.getPosition()) &&
                    !PAST_FIRST_TREE.contains(Player.getPosition()) &&
                    !PAST_SECOND_TREE.contains(Player.getPosition()))
                goToCamp();

            if (HAMAL_TENT.contains(Player.getPosition()))
                if (Utils.clickObj(5887, "Open"))
                    Timer.waitCondition(() -> Objects.findNearest(10, 5887).length == 0, 5000, 8000);

            if (Walking.walkPath(PATH_FROM_MUD_TO_TREE)) {
                Timer.waitCondition(() -> !Player.isMoving(), 12000, 15000);
                Utils.shortSleep();
            }

            if (Utils.useItemOnObject(INV_MUD_ID, TALL_TREE))
                Utils.idle(2000, 4000);

            if (Utils.clickObj(TALL_TREE, "Climb"))
                Timer.abc2WaitCondition(() -> ISLAND_ONE.contains(Player.getPosition()), 15000, 20000);

        }

        if (ISLAND_ONE.contains(Player.getPosition()))
            if (Utils.useItemOnObject(POLE, 5849)) {
                Timer.abc2WaitCondition(() -> ISLAND_TWO.contains(Player.getPosition()), 13000, 18000);
                Utils.shortSleep();
            }

        if (ISLAND_TWO.contains(Player.getPosition()))
            if (Utils.useItemOnObject(PLANK, 5850)) {
                Timer.abc2WaitCondition(() -> ISLAND_THREE.contains(Player.getPosition()), 22000, 28000);
                Utils.shortSleep();
            }

        if (ISLAND_THREE.contains(Player.getPosition())) {
            if (Utils.clickObj(SHINING_POOLING, "Listen-to")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Hello! Who are you?");
                NPCInteraction.handleConversation("So what exactly do you want from me?");
                NPCInteraction.handleConversation("That sounds like something I can do.");
                NPCInteraction.handleConversation("I'll get right on it.");
                NPCInteraction.handleConversation();
                Utils.modSleep();
            }
        }
    }


    public void goToTree() {
        if (!WHOLE_CAVE.contains(Player.getPosition())) {
            cQuesterV2.status = "Step 15: Going to Kendal";
            if (!ISLAND_THREE.contains(Player.getPosition()) &&
                    !PAST_FIRST_TREE.contains(Player.getPosition()) &&
                    !PAST_SECOND_TREE.contains(Player.getPosition()))
                goToIslandTwo();


            if (ISLAND_THREE.contains(Player.getPosition())) {

                if (Utils.clickObj("Flat stone", "Jump-across")) {
                    Timer.abc2WaitCondition(() -> NORTH_SHORE.contains(Player.getPosition()), 10000, 15000);
                    Utils.shortSleep();
                }
            }

            if (!PAST_FIRST_TREE.contains(Player.getPosition()) && !PAST_SECOND_TREE.contains(Player.getPosition())) {
                PathingUtil.walkToTile(TREE_TO_CHOP_TILE);

                if (Utils.clickObj(TREE_TO_CHOP, "Chop down")) {
                    Timer.abc2WaitCondition(() -> Objects.findNearest(2, TREE_TO_CHOP).length < 1, 15000, 20000);
                    Utils.shortSleep();
                }
            }

            if (PAST_FIRST_TREE.contains(Player.getPosition()) && !PAST_SECOND_TREE.contains(Player.getPosition())) {
                Utils.blindWalkToTile(TREE_TO_CHOP_TILE2);

                if (Utils.clickObj(TREE_TO_CHOP, "Chop down")) {
                    Timer.abc2WaitCondition(() -> Objects.findNearest(2, TREE_TO_CHOP).length < 1, 15000, 20000);
                    Utils.shortSleep();
                }
            }

            if (PAST_SECOND_TREE.contains(Player.getPosition())) {
                if (Utils.clickObj("Cave entrance", "Enter")) {
                    Timer.abc2WaitCondition(() -> !PAST_SECOND_TREE.contains(Player.getPosition()), 7000, 12000);
                    Utils.shortSleep();
                }
            }
        }
    }

    RSTile SAFE_TILE = new RSTile(2780, 10077, 0);
    String[] kendalStrings = {"It's just me, no one special.", "You mean a sacrifice?",
            "You look like a man in a bearsuit!", "Can i see that corpse?", "I humbly request to be given the remains.",
            "I will kill you myself!"};

    public void talkToKendal() {
        if (WHOLE_CAVE.contains(Player.getPosition())) {
            cQuesterV2.status = "Step 15: Going to Kendal";
            PathingUtil.localNavigation(KENDAL_AREA_SMALL);

            if (!Autocast.isAutocastEnabled(Autocast.FIRE_STRIKE)) {

                if (Utils.equipItem(ItemID.STAFF_OF_AIR))
                    Utils.microSleep();

                Autocast.enableAutocast(Autocast.FIRE_STRIKE);
                Utils.shortSleep();
            }

            if (NpcChat.talkToNPC("The Kendal")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation(kendalStrings);
            }
        }
    }

    public void killKendal() {
        General.sleep(150);
        if (!KENDAL_SAFE_SPOT_AREA.contains(Player.getPosition()))
            Utils.blindWalkToTile(SAFE_TILE);

        if (KENDAL_SAFE_SPOT_AREA.contains(Player.getPosition())) {
            RSNPC[] kendal = NPCs.find("The Kendal");

            if (kendal.length > 0) {
                cQuesterV2.status = "Killing Kendal";

                if (!kendal[0].isClickable())
                    kendal[0].adjustCameraTo();

                if (!kendal[0].isInCombat() && Utils.clickNPC(kendal[0], "Attack", false))
                    Timer.waitCondition(() -> kendal[0].isInCombat(), 6000, 10000);

                else if ((Combat.getHPRatio() < 50) && Inventory.find(ItemID.LOBSTER).length > 0)
                    Inventory.find(ItemID.LOBSTER)[0].click("Eat");

            } else
                Utils.modSleep();
        }
    }


    public void getCorpse() {
        cQuesterV2.status = "Getting corpse";
        if (Utils.clickGroundItem(CORPSE)) {
            Timer.abc2WaitCondition(() -> Inventory.find(CORPSE).length > 0, 8000, 12000);
            Utils.shortSleep();
        }
    }

    public void goToHamalFour() {
        if (Inventory.find(CORPSE).length > 0) {
            cQuesterV2.status = "Step 16: Going to Hamal ";
            if (!POST_ROCK.contains(Player.getPosition())) {

                PathingUtil.walkToArea(PRE_ROCK_AREA);

                if (Utils.clickObj(5847, "Climb-over")) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                }
            }
            if (!HAMAL_TENT.contains(Player.getPosition()))
                goToHamal();

            if (HAMAL_TENT.contains(Player.getPosition())) {
                if (NpcChat.talkToNPC(HAMAL_ID)) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation("But he killed your daughter!");
                    NPCInteraction.handleConversation("I will.");
                    NPCInteraction.handleConversation();
                }
            }
        }
    }

    public void buryCorpse() {
        if (HAMAL_TENT.contains(Player.getPosition()))
            if (Utils.clickObj(5887, "Open")) {
                Timer.waitCondition(() -> Objects.findNearest(10, 5887).length == 0, 5000, 8000);
                Utils.shortSleep();
            }
        if (Inventory.find(MUDDY_ROCK).length < 5) {
            cQuesterV2.status = "Getting rocks";
            PathingUtil.walkToTile(MUDDY_ROCK_TILE);

            if (Inventory.isFull() && Inventory.find(ItemID.FOOD_IDS).length > 0) {
                AccurateMouse.click(Inventory.find(ItemID.FOOD_IDS)[0], "Eat");
                Utils.microSleep();
            }
            RSItem[] invRocks = Inventory.find(MUDDY_ROCK);
            RSGroundItem[] muddyRock = GroundItems.findNearest(MUDDY_ROCK);

            if (muddyRock.length > 0) {
                if (!muddyRock[0].isClickable())
                    muddyRock[0].adjustCameraTo();

                cQuesterV2.status = "looting muddy rock";
                General.println("[Debug]: looting muddy rock");
                if (AccurateMouse.click(muddyRock[0], "Take")) {
                    Timer.waitCondition(() -> Inventory.find(MUDDY_ROCK).length > invRocks.length, 7000, 10000);
                    Utils.microSleep();
                }
            }


        } else if (Inventory.find(MUDDY_ROCK).length >= 5) {

            if (Inventory.isFull() && Inventory.find(ItemID.FOOD_IDS).length > 0)
                AccurateMouse.click(Inventory.find(ItemID.FOOD_IDS)[0], "Eat");

            cQuesterV2.status = "Navigating to Island";
            if (!ISLAND_ONE.contains(Player.getPosition()) && !ISLAND_TWO.contains(Player.getPosition())
                    && !ISLAND_THREE.contains(Player.getPosition())) {

                Walking.walkPath(PATH_FROM_MUD_TO_TREE);
                Timer.waitCondition(() -> !Player.isMoving(), 12000, 15000);

                if (NpcChat.talkToNPC("Ragnar")) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                }

                if (Inventory.find(4489).length > 0) {
                    if (Utils.clickObj(TALL_TREE, "Climb"))
                        Timer.abc2WaitCondition(() -> ISLAND_ONE.contains(Player.getPosition()), 15000, 20000);

                    if (ISLAND_ONE.contains(Player.getPosition()))
                        if (Utils.useItemOnObject(POLE, 5849))
                            Timer.abc2WaitCondition(() -> ISLAND_TWO.contains(Player.getPosition()), 13000, 18000);

                    if (ISLAND_TWO.contains(Player.getPosition()))
                        if (Utils.useItemOnObject(PLANK, 5850))
                            Timer.abc2WaitCondition(() -> ISLAND_THREE.contains(Player.getPosition()), 22000, 28000);

                    if (ISLAND_THREE.contains(Player.getPosition())) {
                        RSItem[] corpse = Inventory.find(CORPSE);

                        if (corpse.length > 0)
                            if (AccurateMouse.click(corpse[0], "Bury")) {
                                Utils.modSleep();
                                NPCInteraction.waitForConversationWindow();
                                NPCInteraction.handleConversation();
                            }
                        if (Utils.useItemOnObject(MUDDY_ROCK, 5861))
                            Utils.modSleep();
                    }

                }
            }
        }
    }


    int GAME_SETTING = 423;

    @Override
    public void execute() {
        if (!checkRequirements()) {
            cQuesterV2.taskList.remove(this);
            return;
        }

        if (Game.getSetting(423) == 0) {
             buyItems();
             getItems();
            startQuest();

        } else if (Game.getSetting(423) == 10) {
            getPole();
            getMud();
            goToIsland();

        } else if (Game.getSetting(423) == 266) { // changes to 266 once you've added mud to tree
            goToIsland();

        } else if (Game.getSetting(423) == 276) {
            leaveIsland();
            goToHamal();
            talkToHamal();
        }
        if (RSVarBit.get(262).getValue() == 10 || Game.getSetting(423) == 1053972) {
            locateSvidi();
        }
        if (RSVarBit.get(262).getValue() == 20) {
            goToBrundt();
        }
        if (RSVarBit.get(262).getValue() == 30) {
            getHalfARock();
        }
        if (RSVarBit.get(262).getValue() == 40) {
            bringRockToBrundt();
        }
        if (RSVarBit.get(262).getValue() == 50) {
            talkToSvidiAgain();
        }
        if (RSVarBit.get(262).getValue() == 60 && RSVarBit.get(260).getValue() == 20 && RSVarBit.get(263).getValue() == 0) {
            goToHamalTwo(); // may not be needed
            goToJokul();
        }
        if (RSVarBit.get(262).getValue() == 60 && RSVarBit.get(260).getValue() == 20 && RSVarBit.get(263).getValue() == 10) {
            getBerries();
            goToHamalThree();
        }
        if (RSVarBit.get(262).getValue() == 60 && RSVarBit.get(260).getValue() == 20 && RSVarBit.get(263).getValue() == 20) {
            goToIslandTwo();
        }
        if (RSVarBit.get(260).getValue() == 30) {
            goToTree();
            talkToKendal();
            killKendal();
        }
        if (RSVarBit.get(260).getValue() == 40) {
            killKendal();
        }
        if (RSVarBit.get(260).getValue() == 50 ||
                Game.getSetting(423) == -2082834126 || Game.getSetting(423) == 64649522) {
            getCorpse();
            goToHamalFour();
        }
        if (RSVarBit.get(260).getValue() == 60 || Game.getSetting(423) == -2015725252) {
            buryCorpse();
        }
        if (RSVarBit.get(260).getValue() == 70 || Game.getSetting(423) == -807765690) {
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
        return "Mountain Daughter";
    }

    @Override
    public boolean checkRequirements() {
        if (Skills.getActualLevel(Skills.SKILLS.AGILITY) < 20 ||
                Skills.getActualLevel(Skills.SKILLS.MAGIC) < 13) {
            Log.log("Missing an MTD requirement");
            return false;
        }
        return true;
    }
}
