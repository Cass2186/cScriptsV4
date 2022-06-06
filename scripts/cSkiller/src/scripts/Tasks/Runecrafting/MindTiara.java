package scripts.Tasks.Runecrafting;

import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.EntitySelector.Entities;
import scripts.EntitySelector.finders.prefabs.ObjectEntity;
import scripts.ItemID;
import scripts.Tasks.Runecrafting.RunecraftData.RcVars;
import scripts.Timer;
import scripts.Utils;

public class MindTiara implements Task {

    RSArea CASTLE_WARS_BANK = new RSArea(new RSTile(2447, 3079, 0), new RSTile(2435, 3098, 0));
    RSArea MIND_ALTAR_AREA = new RSArea(
            new RSTile[]{
                    new RSTile(2771, 4851, 0),
                    new RSTile(2772, 4830, 0),
                    new RSTile(2790, 4816, 0),
                    new RSTile(2805, 4825, 0),
                    new RSTile(2803, 4846, 0),
                    new RSTile(2789, 4855, 0)
            }
    );

    public void makeTiara() {
        if (MIND_ALTAR_AREA.contains(Player.getPosition()) || CraftRunes.atAltar()) {
            RSItem[] tiaras = Inventory.find("Tiara");
            RSItem[] talisman = Inventory.find(Filters.Items.nameContains("Talisman"));
            for (RSItem i : tiaras) {
                int count = Inventory.getCount(ItemID.TIARA);
                if (talisman.length >0 &&
                        Utils.useItemOnObject(talisman[0].getID(), "Altar")) {
                    Timer.waitCondition(() -> Inventory.getCount(ItemID.TIARA) < count, 3500, 5000);
                }
            }
        }
    }

    public void goToAltar() {
        RSObject ruins = Entities.find(ObjectEntity::new)
                .actionsContains("Enter")
                .getFirstResult();
        if ((!MIND_ALTAR_AREA.contains(Player.getPosition()) && !CraftRunes.atAltar()) &&
                ruins != null && Utils.clickObject(ruins, "Enter")) {
            Timer.waitCondition(() -> MIND_ALTAR_AREA.contains(Player.getPosition()), 6000, 9000);
        }
    }

    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        RSItem[] tiaras = Inventory.find(ItemID.TIARA);
        RSItem[] talisman = Inventory.find(Filters.Items.nameContains("talisman"));
        return Vars.get().currentTask != null && Vars.get().currentTask.equals(SkillTasks.RUNECRAFTING) &&
                tiaras.length > 0 && talisman.length > 0  &&
                !RcVars.get().usingOuraniaAlter;
    }

    @Override
    public void execute() {
        goToAltar();
        makeTiara();
    }

    @Override
    public String taskName() {
        return "Mind Tiara crafting";
    }
}
