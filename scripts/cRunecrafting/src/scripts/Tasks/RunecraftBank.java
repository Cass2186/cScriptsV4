package scripts.Tasks;

import org.tribot.api2007.Equipment;
import org.tribot.script.sdk.*;
import dax.api_lib.models.RunescapeBank;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.*;
import org.tribot.script.sdk.GameTab;
import org.tribot.script.sdk.Inventory;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.tasks.Amount;
import org.tribot.script.sdk.tasks.BankTask;
import org.tribot.script.sdk.tasks.BankTaskError;
import org.tribot.script.sdk.tasks.EquipmentReq;
import org.tribot.script.sdk.types.GameObject;
import org.tribot.script.sdk.types.InventoryItem;
import org.tribot.script.sdk.types.Widget;
import scripts.*;

import scripts.Data.Const;
import scripts.Data.RunecraftItems;

import java.util.List;

import scripts.Data.Vars;


import javax.swing.text.html.Option;
import java.util.Optional;

public class RunecraftBank implements Task {


    Timer tmr = new Timer(General.random(480000, 900000));// 8-15min

    // does NOT include ROD{1)
    public static final int[] RING_OF_DUELING = {2552, 2554, 2556, 2558, 2560, 2562, 2564};

    public BankTask getBankTaskFromComboRune(RunecraftItems rune) {
        Optional<RunecraftItems> itemOptional = RunecraftItems.getCurrentItem();
        if (itemOptional.isEmpty())
            throw new NullPointerException("Missing bank item, ending");

        if (Vars.get().useRingOfElements && itemOptional.get().equals(RunecraftItems.STEAM_RUNE)) {
            return BankTask.builder()
                    .addEquipmentItem(EquipmentReq.slot(org.tribot.script.sdk.Equipment.Slot.HEAD)
                            .item(rune.getTiaraId(), Amount.of(1)))
                    .addEquipmentItem(EquipmentReq.slot(org.tribot.script.sdk.Equipment.Slot.NECK)
                            .item(ItemID.BINDING_NECKLACE, Amount.of(1)))
                    .addEquipmentItem(EquipmentReq.slot(org.tribot.script.sdk.Equipment.Slot.RING)
                            .chargedItem(rune.getChargedTeleItemBaseName(), 2))
                    .addInvItem(rune.getCombiningRuneId(), Amount.fill(28))
                    .addInvItem(ItemID.CHARGED_RING_OF_ELEMENTS, Amount.of(1))
                    .addInvItem(rune.getAdditionalTalisman(), Amount.of(1))
                    .addInvItem(ItemID.PURE_ESSENCE, Amount.fill(1))
                    .build();
        } else if (itemOptional.get().equals(RunecraftItems.STEAM_RUNE)) {
            return BankTask.builder()
                    .addEquipmentItem(EquipmentReq.slot(org.tribot.script.sdk.Equipment.Slot.HEAD)
                            .item(rune.getTiaraId(), Amount.of(1)))
                    .addEquipmentItem(EquipmentReq.slot(org.tribot.script.sdk.Equipment.Slot.NECK)
                            .item(ItemID.BINDING_NECKLACE, Amount.of(1)))
                    .addEquipmentItem(EquipmentReq.slot(org.tribot.script.sdk.Equipment.Slot.RING)
                            .chargedItem(rune.getChargedTeleItemBaseName(), 2))
                    .addInvItem(rune.getCombiningRuneId(), Amount.fill(28))
                    .addInvItem(rune.getAdditionalTalisman(), Amount.of(1))
                    .addInvItem(ItemID.PURE_ESSENCE, Amount.fill(1))
                    .build();
        } else if (itemOptional.get().equals(RunecraftItems.FIRE_RUNE)) {
            return BankTask.builder()
                    .addEquipmentItem(EquipmentReq.slot(org.tribot.script.sdk.Equipment.Slot.HEAD)
                            .item(rune.getTiaraId(), Amount.of(1)))
                    .addEquipmentItem(EquipmentReq.slot(org.tribot.script.sdk.Equipment.Slot.RING)
                            .chargedItem("Ring of dueling", 2))
                    .addInvItem(ItemID.PURE_ESSENCE, Amount.fill(1))
                    .build();
        }
        return BankTask.builder()
                .addEquipmentItem(EquipmentReq.slot(org.tribot.script.sdk.Equipment.Slot.HEAD)
                        .item(rune.getTiaraId(), Amount.of(1)))
                .addEquipmentItem(EquipmentReq.slot(org.tribot.script.sdk.Equipment.Slot.RING)
                        .chargedItem("Ring of dueling", 2))
                .addInvItem(rune.getTeleportId(), Amount.fill(2))
                .addInvItem(ItemID.PURE_ESSENCE, Amount.fill(1))
                .build();

    }

