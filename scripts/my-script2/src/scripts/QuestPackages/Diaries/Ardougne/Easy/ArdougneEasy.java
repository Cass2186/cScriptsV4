package scripts.QuestPackages.Diaries.Ardougne.Easy;

import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.Quest;
import scripts.ItemID;
import scripts.NpcID;
import scripts.ObjectID;
import scripts.QuestSteps.NPCStep;
import scripts.QuestSteps.ObjectStep;
import scripts.QuestSteps.QuestStep;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.ItemRequirement;
import scripts.Requirements.Quest.QuestRequirement;
import scripts.Requirements.Requirement;
import scripts.Requirements.SkillRequirement;
import scripts.Requirements.Util.ConditionalStep;
import scripts.Requirements.VarplayerRequirement;
import scripts.Tasks.Priority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArdougneEasy implements QuestTask {

    // Items required
    ItemRequirement rustySword, silk, coins;

    // Quests required
    Requirement runeMysteries, biohazard;

    Requirement notEssMine, notStealCake, notSellSilk, notEastArdyAltar, notFishingTrawler, notEnterCombatCamp,
            notIdentifySword, notWildyLever, notAlecksEmporium, notProbitaPet;
    NPCStep probitaPet;
    QuestStep claimReward, essMine, stealCake, sellSilk, eastArdyAltar, fishingTrawler, enterCombatCamp,
            identifySword, wildyLever, alecksEmporium;

    public QuestStep loadStep() {
        setupRequirements();
        setupSteps();

        ConditionalStep doEasy = new ConditionalStep( claimReward);

        doEasy.addStep(notAlecksEmporium, alecksEmporium);
        doEasy.addStep(notFishingTrawler, fishingTrawler);
        doEasy.addStep(notIdentifySword, identifySword);
        doEasy.addStep(notEssMine, essMine);
        doEasy.addStep(notStealCake, stealCake);
        doEasy.addStep(notSellSilk, sellSilk);
        doEasy.addStep(notProbitaPet, probitaPet);
        doEasy.addStep(notEastArdyAltar, eastArdyAltar);
        doEasy.addStep(notWildyLever, wildyLever);
        doEasy.addStep(notEnterCombatCamp, enterCombatCamp);

        return doEasy;
    }

    public void setupRequirements() {
        notEssMine = new VarplayerRequirement(1196, false, 0);
        notStealCake = new VarplayerRequirement(1196, false, 1);
        notSellSilk = new VarplayerRequirement(1196, false, 2);
        notEastArdyAltar = new VarplayerRequirement(1196, false, 4);
        notFishingTrawler = new VarplayerRequirement(1196, false, 5);
        notEnterCombatCamp = new VarplayerRequirement(1196, false, 6);
        notIdentifySword = new VarplayerRequirement(1196, false, 7);
        notWildyLever = new VarplayerRequirement(1196, false, 9);
        notAlecksEmporium = new VarplayerRequirement(1196, false, 11);
        notProbitaPet = new VarplayerRequirement(1196, false, 12);

        silk = new ItemRequirement( ItemID.SILK, 1);
        rustySword = new ItemRequirement(ItemID.RUSTY_SWORD, 1);
        coins = new ItemRequirement(ItemID.COINS_995,5000);

        runeMysteries = new QuestRequirement(Quest.RUNE_MYSTERIES, Quest.State.COMPLETE);
        biohazard = new QuestRequirement(Quest.BIOHAZARD, Quest.State.COMPLETE);
    }

    public void setupSteps()
    {
        essMine = new NPCStep( NpcID.WIZARD_CROMPERTY, new RSTile(2683, 3326, 0),
                "Have Wizard Cromperty teleport you to the Rune essence mine.");
        essMine.addDialogStep("Can you teleport me to the Rune Essence?");

        stealCake = new ObjectStep( ObjectID.BAKERS_STALL_11730, new RSTile(2668, 3311, 0),
                "Steal a cake from the East Ardougne market stalls.");

        sellSilk = new NPCStep( NpcID.SILK_MERCHANT_8728, new RSTile(2655, 3300, 0),
                "Sell silk to the Silk trader in East Ardougne for 60 coins each.");// finish dialog
        sellSilk.addDialogStep("120 coins.", "I'll give it do you for 60.");

        eastArdyAltar = new ObjectStep( ObjectID.ALTAR, new RSTile(2618, 3309, 0),
                "Use the altar in East Ardougne's church (requires less than full Prayer points).");

        probitaPet = new NPCStep("Probita", new RSTile(2621, 3294, 0));
        probitaPet.setInteractionString("Check");

        wildyLever = new ObjectStep( ObjectID.LEVER_1814, new RSTile(2561, 3311, 0),
                "Use the Ardougne lever to teleport to the Wilderness (you may pull the lever there to return).");


        enterCombatCamp = new ObjectStep( ObjectID.GATE_2041, new RSTile(2518, 3356, 0),
                "Enter the Combat Training Camp north of West Ardougne.");

        fishingTrawler = new ObjectStep( ObjectID.GANGPLANK_4977, new RSTile(2675, 3170, 0),
                "Go out fishing on the Fishing Trawler.");

        identifySword = new NPCStep( NpcID.TINDEL_MARCHANT, new RSTile(2676, 3152, 0), rustySword, coins);
        identifySword.addDialogStep("Ok, I'll give it a go!");

        alecksEmporium = new NPCStep("Aleck", new RSTile(2566, 3083, 0));
        alecksEmporium.addDialogStep("Ok, let's see what you've got!");

        claimReward = new NPCStep("Two pints", new RSTile(2574, 3323, 0));
        claimReward.addDialogStep("I have a question about my Achievement Diary.");
    }


    public List<ItemRequirement> getItemRequirements()
    {
        return Arrays.asList(rustySword, silk, coins.quantity(100));
    }

    @Override
    public List<Requirement> getGeneralRequirements() {
        List<Requirement> reqs = new ArrayList<>();
        reqs.add(new SkillRequirement(Skills.SKILLS.THIEVING, 5));

        reqs.add(runeMysteries);
        reqs.add(biohazard);

        return reqs;
    }

    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return false;
    }

    @Override
    public void execute() {

    }

    @Override
    public String questName() {
        return "Ardougne Easy";
    }

    @Override
    public boolean checkRequirements() {
        return false;
    }


    @Override
    public List<ItemRequirement> getBuyList() {
        return null;
    }

    @Override
    public boolean isComplete() {
        return false;
    }
}
