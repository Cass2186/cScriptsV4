package scripts.QuestPackages.OlafsQuest;

import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.Quest;
import scripts.ItemID;
import scripts.NpcID;
import scripts.NullObjectID;
import scripts.ObjectID;
import scripts.QuestPackages.NatureSpirit.NatureSpirit;
import scripts.QuestSteps.*;
import scripts.Requirements.*;
import scripts.Requirements.Conditional.NpcCondition;
import scripts.Requirements.Items.ItemCollections;
import scripts.Requirements.Util.ConditionalStep;
import scripts.Requirements.Util.Conditions;
import scripts.Requirements.Util.Operation;
import scripts.Tasks.Priority;

import java.util.*;

public class OlafsQuest implements QuestTask {
    private static OlafsQuest quest;

    public static OlafsQuest get() {
        return quest == null ? quest = new OlafsQuest() : quest;
    }


    //Items Required
    ItemRequirement axe, tinderbox, spade, dampPlanks, windsweptLogs, crudeCarving, cruderCarving, key, rottenBarrels2, rottenBarrel, ropes6, ropes3, crossKey, squareKey,
            triangleKey, circleKey, starKey;

    //Items Recommended
    ItemRequirement  prayerPotions, food, combatGear;

    Requirement givenIngridCarving, inFirstArea, inSecondArea, inThirdArea, keyNearby, puzzleOpen, has2Barrels6Ropes, hasBarrel3Ropes, placedBarrel1, placedBarrel2,
            keyInterfaceOpen, ulfricNearby, killedUlfric;

    QuestStep talkToOlaf, chopTree, giveLogToOlaf, talkToIngrid, talkToVolf, returnToOlaf, useDampPlanks, talkToOlafAfterPlanks, digHole, pickUpKey, searchPainting, doPuzzle, pickUpItems,
            pickUpItems2, useBarrel, useBarrel2, openGate, chooseSquare, chooseCross, chooseTriangle, chooseCircle, chooseStar, killUlfric;

    NPCStep killSkeleton;

    ObjectStep searchChest, searchChestAgain;

    //RSArea()s
    RSArea firstArea, firstArea2, secondArea, secondArea2, thirdArea;


    public Map<Integer, QuestStep> loadSteps() {
        loadRSArea();
        setupItemRequirements();
        setupConditions();
        setupSteps();
        Map<Integer, QuestStep> steps = new HashMap<>();

        steps.put(0, talkToOlaf);

        ConditionalStep getLogs = new ConditionalStep( chopTree);
        getLogs.addStep(windsweptLogs, giveLogToOlaf);

        steps.put(10, getLogs);

        ConditionalStep bringCarvings = new ConditionalStep( talkToIngrid);
        bringCarvings.addStep(givenIngridCarving, talkToVolf);

        steps.put(20, bringCarvings);

        steps.put(30, returnToOlaf);

        steps.put(40, useDampPlanks);

        steps.put(50, talkToOlafAfterPlanks);

        ConditionalStep solvePuzzleSteps = new ConditionalStep( digHole);
        solvePuzzleSteps.addStep(new Conditions(inThirdArea, killedUlfric), searchChestAgain);
        solvePuzzleSteps.addStep(new Conditions(ulfricNearby, inThirdArea), killUlfric);
        solvePuzzleSteps.addStep(inThirdArea, searchChest);
        solvePuzzleSteps.addStep(new Conditions(starKey, keyInterfaceOpen), chooseStar);
        solvePuzzleSteps.addStep(new Conditions(circleKey, keyInterfaceOpen), chooseCircle);
        solvePuzzleSteps.addStep(new Conditions(triangleKey, keyInterfaceOpen), chooseTriangle);
        solvePuzzleSteps.addStep(new Conditions(squareKey, keyInterfaceOpen), chooseSquare);
        solvePuzzleSteps.addStep(new Conditions(crossKey, keyInterfaceOpen), chooseCross);
        solvePuzzleSteps.addStep(new Conditions(key, placedBarrel2), openGate);
        solvePuzzleSteps.addStep(new Conditions(key, placedBarrel1, hasBarrel3Ropes), useBarrel2);
        solvePuzzleSteps.addStep(new Conditions(placedBarrel1, inSecondArea, key), pickUpItems2);
        solvePuzzleSteps.addStep(new Conditions(has2Barrels6Ropes, key), useBarrel);
        solvePuzzleSteps.addStep(new Conditions(inSecondArea, key), pickUpItems);
        solvePuzzleSteps.addStep(puzzleOpen, doPuzzle);
        solvePuzzleSteps.addStep(key, searchPainting);
        solvePuzzleSteps.addStep(keyNearby, pickUpKey);
        solvePuzzleSteps.addStep(inFirstArea, killSkeleton);

        steps.put(60, solvePuzzleSteps);
        steps.put(70, solvePuzzleSteps);

        return steps;
    }

