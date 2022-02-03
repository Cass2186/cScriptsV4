package scripts.QuestPackages.NatureSpirit;

import dax.walker.utils.AccurateMouse;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.types.*;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.PriestInPeril.PriestInPeril;
import scripts.QuestSteps.BuyItemsStep;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.InventoryRequirement;
import scripts.Requirements.ItemReq;
import scripts.Tasks.Priority;

import java.util.ArrayList;
import java.util.Arrays;

public class NatureSpirit implements QuestTask {

    private static NatureSpirit quest;

    public static NatureSpirit get() {
        return quest == null ? quest = new NatureSpirit() : quest;
    }


    int SICKLE = 2961;
    int GHOSTSPEAK_AMULET = 552;
    int ADAMANT_SCIMITAR = 1331;
    int LOBSTER = 379;
    int DRUIDIC_SPELL = 2968;
    int MUSHROOM = 2970;
    int WASHING_BOWL = 2964;
    int MIRROR = 2966;
    int USED_SPELL = 2969;
    int SILVER_SICKLE_B = 2963;
    int DRUIDIC_POUCH = 2957;
    int FILLED_DRUIDIC_POUCH = 2958;
    int RUNE_SCIMITAR = 1333;
    int APPLE_PIE = 2323;
    int MEAT_PIE = 2327;
    int INVISIBLE_GHAST = 945;
    int VISIBLE_GHAST = 946;
    int ROTTEN_FOOD = 2959;
    int PIE_DISH = 2313;
    int GROTO_ENTER_ID = 3516;
    int MUSHROOM_ON_LOG = 3509;
    int SALVE_GRAVEYARD_TELEPORT = 19619;

    RSArea DREZEL_UNDERGROUND = new RSArea(new RSTile(3432, 9892, 0), new RSTile(3443, 9902, 0));
    RSArea BEFORE_BRIDGE = new RSArea(new RSTile(3437, 3328, 0), new RSTile(3443, 3325, 0));
    RSArea INSIDE_TREE = new RSArea(new RSTile(3447, 9733, 0), new RSTile(3435, 9744, 0));
    RSArea ROTTEN_LOG2_AREA = new RSArea(new RSTile(3425, 3333, 0), new RSTile(3427, 3331, 0));
    RSArea GROTTO_TREE_AREA = new RSArea(new RSTile(3446, 3330, 0), new RSTile(3434, 3345, 0));
    RSArea WHOLE_SWAMP_AREA = new RSArea(new RSTile(3478, 3326, 0), new RSTile(3401, 3455, 0));

