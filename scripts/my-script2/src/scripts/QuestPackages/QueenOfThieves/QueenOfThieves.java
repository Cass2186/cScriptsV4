package scripts.QuestPackages.QueenOfThieves;

import dax.walker.utils.AccurateMouse;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.GameState;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Quest;
import org.tribot.script.sdk.Skill;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.InventoryItem;
import org.tribot.script.sdk.types.WorldTile;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.ScorpionCatcher.ScorpionCatcher;
import scripts.QuestSteps.BuyItemsStep;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.ItemRequirement;
import scripts.Requirements.Quest.QuestRequirement;
import scripts.Requirements.Requirement;
import scripts.Requirements.SkillRequirement;
import scripts.Tasks.Priority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class QueenOfThieves implements QuestTask {

    private static QueenOfThieves quest;

    public static QueenOfThieves get() {
        return quest == null ? quest = new QueenOfThieves() : quest;
    }

    int STEW = 2003;

    //NPC ID
    int QUEEN_OF_THIEVES_ID = 7929;
    int COINS = 995;
    int TOMAS = 7926;
    int ROBERT = 7905;
    int DEVON = 7906;


    RSArea START_AREA = new RSArea(new RSTile(1793, 3783, 0), new RSTile(1801, 3779, 0));
    RSArea POOR_AREA = new RSArea(new RSTile(1789, 3773, 0), new RSTile(1780, 3782, 0));
    RSArea ROBERT_ORILEY_AREA = new RSArea(new RSTile(1797, 3755, 0), new RSTile(1791, 3760, 0));
    RSArea MANHOLE_AREA = new RSArea(new RSTile(1811, 3747, 0), new RSTile(1814, 3743, 0));
    RSArea DEVON_AREA = new RSArea(new RSTile(1761, 10149, 0), new RSTile(1769, 10145, 0));
    RSArea CONRAD_AREA = new RSArea(new RSTile(1851, 3734, 0), new RSTile(1846, 3736, 0));
    //RSArea QUEEN_AREA = new RSArea(new RSTile(1767, 10150, 0), new RSTile(1763, 10159, 0));
    RSArea HUGHS_HOUSE = new RSArea(new RSTile(1683, 3677, 1), new RSTile(1679, 3681, 1));
    RSArea QUEEN_AREA = new RSArea(new RSTile(1768, 10150, 0), new RSTile(1762, 10159, 0));

    public boolean checkFavour() {
        return Utils.getVarBitValue(4899) >= 200;
    }

    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.STEW, 2, 100),
                    new GEItem(ItemID.AMULET_OF_GLORY4, 1, 20),
                    new GEItem(ItemID.STAMINA_POTION[0], 3, 15),
                    new GEItem(ItemID.RING_OF_WEALTH[0], 1, 25)
            )
    );


    BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);

    public void buyItems() {
        cQuesterV2.status = "Buying Items";
        Log.info("" + cQuesterV2.status);
        buyStep.buyItems();
    }

    public void getItems() {
        cQuesterV2.status = "Getting Items";
        Log.info("" + cQuesterV2.status);
        BankManager.open(true);
        BankManager.checkEquippedGlory();
        BankManager.depositAll(true);
        BankManager.withdraw(2, true, STEW);
        BankManager.withdraw(10000, true, COINS);
        BankManager.withdraw(2, true, ItemID.STAMINA_POTION[0]);
        BankManager.close(true);
    }

    public void startQuest() {
        cQuesterV2.status = "Starting Quest";
        PathingUtil.walkToArea(START_AREA);
        if (NpcChat.talkToNPC(TOMAS)) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("I'm looking for a quest.", "What are you investigating?");
            NPCInteraction.handleConversation( "Yes.");
            NPCInteraction.handleConversation();
        }
    }

    public void step2() {
        cQuesterV2.status = "Step 3: Going to Poor Woman";
        PathingUtil.walkToTile(new RSTile(1802,3743, 0), 2, true);
        if (NpcChat.talkToNPC(7923)) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }

    }

    public void goToRobertORiley() {
        cQuesterV2.status = "Step 4: Going to Robert O'Riley";
        PathingUtil.walkToArea(ROBERT_ORILEY_AREA);
        if (NpcChat.talkToNPC(ROBERT)) {
            NpcChat.handle(true, "Okay." ,"Nope,");
        }
    }


    public void goToDevon() {
        cQuesterV2.status = "Step 5: Going to Devon.";
        PathingUtil.walkToTile(new WorldTile(1765, 10147,0));
        if (NpcChat.talkToNPC(DEVON)) {
            NpcChat.handle(true, "Okay." ,"Nope,");
        }
    }

    public void killConradKing() {
        cQuesterV2.status = "Step 6: Going to Kill Conrad King.";
        PathingUtil.walkToArea(CONRAD_AREA);
        if (Utils.clickNPC(7928, "Murder")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Brutally.");
            NPCInteraction.handleConversation();
        }
    }


    public void goToQueenOfThieves() {
        cQuesterV2.status = "Step 7: Going to Queen of Thieves.";
        PathingUtil.walkToArea(DEVON_AREA);
        if (!QUEEN_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Going to Queen";
            if (Utils.clickObj("Doorway", "Go-through")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
            Timer.waitCondition(() -> QUEEN_AREA.contains(Player.getPosition()), 7000, 10000);
        }
        if (QUEEN_AREA.contains(Player.getPosition())) {
            if (NpcChat.talkToNPC(QUEEN_OF_THIEVES_ID)) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
        }
    }

    public void goToCouncilor() {
        cQuesterV2.status = "Step 8: Going to Counciler's house";
        PathingUtil.walkToArea(HUGHS_HOUSE);
        if (Utils.clickObj("Chest", "Picklock")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }
    }

    public void returnToTomas() {
        cQuesterV2.status = "Step 9: Going to Tomas.";
        PathingUtil.walkToArea(START_AREA);
        if (NpcChat.talkToNPC(TOMAS)) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Let's talk about my quest.");
            NPCInteraction.handleConversation("Great! So can I have a reward?");
            NPCInteraction.handleConversation();
        }
    }

    public void returnToQueen() {
        cQuesterV2.status = "Step 10: Going to Queen of Thieves.";
        PathingUtil.walkToArea(QUEEN_AREA);
        if (NpcChat.talkToNPC(QUEEN_OF_THIEVES_ID)) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            NPCInteraction.handleConversation();
        }
    }

    int GameState_SETTING = 1672;

    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return cQuesterV2.taskList.size() > 0 && cQuesterV2.taskList.get(0).equals(this);
    }

    @Override
    public void execute() {
        if (Quest.THE_QUEEN_OF_THIEVES.getState().equals(Quest.State.COMPLETE)) {
            cQuesterV2.taskList.remove(this);
            return;
        }
        if (!checkFavour()) {
            Log.error("Missing 20% Port favour");
            cQuesterV2.taskList.remove(this);
            return;
        }

        int varbitId = QuestVarbits.QUEST_THE_QUEEN_OF_THIEVES.getId();
        Log.info("Queen of Thieves GameState setting: " + GameState.getSetting(1672));
        if (Utils.getVarBitValue(varbitId) == 0) {
            buyItems();
            getItems();
            startQuest();

        } else if (Utils.getVarBitValue(varbitId) == 1) {
            step2();

        } else if (Utils.getVarBitValue(varbitId) == 2) {
            goToRobertORiley();

        } else if (Utils.getVarBitValue(varbitId) == 3) {
            goToRobertORiley();

        } else if (Utils.getVarBitValue(varbitId) == 4 ||
                Utils.getVarBitValue(varbitId) == 5) {
            goToDevon();

        } else if (Utils.getVarBitValue(varbitId) == 6) {
            killConradKing();

        } else if (Utils.getVarBitValue(varbitId) >= 7 &&
                Utils.getVarBitValue(varbitId) < 10) {
            goToQueenOfThieves();

        } else if (Utils.getVarBitValue(varbitId) == 10) {// 20490) {
            goToCouncilor();

        } else if (Utils.getVarBitValue(varbitId) == 11) {//20491) {
            returnToTomas();
        } else if (Utils.getVarBitValue(varbitId) == 12) {
            returnToQueen();
        } else if (Quest.THE_QUEEN_OF_THIEVES.getState().equals(Quest.State.COMPLETE)) {
            Utils.closeQuestCompletionWindow();

            // use scroll for favour
            Optional<InventoryItem> closestToMouse =
                    Query.inventory().idEquals(21775).findClosestToMouse();
            if (closestToMouse.map(item -> item.click("Read")).orElse(false)) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Yes.");
                NPCInteraction.handleConversation();
            }
        }
    }

    @Override
    public String questName() {
        return "Queen of Thieves (" + Utils.getVarBitValue(QuestVarbits.QUEST_THE_QUEEN_OF_THIEVES.getId()) + ")";
    }

    @Override
    public boolean checkRequirements() {
        return checkFavour();
    }

    @Override
    public List<Requirement> getGeneralRequirements() {
        return Arrays.asList(
                new SkillRequirement(Skills.SKILLS.THIEVING, 20),
              //  new FavourRequirement(Favour.PISCARILIUS, 20),
                new QuestRequirement(Quest.CLIENT_OF_KOUREND, Quest.State.COMPLETE),
                new QuestRequirement(Quest.X_MARKS_THE_SPOT, Quest.State.COMPLETE)
        );
    }

    @Override
    public List<ItemRequirement> getBuyList() {
        return null;
    }

    @Override
    public boolean isComplete() {
        return Quest.THE_QUEEN_OF_THIEVES.getState().equals(Quest.State.COMPLETE);
    }

}
