package scripts.QuestPackages.XMarksTheSpot;

import dax.walker.utils.AccurateMouse;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.Game;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSTile;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.WitchsPotion.WitchsPotion;
import scripts.QuestSteps.BuyItemsStep;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.InventoryRequirement;
import scripts.Requirements.ItemReq;
import scripts.Requirements.ItemRequirement;
import scripts.Requirements.Requirement;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class XMarksTheSpot implements QuestTask {
    int SPADE = 952;
    private static XMarksTheSpot quest;

    public static XMarksTheSpot get() {
        return quest == null ? quest = new XMarksTheSpot() : quest;
    }


    RSArea START_AREA = new RSArea(new RSTile(3233, 3236, 0), new RSTile(3229, 3242, 0));
    RSArea DIG_AREA_1 = new RSArea(new RSTile(3231, 3209, 0), new RSTile(3229, 3210, 0));
    RSArea DIG_AREA_2 = new RSArea(new RSTile(3203, 3213, 0), new RSTile(3202, 3211, 0));
    RSArea DIG_AREA_3 = new RSArea(new RSTile(3109, 3261, 0), new RSTile(3107, 3263, 0));
    RSArea DIG_AREA_4 = new RSArea(new RSTile(3076, 3260, 0), new RSTile(3078, 3259, 0));
    RSArea FINISH_AREA = new RSArea(new RSTile(3055, 3245, 0), new RSTile(3051, 3248, 0));

    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.SPADE, 1, 300),
                    new GEItem(ItemID.LUMBRIDGE_TELEPORT, 5, 30),
                    new GEItem(ItemID.AMULET_OF_GLORY[2], 1, 30),
                    new GEItem(ItemID.STAMINA_POTION[0], 2, 15),
                    new GEItem(ItemID.RING_OF_WEALTH[0], 1, 25)
            )
    );

    BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);

    public void buyItems() {
        cQuesterV2.status = "Buying Items";
        General.println("[Debug]: " + cQuesterV2.status);
        buyStep.buyItems();
    }

    public void getItems() {
        cQuesterV2.status = "Getting Items";
        General.println("[Debug]: " + cQuesterV2.status);
        BankManager.open(true);
        BankManager.depositAll(true);
        BankManager.withdraw(3, true, ItemID.LUMBRIDGE_TELEPORT);
        BankManager.withdraw(1, true, SPADE);
        BankManager.withdraw(2, true, ItemID.STAMINA_POTION[0]);
        BankManager.close(true);

    }

    public void walkerFailSafe(RSArea area, int nextGameSetting) {
        if (Game.getSetting(2111) != nextGameSetting) {
            PathingUtil.clickScreenWalk(area.getRandomTile());
            Utils.shortSleep();
        }
    }

    public void startQuest() {
        if (!BankManager.checkInventoryItems(SPADE)) {
            buyItems();
            getItems();
        } else {
            cQuesterV2.status = "Starting Quest";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToTile(new RSTile(3229,3240,0 ) ,2, false);
            if (NpcChat.talkToNPC("Veos")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Can I help?", "I'm looking for a quest.");
                NPCInteraction.handleConversation("Sounds good, what should I do?", "Yes.");
                NPCInteraction.handleConversation();
            }
        }
    }

    public void step2() {
        if (!BankManager.checkInventoryItems(SPADE)) {
            buyItems();
            getItems();
        } else {
            cQuesterV2.status = "Going to first clue spot";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(DIG_AREA_1);

            RSItem[] spade = Inventory.find(SPADE);

            if (spade.length > 0 && spade[0].click("Dig")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                walkerFailSafe(DIG_AREA_1, 3);

            }
        }
    }

    public void step3() {
        if (!BankManager.checkInventoryItems(SPADE)) {
            buyItems();
            getItems();
        } else {
            cQuesterV2.status = "Going to second clue spot";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(DIG_AREA_2);
            RSItem[] spade = Inventory.find(SPADE);
            if (spade.length > 0 && spade[0].click("Dig")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                walkerFailSafe(DIG_AREA_2, 4);

            }
        }
    }

    public void step4() {
        cQuesterV2.status = "Going to third clue spot";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(DIG_AREA_3);
        RSItem[] spade = Inventory.find(SPADE);
        if (spade.length > 0 && spade[0].click("Dig")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            walkerFailSafe(DIG_AREA_3, 5);
        }
    }

    public void step5() {
        cQuesterV2.status = "Going to fourth clue spot";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(DIG_AREA_4);
        RSItem[] spade = Inventory.find(SPADE);
        if (spade.length > 0 && spade[0].click("Dig")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            walkerFailSafe(DIG_AREA_4, 6);
        }

    }

    public void step6() {
        cQuesterV2.status = "Finishing quest";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(FINISH_AREA);
        if (NpcChat.talkToNPC("Veos")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }
    }

    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return
                Objects.equals(cQuesterV2.taskList.get(0), XMarksTheSpot.get());
    }



    @Override
    public void execute() {
        cQuesterV2.status = "X Marks the Spot";

        if (Utils.getVarBitValue(8063) == 0 || Utils.getVarBitValue(8063) == 1) {
            buyItems();
            getItems();
            startQuest();
        }
        if (Utils.getVarBitValue(8063) == 2) {
            step2();
        }
        if (Utils.getVarBitValue(8063) == 3) {
            step3();
            Utils.modSleep();
        }
        if (Utils.getVarBitValue(8063) == 4) {
            step4();
            Utils.modSleep();
        }
        if (Utils.getVarBitValue(8063) == 5) {
            step5();
            Utils.modSleep();
        }
        if (Utils.getVarBitValue(8063) == 6) {
            step6();
        }
        if (Utils.getVarBitValue(8063) == 7) {
            Utils.closeQuestCompletionWindow();
            cQuesterV2.taskList.remove(XMarksTheSpot.get());
        }
        if (Utils.getVarBitValue(8063) >= 7) {
            if (Inventory.find("Antique lamp").length > 0) {
                cQuesterV2.status = "Claiming agility xp";
                General.println("[Debug]: " + cQuesterV2.status);
                AccurateMouse.click(Inventory.find(23072)[0], "Rub");
                Timer.abc2WaitCondition(() -> Interfaces.get(240, 9) != null, 5000, 9000);
                if (Interfaces.get(240, 9) != null) {
                    if (InterfaceUtil.clickInterfaceAction(240, "Agility")) {
                        General.sleep(General.random(700, 2000));
                        Interfaces.get(240, 26).click();
                        General.sleep(General.random(700, 2000));
                    }
                }
            }
            cQuesterV2.taskList.remove(XMarksTheSpot.get());
        }

    }

    @Override
    public String questName() {
        return "X Marks the Spot";
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
