package scripts.QuestPackages.WhatLiesBelow;

import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.*;
import org.tribot.script.sdk.types.WorldTile;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.TheHandInTheSand.TheHandInTheSand;
import scripts.QuestSteps.*;
import scripts.Requirements.*;
import scripts.Requirements.Util.ConditionalStep;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;

import java.util.*;

public class WhatLiesBelow implements QuestTask {

    private static WhatLiesBelow quest;

    public static WhatLiesBelow get() {
        return quest == null ? quest = new WhatLiesBelow() : quest;
    }


    //Items Required
    ItemRequirement intel5, bowl, chaosRunes15, emptyFolder, usedFolder, fullFolder, chaosTalismanOrAbyss, folder, wand,
            wandHighlight, beaconRing, letterToSurok, infusedWand, suroksLetter;

    //Items Recommended
    ItemRequirement chronicle;

    Requirement inChaosAltar, inBattle;

    UseItemOnObjectStep useWandOnAltar;
    NPCStep talkToRat, bringFolderToRat, talkToRatAfterFolder, talkToSurok, talkToSurokNoLetter, enterChaosAltar, bringWandToSurok,
            talkToRatAfterSurok, talkToZaff, talkToSurokToFight, fightRoald, talkToRatToFinish, talkToRatAfterSurokNoLetter;

    NPCStep killOutlaws;

