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
import org.tribot.script.sdk.query.Query;
import scripts.Utils;
import scripts.VorkUtils.VorkthUtil;

public class ZombifiedSpawn implements Task {


    public void handleZombifiedSpawn() {
        RSProjectile[] whiteFlame = Projectiles.getAll(p ->
                p.getGraphicID() == VorkthUtil.WHITE_SPAWN_FLAME);


        if (whiteFlame.length > 0) {
            Log.info("Spawn pre-attack detected");
            // click our tile to stop attacking
            RSTile myTile = Player.getPosition();
            if (myTile.click())
                General.sleep(General.randomSD(125, 40));

            if (org.tribot.script.sdk.Prayer.isQuickPrayerEnabled())
                org.tribot.script.sdk.Prayer.disableQuickPrayer();

            //wait for spawn projectile
            VorkthUtil.waitCond(() -> Query.projectiles()
                    .graphicIdEquals(VorkthUtil.SPAWN_PROJECTILE).isAny(), 5000);

        }
        RSProjectile[] spawnProjectile = Projectiles.getAll(p ->
                p.getGraphicID() == VorkthUtil.SPAWN_PROJECTILE);

        //optionally disable prayer
        if (spawnProjectile.length > 0) {
            Log.info("Spawn projectile detected");
            RSTile destination = spawnProjectile[0].getDestination();
            // select spell
            if (Magic.selectSpell("Crumble undead")) {
                Log.info("Selected Spell, waiting for spawn");
                VorkthUtil.waitCond(() -> NPCs.findNearest(VorkthUtil.ZOMBIFIED_SPAWN).length > 0, destination, 5000);
            }

            RSNPC[] spawn = NPCs.findNearest(VorkthUtil.ZOMBIFIED_SPAWN);
            if (spawn.length > 0 && Utils.clickNPC(spawn[0], "Cast", true)) {
                Log.info("Clicked Spawn");


                if (VorkthUtil.waitCond(() -> spawn[0].getHealthPercent() == 0 ||
                        NPCs.findNearest(VorkthUtil.ZOMBIFIED_SPAWN).length == 0, 5000)) {

                    // re-enable prayer if needed
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
        return Query.projectiles()
                .graphicIdEquals(VorkthUtil.WHITE_SPAWN_FLAME).isAny() ||
                NPCs.findNearest(VorkthUtil.ZOMBIFIED_SPAWN).length > 0;
    }

    @Override
    public void execute() {
        handleZombifiedSpawn();
    }
}
