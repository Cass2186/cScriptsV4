package scripts.Tasks.KourendFavour.Hosidius;

import org.tribot.script.sdk.types.WorldTile;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.Vars;
import scripts.NpcChat;
import scripts.PathingUtil;

public class CashInCompost implements Task {

    WorldTile CLERK_TILE = new WorldTile(1701,3525,0);

    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        return Vars.get().hosaFavour &&  Vars.get().shouldCashInCompost;
    }

    @Override
    public void execute() {
        PathingUtil.walkToTile(CLERK_TILE);
        if(NpcChat.talkToNPC("Clerk")){
            NpcChat.handle(true, "Yes");
            Vars.get().shouldCashInCompost = false;
        }
    }


    @Override
    public String toString() {
        return "Cashing in Compost";
    }

    @Override
    public String taskName() {
        return "Hosidius Favour";
    }
}
