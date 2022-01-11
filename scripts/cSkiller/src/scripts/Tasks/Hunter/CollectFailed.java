package scripts.Tasks.Hunter;

import org.tribot.api2007.GroundItems;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.Tasks.Hunter.HunterData.HunterConst;
import scripts.Tasks.Hunter.HunterData.TrapTypes;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Timer;
import scripts.Utils;

public class CollectFailed implements Task {

    public boolean collectSmallNet() {
        return Utils.clickGroundItem(HunterConst.SMALL_FISHING_NET);
    }

    public boolean collectRope() {
        return Utils.clickGroundItem(HunterConst.ROPE);
    }

    public void collectBirdSnare(){
        RSObject[] disabledSnare =   Objects.findNearest(20, HunterConst.DISABLED_BIRD_SNARE);
        RSItem[] num = Inventory.find(HunterConst.BIRD_SNARE);
        if (disabledSnare.length > 0 &&
            disabledSnare[0].click("Dismantle")){
            Timer.waitCondition(()-> Inventory.find(HunterConst.BIRD_SNARE).length > num.length, 5000,7000);
        }
    }


    @Override
    public String toString() {
        return "Collecting failed traps";
    }


    @Override
    public Priority priority() {
        return Priority.HIGHEST;
    }

    @Override
    public boolean validate() {
        if (Vars.get().currentTask != null && Vars.get().currentTask.equals(SkillTasks.HUNTER)){
        if (Skills.getCurrentLevel(Skills.SKILLS.HUNTER) < 29) {
            return GroundItems.find(HunterConst.BIRD_SNARE).length > 0 ||
                    Objects.findNearest(20, HunterConst.DISABLED_BIRD_SNARE).length > 0;

        } else if (Skills.getCurrentLevel(Skills.SKILLS.HUNTER) < 43) { // should be 43
            return GroundItems.find(HunterConst.ROPE).length > 0 ||
                    GroundItems.find(HunterConst.SMALL_FISHING_NET).length > 0;
        } else if (Skills.getCurrentLevel(Skills.SKILLS.HUNTER) < 80) {

        } else {

        }
        }
        return false;

    }

    @Override
    public void execute() {
        Utils.clickGroundItem(HunterConst.BIRD_SNARE);
        collectBirdSnare();
        collectRope();
        collectSmallNet();
    }

    @Override
    public String taskName() {
        return "Hunter";
    }
}