    public void setupItemRequirements()
    {
        combatGear = new ItemRequirement("Combat gear", -1, -1);
        food = new ItemRequirement(ItemCollections.getGoodEatingFood(), -1);
        prayerPotions = new ItemRequirement(ItemCollections.getPrayerPotions(), -1);

        axe = new ItemRequirement("Any axe", ItemCollections.getAxes());
        tinderbox = new ItemRequirement("Tinderbox", ItemID.TINDERBOX);
        spade = new ItemRequirement("Spade", ItemID.SPADE);

        dampPlanks = new ItemRequirement("Damp planks", ItemID.DAMP_PLANKS);
        windsweptLogs = new ItemRequirement("Windswept logs", ItemID.WINDSWEPT_LOGS);

        crudeCarving = new ItemRequirement("Crude carving", ItemID.CRUDE_CARVING);
      //  crudeCarving.setTooltip("You can get another from Olaf");
        cruderCarving = new ItemRequirement("Cruder carving", ItemID.CRUDER_CARVING);
     //   cruderCarving.setTooltip("You can get another from Olaf");

        key = new ItemRequirement("Key", ItemID.KEY_11039);
        key.addAlternateItemIDs(ItemID.KEY_11040, ItemID.KEY_11041, ItemID.KEY_11042, ItemID.KEY_11043);

        crossKey = new ItemRequirement("Key", ItemID.KEY_11039);
        squareKey = new ItemRequirement("Key", ItemID.KEY_11040);
        triangleKey = new ItemRequirement("Key", ItemID.KEY_11041);
        circleKey = new ItemRequirement("Key", ItemID.KEY_11042);
        starKey = new ItemRequirement("Key", ItemID.KEY_11043);

        rottenBarrel = new ItemRequirement("Rotten barrel", ItemID.ROTTEN_BARREL);
        rottenBarrel.addAlternateItemIDs(ItemID.ROTTEN_BARREL_11045);
        rottenBarrels2 = new ItemRequirement("Rotten barrel", ItemID.ROTTEN_BARREL, 2);
        rottenBarrels2.addAlternateItemIDs(ItemID.ROTTEN_BARREL_11045);

        ropes3 = new ItemRequirement("Rope", ItemID.ROPE, 3);
        ropes3.addAlternateItemIDs(ItemID.ROPE_11046);
        ropes6 = new ItemRequirement("Rope", ItemID.ROPE, 6);
        ropes6.addAlternateItemIDs(ItemID.ROPE_11046);
    }

    public void setupConditions()
    {
        givenIngridCarving = new VarbitRequirement(3536, 1, Operation.GREATER_EQUAL);
        inFirstArea = new AreaRequirement(firstArea, firstArea2);
        inSecondArea = new AreaRequirement(secondArea, secondArea2);
        inThirdArea = new AreaRequirement(thirdArea);
        keyNearby = new ItemOnTileRequirement(ItemID.KEY_11039);
       puzzleOpen = new WidgetModelRequirement(253, 0, 24126);
        hasBarrel3Ropes = new Conditions(rottenBarrel, ropes3);
        has2Barrels6Ropes = new Conditions(rottenBarrels2, ropes6);
        placedBarrel1 = new VarbitRequirement(3547, 1);
        placedBarrel2 = new VarbitRequirement(3548, 1);
        keyInterfaceOpen = new WidgetModelRequirement(252, 0, 24124);

        ulfricNearby = new NpcCondition(NpcID.ULFRIC);

        killedUlfric = new VarbitRequirement(3539, 1);
    }

    public void loadRSArea()
    {
        firstArea = new RSArea(new RSTile(2689, 10116, 0), new RSTile(2707, 10141, 0));
        firstArea2 = new RSArea(new RSTile(2707, 10118, 0), new RSTile(2739, 10148, 0));
        secondArea = new RSArea(new RSTile(2691, 10143, 0), new RSTile(2706, 10170, 0));
        secondArea2 = new RSArea(new RSTile(2707, 10149, 0), new RSTile(2735, 10170, 0));
        thirdArea = new RSArea(new RSTile(2726, 10154, 0), new RSTile(2749, 10170, 0));
    }

