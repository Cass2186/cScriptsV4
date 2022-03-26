package scripts.Tasks;

import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.Skill;
import org.tribot.script.sdk.query.Query;
import scripts.Data.Const;
import scripts.Data.Vars;
import scripts.EntitySelector.Entities;
import scripts.EntitySelector.finders.prefabs.ObjectEntity;
import scripts.ItemID;
import scripts.Timer;
import scripts.Utils;

public class CraftMindTiara implements Task {

    public void makeTiara() {
        if (Const.MIND_ALTAR_AREA.contains(Player.getPosition()) || CraftRunes.atAltar()) {
            RSItem[] tiaras = Inventory.find("Tiara");
            RSItem[] talisman = Inventory.find(Filters.Items.nameContains("Talisman"));
            for (RSItem i : tiaras) {
                int count = Inventory.getCount(ItemID.TIARA);
                if (talisman.length >0 &&
                        Utils.useItemOnObject(talisman[0].getID(), "Altar")) {
                    Timer.waitCondition(() -> Inventory.getCount(ItemID.TIARA) < count, 3500, 5000);
                }
            }
        }
    }

    public void goToAltar() {
        RSObject ruins = Entities.find(ObjectEntity::new)
                .actionsContains("Enter")
                .getFirstResult();
        if ((!Const.MIND_ALTAR_AREA.contains(Player.getPosition()) && !CraftRunes.atAltar()) &&
                ruins != null && Utils.clickObject(ruins, "Enter")) {
            Timer.waitCondition(() -> Const.MIND_ALTAR_AREA.contains(Player.getPosition()), 6000, 9000);
        }
    }

    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return Vars.get().mindTiaraCrafting &&
                Skill.RUNECRAFT.getActualLevel() < 19 &&
                Query.inventory().idEquals(ItemID.TIARA).isAny() &&
                Query.inventory().nameContains("talisman").isAny();
    }

    @Override
    public void execute() {
        goToAltar();
        makeTiara();
    }

    @Override
    public String toString() {
        return "Mind Tiara crafting";
    }
}