    public void progressiveBank() {
        Optional<RunecraftItems> itemOptional = RunecraftItems.getCurrentItem();
        if (itemOptional.isPresent()) {
            PathingUtil.walkToTile(itemOptional.get().getBank().getPosition());

            BankTask task = getBankTaskFromComboRune(itemOptional.get());

            Optional<BankTaskError> err = task.execute();
            err.ifPresent(e -> Log.error("Banking error " + e.toString()));
            //if (err.isPresent())
            //   BuyItems.itemsToBuy = BuyItems.populateBuyList(RunecraftItems.getRequiredItemList());
        }

    }

    public boolean getAndEquip(int item, String cmd) {
        if (!Equipment.isEquipped(item)) {
            BankManager.withdraw(1, true, item);
            List<InventoryItem> itm = Query.inventory().idEquals(item).toList();
            if (itm.size() > 0 && itm.get(0).click(cmd))
                return Timer.waitCondition(() -> Equipment.isEquipped(item), 2500, 3500);
        }
        return Equipment.isEquipped(item);
    }

    public boolean getAndEquip(int[] item, String cmd) {
        if (!Equipment.isEquipped(item)) {
            BankManager.withdrawArray(item, 1);
            List<InventoryItem> itm = Query.inventory().idEquals(item).toList();
            if (itm.size() > 0 && itm.get(0).click(cmd))
                return Timer.waitCondition(() -> Equipment.isEquipped(item) ||
                        Inventory.contains(item), 1500, 3000);

        }
        return Equipment.isEquipped(item);
    }


    public void rodFailSafe() {
        RSItem[] item = Equipment.find(Filters.Items.nameContains("dueling(1)"));
        if (item.length > 0) {
            General.println("[Debug]: ROD(1) failsafe detected");
            if (getAndEquip(RING_OF_DUELING, "Wear"))
                BankManager.depositAll(true);
        }
    }

    public boolean itemInInv(int... items) {
        for (int i : items) {
            String name = RSItemDefinition.get(i).getName();
            if (!Inventory.contains(i)) {
                Log.error("Missing an inventory item: " + name);
                return false;
            } else {
                //General.println("[Debug]: We have " + name);
            }
        }

        return true;
    }

    public void goToCWBank() {
        //loop twice b/c sometimes the tele misclicks and opens loot interface
        for (int i = 0; i < 2; i++) {

            if (!CraftRunes.atAltar())
                break;

            closeLootInterface();
            if (rodTele("Castle Wars")) {
                Timer.waitCondition(() -> Query.gameObjects()
                        .nameContains("Bank chest")
                        .isAny(), 3000, 5000);

                Optional<GameObject> chest = Query.gameObjects()
                        .nameContains("Bank chest")
                        .findBestInteractable();

                if (chest.map(c -> c.interact("Bank")).orElse(false)) {
                    Timer.waitCondition(Bank::isOpen, 5000, 7000);
                    return;
                }
            }
        }
    }


