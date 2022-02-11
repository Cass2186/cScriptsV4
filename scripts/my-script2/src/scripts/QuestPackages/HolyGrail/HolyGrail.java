package scripts.QuestPackages.HolyGrail;

import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.types.*;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.Npc;
import scripts.*;
import scripts.EntitySelector.Entities;
import scripts.EntitySelector.finders.prefabs.ObjectEntity;
import scripts.GEManager.GEItem;
import scripts.QuestSteps.*;
import scripts.Requirements.*;
import scripts.Tasks.Priority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class HolyGrail implements QuestTask {
    public static final int RUNE_SCIMITAR = 1333;
    public static final int HOLY_TABLE_NAPKIN = 15;
    public static final int MAGIC_WHISTLE = 16;
    public static final int GRAIL_BELL = 17;
    public static final int MAGIC_GOLD_FEATHER = 18;
    public static final int HOLY_GRAIL = 19;
    public static final int WHITE_COG = 20;
    public static final int BLACK_COG = 21;
    public static final int BLUE_COG = 22;
    public static final int RED_COG = 23;
    public static final int ANTIPOISON4 = 2446;
    public static final int SUPERANTIPOISON4 = 2448;
    /**
     * NPCID
     */
    public static final int MERLIN = 3529;
    public static final int THE_LADY_OF_THE_LAKE = 3530;
    public static final int KING_ARTHUR = 3531;
    public static final int MONK_OF_ENTRANA = 1165;
    public static final int MONK_OF_ENTRANA_1166 = 1166;
    public static final int MONK_OF_ENTRANA_1167 = 1167;
    public static final int MONK_OF_ENTRANA_1168 = 1168;
    public static final int MONK_OF_ENTRANA_1169 = 1169;
    public static final int MONK_OF_ENTRANA_1170 = 1170;
    public static final int MONK_1171 = 1171;
    public static final int GRAIL_MAIDEN = 4056;
    public static final int SIR_PERCIVAL = 4057;
    public static final int KING_PERCIVAL = 4058;
    public static final int MERLIN_4059 = 4059;
    public static final int PEASANT = 4060;
    public static final int PEASANT_4061 = 4061;
    public static final int HIGH_PRIEST = 4062;
    public static final int CRONE = 4063;
    public static final int GALAHAD = 4064;
    public static final int FISHERMAN_4065 = 4065;
    public static final int THE_FISHER_KING = 4066;
    public static final int BLACK_KNIGHT_TITAN = 4067;
    public static final int MONK_4068 = 4068;
    /**
     * OBJECT ID
     */

    public static final int MYSTERIOUS_STATUE = 21;
    public static final int DOOR_22 = 22;
    public static final int SACKS = 23;
    public static final int DOOR_24 = 24;
    private static HolyGrail quest;
    ItemRequirement excalibur = new ItemRequirement("Excalibur", ItemID.EXCALIBUR);
    ItemRequirement holyTableNapkin = new ItemRequirement("Holy Table Napkin", ItemID.HOLY_TABLE_NAPKIN);
    ItemRequirement twoMagicWhistles = new ItemRequirement("Magic Whistles", ItemID.MAGIC_WHISTLE, 2);
    ItemRequirement threeCamelotTele = new ItemRequirement("Camelot Teleports", ItemID.CAMELOT_TELEPORT, 3);
    ItemRequirement ardyTele = new ItemRequirement("Ardougne Teleport", ItemID.ARDOUGNE_TELEPORT);
    ItemRequirement faladorTele = new ItemRequirement("Falador Teleport", ItemID.FALADOR_TELEPORT);
    ItemRequirement sixtyCoins = new ItemRequirement("Coins", ItemID.COINS_995, 60);
    ItemRequirement antipoison = new ItemRequirement("Antipoison", ItemID.ANTIPOISON4);
    //  ItemRequirement food = new ItemRequirement("Food", ItemCollections.getGoodEatingFood(), -1);
    ItemRequirement combatGear = new ItemRequirement("A weapon and armour", -1, -1);
    ItemRequirement emptyInvSpot = new ItemRequirement("Empty Inventory Spot", -1, 1);
    ItemRequirement goldFeather = new ItemRequirement("Magic gold feather", ItemID.MAGIC_GOLD_FEATHER);
    ItemRequirement grailBell = new ItemRequirement("Grail Bell", ItemID.GRAIL_BELL);
    ItemRequirement oneMagicWhistle = new ItemRequirement("Magic Whistle", ItemID.MAGIC_WHISTLE);
    ItemRequirement grail = new ItemRequirement("Holy Grail", ItemID.HOLY_GRAIL);
    ItemRequirement highlightMagicWhistle1 = new ItemRequirement("Magic Whistle", ItemID.MAGIC_WHISTLE, 2);
    ItemRequirement highlightMagicWhistle2 = new ItemRequirement("Magic Whistle", ItemID.MAGIC_WHISTLE);
    ItemRequirement highlightGrailBell = new ItemRequirement("Grail Bell", ItemID.GRAIL_BELL);
    RSArea camelotGround = new RSArea(new RSTile(2744, 3517, 0), new RSTile(2733, 3483, 0));
    RSArea camelotUpstairsRSArea1 = new RSArea(new RSTile(2768, 3517, 1), new RSTile(2757, 3506, 1));
    RSArea camelotUpstairsRSArea2 = new RSArea(new RSTile(2764, 3517, 1), new RSTile(2748, 3496, 1));
    RSArea merlinRoom = new RSArea(new RSTile(2768, 3505, 1), new RSTile(2765, 3496, 1));
    RSArea entranaBoat = new RSArea(new RSTile(2841, 3332, 0), new RSTile(2823, 3328, 2));
    RSArea entranaIsland = new RSArea(new RSTile(2871, 3393, 0), new RSTile(2800, 3329, 2));
    RSArea galahadHouse = new RSArea(new RSTile(2616, 3480, 0), new RSTile(2609, 3473, 0));
    RSArea draynorManorFront = new RSArea(new RSTile(3116, 3353, 0), new RSTile(3100, 3347, 0));
    RSArea draynorManorBottomFloor = new RSArea(new RSTile(3119, 3373, 0), new RSTile(3097, 3354, 0));
    RSArea draynorManorSecondFloor = new RSArea(new RSTile(3118, 3373, 1), new RSTile(3098, 3354, 1));
    RSArea draynorManorTopFloor = new RSArea(new RSTile(3112, 3370, 2), new RSTile(3104, 3362, 2));
    RSArea magicWhistleRoom = new RSArea(new RSTile(3112, 3361, 2), new RSTile(3104, 3357, 2));
    RSArea teleportLocation = new RSArea(new RSTile(2743, 3237, 0), new RSTile(2740, 3234, 0));
    RSArea fisherKingRealmAfterTitan1 = new RSArea(new RSTile(2791, 4734, 0), new RSTile(2752, 4671, 0));
    RSArea fisherKingRealmAfterTitan2 = new RSArea(new RSTile(2808, 4707, 0), new RSTile(2791, 4688, 0));
    RSArea fisherKingRealmAfterTitan3 = new RSArea(new RSTile(2798, 4710, 0), new RSTile(2781, 4707, 0));
    RSArea grailBellRingLocation = new RSArea(new RSTile(2762, 4694, 0), new RSTile(2761, 4694, 0));
    RSArea fisherKingRealmCastle1BottomFloor = new RSArea(new RSTile(2780, 4692, 0), new RSTile(2756, 4675, 0));
    RSArea fisherKingRealmCastle1SecondFloor = new RSArea(new RSTile(2771, 4692, 1), new RSTile(2756, 4675, 1));
    RSArea fisherKingRealm = new RSArea(new RSTile(2683, 4733, 0), new RSTile(2625, 4672, 0));
    RSArea fisherKingRealmCastle2BottomFloor = new RSArea(new RSTile(2652, 4692, 0), new RSTile(2628, 4675, 0));
    RSArea fisherKingRealmCastle2SecondFloor = new RSArea(new RSTile(2652, 4687, 1), new RSTile(2646, 4681, 1));
    RSArea fisherKingRealmCastle2ThirdFloor = new RSArea(new RSTile(2651, 4686, 2), new RSTile(2647, 4682, 2));
    AreaRequirement inCamelot = new AreaRequirement(camelotGround);
    AreaRequirement inCamelotUpstairs = new AreaRequirement(camelotUpstairsRSArea1, camelotUpstairsRSArea2);
    AreaRequirement inMerlinRoom = new AreaRequirement(merlinRoom);
    //   AreaRequirement   merlinNearby = new NpcCondition(NpcID.MERLIN_4059);
    AreaRequirement onEntrana = new AreaRequirement(
            entranaBoat, entranaIsland);
    AreaRequirement inGalahadHouse = new AreaRequirement(galahadHouse);
    AreaRequirement inDraynorFrontManor = new AreaRequirement(draynorManorFront);
    AreaRequirement inDraynorManorBottomFloor = new AreaRequirement(draynorManorBottomFloor);
    AreaRequirement inDraynorManorSecondFloor = new AreaRequirement(draynorManorSecondFloor);
    AreaRequirement inDraynorManorTopFloor = new AreaRequirement(draynorManorTopFloor);
    AreaRequirement inMagicWhistleRoom = new AreaRequirement(magicWhistleRoom);
    GroundItemStep takeWhistles = new GroundItemStep(ItemID.MAGIC_WHISTLE,
            new RSTile(3107, 3359, 2), holyTableNapkin, inMagicWhistleRoom);
    AreaRequirement inTeleportLocation = new AreaRequirement(teleportLocation);
    //AreaRequirement inFisherKingRealmEntrance = new AreaRequirement(fisherKingRealmEntrance);
    // AreaRequirement titanNearby = new NpcCondition(NpcID.BLACK_KNIGHT_TITAN);
    AreaRequirement inFisherKingRealmAfterTitan = new AreaRequirement(
            fisherKingRealmAfterTitan1,
            fisherKingRealmAfterTitan2,
            fisherKingRealmAfterTitan3);
    AreaRequirement inGrailBellRingLocation = new AreaRequirement(grailBellRingLocation);
    AreaRequirement inFisherKingCastle1BottomFloor = new AreaRequirement(fisherKingRealmCastle1BottomFloor);
    AreaRequirement inFisherKingCastle1SecondFloor = new AreaRequirement(fisherKingRealmCastle1SecondFloor);
    AreaRequirement inFisherKingCastle1 = new AreaRequirement(fisherKingRealmCastle1BottomFloor,
            fisherKingRealmCastle1SecondFloor);
    AreaRequirement inFisherKingRealm = new AreaRequirement(fisherKingRealm);
    AreaRequirement inFisherKingCastle2BottomFloor = new AreaRequirement(fisherKingRealmCastle2BottomFloor);
    AreaRequirement inFisherKingCastle2SecondFloor = new AreaRequirement(fisherKingRealmCastle2SecondFloor);
    AreaRequirement inFisherKingCastle2ThirdFloor = new AreaRequirement(fisherKingRealmCastle2ThirdFloor);
    RSTile upperCastleTile = new RSTile(2762, 4682, 1);
    RSTile holyGrailTile = new RSTile(2649, 4685, 2);


    NPCStep talkToKingArthur1, talkToMerlin, goToEntrana, talkToHighPriest, goToGalahad, talkToGalahad, goToDraynorManor, enterDraynorManor, goUpStairsDraynor1,
            goUpStairsDraynor2, openWhistleDoor, goGetExcalibur, goToTeleportLocation1,
            attackTitan, talkToFisherman, goUpStairsBrokenCastle,
            talkToFisherKing, goToCamelot,
            talkToKingArthur2, goToTeleportLocation2, openFisherKingCastleDoor, goUpNewCastleStairs, goUpNewCastleLadder, talkToKingArthur3;

    ObjectStep goUpStairsCamelot, openMerlinDoor, openSack;
    // ConditionalStep findFisherKing;


    GroundItemStep pickupBell = new GroundItemStep(highlightGrailBell.getId(), new RSTile(2762, 4694, 0));
    ClickItemStep ringBell = new ClickItemStep(highlightGrailBell.getId(),
            "Ring",
            new RSTile(2762, 4694, 0), highlightGrailBell);
    GroundItemStep takeGrail = new GroundItemStep(grail.getId(),
            new RSTile(2649, 4684, 2)
            , goldFeather);
    ClickItemStep blowWhistle2 = new ClickItemStep(highlightMagicWhistle1.getId(), "Blow", highlightMagicWhistle2, goldFeather);
    RSTile teleportLocationPoint = new RSTile(2742, 3236, 0);
    RSArea UPPER_FLOOR_MANOR = new RSArea(new RSTile(3107, 3362, 2), new RSTile(3104, 3366, 2));
    RSArea WHISTLE_ROOM = new RSArea(new RSTile(3112, 3357, 2), new RSTile(3104, 3361, 2));
    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.CAMELOT_TELEPORT, 10, 150),
                    new GEItem(ItemID.GAMES_NECKLACE[0], 1, 50),
                    new GEItem(ItemID.AMULET_OF_GLORY[2], 2, 20),
                    new GEItem(ItemID.STAMINA_POTION[0], 5, 30),
                    new GEItem(ItemID.RUNE_SCIMITAR, 1, 35),
                    new GEItem(ItemID.MONKS_ROBE_TOP, 1, 250),
                    new GEItem(ItemID.MONKS_ROBE_BOTTOM, 1, 250),
                    new GEItem(ItemID.PRAYER_POTION_4, 3, 20),
                    new GEItem(ItemID.LOBSTER, 15, 40),
                    new GEItem(ItemID.FALADOR_TELEPORT, 5, 50),
                    new GEItem(ItemID.SUPER_COMBAT_POTION[0], 2, 20),
                    new GEItem(ItemID.ANTIDOTE_PLUS_PLUS[0], 2, 65),
                    new GEItem(ItemID.STAMINA_POTION[0], 4, 30),
                    new GEItem(ItemID.RING_OF_WEALTH[0], 1, 30),
                    new GEItem(ItemID.SKILLS_NECKLACE[2], 2, 30),
                    new GEItem(ItemID.COMBAT_BRACELET[2], 1, 30)

            )
    );
    InventoryRequirement startInventory = new InventoryRequirement(new ArrayList<>(
            Arrays.asList(
                    new ItemReq(ItemID.CAMELOT_TELEPORT, 5, 1),
                    new ItemReq(ItemID.AMULET_OF_GLORY[2], 1, 0, true, true),
                    new ItemReq(ItemID.GAMES_NECKLACE[0], 1, 0),
                    new ItemReq(ItemID.STAMINA_POTION[0], 2, 1)
            )));
    InventoryRequirement entranaInventory = new InventoryRequirement(new ArrayList<>(
            Arrays.asList(
                    new ItemReq(ItemID.CAMELOT_TELEPORT, 5, 1),
                    new ItemReq(ItemID.ANTIDOTE_PLUS_PLUS[0], 1, 0),
                    new ItemReq(ItemID.COINS_995, 5000, 500),
                    new ItemReq(ItemID.SKILLS_NECKLACE[2], 1, 0),
                    new ItemReq(ItemID.STAMINA_POTION[0], 2, 0)))
    );
    InventoryRequirement fightInventory = new InventoryRequirement(new ArrayList<>(
            Arrays.asList(
                    new ItemReq(ItemID.CAMELOT_TELEPORT, 6, 0),
                    new ItemReq(ItemID.ANTIDOTE_PLUS_PLUS[0], 1, 0),
                    new ItemReq(ItemID.COINS_995, 5000, 500),
                    new ItemReq(ItemID.RUNE_SCIMITAR, 1, 0, true, true),
                    new ItemReq(ItemID.MONKS_ROBE_BOTTOM, 1, 1, true, true),
                    new ItemReq(ItemID.MONKS_ROBE_TOP, 1, 1, true, true),
                    new ItemReq(ItemID.PRAYER_POTION_4, 3, 1),
                    new ItemReq(ItemID.MAGIC_WHISTLE, 2, 2),
                    new ItemReq(ItemID.LOBSTER, 8, 2),
                    new ItemReq(ItemID.EXCALIBUR, 1, 0, true, false),
                    new ItemReq(ItemID.FALADOR_TELEPORT, 2, 0),
                    new ItemReq(ItemID.SUPER_COMBAT_POTION[0], 1, 0),
                    new ItemReq(ItemID.SKILLS_NECKLACE[2], 1, 0),
                    new ItemReq(ItemID.STAMINA_POTION[0], 2, 0)))
    );
    BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);
    RSTile COMBAT_TILE = new RSTile(2795, 4722, 0);

    public static HolyGrail get() {
        return quest == null ? quest = new HolyGrail() : quest;
    }

    public void blowWhistleStep(ClickItemStep step) {
        cQuesterV2.status = "Going to teleport location with whistle";
        if (!inFisherKingRealmAfterTitan.check() && !inFisherKingRealm.check()) {
            PathingUtil.walkToTile(teleportLocationPoint, 2, false);
            cQuesterV2.status = "Blowing whistle";
            step.execute();
            Timer.waitCondition(() -> inFisherKingRealmAfterTitan.check() ||
                    inFisherKingRealm.check(), 4500, 6000);
        }
    }

    public void setupSteps() {
        RSTile kingArthurRSTile = new RSTile(2763, 3513, 0);
        talkToKingArthur1 = new NPCStep(NpcID.KING_ARTHUR, kingArthurRSTile, "Talk to King Arthur in Camelot Castle to start.");
        talkToKingArthur1.addDialogStep("Tell me of this quest.");
        talkToKingArthur1.addDialogStep("I'd enjoy trying that.", "Yes.");
        goUpStairsCamelot = new ObjectStep(26106, new RSTile(2751, 3511, 0),
                "Climb-up", Player.getPosition().getPlane() == 1);
        //   openMerlinDoor = new ObjectStep(24, "Open the door to go to Merlin's room.");
        talkToMerlin = new NPCStep(4059, new RSTile(2767, 3503, 1), "Talk to Merlin");
        talkToMerlin.addDialogStep("Where can I find Sir Galahad?");

        goToEntrana = new NPCStep(NpcID.MONK_OF_ENTRANA_1167, new RSTile(3048, 3235, 0),
                "Talk to a monk of Entrana. Bank all combat gear.");
        talkToHighPriest = new NPCStep(NpcID.HIGH_PRIEST, new RSTile(2851, 3348, 0), "Talk to the High Priest.");
        talkToHighPriest.addDialogStep("Ask about the Holy Grail Quest", "Ok, I will go searching.");

        // goToGalahad = new DetailedQuestStep(new RSTile(2612, 3475, 0), "Travel to Galahad's House. His house is west of McGrubor's Woods.");
        talkToGalahad = new NPCStep(NpcID.GALAHAD, new RSTile(2611, 3475, 0), "Talk to Galahad.");
        talkToGalahad.addDialogStep("I seek an item from the realm of the Fisher King.");


        //    goGetExcalibur = new ItemStep("Go retrieve Excalibur from your bank. If you do not own Excalibur, you can retrieve it from the Lady of the Lake in Taverly for 500 coins.", twoMagicWhistles, excalibur);

        //goToTeleportLocation1 = new DetailedQuestStep(teleportLocationPoint, "Go to the tower on Karamja near gold mine west of Brimhaven.", twoMagicWhistles, excalibur);

        // attackTitan = new NPCStep(NpcID.BLACK_KNIGHT_TITAN, "Kill the Black Knight Titan with Excalibur. ", twoMagicWhistles, excalibur);
        talkToFisherman = new NPCStep(NpcID.FISHERMAN_4065, new RSTile(2798, 4706, 0),
                "Talk to the fisherman by the river. After talking to him walk West to the castle.");
        talkToFisherman.addDialogStep("Any idea how to get into the castle?");

        // goUpStairsBrokenCastle = new ObjectStep(16671, new RSTile(2762, 4681, 0), "Go up the stairs inside of the castle.");
        talkToFisherKing = new NPCStep(NpcID.THE_FISHER_KING, upperCastleTile, "Talk to The Fisher King.");
        talkToFisherKing.addDialogStep("You don't look too well.");

        //  goToCamelot = new DetailedQuestStep(new RSTile(2758, 3486, 0), "Go back to Camelot.");
        talkToKingArthur2 = new NPCStep(NpcID.KING_ARTHUR, kingArthurRSTile);

        openSack = new ObjectStep(ObjectID.SACKS, new RSTile(2961, 3506, 0), "Open",
                twoMagicWhistles);
        openSack.addDialogStep("Come with me, I shall make you a king.");

        //  goToTeleportLocation2 = new DetailedQuestStep(teleportLocationPoint, "Go to the tower on Karamja near gold mine west of Brimhaven.", oneMagicWhistle, goldFeather);

        talkToKingArthur3 = new NPCStep(NpcID.KING_ARTHUR, kingArthurRSTile, grail);
    }

    public void getWhistles() {
        if (holyTableNapkin.check()) {
            if (!WHISTLE_ROOM.contains(Player.getPosition()))
                PathingUtil.walkToArea(UPPER_FLOOR_MANOR, false);

            if (UPPER_FLOOR_MANOR.contains(Player.getPosition()) && !WHISTLE_ROOM.contains(Player.getPosition())) {
                RSObject door = Entities.find(ObjectEntity::new)
                        .tileEquals(new RSTile(3106, 3361, 2))
                        .actionsContains("Open")
                        .getFirstResult();

                if (door != null && Utils.clickObject(door, "Open"))
                    Timer.waitCondition(() -> WHISTLE_ROOM.contains(Player.getPosition()), 5000, 7000);
            }
            for (int i = 0; i < 4; i++) {
                RSItem[] whistles = Inventory.find(ItemID.MAGIC_WHISTLE);
                if (whistles.length > 1)
                    break;
                takeWhistles.pickUpItem();
                Waiting.waitNormal(200, 65);
            }
        }
    }

    private void getItemsStart() {
        cQuesterV2.status = "Getting Start Items";
        buyStep.buyItems();
        if (!startInventory.check()) {
            BankManager.open(true);
            BankManager.depositEquipment();
            startInventory.withdrawItems();
        }
    }

    private void getItemsEntrana() {
        if (!entranaInventory.check()) {
            buyStep.buyItems();
            cQuesterV2.status = "Getting Entrana Items";
            General.println("[Debug]: " + cQuesterV2.status);
            BankManager.open(true);
            BankManager.depositAll(true);
            BankManager.depositEquipment();
            BankManager.checkEquippedGlory();
            BankManager.checkCombatBracelet();
            entranaInventory.withdrawItems();
        }
    }

    private void getItemsBlackKnightFight() {
        if (!inFisherKingRealmAfterTitan.check() && (!twoMagicWhistles.check()
                || !excalibur.check()) && !grailBell.check()) {
            cQuesterV2.status = "Getting Fight Items";
            General.println("[Debug]: " + cQuesterV2.status);
            BankManager.open(true);
            BankManager.depositEquipment();
            BankManager.checkCombatBracelet();
            BankManager.checkEquippedGlory();
            BankManager.depositAll(true);
            fightInventory.withdrawItems();
            BankManager.close(true);
        }
    }

    private void goToBlackKnight() {
        RSArea titanArea = new RSArea(new RSTile(2795, 4722, 0), 40);
        if (!inFisherKingRealmAfterTitan.check() &&
                !titanArea.contains(Player.getPosition()) && !inFisherKingCastle1.check()) {
            cQuesterV2.status = "Going to Black Knight Area";
            General.println("[Debug]: " + cQuesterV2.status);
            ClickItemStep blowWhistle1 = new ClickItemStep(highlightMagicWhistle1.getId(),
                    "Blow", teleportLocationPoint, excalibur);
            blowWhistleStep(blowWhistle1);
            Log.log("After blowing whistle step");
        }
    }

    public void killTitan() {
        if (!Combat.isAutoRetaliateOn())
            Combat.setAutoRetaliate(true);

        RSNPC[] titan = NPCs.findNearest(4067);
        Optional<Npc> ttn = Query.npcs().idEquals(4067).findClosest();
        if (titan.length > 0 && !inFisherKingRealmAfterTitan.check()
                && !inFisherKingCastle1.check() && ttn.isPresent()) {
            if (titan[0].isInCombat() && !titan[0].isInteractingWithMe()) {
                // hop worlds, fighting someone else
            } else if (!titan[0].isInCombat()) {
                if (!titan[0].isClickable()) {
                    PathingUtil.walkToTile(COMBAT_TILE.translate(-2, 0), 1, false);
                    Timer.waitCondition(() -> ttn.get().isVisible(), 4500, 6000);
                }
                if (PrayerUtil.setPrayer(PrayerType.MELEE) && CombatUtil.clickTarget(titan[0]))
                    Timer.waitCondition(() -> ttn.get().isHealthBarVisible(), 3000, 4500);
            }

            RSItem[] combat = Inventory.find(ItemID.SUPER_COMBAT_POTION);
            if (combat.length > 0 &&
                    Skills.getCurrentLevel(Skills.SKILLS.STRENGTH) <= Skills.getActualLevel(Skills.SKILLS.STRENGTH) + 2) {
                //drink super combat if w/in 2 levels of actual
                if (combat[0].click("Drink"))
                    Waiting.waitNormal(250, 75);

            }

            int eatAt = General.random(35, 60);
            int drinkPrayerAt = General.random(5, 25);
            if (ttn.get().isInteractingWithMe() && ttn.get().isHealthBarVisible()) {
                //if (titan[0].isInCombat()) {
                cQuesterV2.status = "Fighting";
                // will also drink p-pots & eat
                if (Timer.waitCondition(() -> {

                    Waiting.waitNormal(200, 75);
                    if (Combat.getHPRatio() < eatAt)
                        EatUtil.eatFood();

                    if (Prayer.getPrayerPoints() < drinkPrayerAt)
                        PrayerUtil.drinkPrayerPotion();

                    if (titan[0].getHealthPercent() < 0.07) {
                        Utils.equipItem(excalibur.getId());
                        ttn.ifPresent(t -> Log.log("Health percent of titan (SDK version) is "
                                + t.getHealthBarPercent()));
                        //
                    }

                    AntiBan.timedActions();

                    return titan[0].getHealthPercent() == 0.00;
                }, 95000))
                    PathingUtil.movementIdle();

            } else {
                Log.log("[Debug]: Fight loop");
            }
        }
        if (!Combat.isUnderAttack() && inFisherKingRealmAfterTitan.check()
                && !inFisherKingCastle1BottomFloor.check() && !inFisherKingCastle1.check()) {
            Prayer.disable(Prayer.PRAYERS.PROTECT_FROM_MELEE);
            cQuesterV2.status = "Going to fisherman";
            talkToFisherman.execute();
            pickupBell.pickUpItem();
            ringBell.execute();
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            Timer.waitCondition(() -> inFisherKingCastle1BottomFloor.check(), 5000, 6000);
        }
        if (inFisherKingCastle1.check()) {
            cQuesterV2.status = "Going to Fisher King";
            talkToFisherKing.execute();
            RSNPC[] king = NPCs.findNearest("Fisher King");
            if (king.length > 0 && PathingUtil.localNavigation(king[0].getPosition())) {
                PathingUtil.movementIdle();
                talkToFisherKing.execute();
            }
        }

    }

    @Override
    public String toString() {
        return "Holy Grail (" + Game.getSetting(5) + ")";
    }

    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return cQuesterV2.taskList.get(0).equals(HolyGrail.get());
    }

    @Override
    public void execute() {
        setupSteps();
        if (Game.getSetting(5) == 0) {
            getItemsStart();
            cQuesterV2.status = "Staring quest";
            talkToKingArthur1.execute();
        }
        if (Game.getSetting(5) == 2) {
            cQuesterV2.status = "Talking to Merlin";
            talkToMerlin.execute();
        }
        if (Game.getSetting(5) == 3) {
            cQuesterV2.status = "Getting Entrana Items";
            getItemsEntrana();
            cQuesterV2.status = "Talking to High priest";
            talkToHighPriest.execute();


        }
        if (Game.getSetting(5) == 4) {
            if (!twoMagicWhistles.check()) {
                if (!holyTableNapkin.check()) {
                    cQuesterV2.status = "Going to Galahad";
                    talkToGalahad.execute();
                }
                cQuesterV2.status = "Getting whistles";
                getWhistles();
            }
            if (twoMagicWhistles.check()) {
                cQuesterV2.status = "Getting items for fight";
                getItemsBlackKnightFight();
                goToBlackKnight();
                killTitan();
            }
        }
        if (Game.getSetting(5) == 7) { // game setting jumps from 4-> 8 or 4-7
            cQuesterV2.status = "Talking To Fisher king";
            killTitan();
        }
        if (Game.getSetting(5) == 8) {
            if (!goldFeather.check()) {
                cQuesterV2.status = "Going to King Arthur";
                talkToKingArthur2.execute();
            }
            cQuesterV2.status = "Going to Goblin village";
            openSack.execute();
            if (NPCInteraction.waitForConversationWindow())
                NPCInteraction.handleConversation("Come with me, I shall make you a king.");
        }
        if (Game.getSetting(5) == 9) {
            cQuesterV2.status = "Going to grail location";
            goToBlackKnight();
            cQuesterV2.status = "Taking Holy Grail";
            takeGrail.pickUpItem();
            cQuesterV2.status = "Going to King Arthur with Grail";
            talkToKingArthur3.execute();
        }
        if (Game.getSetting(5) == 10) {
            cQuesterV2.status = "Done Quest";
            Utils.closeQuestCompletionWindow();
            cQuesterV2.taskList.remove(HolyGrail.get());
        }
    }

    @Override
    public String questName() {
        return "Holy Grail";
    }

    @Override
    public boolean checkRequirements() {

        return Skills.getActualLevel(Skills.SKILLS.ATTACK) >= 40
                && Game.getSetting(Quests.MERLINS_CRYSTAL.getGameSetting()) == 7; //merlin's crystal
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
