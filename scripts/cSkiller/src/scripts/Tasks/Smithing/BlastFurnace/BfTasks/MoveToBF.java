package scripts.Tasks.Smithing.BlastFurnace.BfTasks;

import org.tribot.api.General;
import org.tribot.api2007.Player;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Minigame;
import scripts.API.Priority;
import scripts.API.Task;

import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.PathingUtil;
import scripts.QuestVarbits;
import scripts.Tasks.Smithing.BlastFurnace.BfData.BfConst;
import scripts.Utils;

public class MoveToBF implements Task {

    @Override
    public String toString() {
        return "Going to Blast Furnace";
    }

    @Override
    public Priority priority() {
        return Priority.HIGHEST;
    }

    @Override
    public boolean validate() {
        if (Vars.get().currentTask != null && Vars.get().currentTask.equals(SkillTasks.SMITHING)) {
            return !BfConst.WHOLE_BF_AREA.contains(Player.getPosition())
                    && Utils.getVarBitValue(QuestVarbits.QUEST_THE_GIANT_DWARF.getId()) > 0;
        }
        return  false;
    }


    @Override
    public void execute() {
        Log.info(this.toString());
        Minigame.BLAST_FURNACE.teleport();
        PathingUtil.walkToTile(BfConst.BF_WALK_TO_TILE, 2,false);
    }

    @Override
    public String taskName() {
        return "Smithing";
    }

}