    public boolean getPouches() {
        List<InventoryItem> invPouch = CraftRunes.getPouches();
        if (invPouch.size() < 3 && Vars.get().usingLunarImbue) {
            // gets pouches in case we deposited them somehow
            BankManager.withdraw(0, true, ItemID.ALL_POUCHES[0]);
            BankManager.withdraw(0, true, ItemID.ALL_POUCHES[1]);
            BankManager.withdraw(0, true, ItemID.ALL_POUCHES[2]);
            return true;
        }
        return false;
    }

    public void fillPouches() {
        List<InventoryItem> p = CraftRunes.getPouches();
        for (InventoryItem pouch : p) {
            if (pouch.click("Fill"))
                Waiting.waitNormal(60, 15);
        }
        Waiting.waitUniform(600, 1200);
    }


    public void getItemsComboRunes(int talisman, int regularRune) {
        if (!itemInInv(talisman, regularRune, ItemID.PURE_ESSENCE)) {

            List<InventoryItem> p = CraftRunes.getPouches();
            goToCWBank();

            //TODO handle globally
            if (!tmr.isRunning() && Vars.get().shouldAfk) {
                Utils.afk(General.random(15000, 60000));
                tmr.reset();
            }

            BankManager.open(true);

            if (Vars.get().usingLunarImbue && p.size() > 0) {
                BankManager.depositAllExcept(false, regularRune, ItemID.ASTRAL_RUNE,
                        ItemID.ALL_POUCHES[0], ItemID.ALL_POUCHES[1], ItemID.ALL_POUCHES[2]);
                Timer.waitCondition(() -> Inventory.getAll().size() < 6, 1500, 2000);
            } else
                BankManager.depositAllExcept(false, regularRune, ItemID.ASTRAL_RUNE, talisman);

            rodFailSafe();

            if (getAndEquip(RING_OF_DUELING, "Wear"))
                BankManager.depositAllExcept(false, regularRune, ItemID.ASTRAL_RUNE,
                        ItemID.ALL_POUCHES[0], ItemID.ALL_POUCHES[1], ItemID.ALL_POUCHES[2]);

            if (getAndEquip(ItemID.MYSTIC_STEAM_STAFF, "Wield"))
                BankManager.depositAllExcept(false, regularRune, ItemID.ASTRAL_RUNE,
                        ItemID.ALL_POUCHES[0], ItemID.ALL_POUCHES[1], ItemID.ALL_POUCHES[2]);

            if (getAndEquip(ItemID.FIRE_TIARA, "Wear"))
                BankManager.depositAllExcept(false, regularRune, ItemID.ASTRAL_RUNE,
                        ItemID.ALL_POUCHES[0], ItemID.ALL_POUCHES[1], ItemID.ALL_POUCHES[2]);

            if (Vars.get().useStamina && Utils.getVarBitValue(25) == 0 && Game.getRunEnergy() < General.random(65, 80)) {
                getAndEquip(ItemID.STAMINA_POTION, "Drink");
                BankManager.depositAllExcept(false, regularRune, ItemID.ASTRAL_RUNE,
                        ItemID.ALL_POUCHES[0], ItemID.ALL_POUCHES[1], ItemID.ALL_POUCHES[2]);
            }

            if (getPouches())
                p = CraftRunes.getPouches();

            if (getAndEquip(ItemID.BINDING_NECKLACE, "Wear"))
                BankManager.depositAllExcept(false, regularRune, ItemID.ASTRAL_RUNE,
                        ItemID.ALL_POUCHES[0], ItemID.ALL_POUCHES[1], ItemID.ALL_POUCHES[2]);

            if (!itemInInv(regularRune))
                BankManager.depositAllExcept(false, regularRune, ItemID.ASTRAL_RUNE,
                        ItemID.ALL_POUCHES[0], ItemID.ALL_POUCHES[1], ItemID.ALL_POUCHES[2]);


            // if not using lunars, get a talisman
            if (!Vars.get().usingLunarImbue)
                BankManager.withdraw(1, true, talisman);
            else {
                BankManager.withdraw(0, true, ItemID.ASTRAL_RUNE);
            }

            // get essence and binding rune
            BankManager.withdraw(0, true, regularRune);
            BankManager.withdraw(0, true, ItemID.PURE_ESSENCE);

            // fill pouches (if we have any)
            fillPouches();

            // refill inv with essence if needed
            if (!Inventory.isFull())
                BankManager.withdraw(0, true, ItemID.PURE_ESSENCE);

            close(true);

            Utils.hoverXp(Skills.SKILLS.RUNECRAFTING, 5);
            if (!checkItems()) {

            }
        }
    }

