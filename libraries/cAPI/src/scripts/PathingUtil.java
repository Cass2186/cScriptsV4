package scripts;

import dax.api_lib.DaxWalker;
import dax.api_lib.models.RunescapeBank;
import dax.walker.utils.camera.DaxCamera;
import dax.walker_engine.WalkerEngine;
import dax.walker_engine.WalkingCondition;
import dax.walker_engine.interaction_handling.NPCInteraction;
import lombok.val;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.Combat;
import org.tribot.api2007.Equipment;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Options;
import org.tribot.api2007.Prayer;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import org.tribot.api2007.util.DPathNavigator;
import org.tribot.script.sdk.*;
import org.tribot.script.sdk.interfaces.Positionable;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.Area;
import org.tribot.script.sdk.types.InventoryItem;
import org.tribot.script.sdk.types.LocalTile;
import org.tribot.script.sdk.types.WorldTile;
import org.tribot.script.sdk.util.TribotRandom;
import org.tribot.script.sdk.walking.GlobalWalking;
import org.tribot.script.sdk.walking.LocalWalking;
import org.tribot.script.sdk.walking.WalkState;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.Random;
import java.util.function.Supplier;

public class PathingUtil {

    private static PathingUtil pathingUtil = new PathingUtil();

    public static PathingUtil getInstance() {
        return pathingUtil == null ? new PathingUtil() : pathingUtil;
    }

    public static DPathNavigator nav = new DPathNavigator();

    private static int nextStaminaPotionUse = General.randomSD(55, 80, 70, 7);
    private static int eatAtPercent = General.randomSD(45, 70, 60, 5);
    private static RSArea stonesArea = new RSArea(new RSTile(2521, 3595, 0), 5);

    private static void setDaxPref() {
        DaxWalker.setGlobalWalkingCondition(() -> {
            try {
                if (stonesArea.contains(Player.getPosition())) {
                    General.println("[DaxPref]: In stone area");
                    if (Combat.getHPRatio() <= eatAtPercent) {
                        General.println("[DaxPref]: Need to eat food");
                        EatUtil.eatFood();
                    }
                }
                setDaxTalkConditions(new ArrayList<>(Arrays.asList(
                        "Yes, I can think of nothing more exciting!",
                        "Yes, I want to climb over the rocks.")));

                if (NPCInteraction.isConversationWindowUp()) {
                    General.println("[DaxPref]: Chat is up");
                    NPCInteraction.handleConversation("Yes, I can think of nothing more exciting!",
                            "Yes, I want to climb over the rocks.");
                }
                if (Utils.getVarBitValue(25) == 0 && Game.getRunEnergy() <= nextStaminaPotionUse) {
                    //General.println("[DaxPref]: Need to drink stamina ");
                    RSItem[] invStam = Inventory.find(ItemID.STAMINA_POTION);
                    if (invStam.length > 0 && invStam[0].click()) {
                        nextStaminaPotionUse = General.randomSD(55, 80, 70, 7);
                        Waiting.waitUntilAnimating(2000);
                    }
                }
                if (MyPlayer.isPoisoned()) {
                    if (Inventory.find(ItemID.ANTIDOTE_PLUS_PLUS).length > 0)
                        Utils.drinkPotion(ItemID.ANTIDOTE_PLUS_PLUS);

                }
                if (Combat.getHPRatio() <= eatAtPercent) {
                    General.println("[DaxPref]: Need to eat food");
                    EatUtil.eatFood();
                }
                RSItem[] pPot = Inventory.find(ItemID.PRAYER_POTION);
                if (Prayer.getPrayerPoints() <= General.random(12, 20)
                        && pPot.length > 0
                        && CombatUtil.isPraying()) {
                    int points = Prayer.getPrayerPoints();
                    General.println("[DaxPref]: Need to drink prayer potion");
                    if (pPot[0].click("Drink"))
                        Timer.waitCondition(() -> Prayer.getPrayerPoints() > points, 2250, 2750);
                }

            } catch (Exception e) {
                General.println("[DaxPref]: failed to handle staminas/eating", Color.CYAN);
                e.printStackTrace();
            }
            return WalkingCondition.State.CONTINUE_WALKER;
        });
    }

