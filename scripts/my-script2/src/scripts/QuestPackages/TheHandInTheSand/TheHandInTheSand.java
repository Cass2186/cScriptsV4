package scripts.QuestPackages.TheHandInTheSand;

import org.tribot.api2007.Game;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.Inventory;
import org.tribot.script.sdk.Log;
import scripts.*;
import scripts.QuestPackages.TrollRomance.TrollRomance;
import scripts.QuestSteps.*;
import scripts.Requirements.*;
import scripts.Requirements.Util.ConditionalStep;
import scripts.Requirements.Util.Conditions;
import scripts.Tasks.Priority;

import java.util.*;

public class TheHandInTheSand implements QuestTask {

    private static TheHandInTheSand quest;

    public static TheHandInTheSand get() {
        return quest == null ? quest = new TheHandInTheSand() : quest;
    }
    //Items Required
    ItemRequirement truthSerum, beer, redberries, whiteberries, pinkDye, roseLens, lanternLens, magicalOrb, redberryJuice, bottledWater, hand, beerHand, bertsRota, sandysRota, magicScroll, vial, vial2, sand, wizardsHead,
            beerOr2Coins, earthRunes5, coins, bucketOfSand, truthSerumHighlight, activatedOrb;

    //Items Recommended
    ItemRequirement teleportsToYanille, teleportsToBrimhaven;

    Requirement notTeleportedToSarim, inYanille, inLightSpot, receivedBottledWater, vialPlaced, madeTruthSerum;

   QuestStep talkToBert, talkToBertAboutRota, talkToBertAboutScroll, talkToBetty, talkToBettyOnceMore, talkToBettyAgain, talkToRarveAgain, talkToSandyWithPotion, giveCaptainABeer, useLensOnCounter,
            useDyeOnLanternLens, useSerumOnCoffee, searchSandysDesk, standInDoorway, ringBell, ringBellAgain, pickpocketSandy, addWhiteberries, addRedberries, activateMagicalOrb, interrogateSandy, ringBellAfterInterrogation,
            ringBellWithItems, talkToMazion, ringBellEnd;

    //Zones
    RSArea yanille, lightSpot;


    public Map<Integer, QuestStep> loadSteps() {
        loadZones();
        setupItemRequirements();
        setupConditions();
        setupSteps();
        Map<Integer, QuestStep> steps = new HashMap<>();

        steps.put(0, talkToBert);
        // 1528, 1529, 1530 0->1
        steps.put(10, giveCaptainABeer);
        steps.put(20, ringBell);
        steps.put(30, talkToBertAboutRota);
        steps.put(40, searchSandysDesk);

        ConditionalStep goGetScroll = new ConditionalStep( pickpocketSandy);
        goGetScroll.addStep(sand, talkToBertAboutScroll);
        steps.put(50, goGetScroll);
        steps.put(60, ringBellAgain);

        ConditionalStep goToBetty = new ConditionalStep( talkToBetty);
        goToBetty.addStep(madeTruthSerum, talkToBettyOnceMore);
        goToBetty.addStep(new Conditions(roseLens, vialPlaced, inLightSpot), useLensOnCounter);
        goToBetty.addStep(new Conditions(roseLens, vialPlaced), standInDoorway);
        goToBetty.addStep(roseLens, talkToBettyAgain);
        goToBetty.addStep(pinkDye, useDyeOnLanternLens);
        goToBetty.addStep(redberryJuice, addWhiteberries);
        goToBetty.addStep(receivedBottledWater, addRedberries);
        goToBetty.addStep(new Conditions(inYanille, notTeleportedToSarim), talkToRarveAgain);

        steps.put(70, goToBetty);
        steps.put(80, talkToSandyWithPotion);
        steps.put(90, useSerumOnCoffee);
        steps.put(100, activateMagicalOrb);
        steps.put(110, interrogateSandy);
        steps.put(120, ringBellAfterInterrogation);
        steps.put(130, ringBellWithItems);
        steps.put(140, talkToMazion);
        steps.put(150, ringBellEnd);

        return steps;
    }

