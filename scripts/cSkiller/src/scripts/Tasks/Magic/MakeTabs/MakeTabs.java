package scripts.Tasks.Magic.MakeTabs;

import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSInterfaceComponent;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSObject;
import org.tribot.script.sdk.Log;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.InterfaceUtil;
import scripts.PathingUtil;
import scripts.Timer;
import scripts.Utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MakeTabs implements Task {

    int STUDYING_ANIMATION = 9491;
    int ANIMATION_ID = 4068;
    int PARENT_INTERFACE_ID = 79;
    int NOTED_SOFT_CLAY = 1762;
    int SOFT_CLAY = 1761;


    public void enterHouse() {
        if (Inventory.find(SOFT_CLAY).length > 0 && !Interfaces.isInterfaceSubstantiated(52)) {
            Log.debug("Clicking House ads");
            RSObject[] ads = Objects.findNearest(20, 29091);
            if (ads.length > 0 && Utils.clickObject(ads[0], "View"))
                Timer.waitCondition(() -> Interfaces.isInterfaceSubstantiated(52), 8000, 12000);
        }
        if (Interfaces.isInterfaceSubstantiated(52)) {
            Log.debug("Selecting House");
            // get and cache all children of the interface that contains the buttons
            RSInterfaceComponent[] buttons = Interfaces.get(52, 19).getChildren();

            /**
             * now we turn the array into a stream and filter it so we're only left with components that are NOT null
             * then we filter again to check the action that contain enter just to be safe.
             * this then stores the filtered interfaces as a list of RSInterfaceComponent, but unsorted
             */

            List<RSInterfaceComponent> ls = Arrays.stream(buttons).filter(b ->
                    b.getActions() != null).filter(b -> b.getActions()[0].contains("Enter")).collect(Collectors.toList());

            int y = 400; // set a high Y value (arbitrary number)
            RSInterfaceComponent comp = null;

            for (int i = 0; i < ls.size(); i++) { // now we iterate through the unsorted list of components
                if (ls.get(i).getY() < y) { // if the present component's Y is < Y, we the store that one as it's higher up
                    y = ls.get(i).getY(); //stores the y value for future comparisons
                    comp = ls.get(i); // stores the actual interface component
                }
            }
            if (comp != null) {
                if (comp.click()) {// clicks the final interface component that should be the one with the lowest Y (therefore highest up)
                    Timer.slowWaitCondition(() -> Objects.findNearest(30, "Lectern").length > 0, 7000, 9000);
                    General.sleep(General.random(1500, 3000));
                }
            }
        }

    }

    public boolean atLecturn() {
        return Objects.findNearest(30, "Lectern").length > 0;
    }

    public void leaveHouse() {
        RSObject[] lectern = Objects.findNearest(30, "Lectern");
        if (lectern.length > 0 && Inventory.find(SOFT_CLAY).length == 0) {
            Log.debug("Leaving House");
            if (Utils.clickObj(4525, "Enter")) {
                Timer.waitCondition(() -> !atLecturn(), 10000, 13000);
            }
        }
    }

    public void studyLecturn(String teleport) {
        if (Inventory.find("Law rune").length > 0 && Inventory.find(SOFT_CLAY).length > 0
                && atLecturn()) {

            if (Player.getAnimation() == ANIMATION_ID) {
                Log.debug("Making Tabs");
                Timer.abc2SkillingWaitCondition(() -> Inventory.find(SOFT_CLAY).length == 0, 60000, 69000);
                profit = Utils.getInventoryValue() - startInvValue;
                General.println("[Debug]: Current profit is " + profit);
                return;
            }

            RSObject[] lectern = Objects.findNearest(30, "Lectern");
            if (lectern.length > 0) {
                Log.debug("Going to Lectern");

                if (!lectern[0].isClickable() || lectern[0].getPosition().distanceTo(Player.getPosition()) > 10) {
                    Log.debug("Walking closer to Lectern");
                    Walking.blindWalkTo(lectern[0].getPosition());
                    Timer.waitCondition(() -> lectern[0].isClickable(), 6000, 9000);
                }

                if (Utils.clickObject(lectern[0], "Study"))
                    Timer.waitCondition(() -> Interfaces.isInterfaceSubstantiated(PARENT_INTERFACE_ID), 8000, 12000);

                if (Interfaces.isInterfaceSubstantiated(PARENT_INTERFACE_ID) &&
                        InterfaceUtil.clickInterfaceText(PARENT_INTERFACE_ID, teleport))
                    Timer.waitCondition(() -> Player.getAnimation() == ANIMATION_ID, 5000, 7000);

            }
        }
    }

    public void unNoteClay() {
        if (Inventory.find(SOFT_CLAY).length == 0 && !atLecturn()) {
            RSNPC[] phials = NPCs.findNearest("Phials");
            if (phials.length > 0 && phials[0].getPosition().distanceTo(Player.getPosition()) > 6) {
                PathingUtil.localNavigation(phials[0].getPosition());
                General.sleep(General.random(500, 1700));
            }
            if (Utils.useItemOnNPC(NOTED_SOFT_CLAY, "Phials")) {
                Log.debug("Un-noting Clay");

                General.sleep(General.random(2500, 3500));

                NPCInteraction.waitForConversationWindow();

                InterfaceUtil.clickInterfaceText(219, 1, "Exchange All:");
                Timer.waitCondition(() -> Inventory.find(SOFT_CLAY).length > 0, 2500, 3500);

            }
        }
    }

    public boolean hasAnyClay() {
        return Inventory.find(SOFT_CLAY).length > 0 || Inventory.find(NOTED_SOFT_CLAY).length > 0;
    }

    public static int startInvValue = Utils.getInventoryValue();
    public static int profit = 0;


    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return false;
    }

    @Override
    public void execute() {

    }

    @Override
    public String taskName() {
        return "Magic: Tabs";
    }
}
