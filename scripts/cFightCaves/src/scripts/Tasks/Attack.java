package scripts.Tasks;

import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.types.*;
import org.tribot.api2007.util.DPathNavigator;
import org.tribot.script.sdk.Combat;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.MyPlayer;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.interfaces.Character;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.LocalTile;
import org.tribot.script.sdk.types.Npc;
import org.tribot.script.sdk.types.Projectile;
import scripts.*;
import scripts.Data.CaveNPCs;
import scripts.Data.Wave;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Attack {
    public static ArrayList<LocalTile> safespots = new ArrayList<>();

   // SafespotFinder safespotFinder = new SafespotFinder();
    DPathNavigator walker = new DPathNavigator();
    Timer timer = new Timer(12000);

    public void step() throws InterruptedException, IOException {
        //Core.setStatus(this.toString());
        /*if (!isPendingCompletion() || safeSpotEvent.isPendingCompletion()) {
            setComplete();
        }
        if (!isOrbEnabled() && !isSpecialAttackEnabled() && getPercent() > General.random(40, 99)) {
            useSpecialAttack(false);
        }
        setAutoRetaliate();
        if (!safeSpotEvent.isPendingCompletion()) {
            attackNPC();
        } else {
            setComplete();
        }*/
        General.sleep(20, 40);
    }

    @Override
    public String toString() {
        return "Attacking";
    }


    public boolean isPendingCompletion() {
        return !Query.npcs().isMyPlayerInteractingWith().isAny()
                || (isBeingRanged() || isBeingMaged())
//                && Arrays.stream(NPCs.getAll()).filter(i -> Player07.distanceTo(i) < 15).toArray().length > 0;
                && Arrays.stream(NPCs.getAll()).filter(i -> Player.getPosition().distanceTo(i) < 16).toArray().length > 0;
    }

    public int nearbyNpcs() {
        return NPCs.getAll().length;
    }

    boolean setAutoRetaliate() {
        if (CaveNPCs.shouldLure(Wave.getCurrentWave())) {
            if (Combat.isAutoRetaliateOn()) {
                if (Combat.setAutoRetaliate(false)) {
                    return !Combat.isAutoRetaliateOn();
                }
            }
        } else {
            if (!Combat.isAutoRetaliateOn()) {
                if (Combat.setAutoRetaliate(true)) {
                    return Combat.isAutoRetaliateOn();
                }
            }
        }
        return false;
    }

    boolean isOrbEnabled() {
        return Utils.getVarBitValue(Varbits.PVP_SPEC_ORB.getId()) == 0;
    }

    int getPercent() {
       return Combat.getSpecialAttackPercent();
    }

    boolean useSpecialAttack(boolean wait) {
        final int currentSpecLevel = wait ? getPercent() : 0;
        if (Combat.activateSpecialAttack(true)) {
            return Waiting.waitUntil(1500, 50,
                    () -> !wait || getPercent() < currentSpecLevel);
        }
        return false;
    }


    boolean shouldMoveToRangers() {
        RSNPC[] range = NPCs.findNearest(CaveNPCs.XIL.getName());
        Optional<Npc> interacting = Query.npcs().isMyPlayerInteractingWith().findClosest();
        if (isBeingRanged() && (range.length == 0 || interacting != null && !interacting.equals(range[0]))) {
            Log.info("Is being ranged" + isBeingRanged());
            Log.info("Ranger length" + range.length);
        }
        return isBeingRanged() && (range.length == 0 || interacting != null && !interacting.equals(range[0]));
    }

    boolean shouldMoveToMagers() {
        RSNPC[] mage = NPCs.findNearest(CaveNPCs.ZEK.getName());
        Optional<Npc> interacting = Query.npcs().isMyPlayerInteractingWith().findClosest();
        if (isBeingMaged() && (mage.length == 0 || interacting != null && !interacting.equals(mage[0]))) {
            Log.info("We should move to mage");
        }
        return isBeingMaged() && (mage.length == 0 || interacting != null && !interacting.equals(mage[0]));
    }

    boolean moveToMagers() {
        if ((!shouldMoveToMagers() || isBeingRanged()) && NPCs.getAll().length > 0) {
            Log.info("Should move to mages " + !shouldMoveToMagers());
            Log.info("Should move to ranges " + !shouldMoveToRangers());
            Log.info("NPC Length > 0 " + (NPCs.getAll().length > 0));
            return false;
        }
        LocalTile projectileTile = mageProjectileTile();
        walker.setAcceptAdjacent(true);
        if (projectileTile != null) {
            Log.info("Projectile tile is not null");
            Log.info("Projectile tile: " + projectileTile.toString());
            if (PathingUtil.localNav(projectileTile)) {
                Waiting.waitUntil(() -> !shouldMoveToMagers());
            }
            return !shouldMoveToMagers();
        }
        return false;
    }

    boolean moveToRangers() {
        if (!shouldMoveToRangers()) {
            Log.info("We should not move to rangers");
            return false;
        }
        LocalTile projectileTile = rangeProjectileTile();
        walker.setAcceptAdjacent(true);
        if (projectileTile != null) {
            Log.info("We are attempting to move to rangers");
            Log.info("Projectile tile is not null");
            Log.info("Projectile tile: " + projectileTile.toString());
            if (PathingUtil.localNav(projectileTile)) {
                Waiting.waitUntil(() -> !shouldMoveToRangers());
            }
            return !shouldMoveToRangers();
        }
        return false;
    }

    static LocalTile rangeProjectileTile() {
        List<Projectile> projectiles = Query.projectiles().toList();
        for (Projectile projectile : projectiles) {
            if (projectile.isTargetingMe()) {
                for (CaveNPCs npc : CaveNPCs.values()) {
                    if (npc.getProjectileAnimation() ==
                            projectile.getGraphicId() && npc.isShouldPrayRange()) {
                        return projectile.getTile().translate(General.random(0, 5), General.random(0, 5)).toLocalTile();
                    }
                }
            }
        }
        return null;
    }

    static LocalTile mageProjectileTile() {
        List<Projectile> projectiles = Query.projectiles().toList();
        for (Projectile projectile : projectiles) {
            if (projectile.isTargetingMe()) {
                for (CaveNPCs npc : CaveNPCs.values()) {
                    if (npc.getProjectileAnimation() == projectile.getGraphicId() && npc.isShouldPrayMage()) {
                        return projectile.getTile().translate(General.random(0, 5), General.random(0, 5)).toLocalTile();
                    }
                }
            }
        }
        return null;
    }

    static boolean isBeingRanged() {
        if (!CaveNPCs.shouldProtectRange(Wave.getCurrentWave())) {
            return false;
        }
        RSProjectile[] projectiles = Projectiles.getAll();
        if (projectiles.length > 0) {
            for (RSProjectile projectile : projectiles) {
                if (projectile.isTargetingMe()) {
                    for (CaveNPCs npc : CaveNPCs.values()) {
                        if (npc.getProjectileAnimation() == projectile.getGraphicID() && npc.isShouldPrayRange()) {
                            Log.info("Ranged Projectile Coming from " + npc.getName());
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    static boolean isBeingMaged() {
        LocalTile tile = CaveNPCs.getTileOfDangerousNPC(Wave.getCurrentWave());
        if (!CaveNPCs.shouldProtectMage(Wave.getCurrentWave()) || isBeingRanged() ||
                (tile != null && MyPlayer.getTile().distanceTo(tile) < 4)) {
            return false;
        }
        RSProjectile[] projectiles = Projectiles.getAll();
        if (projectiles.length > 0) {
            for (RSProjectile projectile : projectiles) {
                if (projectile.isTargetingMe()) {
                    for (CaveNPCs npc : CaveNPCs.values()) {
                        if (npc.getProjectileAnimation() == projectile.getGraphicID() && npc.isShouldPrayMage()) {
                            Log.info("Mage Projectile Coming from " + npc.getName());
                            return true;
                        }
                    }
                }
            }
        }
        return false;

    }

    public void attackNPC() {
        RSNPC highestPriority = getHighestPriorityNearbyNPC();
        RSPlayer me = Player.getRSPlayer();
//        if (highestPriority != null && (((highestPriority.getPosition().getY() - Game.getBaseY() <= 64 || highestPriority.getPosition().getX() - Game.getBaseX() <= 64) || Player07.distanceTo(highestPriority.getPosition()) < General.random(7, 16)))) {
        if (highestPriority != null) {
            String[] actions = highestPriority.getActions();
            String name = highestPriority.getName();
            String characterName = null;
            RSCharacter character = me.getInteractingCharacter();
            if (character != null) {
                Log.info("Character: " + character.getName());
                characterName = character.getName();
            }
            if (character == null) {
               // if (timer.getStartTime() == 0)
                  //  timer.start();

            } else {
                timer.reset();
            }
            if (!timer.isRunning()) {
                highestPriority.adjustCameraTo();
                timer.setEndIn(General.randomSD(10000, 2500));
                timer.reset();
            }
            if (name != null
                    && actions.length > 0
                    && (character == null
                    || !character.getName().equals(name))
                    && highestPriority.getHealthPercent() > 0) {
//                if (!CaveNPCs.underAttack(Wave.getCurrentWave())) {
//                    Antiban.generateTrackers(Antiban.getReactionTime());
//                    Waiting.waitUntil(() -> CaveNPCs.underAttack(Wave.getCurrentWave()), Reactions.getNormal());
//                }
//                Log.info("Base X", Game.getBaseX());
//                Log.info("Base Y", Game.getBaseY());
//                Log.info("Difference X", highestPriority.getPosition().getX() - Game.getBaseX());
//                Log.info("Difference Y", highestPriority.getPosition().getY() - Game.getBaseY());
//                Log.info("Distance", Player07.distanceTo(highestPriority.getPosition()));
//                Log.info("Base Distance", highestPriority.getPosition().distanceTo(new LocalTile(Game.getBaseX(), Game.getBaseY(), 0)));
//                Log.info("NPC is Clickable", highestPriority.isClickable());
//                Log.info("NPC is on screen", highestPriority.isOnScreen());
//                Log.info("NPC is valid", highestPriority.isValid());
                if (MyPlayer.isHealthBarVisible() && Player.getPosition().distanceTo(highestPriority.getPosition()) < General.random(7, 17)) {
                    if (Utils.clickNPC(highestPriority.getName(), actions[0] + " " + highestPriority.getName())) {
                        Waiting.waitUntil(highestPriority::isInCombat);
                    }
                } else if ((character == null || !characterName.equals(name))
                        && Utils.clickNPC(highestPriority.getName(), actions[0] + " " + highestPriority.getName())) {
                    Waiting.waitUntil(highestPriority::isInCombat);
                }
            }
            if (highestPriority.isInteractingWithMe() && highestPriority.isInCombat()) {
                AntiBan.timedActions();
//                Log.info("We are interacting with the highest priority NPC (" + highestPriority.getName() + ")");
            }
        }
    }

    static RSNPC getHighestPriorityNearbyNPC() {
//        RSNPC[] npcs = Arrays.stream(NPCs.getAll()).filter(i -> Player07.distanceTo(i) <= 17).toArray(RSNPC[]::new);
        RSNPC[] npcs = NPCs.getAll();
        int priority = 0;
        RSNPC temp = null;
        if (npcs.length > 0) {
            for (RSNPC npc : npcs) {
                String name = npc.getName();
                if (name != null) {
                    for (CaveNPCs caveNPC : CaveNPCs.values()) {
                        if (!name.equals(caveNPC.getName())) {
                            continue;
                        }
                        if (caveNPC.getPriority() > priority || temp == null) {
                            Log.info("Priority: " + caveNPC);
                            temp = npc;
                            Log.info("Current highest priority: " + temp.getName());
                            priority = caveNPC.getPriority();
                        }
                    }
                }
            }
        }
        return temp;
    }

  /*  boolean moveBehind() {
        LocalTile destination = null;

        Optional<Character.WalkingDirection> direction =
                MyPlayer.get().get().getWalkingDirection();
        if (direction.isEmpty())
            return false;

        switch (direction.get()) {

            case WEST:, EAST -> {
                //0 - 180 degrees
                destination = MappingEvent.getTileInDirection(General.random(0, 180), General.random(2, 5));
            }
            case NW -> {
                //50 - 220 degrees
                destination = MappingEvent.getTileInDirection(General.random(50, 220), General.random(2, 5));
            }
            case NE, SW -> {
                //310 - 130 degrees
                destination = MappingEvent.getTileInDirection(General.random(130, 310), General.random(2, 5));
            }
            case S, N -> {
                //90-270 degrees
                destination = MappingEvent.getTileInDirection(General.random(90, 270), General.random(2, 5));
            }
            case SE -> {
                //45 - 230 degrees
                destination = MappingEvent.getTileInDirection(General.random(45, 230), General.random(2, 5));
            }
        }
        return destination.click();
    }*/


}
