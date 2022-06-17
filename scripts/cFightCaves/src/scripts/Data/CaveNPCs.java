package scripts.Data;

import lombok.Getter;
import org.tribot.api.General;
import org.tribot.script.sdk.interfaces.Positionable;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.LocalTile;
import org.tribot.script.sdk.types.Npc;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.types.Projectile;

import java.util.*;

public enum CaveNPCs {
    KIH("Tz-Kih", 0, 0, false, false, false, 5, 1, 2, 4, 5, 8, 9, 11, 12, 16, 17, 19, 20, 23, 24, 26, 27, 32, 33, 35, 36, 39, 40, 42, 43, 47, 48, 50, 51, 54, 55, 57, 58),
    KEK("Tz-Kek", 0, 0, false, false, false, 1, 3, 4, 5, 6, 10, 11, 12, 13, 18, 19, 20, 21, 25, 26, 27, 28, 34, 35, 36, 37, 41, 42, 43, 44, 49, 50, 51, 52, 56, 57, 58, 59),
    KEK_2("Tz-Kek", 0, 0, false, false, false, 1, 3, 4, 5, 6, 10, 11, 12, 13, 18, 19, 20, 21, 25, 26, 27, 28, 34, 35, 36, 37, 41, 42, 43, 44, 49, 50, 51, 52, 56, 57, 58, 59),
    MEJKOT("Yt-MejKot", 2637, 0, false, false, true, 3, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61),
    XIL("Tok-Xil", 0, 443, false, true, false, 4, 7, 8, 9, 10, 11, 12, 13, 14, 22, 23, 24, 25, 26, 27, 28, 29, 38, 39, 40, 41, 42, 43, 44, 45, 53, 54, 55, 56, 57, 58, 59, 60),
    ZEK("Ket-Zek", 2644, 445, true, false, true, 2, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62),
    HURKOT("Yt-HurKot", 0, 0, false, false, false, 5, 63),
    JAD("TzTok-Jad", 0, 0, false, false, false, 4, 63);

    private String name;
    private int attackAnimation;
    @Getter
    private boolean shouldPrayMage;
    @Getter
    private boolean shouldPrayRange;
    private boolean shouldLure;
    @Getter
    private int priority;
    @Getter
    private int projectileAnimation;
    @Getter
    private Integer[] waves;

    CaveNPCs(String name, int attackAnimation, int projectileAnimation, boolean shouldPrayMage, boolean shouldPrayRange, boolean shouldLure, int priority, Integer... waves) {
        this.name = name;
        this.attackAnimation = attackAnimation;
        this.projectileAnimation = projectileAnimation;
        this.shouldPrayMage = shouldPrayMage;
        this.shouldPrayRange = shouldPrayRange;
        this.shouldLure = shouldLure;
        this.priority = priority;
        this.waves = waves;
    }

    public String getName() {
        return name;
    }

    public int getAttackAnimation() {
        return attackAnimation;
    }

    public static HashMap<String, Integer> getNPCPriority(int wave) {
        HashMap<String, Integer> npcPriority = new HashMap<>();
        for (CaveNPCs npc : CaveNPCs.values()) {
            if (Arrays.stream(npc.waves).anyMatch(n -> n == wave)) {
                npcPriority.put(npc.name, npc.priority);
            }
        }
        //MapUtil.sortByValue(npcPriority);
        return npcPriority;
    }

