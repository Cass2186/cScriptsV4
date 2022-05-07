package scripts.Tasks.MakeTabs;

import dax.walker_engine.interaction_handling.NPCInteraction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;

import org.tribot.script.sdk.*;

import org.tribot.script.sdk.Inventory;
import org.tribot.script.sdk.Options;
import org.tribot.script.sdk.WorldHopper;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.*;
import org.tribot.script.sdk.walking.LocalWalking;
import scripts.*;
import scripts.Data.Const;
import scripts.Data.Tabs;
import scripts.Data.Vars;
import scripts.QuestSteps.ObjectStep;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;
import scripts.Timer;
import scripts.rsitem_services.GrandExchange;

import java.util.*;
import java.util.stream.Collectors;

public class MakeTabs implements Task {

    private int ANIMATION_ID = 4068;
    private int PARENT_INTERFACE_ID = 79;

    public static boolean atLecturn() {
        return Query.gameObjects()
                .nameContains("Lectern")
                .maxDistance(30)
                .isReachable()
                .isAny();
    }

    private void studyLecturn(String teleport) {
        if (Inventory.contains(ItemID.LAW_RUNE, ItemID.SOFT_CLAY) && atLecturn()) {
            if (MyPlayer.getAnimation() == ANIMATION_ID) {
                Log.debug("Making Tabs");
                Waiting.waitUntil(70000, Utils.random(1000, 2400), () ->
                        !Inventory.contains(ItemID.SOFT_CLAY) ||
                                Widgets.get(233, 2).isPresent());
                Vars.get().profit = Utils.getInventoryValue() - Const.startInvValue;
                //Log.debug("Current profit is " + profit);
                Utils.idleAfkAction();
                return;
            }

            Optional<GameObject> lectern = Query.gameObjects()
                    .nameContains("Lectern")
                    .maxDistance(30)
                    .isReachable() //TODO verify this doesn't break it
                    .findBestInteractable();

            if (!Widgets.isVisible(PARENT_INTERFACE_ID) && lectern.isPresent()) {

                if (lectern.map(l -> !l.isVisible() && l.getTile().distance() > 10).orElse(false)) {
                    Log.info("Walking closer to Lectern");
                    if (lectern.map(l -> PathingUtil.localNav(l.getTile().translate(-1, -1))).orElse(false))
                        Waiting.waitUntil(8000, 450, () -> lectern.get().isVisible());
                }

                if (lectern.map(l -> l.interact("Study")).orElse(false))
                    Waiting.waitUntil(10000, 750, () ->
                            Widgets.isVisible(PARENT_INTERFACE_ID));


                Optional<Widget> tabWidget = Query.widgets()
                        .inIndexPath(PARENT_INTERFACE_ID)
                        .nameContains(teleport)
                        .isVisible()
                        .findFirst();

                if (tabWidget.map(Widget::click).orElse(false))
                    Waiting.waitUntil(6000, 550, () -> Player.getAnimation() == ANIMATION_ID);

            }
        }
    }


    public static int profitPerTab(Tabs tab) {
        int lawPrice = scripts.rsitem_services.GrandExchange.getPrice(ItemID.LAW_RUNE);
        int tabPrice = GrandExchange.getPrice(tab.getTabID());
        int profit = tabPrice - (lawPrice + 5);
        //  Log.debug("Profit per tab " + tab.getName() + " is " + profit + "gp");
        return profit;
    }


    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return Inventory.contains(ItemID.LAW_RUNE, ItemID.SOFT_CLAY) &&
                atLecturn();
    }

    @Override
    public void execute() {
        if (WorldHopper.getCurrentWorld() != 330) {
            if (ChatScreen.isOpen())
                ChatScreen.handle();
            WorldHopper.hop(330);
            return;
        }

        studyLecturn(Vars.get().selectedTab.getName());

    }

    @Override
    public String toString() {
        return "Making Tabs";
    }

}