    private static boolean handleFood() {
        Optional<InventoryItem> eatable = Query.inventory().actionContains("Eat").findClosestToMouse();
        if (Combat.getHPRatio() <= eatAtPercent) {
            Log.warn("[DaxPref]: Need to eat food");
            if (eatable.map(e -> e.click("Eat")).orElse(false)) {
                Utils.idleNormalAction();
                eatAtPercent = General.randomSD(45, 70, 60, 5);
                Log.info("[DaxPref]: Next eating at % " + eatAtPercent);
                return true;
            } else if (eatable.isEmpty()) {
                Log.warn("[DaxPref]: Need to eat food, but none in inventory");
                return false;
            }
        }
        return true;
    }

    public static Optional<LocalTile> getWalkableTile(LocalTile tile) {
        return Query.tiles()
                .inArea(Area.fromRadius(tile, 1))
                .filter(LocalTile::isWalkable)
                .findBestInteractable();
    }


    public static WalkState getWalkState() {
        // for horror from the deep rock area
        if (stonesArea.contains(Player.getPosition())) {
            Log.info("[DaxPref]: In stone area");
            if (!handleFood())
                return WalkState.FAILURE;
        }

        if (MyPlayer.getRunEnergy() > 30 && !org.tribot.script.sdk.Options.isRunEnabled()) {
            org.tribot.script.sdk.Options.setRunEnabled(true);
        }

        // handle stamina
        if (Utils.getVarBitValue(25) == 0 && MyPlayer.getRunEnergy() <= nextStaminaPotionUse) {
            Log.info("[DaxPref]: Should Drink stamina");
            Optional<InventoryItem> invStam = Query.inventory().idEquals(ItemID.STAMINA_POTION).findClosestToMouse();
            if (invStam.map(s -> s.click("Drink")).orElse(false)) {
                nextStaminaPotionUse = General.randomSD(55, 80, 70, 7);
                if (Waiting.waitUntilAnimating(1700))
                    Utils.idleNormalAction();
            }
        }

        // handle poison
        if (MyPlayer.isPoisoned()) {
            Optional<InventoryItem> invAntiPoison = Query.inventory()
                    .idEquals(ItemID.ANTIPOISON1, ItemID.ANTIPOISON2,
                            ItemID.ANTIPOISON3, ItemID.ANTIPOISON4).findClosestToMouse();
            Optional<InventoryItem> invAntidote = Query.inventory()
                    .idEquals(ItemID.ANTIDOTE_PLUS_PLUS).findClosestToMouse();

            if ((invAntiPoison.map(p -> p.click("Drink")).orElse(false) ||
                    invAntidote.map(p -> p.click("Drink")).orElse(false)) &&
                    Waiting.waitUntilAnimating(1700)) {
                Utils.idleNormalAction();
            }
        }
        //handle general eating
        if (Combat.getHPRatio() <= eatAtPercent) {
            if (!handleFood())
                return WalkState.FAILURE;
        }

        return WalkState.CONTINUE;
    }

    public static boolean blindWalkToArea(RSArea area) {
        if (!area.contains(Player.getPosition())) {
            checkRun();
            Walking.blindWalkTo(area.getRandomTile());
            return Timer.abc2WaitCondition(() -> area.contains(Player.getPosition()), 8000, 12000);
        }
        return false;
    }

    public static boolean blindWalkToTile(RSTile tile) {
        checkRun();
        if (Walking.blindWalkTo(tile)) {
            Timer.abc2WaitCondition(() -> tile.getPosition().distanceTo(Player.getPosition()) < 5, 8000, 12000);

        }
        return tile.getPosition().distanceTo(Player.getPosition()) < 4;
    }

    private static void drinkStamina() {
        if (Utils.getVarBitValue(25) == 0 && Game.getRunEnergy() <= nextStaminaPotionUse) {
            //General.println("[DaxPref]: Need to drink stamina ");
            RSItem[] invStam = Inventory.find(ItemID.STAMINA_POTION);
            if (invStam.length > 0 && invStam[0].click()) {
                nextStaminaPotionUse = General.randomSD(55, 80, 70, 7);
                Waiting.waitUntilAnimating(1250);
            }
        }
    }

