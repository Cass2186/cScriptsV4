package scripts;

import dax.api_lib.WebWalkerServerApi;
import dax.api_lib.models.DaxCredentials;
import dax.api_lib.models.DaxCredentialsProvider;
import org.apache.commons.lang3.StringUtils;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.Game;
import org.tribot.api2007.Skills;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.Painting;
import org.tribot.script.interfaces.Starting;
import org.tribot.script.sdk.Waiting;
import scripts.Data.Vars;
import scripts.Nodes.Bank;
import scripts.Nodes.BirdHouses;
import scripts.Nodes.Restock;
import scripts.Nodes.Wait;
import scripts.Tasks.Task;
import scripts.Tasks.TaskSet;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@ScriptManifest(name = "cBirdhouses", authors = {"Cass2186"}, category = "Hunter")
public class cBirdhouses extends Script implements Painting, Starting {


    public static AtomicBoolean isRunning = new AtomicBoolean(true);
    public static String status = "Initializing";



    @Override
    public void run() {
        TaskSet tasks;
        tasks = new TaskSet(
                new Restock(),
                new Bank(),
                new BirdHouses(),
                new Wait()
        );

        isRunning.set(true);
        while (isRunning.get()) {
            Waiting.waitUniform(50, 150);

            Task task = tasks.getValidTask();
            if (task != null) {
                status = task.toString();
                task.execute();
            }

        }

    }

    @Override
    public void onPaint(Graphics g) {;
        double timeRan = getRunningTime();
        double timeRanMin = (timeRan / 3600000);

        List<String> myString = new ArrayList<>(Arrays.asList(
                "cBirdhouses",
                "Running For: " + Timing.msToString(getRunningTime()),
                "Task: " + status
        ));

        PaintUtil.createPaint(g, myString.toArray(String[]::new));
    }

    @Override
    public void onStart() {
        WebWalkerServerApi.getInstance().setDaxCredentialsProvider(new DaxCredentialsProvider() {
            @Override
            public DaxCredentials getDaxCredentials() {
                return new DaxCredentials("sub_H0C2eULZfbWeoF ", "acb35610-d868-4ce8-8797-0d2e659f87f4");
            }
        });

        int b = General.random(120, 160);
        Mouse.setSpeed(b);
        General.println("[Debug]: Setting mouse speed to " + b);

        super.setLoginBotState(false);

        AntiBan.create();
        Vars.get().startTime = Timing.currentTimeMillis();
        Vars.get().currentTime = Timing.currentTimeMillis();
    }
}
