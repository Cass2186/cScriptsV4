package scripts.QuestPackages.DoricsQuest;

import org.tribot.api.General;
import org.tribot.api2007.Game;
import org.tribot.api2007.types.RSTile;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.CooksAssistant.CooksAssistant;
import scripts.QuestSteps.BuyItemsStep;
import scripts.QuestSteps.NPCStep;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.InventoryRequirement;
import scripts.Requirements.ItemReq;
import scripts.Requirements.ItemRequirement;
import scripts.Tasks.Priority;

import java.util.ArrayList;
import java.util.Arrays;

public class DoricsQuest implements QuestTask {

    private static DoricsQuest quest;

    public static DoricsQuest get() {
        return quest == null ? quest = new DoricsQuest() : quest;
    }

    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemId.CLAY, 6, 200),
                    new GEItem(ItemId.COPPER_ORE, 4, 200),
                    new GEItem(ItemId.IRON_ORE, 2, 50),
                    new GEItem(ItemId.FALADOR_TELEPORT, 5, 50),
                    new GEItem(ItemId.STAMINA_POTION[0], 1, 15),
                    new GEItem(ItemId.RING_OF_WEALTH[0], 1, 25)
            )
    );



    ItemReq clay = new ItemReq( ItemId.CLAY, 6);
    ItemReq copper = new ItemReq( ItemId.COPPER_ORE, 4);
    ItemReq  iron = new ItemReq( ItemId.IRON_ORE, 2);

    BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);

    NPCStep talkToDoric = new NPCStep("Doric", new RSTile(2951, 3451, 0),
            new String[]{"I wanted to use your anvils.",
                    "Yes, I will get you the materials."}  , clay, copper, iron);

    InventoryRequirement startInventory = new InventoryRequirement(new ArrayList<>(
            Arrays.asList(
                    clay,
                    copper,
                    iron,
                    new ItemReq(ItemId.FALADOR_TELEPORT, 3),
                    new ItemReq(ItemId.STAMINA_POTION[0], 1, 0),
                    new ItemReq(ItemId.RING_OF_WEALTH[0], 1, 0, true,
                            true)
            ))
    );

    public  void getAndBuyItems() {
        if (!clay.check() || !copper.check() || !iron.check()) {
           cQuesterV2.status = "Dorics: Getting Items";
            General.println("[Debug]: Getting Items");
            buyStep.buyItems();
            startInventory.withdrawItems();
        }
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
        if (Game.getSetting(31) == 0 || Game.getSetting(31) == 10) {
            getAndBuyItems();
            talkToDoric.execute();
        }
        if (Game.getSetting(31) == 100) {
            Utils.closeQuestCompletionWindow();
            cQuesterV2.taskList.remove(DoricsQuest.get());
        }
    }

    @Override
    public String questName() {
        return "Doric's Quest";
    }

    @Override
    public boolean checkRequirements() {
        return true;
    }
}
