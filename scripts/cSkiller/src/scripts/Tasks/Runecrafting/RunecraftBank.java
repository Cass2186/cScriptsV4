package scripts.Tasks.Runecrafting;

import dax.api_lib.DaxWalker;
import dax.api_lib.models.RunescapeBank;
import dax.walker.utils.camera.DaxCamera;
import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.*;
import org.tribot.script.sdk.tasks.Amount;
import org.tribot.script.sdk.tasks.BankTask;
import org.tribot.script.sdk.tasks.BankTaskError;
import org.tribot.script.sdk.tasks.EquipmentReq;
import scripts.*;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.Enums.RunecraftItems;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.Requirements.InventoryRequirement;
import scripts.Requirements.ItemReq;
import scripts.Tasks.MiscTasks.BuyItems;
import scripts.Tasks.Runecrafting.RunecraftData.RcVars;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Optional;

public class RunecraftBank implements Task {

    RSTile COSMIC_PORTAL_TILE = new RSTile(2122, 4833, 0);
    Timer tmr = new Timer(General.random(480000, 900000));// 8-15min
    // does NOT include ROD{1)
    public static final int[] RING_OF_DUELING = {2552, 2554, 2556, 2558, 2560, 2562, 2564};

    public BankTask getBankTaskFromComboRune(RunecraftItems rune) {
        Optional<RunecraftItems> itemOptional = RunecraftItems.getCurrentItem();
        if (itemOptional.isEmpty())
            throw new NullPointerException();

        if (itemOptional.get().equals(RunecraftItems.STEAM_RUNE)) {
            return BankTask.builder()
                    .addEquipmentItem(EquipmentReq.slot(org.tribot.script.sdk.Equipment.Slot.HEAD)
                            .item(rune.getTiaraId(), Amount.of(1)))
                    .addEquipmentItem(EquipmentReq.slot(org.tribot.script.sdk.Equipment.Slot.NECK)
                            .item(ItemId.BINDING_NECKLACE, Amount.of(1)))
                    .addEquipmentItem(EquipmentReq.slot(org.tribot.script.sdk.Equipment.Slot.RING)
                            .chargedItem(rune.getChargedTeleItemBaseName(), 2))
                    .addInvItem(rune.getCombiningRuneId(), Amount.fill(28))
                    .addInvItem(rune.getAdditionalTalisman(), Amount.of(1))
                    .addInvItem(ItemId.PURE_ESSENCE, Amount.fill(1))
                    .build();
        } else if (itemOptional.get().equals(RunecraftItems.FIRE_RUNE)) {
            return BankTask.builder()
                    .addEquipmentItem(EquipmentReq.slot(org.tribot.script.sdk.Equipment.Slot.HEAD)
                            .item(rune.getTiaraId(), Amount.of(1)))
                    .addEquipmentItem(EquipmentReq.slot(org.tribot.script.sdk.Equipment.Slot.RING)
                            .chargedItem("Ring of dueling", 2))
                    .addInvItem(ItemId.PURE_ESSENCE, Amount.fill(1))
                    .build();
        }
        return BankTask.builder()
                .addEquipmentItem(EquipmentReq.slot(org.tribot.script.sdk.Equipment.Slot.HEAD)
                        .item(rune.getTiaraId(), Amount.of(1)))
                .addEquipmentItem(EquipmentReq.slot(org.tribot.script.sdk.Equipment.Slot.RING)
                        .chargedItem("Ring of dueling", 2))
                .addInvItem(rune.getTeleportId(), Amount.fill(2))
                .addInvItem(ItemId.PURE_ESSENCE, Amount.fill(1))
                .build();

    }

    public void progressive() {
        Optional<RunecraftItems> itemOptional = RunecraftItems.getCurrentItem();
        if (itemOptional.isPresent()) {
            PathingUtil.walkToTile(itemOptional.get().getBank().getPosition());

            BankTask task = getBankTaskFromComboRune(itemOptional.get());

            Optional<BankTaskError> err = task.execute();
            if (err.isPresent())
                BuyItems.itemsToBuy = BuyItems.populateBuyList(RunecraftItems.getRequiredItemList());
        }
    }

