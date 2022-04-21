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
import org.tribot.script.sdk.MyPlayer;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.types.Area;
import org.tribot.script.sdk.types.Npc;
import org.tribot.script.sdk.walking.LocalWalking;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.AntiBan;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.PathingUtil;
import scripts.Tasks.PestControl.PestUtils.PestUtils;
import scripts.Timer;
import scripts.Utils;

import java.util.Optional;

public class DefendKnight implements Task {


    public boolean recenter() {
        Area center = PestUtils.getCenterArea();
        if (center != null && !center.contains(MyPlayer.getTile())) {
            Log.info("[Debug]: Moving to center");
            if (PathingUtil.localNav(Area.fromRadius(center.getCenter(), 1).getRandomTile()))
                return Timer.waitCondition(() -> center.contains(MyPlayer.getTile()), 5000, 7000);
        }
        if (center == null) {
            Npc knight = PestUtils.getLeaveKnight();
            if (knight != null) {
                Log.info("[Debug]: Moving to translated tile");
                PathingUtil.localNav(knight.getTile().translate(Utils.random(0, 3), -12));
                Waiting.waitNormal(1250, 215);
            } else
                Log.info("[Debug]: Cannot move to center");
        }
        return center != null && !center.contains(MyPlayer.getTile());
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
            if (PestUtils.waitForTarget())
                Utils.idleNormalAction();
        }

    }

    @Override
    public String taskName() {
        return "Pest Control";
    }
}
