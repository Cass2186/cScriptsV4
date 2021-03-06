package scripts.Tasks;

import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api2007.GroundItems;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSGroundItem;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSItemDefinition;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.MyPlayer;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.GroundItem;
import scripts.*;
import scripts.Data.Areas;
import scripts.Data.Vars;
import scripts.rsitem_services.GrandExchange;
import scripts.rsitem_services.runelite.RuneLitePriceService;

import java.util.List;
import java.util.Optional;

public class LootItems implements Task {

    public static int MINIMUM_LOOT_PRICE = 1000;

    int[] customLootIds = {
            19677, // ancient shart
            19679, // dark totem base
            19681, // middle
            19683, //top
            13510, // ensouled drag head
            13511, // ensouled drag head
            13501, // ensouled demon head
            13502, // ensouled demon head
            11941 // looting bag
    };
    int[] lootID = {1079, 1113, 1201, 1333, 1213, 1387, 1319, 1215, 20557, 562, 565, 4115, 23056, 4105, 4095, 4111, 23050, 4101, 4091, 2366, 11286, 3053, 4117, 23059, 4107, 4097, 4109, 23047, 4099, 4089, 44,
            4131, 4151, 561, 560, 564, 563, 566, 892, 1753, 1751, 536, 5295, 5304, 5300, 5296, 5303, 2363, 2361, 987, 985, 211
    };

    public boolean eatForSpace() {
        General.println("[Debug]: Eating");
        return EatUtil.eatFood();
    }

    public RuneLitePriceService priceGetter = new RuneLitePriceService();


    public static Optional<GroundItem> getLootItem() {

        List<GroundItem> groundItemList = Query.groundItems()
                .inArea(Areas.UNDEAD_DRUID_AREA_SDK)
                .nameNotContains("Burnt bones", "Ashes", "ashes")
                .toList();
        // Log.debug("grounditemlist.size(): " + groundItemList.size());

        if (Vars.get().killingScarabs)
            groundItemList = Query.groundItems()
                    .inArea(Areas.scarabFightAreaSdk)
                    .nameNotContains("Burnt bones", "Ashes", "ashes")
                    .toList();

        // Log.debug("grounditemlist.size(): " + groundItemList.size());
        for (GroundItem g : groundItemList) {
            //    if (GrandExchange.getPrice(g.getId()) > 150)
            // Log.debug("[Loot] Item is " + g.getName() + " worthL: " + GrandExchange.getPrice(g.getId()));
            if (GrandExchange.getPrice(g.getId()) > Vars.get().minLootValue) {
                //  Log.debug("[Loot] Item is " + g.getName());
                return Optional.of(g);
            } else if (g.getDefinition().isStackable() || g.getDefinition().isNoted()) {
                int individualPrice = GrandExchange.getPrice(g.getId());
                if (g.getDefinition().isNoted()) {
                    individualPrice = GrandExchange.getPrice((g.getId() - 1));
                    // Log.debug("Getting Noted id price of " + individualPrice);
                }
                int amount = g.getStack();
                int value = individualPrice * amount;
                if (value > Vars.get().minLootValue) {
                    // Log.debug("[Loot] Item is " + g.getName());
                    return Optional.of(g);
                }
            } else if (g.getId() == 995 && g.getStack() > 500) {
                return Optional.of(g);
            }
        }
        return Optional.empty();
    }

    public boolean pickupLootItem() {
        RSGroundItem[] groundItems = GroundItems.getAll();
        Optional<GroundItem> loot = getLootItem();
        if (loot.isEmpty()) return false;

        int inv = Inventory.getAll().length;

        int id = loot.get().getId();
        if (loot.get().getDefinition().isNoted()) {
            id = id - 1;
        }

        int value = GrandExchange.getPrice(id);

        General.println("[Debug]: Looting " + loot.get().getName());

        if (Inventory.isFull() && !itemIsStackableAndInInventory(loot.get()))
            eatForSpace();

        if (loot.get().interact("Take")) {
            Timer.slowWaitCondition(() ->
                            loot.get().getTile().equals(MyPlayer.getPosition()),
                    5000, 7000);
            General.println("[Debug]: Done looting");
            Vars.get().lootValue = Vars.get().lootValue + value;
            Waiting.waitNormal(300, 45);
            return true;
        }
        return false;
    }


    private boolean itemIsStackableAndInInventory(GroundItem item) {
        RSItem[] inventory = Inventory.find(item.getId());
        return inventory.length > 0 &&
                item.getDefinition().isStackable();
    }


    @Override
    public String toString() {
        return "Looting item";
    }

    @Override
    public Priority priority() {
        return Priority.HIGH;
    }

    @Override
    public boolean validate() {
        return getLootItem().isPresent()
                && ((Vars.get().killingScarabs && Areas.LARGE_SCARAB_FIGHT_AREA.containsMyPlayer())
                || (Vars.get().killingUndeadDruids && Areas.UNDEAD_DRUID_AREA.containsMyPlayer())
                || Vars.get().fightArea.containsMyPlayer());

    }

    @Override
    public void execute() {
        RSItem[] closedBag = Inventory.find(ItemID.LOOTING_BAG);
        if (closedBag.length > 0 && closedBag[0].click("Open"))
            Utils.microSleep();
        pickupLootItem();
    }
}