    public static void setDaxTalkConditions(ArrayList<String> s) {
        DaxWalker.setGlobalWalkingCondition(() -> {
            String[] array = new String[s.size()];
            if (NPCInteraction.isConversationWindowUp()) {
                NPCInteraction.handleConversation(s.toArray(array));
            }
            if (Combat.getHPRatio() <= eatAtPercent) {
                General.println("[DaxPref]: Need to eat food");
                EatUtil.eatFood();
            }
            return WalkingCondition.State.CONTINUE_WALKER;
        });
    }

    public static boolean walkToTile(WorldTile destination) {
        Log.info("[PathingUtil] Global Walking V2 - Worldtile");
        for (int i = 0; i < 3; i++) {
            if (!GlobalWalking.walkTo(destination, PathingUtil::getWalkState)) {
                Log.warn("[PathingUtil]  GlobalWalking failed to generate a path, sleeping ~1-2s");
                Waiting.waitNormal(1700, 200);
            } else
                return true;
        }
        return false;
    }

    //TODO Finish
    private static WorldTile getWalkTileFromArea(Area area) {
        WorldTile centre = area.getCenter();
        NormalDistribution norm = new NormalDistribution(0, 1);
        val x = Utils.roundToNearest(norm.sample(), 1);
        val y = Utils.roundToNearest(norm.sample(), 1);
        Log.info("Generated a normally distributed WorldTile around center with X = "
                + x + " & Y = " + y);
        return centre.translate(x, y);
    }


    public static boolean localNav(WorldTile destination, Supplier<WalkState> state) {
        var path = LocalWalking.Map.builder().travelThroughDoors(true)
                .build().getPath(destination);
        Log.info("[PathingUtil] Local walking V2 - Worldtile");
        return LocalWalking.walkPath(path, state);
    }

    public static boolean localNav(WorldTile dest) {
        return localNav(dest.toLocalTile());
    }

    public static boolean localNav(LocalTile destination, Supplier<WalkState> state) {
        var path = LocalWalking.Map.builder().travelThroughDoors(true)
                .build().getPath(destination);
        Log.info("[PathingUtil] Local walking V2 - LocalTile");
        return LocalWalking.walkPath(path, state);
    }

    public static boolean localNav(LocalTile destination, boolean travelThroughDoors) {
        var path = LocalWalking.Map.builder()
                .travelThroughDoors(travelThroughDoors)
                .build().getPath(destination);
        Log.info("[PathingUtil] Local walking V2 - LocalTile (doors = " + travelThroughDoors + ")");
        return LocalWalking.walkPath(path, PathingUtil::getWalkState);
    }

    public static boolean localNav(LocalTile destination) {
        return localNav(destination, true);
    }

    public static boolean movementIdle() {
        int timeout = Game.isRunOn() ? 10000 : 15000;
        if (Waiting.waitUntil(1750, 75, MyPlayer::isMoving))
            return Waiting.waitUntil(timeout, 200, () -> !MyPlayer.isMoving());
        return false;
    }

    public static boolean humanMovementIdle(WorldTile destination) {
        int timeout = Game.isRunOn() ? 10000 : 15000;

        if (Waiting.waitUntil(1750, 75, MyPlayer::isMoving))
            return Waiting.waitUntil(timeout, 175, () -> !MyPlayer.isMoving() ||
                    destination.distanceTo(MyPlayer.getTile()) < 5);
        else return
                destination.distanceTo(MyPlayer.getTile()) < 5;

    }


    public static boolean movementIdle(int longWaitTimeout) {
        Timer.waitCondition(Player::isMoving, 1500, 2200);
        return Timer.waitCondition(() -> !Player.isMoving(), longWaitTimeout, longWaitTimeout + 1500);
    }

    public static boolean localNavigation(RSTile destination) {
        nav.setAcceptAdjacent(true);
        RSTile[] path = nav.findPath(destination);
        if (path.length == 0) {
            General.println("[PathingUtil]: DPathNavigator failed to generate a path");
            return false;
        } else {
            setDaxPref();
            drinkStamina();
            General.println("[PathingUtil]: DPathNavigator generated a path, feeding to dax");
            return WalkerEngine.getInstance().walkPath(Arrays.asList(path));

            //movementIdle();

        }
    }

