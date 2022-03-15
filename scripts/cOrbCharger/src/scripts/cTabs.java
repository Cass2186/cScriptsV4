package scripts;

import dax.api_lib.WebWalkerServerApi;
import dax.api_lib.models.DaxCredentials;
import dax.api_lib.models.DaxCredentialsProvider;
import dax.teleports.Teleport;
import org.apache.commons.lang3.StringUtils;
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
import scripts.Tasks.MakeTabs.UnnoteClay;
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
                .row(template.toBuilder().label("Task").value(() -> status).build())
                .row(template.toBuilder().label("Magic").value(() -> getXpGainedString()).build())
                //.row(template.toBuilder().label("Test").value("ing").onClick(() -> Log.log("CLICKED!")).build())
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
        TaskSet tasks = new TaskSet(new MakeTabs(), new UnnoteClay());
        isRunning.set(true);

        Vars.get().startMagicLevel = Skill.MAGIC.getCurrentLevel();
        Vars.get().startMagicXp = Skill.MAGIC.getXp();

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


    public static String getXpGainedString() {
        int currentLvl = Skill.MAGIC.getActualLevel();
        int gainedLvl = currentLvl - Vars.get().startMagicLevel;
        int gainedXp = Skill.MAGIC.getXp() - Vars.get().startMagicXp;

        double timeRan = System.currentTimeMillis() - Vars.get().startTime;
        double timeRanMin = (timeRan / 3600000);

        int xpHr = (int) (gainedXp / timeRanMin);
        long xpToLevel1 = Skill.MAGIC.getCurrentXpToNextLevel();
        long ttl = (long) (xpToLevel1 / ((gainedXp / timeRan)));


        if (gainedLvl > 0) {
           return  "[" + currentLvl + " (+" + gainedLvl + ")]: "
                    + Utils.addCommaToNum(gainedXp) + "xp (" + Utils.addCommaToNum(xpHr) + "/hr) " +
                    "|| TNL: "
                    + Utils.getRuntimeString(ttl);
        } else {
            return "[" + currentLvl + "]: "
                    + Utils.addCommaToNum(gainedXp) + "xp (" + Utils.addCommaToNum(xpHr) + "/hr) " +
                    "|| TNL: "
                    + Utils.getRuntimeString(ttl);
        }
    }


}

