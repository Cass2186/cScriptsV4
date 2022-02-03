package scripts.QuestPackages.TrollRomance;

import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.Inventory;
import org.tribot.script.sdk.Log;
import scripts.ItemID;
import scripts.NpcID;
import scripts.ObjectID;
import scripts.QuestPackages.TrollStronghold.TrollStronghold;
import scripts.QuestSteps.*;
import scripts.Requirements.AreaRequirement;
import scripts.Requirements.ItemRequirement;
import scripts.Requirements.Requirement;
import scripts.Requirements.SkillRequirement;
import scripts.Requirements.Util.ConditionalStep;
import scripts.Requirements.Util.Conditions;
import scripts.Tasks.Priority;
import scripts.cQuesterV2;

import java.util.*;

public class TrollRomance implements QuestTask {
    private static TrollRomance quest;

    public static TrollRomance get() {
        return quest == null ? quest = new TrollRomance() : quest;
    }
    //Items Required
    ItemRequirement ironBar, mapleLog, rope, cakeTin, swampTar, bucketOfWax, wax, sled, waxedSled, trollweissFlowers,
            combatGear, sledEquipped, climbingBoots;

    Requirement inStrongholdFloor1, inStrongholdFloor2, inPrison, inTrollweiss, atFlowerLocation, inTrollCave, fightableArrgNearby;

   QuestStep enterStronghold, goDownToUg, goUpToUg, talkToUg, talkToAga, talkToTenzing, talkToDunstan, talkToDunstanAgain, useTarOnWax,
            useWaxOnSled, enterTrollCave, leaveTrollCave, equipSled, sledSouth, goDownToUgAgain, goUpToUgAgain, enterStrongholdAgain, talkToUgWithFlowers,
            goDownToUgForFight, goUpToUgForFight, enterStrongholdForFight, goDownToUgForEnd, goUpToUgForEnd, enterStrongholdForEnd, challengeArrg, killArrg, returnToUg;

    ObjectStep pickFlowers;

    //RSArea()s
    RSArea strongholdFloor1, strongholdFloor2, prison, trollweiss, flowerLocation, trollCave;

    public Map<Integer, QuestStep> loadSteps() {
        loadRSAreas();
        setupItemRequirements();
        setupConditions();
        setupSteps();
        Map<Integer, QuestStep> steps = new HashMap<>();

        ConditionalStep startingOffSteps = new ConditionalStep( enterStronghold);
        startingOffSteps.addStep(inStrongholdFloor1, talkToUg);
        startingOffSteps.addStep(inPrison, goUpToUg);
        startingOffSteps.addStep(inStrongholdFloor2, goDownToUg);

        steps.put(0, startingOffSteps);

        steps.put(5, talkToAga);
        steps.put(10, talkToTenzing);
        steps.put(15, talkToDunstan);
        steps.put(20, talkToDunstanAgain);

        ConditionalStep getSled = new ConditionalStep( useTarOnWax);
        getSled.addStep(wax, useWaxOnSled);

        steps.put(22, getSled);

        ConditionalStep getFlower = new ConditionalStep( enterTrollCave);
        getFlower.addStep(atFlowerLocation, pickFlowers);
        getFlower.addStep(new Conditions(inTrollweiss, sledEquipped), sledSouth);
        getFlower.addStep(inTrollweiss, equipSled);
        getFlower.addStep(inTrollCave, leaveTrollCave);

        steps.put(25, getFlower);

        ConditionalStep bringFlowerToUg = new ConditionalStep( enterStrongholdAgain);
        bringFlowerToUg.addStep(inStrongholdFloor1, talkToUgWithFlowers);
        bringFlowerToUg.addStep(inPrison, goUpToUgAgain);
        bringFlowerToUg.addStep(inStrongholdFloor2, goDownToUgAgain);

        steps.put(30, bringFlowerToUg);

        ConditionalStep defeatArrg = new ConditionalStep( enterStrongholdForFight);
        defeatArrg.addStep(fightableArrgNearby, killArrg);
        defeatArrg.addStep(inStrongholdFloor1, challengeArrg);
        defeatArrg.addStep(inPrison, goUpToUgForFight);
        defeatArrg.addStep(inStrongholdFloor2, goDownToUgForFight);

        steps.put(35, defeatArrg);

        ConditionalStep finishQuest = new ConditionalStep( enterStrongholdForEnd);
        finishQuest.addStep(inStrongholdFloor1, returnToUg);
        finishQuest.addStep(inPrison, goUpToUgForEnd);
        finishQuest.addStep(inStrongholdFloor2, goDownToUgForEnd);

        steps.put(40, finishQuest);

        return steps;
    }

