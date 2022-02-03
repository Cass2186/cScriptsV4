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
import scripts.NmzData.Const;
import scripts.NmzData.Vars;
import scripts.Tasks.*;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


@ScriptManifest(name = "cNMZ v2.1", authors = {"Cass2186"}, category = "Testing")
public class cNMZ extends Script implements Starting, Arguments, Painting, Ending, MessageListening07 {

    public static AtomicBoolean isRunning = new AtomicBoolean(true);
    public static String status = "Initializing";
    public static String gameSetting = "Initializing";
    public static int gameSettingInt = 0;
    public static String currentQuest = "Initializing";
    public java.util.List<Task> taskList = new ArrayList<>();
    public java.util.List<Quests> questList = new ArrayList<>();
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
                new Afk(),
                new AttackNpc(),
                new DrinkPotion(),
                new EnterDream()

        );


        isRunning.set(true);
        while (isRunning.get()) {
            General.sleep(50, 150);
            if (!Game.isInInstance())
                break;
            Task task = tasks.getValidTask();
            if (task != null) {
                status = task.toString();
                task.execute();
            }
            Log.log("[Debug]: Vars.get().rockcakeAt " + Vars.get().eatRockCakeAt);

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
        if (Vars.get().usingOverloadPots){
            myString.add("Overload Timer: " + Timing.msToString(Vars.get().overloadTimer.getRemaining()));
        }
        int points = ( Utils.getVarBitValue(Varbits.NMZ_POINTS.getId()) - Const.STARTING_NMZ_POINTS);
        int pointsHr = (int) (points / timeRanMin);
        myString.add("NMZ Points Gained: " + (
                Utils.addCommaToNum(points) + " (" + Utils.addCommaToNum(pointsHr) + "hr)"));
       /* myString.add("Using Super combat potions? " + Vars.get().usingSuperCombat);
        myString.add("Using Super prayer potions? " + Vars.get().usingPrayerPots);
        myString.add("Using Absorptions potions? " + Vars.get().usingAbsorptions);
        myString.add("Using Overload potions? " + Vars.get().usingOverloadPots);*/
     /*   myString.add("Experience Gained: " + Utils.addCommaToNum(gainedXp) + "xp (+"
                + gainedLvl + ") || "
                + "(" + Utils.addCommaToNum(Vars.get().xpHr) + "/hr)"
        );
        myString.add("Time to Next Level (" + (currentLvl + 1) + "): "
                + Timing.msToString(timeToLevel)
        );
*/

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

        DrinkPotion.determinePotion();
    }

    @Override
    public void onEnd() {
        General.println("[Ending]: Runtime " + Timing.msToString(getRunningTime()));
    }

    @Override
    public void serverMessageReceived(String message) {
        if (message.toLowerCase().contains("you drink some of your overload")) {
            General.println("[Debug]: Overload message recieved");
            Vars.get().overloadTimer.reset();
        }
        if (message.toLowerCase().contains("overload have worn off")) {
            General.println("[Debug]: Overload finished message recieved");
            Vars.get().overloadTimer.setEndIn(1);
        }
    }
}
