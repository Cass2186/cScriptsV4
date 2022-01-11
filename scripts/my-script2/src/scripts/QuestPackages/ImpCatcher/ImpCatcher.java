package scripts.QuestPackages.ImpCatcher;


import org.tribot.api2007.Game;
import org.tribot.api2007.types.RSItemDefinition;
import org.tribot.api2007.types.RSTile;
import scripts.GEManager.GEItem;
import scripts.ItemId;
import scripts.QuestPackages.MonksFriend.MonksFriend;
import scripts.QuestSteps.BuyItemsStep;
import scripts.QuestSteps.NPCStep;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.InventoryRequirement;
import scripts.Requirements.ItemReq;
import scripts.Tasks.Priority;
import scripts.Utils;
import scripts.cQuesterV2;

import java.util.ArrayList;
import java.util.Arrays;

public class ImpCatcher implements QuestTask {


    private static ImpCatcher quest;

    public static ImpCatcher get() {
        return quest == null ? quest = new ImpCatcher() : quest;
    }


    ItemReq blackBead = new ItemReq( ItemId.BLACK_BEAD, 1);
    ItemReq whiteBead = new ItemReq( ItemId.WHITE_BEAD,1);
    ItemReq redBead = new ItemReq( ItemId.RED_BEAD,1);
    ItemReq yellowBead = new ItemReq( ItemId.YELLOW_BEAD,1);


    NPCStep doQuest = new NPCStep("Wizard Mizgog", new RSTile(3103, 3163, 2),
            new String[]{"Give me a quest please."},
            blackBead, whiteBead, redBead, yellowBead);


    public ArrayList<GEItem> getItemsToBuyList(ItemReq... reqs) {
        ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>();
        for (ItemReq r : reqs) {
            RSItemDefinition def = RSItemDefinition.get(r.getId());
            if (def != null && def.isTradeableOnGrandExchange()) {
                itemsToBuy.add(new GEItem(r.getId(), r.getAmount(), 25));
            }
        }
        return itemsToBuy;
    }

    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemId.BLACK_BEAD, 1, 100),
                    new GEItem(ItemId.RED_BEAD, 1, 100),
                    new GEItem(ItemId.YELLOW_BEAD, 1, 100),
                    new GEItem(ItemId.STAMINA_POTION[0], 1, 15),
                    new GEItem(ItemId.WHITE_BEAD, 1, 100),
                    new GEItem(ItemId.NECKLACE_OF_PASSAGE[0], 1, 100),
                    new GEItem(ItemId.RING_OF_WEALTH[0], 1, 25)
            )
    );

    InventoryRequirement initialItemReqs = new InventoryRequirement(new ArrayList<>(
            Arrays.asList(blackBead, whiteBead, redBead, yellowBead,
                    new ItemReq(ItemId.STAMINA_POTION[0], 1, 0),
                    new ItemReq(ItemId.NECKLACE_OF_PASSAGE[0], 1, 0, true),
                    new ItemReq(ItemId.RING_OF_WEALTH[0], 1, 0, true)
            )
    ));

    BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);



    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return             cQuesterV2.taskList.get(0).equals(this);}

    @Override
    public void execute() {
        if (Game.getSetting(160) == 0 || Game.getSetting(160) == 1) {
            if (!initialItemReqs.check()){
                cQuesterV2.status = "Buying items";
                buyStep.buyItems();
                cQuesterV2.status = "Getting items";
                initialItemReqs.withdrawItems();
            }
            cQuesterV2.status = "Doing quest";
            doQuest.execute();
        }
        if (Game.getSetting(160) == 2) {
            Utils.closeQuestCompletionWindow();
            cQuesterV2.taskList.remove(this);
        }
    }

    @Override
    public String questName() {
        return "Imp Catcher";
    }

    @Override
    public boolean checkRequirements() {
        return true;
    }
}