    public void setupItemRequirements()
    {
        beer = new ItemRequirement("Beer", ItemID.BEER);
        bottledWater = new ItemRequirement("Bottled water", ItemID.BOTTLED_WATER);
       // bottledWater.setTooltip("You can get another from Betty");

        redberries = new ItemRequirement("Redberries", ItemID.REDBERRIES);
        whiteberries = new ItemRequirement("White berries", ItemID.WHITE_BERRIES);
        redberryJuice = new ItemRequirement("Red dye", ItemID.REDBERRY_JUICE);


        pinkDye = new ItemRequirement("Pink dye", ItemID.PINK_DYE);
        truthSerum = new ItemRequirement("Truth serum", ItemID.TRUTH_SERUM);
       // truthSerum.setTooltip("You can get another from Betty in Port Sarim");

        truthSerumHighlight = new ItemRequirement("Truth serum", ItemID.TRUTH_SERUM);
       // truthSerumHighlight.setTooltip("You can get another from Betty in Port Sarim");


        lanternLens = new ItemRequirement("Lantern lens", ItemID.LANTERN_LENS);
        roseLens = new ItemRequirement("Rose-tinted lens", ItemID.ROSE_TINTED_LENS);
        hand = new ItemRequirement("Sandy hand", ItemID.SANDY_HAND);
      //  hand.setTooltip("You can get another from Bert");
        beerHand = new ItemRequirement("Beer soaked hand", ItemID.BEER_SOAKED_HAND);
       // beerHand.setTooltip("You can get another from Bert");
        bertsRota = new ItemRequirement("Bert's rota", ItemID.BERTS_ROTA);
       /// bertsRota.setTooltip("You can get another from Bert");
        sandysRota = new ItemRequirement("Sandy's rota", ItemID.SANDYS_ROTA);
      //  sandysRota.setTooltip("You can get another by searching Sandy's desk");

        magicalOrb = new ItemRequirement("Magical orb", ItemID.MAGICAL_ORB);
     //   magicalOrb.setTooltip("You can get another from Zavistic Rarve in Yanille");

        activatedOrb = new ItemRequirement("Magical orb", ItemID.MAGICAL_ORB_A);
       // activatedOrb.setTooltip("You can get another from Zavistic Rarve in Yanille");

        vial = new ItemRequirement("Vial", ItemID.VIAL);
        vial2 = new ItemRequirement("Vial", ItemID.VIAL, 2);

        magicScroll = new ItemRequirement("A magic scroll", ItemID.A_MAGIC_SCROLL);
      //  magicScroll.setTooltip("You can get another from Bert");

        sand = new ItemRequirement("Sand", ItemID.SAND);
        //sand.setTooltip("You can get more by pickpocketing Sandy in Brimhaven");

        beerOr2Coins = new ItemRequirement("Beer or 2 gp", ItemID.BEER);
        earthRunes5 = new ItemRequirement("Earth runes", ItemID.EARTH_RUNE, 5);
        coins = new ItemRequirement(ItemID.COINS_995, 10000);

        bucketOfSand = new ItemRequirement("Bucket of sand", ItemID.BUCKET_OF_SAND);

        wizardsHead = new ItemRequirement("Wizard's head", ItemID.WIZARDS_HEAD);
        //wizardsHead.setTooltip("You can get another from Mazion on Entrana");

        teleportsToBrimhaven = new ItemRequirement("Teleports to Brimhaven, or to near a boat to Brimhaven",
                ItemID.BRIMHAVEN_TELEPORT, 2);
        teleportsToYanille = new ItemRequirement("Teleports to Yanille, such as dueling ring or minigame teleport",
                ItemID.YANILLE_TELEPORT, 3);
    }

    public void loadZones()
    {
        yanille = new RSArea(new RSTile(2528, 3063, 0), new RSTile(2628, 3122, 0));
        lightSpot = new RSArea(new RSTile(3016, 3259, 0), new RSTile(3016, 3259, 0));
    }

