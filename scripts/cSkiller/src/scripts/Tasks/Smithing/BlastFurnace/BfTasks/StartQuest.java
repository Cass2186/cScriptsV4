package scripts.Tasks.Smithing.BlastFurnace.BfTasks;


import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.Game;
import org.tribot.script.sdk.Log;
import scripts.*;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.Requirements.InventoryRequirement;
import scripts.Requirements.ItemReq;
import scripts.Tasks.Smithing.BlastFurnace.BfData.BfConst;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StartQuest implements Task {

    public static int QUEST_VARBIT = 571; // 0 = unstarted

    public void cutScene() {
        while (Game.getSetting(1021) == 16576) {
            General.sleep(General.random(1500, 5500));
        }
    }

    InventoryRequirement inv = new InventoryRequirement(new ArrayList<>(Arrays.asList(
            new ItemReq(ItemID.CAMELOT_TELEPORT, 2, 0),
            new ItemReq(ItemID.STAMINA_POTION[0], 1, 0),
            new ItemReq(ItemID.RING_OF_WEALTH[0], 1, 0,true,true)
    )));


    private void getItems() {

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
        return Vars.get().currentTask != null
                && Vars.get().currentTask.equals(SkillTasks.SMITHING)
                && Utils.getVarBitValue(QUEST_VARBIT) == 0;
    }

    @Override
    public void execute() {
        Log.error("Need to start The Giant Dwarf Quest ");
        Log.error("Please stop the script if you don't want this");
        PathingUtil.walkToArea(BfConst.QUEST_START_AREA, false);

        if (NpcChat.talkToNPC("Dwarven Boatman")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("That's a deal!");
            NPCInteraction.handleConversation("Yes.");
            Timer.waitCondition(Utils::inCutScene, 15000, 20000);
        }
        cutScene();
    }

    @Override
    public String taskName() {
        return null;
    }

}

