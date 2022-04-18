package scripts.Tasks.Fishing;

import dax.walker_engine.interaction_handling.NPCInteraction;
import obf.N;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.ChatScreen;
import org.tribot.script.sdk.Inventory;
import org.tribot.script.sdk.Log;
import scripts.*;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.QuestSteps.NPCStep;
import scripts.QuestSteps.ObjectStep;

public class GetHeavyRod implements Task {

    NPCStep ottoStep = new NPCStep(2914, new RSTile(2503, 3488,0),
            new String[]{"You think so?", "Are there any ways to sue a fishing rod which I might learn?"});

    ObjectStep searchBed = new ObjectStep( ObjectID.BARBARIAN_BED, new RSTile(2503, 3488,0),
            "Search", ChatScreen.isOpen());


    private void talkToOtto(){
        Log.info("Talking to Otto");
        ottoStep.execute();
    }

    private void getRodFromBed(){
        Log.info("Getting Rod from Bed");
        searchBed.execute();
    }

    @Override
    public String toString() {
        return "Getting Heavy Rod";
    }


    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return Vars.get().currentTask != null && Vars.get().currentTask.equals(SkillTasks.FISHING) &&
                Vars.get().getBarbarianRod && !Inventory.contains(ItemID.BARBARIAN_ROD);
    }

    @Override
    public void execute() {
        talkToOtto();
        getRodFromBed();
    }


    @Override
    public String taskName() {
        return "Fishing";
    }
}
