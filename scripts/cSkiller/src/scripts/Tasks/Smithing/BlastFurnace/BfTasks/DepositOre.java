package scripts.Tasks.Smithing.BlastFurnace.BfTasks;


import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api.input.Keyboard;
import org.tribot.api2007.*;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSItem;
import scripts.*;
import scripts.API.Priority;
import scripts.API.Task;

import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.Tasks.Smithing.BlastFurnace.BfData.BfConst;
import scripts.Tasks.Smithing.BlastFurnace.BfData.BfVars;

;

import java.awt.*;
import java.awt.event.KeyEvent;

public class DepositOre implements Task {

    @Override
    public String toString() {
        return "Depositing Ore on Conveyor";
    }

    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        if (Vars.get().currentTask != null && Vars.get().currentTask.equals(SkillTasks.SMITHING)) {
            return (Inventory.find(Filters.Items.nameContains("ore")).length > 5
                    || Inventory.find(Filters.Items.nameContains("coal")).length > 5)
                    && Game.getSetting(261) == 0; //coal bag setting, it's 16 when empty and 0 when >0 items
        }
        return false;
    }

    @Override
    public void execute() {
        if (Banking.isBankScreenOpen())
            BankManager.close(true);

        if (BfVars.get().useGoldSmith && !Equipment.isEquipped(BfConst.GOLDSMITH_GAUNTLETS))
            Utils.equipItem(BfConst.GOLDSMITH_GAUNTLETS, "Wear");


        clickConveyor();

        if (NPCInteraction.isConversationWindowUp())
            NPCInteraction.handleConversation("Yes, and don't ask again.");

        if (emptyCoalBag()) {
            clickConveyor();

        }
        int chance = General.random(0, 100);
        if (chance < 38) {
            if (PathingUtil.clickScreenWalk(BfConst.BAR_COLLECTION_TILE_LEFT_SIDE)) {
                General.println(Utils.getVarBitValue(Varbits.BAR_DISPENSER.value) + " == value");
                Timer.waitCondition(() -> Utils.getVarBitValue(Varbits.BAR_DISPENSER.value) > 1 || Interfaces.isInterfaceSubstantiated(233, 2), 3000, 6000);

            }
        } else if (PathingUtil.clickScreenWalk(BfConst.BAR_COLLECTION_TILE)) {
            General.println(Utils.getVarBitValue(Varbits.BAR_DISPENSER.value) + " == value");
            Timer.waitCondition(() -> Utils.getVarBitValue(Varbits.BAR_DISPENSER.value) > 1 ||
                    Interfaces.isInterfaceSubstantiated(233, 2), 3000, 6000);
        }

        if (Interfaces.isInterfaceSubstantiated(233, 2))
            NPCInteraction.handleConversation();


    }

    @Override
    public String taskName() {
        return "Smithing";
    }

    public int shouldHover = General.random(0, 100);

    public boolean clickConveyor() {
        if (Utils.clickObject(9100, "Put-ore-on", false)) {

            if (Player.getPosition().equals(BfConst.ORE_DEPOSIT_TILE)) // was prev !Player.getPosition...
                Timer.waitCondition(Player::isMoving, 500, 900);

            if (shouldHover < 3 && Player.isMoving()) {
                General.println("[Antiban]: Hovering XP", Color.RED);
                Utils.hoverXp(Skills.SKILLS.SMITHING, 100);
                shouldHover = General.random(0, 100);
            }

            RSItem[] fullBag = Inventory.find(Filters.Items.actionsContains("Empty").and(Filters.Items.idEquals(BfConst.ALL_COAL_BAG)));
            if (fullBag.length > 0 && Player.isMoving())
                fullBag[0].hover();

            if (BfVars.get().abc2Sleep)
                return Timer.abc2WaitCondition(() -> Inventory.getAll().length < 5, 10000, 15000);

            return Timer.waitCondition(() -> Inventory.getAll().length < 5, 10000, 14000);
        }
        General.println("[DepositOre]: Click conveyor returning false");
        return false;
    }

    public boolean emptyCoalBag() {
        if (Inventory.find(BfConst.ALL_COAL_BAG).length > 0) {
            RSItem[] fullBag = Inventory.find(Filters.Items.actionsContains("Empty").and(Filters.Items.idEquals(BfConst.ALL_COAL_BAG)));

            long time = System.currentTimeMillis();
            Keyboard.sendPress((char) KeyEvent.VK_SHIFT, 16);

            if (fullBag.length > 0 && fullBag[0].click("Empty")) {
                Timer.waitCondition(() -> Inventory.find(BfConst.COAL).length > 2, 1500, 2500);
                General.sleep(General.random(100, 300));
                Keyboard.sendRelease((char) KeyEvent.VK_SHIFT, 16);
                if (Interfaces.isInterfaceSubstantiated(193, 2)) {
                    General.println("[Deposit ore]: Coal bag Interface open");
                    BfVars.get().coalBagFull = false;
                    return true;
                }
            }
            Keyboard.sendRelease((char) KeyEvent.VK_SHIFT, 16);
            RSItem[] emptyBag = Inventory.find(Filters.Items.actionsContains("Fill"));
            if (emptyBag.length > 0) {
                BfVars.get().coalBagFull = false;
                return true;
            }
        }
        return false;

    }

}
