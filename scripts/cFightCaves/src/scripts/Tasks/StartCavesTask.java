package scripts.Tasks;

import dax.walker_engine.interaction_handling.NPCInteraction;
import obf.G;
import org.tribot.api.DynamicClicking;
import org.tribot.api2007.NPCChat;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.Script;
import org.tribot.script.sdk.*;
import scripts.Data.Const;
import scripts.Data.Wave;
import scripts.GameSettings;
import scripts.PathingUtil;
import scripts.QueryUtils;

import java.io.IOException;

public class StartCavesTask implements Task{
    GenerateMap map = new GenerateMap();
    private RSObject caveEntrance = null;


    @Override
    public String toString() {
        return "Starting Fight";
    }


    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        return !GameState.isInInstance()&&
                Wave.getCurrentWave() == 0;
    }

    @Override
    public void execute() {
        if (GameState.isInInstance()) {
            Log.warn("In instance already");
            return;
        }
        if (PathingUtil.walkToArea(Const.START_AREA)) {
            if (QueryUtils.getObject(11833).map(
                    ent -> ent.interact("Enter")).orElse(false)) {
                Waiting.waitUntil(7500, 500,
                        () -> !Const.START_AREA.containsMyPlayer());
                map.execute();
            }
        }
    }
}
