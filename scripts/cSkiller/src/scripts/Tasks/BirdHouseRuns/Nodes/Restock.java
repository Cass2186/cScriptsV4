package scripts.Tasks.BirdHouseRuns.Nodes;

import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import scripts.BankManager;

import scripts.Data.Vars;
import scripts.GEManager.GEItem;
import scripts.ItemID;
import scripts.QuestSteps.BuyItemsStep;
import scripts.Tasks.BirdHouseRuns.Data.BirdHouseVars;
import scripts.Tasks.BirdHouseRuns.Data.Birdhouse;
import scripts.Tasks.BirdHouseRuns.Data.Const;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;
import scripts.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class Restock implements Task {

// will need to 'open' all nests before selling them and seeds
    // enchant new necklaces if needed

    ArrayList<GEItem> itemsToBuyInitial = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.LOGS, 1, 50),
                    new GEItem(ItemID.OAK_LOGS, 1, 50),
                    new GEItem(ItemID.WILLOW_LOGS, 1, 50),
                    new GEItem(ItemID.STAMINA_POTION[0], 2, 20),
                    new GEItem(ItemID.MAPLE_LOGS, 1, 50),
                    new GEItem(ItemID.TEAK_LOGS, 1, 30),
                    new GEItem(ItemID.CLOCKWORK, 13, 200),
                    new GEItem(ItemID.CHISEL, 1, 200),
                    new GEItem(ItemID.VARROCK_TELEPORT, 10, 50),
                    new GEItem(ItemID.HAMMER, 1, 200)

            )
    );

    ArrayList<GEItem> restockItems = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.STAMINA_POTION[0], 1, 20),
                    new GEItem(ItemID.BARLEY_SEED, 500, 200),
                    new GEItem(ItemID.VARROCK_TELEPORT, 10, 50)
                    //ruby necklace?
            )
    );

    BuyItemsStep buyStep = new BuyItemsStep(itemsToBuyInitial);


    public void restock() {
        Optional<Birdhouse> bestHouse = Birdhouse.getBestBirdhouse();

        // if we can craft the best birdhouse, add the logs, clockwork and equipment
        if (bestHouse.map(Birdhouse::canCraft).orElse(false)) {
            restockItems.add(new GEItem(bestHouse.get().getLogID(), 8, 50));
            restockItems.add(new GEItem(ItemID.CLOCKWORK, 8, 50));
            restockItems.add(new GEItem(ItemID.CHISEL, 1, 200));
            restockItems.add(new GEItem(ItemID.HAMMER, 1, 200));
        } else {
            // add the birdhouse to the purchase list if we can't craft it
            bestHouse.ifPresent(b -> restockItems
                    .add(new GEItem(b.getBirdhouseID(), 12, 50)));
        }

        BuyItemsStep buyStep = new BuyItemsStep(restockItems);
        buyStep.buyItems();

    }


    public void makeBirdHouse(int logId) {
        BankManager.open(true);
        BankManager.depositAllExcept(true, Const.CHISEL, Const.HAMMER);
        if (!BankManager.checkInventoryItems(Const.CHISEL))
            BankManager.withdraw(1, true, Const.CHISEL);
        if (!BankManager.checkInventoryItems(Const.HAMMER))
            BankManager.withdraw(1, true, Const.HAMMER);
    }


    public void enchantNecklaces() {
        if (Inventory.find(Const.RUBY_NECKLACE).length == 0) {
            BankManager.open(true);
            if (Banking.find(Const.DIGSITE_PENDANT).length == 0) {
                BankManager.depositAll(true);
                BankManager.withdraw(1, true, ItemID.STAFF_OF_FIRE);
                Utils.equipItem(ItemID.STAFF_OF_FIRE);
                BankManager.withdraw(100, true, ItemID.COSMIC_RUNE);
                BankManager.withdraw(0, true, Const.RUBY_NECKLACE);
                BankManager.close(true);
            }
        } else {
            // MagicUtils.castEnchant(Const.RUBY_NECKLACE, ItemID.STAFF_OF_FIRE, "Lvl-3 Enchant");
        }
    }

    @Override
    public void execute() {

        // restock
        BirdHouseVars.get().shouldRestock = false;
    }

    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return BirdHouseVars.get().shouldRestock;
    }
}
