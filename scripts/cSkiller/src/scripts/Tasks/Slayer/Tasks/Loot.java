package scripts.Tasks.Slayer.Tasks;

import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSItemDefinition;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.MyPlayer;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.pricing.Pricing;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.GroundItem;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.EatUtil;
import scripts.Tasks.Slayer.SlayerConst.Assign;
import scripts.Tasks.Slayer.SlayerUtils.SlayerVars;
import scripts.Timer;
import scripts.rsitem_services.runelite.RuneLitePriceService;

import java.util.Optional;

public class Loot implements Task {
    public static int MINIMUM_LOOT_PRICE = 1000;

    private static int[] customLootIds = new int[]{
            19677, // ancient shard
            19679, // dark totem base
            19681, // middle
            19683, //top
            13510, // ensouled drag head
            13511, // ensouled drag head
            13501, // ensouled demon head
            13502, // ensouled demon head
            22374}; // mossy key

    int[] lootID = {1079, 1113, 1201, 1333, 1213, 1387, 1319, 1215, 20557, 562, 565, 4115, 23056, 4105, 4095, 4111, 23050, 4101, 4091, 2366, 11286, 3053, 4117, 23059, 4107, 4097, 4109, 23047, 4099, 4089, 44,
            4131, 4151, 561, 560, 564, 563, 566, 892, 1753, 1751, 536, 5295, 5304, 5300, 5296, 5303, 2363, 2361, 987, 985, 211
    };

    public boolean eatForSpace() {
        Log.info("Eating for space");
        return EatUtil.eatFood(false);
    }

    public RuneLitePriceService priceGetter = new RuneLitePriceService();

    public boolean canTrade(RSItemDefinition itemDef) {
        return itemDef.isTradeableOnGrandExchange();
    }


    public static Optional<GroundItem> getLootItem() {
        Optional<GroundItem> customLoot = Query.groundItems()
                .inArea(SlayerVars.get().fightArea)
                .idEquals()
                .idEquals(customLootIds)
                .findClosestByPathDistance();


        Optional<GroundItem> groundItemList = Query.groundItems()
                .inArea(SlayerVars.get().fightArea)
                .nameNotContains("Burnt bones", "Ashes", "ashes")
                .minStackPrice(SlayerVars.get().minLootValue)
                .findClosestByPathDistance();


        return customLoot.isPresent() ? customLoot : groundItemList;
        /*for (GroundItem g : groundItemList) {
             if (GrandExchange.getPrice(g.getId()) > SlayerVars.get().minLootValue) {
                Log.debug("[Loot] Item is " + g.getName());
                return Optional.of(g);
            } else if (g.getDefinition().isStackable() || g.getDefinition().isNoted()) {
                int individualPrice = GrandExchange.getPrice(g.getId());
                if (g.getDefinition().isNoted()) {
                    individualPrice = GrandExchange.getPrice((g.getId() - 1));
                }
                int amount = g.getStack();
                int value = individualPrice * amount;
                if (value > SlayerVars.get().minLootValue) {
                    Log.debug("[Loot] Item is " + g.getName());
                    return Optional.of(g);
                }
            } else if (g.getId() == 995 && g.getStack() > 500){
                return Optional.of(g);
            }
        }
        return Optional.empty();*/
    }

    public boolean pickupLootItem() {
        Optional<GroundItem> loot = getLootItem();
        if (loot.isEmpty()) return false;

        int id = loot.map(GroundItem::getId).orElse(-1);
        if (loot.map(l -> l.getDefinition().isNoted()).orElse(false)) {
            id = id - 1;
        }

        Optional<Integer> value = Pricing.lookupPrice(id);
        Log.info("Looting " + loot.get().getName());


        if (Inventory.isFull() && !itemIsStackableAndInInventory(loot.get()))
            eatForSpace();

        if (loot.map(l->l.interact("Take")).orElse(false)) {
            Timer.slowWaitCondition(() ->
                            loot.get().getTile().equals(MyPlayer.getTile()),
                    5000, 7000);
            value.ifPresent(v-> SlayerVars.get().lootValue = SlayerVars.get().lootValue + v);
            Waiting.waitNormal(250, 35);
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
        pickupLootItem();


    }


    @Override
    public boolean validate() {
        return Vars.get().currentTask != null &&
                Vars.get().currentTask.equals(SkillTasks.SLAYER) &&
                SlayerVars.get().fightArea != null &&
                SlayerVars.get().fightArea.contains(MyPlayer.getTile()) && getLootItem().isPresent();//&& shouldLoot();
    }


    @Override
    public String taskName() {
        return "Slayer";
    }
}