    public boolean checkItems() {
        if (!Equipment.isEquipped(ItemID.BINDING_NECKLACE)
                || !Equipment.isEquipped(RING_OF_DUELING)) {
            General.println("[Debug]: Missing equipped binding or dueling, ending");
            return false;
        } else if (!Vars.get().lava) {
            if (!Inventory.contains(ItemID.WATER_RUNE)) {
                General.println("[Debug]: Missing water runes in inventory, ending");
                return false;
            }
            if (!Vars.get().usingLunarImbue && !Inventory.contains(ItemID.WATER_TALISMAN)) {
                Log.debug("Missing water talisman in inventory, ending");
                return false;
            }
        } else if (Vars.get().lava) {
            if (!Inventory.contains(ItemID.EARTH_RUNE)) {
                General.println("[Debug]: Missing earth runes in inventory, ending");
                return false;
            }
            if (!Vars.get().usingLunarImbue && !Inventory.contains(ItemID.EARTH_TALISMAN)) {
                General.println("[Debug]: Missing earth talisman in inventory, ending");
                return false;
            }
        } else if (Vars.get().usingLunarImbue && !Inventory.contains(ItemID.ASTRAL_RUNE)) {
            General.println("[Debug]: Missing astral runes in inventory, ending");
            return false;
        }
        return true;
    }


