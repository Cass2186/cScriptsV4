package scripts.Tasks.BirdHouseRuns.Nodes;

import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Waiting;
import scripts.Tasks.BirdHouseRuns.Data.BirdHouseVars;
import scripts.PathingUtil;
import scripts.Tasks.BirdHouseRuns.Data.Areas;
import scripts.Tasks.BirdHouseRuns.Data.Const;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;
import scripts.Timer;
import scripts.Utils;

public class BirdHouses implements Task {


    private void buildBirdHouse(RSTile birdhouseTile, int spaceId) {
        PathingUtil.walkToTile(birdhouseTile, 2, false);

        if (Inventory.find(Const.ALL_BIRDHOUSE_IDS).length < 1) {
           // BirdHouseVars.get().status = "Making Birdhouse";

            if (Utils.useItemOnObject(Const.CHISEL, Const.LOGS))
                Timer.waitCondition(() -> Inventory.find(Const.ALL_BIRDHOUSE_IDS).length > 0, 35000, 5000);
        }

        if (Inventory.find(Const.ALL_BIRDHOUSE_IDS).length > 0) {
            //BirdHouseVars.get().status = "Building Birdhouse";


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
            if (Utils.useItemOnObject(Const.BARLEY_SEED_ID, spaceId)) {
               // BirdHouseVars.get().status = "Adding seeds";

                NPCInteraction.waitForConversationWindow();
                if (NPCInteraction.isConversationWindowUp())
                    return true;
            }
        }
        return false;
    }

    public void updateTimers() {
        BirdHouseVars.get().currentTime = System.currentTimeMillis();
        BirdHouseVars.get().nextCollectionTime = System.currentTimeMillis() + 3000000;
        General.println("[Debug]: Next run at " + BirdHouseVars.get().nextCollectionTime / 60000);
    }

    public boolean clickMushTree() {
        if (Utils.clickObject("Magic MushTree", "Use", false))
            return Timer.waitCondition(() -> Interfaces.isInterfaceSubstantiated(Const.MUSH_INTERFACE_MASTER,
                    Const.MUSH_INTERFACE_CHILD_2), 12000, 18000);
        return false;
    }

    private void useTeleport() {
        if (PathingUtil.walkToTile(Areas.WEST_MUSHROOM_TILE, 2, false))
            Utils.modSleep();

        if ( clickMushTree()) {
            if (Interfaces.isInterfaceSubstantiated(Const.MUSH_INTERFACE_MASTER, Const.MUSH_INTERFACE_CHILD_2)) {
                if (Interfaces.get(Const.MUSH_INTERFACE_MASTER, Const.MUSH_INTERFACE_CHILD_2).click())
                    Utils.modSleep();
            }

        }
    }


    private void emptyBirdHouse(RSTile birdhouseTile, int spaceId) {
      //  BirdHouseVars.get().status = "Going to Birdhouse";

        PathingUtil.walkToTile(birdhouseTile, 5, false);

        RSObject[] fullHouse =
                Objects.findNearest(20, Filters.Objects.idEquals(spaceId).
                        and(Filters.Objects.actionsContains("Empty")));

        if (fullHouse.length > 0) {
          //  BirdHouseVars.get().status = "Looting Birdhouse";


            if (Utils.clickObject(fullHouse[0], "Empty", true)) {
                Timer.waitCondition(() -> Player.getAnimation() != -1, 2500, 4000);
                General.sleep(General.random(250, 750));
            }
        }
    }

    public void doBirdhouseRun() {
        Login.login();

        emptyBirdHouse(Areas.SPACE_ONE_TILE, Const.SPACE_ONE_ID);
        buildBirdHouse(Areas.SPACE_ONE_TILE, Const.SPACE_ONE_ID);


        emptyBirdHouse(Areas.SPACE_TWO_TILE, Const.SPACE_TWO_ID);
        buildBirdHouse(Areas.SPACE_TWO_TILE, Const.SPACE_TWO_ID);
        BirdHouseVars.get().nextRunTimer.reset();
        updateTimers();
        useTeleport();


        emptyBirdHouse(Areas.SPACE_THREE_TILE, Const.SPACE_THREE_ID);
        if (Skills.getActualLevel(Skills.SKILLS.HUNTER) <11)
            clickMushTree();
        buildBirdHouse(Areas.SPACE_THREE_TILE, Const.SPACE_THREE_ID);
        BirdHouseVars.get().nextRunTimer.reset();

        emptyBirdHouse(Areas.SPACE_FOUR_TILE, Const.SPACE_FOUR_ID);
        buildBirdHouse(Areas.SPACE_FOUR_TILE, Const.SPACE_FOUR_ID);
        int i = General.random(30000,60000);
        Log.debug("[debug]: Sleeping for " + i + "ms");
        Waiting.wait(i);
    }


    @Override
    public String toString(){
        return "Collecting birdhouses";
    }

    @Override
    public void execute() {
        doBirdhouseRun();
        BirdHouseVars.get().shouldBank = true;
    }

    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return BirdHouseVars.get().currentTime >= BirdHouseVars.get().nextCollectionTime
                && Inventory.find(BirdHouseVars.get().currentBirdHouseId).length > 0 && !BirdHouseVars.get().shouldBank;
    }
}