    public void getItemsFireRunes() {
        if (Inventory.find(ItemId.PURE_ESSENCE).length < 1
                || !Equipment.isEquipped(ItemId.FIRE_TIARA)) {
            goToCWBank();
            open(true);
            depositAll(false);
            Timer.waitCondition(() -> Inventory.getAll().length == 0, 2500, 4000);

            if (getAndEquip(RING_OF_DUELING, "Wear"))
                depositAll(true);

            if (getAndEquip(ItemId.FIRE_TIARA, "Wear"))
                depositAll(true);


            withdraw(0, true, ItemId.PURE_ESSENCE);
            close(true);
        }
    }

    public boolean getAndEquip(int item, String cmd) {
        if (!Equipment.isEquipped(item)) {
            withdraw(1, true, item);
            RSItem[] itm = Inventory.find(item);

            if (itm.length > 0 && itm[0].click(cmd))
                return Timer.waitCondition(() -> Equipment.isEquipped(item), 2500, 3500);
        }
        return Equipment.isEquipped(item);
    }

    public static boolean withdrawArray(int[] array, int num) {
        for (int i = 0; i < array.length; i++) {

            if (withdraw(num, true, array[i]))
                return true;
        }

        return false;
    }

    public boolean getAndEquip(int[] item, String cmd) {
        if (!Equipment.isEquipped(item)) {
            withdrawArray(item, 1);

            RSItem[] itm = Inventory.find(item);
            if (itm.length > 0 && itm[0].click(cmd))
                return Timer.waitCondition(() -> Equipment.isEquipped(item) || Inventory.find(item).length == 0, 1500, 3000);

        }
        return Equipment.isEquipped(item);
    }


    public void rodFailSafe() {
        RSItem[] item = Equipment.find(Filters.Items.nameContains("dueling(1)"));
        if (item.length > 0) {
            General.println("[Debug]: ROD(1) failsafe detected");
            if (getAndEquip(RING_OF_DUELING, "Wear"))
                depositAll(true);
        }
    }

    public boolean itemInInv(int... items) {
        for (int i : items) {
            String name = RSItemDefinition.get(i).getName();
            if (Inventory.find(i).length < 1) {
                General.println("[Debug]: Missing an inventory item: " + name);
                return false;
            } else {
                //General.println("[Debug]: We have " + name);
            }
        }

        return true;
    }

    public void goToCWBank() {
        if (atAltar() && rodTele("Castle Wars")) {
            Timer.waitCondition(() -> Objects.findNearest(20, Filters.Objects.nameContains("Bank chest")).length > 0, 3000, 5000);
            Interfaces.closeAll(); // failsafe
            RSObject[] chest = Objects.findNearest(20, Filters.Objects.nameContains("Bank chest"));

            if (chest.length > 0) {
                if (!chest[0].isClickable())
                    DaxCamera.focus(chest[0]);

                if (DynamicClicking.clickRSObject(chest[0], "Use")) {
                    General.println("[Bank]: Dynamic clicking Bank");
                    Timer.waitCondition(Banking::isBankScreenOpen, 5000, 7000);
                }
            }
        }
    }

    /**
     * Comment this out when uploading to repo
     *
     * @param regularRuneId
     */
    public void getComboItemsImbue(int regularRuneId) {

        ArrayList<ItemReq> itemList = new ArrayList<>();
        itemList.add(new ItemReq(regularRuneId, 0));
        itemList.add(new ItemReq(ItemId.PURE_ESSENCE, 0));
        itemList.add(new ItemReq(ItemId.ASTRAL_RUNE, 0));
        InventoryRequirement invReq = new InventoryRequirement(itemList);

        if (!invReq.check()) {
            RSItem[] p = Inventory.find(Filters.Items.nameContains("pouch").and(Filters.Items.actionsContains("Fill")));

            goToCWBank();

            if (!tmr.isRunning() && RcVars.get().shouldAfk) {
                Utils.afk(General.random(15000, 60000));
                tmr.reset();
            }

            open(true);

            invReq.withdrawItems();

            getPouches();
            fillPouches();

            if (!Inventory.isFull())
                invReq.withdrawItems();

            Keyboard.pressKeys(KeyEvent.VK_ESCAPE);
            General.sleep(General.random(300, 500));
            close(true);

            Utils.hoverXp(Skills.SKILLS.RUNECRAFTING, 5);
            if (!checkItems()) {
                //scripts.cRunecrafting.Main.isRunning = false;
            }
        }

    }

