package scripts;

import dax.api_lib.WebWalkerServerApi;
import dax.api_lib.models.DaxCredentials;
import dax.api_lib.models.DaxCredentialsProvider;
import dax.teleports.Teleport;
import lombok.SneakyThrows;
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
import scripts.GUI.GUI;
import scripts.NmzData.Const;
import scripts.NmzData.Paint;
import scripts.NmzData.Vars;
import scripts.ScriptUtils.CassScript;
import scripts.ScriptUtils.ScriptTimer;
import scripts.Tasks.*;

import java.awt.*;
import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


@ScriptManifest(name = "cNMZ v2.1", authors = {"Cass2186"}, category = "Testing")
public class cNMZ extends CassScript implements TribotScript {


    @SneakyThrows
    @Override
    public void execute(@NotNull String args) {
        AntiBan.create();
        super.initializeDax();

        DrinkPotion.determinePotion();
        //set up args if needed
        handleInventoryArgs(args);

        if (!Combat.isAutoRetaliateOn())
            Combat.setAutoRetaliate(true);

        URL lcn = new URL("https://raw.githubusercontent.com/Cass2186/cScriptsV4/main/scripts/cNMZ/src/scripts/GUI/cNMZ_GUI.fxml");
        GUI gui = new GUI(lcn);

        Log.info("Loading GUI");
        gui.show();

        while (gui.isOpen())
            Waiting.wait(500);

        Paint.initializeDetailedPaint();
        Paint.addPaint();

        initializeMessageListeners();


        TaskSet tasks;
        tasks = new TaskSet(
                new Afk(),
                new AttackNpc(),
                new DrinkPotion(),
                new EnterDream(),
                new InventorySetup()
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


    private void handleInventoryArgs(String args) {
        //set up args if needed
        if (args.toLowerCase().contains("overload")) {
            Log.info("Using overload");
            Vars.get().invRequirement = Optional.of(InventorySetup.getOverloadInvReq());
        } else {
            Vars.get().invRequirement = Optional.of(InventorySetup.getSuperCombatInv());
        }
    }


    private int getGameNumberFromArgs(String args) {
        String[] split = args.split(";");
        int i = 0;
        if (split.length > 0) {
            try {
                i = Integer.parseInt(split[0]);
            } catch (IllegalArgumentException e) {
                Log.error("Illegal args, use a number without spaces from 1-10");
                Log.error(e.getMessage());
            }

        } else {
            try {
                i = Integer.parseInt(args);
            } catch (IllegalArgumentException e) {
                Log.error("Illegal args, use a number without spaces from 1-10");
                Log.error(e.getMessage());
            }
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