    public void setupConditions()
    {
        notTeleportedToSarim = new VarbitRequirement(1531, 0);
        inYanille = new AreaRequirement(yanille);
        inLightSpot = new AreaRequirement(lightSpot);
        receivedBottledWater = new VarbitRequirement(1532, 1);
        vialPlaced = new VarbitRequirement(1537, 1);
        madeTruthSerum = new VarbitRequirement(1532, 5);
    }

    public void setupSteps()
    {
        talkToBert = new NPCStep( NpcID.BERT, new RSTile(2551, 3099, 0), "Talk to Bert in west Yanille.");
        talkToBert.addDialogStep("Eww a hand, in the sand! Why haven't you told the authorities?");
        talkToBert.addDialogStep("Sure, I'll give you a hand.");
        giveCaptainABeer = new NPCStep( NpcID.GUARD_CAPTAIN, new RSTile(2552, 3080, 0), beer);
        ringBell = new ObjectStep( ObjectID.BELL_6847, new RSTile(2598, 3085, 0), "Ring the bell outside the Wizards' Guild in Yanille. Talk to Zavistic Rarve when he appears.", beerHand);
        talkToBertAboutRota = new NPCStep( NpcID.BERT, new RSTile(2551, 3099, 0), "Return to Bert in west Yanille.");

        searchSandysDesk = new ObjectStep( ObjectID.SANDYS_DESK, new RSTile(2789, 3174, 0), "Travel to Brimhaven, then enter Sandy's building south of the restaurant. Search Sandy's desk for Sandy's rota.");

        pickpocketSandy = new NPCStep( NpcID.SANDY, new RSTile(2790, 3175, 0), "Pickpocket Sandy for some sand.");
        talkToBertAboutScroll = new NPCStep( NpcID.BERT, new RSTile(2551, 3099, 0), bertsRota, sandysRota);

        ringBellAgain = new ObjectStep( ObjectID.BELL_6847, new RSTile(2598, 3085, 0), "Ring the bell outside the Wizards' Guild in Yanille. Talk to Zavistic Rarve when he appears.", magicScroll);
        talkToRarveAgain = new ObjectStep( ObjectID.BELL_6847, new RSTile(2598, 3085, 0), "Talk to Zavistic Rarve again to get teleported to Port Sarim.", vial);
        talkToRarveAgain.addDialogStep("Can you help me more?");
        talkToRarveAgain.addDialogStep("Yes, that would be great!");

        talkToBetty = new NPCStep( NpcID.BETTY_5905, new RSTile(3014, 3258, 0), vial);
        talkToBetty.addDialogStep("Talk to Betty about the Hand in the Sand.");
        addRedberries = new UseItemOnItemStep(ItemID.REDBERRIES, ItemID.BOTTLED_WATER, !Inventory.contains(ItemID.REDBERRIES),
                bottledWater, redberries);
        addWhiteberries = new UseItemOnItemStep( ItemID.WHITE_BERRIES, ItemID.REDBERRY_JUICE,
                !Inventory.contains(ItemID.WHITE_BERRIES),
                redberryJuice, whiteberries);
        useDyeOnLanternLens = new UseItemOnItemStep(ItemID.PINK_DYE, ItemID.LANTERN_LENS,
                !Inventory.contains(ItemID.PINK_DYE), pinkDye, lanternLens);

        talkToBettyAgain = new NPCStep( NpcID.BETTY_5905, new RSTile(3014, 3258, 0), "Talk to Betty with the pink lens.");
        talkToBettyAgain.addDialogStep("Talk to Betty about the Hand in the Sand.");

     //   standInDoorway = new DetailedQuestStep( new RSTile(3016, 3259, 0));
        useLensOnCounter = new ObjectStep( ObjectID.COUNTER, new RSTile(3013, 3259, 0),
                "\"Stand in the Betty's doorway and use the rose-tinted lens on the counter.", roseLens);
       // useLensOnCounter.addSubSteps(standInDoorway);
        talkToBettyOnceMore =  new NPCStep( NpcID.BETTY_5905, new RSTile(3014, 3258, 0), truthSerum, sand);
        talkToBettyOnceMore.addDialogStep("Talk to Betty about the Hand in the Sand.");
        talkToSandyWithPotion =  new NPCStep( NpcID.SANDY, new RSTile(2790, 3175, 0),
            //    "Talk to Sandy in Brimhaven again with the truth serum. Select distractions until one works.",
                truthSerum);
        useSerumOnCoffee = new ObjectStep(10806, new RSTile(2789, 3176, 0), "Use the truth serum on Sandy's coffee mug.", truthSerum);

        //activateMagicalOrb = new DetailedQuestStep( "Activate the magical orb next to Sandy.", magicalOrb);

        interrogateSandy = new NPCStep( NpcID.SANDY, new RSTile(2790, 3175, 0), activatedOrb);
        interrogateSandy.addDialogStep("Why is Bert's rota different from the original?",
                "Why doesn't Bert remember the change in his hours?", "What happened to the wizard?");

        ringBellAfterInterrogation = new ObjectStep( ObjectID.BELL_6847, new RSTile(2598, 3085, 0), "Return to the Wizards' Guild in Yanille and ring the bell outside. Talk to Zavistic Rarve when he appears.", activatedOrb, earthRunes5, bucketOfSand);
        ringBellWithItems = new ObjectStep( ObjectID.BELL_6847, new RSTile(2598, 3085, 0), "Give  Zavistic Rarve 5 earth runes and a bucket of sand.", earthRunes5, bucketOfSand);
        talkToMazion = new NPCStep( NpcID.MAZION, new RSTile(2815, 3340, 0), "Travel to Entrana (bank all combat gear), and talk to Mazion at the sand pit.");
        ringBellEnd = new ObjectStep( ObjectID.BELL_6847, new RSTile(2598, 3085, 0), "Return to the Wizards' Guild in Yanille and ring the bell outside. Talk to Zavistic Rarve when he appears to finish the quest.", wizardsHead);
    }


