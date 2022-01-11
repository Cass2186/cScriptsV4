package scripts;

import dax.api_lib.WebWalkerServerApi;
import dax.api_lib.models.DaxCredentials;
import dax.api_lib.models.DaxCredentialsProvider;
import dax.shared.helpers.questing.Quest;
import dax.shared.helpers.questing.QuestHelper;
import dax.teleports.Teleport;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Game;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSNPC;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.*;
import org.tribot.script.sdk.Log;
import scripts.MoneyMaking.ClockWorks.MakeClockWork;
import scripts.QuestPackages.APorcineOfInterest.APorcineOfInterest;
import scripts.QuestPackages.AnimalMagnetism.AnimalMagnetism;
import scripts.QuestPackages.Barcrawl.BarCrawl;
import scripts.QuestPackages.BoneVoyage.BoneVoyage;
import scripts.QuestPackages.ClientOfKourend.ClientOfKourend;
import scripts.QuestPackages.Contact.Contact;
import scripts.QuestPackages.CooksAssistant.CooksAssistant;
import scripts.QuestPackages.CreatureOfFenkenstrain.CreatureOfFenkenstrain;
import scripts.QuestPackages.DeathPlateau.DeathPlateau;
import scripts.QuestPackages.DemonSlayer.DemonSlayer;
import scripts.QuestPackages.DepthsofDespair.DepthsOfDespair;
import scripts.QuestPackages.DoricsQuest.DoricsQuest;
import scripts.QuestPackages.DragonSlayer.DragonSlayer;
import scripts.QuestPackages.DreamMentor.DreamMentor;
import scripts.QuestPackages.DruidicRitual.DruidicRitual;
import scripts.QuestPackages.DwarfCannon.DwarfCannon;
import scripts.QuestPackages.ElementalWorkshopI.ElementalWorkshop;
import scripts.QuestPackages.EnterTheAbyss.EnterTheAbyss;
import scripts.QuestPackages.ErnestTheChicken.ErnestTheChicken;
import scripts.QuestPackages.FairyTalePt1.FairyTalePt1;
import scripts.QuestPackages.FightArena.FightArena;
import scripts.QuestPackages.FishingContest.FishingContest;
import scripts.QuestPackages.FremTrials.FremTrials;
import scripts.QuestPackages.GertrudesCat.GertrudesCat;
import scripts.QuestPackages.GhostsAhoy.GhostsAhoy;
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
import scripts.QuestPackages.KourendFavour.MakeCompost;
import scripts.QuestPackages.LegendsQuest.LegendsQuest;
import scripts.QuestPackages.LostTribe.LostTribe;
import scripts.QuestPackages.LunarDiplomacy.*;
import scripts.QuestPackages.MerlinsCrystal.MerlinsCrystal;
import scripts.QuestPackages.MonkeyMadnessI.MonkeyMadnessI;
import scripts.QuestPackages.MonksFriend.MonksFriend;
import scripts.QuestPackages.NatureSpirit.NatureSpirit;
import scripts.QuestPackages.OneSmallFavour.OneSmallFavour;

import scripts.QuestPackages.PriestInPeril.PriestInPeril;
import scripts.QuestPackages.RestlessGhost.RestlessGhost;
import scripts.QuestPackages.RfdCook.RfdCook;
import scripts.QuestPackages.RfdEvilDave.RfdEvilDave;
import scripts.QuestPackages.RfdGoblin.RfdGoblin;
import scripts.QuestPackages.RfdMonkey.RfdMonkey;
import scripts.QuestPackages.RuneMysteries.RuneMysteries;
import scripts.QuestPackages.SeaSlug.SeaSlug;
import scripts.QuestPackages.ShadowOfTheStorm.ShadowOfTheStorm;
import scripts.QuestPackages.TheFeud.TheFeud;
import scripts.QuestPackages.TouristTrap.TouristTrap;
import scripts.QuestPackages.TreeGnomeVillage.TreeGnomeVillage;
import scripts.QuestPackages.UnderGroundPass.UndergroundPass;
import scripts.QuestPackages.VampyreSlayer.VampyreSlayer;
import scripts.QuestPackages.VarrockMuseum.VarrockMuseum;
import scripts.QuestPackages.WaterfallQuest.WaterfallQuest;
import scripts.QuestPackages.WitchsHouse.WitchsHouse;
import scripts.QuestPackages.WitchsPotion.WitchsPotion;
import scripts.QuestPackages.XMarksTheSpot.XMarksTheSpot;
import scripts.QuestPackages.ZogreFleshEaters.ZogreFleshEaters;
import scripts.QuestSteps.QuestTask;
import scripts.QuestUtils.TaskSet;
import scripts.QuestUtils.Vars;
import scripts.QuestPackages.ShieldOfArrav.BlackArmsGang;
import scripts.QuestPackages.ShieldOfArrav.PheonixGang;
import scripts.Tasks.Task;
import scripts.QuestPackages.TribalTotem.TribalTotem;
import scripts.QuestPackages.icthlarinslittlehelper.Icthlarinslittlehelper;