    public boolean getPouches() {
        RSItem[] invPouch = Inventory.find(ItemId.ALL_POUCHES);
        if (invPouch.length < 3 && RcVars.get().usingLunarImbue) {
            // gets pouches in case we deposited them somehow
            withdraw(0, true, ItemId.ALL_POUCHES[0]);
            withdraw(0, true, ItemId.ALL_POUCHES[1]);
            withdraw(0, true, ItemId.ALL_POUCHES[2]);
            return true;
        }
        return false;
    }

    public void fillPouches() {
        RSItem[] p = Inventory.find(Filters.Items.nameContains("pouch").and(Filters.Items.actionsContains("Fill")));
        for (RSItem pouch : p) {
            if (pouch.click("Fill"))
                AntiBan.waitItemInteractionDelay();
        }
        General.sleep(General.random(500, 1200));
    }


    public void getItemsComboRunes(int talisman, int regularRune, boolean shouldAfk) {
        if (!itemInInv(talisman, regularRune, ItemId.PURE_ESSENCE)) {


            RSItem[] p = Inventory.find(Filters.Items.nameContains("pouch").and(Filters.Items.actionsContains("Fill")));

            goToCWBank();

            if (!tmr.isRunning() && RcVars.get().shouldAfk) {
                Utils.afk(General.random(15000, 60000));
                tmr.reset();
            }

            open(true);
            if (RcVars.get().usingLunarImbue && p.length > 0)
                depositAllExcept(false, regularRune, ItemId.ASTRAL_RUNE,
                        ItemId.ALL_POUCHES[0], ItemId.ALL_POUCHES[1], ItemId.ALL_POUCHES[2]);
            else
                depositAllExcept(false, regularRune, ItemId.ASTRAL_RUNE, talisman);

            Timer.waitCondition(() -> Inventory.getAll().length < 6, 1500, 2000);


            rodFailSafe();

            if (getAndEquip(RING_OF_DUELING, "Wear"))
                depositAllExcept(false, regularRune, ItemId.ASTRAL_RUNE,
                        ItemId.ALL_POUCHES[0], ItemId.ALL_POUCHES[1], ItemId.ALL_POUCHES[2]);

            if (getAndEquip(ItemId.MYSTIC_STEAM_STAFF, "Wield"))
                depositAllExcept(false, regularRune, ItemId.ASTRAL_RUNE,
                        ItemId.ALL_POUCHES[0], ItemId.ALL_POUCHES[1], ItemId.ALL_POUCHES[2]);

            if (getAndEquip(ItemId.FIRE_TIARA, "Wear"))
                depositAllExcept(false, regularRune, ItemId.ASTRAL_RUNE,
                        ItemId.ALL_POUCHES[0], ItemId.ALL_POUCHES[1], ItemId.ALL_POUCHES[2]);

            if (RcVars.get().useStamina && Utils.getVarBitValue(25) == 0 && Game.getRunEnergy() < General.random(65, 80)) {
                getAndEquip(ItemId.STAMINA_POTION, "Drink");
                depositAllExcept(false, regularRune, ItemId.ASTRAL_RUNE,
                        ItemId.ALL_POUCHES[0], ItemId.ALL_POUCHES[1], ItemId.ALL_POUCHES[2]);
            }

            if (getPouches())
                p = Inventory.find(Filters.Items.nameContains("pouch").and(Filters.Items.actionsContains("Fill")));

            if (getAndEquip(ItemId.BINDING_NECKLACE, "Wear"))
                depositAllExcept(false, regularRune, ItemId.ASTRAL_RUNE,
                        ItemId.ALL_POUCHES[0], ItemId.ALL_POUCHES[1], ItemId.ALL_POUCHES[2]);

            if (!itemInInv(regularRune))
                depositAllExcept(false, regularRune, ItemId.ASTRAL_RUNE,
                        ItemId.ALL_POUCHES[0], ItemId.ALL_POUCHES[1], ItemId.ALL_POUCHES[2]);


            // if not using lunars, get a talisman
            if (!RcVars.get().usingLunarImbue)
                withdraw(1, true, talisman);
            else {
                withdraw(0, true, ItemId.ASTRAL_RUNE);
            }

            // get essence and binding rune
            withdraw(0, true, regularRune);
            withdraw(0, true, ItemId.PURE_ESSENCE);

            // fill pouches (if we have any)
            fillPouches();

            // refill inv with essence if needed
            if (!Inventory.isFull())
                withdraw(0, true, ItemId.PURE_ESSENCE);

            Keyboard.pressKeys(KeyEvent.VK_ESCAPE);
            General.sleep(General.random(300, 500));
            close(true);

            Utils.hoverXp(Skills.SKILLS.RUNECRAFTING, 5);
            if (!checkItems()) {

            }
        }
    }

