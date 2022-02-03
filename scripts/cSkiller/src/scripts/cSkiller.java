package scripts;

import dax.api_lib.WebWalkerServerApi;
import dax.api_lib.models.DaxCredentials;
import dax.api_lib.models.DaxCredentialsProvider;
import dax.teleports.Teleport;
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
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.Npc;
import org.tribot.util.Util;
import scripts.API.SkillSwitchThread;
import scripts.API.Task;
import scripts.API.TaskSet;
import scripts.Data.Const;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.EntitySelector.Entities;
import scripts.EntitySelector.finders.prefabs.NpcEntity;
import scripts.EntitySelector.finders.prefabs.ObjectEntity;
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
public class cSkiller extends Script implements Painting, Starting, Ending, Arguments, Breaking, MessageListening07 {

    private URL fxml;
    private SkillerGUI gui;

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


    private static List<String> getCurrentSkillXP() {

        List<String> skillsList = new LinkedList<String>();

        if (Skills.getXP(Skills.SKILLS.AGILITY) > Vars.get().startAgilityXp)
            skillsList.add("Agility");

        if (Skills.getXP(Skills.SKILLS.CONSTRUCTION) > Vars.get().startConstructionXp)
            skillsList.add("Construction");

        if (Skills.getXP(Skills.SKILLS.CRAFTING) > Vars.get().startCraftingXp)
            skillsList.add("Crafting");

        if (Skills.getXP(Skills.SKILLS.COOKING) > Vars.get().startCoookingXp)
            skillsList.add("Cooking");

        if (Skills.getXP(Skills.SKILLS.FISHING) > Vars.get().startFishingXp)
            skillsList.add("Fishing");

        if (Skills.getXP(Skills.SKILLS.HERBLORE) > Vars.get().startHerbloreXp)
            skillsList.add("Herblore");

        if (Skills.getXP(Skills.SKILLS.HUNTER) > Vars.get().startHunterXp)
            skillsList.add("Hunter");

        if (Skills.getXP(Skills.SKILLS.FIREMAKING) > Vars.get().startFiremakingXp)
            skillsList.add("Firemaking");

        if (Skills.getXP(Skills.SKILLS.MAGIC) > Vars.get().startMagicXp)
            skillsList.add("Magic");

        if (Skills.getXP(Skills.SKILLS.MINING) > Vars.get().startMiningXp)
            skillsList.add("Mining");

        if (Skills.getXP(Skills.SKILLS.WOODCUTTING) > Vars.get().startWoodcuttingXp)
            skillsList.add("Woodcutting");

        return skillsList;
    }

