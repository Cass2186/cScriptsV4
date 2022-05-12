package scripts.QuestPackages.observatoryQuest;

import lombok.Getter;
import obf.G;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.*;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.Area;
import org.tribot.script.sdk.types.GameObject;
import org.tribot.script.sdk.types.Widget;
import org.tribot.script.sdk.types.WorldTile;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.NatureSpirit.NatureSpirit;
import scripts.QuestSteps.*;
import scripts.Requirements.*;
import scripts.Requirements.Conditional.NpcCondition;
import scripts.Requirements.Items.ItemCollections;
import scripts.Requirements.Util.ConditionalStep;
import scripts.Requirements.Util.Conditions;
import scripts.Requirements.Util.LogicType;
import scripts.Tasks.Priority;

import java.util.*;

public class ObservatoryQuest implements QuestTask {

    private static ObservatoryQuest quest;

    public static ObservatoryQuest get() {
        return quest == null ? quest = new ObservatoryQuest() : quest;
    }

    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.PLANK, 3, 40),
                    new GEItem(ItemID.BRONZE_BAR, 1, 500),
                    new GEItem(ItemID.MOLTEN_GLASS, 1, 50),
                    new GEItem(ItemID.RING_OF_DUELING8, 1, 50),
                    new GEItem(ItemID.NECKLACE_OF_PASSAGE5, 1, 75),
                    new GEItem(ItemID.ANTIDOTE_PLUS_PLUS[0], 1, 50),
                    new GEItem(ItemID.LOBSTER, Utils.random(8, 12), 50),
                    new GEItem(ItemID.AMULET_OF_GLORY4, 1, 30),
                    new GEItem(ItemID.STAMINA_POTION[0], 3, 15),
                    new GEItem(ItemID.RING_OF_WEALTH[0], 1, 25)
            )
    );

    InventoryRequirement initialItemReqs = new InventoryRequirement(new ArrayList<>(
            Arrays.asList(
                    new ItemReq(ItemID.PLANK, 3),
                    new ItemReq(ItemID.BRONZE_BAR, 1),
                    new ItemReq(ItemID.MOLTEN_GLASS, 1),
                    new ItemReq(ItemID.NECKLACE_OF_PASSAGE5, 1, 0),
                    new ItemReq(ItemID.RING_OF_DUELING8, 1, 0),
                    new ItemReq(ItemID.ANTIDOTE_PLUS_PLUS[0], 1, 0),
                    new ItemReq(ItemID.LOBSTER, 8, 2),
                    new ItemReq(ItemID.AMULET_OF_GLORY4, 1, 0, true, true),
                    new ItemReq(ItemID.STAMINA_POTION[0], 3, 0),
                    new ItemReq(ItemID.RING_OF_WEALTH[0], 1, 0, true)

            )
    ));

    BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);
    //NPCIDs


    HashMap<Integer, String> starSign = new HashMap<>();
    int currentValue = -1;
    //Items Required
    ItemRequirement plank, bronzeBar, moltenGlass;

    //Items Recommended
    ItemRequirement food, duelingRing, antipoison;

    ItemRequirement mould, lens, key;

    Requirement inObservatoryDungeon, inObservatoryF1, inObservatoryF2, usedKey, sleepingGuardNearby, hasMould,
            lookedThroughTelescope;

    QuestStep talkToProfessor, giveProfessorPlanks, giveProfessorBar, giveProfessorGlass, talkToAssistant,
            enterDungeon, searchChests, prodGuard, inspectStove, leaveDungeon, giveProfessorMould, useGlassOnMould,
            giveProfessorLensAndMould, enterDungeonAgain, enterObservatory, goToF2Observatory, viewTelescope,
            tellProfessorConstellation;

    //RSArea()s
    RSArea observatoryDungeon, observatoryF1, observatoryF2;

    private boolean handleWarning() {
        if (!Query.widgets().inIndexPath(560).textContains("Proceed").isVisible().isAny()) {
            Waiting.waitUntil(3500, 250, () -> Query.widgets().inIndexPath(560).textContains("Proceed").isVisible().isAny());
        }
        Optional<Widget> proceed = Query.widgets().inIndexPath(560).textContains("Proceed").findFirst();
        WorldTile t = MyPlayer.getTile();
        if (proceed.map(p -> p.click()).orElse(false)) {
            return Waiting.waitUntil(5000, 500, () -> !MyPlayer.getTile().equals(t));
        }
        return false;
    }

    public Map<Integer, QuestStep> loadSteps() {
        loadRSAreas();
        setupItemRequirements();
        setupConditions();
        setupSteps();
        Map<Integer, QuestStep> steps = new HashMap<>();

        steps.put(0, talkToProfessor);
        steps.put(1, giveProfessorPlanks);
        steps.put(2, giveProfessorBar);
        steps.put(3, giveProfessorGlass);

        ConditionalStep goGetLens = new ConditionalStep(enterDungeon);
        goGetLens.addStep(new Conditions(inObservatoryDungeon, hasMould), leaveDungeon);
        goGetLens.addStep(hasMould, giveProfessorMould);
        goGetLens.addStep(new Conditions(inObservatoryDungeon, new Conditions(LogicType.OR,
                key, usedKey), sleepingGuardNearby), prodGuard);
        goGetLens.addStep(new Conditions(inObservatoryDungeon, new Conditions(LogicType.OR,
                key, usedKey)), inspectStove);
        goGetLens.addStep(inObservatoryDungeon, searchChests);
        steps.put(4, goGetLens);

        ConditionalStep makeLens = new ConditionalStep(enterDungeon);
        makeLens.addStep(lens, giveProfessorLensAndMould);
        makeLens.addStep(new Conditions(inObservatoryDungeon, hasMould), leaveDungeon);
        makeLens.addStep(hasMould, useGlassOnMould);
        makeLens.addStep(new Conditions(inObservatoryDungeon, usedKey, sleepingGuardNearby), prodGuard);
        makeLens.addStep(new Conditions(inObservatoryDungeon, usedKey), inspectStove);
        steps.put(5, makeLens);

        ConditionalStep goLookInTelescope = new ConditionalStep(enterDungeonAgain);
        goLookInTelescope.addStep(new Conditions(lookedThroughTelescope, inObservatoryF2), tellProfessorConstellation);
        goLookInTelescope.addStep(inObservatoryF2, viewTelescope);
        goLookInTelescope.addStep(inObservatoryF1, goToF2Observatory);
        goLookInTelescope.addStep(inObservatoryDungeon, enterObservatory);
        steps.put(6, goLookInTelescope);

        return steps;
    }

    public void setupItemRequirements() {
        plank = new ItemRequirement("Plank", ItemID.PLANK);
        bronzeBar = new ItemRequirement("Bronze bar", ItemID.BRONZE_BAR);
        moltenGlass = new ItemRequirement("Molten glass", ItemID.MOLTEN_GLASS);

        food = new ItemRequirement(ItemCollections.getGoodEatingFood(), -1);
        duelingRing = new ItemRequirement("Ring of dueling", ItemCollections.getRingOfDuelings());
        antipoison = new ItemRequirement("Antipoison (there is a spawn near the Observatory of superantipoison)",
                ItemCollections.getAntipoisons());

        mould = new ItemRequirement("Lens mould", ItemID.LENS_MOULD);
        lens = new ItemRequirement("Observatory lens", ItemID.OBSERVATORY_LENS);
        key = new ItemRequirement("Goblin kitchen key", ItemID.GOBLIN_KITCHEN_KEY);
    }

    public void setupConditions() {
        inObservatoryDungeon = new AreaRequirement(observatoryDungeon);
        inObservatoryF1 = new AreaRequirement(observatoryF1);
        inObservatoryF2 = new AreaRequirement(observatoryF2);

        // Started quest
        // 3828 = 9
        // 3827 = 1
        usedKey = new VarbitRequirement(3826, 1);
        sleepingGuardNearby = new NpcCondition(NpcID.SLEEPING_GUARD);
        hasMould = new VarbitRequirement(3837, 1);
        // Watched cutscene, 3838 = 1
        lookedThroughTelescope = new VarbitRequirement(3836, 1);
    }

    public void loadRSAreas() {
        observatoryDungeon = new RSArea(new RSTile(2295, 9340, 0), new RSTile(2370, 9410, 0));
        observatoryF1 = new RSArea(new RSTile(2433, 3154, 0), new RSTile(2448, 3169, 0));
        observatoryF2 = new RSArea(new RSTile(2433, 3154, 1), new RSTile(2448, 3169, 1));
    }

    WorldTile CHEST_TILE_1 = new WorldTile(2359, 9366, 0);
    int CLOSED_CHEST = 25387;

    private void searchChests() {
        List<GameObject> closedChest = Query.gameObjects().actionContains("Open").idEquals(25385, CLOSED_CHEST, 25386, 25388, 25389)
                .sortedByPathDistance().toList();
        for (GameObject object : closedChest) {
            if (object.interact("Open")) {
                Waiting.waitUntil(8000, 500, MyPlayer::isAnimating);
            }
            Optional<GameObject> openChest = Query.gameObjects()
                    .actionContains("Search")
                    .idEquals(25385, CLOSED_CHEST, 25386, 25388, 25389)
                    .inArea(Area.fromRadius(MyPlayer.getTile(), 2))
                    .findClosestByPathDistance();
            if (openChest.map(chest->chest.interact("Search")).orElse(false)){
                Waiting.waitUntil(8000, 500, ()-> ChatScreen.isOpen());
                ChatScreen.handle();
            }
        }


    }

    public void setupSteps() {
        talkToProfessor = new NPCStep(NpcID.OBSERVATORY_PROFESSOR, new RSTile(2442, 3186, 0),
                plank.quantity(3), moltenGlass, bronzeBar);
        talkToProfessor.addDialogStep("Talk about the Observatory quest.", "An Observatory?", "Yes.");
        giveProfessorPlanks = new NPCStep(NpcID.OBSERVATORY_PROFESSOR, new RSTile(2442, 3186, 0),
                //  "Give the professor 3 planks.",
                plank.quantity(3));
        giveProfessorPlanks.addDialogStep("Talk about the Observatory quest.");
        giveProfessorBar = new NPCStep(NpcID.OBSERVATORY_PROFESSOR,
                new RSTile(2442, 3186, 0),
                //  "Give the professor a bronze bar.",
                bronzeBar);
        giveProfessorBar.addDialogStep("Talk about the Observatory quest.");
        giveProfessorGlass = new NPCStep(NpcID.OBSERVATORY_PROFESSOR, new RSTile(2442, 3186, 0),
                //  "Give the professor some molten glass.",
                moltenGlass);
        giveProfessorGlass.addDialogStep("Talk about the Observatory quest.");
        talkToAssistant = new NPCStep(NpcID.OBSERVATORY_ASSISTANT, new RSTile(2443, 3189, 0),
                "Talk to the observatory assistant.");
        enterDungeon = new ObjectStep(ObjectID.STAIRS_25432, new RSTile(2458, 3185, 0),
                "Climb-down", handleWarning());
        searchChests = new ObjectStep(ObjectID.CHEST_25385,
                "Search");
        ((ObjectStep) searchChests).addAlternateObjects(ObjectID.CHEST_25386, ObjectID.CHEST_25387,
                ObjectID.CHEST_25388, ObjectID.CHEST_25389, ObjectID.CHEST_25390);
        prodGuard = new NPCStep(NpcID.SLEEPING_GUARD, new RSTile(2327, 9394, 0),
                "Prod the sleeping guard in the north of the dungeon. He'll attack you. You need to then either kill him," +
                        " or get him in the marked spot to the north of the gate.");
        // prodGuard.addTileMarker(new RSTile()(2327, 9399, 0), SpriteID.BARBARIAN_ASSAULT_HORN_FOR_HEALER_ICON);
        inspectStove = new ObjectStep(25442, new RSTile(2327, 9389, 0),
                "Either kill or trap the guard on the marked tile to the north, then search the goblin stove.");
        //  inspectStove.addTileMarker(new RSTile(2327, 9399, 0), SpriteID.BARBARIAN_ASSAULT_HORN_FOR_HEALER_ICON);
        leaveDungeon = new ObjectStep(ObjectID.STAIRS_25429, new RSTile(2355, 9396, 0),
                "Climb-up");
        giveProfessorMould = new NPCStep(NpcID.OBSERVATORY_PROFESSOR, new RSTile(2442, 3186, 0),
                //  "Give the professor the lens mould. If you don't have it, check your bank.",
                mould);
        giveProfessorMould.addDialogStep("Talk about the Observatory quest.");
        useGlassOnMould = new UseItemOnItemStep(ItemID.MOLTEN_GLASS, ItemID.LENS_MOULD, Inventory.contains(ItemID.OBSERVATORY_LENS));
        giveProfessorLensAndMould = new NPCStep(NpcID.OBSERVATORY_PROFESSOR, new RSTile(2442, 3186, 0),
                lens);
        giveProfessorLensAndMould.addDialogStep("Talk about the Observatory quest.");
        enterDungeonAgain = new ObjectStep(ObjectID.STAIRS_25432, new RSTile(2458, 3186, 0),
                "Enter", handleWarning());
        enterObservatory = new ObjectStep(ObjectID.STAIRS_25429, new RSTile(2335, 9352, 0),
                "Climb-up");
        goToF2Observatory = new ObjectStep(ObjectID.STAIRS_25431, new RSTile(2444, 3160, 0),
                "Climb-up");
        viewTelescope = new ObjectStep(25591, new RSTile(2441, 3162, 1),
                "Use the telescope.");
        //tellProfessorConstellation = new StarSignAnswer(this);
        //  tellProfessorConstellation.addDialogStep("Talk about the Observatory quest.");
    }


    public void initializeStarStep() {
        // super(NpcID.OBSERVATORY_PROFESSOR, new RSTile(2440, 3159, 1));
        starSign.put(0, "Aquarius");
        starSign.put(1, "Capricorn");
        starSign.put(2, "Sagittarius");
        starSign.put(3, "Scorpio");
        starSign.put(4, "Libra");
        starSign.put(5, "Virgo");
        starSign.put(6, "Leo");
        starSign.put(7, "Cancer");
        starSign.put(8, "Gemini");
        starSign.put(9, "Taurus");
        starSign.put(10, "Aries");
        starSign.put(11, "Pisces");
    }

    private enum StarSign {
        AQUARIUS(0, "Aquarius"),
        CAPRICORN(1, "Capricorn"),
        SAGITTARIUS(2, "Sagittarius"),
        SCROPIO(3, "Scorpio"),
        LIBRA(4, "Libra"),
        VIRGO(5, "Virgo"),
        LEO(6, "Leo"),
        CANCER(7, "Cancer"),
        GEMINI(8, "Gemini"),
        TAURUS(9, "Taurus"),
        ARIES(10, "Aries"),
        PICES(11, "Pisces");

        @Getter
        private int varbit;
        @Getter
        private String string;

        StarSign(int varbit, String string) {
            this.varbit = varbit;
            this.string = string;
        }

        public static String getStarSignFromVarbit(int varbitValue) {
            for (StarSign s : StarSign.values()) {
                if (s.getVarbit() == varbitValue)
                    return s.getString();
            }
            return null;
        }
    }

    private void updateCorrectChoice() {
        //   addDialogSteps("Talk about the Observatory quest.");
        int currentStep = GameState.getSetting(QuestVarPlayer.QUEST_OBSERVATORY_QUEST.getId());
        if (currentStep < 2) {
            return;
        }

        int newValue = Utils.getVarBitValue(3828);
        if (currentValue != newValue) {
            currentValue = newValue;
            String constellation = StarSign.getStarSignFromVarbit(newValue);
            // setText("Tell the professor you observed " + constellation + ".");
            // addDialogStep(constellation);
        }

    }

    public List<ItemRequirement> getItemRequirements() {
        return Arrays.asList(plank.quantity(3), bronzeBar, moltenGlass);
    }


    public List<ItemRequirement> getItemRecommended() {
        return Arrays.asList(duelingRing, antipoison, food);
    }

    /*
        public List<String> getCombatRequirements()
        {
            return Collections.singletonList("Goblin Guard (level 42, or you can lure it/have someone else kill it)");
        }


        public QuestPointReward getQuestPointReward()
        {
            return new QuestPointReward(2);
        }


        public List<ExperienceReward> getExperienceRewards()
        {
            return Collections.singletonList(new ExperienceReward(Skill.CRAFTING, 2250));
        }


        public List<UnlockReward> getUnlockRewards()
        {
            return Collections.singletonList(new UnlockReward("A reward depending on which constellation you observed."));
        }
    */
    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return cQuesterV2.taskList.size() > 0 &&
                cQuesterV2.taskList.get(0).equals(this);
    }

    @Override
    public void execute() {
        int gameSetting = QuestVarPlayer.QUEST_OBSERVATORY_QUEST.getId();
        Log.info("Observatory Quest Game setting is " + GameState.getSetting(gameSetting));

        // buy initial items on quest start
        if (GameState.getSetting(gameSetting) == 0) {
            if (!initialItemReqs.check()) {
                buyStep.buyItems();
                initialItemReqs.withdrawItems();
            }
        }
        if (GameState.getSetting(gameSetting) == 4) {
            cQuesterV2.status = "Searching chests";
            searchChests();
        }
        // done quest
        if (isComplete()) {
            Log.info("Observatory Quest is complete");
            cQuesterV2.taskList.remove(this);
            return;
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
        if (ChatScreen.isOpen()) {
            Log.debug("Handling chat screen -test");
            ChatScreen.handle();
            // NPCInteraction.handleConversation();
        }

        //slow down looping if it gets stuck
        Waiting.waitNormal(200, 20);
    }

    @Override
    public String questName() {
        return "Observatory Quest";
    }

    @Override
    public boolean checkRequirements() {
        return false;
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
        return Quest.OBSERVATORY_QUEST.getState().equals(Quest.State.COMPLETE);
    }
}
