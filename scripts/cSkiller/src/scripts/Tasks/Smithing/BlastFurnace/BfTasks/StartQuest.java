package scripts.Tasks.Smithing.BlastFurnace.BfTasks;


import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.Game;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.NpcChat;
import scripts.PathingUtil;
import scripts.Tasks.Smithing.BlastFurnace.BfData.BfConst;
import scripts.Utils;

public class StartQuest implements Task {

    public static int QUEST_VARBIT = 571; // 0 = unstarted

    public void cutScene() {
        while (Game.getSetting(1021) == 16576) {
            General.sleep(General.random(1500, 5500));
        }
    }

    @Override
    public String toString() {
        return "Starting Quest";
    }

    @Override
    public Priority priority() {
        return Priority.HIGHEST;
    }

    @Override
    public boolean validate() {
        return  Vars.get().currentTask != null
                && Vars.get().currentTask.equals(SkillTasks.SMITHING)
                && Utils.getVarBitValue(QUEST_VARBIT) == 0;
    }

    @Override
    public void execute() {
        PathingUtil.walkToArea(BfConst.QUEST_START_AREA, false);

        if (NpcChat.talkToNPC("Dwarven Boatman")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("That's a deal!");
            NPCInteraction.handleConversation("Yes, I'm ready and don't mind it taking a few minutes.");
            Utils.longSleep();
        }
        cutScene();
    }

    @Override
    public String taskName() {
        return null;
    }

}

