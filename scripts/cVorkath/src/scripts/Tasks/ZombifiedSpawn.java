package scripts.Tasks;

import org.tribot.api.General;
import org.tribot.api2007.Magic;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Player;
import org.tribot.api2007.Projectiles;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSProjectile;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.Log;
import scripts.Utils;
import scripts.VorkUtils.VorkthUtil;

public class ZombifiedSpawn implements Task {


    public void handleZombifiedSpawn() {
        RSProjectile[] whiteFlame = Projectiles.getAll(p ->
                p.getGraphicID() == VorkthUtil.WHITE_SPAWN_FLAME);


        if (whiteFlame.length > 0) {
            Log.log("[Debug]: Spawn pre-attack detected");
            // click our tile to stop attacking
            RSTile myTile = Player.getPosition();
            if (myTile.click())
                General.sleep(General.randomSD(125, 40));

            if (org.tribot.script.sdk.Prayer.isQuickPrayerEnabled())
                org.tribot.script.sdk.Prayer.disableQuickPrayer();
            //wait for spawn projectile
            VorkthUtil.waitCond(() -> Projectiles.getAll(p ->
                    p.getGraphicID() == VorkthUtil.SPAWN_PROJECTILE).length > 0, 5000);

        }
        RSProjectile[] spawnProjectile = Projectiles.getAll(p ->
                p.getGraphicID() == VorkthUtil.SPAWN_PROJECTILE);
        //optionally disable prayer
        if (spawnProjectile.length > 0) {
            Log.log("[Debug]: Spawn projectile detected");
            RSTile destination = spawnProjectile[0].getDestination();
            // select spell
            if (Magic.selectSpell("Crumble undead")) {
                Log.log("[Debug]: Selected Spell, waiting for spawn");
                VorkthUtil.waitCond(() -> NPCs.findNearest(VorkthUtil.ZOMBIFIED_SPAWN).length > 0, destination, 5000);
            }

            RSNPC[] spawn = NPCs.findNearest(VorkthUtil.ZOMBIFIED_SPAWN);
            //try usign the utils method, if it doens't work just do accurate mouse
            if (spawn.length > 0 && Utils.clickNPC(spawn[0], "Cast", true)) {
                Log.log("[Debug]: Clicked Spawn");
                // re-enable prayer if needed

                if (VorkthUtil.waitCond(() -> spawn[0].getHealthPercent() == 0 ||
                        NPCs.findNearest(VorkthUtil.ZOMBIFIED_SPAWN).length == 0, 5000)) {

                    if (!org.tribot.script.sdk.Prayer.isQuickPrayerEnabled())
                        org.tribot.script.sdk.Prayer.enableQuickPrayer();

                    VorkthUtil.clickVorkath("Attack");
                }

            }
        }

    }

    @Override
    public String toString() {
        return "Zombified Spawn";
    }

    @Override
    public Priority priority() {
        return Priority.HIGH;
    }

    @Override
    public boolean validate() {
        return Projectiles.getAll(p ->
                p.getGraphicID() == VorkthUtil.WHITE_SPAWN_FLAME).length > 0 ||
                NPCs.findNearest(VorkthUtil.ZOMBIFIED_SPAWN).length > 0;
    }

    @Override
    public void execute() {
        handleZombifiedSpawn();
    }
}
