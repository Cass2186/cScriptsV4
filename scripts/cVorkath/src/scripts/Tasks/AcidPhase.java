package scripts.Tasks;

import obf.Ti;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSProjectile;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.Log;
import scripts.EatUtil;
import scripts.EntitySelector.Entities;
import scripts.EntitySelector.finders.prefabs.ObjectEntity;
import scripts.VorkUtils.Vars;
import scripts.VorkUtils.VorkthUtil;

import java.util.Optional;

public class AcidPhase implements Task {

    public boolean isOnRightSide() {
        RSNPC[] vork = NPCs.findNearest("Vorkath");
        if (vork.length == 0) return false;

        int myX = Player.getPosition().getX();
        int vorkX = vork[0].getPosition().getX();

            /* if vork's x coordinate is greater than mine...
             we are to his left, and should move right (otherwise move left). */
        boolean moveRight = vorkX >= myX;
        if (moveRight) {
            Log.log("[Debug]: Moving right");
            return false;

        } else {
            Log.log("[Debug]: Moving Left");
            return true;
        }
    }
    public static boolean isAtYCoord(RSTile centretile){
        int y = centretile.getY();
        return Player.getPosition().getY() == y;
    }


    public void handleAcidPhase() {
        //toggle run off
        Options.setRunEnabled(false);

        RSProjectile[] acid = Projectiles.getAll(p ->
                p.getGraphicID() == VorkthUtil.ACID_SPRAY);
        Optional<RSTile> eTile = VorkthUtil.getEasternAcidTile();
        Optional<RSTile> wTile = VorkthUtil.getWesternAcidTile();
        Optional<RSTile> centre = VorkthUtil.getFightCenterTile();
        /*  if (acid.length > 0 &&*/
        if (eTile.isPresent() && wTile.isPresent() && centre.isPresent()) {
            RSTile topCentre = centre.get().translate(0,-2);
            for (int i = 0; i < 20; i++) {
                Log.log("i = " + i);

                if (i == 0 && VorkthUtil.walkToTile(topCentre, false)) {
                    if (centre.get().equals(Player.getPosition()))
                    Timing.waitCondition(()-> topCentre.distanceToDouble(Player.getPosition()) < 2, 1250);
                else
                        Timing.waitCondition(()-> topCentre.distanceToDouble(Player.getPosition()) < 2.2, 1250);

                }
                if (isOnRightSide() &&
                        VorkthUtil.walkToTile(wTile.get(), false)) {
                    General.sleep(700, 1100);
                    VorkthUtil.waitCond(() -> (Player.isMoving() &&
                                    // (wWalkTriggerTile.equals(Player.getPosition()) ||
                                    (       wTile.get().distanceTo(Player.getPosition()) <= 3)) ||
                                    Entities.find(ObjectEntity::new)
                                            .nameContains("Acid pool").getResults().length == 0,
                            eTile.get(), 4000);

                }
                if (i >=3 &&   Entities.find(ObjectEntity::new)
                        .nameContains("Acid pool").getResults().length == 0) {
                    VorkthUtil.clickVorkath("Attack");
                    break;
                }

                if (!isOnRightSide() && VorkthUtil.walkToTile(eTile.get(), false)) {
                    General.sleep(700, 1100);
                    VorkthUtil.waitCond(() -> (Player.isMoving() &&
                                    // (eWalkTriggerTile.equals(Player.getPosition()) ||
                                    (  eTile.get().distanceTo(Player.getPosition()) <= 3)) ||
                                    Entities.find(ObjectEntity::new)
                                            .nameContains("Acid pool").getResults().length == 0,
                            wTile.get(), 4000);
                }


                if (i >=3 && Entities.find(ObjectEntity::new) //if the i > 3 isn't there it breaks early
                        .nameContains("Acid pool").getResults().length == 0) {
                    Log.log("[Debug]: Attacking acid phase break");
                    VorkthUtil.clickVorkath("Attack");
                    break;
                }
            }
        }
        if (Combat.getHP() <= Vars.get().eatAtHP)
            EatUtil.eatFood(false);

    }


    @Override
    public String toString() {
        return "Acid attack";
    }


    @Override
    public Priority priority() {
        return Priority.HIGH;
    }

    @Override
    public boolean validate() {
        return Projectiles.getAll(p ->
                p.getGraphicID() == VorkthUtil.ACID_SPRAY).length > 0 ||
                Entities.find(ObjectEntity::new) //if the i > 3 isn't there it breaks early
                        .nameContains("Acid pool").getResults().length > 0;
    }

    @Override
    public void execute() {
        handleAcidPhase();
    }
}