    public List<ItemRequirement> getItemRequirements()
    {
        ArrayList<ItemRequirement> reqs = new ArrayList<>();
        reqs.add(beerOr2Coins);
        reqs.add(coins);
        reqs.add(vial2);
        reqs.add(redberries);
        reqs.add(whiteberries);
        reqs.add(lanternLens);
        reqs.add(earthRunes5);
        reqs.add(bucketOfSand);
        return reqs;
    }


    public List<ItemRequirement> getItemRecommended()
    {
        ArrayList<ItemRequirement> reqs = new ArrayList<>();
        reqs.add(teleportsToYanille);
        reqs.add(teleportsToBrimhaven);

        return reqs;
    }


    public List<Requirement> getGeneralRequirements() {
        return Arrays.asList(new SkillRequirement(Skills.SKILLS.THIEVING, 17),
                new SkillRequirement(Skills.SKILLS.CRAFTING, 49));
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
        int varbit = Utils.getVarBitValue(QuestVarbits.QUEST_THE_HAND_IN_THE_SAND.getId());
        if (!checkRequirements()) {
            Log.log("[Debug]: Missing requirement for the hand in the sand (17 thieving and 49 crafting)");
            cQuesterV2.taskList.remove(this);
            return;
        }
        Log.log("[Debug]: The Hand in the Sand Varbit is " + varbit);
        Map<Integer, QuestStep> steps = loadSteps();
        Optional<QuestStep> step = Optional.ofNullable(steps.get(varbit));
        step.ifPresent(QuestStep::execute);
    }

    @Override
    public String questName() {
        return "The Hand in the Sand (" + Utils.getVarBitValue(QuestVarbits.QUEST_THE_HAND_IN_THE_SAND.getId()) + ")";
    }

    @Override
    public boolean checkRequirements() {
        return Utils.checkAllRequirements(getGeneralRequirements().toArray(Requirement[]::new));

    }


    @Override
    public List<ItemRequirement> getBuyList() {
        return null;
    }
}