    public static boolean localNavigation(RSArea destination) {
        nav.setAcceptAdjacent(true);
        RSTile[] path = nav.findPath(destination.getRandomTile());
        if (path.length == 0) {
            General.println("[PathingUtil]: DPathNavigator failed to generate a path");
            return false;
        } else {
            setDaxPref();
            drinkStamina();
            General.println("[PathingUtil]: DPathNavigator generated a path, feeding to dax");
            WalkerEngine.getInstance().walkPath(Arrays.asList(path));
            return Timer.waitCondition(() -> destination.contains(Player.getPosition()), 7000, 10000);
            //movementIdle();

        }
    }

    public static boolean localNavigation(RSTile destination, int tilesBeforeDestination) {
        nav.setAcceptAdjacent(true);
        RSTile[] path = nav.findPath(destination);
        if (path.length == 0) {
            General.println("[PathingUtil]: DPathNavigator failed to generate a path");
            return false;
        } else {
            setDaxPref();
            drinkStamina();
            General.println("[PathingUtil]: DPathNavigator generated a path, feeding to dax");
            if (WalkerEngine.getInstance().walkPath(Arrays.asList(path)) &&
                    Timer.waitCondition(Player::isMoving, 2500, 3000)) {
                return Timer.waitCondition(() -> destination.distanceTo(Player.getPosition()) < tilesBeforeDestination ||
                        !Player.isMoving(), 10000, 15000);
            }
            return true;
        }
    }

    public static boolean localNavigation(RSTile destination, RSObject[] overrideObjects) {
        nav.setAcceptAdjacent(true);
        nav.overrideDoorCache(true, overrideObjects);
        RSTile[] path = nav.findPath(destination);
        if (path.length == 0) {
            General.println("[PathingUtil]: DPathNavigator failed to generate a path");
            return false;
        } else {
            setDaxPref();
            drinkStamina();
            General.println("[PathingUtil]: DPathNavigator generated a path, feeding to dax");
            WalkerEngine.getInstance().walkPath(Arrays.asList(path));
            return true;
            //movementIdle();

        }
    }

    public static boolean localNavigation(RSTile destination, RSObject[] overrideObjects, ArrayList<String> chatList) {
        nav.setAcceptAdjacent(true);
        nav.overrideDoorCache(true, overrideObjects);
        setDaxTalkConditions(chatList);
        RSTile[] path = nav.findPath(destination);
        if (path.length == 0) {
            General.println("[PathingUtil]: DPathNavigator failed to generate a path");
            return false;
        } else {
            General.println("[PathingUtil]: DPathNavigator generated a path, feeding to dax with custom strings");
            WalkingCondition cond = new WalkingCondition() {
                @Override
                public State action() {
                    String[] array = new String[chatList.size()];
                    if (NPCInteraction.isConversationWindowUp()) {
                        NPCInteraction.handleConversation(chatList.toArray(array));
                    }
                    if (Combat.getHPRatio() <= eatAtPercent) {
                        General.println("[DaxPref]: Need to eat food");
                        EatUtil.eatFood();
                    }
                    return State.EXIT_OUT_WALKER_FAIL;
                }

                ;

            };
            WalkerEngine.getInstance().walkPath(Arrays.asList(path), cond);
            return true;
            //movementIdle();

        }
    }

    public static boolean localNavigation(RSTile[] path, RSObject[] overrideObjects, ArrayList<String> chatList) {
        nav.setAcceptAdjacent(true);
        nav.overrideDoorCache(true, overrideObjects);
        setDaxTalkConditions(chatList);
        if (path.length == 0) {
            General.println("[PathingUtil]: DPathNavigator failed to generate a path");
            return false;
        } else {
            General.println("[PathingUtil]: DPathNavigator generated a path, feeding to dax with cusxtom strings");
            setDaxPref();

            WalkerEngine.getInstance().walkPath(Arrays.asList(path), () -> {
                String[] array = new String[chatList.size()];
                if (NPCInteraction.isConversationWindowUp()) {
                    General.println("[Local] Chat is up");
                    NPCInteraction.handleConversation(chatList.toArray(array));
                }
                return WalkingCondition.State.EXIT_OUT_WALKER_FAIL;
            });
            return true;
            //movementIdle();

        }
    }

