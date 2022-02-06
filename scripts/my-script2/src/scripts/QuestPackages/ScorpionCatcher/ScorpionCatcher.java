package scripts.QuestPackages.ScorpionCatcher;

import org.tribot.api2007.Game;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.Skill;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.DragonSlayer.DragonSlayer;
import scripts.QuestPackages.HeroesQuest.HeroesQuestBlackArmsGang;
import scripts.QuestSteps.*;
import scripts.Requirements.*;
import scripts.Requirements.Items.ItemCollections;
import scripts.Requirements.Util.ConditionalStep;
import scripts.Requirements.Util.Conditions;
import scripts.Requirements.Util.LogicType;
import scripts.Tasks.Priority;

import java.util.*;

public class ScorpionCatcher implements QuestTask {

    private static ScorpionCatcher quest;

    public static ScorpionCatcher get() {
        return quest == null ? quest = new ScorpionCatcher() : quest;
    }

    ItemRequirement dustyKey, jailKey, scorpionCageMissingTaverley, scorpionCageMissingMonastery, scorpionCageEmptyOrTaverley, scorpionCageTaverleyAndMonastery, scorpionCageFull, food,
            antiDragonShield, antiPoison, teleRunesFalador, gamesNecklace, gloryOrCombatBracelet, camelotTeleport;
  //  QuestRequirement fairyRingAccess;
    RSArea sorcerersTower3, sorcerersTower2, sorcerersTower1, taverleyDungeon, deepTaverleyDungeon1, deepTaverleyDungeon2, deepTaverleyDungeon3, deepTaverleyDungeon4,
            jailCell, taverleyScorpionRoom, upstairsMonastery, barbarianOutpost;
    Requirement has70Agility, inTaverleyDungeon, inDeepTaverleyDungeon, inJailCell, inSorcerersTower1, inSorcerersTower2,
            inSorcerersTower3, inTaverleyScorpionRoom, inUpstairsMonastery, inBarbarianOutpost;
    QuestStep speakToThormac, speakToSeer1, enterTaverleyDungeon, goThroughPipe, killJailerForKey,
            getDustyFromAdventurer, enterDeeperTaverley, pickUpJailKey,
            searchOldWall, catchTaverleyScorpion, sorcerersTowerLadder0, sorcerersTowerLadder1, sorcerersTowerLadder2, enterMonastery, catchMonasteryScorpion,
            catchOutpostScorpion, enterOutpost, returnToThormac;

    ConditionalStep finishQuest;


    public Map<Integer, QuestStep> loadSteps() {
        setupItemRequirements();
        setUpAreas();
        setupConditions();
        setupSteps();

        Map<Integer, QuestStep> steps = new HashMap<>();

        ConditionalStep goToTopOfTower = new ConditionalStep( sorcerersTowerLadder0);
        goToTopOfTower.addStep(inSorcerersTower1, sorcerersTowerLadder1);
        goToTopOfTower.addStep(inSorcerersTower2, sorcerersTowerLadder2);

        ConditionalStep beginQuest = new ConditionalStep( goToTopOfTower);
        beginQuest.addStep(inSorcerersTower3, speakToThormac);

        finishQuest = new ConditionalStep( goToTopOfTower, scorpionCageFull);
        finishQuest.addStep(inSorcerersTower3, returnToThormac);

        ConditionalStep goGetTaverleyScorpion = new ConditionalStep( enterTaverleyDungeon);
        goGetTaverleyScorpion.addStep(new Conditions(inTaverleyScorpionRoom), catchTaverleyScorpion);
        goGetTaverleyScorpion.addStep(new Conditions(inDeepTaverleyDungeon), searchOldWall);
        goGetTaverleyScorpion.addStep(new Conditions(inTaverleyDungeon, has70Agility), goThroughPipe);
        goGetTaverleyScorpion.addStep(new Conditions(inTaverleyDungeon, dustyKey), enterDeeperTaverley);
     //   goGetTaverleyScorpion.addStep(new Conditions(inTaverleyDungeon,
       //         new Conditions(LogicType.OR, inJailCell, jailKey)), getDustyFromAdventurer);
     //   goGetTaverleyScorpion.addStep(new Conditions(inTaverleyDungeon), pickUpJailKey);
     //   goGetTaverleyScorpion.addStep(new Conditions(inTaverleyDungeon), killJailerForKey);

        ConditionalStep scorpions = new ConditionalStep( finishQuest);
        scorpions.addStep(scorpionCageMissingTaverley, goGetTaverleyScorpion);

        scorpions.addStep(new Conditions(scorpionCageMissingMonastery, inUpstairsMonastery), catchMonasteryScorpion);
        scorpions.addStep(scorpionCageMissingMonastery, enterMonastery);

        scorpions.addStep(new Conditions(scorpionCageTaverleyAndMonastery, inBarbarianOutpost), catchOutpostScorpion);
        scorpions.addStep(scorpionCageTaverleyAndMonastery, enterOutpost);

        steps.put(0, beginQuest);
        steps.put(1, speakToSeer1);
        steps.put(2, scorpions);
        steps.put(3, scorpions);

        return steps;
    }

