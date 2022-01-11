package scripts.API;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Projectiles;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSProjectile;
import scripts.Data.Vars;
import scripts.Utils;
import scripts.cCombat;

public class CannonMonitor implements Runnable {

    int b = 0;
    int CANNON_BALL_PROJECTILE_ID = 53;
    public static int cballsLeft = 30;
    public static String BROKEN_CANNON_MESSAGE = "Your cannon has broken";

    public void getProjectiles() {
        RSProjectile[] obj = Projectiles.getAll();
        RSObject[] cannon = Objects.findNearest(15, 6);
        if (Vars.get().fightArea != null)
            cannon = Objects.findNearest(15, Filters.Objects.idEquals(6).
                    and(Filters.Objects.inArea(Vars.get().fightArea)));

        if (cannon.length > 0) {
            RSArea a = Utils.getObjectRSArea(cannon[0]);
            for (RSProjectile i : obj) {
                if (a.contains(i.getPosition()) && i.getGraphicID() == CANNON_BALL_PROJECTILE_ID) { //
                    cballsLeft--;
                    General.sleep(75);//was 500
                    //General.println("[Cannon]: Detected Cannonball " + cballsLeft, Color.RED);
                    Timing.waitCondition(() -> !a.contains(i.getPosition()), 550); //was 1200


                }
            }
        }
    }

    public static void handleCannonMessage(String message) {
        if (message.contains("load the cannon with")) {
            cballsLeft = 30;
            Vars.get().fill_cannon_at = General.random(0, 22);
            General.println("[CannonHandler]: Refilling cannon at: " + Vars.get().fill_cannon_at);
        }
        if (message.contains("cannon is out of ammo")) {
            cballsLeft = 0;
            General.println("[Message listener]: Cannon is out of ammo, Antiban sleep then refilling it.");
            Utils.modSleep();
            //CannonHandler.fireCannon();

        }else if (message.contains(BROKEN_CANNON_MESSAGE)){
           // CannonHandler.repairCannon();
        } else if (message.contains("isn't your cannon")){
            Vars.get().use_cannon = false;
            General.println("[Debug]: This isn't our cannon: Vars.useCannon: " + Vars.get().use_cannon);
        }
    }


    public static boolean pause = false;

    @Override
    public void run() {
        General.println("[CannonMonitor]: Started cannon thread");
        while (cCombat.isRunning.get() && Vars.get().use_cannon) {

            General.sleep(50);
            if (pause) {
                General.sleep(100);

            } else {
                if (!Vars.get().use_cannon) {
                    try {
                        Thread.sleep(15000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    getProjectiles();
                    if (CannonMonitor.cballsLeft <= Vars.get().fill_cannon_at
                            && Vars.get().use_cannon && Vars.get().cannon_location != null) {
                        General.sleep(General.random(800, 7000));
                        General.println("[CannonMonitor]: Thread interupting to refill cannon: " + Vars.get().fill_cannon_at);
                       // if (CannonHandler.clickCannon("Fire")) {
                       //     General.println("[CannonHandler]: Refilling cannon at: " + Vars.get().fill_cannon_at);
                       // }
                    }
                }
            }
        }
    }
}