    //RSAreas
    RSArea chaosAltar;

    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.BEER, 1, 500),
                    new GEItem(ItemID.VIAL, 2, 300),
                    new GEItem(ItemID.REDBERRIES, 1, 500),
                    new GEItem(ItemID.WHITE_BERRIES, 1, 500),
                    new GEItem(ItemID.LANTERN_LENS, 1, 500),
                    new GEItem(ItemID.EARTH_RUNE, 5, 25),
                    new GEItem(ItemID.BUCKET_OF_SAND, 1, 500),
                    new GEItem(ItemID.STAMINA_POTION[0], 3, 15),
                    new GEItem(ItemID.AMULET_OF_GLORY[2], 2, 15),
                    new GEItem(ItemID.RING_OF_WEALTH[0], 1, 25),
                    new GEItem(ItemID.RING_OF_DUELING[0], 1, 35)
            )
    );

    BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);

    InventoryRequirement startInv = new InventoryRequirement(new ArrayList<>(
            Arrays.asList(
                    new ItemReq(ItemID.BEER, 1),
                    new ItemReq(ItemID.VIAL, 2),
                    new ItemReq(ItemID.REDBERRIES, 1),
                    new ItemReq(ItemID.WHITE_BERRIES, 1),
                    new ItemReq(ItemID.LANTERN_LENS, 1),
                    new ItemReq(ItemID.EARTH_RUNE, 5),
                    new ItemReq(ItemID.BUCKET_OF_SAND, 1),
                    new ItemReq(ItemID.COINS, 10000),
                    new ItemReq(ItemID.RING_OF_DUELING[0], 1, 0),
                    new ItemReq(ItemID.AMULET_OF_GLORY[2], 2, 0, true, true),
                    new ItemReq(ItemID.RING_OF_WEALTH[0], 1, true, true),
                    new ItemReq(ItemID.STAMINA_POTION[0], 3, 0)
            ))
    );

    public Map<Integer, QuestStep> loadSteps() {
        loadRSAreas();
        setupItemRequirements();
        setupConditions();
        setupSteps();
        Map<Integer, QuestStep> steps = new HashMap<>();

        steps.put(0, talkToRat);
        // Occurs if starting quest with a full inv
        steps.put(5, talkToRat);

        ConditionalStep getIntel = new ConditionalStep(killOutlaws);
        getIntel.addStep(fullFolder, bringFolderToRat);

        steps.put(10, getIntel);

        steps.put(20, talkToRatAfterFolder);

        steps.put(25, talkToSurok);

        steps.put(30, talkToSurokNoLetter);
        steps.put(40, talkToSurokNoLetter);
        steps.put(45, talkToSurokNoLetter);
        steps.put(46, talkToSurokNoLetter);

        ConditionalStep chargeWand = new ConditionalStep(enterChaosAltar);
        chargeWand.addStep(inChaosAltar, useWandOnAltar);

        steps.put(50, chargeWand);

        steps.put(55, bringWandToSurok);

        steps.put(60, talkToRatAfterSurok);
        steps.put(61, talkToRatAfterSurok);

        steps.put(70, talkToRatAfterSurokNoLetter);
        steps.put(71, talkToRatAfterSurokNoLetter);
        steps.put(72, talkToRatAfterSurokNoLetter);

        steps.put(80, talkToZaff);
        steps.put(81, talkToZaff);
        ConditionalStep defeatSurok = new ConditionalStep(talkToSurokToFight);
        defeatSurok.addStep(inBattle, fightRoald);

        steps.put(110, defeatSurok);
        steps.put(115, defeatSurok);
        steps.put(120, defeatSurok);
        steps.put(140, talkToRatToFinish);

        return steps;
    }

    public void setupItemRequirements() {
        intel5 = new ItemRequirement("Rat's paper", ItemID.RATS_PAPER, 5);
        emptyFolder = new ItemRequirement("An empty folder", ItemID.AN_EMPTY_FOLDER);
        // Varbit 3525 0,1,2,3,4,5 is fullness of folder
        usedFolder = new ItemRequirement("Used folder", ItemID.USED_FOLDER);
        fullFolder = new ItemRequirement("Full folder", ItemID.FULL_FOLDER);

        folder = new ItemRequirement("Folder", ItemID.AN_EMPTY_FOLDER);
        folder.addAlternateItemID(ItemID.USED_FOLDER, ItemID.FULL_FOLDER);
      // folder.setTooltip("You can get another empty folder from Rat");


        bowl = new ItemRequirement("Bowl", ItemID.BOWL);
        chaosRunes15 = new ItemRequirement("Chaos runes", ItemID.CHAOS_RUNE, 15);
        wand = new ItemRequirement("Wand", ItemID.WAND);
        wandHighlight = new ItemRequirement("Wand", ItemID.WAND);


        infusedWand = new ItemRequirement("Infused wand", ItemID.INFUSED_WAND);
       // infusedWand.setTooltip("You can make another by getting a wand from Surok, and using it on the chaos altar with 15 chaos runes");
        chaosTalismanOrAbyss = new ItemRequirement("Chaos Talisman or access to the Abyss", ItemID.CHAOS_TALISMAN);

        beaconRing = new ItemRequirement("Beacon ring", ItemID.BEACON_RING);
       // beaconRing.setTooltip("You can get another from Zaff");

        letterToSurok = new ItemRequirement("Letter to Surok", ItemID.LETTER_TO_SUROK);
       // letterToSurok.setTooltip("You can get another from Rat");

        suroksLetter = new ItemRequirement("Surok's letter", ItemID.SUROKS_LETTER);
        //suroksLetter.setTooltip("You can get another from Surok");

        chronicle = new ItemRequirement("Chronicle for teleports to south of Varrock", ItemID.CHRONICLE);
    }

    public void loadRSAreas() {
        chaosAltar = new RSArea(new RSTile(2245, 4823, 0), new RSTile(2299, 4860, 2));
    }

    public void setupConditions() {
        inChaosAltar = new AreaRequirement(chaosAltar);
        inBattle = new VarbitRequirement(6719, 2);
    }

    public void setupSteps() {
        talkToRat = new NPCStep(NpcID.RAT_BURGISS, new RSTile(3266, 3333, 0), "Talk to Rat Burgiss south of Varrock.");
        talkToRat.addDialogStep("Shall I get them back for you?");
        talkToRat.addDialogStep("Of course! Tell me what you need me to do.");
       // killOutlaws = new NPCStep(NpcID.OUTLAW, new RSTile(3118, 3472, 0), "Go to the Bandits west of the Grand Exchange and kill 5 for intel. Put the intel into the folder Rat gave you.", true, folder, intel5);
        killOutlaws.addAlternateNpcs(NpcID.OUTLAW_4168, NpcID.OUTLAW_4169, NpcID.OUTLAW_4170, NpcID.OUTLAW_4171, NpcID.OUTLAW_4172, NpcID.OUTLAW_4173, NpcID.OUTLAW_4174, NpcID.OUTLAW_4175, NpcID.OUTLAW_4176);
        bringFolderToRat = new NPCStep(NpcID.RAT_BURGISS, new RSTile(3266, 3333, 0), fullFolder);
        talkToRatAfterFolder = new NPCStep(NpcID.RAT_BURGISS, new RSTile(3266, 3333, 0), "Return to Rat Burgiss south of Varrock.");
        bringFolderToRat.addSubSteps(talkToRatAfterFolder);

        talkToSurok = new NPCStep(NpcID.SUROK_MAGIS, new RSTile(3211, 3493, 0), letterToSurok);
        talkToSurokNoLetter = new NPCStep(NpcID.SUROK_MAGIS, new RSTile(3211, 3493, 0), "Talk to Surok Magis in the Varrock Library.");
        talkToSurokNoLetter.addDialogStep("Go on, then!", "Go on then!");

        talkToSurok.addSubSteps(talkToSurokNoLetter);

       // enterChaosAltar = new DetailedQuestStep("Travel to the chaos altar with the wand and 15 chaos runes. You can either enter with a chaos talisman, or use the abyss.", wand, chaosRunes15, chaosTalismanOrAbyss);
        useWandOnAltar = new UseItemOnObjectStep(ItemID.WAND,
                ObjectID.ALTAR_34769, new RSTile(2271, 4842, 0), ChatScreen.isOpen(),
                wandHighlight, chaosRunes15);
        bringWandToSurok = new NPCStep(NpcID.SUROK_MAGIS, new RSTile(3211, 3493, 0),
                infusedWand, bowl);
        bringWandToSurok.addDialogStep("I have the things you wanted!");
        talkToRatAfterSurok = new NPCStep(NpcID.RAT_BURGISS,
                new RSTile(3266, 3333, 0), suroksLetter);
        talkToRatAfterSurok.addDialogStep("Yes! I have a letter for you.");

        talkToRatAfterSurokNoLetter = new NPCStep(NpcID.RAT_BURGISS, new RSTile(3266, 3333, 0), "Return to Rat Burgiss south of Varrock.");

        talkToZaff = new NPCStep(NpcID.ZAFF, new RSTile(3202, 3434, 0), "Talk to Zaff in the Varrock staff shop.");
        talkToZaff.addDialogStep("Rat Burgiss sent me!");
        talkToSurokToFight = new NPCStep(NpcID.SUROK_MAGIS_4160,
                new RSTile(3211, 3493, 0),
              //  "Prepare to fight King Roald (level 47), then go talk to Surok Magis in the Varrock Library.",
                beaconRing);
        talkToSurokToFight.addDialogStep("Bring it on!");
        fightRoald = new NPCStep(NpcID.KING_ROALD_4163,
                new RSTile(3211, 3493, 0),
               // "Fight King Roald. When he's at 1hp, right-click operate the beacon ring.",
                beaconRing);
        talkToRatToFinish = new NPCStep(NpcID.RAT_BURGISS, new RSTile(3266, 3333, 0), "Return to Rat Burgiss south of Varrock to finish the quest.");
    }


    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        return cQuesterV2.taskList.size() > 0
                && cQuesterV2.taskList.get(0).equals(this);
    }

    @Override
    public void execute() {
        int varbit = Utils.getVarBitValue(QuestVarbits.QUEST_WHAT_LIES_BELOW.getId());
        if (isComplete() || Quest.WHAT_LIES_BELOW.getStep() == 160) {
            Utils.closeQuestCompletionWindow();
            cQuesterV2.taskList.remove(this);
            return;
        }

        if (!checkRequirements()) {
            Log.info("Missing requirement(s) for the hand in the sand (17 thieving and 49 crafting)");
            cQuesterV2.taskList.remove(this);
            return;
        }
        if (Quest.WHAT_LIES_BELOW.getStep() == 0 && !startInv.check()) {
            buyStep.buyItems();
            startInv.setDepositEquipment(true);
            startInv.withdrawItems();
        }
        Log.info("[Debug]: The Hand in the Sand Varbit is " + varbit);
        Map<Integer, QuestStep> steps = loadSteps();
        Optional<QuestStep> step = Optional.ofNullable(steps.get(varbit));

        step.ifPresent(QuestStep::execute);

        if (ChatScreen.isOpen())
            NpcChat.handle("I have a rather sandy problem that I'd like to palm off on you.");

        Waiting.waitNormal(200, 20);
    }

    @Override
    public String questName() {
        return "What Lies Below";
    }

    @Override
    public boolean checkRequirements() {
        return false;
    }

    @Override
    public List<Requirement> getGeneralRequirements() {
        return null;
    }

    @Override
    public List<ItemRequirement> getBuyList() {
        return null;
    }

    @Override
    public boolean isComplete() {
        return Quest.WHAT_LIES_BELOW.getState().equals(Quest.State.COMPLETE);
    }
}