    private void setUpAreas() {
        sorcerersTower3 = new RSArea(new RSTile(2699, 3408, 3), new RSTile(2705, 3402, 3));
        sorcerersTower2 = new RSArea(new RSTile(2699, 3408, 2), new RSTile(2705, 3402, 2));
        sorcerersTower1 = new RSArea(new RSTile(2699, 3408, 1), new RSTile(2705, 3402, 1));

        taverleyDungeon = new RSArea(new RSTile(2816, 9668, 0), new RSTile(2973, 9855, 0));
        deepTaverleyDungeon1 = new RSArea(new RSTile(2816, 9856, 0), new RSTile(2880, 9760, 0));
        deepTaverleyDungeon2 = new RSArea(new RSTile(2880, 9760, 0), new RSTile(2907, 9793, 0));
        deepTaverleyDungeon3 = new RSArea(new RSTile(2889, 9793, 0), new RSTile(2923, 9815, 0));
        deepTaverleyDungeon4 = new RSArea(new RSTile(2907, 9772, 0), new RSTile(2928, 9793, 0));
        jailCell = new RSArea(new RSTile(2928, 9683, 0), new RSTile(2934, 9689, 0));
        taverleyScorpionRoom = new RSArea(new RSTile(2874, 9798, 0), new RSTile(2880, 9793, 0));

        upstairsMonastery = new RSArea(new RSTile(3043, 3499, 1), new RSTile(3060, 3481, 1));

        barbarianOutpost = new RSArea(new RSTile(2546, 3573, 0), new RSTile(2555, 3560, 0));
    }

    private void setupItemRequirements()
    {
        dustyKey = new ItemRequirement("Dusty Key", ItemID.DUSTY_KEY);
      //  dustyKey.setTooltip("Not needed if you have level 70 Agility, can be obtained during the quest");
        jailKey = new ItemRequirement("Jail Key", ItemID.JAIL_KEY);

        scorpionCageMissingTaverley = new ItemRequirement("Scorpion Cage", ItemID.SCORPION_CAGE);
        // The 3 below cages are combos of cages without the taverley scorpion
        scorpionCageMissingTaverley.addAlternateItemIDs(ItemID.SCORPION_CAGE_460, ItemID.SCORPION_CAGE_461, ItemID.SCORPION_CAGE_462);
      //  scorpionCageMissingTaverley.setTooltip("You can get another from Thormac");

        scorpionCageMissingMonastery = new ItemRequirement("Scorpion Cage", ItemID.SCORPION_CAGE_457);
        scorpionCageMissingMonastery.addAlternateItemIDs(ItemID.SCORPION_CAGE_458);

        scorpionCageEmptyOrTaverley = new ItemRequirement("Scorpion Cage", ItemID.SCORPION_CAGE);
        // Alternative is taverley + barb
        scorpionCageEmptyOrTaverley.addAlternateItemIDs(ItemID.SCORPION_CAGE_457);

        scorpionCageTaverleyAndMonastery = new ItemRequirement("Scorpion Cage", ItemID.SCORPION_CAGE_459);

        scorpionCageFull = new ItemRequirement("Scorpion Cage", ItemID.SCORPION_CAGE_463);

        // Recommended
        antiDragonShield = new ItemRequirement("Anti-dragon shield or DFS", ItemCollections.getAntifireShields());
        antiPoison = new ItemRequirement("Antipoison", ItemCollections.getAntipoisons());
        food = new ItemRequirement(ItemID.MONKFISH, -1);
        teleRunesFalador = new ItemRequirement("Teleport to Falador", ItemID.FALADOR_TELEPORT, -1);
        camelotTeleport = new ItemRequirement("Teleport to Camelot", ItemID.CAMELOT_TELEPORT, -1);
        gamesNecklace = new ItemRequirement("Games Necklace", ItemCollections.getGamesNecklaces());
        gloryOrCombatBracelet = new ItemRequirement("A charged glory or a combat bracelet", ItemCollections.getAmuletOfGlories());
      //  gloryOrCombatBracelet.addAlternates(ItemCollections.getCombatBracelets());
      //  fairyRingAccess = new QuestRequirement(QuestHelperQuest.FAIRYTALE_II__CURE_A_QUEEN, QuestState.IN_PROGRESS, "Fairy ring access");
     //   fairyRingAccess.setTooltip(QuestHelperQuest.FAIRYTALE_II__CURE_A_QUEEN.getName() + " is required to at least be started in order to use fairy rings");

    }

