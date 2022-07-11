package scripts;

import org.apache.commons.lang3.StringUtils;
import org.tribot.api.Timing;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Player;
import org.tribot.api2007.Projection;
import org.tribot.api2007.types.RSNPC;
import org.tribot.script.Script;
import org.tribot.script.interfaces.MessageListening07;
import org.tribot.script.interfaces.Painting;
import org.tribot.script.sdk.*;
import org.tribot.script.sdk.input.Mouse;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.script.ScriptConfig;
import org.tribot.script.sdk.script.TribotScript;
import org.tribot.script.sdk.script.TribotScriptManifest;
import org.tribot.script.sdk.types.Npc;
import scripts.Data.Paint;
import scripts.Data.Rotations;
import scripts.Data.Vars;
import scripts.Data.Wave;
import scripts.ScriptUtils.CassScript;
import scripts.Tasks.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;


@TribotScriptManifest(author = "Cass2186", name = "cFightCaves", category = "Testing")
public class cFightCaves extends CassScript implements TribotScript, Painting{

    GenerateMap map = new GenerateMap();
    @Override
    public void configure(ScriptConfig config) {
       TribotScript.super.configure(config);
 }

    @Override
    public void execute(String args) {
        AntiBan.create();
      //  super.initializeDax();
        Mouse.setClickMethod(Mouse.ClickMethod.TRIBOT_DYNAMIC);
        Mouse.setSpeed(175);
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

        while (isRunning.get()) {
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

    @Override
    public void onPaint(Graphics g) {
        Vars.get().ITALY_ROCK_WEST.ifPresent(tile ->  g.drawPolygon(
                Projection.getTileBoundsPoly(Utils.getRSTileFromWorldTile(tile.toWorldTile()), 0)));
        Vars.get().ITALY_ROCK_EAST.ifPresent(tile ->  g.drawPolygon(
                Projection.getTileBoundsPoly(Utils.getRSTileFromWorldTile(tile.toWorldTile()), 0)));
    }
}
