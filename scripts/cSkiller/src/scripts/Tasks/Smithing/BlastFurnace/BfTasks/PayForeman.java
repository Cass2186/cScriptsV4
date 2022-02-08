package scripts.Tasks.Smithing.BlastFurnace.BfTasks;


import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSItem;
import org.tribot.script.sdk.Log;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.BankManager;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.Tasks.Prayer.PlaceBones;
import scripts.Tasks.Smithing.BlastFurnace.BfData.BfConst;
import scripts.Tasks.Smithing.BlastFurnace.BfData.BfVars;
import scripts.Timer;
import scripts.Utils;


public class PayForeman implements Task {

    @Override
    public String toString() {
        return "Paying foreman";
    }

    @Override
    public Priority priority() {
        return Priority.HIGHEST;
    }

    @Override
    public boolean validate() {
            return Vars.get().currentTask != null &&
                    Vars.get().currentTask.equals(SkillTasks.SMITHING) &&
                    ((Skills.getActualLevel(Skills.SKILLS.SMITHING) < 60 &&
                    !foremanTimer.isRunning()) || BfVars.get().startPayment) &&
                    BfConst.WHOLE_BF_AREA.contains(Player.getPosition());

    }

    @Override
    public void execute() {
        getCoins();
        if (Utils.clickNPC("Blast Furnace Foreman", "Pay")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Yes");
            foremanTimer.reset();
            BfVars.get().startPayment = false;
          //  ExampleScriptMain.stamCost = ExampleScriptMain.stamCost + 2500;
        }
    }

    @Override
    public String taskName() {
        return "Smithing";
    }

    public static Timer foremanTimer = new Timer(General.random(530000, 580000)); //54000 = 9min

    public void getCoins(){
        RSItem[] i = Inventory.find(995);
        if (i.length ==0 || i[0].getStack() < 5000){
            Log.debug("Getting coins for Foreman");
            BankManager.open(true);
            if (Inventory.getAll().length > 26){
                BankManager.depositAll(true);
            }
            BankManager.withdraw(0, true, 995);
            BankManager.close(true);
        }
    }

}

