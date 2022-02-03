package scripts.QuestPackages.ATailOfTwoCats;

import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.Game;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.Waiting;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.BlackKnightsFortress.BlackKnightsFortress;
import scripts.QuestSteps.*;
import scripts.Requirements.ItemRequirement;
import scripts.Requirements.Items.FollowerItemRequirement;
import scripts.Requirements.Items.ItemCollections;
import scripts.Requirements.Requirement;
import scripts.Requirements.Util.ConditionalStep;
import scripts.Requirements.Util.Conditions;
import scripts.Requirements.Util.Operation;
import scripts.Requirements.VarbitRequirement;
import scripts.Tasks.Priority;

import java.util.*;

public class ATailOfTwoCats implements QuestTask {

    private static ATailOfTwoCats quest;

    public static ATailOfTwoCats get() {
        return quest == null ? quest = new ATailOfTwoCats() : quest;
    }

    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.CABBAGE, 1, 300),
                    new GEItem(ItemID.IRON_CHAINBODY, 1, 300),
                    new GEItem(ItemID.BRONZE_MED_HELM, 1, 300),
                    new GEItem(ItemID.FALADOR_TELEPORT, 7, 30),
                    new GEItem(ItemID.LOBSTER, 10, 50),
                    new GEItem(ItemID.AMULET_OF_GLORY[2], 1, 30),
                    new GEItem(ItemID.COMBAT_BRACELET6, 1, 30),
                    new GEItem(ItemID.STAMINA_POTION[0], 2, 15),
                    new GEItem(ItemID.RING_OF_WEALTH[0], 1, 25)
            )
    );


    public void buyItems() {
        cQuesterV2.status = "Buying Items";
        General.println("[Debug]: " + cQuesterV2.status);
        if (Skills.getCurrentLevel(Skills.SKILLS.ATTACK) > 30) {
            itemsToBuy.add(new GEItem(ItemID.ADAMANT_SCIMITAR, 1, 300));
        } else if (Skills.getCurrentLevel(Skills.SKILLS.ATTACK) > 20) {
            itemsToBuy.add(new GEItem(ItemID.MITHRIL_SCIMITAR, 1, 300));
        } else {
            itemsToBuy.add(new GEItem(ItemID.IRON_SCIMITAR, 1, 300));
        }
        BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);
        buyStep.buyItems();

    }
    ItemRequirement catspeak, catspeakE, deathRune5, chocolateCake, logs, tinderbox, milk, shears,
            potatoSeed4, rake, dibber, vialOfWater, desertTop, desertBottom, hat, catspeakEWorn, cat;

    Requirement bobNearby, rakedPatch, madeBed, plantedSeed, placedLogs, litLogs, placedCake, placedMilk, usedShears, grownPotatoes;

    QuestStep talkToUnferth, talkToHild, findBob, talkToBob, talkToGertrude, talkToReldo, findBobAgain, talkToBobAgain, talkToSphinx, useRake, plantSeeds, makeBed, useLogsOnFireplace, lightLogs,
            useChocolateCakeOnTable, useMilkOnTable, useShearsOnUnferth, reportToUnferth, talkToApoth, talkToUnferthAsDoctor, findBobToFinish, talkToBobToFinish, talkToUnferthToFinish, waitForPotatoesToGrow;


    public Map<Integer, QuestStep> loadSteps()
    {
        setupItemRequirements();
        setupConditions();
        setupSteps();
        Map<Integer, QuestStep> steps = new HashMap<>();

        steps.put(0, talkToUnferth);

        steps.put(5, talkToHild);
        steps.put(10, talkToHild);

        ConditionalStep findbob1 = new ConditionalStep( findBob);
        findbob1.addStep(bobNearby, talkToBob);

        steps.put(15, findbob1);

        steps.put(20, talkToGertrude);

        steps.put(25, talkToReldo);
        steps.put(28, talkToReldo);

        ConditionalStep findbob2 = new ConditionalStep( findBobAgain);
        findbob2.addStep(bobNearby, talkToBobAgain);

        steps.put(30, findbob2);

        steps.put(35, talkToSphinx);

        ConditionalStep doChores = new ConditionalStep( useRake);
        doChores.addStep(new Conditions(plantedSeed, madeBed, litLogs, placedMilk, usedShears), waitForPotatoesToGrow);
        doChores.addStep(new Conditions(plantedSeed, madeBed, litLogs, placedMilk), useShearsOnUnferth);
        doChores.addStep(new Conditions(plantedSeed, madeBed, litLogs, placedCake), useMilkOnTable);
        doChores.addStep(new Conditions(plantedSeed, madeBed, litLogs), useChocolateCakeOnTable);
        doChores.addStep(new Conditions(plantedSeed, madeBed, placedLogs), lightLogs);
        doChores.addStep(new Conditions(plantedSeed, madeBed), useLogsOnFireplace);
        doChores.addStep(plantedSeed, makeBed);
        doChores.addStep(rakedPatch, plantSeeds);

        steps.put(40, doChores);

        steps.put(45, reportToUnferth);

        steps.put(50, talkToApoth);

        steps.put(55, talkToUnferthAsDoctor);

        ConditionalStep findbob3 = new ConditionalStep( findBobToFinish);
        findbob3.addStep(bobNearby, talkToBobToFinish);
        steps.put(60, findbob3);

        steps.put(65, talkToUnferthToFinish);

        return steps;
    }

    public void setupItemRequirements() {
        catspeak = new ItemRequirement( ItemID.CATSPEAK_AMULET, 1, true);
       // catspeak.setTooltip("You can get another from the Sphinx in Sophanem");
        catspeakE = new ItemRequirement("Catspeak amulet (e)", ItemID.CATSPEAK_AMULETE);
        catspeakEWorn = new ItemRequirement(ItemID.CATSPEAK_AMULETE, 1, true);

        deathRune5 = new ItemRequirement("Death runes", ItemID.DEATH_RUNE, 5);
        cat = new FollowerItemRequirement("A cat",
                  ItemCollections.getCats(),
                NpcCollections.getCats());

        chocolateCake = new ItemRequirement("Chocolate cake", ItemID.CHOCOLATE_CAKE);

        logs = new ItemRequirement("Logs", ItemID.LOGS);

        tinderbox = new ItemRequirement("Tinderbox", ItemID.TINDERBOX);

        milk = new ItemRequirement("Bucket of milk", ItemID.BUCKET_OF_MILK);

        shears = new ItemRequirement("Shears", ItemID.SHEARS);

        potatoSeed4 = new ItemRequirement("Potato seeds", ItemID.POTATO_SEED, 4);

        rake = new ItemRequirement("Rake", ItemID.RAKE);

        dibber = new ItemRequirement("Seed dibber", ItemID.SEED_DIBBER);

        vialOfWater = new ItemRequirement( ItemID.VIAL_OF_WATER);
        desertBottom = new ItemRequirement( ItemID.DESERT_ROBE, 1, true);
        desertTop = new ItemRequirement( ItemID.DESERT_SHIRT, 1, true);
        hat = new ItemRequirement(ItemID.DOCTORS_HAT, 1, true);
        hat.addAlternateItemID(ItemID.NURSE_HAT);
        hat.setDisplayMatchedItemName(true);
    }

    public void setupConditions()
    {
       // bobNearby = new NpcCondition(NpcID.BOB_8034);

        rakedPatch = new VarbitRequirement(1033, 3);
        plantedSeed = new VarbitRequirement(1033, 4, Operation.GREATER_EQUAL);
        grownPotatoes = new VarbitRequirement(1033, 8);
        madeBed = new VarbitRequirement(1029, 1);
        placedLogs = new VarbitRequirement(1030, 1);
        litLogs = new VarbitRequirement(1030, 2);
        placedCake = new VarbitRequirement(1031, 3);
        placedMilk = new VarbitRequirement(1031, 4);
        usedShears = new VarbitRequirement(1032, 8);
    }

    public void setupSteps()
    {
        talkToUnferth = new NPCStep( NpcID.UNFERTH, new RSTile(2919, 3559, 0), cat, catspeak);
        talkToUnferth.addDialogStep("I'll help you.");
        talkToHild = new NPCStep( NpcID.HILD_4112, new RSTile(2930, 3568, 0),
               deathRune5, catspeak);
    //    findBob = new DetailedQuestStep( "Operate the catspeak amulet (e) to locate Bob the Cat. He's often in Catherby Archery Shop or at the Varrock Anvil.", catspeakE);
    //    talkToBob = new NPCStep( NpcID.BOB_8034, cat, catspeakEWorn);
        talkToGertrude = new NPCStep( NpcID.GERTRUDE_7723, new RSTile(3151, 3413, 0),
               cat, catspeakEWorn);
        talkToGertrude.addDialogStep("Ask about Bob's parents.");

        talkToReldo = new NPCStep( NpcID.RELDO_4243, new RSTile(3211, 3494, 0),
                cat, catspeakEWorn);
        talkToReldo.addDialogStep("Ask about Robert the Strong.");
   //     findBobAgain = new DetailedQuestStep( "Use the catspeak amulet (e) again to locate Bob the Cat.", catspeakE);
 //       talkToBobAgain = new NPCStep( NpcID.BOB_8034, cat, catspeakEWorn);
        talkToSphinx = new NPCStep( NpcID.SPHINX_4209, new RSTile(3302, 2784, 0),
               cat, catspeakEWorn);
        talkToSphinx.addDialogStep("Ask the Sphinx for help for Bob.");
        useRake = new ObjectStep(9399, new RSTile(2919, 3562, 0), "Rake", rake);

        //TODO FIX
        plantSeeds = new ObjectStep(9399, new RSTile(2919, 3562, 0), "Plant",
          dibber, potatoSeed4);

        makeBed = new ObjectStep(9438, new RSTile(2917, 3557, 0), "Make");
        useLogsOnFireplace = new ObjectStep(9442, new RSTile(2919, 3557, 0),
                "Use logs on Unferth's fireplace", logs);

        lightLogs = new ObjectStep(9442, new RSTile(2919, 3557, 0),
                "Use a tinderbox on Unferth's fireplace.");

        useChocolateCakeOnTable = new ObjectStep(9435, new RSTile(2921, 3556, 0),
                "Use a chocolate cake on Unferth's table.", chocolateCake);

        useMilkOnTable = new ObjectStep(9435, new RSTile(2921, 3556, 0),
                "Use a bucket of milk on Unferth's table.", milk);

       // useShearsOnUnferth = new NPCStep( NpcID.UNFERTH_4241, new RSTile(2919, 3559, 0),
     //           "Use some shears on Unferth in north east Burthorpe.", shears);
   //     ((NPCStep) (useShearsOnUnferth)).addAlternateNpcs(NpcID.UNFERTH, NpcID.UNFERTH_4238, NpcID.UNFERTH_4239, NpcID.UNFERTH_4240);


       // waitForPotatoesToGrow = new DetailedQuestStep( "You now need to wait 15-35 minutes for the potatoes to grow.");

        reportToUnferth = new NPCStep( NpcID.UNFERTH, new RSTile(2919, 3559, 0),
          cat, catspeakEWorn);
        talkToApoth = new NPCStep( NpcID.APOTHECARY, new RSTile(3195, 3405, 0),
              cat, catspeakEWorn);
        talkToApoth.addDialogStep("Talk about A Tail of Two Cats.");

        talkToUnferthAsDoctor = new NPCStep( NpcID.UNFERTH, new RSTile(2919, 3559, 0),cat, catspeakEWorn, hat, desertTop, desertBottom, vialOfWater);
              //  "Talk to Unferth whilst wearing the doctor/nurse hat, a desert shirt and a desert robe, " +
                    //    "and no weapon/shield.",
      //  findBobToFinish = new DetailedQuestStep( "Use the catspeak amulet (e) to locate Bob once more.", catspeakE);
       // talkToBobToFinish = new NPCStep( NpcID.BOB_8034,  cat, catspeakEWorn);
        talkToUnferthToFinish = new NPCStep( NpcID.UNFERTH, new RSTile(2919, 3559, 0), "Talk to Unferth to complete the quest.");
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
        int varBitValue = Utils.getVarBitValue(QuestVarbits.QUEST_A_TAIL_OF_TWO_CATS.getId());
        if (varBitValue == 4) {
            Utils.closeQuestCompletionWindow();
            cQuesterV2.taskList.remove(this);
            return;
        }
        if (varBitValue == 0) {
            buyItems();
            //getItems();
        }
        Map<Integer, QuestStep> steps = loadSteps();
        Optional<QuestStep> step = Optional.ofNullable(steps.get(varBitValue));
        step.ifPresent(QuestStep::execute);

        if (NPCInteraction.isConversationWindowUp())
            NPCInteraction.handleConversation();
        else
            Waiting.waitNormal(150,25);
    }

    @Override
    public String questName() {
        return "A Tail Of Two Cats";
    }

    @Override
    public boolean checkRequirements() {
        return false;
    }
}
