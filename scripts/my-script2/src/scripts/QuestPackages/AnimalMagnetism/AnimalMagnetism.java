package scripts.QuestPackages.AnimalMagnetism;

import dax.walker.utils.AccurateMouse;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.Log;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.AscentOfArceuus.AscentOfArceuus;
import scripts.QuestSteps.BuyItemsStep;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.InventoryRequirement;
import scripts.Requirements.ItemReq;
import scripts.Requirements.ItemRequirement;
import scripts.Requirements.Requirement;
import scripts.Tasks.Priority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AnimalMagnetism implements QuestTask {


    private static AnimalMagnetism quest;

    public static AnimalMagnetism get() {
        return quest == null ? quest = new AnimalMagnetism() : quest;
    }


    int MITHRIL_AXE = 1355;
    int IRON_BAR = 2351;
    int GHOST_SPEAK_AMULET = 552;
    int DRAGON_BONES = 536;
    int EMPTY_BUCKET = 1925;
    int POT = 1931;
    int HAMMER = 2347;
    int HARD_LEATHER = 1743;
    int HOLY_SYMBOL = 1718;
    int POLISHED_BUTTONS = 10496;
    int ECTO_TOKEN = 4278;
    int FENKENSTRAIN_TAB = 19621;
    int VARROCK_TAB = 8007;
    int CRONE_MADE_AMULET = 10500;
    int BUCKET_OF_SLIME = 4286;
    int BONEMEAL = 4261;
    int UNDEAD_CHICKENS = 10487;
    int MAGNET = 10488;
    int BAR_MAGNET = 10489;
    int BLESSED_AXE = 10491;
    int TWIG = 10490;
    int NOTES = 10492;

    RSArea AVA_AREA = new RSArea(new RSTile(3095, 3355, 0), new RSTile(3091, 3361, 0));
    RSArea ALICE_AREA = new RSArea(new RSTile(3630, 3524, 0), new RSTile(3626, 3527, 0));
    RSArea ALICE_WALKTO_AREA = new RSArea(new RSTile(3629, 3525, 0), new RSTile(3627, 3526, 0));
    RSArea CRONE_HOUSE = new RSArea(new RSTile(3463, 3556, 0), new RSTile(3460, 3560, 0));
    RSArea TRAPDOOR_AREA = new RSArea(new RSTile(3652, 3521, 0), new RSTile(3655, 3517, 0));
    RSArea HUSBAND_AREA = new RSArea(new RSTile(3609, 3530, 0), new RSTile(3625, 3523, 0));
    RSArea HUSBAND_WALKTO_AREA = new RSArea(new RSTile(3614, 3528, 0), new RSTile(3622, 3524, 0));
    RSArea WITCH_AREA = new RSArea(new RSTile(3101, 3364, 0), new RSTile(3097, 3366, 0));
    RSArea MINE_AREA = new RSArea(new RSTile(2975, 3241, 0), new RSTile(2981, 3238, 0));
    RSArea TREE_AREA = new RSArea(new RSTile(3108, 3348, 0), new RSTile(3110, 3343, 0));
    RSArea TURAEL_AREA = new RSArea(new RSTile(2933, 3535, 0), new RSTile(2930, 3538, 0));
    RSArea SLIME_LEVEL1 = new RSArea(new RSTile(3682, 9889, 0), new RSTile(3686, 9886, 0));
    RSArea ECTOFUNTUS = new RSArea(new RSTile(3656, 3522, 0), new RSTile(3664, 3517, 0));
    RSArea BONE_CRUSHER_AREA = new RSArea(new RSTile(3656, 3526, 1), new RSTile(3663, 3523, 1));
    RSArea LARGE_TREE_AREA = new RSArea(new RSTile(3114, 3340, 0), new RSTile(3104, 3350, 0));


    public boolean checkLevel() {
        if (Skills.getActualLevel(Skills.SKILLS.SLAYER) < 18) {
            Log.log("[Debug]: Missing Slayer level of 18 for animal magnetism");
           return false;
        }
        if (Skills.getActualLevel(Skills.SKILLS.WOODCUTTING) < 35) {
            Log.log("[Debug]: Missing woodcutting level of 35 for animal magnetism");
            return false;
        }
        if (Skills.getActualLevel(Skills.SKILLS.CRAFTING) < 19) {
            Log.log("[Debug]: Missing crafting level of 19 for animal magnetism");
            return false;
        }
        return true;
    }

    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.MITHRIL_AXE, 1, 300),
                    new GEItem(ItemID.IRON_BAR, 5, 300),
                    new GEItem(ItemID.DRAGON_BONES, 5, 300),
                    new GEItem(ItemID.BUCKET, 5, 300),
                    new GEItem(ItemID.HAMMER, 1, 600),
                    new GEItem(HARD_LEATHER, 1, 300),
                    new GEItem(HOLY_SYMBOL, 1, 350),
                    new GEItem(POLISHED_BUTTONS, 1, 350),
                    new GEItem(HARD_LEATHER, 1, 50),
                    new GEItem(HARD_LEATHER, 1, 50),
                    new GEItem(POT, 5, 500),
                    new GEItem(ItemID.STAMINA_POTION[0], 3, 15),
                    new GEItem(ItemID.GAMES_NECKLACE[0], 2, 25),
                    new GEItem(ItemID.RING_OF_DUELING[0], 2, 25),
                    new GEItem(ItemID.SALVE_GRAVEYARD_TELEPORT, 6, 40),
                    new GEItem(ItemID.AMULET_OF_GLORY[2], 2, 15),
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
        if (!BankManager.checkInventoryItems(DRAGON_BONES, EMPTY_BUCKET, POT)) {
            cQuesterV2.status = "Buying Items";
            General.println("[Debug]: " + cQuesterV2.status);
            buyStep.buyItems();
        }
    }

    public void getItems() {
        if (!BankManager.checkInventoryItems(DRAGON_BONES, EMPTY_BUCKET, POT, MITHRIL_AXE)) {
            cQuesterV2.status = "Getting Items";
            General.println("[Debug]: " + cQuesterV2.status);
            General.sleep(200);
            BankManager.open(true);
            BankManager.checkEquippedGlory();
            BankManager.depositAll(true);
            BankManager.withdraw(1, true, MITHRIL_AXE);
            BankManager.withdraw(3500, true, 995);
            BankManager.withdraw(1, true, GHOST_SPEAK_AMULET);
            BankManager.withdraw(5, true, DRAGON_BONES);
            BankManager.withdraw(5, true, EMPTY_BUCKET);
            BankManager.withdraw(5, true, POT);
            BankManager.withdraw(20, true, ECTO_TOKEN);
            BankManager.withdraw(1, true, ItemID.RING_OF_DUELING[0]);
            Utils.equipItem(ItemID.RING_OF_DUELING[0]);
            BankManager.withdraw(1, true, ItemID.SALVE_GRAVEYARD_TELEPORT);
            BankManager.withdraw(2, true, ItemID.STAMINA_POTION[0]);
            BankManager.close(true);
            Utils.equipItem(GHOST_SPEAK_AMULET);
        }
    }

    public void startQuest() {
        cQuesterV2.status = "Starting Quest";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(AVA_AREA);
        if (NpcChat.talkToNPC("Ava")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("I would be happy to make your home a better place.");
            NPCInteraction.handleConversation("Yes.");
            NPCInteraction.handleConversation();
        }
    }

    public void getEctoFundus() {
        if (Inventory.find(ECTO_TOKEN).length < 1) {
            if (!SLIME_LEVEL1.contains(Player.getPosition()) && Inventory.find(EMPTY_BUCKET).length > 0 && Inventory.find(BONEMEAL).length < 1) {
                cQuesterV2.status = "Going to Ectofuntus";
                General.println("[Debug]: " + cQuesterV2.status);
                PathingUtil.walkToTile(new RSTile(3683, 9888, 0));
                Timer.waitCondition(() -> SLIME_LEVEL1.contains(Player.getPosition()), 15000, 20000);
                Utils.shortSleep();
            }
            if (SLIME_LEVEL1.contains(Player.getPosition()) && Inventory.find(EMPTY_BUCKET).length > 0 && Inventory.find(BONEMEAL).length < 1) {
                cQuesterV2.status = "Getting Slime";
                General.println("[Debug]: " + cQuesterV2.status);
                if (Utils.useItemOnObject(EMPTY_BUCKET, "Pool of Slime")) {
                    Timer.waitCondition(() -> Inventory.find(EMPTY_BUCKET).length < 1, 15000, 20000);
                    Utils.shortSleep();
                }
            }
            if (!BONE_CRUSHER_AREA.contains(Player.getPosition()) && Inventory.find(EMPTY_BUCKET).length < 1 && Inventory.find(DRAGON_BONES).length > 0) {
                cQuesterV2.status = "Going to grinder";
                General.println("[Debug]: " + cQuesterV2.status);
                PathingUtil.walkToArea(BONE_CRUSHER_AREA);
            }
            if (BONE_CRUSHER_AREA.contains(Player.getPosition()) && Inventory.find(BONEMEAL).length < 5) {
                cQuesterV2.status = "Getting bonemeal";
                General.println("[Debug]: " + cQuesterV2.status);
                if (Utils.useItemOnObject(DRAGON_BONES, "Loader"))
                    Timer.abc2WaitCondition(() -> Inventory.find(BONEMEAL).length ==5 ,
                            85000, 95000);
            }

            if (BONE_CRUSHER_AREA.contains(Player.getPosition()) && Inventory.find(DRAGON_BONES).length < 1) {
                cQuesterV2.status = "Worshiping Ectofuntus";
                General.println("[Debug]: " + cQuesterV2.status);
                PathingUtil.walkToArea(ECTOFUNTUS);
            }

            if (ECTOFUNTUS.contains(Player.getPosition()) && Inventory.find(BONEMEAL).length > 0) {
                for (int i = 0; i < 5; i++) {
                    RSItem[] invBoneMeal = Inventory.find(BONEMEAL);
                    if (AccurateMouse.click(Objects.findNearest(20, "Ectofuntus")[0], "Worship")) {
                        Timer.waitCondition(() -> Inventory.find(BONEMEAL).length < invBoneMeal.length, 4000, 9000);
                        Utils.microSleep();
                    }
                }
                Utils.equipItem(GHOST_SPEAK_AMULET);

                if (NpcChat.talkToNPC("Ghost disciple")) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                    Utils.modSleep();
                }
            }
            claimEctoTokens();
        }
    }

    public void claimEctoTokens() {
        if (Inventory.find(DRAGON_BONES).length < 1 && Inventory.find(ECTO_TOKEN).length < 1) {
            cQuesterV2.status = "Going to claim ecto-tokens";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(ECTOFUNTUS);
            Utils.equipItem(GHOST_SPEAK_AMULET);
            if (NpcChat.talkToNPC("Ghost disciple")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                Utils.modSleep();
            }
        }
    }

    public void talkToAlice() {
        if (!ALICE_AREA.contains(Player.getPosition()) && Inventory.find(ECTO_TOKEN).length > 0) {
            cQuesterV2.status = "Going to Alice";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(ALICE_WALKTO_AREA);
        } else if (Inventory.find(ECTO_TOKEN).length < 1) {
            claimEctoTokens();
        }
        if (ALICE_AREA.contains(Player.getPosition())) {
            NpcChat.talkToNPC("Alice");
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("I'm here about a quest.");
            NPCInteraction.handleConversation();
        }
    }

    public void talkToHusband() {
        if (!HUSBAND_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Going to Husband";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(HUSBAND_WALKTO_AREA);
            Timer.abc2WaitCondition(() -> !Player.isMoving(), 15000, 20000);
        }
        if (NpcChat.talkToNPC("Alice's husband")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }
    }

    public void goToCrone() {
        if (Inventory.find(CRONE_MADE_AMULET).length < 1) {
            if (!CRONE_HOUSE.contains(Player.getPosition())) {
                cQuesterV2.status = "Going to Crone";
                General.println("[Debug]: " + cQuesterV2.status);
                PathingUtil.walkToArea(CRONE_HOUSE);
            }
            if (NpcChat.talkToNPC("Old crone")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                Utils.modSleep();
            }
        }
    }

    public void talkToHusband2() {
        if (!HUSBAND_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Going to Husband";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(HUSBAND_WALKTO_AREA);
            Timer.abc2WaitCondition(() -> !Player.isMoving(), 15000, 20000);
        }
        if (HUSBAND_AREA.contains(Player.getPosition())) {
            NpcChat.talkToNPC("Alice's husband");
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Okay, you need it more than I do, I suppose.");
            NPCInteraction.handleConversation();

        }
    }

    public void talkToHusband3() {
        if (Inventory.find(UNDEAD_CHICKENS).length < 2) {
            if (!HUSBAND_AREA.contains(Player.getPosition())) {
                cQuesterV2.status = "Animal Magnetism: Going to Husband";
                General.println("[Debug]: " + cQuesterV2.status);
                PathingUtil.walkToArea(HUSBAND_WALKTO_AREA);
                Timer.abc2WaitCondition(() -> !Player.isMoving(), 15000, 20000);
            }

            if (NpcChat.talkToNPC("Alice's husband")) {
                NPCInteraction.waitForConversationWindow();
                 NPCInteraction.handleConversation(); // need this
                NPCInteraction.handleConversation("Could I buy those chickens now, then?",
                        "Could I buy 2 chickens?");
                NPCInteraction.handleConversation("Could I buy 2 chickens?");
                NPCInteraction.handleConversation();
            }
        }
    }

    public void cutscene() {
        Utils.cutScene();
        if (NpcChat.talkToNPC("Alice's husband")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            Utils.cutScene();
            Utils.modSleep();
        }
    }

    public void getItems2() {

        if (Inventory.find(UNDEAD_CHICKENS).length > 0) {
            cQuesterV2.status = "Getting Items";
            General.println("[Debug]: " + cQuesterV2.status);
            BankManager.open(true);
            BankManager.depositAll(true);
            BankManager.withdraw(1, true, MITHRIL_AXE);
            BankManager.withdraw(2500, true, 995);
            BankManager.withdraw(1, true, GHOST_SPEAK_AMULET);
            BankManager.withdraw(5, true, IRON_BAR);
            BankManager.withdraw(1, true, HAMMER);
            BankManager.withdraw(1, true, HOLY_SYMBOL);
            BankManager.withdraw(1, true, POLISHED_BUTTONS);
            BankManager.withdraw(1, true, HARD_LEATHER);
            BankManager.withdraw(2, true, UNDEAD_CHICKENS);
            BankManager.withdraw(20, true, ECTO_TOKEN);
            BankManager.withdraw(1, true, ItemID.SKILLS_NECKLACE[0]);
            BankManager.withdraw(2, true, ItemID.STAMINA_POTION[0]);
            BankManager.withdraw(2, true, ItemID.AMULET_OF_GLORY[2]);
            BankManager.withdraw(1, true, ItemID.GAMES_NECKLACE[0]);
            BankManager.close(true);
        }
    }

    public void talkToAva2() {
        if (Inventory.find(UNDEAD_CHICKENS).length > 0) {
            cQuesterV2.status = "Going to Ava";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(AVA_AREA);

            if (NpcChat.talkToNPC("Ava")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
        }
    }

    public void talkToAva2b() {
        cQuesterV2.status = "Going to Ava";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(AVA_AREA);
        if (NpcChat.talkToNPC("Ava")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }
    }

    public void craftMagnet() {
        if (!MINE_AREA.contains(Player.getPosition()) && Inventory.find(BAR_MAGNET).length < 1) {
            cQuesterV2.status = "Crafting Magnet";
            General.println("[Debug]: " + cQuesterV2.status);
            if (Inventory.find(ItemID.SKILLS_NECKLACE[0]).length > 0) {
                AccurateMouse.click(Inventory.find(ItemID.SKILLS_NECKLACE[0])[0], "Rub");
                Timer.waitCondition(() -> Interfaces.get(187, 3, 2) != null, 5000, 8000);
                if (Interfaces.get(187, 3, 2) != null) {
                    if (Interfaces.get(187, 3, 2).click())
                        Utils.idle(2500, 5000);
                }
            }
            PathingUtil.walkToArea(MINE_AREA);
        }
        if (MINE_AREA.contains(Player.getPosition()) && Inventory.find(BAR_MAGNET).length < 1) {
            Walking.blindWalkTo(new RSTile(2978, 3236, 0));

            Utils.idle(500, 1000);
            Timer.waitCondition(() -> !Player.isMoving(), 9000, 15000);
            Walking.blindWalkTo(new RSTile(2979, 3240, 0));

            Utils.idle(500, 1500);
            Timer.waitCondition(() -> !Player.isMoving(), 9000, 15000);

            if (Utils.useItemOnItem(HAMMER, MAGNET))
                Timer.slowWaitCondition(() -> Inventory.find(BAR_MAGNET).length > 0, 7000, 10000);

        }
    }

    public void getMagnet() {
        cQuesterV2.status = "Getting Magnet";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(WITCH_AREA);
        if (NpcChat.talkToNPC("Witch")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            NPCInteraction.handleConversation();
        }
    }

    public void attemptToCutTree() {
        if (!LARGE_TREE_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Going to cut tree";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(TREE_AREA);
        }
        if (LARGE_TREE_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Attempting to cut tree";
            General.println("[Debug]: " + cQuesterV2.status);
            RSNPC[] tree = NPCs.findNearest(4418);
            if (tree.length > 0) {
                AccurateMouse.click(tree[0], "Chop");
                Timer.waitCondition(()-> Player.getAnimation() != -1, 4500,6500);
            }
            Utils.idle(3000, 5000);
            Walking.blindWalkTo(new RSTile(3115, 3350, 0)); // prevents getting stuck on tree

            Utils.idle(2000, 3000);
        }
    }

    public void cutTree() {
        if (!LARGE_TREE_AREA.contains(Player.getPosition()) && Inventory.find(TWIG).length < 1) {
            cQuesterV2.status = "Going to cut tree";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(TREE_AREA);
        }
        if (LARGE_TREE_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Attempting to cut tree";
            General.println("[Debug]: " + cQuesterV2.status);
            RSNPC[] tree = NPCs.findNearest(4418);
            AccurateMouse.click(tree[0], "Chop");

            Utils.idle(3000, 5000);
            if (Inventory.find(TWIG).length > 0) {
                Walking.blindWalkTo(new RSTile(3115, 3350, 0)); // prevents getting stuck on tree
                Utils.idle(2000, 3000);
            }
        }
    }

    public void goToTurael() {
        if (!BankManager.checkInventoryItems(MITHRIL_AXE, HOLY_SYMBOL)) {
            cQuesterV2.status = "Missing an item, rebuying it.";
            General.println("[Debug]: " + cQuesterV2.status);
            buyItems();
            getItems();
        }
        cQuesterV2.status = "Going to Turael";
        General.println("[Debug]: " + cQuesterV2.status);
        if (!TURAEL_AREA.contains(Player.getPosition())) {
            PathingUtil.walkToTile(new RSTile(2931, 3536, 0));
            Timer.abc2WaitCondition(() -> TURAEL_AREA.contains(Player.getPosition()), 9000, 15000);
        }
        if (NpcChat.talkToNPC("Turael")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("I'm here about a quest.");
            NPCInteraction.handleConversation();
        }
        if (NpcChat.talkToNPC("Turael")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Hello, I'm here about those trees again.");
            NPCInteraction.handleConversation("I'd love one, thanks.");
            NPCInteraction.handleConversation();
        }
    }

    public void talkToAva3() {
        cQuesterV2.status = "Going to Ava";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(AVA_AREA);
        if (NpcChat.talkToNPC("Ava")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            NPCInteraction.handleConversation();
            NPCInteraction.handleConversation();
        }
    }

    public void lookAtNotes() {
        if (Inventory.find(NOTES).length > 0) {
            AccurateMouse.click(Inventory.find(NOTES)[0], "Translate");
            Timer.abc2WaitCondition(() -> Interfaces.get(480) != null, 5000, 9000);
            if (Interfaces.get(480) != null) {
                if (Interfaces.get(480, 23).isHidden()) {
                    Interfaces.get(480, 24).click();

                    Utils.idle(800, 2500);
                }
                if (Interfaces.get(480, 29).isHidden()) {
                    Interfaces.get(480, 29).click();

                    Utils.idle(800, 2500);
                }
                if (Interfaces.get(480, 32).isHidden()) {
                    Interfaces.get(480, 32).click();

                    Utils.idle(800, 2500);
                }
                if (Interfaces.get(480, 38).isHidden()) {
                    Interfaces.get(480, 38).click();

                    Utils.idle(800, 2000);
                }
                if (Interfaces.get(480, 41).isHidden()) {
                    Interfaces.get(480, 41).click();

                    Utils.idle(800, 2000);
                }
                if (Interfaces.get(480, 44).isHidden()) {
                    Interfaces.get(480, 44).click();

                    Utils.idle(800, 2000);
                }
                Interfaces.get(480, 4).click();

                Utils.idle(800, 2000);
            }
        }
    }

    @Override
    public void execute() {

        General.println("Game Setting = " + Game.getSetting(939));
        if (Game.getSetting(939) == 0) {
            buyItems();
            getItems();
            startQuest();
        }
        if (Game.getSetting(939) == 10) {
            getEctoFundus();
            talkToAlice();
            talkToHusband();
        }
        if (Game.getSetting(939) == 20) {
            getEctoFundus();
            talkToAlice();
        }
        if (Game.getSetting(939) == 30) {
            talkToHusband();
        }
        if (Game.getSetting(939) == 40) {
            talkToAlice();
        }
        if (Game.getSetting(939) == 50) {
            talkToHusband();
        }
        if (Game.getSetting(939) == 60) {
            talkToAlice();
        }
        if (Game.getSetting(939) == 70) {
            goToCrone();
        }
        if (Game.getSetting(939) == 73) {
            goToCrone();
        }
        if (Game.getSetting(939) == 76) {
            talkToHusband2();
        }
        if (Game.getSetting(939) == 80) {
            talkToHusband();
        }
        if (Game.getSetting(939) == 90) {
            cutscene();
        }
        if (Game.getSetting(939) == 100) {
            talkToHusband3();
            getItems2();
            talkToAva2();
        }
        if (Game.getSetting(939) == 120) {
            getMagnet();
        }
        if (Game.getSetting(939) == 130) {
            getMagnet();
        }
        if (Game.getSetting(939) == 140) {
            craftMagnet();
            if (Inventory.find(BAR_MAGNET).length > 0) {
                talkToAva2b();
            }
        }
        if (Game.getSetting(939) == 150) {
            attemptToCutTree();
        }
        if (Game.getSetting(939) == 160) {
            talkToAva2();
            goToTurael();
        }
        if (Game.getSetting(939) == 170) {
            goToTurael();
        }
        if (Game.getSetting(939) == 180) {
            cutTree();
            if (Inventory.find(TWIG).length > 0) {
                talkToAva3();
            }
        }
        if (Game.getSetting(939) == 190) {
            talkToAva3();
        }
        if (Game.getSetting(939) == 200) {
            talkToAva3();
            lookAtNotes();
        }
        if (Game.getSetting(939) == 210) {
            talkToAva3();
        }
        if (Game.getSetting(939) == 220) {
            Utils.useItemOnItem(HARD_LEATHER, POLISHED_BUTTONS);

            Utils.idle(300, 1200);
        }
        if (Game.getSetting(939) == 230) {
            talkToAva3();
        }
        if (Game.getSetting(939) == 240) {
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
        return "Animal Magnetism";
    }

    @Override
    public boolean checkRequirements() {
        return Skills.SKILLS.SLAYER.getActualLevel() >= 18 && checkLevel();
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
