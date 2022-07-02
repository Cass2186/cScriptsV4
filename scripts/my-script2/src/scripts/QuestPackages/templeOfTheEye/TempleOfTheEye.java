package scripts.QuestPackages.templeOfTheEye;

import org.tribot.api2007.Game;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.GameState;
import org.tribot.script.sdk.Inventory;
import org.tribot.script.sdk.MyPlayer;
import org.tribot.script.sdk.Skill;
import scripts.*;
import scripts.QuestPackages.HeroesQuest.HeroesQuestBlackArmsGang;
import scripts.QuestPackages.WitchsPotion.WitchsPotion;
import scripts.QuestSteps.*;
import scripts.Requirements.*;
import scripts.Requirements.Items.ItemCollections;
import scripts.Requirements.Util.ConditionalStep;
import scripts.Requirements.Util.Conditions;
import scripts.Requirements.Util.Operation;
import scripts.Tasks.Priority;

import java.util.*;

/*
        VARBITS
        -------
        13738: Quest Step
        13739: Told to see Herbert
        13740: One-time abyss teleport
        13741: Received cup of tea
        13742: Apprentice Tamara puzzle
        13743: Apprentice Felix puzzle
        13744: Apprentice Cordelia puzzle
        13745: Quest offered
        13747 - 13752: Energy orbs
        13753: One-time Wizard's Tower teleport
        13759: Guardians of the Rift Tutorial progress
        */
public class TempleOfTheEye implements QuestTask {


    private static TempleOfTheEye quest;

