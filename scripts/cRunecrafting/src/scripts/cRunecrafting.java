package scripts;

import org.tribot.script.ScriptManifest;
import org.tribot.script.sdk.*;
import org.tribot.script.sdk.input.Mouse;
import org.tribot.script.sdk.painting.Painting;
import org.tribot.script.sdk.painting.template.basic.BasicPaintTemplate;
import org.tribot.script.sdk.painting.template.basic.PaintLocation;
import org.tribot.script.sdk.painting.template.basic.PaintRows;
import org.tribot.script.sdk.painting.template.basic.PaintTextRow;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.script.ScriptConfig;
import org.tribot.script.sdk.script.TribotScript;
import scripts.Data.Const;
import scripts.Data.Paint;
import scripts.Data.RunecraftItems;
import scripts.Data.Vars;
import scripts.ScriptUtils.CassScript;
import scripts.ScriptUtils.ScriptTimer;
import scripts.Tasks.*;
import scripts.Tasks.AbyssTasks.GetPouches;
import scripts.Tasks.BloodCrafting.ChipEssence;
import scripts.Tasks.BloodCrafting.GetDenceEssence;
import scripts.Tasks.BloodCrafting.GoToBloodAltar;
import scripts.Tasks.BloodCrafting.GoToDarkAltar;

import java.awt.*;
import java.net.URL;
import java.util.Locale;

@ScriptManifest(name = "cRunecrafting", authors = {"Cass2186"}, category = "Testing")
public class cRunecrafting extends CassScript implements TribotScript {


    @Override
    public void configure(ScriptConfig config) {
        TribotScript.super.configure(config);
    }

    @Override
    public void execute(String args) {
        if (args.toLowerCase().contains("blood")){
            Log.info("Blood rune crafting");
            Vars.get().bloodRuneCrafting = true;
        } else if (args.toLowerCase().contains("abyss")) {
            Log.info("[Args]: Abyss crafting");
            Vars.get().abyssCrafting = true;
        }
        else if (args.toLowerCase().contains("pouch")) {
            Log.info("[Args]: Pouches ");
            Vars.get().collectPouches = true;
        }
        AntiBan.create();
        super.initializeDax();

        populateInitialMap(Vars.get().skillStartXpMap);
        //  Paint
        Paint.addMainPaint();

        Vars.get().startRcLevel = Skill.RUNECRAFT.getCurrentLevel();
        Vars.get().startRcXp = Skill.RUNECRAFT.getXp();

        Mouse.setSpeed(150);
        Mouse.setClickMethod(Mouse.ClickMethod.TRIBOT_DYNAMIC);
        //Tasks
        TaskSet tasks = new TaskSet(
                new CraftRunes(),
                new GoToRcAltar(),
                new RunecraftBank(),
                new RepairPouches(),
                new GetPouches()
        );

        if (Vars.get().bloodRuneCrafting){
            tasks = new TaskSet(
                    new GetDenceEssence(),
                    new GoToBloodAltar(),
                    new GoToDarkAltar()
            );
            Vars.get().safetyTimer =  new Timer(300000);
        }
        ScriptTimer.resetRuntime();
        initializeListeners();
        isRunning.set(true);
        Utils.setCameraZoomAboveDefault();

        Vars.get().currentRune = RunecraftItems.getCurrentItem();

        while (isRunning.get()) {
            Waiting.waitUniform(40, 70);
            //reset safety timer if we've gained xp since last itteration
            if (Skill.RUNECRAFT.getXp() > Vars.get().lastIterRcXp || Vars.get().collectPouches) {
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
        //imbue message listener. there might be a varbit for this
        //TODO check for varbit
        MessageListening.addServerMessageListener(message -> {
            if (message.contains(Const.imbueOff)) {
                Vars.get().isImbueActive = false;
            } else if (message.contains(Const.imbueOn))
                Vars.get().isImbueActive = true;
        });

        // Ending listener
        ScriptListening.addEndingListener(() -> {
            if (Vars.get().skillStartXpMap == null)
                populateInitialMap(Vars.get().skillStartXpMap);

            for (Skill s : Skill.values()) {
                int startXp = Vars.get().skillStartXpMap.get(s);
                if (s.getXp() > startXp) {
                    String str =
                            String.format("Script Ended. Gained %s %s xp (%s /hr)",
                                    Utils.addCommaToNum((s.getXp() - startXp)), s,
                                    Utils.addCommaToNum(PaintUtil.getXpHr(s, startXp, Vars.get().startTime)));
                    Log.info(str);
                }
            }
            Log.info("[Ending]: Runtime " +
                    Utils.getRuntimeString(System.currentTimeMillis() - Vars.get().startTime));

        });
        // reset timer when script is paused so we don't time out
        ScriptListening.addPauseListener(() -> Vars.get().safetyTimer.reset());
    }



}
