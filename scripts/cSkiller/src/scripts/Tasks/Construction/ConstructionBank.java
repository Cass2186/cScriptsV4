package scripts.Tasks.Construction;

import org.tribot.api.General;
import org.tribot.api2007.Equipment;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSItem;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.MyPlayer;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.tasks.Amount;
import org.tribot.script.sdk.tasks.BankTask;
import org.tribot.script.sdk.tasks.EquipmentReq;
import org.tribot.script.sdk.types.WorldTile;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.BankManager;
import scripts.Data.SkillBank;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.ItemID;
import scripts.Requirements.ItemReq;
import scripts.Requirements.ItemRequirement;
import scripts.Tasks.MiscTasks.BuyItems;
import scripts.Timer;
import scripts.Utils;

import java.util.List;

public class ConstructionBank implements Task {


    ItemRequirement hammer = new ItemRequirement(ItemID.HAMMER);
    ItemRequirement saw = new ItemRequirement(ItemID.SAW);
    ItemRequirement coins = new ItemRequirement(ItemID.COINS_995, 100000, 1000);

    public static boolean checkEquippedWealth() {
        if (!Equipment.isEquipped(ItemID.RING_OF_WEALTH)) {
            General.println("[Construction Training]: Getting Ring of Wealth");
            BankManager.withdrawArray(ItemID.RING_OF_WEALTH, 1);
            return Utils.equipItem(ItemID.RING_OF_WEALTH);
        }
        return Equipment.isEquipped(ItemID.RING_OF_WEALTH);
    }



    public void getItems() {
        BankManager.open(true);
        BankManager.depositAll(true);

        //gets 100-150k, rounded in 10k increments
        int coins = Utils.roundToNearest((General.randomDouble(1.0, 1.5) * 100000), 10000);
        BankManager.withdraw(coins, true, ItemID.COINS);

        List<ItemReq>   newInv = SkillBank.withdraw(FURNITURE.getRequiredItemList());
        if (newInv != null && newInv.size() > 0) {
            for (ItemReq r : newInv){
                Log.warn("Missing " + r.getId() + " x " + r.getAmount());
            }
            General.println("[Construction Training]: Creating buy list");
            BuyItems.itemsToBuy =     BuyItems.populateBuyList(FURNITURE.getRequiredItemList());
            return;
        }

        if (!Equipment.isEquipped(ItemID.RING_OF_WEALTH)) {
            BankManager.withdrawArray(ItemID.RING_OF_WEALTH, 1);
            Utils.equipItem(ItemID.RING_OF_WEALTH);
        }
        //get a random amount of coins b/w 100-200k, to the nearest 10k


        BankManager.close(true);
        RSItem[] tele = Inventory.find(ItemID.TELEPORT_TO_HOUSE);
        WorldTile myTile = MyPlayer.getTile();
        if (tele.length > 0 && tele[0].click("Outside")) {
            Waiting.waitUntil(4500, 400, ()-> !MyPlayer.getTile().equals(myTile));
        }

    }

    @Override
    public Priority priority() {
        return Priority.HIGH;
    }

    @Override
    public boolean validate() {
        int item = Query.inventory().nameContains("plank").count();
        int noted = Query.inventory().nameContains("plank").isNoted().count();
        return Vars.get().currentTask != null && Vars.get().currentTask.equals(SkillTasks.CONSTRUCTION) &&
                ((item < 5 && noted == 0 && Utils.getVarBitValue(2187) != 0) ||
                !hammer.check() || !saw.check() || !coins.check());
    }

    @Override
    public void execute() {
        checkEquippedWealth();
        getItems();
    }

    @Override
    public String toString() {
        return "Banking";
    }

    @Override
    public String taskName() {
        return "Construction";
    }
}
