package scripts.Nodes;

import org.tribot.api.General;
import org.tribot.api.input.Keyboard;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSItem;
import scripts.BankManager;

import scripts.Data.Birdhouse;
import scripts.Data.Const;

import scripts.Tasks.Priority;
import scripts.Tasks.Task;
import scripts.Timer;

import scripts.Utils;

import java.util.Optional;

public class MakeBirdHouses implements Task {

    public boolean makeBirdHouse(int logId) {
        RSItem[] chisel = Inventory.find(Const.get().CHISEL);
        RSItem[] hammer = Inventory.find(Const.get().HAMMER);
        if (chisel.length > 0 && hammer.length > 0) {
            if (Utils.useItemOnItem(logId, Const.get().CLOCKWORK)) {
                Timer.waitCondition(() -> (Interfaces.get(270, 14) != null), 3000, 5000);
                if (Interfaces.get(270, 14) != null) {
                    General.sleep(General.randomSD(50, 700, 300, 100));
                    Keyboard.typeString(" ");
                    return Timer.abc2SkillingWaitCondition(() -> Inventory.find(logId).length < 1, 30000, 45000);
                }
            }
        }
        return false;
    }

    public void bank(int logId) {
        if (!makeBirdHouse(logId)) {
            General.println("[Debug]: Banking for items");
            BankManager.open(true);
            BankManager.depositAllExcept(Const.get().CHISEL, Const.get().HAMMER);

            if (Inventory.find(Const.get().CHISEL).length == 0)
                BankManager.withdraw(1, true, Const.get().CHISEL);

            if (Inventory.find(Const.get().HAMMER).length == 0)
                BankManager.withdraw(1, true, Const.get().HAMMER);

            BankManager.withdraw(13, true, logId);
            BankManager.withdraw(13, true, Const.get().CLOCKWORK);
            BankManager.close(true);
        }
    }

    @Override
    public void execute() {
        Optional<Birdhouse> best = Birdhouse.getBestCraftableBirdhouse();
        best.ifPresent(b->bank(b.getLogID()));

    }

    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return false;
    }
}