import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

@ScriptManifest(name = "cQuester v2", authors = {"Cass2186"}, category = "Quests")
public class cQuesterV2 extends Script implements Painting, Starting, Ending, Arguments, MessageListening07 {

    public static AtomicBoolean isRunning = new AtomicBoolean(true);
    public static String status = "Initializing";
    public static String gameSetting = "Initializing";
    public static int gameSettingInt = 0;
    public static String currentQuest = "Initializing";
    public static List<QuestTask> taskList = new ArrayList<>();
    // public List<Quest> questList = new ArrayList<>();
    public int nextStaminaPotionUse = General.randomSD(50, 85, 75, 5);


    @Override
    public void onStart() {
        // action();

        AntiBan.create();

        Teleport.clearTeleportBlacklist();
        Teleport.blacklistTeleports(Teleport.RING_OF_WEALTH_MISCELLANIA);

        WebWalkerServerApi.getInstance().setDaxCredentialsProvider(new DaxCredentialsProvider() {
            @Override
            public DaxCredentials getDaxCredentials() {
                return new DaxCredentials("sub_DPjXXzL5DeSiPf", "PUBLIC-KEY");
            }
        });
        Utils.setCameraZoomAboveDefault();
    }

    @Override
    public void run() {
        Vars.get().startingQuestPoints = Utils.getQuestPoints();
        TaskSet tasks;
        if (taskList == null) {
            General.println("[Debug]: Task list is null");
            tasks = new TaskSet(
                    // new StartHorror(),
                    //  new LightHouseSteps(),
                    // new HorrorFight()
                    // new BlackArmsGang()
                    //  new PheonixGang()
                    // new GhostsAhoy()
                    //new DreamMentor()
                    //  new ChanceChallenge(),
                    // new MemoryChallenge(),
                    //  new MimicChallenge(),
                    // new NumberChallenge()
                    // new HeroesQuestBlackArmsGang()
            );
        } else {
            tasks = new TaskSet(taskList);
        }


        isRunning.set(true);
        while (isRunning.get()) {
            General.sleep(20, 40);
            QuestTask task = tasks.getValidTask();
            if (cQuesterV2.taskList.size() == 0) {
                Log.log("[Debug]: Finished all quests");
                break;
            }

            if (task != null) {
                gameSetting = task.questName();
                task.execute();
            } else {
                Log.log("[Debug]: Task is null");
            }
            if (taskList.isEmpty())
                break;
            else {
                Log.log("[Debug]: Task list size is: " + taskList.size());
                Log.log("[Debug]: Task Quest is: " + taskList.get(0).questName());
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
                    General.println("[Args]: Added Contact!");
                    taskList.add(Contact.get());
                    //  Vars.get().questHashMap.put(new Contact().getQuest(), taskList);
                } else if (arg.toLowerCase().contains("legends")) {
                    General.println("[Args]: Added Legends quest");
                    // taskList.add(new LegendsQuest());
                } else if (arg.toLowerCase().contains("dreammentor")) {
                    General.println("[Args]: Added Dream Mentor");
                    taskList.add(new DreamMentor());
                } else if (arg.toLowerCase().contains("hazeel")) {
                    General.println("[Args]: Added Hazeel cult");
                    taskList.add(HazeelCult.get());
                } else if (arg.toLowerCase().contains("heroes")) {
                    General.println("[Args]: Added Heroes quest");
                    // taskList.addAll(Arrays.asList(new HeroesQuestBlackArmsGang()));
                } else if (arg.toLowerCase().contains("lunardiplomacy")) {
                    General.println("[Args]: Added Lunar Diplomacy");
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
                    //     taskList.addAll(Arrays.asList(new PheonixGang()));
                }
                if (arg.toLowerCase().contains("blackarm")) {
                    General.println("[Args]: Added Shield of Arav Black arms Gang");
                    //    taskList.addAll(Arrays.asList(new BlackArmsGang()));
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
                    taskList.add(FairyTalePt1.get());
                } else if (arg.toLowerCase().contains("shadowofthestorm")) {
                    General.println("[Args]: Added Shadow of the Storm");
                    // taskList.add(TheGolem.get());
                    taskList.add(ShadowOfTheStorm.get());
                } else if (arg.toLowerCase().contains("rfdmonkey")) {
                    General.println("[Args]: Added RFD Monkey");
                    // taskList.add(TheGolem.get());
                    taskList.add(RfdMonkey.get());
                } else if (arg.toLowerCase().contains("junglepotion")) {
                    General.println("[Args]: Added Jungle Potion");
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
                    General.println("[Args]: Added Bone voyage");
                    //taskList.add(Rune.get());
                    //  taskList.add(VarrockMuseum.get());
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
                }
                else if (arg.toLowerCase().contains("merlin")) {
                    General.println("[Args]: Added Merlin's crystal");
                    taskList.add(MerlinsCrystal.get());
                }
                else if (arg.toLowerCase().contains("hosafavour")) {
                    General.println("[Args]: Added Hosidius favour");
                    taskList.add(MakeCompost.get());
                }
                else if (arg.toLowerCase().contains("runemysteries")) {
                    General.println("[Args]: Added Rune Mysteries");
                    taskList.add(RuneMysteries.get());
                }
                else if (arg.toLowerCase().contains("elementalworkshop")) {
                    General.println("[Args]: Added Elemental Workshop");
                    taskList.add(ElementalWorkshop.get());
                }
                else if (arg.toLowerCase().contains("hauntedmine")) {
                    General.println("[Args]: Added haunted Mine");
                    taskList.add(HauntedMine.get());
                }
            } catch (Exception e) {
                e.printStackTrace();
                General.println("[Error]: Args are incorrect", Color.RED);
            }
        }
    }


    @Override
    public void onPaint(Graphics g) {
        double timeRan = getRunningTime();
        double timeRanMin = (timeRan / 3600000);
        List<String> myString;
        RSNPC[] inadeq = NPCs.find(3473);
        Vars.get().currentQuestPoints = Utils.getQuestPoints();
        if (inadeq.length > 0) {
            myString = new ArrayList<>(Arrays.asList(
                    "cQuester v2",
                    "Running For: " + Timing.msToString(getRunningTime()),
                    "Task: " + status,
                    "Game setting: " + gameSetting,
                    "Varbit 3633  (" + Utils.getVarBitValue(3633) + ")",
                    "Is interacting with: " + inadeq[0].isInteractingWithMe()
            ));
        } else {
            myString = new ArrayList<>(Arrays.asList(
                    "cQuester v2",
                    "Running For: " + Timing.msToString(getRunningTime()),
                    "Quest: " + gameSetting,
                    "Task: " + status,
                    "Tasks To Do: " + taskList.size(),
                    "Viewport scale: " + Game.getViewportScale()
            ));
        }

        PaintUtil.createPaint(g, myString.toArray(String[]::new));
    }


    @Override
    public void onEnd() {

    }

    @Override
    public void serverMessageReceived(String message) {
        //chatRecorder.registerServerMessage(message);
        Utils.handleRecoilMessage(message);
        if (message.contains("You haven't got enough.")) {
            cQuesterV2.isRunning.set(false);
        }
        // QuestHelper.autocastAndGrandExchangeFailSafe(message);
        // Utils.handleBlockedFireMessage(message);
        if (taskList.get(0).equals(FremTrials.get())) {
            FremTrials.get().handleTalismanMessage(message);
            FremTrials.get().handleDraugenIsHere(message);
        }
        if (taskList.get(0).equals(ZogreFleshEaters.get())) {
            ZogreFleshEaters.handlePortraitMessage(message);
        }
        // if (RFD_PIRATE) RfdPirate.handleRockMessage(message);


        //  if (ZOGRE_FLESH_EATERS)
        //      ZogreFleshEaters.handlePortraitMessage(message);
    }

}
