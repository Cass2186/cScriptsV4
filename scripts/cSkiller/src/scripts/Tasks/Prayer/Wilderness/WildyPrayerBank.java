package scripts.Tasks.Prayer.Wilderness;

import obf.M;
import org.tribot.api2007.Combat;
import org.tribot.script.sdk.*;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.EquipmentItem;
import org.tribot.script.sdk.types.GameObject;
import org.tribot.script.sdk.types.GroundItem;
import org.tribot.script.sdk.types.InventoryItem;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.BankManager;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.ItemID;
import scripts.Tasks.BirdHouseRuns.Nodes.Wait;
import scripts.Utils;

import java.util.List;
import java.util.Optional;

public class WildyPrayerBank implements Task {

    private void suicideWine() {
        if (Combat.isInWilderness()) {
            Utils.idleNormalAction(true);
            Optional<GroundItem> wine = Query.groundItems()
                    .actionContains("Take")
                    .nameContains("Wine").findBestInteractable();

            for (int i = 0; i < 200; i++) {
                int health = MyPlayer.getCurrentHealth();
                if (health == 0)
                    break;

                if (wine.map(w -> w.interact("Take")).orElse(false)) {
                    if (i == 0) //first click
                        Waiting.waitUntil(2000, 35, () ->
                                MyPlayer.getCurrentHealth() < health);
                    else
                        Waiting.waitNormal(235, 45);
                }
            }
        }
    }


    private void bank() {
        if (!Combat.isInWilderness()) {
            Optional<InventoryItem> amulet = Query.inventory().nameContains("Burning amulet").findFirst();
            if (amulet.map(a -> Utils.equipItem(a.getId())).orElse(false)) {
                Utils.idleNormalAction();
            }
            List<EquipmentItem> equipList = Query.equipment().nameNotContains("Burning amulet").toList();
            if (equipList.size() > 0)
                BankManager.depositEquipment();
            BankManager.depositAll(true);
            if (!Query.equipment()
                    .nameContains("Burning amulet").isAny()) {
                BankManager.withdrawArray(ItemID.BURNING_AMULET, 1);
               amulet = Query.inventory().nameContains("Burning amulet").findFirst();
                if (amulet.map(a -> Utils.equipItem(a.getId())).orElse(false)) {
                    Utils.idleNormalAction();
                }
            }
            BankManager.withdraw(0, true, ItemID.DRAGON_BONES);
            if (!Inventory.contains(ItemID.DRAGON_BONES) && (Bank.isOpen() || Bank.isNearby())) {
                throw new NullPointerException();
            }
        }
        BankManager.close(true);

    }

    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        Optional<InventoryItem> bones = Query.inventory()
                .nameContains("bones").isNotNoted().findFirst();

        return Vars.get().currentTask != null &&
                Vars.get().currentTask.equals(SkillTasks.PRAYER) &&
                Vars.get().useWildernessAltar &&
                (bones.isEmpty() ||
                        (!Combat.isInWilderness() &&
                                Inventory.getCount(ItemID.DRAGON_BONES) < 5));
    }

    @Override
    public void execute() {
        suicideWine();
        bank();
    }

    @Override
    public String toString() {
        return "Suiciding & Banking";
    }

    @Override
    public String taskName() {
        return "Prayer - Wildneress";
    }
}
