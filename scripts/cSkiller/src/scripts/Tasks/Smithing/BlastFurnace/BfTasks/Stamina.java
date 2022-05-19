package scripts.Tasks.Smithing.BlastFurnace.BfTasks;


import org.tribot.api.General;
import org.tribot.api2007.Game;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSItem;
import org.tribot.script.sdk.Equipment;
import org.tribot.script.sdk.Skill;
import org.tribot.script.sdk.Waiting;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.ItemID;
import scripts.Tasks.BirdHouseRuns.Nodes.Wait;
import scripts.Tasks.Smithing.BlastFurnace.BfData.BfConst;
import scripts.Tasks.Smithing.BlastFurnace.BfData.BfVars;
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
                && Game.getRunEnergy() < General.random(80,90) &&
                (Utils.getVarBitValue(25) == 0 || Game.getRunEnergy() <= General.random(25, 45)));
    }

    @Override
    public void execute() {
        RSItem[] stam = Inventory.find(BfConst.STAMINA_POTION);
        if (stam.length > 0 && stam[0].click()) {
                Timer.waitCondition(() -> Player.getAnimation() == 829, 3000);
            General.println("[Stamina]: Stamina dose costs ~" + loss);
          BfVars.get().totalStaminaCost = BfVars.get().totalStaminaCost + loss;
            Waiting.waitNormal(400,75);
            int xp = Skill.SMITHING.getXp();
            if (Equipment.contains(ItemID.GOLDSMITH_GAUNTLETS)){
                Waiting.waitUntil(2500, 5, ()-> Skill.SMITHING.getXp() > xp);
            }
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

