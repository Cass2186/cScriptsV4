package scripts.QuestPackages.TreeGnomeVillage;

import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.FightArena.FightArena;
import scripts.QuestSteps.*;
import scripts.Requirements.*;
import scripts.Requirements.Util.Operation;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;

import java.util.*;

public class TreeGnomeVillage implements QuestTask {

    private static TreeGnomeVillage quest;

    public static TreeGnomeVillage get() {
        return quest == null ? quest = new TreeGnomeVillage() : quest;
    }
    //Items Required
    ItemReq logRequirement, orbsOfProtection;

    private NPCStep talkToCommanderMontai, bringWoodToCommanderMontai, talkToCommanderMontaiAgain,
            firstTracker, secondTracker, thirdTracker, fireBallista, fireBallista1, fireBallista2, fireBallista3, fireBallista4, climbTheLadder,
            talkToKingBolrenFirstOrb, talkToTheWarlord, fightTheWarlord, returnOrbs, finishQuestDialog, elkoySkip;

    Requirement completeFirstTracker, completeSecondTracker, completeThirdTracker, handedInOrbs,
            notCompleteFirstTracker, notCompleteSecondTracker, notCompleteThirdTracker, orbsOfProtectionNearby, givenWood;

    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemId.GAMES_NECKLACE[0], 2, 0),
                    new GEItem(ItemId.AMULET_OF_GLORY[2], 2, 0)
            )
    );


    BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);

    //Zones
     RSArea upstairsTower, zoneVillage;
    AreaRequirement isUpstairsTower, insideGnomeVillage;
