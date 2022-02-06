package scripts.QuestPackages.DruidicRitual;

import org.tribot.api2007.Game;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.tasks.Amount;
import org.tribot.script.sdk.tasks.BankTask;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.VarrockMuseum.VarrockMuseum;
import scripts.QuestSteps.*;
import scripts.Requirements.ItemReq;
import scripts.Requirements.ItemRequirement;
import scripts.Requirements.Requirement;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DruidicRitual implements QuestTask {
    private static DruidicRitual quest;

    public static DruidicRitual get() {
        return quest == null ? quest = new DruidicRitual() : quest;
    }

    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.RAW_RAT_MEAT, 1, 150),
                    new GEItem(ItemID.RAW_BEAR_MEAT, 1, 150),
                    new GEItem(ItemID.RAW_BEEF, 1, 150),
                    new GEItem(ItemID.RAW_CHICKEN, 1, 150),
                    new GEItem(ItemID.FALADOR_TELEPORT, 5, 50),
                    new GEItem(ItemID.STAMINA_POTION[0], 2, 20),
                    new GEItem(ItemID.RING_OF_WEALTH[0], 1, 20)
            )
    );

    BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);

    BankTask startInv = BankTask.builder()
            .addInvItem(ItemID.RAW_RAT_MEAT, Amount.of(1))
            .addInvItem(ItemID.RAW_BEAR_MEAT, Amount.of(1))
            .addInvItem(ItemID.RAW_BEEF, Amount.of(1))
            .addInvItem(ItemID.RAW_CHICKEN, Amount.of(1))
            .addInvItem(ItemID.STAMINA_POTION[0], Amount.of(2))
            .addInvItem(ItemID.FALADOR_TELEPORT, Amount.of(3))
            .addInvItem(ItemID.RING_OF_WEALTH[0], Amount.of(1))
            .build();

    public void getStartInv() {
        //TODO fix if it fails
        if (!startInv.isSatisfied()) {
            cQuesterV2.status = "Buying start items";
            buyStep.buyItems();
            BankManager.open(true);
            BankManager.depositAll(true);
            cQuesterV2.status = "Getting start items";
            startInv.execute();
        }
    }


    ItemReq rawRat = new ItemReq("Raw rat meat", ItemID.RAW_RAT_MEAT);
    ItemReq rawBear = new ItemReq("Raw bear meat", ItemID.RAW_BEAR_MEAT);
    ItemReq rawBeef = new ItemReq("Raw beef", ItemID.RAW_BEEF);
    ItemReq rawChicken = new ItemReq("Raw chicken", ItemID.RAW_CHICKEN);


    ItemReq enchantedBear = new ItemReq("Enchanted bear", ItemID.ENCHANTED_BEAR);
    ItemReq enchantedBeef = new ItemReq("Enchanted beef", ItemID.ENCHANTED_BEEF);
    ItemReq enchantedChicken = new ItemReq("Enchanted chicken", ItemID.ENCHANTED_CHICKEN);
    ItemReq enchantedRat = new ItemReq("Enchanted rat", ItemID.ENCHANTED_RAT);

    RSArea sanfewRoom = new RSArea(new RSTile(2893, 3423, 1), new RSTile(2903, 3433, 1));
    RSArea dungeon = new RSArea(new RSTile(2816, 9668, 0), new RSTile(2973, 9855, 0));


    NPCStep talkToKaqemeex = new NPCStep("Kaqemeex", new RSTile(2925, 3486, 0), rawBear, rawBeef, rawChicken, rawRat);
    //   NPCStep goUpToSanfew = new ObjectStep(16671, new RSTile(2899, 3429, 0), "Talk to Sanfew upstairs in the Taverley herblore store.");
    NPCStep talkToSanfew = new NPCStep("Sanfew", new RSTile(2899, 3429, 1));

    ObjectStep enterDungeon = new ObjectStep(16680, new RSTile(2884, 3397, 0),
            "Enter Taverley Dungeon south of Taverley.", rawBear, rawBeef, rawChicken, rawRat);
    UseItemOnObjectStep useRatOnCauldron = new UseItemOnObjectStep(rawRat.getId(),
            "Cauldron of Thunder",
            new RSTile(2893, 9831, 0), !rawRat.check());

    UseItemOnObjectStep useBeefOnCauldron = new UseItemOnObjectStep(rawBeef.getId(), "Cauldron of Thunder",
            new RSTile(2893, 9831, 0), !rawBeef.check());

    UseItemOnObjectStep useBearOnCauldron = new UseItemOnObjectStep(rawBear.getId(),
            "Cauldron of Thunder",
            new RSTile(2893, 9831, 0), !rawBear.check());

    UseItemOnObjectStep useChickenOnCauldron = new UseItemOnObjectStep(rawChicken.getId(), "Cauldron of Thunder",
            new RSTile(2893, 9831, 0), !rawChicken.check());


    //  NPCStep  goUpToSanfewWithMeat = new ObjectStep(16671, new RSTile(2899, 3429, 0), enchantedBear, enchantedBeef, enchantedChicken, enchantedRat);
    NPCStep talkToSanfewWithMeat = new NPCStep("Sanfew", new RSTile(2899, 3429, 1),
            enchantedBear, enchantedBeef, enchantedChicken, enchantedRat);
    NPCStep talkToKaqemeexToFinish = new NPCStep("Kaqemeex", new RSTile(2925, 3486, 0));

    public void setUpSteps() {
        talkToKaqemeex.addDialogStep("I'm in search of a quest.", "Okay, I will try and help.");
        talkToSanfew.addDialogStep("I've been sent to help purify the Varrock stone circle.","Ok, I'll do that then." );
        enterDungeon.addDialogStep("Ok, I'll do that then.");
    }

    @Override
    public String toString() {
        return "Druidic Ritual (" + Game.getSetting(Quests.DRUIDIC_RITUAL.getGameSetting())+ ")";
    }
    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return  cQuesterV2.taskList.get(0).equals(DruidicRitual.get());
    }

    @Override
    public void execute() {
        setUpSteps();
        if (Game.getSetting(Quests.DRUIDIC_RITUAL.getGameSetting()) == 0) {
            getStartInv();
            cQuesterV2.status = "Talking to Kaqemeex";
            talkToKaqemeex.execute();
        } else if (Game.getSetting(Quests.DRUIDIC_RITUAL.getGameSetting()) == 1) {
            cQuesterV2.status = "Talking to Sanfew";
            talkToSanfew.execute();
        } else if (Game.getSetting(Quests.DRUIDIC_RITUAL.getGameSetting()) == 2) {
            cQuesterV2.status = "Blessing Meat";
            useRatOnCauldron.useItemOnObject();
            useBearOnCauldron.useItemOnObject();
            useBeefOnCauldron.useItemOnObject();
            useChickenOnCauldron.useItemOnObject();
            cQuesterV2.status = "Talking to Sanfew";
            talkToSanfewWithMeat.execute();
        } else if (Game.getSetting(Quests.DRUIDIC_RITUAL.getGameSetting()) == 3) {
            cQuesterV2.status = "Talking to Kaqemeex";
            talkToKaqemeexToFinish.execute();
        } else if (Game.getSetting(Quests.DRUIDIC_RITUAL.getGameSetting()) == 4) {
            Utils.closeQuestCompletionWindow();
            cQuesterV2.taskList.remove(DruidicRitual.get());
        }
    }

    @Override
    public String questName() {
        return "Druidic Ritual";
    }

    @Override
    public boolean checkRequirements() {
        return true;
    }
    @Override
    public List<Requirement> getGeneralRequirements() {
        return null;
    }

    @Override
    public List<ItemRequirement> getBuyList() {
        return null;
    }
}
