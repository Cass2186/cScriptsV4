package scripts;

import dax.walker.utils.AccurateMouse;
import dax.walker.utils.camera.DaxCamera;
import dax.walker_engine.local_pathfinding.Reachable;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.*;
import org.tribot.api2007.types.*;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.MyPlayer;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.interfaces.Character;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.InventoryItem;
import org.tribot.script.sdk.types.Npc;
import org.tribot.script.sdk.walking.LocalWalking;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Much of this was taken from @Elon
 */
public class CombatUtil {

    public static Reachable reachable = new Reachable();

    public static boolean checkForAgroNpc() {
        RSCharacter interacting = Player.getRSPlayer().getInteractingCharacter();
        int ourLvl = Player.getRSPlayer().getCombatLevel();
        if (interacting != null) {
            String name = interacting.getName();
            RSNPC[] npc = NPCs.findNearest(name);
            if (npc.length > 0 && npc[0].getCombatLevel() * 2 > ourLvl) {          //otherwise won't aggro

                if (Player.getPosition().distanceTo(interacting) < 12 && PathFinding.canReach(interacting.getPosition(), false)) {

                    if (Utils.waitCondtion(() -> Combat.isUnderAttack(), 1000, 2500)) {
                        General.println("[CombatUtil]: We still have aggro");
                        return true;
                    }
                }
            }
        }
        return Combat.isUnderAttack();
    }


    public static boolean isInteractingWith(String name) {
        return Query.npcs().nameContains(name).isMyPlayerInteractingWith().isAny();
    }

    public static boolean checkTarget(RSNPC target, RSArea area) {
        if (target == null || area == null)
            return false;

        if (CombatUtil.isBeingSplashed(target) || !area.contains(target))
            return false;

        return !target.isInCombat() || target.isInteractingWithMe()
                || target.getHealthPercent() > 0; // was formerly ==0
    }

    public static boolean checkTarget(RSNPC target) {
        if (target == null)
            return false;

        if (CombatUtil.isBeingSplashed(target))
            return false;

        return !target.isInCombat() || target.isInteractingWithMe()
                || target.getHealthPercent() > 0; // was formerly ==0
    }

    public static boolean checkTarget(RSNPC[] target) {
        if (target == null || target.length == 0)
            return false;

        for (RSNPC t : target) {
            if (!CombatUtil.isBeingSplashed(t)) {
                return !t.isInCombat() || t.isInteractingWithMe()
                        || t.getHealthPercent() > 0;// was formerly ==0
            }
        }
        return false;
    }

    public static boolean checkTarget(RSNPC[] target, RSArea area) {
        if (target == null || target.length == 0 || area == null)
            return false;


        int i = 0;
        for (RSNPC t : target) {
            i++;
            General.println("[CombatUtil]: Checking target - itteration #" + i);
            if (!CombatUtil.isBeingSplashed(t) && area.contains(t)) {
                return !t.isInCombat() || t.isInteractingWithMe()
                        || t.getHealthPercent() > 0;// was formerly ==0
            }
        }
        return false;
    }


    public static boolean resetAgro(RSTile resetTile, int radius) {
        RSTile currentTile = Player.getPosition();
        RSArea resetArea = new RSArea(resetTile, radius);
        if (PathingUtil.localNavigation(resetArea.getRandomTile())) {
            return PathingUtil.localNavigation(currentTile);
        }
        return false;
    }

    public static boolean clickAttack() {
        if (ChooseOption.select("Attack"))
            return Timer.waitCondition(Combat::isUnderAttack, 3500, 5000);
        return false;
    }

    public static boolean attackTarget(int id) {
        Optional<Npc> npc = Query.npcs().idEquals(id)
                .isNotBeingInteractedWith()
                .findClosestByPathDistance();

        if (Query.npcs().idEquals(id)
                .isMyPlayerInteractingWith().isAny())
            return true;

        if (npc.map(n -> n.interact("Attack")).orElse(false)) {
            return Waiting.waitUntil(4000, 200, () -> npc.map(n -> n.isHealthBarVisible()).orElse(false));
        }
        return false;
    }


