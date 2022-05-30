package scripts;

import dax.api_lib.WebWalkerServerApi;
import dax.api_lib.models.DaxCredentials;
import dax.api_lib.models.DaxCredentialsProvider;
import dax.shared.helpers.questing.QuestHelper;
import dax.teleports.Teleport;
import lombok.SneakyThrows;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.*;
import org.tribot.api2007.Combat;
import org.tribot.api2007.types.RSNPC;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.*;
import org.tribot.script.sdk.*;
import org.tribot.script.sdk.types.Area;
import org.tribot.script.sdk.types.World;
import org.tribot.script.sdk.types.WorldTile;
import scripts.MoneyMaking.ClockWorks.MakeClockWork;
import scripts.QuestPackages.APorcineOfInterest.APorcineOfInterest;
import scripts.QuestPackages.AnimalMagnetism.AnimalMagnetism;
import scripts.QuestPackages.AscentOfArceuus.AscentOfArceuus;
import scripts.QuestPackages.Barcrawl.BarCrawl;
import scripts.QuestPackages.BigChompyBirdHunting.BigChompyBirdHunting;
import scripts.QuestPackages.Biohazard.Biohazard;
import scripts.QuestPackages.BlackKnightsFortress.BlackKnight;
import scripts.QuestPackages.BlackKnightsFortress.BlackKnightsFortress;
import scripts.QuestPackages.BoneVoyage.BoneVoyage;
import scripts.QuestPackages.ClientOfKourend.ClientOfKourend;
import scripts.QuestPackages.Contact.Contact;
import scripts.QuestPackages.CooksAssistant.CooksAssistant;
import scripts.QuestPackages.CreatureOfFenkenstrain.CreatureOfFenkenstrain;
import scripts.QuestPackages.DeathPlateau.DeathPlateau;
import scripts.QuestPackages.DeathsOffice.DeathsOffice;
import scripts.QuestPackages.DemonSlayer.DemonSlayer;
import scripts.QuestPackages.DepthsofDespair.DepthsOfDespair;
import scripts.QuestPackages.DesertTreasure.DesertTreasure;
import scripts.QuestPackages.DigSite.DigSite;
import scripts.QuestPackages.DoricsQuest.DoricsQuest;
import scripts.QuestPackages.DragonSlayer.DragonSlayer;
import scripts.QuestPackages.DreamMentor.DreamMentor;
import scripts.QuestPackages.DruidicRitual.DruidicRitual;
import scripts.QuestPackages.DwarfCannon.DwarfCannon;
import scripts.QuestPackages.ElementalWorkshopI.ElementalWorkshop;
import scripts.QuestPackages.EnakhrasLament.EnakhrasLament;
import scripts.QuestPackages.EnterTheAbyss.EnterTheAbyss;
import scripts.QuestPackages.ErnestTheChicken.ErnestTheChicken;
import scripts.QuestPackages.FairyTalePt1.FairyTalePt1;
import scripts.QuestPackages.FamilyCrest.FamilyCrest;
import scripts.QuestPackages.FightArena.FightArena;
import scripts.QuestPackages.FishingContest.FishingContest;
import scripts.QuestPackages.FremTrials.FremTrials;
import scripts.QuestPackages.FremennikIsles.FremennikIsles;
import scripts.QuestPackages.GertrudesCat.GertrudesCat;
import scripts.QuestPackages.GhostsAhoy.GhostsAhoy;
import scripts.QuestPackages.GoblinDiplomacy.GoblinDiplomacy;
import scripts.QuestPackages.GrandTree.GrandTree;
import scripts.QuestPackages.HauntedMine.HauntedMine;
import scripts.QuestPackages.HazeelCult.HazeelCult;
import scripts.QuestPackages.HeroesQuest.HeroesQuestBlackArmsGang;
import scripts.QuestPackages.HolyGrail.HolyGrail;
import scripts.QuestPackages.HorrorFromTheDeep.HorrorFight;
import scripts.QuestPackages.HorrorFromTheDeep.HorrorFromTheDeep;
import scripts.QuestPackages.ImpCatcher.ImpCatcher;
import scripts.QuestPackages.JunglePotion.JunglePotion;
import scripts.QuestPackages.KnightsSword.KnightsSword;
import scripts.QuestPackages.KourendFavour.ArceuusLibrary.ArceuusLibrary;
import scripts.QuestPackages.KourendFavour.ArceuusLibrary.State;
import scripts.QuestPackages.KourendFavour.MakeCompost;
import scripts.QuestPackages.KourendFavour.Piscarlilius.PiscarililusFavour;
import scripts.QuestPackages.LegendsQuest.LegendsQuest;
import scripts.QuestPackages.LostCity.LostCity;
import scripts.QuestPackages.LostTribe.LostTribe;
import scripts.QuestPackages.LunarDiplomacy.*;
import scripts.QuestPackages.MerlinsCrystal.MerlinsCrystal;
import scripts.QuestPackages.MonkeyMadnessI.MonkeyMadnessI;
import scripts.QuestPackages.MonksFriend.MonksFriend;
import scripts.QuestPackages.MountainDaughter.MountainDaughter;
import scripts.QuestPackages.MurderMystery.MurderMystery;
import scripts.QuestPackages.NatureSpirit.NatureSpirit;
import scripts.QuestPackages.OneSmallFavour.OneSmallFavour;

