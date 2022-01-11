package scripts.Tasks.PestControl.PestTasks;

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
import scripts.EntitySelector.Entities;
import scripts.EntitySelector.finders.prefabs.NpcEntity;
import scripts.PathingUtil;
import scripts.Tasks.PestControl.PestUtils.PestUtils;
import scripts.Timer;
import scripts.Utils;

import java.util.Optional;

public class DefendKnight implements Task {



    public RSArea getCenterArea() {
        RSNPC[] knight = Entities.find(NpcEntity::new)
                .nameContains("Void Knight")
                .idEquals(PestUtils.KNIGHT_ID)
                .getResults();

        if (knight.length > 0) {
            return new RSArea(knight[0].getPosition(), 3);
        }
        return null;
    }

    public void recenter() {
        RSArea center = PestUtils.getCenterArea();
        if (!Combat.isUnderAttack() && center != null && !center.contains(Player.getPosition())) {
            Log.log("[Debug]: Moving to center");
            if (PathingUtil.localNavigation(center.getRandomTile()))
                PathingUtil.movementIdle();
        }
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
                Game.isInInstance()  && !PestUtils.ATTACK_PORTALS;
    }

    @Override
    public void execute() {
        PestUtils.getActivityPercent();
        if(!PestUtils.killTargets())
            PestUtils.waitForTarget();

    }

    @Override
    public String taskName() {
        return "Pest Control";
    }
}
