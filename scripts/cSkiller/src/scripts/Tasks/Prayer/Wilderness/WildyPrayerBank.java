package scripts.Tasks.Prayer.Wilderness;

import org.tribot.api2007.Combat;
import org.tribot.script.sdk.Inventory;
import org.tribot.script.sdk.MyPlayer;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.GameObject;
import org.tribot.script.sdk.types.GroundItem;
import org.tribot.script.sdk.types.InventoryItem;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.Tasks.BirdHouseRuns.Nodes.Wait;

import java.util.Optional;

public class WildyPrayerBank implements Task {

    private void suicideWine(){
        Optional<GroundItem> wine = Query.groundItems()
                .actionContains("Take")
                .nameContains("Wine").findBestInteractable();

        for (int i = 0; i < 200; i++){
            int health = MyPlayer.getCurrentHealth();
            if (health == 0)
                break;

            if (wine.map(w-> w.interact("Take")).orElse(false)) {
                if (i == 0) //first click
                    Waiting.waitUntil(2000, 0, ()->
                        MyPlayer.getCurrentHealth() < health);
                else
                    Waiting.waitNormal(75, 15);
            }

        }
    }

    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        Optional<InventoryItem> item = Query.inventory()
                .nameContains("bones").isNotNoted().findFirst();
        return Vars.get().currentTask != null &&
                Vars.get().currentTask.equals(SkillTasks.PRAYER) &&
                Vars.get().useWildernessAltar &&
                Combat.isInWilderness() && Inventory.isEmpty();
    }

    @Override
    public void execute() {
        suicideWine();
    }

    @Override
    public String toString() {
        return "Suiciding";
    }

    @Override
    public String taskName() {
        return "Prayer - Wildneress";
    }
}