import scripts.QuestPackages.PiratesTreasure.PiratesTreasure;
import scripts.QuestPackages.PlagueCity.PlagueCity;
import scripts.QuestPackages.PriestInPeril.PriestInPeril;
import scripts.QuestPackages.PrinceAliRescue.PrinceAliRescue;
import scripts.QuestPackages.QueenOfThieves.QueenOfThieves;
import scripts.QuestPackages.RestlessGhost.RestlessGhost;
import scripts.QuestPackages.RfdCook.RfdCook;
import scripts.QuestPackages.RfdEvilDave.RfdEvilDave;
import scripts.QuestPackages.RfdGoblin.RfdGoblin;
import scripts.QuestPackages.RfdLumbridgeGuide.RfdLumbridgeGuide;
import scripts.QuestPackages.RfdMonkey.RfdMonkey;
import scripts.QuestPackages.RfdPiratePete.RfdPiratePete;
import scripts.QuestPackages.RfdSkratch.RfdSkratch;
import scripts.QuestPackages.RomeoAndJuliet.RomeoAndJuliet;
import scripts.QuestPackages.RuneMysteries.RuneMysteries;
import scripts.QuestPackages.SeaSlug.SeaSlug;
import scripts.QuestPackages.ShadowOfTheStorm.ShadowOfTheStorm;
import scripts.QuestPackages.SheepShearer.SheepShearer;
import scripts.QuestPackages.ShiloVillage.ShilloVillage;
import scripts.QuestPackages.TearsOfGuthix.TearsOfGuthix;
import scripts.QuestPackages.TempleOfIkov.TempleOfIkov;
import scripts.QuestPackages.TheFeud.TheFeud;
import scripts.QuestPackages.TheGolem.TheGolem;
import scripts.QuestPackages.TheHandInTheSand.TheHandInTheSand;
import scripts.QuestPackages.TouristTrap.TouristTrap;
import scripts.QuestPackages.TreeGnomeVillage.TreeGnomeVillage;
import scripts.QuestPackages.TrollStronghold.TrollStronghold;
import scripts.QuestPackages.UnderGroundPass.UndergroundPass;
import scripts.QuestPackages.VampyreSlayer.VampyreSlayer;
import scripts.QuestPackages.VarrockMuseum.VarrockMuseum;
import scripts.QuestPackages.WaterfallQuest.WaterfallQuest;
import scripts.QuestPackages.WitchsHouse.WitchsHouse;
import scripts.QuestPackages.WitchsPotion.WitchsPotion;
import scripts.QuestPackages.XMarksTheSpot.XMarksTheSpot;
import scripts.QuestPackages.ZogreFleshEaters.ZogreFleshEaters;
import scripts.QuestPackages.kingsRansom.KingsRansom;
import scripts.QuestPackages.lairoftarnrazorlor.TarnRoute;
import scripts.QuestPackages.observatoryQuest.ObservatoryQuest;
import scripts.QuestPackages.recruitmentDrive.RecruitmentDrive;
import scripts.QuestSteps.QuestTask;
import scripts.QuestUtils.SupportedQuests;
import scripts.QuestUtils.TaskSet;
import scripts.QuestUtils.Vars;
import scripts.QuestPackages.ShieldOfArrav.BlackArmsGang;
import scripts.QuestPackages.ShieldOfArrav.PheonixGang;
import scripts.Tasks.Task;
import scripts.QuestPackages.TribalTotem.TribalTotem;
import scripts.QuestPackages.icthlarinslittlehelper.Icthlarinslittlehelper;
import scripts.dax.tracker.DaxTracker;
import scripts.gui.GUI;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

@ScriptManifest(name = "cQuester v2", authors = {"Cass2186"}, category = "Quests")
public class cQuesterV2 extends Script implements Painting, Starting, Ending, Arguments, MessageListening07 {

    public static AtomicBoolean isRunning = new AtomicBoolean(true);
    public static String status = "Initializing";
    public static int gameSettingInt = 0;
    public static List<QuestTask> taskList = new ArrayList<>();

