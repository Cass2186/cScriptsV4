package scripts.Tasks;

import obf.N;
import org.tribot.api.General;
import org.tribot.api2007.Game;
import org.tribot.api2007.Options;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.types.LocalTile;
import org.tribot.api2007.util.DPathNavigator;
import org.tribot.script.sdk.types.LocalTile;
import org.tribot.script.sdk.walking.LocalWalking;
import org.tribot.script.sdk.walking.WalkState;
import scripts.Data.CaveNPCs;
import scripts.Data.NavHelper;
import scripts.Data.Vars;
import scripts.Data.Wave;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SafeSpotTask implements Task {

    NavHelper safeSpot = new NavHelper();
    int minDistance = General.random(3, 6);

    @Override
    public String toString() {
        return "Walking to safe spot";
    }

    @Override
    public Priority priority() {
        return Priority.HIGH;
    }

    @Override
    public boolean validate() {
        return (CaveNPCs.underAttack(Wave.getCurrentWave()));
    }

    @Override
    public void execute() {
        if (!isPendingCompletion()) {
            return;
        }
        org.tribot.script.sdk.Options.setRunEnabled(true);
        emergencyRunActivation();
        safeSpot.setAcceptAdjacentTiles(true);
        safeSpot.setExcludedTiles(CaveNPCs.getTilesOfDangerousNPC(Wave.getCurrentWave()));

        int upperLimit = 0;
        if (getClosestPath().get(0).distance() < minDistance) {
            Log.info("We need the next path");
            Log.info("Min Distance " + minDistance);
            Log.info("Distance to next closest" +
                    getNextClosestPath().get(upperLimit).distance());
//            Log.info("Distance from NPC", Player07.distanceTo(CaveNPCs.getTileOfDangerousNPC(Wave.getCurrentWave())));
            if (safeSpot.walkToTile(getDestinationPath().get(0), getWalkState())) {
                return;
            }
        }
        upperLimit = General.random(0, getClosestPath().size() - 1);
        if (safeSpot.walkToTile(getClosestPath().get(upperLimit), getWalkState())) {
            Optional<Integer> dist = Optional.ofNullable(
                    CaveNPCs.getTileOfDangerousNPC(Wave.getCurrentWave()).distance());
            Log.info("We're moving to the closest path");
            Log.info("Min Distance " + minDistance);
//            Log.info("Distance to closest", Player07.distanceTo(getClosestPath().get(upperLimit)));
            if (dist.isPresent())
                Log.info("Distance from NPC " + dist);
            //setComplete();
        }
    }

    private WalkState getWalkState() {
        Optional<LocalTile> local = Optional.ofNullable(CaveNPCs.getTileOfDangerousNPC(Wave.getCurrentWave()));
        if (local.map(l -> l.distance() > General.random(4, 7)).orElse(false) &&
                !CaveNPCs.isDangerousNPCMoving(Wave.getCurrentWave())) {
            return WalkState.FAILURE;
        }
        return WalkState.CONTINUE;
    }

    public void step() throws InterruptedException, IOException {
        if (!isPendingCompletion()) {
            return;
        }
        org.tribot.script.sdk.Options.setRunEnabled(true);
        emergencyRunActivation();
        safeSpot.setAcceptAdjacentTiles(true);
        safeSpot.setExcludedTiles(CaveNPCs.getTilesOfDangerousNPC(Wave.getCurrentWave()));

        int upperLimit;
        if (getClosestPath().get(0).distance() < minDistance) {
//            Log.info("We need the next path");
//            Log.info("Min Distance", minDistance);
//            Log.info("Distance to next closest", Player07.distanceTo(getNextClosestPath().get(upperLimit)));
//            Log.info("Distance from NPC", Player07.distanceTo(CaveNPCs.getTileOfDangerousNPC(Wave.getCurrentWave())));
            if (safeSpot.walkToTile(getDestinationPath().get(0), getWalkState())) {
                return;
            }
        }
        upperLimit = General.random(0, getClosestPath().size() - 1);
        if (safeSpot.walkToTile(getClosestPath().get(upperLimit), getWalkState())) {
//            Log.info("We're moving to the closest path");
//            Log.info("Min Distance", minDistance);
//            Log.info("Distance to closest", Player07.distanceTo(getClosestPath().get(upperLimit)));
//            Log.info("Distance from NPC", Player07.distanceTo(CaveNPCs.getTileOfDangerousNPC(Wave.getCurrentWave())));
            //setComplete();
        }

    }


    List<LocalTile> getDestinationPath() {
        LocalTile closestTile = null;
        Vars.get().allPaths = new ArrayList<>() {{
            add(Vars.get().nePath);
            add(Vars.get().nwPath);
            add(Vars.get().swPath);
            add(Vars.get().sePath);
        }};
        for (List<LocalTile> path : Vars.get().allPaths) {
            if ((path.equals(getClosestPath()) || (path.equals(getNextClosestPath())))) {
                continue;
            }
            if (closestTile == null) {
                closestTile = path.get(0);
            }
            if (closestTile.distance() > path.get(0).distance()) {
                closestTile = path.get(0);
            }
            if (closestTile.distance() >
                    path.get(path.size() - 1).distance()) {
                closestTile = path.get(path.size() - 1);
                Collections.reverse(Vars.get().allPaths);
            }
        }
        for (List<LocalTile> path : Vars.get().allPaths) {
            if (path.contains(closestTile)) {
                return path;
            }
        }
        return null;
    }

    List<LocalTile> getNextClosestPath() {
        LocalTile closestTile = null;
        Vars.get().allPaths = new ArrayList<>() {{
            add(Vars.get().nePath);
            add(Vars.get().nwPath);
            add(Vars.get().swPath);
            add(Vars.get().sePath);
        }};
        for (List<LocalTile> path : Vars.get().allPaths) {
            if (path.equals(getClosestPath())) {
                continue;
            }
            if (closestTile == null) {
                closestTile = path.get(0);
            }
            if (closestTile.distance() > path.get(0).distance()) {
                closestTile = path.get(0);
            }
            if (closestTile.distance() >
                    path.get(path.size() - 1).distance()) {
                closestTile = path.get(path.size() - 1);
                Collections.reverse(Vars.get().allPaths);
            }
        }
        for (List<LocalTile> path : Vars.get().allPaths) {
            if (path.contains(closestTile)) {
                return path;
            }
        }
        return null;
    }

    List<LocalTile> getClosestPath() {
        LocalTile closestTile = null;
        Vars.get().allPaths = new ArrayList<>() {{
            add(Vars.get().nePath);
            add(Vars.get().nwPath);
            add(Vars.get().swPath);
            add(Vars.get().sePath);
        }};
        for (List<LocalTile> path : Vars.get().allPaths) {
            if (closestTile == null) {
                closestTile = path.get(0);
            }
            if (closestTile.distance() > path.get(0).distance()) {
                closestTile = path.get(0);
            }
            if (closestTile.distance() >
                    path.get(path.size() - 1).distance()) {
                closestTile = path.get(path.size() - 1);
                Collections.reverse(Vars.get().allPaths);
            }
        }
        for (List<LocalTile> path : Vars.get().allPaths) {
            if (path.contains(closestTile)) {
                return path;
            }
        }
        return null;
    }

    public boolean isPendingCompletion() {
        return (CaveNPCs.underAttack(Wave.getCurrentWave()));
    }

    boolean emergencyRunActivation() {
        if (!Game.isRunOn() && Game.getRunEnergy() > General.random(3, 10)) {
            return Options.setRunEnabled(true);
        }
        return false;
    }
}

