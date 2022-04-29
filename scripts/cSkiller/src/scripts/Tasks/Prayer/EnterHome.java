package scripts.Tasks.Prayer;

import org.tribot.api.General;
import org.tribot.api2007.Game;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.WorldHopper;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.GameObject;
import org.tribot.script.sdk.types.Widget;
import org.tribot.script.sdk.walking.LocalWalking;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.PathingUtil;
import scripts.QuestSteps.ObjectStep;
import scripts.Timer;
import scripts.Utils;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EnterHome implements Task {

    int ADVERTISEMENT_ID = 29091;
    RSTile OUTSIDE_RIMMINGTON_PORTAL = new RSTile(2953, 3219, 0);
    ObjectStep clickAdvertisementStep = new ObjectStep(ADVERTISEMENT_ID,
            "View", OUTSIDE_RIMMINGTON_PORTAL,
            Interfaces.isInterfaceSubstantiated(52));

    public boolean clickAdvertisement() {
        if (!Interfaces.isInterfaceSubstantiated(52)) {
            Log.debug("[Debug]: Opening host advertisements");
            if (OUTSIDE_RIMMINGTON_PORTAL.distanceTo(Player.getPosition()) > 20)
                PathingUtil.walkToTile(OUTSIDE_RIMMINGTON_PORTAL, 2, false);


            Optional<GameObject> ad = Query.gameObjects().nameContains("House Advertisement").stream().findFirst();
            if (ad.isPresent() && !ad.get().isVisible()) {

                LocalWalking.walkTo(ad.get().getTile().translate(General.random(0, 1), General.random(0, 1)));
                if (Timer.waitCondition(() -> Player.isMoving(), 1500, 2000))
                    Waiting.waitNormal(2050, 420);
            }
            if (Utils.clickObject(ADVERTISEMENT_ID, "View", true)) {
                Timer.slowWaitCondition(() -> Interfaces.get(52) != null, 5000, 7000);
            }
        }
        return Interfaces.get(52) != null;
    }

    List<Widget> houseBlackList = new ArrayList<>();
    List<Widget> goodHouses = new ArrayList<>();

    public void selectHost() {
        if (clickAdvertisement()) {
            List<Widget> button = Query.widgets().actionContains("Enter House")
                    .stream()
                    .filter(wid -> !houseBlackList.contains(wid))
                    .sorted(Comparator.comparing(w-> w.getBounds().getLocation().getY()))
                    .collect(Collectors.toList());
            for (Widget w : button) {
                Log.debug("[Debug]: Entering host");
                if (w.click("Enter House") &&
                        Waiting.waitUntil(4500, 800, Game::isInInstance)) {
                    Waiting.waitNormal(2000,150);
                    return;
                } else {
                    //  Log.log("[Debug]: Blacklisting failed host");
                    houseBlackList.add(w);
                    clickAdvertisement();
                }
            }
        }
    }


    private boolean changeToRightWorld() {
        return WorldHopper.hop(330);
    }


    @Override
    public String toString() {
        return "Entering POH";
    }


    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return Vars.get().currentTask != null && Vars.get().currentTask.equals(SkillTasks.PRAYER)
               && !Vars.get().useWildernessAltar && !Game.isInInstance();
    }

    @Override
    public void execute() {
        if (changeToRightWorld())
            selectHost();
    }

    @Override
    public String taskName() {
        return "Prayer training";
    }
}
