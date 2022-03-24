package scripts.Tasks.Fishing;

import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.MyPlayer;
import org.tribot.script.sdk.Waiting;
import scripts.*;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.Const;
import scripts.Data.SkillBank;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.Requirements.InventoryRequirement;
import scripts.Requirements.ItemReq;
import scripts.Tasks.MiscTasks.BuyItems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Fish implements Task {

    String message = "";
    int FISHING_NPC_ID = 1526;
    int BARBARIAN_ROD = 11323;
    RSTile BARB_FISHING_TILE = new RSTile(2499, 3506, 0);

    InventoryRequirement inventorySetUp;

    public boolean checkItems() {
        if (isBarbFishing()) {
            inventorySetUp = new InventoryRequirement(new ArrayList<>(
                    Arrays.asList(
                            new ItemReq(ItemID.FEATHER, 5000, 50),
                            new ItemReq(BARBARIAN_ROD, 1)
                    )));
        } else {
            inventorySetUp = new InventoryRequirement(new ArrayList<>(
                    Arrays.asList(
                            new ItemReq(ItemID.FEATHER, 5000, 50),
                            new ItemReq(ItemID.FLY_FISHING_ROD, 1)
                    ))
            );
        }

        return inventorySetUp.check();
    }


    public void getFishingItems() {
        if (!checkItems()) {
            BankManager.open(true);
            BankManager.depositAll(true);
            List<ItemReq> newInv = SkillBank.withdraw(inventorySetUp.getInvList());
            if (newInv != null && newInv.size() > 0) {
                List<ItemReq> i = new ArrayList<>();
                i.add(new ItemReq(ItemID.FEATHER, 5000));
                i.add(new ItemReq(ItemID.FLY_FISHING_ROD, 1));
                General.println("[Fishing Training]: Creating buy list");
                BuyItems.itemsToBuy = BuyItems.populateBuyList(i);
                return;
            }
            BankManager.close(true);
        }
    }

    public boolean isBarbFishing() {
        return Skills.getActualLevel(Skills.SKILLS.FISHING) >= 58;
    }


    public boolean goToFishingSpot() {
        if (isBarbFishing()) {
            if (BARB_FISHING_TILE.distanceTo(Player.getPosition()) > 15) {
                message = "Going to fishing spot - barb fishing";
                General.println(message);
                PathingUtil.walkToTile(BARB_FISHING_TILE, 3, false);
            }
            return BARB_FISHING_TILE.distanceTo(Player.getPosition()) < 20;
        } else if (!isBarbFishing() && !Const.BARBARIAN_OUTPOST.contains(Player.getPosition())) {
            message = "Going to fishing spot - barb village";
            PathingUtil.walkToArea(Const.BARBARIAN_OUTPOST, false);
            return Const.BARBARIAN_OUTPOST.contains(Player.getPosition());
        }
        return true;
    }

    public void catchFish() {
        if (checkItems()
                && goToFishingSpot() && !Inventory.isFull()) {

            if (Const.FISHING_ANIMATION.stream()
                    .noneMatch(a -> MyPlayer.getAnimation() == a)) {
                Utils.unselectItem();
                message = "Clicking fishing spot";
                if (isBarbFishing() && Utils.clickNPC("Fishing spot", "Use-rod"))
                    Timer.slowWaitCondition(() ->
                            Player.getAnimation() != -1, 6000, 7500);
                else if (Utils.clickNPC(FISHING_NPC_ID, "Lure"))
                    Timer.slowWaitCondition(() ->
                            Player.getAnimation() != -1, 6000, 7500);

            }
            Waiting.waitNormal(700, 50); //needed otherwise the timers below return
            if (Player.getAnimation() != -1) {
                message = "Fishing idle...";
                General.println(message);
                int chance = Utils.random(0, 100);
                if (chance < 40)
                    Timer.abc2SkillingWaitCondition(() -> (Interfaces.get(233, 2) != null ||
                            Inventory.isFull() ||
                            Const.FISHING_ANIMATION.stream()
                                    .noneMatch(a -> MyPlayer.getAnimation() == a)), 65000, 75000);
                else {
                    Timer.waitCondition(() -> (Interfaces.get(233, 2) != null ||
                            Inventory.isFull() || Const.FISHING_ANIMATION.stream()
                            .noneMatch(a -> MyPlayer.getAnimation() == a)), 65000, 75000);
                    Utils.idleAfkAction();
                }
            }

        } else
            getFishingItems();

    }


    @Override
    public String toString() {
        return message;
    }

    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return Vars.get().currentTask != null && Vars.get().currentTask.equals(SkillTasks.FISHING);
    }

    @Override
    public void execute() {
        checkItems();
        catchFish();
        if (Skills.getActualLevel(Skills.SKILLS.FISHING) >= SkillTasks.FISHING.getEndLevel())
            Vars.get().currentTask = null;
    }


    @Override
    public String taskName() {
        return "Fishing";
    }
}