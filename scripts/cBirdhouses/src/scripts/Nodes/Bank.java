package scripts.Nodes;

import org.tribot.api.General;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.Skills;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import scripts.BankManager;

import scripts.Data.Const;
import scripts.Data.Vars;

import scripts.ItemID;
import scripts.Requirements.ItemReq;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;
import scripts.Timer;

import java.util.List;


public class Bank implements Task {

    private int determineBirdHouse() {

        if (Skills.getCurrentLevel(Skills.SKILLS.HUNTER) < 14)
            return Vars.get().currentBirdHouseId = Const.get().BIRDHOUSE_ID;

        else if (Skills.getCurrentLevel(Skills.SKILLS.HUNTER) < 24)
            return Vars.get().currentBirdHouseId = Const.get().OAK_BIRDHOUSE_ID;

        else if (Skills.getCurrentLevel(Skills.SKILLS.HUNTER) < 34)
            return Vars.get().currentBirdHouseId = Const.get().WILLOW_BIRDHOUSE_ID;

        else if (Skills.getCurrentLevel(Skills.SKILLS.HUNTER) < 44)
            return Vars.get().currentBirdHouseId = Const.get().TEAK_BIRDHOUSE_ID;

        else if (Skills.getCurrentLevel(Skills.SKILLS.HUNTER) < 49)
            return Vars.get().currentBirdHouseId = Const.get().MAPLE_BIRDHOUSE_ID;

        else if (Skills.getCurrentLevel(Skills.SKILLS.HUNTER) < 59)
            return Vars.get().currentBirdHouseId = Const.get().MAHOGANY_BIRDHOUSE_ID;

        else if (Skills.getCurrentLevel(Skills.SKILLS.HUNTER) < 74)
            return Vars.get().currentBirdHouseId = Const.get().YEW_BIRDHOUSE_ID;

        else if (Skills.getCurrentLevel(Skills.SKILLS.HUNTER) < 89)
            return Vars.get().currentBirdHouseId = Const.get().MAGIC_BIRDHOUSE_ID;

        else if (Skills.getCurrentLevel(Skills.SKILLS.HUNTER) <= 100)
            return Vars.get().currentBirdHouseId = Const.get().REDWOOD_BIRDHOUSE_ID;

        else
            return Vars.get().currentBirdHouseId = Const.get().BIRDHOUSE_ID;
    }

    private void goToVarrockWest(){
        RSItem[] varrockTab = Inventory.find(Const.get().VARROCK_TELEPORT);
        RSObject[] bank = Objects.findNearest(20, Filters.Objects.nameContains("Bank"));
        if (varrockTab.length >0 && bank.length == 0) {
            Vars.get().status = "Going to Varrock West Bank";
            RSTile currentTile = Player.getPosition();
            if (varrockTab[0].click("Break")) {
                Timer.waitCondition(() -> !Player.getPosition().equals(currentTile), 12000, 20000);
            }
        }
    }

    private void getItems() {
        if (!BankManager.checkInventoryItems(Const.get().VARROCK_TELEPORT,
                Vars.get().currentBirdHouseId, Const.get().BARLEY_SEED_ID)) {

            determineBirdHouse();
            goToVarrockWest();
            List<ItemReq> list = List.of(
                    new ItemReq(ItemID.VARROCK_TELEPORT, 2),
                    new ItemReq(Vars.get().currentBirdHouseId, 4, 4),
                    new ItemReq(Const.get().BARLEY_SEED_ID, 500,40),
                    new ItemReq(ItemID.STAMINA_POTION, 1),
                   new ItemReq.Builder()
                           .id(Const.get().DIGSITE_PENDANT[0])
                           //.alternateItemIDs(Const.get().DIGSITE_PENDANT[1])
                            .minAmount(1)
                           // .chargesNeeded(1)
                            .build()
            );
         //   BankManager.withdrawList(list);
            BankManager.open(true);
            BankManager.depositAll(true);
            BankManager.withdraw(2,true, Const.get().VARROCK_TELEPORT);
            BankManager.withdraw(4, true, Vars.get().currentBirdHouseId);
            if (Inventory.find(Vars.get().currentBirdHouseId).length < 4){
                General.println("[Bank]: We need to get more birdhouses");
                Vars.get().shouldRestock = true;
                return;
            }
            BankManager.withdraw(1, true, Const.get().DIGSITE_PENDANT);
            BankManager.withdraw(0, true, Const.get().BARLEY_SEED_ID);
            BankManager.withdraw(1, true, Const.get().STAMINA_POTION);
           BankManager.close(true);
        }
        if (!BankManager.checkInventoryItems(Const.get().VARROCK_TELEPORT,
                Vars.get().currentBirdHouseId, Const.get().BARLEY_SEED_ID)) {
            Vars.get().shouldRestock = true;
        }
    }

    private void makeBirdHouses(){
        Vars.get().status = "Making Bird houses.";
    }


    @Override
    public void execute() {
        makeBirdHouses();
        Vars.get().status = "Banking.";
        getItems();
        Vars.get().shouldBank = false;
    }

    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return Vars.get().shouldBank;
    }
}
