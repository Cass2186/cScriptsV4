package scripts.Tasks.Woodcutting;

import org.tribot.api.General;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Equipment;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Skills;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSItem;
import org.tribot.script.sdk.query.Query;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.BankManager;
import scripts.Data.SkillBank;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.ItemID;
import scripts.Requirements.ItemReq;
import scripts.Tasks.MiscTasks.BuyItems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WoodcuttingBank implements Task {

    public void bankForAxe(){
        int axeId = getBestAxe();
        BankManager.open(true);

        if (Banking.isBankScreenOpen() && Banking.find(axeId).length < 1) {
            General.println("[Debug]: Missing Axe ID: " + axeId);

        }
        BankManager.depositAll(true);

        List<ItemReq> inv = new ArrayList<>(Arrays.asList(new ItemReq(axeId, 1)));

        inv = SkillBank.withdraw(inv);
        if (inv != null && inv.size() > 0) {
            General.println("[Woodcutting Training]: Creating buy list");
               BuyItems.populateBuyList(inv);
            BuyItems buyList = new BuyItems(BuyItems.populateBuyList(inv));
            buyList.buyItems();
        }
    }

    public static int getBestAxe() {
        if (Skills.getCurrentLevel(Skills.SKILLS.WOODCUTTING) < 6) {
            return ItemID.AXE_IDS[0]; //iron
        } else if (Skills.getCurrentLevel(Skills.SKILLS.WOODCUTTING) < 21) {
            return ItemID.AXE_IDS[1]; //steel
        } else if (Skills.getCurrentLevel(Skills.SKILLS.WOODCUTTING) < 31) {
            return ItemID.AXE_IDS[2]; //mithril
        } else if (Skills.getCurrentLevel(Skills.SKILLS.WOODCUTTING) < 41) {
            return ItemID.AXE_IDS[3]; //adamant
        } else if (Skills.getCurrentLevel(Skills.SKILLS.WOODCUTTING) < 61) {
            return ItemID.AXE_IDS[4]; //rune
        } else
            return ItemID.AXE_IDS[5]; //dragon
    }


    @Override
    public Priority priority() {
        return Priority.HIGH;
    }

    @Override
    public boolean validate() {
        RSItem[] invAxe = Inventory.find(getBestAxe());

        boolean equippedAxe = Query.equipment()
                .idEquals(getBestAxe())
                .isAny();


        return Vars.get().currentTask != null &&
                Vars.get().currentTask.equals(SkillTasks.WOODCUTTING)
                && (invAxe.length == 0 && !equippedAxe);
    }

    @Override
    public void execute() {
        bankForAxe();
    }

    @Override
    public String taskName() {
        return "Woodcutting";
    }

    @Override
    public String toString() {
        return "Banking";
    }
}
