package scripts.QuestPackages.TempleOfIkov;

import dax.walker_engine.interaction_handling.NPCInteraction;
import org.apache.commons.lang3.ArrayUtils;
import org.tribot.api2007.*;
import org.tribot.api2007.Objects;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;

import org.tribot.script.sdk.GameState;
import org.tribot.script.sdk.Quest;
import org.tribot.script.sdk.Waiting;
import scripts.*;

import scripts.GEManager.GEItem;
import scripts.QuestSteps.*;
import scripts.Requirements.*;
import scripts.Requirements.Items.ItemCollections;
import scripts.Requirements.Items.ItemRequirements;
import scripts.Requirements.Util.ConditionalStep;
import scripts.Requirements.Util.Conditions;
import scripts.Requirements.Util.LogicType;
import scripts.Requirements.Util.Operation;
import scripts.Tasks.Priority;
import scripts.Timer;

import java.util.*;

public class TempleOfIkov implements QuestTask {

    private static TempleOfIkov quest;

    public static TempleOfIkov get() {
        return quest == null ? quest = new TempleOfIkov() : quest;
    }

    //Items Required
    ItemRequirement pendantOfLucien, bootsOfLightness, limpwurt20, yewOrBetterBow, knife, lightSource, lever, iceArrows20, iceArrows, shinyKey,
            armadylPendant, staffOfArmadyl, pendantOfLucienEquipped, bootsOfLightnessEquipped, iceArrowsEquipped;

    Requirement emptyInventorySpot;

    Requirement belowMinus1Weight, below4Weight, inEntryRoom, inNorthRoom, inBootsRoom, dontHaveBoots, inMainOrNorthRoom,
            leverNearby, pulledLever, inArrowRoom, hasEnoughArrows, lesNearby, inLesRoom, inWitchRoom, inDemonArea,
            inArmaRoom;

    QuestStep talkToLucien, prepare, prepareBelow0, enterDungeonForBoots, enterDungeon, goDownToBoots, getBoots, goUpFromBoots, pickUpLever,
            useLeverOnHole, pullLever, enterArrowRoom, returnToMainRoom, goSearchThievingLever, goPullThievingLever, fightLes, tryToEnterWitchRoom,
            enterDungeonKilledLes, enterLesDoor, giveWineldaLimps, talkToWinelda, enterDungeonGivenLimps, enterFromMcgrubbors, pickUpKey, pushWall,
            makeChoice, killLucien, bringStaffToLucien, pickUpStaff;