    public boolean checkItems() {
        if (!Equipment.isEquipped(ItemId.BINDING_NECKLACE)
                || !Equipment.isEquipped(RING_OF_DUELING)) {
            General.println("[Debug]: Missing equipped binding or dueling, ending");
            return false;
        } else if (!RcVars.get().lava) {
            if (Inventory.find(ItemId.WATER_RUNE).length == 0) {
                General.println("[Debug]: Missing water runes in inventory, ending");
                return false;
            }
            if (!RcVars.get().usingLunarImbue && Inventory.find(ItemId.WATER_TALISMAN).length == 0) {
                General.println("[Debug]: Missing water talisman in inventory, ending");
                return false;
            }
        } else if (RcVars.get().lava) {
            if (Inventory.find(ItemId.EARTH_RUNE).length == 0) {
                General.println("[Debug]: Missing earth runes in inventory, ending");
                return false;
            }
            if (!RcVars.get().usingLunarImbue && Inventory.find(ItemId.EARTH_TALISMAN).length == 0) {
                General.println("[Debug]: Missing earth talisman in inventory, ending");
                return false;
            }
        } else if (RcVars.get().usingLunarImbue && Inventory.find(ItemId.ASTRAL_RUNE).length == 0) {
            General.println("[Debug]: Missing astral runes in inventory, ending");
            return false;
        }
        return true;
    }


