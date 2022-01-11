package scripts.QuestPackages.RatCatchers;

import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.InventoryItem;
import scripts.*;
import scripts.QuestPackages.HauntedMine.HauntedMine;
import scripts.QuestSteps.*;
import scripts.Requirements.AreaRequirement;
import scripts.Requirements.FollowerRequirement;
import scripts.Requirements.ItemRequirement;
import scripts.Requirements.Items.FollowerItemRequirement;
import scripts.Requirements.Items.ItemCollections;
import scripts.Requirements.Util.ConditionalStep;
import scripts.Requirements.Util.Conditions;
import scripts.Requirements.VarbitRequirement;
import scripts.Tasks.Priority;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class RatCatchers implements QuestTask {
    private static RatCatchers quest;

    public static RatCatchers get() {
        return quest == null ? quest = new RatCatchers() : quest;
    }

    FollowerItemRequirement cat = new FollowerItemRequirement("A non-overgrown cat",
            ItemCollections.getHuntingCats(),
            NpcCollections.getHuntingCats());

    FollowerRequirement catFollower =
            new FollowerRequirement("A non-overgrown cat following you", NpcCollections.getHuntingCats());
    ItemRequirement ratPoison = new ItemRequirement("Rat poison", ItemId.RAT_POISON);
    // ratPoison.setTooltip("You can get some from under the Clocktower south of Ardougne");
    ItemRequirement cheese = new ItemRequirement("Cheese", ItemId.CHEESE);
    //   cheese.addAlternates(ItemId.POISONED_CHEESE);
    ItemRequirement marrentill = new ItemRequirement("Marrentill", ItemId.MARRENTILL);
    ItemRequirement unicornHornDust = new ItemRequirement("Unicorn horn dust", ItemId.UNICORN_HORN_DUST);
    ItemRequirement bucketOfMilk = new ItemRequirement("Bucket of milk", ItemId.BUCKET_OF_MILK);
    // TODO: Add DS2 as part of check
    ItemRequirement catspeakAmuletOrDS2 = new ItemRequirement("Catspeak amulet", ItemId.CATSPEAK_AMULET);
    //  catspeakAmuletOrDS2.addAlternates(ItemId.CATSPEAK_AMULETE);
    ItemRequirement potOfWeeds = new ItemRequirement("Pot of weeds", ItemId.POT_OF_WEEDS);
    // potOfWeeds.setTooltip("You can make this by using some weeds on a pot");
    ItemRequirement tinderbox = new ItemRequirement("Tinderbox", ItemId.TINDERBOX);
    ItemRequirement coins101 = new ItemRequirement("Coins", ItemId.COINS_995, 101);
    ItemRequirement coin = new ItemRequirement("Coins", ItemId.COINS_995);
    ItemRequirement snakeCharm = new ItemRequirement("Snake charm", ItemId.SNAKE_CHARM);
    //  snakeCharm.canBeObtainedDuringQuest();

    ///raw or cooked
    ItemRequirement fish8 = new ItemRequirement(ItemCollections.getFishFood(), 8);
    //   fish8.addAlternates(ItemCollections.getRawFish());

    ItemRequirement varrockTeleport = new ItemRequirement("Varrock teleport", ItemId.VARROCK_TELEPORT);
    // ItemRequirement sarimTeleport = new ItemRequirement("Port Sarim teleport", ItemCollections.getAmuletOfGlories());
    //  sarimTeleport.addAlternates(ItemId.DRAYNOR_MANOR_TELEPORT);
    //ItemRequirement pollnivneachTeleport = new ItemRequirement("Pollnivneach teleport", ItemId.POLLNIVNEACH_TELEPORT);
    ItemRequirement ardougneTeleport = new ItemRequirement("Ardougne teleport", ItemId.ARDOUGNE_TELEPORT);

    ItemRequirement keldagrimTeleport = new ItemRequirement("Mine cart access to Keldagrim from the GE", -1, -1);
    //keldagrimTeleport.setDisplayItemId(ItemId.MINECART_TICKET);
    ItemRequirement carpetCoins = new ItemRequirement("Coins for magic carpet travel", ItemId.COINS_995, 1000);

    ItemRequirement directions = new ItemRequirement("Directions", ItemId.DIRECTIONS);

    ItemRequirement poisonedCheese1 = new ItemRequirement("Poisoned cheese", ItemId.POISONED_CHEESE);
    ItemRequirement poisonedCheese2 = new ItemRequirement("Poisoned cheese", ItemId.POISONED_CHEESE, 2);
    ItemRequirement poisonedCheese3 = new ItemRequirement("Poisoned cheese", ItemId.POISONED_CHEESE, 3);
    ItemRequirement poisonedCheese4 = new ItemRequirement("Poisoned cheese", ItemId.POISONED_CHEESE, 4);
    ItemRequirement smoulderingPot = new ItemRequirement("Smouldering pot", ItemId.SMOULDERING_POT);
    ItemRequirement catantipoison = new ItemRequirement("Cat antipoison", ItemId.CAT_ANTIPOISON);
    //  catantipoison.setTooltip("You can get another from the Apothecary");
    ItemRequirement musicScroll = new ItemRequirement("Music scroll", ItemId.MUSIC_SCROLL);
    //  musicScroll.setTooltip("You can get another from the snake charmer");


    RSArea varrockSewer = new RSArea(new RSTile(3151, 9855, 0), new RSTile(3290, 9919, 0));
    RSArea mansionGrounds = new RSArea(new RSTile(2821, 5061, 0), new RSTile(2874, 5120, 0));
    RSArea mansionF0 = new RSArea(new RSTile(2831, 5085, 0), new RSTile(2864, 5101, 0));
    RSArea mansionF1 = new RSArea(new RSTile(2829, 5083, 1), new RSTile(2869, 5108, 2));
    RSArea giantRatArea = new RSArea(new RSTile(3263, 3375, 1), new RSTile(3274, 3386, 2));
    RSArea keldagrim = new RSArea(new RSTile(2816, 10112, 0), new RSTile(2950, 10239, 3));
    RSArea ratPit = new RSArea(new RSTile(2945, 9622, 0), new RSTile(3005, 9680, 0));
    RSArea portSarim = new RSArea(new RSTile(3019, 3232, 0), 1);


    AreaRequirement inVarrockSewer = new AreaRequirement(varrockSewer);
    AreaRequirement inMansionGrounds = new AreaRequirement(mansionGrounds);
    AreaRequirement inMansionF0 = new AreaRequirement(mansionF0);
    AreaRequirement inMansionF1 = new AreaRequirement(mansionF1);
    AreaRequirement inGiantRatArea = new AreaRequirement(giantRatArea);
    AreaRequirement inKelgdagrim = new AreaRequirement(keldagrim);
    AreaRequirement inRatPit = new AreaRequirement(ratPit);
    AreaRequirement inPortSarim = new AreaRequirement(portSarim);

    // 1423 0->8 for rats caught
    //

    // caught ne,
    // 1423 = 1,
    // 1424 = 1

    VarbitRequirement caughtRat1 = new VarbitRequirement(1424, 1);
    Conditions caughtRat2And3 = new Conditions(
            new VarbitRequirement(1425, 1),
            new VarbitRequirement(1426, 1)
    );

    VarbitRequirement poisonedHole1 = new VarbitRequirement(1406, 1);
    VarbitRequirement poisonedHole2 = new VarbitRequirement(1407, 1);
    VarbitRequirement poisonedHole3 = new VarbitRequirement(1408, 1);
    VarbitRequirement poisonedHole4 = new VarbitRequirement(1409, 1);

    VarbitRequirement catSeenFailure = new VarbitRequirement(1410, 1);
    // 1422, cat's told you how to get kelda rats

    //   inPlayWidget = new WidgetTextRequirement(282, 20, "PLAY");


    NPCStep talkToGertrude = new NPCStep(NpcID.GERTRUDE_7723, new RSTile(3151, 3413, 0),
            "Talk to Gertrude west of Varrock.");

    ObjectStep enterSewer = new ObjectStep(ObjectID.MANHOLE_882, new RSTile(3237, 3458, 0),
            "Go down into Varrock Sewer via the Manhole south east of Varrock Castle.", cat);
    //   ((ObjectStep)enterSewer).addAlternateObjects(ObjectID.MANHOLE);

    NPCStep talkToPhingspet = new NPCStep(NpcID.PHINGSPET, new RSTile(3243, 9867, 0), cat);

    NPCStep catch8Rats = new NPCStep(NpcID.RAT_2854, new RSTile(3243, 9867, 0), catFollower);
    // ((NPCStep)catch8Rats).setHideWorldArrow(true);

    NPCStep talkToPhingspetAgain = new NPCStep(NpcID.PHINGSPET, new RSTile(3243, 9867, 0),
            "Talk to Phingspet in Varrock Sewer again.");

    NPCStep talkToJimmy = new NPCStep(NpcID.JIMMY_DAZZLER, new RSTile(2563, 3320, 0), cat);
    ClickItemStep readDirections = new ClickItemStep(directions.getId(), "Read", cat, directions);

    ObjectStep climbTrellis = new ObjectStep(ObjectID.TRELLIS, new RSTile(2844, 5105, 0),
            "Climb the trellis around the back of the mansion, avoiding the guards. You may need to deviate from the " +
                    "marked path depending on what the guards are doing.");
    /*   ((ObjectStep)climbTrellis).

   setLinePoints(Arrays.asList(
           new RSTile(2847, 5066,0),
               new
   RSTile(2840,5066,0),
               new

   RSTile(2840,5075,0),
               new

   RSTile(2826,5075,0),
               new

   RSTile(2826,5082,0),
               new

   RSTile(2824,5084,0),
               new

   RSTile(2824,5088,0),
               new

   RSTile(2826,5090,0),
               new

   RSTile(2826,5093,0),
               new

   RSTile(2824,5095,0),
               new

   RSTile(2824,5099,0),
               new

   RSTile(2826,5101,0),
               new

   RSTile(2826,5111,0),
               new

   RSTile(2835,5112,0),
               new

   RSTile(2837,5112,0),
               new

   RSTile(2837,5111,0),
               new

   RSTile(2844,5105,0)
       ));*/
    ObjectStep climbTrellisNoPath = new ObjectStep(ObjectID.TRELLIS, new RSTile(2844, 5105, 0),
            "Climb");
    // climbTrellis.addSubSteps(climbTrellisNoPath);

    //catch rat in NW Room with your cat
    NPCStep catchRat1 = new NPCStep(NpcID.RAT_4593, new RSTile(2835, 5098, 1), catFollower);

    //   catchRat1.addTileMarker(newRSTile(2841,5104,1),SpriteID.EQUIPMENT_SLOT_SHIELD);
    //  catchRat1.setMaxRoamRange(7);

    NPCStep catchRat2And3 = new NPCStep(NpcID.RAT_4593, new RSTile(2859, 5091, 1)
            //      "Hide in the north east room until it's safe to go to the south east room, then catch the rats there.",
    );
    //    catchRat2And3.addTileMarker(new RSTile(2857, 5098, 1), SpriteID.EQUIPMENT_SLOT_SHIELD);
    ObjectStep climbDownLadderInMansion = new ObjectStep(16679, new RSTile(2862, 5092, 1),
            "Climb down the ladder.");

    //"Catch the 3 rats down here."
    NPCStep catchRemainingRats = new NPCStep(NpcID.RAT_4593, new RSTile(2860, 5093, 0),
             catFollower);

    NPCStep talkToJimmyAgain = new NPCStep(NpcID.JIMMY_DAZZLER, new RSTile(2563, 3320, 0), cat);

    NPCStep talkToJack = new NPCStep(NpcID.HOOKNOSED_JACK, new RSTile(3268, 3401, 0), cat);

    ObjectStep climbJackLadder = new ObjectStep(ObjectID.LADDER_11794, new RSTile(3268, 3379, 0),
            "Climb up the ladder south of Jack.", cheese.quantity(4), ratPoison);

    UseItemOnItemStep useRatPoisonOnCheese = new UseItemOnItemStep(ratPoison.getId(), cheese.getId(),
            poisonedCheese1.check());

    ObjectStep useCheeseOnHole1 = new ObjectStep(ObjectID.RAT_HOLE, new RSTile(3265, 3378, 1),
            "Use a poisoned cheese on the rat holes.", poisonedCheese1);
    ObjectStep useCheeseOnHole2 = new ObjectStep(ObjectID.RAT_HOLE_10347, new RSTile(3273, 3377, 1),
            "Use a poisoned cheese on the rat holes.", poisonedCheese1);
    ObjectStep useCheeseOnHole3 = new ObjectStep(ObjectID.RAT_HOLE_10348, new RSTile(3273, 3381, 1),
            "Use a poisoned cheese on the rat holes.", poisonedCheese1);
    ObjectStep useCheeseOnHole4 = new ObjectStep(ObjectID.RAT_HOLE_10349, new RSTile(3271, 3384, 1),
            "Use a poisoned cheese on the rat holes.", poisonedCheese1);
    //  useCheeseOnHole1.addSubSteps(useCheeseOnHole2,useCheeseOnHole3,useCheeseOnHole4);

    ObjectStep goDownToJack = new ObjectStep(ObjectID.LADDER_11795, new RSTile(3268, 3379, 1),
            "Return to Jack.");
    NPCStep talkToJackAfterCheese = new NPCStep(NpcID.HOOKNOSED_JACK, new RSTile(3268, 3401, 0), cat);
    //  talkToJackAfterCheese.addSubSteps(goDownToJack);

    NPCStep talkToApoth = new NPCStep(NpcID.APOTHECARY, new RSTile(3196, 3404, 0),
            bucketOfMilk, unicornHornDust, marrentill);

    NPCStep talkToJackAfterApoth = new NPCStep(NpcID.HOOKNOSED_JACK, new RSTile(3268, 3401, 0),
            catantipoison, cat);
    NPCStep talkToJackAfterCure = new NPCStep(NpcID.HOOKNOSED_JACK, new RSTile(3268, 3401, 0),
          catantipoison, cat);
    // talkToJackAfterApoth.addSubSteps(talkToJackAfterCure);

    ObjectStep climbJackLadderAgain = new ObjectStep(ObjectID.LADDER_11794, new RSTile(3268, 3379, 0),
            "Climb up the ladder south of Jack.", cat, fish8);
    ObjectStep useCatOnHole = new ObjectStep(ObjectID.HOLE_IN_WALL_10320, new RSTile(3270, 3379, 1),
            "Use your cat on the hole in the wall. You'll need to feed it by using fish ON THE WALL whenever its " +
                    "health gets low.", cat, fish8);

    ObjectStep feedCatAsItFights = new ObjectStep(ObjectID.HOLE_IN_WALL_10320, new RSTile(3270, 3379, 1),
            "Use fish on the wall whenever your cat's health gets low.", fish8);
    ObjectStep goDownToJackAfterFight = new ObjectStep(ObjectID.LADDER_11795, new RSTile(3268, 3379, 1),
            "Return to Jack.");
    NPCStep talkToJackAfterFight = new NPCStep(NpcID.HOOKNOSED_JACK, new RSTile(3268, 3401, 0),
            "Return to Jack.");
    // talkToJackAfterFight.addSubSteps(goDownToJackAfterFight);

    ObjectStep travelToKeldagrim = new ObjectStep(16168, new RSTile(3140, 3504, 0),
            "Travel", cat, potOfWeeds, tinderbox);
    NPCStep talkToSmokinJoe = new NPCStep(NpcID.SMOKIN_JOE, new RSTile(2929, 10213, 0), cat, potOfWeeds, tinderbox);

    UseItemOnItemStep lightWeeds = new UseItemOnItemStep(ItemId.TINDERBOX, potOfWeeds.getId(), smoulderingPot.check());
    ObjectStep usePotOnHole = new ObjectStep(ObjectID.RAT_HOLE_10350, new RSTile(2933, 10212, 0),
            "Use the smouldering pot on the hole east of Joe with your cat following you.",
            smoulderingPot, catFollower, catspeakAmuletOrDS2.equipped());

    ObjectStep usePotOnHoleAgain = new ObjectStep(ObjectID.RAT_HOLE_10350, new RSTile(2933, 10212, 0),
            "Use the smouldering pot on the rat hole again.", smoulderingPot, catFollower, catspeakAmuletOrDS2.equipped());

    NPCStep talkToJoeAgain = new NPCStep(NpcID.SMOKIN_JOE, new RSTile(2929, 10213, 0),
            "Talk to Smokin' Joe again.");

    ObjectStep enterSarimRatPits = new ObjectStep(ObjectID.MANHOLE_10321, new RSTile(3018, 3232, 0),
            "Go down the manhole near The Face.");
    NPCStep talkToFelkrash = new NPCStep(NpcID.FELKRASH, new RSTile(2978, 9640, 0),
            "Talk to Felkrash in the Port Sarim Rat Pits.");
    //  ((NPCStep)talkToFelkrash).setWorldMapPoint(new RSTile(3044, 9645,0));

    ObjectStep leaveSarimRatPits = new ObjectStep(ObjectID.LADDER_10309, new RSTile(2962, 9651, 0),
            "Leave the rat pits.");
    NPCStep talkToTheFaceAgain = new NPCStep(NpcID.THE_FACE, new RSTile(3019, 3232, 0),
             cat, catspeakAmuletOrDS2);

    ObjectStep useCoinOnPot = new ObjectStep(ObjectID.MONEY_POT, new RSTile(3355, 2953, 0),
            "Use a coin on the pot next to the Snake Charmer in Pollnivneach.", coin.quantity(101));


   // DetailedQuestStep returnToSarim = new DetailedQuestStep(new RSTile(3019, 3232, 0), snakeCharm, musicScroll);
   ClickItemStep clickSnakeCharm = new ClickItemStep( snakeCharm.getId(), "Play");

    //   playSnakeCharm = new RatCharming(this);

    ObjectStep enterPitsForEnd = new ObjectStep(ObjectID.MANHOLE_10321, new RSTile(3018, 3232, 0),
            "Return to Felkrash to finish.");
    NPCStep talkToFelkrashForEnd = new NPCStep(NpcID.FELKRASH, new RSTile(2978, 9640, 0),
            "Return to Felkrash to finish.");
    // ((NPCStep)talkToFelkrashForEnd).setWorldMapPoint(new RSTile(3044, 9645,0));
    //talkToFelkrashForEnd.addSubSteps(enterPitsForEnd);


    public Map<Integer, QuestStep> loadSteps() {
        readDirections.addDialogStep("Follow the directions to the house.");
        talkToApoth.addDialogStep("Talk about something else.", "Talk about the Ratcatchers Quest.");
        talkToJackAfterCheese.addDialogStep("Can I help?");
        useCatOnHole.addDialogStep("Yes");
        talkToSmokinJoe.addDialogStep("I could help you.");
        useCoinOnPot.addDialogStep("I want to talk to you about animal charming.", "Forget about it. I don't care.",
                "What if I offered you some money?", "Walk away slowly", "Stop");

        Map<Integer, QuestStep> steps = new HashMap<>();

        steps.put(0, talkToGertrude);

        ConditionalStep goTalkToSewerPeople = new ConditionalStep(enterSewer);
        goTalkToSewerPeople.addStep(inVarrockSewer, talkToPhingspet);
        steps.put(5, goTalkToSewerPeople);

        ConditionalStep goCatchSewerRats = new ConditionalStep(enterSewer);
        goCatchSewerRats.addStep(inVarrockSewer, catch8Rats);
        steps.put(10, goCatchSewerRats);

        ConditionalStep goTalkToSewerPeopleAgain = new ConditionalStep(enterSewer);
        goTalkToSewerPeopleAgain.addStep(inVarrockSewer, talkToPhingspetAgain);
        steps.put(15, goTalkToSewerPeopleAgain);

        steps.put(20, talkToJimmy);

        ConditionalStep goEnterMansion = new ConditionalStep(readDirections);
        goEnterMansion.addStep(inMansionGrounds, climbTrellis);
        steps.put(25, goEnterMansion);

        ConditionalStep goCatchMansionRats = new ConditionalStep(readDirections);
        goCatchMansionRats.addStep(new Conditions(inMansionF0, caughtRat1, caughtRat2And3), catchRemainingRats);
        goCatchMansionRats.addStep(new Conditions(inMansionF1, caughtRat1, caughtRat2And3), climbDownLadderInMansion);
        goCatchMansionRats.addStep(new Conditions(inMansionF1, caughtRat1), catchRat2And3);
        goCatchMansionRats.addStep(inMansionF1, catchRat1);
        goCatchMansionRats.addStep(inMansionGrounds, climbTrellisNoPath);
        steps.put(30, goCatchMansionRats);
        steps.put(35, talkToJimmyAgain);
        steps.put(40, talkToJack);
        steps.put(45, talkToJack);

        ConditionalStep goPoisonRats = new ConditionalStep(climbJackLadder);
        goPoisonRats.addStep(new Conditions(inGiantRatArea, poisonedCheese1, poisonedHole1, poisonedHole2, poisonedHole3),
                useCheeseOnHole4);
        goPoisonRats.addStep(new Conditions(inGiantRatArea, poisonedCheese1, poisonedHole1, poisonedHole2), useCheeseOnHole3);
        goPoisonRats.addStep(new Conditions(inGiantRatArea, poisonedCheese1, poisonedHole1), useCheeseOnHole2);
        goPoisonRats.addStep(new Conditions(inGiantRatArea, poisonedCheese1), useCheeseOnHole1);
        goPoisonRats.addStep(new Conditions(inGiantRatArea), useRatPoisonOnCheese);
        steps.put(50, goPoisonRats);

        ConditionalStep goToJackAfterPoisoning = new ConditionalStep(talkToJackAfterCheese);
        goToJackAfterPoisoning.addStep(inGiantRatArea, goDownToJack);
        steps.put(55, goToJackAfterPoisoning);
        steps.put(57, goToJackAfterPoisoning);
        steps.put(60, talkToApoth);
        steps.put(65, talkToApoth);
        steps.put(70, talkToJackAfterApoth);
        steps.put(75, talkToJackAfterCure);

        ConditionalStep goKillBigRat = new ConditionalStep(climbJackLadderAgain);
        goKillBigRat.addStep(inGiantRatArea, useCatOnHole);
        steps.put(80, goKillBigRat);

        ConditionalStep goTalkToJackAfterFight = new ConditionalStep(talkToJackAfterFight);
        goTalkToJackAfterFight.addStep(inGiantRatArea, goDownToJackAfterFight);
        steps.put(85, goTalkToJackAfterFight);

        ConditionalStep goTalkToJoe = new ConditionalStep(travelToKeldagrim);
        goTalkToJoe.addStep(inKelgdagrim, talkToSmokinJoe);
        steps.put(90, goTalkToJoe);

        ConditionalStep goSmokeHole = new ConditionalStep(travelToKeldagrim);
        goSmokeHole.addStep(new Conditions(inKelgdagrim, smoulderingPot, catSeenFailure), usePotOnHoleAgain);
        goSmokeHole.addStep(new Conditions(inKelgdagrim, smoulderingPot), usePotOnHole);
        goSmokeHole.addStep(new Conditions(inKelgdagrim), lightWeeds);
        steps.put(95, goSmokeHole);

        ConditionalStep goTalkToJoeAgain = new ConditionalStep(travelToKeldagrim);
        goTalkToJoeAgain.addStep(inKelgdagrim, talkToJoeAgain);
        steps.put(100, goTalkToJoeAgain);

        ConditionalStep goTalkToFelk = new ConditionalStep(enterSarimRatPits);
        goTalkToFelk.addStep(inRatPit, talkToFelkrash);
        steps.put(105, goTalkToFelk);

        ConditionalStep goTalkToFace = new ConditionalStep(talkToTheFaceAgain);
        goTalkToFace.addStep(inRatPit, leaveSarimRatPits);
        steps.put(110, goTalkToFace);

        steps.put(115, useCoinOnPot);

        //TODO FIX
      //  ConditionalStep goKillRats = new ConditionalStep(returnToSarim);
      //  goKillRats.addStep(new Conditions(inPortSarim, inPlayWidget), playSnakeCharm);
    //    goKillRats.addStep(inPortSarim, clickSnakeCharm);
    //    steps.put(120, goKillRats);

        ConditionalStep goFinishQuest = new ConditionalStep(enterPitsForEnd);
        goFinishQuest.addStep(inRatPit, talkToFelkrashForEnd);
        steps.put(125, goFinishQuest);

        return steps;
    }


    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return cQuesterV2.taskList.get(0).equals(this);
    }

    @Override
    public void execute() {

    }

    @Override
    public String questName() {
        return "Rat Catchers";
    }

    @Override
    public boolean checkRequirements() {
        return false;
    }
}
