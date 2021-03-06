package scripts.Tasks.Mining.Tasks;

import org.tribot.api.General;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.types.Area;
import org.tribot.script.sdk.types.WorldTile;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.EntitySelector.Entities;
import scripts.EntitySelector.finders.prefabs.ObjectEntity;
import scripts.*;
import scripts.Tasks.Mining.Utils.MLMUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DepositPayDirt implements Task {

    RSTile SACK_TILE = new RSTile(3748, 5659, 0);
    int BROKEN_STRUT_ID = 26670;
    RSTile STRUT_ONE_TILE = new RSTile(3742, 5669, 0);

    public List<RSObject> getBrokenStruts() {
        List<RSObject> struts = Arrays.stream(Entities.find(ObjectEntity::new)
                .idEquals(BROKEN_STRUT_ID)
                .getResults()).collect(Collectors.toList());
        //Query.gameObjects().idEquals(BROKEN_STRUT_ID).toList();


        return struts;
    }

    public boolean getHammer() {
        RSItem[] hammer = Inventory.find(ItemID.HAMMER);
        RSItem[] payDirt = Inventory.find("Pay-dirt");
        if (hammer.length == 0) {
            if (Inventory.isFull() && payDirt.length > 0) {
                if (payDirt[0].click("Drop"))
                    General.sleep(200, 500);
            }
            if (Utils.clickObject("Crate", "Search", false))
                return Timer.waitCondition(() -> Inventory.find(ItemID.HAMMER).length > 0, 6000, 9000);
        }

        return Inventory.find(ItemID.HAMMER).length > 0;
    }

    public void repairStruts() {
        List<RSObject> struts = getBrokenStruts();
        if (area.contains(Player.getPosition()) && struts.size() == 2) {
            Log.info("Fixing struts");
            if (getHammer()) {
                for (RSObject obj : struts) {
                    if (!obj.isClickable() && PathingUtil.localNavigation(obj.getPosition())) {
                        Log.info("Navigating to struts");
                        Timer.waitCondition(() -> obj.isClickable(), 5000, 7000);
                    }
                    if (Utils.clickObject(obj, "Hammer")) {
                        Timer.waitCondition(() -> getBrokenStruts().size() < struts.size(), 8000, 9000);
                    }
                }
            }
            // drop hammer
        }
    }

    RSArea area = new RSArea(
            new RSTile[]{
                    new RSTile(3748, 5674, 0),
                    new RSTile(3746, 5674, 0),
                    new RSTile(3746, 5676, 0),
                    new RSTile(3748, 5676, 0)
            }
    );
    Area sdkArea = Area.fromPolygon(
            new WorldTile(3748, 5674, 0),
            new WorldTile(3746, 5674, 0),
            new WorldTile(3746, 5676, 0),
            new WorldTile(3748, 5676, 0)
    );

    public void depositOre() {
        if (!MLMUtils.WHOLE_MLM_AREA.contains(Player.getPosition()))
            MineOreMLM.goToMLM();

        if (!MLMUtils.MLM_DEPOSIT_BOX_TILE.isClickable() &&
                PathingUtil.localNavigation(area.getRandomTile())) {
            Log.info("Deposting ore - Moving");
            Timer.waitCondition(15000, 700,
                    () -> MLMUtils.MLM_DEPOSIT_BOX_TILE.isClickable());
        } else if (PathingUtil.walkToTile(sdkArea.getCenter())) {
            Log.info("Deposting ore - Moving (DAX)");
            Timer.waitCondition(15000, 700,
                    () -> MLMUtils.MLM_DEPOSIT_BOX_TILE.isClickable());
            if (QueryUtils.getObject("Hopper").map(
                    h -> h.interact("Deposit")).orElse(false)) {
                //   if (Utils.clickObject("Hopper", "Deposit", true)) {
                Vars.get().oreDeposits++;
                int ore = Utils.getVarBitValue(Varbits.SACK_NUMBER.getId());
                Log.info("Depositing ore (A)");
                Timer.waitCondition(() -> !Inventory.isFull(), 6000, 9000);
                if (ore >= 56) {
                    Timer.waitCondition(() -> Utils.getVarBitValue(Varbits.SACK_NUMBER.getId()) != ore,
                            7000, 9000);
                }
                return;
            }
        }
        if (MLMUtils.MLM_DEPOSIT_BOX_TILE.distanceTo(Player.getPosition()) < 5) {
            if (QueryUtils.getObject("Hopper").map(
                    h -> h.interact("Deposit")).orElse(false)) {
                //if (Utils.clickObject("Hopper", "Deposit", true)) {
                Vars.get().oreDeposits++;
                int ore = Utils.getVarBitValue(Varbits.SACK_NUMBER.getId());
                Log.info("Depositing ore (B)");
                if (Utils.getVarBitValue(Varbits.SACK_NUMBER.getId()) >= 55) {
                    PathingUtil.localNavigation(SACK_TILE);
                    Timer.waitCondition(() -> Utils.getVarBitValue(Varbits.SACK_NUMBER.getId()) > ore, 6000, 9000);
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Depositing paydirt";
    }

    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        return Vars.get().currentTask != null &&
                Vars.get().currentTask.equals(SkillTasks.MINING) &&
                Skills.getActualLevel(Skills.SKILLS.MINING) >= 30 &&
                Utils.getVarBitValue(Varbits.SACK_NUMBER.getId()) <= 56
                && Vars.get().useMLM
                && Inventory.isFull();
    }

    @Override
    public void execute() {
        General.println("[DepositPayDirt]: Depositing Pay dirt");
        repairStruts();
        depositOre();
    }

    @Override
    public String taskName() {
        return "MLM Training";
    }
}
