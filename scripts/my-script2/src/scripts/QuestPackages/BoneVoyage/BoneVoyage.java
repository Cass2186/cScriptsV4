package scripts.QuestPackages.BoneVoyage;

import dax.walker.utils.camera.DaxCamera;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.types.*;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.XMarksTheSpot.XMarksTheSpot;
import scripts.QuestSteps.BuyItemsStep;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.ItemRequirement;
import scripts.Requirements.Requirement;
import scripts.Tasks.Priority;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class BoneVoyage implements QuestTask {
    private static BoneVoyage quest;

    public static BoneVoyage get() {
        return quest == null ? quest = new BoneVoyage() : quest;
    }

    /**
     * Items
     */
    int SPECIMEN_ROCKS = 11175;
    int POTTERY = 11178;
    int OLD_SYMBOL = 11182;
    int ANCIENT_SYMBOL = 11181;
    int ANCIENT_COIN = 11180;
    int OLD_COIN = 11179;
    int[] ANTIQUE_LAMP = {11185, 11186};
    int TROWEL = 676;
    int SPECIMEN_BRUSH = 670;
    int ROCK_PICK = 675;
    int CLEAN_NECKLACE = 11195;
    int OLD_VASE = 11183;
    int JEWLERY = 11177;
    int BONES = 526;
    int BROKEN_GLASS = 1469;
    int MITHRIL_ORE = 447;
    int BIG_BONES = 532;
    int IRON_ARROWHEADS = 40;
    int BOWL = 1923;
    int WOODEN_STOCK = 9440;
    int IRON_ORE = 440;
    int COAL = 453;
    int TIN_ORE = 438;
    int OPAL = 1625;
    int BROKEN_ARROW = 687;
    int POT = 1931;
    int LEATHER_GLOVES = 1059;
    int LEATHER_BOOTS = 1061;
    int CROSSBOW_LIMBS = 9420;
    int UNCUT_OPAL = 1627;
    int IRON_DART = 807;
    int VODKA = 2015;
    int DIGSITE_TELEPORT = 12403;
    int MARRENTIL_UNF = 93;
    int LUMBERYARD_TELEPORT = 12642;
    int BONE_CHARM = 21530;
    int SEALEGS_POTION = 21531;

    /**
     * Objects
     */
    int TOOLS = 24535;
    int SPECIMEN_ROCK_PILE = 24557;
    int SPECIMEN_TABLE = 24556;
    int STORAGE_CRATE = 24534;
    int POTTERY_CASE = 12139;
    int OLD_SYMBOL_CASE = 12137;
    int ANCIENT_SYMBOL_CASE = 12138;
    int ANCIENT_COIN_CASE = 12234;
    int OLD_COIN_CASE = 15484;
    int GATE = 24536;

    int POSSIBLE_QUEST_KUDOS = 0;
    /**
     * NPCs
     */
    int HISTORIAN_MINAS = 1902;
    int SINCO_DOAR = 1907;

    RSArea MINAS_AREA = new RSArea(new RSTile(3267, 3453, 1), new RSTile(3257, 3455, 1));
    RSArea CLEANING_AREA = new RSArea(new RSTile(3267, 3442, 0), new RSTile(3255, 3446, 0));
    RSArea DISPLAY_AREA = new RSArea(new RSTile(3264, 3447, 0), new RSTile(3257, 3455, 0));
    RSArea START_AREA = new RSArea(new RSTile(3254, 3447, 0), new RSTile(3259, 3450, 0));

    RSArea IN_FRONT_OF_BARGE = new RSArea(new RSTile(3363, 3446, 0), new RSTile(3359, 3444, 0));
    RSArea VARROCK_SAWMILL_OPERATOR_AREA = new RSArea(new RSTile(3301, 3491, 0), new RSTile(3304, 3489, 0));
    RSArea OUTSIDE_WOODCUTTING_GUILD = new RSArea(new RSTile(1658, 3506, 0), new RSTile(1660, 3503, 0));
    RSArea BARGE = new RSArea(new RSTile(3366, 3450, 1), new RSTile(3357, 3455, 1));
    RSArea RUSTY_ANCHOR = new RSArea(new RSTile(3046, 3258, 0), new RSTile(3051, 3256, 0));
    RSArea MINE_AREA = new RSArea(new RSTile(3358, 3507, 0), new RSTile(3363, 3504, 0));
    RSArea POTION_GUY_AREA = new RSArea(new RSTile(3192, 3403, 0), new RSTile(3196, 3405, 0));


    public void depositItemsInitial() {
        if (CLEANING_AREA.contains(Player.getPosition()) &&
                Inventory.getAll().length > 16) {

        }

    }

    public int determinePossibleKudos() {
        POSSIBLE_QUEST_KUDOS = 0;
        if (Utils.getVarBitValue(2561) == 3) {
            General.println("[Debug]: Demon slayer is done: +5");
            POSSIBLE_QUEST_KUDOS = POSSIBLE_QUEST_KUDOS + 5;
        }
        if (Game.getSetting(14) == 7) {
            General.println("[Debug]: Merlin's Crystal is done: +5");
            POSSIBLE_QUEST_KUDOS = POSSIBLE_QUEST_KUDOS + 5;
        }
        if (Game.getSetting(63) == 6) {
            General.println("[Debug]: Rune Mysteries is done: +5");
            POSSIBLE_QUEST_KUDOS = POSSIBLE_QUEST_KUDOS + 5;
        }
        if (Game.getSetting(223) == 9) {
            General.println("[Debug]: Hazeel Cult is done: +5");
            POSSIBLE_QUEST_KUDOS = POSSIBLE_QUEST_KUDOS + 5;
        }
        if (Game.getSetting(302) >= 60) {
            General.println("[Debug]: Priest in Peril is done: +5");
            POSSIBLE_QUEST_KUDOS = POSSIBLE_QUEST_KUDOS + 5;
        }
        if (Game.getSetting(150) == 160) {
            General.println("[Debug]: Grand Tree is done: +5");
            POSSIBLE_QUEST_KUDOS = POSSIBLE_QUEST_KUDOS + 5;
        }
        General.println("[Debug]: Total Quest Kudos: " + POSSIBLE_QUEST_KUDOS);
        return POSSIBLE_QUEST_KUDOS;
    }

    boolean OLD_COIN_PLACED = false;
    boolean POTTERY_PLACED = false;
    boolean ANCIENT_SYMBOL_PLACED = false;
    boolean ANCIENT_COIN_PLACED = false;
    boolean OLD_SYMBOL_PLACED = false;

    public void getModelLengths() {
        if (getLength(OLD_SYMBOL_CASE) != 882) {
            OLD_SYMBOL_PLACED = true;
        }
        if (getLength(ANCIENT_SYMBOL_CASE) != 882) {
            ANCIENT_SYMBOL_PLACED = true;
        }
        if (getLength(OLD_COIN_CASE) != 882) {
            OLD_COIN_PLACED = true;
        }


    }

    public int getLength(int id) {
        RSObject[] obj = Objects.findNearest(20, id);
        return obj.length > 0 ? obj[0].getModel().getPoints().length : -1;
    }

    public void getQuestKudos() {
        determinePossibleKudos();
        if (!CLEANING_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Getting quest Kudos";
            PathingUtil.walkToArea(MINAS_AREA);
            if (NpcChat.talkToNPC(HISTORIAN_MINAS)) {
                NPCInteraction.handleConversation("I have some information that might be of use in your displays.");
                NPCInteraction.handleConversation();
            }
        }
    }

    public void enterCleaningArea() {
        if (!CLEANING_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Going to cleaning area";
            PathingUtil.walkToTile(new RSTile(3261, 3447, 0));
            if (Utils.clickObj(GATE, "Open")) {
                Utils.modSleep();

                if (NPCInteraction.isConversationWindowUp())
                    NPCInteraction.handleConversation("Yes, I'll go in!");

                Timer.waitCondition(() -> CLEANING_AREA.contains(Player.getPosition()), 15000);
            }
            Utils.idle(500, 2500);
        }
    }

    public void leaveCleaningArea() {
        if (CLEANING_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Leaving cleaning area";
            if (PathingUtil.localNavigation(new RSTile(3261, 3445, 0)))
                PathingUtil.movementIdle();
            if (Utils.clickObj(GATE, "Open"))
                Timer.waitCondition(() -> !CLEANING_AREA.contains(Player.getPosition()), 15000);
            Utils.idle(500, 2500);
        }
    }

    public void cleanFinds() {
        if (Inventory.find(POTTERY, OLD_COIN, ANCIENT_COIN, ANCIENT_SYMBOL, OLD_SYMBOL).length < 1) {
            if (Inventory.find(TROWEL, SPECIMEN_BRUSH, ROCK_PICK).length < 3) {
                cQuesterV2.status = "Getting Tools";
                enterCleaningArea();
                if (CLEANING_AREA.contains(Player.getPosition())) {

                    if (Utils.clickObj(TOOLS, "Take")) {
                        NPCInteraction.waitForConversationWindow();
                        NPCInteraction.handleConversation("Yes.");
                        NPCInteraction.handleConversation();
                    }
                    Utils.equipItem(LEATHER_BOOTS);
                    Utils.equipItem(LEATHER_GLOVES);
                }
            }
            if (Equipment.find(LEATHER_BOOTS, LEATHER_GLOVES).length < 2) {
                Utils.equipItem(LEATHER_BOOTS);
                Utils.equipItem(LEATHER_GLOVES);
            }
            if (Inventory.find(SPECIMEN_ROCKS).length < 1 &&
                    Inventory.find(TROWEL, SPECIMEN_BRUSH, ROCK_PICK).length == 3 && !Inventory.isFull()) {
                cQuesterV2.status = "Getting Specimen rocks";
                enterCleaningArea();
                RSObject[] rocks = Objects.findNearest(15, SPECIMEN_ROCK_PILE);

                if (CLEANING_AREA.contains(Player.getPosition()) && rocks.length > 0) {
                    if (!rocks[0].isClickable())
                        DaxCamera.focus(rocks[0]);

                    for (int i = 0; i < 25; i++) {
                        if (DynamicClicking.clickRSObject(rocks[0], "Take")) {
                            if (i == 0)
                                PathingUtil.movementIdle();
                            General.sleep(General.randomSD(240, 65));
                        }
                        if (Inventory.isFull())
                            break;
                    }
                    General.sleep(500, 2500);
                }
            }
            if (Inventory.find(SPECIMEN_ROCKS).length > 0 && Inventory.find(TROWEL, SPECIMEN_BRUSH, ROCK_PICK).length == 3) {
                enterCleaningArea();
                if (CLEANING_AREA.contains(Player.getPosition())) {
                    cQuesterV2.status = "Cleaning specimen rocks";
                    if (Utils.clickObj(SPECIMEN_TABLE, "Clean"))
                        Timer.abc2WaitCondition(() -> Inventory.find(SPECIMEN_ROCKS).length < 1, 60000, 75000);
                }
            }
        }
    }

    private void showFind(boolean placed, int ItemID) {
        if (!placed) {
            showItem(ItemID);
        } else {
            Inventory.drop(ItemID);
        }
    }

    public void showFinds() {
        if (Inventory.find(POTTERY, OLD_COIN, ANCIENT_COIN, ANCIENT_SYMBOL, OLD_SYMBOL).length > 0) {
            cQuesterV2.status = "Showing find to Sinco Doar";
            enterCleaningArea();
            showFind(POTTERY_PLACED, POTTERY);
            showFind(OLD_COIN_PLACED, OLD_COIN);
            showFind(ANCIENT_SYMBOL_PLACED, ANCIENT_SYMBOL);
            showFind(OLD_SYMBOL_PLACED, OLD_SYMBOL);
            showFind(ANCIENT_COIN_PLACED, ANCIENT_COIN);

            if (Utils.getVarBitValue(3639) == 0) // changes to 1 once neckalce is shown
                showItem(CLEAN_NECKLACE);
        }
    }

    public boolean showItem(int ItemID) {
        if (Inventory.find(ItemID).length > 0) {
            String name = RSItemDefinition.get(ItemID).getName();
            if (Utils.useItemOnNPC(ItemID, SINCO_DOAR)) {
                if (name != null)
                    General.println("[Debug]: Showed " + name + " to Sinco Doar", Color.RED);
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                return true;
            }
        }
        return false;
    }

    public void depositTrash() {
        enterCleaningArea();
        if (Inventory.find(BOWL, MITHRIL_ORE, BIG_BONES, IRON_DART, UNCUT_OPAL, CROSSBOW_LIMBS, WOODEN_STOCK, COAL, TIN_ORE, IRON_ARROWHEADS, IRON_ORE).length > 0) {
            Inventory.drop(BOWL, IRON_DART, UNCUT_OPAL, CROSSBOW_LIMBS, MITHRIL_ORE, BIG_BONES, WOODEN_STOCK, COAL, TIN_ORE, IRON_ARROWHEADS, BONES, IRON_ORE, BROKEN_GLASS, BROKEN_ARROW, OPAL, POT);
        }
        if (CLEANING_AREA.contains(Player.getPosition())) {
            if (Utils.clickObj(STORAGE_CRATE, "Add finds")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Yes, Place all my finds in the crate.");
                Timer.waitCondition(() -> Inventory.find(OLD_VASE, JEWLERY, BONES, BROKEN_GLASS).length < 1, 25000);
            }
            Utils.shortSleep();
            if (Inventory.find(BOWL, MITHRIL_ORE, BIG_BONES, IRON_DART, UNCUT_OPAL, CROSSBOW_LIMBS, WOODEN_STOCK, COAL, TIN_ORE, IRON_ARROWHEADS, IRON_ORE).length > 0) {
                Inventory.drop(BOWL, IRON_DART, UNCUT_OPAL, CROSSBOW_LIMBS, MITHRIL_ORE, BIG_BONES, WOODEN_STOCK, COAL, TIN_ORE, IRON_ARROWHEADS, IRON_ORE, BROKEN_GLASS, BROKEN_ARROW, OPAL, POT);
            }
        }
    }

    public void returnFinds() {
        if (Inventory.find(POTTERY, OLD_COIN, ANCIENT_COIN, ANCIENT_SYMBOL, OLD_SYMBOL).length > 0) {
            cQuesterV2.status = "Placing finds in displays";
            leaveCleaningArea();
            if (!CLEANING_AREA.contains(Player.getPosition())) {
                if (Inventory.find(POTTERY).length > 0) {
                    Utils.useItemOnObject(POTTERY, POTTERY_CASE);
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                }
                if (Inventory.find(OLD_COIN).length > 0) {
                    Utils.useItemOnObject(OLD_COIN, OLD_COIN_CASE);
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                }
                if (Inventory.find(ANCIENT_COIN).length > 0) {
                    Utils.useItemOnObject(ANCIENT_COIN, ANCIENT_COIN_CASE);
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                }
                if (Inventory.find(ANCIENT_SYMBOL).length > 0) {
                    Utils.useItemOnObject(ANCIENT_SYMBOL, ANCIENT_SYMBOL_CASE);
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                }
                if (Inventory.find(OLD_SYMBOL).length > 0) {
                    Utils.useItemOnObject(OLD_SYMBOL, OLD_SYMBOL_CASE);
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                }
            }
        }
    }

    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(Arrays.asList(
            new GEItem(ItemID.VARROCK_TELEPORT, 6, 50),
            new GEItem(VODKA, 2, 500),
            new GEItem(MARRENTIL_UNF, 1, 50),
            new GEItem(DIGSITE_TELEPORT, 6, 25),
            new GEItem(LUMBERYARD_TELEPORT, 6, 25),
            new GEItem(ItemID.AMULET_OF_GLORY[0], 1, 20),
            new GEItem(ItemID.SKILLS_NECKLACE[0], 1, 20),
            new GEItem(ItemID.STAMINA_POTION[0], 2, 20)
    ));


    public void buyItems() {
        cQuesterV2.status = "Buying Items";
        General.println("[Debug]: Buying Items");
        BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);
        buyStep.buyItems();

    }

    HashMap<Integer, Integer> getItemsMap = new HashMap<>();

    public void getItems() {
        getItemsMap.put(ItemID.AMULET_OF_GLORY[0], 1);

        cQuesterV2.status = "Getting Items";
        General.println("[Debug]: Getting Items");
        BankManager.open(true);
        BankManager.checkEquippedGlory();
        BankManager.depositAll(true);
        BankManager.withdraw(1, true, ItemID.AMULET_OF_GLORY[0]);
        BankManager.withdraw(2, true, VODKA);
        BankManager.withdraw(5, true, DIGSITE_TELEPORT);
        BankManager.withdraw(1, true, MARRENTIL_UNF);
        BankManager.withdraw(3, true, LUMBERYARD_TELEPORT);
        BankManager.withdraw(3, true, ItemID.VARROCK_TELEPORT);
        BankManager.withdraw(2, true, ItemID.STAMINA_POTION[0]);
        BankManager.withdraw(1, true, ItemID.SKILLS_NECKLACE[0]);
        BankManager.withdraw(1, true, BONE_CHARM);
        BankManager.close(true);
    }

    public void startQuest() {
        cQuesterV2.status = "Starting Quest";
        PathingUtil.walkToArea(START_AREA);
        if (NpcChat.talkToNPC(5214)) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Have you any interesting news?");
            NPCInteraction.handleConversation("Sign me up!", "Yes.");
            NPCInteraction.handleConversation();
        }
    }

    private void step2() {
        cQuesterV2.status = "Going to barge foreman";
        PathingUtil.walkToArea(IN_FRONT_OF_BARGE);
        if (NpcChat.talkToNPC("Barge foreman")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }
    }

    private void step3() {
        cQuesterV2.status = "Going to varrock sawmill";
        PathingUtil.walkToArea(VARROCK_SAWMILL_OPERATOR_AREA);
        if (NpcChat.talkToNPC("Sawmill operator")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("I'm here on behalf of the museum archaeological team.");
            NPCInteraction.handleConversation();

        }
    }

    private void step4() {
        cQuesterV2.status = "Going to woodcutting guild";
        PathingUtil.walkToArea(OUTSIDE_WOODCUTTING_GUILD);
        if (Utils.clickObj("Gate", "Open")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("I'm here on behalf of the museum archaeological team.");
            NPCInteraction.handleConversation();

        }
    }

    private void goOntoBarge() {
        if (!BARGE.contains(Player.getPosition())) {
            cQuesterV2.status = "Going to barge guard";
            PathingUtil.walkToArea(IN_FRONT_OF_BARGE);
            if (NpcChat.talkToNPC("Barge guard")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Can I go onto the barge?");
                NPCInteraction.handleConversation();
                Utils.shortSleep();
            }
        }
    }

    private void step6() {
        if (BARGE.contains(Player.getPosition())) {
            cQuesterV2.status = "Step 6: Going to Lead Navigator";
            if (NpcChat.talkToNPC("Lead Navigator")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Yep, that would be me.");
                NPCInteraction.handleConversation("I'm aware, no need to tell me about them.");
                NPCInteraction.handleConversation();
            }
        }
    }

    public void goToPortSarim() {
        cQuesterV2.status = "Going to Rusty anchor";
        PathingUtil.walkToArea(RUSTY_ANCHOR);
        if (NpcChat.talkToNPC("Jack Seagull")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Ever made any cursed voyages?");
            NPCInteraction.handleConversation();
        }
    }

    public void goToMine() {
        cQuesterV2.status = "Going to Mine";
        if (BARGE.contains(Player.getPosition())) {
            if (NpcChat.talkToNPC("Barge guard")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Can I get off the barge?");
                NPCInteraction.handleConversation();
                Utils.shortSleep();
            }
        }
        PathingUtil.walkToArea(MINE_AREA);
        if (NpcChat.talkToNPC("Odd Old Man")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Talk about lucky charms.");
            NPCInteraction.handleConversation("I'm making a cursed voyage.");
            NPCInteraction.handleConversation();
        }
    }

    public void goToPotionGuy() {
        cQuesterV2.status = "Going to Get potion";
        if (!BankManager.checkInventoryItems(MARRENTIL_UNF, VODKA)) {
            buyItems();
            getItems();
        }
        PathingUtil.walkToArea(POTION_GUY_AREA);
        if (NpcChat.talkToNPC("Apothecary")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Talk about something else.");
            NPCInteraction.handleConversation("Talk about Bone Voyage.");
            NPCInteraction.handleConversation();
            Utils.shortSleep();
        }
    }

    public void giveJrBoneCharm() {
        if (BARGE.contains(Player.getPosition())) {
            if (NpcChat.talkToNPC("Junior Navigator")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
        }
    }

    public void giveLeadPotion() {
        if (!BARGE.contains(Player.getPosition()))
            goOntoBarge();

        if (BARGE.contains(Player.getPosition())) {
            if (NpcChat.talkToNPC("Lead Navigator")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
        }
    }

    public void startVoyage() {
        if (!BARGE.contains(Player.getPosition()) && !NPCInteraction.isConversationWindowUp()) {
            goOntoBarge();
        }
        if (NpcChat.talkToNPC("Lead Navigator")) {
            cQuesterV2.status = "Talking to navigator";
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            NPCInteraction.handleConversation("I'm ready, let's go.");
            cQuesterV2.status = "Waiting until no longer docked";
            Timer.waitCondition(() -> !BARGE.contains(Player.getPosition()), 7000, 9000);
        }
        if (!BARGE.contains(Player.getPosition()) && !Interfaces.isInterfaceSubstantiated(604)) {
            cQuesterV2.status = "Waiting for interface 604";
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            Timer.waitCondition(() -> Interfaces.isInterfaceSubstantiated(604), 15000, 20000);
        }
    }

    public void voyage() {
        if (Interfaces.isInterfaceSubstantiated(604)) {
            if (Interfaces.get(604, 11).click()) // Increases sail
                Utils.microSleep();
            if (Interfaces.get(604, 11).click()) // Increases sail
                Utils.microSleep();
        }
        Utils.longSleep();
    }

    enum Direction {
        STRAIGHT,
        LEFT,
        RIGHT,
    }

    int NAVIGATE_MASTER = 604;

    private Direction getDirection() {
        RSInterface direction = Interfaces.get(NAVIGATE_MASTER, i -> i.getTextureID() == 1543);
        if (direction != null) {
            int spriteId = direction.getSpriteID();

            if (spriteId < 30000)
                return Direction.LEFT;
            else if (spriteId < 34000)
                return Direction.STRAIGHT;
            return Direction.RIGHT;
        }
        return null;
    }

    private Direction getRuterDirection() {
        RSInterface direction = Interfaces.get(NAVIGATE_MASTER, 37);
        if (direction != null) {
            int spriteId = direction.getSpriteID();
            General.println("Spirte id: " + spriteId);
            if (spriteId < 30000)
                return Direction.LEFT;
            else if (spriteId < 34000)
                return Direction.STRAIGHT;
            return Direction.RIGHT;
        }
        return null;
    }

    Direction prevD;

    public void navigate() {
        cQuesterV2.status = "Navigating to island";
        if (NPCInteraction.isConversationWindowUp())
            NPCInteraction.handleConversation();

        Direction d = getDirection();

        if (d == null)
            return;

        if (prevD == null)
            prevD = d;

        if (d.equals(Direction.STRAIGHT)) {
            if (prevD.equals(Direction.LEFT)) {
                InterfaceUtil.click(NAVIGATE_MASTER, 38);
                General.sleep(General.random(1000, 1200));
            } else if (prevD.equals(Direction.RIGHT)) {
                InterfaceUtil.click(NAVIGATE_MASTER, 39);
                General.sleep(General.random(1000, 1200));
            }
            prevD = Direction.STRAIGHT;
            Timer.waitCondition(() -> !d.equals(getDirection()), 7000, 9000);

        } else if (d.equals(Direction.LEFT)) {
            if (prevD.equals(Direction.RIGHT)) {
                InterfaceUtil.click(NAVIGATE_MASTER, 39);
                General.sleep(General.random(1000, 1200));
            }
            InterfaceUtil.click(NAVIGATE_MASTER, 39);
            prevD = Direction.LEFT;
            Timer.waitCondition(() -> !d.equals(getDirection()), 7000, 9000);

        } else if (d.equals(Direction.RIGHT)) {
            if (prevD.equals(Direction.LEFT)) {
                InterfaceUtil.click(NAVIGATE_MASTER, 38);
                General.sleep(General.random(1000, 1200));
            }
            InterfaceUtil.click(NAVIGATE_MASTER, 38);
            prevD = Direction.RIGHT;
            Timer.waitCondition(() -> !d.equals(getDirection()), 7000, 9000);
        }
    }

    int GAME_SETTING = 1630;

    @Override
    public void execute() {
        if (RSVarBit.get(3637).getValue() < 100) { // this value = kudos number

            getQuestKudos();
            cleanFinds();
            getModelLengths();
            showFinds();
            returnFinds();
            depositTrash();
        } else if (RSVarBit.get(3637).getValue() >= 100 && Game.getSetting(1630) == 0) { // this value = kudos number
            buyItems();
            getItems();
            startQuest();
        } else if (Game.getSetting(1630) == 5) {
            step2();
        } else if (Game.getSetting(1630) == 10) {
            step3();
        } else if (Game.getSetting(1630) == 11) {
            step4();
        } else if (Game.getSetting(1630) == 15) {
            step3(); //repeat
        } else if (Game.getSetting(1630) == 20) {
            step2();//repeat
        } else if (Game.getSetting(1630) == 21) {
            goOntoBarge();
            step6();
        } else if (Game.getSetting(1630) == 22) {
            goToPortSarim();
        } else if (Game.getSetting(1630) == 23) {
            goOntoBarge();
            step6();
        } else if (Game.getSetting(1630) == 25) {
            goToMine();
        } else if (Game.getSetting(1630) == 537) {
            goToPotionGuy();
        } else if (Game.getSetting(1630) == 2585) {
            goToPotionGuy();
        } else if (Game.getSetting(1630) == 4633) {
            goOntoBarge();
            giveJrBoneCharm();
        } else if (Game.getSetting(1630) == 5145) {
            giveLeadPotion();
        } else if (Game.getSetting(1630) == 7198) {

            startVoyage();
        } else if (Game.getSetting(1630) == 7203) {
            navigate();
        } else if (Game.getSetting(1630) == 7218) {
            Utils.closeQuestCompletionWindow();
            cQuesterV2.taskList.remove(this);
            if (CLEANING_AREA.contains(Player.getPosition())) {
                cleanFinds();
                showFinds();
                depositTrash();
                ;
            }

        } else {
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
        return "Bone Voyage";
    }

    @Override
    public boolean checkRequirements() {
        return false;
    }

    @Override
    public java.util.List<Requirement> getGeneralRequirements() {
        return null;
    }

    @Override
    public java.util.List<ItemRequirement> getBuyList() {
        return null;
    }
}
