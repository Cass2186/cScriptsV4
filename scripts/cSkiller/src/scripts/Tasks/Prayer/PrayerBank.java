package scripts.Tasks.Prayer;

import org.tribot.api.General;
import org.tribot.api2007.Equipment;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Skills;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.tasks.Amount;
import org.tribot.script.sdk.tasks.BankTask;
import org.tribot.script.sdk.tasks.BankTaskError;
import org.tribot.script.sdk.types.InventoryItem;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.BankManager;
import scripts.Data.Enums.HerbloreItems;
import scripts.Data.SkillBank;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.ItemId;
import scripts.Requirements.ItemReq;
import scripts.Tasks.MiscTasks.BuyItems;
import scripts.Utils;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class PrayerBank implements Task {
    @Override
    public String toString() {
        return "Banking";
    }

    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        Optional<InventoryItem> item = Query.inventory().nameContains("bones").findFirst();
        return Vars.get().currentTask != null && Vars.get().currentTask.equals(SkillTasks.PRAYER) &&
                item.isEmpty();
    }

    @Override
    public void execute() {
        getBones();
    }

    @Override
    public String taskName() {
        return "Prayer";
    }

    public int determineResourcesToNextItem() {
        if (Skills.getActualLevel(Skills.SKILLS.PRAYER) >= SkillTasks.PRAYER.getEndLevel())
            return 0;
        int max = SkillTasks.PRAYER.getEndLevel();
        int xpTillMax = Skills.getXPToLevel(Skills.SKILLS.PRAYER, max);
        General.println("DetermineResourcesToNextItem: " + (xpTillMax / 252));
        return (int) (xpTillMax / 252) + 5;
    }


    BankTask tsk = BankTask.builder()
            .addInvItem(ItemId.DRAGON_BONES, Amount.of(26))
            .addInvItem(ItemId.COINS, Amount.fill(1000))
            .addNotedInvItem(ItemId.DRAGON_BONES, Amount.fill(1)).build();

    public void getBones() {
        List<ItemReq> inv = new ArrayList<>(
                Arrays.asList(
                        new ItemReq(ItemId.DRAGON_BONES, 26),
                        new ItemReq(ItemId.COINS, 0, 1000)
                )
        );
        List<ItemReq> notedInv = new ArrayList<>(
                Arrays.asList(
                        new ItemReq(ItemId.DRAGON_BONES, 0)

                )
        );
        BankManager.open(true);
        BankManager.depositAll(true);

        List<ItemReq> newInv = SkillBank.withdraw(inv);
        List<ItemReq> newNotedInv = null;
        if(BankManager.turnNotesOn()){
            BankManager.withdraw(0, true, ItemId.DRAGON_BONES);
        }
        Optional<InventoryItem> item = Query.inventory().nameContains("bones").isNoted().findFirst();
        if ((newInv != null && newInv.size() > 0) || item.isEmpty()) {
            General.println("[Prayer Training]: Creating buy list");
            BuyItems.itemsToBuy = BuyItems.populateBuyList(getRequiredItemList());
            return;
        }
        BankManager.close(true);

    }

    public List<ItemReq> getRequiredItemList() {
        List<ItemReq> i = new ArrayList<>();
        i.add(new ItemReq(ItemId.DRAGON_BONES, determineResourcesToNextItem()));
        return i;
    }

}
