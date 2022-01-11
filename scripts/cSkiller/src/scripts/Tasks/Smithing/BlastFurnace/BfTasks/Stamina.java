package scripts.Tasks.Smithing.BlastFurnace.BfTasks;


import org.tribot.api.General;
import org.tribot.api2007.Game;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSItem;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.Tasks.Smithing.BlastFurnace.BfData.BfConst;
import scripts.Timer;
import scripts.Utils;
import scripts.rsitem_services.GrandExchange;


public class Stamina implements Task {

    @Override
    public String toString() {
        return "Paying foreman";
    }

    @Override
    public Priority priority() {
        return Priority.HIGH;
    }

    @Override
    public boolean validate() {
        return Vars.get().currentTask != null
                && Vars.get().currentTask.equals(SkillTasks.SMITHING)
                && (Inventory.getCount(BfConst.STAMINA_POTION) > 0
                && Game.getRunEnergy() < General.random(80,90)
                && (Utils.getVarBitValue(25) == 0 || Game.getRunEnergy() <= General.random(25, 45)));
    }

    @Override
    public void execute() {
        RSItem[] stam = Inventory.find(BfConst.STAMINA_POTION);
        if (stam.length > 0) {
            if (stam[0].click())
                Timer.waitCondition(() -> Player.getAnimation() == 829, 3000);
            General.println("[Stamina]: Stamina dose costs ~" + loss);
        //    ExampleScriptMain.stamCost = ExampleScriptMain.stamCost + loss;
        }
    }

    @Override
    public String taskName() {
        return "Smithing";
    }

    public int stamina4Cost = GrandExchange.getPrice(BfConst.STAMINA_POTION[0]);
    int loss = stamina4Cost/4;
    public static Timer foremanTimer = new Timer(540000);

}

