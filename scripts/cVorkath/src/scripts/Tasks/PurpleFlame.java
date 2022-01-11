package scripts.Tasks;

import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Projectiles;
import org.tribot.api2007.types.RSInterface;
import org.tribot.api2007.types.RSProjectile;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Prayer;
import scripts.VorkUtils.VorkthUtil;

public class PurpleFlame  implements Task{

    public void handlePurpleFlame() {
        RSProjectile[] attack = Projectiles.getAll(p -> p.getGraphicID() == VorkthUtil.PRAYER_DISABLING_FLAME);
        if (attack.length > 0) {
            Log.log("[Debug]: Purple prayer-disabling projectile detected");
            // just in case it's off for some reason
            if (!Prayer.isQuickPrayerEnabled())
                Prayer.enableQuickPrayer();

            RSInterface praybutton = Interfaces.findWhereAction("Deactivate", 160);

            if (praybutton != null) {
                if (VorkthUtil.waitCond(() -> !Prayer.isQuickPrayerEnabled(), praybutton, 5000))
                    Prayer.enableQuickPrayer();
            } else if (VorkthUtil.waitCond(() -> !Prayer.isQuickPrayerEnabled(), /*hover button*/ 5000))
                Prayer.enableQuickPrayer();
        }
        VorkthUtil.clickVorkath("Attack");
    }

    @Override
    public String toString(){
        return "Prayer-disabling attack";
    }



    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return Projectiles.getAll(p -> p.getGraphicID() == VorkthUtil.PRAYER_DISABLING_FLAME).length > 0;
    }

    @Override
    public void execute() {
        handlePurpleFlame();
    }
}
