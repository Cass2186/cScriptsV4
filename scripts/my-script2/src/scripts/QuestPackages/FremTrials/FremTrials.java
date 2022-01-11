package scripts.QuestPackages.FremTrials;


import dax.api_lib.DaxWalker;
import dax.walker.utils.AccurateMouse;
import dax.walker.utils.camera.DaxCamera;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.Clicking;
import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.*;
import org.tribot.api2007.ext.Doors;
import org.tribot.api2007.types.*;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.Widgets;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.XMarksTheSpot.XMarksTheSpot;
import scripts.QuestSteps.BuyItemsStep;
import scripts.QuestSteps.GroundItemStep;
import scripts.QuestSteps.NPCStep;
import scripts.QuestSteps.QuestTask;
import scripts.Tasks.Priority;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class FremTrials implements QuestTask {
    boolean REVELLER = false;
    boolean SEER = false;
    boolean MANI = false;
    boolean OLAF = false;
    boolean SIGLI = false;
    boolean SIGMUND = false;
    boolean SWENSEN = false;
    boolean THORVALD = false;


    /**
     * These are for Sigmunds Trial to check who we've talked to
     */
    public boolean SIGMUNDPART = false;
    public boolean SAILOR = false;
    public boolean OLAF_SIGMUND = false;
    public boolean YRSA = false;
    public boolean BRUNDT = false;
    public boolean SIGLI_SIGMUND = false;
    public boolean SLUIRIMEN = false;
    public boolean FISHERMAN = false;
    public boolean SWENSEN_SIGMUND = false;
    public boolean PEER = false;
    public boolean THORVALD_SIGMUND = false;
    public boolean MANNI_SIGMUND = false;
    public boolean THORA = false;
    public boolean ASKELADDEN = false;

    boolean hasItem1;
    boolean hasItem2;

    String seven;
    String eight;
    String nine;
    String ten;
    String eleven;
    String twelve;
    String thirteen;
    String fourteen;
    String fifteen;
    String sixteen;
    String seventeen;
    String eighteen;
    String nineteen;
    String ringOfRecoilMessage = "Your Ring of Recoil has shattered.";

    //Item IDs
    int ringOfRecoil = 2550;
    int staffOfAir = 1381;
    int lyre = 3689;
    int beer = 1917;
    int coins = 995;
    int rawShark = 383;
    int tinderbox = 590;
    int shark = 385;
    int prayer4 = 2434;
    int recoil = 2550;
    int camelotTab = 8010;
    int mindRune = 558;
    int airRune = 556;
    int staffOfFire = 1387;
    int combat6 = 11972;
    int beerTankard = 3803;
    int kegOfBeer = 3711;
    int lowAlcoholKeg = 3712;
    int cherryBomb = 3713;
    int litCherryBomb = 3714;
    int enchantedLyre = 3690;
    int cocktail = 3707;
    int championsToken = 3706;
    int warriorsContract = 3710;
    int weatherForecast = 3705;
    int seaFishingMap = 3704;
    int unusualFish = 3703;
    int customBowString = 3702;
    int trackingMap = 3701;
    int fiscalStatement = 3708;
    int sturdyBoots = 3700;
    public final int[] STAMINA_POTION = {12625, 12627, 12629, 12631};
    int lobster = 379;
    int promissaryNote = 3709;
    int huntersTalisman = 3697;
    int completedHuntersTalisman = 3696;
    int emptyBucket = 3727;
    int CLOSED_CUPBOARD = 4177;
    int OPEN_CUPBOARD = 4178;
    int CLOSED_CHEST = 4170;
    int OPEN_CHEST = 4171; //????
    int TAP = 4176;
    int RANGE = 4172;
    int CLOSED_TRAPDOOR = 4174;
    int OPEN_TRAPDOOR = 4173;
    int MURAL = 4179;
    int FROZEN_TABLE = 4169;
    int UNICORN_HEAD = 4181;
    int BULLS_HEAD = 4182;
    int BOOKCASE = 4171;
    int FULL_BUCKET = 3722;
    int TWO_FIFTHS_BUCKET = 3725;
    int FULL_JUG = 3729;
    int TWO_THIRDS_JUG = 3730;
    int FOUR_LITRE_BUCKET = 3723;
    int RED_GLOOP = 3746;
    int GLOOP_DISK = 3743;
    int VASE_LID = 3737;
    int SEALED_VASE = 3740;
    int FROZEN_KEY = 3741;
    int KEY = 3745;
    int VASE = 3734;
    int DRAIN = 4175;
    int oldRedDish = 9947;
    int woodenDisk = 3744;
    int EMPTY_JUG = 3732;
    int RED_HERRING = 3742;
    int HERRING = 347;
    int fourFithsBucket = 3723;
    int frozenKey = 3741;
    int VASE_OF_WATER = 3735;
    public int i = 0;


    //Items
    public RSItem[] invLyre = Inventory.find(lyre);
    RSItem[] invItem1;
    RSItem[] invItem2;
    RSItem[] invBeer = Inventory.find(beer);
    RSItem[] invRawShark = Inventory.find(rawShark);
    RSItem[] invTinderbox = Inventory.find(tinderbox);
    RSItem[] invCamelotTab = Inventory.find(camelotTab);
    RSItem[] invLowAlcoholKeg = Inventory.find(lowAlcoholKeg);
    RSItem[] invKegOfBeer = Inventory.find(kegOfBeer);
    RSItem[] invBeerTankard = Inventory.find(beerTankard);
    RSItem[] invCherryBomb = Inventory.find(cherryBomb);
    RSItem[] invEnchantedLyre = Inventory.find(enchantedLyre);
    RSItem[] invUnusualFish = Inventory.find(unusualFish);
    RSItem[] invBowString = Inventory.find(customBowString);
    RSItem[] invTrackingMap = Inventory.find(trackingMap);
    RSItem[] invFiscalStatement = Inventory.find(fiscalStatement);
    RSItem[] invSturdyBoots = Inventory.find(sturdyBoots);
    RSItem[] invWeatherForecast = Inventory.find(weatherForecast);
    RSItem[] invSeaFishingMap = Inventory.find(seaFishingMap);
    RSItem[] invPromissaryNote = Inventory.find(promissaryNote);
    RSItem[] invLegendaryCocktail = Inventory.find(3707);
    RSItem[] invChampionsToken = Inventory.find(3706);
    RSItem[] invWarriorsContraction = Inventory.find(3710);
    RSItem[] invFremennikBallad = Inventory.find(3699);
    RSItem[] invExoticFlower = Inventory.find(3698);
    RSItem[] invRingOfRecoil = Inventory.find(ringOfRecoil);
    RSItem[] invRedDish = Inventory.find(oldRedDish);
    RSItem[] invRedHerring = Inventory.find(RED_HERRING);
    RSItem[] invWoodenDisk = Inventory.find(woodenDisk);
    RSItem[] invEmptyBucket = Inventory.find(emptyBucket);
    RSItem[] invEmptyJug = Inventory.find(EMPTY_JUG);
    RSItem[] vase = Inventory.find(3734);

    RSItem[] invFullBucket = Inventory.find(3722);
    RSItem[] invtwofifthsBucket = Inventory.find(3725);
    RSItem[] invFullJug = Inventory.find(3729);
    RSItem[] twoThirdsJug = Inventory.find(3730);
    RSItem[] fourLitreBucket = Inventory.find(3723);
    RSItem[] invHerring = Inventory.find(HERRING);
    RSItem[] redGloop = Inventory.find(3746);
    RSItem[] gloopDisk = Inventory.find(3743);
    RSItem[] vaseLid = Inventory.find(3737);
    RSItem[] sealedVase = Inventory.find(3738);
    RSItem[] invFrozenKey = Inventory.find(3741);
    RSItem[] key = Inventory.find(3745);

    //NPCs
    public RSNPC[] lanzig = NPCs.find("Lanzig");
    public RSNPC[] manni = NPCs.find("Manni the Reveller");
    public RSNPC[] swensen = NPCs.find("Swensen the Navigator");
    public RSNPC[] peerTheSeer = NPCs.find("Peer the Seer");
    public RSNPC[] draugen = NPCs.find("The Draugen");


    //Tiles
    public RSTile lanzigSafeTle = new RSTile(2670, 3665);
    public RSTile inFrontOfBeer = new RSTile(2658, 3675);
    public RSTile bar = new RSTile(2694, 3492);
    public RSTile bridge = new RSTile(2653, 3591);
    public RSTile pipeTile = new RSTile(2663, 3674);
    public RSTile olafTile = new RSTile(2671, 3681);
    public RSTile stageDoor = new RSTile(2667, 3682);
    public RSTile strangeAltarTile = new RSTile(2626, 3599);
    public RSTile sailorTile = new RSTile(2629, 3693);
    public RSTile yrsaTile = new RSTile(2625, 3673);
    public RSTile brundtTile = new RSTile(2660, 3667);
    public RSTile sigliTile = new RSTile(2658, 3656);
    public RSTile skulgrimenTile = new RSTile(2664, 3693);
    public RSTile fishermanTile = new RSTile(2640, 3699);
    public RSTile swensenTile = new RSTile(2645, 3660);
    public RSTile seerTile = new RSTile(2632, 3668);
    public RSTile askeladdenTile = new RSTile(2657, 3661, 0);
    public RSTile draugenTile = new RSTile(2610, 3635, 0);
    public RSTile NETile = new RSTile(2613, 3650, 0);
    public RSTile NWTile = new RSTile(2604, 3641, 0);
    public RSTile NWTile2 = new RSTile(2604, 3651, 0);
    public RSTile koscheiTile = new RSTile(2646, 10085, 2);

    //Areas
    public RSArea lanzigSafeArea = new RSArea(lanzigSafeTle, 1);
    public RSArea kosheiArea = new RSArea(koscheiTile, 20);
    public RSArea bottomRoom = new RSArea(new RSTile(2629, 3666, 0), new RSTile(2633, 3659, 0));
    public RSArea room1 = new RSArea(new RSTile(2631, 10004, 0), 2);
    public RSArea peerBottomRightRoom = new RSArea(new RSTile(2629, 3666, 0), new RSTile(2633, 3659, 0));
    public RSArea OLAF_AREA = new RSArea(new RSTile(2667, 3685, 0), new RSTile(2676, 3674, 0));
    public RSArea PEER_UPSTAIRS = new RSArea(new RSTile(2629, 3665, 2), new RSTile(2638, 3660, 2));
    public RSArea sigmundArea = new RSArea(new RSTile(2642, 3679, 0), 4);
    public RSArea NEArea = new RSArea(NETile, 3);
    public RSArea NWArea = new RSArea(NWTile, 3);
    public RSArea NWArea2 = new RSArea(NWTile2, 3);
    public RSArea barArea = new RSArea(new RSTile(2689, 3497, 0), new RSTile(2700, 3488, 0));
    public RSArea SWArea = new RSArea(new RSTile(2601, 3621, 0), 5);
    public RSArea THORA_AREA = new RSArea(new RSTile(2662, 3667, 0), new RSTile(2655, 3678, 0));
    public RSArea START_AREA = new RSArea(new RSTile(2661, 3666, 0), new RSTile(2656, 3673, 0));
    public RSArea OUTSIDE_SEER_HOUSE_EXIT = new RSArea(new RSTile(2638, 3667, 0), new RSTile(2634, 3668, 0));
    public RSArea SEER_BOTTOM_LEFT_ROOM = new RSArea(new RSTile(2633, 3659, 0), new RSTile(2629, 3666, 0));

    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemId.CAMELOT_TELEPORT, 15, 35),
                    new GEItem(ItemId.SHARK, 25, 25),
                    new GEItem(ItemId.RING_OF_RECOIL, 4, 50),
                    new GEItem(ItemId.BEER, 1, 300),
                    new GEItem(ItemId.AIR_RUNE, 3000, 30),
                    new GEItem(ItemId.MIND_RUNE, 1500, 30),

                    new GEItem(ItemId.RAW_SHARK, 1, 50),
                    new GEItem(ItemId.LOBSTER, 25, 30),
                    new GEItem(ItemId.STAFF_OF_FIRE, 1, 150),
                    new GEItem(ItemId.BLUE_WIZ_HAT, 1, 300),
                    new GEItem(ItemId.BLUE_WIZ_TOP, 1, 300),

                    new GEItem(ItemId.AMULET_OF_GLORY[2], 1, 30),
                    new GEItem(ItemId.STAMINA_POTION[0], 5, 15),
                    new GEItem(ItemId.RING_OF_WEALTH[0], 1, 25)
            )
    );


    BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);

    public void buyItems() {
        cQuesterV2.status = "Buying Items";
        General.println("[Debug]: Buying Items");
        buyStep.buyItems();
    }

    NPCStep startWithBrundtStep = new NPCStep("Brundt the Chieftain", START_AREA,
            new String[]{"Ask about anything else.", "Do you have any quests?",
                    "Yes, I am interested.", "I want to become a Fremennik!"});

    public void startQuest() {
        if (!BankManager.checkInventoryItems(ItemId.CAMELOT_TELEPORT, ItemId.MIND_RUNE, ItemId.AIR_RUNE, tinderbox)) {
            buyItems();
            getItemsPart1();
        }
        cQuesterV2.status = "Starting quest";
        startWithBrundtStep.execute();
    }

    /**
     * THORVALD TRIAL
     */
    public void startThorvald() {
        cQuesterV2.status = "Starting Thorvald";
        gotoSkulrimen();
        if (NpcChat.talkToNPC("Thorvald the Warrior")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Yes");
            NPCInteraction.handleConversation();
        }
    }

    public void getThorvaldItems() {
        invRingOfRecoil = Inventory.find(ringOfRecoil);
        if (invRingOfRecoil.length < 1) {
            cQuesterV2.status = "Getting Items for  Fight";
            General.println("[Debug]: " + cQuesterV2.status);
            if (OUTSIDE_SEER_HOUSE_EXIT.contains(Player.getPosition())) {
                if (GameTab.getOpen() != GameTab.TABS.MAGIC)
                    GameTab.open(GameTab.TABS.MAGIC);

                if (Interfaces.isInterfaceSubstantiated(218, 5)) {
                    if (Interfaces.get(218, 5).click())
                        Timer.waitCondition(() -> Player.getAnimation() != -1, 5000, 7000);
                    if (Player.getAnimation() != -1)
                        Timer.waitCondition(() -> Player.getAnimation() == -1, 25000, 30000);
                }
            }

            if (BankManager.open(true)) {
                BankManager.depositAll(true);
                BankManager.depositEquipment();
                BankManager.checkEquippedGlory();
                BankManager.withdraw(3, true, ringOfRecoil);
                Utils.equipItem(ringOfRecoil);
                BankManager.withdraw(24, true, shark);
                BankManager.withdraw(5, true, camelotTab);
                BankManager.withdraw(1, true, ItemId.STAMINA_POTION[0]);
                General.sleep(General.random(100, 800));
                BankManager.close(true);
            }
            //   if (Inventory.find(ItemId.CAMELOT_TELEPORT).length > 0) {
            //    Clicking.click("Break", Inventory.find(camelotTab));
            //   General.sleep(General.random(3000, 6000));
            //  }
        }
    }

    public void startThorvaldFight() {
        if (!Combat.isAutoRetaliateOn()) {
            Combat.setAutoRetaliate(true);
        }
        cQuesterV2.status = "Starting Fight";
        RSObject[] ladder = Objects.findNearest(20, "Ladder");
        if (ladder.length > 0) {
            ladder[0].click("Climb-down");
            walkIdle();
            General.sleep(General.random(1500, 2500));
        }
        if (PathingUtil.localNavigation(koscheiTile))
            PathingUtil.movementIdle();
        while (kosheiArea.contains(Player.getPosition())) {
            Waiting.waitNormal(50, 25);
            RSNPC[] npc = NPCs.find("Koschei the deathless");
            if (npc.length > 0 && !npc[0].isInCombat()) {
                CombatUtil.clickTarget(npc[0]);
            }
            combatIdleKoshei();
        }
        General.sleep(General.random(3000, 5000));
        NPCInteraction.handleConversation();
        ladder = Objects.findNearest(20, "Ladder");
        if (ladder.length > 0) {
            ladder[0].click("Climb-down");
            General.sleep(General.random(2500, 5000));
        }
    }

    public void combatIdleKoshei() {
        cQuesterV2.status = "Fighting...";
        General.println("[Debug]: Fight idle.");
        while (Combat.isUnderAttack()) {
            General.sleep(General.random(300, 900));
            if (Combat.getHPRatio() < General.random(35, 50)) {
                invItem1 = Inventory.find(shark);

                if (invItem1.length > 0) {
                    General.println("[Debug]: Eating Shark.");
                    Clicking.click("Eat", invItem1);

                }

            }
        }

        NPCInteraction.handleConversation();
    }

    /**
     * SWENSEN TRIAL
     */

    RSArea SWENSEN_ROOM1 = new RSArea(new RSTile(2634, 10002, 0), new RSTile(2628, 10008, 0));
    RSArea SWENSEN_ROOM2 = new RSArea(new RSTile(2646, 10011, 0), new RSTile(2638, 10018, 0));
    RSArea SWENSEN_ROOM3 = new RSArea(new RSTile(2657, 10001, 0), new RSTile(2650, 10007, 0));
    RSArea SWENSEN_ROOM4 = new RSArea(new RSTile(2668, 10012, 0), new RSTile(2662, 10018, 0));
    RSArea SWENSEN_ROOM5 = new RSArea(new RSTile(2634, 10023, 0), new RSTile(2626, 10029, 0));
    RSArea SWENSEN_ROOM6 = new RSArea(new RSTile(2657, 10034, 0), new RSTile(2649, 10040, 0));
    RSArea SWENSEN_ROOM7 = new RSArea(new RSTile(2671, 10022, 0), new RSTile(2661, 10030, 0));
    RSArea SWENSEN_ROOM8 = new RSArea(new RSTile(2670, 10034, 0), new RSTile(2661, 10042, 0));

    RSArea swensenHouse = new RSArea(new RSTile(2647, 3659, 0), 4);
    RSArea WHOLE_SWENSEN_UNDERGROUND = new RSArea(new RSTile(2626, 10043, 0), new RSTile(2672, 9999, 0));


    public void startSwensen() {
        if (!WHOLE_SWENSEN_UNDERGROUND.contains(Player.getPosition())) {
            cQuesterV2.status = "Starting Swensen Trial";
            gotoSwensen();
        }
        if (swensenHouse.contains(Player.getPosition())) {
            swensen = NPCs.find("Swensen the Navigator");
            if (NpcChat.talkToNPC("Swensen the Navigator")) {
                NPCInteraction.handleConversation("Yes");
                NPCInteraction.handleConversation("Yes");
            }
            if (Utils.clickObj("Ladder", "Climb-down")) {
                Timer.waitCondition(() -> SWENSEN_ROOM1.contains(Player.getPosition()), 6000, 8000);
                Utils.shortSleep();
            }
        }
    }

    public void swensenRoom1() {
        if (SWENSEN_ROOM1.contains(Player.getPosition())) {
            Options.setRunEnabled(true);
            if (room1.contains(Player.getPosition())) {
                cQuesterV2.status = "Navigating Room 1";
                swensenRoom(2631, 10003, 4150);
                Timer.waitCondition(() -> SWENSEN_ROOM2.contains(Player.getPosition()), 6000, 8000);
                Utils.shortSleep();
            }
        }
    }

    public void swensenRoom2() {
        if (SWENSEN_ROOM2.contains(Player.getPosition())) {
            cQuesterV2.status = "Navigating Room 2";
            swensenRoom(2640, 10015, 4151);
            Timer.waitCondition(() -> SWENSEN_ROOM3.contains(Player.getPosition()), 6000, 8000);
            Utils.shortSleep();
        }
    }

    public void swensenRoom3() {
        if (SWENSEN_ROOM3.contains(Player.getPosition())) {
            cQuesterV2.status = "Navigating Room 3";
            swensenRoom(2655, 10004, 4152);
            Timer.waitCondition(() -> SWENSEN_ROOM4.contains(Player.getPosition()), 6000, 8000);
            Utils.shortSleep();
        }
    }

    public void swensenRoom4() {
        if (SWENSEN_ROOM4.contains(Player.getPosition())) {
            cQuesterV2.status = "Navigating Room 4";
            swensenRoom(2665, 10017, 4153);
            Timer.waitCondition(() -> SWENSEN_ROOM5.contains(Player.getPosition()), 6000, 8000);
            Utils.shortSleep();
        }
    }

    public void swensenRoom5() {
        if (SWENSEN_ROOM5.contains(Player.getPosition())) {
            cQuesterV2.status = "Navigating Room 5";
            swensenRoom(2630, 10024, 4154);
            Timer.waitCondition(() -> SWENSEN_ROOM6.contains(Player.getPosition()), 6000, 8000);
            Utils.shortSleep();
        }
    }

    public void swensenRoom6() {
        if (SWENSEN_ROOM6.contains(Player.getPosition())) {
            cQuesterV2.status = "Navigating Room 6";
            swensenRoom(2655, 10037, 4155);
            Timer.waitCondition(() -> SWENSEN_ROOM7.contains(Player.getPosition()), 6000, 8000);
            Utils.shortSleep();
        }
    }

    public void swensenRoom7() {
        if (SWENSEN_ROOM7.contains(Player.getPosition())) {
            cQuesterV2.status = "Navigating Room 6";
            swensenRoom(2655, 10037, 4156);
            Timer.waitCondition(() -> SWENSEN_ROOM8.contains(Player.getPosition()), 6000, 8000);
            Utils.shortSleep();
        }
        if (SWENSEN_ROOM8.contains(Player.getPosition())) {
            cQuesterV2.status = "Navigating Room 7";
            if (Utils.clickObj("Ladder", "Climb-up")) {
                Timer.waitCondition(() -> NPCs.find("Swensen the Navigator").length > 0, 6000, 8000);
                Utils.shortSleep();
            }
            if (NpcChat.talkToNPC("Swensen the Navigator")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Yes");
                NPCInteraction.handleConversation();
                SWENSEN = true;
            }
        }
    }


    public void swensenRoom(int x, int y, int portalID) {
        RSObject[] portal = Objects.findNearest(8, portalID);
        if (portal.length > 0) {
            if (!portal[0].isClickable())
                portal[0].adjustCameraTo();

            if (PathingUtil.clickScreenWalk(new RSTile(x, y)))
                Utils.shortSleep();
        }

        if (Utils.clickObj(portalID, "Use")) {
            Timer.waitCondition(() -> Objects.findNearest(6, portalID).length < 1, 7000, 9000);
            Utils.shortSleep();
        }
    }


    /**
     * PEER THE SEER TRIAL
     */
    public void startSeerTrial() {
        cQuesterV2.status = "Staring Seer Trial";
        gotoSeer();
        if (NpcChat.talkToNPC("Peer the Seer")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Yes");
            NPCInteraction.handleConversation("Yes");
            NPCInteraction.handleConversation();
            Utils.modSleep();
        }
    }

    public void seerDoor() {
        cQuesterV2.status = "Seer Trial";
        gotoSeer();
        if (!Interfaces.isInterfaceSubstantiated(Interfaces.get(298, 43))) {
            if (Utils.clickObj(4165, "Open")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                NPCChat.selectOption("Read the riddle", true);
            }
        }
    }

    int SEER_DOOR_ENTRANCE = 4165;

    public void solveDoor() {
        cQuesterV2.status = "Solving Door";
        General.sleep(500);
        if (Interfaces.get(229, 1) != null) {
            String answer;
            String seerText = Interfaces.get(229, 1).getText();
            if (seerText.contains("My first is in fish, but not in the sea.")) {
                answer = "FIRE";
                General.println("[Debug]: Answer is: " + answer);
                NPCInteraction.handleConversation();
                Timer.waitCondition(() -> Interfaces.isInterfaceSubstantiated(298, 47), 3000, 5000);
                General.sleep(General.random(300, 1000));
                if (Interfaces.isInterfaceSubstantiated(298, 47)) {
                    while (!Interfaces.get(298, 43).getText().contains("F")) {
                        String s = Interfaces.get(298, 43).getText();
                        General.sleep(General.randomSD(50, 600, 200, 75));
                        if (Interfaces.get(298, 48).click())
                            Timer.waitCondition(() -> !Interfaces.get(298, 43).getText().contains(s), 4000, 7000);
                    }
                    while (!Interfaces.get(298, 44).getText().contains("I")) {
                        General.sleep(General.random(50, 300));
                        Interfaces.get(298, 50).click();
                        General.sleep(General.random(500, 800));
                    }
                    while (!Interfaces.get(298, 45).getText().contains("R")) {
                        General.sleep(General.random(150, 300));
                        Interfaces.get(298, 52).click();
                        General.sleep(General.random(500, 800));
                    }
                    while (!Interfaces.get(298, 46).getText().contains("E")) {
                        General.sleep(General.random(50, 300));
                        Interfaces.get(298, 54).click();
                        General.sleep(General.random(500, 800));
                    }
                    if (Interfaces.isInterfaceSubstantiated(298, 56)) {
                        Interfaces.get(298, 56).click(); // confirm button
                        Timing.waitCondition(() -> !Interfaces.isInterfaceSubstantiated(298, 56), 3500);
                    }
                }
                Utils.clickObj(SEER_DOOR_ENTRANCE, "Open"); //4165 is the door ID that you need to open to enter Seer's building
                Utils.modSleep();
            }
            if (seerText.contains("My first is in the well, but not at sea.")) {
                answer = "LIFE";
                General.println("[Debug]: Answer is: " + answer);
                NPCInteraction.handleConversation();
                Timing.waitCondition(() -> Interfaces.isInterfaceSubstantiated(298, 47), 3000);
                General.sleep(General.random(300, 1000));
                if (Interfaces.get(298, 47) != null) {
                    while (!Interfaces.get(298, 43).getText().contains("L")) {
                        General.sleep(General.random(50, 300));
                        Interfaces.get(298, 48).click();
                        General.sleep(General.random(500, 800));
                    }
                    while (!Interfaces.get(298, 44).getText().contains("I")) {
                        General.sleep(General.random(50, 300));
                        Interfaces.get(298, 50).click();
                        General.sleep(General.random(500, 800));
                    }
                    while (!Interfaces.get(298, 45).getText().contains("F")) {
                        General.sleep(General.random(150, 300));
                        Interfaces.get(298, 52).click();
                        General.sleep(General.random(500, 800));
                    }
                    while (!Interfaces.get(298, 46).getText().contains("E")) {
                        General.sleep(General.random(50, 300));
                        Interfaces.get(298, 54).click();
                        General.sleep(General.random(500, 800));
                    }
                    if (Interfaces.isInterfaceSubstantiated(298, 56)) {
                        Interfaces.get(298, 56).click(); // confirm button
                        Timing.waitCondition(() -> !Interfaces.isInterfaceSubstantiated(298, 56), 3500);
                    }
                }
                Utils.clickObj(SEER_DOOR_ENTRANCE, "Open"); //4165 is the door ID that you need to open to enter Seer's building
                Utils.modSleep();
            }
            if (seerText.contains("My first is in mage, but not in wizard.")) {
                answer = "MIND";
                General.println("[Debug]: Answer is: " + answer);
                NPCInteraction.handleConversation();
                Timing.waitCondition(() -> Interfaces.isInterfaceSubstantiated(298, 47), 3000);
                General.sleep(General.random(300, 1000));
                if (Interfaces.get(298, 47) != null) {
                    while (!Interfaces.get(298, 43).getText().contains("M")) {
                        General.sleep(General.random(50, 300));
                        Interfaces.get(298, 48).click();
                        General.sleep(General.random(500, 800));
                    }
                    while (!Interfaces.get(298, 44).getText().contains("I")) {
                        General.sleep(General.random(50, 300));
                        Interfaces.get(298, 50).click();
                        General.sleep(General.random(500, 800));
                    }
                    while (!Interfaces.get(298, 45).getText().contains("N")) {
                        General.sleep(General.random(150, 300));
                        if (Interfaces.get(298, 52).click())
                            General.sleep(General.random(500, 800));
                    }
                    while (!Interfaces.get(298, 46).getText().contains("D")) {
                        General.sleep(General.random(50, 300));
                        if (Interfaces.get(298, 54).click())
                            General.sleep(General.random(500, 800));
                    }
                    if (Interfaces.isInterfaceSubstantiated(298, 56)) {
                        if (Interfaces.get(298, 56).click()) // confirm button
                            Timing.waitCondition(() -> !Interfaces.isInterfaceSubstantiated(298, 56), 3500);
                    }
                }
                Utils.clickObj(SEER_DOOR_ENTRANCE, "Open"); //4165 is the door ID that you need to open to enter Seer's building
                Utils.modSleep();
            }
            if (seerText.contains("My first is in tar, but not in a swamp.")) {
                answer = "TREE";
                General.println("[Debug]: Answer is: " + answer);
                NPCInteraction.handleConversation();
                Timing.waitCondition(() -> Interfaces.isInterfaceSubstantiated(298, 47), 3000);
                General.sleep(General.random(300, 1000));
                if (Interfaces.get(298, 47) != null) {
                    while (!Interfaces.get(298, 43).getText().contains("T")) {
                        General.sleep(General.random(50, 300));
                        Interfaces.get(298, 48).click();
                        General.sleep(General.random(500, 800));
                    }
                    while (!Interfaces.get(298, 44).getText().contains("R")) {
                        General.sleep(General.random(50, 300));
                        Interfaces.get(298, 50).click();
                        General.sleep(General.random(500, 800));
                    }
                    while (!Interfaces.get(298, 45).getText().contains("E")) {
                        General.sleep(General.random(150, 300));
                        Interfaces.get(298, 52).click();
                        General.sleep(General.random(500, 800));
                    }
                    while (!Interfaces.get(298, 46).getText().contains("E")) {
                        General.sleep(General.random(50, 300));
                        Interfaces.get(298, 54).click();
                        General.sleep(General.random(500, 800));
                    }
                    if (Interfaces.isInterfaceSubstantiated(298, 56)) {
                        Interfaces.get(298, 56).click(); // confirm button
                        Timing.waitCondition(() -> !Interfaces.isInterfaceSubstantiated(298, 56), 3500);
                    }
                }
                Utils.clickObj(SEER_DOOR_ENTRANCE, "Open"); //4165 is the door ID that you need to open to enter Seer's building
                General.sleep(General.random(2000, 4000));
            }
            if (seerText.contains("My first is in wizard, but not in a mage.")) {
                answer = "WIND";
                General.println("[Debug]: Answer is: " + answer);
                NPCInteraction.handleConversation();
                Timing.waitCondition(() -> Interfaces.isInterfaceSubstantiated(298, 47), 3000);
                General.sleep(General.random(300, 1000));
                if (Interfaces.get(298, 47) != null) {
                    while (!Interfaces.get(298, 43).getText().contains("W")) {
                        General.sleep(General.random(50, 300));
                        Interfaces.get(298, 48).click();
                        General.sleep(General.random(500, 800));
                    }
                    while (!Interfaces.get(298, 44).getText().contains("I")) {
                        General.sleep(General.random(50, 300));
                        Interfaces.get(298, 50).click();
                        General.sleep(General.random(500, 800));
                    }
                    while (!Interfaces.get(298, 45).getText().contains("N")) {
                        General.sleep(General.random(150, 300));
                        Interfaces.get(298, 52).click();
                        General.sleep(General.random(500, 800));
                    }
                    while (!Interfaces.get(298, 46).getText().contains("D")) {
                        General.sleep(General.random(50, 300));
                        Interfaces.get(298, 54).click();
                        General.sleep(General.random(500, 800));
                    }
                    if (Interfaces.isInterfaceSubstantiated(298, 56)) {
                        Interfaces.get(298, 56).click(); // confirm button
                        Timing.waitCondition(() -> !Interfaces.isInterfaceSubstantiated(298, 56), 3500);
                    }
                }
                Utils.clickObj(SEER_DOOR_ENTRANCE, "Open"); //4165 is the door ID that you need to open to enter Seer's building
                General.sleep(General.random(2000, 4000));
            }
            if (seerText.contains("My first is in water, and also in tea.")) {
                answer = "TIME";
                General.println("[Debug]: Answer is: " + answer);
                NPCInteraction.handleConversation();
                Timer.waitCondition(() -> Interfaces.isInterfaceSubstantiated(298, 47), 3000, 5000);
                General.sleep(General.random(300, 1000));
                if (Interfaces.get(298, 47) != null) {
                    while (!Interfaces.get(298, 43).getText().contains("T")) {
                        General.sleep(General.random(50, 300));
                        Interfaces.get(298, 48).click();
                        General.sleep(General.random(500, 800));
                    }
                    while (!Interfaces.get(298, 44).getText().contains("I")) {
                        General.sleep(General.random(50, 300));
                        Interfaces.get(298, 50).click();
                        General.sleep(General.random(500, 800));
                    }
                    while (!Interfaces.get(298, 45).getText().contains("M")) {
                        General.sleep(General.random(150, 300));
                        Interfaces.get(298, 52).click();
                        General.sleep(General.random(500, 800));
                    }
                    while (!Interfaces.get(298, 46).getText().contains("E")) {
                        General.sleep(General.random(50, 300));
                        Interfaces.get(298, 54).click();
                        General.sleep(General.random(500, 800));
                    }
                    if (Interfaces.isInterfaceSubstantiated(298, 56)) {
                        Interfaces.get(298, 56).click(); // confirm button
                        Timer.waitCondition(() -> !Interfaces.isInterfaceSubstantiated(298, 56), 3500, 7000);
                    }
                }
                Utils.clickObj(SEER_DOOR_ENTRANCE, "Open"); //4165 is the door ID that you need to open to enter Seer's building
                General.sleep(General.random(2000, 4000));
            }
        }
    }

    public void seerUpStairsItems() {
        if (peerBottomRightRoom.contains(Player.getPosition())) {
            cQuesterV2.status = "Going upstairs";
            if (Utils.clickObj("Ladder", "Climb-up")) {
                Timer.waitCondition(() -> PEER_UPSTAIRS.contains(Player.getPosition()), 5000, 7000);
                Utils.modSleep();
            }
        }
        if (PEER_UPSTAIRS.contains(Player.getPosition())) {
            updateItems();
            if (fourLitreBucket.length > 0 || invHerring.length > 0 || vase.length > 0) {
                /**
                 * this is in case it loops and calls this method again we don't need it do continue if ...
                 *  this is what our inventory has.
                 */
                return;
            } else if (invEmptyJug.length < 1 || invEmptyBucket.length < 1 || invRedDish.length < 1 || invRedHerring.length < 1 || invWoodenDisk.length < 1) {
                if (invEmptyBucket.length < 1) {
                    cQuesterV2.status = "Getting empty bucket";
                    if (Utils.clickObj(CLOSED_CUPBOARD, "Open"))
                        Timer.waitCondition(() -> Objects.find(20, OPEN_CUPBOARD).length > 0, 5000, 7000);

                    if (Utils.clickObj(OPEN_CUPBOARD, "Search"))
                        Timer.waitCondition(() -> Inventory.find(emptyBucket).length > 0, 5000, 7000);
                    updateItems();
                }
                if (invRedDish.length < 1) {
                    cQuesterV2.status = "Getting red dish";
                    if (Utils.clickObj(UNICORN_HEAD, "Study")) {
                        NPCInteraction.waitForConversationWindow();
                        NPCInteraction.handleConversation();
                        Timer.waitCondition(() -> Inventory.find(oldRedDish).length > 0, 3000, 6000);
                        Utils.shortSleep();
                        updateItems();
                    }
                }
                if (invWoodenDisk.length < 1) {
                    cQuesterV2.status = "Getting wooden dish";
                    if (Utils.clickObj(BULLS_HEAD, "Study")) {
                        NPCInteraction.waitForConversationWindow();
                        NPCInteraction.handleConversation();
                        updateItems();
                    }
                }
                if (invEmptyJug.length < 1) {
                    cQuesterV2.status = "Getting empty jug ";
                    if (Utils.clickObj(4167, "Open")) //had a distance of 5
                        Timer.waitCondition(() -> Objects.findNearest(2, 4168).length > 0, 5000, 8000);

                    if (Utils.clickObj(4168, "Search")) {
                        Timer.waitCondition(() -> Inventory.find(EMPTY_JUG).length > 0, 5000, 8000);
                        Utils.modSleep();
                        updateItems();
                    }
                }
                if (invRedHerring.length < 1) {
                    cQuesterV2.status = "Getting red herring";
                    if (Utils.clickObj(BOOKCASE, "Search")) {
                        Timer.waitCondition(() -> Inventory.find(RED_HERRING).length > 0, 7000, 9000);
                        Utils.shortSleep();
                        updateItems();
                    }
                }
            }
        }
    }

    public void updateItems() {
        invRedDish = Inventory.find(oldRedDish);
        invRedHerring = Inventory.find(RED_HERRING);
        invWoodenDisk = Inventory.find(woodenDisk);
        invEmptyBucket = Inventory.find(emptyBucket);
        invEmptyJug = Inventory.find(EMPTY_JUG);
        invFullBucket = Inventory.find(FULL_BUCKET);
        invtwofifthsBucket = Inventory.find(TWO_FIFTHS_BUCKET);
        invFullJug = Inventory.find(FULL_JUG);
        twoThirdsJug = Inventory.find(TWO_THIRDS_JUG);
        fourLitreBucket = Inventory.find(FOUR_LITRE_BUCKET);
        invHerring = Inventory.find(HERRING);
        redGloop = Inventory.find(RED_GLOOP);
        gloopDisk = Inventory.find(GLOOP_DISK);
        vaseLid = Inventory.find(VASE_LID);
        sealedVase = Inventory.find(SEALED_VASE);
        invFrozenKey = Inventory.find(FROZEN_KEY);
        key = Inventory.find(KEY);
        vase = Inventory.find(VASE);
    }

    RSArea SEER_BOTTOM_RIGHT_ROOM = new RSArea(new RSTile(2638, 3659, 0), new RSTile(2634, 3666, 0));

    public void doWaterPuzzle() {
        cQuesterV2.status = "Doing puzzle";
        updateItems();
        General.println("[Debug]: Doing Puzzle.");
        while (Inventory.find(KEY).length < 1) {
            updateItems();
            General.sleep(100);
            if (invFullBucket.length < 1 && invEmptyBucket.length > 0 && twoThirdsJug.length < 1 && sealedVase.length < 1) {
                cQuesterV2.status = "Filling empty bucket";
                if (Utils.useItemOnObject(emptyBucket, TAP)) {
                    Timer.waitCondition(() -> Inventory.find(FULL_BUCKET).length > 0, 8000, 10000);
                    updateItems();
                    Utils.shortSleep();
                }
            }
            if (key.length < 1 && invtwofifthsBucket.length < 1 && invEmptyJug.length > 0 && fourLitreBucket.length < 1 && vase.length < 1 && sealedVase.length < 1 && invHerring.length < 1) {
                cQuesterV2.status = "Filling jug";
                Utils.useItemOnItem(FULL_BUCKET, EMPTY_JUG);
                Utils.shortSleep();
                updateItems();

            }
            if (key.length < 1 && invEmptyJug.length < 1 && invFullJug.length > 0 && vase.length < 1 && fourLitreBucket.length < 1 && vase.length < 1) {
                cQuesterV2.status = "Emptying jug";
                Utils.useItemOnObject(FULL_JUG, DRAIN);
                Timer.waitCondition(() -> Inventory.find(EMPTY_JUG).length > 0, 6000, 9000);
                Utils.shortSleep();
                updateItems();
            }
            if (key.length < 1 && invtwofifthsBucket.length > 0 && invEmptyJug.length > 0 && fourLitreBucket.length < 1 && vase.length < 1) {
                cQuesterV2.status = "Getting 2/5 Jug";
                if (Utils.useItemOnItem(TWO_FIFTHS_BUCKET, EMPTY_JUG))
                    Utils.shortSleep();
                updateItems();
            }
            if (key.length < 1 && twoThirdsJug.length > 0 && invEmptyBucket.length > 0 && vase.length < 1 && fourLitreBucket.length < 1) {
                cQuesterV2.status = "Refilling bucket";
                if (Utils.useItemOnObject(emptyBucket, TAP))
                    Timer.abc2WaitCondition(() -> Inventory.find(FULL_BUCKET).length > 0, 8000, 12000);
                Utils.shortSleep();
                updateItems();
            }
            if (key.length < 1 && invFullBucket.length > 0 && twoThirdsJug.length > 0 && vase.length < 1) {
                cQuesterV2.status = "Getting 4L Bucket";
                if (Utils.useItemOnItem(FULL_BUCKET, TWO_THIRDS_JUG)) {
                    Utils.shortSleep();
                    updateItems();
                }
            }
            if (key.length < 1 && fourLitreBucket.length > 0 && vase.length < 1) {
                cQuesterV2.status = "Using 4L bucket on chest";
                if (Utils.useItemOnObject(FOUR_LITRE_BUCKET, CLOSED_CHEST)) {
                    Timer.abc2WaitCondition(() -> Inventory.find(VASE).length > 0, 8000, 12000);
                    updateItems();
                }
            }
            if (key.length < 1 && vase.length > 0 && redGloop.length < 1 && gloopDisk.length < 1 && vaseLid.length < 1) {
                cQuesterV2.status = "Getting Red Gloop";
                if (Utils.useItemOnObject(RED_HERRING, RANGE)) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                    Utils.shortSleep();
                    updateItems();
                }
            }
            if (key.length < 1 && redGloop.length > 0 && gloopDisk.length < 1) {
                cQuesterV2.status = "Using gloop on disk";
                if (Utils.useItemOnItem(RED_GLOOP, woodenDisk)) {
                    Timer.waitCondition(() -> Inventory.find(GLOOP_DISK).length > 0, 3000, 5000);
                    Utils.shortSleep();
                    updateItems();
                }
            }
            if (key.length < 1 && redGloop.length < 1 && gloopDisk.length > 0 && !peerBottomRightRoom.contains(Player.getPosition())) {
                cQuesterV2.status = "Going downstairs";
                if (PathingUtil.clickScreenWalk(new RSTile(2636, 3662, 2))) //if you don't walk to the trap door it goes down the wrong one
                    PathingUtil.movementIdle();

                //TODO fix to be tile specific
                if (Utils.clickObj(CLOSED_TRAPDOOR, "Open")) {
                    Timer.waitCondition(() -> Objects.findNearest(2, OPEN_TRAPDOOR).length > 0, 8000, 10000);
                    Utils.shortSleep();
                }
                //TODO fix to be tile specific
                if (Utils.clickObj(OPEN_TRAPDOOR, "Climb-down")) {
                    Timer.waitCondition(() -> bottomRoom.contains(Player.getPosition()), 4000, 6000);
                    Utils.modSleep();
                }
                if (SEER_BOTTOM_LEFT_ROOM.contains(Player.getPosition())) { // failsafe if we go down the wrong ladder
                    Utils.clickObj("Ladder", "Climb-up");
                    Timer.waitCondition(() -> PEER_UPSTAIRS.contains(Player.getPosition()), 5000, 7000);
                    Utils.modSleep();
                }
            }
            if (key.length <
                    1 && redGloop.length < 1 && Inventory.find(GLOOP_DISK).length > 0 && SEER_BOTTOM_RIGHT_ROOM.contains(Player.getPosition())) {
                cQuesterV2.status = "Using Disks on Mural";
                General.println(cQuesterV2.status);
                if (Utils.useItemOnObject(oldRedDish, MURAL)) {
                    Timer.waitCondition(() -> Inventory.find(oldRedDish).length < 1, 6000, 8000);
                    General.sleep(General.random(300, 1000));
                }
                if (Utils.useItemOnObject(GLOOP_DISK, MURAL)) {
                    Timer.waitCondition(() -> Inventory.find(GLOOP_DISK).length < 1, 6000, 8000);
                    General.sleep(General.random(300, 1000));
                }
                updateItems();
            }
            if (key.length < 1 && vaseLid.length > 0 && redGloop.length < 1) {
                cQuesterV2.status = "Filling vase";

                if (invFullJug.length > 0)
                    if (Utils.useItemOnItem(FULL_JUG, VASE))
                        Timer.abc2WaitCondition(() -> Inventory.find(VASE).length > 0, 4000, 6000);

                if (Inventory.find(VASE_OF_WATER).length > 0)
                    if (Utils.useItemOnItem(VASE_LID, VASE_OF_WATER))
                        Timer.abc2WaitCondition(() -> Inventory.find(SEALED_VASE).length > 0, 4000, 6000);

                updateItems();
            }
            if (sealedVase.length > 0) {
                cQuesterV2.status = "Getting frozen Key";
                sealedVase = Inventory.find(3740);
                if (Utils.clickObj("Ladder", "Climb-up"))
                    Timer.abc2WaitCondition(() -> PEER_UPSTAIRS.contains(Player.getPosition()), 6000, 8000);

                if (PEER_UPSTAIRS.contains(Player.getPosition())) {
                    if (Utils.useItemOnObject(SEALED_VASE, FROZEN_TABLE))
                        Timer.abc2WaitCondition(() -> Inventory.find(FROZEN_KEY).length > 0, 8000, 10000);
                }
                updateItems();
            }
            if (invFrozenKey.length > 0) {
                cQuesterV2.status = "Coking key";
                if (Utils.useItemOnObject(FROZEN_KEY, RANGE)) {
                    Timer.abc2WaitCondition(() -> Inventory.find(KEY).length > 0, 6000, 8000);
                    updateItems();
                }
            }
            if (key.length > 0) {
                if (PEER_UPSTAIRS.contains(Player.getPosition())) {
                    cQuesterV2.status = "Going downstairs";
                    PathingUtil.clickScreenWalk(new RSTile(2636, 3662, 2)); //if you don't walk to the trap door it goes down the wrong one
                    Timer.waitCondition(() -> new RSTile(2636, 3662, 2).getPosition().distanceTo(Player.getPosition()) < 2, 8000, 10000);
                    General.sleep(General.random(600, 2400));

                    if (Utils.clickObj(CLOSED_TRAPDOOR, "Open"))
                        Timer.waitCondition(() -> Objects.findNearest(20, OPEN_TRAPDOOR).length > 0, 8000, 11000);

                    if (Utils.clickObj(OPEN_TRAPDOOR, "Climb-down"))
                        Timer.waitCondition(() -> bottomRoom.contains(Player.getPosition()), 7000, 1000);
                }
                Utils.useItemOnObject(KEY, 4166);
                General.sleep(General.random(1500, 3000));
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                updateItems();
                if (!peerBottomRightRoom.contains(Player.getPosition()) && Inventory.find(KEY).length < 1) {
                    cQuesterV2.status = "Home teleporting";
                    SEER = true;
                    if (GameTab.getOpen() != GameTab.TABS.MAGIC)
                        GameTab.open(GameTab.TABS.MAGIC);
                    if (Interfaces.isInterfaceSubstantiated(218, 5)) {
                        if (Interfaces.get(218, 5).getComponentName().contains("Home Teleport")) {
                            Interfaces.get(218, 5).click();
                            General.sleep(General.random(10000, 15000));
                            break;
                        }
                    }
                }
            }
        }

    }


    //**********************SIGMUND TRIAL**************************//

    public void startSigmund() {
        inventoryItemCheck2(995, 5000);
        cQuesterV2.status = "Fremennik Trials:: Staring Sigmund Trial";
        General.println("[Debug]: " + cQuesterV2.status);
        gotoSigmund();
        General.sleep(1000, 2500);
        if (NpcChat.talkToNPC("Sigmund The Merchant")) {
            NPCInteraction.waitForConversationWindow();
            //NPCInteraction.handleConversation();
            // NPCInteraction.handleConversation();
            NPCInteraction.handleConversation("Yes");
            NPCInteraction.handleConversation();
        }
        General.sleep(3000, 4000);
    }

    public void talkToSigmund() {
        cQuesterV2.status = "Fremennik Trials: Staring Sigmund Trial";
        General.println("[Debug]: " + cQuesterV2.status);
        gotoSigmund();
        if (NpcChat.talkToNPC("Sigmund The Merchant")) {
            NPCInteraction.waitForConversationWindow();
            //NPCInteraction.handleConversation();
            NPCInteraction.handleConversation("Yes");
            NPCInteraction.handleConversation();
        }
    }

    public void sigmundPart1() {
        cQuesterV2.status = "Sigmund part 1";
        General.sleep(100);
        if (!SIGMUNDPART) {
            talkToSigmund();
        }
        if (!SAILOR) {
            sailorTalk();
        }
        if (!OLAF_SIGMUND) {
            olafTalk();
        }
        if (!YRSA) {
            yrsaTalk();
        }
        if (!BRUNDT) {
            brundtTalk();
        }
        if (!SIGLI_SIGMUND) {
            sigiliTalk();
        }
        if (!SLUIRIMEN) {
            skulrimenTalk();
        }
        if (!FISHERMAN) {
            fishermanTalk();
        }
        if (!SWENSEN_SIGMUND) {
            swensenTalk();
        }
        if (!PEER) {
            peerTalk();
        }
        if (!THORVALD_SIGMUND) {
            thorvaldTalk();
        }
        if (!MANNI_SIGMUND) {
            //manniTalk();
            talkToManni1();
        }
        if (!THORA) {
            thoraTalk();
        }
        if (!ASKELADDEN) {
            askeladdenTalk();
        }
    }

    public void checkSpecialItem() {
        invPromissaryNote = Inventory.find(promissaryNote);
        invUnusualFish = Inventory.find(unusualFish);
        invBowString = Inventory.find(customBowString);
        invTrackingMap = Inventory.find(trackingMap);
        invFiscalStatement = Inventory.find(fiscalStatement);
        invSturdyBoots = Inventory.find(sturdyBoots);
        invWeatherForecast = Inventory.find(weatherForecast);
        invSeaFishingMap = Inventory.find(seaFishingMap);
        invLegendaryCocktail = Inventory.find(3707);
        invChampionsToken = Inventory.find(3706);
        invWarriorsContraction = Inventory.find(3710);
        invFremennikBallad = Inventory.find(3699);
        invExoticFlower = Inventory.find(3698);
    }

    public void sigmundPart2() {
        checkSpecialItem();
        while (invExoticFlower.length < 1) {
            thoraTalk();
            checkSpecialItem();
            General.sleep(100);
            if (invWeatherForecast.length > 0) {
                swensenTalk();
                checkSpecialItem();
            }
            if (invSeaFishingMap.length > 0) {
                fishermanTalk();
                checkSpecialItem();
            }
            if (invUnusualFish.length > 0) {
                skulrimenTalk();
                checkSpecialItem();
            }
            if (invBowString.length > 0) {
                sigiliTalk();
                checkSpecialItem();
            }
            if (invTrackingMap.length > 0) {
                brundtTalk();
                checkSpecialItem();
            }
            if (invFiscalStatement.length > 0) {
                yrsaTalk();
                checkSpecialItem();

            }
            if (invSturdyBoots.length > 0) {
                olafTalk();
                sailorTalk();
                startSigmund();
                checkSpecialItem();
            }
            if (invPromissaryNote.length > 0) {
                gotoManni();
                checkSpecialItem();
            }
            if (invLegendaryCocktail.length > 0) {
                talkToManni1();
                checkSpecialItem();
            }
            if (invChampionsToken.length > 0) {
                thorvaldTalk();
                checkSpecialItem();
            }
            if (invWarriorsContraction.length > 0) {
                peerTalk();
                checkSpecialItem();
            }
            if (invWeatherForecast.length > 0) {
                swensenTalk();
                checkSpecialItem();
            }
            if (invSeaFishingMap.length > 0) {
                fishermanTalk();
                checkSpecialItem();
            }
            if (invUnusualFish.length > 0) {
                skulrimenTalk();
                checkSpecialItem();
            }
            if (invBowString.length > 0) {
                sigiliTalk();
                checkSpecialItem();
            }
            if (invTrackingMap.length > 0) {
                brundtTalk();
                checkSpecialItem();
            }
            if (invFiscalStatement.length > 0) {
                yrsaTalk();
                checkSpecialItem();
            }
            if (invSturdyBoots.length > 0) {
                olafTalk();
                checkSpecialItem();
            }
            if (invFremennikBallad.length > 0) {
                sailorTalk();
                checkSpecialItem();
            }
            checkSpecialItem();
        }
        if (invExoticFlower.length > 0) {
            talkToSigmund();
        }
    }

    public void sailorTalk() {
        gotoSailor();
        General.sleep(General.random(1000, 2000));
        NpcChat.talkToNPC("Sailor");
        NPCInteraction.waitForConversationWindow();
        NPCInteraction.handleConversation("Ask about the Merchant's trial");
        if (NPCInteraction.isConversationWindowUp()) {
            SAILOR = true;
        }
        NPCInteraction.handleConversation();


    }

    public void olafTalk() {
        cQuesterV2.status = "Talking to Olaf";
        gotoOlaf();
        if (NpcChat.talkToNPC("Olaf the Bard")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Ask about the Merchant's trial");
            if (NPCInteraction.isConversationWindowUp())
                OLAF_SIGMUND = true;
            NPCInteraction.handleConversation();
        }
    }

    public void yrsaTalk() {
        cQuesterV2.status = "Talking to Yrsa";
        gotoYrsa();
        if (NpcChat.talkToNPC("Yrsa")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Ask about the Merchant's trial");
        }
        if (NPCInteraction.isConversationWindowUp()) {
            YRSA = true;
        }
        NPCInteraction.handleConversation();
    }

    public void brundtTalk() {
        gotoBrundt();
        cQuesterV2.status = "Talking to Brundt";
        if (NpcChat.talkToNPC("Brundt the Chieftain")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Ask about anything else.", "Ask about the Merchant's trial");
        }
        if (NPCInteraction.isConversationWindowUp()) {
            BRUNDT = true;
        }
        NPCInteraction.handleConversation();
    }

    public void sigiliTalk() {
        invItem1 = Inventory.find(huntersTalisman);
        if (invItem1.length < 1) {
            cQuesterV2.status = "Talking to Sigili";
            gotoSigli();
            if (NpcChat.talkToNPC("Sigli the Huntsman")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Ask about the Merchant's trial");
            }
            if (NPCInteraction.isConversationWindowUp()) {
                SIGLI_SIGMUND = true;
            }
            NPCInteraction.handleConversation();
        }
    }

    public void skulrimenTalk() {
        cQuesterV2.status = "Talking to Skulrimen";
        gotoSkulrimen();
        if (NpcChat.talkToNPC("Skulgrimen")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Ask about the Merchant's trial");
            if (NPCInteraction.isConversationWindowUp()) {
                SLUIRIMEN = true;
            }
            NPCInteraction.handleConversation();
        }
    }

    public void fishermanTalk() {
        cQuesterV2.status = "Talking to Fisherman";
        gotoFisherman();
        NpcChat.talkToNPC("Fisherman");
        NPCInteraction.waitForConversationWindow();
        NPCInteraction.handleConversation("Ask about the Merchant's trial");
        if (NPCInteraction.isConversationWindowUp()) {
            FISHERMAN = true;
        }
        NPCInteraction.handleConversation();
    }

    public void swensenTalk() {
        cQuesterV2.status = "Talking to Swensen";
        gotoSwensen();
        NpcChat.talkToNPC("Swensen the Navigator");
        NPCInteraction.waitForConversationWindow();
        NPCInteraction.handleConversation("Ask about the Merchant's trial");
        if (NPCInteraction.isConversationWindowUp()) {
            SWENSEN_SIGMUND = true;
        }
        NPCInteraction.handleConversation();
    }

    public void peerTalk() {
        cQuesterV2.status = "Talking to the seer";
        gotoSeer();
        peerTheSeer = NPCs.find("Peer the Seer");
        NpcChat.talkToNPC("Peer the Seer");
        NPCInteraction.waitForConversationWindow();
        NPCInteraction.handleConversation("Ask about the Merchant's trial");
        if (NPCInteraction.isConversationWindowUp()) {
            PEER = true;
        }
        NPCInteraction.handleConversation();
    }

    public void thorvaldTalk() {
        cQuesterV2.status = "Talking to Thorvald";
        gotoSkulrimen();
        if (NpcChat.talkToNPC("Thorvald the Warrior")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Ask about the Merchant's trial");
            if (NPCInteraction.isConversationWindowUp()) {
                THORVALD_SIGMUND = true;
            }
            NPCInteraction.handleConversation();
        }
    }


    public void thoraTalk() {
        if (!THORA_AREA.contains(Player.getPosition())) {
            PathingUtil.walkToTile(new RSTile(2658, 3669, 0), 2, false);
            Timer.abc2WaitCondition(() -> THORA_AREA.contains(Player.getPosition()) && !Player.isMoving(), 9000, 12000);
        }
        cQuesterV2.status = "Talking to Thora";
        RSNPC[] targetNPC = NPCs.findNearest(3932);
        if (targetNPC.length > 0) {
            if (!targetNPC[0].isOnScreen() || !targetNPC[0].isClickable()) {
                if (PathingUtil.localNavigation(targetNPC[0].getPosition()))
                    Timer.waitCondition(() -> targetNPC[0].isClickable(), 8000, 12000);
            }
            if (Utils.clickNPC(targetNPC[0].getID(), "Talk to")) { // has to be this method.
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Ask about the Merchant's trial");
                NPCInteraction.handleConversation();
                THORA = true;
                General.sleep(500);
                NPCInteraction.handleConversation();
            }
        }
    }

    public void askeladdenTalk() {
        gotoAskeladden();
        cQuesterV2.status = "Talking to Askeladden";
        if (NpcChat.talkToNPC("Askeladden")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Ask about the Merchant's trial");
            if (NPCInteraction.isConversationWindowUp())
                ASKELADDEN = true;
            NPCInteraction.handleConversation("Yes");
            NPCInteraction.handleConversation();
        }
    }

    /**
     * **************
     * ** MOVEMENT **
     * **************
     */