    public static boolean clickTarget(RSNPC[] target) {
        if (!CombatUtil.checkTarget(target))
            return false;

        RSCharacter interacting = Player.getRSPlayer().getInteractingCharacter();

        if (interacting != null && checkForAgroNpc())
            return true;

        if (Game.getItemSelectionState() == 1)
            Utils.unselectItem();

        for (RSNPC t : target) {
            if (t.getPosition().distanceTo(Player.getPosition()) > General.random(8, 12))
                if (PathingUtil.walkToArea(PathingUtil.makeLargerArea(t.getPosition()), false))
                    Timer.waitCondition(() -> t.isClickable(), 8000, 15000);


            if (!t.isClickable()) // a double check that we can click
                DaxCamera.focus(t);

            if (AccurateMouse.click(t, "Attack")) {
                long currentTime = System.currentTimeMillis();
                int distance = Player.getPosition().distanceTo(t);
                int sleep_time = General.random((int) ((distance / 3.5) * 1000) + 3000, (int) ((distance / 2.5) * 1000) + 6000);

                return Timer.waitCondition(() -> Combat.isUnderAttack() || t == null, sleep_time, 12000);
            }
        }
        return false;
    }

    public static boolean clickTarget(RSNPC target) {
        if (target == null)
            return false;

        RSCharacter interacting = Player.getRSPlayer().getInteractingCharacter();
        if (interacting != null && checkForAgroNpc())
            return true;

        if (Game.getItemSelectionState() == 1)
            Utils.unselectItem();

        if (target.getPosition().distanceTo(Player.getPosition()) > General.random(8, 12))
            if (PathingUtil.walkToArea(PathingUtil.makeLargerArea(target.getPosition()), false))
                Timer.waitCondition(() -> target.isClickable(), 8000, 15000);


        if (!target.isClickable()) // a double check that we can click
            DaxCamera.focus(target);

        if (AccurateMouse.click(target, "Attack")) {
            long currentTime = System.currentTimeMillis();
            int distance = Player.getPosition().distanceTo(target);
            int sleep_time = General.random((int) ((distance / 3.5) * 1000) + 3000, (int) ((distance / 2.5) * 1000) + 6000);

            return Timer.waitCondition(() -> Combat.isUnderAttack() || !target.isValid(), sleep_time, 10000);
        }
        return false;
    }


    public static boolean isPraying() {
        return Player.getRSPlayer().getPrayerIcon() != -1;
    }

    public static boolean checkSpecialItem(RSNPC target) {
        if (target != null && target.getHealthPercent() < 14) {
            RSItem[] item = Inventory.find(ItemID.SLAYER_SPECIAL_ITEMS);
            if (item.length > 0) {
                General.println("[CombatUtils]: Using Slayer Item on NPC");
                return useSlayerItemOnNPC();
            }
        }
        return false;
    }


    private static boolean useSlayerItemOnNPC(RSNPC npc) {
        RSItem[] item = Inventory.find(ItemID.SLAYER_SPECIAL_ITEMS);
        Optional<InventoryItem> i = Query.inventory().idEquals(ItemID.SLAYER_SPECIAL_ITEMS)
                .findClosestToMouse();
        if (npc != null) {
            Optional<Npc> n = Query.npcs().isInteractingWithMe().stream().findFirst();
            if (!npc.isClickable())
                DaxCamera.focus(npc);
            if (i.isPresent() && n.isPresent() && n.get().getHealthBarPercent() < 0.15) {
                Log.log("Health percent is " + n.get().getHealthBarPercent());
               /* if (Utils.useItemOnNPC(i.get().getId(), npc))
                    return Timing.waitCondition(() -> npc.click(rsMenuNode ->
                            rsMenuNode.getAction().equals("Use")
                                    && rsMenuNode.getTarget().toLowerCase()
                                    .contains(npc.getDefinition().getName().toLowerCase())), 700);

*/
                if (i.get().click()) {
                    Waiting.waitNormal(75, 20);
                    // if (n.get().click("Use "))
                    return Timing.waitCondition(() -> npc.click(rsMenuNode ->
                            rsMenuNode.getAction() != null &&
                                    rsMenuNode.getAction().equals("Use")
                                    && rsMenuNode.getTarget().toLowerCase()
                                    .contains(npc.getDefinition().getName().toLowerCase())), 700);
                }
            }

        }
        Log.log("UseSlayerItemOnNPC is false");
        return false;
    }

