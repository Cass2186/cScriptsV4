package scripts.Tasks;

import org.tribot.script.sdk.*;
import dax.api_lib.models.RunescapeBank;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.*;
import org.tribot.script.sdk.Equipment;
import org.tribot.script.sdk.GameTab;
import org.tribot.script.sdk.Inventory;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.tasks.Amount;
import org.tribot.script.sdk.tasks.BankTask;
import org.tribot.script.sdk.tasks.BankTaskError;
import org.tribot.script.sdk.tasks.EquipmentReq;
import org.tribot.script.sdk.types.EquipmentItem;
import org.tribot.script.sdk.types.GameObject;
import org.tribot.script.sdk.types.InventoryItem;
import org.tribot.script.sdk.types.Widget;
import org.tribot.script.sdk.util.TribotRandom;
import scripts.*;

import scripts.Data.Const;
import scripts.Data.RunecraftItems;

import java.util.Arrays;
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
        if (!Bank.isOpen() && !Bank.isNearby())
            itemOptional.map(i -> PathingUtil.walkToTile(i.getBank().getPosition()));

        BankTask task = itemOptional.map(this::getBankTaskFromComboRune).orElseThrow();

        Optional<BankTaskError> err = task.execute();
        err.ifPresent(e -> Log.error("Banking error " + e.toString()));
        if (err.isPresent())
            throw new IllegalStateException("Missing items");
        //   BuyItems.itemsToBuy = BuyItems.populateBuyList(RunecraftItems.getRequiredItemList());
    }

    public boolean getAndEquip(int item, String cmd) {
        if (!Equipment.contains(item)) {
            BankManager.withdraw(1, true, item);
            List<InventoryItem> itm = Query.inventory().idEquals(item).toList();
            if (itm.size() > 0 && itm.get(0).click(cmd))
                return Waiting.waitUntil(TribotRandom.uniform(2500, 3500), 600,
                        () -> Equipment.contains(item));
        }
        return Equipment.contains(item);
    }

    public boolean getAndEquip(int[] item, String cmd) {
        if (!Equipment.contains(item)) {
            BankManager.withdrawArray(item, 1);
            List<InventoryItem> itm = Query.inventory().idEquals(item).toList();
            if (itm.size() > 0 && itm.get(0).click(cmd))
                return Waiting.waitUntil(TribotRandom.uniform(2500, 3500),
                        TribotRandom.normal(600,45),
                        () -> Equipment.contains(item));


        }
        return Equipment.contains(item);
    }


    public void rodFailSafe() {
        Optional<EquipmentItem> item = Query.equipment()
                .nameContains("dueling(1)").findFirst();
        if (item.isPresent()) {
            General.println("[Debug]: ROD(1) failsafe detected");
            if (getAndEquip(RING_OF_DUELING, "Wear"))
                BankManager.depositAll(true);
        }
    }

    public boolean itemInInv(int... items) {
        return Arrays.stream(items).allMatch(Inventory::contains);
    }

    public void goToCWBank() {
        //loop twice b/c sometimes the tele misclicks and opens loot interface
        for (int i = 0; i < 2; i++) {

            if (!CraftRunes.atAltar())
                break;

            closeLootInterface();
            if (rodTele("Castle Wars")) {
                if (Waiting.waitUntil(5000, 75, () -> Bank.isNearby()
                       /* Query.gameObjects()
                        .nameContains("Bank chest")
                        .isAny()*/))
                    Log.warn("AT BANK");
                else
                    Log.warn("Failed at BANK");

                Optional<GameObject> chest = Query.gameObjects()
                        .nameContains("Bank chest")
                        .findBestInteractable();

                if (chest.map(c -> c.interact("Bank")).orElse(false)) {
                    Waiting.waitUntil(7000, 120, Bank::isOpen);
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
        List<InventoryItem> p = CraftRunes.getGiantAndMediumPouch();
        for (InventoryItem pouch : p) {
            if (Inventory.getCount(ItemID.PURE_ESSENCE) < 8) {
                BankManager.withdraw(0, true, ItemID.PURE_ESSENCE);
            }
            if (pouch.click("Fill"))
                Waiting.waitNormal(60, 15);
        }
        Waiting.waitUntil(TribotRandom.uniform(700,1000), 50, ()->
                Inventory.getAll().size() < 12);
        p = CraftRunes.getLargeAndSmallPouch();
        for (InventoryItem pouch : p) {
            if (Inventory.getCount(ItemID.PURE_ESSENCE) < 10) {
                BankManager.withdraw(0, true, ItemID.PURE_ESSENCE);
            }
            if (pouch.click("Fill"))
                Waiting.waitNormal(60, 15);
        }
        Waiting.waitUniform(600, 1200);
    }

    private boolean getAndDrinkStamina(boolean keepInInventory){
        if(BankManager.withdrawArray(ItemID.STAMINA_POTION, 1)){
            if(QueryUtils.getItem("Stamina potion")
                    .map(p->p.click("Drink")).orElse(false)){


            }
        }
        return Utils.getVarBitValue(25) != 0; //if true, we have active stam
    }


    public void getAbyssItems(int runeId) {
        if (!itemInInv(ItemID.PURE_ESSENCE) || !Query.equipment()
                .nameContains("Amulet of glory(").isAny()) {
            Log.info("Abyss Bank");
            List<InventoryItem> p = CraftRunes.getPouches();

            //TODO handle globally
            if (!tmr.isRunning() && Vars.get().shouldAfk) {
                Utils.afk(General.random(15000, 60000));
                tmr.reset();
            }
            if (RunescapeBank.EDGEVILLE.getPosition().distanceTo(Player.getPosition()) > 20)
                if(!PathingUtil.walkToTile(RunescapeBank.EDGEVILLE.getPosition()))
                    return; //failed for some reason
            Log.info("passed edge");
            BankManager.open(true);

            if (p.size() > 0) {
                BankManager.depositAllExcept(false, runeId, ItemID.BLOOD_ESSENCE_ACTIVE,
                        ItemID.ALL_POUCHES[0], ItemID.ALL_POUCHES[1], ItemID.ALL_POUCHES[2],
                        ItemID.GIANT_POUCH);
                Timer.waitCondition(() -> Inventory.getAll().size() < 6, 1500, 2000);
            } else
                BankManager.depositAllExcept(false, runeId, ItemID.ASTRAL_RUNE);

            rodFailSafe();

            if (getAndEquip(ItemID.AMULET_OF_GLORY, "Wear"))
                BankManager.depositAllExcept(false, runeId, ItemID.ASTRAL_RUNE,ItemID.BLOOD_ESSENCE_ACTIVE,
                        ItemID.ALL_POUCHES[0], ItemID.ALL_POUCHES[1], ItemID.ALL_POUCHES[2], ItemID.GIANT_POUCH);

            if (Vars.get().useStamina && Utils.getVarBitValue(25) == 0 && Game.getRunEnergy() < General.random(65, 80)) {
                getAndEquip(ItemID.STAMINA_POTION, "Drink");
                BankManager.depositAllExcept(false, runeId, ItemID.ASTRAL_RUNE,ItemID.BLOOD_ESSENCE_ACTIVE,
                        ItemID.ALL_POUCHES[0], ItemID.ALL_POUCHES[1], ItemID.ALL_POUCHES[2], ItemID.GIANT_POUCH);
            }

            if (getPouches())
                p = CraftRunes.getPouches();

            // get essence
            BankManager.withdraw(0, true, ItemID.PURE_ESSENCE);

            // fill pouches (if we have any)
            fillPouches();

            // refill inv with essence if needed
            if (!Inventory.isFull())
                BankManager.withdraw(0, true, ItemID.PURE_ESSENCE);

            BankManager.close(true);

            Utils.hoverXp(Skills.SKILLS.RUNECRAFTING, 5);
            if (!checkItems()) {

            }
        }
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

            BankManager.close(true);

            Utils.hoverXp(Skills.SKILLS.RUNECRAFTING, 5);
            if (!checkItems()) {

            }
        }
    }

    public boolean checkItems() {
        if (!Equipment.contains(ItemID.BINDING_NECKLACE)
                || !Equipment.contains(RING_OF_DUELING)) {
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
        Optional<EquipmentItem> equippedGlor = Query.equipment()
                .idEquals(ItemID.AMULET_OF_GLORY).findFirst();

        for (int i = 0; i < 3; i++) {
            General.println("[Teleport Manager]: Going to " + location);
            if (equippedGlor.map(g -> g.click(location)).orElse(false))
                return Waiting.waitUntil(8000, 300, () -> !CraftRunes.atAltar());

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
        Optional<EquipmentItem> equippedRing = Query.equipment()
                .nameContains("dueling").findFirst();

        RSObject[] portal = Objects.findNearest(30, Filters.Objects.nameContains("Portal"));
        RSObject[] altar = Objects.findNearest(20, Filters.Objects.nameContains("Altar"));
        if (equippedRing.isEmpty() && portal.length > 0 && altar.length > 0) {
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
        return Priority.HIGH;
    }

    @Override
    public boolean validate() {
        General.println("Abyss: " + Vars.get().abyssCrafting + " ; Lunars: " +
                Vars.get().usingLunarImbue + " ; Zanaris: "
                + Vars.get().zanarisCrafting);
        // if (getLevel() < 14) {
        //       return invTiara || talisman.length == 0;

        //   } else
        if (getLevel() < 14) {
            return !Inventory.contains(ItemID.PURE_ESSENCE) ||
                    !Equipment.contains(ItemID.EARTH_TIARA);

        } else if (getLevel() < 19) {
            return (!Inventory.contains(ItemID.PURE_ESSENCE)
                    || !Equipment.contains(ItemID.FIRE_TIARA));

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
        if (Vars.get().abyssCrafting)
            getAbyssItems(ItemID.COSMIC_RUNE);
        else {
            //TODO implement combo runes bank (progressive bank doesn't handle pouches)
            progressiveBank();
        }
    }

    @Override
    public String toString() {
        return "Banking";
    }

}