    private void setupConditions()
    {
        has70Agility = new SkillRequirement(Skills.SKILLS.AGILITY, 70);

        inSorcerersTower1 = new AreaRequirement(sorcerersTower1);
        inSorcerersTower2 = new AreaRequirement(sorcerersTower2);
        inSorcerersTower3 = new AreaRequirement(sorcerersTower3);


        inTaverleyDungeon = new AreaRequirement(taverleyDungeon);
        inDeepTaverleyDungeon = new AreaRequirement(deepTaverleyDungeon1, deepTaverleyDungeon2, deepTaverleyDungeon3, deepTaverleyDungeon4);
        inJailCell = new AreaRequirement(jailCell);
       // jailKeyNearby = new ItemOnTileRequirement(jailKey);

        inTaverleyScorpionRoom = new AreaRequirement(taverleyScorpionRoom);
        inUpstairsMonastery = new AreaRequirement(upstairsMonastery);
        inBarbarianOutpost = new AreaRequirement(barbarianOutpost);
    }

    private void setupSteps()
    {
        speakToThormac = new NPCStep( NpcID.THORMAC, new RSTile(2702, 3405, 3),
                "Speak to Thormac on the top floor of the Sorcerer's Tower south of Seer's Village.");
        speakToThormac.addDialogStep("What do you need assistance with?");
        speakToThormac.addDialogStep("So how would I go about catching them then?");
        speakToThormac.addDialogStep("Ok, I will do it then");

        sorcerersTowerLadder0 = new ObjectStep( ObjectID.LADDER_16683, new RSTile(2701, 3408, 0),
                "Climb-up");
        sorcerersTowerLadder1 = new ObjectStep( ObjectID.LADDER_16683, new RSTile(2704, 3403, 1),
                "Climb-up");
        sorcerersTowerLadder2 = new ObjectStep( ObjectID.LADDER_16683, new RSTile(2699, 3405, 2),
                "Climb-up");
        speakToThormac.addSubSteps(sorcerersTowerLadder0, sorcerersTowerLadder1, sorcerersTowerLadder2);

        speakToSeer1 = new NPCStep( NpcID.SEER, new RSTile(2710, 3484, 0),
                "Speak to a seer in Seer's Village.");
        speakToSeer1.addDialogStep("I need to locate some scorpions.");
        speakToSeer1.addDialogStep("Your friend Thormac sent me to speak to you.");

        if (Skill.AGILITY.getActualLevel() >= 70) {
            enterTaverleyDungeon = new ObjectStep( ObjectID.LADDER_16680, new RSTile(2884, 3397, 0),
                    "Go to Taverley Dungeon. As you're 70 Agility, you don't need a dusty key.", scorpionCageMissingTaverley);
        }
        else {
            enterTaverleyDungeon = new ObjectStep( ObjectID.LADDER_16680, new RSTile(2884, 3397, 0),
                    "Go to Taverley Dungeon. Bring a dusty key if you have one, otherwise you can get one in the dungeon.", scorpionCageMissingTaverley, dustyKey);
        }

        goThroughPipe = new ObjectStep( ObjectID.OBSTACLE_PIPE_16509, new RSTile(2888, 9799, 0),
                "Squeeze through the obstacle pipe.");


        getDustyFromAdventurer.addDialogStep("So... do you know anywhere good to explore?");
        getDustyFromAdventurer.addDialogStep("Yes please!");

        enterDeeperTaverley = new ObjectStep( ObjectID.GATE_2623, new RSTile(2924, 9803, 0),
                "Open", dustyKey);
        enterTaverleyDungeon.addSubSteps(goThroughPipe, killJailerForKey, getDustyFromAdventurer, enterDeeperTaverley);
        searchOldWall = new ObjectStep( ObjectID.OLD_WALL, new RSTile(2875, 9799, 0), "Search the Old wall.");

        catchTaverleyScorpion = new UseItemOnNpcStep( ItemID.SCORPION_CAGE, NpcID.KHARID_SCORPION,
                new RSTile(2875, 9799, 0),
                "Use the scorpion cage on the scorpion.",
                scorpionCageMissingTaverley);

        enterMonastery = new ObjectStep( ObjectID.LADDER_2641, new RSTile(3057, 3483, 0),
                "Enter the Edgeville Monastery.");

        catchMonasteryScorpion = new UseItemOnNpcStep( ItemID.SCORPION_CAGE_459,
                NpcID.KHARID_SCORPION_5230,  new RSTile(3057, 3483, 0),
                "Use the scorpion cage on the scorpion.",
                scorpionCageMissingMonastery);

        enterOutpost = new ObjectStep( ObjectID.GATE_2115, new RSTile(2545, 3570, 0),
                "Enter the Barbarian Outpost.");
        catchOutpostScorpion = new UseItemOnNpcStep(  ItemID.SCORPION_CAGE_459,
                NpcID.KHARID_SCORPION_5229, new RSTile(2553, 3570, 0),
                "Use the scorpion cage on the scorpion.", scorpionCageTaverleyAndMonastery);

        returnToThormac = new NPCStep( NpcID.THORMAC,new RSTile(2702, 3405, 3), scorpionCageFull);
    }