    public static TempleOfTheEye get() {
        return quest == null ? quest = new TempleOfTheEye() : quest;
    }

    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return cQuesterV2.taskList.size() > 0 &&
                cQuesterV2.taskList.get(0).equals(this) ;
    }

    @Override
    public void execute() {
       //buyAndGetItems();

        int varbit = GameState.getVarbit(QuestVarbits.QUEST_TEMPLE_OF_THE_EYE.getId());
        Map<Integer, QuestStep> steps = loadSteps();
        if (varbit == 15 && Inventory.contains(ItemID.STRONG_CUP_OF_TEA)){
           // talkToMage2.execute();
           // return;
        }


        Optional<QuestStep> step = Optional.ofNullable(steps.get(varbit));
        cQuesterV2.status = step.map(s -> s.toString()).orElse("Unknown Step Name: Varbit = " +varbit);
        step.ifPresent(QuestStep::execute);
    }

    @Override
    public String questName() {
        return "Temple of the Eye (" + GameState.getVarbit(QuestVarbits.QUEST_TEMPLE_OF_THE_EYE.getId()) +")";
    }

    @Override
    public boolean checkRequirements() {
        return Skill.RUNECRAFT.getActualLevel() >= 10;
    }


    @Override
    public List<ItemRequirement> getBuyList() {
        return null;
    }

    @Override
    public boolean isComplete() {
        return false;
    }

    //Items Required
    ItemRequirement bucketOfWater, strongTea, eyeAmulet, chisel, pickaxe, abyssalIncantation;

    //Items Recommended
    ItemRequirement varrockTeleport, alKharidTeleport;

    Requirement inAbyss, canTeleportFromHerbert, thrownBucket, givenAmuletBack, inWizardBasement, canTeleportFromPersten,
            inWizardFloorOne, felixPuzzleNotSeen, tamaraPuzzleNotSeen, cordeliaPuzzleNotSeen, inTempleOfTheEye,
            felixRiftTalk, tamaraRiftTalk, cordeliaRiftTalk, mysteriousVisionSeen, inTempleOfTheEyeTutorial;

    QuestStep talkToPersten1, finishTalkToPersten1, talkToMage1, getTeaForMage, talkToMage2, finishTalkToMage2,
            teleportViaHerbert, talkToDarkMage1, finishTalkToDarkMage1, talkToMageInWildy, talkToDarkMage2, talkToPersten2,
            finishTalkToPersten2, teleportToArchmage, goDownToArchmage, talktoArchmage1, finishTalkingToArchmage1,
            goUpToTraibornBasement, goUpToTraiborn, talktoTrailborn1, talkToFelix, talkToTamara, talkToCordelia,
            talktoTrailborn2, goDownToArchmageFloorOne, goDownToArchmage2, talktoArchmage2, performIncantation,
            enterWizardBasement, enterPortal, templeCutscene1, talkToFelix2, talkToTamara2, talkToCordelia2, talkToPersten3,
            templeCutscene2, debrief, guardiansTutorial, templeCutscene3, finishQuest;

    ObjectStep touchRunes;

    //RSArea()s
    RSArea abyss, wizardBasement, wizardFloorOne, templeOfTheEye, templeOfTheEye2;

    public Map<Integer, QuestStep> loadSteps() {
        setupItemRequirements();
        setupAreas();
        setupConditions();
        setupSteps();
        Map<Integer, QuestStep> steps = new HashMap<>();

        steps.put(0, talkToPersten1);
        steps.put(5, finishTalkToPersten1);
        steps.put(10, talkToMage1);

        ConditionalStep fetchHerbertsTea = new ConditionalStep(getTeaForMage);
        fetchHerbertsTea.addStep(new Conditions(strongTea, bucketOfWater, eyeAmulet), talkToMage2);
        steps.put(15, fetchHerbertsTea);

        steps.put(20, finishTalkToMage2);

        ConditionalStep teleportAbyss = new ConditionalStep(talkToMageInWildy);
        teleportAbyss.addStep(inAbyss, talkToDarkMage1);
        teleportAbyss.addStep(canTeleportFromHerbert, teleportViaHerbert);
        // teleportAbyss.addRequirement(bucketOfWater);
        steps.put(25, teleportAbyss);

        ConditionalStep goTalkToDarkMage1 = new ConditionalStep(talkToMageInWildy);
        goTalkToDarkMage1.addStep(inAbyss, finishTalkToDarkMage1);
        steps.put(30, goTalkToDarkMage1);

        ConditionalStep goTouchRunes = new ConditionalStep(talkToMageInWildy);
        goTouchRunes.addStep(inAbyss, touchRunes);
        steps.put(35, goTouchRunes);

        ConditionalStep goTalkToDarkMage2 = new ConditionalStep(talkToMageInWildy);
        goTalkToDarkMage2.addStep(inAbyss, talkToDarkMage2);
        steps.put(40, goTalkToDarkMage2);

        steps.put(45, talkToPersten2);
        steps.put(50, talkToPersten2);
        steps.put(55, finishTalkToPersten2);

        ConditionalStep goTalkToArchmage = new ConditionalStep(goDownToArchmage);
        goTalkToArchmage.addStep(canTeleportFromPersten, teleportToArchmage);
        goTalkToArchmage.addStep(inWizardBasement, talktoArchmage1);
       // goTalkToArchmage.addRequirement(abyssalIncantation);
        steps.put(60, goTalkToArchmage);

        steps.put(65, finishTalkingToArchmage1);

        ConditionalStep goTalkToTraiborn1 = new ConditionalStep(goUpToTraiborn);
        goTalkToTraiborn1.addStep(inWizardBasement, goUpToTraibornBasement);
        goTalkToTraiborn1.addStep(inWizardFloorOne, talktoTrailborn1);
        steps.put(70, goTalkToTraiborn1);

        ConditionalStep solveTraibornsPuzzle = new ConditionalStep(goUpToTraiborn);
        solveTraibornsPuzzle.addStep(new Conditions(inWizardFloorOne, felixPuzzleNotSeen), talkToFelix);
        solveTraibornsPuzzle.addStep(new Conditions(inWizardFloorOne, tamaraPuzzleNotSeen), talkToTamara);
        solveTraibornsPuzzle.addStep(new Conditions(inWizardFloorOne, cordeliaPuzzleNotSeen), talkToCordelia);
        steps.put(75, solveTraibornsPuzzle);

        ConditionalStep goTalkToTraiborn2 = new ConditionalStep(goUpToTraiborn);
        goTalkToTraiborn2.addStep(inWizardBasement, goUpToTraibornBasement);
        goTalkToTraiborn2.addStep(inWizardFloorOne, talktoTrailborn2);
        steps.put(80, goTalkToTraiborn2);

        ConditionalStep goTalkToArchmage2 = new ConditionalStep(goDownToArchmage2);
        goTalkToArchmage2.addStep(inWizardFloorOne, goDownToArchmageFloorOne);
        goTalkToArchmage2.addStep(inWizardBasement, talktoArchmage2);
        steps.put(85, goTalkToArchmage2);

        ConditionalStep goBeginIncantation = new ConditionalStep(goDownToArchmage2);
        goBeginIncantation.addStep(inWizardBasement, performIncantation);
        steps.put(90, goBeginIncantation);

        steps.put(95, templeCutscene1);

        ConditionalStep investigateTemple = new ConditionalStep(enterWizardBasement);
        investigateTemple.addStep(inWizardBasement, enterPortal);
        investigateTemple.addStep(new Conditions(inTempleOfTheEye, felixRiftTalk), talkToFelix2);
        investigateTemple.addStep(new Conditions(inTempleOfTheEye, tamaraRiftTalk), talkToTamara2);
        investigateTemple.addStep(new Conditions(inTempleOfTheEye, cordeliaRiftTalk), talkToCordelia2);
        steps.put(100, investigateTemple);

        ConditionalStep goTalkToPerstenTemple = new ConditionalStep(enterWizardBasement);
        goTalkToPerstenTemple.addStep(inWizardBasement, enterPortal);
        goTalkToPerstenTemple.addStep(inTempleOfTheEye, talkToPersten3);
        goTalkToPerstenTemple.addStep(mysteriousVisionSeen, templeCutscene2);
        steps.put(105, goTalkToPerstenTemple);

        ConditionalStep debriefAfterVision = new ConditionalStep(enterWizardBasement);
        debriefAfterVision.addStep(inWizardBasement, enterPortal);
        debriefAfterVision.addStep(inTempleOfTheEye, debrief);
        steps.put(110, debriefAfterVision);

        ConditionalStep doGuardiansTutorial = new ConditionalStep(enterWizardBasement);
        doGuardiansTutorial.addStep(inWizardBasement, enterPortal);
        doGuardiansTutorial.addStep(inTempleOfTheEyeTutorial, guardiansTutorial);
        steps.put(115, doGuardiansTutorial);    // At this stage, the player is handheld by in-game tutorial.

        ConditionalStep finishGuardiansTutorial = new ConditionalStep(enterWizardBasement);
        finishGuardiansTutorial.addStep(inWizardBasement, enterPortal);
        finishGuardiansTutorial.addStep(inTempleOfTheEyeTutorial, templeCutscene3);
        steps.put(120, finishGuardiansTutorial);

        ConditionalStep doFinishQuest = new ConditionalStep(enterWizardBasement);
        doFinishQuest.addStep(inWizardBasement, finishQuest);
        steps.put(125, doFinishQuest);

        return steps;
    }

    public void setupItemRequirements() {
        bucketOfWater = new ItemRequirement("Bucket of water", ItemID.BUCKET_OF_WATER);
        chisel = new ItemRequirement("Chisel", ItemID.CHISEL);
        //chisel.canBeObtainedDuringQuest();
        pickaxe = new ItemRequirement("Pickaxe", ItemCollections.getPickaxes());
        // pickaxe.canBeObtainedDuringQuest();

        varrockTeleport = new ItemRequirement("Method of teleportation to Varrock", ItemID.VARROCK_TELEPORT);
        alKharidTeleport = new ItemRequirement("Method of teleportation to Al Kharid", ItemCollections.getRingOfDuelings());
        alKharidTeleport.addAlternateItemID(ItemCollections.getAmuletOfGlories());
        alKharidTeleport.addAlternateItemID(ItemID.LUMBRIDGE_TELEPORT);

        strongTea = new ItemRequirement("Strong Cup of Tea", ItemID.STRONG_CUP_OF_TEA);
        eyeAmulet = new ItemRequirement("Eye Amulet", ItemID.EYE_AMULET);
        //eyeAmulet.setTooltip("You can get another from Wizard Persten if you lost it");
        abyssalIncantation = new ItemRequirement("Abyssal Incantation", ItemID.ABYSSAL_INCANTATION);
        //abyssalIncantation.setTooltip("You can get another from the Dark Mage in the Abyss if you lost it. If already" +
        //        " shown to Wizard Persten, you can get another from her instead");

    }

    public void setupConditions() {
        inAbyss = new AreaRequirement(abyss);
        inWizardBasement = new AreaRequirement(wizardBasement);
        inWizardFloorOne = new AreaRequirement(wizardFloorOne);
        inTempleOfTheEye = new AreaRequirement(templeOfTheEye);
        inTempleOfTheEyeTutorial = new AreaRequirement(templeOfTheEye2);

        canTeleportFromHerbert = new VarbitRequirement(13740, 0);
        thrownBucket = new VarbitRequirement(QuestVarbits.QUEST_TEMPLE_OF_THE_EYE.getId(), 30, Operation.GREATER_EQUAL);
        givenAmuletBack = new VarbitRequirement(QuestVarbits.QUEST_TEMPLE_OF_THE_EYE.getId(), 55, Operation.GREATER_EQUAL);
        canTeleportFromPersten = new VarbitRequirement(13753, 0);

        felixPuzzleNotSeen = new VarbitRequirement(13743, 0);
        tamaraPuzzleNotSeen = new VarbitRequirement(13742, 0);
        cordeliaPuzzleNotSeen = new VarbitRequirement(13744, 0);

        felixRiftTalk = new VarbitRequirement(13755, 0);
        tamaraRiftTalk = new VarbitRequirement(13754, 0);
        cordeliaRiftTalk = new VarbitRequirement(13756, 0);

        mysteriousVisionSeen = new VarbitRequirement(12139, 1);
    }

    public void setupAreas() {
        abyss = new RSArea(new RSTile(3010, 4803, 0), new RSTile(3070, 4862, 0));
        wizardBasement = new RSArea(new RSTile(3094, 9553, 0), new RSTile(3125, 9582, 0));
        wizardFloorOne = new RSArea(new RSTile(3101, 3153, 1), new RSTile(3116, 3167, 1));
        templeOfTheEye = new RSArea(new RSTile(2370, 5627, 0), new RSTile(2425, 5682, 0));
        templeOfTheEye2 = new RSArea(new RSTile(2433, 5698, 0), new RSTile(3648, 9523, 0));
    }

    public void setupSteps() {
        talkToPersten1 = new NPCStep("Wizard Persten", new RSTile(3285, 3232, 0));
        talkToPersten1.addDialogStep("What's a wizard doing in Al Kharid?");
        talkToPersten1.addDialogStep("Yes.");

        finishTalkToPersten1 = new NPCStep("Wizard Persten", new RSTile(3285, 3232, 0));

        talkToPersten1.addSubSteps(finishTalkToPersten1);

        // Note: You also can't be wearing any other god's equipment when talking to him
        talkToMage1 = new NPCStep("Mage of Zamorak", new RSTile(3258, 3383, 0),
                eyeAmulet);
        talkToMage1.addDialogStep("I need your help with an amulet.");

        getTeaForMage = new NPCStep("Tea Seller", new RSTile(3271, 3411, 0));
        getTeaForMage.addDialogStep("Could I have a strong cup of tea?");

        talkToMage2 = new NPCStep("Mage of Zamorak", new RSTile(3258, 3383, 0),
                strongTea, bucketOfWater, eyeAmulet);
        talkToMage2.addDialogStep("Could you help me with that amulet now?");
        talkToMage2.addDialogStep("Yes.");

        finishTalkToMage2 = new NPCStep("Mage of Zamorak", new RSTile(3258, 3383, 0), eyeAmulet);
        talkToMage2.addDialogStep("Could you help me with that amulet now?");

        talkToMage2.addSubSteps(finishTalkToMage2);

        teleportViaHerbert = new NPCStep("Mage of Zamorak", new RSTile(3258, 3383, 0),

                eyeAmulet);
        teleportViaHerbert.addDialogStep("Could you help me with that amulet now?");
        teleportViaHerbert.addDialogStep("Yes.");

        talkToMageInWildy = new NPCStep("Mage of Zamorak", new RSTile(3102, 3557, 0),
                eyeAmulet);
        talkToMageInWildy.addDialogStep("Could you teleport me to the Abyss?");

        talkToDarkMage1 = new NPCStep("Dark Mage", new RSTile(3039, 4834, 0), eyeAmulet);
        talkToDarkMage1.addDialogStep("I need your help with an amulet.");

        finishTalkToDarkMage1 = new NPCStep("Dark Mage", new RSTile(3039, 4834, 0),
                eyeAmulet);

        talkToDarkMage1.addSubSteps(talkToMageInWildy, teleportViaHerbert, finishTalkToDarkMage1);

        touchRunes = new ObjectStep(43768, "Touch");
        touchRunes.addAlternateObjects(43769, 43770, 43771, 43772, 43773);

        talkToDarkMage2 = new NPCStep("Dark Mage", new RSTile(3039, 4834, 0), eyeAmulet);

        talkToPersten2 = new NPCStep("Wizard Persten", new RSTile(3285, 3232, 0),
                abyssalIncantation);
        talkToPersten2.addDialogStep("About that incantation...");

        finishTalkToPersten2 = new NPCStep("Wizard Persten", new RSTile(3285, 3232, 0),
                abyssalIncantation);

        talkToPersten2.addSubSteps(finishTalkToPersten2);

        teleportToArchmage = new NPCStep("Wizard Persten",  new RSTile(3285, 3232, 0));
        teleportToArchmage.addDialogStep("Yes.");

        goDownToArchmage = new ObjectStep(ObjectID.LADDER_2147, new RSTile(3104, 3162, 0),
                "Climb-down", abyssalIncantation);

        talktoArchmage1 = new NPCStep(NpcID.ARCHMAGE_SEDRIDOR_11433, new RSTile(3104, 9571, 0), abyssalIncantation);
        talktoArchmage1.addDialogStep("I need your help with an incantation.");

        finishTalkingToArchmage1 = new NPCStep(NpcID.ARCHMAGE_SEDRIDOR_11433, new RSTile(3104, 9571, 0),
                "Finish listening to Sedridor.");
        finishTalkingToArchmage1.addDialogStep("I need your help with an incantation.",
                "Can you help me with that incantation?");

        talktoArchmage1.addSubSteps(teleportToArchmage, goDownToArchmage, finishTalkingToArchmage1);

        goUpToTraibornBasement = new ObjectStep(ObjectID.LADDER_2148, new RSTile(3103, 9576, 0),
                "Speak to Wizard Traiborn on the Wizards' Tower 1st floor.");

        goUpToTraiborn = new ObjectStep(ObjectID.STAIRCASE_12536, new RSTile(3103, 3159, 0),
                "Speak to Wizard Traiborn on the Wizards' Tower 1st floor.");

        talktoTrailborn1 = new NPCStep("Wizard Traiborn", new RSTile(3112, 3162, 1));
        talktoTrailborn1.addDialogStep("I need your apprentices to help with an incantation.");

        talktoTrailborn1.addSubSteps(goUpToTraibornBasement, goUpToTraiborn);

        talkToFelix = new NPCStep(11446, new RSTile(3112, 3162, 1),
                "Get puzzle from Apprentice Felix.");
        talkToTamara = new NPCStep(NpcID.APPRENTICE_TAMARA, new RSTile(3112, 3162, 1),
                "Get puzzle from Apprentice Tamara.");
        talkToCordelia = new NPCStep(NpcID.APPRENTICE_CORDELIA_11443, new RSTile(3112, 3162, 1),
                "Get puzzle from Apprentice Cordelia.");

        //TODO handle chat, answer is 11
        talktoTrailborn2 = new NPCStep("Wizard Traiborn" , new RSTile(3112, 3162, 1));
        talktoTrailborn2.addDialogStep("I think I know what a thingummywut is!");

        goDownToArchmageFloorOne = new ObjectStep(ObjectID.STAIRCASE_12537, new RSTile(3103, 3159, 1),
                "Climb-down");
        goDownToArchmageFloorOne.addDialogStep("Climb-down");

        goDownToArchmage2 = new ObjectStep(ObjectID.LADDER_2147, new RSTile(3104, 3162, 0),
                "Climb-down");

        talktoArchmage2 = new NPCStep("Archmage Sedridor", new RSTile(3104, 9571, 0));

        talktoArchmage2.addSubSteps(goDownToArchmageFloorOne, goDownToArchmage2);

        performIncantation = new NPCStep("Archmage Sedridor", new RSTile(3104, 9571, 0));
        performIncantation.addDialogStep("Let's do it.");
        performIncantation.addDialogStep("So we're ready to perform the incantation?");

        enterWizardBasement = new ObjectStep(ObjectID.LADDER_2147, new RSTile(3104, 3162, 0),
                "Climb-down");

        enterPortal = new ObjectStep(43765, new RSTile(3104, 9574, 0),
                "Enter");

        templeCutscene1 = new DetailedQuestStep("Enter the Temple of the Eye.");
        templeCutscene1.addSubSteps(enterPortal);

        talkToFelix2 = new NPCStep(11461, new RSTile(2401, 5643, 0),
                "Talk to Apprentice Felix.");
        talkToTamara2 = new NPCStep(11459, new RSTile(2385, 5659, 0),
                "Talk to Apprentice Tamara.");
        talkToCordelia2 = new NPCStep(11460, new RSTile(2397, 5677, 0),
                "Talk to Apprentice Cordelia.");

        talkToPersten3 = new NPCStep("Wizard Persten", new RSTile(2400, 5667, 0));

        templeCutscene2 = new DetailedQuestStep("Experience the vision.");
        debrief = new DetailedQuestStep("Debrief with Wizard Persten and the apprentices.");

        templeCutscene3 = new DetailedQuestStep("Listen to the Great Guardian.");

        //guardiansTutorial.addSubSteps(templeCutscene3);

        finishQuest = new NPCStep(NpcID.ARCHMAGE_SEDRIDOR_11433, new RSTile(3104, 9571, 0),
                "Speak to Archmage Sedridor in the Wizard's Tower basement to finish the quest.");

    }


    public List<ItemRequirement> getItemRequirements() {
        ArrayList<ItemRequirement> reqs = new ArrayList<>();
        reqs.add(bucketOfWater);
        reqs.add(chisel);
        reqs.add(pickaxe);
        return reqs;
    }


    public List<ItemRequirement> getItemRecommended() {
        ArrayList<ItemRequirement> reqs = new ArrayList<>();
        reqs.add(varrockTeleport);
        reqs.add(alKharidTeleport);
        return reqs;
    }

    @Override
    public List<Requirement> getGeneralRequirements() {
        ArrayList<Requirement> req = new ArrayList<>();
        //  req.add(new QuestRequirement(QuestHelperQuest.ENTER_THE_ABYSS, QuestState.FINISHED));
        req.add(new SkillRequirement(Skills.SKILLS.RUNECRAFTING, 10));
        return req;
    }
}
