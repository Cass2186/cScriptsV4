package scripts;

import dax.api_lib.WebWalkerServerApi;
import dax.api_lib.models.DaxCredentials;
import dax.api_lib.models.DaxCredentialsProvider;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Game;
import org.tribot.api2007.Player;
import org.tribot.api2007.Projection;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSCharacter;
import org.tribot.api2007.types.RSPlayer;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.Arguments;
import org.tribot.script.interfaces.Ending;
import org.tribot.script.interfaces.Painting;
import org.tribot.script.interfaces.Starting;
import scripts.Data.Const;
import scripts.Data.FarmingUtils;
import scripts.Data.Vars;
import scripts.Nodes.*;
import scripts.Tasks.Task;
import scripts.Tasks.TaskSet;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@ScriptManifest(authors = {"Cass"}, category = "Farming", name = "cFarming", description = "Does farm runs || " +
        "Args: Fruit - fruit trees up to palm || " +
        "Herbs - specify  'herbs:ranarr' if over lvl 72 || " +
        "Trees - trees, scales with level || " +
        "Allotments - scales with level")
public class cFarming extends Script implements Painting, Starting, Arguments, Ending {



    final int startFarmLevel = Skills.getActualLevel(Skills.SKILLS.FARMING);
    final int startFarmXp = Skills.getXP(Skills.SKILLS.FARMING);
    int gainedXp = 0;
    int gainedLvl = 0;
    int profit = 0;

    public static AtomicBoolean isRunning = new AtomicBoolean(true);
    public static String status = "Initializing";


    @Override
    public void run() {
        WebWalkerServerApi.getInstance().setDaxCredentialsProvider(new DaxCredentialsProvider() {
            @Override
            public DaxCredentials getDaxCredentials() {
                return new DaxCredentials("sub_H0C2eULZfbWeoF ", "acb35610-d868-4ce8-8797-0d2e659f87f4");
            }
        });

        TaskSet tasks;
        tasks = new TaskSet(
                new Bank(),
                new Restock(),
                new Move(),
                new Harvest(),
                new Plant(),
                new Break()

        );

        if (Vars.get().doingTrees)
            FarmingUtils.populateTreePatches();

        else if (Vars.get().doingFruitTrees)
            FarmingUtils.populateFruitTreePatches();

        else if (Vars.get().doingAllotments)
            FarmingUtils.populateAllotmentPatches();

        else
            FarmingUtils.populatePatchesToVisit();

        isRunning.set(true);
        while (isRunning.get()) {
            General.sleep(50, 150);
            if (Game.isInInstance()) //died
                break;

            Task task = tasks.getValidTask();
            if (task != null) {
                status = task.toString();
                task.execute();
            }

        }
    }

    @Override
    public void passArguments(HashMap<String, String> arg0) {
        String scriptSelect = arg0.get("custom_input");
        String clientStarter = arg0.get("autostart");
        String input = clientStarter != null ? clientStarter : scriptSelect;
        General.println("[Debug]: Argument entered: " + input);
        for (String arg : input.split(";")) {
            try {
                String tasks = arg.split(";")[0].toLowerCase();
                if (tasks.contains("tree")) {
                    General.println("[Arguments]: Doing Trees", Color.RED);
                    Vars.get().doingTrees = true;
                    Plant.determineTreeId();
                } else if (tasks.contains("allotment")) {

                    General.println("[Arguments]: Doing Allotments", Color.GREEN);
                    Vars.get().doingAllotments = true;
                    Plant.determineAllotmentId();
                } else if (tasks.contains("fruit")) {

                    General.println("[Arguments]: Doing Fruit Trees", Color.GREEN);
                    Vars.get().doingFruitTrees = true;
                    Plant.determineFruitTreeId();
                } else if (tasks.contains("herb")) {
                    General.println("[Arguments]: Doing Herbs", Color.YELLOW);
                    Vars.get().doingHerbs = true;
                    Vars.get().doingAllotments = false;
                    Plant.determineHerbId();
                    if (tasks.contains("ranarr")) {
                        General.println("{Arguments]: We are using ranarrs");
                        Vars.get().currentHerbId = Const.RANARR_SEED_ID;
                    }
                }
            } catch (Exception e) {

            }
        }
    }
    public static String reportGainedXp(int gainedXp, int gainedLvl, int gainedXpHr) {
        String string = String.format("Gained XP: %s (%s) / %s/h", Utils.addCommaToNum(gainedXp),
                gainedLvl, Utils.addCommaToNum(gainedXpHr));
        return string;
    }
    @Override
    public void onEnd() {
        General.println("[Ending]: Profit: " + Utils.addCommaToNum(profit));
        General.println("[Ending]: Gained XP: " + gainedXp);
        int xpHr = (int) (gainedXp / (getRunningTime() / 3600000));
        General.println(reportGainedXp(gainedXp, gainedLvl, xpHr));
        General.println("[Debug]: Profit: " + profit);
    }

    @Override
    public void onPaint(Graphics g) {
        int currentLvl = Skills.getActualLevel(Skills.SKILLS.FARMING);
        gainedLvl = currentLvl - startFarmLevel;
        gainedXp = Skills.getXP(Skills.SKILLS.FARMING) - startFarmXp;

        double timeRan = getRunningTime();
        double timeRanMin = (timeRan / 3600000);
        int xpHr = (int) (gainedXp / timeRanMin);
        List<String> myString = new ArrayList<>(Arrays.asList(
                "cFarming",
                "Running For: " + Timing.msToString(this.getRunningTime()),
                //  "Profit: " + Utils.addCommaToNum(Utils.getInventoryValue() - Vars.get().startInvValue),
                "Task: " + Vars.get().status,
                "Experience Gained: " + Utils.addCommaToNum(gainedXp)
                        + " (" + gainedLvl + ")" + " /" + Utils.addCommaToNum(xpHr) + "/hr"
        ));
        RSPlayer p = Player.getRSPlayer();

        if (p != null) {
          RSCharacter ch =  p.getInteractingCharacter();
          if (ch != null){
              g.setColor(Color.WHITE);
              Polygon pp = Projection.getTileBoundsPoly(ch, 0);
              g.drawPolygon(pp);
          }

        }
        PaintUtil.createPaint(g, myString.toArray(String[]::new));
    }

    @Override
    public void onStart() {
        AntiBan.create();
        Vars.get().startTime = Timing.currentTimeMillis();
        Vars.get().currentTime = Timing.currentTimeMillis();
        this.setLoginBotState(false);


    }
}
