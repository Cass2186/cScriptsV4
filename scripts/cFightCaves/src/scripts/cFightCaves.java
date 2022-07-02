package scripts;

import org.apache.commons.lang3.StringUtils;
import org.tribot.script.interfaces.MessageListening07;
import org.tribot.script.sdk.*;
import org.tribot.script.sdk.input.Mouse;
import org.tribot.script.sdk.script.ScriptConfig;
import org.tribot.script.sdk.script.TribotScript;
import org.tribot.script.sdk.script.TribotScriptManifest;
import scripts.Data.Paint;
import scripts.Data.Rotations;
import scripts.Data.Vars;
import scripts.Data.Wave;
import scripts.ScriptUtils.CassScript;
import scripts.Tasks.*;


@TribotScriptManifest(author = "Cass2186", name = "cFightCaves", category = "Testing")
public class cFightCaves extends CassScript implements TribotScript {

    GenerateMap map = new GenerateMap();

    @Override
    public void configure(ScriptConfig config) {
        TribotScript.super.configure(config);
    }

    @Override
    public void execute(String args) {
        AntiBan.create();
        super.initializeDax();
        Mouse.setClickMethod(Mouse.ClickMethod.TRIBOT_DYNAMIC);

        Paint.addMainPaint();
        //Tasks
        TaskSet tasks = new TaskSet(
                new StartCavesTask(),
                new Attack(),
                new EatTask(),
                new PrayerTask(),
                new DrinkPotion(),
                new SafeSpotTask()
        );
        MessageListening.addServerMessageListener(m -> {
            if (m.contains("defeated!")) {
                Log.error("Died, ending");
                throw new NullPointerException();
            } else if (m.contains("Wave:")) {

                if (Rotations.getRotation() == 0) {
                    Rotations.setRotation();
                }
                String message = m.replace((char) 160, ' ');
                Wave.setCurrentWave(Integer.parseInt(StringUtils.substringBetween(message, " ", "<")));
                Log.info("Wave is " + Wave.getCurrentWave());
                if (Wave.getCurrentWave() % 15 == 0) {
                    //Wave.sendWaveWebhook();
                }
            }
        });

        while (super.isRunning.get()) {
            if (GameState.isInInstance() &&  Vars.get().nePath == null){
                Log.warn("Making map, started in instance");
                map.execute();
            }
            //fast loop
            Waiting.waitNormal(35, 5);

            Task task = tasks.getValidTask();
            if (task != null) {
                Vars.get().status = task.toString();
                task.execute();
            }
        }
    }



}
