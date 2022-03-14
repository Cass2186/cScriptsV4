package scripts.QuestPackages.Contact;


import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.Combat;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Prayer;
import org.tribot.api2007.types.*;
import org.tribot.script.sdk.*;
import org.tribot.script.sdk.Equipment;
import org.tribot.script.sdk.tasks.Amount;
import org.tribot.script.sdk.tasks.BankTask;
import org.tribot.script.sdk.tasks.EquipmentReq;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestSteps.*;
import scripts.Requirements.*;
import scripts.Tasks.Priority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Contact implements QuestTask {

    private static Contact quest;

    public static Contact get() {
        return quest == null ? quest = new Contact() : quest;
    }

    //Items Required
    ItemReq lightSource, combatGear, parchment, keris, food, prayerPotions, tinderbox;

    // Item recommended
    ItemReq coins, glory;

    RSArea bank = new RSArea(new RSTile(2772, 5129, 0), new RSTile(2758, 5145, 0));
    RSArea dungeon = new RSArea(new RSTile(3263, 9200, 2), new RSTile(3327, 9280, 2));
    RSArea chasm = new RSArea(new RSTile(3216, 9217, 0), new RSTile(3265, 9277, 0));

    AreaRequirement inBank = new AreaRequirement(bank);
    AreaRequirement inDungeon = new AreaRequirement(dungeon);
    AreaRequirement inChasm = new AreaRequirement(chasm);

    VarbitRequirement hasReadParchment = new VarbitRequirement(3274, 50);

    ItemOnTileRequirement kerisNearby;

    NPCStep talkToHighPriest, talkToHighPriest2, talkToJex, talkToMaisa, talkToOsman, talkToOsmanOutsideSoph,

    killGiantScarab, returnToHighPriest;

    ObjectStep goDownToBank, goDownToDungeon, goDownToChasm, searchKaleef, goDownToBankAgain, goDownToDungeonAgain, goDownToChasmAgain;

    GroundItemStep pickUpKeris;

    ClickItemStep readParchment;

     /*
        Map<Integer, QuestStep> steps = new HashMap<>();

        steps.put(0, talkToHighPriest);
        steps.put(10, talkToHighPriest);
        steps.put(20, talkToHighPriest);
        steps.put(30, talkToJex);

        ConditionalStep goInvestigate = new ConditionalStep( goDownToBank);
        goInvestigate.addStep(new Conditions(inChasm, hasReadParchment), talkToMaisa);
        goInvestigate.addStep(parchment, readParchment);
        goInvestigate.addStep(inChasm, searchKaleef);
        goInvestigate.addStep(inDungeon, goDownToChasm);
        goInvestigate.addStep(inBank, goDownToDungeon);

        steps.put(40, goInvestigate);

        steps.put(50, goInvestigate);

        steps.put(60, talkToOsman);

        steps.put(70, talkToOsmanOutsideSoph);

        ConditionalStep scarabBattle = new ConditionalStep( goDownToBankAgain);
        scarabBattle.addStep(inChasm, killGiantScarab);
        scarabBattle.addStep(inDungeon, goDownToChasmAgain);
        scarabBattle.addStep(inBank, goDownToDungeonAgain);

        steps.put(80, scarabBattle);
        steps.put(90, scarabBattle);
        steps.put(100, scarabBattle);

        ConditionalStep finishOff = new ConditionalStep( returnToHighPriest);
        finishOff.addStep(kerisNearby, pickUpKeris);

        steps.put(110, finishOff);
        steps.put(120, finishOff);

        return steps;
    */

    public void setupItemRequirements() {
        lightSource = new ItemReq(ItemID.LIT_CANDLE, 1);
        tinderbox = new ItemReq(ItemID.TINDERBOX, 1);
        parchment = new ItemReq("Parchment", ItemID.PARCHMENT);


        food = new ItemReq(ItemID.SHARK, 10, 1);

        prayerPotions = new ItemReq(ItemID.PRAYER_POTION_4, 4, 1);

        keris = new ItemReq("Keris", ItemID.KERIS);

        coins = new ItemReq("Coins for carpet rides", ItemID.COINS_995);
        glory = new ItemReq(ItemID.AMULET_OF_GLORY[2], 1, 0, true);
    }

    public void setupRSAreas() {

    }

    public void setupConditions() {

        // kerisNearby = new ItemOnTileRequirement(keris);
    }

    int HIGH_PRIEST_ID = 6192;
    int VARBIT = 3274;
    RSArea AFTER_PUSH_WALL_FAILSAFE = new RSArea(
            new RSTile[] {
                    new RSTile(3293, 9242, 2),
                    new RSTile(3293, 9244, 2),
                    new RSTile(3284, 9244, 2),
                    new RSTile(3284, 9236, 2),
                    new RSTile(3291, 9234, 2),
                    new RSTile(3295, 9234, 2),
                    new RSTile(3294, 9236, 2),
                    new RSTile(3293, 9238, 2),
                    new RSTile(3287, 9240, 2)
            }
    );
    ArrayList<RSTile> path = new ArrayList<>(Arrays.asList(
            new RSTile(3318, 9273, 2),
            new RSTile(3318, 9265, 2),
            new RSTile(3314, 9265, 2),
            new RSTile(3314, 9272, 2),
            new RSTile(3306, 9272, 2),
            new RSTile(3306, 9274, 2),
            new RSTile(3296, 9274, 2),
            new RSTile(3296, 9268, 2),
            new RSTile(3299, 9268, 2),
            new RSTile(0, 0, 0),
            new RSTile(3302, 9268, 2),
            new RSTile(3304, 9264, 2),
            new RSTile(3304, 9264, 2),
            new RSTile(3307, 9264, 2),
            new RSTile(3307, 9260, 2),
            new RSTile(3310, 9260, 2),
            new RSTile(3311, 9259, 2),
            new RSTile(3312, 9259, 2),
            new RSTile(3313, 9260, 2),
            new RSTile(3314, 9260, 2),
            new RSTile(3315, 9259, 2),
            new RSTile(3316, 9259, 2),
            new RSTile(3317, 9260, 2),
            new RSTile(3321, 9260, 2),
            new RSTile(3321, 9254, 2),

            new RSTile(3321, 9254, 3),

            new RSTile(3321, 9251, 2),
            new RSTile(3321, 9246, 2),
            new RSTile(3312, 9246, 2),
            new RSTile(3312, 9252, 2),
            new RSTile(3305, 9252, 2),
            new RSTile(3305, 9255, 2),
            new RSTile(3297, 9255, 2),
            new RSTile(3297, 9251, 2),
            new RSTile(3301, 9251, 2),
            new RSTile(3301, 9238, 2),
            new RSTile(3304, 9238, 2),

            new RSTile(3304, 9238, 3),

            new RSTile(3307, 9238, 2),
            new RSTile(3309, 9238, 2),
            new RSTile(3309, 9233, 2),
            new RSTile(3297, 9233, 2),
            new RSTile(3297, 9229, 2),
            new RSTile(3296, 9228, 2),
            new RSTile(3296, 9225, 2),
            new RSTile(3283, 9225, 2),
            new RSTile(3283, 9227, 2),
            new RSTile(3279, 9227, 2),
            new RSTile(3279, 9226, 2),
            new RSTile(3276, 9226, 2),
            new RSTile(3276, 9229, 2),
            new RSTile(3269, 9229, 2)
    ));

    public void setupSteps() {
        talkToHighPriest = new NPCStep(6192, new RSTile(3281, 2772, 0));
        talkToHighPriest.addDialogStep("Sounds like a quest for me; I can't turn that down!", "Yes.");
        talkToHighPriest2 = new NPCStep(6192, new RSTile(3281, 2772, 0));
        talkToHighPriest2.addDialogStep("Is there any way into Menaphos from below?");
        talkToJex = new NPCStep(NpcID.JEX, new RSTile(3312, 2799, 0));

        goDownToBank = new ObjectStep(20275, new RSTile(3309, 2798, 0),
                "Climb-down", lightSource);
        goDownToDungeon = new ObjectStep(20340, new RSTile(2766, 5129, 0),
                "Climb-down", lightSource);
        goDownToChasm = new ObjectStep(20287, new RSTile(3268, 9229, 2),
                "Climb-down");

        searchKaleef = new ObjectStep(ObjectID.KALEEFS_BODY, new RSTile(3239, 9244, 0), "Search");
        searchKaleef.setUseLocalNav(true);

        readParchment = new ClickItemStep(parchment.getId(), "Read",
                new RSTile(3239, 9244, 0));

        talkToMaisa = new NPCStep(NpcID.MAISA, new RSTile(3218, 9246, 0));
        talkToMaisa.addDialogStep("Draynor Village");
        talkToMaisa.addDialogStep("Leela.");

        talkToOsman = new NPCStep(6165, new RSTile(3287, 3179, 0));
        talkToOsman.addDialogStep("I want to talk to you about Sophanem.");
        talkToOsman.addDialogStep("It would drive a wedge between the Menaphite cities.");
        talkToOsmanOutsideSoph = new NPCStep(6166, new RSTile(3285, 2812, 0));
        talkToOsmanOutsideSoph.addDialogStep("I know of a secret entrance to the north.");

        goDownToBankAgain = new ObjectStep(20275, new RSTile(3315, 2797, 0),
                "Climb-down", Player.getPosition().distanceTo(new RSTile(3315, 2797, 0)) > 10,
                lightSource, combatGear);
        goDownToDungeonAgain = new ObjectStep(20340, new RSTile(2766, 5130, 0),
                "Climb-down", lightSource);
        goDownToChasmAgain = new ObjectStep(20287, new RSTile(3268, 9229, 2),
                "Climb-down");
        killGiantScarab = new NPCStep(NpcID.GIANT_SCARAB, new RSTile(3231, 9251, 0));
        killGiantScarab.setInteractionString("Attack");

        pickUpKeris = new GroundItemStep(keris.getId());

        returnToHighPriest = new NPCStep(6192, new RSTile(3281, 2772, 0));
    }

    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.SNAKESKIN_BANDANA, 1, 500),
                    new GEItem(ItemID.SNAKESKIN_BOOTS, 1, 300),
                    new GEItem(ItemID.AMULET_OF_GLORY[2], 2, 20),
                    new GEItem(ItemID.RED_DHIDE_BODY, 1, 50),
                    new GEItem(ItemID.RED_DHIDE_CHAPS, 1, 50),
                    new GEItem(ItemID.RED_DHIDE_VAMBRACES, 1, 50),
                    new GEItem(ItemID.RED_DHIDE_SHIELD, 1, 100),
                    new GEItem(ItemID.RUNITE_BOLTS, 500, 15),
                    new GEItem(ItemID.RUNE_CROSSBOW, 1, 25),
                    new GEItem(ItemID.SHARK, 25, 20),
                    new GEItem(ItemID.PRAYER_POTION_4, 5, 15),
                    new GEItem(ItemID.NARDAH_TELEPORT, 6, 30),
                    new GEItem(ItemID.ANTIDOTE_PLUS_PLUS[0], 2, 20),
                    new GEItem(ItemID.STAMINA_POTION[0], 4, 20),
                    new GEItem(ItemID.VARROCK_TELEPORT, 5, 50),
                    new GEItem(ItemID.TINDERBOX, 1, 200),
                    new GEItem(ItemID.CANDLE, 1, 500),
                    new GEItem(ItemID.WATERSKIN[0], 1, 500)
            )
    );

    BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);

    BankTask startInv = BankTask.builder()
            .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.HEAD).item(ItemID.SNAKESKIN_BANDANA, Amount.of(1)))
            .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.NECK).item(ItemID.AMULET_OF_GLORY[2], Amount.of(1)))
            .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.FEET).item(ItemID.SNAKESKIN_BOOTS, Amount.of(1)))
            .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.BODY).item(ItemID.RED_DHIDE_BODY, Amount.of(1)))
            .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.LEGS).item(ItemID.RED_DHIDE_CHAPS, Amount.of(1)))
            .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.HANDS).item(ItemID.RED_DHIDE_VAMBRACES, Amount.of(1)))
            .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.SHIELD).item(ItemID.RED_DHIDE_SHIELD, Amount.of(1)))
            .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.AMMO).item(ItemID.RUNITE_BOLTS, Amount.of(500)))
            .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.CAPE).item(ItemID.AVAS_ACCUMULATOR, Amount.of(1)))
            .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.WEAPON).item(ItemID.RUNE_CROSSBOW, Amount.of(1)))
            .addInvItem(ItemID.SHARK, Amount.of(13))
            .addInvItem(ItemID.PRAYER_POTION_4, Amount.of(5))
            .addInvItem(ItemID.NARDAH_TELEPORT, Amount.fill(2))
            .addInvItem(ItemID.ANTIDOTE_PLUS_PLUS[0], Amount.of(2))
            .addInvItem(ItemID.STAMINA_POTION[0], Amount.of(2))
            .addInvItem(ItemID.VARROCK_TELEPORT, Amount.of(5))
            .addInvItem(ItemID.COINS, Amount.of(2000))
            .addInvItem(ItemID.TINDERBOX, Amount.of(1))
            .addInvItem(ItemID.CANDLE, Amount.of(1))
            .addInvItem(ItemID.WATERSKIN[0], Amount.of(1))
            .build();

    public void getStartInv() {
        if (!startInv.isSatisfied() && Inventory.find(ItemID.LIT_CANDLE).length == 0) {
            cQuesterV2.status = "Buying start items";
            buyStep.buyItems();
            BankManager.open(true);
            BankManager.depositAll(true);
            cQuesterV2.status = "Getting start items";
            startInv.execute();
        }
        if (Inventory.find(ItemID.LIT_CANDLE).length == 0) {
            BankManager.close(true);
            if (Utils.useItemOnItem(ItemID.TINDERBOX, ItemID.CANDLE))
                General.sleep(450, 750);
        }
    }

    RSArea SCARAB_BANK_AREA = new RSArea(new RSTile(2793, 5174, 0), new RSTile(2806, 5158, 0));
    RSArea BEFORE_FIRST_TRAP = new RSArea(new RSTile(3297, 9267, 2), new RSTile(3295, 9268, 2));
    RSArea LAGE_SCARAB_FIGHT_AREA = new RSArea(
            new RSTile[]{
                    new RSTile(3295, 9262, 2),
                    new RSTile(3305, 9262, 2),
                    new RSTile(3306, 9257, 2),
                    new RSTile(3308, 9252, 2),
                    new RSTile(3308, 9250, 2),
                    new RSTile(3293, 9252, 2),
                    new RSTile(3295, 9256, 2)
            }
    );
    // whole area before first trap
    RSArea MAZE_PART_ONE = new RSArea(
            new RSTile[]{
                    new RSTile(3322, 9276, 2),
                    new RSTile(3295, 9276, 2),
                    new RSTile(3295, 9267, 2),
                    new RSTile(3298, 9267, 2),
                    new RSTile(3298, 9270, 2),
                    new RSTile(3307, 9270, 2),
                    new RSTile(3307, 9266, 2),
                    new RSTile(3312, 9266, 2),
                    new RSTile(3312, 9263, 2),
                    new RSTile(3321, 9263, 2)
            }
    );

    RSArea BETWEEN_FIRST_AND_SECOND_TRAP = new RSArea(
            new RSTile[]{
                    new RSTile(3298, 9267, 2),
                    new RSTile(3298, 9269, 2),
                    new RSTile(3307, 9269, 2),
                    new RSTile(3307, 9266, 2),
                    new RSTile(3310, 9266, 2),
                    new RSTile(3310, 9261, 2),
                    new RSTile(3323, 9261, 2),
                    new RSTile(3323, 9253, 2),
                    new RSTile(3321, 9253, 2),
                    new RSTile(3320, 9258, 2),
                    new RSTile(3306, 9258, 2)
            }
    );
    RSArea BETWEEN_SECOND_TRAP_AND_FIGHT_AREA = new RSArea(
            new RSTile[]{
                    new RSTile(3321, 9253, 2),
                    new RSTile(3323, 9253, 2),
                    new RSTile(3323, 9245, 2),
                    new RSTile(3311, 9245, 2),
                    new RSTile(3309, 9250, 2),
                    new RSTile(3304, 9250, 2),
                    new RSTile(3304, 9253, 2),
                    new RSTile(3310, 9253, 2),
                    new RSTile(3310, 9256, 2),
                    new RSTile(3319, 9256, 2),
                    new RSTile(3319, 9253, 2)
            }
    );

    public void checkLightSource() {
        if (!lightSource.check() && Utils.useItemOnItem(ItemID.TINDERBOX, ItemID.CANDLE))
            Timer.waitCondition(() -> lightSource.check(), 1200, 2000);
    }

    RSArea AFTER_SLAYER_AREA = new RSArea(new RSTile(3294, 9256, 2), new RSTile(3301, 9238, 2));
    RSArea AFTER_CRUSHERS = new RSArea(new RSTile(3289, 9243, 2), new RSTile(3284, 9236, 2));
    RSTile BEFORE_LADDER_DOWN = new RSTile(3271, 9229, 2);
    RSArea LADDER_AREA_GOING_DOWN = new RSArea(new RSTile(3267, 9230, 2), new RSTile(3272, 9226, 2));
    RSArea WHOLE_UNDERGROUND_AREA = new RSArea(new RSTile(3326, 9218, 0), new RSTile(3203, 9277, 0));

    public void checkPrayer(boolean on) {
        if (Combat.getHPRatio() < 40) {
            EatUtil.eatFood();
        }
        if (on) {
            if (Prayer.getPrayerPoints() < 10)
                PrayerUtil.drinkPrayerPotion();
            if (Prayer.getPrayerPoints() > 0)
                Prayer.enable(Prayer.PRAYERS.PROTECT_FROM_MAGIC);
        } else {
            Prayer.disable(Prayer.PRAYERS.PROTECT_FROM_MAGIC);
        }
    }

    public void checkPrayer(boolean on, Prayer.PRAYERS prayerType) {
        checkLightSource();

        if (Combat.getHPRatio() < 40)
            EatUtil.eatFood();

        if (on) {
            if (Prayer.getPrayerPoints() < 15)
                PrayerUtil.drinkPrayerPotion();
            if (Prayer.getPrayerPoints() > 0)
                Prayer.enable(prayerType);
        } else {
            Prayer.disable(prayerType);
        }
    }

    public void goToCorpse() {
        goDownToDungeon();
        if (WHOLE_UNDERGROUND_AREA.contains(Player.getPosition())) {
            checkLightSource();
            if (!parchment.check()) {
                cQuesterV2.status = "Searching Kaleef";
                if (Inventory.isFull())
                    EatUtil.eatFood();
                searchKaleef.setUseLocalNav(true);
                searchKaleef.execute();
            }
            if (parchment.check()) {
                cQuesterV2.status = "Reading parchment";
                readParchment.execute();
                Timer.waitCondition(() -> Utils.getVarBitValue(VARBIT) == 50, 3000, 5000);
            }
        }
    }


    public void goDownToDungeon() {
        checkLightSource();

        if (!inBank.check() && !inDungeon.check() && !WHOLE_UNDERGROUND_AREA.contains(Player.getPosition()))
            goDownToBank.execute();

        if (inBank.check()) {
            cQuesterV2.status = "Climbing down ladder";
            General.println("[Debug]: Climbing down ladder");
            goDownToDungeon.execute();
            Timer.waitCondition(() -> Player.getPosition().getPlane() == 2 ||
                    Interfaces.isInterfaceSubstantiated(562), 6000, 9000);

            RSInterface warning = Interfaces.get(562, 17);
            if (warning != null && warning.click()) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                General.sleep(2000, 3000);
            }
        }
        checkLightSource();

        if (inDungeon.check()) {
            checkPrayer(true);

            if (!LAGE_SCARAB_FIGHT_AREA.contains(Player.getPosition())) {
                if (MAZE_PART_ONE.contains(Player.getPosition()) &&
                        PathingUtil.localNavigation(BEFORE_FIRST_TRAP.getRandomTile(), 1)) {
                    General.println("[MoveToArea]: Going to first trap");
                    checkLightSource();
                    for (int i = 0; i < 3; i++) {
                        if (Utils.clickObject("Floor", "Search", false)) {
                            NPCInteraction.waitForConversationWindow();
                            NPCInteraction.handleConversation("Yes, I'll give it a go.");
                            Timer.waitCondition(Player::isMoving, 1500);
                        }
                        if (Player.isMoving()) {
                            Waiting.waitNormal(2000, 300);
                            Log.log("[Debug]: Breaking loop");
                            break;
                        }

                    }
                }
                if (BETWEEN_FIRST_AND_SECOND_TRAP.contains(Player.getPosition()) ||
                        !BEFORE_FIRST_TRAP.contains(Player.getPosition())) {
                    checkLightSource();
                    General.println("[MoveToArea]: Going to second trap");
                    if (PathingUtil.localNavigation(new RSTile(3321, 9255, 2)))
                        PathingUtil.movementIdle();
                    if (Utils.clickObject("Odd markings", "Search", false)) {
                        NPCInteraction.waitForConversationWindow();
                        NPCInteraction.handleConversation("Yes, I'll give it a go.");
                        PathingUtil.movementIdle();
                        checkPrayer(true);
                    }
                }
            }
            checkPrayer(true);
            // might not nedd this area check if it's an else instead of if
            if (BETWEEN_SECOND_TRAP_AND_FIGHT_AREA.contains(Player.getPosition())) {
                checkLightSource();
                RSTile targ = new RSTile(3297, 9251, 2);
                if (PathingUtil.localNavigation(targ))
                    Timer.waitCondition(() -> Player.getPosition().distanceTo(targ) < 4, 8000, 12000);
            }
            if (AFTER_SLAYER_AREA.contains(Player.getPosition())) {
                checkLightSource();
                cQuesterV2.status = "After Slayer area";
                General.println("[MoveToArea]: " +  cQuesterV2.status);
                eatAndDrink();
                RSTile afterCrushers = new RSTile(3285, 9242, 2);
                if (PathingUtil.localNavigation(afterCrushers))
                    Timer.waitCondition(() -> Player.getPosition().distanceTo(afterCrushers) < 3, 8000, 12000);

            }
            if (AFTER_CRUSHERS.contains(Player.getPosition()) || AFTER_PUSH_WALL_FAILSAFE.contains(Player.getPosition())) {
                checkLightSource();
                eatAndDrink();
                cQuesterV2.status = "Going to Ladder";
                General.println("[MoveToArea]: Going to Ladder");
                PathingUtil.localNavigation(BEFORE_LADDER_DOWN, 2);

            }
        }
        if (LADDER_AREA_GOING_DOWN.contains(Player.getPosition())) {
            checkLightSource();
            checkPrayer(false);
            PrayerUtil.drinkPrayerPotion();
            if (Utils.clickObject("Ladder", "Climb-down", false)) {
                Timer.waitCondition(() -> Player.getPosition().getPlane() == 0, 4000, 6000);
                General.sleep(1500, 2000);
            }
        }
    }

    public void talkToMaisa() {
        cQuesterV2.status = "Talking to Maisa";
        talkToMaisa.setUseLocalNav(true);

        if (PathingUtil.localNavigation(new RSTile(3224, 9246, 0)))
            PathingUtil.movementIdle();
        checkLightSource();

        if (NpcChat.talkToNPC("Maisa")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Draynor Village", "Leela.");
        }
    }

    int COMBAT_LAMP = 10586;

    public void eatAndDrink() {
        if (Combat.getHPRatio() < 40)
            EatUtil.eatFood();

        if (Prayer.getPrayerPoints() < 15)
            PrayerUtil.drinkPrayerPotion();

        if (Combat.isUnderAttack()) {

        }
    }

    public void fight() {
        checkLightSource();
        RSObject[] body = Objects.findNearest(30, "Kaleef's body");
        if (body.length > 0) {
            RSTile fightCenter = body[0].getPosition();
            RSArea fightArea = new RSArea(fightCenter.translate(-5, 5), 10);
            if (!fightArea.contains(Player.getPosition()) && PathingUtil.localNavigation(fightCenter)) {
                checkPrayer(true, Prayer.PRAYERS.PROTECT_FROM_MISSILES);
                Timer.waitCondition(() -> Combat.isUnderAttack() || fightArea.contains(Player.getPosition()), 15000, 20000);
            }
            if (Game.isInInstance()) {
                //  Combat.setAutoRetaliate(false);

                /**
                 *
                 *  Need to safespot to the right of the body
                 *
                 */
                checkPrayer(true, Prayer.PRAYERS.PROTECT_FROM_MISSILES);
                Prayer.enable(Prayer.PRAYERS.HAWK_EYE);
                RSItem[] anti = Inventory.find(ItemID.ANTIDOTE_PLUS_PLUS);
                if (anti.length > 0 && MyPlayer.isPoisoned()) {
                    anti[0].click("Drink");
                }

                RSNPC[] targ = NPCs.findNearest(NpcID.GIANT_SCARAB);
                RSNPC[] mage = NPCs.findNearest(799);
                RSNPC[] meleeLocustRider = NPCs.findNearest(800);

                cQuesterV2.status = "Fighing";

                RSTile safeTile = fightCenter.translate(5, 0);
                RSTile lureTile = safeTile.translate(-22, -8);
                if (safeTile.distanceTo(Player.getPosition()) > 1) {
                    if (PathingUtil.clickScreenWalk(safeTile))
                        PathingUtil.movementIdle();
                }
                if (mage.length > 0 && CombatUtil.clickTarget(mage[0]))
                    Timer.waitCondition(() -> !Combat.isUnderAttack() || Prayer.getPrayerPoints() < 12
                            || mage[0].getHealthPercent() == 0 || Combat.getHPRatio() < 40, 30000, 40000);

                eatAndDrink();

                if (targ.length > 0 && !Combat.isUnderAttack() && CombatUtil.clickTarget(targ[0])) {
                    Timer.waitCondition(Combat::isUnderAttack, 2500, 4000);

                    eatAndDrink();
                    if (MyPlayer.isPoisoned()) {
                        RSItem[] anitdote = Inventory.find(ItemID.ANTIDOTE_PLUS_PLUS);
                        if (anitdote.length > 0 && anitdote[0].click("Drink")) {
                            targ[0].click("Attack");
                        }
                    }
                    if (Combat.isUnderAttack()) {
                        Timer.waitCondition(() -> !Combat.isUnderAttack() || Prayer.getPrayerPoints() < 12
                                || targ[0].getHealthPercent() == 0 || Combat.getHPRatio() < 40
                                || NPCs.findNearest(799).length > 0
                                || NPCs.findNearest(800).length > 0, 30000, 40000);
                    }
                    eatAndDrink();
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Contact (" + Utils.getVarBitValue(VARBIT) + ")";
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
    public void execute() {
        MessageListening.addServerMessageListener(message -> {
            //  Log.log("Got a new server message:");
            //Log.log("[MessageListener] " + message);
            if (message.contains("extinguish")) {
                checkLightSource();
            }
        });

        setupConditions();
        setupItemRequirements();
        setupRSAreas();
        setupSteps();

        if (Utils.getVarBitValue(VARBIT) == 0) {
            getStartInv();
            cQuesterV2.status = "Talking to high priest";
            talkToHighPriest.execute();
        } else if (Utils.getVarBitValue(VARBIT) == 10) {
            Utils.cutScene();
            if (NPCInteraction.isConversationWindowUp())
                NPCInteraction.handleConversation();
        } else if (Utils.getVarBitValue(VARBIT) == 20) {
            cQuesterV2.status = "Talking to high priest";
            talkToHighPriest2.execute();
            if(NPCInteraction.isConversationWindowUp()){
                NPCInteraction.handleConversation("Is there any way into Menaphos from below?");
            }

        } else if (Utils.getVarBitValue(VARBIT) == 30) {
            cQuesterV2.status = "Talking to Jex";
            talkToJex.execute();
        } else if (Utils.getVarBitValue(VARBIT) == 40) {
            goToCorpse();
        } else if (Utils.getVarBitValue(VARBIT) == 50) {
            talkToMaisa();
        } else if (Utils.getVarBitValue(VARBIT) == 60) {
            cQuesterV2.status = "Talking to Osman";
            talkToOsman.execute();
            NPCInteraction.waitForConversationWindow();
            if (NPCInteraction.isConversationWindowUp())
                NPCInteraction.handleConversation();
            Utils.cutScene();
        } else if (Utils.getVarBitValue(VARBIT) == 70) {
            cQuesterV2.status = "Talking to Osman: Outside Soph";
            talkToOsmanOutsideSoph.execute();
            if (NPCInteraction.isConversationWindowUp())
                NPCInteraction.handleConversation();
            Utils.cutScene();
        } else if (Utils.getVarBitValue(VARBIT) == 90) {
            cQuesterV2.status = "Going down to dungeon";
            goDownToDungeon();
            Timer.waitCondition(() -> Utils.getVarBitValue(VARBIT) == 100, 3000, 4000);
        } else if (Utils.getVarBitValue(VARBIT) == 100) {
            if (!Game.isInInstance())
                goDownToDungeon();
            Utils.cutScene();
            fight();

        } else if (Utils.getVarBitValue(VARBIT) == 120) {
            if (Prayer.isPrayerEnabled(Prayer.PRAYERS.PROTECT_FROM_MISSILES)) {
                Prayer.disable(Prayer.PRAYERS.PROTECT_FROM_MISSILES, Prayer.PRAYERS.EAGLE_EYE,
                        Prayer.PRAYERS.HAWK_EYE);
            }
            cQuesterV2.status = "Going down to Finish";
            returnToHighPriest.execute();
            cQuesterV2.taskList.remove(this);
        }
        if (NPCInteraction.isConversationWindowUp())
            NPCInteraction.handleConversation();

    }

    @Override
    public String questName() {
        return "Contact!";
    }

    @Override
    public boolean checkRequirements() {
        return true;
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
