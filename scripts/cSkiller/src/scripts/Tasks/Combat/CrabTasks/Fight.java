package scripts.Tasks.Combat.CrabTasks;


import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.Combat;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Player;
import org.tribot.api2007.Prayer;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSTile;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.AntiBan;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.EatUtil;
import scripts.Tasks.Combat.Data.Const;
import scripts.Tasks.Combat.Data.CrabVars;
import scripts.Utils;
import scripts.cSkiller;


import java.util.LinkedList;

public class Fight implements Task {

    /**
     * Methods - Crabs (from Naton)
     */


    public static RSNPC[] getAllCrabs() {
        return NPCs.find(Const.SAND_CRAB_IDS);
    }

    public static RSNPC[] getAllRocks() {
        return NPCs.find(Const.SAND_ROCKS_IDS);
    }

    /**
     * @return A 9x9 box with coordinates top right (x+3, y+3), and bottom left (x-3, y-3).
     * @TODO make it so it is only 3x3 for sandcrabs and 9x9 for ammonite
     */
    public static RSArea getAggroArea() {
        return new RSArea(Player.getPosition().translate(3, 3), Player.getPosition().translate(-3, -3));
    }

    /**
     * Checks to see if there are any sandy rocks next to the player. Checks one tile away in each direction, if the player has not lost aggression then the rocks should turn into crabs.
     *
     * @return Whether or not there are sandy rocks next to the player.
     */
    public static boolean sandyRocksNearby() {
        RSArea aggroArea = getAggroArea();
        for (RSNPC npc : getAllRocks())
            if (aggroArea.contains(npc))
                return true;
        return false;
    }

    /**
     * Checks to see if there are any sand crabs next to the player. Checks one tile away in each direction.
     *
     * @return Whether or not there are sand crabs next to the player.
     */
    public static boolean sandCrabsNearby() {
        RSArea aggroArea = getAggroArea();
        for (RSNPC npc : getAllCrabs())
            if (aggroArea.contains(npc))
                return true;
        return false;
    }


    /**
     * Finds all the tiles of all sandy rocks nearby.
     *
     * @return All of the tiles of sandy rocks in a 3x3 box around the player.
     */
    public static RSTile[] getSandyRockTilesNearby() {
        LinkedList<RSTile> tiles = new LinkedList<RSTile>();
        RSArea aggroArea = getAggroArea();
        for (RSNPC npc : getAllRocks())
            if (aggroArea.contains(npc))
                tiles.add(npc.getPosition());
        return tiles.toArray(new RSTile[tiles.size()]);
    }

    public static boolean checkAggro() {
        RSArea area = new RSArea(CrabVars.get().crabTile, 2);
        if (sandyRocksNearby() && area.contains(Player.getPosition())) {
            // If there are sandy rocks nearby, wait 1200-1800 ms and check again to make sure the crab wasn't just respawning.
            RSTile[] rockTilesFirstCheck = getSandyRockTilesNearby();
            if (!Timing.waitCondition(() -> !sandyRocksNearby(), General.random(1200, 1800))) {
                /* Make sure the sandy rock has the same tile as before, just in case a crab respawned just as the second check took place.
                 * May not be necessary as the wait condition checks constantly if there are any sandy rocks nearby
                 */
                RSTile[] rockTilesSecondCheck = getSandyRockTilesNearby();
                for (RSTile tile1 : rockTilesFirstCheck)
                    for (RSTile tile2 : rockTilesSecondCheck)
                        if (tile1.getPosition().equals(tile2)) {
                            General.println("[Debug]: Check aggro returning true");
                            CrabVars.get().shouldResetAggro = true;
                            return true;
                        }
            }
        }
        //General.println("[Debug]: Check aggro returning false");
        return false;

    }


    public boolean waitCond(Boolean abc2Actions, boolean mouseOffScreen) {
        boolean condition;
        Timing.waitCondition(() -> {
            General.sleep(1000, 2500);
            if (abc2Actions)
                AntiBan.timedActions();

            if (mouseOffScreen && Mouse.isInBounds())
                Mouse.leaveGame(true);

            return (checkAggro() && !Player.getRSPlayer().isInCombat())
                    || Combat.getHPRatio() < CrabVars.get().eatAt;
        }, General.random(300000, 460000));

        return ((checkAggro() && !Player.getRSPlayer().isInCombat())
                || Combat.getHPRatio() < CrabVars.get().eatAt);
    }

    /**
     * Methods - Main handling of crabs
     */

    public void afk(RSTile crabTile) {
        if (!crabTile.equals(Player.getPosition())) {
            General.println("[Fight]: Need to move to crab tile");
            CrabVars.get().shouldMoveToCrabTile = true;
            return;
        }
        if (Combat.isUnderAttack() || Combat.getAttackingEntities().length > 0) {

            if (CrabVars.get().usingPrayer) {
                if (!Prayer.isPrayerEnabled(Prayer.PRAYERS.EAGLE_EYE))
                    Prayer.enable(Prayer.PRAYERS.EAGLE_EYE);
            }

            General.println("[Fight]: AFK'ing");
            int chance = General.random(0, 100);

            CrabVars.get().currentTime = System.currentTimeMillis();

            if (chance < 30) {
                cSkiller.status = "AFK [w/ ABC2 Actions]";
                waitCond(true, true);
                if (Combat.getHPRatio() < CrabVars.get().eatAt)
                    EatUtil.eatFood();

            } else {
                AntiBan.timedActions();
                cSkiller.status = "AFK";
                waitCond(false, true);
                if (Combat.isUnderAttack() && Combat.getHPRatio() > CrabVars.get().eatAt)
                    return; // still in combat so we skip abc2 sleep
            }
            if (Combat.getHPRatio() < CrabVars.get().eatAt) {
                Utils.shortSleep(); // short sleep so we don't immediately eat
                CrabVars.get().shouldEat = true;
            } else {
                Utils.abc2ReactionSleep(CrabVars.get().currentTime);
                Mouse.pickupMouse();
            }
        }
    }


    @Override
    public String toString() {
        return "Killing Crabs";
    }

    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {

        if (Vars.get().currentTask != null &&
                Vars.get().currentTask.equals(SkillTasks.ATTACK) ||
                Vars.get().currentTask.equals(SkillTasks.STRENGTH) ||
                Vars.get().currentTask.equals(SkillTasks.DEFENCE) ||
                Vars.get().currentTask.equals(SkillTasks.RANGED)) {
            return (CrabVars.get().crabTile.equals(Player.getPosition()) &&
                    (Combat.isUnderAttack() || Combat.getAttackingEntities().length > 0));
        }
        return false;
    }

    @Override
    public void execute() {
        afk(CrabVars.get().crabTile);
    }

    @Override
    public String taskName() {
        return "Combat Training";
    }

}

