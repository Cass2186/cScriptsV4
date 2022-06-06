package scripts.Tasks.Runecrafting;

import org.tribot.script.sdk.*;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.Widget;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.BankManager;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.ItemID;
import scripts.NpcChat;
import scripts.Tasks.Runecrafting.Ourania.BankOurania;
import scripts.Tasks.Runecrafting.RunecraftData.RcVars;
import scripts.Utils;

import java.util.Optional;

public class RepairPouches implements Task {

    int NPC_CONTACT_WIDGET = 75;

    @Override
    public Priority priority() {
        return Priority.HIGHEST;
    }

    @Override
    public boolean validate() {
        return Vars.get().currentTask != null &&
                Vars.get().currentTask.equals(SkillTasks.RUNECRAFTING) &&
                (RcVars.get().needToRepairPouches ||
                Inventory.contains(ItemID.DEGRADED_LARGE_POUCH, ItemID.DEGRADED_MEDIUM_POUCH));
    }

    @Override
    public void execute() {
        getRunes();
        if (!Widgets.isVisible(NPC_CONTACT_WIDGET) &&
                Magic.selectSpell("NPC Contact")){
            Waiting.waitUntil(4000, 500, ()-> Widgets.isVisible(NPC_CONTACT_WIDGET));
        }
        if (Widgets.isVisible(NPC_CONTACT_WIDGET)){
            Optional<Widget> dark_mage = Query.widgets().actionContains("Dark Mage").findFirst();
            if(dark_mage.map(w->w.click()).orElse(false)){
                Waiting.waitUntil(8000, 700, ()-> ChatScreen.isOpen());
            }
        }
        if (ChatScreen.isOpen())
            NpcChat.handle(true, "Can you repair");

        RcVars.get().needToRepairPouches = false;
    }

    @Override
    public String toString() {
        return "Repairing Pouches";
    }
    @Override
    public String taskName() {
        return "Runecrafting";
    }


    private boolean getRunes(){
        if (RcVars.get().usingOuraniaAlter && Inventory.contains(ItemID.RUNE_POUCH)){
            if (BankOurania.openBank()){
                Utils.idlePredictableAction();
            } else {
                Bank.open();
            }
            if (Inventory.isFull()){
                Bank.deposit(ItemID.PURE_ESSENCE, 1);
            }
            BankManager.withdraw(10, true, ItemID.COSMIC_RUNE);
            BankManager.close(true);
            return true;
        } else {
            Log.warn("May not have runes for NPC Contact, need to code it in!");
        }
        return false;
    }
}
