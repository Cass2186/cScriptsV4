package scripts;

import dax.api_lib.WebWalkerServerApi;
import dax.api_lib.models.DaxCredentials;
import dax.api_lib.models.DaxCredentialsProvider;
import org.apache.commons.lang3.StringUtils;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.Game;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSInterface;
import org.tribot.api2007.types.RSInterfaceChild;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.Ending;
import org.tribot.script.interfaces.Painting;
import org.tribot.script.interfaces.Starting;
import org.tribot.script.sdk.*;
import scripts.Data.Vars;
import scripts.Tasks.Task;
import scripts.Tasks.TaskSet;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@ScriptManifest(name = "cAutoClicker", authors = {"Cass2186"}, category = "Magic")
public class cAutoClicker extends Script implements Painting, Starting, Ending {


    public static AtomicBoolean isRunning = new AtomicBoolean(true);
    public static String status = "Initializing";
   final int startMagicXp = Skills.getXP(Skills.SKILLS.MAGIC);
    int currentMagicXP = Skills.getXP(Skills.SKILLS.MAGIC);
    public static Timer timeoutTimer = new Timer(General.random(300000, 480000)); //5-7 min

    public  boolean clickAlch() {
        RSInterfaceChild alchButton = Interfaces.get(218, 40);
        return alchButton != null && alchButton.click();
    }

    @Override
    public void run() {
        super.setLoginBotState(false);

        isRunning.set(true);
        if (!GameTab.MAGIC.isOpen())
            GameTab.MAGIC.open();


        currentMagicXP = Skills.getXP(Skills.SKILLS.MAGIC);
        while (isRunning.get()) {
            Waiting.wait(10);
            currentMagicXP = Skills.getXP(Skills.SKILLS.MAGIC);
            RSInterface alchButton = Interfaces.get(218, 40);

            if (!Inventory.contains(ItemID.NATURE_RUNE)) {
                General.println("[Debug]: Error - out of nature runes");
                break;
            }

            if (!timeoutTimer.isRunning()) {
                General.println("Script Timed Out");
                break;
            }

            if (!Login.isLoggedIn()) {
                break;
            }
            if (alchButton != null &&
                    !alchButton.getAbsoluteBounds().contains(Mouse.getPos())) {
                clickAlch();


            } else {
                General.sleep(Vars.get().clickDelay);
                Mouse.click(1);
            }
            if (Skills.getXP(Skills.SKILLS.MAGIC) > currentMagicXP) {
                //    General.println("[Debug]: resetting XP");
                currentMagicXP = Skills.getXP(Skills.SKILLS.MAGIC);
                timeoutTimer.reset();
            }
        }

    }

    @Override
    public void onPaint(Graphics g) {;
        double timeRan = getRunningTime();
        double timeRanMin = (timeRan / 3600000);
        int gainedXp =  Skills.getXP(Skills.SKILLS.MAGIC) - startMagicXp;
        List<String> myString = new ArrayList<>(
                Arrays.asList(
                "cAutoclicker",
                "Running For: " + Timing.msToString(getRunningTime()),
                "Magic Xp: " + Utils.addCommaToNum(gainedXp) +" (" + Utils.addCommaToNum((int) (gainedXp/timeRanMin))+ "/hr)"
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

        AntiBan.create();
        Vars.get().startTime = Timing.currentTimeMillis();
        Vars.get().currentTime = Timing.currentTimeMillis();
    }

    @Override
    public void onEnd() {
        Log.debug("[Ending]: Magic XP Gained: " + Utils.addCommaToNum(Skills.getXP(Skills.SKILLS.MAGIC) - startMagicXp));
    }
}
