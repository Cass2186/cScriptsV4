package scripts.Tasks.Herblore;

import org.tribot.api.General;
import org.tribot.api2007.Equipment;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSItem;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.BankManager;
import scripts.Data.Enums.HerbloreItems;
import scripts.Data.SkillBank;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.EntitySelector.Entities;
import scripts.EntitySelector.finders.prefabs.ItemEntity;
import scripts.ItemId;
import scripts.Requirements.InventoryRequirement;
import scripts.Requirements.ItemReq;
import scripts.Tasks.MiscTasks.BuyItems;
import scripts.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


public class HerbloreBank implements Task {


    InventoryRequirement startInventory = new InventoryRequirement(new ArrayList<>(
            Arrays.asList(
                    new ItemReq(ItemId.STEEL_NAILS, 60),
                    new ItemReq(ItemId.PLANK, 2)
            ))
    );

    public void getItemsAmuletOfChemistry(int item1, int item2) {
        if (Inventory.find(item1).length < 1 || Inventory.find(item2).length < 1) {

            List<ItemReq> inv = new ArrayList<>(
                    Arrays.asList(
                            new ItemReq(item1, 14),
                            new ItemReq(item2, 14)
                    )
            );

            BankManager.open(true);
            BankManager.depositAll(true);

            if (Equipment.find(ItemId.AMULET_OF_CHEMISTRY).length < 1) {
                General.println("[Herblore Training]: Getting amulet of Chemistry");
                BankManager.withdraw(1, true, ItemId.AMULET_OF_CHEMISTRY);
                Utils.equipItem(ItemId.AMULET_OF_CHEMISTRY);
            }
            List<ItemReq>   newInv = SkillBank.withdraw(inv);
            if (newInv != null && newInv.size() > 0) {
                General.println("[Herblore Training]: Creating buy list");
                BuyItems.itemsToBuy =     BuyItems.populateBuyList(HerbloreItems.getRequiredItemList());
                return;
            }
            BankManager.close(true);
        }
    }


    public void progressive() {
        int currentLevel = Skills.getCurrentLevel(Skills.SKILLS.HERBLORE);
        if (Skills.getActualLevel(Skills.SKILLS.HERBLORE) >= SkillTasks.HERBLORE.getEndLevel()) {
            General.println("[Debug]: Achieved target level of: " + SkillTasks.HERBLORE.getEndLevel());
            Vars.get().currentTask = null;
            //cSkiller.changeRunningBool(false);
            return;
        }
        Optional<HerbloreItems> itemOptional = HerbloreItems.getCurrentItem();
        itemOptional.ifPresent(itm -> getItemsAmuletOfChemistry(itm.getItemId(), itm.getUnfPotionId()));
    }

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
        RSItem unf = Entities.find(ItemEntity::new) //check if noted
                .nameContains("unf")
                .getFirstResult();

        RSItem itemId = Entities.find(ItemEntity::new) //check if noted
                .nameContains("    ")
                .getFirstResult();

        Optional<HerbloreItems> item = HerbloreItems.getCurrentItem();
        if (item.isPresent()){

      //      Log.log("Using itemId of " + item.get().toString());
            itemId = Entities.find(ItemEntity::new) //check if noted
                    .idEquals(item.get().getItemId())
                    .getFirstResult();
        //    Log.log("Is itemId null?  " + (itemId == null));
        }
        if (item.isPresent()){
            unf = Entities.find(ItemEntity::new) //check if noted
                    .idEquals(item.get().getUnfPotionId())
                    .getFirstResult();
        }
        boolean b = (unf == null || itemId == null);
       // Log.log("[Herblorebank] Is boolean ? " + b);
        return Vars.get().currentTask != null && Vars.get().currentTask.equals(SkillTasks.HERBLORE) &&
                (unf == null || itemId == null);
    }

    @Override
    public void execute() {
        progressive();
    }

    @Override
    public String taskName() {
        return "Herblore";
    }
}
