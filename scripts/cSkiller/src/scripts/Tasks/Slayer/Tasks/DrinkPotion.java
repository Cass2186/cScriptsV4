package scripts.Tasks.Slayer.Tasks;

import org.tribot.api2007.Player;
import org.tribot.api2007.Skills;
import org.tribot.script.sdk.MyPlayer;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.Tasks.Slayer.SlayerUtils.SlayerVars;
import scripts.Utils;

public class DrinkPotion implements Task {


    private boolean shouldDrinkPotion(Skills.SKILLS skill){
        int max = (int) Utils.getSuperLevelBoost(skill);

        //return skill.getCurrentLevel() <
        return false;
    }

    @Override
    public Priority priority() {
        return null;
    }

    @Override
    public boolean validate() {
        return Vars.get().currentTask != null &&
                Vars.get().currentTask.equals(SkillTasks.SLAYER) &&
                SlayerVars.get().fightArea != null &&
                SlayerVars.get().fightArea.contains(MyPlayer.getTile());
    }

    @Override
    public void execute() {

    }

    @Override
    public String toString() {
        return "Drinking Potion";
    }

    @Override
    public String taskName() {
        return "Slayer";
    }
}
