package scripts.Tasks;

import org.tribot.api.General;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.types.RSNPC;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.query.Query;
import scripts.VorkUtils.VorkthUtil;

public class AttackVork implements Task {

    private boolean isInteractingWithVorkath() {
        return Query.npcs().idEquals(VorkthUtil.ATTACKING_VORK)
                .isMyPlayerInteractingWith()
                .minHealthBarPercent(0.01).isAny();
    }


    @Override
    public String toString() {
        return "Attacking Vorkath";
    }

    @Override
    public Priority priority() {
        return Priority.LOWEST;
    }

    @Override
    public boolean validate() {
        return !isInteractingWithVorkath();
    }

    @Override
    public void execute() {
        if (VorkthUtil.clickVorkath("Attack"))
            Waiting.waitUntil(600, 20, this::isInteractingWithVorkath);
    }
}