    private static List<String> getCurrentSkillLevel() {
        List<String> skillsList = new LinkedList<String>();

        if (Skills.getActualLevel(Skills.SKILLS.AGILITY) > Const.startAgilityLvl)
            skillsList.add("Agility");

        if (Skills.getActualLevel(Skills.SKILLS.CONSTRUCTION) > Const.startConstructionLvl)
            skillsList.add("Construction");

        if (Skills.getActualLevel(Skills.SKILLS.CRAFTING) > Const.startCraftingLvl)
            skillsList.add("Crafting");

        if (Skills.getActualLevel(Skills.SKILLS.COOKING) > Const.startCoookingLvl)
            skillsList.add("Cooking");

        if (Skills.getActualLevel(Skills.SKILLS.FISHING) > Const.startFishingLvl)
            skillsList.add("Fishing");

        if (Skills.getActualLevel(Skills.SKILLS.HERBLORE) > Const.startHerbloreLvl)
            skillsList.add("Herblore");

        if (Skills.getActualLevel(Skills.SKILLS.HUNTER) > Const.startHunterLvl)
            skillsList.add("Hunter");

        if (Skills.getActualLevel(Skills.SKILLS.FIREMAKING) > Const.startFiremakingLvl)
            skillsList.add("Firemaking");

        if (Skills.getActualLevel(Skills.SKILLS.MAGIC) > Const.startMagicLvl)
            skillsList.add("Magic");

        if (Skills.getActualLevel(Skills.SKILLS.MINING) > Const.startMiningLvl)
            skillsList.add("Mining");

        if (Skills.getActualLevel(Skills.SKILLS.WOODCUTTING) > Const.startWoodcuttingLvl)
            skillsList.add("Woodcutting");


        if (Skills.getActualLevel(Skills.SKILLS.WOODCUTTING) > Const.startPrayerLvl)
            skillsList.add("Prayer");

        return skillsList;
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
                return new DaxCredentials("sub_DPjXXzL5DeSiPf", " PUBLIC-KEY");
            }
        });
        Utils.setNPCAttackPreference();

    }


    @Override
    public void run() {
        String user = Tribot.getUsername();
        String lcn = "https://raw.githubusercontent.com/Cass2186/cScriptsV2/main/cSkillerGUI.fxml";
        Log.log("Username is " + user);
        Log.log("Location is " + lcn);

        if (Tribot.getUsername().equalsIgnoreCase("cass2186") || Tribot.getUsername().equalsIgnoreCase("Whipz")
                || Tribot.getUsername().equalsIgnoreCase("SkrrtNick") || Tribot.getUsername().equalsIgnoreCase("Destinbrown1225")) {

        } else {
            Log.log("[Debug]: Username is not permitted");
            return;
        }

        General.println("[Debug]: setting fxml || workingDir is " + Util.getHomeDirectory());

        InputStream stream = getClass().getClassLoader().getResourceAsStream("scripts/resources/cSkillerGUI.fxml");
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        SkillerGUI gui = new SkillerGUI(reader.lines().collect(Collectors.joining(System.lineSeparator())));


        if (Vars.get().shouldShowGUI) {
            gui.show();
            while (gui.isOpen())
                sleep(500);
        }

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
              //  new SmithBars(),
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
    public void onPaint(Graphics g) {


        double timeRan = super.getRunningTime();
        double timeRanMin = (timeRan / 3600000);
        List<String> skillsList = getCurrentSkillXP();
        List<String> skillsLevelList = getCurrentSkillLevel();

        if (Skills.getCurrentLevel(Skills.SKILLS.HUNTER) < 29) {
            RSObject[] birdSnare = Objects.findNearest(20, HunterConst.LAID_BIRD_SNARE);
            RSObject[] failedTraps = Objects.findNearest(20, HunterConst.DISABLED_BIRD_SNARE);
            RSObject[] successfulTraps = Objects.findNearest(20, HunterConst.CAUGHT_BIRD_SNARE);

            for (RSObject obj : failedTraps) {
                g.setColor(Color.RED);
                Polygon pp = Projection.getTileBoundsPoly(obj, 0);
                g.drawPolygon(pp);
            }
            for (RSObject obj : birdSnare) {
                g.setColor(Color.WHITE);
                Polygon pp = Projection.getTileBoundsPoly(obj, 0);
                g.drawPolygon(pp);
            }
            for (RSObject obj : successfulTraps) {
                g.setColor(Color.GREEN);
                Polygon pp = Projection.getTileBoundsPoly(obj, 0);
                g.drawPolygon(pp);
            }
        } else if (Skills.getCurrentLevel(Skills.SKILLS.HUNTER) < 43) {
            RSObject[] birdSnare = Objects.findNearest(20, HunterConst.NET_TRAP_RESTING);
            RSObject[] failedTraps = Objects.findNearest(20, HunterConst.NET_TRAP_ENGAGED);
            RSObject[] successfulTraps = Objects.findNearest(20, HunterConst.NET_TRAP_SUCCESSFUL);

            for (RSObject obj : failedTraps) {
                g.setColor(Color.YELLOW);
                Polygon pp = Projection.getTileBoundsPoly(obj, 0);
                g.drawPolygon(pp);
            }
            for (RSObject obj : birdSnare) {
                g.setColor(Color.WHITE);
                Polygon pp = Projection.getTileBoundsPoly(obj, 0);
                g.drawPolygon(pp);
            }
            for (RSObject obj : successfulTraps) {
                g.setColor(Color.GREEN);
                Polygon pp = Projection.getTileBoundsPoly(obj, 0);
                g.drawPolygon(pp);
            }
        }
        RSObject[] veins = Entities.find(ObjectEntity::new)
                .inArea(MLMUtils.NORTH_MINING_AREA)
                .sortByDistance(Player.getPosition(), true)
                .actionsContains("Mine")
                .nameContains("Ore vein")
                .getResults();

        for (RSObject obj : veins) {
            g.setColor(Color.WHITE);
            Polygon pp = Projection.getTileBoundsPoly(obj, 0);
            g.drawPolygon(pp);
        }
        g.setColor(Color.GREEN);
        RSObject vein = MineOreMLM.getVein();

        if (vein != null) {
            g.drawPolygon(Projection.getTileBoundsPoly(vein.getPosition(), 0));
        }
        if (Vars.get().doGarden) {
            List<Npc> npcList = Query.npcs()
                    .stream()
                    .filter(ElementalCollisionDetector::isSpringElemental)
                    .collect(Collectors.toList());

            for (Npc n : npcList) {
                Optional<Polygon> tile = n.getTile().getBounds();
                if (tile.isPresent()) {
                    g.drawPolygon(tile.get());
                    g.drawString("angle: " + n.getOrientation().getAngle(),
                            (int) tile.get().getBounds2D().getX(), (int) tile.get().getBounds2D().getY());

                }

            }
        }

        g.setColor(Color.WHITE);
        List<String> myString = new ArrayList<>(Arrays.asList(
                "cSkiller v" + this.getScriptVersion(),
                "Running For: " + Timing.msToString(getRunningTime()),
                "Task: " + currentSkillName,
                "Action: " + status,
                // "ItemsToBuy: " + BuyItems.itemsToBuy.size(),
                "Skill Timer: " + Timing.msToString(Vars.get().skillSwitchTimer.getRemaining()),
                "AFK Timer: " + Timing.msToString(Vars.get().afkTimer.getRemaining())
        ));
        if (Vars.get().currentTask != null &&
                Vars.get().currentTask.equals(SkillTasks.MINING)) {
            myString.add("Deposits: " + Vars.get().oreDeposits);
        }
        if (Vars.get().currentTask != null &&
                Vars.get().currentTask.equals(SkillTasks.SLAYER)) {
            myString.add("Remaining Kills: " +
                    Game.getSetting(394));

        }
        HashMap<Skills.SKILLS, Integer> xpMap = getXpMap();
        for (Skills.SKILLS s : xpMap.keySet()) {
            int startLvl = Skills.getLevelByXP(Vars.get().skillStartXpMap.get(s));
            int xpHr = (int) (xpMap.get(s) / timeRanMin);
            long xpToLevel1 = Skills.getXPToLevel(s, s.getActualLevel() + 1);
            long ttl = (long) (xpToLevel1 / ((xpMap.get(s) / timeRan)));
            int gained = s.getActualLevel() - startLvl;
            if (gained > 0) {
                myString.add(
                        StringUtils.capitalize(s.toString().toLowerCase(Locale.ROOT))
                                + " [" + s.getActualLevel() + " (+" + gained + ")]: "
                                + Utils.addCommaToNum(xpMap.get(s)) + "xp (" + Utils.addCommaToNum(xpHr) + "/hr) " +
                                "|| TNL: "
                                + Timing.msToString(ttl)
                );
            } else {
                myString.add(StringUtils.capitalize(s.toString().toLowerCase(Locale.ROOT))
                        + " [" + s.getActualLevel() + "]: "
                        + Utils.addCommaToNum(xpMap.get(s)) + "xp (" + Utils.addCommaToNum(xpHr) + "/hr) " +
                        "|| TNL: "
                        + Timing.msToString(ttl)
                );
            }
        }
        if (Vars.get().currentTask != null &&
                Vars.get().currentTask.equals(SkillTasks.PEST_CONTROL)) {
            RSNPC[] knight = Entities.find(NpcEntity::new)
                    .nameContains("Void Knight")
                    .idEquals(PestUtils.KNIGHT_ID)
                    // .actionsNotContains("Leave")
                    .getResults();
            RSPlayer plyr = Player.getRSPlayer();
            if (plyr != null && plyr.isInCombat()) {
                g.setColor(Color.GREEN);
                g.drawPolygon(Projection.getTileBoundsPoly(plyr.getPosition(), 1));
            } else if (plyr != null && !plyr.isInCombat()) {
                g.setColor(Color.WHITE);
                g.drawPolygon(Projection.getTileBoundsPoly(plyr.getPosition(), 1));
            }
            g.setColor(Color.WHITE);
            if (knight.length > 0) {
                g.drawPolygon(Projection.getTileBoundsPoly(knight[0].getPosition(), 1));
            }
            Optional<RSNPC> target = PestUtils.getTarget();

            if (target.isPresent()) {
                g.drawPolygon(Projection.getTileBoundsPoly(target.get().getPosition(), 1));
            }
            PestUtils.getPestPoints();
            myString.add("Attacking Portals? " + PestUtils.ATTACK_PORTALS);
            myString.add("Pest Points: " + PestUtils.PEST_POINTS);

        }

        PaintUtil.createPaint(g, myString.toArray(String[]::new));
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