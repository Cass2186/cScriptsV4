package scripts.Tasks.Cooking.CookTasks;

import org.tribot.api.General;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Skills;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSItem;
import org.tribot.script.sdk.*;
import org.tribot.script.sdk.cache.BankCache;
import org.tribot.script.sdk.tasks.Amount;
import org.tribot.script.sdk.tasks.BankTask;
import org.tribot.script.sdk.types.WorldTile;
import org.tribot.script.sdk.walking.GlobalWalking;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.*;
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

    WorldTile CATHERBY_BANK_TILE = new WorldTile(2809, 3441, 0);

    BankTask wineTask = BankTask.builder()
            .addInvItem(ItemID.GRAPES, Amount.of(14))
            .addInvItem(ItemID.JUG_OF_WATER, Amount.of(14))
            .build();

    public void bank() {
        List<ItemReq> inv;
        if (Skill.COOKING.getActualLevel() >= 35) {
            inv = new ArrayList<>(
                    Arrays.asList(
                            new ItemReq(ItemID.GRAPES, 14, 1),
                            new ItemReq(ItemID.JUG_OF_WATER, 14, 1)
                    )
            );
        } else {
            inv = new ArrayList<>(
                    Arrays.asList(new ItemReq(CookItems.getCookingRawFoodId(), 0)));
        }

        if (goToCatherbyBank(inv)) {
            Log.info("Arrived at Catherby");
            if (Utils.clickObject("Bank chest", "Use", true))
                Timer.waitCondition(Banking::isBankScreenOpen, 5000, 7500);
            else if (Utils.clickObject("Bank booth", "Bank", false))
                Timer.waitCondition(Banking::isBankScreenOpen, 6500, 7500);
        }/* else if (Skill.COOKING.getActualLevel() < 35 && GlobalWalking.walkTo(CATHERBY_BANK_TILE) &&
                PathingUtil.humanMovementIdle(CATHERBY_BANK_TILE)) {
            Log.info("Going to catherby bank");
            Waiting.waitUntil(6000, () -> CATHERBY_BANK_TILE.distanceTo(MyPlayer.getPosition()) < 3);
            BankManager.open(true);

        }*/ else {
            BankManager.open(true);
        }
        BankManager.depositAll(true);
        if (Skill.COOKING.getActualLevel() < 35 && CATHERBY_BANK_TILE.distance() > 20 &&
                checkBankCache(inv)) {
            BankManager.depositAll(true);
            Log.warn("Updating cache");
            BankCache.update();
            return;
        }
        if (Skill.COOKING.getActualLevel() >= 35) {
            wineTask.execute();
            if (wineTask.isSatisfied()) {
                BankManager.close(true);
                Log.info("Closed");
                return;
            }
        }

        List<ItemReq> newInv = SkillBank.withdraw(inv);
        if (newInv != null && newInv.size() > 0) {
            General.println("[Cooking Training]: Creating buy list");
            BuyItems.itemsToBuy = BuyItems.populateBuyList(CookItems.getRequiredRawFood());
            BankManager.withdrawArray(ItemID.RING_OF_WEALTH_REVERSED, 1);
            RSItem[] wealth = Inventory.find(Filters.Items.nameContains("wealth"));
            if (wealth.length > 0)
                Utils.equipItem(wealth[0].getID());
            return;
        }

        BankManager.close(true);
        // if (!CookFood.hasRawFood())
        //cSkiller.changeRunningBool(false); //ends script

    }

    private boolean checkBankCache(List<ItemReq> inv) {
        if (!BankCache.isInitialized()) {
            BankManager.open(true);
            BankManager.depositAll(true);
            BankCache.update();
        }
        if (BankCache.isInitialized()) {
            for (ItemReq i : inv) {
                int reqAmount = i.getAmount() <= 0 ? 1 : i.getAmount();
                if (BankCache.getStack(i.getId()) < reqAmount) {
                    Log.warn("Missing Bank item " + i.getId() + " in amount of at least " + reqAmount);
                    return false;
                } else
                    Log.info("We have Bank item " + i.getId() + " in amount of at least " + reqAmount);

            }
            return true;
        }
        return false;
    }

    private boolean goToCatherbyBank(List<ItemReq> inv) {
        if (CATHERBY_BANK_TILE.distanceTo(MyPlayer.getTile()) > 20
                && Skills.getActualLevel(Skills.SKILLS.COOKING) < 35) {
            General.println("[BankCook]: Going to Catherby Bank");
            BankManager.open(true);
            BankCache.update();
            if (!checkBankCache(inv))
                return false;

            inv = new ArrayList<>(
                    Arrays.asList(
                            new ItemReq(ItemID.CAMELOT_TELEPORT, 1)
                    )
            );
            List<ItemReq> newInv = SkillBank.withdraw(inv);
            if (newInv != null && newInv.size() > 0) {
                General.println("[Cooking Training]: Creating buy list");
                BuyItems.itemsToBuy = BuyItems.populateBuyList(CookItems.getRequiredRawFood());
                BankManager.withdrawArray(ItemID.RING_OF_WEALTH_REVERSED, 1);
                RSItem[] wealth = Inventory.find(Filters.Items.nameContains("wealth"));
                if (wealth.length > 0)
                    Utils.equipItem(wealth[0].getID());
                return false;
            }
            if (org.tribot.script.sdk.Inventory.contains(ItemID.CAMELOT_TELEPORT)) {
                BankManager.close(true);
                if (GlobalWalking.walkTo(CATHERBY_BANK_TILE))
                    Waiting.waitUntil(5000, () -> CATHERBY_BANK_TILE.distanceTo(MyPlayer.getPosition()) < 10);
                return CATHERBY_BANK_TILE.distanceTo(MyPlayer.getTile()) < 20;
            }
        }
        return CATHERBY_BANK_TILE.distance() < 20;
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