  /*  public void getAbyssItems() {
        //GoToAbyss.gloryTeleport("Edgeville");
        if (!itemInInv(ItemID.PURE_ESSENCE)) {

            if (Vars.get().zanarisCrafting) {
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
                PathingUtil.walkToArea(ItemID.ZANARIS_BANK, false);
            }

            RSItem[] p = Inventory.find(Filters.Items.nameContains("pouch").and(Filters.Items.actionsContains("Fill")));

            if (!tmr.isRunning() && Vars.get().shouldAfk) {
                Utils.afk(General.random(15000, 90000));
                tmr.reset();
            }
            if (!Banking.isBankScreenOpen())
                open(true);

            if (Vars.get().usingLunarImbue && p.length > 0) {
                depositAllExcept(true, ItemID.DEGRADED_LARGE_POUCH, ItemID.ALL_POUCHES[0], ItemID.ALL_POUCHES[1], ItemID.ALL_POUCHES[2]);
                Timer.waitCondition(() -> Inventory.getAll().length < 4, 1500, 2000);
            }

            while (Combat.getHP() + 6 < Skills.getActualLevel(Skills.SKILLS.HITPOINTS)) {
                General.println("[Bank]: Eating food");
                withdraw(1, true, ItemID.MONKFISH);
                EatUtil.eatFood();
                General.sleep(100);
            }


            if (!Vars.get().zanarisCrafting && getAndEquip(ItemID.AMULET_OF_GLORY, "Wear"))
                depositAllExcept(true, ItemID.DEGRADED_LARGE_POUCH, ItemID.ALL_POUCHES[0], ItemID.ALL_POUCHES[1], ItemID.ALL_POUCHES[2]);

            if (Vars.get().useStamina && Utils.getVarBitValue(25) == 0 || Game.getRunEnergy() < General.random(75, 80)) {
                getAndEquip(ItemID.STAMINA_POTION, "Drink");
                //drinks twice if needed
                if (Game.getRunEnergy() < 60)
                    getAndEquip(ItemID.STAMINA_POTION, "Drink");
                depositAllExcept(true, ItemID.DEGRADED_LARGE_POUCH, ItemID.ALL_POUCHES[0], ItemID.ALL_POUCHES[1], ItemID.ALL_POUCHES[2]);
            }

            if (!Equipment.isEquipped(Filters.Items.nameContains("axe")) && !Vars.get().zanarisCrafting)
                getAndEquip(ItemID.RUNE_AXE, "Wield");

            if (!Vars.get().zanarisCrafting)
                withdraw(1, true, ItemID.MONKFISH);

            RSItem[] invPouch = Inventory.find(ItemID.ALL_POUCHES);
            if (invPouch.length < 3) {
                // gets pouches in case we deposited them somehow
                withdraw(0, true, ItemID.ALL_POUCHES[0]);
                withdraw(0, true, ItemID.ALL_POUCHES[1]);
                withdraw(0, true, ItemID.ALL_POUCHES[2]);
            }


            // get essence
            withdraw(0, true, ItemID.PURE_ESSENCE);

            // fill pouches (if we have any)
            for (RSItem pouch : p) {
                if (pouch.click("Fill"))
                    AntiBan.waitItemInteractionDelay();
            }

            Utils.shortSleep();

            if (!Inventory.isFull())
                withdraw(0, true, ItemID.PURE_ESSENCE);

            Keyboard.pressKeys(KeyEvent.VK_ESCAPE);
            General.sleep(General.random(300, 500));
            close(true);

            Utils.hoverXp(Skills.SKILLS.RUNECRAFTING, 5);
        }
    }


    public void getItemsRegularRunes(int tiara, boolean useStamina, boolean useTeleTab, int tabId, RunescapeBank bank) {
        if (Inventory.find(ItemID.PURE_ESSENCE).length < 1 || !Equipment.isEquipped(tiara)) {
            General.println("[Debug]: Getting Items from Bank: " + bank.toString());
            PathingUtil.walkToTile(bank.getPosition(), 2, true);

            open(true);

            if (useStamina)
                depositAllExcept(true, tabId, ItemID.STAMINA_POTION[0], ItemID.STAMINA_POTION[1], ItemID.STAMINA_POTION[2], ItemID.STAMINA_POTION[3]);
            else
                depositAllExcept(true, tabId);

            if (!Equipment.isEquipped(tiara)) {
                withdraw(1, true, tiara);
                Utils.equipItem(tiara);
            }
            if (Vars.get().useStamina && Inventory.find(Filters.Items.nameContains("Stamina")).length == 0)
                withdrawArray(ItemID.STAMINA_POTION, 1);

            if (useTeleTab)
                withdraw(1, true, tabId);

            withdraw(0, true, ItemID.PURE_ESSENCE);
            General.sleep(General.random(250, 750));
            close(true);
        }
    }*/

    public static boolean close(boolean shouldWait) {
        return !Bank.close() || !shouldWait || Waiting.waitUntil(2000, Bank::isOpen);
    }

    public static boolean rodTele(String location) {
        Optional<InventoryItem> rod = Query.inventory()
                .nameContains("Ring of dueling")
                .findClosestToMouse();

        for (int i = 0; i < 3; i++) {
            General.println("[Teleport Manager]: Going to " + location);
            if (GameTab.EQUIPMENT.open() &&
                    rod.map(r -> r.click(location)).orElse(false))
                return Timer.waitCondition(() -> !CraftRunes.atAltar(), 6000, 8000);
        }

        return false;
    }


    public static boolean gloryTele(String location) {
        RSItem[] glor = Equipment.find(ItemID.AMULET_OF_GLORY);
        if (glor.length > 0) {
            for (int i = 0; i < 3; i++) {
                General.println("[Teleport Manager]: Going to " + location);
                if (glor[0].click(location))
                    return Const.waitCondition(() -> !CraftRunes.atAltar(), 6000, 8000);
            }
        }

        return false;
    }


    public static boolean rodHover() {
        Optional<InventoryItem> rod = Query.inventory()
                .nameContains("Ring of dueling")
                .findClosestToMouse();

        return GameTab.EQUIPMENT.open() &&
                rod.map(r -> r.hover("Castle Wars")).orElse(false);
    }


