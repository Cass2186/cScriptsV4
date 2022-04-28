package scripts.Tasks.Prayer;

import org.tribot.api.General;
import org.tribot.api2007.Game;
import org.tribot.api2007.Player;
import org.tribot.script.sdk.ChatScreen;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.GameObject;
import org.tribot.script.sdk.types.InventoryItem;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.Timer;
import scripts.Utils;

import java.util.Optional;

public class PlaceBones implements Task {


    public void placeBones() {
        Optional<InventoryItem> item = Query.inventory().nameContains("bones").isNotNoted().findFirst();
        Optional<GameObject> altar = Query.gameObjects()
                .nameContains("Altar")
                .actionContains("Pray")
                        .findClosest();

        if (Player.getAnimation() == -1 && item.isPresent() && altar.isPresent()) {
            Log.info("Placing bones");
            if (Player.getAnimation() != -1 &&
                    Timer.abc2SkillingWaitCondition(() ->
                                    ChatScreen.isOpen() ||
                                            Query.inventory().nameContains("bones").isNotNoted().findFirst().isEmpty(),
                            65000, 75000)) {
                Utils.sleepAfkOrNormal(Utils.random(70, 85));

            } else if (altar.map(a-> item.map(i->i.useOn(a))
                    .orElse(false)).orElse(false) &&
              //  if (Utils.useItemOnObject(item.get().getId(), altar.get().getId()) &&
                    Timer.waitCondition(() -> Player.getAnimation() != -1, 4600, 6200)) {
                Log.info("Idling");

                if (Waiting.waitUntil(75000, 1250, () -> ChatScreen.isOpen() ||
                        Query.inventory().nameContains("bones").isNotNoted().findFirst().isEmpty()))
                    Utils.sleepAfkOrNormal(Utils.random(70, 85));

            }
        } else {
            General.println("[Debug]: Sleeping ~3s for altar to load");
            Waiting.waitNormal(2800, 200);
        }
    }

    @Override
    public String toString() {
        return "Placing bones";
    }


    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        Optional<InventoryItem> item = Query.inventory().nameContains("bones").isNotNoted().findFirst();
        return Vars.get().currentTask != null && Vars.get().currentTask.equals(SkillTasks.PRAYER) &&
                Game.isInInstance() && item.isPresent();
    }

    @Override
    public void execute() {
        placeBones();
    }

    @Override
    public String taskName() {
        return "Prayer training";
    }
}