  /*  public void getAbyssItems() {
        //GoToAbyss.gloryTeleport("Edgeville");
        if (!itemInInv(ItemId.PURE_ESSENCE)) {

            if (RcVars.get().zanarisCrafting) {
                RSObject[] altar = Objects.findNearest(30, Filters.Objects.nameContains("Altar").
                        and(Filters.Objects.actionsNotContains("Pray")));
                if (altar.length > 0) {
                    Utilities.blindWalkToTile(COSMIC_PORTAL_TILE);
                    if (Utilities.clickObject("Portal", "Use")) {
                        Timer.waitCondition(() -> Objects.findNearest(30, Filters.Objects.nameContains("Altar")).length == 0, 5000, 7000);
                        Utils.shortSleep();
                    }
                }
                General.println("[Debug]: Going to Zanaris bank");
                PathingUtil.walkToArea(ItemId.ZANARIS_BANK, false);
            }

            RSItem[] p = Inventory.find(Filters.Items.nameContains("pouch").and(Filters.Items.actionsContains("Fill")));

            if (!tmr.isRunning() && RcVars.get().shouldAfk) {
                Utils.afk(General.random(15000, 90000));
                tmr.reset();
            }
            if (!Banking.isBankScreenOpen())
                open(true);

            if (RcVars.get().usingLunarImbue && p.length > 0) {
                depositAllExcept(true, ItemId.DEGRADED_LARGE_POUCH, ItemId.ALL_POUCHES[0], ItemId.ALL_POUCHES[1], ItemId.ALL_POUCHES[2]);
                Timer.waitCondition(() -> Inventory.getAll().length < 4, 1500, 2000);
            }

            while (Combat.getHP() + 6 < Skills.getActualLevel(Skills.SKILLS.HITPOINTS)) {
                General.println("[Bank]: Eating food");
                withdraw(1, true, ItemId.MONKFISH);
                EatUtil.eatFood();
                General.sleep(100);
            }


            if (!RcVars.get().zanarisCrafting && getAndEquip(ItemId.AMULET_OF_GLORY, "Wear"))
                depositAllExcept(true, ItemId.DEGRADED_LARGE_POUCH, ItemId.ALL_POUCHES[0], ItemId.ALL_POUCHES[1], ItemId.ALL_POUCHES[2]);

            if (RcVars.get().useStamina && Utils.getVarBitValue(25) == 0 || Game.getRunEnergy() < General.random(75, 80)) {
                getAndEquip(ItemId.STAMINA_POTION, "Drink");
                //drinks twice if needed
                if (Game.getRunEnergy() < 60)
                    getAndEquip(ItemId.STAMINA_POTION, "Drink");
                depositAllExcept(true, ItemId.DEGRADED_LARGE_POUCH, ItemId.ALL_POUCHES[0], ItemId.ALL_POUCHES[1], ItemId.ALL_POUCHES[2]);
            }

            if (!Equipment.isEquipped(Filters.Items.nameContains("axe")) && !RcVars.get().zanarisCrafting)
                getAndEquip(ItemId.RUNE_AXE, "Wield");

            if (!RcVars.get().zanarisCrafting)
                withdraw(1, true, ItemId.MONKFISH);

            RSItem[] invPouch = Inventory.find(ItemId.ALL_POUCHES);
            if (invPouch.length < 3) {
                // gets pouches in case we deposited them somehow
                withdraw(0, true, ItemId.ALL_POUCHES[0]);
                withdraw(0, true, ItemId.ALL_POUCHES[1]);
                withdraw(0, true, ItemId.ALL_POUCHES[2]);
            }


            // get essence
            withdraw(0, true, ItemId.PURE_ESSENCE);

            // fill pouches (if we have any)
            for (RSItem pouch : p) {
                if (pouch.click("Fill"))
                    AntiBan.waitItemInteractionDelay();
            }

            Utils.shortSleep();

            if (!Inventory.isFull())
                withdraw(0, true, ItemId.PURE_ESSENCE);

            Keyboard.pressKeys(KeyEvent.VK_ESCAPE);
            General.sleep(General.random(300, 500));
            close(true);

            Utils.hoverXp(Skills.SKILLS.RUNECRAFTING, 5);
        }
    }
*/

    public void getItemsRegularRunes(int tiara, boolean useStamina, boolean useTeleTab, int tabId, RunescapeBank bank) {
        if (Inventory.find(ItemId.PURE_ESSENCE).length < 1 || !Equipment.isEquipped(tiara)) {
            General.println("[Debug]: Getting Items from Bank: " + bank.toString());
            PathingUtil.walkToTile(bank.getPosition(), 2, true);

            open(true);

            if (useStamina)
                depositAllExcept(true, tabId, ItemId.STAMINA_POTION[0], ItemId.STAMINA_POTION[1], ItemId.STAMINA_POTION[2], ItemId.STAMINA_POTION[3]);
            else
                depositAllExcept(true, tabId);

            if (!Equipment.isEquipped(tiara)) {
                withdraw(1, true, tiara);
                Utils.equipItem(tiara);
            }
            if (RcVars.get().useStamina && Inventory.find(Filters.Items.nameContains("Stamina")).length == 0)
                withdrawArray(ItemId.STAMINA_POTION, 1);

            if (useTeleTab)
                withdraw(1, true, tabId);

            withdraw(0, true, ItemId.PURE_ESSENCE);
            General.sleep(General.random(250, 750));
            close(true);
        }
    }

