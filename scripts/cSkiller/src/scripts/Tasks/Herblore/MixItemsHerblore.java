package scripts.Tasks.Herblore;

import org.tribot.api.General;
import org.tribot.api.input.Keyboard;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Skills;
import org.tribot.script.sdk.*;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.BankManager;
import scripts.Data.Enums.HerbloreItems;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.Timer;
import scripts.Utils;

import java.util.Optional;

public class MixItemsHerblore implements Task {


    @Override
    public String toString() {
        return "Mixing Items";
    }


    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return Vars.get().currentTask != null  && Vars.get().currentTask.equals(SkillTasks.HERBLORE) &&
                Skills.SKILLS.HERBLORE.getActualLevel()< 38;
    }

    @Override
    public void execute() {
        Optional<HerbloreItems> itemOptional = HerbloreItems.getCurrentItem();
        itemOptional.ifPresent(HerbloreItems::makePotion);
    }

    @Override
    public String taskName() {
        return "Herblore";
    }
}
