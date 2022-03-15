package scripts.Tasks.MakeTabs;

import dax.walker_engine.interaction_handling.NPCInteraction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.Objects;
import org.tribot.api2007.types.RSInterfaceComponent;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.*;

import org.tribot.script.sdk.Inventory;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.GameObject;
import org.tribot.script.sdk.types.InventoryItem;
import org.tribot.script.sdk.types.Npc;
import org.tribot.script.sdk.types.Widget;
import org.tribot.script.sdk.walking.LocalWalking;
import scripts.*;
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


    int ADVERTISEMENT_ID = 29091;
    RSTile OUTSIDE_RIMMINGTON_PORTAL = new RSTile(2953, 3219, 0);
    ObjectStep clickAdvertisementStep = new ObjectStep(ADVERTISEMENT_ID,
            "View", OUTSIDE_RIMMINGTON_PORTAL,
            Interfaces.isInterfaceSubstantiated(52));

    public boolean clickAdvertisement() {
        if (!Interfaces.isInterfaceSubstantiated(52)) {
            Log.debug("Opening host advertisements");
            if (OUTSIDE_RIMMINGTON_PORTAL.distanceTo(Player.getPosition()) > 20)
                PathingUtil.walkToTile(OUTSIDE_RIMMINGTON_PORTAL, 2, false);


            Optional<GameObject> ad =
                    Query.gameObjects().idEquals(ADVERTISEMENT_ID).stream().findFirst();
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
        if (!Game.isInInstance() && org.tribot.script.sdk.Inventory.contains(ItemID.SOFT_CLAY) && clickAdvertisement()) {
            List<Widget> button = Query.widgets().actionContains("Enter House")
                    .isVisible()
                    .stream()
                    .filter(wid -> !houseBlackList.contains(wid))
                    .collect(Collectors.toList());
            Log.debug("Entering host");
            for (Widget w : button) {

                if (w.click("Enter House") && Timer.waitCondition(Game::isInInstance, 3000, 4500)) {
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

    public static boolean atLecturn() {
        return Query.gameObjects()
                .nameContains("Lectern")
                .maxDistance(30)
                .findBestInteractable()
                .isPresent();
    }

    public void studyLecturn(String teleport) {
        if (org.tribot.script.sdk.Inventory.contains(ItemID.LAW_RUNE) &&
                org.tribot.script.sdk.Inventory.contains(SOFT_CLAY) && atLecturn()) {
            if (MyPlayer.getAnimation() == ANIMATION_ID) {
                Log.debug("Making Tabs");
                Timer.slowWaitCondition(() ->
                        !org.tribot.script.sdk.Inventory.contains(SOFT_CLAY) ||
                                Widgets.get(233, 2).isPresent(), 60000, 69000);
                profit = Utils.getInventoryValue() - startInvValue;
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

    @AllArgsConstructor
    public enum Tabs {
        VARROCK(25, ItemID.FIRE_RUNE, 25, "Varrock", ItemID.VARROCK_TELEPORT),
        LUMBRIDGE(31, ItemID.EARTH_RUNE, 37, "Lumbridge", ItemID.LUMBRIDGE_TELEPORT),
        HOUSE(40, ItemID.EARTH_RUNE, 30, "House", ItemID.TELEPORT_TO_HOUSE),
        FALADOR(37, ItemID.WATER_RUNE, 48, "Falador", ItemID.FALADOR_TELEPORT),
        CAMELOT(45, -1, 55, "Camelot", ItemID.CAMELOT_TELEPORT);

        @Getter
        private int levelReq;
        @Getter
        private int otherRuneId;
        private int xpGained;
        @Getter
        private String name;
        @Getter
        private int tabID;

    }

    public boolean canCraftTab(Tabs t) {
        return Skill.MAGIC.getActualLevel() >= t.getLevelReq();
    }

    public Tabs getMostProfitableTab() {
        Optional<Tabs> t = Arrays.stream(Tabs.values()).max(Comparator.comparingInt(this::profitPerTab));
        Tabs tab = t.orElse(Tabs.VARROCK);
        Log.debug("Best tab is " + tab.getName());
        return tab;
    }


    private int profitPerTab(Tabs tab) {
        int lawPrice = scripts.rsitem_services.GrandExchange.getPrice(ItemID.LAW_RUNE);
        int tabPrice = GrandExchange.getPrice(tab.getTabID());
        int profit = tabPrice - (lawPrice + 5);
        Log.debug("Profit per tab " + tab.getName() + " is " + profit + "gp");
        return profit;
    }

    public static int startInvValue = Utils.getInventoryValue();
    public static int profit = 0;


    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return true;
    }

    @Override
    public void execute() {
        selectHost();
        studyLecturn(Tabs.VARROCK.getName());
    }

    @Override
    public String toString() {
        return "Making Tabs";
    }

}
