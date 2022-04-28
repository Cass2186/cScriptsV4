package scripts.Tasks;

import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.MyPlayer;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.tasks.BankTaskError;
import org.tribot.script.sdk.types.GameObject;
import org.tribot.script.sdk.types.WorldTile;
import scripts.BankManager;
import scripts.EntitySelector.Entities;
import scripts.EntitySelector.finders.prefabs.ObjectEntity;
import scripts.ItemID;
import scripts.Requirements.InventoryRequirement;
import scripts.Requirements.ItemReq;
import scripts.Timer;
import scripts.Utils;
import scripts.VorkUtils.Vars;
import scripts.VorkUtils.VorkthUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class HouseBank implements Task {

    String teleString = "Teleport to House";
    RSTile BANKING_TILE = new RSTile(2099, 3920, 0);
    public static RSTile leaveBankBookTile = new RSTile(2098, 3920, 0);

    InventoryRequirement startInventory = new InventoryRequirement(new ArrayList<>(
            Arrays.asList(
                    new ItemReq(ItemID.STEEL_NAILS, 60),
                    new ItemReq(ItemID.PLANK, 2)
            ))
    );


    public void castHomeTele() {
        if (BANKING_TILE.distanceTo(Player.getPosition()) > 15 && !isInHouse() && Magic.selectSpell(teleString))
            Timer.waitCondition(this::isInHouse, 6000, 9000);

    }

    public boolean isInHouse() {
        Optional<RSObject> p = Optional.ofNullable(Entities.find(ObjectEntity::new)
                .nameContains("Lunar Isle Portal")
                .actionsContains("Enter")
                .getFirstResult());
        return p.isPresent() && Game.isInInstance();
    }

    public boolean restorePrayer() {
        if (Prayer.getPrayerPoints() !=
                Skills.getActualLevel(Skills.SKILLS.PRAYER)) {
            Optional<RSObject> altar = Optional.ofNullable(Entities.find(ObjectEntity::new)
                    .nameContains("Altar")
                    .actionsContains("Pray")
                    .getFirstResult());
            return altar.map(a -> {
                return Utils.clickObj(a.getID(), "Pray") &&
                        Timer.waitCondition(() -> Prayer.getPrayerPoints() ==
                                Skills.getActualLevel(Skills.SKILLS.PRAYER), 8000, 10000);
            }).orElse(false);
        }
        return Prayer.getPrayerPoints() ==
                Skills.getActualLevel(Skills.SKILLS.PRAYER);
    }

    public boolean leaveViaPortal() {
        Optional<GameObject> p = Query.gameObjects().actionContains("Enter")
                .nameContains("Lunar Isle Portal")
                .findBestInteractable();

        return p.map(port -> port.interact("Enter") &&
                Timer.waitCondition(() ->
                                VorkthUtil.LUNAR_ISLE_AREA.contains(Player.getPosition()),
                        8000, 10000)
        ).orElse(false);

    }

    public boolean openBank() {
        RSObject booth = Entities.find(ObjectEntity::new).
                tileEquals(BANKING_TILE)
                .actionsContains("Bank")
                .getFirstResult();
        if (booth != null && Utils.clickObject(booth, "Bank")) {
            return Timer.waitCondition(Banking::isBankLoaded, 7000);
        }
        return Banking.isBankLoaded();
    }

    public void handleBank() {
        if (openBank()) {
            Optional<BankTaskError> err = Vars.get().bankTask.execute();

            if (err.isPresent())
                BankManager.getItemReqFromBankError(err); //incomplete method

            BankManager.close(true);
        }
    }


    public static void leaveLunarIsle() {
        RSObject booth = Entities.find(ObjectEntity::new).
                tileEquals(leaveBankBookTile)
                .actionsContains("Bank")
                .getFirstResult();
        BankManager.close(true);
        if (booth != null && Utils.clickObject(booth, "Bank")) {
            NPCInteraction.waitForConversationWindow();
            WorldTile t = MyPlayer.getTile();
            NPCInteraction.handleConversation();
            Waiting.waitUntil(3000, 300,
                    () -> !MyPlayer.getTile().equals(t));
        }
    }

    @Override
    public String toString() {
        return "Handling house";
    }

    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        int amountFood = Inventory.getCount(Vars.get().foodId);
        int amountPrayerPot = Inventory.find(ItemID.PRAYER_POTION).length;
        int extendedAnti = Inventory.find(22209, 22212, 22215, 22218).length;

        return isInHouse() || (Vars.get().minimumFoodAmount >= amountFood ||
                amountPrayerPot == 0 ||
                (extendedAnti == 0 && !Vars.get().antiFireTimer.isRunning()) &&
                        !Vars.get().bankTask.isSatisfied());

    }

    @Override
    public void execute() {
        if (org.tribot.script.sdk.Prayer.isQuickPrayerEnabled())
            org.tribot.script.sdk.Prayer.disableQuickPrayer();

        castHomeTele();
        if (restorePrayer()) {
            leaveViaPortal();
            Waiting.waitNormal(750, 150);
        }
        handleBank();
        leaveLunarIsle();
    }
}