    public static boolean close(boolean shouldWait) {
        return !Banking.close() || !shouldWait || Timer.waitCondition(() -> !Banking.isBankScreenOpen(), 3000);
    }

    public static boolean rodTele(String location) {
        RSItem[] rod = Equipment.find(RING_OF_DUELING);
        if (rod.length > 0) {
            for (int i = 0; i < 3; i++) {
                General.println("[Teleport Manager]: Going to " + location);
                if (rod[0].click(location))
                    return Timer.waitCondition(() -> !atAltar(), 6000, 8000);
            }
        }

        return false;
    }

    public static boolean depositAll(boolean shouldWait) {
        if (Inventory.getAll().length < 1) { // prevents it from pushing the button if the inventory is already empty
            return true;
        }
        if (!shouldWait) {
            return Banking.depositAll() <= 0; // no check if it was successful

        } else {
            if (!Banking.isBankScreenOpen()) {
                open(true);
                closeHelpWindow();
            }
            General.println("[Bank]: Depositing all");

            Banking.depositAll();
            Timer.waitCondition(() -> Inventory.getAll().length == 0, 2000, 5000);

            if (Inventory.getAll().length == 0)  // this checks if it was successful and if not it will try 1 more time
                return true;
            else {
                General.println("[Bank]: Depositing all attempt 2");
                return Banking.depositAll() <= 0 || Timer.waitCondition(() -> inventoryChange(false), 5000);
            }
        }
    }


    public static boolean gloryTele(String location) {
        RSItem[] rod = Equipment.find(ItemId.AMULET_OF_GLORY);
        if (rod.length > 0) {
            for (int i = 0; i < 3; i++) {
                General.println("[Teleport Manager]: Going to " + location);
                if (rod[0].click(location))
                    return Timer.waitCondition(() -> !atAltar(), 6000, 8000);
            }
        }

        return false;
    }

    public static final int PARENT_INTERFACE_NPC_CONTACT = 75;
    public static final int PARENT_EQUIPMENT_INTERFACE = 387;

    public static boolean rodHover() {
        GameTab.open(GameTab.TABS.EQUIPMENT);
        RSItem[] rod = Equipment.find(RING_OF_DUELING);

        if (GameTab.getOpen().equals(GameTab.TABS.EQUIPMENT)) {
            RSInterface inter = Interfaces.findWhereAction("Castle Wars", PARENT_EQUIPMENT_INTERFACE);
            if (inter != null)
                return inter.hover();
        }
        if (rod.length > 0) {
            General.println("[Teleport Manager]: Hovering");
            return rod[0].hover();
        }
        General.println("[Debug]: Hover Failed");
        return false;
    }


    public static boolean atAltar() {
        RSObject[] altar = Objects.findNearest(20, "Altar");
        return altar.length > 0;
    }


    public void stuckFailSafe() {
        //  if (!RcVars\
        //.get().zanarisCrafting) {
        RSItem[] eqp = Equipment.find(Filters.Items.nameContains("dueling"));
        RSObject[] portal = Objects.findNearest(30, Filters.Objects.nameContains("Portal"));
        RSObject[] altar = Objects.findNearest(20, Filters.Objects.nameContains("Altar"));
        if (eqp.length == 0 && portal.length > 0 && altar.length > 0) {
            General.println("[Debug]: We appear to be stuck, leaving via portal", Color.RED);
            if (Utils.clickObject(portal[0], "Use", true)) {
                Timer.waitCondition(() -> Objects.findNearest(20, Filters.Objects.nameContains("Altar")).length == 0,
                        10000, 12000);
                PathingUtil.walkToTile(RunescapeBank.DUEL_ARENA.getPosition(), 3, true);
            }
        }
        //}
    }


    public static int getLevel() {
        return Skills.getCurrentLevel(Skills.SKILLS.RUNECRAFTING);
    }


