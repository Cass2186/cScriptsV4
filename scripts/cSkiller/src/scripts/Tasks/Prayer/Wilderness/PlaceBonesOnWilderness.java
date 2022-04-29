package scripts.Tasks.Prayer.Wilderness;

import org.tribot.api.General;
import org.tribot.api2007.Combat;
import org.tribot.api2007.Game;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.Player;
import org.tribot.script.sdk.ChatScreen;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.MyPlayer;
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

public class PlaceBonesOnWilderness implements Task {

    public void placeBones() {
        Optional<InventoryItem> item = Query.inventory()
                .nameContains("bones").isNotNoted().findFirst();
        Optional<GameObject> altar = Query.gameObjects()
                .nameContains("Chaos altar")
                .findClosest();

        if (Player.getAnimation() == -1 && item.isPresent() && altar.isPresent()) {
            Log.info("Placing bones");
            PkObserver.scrollToWorldNoClick(PkObserver.nextWorld);
            if (altar.map(a -> item.map(i -> i.useOn(a))
                    .orElse(false)).orElse(false) &&
                    //  if (Utils.useItemOnObject(item.get().getId(), altar.get().getId()) &&
                    Timer.waitCondition(() -> Player.getAnimation() != -1, 4600, 6200)) {
                Log.info("Idling");
            }
        }
        if (Waiting.waitUntil(75000, 50, () -> {
            if (MyPlayer.getAnimation() == -1 &&
                    !Waiting.waitUntil(800, () ->
                            MyPlayer.getAnimation() != -1)) {
                return true;
            }
            PkObserver.scrollToWorldNoClick(PkObserver.nextWorld);
            return ChatScreen.isOpen() ||
                    PkObserver.shouldHop() ||
                    Query.inventory().nameContains("bones")
                            .isNotNoted().findFirst().isEmpty();
        })) {
            Waiting.waitNormal(50, 10);
        }

    }

    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        Optional<InventoryItem> item = Query.inventory().nameContains("bones").isNotNoted().findFirst();
        return Vars.get().currentTask != null &&
                Vars.get().currentTask.equals(SkillTasks.PRAYER) &&
                Vars.get().useWildernessAltar &&
                Combat.isInWilderness() && item.isPresent();
    }

    @Override
    public void execute() {
        placeBones();
    }

    @Override
    public String taskName() {
        return null;
    }
}
