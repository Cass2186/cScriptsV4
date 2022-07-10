package scripts.Tasks.Slayer.Tasks;

import dax.api_lib.models.RunescapeBank;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.Combat;
import org.tribot.api2007.ext.Filters;
import org.tribot.script.sdk.*;
import org.tribot.script.sdk.Equipment;
import org.tribot.script.sdk.Inventory;
import org.tribot.script.sdk.Prayer;
import org.tribot.script.sdk.interfaces.Item;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.InventoryItem;
import scripts.*;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.SkillBank;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.Requirements.ItemReq;
import scripts.Requirements.ItemRequirement;
import scripts.Tasks.MiscTasks.BuyItems;
import scripts.Tasks.Slayer.SlayerConst.Areas;
import scripts.Tasks.Slayer.SlayerConst.Assign;
import scripts.Tasks.Slayer.SlayerUtils.SlayerUtils;
import scripts.Tasks.Slayer.SlayerUtils.SlayerVars;
import scripts.Timer;

import java.util.*;

public class SlayerBank implements Task {

    boolean needsDustyKey = false;

    public boolean is2hEquipped() {
        return Equipment.Slot.SHIELD.getItem().isEmpty();
    }


    public static boolean goToVarrockWestBank() {
        SlayerVars.get().status = "Going to bank";

        if (Prayer.isAllEnabled(Prayer.PROTECT_FROM_MELEE))
            Prayer.disableAll(Prayer.PROTECT_FROM_MELEE);

        if (Prayer.isAllEnabled(Prayer.PROTECT_FROM_MAGIC))
            Prayer.disableAll(Prayer.PROTECT_FROM_MAGIC);

        if (Prayer.isAllEnabled(Prayer.PROTECT_FROM_MISSILES))
            Prayer.disableAll(Prayer.PROTECT_FROM_MISSILES);

        Optional<InventoryItem> tab = Query.inventory()
                .idEquals(ItemID.VARROCK_TELEPORT)
                .findClosestToMouse();

        if (tab.isPresent() && !Bank.isNearby())
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
        BankManager.withdraw(5000, true, ItemID.COINS);
        BankManager.withdraw(2, true, ItemID.VARROCK_TELEPORT);
        if (teleTab != ItemID.VARROCK_TELEPORT)
            BankManager.withdraw(2, true, teleTab);

        if (!isWearingSlayerHelm())
            BankManager.withdraw(1, true, ItemID.ENCHANTED_GEM);

        BankManager.withdrawArray(SlayerVars.get().potionToUse, 1);
        BankManager.withdrawArray(ItemID.STAMINA_POTION, 1);
        BankManager.withdraw(0, true, SlayerVars.get().customFoodId);

        if (!BankManager.checkInventoryItems(ItemID.MONKFISH, ItemID.VARROCK_TELEPORT)) {
            Log.debug("[Bank]: restocking due to monkfish or varrock tabs missing");
            SlayerVars.get().shouldRestock = true;
        }
    }

    private static boolean generalGloryAndExpeditious(boolean useExpeditious) {
        BankManager.open(true);
        if (!Equipment.contains(ItemID.AMULET_OF_FURY) && !BankManager.checkEquippedGlory()) {
            Log.debug("[Bank]: restocking due to glory missing (92)");
            SlayerVars.get().shouldRestock = true;
            return false;
        }
        if (useExpeditious)
            checkExpeditiousBracelet();
        return true;
    }

    /**
     * this one is called when you want a custom array item such as a teleport item (e.g. skills necklace)
     *
     * @param teleItem         the tab needed, use -1 if none
     * @param arrayItem
     * @param itemRequirements
     */
    public void newInventorySetup(int teleItem, int[] arrayItem,
                                  ItemReq... itemRequirements) {
        Assign assign = SlayerVars.get().assignment;
        if (assign != null) {
            assign.getCustomGearList(); //TODO handle this
            newInventorySetup(teleItem, SlayerVars.get().useExpeditiousBracelet,
                    assign.getSpecialItemList().toArray(ItemReq[]::new));
        } else {
            newInventorySetup(teleItem, SlayerVars.get().useExpeditiousBracelet,
                    itemRequirements);
        }
        BankManager.withdrawArray(arrayItem, 1);
    }

    private ItemReq getBestDhideTop() {
        if (!canUseDHide())
            return null;
        if (Skill.RANGED.getActualLevel() >= 70) {
            return new ItemReq(ItemID.BLACK_DHIDE_BODY, 1, true, true);
        } else if (Skill.RANGED.getActualLevel() >= 60) {
            return new ItemReq(ItemID.RED_DHIDE_BODY, 1, true, true);
        } else if (Skill.RANGED.getActualLevel() >= 50) {
            return new ItemReq(ItemID.BLUE_DHIDE_BODY, 1, true, true);
        } else
            return new ItemReq(ItemID.GREEN_DHIDE_BODY, 1, true, true);

    }


