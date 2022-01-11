package scripts.Tasks.Crafting;

import org.tribot.api.General;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSItemDefinition;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.*;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;

public class MakeGlass implements Task {

    public void makeGlassItem(String nameInMenu, int itemId) {
        if (Inventory.find(ItemId.MOLTEN_GLASS).length > 0 && Inventory.find(ItemId.GLASSBLOWING_PIPE).length > 0) {
            RSItemDefinition def = RSItemDefinition.get(itemId);
            if (def != null) {
                General.println("[Debug]: Making glass item: " + def.getName());

                if (Banking.isBankScreenOpen())
                    BankManager.close(true);

                if (Utils.useItemOnItem(ItemId.GLASSBLOWING_PIPE, ItemId.MOLTEN_GLASS)) {
                    Timer.waitCondition(() -> Interfaces.isInterfaceSubstantiated(ItemId.GLASS_PARENT_ID), 4000, 7000);
                    if (InterfaceUtil.getInterfaceKeyAndPress(ItemId.GLASS_PARENT_ID, nameInMenu)) {
                        Timer.abc2SkillingWaitCondition(() ->
                                Inventory.find(ItemId.MOLTEN_GLASS).length < 1 ,60000, 75000);
                    }
                }
            }
        }
        if (!SkillTasks.CRAFTING.isWithinLevelRange())
            Vars.get().currentTask = null;

    }

    @Override
    public String toString() {
        return "Making glass";
    }

    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        return Vars.get().currentTask != null && Vars.get().currentTask.equals(SkillTasks.CRAFTING)

                && (Inventory.find(ItemId.MOLTEN_GLASS).length > 0 &&
                Inventory.find(ItemId.GLASSBLOWING_PIPE).length > 0);
    }

    @Override
    public void execute() {
        if (Skills.getActualLevel(Skills.SKILLS.CRAFTING) >= SkillTasks.CRAFTING.getEndLevel()) {
            cSkiller.currentSkill = null;
            return;
        }
        if (Skills.getActualLevel(Skills.SKILLS.CRAFTING) < 5)
            makeGlassItem("beer glass", ItemId.BEER_GLASS);

        else if (Skills.getActualLevel(Skills.SKILLS.CRAFTING) < 12)
            makeGlassItem("candle lantern", ItemId.CANDLE_LANTERN);

        else if (Skills.getActualLevel(Skills.SKILLS.CRAFTING) < 33)
            makeGlassItem("oil lamp", ItemId.OIL_LAMP);

        else if (Skills.getActualLevel(Skills.SKILLS.CRAFTING) < 42)
            makeGlassItem("vial", ItemId.VIAL);

        else if (Skills.getActualLevel(Skills.SKILLS.CRAFTING) < 46)
            makeGlassItem("fishbowl", ItemId.FISH_BOWL);

        else if (Skills.getActualLevel(Skills.SKILLS.CRAFTING) < 80) { //49 usually
            makeGlassItem("staff orb", ItemId.UNPOWERED_ORB);

        } else if (Skills.getActualLevel(Skills.SKILLS.CRAFTING) < 87)
            makeGlassItem("lantern lens", ItemId.LANTERN_LENS);
        else
            makeGlassItem("light orb", 10980);

    }

    @Override
    public String taskName() {
        return "Crafting";
    }
}
