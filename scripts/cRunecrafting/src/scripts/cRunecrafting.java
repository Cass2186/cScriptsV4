package scripts;

import org.tribot.script.ScriptManifest;
import org.tribot.script.sdk.*;
import org.tribot.script.sdk.input.Mouse;
import org.tribot.script.sdk.painting.Painting;
import org.tribot.script.sdk.painting.template.basic.BasicPaintTemplate;
import org.tribot.script.sdk.painting.template.basic.PaintLocation;
import org.tribot.script.sdk.painting.template.basic.PaintRows;
import org.tribot.script.sdk.painting.template.basic.PaintTextRow;
import org.tribot.script.sdk.script.ScriptConfig;
import org.tribot.script.sdk.script.TribotScript;
import scripts.Data.Const;
import scripts.Data.Vars;
import scripts.ScriptUtils.CassScript;
import scripts.Tasks.*;

import java.awt.*;
import java.net.URL;

@ScriptManifest(name = "cRunecrafting", authors = {"Cass2186"}, category = "Testing")
public class cRunecrafting extends CassScript implements TribotScript {


    @Override
    public void configure(ScriptConfig config) {
        TribotScript.super.configure(config);
    }

    @Override
    public void execute(String args) {
        AntiBan.create();
        super.initializeDax();

        /**
         Paint
         */
        PaintTextRow template = PaintTextRow.builder().background(Color.darkGray.darker()).build();

        BasicPaintTemplate paint = BasicPaintTemplate.builder()
                .row(PaintRows.scriptName(template.toBuilder()))
                .row(PaintRows.runtime(template.toBuilder()))
                .row(template.toBuilder().label("Task").value(() -> Vars.get().status).build())
              //  .row(template.toBuilder().label("House").value(() -> Vars.get().mostRecentHouse).build())
                //.row(template.toBuilder().label("Tab").value(() -> Vars.get().selectedTab.toString()).build())
                .row(template.toBuilder().label("Runecrafting").value(() -> getXpGainedString()).build())
             //   .row(template.toBuilder().label("Profit").value(() -> Vars.get().getProfitString()).build())
                .location(PaintLocation.BOTTOM_LEFT_VIEWPORT)
                .build();

        Painting.addPaint(g -> paint.render(g));

        populateInitialMap(Vars.get().skillStartXpMap);


        Vars.get().startRcLevel = Skill.RUNECRAFT.getCurrentLevel();
        Vars.get().startRcXp = Skill.RUNECRAFT.getXp();

        Mouse.setClickMethod(Mouse.ClickMethod.TRIBOT_DYNAMIC);

        //Tasks
        TaskSet tasks = new TaskSet(
                new CraftRunes(),
                new GoToRcAltar(),
                new RunecraftBank()
        );

        initializeListeners();
        isRunning.set(true);
        while (isRunning.get()) {
            Waiting.waitUniform(50, 100);


            //reset safety timer if we've gained xp since last itteration
            if (Skill.RUNECRAFT.getXp() > Vars.get().lastIterRcXp) {
                Vars.get().safetyTimer.reset();
                Vars.get().lastIterRcXp = Skill.RUNECRAFT.getXp();
            }

            // if we have not gained exp and the timer has expired, end script
            if (!Vars.get().safetyTimer.isRunning()) {
                Log.error("XP Safety timer timed out, ending");
                break;
            }

            Task task = tasks.getValidTask();
            if (task != null) {
                Vars.get().status = task.toString();
                task.execute();
            }
        }
    }

    private void initializeListeners() {
        MessageListening.addServerMessageListener(message -> {
            if (message.contains(Const.imbueOff)) {
                Vars.get().isImbueActive = false;
            } else if (message.contains(Const.imbueOn))
                Vars.get().isImbueActive = true;
        });
        /**
         Ending listener
         **/
        ScriptListening.addEndingListener(() -> {
            if (Vars.get().skillStartXpMap == null)
                populateInitialMap(Vars.get().skillStartXpMap);

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

    private static String getXpGainedString() {
        int currentLvl = Skill.RUNECRAFT.getActualLevel();
        int gainedLvl = currentLvl - Vars.get().startRcLevel;
        int gainedXp = Skill.RUNECRAFT.getXp() - Vars.get().startRcXp;

        double timeRan = System.currentTimeMillis() - Vars.get().startTime;
        double timeRanMin = (timeRan / 3600000);

        int xpHr = (int) (gainedXp / timeRanMin);
        long xpToLevel1 = Skill.RUNECRAFT.getCurrentXpToNextLevel();
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

}