    public static boolean localNavigation(ArrayList<RSTile> path) {
        nav.setAcceptAdjacent(true);
        if (path.size() == 0) {
            General.println("[PathingUtil]: DPathNavigator failed to generate a path");
            return false;
        } else {
            General.println("[PathingUtil]: DPathNavigator generated a path, feeding to dax");
            WalkerEngine.getInstance().walkPath(path);
            return true;
            //movementIdle();

        }
    }

    public static boolean localNavigation(RSTile[] path, RSObject[] overrideObjects) {
        nav.setAcceptAdjacent(true);
        nav.overrideDoorCache(true, overrideObjects);
        if (path.length == 0) {
            General.println("[PathingUtil]: DPathNavigator failed to generate a path");
            return false;
        } else {
            General.println("[PathingUtil]: DPathNavigator generated a path, feeding to dax");
            WalkerEngine.getInstance().walkPath(Arrays.asList(path));
            return true;
            //movementIdle();

        }
    }

    public RSTile getClosestTileInArea(RSArea area) {
        RSTile[] ts = area.getAllTiles();
        RSTile closest = null;
        for (RSTile t : ts) {
            if (closest == null)
                closest = t;
            if (t.getPosition().distanceTo(Player.getPosition()) <
                    closest.getPosition().distanceTo(Player.getPosition()))
                closest = t;
        }
        return closest;
    }

    public static boolean clickScreenWalk(RSTile tile) {
        if (!Player.getPosition().equals(tile)) {
            checkRun();

            if (!tile.isClickable())
                DaxCamera.focus(tile);

            if (tile.isClickable()) {
                return DynamicClicking.clickRSTile(tile, "Walk here");
            }
        }
        return false;
    }


    public static RSTile nearestBankTile(RSTile startTile) {

        int distance = startTile.distanceTo(RunescapeBank.VARROCK_WEST.getPosition());
        RSTile bankTile = RunescapeBank.VARROCK_WEST.getPosition();

        for (RunescapeBank tile : RunescapeBank.values()) {

            if (startTile.distanceTo(tile.getPosition()) < distance) {
                distance = startTile.distanceTo(tile.getPosition());
                bankTile = tile.getPosition();
                General.println(bankTile + "bankTile");
                General.println(distance);
            }
        }
        return bankTile;
    }

    public static boolean canReach(RSArea area) {
        return PathFinding.canReach(area.getRandomTile(), false);
    }

    public static boolean canReach(RSTile tile) {
        return PathFinding.canReach(tile, false);
    }


    public static boolean webWalkToArea(RSArea area) {
        int min = Game.isRunOn() ? 7000 : 12000;
        int max = Game.isRunOn() ? 9000 : 15000;

        if (!area.contains(Player.getPosition())) {
            for (int i = 0; i < 4; i++) {
                if (!WebWalking.walkTo(area.getRandomTile()))
                    General.println("[WebWalking]: Failed to walk, trying again");
                else
                    return Timer.waitCondition(() -> area.contains(Player.getPosition()), min, max);
            }
        }
        return false;
    }

    public static boolean webWalkToTile(RSTile tile) {
        int min = Game.isRunOn() ? 7000 : 12000;
        int max = Game.isRunOn() ? 9000 : 15000;

        if (tile.distanceTo(Player.getPosition()) > 10) {
            for (int i = 0; i < 4; i++) {
                if (!WebWalking.walkTo(tile))
                    General.println("[WebWalking]: Failed to walk, trying again");
                else
                    return Timer.waitCondition(() -> tile.distanceTo(Player.getPosition()) < 8, min, max);
            }
        }
        return false;
    }

    public static void checkRun() {
        if (Game.getRunEnergy() > (General.random(25, 45)))
            Options.setRunEnabled(true);
    }

