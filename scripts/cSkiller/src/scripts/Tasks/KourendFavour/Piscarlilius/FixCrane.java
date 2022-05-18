package scripts.Tasks.KourendFavour.Piscarlilius;

import org.tribot.api.General;
import org.tribot.api2007.Objects;
import org.tribot.api2007.WorldHopper;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.Inventory;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.MyPlayer;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.query.Query;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.ItemID;
import scripts.PathingUtil;
import scripts.Tasks.BirdHouseRuns.Nodes.Wait;
import scripts.Timer;
import scripts.Utils;

public class FixCrane implements Task {

    int BROKEN_CRANE = 27555;
    RSArea CRANE_AREA = new RSArea(new RSTile(1818, 3721, 0), new RSTile(1813, 3731, 0));

    public void checkWorld() {
        if (WorldHopper.getWorld() != 3) {
            WorldHopper.changeWorld(303);
        }
    }

    public void fixCrane() {
        if (Inventory.getCount(ItemID.PLANK) > 2 &&
                Inventory.contains("Mithril nails") &&
                Inventory.contains(ItemID.HAMMER)) {
            PathingUtil.walkToArea(CRANE_AREA);

            if (Objects.findNearest(6, BROKEN_CRANE).length == 0) {
                Log.info("Waiting for Broken Crane");
                Timer.waitCondition(() -> Query.gameObjects()
                        .maxDistance(7).idEquals(BROKEN_CRANE).isAny() &&
                        MyPlayer.getAnimation() == -1, 60000, 95000);
                Utils.idleNormalAction(true);
            }
            if (MyPlayer.getAnimation() != -1) {
                Log.info("Player is still animating");
                Timer.waitCondition(() -> !Query.gameObjects().maxDistance(7).idEquals(BROKEN_CRANE).isAny()
                        || MyPlayer.getAnimation() == -1, 30000, 45000);
            }
            if (MyPlayer.getAnimation() == -1) {
                if (!Waiting.waitUntil(1800, 75, () -> MyPlayer.getAnimation() != -1)) {
                    if (Utils.clickObj(BROKEN_CRANE, "Repair") &&
                            Timer.waitCondition(() -> MyPlayer.getAnimation() != -1, 3500, 6000)) {
                        Timer.waitCondition(() ->
                                !Query.gameObjects().maxDistance(1).idEquals(BROKEN_CRANE).isAny()
                                        || MyPlayer.getAnimation() == -1, 30000, 45000);
                    }
                }
            }




        }
    }

    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        return Vars.get().currentTask != null &&
                Vars.get().currentTask.equals(SkillTasks.KOUREND_FAVOUR) &&
                (Inventory.getCount(ItemID.PLANK) > 2 &&
                        Inventory.contains("Mithril nails") &&
                        Inventory.contains(ItemID.HAMMER));
    }

    @Override
    public void execute() {
        checkWorld();
        fixCrane();
    }

    @Override
    public String toString() {
        return "Repairing Crane";
    }

    @Override
    public String taskName() {
        return "Port Piscarililius Favour";
    }
}
