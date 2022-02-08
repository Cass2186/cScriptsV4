package scripts;

import dax.api_lib.WebWalkerServerApi;
import dax.api_lib.models.DaxCredentials;
import dax.api_lib.models.DaxCredentialsProvider;
import dax.teleports.Teleport;
import org.apache.commons.lang3.StringUtils;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.Game;
import org.tribot.api2007.GroundItems;
import org.tribot.api2007.Projection;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSGroundItem;
import org.tribot.api2007.types.RSItemDefinition;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.Arguments;
import org.tribot.script.interfaces.Ending;
import org.tribot.script.interfaces.Painting;
import org.tribot.script.interfaces.Starting;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.types.GroundItem;
import scripts.API.AntiPKThread;
import scripts.Data.Vars;
import scripts.Tasks.*;
import scripts.rsitem_services.GrandExchange;


import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@ScriptManifest(name = "cCombat v1.5", authors = {"Cass2186"}, category = "Testing")

public class cCombat extends Script implements Painting, Starting, Ending, Arguments {


    public static AtomicBoolean isRunning = new AtomicBoolean(true);
    public static String status = "Initializing";
    public static java.util.List<Task> taskList = new ArrayList<>();

    //AntiPKThread pkObj = new AntiPKThread("Pk thread");

    @Override
    public void run() {
        TaskSet tasks = new TaskSet(
                new AttackNpc(),
                new LootItems(),
                new RechargePrayer(),
                new MoveToArea(),
                new WorldHop(),
                new EatDrink(),
               new Bank()


        );
      //  AntiPKThread pkObj = new AntiPKThread("Pk thread");
    //    pkObj.start();
        Mouse.setSpeed(250);
      //  AntiPKThread.scrollToWorld(AntiPKThread.nextWorld);
          isRunning.set(true);
        while (isRunning.get()) {
       //    Log.log("woo");
            General.sleep(20, 40);
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
                    if (s.length > 1){
                        General.println("[Args]: Target is "+ s[1]);
                        Vars.get().targets = new String[]{s[1]};
                    }
                }



            } catch (Exception e) {
                e.printStackTrace();
                General.println("[Error]: Args are incorrect", Color.RED);
            }
        }
    }

    public RSGroundItem getLootItem() {

        RSGroundItem[] groundItems = GroundItems.getAll();

        if (groundItems.length > 0) {
            for (int i = 0; i < groundItems.length; i++) {
                RSItemDefinition def = groundItems[i].getDefinition();
                int id = groundItems[i].getID();

                if (def == null)
                    return null;

                if (def.isNoted())
                    id = def.getID() - 1;


                if (GrandExchange.getPrice(id) > Vars.get().minLootValue) {


                    if (!def.getName().contains("Burnt bones")
                            && !def.getName().contains("ashes")) {
                        return groundItems[i];

                    }
                } else if (def.isStackable() || def.isNoted()) {

                    int individualPrice = GrandExchange.getPrice(id);
                    int amount = groundItems[i].getStack();
                    int value = individualPrice * amount;

                    if (value > Vars.get().minLootValue) {

                        if (groundItems[i].isClickable())
                            return groundItems[i];
                    }
                }
            }
        }

        return null;
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


    @Override
    public void onPaint(Graphics g) {
        double timeRan = getRunningTime();
        double timeRanMin = (timeRan / 3600000);
        List<String>
                myString = new ArrayList<>(Arrays.asList(
                "cCombat v2",
                "Running For: " + Timing.msToString(getRunningTime()),
                "Task: " + status,
                "Loot Value: " +Utils.addCommaToNum(Vars.get().lootValue) + " || hr: " +
                        Utils.addCommaToNum(Math.round(Vars.get().lootValue/timeRanMin))
        ));
        Optional<GroundItem> item = LootItems.getLootItem();
        if (item.isPresent()){
            Polygon p = Projection.getTileBoundsPoly(item.get().getLegacyRSGroundItem().getPosition(), 0);
            g.drawPolygon(p);
        }
        HashMap<Skills.SKILLS, Integer> xpMap = getXpMap();
        for (Skills.SKILLS s : xpMap.keySet()) {
            int startLvl = Skills.getLevelByXP(Vars.get().skillStartXpMap.get(s));
            int xpHr = (int) (xpMap.get(s) / timeRanMin);
            long xpToLevel1 = Skills.getXPToLevel(s, s.getActualLevel() + 1);
            long ttl = (long) (xpToLevel1 / ((xpMap.get(s) / timeRan)));
            int gained = s.getActualLevel() - startLvl;
            if (gained > 0) {
                myString.add(
                        StringUtils.capitalize(s.toString().toLowerCase(Locale.ROOT))
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

        PaintUtil.createPaint(g, myString.toArray(String[]::new));
    }

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
        Log.log("Loot: " + Vars.get().lootValue);
    }
}
