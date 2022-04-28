package scripts.QuestPackages.LunarDiplomacy;

import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api2007.Game;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.ChatScreen;
import org.tribot.script.sdk.Quest;
import org.tribot.script.sdk.query.Query;
import scripts.NpcID;
import scripts.QuestSteps.NPCStep;
import scripts.QuestSteps.ObjectStep;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.ItemRequirement;
import scripts.Requirements.Requirement;
import scripts.Requirements.Util.Operation;
import scripts.Requirements.VarbitRequirement;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;
import scripts.Utils;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ChanceChallenge implements QuestTask {

    int currentGoal;
    HashMap<Integer, List<Integer>> solutions = new HashMap();

    NPCStep talk = new NPCStep(NpcID.ETHEREAL_FLUKE, new RSTile(1737, 5068, 2),
        new String[]{"Suppose I may as well have a go."});

    ObjectStep spinD1 = new ObjectStep(17019,  "Roll"
   );
    ObjectStep spinD2 = new ObjectStep(17024,  "Roll"
 );
    ObjectStep spinD3 = new ObjectStep(17023,  "Roll"
);
    ObjectStep spinD4 = new ObjectStep(17020, "Roll"
    );
    ObjectStep spinD5 = new ObjectStep(17022, "Roll");
    ObjectStep spinD6 = new ObjectStep(17021,  "Roll");


    public void setupSolutions() {
        solutions.put(12, Arrays.asList(1, 1, 2, 2, 3, 3));
        solutions.put(13, Arrays.asList(1, 1, 2, 2, 3, 4));
        solutions.put(14, Arrays.asList(1, 1, 2, 2, 4, 4));
        solutions.put(15, Arrays.asList(1, 1, 2, 5, 3, 3));
        solutions.put(16, Arrays.asList(1, 1, 2, 5, 3, 4));
        solutions.put(17, Arrays.asList(1, 1, 2, 5, 4, 4));
        solutions.put(18, Arrays.asList(1, 1, 5, 5, 3, 3));
        solutions.put(19, Arrays.asList(1, 1, 5, 5, 3, 4));
        solutions.put(20, Arrays.asList(1, 1, 5, 5, 4, 4));
        solutions.put(21, Arrays.asList(1, 6, 2, 5, 3, 4));
        solutions.put(22, Arrays.asList(1, 6, 2, 5, 4, 4));
        solutions.put(23, Arrays.asList(1, 6, 5, 5, 3, 3));
        solutions.put(24, Arrays.asList(1, 6, 5, 5, 3, 4));
        solutions.put(25, Arrays.asList(1, 6, 5, 5, 4, 4));
        solutions.put(26, Arrays.asList(6, 6, 2, 5, 3, 4));
        solutions.put(27, Arrays.asList(6, 6, 2, 5, 4, 4));
        solutions.put(28, Arrays.asList(6, 6, 5, 5, 3, 3));
        solutions.put(29, Arrays.asList(6, 6, 5, 5, 3, 4));
        solutions.put(30, Arrays.asList(6, 6, 5, 5, 4, 4));
    }

    public void updateSteps() {
        currentGoal = Utils.getVarBitValue(2411);

        if (currentGoal == 0) {
            talk.execute();
            return;
        }

        int die16A = Utils.getVarBitValue(2399);
        if (die16A == 0) {
            die16A = 1;
        }
        else {
            die16A = 6;
        }
        int die16B = Utils.getVarBitValue(2404);
        if (die16B == 0) {
            die16B = 6;
        }
        else {
            die16B = 1;
        }

        int die25A = Utils.getVarBitValue(2403);
        if (die25A == 0) {
            die25A = 5;
        }
        else {
            die25A = 2;
        }
        int die25B = Utils.getVarBitValue(2400);
        if (die25B == 0) {
            die25B = 2;
        }
        else {
            die25B = 5;
        }

        int die34A = Utils.getVarBitValue(2402);
        if (die34A == 0) {
            die34A = 4;
        }
        else {
            die34A = 3;
        }
        int die34B = Utils.getVarBitValue(2401);
        if (die34B == 0) {
            die34B = 3;
        }
        else {
            die34B = 4;
        }

        checkSolutions(die16A, die16B, die25A, die25B, die34A, die34B);
    }

    public void checkSolutions(int d1, int d2, int d3, int d4, int d5, int d6) {
        List<Integer> solution = solutions.get(currentGoal);

        if (solution == null) {
            talk.execute();
        }
        else if (d1 != solution.get(0)) {
            spinD1.execute();
        }
        else if (d2 != solution.get(1)) {
         spinD2.execute();
        }
        else if (d3 != solution.get(2)) {
            spinD3.execute();
        }
        else if (d4 != solution.get(3)) {
            spinD4.execute();
        }
        else if (d5 != solution.get(4)) {
           spinD5.execute();
        }
        else if (d6 != solution.get(5)) {
            spinD6.execute();
        }
    }

    VarbitRequirement finishedChance = new VarbitRequirement(2410, 5,Operation.GREATER_EQUAL);
    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        return Game.isInInstance() && !finishedChance.check() &&
                Query.npcs().idEquals(NpcID.ETHEREAL_FLUKE).isReachable().isAny();
    }

    @Override
    public void execute() {
        setupSolutions();
        updateSteps();
        if (ChatScreen.isOpen())
            NPCInteraction.handleConversation();
    }

    @Override
    public String questName() {
        return "Chance game";
    }

    @Override
    public boolean checkRequirements() {
        return false;
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
        return Quest.LUNAR_DIPLOMACY.getState().equals(Quest.State.COMPLETE);
    }
}