    public void setupSteps()
    {
        talkToOlaf = new NPCStep( NpcID.OLAF_HRADSON, new RSTile(2722, 3727, 0), "Talk to Olaf Hradson north east of Rellekka.");
        talkToOlaf.addDialogStep("Yes.");
        chopTree = new ObjectStep( ObjectID.WINDSWEPT_TREE_18137, new RSTile(2749, 3735, 0), "Chop a log from the Windswept Tree east of Olaf.", axe);
        giveLogToOlaf = new NPCStep( NpcID.OLAF_HRADSON, new RSTile(2722, 3727, 0),
               windsweptLogs);
        talkToIngrid = new NPCStep( NpcID.INGRID_HRADSON, new RSTile(2670, 3670, 0),
             crudeCarving);
        talkToVolf = new NPCStep( NpcID.VOLF_OLAFSON, new RSTile(2662, 3700, 0),
              cruderCarving);

        returnToOlaf = new NPCStep( NpcID.OLAF_HRADSON, new RSTile(2722, 3727, 0),
                "Return to Olaf Hradson north east of Rellekka.");
        useDampPlanks = new UseItemOnObjectStep( ItemID.DAMP_PLANKS, NullObjectID.NULL_14172, new RSTile(2724, 3728, 0),
                "Use the damp planks on Olaf's embers.", dampPlanks);
        talkToOlafAfterPlanks = new NPCStep( NpcID.OLAF_HRADSON, new RSTile(2722, 3727, 0), food);
        talkToOlafAfterPlanks.addDialogStep("Alright, here, have some food. Now give me the map.");
        digHole = new ClickItemStep(ItemID.SPADE, "Dig",
                new RSTile(2748, 3732, 0));

        killSkeleton = new NPCStep( NpcID.SKELETON_FREMENNIK,
                new RSTile(2727, 10141, 0));
        killSkeleton.setInteractionString("Attack");
        killSkeleton.addAlternateNpcs(NpcID.SKELETON_FREMENNIK_4492, NpcID.SKELETON_FREMENNIK_4493, NpcID.SKELETON_FREMENNIK_4494, NpcID.SKELETON_FREMENNIK_4495,
                NpcID.SKELETON_FREMENNIK_4496, NpcID.SKELETON_FREMENNIK_4497, NpcID.SKELETON_FREMENNIK_4498, NpcID.SKELETON_FREMENNIK_4499);

        pickUpKey = new GroundItemStep( key);

        searchPainting = new ObjectStep( ObjectID.PICTURE_WALL, new RSTile(2707, 10147, 0), "Search the picture wall in the north room.");

        //TODO fix
       // doPuzzle = new PaintingWall(this);

     ///   pickUpItems = new DetailedQuestStep( "Pick up 2 rotten barrels and 6 ropes from around the room.", rottenBarrels2, ropes6);
     //   pickUpItems2 = new DetailedQuestStep( "Pick up 1 rotten barrels and 3 ropes from around the room.", rottenBarrel, ropes3);
        pickUpItems.addSubSteps(pickUpItems2);

        useBarrel = new ObjectStep( ObjectID.WALKWAY, new RSTile(2722, 10168, 0), "WALK onto the walkway to the east, and use a barrel on it to repair it.", rottenBarrel, ropes3);
        useBarrel2 = new ObjectStep( ObjectID.WALKWAY_23214, new RSTile(2724, 10168, 0), "WALK on the walkway and repair the next hole in it.", rottenBarrel, ropes3);

        openGate = new ObjectStep( ObjectID.GATE_23216, new RSTile(2725, 10168, 0), "Open the gate on the walkway, clicking the key hole which matches your key.", key);

     //   chooseSquare = new WidgetStep( "Click the square key hole.", 252, 3);
     //   chooseCross = new WidgetStep( "Click the cross key hole.", 252, 4);
      //  chooseTriangle = new WidgetStep( "Click the triangle key hole.", 252, 5);
     //   chooseCircle = new WidgetStep( "Click the circle key hole.", 252, 6);
     //   chooseStar = new WidgetStep( "Click the star key hole.", 252, 7);
        openGate.addSubSteps(chooseCircle, chooseCross, chooseSquare, chooseStar, chooseTriangle);

        searchChest = new ObjectStep( ObjectID.CHEST_14197, new RSTile(2740, 10164, 0), "WALK off the remaining walkway, and search the chest in the wreck. Be prepared to fight Ulfric.");
        searchChest.addAlternateObjects(ObjectID.CHEST_14196);

        killUlfric = new NPCStep( NpcID.ULFRIC, new RSTile(2740, 10164, 0), "Kill Ulfric.");

        searchChestAgain = new ObjectStep( ObjectID.CHEST_14197, new RSTile(2740, 10164, 0), "Search the chest again to finish the quest.");
        searchChestAgain.addAlternateObjects(ObjectID.CHEST_14196);
    }


    public List<ItemRequirement> getItemRequirements()
    {
        return Arrays.asList(axe, tinderbox, spade);
    }


    public List<ItemRequirement> getItemRecommended()
    {
        return Arrays.asList(combatGear, food, prayerPotions);
    }


    public List<String> getCombatRequirements()
    {
        return Arrays.asList("Skeleton fremennik (level 40)", "Ulfric (level 100)");
    }

    public List<Requirement> getGeneralRequirements() {
        ArrayList<Requirement> req = new ArrayList<>();
        //req.add(new QuestRequirement(QuestHelperQuest.THE_FREMENNIK_TRIALS, QuestState.FINISHED));
        req.add(new SkillRequirement(Skills.SKILLS.FIREMAKING, 40));
        req.add(new SkillRequirement(Skills.SKILLS.WOODCUTTING, 50));
        return req;
    }




    @Override
    public Priority priority() {
        return null;
    }

    @Override
    public boolean validate() {
        return false;
    }

    @Override
    public void execute() {

    }

    @Override
    public String questName() {
        return null;
    }

    @Override
    public boolean checkRequirements() {
        return false;
    }

    @Override
    public List<ItemRequirement> getBuyList() {
        return null;
    }

    @Override
    public boolean isComplete() {
        return Quest.OLAFS_QUEST.getState().equals(Quest.State.COMPLETE);
    }
}
