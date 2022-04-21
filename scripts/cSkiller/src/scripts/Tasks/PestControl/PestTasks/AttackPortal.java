package scripts.Tasks.PestControl.PestTasks;

import org.tribot.api.General;
import org.tribot.api2007.Combat;
import org.tribot.api2007.Game;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSNPC;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.MyPlayer;
import org.tribot.script.sdk.types.Area;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.EntitySelector.Entities;
import scripts.EntitySelector.finders.prefabs.NpcEntity;
import scripts.PathingUtil;
import scripts.Tasks.PestControl.PestUtils.PestUtils;
import scripts.Timer;

import java.util.Optional;

public class AttackPortal implements Task {

    public boolean moveToPortal() {
        Optional<RSNPC> portal = PestUtils.getNearestPortal();
        if (!Combat.isUnderAttack() &&
                portal.isPresent() && portal.get().getPosition().distanceTo(Player.getPosition()) > 6) {
            Log.log("[Debug]: Moving to portal");
            portal.ifPresent(m -> PathingUtil.localNavigation(m.getPosition().translate(2, 2)));
            return true;
        } else {
            if (portal.isEmpty())
                Log.log("[Debug]: Portal Optional is empty");
            return false;
        }
    }

    @Override
    public String toString() {
        return "Attacking Portals";
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
                Game.isInInstance() && PestUtils.ATTACK_PORTALS;
    }

    @Override
    public void execute() {
        if (!moveToPortal()) {
            Area area = PestUtils.getCenterArea();
            RSNPC[] knight = Entities.find(NpcEntity::new)
                    .nameContains("Void Knight")
                    .idEquals(PestUtils.KNIGHT_ID)
                    // .actionsNotContains("Leave")
                    .getResults();
            RSNPC[] sq = Entities.find(NpcEntity::new)
                    .nameContains("Squire")
                    .getResults();
            if (area != null) {
                Log.log("[Debug]: Moving to center");
                PathingUtil.localNav(area.getCenter().translate(0, -13));
                PathingUtil.movementIdle();
            } else if (knight.length > 0) {
                Log.log("[Debug]: Failed to move to center, found knight");
                if (PathingUtil.localNavigation(knight[0].getPosition().translate(0, General.random(-15,-13))))
                    PathingUtil.movementIdle();
            } else if (sq.length >0){
                Log.log("[Debug]: Failed to move to center & find knight, using squire anchor");
                if (PathingUtil.localNavigation(sq[0].getPosition().translate(0, General.random(-15,-13))) &&
                    Timer.waitCondition(()-> MyPlayer.isMoving(), 2500,4000)){
                    Timer.waitCondition(()-> !MyPlayer.isMoving(), 3500,5500);
                }
            }
        }
        if (!PestUtils.killTargets()) {

        }
        //  PestUtils.waitForTarget();
    }

    @Override
    public String taskName() {
        return "Pest Control";
    }
}
