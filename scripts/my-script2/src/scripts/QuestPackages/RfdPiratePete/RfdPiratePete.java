package scripts.QuestPackages.RfdPiratePete;

import dax.walker.utils.AccurateMouse;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.Combat;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.*;
import org.tribot.script.sdk.*;
import org.tribot.script.sdk.Equipment;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.*;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestSteps.BuyItemsStep;
import scripts.QuestSteps.NPCStep;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.*;
import scripts.Requirements.Util.Conditions;
import scripts.Tasks.Priority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class RfdPiratePete implements QuestTask {

    private static RfdPiratePete quest;

    public static RfdPiratePete get() {
        return quest == null ? quest = new RfdPiratePete() : quest;
    }

    int lobster = 379;
    int bread = 2309;

    int knife = 946;
    int pestleAndMotar = 233;
    int fishBowl = 6667;

    int bronzeWire = 1794;


    int fishbowlHelmet = 7534;
    int divingApparatus = 7535;
    int mudskipperHide = 7532;
    int crabMeat = 7518;
    int kelp = 7516;
    int cookedCrabCake = 7530;
    int groundKelp = 7517;
    int groundCod = 7528;

    int burntCake = 7531;

    boolean hasKelp = false;
    boolean hasMudSkipperHides = false;

    public int[] neededInventoryItems = {bread, ItemID.RAW_COD, knife, pestleAndMotar, fishBowl, ItemID.NEEDLE, bronzeWire, ItemID.FALADOR_TELEPORT, ItemID.LUMBRIDGE_TELEPORT};
    public boolean hasItems = false;

    public ArrayList<Integer> neededItems = null;

    RSArea rockArea = new RSArea(new RSTile(2946, 9515, 1), new RSTile(2953, 9508, 1));
    Area lumbridge = Area.fromRectangle(new WorldTile(3213, 3225, 0), new WorldTile(3216, 3219, 0));
    // RSArea lumbridgeKitchen = new RSArea(new RSTile(10693, 9102, 0), 20);
    RSArea goblinVillage = new RSArea(new RSTile(2959, 3507, 0), new RSTile(2962, 3504, 0));
    RSArea goblinUnderground = new RSArea(new RSTile(6669, 10443, 0), 15);
    RSArea goblinUnderground2 = new RSArea(new RSTile(10315, 11589, 0), 15);
    RSArea oceanStartArea = new RSArea(new RSTile(2945, 9496, 1), new RSTile(3006, 9471, 1));
    Area WHOLE_OCEAN = Area.fromRectangle(new WorldTile(3003, 9473, 1), new WorldTile(2942, 9533, 1));
    Area mudskipperArea = Area.fromRectangle(new WorldTile(2955, 9520, 1), new WorldTile(2945, 9533, 1));
    Area NUNG_AREA = Area.fromRectangle(new WorldTile(2970, 9514, 1), new WorldTile(2984, 9508, 1));

    RSObject[] kelpObj = Objects.findNearest(30, "Kelp");

    RSItem[] invItem1;
    RSItem[] invItem2;

    RSArea cooksKitchenArea = new RSArea(new RSTile(3212, 3212, 0), new RSTile(3205, 3217, 0));
    RSArea portKhazardArea = new RSArea(new RSTile(2666, 3162, 0), new RSTile(2674, 3161, 0));
    NPCStep talkToNung = new NPCStep("Nung", new RSTile(2975, 9510, 1));

    //TODO get teh rest of these


    InventoryRequirement startInv = new InventoryRequirement(new ArrayList<>(
            Arrays.asList(
                    new ItemReq(ItemID.FALADOR_TELEPORT, 4),
                    new ItemReq(ItemID.BREAD, 1),
                    new ItemReq(ItemID.KNIFE, 1),
                    new ItemReq(ItemID.BRONZE_WIRE, 3),
                    new ItemReq(ItemID.LOBSTER, 12),
                    new ItemReq(ItemID.NEEDLE, 1),
                    new ItemReq(ItemID.EMPTY_FISHBOWL, 1),
                    new ItemReq(ItemID.LUMBRIDGE_TELEPORT, 5),
                    new ItemReq(ItemID.CAMELOT_TELEPORT, 2),
                    new ItemReq(ItemID.RING_OF_WEALTH[0], 1, true, true),
                    new ItemReq(ItemID.STAMINA_POTION[0], 1, 0)
            ))
    );

    VarbitRequirement walkingUnderwater = new VarbitRequirement(1871, 1);
    RSArea  underwater = new RSArea(new RSTile(2944, 9472, 1), new RSTile(3007, 9534, 1));

    VarbitRequirement canGrindCod = new VarbitRequirement(1877, 1);

    Conditions askedCookOptions = new Conditions(
			new VarbitRequirement(1873, 1),
			new VarbitRequirement(1876, 1),
			new VarbitRequirement(1877, 1));

    AreaRequirement inUnderWater = new AreaRequirement(underwater);
    VarbitRequirement  hasEnoughRocks = new VarbitRequirement(1869, 5);


    public void checkLevel() {

    }

    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.FALADOR_TELEPORT, 5, 50),
                    new GEItem(ItemID.PESTLE_AND_MORTAR, 1, 400),
                    new GEItem(ItemID.BREAD, 3, 400),
                    new GEItem(ItemID.KNIFE, 1, 400),
                    new GEItem(ItemID.RAW_COD, 3, 400),
                    new GEItem(ItemID.EMPTY_FISHBOWL, 1, 400),
                    new GEItem(ItemID.NEEDLE, 1, 400),
                    new GEItem(ItemID.BRONZE_WIRE, 3, 400),
                    new GEItem(ItemID.LOBSTER, 15, 40),
                    new GEItem(ItemID.CAMELOT_TELEPORT, 3, 40),
                    new GEItem(ItemID.LUMBRIDGE_TELEPORT, 5, 40),
                    new GEItem(ItemID.AMULET_OF_GLORY[2], 1, 20),
                    new GEItem(ItemID.STAMINA_POTION[0], 3, 15),
                    new GEItem(ItemID.RING_OF_WEALTH[0], 1, 25)
            )
    );

    public void buyItems() {
        cQuesterV2.status = "Buying Items";
        Log.info("Buying Items");
        if (Skills.getActualLevel(Skills.SKILLS.ATTACK) >= 40)
            itemsToBuy.add(new GEItem(ItemID.RUNE_SCIMITAR, 1, 20));
        else if (Skills.getActualLevel(Skills.SKILLS.ATTACK) >= 30)
            itemsToBuy.add(new GEItem(ItemID.ADAMANT_SCIMITAR, 1, 50));
        BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);
        buyStep.buyItems();

    }

    public void getItems() {
        cQuesterV2.status = "Getting Items";
        Log.info(cQuesterV2.status);
        BankManager.open(true);
        BankManager.depositAll(true);
        BankManager.depositEquipment();
        startInv.withdrawItems();
       /* BankManager.withdraw(4, true, ItemID.FALADOR_TELEPORT);
        BankManager.withdraw(1, true, bread);
        BankManager.withdraw(1, true, knife);
        BankManager.withdraw(5, true, ItemID.LUMBRIDGE_TELEPORT);
        BankManager.withdraw(3, true, bronzeWire);
        BankManager.withdraw(12, true, lobster);
        BankManager.withdraw(1, true, ItemID.NEEDLE);
        BankManager.withdraw(2, true, ItemID.CAMELOT_TELEPORT);
        BankManager.withdraw(1, true, fishBowl);*/
        if (Skills.getActualLevel(Skills.SKILLS.ATTACK) >= 40) {
            BankManager.withdraw2(1, true, ItemID.RUNE_SCIMITAR);
            Utils.equipItem(ItemID.RUNE_SCIMITAR);
        } else if (Skills.getActualLevel(Skills.SKILLS.ATTACK) >= 30) {
            BankManager.withdraw(1, true, ItemID.ADAMANT_SCIMITAR);
            Utils.equipItem(ItemID.ADAMANT_SCIMITAR);
        }
       // BankManager.withdraw(1, true, BankManager.STAMINA_POTION[0]);
        BankManager.close(true);
    }

    int FISH_CAKE = 7530;

    public void goToStart() {
        cQuesterV2.status = "Going to Start";
        if (!BankManager.checkInventoryItems(ItemID.FALADOR_TELEPORT, bread, knife,
                ItemID.LUMBRIDGE_TELEPORT, bronzeWire, ItemID.LOBSTER, ItemID.NEEDLE, ItemID.CAMELOT_TELEPORT, ItemID.EMPTY_FISHBOWL) && !BankManager.checkInventoryItems(FISH_CAKE)) {
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
        Log.info(cQuesterV2.status);

        if (Utils.clickObj(12337, "Inspect")) {
            NpcChat.handle(true);
            Utils.idleNormalAction(true);
        }
    }

    public void talkToCook() {
        cQuesterV2.status = "Talking to cook";
        PathingUtil.walkToArea(cooksKitchenArea);
        if (NpcChat.talkToNPC("Cook")) {
            Log.info("In ChatScreen");
            NpcChat.handle(true, "Protecting the Pirate");
           // NpcChat.handle(true);
            ChatScreen.handle(()-> ChatScreen.isSelectOptionOpen(), "Where do I get Ground Cod?");
            //NPCInteraction.handleConversation("Where do I get Ground Cod?");
            Log.info("Done ChatScreen");
            NPCInteraction.handleConversation("Where do I get Ground Cod?");
            NPCInteraction.handleConversation("Where do I get Ground Kelp?");
            NPCInteraction.handleConversation("Where do I get Ground Giant Crab Meat?");
            NPCInteraction.handleConversation("Where do I get Breadcrumbs?");
            NPCInteraction.handleConversation("What do I do with all of it?");
        }
    }

    String[] COOK_DIALOGUE = {
            "Where do I get Ground Cod?",
            "Where do I get Ground Kelp?",
            "Where do I get Ground Giant Crab Meat?",
            "Where do I get Breadcrumbs?",
            "What do I do with all of it?"
    };

    private boolean isStanding() {
        return Utils.getVarBitValue(1871) == 1;
    }

    public void goToPort() {
        if (Inventory.find(divingApparatus).length < 1) {
            cQuesterV2.status = "Going to Port";
            Log.info(cQuesterV2.status);
            PathingUtil.walkToArea(portKhazardArea);
            if (NpcChat.talkToNPC("Murphy")) {
                NpcChat.handle(true, "Talk about Recipe for Disaster.");
            }
        }
    }

    public void equipSwimGear() {
        if (Utils.equipItem(fishbowlHelmet))
            Waiting.waitUntil(2500, 750, ()-> Equipment.contains(fishbowlHelmet));
        if (Utils.equipItem(divingApparatus))
            Waiting.waitUntil(2500, 750, ()-> Equipment.contains(divingApparatus));
    }

    public void goDiving() {
        if (!WHOLE_OCEAN.containsMyPlayer()) {
            if (Inventory.find(divingApparatus).length < 1) {
                cQuesterV2.status = "Going Diving";
                Log.info(cQuesterV2.status);
                PathingUtil.walkToArea(portKhazardArea);
                if (NpcChat.talkToNPC("Murphy")) {
                    NpcChat.handle(true,
                            "Talk about Recipe for Disaster.", "Yes, Let's go diving.");
                }
            }
            Utils.cutScene();
            Timer.waitCondition(() -> inUnderWater.check(), 22000, 26000);
        }
    }

    public void getKelp() {
        if (WHOLE_OCEAN.containsMyPlayer()) {
            cQuesterV2.status = "Getting kelp";
            Optional<GameObject> kelp = Query.gameObjects()
                    .nameContains("Kelp")
                    .actionContains("Pick").findClosestByPathDistance();
            for (int i = 0; i < 3; i++) {
                if (kelp.map(k -> k.interact("Pick")).orElse(false)) {
                    PathingUtil.movementIdle();
                    Timer.waitCondition(() -> MyPlayer.getAnimation() != -1, 10000, 15000);
                    Utils.idleNormalAction(true);
                }
            }
        }
    }

    public void talkToNung() {
        if (WHOLE_OCEAN.containsMyPlayer()) {
            cQuesterV2.status = "Talking to Nung";
            Log.info(cQuesterV2.status);
            if (PathingUtil.blindWalkToTile(new RSTile(2975, 9510, 1)))
                Timer.slowWaitCondition(() -> NUNG_AREA.containsMyPlayer(), 10000, 15000);

            Optional<Npc> nung = Query.npcs()
                    .nameContains("Nung")
                    .findClosestByPathDistance();
            if (nung.map(n -> n.interact("Talk-to")).orElse(false)) {
                Waiting.waitUntil(5500, () -> ChatScreen.isOpen());
                NpcChat.handle(true);

                Waiting.waitUntil(3500, () -> ChatScreen.isOpen());
                NpcChat.handle(true);
            }
        }
    }

    public void getRocks() {
        cQuesterV2.status = "Getting Rocks";
        Log.info(cQuesterV2.status);
        if (!mudskipperArea.containsMyPlayer() && !mudskipperArea.containsMyPlayer()) {
            Walking.blindWalkTo(rockArea.getRandomTile());
            Timer.waitCondition(() -> rockArea.contains(Player.getPosition()), 10000, 15000);

            for (int i = 0; i < 5; i++) {
                Optional<GroundItem> rock = Query.groundItems()
                        .idEquals(7533)
                        .actionContains("Take")
                        .maxDistance(2)
                        .findClosestByPathDistance();
                if (rock.isEmpty()) {
                    Waiting.waitUntil(7500, 200, () -> Query.gameObjects()
                            .idEquals(7533)
                            .maxDistance(2)
                            .findClosestByPathDistance().isPresent());
                }
                if (rock.map(r -> r.interact("Take")).orElse(false)) {
                    Waiting.waitUntil(5000, () -> MyPlayer.getAnimation() != -1);
                }
                if (hasRocks)
                    break;

            }
            if (Utils.clickObj("Underwater Cavern Entrance", "Enter"))
                Timer.waitCondition(() -> mudskipperArea.containsMyPlayer() &&
                        isStanding(), 11000, 15000);
        }
    }

    public void handleWeapon() {
        if (Skills.getActualLevel(Skills.SKILLS.ATTACK) >= 40) {
            if (Utils.equipItem(ItemID.RUNE_SCIMITAR))
                Utils.idleNormalAction(true);

        } else if (Skills.getActualLevel(Skills.SKILLS.ATTACK) >= 30) {
            if (Utils.equipItem(ItemID.ADAMANT_SCIMITAR))
                Utils.idleNormalAction(true);
        }
    }

    public void killMudSkippers() {
        cQuesterV2.status = "Killing mudskippers";
        Log.info(cQuesterV2.status);
        if (mudskipperArea.containsMyPlayer()) {
            General.sleep(2000, 3000);
            handleWeapon();

            while (Inventory.find(mudskipperHide).length < 5) {
                General.sleep(50, 100);
                handleWeapon();

                invItem1 = Inventory.find(mudskipperHide);

                Optional<Npc> mudSkipper = Query.npcs().idEquals(4820).findClosestByPathDistance();
                if (!MyPlayer.isHealthBarVisible()) {
                    Log.info("Attacking mudskippers");

                    if (mudSkipper.map(m -> m.interact("Attack")).orElse(false) &&
                            Timer.waitCondition(MyPlayer::isHealthBarVisible, 5000, 8000)) {
                        CombatUtil.waitUntilOutOfCombat(General.random(40, 60));
                        Utils.idleNormalAction();
                    }
                }

                if (MyPlayer.getCurrentHealthPercent() < General.random(51, 65))
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
            Utils.idleNormalAction(true);
    }

    public void returnToNung() {
        cQuesterV2.status = "Going to Nung";
        Log.info(cQuesterV2.status);
        if (mudskipperArea.containsMyPlayer()) {
            RSObject[] cavern = Objects.findNearest(20, 12462);
            if (cavern.length > 0) {
                if (Inventory.isFull())
                    eatLobster();

                if (Utils.clickObj(12462, "Exit"))
                    Timer.abc2WaitCondition(() -> rockArea.contains(Player.getPosition()), 9000, 15000);

                if (Waiting.waitUntil(5000, 500, () -> MyPlayer.getAnimation() != -1)) {
                    Waiting.waitUntil(4000, 400, () -> MyPlayer.getAnimation() == -1);
                }
            }
        }

        if (!mudskipperArea.containsMyPlayer()) {
            if (PathingUtil.blindWalkToTile(new RSTile(2975, 9510, 1)))
                Timer.waitCondition(() -> NUNG_AREA.containsMyPlayer(), 15000, 20000);

            Utils.idle(750, 2500);

            cQuesterV2.status = "Talking to Nung";
            Log.info(cQuesterV2.status);
            if (NpcChat.talkToNPC("Nung")) {
                NpcChat.handle(true);
            }

        }
    }

    public void giveNungItems() {
        cQuesterV2.status = "Giving Nung Items";
        Log.info(cQuesterV2.status);
        if (PathingUtil.blindWalkToTile(new RSTile(2975, 9510, 1)))
            Timer.waitCondition(() -> NUNG_AREA.containsMyPlayer(), 15000, 20000);

        Utils.idle(750, 2500);
        if (NpcChat.talkToNPC("Nung")) {
            NpcChat.handle(true);
        }
    }

    Area PEN_AREA = Area.fromRectangle(new WorldTile(2985, 9515, 1), new WorldTile(2971, 9522, 1));

    public void enterPen() {
        if (!PEN_AREA.containsMyPlayer()) {
            Walking.blindWalkTo(new RSTile(2974, 9514, 1));
            General.sleep(General.random(4000, 8000));
        }

        RSObject[] penDoor = Objects.findNearest(20, "Pen Door");
        if (penDoor.length > 0 && Utils.clickObject(penDoor[0], "Open")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            Timer.abc2WaitCondition(() -> PEN_AREA.containsMyPlayer(), 8000, 12000);
        }
    }

    public void getCrabMeat() {
        if (PEN_AREA.containsMyPlayer()) {
            cQuesterV2.status = "Killing Crabs";
            Log.info(cQuesterV2.status);

            while (Inventory.find(crabMeat).length < 3) {
                General.sleep(100);

                if (Skills.getActualLevel(Skills.SKILLS.ATTACK) >= 40)
                    Utils.equipItem(ItemID.RUNE_SCIMITAR);

                else if (Skills.getActualLevel(Skills.SKILLS.ATTACK) >= 30)
                    Utils.equipItem(ItemID.ADAMANT_SCIMITAR);

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
                if (crab.length > 0 && !MyPlayer.isHealthBarVisible()) {
                    Log.info("Attacking crab");
                    if (CombatUtil.clickTarget(crab[0])) {
                        Timer.waitCondition(() -> MyPlayer.isHealthBarVisible(), 5000, 8000);
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
                    Timer.abc2WaitCondition(() -> !PEN_AREA.containsMyPlayer(), 8000, 15000);
                }

            }
        }

    }

    public static RSArea leavingArea = new RSArea(new RSTile(2958, 9483, 1), new RSTile(2968, 9475, 1));

    public void leaveUnderWater() {
        if (WHOLE_OCEAN.containsMyPlayer()) {
            cQuesterV2.status = "Leaving underwater";
            Log.info(cQuesterV2.status);
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
            Log.info("Detected rock message");
            hasRocks = true;
        }
    }

    public void makeCake() {
        cQuesterV2.status = "Making cakes";
        Log.info(cQuesterV2.status);
        if (!cooksKitchenArea.contains(Player.getPosition())) {
            getItemsFinal();
            PathingUtil.walkToArea(cooksKitchenArea);
        }

        if (Inventory.find(7529).length < 1
                && Inventory.find(groundCod).length < 1 && Inventory.find(groundKelp).length < 1) {
            if (Utils.useItemOnItem(kelp, pestleAndMotar))
                General.sleep(General.random(800, 1800));
            if (Utils.useItemOnItem(ItemID.RAW_COD, pestleAndMotar))
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
        Log.info(cQuesterV2.status);

        if (Utils.useItemOnObject(7529, "Cooking Range"))
            Timer.abc2WaitCondition(() -> Inventory.find(7530).length > 0, 8000, 10000);

        if (Inventory.find(burntCake).length > 0 &&
                Inventory.find(cookedCrabCake).length < 1) {
            makeCake();
        }
    }

    public void getItemsFinal() {
        if (!WHOLE_OCEAN.containsMyPlayer()) {
            cQuesterV2.status = "Getting items to finish";
            Log.info(cQuesterV2.status);
            BankManager.open(true);
            BankManager.withdraw(1, true, ItemID.LUMBRIDGE_TELEPORT);
            BankManager.withdraw(1, true, pestleAndMotar);
            BankManager.withdraw(2, true, ItemID.RAW_COD);
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
            NpcChat.handle(true,"Protecting the Pirate" ,
                    "What do I do with all of it?");
        }
    }

    public void finishQuest() {
        if (Inventory.find(7530).length > 0) {
            cQuesterV2.status = "Finishing Quest";
            Log.info(cQuesterV2.status);
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
        if (GameState.getSetting(683) == 110) {
            Utils.closeQuestCompletionWindow();
            cQuesterV2.taskList.remove(this);
        } else if (GameState.getSetting(683) == 0) {
            buyItems();
            getItems();
            goToStart();
            inspectPirate();
        } else if (GameState.getSetting(683) == 10) {
            talkToCook();
        } else if (GameState.getSetting(683) == 20 || (GameState.getSetting(683) == 30)) {
            goToPort();
        } else if (GameState.getSetting(683) == 40) {
            equipSwimGear();
            goDiving();
            getKelp();
            talkToNung();
        } else if (GameState.getSetting(683) == 50) {
            getRocks();
            killMudSkippers();
            returnToNung();
        } else if (GameState.getSetting(683) == 60) {
            returnToNung();
        } else if (GameState.getSetting(683) == 70) {
            giveNungItems();
        } else if (GameState.getSetting(683) == 80) {
            enterPen();
            getCrabMeat();
        } else if (GameState.getSetting(683) == 90) {
            getCrabMeat();
            leaveUnderWater();
            makeCake();
        } else if (GameState.getSetting(683) == 100) {
            makeCake();
            cookCake();
            finishQuest();
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

    @Override
    public boolean isComplete() {
        return GameState.getSetting(683) == 110;
    }
}