    public static boolean shouldProtectMage(int wave) {
        for (CaveNPCs npc : CaveNPCs.values()) {
            if (Arrays.stream(npc.waves).anyMatch(n -> n == wave)) {
                if (npc.shouldPrayMage) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean shouldProtectRange(int wave) {
        for (CaveNPCs npc : CaveNPCs.values()) {
            if (Arrays.stream(npc.waves).anyMatch(n -> n == wave)) {
                if (npc.shouldPrayRange) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean shouldLure(int wave) {
        for (CaveNPCs npc : CaveNPCs.values()) {
            if (Arrays.stream(npc.waves).anyMatch(n -> n == wave)) {
                if (npc.shouldLure) {
                    return true;
                }
            }
        }
        return false;
    }

    public static Positionable[] getTilesOfDangerousNPC(int wave) {
        List<Npc> threats = getDangerousNPCs(wave);
        List<Positionable> tiles = new ArrayList<>();
        if (threats.size() > 0) {
            for (Npc threat : threats) {
                tiles.add(threat.getTile());
            }
            return tiles.toArray(Positionable[]::new);
        }
        return null;
    }

    public static LocalTile getTileOfDangerousNPC(int wave) {
        Npc danger = getDangerousNPC(wave);
        if (danger != null) {
            return danger.getTile().toLocalTile();
        }
        return null;
    }

    public static boolean isDangerousNPCMoving(int wave) {
        List<Npc> danger = getDangerousNPCs(wave);
        for (Npc d : danger) {
            if (d.isMoving()) {
                return true;
            }
        }

        return false;
    }

    public static int getAttackAnimationByName(String name) {
        for (CaveNPCs npc : CaveNPCs.values()) {
            if (npc.getName().equals(name)) {
                return npc.getAttackAnimation();
            }
        }
        return -1;
    }

    public static boolean underAttack(int wave) {
        for (CaveNPCs npc : CaveNPCs.values()) {
            if (Arrays.stream(npc.waves).anyMatch(n -> n == wave)) {
                if (npc.attackAnimation != 0) {
                    List<Npc> dangerousNPCs = Query.npcs().nameContains(npc.name).toList();
                    if (dangerousNPCs.size() > 0) {
                        Log.info("Avoidable Animation: " + (dangerousNPCs.get(0).getAnimation() == npc.attackAnimation));
                        Log.info("NPCs Moving and Distance is < 4: " + (dangerousNPCs.get(0).isMoving() && dangerousNPCs.get(0).distance() < 4));
                        Log.info("NPCs too close to player: " + (dangerousNPCs.get(0).distance() < 2));
                        General.sleep(60);
                        return (dangerousNPCs.get(0).getAnimation() == npc.attackAnimation
                                || (dangerousNPCs.get(0).isMoving() && dangerousNPCs.get(0).distance() < 4)
                                || dangerousNPCs.get(0).distance() < 2);
                    }
                }
            }
        }
        return false;
    }


    public static boolean isDangerous(String name) {
        for (CaveNPCs npc : CaveNPCs.values()) {
            if (npc.getName().equals(name)) {
                if (npc.shouldLure) {
                    return true;
                }
            }
        }
        return false;
    }

    private static List<Npc> getDangerousNPCs(int wave) {
        for (CaveNPCs npc : CaveNPCs.values()) {
            if (Arrays.stream(npc.waves).anyMatch(n -> n == wave)) {
                if (npc.shouldLure) {
                    List<Npc> dangerousNPCs = Query.npcs().nameContains(npc.name).toList();
                    if (dangerousNPCs.size() > 0) {
                        return dangerousNPCs;
                    }
                }
            }
        }
        return List.of();
    }

    private static Npc getDangerousNPC(int wave) {
        for (CaveNPCs npc : CaveNPCs.values()) {
            if (Arrays.stream(npc.waves).anyMatch(n -> n == wave)) {
                if (npc.shouldLure) {
                    Optional<Npc> dangerousNPCs = Query.npcs().nameContains(npc.name).findClosest();
                    if (dangerousNPCs.isPresent()) {
                        return dangerousNPCs.get();
                    }
                }
            }
        }
        return null;
    }

    public static LocalTile getIncomingProjectileTile() {
        if (hasIncomingRangedProjectile()) {
            Optional<Projectile> activeProjectile = getActiveProjectile();
            return activeProjectile.map(a ->
                    a.getTile().translate(General.random(0, 5), General.random(0, 5)).toLocalTile()).orElse(null);
        }
        return null;
    }

    public static boolean hasIncomingRangedProjectile() {
        if (!shouldProtectRange(Wave.getCurrentWave())) {
            return false;
        }
        Optional<Projectile> activeProjectile = getActiveProjectile();
        return activeProjectile.isPresent();
    }

    static Optional<Projectile> getActiveProjectile() {
        List<Projectile> projectiles = Query.projectiles().toList();
        if (projectiles.size() > 0) {
            for (Projectile projectile : projectiles) {
                for (CaveNPCs npc : CaveNPCs.values()) {
                    if (npc.attackAnimation == projectile.getGraphicId() && npc.shouldPrayRange) {
                        return Optional.of(projectile);
                    }
                }
            }
        }
        return Optional.empty();
    }
}
