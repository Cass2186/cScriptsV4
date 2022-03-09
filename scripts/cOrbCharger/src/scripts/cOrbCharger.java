package scripts;

import dax.api_lib.WebWalkerServerApi;
import dax.api_lib.models.DaxCredentials;
import dax.api_lib.models.DaxCredentialsProvider;
import dax.teleports.Teleport;
import org.apache.commons.lang3.StringUtils;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Combat;
import org.tribot.api2007.Game;
import org.tribot.api2007.Skills;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.*;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Login;
import org.tribot.script.sdk.painting.MouseSplinePaint;
import scripts.Data.Vars;
import scripts.Tasks.MakeTabs.MakeTabs;
import scripts.Tasks.Task;
import scripts.Tasks.TaskSet;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@ScriptManifest(name = "cOrbCharger & Tabs", authors = {"Cass2186"}, category = "Testing")
public class cOrbCharger extends Script implements Starting, Arguments, Painting, Ending, MessageListening07 {

    public static AtomicBoolean isRunning = new AtomicBoolean(true);
    public static String status = "Initializing";

    public int nextStaminaPotionUse = General.randomSD(50, 85, 75, 5);

    Skills.SKILLS currentSkill = Skills.SKILLS.STRENGTH;


    public void populateInitialMap(){
        Log.log("[Debug]: Populating intial skills xp HashMap");
        for (Skills.SKILLS s : Skills.SKILLS.values() ){
            Vars.get().skillStartXpMap.put(s, s.getXP());
        }
    }

    public HashMap<Skills.SKILLS, Integer> getXpMap(){
        HashMap<Skills.SKILLS, Integer> map = new HashMap<>();
        for (Skills.SKILLS s : Skills.SKILLS.values() ){
            int startXp =  Vars.get().skillStartXpMap.get(s);
            if (s.getXP() > startXp){
                map.put(s,s.getXP() - startXp );
            }
        }

        return map;
    }



    final int startLevel = Skills.getActualLevel(currentSkill);
    final int startXp = Skills.getXP(currentSkill);
    int gainedXp = 0;
    int gainedLvl = 0;
    int currentXp = Skills.getXP(currentSkill);


    @Override
    public void run() {
        populateInitialMap();
        TaskSet tasks;
        tasks = new TaskSet(
              new MakeTabs()

        );


        isRunning.set(true);
        while (isRunning.get()) {
            General.sleep(50, 150);
            if (!Login.isLoggedIn())
                break;

            Task task = tasks.getValidTask();
            if (task != null) {
                status = task.toString();
                task.execute();

            }

        }

    }

    @Override
    public void passArguments(HashMap<String, String> hashMap) {
        String scriptSelect = hashMap.get("custom_input");
        String clientStarter = hashMap.get("autostart");
        String input = clientStarter != null ? clientStarter : scriptSelect;
        General.println("[Debug]: Argument entered: " + input);

        for (String arg : input.split(";")) {
            try {


            } catch (Exception e) {
                e.printStackTrace();
                General.println("[Error]: Args are incorrect", Color.RED);
            }
        }
    }


    @Override
    public void onPaint(Graphics g) {
        int currentLvl = Skills.getActualLevel(currentSkill);
        gainedLvl = currentLvl - startLevel;
        gainedXp = Skills.getXP(currentSkill) - startXp;
        HashMap<Skills.SKILLS, Integer> xpMap = getXpMap();
        double timeRan = getRunningTime();
        double timeRanMin = (timeRan / 3600000);

        List<String> myString = new ArrayList<>(Arrays.asList(
                "cNMZ",
                "Running For: " + Timing.msToString(getRunningTime()),
                "Task: " + status,
                "Drinking Absorption at: " + Vars.get().drinkAbsorptionAt,
                "Eating rockcake at: " + Vars.get().eatRockCakeAt
                //   "Vars.get().add:  " + Vars.get().add
        ));
        for (Skills.SKILLS s : xpMap.keySet()){
            int startLvl =  Skills.getLevelByXP(Vars.get().skillStartXpMap.get(s));
            int xpHr = (int) (xpMap.get(s) / timeRanMin);
            long xpToLevel1 = Skills.getXPToLevel(s, s.getActualLevel() + 1);
            long ttl = (long) ( xpToLevel1 / ((xpMap.get(s) / timeRan)));
            int gained = s.getActualLevel() - startLvl;
            if (gained >0) {
                myString.add(StringUtils.capitalize(s.toString().toLowerCase(Locale.ROOT))
                        + " [" + s.getActualLevel() + "(" + gained + ")]: "
                        + Utils.addCommaToNum(xpMap.get(s)) + "xp (" + Utils.addCommaToNum(xpHr) + "/hr) " +
                        "|| TNL: "
                        + Timing.msToString(ttl)
                );
            } else{
                myString.add(StringUtils.capitalize(s.toString().toLowerCase(Locale.ROOT))
                        + " [" + s.getActualLevel() + "]: "
                        + Utils.addCommaToNum(xpMap.get(s)) + "xp (" + Utils.addCommaToNum(xpHr) + "/hr) " +
                        "|| TNL: "
                        + Timing.msToString(ttl)
                );
            }
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

        if (!Combat.isAutoRetaliateOn())
            Combat.setAutoRetaliate(true);

    }

    @Override
    public void onEnd() {
        HashMap<Skills.SKILLS, Integer> xpMap = getXpMap();
        if (Vars.get().skillStartXpMap == null)
            populateInitialMap();

        for (Skills.SKILLS s : Skills.SKILLS.values()) {
            int startXp = Vars.get().skillStartXpMap.get(s);
            if (s.getXP() > startXp) {
                Log.debug("[Ending]: Gained " + (s.getXP() - startXp) + " " + s + " exp");
            }
        }

        General.println("[Ending]: Runtime " + Timing.msToString(getRunningTime()));
    }

    @Override
    public void serverMessageReceived(String message) {

    }
}