    RSTile LIGHT_BROWN_STONE_TILE = new RSTile(3439, 3336, 0);
    RSTile ORANGE_STONE_TILE = new RSTile(3440, 3335, 0);
    RSTile ROTTEN_LOG_TILE = new RSTile(3435, 3449, 0);
    RSTile ROTTEN_LOG_TILE2 = new RSTile(3426, 3331, 0);

    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(SICKLE, 1, 1500),
                    new GEItem(ItemID.LOBSTER, 16, 50),
                    new GEItem(ItemID.STAMINA_POTION[0], 2, 15),
                    new GEItem(ItemID.PRAYER_POTION_4, 2, 15),
                    new GEItem(ItemID.SALVE_GRAVEYARD_TELEPORT, 7, 30),
                    new GEItem(ItemID.VARROCK_TELEPORT, 10, 40),
                    new GEItem(ItemID.RING_OF_WEALTH[0], 1, 25),
                    new GEItem(ItemID.RING_OF_DUELING[0], 1, 25)
            )
    );

    InventoryRequirement initialItemReqs = new InventoryRequirement(new ArrayList<>(
            Arrays.asList(
                    new ItemReq(ItemID.LOBSTER, 15, 2)

            )
    ));


    public void buyItems() {
        cQuesterV2.status = "Buying Items";
        General.println("[Debug]: Buying Items");

        if (Skills.getCurrentLevel(Skills.SKILLS.ATTACK) >= 40) {
           itemsToBuy.add(new GEItem(ItemID.RUNE_SCIMITAR, 1, 20));
        } else if (Skills.getCurrentLevel(Skills.SKILLS.ATTACK) >= 30) {
            itemsToBuy.add(new GEItem(ItemID.ADAMANT_SCIMITAR, 1, 50));
        }
        BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);
        buyStep.buyItems();
    }

    public void getItems1() {
        cQuesterV2.status = "Getting Items";
        General.println("[Debug]: " + cQuesterV2.status);
        BankManager.open(true);
        BankManager.checkEquippedGlory();
        BankManager.depositAll(true);
        BankManager.withdraw(12, true, LOBSTER);
        BankManager.withdraw(5, true, SALVE_GRAVEYARD_TELEPORT);
        BankManager.withdraw(2, true,
                ItemID.STAMINA_POTION[0]);
        BankManager.withdraw(1, true, SICKLE);
        BankManager.withdraw(2, true,
                ItemID.PRAYER_POTION[0]);
        BankManager.withdraw(1, true, GHOSTSPEAK_AMULET);
        BankManager.withdraw(1, true,
                ItemID.RING_OF_DUELING[0]);
        if (Skills.getCurrentLevel(Skills.SKILLS.ATTACK) >= 40) {
            BankManager.withdraw(1, true, RUNE_SCIMITAR);
            Utils.equipItem(RUNE_SCIMITAR);
        } else if (Skills.getCurrentLevel(Skills.SKILLS.ATTACK) >= 30) {
            BankManager.withdraw(1, true, ADAMANT_SCIMITAR);
            Utils.equipItem(ADAMANT_SCIMITAR);
        }
        BankManager.close(true);
        Utils.equipItem(GHOSTSPEAK_AMULET);
    }

    public void startQuest() {
        cQuesterV2.status = "Going to Start Quest";
        PathingUtil.walkToArea(DREZEL_UNDERGROUND);
        if (NpcChat.talkToNPC("Drezel")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Is there anything else interesting to do around here?");
            NPCInteraction.handleConversation("Well, what is it, I may be able to help?");
            NPCInteraction.handleConversation("Yes.");
            NPCInteraction.handleConversation();
            if (Interfaces.isInterfaceSubstantiated(11, 4)) {
                Interfaces.get(11, 4).click();
            }
        }

    }

    public void goToGrotto() { // this method is used to go to the Grotto tree as Dax won't go all the way across the bridge
        cQuesterV2.status = "Going to Grotto tree";
        General.println("[Debug]: " + cQuesterV2.status);
        if (!WHOLE_SWAMP_AREA.contains(Player.getPosition())) {
            PathingUtil.walkToArea(GATE_AREA);
            openGate();
        }
        if (!GROTTO_TREE_AREA.contains(Player.getPosition())) {
            if (Equipment.find(GHOSTSPEAK_AMULET).length < 1 && Inventory.find(GHOSTSPEAK_AMULET).length < 1) {
                General.println("[Debug]: We are missing our Ghostspeak amulet, banking");
                getItems1();
            }
            Utils.clickObj("Holy barrier", "Pass-through"); // only will execute if we are near the holy barrier
            Timer.waitCondition(() -> NPCs.find("Drezel").length < 1, 8000, 12000); // anchors to drezel to know we have left his area
            PathingUtil.walkToArea(BEFORE_BRIDGE);
        }
        if (BEFORE_BRIDGE.contains(Player.getPosition())) {
            Utils.clickObj("Bridge", "Jump");
            Timer.waitCondition(() -> GROTTO_TREE_AREA.contains(Player.getPosition()) && !Player.isMoving(), 8000, 12000);
            Utils.idle(500, 3000);
        }

    }

    public void getAnotherScroll() { // called if for some reason our first spell doesn't work and we need another
        if (Inventory.find(DRUIDIC_SPELL).length < 1) {
            cQuesterV2.status = "Getting another Spell.";
            General.println("[Debug]: " + cQuesterV2.status);
            goToGrotto();
            dropRottenFood(); // makes inventory space
            Utils.clickObj(GROTO_ENTER_ID, "Enter");
            Timer.waitCondition(() -> NPCs.find("Filliman Tarlock").length > 0, 5000, 7000);
            if (NPCs.find("Filliman Tarlock").length > 0) {
                NpcChat.talkToNPC("Filliman Tarlock");
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                NPCInteraction.handleConversation("Could I have another bloom scroll please?");
                NPCInteraction.handleConversation();
            }
        }
    }

    public void dropRottenFood() { // used to make room in inventory for quest items
        Inventory.drop(ROTTEN_FOOD);
        Inventory.drop(PIE_DISH);
        Inventory.drop(WASHING_BOWL);
        if (Inventory.isFull() && Inventory.find(ItemID.LOBSTER).length > 0) {
            AccurateMouse.click(Inventory.find(ItemID.LOBSTER)[0], "Eat");
        }

    }

    public void step2() {
        if (!BankManager.checkInventoryItems(ItemID.LOBSTER, ItemID.STAMINA_POTION[0], SICKLE)) {
            General.println("[Debug]: Don't have necessary items, buying and banking");
            buyItems();
            getItems1();
        } else {
            if (!GROTTO_TREE_AREA.contains(Player.getPosition()))
                goToGrotto();

            dropRottenFood();

            if (GROTTO_TREE_AREA.contains(Player.getPosition())) {
                RSGroundItem[] washingBowlGround = GroundItems.find(WASHING_BOWL);
                if (washingBowlGround.length > 0 && Inventory.find(MIRROR).length < 1) {
                    if (Utils.clickGroundItem(WASHING_BOWL))
                        Timer.waitCondition(() -> Inventory.find(WASHING_BOWL).length > 0, 5000, 6000);

                    if (Utils.clickGroundItem(MIRROR)) {
                        Timer.waitCondition(() -> Inventory.find("Mirror").length > 0, 5000, 7000);
                        Utils.idle(500, 2500);
                    }
                }

                if (Inventory.find(MIRROR).length > 0)
                    if (Utils.clickObj(GROTO_ENTER_ID, "Enter"))
                        Timer.waitCondition(() -> NPCs.find("Filliman Tarlock").length > 0, 5000, 7000);


                if (NPCs.find("Filliman Tarlock").length > 0 && Inventory.find(MIRROR).length > 0) {
                    if (NpcChat.talkToNPC("Filliman Tarlock")) {
                        NPCInteraction.waitForConversationWindow();
                        NPCInteraction.handleConversation("What's it like being a ghost?");
                        NPCInteraction.handleConversation();
                    }
                    if (Utils.useItemOnNPC(MIRROR, "Filliman Tarlock")) {
                        NPCInteraction.waitForConversationWindow();
                        NPCInteraction.handleConversation();
                        Utils.shortSleep();
                    }
                }
            }
        }
    }


    public void step2b() {
        Utils.clickObj(GROTO_ENTER_ID, "Enter");
        Timer.waitCondition(() -> NPCs.find("Filliman Tarlock").length > 0, 5000, 7000);
        if (NPCs.find("Filliman Tarlock").length > 0) {
            if (NpcChat.talkToNPC("Filliman Tarlock")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
        }
        if (Inventory.find("Journal").length < 1) {
            if (Inventory.isFull()) {
                dropRottenFood();
                Inventory.drop(WASHING_BOWL);
            }
            if (Utils.clickObj("Grotto tree", "Search")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                Timer.waitCondition(() -> Inventory.find("Journal").length > 0, 5000, 7000);
                Utils.shortSleep();
            }
        }
        if (NPCs.find("Filliman Tarlock").length > 0) {
            if (Inventory.find("Journal").length > 0) {
                if (Utils.useItemOnNPC("Journal", "Filliman Tarlock")) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation("How can I help?");
                    NPCInteraction.handleConversation();
                }
            }
        }
    }

    public void getBlessedByDrezel() {
        cQuesterV2.status = "Going to Drezel";
        if (GROTTO_TREE_AREA.contains(Player.getPosition())) {
            if (Utils.clickObj("Bridge", "Jump")) {
                Timer.waitCondition(() -> BEFORE_BRIDGE.contains(Player.getPosition()), 8000, 11000);
            }
        }
        if (!DREZEL_UNDERGROUND.contains(Player.getPosition())) {
            PathingUtil.walkToArea(DREZEL_UNDERGROUND);
        }
        if (DREZEL_UNDERGROUND.contains(Player.getPosition())) {
            cQuesterV2.status = "Getting blessed";
            if (NpcChat.talkToNPC("Drezel")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                General.sleep(General.random(3000, 5000)); // sleep while he blesses you
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
        }
    }


    public void getMushroom() {
        cQuesterV2.status = "Getting Mushroom";
        General.println("[Debug]: " + cQuesterV2.status);
        if (Inventory.find(DRUIDIC_SPELL).length < 1)
            getAnotherScroll();

        if (Inventory.find(MUSHROOM).length < 1) {
            if (!WHOLE_SWAMP_AREA.contains(Player.getPosition())) {
                PathingUtil.walkToArea(GATE_AREA);
                openGate();
            }

            PathingUtil.walkToTile(ROTTEN_LOG_TILE);

            RSObject[] rottenLog = Objects.findNearest(8, "Rotting log");
            if (rottenLog.length > 0) {
                if (rottenLog[0].getPosition().distanceTo(Player.getPosition()) > 4) {
                    PathingUtil.walkToTile(rottenLog[0].getPosition());
                    Utils.idle(500, 2000);
                }
                RSItem[] invSpell = Inventory.find(DRUIDIC_SPELL);
                if (invSpell.length > 0) {
                    if (AccurateMouse.click(invSpell[0], "Cast")) {
                        Timer.waitCondition(() -> Objects.findNearest(6, MUSHROOM_ON_LOG).length > 0, 9000, 12000);
                        Utils.idle(2500, 4000);
                    }
                }
                if (Inventory.isFull()) { // if inventory is still full after dropping junk we'll drop pies to make room for mushrooms
                    Inventory.drop(MEAT_PIE);
                    Inventory.drop(APPLE_PIE);
                    dropRottenFood();
                    Utils.unselectItem();
                }
                RSObject[] fungi = Objects.findNearest(6, MUSHROOM_ON_LOG);
                if (fungi.length > 0)
                    if (DynamicClicking.clickRSObject(fungi[0], "Pick"))
                        Timer.waitCondition(() -> Inventory.find(MUSHROOM).length > 0, 5000, 7000);

            }
        }
    }


    public void step5() {
        if (Inventory.find(MUSHROOM).length > 0) {
            goToGrotto();

            if (Utils.useItemOnObject(MUSHROOM, 3527))
                Timer.waitCondition(() -> Inventory.find(MUSHROOM).length > 1, 5000, 7000);

            RSObject[] grottoTree = Objects.findNearest(20, GROTO_ENTER_ID);

            if (grottoTree.length > 0) {
                AccurateMouse.click(grottoTree[0], "Enter");
                Timer.waitCondition(() -> NPCs.find("Filliman Tarlock").length > 0, 5000, 7000);
            }
        }
        if (NPCs.find("Filliman Tarlock").length > 0)
            step6();

    }

    public void step6() {
        cQuesterV2.status = "Solving Quiz";
        General.println("[Debug]: " + cQuesterV2.status);
        if (Inventory.find(USED_SPELL).length > 0) {
            if (Utils.useItemOnObject(USED_SPELL, 3529)) {
                Timer.waitCondition(() -> Inventory.find(USED_SPELL).length < 1, 8000, 12000);
                General.sleep(General.random(500, 3000));
            }
        }

        RSObject[] grottoTree = Objects.findNearest(20, "Grotto");
        if (grottoTree.length > 0) {
            AccurateMouse.click(grottoTree[0], "Enter");
            Timer.waitCondition(() -> NPCs.find("Filliman Tarlock").length > 0, 5000, 8000);
            General.sleep(General.random(1000, 3500));
        }

        Walking.clickTileMS(ORANGE_STONE_TILE, "Walk here");
        General.sleep(General.random(2600, 4500));

        if (NPCs.find("Filliman Tarlock").length > 0) {
            if (NpcChat.talkToNPC("Filliman Tarlock")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("I think I've solved the puzzle!");

                General.sleep(General.random(3000, 4500)); //sleep as he casts a spell
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();

                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
        }
    }

    public void enterGrotto() {
        cQuesterV2.status = "Entering Grotto";
        General.println("[Debug]: " + cQuesterV2.status);
        RSObject[] grottoTree = Objects.findNearest(20, "Grotto");
        if (grottoTree.length > 0) {
            if (AccurateMouse.click(grottoTree[0], "Enter"))
                Timer.waitCondition(() -> !GROTTO_TREE_AREA.contains(Player.getPosition()), 5000, 8000);
            Utils.idle(500, 1500);
        }
    }

    public void step8() {
        if (!INSIDE_TREE.contains(Player.getPosition())) {
            enterGrotto();
        }
        if (INSIDE_TREE.contains(Player.getPosition())) {
            cQuesterV2.status = "Searching Grotto";
            General.println("[Debug]: " + cQuesterV2.status);
            if (Utils.clickObj(3520, "Search")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
        }
    }

    public void step8b() {
        if (!INSIDE_TREE.contains(Player.getPosition())) {
            enterGrotto();
        }
        if (INSIDE_TREE.contains(Player.getPosition())) {
            cQuesterV2.status = "Searching Grotto";
            General.println("[Debug]: " + cQuesterV2.status);
            if (Utils.clickObj(3520, "Search")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
        }
    }


    public void step9() { // kill 3 ghasts
        cQuesterV2.status = "Going to Kill Ghasts";
        General.println("[Debug]: " + cQuesterV2.status);
        if (INSIDE_TREE.contains(Player.getPosition())) {
            if (Utils.clickObj(3525, "Exit"))
                Timer.abc2WaitCondition(() -> GROTTO_TREE_AREA.contains(Player.getPosition()), 8000, 12000);
        }

        if (GROTTO_TREE_AREA.contains(Player.getPosition()))
            if (Utils.clickObj("Bridge", "Jump"))
                Timer.abc2WaitCondition(() -> BEFORE_BRIDGE.contains(Player.getPosition()), 8000, 12000);

        if (!ROTTEN_LOG2_AREA.contains(Player.getRSPlayer())) {
            PathingUtil.walkToTile(ROTTEN_LOG_TILE2);
            Utils.shortSleep();
        }

        if (ROTTEN_LOG2_AREA.contains(Player.getPosition())) {

            while (Inventory.find(MUSHROOM).length < 4) {
                cQuesterV2.status = "Getting fungi";
                General.sleep(150);
                RSItem[] invFungi = Inventory.find(MUSHROOM);
                if (AccurateMouse.click(Inventory.find(SILVER_SICKLE_B)[0], "Cast Bloom"))
                    Timer.waitCondition(() -> Objects.findNearest(6, MUSHROOM_ON_LOG).length > 0, 6000, 9000);

                dropRottenFood();

                if (Inventory.isFull()) {
                    Inventory.drop(MEAT_PIE);
                    Inventory.drop(APPLE_PIE);
                }

                RSObject[] fungi = Objects.findNearest(6, MUSHROOM_ON_LOG);
                if (fungi.length > 0) {

                    if (!fungi[0].isClickable())
                        fungi[0].adjustCameraTo();

                    if (AccurateMouse.click(fungi[0], "Pick")) {
                        Timer.waitCondition(() -> Inventory.find(MUSHROOM).length > invFungi.length, 5000, 7000);
                        Utils.shortSleep();
                    }
                }
                if (Inventory.find(MUSHROOM).length > 2)
                    break;

            }

            if (Inventory.find(MUSHROOM).length > 2 && Inventory.find(DRUIDIC_POUCH).length > 0)
                if (AccurateMouse.click(Inventory.find(DRUIDIC_POUCH)[0], "Fill"))
                    Timer.abc2WaitCondition(() -> NPCs.find("Ghast").length > 0, 20000, 25000);

            while (Inventory.find(DRUIDIC_POUCH).length < 1) { // returns true if we have a pouc with food in it (difernet ID)
                General.sleep(150);

                if (!Combat.isUnderAttack() && NPCs.find(INVISIBLE_GHAST).length > 0) {
                    cQuesterV2.status = "Attacking";
                    if (Utils.useItemOnNPC(2958, INVISIBLE_GHAST)) {
                        Timer.waitCondition(() -> Combat.isUnderAttack(), 15000, 25000);
                        Utils.idle(400, 1200);
                    }

                } else if (Combat.isUnderAttack() && NPCs.find("Ghast").length > 0) {
                    if (Combat.getHPRatio() < General.random(40, 65) && Inventory.find(LOBSTER).length > 0) {
                        cQuesterV2.status = "Eating";
                        Utils.shortSleep(); //sleep before eating so it's not immediate
                        if (AccurateMouse.click(Inventory.find(LOBSTER)[0], "Eat"))
                            Utils.microSleep();
                    }
                } else {
                    cQuesterV2.status = "Idling...";
                    General.sleep(General.random(1000, 3000));
                }
            }
        }
    }

    public void getFungi() {
        PathingUtil.walkToTile(ROTTEN_LOG_TILE2);
        if (ROTTEN_LOG2_AREA.contains(Player.getPosition())) {
            if (Prayer.getPrayerPoints() < 5) {
                if (Inventory.find(
                        ItemID.PRAYER_POTION).length > 0) {
                    cQuesterV2.status = "Drinking Prayer Potion";
                    General.println("[Debug]: " + cQuesterV2.status);
                    if (AccurateMouse.click(Inventory.find(ItemID.PRAYER_POTION)[0]))
                        General.sleep(General.random(300, 1200));
                }
            }
            if (Prayer.getPrayerPoints() > 5) {
                cQuesterV2.status = "Getting fungi";
                General.println("[Debug]: " + cQuesterV2.status);
                if (Inventory.find(SILVER_SICKLE_B).length > 0) {
                    if (AccurateMouse.click(Inventory.find(SILVER_SICKLE_B)[0], "Cast Bloom")) {
                        Timer.waitCondition(() -> Objects.find(5, MUSHROOM_ON_LOG).length > 0, 7000, 10000);
                        General.sleep(General.random(300, 1200));
                        dropRottenFood();
                    }
                }

                RSItem[] invFungi = Inventory.find(MUSHROOM);
                RSObject[] fungi = Objects.findNearest(6, MUSHROOM_ON_LOG);

                if (fungi.length > 0) {

                    if (!fungi[0].isClickable())
                        fungi[0].adjustCameraTo();

                    if (AccurateMouse.click(fungi[0], "Pick"))
                        Timer.abc2WaitCondition(() -> Inventory.find(MUSHROOM).length > invFungi.length, 5000, 7000);
                }
            }
        }
    }


    public void fightGhast() {
        PathingUtil.walkToTile(ROTTEN_LOG_TILE2);
        PathingUtil.movementIdle();
        if (Inventory.find(MUSHROOM).length > 2) {
            cQuesterV2.status = "Filling pouch";

            if (Inventory.find(DRUIDIC_POUCH).length > 0) {
                if (AccurateMouse.click(Inventory.find(DRUIDIC_POUCH)[0], "Fill"))
                    Timer.waitCondition(() -> NPCs.find("Ghast").length > 0, 25000, 30000);

            } else if (Inventory.find(FILLED_DRUIDIC_POUCH).length > 0)

                if (AccurateMouse.click(Inventory.find(FILLED_DRUIDIC_POUCH)[0], "Fill"))
                    Timer.waitCondition(() -> NPCs.find("Ghast").length > 0, 25000, 30000); //waits to be attacked

        } else if (Inventory.find(FILLED_DRUIDIC_POUCH).length < 1) {
            getFungi();
        }
        while ((Inventory.find(FILLED_DRUIDIC_POUCH).length > 0 || Combat.isUnderAttack())) {
            General.sleep(100);
            RSNPC[] visible = NPCs.find(VISIBLE_GHAST);
            RSNPC[] invisible = NPCs.find(INVISIBLE_GHAST);

            if (!Combat.isUnderAttack() && invisible.length > 0) {
                cQuesterV2.status = "Attacking Ghast";
                if (AccurateMouse.click(Inventory.find(FILLED_DRUIDIC_POUCH)[0], "Use")) {
                    if (DynamicClicking.clickRSNPC(invisible[0], "Use Druid pouch ->"))
                        Timer.waitCondition(() -> Combat.isUnderAttack(), 12000, 15000);
                }
            } else if (!Combat.isUnderAttack() && visible.length > 0) {
                if (CombatUtil.clickTarget(visible[0]))
                    Timer.waitCondition(() -> Combat.isUnderAttack(), 12000, 15000);
            }
            if (Combat.getHPRatio() < General.random(40, 65) && Inventory.find(LOBSTER).length > 0) {
                cQuesterV2.status = "Eating";
                General.println("[Debug]: " + cQuesterV2.status);
                Utils.shortSleep();
                if (AccurateMouse.click(Inventory.find(LOBSTER)[0], "Eat"))
                    General.sleep(General.random(500, 1000));
            } else {
                cQuesterV2.status = "Idling...";
                General.sleep(General.random(500, 3000));
            }
        }

    }

    public void finishQuest() {
        Utils.shortSleep();
        if (!GROTTO_TREE_AREA.contains(Player.getPosition()))
            goToGrotto();

        RSObject[] grottoTree = Objects.findNearest(20, GROTO_ENTER_ID);
        if (grottoTree.length > 0) {
            cQuesterV2.status = "Nature Spirit: Finishing Quest";
            General.println("[Debug]: " + cQuesterV2.status);

            if (!grottoTree[0].isClickable())
                grottoTree[0].adjustCameraTo();

            if (AccurateMouse.click(grottoTree[0], "Enter"))
                Timer.abc2WaitCondition(() -> !GROTTO_TREE_AREA.contains(Player.getPosition()), 7000, 10000);
        }
        if (Utils.clickObj(3520, "Search")) {// clicks the grotto once inside the tree
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            Utils.shortSleep();
        }
    }

    RSArea GATE_AREA = new RSArea(new RSTile(3446, 3458, 0), new RSTile(3437, 3463, 0));

    public void openGate() {
        cQuesterV2.status = "Opening Gate";
        if (Utils.clickObj("Gate", "Open")) {
            Timer.waitCondition(() -> Interfaces.isInterfaceSubstantiated(580), 8000, 12000);
            if (InterfaceUtil.clickInterfaceAction(580, "Yes"))
                Utils.shortSleep();
        }
    }

    private Timer safteyTimer = new Timer(General.random(480000, 660000)); // 8- 11min

    int GAME_SETTING = 307;


    @Override
    public void execute() {
        if (!checkRequirements())
            cQuesterV2.taskList.remove(this);
/*
        if (!Utils.checkGameSettings(GAME_SETTING)) {
            safteyTimer.reset();
            Utils.recordGameSetting(GAME_SETTING);
        }
        if (!safteyTimer.isRunning()) {
            General.println("[Debug]: Script timed out after 8-10min");
            cQuesterV2.taskList.remove(this);
            return;
        } */

        if (Game.getSetting(307) == 0) {
            buyItems();
            getItems1();
            if (Prayer.getPrayerPoints() < 30)
                Utils.clanWarsReset();

            startQuest();
        }
        if (Game.getSetting(307) == 5 || Game.getSetting(307) == 10 || Game.getSetting(307) == 15) {
            step2();
        }
        if (Game.getSetting(307) == 25 || Game.getSetting(307) == 30) {
            step2b();
        }
        if (Game.getSetting(307) == 35) {
            getBlessedByDrezel();
            Utils.longSleep();
        }
        if (Game.getSetting(307) == 40 || Game.getSetting(307) == 45) {
            getMushroom();
        }
        if (Game.getSetting(307) == 50) {
            step5();
        }
        if (Game.getSetting(307) == 55) {
            step5();
            step6();
            Utils.longSleep();
        }
        if (Game.getSetting(307) == 60) {
            enterGrotto();
        }
        if (Game.getSetting(307) == 65) { // for 65 you need to search the Grotto, then  it changes to 70 after he does a spell
            step8();
        }
        if (Game.getSetting(307) == 70) { // this step is getting your sickle blessed
            step8();
        }
        if (Game.getSetting(307) == 75) {
            step9();
        }
        if (Game.getSetting(307) == 80) {
            getFungi();
        }
        if (Game.getSetting(307) == 85) {
            getFungi();
        }
        if (Game.getSetting(307) == 90) {
            fightGhast();
        }
        if (Game.getSetting(307) == 95) {
            fightGhast();
        }
        if (Game.getSetting(307) == 100) {
            fightGhast();
        }
        if (Game.getSetting(307) == 105) {
            finishQuest();
        }
        if (Game.getSetting(307) == 110) {
            if (Utils.closeQuestCompletionWindow())
                NPCInteraction.waitForConversationWindow();
            if (NPCInteraction.isConversationWindowUp()) {
                NPCInteraction.handleConversation();
                Utils.continuingChat();
            }
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
        return "Nature Spirit";
    }

    @Override
    public boolean checkRequirements() {
        if (Skills.getActualLevel(Skills.SKILLS.ATTACK) < 30) {
            General.println("[Debug]: Missing Attack Level Requirement (30+)");
            return false;
        }
        if (Skills.getActualLevel(Skills.SKILLS.PRAYER) < 30) {
            General.println("[Debug]: Missing Prayer Level Requirement (30+)");
            return false;
        }
        if (Game.getSetting(302) < 60) {
            General.println("[Debug]: Missing Priest in Peril Requirement");
            return false;
        }
        if (Game.getSetting(107) < 5) {
            General.println("[Debug]: Missing Restless ghost Requirement");
            return false;
        }
        return true;
    }
}
