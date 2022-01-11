package scripts.Tasks.Hunter;

import org.tribot.api.General;
import org.tribot.api.input.Keyboard;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.Waiting;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.AntiBan;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.EntitySelector.Entities;
import scripts.EntitySelector.finders.prefabs.ObjectEntity;
import scripts.Tasks.Hunter.HunterData.HunterConst;
import scripts.Timer;
import scripts.Utils;

import java.awt.event.KeyEvent;
import java.util.List;

public class CollectSuccessful implements Task {


    public void collectBirdSnare() {
        RSObject trap = Entities.find(ObjectEntity::new)
                .idEquals(HunterConst.CAUGHT_BIRD_SNARE)
                .getFirstResult();

        General.println("[Debug]: collecting successful");
        RSItem[] invTraps = Inventory.find(HunterConst.RAW_BIRD_MEAT);
        if (trap != null && Utils.clickObject(trap, "Check",
                false)) {
            Timer.slowWaitCondition(() -> Inventory.find(HunterConst.RAW_BIRD_MEAT).length >
                    invTraps.length, 7000, 9000); //was 10-12s
        }

    }
    public void dropSalamanders(){
        RSItem[] sals = Inventory.find(HunterConst.ALL_SALAMANDERS);
        if (sals.length > 0) {
            Keyboard.sendPress((char) KeyEvent.VK_SHIFT, 16);
            for (RSItem s : sals){
                s.click("Release");
                AntiBan.waitItemInteractionDelay();
            }
            Keyboard.sendRelease((char) KeyEvent.VK_SHIFT, 16);
            Waiting.waitNormal(400,120); //prevents trying to reclick
        }
    }
    public void collectNetTrap(List<RSTile> tileList) {
        for (RSTile t : tileList) {
            RSObject trap = Entities.find(ObjectEntity::new)
                    .inArea(new RSArea(t,1))
                    .idEquals(HunterConst.NET_TRAP_SUCCESSFUL)
                    .getFirstResult();
            if (Inventory.getAll().length > 20){
                dropSalamanders();

            }

            RSItem[] invTraps = Inventory.find(HunterConst.SWAMP_LIZARD);
            if (trap != null && Utils.clickObject(trap, "Check",
                    false)) {
                Timer.slowWaitCondition(() -> Inventory.find(HunterConst.SWAMP_LIZARD).length >
                        invTraps.length, 7000, 9000); //was 10-12s
            }
        }
    }

    public void collectBoxTrap(List<RSTile> tileList, int chinId) {
        for (RSTile t : tileList) {
            RSObject trap = Entities.find(ObjectEntity::new)
                    .tileEquals(t)
                    .idEquals(HunterConst.SHAKING_BOX_TRAP)
                    .getFirstResult();

            RSItem[] invTraps = Inventory.find(HunterConst.BOX_TRAP_ITEM_ID);

            //TODO Check the action here
            if (trap != null && Utils.clickObject(trap, "Check",
                    false)) {
                Timer.slowWaitCondition(() -> Inventory.find(HunterConst.BOX_TRAP_ITEM_ID).length >
                        invTraps.length, 7000, 9000); //was 10-12s
            }
        }
    }


    @Override
    public String toString() {
        return "Collecting successful traps";
    }


    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
      if (Skills.getCurrentLevel(Skills.SKILLS.HUNTER) < 29) {
            return Vars.get().currentTask != null &&
                    Vars.get().currentTask.equals(SkillTasks.HUNTER) &&
                    Objects.findNearest(20, HunterConst.CAUGHT_BIRD_SNARE)
                    .length > 0;

        } else if (Skills.getCurrentLevel(Skills.SKILLS.HUNTER) < 49) { // should be 43
          return Vars.get().currentTask != null &&
                  Vars.get().currentTask.equals(SkillTasks.HUNTER) &&
                  Objects.findNearest(20, HunterConst.NET_TRAP_SUCCESSFUL)
                  .length > 0;
        } else if (Skills.getCurrentLevel(Skills.SKILLS.HUNTER) < 80) {

        } else {

        }
        return false;
    }

    @Override
    public void execute() {
        if (Skills.getCurrentLevel(Skills.SKILLS.HUNTER) < 20) {
            collectBirdSnare();
        } else if (Skills.getCurrentLevel(Skills.SKILLS.HUNTER) < 29) {
            collectBirdSnare();

        } else if (Skills.getCurrentLevel(Skills.SKILLS.HUNTER) < 43) { // should be 43
            collectNetTrap(HunterConst.CANFIS_TRAP_TILES);
        } else if (Skills.getCurrentLevel(Skills.SKILLS.HUNTER) < 80) {

        } else {

        }
    }

    @Override
    public String taskName() {
        return "Hunter: collect successful";
    }
}