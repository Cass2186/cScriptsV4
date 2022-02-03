package scripts.Tasks.Fletching;

import org.tribot.api.General;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Skills;
import org.tribot.script.sdk.tasks.Amount;
import org.tribot.script.sdk.tasks.BankTask;
import org.tribot.script.sdk.tasks.BankTaskError;
import org.tribot.script.sdk.tasks.InsufficientItemError;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.BankManager;
import scripts.Data.Enums.FletchingItem;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.ItemID;
import scripts.Requirements.ItemReq;
import scripts.Tasks.MiscTasks.BuyItems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class FletchBank implements Task {


    public boolean  check (int item1) {
        return Inventory.find(item1).length < 1 || (Inventory.find(ItemID.KNIFE).length < 1 && Inventory.find(ItemID.FEATHERS).length < 1);
    }
    public void bank(int item1) {
        if (Inventory.find(item1).length < 1 || Inventory.find(ItemID.KNIFE).length < 1) {

            List<ItemReq> inv = new ArrayList<>(
                    Arrays.asList(
                            new ItemReq(item1, 0),
                            new ItemReq(ItemID.KNIFE, 1)
                    )
            );

            BankManager.open(true);
            BankTask tsk = BankTask.builder()
                    .addInvItem(ItemID.KNIFE, Amount.of(1))
                    .addInvItem(item1, Amount.fill(1))
                    .build();

            Optional<BankTaskError> err = tsk.execute();
            if (err.isPresent()) {
                General.println("[Fletching Training]: Creating buy list");
                BuyItems.itemsToBuy = BuyItems.populateBuyList(FletchingItem.getRequiredItemList());
                return;
            }

         /*   List<ItemReq> newInv = SkillBank.withdraw(inv);
            if (newInv != null && newInv.size() > 0) {
                General.println("[Fletching Training]: Creating buy list");
                BuyItems.itemsToBuy = BuyItems.populateBuyList(HerbloreItems.getRequiredItemList());
                return;
            }*/
            BankManager.close(true);
        }
    }

    public void bankFeatherItem(int item1) {
        if (Inventory.find(item1).length < 1 || Inventory.find(ItemID.FEATHERS).length < 1) {

            List<ItemReq> inv = new ArrayList<>(
                    Arrays.asList(
                            new ItemReq(item1, 0),
                            new ItemReq(ItemID.FEATHERS, 0)
                    )
            );

            BankManager.open(true);
            BankManager.depositAll(true);
            BankTask tsk = BankTask.builder()
                    .addInvItem(ItemID.FEATHERS, Amount.fill(100))
                    .addInvItem(item1, Amount.fill(1))
                    .build();

            Optional<BankTaskError> err = tsk.execute();
            if (err.isPresent()) {
                General.println("[Fletching Training]: Creating buy list");
                BuyItems.itemsToBuy = BuyItems.populateBuyList(FletchingItem.getRequiredItemList());
                return;
            }

         /*   List<ItemReq> newInv = SkillBank.withdraw(inv);
            if (newInv != null && newInv.size() > 0) {
                General.println("[Fletching Training]: Creating buy list");
                BuyItems.itemsToBuy = BuyItems.populateBuyList(HerbloreItems.getRequiredItemList());
                return;
            }*/
            BankManager.close(true);
        }
    }

    public void handleBankError(Optional<BankTaskError> err) {
        if (err.isEmpty()) return;

        if (err.get() instanceof InsufficientItemError) {
            ((InsufficientItemError) err.get()).getActualAmt();
        }
    }
    @Override
    public String toString(){
        return "Fletch bank";
    }

    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        boolean b =      Vars.get().currentTask != null  && Vars.get().currentTask.equals(SkillTasks.FLETCHING);

        if (Skills.getActualLevel(Skills.SKILLS.FLETCHING) < 20) {
            return b &&     (check(ItemID.ARROW_SHAFT) || Inventory.find(ItemID.FEATHERS).length == 0);

        } else if (Skills.getActualLevel(Skills.SKILLS.FLETCHING) < 25) {
            return b &&   check(ItemID.LOG_IDS[1]);

        } else if (Skills.getActualLevel(Skills.SKILLS.FLETCHING) < 35) {
            return b &&    check(ItemID.LOG_IDS[1]);

        } else if (Skills.getActualLevel(Skills.SKILLS.FLETCHING) < 40) { //willow short
            return b &&  check(ItemID.LOG_IDS[2]);

        } else if (Skills.getActualLevel(Skills.SKILLS.FLETCHING) < 50) { //willow long
            return b &&    check(ItemID.LOG_IDS[2]);

        } else if (Skills.getActualLevel(Skills.SKILLS.FLETCHING) < 55) { //maple shortbow
            return b &&   check(ItemID.LOG_IDS[3]);

        } else if (Skills.getActualLevel(Skills.SKILLS.FLETCHING) < 65) { //maple longbow
            return b &&    check(ItemID.LOG_IDS[3]);

        } else if (Skills.getActualLevel(Skills.SKILLS.FLETCHING) < 70) {// yew short
            return b &&   check(ItemID.LOG_IDS[4]);

        } else if (Skills.getActualLevel(Skills.SKILLS.FLETCHING) < 75) {// yew long
            return b &&   check(ItemID.LOG_IDS[4]);

        }
        return false;
    }

    @Override
    public void execute() {
        if (Skills.getActualLevel(Skills.SKILLS.FLETCHING) < 20) {
            bankFeatherItem(ItemID.ARROW_SHAFT);

        } else if (Skills.getActualLevel(Skills.SKILLS.FLETCHING) < 25) {
            bank(ItemID.LOG_IDS[1]);

        } else if (Skills.getActualLevel(Skills.SKILLS.FLETCHING) < 35) {
            bank(ItemID.LOG_IDS[1]);

        } else if (Skills.getActualLevel(Skills.SKILLS.FLETCHING) < 40) { //willow short
            bank(ItemID.LOG_IDS[2]);

        } else if (Skills.getActualLevel(Skills.SKILLS.FLETCHING) < 50) { //willow long
            bank(ItemID.LOG_IDS[2]);

        } else if (Skills.getActualLevel(Skills.SKILLS.FLETCHING) < 55) { //maple shortbow
            bank(ItemID.LOG_IDS[3]);

        } else if (Skills.getActualLevel(Skills.SKILLS.FLETCHING) < 65) { //maple longbow
            bank(ItemID.LOG_IDS[3]);

        } else if (Skills.getActualLevel(Skills.SKILLS.FLETCHING) < 70) {// yew short
            bank(ItemID.LOG_IDS[4]);

        } else if (Skills.getActualLevel(Skills.SKILLS.FLETCHING) < 75) {// yew long
            bank(ItemID.LOG_IDS[4]);
        }
        ;
    }

    @Override
    public String taskName() {
        return "Fletching";
    }
}
