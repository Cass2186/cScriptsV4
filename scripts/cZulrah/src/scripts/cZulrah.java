package scripts;

import javafx.scene.shape.MoveTo;
import lombok.AccessLevel;
import lombok.Getter;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.Projection;

import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.Painting;
import org.tribot.script.sdk.Log;

import org.tribot.script.sdk.MyPlayer;
import org.tribot.script.sdk.Prayer;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.types.LocalTile;
import org.tribot.script.sdk.types.Npc;

import scripts.Data.Phase.ZulrahPhase;
import scripts.Data.TickObserver;
import scripts.Data.Vars;
import scripts.Data.ZulrahInstance;
import scripts.Listeners.AnimationListener;
import scripts.Listeners.AnimationObserver;
import scripts.Listeners.TickListener;
import scripts.QuestSteps.QuestTask;
import scripts.Tasks.*;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@ScriptManifest(name = "cZulrah", authors = {"Cass2186"}, category = "Testing", description = "Current Version v0.1")
public class cZulrah extends Script implements AnimationListener, TickListener, Painting {

    public static AtomicBoolean isRunning = new AtomicBoolean(true);
    String status = "";
    public void initializeListeners() {
        AnimationObserver animationListener = new AnimationObserver(() -> true, "Zulrah");
        animationListener.addListener(this);
        animationListener.start();
        TickObserver tickObserver = new TickObserver();
        tickObserver.start();
    }


   /* @Subscribe
    private void onNpcSpawned(NpcSpawned event) {
        NPC npc = event.getNpc();
        if (npc != null && npc.getName() != null &&
                npc.getName().toLowerCase().contains("zulrah")) {
            zulrah = npc;
        }
    }*/

    @Override
    public void onAnimationChanged(int newAnimation) {
        if (TickObserver.instance == null) {
            return;
        }

        ZulrahPhase currentPhase = TickObserver.instance.getPhase();
        ZulrahPhase nextPhase = TickObserver.instance.getNextPhase();

        if (currentPhase == null || nextPhase == null) {
            return;
        }

        if (TickObserver.zulrah != null) { //&& //zulrah.equals(actor) &&
            //  zulrah.getAnimation() == AnimationID.ZULRAH_PHASE) {
            Prayer prayer = nextPhase.getPrayer();

            if (prayer == null) {
                return;
            }

            switch (prayer) {
                case PROTECT_FROM_MAGIC:
                    Log.debug("[cZulrah]: Should pray Magic");
                    Vars.get().shouldPrayMagic = true;
                    break;
                case PROTECT_FROM_MISSILES:
                    Log.debug("[cZulrah]: Should pray Missles");
                    Vars.get().shouldPrayRanged = true;
                    break;
            }
        }
    }

    @Override
    public void run() {
        initializeListeners();
        Utils.setCameraZoomAboveDefault();
        Mouse.setSpeed(250);

        TaskSet   tasks = new TaskSet(
                new AttackZulrah(),
              //  new Bank(),
              //  new ChangeGear(),
                new Eat(),
                new ChangePrayer(),
                new MoveToTile(),
                new ChangeGear()
               // new StartFight(),

        );


        isRunning.set(true);
        while (isRunning.get()) {
            General.sleep(100);
            Task task = tasks.getValidTask();
            if (task != null) {
                status = task.toString();
                task.execute();
            } else {
               // Log.log("[Debug]: Task is null");
            }
        }
    }

    @Override
    public void onTick() {

    }

    @Override
    public void onPaint(Graphics g) {
        double timeRan = getRunningTime();
        double timeRanMin = (timeRan / 3600000);
        List<String>
                myString = new ArrayList<>(Arrays.asList(
                "cZulrah v0.1",
                "Running For: " + Timing.msToString(getRunningTime()),
                "MagicPrayer: " + Vars.get().shouldPrayMagic,
                "RangedPrayer: " + Vars.get().shouldPrayRanged,
                "Task: " + status
        ));

        if (TickObserver.instance != null) {
            ZulrahPhase currentPhase = TickObserver.instance.getPhase();
            LocalTile startTile = TickObserver.instance.getStartLocation();
            if (currentPhase != null) {
                myString.add(currentPhase.toString());
                LocalTile localTile = currentPhase.getStandTile(startTile);
                if (localTile.getBounds().isPresent())
                    g.drawPolygon(localTile.getBounds().get());
            }
            if (startTile != null)
                g.drawPolygon(startTile.getBounds().get());
        }
        g.drawPolygon(MyPlayer.getPosition().getBounds().get());

        PaintUtil.createPaint(g, myString.toArray(String[]::new));
    }
}
