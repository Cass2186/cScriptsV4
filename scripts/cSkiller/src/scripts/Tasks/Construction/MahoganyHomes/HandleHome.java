package scripts.Tasks.Construction.MahoganyHomes;

import lombok.Getter;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.MyPlayer;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.Widgets;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.GameObject;
import org.tribot.script.sdk.types.Widget;
import org.tribot.script.sdk.types.WorldTile;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.PathingUtil;
import scripts.Tasks.BirdHouseRuns.Nodes.Wait;
import scripts.Tasks.Combat.Data.Const;
import scripts.Utils;
import scripts.WidgetInfo;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class HandleHome implements Task {

    private Instant lastChanged;
    private int lastCompletedCount = -1;

    @Getter
    private final List<GameObject> objectsToMark = new ArrayList<>();
    // Varb values: 0=default, 1=Needs repair, 2=Repaired, 3=Remove 4=Bulld 5-8=Built Tiers
    private final HashMap<Integer, Integer> varbMap = new HashMap<>();


    private void setCurrentHome(Home h) {
        ConsVars.get().mahoganyHome = Optional.of(h);
    }

    private void processGameObjects(final GameObject cur) {
        if (cur == null || (!Hotspot.isHotspotObject(cur.getId()) && !Home.isLadder(cur.getId()))) {
            return;
        }

        // Filter objects inside highlight overlay
        objectsToMark.add(cur);
    }

    private void updateVarbMap() {
        varbMap.clear();for (final Hotspot spot : Hotspot.values()) {
            varbMap.put(spot.getVarb(), Utils.getVarBitValue(spot.getVarb()));
        }
    }


    int getCompletedCount() {
        if (ConsVars.get().mahoganyHome.isEmpty()) {
            return -1;
        }

        int count = 0;
        for (final Hotspot hotspot : Hotspot.values())
        {
            final boolean requiresAttention = doesHotspotRequireAttention(hotspot.getVarb());
            if (!requiresAttention)
            {
                continue;
            }

            count++;
        }

        return count;
    }

    boolean doesHotspotRequireAttention(final int varb) {
        final Integer val = varbMap.get(varb);
        if (val == null) {
            return false;
        }
        // Varb values: 0=default, 1=Needs repair, 2=Repaired, 3=Remove 4=Bulld 5-8=Built Tiers
        return val == 1 || val == 3 || val == 4;
    }

    public void handle(){

        if (ConsVars.get().mahoganyHome.isEmpty()) {
            Log.warn("No contract");
            return;
        }
        final Home home = ConsVars.get().mahoganyHome.get();
        if (home.getArea().containsMyPlayer()){
            Log.info("Going to home");
            PathingUtil.walkToArea(home.getArea());
        }
        List<GameObject> listOfObj = Query.gameObjects().maxDistance(10).toList();
        for (GameObject obj : listOfObj){
            processGameObjects(obj);
        }

        for (GameObject gameObject : getObjectsToMark()) {

            if (gameObject.getTile().getPlane() != MyPlayer.getTile().getPlane()) {
                continue;
            }

          //  if (plugin.distanceBetween(home.getArea(), gameObject.getWorldLocation()) > 0) {
                // Object not inside area for this house.
           //     continue;
        //   }

            final Hotspot spot = Hotspot.getByObjectId(gameObject.getId());
            if (spot != null) {
                // Ladders aren't hotspots so handle them after this check
                if (Home.isLadder(gameObject.getId()) || gameObject.getActions().stream()
                        .anyMatch(s->s.contains("Climb"))) {
                    Log.warn("Is ladder");
                    continue;
                }else {
                    // Do not highlight the hotspot if the config is disabled or it doesn't require any attention
                    if (!doesHotspotRequireAttention(spot.getVarb()))
                         continue;
                }
            }

            if (gameObject.interact(gameObject.getActions().get(0))){
                Waiting.waitUntil(6000, 500, ()-> Widgets.isVisible(458));
            }
        }
    }


    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        return Vars.get().currentTask != null &&
                Vars.get().currentTask.equals(SkillTasks.CONSTRUCTION) && ConsVars.get().isDoingHomes &&
                (ConsVars.get().mahoganyHome.isPresent() || Utils.getVarBitValue(5983) == 1);
    }

    @Override
    public void execute() {
        handle();
    }

    @Override
    public String toString() {
        return "Doing Contract";
    }

    @Override
    public String taskName() {
        return "Construction-  Mahogany Homes";
    }


}