    ObjectStep collectArrows;


    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.LIMPWURT_ROOT, 20, 100),
                    new GEItem(ItemID.MANTA_RAY, 5, 30),
                    new GEItem(ItemID.MAGIC_SHORTBOW, 1, 100),
                    new GEItem(ItemID.STAMINA_POTION[0], 3, 15),
                    new GEItem(ItemID.AMULET_OF_GLORY4, 1, 30),
                    new GEItem(ItemID.CAMELOT_TELEPORT, 5, 30),
                    new GEItem(ItemID.KNIFE, 1, 500),
                    new GEItem(ItemID.VARROCK_TELEPORT, 10, 40),
                    new GEItem(ItemID.CANDLE, 1, 400),
                    new GEItem(ItemID.TINDERBOX, 1, 400),
                    new GEItem(ItemID.RING_OF_WEALTH[0], 1, 25)
            )
    );

    InventoryRequirement initialItemReqs = new InventoryRequirement(new ArrayList<>(
            Arrays.asList(
                    new ItemReq(ItemID.LIMPWURT_ROOT, 20, 20),
                    new ItemReq(ItemID.CAMELOT_TELEPORT, 5, 1),
                    new ItemReq(ItemID.MAGIC_SHORTBOW, 1, 1, true, true),
                    new ItemReq(ItemID.AMULET_OF_GLORY4, 1, 0, true, true),
                    new ItemReq(ItemID.STAMINA_POTION[0], 1, 0),
                    new ItemReq(ItemID.KNIFE, 1, 1, true),
                    new ItemReq(ItemID.LIT_CANDLE, 1, 1),
                    new ItemReq(ItemID.TINDERBOX, 1, 1),
                    new ItemReq(ItemID.RING_OF_WEALTH[0], 1, 0, true)
            )
    ));

    BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);

    public Map<Integer, QuestStep> loadSteps() {
        setupItemRequirements();
        setupConditions();
        setupSteps();
        Map<Integer, QuestStep> steps = new HashMap<>();

        steps.put(0, talkToLucien);

        ConditionalStep getLeverPiece = new ConditionalStep(prepare);
        getLeverPiece.addStep(new Conditions(iceArrows, inMainOrNorthRoom), goSearchThievingLever);
        getLeverPiece.addStep(new Conditions(hasEnoughArrows, inArrowRoom), returnToMainRoom);
        getLeverPiece.addStep(inArrowRoom, collectArrows);
        getLeverPiece.addStep(new Conditions(inMainOrNorthRoom, pulledLever), enterArrowRoom);
        getLeverPiece.addStep(leverNearby, pullLever);
        getLeverPiece.addStep(new Conditions(inMainOrNorthRoom, lever), useLeverOnHole);
        getLeverPiece.addStep(new Conditions(inBootsRoom, lever), goUpFromBoots);
        getLeverPiece.addStep(lever, enterDungeon);

        getLeverPiece.addStep(new Conditions(inMainOrNorthRoom, belowMinus1Weight), pickUpLever);
        getLeverPiece.addStep(new Conditions(inBootsRoom, new Conditions(LogicType.OR, belowMinus1Weight, bootsOfLightness)), goUpFromBoots);
        getLeverPiece.addStep(inBootsRoom, getBoots);
        getLeverPiece.addStep(new Conditions(inMainOrNorthRoom, below4Weight, dontHaveBoots), goDownToBoots);
        getLeverPiece.addStep(belowMinus1Weight, enterDungeon);
        getLeverPiece.addStep(new Conditions(bootsOfLightness), prepareBelow0);
        getLeverPiece.addStep(below4Weight, enterDungeonForBoots);

        steps.put(10, getLeverPiece);

        ConditionalStep pullLeverForLes = new ConditionalStep(prepare);
        pullLeverForLes.addStep(new Conditions(iceArrows, inMainOrNorthRoom), goPullThievingLever);
        pullLeverForLes.addStep(new Conditions(hasEnoughArrows, inArrowRoom), returnToMainRoom);
        pullLeverForLes.addStep(inArrowRoom, collectArrows);
        pullLeverForLes.addStep(leverNearby, pullLever);
        pullLeverForLes.addStep(new Conditions(inMainOrNorthRoom, lever), useLeverOnHole);
        pullLeverForLes.addStep(new Conditions(inBootsRoom, lever), goUpFromBoots);
        pullLeverForLes.addStep(lever, enterDungeon);

        pullLeverForLes.addStep(new Conditions(inMainOrNorthRoom, belowMinus1Weight), pickUpLever);
        pullLeverForLes.addStep(new Conditions(inBootsRoom, new Conditions(bootsOfLightness)), goUpFromBoots);
        pullLeverForLes.addStep(inBootsRoom, getBoots);
        pullLeverForLes.addStep(new Conditions(inMainOrNorthRoom, below4Weight, dontHaveBoots), goDownToBoots);
        pullLeverForLes.addStep(belowMinus1Weight, enterDungeon);
        pullLeverForLes.addStep(new Conditions( bootsOfLightness), prepareBelow0);
        pullLeverForLes.addStep(below4Weight, enterDungeonForBoots);

        steps.put(20, pullLeverForLes);

        ConditionalStep goFightLes = new ConditionalStep(prepare);
        goFightLes.addStep(new Conditions(inLesRoom, lesNearby), fightLes);
        goFightLes.addStep(new Conditions(iceArrows, inMainOrNorthRoom), tryToEnterWitchRoom);
        goFightLes.addStep(new Conditions(hasEnoughArrows, inArrowRoom), returnToMainRoom);
        goFightLes.addStep(inArrowRoom, collectArrows);
        goFightLes.addStep(leverNearby, pullLever);
        goFightLes.addStep(new Conditions(inMainOrNorthRoom, lever), useLeverOnHole);
        goFightLes.addStep(new Conditions(inBootsRoom, lever), goUpFromBoots);
        goFightLes.addStep(lever, enterDungeon);

        goFightLes.addStep(new Conditions(inMainOrNorthRoom, belowMinus1Weight), pickUpLever);
        goFightLes.addStep(new Conditions(inBootsRoom, new Conditions(LogicType.OR, belowMinus1Weight, bootsOfLightness)), goUpFromBoots);
        goFightLes.addStep(inBootsRoom, getBoots);
        goFightLes.addStep(new Conditions(inMainOrNorthRoom,  dontHaveBoots), goDownToBoots);
        goFightLes.addStep(belowMinus1Weight, enterDungeon);
        goFightLes.addStep(new Conditions( bootsOfLightness), prepareBelow0);
        goFightLes.addStep(below4Weight, enterDungeonForBoots);

        steps.put(30, goFightLes);

        ConditionalStep goToWitch = new ConditionalStep(enterDungeonKilledLes);
        goToWitch.addStep(inWitchRoom, giveWineldaLimps);
        goToWitch.addStep(inMainOrNorthRoom, enterLesDoor);
        steps.put(40, goToWitch);
        steps.put(50, goToWitch);

        // TODO: Verify taking staff doesn't progress quest beyond varp 26 = 60
        ConditionalStep goodOrBadPath = new ConditionalStep(enterDungeonGivenLimps);
        goodOrBadPath.addStep(staffOfArmadyl, bringStaffToLucien);
        goodOrBadPath.addStep(new Conditions(inArmaRoom, shinyKey), pickUpStaff);
        goodOrBadPath.addStep(new Conditions(inDemonArea, shinyKey), pushWall);
        goodOrBadPath.addStep(new Conditions(LogicType.OR, inArmaRoom, inDemonArea), pickUpKey);
        goodOrBadPath.addStep(new Conditions(LogicType.OR, inMainOrNorthRoom, inWitchRoom), talkToWinelda);
        goodOrBadPath.addStep(shinyKey, enterFromMcgrubbors);

        steps.put(60, goodOrBadPath);

        steps.put(70, killLucien);
        // Sided against Lucien, quest ends at varp 80

        return steps;
    }

    public void setupItemRequirements() {
        pendantOfLucien = new ItemRequirement("Pendant of lucien", ItemID.PENDANT_OF_LUCIEN);
        pendantOfLucienEquipped = new ItemRequirement(ItemID.PENDANT_OF_LUCIEN, 1, true);
        bootsOfLightness = new ItemRequirement( ItemID.BOOTS_OF_LIGHTNESS, 1, true, true);
        bootsOfLightnessEquipped = new ItemRequirement(ItemID.BOOTS_OF_LIGHTNESS, 1, true);
        limpwurt20 = new ItemRequirement("Limpwurt (unnoted)", ItemID.LIMPWURT_ROOT, 20);
        yewOrBetterBow = new ItemRequirement("Yew, magic, or dark bow", ItemID.MAGIC_SHORTBOW);
        yewOrBetterBow.addAlternateItemID(ItemID.YEW_LONGBOW, ItemID.YEW_COMP_BOW, ItemID.MAGIC_SHORTBOW, ItemID.MAGIC_SHORTBOW_I,
                ItemID.MAGIC_LONGBOW, ItemID.DARK_BOW);
        knife = new ItemRequirement("Knife to get the boots of lightness", ItemID.KNIFE);
        lightSource = new ItemRequirement(ItemID.LIT_CANDLE);

        iceArrows20 = new ItemRequirement("Ice arrows", ItemID.ICE_ARROWS, 20);

        iceArrows = new ItemRequirement("Ice arrows", ItemID.ICE_ARROWS);
        iceArrowsEquipped = new ItemRequirement(ItemID.ICE_ARROWS, 1, true);
        lever = new ItemRequirement("Lever", ItemID.LEVER);


        shinyKey = new ItemRequirement("Shiny key", ItemID.SHINY_KEY);

        armadylPendant = new ItemRequirement(ItemID.ARMADYL_PENDANT, 1, true, true);


        staffOfArmadyl = new ItemRequirement("Staff of Armadyl", ItemID.STAFF_OF_ARMADYL);

        // emptyInventorySpot = new FreeInventorySlotRequirement(InventoryID.INVENTORY, 1);
    }


    RSArea entryRoom1 = new RSArea(new RSTile(2647, 9803, 0), new RSTile(2680, 9814, 0));
    RSArea  entryRoom2 = new RSArea(new RSTile(2670, 9801, 0), new RSTile(2680, 9804, 0));
    RSArea   northRoom1 = new RSArea(new RSTile(2634, 9815, 0), new RSTile(2674, 9857, 0));
    RSArea  northRoom2 = new RSArea(new RSTile(2634, 9804, 0), new RSTile(2647, 9815, 0));
    RSArea   bootsRoom = new RSArea(new RSTile(2637, 9759, 0), new RSTile(2654, 9767, 0));
    RSArea   arrowRoom1 = new RSArea(new RSTile(2657, 9785, 0), new RSTile(2692, 9800, 0));
    RSArea   arrowRoom2 = new RSArea(new RSTile(2657, 9799, 0), new RSTile(2666, 9802, 0));
    RSArea   arrowRoom3 = new RSArea(new RSTile(2682, 9799, 0), new RSTile(2749, 9852, 0));
    RSArea   lesRoom = new RSArea(new RSTile(2639, 9858, 0), new RSTile(2651, 9870, 0));
    RSArea  witchRoom = new RSArea(new RSTile(2642, 9871, 0), new RSTile(2659, 9879, 0));

    RSArea  demonArea1 = new RSArea(new RSTile(2625, 9856, 0), new RSTile(2638, 9893, 0));
    RSArea  demonArea2 = new RSArea(new RSTile(2639, 9880, 0), new RSTile(2664, 9892, 0));
    RSArea   demonArea3 = new RSArea(new RSTile(2654, 9893, 0), new RSTile(2661, 9896, 0));
    RSArea   demonArea4 = new RSArea(new RSTile(2659, 9871, 0), new RSTile(2665, 9879, 0));

    RSArea  armaRoom1 = new RSArea(new RSTile(2633, 9894, 0), new RSTile(2651, 9914, 0));
    RSArea    armaRoom2 = new RSArea(new RSTile(2642, 9893, 0), new RSTile(2645, 9893, 0));


    public void setupConditions() {
        dontHaveBoots = new ItemRequirements(LogicType.NOR, bootsOfLightness);

        // below4Weight = new WeightRequirement(4, Operation.LESS_EQUAL);
        // belowMinus1Weight = new WeightRequirement(-1, Operation.LESS_EQUAL);
        inEntryRoom = new AreaRequirement(entryRoom1, entryRoom2);
        inNorthRoom = new AreaRequirement(northRoom1, northRoom2);
        inLesRoom = new AreaRequirement(lesRoom);
        inBootsRoom = new AreaRequirement(bootsRoom);
        inMainOrNorthRoom = new Conditions(LogicType.OR, inEntryRoom, inNorthRoom, inLesRoom);

        pulledLever = new Conditions(true, LogicType.OR, new WidgetTextRequirement(229, 1, "You hear the clunking of some hidden machinery."));
        leverNearby = new ObjectCondition(ObjectID.LEVER_87, new RSTile(2671, 9804, 0));
        inArrowRoom = new AreaRequirement(arrowRoom1, arrowRoom2, arrowRoom3);
        hasEnoughArrows = new Conditions(true, LogicType.OR, iceArrows20);
        //lesNearby = new NpcCondition(NpcID.FIRE_WARRIOR_OF_LESARKUS);
        inWitchRoom = new AreaRequirement(witchRoom);

        inDemonArea = new AreaRequirement(demonArea1, demonArea2, demonArea3, demonArea4);
        inArmaRoom = new AreaRequirement(armaRoom1, armaRoom2);
    }

    public void setupSteps() {
       // setupConditions();
        talkToLucien = new NPCStep(3444, new RSTile(2573, 3321, 0));
        talkToLucien.addDialogStep("I'm a mighty hero!", "That sounds like a laugh!");
        // prepare = new DetailedQuestStep(pendantOfLucienEquipped, limpwurt20, yewOrBetterBow);

        // prepareBelow0 = new DetailedQuestStep(pendantOfLucienEquipped, limpwurt20, yewOrBetterBow);
        //prepare.addSubSteps(prepareBelow0);

        enterDungeonForBoots = new ObjectStep(ObjectID.LADDER_17384, new RSTile(2677, 3405, 0),
                "Climb-down", entryRoom1.contains(Player.getPosition()),
                pendantOfLucienEquipped, knife, lightSource, limpwurt20, yewOrBetterBow);

        enterDungeon = new ObjectStep(ObjectID.LADDER_17384, new RSTile(2677, 3405, 0),
                "Climb-down", pendantOfLucienEquipped, yewOrBetterBow, limpwurt20);

        //  enterDungeon.addSubSteps(enterDungeonForBoots);
        goDownToBoots = ObjectStep.builder().objectId(ObjectID.STAIRS_98)
                .tile(new RSTile(2651, 9805, 0))
                .objectAction("Climb-down")
                .priorAction(()-> PathingUtil.blindWalkToTile(new RSTile(2650, 9805, 0)))
                .requirements(new Requirement[]{lightSource, knife}).build();

      //  goDownToBoots = new ObjectStep(ObjectID.STAIRS_98, new RSTile(2651, 9805, 0),
      //          "Climb-down", lightSource, knife);
        getBoots = new GroundItemStep(ItemID.BOOTS_OF_LIGHTNESS, new RSTile(2651, 9805, 0));
        goUpFromBoots = new ObjectStep(ObjectID.STAIRS_96, new RSTile(2639, 9764, 0),
                "Climb-up");

        pickUpLever = new GroundItemStep(ItemID.LEVER, new RSTile(2637, 9819, 0),
                //    "Cross the bridge in the north room and pick up the lever whilst weighing less than 0kg.",
                armadylPendant);
        useLeverOnHole = new UseItemOnObjectStep(ItemID.LEVER, ObjectID.LEVER_BRACKET, new RSTile(2671, 9804, 0),
                NPCInteraction.isConversationWindowUp(), lever);

        pullLever = new ObjectStep(ObjectID.LEVER_87, new RSTile(2671, 9804, 0), "Pull");

        enterArrowRoom = new ObjectStep(ObjectID.GATE_89, new RSTile(2662, 9803, 0), "Open");


        // collectArrows.addAlternateObjects(ObjectID.OPEN_CHEST);

        returnToMainRoom = new ObjectStep(ObjectID.GATE_89, new RSTile(2662, 9803, 0),
                "Open");

        goSearchThievingLever = new ObjectStep(ObjectID.LEVER_91, new RSTile(2665, 9855, 0),
                "Search");

        goPullThievingLever = new ObjectStep(ObjectID.LEVER_91, new RSTile(2665, 9855, 0),
                "Pull");

        //  goSearchThievingLever.addSubSteps(goPullThievingLever);

        tryToEnterWitchRoom = new ObjectStep(ObjectID.DOOR_93, new RSTile(2646, 9870, 0),
                "Open ", yewOrBetterBow, iceArrowsEquipped);

        fightLes = new NPCStep(NpcID.FIRE_WARRIOR_OF_LESARKUS,
                new RSTile(2646, 9866, 0),
                //   "Kill the Fire Warrior of Lesarkus. He can only be hurt by the ice arrows.",
                yewOrBetterBow, iceArrowsEquipped);

        enterDungeonKilledLes = new ObjectStep(ObjectID.LADDER_17384, new RSTile(2677, 3405, 0),
                "Climb-down", pendantOfLucienEquipped, limpwurt20);

        enterLesDoor = new ObjectStep(ObjectID.DOOR_93, new RSTile(2646, 9870, 0),
                "Open", pendantOfLucienEquipped, limpwurt20);

        // enterLesDoor.addSubSteps(enterDungeonKilledLes);

        giveWineldaLimps = new NPCStep("Winelda", new RSTile(2655, 9876, 0), limpwurt20);

        enterDungeonGivenLimps = new ObjectStep(ObjectID.LADDER_17384, new RSTile(2677, 3405, 0),
                "Enter the Temple of Ikov north of East Ardougne. If you got the shiny key, get it and enter via Mcgrubber's wood west of Seers' Village.", pendantOfLucienEquipped);

        enterFromMcgrubbors = new ObjectStep(ObjectID.LADDER_17384, new RSTile(2659, 3492, 0), "Enter the house at Mcgrubbor's wood and go down the ladder.", shinyKey);


        talkToWinelda = new NPCStep("Winelda", new RSTile(2655, 9876, 0));

        pickUpKey = new GroundItemStep(ItemID.SHINY_KEY, new RSTile(2628, 9859, 0));

        pushWall = new ObjectStep(ObjectID.WALL_1597, new RSTile(2643, 9892, 0),
                "Push");
        // pushWall.addSubSteps(enterDungeonGivenLimps, enterFromMcgrubbors);

        makeChoice = new NPCStep(NpcID.GUARDIAN_OF_ARMADYL_3446,  new RSTile(2638, 9907));
        makeChoice.addDialogStep("I seek the Staff of Armadyl.");
        makeChoice.addDialogStep("Lucien will give me a grand reward for it!");
        makeChoice.addDialogStep("You're right, it's time for my yearly bath.");
        makeChoice.addDialogStep("Ok! I'll help!");
        pickUpStaff = new GroundItemStep(ItemID.STAFF_OF_ARMADYL, new RSTile(2638, 9907));


        //   killLucien = new NPCStep(NpcID.LUCIEN, new RSTile(3122, 3484, 0), "Equip the Armadyl Pendant and kill Lucien in the house west of the Grand Exchange.", armadylPendant);
        bringStaffToLucien = new NPCStep("Lucien", new RSTile(3122, 3484, 0), staffOfArmadyl);
        bringStaffToLucien.addDialogStep("Yes! Here it is.");

    }


    RSTile[] chestTile = {
            new RSTile(2710, 9849, 0),
            new RSTile(2719, 9839, 0),
            new RSTile(2729, 9849, 0),
            new RSTile(2746, 9848, 0),
            new RSTile(2439, 9835, 0),
            new RSTile(2745, 9822, 0)};

    public void collectArrowsStep() {
        if (!hasEnoughArrows.check()) {
            if (!inArrowRoom.check()) {
                cQuesterV2.status = "Entering Arrow Room";
                enterArrowRoom.execute();
            } else {
                cQuesterV2.status = "Collecting Arrows";
                RSObject[] chests = Objects.findNearest(40, "Closed chest");
                for (RSTile chestTile : chestTile) {
                    if (PathingUtil.localNavigation(chestTile))
                        PathingUtil.movementIdle();
                    RSObject[] closed = Objects.findNearest(3, ObjectID.CLOSED_CHEST);
                    if (closed.length > 0 &&
                            Utils.clickObject(closed[0], "Open")) {
                        Timer.waitCondition(() -> Objects.findNearest(2, ObjectID.OPEN_CHEST).length > 0, 3500, 5000);
                    }
                    int currentArrow = Inventory.getCount(ItemID.ICE_ARROWS);
                    if (Utils.clickObj(ObjectID.OPEN_CHEST, "Search")) {
                        Timer.waitCondition(() -> Inventory.getCount(ItemID.ICE_ARROWS) > currentArrow,
                                2200, 3000);
                    }
                }
                ArrayUtils.reverse(chestTile);
            }
        } else if (inArrowRoom.check()) {
            // go to beginning
            PathingUtil.localNavigation(new RSTile(2710, 9849, 0));
        }
    }

    public List<ItemRequirement> getItemRequirements() {
        ArrayList<ItemRequirement> reqs = new ArrayList<>();
        reqs.add(yewOrBetterBow);
        reqs.add(limpwurt20);
        reqs.add(knife);
        reqs.add(lightSource);
        return reqs;
    }


    public List<String> getCombatRequirements() {
        ArrayList<String> reqs = new ArrayList<>();
        reqs.add("Able to survive multiple aggressive spiders (level 61) and demons (level 82)");
        reqs.add("Fire Warrior of Lesarkus (level 84) with ranged");
        reqs.add("If siding with Lucien, Guardian of Armadyl (level 43)");
        return reqs;
    }


    public List<Requirement> getGeneralRequirements() {
        ArrayList<Requirement> req = new ArrayList<>();
        req.add(new SkillRequirement(Skills.SKILLS.THIEVING, 42));
        req.add(new SkillRequirement(Skills.SKILLS.RANGED, 40));
        return req;
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
        if (GameState.getSetting(QuestVarPlayer.QUEST_TEMPLE_OF_IKOV.getId()) == 0 && !initialItemReqs.check()) {
            buyStep.buyItems();
            initialItemReqs.withdrawItems();
        }
        if (GameState.getSetting(QuestVarPlayer.QUEST_TEMPLE_OF_IKOV.getId()) == 90) {
            cQuesterV2.taskList.remove(this);
            return;
        }
        setupSteps();
        Map<Integer, QuestStep> steps = loadSteps();
        Optional<QuestStep> step =
                Optional.ofNullable(steps.get(GameState.getSetting(QuestVarPlayer.QUEST_TEMPLE_OF_IKOV.getId())));
        if (GameState.getSetting(QuestVarPlayer.QUEST_TEMPLE_OF_IKOV.getId()) == 10) {
            collectArrowsStep();
        }
        step.ifPresent(QuestStep::execute);
        if (NPCInteraction.isConversationWindowUp())
            NPCInteraction.handleConversation();

        Waiting.waitNormal(500, 50);
    }

    @Override
    public String questName() {
        return "Temple of Ikov (" + GameState.getSetting(QuestVarPlayer.QUEST_TEMPLE_OF_IKOV.getId()) + ")";
    }

    @Override
    public boolean checkRequirements() {
        List<Requirement> reqs = getGeneralRequirements();
        return reqs.stream().allMatch(Requirement::check);
    }


    @Override
    public List<ItemRequirement> getBuyList() {
        return null;
    }
    @Override
    public boolean isComplete() {
        return Quest.TEMPLE_OF_IKOV.getState().equals(Quest.State.COMPLETE);
    }
}