    /**
     * BANKING BASIC FUNCTIONS
     */
    private static void closeHelpWindow() {
        if (Interfaces.isInterfaceSubstantiated(664, 28))
            if (Interfaces.get(664, 28, 0).click()) {
                Timer.waitCondition(() -> !Interfaces.isInterfaceSubstantiated(664, 28), 5000);
                General.sleep(General.random(300, 1200));
            }
    }

    public static boolean depositAllExcept(boolean shouldWait, int... id) {
        closeHelpWindow();
        return Banking.depositAllExcept(id) <= 0 || !shouldWait || Timer.waitCondition(() -> inventoryChange(false), 5000);
    }


    public static boolean inventoryChange(boolean increase) {
        final int count = Inventory.getAll().length;
        General.sleep(100);
        return increase ? count < Inventory.getAll().length : count > Inventory.getAll().length;
        // says if true in the method call parameters, returns that inventory has increased size (length),
    }


    private static boolean checkWithdrawSuccess(int quantity, int itemID) {
        Timer.waitCondition(() -> Inventory.find(itemID).length > 0, General.random(800, 1500));
        if (Inventory.find(itemID).length < 1 && !Inventory.isFull()) {
            General.println("[Debug]: Seemingly failed to get item, trying again.");
            return withdraw(quantity, true, itemID);
        } else
            return true;
    }

    public static boolean open(boolean shouldWait) {
        if (Banking.isBankScreenOpen())
            return true;

        if (!Banking.isInBank()) {
            General.println("[Bank]: Walking to Bank");
            for (int i = 0; i < 3; i++) { //tries 3 times to make a dax call to walk to bank
                if (DaxWalker.walkToBank()) {
                    Timer.abc2WaitCondition(Banking::isInBank, 8000, 10000);
                    break;
                } else
                    General.sleep(General.random(2500, 4000));
            }

        }

        // will try 3 times to open the bank
        // loop breaks if successful
        if (!Banking.isBankScreenOpen()) {
            for (int i = 0; i < 3; i++) {
                if (Banking.openBank()) {
                    Timer.waitCondition(Banking::isBankScreenOpen, 5000, 7000);
                    if (Banking.isBankScreenOpen())
                        break;
                }
            }
        }
        closeHelpWindow();
        return (!Banking.openBank() || !shouldWait || Timer.waitCondition(Banking::isBankScreenOpen, 5000));
    }

    public static boolean withdraw(int num, boolean shouldWait, int itemId) {

        open(true);

        closeHelpWindow();

        RSItem[] item = Banking.find(itemId);

        if (item.length < 1) {
            String name = RSItemDefinition.get(itemId).getName();
            if (name != null)
                General.println("[Debug]: Missing item: " + name);
            else
                General.println("[Debug]: Missing item");
            return false;
        }

        for (int i = 0; i < 5; i++) {
            if (!shouldWait) {
                if (Banking.withdraw(num, itemId)) {
                    General.sleep(General.random(150, 500));
                    return true;

                } else
                    General.sleep(General.random(350, 800));

            } else {
                if (Banking.withdraw(num, itemId)) {
                    if (Timer.waitCondition(() -> inventoryChange(true) || Inventory.find(itemId).length > 0, 4000, 6000))
                        return true;

                    else
                        General.sleep(General.random(350, 800));
                }
            }
        }
        return false;
    }

