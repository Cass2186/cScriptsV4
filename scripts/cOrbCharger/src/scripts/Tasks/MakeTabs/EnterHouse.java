package scripts.Tasks.MakeTabs;

import org.tribot.api.General;
import org.tribot.script.sdk.*;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.GameObject;
import org.tribot.script.sdk.types.Widget;
import org.tribot.script.sdk.types.WorldTile;
import org.tribot.script.sdk.walking.LocalWalking;
import scripts.ItemID;
import scripts.PathingUtil;
import scripts.QuestSteps.ObjectStep;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;
import scripts.Timer;
import scripts.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EnterHouse implements Task {

    List<Widget> houseBlackList = new ArrayList<>();
    List<Widget> goodHouses = new ArrayList<>();
    int STUDYING_ANIMATION = 9491;
    int ANIMATION_ID = 4068;
    int PARENT_INTERFACE_ID = 79;
    int NOTED_SOFT_CLAY = 1762;
    int SOFT_CLAY = 1761;


    int ADVERTISEMENT_ID = 29091;
    WorldTile OUTSIDE_RIMMINGTON_PORTAL = new WorldTile(2953, 3219, 0);


    public boolean clickAdvertisement() {
        if (!Widgets.isVisible(52)) {
            Log.debug("[Debug]: Opening host advertisements");
            if (OUTSIDE_RIMMINGTON_PORTAL.distanceTo(MyPlayer.getPosition()) > 20)
                PathingUtil.walkToTile(OUTSIDE_RIMMINGTON_PORTAL);


            Optional<GameObject> ad = Query.gameObjects()
                            .idEquals(ADVERTISEMENT_ID)
                            .stream()
                            .findFirst();

            if (ad.isPresent() && !ad.get().isVisible()) {
                LocalWalking.walkTo(ad.get().getTile().translate(Utils.random(0, 1), Utils.random(0, 1)));
                if (Timer.waitCondition(MyPlayer::isMoving, 1500, 2000))
                    Waiting.waitNormal(2050, 420);
            }
            if (Utils.clickObject(ADVERTISEMENT_ID, "View", true)) {
                Timer.slowWaitCondition(() -> Widgets.get(52).isPresent(), 5000, 7000);
            }
        }
        return Widgets.get(52).isPresent();
    }

    public void selectHost() {
        if (!GameState.isInInstance() && org.tribot.script.sdk.Inventory.contains(ItemID.SOFT_CLAY) &&
                clickAdvertisement()) {
            List<Widget> button = Query.widgets().actionContains("Enter House")
                    .isVisible()
                    .stream()
                    .filter(wid -> !houseBlackList.contains(wid))
                    .collect(Collectors.toList());
            Log.debug("Entering host");
            for (Widget w : button) {
                if (w.click("Enter House") && Timer.waitCondition(GameState::isInInstance, 3000, 4500)) {
                    Waiting.waitNormal(2500, 50);
                    return;
                } else {
                    //  Log.log("Blacklisting failed host");
                    //houseBlackList.add(w);
                    clickAdvertisement();
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
        return false;
    }

    @Override
    public void execute() {

    }

    @Override
    public String toString() {
        return "Entering House";
    }
}
