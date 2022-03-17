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


    int STUDYING_ANIMATION = 9491;
    int ANIMATION_ID = 4068;
    int PARENT_INTERFACE_ID = 79;
    int NOTED_SOFT_CLAY = 1762;
    int SOFT_CLAY = 1761;

    public static boolean atLecturn() {
        return Query.gameObjects()
                .nameContains("Lectern")
                .maxDistance(30)
                .findBestInteractable()
                .isPresent();
    }

    public void studyLecturn(String teleport) {
        if (Inventory.contains(ItemID.LAW_RUNE) &&
                Inventory.contains(ItemID.SOFT_CLAY) && atLecturn()) {
            if (MyPlayer.getAnimation() == ANIMATION_ID) {
                Log.debug("Making Tabs");
                Timer.slowWaitCondition(() ->
                        !Inventory.contains(ItemID.SOFT_CLAY) ||
                                Widgets.get(233, 2).isPresent(), 60000, 69000);
                Vars.get().profit = Utils.getInventoryValue() - Const.startInvValue;
                //Log.debug("Current profit is " + profit);
                Utils.idleAfkAction();
                return;
            }
            Optional<GameObject> lectern = Query.gameObjects().nameContains("Lectern")
                    .findBestInteractable();

            if (lectern.isPresent()) {
                Log.debug("Going to Lectern");

                if (!lectern.get().isVisible() || lectern.get().getTile().distanceTo(MyPlayer.getPosition()) > 10) {
                    Log.debug("Walking closer to Lectern");
                    if (lectern.map(l -> LocalWalking.walkTo(l.getTile())).orElse(false))
                        Timer.waitCondition(() -> lectern.get().isVisible(), 6000, 9000);
                }

                if (!Interfaces.isInterfaceSubstantiated(PARENT_INTERFACE_ID) && Utils.clickObj(lectern, "Study"))
                    Timer.slowWaitCondition(() -> Interfaces.isInterfaceSubstantiated(PARENT_INTERFACE_ID), 8000, 12000);


                Optional<Widget> tabWidget = Query.widgets()
                        .inIndexPath(PARENT_INTERFACE_ID)
                        .nameContains(teleport + " Teleport")
                        .isVisible()
                        .findFirst();

                if (tabWidget.map(Widget::click).orElse(false))
                    Timer.waitCondition(() -> Player.getAnimation() == ANIMATION_ID, 5000, 7000);

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
        return org.tribot.script.sdk.Inventory.contains(ItemID.LAW_RUNE) &&
                org.tribot.script.sdk.Inventory.contains(SOFT_CLAY) && atLecturn();
    }

    @Override
    public void execute() {
        if (WorldHopper.getCurrentWorld() != 330) {
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
