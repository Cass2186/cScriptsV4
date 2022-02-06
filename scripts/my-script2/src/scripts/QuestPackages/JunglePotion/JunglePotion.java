package scripts.QuestPackages.JunglePotion;

import dax.walker.utils.AccurateMouse;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.Log;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.FairyTalePt1.FairyTalePt1;
import scripts.QuestSteps.BuyItemsStep;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.ItemRequirement;
import scripts.Requirements.Requirement;
import scripts.Tasks.Priority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JunglePotion implements QuestTask {


    private static JunglePotion quest;

    public static JunglePotion get() {
        return quest == null ? quest = new JunglePotion() : quest;
    }

    int GRIMY_SNAKE_WEED = 1525;
    int SNAKE_WEED = 1526;
    int PALM_TREE_ID = 2577;
    int GRIMY_ARDRIGAL = 1527;
    int ARDRIGAL = 1528;
    int GRIMY_SITO_FOIL = 1529;
    int SITO_FOIL = 1530;
    int GRIMY_VOLENCIA_MOSS = 1531;
    int VOLENCIA_MOSS = 1532;
    int GRIMY_ROGUES_PURSE = 1533;
    int ROGUES_PURSE = 1534;

    RSArea START_AREA = new RSArea(new RSTile(2807, 3089, 0), new RSTile(2812, 3083, 0));
    RSArea SNAKE_WEED_AREA = new RSArea(new RSTile(2761, 3047, 0), new RSTile(2767, 3042, 0));
    RSArea SITO_FOIL_AREA = new RSArea(new RSTile(2787, 3051, 0), new RSTile(2793, 3046, 0));
    RSArea MOSS_AREA = new RSArea(new RSTile(2845, 3037, 0), new RSTile(2854, 3029, 0));
    RSArea PURSE_AREA = new RSArea(new RSTile(2827, 3116, 0), new RSTile(2821, 3121, 0));
    RSArea ARDRIGAL_AREA = new RSArea(new RSTile(2871, 3125, 0), new RSTile(2878, 3118, 0));
    RSArea DUNGEON_AREA = new RSArea(new RSTile(2876, 9472, 0), new RSTile(2821, 9532, 0));

    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.ANTIDOTE_PLUS_PLUS[0],3, 50),
                    new GEItem(ItemID.LOBSTER, 12, 50),
                    new GEItem(ItemID.AMULET_OF_GLORY[2], 2, 20),
                    new GEItem(ItemID.COMBAT_BRACELET[0], 1, 20),
                    new GEItem(ItemID.STAMINA_POTION[0], 2, 15),
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
        cQuesterV2.status = "Getting Items";
        General.println("[Debug]: " + cQuesterV2.status);
        BankManager.open(true);
        BankManager.checkEquippedGlory();
        BankManager.depositAll(true);
        BankManager.withdraw(1, true, ItemID.AMULET_OF_GLORY[0]);
        BankManager.withdraw(2, true, ItemID.STAMINA_POTION[0]);
        BankManager.withdraw(2, true, ItemID.ANTIDOTE_PLUS_PLUS[0]);
        BankManager.withdraw(12, true, ItemID.LOBSTER);
        BankManager.close(true);
    }

    public void startQuest() {
        if (!START_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Going to start";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(START_AREA);
        }
        if (NpcChat.talkToNPC("Trufitus")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("It's a nice village, where is everyone?");
            NPCInteraction.handleConversation("Me? How can I help?");
            NPCInteraction.handleConversation("It sounds like just the challenge for me.");
            NPCInteraction.handleConversation();
        }
    }

    public void getVine() {
        cQuesterV2.status = "Getting vine";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(SNAKE_WEED_AREA);

        if (SNAKE_WEED_AREA.contains(Player.getPosition()) && Inventory.find(GRIMY_SNAKE_WEED).length < 1) {
            RSObject[] vine = Objects.findNearest(20, "Marshy jungle vine");
            if (vine.length > 0) {

                if (!vine[0].isClickable())
                    vine[0].adjustCameraTo();

                if (AccurateMouse.click(vine[0], "Search")) {
                    cQuesterV2.status = "Waiting while searching...";
                    General.println("[Debug]: " + cQuesterV2.status);
                    Timer.abc2SkillingWaitCondition(() -> Inventory.find(GRIMY_SNAKE_WEED).length > 0, 180000, 240000);
                }
            }
        }
    }

    public void returnToStart() {

        cQuesterV2.status = "Returning to start";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(START_AREA);

        if (START_AREA.contains(Player.getPosition())) {
            cleanHerb(GRIMY_SNAKE_WEED, SNAKE_WEED);
            cleanHerb(GRIMY_ARDRIGAL, ARDRIGAL);
            cleanHerb(GRIMY_SITO_FOIL, SITO_FOIL);
            cleanHerb(GRIMY_VOLENCIA_MOSS, VOLENCIA_MOSS);
            cleanHerb(GRIMY_ROGUES_PURSE, ROGUES_PURSE);
            if (NpcChat.talkToNPC("Trufitus")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Of course!");
                NPCInteraction.handleConversation();
            }
        }
    }

    public void getArdrigal() {
        cQuesterV2.status = "Going to get Ardrigal";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(ARDRIGAL_AREA);

        if (ARDRIGAL_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Searching Tree";
            General.println("[Debug]: " + cQuesterV2.status);
            if (Utils.clickObj(PALM_TREE_ID, "Search")) {
                Timer.abc2SkillingWaitCondition(() -> Inventory.find(GRIMY_ARDRIGAL).length > 0, 12000, 30000);
            }
        }
    }

    public void cleanHerb(int grimyID, int cleanID) {
        if (Inventory.find(cleanID).length < 1 && Inventory.find(grimyID).length > 0) {
            cQuesterV2.status = "Cleaning herb";
            General.println("[Debug]: " + cQuesterV2.status);
            if (AccurateMouse.click(Inventory.find(grimyID)[0], "Clean"))
                Timer.waitCondition(() -> Inventory.find(cleanID).length > 0, 6000, 9000);
        }
    }

    public void getSitoFoil() {
        cQuesterV2.status = "Going to get Sito Foil";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(SITO_FOIL_AREA);

        if (SITO_FOIL_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Searching Ashes";
            General.println("[Debug]: " + cQuesterV2.status);
            if (Utils.clickObj("Scorched earth", "Search")) {
                Timer.abc2SkillingWaitCondition(() -> Inventory.find(GRIMY_SITO_FOIL).length > 0, 15000, 30000);

            }
        }
    }

    public void getVolenciaMoss() {
        cQuesterV2.status = "Going to get Volencia Moss";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(MOSS_AREA);

        if (MOSS_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Searching Rocks";
            General.println("[Debug]: " + cQuesterV2.status);
            if (Utils.clickObj(2581, "Search"))
                Timer.abc2SkillingWaitCondition(() -> Inventory.find(GRIMY_VOLENCIA_MOSS).length > 0, 12000, 30000);

            if (Utils.clickObj(2581, "Search")) {
                General.println("[Debug]: Getting an extra for FairyTale part 1.");
                Timer.abc2SkillingWaitCondition(() -> Inventory.find(GRIMY_VOLENCIA_MOSS).length > 1, 12000, 30000);

            }
        }
    }

    public void getRoguesPurse() {
        if (!PURSE_AREA.contains(Player.getPosition()) && !DUNGEON_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Going to get Rogue's purse";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(PURSE_AREA);
        }
        if (PURSE_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Going into cave";
            General.println("[Debug]: " + cQuesterV2.status);
            RSObject[] rock = Objects.findNearest(20, 2584);
            if (rock.length > 0) {
                if (AccurateMouse.click(rock[0], "Search")) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation("Yes, I'll enter the cave.");
                    NPCInteraction.handleConversation();
                }
            }
        }
        if (DUNGEON_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Going to the wall";
            General.println("[Debug]: " + cQuesterV2.status);
            Walking.blindWalkTo(new RSTile(2850, 9476, 0));
            Timer.waitCondition(() -> !Player.isMoving(), 22000, 26000);
            General.sleep(General.random(5000, 9000));

            if (Utils.clickObj(2583, "Search")) {
                cQuesterV2.status = "Waiting while searching...";
                General.println("[Debug]: " + cQuesterV2.status);
                Timer.abc2SkillingWaitCondition(() -> Inventory.find(GRIMY_ROGUES_PURSE).length > 0, 90000, 120000);

            }
            if (Combat.getHPRatio() < 50)
                EatUtil.eatFood(false);
        }
    }


    public void leaveCave() {
        if (DUNGEON_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Leaving...";
            General.println("[Debug]: " + cQuesterV2.status);
            Walking.blindWalkTo(new RSTile(2830, 9522));
            Timer.waitCondition(() -> !Player.isMoving(), 22000, 25000);

            RSObject[] wall = Objects.findNearest(20, "Hand holds");
            if (wall.length > 0) {
                if (AccurateMouse.click(wall[0], "Climb")) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                }
            }
        }
    }

    int GAME_SETTING = 175;

    @Override
    public void execute() {
        Log.log("Jungle potion");
        if (!checkRequirements())
            cQuesterV2.taskList.remove(this);
        if (Game.getSetting(175) == 0) {
            buyItems();
            getItems();
            startQuest();
        }
        if (Game.getSetting(175) == 1) {
            getVine();
        }
        if (Game.getSetting(175) == 2) {
            returnToStart();
        }
        if (Game.getSetting(175) == 3) {
            getArdrigal();
        }
        if (Game.getSetting(175) == 4) {
            returnToStart();
        }
        if (Game.getSetting(175) == 5) {
            getSitoFoil();
        }
        if (Game.getSetting(175) == 6) {
            returnToStart();
        }
        if (Game.getSetting(175) == 7) {
            getVolenciaMoss();
        }
        if (Game.getSetting(175) == 8) {
            returnToStart();
        }
        if (Game.getSetting(175) == 9) {
            getRoguesPurse();
        }
        if (Game.getSetting(175) == 10) {
            leaveCave();
            returnToStart();
        }
        if (Game.getSetting(175) == 12) {
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
        Log.log("Jungle potion");
        return
                cQuesterV2.taskList.get(0).equals(this);
    }

    @Override
    public String questName() {
        return "Jungle Potion";
    }

    @Override
    public boolean checkRequirements() {
        return Game.getSetting(Quests.DRUIDIC_RITUAL.getGameSetting()) == 4;
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
