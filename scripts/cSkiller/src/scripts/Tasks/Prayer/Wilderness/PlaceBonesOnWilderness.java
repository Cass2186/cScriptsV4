package scripts.Tasks.Prayer.Wilderness;

import org.tribot.api.General;
import org.tribot.api2007.Combat;
import org.tribot.api2007.Game;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.Player;
import org.tribot.script.sdk.*;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.GameObject;
import org.tribot.script.sdk.types.InventoryItem;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.Tasks.BirdHouseRuns.Nodes.Wait;
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

        //will scroll to the world only upon initially arriving
        if (Inventory.isFull())
            PkObserver.scrollToWorldNoClick(PkObserver.nextWorld);

        if (MyPlayer.getAnimation() == -1 && item.isPresent() && altar.isPresent()) {
            Log.info("Placing bones");

            if (altar.map(a -> item.map(i -> i.useOn(a))
                    .orElse(false)).orElse(false) &&
                    Waiting.waitUntil(5000, 50, () -> PkObserver.shouldHop() ||
                            MyPlayer.getAnimation() != -1)) {
                Log.info("Idling");
            }
        }
        if (Waiting.waitUntil(75000, 50, () -> {
                    if (MyPlayer.getAnimation() == -1 &&
                            !Waiting.waitUntil(900, 10, () ->
                                    PkObserver.shouldHop() ||  //TODO make sure this works
                                            MyPlayer.getAnimation() != -1)) {
                        return true;
                    }
                    //have this otherwise it flips between inv and hop alot when leveling quickly
                    if (Skill.PRAYER.getActualLevel() > 25)
                        PkObserver.scrollToWorldNoClick(PkObserver.nextWorld);

                    return ChatScreen.isOpen() ||
                            PkObserver.shouldHop() ||
                            Query.inventory().nameContains("bones")
                                    .isNotNoted().findFirst().isEmpty();
                }
        )) {
            Waiting.waitNormal(50, 10);
            // wait after returning
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
    public String toString() {
        return "Placing Bones";
    }

    @Override
    public String taskName() {
        return "Prayer - Wilderness";
    }
}
