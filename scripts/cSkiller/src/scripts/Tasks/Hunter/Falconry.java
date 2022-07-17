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
import org.tribot.script.sdk.Skill;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.Npc;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.AntiBan;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.Tasks.Hunter.HunterData.HunterConst;
import scripts.Timer;
import scripts.Utils;

import java.util.Optional;

public class Falconry implements Task {


    int SPOTTED_FUR = 10125;
    int GLOVE_WITH_FALCON = 10024;
    int GLOVE_WITHOUT_FALCON = 10023;
    //needs 500gp to do this
    //rough area


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

            if (Inventory.getAll().length > General.random(14, 22))
                Inventory.drop(HunterConst.BONES, SPOTTED_FUR);

            Optional<Npc> falcon = Query.npcs().nameContains("Gyr Falcon").findBestInteractable();
            if (Equipment.isEquipped(GLOVE_WITHOUT_FALCON) &&
                    falcon.map(f->f.interact("Retrieve")).orElse(false)){
                Waiting.waitUntil(5000, 125,() -> Equipment.isEquipped(GLOVE_WITH_FALCON) ||
                        !Query.npcs().maxDistance(15 ).nameContains("Gyr Falcon").isAny());
                Utils.idlePredictableAction();
            }
           // RSNPC[] kibbet = NPCs.findNearest(5531);
            org.tribot.script.sdk.input.Mouse.setClickMethod(
                    org.tribot.script.sdk.input.Mouse.ClickMethod.ACCURATE_MOUSE);
            Optional<Npc> kibbet = Query.npcs().idEquals(5531).findBestInteractable();
            if (Equipment.isEquipped(GLOVE_WITH_FALCON) &&
                    kibbet.map(k->k.interact("Catch")).orElse(false)){
                Waiting.waitUntil(2000, ()-> Equipment.isEquipped(GLOVE_WITHOUT_FALCON));
                abc2Chance = General.random(0, 100);
                if (abc2Chance < 12) {
                    Timer.abc2SkillingWaitCondition(() -> Equipment.isEquipped(GLOVE_WITH_FALCON) ||
                            NPCs.findNearest("Gyr Falcon").length > 0, 2500, 4000);
                } else {
                    AntiBan.timedActions();
                    Waiting.waitUntil(4000, 75,() -> Equipment.isEquipped(GLOVE_WITH_FALCON) ||
                            NPCs.findNearest("Gyr Falcon").length > 0);
                    Utils.idlePredictableAction();
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
            } else return Skill.HUNTER.getActualLevel() < 60; //todo switch to 47
        }
        return false;
    }

    @Override
    public void execute() {
        Mouse.setSpeed(160);
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
