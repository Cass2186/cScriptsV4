package scripts.API;

import org.tribot.api.General;
import org.tribot.api2007.Player;
import org.tribot.script.sdk.query.Query;
import scripts.Data.Areas;
import scripts.Data.Vars;
import scripts.cCombat;
public class PrayerFlickThread extends Thread{
    public static boolean pause = false;
    private static int SCARAB_PRE_ATTACK_ANIMATION = 430;

    @Override
    public void run() {
        General.println("[CannonMonitor]: Started cannon thread");
        while (cCombat.isRunning.get() && Vars.get().killingScarabs) {
            General.sleep(30,50);
            if (pause) {
                General.sleep(100,200);

            } else {
                if (!Areas.LARGE_SCARAB_FIGHT_AREA.contains(Player.getPosition())) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    //do stuff
                }
            }
        }
    }
}
