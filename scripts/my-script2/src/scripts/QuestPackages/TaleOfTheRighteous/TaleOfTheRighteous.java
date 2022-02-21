package scripts.QuestPackages.TaleOfTheRighteous;

import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import scripts.*;
import scripts.QuestPackages.ShieldOfArrav.BlackArmsGang;
import scripts.QuestSteps.*;
import scripts.Requirements.*;
import scripts.Requirements.Conditional.NpcCondition;
import scripts.Requirements.Items.ItemCollections;
import scripts.Requirements.Util.ConditionalStep;
import scripts.Requirements.Util.Conditions;
import scripts.Tasks.Priority;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaleOfTheRighteous implements QuestTask {

    private static TaleOfTheRighteous quest;

    public static TaleOfTheRighteous get() {
        return quest == null ? quest = new TaleOfTheRighteous() : quest;
    }

    //Items Required
    ItemRequirement pickaxe, rangedWeapon, runesForCombat, rope, combatGear, meleeWeapon;

    //Items Recommended
    ItemRequirement antiPoison, xericTalisman;

    Requirement inArchive, inPuzzleRoom, strangeObjectEast, strangeObjectWest, isSouthWestWhite, isSouthEastWhite,
            isNorthWestWhite, isNorthEastWhite, inShiroRoom, inCavern, rockfallNearby, boulderBlockingPath, corruptLizardmanNearby;

    QuestStep talkToPhileas, teleportToArchive, talkToPagida, pushStrangeDeviceWest, attackWithMagic, attackWithMelee,
            pushStrangeDeviceEast, attackWithRanged, investigateSkeleton, talkToPhileasAgain, goUpToShiro, talkToShiro,
            talkToDuffy, useRopeOnCrevice, enterCrevice, mineRockfall, pushBoulder, tryToEnterBarrier, killLizardman,
            inspectUnstableAltar, leaveCave, returnToDuffy, enterCreviceAgain, talkToDuffyInCrevice, talkToGnosi,
            returnUpToShiro, returnToShiro, returnToPhileasTent, goUpToShrioToFinish, finishQuest;

    //Zones
    RSArea archive, puzzleRoom, shiroRoom, cavern;


    public Map<Integer, QuestStep> loadSteps()
    {
        loadZones();
        setupItemRequirements();
        setupConditions();
        setupSteps();
        Map<Integer, QuestStep> steps = new HashMap<>();

        steps.put(0, talkToPhileas);

        ConditionalStep travelToPuzzle = new ConditionalStep( teleportToArchive);
        travelToPuzzle.addStep(new Conditions(inPuzzleRoom, strangeObjectEast, isSouthWestWhite, isNorthWestWhite), attackWithRanged);
        travelToPuzzle.addStep(new Conditions(inPuzzleRoom, isSouthWestWhite, isNorthWestWhite), pushStrangeDeviceEast);
        travelToPuzzle.addStep(new Conditions(inPuzzleRoom, strangeObjectWest, isSouthWestWhite), attackWithMelee);
        travelToPuzzle.addStep(new Conditions(inPuzzleRoom, strangeObjectWest), attackWithMagic);
        travelToPuzzle.addStep(inPuzzleRoom, pushStrangeDeviceWest);
        travelToPuzzle.addStep(inArchive, talkToPagida);

        steps.put(1, travelToPuzzle);
        steps.put(2, travelToPuzzle);
        steps.put(3, travelToPuzzle);

        ConditionalStep getSkeletonItem = new ConditionalStep( teleportToArchive);
        getSkeletonItem.addStep(inPuzzleRoom, investigateSkeleton);
        getSkeletonItem.addStep(inArchive, talkToPagida);

        steps.put(4, getSkeletonItem);

        steps.put(5, talkToPhileasAgain);

        ConditionalStep reportToShiro = new ConditionalStep( goUpToShiro);
        reportToShiro.addStep(inShiroRoom, talkToShiro);

        steps.put(6, reportToShiro);

        steps.put(7, talkToDuffy);

        steps.put(8, useRopeOnCrevice);

        ConditionalStep goIntoCavern = new ConditionalStep( enterCrevice);
        goIntoCavern.addStep(corruptLizardmanNearby, killLizardman);
        goIntoCavern.addStep(new Conditions(inCavern, rockfallNearby), mineRockfall);
        goIntoCavern.addStep(new Conditions(inCavern, boulderBlockingPath), pushBoulder);
        goIntoCavern.addStep(inCavern, tryToEnterBarrier);

        steps.put(9, goIntoCavern);

        ConditionalStep investigateAltar = new ConditionalStep( enterCrevice);
        investigateAltar.addStep(inCavern, inspectUnstableAltar);

        steps.put(10, investigateAltar);

        ConditionalStep returnToSurface = new ConditionalStep( returnToDuffy);
        returnToSurface.addStep(inCavern, leaveCave);

        steps.put(11, returnToSurface);

        ConditionalStep enterCaveAgain = new ConditionalStep( enterCreviceAgain);
        enterCaveAgain.addStep(inCavern, talkToDuffyInCrevice);

        steps.put(12, enterCaveAgain);

        ConditionalStep talkWithGnosi = new ConditionalStep( enterCreviceAgain);
        talkWithGnosi.addStep(inCavern, talkToGnosi);

        steps.put(13, talkWithGnosi);

        ConditionalStep reportBackToShiro = new ConditionalStep( returnUpToShiro);
        reportBackToShiro.addStep(inShiroRoom, returnToShiro);

        steps.put(14, reportBackToShiro);

        steps.put(15, returnToPhileasTent);

        ConditionalStep finishOffTheQuest = new ConditionalStep( goUpToShrioToFinish);
        finishOffTheQuest.addStep(inShiroRoom, finishQuest);

        steps.put(16, finishOffTheQuest);

        return steps;
    }

    public void setupItemRequirements()
    {
        pickaxe = new ItemRequirement("A pickaxe", ItemCollections.getPickaxes());
        rangedWeapon = new ItemRequirement("Any ranged weapon + ammo", -1, -1);
        //rangedWeapon.setDisplayItemId(BankSlotIcons.getRangedCombatGear());
        runesForCombat = new ItemRequirement("Runes for a few casts of a combat spell", -1, -1);
       // runesForCombat.setDisplayItemId(ItemID.DEATH_RUNE);
        rope = new ItemRequirement("Rope", ItemID.ROPE);
        combatGear = new ItemRequirement("Combat gear for a level 46 corrupt lizardman", -1, -1);
        //combatGear.setDisplayItemId(BankSlotIcons.getCombatGear());
        xericTalisman = new ItemRequirement("Xeric's Talisman", ItemID.XERICS_TALISMAN);
        meleeWeapon = new ItemRequirement("A melee weapon, or your bare hands", -1, -1);
       // meleeWeapon.setDisplayItemId(BankSlotIcons.getCombatGear());
        antiPoison = new ItemRequirement("Antipoison for lizardmen", ItemCollections.getAntipoisons());
    }

    public void loadZones()
    {
        archive = new RSArea(new RSTile(1538, 10210, 0), new RSTile(1565, 10237, 0));
        puzzleRoom = new RSArea(new RSTile(1563, 10186, 0), new RSTile(1591, 10213, 0));
        shiroRoom = new RSArea(new RSTile(1480, 3640, 1), new RSTile(1489, 3631, 1));
        cavern = new RSArea(new RSTile(1157, 9928, 0), new RSTile(1205, 9977, 0));
    }

    public void setupConditions()
    {
        inArchive = new AreaRequirement(archive);
        inPuzzleRoom = new AreaRequirement(puzzleRoom);
        inShiroRoom = new AreaRequirement(shiroRoom);
        inCavern = new AreaRequirement(cavern);
        strangeObjectEast = new NpcCondition(NpcID.STRANGE_DEVICE, new RSTile(1581, 10200, 0));
        strangeObjectWest = new NpcCondition(NpcID.STRANGE_DEVICE, new RSTile(1575, 10200, 0));
        isSouthWestWhite = new ObjectCondition(ObjectID.WHITE_CRYSTAL_31960, new RSTile(1574, 10196, 0));
        isSouthEastWhite = new ObjectCondition(ObjectID.WHITE_CRYSTAL_31960, new RSTile(1581, 10196, 0));
        isNorthWestWhite = new ObjectCondition(ObjectID.WHITE_CRYSTAL_31960, new RSTile(1574, 10203, 0));
        isNorthEastWhite = new ObjectCondition(ObjectID.WHITE_CRYSTAL_31960, new RSTile(1581, 10203, 0));
        rockfallNearby = new ObjectCondition(ObjectID.BOULDER_32503, new RSTile(1182, 9974, 0));
        boulderBlockingPath = new ObjectCondition(ObjectID.BOULDER_32504, new RSTile(1201, 9960, 0));
        corruptLizardmanNearby = new NpcCondition(NpcID.CORRUPT_LIZARDMAN_8000);
    }

    public void setupSteps()
    {
        talkToPhileas = new NPCStep( NpcID.PHILEAS_RIMOR, new RSTile(1542, 3570, 0), "Talk to Phileas Rimor in Shayzien.");
        talkToPhileas.addDialogStep("Do you need help with anything?");
        talkToPhileas.addDialogStep("Yes.");
        talkToPhileas.addDialogStep("What do you need?");
        teleportToArchive = new NPCStep( NpcID.ARCHEIO, new RSTile(1625, 3808, 0),
                //"Bring a melee weapon, " +
              rangedWeapon, runesForCombat);
        teleportToArchive.addDialogStep("Yes please!");
        talkToPagida = new NPCStep( NpcID.PAGIDA, new RSTile(1553, 10223, 0),
                "Talk to Pagida in the Library Historical Archive.");
        talkToPagida.addDialogStep("I have a question about King Shayzien VII.");
        talkToPagida.addDialogStep("Yes please.");
        pushStrangeDeviceWest = new NPCStep( NpcID.STRANGE_DEVICE, new RSTile(1580, 10199, 0),
                "Push the Strange Device all the way to the west.");
        pushStrangeDeviceEast = new NPCStep( NpcID.STRANGE_DEVICE, new RSTile(1580, 10199, 0),
                "Push the Strange Device all the way to the east.");
        attackWithMagic = new NPCStep( NpcID.STRANGE_DEVICE, new RSTile(1580, 10199, 0),
                runesForCombat);

        attackWithRanged = new NPCStep( NpcID.STRANGE_DEVICE, new RSTile(1580, 10199, 0),
            rangedWeapon);
        attackWithMelee = new NPCStep( NpcID.STRANGE_DEVICE, new RSTile(1580, 10199, 0),
  meleeWeapon);

        investigateSkeleton = new ObjectStep( ObjectID.SKELETON_31962, new RSTile(1577, 10213, 0),
                "Investigate the skeleton in the north cell.");

        talkToPhileasAgain = new NPCStep( NpcID.PHILEAS_RIMOR, new RSTile(1542, 3570, 0),
                "Report back to Phileas Rimor in Shayzien.");
        goUpToShiro = new ObjectStep( ObjectID.LADDER_42207, new RSTile(1481, 3633, 0),
                "Talk to Shiro upstairs in the War Tent located on the west side of the Shayzien Encampment.");
        talkToShiro = new NPCStep( NpcID.LORD_SHIRO_SHAYZIEN_11038, new RSTile(1484, 3634, 1),
                "Talk to Shiro upstairs in the War Tent located on the west side of the Shayzien Encampment.");
        talkToShiro.addSubSteps(goUpToShiro);

        talkToDuffy = new NPCStep( NpcID.HISTORIAN_DUFFY_8163, new RSTile(1278, 3561, 0),
                "Travel to Mount Quidamortem and talk to Historian Duffy.");
        useRopeOnCrevice = new ObjectStep( NullObjectID.NULL_32502, new RSTile(1215, 3559, 0),
                "Use a rope on the crevice west side of Quidamortem.", rope, pickaxe, combatGear);
        enterCrevice = new ObjectStep( NullObjectID.NULL_32502, new RSTile(1215, 3559, 0),
                "Enter the crevice west side of Quidamortem, ready to fight a corrupted lizardman (level 46).", pickaxe, combatGear);
        enterCrevice.addDialogStep("Yes.");
        mineRockfall = new ObjectStep( ObjectID.BOULDER_32503, new RSTile(1182, 9974, 0), "Mine the rockfall.", pickaxe);
        pushBoulder = new ObjectStep( ObjectID.BOULDER_32504, new RSTile(1201, 9960, 0),
                "Push the boulder further along the path.");

        tryToEnterBarrier = new ObjectStep( ObjectID.MAGIC_GATE, new RSTile(1172, 9947, 0),
                "Attempt to enter the magic gate to the south room. You will need to kill the corrupted lizardman who appears.");
        killLizardman = new NPCStep( NpcID.CORRUPT_LIZARDMAN_8000, new RSTile(1172, 9947, 0),
                "Kill the corrupt lizardman.");
        tryToEnterBarrier.addSubSteps(killLizardman);

        inspectUnstableAltar = new ObjectStep( ObjectID.UNSTABLE_ALTAR, new RSTile(1172, 9929, 0),
                "Inspect the Unstable Altar in the south room.");
        leaveCave = new ObjectStep( ObjectID.ROPE_31967, new RSTile(1168, 9973, 0),
                "Leave the cavern. You can hop worlds to appear back at the entrance.");
        returnToDuffy = new NPCStep( NpcID.HISTORIAN_DUFFY_8163, new RSTile(1278, 3561, 0),
                "Return to Historian Duffy.");
        enterCreviceAgain = new ObjectStep( NullObjectID.NULL_32502, new RSTile(1215, 3559, 0),
                "Enter the crevice west side of Quidamortem again.");
        enterCreviceAgain.addDialogStep("Yes.");
        talkToDuffyInCrevice = new NPCStep( NpcID.HISTORIAN_DUFFY, new RSTile(1172, 9929, 0),
                "Talk to Historian Duffy near the Unstable Altar.");
        talkToGnosi = new NPCStep( NpcID.GNOSI, new RSTile(1172, 9929, 0),
                "Talk to Gnosi near the Unstable Altar.");
        returnUpToShiro = new ObjectStep( ObjectID.LADDER_42207, new RSTile(1481, 3633, 0),
                "Return to Shiro upstairs in the War Tent located on the west side of the Shayzien Encampment.");
        returnToShiro = new NPCStep( NpcID.LORD_SHIRO_SHAYZIEN_11038, new RSTile(1484, 3634, 1),
                "Return to Shiro upstairs in the War Tent located on the west side of the Shayzien Encampment.");
        returnToShiro.addSubSteps(returnUpToShiro);
        //returnToPhileasTent = new DetailedQuestStep( new RSTile(1542, 3570, 0), "Return to Phileas Rimor's house in Shayzien.");
        goUpToShrioToFinish = new ObjectStep( ObjectID.LADDER_42207, new RSTile(1481, 3633, 0),
                "Return to Shiro upstairs in the War Tent in west Shayzien to complete the quest.");
        finishQuest = new NPCStep( NpcID.LORD_SHIRO_SHAYZIEN_11038, new RSTile(1484, 3634, 1),
                "Return to Shiro upstairs in the War Tent in west Shayzien to complete the quest.");
        finishQuest.addSubSteps(goUpToShrioToFinish);
    }

    public List<ItemRequirement> getItemRequirements()
    {
        ArrayList<ItemRequirement> reqs = new ArrayList<>();
        reqs.add(pickaxe);
        reqs.add(runesForCombat);
        reqs.add(rangedWeapon);
        reqs.add(rope);
        return reqs;
    }


    public List<ItemRequirement> getItemRecommended()
    {
        ArrayList<ItemRequirement> reqs = new ArrayList<>();
        reqs.add(xericTalisman);
        reqs.add(antiPoison);
        return reqs;
    }

    @Override
    public List<Requirement> getGeneralRequirements() {
        ArrayList<Requirement> req = new ArrayList<>();
       //req.add(new QuestRequirement(QuestHelperQuest.X_MARKS_THE_SPOT, QuestState.FINISHED));
        //req.add(new QuestRequirement(QuestHelperQuest.CLIENT_OF_KOUREND, QuestState.FINISHED));
        //req.add(new FavourRequirement(Favour.SHAYZIEN, 20));
        req.add(new SkillRequirement(Skills.SKILLS.STRENGTH, 16));
        req.add(new SkillRequirement(Skills.SKILLS.MINING, 10));
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

    }

    @Override
    public String questName() {
        return "Tale Of The Righteous";
    }

    @Override
    public boolean checkRequirements() {
        return false;
    }

    @Override
    public List<ItemRequirement> getBuyList() {
        return null;
    }
}
