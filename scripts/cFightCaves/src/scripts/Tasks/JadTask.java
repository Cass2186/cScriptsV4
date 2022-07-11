package scripts.Tasks;

import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Prayer;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.Npc;
import scripts.Data.Const;

import java.util.Optional;

public class JadTask implements Task {


    @Override
    public Priority priority() {
        return Priority.HIGH;
    }

    @Override
    public boolean validate() {
        return Query.npcs().nameContains("Jad").isAny();
    }

    @Override
    public void execute() {
        Optional<Npc> jad = Query.npcs().nameContains("Jad").findBestInteractable();

    }

    public void hanldePrayer() {
        Optional<Npc> jad = Query.npcs().nameContains("Jad").findBestInteractable();
        if (jad.map(j -> j.getAnimation() == Const.TZTOK_JAD_MAGIC_ATTACK).orElse(false) &&
                !Prayer.getActivePrayers().contains(Prayer.PROTECT_FROM_MAGIC)) {
            Log.warn("Need to switch for Magic Attack");
            Prayer.enableAll(Prayer.PROTECT_FROM_MAGIC);
        } else if (jad.map(j -> j.getAnimation() == Const.TZTOK_JAD_RANGE_ATTACK).orElse(false) &&
                !Prayer.getActivePrayers().contains(Prayer.PROTECT_FROM_MISSILES)) {
            Log.warn("Need to switch for Ranged Attack");
            Prayer.enableAll(Prayer.PROTECT_FROM_MISSILES);

        }
    }
}
