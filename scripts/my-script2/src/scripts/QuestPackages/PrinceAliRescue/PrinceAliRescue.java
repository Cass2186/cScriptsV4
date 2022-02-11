package scripts.QuestPackages.PrinceAliRescue;


import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSInterfaceChild;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.GameState;
import org.tribot.script.sdk.Inventory;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Waiting;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.Listeners.InterfaceListener;
import scripts.Listeners.InterfaceObserver;
import scripts.Listeners.InventoryListener;
import scripts.Listeners.InventoryObserver;
import scripts.QuestPackages.KourendFavour.ArceuusLibrary.State;
import scripts.QuestSteps.*;
import scripts.Requirements.*;
import scripts.Requirements.Items.ItemCollections;
import scripts.Requirements.Util.ConditionalStep;
import scripts.Requirements.Util.Conditions;
import scripts.Requirements.Util.LogicType;
import scripts.Tasks.Priority;

import java.util.*;
import java.util.regex.Matcher;

public class PrinceAliRescue implements QuestTask, InterfaceListener {


    private static PrinceAliRescue quest;

    public static PrinceAliRescue get() {
        return quest == null ? quest = new PrinceAliRescue() : quest;
    }

    ItemRequirement softClay, ballsOfWool3, yellowDye, redberries, ashes, bucketOfWater, potOfFlour, bronzeBar, pinkSkirt, beers3, rope, coins100, wig, dyedWig, paste, keyMould, key,
            ropeReqs, yellowDyeReqs, ropeHighlighted;

    //Items Recommended
    ItemRequirement glory;

    Requirement hasOrGivenKeyMould, inCell, givenKeyMould, hasWigPasteAndKey;

    WidgetTextRequirement firstKey, secondKey, thirdKey;

    QuestStep talkToHassan, talkToOsman, talkToNed, talkToAggie, dyeWig, talkToKeli, bringImprintToOsman, talkToLeela, talkToJoe, useRopeOnKeli, useKeyOnDoor, talkToAli, returnToHassan;

    ConditionalStep makeDyedWig, makePaste, makeKeyMould, getKey;

