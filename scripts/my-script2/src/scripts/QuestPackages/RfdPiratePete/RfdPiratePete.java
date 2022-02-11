package scripts.QuestPackages.RfdPiratePete;

import dax.walker.utils.AccurateMouse;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.types.*;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.GoblinDiplomacy.GoblinDiplomacy;
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

public class RfdPiratePete implements QuestTask {

    private static RfdPiratePete quest;

    public static RfdPiratePete get() {
        return quest == null ? quest = new RfdPiratePete() : quest;
    }

    int lobster = 379;
    int bread = 2309;
    int rawCod = 341;
    int knife = 946;
    int pestleAndMotar = 233;
    int fishBowl = 6667;
    int needle = 1733;
    int bronzeWire = 1794;
    int runeScimitar = 1333;
    int adamantScimitar = 1331;
    int faladorTab = 8009;
    int lumbridgeTab = 8008;
    int camelotTab = 8010;
    int fishbowlHelmet = 7534;
    int divingApparatus = 7535;
    int mudskipperHide = 7532;
    int crabMeat = 7518;
    int kelp = 7516;
    int cookedCrabCake = 7530;
    int rawCrabCake = 7529;
    int groundKelp = 7517;
    int groundCod = 7528;
    int breadCrumbs = 7515;
    int burntCake = 7531;

    public int[] neededInventoryItems = {bread, rawCod, knife, pestleAndMotar, fishBowl, needle, bronzeWire, faladorTab, lumbridgeTab};
    public boolean hasItems = false;

    public ArrayList<Integer> neededItems = null;

    RSArea rockArea = new RSArea(new RSTile(2946, 9515, 1), new RSTile(2953, 9508, 1));
    RSArea lumbridge = new RSArea(new RSTile(3213, 3225, 0), new RSTile(3216, 3219, 0));
    // RSArea lumbridgeKitchen = new RSArea(new RSTile(10693, 9102, 0), 20);
    RSArea goblinVillage = new RSArea(new RSTile(2959, 3507, 0), new RSTile(2962, 3504, 0));
    RSArea goblinUnderground = new RSArea(new RSTile(6669, 10443, 0), 15);
    RSArea goblinUnderground2 = new RSArea(new RSTile(10315, 11589, 0), 15);
    RSArea oceanStartArea = new RSArea(new RSTile(2945, 9496, 1), new RSTile(3006, 9471, 1));
    RSArea oceanArea = new RSArea(new RSTile(3003, 9473, 1), new RSTile(2942, 9533, 1));
    RSArea mudskipperArea = new RSArea(new RSTile(2955, 9520, 1), new RSTile(2945, 9533, 1));
    RSArea NUNG_AREA = new RSArea(new RSTile(2970, 9514, 1), new RSTile(2984, 9508, 1));

    RSObject[] kelpObj = Objects.findNearest(30, "Kelp");

    RSItem[] invItem1;
    RSItem[] invItem2;

    RSArea cooksKitchenArea = new RSArea(new RSTile(3212, 3212, 0), new RSTile(3205, 3217, 0));
     RSArea portKhazardArea = new RSArea(new RSTile(2666, 3162, 0), new RSTile(2674, 3161, 0));


    InventoryRequirement startInv = new InventoryRequirement(new ArrayList<>(
            Arrays.asList(
                    new ItemReq(ItemID.FALADOR_TELEPORT, 1),
                    new ItemReq(ItemID.BLUE_DYE, 1),
                    new ItemReq(ItemID.ORANGE_DYE, 1),
                    new ItemReq(ItemID.GOBLIN_MAIL, 3),
                    new ItemReq(ItemID.RING_OF_WEALTH[0], 1, true, true),
                    new ItemReq(ItemID.STAMINA_POTION[0], 1, 0)
            ))
    );


