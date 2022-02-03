package scripts.Tasks;

import dax.walker.utils.camera.DaxCamera;
import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api2007.GroundItems;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSGroundItem;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSItemDefinition;
import org.tribot.script.sdk.Waiting;
import scripts.*;
import scripts.Data.Areas;
import scripts.Data.Vars;
import scripts.rsitem_services.GrandExchange;
import scripts.rsitem_services.runelite.RuneLitePriceService;

public class LootItems implements Task {

    public static int MINIMUM_LOOT_PRICE = 750;

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

    public boolean shouldLoot() {
        RSGroundItem[] groundItems = GroundItems.getAll();
        if (groundItems.length > 0) {
            for (int i = 0; i < groundItems.length; i++) {
                RSItemDefinition def = groundItems[i].getDefinition();
                int id = groundItems[i].getID();
                if (!Areas.UNDEAD_DRUID_AREA.contains(groundItems[i].getPosition()))
                    continue;
                for (int cust : customLootIds) {
                    if (id == cust) {
                        return true;
                    }
                }

                if (def != null && def.isNoted()) {
                    //General.println("Unnoted Id = " + id);
                    id = def.getID() - 1;
                }
                if (GrandExchange.getPrice(id) > Vars.get().minLootValue) {
                        return true;

                } else if (def != null && (def.isStackable() || def.isNoted())) {
                    int individualPrice = GrandExchange.getPrice(id);
                    int amount = groundItems[i].getStack();
                    int value = individualPrice * amount;

                    if (value > Vars.get().minLootValue && new RSArea(Player.getPosition(),5)
                            .contains(groundItems[i]))
                        if (groundItems[i].isClickable())
                            return true;

                }
            }
        }

        return false;
    }

    public RSGroundItem getLootItem() {

        RSGroundItem[] groundItems = GroundItems.getAll();

        if (groundItems.length > 0) {
            for (int i = 0; i < groundItems.length; i++) {
                RSItemDefinition def = groundItems[i].getDefinition();
                if (!Areas.UNDEAD_DRUID_AREA.contains(groundItems[i].getPosition()))
                    continue;

                int id = groundItems[i].getID();

                if (def == null)
                    return null;

                if (def.isNoted())
                    id = def.getID() - 1;

                for (int cust : customLootIds)
                    if (id == cust)
                        return groundItems[i];

                if (GrandExchange.getPrice(id) > Vars.get().minLootValue) {


                    if (!def.getName().contains("Burnt bones")
                            && !def.getName().contains("ashes")) {
                        General.println("returning item line 96");
                        return groundItems[i];

                    }
                } else if (def.isStackable() || def.isNoted()) {

                    int individualPrice = GrandExchange.getPrice(id);
                    int amount = groundItems[i].getStack();
                    int value = individualPrice * amount;

                    if (value > Vars.get().minLootValue) {

                        if (groundItems[i].isClickable())
                            return groundItems[i];
                    }
                }
            }
        }

        return null;
    }

    public void getLoot() {
        RSGroundItem[] groundItems = GroundItems.getAll();
        RSGroundItem loot = getLootItem();
        if (loot != null) {
            int inv = Inventory.getAll().length;
            if (!Areas.UNDEAD_DRUID_AREA.contains(loot.getPosition()))
               return;

           // if (SlayerVars.get().fightArea.contains(loot)) {

                RSItemDefinition def = loot.getDefinition();
                String name = "";
                int id = loot.getID();


                if (def != null) {

                    name = def.getName();
                    if (def.isNoted()) {
                        id = def.getID() - 1;
                    }

                    int value = GrandExchange.getPrice(id);

                    General.println("[Debug]: Looting " + name);

                    if (Inventory.isFull() && !itemIsStackableAndInInventory(loot))
                        eatForSpace();

                    if (loot.getPosition().distanceTo(Player.getPosition()) > 8 && !loot.isClickable()) {
                       return;
                     //   PathingUtil.localNavigation(loot.getPosition());
                        //Timer.waitCondition(loot::isClickable, 4500,7000);
                    }

                    if (!loot.isClickable())
                        DaxCamera.focus(loot);

                    if (DynamicClicking.clickRSGroundItem(loot, "Take " + name)) {
                        Timer.slowWaitCondition(() -> loot.getPosition().equals(Player.getPosition()), 5000, 7000);
                        General.println("[Debug]: Done looting");
                        Vars.get().lootValue = Vars.get().lootValue + value;
                        Waiting.waitNormal(300,45);
                    }

               // }
            }
        }
    }


    private boolean itemIsStackableAndInInventory(RSGroundItem item) {
        RSItem[] inventory = Inventory.find(item.getID());
        if (inventory.length > 0) {
            RSItemDefinition def = inventory[0].getDefinition();
            return def != null && def.isStackable();

        }
        return false;
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
        return shouldLoot() && Vars.get().fightArea.contains(Player.getPosition());
    }

    @Override
    public void execute() {
        RSItem[] closedBag = Inventory.find(ItemID.LOOTING_BAG);
        if (closedBag.length > 0 && closedBag[0].click("Open"))
            Utils.microSleep();
        getLoot();
    }
}
