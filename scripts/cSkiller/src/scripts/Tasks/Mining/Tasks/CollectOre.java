package scripts.Tasks.Mining.Tasks;

import org.tribot.api.General;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.Bank;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Prayer;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.GameObject;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.*;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.EntitySelector.Entities;
import scripts.EntitySelector.finders.prefabs.ItemEntity;
import scripts.Tasks.Mining.Utils.MLMUtils;

import java.util.Optional;

public class CollectOre implements Task {

    RSTile SACK_TILE = new RSTile(3748, 5659, 0);
    RSArea COLLECT_AREA = new RSArea(
            new RSTile[]{
                    new RSTile(3742, 5664, 0),
                    new RSTile(3743, 5657, 0),
                    new RSTile(3751, 5657, 0),
                    new RSTile(3754, 5661, 0),
                    new RSTile(3761, 5665, 0),
                    new RSTile(3762, 5666, 0),
                    new RSTile(3762, 5668, 0),
                    new RSTile(3749, 5668, 0)
            }
    );

    public void bankOre() {
        RSItem[] ore = Entities.find(ItemEntity::new)
                .nameContains("ore")
                .getResults();
        Optional<GameObject> chest = Query.gameObjects().nameContains("chest")
                .findClosestByPathDistance();
        if (ore.length > 0) {
            if (!Bank.isOpen()){
                if(chest.map(c->c.interact("Use")).orElse(false)){
                    Log.info("Interacted with bank chest");
                    Timer.waitCondition(9000, 600, Bank::isOpen);
                } else BankManager.open(true);
            }
            BankManager.depositAll(true);
            BankManager.close(true);
        }
    }


    public void collectOre() {
        if (!MLMUtils.WHOLE_MLM_AREA.contains(Player.getPosition()))
            MineOreMLM.goToMLM();
        if (!SACK_TILE.isClickable() && COLLECT_AREA.contains(Player.getPosition())) {
            Walking.blindWalkTo(SACK_TILE);
            General.println("[Debug]: Collecting ore - Moving");
            Timer.waitCondition(() -> SACK_TILE.isClickable(), 6000, 9000);
        }
        if (!SACK_TILE.isClickable() && PathingUtil.localNavigation(SACK_TILE)) {
            General.println("[Debug]: Collecting ore - Moving");
            PathingUtil.movementIdle();
        }
        General.println("[Debug]: Collecting ore");
        int ore = Utils.getVarBitValue(Varbits.SACK_NUMBER.getId());
        if (Utils.clickObject("Sack", "Search", true)) {
            Vars.get().oreDeposits--;
            Timer.waitCondition(() -> Utils.getVarBitValue(Varbits.SACK_NUMBER.getId()) != ore, 6000, 9000);
        }

    }


    @Override
    public String toString() {
        return "Collecting Ore";
    }


    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return Vars.get().currentTask != null &&
                Vars.get().currentTask.equals(SkillTasks.MINING)
                && Vars.get().useMLM
                && (Utils.getVarBitValue(Varbits.SACK_NUMBER.getId()) > 56 ||
                        Inventory.find(Filters.Items.nameContains("ore")).length > 1 ||
                        Vars.get().oreDeposits == 3);
    }

    @Override
    public void execute() {
        General.println("Collecting ore");

        for (int i = 0; i < 3; i++) {
            collectOre();
            bankOre();
            if (Utils.getVarBitValue(Varbits.SACK_NUMBER.getId()) == 0)
                break;
        }
        bankOre(); // failsafe
    }

    @Override
    public String taskName() {
        return "MLM Training";
    }
}