    public void setupItemRequirements() {
        ironBar = new ItemRequirement("Iron bar", ItemID.IRON_BAR);
        mapleLog = new ItemRequirement("Maple/yew logs", ItemID.MAPLE_LOGS);
        mapleLog.addAlternateItemIDs(ItemID.YEW_LOGS);
        rope = new ItemRequirement("Rope", ItemID.ROPE);
        cakeTin = new ItemRequirement("Cake tin", ItemID.CAKE_TIN);
        swampTar = new ItemRequirement("Swamp tar", ItemID.SWAMP_TAR);
        bucketOfWax = new ItemRequirement("Bucket of wax", ItemID.BUCKET_OF_WAX);
        wax = new ItemRequirement("Wax", ItemID.WAX);
        sled = new ItemRequirement("Sled", ItemID.SLED);
      //  sled.setTooltip("You can have Dunstan make another. Bring him a maple log, a rope and an iron bar");
        waxedSled = new ItemRequirement("Sled", ItemID.SLED_4084);
      //  waxedSled.setTooltip("You can have Dunstan make another. Bring him a maple log, a rope and an iron bar. You then can apply some wax to it");
        sledEquipped = new ItemRequirement(ItemID.SLED_4084, 1, true);
      //  sledEquipped.setTooltip("You can have Dunstan make another. Bring him a maple log, a rope and an iron bar. You then can apply some wax to it");
        trollweissFlowers = new ItemRequirement("Trollweiss", ItemID.TROLLWEISS);
      //  trollweissFlowers.setTooltip("You can get another from the Trollweiss mountain");
        climbingBoots = new ItemRequirement("Climbing boots", ItemID.CLIMBING_BOOTS);
        combatGear = new ItemRequirement("Combat gear, food, and potions", -1, -1);
    }

    public void loadRSAreas()
    {
        strongholdFloor1 = new RSArea(new RSTile(2820, 10048, 1), new RSTile(2862, 10110, 1));
        strongholdFloor2 = new RSArea(new RSTile(2820, 10048, 2), new RSTile(2862, 10110, 2));
        prison = new RSArea(new RSTile(2822, 10049, 0), new RSTile(2859, 10110, 0));
        trollweiss = new RSArea(new RSTile(2753, 3801, 0), new RSTile(2816, 3877, 0));
        flowerLocation = new RSArea(new RSTile(2771, 3771, 0), new RSTile(2817, 3800, 0));
        trollCave = new RSArea(new RSTile(2764, 10184, 0), new RSTile(2808, 10237, 0));
    }

    public void setupConditions()
    {
        inStrongholdFloor1 = new AreaRequirement(strongholdFloor1);
        inStrongholdFloor2 = new AreaRequirement(strongholdFloor2);
        inPrison = new AreaRequirement(prison);
        inTrollweiss = new AreaRequirement(trollweiss);
        inTrollCave = new AreaRequirement(trollCave);
        atFlowerLocation = new AreaRequirement(flowerLocation);
       // fightableArrgNearby = new NpcCondition(NpcID.ARRG_643);
    }

