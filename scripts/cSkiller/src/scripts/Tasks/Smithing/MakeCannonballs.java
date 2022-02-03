package scripts.Tasks.Smithing;

import org.tribot.api.General;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.MakeScreen;
import org.tribot.script.sdk.Waiting;
import scripts.*;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.SkillBank;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.EntitySelector.Entities;
import scripts.EntitySelector.finders.prefabs.ObjectEntity;
import scripts.Requirements.ItemReq;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MakeCannonballs implements Task {

    public void bankForItems() {
        if (Inventory.find(4).length == 0 || Inventory.find(ItemID.STEEL_BAR).length == 0) {

            List<ItemReq> inv = new ArrayList<>(
                    Arrays.asList(
                            new ItemReq(4, 1),
                            new ItemReq(ItemID.STEEL_BAR, 0)
                    )
            );

            BankManager.open(true);
            BankManager.depositAllExcept(false, 4);
            Timer.waitCondition(() -> Inventory.getAll().length == 1, 2500, 4000);

            List<ItemReq> newInv = SkillBank.withdraw(inv);
            if (newInv != null && newInv.size() > 0) {
                General.println("[Smithing Training]: Creating buy list");
                //TODO FIX THIS
                //  BuyItems.itemsToBuy = BuyItems.populateBuyList(HerbloreItems.getRequiredItemList());
                return;
            }
            BankManager.close(true);
        } else smelt();
    }

    public void smelt() {
        RSObject furnace = Entities.find(ObjectEntity::new)
                .actionsContains("Smelt")
                .sortByDistance()
                .getFirstResult();

        if (furnace != null) {
            BankManager.close(true);
            if (!furnace.isClickable())
                PathingUtil.walkToTile(new RSTile(3109, 3499, 0));

            if (Utils.clickObj(furnace.getID(), "Smelt")) {
                Timer.waitCondition(MakeScreen::isOpen, 5500, 7500);
            }

            if (MakeScreen.isOpen()) {
                MakeScreen.makeAll(ItemID.CANNONBALL);
                Timer.skillingWaitCondition(() -> {
                            Waiting.waitNormal(250, 45);
                            AntiBan.timedActions();
                            return Inventory.find(ItemID.STEEL_BAR).length == 0;
                        },
                        135000, 145000);
                int i =   General.randomSD(3500, 650);
                Utils.idle(i,i);
            }


        }
    }


    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return Vars.get().currentTask != null && Vars.get().currentTask.equals(SkillTasks.SMITHING)
                && Skills.SKILLS.SMITHING.getActualLevel() >= 35 && Vars.get().smeltCannonballs;
    }

    @Override
    public void execute() {
        bankForItems();
    }

    @Override
    public String toString() {
        return "Cannonballs";
    }

    @Override
    public String taskName() {
        return "Smithing";
    }
}
