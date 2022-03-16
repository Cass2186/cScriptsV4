package scripts.Tasks.BirdHouseRuns.Nodes;

import org.tribot.api.General;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.Skills;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.Log;
import scripts.BankManager;

import scripts.ItemID;
import scripts.Requirements.ItemReq;
import scripts.Tasks.BirdHouseRuns.Data.Birdhouse;
import scripts.Tasks.BirdHouseRuns.Data.Const;

import scripts.Tasks.BirdHouseRuns.Data.BirdHouseVars;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;
import scripts.Timer;

import java.util.List;


public class Bank implements Task {

    private int determineBirdHouse() {
        if (Skills.getCurrentLevel(Skills.SKILLS.HUNTER) < 14)
            return BirdHouseVars.get().currentBirdHouseId = Const.BIRDHOUSE_ID;

        else if (Skills.getCurrentLevel(Skills.SKILLS.HUNTER) < 24)
            return BirdHouseVars.get().currentBirdHouseId = Const.OAK_BIRDHOUSE_ID;

        else if (Skills.getCurrentLevel(Skills.SKILLS.HUNTER) < 34)
            return BirdHouseVars.get().currentBirdHouseId = Const.WILLOW_BIRDHOUSE_ID;

        else if (Skills.getCurrentLevel(Skills.SKILLS.HUNTER) < 44)
            return BirdHouseVars.get().currentBirdHouseId = Const.TEAK_BIRDHOUSE_ID;

        else if (Skills.getCurrentLevel(Skills.SKILLS.HUNTER) < 49)
            return BirdHouseVars.get().currentBirdHouseId = Const.MAPLE_BIRDHOUSE_ID;

        else if (Skills.getCurrentLevel(Skills.SKILLS.HUNTER) < 59)
            return BirdHouseVars.get().currentBirdHouseId = Const.MAHOGANY_BIRDHOUSE_ID;

        else if (Skills.getCurrentLevel(Skills.SKILLS.HUNTER) < 74)
            return BirdHouseVars.get().currentBirdHouseId = Const.YEW_BIRDHOUSE_ID;

        else if (Skills.getCurrentLevel(Skills.SKILLS.HUNTER) < 89)
            return BirdHouseVars.get().currentBirdHouseId = Const.MAGIC_BIRDHOUSE_ID;

        else if (Skills.getCurrentLevel(Skills.SKILLS.HUNTER) <= 100)
            return BirdHouseVars.get().currentBirdHouseId = Const.REDWOOD_BIRDHOUSE_ID;

        else
            return BirdHouseVars.get().currentBirdHouseId = Const.BIRDHOUSE_ID;
    }

    private void goToVarrockWest(){
        RSItem[] varrockTab = Inventory.find(Const.VARROCK_TELEPORT);
        RSObject[] bank = Objects.findNearest(20, Filters.Objects.nameContains("Bank"));
        if (varrockTab.length >0 && bank.length == 0) {
            Log.debug("Going to Varrock West Bank");
            RSTile currentTile = Player.getPosition();
            if (varrockTab[0].click("Break")) {
                Timer.waitCondition(() -> !Player.getPosition().equals(currentTile), 12000, 20000);
            }
        }
    }

    private void getItems() {
        if (!BankManager.checkInventoryItems(Const.VARROCK_TELEPORT,
                BirdHouseVars.get().currentBirdHouseId, Const.BARLEY_SEED_ID)) {

            determineBirdHouse();
            goToVarrockWest();
            List<ItemReq> list = List.of(
                    new ItemReq(ItemID.VARROCK_TELEPORT, 2),
                    new ItemReq(BirdHouseVars.get().currentBirdHouseId, 4, 4),
                    new ItemReq(Const.BARLEY_SEED_ID, 500,40),
                    new ItemReq(ItemID.STAMINA_POTION, 1),
                   new ItemReq.Builder()
                           .idEquals(Const.DIGSITE_PENDANT[0])
                           .addAlternateIds(Const.DIGSITE_PENDANT)
                            .minAmount(1)
                            .chargesNeeded(1)
                            .build()
            );
         //   BankManager.withdrawList(list);
            BankManager.open(true);
            BankManager.depositAll(true);
            BankManager.withdraw(2,true, Const.VARROCK_TELEPORT);
            BankManager.withdraw(4, true, BirdHouseVars.get().currentBirdHouseId);
            if (Inventory.find(BirdHouseVars.get().currentBirdHouseId).length < 4){
                General.println("[Bank]: We need to get more birdhouses");
                BirdHouseVars.get().shouldRestock = true;
                return;
            }
            BankManager.withdraw(1, true, Const.DIGSITE_PENDANT);
            BankManager.withdraw(0, true, Const.BARLEY_SEED_ID);
            BankManager.withdrawArray(ItemID.STAMINA_POTION,1);
           BankManager.close(true);
        }
        if (!BankManager.checkInventoryItems(Const.VARROCK_TELEPORT,
                BirdHouseVars.get().currentBirdHouseId, Const.BARLEY_SEED_ID)) {
            BirdHouseVars.get().shouldRestock = true;
        }
    }



    @Override
    public void execute() {
        Log.debug("Banking");
        getItems();
        BirdHouseVars.get().shouldBank = false;
    }

    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return BirdHouseVars.get().shouldBank;
    }
}