    public void checkLevel() {

    }
    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.FALADOR_TELEPORT, 5, 50),
                    new GEItem(ItemID.PESTLE_AND_MORTAR, 1, 400),
                    new GEItem(ItemID.BREAD, 1, 400),
                    new GEItem(ItemID.KNIFE, 1, 400),
                    new GEItem(ItemID.RAW_COD, 2, 400),
                    new GEItem(ItemID.FISHBOWL, 1, 400),
                    new GEItem(ItemID.NEEDLE, 1, 400),
                    new GEItem(ItemID.BRONZE_WIRE, 3, 400),
                    new GEItem(ItemID.LOBSTER, 15, 40),
                    new GEItem(ItemID.CAMELOT_TELEPORT, 3, 40),
                    new GEItem(ItemID.LUMBRIDGE_TELEPORT, 5, 40),
                    new GEItem(ItemID.AMULET_OF_GLORY[2], 2, 20),
                    new GEItem(ItemID.STAMINA_POTION[0], 3, 15),
                    new GEItem(ItemID.RING_OF_WEALTH[0], 1, 25)
            )
    );

    public  void buyItems() {
        cQuesterV2.status = "Buying Items";
        General.println("[Debug]: Buying Items");
        if (Skills.getActualLevel(Skills.SKILLS.ATTACK) >= 40)
            itemsToBuy.add(new GEItem(ItemID.RUNE_SCIMITAR, 1, 20));
        else if (Skills.getActualLevel(Skills.SKILLS.ATTACK) >= 30)
            itemsToBuy.add(new GEItem(ItemID.ADAMANT_SCIMITAR, 1, 50));
        BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);
        buyStep.buyItems();

    }

    public  void getItems() {
        cQuesterV2.status = "Getting Items";
        General.println("[Debug]: " + cQuesterV2.status);
        BankManager.open(true);
        BankManager.depositAll(true);
        BankManager.depositEquipment();
        BankManager.withdraw(4, true, faladorTab);
        BankManager.withdraw(1, true, bread);
        BankManager.withdraw(1, true, knife);
        BankManager.withdraw(5, true, lumbridgeTab);
        BankManager.withdraw(3, true, bronzeWire);
        BankManager.withdraw(12, true, lobster);
        BankManager.withdraw(1, true, needle);
        BankManager.withdraw(2, true, camelotTab);
        BankManager.withdraw(1, true, fishBowl);
        if (Skills.getActualLevel(Skills.SKILLS.ATTACK) >= 40) {
            BankManager.withdraw2(1, true, runeScimitar);
            Utils.equipItem(runeScimitar);
        } else if (Skills.getActualLevel(Skills.SKILLS.ATTACK) >= 30) {
            BankManager.withdraw(1, true, adamantScimitar);
            Utils.equipItem(adamantScimitar);
        }
        BankManager.withdraw(1, true, BankManager.STAMINA_POTION[0]);
        BankManager.close(true);
    }

    int FISH_CAKE = 7530;

    public void goToStart() {
        cQuesterV2.status = "Going to Start";
        if (!BankManager.checkInventoryItems(ItemID.FALADOR_TELEPORT, bread, knife,
                ItemID.LUMBRIDGE_TELEPORT, bronzeWire, lobster, needle, camelotTab, fishBowl) && !BankManager.checkInventoryItems(FISH_CAKE)) {
            buyItems();
            getItems();
        } else {

            PathingUtil.walkToArea(lumbridge);

            if (Utils.clickObj("Large door", "Open")) {
                Timer.waitCondition(() -> Objects.findNearest(20, 12337).length > 0, 8000, 12000);
                General.sleep(General.random(3500, 6000));

            }
        }
    }

    public void inspectPirate() {
        cQuesterV2.status = "Inspecting Pirate Pete";
        General.println("[Debug]: " + cQuesterV2.status);

        if (Utils.clickObj(12337, "Inspect")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            Utils.modSleep();
        }
    }

    public void talkToCook() {
        cQuesterV2.status = "Talking to cook";
        PathingUtil.walkToArea(cooksKitchenArea);
        if (NpcChat.talkToNPC("Cook")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Protecting the Pirate");
            NPCInteraction.handleConversation();
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation(COOK_DIALOGUE);
            NPCInteraction.handleConversation("Where do I get Ground Kelp?");
            NPCInteraction.handleConversation("Where do I get Ground Giant Crab Meat?");
            NPCInteraction.handleConversation("Where do I get Breadcrumbs?");
            NPCInteraction.handleConversation("What do I do with all of it?");
            NPCInteraction.handleConversation();
        }
    }

    String[] COOK_DIALOGUE = {
            "Where do I get Ground Cod?",
            "Where do I get Ground Kelp?",
            "Where do I get Ground Giant Crab Meat?",
            "Where do I get Breadcrumbs?",
            "What do I do with all of it?"
    };

    public void goToPort() {
        if (Inventory.find(divingApparatus).length < 1) {
            cQuesterV2.status = "Going to Port";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(portKhazardArea);
            if (NpcChat.talkToNPC("Murphy")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Talk about Recipe for Disaster.");
                NPCInteraction.handleConversation();
            }
        }
    }

    public void equipSwimGear() {
        if (Utils.equipItem(fishbowlHelmet))
            Utils.microSleep();
        if (Utils.equipItem(divingApparatus))
            Utils.shortSleep();
    }

    public void goDiving() {
        if (!oceanArea.contains(Player.getPosition())) {
            if (Inventory.find(divingApparatus).length < 1) {
                cQuesterV2.status = "Going Diving";
                General.println("[Debug]: " + cQuesterV2.status);
                PathingUtil.walkToArea(portKhazardArea);
                if (NpcChat.talkToNPC("Murphy")) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation("Talk about Recipe for Disaster.", "Yes, Let's go diving.");
                    // NPCInteraction.handleConversation("Yes, Let's go diving.");
                    NPCInteraction.handleConversation();
                }
            }
            Utils.cutScene();
            Timer.abc2WaitCondition(() -> oceanStartArea.contains(Player.getPosition()), 22000, 26000);
        }
    }

    public void getKelp() {
        if (oceanArea.contains(Player.getPosition())) {
            cQuesterV2.status = "Getting kelp";
            kelpObj = Objects.findNearest(30, "Kelp");

            if (kelpObj.length > 0) {
                if (!kelpObj[0].isClickable()) {
                    Walking.blindWalkTo(kelpObj[0].getPosition());
                    Timer.waitCondition(() -> kelpObj[0].isClickable(), 10000, 15000);
                    kelpObj[0].adjustCameraTo();
                }
                if (AccurateMouse.click(kelpObj[0], "Pick")) {
                    PathingUtil.movementIdle();
                    Timer.waitCondition(() -> Player.getAnimation() != -1, 10000, 15000);
                    General.sleep(General.random(300, 1200));
                }
                if (AccurateMouse.click(kelpObj[0], "Pick")) {
                    Timer.waitCondition(() -> Player.getAnimation() != -1, 10000, 15000);
                    General.sleep(General.random(300, 1200));
                }
            }
        }
    }

    public void talkToNung() {
        if (oceanArea.contains(Player.getPosition())) {
            cQuesterV2.status = "Talking to Nung";
            General.println("[Debug]: " + cQuesterV2.status);
            if (PathingUtil.blindWalkToTile(new RSTile(2975, 9510, 1)))
                Timer.abc2WaitCondition(() -> NUNG_AREA.contains(Player.getPosition()), 10000, 15000);

            Utils.idle(750, 2500);
            if (NpcChat.talkToNPC("Nung")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                NPCInteraction.handleConversation();

                General.sleep(General.random(4000, 6000));
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
        }
    }

    public void getRocks() {
        cQuesterV2.status = "Getting Rocks";
        General.println("[Debug]: " + cQuesterV2.status);
        if (!mudskipperArea.contains(Player.getPosition()) && !mudskipperArea.contains(Player.getPosition())) {
            Walking.blindWalkTo(rockArea.getRandomTile());
            Timer.waitCondition(() -> rockArea.contains(Player.getPosition()), 10000, 15000);

            for (int i = 0; i < 7; i++) {
                Utils.clickGroundItem(7533);
                Utils.idle(6500, 8000);
                if (hasRocks)
                    break;
            }
            if (Utils.clickObj("Underwater Cavern Entrance", "Enter"))
                Timer.abc2WaitCondition(() -> mudskipperArea.contains(Player.getPosition()), 9000, 15000);

            Utils.shortSleep();
        }
    }

    public void killMudSkippers() {
        cQuesterV2.status = "Killing mudskippers";
        General.println("[Debug]: " + cQuesterV2.status);
        if (mudskipperArea.contains(Player.getPosition())) {
            General.sleep(General.random(2000, 3000));
            if (Skills.getActualLevel(Skills.SKILLS.ATTACK) >= 40) {

                if (Equipment.find(runeScimitar).length < 1) {
                    Utils.equipItem(runeScimitar);
                    General.sleep(General.random(300, 800));
                }
            } else if (Skills.getActualLevel(Skills.SKILLS.ATTACK) >= 30) {
                General.sleep(General.random(300, 800));
                Utils.equipItem(adamantScimitar);
                General.sleep(General.random(300, 800));
                if (Equipment.find(adamantScimitar).length < 1) {
                    Utils.equipItem(adamantScimitar);
                    General.sleep(General.random(300, 800));
                }
            }
            while (Inventory.find(mudskipperHide).length < 5) {
                General.sleep(100);
                if (!Equipment.isEquipped(runeScimitar))
                    Utils.equipItem(runeScimitar);

                invItem1 = Inventory.find(mudskipperHide);

                RSNPC[] mudskipper = NPCs.findNearest(4820);
                if (mudskipper.length > 0 && !Combat.isUnderAttack()) {
                    General.println("[Debug]: Attacking mudskippers");

                    if (AccurateMouse.click(mudskipper[0], "Attack")) {
                        Timer.waitCondition(() -> Combat.isUnderAttack(), 5000, 8000);
                        CombatUtil.waitUntilOutOfCombat(General.random(40, 60));
                        Utils.shortSleep();
                        //  Timer.abc2WaitCondition(() -> !Combat.isUnderAttack()
                        //           || Combat.getHPRatio() < General.random(51, 61), 30000, 45000);
                    }
                }

                if (Combat.getHPRatio() < General.random(51, 61))
                    eatLobster();

                if (GroundItems.find(mudskipperHide).length > 0) {
                    if (Inventory.isFull())
                        eatLobster();

                    Utils.clickGroundItem(mudskipperHide);
                }
            }
        }
    }

    public void eatLobster() {
        invItem2 = Inventory.find(lobster);
        if (invItem2.length > 0 && invItem2[0].click("Eat"))
            General.sleep(General.random(300, 600));
    }

    public void returnToNung() {
        cQuesterV2.status = "Going to Nung";
        General.println("[Debug]: " + cQuesterV2.status);
        if (mudskipperArea.contains(Player.getPosition())) {
            RSObject[] cavern = Objects.findNearest(20, 12462);
            if (cavern.length > 0) {
                if (Inventory.isFull())
                    eatLobster();

                if (Utils.clickObj(12462, "Exit"))
                    Timer.abc2WaitCondition(() -> rockArea.contains(Player.getPosition()), 9000, 15000);
            }
        }

        if (!mudskipperArea.contains(Player.getPosition())) {
            if (PathingUtil.blindWalkToTile(new RSTile(2975, 9510, 1)))
                Timer.waitCondition(() -> NUNG_AREA.contains(Player.getPosition()), 15000, 20000);

            Utils.idle(750, 2500);

            if (NUNG_AREA.contains(Player.getPosition())) {
                cQuesterV2.status = "Talking to Nung";
                General.println("[Debug]: " + cQuesterV2.status);

                if (NpcChat.talkToNPC("Nung")) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                }
            }
        }
    }

    public void giveNungItems() {
        cQuesterV2.status = "Giving Nung Items";
        General.println("[Debug]: " + cQuesterV2.status);
        if (PathingUtil.blindWalkToTile(new RSTile(2975, 9510, 1)))
            Timer.waitCondition(() -> NUNG_AREA.contains(Player.getPosition()), 15000, 20000);

        Utils.idle(750, 2500);
        if (NpcChat.talkToNPC("Nung")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }
    }

    RSArea PEN_AREA = new RSArea(new RSTile(2985, 9515, 1), new RSTile(2971, 9522, 1));

    public void enterPen() {
        if (!PEN_AREA.contains(Player.getPosition())) {
            Walking.blindWalkTo(new RSTile(2974, 9514, 1));
            General.sleep(General.random(4000, 8000));
        }

        RSObject[] penDoor = Objects.findNearest(20, "Pen Door");
        if (penDoor.length > 0 && Utils.clickObject(penDoor[0], "Open")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            Timer.abc2WaitCondition(() -> PEN_AREA.contains(Player.getPosition()), 8000, 12000);
        }
    }

    public void getCrabMeat() {
        if (PEN_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Killing Crabs";
            General.println("[Debug]: " + cQuesterV2.status);

            while (Inventory.find(crabMeat).length < 3) {
                General.sleep(100);

                if (Skills.getActualLevel(Skills.SKILLS.ATTACK) >= 40)
                    Utils.equipItem(runeScimitar);

                else if (Skills.getActualLevel(Skills.SKILLS.ATTACK) >= 30)
                    Utils.equipItem(adamantScimitar);

                RSNPC[] crab = NPCs.findNearest(4822);
                RSGroundItem[] crabMeatGround = GroundItems.find(crabMeat);

                if (Combat.getHPRatio() < General.random(45, 60)) {
                    EatUtil.eatFood();

                } else if (crabMeatGround.length > 0) {
                    invItem1 = Inventory.find(crabMeat);

                    if (Inventory.isFull())
                        eatLobster();

                    if (AccurateMouse.click(crabMeatGround[0], "Take"))
                        Timer.waitCondition(() -> Inventory.find(crabMeat).length > invItem1.length, 6000, 9000);
                }
                if (crab.length > 0 && !Combat.isUnderAttack()) {
                    General.println("[Debug]: Attacking crab");
                    if (CombatUtil.clickTarget(crab[0])) {
                        Timer.waitCondition(() -> Combat.isUnderAttack(), 5000, 8000);
                        CombatUtil.waitUntilOutOfCombat("Crab", General.random(35, 55));
                    }

                    Utils.shortSleep();
                }
            }

            if (Inventory.find(crabMeat).length >= 3) {
                cQuesterV2.status = "Leaving Pen";

                if (Utils.clickObj("Pen Door", "Open")) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                    Timer.abc2WaitCondition(() -> !PEN_AREA.contains(Player.getPosition()), 8000, 15000);
                }

            }
        }

    }

    public static RSArea leavingArea = new RSArea(new RSTile(2958, 9483, 1), new RSTile(2968, 9475, 1));

    public void leaveUnderWater() {
        if (oceanArea.contains(Player.getPosition())) {
            cQuesterV2.status = "Leaving underwater";
            General.println("[Debug]: " + cQuesterV2.status);
            if (!cooksKitchenArea.contains(Player.getPosition())) {
                PathingUtil.blindWalkToArea(leavingArea);
                Timer.abc2WaitCondition(() -> leavingArea.contains(Player.getPosition()) && !Player.isMoving(), 12000, 18000);
                RSObject[] anchor = Objects.findNearest(20, "Anchor");
                if (anchor.length > 0 && Utils.clickObject(anchor[0], "Climb")) {
                    Utils.idle(1500, 4500);
                    Utils.cutScene();
                    NPCInteraction.waitForConversationWindow();
                    Timer.abc2WaitCondition(() -> portKhazardArea.contains(Player.getPosition()), 15000, 20000);
                }
            }
        }
    }

    public static boolean hasRocks = false;

    public static void handleRockMessage(String message) {
        if (message.contains("enough rock")) {
            General.println("[Debug]: Detected rock message");
            hasRocks = true;
        }
    }

    public void makeCake() {
        cQuesterV2.status = "Making cakes";
        General.println("[Debug]: " + cQuesterV2.status);
        if (!cooksKitchenArea.contains(Player.getPosition())) {
            getItemsFinal();
            PathingUtil.walkToArea(cooksKitchenArea);
        }

        if (Inventory.find(7529).length < 1
                && Inventory.find(groundCod).length < 1 && Inventory.find(groundKelp).length < 1) {
            if (Utils.useItemOnItem(kelp, pestleAndMotar))
                General.sleep(General.random(800, 1800));
            if (Utils.useItemOnItem(rawCod, pestleAndMotar))
                General.sleep(General.random(800, 1800));
            if (Utils.useItemOnItem(knife, bread))
                General.sleep(General.random(800, 1800));
            if (Utils.useItemOnItem(crabMeat, pestleAndMotar))
                General.sleep(General.random(1600, 3800));

        }
        talkToCookAgain();

        if (Utils.useItemOnItem(7517, 7515))
            Timer.waitCondition(() -> Inventory.find(7529).length > 0, 8000, 10000);
    }

    public void cookCake() {
        cQuesterV2.status = "Cooking cake";
        General.println("[Debug]: " + cQuesterV2.status);

        if (Utils.useItemOnObject(7529, "Cooking Range"))
            Timer.abc2WaitCondition(() -> Inventory.find(7530).length > 0, 8000, 10000);

        if (Inventory.find(burntCake).length > 0 && Inventory.find(cookedCrabCake).length < 1) {
            makeCake();
        }
    }

    public void getItemsFinal() {
        if (!oceanArea.contains(Player.getPosition())) {
            cQuesterV2.status = "Getting items to finish";
            General.println("[Debug]: " + cQuesterV2.status);
            BankManager.open(true);
            BankManager.withdraw(1, true, lumbridgeTab);
            BankManager.withdraw(1, true, pestleAndMotar);
            BankManager.withdraw(2, true, rawCod);
            BankManager.withdraw(1, true, bread);
            BankManager.withdraw(1, true, kelp);
            BankManager.withdraw(1, true, knife);
            BankManager.withdraw(1, true, crabMeat);
            BankManager.close(true);
        }
    }

    public void talkToCookAgain() {
        cQuesterV2.status = "Talking to cook";
        if (!cooksKitchenArea.contains(Player.getPosition())) {
            PathingUtil.walkToArea(cooksKitchenArea);
        }
        if (NpcChat.talkToNPC("Cook")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Protecting the Pirate");
            NPCInteraction.handleConversation("What do I do with all of it?");
            NPCInteraction.handleConversation();
        }
    }

    public void finishQuest() {
        if (Inventory.find(7530).length > 0) {
            cQuesterV2.status = "Finishing Quest";
            General.println("[Debug]: " + cQuesterV2.status);
            goToStart();
            General.sleep(General.random(2500, 5000));

            if (Utils.useItemOnObject(7530, 12337)) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
        }
    }


    @Override
    public void execute() {
        int varbit = QuestVarbits.QUEST_RECIPE_FOR_DISASTER_PIRATE_PETE.getId();
        // old game settingsi 683
        if (Utils.getVarBitValue(varbit) == 0) {
            buyItems();
            getItems();
            goToStart();
            inspectPirate();
        }
        if (Utils.getVarBitValue(varbit) == 10) {
            talkToCook();
        }
        if (Utils.getVarBitValue(varbit) == 20 || (Utils.getVarBitValue(varbit) == 30)) {
            goToPort();
        }
        if (Utils.getVarBitValue(varbit) == 40) {
            equipSwimGear();
            goDiving();
            getKelp();
            talkToNung();
        }
        if (Utils.getVarBitValue(varbit) == 50) {
            getRocks();
            killMudSkippers();
            returnToNung();
        }
        if (Utils.getVarBitValue(varbit) == 60) {
            returnToNung();
        }
        if (Utils.getVarBitValue(varbit) == 70) {
            giveNungItems();
        }
        if (Utils.getVarBitValue(varbit) == 80) {
            enterPen();
            getCrabMeat();
        }
        if (Utils.getVarBitValue(varbit) == 90) {
            getCrabMeat();
            leaveUnderWater();
            makeCake();
        }
        if (Utils.getVarBitValue(varbit) == 100) {
            cookCake();
            finishQuest();
        }
        if (Utils.getVarBitValue(varbit) == 110) {
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
        return "RFD Pirate Pete (" + Utils.getVarBitValue(
                QuestVarbits.QUEST_RECIPE_FOR_DISASTER_PIRATE_PETE.getId()) + ")";
    }

    @Override
    public boolean checkRequirements() {
       return Skills.getActualLevel(Skills.SKILLS.COOKING) > 30;
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
