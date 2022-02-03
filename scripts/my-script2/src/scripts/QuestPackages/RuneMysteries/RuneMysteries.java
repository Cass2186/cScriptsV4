package scripts.QuestPackages.RuneMysteries;


import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Game;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestSteps.BuyItemsStep;
import scripts.QuestSteps.NPCStep;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.InventoryRequirement;
import scripts.Requirements.ItemReq;
import scripts.Tasks.Priority;

import java.util.ArrayList;
import java.util.Arrays;

public class RuneMysteries implements QuestTask {

    private static RuneMysteries quest;

    public static RuneMysteries get() {
        return quest == null ? quest = new RuneMysteries() : quest;
    }

    int VARROCK_TELEPORT = 8007;
    int LUMBRIDGE_TELEPORT = 8008;

    ItemReq airTalisman = new ItemReq(ItemID.AIR_TALISMAN);
    ItemReq researchPackage = new ItemReq(ItemID.RESEARCH_PACKAGE);
    ItemReq notes = new ItemReq(ItemID.NOTES);

    NPCStep talkToHoracio = new NPCStep("Duke Horacio", new RSTile(3210, 3220, 1),
            new String[]{"Have you any quests for me?", "Have you any quests for me?", "Yes."});
    NPCStep talkToSedridor = new NPCStep("Sedridor", new RSTile(3104, 9571, 0),
            new String[]{"I'm looking for the head wizard.", "Ok, here you are.", "Yes, certainly."}, airTalisman);
    NPCStep finishTalkingToSedridor = new NPCStep("Sedridor", new RSTile(3104, 9571, 0),
            new String[]{"Yes, certainly."});
    NPCStep talkToAubury = new NPCStep(NpcID.AUBURY, new RSTile(3253, 3401, 0),
            new String[]{"I have been sent here with a package for you."}, researchPackage);
    NPCStep talkToAudburyAgain = new NPCStep(NpcID.AUBURY, new RSTile(3253, 3401, 0));
    NPCStep talkToSedridor2 = new NPCStep("Sedridor", new RSTile(3104, 9571, 0), notes);

    // old areas for nick
    RSArea START_AREA = new RSArea(new RSTile(3213, 3218, 1), new RSTile(3208, 3225, 1));
    RSArea VARROCK_AREA = new RSArea(new RSTile(3251, 3400, 0), new RSTile(3254, 3402, 0));
    RSArea WIZARD_TOWER = new RSArea(new RSTile(3106, 9574, 0), new RSTile(3102, 9568, 0));


    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.LUMBRIDGE_TELEPORT, 4, 60),
                    new GEItem(ItemID.VARROCK_TELEPORT, 3, 60),
                    new GEItem(ItemID.NECKLACE_OF_PASSAGE[0], 1, 100),
                    new GEItem(ItemID.STAMINA_POTION[0], 1, 15),
                    new GEItem(ItemID.RING_OF_WEALTH[0], 1, 25)
            )
    );

    InventoryRequirement initialItemReqs = new InventoryRequirement(new ArrayList<>(
            Arrays.asList(
                    new ItemReq(ItemID.LUMBRIDGE_TELEPORT, 4, 1),
                    new ItemReq(ItemID.VARROCK_TELEPORT, 4, 1),
                    new ItemReq(ItemID.NECKLACE_OF_PASSAGE[0], 1, 0, true),
                    new ItemReq(ItemID.STAMINA_POTION[0], 1, 0),
                    new ItemReq(ItemID.RING_OF_WEALTH[0], 1, 0, true)
            )
    ));

    BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);


    public void buyItems() {
        if (!initialItemReqs.check()) {
            cQuesterV2.status = "Buying Items";
            General.println("[Debug]: Buying Items");
            buyStep.buyItems();
        }
    }

    public void getItems() {
        if (!initialItemReqs.check()) {
            cQuesterV2.status = "Getting items";
            General.println("[Debug]: " + cQuesterV2.status);
            BankManager.open(true);
            BankManager.checkEquippedGlory();
            BankManager.depositAll(true);
            BankManager.withdraw(2, true, LUMBRIDGE_TELEPORT);
            BankManager.withdraw(3, true, VARROCK_TELEPORT);
            BankManager.withdraw(1, true, ItemID.NECKLACE_OF_PASSAGE[0]);
            BankManager.withdraw(1, true, ItemID.STAMINA_POTION[0]);
            BankManager.withdraw(1, true, ItemID.RING_OF_WEALTH[0]);
            Banking.close();
        }
    }

    public void startQuest() {
        if (!initialItemReqs.check()) {
            buyItems();
            getItems();
        } else {
            cQuesterV2.status = "Starting Quest";
            General.println("[Debug]: " + cQuesterV2.status);
            talkToHoracio.execute();
        }
    }

    public void goToSedridorFirst() {
        cQuesterV2.status = "Going to wizard's tower";
        General.println("[Debug]: " + cQuesterV2.status);
        talkToSedridor.execute();
    }

    public void step4() {
        cQuesterV2.status = "Returning to wizard's tower";
        General.println("[Debug]: " + cQuesterV2.status);
        talkToSedridor2.execute();

        // might need Utils.continuingChat() here due to a weird interface, trying typing a space instead (untested)=
        Keyboard.typeString(" ");
    }

    @Override
    public void execute() {
        if (!checkRequirements())
            cQuesterV2.taskList.remove(this);

        else if (Game.getSetting(63) == 0) {
            startQuest();
        } else if (Game.getSetting(63) == 1 || Game.getSetting(63) == 2) {
            goToSedridorFirst();
        } else if (Game.getSetting(63) == 3) {
            cQuesterV2.status = "Going to Aubury";
            talkToAubury.execute();

        } else if (Game.getSetting(63) == 4) {
            talkToAudburyAgain.execute();
        } else if (Game.getSetting(63) == 5) {
            step4();
        } else if (Game.getSetting(63) == 6) {
            cQuesterV2.taskList.remove(this);
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
    public String questName() {
        return "Rune mysteries";
    }

    @Override
    public boolean checkRequirements() {
        return true; // no requirements
    }
}
