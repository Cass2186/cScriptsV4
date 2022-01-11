package scripts;

import dax.api_lib.WebWalkerServerApi;
import dax.api_lib.models.DaxCredentials;
import dax.api_lib.models.DaxCredentialsProvider;
import dax.teleports.Teleport;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.*;
import scripts.Tasks.*;
import scripts.VorkUtils.Vars;
import scripts.VorkUtils.VorkthUtil;

import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

@ScriptManifest(name = "cVorkath", authors = {"Cass2186"}, category = "Testing", description = "Current Version v0.1")
public class cVorkath extends Script implements Painting, Starting, Ending, Arguments, Breaking, MessageListening07 {

    public static AtomicBoolean isRunning = new AtomicBoolean(true);
    public static String status = "Initializing";
    public static String currentTask = "Initializing";
    public static Skills.SKILLS currentSkill;

    public Timer safetyTimer = new Timer(360000); //6 min


    @Override
    public void run() {
        Mouse.setSpeed(250);
        TaskSet tasks = new TaskSet(
                new ActivatePrayer(),
                new BallPhase(),
                new PurpleFlame(),
                new StartFight(),
                new HouseBank(),
                new ZombifiedSpawn(),
                new AcidPhase(),
                new AttackVork(),
                new Loot(),
                new DeathCollection(),
                new EatDrink(),
                new ChangeBolts()
        );
        Utils.setCameraZoomAboveDefault();

        isRunning.set(true);

        HashMap<Skills.SKILLS, Integer> startHashMap = Utils.getXpForAllSkills();

        while (isRunning.get()) {
            General.sleep(20, 40);
            if (Utils.hasTotalXpIncreased(startHashMap)) {
                safetyTimer.reset();
            }
            if (!safetyTimer.isRunning()) {
                General.println("[Debug]: Script timed out due to no xp gain in 6 min");
                isRunning.set(false);
                break;
            }
            Task task = tasks.getValidTask();
            if (task != null) {
                currentTask = task.toString();
                task.execute();
            }
        }


    }

    @Override
    public void passArguments(HashMap<String, String> hashMap) {

    }

    @Override
    public void onBreakEnd() {

    }

    @Override
    public void onBreakStart(long l) {

    }

    @Override
    public void onEnd() {

    }

    @Override
    public void onPaint(Graphics g) {
        double timeRan = getRunningTime();
        double timeRanMin = (timeRan / 3600000);
        List<String> myString;
        Optional<RSTile> fightTile = VorkthUtil.getFightCenterTile();
        if (fightTile.isPresent()) {
            g.setColor(Color.WHITE);
            Polygon pp = Projection.getTileBoundsPoly(fightTile.get(), 0);
            g.drawPolygon(pp);
        }
        Optional<RSTile> eTile = VorkthUtil.getEasternAcidTile();
        if (eTile.isPresent()) {
            g.setColor(Color.WHITE);
            Polygon pp = Projection.getTileBoundsPoly(eTile.get(), 0);
            g.drawPolygon(pp);
        }
        Optional<RSTile> wTile = VorkthUtil.getWesternAcidTile();
        if (wTile.isPresent()) {
            g.setColor(Color.WHITE);
            Polygon pp = Projection.getTileBoundsPoly(wTile.get(), 0);
            g.drawPolygon(pp);
        }
        Optional<RSTile> centre = VorkthUtil.getFightCenterTile();
        if (centre.isPresent()) {
            g.setColor(Color.GREEN);
            RSTile topCentre = centre.get().translate(0, -2);


            if (topCentre.distanceToDouble(Player.getPosition()) < 2.5) {
                g.setColor(Color.GREEN);
            } else
                g.setColor(Color.WHITE);
            Polygon pp = Projection.getTileBoundsPoly(topCentre, 0);
            g.drawPolygon(pp);
            g.setColor(Color.WHITE);
            g.drawString("DISTANCE: " + topCentre.distanceToDouble(Player.getPosition()),
                    pp.getBounds().x,   pp.getBounds().y);
        }

        myString = new ArrayList<>(Arrays.asList(
                "cVorkath",
                "Running For: " + Timing.msToString(getRunningTime()),
                "Task: " + status,
                "Current Task: " + currentTask,
                "Is dead? " + Vars.get().collectDeath,
                "Is bankTask Satisfied: " + Vars.get().bankTask.isSatisfied(),
                "Antifire Timer: " + Timing.msToString(Vars.get().antiFireTimer.getRemaining()),
                "Viewport scale: " + Game.getViewportScale()
        ));
        RSNPC[] vork = NPCs.findNearest(VorkthUtil.ATTACKING_VORK);
        if (vork.length > 0){
            myString.add("Health Percent: " + vork[0].getHealthPercent());
        }


        PaintUtil.createPaint(g, myString.toArray(String[]::new));
    }

    @Override
    public void onStart() {
        AntiBan.create();

        Teleport.blacklistTeleports(Teleport.GLORY_KARAMJA, Teleport.RING_OF_WEALTH_MISCELLANIA,
                Teleport.RING_OF_DUELING_FEROX_ENCLAVE);

        WebWalkerServerApi.getInstance().setDaxCredentialsProvider(new DaxCredentialsProvider() {
            @Override
            public DaxCredentials getDaxCredentials() {
                return new DaxCredentials("sub_DPjXXzL5DeSiPf", " PUBLIC-KEY");
            }
        });
    }

    @Override
    public void serverMessageReceived(String message) {
        if (message.toLowerCase().contains("you're dead")) {
            General.println("[Debug]: Death message recieved");
            Vars.get().collectDeath = true;
        }
    }
}
