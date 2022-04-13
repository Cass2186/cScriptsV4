package scripts;

import dax.api_lib.WebWalkerServerApi;
import dax.api_lib.models.DaxCredentials;
import dax.api_lib.models.DaxCredentialsProvider;
import dax.teleports.Teleport;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.*;
import org.tribot.api2007.Objects;
import org.tribot.api2007.types.*;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.*;
import org.tribot.script.sdk.*;
import org.tribot.script.sdk.Bank;
import org.tribot.script.sdk.Options;
import org.tribot.script.sdk.painting.template.basic.*;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.Npc;
import org.tribot.util.Util;
import scripts.API.SkillSwitchThread;
import scripts.API.Task;
import scripts.API.TaskSet;
import scripts.Data.Const;
import scripts.Data.Paint;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.EntitySelector.Entities;
import scripts.EntitySelector.finders.prefabs.NpcEntity;
import scripts.EntitySelector.finders.prefabs.ObjectEntity;
import scripts.Tasks.Agility.Tasks.Wilderness.WildernessAgility;
import scripts.Tasks.Combat.CrabTasks.EatDrink;
import scripts.Tasks.Combat.CrabTasks.Fight;
import scripts.Tasks.Combat.CrabTasks.MoveToCrabTile;
import scripts.Tasks.Combat.CrabTasks.ResetAggro;
import scripts.Tasks.Herblore.MixTar;
import scripts.Tasks.KourendFavour.ArceuusLibrary.State;
import scripts.Tasks.PestControl.PestTasks.AttackPortal;
import scripts.Tasks.PestControl.PestTasks.DefendKnight;
import scripts.Tasks.PestControl.PestTasks.EnterBoat;
import scripts.Tasks.Smithing.BlastFurnace.BfTasks.*;
import scripts.Tasks.SorceressGarden.ElementalCollisionDetector;
import scripts.dax.tracker.DaxTracker;
import scripts.skillergui.GUI;
import scripts.skillergui.SkillerGUI;
import scripts.Tasks.Agility.Tasks.Canifis.CanifisCourse;
import scripts.Tasks.Agility.Tasks.Canifis.GoToCanifis;
import scripts.Tasks.Agility.Tasks.DraynorVillage.DraynorCourse;
import scripts.Tasks.Agility.Tasks.DraynorVillage.GoToDraynor;
import scripts.Tasks.Agility.Tasks.Falador.FaladorCourse;
import scripts.Tasks.Agility.Tasks.Falador.GoToFaladorStart;
import scripts.Tasks.Agility.Tasks.Pollvniveach.GoToStart;
import scripts.Tasks.Agility.Tasks.Pollvniveach.Pollniveach;
import scripts.Tasks.Agility.Tasks.Relleka.GoToRelleka;
import scripts.Tasks.Agility.Tasks.Relleka.RellekaCourse;
import scripts.Tasks.Agility.Tasks.SeersCourse.GoToSeersStart;
import scripts.Tasks.Agility.Tasks.SeersCourse.Seers;
import scripts.Tasks.Agility.Tasks.TreeGnome.GoToTreeGnome;
import scripts.Tasks.Agility.Tasks.TreeGnome.TreeGnomeCourse;
import scripts.Tasks.Agility.Tasks.Varrock.GoToVarrock;
import scripts.Tasks.Agility.Tasks.Varrock.VarrockCourse;
import scripts.Tasks.Construction.BuyHouse;
import scripts.Tasks.Construction.ConstructionBank;
import scripts.Tasks.Construction.MakeFurniture;
import scripts.Tasks.Construction.UnnotePlanks;
import scripts.Tasks.Cooking.BankCook;
import scripts.Tasks.Cooking.CookFood;
import scripts.Tasks.Crafting.CraftBank;
import scripts.Tasks.Crafting.MakeGlass;
import scripts.Tasks.Firemaking.BankFireMaking;
import scripts.Tasks.Firemaking.MakeFires;
import scripts.Tasks.Fishing.Fish;
import scripts.Tasks.Fishing.FishDrop;
import scripts.Tasks.Fletching.FletchBank;
import scripts.Tasks.Fletching.Fletching;
import scripts.Tasks.Herblore.HerbloreBank;
import scripts.Tasks.Herblore.MixItemsHerblore;
import scripts.Tasks.Hunter.CollectFailed;
import scripts.Tasks.Hunter.CollectSuccessful;
import scripts.Tasks.Hunter.Falconry;
import scripts.Tasks.Hunter.HunterData.HunterConst;
import scripts.Tasks.Hunter.SetTrap;
import scripts.Tasks.Magic.Alch;
import scripts.Tasks.Magic.EnchantJewelery;
import scripts.Tasks.Magic.MagicBank;
import scripts.Tasks.Mining.Tasks.*;
import scripts.Tasks.Mining.Utils.MLMUtils;
import scripts.Tasks.MiscTasks.Afk;
import scripts.Tasks.MiscTasks.BuyItems;
import scripts.Tasks.MiscTasks.MiniBreak;
import scripts.Tasks.MiscTasks.SwitchTask;
import scripts.Tasks.PestControl.PestUtils.PestUtils;
import scripts.Tasks.Prayer.EnterHome;
import scripts.Tasks.Prayer.PlaceBones;
import scripts.Tasks.Prayer.PrayerBank;
import scripts.Tasks.Prayer.UnnoteBones;
import scripts.Tasks.Runecrafting.CraftRunes;
import scripts.Tasks.Runecrafting.GoToRcAltar;
import scripts.Tasks.Runecrafting.MindTiara;
import scripts.Tasks.Runecrafting.RunecraftBank;
import scripts.Tasks.Slayer.SlayerUtils.SlayerVars;
import scripts.Tasks.Slayer.Tasks.*;
import scripts.Tasks.Smithing.MakeCannonballs;
import scripts.Tasks.Smithing.SmithBars;
import scripts.Tasks.SorceressGarden.SpringRun;
import scripts.Tasks.Thieving.NpcThieving;
import scripts.Tasks.Thieving.StallThieving;
import scripts.Tasks.Woodcutting.CutLogs;
import scripts.Tasks.Woodcutting.DropLogs;
import scripts.Tasks.Woodcutting.WoodcuttingBank;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;


