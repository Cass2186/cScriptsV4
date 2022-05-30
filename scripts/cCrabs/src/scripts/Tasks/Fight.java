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
import org.tribot.script.sdk.*;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.Area;
import org.tribot.script.sdk.types.Npc;
import org.tribot.script.sdk.types.WorldTile;
import scripts.API.CrabUtils;
import scripts.AntiBan;
import scripts.Data.Const;
import scripts.Data.Vars;
import scripts.EatUtil;
import scripts.ItemID;
import scripts.Utils;

import java.util.LinkedList;
import java.util.List;

public class Fight implements Task {

    /**
     * Methods - Crabs (from Naton)
     */


    public static List<Npc> getAllCrabs() {
        return Query.npcs().idEquals(Const.SAND_CRAB_IDS).toList();
    }

    public static List<Npc> getAllRocks() {
        return Query.npcs().idEquals(Const.SAND_ROCKS_IDS).toList();

    }

    /**
     * @return A 9x9 box with coordinates top right (x+3, y+3), and bottom left (x-3, y-3).
     * @TODO make it so it is only 3x3 for sandcrabs and 9x9 for ammonite
     */
    public static Area getAggroArea() {
        return Area.fromRectangle(MyPlayer.getTile().translate(1, 1),
                MyPlayer.getTile().translate(-1, -1));
    }

    /**
     * Checks to see if there are any sandy rocks next to the player. Checks one tile away in each direction, if the player has not lost aggression then the rocks should turn into crabs.
     *
     * @return Whether or not there are sandy rocks next to the player.
     */
    public static boolean sandyRocksNearby() {
        Area aggroArea = getAggroArea();
        for (Npc npc : getAllRocks())
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
        Area aggroArea = getAggroArea();
        for (Npc npc : getAllCrabs())
            if (aggroArea.contains(npc))
                return true;
        return false;
    }


    /**
     * Finds all the tiles of all sandy rocks nearby.
     *
     * @return All of the tiles of sandy rocks in a 3x3 box around the player.
     */
    public static WorldTile[] getSandyRockTilesNearby() {
        LinkedList<WorldTile> tiles = new LinkedList<WorldTile>();
        Area aggroArea = getAggroArea();
        for (Npc npc : getAllRocks())
            if (aggroArea.contains(npc))
                tiles.add(npc.getTile());
        return tiles.toArray(new WorldTile[tiles.size()]);
    }

    public static boolean checkAggro() {
        Area area = Area.fromRadius(Utils.getWorldTileFromRSTile(Vars.get().crabTile), 2);
        if (sandyRocksNearby() && area.contains(MyPlayer.getTile())) {
            // If there are sandy rocks nearby, wait 1200-1800 ms and check again to make sure the crab wasn't just respawning.
            WorldTile[] rockTilesFirstCheck = getSandyRockTilesNearby();
            if (!Waiting.waitUntil(1500, 350, () -> !sandyRocksNearby())) {
                /* Make sure the sandy rock has the same tile as before, just in case a crab respawned just as the second check took place.
                 * May not be necessary as the wait condition checks constantly if there are any sandy rocks nearby
                 */
                WorldTile[] rockTilesSecondCheck = getSandyRockTilesNearby();
                for (WorldTile tile1 : rockTilesFirstCheck)
                    for (WorldTile tile2 : rockTilesSecondCheck)
                        if (tile1.getTile().equals(tile2)) {
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


            if (Prayer.isPrayerEnabled(Prayer.PRAYERS.EAGLE_EYE) && EatDrink.checkPrayer()) {
                Utils.idleAfkAction();
                Utils.drinkPotion(ItemID.PRAYER_POTION);
            }

            return (CrabUtils.lostAgro(Utils.getWorldTileFromRSTile(Vars.get().crabTile)) /*checkAggro()*/
                    && !MyPlayer.isHealthBarVisible()) || !Login.isLoggedIn()
                    || Combat.getHPRatio() < Vars.get().eatAt || Vars.get().shouldResetAggro;
        }, General.random(300000, 460000));
    }


    private void handleProgressive() {
        if (Vars.get().progressiveMelee) {
            if (Skill.STRENGTH.getActualLevel() < 58) {
                org.tribot.script.sdk.
                        Combat.setAttackStyle(org.tribot.script.sdk.Combat.AttackStyle.AGGRESSIVE);
            } else if (Skill.ATTACK.getActualLevel() < 60) {
                org.tribot.script.sdk.
                        Combat.setAttackStyle(org.tribot.script.sdk.Combat.AttackStyle.ACCURATE);
            } else if (Skill.STRENGTH.getActualLevel() < 62) {
                org.tribot.script.sdk.
                        Combat.setAttackStyle(org.tribot.script.sdk.Combat.AttackStyle.AGGRESSIVE);
            } else if (Skill.DEFENCE.getActualLevel() < 60) {
                org.tribot.script.sdk.
                        Combat.setAttackStyle(org.tribot.script.sdk.Combat.AttackStyle.DEFENSIVE);
            }
        }
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

        if (MyPlayer.isHealthBarVisible() || Combat.getAttackingEntities().length > 0) {
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
                waitCond(true, true);
                if (MyPlayer.isHealthBarVisible() && Combat.getHPRatio() > Vars.get().eatAt)
                    return; // still in combat so we skip abc2 sleep
            }
            if (Combat.getHPRatio() < Vars.get().eatAt) {
                Utils.shortSleep(); // short sleep so we don't immediately eat
                Vars.get().shouldEat = true;
            } else if (chance < 65) {
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
                (MyPlayer.isHealthBarVisible() || Combat.getAttackingEntities().length > 0));
    }

    @Override
    public void execute() {
        handleProgressive();
        afk(Vars.get().crabTile);
    }

}

