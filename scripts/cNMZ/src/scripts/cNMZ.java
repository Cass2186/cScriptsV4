package scripts;

import dax.api_lib.WebWalkerServerApi;
import dax.api_lib.models.DaxCredentials;
import dax.api_lib.models.DaxCredentialsProvider;
import dax.teleports.Teleport;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Combat;
import org.tribot.api2007.Game;
import org.tribot.api2007.Skills;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.*;
import org.tribot.script.sdk.*;
import org.tribot.script.sdk.script.ScriptRuntimeInfo;
import org.tribot.script.sdk.script.TribotScript;
import scripts.NmzData.Const;
import scripts.NmzData.Paint;
import scripts.NmzData.Vars;
import scripts.ScriptUtils.CassScript;
import scripts.ScriptUtils.ScriptTimer;
import scripts.Tasks.*;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


@ScriptManifest(name = "cNMZ v2.1", authors = {"Cass2186"}, category = "Testing")
public class cNMZ extends CassScript implements TribotScript {


    @Override
    public void execute(@NotNull String args) {
        AntiBan.create();
        super.initializeDax();

        if (!Combat.isAutoRetaliateOn())
            Combat.setAutoRetaliate(true);

        DrinkPotion.determinePotion();
        Paint.initializeDetailedPaint();
        Paint.addPaint();

        initializeMessageListeners();


        TaskSet tasks;
        tasks = new TaskSet(
                new Afk(),
                new AttackNpc(),
                new DrinkPotion(),
                new EnterDream()
        );
        Vars.get().endDreamNumber = getGameNumberFromArgs(args);
        isRunning.set(true);
        int dreams = 0;
        while (isRunning.get()) {
            Waiting.waitUniform(50, 100);

            if (!GameState.isInInstance()) {
                Log.warn("dream number = " + dreams);
                dreams++;

            }


            if (!MyPlayer.isMember() || !Login.isLoggedIn())
                break;

            if (!Combat.isAutoRetaliateOn())
                Combat.setAutoRetaliate(true);

            Task task = tasks.getValidTask();
            if (task != null) {
                Vars.get().status = task.toString();
                task.execute();
            }
            if (Vars.get().endDreamNumber < dreams) {
                Log.warn("exceeding dream number");
                break;
            }
        }

    }

    private int getGameNumberFromArgs(String args) {
        int i = 0;
        try {
            i = Integer.parseInt(args);
        } catch (IllegalArgumentException e) {
            Log.error("Illegal args, use a number without spaces from 1-10");
            Log.error(e.getMessage());
        }
        return i;
    }


    private void initializeMessageListeners() {
        MessageListening.addServerMessageListener((message) -> {
                    if (message.toLowerCase().contains("you drink some of your overload")) {
                        General.println("[Debug]: Overload message recieved");
                        Vars.get().overloadTimer.reset();
                    }
                    if (message.toLowerCase().contains("overload have worn off")) {
                        Log.info("Overload finished message recieved");
                        Vars.get().overloadTimer.setEndIn(1);
                    }
                    if (message.toLowerCase().contains("quiver")) {
                        Log.error("Ran out of ammo");
                        Vars.get().overloadTimer.setEndIn(1);
                    }
                }
        );
        ScriptListening.addEndingListener(() ->
                Log.info("[Ending]: Runtime " + ScriptTimer.getRuntimeString()));


    }


}
