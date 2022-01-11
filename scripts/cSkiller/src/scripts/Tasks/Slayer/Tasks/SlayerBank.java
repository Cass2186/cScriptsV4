package scripts.Tasks.Slayer.Tasks;

import dax.api_lib.models.RunescapeBank;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSItem;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.interfaces.Item;
import org.tribot.script.sdk.tasks.BankTask;
import scripts.*;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.Enums.CraftItems;
import scripts.Data.SkillBank;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.Requirements.InventoryRequirement;
import scripts.Requirements.ItemReq;
import scripts.Requirements.ItemRequirement;
import scripts.Tasks.MiscTasks.BuyItems;
import scripts.Tasks.Slayer.SlayerConst.Areas;
import scripts.Tasks.Slayer.SlayerUtils.SlayerVars;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class SlayerBank implements Task {

    boolean needsDustyKey = false;

    public boolean is2hEquipped() {
        return Equipment.SLOTS.SHIELD.getItem() == null;
    }


    //redundant for equipment class one
    public static boolean isEquipped(int id) {
        return Equipment.isEquipped(id);
    }

    public int isInInventory(int id) {
        RSItem[] item = Inventory.find(id);
        return item.length;
    }

    public static boolean goToVarrockWestBank() {
        SlayerVars.get().status = "Going to bank";

        if (Prayer.isPrayerEnabled(Prayer.PRAYERS.PROTECT_FROM_MELEE))
            Prayer.disable(Prayer.PRAYERS.PROTECT_FROM_MELEE);

        if (Prayer.isPrayerEnabled(Prayer.PRAYERS.PROTECT_FROM_MAGIC))
            Prayer.disable(Prayer.PRAYERS.PROTECT_FROM_MAGIC);

        if (Prayer.isPrayerEnabled(Prayer.PRAYERS.PROTECT_FROM_MISSILES))
            Prayer.disable(Prayer.PRAYERS.PROTECT_FROM_MISSILES);

        RSItem[] tab = Inventory.find(ItemId.VARROCK_TELEPORT);

        if (tab.length > 0 && !Banking.isInBank())
            return PathingUtil.walkToTile(RunescapeBank.VARROCK_WEST.getPosition(), 2, false);

        return RunescapeBank.VARROCK_WEST.getPosition().distanceTo(Player.getPosition()) < 15;
    }

    /**
     * THESE 3 Methods are USED MOST FREQUENTLY
     *
     * @param teleTab
     */
    public void generalInventorySetup(int teleTab) { // has 2 open spaces
        generalGloryAndExpeditious(true);
        checkBraceletOfSlaughter();

        BankManager.depositAll(true);
        BankManager.withdraw(5000, true, ItemId.COINS);
        BankManager.withdraw(2, true, ItemId.VARROCK_TELEPORT);
        if (teleTab != ItemId.VARROCK_TELEPORT)
            BankManager.withdraw(2, true, teleTab);

        if (!isWearingSlayerHelm())
            BankManager.withdraw(1, true, ItemId.ENCHANTED_GEM);

        BankManager.withdrawArray(ItemId.SUPER_COMBAT_POTION, 1);
        BankManager.withdrawArray(ItemId.STAMINA_POTION, 1);
        BankManager.withdraw(0, true, ItemId.MONKFISH);

        if (!BankManager.checkInventoryItems(ItemId.MONKFISH, ItemId.VARROCK_TELEPORT)) {
            General.println("[Bank]: restocking due to monkfish or varrock tabs missing");
            SlayerVars.get().shouldRestock = true;
        }
    }

    private static boolean generalGloryAndExpeditious(boolean useExpeditious) {
        BankManager.open(true);
        if (!Equipment.isEquipped(ItemId.AMULET_OF_FURY) && !BankManager.checkEquippedGlory()) {
            General.println("[Bank]: restocking due to glory missing (92)");
            SlayerVars.get().shouldRestock = true;
            return false;
        }
        if (useExpeditious)
            checkExpeditiousBracelet();
        return true;
    }

    /** this one is called when you want a custom array item such as a teleport item (e.g. skills necklace)
     * @param teleItem the tab needed, use -1 if none
     * @param useExpeditious
     * @param arrayItem
     * @param itemRequirements
     */
    public void newInventorySetup(int teleItem, boolean useExpeditious, int[] arrayItem,
                                  ItemReq... itemRequirements) {
        newInventorySetup(teleItem, useExpeditious, itemRequirements);
        BankManager.withdrawArray(arrayItem, 1);
    }

    public void newInventorySetup(int teleItem, boolean useExpeditious,
                                  ItemReq... itemRequirements) {

        generalGloryAndExpeditious(useExpeditious);
        checkBraceletOfSlaughter();

        java.util.List<ItemReq>  myInv = new ArrayList<>(
                Arrays.asList(
                        new ItemReq(ItemId.VARROCK_TELEPORT, 2),
                        new ItemReq(ItemId.STAMINA_POTION, 1),
                        new ItemReq(SlayerVars.get().potionToUse, 1)

                )
        );

        BankManager.depositAll(true);
        if (itemRequirements != null) {
            myInv.addAll(Arrays.asList(itemRequirements));
        }

        if (teleItem != ItemId.VARROCK_TELEPORT && teleItem != -1)
            myInv.add(new ItemReq(teleItem, 1));

        if (!Equipment.isEquipped(ItemId.SLAYER_HELM) && !Equipment.isEquipped(ItemId.SLAYER_HELM_I))
            myInv.add(new ItemReq(ItemId.ENCHANTED_GEM, 1));

        myInv.add(new ItemReq(ItemId.MONKFISH, 0));
        java.util.List<ItemReq> newInv = SkillBank.withdraw(myInv);
        if (newInv != null && newInv.size() > 0) {
            General.println("[Slayer Training]: Creating buy list");
            BuyItems.itemsToBuy = SlayerRestock.getRetockList();
        }
    }

    public void generalInventorySetup(int teleItem, boolean useExpeditious,
                                      ItemRequirement... itemRequirements) {
        generalGloryAndExpeditious(useExpeditious);
        checkBraceletOfSlaughter();

        BankManager.depositAll(true);
        if (itemRequirements != null) {
            for (ItemRequirement i : itemRequirements)
                BankManager.withdraw(i.getAmount(), true, i.getId());
        }
        if (Arrays.stream(itemRequirements).anyMatch(r -> !r.check())) {
            General.println("[SlayerBank]: We failed an itemrequirement, restocking");
            SlayerVars.get().shouldRestock = true;
            return;
        }
        BankManager.withdraw(5000, true, ItemId.COINS);
        BankManager.withdraw(2, true, ItemId.VARROCK_TELEPORT);
        if (teleItem != ItemId.VARROCK_TELEPORT && teleItem != -1)
            BankManager.withdraw(1, true, teleItem);


        if (!Equipment.isEquipped(ItemId.SLAYER_HELM) && !Equipment.isEquipped(ItemId.SLAYER_HELM_I))
            BankManager.withdraw(1, true, ItemId.ENCHANTED_GEM);

        BankManager.withdrawArray(ItemId.STAMINA_POTION, 1);
        BankManager.withdrawArray(SlayerVars.get().potionToUse, 1);
        BankManager.withdraw(0, true, SlayerVars.get().customFoodId);

        if (!BankManager.checkInventoryItems(SlayerVars.get().customFoodId)) {
            General.println("[SlayerBank]: restocking due to food missing");
            SlayerVars.get().shouldRestock = true;
            return;
        }
    }


    public void generalInventorySetup(int teleItem, boolean useExpeditious,
                                      boolean useCannon, int... otherItems) {

        generalGloryAndExpeditious(useExpeditious);
        checkBraceletOfSlaughter();

        BankManager.depositAll(true);
        if (otherItems != null) {
            for (int i : otherItems)
                BankManager.withdraw(1, true, i);
        }
        BankManager.withdraw(5000, true, ItemId.COINS);
        if (useCannon) {
            BankManager.withdraw(750, true, ItemId.CANNONBALL);
            BankManager.withdraw(1, true, 6);
            BankManager.withdraw(1, true, 8);
            BankManager.withdraw(1, true, 10);
            BankManager.withdraw(1, true, 12);
            if (Inventory.find(ItemId.CANNON_IDS).length < 4) {
                General.println("[bank]: We're missing parts of the cannon, turning it to false");
                SlayerVars.get().use_cannon = false;
            }

        }


        BankManager.withdraw(2, true, ItemId.VARROCK_TELEPORT);
        if (teleItem != ItemId.VARROCK_TELEPORT)
            BankManager.withdraw(1, true, teleItem);


        if (!Equipment.isEquipped(ItemId.SLAYER_HELM) && !Equipment.isEquipped(ItemId.SLAYER_HELM_I))
            BankManager.withdraw(1, true, ItemId.ENCHANTED_GEM);

        BankManager.withdrawArray(ItemId.STAMINA_POTION, 1);
        BankManager.withdrawArray(ItemId.SUPER_COMBAT_POTION, 1);
        BankManager.withdraw(0, true, SlayerVars.get().customFoodId);

        if (!BankManager.checkInventoryItems(SlayerVars.get().customFoodId)) {
            General.println("[Bank]: restocking due to food missing");
            SlayerVars.get().shouldRestock = true;
            return;
        }
    }

    public void generalInventorySetup(int teleItem, boolean useExpeditious, boolean alch, boolean useCannon,
                                      int itemToEquip, boolean slayerHelmSub, int... otherItems) {

        generalGloryAndExpeditious(useExpeditious);
        checkBraceletOfSlaughter();
        if (itemToEquip != -1 && !Equipment.isEquipped(itemToEquip)) { /// need an item and it's not equipped
            General.println("[Bank]: Need to equip item - Slayer helm subs? " + slayerHelmSub);
            if (!slayerHelmSub && BankManager.withdraw(1, true, itemToEquip)) {
                RSItem[] item = Inventory.find(itemToEquip);

                if (item.length > 0) {
                    BankManager.close(true);

                    if (item[0].click())
                        Timer.waitCondition(() -> Equipment.isEquipped(itemToEquip), 2500, 5000);

                    BankManager.open(true);

                } else if (Banking.find(itemToEquip).length == 0) {

                    if (itemToEquip == ItemId.BOOTS_OF_STONE) {
                        SlayerVars.get().needBootsOfStone = true;
                        SlayerVars.get().slayerShopRestock = true;
                        General.println("[Debug]: Missing Boots of stone, need to slayer restock");
                        return;

                    } else {
                        General.println("[Debug]: Missing an item, ending");
                        SlayerVars.get().script = false;
                        return;
                    }
                }
            }
        }
        BankManager.depositAll(true);

        if (otherItems != null) {
            for (int i : otherItems)
                BankManager.withdraw(1, true, i);
        }

        if (useCannon) {
            BankManager.withdraw(750, true, ItemId.CANNONBALL);
            BankManager.withdraw(1, true, 6);
            BankManager.withdraw(1, true, 8);
            BankManager.withdraw(1, true, 10);
            BankManager.withdraw(1, true, 12);
            if (Inventory.find(ItemId.CANNON_IDS).length < 4) {
                General.println("[bank]: We're missing parts of the cannon, turning it to false");
                SlayerVars.get().use_cannon = false;
            }
        }

        if (alch) {
            BankManager.withdraw(50, true, ItemId.NATURE_RUNE);
            BankManager.withdraw(250, true, ItemId.FIRE_RUNE);
        }
        BankManager.withdraw(5000, true, ItemId.COINS);
        BankManager.withdraw(2, true, ItemId.VARROCK_TELEPORT);
        BankManager.withdraw(1, true, teleItem);

        if (!Equipment.isEquipped(ItemId.SLAYER_HELM) && !Equipment.isEquipped(ItemId.SLAYER_HELM_I))
            BankManager.withdraw(1, true, ItemId.ENCHANTED_GEM);

        BankManager.withdrawArray(ItemId.STAMINA_POTION, 1);
        BankManager.withdrawArray(ItemId.SUPER_COMBAT_POTION, 1);
        BankManager.withdraw(0, true, SlayerVars.get().customFoodId);

        if (!BankManager.checkInventoryItems(SlayerVars.get().customFoodId)) {
            General.println("[Bank]: restocking due to food missing");
            SlayerVars.get().shouldRestock = true;
            return;
        }
    }


    public void generalInventorySetup(int teleItem, boolean useExpeditious, boolean alch,
                                      int itemToEquip, boolean slayerHelmSub, int... otherItems) {

        generalGloryAndExpeditious(useExpeditious);
        checkBraceletOfSlaughter();

        if (itemToEquip != -1 && !Equipment.isEquipped(itemToEquip)) { /// need an item and it's not equipped
            General.println("[Bank]: Need to equip item - Slayer helm subs? " + slayerHelmSub);

            BankManager.open(true);
            if ((!slayerHelmSub || !isWearingSlayerHelm()) && BankManager.withdraw(1, true, itemToEquip)) {
                RSItem[] item = Inventory.find(itemToEquip);

                if (item.length > 0) {
                    BankManager.close(true);

                    if (item[0].click())
                        Timer.waitCondition(() -> Equipment.isEquipped(itemToEquip), 2500, 5000);

                    BankManager.open(true);

                } else if (Banking.find(itemToEquip).length == 0) {

                    if (itemToEquip == ItemId.BOOTS_OF_STONE) {
                        SlayerVars.get().needBootsOfStone = true;
                        SlayerVars.get().slayerShopRestock = true;
                        General.println("[Debug]: Missing Boots of stone, need to slayer restock");
                        return;

                    } else {
                        General.println("[Debug]: Missing an item, ending");
                        SlayerVars.get().script = false;
                        return;
                    }
                }
            }
        }
        BankManager.depositAll(true);

        if (otherItems != null) {
            for (int i : otherItems)
                BankManager.withdraw(1, true, i);
        }

        if (alch) {
            BankManager.withdraw(50, true, ItemId.NATURE_RUNE);
            BankManager.withdraw(250, true, ItemId.FIRE_RUNE);
        }

        BankManager.withdraw(2, true, ItemId.VARROCK_TELEPORT);
        BankManager.withdraw(1, true, teleItem);

        if (!Equipment.isEquipped(ItemId.SLAYER_HELM) && !Equipment.isEquipped(ItemId.SLAYER_HELM_I))
            BankManager.withdraw(1, true, ItemId.ENCHANTED_GEM);

        BankManager.withdrawArray(ItemId.STAMINA_POTION, 1);
        BankManager.withdrawArray(ItemId.SUPER_COMBAT_POTION, 1);
        BankManager.withdraw(0, true, SlayerVars.get().customFoodId);

        if (!BankManager.checkInventoryItems(SlayerVars.get().customFoodId)) {
            General.println("[Bank]: restocking due to food missing");
            SlayerVars.get().shouldRestock = true;
            return;
        }
    }

    public void generalInventorySetup(int[] teleItem, boolean useExpeditious, boolean alch,
                                      int itemToEquip, int... otherItems) {
        generalGloryAndExpeditious(useExpeditious);
        checkBraceletOfSlaughter();
        if (itemToEquip != -1 && !Equipment.isEquipped(itemToEquip)) {

            if (BankManager.withdraw(1, true, itemToEquip)) {
                RSItem[] item = Inventory.find(itemToEquip);
                if (item.length > 0) {
                    BankManager.close(true);
                    if (item[0].click())
                        Timer.waitCondition(() -> Equipment.isEquipped(itemToEquip), 2500, 5000);
                    BankManager.open(true);
                }

            } else if (Banking.find(itemToEquip).length == 0) {
                if (itemToEquip == ItemId.BOOTS_OF_STONE) {
                    SlayerVars.get().needBootsOfStone = true;
                    SlayerVars.get().slayerShopRestock = true;
                    General.println("[Debug]: Missing Boots of stone, need to slayer restock");
                    return;
                } else {
                    General.println("[Debug]: Missing item, ending");
                    SlayerVars.get().script = false;
                    return;
                }
            }
        }
        BankManager.depositAll(true);
        if (otherItems != null) {
            for (int i : otherItems)
                BankManager.withdraw(1, true, i);
        }

        if (alch) {
            BankManager.withdraw(50, true, ItemId.NATURE_RUNE);
            BankManager.withdraw(250, true, ItemId.FIRE_RUNE);
        }

        BankManager.withdraw(2, true, ItemId.VARROCK_TELEPORT);
        BankManager.withdrawArray(teleItem, 1);

        if (!Equipment.isEquipped(ItemId.SLAYER_HELM) && !Equipment.isEquipped(ItemId.SLAYER_HELM_I))
            BankManager.withdraw(1, true, ItemId.ENCHANTED_GEM);

        BankManager.withdrawArray(ItemId.STAMINA_POTION, 1);
        BankManager.withdrawArray(ItemId.SUPER_COMBAT_POTION, 1);
        BankManager.withdraw(0, true, SlayerVars.get().customFoodId);

        if (!BankManager.checkInventoryItems(SlayerVars.get().customFoodId)) {
            General.println("[Bank]: restocking due to food missing");
            SlayerVars.get().shouldRestock = true;
            return;
        }
    }

    public void generalInventorySetup(int teleTab, boolean useExpeditious, boolean alch,
                                      int itemToEquip, boolean slayerHelmSub, HashMap<Integer, Integer> itemMap) {
        generalGloryAndExpeditious(useExpeditious);
        checkBraceletOfSlaughter();
        if (itemToEquip != -1 && !Equipment.isEquipped(itemToEquip)) {
            if (BankManager.withdraw(1, true, itemToEquip)) {
                RSItem[] item = Inventory.find(itemToEquip);
                if (item.length > 0) {
                    BankManager.close(true);
                    if (item[0].click())
                        Timer.waitCondition(() -> Equipment.isEquipped(itemToEquip), 2500, 5000);
                    BankManager.open(true);
                }

            } else if (Banking.find(itemToEquip).length == 0) {
                if (itemToEquip == ItemId.BOOTS_OF_STONE) {
                    SlayerVars.get().needBootsOfStone = true;
                    SlayerVars.get().slayerShopRestock = true;
                    General.println("[Debug]: Missing Boots of stone, need to slayer restock");
                    return;
                } else {
                    General.println("[Debug]: Missing item, ending");
                    SlayerVars.get().script = false;
                    return;
                }
            }
        }
        BankManager.depositAll(true);
        if (itemMap != null) {
            for (int i : itemMap.keySet()) {
                BankManager.withdraw(itemMap.get(i), true, i);
            }
        }

        if (alch) {
            BankManager.withdraw(50, true, ItemId.NATURE_RUNE);
            BankManager.withdraw(250, true, ItemId.FIRE_RUNE);
        }

        BankManager.withdraw(2, true, ItemId.VARROCK_TELEPORT);
        if (teleTab != ItemId.VARROCK_TELEPORT)
            BankManager.withdraw(2, true, teleTab);

        if (!isWearingSlayerHelm())
            BankManager.withdraw(1, true, ItemId.ENCHANTED_GEM);

        BankManager.withdrawArray(ItemId.STAMINA_POTION, 1);
        BankManager.withdrawArray(ItemId.SUPER_COMBAT_POTION, 1);
        BankManager.withdraw(0, true, SlayerVars.get().customFoodId);

        if (!BankManager.checkInventoryItems(SlayerVars.get().customFoodId)) {
            General.println("[Bank]: restocking due to food missing");
            SlayerVars.get().shouldRestock = true;
            return;
        }
    }

    public void generalInventorySetup(int teleTab, boolean useExpeditious, boolean alch,
                                      int itemToEquip, int... otherItems) {
        generalGloryAndExpeditious(useExpeditious);
        checkBraceletOfSlaughter();
        if (itemToEquip != -1 && !Equipment.isEquipped(itemToEquip)) {
            if (BankManager.withdraw(1, true, itemToEquip)) {
                RSItem[] item = Inventory.find(itemToEquip);
                if (item.length > 0) {
                    BankManager.close(true);
                    if (item[0].click())
                        Timer.waitCondition(() -> Equipment.isEquipped(itemToEquip), 2500, 5000);
                    BankManager.open(true);
                }

            } else if (Banking.find(itemToEquip).length == 0) {
                if (itemToEquip == ItemId.BOOTS_OF_STONE) {
                    SlayerVars.get().needBootsOfStone = true;
                    SlayerVars.get().slayerShopRestock = true;
                    General.println("[Debug]: Missing Boots of stone, need to slayer restock");
                    return;
                } else {
                    General.println("[Debug]: Missing item, ending");
                    SlayerVars.get().script = false;
                    return;
                }
            }
        }
        BankManager.depositAll(true);
        if (otherItems != null) {
            for (int i : otherItems)
                BankManager.withdraw(1, true, i);
        }

        if (alch) {
            BankManager.withdraw(50, true, ItemId.NATURE_RUNE);
            BankManager.withdraw(250, true, ItemId.FIRE_RUNE);
        }

        BankManager.withdraw(2, true, ItemId.VARROCK_TELEPORT);
        if (teleTab != ItemId.VARROCK_TELEPORT)
            BankManager.withdraw(2, true, teleTab);

        if (!Equipment.isEquipped(ItemId.SLAYER_HELM) && !Equipment.isEquipped(ItemId.SLAYER_HELM_I))
            BankManager.withdraw(1, true, ItemId.ENCHANTED_GEM);

        BankManager.withdrawArray(ItemId.STAMINA_POTION, 1);
        BankManager.withdrawArray(ItemId.SUPER_COMBAT_POTION, 1);
        BankManager.withdraw(0, true, SlayerVars.get().customFoodId);

        if (!BankManager.checkInventoryItems(SlayerVars.get().customFoodId)) {
            General.println("[Bank]: restocking due to food missing");
            SlayerVars.get().shouldRestock = true;
            return;
        }
    }

    public static void generalInventorySetup(int teleTab, int openSpaces) { // has 3 open spaces
        BankManager.open(true);
        checkBraceletOfSlaughter();
        if (!generalGloryAndExpeditious(SlayerVars.get().useExpeditiousBracelet))
            return;
        checkExpeditiousBracelet();
        BankManager.depositAll(true);
        BankManager.withdrawArray(ItemId.SUPER_COMBAT_POTION, 1);
        BankManager.withdraw(1, true, ItemId.ENCHANTED_GEM);
        BankManager.withdraw(true, 2, teleTab);
        BankManager.withdraw(true, 1, ItemId.VARROCK_TELEPORT);
        BankManager.withdrawArray(ItemId.STAMINA_POTION, 1);
        BankManager.withdraw(true, 22 - openSpaces, ItemId.MONKFISH);
    }


    public void getAndEquipItem(int itemQuantity, int itemID, String method) { // need a check for having inventory items
        if (Inventory.find(itemID).length < 1) {
            BankManager.open(true);
            RSItem[] bankCount = Banking.find(itemID);
            if (bankCount.length > 0 && bankCount[0].getStack() >= itemQuantity) {
                General.println("[Debug]: Getting: " + itemID + " x " + itemQuantity);
                BankManager.withdraw(itemQuantity, true, itemID);
                General.sleep(General.randomSD(400, 1000, 400, 100));
            } else
                General.println("[Debug]: Missing item: " + itemID);
        }
        RSItem[] item = Inventory.find(itemID);
        if (item.length > 0) {
            BankManager.close(true);
            if (item[0].click()) {
                Timer.waitCondition(() -> Equipment.find(itemID).length > 0, 3000);
                General.sleep(General.random(300, 1200));
            }
        }
    }

    /**
     * needs to be called while bank is open
     *
     * @return
     */
    private static boolean checkExpeditiousBracelet() {
        if (SlayerVars.get().useExpeditiousBracelet) {
            if (Equipment.find(ItemId.EXPEDITIOUS_BRACELET).length < 1) {
                General.println("[BankManager]: Need to replace Expeditious Bracelet.");
                BankManager.withdraw(1, true, ItemId.EXPEDITIOUS_BRACELET);
                Utils.equipItem(ItemId.EXPEDITIOUS_BRACELET);
                return Timer.waitCondition(() -> Equipment.isEquipped(ItemId.EXPEDITIOUS_BRACELET), 2500, 3500);
            }
        }
        return false;
    }


    public static boolean checkEquippedGlory() {
        if (!Equipment.isEquipped(ItemId.AMULET_OF_GLORY)) {
            BankManager.withdrawArray(ItemId.AMULET_OF_GLORY, 1);
            Timer.waitCondition(() -> Inventory.find(ItemId.AMULET_OF_GLORY).length > 0, 2500, 4000);
            RSItem[] invGlory = Inventory.find(ItemId.AMULET_OF_GLORY);
            if (invGlory.length > 0) {
                if (invGlory[0].click("Wear"))
                    return Timer.waitCondition(() -> Equipment.isEquipped(ItemId.AMULET_OF_GLORY), 2500, 4000);
            } else
                return false;
        }
        return true;
    }


    /*****************************
     * **NPC-Specific Inventories**
     ******************************/
    public void abberantSpectreInventory() {
        equipSlayerItem(ItemId.NOSE_PEG, true);
        generalInventorySetup(ItemId.VARROCK_TELEPORT, 10);
        BankManager.withdrawArray(ItemId.PRAYER_POTION, 10);

    }

    private boolean equipItem(int itemId) {
        RSItem[] inv = Inventory.find(itemId);
        if (inv.length > 0) {
            if (Banking.isBankScreenOpen()) {
                BankManager.close(true);
            }
            if (inv[0].click())
                return Timer.waitCondition(() -> Equipment.isEquipped(itemId), 3000, 5000);
        }
        return false;
    }

    private boolean equipSlayerItem(int itemId, boolean isSlayerHelmASubistitute) {
        if (isSlayerHelmASubistitute && (Equipment.isEquipped(ItemId.SLAYER_HELM) || Equipment.isEquipped(ItemId.SLAYER_HELM_I)))
            return true;

        if (Equipment.isEquipped(itemId))
            return true;

        else {
            if (equipItem(itemId))
                return true;

            else {
                BankManager.open(true);
                BankManager.withdraw(1, true, itemId);
                BankManager.close(true);
                return equipItem(itemId);
            }
        }
    }

    public void bansheeInventory() {
        if (!isEquipped(ItemId.EAR_MUFFS) && !isEquipped(ItemId.SLAYER_HELM)
                && !isEquipped(ItemId.SLAYER_HELM_I)) { // not wearing
            getAndEquipItem(1, ItemId.EAR_MUFFS, "Wear");
            Utils.shortSleep();
        }
        if (isEquipped(ItemId.EAR_MUFFS) || isEquipped(ItemId.SLAYER_HELM) || isEquipped(ItemId.SLAYER_HELM_I))
            generalInventorySetup(ItemId.VARROCK_TELEPORT);

    }

    public void turothInventory() {
        if (!isEquipped(ItemId.LEAFBLADE_BATTLEAXE)) {
            equipSlayerItem(ItemId.LEAFBLADE_BATTLEAXE, false);
            Utils.shortSleep();
        }
        if (isEquipped(ItemId.LEAFBLADE_BATTLEAXE))
            generalInventorySetup(ItemId.CAMELOT_TELEPORT);

    }


    public void blueDragonInventory() {
        generalInventorySetup(ItemId.FALADOR_TELEPORT, true, false, true,
                ItemId.ANTIDRAGON_SHIELD, false, ItemId.DUSTY_KEY);

        if (Inventory.find(ItemId.DUSTY_KEY).length == 0) {
            General.println("[Debug]: We do not have a dusty key, will get one for task.");
            SlayerVars.get().needDustyKey = true;
        }
    }

    public void blackDragonInventory() {
        generalInventorySetup(ItemId.FALADOR_TELEPORT, true, false,
                ItemId.ANTIDRAGON_SHIELD, false, ItemId.DUSTY_KEY);

        if (Inventory.find(ItemId.DUSTY_KEY).length == 0) {
            General.println("[Debug]: We do not have a dusty key, will get one for task.");
            SlayerVars.get().needDustyKey = true;
        }
    }

    public void greenDragonInventory() {
        generalInventorySetup(ItemId.BURNING_AMULET, true, false,
                ItemId.ANTIDRAGON_SHIELD, ItemId.LOOTING_BAG);
    }


    private void lightCandle() {
        if (Inventory.find(ItemId.CANDLE).length > 0) {
            BankManager.close(true);
            Utils.useItemOnItem(ItemId.CANDLE, ItemId.TINDERBOX);
            Utils.shortSleep();
        }
    }

    public void caveSlimeInventory() {
        if (!equipSlayerItem(ItemId.SPINY_HELM, true)) {
            SlayerVars.get().shouldRestock = true;
            return;
        }
        generalInventorySetup(ItemId.LUMBRIDGE_TELEPORT, 6);
        if (!BankManager.withdraw(1, true, ItemId.LIT_CANDLE))
            if (!BankManager.withdraw(1, true, ItemId.CANDLE)) {
                General.println("[Debug]: Missing Candle, need to restock");
                SlayerVars.get().shouldRestock = true;
                return;
            }
        BankManager.withdraw(1, ItemId.TINDERBOX);
        BankManager.withdraw(1, true, ItemId.ANTIDOTE_PLUS_PLUS);

        lightCandle();
    }

    public void caveBugInventory() {
        if (!equipSlayerItem(ItemId.SPINY_HELM, true)) {
            SlayerVars.get().shouldRestock = true;
            return;
        }
        generalInventorySetup(ItemId.LUMBRIDGE_TELEPORT, 6);
        if (!BankManager.withdraw(1, true, ItemId.LIT_CANDLE))
            if (!BankManager.withdraw(1, true, ItemId.CANDLE)) {
                General.println("[Debug]: Missing Candle, need to restock");
                SlayerVars.get().shouldRestock = true;
                return;
            }
        BankManager.withdraw(1, ItemId.TINDERBOX);

        lightCandle();
    }

    public void crocodileInventory() {
        BankManager.open(true);
        if (!generalGloryAndExpeditious(SlayerVars.get().useExpeditiousBracelet))
            return;
        BankManager.depositAll(true);
        BankManager.withdraw(1, ItemId.ENCHANTED_GEM);
        BankManager.withdraw(2, ItemId.SHANTAY_PASS);
        BankManager.withdraw(2, ItemId.VARROCK_TELEPORT);
        if (!BankManager.checkInventoryItems(ItemId.SHANTAY_PASS))
            SlayerVars.get().shouldRestock = true;
        BankManager.withdraw(1, ItemId.SUPER_STRENGTH[0]);
        BankManager.withdraw(1, ItemId.STAMINA_POTION[2]);
        BankManager.withdraw(10, ItemId.WATERSKIN[0], true);
        BankManager.withdraw(14, true, ItemId.MONKFISH);
        Utils.microSleep();
    }

    public void lizardInventory() {
        BankManager.open(true);
        if (!generalGloryAndExpeditious(SlayerVars.get().useExpeditiousBracelet))
            return;
        BankManager.depositAll(true);
        BankManager.withdraw(2, true, ItemId.VARROCK_TELEPORT);
        BankManager.withdraw(1, true, ItemId.ENCHANTED_GEM);
        if (!BankManager.withdraw(150, true, ItemId.ICE_COOLER)) {
            SlayerVars.get().slayerShopRestock = true;
            return;
        }
        BankManager.withdraw(2, true, ItemId.SHANTAY_PASS);

        BankManager.withdraw(1, true, ItemId.SUPER_STRENGTH[0]);
        if (!BankManager.checkInventoryItems(ItemId.SHANTAY_PASS)) {
            General.println("[Debug]: Need to restock due to no shantay passes");
            SlayerVars.get().shouldRestock = true;
            return;
        }
        BankManager.withdraw(1, true, ItemId.STAMINA_POTION[2]);
        BankManager.withdrawArray(ItemId.WATERSKIN, 8);
        Timer.waitCondition(() -> Inventory.find(ItemId.WATERSKIN).length > 5, 3000);
        if (Inventory.find(ItemId.WATERSKIN).length < 5) {
            General.println("[Debug]; Restocking due to lack of waterskins");
            SlayerVars.get().shouldRestock = true;
            return;
        } else {
            SlayerVars.get().shouldRestock = false;
        }
        BankManager.withdraw(0, true, ItemId.MONKFISH);
    }


    public void rockSlugInventory() {
        if (!isEquipped(ItemId.BRINE_SABER)) { // not wearing item
            BankManager.open(true);
            if (Banking.find(ItemId.BRINE_SABER).length > 0) {
                General.println("[Debug]: Brine sabre detected.");
                getAndEquipItem(1, ItemId.BRINE_SABER, "Wield");
                generalInventorySetup(ItemId.CAMELOT_TELEPORT);

            } else {
                BankManager.open(true);
                newInventorySetup(ItemId.CAMELOT_TELEPORT, true,
                        new ItemReq(ItemId.BAG_OF_SALT, 150, 50));
            }
        } else {
            generalInventorySetup(ItemId.CAMELOT_TELEPORT);
        }
    }


    public void greaterDemonInventory() {
        BankManager.open(true);
        if (!generalGloryAndExpeditious(SlayerVars.get().useExpeditiousBracelet))
            return;
        BankManager.depositAll(true);
        BankManager.withdraw(12, true, ItemId.PRAYER_POTION[0]);
        if (Inventory.find(ItemId.PRAYER_POTION[0]).length < 6) {
            SlayerVars.get().shouldRestock = true;
            return;
        }
        BankManager.withdraw(1, true, ItemId.SKILLS_NECKLACE);
        BankManager.withdraw(1, true, ItemId.ENCHANTED_GEM);
        BankManager.withdraw(8, true, ItemId.MONKFISH);
        BankManager.withdraw(2, true, ItemId.SUPER_STRENGTH);
        BankManager.withdraw(1, true, ItemId.STAMINA_POTION);
        BankManager.withdraw(1, true, ItemId.VARROCK_TELEPORT);
        BankManager.withdraw(1, true, ItemId.EXPEDITIOUS_BRACELET);
    }

    public void dagannothInventory() {
        BankManager.open(true);
        if (!generalGloryAndExpeditious(SlayerVars.get().useExpeditiousBracelet))
            return;

        BankManager.depositAll(true);
        if (Banking.find(ItemId.CANNON_IDS).length > 0 && SlayerVars.get().use_cannon) {
            BankManager.withdraw(750, true, ItemId.CANNONBALL);
            BankManager.withdraw(1, true, 6);
            BankManager.withdraw(1, true, 8);
            BankManager.withdraw(1, true, 10);
            BankManager.withdraw(1, true, 12);
        } else {
            General.println("[Debug]; Setting cannoning to false b/c we don't have a cannon");
            SlayerVars.get().use_cannon = false;
        }

        BankManager.withdrawArray(ItemId.GAMES_NECKLACE, 1);
        BankManager.withdraw(1, true, ItemId.ENCHANTED_GEM);
        BankManager.withdrawArray(ItemId.SUPER_COMBAT_POTION, 1);
        BankManager.withdraw(1, true, ItemId.STAMINA_POTION);
        BankManager.withdraw(1, true, ItemId.VARROCK_TELEPORT);
        BankManager.withdraw(0, true, ItemId.MONKFISH);
    }

    public void suqahInventory() {
        BankManager.open(true);
        if (!generalGloryAndExpeditious(SlayerVars.get().useExpeditiousBracelet))
            return;

        BankManager.depositAll(true);
        if (Banking.find(ItemId.CANNON_IDS).length > 0 && SlayerVars.get().use_cannon) {
            BankManager.withdraw(750, true, ItemId.CANNONBALL);
            BankManager.withdraw(1, true, 6);
            BankManager.withdraw(1, true, 8);
            BankManager.withdraw(1, true, 10);
            BankManager.withdraw(1, true, 12);
        } else {
            General.println("[Debug]; Setting cannoning to false b/c we don't have a cannon");
            SlayerVars.get().use_cannon = false;
        }

        BankManager.withdraw(1, true, ItemId.LUNAR_ISLE_TELEPORT);
        if (!isWearingSlayerHelm())
            BankManager.withdraw(1, true, ItemId.ENCHANTED_GEM);
        BankManager.withdrawArray(ItemId.SUPER_COMBAT_POTION, 1);

        BankManager.withdraw(2, true, ItemId.VARROCK_TELEPORT);
        BankManager.withdraw(5, true, ItemId.PRAYER_POTION[0]);
        BankManager.withdraw(0, true, ItemId.MONKFISH);
    }

    public static boolean checkBraceletOfSlaughter() {
        if (SlayerVars.get().useBraceletOfSlaughter && !isEquipped(ItemId.BRACELET_OF_SLAUGHTER)) {
            General.println("[Bank]: Equipping bracelet of slaughter");
            BankManager.open(true);
            BankManager.withdraw(1, true, ItemId.BRACELET_OF_SLAUGHTER);
            Utils.equipItem(ItemId.BRACELET_OF_SLAUGHTER, "Wear");
            return Timer.waitCondition(() -> isEquipped(ItemId.BRACELET_OF_SLAUGHTER), 2500, 3500);
        }
        return false;
    }


    public void gargoyleInventory() {
        generalInventorySetup(ItemId.SALVE_GRAVEYARD_TELEPORT,
                false, true, -1, false,
                ItemId.ROCK_HAMMER, ItemId.SALVE_GRAVEYARD_TELEPORT);
        if (!BankManager.checkInventoryItems(ItemId.ROCK_HAMMER)) {
            General.println("[Bank]: Missing a rock hammer, going to buy one from slayer shop");
            SlayerVars.get().slayerShopRestock = true;
            SlayerVars.get().needRockHammer = true;
            BankManager.depositAll(true);
        }
    }


    public String[] specialItemNPCs = {
            "abberant spectre",
            "banshee",
            "basilisk",
            "black demon",
            "bloodveld",
            "black dragon",
            "blue dragon",
            "red dragon",
            "iron dragon",
            "green dragon",
            "cockatrice",
            "cave slime",
            "cave crawler",
            "gargoyle",
            "elves",
            "greater demon",
            "wyrm"

    };

    public static void checkSpecial() {
        if (SlayerVars.get().targets != null) {
            String NPC = SlayerVars.get().targets[0].toLowerCase();

            if (NPC.contains("aberrant spectre") && !Equipment.isEquipped(ItemId.NOSE_PEG)
                    && !Equipment.isEquipped(ItemId.SLAYER_HELM)
                    && !Equipment.isEquipped(ItemId.SLAYER_HELM_I)) {
                General.println("[Bank]: Need to bank for aberrant Spectres inventory");
                SlayerVars.get().shouldBank = true;
                SlayerVars.get().usingSpecialItem = true;


            } else if (NPC.contains("banshee") && (Equipment.find(ItemId.EAR_MUFFS).length < 1 &&
                    !isWearingSlayerHelm())) {
                General.println("[Debug]: Need to bank for Banshee item");
                SlayerVars.get().shouldBank = true;
                General.println("[Bank]: Using a special item for this Assignment");
                SlayerVars.get().usingSpecialItem = true;

            } else if (NPC.contains("basilisk") && Equipment.find(ItemId.MIRROR_SHIELD).length < 1) {
                General.println("[Debug]: Need to bank for Basilisk item");
                SlayerVars.get().shouldBank = true;
                General.println("[Bank]: Using a special item for this Assignment");
                SlayerVars.get().usingSpecialItem = true;

            } else if (NPC.contains("black demon") && Inventory.find(ItemId.PRAYER_POTION).length == 0) {
                General.println("[Debug]: Need to bank for Black demon");
                SlayerVars.get().shouldBank = true;
                General.println("[Bank]: Using a special item for this Assignment");
                SlayerVars.get().usingSpecialItem = true;

            } else if (NPC.contains("bloodveld") && Equipment.find(Filters.Items.nameContains("d'hide")).length < 1) {
                General.println("[Debug]: Need to bank for Bloodveld item (D'hide)");
                SlayerVars.get().shouldBank = true;
                SlayerVars.get().usingSpecialItem = true;
                SlayerVars.get().magicMeleeGear = true;
            } else if (NPC.contains("black dragon") && !Equipment.isEquipped(ItemId.ANTIDRAGON_SHIELD)) {
                General.println("[Debug]: Need to bank for black dragon item(s)");
                SlayerVars.get().shouldBank = true;
                SlayerVars.get().usingSpecialItem = true;

            } else if (NPC.contains("blue dragon") && !Equipment.isEquipped(ItemId.ANTIDRAGON_SHIELD)) {
                General.println("[Debug]: Need to bank for blue dragon item");
                SlayerVars.get().shouldBank = true;
                SlayerVars.get().usingSpecialItem = true;

            } else if (NPC.contains("iron dragon")) {
                General.println("[Debug]: Need to bank for Iron dragon item(s)");
                SlayerVars.get().shouldBank = false;
                SlayerVars.get().shouldSkipTask = true;
                return;

            } else if (NPC.contains("green dragon") && !Equipment.isEquipped(ItemId.ANTIDRAGON_SHIELD)) {
                General.println("[Debug]: Need to bank for green dragon item");
                SlayerVars.get().shouldBank = false;
                SlayerVars.get().shouldSkipTask = true;
                return;


            } else if (NPC.contains("cockatrice") && Equipment.find(ItemId.MIRROR_SHIELD).length < 1) {
                General.println("[Debug]: Need to bank for cockatrice item");
                SlayerVars.get().shouldBank = true;
                SlayerVars.get().usingSpecialItem = true;

            } else if (NPC.contains("dog") && Inventory.find(ItemId.COINS).length ==0) {
                General.println("[Debug]: Need to bank for Dog item");
                SlayerVars.get().shouldBank = true;

            }else if ((NPC.contains("cave slime") || NPC.contains("cave bug")) &&
                    (Inventory.find(ItemId.LIT_CANDLE).length < 1
                    || Inventory.find(ItemId.TINDERBOX).length < 1)) {
                General.println("[Debug]: Need to bank for cave item");
                SlayerVars.get().shouldBank = true;
                SlayerVars.get().usingSpecialItem = true;
                return;

            } else if (NPC.contains("cave crawler") && (Inventory.find(ItemId.ANTIDOTE_PLUS_PLUS).length < 1)) {
                General.println("[Debug]: Need to bank for cave crawler item");
                SlayerVars.get().shouldBank = true;
                SlayerVars.get().usingSpecialItem = true;

            } else if (NPC.contains("gargoyle") && Inventory.find(ItemId.ROCK_HAMMER).length < 1) {
                General.println("[Debug]: Need to bank for gargoyle item");
                SlayerVars.get().shouldBank = true;

            } else if (NPC.contains("elves") && Inventory.find(ItemId.IORWERTH_CAMP_TELEPORT).length < 1) {
                General.println("[Debug]: Need to bank for elf item");
                SlayerVars.get().shouldBank = true;

            } else if (NPC.contains("greater demon") && Inventory.find(ItemId.PRAYER_POTION).length < 1) {
                General.println("[Debug]: Need to bank for Greater demon item");
                SlayerVars.get().shouldBank = true;
                SlayerVars.get().shouldPrayMelee = true;
                SlayerVars.get().usingSpecialItem = true;

            } else if (NPC.contains("harpie bug swarm") && Equipment.find(ItemId.LIT_BUG_LANTERN).length < 1) {
                General.println("[Debug]: Need to bank for Harpie item");
                SlayerVars.get().shouldBank = true;
                SlayerVars.get().usingSpecialItem = true;

            } else if (NPC.contains("kalphite") && Inventory.find(ItemId.SHANTAY_PASS).length < 1
                    && !Areas.KALPHITE_AREA.contains(Player.getPosition())) {
                General.println("[Debug]: Need to bank for Kalphite item");
                SlayerVars.get().shouldBank = true;

            } else if (NPC.contains("lizard") && (Inventory.find(ItemId.ICE_COOLER).length < 1
                    || Inventory.find(ItemId.SHANTAY_PASS).length < 1)) {
                General.println("[Debug]: Need to bank for Lizard item");
                SlayerVars.get().shouldBank = true;

            } else if (NPC.contains("mutated zygomite") && Inventory.find(ItemId.FUNGICIDE_SPRAYER).length < 1) {
                General.println("[Debug]: Need to bank for Zygomite item");
                SlayerVars.get().shouldBank = true;

            } else if (NPC.contains("rockslug") && (Inventory.find(ItemId.BAG_OF_SALT).length < 1 &&
                    Equipment.find(ItemId.BRINE_SABER).length < 1)) {
                General.println("[Debug]: Need to bank for rockslug item");
                SlayerVars.get().shouldBank = true;

            } else if (NPC.contains("turoth") && Equipment.find(ItemId.LEAFBLADE_BATTLEAXE).length < 1) {
                General.println("[Debug]: Need to bank for turoth item");
                SlayerVars.get().shouldBank = true;
                SlayerVars.get().usingSpecialItem = true;

            } else if (NPC.contains("kurask") && Equipment.find(ItemId.LEAFBLADE_BATTLEAXE).length < 1) {
                General.println("[Debug]: Need to bank for kruask item");
                SlayerVars.get().shouldBank = true;
                SlayerVars.get().usingSpecialItem = true;

            } else if (NPC.contains("wall beast") &&
                    (
                            (Inventory.find(ItemId.LIT_CANDLE).length < 1) ||
                            Inventory.find(ItemId.TINDERBOX).length < 1)) {
                General.println("[Debug]: Need to bank for wall beast item");
                SlayerVars.get().shouldBank = true;
                SlayerVars.get().usingSpecialItem = true;

            } else if (NPC.contains("wyrm") && Equipment.find(ItemId.BOOTS_OF_STONE).length < 1) {
                General.println("[Debug]: Need to bank for Wyrm item");
                SlayerVars.get().shouldBank = true;
                SlayerVars.get().usingSpecialItem = true;

            } else if (NPC.contains("sourhog") && !isWearingSlayerHelm()
                    && !Equipment.isEquipped(ItemId.REINFORCED_GOGGLES)) {
                General.println("[Debug]: Need to bank for Sourhog item");
                SlayerVars.get().shouldBank = true;
                SlayerVars.get().usingSpecialItem = true;

            } else if (NPC.contains("cave bug")) {
                SlayerVars.get().usingSpecialItem = true;
                if (Inventory.find(ItemId.LIT_CANDLE).length < 1
                        || Inventory.find(ItemId.TINDERBOX).length < 1) {
                    General.println("[Debug]: Need to bank for Cave bug item");
                    SlayerVars.get().shouldBank = true;

                }
            } else if (NPC.contains("bronze dragon")) {
                SlayerVars.get().shouldSkipTask = true;

            } else {
                SlayerVars.get().wallbeastTask = false; // might not go here, idk man
                SlayerVars.get().magicMeleeGear = false;
                SlayerVars.get().shouldBank = false;
            }
        }
    }

    private static boolean isWearingSlayerHelm() {
        return Equipment.isEquipped(ItemId.SLAYER_HELM) || Equipment.isEquipped(ItemId.SLAYER_HELM_I);
    }

    public boolean checkSpecialEquipment(String NPC, String npcContains, int... equipmentIds) {
        if (NPC.contains(npcContains)) {
            SlayerVars.get().usingSpecialItem = true;
            for (int i : equipmentIds) {
                if (!Equipment.isEquipped(i))
                    return SlayerVars.get().shouldBank = true;
            }
            return SlayerVars.get().usingSpecialItem = true;
        }
        return false;
    }


    private boolean shouldEatAtBank() {
        return Combat.getHPRatio() < 85;
    }

    private int missingHP() {
        int current = Combat.getHP();
        int max = Combat.getMaxHP();

        return max - current;
    }

    public void eatAtBank() {
        if (shouldEatAtBank()) {
            if (EatUtil.eatFood()) {
                return;
            } else {
                int monkfishToGet = Math.round(missingHP() / 16);
                General.println("[Banking]: Withdrawing " + monkfishToGet + " monkfish to heal at bank");
                BankManager.open(true);
                BankManager.withdraw(monkfishToGet, true, ItemId.MONKFISH);
                for (int i = 0; i < monkfishToGet; i++) {
                    EatUtil.eatFood();
                    Utils.microSleep();
                }

            }
        }
    }

    public static void mutatedZygomiteInventory() {
        generalInventorySetup(ItemId.LUMBRIDGE_TELEPORT, 5);
        BankManager.withdrawArray(ItemId.FUNGICIDE_SPRAYER, 4);
        BankManager.withdraw(1, true, ItemId.DRAMEN_STAFF);

    }

    private void wyrmInventory() {
        equipSlayerItem(ItemId.BOOTS_OF_STONE, false);
        generalInventorySetup(ItemId.VARROCK_TELEPORT, 10);
        BankManager.withdrawArray(ItemId.PRAYER_POTION, 10);
        BankManager.withdrawArray(ItemId.SKILLS_NECKLACE, 1);

    }

    public void selectLoadOut() {
        if (SlayerVars.get().targets != null) {
            String NPC = SlayerVars.get().targets[0].toLowerCase();

            General.sleep(50);
            if (NPC.contains("aberrant spectre")) {
                General.println("[Debug]: Setting Abberent Spectre Inventory and Nose Plug");
                abberantSpectreInventory();
                SlayerVars.get().shouldPrayMagic = true;
            } else if (NPC.contains("abyssal demon")) {
                SlayerVars.get().useExpeditiousBracelet = true;
                generalInventorySetup(ItemId.SKILLS_NECKLACE[2], true, false, -1, false);

            } else if (NPC.contains("ankou")) {
                SlayerVars.get().useExpeditiousBracelet = false;
                SlayerVars.get().useBraceletOfSlaughter = true;
                generalInventorySetup(ItemId.VARROCK_TELEPORT, false, false, -1, false);

            } else if (NPC.contains("banshee")) {
                General.println("[Debug]: Setting Banshee Inventory and Earmuffs");
                bansheeInventory();

            } else if (NPC.contains("basilisk")) {
                generalInventorySetup(ItemId.CAMELOT_TELEPORT, true, false,
                        ItemId.MIRROR_SHIELD, false);

            } else if (NPC.contains("black demon")) {
                greaterDemonInventory();

            } else if (NPC.contains("black dragon")) {
                blackDragonInventory();

            } else if (NPC.contains("blue dragon")) {
                blueDragonInventory();

            } else if (NPC.contains("bloodveld")) {
                newInventorySetup(ItemId.VARROCK_TELEPORT, false);

            } else if (NPC.contains("green dragon")) {
                greenDragonInventory();

            } else if (NPC.contains("cockatrice")) {
                generalInventorySetup(ItemId.CAMELOT_TELEPORT,
                        true, false, ItemId.MIRROR_SHIELD,
                        false, ItemId.MIRROR_SHIELD);

            } else if (NPC.contains("cave crawler")) {
                generalInventorySetup(ItemId.CAMELOT_TELEPORT, true,
                        false, -1, ItemId.ANTIDOTE_PLUS_PLUS);

            } else if (NPC.contains("crawling hand")) {
                generalInventorySetup(ItemId.VARROCK_TELEPORT);

            } else if (NPC.contains("cave slime"))
                caveSlimeInventory();

            else if (NPC.contains("crocodile"))
                crocodileInventory();

            else if (NPC.contains("dagannoth")) {
                SlayerVars.get().useExpeditiousBracelet = false;
                SlayerVars.get().useBraceletOfSlaughter = true;
                dagannothInventory();

            } else if (NPC.contains("fire giant"))
                generalInventorySetup(ItemId.VARROCK_TELEPORT, true, true);

            else if (NPC.contains("gargoyle")) {
                SlayerVars.get().useExpeditiousBracelet = false;
                SlayerVars.get().useBraceletOfSlaughter = true;
                gargoyleInventory();

            } else if (NPC.contains("greater demon")) {
                //  greaterDemonEquipment();
                greaterDemonInventory();

            } else if (NPC.contains("harpie bug swarm")) {
                generalInventorySetup(ItemId.ARDOUGNE_TELEPORT, true, false,
                        ItemId.LIT_BUG_LANTERN, false);

            } else if (NPC.contains("hellhound"))
                generalInventorySetup(ItemId.VARROCK_TELEPORT, true, true);

            else if (NPC.contains("hobgloblin"))
                generalInventorySetup(ItemId.VARROCK_TELEPORT, true, false,
                        -1, false, ItemId.BRASS_KEY);

            else if (NPC.contains("infernal mage"))
                SlayerVars.get().shouldSkipTask = true;

            else if (NPC.contains("jelly") || NPC.contains("jellie") )
                generalInventorySetup(ItemId.CAMELOT_TELEPORT, true, false,
                        -1, false);

            else if (NPC.contains("kalphite")) {
                generalInventorySetup(ItemId.VARROCK_TELEPORT, false,
                        new ItemRequirement(ItemId.SHANTAY_PASS, 1));

            } else if (NPC.contains("lesser demon"))
                generalInventorySetup(ItemId.FALADOR_TELEPORT, true, false,
                        -1, false);

            else if (NPC.contains("lizard")) {
                lizardInventory();

            } else if (NPC.contains("mutated zygomite")) {
                General.println("muted zygomite inv");
                mutatedZygomiteInventory();

            } else if (NPC.contains("nechryael")) {
                SlayerVars.get().shouldSkipTask = true;

            } else if (NPC.contains("rockslug")) {
                rockSlugInventory();

            } else if (NPC.contains("pyrefiend")) {
                generalInventorySetup(ItemId.CAMELOT_TELEPORT);

            } else if (NPC.contains("turoth")) {
                turothInventory();

            } else if (NPC.contains("kurask")) {
                turothInventory();

            } else if (NPC.contains("troll")) {
                generalInventorySetup(ItemId.CAMELOT_TELEPORT);

            } else if (NPC.contains("wall beast")) {
                caveSlimeInventory();

            } else if (NPC.contains("wyrm")) {
                wyrmInventory();

            } else if (NPC.contains("bear"))
                generalInventorySetup(ItemId.CAMELOT_TELEPORT);

            else if (NPC.contains("bat"))
                generalInventorySetup(ItemId.FALADOR_TELEPORT);

            else if (NPC.contains("bird"))
                generalInventorySetup(ItemId.LUMBRIDGE_TELEPORT);

            else if (NPC.contains("cave bug"))
                caveBugInventory();

            else if (NPC.contains("cow"))
                generalInventorySetup(ItemId.LUMBRIDGE_TELEPORT);

            else if (NPC.contains("crawling hand"))
                generalInventorySetup(ItemId.SALVE_GRAVEYARD_TELEPORT);

            else if (NPC.contains("dog"))
                generalInventorySetup(ItemId.LUMBRIDGE_TELEPORT, true,
                        new ItemRequirement(ItemId.WATERSKIN[0], 4),
                        new ItemRequirement(ItemId.SHANTAY_PASS, 1),
                        new ItemRequirement(ItemId.COINS, 1000));

            else if (NPC.contains("dwarves"))
                generalInventorySetup(ItemId.FALADOR_TELEPORT);

            else if (NPC.contains("ghost"))
                generalInventorySetup(ItemId.FALADOR_TELEPORT);

            else if (NPC.contains("goblin"))
                newInventorySetup(ItemId.LUMBRIDGE_TELEPORT, true);

            else if (NPC.contains("icefiend"))
                newInventorySetup(ItemId.FALADOR_TELEPORT, true);

            else if (NPC.contains("minotaurs"))
                newInventorySetup(ItemId.VARROCK_TELEPORT, true);

            else if (NPC.contains("brine rat"))
                newInventorySetup(ItemId.CAMELOT_TELEPORT, true, new ItemReq(ItemId.SPADE, 1));

            else if (NPC.contains("rat"))
                newInventorySetup(ItemId.LUMBRIDGE_TELEPORT, true);

            else if (NPC.contains("scorpion"))
                newInventorySetup(ItemId.VARROCK_TELEPORT, true);

            else if (NPC.contains("skeleton"))
                newInventorySetup(ItemId.CAMELOT_TELEPORT, true, new ItemReq(ItemId.BRASS_KEY, 1));

            else if (NPC.contains("spider"))
                newInventorySetup(ItemId.VARROCK_TELEPORT, true);

            else if (NPC.contains("sourhog"))
                generalInventorySetup(ItemId.VARROCK_TELEPORT, true, false,
                        ItemId.REINFORCED_GOGGLES, true, ItemId.EXPEDITIOUS_BRACELET);

            else if (NPC.contains("wolves"))
                generalInventorySetup(ItemId.VARROCK_TELEPORT);

            else if (NPC.contains("zombie"))
                generalInventorySetup(ItemId.VARROCK_TELEPORT, true, false, -1, false, ItemId.BRASS_KEY);

            else if (NPC.contains("moss giant"))
                generalInventorySetup(ItemId.VARROCK_TELEPORT);

            else if (NPC.contains("elves"))
                generalInventorySetup(ItemId.IORWERTH_CAMP_TELEPORT);
            else if (NPC.contains("suqah")) {
                suqahInventory();
                SlayerVars.get().shouldPrayMagic = true;
            } else if (NPC.contains("bronze dragon"))
                return;

            else {
                General.println("[Debug]: No custom inventory determined");
                generalInventorySetup(ItemId.VARROCK_TELEPORT);
            }
        }
    }

    public boolean checkEssentialItems() {
        if (!BankManager.checkInventoryItems(ItemId.VARROCK_TELEPORT, ItemId.MONKFISH) && !SlayerVars.get().shouldSkipTask) {
            General.println("[Bank]: Missing monkfish or Varrock teleport - restocking");
            SlayerVars.get().shouldRestock = true;
            return false;
        }
        return true;
    }

    public boolean checkForDScim() {
        if (missingWeapon()) {
            BankManager.open(true);
            if (Inventory.isFull()) {
                BankManager.deposit(0, SlayerVars.get().customFoodId);
            }
            if (Banking.find(ItemId.ABYSSAL_WHIP).length > 0) {
                General.println("[Banking]: We have an Abyssal whip, we will use it for this task");
                BankManager.withdraw(1, true, ItemId.ABYSSAL_WHIP);
                equipItem(ItemId.ABYSSAL_WHIP);
                BankManager.withdraw(0, true, SlayerVars.get().customFoodId);
                BankManager.close(true);
                return true;
            } else if (Banking.find(ItemId.DRAGON_SCIMITAR).length > 0) {
                General.println("[Banking]: We have a D Scim, we will use it for this task");
                BankManager.withdraw(1, true, ItemId.DRAGON_SCIMITAR);
                equipItem(ItemId.DRAGON_SCIMITAR);
                BankManager.withdraw(0, true, SlayerVars.get().customFoodId);
                BankManager.close(true);
                return true;
            }
            return false;

        }
        return true;
    }

    public boolean missingWeapon() {
        return Equipment.getItem(Equipment.SLOTS.WEAPON) == null;
    }


    public void printReasonForBanking() {
        if (Inventory.find(Filters.Items.actionsContains("Eat")).length < 3)
            General.println("[Bank]: Banking due to low food");

        if (SlayerVars.get().shouldBank)
            General.println("[Bank]: Banking b/c shouldBank variable is true");
    }

    @Override
    public Priority priority() {
        return Priority.HIGH;
    }


    @Override
    public void execute() {
        SlayerVars.get().status = "Banking...";
        Log.log("[SlayerBank]: ");
        //  CannonHandler.pickupCannon();

        printReasonForBanking();

        goToVarrockWestBank();

        eatAtBank();


        /*if (SlayerVars.get().magicMeleeGear && !Equipment.isEquipped(Filters.Items.nameContains("d'hide")))
            EquipmentUtils.getDhideFromBank();

        else if (SetGear.checkGear() != null) {
            General.println("[Bank]: Gear needs to be changed", Color.RED);
            SetGear.getGear();
        }*/

        /**
         * This is what actually gets stuff
         */
        selectLoadOut();

        // close bank
        if (Banking.isBankScreenOpen())
            BankManager.close(true);

        // Set the var to false
        SlayerVars.get().shouldBank = false;

        /**
         * Failsafes
         */
        if (missingWeapon()) {
            if (!checkForDScim()) {
                General.println("[Bank]: 2h weapon failsafe, ending script");
                cSkiller.isRunning.set(false);
            }
        }
        if (!checkEssentialItems())
            SlayerVars.get().shouldRestock = true;
    }

    @Override
    public String toString() {
        return "Banking";
    }

    @Override
    public String taskName() {
        return "Slayer";
    }

    @Override
    public boolean validate() {
         checkSpecial();
        return Vars.get().currentTask != null &&
                Vars.get().currentTask.equals(SkillTasks.SLAYER) &&
                !SlayerVars.get().shouldRestock &&
                !SlayerVars.get().shouldSkipTask &&
                !SlayerVars.get().slayerShopRestock &&
                SlayerVars.get().targets != null &&
                (Inventory.find(Filters.Items.actionsContains("Eat")).length < 3 || SlayerVars.get().shouldBank) &&
                !SlayerShop.shouldSlayerShop();
    }
}
