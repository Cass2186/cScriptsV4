package scripts.Tasks.Cooking;

import org.tribot.api.General;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;
import org.tribot.api2007.Skills;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSItem;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.*;
import scripts.Data.Const;
import scripts.Data.Enums.CookItems;
import scripts.Data.SkillBank;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.Requirements.ItemReq;
import scripts.Tasks.MiscTasks.BuyItems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BankCook implements Task {


    public void bank() {
        List<ItemReq> inv;


        if (!Const.CATHERBY_BANK_AND_COOK_AREA.contains(Player.getPosition())
                && Skills.getActualLevel(Skills.SKILLS.COOKING) < 35) {
            General.println("[BankCook]: Going to Catherby");
            BankManager.open(true);
            inv = new ArrayList<>(
                    Arrays.asList(
                            new ItemReq(ItemID.CAMELOT_TELEPORT, 1),
                            new ItemReq(ItemID.COINS, 1000)
                    )
            );
            SkillBank.withdraw(inv);
            BankManager.close(true);

            PathingUtil.walkToArea(Const.CATHERBY_BANK_AND_COOK_AREA, false);

        }
        if (Utils.clickObject("Bank chest", "Use", true))
            Timer.waitCondition(Banking::isBankScreenOpen, 5000, 7500);
        else
            BankManager.open(true);

        BankManager.depositAll(true);


        if (Skills.getActualLevel(Skills.SKILLS.COOKING) >= 35) {
            inv = new ArrayList<>(
                    Arrays.asList(
                            new ItemReq(ItemID.GRAPES, 14),
                            new ItemReq(ItemID.JUG_OF_WATER, 14)
                    )
            );
        } else {
            inv = new ArrayList<>(
                    Arrays.asList(new ItemReq(CookItems.getCookingRawFoodId(), 0)));
        }
        List<ItemReq> newInv = SkillBank.withdraw(inv);
        if (newInv != null && newInv.size() > 0) {
            General.println("[Cooking Training]: Creating buy list");
            BuyItems.itemsToBuy = BuyItems.populateBuyList(CookItems.getRequiredRawFood());
            BankManager.withdrawArray(ItemID.RING_OF_WEALTH, 1);
            RSItem[] wealth = Inventory.find(Filters.Items.nameContains("wealth"));
            if (wealth.length > 0)
                Utils.equipItem(wealth[0].getID());
            return;
        }
        BankManager.close(true);
        // if (!CookFood.hasRawFood())
        //cSkiller.changeRunningBool(false); //ends script

    }


    @Override
    public String toString() {
        return "Banking";
    }

    @Override
    public Priority priority() {
        return Priority.HIGH;
    }

    @Override
    public boolean validate() {
        return !CookFood.hasRawFood() && Vars.get().currentTask != null &&
                Vars.get().currentTask.equals(SkillTasks.COOKING);
    }

    @Override
    public void execute() {
        bank();
    }

    @Override
    public String taskName() {
        return "Cooking";
    }
}
