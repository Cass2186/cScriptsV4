package scripts.Nodes;

import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Skills;
import scripts.BankManager;

import scripts.Data.Const;
import scripts.Data.Vars;

import scripts.ItemId;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;

import scripts.Utils;

public class Restock implements Task {

// will need to 'open' all nests before selling them and seeds
    // enchant new necklaces if needed

    public void restock() {
     /*   GEManager.goToGE();
        GEManager.getCoins();

        GEManager.buyItem(Const.get().VARROCK_TELEPORT, 40, 25);
        if (Skills.getActualLevel(Skills.SKILLS.HUNTER) < 30) {
            GEManager.buyItem(Const.get().CLOCKWORK, 100, 25);
            GEManager.buyItem(Const.get().CHISEL, 500, 25);
            if (Skills.getActualLevel(Skills.SKILLS.HUNTER) > 15) {
                GEManager.buyItem(Const.get().OAK_LOGS, 100, 25);
            } else {
                GEManager.buyItem(Const.get().LOGS, 100, 25);
            }
        } else {
            GEManager.buyItem(Vars.get().currentBirdHouseId, 100, 25);
        }
*/

    }


    public void makeBirdHouse(int logId) {
        BankManager.open(true);
        BankManager.depositAllExcept(true, Const.get().CHISEL, Const.get().HAMMER);
        if (!BankManager.checkInventoryItems(Const.get().CHISEL))
            BankManager.withdraw(1, true, Const.get().CHISEL);
        if (!BankManager.checkInventoryItems(Const.get().HAMMER))
            BankManager.withdraw(1, true, Const.get().HAMMER);
    }


    public void enchantNecklaces() {
        if (Inventory.find(Const.get().RUBY_NECKLACE).length == 0) {
            BankManager.open(true);
            if (Banking.find(Const.get().DIGSITE_PENDANT).length == 0) {
                BankManager.depositAll(true);
                BankManager.withdraw(1, true, ItemId.STAFF_OF_FIRE);
                Utils.equipItem(ItemId.STAFF_OF_FIRE);
                BankManager.withdraw(100, true, Const.get().COSMIC_RUNE);
                BankManager.withdraw(0, true, Const.get().RUBY_NECKLACE);
                BankManager.close(true);
            }
        }
        else{
           // MagicUtils.castEnchant(Const.get().RUBY_NECKLACE, ItemId.STAFF_OF_FIRE, "Lvl-3 Enchant");
        }
    }

    @Override
    public void execute() {

        // restock
        Vars.get().shouldRestock = false;
    }

    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return Vars.get().shouldRestock;
    }
}
