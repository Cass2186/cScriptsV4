package scripts.Tasks.Smithing.BlastFurnace.BfTasks;

import org.tribot.api.General;
import org.tribot.api2007.Player;
import scripts.API.Priority;
import scripts.API.Task;

import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.PathingUtil;
import scripts.Tasks.Smithing.BlastFurnace.BfData.BfConst;
import scripts.Utils;

public class MoveToBF implements Task {

    @Override
    public String toString() {
        return "Moving to Blast Furnace";
    }

    @Override
    public Priority priority() {
        return Priority.HIGHEST;
    }

    @Override
    public boolean validate() {
        if (Vars.get().currentTask != null && Vars.get().currentTask.equals(SkillTasks.SMITHING)) {
            return !BfConst.WHOLE_BF_AREA.contains(Player.getPosition())
                    && Utils.getVarBitValue(StartQuest.QUEST_VARBIT) > 0;
        }
        return  false;
    }


    @Override
    public void execute() {
        General.println("[Debug]: " + this.toString());
        PathingUtil.walkToTile(BfConst.BF_WALK_TO_TILE, 2,false);
    }

    @Override
    public String taskName() {
        return "Smithing";
    }

}