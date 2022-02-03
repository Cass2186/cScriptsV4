package scripts.Tasks.Mining.Tasks;

import org.tribot.api.General;
import org.tribot.api2007.Equipment;
import org.tribot.api2007.Player;
import org.tribot.api2007.Skills;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.BankManager;
import scripts.Data.SkillBank;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.ItemID;
import scripts.Requirements.ItemReq;
import scripts.Tasks.Mining.Utils.MLMUtils;
import scripts.Tasks.MiscTasks.BuyItems;
import scripts.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MiningBank implements Task {

    public static int getBestPickaxe() {
        int lvl = Skills.getActualLevel(Skills.SKILLS.MINING);
        if (lvl > 40)
            return ItemID.RUNE_PICKAXE;

        else if (lvl > 30)
            return ItemID.ADAMANT_PICKAXE;

        else if (lvl > 20)
            return ItemID.MITHRIL_PICKAXE;

        else if (lvl > 5)
            return ItemID.STEEL_PICKAXE;

        return ItemID.IRON_PICKAXE;
    }


    public boolean canEquipPickAxe() {
        int lvl = Skills.getActualLevel(Skills.SKILLS.ATTACK);
        if (getBestPickaxe() == ItemID.RUNE_PICKAXE)
            return lvl > 39;
        else if (getBestPickaxe() == ItemID.ADAMANT_PICKAXE)
            return lvl > 29;
        else if (getBestPickaxe() == ItemID.MITHRIL_PICKAXE)
            return lvl > 19;
        else if (getBestPickaxe() == ItemID.STEEL_PICKAXE)
            return lvl > 4;

        return true; //iron
    }

    public static boolean hasBestPickAxe() {
        return org.tribot.script.sdk.Inventory.contains(getBestPickaxe()) ||
                Equipment.isEquipped(getBestPickaxe());
    }

    public void getItems() {
        List<ItemReq> inv = new ArrayList<>(Collections.singletonList(new ItemReq(getBestPickaxe(), 1)));

        if (!MLMUtils.WHOLE_MLM_AREA.contains(Player.getPosition())) {
            General.println("[Mining Training]: Adding skills necklace to required items");
            inv.add(new ItemReq(ItemID.SKILLS_NECKLACE[2], 1));
        }

        BankManager.open(true);
        BankManager.depositAll(true);

        List<ItemReq> newInv = SkillBank.withdraw(inv);
        if (newInv != null && newInv.size() > 0) {
            General.println("[Mining Training]: Creating buy list");
            BuyItems.itemsToBuy = BuyItems.populateBuyList(newInv);
            return;
        }
        if (canEquipPickAxe())
            Utils.equipItem(getBestPickaxe());

        BankManager.close(true);
    }

    @Override
    public Priority priority() {
        return Priority.HIGH;
    }

    @Override
    public boolean validate() {
        return Vars.get().currentTask != null &&
                Vars.get().currentTask.equals(SkillTasks.MINING) &&
                Skills.getActualLevel(Skills.SKILLS.MINING) < 60 &&
                !hasBestPickAxe();
    }

    @Override
    public void execute() {
        General.println("Mining bank");
        getItems();
    }

    @Override
    public String taskName() {
        return "Mining";
    }
}
