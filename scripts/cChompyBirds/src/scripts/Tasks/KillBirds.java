package scripts.Tasks;


import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.Npc;

import scripts.Data.Const;

import scripts.Utils;

import java.util.Optional;


public class KillBirds implements Task {

    @Override
    public String toString() {
        return "Killing Birds";
    }

    @Override
    public Priority priority() {
        return Priority.HIGH;
    }

    @Override
    public boolean validate() {
        return Query.npcs().idEquals(Const.CHOMPY_ID).toList().size() > 0;
    }

    @Override
    public void execute() {
        Utils.idleNormalAction();
        kill();
    }

    public boolean attackBird(Npc npc) {
        if (!npc.isHealthBarVisible() && npc.interact("Attack"))
            return Waiting.waitUntil(3000, 100, () -> npc.isHealthBarVisible());

        return npc.isHealthBarVisible();
    }

    public void kill() {
        Optional<Npc> birds = Query.npcs().idEquals(Const.CHOMPY_ID).findBestInteractable();
        if (birds.map(this::attackBird).orElse(false)) {
            Waiting.waitUntil( 45000, 500,
                    () -> birds.get().getHealthBarPercent() == 0);
        }
    }


}

