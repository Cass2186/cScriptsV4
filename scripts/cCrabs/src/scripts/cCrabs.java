package scripts;

import dax.api_lib.WebWalkerServerApi;
import dax.api_lib.models.DaxCredentials;
import dax.api_lib.models.DaxCredentialsProvider;
import org.apache.commons.lang3.StringUtils;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Game;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.*;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Options;
import scripts.Data.Paint;
import scripts.Data.Vars;
import scripts.Tasks.*;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@ScriptManifest(name = "cCrabs v2", authors = {"Cass2186"}, category = "Combat")
public class cCrabs extends Script implements Starting, Ending, Painting, MessageListening07, Breaking, Arguments {

    public static AtomicBoolean isRunning = new AtomicBoolean(true);
    public static String status = "Initializing";


    Skills.SKILLS currentSkill = Skills.SKILLS.STRENGTH;

    public void populateInitialMap(){
        Log.log("[Debug]: Populating intial skills xp HashMap");
        for (Skills.SKILLS s : Skills.SKILLS.values() ){
            Vars.get().skillStartXpMap.put(s, s.getXP());
        }
    }

    public HashMap<Skills.SKILLS, Integer> getXpMap(){
        if (Vars.get().skillStartXpMap == null || Vars.get().skillStartXpMap.size() == 0)
            populateInitialMap();

        HashMap<Skills.SKILLS, Integer> map = new HashMap<>();
        for (Skills.SKILLS s : Skills.SKILLS.values() ){
            int startXp =  Vars.get().skillStartXpMap.get(s);
            if (s.getXP() > startXp){
                map.put(s,s.getXP() - startXp );
            }
        }

        return map;
    }

    @Override
    public void run() {
        Options.AttackOption.setNpcAttackOption(Options.AttackOption.LEFT_CLICK_WHERE_AVAILABLE);
        populateInitialMap();
        TaskSet tasks;
        tasks = new TaskSet(
                new Bank(),
                new EatDrink(),
                new MoveToCrabTile(),
                new HopWorlds(),
                new Fight(),
                new ResetAggro()
        );

        Utils. setCameraZoomAboveDefault();
        super.setLoginBotState(false);

        isRunning.set(true);

        while (isRunning.get()) {
            General.sleep(50, 150);
            if (Game.isInInstance()) //died
                break;

            Task task = tasks.getValidTask();
            if (task != null) {
                status = task.toString();
                task.execute();
            }

        }

    }

    @Override
    public void onStart() {
        WebWalkerServerApi.getInstance().setDaxCredentialsProvider(new DaxCredentialsProvider() {
            @Override
            public DaxCredentials getDaxCredentials() {
                return new DaxCredentials("sub_H0C2eULZfbWeoF ", "acb35610-d868-4ce8-8797-0d2e659f87f4");
            }
        });
    }


    @Override
    public void onPaint(Graphics g) {

        HashMap<Skills.SKILLS, Integer> xpMap = getXpMap();
        double timeRan = getRunningTime();
        double timeRanMin = (timeRan / 3600000);

        List<String> myString = new ArrayList<>(Arrays.asList(
                "cCrabs v2",
                "Running For: " + Timing.msToString(getRunningTime()),
                "Task: " + status
        ));
        if (xpMap != null) {
            for (Skills.SKILLS s : xpMap.keySet()) {
                int startLvl = Skills.getLevelByXP(Vars.get().skillStartXpMap.get(s));
                int xpHr = (int) (xpMap.get(s) / timeRanMin);
                long xpToLevel1 = Skills.getXPToLevel(s, s.getActualLevel() + 1);
                long ttl = (long) (xpToLevel1 / ((xpMap.get(s) / timeRan)));
                int gained = s.getActualLevel() - startLvl;
                if (gained > 0) {
                    myString.add(StringUtils.capitalize(s.toString().toLowerCase(Locale.ROOT))
                            + " [" + s.getActualLevel() + " (+" + gained + ")]: "
                            + Utils.addCommaToNum(xpMap.get(s)) + "xp (" + Utils.addCommaToNum(xpHr) + "/hr) " +
                            "|| TNL: "
                            + Timing.msToString(ttl)
                    );
                } else {
                    myString.add(StringUtils.capitalize(s.toString().toLowerCase(Locale.ROOT))
                            + " [" + s.getActualLevel() + "]: "
                            + Utils.addCommaToNum(xpMap.get(s)) + "xp (" + Utils.addCommaToNum(xpHr) + "/hr) " +
                            "|| TNL: "
                            + Timing.msToString(ttl)
                    );
                }
            }
        }
        PaintUtil.createPaint(g, myString.toArray(String[]::new));
    }

    @Override
    public void passArguments(HashMap<String, String> hashMap) {

    }

    @Override
    public void onEnd() {
        General.println("[Ending]: Runtime: " + Timing.msToString(this.getRunningTime()));
    }


    @Override
    public void onBreakEnd() {

    }

    @Override
    public void onBreakStart(long l) {
        PathingUtil.localNavigation(new RSTile(1760, 3420, 0));
    }


    @Override
    public void serverMessageReceived(String message) {
        if (message.contains("quiver")) {
            General.println("[Debug]: Out of ammo, ending script");
            isRunning.set(false);
        }
    }
}