    public ArrayList<ItemRequirement> getItemRequirements() {
        ArrayList<ItemRequirement> reqs = new ArrayList<>();
        if (Skills.SKILLS.AGILITY.getActualLevel() < 70) {
            reqs.add(dustyKey);
        }

        return reqs.isEmpty() ? null : reqs;
    }


    public ArrayList<ItemRequirement> getItemRecommended()
    {
        return new ArrayList<>(Arrays.asList(antiDragonShield, antiPoison, food, teleRunesFalador, camelotTeleport, gamesNecklace,
                gloryOrCombatBracelet));
    }


    public ArrayList<String> getCombatRequirements()
    {
        return new ArrayList<>(Collections.singletonList("The ability to run past level 172 black demons and level 64 poison spiders"));
    }




    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.ANTIDRAGON_SHIELD, 1, 500),
                    new GEItem(ItemID.FALADOR_TELEPORT, 5, 300),
                    new GEItem(ItemID.SHARK, 8, 50),
                    new GEItem(ItemID.RUNE_SCIMITAR, 1, 50),
                    new GEItem(ItemID.RUNE_FULL_HELM, 1, 20),
                    //combat gear
                    new GEItem(ItemID.ANTIDOTE_PLUS_PLUS[0], 1, 30),
                    new GEItem(ItemID.STAMINA_POTION[0], 5, 15),
                    new GEItem(ItemID.RING_OF_WEALTH[0], 1, 25)
            )
    );

    InventoryRequirement initialItemReqs = new InventoryRequirement(new ArrayList<>(
            Arrays.asList(
                    new ItemReq(ItemID.COINS, 2500, 500),
                    new ItemReq(ItemID.STAMINA_POTION[0], 4, 0),
                    new ItemReq(ItemID.ANTIDRAGON_SHIELD, 1, true, true),
                    new ItemReq(ItemID.FALADOR_TELEPORT, 5, 1),
                    new ItemReq(ItemID.ANTIDOTE_PLUS_PLUS[0], 1, 0),
                    new ItemReq(ItemID.SHARK, 8, 1),
                    //combat gear
                    new ItemReq(ItemID.RING_OF_WEALTH[0], 1, 0, true, true)
            )
    ));

    BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);

    public void buyAndGetItems(){
        if (initialItemReqs.check()){
            buyStep.buyItems();
            initialItemReqs.withdrawItems();
        }
    }

    public ArrayList<Requirement> getGeneralRequirements()
    {
        ArrayList<Requirement> reqs = new ArrayList<>();
       // reqs.add(new QuestRequirement(QuestHelperQuest.ALFRED_GRIMHANDS_BARCRAWL, QuestState.FINISHED));
        reqs.add(new SkillRequirement(Skills.SKILLS.PRAYER, 31));

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
        //get items
        buyAndGetItems();

        int gameSetting = Game.getSetting(QuestVarPlayer.QUEST_SCORPION_CATCHER.getId());
        Map<Integer, QuestStep> steps = loadSteps();
        if (!dustyKey.check()){
            HeroesQuestBlackArmsGang.get().getDustyKey();
        }
        Optional<QuestStep> step = Optional.ofNullable(steps.get(gameSetting));
        cQuesterV2.status = step.map(s -> s.toString()).orElse("Unknown Step Name");
        step.ifPresent(QuestStep::execute);
    }

    @Override
    public String questName() {
        return "Scorpion Catcher";
    }

    @Override
    public boolean checkRequirements() {
        ArrayList<Requirement> reqs =  getGeneralRequirements();
        return reqs.stream().allMatch(Requirement::check);
    }
}
