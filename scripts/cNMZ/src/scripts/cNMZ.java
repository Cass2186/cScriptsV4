package scripts;

import dax.api_lib.WebWalkerServerApi;
import dax.api_lib.models.DaxCredentials;
import dax.api_lib.models.DaxCredentialsProvider;
import dax.teleports.Teleport;
import org.apache.commons.lang3.StringUtils;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Combat;
import org.tribot.api2007.Game;
import org.tribot.api2007.Skills;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.*;
import org.tribot.script.sdk.GameState;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Login;
import org.tribot.script.sdk.MessageListening;
import scripts.NmzData.Const;
import scripts.NmzData.Paint;
import scripts.NmzData.Vars;
import scripts.Tasks.*;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


@ScriptManifest(name = "cNMZ v2.1", authors = {"Cass2186"}, category = "Testing")
public class cNMZ extends Script implements Starting, Ending, MessageListening07 {

    public static AtomicBoolean isRunning = new AtomicBoolean(true);

    public void populateInitialMap() {
        Log.log("[Debug]: Populating intial skills xp HashMap");
        for (Skills.SKILLS s : Skills.SKILLS.values()) {
            Vars.get().skillStartXpMap.put(s, s.getXP());
        }
    }

    @Override
    public void run() {
        populateInitialMap();
        TaskSet tasks;
        tasks = new TaskSet(
                new Afk(),
                new AttackNpc(),
                new DrinkPotion(),
                new EnterDream()

        );
        Paint.initializeDetailedPaint();
        Paint.addPaint();

        isRunning.set(true);
        while (isRunning.get()) {
            General.sleep(50, 150);

            if (!Game.isInInstance())
                break;

            if (!Login.isLoggedIn())
                break;

            if (!Combat.isAutoRetaliateOn())
                Combat.setAutoRetaliate(true);

            Task task = tasks.getValidTask();
            if (task != null) {
                Vars.get().status = task.toString();
                task.execute();
            }
        }

    }


    @Override
    public void onStart() {
        AntiBan.create();
        Teleport.blacklistTeleports(Teleport.GLORY_KARAMJA, Teleport.RING_OF_WEALTH_MISCELLANIA,
                Teleport.RING_OF_DUELING_FEROX_ENCLAVE);

        WebWalkerServerApi.getInstance().setDaxCredentialsProvider(new DaxCredentialsProvider() {
            @Override
            public DaxCredentials getDaxCredentials() {
                return new DaxCredentials("sub_DPjXXzL5DeSiPf", " PUBLIC-KEY");
            }
        });

        if (!Combat.isAutoRetaliateOn())
            Combat.setAutoRetaliate(true);

        DrinkPotion.determinePotion();
    }

    @Override
    public void onEnd() {
        General.println("[Ending]: Runtime " + Timing.msToString(getRunningTime()));
    }

    private void initializeMessageListeners() {
        MessageListening.addServerMessageListener((message) -> {
                    if (message.toLowerCase().contains("you drink some of your overload")) {
                        General.println("[Debug]: Overload message recieved");
                        Vars.get().overloadTimer.reset();
                    }
                    if (message.toLowerCase().contains("overload have worn off")) {
                        General.println("[Debug]: Overload finished message recieved");
                        Vars.get().overloadTimer.setEndIn(1);
                    }
                }
        );
    }

    @Override
    public void serverMessageReceived(String message) {
        if (message.toLowerCase().contains("you drink some of your overload")) {
            General.println("[Debug]: Overload message recieved");
            Vars.get().overloadTimer.reset();
        }
        if (message.toLowerCase().contains("overload have worn off")) {
            General.println("[Debug]: Overload finished message recieved");
            Vars.get().overloadTimer.setEndIn(1);
        }
    }
}