    public static RSTile getTile(RSArea area, boolean first) {
        RSTile[] tile = area.getAllTiles();
        int length = tile.length;
        if (tile.length > 0 && first) {
            return tile[0];
        } else if (tile.length > 0) {
            return tile[length - 1];
        } else {
            General.println("[PathingUtils]: Failed to get first tile in area");
            return area.getRandomTile();
        }
    }

    public static RSArea makeLargerArea(RSArea area) {
        RSTile first = getTile(area, true);
        RSTile last = getTile(area, false);
        RSArea largerArea = new RSArea(first.translate(-2, 2), last.translate(2, -2));
        return largerArea;
    }

    public static RSArea makeLargerArea(RSTile tile) {
        RSArea largerArea = new RSArea(tile.translate(-2, 2), tile.translate(2, -2));
        return largerArea;
    }

    public static boolean testWalk(RSTile tile) {
        checkRun();
        int sleepMin = Game.isRunOn() ? 8000 : 15000;
        int sleepMax = Game.isRunOn() ? 15000 : 20000;

        for (int i = 0; i < 3; i++) {
            if (DaxWalker.walkTo(tile, DaxWalker.getGlobalWalkingCondition())) {
                General.println("[Pathing Utils]: Success");
                return true;
            } else {
                General.sleep(1000);
            }
            General.println("[Pathing Utils]: Failed to generate a path or timed out");
        }
        return false;
    }

    public static boolean walkToArea(RSArea area, boolean abc2Sleep, String message) {
        checkRun();
        int sleepMin = Game.isRunOn() ? 8000 : 15000;
        int sleepMax = Game.isRunOn() ? 15000 : 23000;
        RSArea largeArea = makeLargerArea(area);
        long currentTime;
        setDaxPref();
        if (!largeArea.contains(Player.getPosition())) {
            for (int i = 0; i < 3; i++) {
                if (DaxWalker.walkTo(area.getRandomTile(), DaxWalker.getGlobalWalkingCondition())) {
                    currentTime = System.currentTimeMillis();
                    if (Waiting.waitUntil(1500, () -> MyPlayer.isMoving()))
                        Timer.waitCondition(() ->
                                largeArea.contains(Player.getPosition()) ||
                                        area.contains(Player.getPosition()) ||
                                        !MyPlayer.isMoving(), sleepMin, sleepMax);
                    if (abc2Sleep)
                        Utils.abc2ReactionSleep(currentTime);
                    return true;

                } else {
                    General.println("[PathingUtil]: Failed to generate a path, waiting and trying again. Message: " + message);
                    General.sleep(2500, 3500);
                }
            }

        } else // already in area
            return true;

        return false;
    }

    public static boolean walkToArea(Area area) {
        checkRun();
        int sleepMin = Game.isRunOn() ? 8000 : 15000;
        int sleepMax = Game.isRunOn() ? 15000 : 23000;
        setDaxPref();
        if (!area.contains(MyPlayer.getTile())) {
            for (int i = 0; i < 4; i++) {
                WorldTile t =area.getCenter();


                if (i > 1) //if we fail twice to walk to center, use a random tle
                    t = area.getRandomTile();

                Log.info("[PathingUtil]: Walking to Area (SDK)");
                if (GlobalWalking.walkTo(t, PathingUtil::getWalkState) &&
                        Waiting.waitUntil(1300, MyPlayer::isMoving)){
                        Waiting.waitUntil(TribotRandom.uniform(sleepMin, sleepMax),
                                450, () -> area.contains(MyPlayer.getTile()) ||
                                        !MyPlayer.isMoving());


                } else {
                    Log.warn("[PathingUtil]: Failed to generate a path to Worldtile, waiting and trying again");
                    General.sleep(2000, 3000);
                }
            }

        }

        return area.contains(MyPlayer.getTile());
    }


    public static boolean walkToArea(RSArea area) {
        return walkToArea(area, false);
    }

