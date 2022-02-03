package scripts.Tasks;

import org.tribot.script.sdk.MyPlayer;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.LocalTile;
import org.tribot.script.sdk.types.Npc;
import scripts.Data.Phase.ZulrahPhase;
import scripts.Data.TickObserver;

import java.util.Optional;

public class AttackZulrah implements Task {

    public boolean clickZulrah() {
        Npc zul = TickObserver.zulrah;
        Optional<Npc> notInteractedWith =
                Query.npcs().isNotBeingInteractedWith().nameContains("Zulrah").findBestInteractable();
        if (notInteractedWith.map(z -> z.interact("Attack")).orElse(false)) {
            return true;
        } else if (zul != null && !zul.isHealthBarVisible())
            return zul.interact("Attack");

        return false;
    }

    @Override
    public String toString() {
        return "Attacking Zulrah";
    }

    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return Query.npcs().nameContains("Zulrah")
                .isNotBeingInteractedWith()
                .isHealthBarNotVisible()
                .findBestInteractable().isPresent();
    }

    @Override
    public void execute() {
        clickZulrah();
    }
}
