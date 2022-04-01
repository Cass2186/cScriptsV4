package scripts.QuestPackages.LostTribe;

import dax.walker.utils.AccurateMouse;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.*;
import org.tribot.api2007.types.*;
import org.tribot.script.sdk.Quest;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.NatureSpirit.NatureSpirit;
import scripts.QuestSteps.BuyItemsStep;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.InventoryRequirement;
import scripts.Requirements.ItemReq;
import scripts.Requirements.ItemRequirement;
import scripts.Requirements.Requirement;
import scripts.Tasks.Priority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LostTribe implements QuestTask {

    private static LostTribe quest;

    public static LostTribe get() {
        return quest == null ? quest = new LostTribe() : quest;
    }


    /**
     * Items
     */
    int STEEL_PICKAXE = 1269;
    int UNLIT_CANDLE_LANTERN = 4529;
    int LIT_CANDLE_LANTERN = 4531;
    int LOCKPICK = 1523;
    int TINDERBOX = 590;
    int BROACH = 5008;
    int BOOK = 5009;
    int CHEST_KEY = 5010;
    int HAM_HOOD = 4302;
    int SILVERWARE = 5011;

    /**
     * NPCs
     */
    int HANS = 3105;
    int SIGMUND = 6288;
    int DUKE_HORACIO = 815;
    int COOK = 4626;
    int BOB = 10619;
    int FATHER_AERECK = 921;
    int RELDO = 6203;
    int MISTAG = 5324;
    int KAZGAR = 5325;

    /**
     * Objects
     */
    int HOLE = 6898;
    int BOOKCASE = 6916;
    int CLOSED_CHEST = 6910;
    int TRAPDOOR = 5492;
    int CRATE = 6911;


    //** AREAS
    RSArea START_AREA = new RSArea(new RSTile(3208, 3224, 1), new RSTile(3212, 3218, 1));
    RSArea COOK_AREA = new RSArea(new RSTile(3211, 3212, 0), new RSTile(3206, 3217, 0));
    RSArea BOB_AREA = new RSArea(new RSTile(3232, 3201, 0), new RSTile(3228, 3205, 0));
    RSArea CHURCH_AREA = new RSArea(new RSTile(3247, 3204, 0), new RSTile(3240, 3212, 0));
    RSArea HANS_AREA = new RSArea(new RSTile(3217, 3227, 0), new RSTile(3222, 3220, 0));
    // in front of castle, wait for hans
    RSArea CAVE_ENTRANCE = new RSArea(new RSTile(3219, 9615, 0), new RSTile(3216, 9619, 0));
    RSArea RELDO_AREA = new RSArea(new RSTile(3214, 3490, 0), new RSTile(3207, 3497, 0));
    RSArea AFTER_TUNNEL_ENTRANCE = new RSArea(new RSTile(3220, 9619, 0), new RSTile(3230, 9611, 0));
    RSArea GOBLIN_VILLAGE = new RSArea(new RSTile(2961, 3510, 0), new RSTile(2954, 3512, 0));
    RSArea END_OF_MINE = new RSArea(new RSTile(3318, 9606, 0), new RSTile(3304, 9617, 0));
    RSArea ENTRANCE_TO_HIDEOUT = new RSArea(
            new RSTile[]{
                    new RSTile(3168, 3252, 0),
                    new RSTile(3166, 3250, 0),
                    new RSTile(3164, 3252, 0),
                    new RSTile(3166, 3254, 0)
            }
    );
    RSArea HIDE_OUT = new RSArea(new RSTile(3142, 9655, 0), new RSTile(3159, 9642, 0));

    ArrayList<RSTile> pathThroughTunnel = new ArrayList<RSTile>(Arrays.asList(PATH_THROUGH_TUNNEL));
    public static final RSTile[] PATH_PT_1 = new RSTile[]{new RSTile(3222, 9617, 0), new RSTile(3224, 9617, 0), new RSTile(3226, 9615, 0), new RSTile(3228, 9613, 0), new RSTile(3230, 9611, 0), new RSTile(3232, 9610, 0), new RSTile(3234, 9610, 0), new RSTile(3236, 9610, 0), new RSTile(3239, 9610, 0), new RSTile(3241, 9611, 0), new RSTile(3243, 9612, 0), new RSTile(3246, 9612, 0), new RSTile(3247, 9614, 0), new RSTile(3248, 9616, 0), new RSTile(3249, 9618, 0), new RSTile(3249, 9620, 0), new RSTile(3251, 9621, 0), new RSTile(3251, 9623, 0), new RSTile(3253, 9625, 0), new RSTile(3253, 9627, 0), new RSTile(3253, 9629, 0)};
    public static final RSTile[] PATH_THROUGH_TUNNEL = new RSTile[]{new RSTile(3221, 9618, 0), new RSTile(3224, 9618, 0), new RSTile(3226, 9615, 0), new RSTile(3228, 9612, 0), new RSTile(3232, 9610, 0), new RSTile(3235, 9610, 0), new RSTile(3239, 9610, 0), new RSTile(3242, 9612, 0), new RSTile(3245, 9612, 0), new RSTile(3248, 9614, 0), new RSTile(3248, 9617, 0), new RSTile(3249, 9620, 0), new RSTile(3251, 9623, 0), new RSTile(3253, 9626, 0), new RSTile(3253, 9629, 0), new RSTile(3250, 9631, 0), new RSTile(3247, 9631, 0), new RSTile(3244, 9631, 0), new RSTile(3241, 9631, 0), new RSTile(3238, 9631, 0), new RSTile(3235, 9631, 0), new RSTile(3232, 9632, 0), new RSTile(3230, 9636, 0), new RSTile(3230, 9639, 0), new RSTile(3230, 9642, 0), new RSTile(3233, 9645, 0), new RSTile(3236, 9648, 0), new RSTile(3240, 9648, 0), new RSTile(3243, 9648, 0), new RSTile(3245, 9645, 0), new RSTile(3242, 9643, 0), new RSTile(3241, 9640, 0), new RSTile(3245, 9638, 0), new RSTile(3248, 9639, 0), new RSTile(3251, 9641, 0), new RSTile(3252, 9644, 0), new RSTile(3253, 9647, 0), new RSTile(3254, 9650, 0), new RSTile(3256, 9654, 0), new RSTile(3259, 9656, 0), new RSTile(3262, 9656, 0), new RSTile(3265, 9656, 0), new RSTile(3268, 9656, 0), new RSTile(3271, 9656, 0), new RSTile(3275, 9653, 0), new RSTile(3277, 9650, 0), new RSTile(3277, 9647, 0), new RSTile(3274, 9646, 0), new RSTile(3271, 9645, 0), new RSTile(3267, 9644, 0), new RSTile(3267, 9640, 0), new RSTile(3270, 9638, 0), new RSTile(3273, 9638, 0), new RSTile(3276, 9637, 0), new RSTile(3279, 9638, 0), new RSTile(3283, 9641, 0), new RSTile(3285, 9644, 0), new RSTile(3288, 9645, 0), new RSTile(3291, 9645, 0), new RSTile(3294, 9644, 0), new RSTile(3297, 9643, 0), new RSTile(3300, 9642, 0), new RSTile(3302, 9639, 0), new RSTile(3304, 9636, 0), new RSTile(3305, 9633, 0), new RSTile(3305, 9630, 0), new RSTile(3302, 9629, 0), new RSTile(3299, 9628, 0), new RSTile(3296, 9626, 0), new RSTile(3296, 9623, 0), new RSTile(3294, 9620, 0), new RSTile(3292, 9616, 0), new RSTile(3292, 9613, 0), new RSTile(3294, 9610, 0), new RSTile(3295, 9607, 0), new RSTile(3298, 9606, 0), new RSTile(3302, 9606, 0), new RSTile(3306, 9609, 0), new RSTile(3309, 9612, 0), new RSTile(3312, 9612, 0), new RSTile(3315, 9612, 0)};

    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.STEEL_PICKAXE, 1, 550),
                    new GEItem(ItemID.STAMINA_POTION[0], 2, 15),
                    new GEItem(UNLIT_CANDLE_LANTERN, 1, 500),
                    new GEItem(ItemID.LOCKPICK, 1, 500),
                    new GEItem(ItemID.TINDERBOX, 1, 500),
                    new GEItem(ItemID.VARROCK_TELEPORT, 5, 40),
                    new GEItem(ItemID.FALADOR_TELEPORT, 5, 40),
                    new GEItem(ItemID.LUMBRIDGE_TELEPORT, 5, 40),
                    new GEItem(ItemID.RING_OF_WEALTH[0], 1, 25),
                    new GEItem(ItemID.AMULET_OF_GLORY[2], 1, 25)
            )
    );

    InventoryRequirement initialItemReqs = new InventoryRequirement(new ArrayList<>(
            Arrays.asList(
                    new ItemReq(ItemID.STEEL_PICKAXE, 1, 1)

            )
    ));

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
        BankManager.checkEquippedGlory();
        BankManager.withdraw(1, true, STEEL_PICKAXE);
        BankManager.withdraw(1, true, UNLIT_CANDLE_LANTERN);
        BankManager.withdraw(1, true, LOCKPICK);
        BankManager.withdraw(1, true, TINDERBOX);
        BankManager.withdraw(5, true,
                ItemID.VARROCK_TELEPORT);
        BankManager.withdraw(12,
                true,
                ItemID.LUMBRIDGE_TELEPORT);
        BankManager.withdraw(5, true,
                ItemID.FALADOR_TELEPORT);
        BankManager.withdraw(2, true, BankManager.STAMINA_POTION[0]);
        BankManager.close(true);
        Utils.useItemOnItem(TINDERBOX, UNLIT_CANDLE_LANTERN);
    }

    public void startQuest() {
        cQuesterV2.status = "Starting Quest";
        PathingUtil.walkToArea(START_AREA);
        if(NpcChat.talkToNPC(SIGMUND)){
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Do you have any quests for me?", "Have you any quests for me?");
            NPCInteraction.handleConversation("Yes.");
            NPCInteraction.handleConversation();
        }
    }

    public void talkToDukeHoracio() {
        cQuesterV2.status = "Talking to Duke Horacio";
        PathingUtil.walkToArea(START_AREA);
        if (NpcChat.talkToNPC(DUKE_HORACIO)) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("What happened in the cellar?");
            NPCInteraction.handleConversation();
        }
    }

    public void talkToCook() {
        cQuesterV2.status = "Talking to Craft";
        PathingUtil.walkToArea(COOK_AREA);
        if(NpcChat.talkToNPC(COOK)) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Ask about what happened in the castle cellar", "Do you know what happened in the castle cellar?");
            NPCInteraction.handleConversation();
        }
    }

    public void talkToBob() {
        if (RSVarBit.get(532).getValue() == 1) {
            cQuesterV2.status = "Talking to Bob";
            PathingUtil.walkToArea(BOB_AREA);
            if(NpcChat.talkToNPC("Bob")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Do you know what happened in the castle cellar?");
                NPCInteraction.handleConversation();
            }
        }
    }

    public void talkToHans() {
        if (RSVarBit.get(532).getValue() == 1) {
            cQuesterV2.status = "Going to Hans";
            PathingUtil.walkToArea(HANS_AREA);
            RSNPC[] hans = NPCs.find(HANS);
            if (hans.length > 0) {
                cQuesterV2.status = "Waiting for Hans...";
                Timer.waitCondition(() -> HANS_AREA.contains(hans[0].getPosition()) && NPCs.find(HANS)[0].isOnScreen(), 45000);
            }
            cQuesterV2.status = "Talking to Hans";
            if(NpcChat.talkToNPC(HANS)) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Do you know what happened in the cellar?");
                NPCInteraction.handleConversation();
            }
        }
    }

    public void talkToAereck() {
        if (RSVarBit.get(532).getValue() == 1) {
            cQuesterV2.status = "Talking to Father Aereck";
            PathingUtil.walkToArea(CHURCH_AREA);
            if (NpcChat.talkToNPC(FATHER_AERECK)) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Do you know what happened in the castle cellar?");
                NPCInteraction.handleConversation();
            }
        }
    }

    public void step3() {
        cQuesterV2.status = "Reporting to Duke Horacio";
        PathingUtil.walkToArea(START_AREA);
        if (NpcChat.talkToNPC(DUKE_HORACIO)) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Father Aereck says he saw something in the cellar",
                    "Hans says he saw something in the cellar",
                    "Bob says he saw something in the cellar",
                    "The cook says he saw something in the cellar",
                    "Hans says he saw something in the cellar");
            NPCInteraction.handleConversation();
        }
    }

    public void step4() {
        cQuesterV2.status = "Going to dig the tunnel";
        PathingUtil.walkToArea(CAVE_ENTRANCE);
        if (Utils.useItemOnObject(STEEL_PICKAXE, 6898)) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }
    }

    public void step5() {
        if (Inventory.find(BROACH).length < 1) {
            Utils.useItemOnItem(UNLIT_CANDLE_LANTERN, TINDERBOX);
            cQuesterV2.status = "Going to get Broach";
            if (CAVE_ENTRANCE.contains(Player.getPosition()) && !AFTER_TUNNEL_ENTRANCE.contains(Player.getPosition())) {
                PathingUtil.walkToArea(CAVE_ENTRANCE);
                if (Utils.clickObj(HOLE, "Squeeze-through"))
                    Timer.waitCondition(() -> !CAVE_ENTRANCE.contains(Player.getPosition()), 15000);
            }
            if (AFTER_TUNNEL_ENTRANCE.contains(Player.getPosition())) {
                if (GroundItems.find(BROACH).length > 0) {
                    PathingUtil.localNavigation(GroundItems.find(BROACH)[0].getPosition());

                    Utils.idle(3000, 6000);
                }
                if (Utils.clickGroundItem(BROACH))
                    Timer.waitCondition(() -> Inventory.find(BROACH).length > 0, 15000);
                leaveCave();
            }
        }
    }

    public void leaveCave() {
        PathingUtil.localNavigation(new RSTile(3222, 9618, 0));
        if (Utils.clickObj(6899, "Squeeze-through"))
            Timer.waitCondition(() -> CAVE_ENTRANCE.contains(Player.getPosition()), 15000);
    }

    public void step6() {
        if (Inventory.find(BROACH).length > 0) {
            cQuesterV2.status = "Reporting to Duke Horacio";
            PathingUtil.walkToArea(START_AREA);
            if (NpcChat.talkToNPC(DUKE_HORACIO)) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("I dug through the rubble...");
            }
        }
    }

    public void step7() {
        if (Inventory.find(BOOK).length < 1) {
            cQuesterV2.status = "Going to Reldo";
            PathingUtil.walkToArea(RELDO_AREA);
            if (NpcChat.talkToNPC(RELDO)) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Ask about the brooch.");
                NPCInteraction.handleConversation();
            }
            if (Utils.clickObj(6916, "Search")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
        }
        RSItem[] book = Inventory.find(BOOK);
        if (book.length > 0 && book[0].click("Read")) {
            Timer.waitCondition(() -> Interfaces.get(183, 16) != null, 5000);

            //flip through book pages here
            if (Interfaces.get(183, 16) != null && RSVarBit.get(541).getValue() != 5) {
                Interfaces.get(183, 16).click();

                Utils.idle(150, 900);
                Interfaces.get(183, 16).click();

                Utils.idle(150, 900);
                Interfaces.get(183, 16).click();

                Utils.idle(150, 900);
                Interfaces.get(183, 16).click();

                Utils.idle(150, 900);
                if (RSVarBit.get(541).getValue() != 5) {
                    Interfaces.get(183, 48).click();

                    Utils.idle(150, 900);
                }

            }
        }
    }

    public void step8() {
        cQuesterV2.status = "8: Going to Goblin Generals.";
        PathingUtil.walkToArea(GOBLIN_VILLAGE);
        if (NpcChat.talkToNPC(669)) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Have you ever heard of the Dorgeshuun?");
            NPCInteraction.handleConversation("It doesn't really matter");
            NPCInteraction.handleConversation("Well either way they refused to Fight");
            NPCInteraction.handleConversation("Well I found a brooch underground...");
            NPCInteraction.handleConversation("Well why not show me both greetings?");
            NPCInteraction.handleConversation();
        }
    }

    public void step9() {
        cQuesterV2.status = "Going to Duke Horacio";
        PathingUtil.walkToArea(START_AREA);
        if (NpcChat.talkToNPC(DUKE_HORACIO)) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("I spoke to the generals in the goblin village...");
            NPCInteraction.handleConversation();
        }
    }

    public void step10() {
        Utils.useItemOnItem(UNLIT_CANDLE_LANTERN, TINDERBOX);
        cQuesterV2.status = "Going to Tunnel";
        if (!CAVE_ENTRANCE.contains(Player.getPosition()) && !END_OF_MINE.contains(Player.getPosition()) &&
                !AFTER_TUNNEL_ENTRANCE.contains(Player.getPosition())) {
            PathingUtil.walkToArea(CAVE_ENTRANCE);
        }
        if (CAVE_ENTRANCE.contains(Player.getPosition())) {
            Utils.clickObj(HOLE, "Squeeze-through");
            Timer.waitCondition(() -> !CAVE_ENTRANCE.contains(Player.getPosition()), 15000);

            Utils.idle(500, 2500);
        }
        if (AFTER_TUNNEL_ENTRANCE.contains(Player.getPosition())) {
            cQuesterV2.status = "Navigating Tunnel";

            Utils.drinkPotion(ItemID.STAMINA_POTION);

            Walking.walkPath(PATH_PT_1);

            Utils.idle(1000, 4000);
            Walking.walkPath(PATH_THROUGH_TUNNEL);

            Utils.idle(5000, 25000);
        }
        if (END_OF_MINE.contains(Player.getPosition())) {
            cQuesterV2.status = "Talking to Mistag";
            NpcChat.talkToNPC(MISTAG);
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            GameTab.open(GameTab.TABS.EMOTES);
            if (GameTab.getOpen() == GameTab.TABS.EMOTES) {
                Interfaces.get(216, 1, 22).getY();
                if (!Interfaces.get(216).getAbsoluteBounds().contains(Mouse.getPos())) {
                    Mouse.moveBox(Interfaces.get(216).getAbsoluteBounds());
                    if ((Interfaces.get(216, 1, 22).getY() < 450)) { // is off screen if this is true

                        while (Interfaces.get(216, 1, 22).getAbsolutePosition().getY() > (Interfaces.get(216, 2, 1).getAbsolutePosition().getY())) {
                            Mouse.scroll(false); //scrolls down
                            General.sleep(General.random(200, 500));
                        }
                        RSInterface emote = Interfaces.get(216, 1, 22);
                        if (emote != null && emote.click()) {
                            NPCInteraction.waitForConversationWindow();
                            NPCInteraction.handleConversation();
                        }
                    }
                }
            }
        }
    }

    public void step11() {
        cQuesterV2.status = "Going to Duke Horacio";
        PathingUtil.walkToArea(START_AREA);
        if(NpcChat.talkToNPC(DUKE_HORACIO)) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("I've made contact with the cave goblins...");
            NPCInteraction.handleConversation();
        }
    }

    public void step12() {
        if (Inventory.find(CHEST_KEY).length < 1 && Inventory.find(HAM_HOOD).length < 1) {
            cQuesterV2.status = "Pickpocketing Sigmund";
            PathingUtil.walkToArea(START_AREA);
            if (NPCs.find(SIGMUND).length > 0) {
                PathingUtil.walkToTile(NPCs.find(SIGMUND)[0].getPosition());
                if (AccurateMouse.click(NPCs.find(SIGMUND)[0], "Pickpocket"))
                    Timer.waitCondition(() -> Inventory.find(CHEST_KEY).length > 0, 10000);
            }
        }
        if (Inventory.find(CHEST_KEY).length > 0 && Inventory.find(HAM_HOOD).length < 1) {
            cQuesterV2.status = "Opening Chest";
            PathingUtil.walkToTile(new RSTile(3209, 3216, 1));
            if (Utils.clickObj(CLOSED_CHEST, "Open")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
        }
    }

    public void step13() {
        if (Inventory.find(HAM_HOOD).length > 0) {
            cQuesterV2.status = "Going to Duke Horacio";
            PathingUtil.walkToArea(START_AREA);
            if (NpcChat.talkToNPC(DUKE_HORACIO)) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Did you know Sigmund is a member of HAM?");
                NPCInteraction.handleConversation();
            }
        }
    }


    public void getSilverware() {
        if (Inventory.find(HAM_HOOD).length > 0 && Inventory.find(SILVERWARE).length < 1) {
            if (!HIDE_OUT.contains(Player.getPosition())) {
                cQuesterV2.status = "Going to Hideout";
                PathingUtil.walkToArea(ENTRANCE_TO_HIDEOUT);
                Utils.clickObj(TRAPDOOR, "Pick-Lock");
                NPCInteraction.waitForConversationWindow();
                Utils.clickObj(TRAPDOOR, "Climb-down");
                Timer.waitCondition(() -> HIDE_OUT.contains(Player.getPosition()), 10000);
            }
            if (HIDE_OUT.contains(Player.getPosition())) {
                cQuesterV2.status = "Getting Silverware";
                PathingUtil.localNavigation(new RSTile(3152, 9646));
                if (Utils.clickObj(CRATE, "Search")) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                }
            }
        }
        if (Inventory.find(SILVERWARE).length > 0) {
            cQuesterV2.status = "Going to Duke Horacio";
            PathingUtil.walkToArea(START_AREA);
            if (NpcChat.talkToNPC(DUKE_HORACIO)) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("I found the missing silverware in the HAM cave!");
                NPCInteraction.handleConversation();
            }
        }
    }


    public void step14() {
        cQuesterV2.status = "Going to give treaty";
        if (!CAVE_ENTRANCE.contains(Player.getPosition()) && !END_OF_MINE.contains(Player.getPosition()) &&
                !AFTER_TUNNEL_ENTRANCE.contains(Player.getPosition())) {
            PathingUtil.walkToArea(CAVE_ENTRANCE);
            Utils.clickObj(HOLE, "Squeeze-through");
            Timer.waitCondition(() -> !CAVE_ENTRANCE.contains(Player.getPosition()), 15000);
        }
        if (AFTER_TUNNEL_ENTRANCE.contains(Player.getPosition())) {
            if (NPCs.find(KAZGAR).length > 0) {
                PathingUtil.localNavigation(NPCs.find(KAZGAR)[0].getPosition());
                AccurateMouse.click(NPCs.find(KAZGAR)[0], "Follow");

                Utils.idle(4000, 7000);
            }
        }
        if (END_OF_MINE.contains(Player.getPosition())) {
            cQuesterV2.status = "Talking to Mistag";
            if (NpcChat.talkToNPC(MISTAG)) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                Utils.idle(1500, 4000);
                NPCInteraction.handleConversation();
                cQuesterV2.status = "Finishing...";
                //cut scene
                while (RSVarBit.get(532).getValue() == 10) {
                    General.sleep(200, 500);
                    NPCInteraction.handleConversation();
                }
            }
        }
    }

    @Override
    public void execute() {
        if (Game.getSetting(465) == 0) { // Varbit 532:0->1 and 537:0->3
            buyItems();
            getItems();
            startQuest();
        }
        if (RSVarBit.get(532).getValue() == 1) {
            talkToDukeHoracio();
            talkToCook();
            talkToBob();
            talkToAereck();
            talkToHans();
        }
        if (RSVarBit.get(532).getValue() == 2) {
            step3();
        }
        if (RSVarBit.get(532).getValue() == 3) {
            step4();
        }
        if (RSVarBit.get(532).getValue() == 4) {
            step5();
            step6();

        }
        if (RSVarBit.get(532).getValue() == 5) {
            step7();
        }
        if (RSVarBit.get(532).getValue() == 6) {
            step8();
        }
        if (RSVarBit.get(532).getValue() == 7) {
            step9();
            step10();
        }
        if (RSVarBit.get(532).getValue() == 8) {
            step11();
        }
        if (RSVarBit.get(532).getValue() == 9) {
            General.println("Varbit 534: " + RSVarBit.get(534).getValue());
            if (RSVarBit.get(534).getValue() == 0) {
                step12();
            }
            if (RSVarBit.get(534).getValue() == 1) {
                step13();
            }
            if (RSVarBit.get(534).getValue() == 3) {
                getSilverware();
            }
        }
        if (RSVarBit.get(532).getValue() == 10) {
            step14();
        }
        if (RSVarBit.get(532).getValue() == 11) {
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
        return "Lost Tribe";
    }

    @Override
    public boolean checkRequirements() {
        if (Skills.getActualLevel(Skills.SKILLS.MINING) < 17 || Skills.getActualLevel(Skills.SKILLS.THIEVING) < 13
                || Skills.getActualLevel(Skills.SKILLS.AGILITY) < 13) {
            General.println("[Debug]: We are missing a Quest requirement (17 mining, 13 thieving & Agility required)");
            return false;
        }
        if (Game.getSetting(63) < 6) {
            General.println("[Debug]: We are missing a Quest requirement: Rune mysteries quest");
            return false;
        }
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

    @Override
    public boolean isComplete() {
        return Quest.THE_LOST_TRIBE.getState().equals(Quest.State.COMPLETE);
    }
}
