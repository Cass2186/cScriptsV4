package scripts.QuestPackages.WitchsPotion;


import org.tribot.api.General;
import org.tribot.api2007.Game;
import org.tribot.api2007.GroundItems;
import org.tribot.api2007.types.RSTile;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.WitchsHouse.WitchsHouse;
import scripts.QuestSteps.*;
import scripts.Requirements.InventoryRequirement;
import scripts.Requirements.ItemReq;
import scripts.Requirements.ItemRequirement;
import scripts.Requirements.Requirement;
import scripts.Tasks.Priority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WitchsPotion implements QuestTask {

    private static WitchsPotion quest;

    public static WitchsPotion get() {
        return quest == null ? quest = new WitchsPotion() : quest;
    }

    ItemReq ratTail = new ItemReq("Rat's tail", ItemID.RATS_TAIL);
    ItemReq onion = new ItemReq("Onion", ItemID.ONION);
    ItemReq cookedMeat = new ItemReq("Burnt meat", ItemID.COOKED_MEAT);
    ItemReq burntMeat = new ItemReq("Burnt meat", ItemID.BURNT_MEAT);
    ItemReq eyeOfNewt = new ItemReq("Eye of newt", ItemID.EYE_OF_NEWT, 1);

    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.ONION, 1, 300),
                    new GEItem(ItemID.COOKED_MEAT, 1, 300),
                    new GEItem(ItemID.EYE_OF_NEWT, 1, 300),
                    new GEItem(ItemID.STAMINA_POTION[0], 1, 15),
                    new GEItem(ItemID.RING_OF_WEALTH[0], 1, 25)
            )
    );

    InventoryRequirement initialItemReqs = new InventoryRequirement(new ArrayList<>(
            Arrays.asList(onion, cookedMeat, eyeOfNewt,
                    new ItemReq(ItemID.STAMINA_POTION[0], 1, 0),
                    new ItemReq(ItemID.RING_OF_WEALTH[0], 1, 0, true)
            )
    ));

    BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);


    NPCStep talkToWitch = new NPCStep("Hetty", new RSTile(2968, 3205, 0),
            new String[]{"I am in search of a quest.",
                    "Yes, help me become one with my darker side."},
            onion, eyeOfNewt, cookedMeat);

    NPCStep returnToWitch = new NPCStep("Hetty", new RSTile(2968, 3205, 0),
            onion, eyeOfNewt, burntMeat, ratTail);

    ObjectStep drinkPotion = new ObjectStep(2024, new RSTile(2968, 3206, 0),
            "Drink From");

    UseItemOnObjectStep burnMeat = new UseItemOnObjectStep(cookedMeat.getId(), "Range",
            new RSTile(2963, 3212, 0), burntMeat.check());

    public void getRatTail() {
        if (!ratTail.check()) {
            cQuesterV2.status = "Getting Rat Tail";
            General.println("[Debug]: " + cQuesterV2.status);

            PathingUtil.walkToTile(new RSTile(2956, 3203, 0), 4, false);

            if (Utils.clickNPC("Rat", "Attack"))
                Timer.abc2WaitCondition(() -> GroundItems.find(ratTail.getId()).length > 0, 35000, 45000);

            Utils.clickGroundItem(ratTail.getId());
        }
    }


    @Override
    public String toString() {
        return "Witch's Potion";
    }

    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return Game.getSetting(67) <= 3
                &&
        cQuesterV2.taskList.get(0).equals(this);
    }

    @Override
    public void execute() {
        if (Game.getSetting(67) == 0) {
            if (!initialItemReqs.check()){
                cQuesterV2.status = "Buying items";
                buyStep.buyItems();
                cQuesterV2.status = "Getting items";
                initialItemReqs.withdrawItems();
            }
            cQuesterV2.status = "Going to quest start";
            talkToWitch.execute();
        }
        if (Game.getSetting(67) == 1) {
            getRatTail();
            if (!burntMeat.check()) {
                cQuesterV2.status = "Burning Meat";
                burnMeat.useItemOnObject();
            }
            cQuesterV2.status = "Returning to Hetty";
            returnToWitch.execute();
        }
        if (Game.getSetting(67) == 2) {
            cQuesterV2.status = "Drinking Potion";
            drinkPotion.execute();

        }  if (Game.getSetting(67) >= 3) {
            Utils.closeQuestCompletionWindow();
            cQuesterV2.taskList.remove(WitchsPotion.get());
        }

    }

    @Override
    public String questName() {
        return "Witch's Potion";
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