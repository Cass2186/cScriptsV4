package scripts.QuestPackages.CreatureOfFenkenstrain;

import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.Game;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.Quest;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.EnterTheAbyss.EnterTheAbyss;
import scripts.QuestSteps.*;
import scripts.Requirements.*;
import scripts.Requirements.ItemReq;
import scripts.Requirements.Util.Conditions;
import scripts.Requirements.Util.LogicType;
import scripts.Tasks.Priority;

import java.util.*;

public class CreatureOfFenkenstrain implements QuestTask {


    private static CreatureOfFenkenstrain quest;

    public static CreatureOfFenkenstrain get() {
        return quest == null ? quest = new CreatureOfFenkenstrain() : quest;
    }

    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.SALVE_GRAVEYARD_TELEPORT, 6, 50),
                    new GEItem(ItemID.HAMMER, 1, 500),
                    new GEItem(ItemID.SILVER_BAR, 1, 200),
                    new GEItem(ItemID.BRONZE_WIRE, 3, 300),
                    new GEItem(ItemID.NEEDLE, 1, 500),
                    new GEItem(ItemID.THREAD, 5, 500),
                    new GEItem(ItemID.SPADE, 1, 500),
                    new GEItem(ItemID.RUNE_SCIMITAR, 1, 50),
                    new GEItem(ItemID.MONKFISH, 10, 50),
                    new GEItem(ItemID.AMULET_OF_GLORY[2], 1, 50),
                    new GEItem(ItemID.STAMINA_POTION[0], 3, 15),
                    new GEItem(ItemID.RING_OF_WEALTH[0], 1, 25)
            )
    );

    InventoryRequirement initialItemReqs = new InventoryRequirement(new ArrayList<>(
            Arrays.asList(
                    new ItemReq(ItemID.MONKFISH, 8, 1),
                    new ItemReq(ItemID.RUNE_SCIMITAR, 1, 1, true, true),
                    new ItemReq(ItemID.SALVE_GRAVEYARD_TELEPORT, 6, 1),
                    new ItemReq(ItemID.HAMMER, 1, 1),

                    new ItemReq(ItemID.SILVER_BAR, 1, 1),
                    new ItemReq(ItemID.BRONZE_WIRE, 3, 3),
                    new ItemReq(ItemID.NEEDLE, 1, 1),
                    new ItemReq(ItemID.THREAD, 5, 5),
                    new ItemReq(ItemID.SPADE, 1),
                    new ItemReq(ItemID.COINS, 5000, 200),
                    new ItemReq(ItemID.AMULET_OF_GLORY[2], 1, 0, true, true),
                    new ItemReq(ItemID.STAMINA_POTION[0], 3, 0),
                    new ItemReq(ItemID.RING_OF_WEALTH[0], 1, 0, true, true)
            )
    ));

    BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);


    RSArea barZone = new RSArea(new RSTile(3488, 3477, 0), new RSTile(3504, 3471, 0));
    RSArea castleZoneFloor0 = new RSArea(new RSTile(3526, 3574, 0), new RSTile(3566, 3531, 0));
    RSArea castleZoneFloor1 = new RSArea(new RSTile(3526, 3574, 1), new RSTile(3566, 3531, 1));
    RSArea experimentCave = new RSArea(new RSTile(3466, 9921, 0), new RSTile(3582, 9982, 0));
    RSArea graveIsland = new RSArea(new RSTile(3484, 3585, 0), new RSTile(3517, 3561, 0));
    RSArea castleTower = new RSArea(new RSTile(3544, 3543, 2), new RSTile(3552, 3536, 2));
    RSArea monsterTower = new RSArea(new RSTile(3544, 3558, 2), new RSTile(3553, 3551, 2));
    ItemReq canes = new ItemReq("Garden Cane", ItemID.GARDEN_CANE);
    ItemReq extendedBrush3 = new ItemReq("Extended Brush", ItemID.EXTENDED_BRUSH_4193);
    ItemReq conductorMould = new ItemReq("Conductor Mold", ItemID.CONDUCTOR_MOULD);
    ItemReq lightningRod = new ItemReq("Lightning Rod", ItemID.CONDUCTOR);
    ItemReq towerKey = new ItemReq("Tower Key", ItemID.TOWER_KEY);
    ItemReq pickledBrain = new ItemReq("Pickled Brain", ItemID.PICKLED_BRAIN);
    ItemReq obsidianAmulet = new ItemReq("Obsidian Amulet", ItemID.OBSIDIAN_AMULET);
    ItemReq marbleAmulet = new ItemReq("Marble Amulet", ItemID.MARBLE_AMULET);
    ItemReq starAmulet = new ItemReq("Star Amulet", ItemID.STAR_AMULET);
    ItemReq decapitatedHead = new ItemReq("Decapitated Head", ItemID.DECAPITATED_HEAD);
    ItemReq decapitatedHeadWithBrain = new ItemReq( ItemID.DECAPITATED_HEAD_4198);
    ItemReq armor = new ItemReq("Armour and weapons defeat a level 51 monster and run past level 72 monsters", -1, -1);

    ItemReq cavernKey = new ItemReq("Tavern Key", ItemID.CAVERN_KEY);
    ItemReq torso = new ItemReq("Torso", ItemID.TORSO);
    ItemReq legs = new ItemReq("Legs", ItemID.LEGS);
    ItemReq arms = new ItemReq("Arms", ItemID.ARMS);
    ItemReq shedKey = new ItemReq("Shed key", ItemID.SHED_KEY);
    ItemReq fenkenstrainTeleports = new ItemReq("Fenkenstrain's Castle Teleport", ItemID.FENKENSTRAINS_CASTLE_TELEPORT, 2);
    ItemReq teleportToFurnace = new ItemReq(ItemID.AMULET_OF_GLORY[2]);
    ItemReq coins = new ItemReq("Coins at least", ItemID.COINS_995, 100);
    Conditions hasCavernKey = new Conditions(LogicType.OR, cavernKey, new VarbitRequirement(199, 1));
    ItemOnTileRequirement keyNearby = new ItemOnTileRequirement(cavernKey);
    Conditions hasTorso = new Conditions(LogicType.OR, torso, new VarbitRequirement(188, 1));
    Conditions hasLegs = new Conditions(LogicType.OR, legs, new VarbitRequirement(187, 1));
    Conditions hasArm = new Conditions(LogicType.OR, arms, new VarbitRequirement(186, 1));
    Conditions hasDecapitatedHeadWithBrain = new Conditions(LogicType.OR,
            decapitatedHeadWithBrain, new VarbitRequirement(189, 1));

    AreaRequirement inCanifisBar = new AreaRequirement(barZone);
    AreaRequirement inCastleFloor0 = new AreaRequirement(castleZoneFloor0);
    AreaRequirement inCastleFloor1 = new AreaRequirement(castleZoneFloor1);
    VarbitRequirement putStarOnGrave = new VarbitRequirement(192, 1);

    Conditions hasMarbleAmulet = new Conditions(LogicType.OR, marbleAmulet, putStarOnGrave);
    Conditions hasObsidianAmulet = new Conditions(LogicType.OR, obsidianAmulet, putStarOnGrave);
    Conditions hasStarAmulet = new Conditions(LogicType.OR, starAmulet, putStarOnGrave);

    Requirement followingGardenerForHead, inExperiementCave, inGraveIsland, inCastleTower,
            usedTowerKey, inMonsterTower, usedShedKey;

    ClickItemStep getTorso = new ClickItemStep(ItemID.SPADE, "Dig", new RSTile(3503, 3576, 0));
    ClickItemStep getArm = new ClickItemStep(ItemID.SPADE, "Dig", new RSTile(3504, 3576, 0));
    ClickItemStep getLeg = new ClickItemStep(ItemID.SPADE, "Dig", new RSTile(3505, 3576, 0));

    QuestStep getPickledBrain, talkToFrenkenstrain, goUpstairsForStar, combineAmulet, pickupKey,
            goDownstairsForStar, talkToGardenerForHead, goToHeadGrave, combinedHead,  killExperiment,
            leaveExperimentCave, deliverBodyParts, gatherNeedleAndThread, talkToGardenerForKey, searchForBrush,
            grabCanes, extendBrush, goUpWestStairs, searchFirePlace, makeLightningRod, goUpWestStairsWithRod,
            goUpTowerLadder,
            repairConductor, goBackToFirstFloor, talkToFenkenstrainAfterFixingRod, goToMonsterFloor1,
            goToMonsterFloor2,
            talkToMonster, pickPocketFenkenstrain, enterExperimentCave;
    UseItemOnObjectStep openLockedDoor,useStarOnGrave;
    ObjectStep getBook1, getBook2;


    public void buyItems() {
        if (!initialItemReqs.check()) {
            cQuesterV2.status = "Buying Items";
            General.println("[Debug]: " + cQuesterV2.status);
            buyStep.buyItems();
            initialItemReqs.withdrawItems();
        }
    }
