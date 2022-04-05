package scripts;

import dax.api_lib.WebWalkerServerApi;
import dax.api_lib.models.DaxCredentials;
import dax.api_lib.models.DaxCredentialsProvider;
import dax.teleports.Teleport;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.Game;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.Arguments;
import org.tribot.script.interfaces.Ending;
import org.tribot.script.interfaces.Painting;
import org.tribot.script.interfaces.Starting;
import org.tribot.script.sdk.*;
import org.tribot.script.sdk.antiban.PlayerPreferences;
import org.tribot.script.sdk.painting.template.basic.BasicPaintTemplate;
import org.tribot.script.sdk.painting.template.basic.PaintLocation;
import org.tribot.script.sdk.painting.template.basic.PaintRows;
import org.tribot.script.sdk.painting.template.basic.PaintTextRow;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.script.TribotScript;
import org.tribot.script.sdk.types.Widget;
import scripts.Data.Const;
import scripts.Data.Paint;
import scripts.Data.Vars;
import scripts.ScriptUtils.CassScript;
import scripts.Steps.*;
import scripts.Tasks.Task;
import scripts.Tasks.TaskSet;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@ScriptManifest(name = "cTutorial Island", authors = {"Cass2186"}, category = "Testing")
public class cTutorialIsland extends CassScript implements TribotScript {

    public static AtomicBoolean isRunning = new AtomicBoolean(true);
    public static String status = "Initializing";


    @Override
    public void execute(String args) {
        super.initializeDax();

        AntiBan.create();

        Mouse.setSpeed(Vars.get().mouseSpeed);
        Log.info("Setting mouse speed to " + Vars.get().mouseSpeed);

        Teleport.blacklistTeleports(Teleport.values());

        double preference = PlayerPreferences.preference(
                "antiban.double", g -> g.uniform(0.2, 1.0));

        Log.info("Unique Antiban Modifier is: " + preference);
        Utils.FACTOR = preference;

        TaskSet tasks = new TaskSet(
                new MakeCharacter(),
                new SurvivalGuide(),
                new CookingGuide(),
                new QuestGuide(),
                new MiningGuide(),
                new CombatInstructor(),
                new BankTask(),
                new PrayerTask(),
                new MagicTask()
        );

        Paint.startPaint();

        isRunning.set(true);
        while (isRunning.get()) {
            Waiting.waitUniform(Vars.get().minLoopSleep, Vars.get().maxLoopSleep);

            handleSettings();

            Optional<Widget> first = Query.widgets().indexEquals(263, 1).findFirst();
            if (first.isPresent() && !first.get().isVisible() && !ChatScreen.isOpen()) {
                Log.debug("Clicking Continue failsafe");
                Keyboard.typeString(" ");
            }

            Task task = tasks.getValidTask();
            if (task != null) {
                status = task.toString();
                task.execute();
            }

            if (Game.getSetting(Const.GAME_SETTING) == 1000)
                break;
        }
    }


    private void handleSettings() {
        if (Game.getSetting(Const.GAME_SETTING) >= 10) {
            if (Options.isRoofsEnabled()) {
                Log.debug("Disabling Roofs");
                Options.setRemoveRoofsEnabled(true);
                Widgets.closeAll();
            }
            if (Options.isAnySoundOn()) {
                Log.debug("Disabling Sounds");
                Options.turnAllSoundsOff();
            }
        }
    }



}
