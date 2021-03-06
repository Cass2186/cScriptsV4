package scripts;

import dax.api_lib.WebWalkerServerApi;
import dax.api_lib.models.DaxCredentials;
import dax.api_lib.models.DaxCredentialsProvider;
import dax.api_lib.models.RunescapeBank;
import dax.teleports.Teleport;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.Skills;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.*;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Options;
import org.tribot.script.sdk.WorldHopper;
import scripts.Data.Paint;
import scripts.Data.Vars;
import scripts.Listeners.PkObserver;
import scripts.Tasks.*;
import scripts.Tasks.Spidines.KillSpidine;
import scripts.Tasks.Spidines.SpawnSpidine;
import scripts.Tasks.UndeadDruids.RechargePrayer;


import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@ScriptManifest(name = "cCombat v1.5", authors = {"Cass2186"}, category = "Testing")

public class cCombat extends Script implements Starting, Ending, Arguments, MessageListening07, Breaking {

    public static AtomicBoolean isRunning = new AtomicBoolean(true);
    public static String status = "Initializing";

    //AntiPKThread pkObj = new AntiPKThread("Pk thread");

    private void initializeListeners() {
        PkObserver pkObserver = new PkObserver(() -> true);
        pkObserver.start();
    }

    @Override
    public void run() {
        TaskSet tasks = new TaskSet(
                new AttackNpc(),
                new LootItems(),
                new RechargePrayer(),
                new MoveToArea(),
                new WorldHop(),
                new EatDrink(),
                new Bank(),
                new AlchItems()


        );
        Paint.setPaint();

        if (Vars.get().killingSpidines){
            tasks = new TaskSet(
                    new SpawnSpidine(),
                    new KillSpidine()
            );
        }
        //  AntiPKThread pkObj = new AntiPKThread("Pk thread");
        //    pkObj.start();
        Mouse.setSpeed(Utils.random(140,180));
        //  AntiPKThread.scrollToWorld(AntiPKThread.nextWorld);
        isRunning.set(true);
        initializeListeners();
        Options.AttackOption.setNpcAttackOption(Options.AttackOption.LEFT_CLICK_WHERE_AVAILABLE);

        while (isRunning.get()) {
            General.sleep(40, 60);
            if (!WorldHopper.isInMembersWorld())
                break;

            Utils.forceDownwardCameraAngle();
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

                if (arg.toLowerCase().contains("target:")) {
                    String[] s = arg.split("target:");
                    if (s.length > 1) {
                        General.println("[Args]: Target is " + s[1]);
                        Vars.get().targets = List.of(s[1]);
                    }
                }
                if (arg.toLowerCase().contains("scarab")) {
                    Log.debug("Killing Scarab mages");
                    Vars.get().killingScarabs = true;
                    Vars.get().killingUndeadDruids = false;
                } else if (arg.toLowerCase().contains("spidine")) {
                    Log.debug("Killing Spidines");
                    Vars.get().killingScarabs = false;
                    Vars.get().killingUndeadDruids = false;
                    Vars.get().killingSpidines = true;
                } else {
                    Log.debug("New Target = Undead Druid");
                    Vars.get().targets = List.of("Undead Druid");
                    Vars.get().minLootValue = 650;
                }


            } catch (Exception e) {
                e.printStackTrace();
                General.println("[Error]: Args are incorrect", Color.RED);
            }
        }
    }

    public static void populateInitialMap() {
        Log.log("[Debug]: Populating intial skills xp HashMap");
        for (Skills.SKILLS s : Skills.SKILLS.values()) {
            Vars.get().skillStartXpMap.put(s, s.getXP());
        }
    }

    public HashMap<Skills.SKILLS, Integer> getXpMap() {
        HashMap<Skills.SKILLS, Integer> map = new HashMap<>();
        if (Vars.get().skillStartXpMap == null)
            populateInitialMap();

        for (Skills.SKILLS s : Skills.SKILLS.values()) {
            int startXp = Vars.get().skillStartXpMap.get(s);
            if (s.getXP() > startXp) {
                map.put(s, s.getXP() - startXp);
                //   System.out.println("updating xp map & dax");
            }
        }

        return map;
    }

   /*
    @Override
    public void onPaint(Graphics g) {
        double timeRan = ScriptTimer.getRuntime();
        double timeRanMin = (timeRan / 3600000);
        List<String>
                myString = new ArrayList<>(Arrays.asList(
                "cCombat v2",
                "Running For: " + ScriptTimer.getRuntimeString(),
                "Task: " + status,
                "Loot Value: " + Utils.addCommaToNum(Vars.get().lootValue) + " || hr: " +
                        Utils.addCommaToNum(Math.round(Vars.get().lootValue / timeRanMin))
        ));



        Collection<PaintRow> rows = new ArrayList<>();

        HashMap<Skills.SKILLS, Integer> xpMap = getXpMap();
        for (Skills.SKILLS s : xpMap.keySet()) {
            int startLvl = Skills.getLevelByXP(Vars.get().skillStartXpMap.get(s));
            int xpHr = (int) (xpMap.get(s) / timeRanMin);
            long xpToLevel1 = Skills.getXPToLevel(s, s.getActualLevel() + 1);
            long ttl = (long) (xpToLevel1 / ((xpMap.get(s) / timeRan)));
            int gained = s.getActualLevel() - startLvl;
            if (gained > 0) {
                //  String txt =
                myString.add(
                        StringUtils.capitalize(s.toString().toLowerCase(Locale.ROOT))
                                + " [" + s.getActualLevel() + " (+" + gained + ")]: "
                                + Utils.addCommaToNum(xpMap.get(s)) + "xp (" + Utils.addCommaToNum(xpHr) + "/hr) " +
                                "|| TNL: "
                                + Timing.msToString(ttl)
                );
                // rows.add(template.toBuilder().label(txt).build());
            } else {
                //String txt =
                myString.add(
                        StringUtils.capitalize(s.toString().toLowerCase(Locale.ROOT))
                                + " [" + s.getActualLevel() + "]: "
                                + Utils.addCommaToNum(xpMap.get(s)) + "xp (" + Utils.addCommaToNum(xpHr) + "/hr) " +
                                "|| TNL: "
                                + Timing.msToString(ttl)
                );
                //  rows.add(template.toBuilder().label(txt).build());
            }
        }

        //

        PaintUtil.createPaint(g, myString.toArray(String[]::new));
    }*/

    @Override
    public void onStart() {
        AntiBan.create();
        populateInitialMap();
        Teleport.blacklistTeleports(Teleport.RING_OF_WEALTH_MISCELLANIA, Teleport.RING_OF_WEALTH_FALADOR);

        WebWalkerServerApi.getInstance().setDaxCredentialsProvider(new DaxCredentialsProvider() {
            @Override
            public DaxCredentials getDaxCredentials() {
                return new DaxCredentials("sub_DPjXXzL5DeSiPf", "PUBLIC-KEY");
            }
        });

        Utils.setCameraZoomAboveDefault();
    }

    @Override
    public void onEnd() {
        General.println("[Ending]: Runtime: " + Timing.msToString(this.getRunningTime()), Color.RED);
        HashMap<Skills.SKILLS, Integer> xpMap = getXpMap();
        if (Vars.get().skillStartXpMap == null)
            populateInitialMap();

        for (Skills.SKILLS s : Skills.SKILLS.values()) {
            int startXp = Vars.get().skillStartXpMap.get(s);
            if (s.getXP() > startXp) {
                Log.info("[Ending]: Gained " + (s.getXP() - startXp) + " " + s + " exp");
            }
        }
        Log.info("[Ending]: Loot Value: " + Utils.addCommaToNum(Vars.get().lootValue));
    }


    @Override
    public void serverMessageReceived(String message) {
        if (message.contains("are dead") || message.contains(" have died")) {
            Log.error("Died, ending script");
            isRunning.set(false);
            throw new NullPointerException();
        }
    }

    @Override
    public void onBreakStart(long l) {
        Log.info("Break starting, going to bank for safety");
        PathingUtil.walkToTile(RunescapeBank.VARROCK_WEST.getPosition());
    }

    @Override
    public void onBreakEnd() {

    }

}
