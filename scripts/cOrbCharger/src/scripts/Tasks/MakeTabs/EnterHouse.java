package scripts.Tasks.MakeTabs;

import org.tribot.api.General;
import org.tribot.script.sdk.*;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.GameObject;
import org.tribot.script.sdk.types.Widget;
import org.tribot.script.sdk.types.WorldTile;
import org.tribot.script.sdk.walking.LocalWalking;
import scripts.Data.Const;
import scripts.Data.Vars;
import scripts.ItemID;
import scripts.PathingUtil;
import scripts.QuestSteps.ObjectStep;
import scripts.Requirements.ItemReq;
import scripts.Requirements.ItemRequirement;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;
import scripts.Timer;
import scripts.Utils;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EnterHouse implements Task {

    private int ADVERTISEMENT_ID = 29091;
    private WorldTile OUTSIDE_RIMMINGTON_PORTAL = new WorldTile(2953, 3219, 0);

    private Optional<String> getHostNameFromButtonWidget(Widget widget) {
        double y = widget.getBounds().getLocation().getY();
        Optional<Widget> nameWidget = Query.widgets()
                .inIndexPath(Const.HOUSE_AD_WIDGET_PARENT, 9)
                .filter(w -> w.getBounds().getHeight() < 30) // the box is 25 tall in fixed mode
                .filter(w -> w.getBounds().contains(w.getBounds().getX(), y))
                .filter(w -> w.getText().isPresent())
                .stream().findFirst();


        if (nameWidget.isPresent()) {
            Optional<String> name = nameWidget.get().getText();
            if (name.isPresent()) {
                Log.debug("Name is " + nameWidget.get().getText().get());
                return name;
            }

        }
        return Optional.empty();
    }

    private boolean clickAdvertisement() {
        if (!Widgets.isVisible(Const.HOUSE_AD_WIDGET_PARENT)) {
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
            if (ad.map(a -> a.interact("View")).orElse(false)) {
                Timer.slowWaitCondition(() -> Widgets.get(52).isPresent(), 5000, 7000);
            }
        }
        return Widgets.get(Const.HOUSE_AD_WIDGET_PARENT).isPresent();
    }

    private void selectHost() {
        if (!GameState.isInInstance() && Inventory.contains(ItemID.SOFT_CLAY) &&
                clickAdvertisement()) {

            List<Widget> button = Query.widgets()
                    .actionContains("Enter House")
                    .isVisible()
                    .stream().sorted(Comparator.comparingInt(a -> (int) a.getBounds().getY()))
                    .collect(Collectors.toList());

            Log.debug("Entering host");
            for (Widget w : button) {

                Optional<String> name = getHostNameFromButtonWidget(w);
                if (name.isPresent() && Vars.get().houseBlackListNames.contains(name.get())) {
                    Log.debug("Blacklisted host detected, continuing");
                    continue;
                }

                if (w.click("Enter House") && Timer.waitCondition(GameState::isInInstance, 3000, 4500)) {
                    name.ifPresent(n -> Vars.get().mostRecentHouse = n);
                    Waiting.waitNormal(2500, 125); //wait after arriving in instance for house screen to disapear
                    blacklistFailedHouseAndLeave();
                    return;
                } else {
                    Log.error("Blacklisting failed host");
                    name.ifPresent(n -> Vars.get().houseBlackListNames.add(n));
                    clickAdvertisement();
                }
            }

        }
    }

    private void blacklistFailedHouseAndLeave() {
        Optional<GameObject> lectern = Query.gameObjects()
                .nameContains("Lectern")
                .maxDistance(30)
                .findBestInteractable();

        Optional<GameObject> reachable = Query.gameObjects()
                .nameContains("Lectern")
                .isReachable()
                .maxDistance(30)
                .findBestInteractable();
        if (lectern.isPresent() && reachable.isEmpty()) {
            Log.error("Cannot raech lecturn, blacklisting house");
            Vars.get().houseBlackListNames.add(Vars.get().mostRecentHouse);
            UnnoteClay.leaveHouse();
        }
    }


    public void test(){
        ItemRequirement itemReq = new ItemRequirement.Builder()
                .id(ItemID.RING_OF_WEALTH_5)
                .chargesNeeded(1)
                .minAmount(1)
                .build();

        Log.error("ItemRequrement charge check " + itemReq.hasEnoughCharges());



    }

    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        return !GameState.isInInstance() && Inventory.contains(ItemID.SOFT_CLAY);
    }

    @Override
    public void execute() {
        test();
        if (WorldHopper.getCurrentWorld() != 330) {
            WorldHopper.hop(330);
            return;
        }
        selectHost();
    }

    @Override
    public String toString() {
        return "Entering House";
    }
}