//
    public void gotoOlaf() {
        cQuesterV2.status = "Going to Olaf";
        General.println("[Debug]: Going to Olaf");
        PathingUtil.walkToTile(olafTile);
    }

    public void gotoManni() {
        cQuesterV2.status = "Going to Manni";
        General.println("[Debug]: Going to Manni");
        PathingUtil.walkToArea(START_AREA);
    }

    public void gotoAskeladden() {
        General.println("[Debug]: Going to Askeladden");
        PathingUtil.walkToTile(askeladdenTile);
    }

    public void gotoSeer() {
        General.println("[Debug]: Going to Peer the Seer");
        PathingUtil.walkToTile(seerTile);
    }

    public void gotoSwensen() {
        General.println("[Debug]: Going to Swensen");
        PathingUtil.walkToTile(swensenTile);
    }

    public void gotoFisherman() {
        General.println("[Debug]: Going to Fisherman");
        PathingUtil.walkToTile(fishermanTile);
    }

    public void gotoSkulrimen() {
        cQuesterV2.status = "Going to Skulrimen.";
        General.println("[Debug]: Going to Skiulrimen ");
        PathingUtil.walkToTile(skulgrimenTile);
    }

    public void gotoSigli() {
        cQuesterV2.status = "Going to Sigli.";
        General.println("[Debug]: Going to Sigli");
        PathingUtil.walkToTile(sigliTile);
        walkIdle();
    }

    public void gotoBrundt() {
        cQuesterV2.status = "Going to Brundt.";
        General.println("[Debug]: Going to Brundt");
        PathingUtil.walkToTile(brundtTile);
    }

    public void gotoYrsa() {
        cQuesterV2.status = "Going to Yrsa.";
        General.println("[Debug]: Going to Yrsa");
        PathingUtil.walkToTile(yrsaTile);
    }

    public void gotoSigmund() {
        cQuesterV2.status = "Going to Sigmund.";
        General.println("[Debug]: Going to Sigmund");
        PathingUtil.walkToTile(sigmundArea.getRandomTile());
    }

    public void gotoSailor() {
        cQuesterV2.status = "Going to Sailor.";
        General.println("[Debug]: Going to Sailor");
        PathingUtil.walkToTile(sailorTile);
    }

    public void walkIdle() {
        Timer.waitCondition(() -> Player.isMoving(), 2000, 4000);
        Timer.waitCondition(() -> !Player.isMoving(), 10000, 15000);
    }

    public void getItemsPart1() {
        cQuesterV2.status = "Getting items - Part 1";
        General.println("[Debug]: " + cQuesterV2.status);
        BankManager.open(true);
        BankManager.depositAll(true);
        BankManager.checkEquippedGlory();
        BankManager.withdraw2(1, true, beer);
        BankManager.withdraw2(10000, true, coins);
        BankManager.withdraw2(12, true, lobster);
        BankManager.withdraw2(1, true, tinderbox);
        BankManager.withdraw2(4, true, camelotTab);
        BankManager.withdraw2(800, true, mindRune);
        BankManager.withdraw2(1600, true, airRune);
        BankManager.withdraw2(2, true, STAMINA_POTION[0]);
        BankManager.withdraw2(1, true, staffOfFire);
        BankManager.withdraw2(1, true, ItemId.BLUE_WIZ_TOP);
        BankManager.withdraw2(1, true, ItemId.BLUE_WIZ_HAT);
        BankManager.close(true);
        Utils.equipItem(ItemId.BLUE_WIZ_TOP);
        Utils.equipItem(ItemId.BLUE_WIZ_HAT);
        Utils.equipItem(staffOfFire);
    }

    public void getOlafItems() {
        if (Inventory.find(enchantedLyre).length < 1) {
            cQuesterV2.status = "Getting Items for Olaf";
            invRawShark = Inventory.find(rawShark);
            invTinderbox = Inventory.find(tinderbox);
            invItem1 = Inventory.find(lyre);
            invItem2 = Inventory.find(lobster);
            invCamelotTab = Inventory.find(camelotTab);
            if ((invRawShark.length < 1) || (invItem1.length < 1) || (invCamelotTab.length < 1) || (invItem2.length < 6)) { // this is to prevent it doubling back to this point if script loops
                cQuesterV2.status = "Getting Items for Olaf";
                General.println("[Debug]: " + cQuesterV2.status);
                BankManager.open(true);
                BankManager.depositAll(true);
                BankManager.withdraw(10000, true, 995);
                BankManager.withdraw(14, true, lobster);
                BankManager.withdraw(1, true, rawShark);
                BankManager.withdraw(5, true, camelotTab);
                BankManager.withdraw(1, true, lyre);
                BankManager.withdraw(2, true, STAMINA_POTION[0]);
                General.sleep(General.random(500, 2000));
            }
        }
    }

    public void combatIdle() {
        cQuesterV2.status = "Fight Idle";
        General.println("[Debug]: Fight idle.");
        while (Combat.isUnderAttack()) {
            if (!lanzigSafeArea.contains(Player.getPosition()) && lanzigSafeTle.isClickable()) {
                Walking.clickTileMS(lanzigSafeTle, "Walk here");
            } else if (!lanzigSafeTle.isClickable()) {
                PathingUtil.walkToTile(lanzigSafeTle);
            }
            General.sleep(General.random(1000, 2000));
            if (Combat.getHPRatio() < General.random(40, 65)) {
                invItem1 = Inventory.find(lobster);
                if (invItem1.length > 0) {
                    Clicking.click("Eat", invItem1);
                } else {
                    BankManager.open(true);
                    BankManager.withdraw(12, true, lobster);
                    Banking.close();
                }
            }
        }
    }


    /**
     * *** MANI THE REVELLER ***
     */


    public void finishQuest() {
        cQuesterV2.status = "Finishing quest";
        PathingUtil.walkToArea(START_AREA);
        if (NpcChat.talkToNPC("Brundt the Chieftain")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Ask about anything else.");
            NPCInteraction.handleConversation();
            NPCInteraction.handleConversation();
        }
        if (Interfaces.isInterfaceSubstantiated(277, 16)) {
            Interfaces.get(277, 16).click();
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }
    }


    RSArea SAFE_AREA = new RSArea(new RSTile(2670, 3665, 0), new RSTile(2669, 3665, 0));

    public void setNPCAttackPreference() {
        if (GameTab.getOpen() != GameTab.TABS.OPTIONS) {
            GameTab.open(GameTab.TABS.OPTIONS);
            Utils.idle(200, 500);
        }
        if (GameTab.getOpen() == GameTab.TABS.OPTIONS) {
            if (Interfaces.get(261, 1, 6) != null) {
                Interfaces.get(261, 1, 6).click();
                Utils.idle(200, 500);
            }
            if (Interfaces.get(261, 92, 3) != null) { // selects drop down
                Interfaces.get(261, 92, 3).click();
                Utils.idle(100, 300);
            }
            if (Interfaces.get(261, 109, 3) != null) { // selects 'left click when availible'
                Interfaces.get(261, 109, 3).click();

                Utils.idle(300, 800);
            }
        }
    }

    public void worldHopLanzig() {
        int world = WorldHopper.getRandomWorld(true, false);

        if (WorldHopper.changeWorld(world)) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Yes. In the future, only warn about dangerous worlds.");

            if (WorldHopper.changeWorld(world))
                Timer.waitCondition(() -> WorldHopper.getWorld() ==
                        world, 12000, 18000);
        }
    }

    public void killLanzig() { // need a door check
        if (!BankManager.checkInventoryItems(ItemId.MIND_RUNE, ItemId.AIR_RUNE, ItemId.LOBSTER) ||
                Equipment.find(ItemId.STAFF_OF_FIRE).length < 1) {
            buyItems();
            getItemsPart1();
        }
        RSItem[] invLyre = Inventory.find(lyre);
        RSGroundItem[] groundLyre = GroundItems.find(lyre);

        cQuesterV2.status = "Killing Lanzig";
        General.println("[Debug]: " + cQuesterV2.status);
        setNPCAttackPreference();
        while (Inventory.find(lyre).length < 1) {
            General.sleep(20, 40);

            if (!Autocast.isAutocastEnabled(Autocast.FIRE_STRIKE))
                Autocast.enableAutocast(Autocast.FIRE_STRIKE);

            RSObject[] door = Objects.findNearest(7, 4250);
            if (door.length > 0 && Doors.handleDoor(door[0], true))
                General.sleep(800, 1600);

            if (!SAFE_AREA.contains(Player.getPosition()) && lanzigSafeTle.isClickable()) {
                if (PathingUtil.clickScreenWalk(lanzigSafeTle))
                    PathingUtil.movementIdle();
            } else if (!lanzigSafeTle.isClickable()) {
                PathingUtil.walkToTile(lanzigSafeTle);
            }

            groundLyre = GroundItems.find(lyre);
            lanzig = NPCs.find("Lanzig");
            if (lanzig.length > 0) {
                cQuesterV2.status = "Attacking Lanzig";


                if (!lanzig[0].isClickable())
                    DaxCamera.focus(lanzig[0]);

                if (!lanzig[0].isInCombat() && Utils.clickNPC("Lanzig", "Attack")) {
                    Timer.waitCondition(() -> lanzig[0].isInCombat(), 3000, 5000);
                    if (!SAFE_AREA.contains(Player.getPosition())) {
                        if (lanzigSafeTle.isClickable())
                            AccurateMouse.walkScreenTile(lanzigSafeTle);
                        else
                            Walking.blindWalkTo(lanzigSafeTle);
                        Timer.waitCondition(() -> SAFE_AREA.contains(Player.getPosition()), 3500, 50);
                    }
                    if (CombatUtil.waitUntilOutOfCombat(General.random(40, 60)))
                        Timer.waitCondition(() -> lanzig[0] == null, 3000, 5000);
                }

                groundLyre = GroundItems.find(lyre);
                if (groundLyre.length > 0) {
                    cQuesterV2.status = "Looting lyre";
                    General.println("[Debug]: " + cQuesterV2.status);
                    if (Utils.clickGroundItem(lyre) &&
                            Timer.waitCondition(() -> Inventory.find(lyre).length > 0, 10000, 12000))
                        break;
                }
                if (!SAFE_AREA.contains(Player.getPosition())) {
                    cQuesterV2.status = "Moving to safe tile.";
                    if (lanzigSafeTle.isClickable())
                        AccurateMouse.walkScreenTile(lanzigSafeTle);
                    else
                        Walking.blindWalkTo(lanzigSafeTle);
                    Timer.waitCondition(() -> SAFE_AREA.contains(Player.getPosition()), 3500, 5000);
                }
            } else {
                cQuesterV2.status = "Waiting for Lanzig to spawn";
                AntiBan.timedActions();
                General.sleep(General.random(1000, 5000));
            }
        }
    }

    public void talkToManni1() {
        if (Inventory.find(lowAlcoholKeg).length < 1) {
            if (!barArea.contains(Player.getPosition())) {
                cQuesterV2.status = "Talk to Manni";
                General.println("[Debug]: " + cQuesterV2.status);
                PathingUtil.walkToArea(START_AREA);
                manni = NPCs.find(3920);
            } // DO NOT CHANGE THIS, works well for Manni, whereas other methods don't seem to
            if (manni.length > 0) {
                if (!manni[0].isClickable()) {
                    manni[0].adjustCameraTo();
                }
                AccurateMouse.click(manni[0], "Talk-to");
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                NPCInteraction.handleConversation("Yes", "Ask about the Merchant's trial");
                NPCInteraction.handleConversation("yes", "Yes");
                NPCInteraction.handleConversation();
                General.sleep(General.random(8000, 14000));
                NPCInteraction.handleConversation();

            }
        }
    }

    public void loseDrinkingContest() {
        invLowAlcoholKeg = Inventory.find(lowAlcoholKeg);
        if (!barArea.contains(Player.getPosition())) {
            if (invLowAlcoholKeg.length < 1 && Game.getSetting(347) == 1) {
                cQuesterV2.status = "Losing drinking contest";
                General.println("[Debug]: " + cQuesterV2.status);
                PathingUtil.walkToArea(START_AREA);
                if (NpcChat.talkToNPC("Manni the Reveller")) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation("yes", "Yes");
                    NPCInteraction.handleConversation();
                    General.sleep(General.random(6000, 10000)); //sleep during drinking contest
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                }
            }
        }
    }

    public void getBooze() {
        invBeerTankard = Inventory.find(beerTankard);
        invLowAlcoholKeg = Inventory.find(lowAlcoholKeg);
        cQuesterV2.status = "Getting keg of beer";
        General.println("[Debug]: " + cQuesterV2.status);
        if (invLowAlcoholKeg.length < 1 || invBeerTankard.length < 1) {
            Walking.blindWalkTo(inFrontOfBeer);
            General.sleep(General.random(4000, 6000));
            RSGroundItem[] tableBeerTankard = GroundItems.find(beerTankard);
            RSGroundItem[] tableKeg = GroundItems.find(3711);
            if (tableBeerTankard.length > 0) {

                if (!tableBeerTankard[0].isClickable())
                    DaxCamera.focus(tableBeerTankard[0]);

                if (AccurateMouse.click(tableBeerTankard[0], "Take"))
                    Timer.waitCondition(() -> Inventory.find(beerTankard).length > 0, 10000, 14000);
            }

            if (tableKeg.length > 0) {

                if (!tableKeg[0].isClickable())
                    DaxCamera.focus(tableKeg[0]);

                if (AccurateMouse.click(tableKeg[0], "Take"))
                    Timer.waitCondition(() -> Inventory.find(kegOfBeer).length > 0, 10000, 14000);

            }
            Utils.idle(300, 800);
        }
    }

    public void getLowAlcholKeg() {
        invLowAlcoholKeg = Inventory.find(lowAlcoholKeg);
        if (invLowAlcoholKeg.length < 1) {
            cQuesterV2.status = "Getting low alcohol keg";
            General.println("[Debug]: " + cQuesterV2.status);
            RSItem[] invLowAlcoholKeg = Inventory.find(lowAlcoholKeg);
            if (invLowAlcoholKeg.length < 1) {
                if (!barArea.contains(Player.getPosition())) {
                    PathingUtil.walkToTile(bar);
                }
                if (NpcChat.talkToNPC("Poison Salesman")) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation("Talk about the Fremennik Trials");
                    NPCInteraction.handleConversation(); // leave this
                    NPCInteraction.handleConversation("yes", "Yes");
                    NPCInteraction.handleConversation(); // leave this
                    NPCInteraction.handleConversation("yes", "Yes");
                    NPCInteraction.handleConversation();
                }
            }
        }
    }

    public void handleCouncilWorkman() {
        RSItem[] invLowAlcoholKeg = Inventory.find(lowAlcoholKeg);
        RSItem[] invBeerTankard = Inventory.find(beerTankard);
        RSItem[] invCherryBomb = Inventory.find(cherryBomb);

        if (invLowAlcoholKeg.length > 0 && invCherryBomb.length < 1) {
            cQuesterV2.status = "Handling Workman";
            invLowAlcoholKeg = Inventory.find(lowAlcoholKeg);
            RSItem[] invItem1 = Inventory.find(litCherryBomb);
            invCherryBomb = Inventory.find(cherryBomb);

            if (invLowAlcoholKeg.length > 0) {
                if (invCherryBomb.length < 1 || invItem1.length < 1) {
                    PathingUtil.walkToTile(bridge);
                    //   councilWorkman = NPCs.find("Council workman");
                    RSItem[] invBeer = Inventory.find(beer);
                    invBeerTankard = Inventory.find(beerTankard);
                    if (invBeer.length > 0 && Utils.useItemOnNPC(beer, "Council workman")) {
                        Timer.abc2WaitCondition(NPCInteraction::isConversationWindowUp, 8000, 12000);

                    } else if (invBeerTankard.length > 0) {
                        if (Utils.useItemOnNPC(beerTankard, "Council workman"))
                            Timer.abc2WaitCondition(NPCInteraction::isConversationWindowUp, 8000, 12000);
                    }
                }
            }
        }
    }

    RSArea pipeArea = new RSArea(new RSTile(2663, 3679, 0), new RSTile(2666, 3672, 0));

    public void plantBomb() {
        cQuesterV2.status = "Planting bomb";
        RSItem[] invLowAlcoholKeg = Inventory.find(lowAlcoholKeg);
        if (invLowAlcoholKeg.length > 0) {

            RSItem[] invCherryBomb = Inventory.find(cherryBomb);

            if (invCherryBomb.length > 0) {
                General.println("[Debug]: Walking to Pipe");
                PathingUtil.walkToTile(pipeTile, 4, true);

                General.println("[Debug]: Planting Bomb");
                if (Utils.useItemOnItem(tinderbox, cherryBomb))
                    General.sleep(General.random(500, 1500));
            }

            if (Utils.clickObj("Pipe", "Put-inside"))
                Timer.abc2WaitCondition(() -> Inventory.find(litCherryBomb).length < 1, 5000, 8000);
        }
    }

    public void switchBeer() {
        RSItem[] invCherryBomb = Inventory.find(cherryBomb);
        RSItem[] invLowAlcoholKeg = Inventory.find(lowAlcoholKeg);
        RSItem[] invKegOfBeer = Inventory.find(kegOfBeer);
        if (invCherryBomb.length < 1 && invLowAlcoholKeg.length > 0 && invKegOfBeer.length < 1) {
            cQuesterV2.status = "Fremennik Trials: Switching beer";
            Log.log("[Debug]: " + cQuesterV2.status);

            PathingUtil.walkToTile(inFrontOfBeer, 2, false);

            RSGroundItem[] tableBeerTankard = GroundItems.find(beerTankard);
            RSGroundItem[] tableKeg = GroundItems.find(kegOfBeer);

            if (tableBeerTankard.length > 0) {

                if (!tableBeerTankard[0].isClickable())
                    tableBeerTankard[0].adjustCameraTo();

                if (AccurateMouse.click(tableKeg[0], "Take"))
                    Timer.waitCondition(() -> Inventory.find(kegOfBeer).length > 0, 25000, 30000);
            }
        }

        if (Utils.useItemOnItem(lowAlcoholKeg, kegOfBeer))
            General.sleep(General.random(300, 800));
    }

    public void talkToManni2() {
        cQuesterV2.status = "Ta anni again";
        General.println("[Debug]: " + cQuesterV2.status);
        RSItem[] invCherryBomb = Inventory.find(cherryBomb);

        if (!barArea.contains(Player.getPosition())) {
            if (invCherryBomb.length < 1) {
                cQuesterV2.status = "Talking to Manni again";
                General.println("[Debug]: Walking to Manni.");
                PathingUtil.walkToArea(START_AREA);
                RSNPC[] manni = NPCs.find("Manni the Reveller");
                if (manni.length > 0) {
                    if (!manni[0].isClickable()) {
                        manni[0].adjustCameraTo();
                    }
                    AccurateMouse.click(manni[0], "Talk-to");
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                    NPCInteraction.handleConversation("yes", "Yes");
                    NPCInteraction.handleConversation();
                    General.sleep(General.random(5000, 8000));
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                }
            }
        }
    }


    /**
     * OLAF THE BARD
     */

    public void startOlaf() {
        cQuesterV2.status = "Starting Olaf's Task";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToTile(olafTile);
        if (NpcChat.talkToNPC("Olaf the Bard")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("yes", "Yes");
            NPCInteraction.handleConversation();
        }
    }

    public void enchantLyre() {
        cQuesterV2.status = "Enchanting Lyre";
        General.println("[Debug]: " + cQuesterV2.status);
        invEnchantedLyre = Inventory.find(enchantedLyre);
        if (invEnchantedLyre.length < 1) {
            General.println("[Debug]: Going to enchant lyre");
            PathingUtil.walkToTile(strangeAltarTile);

            if (Utils.useItemOnObject(rawShark, "Strange altar"))
                Timer.abc2WaitCondition(() -> Inventory.find(enchantedLyre).length > 0, 7000, 10000);
        }
    }

    RSArea stageArea = new RSArea(new RSTile(2662, 3682, 0), new RSTile(2655, 3685, 0));
    RSArea predoorArea = new RSArea(new RSTile(2667, 3685, 0), new RSTile(2670, 3681, 0));
    RSArea postDoorArea = new RSArea(new RSTile(2666, 3682, 0), new RSTile(2663, 3685, 0));

    public void playLyre() {
        if (Inventory.find(enchantedLyre).length > 0) {
            cQuesterV2.status = "Play Lyre";
            while (!stageArea.contains(Player.getPosition())) {
                General.sleep(100);
                if (!predoorArea.contains(Player.getPosition())) {
                    PathingUtil.walkToTile(stageDoor);
                    Timer.waitCondition(() -> predoorArea.contains(Player.getPosition()), 8000, 12000);
                }
                if (predoorArea.contains(Player.getPosition())) {
                    General.sleep(General.random(500, 2000));

                    RSObject[] door = Objects.findNearest(10, "Door");
                    if (door.length > 0) {
                        DaxCamera.focus(door[0]);
                        if (AccurateMouse.click(door[0], "Open")) {
                            Timer.waitCondition(() -> postDoorArea.contains(Player.getPosition()), 8000, 12000);
                            NPCInteraction.waitForConversationWindow();
                            NPCInteraction.handleConversation();
                            Walking.blindWalkTo(stageArea.getRandomTile());
                            Timer.waitCondition(() -> stageArea.contains(Player.getPosition()), 8000, 12000);
                        }
                    }
                }
            }
            if (stageArea.contains(Player.getPosition())) {
                invEnchantedLyre = Inventory.find(enchantedLyre);
                if (invEnchantedLyre.length > 0) {
                    Clicking.click("Play", invEnchantedLyre);
                    General.sleep(General.random(5000, 8000));
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                    OLAF = true;
                }

                Walking.blindWalkTo(new RSTile(2666, 3683, 0));
                Timer.waitCondition(() -> postDoorArea.contains(Player.getPosition()), 8000, 12000);
                General.sleep(General.random(500, 2000));

                RSObject[] door = Objects.findNearest(10, "Door");
                if (door.length > 0)
                    if (AccurateMouse.click(door[0], "Open"))
                        Timer.waitCondition(() -> predoorArea.contains(Player.getPosition()), 8000, 12000);

            }
        }
    }


    // ************** SIGLI ***********************************//
    public void startSigli() {
        invItem1 = Inventory.find(huntersTalisman);
        invItem2 = Inventory.find(3696);
        if (invItem1.length < 1 && invItem2.length < 1) {
            gotoSigli();
            if (NpcChat.talkToNPC("Sigli the Huntsman")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation(); // need this
                NPCInteraction.handleConversation("What's a Draugen?");
                NPCInteraction.handleConversation("Yes");
                NPCInteraction.handleConversation();
            }
        }
    }

    public void getSigliItems() {
        cQuesterV2.status = "Fremenik Trials: Getting Items for Sigli's task.";
        General.println("[Debug]: " + cQuesterV2.status);
        invItem2 = Inventory.find(mindRune);
        invItem1 = Inventory.find(lobster);
        invCamelotTab = Inventory.find(camelotTab);
        if ((invItem2.length < 1) || (invItem1.length < 1) || (invCamelotTab.length < 1)) {
            General.println("[Debug]: Withdrawing Quest items.");
            DaxWalker.walkToBank();
            BankManager.open(true);
            BankManager.checkEquippedGlory();
            BankManager.depositAll(true);
            BankManager.withdraw(12, true, lobster);
            BankManager.withdraw(400, true, mindRune);
            BankManager.withdraw(800, true, airRune);
            BankManager.getPotion(STAMINA_POTION);

            inventoryItemCheck2(lobster, 9);
            inventoryItemCheck2(mindRune, 200);
            inventoryItemCheck2(airRune, 400);
            inventoryItemCheck2(camelotTab, 3);
        }
    }

    public RSArea DRAUGEN_RESET_AREA = new RSArea(draugenTile, 10);
    RSArea DRAUGEN_AREA = new RSArea(
            new RSTile[]{
                    new RSTile(2602, 3654, 0),
                    new RSTile(2625, 3646, 0),
                    new RSTile(2653, 3643, 0),
                    new RSTile(2693, 3643, 0),
                    new RSTile(2714, 3634, 0),
                    new RSTile(2710, 3597, 0),
                    new RSTile(2674, 3600, 0),
                    new RSTile(2654, 3596, 0),
                    new RSTile(2638, 3593, 0),
                    new RSTile(2592, 3619, 0)
            }
    );

    public void locateDraugen() {
        RSNPC[] draug = NPCs.findNearest("Draugen");
        if (draug.length > 0) return;

        if (!Game.isRunOn())
            Options.setRunEnabled(true);

        RSItem[] tal = Inventory.find(huntersTalisman);
        if (tal.length > 0) {
            cQuesterV2.status = "Locating Draugen.";

            if (!SE_AREA.contains(Player.getPosition()) && !NWArea.contains(Player.getPosition()) && !SE_SAFE_TILE_AREA.contains(Player.getPosition())
                    && !SWArea.contains(Player.getPosition()) && !NEArea.contains(Player.getPosition()) &&
                    !DRAUGEN_AREA.contains(Player.getPosition())) {
                General.println("[Debug]: Going to Draugen reset tile");
                PathingUtil.walkToTile(draugenTile);
            }
            if (tal[0].click("Locate"))
                General.sleep(General.randomSD(500, 5000, 1750, 500));
        }

        if (moveSW) {
            Log.log("[Debug]: Moving South-West");
            PathingUtil.walkToTile(Player.getPosition().translate(General.random(-6, -9), General.random(-6, -9)));
            PathingUtil.movementIdle();
            moveSW = false;
            checkButterfly();
        } else if (moveSE) {
            if (SE_AREA.contains(Player.getPosition()) || Player.getPosition().getX() > 2660) {
                Log.log("[Debug]: Already as far SE as we will go");
                moveSE = false;
                shouldHop = true;
                PathingUtil.walkToTile(Player.getPosition().translate(General.random(-9, -12), 0));
                PathingUtil.movementIdle();
                checkButterfly();
            } else {
                Log.log("[Debug]: Moving South-East");
                PathingUtil.walkToTile(Player.getPosition().translate(General.random(6, 9), General.random(-6, -9)));
                PathingUtil.movementIdle();
                moveSE = false;
                checkButterfly();
            }
        } else if (moveNE) {
            Log.log("[Debug]: Moving North-East");
            PathingUtil.walkToTile(Player.getPosition().translate(General.random(6, 12), General.random(6, 12)));
            PathingUtil.movementIdle();
            moveNE = false;
            checkButterfly();
        } else if (moveNW) {
            Log.log("[Debug]: Moving North-West");
            PathingUtil.walkToTile(Player.getPosition().translate(General.random(-6, -12), General.random(6, 12)));
            PathingUtil.movementIdle();
            moveNW = false;
            checkButterfly();
        }
    }

    public boolean checkButterfly() {
        RSNPC[] butterfly = NPCs.find("Butterfly");
        if (butterfly.length > 0) {
            General.println("[Debug]: Checking area for butterfly -> true");
            PathingUtil.walkToTile(butterfly[0].getPosition());
            Timer.waitCondition(() -> butterfly[0].getPosition().distanceTo(Player.getPosition()) < 5, 3500, 5000);
            return Utils.clickInventoryItem(huntersTalisman);
        } else
            return false;
    }

    public RSTile SE_SAFE_TILE = new RSTile(2661, 3620, 0);
    public RSArea SE_AREA = new RSArea(new RSTile[]{new RSTile(2662, 3603, 0), new RSTile(2658, 3603, 0), new RSTile(2655, 3600, 0), new RSTile(2649, 3600, 0), new RSTile(2649, 3609, 0), new RSTile(2660, 3618, 0), new RSTile(2666, 3618, 0), new RSTile(2666, 3603, 0)});
    public RSArea SE_SAFE_TILE_AREA = new RSArea(SE_SAFE_TILE, 10);

    boolean moveS = false;
    boolean moveN = false;
    boolean moveW = false;
    boolean moveSW = false;
    boolean moveSE = false;
    boolean moveNW = false;
    boolean moveNE = false;

    public void handleTalismanMessage(String message) {
        if (message.contains("The talisman guides you")) {
            General.println("[Debug]: Handling talisman message");
            if (message.contains("north-west")) {
                if (NWArea.contains(Player.getPosition())) {
                    if (!checkButterfly()) {
                        shouldHop = true;
                        return;
                    }
                } else {
                    moveSW = false;
                    moveSE = false;
                    moveNW = true;
                    moveNE = false;
                    //PathingUtil.walkToArea(NWArea);
                    //Utils.clickInventoryItem(huntersTalisman);

                }
            } else if (message.contains("south-west")) {
                if (SWArea.contains(Player.getPosition())) {
                    if (!checkButterfly()) {
                        shouldHop = true;
                        return;
                    }
                } else {
                    moveSW = true;
                    moveSE = false;
                    moveNW = false;
                    moveNE = false;
                    // PathingUtil.walkToArea(SWArea);
                    //Utils.clickInventoryItem(huntersTalisman);
                }


            } else if (message.contains("north-east")) {
                if (NEArea.contains(Player.getPosition())) {
                    General.println("[Debug]: We are already North east");

                    if (!checkButterfly()) {
                        General.println("[Message Listener}: World-hopping");
                        shouldHop = true;
                        return;
                    }
                } else if (SE_AREA.contains(Player.getPosition())) {
                    moveSW = false;
                    moveSE = false;
                    moveNW = false;
                    moveNE = true;

                    //PathingUtil.walkToTile(SE_SAFE_TILE);
                    //  Utils.clickInventoryItem(huntersTalisman);


                } /*else if (SE_SAFE_TILE_AREA.contains(Player.getPosition())) {
                    General.println("[Debug]: We are already North east");
                    if (!checkButterfly()) {
                        General.println("[Message Listener}: World-hopping");
                        shouldHop = true;
                        return;
                    }
                } else {
                    PathingUtil.walkToArea(NEArea);
                    Utils.clickInventoryItem(huntersTalisman);
                }*/

            } else if (message.contains("south-east")) {
                if (SE_AREA.contains(Player.getPosition())) {
                    if (!checkButterfly()) {
                        General.println("[Message Listener}: World-hopping");
                        shouldHop = true;
                        return;
                    }
                } else {
                    moveSW = false;
                    moveSE = true;
                    moveNW = false;
                    moveNE = false;
                    // PathingUtil.walkToArea(SE_AREA);
                    //Utils.clickInventoryItem(huntersTalisman);
                }
            }
        }

    }

    public void handleDraugenIsHere(String message) {
        if (message.contains("The Draugen is here")) {
            if (NEArea.contains(Player.getPosition()) || DRAUGEN_RESET_AREA.contains(Player.getPosition())
                    || NWArea.contains(Player.getPosition()) || SWArea.contains(Player.getPosition())) {
                shouldAttack = true;
                return;
                //  attackDraugen();
            }
            if (SE_AREA.contains(Player.getPosition())) {
                attackDraugenSouthEast();
            }
        }
    }

    boolean shouldHop = false;

    public void worldHop() {
        if (shouldHop && NPCs.find("The Draugen").length < 1) {
            i++;
            int world = WorldHopper.getRandomWorld(true, false);

            if (WorldHopper.changeWorld(world)) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Yes. In the future, only warn about dangerous worlds.");

                if (WorldHopper.changeWorld(world))
                    Timer.waitCondition(() -> WorldHopper.getWorld() ==
                            world, 12000, 18000);
            }
            locate = true;

            Utils.clickInventoryItem(huntersTalisman);
            shouldHop = false;
        }
    }

    public boolean SHOULD_PRAY = false;

    public RSTile safeTile1 = new RSTile(2612, 3642, 0);
    public RSArea safeArea1 = new RSArea(new RSTile(2611, 3641, 0), new RSTile(2610, 3641, 0));
    public RSTile safeTile2 = new RSTile(2618, 3639);
    public boolean shouldAttack = false;


    public void attackDraugen() {
        if (shouldAttack) {
            Log.log("[Debug]: Killing Draugen");

            draugen = NPCs.find("The Draugen");

            if (Skills.getCurrentLevel(Skills.SKILLS.PRAYER) >= 43)
                SHOULD_PRAY = true;

            while (draugen.length > 0) {
                General.sleep(100);
                if (SHOULD_PRAY && Prayer.getPrayerPoints() > 0)
                    Prayer.enable(Prayer.PRAYERS.PROTECT_FROM_MELEE);


                if (Combat.getHPRatio() < General.random(45, 65) && Inventory.find(
                        ItemId.LOBSTER).length > 0) // low health, eat food
                    AccurateMouse.click(Inventory.find(379)[0], "Eat");

                if (safeTile1.isClickable() && !safeTile1.equals(Player.getPosition())) { // we are not standing on the safetile, so we move there
                    Walking.clickTileMS(safeTile1, "Walk here");
                    Timer.waitCondition(() -> safeTile1.distanceTo(Player.getPosition()) < 2, 3500, 5000);

                    Utils.idle(175, 350);

                } else if (!safeTile1.isClickable() && !safeArea1.contains(Player.getPosition())) {
                    General.println("[Debug]: Safetile 1 is not clickable and we're not standing on it");
                    Walking.blindWalkTo(safeTile1);
                    Timer.waitCondition(() -> safeTile1.distanceTo(Player.getPosition()) < 2, 3500, 5000);
                }
                draugen = NPCs.find("The Draugen");

                if (draugen.length > 0) { // rechecks as he might have died while moving above.
                    if (!Combat.isUnderAttack() && !draugen[0].isInCombat() && Player.getAnimation() == -1 && (safeTile1.equals(Player.getPosition()) || safeTile2.equals(Player.getPosition()))) { // if we're not under attack we need to attack him
                        AccurateMouse.click(draugen[0], "Attack");

                        Utils.idle(1500, 6000);
                    }

                    if (draugen[0].getPosition().distanceTo(Player.getPosition()) < 2) { // the draugen is very close to us (likely hitting us), so try moving.
                        General.println("[Debug]: First Safe Tile seemingly failed, using backup");
                        if (safeTile2.isOnScreen() && !Player.getPosition().equals(safeTile2)) {
                            Walking.clickTileMS(safeTile2, "Walk here");
                            Timer.waitCondition(() -> safeTile2.distanceTo(Player.getPosition()) < 2, 3500, 5000);

                            Utils.idle(1000, 2500);
                        } else {
                            Walking.blindWalkTo(safeTile2);
                            Timer.waitCondition(() -> safeTile2.distanceTo(Player.getPosition()) < 2, 3500, 5000);
                            Utils.idle(1000, 2500);
                        }
                    }
                }
            }
            Prayer.disable(Prayer.PRAYERS.PROTECT_FROM_MELEE);
        }
    }


    public void attackDraugenSouthEast() {
        if (Skills.getCurrentLevel(Skills.SKILLS.PRAYER) >= 43) {
            SHOULD_PRAY = true;
        }

        RSTile safeTile2 = new RSTile(2617, 3638);

        cQuesterV2.status = "Attacking Draugen.";
        while (NPCs.find("The Draugen").length > 0) {
            General.sleep(100);
            if (SHOULD_PRAY && Prayer.getPrayerPoints() > 0)
                Prayer.enable(Prayer.PRAYERS.PROTECT_FROM_MELEE);

            if (Combat.getHPRatio() < General.random(40, 65) && Inventory.find(
                    ItemId.LOBSTER).length > 0)  // low health, eat food
                AccurateMouse.click(Inventory.find(
                        ItemId.LOBSTER)[0], "Eat");

            if (SE_SAFE_TILE.isClickable() && !SE_SAFE_TILE.equals(Player.getPosition())) { // we are not standing on the safetile, so we move there
                PathingUtil.clickScreenWalk(SE_SAFE_TILE);

            } else if (!SE_SAFE_TILE.isClickable() && !SE_SAFE_TILE.equals(Player.getPosition())) {
                General.println("[Debug]: Safetile 1 is not clickable and we're not standing on it");
                PathingUtil.walkToTile(SE_SAFE_TILE, 1, false);
            }

            draugen = NPCs.find("The Draugen");
            if (draugen.length > 0) {
                if (!Combat.isUnderAttack() && !draugen[0].isInCombat()) { // if we're not under attack we need to attack him
                    if (AccurateMouse.click(draugen[0], "Attack"))
                        Timer.waitCondition(() -> draugen[0].isInCombat() || Combat.isUnderAttack(), 6000, 8700);

                } else if (draugen[0].getPosition().distanceTo(Player.getPosition()) < 2) { // the draugen is very close to us (likely hitting us), so try moving.
                    General.println("[Debug]: First Safe Tile seemingly failed, using backup");

                    if (safeTile2.isOnScreen() && !Player.getPosition().equals(safeTile2)) {
                        PathingUtil.clickScreenWalk(safeTile2);
                        Timer.waitCondition(() -> safeTile2.distanceTo(Player.getPosition()) < 2, 3500, 5000);
                        Utils.idle(1000, 2500);

                    } else {
                        Walking.blindWalkTo(safeTile2);
                        Timer.waitCondition(() -> safeTile2.distanceTo(Player.getPosition()) < 2, 3500, 5000);
                        Utils.idle(1000, 2500);
                    }
                }
            }
        }
        Prayer.disable(Prayer.PRAYERS.PROTECT_FROM_MELEE);
    }


    public void finishSigli() {
        cQuesterV2.status = "Finishing Sigli.";
        if (Inventory.find(3696).length > 0) {
            gotoSigli();
            if (NpcChat.talkToNPC("Sigli the Huntsman")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                SIGLI = true;
            }
        }
    }


    /******************************
     ****** GENERAL METHODS ******
     *****************************/

    /**
     * Searches quest guide for a string.
     *
     * @param text to be searched for in the Quest Guide
     * @return true if text is present
     */
    public boolean checkInterfacesText(String text) {
        if (Interfaces.get(119, 16) != null) {
            eight = Interfaces.get(119, 8).getText();
            //    nine = Interfaces.get(119, 9).getText();
            ten = Interfaces.get(119, 10).getText();
            eleven = Interfaces.get(119, 11).getText();
            twelve = Interfaces.get(119, 12).getText();
            thirteen = Interfaces.get(119, 13).getText();
            fourteen = Interfaces.get(119, 14).getText();
            fifteen = Interfaces.get(119, 15).getText();
            sixteen = Interfaces.get(119, 16).getText();
            seventeen = Interfaces.get(119, 17).getText();
            eighteen = Interfaces.get(119, 18).getText();
            nineteen = Interfaces.get(119, 19).getText();
            if (eight.contains(text) //|| nine.contains(text)
                    || ten.contains(text) || eleven.contains(text)
                    || twelve.contains(text) || thirteen.contains(text) || fourteen.contains(text) || fifteen.contains(text)
                    || sixteen.contains(text) || seventeen.contains(text) || eighteen.contains(text) || nineteen.contains(text)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }


    public void setAutoCast() {
        if (Equipment.find(staffOfFire).length > 0) {
            if (!Autocast.isAutocastEnabled(Autocast.FIRE_STRIKE)) {
                Autocast.enableAutocast(Autocast.FIRE_STRIKE);
            }
        }
    }

    public boolean isQuestNameVisible(String questName) {
        RSInterface questBox = Interfaces.get(399, 5);
        RSInterface questListParent = Interfaces.get(399, 7);

        if (questBox != null && questListParent != null) {
            RSInterface[] quests = questListParent.getChildren();
            RSInterface questInter = null;
            if (quests != null) {
                for (RSInterface q : quests) {
                    String name = q.getComponentName();

                    if (name != null) {
                        String stripped = General.stripFormatting(name);

                        if (stripped.toLowerCase().contains(questName.toLowerCase())) {
                            return questBox.getAbsoluteBounds().contains(q.getAbsolutePosition());
                        }

                    }
                }
            }
        }
        return false;
    }

    public void openQuestGuide() {
        if (GameTab.getOpen() != GameTab.TABS.QUESTS) {
            GameTab.open(GameTab.TABS.QUESTS);
            Timer.waitCondition(() -> GameTab.getOpen() == GameTab.TABS.QUESTS, 2550, 3200);
        }
        if (GameTab.getOpen() == GameTab.TABS.QUESTS) {
            if (InterfaceUtil.clickInterfaceAction(629, "Quest List"))
                Timer.waitCondition(() -> Interfaces.isInterfaceSubstantiated(399, 2), 3000, 5000);

            RSInterface questInter = Interfaces.get(399, 2);
            if (questInter != null) {
                Rectangle rec = questInter.getAbsoluteBounds();
                for (int i = 0; i < 3; i++) {
                    if (isQuestNameVisible("Fremennik trials")) {
                        General.println("[Debug]: Quest is visible");
                        break;
                    }
                    // scroll so that the quest name is visible to click
                    RSInterface scrollInter = Interfaces.get(399, 4, 1);
                    int pos = General.random(75, 80);
                    while (scrollInter != null && scrollInter.getY() < pos) { //was 80-85
                        General.sleep(100, 300);
                        if (!rec.contains(Mouse.getPos()))
                            Mouse.moveBox(rec);

                        Mouse.scroll(false);
                        scrollInter = Interfaces.get(399, 4, 1);
                    }
                    while (scrollInter != null && scrollInter.getY() > pos) {//was 80-85
                        if (!rec.contains(Mouse.getPos()))
                            Mouse.moveBox(rec);
                        General.sleep(100, 300);
                        Mouse.scroll(true);
                        scrollInter = Interfaces.get(399, 4, 1);
                    }

                }
            }
        }
        General.sleep(General.random(300, 800));
        if (Interfaces.isInterfaceSubstantiated(399, 7, 37)) {
            for (int i = 0; i < 3; i++) {

                RSInterface questName = Interfaces.get(399, 7, 37);
                if (questName != null && questName.click())// clicks quest
                    Timer.waitCondition(() -> Interfaces.isInterfaceSubstantiated(Interfaces.get(119, 15)), 5000, 8000);

                if (Interfaces.isInterfaceSubstantiated(Interfaces.get(119, 15))) {
                    break;
                } else {
                    Mouse.moveBox(Interfaces.get(399, 2).getAbsoluteBounds());
                    if (!Interfaces.get(399, 2).getAbsoluteBounds().contains(Mouse.getPos())) {
                        Mouse.moveBox(Interfaces.get(399, 2).getAbsoluteBounds());

                    }
                    if (Interfaces.get(399, 4, 1).getY() < General.random(80, 85))
                        Mouse.scroll(false);
                    if (Interfaces.get(399, 4, 1).getY() > General.random(80, 85)) {
                        General.sleep(General.random(200, 500));
                        Mouse.scroll(true);
                    }
                }
            }
        }
        General.println("[Debug]: Checking Quest status");
        General.sleep(General.random(500, 2000));
    }

    public void checkSigmundStatus() {
        if (checkInterfacesText("rare flower")) {
            SIGMUNDPART = true;
            General.println("[Debug]: Already talked to Sigmund");
        }
        if (checkInterfacesText("tax reduction")) {
            SIGMUNDPART = true;
            SAILOR = true;
            OLAF = true;
            YRSA = true;
            General.println("[Debug]: Already talked to Yrsa");
        }
        if (checkInterfacesText("bard")) {
            SIGMUNDPART = true;
            SAILOR = true;
            OLAF_SIGMUND = true;
            General.println("[Debug]: Already talked to Olaf");
        }
        if (checkInterfacesText("chieftain")) {
            SIGMUNDPART = true;
            SAILOR = true;
            OLAF_SIGMUND = true;
            YRSA = true;
            BRUNDT = true;
            General.println("[Debug]: Already talked to Brundy");
        }
        if (checkInterfacesText("hunter")) {
            SIGMUNDPART = true;
            SAILOR = true;
            OLAF_SIGMUND = true;
            YRSA = true;
            BRUNDT = true;
            SIGLI_SIGMUND = true;
            General.println("[Debug]: Already talked to Sigli");
        }
        if (checkInterfacesText("armourer")) {
            SIGMUNDPART = true;
            SAILOR = true;
            OLAF_SIGMUND = true;
            YRSA = true;
            BRUNDT = true;
            SIGLI_SIGMUND = true;
            SLUIRIMEN = true;
            General.println("[Debug]: Already talked to skulrimen");
        }
        if (checkInterfacesText("fisherman")) {
            SIGMUNDPART = true;
            SAILOR = true;
            OLAF_SIGMUND = true;
            YRSA = true;
            BRUNDT = true;
            SIGLI_SIGMUND = true;
            SLUIRIMEN = true;
            FISHERMAN = true;
            General.println("[Debug]: Already talked to fisherman");
        }
        if (checkInterfacesText("navigator")) {
            SIGMUNDPART = true;
            SAILOR = true;
            OLAF_SIGMUND = true;
            YRSA = true;
            BRUNDT = true;
            SIGLI_SIGMUND = true;
            SLUIRIMEN = true;
            FISHERMAN = true;
            SWENSEN_SIGMUND = true;
            General.println("[Debug]: Already talked to Swensen");
        }
        if (checkInterfacesText("seer")) {
            SIGMUNDPART = true;
            SAILOR = true;
            OLAF_SIGMUND = true;
            YRSA = true;
            BRUNDT = true;
            SIGLI_SIGMUND = true;
            SLUIRIMEN = true;
            FISHERMAN = true;
            SWENSEN_SIGMUND = true;
            PEER = true;
            General.println("[Debug]: Already talked to Peer the Seer");
        }
        if (checkInterfacesText("warrior")) {
            SIGMUNDPART = true;
            SAILOR = true;
            OLAF_SIGMUND = true;
            YRSA = true;
            BRUNDT = true;
            SIGLI_SIGMUND = true;
            SLUIRIMEN = true;
            FISHERMAN = true;
            SWENSEN_SIGMUND = true;
            PEER = true;
            THORVALD_SIGMUND = true;
            General.println("[Debug]: Already talked to Thorvald");
        }
        if (checkInterfacesText("reveller")) {
            SIGMUNDPART = true;
            SAILOR = true;
            OLAF_SIGMUND = true;
            YRSA = true;
            BRUNDT = true;
            SIGLI_SIGMUND = true;
            SLUIRIMEN = true;
            FISHERMAN = true;
            SWENSEN_SIGMUND = true;
            PEER = true;
            THORVALD_SIGMUND = true;
            MANNI_SIGMUND = true;
            General.println("[Debug]: Already talked to Manni");
        }
        if (checkInterfacesText("money")) {
            SIGMUNDPART = true;
            SAILOR = true;
            OLAF_SIGMUND = true;
            YRSA = true;
            BRUNDT = true;
            SIGLI_SIGMUND = true;
            SLUIRIMEN = true;
            FISHERMAN = true;
            SWENSEN_SIGMUND = true;
            PEER = true;
            THORVALD_SIGMUND = true;
            MANNI_SIGMUND = true;
            THORA = true;
            General.println("[Debug]: Already talked to Swensen");
        }
        if (Interfaces.get(119, 180) != null) {
            if (Interfaces.get(119, 180).click())
                Timer.waitCondition(() -> Interfaces.get(119, 180) == null, 2000, 4000);
        }
    }

    public void checkQuestStatus() {
        Widgets.closeAll();
        General.sleep(300, 800);

        cQuesterV2.status = "Checking Status";
        openQuestGuide();
        Timer.waitCondition(() -> Interfaces.get(119) != null, 2500, 3500);
        if (checkInterfacesText("I now have the Reveller's vote")) {
            REVELLER = true;
            General.println("[Debug]: I now have the Reveller's vote.");
        }
        if (checkInterfacesText("I now have the Bard's vote")) {
            General.println("[Debug]: I now have the Bard's vote");
            OLAF = true;
        }
        if (checkInterfacesText("I now have the Merchant's vote")) {
            General.println("[Debug]: I now have the Merchant's vote");
            SIGMUND = true;
        }
        if (checkInterfacesText("I now have the Navigator's vote")) {
            General.println("[Debug]: I now have the Navigator's vote");
            SWENSEN = true;
        }
        if (checkInterfacesText("I now have the Hunter's vote")) {
            General.println("[Debug]: I now have the Hunter's vote");
            SIGLI = true;
        }
        if (checkInterfacesText("I now have the Seer's vote")) {
            General.println("[Debug]: I now have the Seer's vote");
            SEER = true;
        }
        if (checkInterfacesText("I now have the Warrior's vote")) {
            General.println("[Debug]: I now have the Warrior's vote");
            THORVALD = true;
        }

        if (Interfaces.isInterfaceSubstantiated(119, 205)) {
            Interfaces.get(119, 205).click();
        }
    }

    public void inventoryItemCheck2(int item1, int quantity1) {
        General.println("[Debug]: Checking inventory");
        General.sleep(General.random(200, 500));
        if (Inventory.find(item1).length < 1) {
            General.println("[Debug]: Missing: " + item1);
            BankManager.withdraw(quantity1, item1);
            hasItem1 = false;
        }
    }


    public boolean locate = true;
    public boolean checkQuestStatus = true;

    @Override
    public void execute() {

        General.println("[Debug]: Game Settings 347 = " + Game.getSetting(347));
        General.sleep(100);

        if (Game.getSetting(347) > 0 && checkQuestStatus) {
            checkQuestStatus();
            checkSigmundStatus();
            checkQuestStatus = false;
        } else if (Game.getSetting(347) == 0) {
            buyItems();
            getItemsPart1();
            startQuest();
        }
        if (Game.getSetting(347) == 1) {
            talkToManni1();
            getBooze();
            loseDrinkingContest();
            getLowAlcholKeg();
            handleCouncilWorkman();
            plantBomb();
            switchBeer();
            talkToManni2();
            ;
        } else if (Game.getSetting(347) == 2) {
            killLanzig();
            getOlafItems();
            startOlaf();
            enchantLyre();
            playLyre();

        } else if (Game.getSetting(347) == 3) { // cahnge back to 3
            sigmundPart1();
            Utils.longSleep();
            sigmundPart2();
        } else if (Game.getSetting(347) == 4) {
            startSwensen();
            swensenRoom1();
            swensenRoom2();
            swensenRoom3();
            swensenRoom4();
            swensenRoom5();
            swensenRoom6();
            swensenRoom7();

        } else if (Game.getSetting(347) == 5) {
            getSigliItems();
            startSigli();
            setAutoCast();
            while (Inventory.find(3696).length < 1) {
                General.sleep(1000);
                locateDraugen();
                worldHop();
                locate = false;
                attackDraugen();
                /**
                 * need to code a way to detect where player is, then pick the safespot and go there, then call the right
                 * attack draugen method (maybe use a switch staement)
                 *
                 */
                //  monitorTalisman();
                // attackDraugen();
            }
            finishSigli();


        } else if (Game.getSetting(347) == 6) {
            if (!SEER_BOTTOM_LEFT_ROOM.contains(Player.getPosition())
                    && !PEER_UPSTAIRS.contains(Player.getPosition()) && !SEER_BOTTOM_RIGHT_ROOM.contains(Player.getPosition())) {
                startSeerTrial();
                seerDoor();
                solveDoor();
            }
            seerUpStairsItems();
            doWaterPuzzle();


        } else if (Game.getSetting(347) == 7) {
            getThorvaldItems();
            gotoSkulrimen();
            startThorvald();
            startThorvaldFight();
        } else if (Game.getSetting(347) == 8) {
            finishQuest();
        } else if (Game.getSetting(347) == 10) {
            cQuesterV2.taskList.remove(this);

        }
    }

    private static FremTrials quest;

    public static FremTrials get() {
        return quest == null ? quest = new FremTrials() : quest;
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
        return "Fremennick Trials";
    }

    @Override
    public boolean checkRequirements() {
        return Skills.SKILLS.ATTACK.getActualLevel() >= 30 &&
                Skills.SKILLS.STRENGTH.getActualLevel() >= 30 &&
                Skills.SKILLS.MAGIC.getActualLevel() >= 13;
    }
}