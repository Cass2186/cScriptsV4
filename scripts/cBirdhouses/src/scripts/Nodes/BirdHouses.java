package scripts.Nodes;

import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;

import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Waiting;
import scripts.Data.Areas;
import scripts.Data.Const;
import scripts.Data.Vars;

import scripts.PathingUtil;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;
import scripts.Timer;

import scripts.Utils;

public class BirdHouses implements Task {


    private void buildBirdHouse(RSTile birdhouseTile, int spaceId) {
        PathingUtil.walkToTile(birdhouseTile, 2, false);

        if (Inventory.find(Const.get().ALL_BIRDHOUSE_IDS).length < 1) {
            Vars.get().status = "Making Birdhouse";
            General.println("[Debug]: " + Vars.get().status);
            if (Utils.useItemOnObject(Const.get().CHISEL, Const.get().LOGS))
                Timer.waitCondition(() -> Inventory.find(Const.get().ALL_BIRDHOUSE_IDS).length > 0, 35000, 5000);
        }

        if (Inventory.find(Const.get().ALL_BIRDHOUSE_IDS).length > 0) {
            Vars.get().status = "Building Birdhouse";
            General.println("[Debug]: " + Vars.get().status);

            for (int i = 0; i < 3; i++) {
                emptyBirdHouse(birdhouseTile, spaceId);
                if (NPCInteraction.isConversationWindowUp())
                    break;
            }
            buildHouse(spaceId);
        }

        addSeeds(spaceId);

    }

    public boolean buildHouse(int spaceId) {
        RSObject[] space = Objects.findNearest(10,
                Filters.Objects.idEquals(spaceId).and(Filters.Objects.actionsContains("Build")));
        for (int i = 0; i < 3; i++) {
            if (space.length > 0 && Utils.clickObject(space[0], "Build")) {
                Timer.waitCondition(() -> Player.getAnimation() != -1, 3000, 5000);
                General.sleep(General.random(250, 750));
            }

            space = Objects.findNearest(10,
                    Filters.Objects.idEquals(spaceId).and(Filters.Objects.actionsContains("Build")));
            if (space.length == 0)
                return true;
        }
        return false;
    }

    public boolean addSeeds(int spaceId) {
        for (int i = 0; i < 3; i++) {
            if (Utils.useItemOnObject(Const.get().BARLEY_SEED_ID, spaceId)) {
                Vars.get().status = "Adding seeds";
                General.println("[Debug]: " + Vars.get().status);
                NPCInteraction.waitForConversationWindow();
                if (NPCInteraction.isConversationWindowUp())
                    return true;
            }
        }
        return false;
    }

    public void updateTimers() {
        Vars.get().currentTime = System.currentTimeMillis();
        Vars.get().nextCollectionTime = System.currentTimeMillis() + 3000000;
        General.println("[Debug]: Next run at " + Vars.get().nextCollectionTime / 60000);
    }

    public boolean clickMushTree() {
        if (Utils.clickObject("Magic MushTree", "Use", false))
            return Timer.waitCondition(() -> Interfaces.isInterfaceSubstantiated(Const.get().MUSH_INTERFACE_MASTER,
                    Const.get().MUSH_INTERFACE_CHILD_2), 12000, 18000);
        return false;
    }

    private void useTeleport() {
        if (PathingUtil.walkToTile(Areas.get().WEST_MUSHROOM_TILE, 2, false))
            Utils.modSleep();

        if ( clickMushTree()) {
            if (Interfaces.isInterfaceSubstantiated(Const.get().MUSH_INTERFACE_MASTER, Const.get().MUSH_INTERFACE_CHILD_2)) {
                if (Interfaces.get(Const.get().MUSH_INTERFACE_MASTER, Const.get().MUSH_INTERFACE_CHILD_2).click())
                    Utils.modSleep();
            }

        }
    }


    private void emptyBirdHouse(RSTile birdhouseTile, int spaceId) {
        Vars.get().status = "Going to Birdhouse";
        General.println("[Debug]: " + Vars.get().status);

        PathingUtil.walkToTile(birdhouseTile, 5, false);

        RSObject[] fullHouse =
                Objects.findNearest(20, Filters.Objects.idEquals(spaceId).
                        and(Filters.Objects.actionsContains("Empty")));

        if (fullHouse.length > 0) {
            Vars.get().status = "Looting Birdhouse";
            General.println("[Debug]: " + Vars.get().status);

            if (Utils.clickObject(fullHouse[0], "Empty", true)) {
                Timer.waitCondition(() -> Player.getAnimation() != -1, 2500, 4000);
                General.sleep(General.random(250, 750));
            }
        }
    }

    public void doBirdhouseRun() {
        Login.login();

        emptyBirdHouse(Areas.get().SPACE_ONE_TILE, Const.get().SPACE_ONE_ID);
        buildBirdHouse(Areas.get().SPACE_ONE_TILE, Const.get().SPACE_ONE_ID);


        emptyBirdHouse(Areas.get().SPACE_TWO_TILE, Const.get().SPACE_TWO_ID);
        buildBirdHouse(Areas.get().SPACE_TWO_TILE, Const.get().SPACE_TWO_ID);
        Vars.get().nextRunTimer.reset();
        updateTimers();
        useTeleport();


        emptyBirdHouse(Areas.get().SPACE_THREE_TILE, Const.get().SPACE_THREE_ID);
        if (Skills.getActualLevel(Skills.SKILLS.HUNTER) <11)
            clickMushTree();
        buildBirdHouse(Areas.get().SPACE_THREE_TILE, Const.get().SPACE_THREE_ID);
        Vars.get().nextRunTimer.reset();

        emptyBirdHouse(Areas.get().SPACE_FOUR_TILE, Const.get().SPACE_FOUR_ID);
        buildBirdHouse(Areas.get().SPACE_FOUR_TILE, Const.get().SPACE_FOUR_ID);
        int i = General.random(30000,60000);
        Log.log("[debug]: Sleeping for " + i + "ms");
        Waiting.wait(i);
    }


    @Override
    public String toString(){
        return "Collecting birdhouses";
    }

    @Override
    public void execute() {
        doBirdhouseRun();
        Vars.get().shouldBank = true;
    }

    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return Vars.get().currentTime >= Vars.get().nextCollectionTime
                && Inventory.find(Vars.get().currentBirdHouseId).length > 0 && !Vars.get().shouldBank;
    }
}