    private static boolean useSlayerItemOnNPC() {
        Optional<InventoryItem> i = Query.inventory().idEquals(ItemID.SLAYER_SPECIAL_ITEMS)
                .findClosestToMouse();

        Optional<Npc> n = Query.npcs().isInteractingWithMe().stream().findFirst();
        if (i.isPresent() && n.isPresent() && n.get().getHealthBarPercent() < 0.15) {
            Log.log("[AttackNPC]: Health percent is " + n.get().getHealthBarPercent());
            if (Game.getItemSelectionState() == 0 &&
                    i.get().click())
                Waiting.waitNormal(75, 20);
            if (Game.getItemSelectionState() == 1)
                return Timing.waitCondition(() -> n.get().interact("Use"), 1000);

        }
        Log.log("[AttackNPC]: UseSlayerItemOnNPC is false");
        return false;
    }


    public static boolean waitUntilOutOfCombat(RSNPC name, int eatAt, int longTimeOut) {
        int eatAtHP = eatAt + General.random(1, 10);//true if praying

        int icon = Player.getRSPlayer().getPrayerIcon();

        if (!Timing.waitCondition(() -> {
            General.sleep(General.random(100, 500));

            AntiBan.timedActions();

            if (EatUtil.hpPercent() <= (eatAtHP)) {
                EatUtil.eatFood();
            }
            if (icon != -1 && Prayer.getPrayerPoints() < General.random(7, 27)) {
                General.println("[CombatUtil]: WaitUntilOutOfCombat -> Drinking Prayer potion");
                if (Inventory.find(ItemID.PRAYER_POTION).length == 0) {
                    Log.log("returning false, no prayer pots");
                    return false;
                }
                EatUtil.drinkPotion(ItemID.PRAYER_POTION);

            }
            if (AntiBan.getShouldHover() && Mouse.isInBounds() && name != null) {
                AntiBan.hoverNextNPC(name.getName());
                AntiBan.resetShouldHover();
            }

            if (AntiBan.getShouldOpenMenu() && (Mouse.isInBounds() && (!ChooseOption.isOpen())) && name != null)
                AntiBan.openMenuNextNPC(name.getName());

            RSCharacter target = Combat.getTargetEntity();
            if (target != null)
                return !Combat.isUnderAttack() || !EatUtil.hasFood()
                        || (isPraying() && Prayer.getPrayerPoints() < 5);

            return !Query.npcs().isMyPlayerInteractingWith().isAny() ||
                    !Combat.isUnderAttack() || !EatUtil.hasFood() || Prayer.getPrayerPoints() < 5;
        }, General.random(longTimeOut - 5000, longTimeOut)))
            return false;

        if (icon != -1 && Prayer.getPrayerPoints() < General.random(7, 25)) {
            General.println("[CombatUtil]: WaitUntilOutOfCombat -> Drinking Prayer potion");
            if (Inventory.find(ItemID.PRAYER_POTION).length == 0) {
                Log.log("returning false, no prayer pots");
                return false;
            }
            EatUtil.drinkPotion(ItemID.PRAYER_POTION);

        }
        AntiBan.resetShouldOpenMenu();

        RSCharacter target = Combat.getTargetEntity();
        if (target != null)
            return target.getHealthPercent() == 0 || !Combat.isUnderAttack()
                    || !EatUtil.hasFood() || (isPraying() && Prayer.getPrayerPoints() < 5);
        ;

        if (ChooseOption.isOpen() && !Combat.isUnderAttack() && EatUtil.hasFood()) {
            CombatUtil.clickAttack();
        }
        return !Query.npcs().isMyPlayerInteractingWith().isAny();
    }


