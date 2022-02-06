package scripts.QuestPackages.TrollStronghold;

import dax.walker.utils.AccurateMouse;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSGroundItem;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Skill;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestSteps.BuyItemsStep;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.Requirement;
import scripts.Requirements.SkillRequirement;
import scripts.Tasks.Priority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TrollStronghold implements QuestTask {
    private static TrollStronghold quest;

    public static TrollStronghold get() {
        return quest == null ? quest = new TrollStronghold() : quest;
    }

    int CLIMBING_BOOTS = 3105;
    int ROCK_ID = 3748;
    int PRISON_KEY = 3135;
    int CELL_KEY_2 = 3137;
    int CELL_KEY_1 = 3136;
    int CELL_DOOR_2_ID = 3765;
    int CELL_DOOR_1_ID = 3767;


    RSArea START_AREA = new RSArea(new RSTile(2899, 3527, 0), new RSTile(2893, 3529, 0));
    RSArea PATH_STEP_1 = new RSArea(new RSTile(2818, 3563, 0), new RSTile(2815, 3566, 0));
    RSArea DAD_AREA = new RSArea(new RSTile(2897, 3620, 0), new RSTile(2901, 3614, 0));
    RSArea BEFORE_ROCKS = new RSArea(new RSTile(2855, 3612, 0), new RSTile(2858, 3608, 0));
    RSArea AFTER_ROCKS = new RSArea(new RSTile(2849, 3613, 0), new RSTile(2875, 3629, 0));
    RSArea AFTER_ROCKS_2_TO_ROCKS_3 = new RSArea(new RSTile(2861, 3628, 0), new RSTile(2877, 3613, 0));
    RSArea AFTER_ROCKS_3_TO_GATE = new RSArea(new RSTile(2880, 3625, 0), new RSTile(2896, 3611, 0));
    RSArea BEFORE_ARENA_GATE = new RSArea(new RSTile(2896, 3616, 0), new RSTile(2894, 3620, 0));
    RSArea SAFE_AREA = new RSArea(new RSTile(2897, 3620, 0), new RSTile(2898, 3616, 0));
    RSArea CAVE_ENTRANCE = new RSArea(new RSTile(2902, 3645, 0), new RSTile(2906, 3641, 0));

    RSTile[] PATH_TO_ROCKS = new RSTile[]{new RSTile(2817, 3564, 0), new RSTile(2817, 3567, 0), new RSTile(2817, 3570, 0), new RSTile(2817, 3573, 0), new RSTile(2817, 3576, 0), new RSTile(2817, 3579, 0), new RSTile(2817, 3582, 0), new RSTile(2817, 3585, 0), new RSTile(2819, 3588, 0), new RSTile(2822, 3589, 0), new RSTile(2825, 3590, 0), new RSTile(2829, 3593, 0), new RSTile(2830, 3596, 0), new RSTile(2833, 3599, 0), new RSTile(2836, 3601, 0), new RSTile(2839, 3602, 0), new RSTile(2842, 3605, 0), new RSTile(2845, 3607, 0), new RSTile(2848, 3609, 0), new RSTile(2851, 3609, 0), new RSTile(2854, 3609, 0), new RSTile(2857, 3608, 0), new RSTile(2857, 3611, 0)};
    RSTile[] PATH_TO_ROCKS_2 = new RSTile[]{new RSTile(2857, 3613, 0), new RSTile(2855, 3616, 0), new RSTile(2852, 3618, 0), new RSTile(2852, 3621, 0), new RSTile(2855, 3624, 0), new RSTile(2858, 3626, 0)};
    RSTile[] PATH_TO_ARENA_EXIT = new RSTile[]{new RSTile(2897, 3618, 0), new RSTile(2900, 3618, 0), new RSTile(2903, 3618, 0), new RSTile(2906, 3618, 0), new RSTile(2909, 3617, 0), new RSTile(2910, 3620, 0), new RSTile(2913, 3623, 0), new RSTile(2916, 3626, 0)};
    RSTile[] PATH_TO_CAVE_ENTRANCE = new RSTile[]{new RSTile(2917, 3629, 0), new RSTile(2917, 3632, 0), new RSTile(2914, 3635, 0), new RSTile(2910, 3638, 0), new RSTile(2907, 3639, 0), new RSTile(2904, 3642, 0)};
    RSArea AFTER_ARENA_EXIT = new RSArea(new RSTile(2915, 3629, 0), new RSTile(2918, 3630, 0));
    RSArea INSIDE_CAVE = new RSArea(new RSTile(2902, 10018, 0), new RSTile(2927, 10037, 0));
    RSArea CAVE_EXIT = new RSArea(new RSTile(2902, 10036, 0), new RSTile(2909, 10031, 0));


    RSArea BEFORE_STRONGHOLD = new RSArea(new RSTile(2839, 3692, 0), new RSTile(2844, 3688, 0));

    RSArea WHOLE_STRONGHOLD_LEVEL2 = new RSArea(new RSTile(2867, 10046, 2), new RSTile(2819, 10114, 2));
    RSArea STRONGHOLD_SOUTHWEST_DOOR = new RSArea(new RSTile(2839, 10058, 2), new RSTile(2840, 10055, 2));
    RSArea STAIRS_AREA = new RSArea(new RSTile(2844, 10053, 2), new RSTile(2846, 10049, 2));

    RSArea WHOLE_STRONGHOLD_LEVEL1 = new RSArea(new RSTile(2867, 10043, 1), new RSTile(2822, 10116, 1));
    RSArea DOOR_AREA = new RSArea(new RSTile(2847, 10108, 1), new RSTile(2846, 10105, 1));
    RSArea KITCHEN_AREA = new RSArea(new RSTile(2831, 10062, 1), new RSTile(2863, 10048, 1));
    RSArea AFTER_DOOR_AREA = new RSArea(new RSTile(2848, 10111, 1), new RSTile(2857, 10103, 1));

    RSArea WHOLE_STRONGHOLD_LEVEL_0 = new RSArea(new RSTile(2820, 10112, 0), new RSTile(2873, 10046, 0));
    RSArea OUTSIDE_CELL_2 = new RSArea(new RSTile(2832, 10085, 0), new RSTile(2835, 10088, 0));
    RSArea DUNSTAN_AREA = new RSArea(new RSTile(2917, 3577, 0), new RSTile(2923, 3572, 0));

    RSTile[] PATH_TO_START_PRAYER_AREA = new RSTile[]{new RSTile(2908, 3654, 0), new RSTile(2908, 3657, 0), new RSTile(2909, 3660, 0), new RSTile(2910, 3663, 0), new RSTile(2911, 3666, 0), new RSTile(2912, 3669, 0), new RSTile(2913, 3672, 0), new RSTile(2913, 3675, 0), new RSTile(2914, 3678, 0), new RSTile(2914, 3681, 0), new RSTile(2914, 3684, 0), new RSTile(2913, 3687, 0)};
    RSTile[] PATH_TO_STOP_PRAY_AREA = new RSTile[]{new RSTile(2913, 3687, 0), new RSTile(2912, 3690, 0), new RSTile(2910, 3693, 0), new RSTile(2908, 3696, 0), new RSTile(2905, 3699, 0), new RSTile(2902, 3700, 0), new RSTile(2899, 3701, 0), new RSTile(2896, 3702, 0), new RSTile(2893, 3702, 0), new RSTile(2889, 3702, 0), new RSTile(2886, 3702, 0), new RSTile(2883, 3700, 0), new RSTile(2880, 3697, 0), new RSTile(2879, 3694, 0), new RSTile(2876, 3693, 0), new RSTile(2873, 3690, 0)};
    RSTile[] PATH_TO_STRONHOLD = new RSTile[]{new RSTile(2873, 3690, 0), new RSTile(2870, 3687, 0), new RSTile(2868, 3684, 0), new RSTile(2868, 3681, 0), new RSTile(2868, 3678, 0), new RSTile(2868, 3675, 0), new RSTile(2868, 3672, 0), new RSTile(2868, 3669, 0), new RSTile(2865, 3666, 0), new RSTile(2862, 3663, 0), new RSTile(2859, 3663, 0), new RSTile(2856, 3665, 0), new RSTile(2853, 3668, 0), new RSTile(2851, 3671, 0), new RSTile(2851, 3674, 0), new RSTile(2850, 3677, 0), new RSTile(2853, 3681, 0), new RSTile(2857, 3685, 0), new RSTile(2858, 3688, 0), new RSTile(2858, 3691, 0), new RSTile(2855, 3693, 0), new RSTile(2852, 3694, 0), new RSTile(2849, 3694, 0), new RSTile(2846, 3694, 0), new RSTile(2843, 3691, 0), new RSTile(2840, 3690, 0)};

    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.STAFF_OF_AIR, 1, 200),
                    new GEItem(ItemID.MIND_RUNE, 400, 20),
                    new GEItem(ItemID.FIRE_RUNE, 1200, 20),
                    new GEItem(ItemID.LOBSTER, 20, 60),
                    new GEItem(ItemID.CLIMBING_BOOTS, 1, 500),
                    new GEItem(ItemID.COMBAT_BRACELET[2], 1, 20),
                    new GEItem(ItemID.GAMES_NECKLACE[0], 1, 70),
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
        BankManager.depositEquipment();
        BankManager.checkCombatBracelet();
        BankManager.checkEquippedGlory();
        BankManager.depositAll(true);
        BankManager.withdraw(400, true, ItemID.MIND_RUNE);
        BankManager.withdraw(1, true, ItemID.STAFF_OF_AIR);
        BankManager.withdraw(2, true, ItemID.STAMINA_POTION[0]);
        BankManager.withdraw(1200, true, ItemID.FIRE_RUNE);
        BankManager.withdraw(18, true, ItemID.LOBSTER);
        BankManager.withdraw(1, true, ItemID.GAMES_NECKLACE[0]);
        BankManager.withdraw(1, true, CLIMBING_BOOTS);
        BankManager.close(true);
        Utils.equipItem(CLIMBING_BOOTS);
        Utils.equipItem(ItemID.STAFF_OF_AIR);
    }

    public void startQuest() {
        cQuesterV2.status = "Starting Quest";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(START_AREA);
        if (NpcChat.talkToNPC("Denulth")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("How goes your Fight with the trolls?");
            NPCInteraction.handleConversation("Is there anything I can do to help?");
            NPCInteraction.handleConversation("I'll get Godric back!");
            NPCInteraction.handleConversation();
        }
    }

    public void drinkStamina() {
        if (Game.getSetting(1575) == 0)
            Utils.drinkPotion(ItemID.STAMINA_POTION);
    }

    public void step2() {
        if (!Equipment.isEquipped(CLIMBING_BOOTS)) {
            buyItems();
            getItems();
        }
        cQuesterV2.status = "Going to Dad";
        General.println("[Debug]: " + cQuesterV2.status);
        if (!BEFORE_ROCKS.contains(Player.getPosition()) && !AFTER_ROCKS.contains(Player.getPosition())
                && !AFTER_ROCKS_2_TO_ROCKS_3.contains(Player.getPosition())
                && !AFTER_ROCKS_3_TO_GATE.contains(Player.getPosition())
                && !DAD_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Going to First set of Rocks";
            General.println("[Debug]: " + cQuesterV2.status);

            if (PathingUtil.walkToArea(PATH_STEP_1))
                Timer.waitCondition(() -> !Player.isMoving(), 15000);

            if (Walking.walkPath(PATH_TO_ROCKS))
                Timer.waitCondition(() -> !Player.isMoving(), 15000);

            Utils.idle(2200, 5000);
        }
        if (BEFORE_ROCKS.contains(Player.getPosition())) {
            cQuesterV2.status = "Climbing rocks";
            General.println("[Debug]: " + cQuesterV2.status);
            if (Utils.clickObj(ROCK_ID, "Climb")) {
                Timer.waitCondition(() -> AFTER_ROCKS.contains(Player.getPosition()), 12000);
                Utils.idle(2200, 5000);
            }
        }
        if (AFTER_ROCKS.contains(Player.getPosition()) && !AFTER_ROCKS_2_TO_ROCKS_3.contains(Player.getPosition())) {
            cQuesterV2.status = "Going to second set of Rocks";
            General.println("[Debug]: " + cQuesterV2.status);
            if (Walking.walkPath(PATH_TO_ROCKS_2)) {
                Timer.waitCondition(() -> !Player.isMoving(), 15000);
                Utils.idle(2000, 6000);
            }

            if (Utils.clickObj("Rocks", "Climb")) {
                Timer.waitCondition(() -> AFTER_ROCKS_2_TO_ROCKS_3.contains(Player.getPosition()), 12000);
                Utils.idle(2000, 6000);
            }
        }
        if (AFTER_ROCKS_2_TO_ROCKS_3.contains(Player.getPosition())) {
            cQuesterV2.status = "Going to third set of Rocks";
            General.println("[Debug]: " + cQuesterV2.status);
            if (PathingUtil.localNavigation(new RSTile(2877, 3622, 0)))
                PathingUtil.movementIdle();

            if (Utils.clickObj("Rocks", "Climb")) {
                Timer.waitCondition(() -> AFTER_ROCKS_2_TO_ROCKS_3.contains(Player.getPosition()), 12000);
                Utils.idle(2000, 6000);
            }
        }
        if (AFTER_ROCKS_3_TO_GATE.contains(Player.getPosition())) {
            cQuesterV2.status = "Going to gate.";
            General.println("[Debug]: " + cQuesterV2.status);
            if (PathingUtil.localNavigation(BEFORE_ARENA_GATE))
                PathingUtil.movementIdle();

            if (Utils.clickObj("Arena Entrance", "Open")) {
                Timer.waitCondition(() -> SAFE_AREA.contains(Player.getPosition()), 12000);
                Utils.idle(500, 4000);
            }
        }
        if (!Autocast.isAutocastEnabled(Autocast.FIRE_STRIKE))
            Autocast.enableAutocast(Autocast.FIRE_STRIKE);

        if (SAFE_AREA.contains(Player.getPosition()) && Autocast.isAutocastEnabled(Autocast.FIRE_STRIKE)) {
            cQuesterV2.status = "Fighting Dad";
            General.println("[Debug]: " + cQuesterV2.status);
            NPCInteraction.handleConversation();
            if (Utils.clickNPC("Dad", "Attack")) {
                NPCInteraction.handleConversation();
                Timer.waitCondition(() -> Combat.isUnderAttack() || !SAFE_AREA.contains(Player.getPosition()), 3500, 5000);
                cQuesterV2.status = "Waiting...";
                Timer.waitCondition(() -> !Combat.isUnderAttack() || !SAFE_AREA.contains(Player.getPosition()), 60000);
            }
            if (!SAFE_AREA.contains(Player.getPosition())) {
                Walking.blindWalkTo(new RSTile(2987, 3618, 0));
                Utils.idle(2000, 6000);
            }
            if (!Combat.isUnderAttack()) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("I'll be going now.");
                NPCInteraction.handleConversation();
            }
        }
    }

    public void step3() {
        if (SAFE_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Going to Cave";
            General.println("[Debug]: " + cQuesterV2.status);
            if (Walking.walkPath(PATH_TO_ARENA_EXIT))
                PathingUtil.movementIdle();

            if (Utils.clickObj("Arena Exit", "Open"))
                Timer.waitCondition(() -> AFTER_ARENA_EXIT.contains(Player.getPosition()), 5000, 7000);
        }

        if (AFTER_ARENA_EXIT.contains(Player.getPosition())) {
            if (Walking.walkPath(PATH_TO_CAVE_ENTRANCE))
                PathingUtil.movementIdle();
            if (Utils.clickObj(3757, "Enter"))
                Timer.waitCondition(() -> INSIDE_CAVE.contains(Player.getPosition()), 6000, 8000);
        }

        if (INSIDE_CAVE.contains(Player.getPosition())) {
            cQuesterV2.status = "Leaving cave";
            General.println("[Debug]: " + cQuesterV2.status);
            if (PathingUtil.localNavigation(CAVE_EXIT))
                PathingUtil.movementIdle();

            if (Utils.clickObj(3758, "Exit"))
                Timer.abc2WaitCondition(() -> !INSIDE_CAVE.contains(Player.getPosition()), 6000, 8000);
        }

    }


    public void step4() {
        if (!WHOLE_STRONGHOLD_LEVEL2.contains(Player.getPosition()) && Inventory.find(PRISON_KEY).length < 1 && !WHOLE_STRONGHOLD_LEVEL1.contains(Player.getPosition())) {
            cQuesterV2.status = "Going to stronghold";
            General.println("[Debug]: " + cQuesterV2.status);

            if (Walking.walkPath(PATH_TO_START_PRAYER_AREA))
                PathingUtil.movementIdle();

            if (Prayer.getPrayerPoints() > 0)
                Prayer.enable(Prayer.PRAYERS.PROTECT_FROM_MISSILES);

            drinkStamina();

            if (Walking.walkPath(PATH_TO_STOP_PRAY_AREA))
                PathingUtil.movementIdle();

            Prayer.disable(Prayer.PRAYERS.PROTECT_FROM_MISSILES);

            if (Walking.walkPath(PATH_TO_STRONHOLD))
                PathingUtil.movementIdle();
        }

        if (BEFORE_STRONGHOLD.contains(Player.getPosition()) && Inventory.find(PRISON_KEY).length < 1) {
            if (Objects.findNearest(15, 3771).length > 0)
                if (Utils.clickObj(3771, "Enter"))
                    Timer.waitCondition(() -> WHOLE_STRONGHOLD_LEVEL2.contains(Player.getPosition()), 5000, 7000);
        }

        if (WHOLE_STRONGHOLD_LEVEL2.contains(Player.getPosition()) && Inventory.find(PRISON_KEY).length < 1) {
            cQuesterV2.status = "Killing general for key";
            General.println("[Debug]: " + cQuesterV2.status);

            if (Prayer.getPrayerPoints() > 0)
                Prayer.enable(Prayer.PRAYERS.PROTECT_FROM_MELEE);

            if (Walking.blindWalkTo(STRONGHOLD_SOUTHWEST_DOOR.getRandomTile()))
                PathingUtil.movementIdle();

            if (Walking.blindWalkTo(new RSTile(2821, 10070, 2)))
                PathingUtil.movementIdle();

            if (Utils.clickNPC("Troll General", "Attack")) {
                Timer.waitCondition(() -> Combat.isUnderAttack(), 10000);
                Timer.waitCondition(() -> !Combat.isUnderAttack() && GroundItems.find(PRISON_KEY).length > 0, 120000);
                Utils.idle(500, 3000);
            }

            Prayer.disable(Prayer.PRAYERS.PROTECT_FROM_MELEE);

            RSGroundItem[] prisionKey = GroundItems.find(PRISON_KEY);
            if (prisionKey.length > 0) {
                cQuesterV2.status = "Looting key";
                General.println("[Debug]: " + cQuesterV2.status);
                if (AccurateMouse.click(prisionKey[0], "Take"))
                    Timer.waitCondition(() -> Inventory.find(PRISON_KEY).length > 0, 12000);
            }
        }
        if (WHOLE_STRONGHOLD_LEVEL2.contains(Player.getPosition()) && Inventory.find(PRISON_KEY).length > 0) {
            cQuesterV2.status = "Going downstairs";
            General.println("[Debug]: " + cQuesterV2.status);

            if (Walking.blindWalkTo(STAIRS_AREA.getRandomTile()))
                PathingUtil.movementIdle();

            if (Utils.clickObj("Stone Staircase", "Climb-down"))
                Timer.waitCondition(() -> WHOLE_STRONGHOLD_LEVEL1.contains(Player.getPosition()), 15000);
        }
    }


    public void step5() {
        if (WHOLE_STRONGHOLD_LEVEL1.contains(Player.getPosition()) && Inventory.find(PRISON_KEY).length > 0) {
            cQuesterV2.status = "Walking to North Door, floor 1";
            if (KITCHEN_AREA.contains(Player.getPosition())) {
                if (Walking.blindWalkTo(new RSTile(2837, 10063, 1)))
                    PathingUtil.movementIdle();

                if (Utils.clickObj(3776, "Open"))
                    Utils.idle(2000, 4000);
            }

            PathingUtil.localNavigation(DOOR_AREA);
            if (Utils.clickObj(3780, "Unlock"))
                Utils.idle(2000, 6000);

        }
    }

    //couting cells from north to south

    public void freePrisoners() {
        if (AFTER_DOOR_AREA.contains(Player.getPosition()))
            if (Utils.clickObj("Stone Staircase", "Climb-down"))
                Timer.waitCondition(() -> WHOLE_STRONGHOLD_LEVEL_0.contains(Player.getPosition()), 15000);

        if (WHOLE_STRONGHOLD_LEVEL_0.contains(Player.getPosition()) && Inventory.find(CELL_KEY_2).length < 1) {
            if (PathingUtil.localNavigation(OUTSIDE_CELL_2))
                PathingUtil.movementIdle();
            cQuesterV2.status = "Getting key #2";
            if (Inventory.isFull())
                EatUtil.eatFood();

            if (Skills.getActualLevel(Skills.SKILLS.THIEVING) >= 30) {
                if (Utils.clickNPC("Berry", "Pickpocket"))
                    Timer.waitCondition(() -> Combat.isUnderAttack() || Inventory.find(CELL_KEY_2).length > 0, 9000);
            } else {
                if (Utils.clickNPC("Berry", "Attack"))
                    Timer.waitCondition(() -> Combat.isUnderAttack(), 7000);
            }
            if (Combat.isUnderAttack()) {
                if (Walking.blindWalkTo(new RSTile(2830, 10086, 0)))
                    PathingUtil.movementIdle();

                if (Utils.clickNPC("Berry", "Attack"))
                    Timer.waitCondition(() -> GroundItems.find(CELL_KEY_2).length > 0, 45000);
            }
        }
        if (WHOLE_STRONGHOLD_LEVEL_0.contains(Player.getPosition()) && Inventory.find(CELL_KEY_1).length < 1) {
            if (Inventory.isFull())
                EatUtil.eatFood();
            cQuesterV2.status = "Getting key #1";

            if (Skills.getActualLevel(Skills.SKILLS.THIEVING) >= 30) {
                if (Utils.clickNPC("Twig", "Pickpocket"))
                    Timer.waitCondition(() -> Combat.isUnderAttack() || Inventory.find(CELL_KEY_1).length > 0, 12000);
            } else {
                if (Utils.clickNPC("Twig", "Attack"))
                    Timer.waitCondition(() -> Combat.isUnderAttack(), 7000);
            }
            if (Combat.isUnderAttack()) {
                if (Walking.blindWalkTo(new RSTile(2830, 10086, 0)))
                    PathingUtil.movementIdle();

                if (Utils.clickNPC("Twig", "Attack"))
                    Timer.waitCondition(() -> GroundItems.find(CELL_KEY_1).length > 0, 45000);
            }
        }
        if (WHOLE_STRONGHOLD_LEVEL_0.contains(Player.getPosition()) && Inventory.find(CELL_KEY_1).length > 0 && Inventory.find(CELL_KEY_2).length > 0) {
            if (Utils.useItemOnObject(CELL_KEY_2, CELL_DOOR_2_ID)) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
        }
        if (WHOLE_STRONGHOLD_LEVEL_0.contains(Player.getPosition()) && Inventory.find(CELL_KEY_1).length > 0) {
            if (Utils.useItemOnObject(CELL_KEY_1, CELL_DOOR_1_ID)) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
            cQuesterV2.status = "Leaving";
            if (Walking.blindWalkTo(new RSTile(2826, 10051, 0)))
                PathingUtil.movementIdle();

            if (Utils.clickObj("Exit", "Open"))
                Utils.modSleep();
        }
    }


    public void step7() {
        cQuesterV2.status = "Finishing Quest";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(DUNSTAN_AREA);
        if (NpcChat.talkToNPC("Dunstan")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }
    }


    int GAME_SETTING = 317;

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
        if (!checkRequirements()){
            Log.log("[Debug]; Missing a requirement for Troll Stronghold (death plateau & 15 agility)");
            cQuesterV2.taskList.remove(this);
            return;
        }
        if (Game.getSetting(317) == 0) {
            buyItems();
            getItems();
            startQuest();
        }
        if (Game.getSetting(317) == 10) {
            step2();
        }
        if (Game.getSetting(317) == 20) {
            step3();
            step4();
            step5();
        }
        if (Game.getSetting(317) == 30) {
            freePrisoners();
        }
        if (Game.getSetting(317) == 40) {
            step7();
        }
        if (Game.getSetting(317) == 50) {
            Utils.closeQuestCompletionWindow();
            cQuesterV2.taskList.remove(this);
        }
    }

    @Override
    public String questName() {
        return "Troll Stronghold";
    }


    public List<Requirement> getGeneralRequirements() {
        ArrayList<Requirement> req = new ArrayList<>();
        req.add(new SkillRequirement(Skills.SKILLS.AGILITY, 15));
        return req;
    }

    @Override
    public boolean checkRequirements() {
        SkillRequirement agil =   new SkillRequirement(Skills.SKILLS.AGILITY, 15);
        return Game.getSetting(QuestVarPlayer.QUEST_DEATH_PLATEAU.getId()) >= 80 && agil.check();
    }

}