    public static boolean walkToArea(RSArea area, boolean abc2Sleep) {
        checkRun();
        int sleepMin = Game.isRunOn() ? 8000 : 15000;
        int sleepMax = Game.isRunOn() ? 15000 : 20000;
        RSArea largeArea = makeLargerArea(area);
        long currentTime;
        setDaxPref();
        if (!area.contains(Player.getPosition())) {
            General.println("[PathingUtil]: Walking to area. ABC2 Sleep? " + abc2Sleep);
            for (int i = 0; i < 3; i++) {
                RSTile targetTile = area.getRandomTile();
                if (DaxWalker.walkTo(targetTile, DaxWalker.getGlobalWalkingCondition())) {
                    currentTime = System.currentTimeMillis();
                    if (Waiting.waitUntil(1500, () -> MyPlayer.isMoving()))
                        Timer.waitCondition(() ->
                                largeArea.contains(Player.getPosition()) ||
                                        area.contains(Player.getPosition()) ||
                                        !MyPlayer.isMoving(), sleepMin, sleepMax);

                    if (!area.contains(Player.getPosition()) &&
                            targetTile.distanceTo(Player.getPosition()) < 6) {
                        Log.debug("[PathingUtil]: Not quite in area, adjusting");
                        Walking.blindWalkTo(area.getRandomTile());
                    }

                    if (abc2Sleep && area.contains(Player.getPosition()))
                        Utils.abc2ReactionSleep(currentTime);
                } else {
                    General.println("[PathingUtil]: Failed to generate a path, waiting 3-5s and trying again.");
                    General.sleep(2500, 3500);
                }
            }

        } else // already in area
            return largeArea.contains(Player.getPosition());

        return false;
    }

    public static void getLocalMapPathToTarget(Positionable target) {
        var path = LocalWalking.Map.builder().travelThroughDoors(true)
                .build().getPath(target);
        // LocalWalking.walkPath(path);
    }

    public static boolean walkToArea(Area area, boolean abc2Sleep) {
        checkRun();
        int sleepMin = Game.isRunOn() ? 8000 : 15000;
        int sleepMax = Game.isRunOn() ? 15000 : 20000;
        long currentTime = System.currentTimeMillis();

        setDaxPref();
        if (!area.contains(MyPlayer.getTile())) {
            Log.debug("[PathingUtil]: Walking to area. ABC2 Sleep? " + abc2Sleep);
            for (int i = 0; i < 3; i++) {
                Optional<LocalTile> targetTile = Query.tiles().inArea(area).isReachable()
                        .sortedByPathDistance()
                        .findClosest();

                // try walking to center tile first, if it fails, use a random tile
                if (i == 0) {
                    Log.info("Using centre of area");
                    targetTile = Optional.of(area.getCenter().toLocalTile());
                }

                if (targetTile.map(PathingUtil::localNav).orElse(false) ||
                        GlobalWalking.walkTo(area.getCenter(), PathingUtil::getWalkState) ||
                        GlobalWalking.walkTo(area.getRandomTile(), PathingUtil::getWalkState)) {
                    val finalTargetTile = targetTile;
                    currentTime = System.currentTimeMillis();
                    if (Waiting.waitUntil(1500, 25, MyPlayer::isMoving) &&
                            Waiting.waitUntil(Utils.random(sleepMin, sleepMax), 250, () ->
                                    finalTargetTile.get().distanceTo(MyPlayer.getTile()) < Utils.random(4, 6) ||
                                            area.contains(MyPlayer.getTile()) || !MyPlayer.isMoving()))
                        return true;

                    if (abc2Sleep && area.contains(MyPlayer.getTile()))
                        Utils.abc2ReactionSleep(currentTime);

                } else {
                    General.println("[PathingUtil]: Failed to generate a path, waiting 2-3s and trying again.");
                    Waiting.waitUniform(2000, 3000);
                }
            }

        } else // already in area
            return area.contains(MyPlayer.getTile());

        return false;
    }