    public static boolean waitUntilOutOfCombat(String name, int eatAt) {
        int eatAtHP = eatAt + General.random(1, 10);//true if praying

        Timing.waitCondition(() -> {
            General.sleep(General.random(100, 500));

            AntiBan.timedActions();

            if (EatUtil.hpPercent() <= (eatAtHP)) {
                EatUtil.eatFood();
            }

            if (AntiBan.getShouldHover() && Mouse.isInBounds()) {
                AntiBan.hoverNextNPC(name);
                AntiBan.resetShouldHover();
            }

            if (AntiBan.getShouldOpenMenu() && (Mouse.isInBounds() && (!ChooseOption.isOpen())))
                AntiBan.openMenuNextNPC(name);

            RSCharacter target = Combat.getTargetEntity();
            if (target != null)
                return !Combat.isUnderAttack() || !EatUtil.hasFood()
                        || (isPraying() && Prayer.getPrayerPoints() < 5);

            return !Query.npcs().isMyPlayerInteractingWith().isAny() || !EatUtil.hasFood() || Prayer.getPrayerPoints() < 5;
        }, General.random(20000, 40000));

        AntiBan.resetShouldOpenMenu();

        RSCharacter target = Combat.getTargetEntity();
        if (target != null)
            return target.getHealthPercent() == 0 || !Combat.isUnderAttack()
                    || !EatUtil.hasFood() || (isPraying() && Prayer.getPrayerPoints() < 5);
        ;

        if (ChooseOption.isOpen() && !Combat.isUnderAttack() && EatUtil.hasFood()) {
            CombatUtil.clickAttack();
        }
        return !Query.npcs().isMyPlayerInteractingWith().isAny();
    }

    public static RSNPC getTarget(String[] monsterStrings) {
        for (String s : monsterStrings) {
            RSNPC t = getTarget(s);
            if (t != null)
                return t;
        }
        return null;
    }

   /* public static Npc getTarget(String[] monsterStrings) {
        for (String s : monsterStrings) {
            RSNPC t = getTarget(s);
            if (t != null)
                return t;
        }
        return null;
    }

    /**
     * UNTESTED version using new SDK
     *
     * @param monsterStrings
     * @return

    public static Npc getTarget(String monsterStrings) {
        if (MyPlayer.isHealthBarVisible()) {
            Optional<org.tribot.script.sdk.types.Player> me = MyPlayer.get();
            if (me.isPresent()) {
                Optional<Character> interactingCharacter = me.get().getInteractingCharacter();
                if (interactingCharacter.isPresent())
                    return (Npc) interactingCharacter.get();
            }
        }

        List<Npc> npc = Query.npcs().nameContains(monsterStrings).toList();
        for (Npc t : npc) {
            if (t.getHealthBarPercent() != 0 && !t.isHealthBarVisible()) {
                if (LocalWalking.createMap().canReach(t.getTile())) {
                    if (!t.isVisible() && PathingUtil.localNav(t.getTile()) &&
                        Timer.waitCondition(t::isVisible, 7000, 10000)){
                        return t;
                    }
                } else {
                    // global walk
                }
            }
        }
        return null;
    }*/

    public static RSNPC getTarget(String monsterStrings) {
        if (Combat.isUnderAttack()) {
            RSCharacter target = Combat.getTargetEntity();
            if (target != null && target.getHealthPercent() != 0)
                return (RSNPC) target;
        }


        RSNPC[] potentialTargets = NPCs.findNearest(monsterStrings);
        int i = 0;
        for (RSNPC targ : potentialTargets) {
            i++;
            if (targ.getHealthPercent() == 0)
                continue;
            if (reachable.canReach(targ.getPosition())) {
                //  if (PathFinding.canReach(targ.getPosition(), false)) {
                if (targ.getHealthPercent() != 0 && !targ.isInCombat()) {
                    // General.println("[Fight]: Got a target at index: " + i);
                    return targ;
                }

            } else if (PathingUtil.localNavigation(targ.getPosition())) {
                // else if (PathingUtil.walkToArea(PathingUtil.makeLargerArea(targ.getPosition()), 2, false)) { //will see if we can get a Dax path to walk to
                //if (PathFinding.canReach(targ.getPosition(), false)) {
                if (reachable.canReach(targ.getPosition())) {
                    if (targ.getHealthPercent() != 0 && !targ.isInCombat()) {
                        General.println("[Fight]: Got a target at index: " + i + "(Dax)");
                        return targ;
                    }
                }
            }
        }
        return null;
    }


