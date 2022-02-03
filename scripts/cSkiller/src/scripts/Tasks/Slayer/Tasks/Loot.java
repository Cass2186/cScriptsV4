package scripts.Tasks.Slayer.Tasks;

import dax.walker.utils.camera.DaxCamera;
import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api2007.GroundItems;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSGroundItem;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSItemDefinition;
import org.tribot.script.sdk.Log;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.EatUtil;
import scripts.PathingUtil;
import scripts.Tasks.Slayer.SlayerConst.Assign;
import scripts.Tasks.Slayer.SlayerUtils.SlayerVars;
import scripts.Timer;
import scripts.rsitem_services.GrandExchange;
import scripts.rsitem_services.runelite.RuneLitePriceService;

public class Loot implements Task {
    public static int MINIMUM_LOOT_PRICE = 1000;

    int[] customLootIds = {
            19677, // ancient shard
            19679, // dark totem base
            19681, // middle
            19683, //top
            13510, // ensouled drag head
            13511, // ensouled drag head
            13501, // ensouled demon head
            13502, // ensouled demon head
            22374 // mossy key
    };
    int[] lootID = {1079, 1113, 1201, 1333, 1213, 1387, 1319, 1215, 20557, 562, 565, 4115, 23056, 4105, 4095, 4111, 23050, 4101, 4091, 2366, 11286, 3053, 4117, 23059, 4107, 4097, 4109, 23047, 4099, 4089, 44,
            4131, 4151, 561, 560, 564, 563, 566, 892, 1753, 1751, 536, 5295, 5304, 5300, 5296, 5303, 2363, 2361, 987, 985, 211
    };

    public boolean eatForSpace() {
        General.println("[Debug]: Eating for space");
        return EatUtil.eatFood();
    }

    public RuneLitePriceService priceGetter = new RuneLitePriceService();

    public boolean canTrade(RSItemDefinition itemDef) {
        return itemDef.isTradeableOnGrandExchange();
    }

    public RSGroundItem getLootItem() {

        RSGroundItem[] groundItems = GroundItems.getAll();

        if (groundItems.length > 0) {
            for (int i = 0; i < groundItems.length; i++) {
                RSItemDefinition def = groundItems[i].getDefinition();
                int id = groundItems[i].getID();

                if (def == null)
                    return null;

                if (!canTrade(def) && !def.isNoted() && id != 995 && SlayerVars.get().fightArea.contains(groundItems[i]))
                    return groundItems[i];

                if (def.isNoted())
                    id = def.getID() - 1;

                for (int cust : customLootIds)
                    if (id == cust && SlayerVars.get().fightArea.contains(groundItems[i]))
                        return groundItems[i];

                if (GrandExchange.getPrice(id) > SlayerVars.get().minLootValue) {

                    if (SlayerVars.get().fightArea.contains(groundItems[i])) {
                        Log.log("[Debug]: Looting " + def.getName());
                        return groundItems[i];
                    }


                    if (SlayerVars.get().fightArea.contains(groundItems[i]) &&
                            !def.getName().contains("Burnt bones")
                            && !def.getName().contains("ashes")) {
                        General.println("[Loot]: returning item line 96");
                        return groundItems[i];

                    }
                } else if (def.isStackable() || def.isNoted()) {

                    int individualPrice = GrandExchange.getPrice(id);
                    int amount = groundItems[i].getStack();
                    int value = individualPrice * amount;

                    if (value > SlayerVars.get().minLootValue) {

                        if (SlayerVars.get().fightArea.contains(groundItems[i])) {
                            Log.log("[Debug]: Looting " + def.getName());
                            return groundItems[i];
                        }

                    }
                }
            }
        }

        return null;
    }

    public void getLoot() {
        RSGroundItem loot = getLootItem();
        if (loot != null) {
            int inv = Inventory.getAll().length;

            if (SlayerVars.get().fightArea.contains(loot)) {
                String name = "";
                int id = loot.getID();


                int value = GrandExchange.getPrice(id);

                General.println("[Loot]: Looting " + name);

                if (Inventory.isFull() && !itemIsStackableAndInInventory(loot))
                    eatForSpace();

                if (loot.getPosition().distanceTo(Player.getPosition()) > 8 && !loot.isClickable()) {
                    PathingUtil.localNavigation(loot.getPosition());
                    Timer.waitCondition(loot::isClickable, 4500, 7000);
                }

                if (!loot.isClickable())
                    DaxCamera.focus(loot);

                if (DynamicClicking.clickRSGroundItem(loot, "Take")) {
                    Timer.slowWaitCondition(() -> Inventory.getAll().length > inv ||
                            Player.getPosition().equals(loot.getPosition()), 5000, 7000);
                    General.println("[Debug]: Done looting");
                    SlayerVars.get().lootValue = SlayerVars.get().lootValue + value;
                }
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
        return "Looting Items";
    }

    @Override
    public Priority priority() {
        return Priority.HIGH;
    }


    @Override
    public void execute() {
        if (SlayerVars.get().assignment.equals(Assign.ABBERANT_SPECTRES)) {
            SlayerVars.get().minLootValue = 1500;
        } else if (SlayerVars.get().assignment.equals(Assign.WYRM)) {
            SlayerVars.get().minLootValue = 2500;
        } else if (SlayerVars.get().assignment.equals(Assign.KURASK)) {
            SlayerVars.get().minLootValue = 1500;
        } else {
            SlayerVars.get().minLootValue = 1500;
        }
        // for (int i = 0; i < 3; i++)
        getLoot();


    }


    @Override
    public boolean validate() {
        return Vars.get().currentTask != null &&
                Vars.get().currentTask.equals(SkillTasks.SLAYER) &&
                SlayerVars.get().fightArea != null &&
                SlayerVars.get().fightArea.contains(Player.getPosition()) && getLootItem() != null;//&& shouldLoot();
    }


    @Override
    public String taskName() {
        return "Slayer";
    }
}
