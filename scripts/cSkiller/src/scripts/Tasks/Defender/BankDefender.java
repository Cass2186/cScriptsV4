package scripts.Tasks.Defender;

import obf.I;
import org.checkerframework.checker.units.qual.A;
import org.tribot.script.sdk.Inventory;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.ItemID;
import scripts.Requirements.InventoryRequirement;
import scripts.Requirements.ItemReq;

import java.util.ArrayList;
import java.util.List;

public class BankDefender implements Task {


    public static InventoryRequirement getTokenFarmingInventory(int foodId){
        InventoryRequirement req = new InventoryRequirement(new ArrayList<>(
                List.of(new ItemReq(ItemID.BLACK_FULL_HELM, 1),
                        new ItemReq(ItemID.BLACK_PLATELEGS, 1),
                        new ItemReq(ItemID.BLACK_PLATEBODY ,1),
                        new ItemReq(ItemID.SUPER_COMBAT_POTION4, 2, 0),
                        new ItemReq(ItemID.WARRIOR_GUILD_TOKEN, 0, 0),
                        new ItemReq(foodId, 0, 1)
                        )
        ));
        return req;
    }

    public static InventoryRequirement getDefenderInventory(int foodId){
        InventoryRequirement req = new InventoryRequirement(new ArrayList<>(
                List.of(new ItemReq(ItemID.SUPER_COMBAT_POTION4, 2, 0),
                        new ItemReq(ItemID.WARRIOR_GUILD_TOKEN, 0, 10),
                        new ItemReq(foodId, 0, 5)
                )
        ));
        return req;
    }

    @Override
    public Priority priority() {
        return Priority.HIGH;
    }

    @Override
    public boolean validate() {
        if (!Vars.get().getDefenders ||
                Vars.get().currentTask == null ||
                !Vars.get().currentTask.equals(SkillTasks.DEFENDERS))
            return false;

        return Vars.get().getDefenders &&
                !Inventory.contains(ItemID.MONKFISH);
    }

    @Override
    public void execute() {
        getTokenFarmingInventory(ItemID.MONKFISH).withdrawItems();
    }

    @Override
    public String toString() {
        return "Banking";
    }
    @Override
    public String taskName() {
        return "Defender";
    }
}
