package scripts.QuestPackages.VampyreSlayer;

import dax.walker.utils.AccurateMouse;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.types.*;
import org.tribot.script.sdk.Quest;
import org.tribot.script.sdk.Waiting;
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

public class VampyreSlayer implements QuestTask {

    private static VampyreSlayer quest;

    public static VampyreSlayer get() {
        return quest == null ? quest = new VampyreSlayer() : quest;
    }

    int stake = 1549;
    int garlic = 1550;
    int hammer = 2347;
    int earthRune = 557;
    int lobster = 379;
    int mindRune = 558;
    int airStaff = 1381;
    int beer = 1917;
    int varrockTab = 8007;


    RSTile startTile = new RSTile(3098, 3268, 0);
    RSTile vampireTile = new RSTile(3077, 9774, 0);
    RSArea vampireArea = new RSArea(vampireTile, 10);
    RSArea barArea = new RSArea(new RSTile(3218, 3401, 0), new RSTile(3227, 3394, 0));
    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.BEER, 1, 300),
                    new GEItem(garlic, 1, 300),
                    new GEItem(ItemID.HAMMER, 1, 500),
                    new GEItem(ItemID.LOBSTER, 15, 50),
                    new GEItem(ItemID.VARROCK_TELEPORT, 5, 50),
                    new GEItem(ItemID.STAFF_OF_AIR, 1, 50),
                    new GEItem(ItemID.MIND_RUNE, 200, 20),
                    new GEItem(ItemID.EARTH_RUNE, 600, 20),
                    new GEItem(ItemID.FIRE_RUNE, 600, 20),
                    new GEItem(ItemID.AMULET_OF_GLORY[2], 1, 20),

                    new GEItem(ItemID.STAMINA_POTION[0], 1, 15),
                    new GEItem(ItemID.RING_OF_WEALTH[0], 1, 25)
            )
    );


    BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);

    public void buyItems() {
        cQuesterV2.status = "Buying Items";
        General.println("[Debug]: Buying Items");
        buyStep.buyItems();
    }

    public void getItems() {
        cQuesterV2.status = "Getting items";
        General.println("[Debug]: Withdrawing Quest items.");
        BankManager.open(true);
        Banking.depositAll();
        BankManager.checkEquippedGlory();
        BankManager.withdraw(1, true, garlic);
        BankManager.withdraw(1, true, airStaff);
        BankManager.withdraw(1, true, hammer);
        BankManager.withdraw(17, true, lobster);
        BankManager.withdraw(200, true, mindRune);
        BankManager.withdraw(500, true, earthRune);
        BankManager.withdraw(1, true, beer);
        BankManager.withdraw(2, true, varrockTab);
        BankManager.withdraw(1, true, ItemID.STAMINA_POTION[0]);
        BankManager.close(true);
        Utils.equipItem(airStaff);
    }

    public void step1() {
        cQuesterV2.status = "Starting Quest";
        PathingUtil.walkToTile(startTile);
        if (NpcChat.talkToNPC("Morgan")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Ok, I'm up for an adventure.", "Yes.");
            NPCInteraction.handleConversation();
        }
    }

    public void step2() {
        checkInventory(garlic, hammer, mindRune, earthRune, lobster);
        cQuesterV2.status = "Getting stake";
        PathingUtil.walkToArea(barArea);
        if (NpcChat.talkToNPC("Dr Harlow")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Morgan needs your help!");
            NPCInteraction.handleConversation();
        }

    }

    public void step3() {
        checkInventory(garlic, hammer, mindRune, earthRune, lobster);
        if (!vampireArea.contains(Player.getPosition()) && Inventory.find(stake).length < 1) {
            cQuesterV2.status = "Getting stake";
            PathingUtil.walkToArea(barArea);
            if (NpcChat.talkToNPC("Dr Harlow")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                NPCInteraction.handleConversation();
            }

        }
    }

    public void checkInventory(int... items) {
        if (!BankManager.checkInventoryItems(items)) {
            buyItems();
            getItems();
        }
    }

    public void step4() {
        checkInventory(garlic, stake, hammer, mindRune, earthRune, lobster);

        if (Inventory.find(stake).length > 0) {
            cQuesterV2.status = "Going to Vampire";
            PathingUtil.walkToTile(vampireTile);
            Autocast.enableAutocast(Autocast.EARTH_STRIKE);
        }
    }

    public void killVampire() {
        cQuesterV2.status = "Killing Vampire";
        Combat.setAutoRetaliate(true);
        if (vampireArea.contains(Player.getPosition())) {
            if (!Combat.isAutoRetaliateOn())
                Combat.setAutoRetaliate(true);

            RSNPC[] vampire = NPCs.findNearest(3482);

            if (!Autocast.isAutocastEnabled(Autocast.EARTH_STRIKE))
                Autocast.enableAutocast(Autocast.EARTH_STRIKE);

            if (vampire.length < 1) {
                RSObject[] coffin = Objects.findNearest(20, 2614);
                if (coffin.length > 0 && AccurateMouse.click(coffin[0], "Open")) {
                    Timer.waitCondition(() -> NPCs.findNearest(3482).length > 0, 8000);
                    General.sleep(General.random(3000, 4000));
                }
            }
            if (Combat.getHPRatio() < General.random(51, 61)) {
                RSItem[] invItem1 = Inventory.find(lobster);
                if (invItem1.length > 0 && invItem1[0].click("Eat"))
                    Waiting.waitNormal(500, 100);
            }

            vampire = NPCs.findNearest("Count Draynor");

            while (vampire.length > 0) {
                General.sleep(100, 300);
                RSItem[] lob = Inventory.find(ItemID.LOBSTER);
                if (!Combat.isUnderAttack() && AccurateMouse.click(vampire[0], "Attack")) {
                    Timer.waitCondition(Combat::isUnderAttack, 1000, 4000);
                }

                if (Game.getSetting(178) == 3) {
                    break;
                }
                if (Combat.getHPRatio() < 55 && lob.length > 0) {
                    if (lob[0].click())
                        General.sleep(General.random(300, 600));
                }
                vampire = NPCs.findNearest("Count Draynor");

            }
        }
    }

    @Override
    public void execute() {
        if (Game.getSetting(178) == 0) {
            buyItems();
            getItems();
            step1();
        }
        if (Game.getSetting(178) == 1) {
            step2();
        }
        if (Game.getSetting(178) == 2) {
            step3();
            step4();
            killVampire();
        }
        if (Game.getSetting(178) == 3) {
            Utils.closeQuestCompletionWindow();
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
        return "Vampyre Slayer";
    }

    @Override
    public boolean checkRequirements() {
        return Skills.SKILLS.MAGIC.getActualLevel() >= 9;
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
        return Quest.VAMPYRE_SLAYER.getState().equals(Quest.State.COMPLETE);
    }
}