    public static boolean waitUntilOutOfCombat(int eatAt) {
        int eatAtHP = eatAt + General.random(1, 10);
        int prayerPotAt = General.random(4, 20);
        Timing.waitCondition(() -> {
            General.sleep(General.random(50, 200));
            AntiBan.timedActions();

            if (EatUtil.hpPercent() <= (eatAtHP))
                EatUtil.eatFood();

            if (isPraying() && Prayer.getPrayerPoints() < prayerPotAt) {
                EatUtil.drinkPotion(ItemID.PRAYER_POTION);
            }

            RSCharacter target = Combat.getTargetEntity();
            if (target != null)
                return !Combat.isUnderAttack()
                        || !EatUtil.hasFood() || Prayer.getPrayerPoints() < 5;

            return !Combat.isUnderAttack() || !EatUtil.hasFood() || Prayer.getPrayerPoints() < 5;
        }, General.random(20000, 40000));

        AntiBan.resetShouldOpenMenu();

        RSCharacter target = Combat.getTargetEntity();
        if (target != null)
            return target.getHealthPercent() == 0 || !Combat.isUnderAttack() || !EatUtil.hasFood()
                    || (isPraying() && Prayer.getPrayerPoints() < 5);

        return !Combat.isUnderAttack();
    }

    public static boolean waitUntilOutOfCombat(int eatAt, boolean endIfNoFood) {
        int eatAtHP = eatAt + General.random(1, 10);
        int prayerPotAt = General.random(4, 20);
        Timing.waitCondition(() -> {
            General.sleep(General.random(50, 200));
            AntiBan.timedActions();

            if (EatUtil.hpPercent() <= (eatAtHP))
                EatUtil.eatFood();

            if (isPraying() && Prayer.getPrayerPoints() < prayerPotAt) {
                EatUtil.drinkPotion(ItemID.PRAYER_POTION);
            }

            RSCharacter target = Combat.getTargetEntity();
            if (target != null)
                return !Combat.isUnderAttack()
                        || (endIfNoFood && !EatUtil.hasFood()) || Prayer.getPrayerPoints() < 5;

            return !Combat.isUnderAttack() || (endIfNoFood && !EatUtil.hasFood()) || Prayer.getPrayerPoints() < 5;
        }, General.random(20000, 40000));

        AntiBan.resetShouldOpenMenu();

        RSCharacter target = Combat.getTargetEntity();
        if (target != null)
            return target.getHealthPercent() == 0 || !Combat.isUnderAttack() || !EatUtil.hasFood()
                    || (isPraying() && Prayer.getPrayerPoints() < 5);

        return !Combat.isUnderAttack();
    }

    public static boolean waitUntilOutOfCombat(String name, int eatAt, RSNPC target) {
        int eatAtHP = eatAt + General.random(1, 10);

        Timing.waitCondition(() -> {
            General.sleep(150);

            AntiBan.timedActions();

            if (EatUtil.hpPercent() <= (eatAtHP)) {
                EatUtil.eatFood();
            }

            if (AntiBan.getShouldHover() && Mouse.isInBounds()) {
                AntiBan.hoverNextNPC(name);
                AntiBan.resetShouldHover();
            }

            if (AntiBan.getShouldOpenMenu() && (Mouse.isInBounds() && (!ChooseOption.isOpen()))) {
                AntiBan.openMenuNextNPC(name);
            }
            return !Combat.isUnderAttack() || !EatUtil.hasFood() || target == null
                    || (isPraying() && Prayer.getPrayerPoints() < 5) || target.getHealthPercent() == 0;
        }, General.random(20000, 40000));

        AntiBan.resetShouldOpenMenu();

        if (ChooseOption.isOpen() && !Combat.isUnderAttack() && EatUtil.hasFood()) {
            CombatUtil.clickAttack();
        }
        return !Combat.isUnderAttack() || target == null || target.getHealthPercent() == 0;
    }

