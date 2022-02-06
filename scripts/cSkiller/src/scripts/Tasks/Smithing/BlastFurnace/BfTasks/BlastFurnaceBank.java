package scripts.Tasks.Smithing.BlastFurnace.BfTasks;


import dax.walker.utils.AccurateMouse;
import org.tribot.api.General;
import org.tribot.api.input.Keyboard;
import org.tribot.api2007.*;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.cache.BankCache;
import scripts.*;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.Tasks.Smithing.BlastFurnace.BfData.BfConst;
import scripts.Tasks.Smithing.BlastFurnace.BfData.BfVars;

import java.awt.*;
import java.awt.event.KeyEvent;

public class BlastFurnaceBank implements Task {

    int failNum = 0;

    @Override
    public String toString() {
        return "Banking";
    }

    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        if (Vars.get().currentTask != null && Vars.get().currentTask.equals(SkillTasks.SMITHING)) {
            return Inventory.find(Filters.Items.nameContains("ore")).length < 5
                    && Utils.getVarBitValue(Varbits.BAR_DISPENSER.getId()) == 0
                    || (Inventory.isFull() && Inventory.find(Filters.Items.nameContains("bar")).length > 5);
        }
        return false;
    }

    @Override
    public void execute() {
        Log.debug("");
        if (BfVars.get().useGoldSmith)
            bank(BfConst.GOLD_ORE);
        else
            bank(BfConst.IRON_ORE);

    }

    @Override
    public String taskName() {
        return "Smithing - BF";
    }

    public static void coalBagMessaageHandler(String message) {
        if (message.toLowerCase().contains("the coal bag contains")) {
            General.println("[Message Listening]: Coal bag is full");
            BfVars.get().coalBagFull = true;
        }
        if (message.toLowerCase().contains("the coal bag is now empty")) {
            BfVars.get().coalBagFull = false;
        }
    }


    public boolean bank(int oreId) {
        RSObject[] bank = Objects.findNearest(20, Filters.Objects.nameContains("Bank"));
        if (bank.length > 0) {
            if (!bank[0].isClickable()) {
                General.println("[Debug]: Walking to bank tile");
                Walking.blindWalkTo(BfConst.BANK_TILE);
            }

            if (BfVars.get().useGoldSmith && Equipment.isEquipped(BfConst.ICE_GLOVES)) {
                if (!Player.getPosition().equals(BfConst.BANK_TILE))
                    Walking.clickTileMS(BfConst.BANK_TILE, "Walk here");
                AntiBan.waitItemInteractionDelay();
                General.println("[Debug]: Equiping goldsmith gauntlets");
                Utils.equipItem(BfConst.GOLDSMITH_GAUNTLETS, "Wear");
                AntiBan.waitItemInteractionDelay();
            }

            if (!Banking.isBankScreenOpen() && Utils.clickObject(bank[0], "Use"))
                Timer.waitCondition(Banking::isBankScreenOpen, 7000, 12000);

            if (Banking.isBankScreenOpen()) {
                BankCache.update();
                if (BankCache.getStack(ItemID.COAL) < 28 || BankCache.getStack(ItemID.IRON_ORE) < 28){
                    throw new NullPointerException("Not enough ore");
                }
                if (!BfVars.get().useGoldSmith)
                    fillCoalBag();

                BankManager.depositAllExcept(false, BfConst.ALL_COAL_BAG[0], BfConst.ALL_COAL_BAG[1],
                        ItemID.STAMINA_POTION[0], ItemID.STAMINA_POTION[1], BfConst.BUCKET_OF_WATER,
                        ItemID.STAMINA_POTION[2], ItemID.STAMINA_POTION[3], ItemID.COINS, BfConst.BUCKET,
                        BfConst.ICE_GLOVES, BfConst.GOLDSMITH_GAUNTLETS);

                Timer.waitCondition(() -> Inventory.find(Filters.Items.nameContains("bar")).length == 0, 3000, 5000);

                if (!checkIceGloves()
                        && !Equipment.isEquipped(BfConst.ICE_GLOVES) && Inventory.find(BfConst.ICE_GLOVES).length == 0
                        && Inventory.find(BfConst.BUCKET_OF_WATER, BfConst.BUCKET).length == 0)
                    BankManager.withdraw(1, true, BfConst.BUCKET);

                if (Inventory.find(ItemID.STAMINA_POTION).length == 0)
                    BankManager.withdrawArray(ItemID.STAMINA_POTION, 1);

                if (Skills.getActualLevel(Skills.SKILLS.SMITHING) < 60
                        && Inventory.find(995).length == 0)
                    BankManager.withdraw(0, true, 995);

                RSItem[] bars = Inventory.find(Filters.Items.nameContains("bar"));
                if (bars.length > 0) {
                    General.println("[Bank]: Depositing bars failsafe", Color.RED);
                    int id = bars[0].getID();
                    Banking.deposit(0, id);
                }

                if (Utils.getVarBitValue(Varbits.BLAST_FURNACE_COAL.getId()) < 26) {
                    BankManager.withdraw(0, true, BfConst.COAL);
                } else
                    BankManager.withdraw(0, true, oreId);

                Keyboard.pressKeys(KeyEvent.VK_ESCAPE);
                Timer.waitCondition(() -> !Banking.isBankScreenOpen(), 500, 750);
                BankManager.close(true);

                if (!BfVars.get().useGoldSmith && Inventory.find(BfConst.IRON_ORE).length < 15) {
                    failNum++;
                    if (failNum > 2) {
                        General.println("[Bank]: Ending due to lack of iron ore", Color.RED);
                        Vars.get().currentTask = null;
                        return false;
                    }

                } else {
                    General.println("[Bank]: Resetting Fail Number");
                    failNum = 0;
                }

                return Inventory.find(Filters.Items.nameContains("ore")).length > 5;
            }
        }
        return false;
    }


    public boolean checkIceGloves() {
        if (Equipment.isEquipped(BfConst.ICE_GLOVES))
            return true;

        if (Banking.openBank()) {
            if (Banking.find(BfConst.ICE_GLOVES).length == 0) {
                return BfVars.get().usingIceGloves = false;
            } else {
                General.println("[Debug]: Getting Ice gloves");
                BankManager.withdraw(1, true, BfConst.ICE_GLOVES);
                Utils.equipItem(BfConst.ICE_GLOVES);
                return true;
            }
        }
        return false;
    }

    public boolean fillCoalBag() {
        if (Inventory.find(BfConst.ALL_COAL_BAG).length > 0) {
            RSItem[] fullBag = Inventory.find(Filters.Items.actionsContains("Empty")
                    .and(Filters.Items.idEquals(BfConst.ALL_COAL_BAG)));
            if (fullBag.length > 0) {
                General.println("[Bank]: Action contains empty");
                if (fullBag[0].click("Empty")) {
                    General.sleep(100);
                    Timer.waitCondition(() -> Inventory.find(Filters.Items.actionsContains("Fill")).length > 0, 2500, 3000);
                    General.sleep(General.random(100, 500));
                }
            }
            RSItem[] emptyBag = Inventory.find(Filters.Items.actionsContains("Fill"));
            if (emptyBag.length > 0 && AccurateMouse.click(emptyBag[0], "Fill")) {
                General.sleep(General.random(100, 500));
                return true;
            }
            if (emptyBag.length > 0 && emptyBag[0].click("Fill")) {
                General.println("[Bank]: Action contains fill");
                return true;
            }
        }
        return false;
    }
}