    // sometimes this is accidentally clicked
    public void closeLootInterface() {
        Optional<RSInterface> inter =
                Optional.ofNullable(Interfaces.findWhereAction("Close", 464));
        inter.ifPresent(RSInterface::click);
        ;
    }

    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        RSItem[] tiaras = Inventory.find(ItemId.TIARA);
        RSItem[] talisman = Inventory.find(Filters.Items.nameContains("talisman"));
        if (Vars.get().currentTask != null && Vars.get().currentTask.equals(SkillTasks.RUNECRAFTING)) {
            General.println("Abyss: " + RcVars.get().abyssCrafting + " ; Lunars: " +
                    RcVars.get().usingLunarImbue + " ; Zanaris: "
                    + RcVars.get().zanarisCrafting);
            // if (getLevel() < 14) {
            //       return tiaras.length == 0 || talisman.length == 0;

            //   } else
            if (getLevel() < 14) {
                return Inventory.find(ItemId.PURE_ESSENCE).length < 1 || !Equipment.isEquipped(ItemId.EARTH_TIARA);

            } else if (getLevel() < 19) {
                return (Inventory.find(ItemId.PURE_ESSENCE).length < 1
                        || !Equipment.isEquipped(ItemId.FIRE_TIARA));

            } else if (RcVars.get().lava && !RcVars.get().abyssCrafting && !RcVars.get().usingLunarImbue) {
                General.println("Lava runes at ine 230 of bank");
                return !itemInInv(ItemId.EARTH_TALISMAN, ItemId.EARTH_RUNE, ItemId.PURE_ESSENCE);

            } else if (!RcVars.get().abyssCrafting && !RcVars.get().zanarisCrafting) {

                if (RcVars.get().usingLunarImbue && !RcVars.get().lava)
                    return !itemInInv(ItemId.WATER_RUNE, ItemId.PURE_ESSENCE);

                else if (RcVars.get().usingLunarImbue && RcVars.get().lava)
                    return !itemInInv(ItemId.EARTH_RUNE, ItemId.PURE_ESSENCE);

                return !itemInInv(ItemId.WATER_RUNE, ItemId.WATER_TALISMAN, ItemId.PURE_ESSENCE);

            } else if (RcVars.get().abyssCrafting || RcVars.get().zanarisCrafting) {
                return Inventory.find(ItemId.PURE_ESSENCE).length == 0;

            }
        }
        return false;
    }

    @Override
    public void execute() {
        closeLootInterface();
        // stuckFailSafe();
        progressive();
      /*  if (getLevel() < 14) {
            // get tiaras
        } else if (getLevel() < 14) {
            //  getItemsRegularRunes(ItemId.EARTH_TIARA, true, true, ItemId.VARROCK_TELEPORT, RunescapeBank.VARROCK_EAST);

        } else if (getLevel() < 19) {
            getItemsFireRunes();

        } else if (RcVars.get().lava && !RcVars.get().abyssCrafting && !RcVars.get().usingLunarImbue) {
            General.println("Lava - no lunars");
            getItemsComboRunes(ItemId.EARTH_TALISMAN, ItemId.EARTH_RUNE, true);


        } else if (!RcVars.get().abyssCrafting && !RcVars.get().lava && !RcVars.get().zanarisCrafting) {
            //steam runes
            //talismanInvTsk.execute();
            getItemsComboRunes(ItemId.WATER_TALISMAN, ItemId.WATER_RUNE, true);
            RSTile current = Player.getPosition();
            if (Inventory.find(ItemId.PURE_ESSENCE).length > 0) {
                rodTele("Duel Arena");
                Timer.waitCondition(() -> !Player.getPosition().equals(current), 3500, 5000);
            }
        } else if (RcVars.get().lava && !RcVars.get().abyssCrafting
                && RcVars.get().usingLunarImbue && !RcVars.get().zanarisCrafting) {
            General.println("[Debug]: Lava - with lunars");
            getItemsComboRunes(ItemId.EARTH_TALISMAN, ItemId.EARTH_RUNE, true);
            RSTile current = Player.getPosition();
            rodTele("Duel Arena");
            Timer.waitCondition(() -> !Player.getPosition().equals(current), 3500, 5000);

        } else if (RcVars.get().abyssCrafting || RcVars.get().zanarisCrafting) {
            // getAbyssItems();
          /*  if (Inventory.find(Const.DEGRADED_LARGE_POUCH).length > 0) {
                General.println("[Debug]: We need to repair a pouch");
                Vars.get().needToRepairPouches = true;
            }
        }*/
    }

    @Override
    public String toString() {
        return "Banking";
    }

    @Override
    public String taskName() {
        return "Runecraft";
    }
}