    public static boolean isBeingSplashed(RSNPC n) {
        for (RSPlayer p : Players.getAll())
            if (p.getInteractingIndex() == n.getIndex() && p.isInCombat())
                return true;
        return false;
    }

    public static boolean setAutoRetaliate(boolean enable) {
        if (!Combat.isAutoRetaliateOn() && enable)
            return Combat.setAutoRetaliate(true);

        else if (Combat.isAutoRetaliateOn() && !enable)
            return Combat.setAutoRetaliate(false);


        return true;
    }

    // General.random(360000, 540000)

    /**
     * Methods - Crabs (from Naton)
     */
    public final static int[] SAND_CRAB_IDS = {7206, 5935};
    public final static int[] SAND_ROCKS_IDS = {7207, 5936};


    public static RSNPC[] getAllCrabs() {
        return NPCs.find(SAND_CRAB_IDS);
    }


    public static RSNPC[] getAllRocks() {
        return NPCs.find(SAND_ROCKS_IDS);
    }

    /**
     * @return A 3x3 box with coordinates top right (x+1, y+1), and bottom left (x-1, y-1).
     */
    public static RSArea getAggroArea() {
        return new RSArea(Player.getPosition().translate(1, 1), Player.getPosition().translate(-1, -1));
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
        if (sandyRocksNearby()) {
            // If there are sandy rocks nearby, wait 1200-1800 ms and check again to make sure the crab wasn't just respawning.
            RSTile[] rockTilesFirstCheck = getSandyRockTilesNearby();
            if (!Timing.waitCondition(() -> !sandyRocksNearby(), General.random(1200, 1800))) {
                /* Make sure the sandy rock has the same tile as before, just in case a crab respawned just as the second check took place.
                 * May not be necessary as the wait condition checks constantly if there are any sandy rocks nearby
                 */
                RSTile[] rockTilesSecondCheck = getSandyRockTilesNearby();
                for (RSTile tile1 : rockTilesFirstCheck)
                    for (RSTile tile2 : rockTilesSecondCheck)
                        if (tile1.getPosition().equals(tile2))
                            return true;
            }
        }
        return false;
    }


    public static boolean waitUntilOutOfCombatSpecialItem(String name, int eatAt) {
        int eatAtHP = eatAt + General.random(1, 10);

        Timing.waitCondition(() -> {
            General.sleep(100);

            AntiBan.timedActions();

            if (EatUtil.hpPercent() <= (eatAtHP)) {
                EatUtil.eatFood();
            }

            if (AntiBan.getShouldHover() && Mouse.isInBounds()) {
                AntiBan.hoverNextNPC(name);
                AntiBan.resetShouldHover();
            }

            if (AntiBan.getShouldOpenMenu() && (Mouse.isInBounds() && (!ChooseOption.isOpen()))) {
                AntiBan.openMenuNextNPC(name);
            }
            return !Combat.isUnderAttack() || !EatUtil.hasFood();
        }, General.random(20000, 40000));

        AntiBan.resetShouldOpenMenu();

        if (ChooseOption.isOpen() && !Combat.isUnderAttack() && EatUtil.hasFood()) {
            CombatUtil.clickAttack();
        }
        return !Combat.isUnderAttack();
    }


    /**
     * The following several methods are from BEG's API
     */
    private static ArrayList<RSTile> adjacentTiles(RSTile tile) {
        ArrayList<RSTile> tiles = new ArrayList<>();
        for (int x = -1; x < 2; x++) {
            for (int y = -1; y < 2; y++) {
                if (x == 0 && y == 0) {
                    continue;
                }
                RSTile adjacentTile = new RSTile(tile.getX() + x, tile.getY() + y, tile.getPlane());
                tiles.add(adjacentTile);
            }
        }
        return tiles;
    }

    private static ArrayList<RSTile> getIntersectingTiles(ArrayList<RSTile> list1, ArrayList<RSTile> list2) {
        ArrayList<RSTile> intersectingTiles = new ArrayList<>();
        for (RSTile tile1 : list1) {
            boolean pass = false;
            for (RSTile tile2 : list2) {
                if (tile1.distanceTo(tile2) == 0) {
                    pass = true;
                    break;
                }
            }
            if (pass) {
                intersectingTiles.add(tile1);
            }
        }
        return intersectingTiles;
    }


