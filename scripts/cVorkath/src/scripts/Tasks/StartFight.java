package scripts.Tasks;

import org.tribot.api2007.Game;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Options;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Prayer;
import org.tribot.script.sdk.Waiting;
import scripts.EntitySelector.Entities;
import scripts.EntitySelector.finders.prefabs.ObjectEntity;
import scripts.PathingUtil;
import scripts.Timer;
import scripts.Utils;
import scripts.VorkUtils.Vars;
import scripts.VorkUtils.VorkthUtil;

import java.util.Arrays;
import java.util.Optional;

public class StartFight implements Task {

    public boolean enterInstance() {
        if (!Game.isInInstance() && VorkthUtil.VORK_ISLAND_AREA.contains(Player.getPosition())) {
            Log.log("[Debug]: Entering Instance");
            Optional<RSObject> p = Optional.ofNullable(Entities.find(ObjectEntity::new)
                    .nameContains("Ice chunks")
                    .actionsContains("Climb-over")
                    .tileEquals(new RSTile(2272, 4053, 0))
                    .getFirstResult());
            if (p.isPresent() && !p.get().isClickable() && PathingUtil.localNavigation(p.get()
                    .getPosition().translate(0, -2))) {
                Timer.waitCondition(() -> p.get().isClickable(), 5000);
                Waiting.waitNormal(1500, 250);
            }
            return p.map(port -> {
                return port.click("Climb-over") &&
                        Timer.waitCondition(() -> Game.isInInstance(), 8000, 10000);
            }).orElse(false);
        }

        return false;
    }

    public void takeBoat() {
        if (!Game.isInInstance() && !VorkthUtil.VORK_ISLAND_AREA.contains(Player.getPosition())) {
            HouseBank.leaveLunarIsle();
            Waiting.waitNormal(1500, 250);
            Log.log("[Debug]: Taking boat");
            PathingUtil.walkToTile(new RSTile(2640, 3697, 0), 2, false);
            if (Utils.clickNPC("Torfinn", "Ungael")) {
                Timer.slowWaitCondition(() -> VorkthUtil.VORK_ISLAND_AREA.contains(Player.getPosition()), 8000,9000);
                Waiting.waitNormal(500,55);
            }
        }

    }

    public boolean checkQuickPrayers() {
        return !Prayer.getSelectedQuickPrayers().containsAll(Arrays.asList(Prayer.PROTECT_FROM_MISSILES,
                Prayer.EAGLE_EYE)) && Prayer.selectQuickPrayers(Prayer.PROTECT_FROM_MISSILES, Prayer.EAGLE_EYE);

    }

    /**
     * pokes voke and walks to fight tile
     *
     * @return true if attacking vork is present
     */
    public boolean startFight() {
        RSNPC[] sleepingVork = NPCs.findNearest(VorkthUtil.SLEEPING_VORK);
        Optional<RSTile> fightTile = VorkthUtil.getFightCenterTile();
        if (!Game.isInInstance() || fightTile.isEmpty()) return false;

        checkQuickPrayers();

        //toggle run off
        Options.setRunEnabled(false);

        //Pokes vorkath
        if (sleepingVork.length > 0 && Vars.get().antiFireTimer.isRunning() &&
                Utils.clickNPC(sleepingVork[0], "Poke", false)) {
            Log.log("[Debug]: Poking Vork");
            VorkthUtil.waitCond(() -> NPCs.findNearest(VorkthUtil.AWAKENING_VORK).length > 0, 6500);
        }

        //walks to fight tile and hovers vork
        RSNPC[] awakeningVork = NPCs.findNearest(VorkthUtil.AWAKENING_VORK);
        if (awakeningVork.length > 0 && VorkthUtil.walkToTile(fightTile.get(), false))
            if (VorkthUtil.waitCond(() -> NPCs.findNearest(VorkthUtil.ATTACKING_VORK).length > 0, 5500))
                return VorkthUtil.clickVorkath("Attack");

        // walk to fight tile

        return NPCs.findNearest(VorkthUtil.ATTACKING_VORK).length > 0 && VorkthUtil.clickVorkath("Attack");
    }


    @Override
    public String toString() {
        return "Starting fight";
    }


    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return NPCs.findNearest(VorkthUtil.SLEEPING_VORK, VorkthUtil.AWAKENING_VORK).length > 0 ||
                (!Game.isInInstance() && Vars.get().bankTask.isSatisfied());
    }

    @Override
    public void execute() {
        takeBoat();
        if (enterInstance()) {
            Waiting.waitNormal(1500, 268);
        }
        startFight();
    }
}