    public cQuesterV2() {
        try {
            Vars.get().daxTracker = new DaxTracker(
                    "3d50ebcf-daa0-4d79-82fd-c76633f97ed0",
                    "tqRBZL6zoacmgLn7pwANpA==");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onStart() {
        AntiBan.create();

        Teleport.clearTeleportBlacklist();
        Teleport.blacklistTeleports(Teleport.RING_OF_WEALTH_MISCELLANIA);

        WebWalkerServerApi.getInstance().setDaxCredentialsProvider(new DaxCredentialsProvider() {
            @Override
            public DaxCredentials getDaxCredentials() {
                return new DaxCredentials("sub_H0C2eULZfbWeoF", "acb35610-d868-4ce8-8797-0d2e659f87f4");
                //return new DaxCredentials("sub_DPjXXzL5DeSiPf", " PUBLIC-KEY");

            }
        });
        SupportedQuests.getTotalQP();
    }

    @SneakyThrows
    @Override
    public void run() {
        if (Tribot.getUsername().equalsIgnoreCase("cass2186") || Tribot.getUsername().equalsIgnoreCase("Whipz")
                || Tribot.getUsername().equalsIgnoreCase("SkrrtNick") || Tribot.getUsername().equalsIgnoreCase("Destinbrown1225")) {

        } else {
            Log.error("Username is not permitted. Message me on discord for access");
            return;
        }
        URL lcn = new URL("https://raw.githubusercontent.com/Cass2186/cScriptsV4/main/scripts/my-script2/src/scripts/gui/cQuesterGUI.fxml");

        GUI gui = new GUI(lcn);

        if (Vars.get().shouldShowGui) {
            Log.debug("Loading GUI");
            gui.show();
            while (gui.isOpen())
                Waiting.wait(500);
        }
        org.tribot.script.sdk.Options.AttackOption.setNpcAttackOption(org.tribot.script.sdk.Options.AttackOption.LEFT_CLICK_WHERE_AVAILABLE);

        Utils.setCameraZoomAboveDefault();

        if (!Combat.isAutoRetaliateOn())
            Combat.setAutoRetaliate(true);

        Vars.get().startingQuestPoints = MyPlayer.getQuestPoints();

        TaskSet tasks;
        if (taskList == null) {
            General.println("[Debug]: Task list is null");
            tasks = new TaskSet();
        } else {
            tasks = new TaskSet(taskList);
        }


        isRunning.set(true);
        while (isRunning.get()) {
            General.sleep(50);
            QuestTask task = tasks.getValidTask();
            if (cQuesterV2.taskList.size() == 0) {
                Log.info("Finished all quests");
                break;
            }
            if (DeathsOffice.shouldHandleDeath()) {
                Log.error("Died - Handling death");
                DeathsOffice.collectItems();
            }
            if (task != null) {
                Vars.get().questName = task.questName();
                task.execute();
            } else {
                Log.warn("Task is null");
            }
            if (taskList.isEmpty())
                break;
            else {
                Log.info("Task list size is: " + taskList.size());
                Log.info("Task Quest is: " + taskList.get(0).questName());
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
                handleArgs(arg);
            } catch (Exception e) {
                e.printStackTrace();
                General.println("[Error]: Args are incorrect", Color.RED);
            }
        }
    }

    Area CASTLE_WARS = Area.fromRectangle(new WorldTile(2437, 3093, 0), new WorldTile(2444, 3087, 0));
    List<WorldTile> allTiles = CASTLE_WARS.getAllTiles();
    HashMap<WorldTile, Integer> tileMap = new HashMap<>();

    public void populateMap() {
        if (tileMap.size() == 0) {
            for (WorldTile t : allTiles) {
                tileMap.put(t, 0);
            }
        }
    }

    @Override
    public void onPaint(Graphics g) {
        populateMap();
        double timeRan = getRunningTime();
        double timeRanMin = (timeRan / 3600000);
        List<String> myString;
        RSNPC[] inadeq = NPCs.find(3473);
        Vars.get().currentQuestPoints = Utils.getQuestPoints();
        if (inadeq.length > 0) {
            myString = new ArrayList<>(Arrays.asList(
                    "cQuester v2",
                    "Running For: " + Timing.msToString(getRunningTime()),
                    "Quest: " + Vars.get().questName,
                    "Task: " + status,
                    "Varbit 3633  (" + Utils.getVarBitValue(3633) + ")",
                    "Is interacting with: " + inadeq[0].isInteractingWithMe()
            ));
        } else {
            myString = new ArrayList<>(Arrays.asList(
                    "cQuester v2",
                    "Running For: " + Timing.msToString(getRunningTime()),
                    "Quest: " + Vars.get().questName,
                    "Task: " + status,
                    "Tasks To Do: " + taskList.size(),
                    "Gained QP: " + (MyPlayer.getQuestPoints() - Vars.get().startingQuestPoints)
            ));
        }
        if (taskList.size() > 0 && taskList.get(0).equals(FamilyCrest.get())) {
            myString.add("northRoomLever " + FamilyCrest.northRoomLever);
            myString.add("northWallLever " + FamilyCrest.northWallLever);
            myString.add("southRoomLever " + FamilyCrest.southRoomLever);
        }

        PaintUtil.createPaint(g, myString.toArray(String[]::new));


    }


    @Override
    public void onEnd() {
        int gainedQp = MyPlayer.getQuestPoints() - Vars.get().startingQuestPoints;
        Log.info("QP Gained: " + gainedQp);
        Vars.get().daxTracker.trackData("Quest Points", gainedQp);
        Vars.get().daxTracker.stop();
    }

    @Override
    public void serverMessageReceived(String message) {
        //chatRecorder.registerServerMessage(message);
        Utils.handleRecoilMessage(message);
        if (message.contains("You haven't got enough.")) {
            cQuesterV2.isRunning.set(false);
            throw new NullPointerException("Do not have enough coins, ending");
        }
        if (message.contains("you are dead")) {
            taskList.add(DeathsOffice.get());
        }
        // QuestHelper.autocastAndGrandExchangeFailSafe(message);
        // Utils.handleBlockedFireMessage(message);
        if (taskList.size() > 0) {
            if (taskList.get(0).equals(FremTrials.get())) {
                FremTrials.get().handleTalismanMessage(message);
                FremTrials.get().handleDraugenIsHere(message);
            }
            if (taskList.get(0).equals(ZogreFleshEaters.get())) {
                ZogreFleshEaters.handlePortraitMessage(message);
            }
            // if (RFD_PIRATE) RfdPirate.handleRockMessage(message);
            if (taskList.get(0).equals(ArceuusLibrary.get())) {
                if (message.equals("You don't find anything useful here.")) {
                    State.get().getLastBookcaseTile().ifPresent(tile -> {
                        if (Utils.isLookingTowards(Player.getRSPlayer(), tile, 1)) {
                            State.get().getLibrary().mark(tile, null);
                            State.get().setLastBookcaseTile(null);
                        } else {
                            State.get().setLastBookcaseTile(null);
                        }
                    });
                }
            }
        }
    }

    public static void handleArgs(String arg) {
        if (arg.toLowerCase().contains("horror")) {

            General.println("[Args]: Added Horror from the deep");
            if (arg.toLowerCase().contains("buybook")) {
                General.println("[Args]: Buying all books for horror");
                HorrorFight.shouldBuyAllBooks = true;
            }
            taskList.addAll(Arrays.asList(BarCrawl.get(), HorrorFromTheDeep.get()));
        } else if (arg.toLowerCase().contains("ghostsahoy")) {
            General.println("[Args]: Added Ghosts Ahoy");
            taskList.add(GhostsAhoy.get());
        } else if (arg.toLowerCase().contains("gertrude")) {
            General.println("[Args]: Added Gertrude's cat");
            taskList.add(GertrudesCat.get());

        } else if (arg.toLowerCase().contains("witchspotion")) {
            General.println("[Args]: Added Witch's Potion");
            taskList.add(WitchsPotion.get());
        } else if (arg.toLowerCase().contains("deathplateau")) {
            General.println("[Args]: Added Death plateau ");
            taskList.add(DeathPlateau.get());
            //   Vars.get().questHashMap.put(new DeathPlateau().getQuest(), taskList);
        } else if (arg.toLowerCase().contains("contact")) {
            General.println("[Args]: Added Contact! (and sub quests)");
            taskList.add(PrinceAliRescue.get());
            taskList.add(Icthlarinslittlehelper.get());
            taskList.add(Contact.get());
        } else if (arg.toLowerCase().contains("legends")) {
            General.println("[Args]: Added Legends quest");
            taskList.add(LegendsQuest.get());
        } else if (arg.toLowerCase().contains("dreammentor")) {
            General.println("[Args]: Added Dream Mentor");
            taskList.add(new DreamMentor());
        } else if (arg.toLowerCase().contains("hazeel")) {
            General.println("[Args]: Added Hazeel cult");
            taskList.add(HazeelCult.get());
        } else if (arg.toLowerCase().contains("heroes")) {
            General.println("[Args]: Added Heroes quest");
            taskList.addAll(Arrays.asList(HeroesQuestBlackArmsGang.get()));
        } else if (arg.toLowerCase().contains("lunardiplomacy")) {
            General.println("[Args]: Added Lunar Diplomacy");
            taskList.add(LunarDiplomacy.get());
            taskList.addAll(Arrays.asList(new ChanceChallenge(),
                    new MemoryChallenge(),
                    new MimicChallenge(),
                    new RaceChallenge(),
                    new NumberChallenge()));
        } else if (arg.toLowerCase().contains("onesmall")) {
            General.println("[Args]: Added One small favour");
            //   taskList.addAll(Arrays.asList(new OneSmallFavour()));
        } else if (arg.toLowerCase().contains("pheonix")) {
            General.println("[Args]: Added Shield of Arav Pheonix Gang");
            taskList.addAll(Arrays.asList(PheonixGang.get()));
        }
        if (arg.toLowerCase().contains("piscfavour")) {
            General.println("[Args]: Added Piscarilius Favour");
            taskList.addAll(List.of(PiscarililusFavour.get()));
        }
        if (arg.toLowerCase().contains("queenofthieves")) {
            General.println("[Args]: Added Queen Of Thieves");
            taskList.addAll(List.of(QueenOfThieves.get()));
        }
        if (arg.toLowerCase().contains("blackarm")) {
            General.println("[Args]: Added Shield of Arav Black arms Gang");
            taskList.addAll(List.of(BlackArmsGang.get()));
        }
        if (arg.toLowerCase().contains("fishingcontest")) {
            General.println("[Args]: Added Fishing Contest");
            taskList.add(FishingContest.get());
        }
        if (arg.toLowerCase().contains("cooksassist")) {
            General.println("[Args]: Added Cook's Assistant ");
            taskList.add(CooksAssistant.get());
        }
        if (arg.toLowerCase().contains("rfdevildave")) {
            General.println("[Args]: Added RFD Evil Dave");
            taskList.add(RfdEvilDave.get());
        }
        if (arg.toLowerCase().contains("rfdcook")) {
            General.println("[Args]: Added RfdCook Questline");
            //TODO add gertrudes cat, sea slug and fm training
            taskList.add(CooksAssistant.get());
            if (Skill.COOKING.getActualLevel() < 10)
                taskList.add(GertrudesCat.get());
            if (Skill.FIREMAKING.getActualLevel() >= 30)
                taskList.add(SeaSlug.get());
            taskList.add(FishingContest.get());
            taskList.add(RfdCook.get());
        }
        if (arg.toLowerCase().contains("tribaltotem")) {
            General.println("[Args]: Added Tribal totem");
            taskList.add(TribalTotem.get());
        } else if (arg.toLowerCase().contains("barcrawl")) {
            General.println("[Args]: Added Barcrawl");
            taskList.add(BarCrawl.get());
        } else if (arg.toLowerCase().contains("animalmag")) {

            taskList.add(ErnestTheChicken.get());
            taskList.add(RestlessGhost.get());
            taskList.add(PriestInPeril.get());
            taskList.add(AnimalMagnetism.get());
            General.println("[Args]: Added animal Magnetism");
        } else if (arg.toLowerCase().contains("porcine")) {
            General.println("[Args]: Added A Porcine Of Interest");
            taskList.add(APorcineOfInterest.get());
        } else if (arg.toLowerCase().contains("littlehelper")) {
            General.println("[Args]: Added Icthlarins Little Helper");
            taskList.add(GertrudesCat.get());
            taskList.add(Icthlarinslittlehelper.get());
        } else if (arg.toLowerCase().contains("druidicritual")) {
            General.println("[Args]: Added Druidic Ritual");
            taskList.add(DruidicRitual.get());
        } else if (arg.toLowerCase().contains("impcatcher")) {
            General.println("[Args]: Added Imp catcher");
            taskList.add(ImpCatcher.get());
        } else if (arg.toLowerCase().contains("clockwork")) {
            General.println("[Args]: Added ClockWork");
            taskList.add(MakeClockWork.get());
        } else if (arg.toLowerCase().contains("legend")) {
            General.println("[Args]: Added Legend's quest");
            taskList.add(new LegendsQuest());
        } else if (arg.toLowerCase().contains("holygrail")) {
            General.println("[Args]: Added Holy Grail");
            taskList.add(MerlinsCrystal.get());
            taskList.add(HolyGrail.get());
        } else if (arg.toLowerCase().contains("witchshouse")) {
            General.println("[Args]: Added witchshouse");
            taskList.add(WitchsHouse.get());
        } else if (arg.toLowerCase().contains("xmark")) {
            General.println("[Args]: Added X Marks the Spot");
            taskList.add(XMarksTheSpot.get());
        } else if (arg.toLowerCase().contains("dragonslayer")) {
            General.println("[Args]: Added Dragon Slayer");
            taskList.add(DragonSlayer.get());
        } else if (arg.toLowerCase().contains("helper")) {
            General.println("[Args]: Added Icthlarin's little helper");
            taskList.add(GertrudesCat.get());
            taskList.add(Icthlarinslittlehelper.get());
        } else if (arg.toLowerCase().contains("depthsofdespair")) {
            General.println("[Args]: Added Depths of Despair");
            taskList.add(DepthsOfDespair.get());
        } else if (arg.toLowerCase().contains("seaslug")) {
            General.println("[Args]: Added Sea slug");
            taskList.add(SeaSlug.get());
        } else if (arg.toLowerCase().contains("fightarena")) {
            General.println("[Args]: Added Fight Arena");
            taskList.add(FightArena.get());
        } else if (arg.toLowerCase().contains("museum")) {
            General.println("[Args]: Added Varrock Museum");
            taskList.add(VarrockMuseum.get());
        } else if (arg.toLowerCase().contains("client")) {
            General.println("[Args]: Added Client of Kourend");
            taskList.add(XMarksTheSpot.get());
            taskList.add(ClientOfKourend.get());
        } else if (arg.toLowerCase().contains("doric")) {
            General.println("[Args]: Added Doric's Quest");
            taskList.add(DoricsQuest.get());
        } else if (arg.toLowerCase().contains("monksfriend")) {
            General.println("[Args]: Added Monk's Friend");
            taskList.add(MonksFriend.get());
        } else if (arg.toLowerCase().contains("priestinperil")) {
            General.println("[Args]: Added Priest In Peril");
            taskList.add(PriestInPeril.get());
        } else if (arg.toLowerCase().contains("startpart1")) {
            General.println("[Args]: Added Startpart1");
            taskList.add(ImpCatcher.get());
            taskList.add(WitchsPotion.get());
            taskList.add(WitchsHouse.get());
            taskList.add(WaterfallQuest.get());
        } else if (arg.toLowerCase().contains("vampireslayer") ||
                arg.toLowerCase().contains("vampyreslayer")) {
            General.println("[Args]: Added Vampyre slayer");
            taskList.add(VampyreSlayer.get());
        } else if (arg.toLowerCase().contains("fremmy")) {
            General.println("[Args]: Added Fremennick Trials");
            taskList.add(FremTrials.get());
        } else if (arg.toLowerCase().contains("waterfall")) {
            General.println("[Args]: Added Waterfall Quest");
            taskList.add(WaterfallQuest.get());
        } else if (arg.toLowerCase().contains("knightssword")) {
            General.println("[Args]: Added Knight's Sword");
            if (Skills.SKILLS.MINING.getActualLevel() < 10) {
                taskList.add(DoricsQuest.get());
                General.println("[Args]: Added Doric's Quest");
            }
            taskList.add(KnightsSword.get());
        } else if (arg.toLowerCase().contains("touristtrap")) {
            if (Skills.SKILLS.SMITHING.getActualLevel() < 20) {
                taskList.add(DoricsQuest.get());
                taskList.add(KnightsSword.get());
            }
            General.println("[Args]: Added Tourist Trap");
            taskList.add(TouristTrap.get());

        } else if (arg.toLowerCase().contains("monkeymadness")) {
            General.println("[Args]: Added Monkey Madness I & Dwarf Cannon");
            taskList.add(DwarfCannon.get());
            taskList.add(MonkeyMadnessI.get());
        } else if (arg.toLowerCase().contains("fairytale")) {
            General.println("[Args]: Added Fairy Tale line");
            taskList.add(PriestInPeril.get());
            taskList.add(DruidicRitual.get());
            taskList.add(RestlessGhost.get());
            taskList.add(JunglePotion.get());
            taskList.add(NatureSpirit.get());
            taskList.add(LostCity.get());
            taskList.add(FairyTalePt1.get());
        } else if (arg.toLowerCase().contains("shadowofthestorm")) {
            General.println("[Args]: Added Shadow of the Storm");
            taskList.add(TheGolem.get());
            taskList.add(ShadowOfTheStorm.get());
        } else if (arg.toLowerCase().contains("rfdmonkey")) {
            General.println("[Args]: Added RFD Monkey");

            taskList.add(RfdMonkey.get());
        } else if (arg.toLowerCase().contains("junglepotion")) {
            General.println("[Args]: Added Jungle Potion");
            taskList.add(DruidicRitual.get());
            taskList.add(JunglePotion.get());
        } else if (arg.toLowerCase().contains("dwarfcannon")) {
            General.println("[Args]: Added Dwarf cannon");
            taskList.add(DwarfCannon.get());
        } else if (arg.toLowerCase().contains("naturespirit")) {
            General.println("[Args]: Added Nature spirit");
            taskList.add(RestlessGhost.get());
            taskList.add(PriestInPeril.get());
            taskList.add(NatureSpirit.get());
        } else if (arg.toLowerCase().contains("losttribe")) {
            General.println("[Args]: Added Lost Tribe");
            taskList.add(LostTribe.get());
        } else if (arg.toLowerCase().contains("restlessghost")) {
            General.println("[Args]: Added Restless Ghost");
            taskList.add(RestlessGhost.get());
        } else if (arg.toLowerCase().contains("entertheabyss")) {
            General.println("[Args]: Added Enter The Abyss");
            taskList.add(EnterTheAbyss.get());
        } else if (arg.toLowerCase().contains("fenkenstrain")) {
            General.println("[Args]: Added Creature of Fenkenstrain");
            taskList.add(CreatureOfFenkenstrain.get());
        } else if (arg.toLowerCase().contains("zogre")) {
            General.println("[Args]: Added Zogre Flesh Eaters");
            taskList.add(ZogreFleshEaters.get());
        } else if (arg.toLowerCase().contains("thefeud")) {
            General.println("[Args]: Added The Feud");
            taskList.add(TheFeud.get());
        } else if (arg.toLowerCase().contains("treegnome")) {
            General.println("[Args]: Added TGV");
            taskList.add(TreeGnomeVillage.get());
        } else if (arg.toLowerCase().contains("undergroundpass")) {
            General.println("[Args]: Added UGP");
            taskList.add(UndergroundPass.get());
            //TODO Add biohazard & plague city
        } else if (arg.toLowerCase().contains("demonslayer")) {
            General.println("[Args]: Added Demon Slayer");
            taskList.add(DemonSlayer.get());
        } else if (arg.toLowerCase().contains("bonevoyage")) {
            General.println("[Args]: Added Bone voyage line");
            taskList.add(RuneMysteries.get());
            taskList.add(HazeelCult.get());
            taskList.add(MerlinsCrystal.get());
            taskList.add(GrandTree.get());
            taskList.add(PriestInPeril.get());
            taskList.add(VarrockMuseum.get());
            taskList.add(BoneVoyage.get());
        } else if (arg.toLowerCase().contains("grandtree")) {
            General.println("[Args]: Added The grand tree");
            taskList.add(GrandTree.get());
        } else if (arg.toLowerCase().contains("ernest")) {
            General.println("[Args]: Added Ernest the Chicken");
            taskList.add(ErnestTheChicken.get());
        } else if (arg.toLowerCase().contains("rfdgoblin")) {
            General.println("[Args]: Added RFD Goblin");
            taskList.add(RfdGoblin.get());
        } else if (arg.toLowerCase().contains("merlin")) {
            General.println("[Args]: Added Merlin's crystal");
            taskList.add(MerlinsCrystal.get());
        } else if (arg.toLowerCase().contains("hosafavour")) {
            General.println("[Args]: Added Hosidius favour");
            taskList.add(MakeCompost.get());
        } else if (arg.toLowerCase().contains("runemysteries")) {
            General.println("[Args]: Added Rune Mysteries");
            taskList.add(RuneMysteries.get());
        } else if (arg.toLowerCase().contains("elementalworkshop")) {
            General.println("[Args]: Added Elemental Workshop");
            taskList.add(ElementalWorkshop.get());
        } else if (arg.toLowerCase().contains("hauntedmine")) {
            General.println("[Args]: Added haunted Mine");
            taskList.add(HauntedMine.get());
        } else if (arg.toLowerCase().contains("tarnslair") ||
                arg.toLowerCase().contains("lairoftarn")) {
            General.println("[Args]: Added Tarn's Lair");
            taskList.add(TarnRoute.get());
        } else if (arg.toLowerCase().contains("familycrest")) {
            General.println("[Args]: Added Family crest");
            taskList.add(FamilyCrest.get());
        } else if (arg.toLowerCase().contains("test")) {
            General.println("[Args]: Added Test");
            taskList.add(TestClass.get());
        } else if (arg.toLowerCase().contains("murdermystery")) {
            General.println("[Args]: Added Murder Mystery");
            taskList.add(MurderMystery.get());
        } else if (arg.toLowerCase().contains("goblindiplomacy")) {
            General.println("[Args]: Added goblin Diplomacy");
            taskList.add(GoblinDiplomacy.get());
        } else if (arg.toLowerCase().contains("romeo")) {
            General.println("[Args]: Added Romeo and Juliet");
            taskList.add(RomeoAndJuliet.get());
        } else if (arg.toLowerCase().contains("biohazard")) {
            General.println("[Args]: Added Biohazard");
            taskList.add(PlagueCity.get());
            taskList.add(Biohazard.get());
        } else if (arg.toLowerCase().contains("plaguecity")) {
            General.println("[Args]: Added Plague City");
            taskList.add(PlagueCity.get());
        } else if (arg.toLowerCase().contains("mtd") || arg.toLowerCase().contains("mountaindaughter")) {
            General.println("[Args]: Added Mountain Daughter");
            taskList.add(MountainDaughter.get());
        } else if (arg.toLowerCase().contains("library")) {
            General.println("[Args]: Added Arceuus library");
            taskList.add(ArceuusLibrary.get());
        } else if (arg.toLowerCase().contains("ascentofarceuus")) {
            General.println("[Args]: Added Ascent of Arceuus");
            taskList.add(AscentOfArceuus.get());
        } else if (arg.toLowerCase().contains("rfdskratch")) {
            General.println("[Args]: Added RFD skratch ");
            taskList.add(BigChompyBirdHunting.get());
            taskList.add(RfdSkratch.get());
        } else if (arg.toLowerCase().contains("bigchompy")) {
            General.println("[Args]: Added Big Chompy");
            taskList.add(BigChompyBirdHunting.get());
        } else if (arg.toLowerCase().contains("lostcity")) {
            General.println("[Args]: Added Lost City");
            taskList.add(LostCity.get());
        } else if (arg.toLowerCase().contains("templeofikov")) {
            General.println("[Args]: Added  Temple of Ikov");
            taskList.add(TempleOfIkov.get());
        } else if (arg.toLowerCase().contains("trollstronghold")) {
            General.println("[Args]: Added  Troll Stronghold");
            taskList.add(TrollStronghold.get());
        } else if (arg.toLowerCase().contains("rfdguide")) {
            General.println("[Args]: Added  RFD Guide");
            taskList.add(RfdLumbridgeGuide.get());
        } else if (arg.toLowerCase().contains("princeali")) {
            General.println("[Args]: Added Prince ali rescue");
            taskList.add(PrinceAliRescue.get());
        } else if (arg.toLowerCase().contains("blackknight")) {
            General.println("[Args]: Added BKF");
            taskList.add(BlackKnight.get());
        } else if (arg.toLowerCase().contains("tearsofguthix")) {
            General.println("[Args]: Added tears ");
            taskList.add(TearsOfGuthix.get());
        } else if (arg.toLowerCase().contains("rfdpirate")) {
            General.println("[Args]: Added RFD Pirate pete ");
            taskList.add(RfdPiratePete.get());
        } else if (arg.toLowerCase().contains("lament")) {
            General.println("[Args]: Added Enakrah's lament  ");
            taskList.add(EnakhrasLament.get());
        } else if (arg.toLowerCase().contains("digsite")) {
            General.println("[Args]: Added Dig Site  ");
            taskList.add(DigSite.get());
        } else if (arg.toLowerCase().contains("sheepshearer")) {
            General.println("[Args]: Added Sheep Shearer");
            taskList.add(SheepShearer.get());
        } else if (arg.toLowerCase().contains("shilovillage")) {
            General.println("[Args]: Added Shilo Village ");
            taskList.add(ShilloVillage.get());
        } else if (arg.toLowerCase().contains("piratestreas")) {
            General.println("[Args]: Added Pirates Treasure ");
            taskList.add(PiratesTreasure.get());
        } else if (arg.toLowerCase().contains("deserttreas")) {
            General.println("[Args]: Added Desert Treasure ");
            taskList.add(DesertTreasure.get());
        } else if (arg.toLowerCase().contains("observ")) {
            General.println("[Args]: Added Observatory Quest ");
            taskList.add(ObservatoryQuest.get());
        } else if (arg.toLowerCase().contains("kingsransom")) {
            General.println("[Args]: Added Kings Ransom ");
            taskList.add(KingsRansom.get());
        } else if (arg.toLowerCase().contains("fremisles")) {
            General.println("[Args]: Added Fremennick Isles");
            taskList.add(FremennikIsles.get());
        } else if (arg.toLowerCase().contains("recruitment")) {
            General.println("[Args]: Added Recruitment Drive");
            taskList.add(RecruitmentDrive.get());
        }
        else if (arg.toLowerCase().contains("sand")) {
            General.println("[Args]: Added hand in the sand");
            taskList.add(TheHandInTheSand.get());
        }
        if (taskList.size() > 0)
            Vars.get().shouldShowGui = false;
    }

}
