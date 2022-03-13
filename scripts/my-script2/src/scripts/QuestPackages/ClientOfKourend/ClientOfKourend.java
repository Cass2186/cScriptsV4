package scripts.QuestPackages.ClientOfKourend;


import dax.walker.utils.AccurateMouse;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.Log;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestSteps.BuyItemsStep;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.ItemRequirement;
import scripts.Requirements.Requirement;
import scripts.Tasks.Priority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClientOfKourend implements QuestTask {

    private static ClientOfKourend quest;

    public static ClientOfKourend get() {
        return quest == null ? quest = new ClientOfKourend() : quest;
    }

    int FEATHER = 314;
    int SCROLL = 21259;
    int ENCHANTED_QUILL = 21260;
    int ORB = 21261;
    int FAVOUR_CERTIFICATE = 22367;

    RSArea PORT_SARIM = new RSArea(new RSTile(3055, 3245, 0), new RSTile(3049, 3249, 0));
    RSArea START_AREA = new RSArea(new RSTile(1828, 3688, 0), new RSTile(1821, 3691, 0));
    RSArea GENERAL_STORE = new RSArea(new RSTile(1812, 3722, 0), new RSTile(1803, 3729, 0));
    RSArea GENERAL_STORE2 = new RSArea(new RSTile(1723, 3716, 0), new RSTile(1718, 3730, 0));
    //  RSArea GENERAL_STORE3 = new RSArea(new RSTile(1556, 3714, 0), new RSTile(1549, 3720, 0));
    RSArea GENERAL_STORE4 = new RSArea(new RSTile(1549, 3631, 0), new RSTile(1539, 3638, 0));
    RSArea GENERAL_STORE5 = new RSArea(new RSTile(1772, 3590, 0), new RSTile(1776, 3587, 0));
    RSArea DARK_ALTAR = new RSArea(new RSTile(1644, 3887, 0), new RSTile(1664, 3877, 0));
    RSArea a = new RSArea(new RSTile(1674, 3885, 0), new RSTile(1703, 3873, 0));
    RSArea SHAYZEIN_AREA = new RSArea(new RSTile(1542, 3634, 0), new RSTile(1549, 3629, 0));
    RSArea GENERAL_STORE3 = new RSArea(new RSTile(1550, 3719, 0), new RSTile(1555, 3715, 0));

    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(FEATHER, 1, 300),
                    new GEItem(ItemID.LOBSTER, 10, 60),

                    new GEItem(ItemID.GAMES_NECKLACE[0], 2, 30),
                    new GEItem(ItemID.AMULET_OF_GLORY[2], 2, 30),
                    new GEItem(ItemID.RING_OF_DUELING[0], 2, 30),
                    new GEItem(ItemID.STAMINA_POTION[0], 3, 30),
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
        General.sleep(200);
        BankManager.open(true);
        BankManager.checkEquippedGlory();
        BankManager.depositAll(true);
        BankManager.withdraw(1, true, ItemID.GAMES_NECKLACE[0]);
        BankManager.withdraw(1, true, ItemID.AMULET_OF_GLORY[0]);
        BankManager.withdraw(1, true, FEATHER);
        BankManager.withdraw(10, true, ItemID.LOBSTER);
        BankManager.withdraw(1, true, ItemID.RING_OF_DUELING[0]);
        BankManager.withdraw(2, true, BankManager.STAMINA_POTION[0]);
        BankManager.close(true);
    }

    public void startQuest() {
        if (Inventory.find(FEATHER).length > 0) {
            cQuesterV2.status = "Starting Quest";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(START_AREA);
            if (START_AREA.contains(Player.getPosition())) {
                NpcChat.talkToNPC("Veos");
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Have you got any quests for me?");
                NPCInteraction.handleConversation("Sounds interesting! How can I help?", "Yes.");
                NPCInteraction.handleConversation();
                Utils.modSleep();
            }
        }
    }

    public void makeQuill() {
        if (Utils.useItemOnItem(SCROLL, FEATHER))
            Timer.abc2WaitCondition(() -> Inventory.find(ENCHANTED_QUILL).length > 0, 6000, 9000);
    }


    public void goToGeneralStore() {
        cQuesterV2.status = "Going to Leenz";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(GENERAL_STORE);

        if (GENERAL_STORE.contains(Player.getPosition())) {
            NpcChat.talkToNPC("Leenz");
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Can I ask you about Port Piscarilius?");
            NPCInteraction.handleConversation("Why should I gain favour with Port Piscarilius?");
            NPCInteraction.handleConversation();
        }
    }

    public void goToGeneralStore2() {
        cQuesterV2.status = "Going to Regath";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(GENERAL_STORE2);
        if (GENERAL_STORE2.contains(Player.getPosition())) {
            NpcChat.talkToNPC("Regath");
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Can I ask you about Arceuus?");
            NPCInteraction.handleConversation("Why should I gain favour with Arceuus?");
            NPCInteraction.handleConversation();
            NPCInteraction.handleConversation();
            Utils.continuingChat();
            NPCInteraction.handleConversation();
            NPCInteraction.handleConversation();
        }
    }

    public void goToGeneralStore3() {
        cQuesterV2.status = "Going to Munty";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToTile(new RSTile(1553, 3749, 0), 2, false);

        if (NpcChat.talkToNPC("Munty")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Can I ask you about Lovakengj?");
            NPCInteraction.handleConversation("Why should I gain favour with Lovakengj?");
            NPCInteraction.handleConversation();


        }
    }


    public void goToGeneralStore4() {
        cQuesterV2.status = "Going to Jennifer";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToTile(new RSTile(1519, 3590, 0), 2, false);
        if (NpcChat.talkToNPC("Jennifer")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Can I ask you about Shayzien?");
            NPCInteraction.handleConversation("Why should I gain favour with Shayzien?");
            NPCInteraction.handleConversation();

        }
    }

    public void goToGeneralStore5() {
        cQuesterV2.status = "Going to Horace";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(GENERAL_STORE5);
        if (NpcChat.talkToNPC("Horace")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Can I ask you about Hosidius?");
            NPCInteraction.handleConversation("Why should I gain favour with Hosidius?");
            NPCInteraction.handleConversation();
        }
    }

    public void returnToVeos() {
        cQuesterV2.status = "Going to Veos";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(START_AREA);
        if (START_AREA.contains(Player.getPosition())) {
            NpcChat.talkToNPC("Veos");
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Let's talk about your client...");
            NPCInteraction.handleConversation();
        }
    }

    public void goToDarkAltar() {
        cQuesterV2.status = "Going to Dark Altar";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(DARK_ALTAR);
        if (DARK_ALTAR.contains(Player.getPosition())) {
            PathingUtil.walkToArea(a);
        }
        if (a.contains(Player.getPosition())) {
            Utils.modSleep();
            AccurateMouse.click(Inventory.find(ORB)[0], "Activate");
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            Utils.modSleep();
        }
    }

    int LAMP = 21262;


    public void claimRewardAgility() {
        cQuesterV2.status = "Claiming Agility xp";
        General.println("[Debug]: " + cQuesterV2.status);
        if (Inventory.find(LAMP).length > 0) {
            AccurateMouse.click(Inventory.find(LAMP)[0], "Rub");
            Timer.abc2WaitCondition(() -> Interfaces.get(240, 9) != null, 5000, 9000);
            if (Interfaces.get(240, 9) != null) {
                if (InterfaceUtil.clickInterfaceAction(240, "Agility")) {
                    General.sleep(General.random(700, 2000));
                    Interfaces.get(240, 26).click();
                    General.sleep(General.random(700, 2000));
                }
            }
        }
        if (Inventory.find(LAMP).length > 0) {
            AccurateMouse.click(Inventory.find(LAMP)[0], "Rub");
            Timer.abc2WaitCondition(() -> Interfaces.get(240, 9) != null, 5000, 9000);
            if (Interfaces.get(240, 9) != null) {
                if (InterfaceUtil.clickInterfaceAction(240, "Agility")) {
                    General.sleep(800, 2000);
                    Interfaces.get(240, 26).click();
                    General.sleep(900, 2200);
                }
            }
        }

    }

    public void claimFavour(String house) {
        if (Inventory.find(FAVOUR_CERTIFICATE).length > 0) {
            AccurateMouse.click(Inventory.find(FAVOUR_CERTIFICATE)[0], "Read");
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation(house);
            NPCInteraction.handleConversation("Yes.");
            NPCInteraction.handleConversation();
        }
    }

    private Timer safteyTimer = new Timer(General.random(480000, 600000)); // 8- 10min

    int GAME_SETTING = 1566;


    @Override
    public void execute() {
        Log.log("[Debug]: Game setting 1566 is: " +Game.getSetting(1566) );
        if (Game.getSetting(1566) == 0) {
            buyItems();
            getItems();
            startQuest();
        }
        if (Game.getSetting(1566) == 1) {
            makeQuill();
            goToGeneralStore();
        }
        if (Game.getSetting(1566) == 65) {
            goToGeneralStore2();
        }
        if (Game.getSetting(1566) == 193) {
            goToGeneralStore3();
        }
        if (Game.getSetting(1566) == 449) {
            goToGeneralStore4();
        }
        if (Game.getSetting(1566) == 961) {
            goToGeneralStore5();
        }
        if (Game.getSetting(1566) == 1986) {
            returnToVeos();
        }
        if (Game.getSetting(1566) == 1988) {
            goToDarkAltar();
        }
        if (Game.getSetting(1566) == 1989 || Game.getSetting(1566) == 1990) {
            returnToVeos();
        }

        if (Game.getSetting(1566) == 1991) {
            Utils.closeQuestCompletionWindow();
            claimRewardAgility();
        }
        if (Game.getSetting(1566) == 1992) {
            claimRewardAgility();
            cQuesterV2.taskList.remove(ClientOfKourend.get());
        }
        if (Game.getSetting(1566) >= 1993) {
            claimFavour("Hosidius");
            cQuesterV2.taskList.remove(ClientOfKourend.get());
        }
    }

    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return
                cQuesterV2.taskList.get(0).equals(ClientOfKourend.get());
    }


    @Override
    public String questName() {
        return "Client of Kourend";
    }

    @Override
    public boolean checkRequirements() {
        return Utils.getVarBitValue(QuestVarbits.QUEST_X_MARKS_THE_SPOT.getId()) >= 7;
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
