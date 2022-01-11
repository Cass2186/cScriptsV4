package scripts.Tasks.Hunter;

import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.Equipment;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSTile;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.AntiBan;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.Tasks.Hunter.HunterData.HunterConst;
import scripts.Timer;
import scripts.Utils;

public class Falconry implements Task {


    int SPOTTED_FUR = 10125;
    int GLOVE_WITH_FALCON = 10024;
    int GLOVE_WITHOUT_FALCON = 10023;
    //needs 500gp to do this
    //rough area
    RSArea FALCONRY_AREA = new RSArea(
            new RSTile[]{
                    new RSTile(2367, 3613, 0),
                    new RSTile(2380, 3613, 0),
                    new RSTile(2385, 3609, 0),
                    new RSTile(2385, 3605, 0),
                    new RSTile(2390, 3594, 0),
                    new RSTile(2391, 3589, 0),
                    new RSTile(2385, 3588, 0),
                    new RSTile(2384, 3583, 0),
                    new RSTile(2380, 3579, 0),
                    new RSTile(2378, 3575, 0),
                    new RSTile(2365, 3575, 0),
                    new RSTile(2365, 3584, 0),
                    new RSTile(2368, 3586, 0),
                    new RSTile(2368, 3594, 0),
                    new RSTile(2366, 3595, 0),
                    new RSTile(2366, 3600, 0),
                    new RSTile(2368, 3602, 0)
            }
    );

    public boolean getFalcon() {
        //enter falconry area first
        if (!Equipment.isEquipped("Falconer's glove")) {
            if (Utils.clickNPC("Matthias", "Talk-to")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Could I have a go with your bird?",
                        "Ok, that seems reasonable.");
            }
        }

        return Equipment.isEquipped("Falconer's glove");
    }


    int abc2Chance = General.random(0, 100);

    public void catchKibbets() {
        if (getFalcon()) {

            if (Inventory.getAll().length > General.random(14, 24))
                Inventory.drop(HunterConst.BONES, SPOTTED_FUR);


            if (Equipment.isEquipped(GLOVE_WITHOUT_FALCON)
                    && Utils.clickNPC("Gyr Falcon", "Retrieve")) {
                Timer.slowWaitCondition(() -> NPCs.findNearest("Gyr Falcon").length == 0, 3500, 5000);
            }
            RSNPC[] kibbet = NPCs.findNearest(5531);
            if (kibbet.length > 0 && Equipment.isEquipped(GLOVE_WITH_FALCON)
                    && Utils.clickNPC(kibbet[0], "Catch", true)) {
                if (abc2Chance < 42) {
                    Timer.abc2SkillingWaitCondition(() -> NPCs.findNearest("Gyr Falcon").length > 0, 2500, 4000);
                    abc2Chance = General.random(0, 100);
                } else {
                    AntiBan.timedActions();
                    Timer.waitCondition(() -> NPCs.findNearest("Gyr Falcon").length > 0, 2500, 4000);

                    int sleep = General.randomSD(720, 340);
                    General.println("[Debug]: Sleeping for " + sleep + "ms");
                    General.sleep(sleep);
                    abc2Chance = General.random(0, 100);
                }
            }

        }
    }

    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        if (Vars.get().currentTask != null &&
                Vars.get().currentTask.equals(SkillTasks.HUNTER)) {
            if (Skills.getCurrentLevel(Skills.SKILLS.HUNTER) < 43) {
                return false;
            } else return Skills.getCurrentLevel(Skills.SKILLS.HUNTER) < 80;
        }
        return false;
    }

    @Override
    public void execute() {
        Mouse.setSpeed(150);
        catchKibbets();
    }
    @Override
    public String toString() {
        return "Falconry";
    }
    @Override
    public String taskName() {
        return "Hunter";
    }
}