    public void setupSteps()
    {
        enterStronghold = new ObjectStep( ObjectID.STRONGHOLD, new RSTile(2839, 3690, 0), "Enter the Troll Stronghold.");

        goDownToUg = new ObjectStep( ObjectID.STONE_STAIRCASE_3789, new RSTile(2844, 10109, 2), "Climb down the north staircase.");
     //   goDownToUg.setWorldMapPoint(new RSTile(2971, 10172, 1));

        goUpToUg = new ObjectStep( ObjectID.STONE_STAIRCASE, new RSTile(2853, 10107, 0), "Go up the stairs from the prison.");
     //   goUpToUg.setWorldMapPoint(new RSTile(2853, 10106, 1));

        talkToUg = new NPCStep( NpcID.UG, new RSTile(2827, 10064, 1), "Talk to Ug in the south west room of the Troll Stronghold's first floor.");
        talkToUg.addDialogStep("Awww, you poor troll. What seems to be the problem?", "Don't worry now, I'll see what I can do.");
      //  talkToUg.setWorldMapPoint(new RSTile(2891, 10097, 0));
      //  talkToUg.addSubSteps(enterStronghold, goDownToUg, goUpToUg);

        talkToAga = new NPCStep( NpcID.AGA, new RSTile(2828, 10104, 1), "Talk to Aga north of Ug.");
        talkToAga.addDialogStep("So... how's your... um... love life?");
     //   talkToAga.setWorldMapPoint(new RSTile(2892, 10136, 0));

        talkToTenzing = new NPCStep( NpcID.TENZING, new RSTile(2820, 3555, 0), "Talk to Tenzing west of Burthorpe.");
        talkToTenzing.addDialogStep("Do you know where I can find Trollweiss?", "What would I need to make such a sled?");
        talkToDunstan = new NPCStep( NpcID.DUNSTAN, new RSTile(2919, 3574, 0), ironBar, mapleLog, rope);
        talkToDunstan.addDialogStep("Talk about a quest.", "I need a sled!!", "No.");
        talkToDunstanAgain = new NPCStep( NpcID.DUNSTAN, new RSTile(2919, 3574, 0),
              ironBar, mapleLog, rope);
        talkToDunstanAgain.addDialogStep("Talk about a quest.");
        useTarOnWax = new UseItemOnItemStep(ItemID.SWAMP_TAR,
                ItemID.BUCKET_OF_WAX, !Inventory.contains(ItemID.SWAMP_TAR), swampTar, bucketOfWax, cakeTin);
        useWaxOnSled = new UseItemOnItemStep( ItemID.WAX, ItemID.SLED, !Inventory.contains(ItemID.WAX), wax, sled);
        enterTrollCave = new ObjectStep( ObjectID.CAVE_ENTRANCE_5007, new RSTile(2821, 3744, 0),
                "Enter the cave north of Trollheim. There are high leveled ice trolls in here, so Protect from Melee and " +
                        "be careful!", waxedSled);
        leaveTrollCave = new ObjectStep( ObjectID.CREVASSE, new RSTile(2772, 10233, 0), "Leave the cave via the north crevice.");
        equipSled = new ClickItemStep(  ItemID.SLED_4084, "Equip", sledEquipped);
        sledSouth = new ObjectStep( ObjectID.SLOPE, new RSTile(2773, 3835, 0), "Sled to the south.");
        pickFlowers = new ObjectStep( ObjectID.RARE_FLOWERS, new RSTile(2781, 3783, 0), "Pick a rare flower.");

        enterStrongholdAgain = new ObjectStep( ObjectID.STRONGHOLD, new RSTile(2839, 3690, 0), "Return to Ug with the trollweiss flowers.", trollweissFlowers, combatGear);

        goDownToUgAgain = new ObjectStep( ObjectID.STONE_STAIRCASE_3789, new RSTile(2844, 10109, 2), "Return to Ug with the trollweiss flowers.");
       // goDownToUgAgain.setWorldMapPoint(new RSTile(2971, 10172, 1));

        goUpToUgAgain = new ObjectStep( ObjectID.STONE_STAIRCASE, new RSTile(2853, 10107, 0), "Return to Ug with the trollweiss flowers.");
       // goUpToUgAgain.setWorldMapPoint(new RSTile(2853, 10106, 1));

        talkToUgWithFlowers = new NPCStep( NpcID.UG, new RSTile(2827, 10064, 1),
         trollweissFlowers);
       // talkToUgWithFlowers.setWorldMapPoint(new RSTile(2891, 10097, 0));
       // talkToUgWithFlowers.addSubSteps(enterStrongholdAgain, goDownToUgAgain, goUpToUgAgain);

        enterStrongholdForEnd = new ObjectStep( ObjectID.STRONGHOLD, new RSTile(2839, 3690, 0), "Return to Ug to finish.");

        goDownToUgForEnd = new ObjectStep( ObjectID.STONE_STAIRCASE_3789, new RSTile(2844, 10109, 2), "Return to Ug to finish.");
     //   goDownToUgForEnd.setWorldMapPoint(new RSTile(2971, 10172, 1));

        goUpToUgForEnd = new ObjectStep( ObjectID.STONE_STAIRCASE, new RSTile(2853, 10107, 0), "Return to Ug to finish.");
     //   goUpToUgForEnd.setWorldMapPoint(new RSTile(2853, 10106, 1));

        enterStrongholdForFight = new ObjectStep( ObjectID.STRONGHOLD, new RSTile(2839, 3690, 0), "Challenge Arrg to a fight.", combatGear);

        goDownToUgForFight = new ObjectStep( ObjectID.STONE_STAIRCASE_3789, new RSTile(2844, 10109, 2), "Challenge Arrg to a fight.", combatGear);
      //  goDownToUgForFight.setWorldMapPoint(new RSTile(2971, 10172, 1));

        goUpToUgForFight = new ObjectStep( ObjectID.STONE_STAIRCASE, new RSTile(2853, 10107, 0), "Challenge Arrg to a fight.", combatGear);
      //  goUpToUgForFight.setWorldMapPoint(new RSTile(2853, 10106, 1));


        challengeArrg = new NPCStep( NpcID.ARRG, new RSTile(2829, 10095, 1),
                 combatGear);
        challengeArrg.addDialogStep("I am here to kill you!");
      //  challengeArrg.setWorldMapPoint(new RSTile(2892, 10127, 0));
      //  challengeArrg.addSubSteps(enterStrongholdForFight, goUpToUgForFight, goDownToUgForFight);

       // killArrg = new NPCStep( NpcID.ARRG_643, "Kill Arrg.");
        returnToUg = new NPCStep( NpcID.UG, new RSTile(2827, 10064, 1), "Talk to Ug in the south west room to finish.");
      //  returnToUg.setWorldMapPoint(new RSTile(2891, 10097, 0));
      //  returnToUg.addSubSteps(goDownToUgForEnd, goUpToUgForEnd, enterStrongholdForEnd);
    }

