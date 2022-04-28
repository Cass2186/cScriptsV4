package scripts.QuestPackages.KourendFavour.Piscarlilius;

import org.tribot.api.General;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.WorldHopper;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.Quest;
import scripts.*;

import scripts.QuestPackages.lairoftarnrazorlor.TarnRoute;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.ItemRequirement;
import scripts.Requirements.Requirement;
import scripts.Tasks.Priority;

import java.util.List;

public class FixCrane implements QuestTask {

    private static FixCrane quest;

    public static FixCrane get() {
        return quest == null ? quest = new FixCrane() : quest;
    }

    int BROKEN_CRANE = 27555;
    RSArea CRANE_AREA = new RSArea(new RSTile(1818, 3721, 0), new RSTile(1813, 3731, 0));

    public void checkWorld() {
        if (WorldHopper.getWorld() != 3) {
            WorldHopper.changeWorld(303);
        }
    }

    public void fixCrane() {
        if (Inventory.find(ItemID.PLANK).length > 2 &&
                Inventory.find("Steel nails").length > 0 &&
                Inventory.find(ItemID.HAMMER).length > 0) {
            PathingUtil.walkToArea(CRANE_AREA);

            Timer.waitCondition(() -> Objects.findNearest(6, BROKEN_CRANE).length > 0 &&
                    Player.getAnimation() == -1, 60000, 95000);
            // Constants.idle(25, 150); // reaction time sleep

            if (Player.getAnimation() == -1) {
                if (Utils.clickObj(BROKEN_CRANE, "Repair")) {
                    Timer.waitCondition(() -> Player.getAnimation() == 7199, 3500, 6000);
                    Timer.waitCondition(() -> Objects.findNearest(10, BROKEN_CRANE).length < 1
                            || Player.getAnimation() == -1, 30000, 45000);
                }
            }
            if (Objects.findNearest(1, BROKEN_CRANE).length > 0)
                Utils.idle(1800, 2500);
            if (Player.getAnimation() != -1) {
                General.println("[Debugging]: Player is still animating");
                Timer.waitCondition(() -> Objects.findNearest(10, BROKEN_CRANE).length < 1
                        || Player.getAnimation() == -1, 30000, 45000);
            }
        }
    }

    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        return cQuesterV2.taskList.get(0).equals(this) &&
                (Inventory.find(ItemID.PLANK).length > 2 &&
                        Inventory.find("Steel nails").length > 0 &&
                        Inventory.find(ItemID.HAMMER).length > 0);
    }

    @Override
    public void execute() {
        checkWorld();
        fixCrane();
        UnnotePlanks.get().execute();
    }

    @Override
    public String questName() {
        return null;
    }

    @Override
    public boolean checkRequirements() {
        return false;
    }

    @Override
    public String toString() {
        return "Repairing Crane";
    }

    @Override
    public List<Requirement> getGeneralRequirements() {
        return null;
    }

    @Override
    public List<ItemRequirement> getBuyList() {
        return null;
    }

    @Override
    public boolean isComplete() {
        return false;
    }

}
