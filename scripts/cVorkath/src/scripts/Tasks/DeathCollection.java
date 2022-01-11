package scripts.Tasks;

import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api2007.*;
import org.tribot.api2007.types.*;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Waiting;
import scripts.*;
import scripts.EntitySelector.Entities;
import scripts.EntitySelector.finders.prefabs.ItemEntity;
import scripts.EntitySelector.finders.prefabs.ObjectEntity;
import scripts.VorkUtils.Vars;
import scripts.VorkUtils.VorkthUtil;

import java.util.Optional;

public class DeathCollection implements Task {

    //withdraw a home tele
    RSArea LUMBRIDGE = new RSArea(new RSTile(3217, 3227, 0), new RSTile(3225, 3212, 0));
    RSArea LUMBRIDGE_BANK = new RSArea(new RSTile(3209, 3218, 2), new RSTile(3207, 3216, 2));

    public void getHouseTele() {
        if (LUMBRIDGE.contains(Player.getPosition())) {
            Log.log("[Debug]: Getting house tab");
            PathingUtil.walkToArea(LUMBRIDGE_BANK, false);
            BankManager.open(true);
            BankManager.withdraw(1, true, ItemId.TELEPORT_TO_HOUSE);
            BankManager.close(true);
        }
        RSItem[] housetab = Inventory.find(ItemId.TELEPORT_TO_HOUSE);
        if (housetab.length > 0 && housetab[0].click("Break")) {
            Timer.waitCondition(Game::isInInstance, 6000);
            Waiting.waitNormal(2500, 300);
        }
        if (leaveViaPortal())
            Waiting.waitNormal(750, 125);
    }

    public boolean leaveViaPortal() {
        Optional<RSObject> p = Optional.ofNullable(Entities.find(ObjectEntity::new)
                .nameContains("Lunar Isle Portal")
                .actionsContains("Enter")
                .getFirstResult());
        return p.map(port -> {
            return port.click("Enter") &&
                    Timer.waitCondition(() -> VorkthUtil.LUNAR_ISLE_AREA.contains(Player.getPosition()),
                            8000, 10000);
        }).orElse(false);

    }

    public void fillRunePouch() {
        if (Utils.useItemOnItem(ItemId.DUST_RUNE, ItemId.RUNE_POUCH)) {
            Timer.waitCondition(() -> Inventory.find(ItemId.DUST_RUNE).length == 0, 2500, 3500);
        }
        if (Utils.useItemOnItem(ItemId.CHAOS_RUNE, ItemId.RUNE_POUCH)) {
            Timer.waitCondition(() -> Inventory.find(ItemId.CHAOS_RUNE).length == 0, 2500, 3500);
        }
        if (Utils.useItemOnItem(ItemId.LAW_RUNE, ItemId.RUNE_POUCH)) {
            Timer.waitCondition(() -> Inventory.find(ItemId.LAW_RUNE).length == 0, 2500, 3500);
        }
    }

    public void equipVoid() {
        RSItem[] wearableItems = Entities.find(ItemEntity::new)
                .actionsContains("Wear")
                .getResults();
        for (RSItem i : wearableItems) {
            if (i.click("Wear"))
                Waiting.waitNormal(400, 120);
        }
        RSItem[] wieldableItems = Entities.find(ItemEntity::new)
                .actionsContains("Wield")
                .getResults();
        for (RSItem i : wieldableItems) {
            if (i.click("Wield"))
                Waiting.waitNormal(400, 120);
        }
    }

    public void collectItems() {
        if (VorkthUtil.LUNAR_ISLE_AREA.contains(Player.getPosition())) {
            HouseBank.leaveLunarIsle();
        }
        if (PathingUtil.walkToTile(new RSTile(2640, 3697, 0), 4, false) &&
                Utils.clickNPC("Torfinn", "Collect")) {
            Timer.waitCondition(() -> Interfaces.isInterfaceSubstantiated(602), 6000, 8000);
        }
        RSInterface unlock = Interfaces.findWhereAction("Unlock", 602);
        if (unlock != null && unlock.click()) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Yes, pay 100,000 x Coins.");
            Timer.slowWaitCondition(() -> Interfaces.isInterfaceSubstantiated(602), 3000, 4000);
            Waiting.waitNormal(400, 55);
        }
        RSInterface take = Interfaces.findWhereAction("Take-All", 602);
        int invLength = Inventory.getAll().length;
        if (take != null && take.click()) {
            Timer.slowWaitCondition(() -> Inventory.getAll().length > invLength, 4500, 5500);
        }
        equipVoid();
        fillRunePouch();
    }

    @Override
    public String toString() {
        return "Collecting Death";
    }

    @Override
    public Priority priority() {
        return Priority.HIGH;
    }

    @Override
    public boolean validate() {
        return Combat.getHP() == 0 ||
                Vars.get().collectDeath || Equipment.getItems().length < 6;
    }

    @Override
    public void execute() {
        getHouseTele();
        collectItems();
    }
}
