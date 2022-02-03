package scripts.Tasks.Magic;

import org.tribot.api.General;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSItem;
import org.tribot.script.sdk.Equipment;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.tasks.*;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.BankManager;
import scripts.Data.SkillBank;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.ItemID;
import scripts.Requirements.ItemReq;
import scripts.Tasks.MiscTasks.BuyItems;
import scripts.Utils;
import scripts.cSkiller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class MagicBank implements Task {

    @Override
    public String toString() {
        return "Banking";
    }

    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        return Vars.get().currentTask != null &&
                Vars.get().currentTask.equals(SkillTasks.MAGIC) &&
                SpellInfo.getCurrentSpell().map(sp -> !sp.hasRequiredItems()).orElse(false);

       /* if (Skills.getActualLevel(Skills.SKILLS.MAGIC) < 27) {
            return !saphRingTask.isSatisfied();
        } else if (Skills.getActualLevel(Skills.SKILLS.MAGIC) < 37) {
            return Vars.get().currentTask != null &&
                    Vars.get().currentTask.equals(SkillTasks.MAGIC) && !emeraldRingTask.isSatisfied();
        } else if (Skills.getActualLevel(Skills.SKILLS.MAGIC) < 45) {
            return Vars.get().currentTask != null &&
                    Vars.get().currentTask.equals(SkillTasks.MAGIC) &&
                    SpellInfo.getCurrentSpell().map(SpellInfo::hasRequiredItems).orElse(false);
        } else if (Skills.getActualLevel(Skills.SKILLS.MAGIC) >= 55) {
            return Vars.get().currentTask != null &&
                    Vars.get().currentTask.equals(SkillTasks.MAGIC) &&
                    (Inventory.find(ItemID.AIR_BATTLESTAFF + 1).length == 0 ||
                            Inventory.find(ItemID.NATURE_RUNE).length == 0);
        } else if (Skills.getActualLevel(Skills.SKILLS.MAGIC) >= 45) {
            return Vars.get().currentTask != null &&
                    Vars.get().currentTask.equals(SkillTasks.MAGIC) &&
                    SpellInfo.getCurrentSpell().map(SpellInfo::hasRequiredItems).orElse(false);
        }
        return Vars.get().currentTask != null &&
                Vars.get().currentTask.equals(SkillTasks.MAGIC) &&
                (Inventory.find(ItemID.AIR_BATTLESTAFF + 1).length == 0 ||
                        Inventory.find(ItemID.NATURE_RUNE).length == 0);*/
    }

    @Override
    public void execute() {
        if (Skills.getActualLevel(Skills.SKILLS.MAGIC) < 27) {
            myEnchBank2(saphRingTask);
            // myEnchBank(saphList, ItemID.STAFF_OF_WATER);
        } else if (Skills.getActualLevel(Skills.SKILLS.MAGIC) < 37) {
            myEnchBank(emList, ItemID.STAFF_OF_AIR);
            //enchantBank(emeraldRingTask);
        } else if (Skills.getActualLevel(Skills.SKILLS.MAGIC) < 45) {
            teleItems();
        } else if (Skills.getActualLevel(Skills.SKILLS.MAGIC) < 55) {
            teleBank(-1);// teleItems();
        } else if (Skills.getActualLevel(Skills.SKILLS.MAGIC) < 75) {
            alchBank(Vars.get().alchItem.getId());
        } else if (Skills.getActualLevel(Skills.SKILLS.MAGIC) >= 57) {
            myEnchBank2(diamondBraceletTAsk);
        }
    }

    @Override
    public String taskName() {
        return "Magic Bank";
    }

    BankTask saphRingTask = BankTask.builder()
            .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.WEAPON).item(ItemID.STAFF_OF_WATER, Amount.of(1)))
            .addInvItem(ItemID.COSMIC_RUNE, Amount.fill(1))
            .addInvItem(ItemID.SAPPHIRE_RING, Amount.fill(1))
            .build();

    BankTask emeraldRingTask = BankTask.builder()
            .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.WEAPON).item(ItemID.STAFF_OF_AIR, Amount.of(1)))
            .addInvItem(ItemID.COSMIC_RUNE, Amount.fill(1))
            .addInvItem(ItemID.EMERALD_RING, Amount.fill(1))
            .build();

    BankTask diamondBraceletTAsk = BankTask.builder()
            .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.WEAPON).item(ItemID.MUD_BATTLESTAFF, Amount.of(1)))
            .addInvItem(ItemID.COSMIC_RUNE, Amount.fill(1))
            .addInvItem(ItemID.DIAMOND_BRACELET, Amount.fill(1))
            .build();


    List<ItemReq> saphList = new ArrayList<>(
            Arrays.asList(
                    //new ItemReq(ItemID.STAFF_OF_AIR, 1, 0, true, true),
                    new ItemReq(ItemID.COSMIC_RUNE, 0, 1),
                    new ItemReq(ItemID.SAPPHIRE_RING, 0, 1)
            ));

    List<ItemReq> emList = new ArrayList<>(
            Arrays.asList(
                    //  new ItemReq(ItemID.STAFF_OF_AIR, 1, 0, true, true),
                    new ItemReq(ItemID.COSMIC_RUNE, 0, 1),
                    new ItemReq(ItemID.EMERALD_RING, 0, 1)
            ));

    public boolean myEnchBank2(BankTask task) {
        if (!task.isSatisfied()) {
            Log.log("[MagicBank]: Banking for items");
            Optional<BankTaskError> err = task.execute();
            if (err.isPresent()) {
                General.println("[Magic Training]: Creating buy list");
                BuyItems.itemsToBuy = BuyItems.populateBuyList(SpellInfo.getRequiredItemList());
            }
        }
        return task.isSatisfied();
    }


    public void myEnchBank(List<ItemReq> req, int staffId) {

        Log.log("[MagicBank]: Banking for items");
        BankManager.open(true);
        if (!org.tribot.api2007.Equipment.isEquipped(staffId)) {
            BankManager.withdraw(1, true, staffId);
            Utils.equipItem(staffId);
        }
        BankManager.open(true);
        BankManager.depositAllExcept(true, ItemID.COSMIC_RUNE);
        List<ItemReq> newInv = SkillBank.withdraw(req);

        if (newInv != null && newInv.size() > 0) {
            General.println("[Magic Training]: Creating buy list");
            BuyItems.itemsToBuy = BuyItems.populateBuyList(SpellInfo.getRequiredItemList());
            return;
        }
    }

    public boolean enchantBank(BankTask task) {
        cSkiller.status = "Banking";
        // for (int i = 0; i < 3; i++) {
        if (task.isSatisfied()) {
            Log.log("[MagicBank]: Task satisfied");
            BankManager.close(true);
            return true;
        }
        Log.log("[MagicBank]: Banking for tele items (i = " + ")");
        BankManager.open(true);

        handleBankError(task.execute());
        // task.execute();
        //}
        Log.log("[MagicBank]: Task satisfied: " + task.isSatisfied());
        return task.isSatisfied();
    }

    public void handleBankError(Optional<BankTaskError> err) {
        if (err.isEmpty()) return;
        if (err.get() instanceof InsufficientItemError) {
            ((InsufficientItemError) err.get()).getActualAmt();
            throw new NullPointerException();
        }
    }

    public boolean teleBank(int additionalRune) {
        cSkiller.status = "Banking";
        BankTask task;
        if (additionalRune == -1) {
            task = BankTask.builder()
                    .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.WEAPON).item(ItemID.STAFF_OF_AIR, Amount.of(1)))
                    .addInvItem(ItemID.LAW_RUNE, Amount.fill(20))
                    .build();
        } else {
            task = BankTask.builder()
                    .addEquipmentItem(EquipmentReq.slot(Equipment.Slot.WEAPON).item(ItemID.STAFF_OF_AIR, Amount.of(1)))
                    .addInvItem(ItemID.LAW_RUNE, Amount.fill(20))
                    .addInvItem(additionalRune, Amount.fill(20))
                    .build();
        }

        // for (int i = 0; i < 3; i++) {
        if (task.isSatisfied()) {
            Log.log("[MagicBank]: Task satisfied");
            BankManager.close(true);
            return true;
        }
        Log.log("[MagicBank]: Banking for tele items (i = " + ")");
        BankManager.open(true);


        Optional<ItemReq> newInv = BankManager.getItemReqFromBankError(task.execute());
        if (newInv.isPresent()) {
            General.println("[Magic Training]: Creating buy list");
            BuyItems.itemsToBuy = BuyItems.populateBuyList(SpellInfo.getRequiredItemList());
            return false;
        }
        //}
        Log.log("[MagicBank]: Task satisfied: " + task.isSatisfied());
        return task.isSatisfied();
    }

    public int determineAmountOfAlchItem() {
        if (Banking.isBankLoaded()) {
            RSItem[] coins = Banking.find(995);
            if (coins.length > 0) {
                int num = coins[0].getStack();
                int b = (int) (num / (Vars.get().alchItem.getAlchValue() * 1.40));
                General.println("[Debug]: Can buy " + b + " Alch items @ " + b);
                return b;
            }
        }
        return 1;
    }

    public int determineAmountOfAirBattlestaff() {
        if (Banking.isBankLoaded()) {
            RSItem[] coins = Banking.find(995);
            if (coins.length > 0) {
                int num = coins[0].getStack();
                int b = num / 13000;
                General.println("[Debug]: Can buy " + b + " battlestaves");
                return b;
            }
        }
        return 1;
    }

    public void teleItems() {
        //    int numOfItems = SpellInfo.getCurrentSpell().map(sp -> sp.determineResourcesToNextItem()).orElse(-1);
        int staffId = SpellInfo.getCurrentSpell().map(sp -> sp.getStaffId()).orElse(-1);

        cSkiller.status = "Banking";


        BankManager.open(true);
        BankManager.depositAll(true);

        if (staffId != -1 &&
                !org.tribot.api2007.Equipment.isEquipped(staffId)) {
            General.println("[Magic Training]: Getting staff");
            BankManager.withdraw(1, true, staffId);
            Utils.equipItem(staffId);
        }

        List<ItemReq> newInv = SkillBank.withdraw(SpellInfo.getRequiredItemList());

        if (newInv != null && newInv.size() > 0) {
            General.println("[Magic Training]: Creating buy list");
            BuyItems.itemsToBuy = BuyItems.populateBuyList(SpellInfo.getRequiredItemList());
            return;
        }

        BankManager.close(true);

    }

    public void alchBank(int alchItemID) {
        cSkiller.status = "Banking";
        if (Inventory.find(ItemID.getNotedId(alchItemID)).length < 1 || Inventory.find(ItemID.NATURE_RUNE).length < 1) {

            List<ItemReq> inv = new ArrayList<>(Arrays.asList(
                    new ItemReq(ItemID.NATURE_RUNE, 0, 1)));

            BankManager.open(true);
            BankManager.depositAll(true);

            if (org.tribot.api2007.Equipment.find(ItemID.STAFF_OF_FIRE).length < 1) {
                General.println("[Magic Training]: Getting staff of fire");
                BankManager.withdraw(1, true, ItemID.STAFF_OF_FIRE);
                Utils.equipItem(ItemID.STAFF_OF_FIRE, "Wield");
            }

            List<ItemReq> newInv = SkillBank.withdraw(inv);
            BankManager.turnNotesOn();
            BankManager.withdraw(0, true, alchItemID);

            if (Inventory.find(ItemID.getNotedId(alchItemID)).length == 0) {
                General.println("[Magic Training]: Adding items to buy list : " + alchItemID);
                if (newInv == null)
                    newInv = new ArrayList<>();

                newInv.add(new ItemReq(alchItemID, determineAmountOfAlchItem()));
                newInv.add(new ItemReq(ItemID.NATURE_RUNE, determineAmountOfAlchItem()));
            }
            if (newInv != null && newInv.size() > 0) {
                General.println("[Magic Training]: Creating buy list");
                BuyItems.itemsToBuy = BuyItems.populateBuyList(newInv);
                return;
            }

            BankManager.close(true);
        }
    }

}
