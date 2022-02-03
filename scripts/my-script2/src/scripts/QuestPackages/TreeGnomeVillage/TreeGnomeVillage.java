package scripts.QuestPackages.TreeGnomeVillage;

import dax.walker.utils.AccurateMouse;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.ext.Doors;
import org.tribot.api2007.types.*;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.FightArena.FightArena;
import scripts.QuestSteps.*;
import scripts.Requirements.*;
import scripts.Requirements.Util.Operation;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;
import scripts.Timer;

import java.util.*;
import java.util.Objects;

public class TreeGnomeVillage implements QuestTask {

    private static TreeGnomeVillage quest;

    public static TreeGnomeVillage get() {
        return quest == null ? quest = new TreeGnomeVillage() : quest;
    }

    //Items Required
    ItemReq logRequirement, orbsOfProtection;

    private NPCStep talkToCommanderMontai, bringWoodToCommanderMontai, talkToCommanderMontaiAgain,
            firstTracker, secondTracker, thirdTracker, fireBallista, fireBallista1, fireBallista2, fireBallista3, fireBallista4, climbTheLadder,
            talkToKingBolrenFirstOrb, talkToTheWarlord, fightTheWarlord, returnOrbs, finishQuestDialog, elkoySkip;

    Requirement completeFirstTracker, completeSecondTracker, completeThirdTracker, handedInOrbs,
            notCompleteFirstTracker, notCompleteSecondTracker, notCompleteThirdTracker, orbsOfProtectionNearby, givenWood;

    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.GAMES_NECKLACE[0], 2, 0),
                    new GEItem(ItemID.AMULET_OF_GLORY[2], 2, 0)
            )
    );


    BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);

    //Zones
    RSArea upstairsTower, zoneVillage;
    AreaRequirement isUpstairsTower, insideGnomeVillage;
    /*
        private final int TRACKER_1_VARBITID = 599;
        private final int TRACKER_2_VARBITID = 600;
        private final int TRACKER_3_VARBITID = 601;

        @Override
        public Map<Integer, QuestStep> loadSteps()
        {
            setupZones();
            ItemReqs();
            setupConditions();
            setupSteps();

            return CreateSteps();
        }

        private Map<Integer, QuestStep> CreateSteps()
        {
            Map<Integer, QuestStep> steps = new HashMap<>();
            steps.put(0, talkToBolrenAtCentreOfMaze);
            steps.put(1, talkToCommanderMontai);
            steps.put(2, bringWoodToCommanderMontai);
            steps.put(3, talkToCommanderMontaiAgain);
            steps.put(4, talkToTrackersStep());
            steps.put(5, retrieveOrbStep());
            steps.put(6, returnFirstOrb);
            steps.put(7, defeatWarlordStep());
            steps.put(8, returnOrbsStep());
            return steps;
        }


        private QuestStep retrieveOrbStep()
        {
            retrieveOrb = new ConditionalStep( climbTheLadder, "Enter the tower by the Crumbled wall and climb the ladder to retrieve the first orb from chest.");
            ObjectStep getOrbFromChest = new ObjectStep( ObjectID.CLOSED_CHEST_2183, new RSTile(2506, 3259, 1), "Retrieve the first orb from chest.");
            getOrbFromChest.addAlternateObjects(ObjectID.OPEN_CHEST_2182);
            retrieveOrb.addStep(isUpstairsTower, getOrbFromChest);
            retrieveOrb.addSubSteps(getOrbFromChest, climbTheLadder);
            return retrieveOrb;
        }

        private QuestStep defeatWarlordStep() {

            fightTheWarlord = new NPCStep( NpcID.KHAZARD_WARLORD_7622, new RSTile(2456, 3301, 0),
                    "Defeat the warlord and retrieve orbs.");
            talkToTheWarlord = new NPCStep( NpcID.KHAZARD_WARLORD_7621, new RSTile(2456, 3301, 0),
                    "Talk to the Warlord south west of West Ardougne, ready to fight him.");

            ItemReq food = new ItemReq("Food", ItemCollections.getGoodEatingFood(), -1);

            ItemReq combatGear = new ItemReq("A Weapon & Armour (magic is best)", -1);
            combatGear.setDisplayItemID(BankSlotIcons.getMagicCombatGear());

            ConditionalStep defeatTheWarlord = new ConditionalStep( talkToTheWarlord,
                    food,
                    combatGear);

            defeatTheWarlord.addStep(fightingWarlord, fightTheWarlord);


            return defeatTheWarlord;
        }

        private QuestStep returnOrbsStep()
        {
            handedInOrbs = new VarbitRequirement(598, 1, Operation.GREATER_EQUAL);

            orbsOfProtectionNearby = new ItemOnTileRequirement(ItemID.ORBS_OF_PROTECTION);

            ItemStep pickupOrb = new ItemStep(
                    "Pick up the nearby Orbs of Protection.", orbsOfProtection);
            returnOrbs.addSubSteps(pickupOrb);

            ConditionalStep returnOrbsSteps = new ConditionalStep( returnOrbs);
            returnOrbsSteps.addStep(orbsOfProtectionNearby, pickupOrb);
            returnOrbsSteps.addStep(handedInOrbs, finishQuestDialog);

            return returnOrbsSteps;
        }

        private void setupItemReqs()
        {
            givenWood = new VarplayerRequirement(QuestVarPlayer.QUEST_TREE_GNOME_VILLAGE.getId(), 3, Operation.GREATER_EQUAL);
            logRequirement = new ItemReq("Logs", ItemID.LOGS, 6).hideConditioned(givenWood);
            orbsOfProtection = new ItemReq("Orbs of protection", ItemID.ORBS_OF_PROTECTION);
            orbsOfProtection.setTooltip("You can retrieve the orbs of protection again by killing the Khazard Warlord again.");
        }

        private void setupZones() {
            upstairsTower = new Zone(new RSTile(2500, 3251, 1), new RSTile(2506, 3259, 1));
            zoneVillage = new Zone(new RSTile(2514, 3158, 0), new RSTile(2542, 3175, 0));
        }

        public void setupConditions()
        {
            notCompleteFirstTracker = new VarbitRequirement(TRACKER_1_VARBITID, 0);
            notCompleteSecondTracker = new VarbitRequirement(TRACKER_2_VARBITID, 0);
            notCompleteThirdTracker = new VarbitRequirement(TRACKER_3_VARBITID, 0);

            completeFirstTracker = new VarbitRequirement(TRACKER_1_VARBITID, 1);
            completeSecondTracker = new VarbitRequirement(TRACKER_2_VARBITID, 1);
            completeThirdTracker = new VarbitRequirement(TRACKER_3_VARBITID, 1);

            insideGnomeVillage = new ZoneRequirement(zoneVillage);
            isUpstairsTower = new ZoneRequirement(upstairsTower);

            talkToSecondTracker = new Conditions(LogicType.AND, completeFirstTracker, notCompleteSecondTracker);
            talkToThirdTracker = new Conditions(LogicType.AND, completeFirstTracker, notCompleteThirdTracker);

            completedTrackers = new Conditions(LogicType.AND, completeFirstTracker, completeSecondTracker, completeThirdTracker);

            shouldFireBallista1 = new Conditions(LogicType.AND, completedTrackers, new VarbitRequirement(602, 0));
            shouldFireBallista2 = new Conditions(LogicType.AND, completedTrackers, new VarbitRequirement(602, 1));
            shouldFireBallista3 = new Conditions(LogicType.AND, completedTrackers, new VarbitRequirement(602, 2));
            shouldFireBallista4 = new Conditions(LogicType.AND, completedTrackers, new VarbitRequirement(602, 3));
        }

        private void setupSteps()
        {
            NPCStep talkToKingBolren = new NPCStep( NpcID.KING_BOLREN, new RSTile(2541, 3170, 0), "");
            talkToKingBolren.addDialogStep("Can I help at all?");
            talkToKingBolren.addDialogStep("I would be glad to help.");


            talkToCommanderMontai = new NPCStep( NpcID.COMMANDER_MONTAI, new RSTile(2523, 3208, 0), "Speak with Commander Montai.");
            talkToCommanderMontai.addDialogStep("Ok, I'll gather some wood.");

            bringWoodToCommanderMontai = new NPCStep( NpcID.COMMANDER_MONTAI, new RSTile(2523, 3208, 0), "Speak with Commander Montai again to give him the wood.", logRequirement);

            talkToCommanderMontaiAgain = new NPCStep( NpcID.COMMANDER_MONTAI, new RSTile(2523, 3208, 0), "Speak with Commander Montai.");
            talkToCommanderMontaiAgain.addDialogStep("I'll try my best.");

            firstTracker = new NPCStep( NpcID.TRACKER_GNOME_1, new RSTile(2501, 3261, 0), "Talk to the first tracker gnome to the northwest.");
            secondTracker = new NPCStep( NpcID.TRACKER_GNOME_2, new RSTile(2524, 3257, 0), "Talk to the second tracker gnome inside the jail.");
            thirdTracker = new NPCStep( NpcID.TRACKER_GNOME_3, new RSTile(2497, 3234, 0), "Talk to the third tracker gnome to the southwest.");

            climbTheLadder = new ObjectStep(16683, new RSTile(2503, 3252, 0), "Climb the ladder");

            ItemReq firstOrb = new ItemReq("Orb of protection", ItemID.ORB_OF_PROTECTION, 1);

            talkToKingBolrenFirstOrb = new NPCStep( NpcID.KING_BOLREN, new RSTile(2541, 3170, 0),
                    "Speak to King Bolren in the centre of the Tree Gnome Maze.", firstOrb);
            talkToKingBolrenFirstOrb.addDialogStep("I will find the warlord and bring back the orbs.");
            elkoySkip = new NPCStep( NpcID.ELKOY_4968, new RSTile(2505, 3191, 0),
                    "Talk to Elkoy outside the maze to travel to the centre.");
            returnFirstOrb = new ConditionalStep( elkoySkip,
                    "Speak to King Bolren in the centre of the Tree Gnome Maze.");
            returnFirstOrb.addStep(insideGnomeVillage, talkToKingBolrenFirstOrb);
            returnFirstOrb.addSubSteps(talkToKingBolrenFirstOrb, elkoySkip);

            returnOrbs = new NPCStep( NpcID.KING_BOLREN, new RSTile(2541, 3170, 0),
                    "Talk to King Bolren in the centre of the Tree Gnome Maze.", orbsOfProtection);

            finishQuestDialog = new NPCStep( NpcID.KING_BOLREN, new RSTile(2541, 3170, 0),
                    "Speak to King Bolren in the centre of the Tree Gnome Maze.");
            returnOrbs.addSubSteps(finishQuestDialog);
        }
    */
    public int orb1 = 587;
    public int orb2 = 588;
    public int logs = 1511;
    public int lobster = 379;
    public int mindRune = 558;
    public int fireRune = 554;
    public int staffOfAir = 1381;

    public String code = null;

    public RSTile startTile = new RSTile(2539, 3167, 0);
    public RSTile commanderTile = new RSTile(2526, 3211, 0);
    public RSTile tracker1Tile = new RSTile(2497, 3255, 0);
    public RSTile tracker2Tile = new RSTile(2497, 3255, 0);
    public RSTile tracker3Tile = new RSTile(2497, 3255, 0);
    public RSTile orb1Tile = new RSTile(2506, 3258, 0);
    public RSTile SAFE_TILE = new RSTile(2444, 3300, 0);
    public RSTile warlordTile = new RSTile(2458, 3304, 0);

    public RSArea startArea = new RSArea(new RSTile(2534, 3172, 0), new RSTile(2544, 3167, 0));
    public RSArea TRACKER_3_AREA = new RSArea(new RSTile(2497, 3234, 0), 5);
    public RSArea area = new RSArea(new RSTile(2499, 3259, 0), new RSTile(2491, 3251, 0));
    public RSArea TRACKER_1_AREA = new RSArea(new RSTile(2505, 3260, 0), new RSTile(2496, 3263, 0));
    public RSArea TRACKER_2_AREA = new RSArea(new RSTile(2522, 3256, 0), new RSTile(2526, 3255, 0));
    public RSArea catapultArea = new RSArea(new RSTile(2508, 3212, 0), new RSTile(2515, 3206, 0));
    public RSArea BOTTOM_OF_ORB_1_AREA = new RSArea(new RSTile(2500, 3251, 0), new RSTile(2506, 3256, 0));
    public RSArea orb1Upper = new RSArea(new RSTile(2500, 3259, 1), new RSTile(2506, 3251, 1));
    public RSArea orb1Outside = new RSArea(new RSTile(2507, 3253, 0), new RSTile(2511, 3252, 0));
    public RSArea START_AREA = new RSArea(new RSTile(2542, 3173, 0), new RSTile(2537, 3165, 0));
    public RSArea RAILING_AREA = new RSArea(new RSTile(2516, 3158, 0), new RSTile(2514, 3160, 0));
    public RSArea AFTER_CRUMBLED_WALL = new RSArea(new RSTile[]{new RSTile(2500, 3257, 0), new RSTile(2505, 3257, 0), new RSTile(2506, 3256, 0), new RSTile(2507, 3256, 0), new RSTile(2507, 3254, 0), new RSTile(2513, 3254, 0), new RSTile(2513, 3260, 0), new RSTile(2500, 3260, 0)});

    public RSNPC[] khazardWarlord = NPCs.find("Khazard warlord");

    public RSGroundItem[] orb2Ground = GroundItems.find(orb2);

    public void buyItems() {
        cQuesterV2.status = "Buying Items";;
        BankManager.open(true);
        BankManager.areItemsLoaded();
        BankManager.getAllList();
        BankManager.withdraw(0, true, 995);
        BankManager.close(true);
        /*GEManager.buyItem(logs, 300, 6);
        GEManager.buyItem(ItemID.MIND_RUNE, 200, 300);
        GEManager.buyItem(ItemID.FIRE_RUNE, 300, 900);
        GEManager.buyItem(ItemID.STAFF_OF_AIR, 100, 1);
        GEManager.buyItem(ItemID.LOBSTER, 50, 25);
        GEManager.buyItem(ItemID.CAMELOT_TELEPORT, 70, 5);
        GEManager.buyItem(
                Utils.STAMINA_POTION[0], 15, 3);
        GEManager.buyItem(
                Utils.RING_OF_DUELING[0], 35, 3);
        GEManager.buyItem(
                Utils.BLUE_WIZ_HAT, 500, 1);
        GEManager.buyItem(
                ItemID.MONKS_ROBE_TOP, 300, 1);
        GEManager.buyItem(ItemID.MONKS_ROBE_BOTTOM, 600, 1);
        GEManager.collectItems();
        GEManager.closeGE();*/
    }

    public void getItems() {
        cQuesterV2.status = "Getting Items";
        General.println("[Debug]: Getting Items");
        BankManager.open(true);
        BankManager.checkEquippedGlory();
        BankManager.depositAll(true);
        BankManager.withdraw(1, true, ItemID.BLUE_WIZARD_HAT);
        BankManager.withdraw(1, true, ItemID.MONKS_ROBE_TOP);
        BankManager.withdraw(1, true, ItemID.MONKS_ROBE);
        Utils.equipItem(ItemID.BLUE_WIZARD_HAT);
        Utils.equipItem(ItemID.MONKS_ROBE_TOP);
        Utils.equipItem(ItemID.MONKS_ROBE);
        BankManager.withdraw(300, true, ItemID.MIND_RUNE);
        BankManager.withdraw(900, true, ItemID.FIRE_RUNE);
        BankManager.withdraw(1, true, ItemID.STAFF_OF_AIR);
        BankManager.withdraw(6, true, logs);
        BankManager.withdraw(12, true, ItemID.LOBSTER);
        BankManager.withdraw(2, true, ItemID.CAMELOT_TELEPORT);
        BankManager.withdraw(2, true,
                ItemID.STAMINA_POTION[0]);
        BankManager.withdraw(1, true,
                ItemID.RING_OF_DUELING[0]);
        BankManager.close(true);
        Utils.equipItem(ItemID.STAFF_OF_AIR);
    }

    public void commanderMontai() {
        if (!BankManager.checkInventoryItems(logs, lobster, mindRune, fireRune)) {
            buyItems();
            getItems();
        } else if (Inventory.find(logs).length > 5) {
            cQuesterV2.status = "Going to Commander";
            PathingUtil.walkToTile(commanderTile);
            if (NpcChat.talkToNPC("Commander Montai")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Ok, I'll gather some wood.");
                NPCInteraction.handleConversation();
                General.sleep(General.randomSD(250, 2000, 700, 300));
            }
            if (NpcChat.talkToNPC("Commander Montai")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                General.sleep(General.randomSD(250, 2000, 700, 300));
            }
        } else {
            buyItems();
            getItems();
        }
    }

    public void commanderMontai2() {
        cQuesterV2.status = "Going to Commander";
        PathingUtil.walkToTile(commanderTile);
        if (NpcChat.talkToNPC("Commander Montai")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("I'll try my best.");
            NPCInteraction.handleConversation();
            Utils.shortSleep();
        }
    }

    public void startQuest() {
        cQuesterV2.status = "Starting Quest";
        if (!START_AREA.contains(Player.getPosition())) {
            PathingUtil.walkToTile(new RSTile(2515, 3160, 0));
            Timer.abc2WaitCondition(() -> RAILING_AREA.contains(Player.getPosition()), 12000, 15000);
            if (RAILING_AREA.contains(Player.getPosition())) {
                RSObject[] railing = org.tribot.api2007.Objects.findNearest(20, "Loose Railing");
                if (railing.length > 0) {
                    if (AccurateMouse.click(railing[0], "Squeeze-through"))
                        Utils.idle(3000, 6000);
                }
            }
        }
        PathingUtil.walkToArea(START_AREA);
        if (NpcChat.talkToNPC("King Bolren")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Can I help at all?");
            NPCInteraction.handleConversation("I would be glad to help.");
            NPCInteraction.handleConversation();
            Utils.modSleep();
        }
    }

    public void talkToTracker1() {
        cQuesterV2.status = "Tracker 1";
        PathingUtil.walkToArea(TRACKER_1_AREA);
        if (NpcChat.talkToNPC("Tracker gnome 1")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            General.sleep(General.randomSD(250, 2000, 700, 300));
        }
    }

    public void talkToTracker2() {
        cQuesterV2.status = "Tracker 2";
        PathingUtil.walkToArea(TRACKER_2_AREA);
        if (NpcChat.talkToNPC("Tracker gnome 2")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            General.sleep(General.randomSD(250, 2000, 700, 300));
        }
    }

    public void talkToTracker3() {
        cQuesterV2.status = "Going to Tracker 3";
        PathingUtil.walkToArea(TRACKER_3_AREA);
        if (NpcChat.talkToNPC(4977)) {
            NPCInteraction.waitForConversationWindow();
            Timer.waitCondition(() -> (Interfaces.get(217) != null || Interfaces.get(231) != null
                    || Interfaces.get(229) != null), 3000, 5000);
            while (Interfaces.get(217) != null || Interfaces.get(231) != null || Interfaces.get(229) != null) {
                General.sleep(20, 75);
                if (InterfaceUtil.clickInterfaceText(217, "continue")) {
                    General.sleep(General.random(300, 600));
                }

                if (Interfaces.isInterfaceSubstantiated(231, 5)) {
                    if (Interfaces.get(231, 5).getText().contains("My legs")) {
                        General.println("[Debug]: Answer is 4");
                        code = "0004";
                        break;
                    }
                }
                if (Interfaces.isInterfaceSubstantiated(231, 5)) {
                    if (Interfaces.get(231, 5).getText().contains("Less than my hands")) {
                        General.println("[Debug]: Answer is 1");
                        code = "0001";
                        break;
                    }
                }
                if (Interfaces.isInterfaceSubstantiated(231, 5)) {
                    if (Interfaces.get(231, 5).getText().contains("More than my head, less than my fingers")) {
                        General.println("[Debug]: Answer is 2");
                        code = "0002";
                        break;
                    }
                }
                if (Interfaces.isInterfaceSubstantiated(231, 5)) {
                    if (Interfaces.get(231, 5).getText().contains("More than we, less than our feet")) {
                        General.println("[Debug]: Answer is 3");
                        code = "0003";
                        break;
                    }
                }
                if (Interfaces.isInterfaceSubstantiated(231, 3)) {
                    NPCChat.clickContinue(true);
                    General.println("[Debug]: Clicking continue");
                    General.sleep(General.random(150, 600));
                }
                if (Interfaces.isInterfaceSubstantiated(229, 2)) {
                    NPCChat.clickContinue(true);
                    General.println("[Debug]: Clicking continue");
                    General.sleep(General.random(150, 600));
                }
            }

        }
    }

    public void catapult() {
        if (code != null) {
            cQuesterV2.status = "Firing Catapult";
            PathingUtil.walkToArea(catapultArea);
            if (Utils.clickObj("Ballista", "Fire")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation(code);
                NPCInteraction.handleConversation();

                Utils.idle(1500, 2500);
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                General.sleep(General.randomSD(250, 1000, 600, 200));
            }
        }
    }

    public void getOrb1() {
        cQuesterV2.status = "Getting Orb 1";
        PathingUtil.walkToArea(orb1Outside);

        if (orb1Outside.contains(Player.getPosition())) {
            if (Utils.clickObj(2185, "Climb-over")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                Timer.waitCondition(() -> AFTER_CRUMBLED_WALL.contains(Player.getPosition()), 10000, 12000);
                General.sleep(General.random(5000, 6000));
            }
        }
        eatOrb1();
        if (AFTER_CRUMBLED_WALL.contains(Player.getPosition())) {
            if (org.tribot.api2007.Objects.findNearest(20, 1535).length > 0) {
                Doors.handleDoor(org.tribot.api2007.Objects.findNearest(20, 1535)[0], true);
                General.sleep(General.random(3000, 4000));
            }
            Walking.blindWalkTo(BOTTOM_OF_ORB_1_AREA.getRandomTile());
            Timer.waitCondition(() -> BOTTOM_OF_ORB_1_AREA.contains(Player.getPosition()), 10000, 13000);
        }
        eatOrb1();
        if (BOTTOM_OF_ORB_1_AREA.contains(Player.getPosition())) {
            Utils.clickObj(16683, "Climb-up");
            if (Timer.waitCondition(() -> orb1Upper.contains(Player.getPosition()), 6000, 8000))
                General.sleep(General.random(200, 600));
        }
        eatOrb1();
        if (orb1Upper.contains(Player.getPosition())) {
            if (Utils.clickObj(2183, "Open"))
                Timer.waitCondition(() -> org.tribot.api2007.Objects.findNearest(20, 2812).length > 0, 5000, 7000);

            if (Utils.clickObj(2182, "Search"))
                if (Timer.waitCondition(() -> Inventory.find(orb1).length > 0, 7000, 9000))
                    General.sleep(General.random(200, 600));
        }
        if (Inventory.find(orb1).length > 0) {

            eatOrb1();

            if (Utils.clickObj(16679, "Climb-down"))
                Timer.waitCondition(() -> BOTTOM_OF_ORB_1_AREA.contains(Player.getPosition()), 8000, 10000);

            if (BOTTOM_OF_ORB_1_AREA.contains(Player.getPosition())) {
                eatOrb1();

                if (Utils.clickObj(1535, "Open"))
                    Utils.idle(3000, 5000);

                if (Utils.clickObj(2184, "Open"))
                    Utils.idle(3000, 5000);
            }
        }
    }

    public void eatOrb1() {
        EatUtil.eatFood();
    }

    public void talkKing2() {
        cQuesterV2.status = "Tree Gnome: Returning to king.";
        PathingUtil.walkToArea(startArea);
        if (NpcChat.talkToNPC("King Bolren")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("I will find the warlord and bring back the orbs.");
            NPCInteraction.handleConversation();
            NPCInteraction.handleConversation();
        }
    }

    public RSArea WHOLE_WARLORD_AREA = new RSArea(new RSTile(2443, 3303, 0), new RSTile(2458, 3297, 0));
    public RSArea SAFE_AREA_WARLORD = new RSArea(new RSTile(2444, 3301, 0), new RSTile(2443, 3300, 0));

    public void killWarlord() {
      /*  if (!BankManager.checkInventoryItems(Utils.FIRE_RUNE, Utils.MIND_RUNE)) {
            buyItems();
            getItems();
        }*/
        if (!WHOLE_WARLORD_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Going to Warlord.";
            PathingUtil.walkToArea(WHOLE_WARLORD_AREA);
        }
        khazardWarlord = NPCs.find("Khazard warlord");

        if (Equipment.find(staffOfAir).length < 1)
            Utils.equipItem(staffOfAir);

        if (!Autocast.isAutocastEnabled(Autocast.FIRE_STRIKE)) {
            Utils.equipItem(ItemID.STAFF_OF_AIR);
            Autocast.enableAutocast(Autocast.FIRE_STRIKE);
        }
        if (Autocast.isAutocastEnabled(Autocast.FIRE_STRIKE)) {

            if (khazardWarlord.length > 0 && !Combat.isUnderAttack() && !SAFE_AREA_WARLORD.contains(Player.getPosition())) {
                cQuesterV2.status = "Talking to Warlord.";
                if (!khazardWarlord[0].isClickable())
                    khazardWarlord[0].adjustCameraTo();

                if (NpcChat.talkToNPC("Khazard warlord")) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                }
            }
            khazardWarlord = NPCs.find(7622);
            while (khazardWarlord.length > 0) {
                General.sleep(75);
                if (!SAFE_AREA_WARLORD.contains(Player.getPosition())) {
                    cQuesterV2.status = "Going to SafeTile.";
                    if (Walking.blindWalkTo(SAFE_TILE)) {
                        Timer.waitCondition(() -> SAFE_AREA_WARLORD.contains(Player.getPosition()), 6000, 8000);
                        Utils.shortSleep();
                    }
                }
                if (SAFE_AREA_WARLORD.contains(Player.getPosition()) && !khazardWarlord[0].isInCombat()) {
                    cQuesterV2.status = "Attacking.";
                    if (!khazardWarlord[0].isClickable()) {
                        PathingUtil.walkToTile(khazardWarlord[0].getPosition(), 4, false);
                        if (NpcChat.talkToNPC(7622))
                            NPCInteraction.handleConversation();
                    }

                    if (Utils.clickNPC(7622, "Attack"))
                        Utils.idle(800, 2400);

                }
                if (Combat.getHPRatio() < General.random(31, 61) && Inventory.find(379).length > 0) {
                    cQuesterV2.status = "Eating...";
                    EatUtil.eatFood();
                }

                if (GroundItems.find(orb2).length > 0) {
                    Utils.shortSleep();
                    break;
                }
            }
            orb2Ground = GroundItems.find(orb2);

            if (orb2Ground.length > 0) {
                cQuesterV2.status = "Looting Orb.";

                if (!orb2Ground[0].isClickable())
                    orb2Ground[0].adjustCameraTo();

                if (Utils.clickGroundItem(orb2)) {
                    Timer.waitCondition(() -> Inventory.find(orb2).length > 0, 10000, 15000);
                    Utils.shortSleep();
                }
            }
        }
    }

    public void finishQuest() {
        if (Inventory.find(orb2).length > 0) {
            cQuesterV2.status = "Finishing Quest.";
            PathingUtil.walkToArea(startArea);
            if (NpcChat.talkToNPC("King Bolren")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                General.sleep(General.random(3000, 5000));
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                NPCInteraction.handleConversation();
                NPCInteraction.handleConversation();
                General.sleep(General.random(3000, 5000));
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
        }
    }

    public void checkLevel() {
        if (Skills.getActualLevel(Skills.SKILLS.HITPOINTS) < 20 || Skills.getActualLevel(Skills.SKILLS.MAGIC) < 13) {
            General.println("[Debug]: Missing required level");

        }
    }

    int GAME_SETTING = 111;

    @Override
    public void execute() {
        checkLevel();


        General.println("[Debug]: Game setting 111 is " + Game.getSetting(GAME_SETTING));


        if (Game.getSetting(GAME_SETTING) == 0) {
            buyItems();
            getItems();
            startQuest();

        } else if (Game.getSetting(GAME_SETTING) == 1) {
            commanderMontai();

        } else if (Game.getSetting(111) == 2) {
            commanderMontai();

        } else if (Game.getSetting(111) == 3) {
            commanderMontai2();


        } else if (Game.getSetting(111) == 4) {
            talkToTracker3();
            talkToTracker1();
            talkToTracker2();
            catapult();


        } else if (Game.getSetting(111) == 5) {
            getOrb1();

        } else if (Game.getSetting(111) == 6) {
            talkKing2();

        } else if (Game.getSetting(111) == 7) {
            killWarlord();

        } else if (Game.getSetting(111) == 8) {
            finishQuest();
        }
        if (Game.getSetting(111) == 9) {
            Utils.closeQuestCompletionWindow();
            NPCInteraction.handleConversation();
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
        return "Tree gnome village";
    }

    @Override
    public boolean checkRequirements() {
        return Skills.SKILLS.MAGIC.getActualLevel() > 12 &&
                Skills.SKILLS.HITPOINTS.getActualLevel() >= 20;
    }
}
