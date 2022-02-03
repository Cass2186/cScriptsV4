package scripts.Tasks.Smithing;

import org.tribot.api.General;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSInterface;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.types.InventoryItem;
import scripts.*;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.Enums.SMITH_ITEMS;
import scripts.Data.SkillBank;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.Requirements.ItemReq;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SmithBars implements Task {

    public int SMITH_INTERFACE_PARENT = 312;
    public int[] INTERFACE_CHILDREN = {
            9, //dagger
            11, //scimiar
            16, //warhammer
            22, // platebody
    };
    public int INTERFACE_COMPONENT = 0;

    public static RSTile ANVIL_TILE = new RSTile(3188, 3425, 0);
    //   public static RSTile bankTile = new RSTile(3185, 3435, 0);

    public void smithItem(SMITH_ITEMS.genericSmithItems item, int barId) {
        RSItem[] invItem1 = Inventory.find(barId);
        Log.log("Bar ID is " + barId);
        if (invItem1.length >= item.getNumBars()) {
            if (!ANVIL_TILE.isClickable()) {
                PathingUtil.walkToTile(ANVIL_TILE, 2, false);
                Timer.waitCondition(() -> ANVIL_TILE.isClickable(), 4500, 7000);
            }

            if (Utils.clickObject("Anvil", "Smith", false))
                Timer.waitCondition(() -> Interfaces.isInterfaceSubstantiated(SMITH_INTERFACE_PARENT), 5000, 8000);

            RSInterface smithInter = Interfaces.get(SMITH_INTERFACE_PARENT, item.getInterfaceChild());
            if (smithInter != null && smithInter.click())
                Timer.abc2SkillingWaitCondition(() ->
                        Inventory.find(barId).length < item.getNumBars(), 35000, 50000);
        } else
            bankForItems(barId);
    }


    public void bankForItems(int barId) {
        if (Inventory.find(ItemID.HAMMER).length < 1 || Inventory.find(barId).length < 5) {

            List<ItemReq> inv = new ArrayList<>(
                    Arrays.asList(
                            new ItemReq(ItemID.HAMMER, 1),
                            new ItemReq(barId, 0)
                    )
            );

            BankManager.open(true);
            BankManager.depositAllExcept(false, ItemID.HAMMER);
            Timer.waitCondition(()-> Inventory.getAll().length == 1, 2500,4000);

            List<ItemReq> newInv = SkillBank.withdraw(inv);
            if (newInv != null && newInv.size() > 0) {
                General.println("[Smithing Training]: Creating buy list");
                //TODO FIX THIS
                //  BuyItems.itemsToBuy = BuyItems.populateBuyList(HerbloreItems.getRequiredItemList());
                return;
            }
            BankManager.close(true);
        }
    }

    public double getXpFromBarId(int barId) {

        List<InventoryItem> all = org.tribot.script.sdk.Inventory.getAll();

        Waiting.waitUntil(5000, ()-> org.tribot.script.sdk.Inventory.getAll().size() > all.size());

        if (barId == ItemID.BRONZE_BAR) {
            return 12.5;
        } else if (barId == ItemID.IRON_BAR) {
            return 25;
        } else if (barId == ItemID.STEEL_BAR) {
            return 37.5;
        } else if (barId == ItemID.MITHRIL_BAR) {
            return 50;
        } else if (barId == ItemID.ADAMANTITE_BAR) {
            return 62.5;
        }
        return -1;


    }


    @Override
    public String toString() {
        return "Smithing items";
    }

    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return Vars.get().currentTask != null && Vars.get().currentTask.equals(SkillTasks.SMITHING);
       // && !Vars.get().smeltCannonballs;
    }

    @Override
    public void execute() {
        if (Skills.getCurrentLevel(Skills.SKILLS.SMITHING) < 5) {
            smithItem(SMITH_ITEMS.genericSmithItems.DAGGER, ItemID.BRONZE_BAR);
        } else if (Skills.getCurrentLevel(Skills.SKILLS.SMITHING) < 9) {
            smithItem(SMITH_ITEMS.genericSmithItems.SCIMITAR, ItemID.BRONZE_BAR);
        } else if (Skills.getCurrentLevel(Skills.SKILLS.SMITHING) < 18) {
            smithItem(SMITH_ITEMS.genericSmithItems.WARHAMMER, ItemID.BRONZE_BAR);
        } else if (Skills.getCurrentLevel(Skills.SKILLS.SMITHING) < 20) {
            smithItem(SMITH_ITEMS.genericSmithItems.PLATEBODY, ItemID.BRONZE_BAR);
        } else if (Skills.getCurrentLevel(Skills.SKILLS.SMITHING) < 24) {
            smithItem(SMITH_ITEMS.genericSmithItems.SCIMITAR, ItemID.IRON_BAR);
        } else if (Skills.getCurrentLevel(Skills.SKILLS.SMITHING) < 33) {
            smithItem(SMITH_ITEMS.genericSmithItems.WARHAMMER, ItemID.IRON_BAR);
        } else if (Skills.getCurrentLevel(Skills.SKILLS.SMITHING) < 39) {
            smithItem(SMITH_ITEMS.genericSmithItems.PLATEBODY, ItemID.IRON_BAR);
        } else if (Skills.getCurrentLevel(Skills.SKILLS.SMITHING) < 48) {
            smithItem(SMITH_ITEMS.genericSmithItems.WARHAMMER, ItemID.STEEL_BAR);
        } else if (Skills.getCurrentLevel(Skills.SKILLS.SMITHING) < 55) {
            smithItem(SMITH_ITEMS.genericSmithItems.PLATEBODY, ItemID.STEEL_BAR);
        } else if (Skills.getCurrentLevel(Skills.SKILLS.SMITHING) < 59) {
            smithItem(SMITH_ITEMS.genericSmithItems.SCIMITAR, ItemID.MITHRIL_BAR);
        } else if (Skills.getCurrentLevel(Skills.SKILLS.SMITHING) < 68) {
            smithItem(SMITH_ITEMS.genericSmithItems.WARHAMMER, ItemID.MITHRIL_BAR);
        } else if (Skills.getCurrentLevel(Skills.SKILLS.SMITHING) < 75) {
            smithItem(SMITH_ITEMS.genericSmithItems.PLATEBODY, ItemID.MITHRIL_BAR);
        } else if (Skills.getCurrentLevel(Skills.SKILLS.SMITHING) < 79) {
            smithItem(SMITH_ITEMS.genericSmithItems.SCIMITAR, ItemID.ADAMANTITE_BAR);
        } else if (Skills.getCurrentLevel(Skills.SKILLS.SMITHING) < 88) {
            smithItem(SMITH_ITEMS.genericSmithItems.WARHAMMER, ItemID.ADAMANTITE_BAR);
        } else smithItem(SMITH_ITEMS.genericSmithItems.PLATEBODY, ItemID.ADAMANTITE_BAR);
    }

    @Override
    public String taskName() {
        return "Smithing";
    }
}