    public static boolean walkToArea(RSArea area, int attempts, boolean abc2Sleep) {
        checkRun();
        int sleepMin = Game.isRunOn() ? 8000 : 15000;
        int sleepMax = Game.isRunOn() ? 15000 : 20000;
        RSArea largeArea = makeLargerArea(area);
        long currentTime;
        setDaxPref();
        if (!area.contains(Player.getPosition())) {
            for (int i = 0; i < attempts; i++) {
                if (DaxWalker.walkTo(area.getRandomTile(), DaxWalker.getGlobalWalkingCondition())) {
                    currentTime = System.currentTimeMillis();
                    if (Waiting.waitUntil(1500, () -> MyPlayer.isMoving()))
                        Timer.waitCondition(() ->
                                largeArea.contains(Player.getPosition()) ||
                                        area.contains(Player.getPosition()) ||
                                        !MyPlayer.isMoving(), sleepMin, sleepMax);
                    if (abc2Sleep)
                        Utils.abc2ReactionSleep(currentTime);
                    return largeArea.contains(Player.getPosition());

                } else {
                    General.println("[PathingUtil]: Failed to generate a path, waiting 3-5s and trying again.");
                    General.sleep(2500, 3500);
                }
            }

        } else // already in area
            return largeArea.contains(Player.getPosition());

        return false;
    }

    public static boolean walkToTile(RSTile tile) {
        return walkToTile(tile, 2, false);
    }

    public static boolean walkToTile(RSTile tile, int sizeRadius, int attempts) {
        checkRun();
        int sleepMin = Game.isRunOn() ? 8000 : 15000;
        int sleepMax = Game.isRunOn() ? 15000 : 20000;
        RSArea largeArea = makeLargerArea(new RSArea(tile, sizeRadius + 1));

        setDaxPref();

        if (!largeArea.contains(Player.getPosition())) {
            for (int i = 0; i < attempts; i++) {

                if (DaxWalker.walkTo(tile, DaxWalker.getGlobalWalkingCondition())) {
                    Timer.waitCondition(() -> largeArea.contains(Player.getPosition()) || !Player.isMoving(), sleepMin, sleepMax);

                    return largeArea.contains(Player.getPosition()) ||
                            tile.distanceTo(Player.getPosition()) <= sizeRadius + 1;

                } else {
                    General.println("[Debug]: Failed to generate a path, waiting 2-5s and trying again.");
                    General.sleep(2500, 3500);

                    // place this here so it tries at least once to generate a path
                    if (Game.isInInstance()) {
                        General.println("[PathingUtil]: In Instance, breaking from dax walker loop");
                        break;
                    }

                }
                Waiting.waitUniform(500, 750);
            }

        } else // already in area
            return true;

        return false;
    }

    public static boolean walkToTile(RSTile tile, int sizeRadius, boolean abc2Sleep) {
        checkRun();
        int sleepMin = Game.isRunOn() ? 8000 : 15000;
        int sleepMax = Game.isRunOn() ? 15000 : 20000;
        RSArea largeArea = makeLargerArea(new RSArea(tile, sizeRadius + 1));
        long currentTime;
        setDaxPref();

        if (!largeArea.contains(Player.getPosition())) {
            for (int i = 0; i < 3; i++) {

                if (DaxWalker.walkTo(tile, DaxWalker.getGlobalWalkingCondition())) {
                    currentTime = System.currentTimeMillis();
                    Timer.waitCondition(() -> largeArea.contains(Player.getPosition()) || !Player.isMoving(), sleepMin, sleepMax);

                    if (abc2Sleep)
                        Utils.abc2ReactionSleep(currentTime);

                    return largeArea.contains(Player.getPosition()) ||
                            tile.distanceTo(Player.getPosition()) <= sizeRadius + 1;

                } else {
                    General.println("[Debug]: Failed to generate a path, waiting 2-5s and trying again.");
                    General.sleep(2500, 3500);

                    // place this here so it tries at least once to generate a path
                    if (Game.isInInstance()) {
                        General.println("[PathingUtil]: In Instance, breaking from dax walker loop");
                        break;
                    }

                }
            }

        } else // already in area
            return true;

        return false;
    }


    public final int[] RING_OF_DUELING = {2552, 2554, 2556, 2558, 2560, 2562, 2564, 2566};

    public boolean rodTele(String location) {
        RSItem[] rod = Equipment.find(RING_OF_DUELING);
        if (rod.length > 0) {
            for (int i = 0; i < 3; i++) {
                // Main.stage = "Going to " + location;
                if (rod[0].click(location))
                    return true;
            }
        }
        return false;
    }

}
