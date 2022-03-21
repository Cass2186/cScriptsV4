package scripts;

import dax.api_lib.WebWalkerServerApi;
import dax.api_lib.models.DaxCredentials;
import dax.api_lib.models.DaxCredentialsProvider;
import dax.teleports.Teleport;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.tribot.script.ScriptManifest;

import org.tribot.script.sdk.*;

import org.tribot.script.sdk.input.Mouse;
import org.tribot.script.sdk.interfaces.BreakStartListener;
import org.tribot.script.sdk.painting.Painting;
import org.tribot.script.sdk.painting.template.basic.BasicPaintTemplate;
import org.tribot.script.sdk.painting.template.basic.PaintLocation;
import org.tribot.script.sdk.painting.template.basic.PaintRows;
import org.tribot.script.sdk.painting.template.basic.PaintTextRow;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.script.ScriptConfig;
import org.tribot.script.sdk.script.TribotScript;
import org.tribot.script.sdk.types.Widget;
import scripts.Data.Const;
import scripts.Data.Tabs;
import scripts.Data.Vars;
import scripts.Tasks.MakeTabs.EnterHouse;
import scripts.Tasks.MakeTabs.MakeTabs;
import scripts.Tasks.MakeTabs.UnnoteClay;
import scripts.Tasks.Task;
import scripts.Tasks.TaskSet;
import scripts.gui.GUI;

import java.awt.*;
import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

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


    @Override
    public void configure(ScriptConfig config) {
        // modify the "config" object here
    }

    @SneakyThrows
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

        //SkillerGUI gui = new SkillerGUI(reader.lines().collect(Collectors.joining(System.lineSeparator())));
        URL lcn = new URL("https://raw.githubusercontent.com/Cass2186/cScriptsV4/main/scripts/cOrbCharger/src/scripts/gui/cTabsGui.fxml");
        GUI gui = new GUI(lcn);


        Log.debug("Loading GUI");
        gui.show();
        while (gui.isOpen())
            Waiting.wait(500);

        Vars.get().safetyTimer.reset();

        /**
         Paint
         */
        PaintTextRow template = PaintTextRow.builder().background(Color.darkGray.darker()).build();

        BasicPaintTemplate paint = BasicPaintTemplate.builder()
                .row(PaintRows.scriptName(template.toBuilder()))
                .row(PaintRows.runtime(template.toBuilder()))
                .row(template.toBuilder().label("Task").value(() -> status).build())
                .row(template.toBuilder().label("Tab").value(() -> Vars.get().selectedTab.toString()).build())
                .row(template.toBuilder().label("Magic").value(() -> getXpGainedString()).build())
                .row(template.toBuilder().label("Profit").value(() -> Vars.get().getProfitString()).build())
                .location(PaintLocation.BOTTOM_LEFT_VIEWPORT)
                .build();

        Painting.addPaint(g -> paint.render(g));

        initializeListeners();
        populateInitialMap();


        Vars.get().startMagicLevel = Skill.MAGIC.getCurrentLevel();
        Vars.get().startMagicXp = Skill.MAGIC.getXp();

        Mouse.setClickMethod(Mouse.ClickMethod.ACCURATE_MOUSE);

        //Tasks
        TaskSet tasks = new TaskSet(
                new MakeTabs(),
                new UnnoteClay(),
                new EnterHouse()
        );

        isRunning.set(true);
        while (isRunning.get()) {
            Waiting.waitUniform(50, 90);

            //check several fail safes
            if (shouldEndScript())
                break;

            //reset safety timer if we've gained xp since last itteration
            if (Skill.MAGIC.getXp() > Vars.get().lastIterMagicXp) {
                Vars.get().safetyTimer.reset();
                Vars.get().lastIterMagicXp = Skill.MAGIC.getXp();
            }

            // if we have not gained exp and the timer has expired, end script
            if (!Vars.get().safetyTimer.isRunning()) {
                Log.error("XP Safety timer timed out, ending");
                break;
            }

            Task task = tasks.getValidTask();
            if (task != null) {
                status = task.toString();
                task.execute();
            }
        }
    }


    private static String getXpGainedString() {
        int currentLvl = Skill.MAGIC.getActualLevel();
        int gainedLvl = currentLvl - Vars.get().startMagicLevel;
        int gainedXp = Skill.MAGIC.getXp() - Vars.get().startMagicXp;

        double timeRan = System.currentTimeMillis() - Vars.get().startTime;
        double timeRanMin = (timeRan / 3600000);

        int xpHr = (int) (gainedXp / timeRanMin);
        long xpToLevel1 = Skill.MAGIC.getCurrentXpToNextLevel();
        long ttl = (long) (xpToLevel1 / ((gainedXp / timeRan)));


        if (gainedLvl > 0) {
            return "[" + currentLvl + " (+" + gainedLvl + ")]: "
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

    private void initializeListeners() {
        MessageListening.addServerMessageListener(message -> {
            if (message.contains("can't reach that")) {
                Vars.get().messageCount++;
                Log.error("Can't reach failsafe: " + Vars.get().messageCount);
            }
        });
        /**
         Ending listener
         **/
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
        // reset timer when script is paused so we don't time out
        ScriptListening.addPauseListener(() -> Vars.get().safetyTimer.reset());
    }

    private boolean shouldEndScript() {
        if (!Login.isLoggedIn()) {
            return true;
        } else if (!Inventory.contains(ItemID.COINS_995)) {
            Log.error("Missing coins, ending");
            return true;
        } else if (!Vars.get().selectedTab.canCraftTab() || !UnnoteClay.hasAnyClay()) {
            Log.error("Missing runes/staff for tab or missing soft clay, ending");
            return true;
        } else if (Vars.get().messageCount >= 3) {
            Log.error("Can't reach failsafe > 3, failing");
            return true;
        }
        return false;
    }

}

