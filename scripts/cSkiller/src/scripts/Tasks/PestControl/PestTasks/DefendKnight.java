package scripts.Tasks.PestControl.PestTasks;

import dax.walker.utils.camera.DaxCamera;
import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api2007.Combat;
import org.tribot.api2007.Game;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSNPC;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Waiting;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.AntiBan;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.PathingUtil;
import scripts.Tasks.PestControl.PestUtils.PestUtils;
import scripts.Timer;

import java.util.Optional;

public class DefendKnight implements Task {

    public static boolean killTargets() {
        Optional<RSNPC> targ = PestUtils.getTarget();
        if (Combat.isUnderAttack()) {
            Log.log("[Debug]: Waiting to finish combat");
            return Timer.waitCondition(() -> {
                AntiBan.timedActions();
                Waiting.waitNormal(700, 150);
                return !Combat.isUnderAttack();
            }, 3000, 45000);
        } else if (targ.isPresent()) {
            Log.log("[Debug]: Attacking Target");
            if (targ.get().getHealthPercent() == 0)
                Waiting.waitNormal(300, 50);


            if (targ.get().getPosition().distanceTo(Player.getPosition()) > General.random(6, 8)) {
                PathingUtil.localNavigation(targ.get().getPosition().translate(-1, -1));
                Timer.waitCondition(() -> targ.get().isClickable(), 5000, 7000);
            }
            if (!targ.get().isClickable())
                DaxCamera.focus(targ.get());

            if (DynamicClicking.clickRSNPC(targ.get(), "Attack")) {
                return Timer.waitCondition(Combat::isUnderAttack, 3500, 4500);
            }

        }
        return false;
    }

    public boolean recenter() {
        RSArea center = PestUtils.getCenterArea();
        if (center != null && !center.contains(Player.getPosition())) {
            Log.log("[Debug]: Moving to center");
            if (PathingUtil.localNavigation(center.getRandomTile()))
                return Timer.waitCondition(() -> center.contains(Player.getPosition()), 5000, 7000);
        }
        if (center == null) {
            RSNPC knight = PestUtils.getLeaveKnight();
            if (knight != null) {
                Log.log("[Debug]: Moving to translated tile");
                PathingUtil.localNavigation(knight.getPosition().translate(0, -12));
                Waiting.waitNormal(750, 125);
            }
            Log.log("[Debug]: Cannot move to center");
        }
        return center != null && !center.contains(Player.getPosition());
    }


    @Override
    public String toString() {
        return "Defending Knight";
    }


    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        RSNPC[] knight = NPCs.findNearest("Void Knight");
        return Vars.get().currentTask != null &&
                Vars.get().currentTask.equals(SkillTasks.PEST_CONTROL) &&
                Game.isInInstance() && !PestUtils.ATTACK_PORTALS;
    }

    @Override
    public void execute() {
        recenter();
        PestUtils.getActivityPercent();
        if (!PestUtils.killTargets() && recenter()) {
            PestUtils.waitForTarget();
        }

    }

    @Override
    public String taskName() {
        return "Pest Control";
    }
}