    //Zones
    RSArea cell;


    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.YELLOW_DYE, 1, 500),
                    new GEItem(ItemID.BALL_OF_WOOL, 3, 500),
                    new GEItem(ItemID.SOFT_CLAY, 1, 50),
                    new GEItem(ItemID.REDBERRIES, 1, 500),
                    new GEItem(ItemID.ASHES, 1, 500),
                    new GEItem(ItemID.BUCKET_OF_WATER, 1, 500),
                    new GEItem(ItemID.POT_OF_FLOUR, 1, 500),
                    new GEItem(ItemID.BRONZE_BAR, 1, 500),
                    new GEItem(ItemID.PINK_SKIRT, 1, 500),
                    new GEItem(ItemID.BEER, 3, 500),
                    new GEItem(ItemID.ROPE, 2, 500),
                    new GEItem(ItemID.AMULET_OF_GLORY4, 2, 30),
                    new GEItem(ItemID.STAMINA_POTION[0], 2, 15),
                    new GEItem(ItemID.RING_OF_WEALTH[0], 1, 25)
            )
    );

    InventoryRequirement initialItemReqs = new InventoryRequirement(new ArrayList<>(
            Arrays.asList(
                    new ItemReq(ItemID.LUMBRIDGE_TELEPORT, 4, 1),
                    new ItemReq(ItemID.VARROCK_TELEPORT, 4, 1),
                    new ItemReq(ItemID.NECKLACE_OF_PASSAGE[0], 1, 0, true),
                    new ItemReq(ItemID.STAMINA_POTION[0], 1, 0),
                    new ItemReq(ItemID.RING_OF_WEALTH[0], 1, 0, true)
            )
    ));

    BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);


    public Map<Integer, QuestStep> loadSteps() {
        setupItemRequirements();
        setupZones();
        setupConditions();
        setupSteps();
        Map<Integer, QuestStep> steps = new HashMap<>();

        steps.put(0, talkToHassan);
        steps.put(10, talkToOsman);

        makeDyedWig = new ConditionalStep(talkToNed);
        makeDyedWig.addStep(wig, dyeWig);
        // makeDyedWig.setLockingCondition(dyedWig);

        makePaste = new ConditionalStep(talkToAggie);
        //  makePaste.setLockingCondition(paste);

        makeKeyMould = new ConditionalStep(talkToKeli);
        //  makeKeyMould.setLockingCondition(hasOrGivenKeyMould);

        getKey = new ConditionalStep(bringImprintToOsman);
        // getKey.setLockingCondition(givenKeyMould);

        ConditionalStep prepareToSaveAli = new ConditionalStep(makeDyedWig);
        prepareToSaveAli.addStep(new Conditions(dyedWig, paste, givenKeyMould), talkToLeela);
        prepareToSaveAli.addStep(new Conditions(dyedWig, paste, hasOrGivenKeyMould), getKey);
        prepareToSaveAli.addStep(new Conditions(dyedWig, paste), makeKeyMould);
        prepareToSaveAli.addStep(dyedWig, makePaste);

        steps.put(20, prepareToSaveAli);

        ConditionalStep getJoeDrunk = new ConditionalStep(makeDyedWig);
        getJoeDrunk.addStep(hasWigPasteAndKey, talkToJoe);
        getJoeDrunk.addStep(dyedWig, makePaste);

        steps.put(30, getJoeDrunk);
        steps.put(31, getJoeDrunk);
        steps.put(32, getJoeDrunk);
        steps.put(33, getJoeDrunk);

        ConditionalStep tieUpKeli = new ConditionalStep(makeDyedWig);
        tieUpKeli.addStep(hasWigPasteAndKey, useRopeOnKeli);
        tieUpKeli.addStep(dyedWig, makePaste);
        steps.put(40, tieUpKeli);

        ConditionalStep freeAli = new ConditionalStep(makeDyedWig);
        freeAli.addStep(new Conditions(hasWigPasteAndKey, inCell), talkToAli);
        freeAli.addStep(hasWigPasteAndKey, useKeyOnDoor);
        freeAli.addStep(dyedWig, makePaste);
        steps.put(50, freeAli);

        steps.put(100, returnToHassan);

        return steps;
    }

    public void setupItemRequirements() {
        softClay = new ItemRequirement("Soft clay", ItemID.SOFT_CLAY);
        ballsOfWool3 = new ItemRequirement("Balls of wool", ItemID.BALL_OF_WOOL, 3);
        yellowDye = new ItemRequirement("Yellow dye", ItemID.YELLOW_DYE);

        redberries = new ItemRequirement("Redberries", ItemID.REDBERRIES);
        ashes = new ItemRequirement("Ashes", ItemID.ASHES);
        bucketOfWater = new ItemRequirement("Bucket of water", ItemID.BUCKET_OF_WATER);
        potOfFlour = new ItemRequirement("Pot of flour", ItemID.POT_OF_FLOUR);
        bronzeBar = new ItemRequirement("Bronze bar", ItemID.BRONZE_BAR);
        pinkSkirt = new ItemRequirement("Pink skirt", ItemID.PINK_SKIRT);
        beers3 = new ItemRequirement("Beers", ItemID.BEER, 3);
        rope = new ItemRequirement("Rope", ItemID.ROPE);

        ropeHighlighted = new ItemRequirement("Rope", ItemID.ROPE);

        ropeReqs = new ItemRequirement("Rope, or 15 coins / 4 balls of wool to obtain during the quest", ItemID.ROPE);
        coins100 = new ItemRequirement(ItemCollections.getCoins(), 100);
        wig = new ItemRequirement("Wig", ItemID.WIG_2421);

        dyedWig = new ItemRequirement("Wig (dyed)", ItemID.WIG);
        paste = new ItemRequirement("Paste", ItemID.PASTE);
        keyMould = new ItemRequirement("Key print", ItemID.KEY_PRINT);
        key = new ItemRequirement("Bronze key", ItemID.BRONZE_KEY);

        yellowDyeReqs = new ItemRequirement("Yellow dye, or 2 onions + 5 coins to obtain during quest", ItemID.YELLOW_DYE);
        glory = new ItemRequirement("Amulet of Glory for Al Kharid and Draynor Village teleports", ItemCollections.getAmuletOfGlories());
    }

    public void initialiseListeners() {
        InterfaceObserver interfaceObserver = new InterfaceObserver(() -> true);
        interfaceObserver.addListener(this);
        interfaceObserver.addRSInterfaceChild(119, 3);
        interfaceObserver.addRSInterfaceChild(231, 5);
        interfaceObserver.addRSInterfaceChild(WidgetInfo.DIALOG_NPC_TEXT.getId(),
                WidgetInfo.DIALOG_NPC_TEXT.getChildId());
        interfaceObserver.start();
    }


    public void setupConditions() {
        inCell = new AreaRequirement(cell);
        hasWigPasteAndKey = new Conditions(dyedWig, paste, key);
        firstKey = new WidgetTextRequirement(217, 5,
                true, "I have duplicated a key, I need to get it from");
        secondKey = new WidgetTextRequirement(231, 5, "the key from Leela.");
        thirdKey = new WidgetTextRequirement(217, 5, true,
                "I got a duplicated cell door key");

        givenKeyMould = new Conditions(true, LogicType.OR, firstKey, secondKey, thirdKey,
                key);
        hasOrGivenKeyMould = new Conditions(LogicType.OR, keyMould, givenKeyMould, key);
    }

    public void setupZones() {
        cell = new RSArea(new RSTile(3121, 3240, 0), new RSTile(3125, 3243, 0));
    }

    public void setupSteps() {
        talkToHassan = new NPCStep(NpcID.HASSAN, new RSTile(3298, 3163, 0), "Talk to Hassan in the Al Kharid Palace.");
        talkToHassan.addDialogStep("Can I help you? You must need some help here in the desert.","Yes.");
        talkToOsman = new NPCStep(6165, new RSTile(3286, 3180, 0), "Talk to Osman north of the Al Kharid Palace.");
        talkToOsman.addDialogStep("What is the first thing I must do?");

        talkToNed = new NPCStep(NpcID.NED, new RSTile(3097, 3257, 0), ballsOfWool3);
        talkToNed.addDialogStep("Ned, could you make other things from wool?");
        talkToNed.addDialogStep("How about some sort of a wig?");
        talkToNed.addDialogStep("I have that now. Please, make me a wig.");
        dyeWig = new UseItemOnItemStep(ItemID.YELLOW_DYE, ItemID.WIG_2421,
                !Inventory.contains(ItemID.YELLOW_DYE), yellowDye, wig);
        talkToAggie = new NPCStep(NpcID.AGGIE, new RSTile(3086, 3257, 0),
                redberries, ashes, potOfFlour, bucketOfWater);
        talkToAggie.addDialogStep("Could you think of a way to make skin paste?");
        talkToAggie.addDialogStep("Yes please. Mix me some skin paste.");
        talkToKeli = new NPCStep(NpcID.LADY_KELI, new RSTile(3127, 3244, 0), softClay);
        talkToKeli.addDialogStep("Heard of you? You are famous in Gielinor!");
        talkToKeli.addDialogStep("What is your latest plan then?");
        talkToKeli.addDialogStep("Can you be sure they will not try to get him out?");
        talkToKeli.addDialogStep("Could I see the key please?");
        talkToKeli.addDialogStep("Could I touch the key for a moment please?");
        bringImprintToOsman = new NPCStep(6165, new RSTile(3285, 3179, 0),
                keyMould, bronzeBar);
        talkToLeela = new NPCStep(NpcID.LEELA, new RSTile(3113, 3262, 0), beers3, dyedWig, paste, rope, pinkSkirt);
        talkToJoe = new NPCStep(NpcID.JOE_4275, new RSTile(3124, 3245, 0),
                beers3, key, dyedWig, paste, rope, pinkSkirt);
        talkToJoe.addDialogStep("I have some beer here, fancy one?");
        useRopeOnKeli = new UseItemOnNpcStep(ItemID.ROPE, NpcID.LADY_KELI, new RSTile(3127, 3244, 0),
                "Use rope on Keli.", ropeHighlighted);

        useKeyOnDoor = new ObjectStep(ObjectID.PRISON_DOOR_2881, new RSTile(3123, 3243, 0),
                "Open", inCell.check(), key, dyedWig, paste, pinkSkirt);

        talkToAli = new NPCStep(NpcID.PRINCE_ALI, new RSTile(3123, 3240, 0),
                key, dyedWig, paste, pinkSkirt);

        returnToHassan = new NPCStep(NpcID.HASSAN, new RSTile(3298, 3163, 0), "Return to Hassan in the Al Kharid Palace to complete the quest.");
    }


    public List<ItemRequirement> getItemRequirements() {
        ArrayList<ItemRequirement> reqs = new ArrayList<>();
        reqs.add(softClay);
        reqs.add(ballsOfWool3);
        reqs.add(yellowDye);
        reqs.add(redberries);
        reqs.add(ashes);
        reqs.add(bucketOfWater);
        reqs.add(potOfFlour);
        reqs.add(bronzeBar);
        reqs.add(pinkSkirt);
        reqs.add(beers3);
        reqs.add(rope);
        reqs.add(coins100);
        return reqs;
    }

    public List<ItemRequirement> getItemRecommended() {
        ArrayList<ItemRequirement> reqs = new ArrayList<>();
        reqs.add(glory);
        return reqs;
    }


    public List<String> getCombatRequirements() {
        ArrayList<String> reqs = new ArrayList<>();
        reqs.add("Able to survive jail guards (level 26) attacking you");
        return reqs;
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
        // need a way to initialize without doing it each iteration, trying a for loop here
        initialiseListeners();

        for (int i = 0; i < 100; i++) {

            int gameSetting = QuestVarPlayer.QUEST_PRINCE_ALI_RESCUE.getId();
            Log.debug("Prince Ali Rescue Game setting is " + gameSetting);

            // buy initial items on quest start
            if (GameState.getSetting(gameSetting) == 0) {
                buyStep.buyItems();
            }

            // done quest
            if (GameState.getSetting(gameSetting) == 100) {
                cQuesterV2.taskList.remove(this);
                break;
            }

            //load questSteps into a map
            Map<Integer, QuestStep> steps = loadSteps();
            //get the step based on the game setting key in the map
            Optional<QuestStep> step = Optional.ofNullable(steps.get(GameState.getSetting(gameSetting)));

            // set status
            cQuesterV2.status = step.map(Object::toString).orElse("Unknown Step Name");

            //do the actual step
            step.ifPresent(QuestStep::execute);

            // handle any chats that are failed to be handled by the QuestStep (failsafe)
            if (NPCInteraction.isConversationWindowUp()) {
                Keyboard.typeString(" "); //for aggie step
                NPCInteraction.handleConversation();
            }
            //slow down looping if it gets stuck
            Waiting.waitNormal(200, 20);
        }
    }

    @Override
    public String questName() {
        return "Prince Ali Rescue";
    }

    @Override
    public boolean checkRequirements() {
        // usually check skill and quest requirements here
        return true;
    }

    @Override
    public void onAppear(RSInterfaceChild rsInterfaceChild) {
        String message = rsInterfaceChild.getText();
        if (message == null) {
            return;
        }
        if (firstKey.checkWidget()) {
            firstKey.setHasPassed(true);
            Log.debug("[PrinceAliRescue]: Has given firstKey = true");
        } else if (secondKey.checkWidget()) {
            secondKey.setHasPassed(true);
            Log.debug("[PrinceAliRescue]: Has given second = true");
        } else if (thirdKey.checkWidget()) {
            thirdKey.setHasPassed(true);
            Log.debug("[PrinceAliRescue]: Has given third = true");
        }
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
