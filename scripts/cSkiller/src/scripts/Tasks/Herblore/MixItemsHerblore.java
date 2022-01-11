package scripts.Tasks.Herblore;

import com.sun.tools.javac.Main;
import org.tribot.api.General;
import org.tribot.api.input.Keyboard;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Skills;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.BankManager;
import scripts.Data.Enums.HerbloreItems;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.ItemId;
import scripts.Timer;
import scripts.Utils;

import java.util.Optional;

public class MixItemsHerblore implements Task {

    public void mixItems(int item1, int item2) {
        if (Banking.isBankScreenOpen())
            BankManager.close(true);

        if (Utils.useItemOnItem(item1, item2))
            Timer.waitCondition(() -> Interfaces.isInterfaceSubstantiated(270, 14), 3000, 6000);

        if (Interfaces.get(270, 14) != null) {
            General.sleep(General.randomSD(25, 200, 125, 40));
            Keyboard.holdKey(scripts.Keyboard.SPACEBAR, 32, (() -> {
                        General.sleep(General.random(100, 250));
                        return Interfaces.get(270, 14) == null;
                    })
            );

            Timer.abc2SkillingWaitCondition(() -> (Interfaces.get(233, 2) != null
                    || Inventory.find(item1).length < 1), 30000, 45000);

        }
        if (!SkillTasks.HERBLORE.isWithinLevelRange())
            Vars.get().currentTask = null;
    }



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
        return Vars.get().currentTask != null  && Vars.get().currentTask.equals(SkillTasks.HERBLORE);
    }

    @Override
    public void execute() {
        Optional<HerbloreItems> itemOptional = HerbloreItems.getCurrentItem();
        itemOptional.ifPresent(itm -> mixItems(itm.getItemId(), itm.getUnfPotionId()));
    }

    @Override
    public String taskName() {
        return "Herblore";
    }
}
