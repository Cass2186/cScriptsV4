package scripts.Tasks.Combat;


import org.tribot.api.General;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSItem;
import org.tribot.script.sdk.Equipment;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Waiting;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.BankManager;
import scripts.Data.SkillBank;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.Gear.ProgressiveMeleeGear;
import scripts.ItemID;
import scripts.Requirements.ItemReq;
import scripts.Tasks.Construction.FURNITURE;
import scripts.Tasks.MiscTasks.BuyItems;
import scripts.Utils;

import java.util.ArrayList;
import java.util.List;

public class CombatSetUp implements Task {

    private boolean isBestGearEquipped() {
        List<Integer> bestList = ProgressiveMeleeGear.getBestUsableGearList();
        boolean b = bestList.stream().allMatch(Equipment::contains);
        Log.debug("Is Best Gear Equipped: " + b);
        return b;
    }

    private void getAndEquipGear() {
        List<Integer> bestList = ProgressiveMeleeGear.getBestUsableGearList();
        List<ItemReq> itemReqs = new ArrayList<>();
        bestList.forEach(i -> itemReqs.add(new ItemReq(i, 1, true, true)));

        BankManager.open(true);
        BankManager.depositAll(true);
        BankManager.depositEquipment();
        BankManager.checkEquippedGlory(); //equip glory
        BankManager.checkCombatBracelet(); //equip combat bracelet

        List<ItemReq> newInv = SkillBank.withdraw(itemReqs);
        if (newInv != null && newInv.size() > 0) {
            General.println("[Combat Training]: Creating buy list");
            BuyItems.itemsToBuy = BuyItems.populateBuyList(newInv);
            return;
        }

        BankManager.withdrawArray(ItemID.RING_OF_WEALTH, 1);
        Utils.equipItem(ItemID.RING_OF_WEALTH);
        BankManager.close(true);

    }

    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        //TODO add skill check
        return Vars.get().currentTask != null &&
                (Vars.get().currentTask.equals(SkillTasks.ATTACK) ||
                        Vars.get().currentTask.equals(SkillTasks.STRENGTH) ||
                        Vars.get().currentTask.equals(SkillTasks.DEFENCE)) &&
                !isBestGearEquipped();
    }

    @Override
    public void execute() {
        getAndEquipGear();
    }

    @Override
    public String toString() {
        return "Combat Set up";
    }

    @Override
    public String taskName() {
        return "Combat";
    }
}