    private static boolean isChokePoint(RSTile tile) {
        if (!PathFinding.isTileWalkable(tile)) {
            return false;
        }
        ArrayList<RSTile> adjacent = adjacentTiles(tile);
        ArrayList<RSTile> walls = new ArrayList<>();
        for (RSTile aTile : adjacent) {
            if (!PathFinding.isTileWalkable(aTile)) {
                walls.add(aTile);
            }
        }
        if (walls.size() < 2) {
            return false;
        }
        for (RSTile wallTile : walls) {
            ArrayList<RSTile> adjacent2 = adjacentTiles(wallTile);
            ArrayList<RSTile> commonTiles = getIntersectingTiles(adjacent, adjacent2);
            boolean pass = true;
            for (RSTile ctile : commonTiles) {
                if (!PathFinding.isTileWalkable(ctile)) {
                    pass = false;
                }
            }
            if (pass) {
                return true;
            }
        }
        return false;
    }


    private static boolean isSafeSpotAgainst(RSTile tile1, RSTile tile2) {
        RSTile[] path = PathFinding.generatePath(tile1, tile2, false);
        if (path == null) {
            return false;
        }
        ArrayList<RSTile> pathTiles = new ArrayList<>(Arrays.asList(path));
        pathTiles = (ArrayList<RSTile>) pathTiles.stream()
                .filter(tile -> tile.distanceTo(tile1) != 0 && tile.distanceTo(tile2) != 0)
                .collect(Collectors.toList());
        for (RSTile possibleChokePoint : pathTiles) {
            if (isChokePoint(possibleChokePoint)) {
                // Tiles behind chokepoints are safe
                return true;
            }
        }
        // Diagonals are safe
        return tile1.getX() != tile2.getX() && tile1.getY() != tile2.getY() && tile1.distanceTo(tile2) == 1;
    }

    private static boolean isSafeSpotAgainst(RSTile tile, RSNPC enemy) {
        ArrayList<RSTile> enemyTiles = new ArrayList<>();
        enemyTiles.add(enemy.getPosition());
        enemyTiles.add(new RSTile(enemy.getPosition().getX() - 1, enemy.getPosition().getY() - 1, enemy.getPosition().getPlane()));
        enemyTiles.add(new RSTile(enemy.getPosition().getX(), enemy.getPosition().getY() - 1, enemy.getPosition().getPlane()));
        enemyTiles.add(new RSTile(enemy.getPosition().getX() - 1, enemy.getPosition().getY(), enemy.getPosition().getPlane()));
        for (RSTile eTile : enemyTiles) {
            if (!isSafeSpotAgainst(tile, eTile)) {
                return false;
            }
        }
        return true;
    }


    /* Safe spots from Mursumarsu https://community.tribot.org/topic/74980-chokepoint-safespot-finder/ */

    public Optional<RSTile> getSafeSpot(String npcName) {
        RSTile playerPosition = Player.getPosition();
        if (playerPosition == null) {
            return Optional.empty();
        }
        int playerX = playerPosition.getX();
        int playerY = playerPosition.getY();
        int playerPlane = playerPosition.getPlane();
        ArrayList<RSTile> playerArea = new ArrayList<>();
        for (int x = -2; x < 3; x++) {
            for (int y = -2; y < 3; y++) {
                playerArea.add(new RSTile(playerX + x, playerY + y, playerPlane));
            }
        }
        RSNPC rsnpc = (RSNPC) AntiBan.getABCUtil().selectNextTarget(NPCs.findNearest(npcName));
        List<RSTile> safeTiles = new LinkedList<>();
        for (RSTile aTile : playerArea) {
            if (isSafeSpotAgainst(aTile, rsnpc)) {
                safeTiles.add(aTile);
            }
        }
        RSTile safeSpot = AntiBan.selectNextTarget(safeTiles.toArray(new RSTile[0]));
        return Optional.ofNullable(safeSpot);
    }


}
