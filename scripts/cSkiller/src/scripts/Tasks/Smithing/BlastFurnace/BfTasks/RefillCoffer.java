package scripts.Tasks.Smithing.BlastFurnace.BfTasks;


import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api.input.Keyboard;
import org.tribot.api2007.Interfaces;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.Tasks.Smithing.BlastFurnace.BfData.BfVars;
import scripts.Timer;
import scripts.Utils;
import scripts.Varbits;


public class RefillCoffer implements Task {
    int refillAt =  General.random(5000,25000);
    int PARENT_INTERFACE = 162;
    int CHAT_BOX_CHILD = 41;


    @Override
    public String toString() {
        return "Refilling Coffer";
    }

    @Override
    public Priority priority() {
        return Priority.HIGH;
    }

    @Override
    public boolean validate() {
        return Vars.get().currentTask != null &&
                Vars.get().currentTask.equals(SkillTasks.SMITHING) &&
                Utils.getVarBitValue(Varbits.BLAST_FURNACE_COFFER.getId()) < refillAt;
    }

    @Override
    public void execute() {
        General.println("Coffer varbit:" + Utils.getVarBitValue(Varbits.BLAST_FURNACE_COFFER.getId()));
        General.println("Coffer refill at:" +refillAt);
        if (Utils.clickObject("Coffer", "Use", false)){
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Deposit coins.");
            Timer.waitCondition(()-> Interfaces.isInterfaceSubstantiated(PARENT_INTERFACE,CHAT_BOX_CHILD), 2000,4000);

            if (Interfaces.isInterfaceSubstantiated(PARENT_INTERFACE,CHAT_BOX_CHILD)){
                Keyboard.typeString(String.valueOf(BfVars.get().cofferFillValue));
                Keyboard.pressEnter();
                Timer.waitCondition(()-> !Interfaces.isInterfaceSubstantiated(PARENT_INTERFACE,CHAT_BOX_CHILD), 2000,4000);
                General.sleep(General.random(3000,6000));
            }

        }
        refillAt =  General.random(5000,25000);
    }

    @Override
    public String taskName() {
        return "Smithing";
    }

}

