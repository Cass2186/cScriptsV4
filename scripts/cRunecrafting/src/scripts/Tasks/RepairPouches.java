package scripts.Tasks;

import scripts.BankManager;


import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSNPC;
import org.tribot.script.sdk.ChatScreen;
import scripts.*;
import scripts.Data.Const;
import scripts.Data.Vars;

public class RepairPouches implements Task {


    public boolean castNpcContact() {
        if (!BankManager.checkInventoryItems(ItemID.COSMIC_RUNE, ItemID.AIR_RUNE, ItemID.ASTRAL_RUNE)) {
            BankManager.open(true);
            BankManager.depositAllExcept(true, Const.ALL_POUCHES[0], Const.ALL_POUCHES[1],
                    Const.ALL_POUCHES[2], Const.DEGRADED_POUCHES[0],Const.DEGRADED_POUCHES[1], ItemID.ASTRAL_RUNE);
            BankManager.withdraw(5, true, ItemID.COSMIC_RUNE);
            BankManager.withdraw(5, true, ItemID.AIR_RUNE);
            BankManager.withdraw(5, true, ItemID.ASTRAL_RUNE);
            BankManager.close(true);
        }
        if (Magic.selectSpell("NPC Contact"))
            return Timer.waitCondition(() -> Interfaces.isInterfaceSubstantiated(Const.PARENT_INTERFACE_NPC_CONTACT), 5000, 8000);


        return Interfaces.isInterfaceSubstantiated(Const.PARENT_INTERFACE_NPC_CONTACT);
    }

    public void repairNpcContact() {
        if (castNpcContact() && InterfaceUtil.clickInterfaceAction(Const.PARENT_INTERFACE_NPC_CONTACT, "Dark Mage")) {
            Timer.waitCondition(ChatScreen::isOpen, 6000, 8000);
            NPCInteraction.handleConversation("Can you repair my pouches?");
            NPCInteraction.handleConversation();
        }
    }

    /*public void repair() {
        RSNPC[] mage = NPCs.findNearest("Dark mage");
        if (mage.length > 0 && GoToAbyss.INNER_ABYSS.contains(Player.getPosition())) {
            PathingUtil.walkToTile(mage[0].getPosition(), 4, false);
            if (Utils.clickNPC(mage[0], "Repairs", true))
                NPCInteraction.waitForConversationWindow();
        }
    }*/

    @Override
    public String toString() {
        return "Repairing Pouches";
    }

    @Override
    public Priority priority() {
        return Priority.HIGHEST;
    }

    @Override
    public boolean validate() {
        return Inventory.find(Const.DEGRADED_POUCHES).length > 0
                && (Vars.get().abyssCrafting || Vars.get().usingLunarImbue);
    }

    @Override
    public void execute() {
        if (Vars.get().usingLunarImbue) {
            repairNpcContact();
        } else if (Vars.get().abyssCrafting) {
          //  GoToAbyss.teleportMage();
          //  GoToAbyss.enterInnerAbyss();
          //  repair();
        }
    }
}