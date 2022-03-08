package scripts.Tasks;


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
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Waiting;
import scripts.AntiBan;
import scripts.Data.Const;
import scripts.Data.Vars;
import scripts.EatUtil;
import scripts.ItemID;
import scripts.Utils;

import java.util.LinkedList;

public class Fight implements Task {

    /**
     * Methods - Crabs (from Naton)
     */


    public static RSNPC[] getAllCrabs() {
        return NPCs.find(Const.get().SAND_CRAB_IDS);
    }

    public static RSNPC[] getAllRocks() {
        return NPCs.find(Const.get().SAND_ROCKS_IDS);
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
        RSArea area = new RSArea(Vars.get().crabTile, 2);
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
                            Vars.get().shouldResetAggro = true;
                            return true;
                        }
            }
        }
        //General.println("[Debug]: Check aggro returning false");
        return false;

    }


    public boolean waitCond(Boolean abc2Actions, boolean mouseOffScreen) {
        return Timing.waitCondition(() -> {
            General.sleep(1100, 2500);
            if (abc2Actions)
                AntiBan.timedActions();

            if (mouseOffScreen && Mouse.isInBounds())
                Mouse.leaveGame(true);


            if (Prayer.isPrayerEnabled(Prayer.PRAYERS.HAWK_EYE) && EatDrink.checkPrayer()){
                Utils.idleAfkAction();
                Utils.drinkPotion(ItemID.PRAYER_POTION);
            }

            return (checkAggro() && !Player.getRSPlayer().isInCombat())
                    || Combat.getHPRatio() < Vars.get().eatAt;
        }, General.random(300000, 460000));
    }

    /**
     * Methods - Main handling of crabs
     */

    public void afk(RSTile crabTile) {
        if (!crabTile.equals(Player.getPosition())) {
            Vars.get().task = "Moving to crab tile";
            General.println("[Fight]: Need to move to crab tile");
            Vars.get().shouldMoveToCrabTile = true;
            return;
        }
        if (Combat.isUnderAttack() || Combat.getAttackingEntities().length > 0) {

            if (Vars.get().usingPrayer) {
                if (!Prayer.isPrayerEnabled(Prayer.PRAYERS.HAWK_EYE))
                    Prayer.enable(Prayer.PRAYERS.HAWK_EYE);
            }

            General.println("[Fight]: AFK'ing");
            int chance = General.random(0, 100);

            Vars.get().currentTime = System.currentTimeMillis();

            if (chance < 8) {
                Vars.get().task = "AFK [w/ ABC2 Actions]";
                waitCond(true, true);
                if (Combat.getHPRatio() < Vars.get().eatAt)
                    EatUtil.eatFood();

            } else {
                AntiBan.timedActions();
                Vars.get().task = "AFK";
                waitCond(false, true);
                if (Combat.isUnderAttack() && Combat.getHPRatio() > Vars.get().eatAt)
                    return; // still in combat so we skip abc2 sleep
            }
            if (Combat.getHPRatio() < Vars.get().eatAt) {
                Utils.shortSleep(); // short sleep so we don't immediately eat
                Vars.get().shouldEat = true;
            } else if (chance < 60) {
                Utils.idleAfkAction();

            } else {
                // Utils.idleAfkAction();
                Utils.abc2ReactionSleep(Vars.get().currentTime);
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
        return (Vars.get().crabTile.equals(Player.getPosition()) &&
                (Combat.isUnderAttack() || Combat.getAttackingEntities().length > 0));
    }

    @Override
    public void execute() {
        afk(Vars.get().crabTile);
    }

}

