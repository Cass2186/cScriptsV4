package scripts.QuestPackages.KnightsSword;

import dax.shared.helpers.questing.Quest;
import dax.walker.utils.AccurateMouse;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSTile;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.MonkeyMadnessI.MonkeyMadnessI;
import scripts.QuestSteps.BuyItemsStep;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.ItemRequirement;
import scripts.Requirements.Requirement;
import scripts.Tasks.Priority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KnightsSword implements QuestTask {


    private static KnightsSword quest;

    public static KnightsSword get() {
        return quest == null ? quest = new KnightsSword() : quest;
    }

    int REDBERRY_PIE = 2325;
    int LOBSTER = 379;
    int STEEL_PICKAXE = 1269;
    int IRON_BAR = 2351;
    int BLURITE_ORE = 668;
    int BLURITE_SWORD = 667;
    int PEST_CONTROL_TELEPORT = 12407;
    int PORTRAIT = 666;

    RSTile SAFE_TILE = new RSTile(3067, 9583, 0);

    RSArea START_AREA = new RSArea(new RSTile(2979, 3339, 0), new RSTile(2974, 3344, 0));
    RSArea RELDO_AREA = new RSArea(new RSTile(3212, 3491, 0), new RSTile(3208, 3496, 0));
    RSArea HUT_AREA = new RSArea(new RSTile(3000, 3143, 0), new RSTile(2998, 3146, 0));
    RSArea SIR_VYN_ROOM = new RSArea(new RSTile(2981, 3335, 2), new RSTile(2985, 3334, 2));
    RSArea BLURITE_AREA = new RSArea(new RSTile(3067, 9583, 0), new RSTile(3068, 9584, 0));


    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(REDBERRY_PIE, 1, 500),
                    new GEItem(ItemID.LOBSTER, 35, 50),
                    new GEItem(ItemID.STEEL_PICKAXE, 1, 500),
                    new GEItem(ItemID.IRON_BAR, 4, 50),
                    new GEItem(ItemID.VARROCK_TELEPORT, 5, 50),
                    new GEItem(ItemID.FALADOR_TELEPORT, 5, 50),
                    new GEItem(ItemID.PEST_CONTROL_TELEPORT, 5, 50),
                    new GEItem(ItemID.AMULET_OF_GLORY[2], 1, 20),
                    new GEItem(ItemID.COMBAT_BRACELET[0], 1, 20),
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
        BankManager.checkEquippedGlory();
        BankManager.depositAll(true);
        BankManager.withdraw(1, true, ItemID.AMULET_OF_GLORY[2]);
        BankManager.withdraw(5, true, ItemID.FALADOR_TELEPORT);
        BankManager.withdraw(4, true, ItemID.VARROCK_TELEPORT);
        BankManager.withdraw(2, true, ItemID.STAMINA_POTION[0]);
        BankManager.withdraw(1, true, REDBERRY_PIE);
        BankManager.withdraw(8, true, LOBSTER);
        BankManager.withdraw(1, true, STEEL_PICKAXE);
        BankManager.withdraw(4, true, IRON_BAR);
        BankManager.withdraw(4, true, PEST_CONTROL_TELEPORT);
        BankManager.close(true);
    }

    public void startQuest() {
        if (!BankManager.checkInventoryItems(IRON_BAR, STEEL_PICKAXE)) {
            buyItems();
            getItems();
        } else {
            cQuesterV2.status = "Going to start";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(START_AREA);
            if (NpcChat.talkToNPC("Squire")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Talk about other things.", "And how is life as a squire?");
                NPCInteraction.handleConversation("I can make a new sword if you like...");
                NPCInteraction.handleConversation("So would these dwarves make another one?");
                NPCInteraction.handleConversation("Ok, I'll give it a go." ,"Yes.");
                NPCInteraction.handleConversation();
            }
        }
    }

    public void step2() {
        cQuesterV2.status = "Going to Reldo";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(RELDO_AREA);
        if (NpcChat.talkToNPC("Reldo")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("What do you know about the Imcando dwarves?");
            NPCInteraction.handleConversation();
        }
    }

    public void step3() {
        cQuesterV2.status = "Going to Thurgo";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(HUT_AREA);
        if (NpcChat.talkToNPC("Thurgo")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Would you like a redberry pie?");
            NPCInteraction.handleConversation("Can you make a special sword for me?");
            NPCInteraction.handleConversation();
        }
    }

    public void step4() {
        cQuesterV2.status = "Going to Thurgo";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(HUT_AREA);
        if (NpcChat.talkToNPC("Thurgo")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Can you make a special sword for me?");
            NPCInteraction.handleConversation();
        }
    }

    public void step5() {
        cQuesterV2.status = "Going to Squire";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(START_AREA);
        if (NpcChat.talkToNPC("Squire")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Talk about other things.");
            NPCInteraction.handleConversation();
        }
    }

    public void step6() {
        if (Inventory.find(PORTRAIT).length < 1) {
            cQuesterV2.status = "Going to Sir Vyin's Room";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(SIR_VYN_ROOM);
            if (SIR_VYN_ROOM.contains(Player.getPosition())) {
                RSNPC[] sirVyn = NPCs.findNearest(4736);

                if (sirVyn.length > 0) {
                    while (SIR_VYN_ROOM.contains(sirVyn[0])) {
                        General.sleep(General.random(500, 4000));

                        if (Utils.clickObj(24057, "Open")) // opens door
                            Timer.abc2WaitCondition(() -> Objects.findNearest(15, 24057).length == 0, 10000, 25000);

                        cQuesterV2.status = "Waiting for Vyin to leave room";
                        General.println("[Debug]: " + cQuesterV2.status);

                    }
                    if (!SIR_VYN_ROOM.contains(sirVyn[0])) {
                        if (Utils.clickObj(2271, "Open"))
                            Timer.waitCondition(() -> Objects.findNearest(20, 2272).length > 0, 5000, 7000);

                        if (Utils.clickObj(2272, "Search")) {
                            NPCInteraction.waitForConversationWindow();
                            NPCInteraction.handleConversation();
                        }
                    }
                }
            }
        }
    }

    public void givingThrugoPictureStep6b() {
        if (Inventory.find(PORTRAIT).length > 0) {
            cQuesterV2.status = "Giving Thurgo picture";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(HUT_AREA);

            if (NpcChat.talkToNPC("Thurgo")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("About that sword...");
                NPCInteraction.handleConversation();
            }
        }
    }

    int BLURITE_ROCK = 11378;

    public void gettingOreStep7() {
        if (Inventory.find(BLURITE_ORE).length < 1 && Inventory.find(BLURITE_SWORD).length < 1) {
            if (Inventory.find(STEEL_PICKAXE).length > 0) {
                cQuesterV2.status = "Going to get blurite ore";
                General.println("[Debug]: " + cQuesterV2.status);
                if (PathingUtil.walkToArea(BLURITE_AREA)) {
                    if (Prayer.getPrayerPoints() > 0 && Skills.getActualLevel(Skills.SKILLS.PRAYER) >= 43)
                        Prayer.enable(Prayer.PRAYERS.PROTECT_FROM_MELEE);

                    PathingUtil.clickScreenWalk(SAFE_TILE);

                    if (Utils.clickObj(BLURITE_ROCK, "Mine")) {
                        Timer.waitCondition(() -> Inventory.find(BLURITE_ORE).length > 0, 16000, 22000);
                        Timer.waitCondition(() -> Objects.findNearest(1, BLURITE_ROCK).length > 0 || Combat.isUnderAttack(), 26000, 42000);
                    }

                    RSItem[] invLob = Inventory.find(ItemID.LOBSTER);
                    if (Combat.getHPRatio() < General.random(45, 65) && invLob.length > 0)
                        Clicking.click("Eat", invLob[0]);

                    if (Objects.findNearest(1, BLURITE_ROCK).length > 0)
                        if (Utils.clickObj(BLURITE_ROCK, "Mine"))
                            Timer.waitCondition(() -> Inventory.find(BLURITE_ORE).length > 1, 16000, 22000);

                    invLob = Inventory.find(ItemID.LOBSTER);
                    if (Combat.getHPRatio() < General.random(45, 65) && invLob.length > 0)
                        invLob[0].click("Eat");
                }
            }
        }
    }


    public void step8() {
        if (Inventory.find(BLURITE_ORE).length > 0) {
            Prayer.disable(Prayer.PRAYERS.PROTECT_FROM_MELEE);
            cQuesterV2.status = "Going to get blurite sword";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(HUT_AREA);
            if (NpcChat.talkToNPC("Thurgo")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Can you make that replacement sword now?");
                NPCInteraction.handleConversation();
            }

            RSItem[] sword = Inventory.find(BLURITE_SWORD);
            if (Inventory.find(BLURITE_ORE).length > 0 && sword.length > 0) {
                if (sword[0].click("Drop"))
                    Timer.waitCondition(() -> Inventory.find(BLURITE_SWORD).length == 0, 3000, 4000);
            }

            if (NpcChat.talkToNPC("Thurgo")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Can you make that replacement sword now?");
                NPCInteraction.handleConversation();
            }

            Utils.clickGroundItem(BLURITE_SWORD);


        }
    }


    public void step9() {
        if (Inventory.find(BLURITE_SWORD).length > 0) {
            cQuesterV2.status = "Finishing quest";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(START_AREA);
            if (NpcChat.talkToNPC("Squire")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Talk about other things.");
                NPCInteraction.handleConversation();
            }
        }
    }


    int GAME_SETTING = 122;

    @Override
    public void execute() {
        if (Game.getSetting(GAME_SETTING) == 0) {
            buyItems();
            getItems();
            startQuest();

        } else if (Game.getSetting(GAME_SETTING) == 1) {
            step2();

        } else if (Game.getSetting(GAME_SETTING) == 2) {
            step3();

        } else if (Game.getSetting(GAME_SETTING) == 3) {
            step4();

        } else if (Game.getSetting(GAME_SETTING) == 4) {
            step5();

        } else if (Game.getSetting(GAME_SETTING) == 5) {
            step6();
            givingThrugoPictureStep6b();
            ;

        } else if (Game.getSetting(GAME_SETTING) == 6) {
            gettingOreStep7();
            step8();
            step9();

        } else if (Game.getSetting(GAME_SETTING) >= 7) {
            Utils.closeQuestCompletionWindow();
            Utils.continuingChat();
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
    public String toString() {
        return "Knight's Sword";
    }

    @Override
    public String questName() {
        return "Knight's Sword";
    }

    @Override
    public boolean checkRequirements() {
        return Skills.SKILLS.MINING.getActualLevel() >= 10;
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
