package scripts.QuestUtils;

import org.tribot.api.General;
import scripts.QuestSteps.QuestStep;
import scripts.Quests;
import scripts.Tasks.Task;
import scripts.Timer;
import scripts.dax.tracker.DaxTracker;

import java.util.HashMap;
import java.util.List;

public class Vars {

    private static Vars vars;

    public static Vars get() {
        return vars == null ? vars = new Vars() : vars;
    }


    public static void reset() {
        vars = new Vars();
    }

    public List<QuestStep> questStepsList;

    // for every key (Quest), there will be a list of steps associated with it
    public HashMap<Quests, List<Task>> questHashMap = new HashMap<>();

    public int startingQuestPoints = 0;
    public int currentQuestPoints = 0;
    public boolean shouldShowGui = true;

    public String status = "Initializing";
    public String questName = "Initializing";


    public DaxTracker daxTracker;

    public int afkDurationAvg = 0;
    public int afkDurationSD = 0;
    public int afkFrequencyAvg = 0;
    public int afkFrequencySD = 0;
    public Timer afkTimer = new Timer(General.randomSD(afkFrequencyAvg, afkFrequencySD));
}
