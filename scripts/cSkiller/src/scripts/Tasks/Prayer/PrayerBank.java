package scripts.Tasks.Prayer;

import org.tribot.api.General;
import org.tribot.api2007.Skills;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.tasks.Amount;
import org.tribot.script.sdk.tasks.BankTask;
import org.tribot.script.sdk.types.InventoryItem;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.BankManager;
import scripts.Data.Enums.Bones;
import scripts.Data.SkillBank;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.ItemID;
import scripts.Requirements.ItemReq;
import scripts.Tasks.MiscTasks.BuyItems;
import scripts.Utils;

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
        return Vars.get().currentTask != null &&
                Vars.get().currentTask.equals(SkillTasks.PRAYER) &&
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
        General.println("DetermineResourcesToNextItem: " + (xpTillMax / Bones.DRAGON_BONES.getGildedAltarXp()));
        return (int) (xpTillMax / Bones.DRAGON_BONES.getGildedAltarXp()) + 5;
    }


    BankTask tsk = BankTask.builder()
            .addInvItem(ItemID.DRAGON_BONES, Amount.of(26))
            .addInvItem(ItemID.COINS, Amount.fill(1000))
            .addNotedInvItem(ItemID.DRAGON_BONES, Amount.fill(1)).build();

    public void getBones() {
        List<ItemReq> inv = new ArrayList<>(
                Arrays.asList(
                        new ItemReq(ItemID.DRAGON_BONES, 25, 0),
                        new ItemReq(ItemID.COINS, 0, 100000)
                )
        );
        //make req for notes bones
        inv.add(new ItemReq.Builder()
                .id(ItemID.DRAGON_BONES)
                .isItemNoted(true)
                .amount(determineResourcesToNextItem()-25).build());

        BankManager.open(true);
        BankManager.depositAll(true);

        List<ItemReq> newInv = SkillBank.withdraw(inv);

        if ((newInv != null && newInv.size() > 0)) {
            General.println("[Prayer Training]: Creating buy list");
            BuyItems.itemsToBuy = BuyItems.populateBuyList(getRequiredItemList());
            return;
        }
        BankManager.close(true);

    }

    public List<ItemReq> getRequiredItemList() {
        List<ItemReq> i = new ArrayList<>();
        i.add(new ItemReq(ItemID.DRAGON_BONES, determineResourcesToNextItem()));
        return i;
    }

}
