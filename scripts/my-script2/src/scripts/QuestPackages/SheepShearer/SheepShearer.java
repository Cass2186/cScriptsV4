package scripts.QuestPackages.SheepShearer;

import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.GameState;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Quest;
import scripts.*;
import scripts.QuestSteps.NPCStep;
import scripts.QuestSteps.QuestStep;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.ItemRequirement;
import scripts.Requirements.Requirement;
import scripts.Tasks.Priority;

import java.util.*;

public class SheepShearer implements QuestTask {
    private static SheepShearer quest;

    public static SheepShearer get() {
        return quest == null ? quest = new SheepShearer() : quest;
    }

    //Items Required
    ItemRequirement twentyBallsOfWool, shears;

    QuestStep startStep;

    public Map<Integer, QuestStep> loadSteps() {
        Map<Integer, QuestStep> steps = new HashMap<>();

        RSTile farmerFredPoint = new RSTile(3190, 3273, 0);

        twentyBallsOfWool = new ItemRequirement("Balls of wool", ItemID.BALL_OF_WOOL, 20);
        shears = new ItemRequirement("Shears if you plan on collecting wool yourself", ItemID.SHEARS);

        startStep = new NPCStep(NpcID.FRED_THE_FARMER, farmerFredPoint,
                new ItemRequirement("Ball of wool", ItemID.BALL_OF_WOOL, 20));

        steps.put(0, startStep);

        steps.put(1, steps.get(0));
        steps.put(2, new NPCStep(NpcID.FRED_THE_FARMER, farmerFredPoint,
                new ItemRequirement("Ball of wool", ItemID.BALL_OF_WOOL, 19)));
        steps.put(3, new NPCStep(NpcID.FRED_THE_FARMER, farmerFredPoint,
                new ItemRequirement("Ball of wool", ItemID.BALL_OF_WOOL, 18)));
        steps.put(4, new NPCStep(NpcID.FRED_THE_FARMER, farmerFredPoint,
                new ItemRequirement("Ball of wool", ItemID.BALL_OF_WOOL, 17)));
        steps.put(5, new NPCStep(NpcID.FRED_THE_FARMER, farmerFredPoint,
                new ItemRequirement("Ball of wool", ItemID.BALL_OF_WOOL, 16)));
        steps.put(6, new NPCStep(NpcID.FRED_THE_FARMER, farmerFredPoint,
                new ItemRequirement("Ball of wool", ItemID.BALL_OF_WOOL, 15)));
        steps.put(7, new NPCStep(NpcID.FRED_THE_FARMER, farmerFredPoint,
                new ItemRequirement("Ball of wool", ItemID.BALL_OF_WOOL, 14)));
        steps.put(8, new NPCStep(NpcID.FRED_THE_FARMER, farmerFredPoint,
                new ItemRequirement("Ball of wool", ItemID.BALL_OF_WOOL, 13)));
        steps.put(9, new NPCStep(NpcID.FRED_THE_FARMER, farmerFredPoint,
                new ItemRequirement("Ball of wool", ItemID.BALL_OF_WOOL, 12)));
        steps.put(10, new NPCStep(NpcID.FRED_THE_FARMER, farmerFredPoint,
                new ItemRequirement("Ball of wool", ItemID.BALL_OF_WOOL, 11)));
        steps.put(11, new NPCStep(NpcID.FRED_THE_FARMER, farmerFredPoint,
                new ItemRequirement("Ball of wool", ItemID.BALL_OF_WOOL, 10)));
        steps.put(12, new NPCStep(NpcID.FRED_THE_FARMER, farmerFredPoint,
                new ItemRequirement("Ball of wool", ItemID.BALL_OF_WOOL, 9)));
        steps.put(13, new NPCStep(NpcID.FRED_THE_FARMER, farmerFredPoint,
                new ItemRequirement("Ball of wool", ItemID.BALL_OF_WOOL, 8)));
        steps.put(14, new NPCStep(NpcID.FRED_THE_FARMER, farmerFredPoint,
                new ItemRequirement("Ball of wool", ItemID.BALL_OF_WOOL, 7)));
        steps.put(15, new NPCStep(NpcID.FRED_THE_FARMER, farmerFredPoint,
                new ItemRequirement("Ball of wool", ItemID.BALL_OF_WOOL, 6)));
        steps.put(16, new NPCStep(NpcID.FRED_THE_FARMER, farmerFredPoint,
                new ItemRequirement("Ball of wool", ItemID.BALL_OF_WOOL, 5)));
        steps.put(17, new NPCStep(NpcID.FRED_THE_FARMER, farmerFredPoint,
                new ItemRequirement("Ball of wool", ItemID.BALL_OF_WOOL, 4)));
        steps.put(18, new NPCStep(NpcID.FRED_THE_FARMER, farmerFredPoint,
                new ItemRequirement("Ball of wool", ItemID.BALL_OF_WOOL, 3)));
        steps.put(19, new NPCStep(NpcID.FRED_THE_FARMER, farmerFredPoint,
                new ItemRequirement("Ball of wool", ItemID.BALL_OF_WOOL, 2)));
        steps.put(20, new NPCStep(NpcID.FRED_THE_FARMER, farmerFredPoint,
                new ItemRequirement("Ball of wool", ItemID.BALL_OF_WOOL, 1)));


        steps.get(0).addDialogStep("I'm looking for a quest.");
        steps.get(0).addDialogStep("Yes, okay. I can do that.");
        return steps;
    }


    public List<ItemRequirement> getItemRequirements() {
        ArrayList<ItemRequirement> reqs = new ArrayList<>();
        reqs.add(twentyBallsOfWool);
        reqs.add(shears);
        return reqs;
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
        if (Quest.SHEEP_SHEARER.getState() == Quest.State.COMPLETE) {
            cQuesterV2.taskList.remove(this);
            return;
        }
        int gameSetting = GameState.getSetting(QuestVarPlayer.QUEST_SHEEP_SHEARER.getId());
        Map<Integer, QuestStep> steps = loadSteps();
        Optional<QuestStep> step = Optional.ofNullable(steps.get(gameSetting));
        step.ifPresent(QuestStep::execute);
    }

    @Override
    public String questName() {
        return "Sheep Shearer";
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

    @Override
    public boolean isComplete() {
        return Quest.SHEEP_SHEARER.getState().equals(Quest.State.COMPLETE);
    }
}