    public void stuckFailSafe() {
        //  if (!Vars\
        //.get().zanarisCrafting) {
        RSItem[] eqp = Equipment.find(Filters.Items.nameContains("dueling"));
        RSObject[] portal = Objects.findNearest(30, Filters.Objects.nameContains("Portal"));
        RSObject[] altar = Objects.findNearest(20, Filters.Objects.nameContains("Altar"));
        if (eqp.length == 0 && portal.length > 0 && altar.length > 0) {
            Log.error("[Debug]: We appear to be stuck, leaving via portal");
            if (Utils.clickObject(portal[0], "Use", true)) {
                Timer.waitCondition(() -> Objects.findNearest(20, Filters.Objects.nameContains("Altar")).length == 0,
                        10000, 12000);
                PathingUtil.walkToTile(RunescapeBank.DUEL_ARENA.getPosition(), 3, true);
            }
        }
        //}
    }


    public static int getLevel() {
        return Skill.RUNECRAFT.getActualLevel();
    }


    /**
     * BANKING BASIC FUNCTIONS
     */
    private static void closeHelpWindow() {
        Optional<Widget> w = Query.widgets().inIndexPath(664, 28).actionContains("Close").findFirst();
        if (w.map(Widget::click).orElse(false)) {
            Timer.waitCondition(() -> !Widgets.isVisible(664, 28), 5000);
            Waiting.waitNormal(400, 40);
        }
    }


    // sometimes this is accidentally clicked when teleporting
    public void closeLootInterface() {
        Optional<Widget> inter = Query.widgets().inIndexPath(464)
                .actionContains("Close").findFirst();
        inter.ifPresent(Widget::click);
    }

    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {

        boolean invTiara = Query.inventory()
                .idEquals(ItemID.TIARA)
                .isAny();
        boolean invTalisman = Query.inventory()
                .nameContains("talisman")
                .isAny();

        General.println("Abyss: " + Vars.get().abyssCrafting + " ; Lunars: " +
                Vars.get().usingLunarImbue + " ; Zanaris: "
                + Vars.get().zanarisCrafting);
        // if (getLevel() < 14) {
        //       return invTiara || talisman.length == 0;

        //   } else
        if (getLevel() < 14) {
            return !Inventory.contains(ItemID.PURE_ESSENCE) ||
                    !Equipment.isEquipped(ItemID.EARTH_TIARA);

        } else if (getLevel() < 19) {
            return (!Inventory.contains(ItemID.PURE_ESSENCE)
                    || !Equipment.isEquipped(ItemID.FIRE_TIARA));

        } else if (Vars.get().lava && !Vars.get().abyssCrafting && !Vars.get().usingLunarImbue) {
            return !itemInInv(ItemID.EARTH_TALISMAN, ItemID.EARTH_RUNE, ItemID.PURE_ESSENCE);

        } else if (!Vars.get().abyssCrafting && !Vars.get().zanarisCrafting) {

            if (Vars.get().usingLunarImbue && !Vars.get().lava)
                return !itemInInv(ItemID.WATER_RUNE, ItemID.PURE_ESSENCE);

            else if (Vars.get().usingLunarImbue && Vars.get().lava)
                return !itemInInv(ItemID.EARTH_RUNE, ItemID.PURE_ESSENCE);

            return !itemInInv(ItemID.WATER_RUNE, ItemID.WATER_TALISMAN, ItemID.PURE_ESSENCE);

        } else if (Vars.get().abyssCrafting || Vars.get().zanarisCrafting) {
            return !Inventory.contains(ItemID.PURE_ESSENCE);

        }
        return false;
    }

    @Override
    public void execute() {
        closeLootInterface();
        //stuckFailSafe();
        //TODO implement combo runes bank (progressive bank doesn't handle pouches)
        progressiveBank();
    }

    @Override
    public String toString() {
        return "Banking";
    }

}
