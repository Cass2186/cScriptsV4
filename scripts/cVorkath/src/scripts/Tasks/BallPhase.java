package scripts.Tasks;

import org.tribot.api2007.NPCs;
import org.tribot.api2007.Player;
import org.tribot.api2007.Projectiles;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSProjectile;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.Log;
import scripts.VorkUtils.VorkthUtil;

public class BallPhase  implements Task{

    public  void handleBallAttack() {
        RSProjectile[] ball = Projectiles.getAll(p ->
                p.getGraphicID() == VorkthUtil.BALL_FLAME);

        if (ball.length > 0) {
            RSNPC[] vork = NPCs.findNearest("Vorkath");
            if (vork.length == 0) return;

            int myX = Player.getPosition().getX();
            int vorkX = vork[0].getPosition().getX();

            /* if vork's x coordinate is greater than mine...
             we are to his left, and should move right (otherwise move left). */
            boolean moveRight = vorkX >= myX;
            if (moveRight) {
                Log.log("[Debug]: Moving right");
                RSTile dest = Player.getPosition().translate(2, 0);
                if (VorkthUtil.walkToTile(dest, false)
                        && VorkthUtil.waitCond(() -> Player.getPosition().equals(dest) ||
                        Projectiles.getAll(p ->
                                p.getGraphicID() == VorkthUtil.BALL_FLAME).length ==0, 6000))
                    VorkthUtil.clickVorkath("Attack");

            } else {
                Log.log("[Debug]: Moving Left");
                RSTile dest = Player.getPosition().translate(-2, 0);
                if (VorkthUtil.walkToTile(dest, false)
                        && VorkthUtil.waitCond(() -> Player.getPosition().equals(dest) ||
                        Projectiles.getAll(p ->
                                p.getGraphicID() == VorkthUtil.BALL_FLAME).length ==0, 6000))
                    VorkthUtil.clickVorkath("Attack");
            }
        }
    }



    @Override
    public String toString(){
        return "Ball Attack";
    }

    @Override
    public Priority priority() {
        return Priority.HIGHEST;
    }

    @Override
    public boolean validate() {
        return  Projectiles.getAll(p ->
                p.getGraphicID() == VorkthUtil.BALL_FLAME).length> 0;
    }

    @Override
    public void execute() {
        handleBallAttack();
    }
}