/*
        Map<Integer, QuestStep> steps = new HashMap<>();

        ConditionalStep grabTheBrain = new ConditionalStep(getPickledBrain);
        grabTheBrain.addStep(pickledBrain, talkToFrenkenstrain);
        steps.put(0, grabTheBrain);





        ConditionalStep fixLightningRod = new ConditionalStep(talkToGardenerForKey);
        fixLightningRod.addStep(new Conditions(inCastleTower, lightningRod), repairConductor);
        fixLightningRod.addStep(new Conditions(lightningRod, inCastleFloor1), goUpTowerLadder);
        fixLightningRod.addStep(new Conditions(lightningRod), goUpWestStairs);
        fixLightningRod.addStep(new Conditions(conductorMould), makeLightningRod);
        fixLightningRod.addStep(new Conditions(inCastleFloor1, extendedBrush3), searchFirePlace);
        fixLightningRod.addStep(new Conditions(extendedBrush3), goUpWestStairs);
        fixLightningRod.addStep(new Conditions(LogicType.AND, brush, canes), extendBrush);
        fixLightningRod.addStep(new Conditions(brush), grabCanes);
        fixLightningRod.addStep(new Conditions(LogicType.OR, usedShedKey, shedKey), searchForBrush);

        ConditionalStep talkToFenkentrain = new ConditionalStep(goBackToFirstFloor);
        talkToFenkentrain.addStep(inCastleFloor0, talkToFenkenstrainAfterFixingRod);

        ConditionalStep goToMonster = new ConditionalStep(goToMonsterFloor1);
        goToMonster.addStep(inMonsterTower, talkToMonster);
        goToMonster.addStep(new Conditions(usedTowerKey, inCastleFloor1), goToMonsterFloor2);
        goToMonster.addStep(new Conditions(towerKey, inCastleFloor1), openLockedDoor);

        steps.put(1, gatherBodyParts);
        steps.put(2, gatherNeedleAndThread);
        steps.put(3, fixLightningRod);
        steps.put(4, talkToFenkentrain);
        steps.put(5, goToMonster);
        steps.put(6, pickPocketFenkenstrain);

        return steps;*/


    ItemReq brush = new ItemReq("Brush", ItemID.GARDEN_BRUSH);
    ItemReq coins50 = new ItemReq("Coins", ItemID.COINS_995, 50);

    ItemReq hammer = new ItemReq("Hammer", ItemID.HAMMER);
    ItemReq ghostSpeakAmulet = new ItemReq("Ghostspeak amulet", ItemID.GHOSTSPEAK_AMULET);
    ItemReq silverBar = new ItemReq("Silver bar", ItemID.SILVER_BAR);
    ItemReq bronzeWire = new ItemReq("Bronze wires", ItemID.BRONZE_WIRE, 3);
    ItemReq needle = new ItemReq("Needle", ItemID.NEEDLE);
    ItemReq thread = new ItemReq("Threads", ItemID.THREAD, 5);
    ItemReq spade = new ItemReq("Spade", ItemID.SPADE);


    public void setupItemReqs() {
        //ItemReq telegrab = new ItemReq("Telegrab runes", new ItemReq("Law rune",
        //        ItemID.LAW_RUNE), new ItemReq("Air rune", ItemID.AIR_RUNE));


        brush.addAlternateItemID(ItemID.EXTENDED_BRUSH, ItemID.EXTENDED_BRUSH_4192);

        teleportToFurnace.addAlternateItemID(ItemID.AMULET_OF_GLORY[3], ItemID.AMULET_OF_GLORY[4], ItemID.AMULET_OF_GLORY[5]);
        //  staminaPotion = new ItemReq("Stamina potions", ItemCollections.getStaminaPotions(), -1);
    }


    public void setupConditions() {
        followingGardenerForHead = new VarbitRequirement(185, 1);
        inExperiementCave = new AreaRequirement(experimentCave);
        inGraveIsland = new AreaRequirement(graveIsland);

        // Needle given, 190 = 1
        // Thread given, 191 0->5

        usedShedKey = new VarbitRequirement(200, 1);
        inCastleTower = new AreaRequirement(castleTower);

        usedTowerKey = new VarbitRequirement(198, 1);
        inMonsterTower = new AreaRequirement(monsterTower);
    }

    public void extendBrushStep() {
        RSItem[] cane = Inventory.find(canes.getId());
        for (RSItem c : cane) {
            if (Utils.useItemOnItem(c.getID(), brush.getId()))
                Utils.microSleep();

        }
    }

    public void setupSteps() {
        ///  getPickledBrain = new DetailedQuestStep(new RSTile(3492, 3474, 0),
        //           "Head to the Canifis bar and either buy the pickled brain for 50 coins, or telegrab it.", telegrabOrCoins);
        // getPickledBrain.addDialogStep("I'll buy one.");
        talkToFrenkenstrain = new NPCStep(NpcID.DR_FENKENSTRAIN, new RSTile(3551, 3548, 0),
                "Talk to Dr.Fenkenstrain to start the quest");
        talkToFrenkenstrain.addDialogStep("Yes.");
        talkToFrenkenstrain.addDialogStep("Braindead");
        talkToFrenkenstrain.addDialogStep("Grave-digging");

        goUpstairsForStar = new ObjectStep(ObjectID.STAIRCASE_5206, new RSTile(3560, 3552, 0),
                "Climb-up");

        getBook1 = new ObjectStep(5166, new RSTile(3555, 3557, 1),
                "Search", NPCInteraction.isConversationWindowUp());
        getBook1.addDialogStep("Handy Maggot Avoidance Techniques");

        getBook2 = new ObjectStep(5166, new RSTile(3542, 3557, 1),
                "Search", NPCInteraction.isConversationWindowUp());
        getBook2.addDialogStep("The Joy of Gravedigging");

        combineAmulet = new UseItemOnItemStep(marbleAmulet.getId(),
                obsidianAmulet.getId(), !marbleAmulet.check());

        goDownstairsForStar = new ObjectStep(ObjectID.STAIRCASE_5207, new RSTile(3573, 3553, 1),
                "Climb-down");

        talkToGardenerForHead = new NPCStep(2012, new RSTile(3548, 3562, 0),
                ghostSpeakAmulet);
        talkToGardenerForHead.addDialogStep("What happened to your head?");

        goToHeadGrave = new ClickItemStep(ItemID.SPADE, "Dig", new RSTile(3608, 3490, 0));

        combinedHead = new UseItemOnItemStep(decapitatedHead.getId(), pickledBrain.getId(),
                decapitatedHeadWithBrain.check());

        useStarOnGrave = new UseItemOnObjectStep(starAmulet.getId(),
                ObjectID.MEMORIAL, new RSTile(3578, 3526, 0), !starAmulet.check());

        enterExperimentCave = new ObjectStep(ObjectID.MEMORIAL, new RSTile(3578, 3528, 0),
                "Push");

        killExperiment = new NPCStep(NpcID.EXPERIMENT, new RSTile(3557, 9946, 0));

        pickupKey = new GroundItemStep(cavernKey);

        leaveExperimentCave = new ObjectStep(17387, new RSTile(3505, 9970, 0),
                "Climb-up");


        deliverBodyParts = new NPCStep(NpcID.DR_FENKENSTRAIN, new RSTile(3551, 3548, 0),
                "Deliver the body parts to Dr.Fenkenstrain, use a teleport to Fenkenstrain's castle or run back through " +
                        "the caves.");
        deliverBodyParts.addDialogStep("I have some body parts for you.");

        gatherNeedleAndThread = new NPCStep(NpcID.DR_FENKENSTRAIN, new RSTile(3551, 3548, 0),
                needle, thread);

        talkToGardenerForKey = new NPCStep(NpcID.GARDENER_GHOST, new RSTile(3548, 3562, 0),
                ghostSpeakAmulet, bronzeWire, silverBar);
        talkToGardenerForKey.addDialogStep("Do you know where the key to the shed is?");

        searchForBrush = new ObjectStep(5156, new RSTile(3546, 3564, 0),
                "Open the cupboard and search it for a brush.", bronzeWire, silverBar);
        //   ((ObjectStep) searchForBrush).addAlternateObjects(ObjectID.OPEN_CUPBOARD_5157);
        grabCanes = new ObjectStep(ObjectID.PILE_OF_CANES, new RSTile(3551, 3564, 0),
                "Grab 3 canes from the pile.", bronzeWire, silverBar);


        goUpWestStairs = new ObjectStep(5206, new RSTile(3538, 3552, 0),
                "Climb-up");
        searchFirePlace = new ObjectStep(5165, new RSTile(3544, 3555, 1),
                "Use the extended brush on the fireplace to get the conductor mould.", extendedBrush3);

        //  makeLightningRod = new DetailedQuestStep( silverBar, conductorMould);
        goUpWestStairsWithRod = new ObjectStep(5206, new RSTile(3537, 3553, 0),
                "Return to the castle and go upstairs.");
        goUpTowerLadder = new ObjectStep(16683, new RSTile(3548, 3539, 1),
                "Go up to the third floor using the ladder in the middle of the castle.");
        repairConductor = new ObjectStep(ObjectID.LIGHTNING_CONDUCTOR, new RSTile(3549, 3537, 2),
                "Repair the lightning Conductor.");

        //goBackToFirstFloor = new DetailedQuestStep("Go back to the first floor of the castle and talk to Dr.Fenkenstrain.");
        talkToFenkenstrainAfterFixingRod = new NPCStep(NpcID.DR_FENKENSTRAIN, new RSTile(3551, 3548, 0),
                "Go back to the first floor of the castle and talk to Dr.Fenkenstrain.");

        goToMonsterFloor1 = new ObjectStep(ObjectID.STAIRCASE_5206, new RSTile(3538, 3552, 0),
                "Go up to the second floor to confront Fenkenstrain's monster.");
        openLockedDoor = new UseItemOnObjectStep(towerKey.getId(), ObjectID.DOOR_5172,
                new RSTile(3548, 3551, 1), !towerKey.check());
        goToMonsterFloor2 = new ObjectStep(16683, new RSTile(3548, 3554, 1),
                "Climb-up");
        talkToMonster = new NPCStep(NpcID.FENKENSTRAINS_MONSTER, new RSTile(3548, 3555, 2),
                "Talk to Fenkenstrain's monster.");

        pickPocketFenkenstrain = new NPCStep(NpcID.DR_FENKENSTRAIN, new RSTile(3551, 3548, 0),
                "Go back to Dr.Fenkenstrain, instead of talking to him right click and pickpocket him.");
    }

    public void getBodyParts() {
        if (hasDecapitatedHeadWithBrain.check() && hasArm.check() && hasLegs.check() && hasTorso.check()) {
            cQuesterV2.status = "Delivering body parts";
            deliverBodyParts.execute();
        } else if (inGraveIsland.check()&&  hasDecapitatedHeadWithBrain.check() && hasArm.check() &&
                !hasLegs.check() && hasTorso.check()) {
            cQuesterV2.status = "Getting Leg";
            getLeg.execute();
        } else if (inGraveIsland.check()&& hasDecapitatedHeadWithBrain.check() && !hasArm.check() &&
                !hasLegs.check() && hasTorso.check()) {
            cQuesterV2.status = "Getting Arm";
            getArm.execute();
        } else if (inGraveIsland.check()&&
                hasDecapitatedHeadWithBrain.check() && !hasArm.check() && !hasLegs.check() && !hasTorso.check()) {
            cQuesterV2.status = "Getting Torso";
            getTorso.execute();
        } else if (inExperiementCave.check() && hasDecapitatedHeadWithBrain.check() && hasCavernKey.check()) {
            cQuesterV2.status = "Leaving cave";
            leaveExperimentCave.execute();
        } else if (inExperiementCave.check() && hasDecapitatedHeadWithBrain.check() && keyNearby.check()) {
            cQuesterV2.status = "Picking up key ";
            pickupKey.execute();
        } else if (inExperiementCave.check() && hasDecapitatedHeadWithBrain.check()) {
            cQuesterV2.status = "Killing experiment";
            killExperiment.execute();
        } else if (putStarOnGrave.check() && hasDecapitatedHeadWithBrain.check()) {
            cQuesterV2.status = "Entering cave";
            enterExperimentCave.execute();
        } else if (hasStarAmulet.check() && hasDecapitatedHeadWithBrain.check()) {
            cQuesterV2.status = "Using Star on grave";
            useStarOnGrave.useItemOnObject();
        } else if (decapitatedHead.check() && pickledBrain.check()) {
            cQuesterV2.status = "Combing head";
            combinedHead.execute();
        } else if (decapitatedHead.check()) {
            cQuesterV2.status = "Getting pickled brain";
            getPickledBrain.execute();
        } else if (inCastleFloor1.check() && hasStarAmulet.check()) {
            goDownstairsForStar.execute();
         } else if (followingGardenerForHead.check()){
            cQuesterV2.status = "Going to grave";
            goToHeadGrave.execute();
        }
        else if (hasStarAmulet.check()) {
            cQuesterV2.status = "Talking to Gardener for head";
            talkToGardenerForHead.execute();
        } else if (hasObsidianAmulet.check() && hasMarbleAmulet.check()) {
            cQuesterV2.status = "Combing amulet";
            combineAmulet.execute();
        } else if (hasObsidianAmulet.check()) {
            cQuesterV2.status = "Getting book 2";
            getBook2.execute();
            NPCInteraction.handleConversation("The Joy of Gravedigging");
        } else {
            cQuesterV2.status = "Getting book 1";
            if (Player.getPosition().getPlane() != 1)
                goUpstairsForStar.execute();
            getBook1.setUseLocalNav(true);
            getBook1.execute();
            NPCInteraction.handleConversation("Handy Maggot Avoidance Techniques");
        }
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
        setupConditions();
        setupSteps();
        buyItems();
        if (Game.getSetting(399) == 0) {
            talkToFrenkenstrain.execute();
        } else if (Game.getSetting(399) == 1) {
            getBodyParts();
        } else if (Game.getSetting(399) == 2) {

        }
    }

    @Override
    public String questName() {
        return "Creature of Fenkenstrain";
    }

    @Override
    public boolean checkRequirements() {
        return Skills.SKILLS.CRAFTING.getActualLevel() >= 20 && Skills.SKILLS.THIEVING.getActualLevel() >= 25;
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
        return Quest.CREATURE_OF_FENKENSTRAIN.getState().equals(Quest.State.COMPLETE);
    }
}
