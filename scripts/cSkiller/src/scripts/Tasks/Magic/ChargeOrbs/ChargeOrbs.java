package scripts.Tasks.Magic.ChargeOrbs;

import org.tribot.script.sdk.*;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.GameObject;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.ItemID;

import java.util.Optional;

public class ChargeOrbs implements Task {

    int animationId = 726;

    //Obelisk of Air
    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return Vars.get().currentTask != null && Vars.get().currentTask.equals(SkillTasks.MAGIC) &&
                Vars.get().makeOrbs &&
                GoToAirObilisk.standTile.distance() < 2
                && Inventory.contains(ItemID.COSMIC_RUNE);
    }

    @Override
    public void execute() {
        Log.info("Charge Air Orb");
        Optional<GameObject> obelisk = Query.gameObjects()
                .nameContains("Obelisk of Air")
                .findClosestByPathDistance();
        if (MyPlayer.getAnimation() == animationId) {
            Log.info("Waiting...");
            Waiting.waitUntil(60000, 500, () -> !Inventory.contains(ItemID.COSMIC_RUNE));
        } else if (MyPlayer.getAnimation() != -1 &&
                obelisk.map(o -> Magic.castOn("Charge Air Orb", o)).orElse(false) &&
                Waiting.waitUntil(4000, 50, () -> MyPlayer.getAnimation() != -1)) {
        }
    }

    @Override
    public String toString() {
        return "Charging Orbs";
    }

    @Override
    public String taskName() {
        return "Magic - Orbs";
    }
}
