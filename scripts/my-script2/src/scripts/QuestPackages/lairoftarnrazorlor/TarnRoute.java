package scripts.QuestPackages.lairoftarnrazorlor;

import dax.walker.utils.AccurateMouse;
import dax.walker_engine.WalkerEngine;
import org.tribot.api2007.Player;
import org.tribot.api2007.Prayer;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.Log;
import scripts.*;
import scripts.QuestPackages.HauntedMine.HauntedMine;
import scripts.QuestPackages.HauntedMine.HauntedMineConst;
import scripts.QuestSteps.ObjectStep;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.*;
import scripts.Requirements.Util.Conditions;
import scripts.Requirements.Util.Operation;
import scripts.Tasks.Priority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TarnRoute implements QuestTask {

    private static TarnRoute quest;

    public static TarnRoute get() {
        return quest == null ? quest = new TarnRoute() : quest;
    }
  /*  public void setupItemRequirements() {
        combatGear = new ItemRequirement("Combat gear", -1, -1);
        combatGear.setDisplayItemID(BankSlotIcons.getCombatGear());
        diary = new ItemRequirement("Tarn's diary", ItemID.TARNS_DIARY);
        diary.setHighlightInInventory(true);
        protectFromMagic = new PrayerRequirement("Activate Protect from Magic", Prayer.PROTECT_FROM_MAGIC);
    }*/

    RSArea hauntedMine = new RSArea(new RSTile(3390, 9600, 0), new RSTile(3452, 9668, 0));
    RSArea room1 = new RSArea(new RSTile(3136, 4544, 0), new RSTile(3199, 4570, 0));
    RSArea room1PastTrap1 = new RSArea(new RSTile(3196, 4558, 0), new RSTile(3196, 4562, 0));
    RSArea room1PastTrap2 = new RSArea(new RSTile(3193, 4563, 0), new RSTile(3196, 4570, 0));

    RSArea room2 = new RSArea(new RSTile(3172, 4574, 1), new RSTile(3197, 4589, 1));
    RSArea room3 = new RSArea(new RSTile(3166, 4575, 0), new RSTile(3170, 4579, 0));
    RSArea room4 = new RSArea(new RSTile(3166, 4586, 0), new RSTile(3170, 4592, 0));
    RSArea room5P1 = new RSArea(new RSTile(3143, 4589, 1), new RSTile(3162, 4590, 1));
    RSArea room5P2 = new RSArea(new RSTile(3154, 4590, 1), new RSTile(3157, 4598, 1));

    RSArea room6P1 = new RSArea(new RSTile(3136, 4592, 1), new RSTile(3151, 4600, 1));

    RSArea room6P2 = new RSArea(new RSTile(3141, 4601, 1), new RSTile(3163, 4607, 1));
    RSArea room6P3 = new RSArea(new RSTile(3159, 4596, 1), new RSTile(3175, 4602, 1));

    RSArea pillar1 = new RSArea(new RSTile(3148, 4595, 1), new RSTile(3148, 4595, 1));
    RSArea pillar2 = new RSArea(new RSTile(3146, 4595, 1), new RSTile(3146, 4595, 1));
    RSArea pillar3 = new RSArea(new RSTile(3144, 4595, 1), new RSTile(3144, 4595, 1));
    RSArea pillar4 = new RSArea(new RSTile(3142, 4595, 1), new RSTile(3142, 4595, 1));

    RSArea pillar5 = new RSArea(new RSTile(3144, 4597, 1), new RSTile(3144, 4597, 1));
    RSArea pillar6 = new RSArea(new RSTile(3144, 4599, 1), new RSTile(3144, 4599, 1));

    RSArea switch1 = new RSArea(new RSTile(3137, 4593, 1), new RSTile(3140, 4601, 1));

    RSArea room6PastTrap1 = new RSArea(new RSTile(3150, 4604, 1), new RSTile(3153, 4604, 1));
    RSArea room6PastTrap2P1 = new RSArea(new RSTile(3154, 4604, 1), new RSTile(3160, 4604, 1));
    RSArea room6PastTrap2P2 = new RSArea(new RSTile(3159, 4596, 1), new RSTile(3175, 4602, 1));

    RSArea extraRoom1 = new RSArea(new RSTile(3160, 4597, 0), new RSTile(3173, 4599, 0));
    RSArea extraRoom2 = new RSArea(new RSTile(3140, 4594, 0), new RSTile(3150, 4601, 0));

    RSArea room7 = new RSArea(new RSTile(3179, 4593, 1), new RSTile(3195, 4602, 1));

    RSArea room8 = new RSArea(new RSTile(3181, 4595, 0), new RSTile(3189, 4601, 0));
    RSArea bossRoom = new RSArea(new RSTile(3176, 4611, 0), new RSTile(3196, 4626, 0));
    RSArea finalRoom = new RSArea(new RSTile(3181, 4632, 0), new RSTile(3191, 4637, 0));


    AreaRequirement inHauntedMine = new AreaRequirement(hauntedMine);
    AreaRequirement inRoom1 = new AreaRequirement(room1);
    AreaRequirement inRoom1PastTrap1 = new AreaRequirement(room1PastTrap1);
    AreaRequirement inRoom1PastTrap2 = new AreaRequirement(room1PastTrap2);
    AreaRequirement inRoom2 = new AreaRequirement(room2);
    AreaRequirement inRoom3 = new AreaRequirement(room3);
    AreaRequirement inRoom4 = new AreaRequirement(room4);
    AreaRequirement inRoom5 = new AreaRequirement(room5P1, room5P2);
    AreaRequirement inRoom6P1 = new AreaRequirement(room6P1);
    AreaRequirement inRoom6P2 = new AreaRequirement(room6P2, room6P3);
    AreaRequirement inRoom7 = new AreaRequirement(room7);
    AreaRequirement onPillar1 = new AreaRequirement(pillar1);
    AreaRequirement onPillar2 = new AreaRequirement(pillar2);
    AreaRequirement onPillar3 = new AreaRequirement(pillar3);
    AreaRequirement onPillar4 = new AreaRequirement(pillar4);
    AreaRequirement onPillar5 = new AreaRequirement(pillar5);
    AreaRequirement onPillar6 = new AreaRequirement(pillar6);
    AreaRequirement atSwitch1 = new AreaRequirement(switch1);
    ItemOnTileRequirement switchPressed = new ItemOnTileRequirement(20635, new RSTile(3138, 4595, 1));
    AreaRequirement inRoom6PastTrap1 = new AreaRequirement(room6PastTrap1);
    AreaRequirement inRoom6PastTrap2 = new AreaRequirement(room6PastTrap2P1, room6PastTrap2P2);
    AreaRequirement inExtraRoom1 = new AreaRequirement(extraRoom1);
    AreaRequirement inExtraRoom2 = new AreaRequirement(extraRoom2);
    AreaRequirement inRoom8 = new AreaRequirement(room8);
    AreaRequirement inBossRoom = new AreaRequirement(bossRoom);
    AreaRequirement inFinalRoom = new AreaRequirement(finalRoom);

    //  tarnInSecondForm = new NpcCondition(NpcID.TARN);
    VarbitRequirement killedTarn = new VarbitRequirement(3290, 2, Operation.GREATER_EQUAL);


    ObjectStep enterLair = new ObjectStep(15833, new RSTile(3424, 9660, 0),
            "Enter the entrance to the north.");

    ObjectStep searchWallRoom1 = new ObjectStep(20588, new RSTile(3197, 4557, 0),
            "Search");

    ArrayList<RSTile> searchWallRoom1Tiles = new ArrayList<>(Arrays.asList(
            new RSTile(3166, 4547, 0),
            new RSTile(3166, 4550, 0),
            new RSTile(3175, 4550, 0),
            new RSTile(3175, 4549, 0),
            new RSTile(3184, 4549, 0),
            new RSTile(3184, 4548, 0),
            new RSTile(3189, 4548, 0),
            new RSTile(3190, 4547, 0),
            new RSTile(3191, 4548, 0),
            new RSTile(3190, 4550, 0),
            new RSTile(3190, 4555, 0),
            new RSTile(3196, 4555, 0),
            new RSTile(3196, 4556, 0)
    ));


    ObjectStep searchWall2Room1 = new ObjectStep(20588, new RSTile(3196, 4561, 0),
            "Search");

    ObjectStep goThroughRoom1 = new ObjectStep(20517, new RSTile(3195, 4571, 0),
            "Enter", inRoom2.check());
    //goThroughRoom1.addSubSteps(searchWallRoom1,searchWall2Room1);

    ObjectStep goThroughRoom2 = new ObjectStep(20513, new RSTile(3174, 4577, 1),
            "Enter", inRoom3.check());
    ArrayList<RSTile> room2Tiles = new ArrayList<>(Arrays.asList(
            new RSTile(3195, 4575, 1),
            new RSTile(3195, 4579, 1),
            new RSTile(3196, 4580, 1),
            new RSTile(3195, 4581, 1),
            new RSTile(3195, 4585, 1),
            new RSTile(3182, 4585, 1),
            new RSTile(3181, 4586, 1),
            new RSTile(3176, 4586, 1),
            new RSTile(3176, 4583, 1),
            new RSTile(3175, 4582, 1),
            new RSTile(3175, 4577, 1)
    ));


    ObjectStep goThroughRoom3 = new ObjectStep(20523, new RSTile(3168, 4579, 0),
            "Enter");
    ObjectStep goThroughRoom4 = new ObjectStep(20525, new RSTile(3166, 4589, 0),
            "Enter");

    ObjectStep leaveExtraRoom1 = new ObjectStep(20531, new RSTile(3168, 4596, 0),
            "Enter");
    ObjectStep leaveExtraRoom2 = new ObjectStep(20529, new RSTile(3150, 4598, 0),
            "Enter");

    ObjectStep goThroughRoom5 = new ObjectStep(20533, new RSTile(3155, 4597, 1),
            "Enter", inRoom6P1.check());
    //   goThroughRoom5.addSubSteps(leaveExtraRoom1,leaveExtraRoom2);

    ObjectStep jumpToPillar1 = new ObjectStep(20543, new RSTile(3148, 4595, 1),
            "Jump-to");
    ObjectStep jumpToPillar2 = new ObjectStep(20544, new RSTile(3146, 4595, 1),
            "Jump-to");
    ObjectStep jumpToPillar3 = new ObjectStep(20545, new RSTile(3144, 4595, 1),
            "Jump-to");
    ObjectStep jumpToPillar4 = new ObjectStep(20546, new RSTile(3142, 4595, 1),
            "Jump-to");

    ObjectStep jumpToSwitch = new ObjectStep(20562, new RSTile(3140, 4595, 1),
            "Jump-to");
    //  jumpToPillar1.addSubSteps(jumpToPillar2,jumpToPillar3,jumpToPillar4,jumpToSwitch);

    ObjectStep pressSwitch = new ObjectStep(20634, new RSTile(3138, 4595, 1),
            "Search");

    ObjectStep jumpBackToPillar4 = new ObjectStep(20546, new RSTile(3142, 4595, 1),
            "Jump-to");
    ObjectStep jumpBackToPillar3 = new ObjectStep(20545, new RSTile(3144, 4595, 1),
            "Jump-to");
    ObjectStep jumpToPillar5 = new ObjectStep(20547, new RSTile(3144, 4597, 1),
            "Jump-to");
    ObjectStep jumpToPillar6 = new ObjectStep(20548, new RSTile(3144, 4599, 1),
            "Jump-to");

    ObjectStep jumpToNorthLedge = new ObjectStep(20563, new RSTile(3144, 4601, 1),
            "Jump-to");
    //     jumpToNorthLedge.addSubSteps(jumpBackToPillar3,jumpBackToPillar4,jumpToPillar5,jumpToPillar6);

    ObjectStep searchWallRoom6 = new ObjectStep(20590, new RSTile(3148, 4604, 1),
            "Search");
    ObjectStep searchWall2Room6 = new ObjectStep(20590, new RSTile(3153, 4604, 1),
            "Search");

    ObjectStep goThroughRoom6 = new ObjectStep(20535, new RSTile(3176, 4598, 1),
            "Enter", inRoom7.check());
    // goThroughRoom6.addSubSteps(searchWallRoom6,searchWall2Room6);

    //TODO get these pillar tiles and code it with protect from magic
    ObjectStep goThroughRoom7 = new ObjectStep(17098, new RSTile(3193, 4598, 1),
            "Activate Protect from Magic and jump across the pillars. Go down the stairs.");
    // protectFromMagic);

    ObjectStep jumpToPillar1Room7 = new ObjectStep(20548, new RSTile(3182, 4600, 1),
            "Jump-to");
    ObjectStep jumpToPillar2Room7 = new ObjectStep(20548, new RSTile(3184, 4600, 1),
            "Jump-to");
    ObjectStep jumpToPillar3Room7 = new ObjectStep(20548, new RSTile(3184, 4598, 1),
            "Jump-to");
    ObjectStep jumpToPillar4Room7 = new ObjectStep(20548, new RSTile(3186, 4598, 1),
            "Jump-to");

    ObjectStep jumpToPillar5Room7 = new ObjectStep(20548, new RSTile(3186, 4596, 1),
            "Jump-to");
    ObjectStep jumpToPillar6Room7 = new ObjectStep(20548, new RSTile(3188, 4596, 1),
            "Jump-to");

    AreaRequirement onRoom7Pillar1 = new AreaRequirement(new RSTile(3182, 4600, 1));
    AreaRequirement onRoom7Pillar2 = new AreaRequirement(new RSTile(3184, 4600, 1));
    AreaRequirement onRoom7Pillar3 = new AreaRequirement(new RSTile(3184, 4598, 1));
    AreaRequirement onRoom7Pillar4 = new AreaRequirement(new RSTile(3186, 4598, 1));
    AreaRequirement onRoom7Pillar5 = new AreaRequirement(new RSTile(3186, 4596, 1));
    AreaRequirement onRoom7Pillar6 = new AreaRequirement(new RSTile(3188, 4596, 1));

    public void handleRoom7() {
        if (inRoom7.check()) {
            Prayer.enable(Prayer.PRAYERS.PROTECT_FROM_MAGIC);
            if (onRoom7Pillar6.check()) {
                // jump to ledge
            } else if (onRoom7Pillar5.check()) {
                jumpToPillar6Room7.execute();
            } else if (onRoom7Pillar4.check()) {
                jumpToPillar5Room7.execute();
            } else if (onRoom7Pillar3.check()) {
                jumpToPillar4Room7.execute();
            } else if (onRoom7Pillar2.check()) {
                jumpToPillar3Room7.execute();
            } else if (onRoom7Pillar1.check()) {
                jumpToPillar2Room7.execute();
            } else {
                jumpToPillar1Room7.execute();
            }
        }
    }


    ObjectStep enterBossRoom = new ObjectStep(20539, new RSTile(3185, 4602, 0), "Enter the north passageway, and be prepared to fight.");


    public void enterMine() {
        cQuesterV2.status = "Walking to mine entrance";
        Log.log("[Debug]: Walking to mine entrance");
        PathingUtil.walkToTile(new RSTile(3446, 3236, 0));
        if (Utils.clickObj(4918, "Climb-over")) {
            Timer.waitCondition(() -> Player.getPosition().equals(new RSTile(3444, 3236, 0)), 4500, 5500);
        }
        if (Utils.clickObj("Cart tunnel", "Crawl-down")) {
            Timer.waitCondition(() -> inHauntedMine.check(), 6500, 7500);
        }
        //TODO enter mine shaft
    }


    public void doSteps() {
        if (inRoom8.check()) {
            Log.log("[Debug]: Entering boss room ");
            enterBossRoom.execute();
        } else if (inRoom7.check()) {
            Log.log("[Debug]: go through room 7 ");
            goThroughRoom7.execute();
        } else if (inRoom6PastTrap2.check()) {
            Log.log("[Debug]: go through room 6 ");
            goThroughRoom6.execute();
        } else if (inRoom6PastTrap1.check()) {
            Log.log("[Debug]: searchWall2Room6.execute()");
            searchWall2Room6.execute();
        } else if (inRoom6P2.check()) {
            Log.log("[Debug]: searchWallRoom6.execute()");
            searchWallRoom6.execute();
        } else if (onPillar6.check()) {
            Log.log("[Debug]: jumpToNorthLedge");
            jumpToNorthLedge.execute();
        } else if (onPillar5.check()) {
            Log.log("[Debug]: jumpToPillar6");
            jumpToPillar6.execute();
        } else if (onPillar3.check() && switchPressed.check()) {
            Log.log("[Debug]: jumpToPillar5");
            jumpToPillar5.execute();
        } else if (onPillar4.check() && switchPressed.check()) {
            Log.log("[Debug]: jumpBackToPillar3");
            jumpBackToPillar3.execute();
        } else if (atSwitch1.check() && switchPressed.check()) {
            Log.log("[Debug]: jumpBackToPillar4");
            jumpBackToPillar4.execute();
        } else if (atSwitch1.check()) {
            Log.log("[Debug]: pressSwitch");
            pressSwitch.execute();
        } else if (onPillar4.check()) {
            Log.log("[Debug]: jumpToSwitch");
            jumpToSwitch.setTileRadius(5);
            jumpToSwitch.execute();
        } else if (onPillar3.check()) {
            Log.log("[Debug]: jumpToPillar4");
            jumpToPillar4.setTileRadius(5);
            jumpToPillar4.execute();
        } else if (onPillar2.check()) {
            Log.log("[Debug]: jumpToPillar3");
            jumpToPillar3.setTileRadius(5);
            jumpToPillar3.execute();
        } else if (onPillar1.check()) {
            Log.log("[Debug]: jumpToPillar2");
            jumpToPillar2.setTileRadius(5);
            jumpToPillar2.execute();
        } else if (inRoom6P1.check()) {
            Log.log("[Debug]: jumpToPillar1");
            if (AccurateMouse.walkScreenTile(new RSTile(3150, 4595, 0)))
                PathingUtil.movementIdle();
            jumpToPillar1.setTileRadius(5);
            jumpToPillar1.execute();
        } else if (inRoom5.check()) {
            Log.log("[Debug]: goThroughRoom5");
            goThroughRoom5.execute();
        } else if (inRoom4.check()) {
            Log.log("[Debug]: goThroughRoom4");
            goThroughRoom4.execute();
        } else if (inRoom3.check()) {
            Log.log("[Debug]: goThroughRoom3");
            goThroughRoom3.execute();
        } else if (inRoom2.check()) {
            Log.log("[Debug]: goThroughRoom2");
            goThroughRoom2.execute();
        } else if (inRoom1PastTrap2.check()) {
            Log.log("[Debug]: goThroughRoom1");
            goThroughRoom1.execute();
        } else if (inRoom1PastTrap1.check()) {
            Log.log("[Debug]: searchWall2Room1");
            searchWall2Room1.execute();
        } else if (inRoom1.check()) {
            Log.log("[Debug]: searchWallRoom1");
            searchWallRoom1.execute();
        } else if (inHauntedMine.check()) {
            cQuesterV2.status = "[Debug]: Entering lair";
            enterLair.execute();
        } else {
            enterMine();
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
        if (!checkRequirements()) {
            cQuesterV2.taskList.remove(this);
            return;
        }
        doSteps();

    }

    @Override
    public String questName() {
        return "Lair of Tarn Razorlor";
    }

    @Override
    public boolean checkRequirements() {
        return true;
    }

    @Override
    public List<Requirement> getGeneralRequirements() {
        return null;
    }

    @Override
    public List<ItemRequirement> getBuyList() {
        return null;
    }
}