@ScriptManifest(name = "cSkiller", authors = {"Cass2186"}, category = "Testing", description = "Current Version v0.1")
public class cSkiller extends Script implements Starting, Ending, Arguments, Breaking, MessageListening07 {

    private boolean shouldEnd = false;


    public static AtomicBoolean isRunning = new AtomicBoolean(true);
    public static String status = "Initializing";
    public static String currentSkillName = "Initializing";
    public static Skills.SKILLS currentSkill;

    public static SkillSwitchThread skillTracker = new SkillSwitchThread();

    public static Timer safetyTimer = new Timer(600000); //6 min

    public cSkiller() {
        try {
            Vars.get().daxTracker = new DaxTracker(
                    "3d50ebcf-daa0-4d79-82fd-c76633f97ed0",
                    "tqRBZL6zoacmgLn7pwANpA==");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void populateInitialMap() {
        Log.log("[Debug]: Populating intial skills xp HashMap");
        for (Skills.SKILLS s : Skills.SKILLS.values()) {
            Vars.get().skillStartXpMap.put(s, s.getXP());
        }
    }

    public HashMap<Skills.SKILLS, Integer> getXpMap() {
        HashMap<Skills.SKILLS, Integer> map = new HashMap<>();
        if (Vars.get().skillStartXpMap == null)
            populateInitialMap();

        for (Skills.SKILLS s : Skills.SKILLS.values()) {
            int startXp = Vars.get().skillStartXpMap.get(s);
            if (s.getXP() > startXp) {
                map.put(s, s.getXP() - startXp);
                //   System.out.println("updating xp map & dax");
            }
        }

        return map;
    }


    @Override
    public void onStart() {
        General.println("Version: " + super.getScriptVersion());

        AntiBan.create();
        populateInitialMap();
        Teleport.blacklistTeleports(Teleport.GLORY_KARAMJA, Teleport.RING_OF_WEALTH_MISCELLANIA);

        WebWalkerServerApi.getInstance().setDaxCredentialsProvider(new DaxCredentialsProvider() {
            @Override
            public DaxCredentials getDaxCredentials() {
                return new DaxCredentials("sub_H0C2eULZfbWeoF", "acb35610-d868-4ce8-8797-0d2e659f87f4");
                //return new DaxCredentials("sub_DPjXXzL5DeSiPf", " PUBLIC-KEY");
            }
        });
        Utils.setNPCAttackPreference();
       // addPaint();

    }


    @SneakyThrows
    @Override
    public void run() {
        String user = Tribot.getUsername();
        URL lcn = new URL("https://raw.githubusercontent.com/Whipz/guis/main/cSkillerGUI.fxml");
        //String lcn = "https://raw.githubusercontent.com/Cass2186/cScriptsV2/main/cSkillerGUI.fxml";
        Log.log("Username is " + user);
        Log.log("Location is " + lcn);

        if (Tribot.getUsername().equalsIgnoreCase("cass2186") || Tribot.getUsername().equalsIgnoreCase("Whipz")
                || Tribot.getUsername().equalsIgnoreCase("SkrrtNick") || Tribot.getUsername().equalsIgnoreCase("Destinbrown1225")) {

        } else {
            Log.log("[Debug]: Username is not permitted");
            return;
        }

        General.println("[Debug]: setting fxml || workingDir is " + Util.getHomeDirectory());

        InputStream stream = getClass().getClassLoader()
                .getResourceAsStream("scripts/resources/cSkillerGUI.fxml");
       // BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

        //SkillerGUI gui = new SkillerGUI(reader.lines().collect(Collectors.joining(System.lineSeparator())));
        lcn = new URL("https://raw.githubusercontent.com/Cass2186/cScriptsV4/main/scripts/cSkiller/src/scripts/resources/cSkillerGUI.fxml");
                //"https://raw.githubusercontent.com/Whipz/guis/main/cSkillerGUI.fxml");
        GUI gui = new GUI(lcn);

        if (Vars.get().shouldShowGUI) {
            Log.debug("Loading GUI");
            gui.show();
            while (gui.isOpen())
                sleep(500);
        }

        Options.AttackOption.setNpcAttackOption(Options.AttackOption.LEFT_CLICK_WHERE_AVAILABLE);

        Utils.setCameraZoomAboveDefault();
        Mouse.setSpeed(Vars.get().mouseSpeed);
        Log.log("Mouse speed is " + Vars.get().mouseSpeed);
        if (Vars.get().currentTask == null) {
            General.println("[Debug]: Vars.get().currentTask is null, generating a task");
            Vars.get().currentTask = SkillTasks.getSkillTask();
        }
        skillTracker.start();
        Options.setShiftClickDrop(true);
        Widgets.closeAll();

        TaskSet tasks = new TaskSet(
                new SwitchTask(),
                new CookFood(),
                new BankCook(),
                new CraftBank(),
                new MakeGlass(),
                new BankFireMaking(),
                new MakeFires(),
                new WoodcuttingBank(),
                new CutLogs(),
                new DropLogs(),
                new HerbloreBank(),
                new MixItemsHerblore(),
                 new MixTar(),
                new BuyItems(),
                new CollectOre(),
                new DepositPayDirt(),
                new MineOreMLM(),
                new UnnoteBones(),
                new PlaceBones(),
                new EnterHome(),
                new MiningBank(),
                new EnchantJewelery(),
                new MagicBank(),
                new IronOre(),
                new Alch(),
                new MagicBank(),
                new FletchBank(),
                new Fletching(),
                new CollectFailed(),
                new CollectSuccessful(),
                new SetTrap(),
                new Falconry(),
                new scripts.Tasks.Magic.Teleport(),
                new DefendKnight(),
                //new AttackPortal(),
                new EnterBoat(),
                new WildernessAgility(),
                new Pollniveach(),
                new GoToStart(),
                new GoToTreeGnome(),
                new TreeGnomeCourse(),
                new GoToDraynor(),
                new DraynorCourse(),
                new GoToVarrock(),
                new VarrockCourse(),
                new GoToCanifis(),
                new CanifisCourse(),
                new Seers(),
                new GoToSeersStart(),
                new GoToRelleka(),
                new RellekaCourse(),
                new FaladorCourse(),
                new GoToFaladorStart(),
                new NpcThieving(),
                new StallThieving(),
                new Afk(),
                new Fish(),
                new FishDrop(),
                new MindTiara(),
                new RunecraftBank(),
                new CraftRunes(),
                new GoToRcAltar(),
                new UnnotePlanks(),
                new MakeFurniture(),
                new ConstructionBank(),
                new AttackNpc(),
                new SlayerShop(),
                new MoveToArea(),
                new SlayerBank(),
                new SlayerRestock(),
                new GetTask(),
                new Loot(),
               // new SmithBars(),
                new BuyHouse(),
                new EatDrink(),
                new Fight(),
                new MoveToCrabTile(),
                new ResetAggro(),
              new BlastFurnaceBank(),
                   new CollectBars(),
                  new DepositOre(),
                 new MoveToBF(),
                 new PayForeman(),
                  new RefillCoffer(),
                  new Stamina(),
                 new StartQuest(),
                new PrayerBank(),
                new SpringRun()
                //MiniBreak.get(),
               // new MakeCannonballs()

        );
        Paint.setToggleablePaint();
        Paint.setSlayerPaint();
        Paint.setMainPaint();
        Paint.setExperiencePaint();

        HashMap<Skills.SKILLS, Integer> startHashMap = Utils.getXpForAllSkills();
        isRunning.set(true);

        while (isRunning.get()) {
            if (shouldEnd)
                break;

            if (Utils.hasTotalXpIncreased(startHashMap)) {
                safetyTimer.reset();
                startHashMap = Utils.getXpForAllSkills();
            }
            if (!safetyTimer.isRunning()) {
                General.println("[Debug]: Script timed out due to no xp gain in 6 min");
                isRunning.set(false);
                break;
            }

            if (Vars.get().currentTask != null && !Vars.get().currentTask.isWithinLevelRange())
                Vars.get().currentTask = null;

            if (Vars.get().currentTask == null) {
                General.println("[Debug]: Generating task with getSkillTask()", Color.RED);
                Vars.get().currentTask = SkillTasks.getSkillTask();
            }

            if (Vars.get().currentTask == null) {
                General.println("[Debug]: Failed to select a new task, likely done all");
                isRunning.set(false);
                return;
            } else {
                currentSkill = Vars.get().currentTask.getSkill();
            }

            General.sleep(10, 40);
            Task task = tasks.getValidTask();
            if (task != null) {
                if (task.equals(MiniBreak.get())) {
                    super.setLoginBotState(false);
                }
                status = task.toString();
                currentSkillName = task.taskName();
                task.execute();
            }
        }
    }

    @Override
    public void passArguments(HashMap<String, String> hashMap) {
        String scriptSelect = hashMap.get("custom_input");
        String clientStarter = hashMap.get("autostart");
        String input = clientStarter != null ? clientStarter : scriptSelect;
        General.println("[Debug]: Argument entered: " + input);

        for (String arg : input.split(";")) {
            try {
                if (arg.toLowerCase().contains("slayer")) {
                    General.println("[Args]: Training Slayer");
                    Vars.get().shouldShowGUI = false;
                }
                if (arg.toLowerCase().contains("prayer")) {
                    General.println("[Args]: Training prayer first");
                    currentSkill = Vars.get().currentTask.getSkill();
                }
                if (arg.toLowerCase().contains("mining")) {
                    General.println("[Args]: Training mining first");
                    currentSkill = Vars.get().currentTask.getSkill();
                }
                if (arg.toLowerCase().contains("magic")) {
                    General.println("[Args]: Training magic first");
                    Vars.get().currentTask = SkillTasks.MAGIC;
                }
                if (arg.toLowerCase().contains("garden")) {
                    General.println("[Args]: Training garden");
                    Vars.get().shouldShowGUI = false;
                    SkillTasks.THIEVING.setEndLevel(99);
                    Vars.get().currentTask = SkillTasks.THIEVING;
                    Vars.get().doGarden = true;
                }
                if (arg.toLowerCase().contains("pestcontrol")) {
                    General.println("[Args]: Training PC");
                    Vars.get().shouldShowGUI = false;
                    SkillTasks.PEST_CONTROL.setStartLevel(1);
                    Vars.get().currentTask = SkillTasks.PEST_CONTROL;
                }


            } catch (Exception e) {
                e.printStackTrace();
                General.println("[Error]: Args are incorrect", Color.RED);
            }
        }
    }


    @Override
    public void onEnd() {
        isRunning.set(false);
        General.println("[Ending]: Runtime: " + Timing.msToString(this.getRunningTime()), Color.RED);
        HashMap<Skills.SKILLS, Integer> xpMap = getXpMap();
        if (Vars.get().skillStartXpMap == null)
            populateInitialMap();

        for (Skills.SKILLS s : Skills.SKILLS.values()) {
            int startXp = Vars.get().skillStartXpMap.get(s);
            if (s.getXP() > startXp) {
                Log.log("[Ending]: Gained " + (s.getXP() - startXp) + " " + s + " exp");
                Log.log("[Ending]: updating xp map & dax");
                Vars.get().daxTracker.trackData(s.toString(), (s.getXP() - startXp));
            }
        }

        Vars.get().daxTracker.stop();
    }

    @Override
    public void onBreakEnd() {
        safetyTimer.reset();
        Vars.get().isOnBreak = false;
    }

    @Override
    public void onBreakStart(long l) {
        Vars.get().isOnBreak = true;
        Vars.get().breakLength = l;
        General.println("[Debug]: On break for " + Timing.msToString(Vars.get().breakLength));
    }


    public void endOnMessage(String serverMessage, String message2) {
        String stripped = General.stripFormatting(serverMessage);
        if (stripped.toLowerCase().contains(message2.toLowerCase())) {
            General.println("[MessageListener]: Message triggering script end: " + serverMessage, Color.RED);
            isRunning.set(false);
            shouldEnd = true;
            throw
                    new NullPointerException();
        }
    }

    /**
     * Only detects when we manually check the charges
     *
     * @param message
     */
    public static void checkExpCharges(String message) {
        if (message.toLowerCase().contains("charges left.")
                && message.toLowerCase().contains("expeditious bracelet has")) {
            String s1 = message.split(" charges left")[0];
            String num = s1.split("has ")[1];
            General.println("[Message Listener]: Expeditious bracelet has " + num + " charges left");
            SlayerVars.get().expeditiousCharges = (Integer.parseInt(num));

        }
    }


    private void endOnDeath(String message) {
        if (Vars.get().currentTask != null && Vars.get().currentTask != SkillTasks.PEST_CONTROL) {
            if (message.toLowerCase().contains("you are dead") || message.toLowerCase().contains("you died")) {
                General.println("[Message Listner]: Death Message received, ending script");
                isRunning.set(false);
                throw
                        new NullPointerException();
            }
        }
    }


    @Override
    public void serverMessageReceived(String message) {
        if (Vars.get().currentTask != null && Vars.get().currentTask.equals(SkillTasks.FIREMAKING)) {
            if (message.contains("You can't light a fire")) {
                Vars.get().shouldResetFireMaking = true;
            }
        }
        if (Vars.get().currentTask != null && Vars.get().currentTask.equals(SkillTasks.FIREMAKING)) {
            BlastFurnaceBank.coalBagMessaageHandler(message);
        }
        if (Vars.get().currentTask != null && Vars.get().currentTask.equals(SkillTasks.SLAYER)) {
            GetTask.handleNewTaskMessage(message);
            GetTask.handleTaskMessage(message);
            GetTask.handleCompleteMessage(message);
            //handleExpeditiousBraceletMessage(message);
            AttackNpc.expeditiousMessage(message);
            MoveToArea.handleCandleMessage(message);
            checkExpCharges(message);
            // KillTracker.braceletOfSlaughterMessage(message);
            //CannonMonitor.handleCannonMessage(message);
            endOnMessage(message, "iron dragon");
            endOnMessage(message, "steel dragon");

        }
        if (message.contains("green dragon"))
            Log.log("[Message]: " + message);
        if (message.equals("You don't find anything useful here.")) {
            State.get().getLastBookcaseTile().ifPresent(tile -> {

                if (Utils.isLookingTowards(Player.getRSPlayer(), tile, 1)) {
                    State.get().getLibrary().mark(tile, null);
                    State.get().setLastBookcaseTile(null);
                } else {
                    Log.log("Misclicked bookcase");
                    State.get().setLastBookcaseTile(null);
                }
            });
        }
        endOnMessage(message, "green dragon");
        endOnMessage(message, "earth warrior");
        endOnDeath(message);
    }
}