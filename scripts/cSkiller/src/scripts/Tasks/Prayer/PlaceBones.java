package scripts.Tasks.Prayer;

import org.tribot.api.General;
import org.tribot.api2007.Game;
import org.tribot.api2007.Player;
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
        Optional<GameObject> altar = Query.gameObjects().nameContains("Altar").actionContains("Pray").
                stream().findFirst();

        if (Player.getAnimation() ==-1  && item.isPresent() && altar.isPresent()) {
            General.println("[Debug]: Placing bones");
            if (Player.getAnimation() != -1) {
                Timer.abc2SkillingWaitCondition(() -> Query.inventory().nameContains("bones").isNotNoted().findFirst().isEmpty(),
                        65000, 75000);

            } else if (Utils.useItemOnObject(item.get().getId(), altar.get().getId()) &&
                    Timer.waitCondition(() -> Player.getAnimation() != -1, 4600, 6200)) {
                General.println("[Debug]: Idling");

                Timer.abc2SkillingWaitCondition(() -> Query.inventory().nameContains("bones").isNotNoted().findFirst().isEmpty(),
                        65000, 75000);
            }
        } else {
            General.println("[Debug]: Sleeping 2-5s for altar to load");
            Waiting.waitNormal(3000, 600);
        }
    }

    @Override
    public String toString(){
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