/*
    private final int TRACKER_1_VARBITID = 599;
    private final int TRACKER_2_VARBITID = 600;
    private final int TRACKER_3_VARBITID = 601;

    @Override
    public Map<Integer, QuestStep> loadSteps()
    {
        setupZones();
        ItemReqs();
        setupConditions();
        setupSteps();

        return CreateSteps();
    }

    private Map<Integer, QuestStep> CreateSteps()
    {
        Map<Integer, QuestStep> steps = new HashMap<>();
        steps.put(0, talkToBolrenAtCentreOfMaze);
        steps.put(1, talkToCommanderMontai);
        steps.put(2, bringWoodToCommanderMontai);
        steps.put(3, talkToCommanderMontaiAgain);
        steps.put(4, talkToTrackersStep());
        steps.put(5, retrieveOrbStep());
        steps.put(6, returnFirstOrb);
        steps.put(7, defeatWarlordStep());
        steps.put(8, returnOrbsStep());
        return steps;
    }


    private QuestStep retrieveOrbStep()
    {
        retrieveOrb = new ConditionalStep( climbTheLadder, "Enter the tower by the Crumbled wall and climb the ladder to retrieve the first orb from chest.");
        ObjectStep getOrbFromChest = new ObjectStep( ObjectID.CLOSED_CHEST_2183, new RSTile(2506, 3259, 1), "Retrieve the first orb from chest.");
        getOrbFromChest.addAlternateObjects(ObjectID.OPEN_CHEST_2182);
        retrieveOrb.addStep(isUpstairsTower, getOrbFromChest);
        retrieveOrb.addSubSteps(getOrbFromChest, climbTheLadder);
        return retrieveOrb;
    }

    private QuestStep defeatWarlordStep() {

        fightTheWarlord = new NPCStep( NpcID.KHAZARD_WARLORD_7622, new RSTile(2456, 3301, 0),
                "Defeat the warlord and retrieve orbs.");
        talkToTheWarlord = new NPCStep( NpcID.KHAZARD_WARLORD_7621, new RSTile(2456, 3301, 0),
                "Talk to the Warlord south west of West Ardougne, ready to fight him.");

        ItemReq food = new ItemReq("Food", ItemCollections.getGoodEatingFood(), -1);

        ItemReq combatGear = new ItemReq("A Weapon & Armour (magic is best)", -1);
        combatGear.setDisplayItemId(BankSlotIcons.getMagicCombatGear());

        ConditionalStep defeatTheWarlord = new ConditionalStep( talkToTheWarlord,
                food,
                combatGear);

        defeatTheWarlord.addStep(fightingWarlord, fightTheWarlord);


        return defeatTheWarlord;
    }

    private QuestStep returnOrbsStep()
    {
        handedInOrbs = new VarbitRequirement(598, 1, Operation.GREATER_EQUAL);

        orbsOfProtectionNearby = new ItemOnTileRequirement(ItemId.ORBS_OF_PROTECTION);

        ItemStep pickupOrb = new ItemStep(
                "Pick up the nearby Orbs of Protection.", orbsOfProtection);
        returnOrbs.addSubSteps(pickupOrb);

        ConditionalStep returnOrbsSteps = new ConditionalStep( returnOrbs);
        returnOrbsSteps.addStep(orbsOfProtectionNearby, pickupOrb);
        returnOrbsSteps.addStep(handedInOrbs, finishQuestDialog);

        return returnOrbsSteps;
    }

    private void setupItemReqs()
    {
        givenWood = new VarplayerRequirement(QuestVarPlayer.QUEST_TREE_GNOME_VILLAGE.getId(), 3, Operation.GREATER_EQUAL);
        logRequirement = new ItemReq("Logs", ItemId.LOGS, 6).hideConditioned(givenWood);
        orbsOfProtection = new ItemReq("Orbs of protection", ItemId.ORBS_OF_PROTECTION);
        orbsOfProtection.setTooltip("You can retrieve the orbs of protection again by killing the Khazard Warlord again.");
    }

    private void setupZones() {
        upstairsTower = new Zone(new RSTile(2500, 3251, 1), new RSTile(2506, 3259, 1));
        zoneVillage = new Zone(new RSTile(2514, 3158, 0), new RSTile(2542, 3175, 0));
    }

    public void setupConditions()
    {
        notCompleteFirstTracker = new VarbitRequirement(TRACKER_1_VARBITID, 0);
        notCompleteSecondTracker = new VarbitRequirement(TRACKER_2_VARBITID, 0);
        notCompleteThirdTracker = new VarbitRequirement(TRACKER_3_VARBITID, 0);

        completeFirstTracker = new VarbitRequirement(TRACKER_1_VARBITID, 1);
        completeSecondTracker = new VarbitRequirement(TRACKER_2_VARBITID, 1);
        completeThirdTracker = new VarbitRequirement(TRACKER_3_VARBITID, 1);

        insideGnomeVillage = new ZoneRequirement(zoneVillage);
        isUpstairsTower = new ZoneRequirement(upstairsTower);

        talkToSecondTracker = new Conditions(LogicType.AND, completeFirstTracker, notCompleteSecondTracker);
        talkToThirdTracker = new Conditions(LogicType.AND, completeFirstTracker, notCompleteThirdTracker);

        completedTrackers = new Conditions(LogicType.AND, completeFirstTracker, completeSecondTracker, completeThirdTracker);

        shouldFireBallista1 = new Conditions(LogicType.AND, completedTrackers, new VarbitRequirement(602, 0));
        shouldFireBallista2 = new Conditions(LogicType.AND, completedTrackers, new VarbitRequirement(602, 1));
        shouldFireBallista3 = new Conditions(LogicType.AND, completedTrackers, new VarbitRequirement(602, 2));
        shouldFireBallista4 = new Conditions(LogicType.AND, completedTrackers, new VarbitRequirement(602, 3));
    }

    private void setupSteps()
    {
        NPCStep talkToKingBolren = new NPCStep( NpcID.KING_BOLREN, new RSTile(2541, 3170, 0), "");
        talkToKingBolren.addDialogStep("Can I help at all?");
        talkToKingBolren.addDialogStep("I would be glad to help.");


        talkToCommanderMontai = new NPCStep( NpcID.COMMANDER_MONTAI, new RSTile(2523, 3208, 0), "Speak with Commander Montai.");
        talkToCommanderMontai.addDialogStep("Ok, I'll gather some wood.");

        bringWoodToCommanderMontai = new NPCStep( NpcID.COMMANDER_MONTAI, new RSTile(2523, 3208, 0), "Speak with Commander Montai again to give him the wood.", logRequirement);

        talkToCommanderMontaiAgain = new NPCStep( NpcID.COMMANDER_MONTAI, new RSTile(2523, 3208, 0), "Speak with Commander Montai.");
        talkToCommanderMontaiAgain.addDialogStep("I'll try my best.");

        firstTracker = new NPCStep( NpcID.TRACKER_GNOME_1, new RSTile(2501, 3261, 0), "Talk to the first tracker gnome to the northwest.");
        secondTracker = new NPCStep( NpcID.TRACKER_GNOME_2, new RSTile(2524, 3257, 0), "Talk to the second tracker gnome inside the jail.");
        thirdTracker = new NPCStep( NpcID.TRACKER_GNOME_3, new RSTile(2497, 3234, 0), "Talk to the third tracker gnome to the southwest.");

        climbTheLadder = new ObjectStep(16683, new RSTile(2503, 3252, 0), "Climb the ladder");

        ItemReq firstOrb = new ItemReq("Orb of protection", ItemId.ORB_OF_PROTECTION, 1);

        talkToKingBolrenFirstOrb = new NPCStep( NpcID.KING_BOLREN, new RSTile(2541, 3170, 0),
                "Speak to King Bolren in the centre of the Tree Gnome Maze.", firstOrb);
        talkToKingBolrenFirstOrb.addDialogStep("I will find the warlord and bring back the orbs.");
        elkoySkip = new NPCStep( NpcID.ELKOY_4968, new RSTile(2505, 3191, 0),
                "Talk to Elkoy outside the maze to travel to the centre.");
        returnFirstOrb = new ConditionalStep( elkoySkip,
                "Speak to King Bolren in the centre of the Tree Gnome Maze.");
        returnFirstOrb.addStep(insideGnomeVillage, talkToKingBolrenFirstOrb);
        returnFirstOrb.addSubSteps(talkToKingBolrenFirstOrb, elkoySkip);

        returnOrbs = new NPCStep( NpcID.KING_BOLREN, new RSTile(2541, 3170, 0),
                "Talk to King Bolren in the centre of the Tree Gnome Maze.", orbsOfProtection);

        finishQuestDialog = new NPCStep( NpcID.KING_BOLREN, new RSTile(2541, 3170, 0),
                "Speak to King Bolren in the centre of the Tree Gnome Maze.");
        returnOrbs.addSubSteps(finishQuestDialog);
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
        buyStep.buyItems();
    }

    @Override
    public String questName() {
        return "Tree gnome village";
    }

    @Override
    public boolean checkRequirements() {
        return Skills.SKILLS.MAGIC.getActualLevel() > 12 &&
                Skills.SKILLS.HITPOINTS.getActualLevel() >= 20;
    }
}
