package scripts.Tasks.Slayer.Tasks;

import org.tribot.api.General;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSItem;
import org.tribot.script.sdk.Waiting;
import scripts.*;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.Tasks.Slayer.SlayerConst.Areas;
import scripts.Tasks.Slayer.SlayerUtils.SlayerVars;

public class SlayerShop implements Task {

    int PARENT_SHOP_INTERFACE = 300;
    int CHILD_ITEMS_INTERFACE = 16;

    private void goToTurael() {
        RSItem[] invitem = Inventory.find(ItemId.GAMES_NECKLACE);
        if (invitem.length < 1) {
            if (!Areas.TURAEL_AREA.contains(Player.getPosition())) {
                BankManager.open(true);

                if (Inventory.getAll().length > 20)
                    BankManager.depositAll(true);

                BankManager.checkEquippedGlory();

                if (!BankManager.withdraw(1, true, ItemId.GAMES_NECKLACE))
                    SlayerVars.get().shouldRestock = true;

                BankManager.withdraw(0, true, 995);
                BankManager.withdraw(1, true, ItemId.VARROCK_TELEPORT);
                BankManager.close(true);
                Utils.shortSleep();
            }
        }

        if (Inventory.find(ItemId.GAMES_NECKLACE).length > 0)
            PathingUtil.walkToArea(Areas.TURAEL_AREA);
    }

    public void getCoinsForShop() {
        RSItem[] invitem = Inventory.find(995);
        if (invitem.length < 1) {
            General.println("[SlayerRestock]: Getting Coins");
            BankManager.open(true);

            if (Inventory.getAll().length > 20)
                BankManager.depositAll(true);

            BankManager.withdraw(0, true, 995);
            BankManager.withdraw(1, true, ItemId.VARROCK_TELEPORT);
            BankManager.close(true);

        }
    }

    public boolean openShop() {
        getCoinsForShop();
        PathingUtil.walkToArea(Areas.NIEVE_AREA);
        General.println("[SlayerRestock]: Opening shop");
        if (Utils.clickNPC("Nieve", "Trade")) {
            return Timer.waitCondition(() -> Interfaces.isInterfaceSubstantiated(PARENT_SHOP_INTERFACE), 10000, 15000);
        }
        return Interfaces.isInterfaceSubstantiated(PARENT_SHOP_INTERFACE);
    }

    private boolean buyItem(int item) {
        if (openShop()) {
            Waiting.waitNormal(1200, 250);
            if (Interfaces.isInterfaceSubstantiated(PARENT_SHOP_INTERFACE, CHILD_ITEMS_INTERFACE) &&
                    Inventory.find(995).length > 0 &&
                    InterfaceUtil.searchInterfaceItemAndClickAction(PARENT_SHOP_INTERFACE,
                            CHILD_ITEMS_INTERFACE, item, "Buy 1"))
                return Timer.waitCondition(() -> Inventory.find(item).length > 0, 7000, 10000);
        }
        return false;
    }

    public boolean buyIceCooler() {
        if (openShop()) {
            if (Interfaces.isInterfaceSubstantiated(PARENT_SHOP_INTERFACE, CHILD_ITEMS_INTERFACE)
                    && Inventory.find(ItemId.COINS).length > 0) {
                for (int i = 0; i < 5; i++)
                    if (InterfaceUtil.searchInterfaceItemAndClickAction(PARENT_SHOP_INTERFACE, CHILD_ITEMS_INTERFACE,
                            ItemId.ICE_COOLER, "Buy 50"))
                        Utils.microSleep();
            }
            return Timer.waitCondition(() -> Inventory.find(ItemId.ICE_COOLER).length > 0, 7000, 10000);
        }

        return false;
    }

    public boolean buyItemIn10Repeat(int itemId, int numberOfRepeats) {
        if (openShop()) {
            if (Interfaces.isInterfaceSubstantiated(PARENT_SHOP_INTERFACE, CHILD_ITEMS_INTERFACE)
                    && Inventory.find(ItemId.COINS).length > 0) {

                for (int i = 0; i < numberOfRepeats; i++)
                    if (InterfaceUtil.searchInterfaceItemAndClickAction(PARENT_SHOP_INTERFACE,
                            CHILD_ITEMS_INTERFACE, itemId, "Buy 10"))
                        Utils.microSleep();
            }
            return Timer.waitCondition(() -> Inventory.find(itemId).length > 0, 7000, 10000);
        }
        return false;
    }


    public void closeShop() {
        if (Interfaces.isInterfaceSubstantiated(PARENT_SHOP_INTERFACE, 1, 11)) {
            if (Interfaces.get(PARENT_SHOP_INTERFACE, 1, 11).click())
                Utils.shortSleep();
        }
    }

    public static boolean shouldSlayerShop() {
        return SlayerVars.get().needsGem || SlayerVars.get().slayerShopRestock ||
                SlayerVars.get().needBootsOfStone || SlayerVars.get().needRockHammer
                || SlayerVars.get().needFungicide;
    }

    @Override
    public String toString() {
        return "Purchasing Items (Slayer shop)";
    }

    @Override
    public Priority priority() {
        return Priority.LOW;
    }


    @Override
    public boolean validate() {
        return  Vars.get().currentTask != null &&
                Vars.get().currentTask.equals(SkillTasks.SLAYER) && shouldSlayerShop();
    }

    @Override
    public void execute() {
        if (SlayerVars.get().needsGem) {
            buyItem(ItemId.ENCHANTED_GEM);
            Utils.shortSleep();
            SlayerVars.get().needsGem = false;
        }
        if (SlayerVars.get().needFungicide) {
            buyItemIn10Repeat(ItemId.FUNGICIDE_SPRAYER[0], 3);
            SlayerVars.get().needFungicide = false;
        }
        if (SlayerVars.get().slayerShopRestock) {
            buyIceCooler();
            Utils.shortSleep();
        }
        if (SlayerVars.get().needBootsOfStone) {
            buyItem(ItemId.BOOTS_OF_STONE);
            SlayerVars.get().needBootsOfStone = false;
        }
        if (SlayerVars.get().needRockHammer) {
            buyItem(ItemId.ROCK_HAMMER);
            SlayerVars.get().needRockHammer = false;
        }
        closeShop();
        SlayerVars.get().slayerShopRestock = false;
    }


    @Override
    public String taskName() {
        return "Slayer";
    }
}
