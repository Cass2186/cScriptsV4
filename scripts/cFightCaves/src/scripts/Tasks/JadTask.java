package scripts.Tasks;

import net.sourceforge.jdistlib.T;
import org.tribot.api.input.Mouse;
import org.tribot.script.sdk.*;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.Npc;
import org.tribot.script.sdk.util.TribotRandom;
import scripts.Data.Const;
import scripts.ItemID;
import scripts.Utils;

import java.util.Optional;

public class JadTask implements Task {

    @Override
    public String toString() {
        return "TzTok-Jad Task";
    }

    @Override
    public Priority priority() {
        return Priority.HIGH;
    }

    @Override
    public boolean validate() {
        return Query.npcs().nameContains("TzTok-Jad").isAny();
    }

    @Override
    public void execute() {
        Mouse.setSpeed(250);
        hanldePrayer();
        if (attackHealer()) Log.info("Attacked Healer");
        else if (attackJad()) Log.info("Attacked Jad");
        else if (boostRanged())  Log.info("Boosted Range");;

    }


    private boolean boostRanged() {
        int lvl = Skill.RANGED.getActualLevel();
        int current = Skill.RANGED.getCurrentLevel();
        int boostAt = (int) (lvl + (Utils.getLevelBoost(Skill.RANGED) / 2));
        if (current <= boostAt) {
            return Query.inventory().nameContains("Ranging potion").isAny() &&
                    Utils.drinkPotion(ItemID.RANGING_POTION) &&
                    Waiting.waitUntil(TribotRandom.uniform(900, 1250), 15,
                            () -> Skill.RANGED.getCurrentLevel() > current);
        }
        return false;
    }

    private static Optional<Npc> getJad() {
        return Query.npcs().nameContains("TzTok-Jad")
                .findClosestByPathDistance();
    }

    private boolean attackHealer() {
        var jad = getJad();
        if (jad.isEmpty())
            return false;

        var healer = Query.npcs()
                .nameContains("yt-hurkot")
                .isInteractingWith(jad.get())
                .findClosestByPathDistance();

        return healer.map(h ->
                h.interact("Attack")).orElse(false) &&
                Waiting.waitUntil(3500, 25,
                        () -> healer.map(h -> h.isHealthBarVisible()).orElse(false) ||
                                needToChangePrayer());
    }

    private boolean attackJad() {
        var jad = Query.npcs().nameContains("TzTok-Jad")
                .isMyPlayerNotInteractingWith()
                .findClosestByPathDistance();

        return jad.map(h -> h.interact("Attack"))
                .orElse(false) &&
                Waiting.waitUntil(3500, 25,
                        () -> jad.map(h -> h.isHealthBarVisible()).orElse(false) ||
                                needToChangePrayer());
    }

    public static boolean needToChangePrayer() {
        var jad = getJad();
        if (jad.map(j -> j.getAnimation() == Const.TZTOK_JAD_MAGIC_ATTACK).orElse(false) &&
                !Prayer.getActivePrayers().contains(Prayer.PROTECT_FROM_MAGIC)) {
            return true;
        } else return jad.map(j -> j.getAnimation() == Const.TZTOK_JAD_RANGE_ATTACK).orElse(false) &&
                !Prayer.getActivePrayers().contains(Prayer.PROTECT_FROM_MISSILES);
    }

    public void hanldePrayer() {
        GameTab.setSwitchPreference(GameTab.SwitchPreference.KEYS);
        var jad = getJad();
        if (jad.map(j -> j.getAnimation() == Const.TZTOK_JAD_MAGIC_ATTACK).orElse(false) &&
                !Prayer.getActivePrayers().contains(Prayer.PROTECT_FROM_MAGIC)) {
            Log.warn("Need to switch for Magic Attack");
            Prayer.enableAll(Prayer.PROTECT_FROM_MAGIC);
        } else if (jad.map(j -> j.getAnimation() == Const.TZTOK_JAD_RANGE_ATTACK).orElse(false) &&
                !Prayer.getActivePrayers().contains(Prayer.PROTECT_FROM_MISSILES)) {
            Log.warn("Need to switch for Ranged Attack");
            Prayer.enableAll(Prayer.PROTECT_FROM_MISSILES);
        } else {

        }
    }
}
