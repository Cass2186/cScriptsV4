package scripts;

import dax.api_lib.WebWalkerServerApi;
import dax.api_lib.models.DaxCredentials;
import dax.api_lib.models.DaxCredentialsProvider;
import dax.teleports.Teleport;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Player;
import org.tribot.api2007.Projection;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSObject;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.*;
import scripts.Data.MiningConst;
import scripts.EntitySelector.Entities;
import scripts.EntitySelector.finders.prefabs.ObjectEntity;
import scripts.Tasks.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@ScriptManifest(authors = {"Cass"}, category = "Mining", name = "cMotherLoad", description = "Mines in MLM")
public class MotherLoadMine extends Script implements Painting, Starting, Ending, Arguments, Breaking {

    Skills.SKILLS currentSkill = Skills.SKILLS.MINING;

    final int startLevel = Skills.getActualLevel(currentSkill);
    final int startXp = Skills.getXP(currentSkill);

    int gainedXp = 0;
    int gainedLvl = 0;
    int currentXp = Skills.getXP(currentSkill);

    public static boolean isRunning = true;
    public static String status = "Initializing...";
    public static String course = "Initializing...";


    public static Timer timeoutTimer = new Timer(General.random(300000, 420000));

    @Override
    public void run() {
        TaskSet tasks = new TaskSet(
                new MineOre(),
                new DepositOre(),
                new Bank()
        );


        /*new DiscordWebhook("https://discordapp.com/api/webhooks/835603662348353550/k7DVaODCO7V8QQFVqtLV02Sh_xh2JgwEohQ8PRSHMyFs3u6oC2_mxhYWlBUrNvMOCOb_")
                .setUsername("Tribot Test")
                .setContent("Testing this all works")
                .setAvatarUrl("https://i.imgur.com/nLcRUFX.jpeg")
                .addEmbed(new DiscordWebhook.EmbedObject()
                        .setImage("https://i.imgur.com/9eUYfp4.jpeg")
                        .setColor(Color.YELLOW)
                        .setDescription("Alpacas!")
                )
                .execute();*/
        while (isRunning) {
            General.sleep(30, 70);
            currentXp = Skills.getXP(currentSkill);

            Task task = tasks.getValidTask();
            if (task != null) {
                status = task.toString();
                task.execute();
            }
            if (Skills.getXP(currentSkill) > currentXp)
                timeoutTimer.reset();

            if (!timeoutTimer.isRunning()) {
                General.println("[Error]: Script Timed Out due to no exp gain in ~5-7min");
                isRunning = false;
                break;
            }
        }
    }

    @Override
    public void onEnd() {
        General.println("[Ending]: Running for: " + Timing.msToString(getRunningTime()));
        AntiBan.destroy();

    }

    @Override
    public void onPaint(Graphics g) {
        int currentLvl = Skills.getActualLevel(currentSkill);
        gainedLvl = currentLvl - startLevel;
        gainedXp = Skills.getXP(currentSkill) - startXp;

        double timeRan = getRunningTime();
        double timeRanMin = (timeRan / 3600000);

        int xpHr = (int) (gainedXp / timeRanMin);
        long xpToLevel = Skills.getXPToLevel(currentSkill, currentLvl + 1);
        RSObject[] vein = Entities.find(ObjectEntity::new)
                .inArea(MiningConst.NORTH_MINING_AREA)
                .sortByDistance(Player.getPosition(), true)
                .actionsContains("Mine")
                .nameContains("Ore vein")
                .getResults();
        for (RSObject obj : vein) {
            Polygon pp = Projection.getTileBoundsPoly(obj, 0);
            g.drawPolygon(pp);
        }

        //  Vars.get().timeToLevel = (long) (xpToLevel / ((gainedXp / timeRan)));
        List<String> myString = new ArrayList<>(Arrays.asList(
                "cMotherloadMine v" + super.getScriptVersion(),
                "Running For: " + Timing.msToString(getRunningTime()),
                "Task: " + status,
                "Mining XP: " + gainedXp + " || " + xpHr + "/hr"
        ));

        PaintUtil.createPaint(g, myString.toArray(String[]::new));
    }

    @Override
    public void onStart() {
        // General.println("[ABC2]: Next eating at: " + Vars.get().eatAt);
        //  int b = Vars.get().mouseSpeed;
        //  Mouse.setSpeed(b);
        //  General.println("[Debug]: Setting mouse speed to " + b);
        AntiBan.create();

        Teleport.blacklistTeleports(Teleport.values());

        WebWalkerServerApi.getInstance().setDaxCredentialsProvider(new DaxCredentialsProvider() {
            @Override
            public DaxCredentials getDaxCredentials() {
                return new DaxCredentials("sub_DPjXXzL5DeSiPf", " PUBLIC-KEY");
            }
        });
    }

    @Override
    public void passArguments(HashMap<String, String> hashMap) {
        String scriptSelect = hashMap.get("custom_input");
        String clientStarter = hashMap.get("autostart");
        String input = clientStarter != null ? clientStarter : scriptSelect;
        General.println("[Debug]: Argument entered: " + input);

        for (String arg : input.split(";")) {
            try {

                if (arg.toLowerCase().contains("afk")) {
                    General.println("[Arguments]: AFK mode enabled");
                    //  Vars.get().afkMode = true;
                    String[] splt = arg.toLowerCase().split("afk:");

                    if (splt.length > 0) {
                        General.println(splt[1], Color.RED);
                        String[] numString = splt[1].split(",");
                        if (numString.length > 1) {
                            int minLngth = Integer.parseInt(numString[0]);
                            int maxLngth = Integer.parseInt(numString[1]);
                            General.println("[Debug]: Min AFK length: " + minLngth);
                            General.println("[Debug]: Max AFK Length: " + maxLngth);
                            //      Vars.get().afkMin = minLngth;
                            //     Vars.get().afkMax = maxLngth;
                        }
                    }
                }
                if (arg.toLowerCase().contains("frequency:")) {

                }

            } catch (Exception e) {
                e.printStackTrace();
                General.println("[Error]: Args are incorrect", Color.RED);
            }
        }
    }

    @Override
    public void onBreakStart(long l) {

    }

    @Override
    public void onBreakEnd() {
        General.println("[Debug]: Resetting timeout timer due to break ending");
        timeoutTimer.reset();
    }
}
