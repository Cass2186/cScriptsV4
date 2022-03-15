package scripts;

import dax.api_lib.WebWalkerServerApi;
import dax.api_lib.models.DaxCredentials;
import dax.api_lib.models.DaxCredentialsProvider;
import dax.teleports.Teleport;
import org.tribot.script.ScriptManifest;

import org.tribot.script.sdk.*;

import org.tribot.script.sdk.painting.Painting;
import org.tribot.script.sdk.painting.template.basic.BasicPaintTemplate;
import org.tribot.script.sdk.painting.template.basic.PaintLocation;
import org.tribot.script.sdk.painting.template.basic.PaintRows;
import org.tribot.script.sdk.painting.template.basic.PaintTextRow;
import org.tribot.script.sdk.script.ScriptConfig;
import org.tribot.script.sdk.script.TribotScript;
import scripts.Data.Vars;
import scripts.Tasks.MakeTabs.MakeTabs;
import scripts.Tasks.Task;
import scripts.Tasks.TaskSet;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@ScriptManifest(name = "cTabs", authors = {"Cass2186"}, category = "Testing")
public class cTabs implements TribotScript {

    public static AtomicBoolean isRunning = new AtomicBoolean(true);
    public static String status = "Initializing";


    public void populateInitialMap() {
        Log.debug("[Debug]: Populating initial skills xp HashMap");
        for (Skill s : Skill.values()) {
            Vars.get().skillStartXpMap.put(s, s.getXp());
        }
    }

    public HashMap<Skill, Integer> getXpMap() {
        HashMap<Skill, Integer> map = new HashMap<>();
        for (Skill s : Skill.values()) {
            int startXp = Vars.get().skillStartXpMap.get(s);
            if (s.getXp() > startXp) {
                map.put(s, s.getXp() - startXp);
            }
        }

        return map;
    }


    @Override
    public void configure(ScriptConfig config) {
        // modify the "config" object here
    }

    @Override
    public void execute(String args) {
        AntiBan.create();

        Teleport.blacklistTeleports(Teleport.GLORY_KARAMJA, Teleport.RING_OF_WEALTH_MISCELLANIA,
                Teleport.RING_OF_DUELING_FEROX_ENCLAVE);

        WebWalkerServerApi.getInstance().setDaxCredentialsProvider(new DaxCredentialsProvider() {
            @Override
            public DaxCredentials getDaxCredentials() {
                return new DaxCredentials("sub_DPjXXzL5DeSiPf", " PUBLIC-KEY");
            }
        });

        populateInitialMap();

        /**
         Paint
         */
        PaintTextRow template = PaintTextRow.builder().background(Color.darkGray.darker()).build();

        BasicPaintTemplate paint = BasicPaintTemplate.builder()
                .row(PaintRows.scriptName(template.toBuilder()))
                .row(PaintRows.runtime(template.toBuilder()))
                .row(template.toBuilder().label("Test").value("ing").onClick(() -> Log.log("CLICKED!")).build())
                //.row(template.toBuilder().label("Resources").value(() -> this.resourcesCollected).build())
                .location(PaintLocation.BOTTOM_LEFT_VIEWPORT)
                .build();
        Painting.addPaint(g -> paint.render(g));


        /**
         Ending listener
         */
        ScriptListening.addEndingListener(() -> {
            if (Vars.get().skillStartXpMap == null)
                populateInitialMap();

            for (Skill s : Skill.values()) {
                int startXp = Vars.get().skillStartXpMap.get(s);
                if (s.getXp() > startXp) {
                    Log.debug("[Ending]: Gained " + (s.getXp() - startXp) + " " + s + " exp");
                }
            }
            Log.debug("[Ending]: Runtime " +
                    Utils.getRuntimeString(System.currentTimeMillis() - Vars.get().startTime));

        });

        /**
         Tasks
         */
        TaskSet tasks = new TaskSet(new MakeTabs());
        isRunning.set(true);

        while (isRunning.get()) {
            Waiting.waitNormal(50, 75);
            if (!Login.isLoggedIn())
                break;

            Task task = tasks.getValidTask();
            if (task != null) {
                status = task.toString();
                task.execute();
            }
        }
    }




  /*  public void onPaint(Graphics g) {
        int currentLvl = Skills.getActualLevel(currentSkill);
        gainedLvl = currentLvl - startLevel;
        gainedXp = Skills.getXP(currentSkill) - startXp;
        HashMap<Skills.SKILLS, Integer> xpMap = getXpMap();
        double timeRan = getRunningTime();
        double timeRanMin = (timeRan / 3600000);

        List<String> myString = new ArrayList<>(Arrays.asList(
                "cTabs",
                "Running For: " + Timing.msToString(getRunningTime()),
                "Task: " + status
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
*/


}

