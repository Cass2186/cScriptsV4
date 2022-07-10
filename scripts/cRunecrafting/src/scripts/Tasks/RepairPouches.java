package scripts.Tasks;

import org.tribot.script.sdk.*;
import org.tribot.script.sdk.query.Query;
import scripts.BankManager;


import dax.walker_engine.interaction_handling.NPCInteraction;
import scripts.*;
import scripts.Data.Const;
import scripts.Data.Vars;

public class RepairPouches implements Task {


    public boolean castNpcContact() {
        if (!BankManager.checkInventoryItems(ItemID.COSMIC_RUNE, ItemID.AIR_RUNE, ItemID.ASTRAL_RUNE)) {
            BankManager.open(true);
            BankManager.depositAllExcept(true, Const.ALL_POUCHES[0], Const.ALL_POUCHES[1],
                    Const.ALL_POUCHES[2], Const.DEGRADED_POUCHES[0], Const.DEGRADED_POUCHES[1], ItemID.ASTRAL_RUNE);
            BankManager.withdraw(5, true, ItemID.COSMIC_RUNE);
            BankManager.withdraw(5, true, ItemID.AIR_RUNE);
            BankManager.withdraw(5, true, ItemID.ASTRAL_RUNE);
            BankManager.close(true);
        }
        if (Magic.selectSpell("NPC Contact"))
            return Waiting.waitUntil(7000, 150, () ->
                    Widgets.get(Const.PARENT_INTERFACE_NPC_CONTACT).isPresent());


        return Widgets.get(Const.PARENT_INTERFACE_NPC_CONTACT).isPresent();
    }

    public void repairNpcContact() {
        if (castNpcContact() && InterfaceUtil.clickInterfaceAction(Const.PARENT_INTERFACE_NPC_CONTACT, "Dark Mage"))
            Waiting.waitUntil(7000, 150, ChatScreen::isOpen);

        if (ChatScreen.isOpen())
            ChatScreen.handle(() -> !ChatScreen.isOpen(), "Can you repair my pouches?");

    }

    private boolean useDarkMage() {
        if (Query.npcs().actionContains("Repairs")
                .findClosestByPathDistance()
                .map(npc -> npc.interact("Repairs")).orElse(false)) {
            return NpcChat.waitForChatScreen();
        }
        return false;
    }


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
        return Inventory.contains(Const.DEGRADED_POUCHES)
                && (Vars.get().abyssCrafting || Vars.get().usingLunarImbue);
    }

    @Override
    public void execute() {
        if (Vars.get().usingLunarImbue && Magic.getActiveSpellBook() == Magic.SpellBook.LUNAR) {
            repairNpcContact();
        } else if (Magic.getActiveSpellBook() != Magic.SpellBook.LUNAR && Vars.get().abyssCrafting) {
            if (GoToRcAltar.enterInnerAbyss())
                useDarkMage();
        } else if (Magic.getActiveSpellBook() != Magic.SpellBook.LUNAR)
            Log.error("Cannot repair pouches, due to invalid spell book",
                    new NullPointerException());
    }
}