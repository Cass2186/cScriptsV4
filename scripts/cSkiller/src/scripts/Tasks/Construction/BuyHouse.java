package scripts.Tasks.Construction;

import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.Equipment;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.tasks.Amount;
import org.tribot.script.sdk.tasks.BankTask;
import org.tribot.script.sdk.tasks.EquipmentReq;
import org.tribot.script.sdk.types.EquipmentItem;
import scripts.*;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.SkillBank;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.QuestSteps.NPCStep;
import scripts.Requirements.ItemReq;
import scripts.Requirements.ItemRequirement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BuyHouse implements Task {

    int houseVarbit1 = 2187;
    int houseVarbit2 = 2188;

    RSArea VARROCK_AGENT_AREA = new RSArea(new RSTile(3242, 3473, 0), new RSTile(3238, 3477, 0));
    int ESTATE_AGENT_ID = 3097;

    NPCStep getHouse = new NPCStep(ESTATE_AGENT_ID, VARROCK_AGENT_AREA.getRandomTile(), new String[]{
            "How can I get a house?", "Yes please!"});

    ItemReq coins = new ItemReq(ItemId.COINS, 1000, 1000);


    BankTask invTask = BankTask.builder().addInvItem(ItemId.COINS, Amount.of(1000))
            .addEquipmentItem(EquipmentReq.slot(org.tribot.script.sdk.Equipment.Slot.RING)
                    .chargedItem("Ring of wealth", 1))
            .build();

    List<ItemReq> inv = new ArrayList<>(List.of(coins));

    public void buyHouse() {
        if (!coins.check()) {
            Log.log("[Debug]: Getting coins");
            invTask.execute();
        } else
            getHouse.execute();
    }


    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        return Vars.get().currentTask != null &&
                Vars.get().currentTask.equals(SkillTasks.CONSTRUCTION) &&
                Utils.getVarBitValue(houseVarbit1) == 0;
    }

    @Override
    public void execute() {
        buyHouse();
    }

    @Override
    public String toString() {
        return "Buying house";
    }


    @Override
    public String taskName() {
        return "Construction";
    }
}