    public void newInventorySetup(int teleItem, boolean useExpeditious,
                                  ItemReq... itemRequirements) {

        generalGloryAndExpeditious(useExpeditious);
        checkBraceletOfSlaughter();

        java.util.List<ItemReq> myInv = new ArrayList<>(
                Arrays.asList(
                        new ItemReq(ItemID.VARROCK_TELEPORT, 2),
                        new ItemReq.Builder()
                                .id(ItemID.STAMINA_POTION[0])
                                .minimumDosesNeeded(1)
                                .amount(1)
                                .alternateItemIDs(List.of(ItemID.STAMINA_POTION[1]
                                        , ItemID.STAMINA_POTION[2], ItemID.STAMINA_POTION[3])).build(),
                        new ItemReq.Builder()
                                .id(SlayerVars.get().potionToUse[0])
                                .minimumDosesNeeded(1)
                                .amount(1)
                                .alternateItemIDs(List.of(SlayerVars.get().potionToUse[1]
                                        , SlayerVars.get().potionToUse[2], SlayerVars.get().potionToUse[3])).build()
                )
        );


        BankManager.depositAll(true);
        if (itemRequirements != null) {
            myInv.addAll(Arrays.asList(itemRequirements));
        }

        if (teleItem != ItemID.VARROCK_TELEPORT && teleItem != -1)
            myInv.add(new ItemReq(teleItem, 1));

        if (!Equipment.contains(ItemID.SLAYER_HELMET) &&
                !Equipment.contains(ItemID.SLAYER_HELMET_I))
            myInv.add(new ItemReq(ItemID.ENCHANTED_GEM, 1));

        myInv.add(new ItemReq(ItemID.MONKFISH, 0));
        java.util.List<ItemReq> newInv = SkillBank.withdraw(myInv);
        if (newInv != null && newInv.size() > 0) {
            Log.debug("[Slayer Training]: Creating buy list");
            BuyItems.itemsToBuy = SlayerUtils.getPurchaseList();
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
            Log.debug("[SlayerBank]: We failed an itemrequirement, restocking");
            SlayerVars.get().shouldRestock = true;
            return;
        }
        BankManager.withdraw(5000, true, ItemID.COINS);
        BankManager.withdraw(2, true, ItemID.VARROCK_TELEPORT);
        if (teleItem != ItemID.VARROCK_TELEPORT && teleItem != -1)
            BankManager.withdraw(1, true, teleItem);


        if (!Equipment.contains(ItemID.SLAYER_HELMET) && !Equipment.contains(ItemID.SLAYER_HELMET_I))
            BankManager.withdraw(1, true, ItemID.ENCHANTED_GEM);

        BankManager.withdrawArray(ItemID.STAMINA_POTION, 1);
        BankManager.withdrawArray(SlayerVars.get().potionToUse, 1);
        BankManager.withdraw(0, true, SlayerVars.get().customFoodId);

        if (!BankManager.checkInventoryItems(SlayerVars.get().customFoodId)) {
            Log.debug("[SlayerBank]: restocking due to food missing");
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
        BankManager.withdraw(5000, true, ItemID.COINS);
        if (useCannon) {
            BankManager.withdraw(750, true, ItemID.CANNONBALL);
            BankManager.withdraw(1, true, 6);
            BankManager.withdraw(1, true, 8);
            BankManager.withdraw(1, true, 10);
            BankManager.withdraw(1, true, 12);
            if (Inventory.getCount(ItemID.CANNON_IDS) < 4) {
                Log.debug("[bank]: We're missing parts of the cannon, turning it to false");
                SlayerVars.get().use_cannon = false;
            }

        }


        BankManager.withdraw(2, true, ItemID.VARROCK_TELEPORT);
        if (teleItem != ItemID.VARROCK_TELEPORT)
            BankManager.withdraw(1, true, teleItem);


        if (!Equipment.contains(ItemID.SLAYER_HELMET) && !Equipment.contains(ItemID.SLAYER_HELMET_I))
            BankManager.withdraw(1, true, ItemID.ENCHANTED_GEM);

        BankManager.withdrawArray(ItemID.STAMINA_POTION, 1);
        BankManager.withdrawArray(ItemID.SUPER_COMBAT_POTION, 1);
        BankManager.withdraw(0, true, SlayerVars.get().customFoodId);

        if (!BankManager.checkInventoryItems(SlayerVars.get().customFoodId)) {
            Log.debug("[Bank]: restocking due to food missing");
            SlayerVars.get().shouldRestock = true;
            return;
        }
    }

    public void generalInventorySetup(int teleItem, boolean useExpeditious, boolean alch, boolean useCannon,
                                      int itemToEquip, boolean slayerHelmSub, int... otherItems) {

        generalGloryAndExpeditious(useExpeditious);
        checkBraceletOfSlaughter();
        if (itemToEquip != -1 && !Equipment.contains(itemToEquip)) { /// need an item and it's not equipped
            Log.debug("[Bank]: Need to equip item - Slayer helm subs? " + slayerHelmSub);
            if (!slayerHelmSub && BankManager.withdraw(1, true, itemToEquip)) {
                Optional<InventoryItem> item = Query.inventory().idEquals(itemToEquip).findClosestToMouse();

                if (item.isPresent()) {
                    BankManager.close(true);

                    if (item.map(InventoryItem::click).orElse(false))
                        Timer.waitCondition(() -> Equipment.contains(itemToEquip), 2500, 5000);

                    BankManager.open(true);

                } else if (!Bank.contains(itemToEquip)) {

                    if (itemToEquip == ItemID.BOOTS_OF_STONE) {
                        SlayerVars.get().needBootsOfStone = true;
                        SlayerVars.get().slayerShopRestock = true;
                        Log.debug("[Debug]: Missing Boots of stone, need to slayer restock");
                        return;

                    } else {
                        Log.debug("[Debug]: Missing an item, ending");
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
            BankManager.withdraw(750, true, ItemID.CANNONBALL);
            BankManager.withdraw(1, true, 6);
            BankManager.withdraw(1, true, 8);
            BankManager.withdraw(1, true, 10);
            BankManager.withdraw(1, true, 12);
            if (Inventory.getCount(ItemID.CANNON_IDS) < 4) {
                Log.debug("[bank]: We're missing parts of the cannon, turning it to false");
                SlayerVars.get().use_cannon = false;
            }
        }

        if (alch) {
            BankManager.withdraw(50, true, ItemID.NATURE_RUNE);
            BankManager.withdraw(250, true, ItemID.FIRE_RUNE);
        }
        BankManager.withdraw(5000, true, ItemID.COINS);
        BankManager.withdraw(2, true, ItemID.VARROCK_TELEPORT);
        BankManager.withdraw(1, true, teleItem);

        if (!Equipment.contains(ItemID.SLAYER_HELMET) && !Equipment.contains(ItemID.SLAYER_HELMET_I))
            BankManager.withdraw(1, true, ItemID.ENCHANTED_GEM);

        BankManager.withdrawArray(ItemID.STAMINA_POTION, 1);
        BankManager.withdrawArray(ItemID.SUPER_COMBAT_POTION, 1);
        BankManager.withdraw(0, true, SlayerVars.get().customFoodId);

        if (!BankManager.checkInventoryItems(SlayerVars.get().customFoodId)) {
            Log.debug("[Bank]: restocking due to food missing");
            SlayerVars.get().shouldRestock = true;
            return;
        }
    }


    public void generalInventorySetup(int teleItem, boolean useExpeditious, boolean alch,
                                      int itemToEquip, boolean slayerHelmSub, int... otherItems) {

        generalGloryAndExpeditious(useExpeditious);
        checkBraceletOfSlaughter();

        if (itemToEquip != -1 && !Equipment.contains(itemToEquip)) { /// need an item and it's not equipped
            Log.debug("[Bank]: Need to equip item - Slayer helm subs? " + slayerHelmSub);

            BankManager.open(true);
            if ((!slayerHelmSub || !isWearingSlayerHelm()) && BankManager.withdraw(1, true, itemToEquip)) {

                Optional<InventoryItem> item = Query.inventory().idEquals(itemToEquip).findClosestToMouse();
                if (item.isPresent()) {
                    BankManager.close(true);

                    if (item.get().click())
                        Timer.waitCondition(() -> Equipment.contains(itemToEquip), 2500, 5000);

                    BankManager.open(true);

                } else if (!Bank.contains(itemToEquip)) {

                    if (itemToEquip == ItemID.BOOTS_OF_STONE) {
                        SlayerVars.get().needBootsOfStone = true;
                        SlayerVars.get().slayerShopRestock = true;
                        Log.debug("[Debug]: Missing Boots of stone, need to slayer restock");
                        return;

                    } else {
                        Log.debug("[Debug]: Missing an item, ending");
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
            BankManager.withdraw(50, true, ItemID.NATURE_RUNE);
            BankManager.withdraw(250, true, ItemID.FIRE_RUNE);
        }

        BankManager.withdraw(2, true, ItemID.VARROCK_TELEPORT);
        BankManager.withdraw(1, true, teleItem);

        if (!Equipment.contains(ItemID.SLAYER_HELMET) && !Equipment.contains(ItemID.SLAYER_HELMET_I))
            BankManager.withdraw(1, true, ItemID.ENCHANTED_GEM);

        BankManager.withdrawArray(ItemID.STAMINA_POTION, 1);
        BankManager.withdrawArray(ItemID.SUPER_COMBAT_POTION, 1);
        BankManager.withdraw(0, true, SlayerVars.get().customFoodId);

        if (!BankManager.checkInventoryItems(SlayerVars.get().customFoodId)) {
            Log.debug("[Bank]: restocking due to food missing");
            SlayerVars.get().shouldRestock = true;
            return;
        }
    }

    public void generalInventorySetup(int[] teleItem, boolean useExpeditious, boolean alch,
                                      int itemToEquip, int... otherItems) {
        generalGloryAndExpeditious(useExpeditious);
        checkBraceletOfSlaughter();
        if (itemToEquip != -1 && !Equipment.contains(itemToEquip)) {

            if (BankManager.withdraw(1, true, itemToEquip)) {
                Optional<InventoryItem> item = Query.inventory().idEquals(itemToEquip).findClosestToMouse();

                if (item.isPresent()) {
                    BankManager.close(true);
                    if (item.get().click())
                        Timer.waitCondition(() -> Equipment.contains(itemToEquip), 2500, 5000);
                    BankManager.open(true);
                }

            } else if (!Bank.contains(itemToEquip)) {
                if (itemToEquip == ItemID.BOOTS_OF_STONE) {
                    SlayerVars.get().needBootsOfStone = true;
                    SlayerVars.get().slayerShopRestock = true;
                    Log.debug("[Debug]: Missing Boots of stone, need to slayer restock");
                    return;
                } else {
                    Log.debug("[Debug]: Missing item, ending");
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
            BankManager.withdraw(50, true, ItemID.NATURE_RUNE);
            BankManager.withdraw(250, true, ItemID.FIRE_RUNE);
        }

        BankManager.withdraw(2, true, ItemID.VARROCK_TELEPORT);
        BankManager.withdrawArray(teleItem, 1);

        if (!Equipment.contains(ItemID.SLAYER_HELMET) && !Equipment.contains(ItemID.SLAYER_HELMET_I))
            BankManager.withdraw(1, true, ItemID.ENCHANTED_GEM);

        BankManager.withdrawArray(ItemID.STAMINA_POTION, 1);
        BankManager.withdrawArray(ItemID.SUPER_COMBAT_POTION, 1);
        BankManager.withdraw(0, true, SlayerVars.get().customFoodId);

        if (!BankManager.checkInventoryItems(SlayerVars.get().customFoodId)) {
            Log.debug("[Bank]: restocking due to food missing");
            SlayerVars.get().shouldRestock = true;
            return;
        }
    }

    public void generalInventorySetup(int teleTab, boolean useExpeditious, boolean alch,
                                      int itemToEquip, boolean slayerHelmSub, HashMap<Integer, Integer> itemMap) {
        generalGloryAndExpeditious(useExpeditious);
        checkBraceletOfSlaughter();
        if (itemToEquip != -1 && !Equipment.contains(itemToEquip)) {
            if (BankManager.withdraw(1, true, itemToEquip)) {
                Optional<InventoryItem> item = Query.inventory().idEquals(itemToEquip).findClosestToMouse();
                if (item.isPresent()) {
                    BankManager.close(true);
                    if (item.get().click())
                        Timer.waitCondition(() -> Equipment.contains(itemToEquip), 2500, 5000);
                    BankManager.open(true);
                }

            } else if (!Bank.contains(itemToEquip)) {
                if (itemToEquip == ItemID.BOOTS_OF_STONE) {
                    SlayerVars.get().needBootsOfStone = true;
                    SlayerVars.get().slayerShopRestock = true;
                    Log.debug("[Debug]: Missing Boots of stone, need to slayer restock");
                    return;
                } else {
                    Log.debug("[Debug]: Missing item, ending");
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
            BankManager.withdraw(50, true, ItemID.NATURE_RUNE);
            BankManager.withdraw(250, true, ItemID.FIRE_RUNE);
        }

        BankManager.withdraw(2, true, ItemID.VARROCK_TELEPORT);
        if (teleTab != ItemID.VARROCK_TELEPORT)
            BankManager.withdraw(2, true, teleTab);

        if (!isWearingSlayerHelm())
            BankManager.withdraw(1, true, ItemID.ENCHANTED_GEM);

        BankManager.withdrawArray(ItemID.STAMINA_POTION, 1);
        BankManager.withdrawArray(ItemID.SUPER_COMBAT_POTION, 1);
        BankManager.withdraw(0, true, SlayerVars.get().customFoodId);

        if (!BankManager.checkInventoryItems(SlayerVars.get().customFoodId)) {
            Log.debug("[Bank]: restocking due to food missing");
            SlayerVars.get().shouldRestock = true;
            return;
        }
    }

    public void generalInventorySetup(int teleTab, boolean useExpeditious, boolean alch,
                                      int itemToEquip, int... otherItems) {
        generalGloryAndExpeditious(useExpeditious);
        checkBraceletOfSlaughter();
        if (itemToEquip != -1 && !Equipment.contains(itemToEquip)) {
            if (BankManager.withdraw(1, true, itemToEquip)) {
                Optional<InventoryItem> item = Query.inventory().idEquals(itemToEquip).findClosestToMouse();
                if (item.isPresent()) {
                    BankManager.close(true);
                    if (item.get().click())
                        Timer.waitCondition(() -> Equipment.contains(itemToEquip), 2500, 5000);
                    BankManager.open(true);
                }

            } else if (!Bank.contains(itemToEquip)) {
                if (itemToEquip == ItemID.BOOTS_OF_STONE) {
                    SlayerVars.get().needBootsOfStone = true;
                    SlayerVars.get().slayerShopRestock = true;
                    Log.debug("[Debug]: Missing Boots of stone, need to slayer restock");
                    return;
                } else {
                    Log.debug("[Debug]: Missing item, ending");
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
            BankManager.withdraw(50, true, ItemID.NATURE_RUNE);
            BankManager.withdraw(250, true, ItemID.FIRE_RUNE);
        }

        BankManager.withdraw(2, true, ItemID.VARROCK_TELEPORT);
        if (teleTab != ItemID.VARROCK_TELEPORT)
            BankManager.withdraw(2, true, teleTab);

        if (!Equipment.contains(ItemID.SLAYER_HELMET) && !Equipment.contains(ItemID.SLAYER_HELMET_I))
            BankManager.withdraw(1, true, ItemID.ENCHANTED_GEM);

        BankManager.withdrawArray(ItemID.STAMINA_POTION, 1);
        BankManager.withdrawArray(ItemID.SUPER_COMBAT_POTION, 1);
        BankManager.withdraw(0, true, SlayerVars.get().customFoodId);

        if (!BankManager.checkInventoryItems(SlayerVars.get().customFoodId)) {
            Log.debug("[Bank]: restocking due to food missing");
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
        BankManager.withdrawArray(ItemID.SUPER_COMBAT_POTION, 1);
        BankManager.withdraw(1, true, ItemID.ENCHANTED_GEM);
        BankManager.withdraw(true, 2, teleTab);
        BankManager.withdraw(true, 1, ItemID.VARROCK_TELEPORT);
        BankManager.withdrawArray(ItemID.STAMINA_POTION, 1);
        BankManager.withdraw(true, 22 - openSpaces, ItemID.MONKFISH);
    }


    public void getAndEquipItem(int itemQuantity, int ItemID, String method) { // need a check for having inventory items
        if (!Inventory.contains(ItemID)) {
            BankManager.open(true);
            List<Item> items = Query.bank().idEquals(ItemID).toList();
            if (items.size() > 0 && items.get(0).getStack() >= itemQuantity) {
                Log.debug("[Debug]: Getting: " + ItemID + " x " + itemQuantity);
                BankManager.withdraw(itemQuantity, true, ItemID);
                General.sleep(General.randomSD(400, 1000, 400, 100));
            } else
                Log.debug("[Debug]: Missing item: " + ItemID);
        }
        Optional<InventoryItem> item = Query.inventory().idEquals(ItemID).findClosestToMouse();
        if (item.isPresent()) {
            BankManager.close(true);
            if (item.get().click()) {
                Timer.waitCondition(() -> Equipment.contains(ItemID), 3000);
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
            if (!Equipment.contains(ItemID.EXPEDITIOUS_BRACELET)) {
                Log.debug("[BankManager]: Need to replace Expeditious Bracelet.");
                BankManager.withdraw(1, true, ItemID.EXPEDITIOUS_BRACELET);
                Utils.equipItem(ItemID.EXPEDITIOUS_BRACELET);
                return Timer.waitCondition(() -> Equipment.contains(ItemID.EXPEDITIOUS_BRACELET), 2500, 3500);
            }
        }
        return false;
    }


    public static boolean checkEquippedGlory() {
        if (!Equipment.contains(ItemID.AMULET_OF_GLORY)) {
            BankManager.withdrawArray(ItemID.AMULET_OF_GLORY, 1);
            Timer.waitCondition(() -> Inventory.contains(ItemID.AMULET_OF_GLORY), 2500, 4000);
            Optional<InventoryItem> invGlory = Query.inventory().idEquals(ItemID.AMULET_OF_GLORY).findClosestToMouse();
            if (invGlory.map(g -> g.click("Wear")).orElse(false))
                return Timer.waitCondition(() -> Equipment.contains(ItemID.AMULET_OF_GLORY), 2500, 4000);
            else
                return false;
        }
        return true;
    }


    /*****************************
     * **NPC-Specific Inventories**
     ******************************/
    public void abberantSpectreInventory() {
        equipSlayerItem(ItemID.NOSE_PEG, true);
        generalInventorySetup(ItemID.VARROCK_TELEPORT, 10);
        BankManager.withdrawArray(ItemID.PRAYER_POTION, 10);

    }

    /*****************************
     * **NPC-Specific Inventories**
     ******************************/
    public void infernalMagesInventory() {
        generalInventorySetup(ItemID.VARROCK_TELEPORT, 10);
        BankManager.withdrawArray(ItemID.PRAYER_POTION, 10);
    }

    private boolean equipItem(int ItemID) {
        Optional<InventoryItem> inv =
                Query.inventory().idEquals(ItemID).findClosestToMouse();

        if (inv.isPresent()) {
            if (Bank.isOpen()) {
                BankManager.close(true);
            }
            if (inv.get().click())
                return Timer.waitCondition(() -> Equipment.contains(ItemID), 3000, 5000);
        }
        return false;
    }

    private boolean equipSlayerItem(int ItemId, boolean isSlayerHelmASubistitute) {
        if (isSlayerHelmASubistitute && (Equipment.contains(ItemID.SLAYER_HELMET) ||
                Equipment.contains(ItemID.SLAYER_HELMET_I)))
            return true;

        if (Equipment.contains(ItemId))
            return true;

        else {
            if (equipItem(ItemId))
                return true;

            else {
                BankManager.open(true);
                if (Inventory.isFull())
                    depositFood(1);
                BankManager.withdraw(1, true, ItemId);
                BankManager.close(true);
                return equipItem(ItemId);
            }
        }
    }

    public void depositFood(int amount) {
        Log.info(String.format("Depositing %s pieces of food to make room in inventory", amount));
        for (int i = 0; i < amount; i++) {
            Optional<InventoryItem> eatable = Query.inventory().actionContains("Eat")
                    .findClosestToMouse();
            if (!Bank.isOpen())
                BankManager.open(true);

            if (eatable.map(e -> Bank.deposit(e, 1)).orElse(false)) {
                Utils.idleNormalAction();
            }
        }
    }

    public void bansheeInventory() {
        if (!Equipment.contains(ItemID.EARMUFFS) && !Equipment.contains(ItemID.SLAYER_HELMET)
                && !Equipment.contains(ItemID.SLAYER_HELMET_I)) { // not wearing
            getAndEquipItem(1, ItemID.EARMUFFS, "Wear");
            Utils.shortSleep();
        }
        if (Equipment.contains(ItemID.EARMUFFS) || Equipment.contains(ItemID.SLAYER_HELMET) || Equipment.contains(ItemID.SLAYER_HELMET_I))
            generalInventorySetup(ItemID.VARROCK_TELEPORT);

    }

    public void turothInventory() {
        if (!Equipment.contains(ItemID.LEAFBLADED_BATTLEAXE)) {
            equipSlayerItem(ItemID.LEAFBLADED_BATTLEAXE, false);
            Utils.shortSleep();
        }
        if (Equipment.contains(ItemID.LEAFBLADED_BATTLEAXE))
            generalInventorySetup(ItemID.CAMELOT_TELEPORT);

    }


    public void blueDragonInventory() {
        generalInventorySetup(ItemID.FALADOR_TELEPORT, true, false, true,
                ItemID.ANTIDRAGON_SHIELD, false, ItemID.DUSTY_KEY);

        if (!Inventory.contains(ItemID.DUSTY_KEY)) {
            Log.debug("[Debug]: We do not have a dusty key, will get one for task.");
            SlayerVars.get().needDustyKey = true;
        }
    }

    public void blackDragonInventory() {
        generalInventorySetup(ItemID.FALADOR_TELEPORT, true, false,
                ItemID.ANTIDRAGON_SHIELD, false, ItemID.DUSTY_KEY);

        if (!Inventory.contains(ItemID.DUSTY_KEY)) {
            Log.debug("[Debug]: We do not have a dusty key, will get one for task.");
            SlayerVars.get().needDustyKey = true;
        }
    }

    public void greenDragonInventory() {
        generalInventorySetup(ItemID.BURNING_AMULET, true, false,
                ItemID.ANTIDRAGON_SHIELD, ItemID.LOOTING_BAG);
    }


    private void lightCandle() {
        if (Inventory.contains(ItemID.CANDLE)) {
            if (!Inventory.contains(ItemID.TINDERBOX)) {
                SlayerVars.get().shouldRestock = true;
                Log.error("Missing an tinderbox");
                return;
            }
            BankManager.close(true);
            Utils.useItemOnItem(ItemID.CANDLE, ItemID.TINDERBOX);
            Utils.shortSleep();

        }
    }

    public void caveSlimeInventory() {
        if (!equipSlayerItem(ItemID.SPINY_HELMET, true)) {
            SlayerVars.get().shouldRestock = true;
            return;
        }
        generalInventorySetup(ItemID.LUMBRIDGE_TELEPORT, 6);
        if (!BankManager.withdraw(1, true, ItemID.LIT_CANDLE))
            if (!BankManager.withdraw(1, true, ItemID.CANDLE)) {
                Log.debug("[Debug]: Missing Candle, need to restock");
                SlayerVars.get().shouldRestock = true;
                return;
            }
        BankManager.withdraw(1, ItemID.TINDERBOX);
        BankManager.withdrawArray(ItemID.ANTIDOTE_PLUS_PLUS, 1);
        BankManager.close(true);
        lightCandle();
    }

    public void caveBugInventory() {
        if (!equipSlayerItem(ItemID.SPINY_HELMET, true)) {
            SlayerVars.get().shouldRestock = true;
            return;
        }
        generalInventorySetup(ItemID.LUMBRIDGE_TELEPORT, 6);
        if (!BankManager.withdraw(1, true, ItemID.LIT_CANDLE))
            if (!BankManager.withdraw(1, true, ItemID.CANDLE)) {
                Log.debug("[Debug]: Missing Candle, need to restock");
                SlayerVars.get().shouldRestock = true;
                return;
            }
        BankManager.withdraw(1, ItemID.TINDERBOX);

        lightCandle();
    }

    public void crocodileInventory() {
        BankManager.open(true);
        if (!generalGloryAndExpeditious(SlayerVars.get().useExpeditiousBracelet))
            return;
        BankManager.depositAll(true);
        BankManager.withdraw(1, ItemID.ENCHANTED_GEM);
        BankManager.withdraw(2, ItemID.SHANTAY_PASS);
        BankManager.withdraw(2, ItemID.VARROCK_TELEPORT);
        if (!BankManager.checkInventoryItems(ItemID.SHANTAY_PASS))
            SlayerVars.get().shouldRestock = true;
        BankManager.withdraw(1, ItemID.SUPER_STRENGTH[0]);
        BankManager.withdraw(1, ItemID.STAMINA_POTION[2]);
        BankManager.withdraw(10, ItemID.WATERSKIN[0], true);
        BankManager.withdraw(14, true, ItemID.MONKFISH);
        Utils.microSleep();
    }

    public void lizardInventory() {
        BankManager.open(true);
        if (!generalGloryAndExpeditious(SlayerVars.get().useExpeditiousBracelet))
            return;
        BankManager.depositAll(true);
        BankManager.withdraw(2, true, ItemID.VARROCK_TELEPORT);
        BankManager.withdraw(1, true, ItemID.ENCHANTED_GEM);
        if (!BankManager.withdraw(150, true, ItemID.ICE_COOLER)) {
            SlayerVars.get().slayerShopRestock = true;
            return;
        }
        BankManager.withdraw(2, true, ItemID.SHANTAY_PASS);

        BankManager.withdraw(1, true, ItemID.SUPER_STRENGTH[0]);
        if (!BankManager.checkInventoryItems(ItemID.SHANTAY_PASS)) {
            Log.debug("[Debug]: Need to restock due to no shantay passes");
            SlayerVars.get().shouldRestock = true;
            return;
        }
        BankManager.withdraw(1, true, ItemID.STAMINA_POTION[2]);
        BankManager.withdrawArray(ItemID.WATERSKIN, 8);
        Timer.waitCondition(() -> Inventory.getCount(ItemID.WATERSKIN) > 5, 3000);
        if (Inventory.getCount(ItemID.WATERSKIN) < 5) {
            Log.debug("[Debug]; Restocking due to lack of waterskins");
            SlayerVars.get().shouldRestock = true;
            return;
        } else {
            SlayerVars.get().shouldRestock = false;
        }
        BankManager.withdraw(0, true, ItemID.MONKFISH);
    }


    public void rockSlugInventory() {
        if (!Equipment.contains(ItemID.BRINE_SABRE)) { // not wearing item
            BankManager.open(true);
            if (Bank.contains(ItemID.BRINE_SABRE)) {
                Log.debug("[Debug]: Brine sabre detected.");
                getAndEquipItem(1, ItemID.BRINE_SABRE, "Wield");
                generalInventorySetup(ItemID.CAMELOT_TELEPORT);

            } else {
                BankManager.open(true);
                newInventorySetup(ItemID.CAMELOT_TELEPORT, true,
                        new ItemReq(ItemID.BAG_OF_SALT, 150, 50));
            }
        } else {
            generalInventorySetup(ItemID.CAMELOT_TELEPORT);
        }
    }


    public void greaterDemonInventory() {
        BankManager.open(true);
        if (!generalGloryAndExpeditious(SlayerVars.get().useExpeditiousBracelet))
            return;
        BankManager.depositAll(true);
        BankManager.withdraw(12, true, ItemID.PRAYER_POTION[0]);
        if (Inventory.getCount(ItemID.PRAYER_POTION[0]) < 6) {
            SlayerVars.get().shouldRestock = true;
            return;
        }
        BankManager.withdraw(1, true, ItemID.SKILLS_NECKLACE);
        BankManager.withdraw(1, true, ItemID.ENCHANTED_GEM);
        BankManager.withdraw(8, true, ItemID.MONKFISH);
        BankManager.withdraw(2, true, ItemID.SUPER_STRENGTH);
        BankManager.withdraw(1, true, ItemID.STAMINA_POTION);
        BankManager.withdraw(1, true, ItemID.VARROCK_TELEPORT);
        BankManager.withdraw(1, true, ItemID.EXPEDITIOUS_BRACELET);
    }

    public void dagannothInventory() {
        BankManager.open(true);
        if (!generalGloryAndExpeditious(SlayerVars.get().useExpeditiousBracelet))
            return;

        BankManager.depositAll(true);
        if (Bank.contains(ItemID.CANNON_IDS) && SlayerVars.get().use_cannon) {
            BankManager.withdraw(750, true, ItemID.CANNONBALL);
            BankManager.withdraw(1, true, 6);
            BankManager.withdraw(1, true, 8);
            BankManager.withdraw(1, true, 10);
            BankManager.withdraw(1, true, 12);
        } else {
            Log.debug("[Debug]; Setting cannoning to false b/c we don't have a cannon");
            SlayerVars.get().use_cannon = false;
        }

        BankManager.withdrawArray(ItemID.GAMES_NECKLACE, 1);
        BankManager.withdraw(1, true, ItemID.ENCHANTED_GEM);
        BankManager.withdrawArray(ItemID.SUPER_COMBAT_POTION, 1);
        BankManager.withdraw(1, true, ItemID.STAMINA_POTION);
        BankManager.withdraw(1, true, ItemID.VARROCK_TELEPORT);
        BankManager.withdraw(0, true, ItemID.MONKFISH);
    }

    public void suqahInventory() {
        BankManager.open(true);
        if (!generalGloryAndExpeditious(SlayerVars.get().useExpeditiousBracelet))
            return;

        BankManager.depositAll(true);
        if (Bank.contains(ItemID.CANNON_IDS) && SlayerVars.get().use_cannon) {
            BankManager.withdraw(750, true, ItemID.CANNONBALL);
            BankManager.withdraw(1, true, 6);
            BankManager.withdraw(1, true, 8);
            BankManager.withdraw(1, true, 10);
            BankManager.withdraw(1, true, 12);
        } else {
            Log.debug("[Debug]; Setting cannoning to false b/c we don't have a cannon");
            SlayerVars.get().use_cannon = false;
        }

        BankManager.withdraw(1, true, ItemID.LUNAR_ISLE_TELEPORT);
        if (!isWearingSlayerHelm())
            BankManager.withdraw(1, true, ItemID.ENCHANTED_GEM);
        BankManager.withdrawArray(ItemID.SUPER_COMBAT_POTION, 1);

        BankManager.withdraw(2, true, ItemID.VARROCK_TELEPORT);
        BankManager.withdraw(5, true, ItemID.PRAYER_POTION[0]);
        BankManager.withdraw(0, true, ItemID.MONKFISH);
    }

    public static boolean checkBraceletOfSlaughter() {
        if (SlayerVars.get().useBraceletOfSlaughter && !Equipment.contains(ItemID.BRACELET_OF_SLAUGHTER)) {
            Log.debug("[Bank]: Equipping bracelet of slaughter");
            BankManager.open(true);
            BankManager.withdraw(1, true, ItemID.BRACELET_OF_SLAUGHTER);
            Utils.equipItem(ItemID.BRACELET_OF_SLAUGHTER, "Wear");
            return Timer.waitCondition(() -> Equipment.contains(ItemID.BRACELET_OF_SLAUGHTER), 2500, 3500);
        }
        return false;
    }


    public void gargoyleInventory() {
        generalInventorySetup(ItemID.SALVE_GRAVEYARD_TELEPORT,
                false, true, -1, false,
                ItemID.ROCK_HAMMER, ItemID.SALVE_GRAVEYARD_TELEPORT);
        if (!BankManager.checkInventoryItems(ItemID.ROCK_HAMMER)) {
            Log.debug("[Bank]: Missing a rock hammer, going to buy one from slayer shop");
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

    private static boolean canUseDHide() {
        return Skill.RANGED.getActualLevel() > 40 && Skill.DEFENCE.getActualLevel() > 40;
    }

    public static void checkSpecial() {
        if (SlayerVars.get().targets != null) {
            String NPC = SlayerVars.get().targets[0].toLowerCase();

            if (NPC.contains("aberrant spectre") && !Equipment.contains(ItemID.NOSE_PEG)
                    && !Equipment.contains(ItemID.SLAYER_HELMET)
                    && !Equipment.contains(ItemID.SLAYER_HELMET_I)) {
                Log.debug("[Bank]: Need to bank for aberrant Spectres inventory");
                SlayerVars.get().shouldBank = true;
                SlayerVars.get().usingSpecialItem = true;


            } else if (NPC.contains("banshee") && (!Equipment.contains(ItemID.EARMUFFS) &&
                    !isWearingSlayerHelm())) {
                Log.debug("[Debug]: Need to bank for Banshee item");
                SlayerVars.get().shouldBank = true;
                Log.debug("[Bank]: Using a special item for this Assignment");
                SlayerVars.get().usingSpecialItem = true;

            } else if (NPC.contains("basilisk") && !Equipment.contains(ItemID.MIRROR_SHIELD)) {
                Log.debug("[Debug]: Need to bank for Basilisk item");
                SlayerVars.get().shouldBank = true;
                Log.debug("[Bank]: Using a special item for this Assignment");
                SlayerVars.get().usingSpecialItem = true;

            } else if (NPC.contains("black demon") &&
                    !Inventory.contains(ItemID.PRAYER_POTION)) {
                Log.debug("[Debug]: Need to bank for Black demon");
                SlayerVars.get().shouldBank = true;
                Log.debug("[Bank]: Using a special item for this Assignment");
                SlayerVars.get().usingSpecialItem = true;

            } else if (NPC.contains("bloodveld") && (canUseDHide() &&
                    !Query.equipment().nameContains("d'hide").isAny())) {
                Log.debug("[Debug]: Need to bank for Bloodveld item (D'hide)");
                SlayerVars.get().shouldBank = true;
                SlayerVars.get().usingSpecialItem = true;
                SlayerVars.get().magicMeleeGear = true;
            } else if (NPC.contains("black dragon") && !Equipment.contains(ItemID.ANTIDRAGON_SHIELD)) {
                Log.debug("[Debug]: Need to bank for black dragon item(s)");
                SlayerVars.get().shouldBank = true;
                SlayerVars.get().usingSpecialItem = true;

            } else if (NPC.contains("blue dragon") && !Equipment.contains(ItemID.ANTIDRAGON_SHIELD)) {
                Log.debug("[Debug]: Need to bank for blue dragon item");
                SlayerVars.get().shouldBank = true;
                SlayerVars.get().usingSpecialItem = true;

            } else if (NPC.contains("iron dragon")) {
                Log.debug("[Debug]: Need to bank for Iron dragon item(s)");
                SlayerVars.get().shouldBank = false;
                SlayerVars.get().shouldSkipTask = true;
                return;

            } else if (NPC.contains("green dragon") && !Equipment.contains(ItemID.ANTIDRAGON_SHIELD)) {
                Log.debug("[Debug]: Need to bank for green dragon item");
                SlayerVars.get().shouldBank = false;
                SlayerVars.get().shouldSkipTask = true;
                return;


            } else if (NPC.contains("cockatrice") && !Equipment.contains(ItemID.MIRROR_SHIELD)) {
                Log.debug("[Debug]: Need to bank for cockatrice item");
                SlayerVars.get().shouldBank = true;
                SlayerVars.get().usingSpecialItem = true;

            } else if (NPC.contains("dog") && !Inventory.contains(ItemID.COINS)) {
                Log.debug("[Debug]: Need to bank for Dog item");
                SlayerVars.get().shouldBank = true;

            } else if ((NPC.contains("cave slime") || NPC.contains("cave bug")) &&
                    (!Inventory.contains(ItemID.LIT_CANDLE)
                            || !Inventory.contains(ItemID.TINDERBOX))) {
                Log.debug("[Debug]: Need to bank for cave item");
                SlayerVars.get().shouldBank = true;
                SlayerVars.get().usingSpecialItem = true;
                return;

            } else if (NPC.contains("cave crawler") &&
                    (!Inventory.contains(ItemID.ANTIDOTE_PLUS_PLUS))) {
                Log.debug("[Debug]: Need to bank for cave crawler item");
                SlayerVars.get().shouldBank = true;
                SlayerVars.get().usingSpecialItem = true;

            } else if (NPC.contains("gargoyle") && !Inventory.contains(ItemID.ROCK_HAMMER)) {
                Log.debug("[Debug]: Need to bank for gargoyle item");
                SlayerVars.get().shouldBank = true;

            } else if (NPC.contains("elve") &&
                    !Inventory.contains(ItemID.IORWERTH_CAMP_TELEPORT)) {
                Log.debug("[Debug]: Need to bank for elf item");
                SlayerVars.get().shouldBank = true;

            } else if (NPC.contains("greater demon") && !Inventory.contains(ItemID.PRAYER_POTION)) {
                Log.debug("[Debug]: Need to bank for Greater demon item");
                SlayerVars.get().shouldBank = true;
                SlayerVars.get().shouldPrayMelee = true;
                SlayerVars.get().usingSpecialItem = true;

            } else if (NPC.contains("harpie bug swarm") && !Equipment.contains(ItemID.LIT_BUG_LANTERN)) {
                Log.debug("[Debug]: Need to bank for Harpie item");
                SlayerVars.get().shouldBank = true;
                SlayerVars.get().usingSpecialItem = true;

            } else if (NPC.contains("kalphite") && !Inventory.contains(ItemID.SHANTAY_PASS)
                    && !Areas.KALPHITE_AREA.contains(MyPlayer.getTile())) {
                Log.debug("[Debug]: Need to bank for Kalphite item");
                SlayerVars.get().shouldBank = true;

            } else if (NPC.contains("lizard") && (!Inventory.contains(ItemID.ICE_COOLER)
                    || !Inventory.contains(ItemID.SHANTAY_PASS))) {
                Log.debug("[Debug]: Need to bank for Lizard item");
                SlayerVars.get().shouldBank = true;

            } else if (NPC.contains("mutated zygomite") && !Inventory.contains(ItemID.FUNGICIDE_SPRAYER)) {
                Log.debug("[Debug]: Need to bank for Zygomite item");
                SlayerVars.get().shouldBank = true;

            } else if (NPC.contains("rockslug") && (!Inventory.contains(ItemID.BAG_OF_SALT) &&
                    !Equipment.contains(ItemID.BRINE_SABRE))) {
                Log.debug("[Debug]: Need to bank for rockslug item");
                SlayerVars.get().shouldBank = true;

            } else if (NPC.contains("turoth") && !Equipment.contains(ItemID.LEAFBLADED_BATTLEAXE)) {
                Log.debug("[Debug]: Need to bank for turoth item");
                SlayerVars.get().shouldBank = true;
                SlayerVars.get().usingSpecialItem = true;

            } else if (NPC.contains("kurask") && !Equipment.contains(ItemID.LEAFBLADED_BATTLEAXE)) {
                Log.debug("[Debug]: Need to bank for kruask item");
                SlayerVars.get().shouldBank = true;
                SlayerVars.get().usingSpecialItem = true;

            } else if (NPC.contains("wall beast") &&
                    (
                            (!Inventory.contains(ItemID.LIT_CANDLE)) ||
                                    !Inventory.contains(ItemID.TINDERBOX))) {
                Log.debug("[Debug]: Need to bank for wall beast item");
                SlayerVars.get().shouldBank = true;
                SlayerVars.get().usingSpecialItem = true;

            } else if (NPC.contains("wyrm") && !Equipment.contains(ItemID.BOOTS_OF_STONE)) {
                Log.debug("[Debug]: Need to bank for Wyrm item");
                SlayerVars.get().shouldBank = true;
                SlayerVars.get().usingSpecialItem = true;

            } else if (NPC.contains("sourhog") && !isWearingSlayerHelm()
                    && !Equipment.contains(ItemID.REINFORCED_GOGGLES)) {
                Log.debug("[Debug]: Need to bank for Sourhog item");
                SlayerVars.get().shouldBank = true;
                SlayerVars.get().usingSpecialItem = true;

            } else if (NPC.contains("cave bug")) {
                SlayerVars.get().usingSpecialItem = true;
                if (!Inventory.contains(ItemID.LIT_CANDLE)
                        || !Inventory.contains(ItemID.TINDERBOX)) {
                    Log.debug("[Debug]: Need to bank for Cave bug item");
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
        return Equipment.contains(ItemID.SLAYER_HELMET) || Equipment.contains(ItemID.SLAYER_HELMET_I);
    }

    public boolean checkSpecialEquipment(String NPC, String npcContains, int... equipmentIds) {
        if (NPC.contains(npcContains)) {
            SlayerVars.get().usingSpecialItem = true;
            for (int i : equipmentIds) {
                if (!Equipment.contains(i))
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
                Log.debug("[Bank]: Withdrawing " + monkfishToGet + " monkfish to heal at bank");
                BankManager.open(true);
                BankManager.withdraw(monkfishToGet, true, ItemID.MONKFISH);
                for (int i = 0; i < monkfishToGet; i++) {
                    EatUtil.eatFood();
                    Utils.microSleep();
                }
            }
        }
    }

    private boolean weHaveAssignmentItemEquipped() {
        Assign assign = SlayerVars.get().assignment;
        if (assign != null) {
            return assign.getCustomGearList().stream().allMatch(item ->
                    Equipment.contains(item));
        }
        return true; //defaults true;
    }

    public static void mutatedZygomiteInventory() {
        generalInventorySetup(ItemID.LUMBRIDGE_TELEPORT, 5);
        BankManager.withdrawArray(ItemID.FUNGICIDE_SPRAYER, 4);
        BankManager.withdraw(1, true, ItemID.DRAMEN_STAFF);

    }

    private void wyrmInventory() {
        equipSlayerItem(ItemID.BOOTS_OF_STONE, false);
        generalInventorySetup(ItemID.VARROCK_TELEPORT, 10);
        BankManager.withdrawArray(ItemID.PRAYER_POTION, 10);
        BankManager.withdrawArray(ItemID.SKILLS_NECKLACE, 1);

    }

    public void selectLoadOut() {
        if (SlayerVars.get().targets != null) {
            String NPC = SlayerVars.get().targets[0].toLowerCase();

            General.sleep(50);
            if (NPC.contains("aberrant spectre")) {
                Log.debug("[Debug]: Setting Abberent Spectre Inventory and Nose Plug");
                abberantSpectreInventory();
                SlayerVars.get().shouldPrayMagic = true;
            } else if (NPC.contains("abyssal demon")) {
                SlayerVars.get().useExpeditiousBracelet = true;
                generalInventorySetup(ItemID.SKILLS_NECKLACE[2], true, false, -1, false);

            } else if (NPC.contains("ankou")) {
                SlayerVars.get().useExpeditiousBracelet = false;
                SlayerVars.get().useBraceletOfSlaughter = true;
                generalInventorySetup(ItemID.VARROCK_TELEPORT, false, false, -1, false);

            } else if (NPC.contains("banshee")) {
                Log.info("[Debug]: Setting Banshee Inventory and Earmuffs");
              //  bansheeInventory();
                generalInventorySetup(ItemID.SALVE_GRAVEYARD_TELEPORT, true,
                        false, false, ItemID.EARMUFFS, true);

            } else if (NPC.contains("basilisk")) {
                generalInventorySetup(ItemID.CAMELOT_TELEPORT, true, false,
                        ItemID.MIRROR_SHIELD, false);

            } else if (NPC.contains("black demon")) {
                greaterDemonInventory();

            } else if (NPC.contains("black dragon")) {
                blackDragonInventory();

            } else if (NPC.contains("blue dragon")) {
                blueDragonInventory();

            } else if (NPC.contains("bloodveld")) {
                newInventorySetup(ItemID.VARROCK_TELEPORT, false, getBestDhideTop());

            } else if (NPC.contains("green dragon")) {
                greenDragonInventory();

            } else if (NPC.contains("cockatrice")) {
                generalInventorySetup(ItemID.CAMELOT_TELEPORT,
                        true, false, ItemID.MIRROR_SHIELD,
                        false, ItemID.MIRROR_SHIELD);

            } else if (NPC.contains("cave crawler")) {
                generalInventorySetup(ItemID.CAMELOT_TELEPORT, true,
                        false, -1, ItemID.ANTIDOTE_PLUS_PLUS);

            } else if (NPC.contains("crawling hand")) {
                generalInventorySetup(ItemID.VARROCK_TELEPORT);

            } else if (NPC.contains("cave slime"))
                caveSlimeInventory();

            else if (NPC.contains("crocodile"))
                crocodileInventory();

            else if (NPC.contains("dagannoth")) {
                SlayerVars.get().useExpeditiousBracelet = false;
                SlayerVars.get().useBraceletOfSlaughter = true;
                dagannothInventory();

            } else if (NPC.contains("fire giant"))
                generalInventorySetup(ItemID.VARROCK_TELEPORT, true, true);

            else if (NPC.contains("dust devil"))
                generalInventorySetup(ItemID.VARROCK_TELEPORT, false,
                        new ItemRequirement(ItemID.SKILLS_NECKLACE4, 1, 0));

            else if (NPC.contains("gargoyle")) {
                SlayerVars.get().useExpeditiousBracelet = false;
                SlayerVars.get().useBraceletOfSlaughter = true;
                gargoyleInventory();

            } else if (NPC.contains("greater demon")) {
                //  greaterDemonEquipment();
                greaterDemonInventory();

            } else if (NPC.contains("harpie bug swarm")) {
                generalInventorySetup(ItemID.ARDOUGNE_TELEPORT, true, false,
                        ItemID.LIT_BUG_LANTERN, false);

            } else if (NPC.contains("hellhound"))
                generalInventorySetup(ItemID.VARROCK_TELEPORT, true, true);

            else if (NPC.contains("hobgloblin"))
                generalInventorySetup(ItemID.VARROCK_TELEPORT, true,
                        new ItemRequirement(ItemID.BRASS_KEY, 1));

            else if (NPC.contains("infernal mage"))
                generalInventorySetup(ItemID.SALVE_GRAVEYARD_TELEPORT, true,
                        new ItemRequirement(ItemID.PRAYER_POTION4, 10));

            else if (NPC.contains("jelly") || NPC.contains("jellie"))
                generalInventorySetup(ItemID.CAMELOT_TELEPORT, true, false,
                        -1, false);

            else if (NPC.contains("kalphite")) {
                generalInventorySetup(ItemID.VARROCK_TELEPORT, false,
                        new ItemRequirement(ItemID.SHANTAY_PASS, 1));

            } else if (NPC.contains("lesser demon"))
                generalInventorySetup(ItemID.FALADOR_TELEPORT, true, false,
                        -1, false);

            else if (NPC.contains("lizard")) {
                lizardInventory();

            } else if (NPC.contains("mutated zygomite")) {
                Log.debug("muted zygomite inv");
                mutatedZygomiteInventory();

            } else if (NPC.contains("nechryael")) {
                SlayerVars.get().shouldSkipTask = true;

            } else if (NPC.contains("rockslug")) {
                rockSlugInventory();

            } else if (NPC.contains("pyrefiend")) {
                generalInventorySetup(ItemID.CAMELOT_TELEPORT);

            } else if (NPC.contains("turoth")) {
                turothInventory();

            } else if (NPC.contains("kurask")) {
                turothInventory();

            } else if (NPC.contains("troll")) {
                generalInventorySetup(ItemID.CAMELOT_TELEPORT);

            } else if (NPC.contains("wall beast")) {
                caveSlimeInventory();

            } else if (NPC.contains("wyrm")) {
                wyrmInventory();

            } else if (NPC.contains("bear"))
                generalInventorySetup(ItemID.CAMELOT_TELEPORT);

            else if (NPC.contains("bat"))
                generalInventorySetup(ItemID.FALADOR_TELEPORT);

            else if (NPC.contains("bird"))
                generalInventorySetup(ItemID.LUMBRIDGE_TELEPORT);

            else if (NPC.contains("cave bug"))
                caveBugInventory();

            else if (NPC.contains("cow"))
                generalInventorySetup(ItemID.LUMBRIDGE_TELEPORT);

            else if (NPC.contains("crawling hand"))
                generalInventorySetup(ItemID.SALVE_GRAVEYARD_TELEPORT);

            else if (NPC.contains("dog"))
                generalInventorySetup(ItemID.LUMBRIDGE_TELEPORT, true,
                        new ItemRequirement(ItemID.WATERSKIN[0], 4),
                        new ItemRequirement(ItemID.SHANTAY_PASS, 1),
                        new ItemRequirement(ItemID.COINS, 1000));

            else if (NPC.contains("dwarves"))
                generalInventorySetup(ItemID.FALADOR_TELEPORT);

            else if (NPC.contains("ghost"))
                generalInventorySetup(ItemID.FALADOR_TELEPORT);

            else if (NPC.contains("goblin"))
                newInventorySetup(ItemID.LUMBRIDGE_TELEPORT, true);

            else if (NPC.contains("icefiend"))
                newInventorySetup(ItemID.FALADOR_TELEPORT, true);

            else if (NPC.contains("minotaurs"))
                newInventorySetup(ItemID.VARROCK_TELEPORT, true);

            else if (NPC.contains("brine rat"))
                newInventorySetup(ItemID.CAMELOT_TELEPORT, true, new ItemReq(ItemID.SPADE, 1));

            else if (NPC.contains("rat"))
                newInventorySetup(ItemID.LUMBRIDGE_TELEPORT, true);

            else if (NPC.contains("scorpion"))
                newInventorySetup(ItemID.VARROCK_TELEPORT, true);

            else if (NPC.contains("skeleton"))
                newInventorySetup(ItemID.CAMELOT_TELEPORT, true, new ItemReq(ItemID.BRASS_KEY, 1));

            else if (NPC.contains("spider"))
                newInventorySetup(ItemID.VARROCK_TELEPORT, true);

            else if (NPC.contains("sourhog"))
                generalInventorySetup(ItemID.VARROCK_TELEPORT, true, false,
                        ItemID.REINFORCED_GOGGLES, true, ItemID.EXPEDITIOUS_BRACELET);

            else if (NPC.contains("wolves"))
                generalInventorySetup(ItemID.VARROCK_TELEPORT);

            else if (NPC.contains("zombie"))
                generalInventorySetup(ItemID.VARROCK_TELEPORT, true, false, -1, false, ItemID.BRASS_KEY);

            else if (NPC.contains("moss giant"))
                generalInventorySetup(ItemID.VARROCK_TELEPORT);

            else if (NPC.contains("elves"))
                generalInventorySetup(ItemID.IORWERTH_CAMP_TELEPORT);

            else if (NPC.contains("suqah")) {
                suqahInventory();
                SlayerVars.get().shouldPrayMagic = true;
            } else if (NPC.contains("bronze dragon"))
                return;
            else if (NPC.contains("nechryael")){
                generalInventorySetup(ItemID.SALVE_GRAVEYARD_TELEPORT, false,
                        new ItemRequirement(ItemID.PRAYER_POTION_4, 5, 1));
            }

            else {
                Log.debug("[Debug]: No custom inventory determined");
                generalInventorySetup(ItemID.VARROCK_TELEPORT);
            }
        }
    }

    public boolean checkEssentialItems() {
        if (!BankManager.checkInventoryItems(ItemID.VARROCK_TELEPORT, ItemID.MONKFISH) && !SlayerVars.get().shouldSkipTask) {
            Log.debug("[Bank]: Missing monkfish or Varrock teleport - restocking");
            SlayerVars.get().shouldRestock = true;
            return false;
        }
        return true;
    }

    private boolean handleGlory() {
        if (Equipment.contains(ItemID.AMULET_OF_FURY)) {
            BankManager.withdraw(1, true, ItemID.AMULET_OF_GLORY);
            return Inventory.contains(ItemID.AMULET_OF_GLORY);
        }
        return Equipment.contains(ItemID.AMULET_OF_GLORY);
    }

    public boolean checkForDScim() {
        if (missingWeapon()) {
            BankManager.open(true);
            if (Inventory.isFull()) {
                BankManager.deposit(0, SlayerVars.get().customFoodId);
            }
            if (Bank.contains(ItemID.ABYSSAL_WHIP)) {
                Log.debug("[Bank]: We have an Abyssal whip, we will use it for this task");
                BankManager.withdraw(1, true, ItemID.ABYSSAL_WHIP);
                equipItem(ItemID.ABYSSAL_WHIP);
                BankManager.withdraw(0, true, SlayerVars.get().customFoodId);
                BankManager.close(true);
                return true;
            } else if (Bank.contains(ItemID.DRAGON_SCIMITAR)) {
                Log.debug("[Bank]: We have a D Scim, we will use it for this task");
                BankManager.withdraw(1, true, ItemID.DRAGON_SCIMITAR);
                equipItem(ItemID.DRAGON_SCIMITAR);
                BankManager.withdraw(0, true, SlayerVars.get().customFoodId);
                BankManager.close(true);
                return true;
            }
            return false;

        }
        return true;
    }

    public boolean missingWeapon() {
        return Equipment.Slot.WEAPON.getItem().isEmpty();
    }


    public void printReasonForBank() {
        List<InventoryItem> inv =
                Query.inventory().actionContains("Eat").toList();
        if (inv.size() < 3)
            Log.debug("[Bank]: Bank due to low food (<3)");

        if (SlayerVars.get().shouldBank)
            Log.debug("[Bank]: Bank b/c shouldBank variable is true");
    }

    @Override
    public Priority priority() {
        return Priority.HIGH;
    }


    @Override
    public void execute() {
        SlayerVars.get().status = "Bank...";
        Log.log("[SlayerBank]: ");
        //  CannonHandler.pickupCannon();

        printReasonForBank();

        goToVarrockWestBank();

        eatAtBank();


        /*if (SlayerVars.get().magicMeleeGear && !Equipment.Equuipment.contains(Filters.Items.nameContains("d'hide")))
            EquipmentUtils.getDhideFromBank();

        else if (SetGear.checkGear() != null) {
            Log.debug();("[Bank]: Gear needs to be changed", Color.RED);
            SetGear.getGear();
        }*/

        /**
         * This is what actually gets stuff
         */
        selectLoadOut();

        // close bank
        if (Bank.isOpen())
            BankManager.close(true);

        // Set the var to false
        SlayerVars.get().shouldBank = false;

        /**
         * Failsafes
         */
        if (missingWeapon()) {
            if (!checkForDScim()) {
                Log.debug("[Bank]: 2h weapon failsafe, ending script");
                cSkiller.isRunning.set(false);
            }
        }
        if (!checkEssentialItems())
            SlayerVars.get().shouldRestock = true;
    }

    @Override
    public String toString() {
        return "Bank";
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
                (Query.inventory().actionContains("Eat").toList().size() < 3 ||
                        SlayerVars.get().shouldBank) &&
                !SlayerShop.shouldSlayerShop();
    }
}
