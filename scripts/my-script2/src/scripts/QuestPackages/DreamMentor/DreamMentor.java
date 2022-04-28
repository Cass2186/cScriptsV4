package scripts.QuestPackages.DreamMentor;

import dax.walker.utils.AccurateMouse;
import dax.walker.utils.camera.DaxCamera;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.Combat;
import org.tribot.api2007.Equipment;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.*;
import org.tribot.script.sdk.*;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.tasks.Amount;
import org.tribot.script.sdk.tasks.BankTask;
import org.tribot.script.sdk.tasks.EquipmentReq;
import org.tribot.script.sdk.types.InventoryItem;
import org.tribot.script.sdk.types.Npc;
import scripts.GEManager.GEItem;
import scripts.*;
import scripts.QuestPackages.FamilyCrest.FamilyCrest;
import scripts.QuestSteps.*;
import scripts.Requirements.InventoryRequirement;
import scripts.Requirements.ItemReq;
import scripts.Requirements.ItemRequirement;
import scripts.Requirements.Requirement;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class DreamMentor implements QuestTask {
    private static DreamMentor quest;

    public static DreamMentor get() {
        return quest == null ? quest = new DreamMentor() : quest;
    }

    String message;

    public static final int BIRDSEYE_JACK = 6126;
    public static final int ONEIROMANCER = 3835;
    int[] TRIDENT_ARRAY = {ItemID.TRIDENT_OF_THE_SEAS, ItemID.TRIDENT_OF_THE_SEAS_FULL};

    /**
     * AREAS
     */
    RSArea lunarMine = new RSArea(new RSTile(2300, 10313, 2), new RSTile(2370, 10354, 2));

    int ITEM_CHILD = 42;
    int[] AHRIMS_SETUP = {1149, 4712, 4714, 2577, 4151};


    /**
     * STEPS
     */
    ObjectStep goDownToCyrisus = new ObjectStep(14996, "Climb-down",
            new RSTile(2141, 3943, 0), lunarMine.contains(Player.getPosition()));

    //check this action and boolean
    ObjectStep enterCyrisusCave = new ObjectStep(11399, "Crawl-Through",
            new RSTile(2335, 10345, 2), Game.isInInstance());

    NPCStep talkToCyrisus = new NPCStep("Fallen Man",
            new String[]{"Yes", "Yes."});

    // give food
    NPCStep feed4Food = new NPCStep("Fallen Man");

    NPCStep talkToCyrisus2 = new NPCStep("Fallen Man");

    // give food
    NPCStep feed4Food2 = new NPCStep("Fallen Man");

    // 3622 = spirit
    NPCStep talkToCyrisus3 = new NPCStep(3466);

    //  addAlternateNpcs(NpcID.CYRISUS_3467);


    NPCStep feed6Food = new NPCStep(3467, new RSTile(2346, 10360, 2));
    NPCStep talkToCyrisus4 = new NPCStep(3467);

    // addAlternateNpcs(NpcID.CYRISUS_3468);

    //SelectingCombatGear(this);

    NPCStep talkToJack = new NPCStep(BIRDSEYE_JACK, new RSTile(2099, 3919, 0),
            new String[]{"Cyrisus in the mine"});


    NPCStep giveCyrisusGear = new NPCStep(3468,
            new String[]{"Talk about the Armament"});


    NPCStep supportCyrisusToRecovery = new NPCStep("Cyrisus",
            new String[]{
                    "You're looking better now.", "Well, you look and sound more lively.",
                    "Are you looking forward to getting out?", "That's the spirit!",
                    "You seem like a nice guy.", "Just being honest.",
                    "When we get out of here I'll buy you a drink!", "Whatever and wherever you want - my treat.",
                    "I'm very impressed you managed to get into this cave.", "I would have given up personally.",
                    "You'll survive this easily.", "Think of all the places you can visit when you get out!",
                    "Just don't worry.", "Of course.",
                    "What are you going to do when you get out of here?", "That's up to you. You could travel with me!",
                    "It's a good thing you have me to look after you.", "Not that I'm bragging or anything.",
                    "Not long now and you'll be back on your feet!", "On whether you mind me helping you further.",
                    "You're sounding much better.", "If you need anything, just let me know.",
                    "It's quite cosy in here.", "The perfect environment for getting back on your feet!",
                    "You're very safe in this little cave.", "The suqah will never fit through that tunnel.",
                    "Tell me a bit about yourself.", "Fishing!"
            });

    NPCStep talkAfterHelping = new NPCStep("Cyrisus");

    NPCStep talkToOneiromancer = new NPCStep(ONEIROMANCER,
            new RSTile(2151, 3867, 0), new String[]{"Cyrisus."});


    UseItemOnObjectStep fillVialWithWater = new UseItemOnObjectStep(11151, 16705,
            new RSTile(2091, 3921, 0),
            Inventory.find(ItemID.DREAM_VIAL_WATER).length == 1);

    UseItemOnItemStep addGoutweed = new UseItemOnItemStep(ItemID.DREAM_VIAL_WATER, ItemID.GOUTWEED,
            Inventory.find(ItemID.DREAM_VIAL_WATER).length == 0);

    UseItemOnItemStep useHammerOnAstralRune = new UseItemOnItemStep(ItemID.HAMMER, ItemID.ASTRAL_RUNE,
            Inventory.find(ItemID.ASTRAL_RUNE_SHARDS).length == 1);


    UseItemOnItemStep usePestleOnShards = new UseItemOnItemStep(ItemID.ASTRAL_RUNE_SHARDS, ItemID.PESTLE_AND_MORTAR,
            Inventory.find(ItemID.GROUND_ASTRAL_RUNE).length == 1);

    UseItemOnItemStep useGroundAstralOnVial = new UseItemOnItemStep(ItemID.GROUND_ASTRAL_RUNE, ItemID.DREAM_VIAL_HERB,
            Inventory.find(ItemID.DREAM_POTION).length == 1);
    // check action
    UseItemOnObjectStep lightBrazier = new UseItemOnObjectStep(ItemID.TINDERBOX, 17025,
            new RSTile(2073, 3911, 0), Game.isInInstance());
    //    sealOfPassage,tinderbox,combatGear);


    NPCStep talkToCyrisusForDream = new NPCStep(6125, new RSTile(2075, 3912, 0),
            new String[]{"Yes, let's go!"});//combatGear, sealOfPassage);

    //  addAlternateNpcs(NpcID.CYRISUS_3469, NpcID.CYRISUS_3470, NpcID.CYRISUS_3471);

    // killInadaquacy = new NpcStep(this, NpcID.THE_INADEQUACY, new RSTile(1824, 5150, 2), "Kill The Inadequacy.");
    // killEverlasting = new NpcStep(this, NpcID.THE_EVERLASTING, new RSTile(1824, 5150, 2), "Kill The Everlasting. You can safe spot it by the entry book.");
    //  killUntouchable = new NpcStep(this, NpcID.THE_UNTOUCHABLE, new RSTile(1824, 5150, 2), "Kill The Untouchable. You can safe spot it by the entry book.");
    // killIllusive = new NpcStep(this, NpcID.THE_ILLUSIVE, new RSTile(1824, 5150, 2), "Kill The Illusive.");
    NPCStep returnToOneiromancer = new NPCStep(ONEIROMANCER, new RSTile(2151, 3867, 0),
            new String[]{"Cyrisus."});


    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.TINDERBOX, 1, 100),
                    new GEItem(ItemID.PESTLE_AND_MORTAR, 1, 300),
                    new GEItem(ItemID.ASTRAL_RUNE, 1, 40),
                    new GEItem(ItemID.STAMINA_POTION[0], 2, 15),
                    new GEItem(ItemID.LUNAR_ISLE_TELEPORT, 6, 25),
                    new GEItem(ItemID.HAMMER, 1, 100),
                    new GEItem(ItemID.POTATOES10, 1, 100),
                    new GEItem(ItemID.ONIONS10, 1, 100),
                    new GEItem(ItemID.CABBAGES10, 1, 100),
                    new GEItem(ItemID.SHARK, 40, 25),
                    new GEItem(ItemID.AMULET_OF_GLORY[2], 2, 25),
                    new GEItem(ItemID.RING_OF_WEALTH[0], 1, 25),
                    //TODO change combat gear to melee + trident
                    new GEItem(ItemID.DRAGON_PLATELEGS, 1, 15),
                    new GEItem(ItemID.OCCULT_NECKLACE, 1, 15),
                    //   new GEItem(ItemID.TRIDENT_OF_THE_SEAS_FULL, 1, 15),
                    new GEItem(ItemID.RUNE_PLATEBODY, 1, 15),
                    new GEItem(ItemID.RUNE_BOOTS, 1, 45),
                    new GEItem(ItemID.RUNE_FULL_HELM, 1, 15),
                    new GEItem(ItemID.RUNE_KITESHIELD, 1, 25),
                    new GEItem(ItemID.RING_OF_RECOIL, 1, 40),
                    new GEItem(ItemID.SUPER_DEFENCE[0], 1, 40),
                    new GEItem(ItemID.GUTHIX_CLOAK, 1, 200),
                    new GEItem(ItemID.COOKED_KARAMBWAN, 25, 40)

            )
    );

    InventoryRequirement initialItems = new InventoryRequirement(new ArrayList<>(
            Arrays.asList(
                    new ItemReq(ItemID.TINDERBOX, 1),
                    new ItemReq(ItemID.PESTLE_AND_MORTAR, 1),
                    new ItemReq(ItemID.ASTRAL_RUNE, 1),
                    new ItemReq(ItemID.STAMINA_POTION[0], 1, 0),
                    new ItemReq(ItemID.LUNAR_ISLE_TELEPORT, 6, 0),
                    new ItemReq(ItemID.SEAL_OF_PASSAGE, 1),
                    new ItemReq(ItemID.HAMMER, 1),
                    new ItemReq(ItemID.POTATOES10, 1),
                    new ItemReq(ItemID.ONIONS10, 1),
                    new ItemReq(ItemID.CABBAGES10, 1),
                    new ItemReq(ItemID.GOUTWEED, 1),
                    new ItemReq(ItemID.RING_OF_WEALTH[0], 1, 0, true)
            ))
    );

    BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);

    ItemReq tridentReq = new ItemReq(ItemID.TRIDENT_OF_THE_SEAS, 1, true, true);
    InventoryRequirement fightItems;

    public void populateItemRequirements() {
        tridentReq.addAlternateItemID(ItemID.TRIDENT_OF_THE_SEAS_FULL);
        if (Skills.getActualLevel(Skills.SKILLS.RANGED) < 70) {
            fightItems = new InventoryRequirement(new ArrayList<>(
                    Arrays.asList(
                            new ItemReq(ItemID.TINDERBOX, 1),
                            new ItemReq(ItemID.SEAL_OF_PASSAGE, 1),
                            tridentReq,
                            new ItemReq(ItemID.RUNE_BOOTS, 1, true, true),
                            new ItemReq(ItemID.RUNE_KITESHIELD, 1, true, true),
                            new ItemReq(ItemID.RUNE_FULL_HELM, 1, true, true),
                            new ItemReq(ItemID.RUNE_PLATEBODY, 1, true, true),
                            new ItemReq(ItemID.DRAGON_PLATELEGS, 1, true, true),
                            new ItemReq(ItemID.OCCULT_NECKLACE, 1, true, true),
                            new ItemReq(12504, 1, 0),
                            new ItemReq(ItemID.DREAM_POTION, 1, 0),
                            new ItemReq(ItemID.LUNAR_ISLE_TELEPORT, 1, 0),
                            new ItemReq(ItemID.SHARK, 0, 1)

                    )));
        } else {
            fightItems = new InventoryRequirement(new ArrayList<>(
                    Arrays.asList(
                            new ItemReq(ItemID.TINDERBOX, 1),
                            new ItemReq(ItemID.SEAL_OF_PASSAGE, 1),
                            new ItemReq(ItemID.RUNE_KITESHIELD, 1, true, true),
                            new ItemReq(ItemID.OBSIDIAN_HELMET, 1, true, true),
                            new ItemReq(ItemID.RING_OF_RECOIL, 1, 0, true, true),
                            new ItemReq(ItemID.COMBAT_BRACELET0, 1, true, true),
                            new ItemReq(ItemID.OBSIDIAN_PLATELEGS, 1, true, true),
                            new ItemReq(ItemID.OBSIDIAN_PLATEBODY, 1, true, true),
                            tridentReq,
                            // new ItemReq(ItemID.TRIDENT_OF_THE_SEAS_FULL, 1, true, true),
                            // new ItemReq(ItemID.RUNITE_BOLTS, 500, true, true),
                            new ItemReq(ItemID.OCCULT_NECKLACE, 1, true, true),
                            new ItemReq(ItemID.DRAGON_BOOTS, 1, true, true),
                            new ItemReq(ItemID.DREAM_POTION, 1, 0),
                            new ItemReq(ItemID.LUNAR_ISLE_TELEPORT, 1, 0),
                            new ItemReq(ItemID.SUPER_DEFENCE[0], 1, 0),
                            new ItemReq(ItemID.COOKED_KARAMBWAN, 7, 0),
                            new ItemReq(ItemID.SHARK, 0, 1)

                    )));
        }
    }

    BankTask fightTask = BankTask.builder()
            .addEquipmentItem(EquipmentReq.slot(org.tribot.script.sdk.Equipment.Slot.AMMO)
                    .item(ItemID.RUNITE_BOLTS, Amount.fill(100))) // mith arrows
            .addEquipmentItem(EquipmentReq.slot(org.tribot.script.sdk.Equipment.Slot.HEAD)
                    .item(ItemID.OBSIDIAN_HELMET, Amount.fill(100))) // mith arrows
            .addEquipmentItem(EquipmentReq.slot(org.tribot.script.sdk.Equipment.Slot.HANDS)
                    .item(ItemID.BLACK_DHIDE_VAMBRACES, Amount.of(1)))
            .addEquipmentItem(EquipmentReq.slot(org.tribot.script.sdk.Equipment.Slot.BODY)
                    .item(ItemID.OBSIDIAN_PLATEBODY, Amount.of(1)))
            .addEquipmentItem(EquipmentReq.slot(org.tribot.script.sdk.Equipment.Slot.LEGS)
                    .item(ItemID.OBSIDIAN_PLATELEGS, Amount.of(1)))
            .addEquipmentItem(EquipmentReq.slot(org.tribot.script.sdk.Equipment.Slot.WEAPON)
                    .item(ItemID.TRIDENT_OF_THE_SEAS_FULL, Amount.of(1)))
            .addEquipmentItem(EquipmentReq.slot(org.tribot.script.sdk.Equipment.Slot.NECK)
                    .item(ItemID.OCCULT_NECKLACE, Amount.of(1)))
            .addEquipmentItem(EquipmentReq.slot(org.tribot.script.sdk.Equipment.Slot.SHIELD)
                    .item(ItemID.RUNE_KITESHIELD, Amount.of(1)))
            .addEquipmentItem(EquipmentReq.slot(org.tribot.script.sdk.Equipment.Slot.FEET)
                    .item(ItemID.DRAGON_BOOTS, Amount.of(1)))
            .addEquipmentItem(EquipmentReq.slot(org.tribot.script.sdk.Equipment.Slot.CAPE)
                    .item(ItemID.AVAS_ACCUMULATOR, Amount.of(1)))
            .addEquipmentItem(EquipmentReq.slot(org.tribot.script.sdk.Equipment.Slot.RING)
                    .item(ItemID.RING_OF_RECOIL, Amount.of(1)))
            .addInvItem(ItemID.SEAL_OF_PASSAGE, Amount.of(1))
            .addInvItem(ItemID.TINDERBOX, Amount.of(1))
            .addInvItem(ItemID.DREAM_POTION, Amount.of(1))
            .addInvItem(ItemID.LUNAR_ISLE_TELEPORT, Amount.of(1))
            .addInvItem(ItemID.RANGING_POTION4, Amount.of(1))
            .addInvItem(ItemID.COOKED_KARAMBWAN, Amount.of(6))
            .addInvItem(ItemID.SHARK, Amount.fill(6))
            .build();


    public void getFightItems() {
        populateItemRequirements();
        if (!fightItems.check()) {
            cQuesterV2.status = "Getting fight items";
            if (BankManager.open(true)) {
                BankManager.depositEquipment();
            }
            fightItems.withdrawItems();
            ;

            //  fightItems.withdrawItems();
            //    fightTask.execute();
        }
    }

    NPCStep talkToBrundtForSeal = new NPCStep(3926, new RSTile(2658, 3669, 0),
            new String[]{"Ask about a Seal of Passage."});//combatGear, sealOfPassage);

    public void getSealOfPassage() {
        if (RELLEKA_AREA.contains(Player.getPosition())) {
            if (Inventory.find(ItemID.SEAL_OF_PASSAGE).length == 0) {
                General.println("[Debug]: Getting a seal of passage");
                talkToBrundtForSeal.execute();
            }
        }
    }


    RSArea RELLEKA_AREA = new RSArea(
            new RSTile[]{
                    new RSTile(2629, 3681, 0),
                    new RSTile(2651, 3686, 0),
                    new RSTile(2654, 3663, 0),
                    new RSTile(2640, 3664, 0),
                    new RSTile(2640, 3667, 0),
                    new RSTile(2630, 3667, 0)
            }
    );

    public void setUpSteps() {
        // setting radius so we don't use dax in the instance
        talkToCyrisus.setRadius(5);
        talkToCyrisus.setRadius(5);
        talkToCyrisus3.setRadius(5);
        talkToCyrisus4.setRadius(5);

        talkToCyrisus3.addDialogStep("You're looking better now.", "Well, you look and sound more lively.");
        talkToCyrisus3.addDialogStep("Are you looking forward to getting out?", "That's the spirit!");
        talkToCyrisus3.addDialogStep("You seem like a nice guy.", "Just being honest.");
        talkToCyrisus3.addDialogStep("When we get out of here I'll buy you a drink!", "Whatever and wherever you want - my treat.");
        talkToCyrisus3.addDialogStep("I'm very impressed you managed to get into this cave.", "I would have given up personally.");
        talkToCyrisus3.addDialogStep("You'll survive this easily.", "Think of all the places you can visit when you get out!");
        talkToCyrisus3.addDialogStep("Just don't worry.", "Of course.");
        talkToCyrisus3.addDialogStep("What are you going to do when you get out of here?", "That's up to you. You could travel with me!");
        talkToCyrisus3.addDialogStep("It's a good thing you have me to look after you.", "Not that I'm bragging or anything.");
        talkToCyrisus3.addDialogStep("Not long now and you'll be back on your feet!", "On whether you mind me helping you further.");
        talkToCyrisus3.addDialogStep("You're sounding much better.", "If you need anything, just let me know.");
        talkToCyrisus3.addDialogStep("It's quite cosy in here.", "The perfect environment for getting back on your feet!");
        talkToCyrisus3.addDialogStep("You're very safe in this little cave.", "The suqah will never fit through that tunnel.");
        talkToCyrisus3.addDialogStep("Tell me a bit about yourself.", "Fishing!");

        talkToCyrisus4.addDialogStep("You're looking better now.", "Well, you look and sound more lively.");
        talkToCyrisus4.addDialogStep("Are you looking forward to getting out?", "That's the spirit!");
        talkToCyrisus4.addDialogStep("You seem like a nice guy.", "Just being honest.");
        talkToCyrisus4.addDialogStep("When we get out of here I'll buy you a drink!", "Whatever and wherever you want - my treat.");
        talkToCyrisus4.addDialogStep("I'm very impressed you managed to get into this cave.", "I would have given up personally.");
        talkToCyrisus4.addDialogStep("You'll survive this easily.", "Think of all the places you can visit when you get out!");
        talkToCyrisus4.addDialogStep("Just don't worry.", "Of course.");
        talkToCyrisus4.addDialogStep("What are you going to do when you get out of here?", "That's up to you. You could travel with me!");
        talkToCyrisus4.addDialogStep("It's a good thing you have me to look after you.", "Not that I'm bragging or anything.");
        talkToCyrisus4.addDialogStep("Not long now and you'll be back on your feet!", "On whether you mind me helping you further.");
        talkToCyrisus4.addDialogStep("You're sounding much better.", "If you need anything, just let me know.");
        talkToCyrisus4.addDialogStep("It's quite cosy in here.", "The perfect environment for getting back on your feet!");
        talkToCyrisus4.addDialogStep("You're very safe in this little cave.", "The suqah will never fit through that tunnel.");
        talkToCyrisus4.addDialogStep("Tell me a bit about yourself.", "Fishing!");


        talkToCyrisus3.addDialogStep("You're looking better now.", "Well, you look and sound more lively.");
        talkToCyrisus3.addDialogStep("Are you looking forward to getting out?", "That's the spirit!");
        talkToCyrisus3.addDialogStep("You seem like a nice guy.", "Just being honest.");
        talkToCyrisus3.addDialogStep("When we get out of here I'll buy you a drink!", "Whatever and wherever you want - my treat.");
        talkToCyrisus3.addDialogStep("I'm very impressed you managed to get into this cave.", "I would have given up personally.");
        talkToCyrisus3.addDialogStep("You'll survive this easily.", "Think of all the places you can visit when you get out!");
        talkToCyrisus3.addDialogStep("Just don't worry.", "Of course.");
        talkToCyrisus3.addDialogStep("What are you going to do when you get out of here?", "That's up to you. You could travel with me!");
        talkToCyrisus3.addDialogStep("It's a good thing you have me to look after you.", "Not that I'm bragging or anything.");
        talkToCyrisus3.addDialogStep("Not long now and you'll be back on your feet!", "On whether you mind me helping you further.");
        talkToCyrisus3.addDialogStep("You're sounding much better.", "If you need anything, just let me know.");
        talkToCyrisus3.addDialogStep("It's quite cosy in here.", "The perfect environment for getting back on your feet!");
        talkToCyrisus3.addDialogStep("You're very safe in this little cave.", "The suqah will never fit through that tunnel.");
        talkToCyrisus3.addDialogStep("Tell me a bit about yourself.", "Fishing!");

    }

    public void buyAndGetItems() {

        if (!initialItems.check() && !Game.isInInstance()) {
            scripts.cQuesterV2.status = "Buying items";
            General.println("[Debug]: Buying Items");
            buyStep.buyItems();
            initialItems.withdrawItems();
        }
    }

    public void goToMine() {
        if (!lunarMine.contains(Player.getPosition()) && !Game.isInInstance()) {
            goDownToCyrisus.execute();
            Timer.waitCondition(() -> lunarMine.contains(Player.getPosition()), 7000, 8000);
        }
        if (!Game.isInInstance()) {
            scripts.cQuesterV2.status = "Going in cave";
            enterCyrisusCave.setUseLocalNav(true);
            enterCyrisusCave.setHandleChat(true);
            enterCyrisusCave.execute();
            General.println("[Debug]: Sleeping  until in instance");
            Timer.waitCondition(Game::isInInstance, 10000, 12000);
            General.println("[Debug]: Sleeping 4-5s");
            General.sleep(6000, 7000);
        }
    }

    public void startQuest() {
        if (initialItems.check() && !Game.isInInstance()) {
            scripts.cQuesterV2.status = "Going to start";
            goToMine();
        }
        if (Game.isInInstance()) {
            scripts.cQuesterV2.status = "Talking to fallen man";
            Log.debug("[Debug]: " + scripts.cQuesterV2.status);
            talkToCyrisus.setRadius(20);
            talkToCyrisus.setUseLocalNav(true);
            talkToCyrisus.execute();
            General.sleep(3000, 4000);
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }
    }

    public boolean openFoodSack(String foodName) {
        scripts.cQuesterV2.status = "Removing bagged " + foodName;
        Optional<InventoryItem> food = Query.inventory().nameContains(foodName)
                .nameNotContains("(")
                .findFirst();

        Optional<InventoryItem> baggedFood = Query.inventory()
                .nameContains(foodName)
                .nameContains("(")
                .findFirst();

        int item = baggedFood.map(InventoryItem::getId).orElse(-1);
        if (baggedFood.map(b -> b.click("Remove-one")).orElse(false)) {
            Timer.waitCondition(() -> Inventory.find(item).length == 0, 1750, 2250);
        }

        food = Query.inventory().nameContains(foodName)
                .nameNotContains("(")
                .findFirst();

        return food.isPresent();
    }

    public void closeWindow() {
        RSInterface button = Interfaces.get(521, 13);
        if (button != null && button.click()) {
            Timer.waitCondition(() -> !Interfaces.isInterfaceSubstantiated(521), 2000, 3000);
        }
    }

    public void feedFood(int num, String name) {
        int b = 0;
        for (int i = 0; i < num; i++) {
            scripts.cQuesterV2.status = "Feeding Food";
            if (openFoodSack("Cabbage")) {
                Utils.useItemOnNPC("Cabbage", name);
                Timer.waitCondition(NPCInteraction::isConversationWindowUp, 3000, 4000);
                b++;
            }
            if (b >= num)
                break;

            if (openFoodSack("Potato")) {
                Utils.useItemOnNPC("Potato", name);
                Timer.waitCondition(NPCInteraction::isConversationWindowUp, 3000, 4000);
                b++;
            }

            if (b >= num)
                break;

            if (openFoodSack("Onion")) {
                Utils.useItemOnNPC("Onion", name);
                Timer.waitCondition(NPCInteraction::isConversationWindowUp, 3000, 4000);
                b++;
            }

            if (b >= num)
                break;

        }
        if (ChatScreen.isOpen())
            NPCInteraction.handleConversation();
    }


    public void getCombatGear() {
        if (!Interfaces.isInterfaceSubstantiated(260)) {
            talkToJack.execute();
            Timer.waitCondition(() -> Interfaces.isInterfaceSubstantiated(260), 4000, 5000);
        }
        if (Interfaces.isInterfaceSubstantiated(260)) {
            RSInterfaceChild bigChild = Interfaces.get(260, ITEM_CHILD);
            if (bigChild != null) {
                RSInterfaceComponent[] items = Interfaces.get(260, ITEM_CHILD).getChildren();
                for (RSInterfaceComponent c : items) {
                    for (int i : AHRIMS_SETUP) {
                        if (c.getComponentItem() == i) {
                            General.println("[Debug]: Getting item ID: " + i);
                            if (c.click())
                                General.sleep(500, 1200);
                        }
                    }

                }
            }
            RSInterface close = Interfaces.findWhereAction("Close", 260);
            if (close != null && close.click()) {
                Timer.waitCondition(() -> !Interfaces.isInterfaceSubstantiated(260), 4000, 5000);
            }
        }
    }

    int eatAt = General.random(28, 44);

    public boolean needsToEat() {
        return Combat.getHP() <= eatAt;
    }


    public static boolean eatFood() {
        Predicate<RSItem> food = Filters.Items.actionsEquals("Eat");
        RSItem[] item = Inventory.find(food);
        int b = General.random(1, 5);
        if (item.length > 0 && item[0].click("Eat"))
            return Timer.waitCondition(() -> Player.getAnimation() == 829, 800, 1400);
        return false;
    }

    public void eat() {
        if (needsToEat()) {
            RSItem[] shark = Inventory.find(ItemID.SHARK);
            RSItem[] kara = Inventory.find(ItemID.COOKED_KARAMBWAN);
            int num = shark.length;
            int b = 0;
            if (shark.length > 0 && kara.length > 0 && shark[0].click("Eat")) {
                Waiting.waitNormal(220, 30);
                if (kara[0].click("Eat"))
                    Waiting.waitNormal(220, 30); //reengage
            } else {
                for (int i = 0; i < 5; i++) {
                    General.println("[Debug]: Eat loop");
                    General.sleep(10, 40);
                    if (eatFood()) {
                        b++;
                        General.sleep(250, 420);
                    }
                    if (Inventory.find(ItemID.SHARK).length <= num - 2 || b == 2)
                        break;
                }
            }
            eatAt = General.random(28, 44);
            General.println("[Debug]: Next eating at: " + eatAt);
        }
    }

    public void ddsSpec(int npcId) {
        RSNPC[] npc = NPCs.find(npcId);
        if (npc.length > 0 &&
                org.tribot.script.sdk.Combat.getSpecialAttackPercent() >= 50) {
            if (!Equipment.isEquipped(ItemID.DRAGON_DAGGERP_5698)) {
                Utils.equipItem(ItemID.DRAGON_DAGGERP_5698, "Wield");
            }
            if (!Equipment.isEquipped(ItemID.DRAGON_DAGGERP_5698)) {
                int currentSpec = org.tribot.script.sdk.Combat.getSpecialAttackPercent();
                int num = 1;
                if (currentSpec == 100)
                    num = 2;
                for (int i = 0; i < num; i++) {
                    if (!org.tribot.script.sdk.Combat.isSpecialAttackEnabled())
                        org.tribot.script.sdk.Combat.activateSpecialAttack();

                    if (!npc[0].isClickable())
                        DaxCamera.focus(npc[0]);

                    if (AccurateMouse.click(npc[0], "Attack")) {
                        Timer.waitCondition(() -> org.tribot.script.sdk.Combat.getSpecialAttackPercent() < currentSpec, 5000, 6000);
                    }
                }
            }
        }
    }

    private Optional<Npc> getInteractingInadequacy(int id) {
        return Query.npcs()
                .isInteractingWithMe().idEquals(id)
                .findClosestByPathDistance();
    }

    private Optional<Npc> getIsMyPlayerInteractingWith() {
        return Query.npcs()
                .isMyPlayerInteractingWith()
                .findClosestByPathDistance();
    }


    public void handleFightOne() {
        if (Game.isInInstance()) {

            if (!Combat.isUnderAttack())
                Timer.waitCondition(() -> NPCInteraction.isConversationWindowUp() ||
                        Combat.isUnderAttack(), 4000, 6000);

            if (NPCInteraction.isConversationWindowUp()) {
                NPCInteraction.handleConversation();
                return;
            }
            Optional<Npc> interactingWithMe = getInteractingInadequacy(3473);
            if (interactingWithMe.isEmpty()){
                return;
            }

            Optional<Npc> imInteractingWith = getIsMyPlayerInteractingWith();
            if (imInteractingWith.map(i-> i.getId() != 3473).orElse(false)){
                //clikc the right NPC if we're interacting with wrong one
            }
            RSNPC[] inadeq = NPCs.find(3473);
            if (inadeq.length > 0) {
                eat();
                if (!inadeq[0].isInCombat()) {
                    if (inadeq[0].isClickable())
                        DaxCamera.focus(inadeq[0]);
                    General.println("[Debug]: Attacking NPC");
                    eat();

                    if (Equipment.isEquipped(ItemID.DRAGON_DAGGERP_5698))
                        Utils.equipItem(TRIDENT_ARRAY[0], "Wield");

                    if (AccurateMouse.click(inadeq[0], "Attack")) {
                        Timer.waitCondition(() -> inadeq[0].isInCombat(), 2000, 3000);
                    }
                }
                Timer.waitCondition(() -> !inadeq[0].isInCombat() || needsToEat(), 8000, 12000);
                eat();
            }
        }

    }

    public void handleFightTwo() {
        if (Game.isInInstance()) {
            if (NPCInteraction.isConversationWindowUp()) {
                NPCInteraction.handleConversation();
                return;
            }
            RSNPC[] npcTwo = NPCs.find(3474);
            RSNPC[] npc = NPCs.find(3474, 3475);
            if (npc.length > 0) {

                RSObject[] book = Objects.findNearest(30, "Our lives");
                if (book.length > 0) {
                    eat();
                    if (npcTwo.length > 0 && npcTwo[0].getHealthPercent() == 0) {
                        General.println("[Debug]: NPC 2 is dead, walking out from safe spot momentarily");
                        RSTile walkOutTile = book[0].getPosition().translate(0, 3);
                        if (!walkOutTile.isClickable())
                            DaxCamera.focus(walkOutTile);

                        if (DynamicClicking.clickRSTile(walkOutTile, "Walk here")) {
                            Waiting.waitNormal(700, 50);
                        }
                        Timer.waitCondition(() -> NPCs.find(3475).length > 0, 3500, 4500);
                    }

                    RSTile safeTile = book[0].getPosition().translate(-1, 1);
                    if (!Player.getPosition().equals(safeTile)) {
                        General.println("[Debug]: Going to safe tile");
                        if (!safeTile.isClickable()) {
                            Walking.blindWalkTo(safeTile);
                            DaxCamera.focus(safeTile);
                        }
                        if (DynamicClicking.clickRSTile(safeTile, "Walk here")) {
                            PathingUtil.movementIdle();
                        }
                    }

                }
                eat();
                if (!npc[0].isInCombat()) {
                    if (npc[0].isClickable())
                        DaxCamera.focus(npc[0]);
                    General.println("[Debug]: Attacking NPC");
                    //  ddsSpec(3473);
                    eat();

                    if (Equipment.isEquipped(ItemID.DRAGON_DAGGERP_5698))
                        Utils.equipItem(TRIDENT_ARRAY[0], "Wield");

                    if (AccurateMouse.click(npc[0], "Attack")) {
                        Timer.waitCondition(() -> npc[0].isInCombat(), 2000, 3000);
                    }
                }
                Timer.waitCondition(() -> !npc[0].isInCombat() || needsToEat(), 8000, 12000);
                eat();
            }
        }
    }

    //example
    BankTask bankTaskOne = BankTask.builder()
            .addInvItem(ItemID.TINDERBOX, Amount.of(1))
            .addEquipmentItem(EquipmentReq.slot(org.tribot.script.sdk.Equipment.Slot.WEAPON).item(
                    1329, Amount.of(1)))
            .build();


    public void handleFightFour() {
        if (Game.isInInstance()) {
            if (NPCInteraction.isConversationWindowUp()) {
                NPCInteraction.handleConversation();
                return;
            }
            RSNPC[] inadeq = NPCs.find("The Illusive");
            if (inadeq.length > 0) {
                eat();
                General.println("[Debug]: Attacking form 4");
                if (!inadeq[0].isInCombat()) {
                    if (!inadeq[0].isClickable())
                        DaxCamera.focus(inadeq[0]);

                    eat();

                    if (Equipment.isEquipped(ItemID.DRAGON_DAGGERP_5698))
                        Utils.equipItem(TRIDENT_ARRAY[0], "Wield");
                    int b = General.random(3, 5);
                    for (int i = 0; i < b; i++) {
                        if (AccurateMouse.click(inadeq[0], "Attack")) {
                            General.sleep(750, 2200);
                        }
                        if (inadeq[0].isInCombat())
                            break;
                    }
                }
                General.println("Waiting for it to be out of combat");
                Timer.waitCondition(() -> !inadeq[0].isInCombat() || needsToEat(), 8000, 12000);
                eat();
            }
        }
    }


    int SPIRIT_VARBIT_ID = 3622;
    int HEALTH_VARBIT = 3621;


    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        return cQuesterV2.taskList.get(0).equals(this);
    }

    @Override
    public void execute() {
        Waiting.waitUniform(30, 60);
        getSealOfPassage();
        populateItemRequirements();
        setUpSteps();
        Log.info("Varbit 3618 = " + Utils.getVarBitValue(3618));
        if (Utils.getVarBitValue(3618) == 0) {
            buyAndGetItems();
            startQuest();
        }
        if (Utils.getVarBitValue(3618) == 2) {
            closeWindow();
            if (NPCInteraction.isConversationWindowUp())
                NPCInteraction.handleConversation();
        }
        if (Utils.getVarBitValue(3618) == 4 &&
                Utils.getVarBitValue(HEALTH_VARBIT) == 0) {
            closeWindow();
            feedFood(4, "Fallen Man");
        }
        if (Utils.getVarBitValue(3618) == 4 &&
                Utils.getVarBitValue(HEALTH_VARBIT) < 20) {
            Log.info("3618 =4 & health < 20");
            if (NPCInteraction.isConversationWindowUp())
                NPCInteraction.handleConversation();
        }
        if ((Utils.getVarBitValue(3618) == 6 ||
                Utils.getVarBitValue(3618) == 8) &&
                Utils.getVarBitValue(HEALTH_VARBIT) < 40 &&
                Utils.getVarBitValue(3622) < 32) {
            Log.info("Talking to fallen man");
            talkToCyrisus3.execute();
        }
        if (Utils.getVarBitValue(3618) == 8 &&
                Utils.getVarBitValue(3622) >= 32
                && Utils.getVarBitValue(HEALTH_VARBIT) < 40) {
            Log.info("3618 =8 & health < 40");
            feedFood(6, "Fallen Man");
        }
        if (Utils.getVarBitValue(3618) == 10 &&
                Utils.getVarBitValue(3622) >= 32
                && Utils.getVarBitValue(HEALTH_VARBIT) >= 40) {
            General.println("[Debug]: Talking to Cyrisus");
            talkToCyrisus4.execute();
        }
        if (Utils.getVarBitValue(3618) == 12 &&
                Utils.getVarBitValue(3622) >= 32
                && Utils.getVarBitValue(HEALTH_VARBIT) < 70) {
            General.println("[Debug]: Feeding Cyrisus");
            feedFood(4, "Cyrisus");
        }
        if (Utils.getVarBitValue(3618) == 12 &&
                Utils.getVarBitValue(3622) < 72
                && Utils.getVarBitValue(HEALTH_VARBIT) == 70) {
            General.println("[Debug]: Talking to Cyrisus (4)");
            talkToCyrisus4.execute();
        }
        if (Utils.getVarBitValue(3618) == 14 &&
                Utils.getVarBitValue(3622) == 72
                && Utils.getVarBitValue(HEALTH_VARBIT) == 70) {
            General.println("[Debug]: Talking to Cyrisus");
            if (NPCInteraction.isConversationWindowUp())
                NPCInteraction.handleConversation();
        }
        if (Utils.getVarBitValue(3618) == 16
                && Utils.getVarBitValue(3622) == 72
                && Utils.getVarBitValue(3623) == 0
                && Utils.getVarBitValue(HEALTH_VARBIT) == 70) {

            if (Utils.getVarBitValue(3630) < 30) {
                General.println("[Debug]: Talking to Black Eyed Jack");
                getCombatGear();
            } else {
                goToMine();
                if (Game.isInInstance()) {
                    giveCyrisusGear.execute();
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                }

            }
        }
        if (Utils.getVarBitValue(3618) == 16
                && Utils.getVarBitValue(SPIRIT_VARBIT_ID) == 72
                && Utils.getVarBitValue(3623) == 100
                && Utils.getVarBitValue(HEALTH_VARBIT) < 100) {
            General.println("[Debug]: Feeding 6 food");
            feedFood(6, "Cyrisus");
        }
        if (Utils.getVarBitValue(3618) == 16
                && Utils.getVarBitValue(SPIRIT_VARBIT_ID) < 100
                && Utils.getVarBitValue(3623) == 100
                && Utils.getVarBitValue(HEALTH_VARBIT) == 100) {
            General.println("[Debug]: Supporting until recovery");
            supportCyrisusToRecovery.execute();
        }
        if (Utils.getVarBitValue(3618) == 18
                && Utils.getVarBitValue(SPIRIT_VARBIT_ID) == 100
                && Utils.getVarBitValue(3623) == 100
                && Utils.getVarBitValue(HEALTH_VARBIT) == 100) {
            General.println("[Debug]: Supporting until recovery");
            if (NPCInteraction.isConversationWindowUp())
                NPCInteraction.handleConversation();
            else
                talkAfterHelping.execute();
        }
        if (Utils.getVarBitValue(3618) == 20
                && Utils.getVarBitValue(SPIRIT_VARBIT_ID) == 100
                && Utils.getVarBitValue(3623) == 100
                && Utils.getVarBitValue(HEALTH_VARBIT) == 100
                && Utils.getVarBitValue(3634) <= 1) {
            General.println("[Debug]: Going to Oneiromancer");
            talkToOneiromancer.execute();
            NPCInteraction.waitForConversationWindow();
            if (NPCInteraction.isConversationWindowUp())
                NPCInteraction.handleConversation();

        }
        if (Utils.getVarBitValue(3618) == 24
                && Utils.getVarBitValue(SPIRIT_VARBIT_ID) == 100
                && Utils.getVarBitValue(3623) == 100
                && Utils.getVarBitValue(HEALTH_VARBIT) == 100
                && Utils.getVarBitValue(3634) == 4 && Utils.getVarBitValue(2430) == 0) {
            if (Inventory.find(ItemID.DREAM_POTION).length == 0) {
                General.println("[Debug]: Going to fill vial");
                if (Inventory.find(ItemID.DREAM_VIAL_EMPTY).length == 1)
                    fillVialWithWater.useItemOnObject();
                addGoutweed.execute();
                useHammerOnAstralRune.execute();
                usePestleOnShards.execute();
                useGroundAstralOnVial.execute();
            } else {
                General.println("[Debug]: Going light brazer");
                if (!Game.isInInstance()) // && !fightItems.check())
                    getFightItems();

                lightBrazier.useItemOnObject();
                talkToCyrisusForDream.execute();
                Timer.waitCondition(() -> Game.isInInstance(), 7000, 1000);
            }
        }
        if (Utils.getVarBitValue(3618) == 24
                && Utils.getVarBitValue(SPIRIT_VARBIT_ID) == 100
                && Utils.getVarBitValue(3623) == 100
                && Utils.getVarBitValue(HEALTH_VARBIT) == 100
                && Utils.getVarBitValue(3634) == 4
                && Utils.getVarBitValue(2430) == 1) {

            if (!Game.isInInstance()) {
                if (!fightItems.check()) {
                    getFightItems();
                }
                lightBrazier.useItemOnObject();
                General.println("[Debug]: Going Talk to cyrisus for dream");
                talkToCyrisusForDream.execute();
                Timer.waitCondition(() -> Game.isInInstance(), 7000, 1000);
            }
            if (Game.isInInstance()) {
                if (Utils.getVarBitValue(3633) == 0) {
                    if (!Combat.isUnderAttack() && NPCInteraction.isConversationWindowUp()) {
                        for (int i = 0; i < 6; i++) {
                            NPCInteraction.handleConversation();
                            Timer.waitCondition(() -> NPCInteraction.isConversationWindowUp() || Combat.isUnderAttack(), 5000, 6000);
                            if (Combat.isUnderAttack())
                                break;
                        }
                    }
                }
            }
            General.println("[Debug]: Fighting");
            handleFightOne();
            handleFightTwo();
            handleFightFour();
        }
        if (Utils.getVarBitValue(3618) == 26) {
            if (Game.isInInstance()) {
                // can also check that NPC 3478 .lenght > 0
                NPCInteraction.handleConversation();
            }
            if (!Game.isInInstance()) {
                returnToOneiromancer.execute();
            }

        }
        if (Utils.getVarBitValue(3618) == 28) {
            cQuesterV2.taskList.remove(this);
        }
    }

    @Override
    public String questName() {
        return "Dream Mentor";
    }

    @Override
    public boolean checkRequirements() {
        return true;
    }

    @Override
    public String toString() {
        return message;
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
        return Quest.DREAM_MENTOR.getState().equals(Quest.State.COMPLETE);
    }
}
