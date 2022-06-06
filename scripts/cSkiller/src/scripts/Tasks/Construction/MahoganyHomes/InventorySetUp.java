package scripts.Tasks.Construction.MahoganyHomes;

import scripts.API.Priority;
import scripts.API.Task;
import scripts.ItemID;
import scripts.Requirements.InventoryRequirement;
import scripts.Requirements.ItemReq;
import scripts.Tasks.Fishing.FishingData.FishingConst;
import scripts.Utils;

import java.util.ArrayList;
import java.util.Arrays;

public class InventorySetUp implements Task {


    public static InventoryRequirement getInvRequirement(int plankId){
        return new  InventoryRequirement(new ArrayList<>(
                Arrays.asList(
                        new ItemReq(ItemID.HAMMER, 1),
                        new ItemReq(ItemID.SAW, 1),
                        new ItemReq(ItemID.VARROCK_TELEPORT, 100,5),
                        new ItemReq(ItemID.ARDOUGNE_TELEPORT, 100,5),
                        new ItemReq(ItemID.FALADOR_TELEPORT, 100,5),
                        new ItemReq(ItemID.STEEL_BAR, 2,1),
                        new ItemReq(plankId, 16,2)
                )));
    }

    @Override
    public Priority priority() {
        return null;
    }

    @Override
    public boolean validate() {
        return false;
    }

    @Override
    public void execute() {

    }

    @Override
    public String taskName() {
        return null;
    }
}