    public List<ItemRequirement> getItemRequirements() {
        return Arrays.asList(ironBar, mapleLog, rope, cakeTin, swampTar, bucketOfWax, combatGear, climbingBoots);
    }


    public List<String> getCombatRequirements() {
        ArrayList<String> reqs = new ArrayList<>();
        reqs.add("Arrg (level 113)");
        return reqs;
    }


    public List<Requirement> getGeneralRequirements() {
        ArrayList<Requirement> req = new ArrayList<>();
       // req.add(new QuestRequirement(QuestHelperQuest.TROLL_STRONGHOLD, QuestState.FINISHED));
        req.add(new SkillRequirement(Skills.SKILLS.AGILITY, 28));
        return req;
    }


  /*  public QuestPointReward getQuestPointReward() {
        return new QuestPointReward(2);
    }


    public List<ExperienceReward> getExperienceRewards()
    {
        return Arrays.asList(
                new ExperienceReward(Skill.AGILITY, 8000),
                new ExperienceReward(Skill.STRENGTH, 4000));
    }
*/



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
        if (!checkRequirements()){
            Log.log("[Debug]; Missing a requirement for Troll Romance (death plateau & 15 agility)");
            cQuesterV2.taskList.remove(this);
            return;
        }
        cQuesterV2.taskList.remove(this);
    }

    @Override
    public String questName() {
        return "Troll Romance";
    }

    @Override
    public boolean checkRequirements() {


        return true;
    }
}
