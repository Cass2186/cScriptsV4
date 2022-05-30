package scripts.Tasks.BrutalBlackDragons;

import org.tribot.script.sdk.types.WorldTile;
import scripts.Data.Vars;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;

public class GoToBrutalBlack implements Task {

    WorldTile SAFE_TILE = new WorldTile(1613,10088,0);


    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        return Vars.get().killingBrutalBlacks;
    }

    @Override
    public void execute() {

    }
}
