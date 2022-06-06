package scripts.QuestPackages.GettingAhead;

import com.allatori.annotations.DoNotRename;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.ChatScreen;
import org.tribot.script.sdk.Inventory;
import org.tribot.script.sdk.Quest;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.icthlarinslittlehelper.Icthlarinslittlehelper;
import scripts.QuestSteps.*;
import scripts.Requirements.*;
import scripts.Requirements.Items.ItemCollections;
import scripts.Requirements.Util.ConditionalStep;
import scripts.Requirements.Util.Conditions;
import scripts.Requirements.Util.LogicType;
import scripts.Tasks.Priority;

import java.util.*;

public class GettingAhead implements QuestTask {

    private static GettingAhead quest;

    public static GettingAhead get() {
        return quest == null ? quest = new GettingAhead() : quest;
    }

    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.BEAR_FUR, 1, 500),
                    new GEItem(ItemID.SOFT_CLAY, 1, 50),
                    new GEItem(ItemID.STAMINA_POTION[0], 2, 15),
                    new GEItem(ItemID.SKILLS_NECKLACE[2], 1, 25),
                    new GEItem(ItemID.SAW, 1, 250),
                    new GEItem(ItemID.RING_OF_WEALTH[0], 1, 25),
                    new GEItem(ItemID.HAMMER, 1, 250),
                    new GEItem(ItemID.KNIFE, 1, 250),
                    new GEItem(ItemID.RED_DYE, 1, 250),
                    new GEItem(ItemID.POT_OF_FLOUR, 1, 250),
                    new GEItem(ItemID.NEEDLE, 1, 250),
                    new GEItem(ItemID.THREAD, 1, 250),
                    new GEItem(ItemID.PLANK, 2, 25),
                    new GEItem(ItemID.STEEL_NAILS, 40, 50)
            )
    );

    InventoryRequirement initialItemReqs = new InventoryRequirement(new ArrayList<>(
            Arrays.asList(
                    new ItemReq(ItemID.BEAR_FUR, 1),
                    new ItemReq(ItemID.SOFT_CLAY, 1),
                    new ItemReq(ItemID.STAMINA_POTION[0], 2, 0),
                    new ItemReq(ItemID.SAW, 1),
                    new ItemReq(ItemID.HAMMER, 1),
                    new ItemReq(ItemID.KNIFE, 1),
                    new ItemReq(ItemID.RED_DYE, 1),
                    new ItemReq(ItemID.POT_OF_FLOUR, 1),
                    new ItemReq(ItemID.NEEDLE, 1),
                    new ItemReq(ItemID.THREAD, 1),
                    new ItemReq(ItemID.PLANK, 2),
                    new ItemReq(ItemID.STEEL_NAILS, 40, 6),
                    new ItemReq(ItemID.SKILLS_NECKLACE[2], 1, 0),
                    new ItemReq(ItemID.RING_OF_WEALTH[0], 1, 0, true)
            )
    ));

    BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);

    public void buyItems() {
        cQuesterV2.status = "Buying items";
        if (!initialItemReqs.check()) {
            buyStep.buyItems();
            initialItemReqs.withdrawItems();
        }
    }

    //Items Required
    ItemRequirement bearFur, softClay, hammer, saw, planks, nails, knife, redDye, potOfFlour, needle, thread;

    //Items Recommended
    ItemRequirement food, staminaPotions, combatGear, skillsNeck, clayHead, clayHeadHighlighted, furHead, furHeadHighlighted, bloodyHead;

    //Other items used
    ItemRequirement clay, pickaxe, bucket, bucketOfWater, itemsTip;

    //NPC Steps
    @DoNotRename
    QuestStep talkToGordon1, talkToMary, talkToGordon2, talkToMary2, talkToGordonGen, talkToGordonGen2, talkToGordonGen3, talkToGordonFinal;

    //Object/items Steps
    @DoNotRename
    QuestStep usePotOfFlour, goToMine, leaveCave, killBeast, useKnifeOnClay, useFurOnHead, useDyeOnHead, buildBearHead;

    //Getting items steps
    @DoNotRename
    QuestStep goUpstairsHouse, takePot, goDownstairsHouse, getNeedle, getThread, takePickaxe, mineClay, takeKnife, fillBucket, wetClay, takeBucket, takeDye,
            takeNails, takeSaw, takeHammer, takePlanks;
    @DoNotRename
    ConditionalStep goUseFlourOnGate, returnToGordon, makeClayHead, addFurToHead, dyeHead, putUpHead;

    //Conditions
    Requirement inMine, inUpstairsHouse;

    //RSArea()s
    RSArea kebosMine, upstairsHouse;


    public Map<Integer, QuestStep> loadSteps() {
        loadRSArea();
        setupItemRequirements();
        setupConditions();
        setupSteps();
        Map<Integer, QuestStep> steps = new HashMap<>();

        steps.put(0, talkToGordon1);
        steps.put(2, talkToMary);

        goUseFlourOnGate = new ConditionalStep(goUpstairsHouse);
        goUseFlourOnGate.addStep(new Conditions(inUpstairsHouse, potOfFlour), goDownstairsHouse);
        goUseFlourOnGate.addStep(new Conditions(potOfFlour), usePotOfFlour);
        goUseFlourOnGate.addStep(inUpstairsHouse, takePot);
        steps.put(4, goUseFlourOnGate);

        ConditionalStep killBeastStep = new ConditionalStep(goToMine);
        killBeastStep.addStep(inMine, killBeast);
        steps.put(6, killBeastStep);
        steps.put(8, killBeastStep);
        steps.put(10, killBeastStep);
        steps.put(12, killBeastStep);

        returnToGordon = new ConditionalStep(takePickaxe);
        returnToGordon.addStep(inMine, leaveCave);
        returnToGordon.addStep(new Conditions(new Conditions(LogicType.OR, clay, softClay), planks),
                talkToGordon2);
        returnToGordon.addStep(new Conditions(LogicType.OR, clay, softClay), takePlanks);
        returnToGordon.addStep(pickaxe, mineClay);

        steps.put(14, returnToGordon);
        steps.put(16, talkToMary2);

        makeClayHead = new ConditionalStep(takePickaxe);
        makeClayHead.addStep(new Conditions(softClay, knife), useKnifeOnClay);
        makeClayHead.addStep(softClay, takeKnife);
        makeClayHead.addStep(new Conditions(bucketOfWater, clay), wetClay);
        makeClayHead.addStep(new Conditions(bucket, clay), fillBucket);
        makeClayHead.addStep(clay, takeBucket);
        makeClayHead.addStep(pickaxe, mineClay);
        steps.put(18, makeClayHead);

        steps.put(20, talkToGordonGen);
        addFurToHead = new ConditionalStep(goUpstairsHouse);
        addFurToHead.addStep(new Conditions(thread, needle), useFurOnHead);
        addFurToHead.addStep(new Conditions(inUpstairsHouse, needle), getThread);
        addFurToHead.addStep(inUpstairsHouse, getNeedle);
        steps.put(22, addFurToHead);
        steps.put(24, talkToGordonGen2);

        dyeHead = new ConditionalStep(takeDye);
        dyeHead.addStep(redDye, useDyeOnHead);
        steps.put(26, dyeHead);
        steps.put(28, talkToGordonGen3);

        putUpHead = new ConditionalStep(takePlanks);
        putUpHead.addStep(new Conditions(planks, hammer, saw, nails), buildBearHead);
        putUpHead.addStep(new Conditions(planks, hammer, saw), takeNails);
        putUpHead.addStep(new Conditions(planks, hammer), takeSaw);
        putUpHead.addStep(planks, takeHammer);
        steps.put(30, putUpHead);
        steps.put(32, talkToGordonFinal);

        return steps;
    }

    public void setupItemRequirements() {
        itemsTip = new ItemRequirement("You can get all the required items during the quest.", -1, -1);

        //Recommended
        combatGear = new ItemRequirement("Combat gear", -1, -1);

        food = new ItemRequirement(ItemCollections.getGoodEatingFood(), -1);
        staminaPotions = new ItemRequirement("Stamina Potion", ItemCollections.getStaminaPotions());
        skillsNeck = new ItemRequirement("Skills Necklace", ItemCollections.getSkillsNecklaces());

        //Required
        bearFur = new ItemRequirement("Bear Fur", ItemID.BEAR_FUR);
        // bearFur.canBeObtainedDuringQuest();
        // bearFur.setTooltip("You can kill a bear west of the farm for some fur");

        softClay = new ItemRequirement("Soft Clay", ItemID.SOFT_CLAY, 1);
        //softClay.canBeObtainedDuringQuest();

        hammer = new ItemRequirement(ItemCollections.getHammer(), 1);
        //hammer.canBeObtainedDuringQuest();
        saw = new ItemRequirement("Any saw", ItemID.SAW, 1);
        // saw.canBeObtainedDuringQuest();
        saw.addAlternateItemIDs(ItemID.CRYSTAL_SAW, ItemID.AMYS_SAW);
        planks = new ItemRequirement("Planks", ItemID.PLANK, 2);
        //   planks.canBeObtainedDuringQuest();
        nails = new ItemRequirement(ItemCollections.getNails(), 6);
        // nails.canBeObtainedDuringQuest();
        knife = new ItemRequirement("Knife", ItemID.KNIFE, 1);
        //knife.canBeObtainedDuringQuest();

        redDye = new ItemRequirement("Red Dye", ItemID.RED_DYE, 1);
        //  redDye.canBeObtainedDuringQuest();

        potOfFlour = new ItemRequirement("Pot of Flour", ItemID.POT_OF_FLOUR, 1);
        // potOfFlour.canBeObtainedDuringQuest();

        needle = new ItemRequirement("Needle", ItemID.NEEDLE, 1);
        //needle.canBeObtainedDuringQuest();
        thread = new ItemRequirement("Thread", ItemID.THREAD, 1);
        // thread.canBeObtainedDuringQuest();
        pickaxe = new ItemRequirement("Any pickaxe", ItemCollections.getPickaxes());
        // pickaxe.canBeObtainedDuringQuest();
        clay = new ItemRequirement("Clay", ItemID.CLAY);
        // clay.canBeObtainedDuringQuest();

        bucket = new ItemRequirement("Bucket", ItemID.BUCKET);
        //  bucket.canBeObtainedDuringQuest();

        bucketOfWater = new ItemRequirement("Bucket of water", ItemID.BUCKET_OF_WATER);
        //  bucketOfWater.canBeObtainedDuringQuest();


        //Making the fake head
        clayHead = new ItemRequirement("Clay Head", ItemID.CLAY_HEAD);
        // clayHead.setTooltip("You can make another by using a knife on some soft clay");

        clayHeadHighlighted = new ItemRequirement("Clay Head", ItemID.CLAY_HEAD);
        // clayHeadHighlighted.setTooltip("You can make another by using a knife on some soft clay");

        furHead = new ItemRequirement("Fur Head", ItemID.FUR_HEAD);
        // furHead.setTooltip("You can make another by using a knife on soft clay, then adding bear fur");
        furHeadHighlighted = new ItemRequirement("Fur Head", ItemID.FUR_HEAD);
        //   furHeadHighlighted.setTooltip("You can make another by using a knife on soft clay, then adding bear fur");

        bloodyHead = new ItemRequirement("Bloody Head", ItemID.BLOODY_HEAD);
    }

    public void setupConditions() {
        inMine = new AreaRequirement(kebosMine);
        inUpstairsHouse = new AreaRequirement(upstairsHouse);
    }

    public void loadRSArea() {
        kebosMine = new RSArea(new RSTile(1174, 10000, 0), new RSTile(1215, 10035, 0));
        upstairsHouse = new RSArea(new RSTile(1238, 3677, 1), new RSTile(1244, 3687, 1));
    }

    public void setupSteps() {
        //Starting Off
        talkToGordon1 = new NPCStep(NpcID.GORDON, new RSTile(1248, 3686, 0), "Talk to Gordon south of the Farming Guild.");
        talkToGordon1.addDialogStep("Need a hand with anything?");
        talkToGordon1.addDialogStep("Yes.");

        talkToMary = new NPCStep(NpcID.MARY_10502, new RSTile(1237, 3678, 0), "Talk to Mary inside the main building, west of Gordon.");

        //Killing the beast
        goUpstairsHouse = new ObjectStep(ObjectID.STAIRCASE_34502, new RSTile(1240, 3686, 0),
                "Climb-up");
        takePot = new GroundItemStep(ItemID.POT_OF_FLOUR, new RSTile(1240, 3686, 0), potOfFlour);
        goDownstairsHouse = new ObjectStep(ObjectID.STAIRCASE_34503, new RSTile(1240, 3686, 1),
                "Climb-down");

        usePotOfFlour = new ObjectStep(NullObjectID.NULL_40427, new RSTile(1257, 3686, 0), "", potOfFlour);

        goToMine = new ObjectStep(ObjectID.CAVE_20852, new RSTile(1212, 3647, 0),
                "Enter the Kebos Lowlands mine just west of the bridge and kill the Headless Beast (level 82).");
        goToMine.addDialogStep("Yes.");
        killBeast = new NPCStep(NpcID.HEADLESS_BEAST_10506, new RSTile(1191, 10021, 0), "Kill the headless " +
                "beast. You can safespot it from the south west corner of the pond in the cave.");
        // ((NPCStep()) killBeast).addSafeSpots(new RSTile(1190, 10017, 0));
        goToMine.addSubSteps(killBeast);

        talkToGordon2 = new NPCStep(NpcID.GORDON, new RSTile(1248, 3686, 0), "");
        leaveCave = new ObjectStep(ObjectID.CAVE_20853, new RSTile(1190, 10029, 0), "Leave the cave.");
        talkToGordon2.addSubSteps(leaveCave);

        //Making the fake head
        talkToGordonGen = new NPCStep(NpcID.GORDON, new RSTile(1248, 3686, 0), clayHead);
        talkToGordonGen2 = new NPCStep(NpcID.GORDON, new RSTile(1248, 3686, 0), furHead);
        talkToGordonGen3 = new NPCStep(NpcID.GORDON, new RSTile(1248, 3686, 0), bloodyHead);

        talkToMary2 = new NPCStep(NpcID.MARY_10502, new RSTile(1237, 3678, 0), "Talk to Mary inside the house.");
        takePickaxe = new ObjectStep(ObjectID.ROCKS_40365, new RSTile(1222, 3653, 0),
                "Take the pickaxe in the mine.");
        mineClay = new ObjectStep(ObjectID.ROCKS_11362, new RSTile(1217, 3657, 0),
                "Mine", pickaxe);
        takeKnife = new GroundItemStep(ItemID.KNIFE, new RSTile(1241, 3679, 0),
                knife);
        takeBucket = new GroundItemStep(ItemID.BUCKET, new RSTile(1244, 3682, 0), bucket);

        fillBucket = new ObjectStep(ObjectID.SINK_1763, new RSTile(1240, 3677, 0), "Fill the bucket on the sink.", bucket);
        wetClay = new UseItemOnItemStep(ItemID.BUCKET_OF_WATER, ItemID.CLAY,
                !Inventory.contains(ItemID.CLAY), bucketOfWater, clay);
        useKnifeOnClay = new UseItemOnItemStep(ItemID.KNIFE,
                ItemID.SOFT_CLAY, !Inventory.contains(ItemID.SOFT_CLAY), knife, softClay);
        useKnifeOnClay.addDialogStep("Yes.");

        getNeedle = new GroundItemStep(ItemID.NEEDLE, new RSTile(1244, 3685, 1),
                needle);
        getThread = new GroundItemStep(ItemID.THREAD, new RSTile(1244, 3684, 1),
                thread);

        useFurOnHead = new UseItemOnItemStep(ItemID.BEAR_FUR, ItemID.CLAY_HEAD,
                !Inventory.contains(ItemID.BEAR_FUR),
                bearFur, clayHeadHighlighted, thread, needle);
        useFurOnHead.addDialogStep("Yes.");

        takeDye = new ObjectStep(ObjectID.SHELVES_40362, new RSTile(1240, 3688, 0), "Get some red dye from the shelves in the farm house.");
        takeDye.addDialogStep("Take some red dye.");
        useDyeOnHead = new UseItemOnItemStep(ItemID.RED_DYE, ItemID.FUR_HEAD,
                !Inventory.contains(ItemID.RED_DYE), redDye, furHeadHighlighted);
        useDyeOnHead.addDialogStep("Yes.");
        useDyeOnHead.addSubSteps(talkToGordonGen3);

        takePlanks = new GroundItemStep(ItemID.PLANK, new RSTile(1202, 3649, 0), planks);
        takeSaw = new GroundItemStep(ItemID.SAW, new RSTile(1239, 3696, 0), saw);
        takeHammer = new GroundItemStep(ItemID.HAMMER, new RSTile(1259, 3686, 0), hammer);
        takeNails = new ObjectStep(ObjectID.WORKBENCH_40364, new RSTile(1239, 3699, 0),
                "Take");
        takeNails.addDialogStep("Take the nails.");
        buildBearHead = new ObjectStep(NullObjectID.NULL_20858, new RSTile(1240, 3683, 0),
                "", bloodyHead, nails, planks, saw, hammer);
        buildBearHead.addDialogStep("Yes.");
        talkToGordonFinal = new NPCStep(NpcID.GORDON, new RSTile(1248, 3686, 0),
                "Return to Gordon to finish the quest.");
    }


    public List<ItemRequirement> getItemRequirements() {
        return Arrays.asList(itemsTip, bearFur, softClay, hammer, saw, planks, nails, knife, redDye, potOfFlour, needle, thread);
    }


    public List<ItemRequirement> getItemRecommended() {
        return Arrays.asList(food, staminaPotions, combatGear, skillsNeck);
    }


    public List<String> getCombatRequirements() {
        return Collections.singletonList("Headless Beast (level 82, safespottable)");
    }


    public List<Requirement> getGeneralRequirements() {
        List<Requirement> requirements = new ArrayList<>();
        requirements.add(new SkillRequirement(Skills.SKILLS.CRAFTING, 30));
        requirements.add(new SkillRequirement(Skills.SKILLS.CONSTRUCTION, 26));
        return requirements;
    }

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
        if (isComplete() || !checkRequirements()) {
            cQuesterV2.taskList.remove(this);
            return;
        }
        if (Quest.GETTING_AHEAD.getStep() == 0) {
            buyItems();
        }
        int varbit = QuestVarbits.QUEST_GETTING_AHEAD.getId();
        Map<Integer, QuestStep> steps = loadSteps();
        Optional<QuestStep> step = Optional.ofNullable(steps.get(Utils.getVarBitValue(varbit)));
        cQuesterV2.status = step.map(s -> s.toString()).orElse("Unknown Step Name");
        step.ifPresent(QuestStep::execute);

        if (ChatScreen.isOpen()) {
            NpcChat.handle();
        }
    }

    @Override
    public String questName() {
        int varbit = QuestVarbits.QUEST_GETTING_AHEAD.getId();
        return "Getting Ahead (" + Utils.getVarBitValue(varbit) + ")";
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
        return Quest.GETTING_AHEAD.getState().equals(Quest.State.COMPLETE);
    }
}
